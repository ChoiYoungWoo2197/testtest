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
	initRadiarChart();
	$("#btnSearch").click(function() {
		initRadiarChart();
	})
});
var initRadiarChart = function() {
	var title = $("#standard option:selected").text();
	var params = { standard: $("#standard").val(), service: $("#service").val() };
	progressRadarChart(1, "getWorkCtrStatistics.do", title + " 통제목적별", false,  params, true);
}
</script>
</head>
<body>
	<c:import url="/titlebar.do" />
	<div class="search">
		<form id="searchForm" method="post" name="searchForm">
		<input type="hidden" id="ufmFltKey" name="ufmFltKey" />
		    <div class="defalt">
		        <fieldset class="searchForm">
		            <legend>기본검색</legend>
		            <ul class="detail newField">
                        <li>
		                    <span class="title"><label for="standard">컴플라이언스</label></span>
		                    <sb:select name="standard" type="" code="STND" value="S03"  />
		                </li>
                        <li>
		                    <span class="title"><label for="service">서비스</label></span>
		                    <sb:select name="service" type="S"/>
		                </li>
		                <li class="">
		                 	<button type="button" id="btnSearch" class="btnSearch btnLast">조건으로 검색</button>
		                 </li>
		            </ul>
		        </fieldset>
		    </div>
	    </form>
	</div>
	<br />
	<div class="chartContainer" id="chartContainer1">
		<canvas class="canvasChart" id="chart1" height="145"></canvas>
		<div class="talbeArea" style="margin-top: 30px;">
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
				<caption>서비스별</caption>
				<thead>
	                   <tr>
	                       <th>통제목적</th>
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