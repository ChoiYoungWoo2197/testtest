<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<script type="text/javascript">

/* 글 목록 화면 function */
function fn_egov_selectList() {
   	document.viewForm.action = "/comps/FM-COMPS001.do";
   	document.viewForm.submit();
}

/* 글 등록 function */
function fn_egov_save() {
	document.viewForm.action = "FM-COMPS001_R.do";
	document.viewForm.submit();
}

</script>
</head>
<body>
<form name="viewForm" method="post">
<input type="hidden" name="seq" value="${vo.umm_man_cyl}"/>
</form>
<c:import url="/titlebar.do" />
<div class="contents">
    <div class="talbeArea">
        <table>
            <colgroup>
                <col width="16%" />
				<col width="18%" />
				<col width="16%" />
				<col width="18%" />
				<col width="16%" />
				<col width="16%" />
            </colgroup>
            <caption>운영주기 상세</caption>
            <tbody>
                <tr>
                	<th scope="row">제목</th>
                   	<td colspan="3"><c:out value="${vo.umm_man_tle}"/></td>
                    <th scope="row">심사여부</th>
                    <td class="last"><c:out value="${vo.umm_cfm_yn_txt}"/></td>
                </tr>
                <tr class="last">
                	<th scope="row">운영주기 기간</th>
					<td colspan="3"><c:out value="${vo.umm_std_dat}"/> ~ <c:out value="${vo.umm_end_dat}"/></td>
                	<th scope="row">심사구분</th>
					<td class="last"><c:out value="${vo.umm_cfm_gbn_txt}"/></td>
                </tr>
            </tbody>
        </table>
    </div>
    <div class="listDetailArea">${vo.umm_man_con}</div>
    <div class="bottomBtnArea">
        <ul class="btnList">
            <li>
              	<button class="btnStrong" onclick="fn_egov_save();"><span class="icoRepair"></span>수정</button>
              	<button class="btnStrong" onclick="fn_egov_selectList();"><span class="icoList"></span>목록</button>
            </li>
        </ul>
    </div>
</div>
</body>
</html>