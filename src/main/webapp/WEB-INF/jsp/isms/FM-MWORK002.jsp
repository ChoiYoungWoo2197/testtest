<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
<%@ include file="/WEB-INF/include/contents.jsp"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<title>사업부 정보보호 업무현황</title>
<meta http-equiv="X-UA-Compatible" content="IE=10" />
<meta charset="UTF-8">
<script type="text/javascript">
var first = true;

	$(document).ready(function() {
		stOrgList();
		getWorkCycle("TERM");
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


		if(first){
			$("#notPerson").hide();
			$("#isPerson").show();
			first = false;
		}

	});

	function getWorkCycle(code) {
		var url = "";
		if("TERM" == code) {
			$("#selectTrmGbn").css("width", "80px");
			url = "${pageContext.request.contextPath}/code/getCommonCodeList.do";
		} else {
			$("#selectTrmGbn").css("width", "150px");
			url = "${pageContext.request.contextPath}/code/getNonCodeList.do";
		}

		$.ajax({
			url			: url,
			type		: "post",
			data		: {code:code},
			dataType	: "json",
			success		: function(data){
				var inData ="";
				for(var i=0; i<data.result.length; i++){
					var strSel = "";
					if('${sampleDocVO.utdTrmGbn}' == data.result[i].code) {
						strSel = 'selected="selected"';
					}
					inData += '<option value="'+data.result[i].code+'" '+strSel+'>'+data.result[i].name+'</option>';
				}
				$("#selectTrmGbn").html(inData);
			 },
			 error : function(){
				 alert('error');
			 }
		 });
	}

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

	function getWorkerList() {
		var dept = $("#dept").val();
		if(dept != ""){
			$.ajax({
				url			: "${pageContext.request.contextPath}/code/getWorkerList.do",
				type		: "post",
				data		: {code:dept},
				dataType	: "json",
				async		: false,
				success		: function(data){
					var	inData = getWorkerInTag(data);
					$("#worker").html(inData);
				 },
				 error : function(){
					 alert('error');
				 }
			 });
		}
	}

	arrtSetting = function (rowId, val, rawObject, cm) {
        var attr = rawObject.attr[cm.name], result;

        if (attr.rowspan) {
            result = ' rowspan=' + '"' + attr.rowspan + '"';
        } else if (attr.display) {
            result = ' style="display:' + attr.display + '"';
        }
        return result;
    };

	function searchList(){
		var url = "${pageContext.request.contextPath}/mwork/FM-MWORK002_P.do";

		$("#gridTb").jqGrid({
			url:url,
			page :1,
			rows :100,
			postData :
			{
				division:$("#division").val(),
				service:$("#service").val(),
				dept:$("#dept").val(),
				worker:$("#worker").val(),
				workTerm:$("select[name=workTerm]").val(),
				standard:$("#standard").val(),
				goalNum:$("#goalNum").val(),
				workCycleTerm:$("#workCycleTerm").val(),
				workName:$("#workName").val(),
				workerName:$("#workerName").val(),
				workState:$("#workState").val()
			},
			datatype : "json",
			mtype : "post",
			colNames:['업무코드','trckey','mngKey','업무자','업무상태','업무기간','업무명','표준','항목번호','점검','업무주기','담당자','결재상태','결재방법','대무자'],
			colModel:[
					  {name:'utwWrkKey',	index:'utwWrkKey',	width: 80,	align:'center',	hidden:false},
			          {name:'utwTrcKey',	index:'utwTrcKey',	width: 0,	align:'center',	hidden:true},
			          {name:'utdMngKey',	index:'utdMngKey',	width: 0,	align:'center',	hidden:true},
			          {name:'wrkId',		index:'wrkId',		width: 0,	align:'center',	hidden:true},
			          {name:'displayWrkSta',index:'displayWrkSta',	width: 80,	align:'center',	editable:false,	sorttype:'text'},
			          {name:'period',		index:'period',		width: 120,	align:'left', hidden:true,	editable:false,	sorttype:'text'},
			          {name:'utdDocNam',	index:'utdDocNam',	width: 352,	align:'left',	editable:false,	sorttype:'text'},
			          {name:'ucmCtrGbn',	index:'ucmCtrGbn',	width: 80,	align:'center',	hidden:true,	editable:false,	sorttype:'text'},
			          {name:'ucmGolNo',		index:'ucmGolNo',	width: 80,	align:'center',	hidden:true,	editable:false,	sorttype:'text'},
			          {name:'ucm3lvNam',	index:'ucm3lvNam',	width: 120,	align:'left',	hidden:true, editable:false,	sorttype:'text'},
			          {name:'utwTrmCod',	index:'utwTrmCod',	width: 80,	align:'center',	editable:false,	sorttype:'text'},
			          {name:'utwWrkId',		index:'utwWrkId',	width: 80,	align:'center',	editable:false,	sorttype:'text'},
			          {name:'wrkStaDtl',	index:'wrkStaDtl',	width: 120,	align:'center',	editable:false},
			          {name:'utdAppNam',	index:'utdAppNam',	width: 80,	align:'center',	editable:false,	sorttype:'text'},
			          {name:'utwAgnId',		index:'utwAgnId',	width: 80,	align:'center',	editable:false,	sorttype:'text'},
//			          {name:'agnStaDtl',	index:'agnStaDtl',	width: 80,	align:'center',	editable:false}
			],
			height: 300,		// 전체 grid 화면의 세로 사이즈 지정.
			pager : '#pager',
			rowNum : '300',
// 			rowList : [15, 20, 50],
			rownumbers : false,	// 각 row의 맨 앞줄에 각 row의 번호가 자동적으로 부여된다.
			sortable : true,	// 초기 sort 조건과 관련되며 ture이면 초기 데이터가 정렬되서 보인다.
			sortname : 'utwWrkKey',	// 초기 디이터 정렬시 정렬조건, 서버에 정렬  조건으로 전송된다.
			sortorder : 'asc',	// 순방향, 역방향 정렬(desc, asc)
			viewrecords : true,	// 오른쪽 하단에 총 몇개중 몇번째꺼 표시
			loadonce : true,	// reload 여부이며, true로 설정시 한번만 데이터를 받아오고 그 다음부터는 데이터를 받아오지 않는다.
			multiselect: true,	// select box 적용
			loadtext : 'loading..',	// 서버연동시 loading 중이라는 표시가 뜨는데 그곳의 문자열 지정.
			jsonReader:{
				page: 'page',
				total: 'total',
				records: 'records',
				root: 'allWorkList',
				repeatitems: false
			},
			onSelectRow: function(id, status, e){
				var workNum = $("#gridTb").getRowData(id).utwWrkKey;
				var trcKey = $("#gridTb").getRowData(id).utwTrcKey;
				var mngKey = $("#gridTb").getRowData(id).utdMngKey;
				var usrKey = $("#gridTb").getRowData(id).wrkId;

				var $myGrid = $(this),
		        i = $.jgrid.getCellIndex($(e.target).closest('td')[0]),
		        cm = $myGrid.jqGrid('getGridParam', 'colModel');
		  		if (cm[i].name != 'cb') {
					workPopup2(workNum, trcKey, mngKey, usrKey);
		  		}
			},
			loadComplete: function(xhr) {
				// 업무현황 set
				setWorCnt(xhr);

				// replace
				var allrows = $("#gridTb").jqGrid("getDataIDs");
				for(var key=1;  key <= allrows.length; key++){
					 var rowData = $("#gridTb").jqGrid("getRowData", key);
					 var wrkState = rowData.wrkStaDtl;
					 var agnState = rowData.agnStaDtl;

					 if(wrkState == '11'){
						 wrkState = "결재진행중";
					 }else if(wrkState == '12'){
						 wrkState = "반려";
					 }else if(wrkState == '13'){
						 wrkState = "미진행";
					 }else if(wrkState == '14'){
						 wrkState = "완료";
					 }else if(wrkState == '15'){
						 wrkState = "결제상신취소";
					 }

					 if(agnState == '21'){
						 agnState = "대무자 요청 결재진행중";
					 }else if(agnState == '22'){
						 agnState = "대무자 요청 반려";
					 }else if(agnState == '23'){
						 agnState = " ";
					 }else if(agnState == '24'){
						 agnState = "대무자 요청 승인";
					 }else if(agnState == '25'){
						 agnState = "대무자 요청 취소";
					 }

					 // 매핑항목
					 rowData.wrkStaDtl = wrkState;
					 rowData.agnStaDtl = agnState;

					 $("#gridTb").jqGrid("setCell", key, "wrkStaDtl", rowData.wrkStaDtl);
					 $("#gridTb").jqGrid("setCell", key, "agnStaDtl", rowData.agnStaDtl);

				}

 				//checkbox 해제
				$("#gridTb input[type='checkbox']").prop("checked", false);
			}
		});
		// jqgrid 페이징바
		$("#gridTb").jqGrid('navGrid','#pager'
			,{ excel:false,add:false,edit:false,view:false,del:false,search:false,refresh:false}
		);
		// jqgrid 전체 넓이 지정, 가로 스크롤 생성
		$("#gridTb").setGridWidth(1018, false);
	}

	// get reload grid
	function searchListB(){
		$("#gridTb").clearGridData();	// 이전 데이터 삭제

		$("#gridTb").setGridParam({
			page : 1,
			datatype : "json",
			postData :
			{
				division:$("#division").val(),
				service:$("#service").val(),
				dept:$("#dept").val(),
				worker:$("#worker").val(),
				workTerm:$("select[name=workTerm]").val(),
				standard:$("#standard").val(),
				goalNum:$("#goalNum").val(),
				workCycleTerm:$("#workCycleTerm").val(),
				workName:$("#workName").val(),
				workerName:$("#workerName").val(),
				workState:$("#workState").val()
			}
		}).trigger("reloadGrid");
	}

	function setWorCnt(data){
		// 최초 검색이 아닌 sorting 등에는 적용하지 않음
		if (data.allWorkList == undefined) {
			return;
		}
		if($("#notPerson").css("display") == "none"){
			// 개인업무 업무카운트
			var wanCnt = Number(data.workCount[0].wanCnt) + Number(data.workDCount[0].wanDCnt);
			var allCnt = Number(data.workDCount[0].miDCnt) + Number(data.workCount[0].miCnt) + wanCnt;

			$("#allCntP").text(allCnt);
			$("#miDCntP").text(data.workDCount[0].miDCnt);
			$("#miCntP").text(data.workCount[0].miCnt);
			$("#wanCntP").text(wanCnt);
			$("#namCnt1P").text(data.namWorkCount[0].namCnt1);
			$("#namCnt2P").text(data.namWorkCount[0].namCnt2);
		}else {
			// 관리자 조회용 업무카운트
			var wanCnt = Number(data.workCount[0].wanCnt) + Number(data.workDCount[0].wanDCnt);
			var allCnt = Number(data.workDCount[0].miDCnt) + Number(data.workCount[0].miCnt) + wanCnt;

			$("#allCnt").text(allCnt);
			$("#miDCnt").text(data.workDCount[0].miDCnt);
			$("#miCnt").text(data.workCount[0].miCnt);
			$("#wanCnt").text(wanCnt);
		}
	}

	function workPopup2(wrkKey, docKey, mngKey, usrKey) {
		openPostPopup("/mwork/FM-MWORK_popup.do", 730, 800, { utwWrkKey: wrkKey, utwTrcKey: docKey, mngKey: mngKey, usrKey: usrKey }, 1);
	}

	function transferUserWork(usrKey){
		var list = $("#gridTb").jqGrid('getGridParam', 'selarrrow');

		var data = Array();
		var usrKey = null;
		for(var i = 0; i < list.length; i++){
			var value = $("#gridTb").jqGrid("getCell", list[i], "utwWrkKey");
			data.push(value);
			var tmpUsrKey = $("#gridTb").jqGrid("getCell", list[i], "wrkId");
			if (usrKey && usrKey != tmpUsrKey) {
				alert("여러명의 담당자 업무를 동시에 처리할 수 없습니다.");
				return;
			}
			usrKey = tmpUsrKey;
		}
		var addList = data.join(",");

		if( !addList) {
			alert("선택된 업무가 없습니다.");
			return;
		}

		window.open("","UserPopup","width=600, height=550, menubar=no, location=no, status=no, scrollbars=auto");
		$("#addList").val(addList);
		$("#usrKey2").val(usrKey);
		document.changeUserWorkForm.submit();
	}

	function excelDown(){
		document.searchForm.action = "${pageContext.request.contextPath}/excel/FM-MWORK006.do";
	   	document.searchForm.submit();
	}
