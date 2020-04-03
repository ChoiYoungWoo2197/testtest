<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<META http-equiv="X-UA-Compatible" content="IE=edge">
<title>SK broadband ISAMS</title>
<link rel="stylesheet" type="text/css" href="/common/css/reset.css"/>
<link rel="stylesheet" type="text/css" href="/common/css/common.css"/>
<link rel="stylesheet" type="text/css" href="/common/css/pop.css"/>
<script type="text/javascript" src="/common/js/jquery.js"></script>
<!--[if lte IE 8]>
<script type="text/javascript" src="/common/js/excanvas.js"></script>
<script type="text/javascript" src="/common/js/Chart-1.1.1.min.js"></script>
<script type="text/javascript" src="/common/js/Chart.StackedBar.js"></script>
<script type="text/javascript" src="/common/js/stateChart-ie8.js"></script>
<![endif]-->
<!--[if gt IE 8]> <!-- -->
<script type="text/javascript" src="/common/js/Chart.min.js"></script>
<script type="text/javascript" src="/common/js/stateChart.js"></script>

<script type="text/javascript" src="/common/js/html2canvas.js"></script>
<script type="text/javascript" src="/common/js/jspdf.debug.js"></script>
<script type="text/javascript" src="/common/js/jquery.blockUI.js"></script>

<script  type="text/javascript">
var params = { bcy_cod: "${bcy_cod}", ucm_ctr_gbn: "${ucm_ctr_gbn}", service: "${service}", dep: "${dep}", duration:false };
$(window).load(function(){
	$(".top_button .save").click(function(){
		makePDF();
	});
	$(".top_button .close").click(function() {
		if (confirm("창을 닫으시겠습니까?")) window.close();
	});

	$("#contentsPDF").append($("#chartContainer1",opener.document).html());
	$("#contentsPDF").append($("#chartContainer2",opener.document).html());
	$(".not_pdf").remove();
<c:choose>
	<c:when test="${type eq 'sa'}">
	progressChartSa(1, "getStatisticsSaPart1.do", 0,  params);
	progressChartSa(2, "getStatisticsSaPart2.do", 1,  params);
	<c:set var="pdfTitle" value="Self Audit" />
	</c:when>
	<c:when test="${type eq 'infra_mp'}">
	progressChartInfraMp(1, "getStatisticsInfraMpPart1.do", 0,  params);
	progressChartInfraMp(2, "getStatisticsInfraMpPart2.do", 1,  params);
	<c:set var="pdfTitle" value="기반시설 취약점 분석평가" />
	</c:when>
	<c:when test="${type eq 'infra_la'}">
	progressChartInfraLa(1, "getStatisticsInfraLaPart1.do", 0, params);
	progressChartInfraLa(2, "getStatisticsInfraLaPart2.do", 1, params);
	<c:set var="pdfTitle" value="기반시설 정보보호 수준평가" />
	</c:when>
	<c:when test="${type eq 'msit'}">
	progressChartMsit(1, "getStatisticsMsitPart1.do", 0, params);
	progressChartMsit(2, "getStatisticsMsitPart2.do", 1, params);
	<c:set var="pdfTitle" value="과기정통부 체크리스트" />
	</c:when>
</c:choose>
	//글자 겹침 현상 발생 방지
	$("#contentsPDF td").each(function(i, v){
		$("#contentsPDF td").eq(i).text($("#contentsPDF td").eq(i).text().replace(/\(/g, " ("));
	});
	makePDF();
});

function addPadding(doc, imgWidth, pageHeight, padding){
	doc.setFillColor(255, 255, 255);
	doc.rect(0, 0, imgWidth, padding, 'F');
	doc.setFillColor(255, 255, 255);
	doc.rect(0, pageHeight-padding, imgWidth, padding, 'F');
}

function makePDF(){
	$(".block2").show();
	$(".top_button .save").attr("disabled", true);
	html2canvas($("#wrapPDF"), {
		onrendered: function(canvas) {
			var imgData = canvas.toDataURL("image/png");
			var imgWidth = 210;
			var pageHeight = imgWidth * 1.414;
			var imgHeight = canvas.height * imgWidth / canvas.width;
			var heightLeft = imgHeight;
			var doc = new jsPDF("p", "mm");
			var firstTop = 10;
			var position = firstTop;
			var padding = 10;
			doc.addImage(imgData, "PNG", padding, position, imgWidth-(padding*2), imgHeight);
			addPadding(doc, imgWidth, pageHeight, padding);
			heightLeft -= pageHeight-firstTop-(padding*4);
			while (heightLeft >= 20) {
				position = heightLeft - imgHeight-(padding*2);
				doc.addPage();
				doc.addImage(imgData, "PNG", padding, position, imgWidth-(padding*2), imgHeight);
				heightLeft -= pageHeight-(padding*2);
				addPadding(doc, imgWidth, pageHeight, padding);
			}
			doc.save("${pdfTitle} ${title}.pdf");
			$(".top_button .save").attr("disabled", false);
			$(".block2").hide();
		}
	});
}
</script>
<style>
body {/*overflow: hidden;*/}
#wrapPDF {width:1020px; margin: 0 auto; padding: 40px 10px 10px 10px;}
.titles { padding: 10px; font-size: 40px; line-height: 0.8; font-weight: bold; color: #000; text-align: center; }
#contentsPDF .title { padding: 40px 0 20px; font-size: 20px; font-weight: bold; color: #000; }
#contentsPDF td { word-break: keep-all; }
.talbeArea .edit_sa span { display: none;}
.blockUI { position: fixed; z-index: 100000; padding: 0px; width: 100%; height: 100%; top: 0px; left: 0px; margin: 0px; background-color: rgb(0, 0, 0); border: none; opacity: 0.01; }
.top_button {position: fixed; top: 0px; }
.top_button.left {z-index: 110000;  left:0px;}
.top_button.right {z-index:1200000;  right:0px;}
.top_button.base {width:100%; background-color:#ede9e7;}
.top_button li { float: left; margin:5px 10px; }
.top_button li button { padding:2px 10px; }
.blockMsg { position: fixed; padding: 0px; top: 10px; left: 70px; font-weight: bold; }
.block2 {z-index:1150000; display: none;}
</style>
</head>
<body>
	<ul class="top_button left base"><li><button class="save">저장</button></li></ul>
	<ul class="top_button right"><li>PDF 저장은 IE 보다 크롬 브라우저를 권장합니다. <button class="close">닫기</button></li></ul>
	<div id="wrapPDF">
		<div class="titles">${pdfTitle}</div>
		<div class="titles">${title}</div>
		<div id="contentsPDF"></div>
	</div>
	<div class="block blockUI"></div>
	<div class="block2 blockUI"></div>
	<div class="block2 blockMsg">PDF 파일을 생성 중 입니다. 잠시 기다려 주세요.</div>
</body>
</html>