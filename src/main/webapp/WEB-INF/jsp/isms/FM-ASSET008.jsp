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

	// get reload grid
	function searchListB(){

		$("#gridTb").clearGridData();	// 이전 데이터 삭제
		var formData = $("#searchForm").serialize();
		$("#gridTb").setGridParam({
			postData : formData,
			datatype : "json",
			ondblClickRow: function(id){
				var uacCatKey = $("#gridTb").getRowData(id).uacCatKey;
				editAssetInfo(uacCatKey);
			}
		}).trigger("reloadGrid");
	}

	function searchList(){
		// form search 조건을 jqgrid에 json형식으로 넘기게 해줌.
		// SearchVO에 선언한 변수명과 name명 일치시켜서 사용.
		var formData = $("#searchForm").serialize();

		$("#gridTb").jqGrid({
			url:"${pageContext.request.contextPath}/asset/FM-ASSET008_LIST.do",
			postData : formData,
			datatype : "json",
			mtype : "post",
			colNames:['key','자산유형명','자산유형코드','위험관리구분','사용여부','가변컬럼01','가변컬럼02',
			          '가변컬럼03','가변컬럼04','가변컬럼05','가변컬럼06','가변컬럼07',
			          '가변컬럼08','가변컬럼09','가변컬럼10','등록자','등록일',
			          '수정자','수정일'],
			colModel:[
					  {name:'uacCatKey',	index:'uacCatKey',				align:'center',	hidden:true},
			          {name:'uacCatNam',	index:'uacCatNam',	width: 150,	align:'center',	hidden:false},
			          {name:'uacCatCod',	index:'uacCatCod',	width: 100,	align:'center',	hidden:false},
			          {name:'uacCatTyp',	index:'uacCatTyp',	width: 100,	align:'center',	hidden:false},
			          {name:'uacUseYn',		index:'uacUseYn',	width: 80,	align:'center',	hidden:false},
			          {name:'uacValCl0',	index:'uacValCl0',	width: 150,	align:'left',	hidden:false},
			          {name:'uacValCl1',	index:'uacValCl1',	width: 150,	align:'left',	hidden:false},
			          {name:'uacValCl2',	index:'uacValCl2',	width: 150,	align:'left',	hidden:false},
			          {name:'uacValCl3',	index:'uacValCl3',	width: 150,	align:'left',	hidden:false},
			          {name:'uacValCl4',	index:'uacValCl4',	width: 150,	align:'left',	hidden:false},
			          {name:'uacValCl5',	index:'uacValCl5',	width: 150,	align:'left',	hidden:false},
			          {name:'uacValCl6',	index:'uacValCl6',	width: 150,	align:'left',	hidden:false},

			          {name:'uacValCl7',	index:'uacValCl7',	width: 150,	align:'left',	hidden:false},
			          {name:'uacValCl8',	index:'uacValCl8',	width: 150,	align:'left',	hidden:false},
			          {name:'uacValCl9',	index:'uacValCl9',	width: 150,	align:'left',	hidden:false},
			          {name:'uacRgtNam',	index:'uacRgtNam',	width: 100,	align:'center',	hidden:false},
			          {name:'uacRgtMdh',	index:'uacRgtMdh',	width: 100,	align:'center',	hidden:false},
			          {name:'uacUptNam',	index:'uacUptNam',	width: 100,	align:'center',	hidden:false},
			          {name:'uacUptMdh',	index:'uacUptMdh',	width: 100,	align:'center',	hidden:false}
			],
			height: 350,		// 전체 grid 화면의 세로 사이즈 지정.
			pager : '#pager',
			rowNum : '300',
// 			rowList : [10, 20, 50],
			rownumbers : true,	// 각 row의 맨 앞줄에 각 row의 번호가 자동적으로 부여된다.
			sortable : true,	// 초기 sort 조건과 관련되며 ture이면 초기 데이터가 정렬되서 보인다.
			sortname : 'uar_ass_key',	// 초기 디이터 정렬시 정렬조건, 서버에 정렬  조건으로 전송된다.
			sortorder : 'asc',	// 순방향, 역방향 정렬(desc, asc)
			viewrecords : true,	// 오른쪽 하단에 총 몇개중 몇번째꺼 표시
			loadonce : true,	// reload 여부이며, true로 설정시 한번만 데이터를 받아오고 그 다음부터는 데이터를 받아오지 않는다.
			multiselect: false,	// select box 적용
			loadtext : 'loading..',	// 서버연동시 loading 중이라는 표시가 뜨는데 그곳의 문자열 지정.
			jsonReader:{
				repeatitems : true,
				root : function (obj) {return obj.result;},
			},
			onSelectRow: function(id){
				var uacCatKey = $("#gridTb").getRowData(id).uacCatKey;
				editAssetInfo(uacCatKey);
			},
			ondblClickRow: function(id){
				var uacCatKey = $("#gridTb").getRowData(id).uacCatKey;
				editAssetInfo(uacCatKey);
			},
			loadComplete: function(xhr) {
			},
			loadError: function(xhr,st,err) {
			},
		});
		// jqgrid 페이징바
		$("#gridTb").jqGrid('navGrid','#pager'
			,{ excel:false,add:false,edit:false,view:false,del:false,search:false,refresh:false}
		);
		// jqgrid 전체 넓이 지정, 가로 스크롤 생성
		$("#gridTb").setGridWidth(1018, false);
	}

	function editAssetInfo(uacCatKey){
		window.open("","assPopup","width=550, height=700, menubar=no, location=no, status=no,scrollbars=yes");
		$("#uacCatKey").val(uacCatKey);
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
	            <ul class="defalt">
	            	<li>
	                    <input type="text" id="searchCondition" name="searchCondition" class="inputText" value="${searchVO.searchCondition}" title="자산유형명 입력" placeholder="자산유형명 입력하세요."  />
	                </li>
	            </ul>
	            <button type="button" onclick="searchListB();" class="btnSearch">검색</button>
	        </fieldset>
	    </div>
	</div>
	<div class="contents">
		<div class="topBtnArea">
			<ul class="btnList">
				<li>
					<button type="button" onclick="editAssetInfo('');"><span class="icoAdd"></span>추가</button>
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
<form id="popForm" name="popForm" action="FM-ASSET008_popup.do" method="post" target="assPopup">
<input type="hidden" id="uacCatKey" name="uacCatKey"/>
</form>
</body>
</html>