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
        $(".list2Tab li").click(function(){
            $(".list2Tab li").removeClass("sel");
            switch($(".list2Tab li").index(this)){
                case 0:
                    $(".searchForm .dep").val("").hide();

                    break;
                case 1:
                    $(".searchForm .dep").show();
                    break;
            }
            $(this).addClass("sel");
            initChart();
        });
        $("#btnSearch").click(function(){initChart()});
        $("#listTab1 li").eq(0).click();
        $(".editSa").click(function(){editSa()});
        $(".settingSa").click(function(){settingSa()});
        $(".downPDF").click(function(){editCancel();downPDF()});
        $(".chkDisplay").change(function () {
            var target = $(this).attr("target");
            var kind = $(this).attr("kind");
            if($(this).prop("checked")){
                $("."+kind+"."+target).show();
                $(".title."+target).show();
            }else{
                $("."+kind+"."+target).hide();
            }
            if(target.indexOf("chart1_2")>=0){
                if($(".chkDisplay[target=chart1_2_1][kind=crt]").prop("checked") || $(".chkDisplay[target=chart1_2_2][kind=crt]").prop("checked")
                    || $(".chkDisplay[target=chart1_2][kind=tbl]").prop("checked")){
                    $(".title.chart1_2").show();
                }else{
                    $(".title.chart1_2").hide();
                }
            }else{
                if(!$(".chkDisplay[target="+target+"][kind=crt]").prop("checked")&&!$(".chkDisplay[target="+target+"][kind=tbl]").prop("checked")){
                    $(".title."+target).hide();
                }
            }
        });
    });
    function initChart(){
        var val = $(".list2Tab li").index($(".list2Tab li.sel"));
        params = { bcy_cod: $("#bcy_cod").val()};
        if($("#quarter").val()) params.quarter = $("#quarter").val();

        $(".chartContainer").hide();
        if(val==0){
            progressChartSa(1, "getStatisticsSaPart1.do", val,  params);
        }else if(val==1) {

            if($("#gpOrg").val()){
                params.dep = $("#gpOrg").val();
            }else if($("#hqOrg").val()){
                params.dep = $("#hqOrg").val();
            }else{
                params.dep = $("#stOrg").val();
            }
            progressChartSa(2, "getStatisticsSaPart2.do", val,  params);
        }
    }
    function downPDF(){
        window.open("","downPDF");
        $("#downPDF").append("<form method=\"POST\" target=\"downPDF\" action=\"${pageContext.request.contextPath}/state/FM-STATE_PDF_popup.do\"></form>");
        $("#bcy_cod").val(params.bcy_cod);
        $("#downPDF form").append("<input type=\"hidden\" name=\"type\" value=\"sa\" />");
        $("#downPDF form").append("<input type=\"hidden\" name=\"title\" value=\""+$("#bcy_cod option:checked").text()+"\" />");
        $("#downPDF form").append("<input type=\"hidden\" name=\"bcy_cod\" value=\""+params.bcy_cod+"\" />");
        $("#downPDF form").append("<input type=\"hidden\" name=\"ucm_ctr_gbn\" value=\""+params.ucm_ctr_gbn+"\" />");
        $("#downPDF form").append("<input type=\"hidden\" name=\"service\" value=\""+params.service+"\" />");
        $("#downPDF form").submit().remove();
    }
    function editSa(){
        $("#chartTbl1_1 .edit_sa").each(function(){
            sa = $(this).attr("sa_edit")?$(this).attr("sa_edit"):$(this).attr("sa_cal");
            $(this).html("<input value='"+sa+"' />");
        });
        $("#chartTbl1_1 .edit_sa input").change(function(){
            sa = $(this).parent(".edit_sa").attr("sa_edit")?$(this).parent(".edit_sa").attr("sa_edit"):$(this).parent(".edit_sa").attr("sa_cal");
            if(sa!=$(this).val()){
                $(this).addClass("chg");
            }else{
                $(this).removeClass("chg");
            }
        });
        $(".editSa").hide();
        $(".editButtons").append("<button type=\"button\" class=\"editUpdate\">저장</button>");
        $(".editButtons").append("<button type=\"button\" class=\"editCancel\">취소</button>");
        $(".editUpdate").click(function(){editUpdate()})
        $(".editCancel").click(function(){editCancel()})
    }
    function editUpdate(){
        if(!$(".edit_sa input.chg").length){
            alert("수정할 내용이 없습니다.");
            return false;
        }
        var listSa = [];
        for(var i=0; i<$(".edit_sa input.chg").length; i++){
            var sa = $(".edit_sa input.chg").eq(i);
            listSa.push({"dept":sa.parent(".edit_sa").attr("dept"), "sa":sa.val()});
        }
        var param = JSON.stringify({"bcy_cod":params.bcy_cod, "list_sa":listSa});

        $.ajax({
            url		: "${pageContext.request.contextPath}/state/FM-STATE018_EDIT_SA.do",
            type	: "POST",
            data	: {"param":param},
            dataType: "json",
            success	: function(data){
                editCancel();
                initChart();
            },
            error : function(){
                alert('error');
            },
        });
    }
    function editCancel(){
        $(".editSa").show();
        $(".editUpdate").remove();
        $(".editCancel").remove();
        $("#chartTbl1_1 .edit_sa").each(function(){
            sa = $(this).attr("sa_edit")?$(this).attr("sa_edit"):$(this).attr("sa_cal");
            $(this).text(sa);
        });
    }
    function settingSa(){
        $(".settingSa").append("<form method=\"POST\" action=\"${pageContext.request.contextPath}/state/FM-STATE018.do\"></form>");
        $(".settingSa form").append("<input type=\"text\" name=\"mode\" value=\"setting\" />");
        $(".settingSa form").submit().remove();
    }
