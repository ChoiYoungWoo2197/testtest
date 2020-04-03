<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
<%@ include file="/WEB-INF/include/contents.jsp"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<style type="text/css">
#ui-datepicker-div { z-index: 999 !important; }
</style>
<script type="text/javascript">
	$(document).ready(function() {
		stOrgList();

		$(".cal").datepicker({
			changeMonth: true,
	        changeYear: true,
	        showButtonPanel: true
		});
		$(".btnCal").click(function(){
			$(this).prev().focus();
	     });
	});

	function workPopup(wrkKey, trcKey){
		window.open("","workPopup","width=730, height=900, menubar=no, location=no, status=no,scrollbars=yes");
		$("#utwWrkKey").val(wrkKey);
		$("#utwTrcKey").val(trcKey);
		document.workPopupForm.submit();
	}

	function search(){
		document.listForm.pageIndex.value = 1;
		document.listForm.action = "FM-SETUP010.do";
		document.listForm.submit();

	}

	function replaceAll(str,ori,rep){
		return str.split(ori).join(rep);
	}

	function excelDown(){
		if ( !$("#strDat").val()) {
			alert("검색 시작일을 지정 해주세요.");
			return false;
		}
		document.listForm.action = "${pageContext.request.contextPath}/excel/FM-SETUP010.do";
	   	document.listForm.submit();
	}

	function fn_egov_pageview(pageNo) {
		document.listForm.pageIndex.value = pageNo;
		document.listForm.action = "FM-SETUP010.do";
		document.listForm.submit();
	}
</script>
</head>
<body>
	<c:import url="/titlebar.do" />
	<form name="listForm" action="FM-SETUP010.do" method="post">
		<div class="search">
			<div class="defalt">
		        <fieldset class="searchForm">
		            <legend>기본검색</legend>
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
						<li style="width: 424px"><span class="title"><label for="strDat">기간</label></span>
							<input type="text" id="strDat" name="strDat" class="inputText cal" style="width: 100px;" value="${searchVO.strDat}"  />
							<button class="btnCal" type="button"><span class="icoCal"><em>달력</em></span></button>
							~
							<input type="text" id="endDat" name="endDat" class="inputText cal" style="width: 100px;" value="${searchVO.endDat}"  />
							<button class="btnCal" type="button"><span class="icoCal"><em>달력</em></span></button>
						</li>
						<li>
		                    <span class="title"><label for="workerName">담당자</label></span>
		                    <input id="workerName" name="workerName" class="inputText wdShort" type="text" value="${searchVO.workerName}"  title="담당자명" placeholder="담당자명"/>
		                </li>

		            </ul>
		            <button onclick="search(); return false;" class="btnSearch">조건으로 검색</button>
		        </fieldset>
	    	</div>
		</div>
		<div class="contents">
			<div class="topBtnArea">
				<ul class="btnList">
					<li>
						<button onclick="excelDown(); return false;"><span class="icoExl"></span>엑셀다운로드</button>
					</li>
				</ul>
			</div>
			<div class="talbeArea">
				<table summary="로그인이력 현황을 이름, 로그인IP, 로그인 일시 등의항목으로설명하고있습니다.">
					<colgroup>
						<col width="25%">
						<col width="*%">
						<col width="25%">
					</colgroup>
					<thead>
						<tr>
							<th scope="col">이름</th>
							<th scope="col">로그인IP</th>
							<th scope="col" class="last">로그인 일시</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="result" items="${resultList}" varStatus="status">
							<tr>
								<td><c:out value="${result.ulmLinId}" /></td>
								<td><c:out value="${result.ulmLinIp}" /></td>
								<td class="last"><c:out value="${result.ulmRgtMdh}" /></td>
							</tr>
						</c:forEach>
						<c:if test="${fn:length(resultList) == 0}">
							<tr class="last">
								<td class="last noDataList" colspan=3>로그이력이 없습니다. </td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</div>
			<div class="paging">
				<ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="fn_egov_pageview" />
				<input name="pageIndex" type="hidden"
					value="<c:out value='${searchVO.pageIndex}'/>" />
			</div>
		</div>
	</form>
</body>
</html>