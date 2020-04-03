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
	<link rel="stylesheet" type="text/css" href="/common/css/tailwind.min.css"/>
	<link rel="stylesheet" type="text/css" href="/common/css/font-awesome.min.css"/>
	<style>
		.upload-guide {
			margin-top: 1rem;
		}
	</style>

	<!--[if IE]> <script src="/common/js/html5.js"></script> <![endif]-->
	<script type="text/javascript" src="/common/js/jquery.js"></script>
	<script type="text/javascript" src="/common/js/jquery.form.js"></script>
	<script type="text/javascript" src="/common/js/jquery.blockUI.js"></script>
	<script type="text/javascript" src="/common/js/ismsUI.js"></script>
	<script type="text/javascript" src="/common/js/common.js"></script>
	<script  type="text/javascript">
		$(document).ready(function() {
			// 로딩바 적용
			loadingBarSet();
		});

		function excelUpload() {

			var uploadFilePath = $("[name='file']").val();

			if (uploadFilePath == "") {
				alert("업로드할 파일을 선택하세요.");
				return false;
			}

			var strUrl = "${pageContext.request.contextPath}/inspt/FM-INSPT006_SMART_UPLOAD_ASYNC.do";

			$("#excelUploadForm").ajaxSubmit({	// IE8 Issue: dataType : "json", multipart/form-data
				url : strUrl,
				data: {
					bcyCode: "${bcyCode}",
					service: "${service}",
					standard: "${standard}"
				},
				dataType : "json",
				success : function(data){
					if(data.status == 200 ) {
						alert("등록되었습니다.");
						opener.location.reload();
						window.close();
					} else {
						alert("형식에 맞지 않는 데이터입니다. \n엑셀 파일을 확인하세요.");
					}
				},
				error : function(data){
					alert("에러가 발생하였습니다. \n 엑셀 파일을 확인하세요.");
				},
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
			<h1>기반시설 평가결과 스마트 업로드</h1>
		</header>
		<article class="cont" id="content-box">
			<div class="cont_container" style="padding-top: 30px;">
				<div class="contents">
					<div class="talbeArea">
						<table summary="엑셀 다운로드">
							<colgroup>
								<col width="40%" />
								<col width="*" />
							</colgroup>
							<caption>평가결과 엑셀업로드</caption>
							<tbody>
							<tr>
								<th class="listTitle" style="height: 50px;">업로드 파일</th>
								<td class="fontLeft last">
									<input type="file" name="file" style="width:200px">
								</td>
							</tr>
							</tbody>
						</table>
					</div>
					<div class="upload-guide">
						<h3 class="font-bold text-lg"><i class="fa fa-minus" aria-hidden="true"></i> 업로드별 처리내용</h3>
						<div class="talbeArea">
							<table>
								<thead>
								<tr>
									<th>파일형식</th>
									<th>파일구성</th>
									<th>처리</th>
								</tr>
								</thead>
								<tbody>
								<tr>
									<td>xls</td>
									<td>-</td>
									<td>평가결과 수정</td>
								</tr>
								<tr>
									<td>xlsx</td>
									<td>-</td>
									<td>평가결과 수정</td>
								</tr>
								<tr>

									<td rowspan="2">zip</td>
									<td>증적파일 디렉토리</td>
									<td>증적파일 업로드</td>
								</tr>
								<tr>
									<td>평가결과 엑셀파일, 증적파일 디렉토리</td>
									<td>평가결과 수정, 증적파일 업로드</td>
								</tr>
								</tbody>
							</table>
						</div>
					</div>
					<div class="upload-guide">
						<h3 class="font-bold text-lg"><i class="fa fa-minus" aria-hidden="true"></i> 압축파일(ZIP) 구조별 예시</h3>
						<h4 class="mt-3">증적파일 업로드</h4>
						<div class="pt-1">
							<img src="/common/images/common/smart-upload-sample-1.jpg">
						</div>
						<h4 class="mt-5">증적파일과 평가결과 업로드</h4>
						<div class="pt-1">
							<img src="/common/images/common/smart-upload-sample-2.jpg">
						</div>
					</div>
				</div>
			</div>
			<div class="centerBtnArea bottomBtnArea">
				<button type="button" class="popClose close"><span class="icoClose">닫기</span></button>
				<ul class="btnList">
					<li><button type="button" class="btnStrong" onclick="excelUpload();"><span class="icoSave"></span>저장</button></li>
				</ul>
			</div>
		</article>
	</div>
</form>
</body>
</html>