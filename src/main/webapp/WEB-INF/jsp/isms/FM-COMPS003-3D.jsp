<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<script type="text/javascript">
$(function(){
	$(".mark").click(function(){
		$("#sts_"+$(this).attr("sIndex")).val("U");
	});
});
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

function addCTR(){

	var inData = '<tr><td rowspan="2"><input type="text" class="inputText" style="width:50px;" name="ucm3lvCod" maxlength="10"></td>' +
		'<td><textarea class="mark" name="ucm3lvNam" maxlength="1500" style="border: 1px solid #ddd;width:430px;height: 40px;resize: none;"></textarea></td>' +
		'<td rowspan="2" colspan="2"><input type="hidden" name="sts" value="C" /></td>' +
		'<td rowspan="2" colspan="2" class="last"><button onclick="addDel(this);">삭제</button></td></tr>' +
		'<tr><td><textarea class="mark" name="ucm3lvDtl" maxlength="1500" style="border: 1px solid #ddd;width:430px; height: 80px;resize: none;"></textarea></td></tr>';
	$("#tbody").append(inData);
	$("input[name='ucm3lvCod']").last().focus();
}

function addDel(obj){
	$(obj).parents("tr").next().remove();
	$(obj).parents("tr").remove();
}

function sav(){
	if(!arrValidation($("input[name=ucm3lvCod]"))) {
		alert("통제항목번호를 입력하세요.");
		return;
	}

	if(!arrValidation($("input[name=ucm3lvNam]"))) {
		alert("점검항목을 입력하세요.");
		return;
	}

	if(!arrValidation($("input[name=ucm3lvDtl]"))) {
		alert("설명을 입력하세요.");
		return;
	}

	var arrCod = [];
	for(var i=0; i<$("[name=ucm3lvCod]").length; i++){
		if($.inArray($("[name=ucm3lvCod]")[i].value, arrCod)>=0){
			alert("통제점검번호가 중복되었습니다.");
			$("[name=ucm3lvCod]")[i].focus();
			return ;
		}else{
			arrCod.push($("[name=ucm3lvCod]")[i].value);
		}
	}

	frm = document.listForm;
   	frm.action = "FM-COMPS003_3D_SAV.do";
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

function reportDown(){
	window.open("","reportDown","width=1024, height=720, menubar=no, location=no, status=no,scrollbars=yes");
	$('form[name=searchData]').attr('target','reportDown').attr('action','${pageContext.request.contextPath}/report/FM-COMPS003.do').submit();
}
</script>
</head>
<body>

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
	<div class="topBtnArea">
		<ul class="btnList">
			<!--li><button onclick="excelUpload(); return false;"><span class="icoExl"></span>엑셀업로드</button></li-->
			<li><button onclick="addCTR(); return false;"><span class="icoAdd"></span>추가</button></li>
			<li><button onclick="sav(); return false;"><span class="icoSave"></span>저장</button></li>
			<!-- <li><button onclick="reportDown(); return false;"><span class="icoDown"></span>PDF다운로드</button></li> -->
		</ul>
	</div>
	<div class="talbeArea">
		<table summary="통제항목설정">
			<colgroup>
				<col width="8%">
				<col width="">
				<col width="20%">
				<col width="12%">
				<col width="8%">
				<col width="8%">
			</colgroup>
			<caption>통제항목설정 현황</caption>
			<thead>
				<tr>
					<th rowspan="2" scope="col">통제<br/>점검<br/>번호</th>
					<th scope="col">점검항목</th>
					<th rowspan="2" scope="col">문서<br/>(정책, 지침서 등)</th>
					<th rowspan="2" scope="col">서비스<br/>맵핑</th>
					<th rowspan="2" scope="col">사용<br/>여부</th>
					<th rowspan="2" scope="col" class="last">최종<br/>수정일</th>
				</tr>
				<tr>
					<th scope="col">설명</th>
				</tr>
			</thead>
			<tbody id="tbody">
			<c:forEach var="result" items="${resultList}" varStatus="status">
				<tr>
					<td rowspan="2">
						<input type="hidden" id="sts_${status.index}" name="sts" value="R" />
						<input type="hidden" name="ucm3lvCod" value="<c:out value="${result.ucm3lvCod}"/>"/>
						<input type="hidden" name="ucmCtrKey" value="<c:out value="${result.ucmCtrKey}"/>"/>
						<c:out value="${result.ucm3lvCod}"/>
					</td>
					<td><textarea class="mark" name="ucm3lvNam" sIndex="${status.index}" maxlength="1500" style="border: 1px solid #ddd;width:430px;height: 40px;resize: none;">${result.ucm3lvNam}</textarea></td>
					<%--<td>
						<textarea class="txtAreaType02 mark" name="ucm3lvDtl" sIndex="${status.index}" maxlength="1500" style="min-width: 100px; max-width: 200px;">${result.ucm3lvDtl}</textarea>
					</td>--%>
					<td rowspan="2">
						<div class="uplidFileList">
                            <ul class="listArea listControl" style="width: 200px; height: 95px;">
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
						<div class="uplidFileList">
                            <ul class="listArea" style="width: 110px; height: 130px;margin: 0;">
                            	<c:forEach var="serviceList" items="${serviceList}" varStatus="status3">
                                	<li>
                                		<label class="mark" sIndex="${status.index}">
                                			<input type="checkbox" id="ra${serviceList.code}${result.ucmCtrKey}" name="uwoCtrMap_${result.ucmCtrKey}" value="${serviceList.code}" <c:forEach var="serviceNode" items="${serviceNode}" varStatus="status4"><c:if test="${serviceList.code eq serviceNode.ucmSvcCod and result.ucmCtrKey eq serviceNode.ucmCtrKey }">checked="checked"</c:if></c:forEach> />
											<span style="width: 80px; height: 15px; overflow: hidden; margin-right: -10px; display: inline-block; white-space: nowrap; -ms-text-overflow: ellipsis;" ><c:out value="${serviceList.name}" /></span>
                                		</label>
                                	</li>
                                </c:forEach>
                            </ul>
                        </div>
					</td>
					<td rowspan="2">
						<label class="mark" sIndex="${status.index}"><input type="radio" name="rad_${status.index}" value="N" <c:if test="${result.ucm3ldYn=='N'}">checked="checked"</c:if>> 사용</label><br/>
						<label class="mark" sIndex="${status.index}"><input type="radio" name="rad_${status.index}" value="Y" <c:if test="${result.ucm3ldYn=='Y'}">checked="checked"</c:if>> 미사용</label>
					</td>
					<td rowspan="2" class="last">
						<c:if test="${result.ucmUpdMdh eq null}">${result.ucmRgtMdh}</c:if>
                        <c:if test="${result.ucmUpdMdh ne null}">${result.ucmUpdMdh}</c:if>
					</td>
				</tr>
				<tr>
					<td>
						<textarea class="mark" name="ucm3lvDtl" sIndex="${status.index}" maxlength="1500"  style="border: 1px solid #ddd;width:430px; height: 80px;resize: none;">${result.ucm3lvDtl}</textarea>
					</td>
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
<form name="searchData" method="post">
	<input type="hidden" name="hUcm1lvCod" value="${ucm1lvCod}">
	<input type="hidden" name="hUcm2lvCod" value="${ucm2lvCod}">
	<input type="hidden" name="standard" value="${standard}">
	<input type="hidden" name="service" value="${service}">
</form>
</body>
</html>