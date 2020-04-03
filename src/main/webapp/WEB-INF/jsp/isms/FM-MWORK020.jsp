<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
<%@ include file="/WEB-INF/include/contents.jsp"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<link rel="stylesheet" type="text/css" href="/common/css/reset.css"/>
<link rel="stylesheet" type="text/css" href="/common/css/common.css"/>
<link rel="stylesheet" type="text/css" href="/common/css/sub.css"/>
<link rel="stylesheet" type="text/css" href="/common/css/pop.css"/>
<script type="text/javascript" src="/common/js/jquery.js"></script>
<script type="text/javascript" src="/common/js/ismsUI.js"></script>
<script type="text/javascript">
var man_std = <%= C_SES_MAN_STD %>+"" ;
var man_end = <%= C_SES_MAN_END %>+"" ;
var deptart = '';
var standard = "ALL";
var isCheck = false;

$(document).ready(function() {
	setAuth();
	getWorkDetailChart();
});

function setAuth(){
	if('<%=C_SES_USER_AUTH %>' == 'V'){
		deptart = '<%=C_SES_DEPT_CD %>';
	}
}

function getWorkDetailChart() {
	$.ajax({
		url		: "${pageContext.request.contextPath}/state/getWorkServiceStatistics.do",
		type		: "post",
		data		: { service:$('#service').val() },
		dataType	: "json",
		success	: function(data){
			var _div = $("#grid_w");
			var _divF = $("#grid_wf");
			var list = "";
			_div.empty();
			_divF.empty();

			$.each(data.result, function(k, v) {
        		var scale = v.titleName;
        		var code = v.titleCode;
        		var all = v.total;
        		var comp = v.comp + " ("+v.perComp+" %)";
        		var delay = v.delay + " ("+v.perDelay+" %)";
        		var nowork = v.ready + " ("+v.perReady+" %)";

        		var list = "<tr><td><a href='#none' onclick=\"getWorkSubDetailChart('"+ code +"')\">"+ scale +"</a></td>"
						 + "<td><a href='#none' onclick=\"getWorkDetailPop('"+ code +"')\">"+ all +"</a></td>"
   			    		 + "<td><a href='#none' onclick=\"getWorkDetailPop('"+ code +"','','',1)\">"+ comp +"</a></td>"
   			    		 + "<td class='colorPoint'><a href='#none' onclick=\"getWorkDetailPop('"+ code +"','','',2)\">"+ delay +"</a></td>"
   			    		 + "<td class='last'><a href='#none' onclick=\"getWorkDetailPop('"+ code +"','','',3)\">"+ nowork +"</a></td></tr>";
   			    code == 'total' ? _divF.append(list) :_div.append(list);
        	});
        	getWorkSubDetailChart();
		}
	});
}

function getWorkSubDetailChart(service) {
	if (service == "total") {
		service = "";
	}
	$('#service').val(service);
	$.ajax({
		url		: "${pageContext.request.contextPath}/state/getWorkDepStatistics.do",
		type		: "post",
		data		: { hasWork: true, service: service },
		dataType	: "json",
		success	: function(data){
			var _div = $("#grid_w02");
			var _div_2 = $("#grid_w02_1");
			_div.empty();
			_div_2.empty();

        	$.each(data.result, function(k, v) {
        		var scale = v.titleName;
        		var code = v.titleCode;
        		var all = v.total;
        		var comp = v.comp + " ("+v.perComp+" %)";
        		var delay = v.delay + " ("+v.perDelay+" %)";
        		var nowork = v.ready + " ("+v.perReady+" %)";

        		var list = "<li><span class='wType03' style='width:120px'><a href='#none' onclick=\"getWorkUserDetailChart('"+ code +"')\">"+ scale +"</a></span>"
						 + "<span style='width: 60px;'><a href='#none' onclick=\"getWorkDetailPop('','"+ code +"')\">" + all + "</a></span>"
						 + "<span style='width: 80px;'><a href='#none' onclick=\"getWorkDetailPop('','"+ code +"','',1)\">" + comp + "</a></span>"
						 + "<span style='width: 80px;'><a href='#none' class='colorPoint' onclick=\"getWorkDetailPop('','"+ code +"','',2)\">" + delay + "</a></span>"
						 + "<span class='last' style='width: 80px;'><a href='#none' onclick=\"getWorkDetailPop('','"+ code +"','',3)\">" + nowork + "</a></span></li>";
    			code == 'total' ? _div_2.append(list) :_div.append(list);
        	});
        	getWorkUserDetailChart();
		}
	});
}

function getWorkUserDetailChart(dep){
	if (dep == "total") {
		dep = "";
	}
	$('#dept').val(dep);
	$.ajax({
		url		: "${pageContext.request.contextPath}/state/getWorkUserStatistics.do",
		type		: "post",
		data		: {depCode: dep},
		dataType	: "json",
		success	: function(data){
			var _div = $("#grid_w03");
			var _div_2 = $("#grid_w03_1");
			_div.empty();
			_div_2.empty();

			$.each(data.result, function(k, v) {
        		var scale = v.titleName;
        		var code = v.titleCode;
        		var all = v.total;
        		var comp = v.comp + " ("+v.perComp+" %)";
        		var delay = v.delay + " ("+v.perDelay+" %)";
        		var nowork = v.ready + " ("+v.perReady+" %)";

        		var list = "<li><span class='wType03' style='width:120px'><a href='#none' onclick=\"getWorkDetailPop('','','"+ code +"')\">"+ scale +"</a></span>"
						 + "<span style='width: 60px;'><a href='#none' onclick=\"getWorkDetailPop('','','"+ code +"')\">" + all + "</a></span>"
						 + "<span style='width: 80px;'><a href='#none' onclick=\"getWorkDetailPop('','','"+ code +"',1)\">" + comp + "</a></span>"
						 + "<span style='width: 80px;'><a href='#none' class='colorPoint' onclick=\"getWorkDetailPop('','','"+ code +"',2)\">" + delay + "</a></span>"
						 + "<span class='last' style='width: 80px;'><a href='#none' onclick=\"getWorkDetailPop('','','"+ code +"',3)\">" + nowork + "</a></span></li>";
				code == 'total' ? _div_2.append(list) :_div.append(list);
			});
		}
	});
}

