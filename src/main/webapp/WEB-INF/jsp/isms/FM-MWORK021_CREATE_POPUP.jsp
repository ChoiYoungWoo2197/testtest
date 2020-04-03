<%@ page import="com.uwo.isms.domain.enums.ProtectionMeasureStatus" %>
<%@ page import="com.uwo.isms.domain.enums.ProtectionMeasureTaskStatus" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ce" uri="http://ckeditor.com"%>
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
	<link rel="stylesheet" type="text/css" href="/common/css/jquery-ui.css"/>

	<style>
		#wrap.pop, .borderBox {
			width: 100%;
			margin-left: auto;
			margin-right: auto;
			padding-left: 0;
			padding-right: 0;
			left: 0 !important;
			right: 0 !important;
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
		}
		.empty-value.comment::before {
			content: "등록된 내용이 없습니다.";
		}
		.inputText {
			width: 100%;
		}
		article.cont {
			padding-top: 8px !important;
			padding-left: 16px !important;
			padding-right: 16px !important;
			padding-bottom: 70px !important;
		}
		#file-manager-vue {
			text-align: left;
		}
		.validate-error {
			outline: 1px solid crimson;
		}
	</style>

	<script type="text/javascript" src="/common/js/jquery.js"></script>
	<script type="text/javascript" src="/common/js/jquery-ui.js"></script>
	<script type="text/javascript" src="/common/js/vue.js"></script>
	<script type="text/javascript" src="/common/js/lodash.js	"></script>
	<script type="text/javascript" src="/common/js/common.js?v=20180321"></script>
	<%@ include file="/WEB-INF/include/vueComponents.jsp"%>
</head>
<body>

	<div id="wrap" class="pop">
		<header>
			<div class="borderBox"></div>
			<h1>기반시설 보호대책 중점과제</h1>
		</header>
		<div style="padding-left: 18px; padding-right: 18px; margin-top: 16px">
		</div>
		<article class="cont" id="content-box">

			<form id="task-form" action="/mwork/FM-MWORK021_STORE.do" method="post">
				<input type="hidden" name="protectionMeasureId" value="${protectionMeasureId}">
				<input type="hidden" name="parentNodeId" value="${categoryId}">

				<div class="talbeArea">

				<table>
					<colgroup>
						<col width="150px">
						<col width="*">
					</colgroup>
					<tr>
						<th>제목</th>
						<td>
							<input data-validate="" name="title" class="inputText" type="text" value="${task.title}">
						</td>
					</tr>
					<tr>
						<th>진행상태</th>
						<td>
							<select name="status" class="selectBox" style="width: 100%">
								<c:forEach items="<%=ProtectionMeasureTaskStatus.values()%>" var="pmStatus">
								<option <c:if test="${task.status.name() eq pmStatus.name()}">selected="selected"</c:if> value="${pmStatus.name()}">${pmStatus.title}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th>내용</th>
						<td>
							<textarea class="txtAreaType03" id="wrk_dtl" name="taskContent">${task.taskContent}</textarea>
							<ce:replace replace="taskContent" basePath="${pageContext.request.contextPath}/editor/ckeditor" ></ce:replace>
						</td>
					</tr>
					<tr>
						<th>과제시작일</th>
						<td>
							<input name="startedAt" data-datepicker class="inputText" type="text"
								   value="<fmt:formatDate value="${startedAt}" pattern="yyyy-MM-dd" />">
						</td>
					</tr>
					<tr>
						<th>과제종료일</th>
						<td>
							<input name="endedAt" data-datepicker class="inputText" type="text"
								   value="<fmt:formatDate value="${endedAt}" pattern="yyyy-MM-dd" />">
						</td>
					</tr>
					<tr>
						<th>목적</th>
						<td>
							<input name="purpose" class="inputText" type="text" value="${task.purpose}">
						</td>
					</tr>
					<tr>
						<th>성과평가지표</th>
						<td>
							<input name="evaluationIndex" class="inputText" type="text" value="${task.evaluationIndex}">
						</td>
					</tr>
					<tr>
						<th>예산</th>
						<td>
							<input name="budget" class="inputText" type="text" value="${task.budget}">
						</td>
					</tr>
					<tr>
						<th>대상시설</th>
						<td>
							<input name="targetFacility" class="inputText" type="text" value="${task.targetFacility}">
						</td>
					</tr>
					<tr>
						<th>증적파일</th>
						<td style="padding-left: 8px; text-align: left; color: #bbb">
							중점과제 생성 후 증적파일 등록 가능
<%--							<div id="file-manager-vue">--%>
<%--								<file-manager ref="fileManager" tbl-gbn="PTM" con-gbn="12" con-key="${task.id}"></file-manager>--%>
<%--							</div>--%>
						</td>
					</tr>

				</table>
			</div>

			</form>

			<div class="centerBtnArea bottomBtnArea">
				<button type="button" class="popClose"><span class="icoClose"><em>닫기</em></span></button>
				<ul class="btnList">

					<li>
						<button type="button" class="btnStrong2" id="saveTask"><span class="icoReDo"></span>저장하기</button>
					</li>

				</ul>
			</div>

		</article>
	</div>

	<script>

		window.onunload = refreshParent;
		function refreshParent() {
			window.opener.location.reload();
		}

		$(document).ready(function () {

			$(".popClose").on("click", function (event) {
				window.onunload = null;
				window.close();
			});

			$("[data-datepicker]").datepicker();

			$("#saveTask").on("click", function (event) {

				var isValidated = true;
				$("[data-validate]").each(function (index, element) {
					if ($(element).val() == "") {
						$(element).addClass("validate-error")
						isValidated = false;
					}
				});

				if (isValidated) {
					$("#task-form").submit();
				} else {
					alert("필수 정보를 입력하세요.")
				}

				return false;
			})

		})

	</script>

</body>
</html>