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
<script type="text/javascript" src="/common/js/jquery.chained.min.js"></script>
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
	searchList();
	stOrgList();
	$("#uumUsrNam").focus();
});

function searchList(){
	$("#gridTb").jqGrid("GridUnload");	// 동적데이터 전송시 필수
	var formData = $("#uumForm").serialize();

	$("#gridTb").jqGrid({
		url:"${pageContext.request.contextPath}/common/FM-COMMON_userlist.do",
		postData : formData,
		datatype : "json",
		mtype : "post",
		colNames:['key','서비스','부서코드','부서','사번','이름','직급'],
		colModel:[
		          {name:'uumUsrKey',	index:'uumUsrKey',									hidden:true},
		          {name:'uumSvcNam',	index:'uumSvcNam',									hidden:true},
		          {name:'uumDepCod',	index:'uumDepCod',		width: 80,	align:'center',	hidden:false},
		          {name:'uagDepNam',	index:'uagDepNam',		width: 120,	align:'center',	hidden:false},
		          {name:'uumUsrId',		index:'uumUsrId',		width: 80,	align:'center',	hidden:false},
		          {name:'uumUsrNam',	index:'uumUsrNam',		width: 100,	align:'center',	hidden:false},
		          {name:'uumPosNam',	index:'uumPosNam',		width: 90,	align:'center',	hidden:false}
		],
		width: 300,
		height: 300,		// 전체 grid 화면의 세로 사이즈 지정.
		rowNum : '0',
//			rowList : [10, 20, 50],
		rownumbers : true,	// 각 row의 맨 앞줄에 각 row의 번호가 자동적으로 부여된다.
		sortable : true,	// 초기 sort 조건과 관련되며 ture이면 초기 데이터가 정렬되서 보인다.
		sortname : 'uumUsrId',	// 초기 디이터 정렬시 정렬조건, 서버에 정렬  조건으로 전송된다.
		sortorder : 'desc',	// 순방향, 역방향 정렬(desc, asc)
		viewrecords : false,	// 오른쪽 하단에 총 몇개중 몇번째꺼 표시
		loadonce : false,	// reload 여부이며, true로 설정시 한번만 데이터를 받아오고 그 다음부터는 데이터를 받아오지 않는다.
		multiselect: false,	// select box 적용
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
		loadComplete: function (data) {
			if (data.result.length == 0) {
				$("#gridTb > tbody").append("<tr><td align='center' colspan='6'>선택 가능한 담당자가 없습니다.</td></tr>")
            }
		},
		loadError: function(xhr,st,err) {
			alert(err);
		},
	}).trigger("reloadGrid");
	// jqgrid 전체 넓이 지정, 가로 스크롤 생성
	$("#gridTb").setGridWidth(555, true);
}

function searchListB(){
	$("#gridTb").clearGridData();	// 이전 데이터 삭제
	$("#gridTb > tbody").empty();

	var formData = $("#uumForm").serialize();
	$("#gridTb").setGridParam({
		postData : formData,
		datatype : "json"
	}).trigger("reloadGrid");
}

function saveWorkerId() {
	if (setId == 0) {
		alert("담당자가 선택되지 않았습니다.");
		return false;
	}
	var uumUsrKey = $("#gridTb").getRowData(setId).uumUsrKey;
	var uumUsrId = $("#gridTb").getRowData(setId).uumUsrId;
	var uumUsrNam = $("#gridTb").getRowData(setId).uumUsrNam;
	var uumDepCod = $("#gridTb").getRowData(setId).uumDepCod;
	var uagDepNam = $("#gridTb").getRowData(setId).uagDepNam;
	try {
		if ($("#setUsrKey").val()) {
			$("#" + $("#setUsrKey").val(), opener.document).val(uumUsrKey);
		}
		if ($("#setUsrId").val()) {
			$("#" + $("#setUsrId").val(), opener.document).val(uumUsrId);
		}
		if ($("#setUsrName").val()) {
			$("#" + $("#setUsrName").val(), opener.document).val(uumUsrNam);
		}
		if ($("#setDepCode").val()) {
			$("#" + $("#setDepCode").val(), opener.document).val(uumDepCod);
		}
		if ($("#setDepName").val()) {
			$("#" + $("#setDepName").val(), opener.document).val(uagDepNam);
		}
	} catch(e) {}
	finally {
		self.close();
	}
}
</script>
</head>
<body>
<form id="uumForm" name="uumForm" method="post">
	<input type="hidden" id="setUsrKey" name="setUsrKey" value="<c:out value="${setUsrKey}"/>"/>
	<input type="hidden" id="setUsrId" name="setUsrId" value="<c:out value="${setUsrId}"/>"/>
	<input type="hidden" id="setUsrName" name="setUsrName" value="<c:out value="${setUsrName}"/>"/>
	<input type="hidden" id="apvGbn" name="apvGbn" value="<c:out value="${apvGbn}"/>"/>
	<input type="hidden" id="setDepCode" name="setDepCode" value="<c:out value="${setDepCode}"/>"/>
	<input type="hidden" id="setDepName" name="setDepName" value="<c:out value="${setDepName}"/>"/>

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
									<span class="title"><label for="service">서비스</label></span>
	                   				 <sb:select name="service" type="S" />
								</li>
								<li>
									<span class="title"><label for="stOrg">부서</label></span>
									<sb:select name="stOrg" type="stOrg" allText="본부전체" />
									<sb:select name="hqOrg" type="hqOrg" allText="그룹,담당전체" />
									<sb:select name="gpOrg" type="gpOrg" allText="팀전체" />
								</li>
				    			<li>
				    				<span class="title"><label for="uumUsrId">사용자 ID</label></span>
				    				<input type="text" id="uumUsrId" name="uumUsrId" class="inputText" title="사번" placeholder="사번"/>
				    			</li>
				    			<li>
				    				<span class="title"><label for="uumUsrNam">사용자 명</label></span>
				    				<input type="text" id="uumUsrNam" name="uumUsrNam" class="inputText" title="이름" placeholder="이름"/>
				    			</li>
				    		</ul>
				            <button type="button" onclick="searchList()" class="btnSearch defaltBtn">조건으로 검색</button>
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
					<li><button type="button" class="btnStrong" onclick="saveWorkerId();"><span class="icoPer"></span>담당자 지정</button></li>
				</ul>
           </div>
	    </article>
	</div>
</form>
</body>
</html>