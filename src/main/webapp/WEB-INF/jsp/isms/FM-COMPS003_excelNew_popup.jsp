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
<style>
.selectBox {width: auto;}
.standardKind {font-size: 11px; font-weight: bold; padding-left: 10px;}
</style>
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
	$("#standard").change(function(){
		var kindArr = {"-":"", "default":"고정3단계", "isms":"ISMS", "isms_p":"ISMS-P", "msit":"과기정통부", "infra_mp":"기반시설 관리적/물리적", "infra_la":"기반시설 수준평가"};
		var kind = $(this).find("option:selected").attr("kind");
		if(!kind) kind = "default";
		$(".standardKind").text(kindArr[kind]);
	});
	$("#standard").val($(opener.document).find("#standard").val()).change();

	var $serviceCheckbox = $(".service-checkbox");
	var $serviceCheckboxToggle = $(".service-checkbox-toggle");

	$serviceCheckboxToggle.on("change", function (event) {
		var checked = $(this).prop("checked");
		$serviceCheckbox.prop("checked", checked);
	});
	$serviceCheckbox.on("change", function (event) {
		var checked = $serviceCheckbox.filter(":checked");
		if (checked && checked.length === $serviceCheckbox.length) {
			$serviceCheckboxToggle.prop("checked", true);
		} else {
			$serviceCheckboxToggle.prop("checked", false);
		}
	})

});

function excelUpload(){
	if($("#standard").val() == "") {
		alert("컴플라이언스를 선택하세요");
		return;
	}

	if($("input[name=service]").length){
		var service=false;
		for(var i =0; i<$("input[name=service]").length; i++){
			if( $("input[name=service]").eq(i).prop("checked") ){
				service = true;
				break;
			}
		}
		if(!service) {
			alert("서비스를 선택하세요");
			return;
		}
	}

	$("#excelUploadForm").ajaxSubmit({	// IE8 Issue: dataType : "json", multipart/form-data
		url : "${pageContext.request.contextPath}/comps/FM-COMPS003_excelNew_Upload.do",
		dataType : "json",
		data : {"service":$('input[name=service]:checked').val()},
		success : function(data){
			if(data.result == "S" ) {
				alert("등록되었습니다.");
				opener.location.reload();
				window.close();
			} else {
				alert(data.message);
			}
		},
		error : function(data){
			console.log(data);
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
								<col width="150px" />
								<col width="*" />
							</colgroup>
							<caption>엑셀 업로드</caption>
							<tbody>
		    					<tr>
									<th class="listTitle" scope="row"><label for="standard">컴플라이언스</label></th>
		   							<td class="fontLeft last">
										<select class="selectBox" id="standard" name="standard">
											<option value="" kind="-">컴플라이언스 선택</option>
											<c:forEach var="compList" items="${compList}" varStatus="status">
												<option value="${compList.code}" kind="${compList.kind}">${compList.name}</option>
											</c:forEach>
										</select>
										<span class="standardKind"></span>
		   							</td>
								</tr>
		  						<tr>
			                		<th class="listTitle">서비스</th>
			                        <td class="fontLeft last">

                                        <%--<p style="color: #ff5a20; margin-bottom: 8px">※ Self-Audit 서비스에는 하나의 컴플라이언스만 생성하세요.</p>--%>

										<table>
											<tr>
												<th><input class="service-checkbox-toggle" type="checkbox"></th>
												<th>서비스명</th>
												<%--<th>생성된 컴플라이언스</th>--%>
											</tr>
											<c:forEach var="result" items="${resultList}" varStatus="status">
											<tr>
												<td>
													<input id="service-${result.code}" class="service-checkbox" type="checkbox" name="service" value="<c:out value="${result.code}"/>">
												</td>
												<td><label style="cursor: pointer" for="service-${result.code}">${result.name}</label></td>
												<%--
												<td data-service-code="${result.code}">
													<ul style="text-align: left">
														<c:if test="${empty result.compliance}">
															<li><span style="color: #ccc">생성된 컴플라이언스가 없습니다.</span></li>
														</c:if>
														<c:forEach items="${result.compliance}" var="compliance" varStatus="complianceStatus">
														<li data-compliance-code="${compliance.complianceCode}">
															${complianceStatus.count}. <c:if test="${empty compliance.complianceName}"><span style="color: #ff5a20">정보가 없습니다</span></c:if> ${compliance.complianceName}
														</li>
														</c:forEach>
													</ul>
												</td>
												--%>
											</tr>
											</c:forEach>
										</table>

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