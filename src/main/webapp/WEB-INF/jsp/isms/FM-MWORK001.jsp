<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/include/contents.jsp"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<script type="text/javascript">
	function moveSchedule(year,month){
		if(year != '' && month != '' ){
			if(month > 12){
				year = Number(year) + 1
				month = 1
			}else if(month < 1){
				year = Number(year) - 1
				month = 12
			}
		}
		$('input[name=sYear]').val(year);
		$('input[name=sMonth]').val(month);
		$('form[name=searchData]').attr('target','').attr('action','${pageContext.request.contextPath}/mwork/FM-MWORK001.do').submit();
	}
	function selectYear(year,_this){
		$('input[name=sYear]').val(year);
		$('.selectYear').find('li').each(function(){
			$(this).removeClass('select');
		});
		$(_this).parent('li').addClass('select');
	}
	/* function workPopup(wrkKey,trcKey){
		window.open("","workPopup","width=730, height=550, menubar=no, location=no, status=no,scrollbars=yes");
		$('input[name=utwWrkKey').val(wrkKey);
		$('input[name=utwTrcKey').val(trcKey);
		$('form[name=workPopupForm]').submit();
	} */
	function excelDown(){
		$('form[name=searchData]').attr('target','').attr('action','${pageContext.request.contextPath}/excel/FM-MWORK001.do').submit();
	}
	function reportDown(){
		window.open("","reportDown","width=1024, height=720, menubar=no, location=no, status=no,scrollbars=yes");
		$('form[name=searchData]').attr('target','reportDown').attr('action','${pageContext.request.contextPath}/report/FM-MWORK001.do').submit();
	}
	$(document).ready(function(){
		$('.talbeArea').find('td').each(function(){
			$(this).find('strong').text($(this).find('li').length);
		});

		// tooltip
		$("#tbody").tooltip();
	});
