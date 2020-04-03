<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/include/head.jsp"%>
<%@ include file="/WEB-INF/include/contents.jsp"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<META http-equiv="X-UA-Compatible" content="IE=edge">
<title>SK broadband ISAMS</title>
<link rel="stylesheet" type="text/css" href="/common/css/reset.css" />
<link rel="stylesheet" type="text/css" href="/common/css/common.css" />
<link rel="stylesheet" type="text/css" href="/common/css/pop.css" />
<link rel="stylesheet" type="text/css" href="/common/css/sub.css" />
<!--[if IE]> <script src="/common/js/html5.js"></script> <![endif]-->
<script type="text/javascript" src="/common/js/jquery.js"></script>
<script type="text/javascript" src="/common/js/jquery.form.js"></script>
<script type="text/javascript" src="/common/js/ismsUI.js"></script>
<script type="text/javascript" src="/common/js/common.js"></script>
<!-- [S] rMateChart -->
<link rel="stylesheet" type="text/css" href="/common/rMateChartH5/css/rMateChartH5.css"/>
<!-- IE7, 8 에서 차트 생성하고자 하는 경우 -->
<!--[if IE]><script language="javascript" type="text/javascript" src="/common/rMateChartH5/js/excanvas.js"></script><![endif]-->
<!-- rMateChartH5 라이센스 -->
<script type="text/javascript" src="/common/rMateChartH5/LicenseKey/rMateChartH5License.js"></script>
<!-- 실제적인 rMateChartH5 라이브러리 -->
<script type="text/javascript" src="/common/rMateChartH5/js/${chartName }.js"></script>
<!-- 테마 js -->
<script type="text/javascript" src="/common/rMateChartH5/js/theme.js"></script>
<script type="text/javascript">

// -----------------------차트 설정 시작-----------------------

// rMate 차트 생성 준비가 완료된 상태 시 호출할 함수를 지정합니다.
var chartVars = "rMateOnLoadCallFunction=chartReadyHandler";

// rMateChart 를 생성합니다.
// 파라메터 (순서대로) 
//  1. 차트의 id ( 임의로 지정하십시오. ) 
//  2. 차트가 위치할 div 의 id (즉, 차트의 부모 div 의 id 입니다.)
//  3. 차트 생성 시 필요한 환경 변수들의 묶음인 chartVars
//  4. 차트의 가로 사이즈 (생략 가능, 생략 시 100%)
//  5. 차트의 세로 사이즈 (생략 가능, 생략 시 100%)
rMateChartH5.create("chart1", "chartHolder", chartVars, "100%", "100%"); 

// 차트의 속성인 rMateOnLoadCallFunction 으로 설정된 함수.
// rMate 차트 준비가 완료된 경우 이 함수가 호출됩니다.
// 이 함수를 통해 차트에 레이아웃과 데이터를 삽입합니다.
// 파라메터 : id - rMateChartH5.create() 사용 시 사용자가 지정한 id 입니다.
function chartReadyHandler(id) {
	document.getElementById(id).setLayout(layoutStr);
	document.getElementById(id).setData(chartData);
}

// 스트링 형식으로 레이아웃 정의.
var layoutStr = '${chartGrid}';

//차트 데이터
var chartData = ${listJson};

/*
//------------------------- 데이터팁 사용자 정의 함수 -----------------------------------------------------
//챠트에서 showDataTips="true" 설정 후 마우스 오버 시 보이는 데이터팁 정의
//layout XML 에서 Chart 속성을 넣을때 dataTipJsFunction를 주고, 만든 javascript 함수명을 넣어줍니다
//예) <Column3DChart showDataTips="true" dataTipJsFunction="dataTipFunc">
//
//파라메터 설명
//seriesId : layout XML에서 부여한 series의 id가 있을 경우, 해당 id를 보내줍니다.
//seriesName : 시리즈의 displayName 으로 정의한 시리즈 이름을 보내줍니다.
//index : 해당 아이템의 인덱스.
//xName : X 축에 displayName 을 정의하였다면 X축의 displayName을 보내줍니다.
//yName : Y 축에 displayName 을 정의하였다면 Y축의 displayName을 보내줍니다.
//data : 해당 item의 값을 표현하기 위해 입력된 data(입력된 데이타중 해당 row(레코드) 자료입니다)
//values : 해당 item의 값 (배열로 전달되며, 챠트 종류에 따라 아래와 같이 들어옵니다.)
			BarSeries     0:x축값 1:y축값
			ColumnSeries  0:x축값 1:y축값
			AreaSeries    0:x축값 1:y축값
			BubbleSeries  0:x축값 1:y축값 2:radius값
			LineSeries    0:x축값 1:y축값
			Candlestick   0:x축값 1:open값 2:close값 3:high값 4:low값
			PieSeries     0:값 1:퍼센트값 2:nameFiled명
*/

