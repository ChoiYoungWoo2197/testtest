<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	function saveYmd(key,idx){
		var etc = $("#uwyEtc"+idx).val();

		$.ajax({
			url : "${pageContext.request.contextPath}/setup/FM-SETUP019_SAVE_YMD.do",
			type : "post",
			data : {"key" : key	,"etc" : etc},
			dataType : "json",
			success : function(data) {
				alert("공휴일이 지정되었습니다.");
				$('form[name=searchData]').attr('target','').attr('action','${pageContext.request.contextPath}/setup/FM-SETUP019.do').submit();
			},
			error : function() {
				alert('error');
			}
		});

	}

	function reYmd(key,week){
		var result = confirm("공휴일을 해제하시겠습니까?");

		if(result){
			if(week == '1' || week == '7'){
				alert("토/일요일은 공휴일 해제가 불가능합니다.");
				return;
			}

			$.ajax({
				url : "${pageContext.request.contextPath}/setup/FM-SETUP019_RE_YMD.do",
				type : "post",
				data : {"key" : key},
				dataType : "json",
				success : function(data) {
					alert("공휴일이 해제되었습니다.");
					$('form[name=searchData]').attr('target','').attr('action','${pageContext.request.contextPath}/setup/FM-SETUP019.do').submit();
				},
				error : function() {
					alert('error');
				}
			});
		}

	}

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
		$('form[name=searchData]').attr('target','').attr('action','${pageContext.request.contextPath}/setup/FM-SETUP019.do').submit();
	}

	function selectYear(year,_this){
		$('input[name=sYear]').val(year);
		$('.selectYear').find('li').each(function(){
			$(this).removeClass('select');
		});
		$(_this).parent('li').addClass('select');
	}

	function showDay(_this){
		var e=_this;
        $('.workArea').removeClass('view');
        $('.hideDay').hide();
        $('.shortText').show();
        e.find('.workArea').addClass('view');
		e.find('.hideDay').show();
		e.find('.shortText').hide();
	}

	function hideDay(_this){
		var e=_this.parent().parent().parent();
		e.find('.hideDay').hide();
		e.find('.shortText').show();
	}
</script>
		<c:import url="/titlebar.do" />
		 <form id="searchData" method="post" name="searchData">
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
	                    <button href="#none" class="btnOpen"><strong class="hidden">직접선택</strong><span></span></button>
	                    <a href="#none" class="btn_today" onclick="moveSchedule('','');">오늘</a>
	                    <div class="select_layer">
	                        <div class="inner_select_layer">
	                            <div class="layer_head">
	                                <strong class="tit_select">직접선택</strong>
	                            </div>
	                            <div class="layer_body">
	                                <div class="wrap_list">
	                                    <ul class="list_date scroll selectYear">
	                                    	<c:forEach var="fYear" items="${yearList }" varStatus="status">
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
						<span class="workEnd" style="padding:0 2px;"><em style="width:2px;height:2px;"></em> 업무일</span>
						<span class="work" style="padding:0 2px;"><em style="width:2px;height:2px;"></em> 공휴일</span>
						<span class="workDelay" style="padding:0 2px;"><em style="width:2px;height:2px;"></em> 법정공휴일</span>
					</div>
				</div>
				<div class="talbeArea">
					<table summary="계정설정 현황을 아이디, 서비스, 부서, 권한, 이름, 로그인 실패횟수, 상세, 사용자	잠금해제 등의 항목으로 설명하고있습니다.">
						<colgroup>
	                         <col width="14.2%">
	                         <col width="14.3%">
	                         <col width="14.3%">
	                         <col width="14.3%">
	                         <col width="14.3%">
	                         <col width="14.3%">
	                         <col width="14.3%">
	                    </colgroup>
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
						<tbody>
							<tr>
							<c:set var="calendarLoop" value="1"/>
							<c:set var="monthLoop" value="true"/>
							<c:set var="prevLoop" value="${resultList[0].fstart }"/>
							<c:if test="${resultList[0].uwyDayWek == 1 }">
							<c:set var="prevLoop" value="8"/>
							</c:if>
							<c:set var="workIndex" value="0"/>
							<c:forEach var="calendar" items="${resultList}" varStatus="status">
								<c:if test="${calendarLoop < 43 }">
									<c:if test="${calendar.smonth >= sMonth && calendar.syear == sYear || calendar.smonth == sMonth - 1 && calendar.uwyDayWek < prevLoop || calendar.smonth == 1 && calendar.syear > sYear || calendar.smonth == 12 && calendar.syear < sYear && calendar.uwyDayWek < prevLoop }">
										<c:if test="${calendar.uwyDayWek == 1 && calendarLoop > 1}">
							<tr>
										</c:if>
								<td <c:if test="${calendar.smonth == sMonth && (calendar.uwyDayWek != 7 && calendar.uwyDayWek != 1) }">onclick="showDay($(this));"</c:if> class="<c:if test="${calendar.stoday eq 'Y' }">today </c:if><c:if test="${calendar.uwyWrkYn eq 'Y' }">sun </c:if><c:if test="${calendar.uwyDayWek == 7 }">sat last </c:if><c:if test="${calendar.smonth != sMonth }">disable</c:if>">
									<p <c:if test="${calendar.smonth == sMonth && (calendar.uwyDayWek == 7 || calendar.uwyDayWek == 1) }">style="color:#e0002a;"</c:if><c:if test="${calendar.smonth == sMonth && (calendar.uwyWrkYn eq 'Y') }">style="color:#ef8009;"</c:if>>${calendar.sday }</p>
										<div class="workArea">
											<div class="listPop">
    											<div class="listContainer">
    								<c:if test="${calendar.smonth == sMonth }">
        											<p class="listTitle"><strong>${calendar.syear }년 ${calendar.smonth }월 ${calendar.sday }일</strong></p>
													<ul class="workList" style="overflow-y:hidden;">
														<li class="workEnd">
									<c:if test="${calendar.uwyWrkYn == 'Y'}">
															<a class="shortText" title="<c:out value="${calendar.uwyEtc}" />"><c:out value="${calendar.uwyEtc}" /></a>
														<div class="hideDay" style="display: none;">
															<textarea id="uwyEtc${status.index }" name="uwyEtc${status.index }"class="inputText" style="width:235px; height:58px; margin:0 0 10px 0;" maxlength="500"><c:out value="${calendar.uwyEtc}" /></textarea>
															<button onclick="event.cancelBubble=true;reYmd('${calendar.uwyWrkYmd}','${calendar.uwyDayWek}'); return false;" style="float: right;">공휴일 해제</button>
														</div>
									</c:if>
									<c:if test="${calendar.uwyWrkYn == 'N'}">
									<div class="hideDay" style="display: none;">
										<textarea onclick="event.cancelBubble=true;" id="uwyEtc${status.index }" name="uwyEtc${status.index }"class="inputText" style="width:235px; height:58px; margin:0 0 10px 0;"></textarea>
										<button onclick="event.cancelBubble=true;saveYmd('${calendar.uwyWrkYmd}','${status.index }'); return false;" style="float: right;">공휴일 지정</button>
									</div>
									</c:if>
														</li>
													</ul>
									<c:if test="${calendar.uwyWrkYn == 'N'}">
									</c:if>
            										<button class="btnSmall close" onclick="event.cancelBubble=true;hideDay($(this));"><em>X</em></button>
            						</c:if>
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
			</div>
		</div>