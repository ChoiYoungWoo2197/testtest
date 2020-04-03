<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
<%@ include file="/WEB-INF/include/contents.jsp"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<script type="text/javascript">
	$(function() {
		stOrgList();
	});

	function workPopup(wrkKey, trcKey){
		window.open("","workPopup","width=730, height=900, menubar=no, location=no, status=no,scrollbars=yes");
		$("#utwWrkKey").val(wrkKey);
		$("#utwTrcKey").val(trcKey);
		document.workPopupForm.submit();
	}

	function excelDown(){
		document.listForm.action = "${pageContext.request.contextPath}/excel/FM-SETUP011.do";
	   	document.listForm.submit();
	}

	function search(){
		document.listForm.action = "FM-SETUP011.do";
		document.listForm.submit();
	}

	function replaceAll(str,ori,rep){
		return str.split(ori).join(rep);
	}

	function fn_egov_pageview(pageNo) {
		document.listForm.pageIndex.value = pageNo;
		document.listForm.action = "FM-SETUP011.do";
		document.listForm.submit();
	}
</script>
</head>
<body>
	<c:import url="/titlebar.do" />
	<form name="listForm" action="FM-SETUP011.do" method="post">
		<div class="search">
			<div class="defalt">
		        <fieldset class="searchForm">
		        	<legend>검색 조건</legend>
		            <ul class="detail newField">
		            	<li>
							<span class="title"><label for="stOrg">본부</label></span>
							<sb:select name="stOrg" type="stOrg" value="${searchVO.stOrg}" forbidden="true" allText="본부전체" />
						</li>
						<li>
							<span class="title"><label for="hqOrg">그룹,담당</label></span>
							<sb:select name="hqOrg" type="hqOrg" value="${searchVO.hqOrg}" forbidden="true" allText="그룹,담당전체" />
						</li>
		                <li>
		                    <span class="title"><label for="gpOrg">팀</label></span>
		                    <sb:select name="gpOrg" type="gpOrg" value="${searchVO.gpOrg}" forbidden="true" allText="팀전체" />
		                </li>
		            	<li>
		                	<span class="title"><label for="service">서비스</label></span>
		                    <sb:select name="service" type="S" value="${searchVO.service}" forbidden="true" />
		                </li>
		                <li>
		                    <span class="title"><label for="workerName">담당자</label></span>
		                    <input id="workerName" name="workerName" class="inputText wdShort" type="text" value="${searchVO.workerName}"  title="담당자명" placeholder="담당자명"/>
		                </li>
						<li></li>
		            </ul>
		            <button onclick="search();" class="btnSearch" type="button">조건으로 검색</button>
		        </fieldset>
	    	</div>
		</div>
		<div class="contents">
			<div class="topBtnArea">
			<ul class="btnList">
				<li><button onclick="excelDown(); return false;" type="button"><span class="icoExl"></span>엑셀다운로드</button></li>
				</ul>
			</div>
			<div class="talbeArea">
				<table summary="계정이력 현황을 설명하고있습니다.">
					<colgroup>
						<col width="10%">
						<col width="10%">
						<col width="15%">
						<col width="10%">
						<col width="*%">
						<col width="15%">
						<col width="15%">
						<col width="15%">
					</colgroup>
					<thead>
						<tr>
							<th scope="col">사번</th>
							<th scope="col">이름</th>
							<th scope="col">부서</th>
							<th scope="col">직급</th>
							<th scope="col">이메일</th>
							<th scope="col">휴대폰 번호</th>
							<th scope="col">자리번호</th>
							<th scope="col" class="last">수정 날짜</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="result" items="${resultList}" varStatus="status">
							<tr>
								<td><c:out value="${result.uulUsrId}" /></td>
								<td><c:out value="${result.uulUsrNam}" /></td>
								<td><c:out value="${result.uulDepCod}" /></td>
								<td><c:out value="${result.uulPosCod}" /></td>
								<td><c:out value="${result.uulMalAds}" /></td>
								<td><c:out value="${result.uulCelNum}" /></td>
								<td><c:out value="${result.uulTelNum}" /></td>
								<td class="last"><c:out value="${result.uulRgtMdh}" /></td>
							</tr>
						</c:forEach>
						<c:if test="${fn:length(resultList) == 0}">
							<tr class="last">
								<td class="last noDataList" colspan=8>
									계정이력이 없습니다.
								</td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</div>
			<div class="paging">
				<ui:pagination paginationInfo="${paginationInfo}" type="image"
					jsFunction="fn_egov_pageview" />
				<input name="pageIndex" type="hidden"
					value="<c:out value='${searchVO.pageIndex}'/>" />
			</div>
		</div>
	</form>
</body>
</html>