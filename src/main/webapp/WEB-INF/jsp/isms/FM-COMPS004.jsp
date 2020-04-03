<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/include/contents.jsp"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=10" />
<meta charset="UTF-8">

<script type="text/javascript">

	$(document).ready(function() {

		searchList();
		// 주기 변경시
		$("#workTerm").change(function(){
			var termCheck = $("select[name=workTerm]").val();
			if(termCheck != "A"){
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
		$("#gridTb").jqGrid("GridUnload");	// 동적데이터 전송시 필수
		// form search 조건을 jqgrid에 json형식으로 넘기게 해줌.
		// SearchVO에 선언한 변수명과 name명 일치시켜서 사용.
		//var formData = $("#sampleDocList").serialize();
		var formData = formSerializeObject($("#sampleDocList")); // jqgrid pageing시 반드시 formSerializeObject()를 사용해야함
		$("#gridTb").jqGrid({
			url:"${pageContext.request.contextPath}/comps/FM-COMPS004_list.do",
			postData : formData,
			datatype : "json",
			mtype : "post",
			colNames:['masterKey','서비스','문서종류','업무명','업무주기','증적주기','승인','매핑항목','업무담당자','최종할당일시','업무마감일','업무기간','사용여부','최종등록자','최종등록일자'],
			colModel:[
					  {name:'utdTrcKey',	index:'utd_trc_key',hidden:true},
			          {name:'service',		index:'service',	width: 70,	align:'center',	editable:false,	sorttype: 'text'},
			          {name:'docGbnNm',		index:'doc_gbn_nm',	width: 60,	align:'center',	editable:false,	sorttype: 'text'},
			          {name:'utdDocNam',	index:'utd_doc_nam',width: 200,	align:'left',	editable:false,	sorttype: 'text'},
			          {name:'workTerm',		index:'work_term',	width: 60,	align:'center',	editable:false,	sorttype: 'text'},
			          {name:'dtrGbn',		index:'dtr_gbn',	width: 60,	align:'center',	editable:false,	sorttype: 'text'},
			          {name:'utdAprYn',		index:'utd_apr_yn',	width: 50,	align:'center',	editable:false,	sorttype: 'text'},
			          {name:'ctrList',		index:'ctr_list',	width: 150,	align:'left',	formatter: function(cellvalue) { return getGridTextIcon(cellvalue, "spacing"); }},
			          {name:'workerList',	index:'worker_list',width: 150,	align:'left',	formatter: function(cellvalue) { return getGridTextIcon(cellvalue); }},
			          {name:'wrkUptMdh',	index:'wrk_upt_mdh',width: 100,	align:'center',	editable:false},
			          {name:'cmpDat',		index:'cmp_dat',	width: 85,	align:'center',	editable:false},
			          {name:'period',		index:'period',		width: 165,	align:'center',	editable:false},
			          {name:'delYn',		index:'del_yn',		width: 58,	align:'center',	editable:false},
			          {name:'utdUptNm',		index:'utd_upt_nm',	width: 100,	align:'center',	editable:false},
			          {name:'utdUptMdh',	index:'utd_upt_mdh',width: 100,	align:'center',	editable:false}
			],
			pager : '#pager',
			rowNum : 100,
 			rowList : [100, 100000],
			rownumbers : true,	// 각 row의 맨 앞줄에 각 row의 번호가 자동적으로 부여된다.
			sortable : true,	// 초기 sort 조건과 관련되며 ture이면 초기 데이터가 정렬되서 보인다.
			sortname : 'utd_trc_key',	// 초기 디이터 정렬시 정렬조건, 서버에 정렬  조건으로 전송된다.
			sortorder : 'asc',	// 순방향, 역방향 정렬(desc, asc)
			viewrecords : true,	// 오른쪽 하단에 총 몇개중 몇번째꺼 표시
			loadonce : false,	// reload 여부이며, true로 설정시 한번만 데이터를 받아오고 그 다음부터는 데이터를 받아오지 않는다.
			multiselect: true,	// select box 적용
			loadtext : 'loading..',	// 서버연동시 loading 중이라는 표시가 뜨는데 그곳의 문자열 지정.
			jsonReader:{
				repeatitems : true,
				root : function (obj) {return obj.result},
				page: function (obj) {return obj.currentPageNo},
			    total: function (obj) {return obj.totalPage},
			    records: function (obj) {return obj.totalRecord}
			},
			loadComplete: function() {
			    $("option[value=100000]").text('전체');
			},
			onSelectRow: function(id, status, e){
				var docNum = $("#gridTb").getRowData(id).utdTrcKey;
				var $myGrid = $(this),
		        i = $.jgrid.getCellIndex($(e.target).closest('td')[0]),
		        cm = $myGrid.jqGrid('getGridParam', 'colModel');
		  		if (cm[i].name != 'cb') {
		  			docPopup(docNum);
		  			//중복팝업 방지를 위해 수정할 필요가 있다.
		  		}
			}
		}).trigger("reloadGrid");
		// jqgrid 페이징바
		$("#gridTb").jqGrid('navGrid','#pager'
			,{ excel:false,add:false,edit:false,view:false,del:false,search:false,refresh:false}
		);
		// jqgrid 전체 넓이 지정, 가로 스크롤 생성
		$("#gridTb").setGridWidth(1018, false);
	}

	function excelDown(){
		document.sampleDocList.action = "${pageContext.request.contextPath}/excel/FM-COMPS004.do";
	   	document.sampleDocList.submit();
	}

	// 2016-10-27, 엑셀 업로드
	function excelUpload() {
		openPostPopup("FM-COMPS004_excel_popup.do", 450, 360);
	}

	// 2017-02-16, 업무 일괄배정
	function insertWork() {
		var list = $("#gridTb").jqGrid('getGridParam', 'selarrrow');
		var data = Array();
		for (var i = 0; i < list.length; i++){
			var value = $("#gridTb").jqGrid("getCell", list[i], "utdTrcKey");
			data.push(value);
		}
		var addList = data.join(",");

		if ( !addList) {
			alert("선택한 보호활동이 없습니다.");
			return false;
		}

		if (confirm("선택한 보호활동의 담당자에게 업무를 일괄할당 하시겠습니까?")) {
			loadingBarSet();
			$.post("FM-COMPS004_insert_work.do", {trcKeys: addList}, function(data) {
				if (data.result == "S") {
					alert("선택된 보호활동 담당자에게 업무를 일괄할당 했습니다.");
					searchList();
				} else {
					alert("일괄할당 처리를 실패했습니다.");
				}
			}, "json")
			.fail(function() {
				 alert("처리 중 에러가 발생했습니다.");
			});
		}
	}

	// 2018-02-20, 업무 일괄삭제
	function deleteDoc() {
		var list = $("#gridTb").jqGrid('getGridParam', 'selarrrow');
		var data = Array();
		var dataCount = 0;
		for (var i = 0; i < list.length; i++){
			var value = $("#gridTb").jqGrid("getCell", list[i], "utdTrcKey");
			data.push(value);
			dataCount++;
		}
		var addList = data.join(",");

		if ( !addList) {
			alert("선택한 보호활동이 없습니다.");
			return false;
		}

		if (confirm("선택한 "+ dataCount +"개의 보호활동을 삭제 하시겠습니까?\n데이터 삭제 후 복구가 불가능합니다.")) {
			loadingBarSet();
			$.post("FM-COMPS004_del_doc.do", {trcKeys: addList}, function(data) {
				if (data.result == "S") {
					alert("선택한 보호활동을 삭제했습니다.");
					searchList();
				} else {
					alert("처리 중 에러가 실패했습니다.");
				}
			}, "json")
			.fail(function() {
				 alert("처리 중 에러가 발생했습니다.");
			});
		}
	}
</script>
</head>
<body>
	<c:import url="/titlebar.do" />
	<form id="sampleDocList" name="sampleDocList" method="post">
	<input type="hidden" id="manCyl" name="manCyl"	value="<%=C_SES_MAN_CYL%>">
	<div class="search">
	    <div class="defalt">
	        <fieldset class="searchForm">
	            <legend>기본검색</legend>
	            <ul class="detail">
	                <li>
	                    <span class="title"><label for="standard">컴플라이언스</label></span>
	                    <sb:select name="standard" type="A" code="STND"/>
	                </li>
	                <li>
	                    <span class="title"><label for="service">서비스</label></span>
	                    <sb:select name="service" type="S"/>
	                </li>
	                <li>
	                    <span class="title"><label for="sampleCode">문서양식</label></span>
	                    <sb:select name="sampleCode" type="A" code="DGBN" />
	                </li>
	                <li>
	                	<span class="title"><label for="approval2">결재방법</label></span>
						<select id="approval2" class="selectBox" name="approval">
							<option value="" selected>전체</option>
							<option value="N">자가결재</option>
							<option value="Y">승인결재</option>
						</select>
					</li>
					<li>
	                    <span class="title"><label for="useYn">사용여부</label></span>
						<sb:select name="useYn" type="A" code="USE_YN" value="Y" />
	                </li>
					<li>
	                    <span class="title"><label for="workTerm">업무주기</label></span>
						<sb:select name="workTerm" type="A" code="TERM" />
	                </li>
	                <li>
						<span class="title"><label for="workCycleTerm">증적주기</label></span>
						<sb:select name="workCycleTerm" type="A" code="DOCTERM" />
					</li>
					<li>
						<span class="title"><label for="wrkUptMdh">할당여부</label></span>
						<select id="wrkUptMdh" name="wrkUptMdh" class="selectBox">
							<option value="" selected>전체</option>
							<option value="N">할당 전</option>
							<option value="Y">할당</option>
						</select>
					</li>
					<li>
						<span class="title"><label for="findusr">담당자</label></span>
						<input id="findusr" class="inputText" type="text" title="업무명 입력" placeholder="담당자" name="findusr" style="width: 107px;"/>
					</li>
					<li class="last" style="width: 100%;">
						<span class="title"><label for="workName">업무명</label></span>
						<input id="workName" class="inputText" type="text" title="업무명 입력" placeholder="업무명을 입력하세요." name="workName"/>
					</li>
	            </ul>
	            <button type="button" onclick="searchList()" class="btnSearch defaltBtn">검색</button>
	        </fieldset>
	    </div>
	</div>
	<div class="contents">
		<div class="topBtnArea">
			<ul class="btnList">
				<li><button type="button" onclick="deleteDoc()" style="color: red;"><span class="icoDel"></span>보호활동 삭제</button></li>
				<li><button type="button" onclick="insertWork()"><span class="icoSave"></span>업무 일괄할당</button></li>
				<li><button type="button" onclick="docPopup('')"><span class="icoAdd"></span>추가</button></li>
				<li><button type="button" onclick="excelUpload()"><span class="icoSave"></span>보호활동 엑셀업로드</button></li>
				<li><button type="button" onclick="excelDown()"><span class="icoExl"></span>엑셀다운로드</button></li>
			</ul>
		</div>
		<div class="talbeArea">
			<table id="gridTb">
			</table>
			<!-- con_tbl -->
			<div id="pager"></div>
		</div>
	</div>
	</form>
</body>
</html>