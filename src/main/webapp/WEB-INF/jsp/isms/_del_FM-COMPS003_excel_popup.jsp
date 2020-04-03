<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>

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
<script type="text/javascript" src="/common/js/jquery.blockUI.js"></script>
<script  type="text/javascript">
$(document).ready(function() {
	// 로딩바 적용
	loadingBarSet();
});
function excelUpload(){
	if($("select[name=standard]").val() == "") {
		alert("컴플라이언스를 선택하세요");
		return;
	}

	if( $("select[name=service]").val() == "") {
		alert("서비스를 선택하세요");
		return;
	}

	$("#excelUploadForm").ajaxSubmit({	// IE8 Issue: dataType : "json", multipart/form-data
		url : "${pageContext.request.contextPath}/comps/FM-COMPS003_excelUpload.do",
		dataType : "json",
		data : {'service':$('input[name=service]:checked').val()},
		success : function(data){
			if(data.result == "S" ) {
				alert("등록되었습니다.");
				opener.location.reload();
				window.close();
			} else {
				alert("형식에 맞지 않는 데이터입니다. \n엑셀 파일을 확인하세요.");
			}
		},
		error : function(data){
			alert("에러가 발생하였습니다. \n 엑셀 파일을 확인하세요.");
		}
	})
}
</script>
</head>
<body>
	<form id="excelUploadForm" name="excelUploadForm" method="post" enctype="multipart/form-data">
	<div id="skipnavigation">
	    <ul>
	        <li><a href="#content-box">본문 바로가기</a></li>
	    </ul>
	</div>
	<div id="wrap" class="pop">
		<header>
			<div class="borderBox"></div>
			<h1>엑셀 업로드</h1>
		</header>
		<article class="cont" id="content-box">
			<div class="cont_container">
				<div class="contents">
					<div class="talbeArea">
		        		<table summary="엑셀 업로드">
		            		<colgroup>
								<col width="40%" />
								<col width="*" />
							</colgroup>
							<caption>엑셀 업로드</caption>
							<tbody>
		    					<tr>
									<th class="listTitle" scope="row"><label for="standard">컴플라이언스</label></th>
		   							<td class="fontLeft last">
		   								 <sb:select name="standard" type="A" code="STND" allText="선택"/>
		   							</td>
								</tr>
		  						<tr>
			                		<th class="listTitle">서비스</th>
			                        <td class="fontLeft last">
			                        	<c:forEach var="result" items="${resultList}" varStatus="status">
  											<input type="checkbox" name="service" value="<c:out value="${result.code}"/>">${result.name}<br>
			                        	</c:forEach>
			                		</td>
			            		</tr>
		  						<tr>
			                		<th class="listTitle">파일</th>
			                        <td class="fontLeft last">
										<input type="file" name="excelFile" style="width:200px">
			                		</td>
			            		</tr>
		        			</tbody>
		    			</table>
					</div>
					<div class="bottomBtnArea">
						<ul class="btnList">
							<li><button type="button" onclick="excelUpload();"><span class="icoSave"></span>저장</button></li>
						</ul>
					</div>
	       		</div>
           </div>
           <div class="centerBtnArea">
				<button class="btnStrong close" onclick="return false;" >닫기</button>
			</div>
	</article>
</div>
</form>
</body>
</html>