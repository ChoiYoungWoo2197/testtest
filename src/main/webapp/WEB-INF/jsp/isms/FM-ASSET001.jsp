<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<style type="text/css">
ul.legend { float: right; margin-top: 8px; }
ul.legend li {float: left; display: inline-block; padding: 2px 10px; margin-left: 5px; }
ul.legend li.typeA { background-color: #fdfbd8; border: 1px solid #ccc; }
ul.legend li.typeB { background-color: #ffdfb2; border: 1px solid #ccc; }
</style>
<script type="text/javascript">
	var columns=['key','자산코드','서비스명','서비스상세','자산유형명','자산명','IP','호스트명','OS','관리부서','관리담당자','관리책임자','운영부서','운영담당자','운영책임자',
	         '용도','자산위치','기밀성','무결성','가용성','중요도점수','자산중요도','인증대상','샘플링대상','기반시설대상','개인정보보유','사용유무',
	         '가변필드1','가변필드2','가변필드3','가변필드4','가변필드5','가변필드6','가변필드7','가변필드8','가변필드9','가변필드10',
	         '등록일','최종수정일'
	];
	var colModels=[
			  {name:'uar_ass_key',	index:'uar_ass_key',				align:'center',	hidden:true},
	          {name:'uar_ass_cod',	index:'uar_ass_cod',	width: 130,	align:'center',	hidden:false},
	          {name:'uar_sub_nam',	index:'uar_sub_nam',	width: 90,	align:'center',	hidden:false},
	          {name:'uar_svr_etc',	index:'uar_svr_etc',	width: 150,	align:'center',	hidden:false},
	          {name:'uar_ass_nam',	index:'uar_ass_nam',	width: 100,	align:'center',	hidden:false,	sorttype: 'text'},
	          {name:'uar_eqp_nam',	index:'uar_eqp_nam',	width: 100,	align:'center',	hidden:false},
	          {name:'uar_ip',		index:'uar_ip',			width: 100,	align:'center',	hidden:false},
	          {name:'uar_host',		index:'uar_host',		width: 100,	align:'center',	hidden:false},
	          {name:'uar_os',		index:'uar_os',			width: 100,	align:'center',	hidden:false},
	          {name:'uar_dep_nam',	index:'uar_dep_nam',	width: 100,	align:'center',	hidden:false},
	          {name:'uar_own_nam',	index:'uar_own_nam',	width: 80,	align:'center',	hidden:false},
	          {name:'uar_adm_nam',	index:'uar_adm_nam',	width: 80,	align:'center',	hidden:false},
	          {name:'uar_opr_nam',	index:'uar_opr_nam',	width: 100,	align:'center',	hidden:false},
	          {name:'uar_use_nam',	index:'uar_use_nam',	width: 80,	align:'center',	hidden:false},
	          {name:'uar_pic_nam',	index:'uar_pic_nam',	width: 80,	align:'center',	hidden:false},

	          {name:'uar_dtl_exp',	index:'uar_dtl_exp',	width: 150,	align:'center',	hidden:false},
	          {name:'uar_adm_pos',	index:'uar_adm_pos',	width: 120,	align:'center',	hidden:false},
	          {name:'uar_app_con',	index:'uar_app_con',	width: 50,	align:'center',	hidden:false},
	          {name:'uar_app_itg',	index:'uar_app_itg',	width: 50,	align:'center',	hidden:false},
	          {name:'uar_app_avt',	index:'uar_app_avt',	width: 50,	align:'center',	hidden:false},
	          {name:'uar_app_tot',	index:'uar_app_tot',	width: 80,	align:'center',	hidden:false},
	          {name:'uar_ass_lvl',	index:'uar_ass_lvl',	width: 80,	align:'center',	hidden:false},
	          {name:'uar_aud_yn',	index:'uar_aud_yn',		width: 80,	align:'center',	hidden:false},
	          {name:'uar_smp_yn',	index:'uar_smp_yn',		width: 80,	align:'center',	hidden:false},
	          {name:'uar_inf_yn',	index:'uar_inf_yn',		width: 80,	align:'center',	hidden:false},
	          {name:'uar_prv_yn',	index:'uar_prv_yn',		width: 80,	align:'center',	hidden:false},
	          {name:'uar_use_yn',	index:'uar_use_yn',		width: 80,	align:'center',	hidden:false},

	          {name:'uar_val_cl0',	index:'uar_val_cl0',	width: 150,	align:'center',	hidden:true},
	          {name:'uar_val_cl1',	index:'uar_val_cl1',	width: 150,	align:'center',	hidden:true},
	          {name:'uar_val_cl2',	index:'uar_val_cl2',	width: 150,	align:'center',	hidden:true},
	          {name:'uar_val_cl3',	index:'uar_val_cl3',	width: 150,	align:'center',	hidden:true},
	          {name:'uar_val_cl4',	index:'uar_val_cl4',	width: 150,	align:'center',	hidden:true},
	          {name:'uar_val_cl5',	index:'uar_val_cl5',	width: 150,	align:'center',	hidden:true},
	          {name:'uar_val_cl6',	index:'uar_val_cl6',	width: 150,	align:'center',	hidden:true},
	          {name:'uar_val_cl7',	index:'uar_val_cl7',	width: 150,	align:'center',	hidden:true},
	          {name:'uar_val_cl8',	index:'uar_val_cl8',	width: 150,	align:'center',	hidden:true},
	          {name:'uar_val_cl9',	index:'uar_val_cl9',	width: 150,	align:'center',	hidden:true},

	          {name:'uar_rgt_mdh',	index:'uar_rgt_mdh',	width: 80,	align:'center',	hidden:false},
	          {name:'uar_upt_mdh',	index:'uar_upt_mdh',	width: 80,	align:'center',	hidden:false}
	];

	$(document).ready(function() {
		stOrgList();
		$("#assGbn").chained("#assCat2");

		$("#list4Tab").find("li").first().addClass("sel");
		searchList(6);

		$("#list4Tab").find("li").click(function() {
			$(this).parent().find("li").removeClass("sel");
			$(this).addClass("sel");
			$("#assCat2").val("");
			searchList($(this).attr("title"));
		});

		// legend
		if ($("#manCyl").val() == 0) {
			$(".legend").append('<li class="typeA ui-corner-all">심사 자산</li>');
		}
		else {
			$(".legend").append('<li class="typeB ui-corner-all">샘플링 대상</li>');
		}

		// 2016-11-30, 심사 자산 확정
		// 2017-06-23, 체크박스로 확정 되도록 변경
		$("#btnAddFinal").click(function() {
			var assetKeys = getAssetKeys();
			if ( !assetKeys) {
				alert("[심사자산]으로 확정할 자산을 선택해주세요.");
				return false;
			}
			if (confirm("기존 심사 자산이 존재하는 경우 현재의 자산현황(ISC)으로 업데이트됩니다.\n선택한 자산들을 [심사자산]으로 확정 하시겠습니까?")) {
				$.post("FM-ASSET003_asset_update.do", {command: "addFinal", assKeys: assetKeys}, function() {
					alert("[심사자산] 확정이 정상적으로 처리되었습니다.");
					$("#gridTb").trigger("reloadGrid");
				});
			}
		});

		// 2017-06-27, 샘픙링 대상 설정
		$("#btnAddSample").click(function() {
			var assetKeys = getAssetKeys();
			if ( !assetKeys) {
				alert("[샘플링 대상]으로 변경할 자산을 선택해주세요.");
				return false;
			}
			if (confirm("선택한 자산들을 [샘플링 대상]으로 지정 하시겠습니까?")) {
				$("#assCat").val($("#assCat2").val());
				$.post("FM-ASSET003_asset_update.do", {command: "addSample", assKeys: assetKeys}, function() {
					alert("[샘플링 대상] 지정이 정상적으로 처리되었습니다.");
					$("#gridTb").trigger("reloadGrid");
				});
			}
		});

		// 2017-06-27, 샘픙링 대상 제외
		$("#btnRemoveSample").click(function() {
			var assetKeys = getAssetKeys();
			if ( !assetKeys) {
				alert("[샘플링대상]에서 제외할 자산을 선택해주세요.");
				return false;
			}
			if (confirm("선택한 자산들을 [샘플링 대상]에서 제외 하시겠습니까?")) {
				$("#assCat").val($("#assCat2").val());
				$.post("FM-ASSET003_asset_update.do", {command: "removeSample", assKeys: assetKeys}, function() {
					alert("[샘플링 대상] 제외가 정상적으로 처리되었습니다.");
					$("#gridTb").trigger("reloadGrid");
				});
			}
		});

		// 2017-06-27, 심사 대상 제외
		$("#btnRemoveFinal").click(function() {
			var assetKeys = getAssetKeys();
			if ( !assetKeys) {
				alert("[심사자산]에서 제외할 자산을 선택해주세요.");
				return false;
			}
			if (confirm("선택한 자산들을 [심사자산]에서 제외 하시겠습니까?")) {
				$("#assCat").val($("#assCat2").val());
				$.post("FM-ASSET003_asset_update.do", {command: "removeFinal", assKeys: assetKeys}, function() {
					alert("[심사자산] 제외가 정상적으로 처리되었습니다.");
					$("#gridTb").trigger("reloadGrid");
				});
			}
		});
	});

	function searchList(assCat){
		$("#gridTb").jqGrid("GridUnload");

		// 상단 검색조건(자산분류)
		if ($("#assCat2").val()) {
			assCat = $("#assCat2").val();
			$("#list4Tab").find("li").removeClass("sel");
			$("#list4Tab").find("li").each(function() {
				if (assCat == $(this).attr("title")) {
					$(this).addClass("sel");
				}
			});
		}

		if (! assCat) {
			assCat = $("#list4Tab").find("li.sel").attr("title");
		}
		$("#assCat").val(assCat);

		var formData = formSerializeObject($("#searchForm"));
		// deep copy
		var column = $.extend(true, [], columns);
		var colModel = $.extend(true, [], colModels);


		// 2016-06-16
		var assetColumns = new Array();
		$.ajax({
			url			: "${pageContext.request.contextPath}/asset/FM-ASSET001_CAT_LIST.do",
			async       : false,
			type		: "post",
			data		: {uarAssCat: assCat},
			dataType	: "json",
			success		: function(data) {
				assetColumns = [data.result.uacValCl0, data.result.uacValCl1, data.result.uacValCl2, data.result.uacValCl3, data.result.uacValCl4,
			                   data.result.uacValCl5, data.result.uacValCl6, data.result.uacValCl7, data.result.uacValCl8, data.result.uacValCl9];
			 }
		});

		var cnt = 0;
		for (var i in column) {
			if (column[i].indexOf("가변필드") == 0 && assetColumns[cnt]) {
				column[i] = assetColumns[cnt];
				colModel[i].hidden = false;
				cnt++;
			}
		}

		$("#gridTb").jqGrid({
			url:"${pageContext.request.contextPath}/asset/FM-ASSET001_list.do",

			datatype : "json",
			mtype : "post",
			colNames:column,
			colModel:colModel,
			height: 400,		// 전체 grid 화면의 세로 사이즈 지정.
			pager : '#pager',
			rowNum : 50,
			rowList : [50, 100, 500, 1000],
			postData : formData,
// 			rowList : [10, 20, 50],
			rownumbers : true,	// 각 row의 맨 앞줄에 각 row의 번호가 자동적으로 부여된다.
			sortable : true,	// 초기 sort 조건과 관련되며 ture이면 초기 데이터가 정렬되서 보인다.
			sortname : 'uar_ass_key',	// 초기 디이터 정렬시 정렬조건, 서버에 정렬  조건으로 전송된다.
			sortorder : 'asc',	// 순방향, 역방향 정렬(desc, asc)
			viewrecords : true,	// 오른쪽 하단에 총 몇개중 몇번째꺼 표시
			loadonce : false,	// reload 여부이며, true로 설정시 한번만 데이터를 받아오고 그 다음부터는 데이터를 받아오지 않는다.
			multiselect: true,	// select box 적용
			loadtext : 'loading..',	// 서버연동시 loading 중이라는 표시가 뜨는데 그곳의 문자열 지정.
			jsonReader:{
				repeatitems : true,
				root : function (obj) {return obj.result},
				page: function (obj) {return obj.currentPageNo},
			    total: function (obj) {return obj.totalPage},
			    records: function (obj) {return obj.totalRecord}
			},
			onSelectRow: function(id, status, e){
				var assetKey = $("#gridTb").getRowData(id).uar_ass_key;

				var $myGrid = $(this),
		        i = $.jgrid.getCellIndex($(e.target).closest('td')[0]),
		        cm = $myGrid.jqGrid('getGridParam', 'colModel');
		  		if (cm[i].name != 'cb') {
		  			editAssetInfo(assetKey);
		  		}
			},
			// 2017-06-28, 인증대상, 샘플자산 강조
			loadComplete: function(data) {
				var ids = $("#gridTb").getDataIDs();
				var manCyl = $("#manCyl").val();
				$.each(ids, function(idx, rowId) {
					var audYn = $("#gridTb").getRowData(rowId).uar_aud_yn;
					var smpYn = $("#gridTb").getRowData(rowId).uar_smp_yn;
					if (manCyl == 0 && audYn == "대상") {
						$("#gridTb").setRowData(rowId, false, {background: "#fdfbd8"});
					}
					else if (manCyl > 0 && smpYn == "대상") {
						$("#gridTb").setRowData(rowId, false, {background: "#ffdfb2"});
					}
				});
			}
		}).trigger("reloadGrid");

		// jqgrid 페이징바
		$("#gridTb").jqGrid('navGrid','#pager'
			,{ excel:false,add:false,edit:false,view:false,del:false,search:false,refresh:false}
		);

		// jqgrid 전체 넓이 지정, 가로 스크롤 생성
		$("#gridTb").setGridWidth(1018, false);
	}

	function editAssetInfo(assetKey, mode){
		window.open("","assetCodeInfoPopup","width=800, height=700, menubar=no, location=no, status=no,scrollbars=yes");
		$("#uarAssKey").val(assetKey);
		$("#mode").val("0");
		document.assetCodeInfoForm.submit();
	}

	function editAssetAdd(assetKey, mode){
		window.open("","assetCodeInfoPopup","width=800, height=700, menubar=no, location=no, status=no,scrollbars=yes");
		$("#uarAssKey").val(assetKey);
		$("#mode").val("1");
		document.assetCodeInfoForm.submit();
	}

	function excelDown(){
		$("#useYn").val("");
		document.searchForm.action = "${pageContext.request.contextPath}/excel/FM-ASSET001.do";
	   	document.searchForm.submit();
	}

	function excelUpload(){
	   	window.open("${pageContext.request.contextPath}/asset/FM-ASSET001_excel_popup.do",
	   			"assetUploadPopup","width=1200, height=700, menubar=no, location=no, status=no,scrollbars=yes");
	}

	function excelReportDown(){
		document.searchForm.action = "${pageContext.request.contextPath}/excel/FM-ASSET001_REPORT.do";
	   	document.searchForm.submit();
	}

	// 2017-06-27, 체크된 자산키를 String으로 리턴
	function getAssetKeys() {
		var list = $("#gridTb").jqGrid('getGridParam', 'selarrrow');
		var data = Array();
		$.each(list, function(k, v) {
			var value = $("#gridTb").jqGrid("getCell", v, "uar_ass_key");
			data.push(value);
		});
		return data.join(",");
	}
</script>
</head>
<body>
<form id="searchForm" name="searchForm" method="post">
<input type="hidden" id="assCat" name="assCat" />
<input type="hidden" id="useYn" name="useYn" value="Y" />
<input type="hidden" id="manCyl" name="manCyl" value="${assetManCyl}" />
	<c:import url="/titlebar.do" />
	<div class="search">
	    <div class="defalt">
	        <fieldset class="searchForm">
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
	                    <sb:select name="service" type="S2" forbidden="true" />
	                </li>
                    <li>
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
	                	<span class="title"><label for="assLvl">자산등급</label></span>
	                    <select id="assLvl" name="assLvl" class="selectBox">
                         	<option value="">전체</option>
                         	<option value="L(1)">L(1)</option>
                         	<option value="M(2)">M(2)</option>
                         	<option value="H(3)">H(3)</option>
                         </select>
	                </li>
	                <li>
	                	<span class="title"><label for="audYn">심사대상</label></span>
	                    <select id="audYn" name="audYn" class="selectBox">
                         	<option value="">전체</option>
                         	<option value="Y">대상</option>
                         	<option value="N">비대상</option>
                         </select>
	                </li>
	                <li>
	                	<span class="title"><label for="smpYn">샘플링대상</label></span>
	                    <select id="smpYn" name="smpYn" class="selectBox">
                         	<option value="">전체</option>
                         	<option value="Y">대상</option>
                         	<option value="N">비대상</option>
                         </select>
	                </li>
	                <li>
	                	<span class="title"><label for="assNam">자산검색</label></span>
	                    <input type="text" id="assNam" name="assNam" class="inputText" title="자산명 입력" placeholder="자산명, 호스트, IP, 자산코드를 입력하세요.">
	                </li>
	                <li class="btnArea">
	                 	<button type="button" onclick="searchList();" class="btnSearch defaltBtn">조건으로 검색</button>
					</li>
	            </ul>
	        </fieldset>
	    </div>
	</div>
	<div class="contents">
		<div class="topBtnArea">
			<ul class="btnList">
			<c:if test="${assetManCyl eq '0'}">
				<li>
					<button type="button" id="btnAddFinal" style="color: red;"><span class="icoSave"></span>심사자산 확정</button>
				</li>
			</c:if>
			<c:if test="${assetManCyl != '0'}">
				<li>
					<button type="button" id="btnAddSample" style="color: red;"><span class="icoSave"></span>샘플링대상 추가</button>
				</li>
				<li>
					<button type="button" id="btnRemoveSample" style="background: #fff;"><span class="icoDel"></span>샘플링대상 제외</button>
				</li>
				<li>
					<button type="button" id="btnRemoveFinal" style="background: #fff;"><span class="icoDel"></span>심사자산 제외</button>
				</li>
				<li>
					<button type="button" onclick="editAssetAdd('${uarAssCod}','1');"><span class="icoAdd"></span>추가</button>
				</li>
				<li>
					<button type="button" onclick="excelUpload();"><span class="icoAdd"></span>자산 업로드</button>
				</li>
				<li>
					<button type="button" onclick="excelDown();"><span class="icoExl"></span>자산 다운로드</button>
				</li>
			</c:if>
				<li>
					<button type="button" onclick="excelReportDown();"><span class="icoExl"></span>자산관리대장 다운로드</button>
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
		<div class="talbeArea">
			<table id="gridTb">
			</table>
			<div id="pager"></div>
		</div>
		<!-- 범례 -->
		<ul class="legend"></ul>
	</div>
</form>
<form id="assetCodeInfoForm" name="assetCodeInfoForm" action="FM-ASSET001_popup.do" method="post" target="assetCodeInfoPopup">
<input type="hidden" id="uarAssKey" name="uarAssKey" />
<input type="hidden" id="mode" name="mode" />
</form>
</body>
</html>