function getWorkDetailPop(service, dept, user, sta) {
	$("#link_service").val(service);
	$("#link_dept").val(dept);
	$("#link_id").val(user);
	$("#link_sta").val(sta);
	//$("#link_std").val(standard);
	window.open('', 'dashBoardPopup', 'height=700px,width=1100px,scrollbars=yes,resizable=yes');
	document.linkForm.submit();
}

function chartPopup(sMode){
	window.open("","chartPopup","width=650, height=650, menubar=no, location=no, status=no,scrollbars=yes");
	$('input[name=sMode]').val(sMode);
	$('input[name=service]').val($('select[name=service]').val());
	$('form[name=chartPopupForm]').attr('action','${pageContext.request.contextPath}/chart/FM-STATE002_popup.do').submit();
}
</script>
</head>
<body>
	<c:import url="/titlebar.do" />
	<div class="pointTable">
		<div class="title">서비스별</div>
		<div class="talbeArea">
			<table summary="통제항목별 진행현황을 통제항목, 전체, 완료, 비연, 미진행, 금일완료 등으로 확인 할 수 있습니다.">
				<colgroup>
					<col width="20%">
					<col width="*">
					<col width="20%">
					<col width="20%">
					<col width="20%">
				</colgroup>
				<caption>서비스별 진행현황</caption>
				<thead>
                    <tr>
                        <th>서비스명</th>
                        <th>전체</th>
                        <th>완료</th>
                        <th>지연</th>
                        <th class="last">미진행</th>
                    </tr>
				</thead>
                <tbody id="grid_w">
                </tbody>
				<tfoot id="grid_wf">
				</tfoot>
			</table>
			<div class="topBtnArea">
                <ul class="btnList">
                    <!-- <li>
                        <sb:select name="service" type="S" allText="서비스 전체"/>
                    </li>
                    <li><button class="btnOk" onclick="getWorkDetailChart();">확인</button></li>
                    <li>
						<button onclick="chartPopup('');"><span class="icoList"></span>차트</button>
					</li> -->
                </ul>
            </div>
		</div>
	</div>
	<div class="contents">
	    <div class="listMoveArea dualArea">
	        <div class="listArea">
	            <div class="title">부서별</div>
	            <div class="talbeArea">
	                <dl class="workingArea">
	                    <dt>
	                        <span class="wType03" style="width:120px">부서</span><span style="width: 60px;">전체</span><span style="width: 80px;">완료</span><span style="width: 80px;">지연</span><span class="last" style="width: 80px;">미진행</span>
	                    </dt>
	                    <dd class="listArea">
	                        <ul class="listUl" id="grid_w02">
	                        </ul>
	                    </dd>
	                    <dd class="resume" id="grid_w02_1">
	                    </dd>
	                </dl>
					<div class="topBtnArea">
						<ul class="btnList">
							<li>
								<button onclick="chartPopup('DEPT');"><span class="icoList"></span>차트</button>
							</li>
						</ul>
					</div>
	            </div>
	        </div>
	        <div class="listArea">
	            <div class="title">담당자별</div>
	            <div class="talbeArea">
	                <dl class="workingArea">
	                    <dt>
	                        <span class="wType03" style="width:120px">담당자</span><span style="width: 60px;">전체</span><span style="width: 80px;">완료</span><span style="width: 80px;">지연</span><span class="last" style="width: 80px;">미진행</span>
	                    </dt>
	                    <dd class="listArea">
	                        <ul class="listUl" id="grid_w03">
	                        </ul>
	                    </dd>
	                    <dd class="resume" id="grid_w03_1">
	                    </dd>
	                </dl>
	                <div class="topBtnArea">
	                    <ul class="btnList">
	                        <li>
	                        	<button onclick="chartPopup('USER');"><span class="icoList"></span>차트</button>
                        	</li>
	                    </ul>
	                </div>
	            </div>
	        </div>
	    </div>
	</div>
	<form id="linkForm" name="linkForm" method="post" action="/state/FM-STATE002_popup.do" onsubmit="return false;" target="dashBoardPopup">
		<input type="hidden" id="link_service" name="link_service">
		<input type="hidden" id="link_dept" name="link_dept">
		<input type="hidden" id="link_id" name="link_id">
		<input type="hidden" id="link_std" name="link_std">
		<input type="hidden" id="link_sta" name="link_sta">
	</form>
	<form id="chartPopupForm" name="chartPopupForm" method="post" target="chartPopup">
		<input type="hidden" id="auth" name="auth" value="<%=C_SES_USER_AUTH %>"/>
		<input type="hidden" id="dept" name="dept" value=""/>
		<input type="hidden" id="axis" name="axis" value="<%=C_SES_MAN_CYL %>"/>
		<input type="hidden" id="scale" name="scale" value="null"/>
		<input type="hidden" id="sMode" name="sMode" value=""/>
		<input type="hidden" id="service" name="service" value=""/>
	</form>
</body>
</html>