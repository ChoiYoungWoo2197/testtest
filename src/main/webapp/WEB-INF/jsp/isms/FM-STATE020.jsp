<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>

<html>
<head>
<script type="text/javascript" src="/common/js/jquery.cookie.js"></script>
<!--[if lte IE 8]>
<script type="text/javascript" src="/common/js/excanvas.js"></script>
<script type="text/javascript" src="/common/js/Chart-1.1.1.min.js"></script>
<script type="text/javascript" src="/common/js/Chart.StackedBar.js"></script>
<script type="text/javascript" src="/common/js/stateChart-ie8.js"></script>
<![endif]-->
<!--[if gt IE 8]> -->
<script type="text/javascript" src="/common/js/Chart.min.js"></script>
<script type="text/javascript" src="/common/js/stateChart.js"></script>

    <script type="text/javascript" src="/common/js/html2canvas.js"></script>
    <script type="text/javascript" src="/common/js/jspdf.debug.js"></script>
<script type="text/javascript">
    var params = {};
    $(function(){
        $(".list2Tab li").click(function(){
            $(".list2Tab li").removeClass("sel");
            $(this).addClass("sel");
            initChart();
        });
        $("#btnSearch").click(function(){initChart()});
        $("#listTab1 li").eq(0).click();
        $(".downPDF").click(function(){downPDF()});
        $(".chkDisplay").change(function () {
            var target = $(this).attr("target");
            var kind = $(this).attr("kind");
            if($(this).prop("checked")){
                $("."+kind+"."+target).show();
                $(".title."+target).show();
            }else{
                $("."+kind+"."+target).hide();
            }
            if(!$(".chkDisplay[target="+target+"][kind=crt]").prop("checked")&&!$(".chkDisplay[target="+target+"][kind=tbl]").prop("checked")){
                $(".title."+target).hide();
            }
        });
    });
    function initChart(){
        var val = $(".list2Tab li").index($(".list2Tab li.sel"));
        params = {bcy_cod: $("#bcy_cod").val(), ucm_ctr_gbn: $("#ucm_ctr_gbn").val(), service: $("#service").val() };
        $(".chartContainer").hide();
        if(val==0){
            progressChartInfraLa(1, "getStatisticsInfraLaPart1.do", val,  params);
        }else if(val==1) {
            progressChartInfraLa(2, "getStatisticsInfraLaPart2.do", val,  params);
        }
    }
    function downPDF(){
        window.open("","downPDF");
        $("#bcy_cod").val(params.bcy_cod);
        $("#ucm_ctr_gbn").val(params.ucm_ctr_gbn);
        $("#service").val(params.service);

        var title = $("#bcy_cod option:checked").text();
        title += " "+$("#ucm_ctr_gbn option:checked").text();
        title += " "+$("#service option:checked").text();

        $("#downPDF").append("<form method=\"POST\" target=\"downPDF\" action=\"${pageContext.request.contextPath}/state/FM-STATE_PDF_popup.do\"></form>");
        $("#downPDF form").append("<input type=\"hidden\" name=\"type\" value=\"infra_la\" />");
        $("#downPDF form").append("<input type=\"hidden\" name=\"title\" value=\""+title+"\" />");
        $("#downPDF form").append("<input type=\"hidden\" name=\"bcy_cod\" value=\""+params.bcy_cod+"\" />");
        $("#downPDF form").append("<input type=\"hidden\" name=\"ucm_ctr_gbn\" value=\""+params.ucm_ctr_gbn+"\" />");
        $("#downPDF form").append("<input type=\"hidden\" name=\"service\" value=\""+params.service+"\" />");
        $("#downPDF form").submit().remove();
    }