</script>
</head>
<body>
                   <c:import url="/titlebar.do" />
                   <form id="searchData" method="post" name="searchData">
                   	<input type="hidden" name="sessionStd" value="${sessionStd }" />
                    <input type="hidden" name="sYear" value="${sYear }" />
		<input type="hidden" name="sMonth" value="${sMonth }"/>
                   </form>
                   <div class="contents">
                       <div class="calendarUI">
                           <div class="calendarHead">
                               <div class="nav_calendar" style="max-width:30%;">
                                   <ul class="btnArea">
                                       <li class="beforeDuration"><a href="#none" onclick="moveSchedule('${sYear }','${sMonth-1 }');"><span>이전 기간</span>&lt;</a></li>
                                       <li class="afterDuration"><a href="#none" onclick="moveSchedule('${sYear }','${sMonth+1 }');"><span>다음 기간</span>&gt;</a></li>
                                   </ul>
                                   <span class="info_calendar">
                                       <span class="year">${sYear }.</span><span class="mon">${sMonth } <span class="eMon">${dMonth }</span></span>
                                   </span>
                                   <button type="button" class="btnOpen"><strong class="hidden">직접선택</strong><span></span></button>
                                   <a href="#none" class="btn_today" onclick="moveSchedule('','');">오늘</a>
                                   <div class="select_layer">
                                       <div class="inner_select_layer">
                                           <div class="layer_head">
                                               <strong class="tit_select">직접선택</strong>
                                           </div>
                                           <div class="layer_body">
                                               <div class="wrap_list">
                                                   <ul class="list_date scroll selectYear">
                                                    <c:forEach var="fYear" items="${calendarYear }" varStatus="status">
                                        	<li <c:if test="${fYear.syear == sYear }">class="select"</c:if>><a href="#none" onclick="selectYear('${fYear.syear }',this);">${fYear.syear }년</a></li>
                                        </c:forEach>
                                                   </ul>
                                                   <ul class="list_date scroll">
                                                   	<c:forEach var="fMonth" begin="1" end="12" step="1">
                                                   		<li <c:if test="${fMonth == sMonth }">class="select"</c:if>><a href="#none" onclick="moveSchedule($('input[name=sYear]').val(),'${fMonth }');">${fMonth }월</a></li>
                                                   	</c:forEach>
                                                   </ul>
                                               </div>
                                           </div>
                                           <div class="layer_foot">
                                               <a href="#none">닫기</a>
                                           </div>
                                       </div>
                                   </div>
                               </div>
                               <div class="calLegend">
                                   <span class="workEnd"><em></em> 완료</span>
                                   <span class="work"><em></em> 할당</span>
                                   <span class="workDelay"><em></em> 지연</span>
                               </div>
                           </div>
                           <div class="talbeArea">
                               <table summary="주기별 업무 현황을 선택, 주기, 업무명, 업무시작일, 업무종료일, 담당자, 대무자, 처리상태 등의 항목으로 설명하고 있습니다.">
                                   <colgroup>
                                       <col width="14.2%">
                                       <col width="14.3%">
                                       <col width="14.3%">
                                       <col width="14.3%">
                                       <col width="14.3%">
                                       <col width="14.3%">
                                       <col width="14.3%">
                                   </colgroup>
                                   <caption></caption>
                                   <thead>
                                       <tr>
                                           <th class="sun" scope="col">일</th>
                                           <th scope="col">월</th>
                                           <th scope="col">화</th>
                                           <th scope="col">수</th>
                                           <th scope="col">목</th>
                                           <th scope="col">금</th>
                                           <th scope="col" class="sun last">토</th>
                                       </tr>
                                   </thead>
                                   <tbody id="tbody">
                                       <tr>
                                       	<c:set var="calendarLoop" value="1"/>
                                       	<c:set var="monthLoop" value="true"/>
                                       	<c:set var="prevLoop" value="${calendarList[0].fstart }"/>
                                       	<c:if test="${calendarList[0].uwyDayWek == 1 }">
                                       		<c:set var="prevLoop" value="8"/>
                                       	</c:if>
                                       	<c:set var="workIndex" value="0"/>
                                       	<c:forEach var="calendar" items="${calendarList}" varStatus="status">
                                        	<c:if test="${calendarLoop < 43 }">
                                         		<c:if test="${calendar.smonth >= sMonth && calendar.syear == sYear || calendar.smonth == sMonth - 1 && calendar.uwyDayWek < prevLoop || calendar.smonth == 1 && calendar.syear > sYear || calendar.smonth == 12 && calendar.syear < sYear && calendar.uwyDayWek < prevLoop }">
                                     				<c:if test="${calendar.uwyDayWek == 1 && calendarLoop > 1}">
                                     	<tr>
                                     				</c:if>
											<td class="<c:if test="${calendar.stoday eq 'Y' }">today </c:if><c:if test="${calendar.uwyWrkYn eq 'Y' }">sun </c:if><c:if test="${calendar.uwyDayWek == 7 }">sat last </c:if><c:if test="${calendar.smonth != sMonth }">disable</c:if>">
                                            	<p>${calendar.sday }<span>업무: <strong>0</strong></span></p>
                                           		<div class="workArea">
                                                   <div class="listPop">
                                                       <div class="listContainer">
                                                           <p class="listTitle"><strong>${calendar.syear }년 ${calendar.smonth }월 ${calendar.sday }일</strong> 업무리스트</p>
                                                           <ul class="workList">
                                                           	<c:set var="workIndex" value="0"/>
                                                           	<c:forEach var="result" items="${resultList}" varStatus="status">
                                                           		<c:if test="${result.utwEndDat eq calendar.uwyWrkYmd}">
                                                           			<li class="work<c:if test="${result.sta eq 'DL'}">Delay</c:if><c:if test="${result.sta eq 'CP'}">End</c:if>"><a href="#none" title="${result.utdDocNam }" onclick="workPopup('${result.utwWrkKey }','${result.utwTrcKey }');">${result.utdDocNam }</a></li>
                                                           			<c:set var="workIndex" value="${workIndex + 1 }"/>
                                                           		</c:if>
                                                           	</c:forEach>
                                                           </ul>
                                                           <a href="#none" class="btnMoreArea"><span>+ <c:if test="${workIndex - 2 gt 0 }">${workIndex - 2 }</c:if><c:if test="${workIndex - 2 le 0 }">0</c:if>개</span></a>
                                                           <button class="btnSmall close"><em>X</em></button>
                                                       </div>
                                                   </div>
                                               </div>
                                           </td>
                                           			<c:if test="${calendar.uwyDayWek == 7 }">
                                      	</tr>
                                           			</c:if>
                                         			<c:set var="calendarLoop" value="${calendarLoop + 1 }"/>
                                         		</c:if>
                                        	</c:if>
                                       	</c:forEach>
                                   </tbody>
                               </table>
                           </div>
                           <div class="topBtnArea">
			<ul class="btnList">
				<li>
					<button onclick="excelDown();"><span class="icoExl"></span>엑셀다운로드</button>
				</li>
				<li>
					<button onclick="reportDown();"><span class="icoDown"></span>PDF다운로드</button>
				</li>
			</ul>
		</div>
                       </div>
                   </div>
	<form id="workPopupForm" name="workPopupForm" action="/mwork/FM-MWORK_popup.do" method="post" target="workPopup">
		<input type="hidden" name="utwWrkKey" value="">
		<input type="hidden" name="utwTrcKey" value="">
	</form>
</body>
</html>