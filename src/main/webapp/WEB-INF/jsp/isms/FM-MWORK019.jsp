<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
<%@ include file="/WEB-INF/include/contents.jsp"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<script type="text/javascript">

	$(document).ready(function() {

	});

	/*
	 * 같은 값이 있는 열을 병합함
	 * 사용법 : $('#테이블 ID').rowspan(0);
	 */
	$.fn.rowspan = function(colIdx, isStats) {
	    return this.each(function(){
	        var that;
	        $('tr', this).each(function(row) {
	            $('td:eq('+colIdx+')', this).filter(':visible').each(function(col) {

	                if ($(this).html() == $(that).html()
	                    && (!isStats
	                            || isStats && $(this).prev().html() == $(that).prev().html()
	                            )
	                    ) {
	                    rowspan = $(that).attr("rowspan") || 1;
	                    rowspan = Number(rowspan)+1;

	                    $(that).attr("rowspan",rowspan);

	                    // do your action for the colspan cell here
	                    $(this).hide();

	                    //$(this).remove();
	                    // do your action for the old cell here

	                } else {
	                    that = this;
	                }

	                // set the that if not already set
	                that = (that == null) ? this : that;
	            });
	        });
	    });
	};

	function workPopup(wrkKey, trcKey){
		window.open("","workPopup","width=730, height=900, menubar=no, location=no, status=no,scrollbars=yes");
		$("#utwWrkKey").val(wrkKey);
		$("#utwTrcKey").val(trcKey);
		document.workPopupForm.submit();
	}

	function excelDown(){
		document.listForm.action = "${pageContext.request.contextPath}/excel/FM-MWORK019.do";
		document.listForm.submit();

	}

	function search(){
		/*
		var service = $("#service").val();
		var worker = $("#worker").val();

		$.ajax({
			url : "${pageContext.request.contextPath}/mwork/FM-MWORK019_getWorkList.do",
			type : "post",
			data : {"service": service, "worker": worker},
			dataType : "json",
			 success	: function(data){
				 var workListHTML = "";
					for(var i=0; i<data.resultList.length; i++) {
						var docnam = data.resultList[i].utlDocNam;
						var docgbn = data.resultList[i].utlDocGbn;
						var trmgbn = data.resultList[i].utlTrmGbn;
						//var divcod = data.resultList[i].utlDivCod;
						var svccod = data.resultList[i].utlSvcCod;
						var delyn = data.resultList[i].utlDelYn;
						var apryn = data.resultList[i].utlAprYn;
						var applev = data.resultList[i].utlAppLev;
						var wrkend = data.resultList[i].utlWrkEnd;
						var strdat = data.resultList[i].utlStrDat;
						var enddat = data.resultList[i].utlEndDat;
						var docetc = data.resultList[i].utlDocEtc;
						var sdmyn = data.resultList[i].utlSdmYn;
						var rgtid = data.resultList[i].utlRgtId;
						var rgtmdh = data.resultList[i].utlRgtMdh;
						var uptid = data.resultList[i].utlUptId;
						var uptmdh = data.resultList[i].utlUptMdh;

						workListHTML += '<tr>';
						workListHTML += '<td>'+docnam+'</td>';
						workListHTML += '<td>'+docgbn+'</td>';
						workListHTML += '<td>'+trmgbn+'</td>';
						//workListHTML += '<td>'+divcod+'</td>';
						workListHTML += '<td>'+svccod+'</td>';
						workListHTML += '<td>'+delyn+'</td>';
						workListHTML += '<td>'+apryn+'</td>';
						workListHTML += '<td>'+applev+'</td>';
						workListHTML += '<td>'+wrkend+'</td>';
						workListHTML += '<td>'+strdat+'</td>';
						workListHTML += '<td>'+enddat+'</td>';
						workListHTML += '<td>'+docetc+'</td>';
						workListHTML += '<td>'+sdmyn+'</td>';
						workListHTML += '<td>'+rgtid+'</td>';
						workListHTML += '<td>'+rgtmdh+'</td>';
						workListHTML += '<td>'+uptid+'</td>';
						workListHTML += '<td>'+uptmdh+'</td>';
						workListHTML += '</tr>';
					}
					alert(data.resultList.length);
					if(data.resultList.length == 0){
						workListHTML += '<tr><td colspan="16"> 검색 된 데이타가 없습니다.</td></tr>'
					}
					workListHTML = replaceAll(workListHTML, 'null', '');
					$("#workList").html(workListHTML);
			 },
			 error : function(){
				 alert('error' + id);
			 }
		 });
	*/
		document.listForm.action = "FM-MWORK019.do";
		document.listForm.submit();
	}

	function replaceAll(str,ori,rep){
		return str.split(ori).join(rep);
	}

  	function fn_egov_pageview(pageNo) {
		document.listForm.pageIndex.value = pageNo;
		document.listForm.action = "FM-MWORK019.do";
		document.listForm.submit();
  	}

