<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<script type="text/javascript">
	function fn_search() {
		document.listForm.action = "FM-SETUP008.do";
		document.listForm.pageIndex.value = 1;
		document.listForm.submit();
	}

	function fn_egov_pageview(pageNo) {
		document.listForm.pageIndex.value = pageNo;
		document.listForm.action = "FM-SETUP008.do";
		document.listForm.submit();
	}

	function getCodeList() {

	}

	function setCodeInfo(code) {
		window.open("", "codePopup", "width=400, height=440, menubar=no, location=no, status=no, scrollbars=yes");
		$("#codeKey").val(code);
		document.listForm.target = "codePopup"; 
		document.listForm.action = "FM-SETUP008_popup.do";
		document.listForm.submit();
	}
</script>
</head>
<body>
	<form name="listForm" method="post">
	<input type="hidden" id="codeKey" name="codeKey"/>
	<p class="history">
		<a href="#none">시스템관리</a><span>&gt;</span>비주기설정
	</p>
	<div class="conttitle">비주기설정</div>
	<div class="search">
		<div class="defalt">
			<fieldset class="searchForm">
				<legend>검색 조건</legend>
				<ul class="defalt">
					<li>
						<input type="text" class="inputText" id="searchCondition" name="searchCondition" value="${searcoVO.searchCondition}" title="비주기명 입력" placeholder="비주기명을 입력하세요." />
					</li>
				</ul>
				<button href="javascript:fn_search();" class="btnSearch">검색</button>
			</fieldset>
		</div>
	</div>
	<div class="contents">
		<div class="talbeArea">
			<table summary="비주기설정 현황을 심사주기, 비주기코드, 비주기명, 기타, 사용여부 등의 항목으로 설명하고있습니다.">
				<colgroup>
					<!--col width="25%"-->
					<col width="10%">
					<col width="*">
					<col width="20%">
					<col width="10%">
					<col width="10%">
				</colgroup>
				<thead>
					<tr>
						<!--th scope="col">심사주기</th-->
						<th scope="col">비주기코드</th>
						<th scope="col">비주기명</th>
						<th scope="col">비고</th>
						<th scope="col">사용여부</th>
						<th scope="col" class="last"></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="list" items="${codeList}" varStatus="status">
						<tr>
							<!--td><c:out value="${list.stdDat}" />~<c:out value="${list.endDat}" /></td-->
							<td><c:out value="${list.uncNtrCod}" /></td>
							<td><c:out value="${list.uncNtrNam}" /></td>
							<td><c:out value="${list.uncEtc}" /></td>
							<td>
								<c:if test="${list.uncUseYn == 'Y'}">사용</c:if>
								<c:if test="${list.uncUseYn == 'N'}">미사용</c:if></td>
							<td class="last">
								<button onclick="setCodeInfo('<c:out value="${list.uncNtrCod}"/>'); return false;">수정</button>
							</td>
						</tr>
					</c:forEach>
					<c:if test="${fn:length(codeList) == 0}">
						<tr class="last">
							<td class="last noDataList" colspan=6>등록된 비주기설정이 없습니다.</td>
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
					<button onclick="setCodeInfo(''); return false;">
						<span class="icoAdd"></span>등록
					</button>
				</li>
			</ul>
		</div>
	</div>
	</form>
	
</body>
</html>