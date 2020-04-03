<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/WEB-INF/include/contents.jsp"%>
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
<script type="text/javascript" src="/common/js/jquery.form.js"></script>
<script type="text/javascript" src="/common/js/common.js"></script>
<script  type="text/javascript">
$(document).ready(function() {
	
});

function viewSave(){
		
	var	url = "${pageContext.request.contextPath}/inspt/FM-INSPT001_VIEW_UPDATE.do";
	
	$.ajax({
		url			: url,
		type		: "post",
		data		: $("#viewForm").serialize(),
		dataType	: "json",
		success	: function(data){
				alert("설정되었습니다.");
				$(opener.location).attr("href", "javascript:search();" );
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
<form name="viewForm" id="viewForm">
<div id="skipnavigation">
    <ul>
        <li><a href="#content-box">본문 바로가기</a></li>
    </ul>
</div>
<div id="wrap" class="pop">
	<header>
		<div class="borderBox"></div>
		<h1>출력 설정</h1>
	</header>
	<article class="cont" id="content-box">
		<div class="cont_container">
			<div class="contents">
				<div class="talbeArea">
			        <table summary="출력 설정"> 
			            <colgroup>
			                <col width="30%" />
							<col width="*" />
			            </colgroup>
			            <caption>출력 설정</caption> 
			            <tbody>
					    	<tr>
		            			<th scope="row" class="listTitle"><label for="utwVewLvl">* 출력 설정</label></th>            			
		                		<td class="fontLeft last">
			                		<select id="utwVewLvl" name="utwVewLvl" class="selectBox">
			                			<option value="0" >미출력</option>									
										<option value="1" <c:if test="${viewType == '1'}">selected="selected" </c:if> >바로보기</option>
										<option value="2" <c:if test="${viewType == '2'}">selected="selected" </c:if> >바로보기+출력</option>
									</select>
		                		</td>
					    	</tr>
			            </tbody>
			        </table>
   				</div>
			</div>
			<div class="bottomBtnArea">
  				<button onclick="viewSave();  return false;"><span class="icoRepair"></span>수정</button>
			</div>
		</div>
		<div class="centerBtnArea">
			<button class="btnStrong close">닫기</button>
		</div>
	</article>
	<input type="hidden" id="wKey" name="wKey" value="${wKey }">
</div>
</form>
</body>
</html>