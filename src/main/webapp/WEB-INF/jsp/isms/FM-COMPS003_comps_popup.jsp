<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<META http-equiv="X-UA-Compatible" content="IE=edge">
<title>SK broadband ISAMS</title>
<link rel="stylesheet" type="text/css" href="/common/css/reset.css"/>
<link rel="stylesheet" type="text/css" href="/common/css/common.css"/>
<link rel="stylesheet" type="text/css" href="/common/css/pop.css"/>
<script type="text/javascript" src="/common/js/jquery.js"></script>
<script type="text/javascript" src="/common/js/ismsUI.js"></script>
<script type="text/javascript" src="/common/js/common.js"></script>
<script  type="text/javascript">
function fn_code_sav() {
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
			alert("접두어는 1자리의 영문자로 입력하세요.");
			$("#uccEtc").focus();
			return;
		}
	}

	$.ajax({
		 url		: "${pageContext.request.contextPath}/setup/FM-SETUP018_W.do",
		 type		: "post",
		 data		: $("#codeForm").serialize(),
		 dataType	: "json",
		 success	: function(data){
			 if(data.result == 'D') {
				alert("중복된 코드입니다. 코드를 확인하세요.");
				 $("#uccSndCod").focus();
			 } else if(data.result == 'DN') {
				 alert("중복된 컴플라이언스명입니다. 컴플라이언스명을 확인하세요.");
				 $("#uccSndNam").focus();
			 } else if(data.result == 'DP') {
				 alert("중복된 접두어입니다. 접두어를 확인하세요.");
				 $("#uccEtc").focus();
			 } else if(data.result == 'BP') {
				 alert("접두어가 누락되었습니다.");
			 } else {
				alert("저장 되었습니다.");
				opener.location.reload(true);
				self.close();
			}
		 },
		 error : function(){
			 alert('error');
		 }
	 });
}
</script>
</head>
<body>
<form id="codeForm" name="codeForm" method="post">
<input type="hidden" id="uccFirCod" name="uccFirCod" value="STND">
<input type="hidden" id="uccFirNam" name="uccFirNam" value="통제항목표준명">
<input type="hidden" id="uomDepYn" name="uomDepYn" value="">
	<div id="skipnavigation">
	    <ul>
	        <li><a href="#content-box">본문 바로가기</a></li>
	    </ul>
	</div>
	<div id="wrap" class="pop">
		<header>
			<div class="borderBox"></div>
				<h1>컴플라이언스 추가</h1>
		</header>
		<article class="cont" id="content-box">
			<div class="cont_container" style="padding-bottom: 10px;">
				<div class="talbeArea">
					<table summary="공통코드 저장"> 
			            <colgroup>
			                <col width="20%" />
							<col width="*" />
			            </colgroup>
			            <caption>공통코드 정보</caption> 
			            <tbody>
 			                <tr>	
			                    <th scope="row"><label for="uccSndCod">* 코드</label></th>
			                    <td class="fontLeft last">
			                    	<input type="text" id="uccSndCod" name="uccSndCod" class="inputText" style="width:150px" maxlength="10"  /><br />
									<span style="color: #ea002c;font-weight: bold;font-size: 11px;margin-left: 10px;">* 10자리 미만의 영문과 숫자로 첫자는 영문만 사용가능합니다.</span>
			                    </td>
			                </tr>
			                <tr>	
			                    <th scope="row"><label for="uccSndNam">* 컴플라이언스명</label></th>
			                    <td class="fontLeft last">
			                    	<input type="text" id="uccSndNam" name="uccSndNam" class="inputText" style="width:98%;" maxlength="25" maxlength="25" />
			                    </td>
			                </tr>
							<tr>
								<th scope="row">* 룰 타입</th>
								<td class="fontLeft last">
									<label><input type="radio" name="stndKind" id="stndKind_default" value="default" /> 고정3단계</label>
									<%--<label><input type="radio" name="stndKind" id="stndKind_isms" value="isms" /> ISMS</label>
									<label><input type="radio" name="stndKind" id="stndKind_isms_p" value="isms_p" /> ISMS-P</label>--%>
									<label><input type="radio" name="stndKind" id="stndKind_msit" value="msit" /> 과기정통부</label>
									<label><input type="radio" name="stndKind" id="stndKind_infra_mp" value="infra_mp" /> 기반시설 관리적/물리적</label>
									<label><input type="radio" name="stndKind" id="stndKind_infra_la" value="infra_la" /> 기반시설 수준평가</label>
								</td>
							</tr>
							<tr>
								<th scope="row"><label for="uccEtc">* 접두어</label></th>
								<td class="fontLeft last">
									<input type="text" id="uccEtc" name="uccEtc" class="inputText" style="width: 50px;" maxlength="5" />
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
			                    <th scope="row">사용유무</th>
			                    <td class="fontLeft last">
			                    	<input type="radio" id="uncUseY" name="uccUseYn" value="Y" checked="checked" /> <label for="uncUseY">사용</label>
									<input type="radio" id="uncUseN" name="uccUseYn" value="N" /> <label for="uncUseN">미사용</label>
			                    </td>
			                </tr>
			            </tbody>
			        </table>
				</div>
				<div class="bottomBtnArea">
			        <ul class="btnList">
			            <li>
							<button class="btnStrong" onclick="fn_code_sav(); return false;"><span class="icoAdd"></span>등록</button>
			            </li>
			        </ul>
			    </div>
            </div>
            <div class="centerBtnArea">
				<button class="btnStrong close" >닫기</button>
			</div>
		</article>
	</div>
</form>
</body>
</html>