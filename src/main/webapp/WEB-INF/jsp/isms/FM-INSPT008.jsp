<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
<%@ include file="/WEB-INF/include/contents.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <style>
        td.notComp {
            color: #aaa !important;
        }

        td.notComp a {
            color: inherit;
        }

        #workList td, #workList .uplidFileList {
            height: 100%;
        }

        #workList .listArea.listControl {
            width: 200px;
            height: 100%;
            min-height: 1rem;
            margin: 0;
            padding: 0;
        }

        .link-text {
            color: #63b3ed !important;
        }

        .sa-box-title {
            border: 1px solid #b2b2b2;
            background: #ebebeb;
            margin-top: 0.7rem;
            border-bottom: 0;
            padding-top: 4px;
            padding-bottom: 4px;
        }

        .sa-work a.link-text {
            color: #ef8009 !important;
        }

        .sa-work-row {
            border-right: 3px solid #ef8009;
        }

        .main-table {
            border-left: 1px solid #ddd;
            border-right: 1px solid #ddd;
        }

        .work-table-parent-td, .inner-work-table {
            padding: 0 !important;
        }

        .inner-work-table {
            height: 100%;
        }

        .inner-work-table td:last-child {
            border-right: 0;
        }

        .inner-work-table tr:last-child td {
            border-bottom: 0;
        }

        .inner-work-table td[rowspan]:last-child {
            border-bottom: 0 !important;
        }

        .no-activity {
            color: #ccc;
            text-align: left;
        }

        .my-badge-info {
			display: inline-block;
            font-size: 10px;
            padding: 0.2em 0.5em;
            background-color: #eee;
            border: 1px solid #d7d8d7;
            border-radius: 0.5em;
            margin-bottom: 0.5em
        }

        .last-activity {
            border-bottom: 0 !important;
        }
    </style>
    <script>
        //첨부파일 다운로드
        function getFile(key, where) {
            $("#downKey").val(key);
            $("#where").val("wrk");
            document.fileDown.action = "${pageContext.request.contextPath}/common/getFileDown.do";
            document.fileDown.submit();
        }

        function search() {
            if(!$("#service").val()) {
                alert("서비스를 선택해주세요.");
                return;
            }

            document.searchForm.action = "FM-INSPT008.do";
            document.searchForm.submit();
        }

        $(document).ready(function () {

            /*SA증적파일 체크박스에 변화가 생겼을 때*/
            $("#listForm").on("click", "[data-safile]", function(event) {
                var checked = $(this).is(":checked") ? "Y" : "N";
                var service = $(this).data("service");
                var control = $(this).data("control");
                var file = $(this).data("safile");

                if (checked == "" || service == "" || control == "" || file == "") {
                    alert("정보가 부족합니다.")
                } else {
                    $("[data-control='" + control + "'][data-safile='" + file + "']").prop("checked", checked === "Y" ? "checked" : "");
                    $.get("FM-INSPT001_SA_FILE_SAVE.do",{fleKey: file, service: service, ctrKey: control, checked: checked}, function () {}, "json");
                }

            });

        });

    </script>

</head>
<body>
<form action="" name="fileDown" id="fileDown" method="post">
    <input type="hidden" name="downKey" id="downKey">
    <input type="hidden" name="where" id="where">
</form>
<form id="testViewForm" name="testViewForm" action="FM-INSPT001_popup.do" method="post" target="testViewPopup">
    <input type="hidden" id="wKey" name="wKey" value="">
</form>
<c:import url="/titlebar.do"/>
<form id="searchForm" action="/inspt/FM-INSPT008.do" name="searchForm" method="get">
    <div class="search">
        <div class="defalt">
            <fieldset class="searchForm">
                <legend>기본검색</legend>
                <ul class="detail">
                    <li>
                        <span class="title" style="width: 100px; line-height: 28px"><label for="standard">컴플라이언스</label></span>
                        <sb:select name="standard" type="" code="STND" value="${searchVO.standard}" allText="선택"/>
                    </li>
                    <li>
                        <span class="title" style="width: 60px; line-height: 28px"><label
                                for="service">서비스</label></span>
                        <sb:select name="service" type="S" value="${searchVO.service}" allText="선택"/>
                    </li>
                </ul>
                <button onclick="search(); return false;" class="btnSearch" style="height: 27px; margin-left: 1rem">검색
                </button>
            </fieldset>
        </div>
    </div>
</form>

