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

	$("#assCatS").val($("#assCat").val());
	$("#assGbnS").val($("#assGbn").val());
	$("#assGbnS").val($("#assGbn").val());
	$("#serviceS").val($("#service").val());

	document.excelDownloadForm.action = "${pageContext.request.contextPath}/excel/FM-RISKM006_LIST_DOWN.do";
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
			<h1>취약점 진단결과 다운로드</h1>
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
							<caption>취약점 진단결과 다운로드</caption>
							<tbody>
								<tr>
									<th class="listTitle" scope="row"><label for="down">서비스</label></th>
		   							<td class="fontLeft last">
		                    			<sb:select name="service" type="S"/>
		   							</td>
		   						</tr>
								<tr>
									<th class="listTitle" scope="row"><label for="down">자산분류</label></th>
		   							<td class="fontLeft last">
		   								<select id="assCat" name="assCat" class="selectBox"title="항목 선택">
											<c:forEach var="assCat" items="${assCatList}" varStatus="status">
												<option value="${assCat.key}"><c:out value="${assCat.name}" /></option>
											</c:forEach>
			                            </select>
		   							</td>
		   						</tr>
		   						<tr>
									<th class="listTitle" scope="row"><label for="down">자산유형</label></th>
		   							<td class="fontLeft last">
		   								<select id="assGbn" name="assGbn" class="selectBox"title="항목 선택">
											<option value="">전체</option>
											<c:forEach var="assGbn" items="${assGbnList}" varStatus="status">
												<option value="${assGbn.code}"><c:out value="${assGbn.name}" /></option>
											</c:forEach>
			                            </select>
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
					<li><button type="button" class="btnStrong" onclick="excelDownload();"><span class="icoDown"></span>진단결과 다운로드</button>
				</ul>
			</div>
		</article>
	</div>
</form>
<form id="excelDownloadForm" name="excelDownloadForm" method="post">
	<input type="hidden" id="serviceS" 	name="serviceS" value="">
	<input type="hidden" id="dept" 		name="dept" 	value="${paramMap.excelDepCod}">
	<input type="hidden" id="downType"	name="downType" value="${paramMap.downType}">
	<input type="hidden" id="assCatS" 	name="assCatS" 	value="">
	<input type="hidden" id="assGbnS" 	name="assGbnS" 	value="">

</form>
</body>
</html>