</script>
<style>
.editButtons {text-align: right;padding: 10px 0;}
.editButtons button{min-width: 50px;margin-left: 10px;}
.talbeArea .edit_sa {height: 22px;}
.talbeArea .edit_sa span {font-size: 11px; margin-left: 4px;}
.talbeArea .edit_sa input {width: 70px;height: 20px;padding: 0 5px;border: 1px solid #ddd;text-align: center;}
.talbeArea .edit_sa input.chg {border-color: #FF4F42;}

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
                    <li class="dep" style="display: none;">
                        <span class="title"><label for="stOrg">본부</label></span>
                        <sb:select name="stOrg" type="stOrg" forbidden="true" allText="본부전체" />
                    </li>
                    <li class="dep" style="display: none;">
                        <span class="title"><label for="hqOrg">그룹,담당</label></span>
                        <sb:select name="hqOrg" type="hqOrg" forbidden="true" allText="그룹,담당전체" />
                    </li>
                    <li class="dep" style="display: none;">
                        <span class="title"><label for="gpOrg">팀</label></span>
                        <sb:select name="gpOrg" type="gpOrg" forbidden="true" allText="팀전체" />
                    </li>
                    <li>
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
        <li class="sel"><a href="javascript:;">분류별 보안수준</a></li>
        <li><a href="javascript:;">분류별 평가 결과</a></li>
    </ul>
</div>

<div class="chartContainer contents" id="chartContainer1">
    <ul class="choice_section not_pdf">
        <li><label><input type="checkbox" class="chkDisplay" target="chart1_1" kind="crt" checked>부서별 점검 레벨 - 차트</label></li>
        <li><label><input type="checkbox" class="chkDisplay" target="chart1_1" kind="tbl" checked>부서별 점검 레벨 - 표</label></li>
        <li><label><input type="checkbox" class="chkDisplay" target="chart1_2_1" kind="crt" checked>부서별 진단결과 & 총 진척률 - 차트1</label></li>
        <li><label><input type="checkbox" class="chkDisplay" target="chart1_2_2" kind="crt" checked>부서별 진단결과 & 총 진척률 - 차트2</label></li>
        <li><label><input type="checkbox" class="chkDisplay" target="chart1_2" kind="tbl" checked>부서별 진단결과 & 총 진척률 - 표</label></li>
        <li><label><input type="checkbox" class="chkDisplay" target="chart1_3" kind="crt" checked>분야별 진단결과 & 총 진척률 - 차트</label></li>
        <li><label><input type="checkbox" class="chkDisplay" target="chart1_3" kind="tbl" checked>분야별 진단결과 & 총 진척률 - 표</label></li>
        <li><label><input type="checkbox" class="chkDisplay" target="chart1_4" kind="crt" checked>부서별 분야 결과 - 차트</label></li>
        <li><label><input type="checkbox" class="chkDisplay" target="chart1_4" kind="tbl" checked>부서별 분야 결과 - 표</label></li>
        <li><label><input type="checkbox" class="chkDisplay" target="chart1_5" kind="crt" checked>연도별 평가 결과 비교 - 차트</label></li>
        <li><label><input type="checkbox" class="chkDisplay" target="chart1_5" kind="tbl" checked>연도별 평가 결과 비교 - 표</label></li>
    </ul>

    <div class="title chart1_1">부서별 점검 레벨</div>
    <div class="crt chart1_1" >
        <canvas class="canvasChart" id="chart1_1" width="1020" height="300"></canvas>
    </div>
    <div class="talbeArea tbl chart1_1">
        <div class="editButtons not_pdf">
            <button type="button" class="settingSa">점검레벨 기준설정</button>
            <button type="button" class="editSa">보안레벨 수정</button>
        </div>
        <table id="chartTbl1_1" summary="">
            <colgroup>
                <col width="20%" />
                <col width="24%" />
            </colgroup>
            <caption></caption>
            <thead>
                <tr>
                    <th>구분</th>
                    <th>부서명</th>
                    <th class="sa_level">SA 점검레벨</th>
                    <th class="total last">항목수</th>
                </tr>
            </thead>
            <tbody></tbody>
            <tfoot>
                <tr>
                    <th colspan="2">평균</th>
                    <th class="items item_sa"></th>
                    <th class="last items item_cnt"></th>
                </tr>
            </tfoot>
        </table>
    </div>

    <div class="title chart1_2">부서별 진단결과 & 총 진척률</div>
    <div class="crt chart1_2_1"><canvas class="canvasChart" id="chart1_2_1" width="1020" height="300"></canvas></div>
    <div class="crt chart1_2_2"><canvas class="canvasChart" id="chart1_2_2" width="1020" height="300"></canvas></div>
    <div class="talbeArea tbl chart1_2">
        <table id="chartTbl1_2" summary="">
            <colgroup>
                <col width="15%" />
                <col width="" />
                <col width="12%" />
                <col width="8%" />
                <col width="8%" />
                <col width="8%" />
                <col width="8%" />
                <col width="8%" />
                <col width="12%" />
            </colgroup>
            <caption></caption>
            <thead>
                <tr>
                    <th rowspan="2">구분</th>
                    <th rowspan="2">부서명</th>
                    <th rowspan="2">총 항목수</th>
                    <th colspan="5">진단결과</th>
                    <th rowspan="2" class="last">총 진척률</th>
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
                    <th colspan="2">합계</th>
                    <th class="items count"></th>
                    <th class="items cnt_y"></th>
                    <th class="items cnt_up"></th>
                    <th class="items cnt_lp"></th>
                    <th class="items cnt_n"></th>
                    <th class="items cnt_na"></th>
                    <th rowspan="2" class="last items total_prg"></th>
                </tr>
                <tr>
                    <th colspan="3">점유율</th>
                    <th class="items prg_y"></th>
                    <th class="items prg_up"></th>
                    <th class="items prg_lp"></th>
                    <th class="items prg_n"></th>
                    <th class="items prg_na"></th>
                </tr>
            </tfoot>
        </table>
    </div>

    <div class="title chart1_3">분야별 진단결과 & 총 진척률</div>
    <div class="crt chart1_3"><canvas class="canvasChart" id="chart1_3" width="1020" height="300"></canvas></div>
    <div class="talbeArea tbl chart1_3">
        <table id="chartTbl1_3" summary="">
            <colgroup>
                <col width="" />
                <col width="15%" />
                <col width="12%" />
                <col width="8%" />
                <col width="8%" />
                <col width="8%" />
                <col width="8%" />
                <col width="8%" />
            </colgroup>
            <caption></caption>
            <thead>
                <tr>
                    <th rowspan="2">분야별</th>
                    <th rowspan="2">전체 부서 진척률</th>
                    <th rowspan="2">총 항목수</th>
                    <th colspan="5" class="last">진단결과</th>
                </tr>
                <tr>
                    <th>Y</th>
                    <th>UP</th>
                    <th>LP</th>
                    <th>N</th>
                    <th class="last">N/A</th>
                </tr>
            </thead>
            <tbody></tbody>
            <tfoot>
                <tr>
                    <th>합계</th>
                    <th class="items prg"></th>
                    <th class="items counts"></th>
                    <th class="items cnt_y"></th>
                    <th class="items cnt_up"></th>
                    <th class="items cnt_lp"></th>
                    <th class="items cnt_n"></th>
                    <th class="last items cnt_na"></th>
                </tr>
                <tr>
                    <th colspan="3">점유율</th>
                    <th class="items per_y"></th>
                    <th class="items per_up"></th>
                    <th class="items per_lp"></th>
                    <th class="items per_n"></th>
                    <th class="last items per_na"></th>
                </tr>
            </tfoot>
        </table>
    </div>

    <div class="title chart1_4">부서별 분야 결과</div>
    <div class="crt chart1_4"><canvas class="canvasChart" id="chart1_4" width="1020" height="300"></canvas></div>
    <div class="talbeArea tbl chart1_4">
        <table id="chartTbl1_4" summary="">
            <colgroup>
                <col width="*" />
            </colgroup>
            <thead>
            <tr><th>구분</th></tr>
            </thead>
            <tbody></tbody>
        </table>
    </div>

    <div class="title chart1_5">연도별 점검레벨 결과 비교</div>
    <div class="crt chart1_5"><canvas class="canvasChart" id="chart1_5" width="1020" height="300"></canvas></div>
    <div class="talbeArea tbl chart1_5">
        <table id="chartTbl1_5" summary="">
            <colgroup>
                <col width="" />
            </colgroup>
            <caption></caption>
            <thead>
            <tr>
                <th>점검레벨</th>
            </tr>
            </thead>
            <tbody></tbody>
            <tfoot></tfoot>
        </table>
    </div>
</div>

<div class="chartContainer contents" id="chartContainer2" style="display: none;">
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
