var jsonData = new Array();
var chartInstance = new Array();
var defaultHtml =
		'<ul class="chartLabel" style="position: relative; top: -50px; left: 400px; width: 200px;">'
			+'<li class="comp"><span class="icon"></span> 완료</li>'
			+'<li class="delay"><span class="icon"></span> 지연</li>'
			+'<li class="ready"><span class="icon"></span> 미진행</li>'
		+'</ul>';

$(function() {
	$.ajaxSetup({
		async: false
	});

	Chart.defaults.global.scaleFontFamily = "'gulimche', 'dotumche'";
	Chart.defaults.global.scaleFontColor = "#333";
	Chart.defaults.global.tooltipFontFamily = "'gulimche', 'dotumche'";
	Chart.defaults.global.tooltipTitleFontFamily = "'gulimche', 'dotumche'";
});

// 진행현황(건) + 진형률(%) + 데이터 테이블
function progressBarChart(idx, url, title, toLink, params) {
	// get Json data
	$.getJSON(url, params, function(json) {
		jsonData[idx] = json.result;
	});

	if ($.isEmptyObject(jsonData[idx])) {
		$("#chartContainer" + idx).hide();
		return false;
	}

	// set table data
	progressTableData(idx, toLink);
	$("#chartContainer" + idx).show();

	var chartData1 = {
		labels : $.map(jsonData[idx], function(el) { return el.titleName }),
		datasets : [
			{
				label: "완료",
				borderWidth: 2,
				strokeColor : "rgba(54, 162, 235, 1)",
				fillColor : "rgba(54, 162, 235, 0.75)",
				data : $.map(jsonData[idx], function(el) { return el.comp })
			},
			{
				label: "지연",
				borderWidth: 2,
				strokeColor : "rgba(255, 99, 132, 1)",
				fillColor : "rgba(255, 99, 132, 0.75)",
				data : $.map(jsonData[idx], function(el) { return el.delay })
			},
			{
				label: "미진행",
				borderWidth: 2,
				strokeColor : "rgba(255, 206, 86, 1)",
				fillColor : "rgba(255, 206, 86, 0.75)",
				data : $.map(jsonData[idx], function(el) { return el.ready })
			}

		]
	};

	var chartData2 = {
		labels : $.map(jsonData[idx], function(el) { return el.titleName }),
		datasets : [
			{
				label: "완료(%)",
				borderWidth: 2,
				strokeColor : "rgba(54, 162, 235, 1)",
				fillColor : "rgba(54, 162, 235, 0.75)",
				data : $.map(jsonData[idx], function(el) { return el.perComp })
			},
			{
				label: "지연(%)",
				borderWidth: 2,
				strokeColor : "rgba(255, 99, 132, 1)",
				fillColor : "rgba(255, 99, 132, 0.75)",
				data : $.map(jsonData[idx], function(el) { return el.perDelay })
			},
			{
				label: "미진행(%)",
				borderWidth: 2,
				strokeColor : "rgba(255, 206, 86, 1)",
				fillColor : "rgba(255, 206, 86, 0.75)",
				data : $.map(jsonData[idx], function(el) { return el.perReady })
			}
		]
	};

	var no = idx * 2;
	if (chartInstance[no]) {
		chartInstance[no - 1].destroy();
		chartInstance[no].destroy();
	}

	try {
		var ctx = document.getElementById('chart' + idx + 'a').getContext("2d");
		chartInstance[no - 1] = new Chart(ctx).StackedBar(chartData1, {
			responsive: true,
		});
		$('#legend' + idx + 'a').html(defaultHtml);
	} catch(e) {}

	try {
		var ctx2 = document.getElementById('chart' + idx + 'b').getContext("2d");
		chartInstance[no] = new Chart(ctx2).StackedBar(chartData2, {
			responsive: true,
		});
		$('#legend' + idx + 'b').html(defaultHtml);
	} catch(e) {}

	if (idx > 1) {
		$("body").scrollTo($("#chartContainer" + idx), 300);
	}
}

