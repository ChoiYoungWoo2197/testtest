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
			var uagSvrCod = $("#grpKey").val();
			var uagSvrNam = $("#grpKey option:selected").text();
			var doa = $("#doa").val();
			if (!uagSvrCod) {
				alert("서비스를 선택해주세요.");
				return false;
			}
			if (!doa) {
				alert("위험도를 선택해주세요.");
				return false;
			}
			if (!$("#rskDetailGgrid").text()) {
				alert("위험관리 내역이 존재하지 않습니다.");
				return false;
			}
			$("#uagSvrCod2").val(uagSvrCod);
			$("#uagSvrNam2").val(uagSvrNam);
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

	// 위험내역 리스트
	function searchList(){
		$("#rskDetailGgrid").jqGrid("GridUnload");	// 동적데이터 전송시 필수
		var formData = formSerialize($("#searchForm"));
		$("#rskDetailGgrid").jqGrid({
			url:"${pageContext.request.contextPath}/riskm/FM-RISKM010_rskList.do",
			postData : formData,
			datatype : "json",
			mtype : "post",
			colNames:['key','우려사항코드','서비스', '분류','취약점내용','위험도','진단점수','진단상태','진단상태','중요도코드','중요도','Concern Value',
			          '상태','urrRskKey'],
			colModel:[
					  {name:'ursRskKey',	index:'ursRskKey',								hidden:true},
			          {name:'usoCocCod',	index:'usoCocCod',								hidden:true},
			          {name:'uagSvrNam',	index:'uagSvrNam',	width: 60,	align:'center',	hidden:false},
			          {name:'usoCocNam',	index:'usoCocNam',	width: 90,	align:'center',	hidden:false},
			          {name:'urvVlbDes',	index:'urvVlbDes',	width: 400,	align:'left',	hidden:false},
			          {name:'ursRskRst',	index:'ursRskRst',	width: 60,	align:'center',	hidden:false},
			          {name:'ursRskChk',	index:'ursRskChk',								hidden:true},
			          {name:'ursChkNam',	index:'ursChkNam',	width: 100,	align:'center',	hidden:false},
			          {name:'usoRskGrd',	index:'usoRskGrd',								hidden:true},
			          {name:'usoGrdNam',	index:'usoGrdNam',	width: 70,	align:'center',	hidden:false},
			          {name:'usoRskPnt',	index:'usoRskPnt',	width: 60,	align:'center',	hidden:false},
			          {name:'ursStaCod',	index:'ursStaCod',								hidden:true},
			          {name:'ursStaNam',	index:'ursStaNam',	width: 112,	align:'center',	hidden:false},
			          {name:'urrRskKey',	index:'urrRskKey',								hidden:true}
			],
			height: 400,		// 전체 grid 화면의 세로 사이즈 지정.
			pager : '#detailPager',
			rowNum : '300',
// 			rowList : [10, 20, 50],
			rownumbers : true,	// 각 row의 맨 앞줄에 각 row의 번호가 자동적으로 부여된다.
			sortable : true,	// 초기 sort 조건과 관련되며 ture이면 초기 데이터가 정렬되서 보인다.
			sortname : 'urgRskKey',	// 초기 디이터 정렬시 정렬조건, 서버에 정렬  조건으로 전송된다.
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
				var ursRskKey = $("#rskDetailGgrid").getRowData(id).ursRskKey;
				var urrRskKey = $("#rskDetailGgrid").getRowData(id).urrRskKey;
				editRskPop(ursRskKey, urrRskKey);
			},
		}).trigger("reloadGrid");

		// jqgrid 페이징바
		$("#rskDetailGgrid").jqGrid('navGrid','#detailPager'
			,{ excel:false,add:false,edit:false,view:false,del:false,search:false,refresh:false}
		);
		// jqgrid 전체 넓이 지정, 가로 스크롤 생성
		$("#rskDetailGgrid").setGridWidth(1018, false);
	}

	// 위험내역 등록 및 수정 팝업창
	function editRskPop(key, urrKey) {
		$("#ursRskKey").val(key);
		$("#urrRskKey").val(urrKey);
		$("#grpKey2").val($("#grpKey").val());
		window.open("","rskInfoPopup","width=730, height=800, menubar=no, location=no, status=no,scrollbars=yes");
		document.rskInfoForm.submit();
	}