</script>
<style>
.choice_section { border: 1px solid #ccc;background-color: #f7f7f7;padding: 10px 20px;}
.choice_section li {display: inline-block;min-width: 240px;}
.choice_section label {cursor:pointer;}
.choice_section input {margin-right: 5px;cursor:pointer;}
</style>
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
                        <span class="title"><label for="bcy_cod">주기</label></span>
                        <select class="selectBox" id="bcy_cod" name="bcy_cod">
                            <c:forEach var="listBcyCod" items="${listBcyCod}" varStatus="status">
                                <option value="${listBcyCod.ummManCyl}">${listBcyCod.ummManTle}</option>
                            </c:forEach>
                        </select>
                    </li>
                    <li>
                        <span class="title"><label for="ucm_ctr_gbn">컴플라이언스</label></span>
                        <select class="selectBox" id="ucm_ctr_gbn" name="ucm_ctr_gbn">
                            <c:forEach var="compList" items="${compList}" varStatus="status">
                                <option value="${compList.code}">${compList.name}</option>
                            </c:forEach>
                        </select>
                    </li>
                    <li>
                        <span class="title"><label for="service">서비스</label></span>
                        <select class="selectBox" id="service" name="service">
                            <c:forEach var="service" items="${listService}" varStatus="status">
                                <option value="${service.code}">${service.name}</option>
                            </c:forEach>
                        </select>
                    </li>
                    <li class="">
                        <button type="button" id="btnSearch" class="btnSearch">검색</button>
                    </li>
                </ul>
            </fieldset>
        </div>
    </form>
</div>
<div class="topBtnArea">
    <ul class="btnList">
        <li>
            <button type="button" class="downPDF"><span class="icoDown"></span>보고서 다운로드</button>
        </li>
    </ul>
    <div id="downPDF"></div>
</div>
<div class="tabArea" style="margin-bottom: 10px;">
    <ul id="listTab1" class="list2Tab">
        <li class="sel"><a href="javascript:;">분야별 평가결과</a></li>
        <li><a href="javascript:;">연도별 평가결과 비교</a></li>
    </ul>
</div>

<div class="chartContainer contents" id="chartContainer1">
    <ul class="choice_section not_pdf">
        <li><label><input type="checkbox" class="chkDisplay" target="chart1" kind="crt" checked>분야별 평가 결과 - 차트1</label></li>
        <li><label><input type="checkbox" class="chkDisplay" target="chart1" kind="tbl" checked>분야별 평가 결과 - 표</label></li>
    </ul>
    <div class="title chart1">분야별 평가 결과</div>
    <div class="crt chart1">
        <div style="display:inline-block;width:508px"><canvas class="canvasChart" id="chart1_1" width="505" height="300"></canvas></div>
        <div style="display:inline-block;width:508px"><canvas class="canvasChart" id="chart1_2" width="505" height="300"></canvas></div>
    </div>
    <div class="talbeArea tbl chart1">
        <table id="chartTbl1_1" summary="">
            <colgroup>
                <col width="" />
                <col width="10%" />
                <col width="10%" />
                <col width="10%" />
                <col width="10%" />
                <col width="12%" />
            </colgroup>
            <caption></caption>
            <thead>
            <tr>
                <th rowspan="2">통제분야</th>
                <th colspan="4">세부 평가 점수</th>
                <th rowspan="2" class="last">성숙도 단계</th>
            </tr>
            <tr>
                <th>항목수</th>
                <th>결과</th>
                <th>총점</th>
                <th>백분률(%)</th>
            </tr>
            </thead>
            <tbody></tbody>
            <tfoot></tfoot>
        </table>
    </div><br>
    <div class="talbeArea talbeSubArea">
        <table id="chartTbl1_2" summary="">
            <colgroup>
                <col width="" />
                <col width="15%" />
                <col width="10%" />
                <col width="10%" />
                <col width="10%" />
                <col width="15%" />
            </colgroup>
            <thead>
            <tr>
                <th>통제항목</th>
                <th>세부평가항목</th>
                <th>평가결과</th>
                <th>평가점수</th>
                <th>평가개수</th>
                <th class="last">통제분야별 성숙도</th>
            </tr>
            </thead>
            <tbody></tbody>
        </table>
    </div>
</div>

<div class="chartContainer contents" id="chartContainer2" style="display: none;">
    <ul class="choice_section not_pdf">
        <li><label><input type="checkbox" class="chkDisplay" target="chart2" kind="crt" checked>연도별 평가 결과 비교 - 차트1</label></li>
        <li><label><input type="checkbox" class="chkDisplay" target="chart2" kind="tbl" checked>연도별 평가 결과 비교 - 표</label></li>
    </ul>
    <div class="title chart2">연도별 평가 결과 비교</div>
    <div class="crt chart2"><canvas class="canvasChart" id="chart2" width="1020" height="300"></canvas></div>
    <div class="talbeArea tbl chart2">
        <table id="chartTbl2" summary="">
            <colgroup>
                <col width="10%" />
                <col width="" />
                <col width="12%" />
                <col width="12%" />
            </colgroup>
            <thead>
            <tr>
                <th>통제분야</th>
                <th>통제항목</th>
                <th>세부평가항목</th>
                <th class="years last">통제분야별 성숙도</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
    <div class="comment"></div>
</div>
</body>
</html>
