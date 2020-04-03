<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.uwo.isms.domain.WorkVO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="dec" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ include file="/WEB-INF/include/head.jsp" %>
<%@ include file="/WEB-INF/include/contents.jsp"%>
<%
	List menuList = (List)request.getSession().getAttribute("menuList");
	List spdMenuList = (List)request.getSession().getAttribute("spdMenuList");

	String url = (String)request.getRequestURI();
	String[] menu = url.split("/");
	String topMenu = "";
	String leftMenu = "";
	if(!menu[menu.length-1].equals("FM-MAIN.do") ) {
		topMenu = menu[menu.length-1].substring(0,8);
		leftMenu = menu[menu.length-1].substring(8,11);
	}

%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<meta http-equiv="Pragma" content="no-cache"/>
<title>SK broadband ISAMS</title>
<link rel="stylesheet" type="text/css" href="/common/jqGrid/css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="/common/css/jquery-ui.css"/>
<link rel="stylesheet" type="text/css" href="/common/css/reset.css"/>
<link rel="stylesheet" type="text/css" href="/common/css/common.css?v=20180321"/>
<link rel="stylesheet" type="text/css" href="/common/css/main.css?v=20161028"/>
<link rel="stylesheet" type="text/css" href="/common/css/sub.css?v=20161028"/>
<link rel="stylesheet" type="text/css" href="/common/css/tailwind.min.css"/>
<link rel="stylesheet" type="text/css" href="/common/css/font-awesome.min.css"/>
<style>
	*, ::after, ::before {
		box-sizing: unset;
	}
</style>
<!--[if IE]> <script src="/common/js/html5.js"></script> <![endif]-->
<script type="text/javascript" src="/common/js/jquery.js"></script>
<script type="text/javascript" src="/common/js/ismsUI.js?v=20170309"></script>
<script type="text/javascript" src="/common/js/jquery.form.js"></script>
<script type="text/javascript" src="/common/js/jquery.blockUI.js"></script>
<script type="text/javascript" src="/common/js/jquery-ui.js"></script>
<script type="text/javascript" src="/common/jqGrid/js/i18n/grid.locale-kr.js"></script>
<script type="text/javascript" src="/common/jqGrid/js/jquery.jqGrid.js"></script>
<script type="text/javascript" src="/common/js/jquery.chained.min.js"></script>
<script type="text/javascript" src="/common/js/common.js?v=20180321"></script>
<script type="text/javascript" src="/common/js/jquery.scrollTo.min.js"></script>
<script type="text/javascript" src="/common/js/jquery.scrollUp.min.js"></script>
<script type="text/javascript" src="/common/js/lodash.js"></script>
<script type="text/javascript">

	$.ajaxSetup({ cache: false });

	/**
	 * WorkVO.statusType 즉, 활동 업무결과 타입 값을 javascript 에서도 참조하기 위한 객체 생성
	 * key 값은 statusType Enum 값이며 title 값은 화면에 표시하기 위한 텍스트 값이다.
	 */
	var workStatusType = {
		<% for (WorkVO.statusType type : WorkVO.statusType.values()) { %>
		"<%=type.getValue()%>"	: { key : "<%=type.name()%>", title : "<%=type%>" },
		<% } %>
	};


var ctx = "${pageContext.request.contextPath}";
$(document).ready(function(){
	var stdD = '<%=SES_MAN_STD%>';
	var endD = '<%=SES_MAN_END%>';
	$("#manCylDate").text(stdD.substring(0, 4)+"."+stdD.substring(4, 6)+" ~ "+endD.substring(0,4)+"."+endD.substring(4,6));
});

function logout(){
	location.href = "${pageContext.request.contextPath}/login/logout.do";
}

function getManCyl(gubun){
	var mancyl = '<%=SES_MAN_CYL%>';
	$.ajax({
		 url		: "${pageContext.request.contextPath}/login/GetManCyl.do",
		 type		: "post",
		 data		: {"gubun":gubun, "mancyl": mancyl},
		 dataType	: "json",
		 success	: function(data){
			var stdD = '<%=SES_MAN_STD%>';
			var endD = '<%=SES_MAN_END%>';
			$("#manCylDate").text(stdD.substring(0, 6)+" ~ "+endD.substring(0,6));

			location.reload(true);
		 },
		 error : function(){
			 alert('error');
		 }
	 });
}
function goMain(){
	location.href="${pageContext.request.contextPath}/FM-MAIN.do";
}

function goLayoutPage(mnuKey,mnuUrl){
	location.href="${pageContext.request.contextPath}"+mnuUrl;
}

