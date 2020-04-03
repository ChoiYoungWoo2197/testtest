<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
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
<link rel="stylesheet" type="text/css" href="/common/css/jquery-ui.css"/>
<!--[if IE]> <script src="/common/js/html5.js"></script> <![endif]-->
<script type="text/javascript" src="/common/js/jquery.js"></script>
<script type="text/javascript" src="/common/js/ismsUI.js"></script>
<script type="text/javascript" src="/common/js/jquery.form.js"></script>
<script type="text/javascript" src="/common/js/jquery.blockUI.js"></script>
<script type="text/javascript" src="/common/js/jquery-ui.js"></script>
<script type="text/javascript" src="/common/js/jquery.chained.min.js"></script>
<script type="text/javascript" src="/common/js/common.js"></script>
<script  type="text/javascript">
var dCnt = 0;

$(function() {
	loadingBarSet();	// 로딩바 적용
});

function userSave(){
	var mode = '${mode}';
	if(mode == 0){
		dCnt = 1;
	}

	if(dCnt == 0 ){
		alert("중복확인을 눌러주세요");
		return;
	}

	if($("#uumUsrNam").val() == ''){
		alert("이름을 입력하세요.");
		return;
	}

	var pwd = $("#uumUsrPwd").val();
	/*
	if(pwd == '' || pwd == null){
		alert("비밀번호를 입력하세요.");
		return;
	}
	*/
	var pwd_r = $("#uumUsrPwdR").val();
	/*
	if(pwd_r == '' || pwd_r == null){
		alert("비밀번호확인을 입력하세요.");
		return;
	}
	*/
	/*
	if($("#uumMalAds").val() == ''){
		alert("이메일을 입력하세요.");
		return;
	}
	if($("select[name=uumSvcCod]").val() == ''){
		alert("서비스를 선택하세요.");
		return;
	}

	if($("select[name=uumDepCod]").val() == ''){
		alert("부서를 선택하세요.");
		return;
	}
	*/
	if($('#stOrg').val() == ''){
		alert("본부을 선택하세요.");
		return;
	}
	/*
	if($("select[name=uumPosCod]").val() == ''){
		alert("직급을 선택하세요.");
		return;
	}
	*/
	if($("select[name=uumAuhKey]").val() == ''){
		alert("담당자권한을 선택하세요.");
		return;
	}

	if (pwd) {
		var result = validatePassword(pwd);
		if (result) {
			alert(result);
			return;
		}
	}

	if(pwd != pwd_r){
		alert("비밀번호가 다릅니다.");
		return;
	}

	if($('#gpOrg').val() != ''){
		$('input[name=uumDepCod]').val($('#gpOrg').val());
	}else if($('#hqOrg').val() != ''){
		$('input[name=uumDepCod]').val($('#hqOrg').val());
	}else if($('#stOrg').val() != ''){
		$('input[name=uumDepCod]').val($('#stOrg').val());
	}

	var url = ""
	var mode = "${mode}";
	if(mode == '1'){
		url = "${pageContext.request.contextPath}/setup/FM-SETUP007_W.do";
	} else {
		url = "${pageContext.request.contextPath}${updatePage}";
	}

	$.ajax({
		url			: url,
		type		: "post",
		data		: $("#userForm").serialize(),
		dataType	: "json",
		success	: function(data){
			if(data.result == "S") {
				alert("저장되었습니다.");
				$(opener.location).attr("href", "javascript:fn_egov_search_Restde();" );
				window.close();
			}
		},
		error : function(){
			alert('error');
		}
	});
}

function userDuplicateSearch(){
	var uId = $("#dUumUsrId").val();

	if(uId == null || uId == ""){
		alert("아이디를 입력하세요");
		return;
	}else{
		$.ajax({
			url		: "${pageContext.request.contextPath}/setup/FM-SETUP007_DUPLICATE_TEST.do",
			type		: "post",
			data		: {"uId":uId},
			dataType	: "json",
			success	: function(data){
				var cnt  = data.cnt;
				if(cnt > 0){
					alert('사용할 수 없는 아이디입니다.');
				}else{
					alert("사용할 수 있는 아이디입니다.");
					$("#dUumUsrId").prop("disabled","true");
					$("#uumUsrId").val($("#dUumUsrId").val());
					dCnt = 1;
				}
			},
			error : function(){
				alert('error');
			}
		});
	}
}

