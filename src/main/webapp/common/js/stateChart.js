var jsonData = new Array();
var chartInstance = new Array();

var colorChart = [
	'rgba(23,120,200,0.7)', 'rgba(255,79,66,0.7)', 'rgba(255,190,7,0.7)', 'rgba(220,100,230,0.7)'
	, 'rgba(100,180,20,0.7)', 'rgba(100,100,100,0.7)', 'rgba(76,235,195,0.7)', 'rgba(255,125,206,0.7)'
	, 'rgba(255,91,84,0.7)', 'rgba(160,134,251,0.7)', 'rgba(220,191,76,0.7)','rgba(160,160,160,0.7)'
    ,'rgba(54,162,235,0.7)', 'rgba(255,99,132,0.7)', 'rgba(255,230,20,0.7)', 'rgba(205,179,255,0.7)'
	, 'rgba(180,250,100,0.7)', 'rgba(200,200,200,0.7)', 'rgba(32,200,230,0.7)', 'rgba(255,40,140,0.7)'
	, 'rgba(255,158,0,0.7)', 'rgba(240,62,255,0.7)', 'rgba(230,255,126,0.7)', 'rgba(130,130,130,0.7)'
    , 'rgba(100,152,205,0.7)', 'rgba(230,150,135,0.7)', 'rgba(223,200,98,0.7)', 'rgba(218,154,219,0.7)'
	, 'rgba(131,177,96,1)'
	,
	'rgba(23,120,200,0.7)', 'rgba(255,79,66,0.7)', 'rgba(255,190,7,0.7)', 'rgba(220,100,230,0.7)'
	, 'rgba(100,180,20,0.7)', 'rgba(100,100,100,0.7)', 'rgba(76,235,195,0.7)', 'rgba(255,125,206,0.7)'
	, 'rgba(255,91,84,0.7)', 'rgba(160,134,251,0.7)', 'rgba(220,191,76,0.7)','rgba(160,160,160,0.7)'
	,'rgba(54,162,235,0.7)', 'rgba(255,99,132,0.7)', 'rgba(255,230,20,0.7)', 'rgba(205,179,255,0.7)'
	, 'rgba(180,250,100,0.7)', 'rgba(200,200,200,0.7)', 'rgba(32,200,230,0.7)', 'rgba(255,40,140,0.7)'
	, 'rgba(255,158,0,0.7)', 'rgba(240,62,255,0.7)', 'rgba(230,255,126,0.7)', 'rgba(130,130,130,0.7)'
	, 'rgba(100,152,205,0.7)', 'rgba(230,150,135,0.7)', 'rgba(223,200,98,0.7)', 'rgba(218,154,219,0.7)'
	, 'rgba(131,177,96,1)'
	,
	'rgba(23,120,200,0.7)', 'rgba(255,79,66,0.7)', 'rgba(255,190,7,0.7)', 'rgba(220,100,230,0.7)'
	, 'rgba(100,180,20,0.7)', 'rgba(100,100,100,0.7)', 'rgba(76,235,195,0.7)', 'rgba(255,125,206,0.7)'
	, 'rgba(255,91,84,0.7)', 'rgba(160,134,251,0.7)', 'rgba(220,191,76,0.7)','rgba(160,160,160,0.7)'
	,'rgba(54,162,235,0.7)', 'rgba(255,99,132,0.7)', 'rgba(255,230,20,0.7)', 'rgba(205,179,255,0.7)'
	, 'rgba(180,250,100,0.7)', 'rgba(200,200,200,0.7)', 'rgba(32,200,230,0.7)', 'rgba(255,40,140,0.7)'
	, 'rgba(255,158,0,0.7)', 'rgba(240,62,255,0.7)', 'rgba(230,255,126,0.7)', 'rgba(130,130,130,0.7)'
	, 'rgba(100,152,205,0.7)', 'rgba(230,150,135,0.7)', 'rgba(223,200,98,0.7)', 'rgba(218,154,219,0.7)'
	, 'rgba(131,177,96,1)'
	,
	'rgba(23,120,200,0.7)', 'rgba(255,79,66,0.7)', 'rgba(255,190,7,0.7)', 'rgba(220,100,230,0.7)'
	, 'rgba(100,180,20,0.7)', 'rgba(100,100,100,0.7)', 'rgba(76,235,195,0.7)', 'rgba(255,125,206,0.7)'
	, 'rgba(255,91,84,0.7)', 'rgba(160,134,251,0.7)', 'rgba(220,191,76,0.7)','rgba(160,160,160,0.7)'
	,'rgba(54,162,235,0.7)', 'rgba(255,99,132,0.7)', 'rgba(255,230,20,0.7)', 'rgba(205,179,255,0.7)'
	, 'rgba(180,250,100,0.7)', 'rgba(200,200,200,0.7)', 'rgba(32,200,230,0.7)', 'rgba(255,40,140,0.7)'
	, 'rgba(255,158,0,0.7)', 'rgba(240,62,255,0.7)', 'rgba(230,255,126,0.7)', 'rgba(130,130,130,0.7)'
	, 'rgba(100,152,205,0.7)', 'rgba(230,150,135,0.7)', 'rgba(223,200,98,0.7)', 'rgba(218,154,219,0.7)'
	, 'rgba(131,177,96,1)'
];

$(function() {
	$.ajaxSetup({
		async: false,
		headers: { "Cache-Control": "max-age=0, no-cache" }
	});
});

