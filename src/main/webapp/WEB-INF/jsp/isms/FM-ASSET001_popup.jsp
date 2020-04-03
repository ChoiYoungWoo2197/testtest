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
<script type="text/javascript" src="/common/js/jquery.chained.min.js"></script>
<script type="text/javascript" src="/common/js/common.js"></script>

<script  type="text/javascript">
$(document).ready(function() {
	getValClList();
	getImportanceScore();
	$("#uarAssGbn").chained("#arrAssCat");
	$("#arrAssCat").change(function() { getValClList() });
});


function getValClList() {
	var uarAssCat = $("#arrAssCat option:selected").attr("title");
	if(uarAssCat != ""){
		$.post("${pageContext.request.contextPath}/asset/FM-ASSET001_CAT_LIST.do", {uarAssCat:uarAssCat}, function(data) {
			setValClData(data.result);
		 }, "json");
	}
}

function setValClData(data) {
	$(".trValCl").hide();
	$(".uarValTxt").text("");

	$.each(data, function(k, v) {console.log(k)
		$(".uarValTxt").each(function() {
			if ($(this).attr("for") == k && v) {
				$(this).html(v);
				$(this).parents().parents().show();
				return true;
			}
		});
	});
}


function getImportanceScore(){

	var cScore = $("#uarAppCon option:selected").text();
	var iScore = $("#uarAppItg option:selected").text();
	var aScore = $("#uarAppAvt option:selected").text();
	var sum = 0;
	sum = Number(cScore) + Number(iScore) + Number(aScore);

	if(sum >=3 && sum <=4 ){
		grade = "L(1)";
	} else if(sum >=5 && sum <=7 ){
		grade = "M(2)";
	} else if(sum >=8 && sum <=9 ){
		grade = "H(3)";
	} else{
		grade="N(0)";
	}

	$("#uarAppTot").val(sum);
	$("#uarAssLvl").val(grade);
}

function fn_save(){
	if (!$("#uarSvrCod").val()) {
		alert("서비스를 선택하세요.");
		return;
	}
	if (!$("#arrAssCat").val()) {
		alert("자산분류 선택하세요.");
		return;
	}
	if (!$("#uarAssGbn").val()) {
		alert("자산유형 선택하세요.");
		return;
	}
	if (!$("#uarOwnId").val() || !$("#uarAdmId").val()) {
		alert("관리자를 선택하세요.");
		return;
	}
	if (!$("#uarUseId").val() || !$("#uarPicId").val()) {
		alert("운영자를 선택하세요.");
		return;
	}

	$("#uarSvrNam").val($("#uarSvrCod option:selected").text());
	$("#uarAssNam").val($("#uarAssGbn option:selected").text());
	$("#uarAssCat").val($("#arrAssCat option:selected").attr("title"));
	$("#uarCatCod").val($("#arrAssCat").val());

	$("#popForm").ajaxSubmit({
		url : "${pageContext.request.contextPath}/asset/FM-ASSET001_assetCode_save.do",
		success : function(data){
			try {
				alert("저장되었습니다.");
				window.opener.searchList();
			}
			catch (ex) {
				opener.location.reload(true);
			}
			finally {
				self.close();
			}
		},
		error : function(data){
			alert("error");
		}
	});

}
</script>
</head>
<body>
<form id="popForm" name="popForm" method="post">
<input type="hidden" id="uarAssKey" name="uarAssKey" value="${info.uarAssKey}">
<input type="hidden" id="mode" name="mode" value="${info.mode}">
<input type="hidden" id="uarSvrTxt" name="uarSvrTxt">
<input type="hidden" id="uarAssCat" name="uarAssCat">
<input type="hidden" id="assCatCod" name="assCatCod">

<div id="skipnavigation">
	<ul>
		<li><a href="#content-box">본문 바로가기</a></li>
	</ul>
