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
	<meta charset="utf-8">
	<title></title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="description" content="">
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<link rel="stylesheet" type="text/css" href="/common/css/reset.css"/>
	<link rel="stylesheet" type="text/css" href="/common/css/common.css"/>
	<link rel="stylesheet" type="text/css" href="/common/css/pop.css"/>
	<link rel="stylesheet" type="text/css" href="/common/css/font-awesome.min.css"/>
	<link rel="stylesheet" type="text/css" href="/common/css/tailwind.min.css"/>

	<style>
		#wrap.pop, .borderBox {
			width: 1024px;
			margin-left: auto;
			margin-right: auto;
		}

		#wrap.pop article.cont {
			padding-top: 3rem;
		}
		.category-section {
			margin-bottom: 3rem;
			padding-bottom: 2rem;
			border-bottom: 1px solid #ccc;
		}
		.category-section > h1 {
			font-size: 24px;
			font-weight: bold;
			margin-bottom: 1rem;
		}
		.task-section {
			margin-bottom: 2rem;
		}
		.task-section > h2 {
			font-size: 16px;
			font-weight: bold;
			margin-bottom: 0.5rem;
		}
		.talbeArea table td.text-left {
			text-align: left;
		}
		.empty-value {
			color: #ccc;
			display: inline-block;
			min-height: 1rem;
		}
		.empty-value.comment::before {
			content: "정보 없음";
		}
		.talbeArea td {
			padding: 8px !important;
		}
	</style>

	<script type="text/javascript" src="/common/js/jquery.js"></script>
	<script type="text/javascript" src="/common/js/common.js?v=20180321"></script>
</head>
<body>

	<div id="wrap" class="pop">
		<header>
			<div class="borderBox"></div>
			<h1>"${protectionMeasure.title}" 기반시설 보호대책 작업표</h1>
		</header>
		<article class="cont" id="content-box">

			<c:forEach items="${rootNode.children}" var="categoryNode">

			<div class="category-section">
				<h1 class="border-b"><i class="fa fa-folder-o" aria-hidden="true"></i> ${categoryNode.data.title}</h1>

				<c:choose>
					<c:when test="${not empty categoryNode.children}">

						<c:forEach items="${categoryNode.children}" var="taskNode" varStatus="taskVarStatus">

						<fmt:parseDate value='${taskNode.data.startedAt}' var='startedAt' pattern="yyyy-MM-dd H:m" />
						<fmt:parseDate value='${taskNode.data.endedAt}' var='endedAt' pattern="yyyy-MM-dd H:m" />

						<div class="task-section">
							<h2><i class="fa fa-check-square-o" aria-hidden="true"></i> ${taskNode.data.title}</h2>

							<div class="talbeArea">

								<table class="task-info-table mb-5">
									<thead>
									<tr>
										<th width="100">과제상태</th>
										<th width="150">과제 시작일</th>
										<th width="150">과제 종료일</th>
										<th width="*">예산</th>
										<th width="50%">대상시설</th>
									</tr>
									</thead>
									<tbody>
									<tr>
										<td>
											${taskNode.data.status.title}
										</td>
										<td>
											<fmt:formatDate value="${startedAt}" pattern="YYYY년 MM월 dd일"/>
											<c:if test="${empty startedAt}"><span class="empty-value comment"></span></c:if>
										</td>
										<td>
											<fmt:formatDate value="${endedAt}" pattern="YYYY년 MM월 dd일"/>
											<c:if test="${empty endedAt}"><span class="empty-value comment"></span></c:if>
										</td>
										<td>
											${taskNode.data.budget}
											<c:if test="${empty taskNode.data.budget}"><span class="empty-value comment"></span></c:if>
										</td>
										<td>
											${taskNode.data.targetFacility}
											<c:if test="${empty taskNode.data.targetFacility}"><span class="empty-value comment"></span></c:if>
										</td>
									</tr>

									</tbody>
								</table>

								<table class="task-info-table mb-5">
									<thead>
									<tr>
										<th width="50%">목적</th>
										<th width="50%">성과평가지표</th>
									</tr>
									</thead>
									<tbody>
									<tr>
										<td class="text-left">
											${taskNode.data.purpose}
											<c:if test="${empty taskNode.data.purpose}"><span class="empty-value comment"></span></c:if>
										</td>
										<td class="text-left">
											${taskNode.data.evaluationIndex}
											<c:if test="${empty taskNode.data.evaluationIndex}"><span class="empty-value comment"></span></c:if>
										</td>
									</tr>

									</tbody>
								</table>


								<table class="task-content-table mb-5">
									<tr>
										<th>중점과제 내용</th>
									</tr>
									<tr>
										<td class="text-left" style="min-height: 3rem">
											${taskNode.data.taskContent}
											<c:if test="${empty taskNode.data.taskContent}"><span class="empty-value comment"></span></c:if>
										</td>
									</tr>
									<%--
									<c:if test="${not empty taskNode.children}">
										<c:forEach items="${taskNode.children}" var="microTaskNode" varStatus="microTaskVarStatus">
											<tr>
												<td>${microTaskVarStatus.index + 2}</td>
												<td class="text-left">
													${microTaskNode.data.taskContent}
													<c:if test="${empty microTaskNode.data.taskContent}"><span class="empty-value comment"></span></c:if>
												</td>
											</tr>
										</c:forEach>
									</c:if>
									--%>
								</table>

								<table class="task-file-table">
									<tr>
										<th colspan="4">중점과제 증적파일</th>
									</tr>

									<c:choose>
										<c:when test="${not empty taskNode.data.files}">

											<c:forEach items="${taskNode.data.files}" var="file" varStatus="taskFileVarStatus">

												<fmt:parseDate value='${file.umf_rgt_mdh}' var='fileCreatedAt' pattern="yyyy-MM-dd H:m" />

												<tr>
													<td width="50">${taskFileVarStatus.index + 1}</td>
													<td class="text-left">
														<a onclick="sendFileRequest(${file.umf_fle_key})" href="#">${file.umf_con_fnm}</a>
													</td>
													<td width="200">${file.wrk_name}</td>
													<td width="200">
														<fmt:formatDate value="${fileCreatedAt}" pattern="YYYY년 MM월 dd일"/>
													</td>
												</tr>

											</c:forEach>

										</c:when>
										<c:otherwise>
											<td colspan="2" class="text-left"><span class="empty-value comment"></span></td>
										</c:otherwise>
									</c:choose>
								</table>

							</div>

						</div>

						</c:forEach>

					</c:when>
					<c:otherwise>
						<p class="empty-value">등록된 중점과제가 없습니다.</p>
					</c:otherwise>
				</c:choose>

			</div>
			</c:forEach>

		</article>
	</div>

</body>
</html>