<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
<script type="text/javascript">

	function fn_search() {
		document.listForm.action = "FM-COMPS006.do";
		document.listForm.submit();
	}

	function fn_view(id) {
		document.listForm.uomSvcCod.value = id;
		document.listForm.uccSndCod.value = id;
		document.listForm.action = "FM-COMPS006_RW.do";
		document.listForm.submit();
	}

</script>
		<c:import url="/titlebar.do" />
		<form name="listForm" action="FM-COMPS006.do" method="post">
		<input type="hidden" id="uccFirCod" name="uccFirCod" value="SERVICE">
		<input type="hidden" id="uomSvcCod" name="uomSvcCod">
		<input type="hidden" id="uccSndCod" name="uccSndCod">
		<input type="hidden" id="uomBcyCod" name="uomBcyCod" value="${searchVO.bcyCode}">
		<input type="hidden" id="year" name="year" value="${selectedYear}">
		<div class="search">
		    <div class="defalt">
		        <fieldset class="searchForm">
		            <legend>기본검색</legend>
		            <ul class="defalt">
						<li>
							<select id="year-select-redirect" name="bcyCode" class="selectBox" style="width: 200px">
								<c:forEach varStatus="status" items="${mainCycleList}" var="cycle">
									<option <c:if test="${selectedYear == cycle.year}">selected="selected"</c:if> value="${cycle.year}">${cycle.name}</option>
								</c:forEach>
							</select>
						</li>
<%--		           		<li>--%>
<%--		           			<sb:select name="service" type="S" value="${searchVO.service }"/>--%>
<%--		                </li>--%>
		            </ul>
<%--		            <button class="btnSearch" onclick="fn_search()">검색</button>--%>
		        </fieldset>
		    </div>
		</div>
		<div class="contents">
			<div class="talbeArea">
				<table summary="메뉴관리를 번호, 메뉴명, 메뉴 URL, 순서,  작성자, 작성일, 사용유무, 하위메뉴 등의 항목으로 설명하고있습니다.">
					<colgroup>
						<col width="5%">
						<col width="10%">
						<col width="20%">
						<col width="25%">
						<col width="10%">
						<col width="15%">
						<col width="15%">
					</colgroup>
					<thead>
						<tr>
							<th scope="col">번호</th>
							<th scope="col">연도</th>
							<th scope="col">코드</th>
							<th scope="col">서비스</th>
							<th scope="col">부서수</th>
							<th scope="col">작성자</th>
							<th scope="col" class="last">작성일</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="result" items="${resultList}" varStatus="status">
							<tr>
								<td><c:out value="${fn:length(resultList) - status.index}" /></td>
								<td><c:out value="${result.year}" /></td>
								<td><a class="font-bold" href="javascript:fn_view('<c:out value="${result.uccSndCod}"/>');"><c:out value="${result.uccSndCod}" /></a></td>
								<td><c:out value="${result.uccSndNam}" /></td>
								<td><c:out value="${result.deptCnt}" /></td>
								<td><c:out value="${result.uumUsrNam}" /></td>
								<td class="last"><c:out value="${result.uccRgtMdh}" /></td>
							</tr>
						</c:forEach>
						<c:if test="${fn:length(resultList) == 0}">
							<tr class="last">
								<td class="last noDataList" colspan="8">
									등록된 서비스가 없습니다.
								</td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</div>
		</div>
		</form>