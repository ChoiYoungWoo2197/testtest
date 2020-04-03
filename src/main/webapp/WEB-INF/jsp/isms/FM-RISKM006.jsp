<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
<%@ include file="/WEB-INF/include/contents.jsp"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<script type="text/javascript" src="/common/js/jquery.chained.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		stOrgList();
		$("#assGbn").chained("#assCat2");

		$("#list4Tab").find("li").first().addClass("sel");
		searchList(6);

		$("#list4Tab").find("li").click(function() {
			$(this).parent().find("li").removeClass("sel");
			$(this).addClass("sel");
			searchList($(this).attr("title"));
		});
	});

	function goPage() {
		location.href = "FM-RISKM006_M.do";
	}

	function excelUpload(){
		window.open("","excelPopup","width=450, height=360, menubar=no, location=no, status=no, scrollbars=auto");
		$("#upType").val("A");
		document.excelPopForm.submit();
	}

	function excelDown() {
		var title = $("#assCat2 :selected").text();
		if (confirm("선택된 자산분류 탭인 [" + title + "]의 취약점 결과가 다운로드 됩니다.")) {
			$("#assCatNam").val(title);
			var params = formSerializeObject($("#searchForm"));
			openPostUrl("${pageContext.request.contextPath}/excel/FM-RISKM006_LIST_DOWN.do", params);
		}
	}

	/*리스트 조회*/
	function searchList(assCat){
		$("#gridTb").jqGrid("GridUnload");

		if (assCat) {
			$("#assCat").val(assCat);
			$("#assCat2").val(assCat).change();
		}
		var formData = formSerializeObject($("#searchForm"));

		$("#gridTb").jqGrid({
			url:"${pageContext.request.contextPath}/riskm/FM-RISKM006_list.do",
			postData : formData,
			datatype : "json",
			mtype : "post",
			colNames:['자산코드','그룹코드','그룹명','조직(팀)','자산코드','자산유형명','진단여부','호스트명','IP','OS','위치','용도','담당자','자산등급','비고'],
			colModel:[
					  {name:'uarAssKey',	index:'uarAssKey',									hidden:true},
					  {name:'uarGrpCod',	index:'uarGrpCod',									hidden:true},
					  {name:'uagGrpNam',	index:'uagGrpNam',		width: 250,	align:'center',	hidden:false},
			          {name:'uarDepNam',	index:'uarDepNam',		width: 120,	align:'center',	hidden:false},
			          {name:'uarAssCod',	index:'uarAssCod',		width: 150,	align:'center',	hidden:false},
			          {name:'uarAssNam',	index:'uarAssNam',		width: 120,	align:'center',	hidden:false},
			          {name:'rstYn',	    index:'rstYn',			width: 60,	align:'center',	hidden:false},
					  {name:'uarHost',		index:'uarHost',		width: 100,	align:'center',	hidden:false},
					  {name:'uarIp',		index:'uarIp',			width: 100,	align:'center',	hidden:false},
					  {name:'uarOs',		index:'uarOs',			width: 100,	align:'center',	hidden:false},
					  {name:'uarAdmPos',	index:'uarAdmPos',		width: 100,	align:'center',	hidden:false},
					  {name:'uarDtlExp',	index:'uarDtlExp',		width: 100,	align:'center',	hidden:false},
					  {name:'uarUseNam',	index:'uarUseNam',		width: 100,	align:'center',	hidden:false},
					  {name:'uarAssLvl',	index:'uarAssLvl',		width: 80,	align:'center',	hidden:false},
					  {name:'rem',			index:'rem',										hidden:true}

			],
			height: 400,		// 전체 grid 화면의 세로 사이즈 지정.
			pager : '#pager',
			rowNum : 50,
// 			rowList : [10, 20, 50],
			rownumbers : true,	// 각 row의 맨 앞줄에 각 row의 번호가 자동적으로 부여된다.
			sortable : true,	// 초기 sort 조건과 관련되며 ture이면 초기 데이터가 정렬되서 보인다.
			sortname : 'uarGrpCod',	// 초기 디이터 정렬시 정렬조건, 서버에 정렬  조건으로 전송된다.
			sortorder : 'asc',	// 순방향, 역방향 정렬(desc, asc)
			viewrecords : true,	// 오른쪽 하단에 총 몇개중 몇번째꺼 표시
			loadonce : false,	// reload 여부이며, true로 설정시 한번만 데이터를 받아오고 그 다음부터는 데이터를 받아오지 않는다.
			multiselect: false,	// select box 적용
			loadtext : 'loading..',	// 서버연동시 loading 중이라는 표시가 뜨는데 그곳의 문자열 지정.
			jsonReader:{
				repeatitems : true,
				root : function (obj) {return obj.result},
				page: function (obj) {return obj.currentPageNo},
			    total: function (obj) {return obj.totalPage},
			    records: function (obj) {return obj.totalRecord}
			},
			onSelectRow: function(id){
				var uarAssCod = $("#gridTb").getRowData(id).uarAssCod;
				selectRowItem(uarAssCod);

			}
		}).trigger("reloadGrid");
		// jqgrid 페이징바
		$("#gridTb").jqGrid('navGrid','#pager'
			,{ excel:false,add:false,edit:false,view:false,del:false,search:false,refresh:false}
		);
		// jqgrid 전체 넓이 지정, 가로 스크롤 생성
		$("#gridTb").setGridWidth(1018, false);
	}

	function selectRowItem(uarAssCod){
		var win = window.open("","detailPopup","width=900, height=590, menubar=no, location=no, status=no, scrollbars=auto");
		$("#uarAssCod").val(uarAssCod);
		document.detailPopForm.submit();
		win.focus();
	}
