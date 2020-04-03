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
		url:"${pageContext.request.contextPath}/riskm/FM-RISKM008_dep_list.do",
		postData : formData,
		datatype : "json",
		mtype : "post",
		colNames:['부서코드','부서명','서비스코드','서비스명'],
		colModel:[
		          {name:'udmDepCod',	index:'udmDepCod',		width: 100,	align:'center',	hidden:false},
		          {name:'udmDepNam',	index:'udmDepNam',		width: 150,	align:'left',	hidden:false},
		          {name:'uagSvrCod',	index:'uagSvrCod',		                        	hidden:true},
		          {name:'uagSvrNam',	index:'uagSvrNam',		width: 100,	align:'center',	hidden:false}
		],
		width: 300,
		height: 310,		// 전체 grid 화면의 세로 사이즈 지정.
		rowNum : '0',
//			rowList : [10, 20, 50],
		rownumbers : true,	// 각 row의 맨 앞줄에 각 row의 번호가 자동적으로 부여된다.
		sortable : true,	// 초기 sort 조건과 관련되며 ture이면 초기 데이터가 정렬되서 보인다.
		sortname : 'uumUsrId',	// 초기 디이터 정렬시 정렬조건, 서버에 정렬  조건으로 전송된다.
		sortorder : 'desc',	// 순방향, 역방향 정렬(desc, asc)
		viewrecords : false,	// 오른쪽 하단에 총 몇개중 몇번째꺼 표시
		loadonce : false,	// reload 여부이며, true로 설정시 한번만 데이터를 받아오고 그 다음부터는 데이터를 받아오지 않는다.
		multiselect: true,	// select box 적용
		loadtext : 'loading..',	// 서버연동시 loading 중이라는 표시가 뜨는데 그곳의 문자열 지정.
		jsonReader:{
			repeatitems : true,
			root : function (obj) {return obj.result;},
		},
		onSelectRow: function(id){
		},
		ondblClickRow: function(id){
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

function addRskDepList(){
	var list = $("#gridTb").jqGrid('getGridParam', 'selarrrow');
	var addList = "";
	for(var i = 0; i < list.length; i++){
		var value = $("#gridTb").jqGrid('getCell', list[i], 'udmDepCod');
		if ( i == list.length-1){
			addList += value;                                 //  선택된 행의 Code를 줄줄이 가져옴
		} else{
			addList += value + ",";
		}
	}

	if(addList.length <= 0 ){
		alert("선택된 부서가 없습니다.");
		return;
	}

	if( confirm('추가하시겠습니까?') ) {
		$.ajax({
			url			: "${pageContext.request.contextPath}/riskm/FM-RISKM008_addRskDepList.do",
			type		: "post",
			data		: {'addList':addList},
			dataType	: "json",
			async		: false,
			success		: function(data){
				window.opener.searchListB();
				alert("정상적으로 추가 되었습니다.");
			},
			error : function(){
				alert('error');
			}
		});
	}else{
		return;
	}
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
			<h1>취약점 대상부서 추가</h1>
		</header>
		<article class="cont" id="content-box">
			<div class="cont_container">
				<div class="search">
				    <div class="defalt">
				    	<fieldset class="searchForm">
				    		<ul class="detail">
				    			<li>
				            		<span class="title"><label for="service">서비스</label></span>
				                    <sb:select name="service" type="S"/>
				            	</li>
				    			<li>
				    				<span class="title"><label for="udmDepCod">부서코드</label></span>
				    				<input type="text" id="udmDepCod" name="udmDepCod" class="inputText" title="코드" placeholder="코드"/>
				    			</li>
				    			<li>
				    				<span class="title"><label for="udmDepNam">부서 명</label></span>
				    				<input type="text" id="udmDepNam" name="udmDepNam" class="inputText" title="부서명" placeholder="부서명"/>
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
			<div class="centerBtnArea bottomBtnArea">
				<button type="button" class="popClose close"><span class="icoClose">닫기</span></button>
				<ul class="btnList">
					<li><button type="button" class="btnStrong" onclick="addRskDepList();"><span class="icoSave"></span>저장</button></li>
				</ul>
			</div>
	    </article>
	</div>
</form>
</body>
</html>