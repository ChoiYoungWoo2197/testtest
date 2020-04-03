<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/contents.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>

<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>SK broadband ISAMS</title>
	<script type="text/javascript" src="/common/js/Chart.min.js"></script>
	<script type="text/javascript" src="/common/js/mainChart.js?v=17112200021"></script>
<script type="text/javascript">
	$(window).load(function(){
		<c:if test="${listPopup.size()>0}">
			<c:set var="popup_key" value="" />
			<c:forEach items="${listPopup}" var="popup">
				<c:set var="popup_key" value="popup_${popup.popupId}_${popup.brdKey}" />
		if(getCookie("${popup_key}")!="N"){
			${popup_key} = window.open("/popup/POPUP.do?pid=${popup.popupId}&bk=${popup.brdKey}","${popup_key}","menubar=no, scrollbars=no, location=no, status=no, resizeable=no, width=${popup.width}, height=${popup.height}, top=${popup.top}, left=${popup.left}");
			try { ${popup_key}.focus(); } catch(e){ alert( "팝업 차단 설정을 해제해주세요." ); }
			${popup_key}.focus();
		}
			</c:forEach>
		</c:if>

		<c:if test="${authKey ne 'P'}">
		totalDashboard();
		$("#standard").change(function() {
			totalDashboard();
		})
		$.post("getComplianceAndDocCount.do", function(json) {
			var data = json.result;
			$("#totalCmp").text(data.totalCmp);
			$("#totalDoc").text(data.totalDoc);
		}, "json");

		mainProgressBarChart(3, "/state/getWorkMonthStatistics.do", null, true);
		</c:if>

		mainProgressPieChart(4, "getProgressWorkCount.do", null);

		getBottomList();
		detailWork();
		detailRsk003();
		detailRsk010();
		detailInspt002();

	});

	function getCookie(cname) {
		var name = cname + "=";
		var ca = document.cookie.split(';');
		for (var i = 0; i < ca.length; i++) {
			var c = ca[i];
			while (c.charAt(0) == ' ') c = c.substring(1);
			if (c.indexOf(name) == 0) return c.substring(name.length, c.length);
		}
		return "";
	}

	<c:if test="${authKey ne 'P'}">
	function totalDashboard() {
		var std =  $("#standard").val();
		mainProgressPieChart(1, "getTotalProgressWorkCount.do", { standard: std });
		mainProgressRadarChart(2, "/state/getWorkCtrStatistics.do", { standard: std }, true);
		$.post("getComplianceSummary.do", { standard: std }, function(json) {
 			var data = json.result;
 			$("#cmpCnt1").text(data.cmpCnt1);
 			$("#cmpCnt2").text(data.cmpCnt2);
 			$("#cmpCnt3").text(data.cmpCnt3);
 		}, "json");
	}
	</c:if>

	function detailWork(){
		$.ajax({
			url		: "/FM-MAIN_DetailWorkList.do",
			type		: "post",
			data		: {},
			dataType	: "json",
			success	: function(data){
				var list = "";
				for(var i = 0;i<data.nowork.length;i++){
					list += "<li class='item'><a href='javascript:workPopup("+data.nowork[i].utwWrkKey+","+data.nowork[i].utwTrcKey+")'><em>"+data.nowork[i].utdDocNam+"</em><span>[할당]"+data.nowork[i].utwStrDat+" [종료]"+data.nowork[i].utwEndDat+"</span></a></li>";
				}
				$("#noworkList").html(list);
				list = "";
				for(var i = 0;i<data.delay.length;i++){
					list += "<li class='item'><a href='javascript:workPopup("+data.delay[i].utwWrkKey+","+data.delay[i].utwTrcKey+")'><em>"+data.delay[i].utdDocNam+"</em><span>[할당]"+data.delay[i].utwStrDat+" [종료]"+data.delay[i].utwEndDat+"</span></a></li>";
				}
				$("#delayList").html(list);
				list = "";
				for(var i = 0;i<data.compwork.length;i++){
					list += "<li class='item'><a href='javascript:workPopup("+data.compwork[i].utwWrkKey+","+data.compwork[i].utwTrcKey+")'><em>"+data.compwork[i].utdDocNam+"</em><span>[할당]"+data.compwork[i].utwStrDat+" [종료]"+data.compwork[i].utwEndDat+"</span></a></li>";
				}
				$("#completeList").html(list);
			},
			error : function(){
				alert('error');
			}
		});
	}


	function goBoard(key,gbn){
		openPostUrl("/board/FM-BOARD001_V.do", {selectedId: key});
	}

	function getBottomList(){
		$('.noticeUl').show();
		$.ajax({
			url		: "/FM-MAIN_getBottomList.do",
			type		: "post",
			data		: {},
			dataType	: "json",
			success	: function(data){
				var list = "";
				for(var i = 0;i<data.noti.length;i++){
					list += "<li class='item'><a href='javascript:goBoard("+data.noti[i].ubmBrdKey+","+data.noti[i].ubmBrdGbn+");'><em>"+data.noti[i].ubmBrdTle+"</em><span>"+data.noti[i].ubmRgtMdh+"</span></a></li>"
				}
				$("#notiList").html(list);
				list = "";
				for(var i = 0;i<data.alarm.length;i++){
					list += "<li class='item'><a href='#none'><em>"+data.alarm[i].ueaSndTit+"</em><span>"+data.alarm[i].ueaSndDat+"</span></a></li>"
				}
				$("#alarmList").html(list);
			}
		});
	}

	function passwordChangPopup() {
		window.open("/setup/FM-SETUP007_pwd_popup.do","passwordChangPopup","width=400, height=300, menubar=no, location=no, status=no");
	}

	function detailRsk003() {
		$.post("/FM-RSIKM003_MAIN.do", function(data) {
			var htmlStr = "";
			$.each(data.rsklist, function(key, val) {
				htmlStr += "<li class='item'><a href=\"javascript:editRskPop003('" + val.ursRskKey + "')\">"
						+ "<em>"+ val.usoCocNam +"</em><span>"+ val.ursStaNam +"</span></a></li>";
			});
			$("#rsk003").html(htmlStr);
		}, "json");
	}

	function detailRsk010() {
		$.post("/FM-RSIKM010_MAIN.do", function(data) {
			var htmlStr = "";
			$.each(data.rsklist, function(key, val) {
				htmlStr += "<li class='item'><a href=\"javascript:editRskPop010('" + val.ursRskKey + "','" + val.urrRskKey + "')\">"
						+ "<em>"+ val.usoCocNam +"</em><span>"+ val.ursStaNam +"</span></a></li>";
			});
			$("#rsk010").html(htmlStr);
		}, "json");
	}

	function detailInspt002() {
		$.post("/FM-INSPT002_MAIN.do", function(data) {
			var htmlStr = "";
			$.each(data.rsklist, function(key, val) {
				htmlStr += "<li class='item'><a href=\"javascript:editInsptPop002('"+ val.ufmFltKey + "','" + val.ufpMapKey + "')\">"
						+ "<em>"+ val.ufmFltDes +"</em><span class='usrNam'>"+ val.uumUsrNam +"</span><span>"+ val.ufpStaNam +"</span></a></li>";
			});
			$("#inspt002").html(htmlStr);
			<c:if test="${authKey eq 'P'}">$(".usrNam").hide()</c:if>
		}, "json");
	}


	function editRskPop003(ursRskKey) {
		openPostPopup("/riskm/FM-RISKM003_popup.do", 730, 800, { ursRskKey: ursRskKey });
	}

	function editRskPop010(ursRskKey, urrRskKey) {
		openPostPopup("/riskm/FM-RISKM010_popup.do", 730, 800, { ursRskKey: ursRskKey, urrRskKey: urrRskKey });
	}

	function editInsptPop002(ufmFltKey, ufpMapKey) {
		openPostPopup("/inspt/FM-INSPT002_mng_popup.do", 730, 800, { ufmFltKey: ufmFltKey, ufpMapKey: ufpMapKey });
	}
