<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="com.ckeditor.CKEditorConfig"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="ce" uri="http://ckeditor.com"%>
<%@ include file="/WEB-INF/include/head.jsp"%>
<%@ include file="/WEB-INF/include/contents.jsp"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<META http-equiv="X-UA-Compatible" content="IE=edge">
<title>SK broadband ISAMS</title>
<link rel="stylesheet" type="text/css" href="/common/css/reset.css" />
<link rel="stylesheet" type="text/css" href="/common/css/common.css" />
<link rel="stylesheet" type="text/css" href="/common/css/pop.css" />
<link rel="stylesheet" type="text/css" href="/common/css/sub.css" />
<!--[if IE]> <script src="/common/js/html5.js"></script> <![endif]-->
<script type="text/javascript" src="/common/js/jquery.js"></script>
<script type="text/javascript" src="/common/js/jquery.form.js"></script>
<script type="text/javascript" src="/common/js/ismsUI.js"></script>
<script type="text/javascript" src="/common/js/common.js"></script>
</head>
<body>
	<script type="text/javascript">
	var fileCnt = 0;
	$(document).ready(function() {
		// 관련문서 갖고오기
		getGuideList();

		fileList();

		$(".btnOpen").click();

	});

	// 관련문서 갖고오기
	function getGuideList(){
		if (!$("#ctrKey")) {
			return false;
		}
		var ctrKey = $("#ctrKey").val()+"";
		var ctrKeyArray = ctrKey.split(",");

		for(var i=0; i<ctrKeyArray.length; i++){

			$.ajax({
				url		: "${pageContext.request.contextPath}/mwork/FM-MWORK_getGuideList.do",
				type	: "post",
				data	: {"ctrKey": ctrKeyArray[i]},
				dataType: "json",
				success	: function(data){
					var list = "";
					var key = "";
					for(var i=0; i<data.result.length; i++){
						if(i == data.result.length-1){
							list += '<a href="javascript:brdPopup('+data.result[i].ucbBrdKey+')">'+data.result[i].ubmBrdTle+'</a>';
						}else {
							list += '<a href="javascript:brdPopup('+data.result[i].ucbBrdKey+')">'+data.result[i].ubmBrdTle+'</a><br />';
						}
						key = data.result[i].ucbCtrKey;
					}
					$("#"+key).html(list);

					if(data.result.length == 0){
						$("#"+key).html("해당 관련문서가 없습니다.");
					}
				}
			});
		}
	}

	// 첨부파일 다운로드
	function getFile(key, where) {
		$("#downKey").val(key);
		$("#where").val("wrk");
		document.fileDown.action = "${pageContext.request.contextPath}/common/getFileDown.do";
		document.fileDown.submit();
	}

	// 관련문서 게시판 팝업창
	function brdPopup(brdKey) {
		window.open("", "getbrdView","width=500, height=400, menubar=no, location=no, status=no, scrollbars=yes");
		$("#brdViewKey").val(brdKey);
		document.brdViewForm.submit();
	}

	function allWorkList(){

		var worker = $("#utw_wrk_id").val();
		var trcKey = $("#utw_trc_key").val();
		var trm_cod_nam = $("#trm_cod_nam").val();
		var utd_doc_nam = $("#utd_doc_nam").val();

		$.ajax({
			url : "${pageContext.request.contextPath}/mwork/getAllWorkList.do",
			type : "post",
			data : {
				"worker" : worker, "trcKey" : trcKey
			},
			dataType : "json",
			success : function(data) {
				var list = "";
				for(var i=0; i<data.resultList.length; i++){
					list += "<li><span>";
					list += (i+1);
					list += "</span><div class='centets'><ul class='sort statusDivisionThree dualLine'><li><div class='sortType01'><strong>서비스 :</strong><p>";
					list += "<%=C_SES_SERVICE_NAME%>";
					list += "</p></div></li><li><div class='sortType01'><strong>업무주기 :</strong><p>";
					list += trm_cod_nam;
					list += "</p></div></li><li><div class='sortType01'><strong>수행기간 :</strong><p>";
					list += data.resultList[i].utwStrDat;
					list += "~<br>";
					list += data.resultList[i].utwEndDat;
					list += "</p></div></li></ul><div class='sortType02'><strong>업무명 :</strong><p>";
					list += utd_doc_nam;
					list += "</p></div>";
					list += "<div class='uplidFileList'><p class='titleArea'>첨부파일</p><ul class='listArea' id='fileList_"+i+"'></ul>";
					list += "<div class='topBtnArea'><ul class='btnList'><li><form enctype='multipart/form-data' method='post' id='upload_File_"+i+"' name='upload_File_"+i+"'><input type='hidden' id='wKey' name='wKey' value='"+data.resultList[i].utwWrkKey+"'/><input type='hidden' id='wKey_"+i+"' name='wKey_"+i+"' value='"+data.resultList[i].utwWrkKey+"'/><input type='file' name='b_file' id ='b_file' style='width: 300px'></form></li><li><button onclick='fileUploadSubmit("+i+");'><span class='icoUpload'></span>파일 업로드</button></li></ul></div>";
					list += "</div></div></li>";
				}
				$("#allList").html(list);

				for(var i=0; i<data.resultList.length; i++){
					var wrk_key = data.resultList[i].utwWrkKey;
					fileList(wrk_key,i);
				}
			},
			error : function() {
				alert("실패");
			},
			complete : function() {
			}
		});
	}

	function fileList(){
		var wrk_key =  $("#utw_wrk_key").val();
		$.ajax({
			url : "${pageContext.request.contextPath}/mwork/getAllWorkList_file.do",
			type : "post",
			data : {
				"wrk_key" : wrk_key
			},
			dataType : "json",
			success : function(data) {
				var list = "";
				for(var i=0; i<data.resultList.length; i++){
					var wrk = "wrk";
					list += "<li><a href='javascript:getFile("+data.resultList[i].umf_fle_key+")'><span class='icoDown'></span>";
					list += data.resultList[i].umf_con_fnm;
					list += "</a>";
					list += "</li>";
				}
				var fileUp = "<input type='hidden' id='wKey' name='wKey' value=''/><input type='file' id='b_file' name='b_file' class='multi' maxlength='5'>";

				$("#uploadFileList").html(list);
				$("#fileCnt").html(data.resultList.length);
				fileCnt = data.resultList.length;
				$("#upload_File").html(fileUp);
			},
			error : function() {
				alert("실패");
			},
			complete : function() {
			}
		});
	}

	function copyWork() {
		opener.callbackPop(CKEDITOR.instances.utw_wrk_dtl.getData());
	}

	function changeBcy(){
		$("#utwTrcKey").val($("#selctYear01").val());
		$("#mngKey").val($("#mng").val());
		$("#usrKey").val($("#usr").val());
		document.reloadForm.submit();
	}
	</script>
