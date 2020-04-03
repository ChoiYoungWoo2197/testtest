<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
<%@ include file="/WEB-INF/include/contents.jsp"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<style>
	td.notComp { color: #aaa !important; }
	td.notComp a { color: inherit; }
	#workList td, #workList .uplidFileList {
		height: 100%;
	}
	#workList .listArea.listControl {
		width: 200px;
		height: 100%;
		min-height: 1rem;
		margin: 0;
		padding: 0;
	}
	.link-text {
		color: #63b3ed !important;
	}
	.sa-box-title {
		border: 1px solid #b2b2b2;
		background: #ebebeb;
		margin-top: 0.7rem;
		border-bottom: 0;
		padding-top: 4px;
		padding-bottom: 4px;
	}

	.sa-work a.link-text {
		color: #ef8009 !important;
	}

	.sa-work-row {
		border-right: 3px solid #ef8009;
	}

</style>
<script type="text/javascript">
$(document).ready(function() {
	loadingBarSet();	// loding bar
	getCtrCode();
	$("#standard").change(function() {
		getCtrCode();
	});

	// 2016-11-01
	$("#listForm").on("click", ".umfFleKey", function() {
		var checked = $(this).is(":checked") ? "Y" : "N";
		$.post("FM-INSPT001_FILE_SAVE.do", {fleKey: $(this).attr("alt"), msrYn: checked}, function() {
		}, "json");
	});

	/*SA증적파일 체크박스에 변화가 생겼을 때*/
	$("#listForm").on("click", "[data-safile]", function(event) {
		var checked = $(this).is(":checked") ? "Y" : "N";
		var service = $(this).data("service");
		var control = $(this).data("control");
		var file = $(this).data("safile");

		if (checked == "" || service == "" || control == "" || file == "") {
			alert("정보가 부족합니다.")
		} else {
			$("[data-control='" + control + "'][data-safile='" + file + "']").prop("checked", checked === "Y" ? "checked" : "");
			$.get("FM-INSPT001_SA_FILE_SAVE.do",{fleKey: file, service: service, ctrKey: control, checked: checked}, function () {}, "json");
		}

	});

	// tooltip
	$("#workList").tooltip();
});

function getCtrCode() {
	var standard = $("#standard").val();
	var service = $("#service").val();
		$.ajax({
		url			: "${pageContext.request.contextPath}/inspt/FM-INSPT004_getCtrCode.do",
		type		: "post",
		data		: {"standard":standard,"service":service},
		dataType	: "json",
		success		: function(data){
			var inData = '<option value="">전체</option>';
			for(var i=0; i<data.result.length; i++) {
				var str = cutString(data.result[i].ucm1lvNam, 30);
				inData += '<option value="'+data.result[i].ucm1lvCod+'">'+str+'</option>';
			}
			$("#ucm1lvCod").html(inData);
		 },
		 error : function(){
			 alert('error');
		 }
	 });
}

function sav(){

	frm = document.listForm;
   	frm.action = "FM-INSPT001_FILE_SAVE.do";
	frm.submit();
}

//첨부파일 다운로드
function getFile(key, where) {
	$("#downKey").val(key);
	$("#where").val("wrk");
	document.fileDown.action = "${pageContext.request.contextPath}/common/getFileDown.do";
	document.fileDown.submit();
}

function fileDown(){
	var obj = document.getElementsByName("checking");
	var checkCnt = 0;
	var arr = new Array;

	for(var i=0; i<obj.length; i++){
		if(obj[i].checked == true){
			checkCnt++;
		}
	}
	if(checkCnt ==0){
		alert("통제항목을 선택하세요");
		return;
	}

	document.listForm.action = "${pageContext.request.contextPath}/common/ZIP_DOWN.do";
   	document.listForm.submit();
}

var workListOfControlItem = null;

