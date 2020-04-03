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

    </style>

    <script type="text/javascript" src="/common/js/jquery-3.4.1.min.js"></script>
    <script type="text/javascript" src="/common/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/common/js/lodash.js"></script>
    <script type="text/javascript" src="/common/js/vue.js"></script>
    <script type="text/javascript" src="/common/js/common.js"></script>
    <script>
        $.ajaxSetup({ cache: false });
    </script>
    <%@ include file="/WEB-INF/include/FileManagerVueComponent.jsp"%>

    <script>
        $(document).ready(function () {

            $("#search-form").on("submit", function () {

                var searchBcyCode = $("[name='bcyCode']").val();
                var searchStandard = $("[name='standard']").val();
                var searchService = $("[name='service']").val();

                if (searchBcyCode == "" || searchStandard == "" || searchService == "") {
                    alert("운영주기, 컴플라이언스, 서비스를 선택하세요.")
                    return false;
                }

                return true;
            })

            var activeControlItem = $("#control-${searchVO.controlItem}");

            if (activeControlItem.length > 0) {
                $("#control-item-list-column-frame").scrollTop($("#control-${searchVO.controlItem}").offset().top - 65)
            }

            new Vue({
                el: '#self-file-manager',
                data: {},
                methods: {
                    saveFiles: function () {
                        this.$refs.fileManager.saveFiles();
                    }
                }

            })

            $("#inspection-result-form").on("submit", function (event) {
                if ( $("[name='selectedAnswer[]']:checked", this).val() === undefined ) {
                    if (confirm("항목 결과를 선택하지 않으셨습니다. 해당 항목의 결과를 초기화 하시겠습니까?")) {
                        return true;
                    } else {
                        location.reload(true);
                        return false;
                    }
                }
                return true;
            })

            $("#download-result-excel-form").on("submit", function (event) {
                if ( ! checkSearchCondition()) {
                    alert("조회된 결과가 없습니다. 다시 검색해주시기 바랍니다.")
                    return false;
                }
                return true;
            })

            $("#upload-result-excel-form > button").on("click", function (event) {
                if ( ! checkSearchCondition()) {
                    return alert("조회된 결과가 없습니다. 다시 검색해주시기 바랍니다.")
                }
                window.open("/inspt/FM-INSPT006_UPLOAD_EXCEL_POPUP.do", "uploadExcelPopup", 'width=600,height=250');
                $("#upload-result-excel-form").submit();
            })

            $("#smart-upload-form > button").on("click", function (event) {
                if ( ! checkSearchCondition()) {
                    return alert("조회된 결과가 없습니다. 다시 검색해주시기 바랍니다.")
                }
                window.open("/inspt/FM-INSPT006_SMART_UPLOAD_POPUP.do", "smartUploadPopup", 'width=600,height=500');
                $("#smart-upload-form").submit();
            })

            $("#upload-zip-form").on("submit", function (event) {
                if ( ! checkSearchCondition()) {
                    alert("조회된 결과가 없습니다. 다시 검색해주시기 바랍니다.");
                    return false;
                }

                var fileName = $("[name='zipFile']").val();
                var fileNameToken = fileName.split(".");
                var fileExtension = fileNameToken[fileNameToken.length - 1];
                if (fileName === "") {
                    alert("파일을 선택하세요.");
                    return false;
                }
                if (fileExtension !== "zip") {
                    alert("ZIP 파일형태만 지원합니다.");
                    return false;
                }
                return true;
            })

        })
        
        function checkSearchCondition() {
            var bcyCode = $("#bcyCode-select").val();
            var standard = $("#standard-select").val();
            var service = $("#service-select").val();
            var controlItemCount = $(".conpliance-control-item").length;

            return bcyCode != "" && standard != "" && service != "" && controlItemCount > 0
        }
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
                <a class="nav-link" href="${currentUrl}">기반시설 통제항목별 평가 관리</a>
            </li>
        </ul>
        <form id="smart-upload-form" action="/inspt/FM-INSPT006_SMART_UPLOAD_POPUP.do" method="get" class="form-inline my-2 my-lg-0 mr-3" target="smartUploadPopup">
            <c:forTokens items="<%=request.getQueryString()%>" delims="&" var="query" varStatus="queryStatus">
                <input type="hidden" name="${fn:split(query, '=')[0]}" value="${fn:split(query, '=')[1]}">
            </c:forTokens>
            <button class="btn btn-outline-secondary my-2 my-sm-0" type="button"><i class="fa fa-upload" aria-hidden="true"></i> 스마트 업로드</button>
        </form>
        <form id="upload-result-excel-form" action="/inspt/FM-INSPT006_UPLOAD_EXCEL_POPUP.do" method="get" class="form-inline my-2 my-lg-0 mr-3" target="uploadExcelPopup">
            <c:forTokens items="<%=request.getQueryString()%>" delims="&" var="query" varStatus="queryStatus">
                <input type="hidden" name="${fn:split(query, '=')[0]}" value="${fn:split(query, '=')[1]}">
            </c:forTokens>
            <button class="btn btn-outline-secondary my-2 my-sm-0" type="button"><i class="fa fa-upload" aria-hidden="true"></i> 평가결과 엑셀 업로드</button>
        </form>
        <form id="download-result-excel-form" action="/inspt/FM-INSPT006_DOWNLOAD_EXCEL.do" method="post" enctype="multipart/form-data" class="form-inline my-2 my-lg-0 mr-3">
            <c:forTokens items="<%=request.getQueryString()%>" delims="&" var="query" varStatus="queryStatus">
                <input type="hidden" name="${fn:split(query, '=')[0]}" value="${fn:split(query, '=')[1]}">
            </c:forTokens>
            <button class="btn btn-outline-secondary my-2 my-sm-0" type="submit"><i class="fa fa-download" aria-hidden="true"></i> 평가결과 엑셀 다운로드</button>
        </form>
        <form id="upload-zip-form" action="/inspt/FM-INSPT006_SAVE_FILE_IN_ZIP.do" method="post" enctype="multipart/form-data" class="form-inline my-2 my-lg-0">
            <input type="hidden" name="zipBcyCode" value="${searchVO.bcyCode}">
            <input type="hidden" name="zipStandard" value="${searchVO.standard}">
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
                                    <option <c:if test="${searchVO.bcyCode eq cycle.code}">selected="selected"</c:if>
                                            value="${cycle.code}">${cycle.name}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="standard-select" class="text-gray-700">컴플라이언스</label>
                            <select class="form-control" name="standard" id="standard-select">
                                <option value="">::선택하세요</option>
                                <c:forEach items="${complianceList}" var="compliance" varStatus="status">
                                    <option <c:if test="${searchVO.standard eq compliance.uccSndCod}">selected="selected"</c:if>
                                            value="${compliance.uccSndCod}">${compliance.uccSndNam}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="service-select" class="text-gray-700">서비스</label>
                            <select class="form-control" name="service" id="service-select">
                                <option value="">::선택하세요</option>
                                <c:forEach items="${serviceList}" var="service" varStatus="status">
                                    <option <c:if test="${searchVO.service eq service.uomSvcCod}">selected="selected"</c:if>
                                            value="${service.uomSvcCod}">${service.uomSvcNam}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="workState-select" class="text-gray-700">업무 완료여부</label>
                            <select class="form-control" name="workState" id="workState-select">
                                <option value="">모두</option>
                                <option
                                        <c:if test="${searchVO.workState eq 'initiated'}">selected="selected"</c:if>
                                        value="initiated">미완료
                                </option>
                                <option
                                        <c:if test="${searchVO.workState eq 'finished'}">selected="selected"</c:if>
                                        value="finished">완료
                                </option>
                            </select>
                        </div>

                        <button class="btn btn-secondary btn-block mb-3" type="submit">검색</button>

                    </form>

                </div>

            </div>

        </div>
        <div id="control-item-list-column-frame" class="col-4">

            <div id="control-item-list-column" class="work-column">

                <div class="work-column-header">
                    평가 항목 목록
                </div>

                <div class="work-column-body">

                    <c:if test="${empty controlItemList}">
                        <span class="empty-value"></span>
                    </c:if>

                    <c:forEach items="${controlItemList}" var="controlItem" varStatus="status">

                        <div id="control-${controlItem.ucmCtrKey}" class="conpliance-control-item card <c:if test="${controlItem.ucmCtrKey eq searchVO.controlItem}">active</c:if>">
                            <div class="card-header">
                                <a href="?${controlItem.urlQuery}">${controlItem.ucm3lvCod}</a>
                                <small class="float-right">
                                    <c:choose>
                                        <c:when test="${controlItem.completed eq 'true'}">
                                            <span class="related-control-item-number badge badge-primary">완료</span>
                                        </c:when>
                                        <c:when test="${controlItem.completed eq 'false'}">
                                            <span class="related-control-item-number badge badge-danger">미완료</span>
                                        </c:when>
                                    </c:choose>
                                </small>
                            </div>
                            <div class="card-body">
                                    ${controlItem.ucm3lvNam}
                            </div>
                        </div>

                    </c:forEach>

                </div>

            </div>

        </div>
        <div id="control-item-column-frame" class="col-4">

            <div id="control-item-column" class="work-column">

                <div class="work-column-header">
                    평가 항목 정보
                </div>
                <div class="work-column-body">

                    <c:if test="${empty controlItem.ucm_ctr_key and empty answerList}">
                        <span class="empty-value"></span>
                    </c:if>

                    <c:if test="${not empty controlItem.ucm_ctr_key}">

                    <div class="card">
                        <div class="card-header text-gray-700">
                            항목 기본정보
                        </div>
                        <div class="card-body">

                            <form>
                                <div class="form-group">
                                    <label class="text-gray-700">통제 분야</label>
                                    <p>
                                        <span class="badge badge-secondary">${controlItem.ucm_1lv_cod}</span>
                                        ${controlItem.ucm_1lv_nam}
                                    </p>
                                </div>
                                <div class="form-group">
                                    <label class="text-gray-700">통제 항목</label>
                                    <p>
                                        <span class="badge badge-secondary">${controlItem.ucm_2lv_cod}</span>
                                        ${controlItem.ucm_2lv_nam}
                                    </p>
                                </div>
                                <div class="form-group">
                                    <label class="text-gray-700">평가 내용</label>
                                    <p>
                                        <span class="badge badge-secondary">${controlItem.ucm_3lv_cod}</span>
                                        ${controlItem.ucm_3lv_nam}
                                    </p>
                                </div>
                            </form>

                        </div>
                    </div>

                    </c:if>

                    <c:if test="${not empty answerList}">

                    <form id="inspection-result-form" method="post" action="/inspt/FM-INSPT006_ANSWER_RESULT_RW.do">

                        <c:forTokens items="<%=request.getQueryString()%>" delims="&" var="query" varStatus="queryStatus">
                            <input type="hidden" name="${fn:split(query, '=')[0]}" value="${fn:split(query, '=')[1]}">
                        </c:forTokens>

                        <div class="card">
                            <div class="card-header">
                                <span class="text-gray-700">항목 결과</span>
                                <button type="submit" class="btn btn-outline-secondary btn-sm float-right">저장</button>
                            </div>
                            <div class="card-body">

                                <ul class="list-group list-group-flush">
                                    <c:forEach items="${answerList}" var="answer" varStatus="status">
                                        <li class="list-group-item pl-0">
                                            <div class="form-check">
                                                <input
                                                    <c:choose>
                                                        <c:when test="${selectedComplianceKind eq 'infra_mp'}">id="answer-${answer.title}"</c:when>
                                                        <c:otherwise>id="answer-${answer.id}"</c:otherwise>
                                                    </c:choose>
                                                    class="form-check-input"
                                                    <%--과기정통부 체크리스트는 다중 선택 => checkbox--%>
                                                    <%--기반시설 수준평가, 물리/관리 취약점은 단일 선택 => radio--%>
                                                    <c:choose>
                                                        <c:when test="${selectedComplianceKind eq 'msit'}">type="checkbox"</c:when>
                                                        <c:otherwise>type="radio"</c:otherwise>
                                                    </c:choose>
                                                       name="selectedAnswer[]"
                                                    <c:choose>
                                                        <c:when test="${selectedComplianceKind eq 'infra_mp'}">value="${answer.title}"</c:when>
                                                        <c:otherwise>value="${answer.id}"</c:otherwise>
                                                    </c:choose>
                                                    <c:if test="${answer.selected eq 'true'}">checked="checked"</c:if>>
                                                <label class="form-check-label" for="answer-${answer.id}">
                                                    <span class="mr-1">${answer.seq}.</span> ${answer.title}
                                                </label>
                                            </div>
                                        </li>
                                    </c:forEach>
                                </ul>

                            </div>
                        </div>

                    </form>

                    </c:if>

                    <c:if test="${not empty answerList}">

                    <form id="inspection-result-form" method="post" action="/inspt/FM-INSPT006_INFO_RESULT_RW.do">

                        <c:forTokens items="<%=request.getQueryString()%>" delims="&" var="query" varStatus="queryStatus">
                            <input type="hidden" name="${fn:split(query, '=')[0]}" value="${fn:split(query, '=')[1]}">
                        </c:forTokens>

                        <div class="card">
                            <div class="card-header">
                                <span class="text-gray-700">평가 결과</span>
                                <button type="submit" class="btn btn-outline-secondary btn-sm float-right">저장</button>
                            </div>
                            <div class="card-body">

                                <div class="form-group">
                                    <label>점검결과</label>
                                    <textarea class="form-control" name="result-detail" rows="3">${additionalInfo.uidRstDtl}</textarea>
                                </div>

                                <c:if test="${selectedComplianceKind eq 'infra_mp' or selectedComplianceKind eq 'infra_la'}">
                                    <div class="form-group">
                                        <label>정책, 지침</label>
                                        <textarea class="form-control" name="result-related-document" rows="3">${additionalInfo.uidRltDoc}</textarea>
                                    </div>
                                </c:if>

                            </div>
                        </div>
                    </form>

                    </c:if>

                </div>

            </div>

        </div>
        <div id="file-column-frame" class="col-2">

            <div id="file-column" class="work-column">

                <div class="work-column-header">
                    평가 항목 증적들
                </div>
                <div class="work-column-body">

                    <c:if test="${empty controlItem.ucm_ctr_key and empty answerList}">
                        <span class="empty-value"></span>
                    </c:if>

                    <c:if test="${not empty controlItem.ucm_ctr_key and not empty answerList}">
                        <div id="self-file-manager" class="card">
                            <div class="card-header text-gray-700">평가 항목 자체 증적</div>
                            <div class="card-body">
                                <file-manager ref="fileManager"
                                              list-url="/inspt/FM-INSPT006_FILELIST_OF_CONTROL.do"
                                              save-url="/inspt/FM-INSPT006_SAVE_FILE_OF_CONTROL.do"
                                              tbl-gbn="IST"
                                              con-gbn="${searchVO.standard}"
                                              :con-key="${controlItem.ucm_ctr_key}"></file-manager>
                            </div>
                            <div class="card-footer">
                                <button class="btn btn-sm btn-outline-secondary" v-on:click="saveFiles()">저장</button>
                            </div>
                        </div>
                    </c:if>

                    <c:forEach items="${fileListGroupByActivity}" var="activity" varStatus="activityStatus">
                    <div class="card">
                        <div class="card-header text-gray-700">
                            ${activity.utdDocNam}
                        </div>
                        <div class="card-body">

                            <c:if test="${empty activity.files}">
                                <span class="empty-value"></span>
                            </c:if>

                            <ul>
                                <c:forEach items="${activity.files}" var="file" varStatus="fileStatus">
                                <li class="mb-2">
                                    <a href="/common/getFileDown.do?downKey=${file.umfFleKey}" target="_blank">${fileStatus.count}. ${file.umfConFnm}</a>
                                </li>
                                </c:forEach>
                            </ul>

                        </div>
                        <div class="card-footer">
                            <button class="btn btn-sm btn-outline-secondary"
                                    onclick="docPopup(${activity.utdTrcKey})"
                                    data-target="#exampleModal">활동 보기
                            </button>
                        </div>
                    </div>
                    </c:forEach>

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