// 진행현황(건) + 진형률(%) + 데이터 테이블
function progressLineChart(idx, url, title, toLink, params) {
	// get Json data
	$.getJSON(url, params, function(json) {
		jsonData[idx] = json.result;
	});

	if ($.isEmptyObject(jsonData[idx])) {
		$("#chartContainer" + idx).hide();
		return false;
	}

	// set table data
	progressTableData(idx, toLink);

	// remove last(total) element
	jsonData[idx].pop();

	var chartData1 = {
		labels : $.map(jsonData[idx], function(el) { return el.titleName }),
		datasets : [
			{
				label: "완료",
				fill: false,
				lineTension: 0.2,
				strokeColor : "rgba(54, 162, 235, 1)",
				fillColor : "rgba(220, 220, 220, 0)",
				pointColor : "rgba(54, 162, 235, 1)",
				pointStrokeColor : "#fff",
				pointHighlightFill : "#fff",
				pointHighlightStroke : "rgba(54, 162, 235, 1)",
				data : $.map(jsonData[idx], function(el) { return el.comp })
			},
			{
				label: "지연",
				fill: false,
				lineTension: 0.2,
				strokeColor : "rgba(255, 99, 132, 1)",
				fillColor : "rgba(220, 220, 220, 0)",
				pointColor : "rgba(255, 99, 132, 1)",
				pointStrokeColor : "#fff",
				pointHighlightFill : "#fff",
				pointHighlightStroke : "rgba(255, 99, 132, 1)",
				data : $.map(jsonData[idx], function(el) { return el.delay })
			},
			{
				label: "미진행",
				fill: false,
				lineTension: 0.2,
				strokeColor : "rgba(255, 206, 86, 1)",
				fillColor : "rgba(220, 220, 220, 0)",
				pointColor : "rgba(255, 206, 86, 1)",
				pointStrokeColor : "#fff",
				pointHighlightFill : "#fff",
				pointHighlightStroke : "rgba(255, 206, 86, 1)",
				data : $.map(jsonData[idx], function(el) { return el.ready })
			},
			{
				label: "전체",
				fill: false,
				lineTension: 0.2,
				strokeColor : "rgba(185, 185, 185, 1)",
				fillColor : "rgba(220, 220, 220, 0)",
				pointColor : "rgba(185, 185, 185, 1)",
				pointStrokeColor : "#fff",
				pointHighlightFill : "#fff",
				pointHighlightStroke : "rgba(185, 185, 185, 1)",
				data : $.map(jsonData[idx], function(el) { return el.total })
			}
		]
	};

	var chartData2 = {
		labels : $.map(jsonData[idx], function(el) { return el.titleName }),
		datasets : [
			{
				label: "완료(%)",
				strokeColor : "rgba(54, 162, 235, 1)",
				fillColor : "rgba(54, 162, 235, 0.75)",
				data : $.map(jsonData[idx], function(el) { return el.perComp })
			},
			{
				label: "지연(%)",
				strokeColor : "rgba(255, 99, 132, 1)",
				fillColor : "rgba(255, 99, 132, 0.75)",
				data : $.map(jsonData[idx], function(el) { return el.perDelay })
			},
			{
				label: "미진행(%)",
				strokeColor : "rgba(255, 206, 86, 1)",
				fillColor : "rgba(255, 206, 86, 0.75)",
				data : $.map(jsonData[idx], function(el) { return el.perReady })
			}

		]
	};

	var no = idx * 2;
	if (chartInstance[no]) {
		chartInstance[no - 1].destroy();
		chartInstance[no].destroy();
	}

	try {
		var ctx = document.getElementById('chart' + idx + 'a').getContext("2d");
		chartInstance[no - 1] = new Chart(ctx).Line(chartData1, {
			responsive: true,
		});
		$('#legend' + idx + 'a').html(defaultHtml);
	} catch(e) {}

	try {
		var ctx2 = document.getElementById('chart' + idx + 'b').getContext("2d");
		chartInstance[no] = new Chart(ctx2).StackedBar(chartData2, {
			responsive: true,
		});
		$('#legend' + idx + 'b').html(defaultHtml);
	} catch(e) {}
}

