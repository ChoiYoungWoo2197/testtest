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
<!--[if IE]> <script src="/common/js/html5.js"></script> <![endif]-->
<script type="text/javascript" src="/common/js/jquery.js"></script>
<script type="text/javascript" src="/common/js/jquery.form.js"></script>
<script type="text/javascript" src="/common/js/jquery.blockUI.js"></script>
<script type="text/javascript" src="/common/js/ismsUI.js"></script>
<script type="text/javascript" src="/common/js/common.js"></script>
<script  type="text/javascript">
$(document).ready(function() {
	// 로딩바 적용
	loadingBarSet();
});

function excelDownload(){
	var strAction = "";
	$("#service").val($("#service_cd").val());

	document.excelDownloadForm.action = "${pageContext.request.contextPath}/excel/FM-RISKM009_LIST_DOWN.do";
   	document.excelDownloadForm.submit();
}
</script>
</head>
<body>
	<form id="excelUploadForm" name="excelUploadForm" method="post" enctype="multipart/form-data">
	<div id="skipnavigation">
	    <ul>
	        <li><a href="#content-box">본문 바로가기</a></li>
	    </ul>
	</div>
	<div id="wrap" class="pop">
		<header>
			<div class="borderBox"></div>
			<h1>부서별 진단결과 다운로드</h1>
		</header>
		<article class="cont" id="content-box">
			<div class="cont_container">
				<div class="contents">
					<div class="talbeArea">
		        		<table summary="취약점 진단결과 다운로드">
		            		<colgroup>
								<col width="40%" />
								<col width="*" />
							</colgroup>
							<caption>부서별 진단결과 다운로드</caption>
							<tbody>
								<tr>
									<th class="listTitle" scope="row"><label for="down">서비스</label></th>
		   							<td class="fontLeft last">
		   								<sb:select name="service_cd" type="S"/>
		   							</td>
		   						</tr>
							</tbody>
						</table>
					</div>
	       		</div>
			</div>
			<div class="centerBtnArea bottomBtnArea">
				<button type="button" class="popClose close"><span class="icoClose">닫기</span></button>
				<ul class="btnList">
					<li><button type="button" class="btnStrong" onclick="excelDownload();"><span class="icoDown"></span>진단결과 다운로드</button></li>
				</ul>
			</div>
		</article>
	</div>
</form>
<form id="excelDownloadForm" name="excelDownloadForm" method="post">
	<input type="hidden" id="downType"	name="downType" value="${paramMap.downType}">
	<input type="hidden" id="service" 	name="service" 	value="">
</form>
</body>
</html>