</script>
</head>
<body>
	<p class="history"><a href="#none">보호활동</a><span>&gt;</span>수행업무이력</p>
	<div class="conttitle">수행업무이력</div>
	<form name="listForm" action="FM-MWORK019.do" method="post">
	<div class="search">
	    <div class="defalt">
	        <fieldset class="searchForm">
	            <ul class="defalt">
                     <li>
                         <sb:select name="service" type="S" allText="서비스 전체" />
					</li>
	            </ul>
	            <button onclick="search(); return false;" class="btnSearch">검색</button>
	        </fieldset>
	    </div>
	</div>
	<div class="contents">
		<div class="topBtnArea">
			<ul class="btnList">
				<li>
					<button onclick="excelDown(); return false;"><span class="icoExl"></span>엑셀다운로드</button>
				</li>
			</ul>
		</div>
		<div class="talbeArea ">
			<table summary="수행업무이력 현황을 문서명, 문서종류, 업무주기 구분, 서비스코드, 삭제여부, 결재여부, 결재단계, 업무기한, 업무 시작일, 업무 만료일, 문서 설명, 메일발송여부, 생성자, 생성일시, 최종 수정자, 최종 수정일시 등의 항목으로 설명하고있습니다.">
				<colgroup>
					<col width="" />
					<col width="6%" />
					<col width="6%" />
					<col width="6%" />
					<col width="6%" />
					<col width="6%" />
					<col width="8%" />
					<col width="8%" />
					<col width="6%" />
					<col width="10%" />
					<col width="8%" />
				</colgroup>
				<thead>
					<tr>
						<th scope="col">문서명</th>
						<th scope="col">문서종류</th>
						<th scope="col">업무주기</th>
						<th scope="col">서비스</th>
						<th scope="col">삭제여부</th>
						<th scope="col">결재여부</th>
						<th scope="col">시작일</th>
						<th scope="col">종료일</th>
						<th scope="col">메일<br />발송여부</th>
						<th scope="col">생성자</th>
						<th scope="col" class="last">생성일시</th>
					</tr>
				</thead>
				<tbody id="workList">
					<c:forEach var="result" items="${resultList}" varStatus="status">
						<tr>
							<td class="fontLeft"><c:out value="${result.utlDocNam}" /></td>
							<td><c:out value="${result.utlDocGbn}" /></td>
							<td><c:out value="${result.utlTrmGbn}" /></td>
							<td><c:out value="${result.utlSvcCod}" /></td>
							<td><c:out value="${result.utlDelYn}" /></td>
							<td><c:out value="${result.utlAprYn}" /></td>
							<td><c:out value="${result.utlStrDat}" /></td>
							<td><c:out value="${result.utlEndDat}" /></td>
							<td><c:out value="${result.utlSdmYn}" /></td>
							<td><c:out value="${result.utlRgtId}" /></td>
							<td class="last"><c:out value="${result.utlRgtMdh}" /></td>
						</tr>
					</c:forEach>
					<c:if test="${fn:length(resultList) == 0}">
						<tr class="last">
							<td class="last noDataList" colspan="11">검색된 수행업무이력이 없습니다.</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</div>
		<div class="paging">
			<ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="fn_egov_pageview" />
			<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>" />
		</div>
	</div>
	</form>
	</body>
	</html>