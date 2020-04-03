<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="/WEB-INF/include/contents.jsp"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<script type="text/javascript">
	function fn_egov_search_Restde() {
		document.listForm.pageIndex.value = 1;
		document.listForm.submit();
	}

	function fn_egov_pageview(pageNo) {
		document.listForm.pageIndex.value = pageNo;
		document.listForm.action = "FM-BOARD005.do";
		document.listForm.submit();
	}

	function fn_egov_regist_Restde() {
		document.listForm.action = "FM-BOARD005_RW.do";
		document.listForm.submit();
	}

	function fn_egov_select(id) {
		document.listForm.selectedId.value = id;
		document.listForm.action = "FM-BOARD005_V.do";
		document.listForm.submit();
	}
</script>
</head>
<body>
	<c:import url="/titlebar.do" />
	<form name="listForm" action="FM-BOARD005.do" method="post">
	<input type="hidden" name="selectedId" />
	<div class="search">
		<div class="defalt">
			<fieldset class="searchForm">
				<legend>검색 조건</legend>
				<ul class="defalt">
					<li><select id="sel01" class="selectBox" title="검색항목 선택" name="searchCondition">
							<option value="1"
								<c:if test="${searchVO.searchCondition == '1'}">selected="selected"</c:if>>제목</option>
							<option value="2"
								<c:if test="${searchVO.searchCondition == '2'}">selected="selected"</c:if>>내용</option>
							<option value="3"
								<c:if test="${searchVO.searchCondition == '3'}">selected="selected"</c:if>>통제항목</option>
					</select></li>
					<li><input id="searchKeyword" name="searchKeyword" value="${searchVO.searchKeyword}" class="inputText" type="text"	title="검색어 입력" placeholder="검색어를 입력하세요."/></li>
				</ul>
				<button href="#none" class="btnSearch" onclick="fn_egov_search_Restde();">검색</button>
			</fieldset>
		</div>
	</div>
	<div class="contents">
		<div class="talbeArea">
			<table summary="FAQ 현황을 번호, 제목, 공통표준, 통제항목번호 등의 항목으로 설명하고있습니다.">
				<colgroup>
					<col width="8%">
					<col width="*%">
					<col width="10%">
					<col width="15%">
				</colgroup>
				<thead>
					<tr>
						<th scope="col">번호</th>
						<th scope="col">제목</th>
						<th scope="col">공통표준</th>
						<th scope="col" class="last">통제항목번호</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="result" items="${resultList}" varStatus="status">
						<tr>
							<td><c:out value="${totCnt - ((paginationInfo.currentPageNo - 1) * 10 + status.index)}" /></td>
							<td  class="fontLeft"><a href="javascript:fn_egov_select('<c:out value="${result.ubmBrdKey}"/>')">
								<c:out value="${result.ubmBrdTle}" /></a></td>
							<td><c:out value="${result.standard}" /></td>
							<td class="last"><c:out value="${result.ubmGolNo}" /></td>
						</tr>
					</c:forEach>
					<c:if test="${fn:length(resultList) == 0}">
						<tr class="last">
							<td class="last noDataList" colspan=4>
								등록된 게시물이 없습니다.
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
		<c:if test="${writeAuth eq true}">
			<div class="bottomBtnArea">
				<ul class="btnList">
					<li>
						<button onclick="fn_egov_regist_Restde(); return false;">
							<span class="icoAdd"></span>글쓰기
						</button>
					</li>
				</ul>
			</div>
			</c:if>
	</div>
	</form>
</body>
</html>