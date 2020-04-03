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
	window.onhashchange = function() {
		go(window.location.hash.substr(1));
	}

	$(document).ready(function() {
		stOrgList();
		go(window.location.hash.substr(1));
	});

	function go(gubun) {
		if ( !gubun) {
			gubun = "A";
		}
		$("#listTab").find("li").each(function() {
			if ($(this).attr("value") == gubun) {
				$(this).addClass("sel");
			}
			else {
				$(this).removeClass("sel");
			}
		});
		searchList(gubun);
	}

	function searchList(workState) {
		$("#gridTb").jqGrid("GridUnload");	// 동적데이터 전송시 필수

		if(workState==undefined) workState=$("#listTab").find("li.sel").attr("value");
		$("#workState").val(workState);
		var formData = formSerializeObject($("#searchForm"));

		$("#gridTb").jqGrid({
			url: "${pageContext.request.contextPath}/mwork/FM-MWORK006_list.do",
			datatype : "json",
			mtype : "post",
			colNames:[
				'업무코드','trckey','mngKey','업무자','서비스',
				'업무상태', '처리상태', /*'업무결과',*/ '업무명','업무주기',
				'증적주기',	'업무마감일','담당자','결재상태','결재방법',
				'대무자', '최초 완료일', '최종 수정일'
			],
			colModel:[
					  {name:'utwWrkKey',	index:'utw_wrk_key',	width: 70,	align:'center',	hidden:false},
			          {name:'utwTrcKey',	index:'utw_trc_key',	width: 0,	align:'center',	hidden:true},
			          {name:'utdMngKey',	index:'utd_mng_key',	width: 0,	align:'center',	hidden:true},
			          {name:'wrkId',		index:'wrk_id',			width: 0,	align:'center',	hidden:true},
			          {name:'utwSvcCod',	index:'utw_svc_cod',	width: 70,	align:'center',	editable:false,	sorttype:'text'},
			          {name:'displayWrkSta',index:'display_wrk_sta',width: 70,	align:'center',	editable:false,	sorttype:'text'},
				      // {name:'utwWrkPrg',	index:'utw_wrk_prg',	width: 70,	align:'center',	editable:false,	sorttype:'text'},
				      {name:'utwComNam',	index:'utw_com_nam',	width: 100,	align:'center',	editable:false,	sorttype:'text'},
			          {name:'utdDocNam',	index:'utd_doc_nam',	width: 296,	align:'left',	editable:false,	sorttype:'text'},
			          {name:'utwTrmCod',	index:'utw_trm_cod',	width: 65,	align:'center',	editable:false,	sorttype:'text'},
			          {name:'utdTrmGbn',	index:'utd_trm_gbn',	width: 65,	align:'center',	editable:false,	sorttype:'text'},
			          {name:'utwEndDat',	index:'utw_end_dat',	width: 80,	align:'center',	editable:false,	sorttype:'text'},
			          {name:'utwWrkId',		index:'utw_wrk_id',		width: 65,	align:'center',	editable:false,	sorttype:'text'},
			          {name:'wrkStaDtl',	index:'wrk_sta_dtl',	width: 90,	align:'center',	editable:false},
			          {name:'utdAppNam',	index:'utd_app_nam',	width: 70,	align:'center',	editable:false,	sorttype:'text'},
			          {name:'utwAgnId',		index:'utw_agn_id',		width: 65,	align:'center',	editable:false,	sorttype:'text'},
			          {name:'utwWrkDat',	index:'utw_wrk_dat',	width: 110,	align:'center'},
			          {name:'utwUptMdh',	index:'utw_upt_mdh',	width: 110,	align:'center'}
			],
			pager : '#pager',
			rowNum : 50,
			rowList : [50, 100000],
			postData : formData,
			rownumbers : true,	// 각 row의 맨 앞줄에 각 row의 번호가 자동적으로 부여된다.
			sortable : true,	// 초기 sort 조건과 관련되며 ture이면 초기 데이터가 정렬되서 보인다.
			sortname : 'utw_wrk_key',	// 초기 디이터 정렬시 정렬조건, 서버에 정렬  조건으로 전송된다.
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
				$("#workAllCount").text(xhr.workAllCount);
				$("#workDelayCount").text(xhr.workDelayCount);
				$("#workReadyCount").text(xhr.workReadyCount);
				$("#workCompCount").text(xhr.workCompCount);

				// 2018-11-07 s,
			    $("option[value=100000]").text('전체');

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

					 /*

					// workStatusType 객체가 없다면 콘솔에 오류를 표시하고
					// 활동 업무결과 정수 값을 타입 값으로 변환하는 작업을 진행하지 않는다.
					if (typeof workStatusType === "undefined") {
						console.error("활동 업무결과 타입 값을 담고 있는 workStatusType 객체가 존재하지 않습니다.")
					} else {
						// workStatusType 객체의 title 값을 화면에 표시한다.
						rowData.utwWrkPrg = workStatusType[rowData.utwWrkPrg]["title"];
						$("#gridTb").jqGrid("setCell", key, "utwWrkPrg", rowData.utwWrkPrg);
					}

					*/

					 // 매핑항목
					 rowData.wrkStaDtl = wrkState;
					 rowData.agnStaDtl = agnState;

					 $("#gridTb").jqGrid("setCell", key, "wrkStaDtl", rowData.wrkStaDtl);
					 $("#gridTb").jqGrid("setCell", key, "agnStaDtl", rowData.agnStaDtl);

				}

 				//checkbox 해제
				$("#gridTb input[type='checkbox']").prop("checked", false);
			}
		}).trigger("reloadGrid");
		// jqgrid 페이징바
		$("#gridTb").jqGrid('navGrid','#pager'
			,{ excel:false,add:false,edit:false,view:false,del:false,search:false,refresh:false}
		);
		// jqgrid 전체 넓이 지정, 가로 스크롤 생성
		$("#gridTb").setGridWidth(1018, false);
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
		document.searchForm.action = "${pageContext.request.contextPath}/excelNew/FM-MWORK006.do";
	   	document.searchForm.submit();
	}

	// 2018-07-17 s,
	function workDelete() {
		var list = $("#gridTb").jqGrid('getGridParam', 'selarrrow');

		var data = Array();
		var usrKey = null;
		for (var i = 0; i < list.length; i++){
			if ($("#gridTb").jqGrid("getCell", list[i], "displayWrkSta") == "완료") {
				alert("완료된 업무가 포함되어 있습니다.\n확인 후 다시 시도해 주십시요.");
				return;
			}

			var value = $("#gridTb").jqGrid("getCell", list[i], "utwWrkKey");
			data.push(value);
		}
		var addList = data.join(",");

		if ( !addList) {
			alert("선택된 업무가 없습니다.");
			return;
		}

		if (confirm("선택된 업무를 삭제하시겠습니까?")) {
			$.post("/comps/FM-COMPS_del_work.do", {"wrkKeys": addList}, function(data) {
				if (data.result) {
					alert("삭제가 완료 되었습니다.");
					searchList();
				}
				else {
					alert("삭제 도중 오류가 발생하였습니다.");
				}
			}, "json").fail(function() {
			    alert("삭제 도중 오류가 발생하였습니다.");
			});
		}
	}
