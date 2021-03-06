<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<script type="text/javascript">
function go2Depth(){
	frm = document.listForm;
   	frm.action = "FM-COMPS003_2D.do"
	frm.submit();
}

function go1Depth(key){
	frm = document.listForm;
   	frm.action = "FM-COMPS003.do"
	frm.submit();
}

function formList(mapKey){
	window.open("","getMappingFormPopup","width=750, height=500, menubar=no, location=no, status=no, scrollbars=yes");
	$("#mapKey").val(mapKey);
	$("#mapService").val($("#service").val());
	document.mappingForm.submit();
}

function delmapping(brdKey){
	$("#brdKey").val(brdKey);
	frm = document.listForm;
	frm.action = "FM-COMPS003_DEL_MAPPING.do";
	frm.submit();
}

function brdPopup(brdKey){
	window.open("","getbrdView","width=750, height=500, menubar=no, location=no, status=no, scrollbars=yes");
	$("#brdViewKey").val(brdKey);
	document.brdViewForm.submit();
}

function goPopReload(){
	frm = document.listForm;
   	frm.action = "FM-COMPS003_3D.do"
	frm.submit();
}
</script>
</head>
<body>
<!-- listExpData 처리해야 함-->
<form name="listForm" id="listForm" method="post">
<input type="hidden" id="hUcm1lvCod" name="hUcm1lvCod" value="${ucm1lvCod}">
<input type="hidden" id="hUcm2lvCod" name="hUcm2lvCod" value="${ucm2lvCod}">
<input type="hidden" id="standard" name="standard" value="${standard}">
<input type="hidden" id="service" name="service" value="${service}">
<input type="hidden" id="brdKey" name="brdKey">
<c:import url="/titlebar.do" />
<div class="contents">
	<div class="tabArea">
	    <ul class="list3Tab">
	        <li><a href="javascript:go1Depth();">통제목적</a></li>
	        <li><a href="javascript:go2Depth();">통제항목</a></li>
	        <li class="sel"><a href="#none">통제점검</a></li>
	    </ul>
	</div>
	<div class="talbeArea">
		<table summary="통제항목설정">
			<colgroup>
				<col width="7%">
				<col width="17%">
				<col width="17%">
				<col width="17%">
				<col width="*">
				<col width="10%">
				<col width="8%">
			</colgroup>
			<caption>통제항목설정 현황</caption>
			<thead>
			    <tr>
					<th rowspan="2" scope="col">통제<br/>점검<br/>번호</th>
					<th colspan="3" scope="col">점검항목</th>
					<th rowspan="2" scope="col">문서 (정책, 지침서 등)</th>
					<th rowspan="2" scope="col">서비스 맵핑</th>
					<th rowspan="2" scope="col" class="last">최종<br/>수정일</th>
			    </tr>
				<tr>
					<th scope="col">Ref No</th>
					<th scope="col">등급</th>
					<th scope="col">점수</th>
				</tr>
			</thead>
			<tbody id="tbody">
			<c:forEach var="result" items="${resultList}" varStatus="status">
				<tr>
					<td rowspan="2">${result.ucm3lvCod}</td>
					<td colspan="3" style="text-align: left">${result.ucm3lvNam}</td>
					<td rowspan="2">
						<div class="uplidFileList">
                            <ul class="listArea" style="width: 230px; height: auto; min-height: 30px; max-height:96px;">
                            	<c:forEach var="res" items="${brdlist}" varStatus="status2">
                            	  <c:if test="${result.ucmCtrKey == res.ucbCtrKey }">
                                	<li>
                                		<a href="javascript:brdPopup('<c:out value="${res.ubmBrdKey}"/>')"><span class="icoDown"></span><c:out value="${res.ubmBrdTle}"/></a>
                                		<a href="javascript:delmapping('${res.ubmBrdKey}');" class="del" title="삭제">x<em class="hidden">삭제</em></a>
                                	</li>
                                  </c:if>
                                </c:forEach>
                            </ul>
                        </div>
                        <button onclick="formList('${result.ucmCtrKey}'); return false;" style="padding: 0px 5px;"><span class="icoUpload"></span>관련문서 등록</button>
					</td>
					<td rowspan="2">
						<ul>
							<c:forEach var="serviceNode" items="${serviceNode}" varStatus="status4">
								<c:if test="${serviceNode.ucmCtrKey eq result.ucmCtrKey}">
									<c:forEach var="serviceList" items="${serviceList}" varStatus="status3">
										<c:if test="${serviceNode.ucmSvcCod eq serviceList.code}">
											<li>${serviceList.name}</li>
										</c:if>
									</c:forEach>
								</c:if>
							</c:forEach>
						</ul>
					</td>
					<td rowspan="2" class="last">
						<c:if test="${result.ucmUpdMdh eq null}">${result.ucmRgtMdh}</c:if>
                        <c:if test="${result.ucmUpdMdh ne null}">${result.ucmUpdMdh}</c:if>
					</td>
				</tr>
				<tr>
					<c:forEach var="expData" items="${listExpData}" varStatus="status2">
						<c:if test="${expData.ctrKey==result.ucmCtrKey&&expData.lvlCod==result.ucm3lvCod}">
							<td style="height: 20px;">${expData.refNo}</td>
							<td style="height: 20px;">${expData.grade}</td>
							<td style="height: 20px;">${expData.point}</td>
						</c:if>
					</c:forEach>
				</tr>
			</c:forEach>
			</tbody>
		</table>
    </div>
</div>
</form>
<form id="mappingForm" name="mappingForm" action="FM-COMPS003_popup.do" method="post" target="getMappingFormPopup">
	<input type="hidden" name="mapKey" id="mapKey" value="">
	<input type="hidden" name="mapService" id="mapService" value="">
</form>
<form id="brdViewForm" name="brdViewForm" action="FM-COMPS003_brd_popup.do" method="post" target="getbrdView">
	<input type="hidden" name="brdViewKey" id="brdViewKey" value="">
</form>
</body>
</html>