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
	fn_assCatChange();
});

function fn_save(){
	if($("#usoCocCod").val() == ''){
		alert("우려사항 코드를 입력하세요.");
		$("#usoCocCod").focus();
		return;
	}

	if($("#usoAssCat").val() == ''){
		alert("자산 유형을 입력하세요.");
		$("#usoAssCat").focus();
		return;
	}

	/* if($("#usoGbnNam").val() == ''){
		alert("우려사항구분을 입력하세요.");
		$("#usoGbnNam").focus();
		return;
	} */

	if($("#usoCocNam").val() == ''){
		alert("우려사항명을 입력하세요.");
		$("#usoCocNam").focus();
		return;
	}

	if($("#usoCocDes").val() == ''){
		alert("우려사항 내용을 입력하세요.");
		$("#usoCocDes").focus();
		return;
	}

	if($("#usoRskGrd").val() == ''){
		alert("위험등급을 선택하세요.");
		$("#usoSecCat").focus();
		return;
	}

	if($("#usoRskPnt").val() == ''){
		alert("Concern Value를 선택하세요.");
		$("#usoSecCat").focus();
		return;
	}

	var usoCocKey = $("#usoCocKey").val();
	if(usoCocKey == ''){
		url = "${pageContext.request.contextPath}/riskm/FM-RISKM004_W.do";
	} else {
		url = "${pageContext.request.contextPath}/riskm/FM-RISKM004_U.do";
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

function fn_assCatChange() {
	if($("#usoAssCat").val() == '99') {
		$("#hidden01").show();
		$("#hidden02").show();
		$("#hidden03").show();


	} else {
		$("#hidden01").hide();
		$("#hidden02").hide();
		$("#hidden03").hide();
		$("#usoCocGrd option:eq(0)").attr("selected", "selected");
		$("#usoCocPnt option:eq(0)").attr("selected", "selected");
		$("#usoFrqGrd option:eq(0)").attr("selected", "selected");
		$("#usoFrqPnt option:eq(0)").attr("selected", "selected");
		$("#usoSumPnt").val("");
	}
}

function fn_sumPoint() {
	var usoCocPnt = $("#usoCocPnt").val();
	var usoFrqPnt = $("#usoFrqPnt").val();

	if(usoCocPnt == "") {
		usoCocPnt = 0;
	}
	if(usoFrqPnt == "") {
		usoFrqPnt = 0;
	}
	$("#usoSumPnt").val(Number(usoCocPnt) + Number(usoFrqPnt));
}
</script>
</head>
<body>
<form id=usoForm name="usoForm" method="post">
	<input type="hidden" id="usoCocKey" name="usoCocKey" value="${info.usoCocKey}">
	<input type="hidden" id="assCatCod" name="assCatCod">
	<div id="skipnavigation">
	    <ul>
	        <li><a href="#content-box">본문 바로가기</a></li>
	    </ul>
	</div>
	<div id="wrap" class="pop">
		<header>
			<div class="borderBox"></div>
			<h1>우려사항 세부내용</h1>
		</header>
		<article class="cont" id="content-box">
			<div class="cont_container">
				<div class="contents">
					<div class="talbeArea">
						<table summary="우려사항 세부내용">
							<colgroup>
								<col width="20%">
								<col width="30%">
								<col width="20%">
								<col width="30%">
							</colgroup>
							<caption>자산코드세부내용</caption>
							<tbody>
								<tr>
									<th scope="row"><label for="usoCocCod">우려사항코드</label></th>
									<td class="fontLeft ">
										<input type="text" id="usoCocCod" name="usoCocCod" class="inputText" value="<c:out value='${info.usoCocCod}'/>" />
									</td>
									<th scope="row"><label for="usoAssCat">자산유형</label></th>
									<td class="fontLeft last">
										<select id="usoAssCat" name="usoAssCat" class="selectBox"> <!-- onchange="fn_assCatChange();"> -->
											<option value="">선택</option>
											<c:forEach var="assCat" items="${assCatList}" varStatus="status">
												<option value="${assCat.key}" <c:if test="${assCat.key eq info.usoAssCat}">selected="selected"</c:if>><c:out value="${assCat.name}" /></option>
											</c:forEach>
											<%-- <option value="99" <c:if test="${'99' eq info.usoAssCat}">selected="selected"</c:if>>관리체계</option> --%>
										</select>
									</td>
								</tr>
								<tr>
									<th scope="row"><label for="usoGbnNam">우려사항구분</label></th>
									<td class="fontLeft last" colspan="3">
										<input type="text" id="usoGbnNam" name="usoGbnNam" class="inputText" value="<c:out value='${info.usoGbnNam}'/>" />
									</td>
								</tr>
								<tr>
									<th scope="row"><label for="usoCocNam">우려사항명</label></th>
									<td class="fontLeft last" colspan="3">
										<input type="text" id="usoCocNam" name="usoCocNam" class="inputText" value="<c:out value='${info.usoCocNam}'/>" />
									</td>
								</tr>
								<tr>
									<th scope="row"><label for="usoCocDes">우려사항 내용</label></th>
									<td class="fontLeft last" colspan="3">
										<textarea id="usoCocDes" name="usoCocDes" maxlength="250" class="txtAreaType04">${info.usoCocDes}</textarea>
									</td>
								</tr>
								<%-- <tr>
									<th scope="row"><label for="usoFirCat">우려사항<br/>대분류</label></th>
									<td class="fontLeft">
										<input type="text" id="usoFirCat" name="usoFirCat" class="inputText" style="width:109px" value="<c:out value='${info.usoFirCat}'/>" />
									</td>
									<th scope="row"><label for="usoSecCat">우려사항<br/>중분류명</label></th>
									<td class="fontLeft  last">
										<input type="text" id="usoSecCat" name="usoSecCat" class="inputText" style="width:109px" value="<c:out value='${info.usoSecCat}'/>" />
									</td>
								</tr> --%>
								<tr>
									<th scope="row"><label for="usoRskGrd">위험등급</label></th>
									<td class="fontLeft">
										<select id="usoRskGrd" name="usoRskGrd" class="selectBox">
											<option value="">선택</option>
											<option value="U" <c:if test="${'U' eq info.usoRskGrd}">selected="selected"</c:if>>상</option>
											<option value="M" <c:if test="${'M' eq info.usoRskGrd}">selected="selected"</c:if>>중</option>
											<option value="D" <c:if test="${'D' eq info.usoRskGrd}">selected="selected"</c:if>>하</option>
										</select>
									</td>
									<th scope="row"><label for="usoRskPnt">발생가능성</label></th>
									<td class="fontLeft last">
										<select id="usoRskPnt" name="usoRskPnt" class="selectBox">
											<option value="">선택</option>
											<option value="1" <c:if test="${'1' eq info.usoRskPnt}">selected="selected"</c:if>>1</option>
											<option value="2" <c:if test="${'2' eq info.usoRskPnt}">selected="selected"</c:if>>2</option>
											<option value="3" <c:if test="${'3' eq info.usoRskPnt}">selected="selected"</c:if>>3</option>
										</select>
									</td>
								</tr>
								<%-- <tr id="hidden01">
									<th scope="row"><label for="usoCocGrd">위험강도<br/>(중요도)</label></th>
									<td class="fontLeft">
										<select id="usoCocGrd" name="usoCocGrd" class="selectBox">
											<option value="">선택</option>
											<option value="H" <c:if test="${'H' eq info.usoCocGrd}">selected="selected"</c:if>>상</option>
											<option value="M" <c:if test="${'M' eq info.usoCocGrd}">selected="selected"</c:if>>중</option>
											<option value="L" <c:if test="${'L' eq info.usoCocGrd}">selected="selected"</c:if>>하</option>
										</select>
									</td>
									<th scope="row"><label for="usoCocPnt">위험강도<br/>평가점수</label></th>
									<td class="fontLeft last">
										<select id="usoCocPnt" name="usoCocPnt" onchange="fn_sumPoint();" class="selectBox">
											<option value="">선택</option>
											<option value="3" <c:if test="${'3' eq info.usoCocPnt}">selected="selected"</c:if>>상</option>
											<option value="2" <c:if test="${'2' eq info.usoCocPnt}">selected="selected"</c:if>>중</option>
											<option value="1" <c:if test="${'1' eq info.usoCocPnt}">selected="selected"</c:if>>하</option>
										</select>
									</td>
								</tr>
								<tr id="hidden02">
									<th scope="row"><label for="usoFrqGrd">발생빈도</label></th>
									<td class="fontLeft">
										<select id="usoFrqGrd" name="usoFrqGrd" class="selectBox">
											<option value="">선택</option>
											<option value="H" <c:if test="${'H' eq info.usoFrqGrd}">selected="selected"</c:if>>상</option>
											<option value="M" <c:if test="${'M' eq info.usoFrqGrd}">selected="selected"</c:if>>중</option>
											<option value="L" <c:if test="${'L' eq info.usoFrqGrd}">selected="selected"</c:if>>하</option>
										</select>
									</td>
									<th scope="row"><label for="usoFrqPnt">발생빈도<br/>평가점수</label></th>
									<td class="fontLeft last">
										<select id="usoFrqPnt" name="usoFrqPnt" onchange="fn_sumPoint();" class="selectBox">
											<option value="">선택</option>
											<option value="3" <c:if test="${'3' eq info.usoFrqPnt}">selected="selected"</c:if>>상</option>
											<option value="2" <c:if test="${'2' eq info.usoFrqPnt}">selected="selected"</c:if>>중</option>
											<option value="1" <c:if test="${'1' eq info.usoFrqPnt}">selected="selected"</c:if>>하</option>
										</select>
									</td>
								</tr>
								<tr id="hidden03">
									<th scope="row"><label for="usoSumPnt">위험평가<br/>합계점수</label></th>
									<td class="fontLeft last" colspan="3">
										<input type="text" id="usoSumPnt" name="usoSumPnt" class="inputText" style="width:109px" value="<c:out value='${info.usoSumPnt}'/>" readonly="readonly" />
									</td>
								</tr> --%>
								<tr>
									<th scope="row">사용유무</th>
									<td class="fontLeft last" colspan="3">
										<input type="radio" id="usoUseY" name="usoUseYn" value="Y" <c:if test="${info.usoUseYn eq 'Y' or empty info.usoUseYn}">checked="checked"</c:if> /> <label for="usoUseY">사용</label>
										<input type="radio" id="usoUseN" name="usoUseYn" value="N" <c:if test="${info.usoUseYn eq 'N'}">checked="checked"</c:if> /> <label for="usoUseN">미사용</label>
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
</html>