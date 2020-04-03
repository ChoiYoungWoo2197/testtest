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
    $(function(){
        $("#editKindNew").change(function(){changeKind()});
        $(".createCriterion").click(function(){editCriterion();$("#editKindNew").focus()});
        $(".kindTitle").click(function(){editCriterion($(this));$("#editKindEdit").focus()});

        switch ("${result}") {
            case "success":
                alert("적용 되었습니다.");
                break;
            case "insert":
                alert("생성되지 않았습니다.");
                break;
            case "update":
                alert("수정되지 않았습니다.");
                break;
            default :
                break;
        }
    });

    function editCriterion(obj){
        $(".editSection input").val("");
        $(".editSection select").val("");
        $("#editKindNew").val("").hide();
        $("#editKindEdit").val("").hide();
        $(".editKindType").text("");
        if(obj==undefined){
            $("#editMode").val("new");
            $("#editKind").val("");
            $("#editKindNew").show();
            $(".typeTitle").text("평가 기준 생성");
        }else{
            kind = obj.parent().attr("kind");
            bcy_cod = obj.parent().attr("bcy_cod");
            for(i=1; i<=5; i++){
                $("#editPoint"+i+"Val1").val($(".editSection."+kind+"."+bcy_cod+">dd.point"+i+">ul>li:eq(0)").text());
                $("#editPoint"+i+"Op1").val($(".editSection."+kind+"."+bcy_cod+">dd.point"+i+">ul>li:eq(1)").attr("op"));
                $("#editPoint"+i+"Op2").val($(".editSection."+kind+"."+bcy_cod+">dd.point"+i+">ul>li:eq(3)").attr("op"));
                $("#editPoint"+i+"Val2").val($(".editSection."+kind+"."+bcy_cod+">dd.point"+i+">ul>li:eq(4)").text());
            }
            $("#editMode").val("edit");
            $("#editKind").val(obj.parent().attr("kind"));
            $(".editKindType").text(obj.parent().find(".kindType").text());
            $("#editKindEdit").val(obj.text());
            if(obj.parent().attr("use")=="y"){
                $("#editUseY").prop("checked", true);
            }else{
                $("#editUseN").prop("checked", true);
            }
            $("#editKindEdit").show();
            $(".typeTitle").text("평가 기준 수정");
        }
        $(".editWrap").show();
    }

    function changeKind() {
        var editKind = $("#editKindNew option:selected");
        var use = editKind.attr("use");
        if (use == "y") {
            $("#editUseY").prop("checked", true);
        } else {
            $("#editUseN").prop("checked", true);
        }
        $("#editKind").val(editKind.val());
        $(".editKindType").text(editKind.attr("kind_type")?editKind.attr("kind_type"):"");
        $(".editSection input").val("");
        $(".editSection select").val("");
    }

    function checkEditForm(){
        $(".error").removeClass("error");

        if($("#editMode").val()=="new"&& $("#editKindNew").val()=="") $("#editKindNew").addClass("error");
        for(var i=1; i<=5; i++){
            for(var j=1; j<=2; j++) {
                if ($.trim($("#editPoint" + i + "Val"+j).val()).length > 0 && $("#editPoint" + i + "Op"+j).val().length == 0
                    || $.trim($("#editPoint" + i + "Val"+j).val()).length == 0 && $("#editPoint" + i + "Op"+j).val().length > 0) {
                    $("#editPoint" + i + "Val"+j).addClass("error");
                    $("#editPoint" + i + "Op"+j).addClass("error");
                }
            }
            if ($.trim($("#editPoint" + i + "Val1").val()).length == 0 && $("#editPoint" + i + "Op1").val().length == 0
                && $.trim($("#editPoint" + i + "Val2").val()).length == 0 && $("#editPoint" + i + "Op2").val().length == 0) {
                $("#editPoint" + i + "Val1").addClass("error");
                $("#editPoint" + i + "Op1").addClass("error");
                $("#editPoint" + i + "Val2").addClass("error");
                $("#editPoint" + i + "Op2").addClass("error");
            }
        }
        if($(".error").length>0) return false;
        return true;
    }
