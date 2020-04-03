<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/include/contents.jsp"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<script type="text/javascript" src="/common/js/jquery.cookie.js"></script>
<script type="text/javascript">
var isEmtpyWorkList= true;
var arrDtl = {};
$(document).ready(function() {
	loadingBarSet();	// loding bar
	getCtrCode();
	$("#standard").change(function() {
		getCtrCode();
	});

	// [운영 현황] 수정
	$("#tbody").on("focus", ".ucmEtc", function() {
		$(this).css("height", 94).css("min-height", 94);
		$(this).parent().find(".btnSaveEtc").show();
	});
	$("#tbody").on("click", ".btnSaveEtc", function() {
		$this = $(this);
		$ucmEtc = $this.parent().find(".ucmMsrDtl");
		var postData = { ucmCtrKey: $ucmEtc.attr("alt"), ucmSvcCod: $("#ucmSvcCod").val(), ucmMsrDtl : $ucmEtc.val() }
		$.post("FM-INSPT004_SAV.do", postData, function(data) {
		}, "json");
		$this.hide();
		$ucmEtc.css("height", 120).css("min-height", 120);
		$ucmEtc.effect("highlight", 500);
	});

	// tooltip
	$("#tbody").tooltip();
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
		 }
	 });
}

function search() {		// QR_MWORK008_A -> 증적자료 select

	if(!$("#standard").val()) {
		alert("컴플라이언스를 선택해주세요.");
		return;
	}
	if(!$("#service").val()) {
		alert("서비스를 선택해주세요.");
		return;
	}

	// 2018-03-22 s,
	$("#ucmSvcCod").val($("#service").val());

	arrDtl = {};
	$("#ucmMsrDtl").val("");
	var $obj = $("#tbody");
 	$obj.empty();

	$("#searchForm").ajaxSubmit({
		url			: "${pageContext.request.contextPath}/inspt/FM-INSPT004_getCntrWork.do",
		type		: "post",
		data		: $("#searchForm").serialize(),
		dataType	: "json",
		success		: function(json) {
			var arrRow = Array();
			var workListHTML = "";
			var data = json.resultList;
			var file = json.fileList;
			var saFile = json.saFile;
		 	for(var i  = 0; i < data.length; i++) {
		 		var currentCtrKey = data[i].ucmCtrKey;
		 		var saFileOfCurrentControl = _.filter(saFile, function (file) {
					return file.ctrKey == currentCtrKey;
				});

				workListHTML = "";
				// 속도문제로 rowspan() 대처함
				if (i > 0 && data[i-1].ucm2lvCod == data[i].ucm2lvCod) {
					ucm2lvCod = '';
					arrRow[0]++;
				} else {
					ucm2lvCod = '<td class="ucm2lvCod">'+data[i].ucm2lvCod+'</td>';
					$obj.find(".ucm2lvCod:last").attr("rowspan", arrRow[0]);
					arrRow[0] = 1;
				}
				if (i > 0 && data[i-1].ucm2lvNam == data[i].ucm2lvNam) {
					ucm2lvNam = '';
					arrRow[1]++;
				} else {
					ucm2lvNam = '<td class="ucm2lvNam">'+data[i].ucm2lvNam+'</td>';
					$obj.find(".ucm2lvNam:last").attr("rowspan", arrRow[1]);
					arrRow[1] = 1;
				}

				var last = "";
				if (data.length == (i+1)) {
					last = 'class="last"';
				}

				var ucmMsrDtl = data[i].ucmMsrDtl ? data[i].ucmMsrDtl : '';
				var ucm3lvNam = data[i].ucm3lvNam ? data[i].ucm3lvNam : '';
				workListHTML += '<tr '+ last +'>';
				workListHTML += ucm2lvCod;
				workListHTML += ucm2lvNam;
				workListHTML += '<td class="ucm3lvNam"><div class="ucmTitle">'+data[i].ucm3lvCod+'</div><div class="ucmDescription">'+ucm3lvNam.replace(/\n/gi, '<br/>')+'</div></td>';
				workListHTML += '<td class="fontRight verticalTop"><textarea class="ucmMsrDtl txtAreaType02 ucmEtc" alt="'+data[i].ucmCtrKey+'" maxlength="1500" style="width:200px; min-width: 100px; max-height: 1000px;" >' + ucmMsrDtl + '</textarea>';
				workListHTML += '<button type="button" class="btnSaveEtc">저장</button></td>';
				workListHTML += '<td class="last"><div class="uplidFileList"><ul class="listArea" style="width: 250px; height: auto; min-height: 120px; margin: 0;">';

				// 2016-11-01
				/*
				if (data[i].wrkKeys) {
					var wrkKeys = data[i].wrkKeys.split(",");
					for (var j = 0; j < wrkKeys.length; j++) {
						for (var k = 0; k < file.length; k++) {
							if (wrkKeys[j] == file[k].umfConKey) {
								workListHTML += '<li title="'+file[k].umfConFnm+'"><a href="javascript:;" class="hiddenLink" onclick="viewDoc('+file[k].umfFleKey+')">'+file[k].umfConFnm+'</a></li>';
							}
						}
					}
				}
				*/

				/* SA 증적파일 추가 */
				for (var k = 0; k < saFileOfCurrentControl.length; k++) {
					// workListHTML += '<li><a href="/common/getFileDown.do?downKey=' + saFileOfCurrentControl[k].fleKey + '"><i class="fa fa-plus" style="color: red; margin-right: 0.3rem" aria-hidden="true"></i> ' + saFileOfCurrentControl[k].conFnm + '</a></li>';
					workListHTML += '<li>';
					workListHTML += '<a href="/common/getFileDown.do?downKey=' + saFileOfCurrentControl[k].fleKey + '">';
					console.log(saFileOfCurrentControl[k]);
					if (saFileOfCurrentControl[k].isSa == 'true') {
						workListHTML += '<span style="color: #ef8009 !important">' + saFileOfCurrentControl[k].conFnm + '</span>'
					} else {
						workListHTML += saFileOfCurrentControl[k].conFnm;
					}


					workListHTML += '</a>';
					workListHTML += '</li>';
				}

				workListHTML +=	'</ul></div></td>';
				workListHTML +=	'</tr>';
				$obj.append(workListHTML);
			}

			// last cell
			$obj.find(".ucm2lvCod:last").attr("rowspan", arrRow[0]);
			$obj.find(".ucm2lvNam:last").attr("rowspan", arrRow[1]);
			//$obj.find(".ucm3lvNam:last").attr("rowspan", arrRow[2]);

			// 부모 td 보다 height가 클 경우에만 scrollbar가 보여짐
			$(".ucmDescription").each(function() {
				var size = $(this).parent().height() - 30; // td height: 126, title height: 19
				$(this).css("max-height", size + "px");
			});

			// 2018-05-16 s,
			$(".ucmEtc").each(function() {
				var h = $(this).parent().height() - 12;
				$(this).height(h + "px");
			});

			if(workListHTML == "") {
				workListHTML += '<tr class="last"><td class="last noDataList" colspan="5">검색결과가 없습니다.</td></tr>';
				$obj.append(workListHTML);
			}
			
			checkEmptyWorkList(data);
		 }
	 });

}

