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
<!--[if gt IE 8]>-->
<script type="text/javascript" src="/common/js/Chart.min.js"></script>
<script type="text/javascript" src="/common/js/stateChart.js"></script>
<script type="text/javascript">
    var params = {};
    $(function(){
        stOrgList();
        $("#btnSearch").click(function(){initChart()}).click();
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
        params = {bcy_cod: $("#bcy_cod").val()};
        if($("#quarter").val()) params.quarter = $("#quarter").val();
        progressChartSa(2, "getStatisticsSaPart2_2.do", 2,  params);
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
                        <span class="title"><label for="quarter">분기</label></span>
                        <select class="selectBox" id="quarter" name="quarter">
                            <option value="">전체</option>
                            <option value="1">1 분기</option>
                            <option value="2">2 분기</option>
                            <option value="3">3 분기</option>
                            <option value="4">4 분기</option>
                        </select>
                    </li>
                    <li>
                        <button type="button" id="btnSearch" class="btnSearch">검색</button>
                    </li>
                </ul>
            </fieldset>
        </div>
    </form>
</div>

<div class="chartContainer contents" id="chartContainer2">
    <ul class="choice_section not_pdf">
        <li><label><input type="checkbox" class="chkDisplay" target="chart2_1" kind="crt" checked>항목별 진척률 - 차트</label></li>
        <li><label><input type="checkbox" class="chkDisplay" target="chart2_1" kind="tbl" checked>항목별 진척률 - 표</label></li>
        <li><label><input type="checkbox" class="chkDisplay" target="chart2_2" kind="crt" checked>분야별 평가 결과 - 차트</label></li>
        <li><label><input type="checkbox" class="chkDisplay" target="chart2_2" kind="tbl" checked>분야별 평가 결과 - 표</label></li>
    </ul>
    <div class="title chart2_1">항목별 진척률</div>
    <div class="crt chart2_1"><canvas class="canvasChart" id="chart2_1" width="1020" height="300"></canvas></div>
    <div class="talbeArea tbl chart2_1">
        <table id="chartTbl2_1" summary="">
            <colgroup>
                <col width="18%" />
                <col width="" />
                <col width="30%" />
                <col width="12%" />
            </colgroup>
            <caption></caption>
            <thead>
            <tr>
                <th>분야</th>
                <th>통제분야</th>
                <th>통제항목</th>
                <th class="last">평균진척률</th>
            </tr>
            </thead>
            <tbody></tbody>
            <tfoot></tfoot>
        </table>
    </div>

    <div class="title chart2_2">분야별 평가 결과</div>
    <div class="crt chart2_2"><canvas class="canvasChart" id="chart2_2" width="1020" height="300"></canvas></div>
    <div class="talbeArea tbl chart2_2">
        <table id="chartTbl2_2" summary="">
            <colgroup>
                <col width="" />
                <col width="10%" />
                <col width="10%" />
                <col width="10%" />
                <col width="10%" />
                <col width="10%" />
                <col width="20%" />
            </colgroup>
            <caption></caption>
            <thead>
            <tr>
                <th rowspan="2">분야</th>
                <th colspan="5">평가</th>
                <th rowspan="2" class="last">합계(통제항목)</th>
            </tr>
            <tr>
                <th>Y</th>
                <th>UP</th>
                <th>LP</th>
                <th>N</th>
                <th>N/A</th>
            </tr>
            </thead>
            <tbody></tbody>
            <tfoot>
            <tr>
                <th>합계</th>
                <th class="items item_y"></th>
                <th class="items item_up"></th>
                <th class="items item_lp"></th>
                <th class="items item_n"></th>
                <th class="items item_na"></th>
                <th class="last items item_total"></th>
            </tr>
            </tfoot>
        </table>
    </div>
</div>

</body>
</html>
