<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
<%@ include file="/WEB-INF/include/contents.jsp"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<script type="text/javascript">
	$(document).ready(function() {
		searchList();
		getMngList();
	});

	function searchList(){
		$("#gridTb").jqGrid({
			url:"${pageContext.request.contextPath}/inspt/FM-INSPT002_list.do",
			postData : $("#searchForm").serialize(),
			datatype : "json",
			mtype : "post",
			colNames:['key','컴플라이언스','관련조항','결함명','결함등급','결함처리','결함등록일','결함등록자'],
			colModel:[
					  {name:'ufmFltKey',	index:'ufmFltKey',								hidden:true},
			          {name:'ufmCtrNam',	index:'ufmCtrNam',	width: 120,	align:'center',	hidden:false},
			          {name:'ufmCtrDes',	index:'ufmCtrDes',	width: 200,	align:'center',	hidden:false},
			          {name:'ufmFltNam',	index:'ufmFltNam',	width: 287,	align:'left',	hidden:false},
			          {name:'ufmFltLvl',	index:'ufmFltLvl',	width: 80,	align:'center',	hidden:false},
			          {name:'ufmRstSta',	index:'ufmRstSta',	width: 80,	align:'center',	hidden:false},
			          {name:'ufmRgtMdh',	index:'ufmFltMdh',	width: 90,	align:'center',	hidden:false},
			          {name:'ufmRgtNam',	index:'ufmRgtNam',	width: 90,	align:'center',	hidden:false}
			],
			height: 293,		// 전체 grid 화면의 세로 사이즈 지정.
			pager : '#pager',
			rowNum : '300',
//	 		rowList : [10, 20, 50],
			rownumbers : true,	// 각 row의 맨 앞줄에 각 row의 번호가 자동적으로 부여된다.
			sortable : true,	// 초기 sort 조건과 관련되며 ture이면 초기 데이터가 정렬되서 보인다.
			sortname : 'ufmFltKey',	// 초기 디이터 정렬시 정렬조건, 서버에 정렬  조건으로 전송된다.
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
				var fltKey = $("#gridTb").getRowData(id).ufmFltKey;
				reloadMngList(fltKey);
			},
			ondblClickRow: function(id){
				editFlawPop('U');
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

	function searchListB(){
		//보안담담자 목록 초기화
		reloadMngList("");
		$("#gridTb").clearGridData();	// 이전 데이터 삭제
		$("#gridTb").setGridParam({
			postData : $("#searchForm").serialize(),
			datatype : "json"
		}).trigger("reloadGrid");
		if($('select[name=ufmRstSta]').val() == 'Y'){
			$('.cReport').show();
			$('.dReport').show();
			$('.iReport').hide();
		}else{
			$('.cReport').hide();
			$('.dReport').hide();
			$('.iReport').show();
		}
	}

	function reloadUserList(fltKey){
		$("#fltKey").val(fltKey);
		$("#userGgrid").clearGridData();	// 이전 데이터 삭제
		$("#userGgrid").setGridParam({
			postData : $("#searchForm").serialize(),
		}).trigger("reloadGrid");
	}

	function getMngList(fltKey){
		$("#fltKey").val(fltKey);
		$("#mngGgrid").jqGrid({
			url:"${pageContext.request.contextPath}/inspt/FM-INSPT002_mngList.do",
			postData : $("#searchForm").serialize(),
			datatype : "json",
			mtype : "post",
			colNames:['key','서비스','부서','보완담당자','보완증적파일','보완등록일자','보완조치결과',
			          '보완승인자','자료처리상태'],
			colModel:[
					  {name:'ufpMapKey',	index:'ufpMapKey',								hidden:true},
			          {name:'ufpSvcNam',	index:'ufpSvcNam',	width: 120,	align:'center',	hidden:false},
			          {name:'ufpDepNam',	index:'ufpDepNam',	width: 140,	align:'center',	hidden:false},
			          {name:'ufpWrkNam',	index:'ufpWrkNam',	width: 115,	align:'center',	hidden:false},
			          {name:'ufpImpYn',		index:'ufpImpYn',	width: 120,	align:'center',	hidden:false},
			          {name:'ufpImpMdh',	index:'ufpImpMdh',	width: 100,	align:'center',	hidden:false},
			          {name:'ufpCofYn',		index:'ufpCofYn',	width: 117,	align:'center',	hidden:false},
			          {name:'ufpCofNam',	index:'ufpCofNam',	width: 115,	align:'center',	hidden:false},
			          {name:'ufpStaNam',	index:'ufpStaNam',	width: 120,	align:'center',	hidden:false}
			],
			height: 200,		// 전체 grid 화면의 세로 사이즈 지정.
			pager : '#mngPager',
			rowNum : '300',
// 			rowList : [10, 20, 50],
			rownumbers : true,	// 각 row의 맨 앞줄에 각 row의 번호가 자동적으로 부여된다.
			sortable : true,	// 초기 sort 조건과 관련되며 ture이면 초기 데이터가 정렬되서 보인다.
			sortname : 'ufpMapKey',	// 초기 디이터 정렬시 정렬조건, 서버에 정렬  조건으로 전송된다.
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
				editFlawMngPop("U");
			},
			ondblClickRow: function(id){
			},
			loadError: function(xhr,st,err) {
			},
		});
		// jqgrid 페이징바
		$("#mngGgrid").jqGrid('navGrid','#mngPager'
			,{ excel:false,add:false,edit:false,view:false,del:false,search:false,refresh:false}
		);
		// jqgrid 전체 넓이 지정, 가로 스크롤 생성
		$("#mngGgrid").setGridWidth(1018, false);
	}

	// get reload grid(위험내역)
	function reloadMngList(fltKey){
		$("#ufmFltKey").val(fltKey);
		$("#mngGgrid").clearGridData();	// 이전 데이터 삭제
		$("#mngGgrid").setGridParam({
			postData : $("#searchForm").serialize(),
		}).trigger("reloadGrid");
	}

	function editFlawPop(type){
		var fltKey = "";
		if(type == 'U') {
			var id = $("#gridTb").jqGrid('getGridParam', "selrow" );
			if(id == null){
				alert("결함내역을 선택하여 주세요.")
				return;
			}
			fltKey = $("#gridTb").getRowData(id).ufmFltKey;
		}

		$("#popUfmFltKey").val(fltKey);
		window.open("","fltPopup","width=600, height=700, menubar=no, location=no, status=no,scrollbars=yes");
		document.fltForm.submit();
	}


	function editFlawMngPop(type){
		var id = $("#gridTb").jqGrid('getGridParam', "selrow" );
		if(id == null){
			alert("결함내역을 선택하여 주세요.")
			return;
		}
		fltKey = $("#gridTb").getRowData(id).ufmFltKey;

		var mngKey = "";
		if(type == 'U') {
			var id = $("#mngGgrid").jqGrid('getGridParam', "selrow" );
			mngKey = $("#mngGgrid").getRowData(id).ufpMapKey;
		}

		$("#mngPopUfmFltKey").val(fltKey);
		$("#mngPopUfpMapKey").val(mngKey);
		window.open("","mngPopup","width=600, height=700, menubar=no, location=no, status=no,scrollbars=yes");
		document.mngForm.submit();
	}

	function reportDown(cod){
		if(cod == 'Y'){
			window.open("","comDat","width=600, height=800, menubar=no, location=no, status=no,scrollbars=yes");
			$('form[name=searchForm]').attr('target','comDat').attr('action','${pageContext.request.contextPath}/inspt/FM-INSPT002_C_popup.do').submit();
		}else if(cod == 'D'){
			if($('#mngGgrid').find('tr').length < 2){
				alert('담당자가 없습니다.');
				return false;
			}
			window.open("","reportDown","width=1024, height=720, menubar=no, location=no, status=no,scrollbars=yes");
			$('form[name=searchForm]').attr('target','reportDown').attr('action','${pageContext.request.contextPath}/report/FM-INSPT002_D.do').submit();
		}else{
			window.open("","comDat","width=600, height=800, menubar=no, location=no, status=no,scrollbars=yes");
			$('form[name=searchForm]').attr('target','comDat').attr('action','${pageContext.request.contextPath}/inspt/FM-INSPT002_I_popup.do?ufmRstSta=N').submit();
		}
		$('form[name=searchForm]').attr('target','');
 	}

</script>
</head>
<body>
	<c:import url="/titlebar.do" />
	<div class="search">
		<form id="searchForm" method="post" name="searchForm">
		<input type="hidden" id="ufmFltKey" name="ufmFltKey" />
		    <div class="defalt">
		        <fieldset class="searchForm">
		            <legend>기본검색</legend>
		            <ul class="detail newField">
                        <li>
		                    <span class="title"><label for="standard">컴플라이언스</label></span>
		                    <sb:select name="standard" type="A" code="STND" />
		                </li>
                        <li>
                            <span class="title"><label for="ufmCtrDes">관련조항</label></span>
                            <input id="ufmCtrDes" name="ufmCtrDes" class="inputText wdShort" type="text"  title="관련조항 입력" placeholder="관련조항 입력"/>
                        </li>
                        <li>
                            <span class="title"><label for="ufmFltLvl">결함등급</label></span>
                            <select id="ufmFltLvl" name="ufmFltLvl" class="selectBox">
                            	<option value="">전체</option>
                            	<option value="1">결함</option>
                            	<option value="2">중결함</option>
                            </select>
                        </li>
                        <li>
		                    <span class="title"><label for="service">서비스</label></span>
		                    <sb:select name="service" type="S"/>
		                </li>
                        <li>
		                    <span class="title"><label for="usrNam">보완담당자</label></span>
		                    <input id="usrNam" name="usrNam" class="inputText wdShort" type="text"  title="보완담당자명" placeholder="보완담당자명"
		                    <c:if test="${authKey eq 'P'}" >disabled="disabled"</c:if>/>
		                </li>
                        <li>
							<span class="title"><label for="ufmRstSta">결함처리</label></span>
							<sb:select name="ufmRstSta" type="A" code="FLTRST" />
						</li>
						<li>
							<span class="title"><label for="ufpStaCod">보완처리</label></span>
							<select id="ufpStaCod" name="ufpStaCod" class="selectBox">
								<option value="">전체</option>
								<option value="1">결함등록</option>
								<option value="2">승인완료</option>
								<option value="3">조치완료</option>
							</select>
						</li>
                        <li class="last">
                            <span class="title"><label for="ufmFltNam">결함명</label></span>
                            <input id="ufmFltNam" name="ufmFltNam" class="inputText" type="text"  title="결함명 입력" placeholder="결함명 입력을 입력하세요"/>
                        </li>
		                <li class="btnArea">
		                 	<button type="button" onclick="searchListB();" class="btnSearch">조건으로 검색</button>
		                 </li>
		            </ul>
		        </fieldset>
		    </div>
	    </form>
	</div>
	<div class="contents">
		<div class="title">결함 내역</div>
		<div class="talbeArea">
			<c:if test="${authKey ne 'P'}" >
			<div class="topBtnArea">
				<ul class="btnList">
					<li class="iReport">
						<button type="button" onclick="reportDown('N');"><span class="icoDown"></span>보완조치요약서</button>
					</li>
					<li class="cReport" style="display:none;">
						<button type="button" onclick="reportDown('Y');"><span class="icoDown"></span>보완조치완료확인서</button>
					</li>
					<li>
						<button type="button" onclick="editFlawPop('I');"><span class="icoAdd"></span>등록</button>
					</li>
					<!-- <li>
						<button type="button" onclick="editFlawPop('U');"><span class="icoAdd"></span>수정</button>
					</li> -->
				</ul>
			</div>
			</c:if>
			<table id="gridTb">
			</table>
			<div id="pager"></div>
		</div>
	</div>

	<div class="contents">
		<div class="listArea">
			<div class="title">보완 담당자</div>
			<div class="talbeArea">
				<table id="mngGgrid">
				</table>
				<div id="mngPager"></div>
				<div class="topBtnArea">
					<ul class="btnList">
						<li class="dReport" style="display:none;">
							<button type="button" onclick="reportDown('D');"><span class="icoDown"></span>보완조치내역시</button>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>

	<form id="fltForm" name="fltForm" action="FM-INSPT002_popup.do" method="post" target="fltPopup">
		<input type="hidden" id="popUfmFltKey" name="ufmFltKey"/>
	</form>

	<form id="mngForm" name="mngForm" action="FM-INSPT002_mng_popup.do" method="post" target="mngPopup">
		<input type="hidden" id="mngPopUfmFltKey" name="ufmFltKey"/>
		<input type="hidden" id="mngPopUfpMapKey" name="ufpMapKey"/>
	</form>
</body>
</html>