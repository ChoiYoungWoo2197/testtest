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
<script type="text/javascript" src="/common/js/jquery.blockUI.js"></script>
<script type="text/javascript" src="/common/js/ismsUI.js"></script>
<script type="text/javascript" src="/common/js/common.js"></script>
<script type="text/javascript" src="/common/js/jquery.chained.min.js"></script>
<script  type="text/javascript">
$(document).ready(function() {
	// 로딩바 적용
	loadingBarSet();
	$("#assGbn").chained("#assCat");
});

function excelDownload(){
	var strAction = "";

	if ( !$("#assCat").val()) {
		alert("샘플양식의 자산분류를 선택해주세요.");
		return false;
	}

	$("#assCatS").val($("#assCat").val());
	$("#assGbnS").val($("#assGbn").val());

	strAction = "${pageContext.request.contextPath}/excel/FM-RISKM006.do";
	document.excelDownloadForm.action = strAction;
   	document.excelDownloadForm.submit();
}

function checkExcelUpload() {
	/* if (!$("#uarAssCat").val()) {
		alert("업로드할 자산분류를 선택해주세요.");
		return false;
	} */
	var strUrl = "${pageContext.request.contextPath}/riskm/FM-RISKM006_checkExcelSave.do"

	$("#excelUploadForm").ajaxSubmit({	// IE8 Issue: dataType : "json", multipart/form-data
		url : strUrl,
		dataType : "json",
		success : function(data){
			/*if (data.assetNotFound.length > 0) {
				var asset = "";
				var no = 0;
				$.each(data.assetNotFound, function(k, v) {
					asset += "\n" + ++no + ". " + v.depNam + ", " + v.cmpVal1 + ", " + v.cmpVal2;
				})
				if (!confirm("매칭되지 않는 자산들이 존재합니다.\n제외하고 진행하시겠습니까?\n" + asset)) {
					return false;
				}

			}*/
			if(data.riskExists == "F") {
				if (!confirm("이미 진단된 항목이 존재합니다.\n업데이트 하시겠습니까?")) {
					return false;
				}
			}
			excelUpload();
		},
		error : function(data){
			alert("에러가 발생하였습니다. \n 엑셀 파일을 확인하세요.");
		}
	});
}

function excelUpload(){
	var strUrl ="";
	strUrl = "${pageContext.request.contextPath}/riskm/FM-RISKM006_excelSave.do"

	$("#excelUploadForm").ajaxSubmit({	// IE8 Issue: dataType : "json", multipart/form-data
		url : strUrl,
		dataType : "json",
		success : function(data){
			if(data.result == "S" ) {
				alert("등록되었습니다.");
				try {
					opener.searchList($("#uarAssCat").val());
				}
				catch (e) {}
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
		        		<table summary="엑셀 양식 다운로드">
		            		<colgroup>
								<col width="40%" />
								<col width="*" />
							</colgroup>
							<caption>엑셀 업로드</caption>
							<tbody>
								<tr>
									<th class="listTitle" scope="row"><label>자산분류</label></th>
		   							<td class="fontLeft last">
		   								<select id="assCat" name="assCat" class="selectBox"title="항목 선택">
											<option value="">선택</option>
											<c:forEach var="assCat" items="${assCatList}" varStatus="status">
												<option value="${assCat.key}"><c:out value="${assCat.name}" /></option>
											</c:forEach>
			                            </select>
		   							</td>
		   						</tr>
		   						<tr>
			   						<th scope="row"><label for="assGbn">자산유형</label></th>
									<td class="fontLeft last">
										<select id="assGbn" name="assGbn" class="selectBox" title="항목 선택">
											<option value="">전체</option>
											<c:forEach var="assGbn" items="${assGbnList}" varStatus="status">
												<option value="${assGbn.code}" class="${assGbn.categoryKey}"><c:out value="${assGbn.name}" /></option>
											</c:forEach>
										</select>
									</td>
		   						</tr>
		    					<tr>
									<th class="listTitle" scope="row"><label>샘플양식</label></th>
		   							<td class="fontLeft last">
		   								 <button type="button" onclick="excelDownload();"><span class="icoDown"></span>양식 다운로드</button>
		   							</td>
								</tr>
							</tbody>
						</table>
					</div>
					<div>&nbsp;</div>
					<div class="talbeArea">
						<table summary="엑셀 업로드">
		            		<colgroup>
								<col width="40%" />
								<col width="*" />
							</colgroup>
							<caption>엑셀 업로드</caption>
							<tbody>
								<%-- <tr>
									<th class="listTitle" scope="row"><label for="down">자산분류</label></th>
		   							<td class="fontLeft last">
		   								<select id="uarAssCat" name="uarAssCat" class="selectBox"title="항목 선택">
											<option value="">선택</option>
											<c:forEach var="assCat" items="${assCatList}" varStatus="status">
												<option value="${assCat.key}"><c:out value="${assCat.name}" /></option>
											</c:forEach>
											<option value="SW001">소프트웨어-홈페이지</option>
											<option value="SW012">소프트웨어-모바일</option>
			                            </select>
		   							</td>
		   						</tr> --%>
		  						<tr>
			                		<th class="listTitle">업로드 파일</th>
			                        <td class="fontLeft last">
										<input type="file" name="excelFile" style="width:200px">
			                		</td>
			            		</tr>
		        			</tbody>
		    			</table>
					</div>
	       		</div>
			</div>
			<div class="centerBtnArea bottomBtnArea">
				<button type="button" class="popClose close"><span class="icoClose">닫기</span></button>
				<ul class="btnList">
					<li><button type="button" class="btnStrong" onclick="checkExcelUpload();"><span class="icoSave"></span>저장</button></li>
				</ul>
			</div>
		</article>
	</div>
</form>
<form id="excelDownloadForm" name="excelDownloadForm" method="post">
	<input type="hidden" id="service" 	name="service" 	value="${paramMap.excelSvcCod}">
	<input type="hidden" id="dept" 		name="dept" 	value="${paramMap.excelDepCod}">
	<input type="hidden" id="upType"	name="upType"   value="${paramMap.upType}">
	<input type="hidden" id="assCatS" 	name="assCatS" 	value="">
	<input type="hidden" id="assGbnS" 	name="assGbnS" 	value="">

</form>
</body>
</html>