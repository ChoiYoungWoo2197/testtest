<%@ page import="com.uwo.isms.domain.WorkVO.statusType" %>
<%@ page import="com.uwo.isms.domain.WorkVO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="/WEB-INF/include/contents.jsp" %>

<!doctype html>
<html class="no-js" lang="ko">

<head>
    <meta charset="utf-8">
    <title></title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Cache-Control" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>
    <meta http-equiv="Pragma" content="no-cache"/>

    <link rel="stylesheet" type="text/css" href="/common/css/font-awesome.min.css"/>
    <link rel="stylesheet" type="text/css" href="/common/css/tailwind.min.css"/>
    <link rel="stylesheet" type="text/css" href="/common/css/bootstrap.min.css"/>

    <style>

        html {
            font-size: 12px;
        }

        .navbar {
            min-width: 1260px;
            border-top: 3px solid #ff5a20;
            padding-left: 22px;
        }

        .navbar-brand > img {
            height: 22px;
            width: auto;
        }

        .main-row > div[class^="col-"] {
            height: 100vh;
            border-right: 1px solid #ccc;
            padding-top: 46px;
            overflow-y: scroll;
            overflow-x: hidden;
            background-color: #f8f9fa;
        }

        .main-row > div[class^="col-"]:last-child {
            border-right: 0;
        }

        .work-column {
            position: relative;
        }

        .work-column-header {
            /*height: 30px;*/
            padding: 0.5rem 0;
            margin-left: -15px;
            margin-right: -15px;
            border-bottom: 1px solid #ccc;
            background-color: #ede9e7;
            text-align: center;
            /*display: none;*/
        }

        .work-column-body {
            padding: 1rem 0.5rem 2rem;
        }

        .card {
            margin-bottom: 1.5rem;
        }

        .card.active {
            outline: 3px solid #007bff;
            outline-offset: -3px;
        }

        .nav-item.active > .nav-link {
            color: #dc3545 !important;
        }

        .empty-value {
            font-size: 0.8rem;
            color: #aaa;
        }
        .empty-value::before {
            content: "일치하는 정보가 없습니다.";
        }

        .custom-file {
            width: auto;
        }

        .custom-file-label {
            padding-right: 58px;
        }

        .fa.fa-minus {
            font-size: 1rem;
        }

        .nav-item.active > .nav-link {
            color: #e0002a !important;
        }

        .badge-danger {
            background-color: #ef8009;
        }


        .table .thead-light th {
            background-color: #ede9e7;
        }

    </style>

