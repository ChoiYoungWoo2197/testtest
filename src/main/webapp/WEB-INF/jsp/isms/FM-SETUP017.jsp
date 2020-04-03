<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script type="text/javascript">
	function fn_search() {
		document.listForm.action = "FM-SETUP017.do";
		document.listForm.pageIndex.value = 1;
		document.listForm.submit();
	}

	function fn_pageview(pageNo) {
		document.listForm.pageIndex.value = pageNo;
		document.listForm.action = "FM-SETUP017.do";
		document.listForm.submit();
	}

	function fn_regist_Restde() {
		document.listForm.action = "FM-SETUP017_RW.do";
		document.listForm.submit();
	}

	function fn_auh_select(id) {
		document.listForm.uatAuhKey.value = id;
		document.listForm.action = "FM-SETUP017_RW.do";
		document.listForm.submit();
	}
</script>
<c:import url="/titlebar.do" />
<form name="listForm" action="FM-SETUP017.do" method="post">
<input type="hidden" id="uatAuhKey" name="uatAuhKey">
<div class="search">
	<div class="defalt">
		<fieldset class="searchForm">
			<legend>기본검색</legend>
			<ul class="defalt">
				<li>
					<input type="text" id="searchCondition" name="searchCondition" class="inputText" value="${searchVO.searchCondition}" title="권한명 입력" placeholder="권한명을 입력하세요."  />
				</li>
			</ul>
			<button class="btnSearch" onclick="fn_search()">검색</button>
		</fieldset>
	</div>
</div>
<div class="contents">
	<div class="talbeArea">
		<table summary="권한관리를 번호, 권한명, 권한등급, 사용유무, 작성자, 작성일 등의 항목으로 설명하고있습니다.">
			<colgroup>
				<col width="8%">
				<col width="*">
				<col width="10%">
				<col width="15%">
				<col width="15%">
			</colgroup>
			<thead>
				<tr>
					<th scope="col">번호</th>
					<th scope="col">권한명</th>
					<th scope="col">권한등급</th>
					<th scope="col">사용유무</th>
					<th scope="col">작성자</th>
					<th scope="col" class="last">작성일</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="result" items="${list}" varStatus="status">
					<tr>
						<td><c:out value="${totCnt - ((paginationInfo.currentPageNo - 1) * 10 + status.index)}" /></td>
						<td  class="fontLeft"><a href="javascript:fn_auh_select('<c:out value="${result.uatAuhKey}"/>');"><c:out value="${result.uatAuhNam}" /></a></td>
						<td><c:out value="${result.uatAuhGbn}" /></td>
						<td>
						<c:choose>
							<c:when test="${result.uatUseYn eq 'Y'}">사용</c:when>
							<c:otherwise>미사용</c:otherwise>
						</c:choose></td>
						<td><c:out value="${result.uumUsrNam}" /></td>
						<td class="last"><c:out value="${result.uatRgtMdh}" /></td>
					</tr>
				</c:forEach>
				<c:if test="${fn:length(list) == 0}">
					<tr class="last">
						<td class="last noDataList" colspan="6">
							등록된 권한이 없습니다.
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
	<div class="bottomBtnArea">
		<ul class="btnList">
			<li>
				<button onclick="fn_regist_Restde();">
					<span class="icoAdd"></span>등록
				</button>
			</li>
		</ul>
	</div>
</div>
</form>