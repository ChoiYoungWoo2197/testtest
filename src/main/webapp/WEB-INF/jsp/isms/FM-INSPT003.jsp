<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
<%@ include file="/WEB-INF/include/contents.jsp"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<script type="text/javascript">
$(document).ready(function() {
	getWorkerList();
	search();
	// 주기 변경시
	$("#workTerm").change(function(){
		var termCheck = $("select[name=workTerm]").val();
		if(termCheck != ""){
			if(termCheck == "Y"){
				getWorkCycle("TERM");
			}else {
				getWorkCycle("nonCycle");
			}
		}else{
			$("#workCycleTerm").html('<option value="">전체</option>');
		}
	});
});


function getWorkCycle(code) {
	var url = "";
	if("TERM" == code) {
		url = "${pageContext.request.contextPath}/code/getCommonCodeList.do";
	} else {
		url = "${pageContext.request.contextPath}/code/getNonCodeList.do";
	}
	
	$.ajax({
		url			: url,
		type		: "post",
		data		: {code:code},
		dataType	: "json",
		success		: function(data){
			var inData ="";
			inData += '<option value="">전체</option>';
			for(var i=0; i<data.result.length; i++){
				inData += '<option value="'+data.result[i].code+'">'+data.result[i].name+'</option>';
			}
			$("#workCycleTerm").html(inData);
		 },
		 error : function(){
			 alert('error');
		 }
	 });
}

function getWorkerList() {
	var dept = $("#dept").val();
	if(dept != ""){
		$.ajax({
			url			: "${pageContext.request.contextPath}/code/getWorkerList.do",
			type		: "post",
			data		: {code:dept},
			dataType	: "json",
			async		: false,
			success		: function(data){
				var	inData = getWorkerInTag(data); 
				$("#worker").html(inData);
			 },
			 error : function(){
				 alert('error');
			 }
		 });
	}
}


function excelDown(){	
	document.searchForm.action = "${pageContext.request.contextPath}/excel/FM-INSPT003.do";
   	document.searchForm.submit();
}

function fileDown(){
	var obj = document.getElementsByName("checking");
	var checkCnt = 0;
	var arr = new Array;
	
	for(var i=0; i<obj.length; i++){
		if(obj[i].checked == true){
			checkCnt++;
		}
	}
	if(checkCnt ==0){
		alert("통제항목을 선택하세요");
		return;
	}
	
	document.listForm.action = "${pageContext.request.contextPath}/common/ZIP_DOWN.do";
   	document.listForm.submit();
}

function search(){	
	var service = $("#service").val();
	var standard = $("#standard").val();
	var worker = $("#worker").val();
	var dept = $("#dept").val();	
	var goalNum = $("#goalNum").val();
	var workTerm = $("select[name=workTerm]").val();
	var workCycleTerm = $("#workCycleTerm").val();
	
	$("#searchForm").ajaxSubmit({
		url : "${pageContext.request.contextPath}/inspt/FM-INSPT003_getCntrWork.do",
		 type		: "post",
		 data		: $("#searchForm").serialize(),
		 dataType	: "json",
		 success	: function(data){
			 var workListHTML = "";
			for(var i=0; i<data.resultList.length; i++) {
				var ctrKey = data.resultList[i].ucmCtrKey;
				var golNo = data.resultList[i].ucm3lvCod;
				var docNam = data.resultList[i].utdDocNam;
				var fileName = data.resultList[i].fileName;
				var termGbn = data.resultList[i].termGbn;
				
				var utwSvcCod = data.resultList[i].utwSvcCod;
				var utwDepCod = data.resultList[i].utwDepCod;
				var worker = data.resultList[i].worker;
				var ucmCtrGbn = data.resultList[i].ucmCtrGbn;
				var utwWrkKey = data.resultList[i].utwWrkKey;
				
				var fileList = data.resultList[i].fileList;
				var fileListHTML ="";
				for(var j=0; j<fileList.length; j++) {
					fileListHTML += '<li><a href="#none"><span class="icoDown"></span>'+fileList[j].umfConFnm+'</a></li>';
				}
			
				workListHTML += '<tr>';
				workListHTML += '<td>'+ucmCtrGbn+'</td>';
				workListHTML += '<td>'+golNo+'</td>';
				workListHTML += '<td>'+utwSvcCod+'</td>';
				workListHTML += '<td>'+utwDepCod+'</td>';
				workListHTML += '<td>'+worker+'</td>';
				workListHTML += '<td>'+docNam+'</td>';					
				workListHTML += '<td>'+termGbn+'</td>';					
				workListHTML += '<td><div class="uplidFileList"><ul class="listArea">'+fileListHTML+'</ul></div></td>';
				workListHTML += '</tr>';
			}
			if(workListHTML == ""){
				workListHTML += '<tr class="last"><td class="last noDataList" colspan="8">검색된 수행결과가 없습니다.</td></tr>'
			}
			workListHTML = replaceAll(workListHTML, 'null', '-');
			$("#workList").html(workListHTML);				
		 },
		 error : function(){
			 alert('error' + id);
		 }
	 });	
	//document.searchForm.action = "${pageContext.request.contextPath}/mwork/FM-MWORK008.do";
   	//document.searchForm.submit();
}

