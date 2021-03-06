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
	var formData = $("#usoForm").serialize();
	
	$("#gridTb").jqGrid({
		url:"${pageContext.request.contextPath}/riskm/FM-RISKM007_COCLIST.do",
		postData : formData,
		datatype : "json",
		mtype : "post", 
		colNames:['코드','명'],
		colModel:[
		          {name:'usoCocCod',	index:'usoCocCod',		width: 150,	align:'center',	hidden:false},
		          {name:'usoCocNam',	index:'usoCocNam',		width: 200,	align:'center',	hidden:false}
		], 
		width: 300,
		height: 275,		// 전체 grid 화면의 세로 사이즈 지정.
		rowNum : '300',
//			rowList : [10, 20, 50],
		rownumbers : true,	// 각 row의 맨 앞줄에 각 row의 번호가 자동적으로 부여된다.
		sortable : true,	// 초기 sort 조건과 관련되며 ture이면 초기 데이터가 정렬되서 보인다.
		sortname : 'usoCocCod',	// 초기 디이터 정렬시 정렬조건, 서버에 정렬  조건으로 전송된다.
		sortorder : 'desc',	// 순방향, 역방향 정렬(desc, asc)
		viewrecords : false,	// 오른쪽 하단에 총 몇개중 몇번째꺼 표시
		loadonce : false,	// reload 여부이며, true로 설정시 한번만 데이터를 받아오고 그 다음부터는 데이터를 받아오지 않는다.
		multiselect: false,	// select box 적용
		loadtext : 'loading..',	// 서버연동시 loading 중이라는 표시가 뜨는데 그곳의 문자열 지정.
		jsonReader:{
			repeatitems : true,
			root : function (obj) {return obj.result;},
		},
		onSelectRow: function(id){
			var usoCocCod = $("#gridTb").getRowData(id).usoCocCod; 
			var usoCocNam = $("#gridTb").getRowData(id).usoCocNam; 

			opener.document.usoForm.usoCocCod.value = usoCocCod;
			opener.document.usoForm.usoCocNam.value = usoCocNam;
		},
		ondblClickRow: function(id){
			var urvRskKey = $("#gridTb").getRowData(id).urvRskKey;        
		},
		loadComplete: function(xhr) {
		},
		loadError: function(xhr,st,err) {
			alert(err);
		},
	});
	// jqgrid 페이징바
	/* $("#gridTb").jqGrid('navGrid','#pager'
		,{ excel:false,add:false,edit:false,view:false,del:false,search:false,refresh:false} 
	); */
	// jqgrid 전체 넓이 지정, 가로 스크롤 생성
	$("#gridTb").setGridWidth(555, true);
}

function searchListB(){
	$("#gridTb").clearGridData();	// 이전 데이터 삭제
	
	var formData = $("#usoForm").serialize();
	$("#gridTb").setGridParam({
		postData : formData
	}).trigger("reloadGrid");
}
</script>
</head>
<body>
<form id="usoForm" name="usoForm" method="post">
	<div id="skipnavigation">
	    <ul>
	        <li><a href="#content-box">본문 바로가기</a></li>
	    </ul>
	</div>
	<div id="wrap" class="pop">
		<header>
			<div class="borderBox"></div>
			<h1>우려사항 리스트</h1>
		</header>
		<article class="cont" id="content-box">
			<div class="cont_container">
				<div class="search">
				    <div class="defalt">
				    	<fieldset class="searchForm">
				    		<ul class="detail">
				    			<li>
				    				<span class="title"><label for="usoCocCod">코드</label></span> 
				    				<input type="text" id="usoCocCod" name="usoCocCod" class="inputText" title="우려사항코드" placeholder="우려사항코드"/>
				    			</li>
				    			<li>
				    				<span class="title"><label for="usoCocNam">명</label></span> 
				    				<input type="text" id="usoCocNam" name="usoCocNam" class="inputText" title="우려사항명" placeholder="우려사항명"/>
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