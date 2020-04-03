<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="ce" uri="http://ckeditor.com" %>
<%@ include file="/WEB-INF/include/head.jsp"%>
<html>
<head>
<script type="text/javaScript" language="javascript">

$(document).ready(function() {
	getManCylMax();
});

function getManCylMax() {
	var man_end = '';

	$.ajax({
		url			: "${pageContext.request.contextPath}/comps/FM-COMPS001_getManCylMax.do",
		type		: "post",
		dataType	: "json",
		success	: function(data){
			man_end = data.result;
//			alert("man_end - "+man_end);

			var sub1 = man_end.substring(0, 4);
			var sub2 = man_end.substring(4, 6);
			var sub3 = man_end.substring(6, 8);
			var bar_date = sub1+"-"+sub2+"-"+sub3;
			var sDate = new Date(bar_date);
			var eDate = new Date(bar_date);
			sDate.setDate(Number(sub3)+1);
			eDate.setDate(Number(sub3)+2);

			// 신규 심사주기 등록시 현재 주기 기준으로 이후날짜로 입력하게 설정
			$( "#umm_std_dat" ).datepicker({
				 changeMonth: true,
		         changeYear: true,
		         showButtonPanel: true,
				minDate : sDate
			});
			$( "#umm_std_dat_btn" ).click(function(){
		        $( "#umm_std_dat" ).focus()
		    });
			$( "#umm_end_dat" ).datepicker({
				changeMonth: true,
		        changeYear: true,
		        showButtonPanel: true,
				minDate : eDate
			});
			$( "#umm_std_end_btn" ).click(function(){
		        $( "#umm_end_dat" ).focus()
		    });

		},
		error : function(){
			alert("error :: 설정된 주기가 없습니다.");
		}
	});

}

/* 글 목록 화면 function */
function fn_egov_selectList() {
   	document.detailForm.action = "FM-COMPS001.do";
   	document.detailForm.submit();
}

/* 글 등록 function */
function fn_egov_save() {

	var sDate = $("#umm_std_dat").val();
	var eDate = $("#umm_end_dat").val();


	if($("#umm_man_tle").val() == "") {
		alert("제목을 입력하여 주세요.");
		return;
	}

	if(sDate > eDate){
		alert("종료일자가 시작일자보다 빠릅니다. 다시 확인해 주세요.");
		return;
	}

	$("#umm_man_con").val(CKEDITOR.instances.umm_man_con.getData());

	var strUrl = "";
	if($("#umm_man_cyl").val() == "") {
		strUrl = "FM-COMPS001_W.do";
	} else {
		strUrl = 'FM-COMPS001_U.do'
	}

	$("#detailForm").ajaxSubmit({
		url : "${pageContext.request.contextPath}/comps/"+strUrl,
		success : function(data){
			var ret = data.result;
			if(ret == '1'){
				alert("심사주기 기간이 중복됩니다.");
			}else{
				alert("저장되었습니다.");
				location.href = 'FM-COMPS001.do';
			}

		},
		error : function(e){
			alert("error");
		}
	});



	//document.detailForm.action = strUrl;
	//document.detailForm.submit();
}
</script>
</head>
<body>
<form id="detailForm" name="detailForm" method="post">
<input type="hidden" id="umm_man_cyl" name="umm_man_cyl" value="${vo.umm_man_cyl}" />
<c:import url="/titlebar.do" />
<div class="contents">
    <div class="talbeArea">
        <table summary="운영주기설정의 제목, 인증여부, 시작일자, 종료일자, 심사구분 등의 정보를 설정할 수 있습니다.">
            <colgroup>
                <col width="15%" />
				<col width="50%" />
				<col width="15%" />
				<col width="20%" />
            </colgroup>
            <caption>운영주기 설정</caption>
            <tbody>
                <tr>
                    <th scope="row"><label for="umm_man_tle">제목</label></th>
                    <td class="listTitle">
                    	<input id="umm_man_tle" name="umm_man_tle" maxlength="30" style="width:180px" value="${vo.umm_man_tle}" class="inputText" />
                    </td>
                    <th scope="row">인증여부</th>
                    <td class="fontLeft last">
                    	<select id="umm_cfm_yn" name="umm_cfm_yn" class="selectBox">
							<option value="Y" <c:if test="${vo.umm_cfm_yn eq 'Y'}">selected="selected"</c:if>>심사</option>
							<option value="N" <c:if test="${vo.umm_cfm_yn eq 'N'}">selected="selected"</c:if>>미심사</option>
						</select>
                    </td>
                </tr>
                <tr class="last">
                	<th scope="row"><label for="umm_std_dat">운영주기 기간</label></th>
					<td class="fontLeft">
					<c:if test="${not empty vo.umm_man_tle}">
						${vo.umm_std_dat} ~ ${vo.umm_end_dat}
					</c:if>
					<c:if test="${empty vo.umm_man_tle}">
						<input id="umm_std_dat" name="umm_std_dat"	class="inputText cal" style="width:80px"  maxlength="15" />
						<button id="umm_std_dat_btn" type="button"><span class="icoCal"><em>달력</em></span></button>
						 ~ <input id="umm_end_dat" name="umm_end_dat" class="inputText" style="width:80px"  maxlength="15" />
						 <button id="umm_std_end_btn" type="button"><span class="icoCal"><em>달력</em></span></button>
					</c:if>
					</td>
					<th scope="row"><label for="umm_cfm_gbn">심사구분</label></th>
					<td class="fontLeft last">
						<select id="umm_cfm_gbn" name="umm_cfm_gbn" class="selectBox">
							<option value="1" <c:if test="${vo.umm_cfm_gbn eq '1'}">selected="selected"</c:if>>최초심사</option>
							<option value="2" <c:if test="${vo.umm_cfm_gbn eq '2'}">selected="selected"</c:if>>재심사</option>
							<option value="3" <c:if test="${vo.umm_cfm_gbn eq '3'}">selected="selected"</c:if>>갱신심사</option>
							<option value="4" <c:if test="${vo.umm_cfm_gbn eq '4'}">selected="selected"</c:if>>사후심사</option>
						</select>
					</td>
                </tr>
            </tbody>
        </table>
    </div>
    <div class="listDetailArea write">
        <div class="editerArea">
			<textarea  id="umm_man_con" name="umm_man_con" maxlength="500" class="txtAreaType03">${vo.umm_man_con}</textarea>
			<ce:replace replace="umm_man_con" basePath="${pageContext.request.contextPath}/editor/ckeditor"></ce:replace>
        </div>
    </div>
    <div class="bottomBtnArea">
        <ul class="btnList">
            <li>
            <c:choose>
              <c:when test="${empty vo.umm_man_tle}">
              	<button type="button" class="btnStrong" onclick="fn_egov_save();"><span class="icoAdd"></span>등록</button>
              </c:when>
              <c:otherwise>
              	<button type="button" class="btnStrong" onclick="fn_egov_save();"><span class="icoRepair"></span>수정</button>
              </c:otherwise>
            </c:choose>
            	<button type="button" class="btnStrong" onclick="fn_egov_selectList();"><span class="icoList"></span>목록</button>
            </li>
        </ul>
    </div>
</div>
</form>
</body>
</html>