function dataTipFunc002(seriesId, seriesName, index, xName, yName, data, values)
{
	var str = values[0] +"<br/>"+ seriesName +" : " + values[1] + yName +"<br/>합계 : "+ getSum(data) + yName
	return str;
}

function dataTipFunc002_B(seriesId, seriesName, index, xName, yName, data, values)
{
	var str = values[1] +"<br/>"+ seriesName +" : " + values[0] + xName +"<br/>합계 : "+ getSum(data) + xName;
	return str;
}

function dataTipFunc005(seriesId, seriesName, index, xName, yName, data, values)
{
	var str = values[0] +"<br/>"+ seriesName +" : " + values[1] + yName +"<br/>합계 : "+ getSum(data) + yName;
	return str;
}

function dataTipFunc006(seriesId, seriesName, index, xName, yName, data, values)
{
	var str = values[0] +"<br/>"+ seriesName +" : " + values[1] + yName +"<br/>합계 : "+ getSum(data) + yName;
	return str;
}

function dataTipFunc004(seriesId, seriesName, index, xName, yName, data, values)
{
	var str = values[0] +"<br/>"+ seriesName +" : " + values[1] + yName;
	return str;
}

function dataTipFunc004_B(seriesId, seriesName, index, xName, yName, data, values)
{
	var str = values[1] +"<br/>"+ seriesName +" : " + values[0] + xName;
	return str;
}

function getSum(values) {
	var sum = 0;
	for(var v in values) {
		sum += Number(values[v]) || 0;
	}
	return sum;
}

/**
 * rMateChartH5 3.0에서 제공하고 있는 테마기능을 사용하시려면 아래 내용을 설정하여 주십시오.
 * 테마 기능을 사용하지 않으시려면 아래 내용은 삭제 혹은 주석처리 하셔도 됩니다.
 *
 * -- rMateChartH5.themes에 등록되어있는 테마 목록 --
 * - simple
 * - cyber
 * - modern
 * - lovely
 * - pastel
 * - old
 * -------------------------------------------------
 *
 * rMateChartH5.themes 변수는 theme.js에서 정의하고 있습니다.
 */
rMateChartH5.registerTheme(rMateChartH5.themes);

/**
 * 샘플 내의 테마 버튼 클릭 시 호출되는 함수입니다.
 * 접근하는 차트 객체의 테마를 변경합니다.
 * 파라메터로 넘어오는 값
 * - simple
 * - cyber
 * - modern
 * - lovely
 * - pastel
 * - old
 * - default
 *
 * default : 테마를 적용하기 전 기본 형태를 출력합니다.
 * old : rMateChartH5 2.5 이하 버전의 형태를 출력합니다.
 */
function rMateChartH5ChangeTheme(theme){
	document.getElementById("chart1").setTheme(theme);
}

// -----------------------차트 설정 끝 -----------------------

</script>
</head>
<body>
	<div id="skipnavigation">
		<ul>
			<li><a href="#content-box">본문 바로가기</a></li>
		</ul>
	</div>
	<div id="wrap" class="pop">
		<header>
			<div class="borderBox"></div>
			<h1>${sTitle }</h1>
		</header>
		<article class="cont" id="content-box">
			<div class="cont_container">
				<div class="contents">
					<div class="title">${mTitle }</div>
					<div class="talbeArea">
						<div id="content">
							<!-- 차트가 삽입될 DIV -->
							<div id="chartHolder" style="width:600px; height:400px;">
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="centerBtnArea closeArea">
				<button class="btnStrong close">닫기</button>
			</div>
		</article>
	</div>
</body>
</html>
