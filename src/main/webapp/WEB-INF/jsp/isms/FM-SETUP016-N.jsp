<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script type="text/javascript">
function fn_regist_Restde(){
	document.listForm.action = "FM-SETUP016_RW.do";
	document.listForm.submit();
}

function fn_mnu_select(id) {
	document.listForm.ummMnuKey.value = id;
   	document.listForm.action = "FM-SETUP016_RW.do";
   	document.listForm.submit();
}

function fn_prt_list(){
   	document.listForm.action = "FM-SETUP016.do";
   	document.listForm.submit();
}
</script>
<div>
	<article id="content-box">
		<c:import url="/titlebar.do" />
		<form name="listForm" action="FM-SETUP016.do" method="post">
		<input type="hidden" id="ummMnuKey" name="ummMnuKey" />
		<input type="hidden" id="ummMnuPrt" name="ummMnuPrt" value="${paramMap.ummMnuPrt}" />
		<input type="hidden" id="ummMnuPrtNam" name="ummMnuPrtNam" value="${paramMap.ummMnuPrtNam}" />
		<input type="hidden" id="searchCondition" name="searchCondition" value="${paramMap.searchCondition}" />
		<div class="contents">
			<div class="title"><c:out value="${paramMap.ummMnuPrtNam}" /></div>
			<div class="talbeArea">
				<table summary="메뉴관리 현황을 번호, 메뉴명, 메뉴 URL, 순서, 사용유무, 작성자, 작성일, 하위메뉴 등의 항목으로 설명하고있습니다.">
					<colgroup>
						<col width="5%">
						<col width="20%">
						<col width="*%">
						<col width="5%">
						<col width="10%">
						<col width="15%">
						<col width="10%">
					</colgroup>
					<thead>
						<tr>
							<th scope="col">번호</th>
							<th scope="col">메뉴명</th>
							<th scope="col">메뉴 URL</th>
							<th scope="col">순서</th>
							<th scope="col">사용유무</th>
							<th scope="col">작성자</th>
							<th scope="col" class="last">작성일</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="result" items="${list}" varStatus="status">
                        <tr>
                        	<td><c:out value="${fn:length(list) - status.index}"/></td>
                        	<td><a href="javascript:fn_mnu_select('<c:out value="${result.ummMnuKey}"/>');"><c:out value="${result.ummMnuNam}"/></a></td>
                        	<td><c:out value="${result.ummMnuUrl}"/></td>
                        	<td><c:out value="${result.ummOdrNum}"/></td>
                            <td>
                            	<c:choose>
                            		<c:when test="${result.ummUseYn eq 'Y'}">사용</c:when>
                            		<c:otherwise>미사용</c:otherwise>
                            	</c:choose>
                            </td>
                            <td><c:out value="${result.uumUsrNam}"/></td>
                            <td><c:out value="${result.ummRgtMdh}"/></td>
                        </tr>
                        </c:forEach>
						<c:if test="${fn:length(list) == 0}">
							<tr class="last">
								<td class="last noDataList" colspan="7">
									등록된 하위메뉴가 없습니다.
								</td>
							</tr>
						</c:if>
					</tbody>
				</table>
				<div class="bottomBtnArea">
					<ul class="btnList">
						<li>
							<button onclick="fn_prt_list(); return false;">
								상위메뉴
							</button>
							<button onclick="fn_regist_Restde(); return false;">
								<span class="icoAdd"></span>등록
							</button>
						</li>
					</ul>
				</div>
			</div>
		</div>
		</form>
	</article>
</div>
