<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="validator"	uri="http://www.springmodules.org/tags/commons-validator"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script type="text/javaScript" language="javascript" defer="defer">
	$(document).ready(function() {

	});

	function fn_mnu_save(gubun) {
		if ($("#ummMnuNam").val() == "") {
			alert("메뉴명을 입력하세요.");
			$("#ummMnuNam").focus();
			return;
		}

		if ($("#ummMnuUrl").val() == "") {
			alert("메뉴 URL을 입력하세요.");
			$("#ummMnuUrl").focus();
			return;
		}

		var numChk = /^[0-9]*$/
		if (!numChk.test($("#ummOdrNum").val())) {
			alert("숫자만 입력하세요.");
			$("#ummOdrNum").val("");
			$("#ummOdrNum").focus();
			return;
		}


		var strUrl = "";
		if (gubun == "I") {
			strUrl = "${pageContext.request.contextPath}/setup/FM-SETUP016_W.do";
		} else {
			strUrl = "${pageContext.request.contextPath}/setup/FM-SETUP016_U.do";
		}

		$.ajax({
			url : strUrl,
			type : "post",
			data : $("#mnuForm").serialize(),
			dataType : "json",
			success : function(data) {
				alert("저장 되었습니다.");
				fn_selectList();

			},
			error : function() {
				alert('error');
			}
		});
	}

	/* 글 목록 화면 function */
	function fn_selectList() {
		var ummMnuPtr = document.mnuForm.ummMnuPrt.value;
		if (ummMnuPtr == "") {
			document.mnuForm.action = "FM-SETUP016.do";
		} else {
			document.mnuForm.ummMnuKey.value = ummMnuPtr;
			document.mnuForm.action = "FM-SETUP016_N.do";
		}
		document.mnuForm.submit();
	}
</script>
	<c:import url="/titlebar.do" />
	<div class="contents">
	<form id="mnuForm" name="mnuForm" method="post">
		<input type="hidden" id="ummMnuKey" name="ummMnuKey" value="${mnuInfo.ummMnuKey}"/>
		<input type="hidden" id="ummMnuPrt" name="ummMnuPrt" value="${paramMap.ummMnuPrt}"/>
		<input type="hidden" id="ummMnuPrtNam" name="ummMnuPrtNam" value="${paramMap.ummMnuPrtNam}">
		<input type="hidden" id="searchCondition" name="searchCondition" value="${paramMap.searchCondition}"/>
		<div class="talbeArea">
			<table>
				<colgroup>
					<col width="20%">
					<col width="30%">
					<col width="20%">
					<col width="30%">
				</colgroup>
				<tbody>
					<tr>
						<th scope="row" class="listTitle"><label for="ummMnuNam">* 메뉴명</label></th>
						<td class="fontLeft"><input type="text" id="ummMnuNam" name="ummMnuNam" value="${mnuInfo.ummMnuNam}" maxlength="25" class="inputText"  style="width:150px" /></td>
						<th scope="row"><label for="ummOdrNum">* 순번</label></th>
						<td class="fontLeft last"><input type="text" id="ummOdrNum" name="ummOdrNum" value="${mnuInfo.ummOdrNum}" maxlength="2" class="inputText" style="width:50px" /></td>
					</tr>
					<tr>
						<th scope="row" class="listTitle"><label for="ummMnuUrl">* 메뉴 URL</label></th>
						<td colspan="3" class="fontLeft last"><input type="text" id="ummMnuUrl" name="ummMnuUrl" value="${mnuInfo.ummMnuUrl}" maxlength="200" class="inputText" style="width:550px" /></td>
					</tr>
					<tr>
						<th scope="row" class="listTitle"><label for="ummMnuEtc">메뉴 설명</label></th>
						<td colspan="3" class="fontLeft last"><textarea id="ummMnuEtc" name="ummMnuEtc" maxlength="250" class="txtAreaType03"><c:out value="${mnuInfo.ummMnuEtc}"/></textarea></td>
					</tr>
					<tr>
						<th scope="row" class="listTitle">* 빠른 서비스</th>
						<td class="fontLeft">
							<input type="radio" id="ummMnuSpdY" name="ummMnuSpd" value="Y" <c:if test="${mnuInfo.ummMnuSpd eq 'Y'}">checked="checked"</c:if> />  <label for="ummMnuSpdY">사용</label>
							<input type="radio" id="ummMnuSpdN" name="ummMnuSpd" value="N" <c:if test="${mnuInfo.ummMnuSpd eq 'N' or empty mnuInfo.ummMnuSpd}">checked="checked"</c:if> />  <label for="ummMnuSpdN">미사용</label>
						</td>
						<th scope="row" class="listTitle">* 메뉴 권한</th>
						<td class="fontLeft last">
							<input type="radio" id="ummMnuLvl01" name="ummMnuLvl" value="1" <c:if test="${mnuInfo.ummMnuLvl eq '1' or empty mnuInfo.ummMnuLvl}">checked="checked"</c:if> /> <label for="ummMnuLvl01">일반</label>
							<input type="radio" id="ummMnuLvl02" name="ummMnuLvl" value="2" <c:if test="${mnuInfo.ummMnuLvl eq '2'}">checked="checked"</c:if> /> <label for="ummMnuLvl02">관리자</label>
						</td>
					</tr>
					<tr>
						<th scope="row" class="listTitle">사용여부</th>
						<td colspan="3" class="fontLeft last">
							<input type="radio" id="ummUseY" name="ummUseYn" value="Y" <c:if test="${mnuInfo.ummUseYn eq 'Y' or empty mnuInfo.ummUseYn}">checked="checked"</c:if> />  <label for="ummUseY">사용</label>
							<input type="radio" id="ummUseN" name="ummUseYn" value="N" <c:if test="${mnuInfo.ummUseYn eq 'N'}">checked="checked"</c:if> />  <label for="ummUseN">미사용</label>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="bottomBtnArea">
			<ul class="btnList">
			<c:if test="${empty mnuInfo.ummMnuKey }">
				<li>
			    	<button class="btnStrong" onclick="fn_mnu_save('I'); return false;" ><span class="icoAdd"></span>등록</button>
				</li>
			</c:if>
			<c:if test="${not empty mnuInfo.ummMnuKey }">
				<li>
				    <button class="btnStrong" onclick="fn_mnu_save('U'); return false;"><span class="icoRepair"></span>수정</button>
				</li>
			</c:if>
				<li>
				    <button class="btnStrong" onclick="fn_selectList(); return false;" ><span class="icoList"></span>목록</button>
				</li>
			</ul>
		</div>
	</form>
</div>