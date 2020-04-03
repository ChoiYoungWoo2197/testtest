<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<script type="text/javascript">
function fn_egov_search_Restde(){
	document.listForm.pageIndex.value = 1;
   	document.listForm.submit();
}

function fn_egov_pageview(pageNo){
	document.listForm.pageIndex.value = pageNo;
	document.listForm.action = "FM-COMPS001.do";
   	document.listForm.submit();
}

function fn_egov_regist_Restde(){
	document.listForm.action = "FM-COMPS001_RW.do";
	document.listForm.submit();
}

function fn_egov_select(id) {
	document.listForm.selectedId.value = id;
   	document.listForm.action = "FM-COMPS001_V.do";
   	document.listForm.submit();
}
</script>
</head>
<body>
	<form name="listForm" action="FM-COMPS001.do" method="post">
	<input type="hidden" name="selectedId">
	<input type="hidden" id="pageIndex" name="pageIndex">
	<c:import url="/titlebar.do" />
	<div class="contents">
		<div class="talbeArea">
			<table summary="운영주기 현황을 주기번호, 제목, 작성자 등의 항목으로 설명하고 있습니다.">
				<colgroup>
					<col width="10%">
					<col width="*">
					<col width="13%">
				</colgroup>
				<caption>운영주기 현황</caption>
				<thead>
				    <tr>
				        <th scope="col">번호</th>
				        <th scope="col">제목</th>
				        <th scope="col">작성자</th>
				    </tr>
				</thead>
				<tbody>
				<c:forEach var="result" items="${resultList}" varStatus="status">
					<tr>
					   <td><c:out value="${totCnt - ((paginationInfo.currentPageNo - 1) * 10 + status.index)}"/></td>
					   <td class="fontLeft"><a href="javascript:fn_egov_select('<c:out value="${result.ummManCyl}"/>');"><c:out value="${result.ummManTle}"/></a></td>
					   <td class="last"><c:out value="${result.ummCreId}"/></td>
					</tr>
				</c:forEach>
		     	<c:if test="${fn:length(resultList) == 0}">
					<tr class="last">
						<td class="last" class="last noDataList" colspan="3">
							등록된 운영주기가 없습니다.
						</td>
					</tr>
				</c:if>
				</tbody>
			</table>
	    </div>
	    <div class="paging">
	       <ui:pagination paginationInfo = "${paginationInfo}"	type="image" jsFunction="fn_egov_pageview"/>
	    </div>
	    <div class="bottomBtnArea">
			<ul class="btnList">
				<li>
					<button onclick="fn_egov_regist_Restde();"><span class="icoAdd"></span>등록</button>
				</li>
			</ul>
		</div>
	</div>
	</form>
</body>
</html>