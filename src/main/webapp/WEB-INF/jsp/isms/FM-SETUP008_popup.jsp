<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/WEB-INF/include/contents.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<META http-equiv="X-UA-Compatible" content="IE=edge">
<title>SK broadband ISAMS</title>
<link rel="stylesheet" type="text/css" href="/common/css/reset.css"/>
<link rel="stylesheet" type="text/css" href="/common/css/common.css"/>
<link rel="stylesheet" type="text/css" href="/common/css/pop.css"/>
<!--[if IE]> <script src="/common/js/html5.js"></script> <![endif]-->
<script type="text/javascript" src="/common/js/jquery.js"></script>
<script type="text/javascript" src="/common/js/ismsUI.js"></script>
<script type="text/javascript" src="/common/js/common.js"></script>
<script  type="text/javascript">
	$(document).ready(function() {
		// 코드 추가일 경우 심사주기 get selectBox
		if("${mode}" == "add"){
			getManCycleSelectBox("selectManCycle", "<%=C_SES_MAN_CYL%>");
		}
	});
	
	function getManCycleSelectBox(id, code){
		
		$.ajax({
			 url		: "${pageContext.request.contextPath}/code/GetManCycleSelectBox.do",
			 type		: "post",
			 dataType	: "json",
			 success	: function(data){
				 var option = '';
				 for(var i=0; i < data.result.length; i++) {
					option += '<option value="' + data.result[i].code+ '">' + data.result[i].name + '</option>';
				}
				$("#"+id+" > option").remove();
				$("#"+id).append(option);
				$("#"+id+" > option[value=" + code +"]").attr("selected", "ture");	
			 },
			 error : function(){
				 alert('error');
			 }
		 });
	}
	
	function saveCodeInfo(){
		
		var bcyCod = "";
		var code = $("#code").val();
		var codeNam = $("#codeNam").val();
		var useYn = $(":input:radio[name=useYn]:checked").val();
		var codeEtc = $("#codeEtc").val();
		var mode = "${mode}"; 
		
		if(codeNam == "") {
			alert("비주기명을 입력하세요.");
			return;
		}
		
		if( mode == "add"){
			bcyCod = $("#selectManCycle").val();
		}else{
			bcyCod = "${codeInfo.uncBcyCod}";
		}
		
		$.ajax({
			 url		: "${pageContext.request.contextPath}/setup/FM-SETUP008_codeInfo_save.do",
			 type		: "post",
			 data		: {"bcyCod":bcyCod, "code":code, "codeNam":codeNam, "useYn":useYn, "codeEtc":codeEtc, "mode":mode},
			 dataType	: "json",
			 success	: function(data){
				 alert("비주기 업무를 저장하였습니다.");
				 window.opener.document.location.href = window.opener.document.URL;
				 window.close();
			 },
			 error : function(){
				 alert('error');
			 }
		 });
	}
</script>
</head>
<body>
	<div id="skipnavigation">
    <ul>
        <li><a href="#content-box">본문 바로가기</a></li>
    </ul>
	</div>
	<div id="wrap" class="pop">
		<header>
		<div class="borderBox"></div>
			<h1>비주기 세부내용</h1>
		</header>
           <article class="cont" id="content-box">
               <div class="cont_container">
				<div class="contents">
					<div class="talbeArea">
				        <table summary="비주기 세부내용"> 
				            <colgroup>
				                <col width="30%" />
								<col width="*" />
				            </colgroup>
				            <caption>비주기 세부내용</caption> 
				            <tbody>
				                <tr>
			            			<th scope="row" class="listTitle"><label for="selectManCycle">* 심사주기</label></th>            			
			                		<td class="fontLeft last">
			                		<c:if test="${mode == 'add' }">
										<select id="selectManCycle" name="bcyCod" style="width:180px" class="selectBox"></select>
									</c:if>
									<c:if test="${mode == 'update' }">
										${codeInfo.stdDat}~${codeInfo.endDat}
									</c:if>
			                		</td>
						    	</tr>
						    	<tr>
			            			<th scope="row" class="listTitle"><label for="codeNam">* 비주기명</label></th>            			
			                		<td class="fontLeft last">
			                			<input type="hidden" id="code" name="code" value="<c:out value="${codeInfo.uncNtrCod}" />"/>
			                			<input type="text" id="codeNam" name="codeNam" class="inputText"  maxlength="25" style="width:240px" value="<c:out value="${codeInfo.uncNtrNam}" />"/>
			                		</td>
						    	</tr>
						    	<tr>
			            			<th scope="row" class="listTitle"><label for="codeEtc">기타</label></th>            			
			                		<td class="fontLeft last"><textarea  id="codeEtc" name="codeEtc" maxlength="250" class="txtAreaType02">${codeInfo.uncEtc}</textarea></td>
						    	</tr>
						    	<tr>
			            			<th scope="row" class="listTitle">사용유무</th>            			
			                		<td class="fontLeft last">
										<input type="radio" id="useYn" name="useYn" value="Y" <c:if test="${codeInfo.uncUseYn eq 'Y' or empty codeInfo.uncUseYn}">checked="checked"</c:if> /> <label for="uncUseY">사용</label>
										<input type="radio" id="useYn" name="useYn" value="N" <c:if test="${codeInfo.uncUseYn eq 'N'}">checked="checked"</c:if> /> <label for="uncUseN">미사용</label>
			                		</td>
						    	</tr>
				              
				            </tbody>
				        </table>
    				</div>
               	</div>
			</div>
			<div class="centerBtnArea">
				<button class="btnStrong" onclick="saveCodeInfo();return false;"><span class="icoAdd"></span>저장</button>
				<button class="btnStrong close">닫기</button>
			</div>
		</article>
	</div>
</body>
</html>