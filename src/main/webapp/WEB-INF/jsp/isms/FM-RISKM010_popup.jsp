<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/WEB-INF/include/contents.jsp"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<META http-equiv="X-UA-Compatible" content="IE=edge">
<title>SK broadband ISAMS</title>
<link rel="stylesheet" type="text/css" href="/common/css/reset.css"/>
<link rel="stylesheet" type="text/css" href="/common/css/common.css"/>
<link rel="stylesheet" type="text/css" href="/common/css/pop.css"/>
<link rel="stylesheet" type="text/css" href="/common/css/jquery-ui.css"/>
<!--[if IE]> <script src="/common/js/html5.js"></script> <![endif]-->
<script type="text/javascript" src="/common/js/jquery.js"></script>
<script type="text/javascript" src="/common/js/ismsUI.js"></script>
<script type="text/javascript" src="/common/js/jquery.form.js"></script>
<script type="text/javascript" src="/common/js/common.js"></script>
<script type="text/javascript" src="/common/js/jquery-ui.js"></script>
<script  type="text/javascript">

$(document).ready(function() {
	var urrRskKey = $("#urrRskKey").val();
	$(".radioDep").click(function() {
		getUrrDetail($(this).val());
	});

	if (urrRskKey) {
		$("#depList").hide();
		getUrrDetail(urrRskKey);
	}
	else {
		resetForm();
	}

	// 1. 담당자 지정 전
	if ($("#ursStaCod").val() == "Q") {
		$(".btnAction.Q").show();
		$("#tblDetail").hide();
		$(".radioDep").prop("disabled", true);
	}
	else {
		$("#ursReqMdh").addClass("readonly");

		if (!urrRskKey) {
			var flag = true;
			$(".radioDep").each(function() {
				$this = $(this);
				if ($this.hasClass("P")) {
					$this.click();
					flag = false;
					return false;
				};
			})

			if (flag) {
				$(".radioDep").first().click();
			}
		}

		// 부서 클릭시 라디오버튼 클릭
		$(".udmDepNam").click(function() {
			$(this).parent().find(".radioDep").click();
		})
	}

	$("#accordion").accordion({animate: 0});

	$(".inputDate").not(".readonly").datepicker({
		changeMonth: true,
        changeYear: true,
        showButtonPanel: true,
		minDate : "today"
	});

	// 2017-05-26, 조치완료일은 이전날짜 선택가능
	$("#urrEndMdh").not(".readonly").datepicker({
		changeMonth: true,
        changeYear: true,
        showButtonPanel: true
    });

	$(".btnDate").click(function() {
		$(this).prev().focus();
	});
});

function getUrrDetail(key) {
	resetForm();
	$("#tblDetail").show();

	if (!key) {
		return false;
	}

	$.post("FM-RISKM010_urrDetail.do", {urrRskKey: key}, function(data) {
		res = data.result;
		var urrRskKey = res.urrRskKey;
		var urrStaCod = res.urrStaCod;
		var urrStaNam = res.urrStaNam;
		var urrRskPrc = res.urrRskPrc;
		var urrMesDes = res.urrMesDes ? res.urrMesDes : "";
		var urrLimDes = res.urrLimDes ? res.urrLimDes : "";
		var urrRstDes = res.urrRstDes ? res.urrRstDes : "";
		var urrPlnSta = res.urrPlnSta;
		var urrEndSta = res.urrEndSta;
		var urrPlnMdh = res.urrPlnMdh ? res.urrPlnMdh : "";
		var urrEndMdh = res.urrEndMdh ? res.urrEndMdh : "";

		$("#urrRskKey").val(urrRskKey);
		$("#urrStaCod").val(urrStaCod);
		$("#urrStaNam").val(urrStaNam);
		$("#urrRskPrc").val(urrRskPrc);
		$("#urrMesDes").val(urrMesDes);
		$("#urrLimDes").val(urrLimDes);
		$("#urrMesDes").val(urrMesDes);
		$("#urrRstDes").val(urrRstDes);
		$("#urrPlnSta").val(urrPlnSta);
		$("#urrEndSta").val(urrEndSta);
		$("#urrPlnMdh").val(urrPlnMdh);
		$("#urrEndMdh").val(urrEndMdh);

		fillForm(urrStaCod);
		var active = 0;
		// 2nd. tab
		if (urrStaCod == "C" || urrStaCod == "V" || urrStaCod == "A"|| urrStaCod == "R") {
			active = 1;
		}
		$("#accordion").accordion("option", "active", active);
	}, "json")
	.fail(function() {
		alert("데이터를 가져오는데 실패했습니다.");
	});
}

