<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="com.ckeditor.CKEditorConfig"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="ce" uri="http://ckeditor.com" %>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
<%@ include file="/WEB-INF/include/head.jsp" %>
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
<link rel="stylesheet" type="text/css" 	href="/common/jqGrid/css/ui.jqgrid.css" />
<!--[if IE]> <script src="/common/js/html5.js"></script> <![endif]-->
<script type="text/javascript" src="/common/js/jquery.js"></script>
<script type="text/javascript" src="/common/js/ismsUI.js"></script>
<script type="text/javascript" src="/common/js/jquery-ui.js"></script>
<script type="text/javascript" src="/common/jqGrid/js/i18n/grid.locale-kr.js"></script>
<script type="text/javascript" src="/common/jqGrid/js/jquery.jqGrid.js"></script>
<script type="text/javascript" src="/common/js/common.js"></script>

<script  type="text/javascript">
	$(document).ready(function() {
		searchList();
		// 주기 변경시
		$("#workTerm").change(function(){
			var termCheck = $("select[name=workTerm]").val();
			if(termCheck != ""){
				if(termCheck == "Y"){
					getWorkCycle("TERM");
				}else {
					getWorkCycle("nonCycle");
				}
			}else{
				$("#workCycleTerm").html('<option value="">전체</option>');
			}
		});
	});

	function getWorkCycle(code) {
		var url = "";
		if("TERM" == code) {
			url = "${pageContext.request.contextPath}/code/getCommonCodeList.do";
		} else {
			url = "${pageContext.request.contextPath}/code/getNonCodeList.do";
		}

		$.ajax({
			url			: url,
			type		: "post",
			data		: {code:code},
			dataType	: "json",
			success		: function(data){
				var inData ="";
				inData += '<option value="">전체</option>';
				for(var i=0; i<data.result.length; i++){
					inData += '<option value="'+data.result[i].code+'">'+data.result[i].name+'</option>';
				}
				$("#workCycleTerm").html(inData);
			 },
			 error : function(){
				 alert('error');
			 }
		 });
	}


	function searchList(){
		// form search 조건을 jqgrid에 json형식으로 넘기게 해줌.
		// SearchVO에 선언한 변수명과 name명 일치시켜서 사용.
		var formData = $("#mappingSampleDocForm2").serialize();

		$("#gridTb").jqGrid({
			url:"${pageContext.request.contextPath}/comps/FM-COMPS005_list.do",
			postData : formData,
			datatype : "json",
			mtype : "post",
			colNames:['trcKey','업무담당자','서비스','문서양식','업무명','업무주기','결재방법'],
			colModel:[
					  {name:'utdTrcKey',	index:'utdTrcKey',	width: 0,	align:'center',	hidden:true},
					  {name:'workerList',	index:'workerList',	width: 0,	align:'left',	hidden:true},
					  {name:'service',		index:'service',	width: 70,	align:'center',	editable:false,	sorttype: 'text'},
			          {name:'docGbnNm',		index:'docGbnNm',	width: 70,	align:'center',	editable:false,	sorttype: 'text'},
			          {name:'utdDocNam',	index:'utdDocNam',	width: 200,	align:'left',	editable:false,	sorttype: 'text'},
			          {name:'workTerm',		index:'workTerm',	width: 70,	align:'center',	editable:false,	sorttype: 'text'},
			          {name:'utdAprYn',		index:'utdAprYn',	width: 70,	align:'center',	editable:false,	sorttype: 'text'},
			],
			width : 698,
			height: 250,		// 전체 grid 화면의 세로 사이즈 지정.
			pager : '#pager',
			rowNum : '300',
			rownumbers : true,	// 각 row의 맨 앞줄에 각 row의 번호가 자동적으로 부여된다.
			sortable : true,	// 초기 sort 조건과 관련되며 ture이면 초기 데이터가 정렬되서 보인다.
			sortname : 'utdTrcKey',	// 초기 디이터 정렬시 정렬조건, 서버에 정렬  조건으로 전송된다.
			sortorder : 'asc',	// 순방향, 역방향 정렬(desc, asc)
			viewrecords : true,	// 오른쪽 하단에 총 몇개중 몇번째꺼 표시
			loadonce : false,	// reload 여부이며, true로 설정시 한번만 데이터를 받아오고 그 다음부터는 데이터를 받아오지 않는다.
			multiselect: false,	// select box 적용
			loadtext : 'loading..',	// 서버연동시 loading 중이라는 표시가 뜨는데 그곳의 문자열 지정.
			jsonReader:{
				repeatitems : true,
				root : function (obj) {return obj.result;},
			},
			onSelectRow: function(id){
				$("#utcTrcKey").val($("#gridTb").getRowData(id).utdTrcKey);
				$("#setDocNam").text($("#gridTb").getRowData(id).utdDocNam);
				$("#setDocGbn").text($("#gridTb").getRowData(id).docGbnNm);
				$("#setWorkTerm").text($("#gridTb").getRowData(id).workTerm);
				$("#setAprYn").text($("#gridTb").getRowData(id).utdAprYn);
				$("#setWorker").text($("#gridTb").getRowData(id).workerList);
			},
			ondblClickRow: function(id){
				var docNum = $("#gridTb").getRowData(id).utdTrcKey;
				editSampleDoc(docNum);
			},
			loadComplete: function(xhr) {
			},
			loadError: function(xhr,st,err) {
				alert(err);
			},
		});
		// jqgrid 페이징바
		$("#gridTb").jqGrid('navGrid','#pager'
			,{ excel:false,add:false,edit:false,view:false,del:false,search:false,refresh:false}
		);
	}

	// get reload grid
	function getSampleDocList(){
		$("#gridTb").clearGridData();	// 이전 데이터 삭제

		var formData = $("#mappingSampleDocForm2").serialize();
		$("#gridTb").setGridParam({
			postData : formData,
			ondblClickRow: function(id){
				var docNum = $("#gridTb").getRowData(id).utdTrcKey;
				editSampleDoc(docNum);
			}
		}).trigger("reloadGrid");
	}

	// 선택 된 증적양식과 통제항목 mapping
	function setMappting(){
		if (!$("#utcTrcKey").val()) {
			alert("증적양식을 선택해주세요.");
			return false;
		}

		var ucmKey = $("#code").val();

		var formData = $("#mappingSampleDocForm").serialize();

		$.ajax({
			url			: "/comps/FM-COMPS005_setMapping_save.do",
			type		: "post",
			data		: formData,
			dataType	: "json",
			success		: function(data){
				alert("증적양식과 통제항목이 연결되었습니다.");
				// FM-SETUP005.jsp의 매핑 증적양식 리스트 함수 호출
				$(opener.location).attr("href", "javascript:getMappingSampleDocList("+ucmKey+");");
				window.close();
			 },
			 error : function(){
				 alert('error');
			 }
		 });
 	}

	function getWorkerList() {
		var dept = $("select[name=dept]").val();
		if(dept != ""){
			$.ajax({
				url			: "${pageContext.request.contextPath}/code/getWorkerList.do",
				type		: "post",
				data		: {code:dept},
				dataType	: "json",
				success		: function(data){
					var inData ="";
					inData += '<option value="">전체</option>';
					for(var i=0; i<data.result.length; i++){
						var strSel = "";
						if('${searchVO.worker}' == data.result[i].code) {
							inData += '<option value="'+data.result[i].code+'" selected="selected">'+data.result[i].name+'</option>';
						} else {
							inData += '<option value="'+data.result[i].code+'">'+data.result[i].name+'</option>';
						}

					}
					$("#worker").html(inData);
				 },
				 error : function(){
					 alert('error');
				 }
			 });
		}else{
			$("#worker").html('<option value="">전체</option>');
		}
	}