/*
	function goPage() {
		location.href = "FM-RISKM003_M.do";
	}*/

	function excelUpload(){
		window.open("","excelPopup","width=450, height=300, menubar=no, location=no, status=no, scrollbars=auto");
		$("#downType").val("1");
		document.excelPopForm.submit();
	}

	function reportDown(){
		window.open("","reportPopup","width=450, height=250, menubar=no, location=no, status=no, scrollbars=auto");
		document.reportPopForm.submit();
 	}

	function reportDownT(){
		window.open("","reportDown","width=1024, height=720, menubar=no, location=no, status=no,scrollbars=yes");
		$('form[name=searchForm]').attr('target','reportDown').attr('action','${pageContext.request.contextPath}/report/FM-ASSET002_T.do').submit();
		$('form[name=searchForm]').attr('target','');
	}

	function reloadList() {
		$("#rskDetailGgrid").trigger("reloadGrid");
	}
</script>
</head>
<body>
	<c:import url="/titlebar.do" />
	<!--
	<div class="tabArea">
	    <ul class="list2Tab">
	        <li class="sel"><a href="#none">일반자산</a></li>
	        <li><a href="javascript:goPage();">관리체계</a></li>
	    </ul>
	</div>
	 -->

	<div class="search">
		<form id="searchForm" method="post" name="searchForm">
			<input type="hidden" id="searchDoaGrd" value="<c:out value="${doaGrd}"/>" />
		    <div class="defalt">
		        <fieldset class="searchForm">
		            <legend>기본검색</legend>
		            <ul class="detail newField">
						<li>
		            		<span class="title"><label for="grpKey">서비스</label></span>
		                    <sb:select name="uagSvrCod" type="S" forbidden="true"/>
		            	</li>
		            	<li>
			                <span class="title"><label for="doaGrd">위험도등급</label></span>
		                    <sb:select name="doaGrd" type="A" code="DOAGRD" allText="전체" value="${doaGrd}" />
						</li>
						<li>
							<span class="title"><label for="grpKey">위험도</label></span>
							<select id="doa" name="doa" class="selectBox"  title="doa 선택">
                            </select>
						</li>
		                <li class="btnArea">
		                 	<button type="button" onclick="searchList();" class="btnSearch">조건으로 검색</button>
						</li>
		            </ul>
		        </fieldset>
		    </div>
	    </form>
	</div>
	<div class="contents">
		<div class="listArea">
			<div class="title">위험관리 내역</div>
			<div class="talbeArea">
          		<div class="topBtnArea">
					<ul class="btnList">
						<c:if test="${authGbn eq 'A'}">
							<li><button type="button" id="btnBatch"><span class="icoPer"></span>대책수립 담당자 일괄 지정</button></li>
						</c:if>
						<li>
							<button class="iReport" type="button" onclick="reportDown();"><span class="icoDown"></span>위험조치계획서</button>
						</li>
					</ul>
				</div>
				<table id="rskDetailGgrid">
				</table>
				<div id="detailPager"></div>
			</div>
		</div>
	</div>
	<!-- 위험내역 팝업폼 -->
	<form id="rskInfoForm" name="rskInfoForm" action="FM-RISKM010_popup.do" method="post" target="rskInfoPopup">
		<input type="hidden" id="ursRskKey" name="ursRskKey"/>
		<input type="hidden" id="urrRskKey" name="urrRskKey"/>
		<input type="hidden" id="grpKey2" name="grpKey"/>
	</form>
	<form id="reportPopForm" name="reportPopForm" action="FM-RISKM010_report_popup.do" method="post" target="reportPopup">
	</form>
	<form id="excelPopForm" name="excelPopForm" action="FM-RISKM010_excel_popup.do" method="post" target="excelPopup">
		<input type="hidden" id="excelSvcCod" name="excelSvcCod"/>
		<input type="hidden" id="excelDepCod" name="excelDepCod"/>
		<input type="hidden" id="downType" name="downType"/>
	</form>
	<form id="batchPopForm" name="batchPopForm" action="FM-RISKM010_batch_popup.do" method="post" target="batchPopup">
		<input type="hidden" id="uagSvrCod2" name="uagSvrCod"/>
		<input type="hidden" id="uagSvrNam2" name="uagSvrNam"/>
		<input type="hidden" id="doa2" name="doa"/>
	</form>
</body>
</html>