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
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<c:set var="registerFlag" value="${empty vo.ubm_brd_tle ? '등록' : '수정'}" />
<c:set var="col" value="${registerFlag == '등록' ? '5' : '5'}" />
<script type="text/javaScript" language="javascript" defer="defer">

var fileCnt = 0;
/* 글 목록 화면 function */
var standard = "A";
var depth1 = "A";
var depth2 = "A";
var depth3 = "A";

$(document).ready(function() {

	getDepthList(0,'standard');
	$("#standardView").hide();
});

function getDepthList(num,id){

	if($("#standard").val() == "A"){
		$("#selectStandard").hide();
	}else{
		$("#selectStandard").show();
	}

	var option ='';
	if(num == "1"){
		standard = $("#standard").val();
		if($("#standard").val() == 'A'){
			option = '<option value="A">-- 선택 --</option>';
			$("#depth1 > option").remove();
			$("#depth1").append(option);
			$("#depth2 > option").remove();
			$("#depth2").append(option);
			$("#depth3 > option").remove();
			$("#depth3").append(option);
		}
	}
	if(num == "2"){
		depth1 = $("#depth1").val();
	}
	if(num == "3"){
		depth2 = $("#depth2").val();
	}
	if(num == "4"){
		depth3 = $("#depth3").val();
	}

	$.ajax({
		 url		: "${pageContext.request.contextPath}/comps/GetDepth.do",
		 type		: "post",
		 data		: {"code": num , "standard" : standard, "depth1" : depth1 , "depth2" : depth2 , "depth3" : depth3},
		 dataType	: "json",
		 success	: function(data){

			 $("#cntr2Dtl").text("");
			 $("#cntr3Name").text("");
			 $("#cntr3Dtl").text("");


			 if(num == "4"){
				 $("#standardView").show();
				 $("#code").val(data.result[0].ucmCtrKey);
				 var cntr2Dtl = data.result[0].ucm2lvDtl;
				 cntr2Dtl = cntr2Dtl.replace(/\n/g, "<br />");
				 var cntr3Name = data.result[0].ucm3lvNam;
				 cntr3Name = cntr3Name.replace(/\n/g, "<br />");
				 var cntr3Dtl = data.result[0].ucm3lvDtl;
				 cntr3Dtl = cntr3Dtl.replace(/\n/g, "<br />");
				// $("#cntr2Dtl").text(cntr2Dtl);
				 $("#cntr3Name").html(cntr3Name);
				 //$("#cntr3Dtl").html(cntr3Dtl);

			 }else{
				setSelectBox(data, id);
				if(num == "2"){
					 $("#ucm1lvNam").val(data.result[0].ucm1lvNam);
				}
				if(num == "3"){
					 $("#ucm2lvNam").val(data.result[0].ucm2lvNam);
				}
			 }
		 },
		 error : function(){
			 alert('error' + id);
		 }
	 });
}

function setSelectBox(data, id){
	var option ='';

	if(id == 'standard'){
		option = '<option value="A"> 일반문의 </option>';
	}else{
		option = '<option value="A">-- 선택 --</option>';
	}

	if(id == "standard"){
		for(var i=0; i < data.result.length; i++) {
			option += '<option value="' + data.result[i].code+ '">' + data.result[i].cname + '</option>';
		}
	}else{
		for(var i=0; i < data.result.length; i++) {
			option += '<option value="' + data.result[i].code+ '">' + data.result[i].code + ' '+ data.result[i].cname+'</option>';
		}
	}

	$("#"+id+" > option").remove();
	$("#"+id).append(option);
}

function fn_egov_selectList() {
   	document.detailForm.action = "FM-BOARD005.do";
   	document.detailForm.submit();
}


/* 글 삭제 function */
function fn_egov_delete() {
   	document.detailForm.action = "FM-BOARD005_D.do";
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

   	frm.action = "<c:url value="${registerFlag == '등록' ? 'FM-BOARD005_W.do' : 'FM-BOARD005_U.do'}"/>";
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
function delFile(fileCnt){

	$("#li"+fileCnt).remove();
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
				frm.action = "FM-BOARD005_R.do";
				frm.submit();
			 }
		 });
	}
}
function addFileUpdate(seq){
	frm = document.detailForm;
   	frm.action = "FM-BOARD005_U.do";
	frm.submit();
}

