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
	<div class="talbeArea">
		<table summary="통제항목설정">
			<colgroup>
				<col width="7%"/>
				<col width="8%"/>
				<col width="18%"/>
				<col width="*"/>
			</colgroup>
			<caption>통제항목설정 현황</caption>
			<thead>
			    <tr>
					<th colspan="2">통제항목번호</th>
					<th>통제항목명</th>
					<th>통제항목내용</th>
			    </tr>
			</thead>
			<tbody id="tbody">
			 <c:forEach var="result" items="${resultList}" varStatus="status">
				<tr>
					<td><button onclick="go3Depth('${result.ucm2lvCod}');">점검</button></td>
					<td>${result.ucm2lvCod}</td>
					<td>${result.ucm2lvNam}</td>
					<td style="text-align: left">${result.ucm2lvDtl}</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
    </div>
</div>
</form>
</body>
</html>
