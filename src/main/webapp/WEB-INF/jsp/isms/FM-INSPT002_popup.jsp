<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
<%@ include file="/WEB-INF/include/contents.jsp"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<META http-equiv="X-UA-Compatible" content="IE=edge">
<title>SK broadband ISAMS</title>
<link rel="stylesheet" type="text/css" href="/common/css/reset.css"/>
<link rel="stylesheet" type="text/css" href="/common/css/common.css"/>
<link rel="stylesheet" type="text/css" href="/common/css/sub.css"/>
<link rel="stylesheet" type="text/css" href="/common/css/pop.css"/>
<!--[if IE]> <script src="/common/js/html5.js"></script> <![endif]-->
<script type="text/javascript" src="/common/js/jquery.js"></script>
<script type="text/javascript" src="/common/js/ismsUI.js"></script>
<script type="text/javascript" src="/common/js/jquery.form.js"></script>
<script type="text/javascript" src="/common/js/jquery.chained.min.js"></script>
<script type="text/javascript" src="/common/js/common.js"></script>
<script type="text/javascript" src="/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="/ckfinder/ckfinder.js"></script>
<script  type="text/javascript">

$(document).ready(function() {
	getMngList();
	stOrgList();
	//CKEDITOR.replace("ufmFltDes");
});

function fn_save(){
	//$("#ufmFltDes").val(CKEDITOR.instances.ufmFltDes.getData());

	if($("#ufmCtrCod").val() == ""){
		alert("컴플라이언스를 선택하여 주세요.");
		$("#ufmCtrCod").focus();
		return;
	}

	if($("#ufmFltLvl").val() == ""){
		alert("결함등급을 선택하여 주세요.");
		$("#ufmFltLvl").focus();
		return;
	}

	if($("#ufmFltNam").val() == ""){
		alert("결함명을 입력하여 주세요.");
		$("#ufmFltNam").focus();
		return;
	}

	if($("#ufmFltDes").val() == ''){
		alert("결함내용을 입력하여 주세요.");
		$("#ufmFltDes").focus();
		return;
	}

	if($("input[name=ufpWrkId]").val() == undefined){
		alert("보완 담당자를 지정해 주세요.");
		return;
	}

	if($("#ufmFltKey").val() != "" && $("#ufmRstSta").val() == "") {
	 	alert("처리결과를 선택하여 주세요.");
	 	return;
	}

	if($("#ufmFltKey").val() == ""){
		url = "${pageContext.request.contextPath}/inspt/FM-INSPT002_W.do";
	} else {
		url = "${pageContext.request.contextPath}/inspt/FM-INSPT002_U.do";
	}

	$.ajax({
		url			: url,
		type		: "post",
		data		: $("#popForm").serialize(),
		dataType	: "json",
		success	: function(data){
			alert("저장되었습니다.");
			opener.location.reload();
			window.close();
		},
		error : function(){
			alert('error');
		}
	});
}

//업무담당자 조회
function getMngList(){
	$.ajax({
		url		: "${pageContext.request.contextPath}/inspt/FM-INSPT002_mngList.do",
		type		: "post",
		data		: {"ufmFltKey":$("#ufmFltKey").val()},
		dataType	: "json",
		success	: function(data){
			var workerList = "";
			for(var i=0; i<data.result.length; i++) {
				workerList += '<tr id="mng_'+data.result[i].ufpWrkId+'">';
				workerList += '<td>';
				workerList += '	<input type="checkbox" name="chkMng" class="chkNode01" value="'+data.result[i].ufpWrkId+'" >';
				workerList += '	<input type="hidden" id="type_'+data.result[i].ufpWrkId+'" name="mngType" value="R">';
				workerList += '	<input type="hidden" name="ufpWrkId" value="'+data.result[i].ufpWrkId+'">';
				workerList += '</td>';
				workerList += '<td>'+data.result[i].ufpSvcNam+'</td>';
				workerList += '<td>'+data.result[i].ufpDepNam+'</td>';
				workerList += '<td>'+data.result[i].uumUsrId+'</td>';
				workerList += '<td>'+data.result[i].uumUsrNam+'</td>';
				workerList += '<td class="last">'+data.result[i].uumPosNam+'</td>';
				workerList += '</tr>';
			}

			if(data.result.length == 0){
				workerList += '<tr id="noneData" class="last"><td class="last noDataList" colspan="7">지정된 보안 담당자가 없습니다.</td></tr>';
			}
			$("#setGridTb").html(workerList);
		 },
		 error : function(){
			 alert('error');
		 }
	 });
}

