<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"  %>
<!DOCTYPE html>
<html lang="ko">
<head>
<script type="text/javaScript">
$(document).ready(function(){

});

function fn_auh_save(gubun) {
	if($("#uatAuhNam").val() == "") {
		alert("권한명을 입력하세요.");
		return;
	}

	var strUrl = "";
	if(gubun == "I") {
		strUrl = "${pageContext.request.contextPath}/setup/FM-SETUP017_W.do";
	} else {
		strUrl = "${pageContext.request.contextPath}/setup/FM-SETUP017_U.do";
	}

	$.ajax({
		 url : strUrl,
		 type : "post",
		 data : $("#auhForm").serialize(),
		 dataType : "json",
		 success : function(data){
				alert("저장 되었습니다.");
				if(gubun == "I") {
					$("#uatAuhKey").val(data.uatAuhKey);
				}
				document.auhForm.action = "FM-SETUP017_RW.do";
			   	document.auhForm.submit();

		 },
		 error : function(){
			 alert('error');
		 }
	 });
}


/* 글 목록 화면 function */
function fn_selectList() {
	document.auhForm.action = "FM-SETUP017.do";
   	document.auhForm.submit();
}

function fn_mnu_all_chk(key) {
	if($(".chk_"+key).is(":checked") == true) {
		$(".chk_node_"+key).prop("checked", true);
	} else {
		$(".chk_node_"+key).prop("checked", false);
	}


}
</script>
</head>
<body>

<form id="auhForm" name="auhForm" method="post">
<input type="hidden" id="uatAuhKey" name="uatAuhKey" value="${auhInfo.uatAuhKey}"/>
<input type="hidden" id="searchCondition" name="searchCondition" value="${paramMap.searchCondition}"/>
<c:import url="/titlebar.do" />
<div class="contents">
    <div class="talbeArea">
        <table summary="권한설정">
            <colgroup>
                <col width="20%" />
				<col width="30%" />
				 <col width="20%" />
				<col width="30%" />
            </colgroup>
            <caption>권한설정 정보</caption>
            <tbody>
                <tr>
                    <th scope="row"><label for="uatAuhNam">* 권한명</label></th>
                    <td class="fontLeft">
                    	<input type="text" id="uatAuhNam" name="uatAuhNam" value="${auhInfo.uatAuhNam}" maxlength="25" class="inputText"  style="width:150px" />
                    </td>
                    <th scope="row"><label for="uatAuhGbn">* 기본 권한등급</label></th>
                    <td class="fontLeft last">
                    	<select id="uatAuhGbn" name="uatAuhGbn" class="selectBox">
                    	<c:forEach var="auhGbn" items="${auhGbnList}" varStatus="status">
                    		<option value="${auhGbn.code}" <c:if test="${auhGbn.code eq auhInfo.uatAuhGbn}">selected="selected"</c:if>><c:out value="${auhGbn.name}"/></option>
                    	</c:forEach>
                    	</select>
                    </td>
				</tr>
				<tr>
                    <th scope="row"><label for="uatAuhEtc">권한 설명</label></th>
                    <td colspan="3" class="fontLeft last">
                    	<textarea class="txtAreaType03" name="uatAuhEtc" maxlength="250"><c:out value="${auhInfo.uatAuhEtc}"/></textarea>
                    </td>
                </tr>
                <tr>
                    <th scope="row">사용유무</th>
                    <td colspan="3" class="fontLeft last">
                    	<input type="radio" id="uatUseY" name="uatUseYn" value="Y" <c:if test="${auhInfo.uatUseYn eq 'Y' or empty auhInfo.uatUseYn}">checked="checked"</c:if> /> <label for="uatUseY">사용</label>
						<input type="radio" id="uatUseN" name="uatUseYn" value="N" <c:if test="${auhInfo.uatUseYn eq 'N'}">checked="checked"</c:if> /> <label for="uatUseN">미사용</label>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
    <c:if test="${not empty mapList}">
	<div class="title">메뉴 권한</div>
	<div class="menuSelect">
	<c:forEach var="map" items="${mapList}" varStatus="status">
   		<dl class="mainMenu">
			<dt class="item">
				<label for="ra<c:out value='${map.ummMnuKey}' />">
					<input type="checkbox" class="chk_${map.ummMnuKey}" name="uamMnuKey" onchange="fn_mnu_all_chk('${map.ummMnuKey}');" value="${map.ummMnuKey}" <c:if test="${map.ummMnuChk eq 'Y' }">checked="checked"</c:if> /><c:out value="${map.ummMnuNam}" />
				</label>
			</dt>
			<dd class="subMenu">
				<ul class="menuList">
				<c:set var="cnt" value="0"/>
				<c:forEach var="mapNode" items="${mapNodeList}" varStatus="status">
					<c:if test="${map.ummMnuKey eq mapNode.ummMnuPrt}">
						<c:set var="cnt" value="${cnt+1}"/>
						<li class="item">
	                	  	<label for="ra<c:out value='${mapNode.ummMnuKey}' />">
	                	  		<input type="checkbox" class="chk_node_${map.ummMnuKey}" id="ra<c:out value='${mapNode.ummMnuKey}' />" name="uamMnuKey" value="${mapNode.ummMnuKey}" <c:if test="${mapNode.ummMnuChk eq 'Y' }">checked="checked"</c:if> /><c:out value="${mapNode.ummMnuNam}" />
	                	  		<input type="checkbox" class="uamMnuRgt "name="uamMnuRgt" value="${mapNode.ummMnuKey}" <c:if test="${mapNode.ummMnuRgt eq 'Y' }">checked="checked"</c:if> style="display: none;" />
	                	  	</label>
                	  	</li>
					</c:if>
				</c:forEach>
				</ul>
			</dd>
		</dl>
   	</c:forEach>
   	</div>
   	</c:if>
	<div class="bottomBtnArea">
	    <ul class="btnList">
	        <li>
	          	<c:if test="${empty auhInfo.uatAuhKey }">
	          		<button class="btnStrong" onclick="fn_auh_save('I'); return false;"><span class="icoAdd"></span>등록</button>
				</c:if>
				<c:if test="${not empty auhInfo.uatAuhKey }">
					<button class="btnStrong" onclick="fn_auh_save('U'); return false;"><span class="icoRepair"></span>수정</button>
				</c:if>
				<button class="btnStrong" onclick="fn_selectList(); return false;"><span class="icoList"></span>목록</button>
	        </li>
	    </ul>
	</div>
</div>
</form>
</body>
</html>