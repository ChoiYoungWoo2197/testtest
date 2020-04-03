<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
	<script type="text/javascript">
	var fileCnt = 0;
	$(document).ready(function() {

	});

	// uwo_trc_wrk의 utw_wrk_sta 변경 및 uwo_app_mtr의 uam_sta_cod 변경
	// 버튼명 : 승인, 반려, 결재상신취소
	function saveAppWork(gubun){
		var appLev = $("#uam_app_lev").val();
		var appStaCod = $("#uam_sta_cod").val();

		/* 2018-05-11 s, 2차 결재로 변경
		// 승인
		if(gubun == "3"){
			$("#uam_sta_cod").val("19");
			$("#uam_cf1_etc").val($("#comFirmEtc").val());
			$("#utw_apr_id").val("<%=C_SES_USER_KEY%>");
			$("#utw_wrk_sta").val("10");
			msg = "결재를 승인 하시겠습니까?";
		}
		// 반려
		else if(gubun == "4"){
			$("#uam_sta_cod").val("12");
			$("#uam_cf1_etc").val($("#rejectEtc").val());
			$("#utw_wrk_sta").val("10");
			$("#utw_apr_id").val("<%=C_SES_USER_KEY%>");
			msg = "결재를 반려 하시겠습니까?";
		}
		else if (gubun == "5") {
			$("#uam_cf1_id").val("<%=C_SES_USER_KEY%>");
			$("#uam_cf2_mdh").val("");
			$("#uam_cf2_etc").val("");
			msg = "결재상신을 취소 하시겠습니까?";
		}
		*/

		// 2차결재 : 1차
		if(appStaCod == "11"){
			$("#uam_cf1_id").val("<%=C_SES_USER_KEY%>");
			$("#uam_cf2_mdh").val("");
			$("#uam_cf2_etc").val("");

			// 승인
			if(gubun == "3"){
				$("#uam_sta_cod").val("21");
				$("#uam_cf1_etc").val($("#comFirmEtc").val());
				$("#utw_wrk_sta").val("10");
				$("#utw_apr_id").val("");
			}
			// 반려
			else if(gubun == "4"){
				$("#uam_sta_cod").val("12");
				$("#uam_cf1_etc").val($("#rejectEtc").val());
				$("#utw_wrk_sta").val("10");
				$("#utw_apr_id").val("<%=C_SES_USER_KEY%>");
			}
		}
		// 2차결재 : 2차
		else if(appStaCod == "21"){
			$("#uam_cf2_id").val("<%=C_SES_USER_KEY%>");
			$("#uam_cf2_mdh").val("2");

			// 승인
			if(gubun == "3"){
				$("#uam_sta_cod").val("29");
				$("#uam_cf2_etc").val($("#comFirmEtc").val());
				$("#utw_wrk_sta").val("90");
				$("#utw_apr_id").val("<%=C_SES_USER_KEY%>");
			}
			// 반려
			else if(gubun == "4"){
				$("#uam_sta_cod").val("22");
				$("#uam_cf2_etc").val($("#rejectEtc").val());
				$("#utw_wrk_sta").val("10");
				$("#utw_apr_id").val("<%=C_SES_USER_KEY%>");
			}
		}

		if (gubun == "3") {
			msg = "결재를 승인 하시겠습니까?";
		} else if (gubun == "4") {
			msg = "결재를 반려 하시겠습니까?";
		} else if (gubun == "5") {
			msg = "결재상신을 취소 하시겠습니까?";
		}

		if (confirm(msg)) {
			document.workForm.action = "${pageContext.request.contextPath}/mwork/FM-MWORK_work_stateUpdate.do";
			document.workForm.submit();

			try {
				opener.location.reload(true);
			}
			catch (e) {}

			//$(":input:checkbox[name=cancelWorkApp]").attr("checked", false);
			//$(":input:checkbox[name=cancelAgnApp]").attr("checked", false);
		}
	}

	// 업무삭제
	function delWork() {
		if (confirm("해당 업무를 삭제하시겠습니까?")) {
			document.workForm.action = "${pageContext.request.contextPath}/mwork/FM-MWORK_work_del.do";
			document.workForm.submit();
			opener.parent.location.reload();
			window.close();
		}
	}

	function workPopup(wrkKey, trcKey) {
		var win = window.open("","workPopupSub","width=730, height=550, menubar=no, location=no, status=no,scrollbars=yes");
		$("#utwWrkKey").val(wrkKey);
		$("#utwTrcKey").val(trcKey);
		$("#workPopupForm").submit();
		win.focus();
	}
	</script>
