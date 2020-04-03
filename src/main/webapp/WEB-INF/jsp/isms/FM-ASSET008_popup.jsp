<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
function fn_save(){
	if($("#uacCatNam").val() == ''){
		alert("자산유형명을 입력하세요.");
		$("#uacCatNam").focus();
		return;
	}

	var uacCatKey = "${info.uacCatKey}";
	if(uacCatKey == ''){
		url = "${pageContext.request.contextPath}/asset/FM-ASSET008_W.do";
	} else {
		url = "${pageContext.request.contextPath}/asset/FM-ASSET008_U.do";
	}

	$.ajax({
		url			: url,
		type		: "post",
		data		: $("#uacForm").serialize(),
		dataType	: "json",
		success	: function(data){
			if(data.result == "S") {
				alert("저장되었습니다.");
				opener.location.reload();
				window.close();
			}
		},
		error : function(){
			alert('error');
		}
	});
}
</script>
</head>
<body>
<form id=uacForm name="uacForm" method="post">
	<input type="hidden" name="uacCatKey" value="${info.uacCatKey}">
	<div id="skipnavigation">
	    <ul>
	        <li><a href="#content-box">본문 바로가기</a></li>
	    </ul>
	</div>
	<div id="wrap" class="pop">
		<header>
			<div class="borderBox"></div>
			<h1>자산코드세부내용</h1>
		</header>
		<article class="cont" id="content-box">
			<div class="cont_container">
				<div class="contents">
					<div class="talbeArea">
						<table summary="자산코드세부내용을 자산유형명, 사용여부, 자산유형 설명, 가변컬럼 이름, Key값 정의 등을 확인/수정 할 수 있습니다.">
							<colgroup>
								<col width="20%">
								<col width="30%">
								<col width="20%">
								<col width="30%">
							</colgroup>
							<caption>자산코드세부내용</caption>
							<tbody>
								<tr>
									<th scope="row"><label for="uacCatNam">자산유형명</label></th>
									<td class="fontLeft">
										<input type="text" id="uacCatNam" name="uacCatNam" class="inputText" maxlength="25" value="<c:out value='${info.uacCatNam}'/>">
									</td>
									<th scope="row">자산유형코드</th>
									<td class="fontLeft last">
										<input type="text" id="uacCatCod" name="uacCatCod" class="inputText" maxlength="25" value="<c:out value='${info.uacCatCod}'/>">
									</td>
								</tr>
								<tr>
									<th scope="row"><label for="txtArea01">자산유형 설명</label></th>
									<td class="fontLeft last" colspan="3">
										<textarea id="uacCatDes" name="uacCatDes" maxlength="250" class="txtAreaType04">${info.uacCatDes}</textarea>
									</td>
								</tr>
								<tr>
									<th scope="row">가변컬럼 이름</th>
									<td class="fontLeft last" colspan="3">
										<ul class="variableItems">
											<li><label for="txt02">가변컬럼 01</label><input id="uacValCl0" name="uacValCl0" type="text" class="inputText" maxlength="25" value="<c:out value='${info.uacValCl0}'/>"></li>
											<li><label for="txt03">가변컬럼 02</label><input id="uacValCl1" name="uacValCl1" type="text" class="inputText" maxlength="25" value="<c:out value='${info.uacValCl1}'/>"></li>
											<li><label for="txt04">가변컬럼 03</label><input id="uacValCl2" name="uacValCl2" type="text" class="inputText" maxlength="25" value="<c:out value='${info.uacValCl2}'/>"></li>
											<li><label for="txt05">가변컬럼 04</label><input id="uacValCl3" name="uacValCl3" type="text" class="inputText" maxlength="25" value="<c:out value='${info.uacValCl3}'/>"></li>
											<li><label for="txt06">가변컬럼 05</label><input id="uacValCl4" name="uacValCl4" type="text" class="inputText" maxlength="25" value="<c:out value='${info.uacValCl4}'/>"></li>
											<li><label for="txt07">가변컬럼 06</label><input id="uacValCl5" name="uacValCl5" type="text" class="inputText" maxlength="25" value="<c:out value='${info.uacValCl5}'/>"></li>
											<li><label for="txt08">가변컬럼 07</label><input id="uacValCl6" name="uacValCl6" type="text" class="inputText" maxlength="25" value="<c:out value='${info.uacValCl6}'/>"></li>
											<li><label for="txt09">가변컬럼 08</label><input id="uacValCl7" name="uacValCl7" type="text" class="inputText" maxlength="25" value="<c:out value='${info.uacValCl7}'/>"></li>
											<li><label for="txt10">가변컬럼 09</label><input id="uacValCl8" name="uacValCl8" type="text" class="inputText" maxlength="25" value="<c:out value='${info.uacValCl8}'/>"></li>
											<li><label for="txt11">가변컬럼 10</label><input id="uacValCl9" name="uacValCl9" type="text" class="inputText" maxlength="25" value="<c:out value='${info.uacValCl9}'/>"></li>
										</ul>
									</td>
								</tr>
								<tr>
									<th scope="row">사용여부</th>
									<td class="fontLeft last" colspan="3">
										<input type="radio" id="uacUseY" name="uacUseYn" value="Y" <c:if test="${info.uacUseYn eq 'Y' or empty info.uacUseYn}">checked="checked"</c:if>> <label for="uacUseY">사용</label>
										<input type="radio" id="uacUseN" name="uacUseYn" value="N" <c:if test="${info.uacUseYn eq 'N'}">checked="checked"</c:if>> <label for="uacUseN">미사용</label>
									</td>
								</tr>
								<tr>
									<th scope="row">위험관리항목</th>
									<td class="fontLeft last" colspan="3">
										<input type="radio" id="uacCatTypA" name="uacCatTyp" value="A" <c:if test="${info.uacCatTyp eq 'A' or empty info.uacCatTyp}">checked="checked"</c:if>> <label for="uacCatTypA">기술적</label>
										<input type="radio" id="uacCatTypD" name="uacCatTyp" value="D" <c:if test="${info.uacCatTyp eq 'D'}">checked="checked"</c:if>> <label for="uacCatTypD">관리적</label>
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
					<li><button type="button" class="btnStrong" onclick="fn_save();"><span class="icoSave"></span>저장</button></li>
				</ul>
			</div>
	    </article>
	</div>
</form>
</body>