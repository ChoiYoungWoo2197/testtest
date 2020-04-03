<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
<%@ include file="/WEB-INF/include/contents.jsp"%>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=10" />
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="/common/css/reset.css"/>
<link rel="stylesheet" type="text/css" href="/common/css/common.css"/>
<link rel="stylesheet" type="text/css" href="/common/css/sub.css"/>
<link rel="stylesheet" type="text/css" href="/common/css/pop.css"/>
<!--[if IE]> <script src="/common/js/html5.js"></script> <![endif]-->
<script type="text/javascript" src="/common/js/jquery.js"></script>
<script type="text/javascript" src="/common/js/ismsUI.js"></script>
<script type="text/javascript" src="/common/js/jquery.chained.min.js"></script>
<script type="text/javascript" src="/common/js/common.js"></script>
<script type="text/javascript">

$(document).ready(function() {
	searchPop();
	stOrgList();
});

function searchPop(p){
	if (p) {
		$("#worker").val("");
	}
	var formData = formSerialize($("#searchForm"));
	$.ajax({
		url			: "${pageContext.request.contextPath}/state/FM-STATE002_search.do",
		type		: "post",
		data		: formData,
		dataType	: "json",
		success		: function(data){
			var html = "";
			for(var i=0; i<data.result.length; i++) {
				html +="<tr>";
				html +="<td>"+(data.result.length - i)+"</td>";
				html +="<td>"+data.result[i].dept+"</td>";
				html +="<td>"+data.result[i].unam+" "+data.result[i].posi+"</td>";
				html +="<td>"+data.result[i].std+"</td>";
				html +="<td>"+data.result[i].goal+"</td>";
				html +="<td><a href=\"javascript:workPopup('" + data.result[i].wkey + "','" + data.result[i].dkey + "');\">"+data.result[i].dnam+"</td>";
				html +="<td class='last'>"+data.result[i].sta+"</td>";
				//html +="<td class='last'></td>";
				html +="</tr>";
			}
			$("#workList").empty();
			$("#workList").html(html);
		 }
	 });
}

/* function workPopup(wrkKey,trcKey){
	window.open("","workPopup","width=730, height=550, menubar=no, location=no, status=no,scrollbars=yes");
	$('input[name=utwWrkKey').val(wrkKey);
	$('input[name=utwTrcKey').val(trcKey);
	$('form[name=workPopupForm]').submit();
} */
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
        <h1>수행업무 현황조회</h1>
    </header>

    <article class="cont" id="content-box">
        <div class="cont_container">
            <div class="search">
            <form id="searchForm" method="post" name="searchForm">
            <input type="hidden" id="worker" name="worker" value="${searchVO.worker}"/>
            <input type="hidden" id="dept" name="dept" value="${searchVO.dept}"/>
                <div class="defalt">
                    <fieldset class="searchForm">
                        <legend>기본검색</legend>
                        <ul class="detail newField">
                        	<li>
                                <span class="title"><label for="standard">통제항목</label></span>
			                    <sb:select name="standard" type="A" code="STND" value="${searchVO.standard }"/>
                            </li>
                            <li>
                                <span class="title"><label for="service">서비스</label></span>
                                <sb:select name="service" type="S" value="${searchVO.service }"/>
                            </li>

							<li>
								<span class="title"><label for="stOrg">부서</label></span>
								<sb:select name="stOrg" type="stOrg" value="${searchVO.stOrg}" allText="본부전체" />
								<sb:select name="hqOrg" type="hqOrg" value="${searchVO.hqOrg}" allText="그룹,담당전체" />
								<sb:select name="gpOrg" type="gpOrg" value="${searchVO.gpOrg}" allText="팀전체" />
							</li>
                            <li>
                                <span class="title"><label for="worker">담당자</label></span>
                                <input id="workerName" name="workerName" class="inputText wdShort" type="text" title="담당자 입력" placeholder="담당자 이름">
                            </li>
                            <li>
                                <span class="title"><label for="staCombo">상태조회</label></span>
                                <select id="staCombo" name="sta" class="selectBox" title="항목 선택">
                                    <option value="" selected>전체</option>
									<option value="1" <c:if test="${searchVO.searchCondition == '1'}">selected="selected"</c:if>>완료</option>
									<option value="2" <c:if test="${searchVO.searchCondition == '2'}">selected="selected"</c:if>>지연</option>
									<option value="3" <c:if test="${searchVO.searchCondition == '3'}">selected="selected"</c:if>>미진행</option>
									<%//<option value="4" <c:if test="${searchVO.searchCondition == '4'}">selected="selected"</c:if>>금일완료</option> %>
                                </select>
                            </li>
                            <li class="btnArea">
		                 		 <button class="btnSearch" onclick="searchPop(true);" type="button">조건으로 검색</button>
							</li>
                        </ul>
                    </fieldset>
                </div>
            </form>
            </div>

            <div class="contents">
                <div class="title">수행업무 현황</div>
                <div class="talbeArea">
                    <table summary="수행업무 현황을 No., 부서, 담당자(직위), 통제항목, 항목번호, 업무명, 기한(상태), 비고 등의 항목으로 설명하고 있습니다.">
                        <colgroup>
                            <col width="5%">
                            <col width="15%">
                            <col width="12%">
                            <col width="10%">
                            <col width="10%">
                            <col width="*">
                            <col width="10%">
                            <%-- <col width="10%"> --%>
                        </colgroup>
                        <caption>담당자별 현황</caption>
                        <thead>
                            <tr>
                                <th scope="col">No.</th>
                                <th scope="col">부서</th>
                                <th scope="col">담당자(직위)</th>
                                <th scope="col">통제항목</th>
                                <th scope="col">항목번호</th>
                                <th scope="col">업무명</th>
                                <th scope="col" class="last">기한(상태)</th>
                                <!-- <th scope="col" class="last">비고</th> -->
                            </tr>
                        </thead>
                        <tbody id="workList">
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="centerBtnArea closeArea">
            <button class="btnStrong close">닫기</button>
        </div>
    </article>
</div>
<form name="hiddenMap">
	<input type="hidden" id="link_id" name="link_id"/>
	<input type="hidden" id="link_dept" name="link_dept"/>
	<input type="hidden" id="link_std" name="link_std"/>
	<input type="hidden" id="link_sta" name="link_sta"/>

</form>
<form id="workPopupForm" name="workPopupForm" action="../mwork/FM-MWORK_popup.do" method="post" target="workPopup">
	<input type="hidden" id="utwWrkKey" name="utwWrkKey" value="">
	<input type="hidden" id="utwTrcKey" name="utwTrcKey" value="">
</form>
<form id="postPopup" name="postPopup" method="post"></form>
 </body>
 </html>