<%--    <script type="text/javascript" src="/common/js/jquery-3.4.1.min.js"></script>--%>
    <script type="text/javascript" src="/common/js/jquery.js"></script>
    <script type="text/javascript" src="/common/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/common/js/lodash.js"></script>
    <script type="text/javascript" src="/common/js/vue.js"></script>
    <script type="text/javascript" src="/common/js/common.js"></script>
    <script type="text/javascript" src="/common/js/jquery.chained.min.js"></script>
    <script>
        $.ajaxSetup({ cache: false });
    </script>
    <%@ include file="/WEB-INF/include/MiniFileManagerVueComponent.jsp"%>

    <script>

        function getFile(key){
            $("#downKey").val(key);
            document.fileDown.action = "${pageContext.request.contextPath}/common/getFileDown.do";
            document.fileDown.submit();
        }

        var workStatusValueText = {
            <c:forEach items="<%=WorkVO.statusType.values()%>" var="sType">
            "${sType.value}": "${sType}",
            </c:forEach>
        };

        function replaceAll(str,ori,rep){
            return str.split(ori).join(rep);
        }

        function search(){

            var userKey = "${userKey}";
            var isAdmin = "${authGbn == 'A'}" === "true";

            var $obj = $("#workList");
            $obj.empty();

            var bcyCode = $("#bcyCode-select").val();
            var depLevel1 = $("#stOrg").val();
            var depLevel2 = $("#hqOrg").val();
            var depLevel3 = $("#gpOrg").val();
            var onlyAssignedWork = $("#assign-filter").val();

            var lastDepCode = depLevel3 ? depLevel3 : depLevel2 ? depLevel2 : depLevel1;

            if (bcyCode == "") {
                alert("운영 주기를 선택하세요.");
                return false;
            }

            if (depLevel1 == "") {
                alert("본부 정보를 선택하세요.");
                return false;
            }

            $.ajax({
                url : "${pageContext.request.contextPath}/inspt/FM-INSPT007_GET_DATA.do",
                type		: "post",
                data		: { bcyCode: bcyCode, depLevel1: depLevel1, depLevel2: depLevel2,
                    depLevel3: depLevel3, onlyAssignedWork: onlyAssignedWork },
                dataType	: "json",
                success	: function(data){
                    workListOfControlItem = data.resultList;

                    var saFiles = data.saFileList;
                    var search = data.search;

                    var arrRow = Array();
                    var workListHTML = "";
                    var loopCurrentUtdTrcKey = null;
                    var loopCurrentUcmGolNo = null;
                    var checkedControlList = [];
                    var tempRowSpan = null;

                    for(var i=0; i<data.resultList.length; i++) {

                        workListHTML = "";
                        // 속도문제로 rowspan() 대처함
                        var ctrKey = data.resultList[i].ucmCtrKey;
                        if (i > 0 && data.resultList[i-1].ucm2lvCod == data.resultList[i].ucm2lvCod) {
                            ucm2lvCod = '';
                            arrRow[0]++;
                        } else {
                            ucm2lvCod = '<td class="ucm2lvCod">'+data.resultList[i].ucm2lvCod+'</td>';
                            $obj.find(".ucm2lvCod:last").attr("rowspan", arrRow[0]);
                            arrRow[0] = 1;
                        }
                        if (i > 0 && data.resultList[i-1].ucm2lvNam == data.resultList[i].ucm2lvNam) {
                            ucm2lvNam = '';
                            arrRow[1]++;
                        } else {
                            ucm2lvNam = '<td class="ucm2lvNam">'+data.resultList[i].ucm2lvNam+'</td>';
                            $obj.find(".ucm2lvNam:last").attr("rowspan", arrRow[1]);
                            arrRow[1] = 1;
                        }
                        if (i > 0 && data.resultList[i-1].ucm3lvNam == data.resultList[i].ucm3lvNam) {
                            ucm3lvNam = '';
                            selfFileManager = '';
                            arrRow[2]++;
                            tempRowSpan = null;
                        } else {
                            ucm3lvNam = '<td class="ucm3lvCod fontLeft">'+data.resultList[i].ucmGolNo+'</td>';
                            ucm3lvNam += '<td class="ucm3lvNam fontLeft">'+data.resultList[i].ucm3lvNam+'</td>';

                            selfFileManager = '<td data-control="' + ucmCtrKey + '" id="self-file-manager-'
                                + ucmCtrKey + '" class="self-file-manager">';
                            selfFileManager += '<file-manager ref="fileManager" ' +
                                'list-url="/inspt/FM-INSPT007_FILELIST_OF_CONTROL.do" ' +
                                'save-url="/inspt/FM-INSPT007_SAVE_FILE_OF_CONTROL.do" ' +
                                'tbl-gbn="SA" ' +
                                'con-gbn="' + lastDepCode + '" ' +
                                ':con-key="' + data.resultList[i].ucmCtrKey + '"></file-manager>';
                            selfFileManager += '</td>';

                            $obj.find(".ucm3lvCod:last").attr("rowspan", arrRow[2]);
                            $obj.find(".ucm3lvNam:last").attr("rowspan", arrRow[2]);
                            $obj.find(".self-file-manager:last").attr("rowspan", arrRow[2]);
                            arrRow[2] = 1;
                        }

                        var docNam = data.resultList[i].utdDocNam;
                        var fileName = data.resultList[i].fileName;
                        var termGbn = data.resultList[i].termGbn;

                        var utwSvcCod = data.resultList[i].utwSvcCod;
                        var utwDepNam = data.resultList[i].utwDepNam;
                        var workerKey = data.resultList[i].workerId;
                        var worker = data.resultList[i].worker;
                        var ucmCtrGbn = data.resultList[i].ucmCtrGbn;
                        var utwWrkKey = data.resultList[i].utwWrkKey;
                        var utdTrcKey = data.resultList[i].utdTrcKey;
                        var ucmGolNo = data.resultList[i].ucmGolNo;
                        var ucmCtrKey = data.resultList[i].ucmCtrKey;
                        var wrkPrg = data.resultList[i].wrkPrg;
                        var wrkDtl = data.resultList[i].wrkDtl;

                        var changedUcmGolNo = false;
                        var changedUtdTrcKey = false;

                        if (loopCurrentUcmGolNo !== ucmGolNo) {
                            loopCurrentUcmGolNo = ucmGolNo;
                            loopCurrentUtdTrcKey = null;
                            changedUcmGolNo = true;
                        } else {
                            changedUcmGolNo = false;
                        }

                        if (loopCurrentUtdTrcKey !== utdTrcKey) {
                            loopCurrentUtdTrcKey = utdTrcKey;
                            changedUtdTrcKey = true;
                        } else {
                            changedUtdTrcKey = false;
                        }

                        var trClass = "";
                        var tdClass = "";
                        if( data.resultList.length == (i+1)) {
                            trClass = "last";
                        }

                        // 2018-10-12 s,
                        if (data.resultList[i].utwWrkSta != "90") {
                            tdClass = "notComp";
                        }


                        workListHTML += '<tr class="'+ trClass +'">';
                        workListHTML += ucm2lvCod;
                        workListHTML += ucm2lvNam;
                        workListHTML += ucm3lvNam;

                        if (changedUtdTrcKey) {
                            var rowspanCount = _.filter(data.resultList, function(el) {
                                return el.ucmGolNo === ucmGolNo && el.utdTrcKey === utdTrcKey
                            }).length;

                            workListHTML += '<td class="'+ tdClass +'" rowspan="' + rowspanCount + '">';
                            if (isAdmin) {
                                workListHTML += '<a class="link-text" href="javascript:docPopup(' + utdTrcKey + ')">'+docNam+'</a>';
                            } else {
                                workListHTML += '<span>' + docNam + '</span>';
                            }

                            workListHTML += '</td>';
                        }

                        if (changedUcmGolNo && loopCurrentUtdTrcKey === null) {
                            workListHTML += '<td>-</td>';
                        }

                        workListHTML += '<td class="'+ tdClass +'">'+utwDepNam+'</td>';

                        workListHTML += '<td class="'+ tdClass +'">';

                        if (isAdmin || userKey == workerKey) {
                            workListHTML += '<a class="link-text" href="javascript:workPopup('+utwWrkKey+','+utdTrcKey+')">' + worker + '</a>';
                        } else {
                            workListHTML += '<span>' + worker + '</span>';
                        }
                        workListHTML += '</td>';

                        if (utwWrkKey === null) {
                            workListHTML += '<td>-</td>';
                            workListHTML += '<td>-</td>';
                        } else {
                            workListHTML += '<td>' + workStatusValueText[wrkPrg] + '</td>';
                            workListHTML += '<td>' + wrkDtl + '</td>';
                        }

                        workListHTML += '<td>';
                        workListHTML += '<div class="uplidFileList">';
                        workListHTML += '<ul class="listArea listControl" style="width: 120px; height: auto; min-height: 22px; margin: 0;">';

                        var hasFile = false;
                        for(var j=0; j<data.fileList.length; j++) {
                            if(utwWrkKey == data.fileList[j].umfConKey) {
                                hasFile = true;
                                workListHTML += '<li title="'+data.fileList[j].umfConFnm+'">';
                                workListHTML += '<a href="javascript:getFile(\''+data.fileList[j].umfFleKey+'\')">'+data.fileList[j].umfConFnm+'</a>';
                                workListHTML += '</li>';
                            }
                        }

                        if ( ! hasFile) {
                            workListHTML += "<li style='color: #ccc'>자료가 없습니다.</li>";
                        }

                        workListHTML += '</ul>';

                        workListHTML += '</div></td>';

                        // HERE
                        // workListHTML += selfFileManager;

                        workListHTML += '</tr>';
                        workListHTML = replaceAll(workListHTML, 'null', '-');
                        $obj.append(workListHTML);
                    }

                    // last cell
                    $obj.find(".ucm2lvCod:last").attr("rowspan", arrRow[0]);
                    $obj.find(".ucm2lvNam:last").attr("rowspan", arrRow[1]);
                    $obj.find(".ucm3lvCod:last").attr("rowspan", arrRow[2]);
                    $obj.find(".ucm3lvNam:last").attr("rowspan", arrRow[2]);

                    // 부모 td 보다 height가 클 경우에만 scrollbar가 보여짐
                    $(".ucmDescription").each(function() {
                        var size = parseInt($(this).parent().attr("rowspan")) * 96 - 19; // td height: 96, title height: 19
                        $(this).css("max-height", size + "px");
                    });

                    if(workListHTML == ""){
                        workListHTML += '<tr class="last"><td class="last no-data-list" colspan="9">검색결과가 없습니다.</td></tr>';
                        $obj.append(workListHTML);
                    }

                    $(".self-file-manager").each(function (element) {
                        new Vue({
                            el: "#" + $(this).attr("id"),
                            data: {}
                        })
                    })

                }
            });
        }

        $(document).ready(function() {
            stOrgList();

            $("#zipfile-upload-form").on("submit", function (event) {
                var bcyCode = $("#bcyCode-select").val();
                var depLevel1 = $("#stOrg").val();
                var depLevel2 = $("#hqOrg").val();
                var depLevel3 = $("#gpOrg").val();
                var lastDepCode = depLevel3 ? depLevel3 : depLevel2 ? depLevel2 : depLevel1;
                $("[name='zipBcyCode']").val(bcyCode);
                $("[name='zipDepCode']").val(lastDepCode);

                if (lastDepCode == "" || bcyCode == "") {
                    alert("정보가 없습니다.");
                    return false;
                }

                return true;
            })

            $("#excel-download-form").on("submit", function () {
                var bcyCode = $("#bcyCode-select").val();
                var stOrg = $("#stOrg").val();
                var hqOrg = $("#hqOrg").val();
                var gpOrg = $("#gpOrg").val();
                var org = gpOrg != "" ? gpOrg : hqOrg != "" ? hqOrg : stOrg;

                if (bcyCode == "" || org == "") {
                    alert("정보를 선택하세요.")
                    return false;
                }

                if ($("#workList > tr > td.no-data-list").length > 0) {
                    alert("검색된 정보가 없습니다.")
                    return false;
                }

                $("[name='bcyCode']", this).val(bcyCode);
                $("[name='depCode']", this).val(org);
                return true;
            })

            $("#zip-download-form").on("submit", function () {
                var bcyCode = $("#bcyCode-select").val();
                var stOrg = $("#stOrg").val();
                var hqOrg = $("#hqOrg").val();
                var gpOrg = $("#gpOrg").val();
                var org = gpOrg != "" ? gpOrg : hqOrg != "" ? hqOrg : stOrg;

                if (bcyCode == "" || org == "") {
                    alert("정보를 선택하세요.")
                    return false;
                }

                if ($("#workList > tr > td.no-data-list").length > 0) {
                    alert("검색된 정보가 없습니다.")
                    return false;
                }
                
                if($(".listArea a").length == 0) {
                	alert("다운로드할 증적자료가 없습니다.");
					return false;
                }

                $("[name='bcyCode']", this).val(bcyCode);
                $("[name='depCode']", this).val(org);
                return true;
            })

            $("#excel-upload-form > button").on("click", function (event) {
                var bcyCode = $("#bcyCode-select").val();
                $("#excel-upload-form [name='bcyCode']").val(bcyCode);
                window.open("/inspt/FM-INSPT007_MANAGER_EXCEL_UPLOAD_POPUP.do", "uploadExcelPopup", 'width=600,height=250');
                $("#excel-upload-form").submit();
            })

            <c:choose>
                <c:when test="${authGbn == 'A'}">

                </c:when>
                <c:otherwise>

            $("#stOrg").attr("disabled", true);
            $("#stOrg").val("${stOrg}");
            $("#hqOrg").val("${hqOrg}");
            $("#gpOrg").val("${gpOrg}");

                </c:otherwise>
            </c:choose>

        });

    </script>

