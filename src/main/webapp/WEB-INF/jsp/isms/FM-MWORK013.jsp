<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib  prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/include/head.jsp" %>
<%@ include file="/WEB-INF/include/contents.jsp"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<script type="text/javascript">
	$(document).ready(function() {
		go(window.location.hash.substr(1));
	});

	window.onhashchange = function() {
		go(window.location.hash.substr(1));
	}

	function go(gubun) {
		if ( !gubun) {
			gubun = "A";
		}
		var category = "";

		$("#listTab").find("li").each(function() {
			if ($(this).attr("value") == gubun) {
				$(this).addClass("sel");
				category = $(this).attr("title");
			}
			else {
				$(this).removeClass("sel");
			}
		});

		// 결재업무(요청) get
		//getAppWork(gubun, category);
		$("#gubun").val(gubun);
		searchList();
	}


	// 2018-07-18 s, table -> grid
	function searchList() {

		var gubun = $("#gubun").val();
		$("#gridTb").jqGrid("GridUnload");	// 동적데이터 전송시 필수

		$("#gridTb").jqGrid({
			url: "${pageContext.request.contextPath}/mwork/FM-MWORK013_getAppList.do",
			datatype : "json",
			mtype : "post",
			ajaxGridOptions : {async:false}, //JqGrid에서  async기본 값은 true로 비동기 요청을 하므로, 옵션에 false로 적용하게 되면 동기 요청을 하면서 순차적으로 처리가 가능해진다.
			colNames:['wrkKey','trckey','appKey','appGbn','결재유형','상신일','상신자','결재내용','1차 승인일','1차 결재자','1차 의견','2차 승인일','2차 결재자','2차 의견'],
			colModel:[
					  {name:'uamWrkKey',	index:'uam_wrk_key',	hidden:true},
			          {name:'utwTrcKey',	index:'utw_trc_key',	hidden:true},
			          {name:'uamAppKey',	index:'uam_app_key',	hidden:true},
			          {name:'uamAppGbn',	index:'uam_app_gbn',	hidden:true},
			          {name:'appGbnNam',	index:'app_gbn_nam',	width:70,	align:'center'},
			          {name:'uamReqMdh',	index:'uam_req_mdh',	width:110,	align:'center'},
			          {name:'uamReqNam',	index:'uam_req_nam',	width:80,	align:'center'},
			          {name:'utdDocNam',	index:'utd_doc_nam',	width:250,	align:'left'},
			          {name:'uamCf1Mdh',	index:'uam_cf1_mdh',	width:110,	align:'center'},
			          {name:'uamCf1Nam',	index:'uam_cf1_nam',	width:70,	align:'center'},
			          {name:'uamCf1Etc',	index:'uam_cf1_etc',	width:120,	align:'left', sortable:false},
			          {name:'uamCf2Mdh',	index:'uam_cf2_mdh',	width:110,	align:'center'},
			          {name:'uamCf2Nam',	index:'uam_cf2_nam',	width:70,	align:'center'},
			          {name:'uamCf2Etc',	index:'uam_cf2_etc',	width:120,	align:'left', sortable:false}
			],
			pager : '#pager',
			rowNum : 50,
			rowList : [50, 100000],
			postData : {code: gubun},
			rownumbers : true,	// 각 row의 맨 앞줄에 각 row의 번호가 자동적으로 부여된다.
			sortable : true,	// 초기 sort 조건과 관련되며 ture이면 초기 데이터가 정렬되서 보인다.
			sortname : 'uam_req_mdh',	// 초기 디이터 정렬시 정렬조건, 서버에 정렬  조건으로 전송된다.
			sortorder : 'desc',	// 순방향, 역방향 정렬(desc, asc)
			viewrecords : true,	// 오른쪽 하단에 총 몇개중 몇번째꺼 표시
			loadonce : false,	// reload 여부이며, true로 설정시 한번만 데이터를 받아오고 그 다음부터는 데이터를 받아오지 않는다.
			multiselect: false,	// select box 적용
			jsonReader:{
				repeatitems : true,
				root : function (obj) {return obj.result},
				page: function (obj) {return obj.currentPageNo},
			    total: function (obj) {return obj.totalPage},
			    records: function (obj) {return obj.totalRecord}
			},
			onSelectRow: function(id, status, e){
				var wrkKey = $("#gridTb").getRowData(id).uamWrkKey;
				var trcKey = $("#gridTb").getRowData(id).utwTrcKey;
				var appGbn = $("#gridTb").getRowData(id).uamAppGbn;
				var appKey = $("#gridTb").getRowData(id).uamAppKey;
				appPopup(wrkKey, trcKey, appGbn, appKey);
			},
			loadComplete: function(xhr) {
				// 2018-11-07 s,
			    $("option[value=100000]").text('전체');
			}
		}).trigger("reloadGrid");
		// jqgrid 페이징바
		$("#gridTb").jqGrid('navGrid','#pager'
			,{ excel:false,add:false,edit:false,view:false,del:false,search:false,refresh:false}
		);
		// jqgrid 전체 넓이 지정, 가로 스크롤 생성
		$("#gridTb").setGridWidth(1018, false);
	}

	/*
	function getAppWork(gubun, category, workKey){
		$.ajax({
			url		: "${pageContext.request.contextPath}/mwork/FM-MWORK013_getAppList.do",
			type	: "post",
			data	: {"code": gubun, "workCode":workKey},
			dataType: "json",
			success	: function(data){
				var list = "";
				var key = "";
				for(var i=0; i<data.result.length; i++){
					list += '<tr>';
					if(gubun != "H"){
						list += '<td><a href="javascript:getAppWorkHistory('+data.result[i].uamWrkKey+')">R</a></td>';
					}
					list += '<td>'+data.result[i].appGbnNam+'</td>';
					list += '<td>'+data.result[i].uamReqMdh+'</td>';
					list += '<td>'+data.result[i].uamReqNam+'</td>';

					if (data.result[i].uamAppGbn == "1") {
						list += '<td><a href="javascript:workPopup(\''+data.result[i].uamWrkKey+'\',\''+data.result[i].utwTrcKey+'\')" class="wd120">'+data.result[i].utdDocNam+'</a></td>';
					} else {
						list += '<td><a href="javascript:transPopup(\''+data.result[i].uamWrkKey+'\',\''+data.result[i].uamAppKey+'\')" class="wd120">업무 이관 요청</a></td>';
					}

					if(gubun == "H"){
						list += '<td id=\'cf1Mdh'+data.result[i].uamAppKey+'\'>'+data.result[i].uamCf1Mdh+'</td>';
					}else{
						list += '<td id=\'cf1Mdh'+data.result[i].uamAppKey+'\'>'+data.result[i].uamCf1Mdh.substring(0, 10)+'</td>';
					}
					list += '<td id=\'cf1Id'+data.result[i].uamAppKey+'\' value=\''+data.result[i].uamCf1Id+'\'>'+data.result[i].uamCf1Nam+'</td>';
					list += '<td id=\'cf1Etc'+data.result[i].uamAppKey+'\'>'+data.result[i].uamCf1Etc+'</td>';
					if(gubun == "H"){
						list += '<td id=\'cf2Mdh'+data.result[i].uamAppKey+'\'>'+data.result[i].uamCf2Mdh+'</td>';
					}else{
						list += '<td id=\'cf2Mdh'+data.result[i].uamAppKey+'\'>'+data.result[i].uamCf2Mdh.substring(0, 10)+'</td>';
					}
					list += '<td id=\'cf2Id'+data.result[i].uamAppKey+'\' value=\''+data.result[i].uamCf2Id+'\'>'+data.result[i].uamCf2Nam+'</td>';
					list += '<td id=\'cf2Etc'+data.result[i].uamAppKey+'\'>'+data.result[i].uamCf2Etc+'</td>';
					list += '</tr>';
				}
				if(data.result.length == 0){
					list += '<tr><td class="noDataList" colspan="11">'+category+' 문서가 없습니다.</td></tr>';
				}

				$("#dataList").html(list);
				$("#dataList").find("tr").each(function() {
					$(this).find("td:last").addClass("last");
				});
			},
			error : function(){
				alert('error');
			},
		});
	}*/

	function transPopup(wrkKey, appKey) {
		var win = window.open("","workPopup","width=730, height=700, menubar=no, location=no, status=no,scrollbars=yes");
		$("#utwWrkKey").val(wrkKey);
		$("#appKey").val(appKey);
		$("#workPopupForm").attr("action", "FM-MWORK_transPopup.do").submit();
		win.focus();
	}

	function getAppWorkHistory(workKey){
		window.open("","hisPopup","width=850, height=600, menubar=no, location=no, status=no, scrollbars=yes");
		$("#workCode").val(workKey);
		document.hisPopupForm.submit();
	}

	// 2018-07-18 s,
	function appPopup(wrkKey, trcKey, appGbn, appKey) {
		if (appGbn == "1") {
			workPopup(wrkKey, trcKey);
		}
		else {
			transPopup(wrkKey, appKey);
		}
	}
