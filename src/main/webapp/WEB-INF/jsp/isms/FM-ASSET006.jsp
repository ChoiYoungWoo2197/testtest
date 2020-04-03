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
		$("#strDat").datepicker({
			changeMonth: true,
            changeYear: true,
            showButtonPanel: true
		});
		 $( "#strDat_btn" ).click(function(){
	         $( "#strDat" ).focus()
	     });
		$("#endDat").datepicker({
			changeMonth: true,
            changeYear: true,
            showButtonPanel: true
		});
		 $( "#endDat_btn" ).click(function(){
	         $( "#endDat" ).focus()
	     });
	});

	function excelDown(){
		document.searchForm.action = "${pageContext.request.contextPath}/excel/FM-ASSET006.do";
	   	document.searchForm.submit();
	}
	
	function search(){	
		$("#searchForm").ajaxSubmit({
			url : "${pageContext.request.contextPath}/asset/FM-ASSET006_getHistory.do",
			success : function(data){			
				var accountListHTML = "";
				for(var i=0; i<data.resultList.length; i++) {
					accountListHTML += '<tr>';
					accountListHTML += '<td>'+data.resultList[i].uacCatNam+'</td>';
					accountListHTML += '<td>'+data.resultList[i].uaoSvrNam+'</td>';
					accountListHTML += '<td>'+data.resultList[i].uaoDepNam+'</td>';
					accountListHTML += '<td>'+data.resultList[i].uaoAssCod+'</td>';
					accountListHTML += '<td>'+data.resultList[i].uaoEqpNam+'</td>';
					accountListHTML += '<td>'+data.resultList[i].uaoAosNam+'</td>';
					accountListHTML += '<td>'+data.resultList[i].uaoMakNam+'</td>';
					accountListHTML += '<td>'+data.resultList[i].uaoDtlNam+'</td>';
					accountListHTML += '<td>'+data.resultList[i].uaoDtlExp+'</td>';
					accountListHTML += '<td>'+data.resultList[i].uaoOwnNam+'</td>';
					accountListHTML += '<td>'+data.resultList[i].uaoAdmNam+'</td>';
					accountListHTML += '<td>'+data.resultList[i].uaoUseNam+'</td>';
					accountListHTML += '<td>'+data.resultList[i].uaoAdmPos+'</td>';
					accountListHTML += '<td>'+data.resultList[i].uaoAppCon+'</td>';
					accountListHTML += '<td>'+data.resultList[i].uaoAppItg+'</td>';
					accountListHTML += '<td>'+data.resultList[i].uaoAppAvt+'</td>';
					accountListHTML += '<td>'+data.resultList[i].uaoAssLvl+'</td>';
					accountListHTML += '<td>'+data.resultList[i].uaoAudYn+'</td>';
					accountListHTML += '<td>'+data.resultList[i].uaoSmpYn+'</td>';
					accountListHTML += '<td>'+data.resultList[i].uaoValCl0+'</td>';
					accountListHTML += '<td>'+data.resultList[i].uaoValCl1+'</td>';
					accountListHTML += '<td>'+data.resultList[i].uaoValCl2+'</td>';
					accountListHTML += '<td>'+data.resultList[i].uaoValCl3+'</td>';
					accountListHTML += '<td>'+data.resultList[i].uaoValCl4+'</td>';
					accountListHTML += '<td>'+data.resultList[i].uaoValCl5+'</td>';
					accountListHTML += '<td>'+data.resultList[i].uaoValCl6+'</td>';
					accountListHTML += '<td>'+data.resultList[i].uaoValCl7+'</td>';
					accountListHTML += '<td>'+data.resultList[i].uaoValCl8+'</td>';
					accountListHTML += '<td>'+data.resultList[i].uaoValCl9+'</td>';
					accountListHTML += '<td>'+data.resultList[i].uaoRgtId+'</td>';
					accountListHTML += '<td class="last">'+data.resultList[i].uaoRgtMdh+'</td>';
					accountListHTML += '</tr>';
				}
				if(data.resultList.length == 0){
					accountListHTML += '<tr><td class="last noDataList" colspan="30">계정이력이 없습니다.</td></tr>'
				}
				accountListHTML = replaceAll(accountListHTML, 'null', '');
				$("#logData").html(accountListHTML);			
			},
			error : function(e){
				alert("error");	
			}
		});
	}
	
	function replaceAll(str,ori,rep){
		return str.split(ori).join(rep);
	}
	
	function fn_egov_pageview(pageNo) {
		document.listForm.pageIndex.value = pageNo;
		document.listForm.action = "FM-ASSET006.do";
		document.listForm.submit();
	}
	
	function getWorkerList() {
		var dept = $("#dept").val();
		if(dept != ""){
			$.ajax({
				url			: "${pageContext.request.contextPath}/code/getWorkerList.do",
				type		: "post",
				data		: {code:dept},
				dataType	: "json",
				async		: false,
				success		: function(data){
					var	inData = getWorkerInTag(data); 
					$("#worker").html(inData);
				 },
				 error : function(){
					 alert('error');
				 }
			 });
		}
	}
	