$(function() {
	stOrgList();
	$( "#uumAgnStr, #uumAgnEnd" ).datepicker({
		dateFormat: 'yy-mm-dd'
	});

	$(".btnCal").click(function() {
		$(this).prev().focus();
    });

	// 2017-02-13, 1차 결재자
	$("#uumApvOneNam").click(function() {
		if ($("#uumApvOne").val()) {
			if (confirm("1차 결재자를 초기화 하시겠습니까?")) {
				$("#uumApvOneNam").val("");
				$("#uumApvOne").val("");
			}
		}
		else {
			$("#btnApvOne").click();
		}
	});
	// 2017-02-13, 2차 결재자
	$("#uumApvTwoNam").click(function() {
		if ($("#uumApvTwo").val()) {
			if (confirm("2차 결재자를 초기화 하시겠습니까?")) {
				$("#uumApvTwoNam").val("");
				$("#uumApvTwo").val("");
			}
		}
		else {
			$("#btnApvTwo").click();
		}
	});
	// 2017-02-13, 대무자
	$("#uumAgnNam").click(function() {
		if ($("#uumAgnId").val()) {
			if (confirm("대무자를 초기화 하시겠습니까?")) {
				$("#uumAgnNam").val("");
				$("#uumAgnId").val("");
			}
		}
		else {
			$("#btnAgn").click();
		}
	});
});
</script>
</head>
<body>
<form id="userForm" name="userForm" method="post" >
<div id="skipnavigation">
    <ul>
        <li><a href="#content-box">본문 바로가기</a></li>
    </ul>
