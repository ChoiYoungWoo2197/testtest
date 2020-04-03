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
		$("#assGbn").chained("#assCat");
		//getWorkerList();
		stOrgList();
		doaList();

		// 2017-07-05, 위험도관련
		if ($("#doaGrd").val() || $("#searchDoaGrd").val() == "A") {
			$("#doa").val("1");
		}
		$("#doa").change(function() {
			$("#doaGrd").val("");
		});
		$("#doaGrd").change(function() {
			$("#doa").val("1");
		});

		searchList();

		$("#btnBatch").click(function() {
			var uagGrpCod = $("#grpKey").val();
			var uagGrpNam = $("#gridTb").getRowData($("#gridTb").jqGrid('getGridParam','selrow')).uarGrpNam;
			var doa = $("#doa").val();
			if (!uagGrpCod) {
				alert("위험그룹를 선택해주세요.");
				return false;
			}
			if (!$("#rskDetailGgrid").text()) {
				alert("위험관리 내역이 존재하지 않습니다.");
				return false;
			}
			if (doa <= 0) {
				alert("위험도를 선택해주세요.");
				return false;
			}

			$("#uagGrpCod2").val(uagGrpCod);
			$("#uagGrpNam2").val(uagGrpNam);
			$("#doa2").val(doa);

			window.open("","batchPopup","width=540, height=300, menubar=no, location=no, status=no, scrollbars=no");
			$("#batchPopForm").submit();
		});
	});

	/*DOA 조회 */
	function doaList(){
		$.ajax({
			url		: "${pageContext.request.contextPath}/code/getDoaList.do",
			async       : false, // important!
			type		: "post",
			data		: {},
			dataType	: "json",
			success	: function(data){
				var str  = '<option value="0">전체</option>';
	        	for(var i = 0;i<data.result.length;i++){
	        		if('Y' == data.result[i].uccEtc){
	        			str += "	<option value='"+data.result[i].uccSndCod+"' selected='selected'>"+data.result[i].uccSndCod+"(DOA)</option>";
	        		}else{
						str += "	<option value='"+data.result[i].uccSndCod+"'>"+data.result[i].uccSndCod+"</option>";
	        		}
	        	}
	        	$('#doa').html(str);
			},
			error : function(){
			}
		});
	}

	function searchList(){
		$("#gridTb").jqGrid("GridUnload");	// 동적데이터 전송시 필수
		var formData = formSerialize($("#searchForm"));
		$("#gridTb").jqGrid({
			url:"${pageContext.request.contextPath}/riskm/FM-RISKM003_list.do",
			postData : formData,
			datatype : "json",
			mtype : "post",
			colNames:['부서','ser_cod','서비스','cat_cod','자산분류','자산건수','그룹코드','그룹명','자산중요도','담당자'],
			colModel:[
			          {name:'uarDepNam',	index:'uarDepNam',	width: 105,	align:'center',	hidden:false},
					  {name:'uarSvrCod',	index:'uarSvrCod',								hidden:true},
					  {name:'uarSvrNam',	index:'uarSvrNam',	width: 70,	align:'center',	hidden:false},
					  {name:'uarAssCat',	index:'uarAssCat',								hidden:true},
					  {name:'uarCatNam',	index:'uarCatNam',	width: 80,	align:'center',	hidden:false},
					  {name:'uarAssCnt',	index:'uarAssCnt',	width: 60,	align:'center',	hidden:false},
					  {name:'uarGrpCod',	index:'uarGrpCod',	width: 240,	align:'center',	hidden:false},
					  {name:'uarGrpNam',	index:'uarGrpNam',	width: 260,	align:'center',	hidden:false},
					  {name:'uarAssLvl',	index:'uarAssLvl',	width: 70,	align:'center',	hidden:false},
					  {name:'uarUseNam',	index:'uarUseNam',	width: 60,	align:'center',	hidden:false}
			],
			height: 200,		// 전체 grid 화면의 세로 사이즈 지정.
			pager : '#pager',
			rowNum : '300',
// 			rowList : [10, 20, 50],
			rownumbers : true,	// 각 row의 맨 앞줄에 각 row의 번호가 자동적으로 부여된다.
			sortable : true,	// 초기 sort 조건과 관련되며 ture이면 초기 데이터가 정렬되서 보인다.
			sortname : 'uarGrpCod',	// 초기 디이터 정렬시 정렬조건, 서버에 정렬  조건으로 전송된다.
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
				var grpKey = $("#gridTb").getRowData(id).uarGrpCod;
				getRskDetailList(grpKey);
			}
		}).trigger("reloadGrid");

		// jqgrid 페이징바
		$("#gridTb").jqGrid('navGrid','#pager'
			,{ excel:false,add:false,edit:false,view:false,del:false,search:false,refresh:false}
		);
		// jqgrid 전체 넓이 지정, 가로 스크롤 생성
		$("#gridTb").setGridWidth(1018, false);

		getRskDetailList();
	}

	// 위험내역 리스트
	function getRskDetailList(grpKey){
		$("#rskDetailGgrid").jqGrid("GridUnload");	// 동적데이터 전송시 필수
		$("#grpKey").val(grpKey);
		var formData = formSerialize($("#searchForm"));
		$("#rskDetailGgrid").jqGrid({
			url:"${pageContext.request.contextPath}/riskm/FM-RISKM003_rskList.do",
			postData : formData,
			datatype : "json",
			mtype : "post",
			colNames:['key','자산그룹코드','자산그룹명','진단구분','진단항목','위험도','진단상태','진단상태','중요도코드','중요도','우려도','처리상태'],
			colModel:[
					  {name:'ursRskKey',	index:'ursRskKey',								hidden:true},
			          {name:'uagGrpCod',	index:'uagGrpCod',								hidden:true},
			          {name:'uagGrpNam',	index:'uagGrpNam',	width: 220,	align:'center',	hidden:false},
			          {name:'urvRskDiv',	index:'urvRskDiv',	width: 122,	align:'left',	hidden:false},
			          {name:'urvRskItm',	index:'urvRskItm',	width: 255,	align:'left',	hidden:false},
			          {name:'ursRskRst',	index:'ursRskRst',	width: 55,	align:'center',	hidden:false},
			          {name:'ursRskChk',	index:'ursRskChk',								hidden:true},
			          {name:'ursChkNam',	index:'ursChkNam',	width: 60,	align:'center',	hidden:false},
			          {name:'usoRskGrd',	index:'usoRskGrd',								hidden:true},
			          {name:'usoGrdNam',	index:'usoGrdNam',	width: 60,	align:'center',	hidden:false},
			          {name:'usoRskPnt',	index:'usoRskPnt',	width: 60,	align:'center',	hidden:false},
			          {name:'ursStaNam',	index:'ursStaNam',	width: 112,	align:'center',	hidden:false}
			],
			height: 200,		// 전체 grid 화면의 세로 사이즈 지정.
			pager : '#detailPager',
			rowNum : '300',
// 			rowList : [10, 20, 50],
			rownumbers : true,	// 각 row의 맨 앞줄에 각 row의 번호가 자동적으로 부여된다.
			sortable : true,	// 초기 sort 조건과 관련되며 ture이면 초기 데이터가 정렬되서 보인다.
			sortname : 'urgRskKey',	// 초기 디이터 정렬시 정렬조건, 서버에 정렬  조건으로 전송된다.
			sortorder : 'asc',	// 순방향, 역방향 정렬(desc, asc)
			viewrecords : true,	// 오른쪽 하단에 총 몇개중 몇번째꺼 표시
			loadonce : false,	// reload 여부이며, true로 설정시 한번만 데이터를 받아오고 그 다음부터는 데이터를 받아오지 않는다.
			multiselect: false,	// select box 적용
			loadtext : 'loading..',	// 서버연동시 loading 중이라는 표시가 뜨는데 그곳의 문자열 지정.
			jsonReader:{
				repeatitems : true,
				root : function (obj) {return obj.result;},
			},
			onSelectRow: function(id){
				var ursRskKey = $("#rskDetailGgrid").getRowData(id).ursRskKey;
				editRskPop(ursRskKey);
			}
		}).trigger("reloadGrid");

		// jqgrid 페이징바
		$("#rskDetailGgrid").jqGrid('navGrid','#detailPager'
			,{ excel:false,add:false,edit:false,view:false,del:false,search:false,refresh:false}
		);
		// jqgrid 전체 넓이 지정, 가로 스크롤 생성
		$("#rskDetailGgrid").setGridWidth(1018, false);
	}

	// 위험내역 등록 및 수정 팝업창
	function editRskPop(key){
		$("#ursRskKey").val(key);
		window.open("","rskInfoPopup","width=730, height=800, menubar=no, location=no, status=no,scrollbars=yes");
		document.rskInfoForm.submit();
	}

	function getWorkerList() {
		var gpOrg = $("#gpOrg").val();
		if(gpOrg != ""){
			$.ajax({
				url			: "${pageContext.request.contextPath}/code/getWorkerList.do",
				type		: "post",
				data		: {code:gpOrg},
				dataType	: "json",
				async		: false,
				success		: function(data){
					var	inData = getWorkerInTag(data);
					$("#worker").html(inData);
				 },
				 error : function(){
					 alert('error');
				 }
			 });
		}
	}
