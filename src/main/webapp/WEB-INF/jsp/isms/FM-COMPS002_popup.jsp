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
<!--[if IE]> <script src="/common/js/html5.js"></script> <![endif]-->
<script type="text/javascript" src="/common/js/jquery.js"></script>
<script type="text/javascript" src="/common/js/ismsUI.js"></script>
<script type="text/javascript" src="/common/js/common.js"></script>
<script  type="text/javascript">
function fn_pop_save(gubun){
	
	if($("#uomSvcNam").val() == "") {
		alert("서비스명을 입력하세요.");
		return
	}
	
	if($("#uomSvcCod").val() == "") {
		alert("서비스코드를 입력하세요.");
		return
	}
	
	if($("#uomDepNam").val() == "") {
		alert("부서명을 입력하세요.");
		return
	}
	
	if($("#uomDepCod").val() == "") {
		alert("부서코드를 입력하세요.");
		return
	}
	
	var strUrl = "";
	if(gubun == "I") {
		strUrl = "${pageContext.request.contextPath}/comps/FM-COMPS002_W_popup.do";
	} else {
		strUrl = "${pageContext.request.contextPath}/comps/FM-COMPS002_U_popup.do";
	}
	$.ajax({
		 url : strUrl,
		 type : "post",
		 data : $("#popupForm").serialize(),
		 dataType : "json",
		 success : function(data){
			if(data.result == 'D') {
				alert("중복된 코드입니다. 코드를 확인하세요.");
			} else {
				alert("저장 되었습니다.");
				$(opener.location).attr("href", "javascript:fn_stnd_select('"+$("#stndCod").val()+"');" );
				window.close();
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
	<div id="skipnavigation">
	    <ul>
	        <li><a href="#content-box">본문 바로가기</a></li>
	    </ul>
	</div>
	<div id="wrap" class="pop">
		<header>
			<div class="borderBox"></div>
				<h1>서비스 관리</h1>
			</header>
            <article class="cont" id="content-box">
                <div class="cont_container">
					<div class="contents">
						<div class="talbeArea">
					        <table summary="컴플라이언스 등록"> 
					            <colgroup>
					                <col width="40%" />
									<col width="*" />
					            </colgroup>
					            <caption>서비스 관리 정보</caption> 
					            <tbody>
					                <tr>
				            			<th scope="row" class="listTitle"><label for="uomSvcNam">서비스명</label></th>            			
				                		<td class="fontLeft last"><input type="text" id="uomSvcNam" name="uomSvcNam" value="${info.uomSvcNam}"  maxlength="100" /></td>
							    	</tr>
							    	<tr>
				            			<th scope="row" class="listTitle"><label for="uomSvcCod">서비스코드</label></th>            			
				                		<td class="fontLeft last"><input type="text" id="uomSvcCod" name="uomSvcCod" value="${info.uomSvcCod}"  maxlength="25" <c:if test="${not empty info.uomSvcCod}">disabled="true"</c:if>  /></td>
							    	</tr>
							    	<tr>
				            			<th scope="row" class="listTitle"><label for="uomDepNam">부서명</label></th>            			
				                		<td class="fontLeft last"><input type="text" id="uomDepNam" name="uomDepNam" value="${info.uomDepNam}"  maxlength="100" /></td>
							    	</tr>
							    	<tr>
				            			<th scope="row" class="listTitle"><label for="uomDepCod">부서코드</label></th>            			
				                		<td class="fontLeft last"><input type="text" id="uomDepCod" name="uomDepCod" value="${info.uomDepCod}"  maxlength="25" <c:if test="${not empty info.uomDepCod}">disabled="true"</c:if> /></td>
							    	</tr>
							    	<tr>
				            			<th scope="row" class="listTitle">사용유무</th>            			
				                		<td class="fontLeft last">
				                			<input type="radio" id="uomUseY" name="uomUseYn" value="Y" <c:if test="${info.uomUseYn eq 'Y' or empty info.uomUseYn}">checked="checked"</c:if> /> <label for="uomUseY">사용</label>
											<input type="radio" id="uomUseN" name="uomUseYn" value="N" <c:if test="${info.uomUseYn eq 'N'}">checked="checked"</c:if> /> <label for="uomUseN">미사용</label>
				                		</td>
							    	</tr>
					              
					            </tbody>
					        </table>
	    				</div>
	    				<div class="bottomBtnArea">
			       			<c:choose>
			       				<c:when test="${empty info.uomSvcCod}">
			       					<button onclick="fn_pop_save('I');"><span class="icoAdd"></span>등록</button>
			       				</c:when>
			       				<c:otherwise>
			       					<button onclick="fn_pop_save('I');"><span class="icoRepair"></span>수정</button>
			       				</c:otherwise>
			       			</c:choose>
						</div>
                	</div>
                </div>
                
                <div class="centerBtnArea">
					<button class="btnStrong close">닫기</button>
				</div>
            </article>
        </div>
    </body>
</html>