</div>
<div id="wrap" class="pop">
	<header>
		<div class="borderBox"></div>
		<h1>계정 설정</h1>
	</header>
	<article class="cont" id="content-box">
		<div class="cont_container">
			<div class="contents">
				<div class="talbeArea">
			        <table summary="계정 설정">
			            <colgroup>
			                <col width="32%" />
							<col width="*" />
			            </colgroup>
			            <caption>계정 설정</caption>
			            <tbody>

			                <tr>
		            			<th scope="row" class="listTitle"><label for="dUumUsrId">* 아이디</label></th>
		                		<td class="fontLeft last">
		                		<c:if test="${mode == '1'}">
		                			<input type="hidden" id="uumUsrId" name="uumUsrId">
									<input type="text" id="dUumUsrId" name="dUumUsrId" class="inputText" maxlength="25" style="width:120px" value="<c:out value="${user.uumUsrId}"/>">
									&nbsp;<button type="button" onclick="userDuplicateSearch()">중복확인</button>
								</c:if>
								<c:if test="${mode == '0'}">
									<input type="hidden" id="uumUsrId" name="uumUsrId" value="<c:out value="${user.uumUsrId}"/>">
									<c:out value="${user.uumUsrId}"/>
								</c:if>
		                		</td>
					    	</tr>
					    	<tr>
		            			<th scope="row" class="listTitle"><label for="uumUsrNam">* 이름</label></th>
		                		<td class="fontLeft last"><input type="text" class="inputText" style="width:120px" id="uumUsrNam" name="uumUsrNam" maxlength="25" value="<c:out value="${user.uumUsrNam}"/>"></td>
					    	</tr>
					    	<tr>
		            			<th scope="row" class="listTitle"><label for="uumUsrPwd">* 비밀번호</label></th>
		                		<td class="fontLeft last"><input type="password" class="inputText" style="width:120px"  id="uumUsrPwd" name="uumUsrPwd" maxlength="25" value=""></td>
					    	</tr>
					    	<tr>
		            			<th scope="row" class="listTitle"><label for="uumUsrPwdR">* 비밀번호확인</label></th>
		                		<td class="fontLeft last"><input type="password" class="inputText" style="width:120px"  id="uumUsrPwdR" name="uumUsrPwdR" maxlength="25" value=""></td>
					    	</tr>
					    	<tr>
		            			<th scope="row" class="listTitle"><label for="uumMalAds">이메일</label></th>
		                		<td class="fontLeft last"><input type="text"  class="inputText" style="width:250px"  id="uumMalAds" name="uumMalAds" maxlength="50" value="<c:out value="${user.uumMalAds}"/>"></td>
					    	</tr>
					    	<tr>
		            			<th scope="row" class="listTitle">서비스</th>
		                		<td class="fontLeft last">
			                		<sb:select name="uumSvcCod" type="S" value="${user.uumSvcCod}" allText="선택" forbidden="true"/>
		                		</td>
					    	</tr>
					    	<tr>
					    		<th scope="row" class="listTitle">* 본부</th>
					    		<td class="fontLeft last">
						    		<sb:select name="stOrg" type="stOrg" value="${user.uumDepLv1}" forbidden="true" allText="선택" />
								</td>
							</tr>
							<tr>
					    		<th scope="row" class="listTitle">그룹,담당</th>
					    		<td class="fontLeft last">
					    			<sb:select name="hqOrg" type="hqOrg" value="${user.uumDepLv2}" forbidden="true" allText="선택" />
								</td>
							</tr>
							<tr>
					    		<th scope="row" class="listTitle">팀</th>
					    		<td class="fontLeft last">
					    			<sb:select name="gpOrg" type="gpOrg" value="${user.uumDepLv3}" forbidden="true" allText="선택" />
								</td>
							</tr>
							<tr>
		            			<th scope="row" class="listTitle"><label for="uumPosCod">직급</label></th>
		                		<td class="fontLeft last">
			                		<select id="uumPosCod" name="uumPosCod" class="selectBox">
			                			<option value="" >선택</option>
									<c:forEach var="pos" items="${posList }" varStatus="status">
										<option value="${pos.uccSndCod }" <c:if test="${pos.uccSndCod == user.uumPosCod}">selected="selected" </c:if> >${pos.uccSndNam }</option>
									</c:forEach>
									</select>
		                		</td>
					    	</tr>
					    	<tr>
		            			<th scope="row" class="listTitle"><label for="uumCelNum">휴대전화</label></th>
		                		<td class="fontLeft last"><input type="text" id="uumCelNum" name="uumCelNum" class="inputText" style="width:120px" maxlength="25"  value="<c:out value="${user.uumCelNum}"/>"></td>
					    	</tr>
					    	<tr>
		            			<th scope="row" class="listTitle"><label for="uumTelNum">일반전화</label></th>
		                		<td class="fontLeft last"><input type="text" id="uumTelNum" name="uumTelNum" class="inputText" style="width:120px" maxlength="25" value="<c:out value="${user.uumTelNum}"/>"></td>
					    	</tr>
					    	<c:if test="${page == 'setup'}">
					    	<tr>
		            			<th scope="row" class="listTitle"><label for="uumAuhKey">* 담당권한</label></th>
		                		<td class="fontLeft last">
		                			<select id="uumAuhKey" name="uumAuhKey" class="selectBox">
		                				<option value="" >선택</option>
									<c:forEach var="auh" items="${auhList }" varStatus="status">
										<option value="${auh.uatAuhKey }" <c:if test="${auh.uatAuhKey == user.uumAuhKey}">selected="selected" </c:if> >${auh.uatAuhNam}</option>
									</c:forEach>
									</select>
		                		</td>
					    	</tr>
					    	<tr>
		            			<th scope="row" class="listTitle"><label for="uumApvGbn">* 결재권한</label></th>
		                		<td class="fontLeft last">
			                		<sb:select name="uumApvGbn" type="" code="POS_YN" value="${user.uumApvGbn}" forbidden="true" />
		                		</td>
					    	</tr>
					    	<tr>
		            			<th scope="row" class="listTitle"><label for="uumApvGbn">* 실무담당</label></th>
		                		<td class="fontLeft last">
			                		<sb:select name="uumTraGbn" type="" code="TRA_YN" value="${user.uumTraGbn}" forbidden="true" />
		                		</td>
					    	</tr>
					    	<tr>
		            			<th scope="row" class="listTitle"><label for="uumUseYn">* 사용여부</label></th>
		                		<td class="fontLeft last">
			                		<sb:select name="uumUseYn" type="" code="USER_STAT" value="${user.uumUseYn}" forbidden="true" />
		                		</td>
					    	</tr>
     						<tr>
		            			<th scope="row" class="listTitle"><label for="uumApvOne">1차결재자</label></th>
		                		<td class="fontLeft last">
					    			<input type="hidden" id="uumApvOne" name="uumApvOne" value="<c:out value='${user.uumApvOne}'/>"/>
					    			<input type="text" id="uumApvOneNam" name="uumApvOneNam" style="width: 80px;" class="inputText readonly" value="<c:out value='${user.uumApvOneNam}'/>" readonly="readonly"/>
					    			<button type="button" onclick="userListPopup('uumApvOne','','uumApvOneNam','Y');"><span class="icoPer"></span></button>
					    		</td>
					    	</tr>
					    	<tr>
		            			<th scope="row" class="listTitle"><label for="uumApvTwo">2차결재자</label></th>
		                		<td class="fontLeft last">
		                			<input type="hidden" id="uumApvTwo" name="uumApvTwo" value="<c:out value='${user.uumApvTwo}'/>"/>
					    			<input type="text" id="uumApvTwoNam" name="uumApvTwoNam" style="width: 80px;" class="inputText readonly" value="<c:out value='${user.uumApvTwoNam}'/>" readonly="readonly"/>
					    			<button type="button" onclick="userListPopup('uumApvTwo','','uumApvTwoNam','Y');"><span class="icoPer"></span></button>
		                		</td>
					    	</tr>
					    	</c:if>
					    	<tr>
					    		<th scope="row" class="listTitle"><label for="uumApvTwo">대무자</label></th>
					    		<td class="fontLeft last">
					    			<input type="hidden" id="uumAgnId" name="uumAgnId" value="<c:out value='${user.uumAgnId}'/>"/>
					    			<input type="text" id="uumAgnNam" name="uumAgnNam" style="width: 80px;" class="inputText readonly" value="<c:out value='${user.uumAgnNam}'/>" readonly="readonly"/>
					    			<button type="button" onclick="userListPopup('uumAgnId','','uumAgnNam');"><span class="icoPer"></span></button>
					    		</td>
					    	</tr>
					    	<tr>
					    		<th scope="row" class="listTitle"><label for="uumApvTwo">대무기간</label></th>
					    		<td class="last fontLeft" colspan="3">
									<input type="text" id="uumAgnStr" name="uumAgnStr"	class="inputText cal" value="<c:out value='${user.uumAgnStr}'/>"/>
									<button id="uumAgnStr_btn" type="button" class="btnCal"><span class="icoCal"><em>달력</em></span></button>
									~
									<input type="text" id="uumAgnEnd" name="uumAgnEnd" class="inputText cal" value="<c:out value='${user.uumAgnEnd}'/>"/>
									<button id="uumAgnEnd_btn" type="button" class="btnCal"><span class="icoCal"><em>달력</em></span></button>
								</td>
					    	</tr>
			            </tbody>
			        </table>
   				</div>
			</div>
		</div>
		<div class="centerBtnArea bottomBtnArea">
			<button type="button" class="popClose close"><span class="icoClose">닫기</span></button>
			<c:choose>
     			<c:when test="${mode == '1'}">
     				<button class="btnStrong" onclick="userSave(); return false;"><span class="icoAdd"></span>등록</button>
     			</c:when>
     			<c:otherwise>
     				<button class="btnStrong" onclick="userSave(); return false;"><span class="icoRepair"></span>저장</button>
     			</c:otherwise>
     		</c:choose>
		</div>
	</article>
</div>
<input type="hidden" id="userEditPage" name="userEditPage" value="${page }">
<input type="hidden" name="mode" value="${mode}">
<input type="hidden" name="uumDepCod" value="${user.uumDepCod }">
</form>
</body>
</html>