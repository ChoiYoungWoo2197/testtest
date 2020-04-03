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
<!--[if IE]> <script src="/common/js/html5.js"></script> <![endif]-->
<script type="text/javascript" src="/common/js/jquery.js"></script>
<script type="text/javascript" src="/common/js/ismsUI.js"></script>
<script type="text/javascript" src="/common/js/jquery.form.js"></script>
<script type="text/javascript" src="/common/js/common.js"></script>
<script  type="text/javascript">
	function goBcyPage() {
		document.popForm.action = "FM-RISKM003_grp_popup.do";
		document.popForm.submit();
	}
</script>
</head>
<body>
<form id="popForm" name="popForm" method="post">
	<input type="hidden" id="grpPopKey" name="grpPopKey" value="${info.uagGrpKey}">
	<div id="skipnavigation">
	    <ul>
	        <li><a href="#content-box">본문 바로가기</a></li>
	    </ul>
	</div>
	<div id="wrap" class="pop">
		<header>
			<div class="borderBox"></div>
			<h1>위험관리내역
				<select id="bcyCod" name="bcyCod" style="width:170px" class="selectBox" onchange="goBcyPage();">
				<c:forEach var="bcy" items="${bcyList}" varStatus="status">
					<option value="${bcy.code}">${bcy.name}</option>	
				</c:forEach>
				</select>
			</h1>
		</header>
		<article class="cont" id="content-box">
			<div class="cont_container">
				<div class="contents">
					<div class="talbeArea">
						<table summary="위험관리내역"> 
							<colgroup>
								<col width="20%">
								<col width="30%">
								<col width="20%">
								<col width="30%">
							</colgroup>
							<caption>위험관리내역</caption> 
							<tbody>
								<tr>
									<th scope="row" class="listTitle"><label for="uagSvcNam">서비스</label></th>
									<td class="fontLeft"><c:out value="${info.uagSvcNam}"/></td>
									<th scope="row" class="listTitle"><label for="uagDepNam">부서</label></th>
									<td class="fontLeft last"><c:out value="${info.uagDepNam}"/></td>
								</tr>
								<tr>
									<th scope="row" class="listTitle"><label for="urgSvcCod">자산유형</label></th>
									<td class="fontLeft"><c:out value="${info.uagCatNam}"/></td>
									<th scope="row" class="listTitle"><label for="urgDepCod">소속자산수</label></th>
									<td class="fontLeft last"><c:out value="${info.uagAssCnt}"/></td>
								</tr>
								<tr>
									<th scope="row" class="listTitle"><label for="urgSvcCod">자산그룹명</label></th>
									<td class="fontLeft"><c:out value="${info.uagGrpNam}"/></td>
									<th scope="row" class="listTitle"><label for="urgDepCod">자산그룹코드</label></th>
									<td class="fontLeft last"><c:out value="${info.uagGrpCod}"/></td>
								</tr>
								<tr>
									<th scope="row" class="listTitle"><label for="urgSvcCod">그룹등급</label></th>
									<td class="fontLeft"><c:out value="${info.grpLvlTxt}"/></td>
									<th scope="row"><label for="viewUsrId">담당자</label></th>
									<td class="fontLeft last"><c:out value="${info.uagMngNam}"/></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="contents">
					<div class="title">위험관리내역</div>
					<div class="talbeArea hiddenTable">
						<table style="width:1500px">  
							<colgroup>
								<col width="6%">
								<col width="6%">
								<col width="6%">
								<col width="9%">
								<col width="6%">
								<col width="6%">
								<col width="6%">
								<col width="6%">
								<col width="15%">
								<col width="6%">
								<col width="15%">
								<col width="6%">
							</colgroup>
                            <caption>위험관리내역</caption> 
							<thead>
								<tr>
									<th scope="row">대분류</th>
									<th scope="row">중분류</th>
									<th scope="row">우려사항코드</th>
									<th scope="row">우려사항명</th>
									<th scope="row">발생빈도</th>
									<th scope="row">위험강도</th>
									<th scope="row">진단결과</th>
									<th scope="row">위험처리</th>
									<th scope="row">보호대책</th>
									<th scope="row">대책승인</th>
									<th scope="row">조치결과</th>
									<th scope="row" class="last">결과승인</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="rsk" items="${rskList}" varStatus="status">
								<tr>
									<td><c:out value="${rsk.usoFirCat}"/></td>
									<td><c:out value="${rsk.usoSecCat}"/></td>
									<td><c:out value="${rsk.usoCocCod}"/></td>
									<td><textarea class="txtAreaType01">${rsk.usoCocNam}</textarea></td>
									<td><c:out value="${rsk.usoFrqTxt}"/></td>
									<td><c:out value="${rsk.usoCocTxt}"/></td>
									<td><c:out value="${rsk.urgRskChk}"/></td>
									<td><c:out value="${rsk.urgRskPrc}"/></td>
									<td><textarea class="txtAreaType02">${rsk.urgMesDes}</textarea></td>
									<td><c:out value="${rsk.urgMesYn}"/></td>
									<td><textarea class="txtAreaType02">${rsk.urgRstDes}</textarea></td>
									<td><c:out value="${rsk.urgRstYn}"/></td>
									

									
								</tr>
								</c:forEach>
								<c:if test="${fn:length(rskList) == 0}">
									<tr class="last">
										<td class="last noDataList" colspan='12'>
											관리내역이 없습니다. 
										</td>
									</tr>
								</c:if>
							</tbody>
						</table>
					</div>
				</div>
			</div>
	       <div class="centerBtnArea closeArea">
               <button class="btnStrong close">닫기</button>
           </div>
	    </article>
	</div>
</form>
</body>
</html>