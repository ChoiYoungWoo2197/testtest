<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<script type="text/javascript">

	$(document).ready(function() {
		
	});

	function fn_egov_pageview(pageNo) {
		document.listForm.pageIndex.value = pageNo;
		document.listForm.action = "FM-MWORK012.do";
		document.listForm.submit();
	}	
</script>
</head>
<body>
<div>
	<p class="history">
		<a href="#none">보호활동</a><span>&gt;</span>업무알림
	</p>
	<div class="conttitle">업무알림</div>
	<form name="listForm" action="FM-MWORK012.do" method="post">
	<div class="contents">
		<div class="talbeArea">
			<table>
				<colgroup>
					<col width="5%">
					<col width="24%">
					<col width="35%">
					<col width="12%">
					<col width="12%">
					<col width="12%">
				</colgroup>
				<thead>
					<tr>
						<th scope="col">번호</th>
						<th scope="col">제목</th>
						<th scope="col">내용</th>
						<th scope="col">수신자</th>
						<th scope="col">발신자</th>
						<th scope="col">발신일자</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="result" items="${resultList}" varStatus="status">
						<tr>
<%-- 							<td><c:out value="${status.index+1}"/></td> --%>
							<td><c:out value="${result.rnum}"/></td> 
							<td class="fontLeft"><c:out value="${result.ueaSndTit}"/></td>
							<td class="fontLeft">${result.ueaSndCon}</td>
							<td><c:out value="${result.ueaRcvId}"/></td>
							<td><c:out value="${result.ueaSndId}"/></td>
							<td class="last"><c:out value="${result.ueaSndDat}"/></td>								
						</tr>
					</c:forEach>
					<c:if test="${fn:length(resultList) == 0}">
						<tr class="last">
							<td class="last noDataList" colspan=8>검색된 업무알림이 없습니다. 
							</td>
						</tr>
					</c:if>
				</tbody>
			</table>				
		</div>
		<div class="paging">
			<ui:pagination paginationInfo = "${paginationInfo}"	type="image" jsFunction="fn_egov_pageview"/>
			<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>"/>
		</div>			
	</div>
	</form>
</div>
</body>
</html>