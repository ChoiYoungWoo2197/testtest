<%@page import="com.ckeditor.CKEditorConfig"%>
<%@page import="com.uwo.isms.domain.BoardVO"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="validator"	uri="http://www.springmodules.org/tags/commons-validator"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="ce" uri="http://ckeditor.com"%>
<%
	CKEditorConfig setting = new CKEditorConfig();
%>
<%@ include file="/WEB-INF/include/contents.jsp"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<c:set var="registerFlag" value="${empty vo.ubm_brd_tle ? '등록' : '수정'}" />
<c:set var="col" value="${registerFlag == '등록' ? '5' : '4'}" />
<script type="text/javaScript">
/* 글 목록 화면 function */
function fn_egov_selectList() {
   	document.detailForm.action = "FM-BOARD003.do";
   	document.detailForm.submit();
}

/* 글 삭제 function */
function fn_egov_delete() {
   	document.detailForm.action = "FM-BOARD003_D.do";
   	document.detailForm.submit();
}

/* 글 등록 function */
function fn_egov_save() {
	frm = document.detailForm;

	var tle = $("#ubm_brd_tle").val();

	if(tle == ""){
		alert("제목을 입력하세요");
		return;
	}
	if(CKEDITOR.instances.ubm_brd_cts.getData() == ''){
		alert("내용을 입력하세요");
		return;
	}

   	frm.action = "<c:url value="${registerFlag == '등록' ? 'FM-BOARD003_W.do' : 'FM-BOARD003_U.do'}"/>";
	frm.submit();
}

var fileCnt = 0;

function addFile(){
	 /* var oRow = fileTbl.insertRow();
	 var oCell1 = oRow.insertCell();
	 var oCell2 = oRow.insertCell();
	 var oCell3 = oRow.insertCell();
	 oCell3.innerHTML="<input type='button' value='추가' onclick='addFile()'>";
	 oCell2.innerHTML="<input type='file' name='filePath["+oRow.rowIndex+"]' style='width:500px'>";
	 oCell3.innerHTML="<input type='image' src='../images/btn_del.png' value='삭제' onclick='delFile("+oRow.rowIndex+")'>"; */
	 fileCnt++;
	$("#gBtn2").append("<li style='margin-left: 10px;'><input type='file' name='filePath["+fileCnt+"]' style='width:500px'></li>");

}
function delFile(no){
	 fileTbl.deleteRow(no);
}