</script>
<style>
.buttons {text-align: right;padding: 10px 0;}
.buttons button{min-width: 50px;margin-left: 10px;}

.editbox input[type=text], .editbox select {width: 80%;height: 20px;padding: 0 5px;border: 1px solid #ddd;text-align: center;}
.kindTitle {cursor: pointer; font-weight: bold}
.editSection {overflow: hidden; display: inline-block; }
.editSection>*, .editSection ul li { display: inline-block; margin: 0 5px; font-family: '맑은 고딕'}
.editSection ul li:nth-child(3) { margin: 0 5px; }
.editSection dt, .editSection dd { margin: 3px 0; float: left; }
.editSection dt { font-weight: bold; min-width: 40px; clear: left;  margin-right: 10px; }
.editSection input[type=text] { width: 80px;height: 20px;padding: 0 5px;border: 1px solid #ddd;text-align: center;}
.editSection select { width: 40px;height: 20px;padding: 0 5px;border: 1px solid #ddd;text-align: center;}
.editForm .error { border: 1px solid #f55 !important;}
</style>
</head>
<body>
<c:import url="/titlebar.do" />
<div class="search">
    <form id="searchForm" method="post" name="searchForm">
        <input type="hidden" name="mode" value="setting" />
        <div class="defalt">
            <fieldset class="searchForm">
                <legend>기본검색</legend>
                <ul class="detail newField">
                    <li>
                        <span class="title"><label for="bcy_cod">주기</label></span>
                        <select class="selectBox" id="bcy_cod" name="bcy_cod">
                            <c:forEach var="listBcyCod" items="${listBcyCod}" varStatus="status">
                                <option value="${listBcyCod.ummManCyl}" <c:if test="${listBcyCod.ummManCyl eq bcy_cod}">selected</c:if>>${listBcyCod.ummManTle}</option>
                            </c:forEach>
                        </select>
                    </li>
                    <li>
                        <input type="submit" id="btnSearch" class="btnSearch" value="검색" />
                    </li>
                </ul>
            </fieldset>
        </div>
    </form>
</div>
<br />
<div class="contents">
    <div class="talbeArea ">
        <table summary="">
            <colgroup><col width="20%" /><col width="15%" /><col width="*" /><col width="20%" /></colgroup>
            <thead>
                <tr>
                    <th>평가 기준 종류</th><th>평가 기준 구분</th><th>점수별 구간</th><th>사용여부</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${listCriterionPoint}" varStatus="status">
                    <tr bcy_cod="${item.uscBcyCod}" kind="${item.uscKindCod}" use="${item.uscUse}" >
                        <td class="kindTitle">${item.uscKindTitle}</td>
                        <td class="kindType">${item.uscKindType}</td>
                        <td>
                            <dl class="editSection ${item.uscKindCod} ${item.uscBcyCod}">
                                <c:forEach var="item2" items="${item.uscPoint}" varStatus="status2">
                                    <dt>${item2.uscpKindPoint} 점</dt>
                                    <dd class="point${item2.uscpKindPoint}">
                                        <ul>
                                            <li>${item2.uscpP1Val}</li>
                                            <li op="${item2.uscpP1Op}">
                                                <c:choose>
                                                    <c:when test="${item2.uscpP1Op=='lt'}">＜</c:when>
                                                    <c:when test="${item2.uscpP1Op=='lte'}">≤</c:when>
                                                    <c:when test="${item2.uscpP1Op=='eq'}">＝</c:when>
                                                    <c:when test="${item2.uscpP1Op=='gt'}">＞</c:when>
                                                    <c:when test="${item2.uscpP1Op=='gte'}">≥</c:when>
                                                </c:choose>
                                            </li>
                                            <li style="font-weight: bold">ｎ</li>
                                            <li op="${item2.uscpP2Op}">
                                                <c:choose>
                                                    <c:when test="${item2.uscpP2Op=='lt'}">＜</c:when>
                                                    <c:when test="${item2.uscpP2Op=='lte'}">≤</c:when>
                                                    <c:when test="${item2.uscpP2Op=='eq'}">＝</c:when>
                                                    <c:when test="${item2.uscpP2Op=='gt'}">＞</c:when>
                                                    <c:when test="${item2.uscpP2Op=='gte'}">≥</c:when>
                                                </c:choose>
                                            </li>
                                            <li>${item2.uscpP2Val}</li>
                                        </ul>
                                    </dd>
                                </c:forEach>
                            </dl>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${item.uscUse=='y'}">사용</c:when>
                                <c:when test="${item.uscUse=='n'}">미사용</c:when>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>

            </tbody>
        </table>
        <div class="buttons">
            <button type="button" class="createCriterion">평가 기준 생성</button>
        </div>
    </div>
    <div class="editWrap" style="display: none;">
        <div class="title typeTitle"></div>
        <div class="talbeArea editbox">
            <form id="editForm" class="editForm" method="post" name="editForm" onsubmit="return checkEditForm()">
                <input type="hidden" id="editMode" name="mode" value="" />
                <input type="hidden" name="bcy_cod" value="${bcy_cod}" />
                <input type="hidden" id="editKind" name="editKind" value="${item.uscKindCod}" />
                <table summary="">
                    <colgroup>
                        <col width="20%" /><col width="15%" /><col width="" /><col width="20%" />
                    </colgroup>
                    <thead>
                    <tr>
                        <th>평가 기준 종류</th>
                        <th>평가 기준 구분</th>
                        <th>점수별 구간</th>
                        <th>사용여부</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>
                            <select id="editKindNew">
                                <option value="">기준 선택</option>
                                <c:forEach var="item" items="${listCriterion}" varStatus="status">
                                    <option value="${item.uscKindCod}" bcy_cod="${item.uscBcyCod}" kind_type="${item.uscKindType}" use="${item.uscUse}">${item.uscKindTitle}</option>
                                </c:forEach>
                            </select>
                            <input type="text" id="editKindEdit" value="" readonly />
                        </td>
                        <td class="editKindType"></td>
                        <td>
                            <dl class="editSection">
                                <c:forEach var="num" begin="1" end="5" step="1" varStatus="status">
                                    <dt>${num} 점</dt>
                                    <dd>
                                        <ul>
                                            <li><input type="text" id="editPoint${num}Val1" name="editPoint${num}Val1" value="" /></li>
                                            <li>
                                                <select id="editPoint${num}Op1" name="editPoint${num}Op1">
                                                    <option value=""></option>
                                                    <option value="lt">＜</option>
                                                    <option value="lte">≤</option>
                                                    <option value="eq">＝</option>
                                                    <option value="gt">＞</option>
                                                    <option value="gte">≥</option>
                                                </select>
                                            </li>
                                            <li style="font-weight: bold">ｎ</li>
                                            <li>
                                                <select id="editPoint${num}Op2" name="editPoint${num}Op2">
                                                    <option value=""></option>
                                                    <option value="lt">＜</option>
                                                    <option value="lte">≤</option>
                                                    <option value="eq">＝</option>
                                                    <option value="gt">＞</option>
                                                    <option value="gte">≥</option>
                                                </select>
                                            </li>
                                            <li><input type="text"id="editPoint${num}Val2" name="editPoint${num}Val2" value="" /></li>
                                        </ul>
                                    </dd>
                                </c:forEach>
                            </dl>
                        </td>
                        <td>
                            <label><input type="radio" id="editUseY" name="editUse" value="y" checked /> 사용</label>
                            <label><input type="radio" id="editUseN" name="editUse" value="n" /> 미사용</label>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <div class="buttons">
                    <button type="submit" class="typeTitle"></button>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>
