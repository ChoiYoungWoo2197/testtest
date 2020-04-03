var jsonData = new Array();
var chartInstance = new Array();

$(function() {
	$.ajaxSetup({
		async: false
	});

	Chart.defaults.global.scaleFontFamily = "'gulimche', 'dotumche'";
	Chart.defaults.global.scaleFontColor = "#333";
	Chart.defaults.global.tooltipFontFamily = "'gulimche', 'dotumche'";
	Chart.defaults.global.tooltipTitleFontFamily = "'gulimche', 'dotumche'";
});

function mainProgressBarChart(idx, url, params, removeTotal) {
	// get Json data
	$.getJSON(url, params, function(json) {
		jsonData[idx] = json.result;
	});
	// remove last(total) element
	if (removeTotal) {
		jsonData[idx].pop();
	}

	var chartData1 = {
		labels : $.map(jsonData[idx], function(el) { return el.titleName }),
		datasets : [
			{
				label: "완료",
				borderWidth: 2,
				strokeColor : "rgba(54, 162, 235, 1)",
				fillColor : "rgba(54, 162, 235, 0.75)",
				data : $.map(jsonData[idx], function(el) { return el.perComp })
			},
			{
				label: "지연",
				borderWidth: 2,
				strokeColor : "rgba(255, 99, 132, 1)",
				fillColor : "rgba(255, 99, 132, 0.75)",
				data : $.map(jsonData[idx], function(el) { return el.perDelay })
			},
			{
				label: "미진행",
				borderWidth: 2,
				strokeColor : "rgba(255, 206, 86, 1)",
				fillColor : "rgba(255, 206, 86, 0.75)",
				data : $.map(jsonData[idx], function(el) { return el.perReady })
			}

		]
	};

	if (chartInstance[idx]) {
		chartInstance[idx].destroy();
	}

	var ctx = document.getElementById('chart' + idx).getContext("2d");
	chartInstance[idx] = new Chart(ctx).StackedBar(chartData1, {
		responsive: true,
		barMaxWidth: 30
	});
}


function mainProgressRadarChart(idx, url, params, removeTotal) {
	$.getJSON(url, params, function(json) {
		jsonData[idx] = json.result;
	});
	// remove last(total) element
	if (removeTotal) {
		jsonData[idx].pop();
	}

	var chartData1 = {
		labels : $.map(jsonData[idx], function(el) { return el.titleName }),
		datasets : [
			{
				label: "달성률(%)",
				fillColor: "rgba(90, 174, 255, 0.5)",
				strokeColor: "rgba(90, 174, 255, 1)",
				pointColor: "rgba(90, 174, 255, 1)",
				pointHighlightStroke: "rgba(90, 174, 255, 1)",
				pointStrokeColor: "#fff",
				pointHighlightFill: "#fff",
				data : $.map(jsonData[idx], function(el) { return el.perComp })
			}

		]
	};

	if (chartInstance[idx]) {
		chartInstance[idx].destroy();
	}

	var ctx = document.getElementById('chart' + idx).getContext("2d");
	chartInstance[idx] = new Chart(ctx).Radar(chartData1, {
		responsive: true,
	});
}

function mainProgressPieChart(idx, url, params) {
	// get Json data
	var tmpData;
	$.getJSON(url, params, function(json) {
		jsonData[idx] = json.result;
		data = json.periodResult;
	});

	$obj = $('#chartContainer' + idx);
	$obj.find(".compCnt").text(jsonData[idx].comp);
	$obj.find(".delayCnt").text(jsonData[idx].delay);
	$obj.find(".readyCnt").text(jsonData[idx].ready);
	$obj.find(".total").text(jsonData[idx].total);

	$obj.find(".nonePeriod").text(data.nonePeriod);
	$obj.find(".period").text(data.period);

	//var data = jsonData[idx];

	var chartData1 = [
		{
			value: jsonData[idx].comp,
			color:"rgba(54, 162, 235, 0.75)",
			highlight: "rgba(54, 162, 235, 1)",
			label: "완료"
		},
		{
			value: jsonData[idx].delay,
			color: "rgba(255, 99, 132, 0.75)",
			highlight: "rgba(255, 99, 132, 1)",
			label: "지연"
		},
		{
			value: 0,
			color: "rgba(255, 206, 86, 0.75)",
			highlight: "rgba(255, 206, 86, 1)",
			label: "미진행"
		}
	];

	if (chartInstance[idx]) {
		chartInstance[idx].destroy();
	}

	var ctx = document.getElementById('chart' + idx).getContext("2d");
	chartInstance[idx] = new Chart(ctx).Doughnut(chartData1, {
		responsive: true
	});
}