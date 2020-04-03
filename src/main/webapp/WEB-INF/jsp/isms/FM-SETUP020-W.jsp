<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="validator"	uri="http://www.springmodules.org/tags/commons-validator"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
<script type="text/javaScript" language="javascript" defer="defer">
	function fn_svc_save(gubun) {
		if ($("select[name=uvmSvcCod]").val() == "") {
			alert("서비스코드를 선택하세요.");
			$("select[name=uvmSvcCod]").focus();
			return;
		}
		
		if ($("select[name=uvmBcyCod]").val() == "") {
			alert("심사주기를 선택하세요.");
			$("select[name=uvmBcyCod]").focus();
			return;
		}
		
		if ($("input[name=uvmSvcNam]").val() == "") {
			alert("서비스명을 입력하세요.");
			$("input[name=uvmSvcNam]").focus();
			return;
		}
			
		var numChk = /^[0-9]*$/
		if (!numChk.test($("input[name=uvmMenCnt]").val())) {
			alert("회원수는 숫자만 입력하세요.");
			$("input[name=uvmMenCnt]").val("");
			$("input[name=uvmMenCnt]").focus();
			return;
		}		

		var strUrl = "";
		if (gubun == "I") {
			strUrl = "${pageContext.request.contextPath}/setup/FM-SETUP020_W.do";
		} else {
			strUrl = "${pageContext.request.contextPath}/setup/FM-SETUP020_U.do";
		}

		/*
		$.ajax({
			url : strUrl,
			type : "post",
			data : $("#sForm").serialize(),
			dataType : "json",
			processData: false,
			contentType: false,
			success : function(data) {
				alert("저장 되었습니다.");
				fn_selectList();

			},
			error : function() {
				alert('error');
			}
		});
		*/
		$("#sForm").attr('action',strUrl).submit();
	}

	/* 글 목록 화면 function */
	function fn_selectList() {
		document.sForm.action = "FM-SETUP020.do";
		document.sForm.submit();
	}
	
	function setFielDel(mode) {
		if(mode == 'All'){
			$('input[name=uvmAllOrg]').val('N');
			$('input[name=uvmAllOrgfile]').val('');
		}else{
			$('input[name=uvmInfOrg]').val('N');
			$('input[name=uvmInfOrgfile]').val('');
		}
	}
	
	function chkFileExt($fileVal, $chkExt){
		var result = true;			
		if( $fileVal != null && $fileVal != '' ) {
			var splitLength = ($fileVal.split('.')).length;
			var ext = ($fileVal.split(".")[splitLength-1]).toUpperCase();
			
			for(var i=0; i<$chkExt.length; i++){
				if(ext != $chkExt[i].toUpperCase()){
					result = false;
				}else{
					result = true;					
					break;
				}
			}
		}		
		return result;
	}
	
	$(document).ready(function() {
		$('select[name=uvmSvcCod]').bind('change', function(){
			$('input[name=uvmSvcNam]').val($('select[name=uvmSvcCod] option:selected').text());
			$("input[name=uvmSvcNam]").focus();
		});
		$('input[type=file]').bind('change', function(){
			if($(this).val().length > 0){
				if(chkFileExt($(this).val(), ['JPG','GIF','JPEG','PNG'])){
					$(this).closest('td').find($('input[type=hidden]')).val('Y');
					return false;
				}else{
					$(this).val('');
					alert('이미지 파일만 등록 가능합니다.');
					return false;
				}
			}else{
				return false;				
			}
		});		
	});
	
 	function delFile_U(key){
 		if(confirm("해당 파일을 삭제하시겠습니까?")){
 			$.ajax({
 				url		: "${pageContext.request.contextPath}/common/delFile.do",
 				type		: "post",
 				data		: {"key":key},
 				dataType	: "json",
 				success	: function(e){		
 					alert("삭제되었습니다.");
 				 },
 				 error : function(){
 					alert("실패");
 					//
 				 },
 				 complete : function(){
 					$("#sForm").attr('action','${pageContext.request.contextPath}/setup/FM-SETUP020_RW.do').submit();
 				 }
 			 });		
 		}
 	}