function search(){

	if($("select[name=standard]").val() == "") {
		alert("컴플라이언스를 선택하세요.");
		return;
	}

	if($("select[name=service]").val() == "") {
		alert("서비스를 선택하세요.");
		return;
	}

	var service = $("#service").val();
	var standard = $("#standard").val();
	var worker = $("#worker").val();
	var dept = $("#dept").val();
	var goalNum = $("#goalNum").val();
	var workTerm = $("select[name=workTerm]").val();
	var workCycleTerm = $("#workCycleTerm").val();
 	var $obj = $("#workList");
 	$obj.empty();

	$("#searchForm").ajaxSubmit({
		url : "${pageContext.request.contextPath}/inspt/FM-INSPT001_getCntrWork.do",
		 type		: "post",
		 data		: $("#searchForm").serialize(),
		 dataType	: "json",
		 success	: function(data){
			 workListOfControlItem = data.resultList;

			 var saFiles = data.saFileList;
			 var search = data.search;

				var arrRow = Array();
				var workListHTML = "";
			 	var loopCurrentUtdTrcKey = null;
			 	var loopCurrentUcmGolNo = null;
			 	var checkedControlList = [];
				var tempRowSpan = null;
			 	for(var i=0; i<data.resultList.length; i++) {

					workListHTML = "";
					// 속도문제로 rowspan() 대처함
					var ctrKey = data.resultList[i].ucmCtrKey;
					if (i > 0 && data.resultList[i-1].ucm2lvCod == data.resultList[i].ucm2lvCod) {
						ucm2lvCod = '';
						arrRow[0]++;
					} else {
						ucm2lvCod = '<td class="ucm2lvCod">'+data.resultList[i].ucm2lvCod+'</td>';
						$obj.find(".ucm2lvCod:last").attr("rowspan", arrRow[0]);
						arrRow[0] = 1;
					}
					if (i > 0 && data.resultList[i-1].ucm2lvNam == data.resultList[i].ucm2lvNam) {
						ucm2lvNam = '';
						arrRow[1]++;
					} else {
						ucm2lvNam = '<td class="ucm2lvNam">'+data.resultList[i].ucm2lvNam+'</td>';
						$obj.find(".ucm2lvNam:last").attr("rowspan", arrRow[1]);
						arrRow[1] = 1;
					}
					if (i > 0 && data.resultList[i-1].ucm3lvNam == data.resultList[i].ucm3lvNam) {
						ucm3lvNam = '';
						arrRow[2]++;
						tempRowSpan = null;
					} else {
						ucm3lvNam = '<td class="ucm3lvNam fontLeft"><div class="ucmTitle">'+data.resultList[i].ucmGolNo+'</div><div class="ucmDescription">'+data.resultList[i].ucm3lvNam+'</div></td>';

						$obj.find(".ucm3lvNam:last").attr("rowspan", arrRow[2]);
						$obj.find(".saFileListColumn:last").attr("rowspan", arrRow[2]);
						tempRowSpan = arrRow[2];

						arrRow[2] = 1;
					}

					var docNam = data.resultList[i].utdDocNam;
					var fileName = data.resultList[i].fileName;
					var termGbn = data.resultList[i].termGbn;

					// var utwSvcCod = data.resultList[i].utwSvcCod;
					var utwSvcCod = data.resultList[i].utwSvcCod;
					var utwDepNam = data.resultList[i].utwDepNam;
					var worker = data.resultList[i].worker;
					var ucmCtrGbn = data.resultList[i].ucmCtrGbn;
					var utwWrkKey = data.resultList[i].utwWrkKey;
					var utdTrcKey = data.resultList[i].utdTrcKey;
					var ucmGolNo = data.resultList[i].ucmGolNo;
					var ucmCtrKey = data.resultList[i].ucmCtrKey;
					var isSa = data.resultList[i].isSa;

					var changedUcmGolNo = false;
					var changedUtdTrcKey = false;


					var saFilesOfControlItem = null;
					if (checkedControlList.indexOf(ucmCtrKey) === -1) {
						checkedControlList.push(ucmCtrKey);

						/* 현재 통제항목에 포함되어 있는 SA 증적파일 필터링 */
						saFilesOfControlItem = _.filter(saFiles, function (saFile) {
							return ucmCtrKey == saFile.baseCtrKey;
						});

						/* 필터링 된 SA 증적파일을 파일 식별값 기준으로 중복을 제거한다. */
						saFilesOfControlItem = _.uniqBy(saFilesOfControlItem, "fleKey");
					}

					if (loopCurrentUcmGolNo !== ucmGolNo) {
						loopCurrentUcmGolNo = ucmGolNo;
						loopCurrentUtdTrcKey = null;
						changedUcmGolNo = true;
					} else {
						changedUcmGolNo = false;
					}

					if (loopCurrentUtdTrcKey !== utdTrcKey) {
						loopCurrentUtdTrcKey = utdTrcKey;
						changedUtdTrcKey = true;
					} else {
						changedUtdTrcKey = false;
					}

					var trClass = "";
					var tdClass = "";
					if( data.resultList.length == (i+1)) {
						trClass = "last";
					}

					// 2018-10-12 s,
					if (data.resultList[i].utwWrkSta != "90") {
						tdClass = "notComp";
					}


					workListHTML += '<tr class="'+ trClass + " " + (isSa === "true" ? "sa-work-row" : "") + '">';
					workListHTML += ucm2lvCod;
					workListHTML += ucm2lvNam;
					workListHTML += ucm3lvNam;

					if (changedUtdTrcKey) {
						var rowspanCount = _.filter(data.resultList, function(el) {
							return el.ucmGolNo === ucmGolNo && el.utdTrcKey === utdTrcKey
						}).length;

						workListHTML += '<td class="'+ tdClass + " " + (isSa === "true" ? "sa-work" : "") +'" rowspan="' + rowspanCount + '">';

						if (isSa === "true") {
							// workListHTML += '<i class="fa fa-check-circle-o" aria-hidden="true"></i>';
						}

						workListHTML += '<a class="link-text" href="javascript:docPopup(' + utdTrcKey + ')">'+docNam+'</a>';
						workListHTML += '</td>';
					}

					if (changedUcmGolNo && loopCurrentUtdTrcKey === null) {
						workListHTML += '<td>-</td>';
					}

					workListHTML += '<td class="'+ tdClass +'">'+termGbn+'</td>';
					workListHTML += '<td class="'+ tdClass +'">'+utwDepNam+'</td>';

					workListHTML += '<td class="'+ tdClass +'">';
					workListHTML += '<a class="link-text" href="javascript:workPopup('+utwWrkKey+','+utdTrcKey+')">' + worker + '</a></td>';

					workListHTML += '<td>';
					workListHTML += '<div class="uplidFileList">';
					workListHTML += '<ul class="listArea listControl" style="width: 120px; height: auto; min-height: 22px; margin: 0;">';

					var hasFile = false;
					for(var j=0; j<data.fileList.length; j++) {
						if(utwWrkKey == data.fileList[j].umfConKey) {
							hasFile = true;
							workListHTML += '<li title="'+data.fileList[j].umfConFnm+'">';

							// if (isSa === "true") {
							if (true) {

								var matchedFiles = _.filter(saFiles, function (file) {
									return file.ctrKey == ucmCtrKey && file.umfConKey == utwWrkKey && data.fileList[j].umfFleKey == file.umfFleKey;
								});

								workListHTML += '<input type="checkbox" class="" ' + (matchedFiles.length > 0 ? 'checked="checked"' : '') +
										'data-service="' + service + '" data-control="' + ucmCtrKey + '" data-safile="' + data.fileList[j].umfFleKey + '">';

							} else {

								workListHTML += '<input type="checkbox" class="umfFleKey" alt="'+data.fileList[j].umfFleKey+'" name="umfFleKey"';
								if(data.fileList[j].umfMsrYn == "Y") {
									workListHTML += 'checked ="checked"';
								}
								workListHTML += 'value="'+data.fileList[j].umfFleKey+'">';

							}


							workListHTML += '<a href="javascript:getFile(\''+data.fileList[j].umfFleKey+'\')">'+data.fileList[j].umfConFnm+'</a>';
							workListHTML += '</li>';

						}
					}

					if ( ! hasFile) {
						workListHTML += "<li style='color: #ccc'>자료가 없습니다.</li>";
					}

					workListHTML += '</ul>';

			 		workListHTML += '</div></td>';
					workListHTML += '</tr>';
					workListHTML = replaceAll(workListHTML, 'null', '-');
					$obj.append(workListHTML);
				}

				// last cell
				$obj.find(".ucm2lvCod:last").attr("rowspan", arrRow[0]);
				$obj.find(".ucm2lvNam:last").attr("rowspan", arrRow[1]);
				$obj.find(".ucm3lvNam:last").attr("rowspan", arrRow[2]);

				// 부모 td 보다 height가 클 경우에만 scrollbar가 보여짐
				$(".ucmDescription").each(function() {
					var size = parseInt($(this).parent().attr("rowspan")) * 96 - 19; // td height: 96, title height: 19
					$(this).css("max-height", size + "px");
				});

				if(workListHTML == ""){
					workListHTML += '<tr class="last"><td class="last noDataList" colspan="9">검색결과가 없습니다.</td></tr>';
					$obj.append(workListHTML);
				}
		 }
	 });
}

