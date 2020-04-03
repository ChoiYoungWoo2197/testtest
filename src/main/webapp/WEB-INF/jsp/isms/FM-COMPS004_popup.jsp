<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
<%@ taglib prefix="ce" uri="http://ckeditor.com"%>
<%@ include file="/WEB-INF/include/contents.jsp"%>
<%@ include file="/WEB-INF/include/head.jsp"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<META http-equiv="X-UA-Compatible" content="IE=edge">
<title>SK broadband ISAMS</title>
<link rel="stylesheet" type="text/css" href="/common/jqGrid/css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="/common/css/jquery-ui.css"/>
<link rel="stylesheet" type="text/css" href="/common/css/reset.css"/>
<link rel="stylesheet" type="text/css" href="/common/css/common.css"/>
<link rel="stylesheet" type="text/css" href="/common/css/pop.css"/>
<link rel="stylesheet" type="text/css" href="/common/css/sub.css"/>
<link rel="stylesheet" type="text/css" href="/common/css/jquery-ui.css"/>
<link rel="stylesheet" type="text/css" href="/common/css/tree/jquery.treeview.css" />
<!--[if IE]> <script src="/common/js/html5.js"></script> <![endif]-->
<script type="text/javascript" src="/common/js/jquery.js"></script>
<script type="text/javascript" src="/common/js/jquery.form.js"></script>
<script type="text/javascript" src="/common/js/jquery.metadata.js"></script>
<script type="text/javascript" src="/common/js/jquery.MultiFile.js"></script>
<script type="text/javascript" src="/common/js/jquery.blockUI.js"></script>
<script type="text/javascript" src="/common/js/jquery-ui.js"></script>
<script type="text/javascript" src="/common/jqGrid/js/i18n/grid.locale-kr.js"></script>
<script type="text/javascript" src="/common/jqGrid/js/jquery.jqGrid.js"></script>
<script type="text/javascript" src="/common/js/ismsUI.js"></script>
<script type="text/javascript" src="/common/js/common.js"></script>
<script  type="text/javascript" src="/common/js/tree/jquery.treeview.js"></script>
<script type="text/javascript" src="/common/js/lodash.js"></script>
<script type="text/javascript">
var man_end = '<%= SES_MAN_END %>';
var man_std = '<%= SES_MAN_STD %>';
var changeStatus = false;
	$(document).ready(function() {
		loadingBarSet();	// 로딩바 적용

		var sDate = $.datepicker.parseDate("yymmdd", man_std);
		var eDate = $.datepicker.parseDate("yymmdd", man_end);
		var sDateStr = $.datepicker.formatDate("yy-mm-dd", sDate);
		var eDateStr = $.datepicker.formatDate("yy-mm-dd", eDate);

		// 기준일
		$("#utdStrDat").datepicker({
			changeMonth: true,
	        changeYear: true,
	        showButtonPanel: true,
			maxDate : eDate,
			minDate : sDate
		});
		$( "#utdStrDat_btn" ).click(function(){
	         $( "#utdStrDat" ).focus()
	     });
		// 결재일
		$("#utdEndDat").datepicker({
			changeMonth: true,
	        changeYear: true,
	        showButtonPanel: true,
			maxDate : eDate,
			minDate : sDate
		});
		$( "#utdEndDat_btn" ).click(function(){
	         $( "#utdEndDat" ).focus()
	     });
		$("#utdCmpDat").datepicker({
			changeMonth: true,
	        changeYear: true,
	        showButtonPanel: true,
			maxDate : eDate,
			minDate : sDate
		});
		$("#utdCmpDat_btn").click(function() { $("#utdCmpDat").focus(); });
		$("#utdTrmGbn, #utdDtrGbn").change(function() { changeTrmGbn(); });
		$(".utdAprYn").change(function() { changeAprYn(); });

		getWorkerList();
		getCtrMapList();

		changeServiceType();
		changeTrmGbn();
		changeAprYn();


		if ($("#mode").val() == "update") {
			$("#wrkList").show();
			getWorkList();
		} else {
			$("#utdStrDat").val(sDateStr);
			$("#utdEndDat").val(eDateStr);
		}

		// 업무담당자 tree
		$("#btnOpenUser").click(function() {
			deptAjax();
		});

		// 중요한 상태정보 변경시
		$("#utdTrmGbn, #utdDtrGbn, #utdStrDat, #utdEndDat, #utdCmpDat, #utdSvcCod").change(function() {
			changeStatus = true;
		});
	});


	// 증적양식 저장
	function saveSD() {
		if ($("#mode").val() == "update" && $("#utdSvcCod option:selected").text().indexOf("전체") != -1) {
			alert("서비스가 전체인 상태에서 수정할 수 없습니다.");
			return false;
		}

		// 사용 -> 미사용시 메세지 띄우기
		var useyn = $(":input:radio[name=utdDelYn]:checked").val();
		var useynOld = $("#useYnOld").val();
		// 기존 useyn 값이 Y로 변경 되었을 경우
		if (useyn == "Y" && useynOld == "N") {
			if (confirm("미사용으로 변경 시 생성 된 모든 업무가 삭제됩니다.\n진행하시겠습니까?")) {
				deleteSampleDoc();
			}
		} else if (useyn == "Y" && useynOld == "Y") {
			alert("현재 미사용중인 증적양식 입니다.\n수정을 원하실 경우 \'사용\'으로 변경 후 수정하여 주세요.");
			return false;
		} else {
			insertSampleDoc();
		}
	}


	function insertSampleDoc() {
		if (!checkValue()) {
			return false;
		}
		for (instance in CKEDITOR.instances) {
	        CKEDITOR.instances[instance].updateElement();
		}
		var standardData = $("input[name=chkMap]").map(function() { return $(this).val(); }).get().join(",");
		var workerData = $("input[name=chkWorker]").map(function() { return $(this).val(); }).get().join(",");
		var data = Array();

		// 기존 getDataIDs 는 현재 보여지는 ROW 들에 대한 정보만 가지고 온다.
		// 페이지네이션을 적용하였기 때문에 전체 ROW 정보들을 가지고 와야 한다.
		// jqGrid 에서 그러한 메소드를 제공하고 있지 않기 때문에 오리지날 date에 바로 접근해 작업을 진행한다.
		var workList = $("#gridTb")[0].p !== undefined ? $("#gridTb")[0].p.data : [];
		for(var i = 0; i < workList.length; i++){
			data.push(workList[i].utwWrkKey);
		}
		var workData = data.join(",");

		$("#standardData").val(standardData);
		$("#workerData").val(workerData);
		$("#workData").val(workData);

		if (workerData && confirm("업무 담당자에게 미할당된 업무를 일괄 할당하시겠습니까?\n[취소]시 업무 변경내용만 저장됩니다.")) {
			$("#addWork").val("true");
		}

		$("#sampleDocInputForm").ajaxSubmit({	// IE8 Issue: dataType : "json", multipart/form-data
			url : "${pageContext.request.contextPath}/comps/FM-COMPS004_sampleDoc_save.do",
			dataType : "json",
			success : function(data) {
				if(data.nonWorkYn == "Y"){
					alert("해당 날짜는 \n[ "+data.msg+" ]\n사유로 휴무입니다.");
					return false;
				}
				alert("저장 되었습니다.");
				$("#utdTrcKey").val(data.result);
				if ($("#mode").val() == "insert") {
					opener.location.reload(true);
				}
				location.href = "FM-COMPS004_popup.do?utdTrcKey="+ $("#utdTrcKey").val();
			},
			error : function(data) {
				$("#loddingBarDiv").hide();
				alert("error :: 증적양식 저장에 실패했습니다.");
			}
		});
	}

	function deleteSampleDoc() {
		$("#sampleDocInputForm").ajaxSubmit({	// IE8 Issue: dataType : "json", multipart/form-data
			url : "${pageContext.request.contextPath}/comps/FM-COMPS004_sampleDoc_save.do",
			async : false,
			dataType : "json",
			success : function(data) {
				$("#utdTrcKey").val(data.result);
				alert("저장 되었습니다.");
				location.href = "FM-COMPS004_popup.do?utdTrcKey="+ $("#utdTrcKey").val();
			},
			error : function(data) {
				alert("error :: 증적양식 저장에 실패했습니다.");
			}
		});
	}


	// checkValue
	function checkValue() {
		var docName = $("#utdDocNam").val();
		var startDate = $("#utdStrDat").val();
		var endDate = $("#utdEndDat").val();

		var utdTrmGbn = $("#utdTrmGbn").val();
		var utdDtrGbn = $("#utdDtrGbn").val();	// 2016-10-28
		if (utdTrmGbn == "N" || utdTrmGbn == "Y" || utdDtrGbn == "Y") {
			var cmpDate = $("#utdCmpDat").val();
		} else {
			var cmpDate = $("#utdCmpDat").val($("#utdEndDat").val());
		}

		var docGbn = $("#utdDocGbn").val();
		var svcCod = $("#utdSvcCod").val();

		// 신규 첨부파일
		var file = $("#b_file").length;
		// 기존 첨부파일
		var fileList = "${fn:length(fileVOList)}";
		var mode = $("#mode").val();

		// 문서명 체크
		if (docName == "") {
			alert("문서명은 필수 입력 항목 입니다.");
			$("#utdDocNam").focus();
			return false;
		}

		if (!$.isNumeric($("#utdDocCnt").val())) {
			alert("필요증적 갯수를 숫자로 입력해주세요.");
			$("#utdDocCnt").focus();
			return false;
		}

		// 업무시작일 체크
		if (startDate == "") {
			alert("업무시작일은 필수 입력 항목 입니다.");
			$("#utdStrDat").focus();
			return false;
		}
		// 업무종료일 체크
		if (endDate == "") {
			alert("업무종료일은 필수 입력 항목 입니다.");
			$("#utdEndDat").focus();
			return false;
		}
		// 업무수행일 체크
		if (cmpDate == "") {
			alert("수행일은 필수 입력 항목 입니다.");
			$("#utdCmpDat").focus();
			return false;
		}
		return true;
	}


	// 첨부파일 다운로드
	function getFile(key) {
		$("#downKey").val(key);
		document.fileDown.action = "${pageContext.request.contextPath}/common/getFileDown.do";
		document.fileDown.submit();
	}

	// 첨부파일 삭제
	function delFile_U(key) {

		$.ajax({
			url : "${pageContext.request.contextPath}/common/delFile.do",
			type : "post",
			data : {
				"key" : key
			},
			dataType : "json",
			success : function(e) {
				alert("삭제되었습니다.");
				$("#" + key).remove();
				var cnt = parseInt($("#fileCnt").text()) - 1;
				$("#fileCnt").text(cnt);
			},
			error : function() {
				alert("실패");
			},
			complete : function() {
			}
		});
	}

	function changeServiceType(){
		var svc = $("#utdSvcCod").val();
		if(svc == ''){
			$("#workerMappingList").hide();
			$("#cntrMappingList").hide();
		}else{
			$("#workerMappingList").show();
			$("#cntrMappingList").show();
		}
	}

	function changeAprYn() {
		var utdAprYn = $(':radio[name="utdAprYn"]:checked').val();
		if (utdAprYn == "Y") {
			$("#utdAppLev").show();
		} else {
			$("#utdAppLev").hide();
		}
	}

	function changeTrmGbn() {
		var utdTrmGbn = $("#utdTrmGbn").val();
		var utdDtrGbn = $("#utdDtrGbn").val();	// 2016-10-28
		if (utdTrmGbn == "N" || utdTrmGbn == "Y" || utdDtrGbn == "Y") {
			$("#cmpDat").show();
			$("#utdEndDat").addClass("readonly");
		} else {
			$("#cmpDat").hide();
			$("#utdEndDat").removeClass("readonly");
		}
	}


	/*
		통제항목
	*/
	// 연결된 통제항목 리스트
	function getCtrMapList(){
		if($("#mode").val() == "update"){
			$("#cntrMapBtn").show();
			$("#cntrMap").show();
		}else{
			$("#cntrMapBtn").hide();
			$("#cntrMap").hide();
		}

		var trcKey = ${utdTrcKey}+"";
		$.ajax({
			 url:"${pageContext.request.contextPath}/comps/FM-COMPS004_getCtrMapList.do",
			 type		: "post",
			 data		: {"trcKey":trcKey},
			 dataType	: "json",
			 success	: function(data){
				 var listHTML = "";
					for(var i=0; i<data.result.length; i++) {
						listHTML += '<tr>';
						listHTML += '<td><input type="checkbox" value="'+data.result[i].ucmCtrKey+'" name="chkMap" class="chkNode01"></td>';
						listHTML += '<td>'+data.result[i].uccSndNam+'</td>';
						listHTML += '<td>'+data.result[i].ucm1lvCod+'</td>';
						listHTML += '<td>'+data.result[i].ucm2lvCod+'</td>';
						listHTML += '<td>'+data.result[i].ucm3lvCod+'</td>';
						listHTML += '<td class="last">'+data.result[i].ucm3lvNam+'</td>';
						listHTML += '</tr>';
					}
					if(listHTML == ""){
						listHTML += '<tr><td class="last" colspan="6" height="50">연결된 통제항목이 없습니다.</td></tr>';
					}
					$("#standardList").html(listHTML);
			 },
			 error : function(){
				 alert('error');
			 }
		 });
	}

	// 통제항목 선택 레이어
	function getDepthList(num){
		$("#ucmCtrKey").val("");
		if(num == "1"){
			$("#depth1").html('<option value="">목적 선택 </option>');
			$("#depth2").html('<option value="">항목 선택 </option>');
			$("#depth3").html('<option value="">점검 선택 </option>');
		} else if(num == "2") {
			$("#depth2").html('<option value="">항목 선택 </option>');
			$("#depth3").html('<option value="">점검 선택 </option>');
		} else if(num == "3") {
			$("#depth3").html('<option value="">점검 선택 </option>');
		}

		var svc = $("#utdSvcCod").val();

		var option ='';
		$.ajax({
			 url		: "${pageContext.request.contextPath}/comps/GetDepth.do",
			 type		: "post",
			 data		: {"code": num , "standard" :  $("#popStandard").val(), "depth1" : $("#depth1").val() , "depth2" : $("#depth2").val() , "depth3" : $("#depth3").val(), "svc" : svc},
			 dataType	: "json",
			 success	: function(data){
				 if(num == "4"){
					 $("#ucmCtrKey").val(data.result[0].ucmCtrKey);
					 $("#ucm3lvNam").html(data.result[0].ucm3lvNam);
					 $("#ucm3lvDtl").html(data.result[0].ucm3lvDtl);
					 $("#ucm2lvDtl").html( data.result[0].ucm2lvDtl);
				 }else{
					setSelectBox(data, num);
					if(num == "1"){
						$("#standardNam").html($("#popStandard > option:selected").text());
						$("#ucm1lvCod").html("");
						$("#ucm1lvNam").html("");
						$("#ucm3lvNam").html("");
						$("#ucm3lvDtl").html("");
						$("#ucm2lvNam").html("");
						$("#ucm2lvDtl").html("");
					}
					if(num == "2"){
						$("#ucm1lvCod").html(data.result[0].code);
						$("#ucm1lvNam").html(data.result[0].ucm1lvNam);
						$("#ucm3lvNam").html("");
						$("#ucm3lvDtl").html("");
						$("#ucm2lvNam").html("");
						$("#ucm2lvDtl").html("");
					}
					if(num == "3"){
						$("#ucm2lvNam").html(data.result[0].ucm2lvNam);
						$("#ucm3lvNam").html("");
						$("#ucm3lvDtl").html("");
						$("#ucm2lvDtl").html("");
					}
				 }
			 },
			 error : function(){
				 alert('error' + id);
			 }
		 });
	}

	function setSelectBox(data, num){
		var option ='';
		if(num == '1') {
			option = '<option value="A">목적 선택 </option>';
		} else if(num == '2') {
			option = '<option value="">항목 선택 </option>';
		} else if(num == '3') {
			option = '<option value="">점검 선택 </option>';
		}
		for(var i=0; i < data.result.length; i++) {
			cname = cutString(data.result[i].cname, 30);
			option += '<option value="' + data.result[i].code+ '">' + data.result[i].code + ' ' + cname + '</option>';
		}

		if(num == '1') {
			$("#depth1").html(option);
		} else if(num == '2') {
			$("#depth2").html(option);
		} else if(num == '3') {
			$("#depth3").html(option);
		}

	}

	// 버튼 - 통제항목 추가
	function addStandard() {
		var ctrKey = $("#ucmCtrKey").val();
		if (!ctrKey) {
			alert("추가할 통제항목을 선택해주세요.");
			return false;
		}
		var chkFlag = false;
		$(".chkNode01").each(function() {
			if ($(this).val() == ctrKey) {
				alert("이미 추가되어 있는 통제항목 입니다.");
				chkFlag = true;
				return false;
			}
		});
		if (chkFlag) {
			return false;
		}
		var listHTML = '<tr>'
					 + '<td><input type="checkbox" value="'+ctrKey+'" name="chkMap" class="chkNode01"></td>'
					 + '<td>'+$("#standardNam").text()+'</td>'
					 + '<td>'+$("#depth1").val()+'</td>'
					 + '<td>'+$("#depth2").val()+'</td>'
					 + '<td>'+$("#depth3").val()+'</td>'
					 + '<td class="last">'+$("#ucm3lvNam").text()+'</td>'
					 + '</tr>';
		$("#btnCloseStandard").click();
		if ($(".chkNode01").size() == 0) {
			$("#standardList").empty();
		}
		$("#standardList").append(listHTML);
	}

	// 버튼 - 통제항목 삭제
	function delStandard() {
		if (!$(".chkNode01").is(":checked")) {
			alert("선택된 통제항목이 없습니다.");
			return false;
		}
		$(".chkNode01").each(function() {
			if ($(this).is(":checked")) {
				$(this).parent().parent().hide("fast").empty();
			}
		});
		if ($(".chkNode01").size() == 0) {
			$("#standardList").append('<tr><td class="last" colspan="6" height="50">연결된 통제항목이 없습니다.</td></tr>');
		}
	}
	/*
		//통제항목
	*/


	/*
		담당자 지정
	*/
	// 지정된 업무담당자 리스트
	function getWorkerList() {
		$.ajax({
			url		: "${pageContext.request.contextPath}/comps/FM-COMPS004_workerList.do",
			type		: "post",
			data		: {"utdTrcKey":$("#utdTrcKey").val()},
			dataType	: "json",
			success	: function(data){
				var workerList = "";
				for(var i=0; i<data.result.length; i++) {
					workerList += '<tr>';
					workerList += '<td>';
					workerList += '	<input type="checkbox" name="chkWorker" class="chkNode02" value="'+data.result[i].uumUsrKey+'" >';
					workerList += '	<input type="hidden" name="utmWrkId" value="'+data.result[i].uumUsrKey+'">';
					workerList += '</td>';
					workerList += '<td>'+data.result[i].service+'</td>';
					workerList += '<td>'+data.result[i].dept+'</td>';
					workerList += '<td>'+data.result[i].uumUsrId+'</td>';
					workerList += '<td>'+data.result[i].uumUsrNam+'</td>';
					workerList += '<td class="last">'+data.result[i].pos+'</td>';
					workerList += '</tr>';
				}
				workerList = workerList.replace(/null/gi, '-');
				if(data.result.length == 0){
					workerList += '<tr><td class="last" colspan="7" height="50">지정된 담당자가 없습니다.</td></tr>';
				}
				$("#workerList").html(workerList);
			 },
			 error : function(){
				 alert('error');
			 }
		 });
	}

	// 담당자 지정 레이어
	function deptAjax(){
		$.ajax({
			type : "POST"
			, async : true
			, url : "${pageContext.request.contextPath}/comps/FM-COMPS004_dept.do" //Request URL
			, timeout : 30000
			, cache : false
			, data : $('#treeForm').serialize()
			, dataType : "json"
			, contentType: "application/x-www-form-urlencoded; charset=UTF-8"
			, error : function(request, status, error) {
				alert("code : " + request.status + "\r\nmessage : " + request.reponseText);
			}
			, success : function(response, status, error) {
				var jsTreeStr = '';
				for(var i = 0; i < response.result.length; i++) {
					var orgNM = response.result[i].udmDepNam;
					var orgId = response.result[i].udmDepCod;
					var lvl = response.result[i].udmDepLvl;
					var nLvl = 0;
					if(i < response.result.length - 1) {
						nLvl = response.result[i+1].udmDepLvl;
					} else {
						nLvl = 0;
					}

					if(lvl < nLvl) {
						jsTreeStr += '<li class="expandable"><div class="hitarea expandable-hitarea"></div>';
					} else {
						jsTreeStr += '<li>';
					}

					var classStr = '';
					if(lvl < nLvl) {
						classStr = 'folder';
					} else {
						classStr = 'file';
					}

					jsTreeStr += '<span class="'+classStr+'" id="'+lvl+'"><a href="'+orgId+'" class="jsTreeData">'+orgNM+'</a></span>';

					if(lvl == 1 && nLvl == 1) {
						jsTreeStr += '</li>';
					} else if(lvl == 1 && nLvl == 2) {
						jsTreeStr += '<ul style="display: none;">';
					} else if(lvl == 2 && nLvl == 1) {
						jsTreeStr += '</li></ul></li>';
					} else if(lvl == 2 && nLvl == 2) {
						jsTreeStr += '</li>';
					} else if(lvl == 2 && nLvl == 3) {
						jsTreeStr += '<ul style="display: none;">';
					} else if(lvl == 3 && nLvl == 1) {
						jsTreeStr += '</li></ul></li></ul></li>';
					} else if(lvl == 3 && nLvl == 2) {
						jsTreeStr += '</li></ul></li>';
					} else if(lvl == 3 && nLvl == 3) {
						jsTreeStr += '</li>';
					} else if(lvl == 3 && nLvl == 4) {
						jsTreeStr += '<ul style="display: none;">';
					} else if(lvl == 4 && nLvl == 1) {
						jsTreeStr += '</li></ul></li></ul></li></ul></li>';
					} else if(lvl == 4 && nLvl == 2) {
						jsTreeStr += '</li></ul></li></ul></li>';
					} else if(lvl == 4 && nLvl == 3) {
						jsTreeStr += '</li></ul></li>';
					} else if(lvl == 4 && nLvl == 4) {
						jsTreeStr += '</li>';
					}
				}

				$('#browser').html(jsTreeStr);

				$("#browser").treeview({
					collapsed: true,
					animated: "fast",
					prerendered: true,
					persist: "location"
				});

				$('ul li:last-child').each(function() {
					if($(this).attr('class') == 'expandable') {
						$(this).attr('class', 'closed expandable lastExpandable').find('div:first').attr('class', 'hitarea lastExpandable-hitarea');
					}
				});

				$('.jsTreeData').bind('click', function(){
					var _data = $(this).attr('href');
					$.ajax({
						type : "POST"
						, async : true
						, url : "${pageContext.request.contextPath}/comps/FM-COMPS004_member.do" //Request URL
						, timeout : 30000
						, cache : false
						/* , data : {'dept':_data,'service':$('input[name=service]').val()} */
						, dataType : "json"
						, data : {'dept':_data}
						, contentType: "application/x-www-form-urlencoded; charset=UTF-8"
						, error : function(request, status, error) {
							alert("code : " + request.status + "\r\nmessage : " + request.reponseText);
						}
						, success : function(response, status, error) {
							$('#sehGridTb').empty();
							if(response.result.length > 0){
							for(var i = 0; i < response.result.length; i++) {
								var code = response.result[i].code;
								var uumUsrId = response.result[i].uumUsrId;
								var name = response.result[i].name;
								var uumSvcCod = response.result[i].uumSvcCod;
								var service = response.result[i].service;
								var uumDepCod = response.result[i].uumDepCod;
								var dept = response.result[i].dept;
								var pos = response.result[i].pos;
								var _row = "";

								_row += "<tr>";
								_row += "<td>";
								_row += "<input name=\"sehWorker\" class=\"chkNode03\" type=\"checkbox\" value=\"" + code + "\">";
								_row += "<input name=\"utmWrkId\" type=\"hidden\" value=\"" + code + "\">";
								_row += "<input name=\"utmSvcCod\" type=\"hidden\" value=\"" + uumSvcCod + "\">";
								_row += "<input name=\"utmDepCod\" type=\"hidden\" value=\"" + uumDepCod + "\">";
								_row += "</td>";
								_row += "<td>" + service + "</td>";
								_row += "<td>" + dept + "</td>";
								_row += "<td class=\"sehId\">" + uumUsrId + "</td>";
								_row += "<td>" + name + "</td>";
								_row += "<td class=\"last\">" + pos + "</td>";
								_row += "</tr>";

								$('#sehGridTb').append(_row);
							}
							}else{
								$('#sehGridTb').append('<tr class="last"><td class="last noDataList memLast" colspan="7">검색된 담당자가 없습니다.</td></tr>');
							}
						}
					});
					return false;
				});
				$('.jsTreeNoData').bind('click', function(){
					return false;
				});
			}
			, beforeSend: function() {
				var padingTop = (Number(($('#target').css('height')).replace("px","")) / 2) - 12;
				$('#loading').css('position', 'absolute');
				$('#loading').css('left', $('#target').offset().left);
				$('#loading').css('top', $('#target').offset().top);
				$('#loading').css('width', $('#target').css('width'));
				$('#loading').css('height', $('#target').css('height'));
				$('#loading').css('padding-top', padingTop);
				$('#loading').show().fadeIn('fast');
			}
			, complete: function() {
				$('#loading').fadeOut();
				$('#loading').css('border', 0).css('display', 'none').css('text-align', 'center').css('background', '#ffffff').css('filter', 'alpha(opacity=60)').css('opacity', 'alpha*0.6');
			}
		});
	}

	// 담당자 추가 레이어 담당자 검색
	function memberAjax(){
		$.ajax({
			type : "POST"
			, async : true
			, url : "${pageContext.request.contextPath}/comps/FM-COMPS004_member.do" //Request URL
			, timeout : 30000
			, cache : false
			, dataType : "json"
			, data : {'searchKeyword':$('input[name=searchKeyword]').val(),'service':$('input[name=service]').val()}
			, contentType: "application/x-www-form-urlencoded; charset=UTF-8"
			, error : function(request, status, error) {
				alert("code : " + request.status + "\r\nmessage : " + request.reponseText);
			}
			, success : function(response, status, error) {
				$('#sehGridTb').empty();
				if(response.result.length > 0){
				for(var i = 0; i < response.result.length; i++) {
					var code = response.result[i].code;
					var uumUsrId = response.result[i].uumUsrId;
					var name = response.result[i].name;
					var uumSvcCod = response.result[i].uumSvcCod;
					var service = response.result[i].service;
					var uumDepCod = response.result[i].uumDepCod;
					var dept = response.result[i].dept;
					var pos = response.result[i].pos;
					var _row = "";

					_row += "<tr>";
					_row += "<td>";
					_row += "<input name=\"sehWorker\" class=\"chkNode03\" type=\"checkbox\" value=\"" + code + "\">";
					_row += "<input name=\"utmWrkId\" type=\"hidden\" value=\"" + code + "\">";
					_row += "<input name=\"utmSvcCod\" type=\"hidden\" value=\"" + uumSvcCod + "\">";
					_row += "<input name=\"utmDepCod\" type=\"hidden\" value=\"" + uumDepCod + "\">";
					_row += "</td>";
					_row += "<td>" + service + "</td>";
					_row += "<td>" + dept + "</td>";
					_row += "<td class=\"memId\">" + uumUsrId + "</td>";
					_row += "<td>" + name + "</td>";
					_row += "<td class=\"last\">" + pos + "</td>";
					_row += "</tr>";

					$('#sehGridTb').append(_row);
				}
				}else{
					$('#sehGridTb').append('<tr class="last"><td class="last noDataList memLast" colspan="7">검색된 담당자가 없습니다.</td></tr>');
				}
			}
		});
		return false;
	}

	// 버튼 - 업무담당자 추가
	function addWorker() {
		if (!$(".chkNode03").is(":checked")) {
			alert("추가할 업무 담당자를 선택해주세요.");
			return false;
		}
		var chkFlag = false;
		$(".chkNode02").each(function() {
			var sel = $(this).val();
			$(".chkNode03:checked").each(function() {
				if ($(this).val() == sel) {
					$(this).prop("checked", false);
					chkFlag = true;
				}
			});
		});
		var listHTML = '';
		$(".chkNode03:checked").each(function() {
			var $tr = $(this).parent().parent();
			listHTML += '<tr>'
					 + '<td><input type="checkbox" name="chkWorker" class="chkNode02" value="'+$(this).val()+'" />'
					 + '<input type="hidden" name="utmWrkId" value="'+$(this).val()+'" /></td>'
					 + '<td>'+$tr.find("td:eq(1)").text()+'</td>'
					 + '<td>'+$tr.find("td:eq(2)").text()+'</td>'
					 + '<td>'+$tr.find("td:eq(3)").text()+'</td>'
					 + '<td>'+$tr.find("td:eq(4)").text()+'</td>'
					 + '<td class="last">'+$tr.find("td:eq(5)").text()+'</td>'
					 + '</tr>';
		});
		$("#btnCloseWorker").click();
		if ($(".chkNode02").size() == 0) {
			$("#workerList").empty();
		}
		$("#workerList").append(listHTML);

		if (chkFlag) {
			alert("이미 선택되어 있는 담당자를 제외하고 추가되었습니다.");
		}
		changeStatus = true;
	}

	// 버튼 - 담당자 삭제
	function delWorker() {
		if (!$(".chkNode02").is(":checked")) {
			alert("선택된 담당자가 없습니다.");
			return false;
		}
		$(".chkNode02").each(function() {
			if ($(this).is(":checked")) {
				$(this).parent().parent().hide("fast").empty();
			}
		});
		if ($(".chkNode02").size() == 0) {
			$("#workerList").append('<tr><td class="last" colspan="7" height="50">지정된 담당자가 없습니다.</td></tr>');
		}
	}
	/*
		//담당자 지정
	*/


	/*
		업무 현황
	*/
	function getWorkList() {
		var url = "${pageContext.request.contextPath}/comps/FM-COMPS004_getWorkList.do";
		$("#gridTb").jqGrid({
			url:url,
			postData : { trcKey: $("#utdTrcKey").val() },
			datatype : "json",
			mtype : "post",
			colNames:['업무코드', '담당자','업무상태','업무주기','업무시작일','업무마감일'],
			colModel:[
				{name:'utwWrkKey',	index:'utwWrkKey',	width: 80,	align:'center', sorttype:'number'},
				{name:'uumUsrNam',	index:'uumUsrNam',	width: 120,	align:'center'},
				{name: 'utwWrkSta', index: 'utwWrkSta', width: 90, align: 'center'},
				{name: 'uccTrmNam', index: 'uccTrmNam', width: 90, align: 'center'},
				{name: 'utwStrDat', index: 'utwStrDat', width: 100, align: 'center'},
				{name: 'utwEndDat', index: 'utwEndDat', width: 100, align: 'center'},
			],
			height: 150,		// 전체 grid 화면의 세로 사이즈 지정.
			pager : '#pager',
			rowNum : '20',
			rownumbers : true,	// 각 row의 맨 앞줄에 각 row의 번호가 자동적으로 부여된다.
			sortable : true,	// 초기 sort 조건과 관련되며 ture이면 초기 데이터가 정렬되서 보인다.
			sortname : 'utwWrkKey',	// 초기 디이터 정렬시 정렬조건, 서버에 정렬  조건으로 전송된다.
			sortorder : 'desc',	// 순방향, 역방향 정렬(desc, asc)
			viewrecords : true,	// 오른쪽 하단에 총 몇개중 몇번째꺼 표시
			loadonce : true,	// reload 여부이며, true로 설정시 한번만 데이터를 받아오고 그 다음부터는 데이터를 받아오지 않는다.
			multiselect: true,	// select box 적용
			loadtext : 'loading..',	// 서버연동시 loading 중이라는 표시가 뜨는데 그곳의 문자열 지정.
			jsonReader:{
				root: 'result',
				repeatitems: false
			},
			gridComplete: function () {

				// 저장된 선택 ROW 정보를 이용해 현재 보여지고 있는 ROW들의 체크박스 checked 여부를 적용한다.

				var selectedRowStorage = $(this).data("selectedRows");

				if (selectedRowStorage) {
					var rowIdsOfCurrentGridView = $(this).getDataIDs();
					for (var i = 0; i < rowIdsOfCurrentGridView.length; i++) {
						var rowIdOfCurrentLoop =rowIdsOfCurrentGridView[i];
						if (_.find(selectedRowStorage, {"_id_": rowIdOfCurrentLoop})) {
							$(this).setSelection(rowIdOfCurrentLoop)
						}
					}
				}

				// 전체 선택 체크박스를 사용하지 않는다.
				$("#jqgh_gridTb_cb").hide();

			},
			onSelectRow: function(id, status, e){

				// 선택된 ROW 정보를 담을 저장소가 없다면 새롭게 생성한다.
				if ($(this).data("selectedRows") === undefined) {
					$(this).data("selectedRows", [])
				}

				// 현재 선택된 ROW
				var selectedRow = $(this).getLocalRow(id);

				if (selectedRow === null || e === undefined) {
					// 오류처리
					return false;
				}

				// 선택된 ROW 정보를 담은 리스트
				var selectedRowStorage = $(this).data("selectedRows");

				// cbox 클래스를 가지고 있는 node 라면 checkbox node 라고 판단한다.
				var isCheckBox = $(e.target).hasClass("cbox");
				// 의미를 정확하게 표시하기 위해 의미있는 변수명에 다시 저장한다.
				var isChecked = status;

		  		if (isCheckBox) {
					// 체크박스에서 이벤트가 시작된 경우

					if (isChecked) {
						selectedRowStorage.push(selectedRow)
					} else {
						selectedRowStorage.splice(selectedRowStorage.indexOf(selectedRow), 1)
					}
				} else {
		  			// 체크박스가 아닌 다른 칼럼에서 이벤트가 시작된 경우

					var trcKey = $("#utdTrcKey").val();
					var workNum = selectedRow.utwWrkKey;

		  			$(this).setSelection(id, false);
		  			workPopup(workNum, trcKey);
		  		}

			}
		});
		// jqgrid 전체 넓이 지정, 가로 스크롤 생성
		$("#gridTb").setGridWidth(672, false);
	}

	// 업무 삭제
	function delWorkList() {

		var jqGrid = $("#gridTb");

		var selectedRowStorage = jqGrid.data("selectedRows");

		// 선택된 ROW 정보들을 이용해 jqGrid 의 오리지날 data 에서 해당 ROW 정보를 삭제한다.
		// 오리지날 data 에 바로 접근하는 이유는 관련 메소드를 지원하지 않기 때문이다.
		for (var i = 0; i < selectedRowStorage.length; i++) {
			var rowOfCurrentLoop = selectedRowStorage[i];
			var result = jqGrid[0].p.data.splice(jqGrid[0].p.data.indexOf(rowOfCurrentLoop), 1);
		}

		jqGrid.data("selectedRows", []);
		jqGrid.trigger("reloadGrid");
	}
	/*
		//업무현황
	*/