function delFile_U(key){
	if(confirm("해당 파일을 삭제하시겠습니까?")){
		$.ajax({
			url		: "${pageContext.request.contextPath}/common/delFile.do",
			type		: "post",
			data		: {"key":key},
			dataType	: "json",
			success	: function(e){
				alert("삭제되었습니다.");
			 },
			 error : function(){
				alert("실패");
				//
			 },
			 complete : function(){
				frm = document.reloadForm;
				frm.seq.value = ${vo.ubm_brd_key};
				frm.action = "FM-BOARD003_R.do";
				frm.submit();
			 }
		 });
	}
}
function addFileUpdate(seq){
	frm = document.detailForm;
   	frm.action = "FM-BOARD003_U.do";
	frm.submit();
}
</script>
</head>
<body>
	<c:import url="/titlebar.do" />
	<div class="contents">
	<form:form commandName="vo" name="detailForm" enctype="multipart/form-data">
		<form:hidden path="ubm_brd_key"/>
		<div class="talbeArea">
			<table summary="게시판 제목과 노출여부, 권한 등의 정보를 설정할 수 있습니다.">
				<colgroup>
					<col width="15%">
					<col width="*">
					<col width="15%">
					<col width="20%">
				</colgroup>
				<caption>게시판 작성 정보</caption>
				<tbody>
					<tr>
						<th><label for="txt01"> * 제목</label></th>
						<td class="listTitle">
							<form:input path="ubm_brd_tle" maxlength="80" size="80" class="inputText"/>
							&nbsp;<form:errors path="ubm_brd_tle" />
						</td>
						<th>공지 여부</th>
						<td class="last">
							<form:checkbox path="ubm_ntc_yn" value="Y" />
									&nbsp;<form:errors path="ubm_ntc_yn" />
						</td>
					</tr>
					<tr class="last">
						<th>권한 설정</th>
						<td class="fontLeft last" colspan="3">
							<ul class="listPermission">
								<li>
									<label for="ubm_div_cod"><strong>서비스 권한</strong></label>
									<form:select path="ubm_div_cod" class="selectBox">
										<option value="A"}>전체</option>
										<c:forEach var="divResult" items="${divlist }" varStatus="status">
										<option value="${divResult.uomSvcCod }" <c:if test="${vo.ubm_div_cod == divResult.uomSvcCod}">selected="selected"</c:if>><c:out value="${divResult.uomSvcNam }"/></option>
										</c:forEach>
									</form:select>
								</li>
								<li>
									<label for="ubm_sek_ath"><strong>조회 권한</strong></label>
									<form:select path="ubm_sek_ath" class="selectBox">
										<form:option value="1">통합관리자</form:option>
										<form:option value="2">결재관리자</form:option>
										<form:option value="3">업무담당자</form:option>
									</form:select>
								</li>
                                <li>
                                    <label for="ubm_dwn_ath"><strong>다운 권한</strong></label>
                                    <form:select path="ubm_dwn_ath" class="selectBox">
										<form:option value="1">통합관리자</form:option>
										<form:option value="2">결재관리자</form:option>
										<form:option value="3">업무담당자</form:option>
									</form:select>
                                </li>
							</ul>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
        <div class="listDetailArea write">
	        <div class="uplidFileList">
	        	<p class="titleArea">첨부파일 : <strong>${fn:length(file)}</strong></p>
	            <div class="topBtnArea">
                       <ul class="btnList">
                           <li><button onclick="addFile(); return false;"><span class="icoUpload"></span>파일 추가</button></li>
                       </ul>
                   </div>
	            <ul class="listArea" id="gBtn2">
	                <c:if test="${registerFlag == '등록'}">
	                        <li style="margin-left: 10px;"><input type="file" name="filePath[0]" style="width: 500px"></li>
					</c:if>
					<c:if test="${registerFlag == '수정'}">
	                <c:forEach var="result" items="${file}" varStatus="status">
							<li><a href="#none"><span class="icoDown"></span><c:out value='${result.umf_con_fnm}'/></a>
							<c:if test="${registerFlag == '수정'}">
								<a class="del" title="삭제" onclick="delFile_U(<c:out value='${result.umf_fle_key}'/>)">x<em class="hidden">삭제</em></a>
							</c:if>
							</li>
					</c:forEach>
					</c:if>
	                </ul>
	            </div>
        	<div class="editerArea">
        		<form:textarea path="ubm_brd_cts" rows="5" cols="70" id="ubm_brd_cts" name="ubm_brd_cts" />
				<ce:replace replace="ubm_brd_cts" basePath="${pageContext.request.contextPath}/editor/ckeditor"></ce:replace>
        	</div>

        </div>
        </form:form>
        <div class="bottomBtnArea">
            <ul class="btnList">
            	<c:if test="${registerFlag == '등록'}">
                <li>
                    <button class="btnStrong" onclick="fn_egov_save();"><span class="icoAdd"></span>게시글 작성</button>
                </li>
                </c:if>
                <c:if test="${registerFlag == '수정'}">
                <li>
                    <button class="btnStrong" onclick="fn_egov_save();"><span class="icoRepair"></span>저장</button>
                </li>
                </c:if>
                <li>
                    <button class="btnStrong" onclick="fn_egov_selectList();"><span class="icoList"></span>목록</button>
                </li>
            </ul>
        </div>
    </div>
    <form action="" name="reloadForm" id="reloadForm">
		<input type="hidden" id="seq" name="seq">
	</form>
</body>
</html>