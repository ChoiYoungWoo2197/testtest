<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<script type="text/javascript">
	$(document).ready(function() {
		stOrgList();
		searchList();
	});

	function searchList(){
		var getColumnIndexByName = function(grid,columnName) {
            var cm = grid.jqGrid('getGridParam','colModel'), i=0,l=cm.length;
            for (; i<l; i+=1) {
                if (cm[i].name===columnName) {
                    return i; // return the index
                }
            }
            return -1;
        }

		// form search 조건을 jqgrid에 json형식으로 넘기게 해줌.
		// SearchVO에 선언한 변수명과 name명 일치시켜서 사용.
		var formData = formSerialize($("#searchForm"));

		$("#gridTb").jqGrid({
			url:"${pageContext.request.contextPath}/riskm/FM-RISKM008_LIST.do",
			postData : formData,
			datatype : "json",
			mtype : "post",
			colNames:['key','서비스명','본부','그룹,담당','부서명','부서코드','담당자','변경','서비스코드'],//,'진단유형'
			colModel:[
					  {name:'urdDepKey',	index:'urdDepKey',					align:'center',	hidden:true},
			          {name:'uagSvrNam',	index:'uagSvrNam',		width: 80,	align:'center',	hidden:false},
			          {name:'stOrg',		index:'stOrg',			width: 130,	align:'left',	hidden:false},
			          {name:'hqOrg',		index:'hqOrg',			width: 130,	align:'left',	hidden:false},
			          {name:'gpOrg',		index:'gpOrg',			width: 130,	align:'left',	hidden:false},
			          {name:'udmDepCod',	index:'udmDepCod',		width: 100,	align:'center',	hidden:false},
			          {name:'urdMngNam',	index:'urdMngNam',		width: 90,	align:'center', hidden:false},
			          {name:'act', 			index: 'act', 			width: 28,	align:'center', sortable: false,
			        	  formatter: 'actions', formatoptions: {editbutton:false, delbutton:false}},
			          {name:'uagSvrCod',	index:'uagSvrCod',		                        	hidden:true}
			          //,{name:'uacCatNam',	index:'uacCatNam',		width: 150,	align:'center',	hidden:false}
			],
			height: 350,		// 전체 grid 화면의 세로 사이즈 지정.
			pager : '#pager',
			rowNum : '0',
// 			rowList : [10, 20, 50],
			rownumbers : true,	// 각 row의 맨 앞줄에 각 row의 번호가 자동적으로 부여된다.
			sortable : true,	// 초기 sort 조건과 관련되며 ture이면 초기 데이터가 정렬되서 보인다.
			sortname : 'urdDepKey',	// 초기 디이터 정렬시 정렬조건, 서버에 정렬  조건으로 전송된다.
			sortorder : 'desc',	// 순방향, 역방향 정렬(desc, asc)
			viewrecords : false,	// 오른쪽 하단에 총 몇개중 몇번째꺼 표시
			loadonce : false,	// reload 여부이며, true로 설정시 한번만 데이터를 받아오고 그 다음부터는 데이터를 받아오지 않는다.
			multiselect: true,	// select box 적용
			loadtext : 'loading..',	// 서버연동시 loading 중이라는 표시가 뜨는데 그곳의 문자열 지정.
			autowidth: true,
			ShrinkToFit: false,
			jsonReader:{
				repeatitems : true,
				root : function (obj) {return obj.result;},
			},
			onSelectRow: function(id){
				//var urdDepKey = $("#gridTb").getRowData(id).urdDepKey;
			},
			ondblClickRow: function(id){
				//var urdDepKey = $("#gridTb").getRowData(id).urdDepKey;
			},
			loadComplete: function(xhr) {
	                var iCol = getColumnIndexByName($("#gridTb"), 'act');
	                $(this).find(">tbody>tr.jqgrow>td:nth-child(" + (iCol + 1) + ")")
	                    .each(function() {
	                        $("<div>", {
	                            title: "담당자 지정",
	                            click: function(e) {
	                            	e.stopPropagation();
                                	var id = $(e.target).closest("tr.jqgrow").attr("id");
                               		openSelectMng(id);
	                            }
	                        }
	                      ).css({"margin-right": "5px", float: "left", cursor: "pointer"})
	                       .addClass("ui-pg-div ui-inline-custom")
	                       .append('<span class="ui-icon ui-icon-person"></span>')
	                       .prependTo($(this).children("div"));
	                });

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


	// get reload grid
	function searchListB(){

		$("#gridTb").clearGridData();	// 이전 데이터 삭제

		var formData = formSerialize($("#searchForm"));
		$("#gridTb").setGridParam({
			postData : formData,
			datatype : "json"
		}).trigger("reloadGrid");
	}
	function addDepBtn(){
		window.open("","addRskDepPop","width=600, height=630, menubar=no, location=no, status=no,scrollbars=yes");
		document.popForm.submit();
	}

	function delDepBtn(){
		var list = $("#gridTb").jqGrid('getGridParam', 'selarrrow');
		var delList = "";
		for(var i = 0; i < list.length; i++){
			var value = $("#gridTb").jqGrid('getCell', list[i], 'urdDepKey');
			if ( i == list.length-1){
				delList += value;                                 //  선택된 행의 Code를 줄줄이 가져옴
			} else{
				delList += value + ",";
			}
		}

		if(delList.length <= 0 ){
			alert("선택된 부서가 없습니다.");
			return;
		}

		if( confirm('삭제하시겠습니까?') ) {
			$.ajax({
				url			: "${pageContext.request.contextPath}/riskm/FM-RISKM008_delRskDepList.do",
				type		: "post",
				data		: {'delList':delList},
				dataType	: "json",
				async		: false,
				success		: function(data){
					searchListB();
					alert("정상적으로 삭제 되었습니다.");
				},
				error : function(){
					alert('error');
				}
			});
		}else{
			return;
		}
	}

	function openSelectMng(id) {
		$obj = $("#gridTb").getRowData(id);
   		$("#depKey2").val($obj.urdDepKey);
   		$("#depCod2").val($obj.udmDepCod);
		window.open("","mngPopup","width=598, height=520, menubar=no, location=no, status=no, scrollbars=no");
		$("#mngPopForm").submit();
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
	                    <sb:select name="service" type="DIV" forbidden="true"/>
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
					<button onclick="addDepBtn(); return false;"><span class="icoAdd"></span>추가</button>
					<button onclick="delDepBtn(); return false;"><span class="icoDel"></span>삭제</button>
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
<form id="popForm" name="popForm" action="FM-RISKM008_Dep_Popup.do" method="post" target="addRskDepPop">
</form>
<form id="mngPopForm" name="mngPopForm" action="FM-RISKM008_mng_popup.do" method="post" target="mngPopup">
	<input type="hidden" id="depKey2" name="depKey" />
	<input type="hidden" id="depCod2" name="depCod" />
</form>
</body>
</html>