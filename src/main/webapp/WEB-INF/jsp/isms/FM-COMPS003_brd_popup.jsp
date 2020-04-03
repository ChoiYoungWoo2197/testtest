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
function getFile(key){
	$("#downKey").val(key);
	document.fileDown.action = "${pageContext.request.contextPath}/common/getFileDown.do";
   	document.fileDown.submit();
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
			<h1>정책서 세부내역</h1>
		</header>
		<article class="cont" id="content-box">
			<div class="cont_container">
				<div class="contents">
					<div class="talbeArea">
		        		<table summary="정책서 세부내역">
		            		<colgroup>
								<col width="20%" />
								<col width="*" />
							</colgroup>
							<caption>정책서 세부내역</caption>
							<tbody>
		    					<tr>
									<th class="listTitle">제목</th>
		   							<td class="fontLeft last"><c:out value="${vo.ubm_brd_tle}"/></td>
								</tr>
								<tr>
									<th class="listTitle">권한</th>
		   							<td class="fontLeft last"><c:out value="${vo.ubm_sek_ath}"/></td>
								</tr>
								<tr>
									<th class="listTitle">내용</th>
		   							<td class="fontLeft last">${vo.ubm_brd_cts}</td>
								</tr>
								<tr>
									<th class="listTitle">제목</th>
		   							<td class="fontLeft last"><c:out value="${vo.ubm_brd_tle}"/></td>
								</tr>
		  						<tr>
			                		<th class="listTitle">파일</th>
			                        <td class="fontLeft last">
			                        	<div class="uplidFileList">
				                            <ul class="listArea" style="width: 95%;">
				                            	<c:forEach var="file" items="${list}" varStatus="status2">
				                            	  <c:if test="${result.ucmCtrKey == res.ucbCtrKey }">
				                                	<li>
				                                		<a href="javascript:getFile(<c:out value='${file.umfFleKey}'/>)"><span class="icoDown"></span><c:out value="${file.umfConFnm}"></c:out></a>
				                                	</li>
				                                  </c:if>
				                                </c:forEach>
				                            </ul>
				                        </div>
			                		</td>
			            		</tr>
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
<form name="fileDown" method="post">
<input type="hidden" id="downKey" name="downKey" />
</form>
</body>
</html>



