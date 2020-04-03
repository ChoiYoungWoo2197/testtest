<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/include/contents.jsp"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<link rel="stylesheet" href="/common/css/jstree/style.min.css">
	<style>
		.input-group {
			margin-bottom: 1rem;
		}
		.input-group .input-group__title {
			color: #888;
			margin-bottom: 0.5rem;
		}

		.card {
			border-top: 2px solid #ccc;
		}
		.card-header {
			padding: 1rem;
			background-color: #eee;
			overflow: auto;
		}
		.card-header > button {
			float: right;
		}
		.card-header > h1 {
			font-size: 14px;
			font-weight: bold;
			float: left;
		}
		.card-body {
			padding: 1rem 0;
		}
		th, td {
			padding: 0.5rem;
		}
		#protection-measure-task .inputText, #protection-measure-task .inputTextarea, #protection-measure-task .selectBox {
			width: 597px;
			padding: 0.5rem 10px;
		}
		.inputTextarea {
			border: 1px solid #ddd;
		}
		#content-box {
			margin-bottom: 100px;
		}

		.jstree-anchor, .jstree-anchor:link, .jstree-anchor:visited, .jstree-anchor:hover, .jstree-anchor:active {
			color: #999;
			font-weight: normal;
		}
		.jstree-anchor.permitted-node {
			color: #666;
			font-weight: bold;
		}

		.empty-value-color {
			color: #bbb !important;
		}
		.empty-table-message {
			text-align: center; height: 100px; line-height: 100px; color: #bbb !important;
		}
		.active-category {
			color: #ea002c;
		}
		.table-title {
			font-size: 16px;
			font-weight: bold;
			margin-top: 16px;
			margin-bottom: 8px;
		}
	</style>
	<script type="application/javascript" src="/common/js/jstree.js"></script>
	<script type="application/javascript" src="/common/js/vue.js"></script>
	<script type="application/javascript" src="/common/js/moment.js"></script>
	<script type="application/javascript" src="/common/js/helpers.js"></script>
	<script>
	function openPopUp(url) {
		var win = window.open(url, "TEST", "width=730, height=800, menubar=no, location=no, status=no, scrollbars=yes");
	}
	$(document).ready(function () {


	})
	</script>

</head>
<body>

<c:import url="/titlebar.do" />

<div class="search">
	<div class="defalt">

			<form action="/mwork/FM-MWORK021.do" method="get">
				<ul class="detail">
					<li>

						<span class="title" style="line-height: 30px"><label>기간</label></span>

						<select id="year-select" class="selectBox" name="year" style="width: 160px">
							<c:forEach varStatus="status" items="${mainCycleList}" var="manCyl">
								<option <c:if test="${selectedYear eq manCyl.year}">selected="selected"</c:if> value="${manCyl.year}">${manCyl.name}</option>
							</c:forEach>
						</select>

					</li>
				</ul>
				<button href="#none" class="btnSearch" style="height: 27px">검색</button>
			</form>

	    </div>
</div>

<div class="contents">

	<c:if test="${empty protectionMeasure}">

		<div class="talbeArea mt-5">
			<table>
				<tr>
					<td class="empty-table-message">생성된 보호대책 정보가 없습니다.</td>
				</tr>
			</table>
		</div>

	</c:if>

	<c:if test="${not empty protectionMeasure}">

	<div class="talbeArea mt-5">

		<table>
			<colgroup>
				<col width="100px">
				<col width="*">
				<col width="150px">
				<col width="300px">
			</colgroup>
			<tr>
				<th>제목</th>
				<td>${protectionMeasure.title}</td>
				<th>보호지침 가이드</th>
				<c:if test="${empty protectionMeasureGuideFile}">
					<td class="empty-value-color">보호지침 가이드 정보가 없습니다.</td>
				</c:if>
				<c:if test="${not empty protectionMeasureGuideFile}">
					<td>
						<a onclick="sendFileRequest(${protectionMeasureGuideFile.umf_fle_key})" href="#">
							${protectionMeasureGuideFile.umf_con_fnm}
						</a>
					</td>
				</c:if>
			</tr>
		</table>

	</div>

	<h1 class="table-title"><i class="fa fa-folder-o" aria-hidden="true"></i> 중점과제 분류</h1>
	<div class="talbeArea">
		<table>
			<colgroup>
				<col width="100px">
				<col width="*">
				<col width="150px">
			</colgroup>
			<tr>
				<th>번호</th>
				<th>분류명</th>
				<th>생성일</th>
			</tr>

			<c:if test="${empty categoryList}">
				<tr>
					<td colspan="3" class="empty-table-message">
						할당된 보호대책 중점과제 분류 정보가 없습니다.
					</td>
				</tr>
			</c:if>

			<c:forEach items="${categoryList}" var="category" varStatus="categoryStatus">
				<tr>
					<td>${categoryStatus.count}</td>
					<td style="text-align: left">
						<a class="<c:if test="${selectedCategory eq category.taskId}">active-category</c:if>" href="?year=${selectedYear}&category=${category.taskId}">${category.taskTitle}</a>
					</td>
					<td><fmt:formatDate value="${category.createdAt}" pattern="yyyy년 MM월 dd일" /></td>
				</tr>
			</c:forEach>

		</table>
	</div>

	<div style="position: relative; margin-top: 16px; margin-bottom: 8px">
		<h1 class="table-title" style="display: inline-block; margin-top: 0; margin-bottom: 0">
			<i class="fa fa-folder-o" aria-hidden="true"></i> 중점과제
		</h1>

<%--		<c:if test="${not empty selectedCategory}">--%>
<%--			<button id="create-task-btn" style="float: right"--%>
<%--					onclick="openPopUp('/mwork/FM-MWORK021_CREATE_POPUP.do?protectionMeasure=${protectionMeasure.id}&category=${selectedCategory}'); return false;">--%>
<%--				<span class="icoAdd"></span>중점과제 생성</button>--%>
<%--		</c:if>--%>
	</div>

	<div class="talbeArea">
		<table>
			<colgroup>
				<col width="100px">
				<col width="*">
				<col width="150px">
				<col width="150px">
				<col width="150px">
			</colgroup>
			<tr>
				<th>번호</th>
				<th>중점과제 제목</th>
				<th>시작일</th>
				<th>종료일</th>
				<th>진행상태</th>
			</tr>

			<c:choose>
				<c:when test="${not empty selectedCategory and empty taskList}">
					<tr>
						<td colspan="5" class="empty-table-message">할당된 보호대책 중점과제 정보가 없습니다.</td>
					</tr>
				</c:when>
				<c:when test="${empty selectedCategory}">
					<tr>
						<td colspan="5" class="empty-table-message">중점과제 분류를 클릭하세요.</td>
					</tr>
				</c:when>
			</c:choose>

			<c:forEach items="${taskList}" var="task" varStatus="taskStatus">
				<tr>
					<td>${taskStatus.count}</td>
					<td>
						<a onclick="openPopUp('/mwork/FM-MWORK021_VIEW_POPUP.do?task=${task.id}'); return false;"
						   href="/mwork/FM-MWORK021_VIEW_POPUP.do?task=${task.id}">${task.title}</a>
					</td>
					<td><fmt:formatDate value="${task.startedAt}" pattern="yyyy년 MM월 dd일" /></td>
					<td><fmt:formatDate value="${task.endedAt}" pattern="yyyy년 MM월 dd일" /></td>
					<td>${task.statusText}</td>
				</tr>
			</c:forEach>

		</table>
	</div>

	</c:if>

</div>

</body>
</html>