function testView(wKey){
	window.open("", "testViewPopup", "width=380, height=250, menubar=no, location=no, status=no, scrollbars=yes");
	$("#wKey").val(wKey);
	document.testViewForm.submit();
}

function cntrPopup(cntrKey){
	window.open("","cntrPopup","width=730, height=500, menubar=no, location=no, status=no,scrollbars=yes");
	$("#ucmCtrKey").val(cntrKey);
	document.cntrPopupForm.submit();
}

function replaceAll(str,ori,rep){
	return str.split(ori).join(rep);
}

$.fn.rowspan = function(colIdx, isStats) {
    return this.each(function(){
        var that;
        $('tr', this).each(function(row) {
            $('td:eq('+colIdx+')', this).filter(':visible').each(function(col) {

                if ($(this).html() == $(that).html()
                    && (!isStats
                            || isStats && $(this).prev().html() == $(that).prev().html()
                            )
                    ) {
                    rowspan = $(that).attr("rowspan") || 1;
                    rowspan = Number(rowspan)+1;

                    $(that).attr("rowspan",rowspan);

                    // do your action for the colspan cell here
                    $(this).hide();

                    //$(this).remove();
                    // do your action for the old cell here

                } else {
                    that = this;
                }

                // set the that if not already set
                that = (that == null) ? this : that;
            });
        });
    });
};

