<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<script type="text/javascript">
$(function(){
    $("button.excelDown").click(function(){
        $("#standard").val("${searchVO.standard}");
        if(confirm("[ "+ $("#standard :selected").text() + " ] 엑셀 다운로드를 하시겠습니까?")){
            document.listForm.action = "${pageContext.request.contextPath}/excelNew/FM-COMPS003.do";
            document.listForm.submit();
        }
        return false;
    });

	<c:if test="${fn:length(resultList) == 0}">
	$("button.excelDown").attr("disabled", true);
	</c:if>
});

function go2Depth(ucm1lvCod){
	$("#hUcm1lvCod").val(ucm1lvCod);
	frm = document.listForm;
   	frm.action = "FM-COMPS003_2D.do";
	frm.submit();
}

function addCTR(){
	if($("select[name=standard]").val() == "") {
		alert("컴플라이언스를 선택하세요.");
		return;
	}
	var inData = "<tr><td><input type='text' name='sts' value='C' class='inputText' style='width:15px;' readonly='readonly' ></td>" +
				"<td><input type='text' class='inputText' size='10' style='width:80px;' name='ucm1lvCod' maxlength='10'></td>" +
				"<td><input type='text' class='inputText' size='65' name='ucm1lvNam' maxlength='1500'></td>" +
				"<td class='last'><button onclick='addDel(this);'>삭제</button></td></tr>";
	if("${fn:length(resultList)}" == "0" && $("input[name='ucm1lvCod']").val() == undefined ) {
		$("#tbody").html(inData);
	} else {
		$("#tbody").append(inData);
	}
	$("input[name='ucm1lvCod']").last().focus();
}

function sav(){
	if(!arrValidation($("input[name=ucm1lvCod]"))) {
		alert("통제항목번호를 입력하세요.");
		return;
	}

	if(!arrValidation($("input[name=ucm1lvNam]"))) {
		alert("통제목적명을 입력하세요.");
		return;
	}
	var arrCod = [];
	for(var i=0; i<$("[name=ucm1lvCod]").length; i++){
		if($.inArray($("[name=ucm1lvCod]")[i].value, arrCod)>=0){
			alert("통제목적번호가 중복되었습니다.");
			$("[name=ucm1lvCod]")[i].focus();
			return ;
		}else{
			arrCod.push($("[name=ucm1lvCod]")[i].value);
		}
	}

	frm = document.listForm;
   	frm.action = "FM-COMPS003_SAV.do";
	frm.submit();
}

function mark(key){
	$("#sts_"+key).val("U");
}

function fn_search(){
	if($("select[name=standard]").val() == "") {
		alert("컴플라이언스를 선택하세요.");
		return;
	}
	document.listForm.action = "FM-COMPS003.do";
   	document.listForm.submit();
}

function excelUpload(){
	$("#standard").val("${searchVO.standard}");
	window.open("FM-COMPS003_excelNew_popup.do","ExcelUpload","width=650, height=480, menubar=no, location=no, status=no, scrollbars=auto");
}

function addDel(_this){
	$(_this).parent().parent().html('');
	return false;
}

function compsInsert(){
	window.open("FM-COMPS003_comps_popup.do","compsPopup","width=650, height=480, menubar=no, location=no, status=no,scrollbars=yes");
}

function compsDelete(){
	$("#standard").val("${searchVO.standard}");
	if (confirm("[ "+ $("#standard :selected").text() + " ] 컴플라이언스의 통제항목을 모두 삭제하시겠습니까?")) {
		$.post("FM-COMPS003_del_comps.do", { standard: $("#standard").val() }, function(data) {
			if (data.result == 'S') {
				alert("삭제 완료되었습니다.");
				document.listForm.action = "FM-COMPS003.do";
				$("#listForm").submit();
			}
			else {
				alert("삭제 요청 처리를 실패했습니다.");
			}
		}, "json")
		.fail(function() {
			alert("삭제 처리 중 오류가 발생하였습니다.");
		});
	}
}
</script>
</head>
<body>
<form name="listForm" id="listForm" method="post">
<input type="hidden" name="hUcm1lvCod" id="hUcm1lvCod"/>
<c:import url="/titlebar.do" />

