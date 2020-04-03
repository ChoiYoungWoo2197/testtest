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
function fn_node_list(kind, sort) {
	document.listForm.uccKind.value = kind;
	document.listForm.uccSort.value = sort;
	document.listForm.uccFirCod.value = "STND";
	document.listForm.action = "FM-SETUP018_N.do";
	document.listForm.submit();
}
</script>
<style>
.talbeArea th a:after { font-weight: normal; margin-left: 5px; font-size: 10px; }
.talbeArea th.asc a:after { content:"▲" }
.talbeArea th.desc a:after { content:"▼" }
</style>
<div>
	<c:import url="/titlebar.do" />
	<form name="listForm" action="FM-SETUP018.do" method="post">
	<input type="hidden" name="searchKeyword" value="${paramMap.searchKeyword}"/>
	<input type="hidden" name="searchCondition" value="${paramMap.searchCondition}"/>
	<input type="hidden" id="uccFirCod" name="uccFirCod" value="${paramMap.uccFirCod}">
	<input type="hidden" id="uccFirNam" name="uccFirNam" value="${paramMap.uccFirNam}">
	<input type="hidden" name="uccSndCod" />
	<input type="hidden" name="uccKind" value="${paramMap.uccKind}" />
	<input type="hidden" name="uccSort" value="${paramMap.uccSort}" />
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
						<th scope="col" class="<c:choose><c:when test="${paramMap.uccKind eq 'ucc_snd_cod' and paramMap.uccSort eq 'asc'}">asc</c:when><c:when test="${paramMap.uccKind eq 'ucc_snd_cod' and paramMap.uccSort eq 'desc'}">desc</c:when></c:choose>">
							<a href="javascript:fn_node_list('ucc_snd_cod', <c:choose><c:when test="${paramMap.uccKind eq 'ucc_snd_cod' and paramMap.uccSort eq 'asc'}">'desc'</c:when><c:otherwise>'asc'</c:otherwise></c:choose>)">코드</a>
						</th>
						<th scope="col" class="<c:choose><c:when test="${paramMap.uccKind eq 'ucc_snd_nam' and paramMap.uccSort eq 'asc'}">asc</c:when><c:when test="${paramMap.uccKind eq 'ucc_snd_nam' and paramMap.uccSort eq 'desc'}">desc</c:when></c:choose>">
							<a href="javascript:fn_node_list('ucc_snd_nam', <c:choose><c:when test="${paramMap.uccKind eq 'ucc_snd_nam' and paramMap.uccSort eq 'asc'}">'desc'</c:when><c:otherwise>'asc'</c:otherwise></c:choose>)">컴플라이언스명</a>
						</th>
						<th scope="col">사용유무</th>
						<th scope="col">작성자</th>
						<th scope="col" class="last <c:choose><c:when test="${paramMap.uccKind eq 'ucc_rgt_mdh' and paramMap.uccSort eq 'asc'}">asc</c:when><c:when test="${paramMap.uccKind eq 'ucc_rgt_mdh' and paramMap.uccSort eq 'desc'}">desc</c:when></c:choose>">
							<a href="javascript:fn_node_list('ucc_rgt_mdh', <c:choose><c:when test="${paramMap.uccKind eq 'ucc_rgt_mdh' and paramMap.uccSort eq 'asc'}">'desc'</c:when><c:otherwise>'asc'</c:otherwise></c:choose>)">작성일</a>
						</th>
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
						<button onclick="fn_regist_Restde(); return false;"><span class="icoAdd"></span>등록</button>
					</li>
				</ul>
			</div>
		</div>
	</div>
	</form>
</div>
