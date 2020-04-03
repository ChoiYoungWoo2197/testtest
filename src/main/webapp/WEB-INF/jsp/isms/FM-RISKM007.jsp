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
			url:"${pageContext.request.contextPath}/riskm/FM-RISKM007_LIST.do",
			postData : formData,
			datatype : "json",
			mtype : "post",
			colNames:['key','진단유형','진단코드','진단구분','항목','중요도','출처','정렬순서','사용여부'],
			colModel:[
					  {name:'urvRskKey',	index:'urvRskKey',									hidden:true},
			          {name:'urvRskTyp',	index:'urvRskTyp',		width: 100,	align:'center',	hidden:true},
			          {name:'urvVlbCod',	index:'urvVlbCod',		width: 80,	align:'center',	hidden:false},
			          {name:'urvRskDiv',	index:'urvRskDiv',		width: 150,	align:'left',	hidden:false},
			          {name:'urvRskItm',	index:'urvRskItm',		width: 460,	align:'left',	hidden:false},
			          {name:'urvRskImpNam',	index:'urvRskImpNam',	width: 60,	align:'center',	hidden:false},
			          {name:'urvRskSrc',	index:'urvRskSrc',		width: 80,	align:'center',	hidden:false},
			          {name:'urvRskOrd',	index:'urvRskOrd',		width: 60,	align:'center',	hidden:false},
			          {name:'urvUseYn',		index:'urvUseYn',		width: 60,	align:'center',	hidden:false}
			],
			height: 350,		// 전체 grid 화면의 세로 사이즈 지정.
			pager : '#pager',
			rowNum : '0',
// 			rowList : [10, 20, 50],
			rownumbers : true,	// 각 row의 맨 앞줄에 각 row의 번호가 자동적으로 부여된다.
			sortable : true,	// 초기 sort 조건과 관련되며 ture이면 초기 데이터가 정렬되서 보인다.
			sortname : 'urvRskKey',	// 초기 디이터 정렬시 정렬조건, 서버에 정렬  조건으로 전송된다.
			sortorder : 'desc',	// 순방향, 역방향 정렬(desc, asc)
			viewrecords : false,	// 오른쪽 하단에 총 몇개중 몇번째꺼 표시
			loadonce : false,	// reload 여부이며, true로 설정시 한번만 데이터를 받아오고 그 다음부터는 데이터를 받아오지 않는다.
			multiselect: false,	// select box 적용
			loadtext : 'loading..',	// 서버연동시 loading 중이라는 표시가 뜨는데 그곳의 문자열 지정.
			jsonReader:{
				repeatitems : true,
				root : function (obj) {return obj.result;},
			},
			onSelectRow: function(id){
				var urvRskKey = $("#gridTb").getRowData(id).urvRskKey;
				editVblInfo(urvRskKey);
			},
			ondblClickRow: function(id){
				var urvRskKey = $("#gridTb").getRowData(id).urvRskKey;
				//editAssetInfo(urvRskKey);
			},
			loadComplete: function(xhr) {
			},
			loadError: function(xhr,st,err) {
				//alert(err);
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
			datatype : "json"
		}).trigger("reloadGrid");
	}
	function editVblInfo(urvRskKey){
		window.open("","vlbPop","width=600, height=400, menubar=no, location=no, status=no,scrollbars=yes");
		$("#urvRskKey").val(urvRskKey);
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
	            <ul class="detail">
	            	<%-- <li>
	            		<span class="title"><label for="usoCocNam">취약점 유형</label></span>
						<select id="vlbValue" name="vlbValue" class="selectBox" onchange="searchListB();">
							<option value="">전체</option>
							<c:forEach var="vlbCod" items="${vlbList}" varStatus="status">
								<option value="${vlbCod.code}" ><c:out value="${vlbCod.name}" /></option>
							</c:forEach>
						</select>
					<!-- <button onclick="vlbType(); return false;"><span class="icoAdd"></span>취약점 유형 관리</button> -->
					</li>--%>
					<li class="last" style="width: 100%;">
						<span class="title"><label for="usoCocNam">취약점 명</label></span>
						<input type="text" id="urvRskItm" name="urvRskItm" class="inputText" title="취약점 진단명 입력" placeholder="취약점 진단명을 입력하세요."/>
					</li>
	            </ul>
	            <button onclick="searchListB(); return false;" class="btnSearch defaltBtn">조건으로 검색</button>
	        </fieldset>
	    </div>
	</div>
	<div class="contents">
		<div class="topBtnArea">
			<ul class="btnList">
				<li>
					<button onclick="editVblInfo(''); return false;"><span class="icoAdd"></span>추가</button>
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
<form id="popForm" name="popForm" action="FM-RISKM007_popup.do" method="post" target="vlbPop">
<input type="hidden" id="urvRskKey" name="urvRskKey"/>
</form>
</body>
</html>