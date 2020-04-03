<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String prevUrl = (String)request.getParameter("prevUrl");
	String message = (String) request.getParameter("message");
	String userId = (String)request.getParameter("userId");
	if (userId != null && !userId.equals("")) {
//		RequestDispatcher dispatcher = request.getRequestDispatcher("/login/SSO_LOGIN.do");
//		dispatcher.forward(request, response);
		response.sendRedirect("/login/SSO_LOGIN.do?userId=" + userId);
	}
%>
<!DOCTYPE html>
<html lang="ko">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>SK broadband ISAMS</title>
        <link rel="stylesheet" type="text/css" href="/common/css/reset.css"/>
        <link rel="stylesheet" type="text/css" href="/common/css/common.css"/>
        <!--[if IE]> <script src="/common/js/html5.js"></script> <![endif]-->
        <script type="text/javascript" src="/common/js/jquery.js"></script>
        <script type="text/javascript" src="/common/js/ismsUI.js"></script>
        <script type="text/javascript">
	        $(document).ready(function () {
	        	var ssoId = "<%=userId%>";
	    		messageCheck();
	    		$("#id").focus();
	    	});

	        function actionLogin(){

	        	if($('#id').val() == ""){
	        		alert("사번을 입력하세요.");
	        		$('#id').focus();
	        		return false;
	        	}

	        	if($('#password').val() == ""){
	        		alert("비밀번호를 입력하세요.");
	        		$('#password').focus();
	        		return false;
	        	}

	        	document.loginForm.action = "login/actionLogin.do";
	    		document.loginForm.submit();
	    	}

	        function messageCheck(){
	    		var message = "<%=message%>";

	    		if(message != null){
	    			if(message == "F"){alert("사번 또는 비밀번호를 다시 확인하세요.");}
	    			else if(message == "SetLockId"){alert("로그인 실패 5회로 사번이 잠금상태로 변경되었습니다.");}
	    			else if(message == "Locked"){alert("잠금상태 사번 입니다. 관리자에게 문의하여 주십시오.");}
	    			else if(message == "P1") { alert("최초 로그인 하였거나 비밀번호가 초기화 되었습니다.\n\n비밀번호 변경 후 이용해 주시기 바랍니다."); self.location = "changePassword.jsp"; }
	    			else if(message == "P2") { alert("비밀번호 변경 후 90일이 경과 하였습니다.\n\n새로운 비밀번호 변경 후 이용해 주시기 바랍니다."); self.location = "changePassword.jsp"; }
	    		}
	    	}

        </script>
    </head>
    <body>
        <div id="skipnavigation">
            <ul>
                <li><a href="#content-box">본문 바로가기</a></li>
            </ul>
        </div>
        <div id="wrap" class="login">
            <header>
                <hgroup>
                    <h1><a href="#none">SK broadband ISAMS</a></h1>
                </hgroup>
            </header>
            <form name="loginForm" method="post">
            <div class="cont">
                <div class="cont_container">
                    <article id="content-box">
                        <fieldset class="login_form">
                            <h2><strong><span class="red">SK</span> <span class="orange">broadband</span> ISAMS</strong> LOGIN</h2>
                            서비스를 이용하시려면 로그인을 하셔야 합니다.
							<legend>로그인</legend>
							<ul class="loginInputArea">
								<li>
									<label for="id" class="hidden">User ID</label>
									<input id="id" name="id" class="inputText" type="text" value="" placeholder="사번"/>
								</li>
								<li>
									<label for="password" class="hidden">Password</label>
									<input id="password" name="password" class="inputText" type="password" value="" placeholder="비밀번호"/>
								</li>
							</ul>
							<button class="btnLogin" onclick="actionLogin(); return false;">LOGIN</button>
						</fieldset>
                    </article>
                </div>
            </div>
            <input type="hidden" name="prevUrl" value="<%=prevUrl%>" />
            </form>
            <footer>COPYRIGHT 2015 BY SK broadband CO.LTD. ALL RIGHTS RESERVED.</footer>
        </div>
    </body>
</html>