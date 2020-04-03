	<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
<%@ include file="/WEB-INF/include/contents.jsp"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<META http-equiv="X-UA-Compatible" content="IE=edge">
<title>SK broadband ISAMS</title>
<link rel="stylesheet" type="text/css" href="/common/css/reset.css"/>
<link rel="stylesheet" type="text/css" href="/common/css/common.css"/>
<link rel="stylesheet" type="text/css" href="/common/css/pop.css"/>
<!--[if IE]> <script src="/common/js/html5.js"></script> <![endif]-->
<script type="text/javascript" src="/common/js/jquery.js"></script>
<script type="text/javascript" src="/common/js/ismsUI.js"></script>
<script type="text/javascript" src="/common/js/jquery.form.js"></script>
<script type="text/javascript" src="/common/js/jquery.blockUI.js"></script>
<script type="text/javascript" src="/common/js/common.js"></script>
<style type="text/css">
input.impact { background-color: #FDDFDF; border: 1px solid #FF0000; }
</style>
<script  type="text/javascript">
$(document).ready(function() {
	loadingBarSet();	// 로딩바 적용

	if($("#totCnt").val() != ""){
		alert("총 건수 : "+$("#totCnt").val()+"\n정상 건수 : "+$("#sucCnt").val()+"\n에러 건수 : "+$("#errCnt").val()+"\n저장되었습니다." );
		opener.location.reload();
		window.close();
	}
});
function excelDownload(){
	document.excelDownloadForm.action = "${pageContext.request.contextPath}/excel/FM-ASSET001_sample.do";
   	document.excelDownloadForm.submit();
}

function excelUpload(){
	if($("#excelFile").val() == null || $("#excelFile").val() == "") {
		alert("엑셀파일을 선택 해주세요.");
		return;
	}

	$("#excelUploadForm").ajaxSubmit({	// IE8 Issue: $.parseJSON(), multipart/form-data
		url : "${pageContext.request.contextPath}/asset/FM-ASSET001_excelUpload.do",
		success : function(data){
			data = $.parseJSON(data);
			var inData = "";
			for(var i=0; i<data.result.length; i++){
				inData += '<tr>'+
					'<td><input type="text" name="uarAssKey" value="'+data.result[i].uarAssKey+'" class="numeric"></td>'+	//자산관리키
					'<td><input type="text" name="uarGrpCod" value="'+data.result[i].uarGrpCod+'" class="required" readonly="readonly"></td>'+	//그룹코드
					'<td><input type="text" name="uarGrpNam" value="'+data.result[i].uarGrpNam+'" class="required" readonly="readonly"></td>'+	//그룹명
					'<td><input type="text" name="uarCatCod" value="'+data.result[i].uarCatCod+'" class="required" ></td>'+	//분류코드
					'<td><input type="text" name="uarSubCod" value="'+data.result[i].uarSubCod+'" class="required" ></td>'+	//서비스코드
					'<td><input type="text" name="uarSubNam" value="'+data.result[i].uarSubNam+'" class="required" ></td>'+	//서비스명
					'<td><input type="text" name="uarSvrEtc" value="'+data.result[i].uarSvrEtc+'" ></td>'+	//서비스상세
					'<td><input type="text" name="uarDepCod" value="'+data.result[i].uarDepCod+'" class="required" ></td>'+	//관리부서코드
					'<td><input type="text" name="uarDepNam" value="'+data.result[i].uarDepNam+'" class="required" ></td>'+	//관리부서명
					'<td><input type="text" name="uarOwnId" value="'+data.result[i].uarOwnId+'" class="required" ></td>'+	//관리담당자키
					'<td><input type="text" name="uarOwnNam" value="'+data.result[i].uarOwnNam+'" ></td>'+	//관리담담자명
					'<td><input type="text" name="uarAdmId" value="'+data.result[i].uarAdmId+'" class="required" ></td>'+	//관리책임자키
					'<td><input type="text" name="uarAdmNam" value="'+data.result[i].uarAdmNam+'" ></td>'+	//관리책임자명
					'<td><input type="text" name="uarOprCod" value="'+data.result[i].uarOprCod+'" ></td>'+	//운영부서코드
					'<td><input type="text" name="uarOprNam" value="'+data.result[i].uarOprNam+'" ></td>'+	//운영부서명
					'<td><input type="text" name="uarUseId" value="'+data.result[i].uarUseId+'" ></td>'+	//운영담당자키
					'<td><input type="text" name="uarUseNam" value="'+data.result[i].uarUseNam+'" ></td>'+	//운영담당자명
					'<td><input type="text" name="uarPicId" value="'+data.result[i].uarPicId+'" ></td>'+	//운영책임자키
					'<td><input type="text" name="uarPicNam" value="'+data.result[i].uarPicNam+'" ></td>'+	//운영책임자명
					'<td><input type="text" name="uarAssGbn" value="'+data.result[i].uarAssGbn+'" ></td>'+	//유형코드
					'<td><input type="text" name="uarAssNam" value="'+data.result[i].uarAssNam+'" ></td>'+	//유형명
					'<td><input type="text" name="uarEqpNam" value="'+data.result[i].uarEqpNam+'" ></td>'+	//자산명
					'<td><input type="text" name="uarDtlExp" value="'+data.result[i].uarDtlExp+'" ></td>'+	//용도
					'<td><input type="text" name="uarAdmPos" value="'+data.result[i].uarAdmPos+'" ></td>'+	//위치
					'<td><input type="text" name="uarRskGrp" value="'+data.result[i].uarRskGrp+'" class="required readonly="readonly"></td>'+	//취약점그룹코드
					'<td><input type="text" name="uarRskNam" value="'+data.result[i].uarRskNam+'" class="required readonly="readonly"></td>'+	//취약점그룹명
					'<td><input type="text" name="uarAppCon" value="'+data.result[i].uarAppCon+'" class="required numeric"></td>'+	//기밀성
					'<td><input type="text" name="uarAppItg" value="'+data.result[i].uarAppItg+'" class="required numeric"></td>'+	//무결성
					'<td><input type="text" name="uarAppAvt" value="'+data.result[i].uarAppAvt+'" class="required numeric"></td>'+	//가용성
					'<td><input type="text" name="uarAppTot" value="'+data.result[i].uarAppTot+'" class="required" readonly="readonly"></td>'+	//중요도점수
					'<td><input type="text" name="uarAssLvl" value="'+data.result[i].uarAssLvl+'" class="required" readonly="readonly"></td>'+	//중요도등급
					'<td><input type="text" name="uarAudYn" value="'+data.result[i].uarAudYn+'" ></td>'+	//ISMS인증대상
					'<td><input type="text" name="uarSmpYn" value="'+data.result[i].uarSmpYn+'" ></td>'+	//샘플링대상
					'<td><input type="text" name="uarInfYn" value="'+data.result[i].uarInfYn+'" ></td>'+	//기반시설대상
					'<td><input type="text" name="uarPrvYn" value="'+data.result[i].uarPrvYn+'" ></td>'+	//개인정보보유
					'<td><input type="text" name="uarUseYn" value="'+data.result[i].uarUseYn+'" ></td>'+	//사용유무
					'<td><input type="text" name="uarIp" value="'+data.result[i].uarIp+'" ></td>'+
					'<td><input type="text" name="uarHost" value="'+data.result[i].uarHost+'" ></td>'+
					'<td><input type="text" name="uarOs" value="'+data.result[i].uarOs+'" ></td>'+
					'<td><input type="text" name="uarValCl0" value="'+data.result[i].uarValCl0+'" ></td>'+
					'<td><input type="text" name="uarValCl1" value="'+data.result[i].uarValCl1+'" ></td>'+
					'<td><input type="text" name="uarValCl2" value="'+data.result[i].uarValCl2+'" ></td>'+
					'<td><input type="text" name="uarValCl3" value="'+data.result[i].uarValCl3+'" ></td>'+
					'<td><input type="text" name="uarValCl4" value="'+data.result[i].uarValCl4+'" ></td>'+
					'<td><input type="text" name="uarValCl5" value="'+data.result[i].uarValCl5+'" ></td>'+
					'<td><input type="text" name="uarValCl6" value="'+data.result[i].uarValCl6+'" ></td>'+
					'<td><input type="text" name="uarValCl7" value="'+data.result[i].uarValCl7+'" ></td>'+
					'<td><input type="text" name="uarValCl8" value="'+data.result[i].uarValCl8+'" ></td>'+
					'<td class="last"><input type="text" name="uarValCl9" value="'+data.result[i].uarValCl9+'" ></td>'+
					'</tr>';
			}
			$("#assList").html(inData);
			if (chkRequired()) {
				alert("필수항목이 누락된 데이터가 존재합니다.\n마킹된 데이터를 확인 해주세요.");
			}
			if (chkValidation()) {
				alert("잘못된 형식의 데이터가 존재합니다.\n마킹된 데이터를 확인 해주세요.");
				return;
			}
		},
		error : function(data){
			alert("엑셀 업로드를 실패했습니다.");
		},
		complete : function(){
		}
	});
}

function chkRequired() {
	var chkFlag = false;
	$(".required").each(function() {
		if (!$(this).val()) {
			$(this).addClass("impact");
			chkFlag = true;
		}
	});
	return chkFlag;
}

function chkValidation() {
	var chkFlag = false;
	$(".numeric").each(function() {
		if ($(this).val() && !$.isNumeric($(this).val())) {
			$(this).addClass("impact");
			chkFlag = true;
		}
	})
	return chkFlag;
}

function excelSave(){
	if ($("#assList").find(".noDataList").size() > 0 || $("#assList").find("tr").size() == 0) {
		alert("엑셀 데이터가 존재하지 않습니다.");
		return;
	}

	if (chkRequired()) {
		alert("누락된 데이터 확인 후 다시 시도해주세요.");
		return;
	}

	if (chkValidation()) {
		alert("잘못된 형식의 데이터 확인 후 다시 시도해주세요.");
		return;
	}

	if(!arrValidation( $("input[name=uarSubCod]") )) {
		alert("서비스코드를 입력하세요");
		return;
	}

	if(!arrValidation( $("input[name=uarCatCod]") )) {
		alert("분류코드를 입력하세요");
		return;
	}

	if(!arrValidation( $("input[name=uarDepCod]") )) {
		alert("관리부서코드를 입력하세요");
		return;
	}

	if(!arrValidation( $("input[name=uarOwnId]") )) {
		alert("관리담당자 사번을 입력하세요");
		return;
	}

	if(!arrValidation( $("input[name=uarAdmId]") )) {
		alert("관리책임자 사번을 입력하세요");
		return;
	}

	if(!arrValidation( $("input[name=uarAssGbn]") )) {
		alert("자산유형코드를 입력하세요");
		return;
	}

	if(!arrValidation( $("input[name=uarAppCon]") )) {
		alert("기밀성 점수를 입력하세요");
		return;
	}

	if(!arrValidation( $("input[name=uarAppItg]") )) {
		alert("무결성 점수를 입력하세요");
		return;
	}

	if(!arrValidation( $("input[name=uarAppAvt]") )) {
		alert("가용성 점수를 입력하세요");
		return;
	}

	document.uploadDataForm.action = "${pageContext.request.contextPath}/asset/FM-ASSET001_excelSave.do";
	document.uploadDataForm.submit();
}
</script>
</head>
<body>
	<input type="hidden" id="totCnt" value="${result.totCnt}">
	<input type="hidden" id="sucCnt" value="${result.sucCnt}">
	<input type="hidden" id="errCnt" value="${result.errCnt}">
	<div id="skipnavigation">
		<ul>
			<li><a href="#content-box">본문 바로가기</a></li>
		</ul>
	</div>
	<div id="wrap" class="pop">
		<header>
		    <div class="borderBox"></div>
		    <h1>자산 엑셀업로드</h1>
		</header>
		<article class="cont" id="content-box">
			<div class="cont_container">
				<form id="excelUploadForm" name="excelUploadForm" method="post" enctype="multipart/form-data">
				<div class="contents">
					<div class="talbeArea">
						<table summary="자산 엑셀업로드 팝업">
							<colgroup>
								<col width="15%">
								<col width="*%">
								<col width="15%">
								<col width="20%">
							</colgroup>
							<caption>엑셀업로드</caption>
							<tbody>
		  						<tr>
			                		<th class="listTitle">엑셀파일</th>
			                        <td class="fontLeft">
			                        	<input type="file" id="excelFile" name="excelFile" style="width:300px">
										<button type="button" class="" onclick="excelUpload();"><span class="icoUpload"></span>파일 업로드</button>
			                		</td>
			                		<th class="listTitle" scope="row"><label>샘플양식</label></th>
		   							<td class="fontLeft last">
		   								 <button type="button" onclick="excelDownload();"><span class="icoDown"></span>다운로드</button>
		   							</td>
			            		</tr>
							</tbody>
						</table>
						<div class="bottomBtnArea">
							<ul class="btnList">

							</ul>
						</div>
					</div>
				</div>
				</form>
				<form id="uploadDataForm" name="uploadDataForm" method="post">
				<div class="contents">
					<div class="title">업로드 자산</div>
					<div class="talbeArea hiddenTable">
						<table summary="업로드 자산" style="width:2807px">
							<colgroup>
								<col width="3%">
								<col width="3%">
								<col width="3%">
								<col width="3%">
								<col width="3%">
								<col width="3%">
								<col width="3%">
								<col width="3%">
								<col width="2%">
								<col width="2%">
								<col width="2%">
								<col width="2%">
								<col width="2%">
								<col width="2%">
								<col width="2%">
								<col width="2%">
								<col width="2%">
								<col width="2%">
								<col width="2%">
								<col width="2%">
								<col width="2%">
								<col width="2%">
								<col width="2%">
								<col width="2%">
								<col width="2%">
								<col width="2%">
								<col width="2%">
								<col width="2%">
								<col width="2%">
								<col width="2%">
								<col width="2%">
								<col width="2%">
								<col width="2%">
								<col width="2%">
								<col width="2%">
								<col width="2%">
								<col width="2%">
								<col width="2%">
								<col width="2%">
								<col width="2%">
								<col width="2%">
								<col width="2%">
								<col width="2%">
								<col width="2%">
								<col width="2%">
								<col width="2%">
								<col width="2%">
								<col width="2%">
								<col width="2%">
							</colgroup>
							<caption>자산 업로드</caption>
							<thead>
								<tr>
									<th scope="row">자산관리키</th>
									<th scope="row">그룹코드</th>
									<th scope="row">그룹명</th>
									<th scope="row">분류코드</th>
									<th scope="row">서비스코드</th>
									<th scope="row">서비스명</th>
									<th scope="row">서비스상세</th>
									<th scope="row">관리부서코드</th>
									<th scope="row">관리부서명</th>
									<th scope="row">관리담당자ID</th>
									<th scope="row">관리담당자명</th>
									<th scope="row">관리책임자ID</th>
									<th scope="row">관리책임자명</th>
									<th scope="row">운영부서코드</th>
									<th scope="row">운영부서명</th>
									<th scope="row">운영담당자ID</th>
									<th scope="row">운영담당자명</th>
									<th scope="row">운영책임자ID</th>
									<th scope="row">운영책임자명</th>
									<th scope="row">자산유형코드</th>
									<th scope="row">자산유형명</th>
									<th scope="row">자산명</th>
									<th scope="row">용도</th>
									<th scope="row">위치</th>
									<th scope="row">취약점코드</th>
									<th scope="row">취약점명</th>
									<th scope="row">기밀성(1/2/3)</th>
									<th scope="row">무결성(1/2/3)</th>
									<th scope="row">가용성(1/2/3)</th>
									<th scope="row">중요도점수</th>
									<th scope="row">중요도등급</th>
									<th scope="row">ISMS인증대상</th>
									<th scope="row">샘플링대상</th>
									<th scope="row">기반시설대상</th>
									<th scope="row">개인정보보유</th>
									<th scope="row">사용유무</th>
									<th scope="row">IP</th>
									<th scope="row">호스트</th>
									<th scope="row">OS</th>
									<th scope="row">가변1</th>
									<th scope="row">가변2</th>
									<th scope="row">가변3</th>
									<th scope="row">가변4</th>
									<th scope="row">가변5</th>
									<th scope="row">가변6</th>
									<th scope="row">가변7</th>
									<th scope="row">가변8</th>
									<th scope="row">가변9</th>
									<th scope="row" class="last">가변10</th>
								</tr>
							</thead>
							<tbody id="assList">
								<tr class="last">
									<td class="last noDataList" colspan="46">
										엑셀 업로드 데이터가 없습니다.
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				</form>
			</div>
			<div class="centerBtnArea bottomBtnArea">
				<button type="button" class="popClose close"><span class="icoClose">닫기</span></button>
				<ul class="btnList">
					<li><button type="button" class="btnStrong" onclick="excelSave();"><span class="icoSave"></span>저장</button></li>
				</ul>
			</div>
		</article>
	</div>
<form id="excelDownloadForm" name="excelDownloadForm" method="post">
</form>
</body>