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
<link rel="stylesheet" type="text/css" href="/common/jqGrid/css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="/common/css/jquery-ui.css"/>
<link rel="stylesheet" type="text/css" href="/common/css/reset.css"/>
<link rel="stylesheet" type="text/css" href="/common/css/common.css"/>
<link rel="stylesheet" type="text/css" href="/common/css/main.css"/>
<link rel="stylesheet" type="text/css" href="/common/css/sub.css"/>
<link rel="stylesheet" type="text/css" href="/common/css/pop.css"/>
<!--[if IE]> <script src="/common/js/html5.js"></script> <![endif]-->
<!--[if IE]> <script src="/common/js/html5.js"></script> <![endif]-->
<script type="text/javascript" src="/common/js/ga.js"></script>
<script type="text/javascript" src="/common/js/jquery.js"></script>
<script type="text/javascript" src="/common/js/common.js"></script>
<script type="text/javascript" src="/common/js/canvasjs.min.js"></script>
<script type="text/javascript" src="/common/js/ismsUI.js"></script> 
<script type="text/javascript" src="/common/js/jquery.form.js"></script>
<script type="text/javascript" src="/common/js/jquery.blockUI.js"></script>
<script type="text/javascript" src="/common/js/jquery-ui.js"></script>
<script type="text/javascript" src="/common/jqGrid/js/i18n/grid.locale-kr.js"></script>
<script type="text/javascript" src="/common/jqGrid/js/jquery.jqGrid.js"></script>
<script  type="text/javascript">

$(document).ready(function() {
	initGrid();
});

function initGrid(){
	var formData = $("#uumForm").serialize();
	
	$("#gridTb").jqGrid({
		url:"${pageContext.request.contextPath}/mwork/FM-MWORK006_USRLIST.do",
		postData : formData,
		datatype : "json",
		mtype : "post", 
		colNames:['key','사번','이름','부서'],  
		colModel:[
		          {name:'uumUsrKey',	index:'uumUsrKey',									hidden:true},
		          {name:'uumUsrId',		index:'uumUsrId',		width: 80,	align:'center',	hidden:false},
		          {name:'uumUsrNam',	index:'uumUsrNam',		width: 80,	align:'center',	hidden:false},
		          {name:'uagDepNam',	index:'uagDepNam',		width: 140,	align:'center',	hidden:false}
		], 
		width: 300,
		height: 275,		// 전체 grid 화면의 세로 사이즈 지정.
		rowNum : '0',
//			rowList : [10, 20, 50],
		rownumbers : true,	// 각 row의 맨 앞줄에 각 row의 번호가 자동적으로 부여된다.
		sortable : true,	// 초기 sort 조건과 관련되며 ture이면 초기 데이터가 정렬되서 보인다.
		sortname : 'uumUsrId',	// 초기 디이터 정렬시 정렬조건, 서버에 정렬  조건으로 전송된다.
		sortorder : 'desc',	// 순방향, 역방향 정렬(desc, asc)
		viewrecords : false,	// 오른쪽 하단에 총 몇개중 몇번째꺼 표시
		loadonce : false,	// reload 여부이며, true로 설정시 한번만 데이터를 받아오고 그 다음부터는 데이터를 받아오지 않는다.
		multiselect: false,	// select box 적용
		loadtext : 'loading..',	// 서버연동시 loading 중이라는 표시가 뜨는데 그곳의 문자열 지정.
		jsonReader:{
			repeatitems : true,
			root : function (obj) {return obj.result;},
		},
		/* onSelectRow: function(id){
			var uumUsrKey = $("#gridTb").getRowData(id).uumUsrKey; 
			var uumUsrId = $("#gridTb").getRowData(id).uumUsrId; 
			var uumUsrNam = $("#gridTb").getRowData(id).uumUsrNam; 
			opener.document.popForm.ursMngKey.value = uumUsrKey;
			opener.document.popForm.ursMesMng.value = uumUsrId;
			opener.document.popForm.ursMngNam.value = uumUsrNam;
		},
		ondblClickRow: function(id){
			var uumUsrKey = $("#gridTb").getRowData(id).uumUsrKey; 
			var uumUsrId = $("#gridTb").getRowData(id).uumUsrId; 
			var uumUsrNam = $("#gridTb").getRowData(id).uumUsrNam; 
			opener.document.popForm.ursMngKey.value = uumUsrKey;
			opener.document.popForm.ursMesMng.value = uumUsrId;
			opener.document.popForm.ursMngNam.value = uumUsrNam;
		}, */
		onSelectRow: function(id){
			var uumUsrKey = $("#gridTb").getRowData(id).uumUsrKey; 
			var uumUsrId = $("#gridTb").getRowData(id).uumUsrId; 
			var uumUsrNam = $("#gridTb").getRowData(id).uumUsrNam; 
			
			opener.document.userForm.uumAgnKey.value = uumUsrKey;
			opener.document.userForm.uumAgnId.value = uumUsrId;
			opener.document.userForm.uumAgnNam.value = uumUsrNam;
			opener.document.userForm.wrkKey.value = uumUsrKey;
		},
		ondblClickRow: function(id){
			var uumUsrKey = $("#gridTb").getRowData(id).uumUsrKey; 
			var uumUsrId = $("#gridTb").getRowData(id).uumUsrId; 
			var uumUsrNam = $("#gridTb").getRowData(id).uumUsrNam; 
			opener.document.userForm.uumAgnKey.value = uumUsrKey;
			opener.document.userForm.uumAgnId.value = uumUsrId;
			opener.document.userForm.uumAgnNam.value = uumUsrNam;
		},
		loadComplete: function(xhr) {
		},
		loadError: function(xhr,st,err) {
			alert(err);
		},
	});
	// jqgrid 전체 넓이 지정, 가로 스크롤 생성
	$("#gridTb").setGridWidth(555, true);
}

function searchListB(){
	$("#gridTb").clearGridData();	// 이전 데이터 삭제
	
	var formData = $("#uumForm").serialize();
	$("#gridTb").setGridParam({
		postData : formData
	}).trigger("reloadGrid");
}
</script>
</head>
<body>
<form id="uumForm" name="uumForm" method="post">
	<div id="skipnavigation">
	    <ul>
	        <li><a href="#content-box">본문 바로가기</a></li>
	    </ul>
	</div>
	<div id="wrap" class="pop">
		<header>
			<div class="borderBox"></div>
			<h1>사용자 조회</h1>
		</header>
		<article class="cont" id="content-box">
			<div class="cont_container">
				<div class="search">
				    <div class="defalt">
				    	<fieldset class="searchForm">
				    		<ul class="detail">
				    			<li>
				    				<span class="title"><label for="uumUsrId">사용자 ID</label></span> 
				    				<input type="text" id="uumUsrId" name="uumUsrId" class="inputText" title="사번" placeholder="사번"/>
				    			</li>
				    			<li>
				    				<span class="title"><label for="uumUsrNam">사용자 명</label></span> 
				    				<input type="text" id="uumUsrNam" name="uumUsrNam" class="inputText" title="이름" placeholder="이름"/>
				    			</li>
				    		</ul>
				            <button onclick="searchListB(); return false;" class="btnSearch defaltBtn">조건으로 검색</button>
				        </fieldset>
					</div>
				</div>
				<div class="talbeArea">
					<table id="gridTb">  
					</table>
				</div>
			</div>
	       <div class="centerBtnArea closeArea">
               <button class="btnStrong close">닫기</button>
           </div>
	    </article>
	</div>
</form>
</body>
</html>