</head>

<body>

<nav class="navbar fixed-top navbar-expand-lg navbar-light bg-light" style="border-bottom: 1px solid #ccc;">
    <a class="navbar-brand" href="#">
        <img src="/common/images/common/logo/logo.png" alt="">
    </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse pl-3">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="${currentUrl}">Self-Audit 작업 현황</a>
            </li>
        </ul>
        <form id="excel-download-form" action="/inspt/FM-INSPT007_MANAGER_EXCEL_DOWNLOAD.do" method="get" class="form-inline mr-2">
            <input type="hidden" name="bcyCode" value="">
            <input type="hidden" name="depCode" value="">
            <button class="btn btn-outline-secondary mr-2" type="submit" style="border: 1px solid #ced4da">
                <i class="fa fa-download" aria-hidden="true"></i> 작업 현황 다운로드
            </button>
        </form>

        <c:if test="${authGbn == 'A'}">
        <form id="excel-upload-form" action="/inspt/FM-INSPT007_MANAGER_EXCEL_UPLOAD_POPUP.do" method="get" class="form-inline mr-2" target="uploadExcelPopup">
            <input type="hidden" name="bcyCode" value="">
            <button class="btn btn-outline-secondary mr-2" type="button" style="border: 1px solid #ced4da">
                <i class="fa fa-upload" aria-hidden="true"></i> 작업 현황 업로드
            </button>
        </form>
        </c:if>

        <form id="zip-download-form" class="form-inline mr-2" action="/inspt/FM-INSPT007_MANAGER_ZIP_DOWNLOAD.do" method="get">
            <input type="hidden" name="bcyCode" value="">
            <input type="hidden" name="depCode" value="">
            <button class="btn btn-outline-secondary mr-2" type="submit" style="border: 1px solid #ced4da">
                <i class="fa fa-download" aria-hidden="true"></i> 증적자료 다운로드
            </button>
        </form>
        <%--
        <form id="zipfile-upload-form" action="/inspt/FM-INSPT007_SAVE_FILE_IN_ZIP.do" method="post" enctype="multipart/form-data" class="form-inline my-2 my-lg-0">
            <input type="hidden" name="zipBcyCode" value="">
            <input type="hidden" name="zipDepCode" value="">
            <div class="input-group mr-3">
                <div class="input-group-prepend">
                    <span class="input-group-text cursor-pointer" data-toggle="modal" data-target="#zip-file-upload-guide-modal">
                        <i class="fa fa-info-circle mr-2" aria-hidden="true" style="font-size: 1.2rem"></i> 증적 일괄 업로드 가이드
                    </span>
                </div>
                <div class="custom-file">
                    <input type="file" class="custom-file-input" id="inputGroupFile01" name="zipFile" aria-describedby="inputGroupFileAddon01">
                    <label class="custom-file-label" for="inputGroupFile01" data-browse="찾기">압축파일을 선택하세요.</label>
                </div>
            </div>
            <button class="btn btn-outline-secondary my-2 my-sm-0" type="submit">업로드</button>
        </form>
        --%>
    </div>
