<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String message = (String) request.getParameter("message");
%>
<!DOCTYPE html>
<html lang="ko">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>SK broadband ISAMS</title>
		<link rel="stylesheet" type="text/css" href="/common/css/jquery-ui.css"/>
        <link rel="stylesheet" type="text/css" href="/common/css/reset.css"/>
        <link rel="stylesheet" type="text/css" href="/common/css/common.css"/>
        <!--[if IE]> <script src="/common/js/html5.js"></script> <![endif]-->
        <script type="text/javascript" src="/common/js/jquery.js"></script>
		<script type="text/javascript" src="/common/js/jquery-ui.js"></script>
        <script type="text/javascript" src="/common/js/ismsUI.js"></script>
        <script type="text/javascript" src="/common/js/common.js"></script>
        <style type="text/css">
        #headText { text-align: left; padding: 10px 10px 5px 22px; }
	    .stateContainer { padding-left: 20px !important; text-align: left !important; height: 15px !important; }
    	.stateContainer span { float: left !important; color: inherit !important; }
        </style>
        <script type="text/javascript">
	        $(document).ready(function () {
        		$(".stateContainer").hide();
	        	$("#password").focus();

	        	messageCheck();

        		$("#password").keyup(function() {
        			checkPassword();
	        	});
	        });

	        function actionLogin() {
	        	if (checkPassword() && checkPassword2()) {
        			$("#loginForm").attr("action", "login/changePassword.do").submit();
        		}
        		return false;
	        }

	        function checkPassword() {
	        	var pwd = $("#password").val();
	        	var result = validatePassword(pwd);
	        	$(".stateContainer").hide();

	        	if (result) {
        			//result = result.replace(/\n/gi, "<br/>");
	        		$("#stateError").html("비밀번호가 유효하지 않습니다.");
	        		$("#stateContainer").show();
	        		return false;
        		}
        		else {
            		$("#statePassContainer").show();
            		return true;
        		}
	        }

	        function checkPassword2() {
	        	var pwd = $("#password").val();
        		var pwd2 = $("#password2").val();
	        	$(".stateContainer").hide();

	        	if (pwd != pwd2) {
        			$("#stateError").html("비밀번호가 일치하지 않습니다.");
	        		$("#stateContainer").show();
        			return false;
        		}
	        	else {
		        	return true;
	        	}
	        }

	        function messageCheck(){
	    		var message = "<%=message%>";

	    		if (message != null) {
	    			if (message == "D") { alert("기존과 동일한 비밀번호는 사용하실 수 없습니다."); }
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
            <form id="loginForm" name="loginForm" method="post">
            <div class="cont">
                <div class="cont_container">
                    <article id="content-box">
                        <fieldset class="login_form">
                            <h2 style="background-color: #E0002A; color: #fff; font-size: 2em; font-weight: 800; padding-top: 15px;">비밀번호 재설정</h2>
                            <div id="headText">비밀번호는 영문, 숫자, 특수문자 중<br/>2종류 조합 10자리 이상으로 또는<br/>3종류 조합 8자리 이상으로 입력해주세요.</div>
							<legend>로그인</legend>
							<ul class="loginInputArea">
								<li>
									<label for="password" class="hidden">변경할 비밀번호</label>
									<input id="password" name="password" class="inputText" type="password" value="" placeholder="변경할 비밀번호"/>
								</li>
								<li>
									<label for="password2" class="hidden">변경할 비밀번호 확인</label>
									<input id="password2" name="password2" class="inputText" type="password" value="" placeholder="변경할 비밀번호 확인"/>
								</li>
								<li id="stateContainer" class="stateContainer ui-state-error-text">
									<span class="ui-icon ui-icon-circle-close"></span>
									<span id="stateError"></span>
								</li>
								<li id="statePassContainer" class="stateContainer">
									<span class="ui-icon ui-icon-circle-check"></span>
									<span>유효한 비밀번호 입니다.</span>
								</li>
							</ul>
							<button class="btnLogin btnSearch" onclick="actionLogin(); return false;">비밀번호 변경</button>
						</fieldset>
                    </article>
                </div>
            </div>
            </form>
            <footer>COPYRIGHT 2015 BY SK broadband CO.LTD. ALL RIGHTS RESERVED.</footer>
        </div>
    </body>
</html>