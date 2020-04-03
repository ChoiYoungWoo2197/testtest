<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script type="text/javascript">
function fn_regist_Restde(){
	document.listForm.action = "FM-SETUP018_RW.do";
	document.listForm.submit();
}

function fn_cod_select(id) {
	document.listForm.uccSndCod.value = id;
   	document.listForm.action = "FM-SETUP018_RW.do";
   	document.listForm.submit();
}

function fn_list(){
   	document.listForm.action = "FM-SETUP018.do";
   	document.listForm.submit();
}
</script>
<div>
	<c:import url="/titlebar.do" />
	<form name="listForm" action="FM-SETUP018.do" method="post">
	<input type="hidden" name="searchKeyword" value="${paramMap.searchKeyword}"/>
	<input type="hidden" name="searchCondition" value="${paramMap.searchCondition}"/>
	<input type="hidden" id="uccFirCod" name="uccFirCod" value="${paramMap.uccFirCod}">
	<input type="hidden" id="uccFirNam" name="uccFirNam" value="${paramMap.uccFirNam}">
	<input type="hidden" name="uccSndCod"/>
	<div class="contents">
		<div class="title">[<c:out value="${paramMap.uccFirNam}" />]</div>
		<div class="talbeArea">
			<table summary="공통코드 관리">
				<colgroup>
					<col width="8%">
					<col width="20%">
					<col width="*%">
					<col width="10%">
					<col width="15%">
					<col width="15%">
				</colgroup>
				<thead>
					<tr>
						<th scope="col">번호</th>
						<th scope="col">코드</th>
						<th scope="col">코드명</th>
						<th scope="col">사용유무</th>
						<th scope="col">작성자</th>
						<th scope="col" class="last">작성일</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="result" items="${list}" varStatus="status">
                       <tr>
                       	<td><c:out value="${fn:length(list) - status.index}"/></td>
                       	<td><c:out value="${result.uccSndCod}"/></td>
                       	<td><a href="javascript:fn_cod_select('<c:out value="${result.uccSndCod}"/>')"><c:out value="${result.uccSndNam}"/></a></td>
                       	<td><c:out value="${result.uccUseYn}"/></td>
                       	<td><c:out value="${result.uumUsrNam}"/></td>
                       	<td class="last"><c:out value="${result.uccRgtMdh}"/></td>
                       </tr>
                       </c:forEach>
					<c:if test="${fn:length(list) == 0}">
						<tr class="last">
							<td class="last noDataList" colspan="7">
								등록된 공통코드가 없습니다.
							</td>
						</tr>
					</c:if>
				</tbody>
			</table>
			<div class="bottomBtnArea">
				<ul class="btnList">
					<li>
						<button onclick="fn_list(); return false;"><span class="icoList"></span>대분류코드</button>
						<button onclick="fn_regist_Restde(); return false;"><span class="icoAdd"></span>등록	</button>
					</li>
				</ul>
			</div>
		</div>
	</div>
	</form>
</div>