function reportDown(){
	if(!$("#standard").val() || !$("#service").val()) {
		alert("컴플라이언스와 서비스를 선택해주세요.");
		return;
	}
	window.open("","reportDown","width=1024, height=720, menubar=no, location=no, status=no,scrollbars=yes");
	$('#searchForm').attr('target','reportDown').attr('action','${pageContext.request.contextPath}/report/FM-INSPT004.do').submit();
	$('#searchForm').attr('target','');
}

function viewDoc(key) {
	$("#umfFleKey").val(key);
	window.open("","reportDown","menubar=no, location=no, status=no,scrollbars=yes");
	$('#searchForm').attr('target','reportDown').attr('action','FM-INSPT004_viewDoc.do').submit();
	$('#searchForm').attr('target','');
}

function downloadZip() {
	$("#searchForm").attr("action", "FM-INSPT004_downloadZip.do")
		.submit();
}

function excelDown(){
	if(getIsEmtpyWorkList()) {
		alert('증적자료가 존재할 경우에만 엑셀 다운로드를 할 수 있습니다.');
		return false;
	}
	
	document.searchForm.action = "${pageContext.request.contextPath}/excel/FM-INSPT004.do";
	document.searchForm.submit();
}

function excelUpload() {
	openPostPopup("FM-INSPT004_excel_popup.do", 500, 300);
}

function checkEmptyWorkList(data) {
	if(!data.length) {
		isEmtpyWorkList = true;
	}
	else {
		isEmtpyWorkList = false;
	}
}

function getIsEmtpyWorkList() {
	return isEmtpyWorkList;
}
</script>
</head>
<body>
<form id="searchForm" name="searchForm" method="post">
<input type="hidden" id="umfFleKey" name="umfFleKey" />
<input type="hidden" id="ucmMsrDtl" name="ucmMsrDtl" />
<input type="hidden" id="ucmSvcCod" name="ucmSvcCod" />
	<c:import url="/titlebar.do" />
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
		<ul class="btnList">
			<li><button onclick="downloadZip(); return false;"><span class="icoDown"></span>증적파일다운로드</button></li>
			<li><button onclick="reportDown(); return false;"><span class="icoDown"></span>PDF다운로드</button></li>
			<li><button onclick="excelUpload()"><span class="icoExl"></span>엑셀업로드</button></li>
			<li><button onclick="excelDown()"><span class="icoExl"></span>엑셀다운로드</button></li>
		</ul>
	</div>
	<div class="talbeArea">
		<table summary="통제항목설정">
			<colgroup>
				<col width="60px">
				<col width="">
				<col width="300px">
				<col width="200px">
				<col width="250px">
			</colgroup>
			<caption>통제항목설정 현황</caption>
			<thead>
			    <tr>
					<th scope="col">통제<br/>항목<br/>번호</th>
					<th scope="col">통제항목</th>
					<th scope="col">통제점검</th>
					<th scope="col">운영현황</th>
					<th scope="col" class="last">증적자료</th>
			    </tr>
			</thead>
			<tbody id="tbody">
				<tr>
					<td colspan="5" class="last noDataList">검색 조건 선택 후 검색해주십시요.</td>
				</tr>
 			</tbody>
		</table>
    </div>
</div>
</body>
</html>