function selMngPopList(){
	var formData = $("#popForm").serialize();
	$.ajax({
		url			: "${pageContext.request.contextPath}/common/FM-COMMON_userlist.do",
		type		: "post",
		data		: formData,
		dataType	: "json",
		success	: function(data){
			var userList = "";
			for(var i=0; i<data.result.length; i++) {
				userList += '<tr id="'+data.result[i].uumUsrKey+'">';
				userList += '<td><input type="checkbox" name="chkPopUser" value="'+data.result[i].uumUsrKey+'" >';
				userList += ' <input type="hidden" name="popUfpWrkId" value="'+data.result[i].uumUsrKey+'" ></td>';
				userList += '<td>'+data.result[i].uumSvcNam+'</td>';
				userList += '<td>'+data.result[i].uagDepNam+'</td>';
				userList += '<td>'+data.result[i].uumUsrId+'</td>';
				userList += '<td>'+data.result[i].uumUsrNam+'</td>';
				userList += '<td class="last">'+data.result[i].uumPosNam+'</td>';
				userList += '</tr>';
			}
			if(data.result.length == 0){
				userList += '<tr class="last"><td class="last noDataList" colspan="6">담당자 정보가 없습니다.</td></tr>';
			}
			$("#popUsrList").html(userList);
		},
		error : function(data){
			alert("error");
		}
	});
}

function selMngList() {
	$("#noneData").remove();
	$("input[name=chkPopUser]:checked").each( function(){
		var id = $(this).val();
		var tag = $("#"+id).html();
		var inTag = '<tr id="mng_'+$(this).val()+'"><input type="hidden" id="type_'+$(this).val()+'" name="mngType" value="I">'+ tag +'</tr>';
		inTag = inTag.replace("chkPopUser", "chkMng");
		inTag = inTag.replace("popUfpWrkId", "ufpWrkId");
		$("#setGridTb").append(inTag);
	});

	$("#userPopBG").hide();
	$("#userLayerPop").hide();

	$(".talbeArea").css("z-index", "100");
	$("#wrap.pop .closeArea").show();
	$("#wrap.pop header").show();
}

function delWorkerList() {
	$("input[name=chkMng]:checked").each( function(){
		var id = $(this).val();
		$("#type_"+id).val("D");
		$("#mng_"+id).hide();
	});
}

function getDepthList(num){
	 $("#popCtrKey").val("");
	 $("#popCtrCod").val("");
	if(num == "1"){
		$("#depth1").html('<option value="">목적 선택 </option>');
		$("#depth2").html('<option value="">항목 선택 </option>');
		$("#depth3").html('<option value="">점검 선택 </option>');
	} else if(num == "2") {
		$("#depth2").html('<option value="">항목 선택 </option>');
		$("#depth3").html('<option value="">점검 선택 </option>');
	} else if(num == "3") {
		$("#depth3").html('<option value="">점검 선택 </option>');
	}

	var option ='';
	$.ajax({
		 url		: "${pageContext.request.contextPath}/comps/GetDepth.do",
		 type		: "post",
		 data		: {"code": num , "standard" :  $("#popStandard").val(), "depth1" : $("#depth1").val() , "depth2" : $("#depth2").val() , "depth3" : $("#depth3").val()},
		 dataType	: "json",
		 success	: function(data){
			 if(num == "4"){
				 $("#popCtrKey").val(data.result[0].ucmCtrKey);
				 $("#ucm3lvNam").html(data.result[0].ucm3lvNam);
				 $("#ucm3lvDtl").html(data.result[0].ucm3lvDtl);
				 $("#ucm2lvDtl").html( data.result[0].ucm2lvDtl);
			 }else{
				setSelectBox(data, num);
				if(num == "1"){
					$("#standardNam").html($("#popStandard > option:selected").text());
					$("#ucm1lvCod").html("");
					$("#ucm1lvNam").html("");
					$("#ucm3lvNam").html("");
					$("#ucm3lvDtl").html("");
					$("#ucm2lvNam").html("");
					$("#ucm2lvDtl").html("");
				}
				if(num == "2"){
					$("#ucm1lvCod").html(data.result[0].code);
					$("#ucm1lvNam").html(data.result[0].ucm1lvNam);
					$("#ucm3lvNam").html("");
					$("#ucm3lvDtl").html("");
					$("#ucm2lvNam").html("");
					$("#ucm2lvDtl").html("");
				}
				if(num == "3"){
					$("#ucm2lvNam").html(data.result[0].ucm2lvNam);
					$("#ucm3lvNam").html("");
					$("#ucm3lvDtl").html("");
					$("#ucm2lvDtl").html("");
				}
			 }
		 },
		 error : function(){
			 alert('error' + id);
		 }
	 });
}