</script>
</head>
<body>
	<p class="history">
		<a href="#none">자산관리</a><span>&gt;</span>자산이력
	</p>
	<div class="conttitle">자산이력</div>
	<form id="searchForm" name="searchForm" action="FM-ASSET006.do" method="post">
		<div class="search">
		    <div class="defalt">
				<fieldset class="searchForm">
		            <ul class="detail">
		            	<li>
		                	<span class="title"><label for="service">서비스</label></span>
		                   <sb:select name="service" type="S" />
		                </li>
		                <li>
		                	<span class="title"><label for="dept">부서</label></span>
		                    <sb:select name="dept" type="D" event="getWorkerList()" />
		                </li>
		                <li>
	                         <span class="title"><label for="worker">담당자</label></span>
	                         <select id="worker" name="worker" class="selectBox">
	                         	<option value="">전체</option>
	                         </select>
	                    </li>
	                    <li>
							<span class="title"><label for="assGbn">자산유형</label></span>
		                    <select id="assGbn" name="assGbn" class="selectBox">
		                    	<option value="">전체</option>
								<c:forEach var="assCat" items="${assCatList}" varStatus="status">
									<option value="${assCat.key}" ><c:out value="${assCat.name}" /></option>
								</c:forEach>
		                    </select>
		                </li>
		                <li>
		                	<span class="title"><label for="groupName">자산그룹명</label></span>
		                    <input type="text" id="groupName" name="groupName" class="inputText wdShort" title="자산그룹명 입력" placeholder="자산그룹명 입력">
		                </li>
		                <li>
		                	<span class="title"><label for="mngCode">자산코드</label></span>
		                    <input type="text" id="mngCode" name="mngCode" class="inputText wdShort" title="자산코드 입력" placeholder="자산코드 입력">
		                </li>
		                <li>
		                	<span class="title"><label for="rgtNam">수정자</label></span>
		                    <input type="text" id="rgtNam" name="rgtNam" class="inputText wdShort" title="수정자 입력" placeholder="수정자 입력">
		                </li>
		                <li>
		                	<span class="title"><label for="rgtMdh">수정일</label></span>
		                    <input type="text" id="strDat" name="strDat" readonly="readonly" class="inputText cal wdShort">
		                    <button id="strDat_btn" type="button"><span class="icoCal"><em>달력</em></span></button>
		                     ~
		                    <input type="text" id="endDat" name="endDat" readonly="readonly" class="inputText cal wdShort">
		                    <button id="endDat_btn" type="button"><span class="icoCal"><em>달력</em></span></button> 
		                </li>
		                <li class="last" style="width: 100%;">
		                	<span class="title"><label for="mngName">자산명</label></span>
		                    <input type="text" id="mngName" name="mngName" class="inputText" title="자산명 입력" placeholder="자산명을 입력하세요.">
		                </li>
		            </ul>
		            <button type="button" onclick="search();" class="btnSearch defaltBtn">조건으로 검색</button>
		        </fieldset>
		    </div>
		</div>
		<div class="contents">
			<div class="topBtnArea">
			<ul class="btnList">
				<li><button type="button" onclick="excelDown();"><span class="icoExl"></span>엑셀다운로드</button></li>
				</ul>
			</div>
			<div class="talbeArea hiddenTable">
				<table style="width:3000px" summary="자산이력 현황을 설명하고있습니다.">
					<colgroup>
						<col width="3%">
						<col width="3%">
						<col width="3%">
						<col width="4%">
						<col width="5%">
						<col width="3%">
						<col width="4%">
						<col width="4%">
						<col width="4%">
						<col width="3%">
						<col width="3%">
						<col width="3%">
						<col width="4%">
						<col width="3%">
						<col width="3%">
						<col width="3%">
						<col width="3%">
						<col width="3%">
						<col width="3%">
						<col width="3%">
						<col width="3%">
						<col width="3%">
						<col width="3%">
						<col width="3%">
						<col width="3%">
						<col width="3%">
						<col width="3%">
						<col width="3%">
						<col width="3%">
						<col width="3%">
						<col width="3%">
					</colgroup>
					<thead>
						<tr>
							<th scope="col">자산유형</th>
							<th scope="col">서비스명</th>
							<th scope="col">부서명</th>
							<th scope="col">자산코드</th>
							<th scope="col">자산(장비)명</th>
							<th scope="col">OS</th>
							<th scope="col">제조사</th>
							<th scope="col">모델명</th>
							<th scope="col">용도</th>
							<th scope="col">관리자</th>
							<th scope="col">운용자</th>
							<th scope="col">책임자</th>
							<th scope="col">위치</th>
							<th scope="col">기밀성(C)</th>
							<th scope="col">무결성(I)</th>
							<th scope="col">가용성(A)</th>
							<th scope="col">등급</th>
							<th scope="col">인증대상</th>
							<th scope="col">샘플링대상</th>
							<th scope="col">가변필드1</th>
							<th scope="col">가변필드2</th>
							<th scope="col">가변필드3</th>
							<th scope="col">가변필드4</th>
							<th scope="col">가변필드5</th>
							<th scope="col">가변필드6</th>
							<th scope="col">가변필드7</th>
							<th scope="col">가변필드8</th>
							<th scope="col">가변필드9</th>
							<th scope="col">가변필드10</th>
							<th scope="col">수정자</th>
							<th scope="col" class="last">수정일</th>
						</tr>
					</thead>
					<tbody id="logData">
						<c:forEach var="result" items="${resultList}" varStatus="status">
							<tr>
								<td><c:out value="${result.uacCatNam}" /></td>
								<td><c:out value="${result.uaoSvrNam}" /></td>
								<td><c:out value="${result.uaoDepNam}" /></td>
								<td><c:out value="${result.uaoAssCod}" /></td>
								<td><c:out value="${result.uaoEqpNam}" /></td>
								<td><c:out value="${result.uaoAosNam}" /></td>
								<td><c:out value="${result.uaoMakNam}" /></td>
								<td><c:out value="${result.uaoDtlNam}" /></td>
								<td><c:out value="${result.uaoDtlExp}" /></td>
								<td><c:out value="${result.uaoOwnNam}" /></td>
								<td><c:out value="${result.uaoAdmNam}" /></td>
								<td><c:out value="${result.uaoUseNam}" /></td>
								<td><c:out value="${result.uaoAdmPos}" /></td>
								<td><c:out value="${result.uaoAppCon}" /></td>
								<td><c:out value="${result.uaoAppItg}" /></td>
								<td><c:out value="${result.uaoAppAvt}" /></td>
								<td><c:out value="${result.uaoAssLvl}" /></td>
								<td><c:out value="${result.uaoAudYn}" /></td>
								<td><c:out value="${result.uaoSmpYn}" /></td>
								<td><c:out value="${result.uaoValCl0}" /></td>
								<td><c:out value="${result.uaoValCl1}" /></td>
								<td><c:out value="${result.uaoValCl2}" /></td>
								<td><c:out value="${result.uaoValCl3}" /></td>
								<td><c:out value="${result.uaoValCl4}" /></td>
								<td><c:out value="${result.uaoValCl5}" /></td>
								<td><c:out value="${result.uaoValCl6}" /></td>
								<td><c:out value="${result.uaoValCl7}" /></td>
								<td><c:out value="${result.uaoValCl8}" /></td>
								<td><c:out value="${result.uaoValCl9}" /></td>
								<td><c:out value="${result.uaoRgtId}" /></td>
								<td class="last"><c:out value="${result.uaoRgtMdh}" /></td>
							</tr>
						</c:forEach>
						<c:if test="${fn:length(resultList) == 0}">
							<tr class="last">
								<td class="last noDataList" colspan='30'>
									계정이력이 없습니다. 
								</td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</div>
			<div class="paging">
				<ui:pagination paginationInfo="${paginationInfo}" type="image"
					jsFunction="fn_egov_pageview" />
				<input name="pageIndex" type="hidden"
					value="<c:out value='${searchVO.pageIndex}'/>" />
			</div>
		</div>
	</form>
</body>
</html>