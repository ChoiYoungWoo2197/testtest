<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
<%@ include file="/WEB-INF/include/contents.jsp"%>
<html lang="ko">
<head>
<script type="text/javascript">

var selectId ="";
var selectTerm="";

$(document).ready(function() {
	$("#hqOrg").chained("#stOrg");
	$("#gpOrg").chained("#hqOrg");

	getWorkCycle("TERM");
	search();
	// 주기 변경시
	$("#workTerm").change(function(){
		var termCheck = $("select[name=workTerm]").val();
		if(termCheck != "A"){
			if(termCheck == "Y"){
				getWorkCycle("TERM");
			}else {
				getWorkCycle("nonCycle");
			}
		}else{
			$("#workCycleTerm").html('<option value="">전체</option>');
		}
	});


	$('#setAgnIdBtn').click( function(){
		var checked = '';
		$("input[name=chkUtwWrkKey]:checked").each(function() {
			if($(this).val() != 'false'){
				checked += $(this).val()+",";
			}
		});


		if(checked == ''){
			alert("대무자를 지정 할 업무를 선택해 주십시오");
			return false;
		}else{
			$("#checkKey").val(checked);
			window.open("","agnPopup","width=600, height=500, menubar=no, location=no, status=no, scrollbars=auto");
			document.agnForm.submit();
		}
	});
});

function getWorkCycle(code) {
	var url = "";
	if("TERM" == code) {
		url = "${pageContext.request.contextPath}/code/getCommonCodeList.do";
	} else {
		url = "${pageContext.request.contextPath}/code/getNonCodeList.do";
	}

	$.ajax({
		url			: url,
		type		: "post",
		data		: {code:code},
		dataType	: "json",
		success		: function(data){
			var inData ="";
			inData += '<option value="">전체</option>';
			for(var i=0; i<data.result.length; i++){
				inData += '<option value="'+data.result[i].code+'">'+data.result[i].name+'</option>';
			}
			$("#workCycleTerm").html(inData);
		 },
		 error : function(){
			 alert('error');
		 }
	 });
}

function search(){

	var formData = $("#searchForm").serialize();
	$.ajax({
		url : "${pageContext.request.contextPath}/mwork/FM-MWORK005List.do",
		type		: "post",
		data		: formData,//{"service": service, "dept": dept, "worker":worker},
		dataType	: "json",
		success	: function(data){
			var workListHTML = "";
			for(var i=0; i<data.result.length; i++) {
				workListHTML += '<tr>';
				workListHTML += '<td>'+data.result[i].utwWrkNm+'</td>';
				workListHTML += '<td>'+data.result[i].utwDepNam+'</td>';
				workListHTML += "<td style='cursor: pointer;' onclick='userWorkByTerm("+'"D"'+',"'+data.result[i].utwWrkId+'"'+");'>" + data.result[i].trmD + "</td>";
				workListHTML += "<td style='cursor: pointer;' onclick='userWorkByTerm("+'"W"'+',"'+data.result[i].utwWrkId+'"'+");'>" + data.result[i].trmW + "</td>";
				workListHTML += "<td style='cursor: pointer;' onclick='userWorkByTerm("+'"M"'+',"'+data.result[i].utwWrkId+'"'+");'>" + data.result[i].trmM + "</td>";
				workListHTML += "<td style='cursor: pointer;' onclick='userWorkByTerm("+'"T"'+',"'+data.result[i].utwWrkId+'"'+");'>" + data.result[i].trmT + "</td>";
				workListHTML += "<td style='cursor: pointer;' onclick='userWorkByTerm("+'"Q"'+',"'+data.result[i].utwWrkId+'"'+");'>" + data.result[i].trmQ + "</td>";
				workListHTML += "<td style='cursor: pointer;' onclick='userWorkByTerm("+'"H"'+',"'+data.result[i].utwWrkId+'"'+");'>" + data.result[i].trmH + "</td>";
				workListHTML += "<td style='cursor: pointer;' onclick='userWorkByTerm("+'"Y"'+',"'+data.result[i].utwWrkId+'"'+");'>" + data.result[i].trmY + "</td>";
				workListHTML += "<td style='cursor: pointer;' onclick='userWorkByTerm("+'"N"'+',"'+data.result[i].utwWrkId+'"'+");'>" + data.result[i].trmN + "</td>";
				workListHTML += "<td class='last' style='cursor: pointer;' onclick='userWorkByTerm("+'"A"'+',"'+data.result[i].utwWrkId+'"'+");'>" + data.result[i].trmA + "</td>";
				//workListHTML += '<td class="last"><button onclick="transferUserWork(\''+data.result[i].utwWrkId+'\'); return false;">변경</button></td>';
				workListHTML += '</tr>';
			}
			if(workListHTML == ""){
				workListHTML += '<tr class="last"><td class="last noDataList" colspan="12">검색된 통제항목별 현황이 없습니다.</td></tr>'
			}
			workListHTML = replaceAll(workListHTML, 'null', '-');
			$("#workList").html(workListHTML);
			$("#workList").rowspan(0);

			$("#termTBList").empty();

		},
		error : function(){
			alert('error');
		}
	});
}

