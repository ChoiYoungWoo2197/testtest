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
	progressBarChart(1, "getRiskCatStatistics.do", 0, true);
});

function progressBarChart(idx, url, chartType, toLink, params, removeTotal) {
	var chartData = Array();

	// get Json data
	$.getJSON(url, params, function(json) {
		jsonData[idx] = json.result;
	});
	// remove last(total) element
	if (removeTotal) {
		jsonData[idx].pop();
	}

	if ($.isEmptyObject(jsonData[idx])) {
		$("#chartContainer" + idx).hide();
		return false;
	}

	// set table data
	progressTableData(idx, toLink);
	$("#chartContainer" + idx).show();

	chartData[0] = {
		labels : $.map(jsonData[idx], function(el) { return el.titleName }),
		datasets : [
			{
				label: "상(H)",
				borderWidth: 1,
				borderColor : "rgba(255, 99, 132, 1)",
				backgroundColor : "rgba(255, 99, 132, 0.7)",
				data : $.map(jsonData[idx], function(el) { return el.scoreH })
			},
			{
				label: "중(M)",
				borderWidth: 1,
				borderColor : "rgba(54, 162, 235, 1)",
				backgroundColor : "rgba(54, 162, 235, 0.7)",
				data : $.map(jsonData[idx], function(el) { return el.scoreM })
			},
			{
				label: "하(L)",
				borderWidth: 1,
				borderColor : "rgba(255, 206, 86, 1)",
				backgroundColor : "rgba(255, 206, 86, 0.7)",
				data : $.map(jsonData[idx], function(el) { return el.scoreL })
			}

		]
	};

	if (chartInstance[idx]) {
		chartInstance[idx].destroy();
	}

	chartInstance[idx] = new Chart($('#chart' + idx), {
		type: 'bar',
		data: chartData[chartType],
		responsive: true,
		options: {
			title: {
				display: true,
				fontSize: 16,
				fontFamily: 'NG'
			},
			legend: { position: 'bottom' },
			tooltips: { mode: 'label' },
			scales: {
				xAxes: [{
					stacked: true,
	 				categoryPercentage: 0.3,
					gridLines: { display: false }
				}],
				yAxes: [{
					stacked: true,
					ticks: {
			           	userCallback: function(value, index, values) {
			           		return getNumeric(value);
			           	}
			        }
				}]
			}
		}
	});
}

//데이터 테이블
function progressTableData(idx, toLink) {
	var $tbl = $("#chartTbl" + idx);
	$tbl.find("tbody, tfoot").empty();

	$.each(jsonData[idx], function(k, v) {
		var html = "";
		var url = "";
		html += '<tr><td>' + v.titleName + '</td>';

		html += v.total > 0  ? '<td class="link" onclick="goUrl('+ v.uacCatKey +', \'A\')"><a href="javascript:;">' + v.total + '</a></td>' : '<td>' + v.total + '</td>';
		html +=	v.scoreH > 0 ? '<td class="link" onclick="goUrl('+ v.uacCatKey +', \'H\')"><a href="javascript:;">' + v.scoreH + '</a></td>' : '<td>' + v.scoreH + '</td>'
		html +=	v.scoreM > 0 ? '<td class="link" onclick="goUrl('+ v.uacCatKey +', \'M\')"><a href="javascript:;">' + v.scoreM + '</a></td>' : '<td>' + v.scoreM + '</td>'
		html += v.scoreL > 0 ? '<td class="last link" onclick="goUrl('+ v.uacCatKey +', \'L\')"><a href="javascript:;">' + v.scoreL + '</a></td>' : '<td class="last">' + v.scoreL + '</td></tr>';
		v.titleCode != "total" ? $tbl.find("tbody").append(html) : $tbl.find("tfoot").append(html);
	});
}

function goUrl(cat, doa) {
	var url = "";
	if (cat == 0) {
		url += "../riskm/FM-RISKM010.do?doaGrd=" + doa;
	}
	else {
		url = "../riskm/FM-RISKM003.do?cat=" + cat + "&doaGrd=" + doa;
	}

	window.open(url);
}
</script>
</head>
<body>
	<c:import url="/titlebar.do" />
	<div class="chartContainer" id="chartContainer1">
		<div class="tabArea" style="margin-bottom: 10px;">
		    <ul id="listTab1" class="list2Tab">
		        <li class="sel"><a href="javascript:;">유형별 위험도 (건)</a></li>
		        <li></li>
		    </ul>
		</div>
		<canvas class="canvasChart" id="chart1"></canvas>
		<div class="chartLegend" id="legend1"></div>
		<div class="talbeArea">
			<table id="chartTbl1" summary="통제항목별 진행현황을 통제항목, 전체, 완료, 지연, 미진행 등을 확인 할 수 있습니다.">
				<colgroup>
					<col width="">
					<col width="18%">
					<col width="18%">
					<col width="18%">
					<col width="18%">
				</colgroup>
				<caption>우형별</caption>
				<thead>
	                   <tr>
	                       <th>유형</th>
	                       <th>전체</th>
	                       <th>상 (H)</th>
	                       <th>중 (M)</th>
	                       <th class="last">하 (L)</th>
	                   </tr>
				</thead>
	            <tbody>
	            </tbody>
	            <tfoot>
	            </tfoot>
			</table>
		</div>
		<div class="comment">
		</div>
	</div>
</body>
</html>