</script>
</head>
<body>
	<div id="wrap" class="pop">
	    <header>
	        <div class="borderBox"></div>
	        <h1>수행업무설정</h1>
	    </header>

	    <article class="cont" id="content-box">
	        <div class="cont_container">
	        	<form id="mappingSampleDocForm2" name="mappingSampleDocForm2" method="post">
	            <div class="search">
	                <div class="defalt">
	                    <fieldset class="searchForm">
	                        <legend>기본검색</legend>
	                        <ul class="detail">
	                            <li>
	                                <span class="title"><label for="service">서비스</label></span>
									<sb:select name="service" type="S"/>
	                            </li>
	                            <li>
				                    <span class="title"><label for="sampleCode">문서양식</label></span>
				                    <sb:select name="sampleCode" type="A" code="DGBN" />
				                </li>
				                <li>
				                	<span class="title"><label for="approval">결재방법</label></span>
									<select id="approval" class="selectBox" name="approval">
										<option value="" selected>전체</option>
										<option value="N">자가결재</option>
										<option value="Y">승인결재</option>
									</select>
								</li>
	                            <li>
	                                <span class="title"><label for="workTerm">업무주기</label></span>
	                                <select id="workTerm" name="workTerm" class="selectBox">
										<option value="">전체</option>
										<option value="Y">주기</option>
										<option value="N">비주기</option>
									</select>
	                             </li>
	                             <li>
									<span class="title"><label for="workCycleTerm">상세주기</label></span>
									<select id="workCycleTerm" name="workCycleTerm" class="selectBox">
										<option value="">전체</option>
									</select>
	                            </li>
								<li class="last" style="width: 100%;">
                                       <span class="title"><label for="workName">업무명</label></span>
                                       <input id="workName" name="workName" class="inputText" type="text" title="업무명 입력" placeholder="업무명을 입력하세요.">
								</li>
							</ul>
							<button class="btnSearch defaltBtn" onclick="getSampleDocList(); return false;">조건으로 검색</button>
						</fieldset>
					</div>
				</div>
				</form>
				<div class="contents">
					<div class="talbeArea">
						<table id="gridTb" class="cont_tbl">
						</table>
						<div id="pager"></div>
					</div>
				</div>
				<form id="mappingSampleDocForm" name="mappingSampleDocForm" method="post">
				<input type="hidden" id="code" name="code" value="<c:out value="${ucmCtrKey}"/>" />
				<input type="hidden" id="utcTrcKey" name="utcTrcKey" value="" />
				<input type="hidden" id="utcBcyCod" name="utcBcyCod" value="<%=C_SES_MAN_CYL%>" />
				<input type="hidden" id="utcRgtId" name="utcRgtId" value="<%=C_SES_USER_KEY%>" />
				<div class="contents">
					<div class="title">증적양식</div>
	   				<div class="talbeArea">
						<table>
							<colgroup>
								<col width="15%"/>
								<col width="35%" />
								<col width="15%" />
								<col />
							</colgroup>
							<tr>
								<th ><label for="setDocNam">업무명</label></th>
								<td id="setDocNam"></td>
								<th><label for="setDocGbn">문서양식</label></th>
								<td class="last" id="setDocGbn"></td>
							</tr>
							<tr>
								<th><label for="setWorkTerm">업무주기</label></th>
								<td id="setWorkTerm"></td>
								<th><label for="setAprYn">결재방법</label></th>
								<td class="last" id="setAprYn"></td>
							</tr>
							<tr>
								<th><label for="setWorker">업무담당자</label></th>
								<td class="last" id="setWorker" colspan="3"></td>
							</tr>
						</table>
					</div>
				</div>
				</form>
			</div>
			<div class="centerBtnArea">
	           	<button type="button" class="popClose close"><span class="icoClose"><em>닫기</em></span></button>
               	<ul class="btnList">
                   	<li><button type="button" class="btnStrong" id="addStandardBtn" onclick="setMappting();"><span class="icoSave"></span>업무명(양식서) 연결 저장</button></li>
              	</ul>
           	</div>
		</article>

	</div>
</body>
</html>