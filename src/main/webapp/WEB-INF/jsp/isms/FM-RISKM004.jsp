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
			url:"${pageContext.request.contextPath}/riskm/FM-RISKM004_LIST.do",
			postData : formData,
			datatype : "json",
			mtype : "post",
			colNames:['key','자산유형','우려사항코드','우려사항구분','우려사항명','위험등급','발생가능성'],
			colModel:[
					  {name:'usoCocKey',	index:'usoCocKey',				align:'center',	hidden:true},
			          {name:'usoAssCat',	index:'usoAssCat',	width: 105,	align:'center',	hidden:false},
			          {name:'usoCocCod',	index:'usoCocCod',	width: 100,	align:'center',	hidden:false},
			          {name:'usoGbnNam',	index:'usoGbnNam',	width: 200,	align:'left',	hidden:false},
			          {name:'usoCocNam',	index:'usoCocNam',	width: 350,	align:'left',	hidden:false},
			          {name:'usoRskTxt',	index:'usoRskTxt',	width: 100,	align:'center',	hidden:false},
			          {name:'usoRskPnt',	index:'usoRskPnt',	width: 100,	align:'center',	hidden:false}
			],
			height: 350,		// 전체 grid 화면의 세로 사이즈 지정.
			pager : '#pager',
			rowNum : '0',
// 			rowList : [10, 20, 50],
			rownumbers : true,	// 각 row의 맨 앞줄에 각 row의 번호가 자동적으로 부여된다.
			sortable : true,	// 초기 sort 조건과 관련되며 ture이면 초기 데이터가 정렬되서 보인다.
			sortname : 'usoCocKey',	// 초기 디이터 정렬시 정렬조건, 서버에 정렬  조건으로 전송된다.
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
				var usoCocKey = $("#gridTb").getRowData(id).usoCocKey;
				editAssetInfo(usoCocKey);
			},
			ondblClickRow: function(id){
				var usoCocKey = $("#gridTb").getRowData(id).usoCocKey;
				editAssetInfo(usoCocKey);
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
			datatype : "json",
			ondblClickRow: function(id){
				var assetKey = $("#gridTb").getRowData(id).uar_ass_key;
				editAssetInfo(assetKey);
			}
		}).trigger("reloadGrid");
	}

	function editAssetInfo(usoCocKey){
		window.open("","cocPop","width=600, height=450, menubar=no, location=no, status=no,scrollbars=yes");
		$("#usoCocKey").val(usoCocKey);
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
					<li>
						<span class="title"><label for="usoAssCat">자산유형</label></span>
						<select id="usoAssCat" name="usoAssCat" class="selectBox">
							<option value="">전체</option>
							<c:forEach var="assCat" items="${assCatList}" varStatus="status">
								<option value="${assCat.key}" ><c:out value="${assCat.name}" /></option>
							</c:forEach>
						</select>
					</li>
					<li>
						<span class="title"><label for="usoRskGrd">위험등급</label></span>
						<select id="usoRskGrd" name="usoRskGrd" class="selectBox">
							<option value="">전체</option>
							<option value="U">상</option>
							<option value="M">중</option>
							<option value="D">하</option>
						</select>
					</li>
					<li>
						<span class="title"><label for="usoRskPnt">발생가능성</label></span>
						<select id="usoRskPnt" name="usoRskPnt" class="selectBox">
							<option value="">전체</option>
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
						</select>
					</li>
					<li>
						<span class="title"><label for="usoCocCod">우려사항코드</label></span>
						<input type="text" id="usoCocCod" name="usoCocCod" class="inputText wdShort" title="우려사항코드 입력" placeholder="우려사항코드 입력"/>
					</li>
					<li class="last" style="width: 100%;">
						<span class="title"><label for="usoCocNam">우려사항명</label></span>
						<input type="text" id="usoCocNam" name="usoCocNam" class="inputText" title="우려사항명 입력" placeholder="우려사항명을 입력하세요."/>
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
					<button onclick="editAssetInfo(''); return false;"><span class="icoAdd"></span>추가</button>
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
<form id="popForm" name="popForm" action="FM-RISKM004_popup.do" method="post" target="cocPop">
<input type="hidden" id="usoCocKey" name="usoCocKey"/>
</form>
</body>
</html>