</nav>

<div class="container-fluid">

    <div class="row main-row">

        <div id="search-column-frame" class="col-2">

            <div id="search-column" class="work-column">

                <div class="work-column-header">검색</div>

                <div class="work-column-body">

                    <a href="${currentUrl}" class="btn btn-outline-secondary btn-block mb-3 text-gray-700">검색초기화</a>

                    <form id="search-form" method="get">

                        <div class="form-group">
                            <label for="bcyCode-select" class="text-gray-700">운영 주기</label>
                            <select class="form-control" name="bcyCode" id="bcyCode-select">
                                <option value="">::선택하세요</option>
                                <c:forEach items="${mainCycleList}" var="cycle" varStatus="status">
                                    <option value="${cycle.code}">${cycle.name}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="stOrg" class="text-gray-700">본부</label>
                            <sb:select name="stOrg" type="stOrg" forbidden="true" allText="본부전체" classes="form-control" />
                        </div>

                        <div class="form-group">
                            <label for="hqOrg" class="text-gray-700">그룹,담당</label>
                            <sb:select name="hqOrg" type="hqOrg" forbidden="true" allText="그룹,담당전체" classes="form-control" />
                        </div>

                        <div class="form-group">
                            <label for="gpOrg" class="text-gray-700">부서</label>
                            <sb:select name="gpOrg" type="gpOrg" forbidden="true" allText="팀전체" classes="form-control" />
                        </div>

                        <div class="form-group">
                            <label for="assign-filter" class="text-gray-700">통제항목 표시 범위</label>
                            <select class="form-control" name="assign-filter" id="assign-filter">
                                <option value="assigned">할당된 통제항목만 표시</option>
                                <option value="all">모든 통제항목 표시</option>
                            </select>
                        </div>

                        <button class="btn btn-secondary btn-block mb-3" onclick="search(); return false;" type="submit">검색</button>

                    </form>

                </div>

            </div>

        </div>
        <div id="control-item-list-column-frame" class="col-10">

            <div id="control-item-list-column" class="work-column">

                <div class="work-column-header">
                    작업 현황
                </div>

                <div class="work-column-body">

                    <form action="" name="fileDown" id="fileDown" method="post">
                        <input type="hidden" name="downKey" id="downKey">
                        <input type="hidden" name="where" id="where">
                    </form>
                    <form id="testViewForm" name="testViewForm" action="FM-INSPT001_popup.do" method="post" target="testViewPopup">
                        <input type="hidden" id="wKey" name="wKey" value="">
                    </form>

                    <div class="contents">
                        <div class="talbeArea">
                            <form id="listForm" name="listForm" method="post">
                                <table class="table table-bordered">
                                    <colgroup>
                                        <col width="50px">
                                        <col width="130px">
                                        <col width="70px">
                                        <col width="200px">
                                        <col width="100px">
                                        <col width="100px">
                                        <col width="60px">
                                        <col width="50px">
                                        <col width="*">
                                        <col width="200px">