function setSelectBox(data, num){
	var option ='';
	if(num == '1') {
		option = '<option value="A">목적 선택 </option>';
	} else if(num == '2') {
		option = '<option value="">항목 선택 </option>';
	} else if(num == '3') {
		option = '<option value="">점검 선택 </option>';
	}

	for(var i=0; i < data.result.length; i++) {
		option += '<option value="' + data.result[i].code+ '">' + data.result[i].code + '</option>';
	}

	if(num == '1') {
		$("#depth1").html(option);
	} else if(num == '2') {
		$("#depth2").html(option);
	} else if(num == '3') {
		$("#depth3").html(option);
	}

}

function setMappting(){
	var depth3 = $("#depth3").val() ? $("#depth3").val() : $("#depth2").val();
	var ctrKey = $("#popCtrKey").val();
	if (!ctrKey) {
		alert("통제항목을 선택해주세요.");
		return false;
	}

	$("#ufmCtrKey").val(ctrKey);
	$("#ufmCtrNam").val(depth3);
	$("#ufmCtrDes").val($("#ucm2lvNam").text());
	$("#popBG").hide();
	$("#addStandLayerPop").hide();

	$(".talbeArea").css("z-index", "100");
	$("#wrap.pop .closeArea").show();
	$("#wrap.pop header").show();
}