// 진행현황(건) + 진형률(%) + 데이터 테이블
function progressBarChart(idx, url, chartType, toLink, params, removeTotal) {
	var chartData = Array();
	jsonData = new Array();
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
				label: "완료",
				borderWidth: 1,
				borderColor : "rgba(54, 162, 235, 1)",
				backgroundColor : "rgba(54, 162, 235, 0.7)",
				data : $.map(jsonData[idx], function(el) { return el.comp })
			},
			{
				label: "지연",
				borderWidth: 1,
				borderColor : "rgba(255, 99, 132, 1)",
				backgroundColor : "rgba(255, 99, 132, 0.7)",
				data : $.map(jsonData[idx], function(el) { return el.delay })
			},
			{
				label: "미진행",
				borderWidth: 1,
				borderColor : "rgba(255, 206, 86, 1)",
				backgroundColor : "rgba(255, 206, 86, 0.7)",
				data : $.map(jsonData[idx], function(el) { return el.ready })
			}

		]
	};

	chartData[1] = {
		labels : $.map(jsonData[idx], function(el) { return el.titleName }),
		datasets : [
			{
				label: "완료(%)",
				borderWidth: 1,
				borderColor : "rgba(54, 162, 235, 1)",
				backgroundColor : "rgba(54, 162, 235, 0.7)",
				data : $.map(jsonData[idx], function(el) { return el.perComp })
			},
			{
				label: "지연(%)",
				borderWidth: 1,
				borderColor : "rgba(255, 99, 132, 1)",
				backgroundColor : "rgba(255, 99, 132, 0.7)",
				data : $.map(jsonData[idx], function(el) { return el.perDelay })
			},
			{
				label: "미진행(%)",
				borderWidth: 1,
				borderColor : "rgba(255, 206, 86, 1)",
				backgroundColor : "rgba(255, 206, 86, 0.7)",
				data : $.map(jsonData[idx], function(el) { return el.perReady })
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

	if (idx > 1) {
		$("body").scrollTo($("#chartContainer" + idx), 300);
	}
}

// 진행현황(건) + 진형률(%) + 데이터 테이블
function progressLineChart(idx, url, chartType, toLink, params) {
	var chartData = Array();
	jsonData = new Array();
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

	chartData[0] = {
		labels : $.map(jsonData[idx], function(el) { return el.titleName }),
		datasets : [
			{
				label: "완료",
				fill: false,
				tension: 0.3,
				borderColor : "rgba(54, 162, 235, 1)",
				backgroundColor : "rgba(54, 162, 235, 0.7)",
				data : $.map(jsonData[idx], function(el) { return el.comp })
			},
			{
				label: "지연",
				fill: false,
				tension: 0.3,
				borderColor : "rgba(255, 99, 132, 1)",
				backgroundColor : "rgba(255, 99, 132, 0.7)",
				data : $.map(jsonData[idx], function(el) { return el.delay })
			},
			{
				label: "미진행",
				fill: false,
				tension: 0.3,
				borderColor : "rgba(255, 206, 86, 1)",
				backgroundColor : "rgba(255, 206, 86, 0.7)",
				data : $.map(jsonData[idx], function(el) { return el.ready })
			},
			{
				label: "전체",
				fill: false,
				tension: 0.3,
				borderColor : "rgba(185,185,185,1)",
				backgroundColor : "rgba(185,185,185,0.7)",
				data : $.map(jsonData[idx], function(el) { return el.total })
			}
		]
	};

	if (chartInstance[idx]) {
		chartInstance[idx].destroy();
	}

	chartInstance[idx] = new Chart($('#chart' + idx), {
		type: 'line',
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
					gridLines: { display: false }
				}],
				yAxes: [{
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

// 진행현황(건) [차트 동적 생성]
function progressBarChartDynamic(idx, url, title, toLink, params, removeTotal) {
	jsonData = new Array();
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
			backgroundColor : bg,
			data : $.map(jsonData[idx], function(el) { return el[key] })
		});
		i >= arrBackground.length - 1 ? i = 0 : i++;
	});
	chartData1.datasets = datasets;

	if (chartInstance[idx]) {
		chartInstance[idx].destroy();
	}

	chartInstance[idx] = new Chart($('#chart' + idx), {
		type: 'bar',
		data: chartData1,
		responsive: true,
		options: {
			title: {
				display: true,
				fontSize: 16,
				fontFamily: 'NG',
				text: title
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

	if (idx > 1) {
		$("body").scrollTo($("#chartContainer" + idx), 300);
	}
}

// Radar
function progressRadarChart(idx, url, title, toLink, params, removeTotal) {
	jsonData = new Array();
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
				backgroundColor: "rgba(90, 174, 255, 0.5)",
	            borderColor: "rgba(90, 174, 255, 1)",
	            pointBackgroundColor: "rgba(90, 174, 255, 1)",
	            pointBorderColor: "rgba(90, 174, 255, 1)",
	            pointHoverBackgroundColor: "#fff",
	            pointHoverBorderColor: "#fff",
				data : $.map(jsonData[idx], function(el) { return el.perComp })
			}

		]
	};

	if (chartInstance[idx]) {
		chartInstance[idx].destroy();
	}

	chartInstance[idx] = new Chart($('#chart' + idx), {
		type: 'radar',
		data: chartData1,
		options: {
			title: {
				display: true,
				fontSize: 16,
				fontFamily: 'NG',
				text: title + ' 달성률 (%)'
			},
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

// 2017-02-08, number comma
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

// 2017-03-03, tab
function clickListTab(obj) {
	$this = $(obj);
	$this.parent().find("li").removeClass("sel");
	$this.addClass("sel");
	return $this.parent().find("li").index($this);
}

function dec_point(val, d){
    if(d==undefined) d=2;
	var rval = parseInt(Math.round(val * Math.pow(10, d))) / Math.pow(10, d);
	return rval;
}

function progressChartSa(idx, url, chartType, params) {
	var chartData = Array();
	jsonData = new Array();
	$.getJSON(url, params, function(json) {
		jsonData[idx] = json.result;
	});
	$("#chartContainer" + idx).show();

	if(idx==1){
		progressTableDataSaPart1(idx);
        if(chartInstance[0]) chartInstance[0].destroy();
        if(chartInstance[1]) chartInstance[1].destroy();
        if(chartInstance[2]) chartInstance[2].destroy();
        if(chartInstance[3]) chartInstance[3].destroy();
        if(chartInstance[4]) chartInstance[4].destroy();
        if(chartInstance[5]) chartInstance[5].destroy();
	}else if(idx==2){
		progressTableDataSaPart2(idx);
		if(chartInstance[6]) chartInstance[6].destroy();
		if(chartInstance[7]) chartInstance[7].destroy();
	}else{
		return ;
	}

	if(idx==1){
		if($.isEmptyObject(jsonData[idx][0])){
			blankChart("chart1_1");
			blankChart("chart1_2_1");
			blankChart("chart1_2_2");
			blankChart("chart1_3");
			blankChart("chart1_4");
			blankChart("chart1_5");
			blankTable("chartTbl1_1");
			blankTable("chartTbl1_2");
			blankTable("chartTbl1_3");
			blankTable("chartTbl1_4");
			blankTable("chartTbl1_5");
			return false;
		}
	}else if(idx==2){
		if($.isEmptyObject(jsonData[idx][1])){
			blankChart("chart2_1");
			blankChart("chart2_2");
			blankTable("chartTbl2_1");
			blankTable("chartTbl2_2");
			return false;
		}
	}

	if(idx==1) {
		chartData[0] = {
			labels: $.map(jsonData[idx][0], function (el) {
				return $.map(el["dep3"], function (el2) {
					return el2.title;
				});
			}),
			datasets: [
				{
					label: "보안점검 Level",
					borderWidth: 1,
					borderColor: "rgba(54, 162, 235, 1)",
					backgroundColor: "rgba(54, 162, 235, 0.7)",
					data: $.map(jsonData[idx][0], function (el) {
						return $.map(el["dep3"], function (el2) {
							return el2.sa;
						});
					})
				}
			]
		};
		chartData[1] = {
			labels: $.map(jsonData[idx][0], function (el) {
				return $.map(el["dep3"], function (el2) {
					return el2.title;
				});
			}),
			datasets: [
				{
					label: "팀별 진척률(%)",
					backgroundColor: colorChart,
					borderColor: colorChart,
					data: $.map(jsonData[idx][0], function (el) {
						return $.map(el["dep3"], function (el2) {
							return el2.cnt-el2.cnt_na==0?0:dec_point(((el2.cnt_y*100)+(el2.cnt_up*70)+(el2.cnt_lp*30))/(el2.cnt-el2.cnt_na));
						});
					})
				}

			]
		};
		chartData[2] = {
			labels: $.map(jsonData[idx][0], function (el) {
				return $.map(el["dep3"], function (el2) {
					return el2.title;
				});
			}),
			datasets: [
				{
					label: "Y",
					borderWidth: 1,
					borderColor: colorChart[0],
					backgroundColor: colorChart[0],
					data: $.map(jsonData[idx][0], function (el) {
						return $.map(el["dep3"], function (el2) {
							return el2.cnt_y;
						});
					})
				},
				{
					label: "UP",
					borderWidth: 1,
					borderColor: colorChart[4],
					backgroundColor: colorChart[4],
					data: $.map(jsonData[idx][0], function (el) {
						return $.map(el["dep3"], function (el2) {
							return el2.cnt_up;
						});
					})
				},
				{
					label: "LP",
					borderWidth: 1,
					borderColor: colorChart[2],
					backgroundColor: colorChart[2],
					data: $.map(jsonData[idx][0], function (el) {
						return $.map(el["dep3"], function (el2) {
							return el2.cnt_lp;
						});
					})
				},
				{
					label: "N",
					borderWidth: 1,
					borderColor: colorChart[1],
					backgroundColor: colorChart[1],
					data: $.map(jsonData[idx][0], function (el) {
						return $.map(el["dep3"], function (el2) {
							return el2.cnt_n;
						});
					})
				},
				{
					label: "N/A",
					borderWidth: 1,
					borderColor: colorChart[11],
					backgroundColor: colorChart[11],
					data: $.map(jsonData[idx][0], function (el) {
						return $.map(el["dep3"], function (el2) {
							return el2.cnt_na;
						});
					})
				}
			]
		};
		chartData[3] = {
			labels: $.map(jsonData[idx][1], function (el) {
				return el.title;
			}),
			datasets: [
				{
					label: "전체 부서 진척률(%)",
					backgroundColor: colorChart,
					borderColor: colorChart,
					data: $.map(jsonData[idx][1], function (el) {
						//return el.prg;
						return dec_point(el.prg_sum/(el.count-el.cnt_na));
					})
				}

			]
		};
		chartData[4] = {
			labels: $.map(jsonData[idx][0], function (el1) {
				return $.map(el1["dep3"], function(el2) {
					return el2.title;
				});
			}),
			datasets: $.map(jsonData[idx][1], function(el1) {
				var i = jsonData[idx][1].indexOf(el1);
				var dataset = {label:el1.title, borderWidth:1, borderColor: colorChart[i], backgroundColor: colorChart[i], data:{} };
				dataset.data = $.map(jsonData[idx][0], function (el2) {
					return $.map(el2["dep3"], function(el3) {
						var dept = el1.dep[el3.code];
						var prg = 0;
						if(dept!=undefined){
							if(dept.count==0){
								prg = "N/A";
							}else{
								prg = dec_point(dept.prg/dept.count);
							}
						}
						return prg;
					});
				});
				return dataset;
			})
		};
		var ci = 0;
		chartData[5] = {
			labels: $.map(jsonData[1][3][1], function (el) {
				return el[1]
			}),
			datasets: $.map(jsonData[1][3][2][1], function (el) {
				var title = el.ummManTle;
				var cData = {
					label: title,
					fill: false,
					lineTension: 0.1,
					backgroundColor: colorChart[ci],
					borderColor: colorChart[ci],
					data: $.map(jsonData[1][3][1], function (el2) {
						return jsonData[1][3][0][el.ummManCyl]!=undefined&&jsonData[1][3][0][el.ummManCyl][el2[0]]!=undefined?jsonData[1][3][0][el.ummManCyl][el2[0]]:0;
					})
				};
				ci++;
				return cData;
			})
		};
	}else{
		chartData[0] = {
			labels : $.map(jsonData[idx][1], function(el){ return $.map(el["lv1"], function(el2){ return $.map(el2["lv2"], function(el3){ return el3.title;});});}),
			datasets : [
				{
					label: "항목별 진척률(%)",
					backgroundColor: colorChart,
					borderColor: colorChart[0],
					data : $.map(jsonData[idx][1], function(el){
						return $.map(el["lv1"], function(el2){
							return $.map(el2["lv2"], function(el3){
								return dec_point(el3.prg_sum/el3.prg_cnt);
							});
						});
					})
				}

			]
		};
		chartData[1] = {
			labels: $.map(jsonData[idx][1], function(el){ return $.map(el["lv1"], function(el2){ return el2.title;});}),
			datasets: [
				{
					label: "Y",
					borderWidth: 1,
					borderColor: colorChart[0],
					backgroundColor: colorChart[0],
					data: $.map(jsonData[idx][1], function (el) {
						return $.map(el["lv1"], function (el2) {
							return el2.cnt_y;
						});
					})
				},
				{
					label: "UP",
					borderWidth: 1,
					borderColor: colorChart[4],
					backgroundColor: colorChart[4],
					data: $.map(jsonData[idx][1], function (el) {
						return $.map(el["lv1"], function (el2) {
							return el2.cnt_up;
						});
					})
				},
				{
					label: "LP",
					borderWidth: 1,
					borderColor: colorChart[2],
					backgroundColor: colorChart[2],
					data: $.map(jsonData[idx][1], function (el) {
						return $.map(el["lv1"], function (el2) {
							return el2.cnt_lp;
						});
					})
				},
				{
					label: "N",
					borderWidth: 1,
					borderColor: colorChart[1],
					backgroundColor: colorChart[1],
					data: $.map(jsonData[idx][1], function (el) {
						return $.map(el["lv1"], function (el2) {
							return el2.cnt_n;
						});
					})
				},
				{
					label: "N/A",
					borderWidth: 1,
					borderColor: colorChart[5],
					backgroundColor: colorChart[5],
					data: $.map(jsonData[idx][1], function (el) {
						return $.map(el["lv1"], function (el2) {
							return el2.cnt_na;
						});
					})
				}
			]
		};
	}

    var duration = (params.duration==undefined?1000:params.duration);
    if(idx==1){
		var size_h = (chartData[1]["datasets"][0]["data"].length*20)+50;
		document.getElementById("chart1_1").height = 500;
		document.getElementById("chart1_2_1").height = size_h;
		document.getElementById("chart1_2_2").height = 500;
		document.getElementById("chart1_3").height = 500;
		document.getElementById("chart1_4").height = 500;
		document.getElementById("chart1_5").height = 500;
		chartInstance[0] = new Chart($("#chart1_1"), {
			type: "radar",
			data: chartData[0],
			options: {
                animation: {duration: duration},
				title: {
					display: true,
					fontSize: 16,
					text: ""
				},
				legend: {display:true, position: "bottom" },
				tooltips: {mode:"label"},
				scale: {
					ticks: {
						stepSize: 1,
						beginAtZero: true,
						max: 5
					}
				}
			}
		});
		chartInstance[1] = new Chart($("#chart1_2_1"), {
			type: "horizontalBar",
			data: chartData[1],
			responsive: true,
			options: {
                animation: {duration: duration},
				title: {display: false},
				legend: {display: false},
				tooltips: { mode: "label" },
				scales: {
					xAxes: [{
						stacked: true,
						gridLines: { display: true },
						ticks: {
							beginAtZero: true,
							max: 100
						}
					}]
				}
			}
		});
		chartInstance[2] = new Chart($("#chart1_2_2"), {
			type: "bar",
			data: chartData[2],
			responsive: true,
			options: {
                animation: {duration: duration},
				title: {display: false},
				legend: {
					position: 'bottom',
					labels: {
						fontSize: 8
					}
				},
				tooltips: { mode: "label" },
				scales: {
					xAxes: [{
						stacked: false,
						gridLines: { display: true }
					}],
					yAxes: [{
						stacked: false,
						ticks: {
							stepSize: 10,
							beginAtZero: true
						}
					}]
				}
			}
		});
		chartInstance[3] = new Chart($("#chart1_3"), {
			type: "bar",
			data: chartData[3],
			responsive: true,
			options: {
                animation: {duration: duration},
				title: {
					display: true,
					fontSize: 16,
					fontFamily: "NG"
				},
				legend: { display: false, position: "bottom" },
				tooltips: { mode: "label" },
				scales: {
					xAxes: [{
						stacked: true,
						categoryPercentage: 0.3,
						gridLines: { display: false }
					}],
					yAxes: [{
						stacked: true,
						ticks: {
							max: 100
						}
					}]
				}
			}
		});
		chartInstance[4] = new Chart($("#chart1_4"), {
			type: "bar",
			data: chartData[4],
			responsive: true,
			options: {
                animation: {duration: duration},
				title: {display: false},
				legend: {
					position: 'bottom',
					labels: {
						fontSize: 8
					}
				},
				tooltips: { mode: "label" },
				scales: {
					xAxes: [{
						stacked: false,
						gridLines: { display: true }
					}],
					yAxes: [{
						stacked: false,
						ticks: {
							min: 0,
							max: 100,
							stepSize: 10,
							beginAtZero: true
						}
					}]
				}
			}
		});
		chartInstance[5] = new Chart($("#chart1_5"), {
			type: 'line',
			data: chartData[5],
			responsive: true,
			options: {
				animation: {duration: duration},
				title: {
					display: true,
					fontSize: 16,
					fontFamily: 'NG'
				},
				legend: {position: 'bottom'},
				tooltips: {mode: 'label'},
				scales: {
					xAxes: [{
						gridLines: {display: false}
					}],
					yAxes: [{
						ticks: {
							min:0,
							max:5,
							stepSize: 1,
							userCallback: function (value, index, values) {
								return getNumeric(value);
							}
						}
					}]
				}
			}
		});
	}else{
		size_h = (chartData[0]["datasets"][0]["data"].length*20)+50;
		document.getElementById("chart2_1").height = size_h;
		document.getElementById("chart2_2").height = 500;
		chartInstance[6] = new Chart($("#chart2_1"), {
			type: "horizontalBar",
			data: chartData[0],
			responsive: true,
			options: {
                animation: {duration: duration},
				title: {display: false},
				legend: {display: false},
				tooltips: { mode: "label" },
				scales: {
					xAxes: [{
						stacked: true,
						gridLines: { display: true },
						ticks: {
							beginAtZero: false,
							max: 100
						}
					}]
				}
			}
		});
		chartInstance[7] = new Chart($("#chart2_2"), {
			type: "bar",
			data: chartData[1],
			responsive: true,
			options: {
                animation: {duration: duration},
				title: {display: false},
				legend: {
					position: 'bottom',
					labels: {
						fontSize: 8
					}
				},
				tooltips: { mode: "label" },
				scales: {
					xAxes: [{
						stacked: false,
						gridLines: { display: true }
					}],
					yAxes: [{
						stacked: false,
						ticks: {
							stepSize: 50,
							beginAtZero: true
						}
					}]
				}
			}
		});
	}
}
function progressTableDataSaPart1(idx) {
	progressTableDataSaPart1_1(idx);
	progressTableDataSaPart1_2(idx);
	progressTableDataSaPart1_3(idx);
	progressTableDataSaPart1_4(idx);
	progressTableDataSaPart1_5(idx);
}
function progressTableDataSaPart1_1(idx) {
	var $tbl = $("#chartTbl1_1");
	var item = {count:0, sa:0, file:0, na:0, prg:0, cnt:0};
	var html = "";

	$tbl.find("tbody").empty();
	$tbl.find(".criterion").remove();
	$tbl.find(".items").empty();
    if($.isEmptyObject(jsonData[idx][0])) return false;

	var cps = [];
	var cp_val;

	$.each(jsonData[idx][2], function(k, v) {
		$tbl.find(".total").before("<th class=\"criterion\">"+v.uscKindTitle+"</th>");
		cps.push(v.uscKindCod);
	});

	$.each(jsonData[idx][0], function(k1, v1) {
		html = "<tr><td rowspan='"+v1.dep3.length+"'>" + v1.title + "</td>";
		$.each(v1.dep3, function(k2, v2) {
			item.count += 1;
			item.sa += parseFloat(v2.sa);
			item.cnt += v2.cnt;
			if(k2 > 0) html = "<tr>";
			html += "<td>" + v2.title + "</td>";
			sa = v2.sa_edit?v2.sa_edit+"<span>("+v2.sa_cal+")</span>":v2.sa;
			html += "<td class='edit_sa' dept='"+v2.code+"' sa_cal='"+v2.sa_cal+"' sa_edit='"+v2.sa_edit+"'>"+sa+"</td>";
			$.each(cps, function(k3, v3) {
				cp_val = 0;
				if(v3=="inc"){
					item.file += v2.file;
					cp_val = v2.file;
				}else if(v3=="na"){
					item.na += v2.na;
					cp_val = v2.na;
				}else if(v3=="prog"){
					item.prg += v2.prg;
					cp_val = v2.prg;
				}
				html += "<td>" + cp_val + "</td>";
			});
			html += "<td class=\"last\">" + v2.cnt + "</td>";
			html += "</tr>";
			$tbl.find("tbody").append(html);
		});
	});

	$tbl.find(".items.item_sa").text(dec_point(item.sa/item.count));

	$.each(cps, function(k, v) {
		cp_val = 0;
		if(v=="inc"){
			cp_val = dec_point(item.file/item.count);
		}else if(v=="na"){
			cp_val = dec_point(item.na/item.count);
		}else if(v=="prog"){
			cp_val = dec_point(item.prg/item.count);
		}
		$tbl.find(".items.item_cnt").before("<th class=\"criterion\">" +cp_val+"</th>");
	});

	$tbl.find(".items.item_cnt").text(dec_point(item.cnt/item.count));
}
function progressTableDataSaPart1_2(idx) {
	var $tbl = $("#chartTbl1_2");
	var item = {count:0, cnt:0, cnt_y:0, cnt_up:0, cnt_lp:0, cnt_n:0, cnt_na:0, prg_per:0};
	var html = "";
	var prg_per = 0;

	$tbl.find("tbody").empty();
	$tbl.find(".items").empty();
    if($.isEmptyObject(jsonData[idx][0])) return false;

	$.each(jsonData[idx][0], function(k1, v1) {
		html = "<tr><td rowspan='"+v1.dep3.length+"'>" + v1.title + "</td>";
		$.each(v1.dep3, function(k2, v2) {
			item.cnt += v2.cnt;
			item.cnt_y += v2.cnt_y;
			item.cnt_up += v2.cnt_up;
			item.cnt_lp += v2.cnt_lp;
			item.cnt_n += v2.cnt_n;
			item.cnt_na += v2.cnt_na;
			prg_per = v2.cnt-v2.cnt_na==0?0:dec_point(((v2.cnt_y*100)+(v2.cnt_up*70)+(v2.cnt_lp*30))/(v2.cnt-v2.cnt_na));
			item.prg_per += prg_per;
			item.count += 1;
			if(k2 > 0) html = "<tr>";
			html += "<td>" + v2.title + "</td>";
			html += "<td>" + v2.cnt + "</td>";
			html += "<td>" + v2.cnt_y + "</td>";
			html += "<td>" + v2.cnt_up + "</td>";
			html += "<td>" + v2.cnt_lp + "</td>";
			html += "<td>" + v2.cnt_n + "</td>";
			html += "<td>" + v2.cnt_na + "</td>";
			html += "<td class=\"last\">" + prg_per + "% </td>";
			html += "</tr>";
			$tbl.find("tbody").append(html);
		});
	});
	$tbl.find(".items.count").text(item.cnt);
	$tbl.find(".items.cnt_y").text(item.cnt_y);
	$tbl.find(".items.cnt_up").text(item.cnt_up);
	$tbl.find(".items.cnt_lp").text(item.cnt_lp);
	$tbl.find(".items.cnt_n").text(item.cnt_n);
	$tbl.find(".items.cnt_na").text(item.cnt_na);
	$tbl.find(".items.total_prg").text(dec_point(item.prg_per/item.count));

	$tbl.find(".items.prg_y").text(dec_point(item.cnt_y/(item.cnt_y+item.cnt_up+item.cnt_lp+item.cnt_n+item.cnt_na)*100));
	$tbl.find(".items.prg_up").text(dec_point(item.cnt_up/(item.cnt_y+item.cnt_up+item.cnt_lp+item.cnt_n+item.cnt_na)*100));
	$tbl.find(".items.prg_lp").text(dec_point(item.cnt_lp/(item.cnt_y+item.cnt_up+item.cnt_lp+item.cnt_n+item.cnt_na)*100));
	$tbl.find(".items.prg_n").text(dec_point(item.cnt_n/(item.cnt_y+item.cnt_up+item.cnt_lp+item.cnt_n+item.cnt_na)*100));
	$tbl.find(".items.prg_na").text(dec_point(item.cnt_na/(item.cnt_y+item.cnt_up+item.cnt_lp+item.cnt_n+item.cnt_na)*100));
}
function progressTableDataSaPart1_3(idx) 	{
	var $tbl = $("#chartTbl1_3");
	var item = {count:0, counts:0, prg_sum:0, per:0, y:0, up:0, lp:0, n:0, na:0};
	var html = "";
	var dep_cnt = 0;

	$tbl.find("tbody").empty();
	$tbl.find(".items").empty();
    if($.isEmptyObject(jsonData[idx][1])) return false;

	$.each(jsonData[idx][1], function(k, v) {
		var nPrg = dec_point(v.prg_sum/(v.count-v.cnt_na));
		html = "<tr><td>" + v.title + "</td>";
		html += "<td>" + nPrg + "</td>";
		html += "<td>" + v.count + "</td>";
		html += "<td>" + v.cnt_y + "</td>";
		html += "<td>" + v.cnt_up + "</td>";
		html += "<td>" + v.cnt_lp + "</td>";
		html += "<td>" + v.cnt_n + "</td>";
		html += "<td class=\"last\">" + v.cnt_na + "</td>";
		html += "</tr>";
		$tbl.find("tbody").append(html);
		item.count++;
		item.prg_sum += v.prg_sum;
		item.per += v.prg;
		item.counts += v.count;
		item.y += v.cnt_y;
		item.up += v.cnt_up;
		item.lp += v.cnt_lp;
		item.n += v.cnt_n;
		item.na += v.cnt_na;
		if(!dep_cnt) dep_cnt = v.dep_cnt;
	});
	//$tbl.find(".prg").text(dec_point(item.prg_sum/(dep_cnt*item.counts-item.na)));
	$tbl.find(".prg").text(dec_point(item.prg_sum/(item.counts-item.na)));
	$tbl.find(".counts").text(item.counts);
	$tbl.find(".cnt_y").text(item.y);
	$tbl.find(".cnt_up").text(item.up);
	$tbl.find(".cnt_lp").text(item.lp);
	$tbl.find(".cnt_n").text(item.n);
	$tbl.find(".cnt_na").text(item.na);

	$tbl.find(".per_y").text(dec_point(item.y/(item.y+item.up+item.lp+item.n+item.na)*100));
	$tbl.find(".per_up").text(dec_point(item.up/(item.y+item.up+item.lp+item.n+item.na)*100));
	$tbl.find(".per_lp").text(dec_point(item.lp/(item.y+item.up+item.lp+item.n+item.na)*100));
	$tbl.find(".per_n").text(dec_point(item.n/(item.y+item.up+item.lp+item.n+item.na)*100));
	$tbl.find(".per_na").text(dec_point(item.na/(item.y+item.up+item.lp+item.n+item.na)*100));
}
function progressTableDataSaPart1_4(idx) {
	var $tbl = $("#chartTbl1_4");
	var html = "", col="";
	var dept ="";
	$tbl.find("tbody").empty();
	$tbl.find(".dept").remove();
	if($.isEmptyObject(jsonData[idx][1])) return false;

	$.each(jsonData[idx][1], function(k, v) {
		html += "<th class=\"dept "+(k==jsonData[idx][1].length-1?"last":"")+"\">"+v.title+"</th>";
		col += "<col class=\"dept\" width=\""+(88/jsonData[idx][1].length)+"%\" />";
	});
	$tbl.find("thead tr").append(html);
	$tbl.find("colgroup").append(col);

	$.each(jsonData[idx][0], function(k1, v1) {
		$.each(v1.dep3, function(k2, v2) {
			dep_code = v2.code;
			html = "<tr><td>" + v2.title + "</td>";
			$.each(jsonData[idx][1], function(k3, v3) {
				dept = v3["dep"][dep_code];
				var prg = 0;
				if(dept!=undefined){
					if(dept.count==0){
						prg = "N/A";
					}else{
						prg = dec_point(dept.prg/dept.count);
					}
				}
				html += "<td class=\"dept "+(k3==jsonData[idx][1].length-1?"last":"")+"\">" + prg +"</td>";
			});
			html += "</tr>";
			$tbl.find("tbody").append(html);
		});
	});
}
function progressTableDataSaPart1_5() {
	var $tbl = $("#chartTbl1_5");
	$tbl.find("tbody, tfoot").empty();
	$tbl.find("thead tr th.year").remove();
    if($.isEmptyObject(jsonData[1])) return ;

	var year = [];
	$.each(jsonData[1][3][2][1], function(k, v) {
		year.push(v.ummManCyl);
		$tbl.find("thead tr").append("<th class='year"+(jsonData[1][3][2][1].length-1==k?" last":"")+"'>"+v.ummManTle+"<br/>점검레벨</th>");
	});

	var html = "";
	var sa_level = "";
	$.each(jsonData[1][3][1], function(k1, v1) {
		html = '<tr><td>' + v1[1] + '</td>';
		$.each(year, function(k2, v2){
			sa_level = "0";
			if(jsonData[1][3][0][v2]!=undefined&&jsonData[1][3][0][v2][v1[0]]!=undefined){
				sa_level = jsonData[1][3][0][v2][v1[0]];
			}
			html += "<td "+(year.length-1==k2?"class='last'":"")+">"+dec_point(sa_level)+"</td>";
		});
		$tbl.find("tbody").append(html);
	});
}

function progressTableDataSaPart2(idx) {
	progressTableDataSaPart2_1(idx);
	progressTableDataSaPart2_2(idx);
}
function progressTableDataSaPart2_1(idx) {
	var $tbl = $("#chartTbl2_1");
	var html = "";

	$tbl.find("tbody").empty();
	if($.isEmptyObject(jsonData[idx][0])) return false;

	$.each(jsonData[idx][1], function(k0, v0) {
		var lv1=0;
		html = "<tr><td rowspan=\""+v0.count+"\">"+v0.code+". "+v0.title+"</td>";
		$.each(v0.lv1, function(k1, v1) {
			if(lv1!=0) html += "<tr>";
			html += "<td rowspan=\""+v1.count+"\">"+v1.code+" "+v1.title+"</td>";
			var lv2=0;
			$.each(v1.lv2, function(k2, v2) {
				var per = "N/A";
				if(v2.prg_cnt>0){
					per = dec_point(v2.prg_sum/v2.prg_cnt);
				}
				if(lv2!=0) html += "<tr>";
				html += "<td>"+v2.code+" "+v2.title+"</td>";
				html += "<td class=\"last\">"+per+"</td>";
				html += "</tr>";
				$tbl.find("tbody").append(html);
				html = "";
				lv2++;
			});
			lv1++;
		});
	});
}
function progressTableDataSaPart2_2(idx) {
	var $tbl = $("#chartTbl2_2");
	var html = "";
	var items = {cnt_y:0, cnt_up:0, cnt_lp:0, cnt_n:0, cnt_na:0, total:0};
	$tbl.find("tbody").empty();
	$tbl.find(".items").empty();

	if($.isEmptyObject(jsonData[idx][0])) return false;

	$.each(jsonData[idx][1], function(k0, v0) {
		$.each(v0.lv1, function(k1, v1) {
			var total = v1.cnt_y+v1.cnt_up+v1.cnt_lp+v1.cnt_n+v1.cnt_na;
			html = "<tr>";
			html += "<td>"+v1.code+" "+v1.title+"</td>";
			html += "<td>"+v1.cnt_y+"</td>";
			html += "<td>"+v1.cnt_up+"</td>";
			html += "<td>"+v1.cnt_lp+"</td>";
			html += "<td>"+v1.cnt_n+"</td>";
			html += "<td>"+v1.cnt_na+"</td>";
			html += "<td class=\"last\">"+total+"</td>";
			html += "</tr>";
			$tbl.find("tbody").append(html);
			items.cnt_y += v1.cnt_y;
			items.cnt_up += v1.cnt_up;
			items.cnt_lp += v1.cnt_lp;
			items.cnt_n += v1.cnt_n;
			items.cnt_na += v1.cnt_na;
			items.total += total;
		});
	});

	$tbl.find(".item_y").text(items.cnt_y);
	$tbl.find(".item_up").text(items.cnt_up);
	$tbl.find(".item_lp").text(items.cnt_lp);
	$tbl.find(".item_n").text(items.cnt_n);
	$tbl.find(".item_na").text(items.cnt_na);
	$tbl.find(".item_total").text(items.total);
}

function progressChartInfraMp(idx, url, chartType, params) {
	var chartData = Array();
	jsonData = new Array();
	$.getJSON(url, params, function (json) {
		jsonData[idx] = json.result;
	});
    $("#chartContainer" + idx).show();

	if(idx==1){
		progressTableDataInfraMpPart1_1(idx);
		progressTableDataInfraMpPart1_2(idx);
        if(chartInstance[0]) chartInstance[0].destroy();
        if(chartInstance[1]) chartInstance[1].destroy();
	}else if(idx==2){
		progressTableDataInfraMpPart2(idx);
        if(chartInstance[2]) chartInstance[2].destroy();
	}else{
		return ;
	}

	if(idx==1){
		if($.isEmptyObject(jsonData[idx])){
			blankChart("chart1_1");
			blankTable("chartTbl1_1");
			blankChart("chart1_2");
			blankTable("chartTbl1_2");
			return false;
		}
		var data = jsonData[idx][0].data;
		data.push(jsonData[idx][0].avg);
		chartData[0] = {
			labels : $.map(data, function(el) { return el.title }),
			datasets : [
				{
					label: "정보보호수준(%)",
					borderWidth: 1,
					borderColor : colorChart,
					backgroundColor : colorChart,
					data : $.map(data, function(el) { return el.value })
				}
			]
		};
		var ci = 0;
		chartData[1] = {
			labels: $.map(jsonData[1][1].data, function (el) {
				return el.title
			}),
			datasets: $.map(jsonData[1][1].bcy, function (el) {
				var title = el.ummManTle;
				var cData = {
					label: title,
					fill: false,
					lineTension: 0.1,
					backgroundColor: colorChart[ci],
					borderColor: colorChart[ci],
					data: $.map(jsonData[1][1].data, function (el2) {
						return el2.value[el.ummManCyl]==undefined?0:el2.value[el.ummManCyl];
					})
				};
				ci++;
				return cData;
			})
		};

	}else if(idx==2){
		if($.isEmptyObject(jsonData[idx][1])){
			blankChart("chart2");
			blankTable("chartTbl2");
			return false;
		}
		chartData[2]  = {
			labels : $.map(jsonData[idx][1], function(el) { return el.title }),
			datasets : [
				{
					label: "보안수준(%)",
					borderColor: 'rgba(23,120,200,1)',
					backgroundColor: 'rgba(54,162,235,0.5)',
					pointBackgroundColor: 'rgba(54,162,235,1)',
					pointBorderColor: 'rgba(23,120,200,1)',
					data : $.map(jsonData[idx][1], function(el) { return el.level })
				}

			]
		};
	}


    var duration = (params.duration==undefined?1000:params.duration);
	if(idx==1){
		document.getElementById("chart1_1").height = 300;
		chartInstance[0] = new Chart($("#chart1_1"), {
			type: "bar",
			data: chartData[0],
			responsive: true,
			options: {
                animation: {duration: duration},
				title: {
					display: true,
					fontSize: 16,
					fontFamily: "NG"
				},
				legend: { display:false, position: "bottom" },
				tooltips: { mode: "label" },
				scales: {
					xAxes: [{
						stacked: true,
						categoryPercentage: 0.3,
						gridLines: { display: false }
					}],
					yAxes: [{
						stacked: true,
						ticks: {
							max: 100,
							userCallback: function(value, index, values) {
								return getNumeric(value);
							}
						}
					}]
				}
			}
		});
		document.getElementById("chart1_2").height = 300;
		chartInstance[1] = new Chart($("#chart1_2"), {
			type: 'line',
			data: chartData[1],
			responsive: true,
			options: {
				animation: {duration: duration},
				title: {
					display: true,
					fontSize: 16,
					fontFamily: 'NG'
				},
				legend: {position: 'bottom'},
				tooltips: {mode: 'label'},
				scales: {
					xAxes: [{
						gridLines: {display: false}
					}],
					yAxes: [{
						ticks: {
							min:0,
							max:100,
							stepSize: 10,
							userCallback: function (value, index, values) {
								return getNumeric(value);
							}
						}
					}]
				},
				layout: {
					padding: {
						left: 100,
						right: 50,
						top: 100,
						bottom: 50
					}
				}
			}
		});
	}else{
		document.getElementById("chart2").height = 500;
		chartInstance[2] = new Chart($("#chart2"), {
			type: "radar",
			data: chartData[2],
			options: {
                animation: {duration: duration},
				title: {
					display: true,
					fontSize: 16,
					text: ""
				},
				legend: {display:true, position: "bottom" },
				tooltips: {mode:"label"},
				scale: {
					ticks: {
						beginAtZero: true,
						max: 100
					}
				}
			}
		});
	}
}
function progressTableDataInfraMpPart1_1(idx) {
	var $tbl = $("#chartTbl1_1");
	$tbl.find("tbody, tfoot").empty();
	if($.isEmptyObject(jsonData[1][0])) return false;
	$.each(jsonData[1][0].data, function(k, v) {
		$tbl.find("tbody").append('<tr><td>' + v.title + '</td><td class="last">' + v.value + '</td></tr>');
	});
	$tbl.find("tfoot").append('<tr><td>' + jsonData[1][0].avg.title + '</td><td class="last">' + jsonData[1][0].avg.value + '</td></tr>');
}
function progressTableDataInfraMpPart1_2(idx) {
	var html = "";
	$tbl = $("#chartTbl1_2");
	$tbl.find("tbody, tfoot").empty();
	$tbl.find("thead tr th.year").remove();
	if($.isEmptyObject(jsonData[1][1])) return false;
	var year = [];
	$.each(jsonData[1][1].bcy, function(k, v) {
		year.push(v.ummManCyl);
		$tbl.find("thead tr").append("<th class='year"+(jsonData[1][1].bcy.length-1==k?" last":"")+"'>"+v.ummManTle+"<br/>정보보호수준(%)</th>");
	});

	$.each(jsonData[1][1].data, function(k1, v1) {
		html = '<tr><td>' + v1.title + '</td>';
		$.each(year, function(k2, v2){
			html += "<td "+(year.length-1==k2?"class='last'":"")+">"+(v1.value[v2]?v1.value[v2]:0)+"</td>";
		});
		$tbl.find("tbody").append(html);
	});
}
function progressTableDataInfraMpPart2(idx) {
	var $tbl = $("#chartTbl" + idx);
	$tbl.find("tbody, tfoot").empty();
	var objLv1 = new Object();

	for(var i=0; i<jsonData[idx][0].length; i++){
		objLv1[jsonData[idx][0][i].code] = {"title":jsonData[idx][0][i].title, "level":jsonData[idx][0][i].level, "count":jsonData[idx][0][i].count, "points":jsonData[idx][0][i].points, "aswPoints":jsonData[idx][0][i].aswPoints};
	}
	var html = "";
	var lv1 = "";
	var code ="";
	var lv2Cnt =0;
	var item = [{total:0, not_na:0, grade_1:0, grade_2:0, grade_3:0, result_o:0, result_x:0, result_p:0, result_na:0}, {total:0, not_na:0, grade_1:0, grade_2:0, grade_3:0, result_o:0, result_x:0, result_p:0, result_na:0}];

	$.each(jsonData[idx][1], function(k, v) {
		html = "<tr>";
		code = v.code.split(".")[0];
		if(lv1!=code){
			html += "<td style=\"font-weight:bold\" rowspan=\""+(objLv1[code].count+1)+"\">"+objLv1[code].title+"</td>";
			lv1=code;
		}
		item[code-1].total += v.total;
		item[code-1].not_na += v.not_na;
		item[code-1].grade_1 += v.grade_1;
		item[code-1].grade_2 += v.grade_2;
		item[code-1].grade_3 += v.grade_3;
		item[code-1].result_o += v.result_o;
		item[code-1].result_x += v.result_x;
		item[code-1].result_p += v.result_p;
		item[code-1].result_na += v.result_na;

		html += "<td>"+v.title+"</td><td>"+v.total+"</td><td>"+v.not_na+"</td>";
		html += "<td>"+v.grade_1+"</td><td>"+v.grade_2+"</td><td>"+v.grade_3+"</td>";
		html += "<td>"+v.result_o+"</td><td>"+v.result_x+"</td><td>"+v.result_p+"</td><td>"+v.result_na+"</td>";
		html += "<td class=\"last\">"+v.level+"</td>";
		html += "</tr>";
		lv2Cnt++;
		if(lv2Cnt==objLv1[code].count){
			html += "<tr style=\"font-weight:bold\"><td>합계</td><td>"+item[code-1].total+"</td><td>"+item[code-1].not_na+"</td><td>"+item[code-1].grade_1+"</td>";
			html += "<td>"+item[code-1].grade_2+"</td><td>"+item[code-1].grade_3+"</td><td>"+item[code-1].result_o+"</td><td>"+item[code-1].result_x+"</td>";
			html += "<td>"+item[code-1].result_p+"</td><td>"+item[code-1].result_na+"</td><td class=\"last\">"+objLv1[code].level+"</td></tr>";
			lv2Cnt=0;
		}
		if(k==jsonData[idx][1].length-1){
			html += "<tr style=\"font-weight:bold\"><td colspan=\"2\">합계</td><td>"+Number(item[0].total+item[1].total)+"</td>";
			html += "<td>"+Number(item[0].not_na+item[1].not_na)+"</td><td>"+Number(item[0].grade_1+item[1].grade_1)+"</td>";
			html += "<td>"+Number(item[0].grade_2+item[1].grade_2)+"</td><td>"+Number(item[0].grade_3+item[1].grade_3)+"</td>";
			html += "<td>"+Number(item[0].result_o+item[1].result_o)+"</td><td>"+Number(item[0].result_x+item[1].result_x)+"</td>";
			html += "<td>"+Number(item[0].result_p+item[1].result_p)+"</td><td>"+Number(item[0].result_na+item[1].result_na)+"</td>";
			var points=0;
			var aswPoints=0;
			for(var oi=0; oi<Object.keys(objLv1).length; oi++){
				points += Number(objLv1[oi+1].points);
				aswPoints += objLv1[oi+1].aswPoints;
			}
			html += "<td class=\"last\">"+dec_point((points-aswPoints)/points*100)+"</td>";
			html += "</tr>";
		}
		$tbl.find("tbody").append(html);
	});
}

function progressChartInfraLa(idx, url, chartType, params) {
	var chartData = Array();
	jsonData = new Array();
	$.getJSON(url, params, function (json) {
		jsonData[idx] = json.result;
	});
    $("#chartContainer" + idx).show();

	$(".talbeSubArea").hide();
	if(idx==1){
		if (chartInstance[0]) chartInstance[0].destroy();
		if (chartInstance[1]) chartInstance[1].destroy();
		progressTableDataInfraLaPart1(idx);
	}else if(idx==2){
		if (chartInstance[2]) chartInstance[2].destroy();
		progressTableDataInfraLaPart2(idx);
	}else{
		return ;
	}

    if($.isEmptyObject(jsonData[idx][1])){
		if(idx==1){
			blankChart("chart1_1");
			blankChart("chart1_2");
			blankTable("chartTbl1_1");
		}else if(idx==2){
			blankChart("chart2");
			blankTable("chartTbl2");
		}
    	return false;
	}

    if(idx==1) {
		chartData[0] = {
			labels : $.map(jsonData[idx][0], function(el) { return el.title }),
			datasets : [
				{
					label: "백분률(%)",
					backgroundColor: 'rgba(23,120,200,0.5)',
					borderColor: colorChart[0],
					pointBackgroundColor: colorChart[0],
					pointBorderColor: colorChart[0],
					data : $.map(jsonData[idx][0], function(el) { return el.per })
				}
			]
		};
		chartData[1] = {
			labels: $.map(jsonData[idx][0], function (el) {
				return el.title
			}),
			datasets: [
				{
					label: "성숙도단계",
					backgroundColor: 'rgba(255,79,66,0.5)',
					borderColor: colorChart[1],
					pointBackgroundColor: colorChart[1],
					pointBorderColor: colorChart[1],
					data: $.map(jsonData[idx][0], function (el) {
						return el.level
					})
				}
			]
		};
	}else if(idx==2) {
		var ci = 0;
        chartData[2] = {
            labels: $.map(jsonData[idx][1], function (el) {
                return el.code + ". " + el.title
            }),
            datasets: $.map(jsonData[idx][0], function (el) {
                var title = el.ummManTle;
                var cData = {
                    label: title,
                    fill: false,
                    lineTension: 0.1,
                    backgroundColor: colorChart[ci],
                    borderColor: colorChart[ci],
                    data: $.map(jsonData[idx][1], function (el2) {
                        return el2.level[title]==undefined?0:el2.level[title];
                    })
                };
                ci++;
                return cData;
            })
        };
    }

    var duration = (params.duration==undefined?1000:params.duration);
    if(idx==1){
		document.getElementById("chart1_1").height = 500;
		document.getElementById("chart1_2").height = 500;
		chartInstance[0] = new Chart($("#chart1_1"), {
			type: "radar",
			data: chartData[0],
			responsive: false,
			options: {
                animation: {duration: duration},
				title: {
					display: true,
					fontSize: 16,
					text: ""
				},
				legend: {display:true, position: "bottom" },
				tooltips: {mode:"label"},
				scale: {
					ticks: {
						beginAtZero: true,
						max: 100
					}
				}
			}
		});
		chartInstance[1] = new Chart($("#chart1_2"), {
			type: "radar",
			data: chartData[1],
			options: {
                animation: {duration: duration},
				title: {
					display: true,
					fontSize: 16,
					text: ""
				},
				legend: {display:true, position: "bottom" },
				tooltips: {mode:"label"},
				scale: {
					ticks: {
						beginAtZero: true,
						max: 5
					}
				}
			}
		});
	}else if(idx==2){
		document.getElementById("chart2").height = 500;
		chartInstance[2] = new Chart($("#chart2"), {
			type: 'line',
			data: chartData[2],
			responsive: true,
			options: {
                animation: {duration: duration},
				title: {
					display: true,
					fontSize: 16,
					fontFamily: 'NG'
				},
				legend: {position: 'bottom'},
				tooltips: {mode: 'label'},
				scales: {
					xAxes: [{
						gridLines: {display: false}
					}],
					yAxes: [{
						ticks: {
							min:0,
							max:5,
                            stepSize: 1,
							userCallback: function (value, index, values) {
								return getNumeric(value);
							}
						}
					}]
				}
			}
		});
	}
}
function progressTableDataInfraLaPart1(idx) {
	var $tbl = $("#chartTbl1_1");
	$tbl.find("tbody, tfoot").empty();
	var html = "";
	var item = {items:0, answer:0, points:0, level:0};
	var objLen = Object.keys(jsonData[idx][0]).length;
	var len = 0;
	$.each(jsonData[idx][0], function(k, v) {
		html = "<tr><td class=\"lv1\" code=\""+k+"\">" + v.title + "</td><td>" + v.items + "</td><td>" + v.answer + "</td>";
		html += "<td>" + v.points + "</td><td>" + v.per + "</td><td class=\"last\">" + v.level + " 단계</td></tr>";
		item.items += v.items;
		item.answer += v.answer;
		item.points += v.points;
		item.level += parseInt(v.level);
		len++;
		$tbl.find("tbody").append(html);
		if(objLen == len){
			html = "<tr><td></td><td>" + item.items + "</td><td>" + item.answer + "</td><td>" + item.points + "</td>";
			html += "<td>" + dec_point(item.answer*100/item.points)+"" + "</td>";
			html += "<td class=\"last\">" + dec_point(item.level/len) + "</td></tr>";
			$tbl.find("tfoot").append(html);
			$tbl.find("td.lv1").click(function(){
				showInfraLaLv2($(this).attr("code"));
			});
		}
	});
}
function progressTableDataInfraLaPart2(idx) {
    var $tbl = $("#chartTbl2");
    $tbl.find("tbody").empty();
    $tbl.find("colgroup").empty();
    $tbl.find(".years").remove();
    $tbl.find(".year_last").text("");

    $tbl.find("colgroup").append("<col width=\"10%\" />");
    $tbl.find("colgroup").append("<col width=\"*\" />");
    $tbl.find("colgroup").append("<col width=\"12%\" />");

    var html = "";
    var year = [];
    $.each(jsonData[idx][0], function(k, v) {
        year.push(v.ummManTle);
        $tbl.find("colgroup").append("<col width=\""+((100-50)/jsonData[idx][0].length)+"%\" />");
        $tbl.find("thead tr").append("<th class=\"years\">"+year[year.length-1]+"<br/>평가결과</th>");
    });
    $tbl.find("colgroup").append("<col width=\"12%\" />");
    $tbl.find("thead tr").append("<th class=\"years last\">"+year[year.length-1]+"<br/>통제분야별 성숙도</th>");


    var lv1=0;
    $.each(jsonData[idx][1], function(k1, v1) {
        var lv2=0;
        html = "<tr><td rowspan=\""+v1.count+"\">"+v1.code+". "+v1.title+"</td>";
        $.each(v1.lv2, function(k2, v2) {
            if(lv2!=0) html += "<tr>";
            html += "<td rowspan=\""+v2["lv3"].length+"\">"+v2.code+" "+v2.title+"</td>";
            var lv3=0;
            $.each(v2.lv3, function(k3, v3) {
                if(lv3!=0) html += "<tr>";
                html += "<td>"+v3.code+"</td>";
                $.each(year, function(k4,v4){
                    html += "<td>"+(v3[v4]?v3[v4]:0)+"</td>";
                });
                if(lv1==0){
                    html += "<td class=\"last\" rowspan=\""+v1.count+"\">"+v1.level[year[year.length-1]]+"</td></tr>";
                    lv1++;
                }
                html += "</tr>";
                $tbl.find("tbody").append(html);
                html = "";
                lv3++;
            });
            lv2++;
        });
        lv1=0;
    });
}
function showInfraLaLv2(code){
	$("#chartTbl1_2 tbody").empty();

	var html = "";
	var data = jsonData[1][1]
	var codeLv2 = {};
	var rows = 0;
	var level = 6;
	for(var i=0; i<data.length; i++){
		if(code==data[i]["codeLv1"]) {
			if(codeLv2[data[i]["codeLv2"]]==undefined){
				codeLv2[data[i]["codeLv2"]] = {count:0, answer:0};
			}
			codeLv2[data[i]["codeLv2"]].count+=1;
			codeLv2[data[i]["codeLv2"]].answer+=parseInt(data[i]["answer"]);
			level = parseInt(data[i]["answer"])<level?parseInt(data[i]["answer"]):level;
			rows++;
		}
	}

	var preLv2 = "";
	$.each(jsonData[1][1], function(k, v) {
		if(code==v.codeLv1){
			html = "<tr>";
			if(preLv2 != v.codeLv2){
				html += "<td rowspan=\""+codeLv2[v.codeLv2].count+"\">"+v.codeLv2+" "+v.title+"</td>";
			}
			html += "<td>"+v.codeLv3+"</td><td>"+v.answer+"</td>";
			if(preLv2 != v.codeLv2) {
				html += "<td rowspan=\""+codeLv2[v.codeLv2].count+"\">" + codeLv2[v.codeLv2].answer + "</td>";
				html += "<td rowspan=\""+codeLv2[v.codeLv2].count+"\">" + codeLv2[v.codeLv2].count + "</td>";
				if(preLv2=="") html += "<td class=\"last\" rowspan=\""+rows+"\">" + level + "</td>";
				preLv2 = v.codeLv2;
			}
			html += "</tr>";
			$("#chartTbl1_2 tbody").append(html);
		}
	});

	$(".talbeSubArea").show();
}

function progressChartMsit(idx, url, chartType, params) {
	var chartData = Array();
	jsonData = new Array();
	$.getJSON(url, params, function(json) {
		jsonData[idx] = json.result;
	});
    $("#chartContainer" + idx).show();

	if(idx==1){
		if(chartInstance[1]) chartInstance[1].destroy();
		if(chartInstance[2]) chartInstance[2].destroy();
		progressTableDataMsitPart1_1();
		progressTableDataMsitPart1_2();
	}else if(idx==2){
		if(chartInstance[3]) chartInstance[3].destroy();
		progressTableDataMsitPart2(idx);
	}else{
		return ;
	}
    if($.isEmptyObject(jsonData[idx][0])){
		if(idx==1){
			blankChart("chart1_1");
			blankTable("chartTbl1_1");
			blankChart("chart1_2");
			blankTable("chartTbl1_2");
		}else if(idx==2){
			blankChart("chart2");
			blankTable("chartTbl2");
		}
    	return false;
	}
	if(idx==1) {
		chartData[0] = {
			labels: $.map(jsonData[1][0], function (el) {
				return el.title
			}),
			datasets: [
				{
					label: "준수율(%)",
					borderWidth: 1,
					borderColor: colorChart,
					backgroundColor: colorChart,
					data: $.map(jsonData[1][0], function (el) {
						return el.value
					})
				}
			]
		};
		var ci = 0;
		chartData[1] = {
			labels: $.map(jsonData[1][1], function (el) {
				return el.title
			}),
			datasets: $.map(jsonData[1][2], function (el) {
				var title = el.ummManTle;
				var cData = {
					label: title,
					fill: false,
					lineTension: 0.1,
					backgroundColor: colorChart[ci],
					borderColor: colorChart[ci],
					data: $.map(jsonData[1][1], function (el2) {
						return el2.value[el.ummManCyl]==undefined?0:el2.value[el.ummManCyl];
					})
				};
				ci++;
				return cData;
			})
		};
	}else{
		chartData[0]  = {
			labels : $.map(jsonData[2][0], function(el) { return el.title }),
			datasets : [
				{
					label: "준수율(%)",
					backgroundColor: "rgba(90, 174, 255, 0.5)",
					borderColor: "rgba(90, 174, 255, 1)",
					pointBackgroundColor: "rgba(90, 174, 255, 1)",
					pointBorderColor: "rgba(90, 174, 255, 1)",
					pointHoverBackgroundColor: "#fff",
					pointHoverBorderColor: "#fff",
					data : $.map(jsonData[2][0], function(el) { return el.avg })
				}
			]
		};
	}

    var duration = (params.duration==undefined?1000:params.duration);

    if(idx==1){
		document.getElementById("chart1_1").height = 500;
		chartInstance[1] = new Chart($("#chart1_1"), {
			type: "bar",
			data: chartData[0],
			responsive: true,
			options: {
				animation: {duration: duration},
				title: {
					display: true,
					fontSize: 16,
					fontFamily: "NG"
				},
				legend: { display:false, position: "bottom" },
				tooltips: { mode: "label" },
				scales: {
					xAxes: [{
						stacked: true,
						categoryPercentage: 0.3,
						gridLines: { display: false }
					}],
					yAxes: [{
						stacked: true,
						ticks: {
							max: 100,
							userCallback: function(value, index, values) {
								return getNumeric(value);
							}
						}
					}]
				}
			}
		});

		document.getElementById("chart1_2").height = 500;
		chartInstance[2] = new Chart($("#chart1_2"), {
			type: 'line',
			data: chartData[1],
			responsive: true,
			options: {
				animation: {duration: duration},
				title: {
					display: true,
					fontSize: 16,
					fontFamily: 'NG'
				},
				legend: {position: 'bottom'},
				tooltips: {mode: 'label'},
				scales: {
					xAxes: [{
						gridLines: {display: false}
					}],
					yAxes: [{
						ticks: {
							min:0,
							max:100,
							stepSize: 10,
							userCallback: function (value, index, values) {
								return getNumeric(value);
							}
						}
					}]
				}
			}
		});
	}else{
		document.getElementById("chart2").height = 500;
		chartInstance[3] = new Chart($("#chart2"), {
			type: "radar",
			data: chartData[0],
			options: {
				animation: {duration: duration},
				title: {
					display: true,
					fontSize: 16,
					text: ""
				},
				legend: {display:true, position: "bottom" },
				tooltips: {mode:"label"},
				scale: {
					ticks: {
						beginAtZero: true,
						max: 100
					}
				}
			}
		});
	}
}
function progressTableDataMsitPart1_1() {
	var $tbl = $("#chartTbl1_1");
	$tbl.find("tbody, tfoot").empty();
	var html = "";

	$.each(jsonData[1][0], function(k, v) {
		html = '<tr><td>' + v.title + '</td><td class="last">' + v.value + '</td></tr>';
		if (v.code != "avg"){
			$tbl.find("tbody").append(html);
		}else {
			$tbl.find("tfoot").append(html);
		}
	});
}
function progressTableDataMsitPart1_2() {
	var $tbl = $("#chartTbl1_2");
    $tbl.find("tbody, tfoot").empty();
    $tbl.find("thead tr th.year").remove();

	var year = [];
	$.each(jsonData[1][2], function(k, v) {
		year.push(v.ummManCyl);
		$tbl.find("thead tr").append("<th class='year"+(jsonData[1][2].length-1==k?" last":"")+"'>"+v.ummManTle+"<br/>준수율(%)</th>");
	});


	var html = "";
	$.each(jsonData[1][1], function(k1, v1) {
		html = '<tr><td>' + v1.title + '</td>';
		$.each(year, function(k2, v2){
			html += "<td "+(year.length-1==k2?"class='last'":"")+">"+(v1.value[v2]?v1.value[v2]:0)+"</td>";
		});
		$tbl.find("tbody").append(html);
	});
}

function progressTableDataMsitPart2(idx) {
	var $tbl = $("#chartTbl" + idx);
	$tbl.find("tbody, tfoot").empty();
	var objLv2 = new Object();

	if($.isEmptyObject(jsonData[idx])||$.isEmptyObject(jsonData[idx][1])) return false;

	for(var i=0; i<jsonData[idx][0].length; i++){
		objLv2[jsonData[idx][0][i].code] = {"title":jsonData[idx][0][i].title, "avg":jsonData[idx][0][i].avg, "count":jsonData[idx][0][i].count};
	}

	var html = "";
	var lv2 = "";
	$.each(jsonData[idx][1], function(k, v) {
		html = "<tr>";
		if(lv2!=v.lv2Code){
			html += "<td rowspan=\""+objLv2[v.lv2Code].count+"\">"+objLv2[v.lv2Code].title+"</td>";
		}
		html += "<td style=\"text-align:left\">"+v.lv3Title+"</td><td>"+v.question+"</td>";
		html += "<td>"+v.point+"</td><td>"+v.choice+"</td><td>"+v.answer+"</td>";
		if(lv2!=v.lv2Code){
			html += "<td rowspan=\""+objLv2[v.lv2Code].count+"\" class=\"last\">"+objLv2[v.lv2Code].avg+"</td>";
			lv2=v.lv2Code;
		}
		html += "</tr>";
		$tbl.find("tbody").append(html);
	});
}

function blankTable(tid){
	$("#"+tid+" tbody").append("<tr><td colspan=\""+$("#"+tid+" colgroup col").length+"\">표시할 정보가 없습니다.</td></tr>");
}
function blankChart(cid){
    var w=$("#"+cid).parent().width();
    var h=200;
    var canvas = document.getElementById(cid);
    canvas.width = w;
    canvas.height = h;
    var ctx = canvas.getContext('2d');
    ctx.font="12px dotum";
    ctx.fillText("표시할 정보가 없습니다.", (w-130)/2, 100);

}