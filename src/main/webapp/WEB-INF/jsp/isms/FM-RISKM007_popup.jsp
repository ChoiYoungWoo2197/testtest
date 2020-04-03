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

$(document).ready(function() {
});

function fn_save(){

	if($("#urvRskDiv").val() == ""){
		alert("진단구분을 입력하시기 바랍니다.");
		return;
	}
	if($("#urvRskItm").val() == ""){
		alert("진단항목을 입력하시기 바랍니다.");
		return;
	}
	if($("#urvRskImp").val() == ""){
		alert("중요도를 입력하시기 바랍니다.");
		return;
	}
	/* if($("#usoCocCod").val() == ""){
		alert("우려사항을 입력하시기 바랍니다.");
		return;
	} */
	if($("#urvRskOrd").val() == ""){
		alert("정렬순서을 입력하시기 바랍니다.");
		return;
	}


	var urvRskKey = $("#urvRskKey").val();
	if(urvRskKey == ''){
		url = "${pageContext.request.contextPath}/riskm/FM-RISKM007_W.do";
	} else {
		url = "${pageContext.request.contextPath}/riskm/FM-RISKM007_U.do";
	}

	$.ajax({
		url			: url,
		type		: "post",
		data		: $("#usoForm").serialize(),
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

function fn_cocListPop(){
	window.open("","cocPop","width=600, height=580, menubar=no, location=no, status=no,scrollbars=yes");
	document.cocPopForm.submit();
}


</script>
</head>
<body>
<form id=usoForm name="usoForm" method="post">
	<input type="hidden" id="urvRskKey" name="urvRskKey" value="${info.urvRskKey}">
	<div id="skipnavigation">
	    <ul>
	        <li><a href="#content-box">본문 바로가기</a></li>
	    </ul>
	</div>
	<div id="wrap" class="pop">
		<header>
			<div class="borderBox"></div>
			<h1>취약점 진단 세부내용</h1>
		</header>
		<article class="cont" id="content-box">
			<div class="cont_container">
				<div class="contents">
					<div class="talbeArea">
						<table summary="취약점 진단 세부내용">
							<colgroup>
								<col width="20%">
								<col width="30%">
								<col width="20%">
								<col width="30%">
							</colgroup>
							<tbody>
								<tr>
									<th scope="row"><label for="arrAssCat">취약점 유형</label></th>
									<td class="fontLeft last" colspan="3">
										<select id="urvRskTyp" name="urvRskTyp" class="selectBox">
											<c:forEach var="vlbCod" items="${vlbList}" varStatus="status">
												<option value="${vlbCod.code}" <c:if test="${info.urvRskTyp eq vlbCod.code}">selected</c:if>><c:out value="${vlbCod.code}" /></option>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<th scope="row"><label for="urv_vlb_cod">진단코드</label></th>
									<td class="fontLeft last">
										<input type="text" id="urvVlbCod" name="urvVlbCod" class="inputText" value="<c:out value='${info.urvVlbCod}'/>" />
									</td>
									<th scope="row"><label for="urvRskDiv">진단구분</label></th>
									<td class="fontLeft last">
										<input type="text" id="urvRskDiv" name="urvRskDiv" class="inputText" value="<c:out value='${info.urvRskDiv}'/>" />
									</td>
								</tr>
								<tr>
									<th scope="row"><label for="urvRskItm">항목</label></th>
									<td class="fontLeft last" colspan="3">
										<input type="text" id="urvRskItm" name="urvRskItm" class="inputText" style="width:420px" value="<c:out value='${info.urvRskItm}'/>" />
									</td>
								</tr>
								<tr>
									<th scope="row"><label for="urvRskImp">중요도</label></th>
									<td class="fontLeft last">
									<select id="urvRskImp" name="urvRskImp" class="selectBox">
											<c:forEach var="vlbImp" items="${vlbImpList}" varStatus="status">
												<option value="${vlbImp.code}" <c:if test="${info.urvRskImp eq vlbImp.code}">selected</c:if>><c:out value="${vlbImp.name}" /></option>
											</c:forEach>
										</select>
<%-- 										<input type="text" id="urvRskImp" name="urvRskImp" class="inputText" value="<c:out value='${info.urvRskImp}'/>" /> --%>
									</td>
									<th scope="row"><label for="urvUseYn">사용여부</label></th>
									<td class="fontLeft last" colspan="3">
										<input type="radio" id="urvUseY" name="urvUseYn" value="Y" <c:if test="${info.urvUseYn eq 'Y' or empty info.urvUseYn}">checked="checked"</c:if> /> <label for="urvUseY">사용</label>
										<input type="radio" id="urvUseY" name="urvUseYn" value="N" <c:if test="${info.urvUseYn eq 'N'}">checked="checked"</c:if> /> <label for="urvUseN">미사용</label>
									</td>
								</tr>
								<tr>
									<th scope="row"><label for="urvRskOrd">정렬순서</label></th>
									<td class="fontLeft last" colspan="3">
										<input type="text" id="urvRskOrd" name="urvRskOrd" style="width: 80px;" class="inputText" value="<c:out value='${info.urvRskOrd}'/>" />
									</td>
								</tr>
								<%-- <tr>
									<th scope="row"><label for="usoCocCod">우려사항 연계코드</label></th>
									<td class="fontLeft last" colspan="3">
										<input type="text" id="usoCocCod" name="usoCocCod" style="width: 80px;" class="inputText" value="<c:out value='${info.usoCocCod}'/>" />
										<button type="button" onclick="fn_cocListPop();"><span class="icoSave"></span></button>
										<input type="text" id="usoCocNam" name="usoCocNam" style="width: 200px;" class="inputText" value="<c:out value='${info.usoCocNam}'/>" />
									</td>
								</tr> --%>
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
<form id="cocPopForm" name="cocPopForm" action="FM_RISKM007_cocListPopup.do" method="post" target="cocPop">
</form>
</body>
</html>