</script>
</head>
<body>
<form id="popForm" name="popForm" method="post">
	<input type="hidden" id="ufmFltKey" name="ufmFltKey" value="${info.ufmFltKey}" />
	<div id="skipnavigation">
	    <ul>
	        <li><a href="#content-box">본문 바로가기</a></li>
	    </ul>
	</div>
	<div id="wrap" class="pop">
		<header>
			<div class="borderBox"></div>
			<h1>결함조치내역</h1>
		</header>
		<article class="cont" id="content-box">
			<div class="cont_container">
				<div class="contents">
					<div class="talbeArea">
						<table summary="위험관리 세부내용">
							<colgroup>
								<col width="20%">
								<col width="30%">
								<col width="20%">
								<col width="30%">
							</colgroup>
							<caption>결함조치내역</caption>
							<tbody>
								<tr>
									<th scope="row" class="listTitle"><label for="ufmCtrCod">컴플라이언스</label></th>
									<td class="fontLeft">
										<sb:select name="ufmCtrCod" type="A" code="STND" allText="선택" value="${info.ufmCtrCod}"/>
									</td>
									<th scope="row" class="listTitle"><label for="ufmFltLvl">결함등급</label></th>
									<td class="fontLeft last">
										<select id="ufmFltLvl" name="ufmFltLvl" class="selectBox">
											<option value="">선택</option>
											<option value="1" <c:if test="${info.ufmFltLvl eq '1'}">selected="selected"</c:if>>결함</option>
											<option value="2" <c:if test="${info.ufmFltLvl eq '2'}">selected="selected"</c:if>>중결함</option>
										</select>
									</td>
								</tr>
								<tr>
									<th scope="row"><label for="ufmFltNam">관련조항</label></th>
									<td class="fontLeft last" colspan="3">
										<input type="text" id="ufmCtrDes" name="ufmCtrDes" class="inputText" style="width:150px" value="${info.ufmCtrDes}">
										<input type="hidden" id="ufmCtrKey" name="ufmCtrKey" readonly="readonly" value="${info.ufmCtrKey}">
										<input type="text" id="ufmCtrNam" name="ufmCtrNam" class="inputText" style="width:80px" readonly="readonly" value="${info.ufmCtrNam}">
										<button type="button" class="openPop"><span class="icoSynch"></span>통제항목 선택</button>
										<div id="addStandLayerPop" class="layerPop">
											<input type="hidden" id="popCtrKey" name="popCtrKey">
                                            <div class="popTitle">
                                                <p>통제항목 연결</p>
                                            </div>
                                            <div class="search">
                                                <div class="defalt">
                                                    <fieldset class="searchForm">
                                                        <legend>기본검색</legend>
                                                        <ul class="detail">
                                                            <li class="width100">
                                                                <span class="title"><label for="popStandard">표준</label></span>
                                                                <sb:select name="popStandard" type="A" code="STND" allText="선택" event="getDepthList(1);"/>
                                                            </li>
                                                            <li class="last">
                                                                <span class="title">통제항목</span>
                                                                <select id="depth1" name="depth1" onchange="getDepthList(2);" class="selectBox" title="항목 선택">
                                                                    <option value="">목적 선택</option>
                                                                </select>
                                                                <select id="depth2" name="depth2" onchange="getDepthList(3);" class="selectBox" title="항목 선택">
                                                                    <option value="">항목 선택</option>
                                                                </select>
                                                                <select id="depth3" name="depth3" onchange="getDepthList(4);" class="selectBox" title="항목 선택">
                                                                    <option value="">점검 선택</option>
                                                                </select>
                                                            </li>
                                                        </ul>
                                                    </fieldset>
                                                </div>
                                            </div>
                                            <div class="layerContents">
                                                <div class="contents">
                                                    <div style="z-index: 100;" class="selectDetail">
                                                        <ul class="detailArea">
                                                            <li class="floatList">
                                                                <dl>
                                                                    <dt>표준명</dt>
                                                                    <dd id="standardNam"></dd>
                                                                </dl>
                                                            </li>
                                                            <li class="floatList">
                                                                <dl>
                                                                    <dt>통제점검</dt>
                                                                    <dd id="ucm1lvCod"></dd>
                                                                  </dl>
                                                            </li>
                                                            <li>
                                                                <dl>
                                                                    <dt>통제목적</dt>
                                                                    <dd id="ucm1lvNam"></dd>
                                                                </dl>
                                                            </li>
                                                             <li>
                                                                <dl>
                                                                    <dt>통제항목</dt>
                                                                    <dd id="ucm2lvNam"></dd>
                                                                </dl>
                                                            </li>
                                                             <li>
                                                                <dl>
                                                                    <dt>상세내용</dt>
                                                                    <dd id="ucm2lvDtl"></dd>
                                                                </dl>
                                                            </li>

                                                            <li>
                                                                <dl>
                                                                    <dt>점검항목</dt>
                                                                    <dd id="ucm3lvNam"></dd>
                                                                </dl>
                                                            </li>
                                                            <li>
                                                                <dl>
                                                                    <dt>설명</dt>
                                                                	<dd id="ucm3lvDtl"></dd>
                                                                </dl>
                                                            </li>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="centerBtnArea bottomBtnArea" style="position: relative; top: -20px;">
                                            	<ul class="btnList">
                                                	<li><button type="button" class="btnStrong" onclick="setMappting();"><span class="icoPer"></span>통제항목 선택</button></li>
                                           		</ul>
                                        	</div>
                                        	<button type="button" class="popClose"><span class="icoClose"><em>닫기</em></span></button>
                                        </div>
                                        <div id="popBG" class="popBG" style="display: none;"></div>
									</td>
								</tr>
								<tr>
									<th scope="row"><label for="ufmFltNam">결함명</label></th>
									<td class="fontLeft last" colspan="3">
										<input type="text" id="ufmFltNam" name="ufmFltNam" class="inputText" value="${info.ufmFltNam}">
									</td>
								</tr>
								<tr>
									<th scope="row"><label for="ufmFltDes">결함내용</label></th>
									<td class="fontLeft last" colspan="3">
										<textarea id="ufmFltDes" name="ufmFltDes" maxlength="250" class="txtAreaType04">${info.ufmFltDes}</textarea>
									</td>
								</tr>
								<c:if test="${not empty info.ufmFltKey}">
								<tr>
									<th scope="row"><label for="ufmFltMdh">결함등록일자</label></th>
									<td class="fontLeft">
										<input type="text" class="inputText wdShort" value="<c:out value='${info.ufmFltMdh}'/>" readonly="readonly"/>
									</td>
									<th scope="row"><label for="ufmFltDes">처리결과</label></th>
									<td class="fontLeft last">
										<input type="text" class="inputText wdShort" value="<c:out value='${info.ufmRstNam}'/>" readonly="readonly"/>
									</td>
								</tr>
								</c:if>
							</tbody>
						</table>
					</div>
				</div>

				<div class="contents">
					<div class="title">보완 담당자</div>
					<div class="talbeArea">
						<div class="topBtnArea">
							<ul class="btnList">
								<li>
									<button type="button" class="openPop"><span class="icoPer"></span>보완담당자 선택</button>
                                    <div id="userLayerPop" class="layerPop" style="display: none;">
                                        <div class="popTitle">
                                            <p>보완 담당자 지정</p>
                                        </div>
                                        <div class="search">
                                            <div class="defalt">
                                                <fieldset class="searchForm">
                                                    <legend>기본검색</legend>
                                                    <ul class="detail">
                                                        <li>
															<span class="title"><label for="service">서비스</label></span>
							                   				<sb:select name="service" type="S" />
														</li>
														<li>
															<span class="title"><label for="stOrg">부서</label></span>
															<sb:select name="stOrg" type="stOrg" allText="본부전체" />
															<sb:select name="hqOrg" type="hqOrg" allText="그룹,담당전체" />
															<sb:select name="gpOrg" type="gpOrg" allText="팀전체" />
														</li>
                                                        <li class="last" style="width: 100%;">
                                                            <span class="title"><label for="uumUsrNam">이름</label></span>
                                                            <input id="uumUsrNam" name="uumUsrNam" class="inputText" type="text" title="이름 입력" placeholder="담당자 이름을 입력하세요.">
                                                        </li>
                                                    </ul>
                                                    <button type="button" onclick="selMngPopList();" class="btnSearch defaltBtn">검색</button>
                                                </fieldset>
                                            </div>
                                        </div>
                                        <div class="layerContents">
                                            <div class="contents">
                                                <div class="talbeArea" style="z-index: 100;">
                                                    <table>
														<colgroup>
															<col width="5%">
															<col width="20%">
															<col width="25%">
															<col width="*">
															<col width="20%">
															<col width="15%">
														</colgroup>
						                                <caption>업무담당자</caption>
														<thead>
															<tr>
																<th scope="row"></th>
																<th scope="row">서비스</th>
																<th scope="row">부서</th>
																<th scope="row">아이디</th>
																<th scope="row">이름</th>
																<th scope="row" class="last">직급</th>
															</tr>
														</thead>
														<tbody id="popUsrList">
															<tr class="last"><td class="last noDataList" colspan="6">담당자 검색하여 주세요.</td></tr>
														</tbody>
													</table>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="centerBtnArea bottomBtnArea" style="position: relative; top: -20px;">
                                            <ul class="btnList">
                                                <li><button type="button" class="btnStrong" onclick="selMngList();"><span class="icoPer"></span>담당자 선택</button></li>
                                            </ul>
                                        </div>
                                        <button type="button" class="popClose"><span class="icoClose"><em>닫기</em></span></button>
                                    </div>
                                    <div id="userPopBG" class="popBG" style="display: none;"></div>
								</li>
								<li><button type="button" onclick="delWorkerList();"><span class="icoDel"></span>삭제</button></li>
							</ul>
						</div>
						<table>
							<colgroup>
								<col width="5%">
								<col width="20%">
								<col width="25%">
								<col width="*">
								<col width="20%">
								<col width="20%">
							</colgroup>
                               <caption>업무담당자</caption>
							<thead>
								<tr>
									<th scope="row"><input type="checkbox" class="chkAll01" title="리스트 전체 선택"></th>
									<th scope="row">서비스</th>
									<th scope="row">부서</th>
									<th scope="row">아이디</th>
									<th scope="row">이름</th>
									<th scope="row" class="last">직급</th>
								</tr>
							</thead>
							<tbody id="setGridTb">
							</tbody>
						</table>
					</div>
				</div>
			</div>
	       <div class="centerBtnArea bottomBtnArea">
				<button type="button" class="popClose close"><span class="icoClose">닫기</span></button>
				<ul class="btnList">
					<li><button type="button" class="btnStrong" onclick="fn_save();"><span class="icoSave"></span>저장</button></li>
				</ul>
			</div>
	    </article>
	</div>
</form>
</body>
</html>