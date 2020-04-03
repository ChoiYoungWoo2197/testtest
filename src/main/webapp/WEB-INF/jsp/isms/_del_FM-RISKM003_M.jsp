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
	});
	
	function searchList(){
		$("#gridTb").jqGrid({
			url:"${pageContext.request.contextPath}/riskm/FM-RISKM003_rskList.do",
			postData : $("#searchForm").serialize(),
			datatype : "json",
			mtype : "post",
			colNames:['key','자산유형','대분류','중분류','우려사항코드','우려사항','발생빈도','위협강도','위험도','자산가치',
			          '진단결과','위험처리','보호대책','대책승인','조치결과','결과승인','시나리오코드','시나리오내용'],
			colModel:[
					  {name:'urgRskKey',	index:'urgRskKey',								hidden:true},
			          {name:'usoAssCat',	index:'usoAssCat',	width: 80,	align:'center',	hidden:false},
			          {name:'usoFirCat',	index:'usoFirCat',	width: 80,	align:'center',	hidden:false},
			          {name:'usoSecCat',	index:'usoSecCat',	width: 80,	align:'center',	hidden:false},
			          {name:'usoCocCod',	index:'usoCocCod',	width: 80,	align:'center',	hidden:false},
			          {name:'usoCocNam',	index:'usoCocNam',	width: 100,	align:'left',	hidden:false},
			          {name:'usoFrqTxt',	index:'usoFrqTxt',	width: 100,	align:'center',	hidden:false},
			          {name:'usoCocTxt',	index:'usoCocTxt',	width: 100,	align:'center',	hidden:false},
			          {name:'urgRskPnt',	index:'urgRskPnt',	width: 100,	align:'center',	hidden:false},
			          {name:'urgAssVal',	index:'urgAssVal',	width: 100,	align:'center',	hidden:false},
			          {name:'urgRskChk',	index:'urgRskChk',	width: 100,	align:'center',	hidden:false},
			          {name:'urgRskPrc',	index:'urgRskPrc',	width: 100,	align:'center',	hidden:false},
			          {name:'urgMesDes',	index:'urgMesDes',	width: 150,	align:'left',	hidden:false},
			          {name:'urgMesYn',		index:'urgMesYn',	width: 80,	align:'center',	hidden:false},
			          {name:'urgRstDes',	index:'urgRstDes',	width: 150,	align:'left',	hidden:false},
			          {name:'urgRstYn',		index:'urgRstYn',	width: 80,	align:'center',	hidden:false},
			          {name:'usmSroCod',	index:'usmSroCod',	width: 80,	align:'center',	hidden:false},
			          {name:'usmSroDes',	index:'usmSroDes',	width: 150,	align:'center',	hidden:false}
			],
			height: 293,		// 전체 grid 화면의 세로 사이즈 지정.
			pager : '#pager',
			rowNum : '300',
//	 		rowList : [10, 20, 50],
			rownumbers : true,	// 각 row의 맨 앞줄에 각 row의 번호가 자동적으로 부여된다.
			sortable : true,	// 초기 sort 조건과 관련되며 ture이면 초기 데이터가 정렬되서 보인다.
			sortname : 'usoCocNam',	// 초기 디이터 정렬시 정렬조건, 서버에 정렬  조건으로 전송된다.
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
				editRskPop("U");
			},
			ondblClickRow: function(id){
			},
			loadError: function(xhr,st,err) {
				alert(err);
			},
		});
		// jqgrid 페이징바
		$("#gridTb").jqGrid('navGrid','#detailPager'
			,{ excel:false,add:false,edit:false,view:false,del:false,search:false,refresh:false} 
		);
		// jqgrid 전체 넓이 지정, 가로 스크롤 생성
		$("#gridTb").setGridWidth(738, false);
	}

	// get reload grid(위험내역)
	function searchListB(){
		$("#gridTb").clearGridData();	// 이전 데이터 삭제
		$("#gridTb").setGridParam({
			postData : $("#searchForm").serialize(),
		}).trigger("reloadGrid");
	}
	
	
	// 위험내역 등록 및 수정 팝업창
	function editRskPop(type){
		var rskKey = "";
		if(type == 'U') {
			var id = $("#gridTb").jqGrid('getGridParam', "selrow" );
			rskKey = $("#gridTb").getRowData(id).urgRskKey;
		}
		
		$("#popRskKey").val(rskKey);
		window.open("","rskInfoPopup","width=600, height=500, menubar=no, location=no, status=no,scrollbars=yes");
		document.rskInfoForm.submit();
	}
	
	function goPage() {
		location.href = "FM-RISKM003.do";
	}
	
	function excelUpload(){
		window.open("","excelPopup","width=400, height=300, menubar=no, location=no, status=no, scrollbars=auto");
		$("#downType").val("2");
		$("#excelSvcCod").val($("#service").val());
		$("#excelDepCod").val($("#dept").val());
		document.excelPopForm.submit();
	}
	
	function reportDown(){
		window.open("","reportDown","width=1024, height=720, menubar=no, location=no, status=no,scrollbars=yes");
		$('form[name=searchForm]').attr('target','reportDown').attr('action','${pageContext.request.contextPath}/report/FM-RISKM003.do').submit();	
		$('form[name=searchForm]').attr('target','');
 	}
	
	function reportDownI(){
		window.open("","reportDown","width=1024, height=720, menubar=no, location=no, status=no,scrollbars=yes");
		$('form[name=searchForm]').attr('target','reportDown').attr('action','${pageContext.request.contextPath}/report/FM-ASSET002_I.do').submit();
		$('form[name=searchForm]').attr('target','');
	}