<div class="contents">
    <div class="topBtnArea">
        <!-- <ul class="btnList">
            <li><button onclick="sav(); return false;"><span class="icoSave"></span>저장</button></li>
            <li><button onclick="fileDown(); return false;"><span class="icoDown"></span>파일다운로드</button></li>
        </ul> -->
    </div>
    <div class="talbeArea">
        <form id="listForm" name="listForm" method="post">
            <table class="main-table" summary="심사문서관리 현황을 리스트 선택, 항목번호, 점검항목, 업무명(양식서), 증적파일명, 업무주기 등의 항목으로 설명하고 있습니다.">
                <!-- style="width:700px"> -->
                <colgroup>
                    <col width="130px">
                    <col width="*">
                    <col width="100px">
                    <col width="60px">
                    <col width="110px">
                    <col width="80px">
                    <col width="200px">
                </colgroup>
                <thead>
                <tr>
                    <th scope="col">통제항목</th>
                    <th scope="col">통제점검</th>
                    <th scope="col">업무명</th>
                    <th scope="col">주기</th>
                    <th scope="col">부서</th>
                    <th scope="col">담당자</th>
                    <th scope="col" class="last">증적자료</th>
                </tr>
                </thead>
                <tbody id="workList">

                <c:if test="${controlItems.size() eq 0}">
                    <tr>
                        <td colspan="8" class="last noDataList">검색 조건 선택 후 검색해주십시요.</td>
                    </tr>
                </c:if>

                <c:forEach items="${controlItems.rowMap()}" var="item" varStatus="status">

                    <c:forEach items="${item.value}" var="item2" varStatus="status2">

                        <tr>

                            <c:if test="${status2.index eq 0}">
                                <td class="fontLeft" rowspan="${item.value.size()}">
                                    <span class="my-badge-info">${item.key}</span>
                                    <p>${item2.value.ucm2lvNam}</p>
                                </td>
                            </c:if>

                            <td class="fontLeft">
                                <span class="my-badge-info">${item2.key}</span>
                                <p>${item2.value.ucm3lvNam}</p>
                            </td>
                            <td colspan="5" class="work-table-parent-td">

                                <c:if test="${not activities.containsKey(item2.key)}">
                                    <span class="no-activity">할당된 활동이 없습니다.</span>
                                </c:if>

                                <c:if test="${activities.containsKey(item2.key)}">
                                <table class="inner-work-table">
                                    <colgroup>
                                        <col width="100px">
                                        <col width="60px">
                                        <col width="110px">
                                        <col width="80px">
                                        <col width="200px">
                                    </colgroup>
                                    <tbody>

                                    <c:forEach items="${activities.get(item2.key).rowMap()}" var="workItem" varStatus="workStatus">
                                        <c:forEach items="${workItem.value}" var="workItem2" varStatus="workStatus2">
                                        <tr>

                                            <c:if test="${workStatus2.index eq 0}">
                                                <td class="fontLeft <c:if test="${workStatus.last}">last-activity</c:if>" rowspan="${workItem.value.size()}">
                                                    <c:choose>
                                                        <c:when test="${workItem2.value.utdSvcCod eq 'SC007'}">
                                                            <span class="my-badge-info">공통</span>
                                                        </c:when>
                                                        <c:when test="${workItem2.value.utdSvcCod eq saServiceCode}">
                                                            <span class="my-badge-info">SA</span>
                                                        </c:when>
                                                    </c:choose>
                                                    <p><a class="link-text" href="javascript:docPopup(${workItem2.value.utdTrcKey})">${workItem2.value.utdDocNam}</a></p>
                                                </td>
                                            </c:if>

                                            <td>
												${workItem2.value.utwTrmCodForHuman}
											</td>
                                            <td>
												${workItem2.value.udmDepNam}
											</td>
                                            <td>
                                                <a class="link-text" href="javascript:workPopup(${workItem2.value.utwWrkKey},${workItem2.value.utdTrcKey})">
                                                    ${workItem2.value.utwWrkIdForHuman}
                                                </a>
											</td>
                                            <td>

                                                <div class="uplidFileList">
                                                    <ul class="listArea listControl" style="width: 190px; height: 100%; min-height: 22px; margin: 0;">
                                                    <c:forEach items="${files.get(workItem2.value.utwWrkKey)}" var="file" varStatus="status">
                                                        <li title="${file.umfConFnm}">

                                                            <c:set var="hasControlItem" value="false"/>
                                                            <c:forTokens items="${file.ctrLst}" delims="," var="ctrLstToken">
                                                                <c:if test="${ctrLstToken eq item2.value.ucmCtrKey}">
                                                                    <c:set var="hasControlItem" value="true"/>
                                                                </c:if>
                                                            </c:forTokens>

                                                            <c:choose>
                                                                <c:when test="${workItem2.value.utwWrkSta eq 90}">

                                                            <input type="checkbox" class="" <c:if test="${hasControlItem eq 'true'}">checked="checked"</c:if>
                                                                   data-service="${search.service}" data-control="${item2.value.ucmCtrKey}"
                                                                   data-safile="${file.umfFleKey}" >
                                                            <a href="javascript:getFile('${file.umfFleKey}')">${file.umfConFnm}</a>

                                                                </c:when>
                                                                <c:otherwise>

                                                            <a href="javascript:getFile('${file.umfFleKey}')">
                                                                <span style="color: #ccc">${file.umfConFnm}</span>
                                                            </a>

                                                                </c:otherwise>
                                                            </c:choose>









                                                        </li>
                                                    </c:forEach>
                                                    </ul>
                                                </div>
                                            </td>
                                        </tr>
                                        </c:forEach>
                                    </c:forEach>

                                    </tbody>

                                </table>
                                </c:if>

                            </td>

                        </tr>

                    </c:forEach>

                </c:forEach>


                </tbody>
            </table>
        </form>
    </div>
</div>
</body>
</html>