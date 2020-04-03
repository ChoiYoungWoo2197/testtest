<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/include/contents.jsp"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<META http-equiv="X-UA-Compatible" content="IE=edge">
<title>SK broadband ISAMS</title>
<link rel="stylesheet" type="text/css" href="/common/css/reset.css" />
<link rel="stylesheet" type="text/css" href="/common/css/common.css" />
<link rel="stylesheet" type="text/css" href="/common/css/pop.css" />
<link type="text/css" rel="stylesheet"	href="/common/css/jquery-ui.css" />
<link type="text/css" rel="stylesheet"	href="/common/jqGrid/css/ui.jqgrid.css" />
<!--[if IE]> <script src="/common/js/html5.js"></script> <![endif]-->
<script type="text/javascript" src="/common/js/jquery.js"></script>
<script type="text/javascript" src="/common/js/jquery.form.js"></script>
<script type="text/javascript" src="/common/js/jquery.metadata.js"></script>
<script type="text/javascript" src="/common/js/jquery.MultiFile.js"></script>
<script type="text/javascript" src="/common/js/jquery.blockUI.js"></script>
<script type="text/javascript" src="/common/js/jquery.jqGrid.js"></script>
<script type="text/javascript" src="/common/jqGrid/js/jquery-ui.js"></script>
<script type="text/javascript" src="/common/jqGrid/js/i18n/grid.locale-kr.js"></script>
<script type="text/javascript" src="/common/js/common.js"></script>
<script type="text/javascript">
var standard = "A";
var depth1 = "A";
var depth2 = "A";
var depth3 = "A";

$(document).ready(function() {
	
	getDepthList(0,'standard')
	
});

function getDepthList(num,id){
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
				 $("#code").val(data.result[0].ucmCtrKey);
				 var cntr2Dtl = data.result[0].ucm2lvDtl;
				 cntr2Dtl = cntr2Dtl.replace(/\n/g, "<br />");
				 var cntr3Name = data.result[0].ucm3lvNam;
				 cntr3Name = cntr3Name.replace(/\n/g, "<br />");
				 var cntr3Dtl = data.result[0].ucm3lvDtl;
				 cntr3Dtl = cntr3Dtl.replace(/\n/g, "<br />");
				 $("#cntr2Dtl").text(cntr2Dtl);
				 $("#cntr3Name").text(cntr3Name);
				 $("#cntr3Dtl").html(cntr3Dtl);

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
	option = '<option value="A">-- 선택 --</option>';
	
	if(id == "standard"){
		for(var i=0; i < data.result.length; i++) {
			option += '<option value="' + data.result[i].code+ '">' + data.result[i].cname + '</option>';
		}
	}else{
	
		for(var i=0; i < data.result.length; i++) {
			option += '<option value="' + data.result[i].code+ '">' + data.result[i].code + '</option>';
		}
	}
	
	$("#"+id+" > option").remove();
	$("#"+id).append(option);
}

function setMappting(){
	var ucmKey = $("#code").val();
	
	$("#mappingSampleDocForm").ajaxSubmit({
		url : "${pageContext.request.contextPath}/comps/FM-COMPS005_setMapping_save.do",
		success : function(data){
			alert("증적양식과 통제항목이 매핑 되었습니다.");
			// FM-COMPS00.jsp의 매핑 증적양식 리스트 함수 호출
			$(opener.location).attr("href", "javascript:getCtrMapList();");
			//window.close();
		},
		error : function(data){
			alert("error");
		},
		complete : function(){
		}
	});
}
</script>
</head>
<body>
	<div id="wrap" class="pop">
		<header>
			<div class="borderBox"></div>
			<h1>수행업무설정</h1>
		</header>
		<article class="cont" id="content-box">
			<select id="standard" name="standard" onchange="getDepthList(1,'depth1');" style="width: 100px" class="selectBox">
				<option value="A">-- 선택 --</option>
			</select>				
			<br/>
			<select id="depth1" name="depth1" onchange="getDepthList(2,'depth2');" style="width: 100px" class="selectBox">
				<option value="A">-- 선택 --</option>
			</select>
			<input type="text" style="border: none; width: 450px;" id = "ucm1lvNam">
			<br/>
			<select id="depth2" name="depth2" onchange="getDepthList(3,'depth3');" style="width: 100px" class="selectBox">
				<option value="A">-- 선택 --</option>
			</select>
			<input type="text" style="border: none; width: 450px;" id = "ucm2lvNam">
			<br/>
			<select id="depth3" name="depth3" onchange="getDepthList(4,'depth4');" style="width: 100px" class="selectBox">
				<option value="A">-- 선택 --</option>
			</select>
			<div>
                <ul class="btnList">
                    <li><button id="addStandardBtn" onclick="setMappting(); return false;"><span class="icoSynch"></span>통제항목 연결</button></li>
                </ul>
            </div>	
            <div class="talbeArea">
            <table>
            	<tr>
            		<th colspan="4">상세내용</th>
            	</tr>
                <tr>
                	<td colspan="4" id="cntr2Dtl"></td>
                </tr>
                <tr>
                	<th colspan="4">점검항목</th>
                </tr>
                <tr>
                	<td colspan="4" id="cntr3Name"></td>
                </tr>
                <tr>
                	<th colspan="4">설명</th>
                </tr>
                <tr>
                	<td colspan="4" id="cntr3Dtl"></td>
                </tr>   
            </table>
            </div>
		</article>
		<form id="mappingSampleDocForm" name="mappingSampleDocForm" method="post">
			<input type="hidden" id="code"	name="code"/>
			<input type="hidden" id="utcTrcKey"	name="utcTrcKey"	value="${utdTrcKey }"/>
			<input type="hidden" id="utcBcyCod"	name="utcBcyCod"	value="<%=C_SES_MAN_CYL%>"/>
			<input type="hidden" id="utcRgtId"	name="utcRgtId"		value="<%=C_SES_USER_KEY%>"/>
		</form>
	</div>
</body>
</html>