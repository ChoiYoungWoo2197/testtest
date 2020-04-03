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

<fmt:parseDate value="${task.createdAt}" pattern="yyyy-MM-dd HH:mm" var="createdAt"/>
<fmt:parseDate value="${task.updatedAt}" pattern="yyyy-MM-dd HH:mm" var="updatedAt"/>
<fmt:parseDate value="${task.startedAt}" pattern="yyyy-MM-dd HH:mm" var="startedAt"/>
<fmt:parseDate value="${task.endedAt}" pattern="yyyy-MM-dd HH:mm" var="endedAt"/>

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8">
	<title></title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="description" content="">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Cache-Control" content="no-cache"/>
	<meta http-equiv="Expires" content="0"/>
	<meta http-equiv="Pragma" content="no-cache"/>

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
	</style>

	<script type="text/javascript" src="/common/js/jquery.js"></script>
	<script type="text/javascript" src="/common/js/jquery-ui.js"></script>
	<script type="text/javascript" src="/common/js/vue.js"></script>
	<script type="text/javascript" src="/common/js/lodash.js	"></script>
	<script type="text/javascript" src="/common/js/common.js?v=20180321"></script>
	<script>
		$.ajaxSetup({ cache: false });
	</script>
	<%@ include file="/WEB-INF/include/vueComponents.jsp"%>
</head>
<body>

	<div id="wrap" class="pop">
		<header>
			<div class="borderBox"></div>
			<h1>기반시설 보호대책 중점과제</h1>
		</header>
		<div style="padding-left: 18px; padding-right: 18px; margin-top: 16px">
			<div style="float: left">
				중점과제 번호 : ${task.id}
			</div>
			<div style="float: right; color: #aaa">
				<span>생성일 : <fmt:formatDate value="${createdAt}" pattern="yyyy년 MM월 dd일" /></span>
			</div>
		</div>
		<article class="cont" id="content-box">

			<form id="task-form" action="/mwork/FM-MWORK021_UPDATE.do" method="post">
				<input type="hidden" name="id" value="${task.id}">

				<div class="talbeArea">

				<table>
					<colgroup>
						<col width="150px">
						<col width="*">
					</colgroup>
					<tr>
						<th>제목</th>
						<td>
							<input name="title" class="inputText" type="text" value="${task.title}">
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
						<td style="padding-left: 8px">
							<div id="file-manager-vue">
								<file-manager ref="fileManager" tbl-gbn="PTM" con-gbn="12" con-key="${task.id}"></file-manager>
							</div>
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

					<li>
						<button type="button" class="btnStrong" id="finishTask"><span class="icoSave"></span>저장 후 업무완료</button>
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
		
		function validate() {
			var regexDate = /^(19|20)\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[0-1])$/; 
			var regexNumber =  /^[0-9]*$/;
			
			if(!regexDate.test($("[name='startedAt']").val()) || !regexDate.test($("[name='endedAt']").val())) {
				alert('지원하지 않는 날짜 형식입니다. 다시 입력해주세요.');
				return false;
			}
			else if($("[name='startedAt']").val() > $("[name='endedAt']").val()) {
				alert('과제종료일은 시작일보다 빠를수는 없습니다. 다시 입력해주세요.');
				return false;
			}
			else if(!regexNumber.test($("[name='budget']").val())) {
				alert('예산은 숫자만 입력할수 있습니다. 다시 입력해주세요.');
				return false;
			}
			

			return true;
		}

		function saveTaskFile(fileNamagerVue) {
			if (fileNamagerVue.$refs.fileManager.isChanged()) {
				fileNamagerVue.$refs.fileManager.saveFiles();
			}
		}

		$(document).ready(function () {
			$(".popClose").on("click", function (event) {
				window.onunload = null;
				window.close();
			});

			$("[data-datepicker]").datepicker();

			var fileNamagerVue = new Vue({
				el: "#file-manager-vue"
			});

			$("#saveTask").on("click", function (event) {
				if(!validate()) {
					return false;
				}

				saveTaskFile(fileNamagerVue);

				$("#task-form").submit();
			});

			$("#finishTask").on("click", function (event) {		
				if(!validate()) {
					return false;
				}

				saveTaskFile(fileNamagerVue);

				$("[name='status']").val("<%=ProtectionMeasureStatus.FINISHED%>");
				$("#task-form").submit();
			});
		})

	</script>

</body>
</html>