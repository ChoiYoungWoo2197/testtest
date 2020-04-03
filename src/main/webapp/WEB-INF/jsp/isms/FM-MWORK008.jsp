<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
<%@ include file="/WEB-INF/include/contents.jsp"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<script type="text/javascript" src="<c:url value='/js/jquery.form.js'/>"></script>
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
	document.searchForm.action = "${pageContext.request.contextPath}/excel/FM-MWORK008.do";
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
	
	document.searchForm.action = "${pageContext.request.contextPath}/common/ZIP_DOWN.do";
   	document.searchForm.submit();
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
		url : "${pageContext.request.contextPath}/mwork/FM-MWORK008_getCntrWork.do",
		 type		: "post",
		 data		: $("#searchForm").serialize(),
		 dataType	: "json",
		 success	: function(data){
			 var workListHTML = "";
				for(var i=0; i<data.resultList.length; i++) {
					
					var ctrKey = data.resultList[i].ucmCtrKey;
					var golNo = data.resultList[i].ucm3lvCod;
					var lvNam = data.resultList[i].ucm3lvNam;
					var docNam = data.resultList[i].utdDocNam;
					var fileName = data.resultList[i].fileName;
					var termGbn = data.resultList[i].termGbn;	

					fileName = replaceAll(fileName,',','<br/>');
					
					workListHTML += '<tr>';
					workListHTML += '<td><input type="checkbox" value="'+ctrKey+'" name="checking"></td>';
					workListHTML += '<td>'+golNo+'</td>';
					workListHTML += '<td>'+lvNam+'</td>';
					workListHTML += '<td>'+docNam+'</td>';
					workListHTML += '<td><div class="uplidFileList"><ul class="listArea"><li><a href="#none" class="hiddenLink" title="'+data.resultList[i].fileName+'">'+fileName+'</a></li></ul></div></td>';
					workListHTML += '<td class="last">'+termGbn+'</td>';				
					workListHTML += '</tr>';
				}
				if(workListHTML == ""){
					workListHTML += '<tr class="last"><td class="last noDataList" colspan="6">검색된 수행결과가 없습니다.</td></tr>'
				}
				workListHTML = replaceAll(workListHTML, 'null', '-');
				$("#workList").html(workListHTML);
				$("#workList").rowspan(0);
				$("#workList").rowspan(1);
				$("#workList").rowspan(2);
				$("#workList").rowspan(3);
				$("#workList").rowspan(4);
				$("#workList").rowspan(5);
				//$("#workList").rowspan(6);
		 },
		 error : function(){
			 alert('error' + id);
		 }
	 });	
	//document.searchForm.action = "${pageContext.request.contextPath}/mwork/FM-MWORK008.do";
   	//document.searchForm.submit();
}

function cntrPopup(cntrKey){
	window.open("","cntrPopup","width=730, height=500, menubar=no, location=no, status=no,scrollbars=yes");
	$("#ucmCtrKey").val(cntrKey);
	document.cntrPopupForm.submit();
}

function replaceAll(str,ori,rep){
	return str.split(ori).join(rep);
}

$.fn.rowspan = function(colIdx, isStats) {
    return this.each(function(){      
        var that;     
        $('tr', this).each(function(row) {      
            $('td:eq('+colIdx+')', this).filter(':visible').each(function(col) {
                 
                if ($(this).html() == $(that).html()
                    && (!isStats 
                            || isStats && $(this).prev().html() == $(that).prev().html()
                            )
                    ) {            
                    rowspan = $(that).attr("rowspan") || 1;
                    rowspan = Number(rowspan)+1;
 
                    $(that).attr("rowspan",rowspan);
                     
                    // do your action for the colspan cell here            
                    $(this).hide();
                     
                    //$(this).remove(); 
                    // do your action for the old cell here
                     
                } else {            
                    that = this;         
                }          
                 
                // set the that if not already set
                that = (that == null) ? this : that;      
            });     
        });    
    });  
}; 
</script>
</head>
<body>
	<p class="history">
		<a href="#none">정보보호활동</a><span>&gt;</span>수행결과조회
	</p>
	<div class="conttitle">수행결과조회</div>
	<div class="search">
		<form id="searchForm" name="searchForm" method="post">
			<div class="defalt">			
				<fieldset class="searchForm">
					<legend>상세 검색</legend>
					<ul class="detail">
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
						<li><span class="title"><label for="goalNum">통제항목번호</label></span>
							<input id="goalNum" class="inputText" type="text" title="통제항목번호 입력" placeholder="통제항목번호를 입력하세요." name="goalNum"/>
						</li>
					</ul>
					<button class="btnSearch defaltBtn" onclick="search(); return false;">조건으로 검색</button>
				</fieldset>			
			</div>
		</form>
	</div>
	<div class="contents">
		<div class="topBtnArea">
			<ul class="btnList">
				<li><button onclick="excelDown(); return false;"><span class="icoExl"></span>엑셀다운로드</button></li>
				<li><button onclick="fileDown(); return false;"><span class="icoDown"></span>파일다운로드</button></li>
			</ul>
		</div>
		<div class="talbeArea">
			<table summary="통제항목별 조회 현황을
				통제목적, 통제항목, 통제점검, 점검항목, 업무명(양식서), 업무주기, 담당자 등의 항목으로설명하고있습니다.">
				<colgroup>
						<col width="5%">
					 	<col width="10%">
                           <col width="30%">
                           <col width="30%">
                           <col width="15%">
                           <col width="10%">
				</colgroup>
				<thead>
					<tr>
						<th></th>
					 	<th scope="col">항목번호</th>
                        <th scope="col">점검항목</th>
                        <th scope="col">업무명<br>(양식서)</th>
                        <th scope="col">증적파일명</th>
                        <th scope="col" class="last">업무주기</th>
					</tr>
				</thead>
				<tbody id ="workList">
				</tbody>
			</table>
			
		</div>
	</div>
</body>
</html>