</head>
<body>
	<form id="workForm" name="workForm">
		<input type="hidden" id="utw_wrk_key" name="utw_wrk_key" value="<c:out value='${workVO.utw_wrk_key}'/>" />
		<input type="hidden" id="utw_trc_key" name="utw_trc_key" value="<c:out value='${workVO.utw_trc_key}'/>" />
		<input type="hidden" id="utw_wrk_sta" name="utw_wrk_sta" value="<c:out value='${workVO.utw_wrk_sta}'/>">
		<input type="hidden" id="utw_wrk_dtl_h" name="utw_wrk_dtl_h" value="<c:out value='${workVO.utw_wrk_dtl}'/>">
		<input type="hidden" id="trm_cod_nam" name="trm_cod_nam" value="<c:out value='${workVO.trm_cod_nam}'/>">
		<input type="hidden" id="utd_doc_nam" name="utd_doc_nam" value="<c:out value='${workVO.utd_doc_nam}'/>">
		<input type="hidden" id="utd_doc_cnt" name="utd_doc_cnt" value="<c:out value='${workVO.utd_doc_cnt}'/>">

		<input type="hidden" id="utw_wrk_id" name="utw_wrk_id" value="<c:out value='${workVO.utw_wrk_id}'/>">
		<input type="hidden" id="utw_agn_yn" name="utw_agn_yn" value="<c:out value='${workVO.utw_agn_yn}'/>">
		<input type="hidden" id="agn_sta_dtl" name="agn_sta_dtl" value="<c:out value='${workVO.agn_sta_dtl}'/>">
		<input type="hidden" id="utw_prd_cod" name="utw_prd_cod" value="<%=C_SES_MAN_CYL%>" />
		<input type="hidden" id="utw_apr_id" name="utw_apr_id" value='${workVO.utw_apr_id}' />
		<input type="hidden" id="utw_upt_id" name="utw_upt_id" value="<%=C_SES_USER_KEY%>" />
		<input type="hidden" id="uam_app_key" name="uam_app_key" value="<c:out value='${workVO.uam_app_key}'/>" />
		<input type="hidden" id="agn_app_key" name="agn_app_key" value="<c:out value='${workVO.agn_app_key}'/>" />
		<input type="hidden" id="uam_app_gbn" name="uam_app_gbn" value="<c:out value='${workVO.uam_app_gbn}'/>" />
		<input type="hidden" id="uam_app_lev" name="uam_app_lev" value="<c:out value='${workVO.uam_app_lev}'/>" />
		<input type="hidden" id="uam_sta_cod" name="uam_sta_cod" value="<c:out value='${workVO.uam_sta_cod}'/>" />
		<input type="hidden" id="uam_req_id" name="uam_req_id" value='${workVO.utw_wrk_id}' />
		<input type="hidden" id="uam_rgt_id" name="uam_rgt_id" value="<%=C_SES_USER_KEY%>" />
		<input type="hidden" id="uam_cf1_id" name="uam_cf1_id" value="<c:out value='${workVO.uam_cf1_id}'/>" />
		<input type="hidden" id="uam_cf1_mdh" name="uam_cf1_mdh" value="<c:out value='${workVO.uam_cf1_mdh}'/>" />
		<input type="hidden" id="uam_cf1_etc" name="uam_cf1_etc" value="<c:out value='${workVO.uam_cf1_etc}'/>" />
		<input type="hidden" id="uam_cf2_id" name="uam_cf2_id" value="<c:out value='${workVO.uam_cf2_id}'/>" />
		<input type="hidden" id="uam_cf2_mdh" name="uam_cf2_mdh" value="<c:out value='${workVO.uam_cf2_mdh}'/>" />
		<input type="hidden" id="uam_cf2_etc" name="uam_cf2_etc" value="<c:out value='${workVO.uam_cf2_etc}'/>" />
		<input type="hidden" id="uam_upt_id" name="uam_upt_id" value="<%=C_SES_USER_KEY%>" />
		<input type="hidden" id="mode" name="mode" value="<c:out value='${mode}'/>" />
		<input type="hidden" id="ctrKey" name="ctrKey" value="<c:out value='${ctrKey}'/>" />
		<input type="hidden" id="mng" name="mng" value="<c:out value='${mngKey}'/>" />
		<input type="hidden" id="usr" name="usr" value="<c:out value='${usrKey}'/>" />
	</form>
	<form action="/mwork/FM-MWORK_copyWorkpopup.do" name="reloadForm" id="reloadForm">
		<input type="hidden" id="utwWrkKey" name="utwWrkKey">
		<input type="hidden" id="utwTrcKey" name="utwTrcKey">
		<input type="hidden" id="mngKey" name="mngKey">
		<input type="hidden" id="usrKey" name="usrKey">
	</form>
	<form action="" name="fileDown" id="fileDown" method="post">
		<input type="hidden" name="downKey" id="downKey">
		<input type="hidden" name="where" id="where">
	</form>
	<div id="skipnavigation">
		<ul>
			<li><a href="#content-box">본문 바로가기</a></li>
		</ul>
	</div>
	<div id="wrap" class="pop">
		<header>
			<div class="borderBox"></div>
			<h1>업무 상세