function userWorkByTerm(term,id,order){

	//팝업 갱신용
	$("#agnTerm").val(term);
	$("#agnId").val(id);
	$("#agnOrder").val(order);

	var state = $("#workState").val();
	selectId = id;
	selectTerm = term;
	if(order == "" || order == null){
		order = '9';
	}
	$.ajax({
		url		: "${pageContext.request.contextPath}/mwork/FM-MWORK005termList.do",
		type		: "post",
		data		: {"term": term, "id": id, "order":order, "state" : state},
		dataType	: "json",
		success	: function(data){
			var tt = $("#cntBar").html('<img src="../images/ico_h3.png" width="40" height="30">주기별 상세업무('+data.cnt+')');

			var list = '';
			for(var i = 0;i<data.result.length;i++){
				list += "<tr>";
				/* if(data.result[i].sta == '미진행'){
					list += '<td><input type="checkbox" name="chkUtwWrkKey" value="'+data.result[i].utwWrkKey+'" ></td>';
				}else{
					list += '<td></td>';
				} */

				list += '<td>'+data.result[i].utdTrmNam+'</td>';
				list += '<td><a href="javascript:workPopup(\''+data.result[i].utwWrkKey+'\',\''+data.result[i].utwTrcKey+'\')">'+data.result[i].utdDocNam+'</td>';
				list += '<td>'+data.result[i].utwStrDat+'</td>';
				list += '<td>'+data.result[i].utwEndDat+'</td>';
				list += '<td>'+data.result[i].utwAgnId+'</td>';
				list += '<td class="last">'+data.result[i].sta+'</td>';
				list += '</tr>'
			}
			list = replaceAll(list, 'null', '');
			$("#termTBList").html(list);
			if (!$.isEmptyObject(list)) {
				$("body").scrollTo($("#termContainer"), 300);
			}

		},
		error : function(){
			alert('error');
		}
	});
}

function replaceAll(str,ori,rep){
	return str.split(ori).join(rep);
}

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

function transferUserWork(usrKey){
	window.open("","UserPopup","width=400, height=520, menubar=no, location=no, status=no, scrollbars=auto");
	$("#userKey").val(usrKey);
	document.changeUserWorkForm.submit();
}

function excelDown(){
	document.searchForm.action = "${pageContext.request.contextPath}/excel/FM-MWORK005.do";
   	document.searchForm.submit();
}

function goPage(){
	location.href = "${pageContext.request.contextPath}/mwork/FM-MWORK005_T.do";
}
</script>
</head>
<body>
	<p class="history">
		<a href="#none">보호활동</a><span>&gt;</span>담당자별현황조회
	</p>
	<div class="conttitle">담당자별현황조회</div>
