<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="validator"	uri="http://www.springmodules.org/tags/commons-validator"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ce" uri="http://ckeditor.com"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/WEB-INF/include/contents.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<script type="text/javaScript">
<c:if test="${authKey eq 'A' || usrKey eq vo.ubm_rgt_id}">
	$(document).ready(function() {
		var btns = '<li><button type="button" class="btnStrong" onclick="fn_egov_save();"><span class="icoRepair"></span>답변</button></li><li><button type="button" class="btnStrong" onclick="fn_egov_delete();"><span class="icoDel"></span>삭제</button></li>';
		$("#btnGroup").prepend(btns);
	});
	/* 글 삭제 function */
	function fn_egov_delete() {
		if (confirm("삭제하시겠습니까?")) {
			document.viewForm.action = "FM-BOARD004_D.do";
			document.viewForm.submit();
		}
	}

	/* 글 수정 function */
	function fn_egov_save() {
		frm = document.viewForm;
		frm.seq.value = ${vo.ubm_brd_key};
		frm.action = "FM-BOARD004_R.do";
		frm.submit();
	}
</c:if>

	/* 글 목록 화면 function */
	function fn_egov_selectList() {
		document.viewForm.action = "/board/FM-BOARD004.do";
		document.viewForm.submit();
	}

 	function getFile(key) {
		$("#downKey").val(key);
		document.fileDown.action = "/common/getFileDown.do";
		document.fileDown.action = "${pageContext.request.contextPath}/common/getFileDown.do";
		document.fileDown.submit();
	}

	function fn_egov_regist_Restde() {
		document.viewForm.action = "FM-BOARD004_RW.do";
		document.viewForm.submit();
	}
</script>
</head>
<body>
<div>
		<c:import url="/titlebar.do" />
		<form name="viewForm" method="post">
		<input type="hidden" name="seq" />
		<input type="hidden" name="ubm_brd_key" value="${vo.ubm_brd_key}"/>
		<div class="contents">
			<div class="talbeArea">
				<table summary="Q&A">
					<colgroup>
						<col width="15%">
						<col width="*">
						<col width="15%">
						<col width="20%">
					</colgroup>
					<caption>Q&A 상세 정보</caption>
					<tbody>
						<tr>
							<th>제목</th>
							<td class="listTitle last" colspan="3">
								<c:out value="${vo.ubm_brd_tle}" />
							</td>
						</tr>
						<tr>
							<th>작성자</th>
							<td class="fontLeft"><c:out value="${vo.ubm_rgt_id}" /></td>
							<th>등록일</th>
							<td class="last"><c:out value="${vo.ubm_rgt_mdh}" /></td>
						</tr>
						<tr class="last">
                            <th scope="row">질문 유형</th>
                            <td class="fontLeft last" colspan="3">
                                <span class="floatLine">${std.uccSndNam}</span>
                                <ul class="listPermission floatLine">
                                    <li>
                                        <strong>통제목적</strong>${std.ucm1lvCod}
                                    </li>
                                    <li>
                                        <strong>통제항목</strong>${std.ucm2lvCod}
                                    </li>
                                    <li>
                                        <strong>통제점검</strong>${std.ucm3lvCod}
                                    </li>
                                </ul>
                            </td>
                        </tr>
					</tbody>
				</table>
			</div>
			<div class="listDetailArea">
				<div class="talbeArea">
					<table summary="통제점검 1.2.3.3의 내용으로 상세내용, 점검항목, 설명 등의 항목으로 설명하고 있습니다.">
						<colgroup>
							<col width="15%">
                            <col width="*">
                            <col width="15%">
                            <col width="20%">
                        </colgroup>
                        <tbody>
							<tr>
								<th scope="row">상세내용</th>
                                <td class="listTitle last" colspan="3">${std.ucm1lvNam}</td>
                            </tr>
                            <tr>
                                <th scope="row">점검항목</th>
                                <td class="listTitle last" colspan="3">${std.ucm2lvNam}</td>
                            </tr>
                            <tr>
                                <th scope="row">설명</th>
                                <td class="listTitle last" colspan="3">   ${std.ucm3lvNam}</td>
                             </tr>
                         </tbody>
                     </table>
                 </div>
                 <p class="detailTitle">질문</p>
                 ${vo.ubm_brd_cts}
             </div>
             <div class="listDetailArea">
                                <p class="detailTitle">답변</p>
                                <c:if test='${vo.ubm_ans_cts == null}'>
                                	답변이 등록되지 않았습니다.
                                </c:if>
                                <c:if test='${vo.ubm_ans_cts != null}'>
                                	${vo.ubm_ans_cts}
                                </c:if>
                                <div class="uplidFileList">
                                    <p class="titleArea">첨부파일 : <strong>${fn:length(file)}</strong></p>
                                    <ul class="listArea">
					<c:forEach var="result" items="${file}" varStatus="status">
						<li><a href="#none" onclick="getFile(<c:out value='${result.umf_fle_key}'/>)"><span class="icoDown"></span><c:out value="${result.umf_con_fnm}"/></a></li>
					</c:forEach>
					</ul>
                                </div>
                            </div>
			<div class="bottomBtnArea">
				<ul class="btnList" id="btnGroup">
					<li>
						<button class="btnStrong" onclick="fn_egov_selectList();">
							<span class="icoList"></span>목록
						</button>
					</li>
				</ul>
			</div>
		</div>
		</form>
		<form action="" name="fileDown" id="fileDown" method="post">
    		<input type="hidden" name="downKey" id="downKey">
    		<input type="hidden" name="where" id="where" value="brd">
    	</form>
	</div>
	</body>
	</html>