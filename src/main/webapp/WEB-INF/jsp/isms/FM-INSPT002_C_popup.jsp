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
<link rel="stylesheet" type="text/css" href="/common/css/pop.css"/>
<link rel="stylesheet" type="text/css" href="/common/css/sub.css"/>
<link rel="stylesheet" type="text/css" href="/common/css/jquery-ui.css"/>
<!--[if IE]> <script src="/common/js/html5.js"></script> <![endif]-->
<script type="text/javascript" src="/common/js/jquery.js"></script>
<script type="text/javascript" src="/common/js/ismsUI.js"></script>
<script type="text/javascript" src="/common/js/jquery.form.js"></script>
<script type="text/javascript" src="/common/js/common.js"></script>
<script  type="text/javascript">

function reportDown(){
	var $frm = $('form[name=popForm]');
	var state = true;
	if($('input[name=sTitle]').val() == ''){
		alert('신청기관을 입력하세요.');
		$('input[name=sTitle]').focus();
		state = false;
		return false;
	}
	if($('input[name=mTitle]').val() == ''){
		alert('대표자를 입력하세요.');
		$('input[name=mTitle]').focus();
		state = false;
		return false;
	}
	if($('input[name=bcyText]').val() == ''){
		alert('심사범위를 입력하세요.');
		$('input[name=bcyText]').focus();
		state = false;
		return false;
	}
	
	if(state){
		window.open("","reportDown","width=1024, height=720, menubar=no, location=no, status=no,scrollbars=yes");
		$frm.attr('target','reportDown').attr('action','${pageContext.request.contextPath}/report/FM-INSPT002.do').submit();
		$frm.attr('target','').attr('action','');
	}
}
</script>
</head>
<body>
<form id="popForm" name="popForm" method="post">
	<input type="hidden" name="standard" value="${paramMap.standard }" />
	<input type="hidden" name="ufmCtrDes" value="${paramMap.ufmCtrDes }" />
	<input type="hidden" name="ufmRstSta" value="${paramMap.ufmRstSta }" />
	<input type="hidden" name="dept" value="${paramMap.dept }" />
	<input type="hidden" name="worker" value="${paramMap.worker }" />
	<input type="hidden" name="service" value="${paramMap.service }" />
	<input type="hidden" name="ufmFltNam" value="${paramMap.ufmFltNam }" />
	<div id="skipnavigation">
	    <ul>
	        <li><a href="#content-box">본문 바로가기</a></li>
	    </ul>
	</div>
	<div id="wrap" class="pop">
		<header>
			<div class="borderBox"></div>
			<h1>완료 결함관리</h1>
		</header>
		<article class="cont" id="content-box">
			<div class="cont_container">
				<div class="contents">
					<div class="title">인증심사 신청내역</div>
					<div class="talbeArea">
					<table>
						<colgroup>
							<col width="20%">
							<col width="*">
						</colgroup>					
						<tbody>
							<tr>
								<th scope="row" class="listTitle">* 신청기관</th>
								<td class="fontLeft">
									<input type="text" class="inputText" name="sTitle" maxlength="255"/>
								</td>
							</tr>
							<tr>
								<th scope="row" class="listTitle">* 대 표 자</th>
								<td class="fontLeft">
									<input type="text" class="inputText" name="mTitle" maxlength="255"/>
								</td>
							</tr>
							<tr>
								<th scope="row" class="listTitle">* 심사범위</th>
								<td class="fontLeft">
									<input type="text" class="inputText" name="bcyText" maxlength="255"/>
								</td>
							</tr>
						</tbody>
					</table>
					</div>
					<div class="title">결함 내역</div>
					<div class="talbeArea">
						<div class="topBtnArea">
							<ul class="btnList">
								<c:if test="${fn:length(result) > 0}">
								<li><button type="button" onclick="reportDown();"><span class="icoDown"></span>완료 PDF다운로드</button></li>
								</c:if>
							</ul>
						</div>
						<table>  
							<colgroup>
								<col width="20%">
								<col width="*">
								<col width="20%">
							</colgroup>
                               <caption>결함 내역</caption> 
							<thead>
								<tr>
									<th scope="row">컴플라이언스</th>
									<th scope="row">결함명</th>
									<th scope="row">결함등급</th>
								</tr>
							</thead>
							<tbody>
							<c:forEach var="result" items="${result}" varStatus="status">
							<tr>
								<td><c:out value="${result.ufmCtrNam }" /></td>
								<td><c:out value="${result.ufmFltNam }" /></td>
								<td><c:out value="${result.ufmFltLvl }" /></td>
							</tr>
							</c:forEach>
							<c:if test="${fn:length(result) == 0}">
							<tr id="noneData" class="last"><td class="last noDataList" colspan="4">결함 내역이 없습니다.</td></tr>
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