</script>
</head>
<body>
<input type="hidden" id="gubun"/>
<form id="appInfoForm" name="appInfoForm" method="post">
<input type="hidden" id="uam_app_key"	name="uam_app_key"	value="">
<input type="hidden" id="uam_app_lev"	name="uam_app_lev"	value="">
<input type="hidden" id="uam_sta_cod"	name="uam_sta_cod"	value="">
<input type="hidden" id="uam_cf1_id"	name="uam_cf1_id"	value="">
<input type="hidden" id="uam_cf1_mdh"	name="uam_cf1_mdh"	value="">
<input type="hidden" id="uam_cf1_etc"	name="uam_cf1_etc"	value="">
<input type="hidden" id="uam_cf2_id"	name="uam_cf2_id"	value="">
<input type="hidden" id="uam_cf2_mdh"	name="uam_cf2_mdh"	value="">
<input type="hidden" id="uam_cf2_etc"	name="uam_cf2_etc"	value="">
<input type="hidden" id="utw_wrk_id"	name="utw_wrk_id"	value="">
<input type="hidden" id="utw_agn_id"	name="utw_agn_id"	value="">
<input type="hidden" id="utd_doc_nam"	name="utd_doc_nam"	value="">
<input type="hidden" id="uam_upt_id"	name="uam_upt_id"	value="<%=C_SES_USER_KEY%>">
</form>
	<c:import url="/titlebar.do" />
	<div class="contents">
		<div class="tabArea">
		    <ul id="listTab" class="list4Tab">
		        <li value="A" title="결재승인" class="sel"><a href="javascript:go('A');">결재승인</a></li>
		        <li value="I" title="결재요청"><a href="javascript:go('I');">결재요청</a></li>
		        <li value="R" title="반려"><a href="javascript:go('R');">반려문서</a></li>
		        <li value="C" title="완료"><a href="javascript:go('C');">완료문서</a></li>
		    </ul>
		</div>
		<div class="talbeArea">
			<table id="gridTb">
			</table>
			<!-- con_tbl -->
			<div id="pager"></div>
		</div>
		<!--
		<div class="talbeArea">
			<table summary="결재관리">
				<colgroup>
					<col width="5%"/>
					<col width="8%"/>
					<col width="8%"/>
					<col width="7%"/>
					<col/>
					<col width="8%"/>
					<col width="7%"/>
					<col width="12%"/>
					<col width="8%"/>
					<col width="7%"/>
					<col width="12%"/>
				</colgroup>
				<caption>결재관리</caption>
				<thead>
				    <tr>
						<th rowspan="2">이력</th>
						<th rowspan="2">결재유형</th>
						<th rowspan="2">상신일</th>
						<th rowspan="2">상신자</th>
						<th rowspan="2">결재내용</th>
						<th colspan="3">1차 결재</th>
						<th class="last" colspan="3">2차 결재</th>
					</tr>
					<tr>
						<th>승인일</th>
						<th>결재자</th>
						<th>의견</th>
						<th>승인일</th>
						<th>결재자</th>
						<th class="last">의견</th>
					</tr>
				</thead>
				<tbody id="dataList">
				</tbody>
			</table>
		</div>
		 -->
	</div>

<form id="workPopupForm" name="workPopupForm" method="post" target="workPopup">
	<input type="hidden" id="utwWrkKey" name="utwWrkKey"/>
	<input type="hidden" id="utwTrcKey" name="utwTrcKey"/>
	<input type="hidden" id="appKey" name="appKey"/>
</form>
<form id="hisPopupForm" name="hisPopupForm" action="FM-MWORK013_his_popup.do" method="post" target="hisPopup">
	<input type="hidden" id="workCode" name="workCode" value="">
</form>
</body>
</html>