</script>
</head>
<body>
	<c:import url="/titlebar.do" />
	<div class="search">
		<form id="searchForm" method="post" name="searchForm">
			<input type="hidden" id="assCat" name="assCat" />
			<input type="hidden" id="grpKey" name="grpKey" />
			<input type="hidden" id="assCatNam" name="assCatNam" />
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
		            	<li style="display: none;">
			            	<span class="title"><label for="assCat2">자산분류</label></span>
			            	<select id="assCat2" name="assCat2" class="selectBox" title="항목 선택">
				            	<option value="" title="">전체</option>
								<c:forEach var="assCat" items="${assCatList}" varStatus="status">
									<option value="${assCat.key}"><c:out value="${assCat.name}" /></option>
								</c:forEach>
							</select>
			           	</li>
			           	<li>
			            	<span class="title"><label for="assGbn">자산유형</label></span>
			            	<select id="assGbn" name="assGbn" class="selectBox" title="항목 선택">
				            	<option value="" title="">전체</option>
								<c:forEach var="assGbn" items="${assGbnList}" varStatus="status">
									<option value="${assGbn.code}" class="${assGbn.categoryKey}"><c:out value="${assGbn.name}" /></option>
								</c:forEach>
							</select>
			           	</li>
						<li>
		            		<span class="title"><label for="service">자산등급</label></span>
		                    <select id="assLvl" name="assLvl" class="selectBox"  title="항목 선택">
								<option value="">전체</option>
								<option value="L(1)">1등급</option>
								<option value="M(2)">2등급</option>
								<option value="H(3)">3등급</option>
                            </select>
                        </li>
                        <li>
		                    <span class="title"><label for="operMngNm">운영담당자</label></span>
		                    <input id="operMngNm" name="operMngNm" class="inputText wdShort" type="text"  title="운영담당자명" placeholder="운영담당자명"/>
		                </li>
		                <li>
		                    <span class="title"><label for="groupName">진단여부</label></span>
		                    <select id="rstYn" name="rstYn" class="selectBox"  title="항목 선택">
								<option value="">전체</option>
								<option value="Y" selected>예</option>
								<option value="N">아니오</option>
                            </select>
		                </li>
		                <li>
		                    <span class="title"><label for="groupName">자산명</label></span>
		                    <input id="assNam" name="assNam" class="inputText wdLong" type="text"  title="자산명 입력" placeholder="자산명, 호스트, IP, 자산코드를 입력하세요.">
		                </li>
						<li>
		                    <span class="title"><label for="groupName">자산그룹명</label></span>
		                    <input id="groupName" name="groupName" class="inputText wdLong" type="text"  title="자산그룹명 입력" placeholder="자산그룹명 입력"/>
		                </li>
		                <li class="btnArea">
		                 	<button onclick="searchList(); return false;"  class="btnSearch">조건으로 검색</button>
						</li>
		            </ul>
		        </fieldset>
		    </div>
	    </form>
	</div>
	<div class="contents">
		<div class="topBtnArea">
			<ul class="btnList">
				<li>
					<button type="button" onclick="excelUpload();"><span class="icoSave"></span>취약점 결과 엑셀업로드</button>
				</li>
				<li>
					<button type="button" onclick="excelDown();"><span class="icoDown"></span>취약점 결과 다운로드</button>
				</li>
			</ul>
		</div>
		<div class="tabArea freeArea">
		    <ul id="list4Tab" class="listFreeTab">
				<c:forEach var="assCat" items="${assCatList}" varStatus="status">
					<li title="<c:out value="${assCat.key}" />"><a href="javascript:;"><c:out value="${assCat.name}" /></a></li>
				</c:forEach>
		    </ul>
		</div>
		<div style="height: 10px; clear: both"></div>
		<table id="gridTb">
		</table>
		<div id="pager"></div>
	</div>
	<form id="excelPopForm" name="excelPopForm" action="FM-RISKM006_excel_popup.do" method="post" target="excelPopup">
		<input type="hidden" id="excelSvcCod" name="excelSvcCod"/>
		<input type="hidden" id="excelDepCod" name="excelDepCod"/>
		<input type="hidden" id="upType" name="upType"/>
	</form>
	<form id="detailPopForm" name="detailPopForm" action="FM-RISKM006_detail_popup.do" method="post" target="detailPopup">
		<input type="hidden" id="uarAssCod" name="uarAssCod"/>
	</form>
	<form id="excelDownloadForm" name="excelDownloadForm" action="FM-RISKM006_excel_down_popup.do" method="post" target="excelDownPopup">
		<input type="hidden" id="downType" name="downType"/>
	</form>
</body>
</html>