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
	if($("#usmSroLv0").val() == ''){
		alert("분류 코드를 입력하세요.");
		$("#usmSroLv0").focus();
		return;
	}

	if($("#usmSroLv1").val() == ''){
		alert("통제 목적을 입력하세요.");
		$("#usmSroLv1").focus();
		return;
	}

	if($("#usmSroLv2").val() == ''){
		alert("통제 사항을 입력하세요.");
		$("#usmSroLv2").focus();
		return;
	}

	if($("#usmSroDes").val() == ''){
		alert("위험시나리오를 입력하세요.");
		$("#usmSroDes").focus();
		return;
	}

	var usmSroKey = "${info.usmSroKey}";
	if(usmSroKey == ''){
		url = "${pageContext.request.contextPath}/riskm/FM-RISKM005_W.do";
	} else {
		url = "${pageContext.request.contextPath}/riskm/FM-RISKM005_U.do";
	}

	$.ajax({
		url			: url,
		type		: "post",
		data		: $("#usmForm").serialize(),
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
</script>
</head>
<body>
<form id=usmForm name="usmForm" method="post">
	<input type="hidden" name="usmSroKey" value="${info.usmSroKey}">
	<div id="skipnavigation">
	    <ul>
	        <li><a href="#content-box">본문 바로가기</a></li>
	    </ul>
	</div>
	<div id="wrap" class="pop">
		<header>
			<div class="borderBox"></div>
			<h1>위험시나리오 세부내용</h1>
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
									<th scope="row"><label for="usmSroLv0">분류</label></th>
									<td class="fontLeft last" colspan="3">
										<input type="text" id="usmSroLv0" name="usmSroLv0" class="inputText" maxlength="25" value="<c:out value='${info.usmSroLv0}'/>">
									</td>
								</tr>
								<tr>
									<th scope="row"><label for="usmSroLv1">통제목적</label></th>
									<td class="fontLeft last" colspan="3">
										<input type="text" id="usmSroLv1" name="usmSroLv1" class="inputText" maxlength="25" value="<c:out value='${info.usmSroLv1}'/>">
									</td>
								</tr>
								<tr>
									<th scope="row"><label for="usmSroLv2">통제사항</label></th>
									<td class="fontLeft last" colspan="3">
										<input type="text" id="usmSroLv2" name="usmSroLv2" class="inputText" maxlength="25" value="<c:out value='${info.usmSroLv2}'/>">
									</td>
								</tr>
								<tr>
									<th scope="row"><label for="txtArea01">위험시나리오</label></th>
									<td class="fontLeft last" colspan="3">
										<textarea id="usmSroDes" name="usmSroDes" maxlength="250" class="txtAreaType04" >${info.usmSroDes}</textarea>
									</td>
								</tr>
								<tr>
									<th scope="row"><label for="usmCocKey">위협코드</label></th>
									<td class="fontLeft">
										<input type="text" id="usmCocKey" name="usmCocKey" class="inputText" maxlength="25" value="<c:out value='${info.usmCocKey}'/>">
									</td>
									<th scope="row"><label for="usmRefCod">참조코드</label></th>
									<td class="fontLeft last">
										<input type="text" id="usmRefCod" name="usmRefCod" class="inputText" maxlength="25" value="<c:out value='${info.usmRefCod}'/>">
									</td>
								</tr>
								<tr>
									<th scope="row">사용유무</th>
									<td class="fontLeft last" colspan="3">
										<input type="radio" id="usmUseY" name="usmUseYn" value="Y" <c:if test="${info.usmUseYn eq 'Y' or empty info.usmUseYn}">checked="checked"</c:if> /> <label for="usmUseY">사용</label>
										<input type="radio" id="usmUseN" name="usmUseYn" value="N" <c:if test="${info.usmUseYn eq 'N'}">checked="checked"</c:if> /> <label for="usmUseN">미사용</label>
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