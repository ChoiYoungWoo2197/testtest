<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<!--[if lte IE 8]>
	<script type="text/javascript" src="/common/js/excanvas.js"></script>
	<script type="text/javascript" src="/common/js/Chart-1.1.1.min.js"></script>
	<script type="text/javascript" src="/common/js/Chart.StackedBar.js"></script>
	<script type="text/javascript" src="/common/js/stateChart-ie8.js"></script>
<![endif]-->
<!--[if gt IE 8]> <!-- -->
	<script type="text/javascript" src="/common/js/Chart.min.js"></script>
	<script type="text/javascript" src="/common/js/stateChart.js"></script>
<!-- <![endif]-->
<script type="text/javascript">
var chartCode2;
var chartCode3;
$(window).load(function() {
	$("#chartTbl1").on("click", "td", function() {
		if (!$(this).attr("id")) {
			return false;
		}
		chartCode2 = $(this).attr("id");
		$(".chartTitle2").text($(this).text());
		$("#listTab2 li").eq(0).click();
		$("#chartContainer3").hide();
	});
	$("#chartTbl2").on("click", "td", function() {
		if (!$(this).attr("id")) {
			return false;
		}
		chartCode3 = $(this).attr("id");
		$(".chartTitle3").text($(this).text());
		$("#listTab3 li").eq(0).click();
	});

	$("#listTab1 li").click(function() {
		var val = clickListTab(this);
		progressBarChart(1, "getWorkDepStatistics.do", val, true,  { depLevel: 1 });
	});
	$("#listTab2 li").click(function() {
		var val = clickListTab(this);
		progressBarChart(2, "getWorkDepStatistics.do", val, true, { depLevel: 2, depCode: chartCode2 });
	});
	$("#listTab3 li").click(function() {
		var val = clickListTab(this);
		progressBarChart(3, "getWorkDepStatistics.do", val, false, { depLevel: 3, depCode: chartCode3 });
	});

	$("#listTab1 li").eq(0).click();

	/*$("#chartSAC").click(function(){
		$("#chartSAC").append("<form action=\"${pageContext.request.contextPath}/excelNew/excelReportSAC.do\"></form>");
		$("#chartSAC form").submit().remove();
	});
	$("#chartSAS").click(function(){
		$("#chartSAS").append("<form action=\"${pageContext.request.contextPath}/excelNew/excelReportSAS.do\"></form>");
		$("#chartSAS form").submit().remove();
	});*/
});
</script>
</head>
<body>
	<c:import url="/titlebar.do" />
	<div class="chartContainer" id="chartContainer1">
		<div class="tabArea" style="margin-bottom: 10px;">
		    <ul id="listTab1" class="list2Tab">
		        <li class="sel"><a href="javascript:;">본부별 진행현황 (건)</a></li>
		        <li><a href="javascript:;">본부별 진행현황 (%)</a></li>
		    </ul>
		</div>
		<canvas class="canvasChart" id="chart1"></canvas>
		<div class="chartLegend" id="legend1"></div>
		<div class="talbeArea">
			<table id="chartTbl1" summary="통제항목별 진행현황을 통제항목, 전체, 완료, 지연, 미진행 등을 확인 할 수 있습니다.">
				<colgroup>
					<col width="">
					<col width="12%">
					<col width="12%">
					<col width="12%">
					<col width="12%">
					<col width="12%">
					<col width="12%">
					<col width="12%">
				</colgroup>
				<caption>본부별</caption>
				<thead>
	                   <tr>
	                       <th>본부별</th>
	                       <th>전체</th>
	                       <th>완료</th>
	                       <th>지연</th>
	                       <th>미진행</th>
	                       <th>진행률(%)</th>
	                       <th>지연율(%)</th>
	                       <th class="last">미진행율(%)</th>
	                   </tr>
				</thead>
	            <tbody>
	            </tbody>
	            <tfoot>
	            </tfoot>
			</table>
		</div>
		<div class="comment">
		※ 본부명을 클릭하면 해당 본부의 그룹,담당 정보를 확인 할 수 있습니다.
		</div>
	</div>

	<div class="chartContainer" id="chartContainer2" style="display: none">
		<div class="tabArea" style="margin-bottom: 10px;">
		    <ul id="listTab2" class="list2Tab">
		        <li class="sel"><a href="javascript:;"><span class="chartTitle2"></span> 진행현황 (건)</a></li>
		        <li><a href="javascript:;"><span class="chartTitle2"></span> 진행현황 (%)</a></li>
		    </ul>
		</div>
		<canvas class="canvasChart" id="chart2"></canvas>
		<div class="talbeArea">
			<table id="chartTbl2" summary="통제항목별 진행현황을 통제항목, 전체, 완료, 지연, 미진행 등을 확인 할 수 있습니다.">
				<colgroup>
					<col width="">
					<col width="12%">
					<col width="12%">
					<col width="12%">
					<col width="12%">
					<col width="12%">
					<col width="12%">
					<col width="12%">
				</colgroup>
				<caption>그룹,담당별</caption>
				<thead>
	                   <tr>
	                       <th>그룹,담당별</th>
	                       <th>전체</th>
	                       <th>완료</th>
	                       <th>지연</th>
	                       <th>미진행</th>
	                       <th>진행률(%)</th>
	                       <th>지연율(%)</th>
	                       <th class="last">미진행율(%)</th>
	                   </tr>
				</thead>
	            <tbody>
	            </tbody>
	            <tfoot>
            	</tfoot>
			</table>
		</div>
		<div class="comment">
			※ 그룹,담당명을 클릭하면 해당 그룹,담당의 부서 정보를 확인 할 수 있습니다.
		</div>
	</div>

	<div class="chartContainer" id="chartContainer3" style="display: none">
		<div class="tabArea" style="margin-bottom: 10px;">
		    <ul id="listTab3" class="list2Tab">
		        <li class="sel"><a href="javascript:;"><span class="chartTitle3"></span> 진행현황 (건)</a></li>
		        <li><a href="javascript:;"><span class="chartTitle3"></span> 진행현황 (%)</a></li>
		    </ul>
		</div>
		<canvas class="canvasChart" id="chart3"></canvas>
		<div class="talbeArea">
			<table id="chartTbl3" summary="통제항목별 진행현황을 통제항목, 전체, 완료, 지연, 미진행 등을 확인 할 수 있습니다.">
				<colgroup>
					<col width="">
					<col width="12%">
					<col width="12%">
					<col width="12%">
					<col width="12%">
					<col width="12%">
					<col width="12%">
					<col width="12%">
				</colgroup>
				<caption>팀별</caption>
				<thead>
	                   <tr>
	                       <th>팀별</th>
	                       <th>전체</th>
	                       <th>완료</th>
	                       <th>지연</th>
	                       <th>미진행</th>
	                       <th>진행률(%)</th>
	                       <th>지연율(%)</th>
	                       <th class="last">미진행율(%)</th>
	                   </tr>
				</thead>
	            <tbody>
	            </tbody>
	            <tfoot>
            	</tfoot>
			</table>
		</div>
	</div>
</body>
</html>