function testView(wKey){
	window.open("", "testViewPopup",	"width=380, height=250, menubar=no, location=no, status=no, scrollbars=yes");
	$("#wKey").val(wKey);
	document.testViewForm.submit();
}

function cntrPopup(cntrKey){
	window.open("","cntrPopup","width=730, height=500, menubar=no, location=no, status=no,scrollbars=yes");
	$("#ucmCtrKey").val(cntrKey);
	document.cntrPopupForm.submit();
}

function replaceAll(str,ori,rep){
	return str.split(ori).join(rep);
}
</script>
</head>
<body>
	<form id="testViewForm" name="testViewForm" action="FM-INSPT001_popup.do" method="post" target="testViewPopup">
		<input type="hidden" id="wKey" name="wKey" value="">
	</form>
	<p class="history">
		<a href="#none">보안감사</a><span>&gt;</span>심사원화면
	</p>
	<div class="conttitle">심사원화면</div>
	<div class="search">
		<form id="searchForm" name="searchForm" method="post">
			<div class="defalt">			
				<fieldset class="searchForm">
					<legend>상세 검색</legend>
					<ul class="detail newField">
						<li>
							<span class="title"><label for="service">서비스</label></span>
                           <sb:select name="service" type="S" />
                        </li>
                        <li>
                            <span class="title"><label for="dept">부서</label></span>
                           	<sb:select name="dept" type="D" event="getWorkerList()" />
                        </li>
                        <li>
                            <span class="title"><label for="worker">담당자</label></span>
                            <select id="worker" name="worker" class="selectBox">
                            	<option value="">전체</option>
                            </select>
                        </li>
                        <li>
                            <span class="title"><label for="standard">컴플라이언스</label></span>
							<sb:select name="standard" type="A" code="STND" />
                        </li>
						<li><span class="title"><label for="workTerm">업무주기</label></span>
							<select id="workTerm" name="workTerm" class="selectBox">
								<option value="">전체</option>
								<option value="Y" <c:if test="${searchVO.workTerm eq 'Y'}">selected="selected"</c:if>>주기</option>
								<option value="N" <c:if test="${searchVO.workTerm eq 'N'}">selected="selected"</c:if>>비주기</option>
							</select>
						</li>
						<li>
							<span class="title"><label for="workCycleTerm">상세주기</label></span>
							<select id="workCycleTerm" name="workCycleTerm" class="selectBox">
								<option value="">전체</option>
							</select>
						</li>
						<li>
							<span class="title"><label for="goalNum">통제항목번호</label></span>
							<input id="goalNum" class="inputText wdShort" type="text" title="통제항목번호 입력" placeholder="통제항목번호 입력" name="goalNum"/>
						</li>
						<li class="last">
							<span class="title"><label for="workName">업무명</label></span>
							<input id="workName" class="inputText wdShort" type="text" title="업무명 입력" placeholder="업무명 입력" name="workName" />
						</li>
						<li class="btnArea" "><button type="button"  onclick="search();" class="btnSearch">조건으로 검색</button></li>
					</ul>
				</fieldset>			
			</div>
		</form>
	</div>
	<div class="contents">
		<div class="topBtnArea">
			<ul class="btnList">
				<li><!-- <button onclick="excelDown(); return false;"><span class="icoExl"></span>엑셀다운로드</button> --></li>
			</ul>
		</div>
		<div class="talbeArea hiddenTable">
		<form id="listForm" name="listForm" method="post">
			<table summary=“심사문서관리 현황을 리스트 선택, 항목번호, 점검항목, 업무명(양식서), 증적파일명, 업무주기 등의 항목으로 설명하고 있습니다.” style="width:1000px"> 
				<colgroup>
					<col width="10%">
					<col width="10%">
					<col width="10%">
					<col width="10%">
					<col width="10%">
					<col width="*">
					<col width="10%">
					<col width="10%">
				</colgroup>
				<thead>
					<tr>
						<th scope="col">컴플라이언스</th>
						<th scope="col">항목번호</th>
						<th scope="col">서비스</th>
						<th scope="col">부서</th>
						<th scope="col">담당자</th>
                      	<th scope="col">업무명<br>(양식서)</th>
                      	<th scope="col">업무주기</th>
                      	<th scope="col" class="last">증적파일명</th>                      	
					</tr>
				</thead>
				<tbody id ="workList">
				</tbody>
			</table>
		</form>	
		</div>
	</div>
</body>
</html>