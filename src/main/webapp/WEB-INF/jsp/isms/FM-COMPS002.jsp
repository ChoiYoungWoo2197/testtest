<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib  prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<script type="text/javascript">
function fn_search(){
	if($("#stndNam").val()==""){
		alert("검색어를 입력해 주세요.");
		return;
	}
	document.listForm.pageIndex.value = 1;
   	document.listForm.submit();
}

function fn_pageview(pageNo){
	document.listForm.pageIndex.value = pageNo;
	document.listForm.action = "FM-COMPS002.do";
   	document.listForm.submit();
}

function fn_regist_Restde(){
	document.listForm.action = "FM-COMPS002_RW.do";
	document.listForm.submit();
}

function fn_stnd_select(id) {
	document.listForm.stndCod.value = id;
   	document.listForm.action = "FM-COMPS002_RW.do";
   	document.listForm.submit();
}
</script>
</head>
<body>
<form name="listForm" action="FM-COMPS002.do" method="post">
<input type="hidden" id="stndCod" name="stndCod">
<input type="hidden" id="pageIndex" name="pageIndex">
<input type="hidden" name="selectedId">
	<p class="history"><a href="/comps/FM-COMPS001.do">컴플라이언스관리</a><span>&gt;</span>컴플라이언스 등록</p>
	<div class="conttitle">컴플라이언스 등록</div>
	<div class="search">
	    <div class="defalt">
	        <fieldset class="searchForm">
	            <legend>표준명</legend>
	            <ul class="defalt">
	                <li>
	                	<span class="title"><label for="sel01">표준명</label></span>
	                    <input type="text" id="stndNam" class="inputText" name="stndNam" value="${searchVO.stndNam}" onkeydown="javascript:if(event.keyCode==13){return false;}" title="검색어 입력" placeholder="검색어를 입력하세요.">
	                </li>
	            </ul>
	            <button onclick="fn_search();" class="btnSearch">검색</button>
	        </fieldset>
	    </div>
	</div>
	<br/>
	<div class="contents">
		<div class="talbeArea">
			<table summary="컴플라이언스 등록">
				<colgroup>
					<col width="5%">
					<col width="15%">
					<col width="*">
					<col width="15%">
					<col width="15%">
					<col width="8%">
				</colgroup>
				<caption>컴플라이언스 등록 현황</caption>
				<thead>
				    <tr>
				        <th scope="col">번호</th>
				        <th scope="col">표준 코드</th>
				        <th scope="col">표준명</th>
				        <th scope="col">작성자</th>
				        <th scope="col">작성일</th>
				        <th scope="col">사용유무</th>
				    </tr>
				</thead>
				<tbody>
				<c:forEach var="result" items="${list}" varStatus="status">
				<tr>
					<td><c:out value="${fn:length(list) - status.index}"/></td>
					<td><c:out value="${result.uccSndCod}"/></td>
					<td class="fontLeft"><a href="javascript:fn_stnd_select('<c:out value="${result.uccSndCod}"/>')"><c:out value="${result.uccSndNam}"/></a></td>
					<td><c:out value="${result.uumUsrNam}"/></td>
					<td><c:out value="${result.uccRgtMdh}"/></td>
					<td class="last">
                       <c:choose>
                       	<c:when test="${result.uccUseYn eq 'Y'}">사용</c:when>
                       	<c:otherwise>미사용</c:otherwise>
                       </c:choose>
					</td>
				</tr>
				</c:forEach>
		     	<c:if test="${fn:length(list) == 0}">
					<tr class="last">
						<td class="last noDataList" colspan="6">
							데이터가 존재하지 않습니다.
						</td>
					</tr>
				</c:if>
				</tbody>
			</table>
	    </div>
	    <div class="paging">
	       <ui:pagination paginationInfo = "${paginationInfo}"	type="image" jsFunction="fn_pageview"/>
	    </div>
	    <div class="bottomBtnArea">
			<ul class="btnList">
				<li>
					<button onclick="fn_regist_Restde(); return false;"><span class="icoAdd"></span>글쓰기</button>
				</li>
			</ul>
		</div>
	</div>
</form>
</body>
</html>