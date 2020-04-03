<%@ page import="com.uwo.isms.domain.ProtectionMeasureVO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/include/contents.jsp"%>
<!DOCTYPE html>
<html lang="ko">
<head>
</head>
<body>

<c:import url="/titlebar.do" />

<div class="search">
	    <div class="defalt">
			<form action="/inspt/FM-INSPT005.do" method="get">
				<ul class="detail">
					<li>

						<span class="title" style="line-height: 30px"><label>기간</label></span>

						<select class="selectBox" name="year" style="width: 160px">
							<c:forEach varStatus="status" items="${mainCycleList}" var="cycle">
								<option <c:if test="${selectedYear == cycle.year}">selected="selected"</c:if> value="${cycle.year}">${cycle.name}</option>
							</c:forEach>
						</select>

					</li>
				</ul>
				<button href="#none" class="btnSearch" style="height: 27px">검색</button>
			</form>
	    </div>
	</div>

<div class="contents">

	<form id="protection-measure-create-form" method="post" action="/inspt/FM-INSPT005_PTM_STORE.do" enctype="multipart/form-data">
		<input type="hidden" name="season" value="${selectedYear}">

		<div class="talbeArea mt-5 mb-5">

			<table class="talbeArea">

				<tr>
					<th>보호지침 파일</th>
					<td class="fontLeft">
						<input name="uploadPath" type="file">
					</td>
				</tr>

			</table>

		</div>

		<div class="text-right">
			<button type="submit" class="btnStrong"><span class="icoAdd"></span> ${selectedYear + 1}년도 보호대책 생성</button>
		</div>

	</form>

</div>

</body>
</html>