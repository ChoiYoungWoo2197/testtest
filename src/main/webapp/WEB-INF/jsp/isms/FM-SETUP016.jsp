<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script type="text/javascript">
	function fn_search() {
		if ($("#stndNam").val() == "") {
			alert("메뉴명을 입력해 주세요.");
			return;
		}
		document.listForm.action = "FM-SETUP016.do";
		document.listForm.pageIndex.value = 1;
		document.listForm.submit();
	}

	function fn_pageview(pageNo) {
		document.listForm.pageIndex.value = pageNo;
		document.listForm.action = "FM-SETUP016.do";
		document.listForm.submit();
	}

	function fn_regist_Restde() {
		document.listForm.ummMnuKey.value = "";
		document.listForm.action = "FM-SETUP016_RW.do";
		document.listForm.submit();
	}

	function fn_mnu_select(id) {
		document.listForm.ummMnuKey.value = id;
		document.listForm.action = "FM-SETUP016_RW.do";
		document.listForm.submit();
	}

	function fn_node_list(ummMnuKey, ummMnuNam) {
		document.listForm.ummMnuPrt.value = ummMnuKey;
		document.listForm.ummMnuPrtNam.value = ummMnuNam;
		document.listForm.action = "FM-SETUP016_N.do";
		document.listForm.submit();
	}

</script>
		<c:import url="/titlebar.do" />
		<form name="listForm" action="FM-SETUP016.do" method="post">
		<input type="hidden" id="ummMnuKey" name="ummMnuKey">
		<input type="hidden" id="ummMnuPrt" name="ummMnuPrt">
		<input type="hidden" id="ummMnuPrtNam" name="ummMnuPrtNam">
		<div class="search">
		    <div class="defalt">
		        <fieldset class="searchForm">
		            <legend>기본검색</legend>
		            <ul class="defalt">
		                <li>
		                    <input id="txt01" class="inputText" type="text"	title="메뉴명 입력" placeholder="메뉴명를 입력하세요." name="searchCondition" value="${searchVO.searchCondition}"/>
		                </li>
		            </ul>
		            <button class="btnSearch" onclick="fn_search()">검색</button>
		        </fieldset>
		    </div>
		</div>
		<div class="contents">
			<div class="talbeArea">
				<table summary="메뉴관리를 번호, 메뉴명, 메뉴 URL, 순서,  작성자, 작성일, 사용유무, 하위메뉴 등의 항목으로 설명하고있습니다.">
					<colgroup>
						<col width="5%">
						<col width="20%">
						<col width="*%">
						<col width="5%">
						<col width="12%">
						<col width="13%">
						<col width="10%">
						<col width="10%">
					</colgroup>
					<thead>
						<tr>
							<th scope="col">번호</th>
							<th scope="col">메뉴명</th>
							<th scope="col">메뉴 URL</th>
							<th scope="col">순서</th>
							<th scope="col">작성자</th>
							<th scope="col">작성일</th>
							<th scope="col">사용유무</th>
							<th scope="col" class="last">하위메뉴</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="result" items="${list}" varStatus="status">
							<tr>
								<td><c:out value="${totCnt - ((paginationInfo.currentPageNo - 1) * 10 + status.index)}" /></td>
								<td><a href="javascript:fn_mnu_select('<c:out value="${result.ummMnuKey}"/>');"><c:out value="${result.ummMnuNam}" /></a></td>
								<td><c:out value="${result.ummMnuUrl}" /></td>
								<td><c:out value="${result.ummOdrNum}" /></td>
								<td><c:out value="${result.uumUsrNam}" /></td>
								<td><c:out value="${result.ummRgtMdh}" /></td>
								<td><c:choose><c:when test="${result.ummUseYn eq 'Y'}">사용</c:when><c:otherwise>미사용</c:otherwise></c:choose></td>
								<td class="last">
									<button type="button" onclick="fn_node_list('${result.ummMnuKey}', '${result.ummMnuNam}');">보기</button>
									</td>
							</tr>
						</c:forEach>
						<c:if test="${fn:length(list) == 0}">
							<tr class="last">
								<td class="last noDataList" colspan="8">
									등록된 메뉴가 없습니다.
								</td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</div>
			<div class="paging">
				<ui:pagination paginationInfo = "${paginationInfo}"	type="image" jsFunction="fn_pageview"/>
				<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>"/>
			</div>
			<div class="bottomBtnArea">
				<ul class="btnList">
					<li>
						<button onclick="fn_regist_Restde(); return false;">
							<span class="icoAdd"></span>등록
						</button>
					</li>
				</ul>
			</div>
		</div>
		</form>