/*
	function goPage() {
		location.href = "FM-RISKM003_M.do";
	}

	function excelUpload(){
		window.open("","excelPopup","width=450, height=300, menubar=no, location=no, status=no, scrollbars=auto");
		$("#downType").val("1");
		document.excelPopForm.submit();
	}
*/
	function reportDown(){
		window.open("","reportPopup","width=450, height=250, menubar=no, location=no, status=no, scrollbars=auto");
		document.reportPopForm.submit();
 	}
/*
	function reportDownT(){
		window.open("","reportDown","width=1024, height=720, menubar=no, location=no, status=no,scrollbars=yes");
		$('form[name=searchForm]').attr('target','reportDown').attr('action','${pageContext.request.contextPath}/report/FM-ASSET002_T.do').submit();
		$('form[name=searchForm]').attr('target','');
	}
*/
	function reloadList() {
		$("#rskDetailGgrid").trigger("reloadGrid");
	}
</script>
</head>
<body>
	<c:import url="/titlebar.do" />

	<div class="search">
		<form id="searchForm" method="post" name="searchForm">
		<input type="hidden" id="grpKey" name="grpKey" />
		<input type="hidden" name="rskYn" value="Y"/>
		<input type="hidden" name="smpYn" value="Y"/>
		<input type="hidden" id="searchDoaGrd" value="<c:out value="${doaGrd}"/>" />
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
		                    <sb:select name="service" type="S" forbidden="true"/>
		            	</li>
		            	<li>
		            		<span class="title"><label for="service">자산분류</label></span>
			            	<select id="assCat" name="assCat" class="selectBox" title="항목 선택">
				            	<option value="" title="">전체</option>
								<c:forEach var="assCat" items="${assCatList}" varStatus="status">
									<option value="${assCat.key}" <c:if test="${cat eq assCat.key}">selected</c:if>><c:out value="${assCat.name}" /></option>
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
		            		<span class="title"><label for="rskSta">처리상태</label></span>
		                    <sb:select name="rskSta" type="A" code="RSLSTATUS"/>
		            	</li>
		            	<li>
			                <span class="title"><label for="doaGrd">위험도등급</label></span>
		                    <sb:select name="doaGrd" type="A" code="DOAGRD" allText="전체" value="${doaGrd}" />
						</li>
		                <li>
			                <span class="title"><label for="doa">위험도</label></span>
							<select id="doa" name="doa" class="selectBox"  title="doa 선택">
                            </select>
						</li>
						<li>
		                    <span class="title"><label for="groupName">자산그룹명</label></span>
		                    <input id="groupName" name="groupName" class="inputText wdLong" type="text"  title="자산그룹명 입력" placeholder="자산그룹명 입력"/>
		                </li>
		                <li class="btnArea">
		                 	<button type="button" onclick="searchList()"  class="btnSearch">조건으로 검색</button>
						</li>
		            </ul>
		        </fieldset>
		    </div>
	    </form>
	</div>
	<div class="contents">
		<div class="title">위험그룹 내역</div>
		<div class="talbeArea">
			<div class="topBtnArea">
				<ul class="btnList">
					<li><button class="iReport" type="button" onclick="reportDown();"><span class="icoDown"></span>위험조치계획서</button></li>
				</ul>
			</div>
			<table id="gridTb">
			</table>
			<div id="pager"></div>
		</div>
	</div>
	<div class="contents">
		<div class="listArea">
			<div class="title">위험관리 내역</div>
			<div class="talbeArea">
          		<div class="topBtnArea">
					<ul class="btnList">
					<c:if test="${authGbn eq 'A'}">
						<li><button type="button" id="btnBatch"><span class="icoPer"></span>대책담당자 일괄 할당</button></li>
					</c:if>
					</ul>
				</div>
				<table id="rskDetailGgrid">
				</table>
				<div id="detailPager"></div>
			</div>
		</div>
	</div>
	<!-- 위험내역 팝업폼 -->
	<form id="rskInfoForm" name="rskInfoForm" action="FM-RISKM003_popup.do" method="post" target="rskInfoPopup">
		<input type="hidden" id="ursRskKey" name="ursRskKey"/>
	</form>
	<form id="reportPopForm" name="reportPopForm" action="FM-RISKM003_report_popup.do" method="post" target="reportPopup">
	</form>
	<!-- form id="excelPopForm" name="excelPopForm" action="FM-RISKM003_excel_popup.do" method="post" target="excelPopup">
		<input type="hidden" id="excelSvcCod" name="excelSvcCod"/>
		<input type="hidden" id="excelDepCod" name="excelDepCod"/>
		<input type="hidden" id="downType" name="downType"/>
	</form -->
	<form id="batchPopForm" name="batchPopForm" action="FM-RISKM003_batch_popup.do" method="post" target="batchPopup">
		<input type="hidden" id="uagGrpCod2" name="uagGrpCod"/>
		<input type="hidden" id="uagGrpNam2" name="uagGrpNam"/>
		<input type="hidden" id="doa2" name="doa"/>
	</form>
</body>
</html>