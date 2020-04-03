<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib  prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<script type="text/javascript">
function fn_search(){
	document.listForm.pageIndex.value = 1;
   	document.listForm.submit();
}

function fn_pageview(pageNo){
	document.listForm.pageIndex.value = pageNo;
	document.listForm.action = "FM-SETUP018.do";
   	document.listForm.submit();
}


function fn_node_list(code, name) {
	document.listForm.uccFirCod.value = code;
	document.listForm.uccFirNam.value = name;
	document.listForm.action = "FM-SETUP018_N.do";
	document.listForm.submit();
}
</script>
</head>
<body>
<form name="listForm" action="FM-SETUP018.do" method="post">
<input type="hidden" id="uccFirCod" name="uccFirCod">
<input type="hidden" id="uccFirNam" name="uccFirNam">
<input type="hidden" id="pageIndex" name="pageIndex">
<input type="hidden" name="selectedId">
	<c:import url="/titlebar.do" />
	<div class="search">
	    <div class="defalt">
	        <fieldset class="searchForm">
	            <legend>대분류명</legend>
	            <ul class="detail newField">
	                <li style="width: 110px;">
	                	<span class="title">
	                		<select name="searchKeyword" class="selectBox">
	                			<option value="N" <c:if test="${searchVO.searchKeyword eq 'N'}">selected="selected"</c:if>>대분류명</option>
	                			<option value="C" <c:if test="${searchVO.searchKeyword eq 'C'}">selected="selected"</c:if>>대분류코드</option>
	                		</select>
						</span>
					</li>
					<li>
	                    <input type="text" id="searchCondition" class="inputText" size="" name="searchCondition" value="${searchVO.searchCondition}"  title="검색어 입력" placeholder="검색어를 입력하세요.">
	                </li>
	                <li>
		               <button onclick="fn_search();" class="btnSearch">검색</button>
		            </li>
	            </ul>
	        </fieldset>
	    </div>
	</div>
	<div class="contents">
		<div class="talbeArea">
			<table summary="공통코드 현황">
				<colgroup>
					<col width="8%">
					<col width="20%">
					<col width="*%">
					<col width="15%">
					<col width="15%">
					<col width="10%">
				</colgroup>
				<caption>공통코드 현황</caption>
				<thead>
				    <tr>
				        <th scope="col">번호</th>
				        <th scope="col">대분류코드</th>
				        <th scope="col">대분류명</th>
				        <th scope="col">코드수</th>
				        <th scope="col">최종 수정일</th>
				        <th scope="col">하위코드</th>
				    </tr>
				</thead>
				<tbody>
				<c:forEach var="result" items="${list}" varStatus="status">
				<tr>
					<td><c:out value="${totCnt - ((paginationInfo.currentPageNo - 1) * 10 + status.index)}"/></td>
					<td><c:out value="${result.uccFirCod}"/></td>
					<td><c:out value="${result.uccFirNam}"/></td>
					<td><c:out value="${result.firCnt}"/></td>
					<td><c:out value="${result.uccRgtMdh}"/></td>
					<td class="last">
						<button type="button" onclick="fn_node_list('${result.uccFirCod}','<c:out value="${result.uccFirNam}"/>');">보기</button>
					</td>
				</tr>
				</c:forEach>
		     	<c:if test="${fn:length(list) == 0}">
					<tr class="last">
						<td class="last noDataList" colspan="6">
							등록된 공통코드가 없습니다.
						</td>
					</tr>
				</c:if>
				</tbody>
			</table>
	    </div>
	    <div class="paging">
	       <ui:pagination paginationInfo = "${paginationInfo}"	type="image" jsFunction="fn_pageview"/>
	    </div>
	</div>
</form>
</body>
</html>