<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="com.ckeditor.CKEditorConfig"%>
<%@ page import="com.uwo.isms.domain.WorkVO" %>
<%@ page import="com.uwo.isms.domain.WorkVO.statusType" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
<%@ taglib prefix="ce" uri="http://ckeditor.com"%>
<%@ taglib prefix="form" uri="/config/tlds/custom-taglib" %>
<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="/WEB-INF/include/head.jsp"%>
<%@ include file="/WEB-INF/include/contents.jsp"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<META http-equiv="X-UA-Compatible" content="IE=edge">
<title>SK broadband ISAMS</title>
<link rel="stylesheet" type="text/css" href="/common/css/reset.css" />
<link rel="stylesheet" type="text/css" href="/common/css/common.css" />
<link rel="stylesheet" type="text/css" href="/common/css/pop.css" />
<link rel="stylesheet" type="text/css" href="/common/css/sub.css" />
<!--[if IE]> <script src="/common/js/html5.js"></script> <![endif]-->
<script type="text/javascript" src="/common/js/jquery.js"></script>
<script type="text/javascript" src="/common/js/jquery.form.js"></script>
<script type="text/javascript" src="/common/js/ismsUI.js"></script>
<script type="text/javascript" src="/common/js/vue.js"></script>
<script type="text/javascript" src="/common/js/lodash.js"></script>
<script type="text/javascript" src="/common/js/common.js"></script>
<script type="text/javascript" src="/common/js/jquery.MultiFile.js"></script>
</head>
<body>
	<script type="text/javascript">
	var fileCnt = 0;
	var workResultVm = null;

	$(document).ready(function() {

		// 업무결과, 처리상태 상호작용

		// [처리상태 -> 업무결과] 방향
		//
		// 업무완료 -> Y
		// 미처리 -> N
		// 기간내 미발생 -> N/A
		//
		// [업무결과 -> 처리상태] 방향
		//
		// Y -> 업무완료
		// N -> 미처리
		// N/A -> 기간내 미발생
		// LP -> 업무 진행중
		// UP -> 업무 진행중

		// [처리상태] Y: 100, UP: 70, LP: 30, N: 0, N/A: -1
		// [업무결과] 업무 진행중: 10, 업무완료: 90, 업무 삭제요청: 93, 기간내 미발생: 92, 미처리: 91

		/* 업무결과, 처리상태 상호 작용 Vue */
		workResultVm = new Vue({
			el: "#workResultVm",
			data: {
				originWrkPrg: "${workVO.utw_wrk_prg}",
				wrkPrg: "${workVO.utw_wrk_prg}",
				completeStatus: "${workVO.utw_com_sta}",
				workStatus: "${workVO.utw_wrk_sta_origin}",
				wrkPrgSelectValue: "${workVO.utw_wrk_prg}",
				comStaToWrkPrg: {
					// 선택하세요.
					"":"0",
					// 업무 진행중
					"10": "30",
					// 업무완료(90) -> Y(100)
					"90": "100",
					// 미처리(91) -> N(0)
					"91": "0",
					// 기간내 미발생(92) -> N/A(-1)
					"92": "-1",
					// 업무 삭제요청
					"93": "-1"
				},
				wrkPrgSelectOptionData: [
					{title: "선택", value: ""},
				<c:forEach var="type" items="<%=statusType.values()%>" varStatus="status">
					{title: "${type.getTitle()}", value: "${type.getValue()}"},
				</c:forEach>
				]
			},
			computed: {
				wrkPrgSelectOptions: function () {
					return this.wrkPrgSelectOptionData;
				},
				wrkPrgSelectOptionsForP: function () {
					return _.filter(this.wrkPrgSelectOptionData, function (option) {
						return option.title == "LP" || option.title == "UP"
					})
				}
			},
			watch: {
				completeStatus: function (newStatus, oldStatus) {
					if (newStatus == 10) {
						this.wrkPrgSelectValue = 0;
						// if (this.originWrkPrg > 0) {
						// 	this.wrkPrgSelectValue = this.originWrkPrg
						// } else {
						// 	this.wrkPrgSelectValue = 0;
						// }
					} else {
						this.wrkPrgSelectValue = this.comStaToWrkPrg[newStatus];
					}
				},
				wrkPrgSelectValue: function (newWrkPrg, oldWrkPrg) {
					this.wrkPrg = newWrkPrg;
				}
			},
			methods: {

			}
		});

		var $comSta = $("#completeStatus");

		$comSta.on("change", function (event) {
			var selectedValue = $(this).val();
			workResultVm.completeStatus = selectedValue;
		});


		// 관련문서 갖고오기
		getGuideList();

		similarWorkList();

		fileList();

		$(".btnOpen").click();

		$("#completeStatus").prop("disabled", true);
		<c:if test="${workVO.wrk_sta_dtl == '12' || workVO.wrk_sta_dtl == '13' || workVO.wrk_sta_dtl == '15'}">
			$("#completeStatus").prop("disabled", false);
		</c:if>

		// 2017-05-11
		$("#b_file").MultiFile({
			afterFileAppend: function() {
				setUploadFileCnt();
			},
			afterFileRemove: function() {
				setUploadFileCnt();
			}
		});

	});

	// 관련문서 갖고오기
	function getGuideList(){
		var ctrKey = $("#ctrKey").val()+"";
		var ctrKeyArray = ctrKey.split(",");

		for(var i=0; i<ctrKeyArray.length; i++){

			$.ajax({
				url		: "${pageContext.request.contextPath}/mwork/FM-MWORK_getGuideList.do",
				type	: "post",
				data	: {"ctrKey": ctrKeyArray[i]},
				dataType: "json",
				success	: function(data){
					var list = "";
					var key = "";
					for(var i=0; i<data.result.length; i++){
						if(i == data.result.length-1){
							list += '<a href="javascript:brdPopup('+data.result[i].ucbBrdKey+')">'+data.result[i].ubmBrdTle+'</a>';
						}else {
							list += '<a href="javascript:brdPopup('+data.result[i].ucbBrdKey+')">'+data.result[i].ubmBrdTle+'</a><br />';
						}
						key = data.result[i].ucbCtrKey;
					}
					$("#"+key).html(list);

					if(data.result.length == 0){
						$("#"+key).text("해당 관련문서가 없습니다.");
					}
				},
				error : function(){
					alert('error');
				},
			});
		}
	}
	function saveWorkTemp(gubun) {
		var cmpCnt = $("#utd_doc_cnt").val();
		var completeStatus = $("#completeStatus").val();

		if (!completeStatus) {
			alert("업무수행결과 처리상태를 선택해주세요.");
			 $("#completeStatus").focus();
			 return false;
		}

		// if(completeStatus === "10") {
        //     alert("업무 수행중에는 완료처리를 하실 수 없습니다. 임시저장 버튼을 눌러주세요.111");
        //     return false;
        // }

		if (completeStatus != "90" || cmpCnt <= fileCnt) {
			saveWork(gubun, completeStatus);
		} else {
			alert("업무완료를 위한 증적파일 숫자가 부족합니다.\n작업내용만 저장됩니다.");
			saveWork(0);
		}
	}

	// uwo_trc_wrk의 utw_wrk_sta 변경 및 uwo_app_mtr 생성
	// 버튼명 : 미완료로변경, 임시저장, 업무완료, 결재상신
	function saveWork(gubun, completeStatus) {

		var msg = "";
		var msgEmpty = "";
		var file = $(".MultiFile-title").length;
		var fileList = "${fn:length(fileList)}";

		$("#utw_wrk_dtl").val(CKEDITOR.instances.wrk_dtl.getData());
		$("#uam_app_gbn").val("0");

		// 임시저장
		if(gubun == '0'){
			$("#utw_wrk_sta").val("10");
			var completeStatus = $("#completeStatus").val();
			$("#utw_com_sta").val(completeStatus);
			msg = "작업한 내용을 임시저장 하시겠습니까?";
		}
		// 결재업무
		else if(gubun == '2'){
			$("#utw_wrk_sta").val("10");
			$("#uam_app_gbn").val("1");
			$("#uam_app_lev").val('${workVO.utd_app_lev}');
			$("#uam_sta_cod").val("11");
			$("#utw_com_sta").val(completeStatus);
			msgEmpty = "수행내용에 작업내용을 작성해주세요.";
			msg = "업무를 상신 하시겠습니까?";
		}
		// 미완료로 변경
		else if(gubun == '3'){
			$("#utw_wrk_sta").val("10");
			$("#utw_com_sta").val("");
			$("#utw_wrk_prg").val("0");
			msg = "업무상태를 미완료로 변경 하시겠습니까?";
		}
		// 91: 미처리, 92: 기간내 미발생, 93: 업무삭제요청
		else if (gubun == "1") {
			$("#utw_wrk_sta").val("90");
			$("#utw_com_sta").val(completeStatus);
			switch (completeStatus) {
				case "91" :
					msgEmpty = "수행내용에 [업무 미처리] 사유를 작성 해주세요.";
					msg = "업무를 [미처리] 완료처리 하시겠습니까?";
					break;
				case "92" :
					msg = "업무를 [기간내 미발생] 완료처리 하시겠습니까?";
					break;
				case "93" :
					$("#utw_wrk_sta").val("10");
					$("#uam_app_gbn").val("1");
					$("#uam_app_lev").val("1");
					$("#uam_sta_cod").val("11");
					msgEmpty = "수행내용에 [업무 삭제요청] 사유를 작성해주세요.";
					msg = "업무를 [업무삭제요청] 하시겠습니까?\n삭제요청시 해당업무는 보안관리자 승인 후 처리됩니다.";
					break;
				default :
					msgEmpty = "수행내용에 작업내용을 작성해주세요.";
					msg = "업무를 완료처리 하시겠습니까?";
			}
		}

		if (msgEmpty && !$("#utw_wrk_dtl").val()) {
			alert(msgEmpty);
			return false;
		}

		if(confirm(msg)){
			document.workForm.action = "${pageContext.request.contextPath}/mwork/FM-MWORK_work_save.do";
			document.workForm.submit();
			try {
				window.opener.searchList();
			}
			catch (ex) {
				opener.location.reload(true);
			}
		}
	}

	// uwo_trc_wrk의 utw_wrk_sta 변경 및 uwo_app_mtr의 uam_sta_cod 변경
	// 버튼명 : 승인, 반려, 결재상신취소
	function saveAppWork(gubun){
		$("#utw_wrk_dtl").val(CKEDITOR.instances.wrk_dtl.getData());

		var appLev = $("#uam_app_lev").val();
		var appStaCod = $("#uam_sta_cod").val();
		var msg = "승인";

		if(gubun == "5"){
			$("#utw_wrk_sta").val("10");
			$("#uam_sta_cod").val("30");
		}else{
			// 1차결재
			if(appLev == "1"){
				$("#uam_cf1_id").val("<%=C_SES_USER_KEY%>");
				$("#uam_cf2_mdh").val("");
				$("#uam_cf2_etc").val("");

				// 승인
				if(gubun == "3"){
					$("#uam_sta_cod").val("19");
					$("#uam_cf1_etc").val($("#comFirmEtc").val());
					$("#utw_apr_id").val("<%=C_SES_USER_KEY%>");
					$("#utw_wrk_sta").val("90");
				}
				// 반려
				else if(gubun == "4"){
					$("#uam_sta_cod").val("12");
					$("#uam_cf1_etc").val($("#rejectEtc").val());
					$("#utw_wrk_sta").val("10");
					$("#utw_apr_id").val("<%=C_SES_USER_KEY%>");
				}
			}
			// 2차결재
			else if(appLev == "2"){
				// 2차결재 : 1차
				if(appStaCod == "11"){
					$("#uam_cf1_id").val("<%=C_SES_USER_KEY%>");
					$("#uam_cf2_mdh").val("");
					$("#uam_cf2_etc").val("");

					// 승인
					if(gubun == "3"){
						$("#uam_sta_cod").val("21");
						$("#uam_cf1_etc").val($("#comFirmEtc").val());
						$("#utw_wrk_sta").val("10");
						$("#utw_apr_id").val("");
					}
					// 반려
					else if(gubun == "4"){
						$("#uam_sta_cod").val("12");
						$("#uam_cf1_etc").val($("#rejectEtc").val());
						$("#utw_wrk_sta").val("10");
						$("#utw_apr_id").val("<%=C_SES_USER_KEY%>");
					}
				}
				// 2차결재 : 2차
				else if(appStaCod == "21"){
					$("#uam_cf2_id").val("<%=C_SES_USER_KEY%>");
					$("#uam_cf2_mdh").val("2");

					// 승인
					if(gubun == "3"){
						$("#uam_sta_cod").val("29");
						$("#uam_cf2_etc").val($("#comFirmEtc").val());
						$("#utw_wrk_sta").val("90");
						$("#utw_apr_id").val("<%=C_SES_USER_KEY%>");
					}
					// 반려
					else if(gubun == "4"){
						$("#uam_sta_cod").val("22");
						$("#uam_cf2_etc").val($("#rejectEtc").val());
						$("#utw_wrk_sta").val("10");
						$("#utw_apr_id").val("<%=C_SES_USER_KEY%>");
					}
				}
			}
		}

		if (gubun == "3") {
			msg = "결재를 승인 하시겠습니까?";
		} else if (gubun == "4") {
			msg = "결재를 반려 하시겠습니까?";
		} else if (gubun == "5") {
			msg = "결재상신을 취소 하시겠습니까?";
		}

		if (confirm(msg)) {
			document.workForm.action = "${pageContext.request.contextPath}/mwork/FM-MWORK_work_stateUpdate.do";
			document.workForm.submit();
			try {
				window.opener.searchList();
			}
			catch (ex) {
				opener.location.reload(true);
			}
		}
	}

	// 업무삭제
	function delWork() {
		if (confirm("해당 업무를 삭제하시겠습니까?")) {
			document.workForm.action = "${pageContext.request.contextPath}/mwork/FM-MWORK_work_del.do";
			document.workForm.submit();
			try {
				window.opener.searchList();
			}
			catch (ex) {
				opener.location.reload(true);
			}
			finally {
				self.close();
			}
		}
	}

	// 첨부파일 다운로드
	function getFile(key, where) {
		$("#downKey").val(key);
		$("#where").val("wrk");
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
				setUploadFileCnt();
			},
			error : function() {
				alert("실패");
			},
			complete : function() {
			}
		});
	}

	// 관련문서 게시판 팝업창
	function brdPopup(brdKey) {
		window.open("", "getbrdView","width=500, height=400, menubar=no, location=no, status=no, scrollbars=yes");
		$("#brdViewKey").val(brdKey);
		document.brdViewForm.submit();
	}

	function similarWorkList(){
		var wrkKey =  $("#utw_wrk_key").val();
		var worker = $("#utw_wrk_id").val();
		var trcKey = $("#utw_trc_key").val();
		var trm_cod_nam = $("#trm_cod_nam").val();
		var utd_doc_nam = $("#utd_doc_nam").val();

		$.ajax({
			url : "${pageContext.request.contextPath}/mwork/getSimilarWorkList.do",
			type : "post",
			data : {
				"worker" : worker, "trcKey" : trcKey, "wrkKey" : wrkKey
			},
			dataType : "json",
			success : function(data) {
				var workListHTML = "";
				for(var i=0; i<data.similarWorkList.length; i++) {
					if(i == data.similarWorkList.length-1){
						workListHTML += '<tr class="last">';
					}else{
						workListHTML += '<tr>';
					}
					workListHTML += '<td>'+data.similarWorkList[i].utwPrdCod+'</td>';
					workListHTML += '<td>'+data.similarWorkList[i].uccSvcNam+'</td>';
					workListHTML += '<td><a href="javascript:copyWorkPopup(\''+data.similarWorkList[i].utwWrkKey+'\',\''+data.similarWorkList[i].utwTrcKey+'\')">'+data.similarWorkList[i].utdDocNam+'</td>';
					workListHTML += '<td>'+data.similarWorkList[i].uccTrmNam+'</td>';
					workListHTML += '<td>'+data.similarWorkList[i].uumUsrNam+'</td>';
					workListHTML += '<td>'+data.similarWorkList[i].utwEndDat+'</td>';
					workListHTML += '<td class="last">'+data.similarWorkList[i].utwRgtMdh+'</td>';
					workListHTML += '</tr>';
				}
				if(workListHTML == ""){
					$("#containerSimilarWorkList").hide();
					//workListHTML += '<tr class="last"><td class="last noDataList" colspan="7">검색된 기존업무 현황이 없습니다.</td></tr>';
				}
				$("#similarWorkList").html(workListHTML);
			},
			error : function() {
				alert("실패");
			},
			complete : function() {
			}
		});
	}

	function fileList(){
		var wrk_key =  $("#utw_wrk_key").val();
		$.ajax({
			url : "${pageContext.request.contextPath}/mwork/getAllWorkList_file.do",
			type : "post",
			data : {
				"wrk_key" : wrk_key
			},
			dataType : "json",
			success : function(data) {
				var list = "";
				for(var i=0; i<data.resultList.length; i++){
					var wrk = "wrk";
					list += "<li id="+data.resultList[i].umf_fle_key+" class='MultiFile-label'><a href='javascript:getFile("+data.resultList[i].umf_fle_key+")'><span class='icoDown'></span>";
					list += data.resultList[i].umf_con_fnm;
					list += "</a>";
				<c:if test="${loginAuth == 'A' || workVO.wrk_sta_dtl == '12' || workVO.wrk_sta_dtl == '13' || workVO.wrk_sta_dtl == '15'}">
					list += "<a href='javascript:delFile_U("+data.resultList[i].umf_fle_key+")' class='del' title='삭제'>x<em class='hidden'>삭제</em></a>";
				</c:if>
					list += "</li>";
				}

				$("#uploadFileList").find("li").not(".fixed").remove();
				$("#uploadFileList").find(".MultiFile-label").remove();
				$("#uploadFileList").append(list);
				$("#fileCnt").html(data.resultList.length);
				fileCnt = data.resultList.length;
			},
			error : function() {
				alert("실패");
			},
			complete : function() {
			}
		});
	}

	function copyWorkPopup(wrkKey, trcKey) {

		window.open("","copyWorkPopup","left=400, top=200, width=730, height=700, menubar=no, location=no, status=no, scrollbars=yes");
		$("#copyWorkPopupForm [name=utwWrkKey]").val(wrkKey);
		$("#copyWorkPopupForm [name=utwTrcKey]").val(trcKey);
		document.copyWorkPopupForm.submit();
	}

	function callbackPop(str){
		CKEDITOR.instances.wrk_dtl.setData(str);
	}

	function viewDoc(key) {
		$("#umfFleKey").val(key);
		var win = window.open("","docForm","menubar=no, location=no, status=no,scrollbars=yes");
		$('#docForm').attr('action','../inspt/FM-INSPT004_viewDoc.do').submit();
		win.focus();
	}

	function docPopup() {
		openPostPopup("/comps/FM-COMPS004_popup.do", 730, 700, { utdTrcKey: $("#utw_trc_key").val() });
	}

	// 2017-05-11
	function setUploadFileCnt() {
		fileCnt = $("#uploadFileList").find(".MultiFile-label").size();
		$("#fileCnt").text(fileCnt);

	}

	// 2018-07-17 s, 화일 업로드
	function fileUploadSubmit(fileListNum){
		if ( !$("#b_file").val()) {
			return false;
		}
		$("#workForm").ajaxSubmit({	// IE8 Issue: $.parseJSON(), multipart/form-data
			url : "batchUpload.do",
			success : function(data){
				data = $.parseJSON(data);
				$("#workForm")[0].reset();
				fileList();
			},
			error : function(data){
				alert("업로드 중 에러가 발생했습니다.");
			}
		});
	}
	</script>
