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
$(window).load(function() {
	$("#chartTbl1").on("click", "td", function() {
		var code = $(this).attr("id");
		var title = $(this).text();
		if (code) {
			progressBarChartDynamic(2, "getAssetDepStatisticsOfAsset.do", title + " 자산내역", true,  { service: code }, true);
			progressTableDataDynamic(2, "getAssetDepStatistics.do", "부서", false, { service: code });
		}
	});

	// progressBarChartDynamic(idx, url, title, toLink, params, removeTotal)
	progressBarChartDynamic(1, "getAssetServiceStatisticsOfAsset.do", "서비스별 자산내역", true, null, true);
 	// progressTableDataDynamic(idx, url, th, toLink, params)
	progressTableDataDynamic(1, "getAssetServiceStatistics.do", "서비스", true);
});
</script>
<style>
.talbeArea th {min-width: 80px;}
</style>
</head>
<body>
	<c:import url="/titlebar.do" />
	<div class="chartContainer" id="chartContainer1">
		<canvas class="canvasChart" id="chart1"></canvas>
		<div class="talbeArea">
			<table id="chartTbl1" summary="서비스별 자산현황을 확인 할 수 있습니다.">
				<colgroup>
					<col width="15%">
				</colgroup>
				<caption>서비스별</caption>
				<thead>
				</thead>
	            <tbody>
	            </tbody>
	            <tfoot>
	            </tfoot>
			</table>
		</div>
		<div class="comment">
			※ 서비스명을 클릭하면 해당 서비스의 부서 정보를 확인 할 수 있습니다.
		</div>
	</div>

	<div class="chartContainer" id="chartContainer2" style="display: none">
		<canvas class="canvasChart" id="chart2"></canvas>
		<div class="talbeArea">
			<table id="chartTbl2" summary="부서별 자신현황을 확인 할 수 있습니다.">
				<colgroup>
					<col width="15%">
				</colgroup>
				<caption>부서별</caption>
				<thead>
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