<%-- 				<select class="selectBox" id="selctYear01" style="width:150px" onchange="changeBcy()">
					<c:forEach var="bcyList" items="${bcyList}" varStatus="status">
						<option value="${bcyList.utdTrcKey }">${bcyList.mcyDt }</option>
					</c:forEach>
				</select> --%>
			</h1>
		</header>
		<article class="cont" id="content-box">
			<div class="cont_container">
				<div class="contents">
					<div class="title">업무상태 및 담당자</div>
					<div class="talbeArea">
						<table summary="업무상태 및 담당자를 업무수행일, 업무주기, 업무기간, 업무상태, 대무자, 대무 요청상태, 담당자, 결재자 등의 정보로 설명합니다.">
							<colgroup>
								<col width="10%">
								<col width="14%">
								<col width="10%">
								<col width="14%">
								<col width="10%">
								<col width="14%">
								<col width="10%">
								<col width="*%">
							</colgroup>
							<caption>업무상태 및 담당자</caption>
							<tbody>
								<tr>
									<th scope="row">업무<br>수행일</th>
									<td><c:out value="${workVO.utw_wrk_dat}" escapeXml="false" /></td>
									<th scope="row">업무주기</th>
									<td><c:out value="${workVO.trm_cod_nam}" escapeXml="false" /></td>
									<th scope="row">업무기간</th>
									<td><c:out value="${workVO.utw_str_dat}" escapeXml="false" /> ~<br><c:out value="${workVO.utw_end_dat}" escapeXml="false" /></td>
									<th scope="row">업무상태</th>
									<td class="last">
										<c:out value="${workVO.utw_wrk_sta}" escapeXml="false" />
										<c:if test="${workVO.wrk_sta_dtl == '11'}" ><br />(결재진행중)</c:if>
										<c:if test="${workVO.wrk_sta_dtl == '12'}" ><br />(반려)</c:if>
									</td>
								</tr>
								<tr>
									<th scope="row">대무자</th>
									<td>
										<c:if test="${workVO.agn_sta_dtl == '23'}" >-</c:if>
										<c:if test="${workVO.agn_sta_dtl != '23'}" ><c:out value="${workVO.utw_agn_nam}" escapeXml="false" /></c:if>
									</td>
									<th scope="row">대무자<br>요청상태</th>
									<td>
										<c:if test="${workVO.agn_sta_dtl == '21'}" >결재진행중</c:if>
										<c:if test="${workVO.agn_sta_dtl == '22'}" >반려</c:if>
										<c:if test="${workVO.agn_sta_dtl == '23'}" >-</c:if>
										<c:if test="${workVO.agn_sta_dtl == '24'}" >승인</c:if>
										<c:if test="${workVO.agn_sta_dtl == '25'}" >취소</c:if>
									</td>
									<th scope="row">담당자</th>
									<td><c:out value="${workVO.utw_wrk_nam}" escapeXml="false" /></td>
									<th scope="row">결재자</th>
									<td class="last"><c:out value="${workVO.apr_yn_nam}" escapeXml="false" /></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<c:if test="${fn:length(cntrList) != 0}">
				<div class="contents">
					<div class="title">표준항목 상세내용<button type="button" class="btnOpen open">상세 닫기 <span></span></button></div>
					<div class="selectDetail">
					<c:forEach var="list" items="${cntrList}" varStatus="status">
						<ul class="detailArea">
							<li class="floatList">
								<dl>
									<dt>표준명</dt>
									<dd><c:out value='${list.ucmCtrGbn}' escapeXml="false" />[<c:out value="${list.ucmGolNo}" escapeXml="false" />]</dd>
								</dl>
							</li>
							<li class="floatList">
								<dl>
									<dt>통제점검</dt>
									<dd><c:out value="${list.ucm3lvCod}" escapeXml="false" /></dd>
								</dl>
							</li>
							<li>
								<dl>
									<dt>통제항목</dt>
									<dd><c:out value="${list.ucm1lvCod}" escapeXml="false" /> <c:out value="${list.ucm1lvNam}" escapeXml="false" /></dd>
								</dl>
							</li>
							<li>
								<dl>
									<dt>통제목적</dt>
									<dd><c:out value="${list.ucm2lvCod}" escapeXml="false" /> <c:out value="${list.ucm2lvNam}" escapeXml="false" /></dd>
								</dl>
							</li>
							<li>
								<dl>
									<dt>상세내용</dt>
									<dd><c:out value="${fn:replace(list.ucm2lvDtl, LF, '<br />')}" escapeXml="false" /></dd>
								</dl>
							</li>
							<li>
								<dl>
									<dt>점검항목</dt>
									<dd><c:out value="${list.ucm3lvNam}" escapeXml="false" /></dd>
								</dl>
							</li>
							<li>
								<dl>
									<dt>설명</dt>
									<dd><c:out value="${fn:replace(list.ucm3lvDtl, LF, '<br />')}" escapeXml="false" /></dd>
								</dl>
							</li>
							<li>
								<dl>
									<dt>
										관련문서(정책, 지침 등)
									</dt>
									<dd>
										<ul id="<c:out value='${list.ucmCtrKey}'/>" class="listArea"></ul>
									</dd>
								</dl>
							</li>
						</ul>
						</c:forEach>
					</div>
				</div>
				</c:if>
				<c:if test="${not empty workVO.uam_cf1_nam}">
				<div class="contents">
					<div class="title">결재의견</div>
					<div class="talbeArea">
						<table summary="1차 결재의견을 1차 결재일, 1차 결재자, 1차 결재의견 등의 정보로 설명합니다.">
							<colgroup>
								<col width="20%">
								<col width="30%">
								<col width="*%">
							</colgroup>
							<caption>1차 결재의견</caption>
							<thead>
								<tr>
									<th scope="col">1차 결재일</th>
									<th scope="col">1차 결재자</th>
									<th scope="col" class="last">1차 결재의견</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td><c:out value="${workVO.uam_cf1_mdh}" /></td>
									<td><c:out value="${workVO.uam_cf1_nam}" /></td>
									<td class="last"><c:out value="${workVO.uam_cf1_etc}" /></td>
								</tr>
							</tbody>
						</table>
						<c:if test="${not empty workVO.uam_cf2_nam}">
						<table summary="2차 결재의견을 2차 결재일, 2차 결재자, 2차 결재의견 등의 정보로 설명합니다.">
							<colgroup>
								<col width="20%">
								<col width="30%">
								<col width="*%">
							</colgroup>
							<caption>2차 결재의견</caption>
							<thead>
								<tr>
									<th scope="col">2차 결재일</th>
									<th scope="col">2차 결재자</th>
									<th scope="col" class="last">2차 결재의견</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td><c:out value="${workVO.uam_cf2_mdh}" /></td>
									<td><c:out value="${workVO.uam_cf2_nam}" /></td>
									<td class="last"><c:out value="${workVO.uam_cf2_etc}" /></td>
								</tr>
							</tbody>
						</table>
						</c:if>
					</div>
				</div>
				</c:if>
				<div class="contents">
					<div class="title">업무수행내용</div>
					<div class="talbeArea">
						<table summary="업무기간을 확인 할 수 있습니다.">
							<colgroup>
								<col width="20%">
								<col width="*%">
							</colgroup>
							<caption>업무기간</caption>
							<tbody>
								<tr>
									<th scope="row">업무기간</th>
									<td class="last fontLeft">
										<input id="utw_str_dat" class="inputText cal" type="text" title="시작 날짜 선택" placeholder="0000.00.00" value="<c:out value="${workVO.utw_str_dat}"/>" readonly="readonly">
										<!--button>
											<span class="icoCal">달력</span>
										</button--> ~
										<input id="utw_end_dat" class="inputText cal" type="text" title="마감 날짜 선택" placeholder="0000.00.00" value="<c:out value="${workVO.utw_end_dat}"/>" readonly="readonly">
										<!--button>
											<span class="icoCal">달력</span>
										</button-->
									</td>
								</tr>
							</tbody>
						</table>
						<table summary="수행내용을 작성할 수 있습니다.">
							<caption>수행내용 작성</caption>
							<thead>
								<tr>
									<th scope="col" class="last">수행내용 작성</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td class="last noPadding">
										<textarea class="txtAreaType03" id="utw_wrk_dtl" readonly="readonly" name="utw_wrk_dtl">${workVO.utw_wrk_dtl}</textarea>
										<ce:replace replace="utw_wrk_dtl" basePath="${pageContext.request.contextPath}/editor/ckeditor"></ce:replace>
									</td>
								</tr>
							</tbody>
						</table>

						<div class="uplidFileList margin10">
							<p class="titleArea">
								관련문서(정책, 지침 등) - 총 <strong id="fileCnt"></strong>건
							</p>
 							<ul class="listArea width100" id="uploadFileList">
							</ul>
						</div>

					</div>
				</div>
			</div>
				<div class="centerBtnArea bottomBtnArea">
					<button type="button" class="popClose close"><span class="icoClose">닫기</span></button>
					<ul class="btnList">
					<li><button type="button" class="btnStrong" onclick="copyWork();"><span class="icoAdd"></span>내용복사</button></li>
				</ul>
			</div>
		</article>
		<form id="brdViewForm" name="brdViewForm"
			action="/mwork/FM-MWORK_brd_popup.do" method="post"
			target="getbrdView">
			<input type="hidden" name="brdViewKey" id="brdViewKey" value="">
		</form>
	</div>
</body>
</html>