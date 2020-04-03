<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>

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
<script type="text/javascript" src="/common/js/jquery.form.js"></script>
<script type="text/javascript" src="/common/js/jquery.blockUI.js"></script>
<script type="text/javascript" src="/common/js/ismsUI.js"></script>
<script type="text/javascript" src="/common/js/common.js"></script>
<script type="text/javascript" src="/common/js/jquery-ui.js"></script>
<script  type="text/javascript">
$(document).ready(function() {
	// 로딩바 적용
	loadingBarSet();

	$("#ursReqMdh").datepicker({
		changeMonth: true,
        changeYear: true,
        showButtonPanel: true,
		minDate : "today"
	});
	$( "#ursReqMdhBtn").click(function() {
     	$("#ursReqMdh").focus();
	});

	$("#btnSubmit").click(function() {
		if (!$("#ursReqMdh").val()) {
			alert("처리완료 요청일을 입력해 주세요.");
			return false;
		}
		if (confirm("담당자에게 대책수립을 할당 하시겠습니까?")) {
			$.post("${pageContext.request.contextPath}/riskm/FM-RISKM003_batchJob.do",
				{ uagGrpCod: $("#uagGrpCod").val(), doa: $("#doa").val(), ursReqMdh: $("#ursReqMdh").val() }, function(data) {
					if (data.result == "true") {
						alert("담당자에게 대책수립이 할당 되었습니다.");
						try {
							opener.reloadGetRskDetailList($("#uagSvrCod").val());
						}
						catch (ex) {}
						finally {
							self.close();
						}
					} else {
						alert("대책수립 일괄할당 처리가 실패 했습니다.");
					}
			}, "json")
			.fail(function() {
   				 alert("처리 중 에러가 발생했습니다.");
  			});
		}
	});

});
</script>
</head>
<body>
	<form id="writeForm" name="writeForm" method="post">
	<input type="hidden" id="uagGrpCod" name="uagGrpCod" value="${uagGrpCod}"/>
	<div id="skipnavigation">
	    <ul>
	        <li><a href="#content-box">본문 바로가기</a></li>
	    </ul>
	</div>
	<div id="wrap" class="pop">
		<header>
			<div class="borderBox"></div>
			<h1>기술적 위험조치 - 대책수립 일괄 할당</h1>
		</header>
		<article class="cont" id="content-box">
			<div class="cont_container">
				<div class="contents">
					<div class="talbeArea">
		        		<table summary="당자 대책수립 일괄 할당">
		            		<colgroup>
								<col width="40%" />
								<col width="*" />
							</colgroup>
							<caption>담당자 대책수립 일괄 할당</caption>
							<tbody>
								<tr>
									<th scope="row" class="listTitle"><label for="uagGrpCod">그룹명</label></th>
									<td class="fontLeft last">
										<input type="text" class="inputText" value="${uagGrpNam}" readonly="readonly"/>
									</td>
								</tr>
								<tr>
									<th scope="row" class="listTitle"><label for="urgSvcCod">위험도</label></th>
									<td class="fontLeft last">
										<input type="text" id="doa" name="doa" class="inputText wdShort" value="${doa}" readonly="readonly"/>
									</td>
								</tr>
								<tr>
									<th scope="row"><label for="ursReqMdh">처리완료요청일</label></th>
									<td class="fontLeft last">
										<input id="ursReqMdh" name="ursReqMdh" class="inputText cal" style="width:80px"  maxlength="15" />
										<button id="ursReqMdhBtn" type="button"><span class="icoCal"><em>달력</em></span></button>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
	       		</div>
			</div>
			<div class="bottomBtnArea">

            </div>

			<div class="centerBtnArea bottomBtnArea">
				<button type="button" class="popClose close"><span class="icoClose">닫기</span></button>
				<ul class="btnList">
					<li><button type="button" class="btnStrong" id="btnSubmit"><span class="icoSave"></span>대책 담당자 일괄 할당</button></li>
                </ul>
			</div>
		</article>
	</div>
</form>
</body>
</html>