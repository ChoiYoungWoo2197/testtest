<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
<%@ include file="/WEB-INF/include/contents.jsp"%>
<html lang="ko">
<head>
<script type="text/javascript">
function goPage(){	
	location.href = "${pageContext.request.contextPath}/mwork/FM-MWORK005.do";
}

function saveWorkApp() {
	var cnt = 0;
	var arrKey = "";
	$("input[name=chkKey]:checked").each(function(i) {
		if(cnt != 0) {
			arrKey += "," + $(this).val();
		} else {
			arrKey += $(this).val();
		}
		cnt++;
	});
	if(cnt == 0) {
		alert("승인 업무를 선택하세요.");
		return;
	}
	
	$.ajax({
		url			: "${pageContext.request.contextPath}/mwork/FM-MWORK005_APP.do",
		type		: "post",
		data		: {wrkKey:arrKey},
		dataType	: "json",
		success		: function(data){
			alert("승인되었습니다.");
			location.reload(true);
		 },
		 error : function(){
			 alert('error');
		 }
	 });
}

function saveWorkRtn() {
	var cnt = 0;
	var arrKey = "";
	$("input[name=chkKey]:checked").each(function(i) {
		if(cnt != 0) {
			arrKey += "," + $(this).val();
		} else {
			arrKey += $(this).val();
		}
		cnt++;
	});
	if(cnt == 0) {
		alert("반려 업무를 선택하세요.");
		return;
	}
	
	$.ajax({
		url			: "${pageContext.request.contextPath}/mwork/FM-MWORK005_RTN.do",
		data		: {wrkKey:arrKey},
		dataType	: "json",
		success		: function(data){
			alert("반려되었습니다.");
			location.reload(true);
		 },
		 error : function(){
			 alert('error');
		 }
	 });
}


</script>
</head>
<body>
	<p class="history">
		<a href="#none">정보보호활동</a><span>&gt;</span>담당자별현황조회
	</p>
	<div class="conttitle">담당자별현황조회</div>
	<div class="tabArea">
	    <ul class="list2Tab">
	        <li><a href="javascript:goPage();">담당자별 현황</a></li>
	        <li class="sel"><a href="#none">업무이관 요청</a></li>
	    </ul>
	</div> 
	<div class="contents">
		<div class="title">업무이관 요청</div>
		<div class="talbeArea">
			<div class="topBtnArea">
				<ul class="btnList">
					<li>
						<button type="button" onclick="saveWorkApp()"><span class="icoAdd"></span>승인</button>
					</li>
					<li>
						<button type="button" onclick="saveWorkRtn();"><span class="icoAdd"></span>반려</button>
					</li>
				</ul>
			</div>
			<table>
				<colgroup>
					<col width="5" />
					<col width="10%" />
					<col width="*%" />
					<col width="12%" />
					<col width="12%" />
					<col width="10%" />
					<col width="10%" />
					<col width="15%" />
				</colgroup>
				<thead>
					<tr>
						<th scope="col"><input type="checkbox" class="chkAll01" title="리스트 전체 선택"></th>
						<th scope="col">주기</th>
						<th scope="col">업무명</th>
						<th scope="col">업무시작일</th>
						<th scope="col">업무종료일</th>
						<th scope="col">요청자</th>
						<th scope="col">업무이관자</th>
						<th scope="col">상태</th>
					</tr>
				</thead>
				<tbody>
					 <c:forEach var="info" items="${resultList}" varStatus="status">
					<tr>
						<td>
						<c:if test="${info.utwStaGbn eq 'RES' and info.uamStaCod eq '31'}">
							<input type="checkbox" name="chkKey" class="chkNode01" value="${info.utwWrkKey}">
						</c:if>
						</td>
						<td><c:out value="${info.utdTrmNam}"/></td>
						<td><c:out value="${info.utdDocNam}"/></td>
						<td><c:out value="${info.utwStrDat}"/></td>
						<td><c:out value="${info.utwEndDat}"/></td>
						<td><c:out value="${info.utwWrkId}"/></td>
						<td><c:out value="${info.utwAgnId}"/></td>
						<td><c:out value="${info.utwStaNam}"/></td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="topBtnArea">
			</div>
		</div>
	</div>
</body>
</html>