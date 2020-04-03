<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
<%@ include file="/WEB-INF/include/contents.jsp"%>
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
<script type="text/javascript" src="/common/js/jquery.form.js"></script>
<script type="text/javascript" src="/common/js/common.js"></script>
<script  type="text/javascript">

function fn_save(obj, code) {
	if($("#ufpImpDes").val() == ""){
		alert("보완 내역을 입력하여 주세요.");
		$("#ufpImpDes").focus();
		return;
	}

	if ($(obj).attr("title")) {
		if ( !confirm($(obj).attr("title") + " 하시겠습니까?")) {
			return;
		}
	}
	$("#ufpStaCod").val(code);

	$("#popForm").ajaxSubmit({	// IE8 Issue: dataType : "json", multipart/form-data
		url			: "${pageContext.request.contextPath}/inspt/FM-INSPT002_MNG_U.do",
		dataType : "json",
		success	: function(data){
			alert("처리되었습니다.");
			$(opener.location).attr("href", "javascript:reloadMngList('"+$("#ufmFltKey").val()+"');" );
			window.close();
		},
		error : function(){
			alert('error');
		}
	});
}

function getFile(key, where) {
	$("#downKey").val(key);
	$("#where").val(where);
	document.fileDown.action = "${pageContext.request.contextPath}/common/getFileDown.do";
	document.fileDown.submit();
}

var fileCnt = 0;
function addFile() {
	fileCnt++;
	var inTag = '<li id="liFile_'+fileCnt+'">' +
				'<input type="file" name="ufmImpFile'+fileCnt+'" style="width:90%">'+
				'<a href="javascript:delFile('+fileCnt+')" class="del" title="삭제">x<em class="hidden">삭제</em></a></li>';
	$("#ulFile").prepend(inTag);
}

function delFile(cnt) {
	$("#liFile_"+cnt).remove();
}

function delFile_U(key){
	if(confirm("해당 파일을 삭제하시겠습니까?")){
		$.ajax({
			url		: "${pageContext.request.contextPath}/common/delFile.do",
			type		: "post",
			data		: {"key":key},
			dataType	: "json",
			success	: function(e){
				alert("삭제되었습니다.");
				$("#insFile_"+key).remove();
			 },
			 error : function(){
				alert("error");
			 }
		 });
	}
}