function resetForm() {
	$(".urrInput, .urrTextarea, .urrInputDate, .urrSelectBox").val("");
	$(".btnAction").hide();
	$(".urrInputDate").prop("disabled", true);
	$(".urrTextarea").prop("readonly", true);
	$(".urrSelectBox").prop("disabled", true);
}

function fillForm(sts) {
/*
	Q	대책검토중
	S	대책담당자지정
	X	대책수립반려
	P	대책수립검토중
	C	대책수립승인
	R	대책실행반려
	A	대책최종완료
*/
	if (sts) {
		$(".btnAction." + sts).show();
		$(".urrTextarea." + sts).prop("readonly", false);
		$(".urrSelectBox." + sts).prop("disabled", false);
		$(".urrInputDate." + sts).prop("disabled", false);
	}
	if($("#authGbn").val() != "A"){
		$(".bthAuth").hide();
		$("#urrRskPrc").prop("disabled", true);
	}
}
// 1. 대책수립 담당자 지정
function fn_mngSave() {
	var flag = true;
	if (!$("#ursReqMdh").val()) {
		alert("처리완료 요청일을 입력하시기 바랍니다.");
		$("#ursReqMdh").focus();
		return false;
	}

	$(".urrRstUsrs").each(function() {
		$this = $(this);
		if (!$this.val()) {
			var dep = $this.parent().parent().find(".udmDepNam").text();
			alert(dep + " 대책수립 담당자를 지정해 주세요.\n[관리적 취약점 부서설정]에서 부서별 담당자를 설정할 수 있습니다.");
			flag = false;
			return false;
		}
	});

	if (!flag) {
		return false;
	}

	var urrRskKeys = $(".urrRskKeys").map(function() { return $(this).val(); }).get().join(",");
	var urrRstUsrs = $(".urrRstUsrs").map(function() { return $(this).val(); }).get().join(",");
	$("#urrRskKey").val(urrRskKeys);
	$("#urrRstUsr").val(urrRstUsrs);

	if (confirm("대책수립 담당자를 지정 하시겠습니까?")) {
		url = "${pageContext.request.contextPath}/riskm/FM-RISKM010_MNGSAVE.do";
		$.ajax({
			url			: url,
			type		: "post",
			data		: $("#popForm").serialize(),
			dataType	: "json",
			success	: function(data){
				alert("저장되었습니다.");
				finStatus(true);
			},
			error : function(){
				alert('error');
			}
		});
	}
}

function fn_save(){

	var url = "${pageContext.request.contextPath}/riskm/FM-RISKM010_RSKSAVE.do";

	$.ajax({
		url			: url,
		type		: "post",
		data		: $("#popForm").serialize(),
		dataType	: "json",
		success	: function(data){
			alert("저장 되었습니다.");
			finStatus();
		},
		error : function(){
			alert('error');
		}
	});
}

function fn_req(){

	// 2. 조치계획 승인요청
	if ($("#urrStaCod").val() == "S" || $("#urrStaCod").val() == "X"){
		if ( !$("#urrPlnSta").val()) {
			alert("조치계획을 선택해 주세요.");
			$("#urrPlnSta").focus();
			return;
		}
		if ( !$("#urrPlnMdh").val()) {
			alert("조치계획일을 선택해 주세요.");
			$("#urrPlnMdh").focus();
			return;
		}
		if ( !$("#urrMesDes").val()) {
			alert("조치방안을 입력해 주세요.");
			$("#urrMesDes").focus();
			return;
		}
		if( !$("#urrLimDes").val())  {
			alert("조치 제약사항을 입력해 주세요.");
			$("#urrLimDes").focus();
			return;
		}
	};

	// 3. 조치결과 승인요청
	if ($("#urrStaCod").val() == "C" || $("#urrStaCod").val() == "R"){
		if ( !$("#urrEndSta").val()) {
			alert("조치결과를 선택해 주세요.");
			$("#urrEndSta").focus();
			return;
		}
		if ( !$("#urrEndMdh").val()) {
			alert("조치완료일을 선택해 주세요.");
			$("#urrEndMdh").focus();
			return;
		}if( !$("#urrRstDes").val()) {
			alert("조치결과를 입력해 주세요.");
			$("#urrRstDes").focus();
			return;
		}
	};

	if (confirm("승인 요청 하시겠습니까?")) {
		var url = "${pageContext.request.contextPath}/riskm/FM-RISKM010_RSKREQ.do";
		$.ajax({
			url			: url,
			type		: "post",
			data		: $("#popForm").serialize(),
			dataType	: "json",
			success	: function(data){
				alert("승인요청 되었습니다.");
				finStatus();
			},
			error : function(){
				alert('error');
			}
		});
	}
}