</script>
</head>
<body>
	<div class="upArea" id="chartContainer4" style="height: 360px;">
		<div class="header">
			<div class="conttitle">개인 현황</div>
           	<div class="labelBox">
               	<div class="label">주기 업무 수</div>
               	<div class="data period"></div>
			</div>
			<div class="labelBox">
               	<div class="label">비주기 업무 수</div>
              	<div class="data nonePeriod"></div>
            </div>
            <div class="clearfix"></div>
		</div>
		<div class="graph" style="height: 320px;">
			<h2>정보보호 업무 전체 진행현황</h2>
			<canvas class="canvasChart" id="chart4" height="185" style="height: 212px;"></canvas>
			<div class="totlaWork">
				<em>총업무</em> <strong class="total"></strong>건
			</div>
			<ul class="chartLabel">
                <li class="comp"><span class="icon"></span> 완료 : <span class="compCnt">&nbsp;</span>건</li>
                <li class="delay"><span class="icon"></span> 지연 : <span class="delayCnt">&nbsp;</span>건</li>
                <li class="ready"><span class="icon"></span> 미진행 : <span class="readyCnt">&nbsp;</span>건</li>
            </ul>
		</div>
		<div class="detailWork">
			<dl class="detailList sel">
				<dt class="category01">
					<a href="#none"><span><em>미진행</em><strong class="readyCnt"></strong>건</span></a>
				</dt>
				<dd>
					<h3>미진행 업무</h3>
					<ul class="list" id="noworkList">

					</ul>
					<a href="/mwork/FM-MWORK006.do#R" class="btnMore">더보기 <span>></span></a>
				</dd>
			</dl>
			<dl class="detailList">
				<dt class="category02">
					<a href="#none">
						<span>
							<em>지연</em>
							<strong class="delayCnt"></strong>건
						</span>
					</a>
				</dt>
				<dd>
					<h3>지연 업무</h3>
					<ul class="list" id="delayList">

					</ul>
					<a href="/mwork/FM-MWORK006.do#D" class="btnMore">더보기 <span>></span></a>
				</dd>
			</dl>
			<dl class="detailList">
				<dt class="category03">
					<a href="#none">
						<span>
							<em>완료</em>
							<strong class="compCnt"></strong>건
						</span>
					</a>
				</dt>
				<dd>
					<h3>완료 업무</h3>
					<ul class="list" id="completeList">

					</ul>
					<a href="/mwork/FM-MWORK006.do#C" class="btnMore">더보기 <span>></span></a>
				</dd>
			</dl>
		</div>
	</div>
	<div class="downArea">
		<div class="boardArea" style="margin-right: 40px;">
		    <h2><span>기술적 위험조치 관리</span></h2>
		    <ul class="list" id="rsk003">
		    </ul>
		    <a href="/riskm/FM-RISKM003.do" class="btnMore">더보기 <span>></span></a>
		</div>

		<div class="boardArea" style="margin-right: 40px;">
		    <h2><span>관리적 위험조치 관리</span></h2>
		    <ul class="list" id="rsk010">
		    </ul>
		    <a href="/riskm/FM-RISKM010.do" class="btnMore">더보기 <span>></span></a>
		</div>

		<div class="boardArea">
		    <h2><span>결함 관리</span></h2>
		    <ul class="list" id="inspt002">
		    </ul>
		    <a href="/inspt/FM-INSPT002.do" class="btnMore">더보기 <span>></span></a>
		</div>
		<div class="clearfix"></div>
	</div>

	<c:if test="${authKey ne 'P'}">
	<div class="upArea" id="chartContainer1" style="height: 420px;">
		<div class="header">
			<div class="conttitle">종합 현황</div>
 			<div class="stnd">
 				<sb:select name="standard" type="" code="STND" />
 			</div>
 			<div class="labelBox">
               	<div class="label">전체 점검 항목 수</div>
               	<div class="data" id="totalCmp"></div>
			</div>
			<div class="labelBox">
               	<div class="label">전체 보안 업무 수</div>
              	<div class="data" id="totalDoc"></div>
            </div>
			<div class="clearfix"></div>
		</div>
		<div class="graph">
			<h2>정보보호 업무 전체 진행현황</h2>
			<canvas class="canvasChart" id="chart1" height="190" style="height: 218px; margin-top: 30px;"></canvas>
			<div class="totlaWork" style="top: 138px;">
				<em>총업무</em> <strong class="total">&nbsp;</strong>건
			</div>
			<ul class="chartLabel" style="margin-top: 35px;">
                <li class="comp"><span class="icon"></span> 완료 : <span class="compCnt">&nbsp;</span>건</li>
                <li class="delay"><span class="icon"></span> 지연 : <span class="delayCnt">&nbsp;</span>건</li>
                <li class="ready"><span class="icon"></span> 미진행 : <span class="readyCnt">&nbsp;</span>건</li>
            </ul>
            <div class="clearfix"></div>
            <!-- <div class="bottom">
	            <div class="labelBox">
	               	<div class="label">비주기</div>
	              	<div class="data nonePeriod"></div>
	            </div>
	            <div class="labelBox">
	               	<div class="label">주기</div>
	              	<div class="data period"></div>
	            </div>
            </div> -->
		</div>

		<div class="graph">
			<h2>분야별 진행현황</h2>
			<canvas class="canvasChart" id="chart2" height="270"></canvas>
		</div>
		<div  class="graph lastPos">
			<h2>컴플라이언스 현황</h2>
			<div class="labelBox">
               	<div class="label">통제 분야 수</div>
              	<div class="data" id="cmpCnt1"></div>
            </div>
            <div class="labelBox">
               	<div class="label">통제 항목 수</div>
              	<div class="data" id="cmpCnt2"></div>
            </div>
            <div class="labelBox">
               	<div class="label">점검 항목 수</div>
              	<div class="data" id="cmpCnt3"></div>
            </div>

            <div class="clearfix"></div>
            <h2 style="margin-top: 5px;">관련 보안 업무 수</h2>
            <div class="labelBox">
               	<div class="label">주기성 업무 수</div>
              	<div class="data period"></div>
            </div>
           	<div class="labelBox">
               	<div class="label">비주기 업무 수</div>
              	<div class="data nonePeriod"></div>
            </div>
            <div class="clearfix"></div>

    	</div>
		<div class="clearfix"></div>
	</div>

	<div class="upArea" style="height: 360px;">
		<div class="header">
			<div class="conttitle">월별 업무 진행현황</div>
			<div class="clearfix"></div>
		</div>
		<div>
			<div style="padding: 20px;">
				<canvas class="canvasChart" id="chart3" width="1018" height="300"></canvas>
			</div>
		</div>
	</div>
	</c:if>

	<form name="toBoard" id="toBoard" method="post">
		<input type="hidden" id="serviceName" name="serviceName" />
		<input type="hidden" id="brdGbn" name="brdGbn">
		<input type="hidden" id="brdKey" name="brdKey">
	</form>
	<form id="workPopupForm" name="workPopupForm" action="/mwork/FM-MWORK_popup.do" method="post" target="workPopup">
		<input type="hidden" id="wrkKey" name="wrkKey" value="">
		<input type="hidden" id="docKey" name="docKey" value="">
	</form>
</body>
</html>