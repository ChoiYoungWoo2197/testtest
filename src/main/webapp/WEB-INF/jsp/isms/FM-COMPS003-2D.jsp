<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib  prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<script type="text/javascript">
function go3Depth(ucm2lvCod){
	$("#hUcm2lvCod").val(ucm2lvCod);
	frm = document.listForm;
   	frm.action = "FM-COMPS003_3D.do"
	frm.submit();
}
function go1Depth(){
	frm = document.listForm;
   	frm.action = "FM-COMPS003.do"
	frm.submit();
}
function sav(){
	if(!arrValidation($("input[name=ucm2lvCod]"))) {
		alert("통제항목번호를 입력하세요.");
		return;
	}

	if(!arrValidation($("input[name=ucm2lvNam]"))) {
		alert("통제항목명을 입력하세요.");
		return;
	}

	if(!arrValidation($("input[name=ucm2lvDtl]"))) {
		alert("통제항목내용명을 입력하세요.");
		return;
	}

	var arrCod = [];
	for(var i=0; i<$("[name=ucm2lvCod]").length; i++){
		if($.inArray($("[name=ucm2lvCod]")[i].value, arrCod)>=0){
			alert("통제항목번호가 중복되었습니다.");
			$("[name=ucm2lvCod]")[i].focus();
			return ;
		}else{
			arrCod.push($("[name=ucm2lvCod]")[i].value);
		}
	}

	frm = document.listForm;
   	frm.action = "FM-COMPS003_2D_SAV.do"
	frm.submit();
}

function addCTR(){
	 var inData = "<tr><td><input type='text' name='sts' value='C' readonly='readonly' class='inputText' style='width:15px;'>" +
			"<td><input type='text' class='inputText' style='width:80px;' name='ucm2lvCod' maxlength='10'></td>" +
			"<td><input type='text' class='inputText' size='20' name='ucm2lvNam' maxlength='1500'></td>" +
			"<td><input type='text' class='inputText' size='40' name='ucm2lvDtl' maxlength='1500'></td>" +
			"<td class='last'><button onclick='addDel(this);'>삭제</button></td><tr>";
	$("#tbody").append(inData);
	$("input[name='ucm2lvCod']").last().focus();
}

function mark(key){
	$("#sts_"+key).val("U");
}

function addDel(_this){
	$(_this).parent().parent().html('');
	return false;
}
</script>
</head>
<body>
<form name="listForm" id="listForm" method="post">
<input type="hidden" id="hUcm1lvCod" name="hUcm1lvCod" value="${ucm1lvCod}">
<input type="hidden" id="hUcm2lvCod" name="hUcm2lvCod">
<input type="hidden" id="standard" name="standard" value="${standard}">
<input type="hidden" id="service" name="service" value="${service}">
<c:import url="/titlebar.do" />
<div class="contents">
	<div class="tabArea">
	    <ul class="list3Tab">
	        <li><a href="javascript:go1Depth();">통제목적</a></li>
	        <li class="sel"><a href="#none">통제항목</a></li>
	        <li><a href="#none">통제점검</a></li>
	    </ul>
	</div>
	<div class="topBtnArea">
		<ul class="btnList">
			<li><button onclick="addCTR(); return false;"><span class="icoAdd"></span>추가</button></li>
			<li><button onclick="sav(); return false;"><span class="icoSave"></span>저장</button></li>
		</ul>
	</div>
	<div class="talbeArea">
		<table summary="통제항목설정">
			<colgroup>
				<col width="8%"/>
				<col width="12%"/>
				<col width="20%"/>
				<col width=""/>
				<col width="13%"/>
			</colgroup>
			<caption>통제항목설정 현황</caption>
			<thead>
			    <tr>
					<th colspan="2">통제항목번호</th>
					<th>통제항목명</th>
					<th>통제항목내용</th>
					<th colspan="2">사용유무</th>
			    </tr>
			</thead>
			<tbody id="tbody">
			 <c:forEach var="result" items="${resultList}" varStatus="status">
				<tr>
					<td><button onclick="go3Depth('${result.ucm2lvCod}');">점검</button></td>
					<td><input type="text" size="10" name="ucm2lvCod" value="<c:out value="${result.ucm2lvCod}"/>" class="inputText" style="width:80px;" readonly="readonly"></td>
					<td><input type="text" size="20" name="ucm2lvNam" value="<c:out value="${result.ucm2lvNam}"/>" class="inputText" onclick="mark(${status.index});" maxlength="1500"></td>
					<td><input type="text" size="40" name="ucm2lvDtl" value="<c:out value="${result.ucm2lvDtl}"/>" class="inputText" onclick="mark(${status.index});" maxlength="1500"></td>
					<td class="last">
						<label><input type="radio" name="rad_${status.index}" value="N" <c:if test="${result.ucm2ldYn == 'N'}">checked="checked"</c:if> onclick="mark(${status.index});"> 사용</label>
						<label><input type="radio" name="rad_${status.index}" value="Y" <c:if test="${result.ucm2ldYn == 'Y'}">checked="checked"</c:if> onclick="mark(${status.index});"> 미사용</label>
						<input type="hidden" id="sts_${status.index}" name="sts" value="R" />
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
    </div>
</div>
</form>
</body>
</html>