function copyToFAQ() {
	frm = document.detailForm;
   	frm.action = "FM-BOARD005_copyToFAQ.do";
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
           <table summary="FAQ 게시판의 제목과 작성자, 작성일 등의 정보를 설명하고 있습니다.">
               <colgroup>
                   <col width="15%">
                   <col width="*">
                   <col width="15%">
                   <col width="20%">
               </colgroup>
               <caption>게시판 상세 정보</caption>
               <tbody>
                   <tr>
                       <th><label for="ubm_brd_tle">제목</label></th>
                       <td class="listTitle"><form:input path="ubm_brd_tle" maxlength="80" size="80" class="inputText" id="ubm_brd_tle"/>
&nbsp;<form:errors path="ubm_brd_tle" /></td>
                       <th>공지 여부</th>
                       <td class="last">
                           <form:checkbox path="ubm_ntc_yn" value="Y" />
&nbsp;<form:errors path="ubm_ntc_yn" />
                       </td>
                   </tr>
                   <c:if test="${registerFlag == '등록'}">
                          <tr class="last">
                              <th>질문 유형</th>
                              <td class="fontLeft last" colspan="3">
                                  <ul class="listPermission">
                                      <li>
                                          <label for="standard"><strong>유형선택</strong></label>
                                          <select id="standard" name="standard" onchange="getDepthList(1,'depth1');" class="selectBox">
	<option value="A">-- 선택 --</option>
</select>
                                      </li>
                                      <li class="subContents" id="selectStandard">
                                          <ul class="sub">
                                              <li>
                                                  <label for="depth1"><strong>통제목적</strong></label>
                                                  <select id="depth1" name="depth1" onchange="getDepthList(2,'depth2');" class="selectBox">
		<option value="A">-- 선택 --</option>
		</select>
                                              </li>
                                              <li>
                                                  <label for="depth2"><strong>통제항목</strong></label>
                                                  <select id="depth2" name="depth2" onchange="getDepthList(3,'depth3');" class="selectBox">
		<option value="A">-- 선택 --</option>
		</select>
                                              </li>
                                              <li>
                                                  <label for="depth3"><strong>통제점검</strong></label>
                                                  <select id="depth3" name="depth3" onchange="getDepthList(4,'depth4');" class="selectBox">
		<option value="A">-- 선택 --</option>
		</select>
                                              </li>
                                          </ul>
                                      </li>
                                  </ul>
                              </td>
                          </tr>
                          </c:if>
                   <c:if test="${registerFlag == '수정'}">
                   <tr class="last">
           <th scope="row">질문 유형</th>
           <td class="fontLeft last" colspan="3">
               <span class="floatLine">${std.uccSndNam}</span>
               <ul class="listPermission floatLine">
                   <li>
                       <strong>통제목적</strong>${std.ucm1lvCod}
                   </li>
                   <li>
                       <strong>통제항목</strong>${std.ucm2lvCod}
                   </li>
                   <li>
                       <strong>통제점검</strong>${std.ucm3lvCod}
                   </li>
               </ul>
           </td>
       </tr>
                   </c:if>
               </tbody>
           </table>
       </div>
       <div class="listDetailArea write">
       	<c:if test="${registerFlag == '등록'}">
           <div class="talbeArea" id="standardView">
               <table>
                   <colgroup>
                       <col width="15%">
                       <col width="*">
                       <col width="15%">
                       <col width="20%">
                   </colgroup>
                   <tbody>
                       <!--tr>
                           <th scope="row">상세내용</th>
                           <td class="listTitle last" colspan="3" id="cntr2Dtl"></td>
                       </tr-->
                       <tr>
                           <th scope="row">점검항목</th>
                           <td class="listTitle last" colspan="3" id="cntr3Name"></td>
                       </tr>
                       <!--tr>
                           <th scope="row">설명</th>
                           <td class="listTitle last" colspan="3" id="cntr3Dtl"></td>
                       </tr-->
                   </tbody>
               </table>
           </div>
           </c:if>
           <c:if test="${registerFlag == '수정'}">
                       <div class="talbeArea">
<table summary=“통제점검 1.2.3.3의 내용으로 상세내용, 점검항목, 설명 등의 항목으로 설명하고 있습니다.”>
	<colgroup>
<col width="15%">
           <col width="*">
           <col width="15%">
           <col width="20%">
       </colgroup>
                 <tbody>
<tr>
	<th scope="row">상세내용</th>
                         <td class="listTitle last" colspan="3">${std.ucm1lvNam}</td>
           </tr>
           <tr>
               <th scope="row">점검항목</th>
               <td class="listTitle last" colspan="3">${std.ucm2lvNam}</td>
           </tr>
           <tr>
               <th scope="row">설명</th>
               <td class="listTitle last" colspan="3">   ${std.ucm3lvNam}</td>
            </tr>
        </tbody>
    </table>
</div>
           </c:if>
           <p class="detailTitle">질문</p>
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
             <div class="listDetailArea write">
                                <p class="detailTitle">답변</p>
                                <div class="editerArea">
				        		<form:textarea path="ubm_ans_cts" rows="5" cols="70" id="ubm_brd_cts" name="ubm_brd_cts" />
								<ce:replace replace="ubm_ans_cts" basePath="${pageContext.request.contextPath}/editor/ckeditor"></ce:replace>
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