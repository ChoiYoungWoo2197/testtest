<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
<%@ include file="/WEB-INF/include/contents.jsp"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<script type="text/javascript">
var loadOnce = true;

	$(document).ready(function() {
		stOrgList();
		$("#assetList").hide();

		searchList();
		getAssetListBelongG();
		//getAssetListNotBelongG();
	});

	function getSelectBox(id, code, mode, worker){

		var sqlUrl = "commonCode.";

		if(id == 'division') {
			if(mode == 'change'){
				id = "service";
				sqlUrl += "QR_SVCCODE_LIST";
			}else{
				sqlUrl += "QR_DIVCODE_LIST";
			}
		}
		else if(id == 'service') {
			if(mode == 'change'){
				id = "dept";
				sqlUrl += "QR_DEPCODE_LIST";
			}else{
				sqlUrl += "QR_SVCCODE_LIST";
			}
		}
		else if(id == 'dept')			{
			if(mode == 'change'){
				id = "worker";
				sqlUrl += "QR_WORKER_LIST"
			}else{
				sqlUrl += "QR_DEPCODE_LIST";
			}
		}
		else if(id == 'assGbn')		{	sqlUrl += "QR_COMMONCODE_LIST"	}

		$.ajax({
			 url		: "${pageContext.request.contextPath}/code/GetSelectBox.do",
			 type		: "post",
			 data		: {"code": code, "sqlUrl": sqlUrl, "mode": mode},
			 dataType	: "json",
			 success	: function(e){
				 setSelectBox(e, id, code);
			 },
			 error : function(){
				 alert('error' + id);
			 }
		 });
	}

	// select box
	function setSelectBox(data, id, code){
		var auth = "<%=C_SES_USER_AUTH%>";
		var worker = "";

		var option = '';
		if(auth == 'A' || (auth != 'P' && id == 'worker')){
			option = '<option value="A">-- 전체 --</option>';
		}else{
			if(id == 'workCycleTerm'){
				option = '<option value="A">-- 전체 --</option>';
			}
		}

		for(var i=0; i < data.result.length; i++) {
			option += '<option value="' + data.result[i].code+ '">' + data.result[i].name + '</option>';
		}

		$("#"+id+" > option").remove();
		$("#"+id).append(option);

		if(id != "division" && id != "service" && id != "dept"){
			$("#"+id+" option:first").attr("selected", "ture");		// 첫번째 선택(first or last)
		}else{
			if(id == "division"){
				$("#"+id+" > option[value=" + code +"]").attr("selected", "ture");

				if(auth == 'A'){
					code = $("#"+id).val();
				}else{
					code= "<%=C_SES_SERVICE_CD%>";
				}

				// SERVICE select box
				getSelectBox("service", code);
			}
			else if(id == "service"){
				$("#"+id+" > option[value=" + code +"]").attr("selected", "ture");

				if(auth == 'A'){
					code = $("#"+id).val();
				}else{
					code = "<%=C_SES_DEPT_CD%>";
				}
				// DEPT select box
				getSelectBox("dept", code);
			}
			else if(id == "dept"){
				$("#"+id+" > option[value=" + code +"]").attr("selected", "ture");

				if(auth == 'P'){
					worker = "<%=C_SES_USER_KEY%>";
				}
				// 담당자 select box
// 				getSelectBox("worker", code,"", worker);
			}
		}
	}

	function searchList(){
		var formData = formSerialize($("#searchForm"));

		$("#gridTb").jqGrid({
			url:"${pageContext.request.contextPath}/asset/FM-ASSET002_list.do",
			postData : formData,
			datatype : "json",
			mtype : "post",
			colNames:['key','자산분류키','서비스명','하위서비스명','부서명','담당자','자산분류명','자산수','부서코드','그룹코드','그룹명','그룹설명','등급','최종수정자id','최종등록일'],
			colModel:[
					  {name:'uagGrpKey',	index:'uagGrpKey',				align:'center',	hidden:true},
					  {name:'uagAssCat',	index:'uagAssCat',				align:'center',	hidden:true},
					  {name:'uagSvrNam',	index:'uagSvrNam',	width: 120,	align:'center',	hidden:false},
					  {name:'uagSubNam',	index:'uagSubNam',	width: 120,	align:'center',	hidden:false},
					  {name:'uagDepNam',	index:'uagDepNam',	width: 150,	align:'center',	hidden:false},
					  {name:'uagAdmNam',	index:'uagAdmNam',	width: 90,	align:'center',	hidden:false},
			          {name:'uagCatNam',	index:'uagCatNam',	width: 90,	align:'center',	hidden:false},
			          {name:'uagAssCod',	index:'uagAssCod',	width: 50,	align:'center',	hidden:false},
			          {name:'uagDepCod',	index:'uagDepCod',	width: 100,	align:'center',	hidden:false},
			          {name:'uagGrpCod',	index:'uagGrpCod',	width: 200,	align:'center',	hidden:false},
			          {name:'uagGrpNam',	index:'uagGrpNam',	width: 240,	align:'center',	hidden:false},
			          {name:'uagGrpDes',	index:'uagGrpDes',	width: 100,	align:'center',	hidden:false},
			          {name:'uagGrpLvl',	index:'uagGrpLvl',	width: 50,	align:'center',	hidden:false},
			          //{name:'uagUseYn',		index:'uagUseYn',	width: 50,	align:'center',	hidden:false},
			          {name:'uagUptId',		index:'uagUptId',				align:'center',	hidden:true},
			          //{name:'uptNam',		index:'uptNam',		width: 120,	align:'center',	hidden:false},
			          {name:'uagUptMdh',	index:'uagUptMdh',	width: 80,	align:'center',	hidden:false}
			],
			height: 165,		// 전체 grid 화면의 세로 사이즈 지정.
			pager : '#pager',
			rowNum : '300',
// 			rowList : [10, 20, 50],
			rownumbers : true,	// 각 row의 맨 앞줄에 각 row의 번호가 자동적으로 부여된다.
			sortable : true,	// 초기 sort 조건과 관련되며 ture이면 초기 데이터가 정렬되서 보인다.
			sortname : 'uagGrpNam',	// 초기 디이터 정렬시 정렬조건, 서버에 정렬  조건으로 전송된다.
			sortorder : 'asc',	// 순방향, 역방향 정렬(desc, asc)
			viewrecords : false,	// 오른쪽 하단에 총 몇개중 몇번째꺼 표시
			loadonce : false,	// reload 여부이며, true로 설정시 한번만 데이터를 받아오고 그 다음부터는 데이터를 받아오지 않는다.
			multiselect: false,	// select box 적용
			loadtext : 'loading..',	// 서버연동시 loading 중이라는 표시가 뜨는데 그곳의 문자열 지정.
			jsonReader:{
				repeatitems : true,
				root : function (obj) {return obj.result;},
			},
			onSelectRow: function(id){
				//var groupKey = $("#gridTb").getRowData(id).uagGrpKey;
				var groupKey = $("#gridTb").getRowData(id).uagGrpCod;
				var depCode = $("#gridTb").getRowData(id).uagDepCod;
				var catKey = $("#gridTb").getRowData(id).uagCatKey;
				reloadAssetListBelongG(groupKey);
				//reloadAssetListNotBelongG(catKey);
				$("#assetList").show();
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

		var formData = formSerialize($("#searchForm"));
		$("#gridTb").setGridParam({
			postData : formData,
			datatype : "json",
			ondblClickRow: function(id){
				//var groupKey = $("#gridTb").getRowData(id).uagGrpKey;
				var groupKey = $("#gridTb").getRowData(id).uagGrpCod;
				editAssetInfo(groupKey);
			}
		}).trigger("reloadGrid");
	}

	// 그룹 소속 자산 리스트
	function getAssetListBelongG(groupKey){
		$("#assetBelongGgrid").jqGrid({
			url:"${pageContext.request.contextPath}/asset/FM-ASSET002_assetlistBelongG.do",
			postData : {"code":groupKey, "searchCondition":"in"},
			datatype : "json",
			mtype : "post",
			colNames:['key','자산그룹코드','자산분류코드','자산분류명','서비스코드','서비스명','하위서비스코드','하위서비스명','부서코드','부서명','자산유형코드','자산유형명','자산명','용도','위치','등급'],
			colModel:[
					  {name:'uarAssKey',	index:'uarAssKey',								hidden:true},
			          {name:'uarGrpCod',	index:'uarGrpCod',								hidden:true},
			          {name:'uarAssCat',	index:'uarAssCat',	width: 100,	align:'center',	hidden:true},
			          {name:'uarCatNam',	index:'uarCatNam',	width: 120,	align:'center',	hidden:false},
			          {name:'uarSvrCod',	index:'uarSvrCod',	width: 120,	align:'center',	hidden:true},
			          {name:'uarSvrNam',	index:'uarSvrNam',	width: 120,	align:'center',	hidden:false},
			          {name:'uarSubCod',	index:'uarSubCod',	width: 120,	align:'center',	hidden:true},
			          {name:'uarSubNam',	index:'uarSubNam',	width: 120,	align:'center',	hidden:false},
			          {name:'uarDepCod',	index:'uarDepCod',	width: 120,	align:'center',	hidden:true},
			          {name:'uarDepNam',	index:'uarDepNam',	width: 120,	align:'center',	hidden:false},
			          {name:'uarAssGbn',	index:'uarAssGbn',	width: 120,	align:'center',	hidden:true},
			          {name:'uarAssNam',	index:'uarAssNam',	width: 120,	align:'center',	hidden:false},
			          {name:'uarEqpNam',	index:'uarEqpNam',	width: 120,	align:'center',	hidden:false},
			          {name:'uarDtlExp',	index:'uarDtlExp',	width: 120,	align:'center',	hidden:false},
			          {name:'uarAdmPos',	index:'uarAdmPos',	width: 120,	align:'center',	hidden:false},
			          {name:'uarAssLvl',	index:'uarAssLvl',	width: 120,	align:'center',	hidden:false}
			],
			height: 165,		// 전체 grid 화면의 세로 사이즈 지정.
			pager : '#abgPager',
			rowNum : '300',
// 			rowList : [10, 20, 50],
			rownumbers : true,	// 각 row의 맨 앞줄에 각 row의 번호가 자동적으로 부여된다.
			sortable : true,	// 초기 sort 조건과 관련되며 ture이면 초기 데이터가 정렬되서 보인다.
			sortname : 'uar_eqp_nam',	// 초기 디이터 정렬시 정렬조건, 서버에 정렬  조건으로 전송된다.
			sortorder : 'asc',	// 순방향, 역방향 정렬(desc, asc)
			viewrecords : false,	// 오른쪽 하단에 총 몇개중 몇번째꺼 표시
			loadonce : false,	// reload 여부이며, true로 설정시 한번만 데이터를 받아오고 그 다음부터는 데이터를 받아오지 않는다.
			multiselect: false,	// select box 적용
			loadtext : 'loading..',	// 서버연동시 loading 중이라는 표시가 뜨는데 그곳의 문자열 지정.
			jsonReader:{
				repeatitems : true,
				root : function (obj) {return obj.result;},
			},
			onSelectRow: function(id){
				var assetKey = $("#assetBelongGgrid").getRowData(id).uarAssKey;
				editAssetInfo(assetKey);
			}
		});
		// jqgrid 페이징바
		$("#assetBelongGgrid").jqGrid('navGrid','#abgPager'
			,{ excel:false,add:false,edit:false,view:false,del:false,search:false,refresh:false}
		);
		// jqgrid 전체 넓이 지정, 가로 스크롤 생성
		$("#assetBelongGgrid").setGridWidth(1018, false);
	}

	// get reload grid(그룹 소속 자산)
	function reloadAssetListBelongG(groupKey){

		$("#assetBelongGgrid").clearGridData();	// 이전 데이터 삭제

		$("#assetBelongGgrid").setGridParam({
			postData : {"code":groupKey, "searchCondition":"in"},
		}).trigger("reloadGrid");
	}

	// get reload grid(그룹 소속 자산)
	function reloadAssetListNotBelongG(catKey){

		$("#assetNotBelongGgrid").clearGridData();	// 이전 데이터 삭제

		$("#assetNotBelongGgrid").setGridParam({
			postData : {"catKey":catKey},
			datatype : "json"
		}).trigger("reloadGrid");
	}

	function editAssetGroupInfo(){
		var rowId = $("#gridTb").jqGrid('getGridParam', "selrow" );

		if(rowId == null){
			alert("수정할 그룹을 선택해 주십시오.");
			return false;
		}
		groupKey = $("#gridTb").getRowData(rowId).uagGrpKey;
		depCode = $("#gridTb").getRowData(rowId).uagDepCod;
		window.open("","assetGroupInfoPopup","width=460, height=560, menubar=no, location=no, status=no,scrollbars=yes");

		$("#uag_grp_key").val(groupKey);
		$("#uag_dep_cod").val(depCode);
		document.assetGroupInfoForm.submit();
	}

	function editAssetInfo(assetKey, mode) {
		openPostPopup("FM-ASSET001_popup.do", 800, 700, { uarAssKey : assetKey, mode : 0});
	}

	function updateAssetGroup(groupKey, assetKey){
		$.ajax({
			 url		: "${pageContext.request.contextPath}/asset/FM-ASSET002_groupAsset_update.do",
			 type		: "post",
			 data		: {"code": groupKey, "searchKeyword": assetKey},
			 dataType	: "json",
			 success	: function(e){
				 if(groupKey == ""){
					 alert('그룹에서 제외 되었습니다.');
				 }else{
					 alert('그룹에 등록 되었습니다.');
				 }
			 },
			 error : function(){
				 alert('error');
			 }
		 });
	}

	function reportDown(cod){
		if (!$("#service").val()) {
			alert("서비스를 선택해 주십시오.");
			return false;
		}
		window.open("","reportDown","width=1024, height=720, menubar=no, location=no, status=no,scrollbars=yes");
		$('#searchForm').attr('target','reportDown').attr('action','${pageContext.request.contextPath}/report/FM-ASSET002.do').submit();
		$('#searchForm').attr('target','');
 	}
</script>
<style>
.inp_work_inp20 { border:1px solid #949494;  width:20%; height:18px;}
</style>
</head>
<body>
	<c:import url="/titlebar.do" />
	<div class="search">
		<form id="searchForm" method="post" name="searchForm">
		    <div class="defalt">
		        <fieldset class="searchForm">
		            <legend>기본검색</legend>
		            <ul class="detail newField">
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
		            		<span class="title"><label for="assCat">자산분류</label></span>
		                	<sb:select name="assCat" type="CAT" allText="전체" />
		        	  	</li>
                        <li>
	                		<span class="title"><label for="assLvl">자산등급</label></span>
	                	    <select id="assLvl" name="assLvl" class="selectBox">
                   		      	<option value="">전체</option>
                     	    	<option value="L(1)">L(1)</option>
                     	    	<option value="M(2)">M(2)</option>
                     	    	<option value="H(3)">H(3)</option>
                     	    </select>
                     	</li>
		                <li>
		                    <span class="title"><label for="groupName">그룹이름</label></span>
		                    <input id="groupName" name="groupName" class="inputText wdShort" type="text" title="검색어 입력" placeholder="그룹이름 입력" style="width: 323px;"/>
		                </li>
		                <li class="btnArea">
		                 	<button type="button" onclick="searchListB();"  class="btnSearch">조건으로 검색</button>
		                 </li>
		            </ul>
		        </fieldset>
		    </div>
	    </form>
	</div>
	<div class="contents">
		<div class="title">그룹 내역</div>
		<div class="talbeArea">
			<div class="topBtnArea">
				<ul class="btnList">
					<li>
						<button type="button" onclick="reportDown();"><span class="icoDown"></span>자산 그룹핑 결과</button>
					</li>
					<li>
						<button type="button" onclick="editAssetGroupInfo();"><span class="icoAdd"></span>그룹 수정</button>
					</li>
					<!-- <li>
						<button type="button" onclick="editAssetGroupInfo();"><span class="icoAdd"></span>추가</button>
					</li> -->
				</ul>
			</div>
			<table id="gridTb">
			</table>
			<div id="pager"></div>
		</div>
	</div>
	<div class="contents">
        <!-- <div class="listMoveArea">
            <div class="listArea"> -->
                <div class="title">자산 내역</div>
                <div class="talbeArea">
                	<table id="assetBelongGgrid"></table>
					<div id="abgPager"></div>
                <!-- </div>
            </div> -->
            <!-- <ul class="actionBtnArea">
                <li><button class="arrow" onclick="javascript:goNotBelong();"><span>&gt;</span></button></li>
                <li><button class="arrow" onclick="javascript:goBelong();"><span>&lt;</span></button></li>
            </ul>
            <div class="listArea">
                <div class="title">미등록 자산현황</div>
                <div class="talbeArea">
                	<table id="assetNotBelongGgrid"></table>
					<div id="anbgPager"></div>
                </div>
            </div> -->
        </div>
    </div>
	<!-- 자산그룹 팝업폼 -->
	<form id="assetGroupInfoForm" name="assetGroupInfoForm" action="/asset/FM-ASSET002_popup.do" method="post" target="assetGroupInfoPopup">
	<input type="hidden" id="uag_grp_key" name="uag_grp_key"/>
	<input type="hidden" id="uag_dep_cod" name="uag_dep_cod"/>
	</form>
</body>
</html>