function fn_appr(){
	// 4. 최종 승인
	if ($("#urrStaCod").val() == "V") {
		if ( !$("#urrRskPrc").val()) {
			alert("조치 완료결과를 선택해 주세요.");
			$("#urrRskPrc").focus();
			return;
		}
	}

	if (confirm("승인 하시겠습니까?")) {
		var url = "${pageContext.request.contextPath}/riskm/FM-RISKM010_RSKAPPR.do";
		$.ajax({
			url			: url,
			type		: "post",
			data		: $("#popForm").serialize(),
			dataType	: "json",
			success	: function(data){
				alert("승인되었습니다.");
				$("#urrRskKey").val("");
				finStatus();
			},
			error : function(){
				alert('error');
			}
		});
	}
}

function fn_reject(){
	if (confirm("반려 하시겠습니까?")) {
		var url = "${pageContext.request.contextPath}/riskm/FM-RISKM010_RSKREJ.do";
		$.ajax({
			url			: url,
			type		: "post",
			data		: $("#popForm").serialize(),
			dataType	: "json",
			success	: function(data){
				alert("반려되었습니다.");
				$("#urrRskKey").val("");
				finStatus();
			},
			error : function(){
				alert('error');
			}
		});
	}
}

function finStatus(close) {
	try {
		window.opener.reloadList();
	}
	catch (ex) {}
	if (close) {
		self.close();
	}
	else {
		self.location.href = "FM-RISKM010_popup.do?ursRskKey="+ $("#ursRskKey").val() +"&urrRskKey=" + $("#urrRskKey").val();
	}
}
</script>
</head>
<body>
<form id="popForm" name="popForm" method="post">
	<input type="hidden" id="ursRskKey" name="ursRskKey" value="${paramMap.ursRskKey}">
	<input type="hidden" id="ursStaCod" name="ursStaCod" value="${paramMap.ursStaCod}">
	<input type="hidden" id="authGbn" name="authGbn" value="${authGbn}">
	<input type="hidden" id="urrRskKey" name="urrRskKey" value="${urrRskKey}" class="urrInput" />
	<input type="hidden" id="urrRstUsr" name="urrRstUsr" class="urrInput" />
	<input type="hidden" id="urrStaCod" name="urrStaCod" class="urrInput" />
	<div id="skipnavigation">
	    <ul>
	        <li><a href="#content-box">본문 바로가기</a></li>
	    </ul>
	</div>
	<div id="wrap" class="pop">
		<header>
			<div class="borderBox"></div>
			<h1>관리적 위험 조치</h1>
		</header>
		<article class="cont" id="content-box">
			<div class="cont_container">
				<div class="contents">
					<div class="title">위험관리 세부내용</div>
					<div class="talbeArea">
						<table summary="위험관리 Header">
							<colgroup>
								<col width="20%">
								<col width="30%">
								<col width="20%">
								<col width="30%">
							</colgroup>
							<tbody>
							<tr>
								<th scope="row" class="listTitle"><label for="urgSvcCod">서비스</label></th>
								<td class="fontLeft">
									<c:out value="${paramMap.uagSvrNam}" />
								</td>
								<th scope="row"><label for="urgRskPrc">처리상태</label></th>
								<td class="fontLeft last impact">
									<c:out value="${paramMap.ursStaNam}" />
								</td>
							</tr>
							<tr>
								<th scope="row"><label for="ursReqMdh">조치완료 요청일</label></th>
								<td class="fontLeft">
									<input id="ursReqMdh" name="ursReqMdh" class="inputText cal inputDate" style="width:80px" maxlength="15" value="<c:out value='${paramMap.ursReqMdh}'/>" readonly="readonly"/>
									<button id="ursReqMdhBtn" type="button" class="btnDate"><span class="icoCal"><em>달력</em></span></button>
								</td>
								<th scope="row"><label for="urgRskChk">위험진단결과</label></th>
								<td class="fontLeft last">
									<c:out value="${paramMap.ursChkNam}" />
								</td>
							</tr>
							<tr>
								<th scope="row"><label for="cocKey">우려사항</label></th>
								<td class="fontLeft last" colspan="3">
									<c:out value="${paramMap.usoCocNam}" />
								</td>
							</tr>
							<tr>
								<th scope="row"><label for="usoCocDes">우려사항 설명</label></th>
								<td class="fontLeft last" colspan="3">
									<textarea id="usoCocDes" name="usoCocDes" readonly="readonly" class="txtAreaType06 txtAreaTypeFull">${paramMap.usoCocDes}</textarea>
								</td>
							</tr>
							<tr>
								<th scope="row"><label for="urvVlbDes">취약점 내용</label></th>
								<td class="fontLeft last" colspan="3">
									<textarea id="urvVlbDes" name="urvVlbDes" readonly="readonly" class="txtAreaType06 txtAreaTypeFull">${paramMap.urvVlbDes}</textarea>
								</td>
							</tr>
						</tbody>
						</table>
					</div>
				</div>

				<div class="contents" id="depList">
					<div class="title">대상 부서</div>
					<div class="talbeArea">
						<table>
							<colgroup>
								<col width="3%">
								<col width="">
								<col width="20%">
								<col width="17%">
								<col width="17%">
								<col width="18%">
							</colgroup>
                               <caption>업무담당자</caption>
							<thead>
								<tr>
									<th scope="row" colspan="2">부서</th>
									<th scope="row">처리상태</th>
									<th scope="row">조치 계획일</th>
									<th scope="row">완료 예정일</th>
									<th scope="row" class="last" colspan="2">담당자</th>
								</tr>
							</thead>
							<tbody id="workerList">
							<c:forEach var="dep" items="${depList}" varStatus="status">
								<tr>
									<td class="last"><input type="radio" name="radioDep" class="radioDep <c:out value="${dep.urrStaCod}"/>" value="<c:out value="${dep.urrRskKey}"/>"/></td>
									<td class="fontLeft udmDepNam"><c:out value="${dep.udmDepNam}" /></td>
									<td><c:out value="${dep.urrStaNam}"/></td>
									<td><c:out value="${dep.urrPlnMdh}"/></td>
									<td><c:out value="${dep.urrEndMdh}"/></td>
									<td class="last">
										<input type="hidden" value="<c:out value="${dep.urrRskKey}"/>" class="urrRskKeys" />
										<input type="hidden" id="urrId<c:out value="${dep.urrRskKey}"/>" class="urrRstUsrs" value="<c:out value="${dep.urrRstUsr}"/>" />
										<input type="text" id="urrName<c:out value="${dep.urrRskKey}"/>" style="width: 45px; border: 0; background-color: #fff;" class="inputText readonly" value="<c:out value="${dep.urrUsrNam}" />" readonly="readonly"/>
										<button type="button" class="btnUser btnAction bthAuth Q" style="padding: 0 0 0 4px; line-height: 20px" title="담당자 변경" onclick="userListPopup('urrId<c:out value="${dep.urrRskKey}"/>','','urrName<c:out value="${dep.urrRskKey}"/>');"><span class="icoPer"></span></button>
									</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>
				</div>

				<div id="tblDetail" class="contents">
					<div class="title">위험 조치 내역</div>
					<div id="accordion">
					    <h3>조치 계획</h3>
					    <div>
					   		<div class="talbeArea">
								<table summary="위험관리 세부내용">
									<colgroup>
										<col width="20%">
										<col width="30%">
										<col width="20%">
										<col width="30%">
									</colgroup>
									<tbody>
									<tr>
										<th scope="row"><label for="urrMesDes">조치 계획</label></th>
										<td class="fontLeft">
											<sb:select name="urrPlnSta" type="A" allText="선택" code="RSK_PLAN" classes="urrSelectBox S X P" />
										</td>
										<th scope="row"><label for="urrPlnMdh">조치 계획일</label></th>
										<td class="fontLeft last">
											<input id="urrPlnMdh" name="urrPlnMdh" class="inputText cal inputDate urrInputDate S X P" style="width:80px" maxlength="15" readonly="readonly"/>
											<button id="urrPlnMdhBtn" type="button" class="btnDate"><span class="icoCal"><em>달력</em></span></button>
										</td>
									</tr>
									<tr>
										<th scope="row"><label for="urrMesDes">조치 방안</label></th>
										<td class="fontLeft last" colspan="3">
											<textarea id="urrMesDes" name="urrMesDes" maxlength="250" class="txtAreaType05 txtAreaTypeFull urrTextarea S X P" readonly="readonly"></textarea>
										</td>
									</tr>
									<tr>
										<th scope="row"><label for="urrLimDes">조치 제약사항</label></th>
										<td class="fontLeft last" colspan="3">
											<textarea id="urrLimDes" name="urrLimDes" maxlength="250" class="txtAreaType06 txtAreaTypeFull urrTextarea S X P" readonly="readonly"></textarea>
										</td>
									</tr>
									</tbody>
								</table>
						  </div>
					  </div>
					  <h3>조치 결과</h3>
					  <div>
				  		<div class="talbeArea">
							<table summary="위험관리 세부내용">
								<colgroup>
									<col width="20%">
									<col width="30%">
									<col width="20%">
									<col width="30%">
								</colgroup>
								<tbody>
								<tr>
									<th scope="row"><label for="urrMesDes">조치 결과</label></th>
									<td class="fontLeft">
										<sb:select name="urrEndSta" type="A" allText="선택" code="RSK_RESULT" classes="urrSelectBox C R" />
									</td>
									<th scope="row"><label for="urrEndMdh">조치 완료일</label></th>
									<td class="fontLeft last">
										<input id="urrEndMdh" name="urrEndMdh" class="inputText cal urrInputDate C R" style="width:80px" maxlength="15" readonly="readonly"/>
										<button id="urrEndMdhBtn" type="button" class="btnDate"><span class="icoCal"><em>달력</em></span></button>
									</td>
								</tr>
								<tr>
									<th scope="row"><label for="urrMesDes">조치 내용</label></th>
									<td class="fontLeft last" colspan="3">
										<textarea id="urrRstDes" name="urrRstDes" maxlength="250" class="txtAreaType05 txtAreaTypeFull urrTextarea C R V" readonly="readonly"></textarea>
									</td>
								</tr>
								<tr>
									<th scope="row"><label for="urrRskPrc">조치 완료결과</label></th>
									<td class="fontLeft last" colspan="3">
										<sb:select name="urrRskPrc" type="A" allText="선택" code="RSKPRC" classes="urrSelectBox V" />
									</td>
								</tr>
								</tbody>
							</table>
						 </div>
					  </div>
					</div>
				</div>
			</div>
	       <div class="centerBtnArea bottomBtnArea">
				<button type="button" class="popClose close"><span class="icoClose">닫기</span></button>
				<ul class="btnList">
					<li class="btnAction S X C R"><button type="button" class="btnStrong" onclick="fn_save();"><span class="icoSave"></span>임시저장</button></li>
					<li class="btnAction S X"><button type="button" class="btnStrong" onclick="fn_req();"><span class="icoSave"></span>조치계획 승인요청</button></li>
					<li class="btnAction C R"><button type="button" class="btnStrong" onclick="fn_req();"><span class="icoSave"></span>최종 승인요청</button></li>
					<li class="btnAction bthAuth P"><button type="button" class="btnStrong" onclick="fn_appr();"><span class="icoSave"></span>조치계획 승인</button></li>
					<li class="btnAction bthAuth V"><button type="button" class="btnStrong" onclick="fn_appr();"><span class="icoSave"></span>최종 승인</button></li>
					<li class="btnAction bthAuth P V"><button type="button" class="btnStrong" onclick="fn_reject();"><span class="icoSave"></span>반려</button></li>
					<li class="btnAction bthAuth Q"><button type="button" class="btnStrong" onclick="fn_mngSave();"><span class="icoSave"></span>대책수립 담당자 지정</button></li>
				</ul>
           </div>
	    </article>
	</div>
</form>
</body>
</html>