</head>
<body>
	<form id="workPopupForm" name="workPopupForm" action="FM-MWORK_popup.do" method="post" target="workPopupSub">
		<input type="hidden" id="utwWrkKey" name="utwWrkKey" />
		<input type="hidden" id="utwTrcKey" name="utwTrcKey" />
	</form>
	<form id="workForm" name="workForm" method="post" enctype="multipart/form-data">
		<input type="hidden" id="utw_wrk_key" name="utw_wrk_key" value="<c:out value='${workVO.utw_wrk_key}'/>" />
		<input type="hidden" id="utw_trc_key" name="utw_trc_key" value="<c:out value='${workVO.utw_trc_key}'/>" />
		<input type="hidden" id="utw_wrk_sta" name="utw_wrk_sta" value="<c:out value='${workVO.utw_wrk_sta}'/>">
		<input type="hidden" id="uam_sta_cod" name="uam_sta_cod" value="<c:out value='${workVO.uam_sta_cod}'/>" />
		<input type="hidden" id="utw_wrk_id" name="utw_wrk_id" value="<c:out value='${workVO.utw_wrk_id}'/>">
		<input type="hidden" id="uam_app_key" name="uam_app_key" value="<c:out value='${workVO.uam_app_key}'/>" />
		<input type="hidden" id="agn_app_key" name="agn_app_key" value="<c:out value='${workVO.agn_app_key}'/>" />
		<input type="hidden" id="uam_cf1_id" name="uam_cf1_id" value="<c:out value='${workVO.uam_cf1_id}'/>" />
		<input type="hidden" id="uam_cf1_mdh" name="uam_cf1_mdh" value="<c:out value='${workVO.uam_cf1_mdh}'/>" />
		<input type="hidden" id="uam_cf1_etc" name="uam_cf1_etc" value="<c:out value='${workVO.uam_cf1_etc}'/>" />
		<input type="hidden" id="uam_cf2_id" name="uam_cf2_id" value="<c:out value='${workVO.uam_cf2_id}'/>" />
		<input type="hidden" id="uam_cf2_mdh" name="uam_cf2_mdh" value="<c:out value='${workVO.uam_cf2_mdh}'/>" />
		<input type="hidden" id="uam_cf2_etc" name="uam_cf2_etc" value="<c:out value='${workVO.uam_cf2_etc}'/>" />
		<input type="hidden" id="addList" name="addList" value="<c:out value='${addList}'/>" />
		<input type="hidden" id="wrkId" name="wrkId" value="<c:out value='${wrkId}'/>" />
	</form>

	<div id="skipnavigation">
		<ul>
			<li><a href="#content-box">본문 바로가기</a></li>
		</ul>
	</div>
	<div id="wrap" class="pop">
		<header>
			<div class="borderBox"></div>
			<h1>이관 요청 상세</h1>
		</header>
		<article class="cont" id="content-box">
			<div class="cont_container">
				<div class="contents">
					<div class="title">업무이관 요청자</div>
					<div class="talbeArea">
						<table summary="업무상태 및 담당자를 업무수행일, 업무주기, 업무기간, 업무상태, 대무자, 대무 요청상태, 담당자, 결재자 등의 정보로 설명합니다.">
						<colgroup>
							<col width="14%">
							<col width="18%">
							<col width="14%">
							<col width="20%">
							<col width="14%">
							<col width="*%">
						</colgroup>
							<caption>업무상태 및 담당자</caption>
							<tbody>
								<tr>
									<th scope="row">서비스</th>
									<td><c:out value="${workVO.utw_svc_nam}" escapeXml="false" /></td>
									<th scope="row">부서</th>
									<td><c:out value="${workVO.utw_dep_nam}" escapeXml="false" /></td>
									<th scope="row">요청자</th>
									<td class="last"><c:out value="${workVO.uam_req_nam}" escapeXml="false" /></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>

				<div class="contents">
					<div class="title">이관 요청 업무</div>
					<div class="talbeArea">
					<table summary="기존업무 확인">
						<colgroup>
							 <col width="10%">
                             <col width="30%">
                             <col width="10%">
                             <col width="10%">
                             <col width="10%">
                             <col width="15%">
                             <col width="15%">
						</colgroup>
						<thead>
							<tr>
								 <th scope="col">업무코드</th>
                                 <th scope="col">업무명</th>
                                 <th scope="col">업무주기</th>
                                 <th scope="col">서비스</th>
                                 <th scope="col">대무자</th>
                                 <th scope="col">업무시작일</th>
                                 <th scope="col" class="last">업무마감일</th>
							</tr>
						</thead>
						<tbody>
						<c:forEach var="result" items="${wrkList}" varStatus="status">
							<tr>
								<td><c:out value='${result.utwWrkKey}'/></td>
								<td><a href="javascript:workPopup('<c:out value='${result.utwWrkKey}'/>','<c:out value='${result.utwTrcKey}'/>')"><c:out value='${result.utdDocNam}'/></a></td>
								<td><c:out value='${result.uccTrmNam}'/></td>
								<td><c:out value='${result.uccSvcNam}'/></td>
								<td><c:out value='${result.uumUsrNam}'/></td>
								<td><c:out value='${result.utwStrDat}'/></td>
								<td class="last"><c:out value='${result.utwEndDat}'/></td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
					</div>
				</div>

				<div class="contents">
					<div class="title">결재의견</div>
					<div class="talbeArea">
						<table summary="1차 결재의견을 1차 결재일, 1차 결재자, 1차 결재의견 등의 정보로 설명합니다.">
							<colgroup>
								<col width="20%">
								<col width="30%">
								<col width="*%">
							</colgroup>
							<caption>결재의견</caption>
							<thead>
								<tr>
									<th scope="col">결재일</th>
									<th scope="col">결재자</th>
									<th scope="col" class="last">결재의견</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td><c:out value="${workVO.agn_cf1_mdh}" /></td>
									<td><c:out value="${workVO.agn_cf1_nam}" /></td>
									<td class="last"><c:out value="${workVO.agn_cf1_etc}" /></td>
								</tr>
							</tbody>
						</table>
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
					</div>
				</div>
				<div class="contents">

					<div class="bottomBtnArea">
						<ul class="btnList">
						<!-- 업무담당자의 지정된 결재자에게만 승인, 반려 버튼 보여줌 -->
						<c:if test="${workVO.agn_sta_dtl == '21'}">
							<c:if test="${workVO.utw_wrk_id eq usrKey}">
							<li>
							<button type="button" onclick="saveAppWork('5')">
								<span class="icoReDo"></span>결재상신취소
							</button>
							</li>
							</c:if>
							<c:if test="${authKey != 'P' or workVO.agn_cf1_id eq usrKey or workVO.agn_cf2_id eq usrKey}">
							<li>
							<button id="cancleAppDialog" class="openPop" type="button">
								<span class="icoSave"></span>승인
							</button>
							<div class="layerPopType02">
								<div class="popTitle">
									<p>의견 등록</p>
								</div>
								<div class="layerContents">
									<label for="comFirmEtc">의견을 등록하세요.</label>
									<p>
										<textarea id="comFirmEtc" class="txtAreaType02"></textarea>
									</p>
									<div class="centerBtnArea">
										<button onclick="saveAppWork('3')">확인</button>
									</div>
								</div>
								<button class="popClose" type="button">
									<span class="icoClose">닫기</span>
								</button>
							</div>
							<div class="popBG"></div>
							</li>
							<li>
							<button id="cancleAppDialog" class="openPop" type="button">
								<span class="icoReDo"></span>반려
							</button>
							<div class="layerPopType02">
								<div class="popTitle">
									<p>반려 사유 등록</p>
								</div>
								<div class="layerContents">
									<label for="rejectEtc">반려 사유를 등록하세요.</label>
									<p>
										<textarea id="rejectEtc" class="txtAreaType02"></textarea>
									</p>
									<div class="centerBtnArea">
										<button onclick="saveAppWork('4')">확인</button>
									</div>
								</div>
								<button class="popClose" type="button">
									<span class="icoClose">닫기</span>
								</button>
							</div>
							<div class="popBG"></div>
							</li>
						</c:if>
					</c:if>
						</ul>
					</div>
				</div>
			</div>
			<div class="centerBtnArea closeArea">
				<button class="btnStrong close" type="button">닫기</button>
			</div>
		</article>
	</div>
</body>
</html>