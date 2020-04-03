<%@page import="java.util.List"%>
<%@page import="com.uwo.isms.domain.BoardVO"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ce" uri="http://ckeditor.com"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/WEB-INF/include/contents.jsp"%>
<script type="text/javaScript">
<c:if test="${authKey eq 'A' || usrKey eq vo.ubm_rgt_id}">
	$(document).ready(function() {
		var btns = '<li><button type="button" class="btnStrong" onclick="fn_egov_save();"><span class="icoRepair"></span>수정</button></li><li><button type="button" class="btnStrong" onclick="fn_egov_delete();"><span class="icoDel"></span>삭제</button></li>';
		$("#btnGroup").prepend(btns);
	});
	/* 글 삭제 function */
	function fn_egov_delete() {
		if (confirm("삭제하시겠습니까?")) {
			document.viewForm.action = "FM-BOARD003_D.do";
			document.viewForm.submit();
		}
	}

	/* 글 수정 function */
	function fn_egov_save() {
		frm = document.viewForm;
		frm.seq.value = ${vo.ubm_brd_key};
		frm.action = "FM-BOARD003_R.do";
		frm.submit();
	}
</c:if>

	/* 글 목록 화면 function */
	function fn_egov_selectList() {
		document.viewForm.action = "/board/FM-BOARD003.do";
		document.viewForm.submit();
	}

 	function getFile(key) {
		$("#downKey").val(key);
		document.fileDown.action = "../common/getFileDown.do";
		document.fileDown.action = "${pageContext.request.contextPath}/common/getFileDown.do";
		document.fileDown.submit();
	}

	function fn_egov_regist_Restde() {
		document.viewForm.action = "FM-BOARD003_RW.do";
		document.viewForm.submit();
	}
</script>
<div>
		<c:import url="/titlebar.do" />
		<form name="viewForm" method="post">
		<input type="hidden" name="seq" />
		<input type="hidden" name="ubm_brd_key" value="${vo.ubm_brd_key}"/>
		<div class="contents">
			<div class="talbeArea">
				<table summary="공지사항 제목과 작성자, 작성일 등의 정보를 설명하고있습니다.">
					<colgroup>
						<col width="15%">
						<col width="*">
						<col width="15%">
						<col width="20%">
					</colgroup>
					<caption>게시판 상세 정보</caption>
					<tbody>
						<tr>
							<th>제목</th>
							<td class="listTitle last" colspan="3"><c:out
									value="${vo.ubm_brd_tle}" /></td>
						</tr>
						<tr class="last">
							<th>작성자</th>
							<td class="fontLeft"><c:out value="${vo.ubm_rgt_id}" /></td>
							<th>등록일</th>
							<td class="fontLeft last"><c:out value="${vo.ubm_rgt_mdh}" /></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="listDetailArea">
				${vo.ubm_brd_cts}
				<c:if test="${fn:length(file) > 0 }">
				<div class="uplidFileList">
					<p class="titleArea">
						첨부파일
					</p>
					<ul class="listArea">
					<c:forEach var="result" items="${file}" varStatus="status">
						<li><a href="#none" onclick="getFile(<c:out value='${result.umf_fle_key}'/>)"><span class="icoDown"></span><c:out value="${result.umf_con_fnm}"/></a></li>
					</c:forEach>
					</ul>
				</div>
				</c:if>
			</div>
			<div class="bottomBtnArea">
				<ul class="btnList" id="btnGroup">
					<li>
						<button class="btnStrong" onclick="fn_egov_selectList();">
							<span class="icoList"></span>목록
						</button>
					</li>
				</ul>
			</div>
		</div>
		</form>
		<form action="" name="fileDown" id="fileDown" method="post">
    		<input type="hidden" name="downKey" id="downKey">
    		<input type="hidden" name="where" id="where" value="brd">
    	</form>
	</div>