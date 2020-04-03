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
var ctx = "${pageContext.request.contextPath}";
var setId = 0;
$(document).ready(function() {
	initGrid();

	$("#btnSave").click(function() {
		if (setId == 0) {
			alert("담당자가 선택되지 않았습니다.");
			return false;
		}
		if (confirm("위험관리 부서 담당자로 지정하시겠습니까?")) {
			var usrKey = $("#gridTb").getRowData(setId).uumUsrKey;
			$.post("${pageContext.request.contextPath}/riskm/FM-RISKM010_updateRskDepMng.do",
				{ depKey: $("#depKey").val(), usrKey: usrKey }, function(data) {
					if (data.result == "true") {
						alert("담당자가 지정 되었습니다.");
						try {
							opener.searchListB();
						}
						catch (ex) {}
						finally {
							self.close();
						}
					} else {
						alert("담당자 지정 처리가 실패 했습니다.");
					}
			}, "json")
			.fail(function() {
   				 alert("처리 중 에러가 발생했습니다.");
  			});
		}
	});
});

function initGrid(){
	var formData = $("#uumForm").serialize();

	$("#gridTb").jqGrid({
		url:"${pageContext.request.contextPath}/common/FM-COMMON_userlist.do",
		postData : formData,
		datatype : "json",
		mtype : "post",
		colNames:['key','서비스','부서','사번','이름','직급'],
		colModel:[
		          {name:'uumUsrKey',	index:'uumUsrKey',									hidden:true},
		          {name:'uumSvcNam',	index:'uumSvcNam',		width: 80,	align:'center',	hidden:false},
		          {name:'uagDepNam',	index:'uagDepNam',		width: 120,	align:'center',	hidden:false},
		          {name:'uumUsrId',		index:'uumUsrId',		width: 80,	align:'center',	hidden:false},
		          {name:'uumUsrNam',	index:'uumUsrNam',		width: 90,	align:'center',	hidden:false},
		          {name:'uumPosNam',	index:'uumPosNam',		width: 90,	align:'center',	hidden:false}
		],
		width: 300,
		height: 330,		// 전체 grid 화면의 세로 사이즈 지정.
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
		onSelectRow: function(id) {
			setId = id;
		},
		ondblClickRow: function(id) {
			setId = id;
		},
		loadComplete: function(xhr) {
		}
	});
	// jqgrid 전체 넓이 지정, 가로 스크롤 생성
	$("#gridTb").setGridWidth(555, true);
}
</script>
</head>
<body>
<form id="uumForm" name="uumForm" method="post">
	<input type="hidden" id="depKey" name="depKey" value="<c:out value="${depKey}"/>"/>
	<input type="hidden" id="gpOrg" name="gpOrg" value="<c:out value="${depCod}"/>"/>

	<div id="skipnavigation">
	    <ul>
	        <li><a href="#content-box">본문 바로가기</a></li>
	    </ul>
	</div>
	<div id="wrap" class="pop">
		<header>
			<div class="borderBox"></div>
			<h1>취약점 대상부서 담당자 변경</h1>
		</header>
		<article class="cont" id="content-box">
			<div class="cont_container">
				<div class="talbeArea">
					<table id="gridTb">
					</table>
				</div>
			</div>
	       <div class="centerBtnArea bottomBtnArea">
				<button type="button" class="popClose close"><span class="icoClose">닫기</span></button>
				<ul class="btnList">
					<li><button type="button" class="btnStrong" id="btnSave"><span class="icoSave"></span>저장</button></li>
				</ul>
           </div>
	    </article>
	</div>
</form>
</body>
</html>