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
function mapping(key){
	$("#brdKey").val(key);
	if(confirm("해당 정책서와 통제항목을 연결하시겠습니까?")){
		$.ajax({
			 url : "${pageContext.request.contextPath}/inspt/FM-INSPT004_MAPPING.do",
			 type : "post",
			 data : $("#hiddenMap").serialize(),
			 dataType : "json",
			 success : function(data){
				if(data.result == 'S') {
					alert("연결되었습니다.");
					$(opener.location).attr("href", "javascript:search();" );
					window.close();
				} else {
					alert("연결에 실패하였습니다.");
				}
			 },
			 error : function(){
				 alert('error');
			 }
		 });
	}
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
				<h1>정책서 연결</h1>
			</header>
            <article class="cont" id="content-box">
                <div class="cont_container">
					<div class="talbeArea">
						<table summary="통제항목설정"> 
							<colgroup>
								<col width="20%"/>
				                <col width="*%"/>
				                <col width="15%"/>
							</colgroup>
							<caption>통제항목설정 현황</caption> 
							<thead>
							    <tr>
				                    <th scope="col">분류</th>
				                    <th scope="col">제목</th>
				                    <th scope="col">연결</th>
							    </tr>
							</thead>
							<tbody id="tbody">
							<c:forEach var="list" items="${formlist}" varStatus="status">
			                	<tr>		                		
			                		<td><c:out value='${list.ubmCatCod}'/></td>
			                		<td><c:out value='${list.ubmBrdTle}'/></td>
			                		<td class="last"><button onclick="mapping(${list.ubmBrdKey}); return false;">연결</button></td>
			                	</tr>
							</c:forEach>
							<c:if test="${fn:length(formlist) == 0}">
								<tr class="last">
									<td class="last noDataList" colspan="4">
										연결할 통제항목이 없습니다.
									</td>
								</tr>
							</c:if>
							</tbody>
						</table>
				    </div>
                </div>
                <div class="centerBtnArea">
					<button class="btnStrong close" >닫기</button>
				</div>
            </article>
        </div>
        <form name="hiddenMap" id="hiddenMap">
			<input type="hidden" id="mapKey" name="mapKey" value="${mapKey }"/>
			<input type="hidden" id="brdKey" name="brdKey"/>
		</form>
    </body>
</html>