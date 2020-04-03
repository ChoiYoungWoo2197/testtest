<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="validator"	uri="http://www.springmodules.org/tags/commons-validator"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
pageContext.setAttribute("crlf", "\r\n"); 
pageContext.setAttribute("cr", "\r"); 
pageContext.setAttribute("lf", "\n"); 
%>
<script type="text/javaScript" language="javascript" defer="defer">
	/* 글 목록 화면 function */
	function fn_egov_selectList() {
		$('#sForm').attr('action','${pageContext.request.contextPath}/setup/FM-SETUP020.do').submit();
	}
	
	/* 글 수정 function */
	function fn_egov_save() {
		$('#sForm').attr('action','${pageContext.request.contextPath}/setup/FM-SETUP020_RW.do').submit();
	}
	
 	function getFile(key) {
		$("#downKey").val(key);
		$('#fileDown').attr('action','${pageContext.request.contextPath}/common/getFileDown.do').submit();
	}
 	
 	function reportDown(){
 		window.open("","reportDown","width=1024, height=720, menubar=no, location=no, status=no,scrollbars=yes");
 		$('form[name=rFrom]').attr('target','reportDown').attr('action','${pageContext.request.contextPath}/report/FM-SETUP020.do').submit();
 	}
</script>
	<p class="history">
		<a href="#none">시스템관리</a><span>&gt;</span>서비스관리
	</p>
	<div class="conttitle">서비스관리</div>
	<div class="contents">
	<form id="sForm" name="sForm" method="post">
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
						<th scope="row" class="listTitle">서비스코드</th>
						<td class="fontLeft">
							${list.service }
						</td>
						<th scope="row" class="listTitle">심사주기</th>
						<td class="fontLeft last">
							${list.uvmBcyDat }
						</td>
					</tr>
					<tr>
						<th scope="row" class="listTitle">서비스명</th>
						<td colspan="3" class="fontLeft last">${list.uvmSvcNam}</td>
					</tr>
					<tr>
						<th scope="row" class="listTitle">서비스 설명</th>
						<td class="fontLeft last" colspan="3">
							${fn:replace(list.uvmSvcDes, lf, "<br/>")}
						</td>
					</tr>
					<tr>
						<th scope="row" class="listTitle">웹페이지</th>
						<td class="fontLeft last" colspan="3">${list.uvmWebUrl}</td>
					</tr>
					<tr>
						<th scope="row" class="listTitle">외주업체 현황</th>
						<td class="fontLeft last" colspan="3">
							${fn:replace(list.uvmPrdDes, lf, "<br/>")}
						</td>
					</tr>
					<tr>
						<th scope="row" class="listTitle">개인정보 포함여부</th>
						<td class="fontLeft">${list.uvmPifYnNam}</td>
						<th scope="row" class="listTitle">인증의무대상 여부</th>
						<td class="fontLeft last">${list.uvmDutYnNam}</td>
					</tr>
					<tr>
						<th scope="row" class="listTitle">인증의무대상 기준</th>
						<td class="fontLeft last" colspan="3">
							${fn:replace(list.uvmDutDes, lf, "<br/>")}
						</td>
					</tr>
					<tr>
						<th scope="row" class="listTitle">이용자(고객)</th>
						<td class="fontLeft">${list.uvmUsePer}</td>
						<th scope="row" class="listTitle">회원수</th>
						<td class="fontLeft last">${list.uvmMenCnt}</td>
					</tr>
					<tr>
						<th scope="row" class="listTitle">인증 희망사유</th>
						<td class="fontLeft last" colspan="3">
							${fn:replace(list.uvmCofRes, lf, "<br/>")}
						</td>
					</tr>
					<tr>
						<th scope="row" class="listTitle">전체 조직도</th>
						<td class="fontLeft last" colspan="3">
							<c:if test="${list.uvmAllOrg eq 'Y' and fn:length(fileAll) > 0 }">
							<div class="uplidFileList">
								<ul class="listArea" style="height:auto;width:auto;">
								<c:forEach var="result" items="${fileAll}" varStatus="status">
									<li><a href="#none" onclick="getFile(<c:out value='${result.umf_fle_key}'/>)"><span class="icoDown"></span><c:out value="${result.umf_con_fnm}"/></a></li>
								</c:forEach>
								</ul>
							</div>
							</c:if>
						</td>
					</tr>
					<tr>
						<th scope="row" class="listTitle">정보보호 조직도</th>
						<td class="fontLeft last" colspan="3">
							<c:if test="${list.uvmInfOrg eq 'Y' and fn:length(fileInf) > 0 }">
							<div class="uplidFileList">
								<ul class="listArea" style="height:auto;width:auto;">
								<c:forEach var="result" items="${fileInf}" varStatus="status">
									<li><a href="#none" onclick="getFile(<c:out value='${result.umf_fle_key}'/>)"><span class="icoDown"></span><c:out value="${result.umf_con_fnm}"/></a></li>
								</c:forEach>
								</ul>
							</div>
							</c:if>
						</td>
					</tr>
					<tr>
						<th scope="row" class="listTitle">기타사항</th>
						<td class="fontLeft last" colspan="3">
							${fn:replace(list.uvmEtcDes, lf, "<br/>")}
						</td>
					</tr>
					<tr>
						<th class="listTitle" scope="row">사용여부</th>
						<td class="fontLeft last" colspan="3" >
							<c:if test="${list.uvmUseYn eq 'Y' or empty list.uvmUseYn}">사용</c:if><c:if test="${list.uvmUseYn eq 'N'}">미사용</c:if>
						</td>
					</tr>
				</tbody>
			</table>				
		</div>
		<div class="bottomBtnArea">
			<ul class="btnList">
				<li>
					<button class="btnStrong" onclick="reportDown(); return false;">
						<span class="icoDown"></span>PDF다운로드
					</button>
				</li>
				<li>
					<button class="btnStrong" onclick="fn_egov_save();">
						<span class="icoRepair"></span>수정
					</button>
				</li>
				<li>
					<button class="btnStrong" onclick="fn_egov_selectList();">
						<span class="icoList"></span>목록
					</button> 
				</li>
			</ul>
		</div>
	</form>
	<form action="" name="fileDown" id="fileDown" method="post">
   		<input type="hidden" name="downKey" id="downKey">
   		<input type="hidden" name="where" id="where" value="svc">
   	</form>
   	<form name="rFrom" method="post">
   		<input type="hidden" name="uvmSvcKey" value="${list.uvmSvcKey }" />
   	</form>
</div>