</script>
</head>
<body>
	<c:import url="/titlebar.do" />
	<div class="search">
		<form id="searchForm" name="searchForm" method="post">
			<input type="hidden" id="workState" name="workState" />
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
		                    <span class="title"><label for="workTerm">업무주기</label></span>
							<sb:select name="workTerm" type="A" code="TERM" />
		                </li>
		                <li>
							<span class="title"><label for="workCycleTerm">증적주기</label></span>
							<sb:select name="workCycleTerm" type="A" code="DOCTERM" />
						</li>
						<li>
							<span class="title"><label for="utwComSta">처리상태</label></span>
							<sb:select name="utwComSta" type="A" code="WKCD_COMP" />
						</li>
						<li>
							<span class="title"><label for="findusr">담당자</label></span>
							<input id="utwWrkId" class="inputText wdShort" type="text" title="담당자 입력" placeholder="담당자" name="utwWrkId"/>
						</li>
						<li>
	            	    	<span class="title"><label for="workName">업무명</label></span>
							<input id="workName" class="inputText" type="text" title="업무명 입력" placeholder="업무명을 입력하세요." name="workName"/>
	                	</li>
	            	</ul>
                	<button type="button" onclick="searchList()" class="btnSearch defaltBtn">조건으로 검색</button>
				</fieldset>
			</div>
		</form>
	</div>
	<div class="contents">
		<div class="topBtnArea">
			<ul class="btnList">
				<li>
				<c:if test="${loginAuth ne 'P'}">
					<button type="button" onclick="workDelete();"><span class="icoDel"></span>지연업무 삭제</button>
					<button type="button" onclick="transferUserWork();"><span class="icoChagePer"></span>업무이관요청</button>
				</c:if>
					<button onclick="excelDown(); return false;"><span class="icoExl"></span>엑셀다운로드</button>
				</li>
			</ul>
		</div>
		<div class="tabArea" style="margin-bottom: 10px;">
		    <ul id="listTab" class="list4Tab">
		        <li value="A" title="전체" class="sel"><a class="left" href="javascript:go('A');">전체 <span class="listTabCnt" id="workAllCount"></span></a></li>
		        <li value="R" title="미진행"><a class="left" href="javascript:go('R');">미진행 <span class="listTabCnt" id="workReadyCount"></span></a></li>
		        <li value="D" title="지연"><a class="left" href="javascript:go('D');">지연 <span class="listTabCnt" id="workDelayCount"></span></a></li>
		        <li value="C" title="완료"><a class="left" href="javascript:go('C');">완료 <span class="listTabCnt" id="workCompCount"></span></a></li>
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