function mypage() {
	window.open("", "userDetailPopupM",	"width=460, height=700, menubar=no, location=no, status=no, scrollbars=yes");
	$("#mode").val('0');
	document.userDetailFormM.submit();
}
</script>
<dec:head />
</head>
<body>
<form id="layoutForm" name="layoutForm">
<input type="hidden" id="layoutMenuKey" name="layoutMenuKey" >
</form>
	<div id="skipnavigation">
	    <ul>
	        <li><a href="#content-box">본문 바로가기</a></li>
	        <li><a href="#gnb">주 메뉴 바로가기</a></li>
	    </ul>
	</div>
	<div id="wrap">
		<header>
		    <hgroup>
		        <h1><a href="javascript:goMain();">SK broadband ISAMS</a></h1>
		        <nav class="gnb">
		            <ul>
		            <c:set var="leftMenuUrl" value="<%= leftMenu %>"/>
					<c:set var="topMenuUrl" value="<%= topMenu %>"/>
					<c:set var="lvlCnt" value="0"/>
		            <c:forEach var="topMenu" items="<%=menuList%>" varStatus="status">
				  	  <c:set var="ummMnuUrl" value="${fn:split(topMenu.ummMnuUrl, '/')}"/>
				  	  <c:if test="${empty topMenu.ummMnuPrt and topMenu.ummMnuLvl eq '1'}">
						<c:set var="lvlCnt" value="${lvlCnt+1}"/>
					    <c:choose>
						  <c:when test="${fn:substring(ummMnuUrl[fn:length(ummMnuUrl)-1],0,8) eq topMenuUrl}">
							<li <c:if test="${status.count eq '6' }">class="last"</c:if>>
								<a class="sel" href="${pageContext.request.contextPath}<c:out value="${topMenu.ummMnuUrl}"/>"><c:out value="${topMenu.ummMnuNam}"/></a>
							</li>
							<c:set var="topMnuNam" value="${topMenu.ummMnuNam}"/>
						  </c:when>
						  <c:otherwise>
						  	<li <c:if test="${status.count eq '6' }">class="last"</c:if>>
								<a href="${pageContext.request.contextPath}<c:out value="${topMenu.ummMnuUrl}"/>"><c:out value="${topMenu.ummMnuNam}"/></a>
							</li>
						  </c:otherwise>
					    </c:choose>
				  	  </c:if>
					</c:forEach>
		            </ul>
		            <ul class="adminList">
		            <c:forEach var="topMenu" items="<%=menuList%>" varStatus="status">
				  	  <c:set var="ummMnuUrl" value="${fn:split(topMenu.ummMnuUrl, '/')}"/>
				  	  <c:if test="${empty topMenu.ummMnuPrt and topMenu.ummMnuLvl eq '2'}">
					    <c:choose>
						  <c:when test="${fn:substring(ummMnuUrl[fn:length(ummMnuUrl)-1],0,8) eq topMenuUrl}">
							<li><a class="sel" href="${pageContext.request.contextPath}<c:out value="${topMenu.ummMnuUrl}"/>"><c:out value="${topMenu.ummMnuNam}"/></a></li>
							<c:set var="topMnuNam" value="${topMenu.ummMnuNam}"/>
						  </c:when>
						  <c:otherwise>
							<li><a href="${pageContext.request.contextPath}<c:out value="${topMenu.ummMnuUrl}"/>"><c:out value="${topMenu.ummMnuNam}"/></a></li>
						  </c:otherwise>
					    </c:choose>
				  	  </c:if>
					</c:forEach>
		            </ul>
		        </nav>
		    </hgroup>
		</header>
			<div class="cont <c:if test="${empty topMenuUrl}">main</c:if>">
			<div class="cont_container">
			    <aside>
			        <div class="personalArea singleInfo">
			            <div class="infoArea">
			                <div class="txtArea">
			                    <p><strong><%=SES_USER_NAME%></strong></p>
			                    <em><%=SES_SERVICE_NAME%>_<%=SES_DEPT_NAME%></em>
			                    <ul class="btnArea">
			                        <li><a href="javascript:logout();">로그아웃</a></li>
			                        <li class="last"><a href="javascript:mypage();">개인정보</a></li>
			                    </ul>
			                </div>
			            </div>
			            <ul class="workFlow" id="approval">
	                        <li>
	                           <a href="/mwork/FM-MWORK013.do#A">결재승인<br/><strong class="step1">-</strong></a>
	                        </li>
	                        <li>
	                           <a href="/mwork/FM-MWORK013.do#I">결재요청<br/><strong class="step2">-</strong></a>
	                        </li>
	                        <li class="last">
	                           <a href="/mwork/FM-MWORK013.do#R">반려<br/><strong class="step3">-</strong></a>
	                        </li>
	                    </ul>
	                    <c:if test="${empty topMenuUrl}">
			    	    <div id="durationTable" class="durationTable">
							<h2 class="hidden">업무진행 기간</h2>
	                        <span id="manCylDate"></span>
	                        <ul class="btnArea">
								<li class="beforeDuration"><a href="javascript:getManCyl('pre');"><span>이전 기간</span>&lt;</a></li>
								<li class="afterDuration"><a href="javascript:getManCyl('next');"><span>다음 기간</span>&gt;</a></li>
							</ul>
						</div>
					    </c:if>
	         	        <ul class="noticeUl" style="display:none;">
	                        <li class="On">
	                            <dl>
	                                <dt><a href="#none">공지사항</a></dt>
	                                <dd>
	                                    <ul class="list" id="notiList"></ul>
	                                    <a href="/board/FM-BOARD001.do" class="btnMore">더보기 <span>></span></a>
	                                </dd>
	                            </dl>
	                        </li>
	                        <li>
	                            <dl>
	                                <dt><a href="#none">알림</a></dt>
	                                <dd>
	                                    <ul class="list" id="alarmList"></ul>
	                                    <a href="/mwork/FM-MWORK012.do" class="btnMore">더보기 <span>></span></a>
	                                </dd>
	                            </dl>
	                        </li>
	                    </ul>
			        </div>
			      <c:if test="${empty topMenuUrl}">
			        <div id="quckService" class="quckService menu3Tab">
			            <h2>빠른 서비스</h2>
			            <ul id="leftMenu" class="list">
			            <c:forEach var="spdMenu" items="<%=spdMenuList%>" varStatus="status">
			              	<li class="mn"><a href="javascript:goLayoutPage('${spdMenu.ummMnuKey}', '${spdMenu.ummMnuUrl}');"><c:out value="${spdMenu.ummMnuNam}"/></a></li>
			            </c:forEach>
			            </ul>
			        </div>
			      </c:if>
			      <c:if test="${not empty topMenuUrl}">
					<div id="durationTable" class="durationTable">
						<h2 class="hidden">업무진행 기간</h2>
                        <span id="manCylDate"></span>
                        <ul class="btnArea">
							<li class="beforeDuration"><a href="javascript:getManCyl('pre');"><span>이전 기간</span>&lt;</a></li>
							<li class="afterDuration"><a href="javascript:getManCyl('next');"><span>다음 기간</span>&gt;</a></li>
						</ul>
					</div>
					<div id="subTitle" class="subTitle">
						<h2><c:out value="${topMnuNam}"/></h2>
					</div>
					<ul id="leftMenu" class="lnb">
					<c:forEach var="leftMenu" items="<%=menuList%>" varStatus="status">
					  <c:set var="leftUmmMnuUrl" value="${fn:split(leftMenu.ummMnuUrl, '/')}"/>
				  	  <c:if test="${not empty leftMenu.ummMnuPrt and fn:substring(leftUmmMnuUrl[fn:length(leftUmmMnuUrl)-1],0,8) eq topMenuUrl}">
						<c:choose>
						  <c:when test="${fn:substring(leftUmmMnuUrl[fn:length(leftUmmMnuUrl)-1],8,11) eq leftMenuUrl}">
							<li id='${leftMenuUrl}' class="sel">
								<span>
									<a href="${pageContext.request.contextPath}<c:out value="${leftMenu.ummMnuUrl}"/>"><c:out value="${leftMenu.ummMnuNam}"/></a>
								</span>
							</li>
		  				  </c:when>
						  <c:otherwise>
							<li id='${leftMenuUrl}'>
								<span>
                                    <a href="${pageContext.request.contextPath}<c:out value="${leftMenu.ummMnuUrl}"/>"
                                            <c:if test="${ fn:contains(leftMenu.ummMnuUrl, '_POPUP.do') }">target="_blank"</c:if>>
										<c:out value="${leftMenu.ummMnuNam}"/>
									</a>
								</span>
							</li>
						 </c:otherwise>
						</c:choose>
				  	  </c:if>
					</c:forEach>
					</ul>
				  </c:if>
			    </aside>
			    <article class="<c:if test="${empty topMenuUrl}">main</c:if>" id="content-box">
					<dec:body/>
			    </article>
			</div>
		</div>
		<footer>COPYRIGHT 2015 BY SK broadband CO.LTD. ALL RIGHTS RESERVED.</footer>
	</div>
	<form id="userDetailFormM" name="userDetailFormM" action="/common/FM-COMMON_myinfo_popup.do" method="post" target="userDetailPopupM">
		<input type="hidden" id="mode" name="mode" value="">
		<input type="hidden" id="page" name="page" value="main">
	</form>
	<form id="postPopup" name="postPopup" method="post"></form>
</body>
</html>