</script>
</head>
<body>
<form id="popForm" name="popForm" method="post" enctype="multipart/form-data">
	<input type="hidden" id="ufmFltKey" name="ufmFltKey" value="${info.ufpFltKey}" />
	<input type="hidden" id="ufpMapKey" name="ufpMapKey" value="${info.ufpMapKey}" />
	<input type="hidden" id=ufpStaCod name="ufpStaCod" />
	<div id="skipnavigation">
	    <ul>
	        <li><a href="#content-box">본문 바로가기</a></li>
	    </ul>
	</div>
	<div id="wrap" class="pop">
		<header>
			<div class="borderBox"></div>
			<h1>결함조치내역</h1>
		</header>
		<article class="cont" id="content-box">
			<div class="cont_container">
				<div class="contents">
					<div class="talbeArea">
						<table summary="위험관리 세부내용">
							<colgroup>
								<col width="21%">
								<col width="30%">
								<col width="20%">
								<col width="30%">
							</colgroup>
							<caption>결함조치내역</caption>
							<tbody>
								<tr>
									<th scope="row" class="listTitle"><label for="ufmCtrCod">컴플라이언스</label></th>
									<td class="fontLeft">
										<input type="text" class="inputText wdShort" value="<c:out value='${info.ufmCtrCod}'/>" readonly="readonly"/>
									</td>
									<th scope="row" class="listTitle"><label for="ufmFltLvl">결함등급</label></th>
									<td class="fontLeft last">
										<input type="text" class="inputText wdShort" value="<c:out value='${info.ufmFltLvl}'/>" readonly="readonly"/>
									</td>
								</tr>
								<tr>
									<th scope="row"><label for="ufmFltMdh">결함등록일자</label></th>
									<td class="fontLeft">
										<input type="text" class="inputText wdShort" value="<c:out value='${info.ufmFltMdh}'/>" readonly="readonly"/>
									</td>
									<th scope="row"><label for="ufpStaCod">보완조치결과</label></th>
									<td class="fontLeft last">
										<input type="text" class="inputText wdShort" value="<c:out value='${info.ufpStaNam}'/>" readonly="readonly"/>
									</td>
								</tr>
								<tr>
									<th scope="row"><label for="ufmFltNam">관련조항</label></th>
									<td class="fontLeft last" colspan="3"><c:out value="${info.ufmCtrDes}"/> - <c:out value="${info.ufmCtrKey}"/></td>
								</tr>
								<tr>
									<th scope="row"><label for="ufmFltNam">결함명</label></th>
									<td class="fontLeft last" colspan="3"><c:out value="${info.ufmFltNam}"/></td>
								</tr>
								<tr>
									<th scope="row"><label for="ufmFltDes">결함내용</label></th>
									<td class="fontLeft last" colspan="3">
										<textarea readonly="readonly" class="txtAreaType04">${info.ufmFltDes}</textarea>
									</td>

								</tr>
								<tr>
									<th scope="row"><label for="ufpImpDes">보완내역</label></th>
									<td class="fontLeft last" colspan="3">
										<textarea id="ufpImpDes" name="ufpImpDes" maxlength="250" class="txtAreaType04">${info.ufpImpDes}</textarea>
									</td>
								</tr>
								<tr>
									<th scope="row"><label for="ufmImpDes">보완증적파일</label></th>
									<td class="fontLeft last" colspan="3">
										<div class="uplidFileList margin10">
											<ul id="ulFile" class="listArea width100">
											<c:forEach var="file" items="${fileList}" varStatus="status">
												<li id="insFile_${file.umf_fle_key}">
													<a href="javascript:getFile(<c:out value='${file.umf_fle_key}'/>, 'ins')"><span class="icoDown"></span><c:out value="${file.umf_con_fnm}"/></a>
												  	<a href="javascript:delFile_U(<c:out value='${file.umf_fle_key}'/>)" class="del" title="삭제">x<em class="hidden">삭제</em></a>
												</li>
											</c:forEach>
											</ul>
											<button type="button" onclick="addFile();"><span class="icoUpload"></span>파일 추가</button>
										</div>

									</td>
								</tr>
								<c:if test="${info.ufpCofYn eq 'Y'}">
									<tr>
										<th scope="row"><label for="ufpCofMdh">보완승인일자</label></th>
										<td class="fontLeft"><c:out value="${info.ufpCofMdh}"/></td>
										<th scope="row"><label for="ufpCofId">승인자</label></th>
										<td class="fontLeft last"><c:out value="${info.ufpCofNam}"/></td>
									</tr>
								</c:if>
							</tbody>
						</table>
					</div>
				</div>
			</div>
	        <div class="centerBtnArea bottomBtnArea">
				<button type="button" class="popClose close"><span class="icoClose">닫기</span></button>
				<ul class="btnList">
				<c:if test="${info.ufpStaCod eq 0 || info.ufpStaCod eq 2}">
					<li><button type="button" class="btnStrong" onclick="fn_save(this, 0);"><span class="icoSave"></span>임시저장</button></li>
					<li><button type="button" class="btnStrong" onclick="fn_save(this, 1);" title="보완승인요청"><span class="icoSave"></span>보완승인 요청</button></li>
				</c:if>
				<c:if test="${info.ufpStaCod eq 1 && authKey eq 'A'}">
					<li><button type="button" class="btnStrong" onclick="fn_save(this, 9);"><span class="icoSave"></span>보완승인 완료</button></li>
					<li><button type="button" class="btnStrong" onclick="fn_save(this, 2);" title="보완승인반려"><span class="icoSave"></span>반려</button></li>
				</c:if>
				</ul>
           </div>
	    </article>
	</div>
</form>
<form action="" name="fileDown" id="fileDown" method="post">
	<input type="hidden" name="downKey" id="downKey">
	<input type="hidden" name="where" id="where" value="ins">
</form>
</body>
</html>