</script>
</head>
<body>
	<c:import url="/titlebar.do" />
	<div class="search">
		<form id="searchForm" name="searchForm" method="post">
			<div class="defalt">
				<fieldset class="searchForm">
					<legend>상세 검색</legend>
					<ul class="detail">
		            	<li>
							<span class="title"><label for="stOrg">본부</label></span>
							<sb:select name="stOrg" type="stOrg" forbidden="true" allText="본부전체" />
						</li>
						<li>
							<span class="title"><label for="hqOrg">그룹,담당</label></span>
							<sb:select name="hqOrg" type="hqOrg" forbidden="true" allText="그룹,담당전체" />
						</li>
		                <li>
		                    <span class="title"><label for="gpOrg">팀</label></span>
		                    <sb:select name="gpOrg" type="gpOrg" forbidden="true" allText="팀전체" />
		                </li>
		            	<li>
		                	<span class="title"><label for="service">서비스</label></span>
		                    <sb:select name="service" type="S" forbidden="true" />
		                </li>
						<li>
							<span class="title"><label for="workCycleTerm">업무주기</label></span>
							<select id="workCycleTerm" name="workCycleTerm" class="selectBox">
								<option value="">전체</option>
							</select>
						</li>
                      	<li>
	                       <span class="title"><label for="workerName">담당자</label></span>
	                       <input id="workerName" name="workerName" class="inputText wdShort" type="text"  title="담당자명" placeholder="담당자명"
							<c:if test="${authKey eq 'P'}" >disabled="disabled"</c:if>/>
	                	</li>
						<li><span class="title"><label for="workName">업무명</label></span>
							<input id="workName" class="inputText" type="text" title="업무명 입력" placeholder="업무명을 입력하세요." name="workName"/>
						</li>
						<li></li>
					</ul>
					<button class="btnSearch" onclick="searchListB(); return false;">조건으로 검색</button>
				</fieldset>
			</div>
		</form>
	</div>
	<div class="contents">
		<div class="title">업무현황</div>
			<div class="talbeArea"  id="notPerson">
			<!-- 관리자인 경우 -->
			<table class="cont_tbl" style="border:0;">
				<colgroup>
					<col width="25%"/>
					<col width="25%"/>
					<col width="25%"/>
					<col width="25%"/>
				</colgroup>
				<thead>
					<tr>
						<th>전체업무</th>
						<th>지연업무</th>
						<th>미완료업무</th>
						<th class="last">완료업무</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td id="allCnt">-</td>
						<td id="miDCnt">-</td>
						<td id="miCnt">-</td>
						<td id="wanCnt" class="last">-</td>
					</tr>
				</tbody>
			</table>
			</div>
			<!-- 업무담당자인 경우 -->
			<div class="talbeArea" id="isPerson" >
			<table style="border:0;">
				<colgroup>
					<col width="25%"/>
					<col width="25%"/>
					<col width="25%"/>
					<col width="25%"/>
				</colgroup>
				<thead>
					<tr>
						<th>전체</th>
						<th>지연업무</th>
						<th>미완료업무</th>
						<th class="last">완료업무</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td id="allCntP">-</td>
						<td id="miDCntP">-</td>
						<td id="miCntP">-</td>
						<td id="wanCntP" class="last">-</td>
					</tr>
				</tbody>
			</table>
			</div>
			<div class="topBtnArea">
					<ul class="btnList">
						<li>
							<button type="button" onclick="transferUserWork();"><span class="icoChagePer"></span>업무이관요청</button>
							<button onclick="excelDown(); return false;"><span class="icoExl"></span>엑셀다운로드</button>
						</li>
					</ul>
				</div>
		<div class="talbeArea">
			<table id="gridTb">
			</table>
			<!-- con_tbl -->
			<div id="pager"></div>
		</div>
		<form id="changeUserWorkForm" name="changeUserWorkForm" action="FM-MWORK006_TRS_POPUP.do" method="post" target="UserPopup">
			<input type="hidden" id="addList" name="addList" />
			<input type="hidden" id="usrKey2" name="usrKey" />
		</form>
	</div>
</body>
</html>