//
// 진행현황(건) [차트 동적 생성]
function progressBarChartDynamic(idx, url, title, toLink, params, removeTotal) {

	$.getJSON(url, params, function(json) {
		jsonData[idx] = json.result;
		category = json.category;
	});
	// remove last(total) element
	if (removeTotal) {
		jsonData[idx].pop();
	}

	if ($.isEmptyObject(jsonData[idx])) {
		$("#chartContainer" + idx).hide();
		return false;
	}

	$("#chartContainer" + idx).show();
	var arrBackground = [
		'rgba(168,197,69,0.7)',
		'rgba(255,148,54,0.7)',
		'rgba(90,174,255,0.7)',
	    'rgba(255,206,86,0.7)',
	    'rgba(75,192,192,0.7)',
	    'rgba(153,102,255,0.7)',
		'rgba(168,197,69,0.7)',
		'rgba(221,207,172,0.7)',
		'rgba(119,119,119,0.7)',
		'rgba(159,181,205,0.7)',
		'rgba(42,162,160,0.7)',
		'rgba(55,192,124,0.7)',
		'rgba(63,89,110,0.7)',
		'rgba(126,146,165,0.7)',
		'rgba(164,141,112,0.7)',
		'rgba(119,182,208,0.7)',
		'rgba(206,228,237,0.7)',
		'rgba(85,85,85,0.7)',
		'rgba(177,149,166,0.7)',
		'rgba(212,196,206,0.7)',
		'rgba(103,66,90,0.7)',
		'rgba(185,220,218,0.7)',
		'rgba(55,152,185,0.7)',
		'rgba(137,213,175,0.7)',
		'rgba(134,151,38,0.7)',
		'rgba(193,204,137,0.7)',
		'rgba(131,110,44,0.7)',
		'rgba(197,186,142,0.7)',
		'rgba(129,109,91,0.7)',
		'rgba(222,227,231,0.7)',
		'rgba(132,146,159,0.7)',
		'rgba(59,75,91,0.7)'
	];
	var chartData1 = {};
	var datasets = Array();
	chartData1.labels = $.map(jsonData[idx], function(el) { return el.titleName });
	var keys = $.map(category, function(el) { return el.name });

	var i = 0;
	$.each(category, function(k, v) {
		key = v.code.toLowerCase();
		bg = arrBackground[i];
		datasets.push(
		{
			label: v.name,
			strokeColor : bg,
			fillColor : bg,
			data : $.map(jsonData[idx], function(el) { return el[key] })
		});
		i >= arrBackground.length - 1 ? i = 0 : i++;
	});
	chartData1.datasets = datasets;

	if (chartInstance[idx]) {
		chartInstance[idx].destroy();
	}

	try {
		var ctx = document.getElementById('chart' + idx).getContext("2d");
		chartInstance[idx] = new Chart(ctx).StackedBar(chartData1, {
			responsive: true,
		});
	} catch(e) {}

	if (idx > 1) {
		$("body").scrollTo($("#chartContainer" + idx), 300);
	}
}

//Radar
function progressRadarChart(idx, url, title, toLink, params, removeTotal) {

	$.getJSON(url, params, function(json) {
		jsonData[idx] = json.result;
		category = json.category;
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

	try {
		var ctx = document.getElementById('chart' + idx).getContext("2d");
		chartInstance[idx] = new Chart(ctx).Radar(chartData1, {
			responsive: true,
		});
	} catch(e) {}
}

// 데이터 테이블
function progressTableData(idx, toLink) {
	var $tbl = $("#chartTbl" + idx);
	$tbl.find("tbody, tfoot").empty();

	$.each(jsonData[idx], function(k, v) {
		var html = "";
		if (v.total != 0 && toLink) {
			html += '<tr><td class="link" id="' + v.titleCode + '"><a href="javascript:;">' + v.titleName + '</a></td>'
		}
		else {
			html += '<tr><td>' + v.titleName + '</td>'
		}
		html += '<td>' + v.total + '</td>'
			 +  '<td>' + v.comp + '</td>'
			 +  '<td>' + v.delay + '</td>'
			 +  '<td>' + v.ready + '</td>'
			 +  '<td>' + v.perComp + '</td>'
			 +  '<td>' + v.perDelay + '</td>'
			 +  '<td class="last">' + v.perReady + '</td></tr>'
		v.titleCode != "total" ? $tbl.find("tbody").append(html) : $tbl.find("tfoot").append(html);
	});
}

// 데이터 테이블 [컬럼 동적 생성]
function progressTableDataDynamic(idx, url, th, toLink, params) {

	var category = {};
	var $tbl = $("#chartTbl" + idx);
	$tbl.find("thead, tbody, tfoot").empty();

	$.getJSON(url, params, function(json) {
		jsonData[idx] = json.result;
		category = json.category;
	});

	var html = '<tr><th>' + th + '</th>';
	$.each(category, function(k, v) {
		html += '<th>' + v.name + '</th>';
	});
	html += '</tr>';
	$tbl.find("thead").append(html);

	$.each(jsonData[idx], function(k, v) {
		var html = "";
		if (v.total != 0 && toLink) {
			html += '<tr><td class="link" id="' + v.titleCode + '"><a href="javascript:;">' + v.titleName + '</a></td>'
		}
		else {
			html += '<tr><td>' + v.titleName + '</td>'
		}
		$.each(category, function(k2, v2) {
			key = v2.code.toLowerCase();
			html += '<td>' + toNumberFormat(v[key]) + '</td>';
		});
		html += '</tr>';
		v.titleCode != "total" ? $tbl.find("tbody").append(html) : $tbl.find("tfoot").append(html);
	});
}