</script>
	<p class="history">
		<a href="#none">시스템관리</a><span>&gt;</span>서비스관리
	</p>
	<div class="conttitle">서비스관리</div>
	<div class="contents">
	<form id="sForm" name="sForm" method="post" enctype="multipart/form-data">
		<input type="hidden" name="uvmSvcKey" value="${list.uvmSvcKey }" />
		<input type="hidden" name="service" value="${paramMap.service }" />
		<input type="hidden" name="workCycleTerm" value="${paramMap.workCycleTerm}" />
		<input type="hidden" name="searchCondition" value="${paramMap.searchCondition}"/>
		<div class="talbeArea">
			<table>
				<colgroup>
					<col width="25%">
					<col width="20%">
					<col width="25%">
					<col width="30%">
				</colgroup>					
				<tbody>
					<tr>
						<th scope="row" class="listTitle">* 서비스코드</th>
						<td class="fontLeft">
							<sb:select name="uvmSvcCod" type="S" value="${list.uvmSvcCod }"/>
						</td>
						<th scope="row" class="listTitle">* 심사주기</th>
						<td class="fontLeft last">
							<select name="uvmBcyCod" class="selectBox" style="width:90%;" title="심사주기 선택">
									<option value="">선택</option>
									<c:forEach var="cycle" items="${nonCycle }" varStatus="status">
										<option value="${cycle.code }" <c:if test="${cycle.code eq list.uvmBcyCod }">selected="selected"</c:if>>${cycle.name }</option>
									</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th scope="row" class="listTitle">* 서비스명</th>
						<td colspan="3" class="fontLeft last">
							<input type="text" name="uvmSvcNam" value="${list.uvmSvcNam}" maxlength="200" class="inputText" style="width:98%;" title="서비스명 입력"/>
						</td>
					</tr>
					<tr>
						<th scope="row" class="listTitle">서비스 설명</th>
						<td class="fontLeft last" colspan="3">
							<textarea name="uvmSvcDes" maxlength="250" class="txtAreaType03" title="서비스 설명 입력">${list.uvmSvcDes}</textarea>
						</td>
					</tr>
					<tr>
						<th scope="row" class="listTitle">웹페이지</th>
						<td class="fontLeft last" colspan="3">
							<input type="text" name="uvmWebUrl" value="${list.uvmWebUrl}" maxlength="200" class="inputText" style="width:98%;" title="웹페이지 입력"/>
						</td>
					</tr>
					<tr>
						<th scope="row" class="listTitle">외주업체 현황</th>
						<td class="fontLeft last" colspan="3">
							<textarea name="uvmPrdDes" maxlength="250" class="txtAreaType03" title="외주업체 현황 입력">${list.uvmPrdDes}</textarea>
						</td>
					</tr>
					<tr>
						<th scope="row" class="listTitle">개인정보 포함여부</th>
						<td class="fontLeft">
							<input type="radio" id="uvmPifY" name="uvmPifYn" value="Y" <c:if test="${list.uvmPifYn eq 'Y' or empty list.uvmPifYn}">checked="checked"</c:if> />  <label for="uvmPifY">대상</label>
							<input type="radio" id="uvmPifN" name="uvmPifYn" value="N" <c:if test="${list.uvmPifYn eq 'N'}">checked="checked"</c:if> />  <label for="uvmPifN">비대상</label>
						</td>
						<th scope="row" class="listTitle">인증의무대상 여부</th>
						<td class="fontLeft last">
							<input type="radio" id="uvmDutY" name="uvmDutYn" value="Y" <c:if test="${list.uvmDutYn eq 'Y' or empty list.uvmDutYn}">checked="checked"</c:if> />  <label for="uvmDutY">인증</label>
							<input type="radio" id="uvmDutN" name="uvmDutYn" value="N" <c:if test="${list.uvmDutYn eq 'N'}">checked="checked"</c:if> />  <label for="uvmDutN">비인증</label>
						</td>
					</tr>
					<tr>
						<th scope="row" class="listTitle">인증의무대상 기준</th>
						<td class="fontLeft last" colspan="3">
							<textarea name="uvmDutDes" maxlength="250" class="txtAreaType03" title="인증의무대상 기준 입력">${list.uvmDutDes}</textarea>
						</td>
					</tr>
					<tr>
						<th scope="row" class="listTitle">이용자(고객)</th>
						<td class="fontLeft">
							<input type="text" name="uvmUsePer" value="${list.uvmUsePer}" maxlength="200" class="inputText" title="이용자(고객) 입력"/>
						</td>
						<th scope="row" class="listTitle">회원수</th>
						<td class="fontLeft last">
							<input type="text" name="uvmMenCnt" value="${list.uvmMenCnt}" maxlength="200" class="inputText" title="회원수 입력"/>
						</td>
					</tr>
					<tr>
						<th scope="row" class="listTitle">인증 희망사유</th>
						<td class="fontLeft last" colspan="3">
							<textarea name="uvmCofRes" maxlength="250" class="txtAreaType03" title="인증 희망사유 입력">${list.uvmCofRes}</textarea>
						</td>
					</tr>
					<tr>
						<th scope="row" class="listTitle">전체 조직도</th>
						<td class="fontLeft last" colspan="3">
							<input type="hidden" name="uvmAllOrg" value="${list.uvmAllOrg}<c:if test="${empty list.uvmAllOrg }" >N</c:if>" />
							<input type="file" name="uvmAllOrgfile" style="width:90%;" title="전체 조직도 등록"/>
							<button onclick="setFielDel('All'); return false;">삭제</button>
							<c:if test="${list.uvmAllOrg eq 'Y' and fn:length(fileAll) > 0 }">
							<div class="uplidFileList">
								<ul class="listArea" style="height:auto;width:auto;">
								<c:forEach var="result" items="${fileAll}" varStatus="status">
									<li><a href="#none"><span class="icoDown"></span><c:out value='${result.umf_con_fnm}'/></a>
										<a class="del" title="삭제" onclick="delFile_U(<c:out value='${result.umf_fle_key}'/>)">x<em class="hidden">삭제</em></a>													
									</li>
								</c:forEach>
								</ul>
							</div>
							</c:if>
						</td>
					</tr>
					<tr>
						<th scope="row" class="listTitle">정보보호 조직도</th>
						<td class="fontLeft last" colspan="3">
							<input type="hidden" name="uvmInfOrg" value="${list.uvmInfOrg}<c:if test="${empty list.uvmInfOrg }" >N</c:if>" />
							<input type="file" name="uvmInfOrgfile" style="width:90%;" title="전체 조직도 등록"/>
							<button onclick="setFielDel('Inf'); return false;">삭제</button>
							<c:if test="${list.uvmInfOrg eq 'Y' and fn:length(fileInf) > 0 }">
							<div class="uplidFileList">
								<ul class="listArea" style="height:auto;width:auto;">
								<c:forEach var="result" items="${fileInf}" varStatus="status">
									<li><a href="#none"><span class="icoDown"></span><c:out value='${result.umf_con_fnm}'/></a>
										<a class="del" title="삭제" onclick="delFile_U(<c:out value='${result.umf_fle_key}'/>)">x<em class="hidden">삭제</em></a>													
									</li>
								</c:forEach>
								</ul>
							</div>
							</c:if>
						</td>
					</tr>
					<tr>
						<th scope="row" class="listTitle">기타사항</th>
						<td class="fontLeft last" colspan="3">
							<textarea name="uvmEtcDes" maxlength="250" class="txtAreaType03" title="기타사항 입력">${list.uvmEtcDes}</textarea>
						</td>
					</tr>
					<tr>
						<th class="listTitle" scope="row">사용여부</th>
						<td class="fontLeft last" colspan="3" >
							<input type="radio" id="uvmUseY" name="uvmUseYn" value="Y" <c:if test="${list.uvmUseYn eq 'Y' or empty list.uvmUseYn}">checked="checked"</c:if> />  <label for="uvmUseY">사용</label>
							<input type="radio" id="uvmUseN" name="uvmUseYn" value="N" <c:if test="${list.uvmUseYn eq 'N'}">checked="checked"</c:if> />  <label for="uvmUseN">미사용</label>
						</td>
					</tr>
				</tbody>
			</table>				
		</div>
		<div class="bottomBtnArea">
			<ul class="btnList">
			<c:if test="${empty list.uvmSvcKey }">
				<li>
			    	<button class="btnStrong" onclick="fn_svc_save('I'); return false;" ><span class="icoAdd"></span>등록</button>
				</li>
			</c:if>
			<c:if test="${not empty list.uvmSvcKey }">
				<li>
				    <button class="btnStrong" onclick="fn_svc_save('U'); return false;"><span class="icoRepair"></span>수정</button>
				</li>
			</c:if>
				<li>
				    <button class="btnStrong" onclick="fn_selectList(); return false;" ><span class="icoList"></span>목록</button>
				</li>
			</ul>
		</div>
	</form>
</div>