<div class="search">
	<div>
		<ul class="alignUL_LR">
			<li>
				<sb:select name="standard" type="A" code="STND" allText="컴플라이언스" value="${searchVO.standard}"/>
				<sb:select name="service" type="S" allText="전체" value="${searchVO.service }"/>
				<button onclick="fn_search(); return false;" class="btnSearch">검색</button>
			</li>
			<li>
				<button onclick="compsInsert(); return false;"><span class="icoList"></span>컴플라이언스 추가</button>
			</li>
			<li></li>
		</ul>
	</div>
</div>
<br/>
<div class="contents">
	<c:if test="${fn:length(searchVO.standard)>0}">
		<div class="topBtnArea">
			<ul class="btnList">
				<li><button type="button" onclick="compsDelete();" style="color: red;"><span class="icoDel"></span>컴플라이언스 초기화</button></li>
				<li><button onclick="excelUpload(); return false;"><span class="icoExl"></span>엑셀업로드</button></li>
				<li><button class="excelDown"><span class="icoExl"></span>엑셀다운로드</button></li>
				<li><button onclick="addCTR(); return false;"><span class="icoAdd"></span>추가</button></li>
				<li><button onclick="sav(); return false;"><span class="icoSave"></span>저장</button></li>
			</ul>
		</div>
	</c:if>
	<div class="tabArea">
	    <ul class="list3Tab">
	        <li class="sel"><a href="#none">통제목적</a></li>
	        <li><a href="#none">통제항목</a></li>
	        <li><a href="#none">통제점검</a></li>
	    </ul>
	</div>
	<div class="talbeArea">
		<table summary="통제항목설정">
			<colgroup>
                <col width="8%"/>
                <col width="12%"/>
                <col width=""/>
                <col width="15%"/>
			</colgroup>
			<caption>통제항목설정 현황</caption>
			<thead>
			    <tr>
                    <th scope="col" colspan="2">통제목적번호</th>
                    <th scope="col" >통제목적명</th>
                    <th scope="col">사용유무</th>
			    </tr>
			</thead>
			<tbody id="tbody">
			 <c:forEach var="result" items="${resultList}" varStatus="status">
				<tr>
					<td><button onclick="go2Depth('${result.ucm1lvCod}');">항목</button></td>
					<td><input type="text" name="ucm1lvCod" size="10" value="<c:out value="${result.ucm1lvCod}"/>" class="inputText" style="width:80px;" readonly="readonly"></td>
					<td><input type="text" name="ucm1lvNam" size="65" value="<c:out value="${result.ucm1lvNam}"/>" class="inputText" onclick="mark(${status.index});" maxlength="1500"></td>
					<td class="last">
						<label><input type="radio" id="rad_Y_${status.index}" name="rad_${status.index}" value = "N" <c:if test="${result.ucm1ldYn == 'N'}">checked="checked"</c:if> onclick="mark(${status.index});"> 사용</label>
						<label><input type="radio" id="rad_TY_${status.index}" name="rad_${status.index}" value = "Y" <c:if test="${result.ucm1ldYn == 'Y'}">checked="checked"</c:if> onclick="mark(${status.index});"> 미사용</label>
						<input type="hidden" id="sts_${status.index}" name="sts" value="R" />
					</td>
				</tr>
			</c:forEach>
	     	<c:if test="${fn:length(resultList) == 0}">
				<tr class="last">
					<td class="last noDataList" colspan="6">
						검색된 통제항목이 없습니다.
					</td>
				</tr>
			</c:if>
			</tbody>
		</table>
    </div>
</div>
</form>
</body>
</html>