</head>
<body>
	<form action="/mwork/FM-MWORK_popup.do" name="reloadForm" id="reloadForm">
		<input type="hidden" id="utwWrkKey" name="utwWrkKey">
		<input type="hidden" id="utwTrcKey" name="utwTrcKey">
		<input type="hidden" id="mngKey" name="mngKey">
		<input type="hidden" id="usrKey" name="usrKey">
	</form>
	<form action="" name="fileDown" id="fileDown" method="post">
		<input type="hidden" name="downKey" id="downKey">
		<input type="hidden" name="where" id="where">
	</form>
	<form id="docForm" method="post" target="docForm">
		<input type="hidden" id="umfFleKey" name="umfFleKey" />
	</form>
	<form id="workForm" name="workForm" method="post" enctype="multipart/form-data">
		<input type="hidden" id="utw_wrk_key" name="utw_wrk_key" value="<c:out value='${workVO.utw_wrk_key}'/>" />
		<input type="hidden" id="utw_trc_key" name="utw_trc_key" value="<c:out value='${workVO.utw_trc_key}'/>" />
		<input type="hidden" id="utw_wrk_sta" name="utw_wrk_sta" value="<c:out value='${workVO.utw_wrk_sta}'/>">
		<input type="hidden" id="utw_com_sta" name="utw_com_sta" value="<c:out value='${workVO.utw_com_sta}'/>">
		<input type="hidden" id="trm_cod_nam" name="trm_cod_nam" value="<c:out value='${workVO.trm_cod_nam}'/>">
		<input type="hidden" id="utd_doc_nam" name="utd_doc_nam" value="<c:out value='${workVO.utd_doc_nam}'/>">
		<input type="hidden" id="utd_doc_cnt" name="utd_doc_cnt" value="<c:out value='${workVO.utd_doc_cnt}'/>">
		<input type="hidden" id="utw_wrk_dtl" name="utw_wrk_dtl" value="<c:out value='${workVO.utw_wrk_dtl}'/>">
		<input type="hidden" id="utw_wrk_id" name="utw_wrk_id" value="<c:out value='${workVO.utw_wrk_id}'/>">
		<input type="hidden" id="utw_agn_yn" name="utw_agn_yn" value="<c:out value='${workVO.utw_agn_yn}'/>">
		<input type="hidden" id="agn_sta_dtl" name="agn_sta_dtl" value="<c:out value='${workVO.agn_sta_dtl}'/>">
		<input type="hidden" id="utw_prd_cod" name="utw_prd_cod" value="<%=C_SES_MAN_CYL%>" />
		<input type="hidden" id="utw_apr_id" name="utw_apr_id" value='${workVO.utw_apr_id}' />
		<input type="hidden" id="utw_upt_id" name="utw_upt_id" value="<%=C_SES_USER_KEY%>" />
		<input type="hidden" id="uam_app_key" name="uam_app_key" value="<c:out value='${workVO.uam_app_key}'/>" />
		<input type="hidden" id="agn_app_key" name="agn_app_key" value="<c:out value='${workVO.agn_app_key}'/>" />
		<input type="hidden" id="uam_app_gbn" name="uam_app_gbn" value="<c:out value='${workVO.uam_app_gbn}'/>" />
		<input type="hidden" id="uam_app_lev" name="uam_app_lev" value="<c:out value='${workVO.uam_app_lev}'/>" />
		<input type="hidden" id="uam_sta_cod" name="uam_sta_cod" value="<c:out value='${workVO.uam_sta_cod}'/>" />
		<input type="hidden" id="uam_req_id" name="uam_req_id" value='${workVO.utw_wrk_id}' />
		<input type="hidden" id="uam_rgt_id" name="uam_rgt_id" value="<%=C_SES_USER_KEY%>" />
		<input type="hidden" id="uam_cf1_id" name="uam_cf1_id" value="<c:out value='${workVO.uam_cf1_id}'/>" />
		<input type="hidden" id="uam_cf1_mdh" name="uam_cf1_mdh" value="<c:out value='${workVO.uam_cf1_mdh}'/>" />
		<input type="hidden" id="uam_cf1_etc" name="uam_cf1_etc" value="<c:out value='${workVO.uam_cf1_etc}'/>" />
		<input type="hidden" id="uam_cf2_id" name="uam_cf2_id" value="<c:out value='${workVO.uam_cf2_id}'/>" />
		<input type="hidden" id="uam_cf2_mdh" name="uam_cf2_mdh" value="<c:out value='${workVO.uam_cf2_mdh}'/>" />
		<input type="hidden" id="uam_cf2_etc" name="uam_cf2_etc" value="<c:out value='${workVO.uam_cf2_etc}'/>" />
		<input type="hidden" id="uam_upt_id" name="uam_upt_id" value="<%=C_SES_USER_KEY%>" />
		<input type="hidden" id="mode" name="mode" value="<c:out value='${mode}'/>" />
		<input type="hidden" id="ctrKey" name="ctrKey" value="<c:out value='${ctrKey}'/>" />
		<input type="hidden" id="mng" name="mng" value="<c:out value='${mngKey}'/>" />
		<input type="hidden" id="usr" name="usr" value="<c:out value='${usrKey}'/>" />
	<div id="skipnavigation">
		<ul>
			<li><a href="#content-box">본문 바로가기</a></li>
		</ul>
	</div>
	<div id="wrap" class="pop">
		<header>
			<div class="borderBox"></div>
			<h1>업무 상세</h1>
		</header>
		<article class="cont" id="content-box">
			<div class="cont_container">
				<div class="contents">
					<div class="title">업무상태 및 담당자</div>
					<div class="talbeArea">
						<table summary="업무상태 및 담당자를 업무수행일, 업무주기, 업무기간, 업무상태, 대무자, 대무 요청상태, 담당자, 결재자 등의 정보로 설명합니다.">
							<colgroup>
								<col width="10%">
								<col width="14%">
								<col width="10%">
								<col width="14%">
								<col width="10%">
								<col width="14%">
								<col width="10%">
								<col width="*%">
							</colgroup>
							<caption>업무상태 및 담당자</caption>
							<tbody>
								<tr>
									<th scope="row">서비스</th>
									<td><c:out value="${workVO.utw_svc_nam}" escapeXml="false" /></td>
									<th scope="row">부서</th>
									<td><c:out value="${workVO.utw_dep_nam}" escapeXml="false" /></td>
									<th scope="row">담당자</th>
									<td><c:out value="${workVO.utw_wrk_nam}" escapeXml="false" /></td>
									<th scope="row">대무자</th>
									<td  class="last">
										<c:out value="${workVO.utw_agn_nam}" escapeXml="false" />
										<%-- <c:if test="${workVO.agn_sta_dtl == '23'}" >-</c:if>
										<c:if test="${workVO.agn_sta_dtl != '23'}" ><c:out value="${workVO.utw_agn_nam}" escapeXml="false" /></c:if> --%>
									</td>
								</tr>
								<tr>
									<th scope="row">업무주기</th>
									<td><c:out value="${workVO.trm_cod_nam}" escapeXml="false" /></td>
									<th scope="row">증적<br />등록주기</th>
									<td><c:out value="${workVO.utd_dtr_gbn}" escapeXml="false" /></td>
									<th scope="row">업무<br />할당일</th>
									<td><c:out value="${workVO.utw_rgt_mdh}" escapeXml="false" /></td>
									<th scope="row">업무<br />마감일</th>
									<td class="last impact"><c:out value="${workVO.utw_cmp_dat}" escapeXml="false" /></td>
								</tr>
								<tr>
									<th scope="row">필수<br />증적갯수</th>
									<td><c:out value="${workVO.utd_doc_cnt}" escapeXml="false" /></td>
									<th scope="row">업무코드</th>
									<td><c:out value="${workVO.utw_wrk_key}" escapeXml="false" /></td>
									<th scope="row">업무<br>수행일</th>
									<td><c:out value="${workVO.utw_wrk_dat}" escapeXml="false" /></td>
									<th scope="row">업무상태</th>
									<td class="last">
										<strong><c:out value="${workVO.utw_wrk_sta}" escapeXml="false" /></strong>
										<c:if test="${workVO.wrk_sta_dtl == '11'}" ><br />(결재진행중)</c:if>
										<c:if test="${workVO.wrk_sta_dtl == '12'}" ><br />(반려)</c:if>
									</td>

								</tr>
								<tr>
									<th scope="row">업무명</th>
									<td colspan="7" class="last fontLeft"><c:out value="${workVO.utd_doc_nam}" escapeXml="false" />
									<c:if test="${loginAuth == 'A'}">
										<button type="button" onclick="docPopup()" style="float:right;"><span class="icoSynch"></span>수행업무 설정</button>
									</c:if>
									</td>
								</tr>
								<tr>
									<th scope="row">업무설명</th>
									<td colspan="7" class="last fontLeft"><c:out value="${workVO.utd_doc_etc}" escapeXml="false" /></td>
								</tr>
								<tr>
									<th scope="row">템플릿</th>
									<td colspan="7" class="last fontLeft" id="downloadFileList" style="height: 35px;">
									<ul class="listArea">
									<c:forEach var="list" items="${sampleDocFileList}" varStatus="status">
										<li>
											<a href="javascript:getFile(<c:out value="${list.umf_fle_key}" />)"><span class="icoDown"></span> <c:out value="${list.umf_con_fnm}" /></a>
										</li>
									</c:forEach>
									</ul>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>

				<div id="containerSimilarWorkList" class="contents">
					<div class="title">기존업무 확인<button type="button" class="btnOpen open">상세 닫기 <span></span></button></div>
					<div class="talbeArea">
					<table summary="기존업무 확인">
						<colgroup>
							 <col width="10%">
                             <col width="10%">
                             <col width="30%">
                             <col width="10%">
                             <col width="10%">
                             <col width="15%">
                             <col width="15%">
						</colgroup>
						<thead>
							<tr>
								 <th scope="col">대주기</th>
                                 <th scope="col">서비스</th>
                                 <th scope="col">업무명</th>
                                 <th scope="col">업무주기</th>
                                 <th scope="col">담당자</th>
                                 <th scope="col">업무완료일</th>
                                 <th scope="col" class="last">최종등록일</th>
							</tr>
						</thead>
						<tbody id="similarWorkList">
						</tbody>
					</table>
					</div>
				</div>

				<c:if test="${fn:length(cntrList) != 0}">
				<div class="contents">
					<div class="title">표준항목 상세내용<button type="button" class="btnOpen open">상세 닫기 <span></span></button></div>
					<div class="selectDetail">
					<c:forEach var="list" items="${cntrList}" varStatus="status">
						<ul class="detailArea">
							<li class="floatList">
								<dl>
									<dt>표준명</dt>
									<dd><c:out value='${list.ucmCtrGbn}' escapeXml="false" />[<c:out value="${list.ucmGolNo}" escapeXml="false" />]</dd>
								</dl>
							</li>
							<li class="floatList">
								<dl>
									<dt>통제번호</dt>
									<dd><c:out value="${list.ucm3lvCod}" escapeXml="false" /></dd>
								</dl>
							</li>
							<li>
								<dl>
									<dt>통제항목</dt>
									<dd><c:out value="${list.ucm1lvCod}" escapeXml="false" /> <c:out value="${list.ucm1lvNam}" escapeXml="false" /></dd>
								</dl>
							</li>
							<li>
								<dl>
									<dt>통제목적</dt>
									<dd><c:out value="${list.ucm2lvCod}" escapeXml="false" /> <c:out value="${list.ucm2lvNam}" escapeXml="false" /></dd>
								</dl>
							</li>
							<li>
								<dl>
									<dt>상세내용</dt>
									<dd><c:out value="${fn:replace(list.ucm2lvDtl, LF, '<br />')}" escapeXml="false" /></dd>
								</dl>
							</li>
							<li>
								<dl>
									<dt>점검항목</dt>
									<dd><c:out value="${list.ucm3lvNam}" escapeXml="false" /></dd>
								</dl>
							</li>
							<li>
								<dl>
									<dt>설명</dt>
									<dd><c:out value="${fn:replace(list.ucm3lvDtl, LF, '<br />')}" escapeXml="false" /></dd>
								</dl>
							</li>
							<li>
								<dl>
									<dt>
										관련문서(정책, 지침 등)
									</dt>
									<dd>
										<ul id="<c:out value='${list.ucmCtrKey}'/>" class="listArea"></ul>
									</dd>
								</dl>
							</li>
						</ul>
						</c:forEach>
					</div>
				</div>
				</c:if>
				<c:if test="${not empty workVO.uam_cf1_nam}">
				<div class="contents">
					<div class="title">결재의견</div>
					<div class="talbeArea">
						<table summary="1차 결재의견을 1차 결재일, 1차 결재자, 1차 결재의견 등의 정보로 설명합니다.">
							<colgroup>
								<col width="20%">
								<col width="30%">
								<col width="*%">
							</colgroup>
							<caption>1차 결재의견</caption>
							<thead>
								<tr>
									<th scope="col">1차 결재일</th>
									<th scope="col">1차 결재자</th>
									<th scope="col" class="last">1차 결재의견</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td><c:out value="${workVO.uam_cf1_mdh}" /></td>
									<td><c:out value="${workVO.uam_cf1_nam}" /></td>
									<td class="last"><c:out value="${workVO.uam_cf1_etc}" /></td>
								</tr>
							</tbody>
						</table>
						<c:if test="${not empty workVO.uam_cf2_nam}">
						<table summary="2차 결재의견을 2차 결재일, 2차 결재자, 2차 결재의견 등의 정보로 설명합니다.">
							<colgroup>
								<col width="20%">
								<col width="30%">
								<col width="*%">
							</colgroup>
							<caption>2차 결재의견</caption>
							<thead>
								<tr>
									<th scope="col">2차 결재일</th>
									<th scope="col">2차 결재자</th>
									<th scope="col" class="last">2차 결재의견</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td><c:out value="${workVO.uam_cf2_mdh}" /></td>
									<td><c:out value="${workVO.uam_cf2_nam}" /></td>
									<td class="last"><c:out value="${workVO.uam_cf2_etc}" /></td>
								</tr>
							</tbody>
						</table>
						</c:if>
					</div>
				</div>
				</c:if>
				<div class="contents">
					<div class="title">업무수행결과</div>
					<div class="talbeArea">
						<table id="workResultVm" summary="업무기간을 확인 할 수 있습니다.">
							<colgroup>
								<col width="20%">
								<col width="*%">
							</colgroup>
							<caption>업무기간</caption>
							<tbody>
								<tr>
									<th scope="row">처리상태</th>
									<td class="last fontLeft">
										<sb:select name="completeStatus" type="A" code="WKCD_COMP" allText="선택" value="${workVO.utw_com_sta}"/>
									</td>
								</tr>
                                <tr>
                                    <th scope="row">업무결과</th>
                                    <td class="last fontLeft">

                                        <input type="hidden" id="utw_wrk_prg" name="utw_wrk_prg" v-model="wrkPrg">

                                        <%--
                                        해당 SELECT 필드는 활동팝업에서만 사용되고 있기 때문에 따로 분리하지 않았다.
                                        이후 여러곳에서 사용되어진다면 따로 분리해서 include 하는 형식으로 리팩토링하여야 한다.
                                        DB 해당 칼럼의 기본 값이 0 이기 때문에 SELECT 기본 값은 N으로 설정된다.
                                        --%>
                                        <select v-show="completeStatus != 10" class="selectBox" v-model="wrkPrgSelectValue" disabled="disabled">
                                            <option v-for="option in wrkPrgSelectOptions" v-bind:value="option.value">{{ option.title }}</option>
                                        </select>

                                        <select v-show="completeStatus == 10" class="selectBox" v-model="wrkPrgSelectValue" v-bind:disabled="workStatus == 90">
											<option value="0">:::선택하세요</option>
                                            <option v-for="option in wrkPrgSelectOptionsForP" v-bind:value="option.value">
												{{ option.title }}
												{{ option.title == 'LP' ? "(50% 미만)" : "" }}
												{{ option.title == 'UP' ? "(50% 이상)" : "" }}
											</option>
                                        </select>

                                        <%--
                                        업무결과 SELECT 와 처리상태 SELECT 상호작용 규칙

                                        [처리상태 -> 업무결과] 방향

                                        업무완료 -> Y
                                        미처리 -> N
                                        기간내 미발생 -> N/A

                                        [업무결과 -> 처리상태] 방향

                                        Y -> 업무완료
                                        N -> 미처리
                                        N/A -> 기간내 미발생
                                        LP -> 업무 진행중
                                        UP -> 업무 진행중

                                        '업무 완료'를 선택하면 자동으로 'Y'가 선택됨.
                                        '업무 수행중'을 선택하면 'LP', 'UP' 중 선택을 하게함.
                                        '미처리'를 선택하면 'N'이 선택됨.
                                        '기간내 미발생'을 선택하면 'N/A'가 선택됨.
                                        '업무삭제 요청'은 별개의 기능임.
                                        --%>

                                    </td>
                                </tr>
							</tbody>
						</table>
						<table summary="수행내용을 작성할 수 있습니다.">
							<caption>수행내용</caption>
							<thead>
								<tr>
									<th scope="col" class="last">수행내용</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td class="last noPadding">
										<textarea class="txtAreaType03" id="wrk_dtl" name="wrk_dtl" placeholder="수행한 업무에 대해 최대한 상세히 기입 부탁드립니다. </br> * 솔루션인 경우, 솔루션명을 입력 </br> * 정보시스템인 경우, 서버, DB, 네트워크 등을 입력">${workVO.utw_wrk_dtl}</textarea>
										<ce:replace replace="wrk_dtl" basePath="${pageContext.request.contextPath}/editor/ckeditor" ></ce:replace>
									</td>
								</tr>
							</tbody>
						</table>

						<div class="uplidFileList margin10">
							<p class="titleArea">
								증적파일(정책, 지침 등) - 총 <strong id="fileCnt"></strong>건
							</p>

							<ul class="listArea width100" id="uploadFileList" style="position: relative;">
							<c:if test="${loginAuth eq 'A' || workVO.wrk_sta_dtl == '12' || workVO.wrk_sta_dtl == '13' || workVO.wrk_sta_dtl == '15'}">
								<li class="fixed"><input type="file" class="" name="b_file" id ="b_file" style="width: 300px">
								<c:if test="${loginAuth eq 'A'}">
									<button type="button" onclick="fileUploadSubmit();" style="position: absolute; top: 3px; left: 320px;"><span class="icoUpload"></span>추가 파일 업로드</button>
								</c:if>
							</li>
							</c:if>
							</ul>
						</div>

					</div>
				</div>
			</div>
			<div class="centerBtnArea bottomBtnArea">
                <button type="button" class="popClose close"><span class="icoClose">닫기</span></button>
				<ul class="btnList">
				<c:if test="${loginAuth eq 'A'}">
					<li>
						<button type="button" class="btnStrong2" onclick="delWork(); return false;"><span class="icoDel"></span>업무삭제</button>
					</li>
				</c:if>
				<c:if test="${loginAuth eq 'A' || userKey == workVO.utw_wrk_id || userKey == workVO.utw_agn_id}">
					<c:if test="${workVO.wrk_sta_dtl == '14'}">
					<li>
						<button type="button" class="btnStrong" onclick="saveWork(3); return false;"><span class="icoSave"></span>미완료변경</button>
					</li>
					</c:if>
					<c:if test="${workVO.wrk_sta_dtl == '12' || workVO.wrk_sta_dtl == '13' || workVO.wrk_sta_dtl == '15'}">
					<li>
						<button type="button" class="btnStrong2" onclick="saveWork(0);return false;"><span class="icoReDo"></span>임시저장</button>
					</li>
					<!-- 증적양식 결재여부에 따라 버튼 달라짐 -->
						<c:if test="${workVO.utd_apr_yn == 'N'}">
					<li>
						<button type="button" class="btnStrong" onclick="saveWorkTemp(1);return false;"><span class="icoSave"></span>업무완료</button>
					</li>
						</c:if>
						<c:if test="${workVO.utd_apr_yn == 'Y' || workVO.utd_apr_yn == '12'}">
					<li>
						<button type="button" class="btnStrong" onclick="saveWorkTemp(2);return false;"><span class="icoSave"></span>결재상신</button>
					</li>
						</c:if>
					</c:if>
					<c:if test="${workVO.wrk_sta_dtl == '11' || workVO.agn_sta_dtl == '21' }">
					<li>
						<button type="button" class="btnStrong" onclick="saveAppWork(5)"><span class="icoReDo"></span>결재상신취소</button>
					</li>
					</c:if>
				</c:if>
				<c:if test="${loginAuth == 'A' || userKey == workVO.uam_cf1_id || userKey == workVO.uam_cf2_id}">
					<!-- 미완료(결재진행중)상태에서만 승인, 반려 버튼 보여줌 -->
					<c:if test="${workVO.wrk_sta_dtl == '11'}">
						<!-- 업무담당자의 지정된 결재자에게만 승인, 반려 버튼 보여줌 -->
						<c:if test="${(userKey == workVO.uam_cf1_id && workVO.uam_sta_cod == '11') || (userKey == workVO.uam_cf2_id && workVO.uam_sta_cod == '21')}">
					<li>
						<button type="button" class="openPop btnStrong"><span class="icoReDo"></span>승인</button>
						<div class="layerPopType02">
							<div class="popTitle">
								<p>의견 등록</p>
							</div>
							<div class="layerContents">
								<label for="comFirmEtc">의견을 등록하세요.</label>
								<p>
									<textarea id="comFirmEtc" class="txtAreaType02"></textarea>
								</p>
								<div class="centerBtnArea">
									<button type="button" onclick="saveAppWork('3')">확인</button>
								</div>
							</div>
							<button type="button" class="popClose"><span class="icoClose">닫기</span></button>
						</div>
						<div class="popBG"></div>
						</li>
						<li>
						<button type="button" class="openPop btnStrong"><span class="icoReDo"></span>반려</button>
						<div class="layerPopType02">
							<div class="popTitle">
								<p>반려 사유 등록</p>
							</div>
							<div class="layerContents">
								<label for="rejectEtc">반려 사유를 등록하세요.</label>
								<p>
									<textarea id="rejectEtc" class="txtAreaType02"></textarea>
								</p>
								<div class="centerBtnArea">
									<button type="button" onclick="saveAppWork('4')">확인</button>
								</div>
							</div>
							<button type="button" class="popClose"><span class="icoClose">닫기</span></button>
						</div>
						<div class="popBG"></div>
					</li>
						</c:if>
					</c:if>
				</c:if>
				</ul>
            </div>
		</article>
		</form>
		<form id="brdViewForm" name="brdViewForm"
			action="/mwork/FM-MWORK_brd_popup.do" method="post"
			target="getbrdView">
			<input type="hidden" name="brdViewKey" id="brdViewKey" value="">
		</form>
		<form id="copyWorkPopupForm" name="copyWorkPopupForm" action="/mwork/FM-MWORK_copyWorkpopup.do" method="post" target="copyWorkPopup">
			<input type="hidden" id="utwWrkKey" name="utwWrkKey" value="">
			<input type="hidden" id="utwTrcKey" name="utwTrcKey" value="">
			<input type="hidden" id="mngKey" name="mngKey" value="">
			<input type="hidden" id="usrKey" name="usrKey" value="">
		</form>
		<form id="postPopup"></form>
	</div>
</body>
</body>
</html>