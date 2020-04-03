<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"  %>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<script type="text/javaScript" language="javascript" >
function fn_code_sav(gbn) {
    var regexp = /^[a-z|A-Z]{1}[\w]{0,9}$/;
    if(!regexp.test($("#uccSndCod").val())) {
        alert("10자리 미만의 영문과 숫자로 첫자는 영문만 사용가능합니다.");
        $("#uccSndCod").focus();
        return;
    }
	if($("#uccSndNam").val() == "") {
		alert("컴플라이언스명을 입력하세요.");
		$("#uccSndNam").focus();
		return;
	}
    if($(':radio[name="stndKind"]:checked').val()==undefined){
        alert("룰타입을 선택하세요.");
        return;
    }
    if($("#uccEtc").val() == "") {
        alert("접두어를 입력하세요.");
        $("#uccEtc").focus();
        return;
    }else{
        var regexp2 = /^[a-z|A-Z]{1}$/;
        if(!regexp2.test($("#uccEtc").val())) {
            alert("1자리의 영문만 사용가능합니다.");
            $("#uccEtc").focus();
            return;
        }
    }

	var url = "";
	if(gbn == 'I') {
		url = "${pageContext.request.contextPath}/setup/FM-SETUP018_W.do";
	} else {
		url = "${pageContext.request.contextPath}/setup/FM-SETUP018_U.do";
	}

	$.ajax({
		 url		: url,
		 type		: "post",
		 data		: $("#codeForm").serialize(),
		 dataType	: "json",
		 success	: function(data){
             if(data.result == 'D') {
                 alert("중복된 코드입니다. 코드를 확인하세요.");
             } else if(data.result == 'DN') {
                 alert("중복된 컴플라이언스명입니다. 컴플라이언스명을 확인하세요.");
                 $("#uccSndNam").focus();
             } else if(data.result == 'DP') {
                 alert("중복된 접두어입니다. 접두어를 확인하세요.");
             } else if(data.result == 'BP') {
                 alert("접두어가 누락되었습니다.");
             } else {
				alert("저장 되었습니다.");
				fn_selectList();
			}
		 },
		 error : function(){
			 alert('error');
		 }
	 });
}


/* 글 목록 화면 function */
function fn_selectList() {
   	document.codeForm.action = "FM-SETUP018_N.do";
   	document.codeForm.submit();
}

</script>
</head>
<body>
<form id="codeForm" name="codeForm" method="post">
<input type="hidden" id="uccFirCod" name="uccFirCod" value="${paramMap.uccFirCod}">
<input type="hidden" id="uccFirNam" name="uccFirNam" value="${paramMap.uccFirNam}">
<input type="hidden" id="hUccSndCod" name="hUccSndCod" value="${info.uccSndCod}">
<input type="hidden" id="uomDepYn" name="uomDepYn" value="${info.uomDepYn}">
<input type="hidden" name="searchKeyword" value="${paramMap.searchKeyword}"/>
<input type="hidden" name="searchCondition" value="${paramMap.searchCondition}"/>
<c:import url="/titlebar.do" />
<div class="contents">
    <div class="talbeArea">
        <table summary="공통코드 저장">
            <colgroup>
                <col width="15%" />
				<col width="*" />
            </colgroup>
            <caption>공통코드 정보</caption>
            <tbody>
                <tr>
                    <th scope="row"><label for="uccSndCod">* 코드</label></th>
                    <td class="fontLeft last">
                    	<input type="text" id="uccSndCod" name="uccSndCod" class="inputText" style="width:150px" value="${info.uccSndCod}" <c:if test="${not empty info.uccSndCod}">disabled="true"</c:if> maxlength="10"  />
                        <span style="color: #ea002c;font-weight: bold;font-size: 11px;margin-left: 10px;">* 10자리 미만의 영문과 숫자로 첫자는 영문만 사용가능합니다.</span>
                    </td>
                </tr>
                <tr>
                    <th scope="row"><label for="uccSndNam">* 컴플라이언스명</label></th>
                    <td class="fontLeft last">
                    	<input type="text" id="uccSndNam" name="uccSndNam" class="inputText" style="width:550px" maxlength="25" value="${info.uccSndNam}" maxlength="25" size="99"/>
                    </td>
                </tr>
                <tr>
                    <th scope="row">* 룰 타입</th>
                    <td class="fontLeft last">
                        <label><input type="radio" name="stndKind" id="stndKind_default" value="default" <c:if test="${info.stndKind eq 'default' or empty info.stndKind}">checked="checked"</c:if>/> 고정3단계</label>
                        <label><input type="radio" name="stndKind" id="stndKind_msit" value="msit" <c:if test="${info.stndKind eq 'msit'}">checked="checked"</c:if>/> 과기정통부</label>
                        <label><input type="radio" name="stndKind" id="stndKind_infra_mp" value="infra_mp" <c:if test="${info.stndKind eq 'infra_mp'}">checked="checked"</c:if>/> 기반시설 관리적/물리적</label>
                        <label><input type="radio" name="stndKind" id="stndKind_infra_la" value="infra_la" <c:if test="${info.stndKind eq 'infra_la'}">checked="checked"</c:if>/> 기반시설 수준평가</label>
                    </td>
                </tr>
                <tr>
                    <th scope="row"><label for="uccEtc">* 접두어</label></th>
                    <td class="fontLeft last">
                        <input type="text" id="uccEtc" name="uccEtc" class="inputText" style="width: 50px;" maxlength="5" value="${info.uccEtc}"/>
                        <span style="color: #ea002c;font-weight: bold;font-size: 11px;margin-left: 10px;">* 1자리의 영문만 사용가능합니다.</span>
                    </td>
                </tr>
                <tr>
                    <th scope="row">사용중인 접두어</th>
                    <td class="fontLeft last">
                        <ul style="height: 88px; overflow: auto;">
                            <c:forEach var="listComCodPrefix" items="${listComCodPrefix}" varStatus="status">
                                <li><span style="font-weight: bold;">${listComCodPrefix.uccEtc}</span> - ${listComCodPrefix.uccSndNam}</li>
                            </c:forEach>
                        </ul>
                    </td>
                </tr>
                <tr>
                    <th scope="row"><label for="uncUseY">사용유무</label></th>
                    <td class="fontLeft last">
                    	<input type="radio" id="uncUseY" name="uccUseYn" value="Y" <c:if test="${info.uccUseYn eq 'Y' or empty info.uccUseYn}">checked="checked"</c:if>/> <label for="uncUseY">사용</label>
						<input type="radio" id="uncUseN" name="uccUseYn" value="N" <c:if test="${info.uccUseYn eq 'N'}">checked="checked"</c:if> /> <label for="uncUseN">미사용</label>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
    <div class="bottomBtnArea">
        <ul class="btnList">
            <li>
            <c:choose>
              <c:when test="${empty info.uccSndCod}">
              	<button class="btnStrong" onclick="fn_code_sav('I'); return false;"><span class="icoAdd"></span>등록</button>
              </c:when>
              <c:otherwise>
              	<button class="btnStrong" onclick="fn_code_sav('U'); return false;"><span class="icoRepair"></span>수정</button>
              </c:otherwise>
            </c:choose>
            	<button class="btnStrong" onclick="fn_selectList();"><span class="icoList"></span>목록</button>
            </li>
        </ul>
    </div>
</div>
</form>
</body>
</html>