</script>
<style type="text/css">
#utdTrmGbn { width: 80px; }
#utdAppLev { width: 50px; }
</style>
</head>
<body>
	<div id="skipnavigation">
	    <ul>
	        <li><a href="#content-box">본문 바로가기</a></li>
	    </ul>
	</div>
	<div id="wrap" class="pop">
	    <header>
	        <div class="borderBox"></div>
	        <h1>수행업무설정</h1>
	    </header>
	    <form action="" name="fileDown" id="fileDown" method="post">
   			<input type="hidden" name="downKey" id="downKey">
   			<input type="hidden" name="where" id="where" value="doc">
   		</form>
   		<form id="sampleDocInputForm" name="sampleDocInputForm" method="post" enctype="multipart/form-data" >
	   		<input type="hidden" id="utdTrcKey"	name="utdTrcKey"	value="<c:out value="${utdTrcKey}"/>"/>
			<input type="hidden" id="utdBcyCod"	name="utdBcyCod"	value="<%=C_SES_MAN_CYL%>"/>
			<input type="hidden" id="utdRgtId"	name="utdRgtId"		value="<%=C_SES_USER_KEY%>"/>
			<input type="hidden" id="utdUptId"	name="utdUptId"		value=""/>
			<input type="hidden" id="mode"		name="mode"			value="<c:out value="${mode}"/>"/>
			<input type="hidden" id="useYnOld"	name="useYnOld"		value="<c:out value="${sampleDocVO.utdDelYn}"/>"/>
			<input type="hidden" id="aprlevOld"	name="aprlevOld"	value="<c:out value="${sampleDocVO.aprlevOld}"/>"/>
			<input type="hidden" id="standardData" name="standardData" />
			<input type="hidden" id="workerData" name="workerData" />
			<input type="hidden" id="workData" name="workData" />
			<input type="hidden" id="addWork" name="addWork" />
			<article class="cont" id="content-box">
				<div class="cont_container">
					<div class="contents">
						<div class="title">업무상세설정</div>
							<div class="talbeArea">
								<table summary="업무상세설정을 서비스, 문서명, 문서양식, 결재방법, 알림메일, 삭제여부, 업무주기, 문서설명  등을 확인/수정 할 수 있습니다.">
									<colgroup>
										<col width="15%">
										<col width="35%">
										<col width="15%">
										<col width="35%">
									</colgroup>
                                	<caption>업무상세설정</caption>
									<tbody>
										<tr>
											<th scope="row"><label for="utdSvcCod">서비스</label></th>
											<td class="fontLeft">
												<sb:select name="utdSvcCod" type="S" value="${sampleDocVO.utdSvcCod}" event="changeServiceType();"/>
											</td>
											<th scope="row"><label for="utdDocNam">업무명</label></th>
											<td class="last fontLeft">
												<input type="text" id="utdDocNam" name="utdDocNam" class="inputText" value="<c:out value="${sampleDocVO.utdDocNam}"/>" required="required" maxlength="200"/>
											</td>

										</tr>
										<tr>
											<th scope="row">알림메일</th>
											<td class="fontLeft">
												<input type="radio" id="utdSdmY" name="utdSdmYn" value="<c:out value="N"/>" <c:if test="${sampleDocVO.utdSdmYn == 'N' or empty sampleDocVO.utdSdmYn}">checked="checked"</c:if> /> <label for="utdSdmY">미발송</label>
												<input type="radio" id="utdSdmN" name="utdSdmYn" value="<c:out value="Y"/>" <c:if test="${sampleDocVO.utdSdmYn == 'Y'}">checked="checked"</c:if> /> <label for="utdSdmN">발송</label>
											</td>
											<th scope="row">결재방법</th>
											<td class="fontLeft last">
												<input type="radio" id="utdAprY" name="utdAprYn" class="utdAprYn" value="<c:out value="N"/>" <c:if test="${sampleDocVO.utdAprYn == 'N' or empty sampleDocVO.utdAprYn}">checked="checked"</c:if>/> <label for="utdAprY">자가결재</label>
												<input type="radio" id="utdAprN" name="utdAprYn" class="utdAprYn" value="<c:out value="Y"/>" <c:if test="${sampleDocVO.utdAprYn == 'Y'}">checked="checked"</c:if> /> <label for="utdAprN">승인결재</label>
												<sb:select name="utdAppLev" type="" code="SIGN_STEP" value="${sampleDocVO.utdAppLev}" />
											</td>
										</tr>
										<tr>
											<th scope="row">업무주기</th>
											<td class="fontLeft">
												<sb:select name="utdTrmGbn" type="" code="TERM" value="${sampleDocVO.utdTrmGbn}"/>
												<span id="cmpDat">
													<input type="text" id="utdCmpDat" name="utdCmpDat" class="inputText cal wdShort" style="width:100px;" value="<c:out value="${sampleDocVO.utdCmpDat}"/>" />
													<button id="utdCmpDat_btn" type="button"><span class="icoCal"><em>달력</em></span></button>
												</span>
											</td>
											<th scope="row">증적등록주기</th>
											<td class="last fontLeft">
												<sb:select name="utdDtrGbn" type="" code="DOCTERM" value="${sampleDocVO.utdDtrGbn}"/>
											</td>
										</tr>
										<tr>
											<th scope="row"><label for="utdDocCnt">필요증적갯수</label></th>
											<td class="fontLeft">
												<input type="text" id="utdDocCnt" name="utdDocCnt" class="inputText" style="width:30px;" value="<c:out value="${sampleDocVO.utdDocCnt}"/>" required="required" maxlength="2" /> 개
											</td>
											<th scope="row">사용여부</th>
											<td class="fontLeft last">
												<input type="radio" id="utdDelY" name="utdDelYn" value="<c:out value="N"/>" <c:if test="${sampleDocVO.utdDelYn == 'N' or empty sampleDocVO.utdDelYn}">checked</c:if> checked="checked" /> <label for="utdDelY">사용</label>
												<input type="radio" id="utdDelN" name="utdDelYn" value="<c:out value="Y"/>" <c:if test="${sampleDocVO.utdDelYn == 'Y'}">checked</c:if> /> <label for="utdDelN">미사용</label>
											</td>
										</tr>
										<tr>
											<th scope="row">업무기간</th>
											<td class="last fontLeft" colspan="3">
												<input type="text" id="utdStrDat" name="utdStrDat"	class="inputText cal" style="width: 100px;" value="<c:out value="${sampleDocVO.utdStrDat}"/>"required="required" />
												<button id="utdStrDat_btn" type="button"><span class="icoCal"><em>달력</em></span></button>
												~
												<input type="text" id="utdEndDat" name="utdEndDat" class="inputText cal" style="width: 100px;" value="<c:out value="${sampleDocVO.utdEndDat}"/>" required="required" />
												<button id="utdEndDat_btn" type="button"><span class="icoCal"><em>달력</em></span></button>
											</td>
										</tr>
										<tr>
											<th scope="row"><label for="utdDocGbn">문서유형</label></th>
											<td class="last fontLeft" colspan="3">
												<sb:select name="utdDocGbn" type="A" code="DGBN" allText="선택" value="${sampleDocVO.utdDocGbn}"/>
											</td>
										</tr>
										<tr>
											<th scope="row"><label for="utdDocEtc">업무설명</label></th>
											<td class="last fontLeft" colspan="3" style="height: 30px;">
												<%-- <input type="text" id="utdDocEtc" name="utdDocEtc" class="inputText" value="<c:out value="${sampleDocVO.utdDocEtc}"/>" required="required" maxlength="200"/> --%>
												<textarea id="utdDocEtc" class="txtAreaType03" name="utdDocEtc">${sampleDocVO.utdDocEtc}</textarea>
												<ce:replace replace="utdDocEtc" basePath="${pageContext.request.contextPath}/editor/ckeditor" ></ce:replace>
											</td>
										</tr>
									</tbody>
								</table>
								<div class="uplidFileList margin10">
									<p class="titleArea">문서템플릿 - 총 <strong id="fileCnt"><c:out value="${fn:length(fileVOList)}"/></strong>건</p>
									<ul class="listArea width100">
										<li><input type='file' class="multi" name='b_file' id ='b_file' maxlength="5" style='width: 300px'></li>
										<c:forEach var="fileVOList" items="${fileVOList}" varStatus="status">
										<li id="${fileVOList.umf_fle_key}">
											<a href="javascript:getFile(<c:out value='${fileVOList.umf_fle_key}'/>);"><span class="icoDown"></span><c:out value="${fileVOList.umf_con_fnm}" /></a>
											<a href="javascript:delFile_U(<c:out value='${fileVOList.umf_fle_key}'/>);" title="삭제" class="del">x<em class="hidden">삭제</em></a>
										</li>
										</c:forEach>
									</ul>
								</div>
							</div>
					</div>
					<div class="contents" id="cntrMappingList">
						<input type="hidden" id="ucmCtrKey"/>
						<div class="title">연결된 통제항목</div>
						<div class="talbeArea">
							<table summary="연결된 통제항목을 리스트 선택, 공통표준명, 통제목적, 통제항모그 통제점검, 통제항목 등을 확인 할 수 있습니다.">
								<colgroup>
									<col width="5%">
									<col width="15%">
									<col width="11%">
									<col width="11%">
									<col width="11%">
									<col width="*">
								</colgroup>
                                <caption>연결된 통제항목</caption>
								<thead>
									<tr>
										<th scope="row"><input type="checkbox" class="chkAll01" title="리스트 전체 선택"></th>
										<th scope="row">컴플라이언스</th>
										<th scope="row">통제목적</th>
										<th scope="row">통제항목</th>
										<th scope="row">통제점검</th>
										<th scope="row" class="last">점검항목</th>
									</tr>
								</thead>
								<tbody id="standardList">
								</tbody>
							</table>
							<div class="topBtnArea">
								<ul class="btnList">
									<li>
                                        <button type="button" id="btnOpenStandard" class="openPop"><span class="icoSynch"></span>통제항목 추가</button>

                                        <!-- 통제항목 추가 레이어팝업 -->
                                        <div id="addStandLayerPop" class="layerPop" style="display: none;">
                                            <div class="popTitle">
                                                <p>통제항목 연결 추가</p>
                                            </div>
                                            <div class="search">
                                                <div class="defalt">
                                                    <fieldset class="searchForm">
                                                        <legend>기본검색</legend>
                                                        <ul class="detail">
                                                            <li class="width100">
                                                                <span class="title"><label for="popStandard">표준</label></span>
								                                <sb:select name="popStandard" type="A" code="STND" allText="선택" event="getDepthList(1);"/>
                                                            </li>
                                                            <li class="last">
                                                                <span class="title">통제항목</span>
                                                                <select id="depth1" name="depth1" onchange="getDepthList(2);" class="selectBox" style="width: 210px;" title="항목 선택">
                                                                    <option>목적 선택</option>
                                                                </select>
                                                                 <select id="depth2" name="depth2" onchange="getDepthList(3);" class="selectBox" style="width: 210px;" title="항목 선택">
                                                                    <option>항목 선택</option>
                                                                </select>
                                                                 <select id="depth3" name="depth3" onchange="getDepthList(4);" class="selectBox" title="항목 선택">
                                                                    <option>점검 선택</option>
                                                                </select>
                                                            </li>
                                                        </ul>
                                                    </fieldset>
                                                </div>
                                            </div>
                                            <div class="layerContents" style="height:0px;">
                                                <div class="contents">
                                                    <div class="selectDetail">
                                                        <ul class="detailArea">
                                                            <li class="floatList">
                                                                <dl>
                                                                    <dt>표준명</dt>
                                                                    <dd id="standardNam"></dd>
                                                                </dl>
                                                            </li>
                                                            <li class="floatList">
                                                                <dl>
                                                                    <dt>통제점검</dt>
                                                                    <dd id="ucm1lvCod"></dd>
                                                                </dl>
                                                            </li>
                                                            <li>
                                                                <dl>
                                                                    <dt>통제목적</dt>
                                                                    <dd id="ucm1lvNam"></dd>
                                                                </dl>
                                                            </li>
                                                             <li>
                                                                <dl>
                                                                    <dt>통제항목</dt>
                                                                    <dd id="ucm2lvNam"></dd>
                                                                </dl>
                                                            </li>
                                                             <li>
                                                                <dl>
                                                                    <dt>상세내용</dt>
                                                                    <dd id="ucm2lvDtl"></dd>
                                                                </dl>
                                                            </li>

                                                            <li>
                                                                <dl>
                                                                    <dt>점검항목</dt>
                                                                    <dd id="ucm3lvNam"></dd>
                                                                </dl>
                                                            </li>
                                                            <li>
                                                                <dl>
                                                                    <dt>설명</dt>
                                                                	<dd id="ucm3lvDtl"></dd>
                                                                </dl>
                                                            </li>
                                                        </ul>
                                                    </div>

                                                </div>
                                            </div>
                                            <div class="centerBtnArea bottomBtnArea" style="position: relative; top: -20px;">
                                           		<ul class="btnList">
                                                   <li><button type="button" class="btnStrong" onclick="addStandard();"><span class="icoSave"></span>통제항목 추가</button></li>
                                               </ul>
                                            </div>
                                            <button type="button" id="btnCloseStandard" class="popClose"><span class="icoClose"><em>닫기</em></span></button>
                                        </div>
                                        <!-- //통제항목 추가 레이어팝업 -->
                                        <div id="popBG" class="popBG" style="display: none;"></div>
									</li>
									<li>
                                        <button type="button" onclick="delStandard();" ><span class="icoDel"></span>삭제</button>
									</li>
								</ul>
							</div>
						</div>
					</div>

					<div class="contents" id="workerMappingList">
						<div class="title">업무담당자</div>
						<div class="talbeArea">
							<div class="topBtnArea">
								<ul class="btnList">
									<li>
										<button type="button" id="btnOpenUser" class="openPop"><span class="icoPer"></span>담당자 추가</button>

                                        <!-- 업무담당자 추가 레이어팝업 -->
                                        <div id="userLayerPop" class="layerPop" style="display: none;">
                                            <div class="popTitle">
                                                <p>업무 담당자 지정</p>
                                            </div>
											<div class="cont_container">
												<div class="contents" style="width: 200px; padding: 10px 20px 0 0; float: left;">
													<div class="foldList" id="target">
														<div class="treeBox" style="height:530px;">
															<ul id="browser" class="filetree">
															</ul>
														</div>
													</div>
												</div>
												<div class="contents" style="float: left;width: 440px;">
													<div class="title">
														<div class="topBtnArea">
															<ul class="btnList">
								                                <li>
																	<input id="searchKeyword" name="searchKeyword" class="inputText" type="text" title="이름 입력" placeholder="담당자 이름" onkeypress="doNotEnter();" style="width:100px;">
																	<button type="button" onclick="memberAjax();" class="btnSearch defaltBtn">검색</button>
																</li>
															</ul>
														</div>
													</div>
													<div class="talbeArea" style="max-height: 468px; overflow-y: auto;">
														<table>
															<colgroup>
																<col width="5%">
																<col width="20%">
																<col width="*">
																<col width="15%">
																<col width="15%">
																<col width="15%">
															</colgroup>
							                                <caption>업무담당자</caption>
															<thead>
																<tr>
																	<th scope="row"><input type="checkbox" class="chkAll03" title="리스트 전체 선택"></th>
																	<th scope="row">서비스</th>
																	<th scope="row">부서</th>
																	<th scope="row">사번</th>
																	<th scope="row">이름</th>
																	<th scope="row" class="last">직급</th>
																</tr>
															</thead>
															<tbody id="sehGridTb">
																<tr class="last"><td class="last noDataList memLast" colspan="7">검색된 담당자가 없습니다.</td></tr>
															</tbody>
														</table>
													</div>
												</div>
											</div>
											<!-- //업무담당자 추가 레이어팝업 -->
											<div class="centerBtnArea bottomBtnArea" style="clear: both; margin-top: 50px;">
                                           		<ul class="btnList">
                                                   <li><button type="button" class="btnStrong" onclick="addWorker();"><span class="icoSave"></span>담당자 추가</button></li>
                                               </ul>
                                            </div>
                                            <button type="button" class="popClose" id="btnCloseWorker"><span class="icoClose"><em>닫기</em></span></button>
                                        </div>
                                        <div id="userPopBG" class="popBG" style="display: none;"></div>
                                    </li>
									<li><button type="button" onclick="delWorker();"><span class="icoDel"></span>삭제</button></li>
								</ul>
							</div>
							<table>
								<colgroup>
									<col width="5%">
									<col width="15%">
									<col width="">
									<col width="15%">
									<col width="20%">
									<col width="15%">
								</colgroup>
                                <caption>업무담당자</caption>
								<thead>
									<tr>
										<th scope="row"><input type="checkbox" class="chkAll02" title="리스트 전체 선택"></th>
										<th scope="row">서비스</th>
										<th scope="row">부서</th>
										<th scope="row">사번</th>
										<th scope="row">이름</th>
										<th scope="row" class="last">직급</th>
									</tr>
								</thead>
								<tbody id="workerList">
								</tbody>
							</table>
						</div>
					</div>
					<div class="contents" id="wrkList" style="display: none;">
						<div class="title">업무  현황</div>
						<div class="talbeArea">
							<div class="topBtnArea">
								<ul class="btnList">
									<li><button type="button" id="btnDelWork" onclick="delWorkList()"><span class="icoDel"></span>삭제</button></li>
								</ul>
							</div>
							<table id="gridTb">
							</table>
							<div id="pager"></div>
						</div>
					</div>
				</div>
				<div class="centerBtnArea bottomBtnArea">
					<button type="button" class="popClose close"><span class="icoClose">닫기</span></button>
                    <ul class="btnList">
                        <li>
                        	<button type="button" class="btnStrong" id="saveSampleDoc" onclick="saveSD()"><span class="icoSave"></span>저장</button>
   	                    </li>
       	            </ul>
	             </div>
			</article>
			</form>
	</div>
	<form id="postPopup" name="postPopup" method="post"></form>
</body>
</html>