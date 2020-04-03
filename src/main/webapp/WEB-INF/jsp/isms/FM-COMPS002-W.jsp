<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"  %>
<!DOCTYPE html>
<html lang="ko">
<head>
<script type="text/javaScript" language="javascript" >
$(document).ready(function(){
	var result = '${result}' 
	if(result != "") {
		if(result == "D") {
			alert("표준코드 이미 존재합니다.");
		} else if(result == "F") {
			alert("저장에 실패하였습니다.");
		} else {
			alert("저장 되었습니다.");
		}
	}
});

function fn_stnd_add() {
	if($("#uccSndNam").val() == "") {
		alert("표준명을 입력하세요.");
		return;
	}
	
	if($("#uccSndCod").val() == "") {
		alert("표준코드를 입력하세요.");
		return;
	}
	
	document.stndForm.action = "FM-COMPS002_W.do";
   	document.stndForm.submit();
}

function fn_stnd_mod() {
	if($("#uccSndNam").val() == "") {
		alert("표준명을 입력하세요.");
		return;
	}
	
	if($("#uccSndCod").val() == "") {
		alert("표준코드를 입력하세요.");
		return;
	}
	
	document.stndForm.action = "FM-COMPS002_U.do";
   	document.stndForm.submit();
}

function fn_org_pop(divCod, svcCod, depCod) {
	document.stndForm.divCod.value = divCod;
	document.stndForm.svcCod.value = svcCod;
	document.stndForm.depCod.value = depCod;
	
	window.open("","deptPop","width=400, height=400, menubar=no, location=no, status=no, scrollbars=yes");
	document.stndForm.target = "deptPop";
	document.stndForm.action = "FM-COMPS002_popup.do";
	document.stndForm.submit();
}


/* 글 목록 화면 function */
function fn_selectList() {
   	document.stndForm.action = "FM-COMPS002.do";
   	document.stndForm.submit();
}

</script>
</head>
<body>

<form id="stndForm" name="stndForm" method="post">
<input type="hidden" name="stndCod" value="${stndInfo.uccSndCod}"/>
<input type="hidden" name="stndNam" value="${stndNam}"/>
<input type="hidden" name="divCod"/>
<input type="hidden" name="svcCod"/>
<input type="hidden" name="depCod"/>
<p class="history"><a href="/comps/FM-COMPS001.do">컴플라이언스관리</a><span>&gt;</span>컴플라이언스 등록</p>
<div class="conttitle">컴플라이언스 등록</div>
<div class="contents">
    <div class="talbeArea">
        <table summary="컴플라이언스 등록"> 
            <colgroup>
                <col width="15%" />
				<col width="*" />
				<col width="15%" />
				<col width="20%" />
            </colgroup>
            <caption>플라이언스 등록 정보</caption> 
            <tbody>
                <tr>
                    <th>표준명</th>
                    <td class="listTitle">
                    	<input type="text" id="uccSndNam" name="uccSndNam" value="${stndInfo.uccSndNam}" maxlength="25" class="inputText" style="width:150px" /> 
                    </td>
                    <th>표준코드</th>
                    <td class="fontLeft last">
                    	<input type="text" id="uccSndCod" name="uccSndCod" value="${stndInfo.uccSndCod}" <c:if test="${not empty stndInfo.uccSndCod}">disabled="true"</c:if> maxlength="5" class="inputText" style="width:80px" />
                    </td>
                </tr>
                <tr>
                    <th>표준 비고</th>
                    <td colspan="3" class="fontLeft last">
                    	<textarea class="txtAreaType03" name="uccEtc"><c:out value="${stndInfo.uccEtc}"/></textarea>
                    </td>
                </tr>
                <tr>
                    <th>사용유무</th>
                    <td colspan="3" class="fontLeft last">
                    	<input type="radio" name="uccUseYn" value="Y" <c:if test="${stndInfo.uccUseYn eq 'Y' or empty stndInfo.uccUseYn}">checked="checked"</c:if> />사용
						<input type="radio" name="uccUseYn" value="N" <c:if test="${stndInfo.uccUseYn eq 'N'}">checked="checked"</c:if> />미사용
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
    <div class="bottomBtnArea">
        <ul class="btnList">
            <li>
            <c:choose>
              <c:when test="${empty stndInfo.uccSndCod}">
              	<button class="btnStrong" onclick="fn_stnd_add();"><span class="icoAdd"></span>등록</button>
              </c:when>
              <c:otherwise>
              	<button class="btnStrong" onclick="fn_stnd_mod();"><span class="icoRepair"></span>수정</button>
              </c:otherwise>
            </c:choose>
            	<button class="btnStrong" onclick="fn_selectList();"><span class="icoList"></span>목록</button>
            </li>
        </ul>
    </div>
    <br />
    
    <c:if test="${not empty stndInfo.uccSndCod }">
    <div class="talbeArea">
		<table summary=“컴플라이언스 등록”> 
			<colgroup>
				<col width="5%">
				<col width="15%">
				<col width="12">
				<col width="15%">
				<col width="12%">
				<col width="13%">
				<col width="11%">
				<col width="8%">
				<col width="10%">
			</colgroup>
			<caption>컴플라이언스 등록 현황</caption> 
			<thead>
			    <tr>    
			        <th scope="col">번호</th>
			        <th scope="col">서비스</th>
			        <th scope="col">서비스코드</th>
			        <th scope="col">부서</th>
			        <th scope="col">부서코드</th>
			        <th scope="col">작성자</th>
			        <th scope="col">작성일</th>
			        <th scope="col">사용유무</th>
			        <th scope="col">수정</th>
			    </tr>
			</thead>
			<tbody>
			<c:forEach var="result" items="${deptList}" varStatus="status"> 
			<tr>
				<td><c:out value="${fn:length(deptList) - status.index}"/></td>
                <td><c:out value="${result.uomSvcNam}"/></td>
                <td><c:out value="${result.uomSvcCod}"/></td>
                <td><c:out value="${result.uomDepNam}"/></td>
                <td><c:out value="${result.uomDepCod}"/></td>
                <td><c:out value="${result.uomRgtNam}"/></td>
                <td><c:out value="${result.uomRgtMdh}"/></td>
                <td>
                	<c:choose>
                		<c:when test="${result.uomUseYn eq 'Y'}">사용</c:when>
                		<c:otherwise>미사용</c:otherwise>
                	</c:choose>
                </td>
                <td>
                	<button onclick="fn_org_pop('${result.uomDivCod}','${result.uomSvcCod}','${result.uomDepCod}');  return false;">수정</button>
                </td>
			</tr>
			</c:forEach>
	     	<c:if test="${fn:length(deptList) == 0}">
				<tr class="last">
					<td class="last noDataList" colspan="9">
						데이터가 존재하지 않습니다.
					</td>
				</tr>
			</c:if>
			</tbody>
		</table>
    </div>
     <div class="bottomBtnArea">
        <ul class="btnList">
            <li>
              	<button class="btnStrong" onclick="fn_org_pop();"><span class="icoAdd"></span>부서 등록</button>
            </li>
        </ul>
    </div>
    </c:if>
</div>
</form>
</body>
</html>