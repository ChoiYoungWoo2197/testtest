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
<link rel="stylesheet" type="text/css" href="/common/jqGrid/css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="/common/css/jquery-ui.css"/>
<link rel="stylesheet" type="text/css" href="/common/css/reset.css"/>
<link rel="stylesheet" type="text/css" href="/common/css/common.css"/>
<link rel="stylesheet" type="text/css" href="/common/css/pop.css"/>
<!--[if IE]> <script src="/common/js/html5.js"></script> <![endif]-->
<script type="text/javascript" src="/common/js/jquery.js"></script>
<script type="text/javascript" src="/common/js/jquery.form.js"></script>
<script type="text/javascript" src="/common/js/jquery.blockUI.js"></script>
<script type="text/javascript" src="/common/js/ismsUI.js"></script>
<script type="text/javascript" src="/common/js/common.js"></script>
<script type="text/javascript" src="/common/jqGrid/js/i18n/grid.locale-kr.js"></script>
<script type="text/javascript" src="/common/jqGrid/js/jquery.jqGrid.js"></script>
<script  type="text/javascript">
$(document).ready(function() {
	// 로딩바 적용
	loadingBarSet();
	serachList();

});

function serachList(){
	var formData = $("#searchForm").serialize();

	$("#gridTb").jqGrid({
		url:"${pageContext.request.contextPath}/riskm/FM-RISKM009_rst_list.do",
		postData : formData,
		datatype : "json",
		mtype : "post",
		colNames:['분류', '통제목적','통제사항','내용','진단결과'],
		colModel:[
			{name:'usmSroLv0',	index:'usmSroLv0',	width: 120,	align:'left',	hidden:false},
			{name:'usmSroLv1',	index:'usmSroLv1',	width: 150,	align:'left',	hidden:false},
			{name:'usmSroLv2',	index:'usmSroLv2',	width: 200,	align:'left',	hidden:false},
			{name:'usmSroDes',	index:'usmSroDes',	width: 270,	align:'left',	hidden:false},
		  	{name:'urrRskRst',	index:'urrRskRst',	width: 60,	align:'center',	hidden:false}
		],
		height: 400,		// 전체 grid 화면의 세로 사이즈 지정.
		pager : '#pager',
		rowNum : '300',
//			rowList : [10, 20, 50],
		rownumbers : true,	// 각 row의 맨 앞줄에 각 row의 번호가 자동적으로 부여된다.
		sortable : true,	// 초기 sort 조건과 관련되며 ture이면 초기 데이터가 정렬되서 보인다.
		sortname : 'urvRskDiv',	// 초기 디이터 정렬시 정렬조건, 서버에 정렬  조건으로 전송된다.
		sortorder : 'asc',	// 순방향, 역방향 정렬(desc, asc)
		viewrecords : false,	// 오른쪽 하단에 총 몇개중 몇번째꺼 표시
		loadonce : false,	// reload 여부이며, true로 설정시 한번만 데이터를 받아오고 그 다음부터는 데이터를 받아오지 않는다.
		multiselect: false,	// select box 적용
		loadtext : 'loading..',	// 서버연동시 loading 중이라는 표시가 뜨는데 그곳의 문자열 지정.
		jsonReader:{
			repeatitems : true,
			root : function (obj) {return obj.result;},
		},loadComplete: function(xhr) {
		},loadError: function(xhr,st,err) {
			alert(err);
		}
	});
	// jqgrid 페이징바
	/* $("#gridTb").jqGrid('navGrid','#pager'
		,{ excel:false,add:false,edit:false,view:false,del:false,search:false,refresh:false}
	); */
	// jqgrid 전체 넓이 지정, 가로 스크롤 생성
	$("#gridTb").setGridWidth(860, false);
}
</script>
</head>
<body>
	<form id="searchForm" method="post" name="searchForm">
		<div id="skipnavigation">
		    <ul>
		        <li><a href="#content-box">본문 바로가기</a></li>
		    </ul>
		</div>
		<div id="wrap" class="pop">
			<header>
				<div class="borderBox"></div>
				<h1>취약점 점검 결과</h1>
			</header>
			<article class="cont" id="content-box">
				<div class="cont_container">
					<div class="contents">
						<div class="talbeArea">
							<table id="gridTb">
							</table>
						</div>
					</div>
				</div>
				<div class="centerBtnArea">
					<button type="button" class="popClose close"><span class="icoClose">닫기</span></button>
					<button class="btnStrong close" onclick="return false;" >닫기</button>
				</div>
			</article>
		</div>
		<input type="hidden" name="udmDepCod" id="udmDepCod" value='<c:out value="${paramMap.udmDepCod}"/>'/>
	</form>
</body>
</html>