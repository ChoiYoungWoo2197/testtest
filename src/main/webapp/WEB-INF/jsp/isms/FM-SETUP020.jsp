<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
<script type="text/javascript">
	function fn_search() {
		document.listForm.action = "FM-SETUP020.do";
		document.listForm.pageIndex.value = 1;
		document.listForm.submit();
	}

	function fn_pageview(pageNo) {
		document.listForm.pageIndex.value = pageNo;
		document.listForm.action = "FM-SETUP020.do";
		document.listForm.submit();
	}

	function fn_regist_Restde() {
		alert("준비 중"); return;
		document.listForm.uvmSvcKey.value = "";
		document.listForm.action = "FM-SETUP020_RW.do";
		document.listForm.submit();
	}

	function fn_view(uvmSvcKey) {
		alert("준비 중"); return;
		document.listForm.uvmSvcKey.value = uvmSvcKey;
		document.listForm.action = "FM-SETUP020_N.do";
		document.listForm.submit();
	}

</script>
		<c:import url="/titlebar.do" />
		<form name="listForm" action="FM-SETUP020.do" method="post">
		<input type="hidden" id="uvmSvcKey" name="uvmSvcKey" />
		<div class="search">
		    <div class="defalt">
		        <fieldset class="searchForm">
		            <legend>기본검색</legend>
		            <ul class="detail newField">
		                <li>
			                <span class="title"><label for="workCycleTerm">서비스명</label></span>
		                    <input id="txt01" class="inputText" type="text"	title="서비스명 입력" placeholder="서비스명을 입력하세요." name="searchCondition" value="${searchVO.searchCondition}" style="width:200px;"/>
		                </li>
		                <li style="width: auto;">
		                 	<button type="button" id="btnSearch" class="btnSearch" onclick="fn_search()">검색</button>
		                 </li>
		            </ul>
		        </fieldset>
		    </div>
		</div>
		<div class="contents">
			<div class="talbeArea">
				<table summary="메뉴관리를 번호, 메뉴명, 메뉴 URL, 순서,  작성자, 작성일, 사용유무, 하위메뉴 등의 항목으로 설명하고있습니다.">
					<colgroup>
						<col width="5%">
						<col width="15%">
						<col width="*%">
						<col width="15%">
						<col width="10%">
						<col width="15%">
						<col width="15%">
					</colgroup>
					<thead>
						<tr>
							<th scope="col">번호</th>
							<th scope="col">서비스 코드</th>
							<th scope="col">서비스명</th>
							<th scope="col">하위 서비스 수</th>
							<th scope="col">사용우뮤</th>
							<th scope="col">작성자</th>
							<th scope="col" class="last">작성일</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="result" items="${list}" varStatus="status">
							<tr>
								<td><c:out value="${status.index + 1}" /></td>
								<td><c:out value="${result.svcCod}" /></td>
								<%-- <td><a href="javascript:fn_view('<c:out value="${result.svcCod}"/>');"><c:out value="${result.svcNam}" /></a></td> --%>
								<td><c:out value="${result.svcNam}" /></td>
								<td><c:out value="${result.subCnt}" /></td>
								<td><c:choose><c:when test="${result.useYn eq 'Y'}">사용</c:when><c:otherwise>미사용</c:otherwise></c:choose></td>
								<td><c:out value="${result.rgtNam}" /></td>
								<td class="last"><c:out value="${result.rgtMdh}" /></td>
							</tr>
						</c:forEach>
						<c:if test="${fn:length(list) == 0}">
							<tr class="last">
								<td class="last noDataList" colspan="8">
									등록된 서비스가 없습니다.
								</td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</div>
			<div class="bottomBtnArea">
				<!-- <ul class="btnList">
					<li>
						<button onclick="fn_regist_Restde(); return false;">
							<span class="icoAdd"></span>등록
						</button>
					</li>
				</ul> -->
			</div>
		</div>
		</form>