<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>

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
<script type="text/javascript" src="/common/js/jquery.form.js"></script>
<script type="text/javascript" src="/common/js/ismsUI.js"></script>
<script type="text/javascript" src="/common/js/common.js"></script>
<script  type="text/javascript">
	function pwdSave() {
		var newPwd = $("#newPwd").val();
		var newPwdOk = $("#newPwdOk").val();
		if($("#newPwd").val() != $("#newPwdOk").val()) {
			alert("입력하신 비밀번호가 동일하지 않습니다.");
			$("#newPwdOk").val("");
			$("#newPwdOk").focus();
			return;
		}
		
		$.ajax({
			url		: "${pageContext.request.contextPath}/setup/FM-SETUP007_PW.do",
			type		: "post",
			data		: $("#pwdForm").serialize(),
			dataType	: "json",
			success	: function(data){			
				if(data.result == "D") {
					alert("기존 비밀번호와 동일합니다. \n 다른 비밀번호를 입력해 주세요..");
				} else {
					alert("변경 되었습니다.");
					window.close();	
				}
			},
			error : function(){
				alert('error');
			}
		});
	}
</script>
</head>
<body>
	<form id="pwdForm" name="pwdForm" method="post">
	<div id="skipnavigation">
	    <ul>
	        <li><a href="#content-box">본문 바로가기</a></li>
	    </ul>
	</div>
	<div id="wrap" class="pop">
		<header>
			<div class="borderBox"></div>
			<h1>비밀번호 변경</h1>
		</header>
		<article class="cont" id="content-box">
			<div class="cont_container">
				<div class="contents">
					<div class="talbeArea">
		        		<table summary="엑셀 업로드"> 
		            		<colgroup>
								<col width="40%" />
								<col width="*" />
							</colgroup>
							<caption>엑셀 업로드</caption> 
							<tbody>
								<tr>
									<th class="listTitle last" colspan="2"><label for="newPwd">최초 접속시 비밀번호를 변경해주세요.</label></th>            			
								</tr>
								<tr>
									<th class="listTitle" scope="row"><label for="newPwd">새로운 비밀번호</label></th>            			
		   							<td class="fontLeft last">
										<input type="password" id="newPwd" name="newPwd" class="inputType"/>
		   							</td>
								</tr> 
								<tr>
									<th class="listTitle" scope="row"><label for="newPwdOk">새로운 비밀번호 확인</label></th>            			
		   							<td class="fontLeft last">
										<input type="password" id="newPwdOk" name="newPwdOk" class="inputType"/>
		   							</td>
								</tr>         
		        			</tbody>
		    			</table>
					</div>
					<div class="bottomBtnArea">
						<ul class="btnList">
							<li><button type="button" onclick="pwdSave();"><span class="icoSave"></span>저장</button></li>
						</ul>
					</div>
	       		</div>
           </div>
           <div class="centerBtnArea">
				<button class="btnStrong close" onclick="return false;" >닫기</button>
			</div>
	</article>
</div>
</form>
</body>
</html>