<!-- 	<div class="tabArea">
	    <ul class="list2Tab">
	        <li class="sel"><a href="#none">담당자별 현황</a></li>
	        <li><a href="javascript:goPage();">업무이관 요청</a></li>
	    </ul>
	</div> -->
	<div class="search">
		<form id="searchForm" method="post" name="searchForm">
			<div class="defalt">
				<fieldset class="searchForm">
				<legend>상세 검색</legend>
					<ul class="detail">
                     	<li>
							<span class="title"><label for="stOrg">본부</label></span>
							<sb:select name="stOrg" type="stOrg" forbidden="true" allText="본부전체" />
						</li>
						<li>
							<span class="title"><label for="hqOrg">그룹,담당</label></span>
							<sb:select name="hqOrg" type="hqOrg" forbidden="true" allText="그룹,담당전체" />
						</li>
		                <li>
		                    <span class="title"><label for="gpOrg">팀</label></span>
		                    <sb:select name="gpOrg" type="gpOrg" forbidden="true" allText="팀전체" />
		                </li>
		            	<li>
		                	<span class="title"><label for="service">서비스</label></span>
		                    <sb:select name="service" type="S" forbidden="true" />
		                </li>
						<li>
							<span class="title"><label for="workCycleTerm">업무주기</label></span>
							<select id="workCycleTerm" name="workCycleTerm" class="selectBox">
								<option value="">전체</option>
							</select>
						</li>
	                  	<li>
		        			<span class="title"><label for="workerName">담당자</label></span>
		              		<input id="workerName" name="workerName" class="inputText wdShort" type="text"  title="담당자명" placeholder="담당자명"/>
		              	</li>
		              	<li></li>
                  	</ul>
					<button class="btnSearch" onclick="search(); return false;">조건으로 검색</button>
				</fieldset>
			</div>
		</form>
	</div>
	<div class="contents">
		<div class="title">담당자별 현황조회</div>
		<div class="talbeArea">
			<table>
				<colgroup>
					<col width="*" />
					<col width="14%" />
					<col width="8%" />
					<col width="8%" />
					<col width="8%" />
					<col width="8%" />
					<col width="8%" />
					<col width="8%" />
					<col width="8%" />
					<col width="8%" />
					<col width="8%" />
					<%-- <col width="10%" /> --%>
				</colgroup>
				<thead>
					<tr>
						<th scope="col">담당자</th>
						<th scope="col">부서</th>
						<th scope="col">일간</th>
						<th scope="col">주간</th>
						<th scope="col">월간</th>
						<th scope="col">격월</th>
						<th scope="col">분기</th>
						<th scope="col">반기</th>
						<th scope="col">연간</th>
						<th scope="col">비주기</th>
						<th scope="col">전체</th>
						<!-- <th scope="col" class="last">담당자<br/>변경</th> -->
					</tr>
				</thead>
				<tbody id="workList">
				</tbody>
			</table>
			<div class="topBtnArea">
			<ul class="btnList">
				<li>
					<button onclick="excelDown(); return false;"><span class="icoExl"></span>엑셀다운로드</button>
				</li>
			</ul>
		</div>
		</div>
		<div id="termContainer" class="title">주기별 상세업무</div>
		<div class="talbeArea">
			<table>
				<colgroup>
					<%-- <col width="4%"/> --%>
					<col width="10%"/>
					<col width="*"/>
					<col width="10%"/>
					<col width="10%"/>
					<col width="10%"/>
					<col width="10%"/>
				</colgroup>
				<thead>
					<tr>
						<%-- <th><input type="checkbox" name="chkKey" class="chkNode01" value="${info.utwWrkKey}"></th> --%>
						<th scope="col">주기</th>
						<th scope="col">업무명</th>
						<th scope="col">업무시작일</th>
						<th scope="col">업무종료일</th>
						<th scope="col">대무자</th>
						<th scope="col" class="last">처리상태</th>
					</tr>
				</thead>
				<tbody id="termTBList">
				</tbody>
			</table>
			<div class="topBtnArea">
			    <ul class="btnList">
			        <!-- <li>
			            <button id="setAgnIdBtn"><span class="icoChagePer"></span>대무자 지정</button>
			        </li> -->
			    </ul>
			</div>
		</div>
	</div>
<form id="changeUserWorkForm" name="changeUserWorkForm" action="FM-MWORK005_USER_popup.do" method="post" target="UserPopup">
	<input type="hidden" id="userKey" name="userKey" value="">
</form>
<form id="agnForm" name="agnForm" action="FM-MWORK005_AGN_popup.do" method="post" target="agnPopup">
	<input type="hidden" id="checkKey" name="checkKey" value="">
	<input type="hidden" id="agnTerm" name="agnTerm" value="">
	<input type="hidden" id="agnId" name="agnId" value="">
	<input type="hidden" id="agnOrder" name="agnOrder" value="">
</form>
</body>
</html>