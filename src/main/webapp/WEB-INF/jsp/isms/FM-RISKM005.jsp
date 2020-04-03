<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<script type="text/javascript">
	$(document).ready(function() {
		searchList();
	});

	function searchList(){
		// form search 조건을 jqgrid에 json형식으로 넘기게 해줌.
		// SearchVO에 선언한 변수명과 name명 일치시켜서 사용.
		var formData = $("#searchForm").serialize();

		$("#gridTb").jqGrid({
			url:"${pageContext.request.contextPath}/riskm/FM-RISKM005_LIST.do",
			postData : formData,
			datatype : "json",
			mtype : "post",
			colNames:['key','시나리오코드','분류','통제목적','통제사항','시나리오내용',
			          '위협코드','참조코드','사용여부'],
			colModel:[
					  {name:'usmSroKey',	index:'usmSpoKey',								hidden:true},
			          {name:'usmSroCod',	index:'usmSroCod',	width: 80,	align:'center',	hidden:false},
			          {name:'usmSroLv0',	index:'usmSroLv0',	width: 120,	align:'left',	hidden:false},
			          {name:'usmSroLv1',	index:'usmSroLv1',	width: 150,	align:'center',	hidden:false},
			          {name:'usmSroLv2',	index:'usmSroLv2',	width: 150,	align:'center',	hidden:false},
			          {name:'usmSroDes',	index:'usmSroDes',	width: 200,	align:'center',	hidden:false},
			          {name:'usmCocKey',	index:'usmCocKey',	width: 80,	align:'center',	hidden:false},
			          {name:'usmRefCod',	index:'usmRefCod',	width: 100,	align:'center',	hidden:false},
			          {name:'usmUseYn',		index:'usmRefCod',	width: 60,	align:'center',	hidden:false}
			],
			height: 350,		// 전체 grid 화면의 세로 사이즈 지정.
			pager : '#pager',
			rowNum : '300',
// 			rowList : [10, 20, 50],
			rownumbers : true,	// 각 row의 맨 앞줄에 각 row의 번호가 자동적으로 부여된다.
			sortable : true,	// 초기 sort 조건과 관련되며 ture이면 초기 데이터가 정렬되서 보인다.
			sortname : 'usoCocKey',	// 초기 디이터 정렬시 정렬조건, 서버에 정렬  조건으로 전송된다.
			sortorder : 'desc',	// 순방향, 역방향 정렬(desc, asc)
			viewrecords : true,	// 오른쪽 하단에 총 몇개중 몇번째꺼 표시
			loadonce : false,	// reload 여부이며, true로 설정시 한번만 데이터를 받아오고 그 다음부터는 데이터를 받아오지 않는다.
			multiselect: false,	// select box 적용
			loadtext : 'loading..',	// 서버연동시 loading 중이라는 표시가 뜨는데 그곳의 문자열 지정.
			jsonReader:{
				repeatitems : true,
				root : function (obj) {return obj.result;},
			},
			onSelectRow: function(id){
				var usmSroKey = $("#gridTb").getRowData(id).usmSroKey;
				editScenario(usmSroKey);
			},
			ondblClickRow: function(id){
				var usmSroKey = $("#gridTb").getRowData(id).usmSroKey;
				editScenario(usmSroKey);
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
		// jqgrid 전체 넓이 지정, 가로 스크롤 생성
		$("#gridTb").setGridWidth(1018, false);
	}

	// get reload grid
	function searchListB(){

		$("#gridTb").clearGridData();	// 이전 데이터 삭제

		var formData = $("#searchForm").serialize();
		$("#gridTb").setGridParam({
			postData : formData,
			ondblClickRow: function(id){
				var assetKey = $("#gridTb").getRowData(id).uar_ass_key;
				editAssetInfo(assetKey);
			}
		}).trigger("reloadGrid");
	}

	function editScenario(usmSroKey){
		window.open("","sroPop","width=600, height=400, menubar=no, location=no, status=no,scrollbars=yes");
		$("#usmSroKey").val(usmSroKey);
		document.popForm.submit();
	}

</script>
</head>
<body>
<form id="searchForm" name="searchForm" method="post">
	<c:import url="/titlebar.do" />
	<div class="search">
	    <div class="defalt">
	       <fieldset class="searchForm">
	            <legend>기본검색</legend>
	            <ul class="detail newField">
	            	<li>
						<span class="title"><label for="usmSroLv2">통제사항</label></span>
						<input type="text" id="usmSroLv2" name="usmSroLv2" class="inputText wdShort" title="통제사항 입력" placeholder="통제사항 입력"/>
					</li>
					<li>
						<span class="title"><label for="usmCocKey">위협코드</label></span>
						<input type="text" id="usmCocKey" name="usmCocKey" class="inputText wdShort" title="위협코드 입력" placeholder="위협코드 입력"/>
					</li>
					<li>
						<span class="title"><label for="usmRefCod">참조코드</label></span>
						<input type="text" id="usmRefCod" name="usmRefCod" class="inputText wdShort" title="참조코드 입력" placeholder="참조코드 입력"/>
					</li>
					<li>
						<span class="title"><label for="usmSroCod">시나리오코드</label></span>
						<input type="text" id="usmSroCod" name="usmSroCod" class="inputText wdShort" title="시나리오코드 입력" placeholder="시나리오코드 입력"/>
					</li>
	            	<li>
						<span class="title"><label for="usmSroDes">시나리오</label></span>
						<input type="text" id="usmSroDes" name="usmSroDes" class="inputText wdShort" title="시나리오 입력" placeholder="시나리오 입력" style="width: 217px;"/>
					</li>
	                <li class="btnArea">
	                 	<button onclick="searchListB(); return false;"  class="btnSearch">조건으로 검색</button>
	                 </li>
	            </ul>
	        </fieldset>
	    </div>
	</div>
	<div class="contents">
		<div class="topBtnArea">
			<ul class="btnList">
				<li>
					<button onclick="editScenario(''); return false;"><span class="icoAdd"></span>추가</button>
				</li>
			</ul>
		</div>
		<div class="talbeArea">
			<table id="gridTb">
			</table>
			<div id="pager"></div>
		</div>
	</div>
</form>
<form id="popForm" name="popForm" action="FM-RISKM005_popup.do" method="post" target="sroPop">
<input type="hidden" id="usmSroKey" name="usmSroKey"/>
</form>
</body>
</html>