</script>
</head>
<body>
	<form action="" name="fileDown" id="fileDown" method="post">
		<input type="hidden" name="downKey" id="downKey">
		<input type="hidden" name="where" id="where">
	</form>
	<form id="testViewForm" name="testViewForm" action="FM-INSPT001_popup.do" method="post" target="testViewPopup">
		<input type="hidden" id="wKey" name="wKey" value="">
	</form>
	<c:import url="/titlebar.do" />
	<form id="searchForm" name="searchForm" method="post">
	<input type="hidden" id="umfFleKey" name="umfFleKey" />
	<input type="hidden" id="ucmMsrDtl" name="ucmMsrDtl" />
		<div class="search">
		    <div class="defalt">
		        <fieldset class="searchForm">
		            <legend>기본검색</legend>
		            <ul class="detail">
		        	    <li>
	                	    <span class="title"><label for="standard">컴플라이언스</label></span>
		                    <sb:select name="standard" type="" code="STND"/>
						</li>
	               		<li>
		                    <span class="title"><label for="service">서비스</label></span>
		                    <sb:select name="service" type="S" value="SC007" allText="선택"/>
		                </li>
		                <li>
		                	<span class="title"><label for="ucm1lvCod">통제분야</label></span>
							<select id="ucm1lvCod" class="selectBox" name="ucm1lvCod">
								<option value="">전체</option>
							</select>
						</li>
		            </ul>
		            <button onclick="search(); return false;" class="btnSearch">검색</button>
		        </fieldset>
		    </div>
		</div>
	</form>

	<div class="contents">
		<div class="topBtnArea">
			<!-- <ul class="btnList">
				<li><button onclick="sav(); return false;"><span class="icoSave"></span>저장</button></li>
				<li><button onclick="fileDown(); return false;"><span class="icoDown"></span>파일다운로드</button></li>
			</ul> -->
		</div>
		<div class="talbeArea">
		<form id="listForm" name="listForm" method="post">
			<table summary="심사문서관리 현황을 리스트 선택, 항목번호, 점검항목, 업무명(양식서), 증적파일명, 업무주기 등의 항목으로 설명하고 있습니다."><!-- style="width:700px"> -->
				<colgroup>
					<col width="50px">
					<col width="130px">
					<col width="">
					<col width="100px">
					<col width="70px">
					<col width="110px">
					<col width="80px">
					<col width="120px">
				</colgroup>
				<thead>
					<tr>
						<th scope="col">통제<br>항목<br>번호</th>
						<th scope="col">통제항목</th>
						<th scope="col">통제점검</th>
						<th scope="col">업무명</th>
						<th scope="col">업무<br>주기</th>
						<th scope="col">부서</th>
                      	<th scope="col">담당자</th>
                      	<th scope="col" class="last">증적자료</th>
					</tr>
				</thead>
				<tbody id="workList">
					<tr>
						<td colspan="8" class="last noDataList">검색 조건 선택 후 검색해주십시요.</td>
					</tr>
				</tbody>
			</table>
		</form>
		</div>
	</div>
</body>
</html>