</div>
<div id="wrap" class="pop">
	<header>
	    <div class="borderBox"></div>
	    <h1>자산내역</h1>
	</header>
	<article class="cont" id="content-box">
		<div class="cont_container">
			<div class="contents">
				<div class="talbeArea">
					<table summary="자산코드세부내용을 조직사항, 운용자, 책임자(팀장), 자산유형, 자산코드(현업), 자산코드(시스템), 사용여부, 자산활용, 자산명, 자산용도, 자산위치, 제조사, 모델명, 자산설명, 비고, 가변컬럼, 평가항목 등을 확인/수정 할 수 있습니다.">
						<colgroup>
							<col width="13%">
							<col width="20%">
							<col width="13%">
							<col width="20%">
							<col width="13%">
							<col width="20%">
						</colgroup>
						<caption>자산내역</caption>
						<tbody>
							<tr>
								<th scope="row"><label for="uarSvrCod">* 서비스</label></th>
								<td class="fontLeft" colspan="1">
									<sb:select name="uarSvrCod" type="S2" value="${info.uarSubCod}" allText="서비스 선택" />
								</td>
								<th scope="row"><label for="arrAssCat">* 자산분류</label></th>
								<td class="fontLeft" colspan="1">
									<select id="arrAssCat" name="arrAssCat" class="selectBox" title="항목 선택">
										<option value="" title="">선택</option>
										<c:forEach var="assCat" items="${assCatList}" varStatus="status">
											<option value="${assCat.code}" title="${assCat.key}" <c:if test="${assCat.key eq info.uarAssCat}">selected="selected"</c:if>><c:out value="${assCat.name}" /></option>
										</c:forEach>
									</select>
								</td>
								<th scope="row"><label for="uarAssGbn">* 자산유형</label></th>
								<td class="fontLeft last">
									<select id="uarAssGbn" name="uarAssGbn" class="selectBox" title="항목 선택">
										<option value="">선택</option>
										<c:forEach var="assGbn" items="${assGbnList}" varStatus="status">
											<option value="${assGbn.code}" class="${assGbn.category}" <c:if test="${assGbn.code eq info.uarAssGbn}">selected="selected"</c:if>><c:out value="${assGbn.name}" /></option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<th scope="row"><label for="uarOwnId">* 관리 담당자</label></th>
								<td class="fontLeft" colspan="1">
									<input type="hidden" id="uarOwnId" name="uarOwnId" value="${info.uarOwnId}"/>
									<input type="text" class="inputText readonly" id="uarOwnNam" name="uarOwnNam" style="width:68px" value="${info.uarOwnNam}" readonly="readonly">
									<button type="button" id="uarOwnId" onclick="userListPopup('','uarOwnId','uarOwnNam','','uarDepCod','uarDepNam');" style="width: 35px;"><span class="icoPer"></span></button>
								</td>
								<th scope="row"><label for="uarAdmId">* 관리 책임자</label></th>
								<td class="fontLeft" colspan="1">
									<input type="hidden" id="uarAdmId" name="uarAdmId" value="${info.uarAdmId}"/>
									<input type="text" class="inputText readonly" id="uarAdmNam" name="uarAdmNam" style="width:68px" value="${info.uarAdmNam}" readonly="readonly">
									<button type="button" id="btnApvOne" onclick="userListPopup('','uarAdmId','uarAdmNam','');" style="width: 35px;"><span class="icoPer"></span></button>
								</td>
								<th scope="row"><label for="uarDepCod">관리 부서</label></th>
								<td class="fontLeft last">
									<input type="hidden" id="uarDepCod" name="uarDepCod" value="${info.uarDepCod}">
									<input type="text" class="inputText readonly" id="uarDepNam" name="uarDepNam" style="width:108px" readonly="readonly" value="${info.uarDepNam}">
								</td>
							</tr>
							<tr>
								<th scope="row"><label for="uarUseId">* 운영 담당자</label></th>
								<td class="fontLeft" colspan="1">
									<input type="hidden" id="uarUseId" name="uarUseId" value="${info.uarUseId}"/>
									<input type="text" class="inputText readonly" id="uarUseNam" name="uarUseNam" style="width:68px" value="${info.uarUseNam}" readonly="readonly">
									<button type="button" id="uarOwnId" onclick="userListPopup('','uarUseId','uarUseNam','','uarOprCod','uarOprNam');" style="width: 35px;"><span class="icoPer"></span></button>
								</td>
								<th scope="row"><label for="uarPicId">* 운영 책임자</label></th>
								<td class="fontLeft" colspan="1">
									<input type="hidden" id="uarPicId" name="uarPicId" value="${info.uarPicId}"/>
									<input type="text" class="inputText readonly" id="uarPicNam" name="uarPicNam" style="width:68px" value="${info.uarPicNam}" readonly="readonly">
									<button type="button" id="btnApvOne" onclick="userListPopup('','uarPicId','uarPicNam','');" style="width: 35px;"><span class="icoPer"></span></button>
								</td>
								<th scope="row"><label for="uarOprCod">운영 부서</label></th>
								<td class="fontLeft last">
									<input type="hidden" id="uarOprCod" name="uarOprCod" value="${info.uarOprCod}">
									<input type="text" class="inputText readonly" id="uarOprNam" name="uarOprNam" style="width:108px" readonly="readonly" value="${info.uarOprNam}">
								</td>
							</tr>
							<tr>
								<th scope="row"><label for="uarEqpNam">자산명</label></th>
								<td class="fontLeft last" colspan="5">
									<input type="text" id="uarEqpNam" name="uarEqpNam" class="inputText" style="width:364px" value="${info.uarEqpNam}">
								</td>
							</tr>
							<tr>
								<th scope="row"><label for="uarIp">IP</label></th>
								<td class="fontLeft">
									<input type="text" id="uarIp" name="uarIp" class="inputText" style="width:110px" maxlength="25" value="${info.uarIp}">
								</td>
								<th scope="row"><label for="uarHost">호스트명</label></th>
								<td class="fontLeft">
									<input type="text" id="uarHost" name="uarHost" class="inputText" style="width:110px" maxlength="25" value="${info.uarHost}">
								</td>
								<th scope="row"><label for="uarOs">OS</label></th>
								<td class="fontLeft last">
									<input type="text" id="uarOs" name="uarOs" class="inputText" style="width:110px" maxlength="25" value="${info.uarOs}">
								</td>
							</tr>
							<tr class="trValCl" style="display: none;">
								<th scope="row"><label id="uarValTxt0" for="uacValCl0" class="uarValTxt"></label></th>
								<td class="fontLeft">
									<input type="text" id="uarValCl0" name="uarValCl0" class="inputText" style="width:110px" maxlength="25" value="${info.uarValCl0}">
								</td>
								<th scope="row"><label id="uarValTxt1" for="uacValCl1" class="uarValTxt"></label></th>
								<td class="fontLeft">
									<input type="text" id="uarValCl1" name="uarValCl1" class="inputText" style="width:110px" maxlength="25" value="${info.uarValCl1}">
								</td>
								<th scope="row"><label id="uarValTxt2" for="uacValCl2" class="uarValTxt"></label></th>
								<td class="fontLeft last">
									<input type="text" id="uarValCl2" name="uarValCl2" class="inputText" style="width:110px" maxlength="25" value="${info.uarValCl2}">
								</td>
							</tr>
							<tr class="trValCl" style="display: none;">
								<th scope="row"><label id="uarValTxt3" for="uacValCl3" class="uarValTxt"></label></th>
								<td class="fontLeft">
									<input type="text" id="uarValCl3" name="uarValCl3" class="inputText" style="width:110px" maxlength="25" value="${info.uarValCl3}">
								</td>
								<th scope="row"><label id="uarValTxt4" for="uacValCl4" class="uarValTxt"></label></th>
								<td class="fontLeft">
									<input type="text" id="uarValCl4" name="uarValCl4" class="inputText" style="width:110px" maxlength="25" value="${info.uarValCl4}">
								</td>
								<th scope="row"><label id="uarValTxt5" for="uacValCl5" class="uarValTxt"></label></th>
								<td class="fontLeft last">
									<input type="text" id="uarValCl5" name="uarValCl5" class="inputText" style="width:110px" maxlength="25" value="${info.uarValCl5}">
								</td>
							</tr>
							<tr class="trValCl" style="display: none;">
								<th scope="row"><label id="uarValTxt6" for="uacValCl6" class="uarValTxt"></label></th>
								<td class="fontLeft">
									<input type="text" id="uarValCl6" name="uarValCl6" class="inputText" style="width:110px" maxlength="25" value="${info.uarValCl6}">
								</td>
								<th scope="row"><label id="uarValTxt7" for="uacValCl7" class="uarValTxt"></label></th>
								<td class="fontLeft">
									<input type="text" id="uarValCl7" name="uarValCl7" class="inputText" style="width:110px" maxlength="25" value="${info.uarValCl7}">
								</td>
								<th scope="row"><label id="uarValTxt8" for="uacValCl8" class="uarValTxt"></label></th>
								<td class="fontLeft last">
									<input type="text" id="uarValCl8" name="uarValCl8" class="inputText" style="width:110px" maxlength="25" value="${info.uarValCl8}">
								</td>
							</tr>
							<tr>
								<th scope="row"><label for="uarAdmPos">자산위치</label></th>
								<td class="fontLeft last" colspan="5">
									<input type="text" class="inputText" id="uarAdmPos" name="uarAdmPos" style="width: 614px;" value="${info.uarAdmPos}">
								</td>
							</tr>
							<tr>
								<th scope="row"><label for="uarSvrEtc">서비스상세</label></th>
								<td class="fontLeft last" colspan="5">
									<input type="text" class="inputText" id="uarSvrEtc" name="uarSvrEtc" style="width: 614px;" value="${info.uarSvrEtc}">
								</td>
							</tr>
							<tr>
								<th scope="row"><label for="uarDtlExp">용도</label></th>
								<td class="fontLeft last" colspan="5">
									<input type="text" class="inputText" id="uarDtlExp" name="uarDtlExp" style="width: 614px;" maxlength="250" value="${info.uarDtlExp}">
								</td>
							</tr>
							<tr>
								<th scope="row">* 사용여부</th>
								<td class="fontLeft">
									<input type="radio" id="uarUseY" name="uarUseYn" value="Y" <c:if test="${'Y' eq info.uarUseYn or empty info.uarAudYn}">checked="checked"</c:if>> <label for="uarUseY">사용</label>
									<input type="radio" id="uarUseN" name="uarUseYn" value="N" <c:if test="${'N' eq info.uarUseYn}">checked="checked"</c:if>> <label for="uarUseN">미사용</label>
								</td>
								<th scope="row">* 샘플링대상<br/>여부</th>
								<td class="fontLeft">
									<input type="radio" id="uarSmpY" name="uarSmpYn" value="Y" <c:if test="${'Y' eq info.uarSmpYn or empty info.uarSmpYn }">checked="checked"</c:if>> <label for="uarSmpY">대상</label>
									<input type="radio" id="uarSmpN" name="uarSmpYn" value="N" <c:if test="${'N' eq info.uarSmpYn}">checked="checked"</c:if>> <label for="uarSmpN">비대상</label>
								</td>
								<th scope="row">* ISMS<br/>인증대상</th>
								<td class="fontLeft last">
									<input type="radio" id="uarAudY" name="uarAudYn" value="Y" <c:if test="${'Y' eq info.uarAudYn or empty info.uarAudYn}">checked="checked"</c:if>> <label for="uarAudY">대상</label>
									<input type="radio" id="uarAudN" name="uarAudYn" value="N" <c:if test="${'N' eq info.uarAudYn}">checked="checked"</c:if>> <label for="uarAudN">비대상</label>
								</td>
							</tr>
							<tr>
								<th scope="row">* 기반시설<br/>대상여부</th>
								<td class="fontLeft">
									<input type="radio" id="uarInfY" name="uarInfYn" value="Y" <c:if test="${'Y' eq info.uarInfYn or empty info.uarInfYn }">checked="checked"</c:if>> <label for="uarInfY">대상</label>
									<input type="radio" id="uarInfN" name="uarInfYn" value="N" <c:if test="${'N' eq info.uarInfYn}">checked="checked"</c:if>> <label for="uarInfN">비대상</label>
								</td>
								<th scope="row">* 개인정보<br/>보유여부</th>
								<td class="fontLeft">
									<input type="radio" id="uarPrvY" name="uarPrvYn" value="Y" <c:if test="${'Y' eq info.uarPrvYn or empty info.uarPrvYn}">checked="checked"</c:if>> <label for="uarPrvY">대상</label>
									<input type="radio" id="uarPrvN" name="uarPrvYn" value="N" <c:if test="${'N' eq info.uarPrvYn}">checked="checked"</c:if>> <label for="uarPrvN">비대상</label>
								</td>
								<th scope="row"><label for="uarRskGrp">취약점분류</label></th>
								<td class="fontLeft last">
									<input type="text" class="inputText readonly" id="uarRskNam" name="uarRskNam" style="width:108px" readonly="readonly" value="${info.uarRskNam}">
								</td>
							</tr>
							<tr>
								<th scope="row"><label for="uarAppCon">평가항목</label></th>
								<td class="fontLeft last" colspan="5">
									<ul class="dualListArea list4">
										<li>
											<label for="uarAppCon"><strong>기밀성</strong></label>
											<select id="uarAppCon" name="uarAppCon" class="selectBox" title="항목 선택" onchange="getImportanceScore();" style="width: 80px;">
												<option value="0" <c:if test="${info.uarAppCon == '0'}">selected="selected"</c:if> >0</option>
												<option value="1" <c:if test="${info.uarAppCon == '1'}">selected="selected"</c:if> >1</option>
												<option value="2" <c:if test="${info.uarAppCon == '2'}">selected="selected"</c:if> >2</option>
												<option value="3" <c:if test="${info.uarAppCon == '3'}">selected="selected"</c:if> >3</option>
											</select>
										</li>
										<li>
											<label for="uarAppItg"><strong>무결성</strong></label>
											<select id="uarAppItg" name="uarAppItg" class="selectBox" title="항목 선택" onchange="getImportanceScore();" style="width: 80px;">
												<option value="0" <c:if test="${info.uarAppItg == '0'}">selected="selected"</c:if> >0</option>
												<option value="1" <c:if test="${info.uarAppItg == '1'}">selected="selected"</c:if> >1</option>
												<option value="2" <c:if test="${info.uarAppItg == '2'}">selected="selected"</c:if> >2</option>
												<option value="3" <c:if test="${info.uarAppItg == '3'}">selected="selected"</c:if> >3</option>
											</select>
										</li>
										<li>
											<label for="uarAppAvt"><strong>가용성</strong></label>
											<select id="uarAppAvt" name="uarAppAvt" class="selectBox" title="항목 선택" onchange="getImportanceScore();" style="width: 80px;">
												<option value="0" <c:if test="${info.uarAppAvt == '0'}">selected="selected"</c:if> >0</option>
												<option value="1" <c:if test="${info.uarAppAvt == '1'}">selected="selected"</c:if> >1</option>
												<option value="2" <c:if test="${info.uarAppAvt == '2'}">selected="selected"</c:if> >2</option>
												<option value="3" <c:if test="${info.uarAppAvt == '3'}">selected="selected"</c:if> >3</option>
											</select>
										</li>
										<li>
											<label for="uarAssLvl"><strong>등급</strong></label>
											<input type="text" class="inputText readonly" id="uarAssLvl" name="uarAssLvl" style="width:73px" readonly="readonly" value="${info.uarAssLvl}">
											<input type="hidden" id="uarAppTot" name="uarAppTot" value="${info.uarAppTot}">
										</li>
									</ul>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div class="centerBtnArea bottomBtnArea">
               <button type="button" class="popClose close"><span class="icoClose">닫기</span></button>
               <ul class="btnList">
				<li><button type="button" class="btnStrong" onclick="fn_save();"><span class="icoSave"></span>저장</button></li>
			</ul>
		</div>
	</article>
</div>
</form>
</body>