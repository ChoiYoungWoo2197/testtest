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
<script type="text/javascript" src="/common/js/jquery.form.js"></script>
<script type="text/javascript" src="/common/js/ismsUI.js"></script>
<script type="text/javascript" src="/common/js/common.js"></script>
<script  type="text/javascript">
function setAgnId(agnKey, name){
	if(confirm(name+"님을 대무자 지정 승인요청을 하시겠습니까?")){		
		$.ajax({
			url		: "${pageContext.request.contextPath}/mwork/FM-MWORK002_setAgnId.do",
			type		: "post",
			data		: {"workCode": $("#workCode").val(), "worker": agnKey},
			dataType	: "json",
			success	: function(data){
				alert("대무자 지정 승인요청을 완료하였습니다.");
				parent.opener.userWorkByTerm($("#agnTerm").val(),$("#agnId").val(),$("#agnOrder").val());
				window.close();
			},
			error : function(){
				alert("대무자 지정 승인요청을 실패하였습니다.");
			}
		});
	}
}
</script>
</head>
<body>
	<input type="hidden" id="workCode" name="workCode" value="${paramMap.checkKey}">
	<input type="hidden" id="agnTerm" name="agnTerm" value="${paramMap.agnTerm}">
	<input type="hidden" id="agnId" name="agnId" value="${paramMap.agnId}">
	<input type="hidden" id="agnOrder" name="agnOrder" value="${paramMap.agnOrder}">
	<div id="skipnavigation">
	    <ul>
	        <li><a href="#content-box">본문 바로가기</a></li>
	    </ul>
	</div>
	<div id="wrap" class="pop">
		<header>
			<div class="borderBox"></div>
			<h1>대무자 지정</h1>
		</header>
		<article class="cont" id="content-box">
			<div class="cont_container">
				<div class="contents">
					<div class="talbeArea">
		        		<table summary="대무자 지정"> 
		            		<colgroup>
								<col width="18%" />
								<col width="18%" />
								<col width="18%" />
								<col width="18%" />
								<col width="18%" />
								<col width="10%" />
							</colgroup>
							<caption>담당자 정보</caption> 
							<thead>
								<tr>
									<th scope="col">서비스</th>
									<th scope="col">부서</th>
									<th scope="col">직급</th>
									<th scope="col">ID</th>
									<th scope="col">이름</th>
									<th scope="col"></th>
								</tr>
							</thead>
							<tbody>
							<c:forEach var="list" items="${resultList}" varStatus="status">
		    					<tr>
		    						<td><c:out value="${list.service}"/></td>
		    						<td><c:out value="${list.dept}"/></td>
		    						<td><c:out value="${list.pos}"/></td>
		    						<td><c:out value="${list.uumUsrId}"/></td>
		    						<td><c:out value="${list.uumUsrNam}"/></td>
		    						<td class="last"><button onclick="setAgnId('${list.uumUsrKey}','${list.uumUsrNam}');">지정</button></td>
		    					</tr>
		    				</c:forEach>
		    				<c:if test="${fn:length(userList) == 0}">
								<tr class="last">
									<td class="last noDataList" colspan="6">
										지정할 대무자가 없습니다.
									</td>
								</tr>
							</c:if>		
		        			</tbody>
		    			</table>
					</div>
				</div>
           </div>
           <div class="centerBtnArea">
				<button class="btnStrong close" onclick="return false;" >닫기</button>
			</div>
	</article>
</div>
<form id="mappingForm" name="mappingForm" method="post">
	<input type="hidden" id="before" name="before" value="${usrKey }">
	<input type="hidden" id="change" name="change">
	<input type="hidden" id="gubun" name="gubun">
</form>
</body>
</html>
