<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
<%@ include file="/WEB-INF/include/contents.jsp"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<script type="text/javascript">
var detailLoadOnce = true;
var actLoadOnce = true;
	$(document).ready(function() {
		stOrgList();
		serachList();
	});

	// get reload grid
	function searchListB(){
		 reloadGetRskDetailList("");
		$("#gridTb").clearGridData();	// 이전 데이터 삭제
		$("#gridTb").setGridParam({
			postData : formSerialize($("#searchForm")),
			datatype : "json",
			ondblClickRow: function(id){
				var grpKey = $("#gridTb").getRowData(id).uagGrpKey;
				getRskDetailList(grpKey);
			}
		}).trigger("reloadGrid");
	}


	// get reload grid(위험내역)
	function reloadGetRskDetailList(grpKey){
		$("#grpKey").val(grpKey);
		$("#rskDetailGgrid").clearGridData();	// 이전 데이터 삭제
		$("#rskDetailGgrid").setGridParam({
			postData : formSerialize($("#searchForm")),
		}).trigger("reloadGrid");
	}

	function goPage() {
		location.href = "FM-RISKM006_M.do";
	}

	function excelUpload(){
		window.open("","excelPopup","width=450, height=250, menubar=no, location=no, status=no, scrollbars=auto");
		$("#upType").val("D");
		document.excelPopForm.submit();
	}

	function excelDown(){
		window.open("","excelDownPopup","width=450, height=250, menubar=no, location=no, status=no, scrollbars=auto");
		$("#downType").val("D");
		document.excelDownloadForm.submit();
	}

	/*리스트 조회*/
	function serachList(){
		var formData = formSerialize($("#searchForm"));

		$("#gridTb").jqGrid({
			url:"${pageContext.request.contextPath}/riskm/FM-RISKM009_list.do",
			postData : formData,
			datatype : "json",
			mtype : "post",
			colNames:['자산코드','서비스명','본부','그룹,담당','부서명','부서코드','평가여부','서비스코드'],
			colModel:[
					  {name:'urdDepKey',	index:'urdDepKey',									hidden:true},
					  {name:'uagSvrNam',	index:'uagSvrNam',		width: 120,	align:'center',	hidden:false},
					  {name:'stOrg',		index:'stOrg',			width: 200,	align:'center',	hidden:false},
			          {name:'hqOrg',		index:'hqOrg',			width: 200,	align:'center',	hidden:false},
			          {name:'gpOrg',		index:'gpOrg',			width: 200,	align:'center',	hidden:false},
					  {name:'udmDepCod',	index:'udmDepCod',		width: 140,	align:'center',	hidden:false},
			          {name:'rstYn',		index:'rstYn',			width: 95,	align:'center',	hidden:false},
					  {name:'uagSvrCod',	index:'uagSvrCod',									hidden:true}
			],
			height: 400,		// 전체 grid 화면의 세로 사이즈 지정.
			pager : '#pager',
			rowNum : '300',
// 			rowList : [10, 20, 50],
			rownumbers : true,	// 각 row의 맨 앞줄에 각 row의 번호가 자동적으로 부여된다.
			sortable : true,	// 초기 sort 조건과 관련되며 ture이면 초기 데이터가 정렬되서 보인다.
			sortname : 'uarGrpCod',	// 초기 디이터 정렬시 정렬조건, 서버에 정렬  조건으로 전송된다.
			sortorder : 'asc',	// 순방향, 역방향 정렬(desc, asc)
			viewrecords : false,	// 오른쪽 하단에 총 몇개중 몇번째꺼 표시
			loadonce : true,	// reload 여부이며, true로 설정시 한번만 데이터를 받아오고 그 다음부터는 데이터를 받아오지 않는다.
			multiselect: false,	// select box 적용
			loadtext : 'loading..',	// 서버연동시 loading 중이라는 표시가 뜨는데 그곳의 문자열 지정.
			jsonReader:{
				repeatitems : true,
				root : function (obj) {return obj.result;},
			},
			onSelectRow: function(id){
				var udmDepCod = $("#gridTb").getRowData(id).udmDepCod;
				selectRowItem(udmDepCod);

			},
			ondblClickRow: function(id){
				var uarAssCod = $("#gridTb").getRowData(id).uarAssCod;
				//grpInfoPopup(grpKey);
			},
			loadComplete: function(xhr) {
			},
			loadError: function(xhr,st,err) {
			}
		});
		// jqgrid 페이징바
		$("#gridTb").jqGrid('navGrid','#pager'
			,{ excel:false,add:false,edit:false,view:false,del:false,search:false,refresh:false}
		);
		// jqgrid 전체 넓이 지정, 가로 스크롤 생성
		$("#gridTb").setGridWidth(1018, false);
	}

	function selectRowItem(udmDepCod){
		window.open("","detailPopup","width=900, height=590, menubar=no, location=no, status=no, scrollbars=auto");
		$("#udmDepCod").val(udmDepCod);
		document.detailPopForm.submit();
	}
</script>
</head>
<body>
	<c:import url="/titlebar.do" />
	<div class="search">
		<form id="searchForm" method="post" name="searchForm">
			<input type="hidden" id="grpKey" name="grpKey" />
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
		                <li class="btnArea">
		                 	<button onclick="searchListB(); return false;"  class="btnSearch">조건으로 검색</button>
						</li>
		            </ul>
		        </fieldset>
		    </div>
	    </form>
	</div>
	<div class="contents">
		<div class="title">&nbsp;</div>
		<div class="talbeArea">
			<div class="topBtnArea">
				<ul class="btnList">
					<li>
						<button type="button" onclick="excelUpload();"><span class="icoSave"></span>엑셀업로드</button>
					</li>
					<li>
						<button type="button" onclick="excelDown();"><span class="icoDown"></span>취약점 결과 다운로드</button>
					</li>
				</ul>
			</div>
			<table id="gridTb">
			</table>
			<div id="pager"></div>
		</div>
	</div>
	<form id="excelPopForm" name="excelPopForm" action="FM-RISKM009_excel_popup.do" method="post" target="excelPopup">
		<input type="hidden" id="excelSvcCod" name="excelSvcCod"/>
		<input type="hidden" id="excelDepCod" name="excelDepCod"/>
		<input type="hidden" id="upType" name="upType"/>
	</form>
	<form id="detailPopForm" name="detailPopForm" action="FM-RISKM009_detail_popup.do" method="post" target="detailPopup">
		<input type="hidden" id="udmDepCod" name="udmDepCod"/>
	</form>
	<form id="excelDownloadForm" name="excelDownloadForm" action="FM-RISKM009_excel_down_popup.do" method="post" target="excelDownPopup">
		<input type="hidden" id="downType" name="downType"/>
	</form>
</body>
</html>