<%--                                        <col width="200px">--%>
                                    </colgroup>
                                    <thead class="thead-light">
                                    <tr>
                                        <th scope="col">번호</th>
                                        <th scope="col">통제항목</th>
                                        <th scope="col">점검번호</th>
                                        <th scope="col">통제점검</th>
                                        <th scope="col">업무명</th>
                                        <th scope="col">부서</th>
                                        <th scope="col">담당자</th>
                                        <th scope="col">결과</th>
                                        <th scope="col">수행내역</th>
                                        <th scope="col">업무 증적자료</th>
<%--                                        <th scope="col" class="last">항목 증적자료</th>--%>
                                    </tr>
                                    </thead>
                                    <tbody id="workList">
                                    <tr>
                                        <td colspan="11" class="last no-data-list">검색 조건 선택 후 검색해주십시요.</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </form>
                        </div>
                    </div>

                </div>

            </div>

        </div>

    </div>

</div>

<!-- Modal -->
<div class="modal fade" id="zip-file-upload-guide-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">증적 일괄 업로드 가이드</h5>
            </div>
            <div class="modal-body">
                <h5 class="h5 text-gray-600 mt-4"><i class="fa fa-minus" aria-hidden="true"></i> 지원하는 압축파일 형태</h5>
                <p class="mb-4"><span class="badge badge-warning">ZIP</span> 형태의 압축파일만 지원합니다.</p>
                <h5 class="h5 text-gray-600"><i class="fa fa-minus" aria-hidden="true"></i> 압축파일 구조</h5>
                <p>디렉토리 이름은 컴플라이언스 통제항목 번호입니다. 검색된 "평가 항목 목록"의 통제항목 번호로 디렉토리 이름을 설정하세요.</p>
                <div class="border-t pt-5">
                    <img src="/common/images/common/inspection-zip-structure.png">
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
            </div>
        </div>
    </div>
</div>

<form id="postPopup" name="postPopup" method="post" action="/comps/FM-COMPS004_popup.do" target="S004_popup405"></form>
</body>

</html>