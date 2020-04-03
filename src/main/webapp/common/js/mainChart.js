var jsonData = new Array();
var chartInstance = new Array();

$(function() {
	$.ajaxSetup({
		async: false,
		headers: { "Cache-Control": "max-age=0, no-cache" }
	});
});

function mainProgressBarChart(idx, url, params, removeTotal) {
	// get Json data
	$.post(url, params, function(json) {
		jsonData[idx] = json.result;
	}, "json");
	// remove last(total) element
	if (removeTotal) {
		jsonData[idx].pop();
	}

	var chartData1 = {
		labels : $.map(jsonData[idx], function(el) { return el.titleName }),
		datasets : [
			{
				label: "완료(%)",
				borderColor : "rgba(54, 162, 235, 1)",
				backgroundColor : "rgba(54, 162, 235, 0.75)",
				data : $.map(jsonData[idx], function(el) { return el.perComp })
			},
			{
				label: "지연(%)",
				borderColor : "rgba(255, 99, 132, 1)",
				backgroundColor : "rgba(255, 99, 132, 0.75)",
				data : $.map(jsonData[idx], function(el) { return el.perDelay })
			},
			{
				label: "미진행(%)",
				borderColor : "rgba(255, 206, 86, 1)",
				backgroundColor : "rgba(255, 206, 86, 0.75)",
				data : $.map(jsonData[idx], function(el) { return el.perReady })
			}

		]
	};

	if (chartInstance[idx]) {
		chartInstance[idx].destroy();
	}

	try {
		chartInstance[idx] = new Chart($('#chart' + idx), {
			type: 'bar',
			data: chartData1,
			responsive: true,
			options: {
				legend: { position: 'bottom' },
				tooltips: { mode: 'label' },
				scales: {
					xAxes: [{
						stacked: true,
		 				categoryPercentage: 0.4,
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
	} catch(e) {}
}


function mainProgressRadarChart(idx, url, params, removeTotal) {
	$.post(url, params, function(json) {
		jsonData[idx] = json.result;
	}, "json");
	// remove last(total) element
	if (removeTotal) {
		jsonData[idx].pop();
	}

	var chartData1 = {
		labels : $.map(jsonData[idx], function(el) { return el.titleName }),
		datasets : [
			{
				label: "달성률(%)",
				backgroundColor: "rgba(54, 162, 235, 0.5)",
	            borderColor: "rgba(54, 162, 235, 1)",
	            pointBackgroundColor: "rgba(54, 162, 235, 1)",
	            pointBorderColor: "rgba(54, 162, 235, 1)",
	            pointHoverBackgroundColor: "#fff",
	            pointHoverBorderColor: "#fff",
				data : $.map(jsonData[idx], function(el) { return el.perComp })
			}

		]
	};

	if (chartInstance[idx]) {
		chartInstance[idx].destroy();
	}

	try {
		chartInstance[idx] = new Chart($('#chart' + idx), {
			type: 'radar',
			data: chartData1,
			options: {
				legend: { display: false },
				tooltips: { mode: 'label' },
				scale: {
		            ticks: {
		                beginAtZero: true,
		                max: 100
		            }
		        }
			}
		});
	}
	catch(e) {}
}

function mainProgressPieChart(idx, url, params) {
	// get Json data
	var tmpData;
	$.post(url, params, function(json) {
		jsonData[idx] = json.result;
		data = json.periodResult;
	}, "json");

	$obj = $('#chartContainer' + idx);
	$obj.find(".compCnt").text(jsonData[idx].comp);
	$obj.find(".delayCnt").text(jsonData[idx].delay);
	$obj.find(".readyCnt").text(jsonData[idx].ready);
	$obj.find(".total").text(jsonData[idx].total);

	$obj.find(".nonePeriod").text(data.nonePeriod);
	$obj.find(".period").text(data.period);

	//var data = jsonData[idx];

	var chartData1 = {
		labels : ["완료", "지연", "미진행"],
		datasets : [
			{
				backgroundColor: [
				                   "rgba(54, 162, 235, 0.75)",
				                   "rgba(255, 99, 132, 0.75)",
				                   "rgba(255, 206, 86, 0.75)"
				               ],
				hoverBackgroundColor: [
				                   "rgba(54, 162, 235, 1)",
				                   "rgba(255, 99, 132, 1)",
				                   "rgba(255, 206, 86, 1)"
				               ],
				data : [jsonData[idx].comp, jsonData[idx].delay, jsonData[idx].ready]
			}
		]
	};

	if (chartInstance[idx]) {
		chartInstance[idx].destroy();
	}

	try {
		chartInstance[idx] = new Chart($('#chart' + idx), {
			type: 'doughnut',
			data: chartData1,
			responsive: true,
			options: {
				legend: { display: false },
				tooltips: { mode: 'label' }
			}
		});
	}
	catch(e) {}
}

//2017-02-08, number comma
function getNumeric(number) {
	num = String(number).replace(/^\s+|\s+$/g, "");
	var regex = /^[0-9]+$/g;
	if (regex.test(num)) {
		return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	}
	else {
		return "";
	}
}