</script>
</head>
<body>
	<p class="history"><a href="#none">위험관리</a><span>&gt;</span>위험조치 관리</p>
	<div class="conttitle">위험조치 관리</div>
	<div class="tabArea">
	    <ul class="list2Tab">
	        <li><a href="javascript:goPage();">일반자산</a></li>
	        <li class="sel"><a href="#none">관리체계</a></li>
	    </ul>
	</div>
	<div class="search">
		<form id="searchForm" method="post" name="searchForm">
		<input type="hidden" id="grpKey" name="grpKey" value="0" />
		    <div class="defalt">
		        <fieldset class="searchForm">
		            <legend>기본검색</legend>
		            <ul class="detail newField">
                        <li>
                            <span class="title"><label for="frqGrd">발생빈도</label></span>
                            <select id="frqGrd" name="frqGrd" class="selectBox"  title="항목 선택">
								<option value="">전체</option>
								<option value="H">상</option>
								<option value="M">중</option>
								<option value="L">하</option>
                            </select>
                        </li>
                        <li>
                            <span class="title"><label for="staCod">처리상태</label></span>
                            <sb:select name="staCod"  type="A" code="RSKSTATE"/>
                        </li>
                        <li>
                            <span class="title"><label for="rskYn">진단결과</label></span>
                            <select id="rskChk" name="rskChk" class="selectBox"  title="항목 선택">
								<option value="">전체</option>
								<option value="N">미흡</option>
								<option value="P">부분미흡</option>
								<option value="Y">양호</option>
                            </select>
                        </li>
                        <li>
                            <span class="title"><label for="cocGrd">위험강도</label></span>
                            <select id="cocGrd" name="cocGrd" class="selectBox"  title="항목 선택">
								<option value="">전체</option>
								<option value="H">상</option>
								<option value="M">중</option>
								<option value="L">하</option>
                            </select>
                        </li>
                        <li>
                            <span class="title"><label for="worker">위험등급</label></span>
                            <select id="rskGrd" name="rskGrd" class="selectBox"  title="항목 선택">
								<option value="">전체</option>
								<option value="H">상</option>
								<option value="M">중</option>
								<option value="L">하</option>
                            </select>
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
		<div class="title">위험관리 내역</div>
		<div class="talbeArea">
			<div class="topBtnArea">
				<ul class="btnList">
					<li>
						<button type="button" onclick="reportDownI();"><span class="icoDown"></span>위험분석보고서</button>
					</li>
					<li>
						<button type="button" onclick="reportDown();"><span class="icoDown"></span>위험조치계획서</button>
					</li>
				 	<li>
						<button type="button" onclick="excelUpload();"><span class="icoAdd"></span>엑셀업로드</button>
					</li>
					<li>
						<button type="button" onclick="editRskPop('I');"><span class="icoAdd"></span>추가</button>
					</li>
				</ul>
			</div>
			<table id="gridTb">  
			</table>
			<div id="pager"></div>
		</div>
	</div>
	<!-- 위험내역 팝업폼 -->
	<form id="rskInfoForm" name="rskInfoForm" action="FM-RISKM003_M_popup.do" method="post" target="rskInfoPopup">
		<input type="hidden" id="popRskKey" name="popRskKey"/>
	</form>
	<form id="excelPopForm" name="excelPopForm" action="FM-RISKM003_excel_popup.do" method="post" target="excelPopup">
		<input type="hidden" id="excelSvcCod" name="excelSvcCod"/>
		<input type="hidden" id="excelDepCod" name="excelDepCod"/>
		<input type="hidden" id="downType" name="downType"/>
	</form>
</body>
</html>