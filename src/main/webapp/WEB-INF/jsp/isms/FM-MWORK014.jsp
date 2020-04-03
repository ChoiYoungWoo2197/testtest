<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib  prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
<%@ include file="/WEB-INF/include/head.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<script type="text/javascript">
$(document).ready(function() {
	stOrgList();
	getList();

	$("#btnCheckAll").click(function() {
		if ($(this).prop("checked")) {
			$(".checking").prop("checked", true);
		}
		else {
			$(".checking").prop("checked", false);
		}
	});
});

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

function getList() {
	$.ajax({
		url		: "${pageContext.request.contextPath}/mwork/FM-MWORK014_LIST.do",
		type		: "post",
		data		: {stOrg: $("#stOrg").val(), hqOrg: $("#hqOrg").val(), gpOrg: $("#gpOrg").val()},
		dataType	: "json",
		success	: function(data){
			var list = "";
			for(var i = 0;i<data.resultList.length;i++){

				var wrkId = data.resultList[i].wrkId;
				var wrkNm = data.resultList[i].wrkNm;
				var depNm = data.resultList[i].depNm;
				var posNm = data.resultList[i].posNm;
				var celNum = data.resultList[i].celNum;
				var compcnt = data.resultList[i].compcnt;
				var noworkcnt = data.resultList[i].noworkcnt;
				var allworkcnt = data.resultList[i].allworkcnt;
				var depFull =  data.resultList[i].udmDepFul;
				var depList = [depNm];
				if (depFull != null) {
					depList = depFull.split("^");
				}
				for (var j = depList.length; j < 3; j++) {
					depList[j] = "";
				}

				list += "<tr>";
				list += "<td>"+depList[0]+"</td>";
				list += "<td>"+depList[1]+"</td>";
				list += "<td>"+depList[2]+"</td>";
				list += "<td><input name='checking' type='checkbox' class='checking' value='"+wrkId+"&"+wrkNm+"&"+celNum+"'/></td>";
				list += "<td>"+wrkNm+"</td>";
				list += "<td>"+allworkcnt+"</td>";
				list += "<td>"+noworkcnt+"</td>";
				list += "<td>"+compcnt+"</td>";
				list += "<td class='last'><button type='button' class='btn_in' onclick='mailSend("+wrkId+")'>발송</button></td>";
				//list += "<td><buton type='button' id='btn_n' class='btn_in' style='padding-top: 1px; cursor: pointer;' onclick='sendSmsDoNotWork("+wrkId+")'>발송</button></td>";
				list += "</tr>";
			}
			$("#mailList").html(list);
			$("#mailList").rowspan(0);
			$("#mailList").rowspan(1);
			$("#mailList").rowspan(2);
		},
		error : function(){
			alert('error');
		}
	});
}



function mailSend(userKey){
	var msg = '담당자에게 미수행업무에 대한 알림메일을 발송하시겠습니까?';
	if(confirm(msg)) {
		$.ajax({
			url		: "${pageContext.request.contextPath}/mwork/FM-NOWORK_SENDMAIL.do",
			type		: "post",
			data		: {"userKey":userKey,"type":"P"},
			dataType	: "json",
			success	: function(data){
				alert("알림메일이 발송되었습니다.");
			},
			error : function(){
				alert('error');
			}
		});
	}
}

/* function mailSendAll(){
	var msg = '전체 담당자에게 미수행업무에 대한 알림메일을 발송하시겠습니까?';
	if(confirm(msg)) {
		$.ajax({
			url		: "${pageContext.request.contextPath}/mwork/FM-NOWORK_SENDMAIL_ALL.do",
			type		: "post",
			data		: {},
			dataType	: "json",
			success	: function(data){
				alert("알림메일이 발송되었습니다.");
			},
			error : function(){
				alert('error');
			}
		});
	}
} */

function checkMail() {
	var msg = '';
	var obj = document.getElementsByName("checking");
	var checkCnt = 0;
	var arr = new Array;
		for(var i=0; i<obj.length; i++){
			if(obj[i].checked == true){
				checkCnt++;
			}
    	}
		if(checkCnt ==0){
			alert("담당자를 선택하세요");
			return;
		}
		for(var i=0; i<obj.length; i++){
			if(obj[i].checked){
				var splitArr = obj[i].value.split('&');
				arr.push(splitArr[0]);
			}
		}
		msg = '선택한 담당자에게 미수행업무에 대한 알림메일을 발송하시겠습니까?';

	if(confirm(msg)) {
		$.ajax({
			url		: "${pageContext.request.contextPath}/mwork/FM-NOWORK_SENDMAIL.do",
			type		: "post",
			data		: {"userKey":arr+"","type":"C"},
			dataType	: "json",
			success	: function(data){
				alert("알림메일이 발송되었습니다.");
			},
			error : function(){
				alert('error');
			}
		});
	}
}

function sendSmsDoNotWork(id) {
	var msg = '';
	var obj = document.getElementsByName("checking");
	var checkCnt = 0;
	var arr = new Array;
	var emptyMobile="";
	var cnt = 0;
	var emptyCnt = 0;
	var mode = "";
	if("check" == id){
		for(var i=0; i<obj.length; i++){
			if(obj[i].checked == true){
				checkCnt++;
			}
    	}
		if(checkCnt ==0){
			alert("담당자를 선택하세요");
			return;
		}

		for(var i=0; i<obj.length; i++){
			if(obj[i].checked){
				var splitArr = obj[i].value.split('&');
				if(splitArr[2]=='' || splitArr[2] == null || splitArr[2] == 'null'){
					emptyMobile+= "["+splitArr[1]+"]";
					emptyCnt++;
				}else{
					arr.push(splitArr[0]);
				}
				cnt++;
			}
		}

		if(cnt == emptyCnt){
			msg = '선택한 사용자의 핸드폰 번호가 누락되어 전송할 수 없습니다.';
		}else if(cnt > 0 && emptyCnt >0){
			msg = '선택한 담당자에게 미수행업무에 대한 SMS를 발송하시겠습니까?\n\n 아래의 사용자는 핸드폰번호 누락으로 인해 전송이 되지 않습니다.\n'+emptyMobile;
		}else{
			msg = '선택한 담당자에게 미수행업무에 대한 SMS를 발송하시겠습니까?';
		}
	}else if("" == id || undefined == id) {
		msg = '일괄적으로 미수행업무에 대한 SMS를 발송하시겠습니까?';
		for(var i=0; i<obj.length; i++){
				var splitArr = obj[i].value.split('&');
				if(splitArr[2]=='' || splitArr[2] == null || splitArr[2] == 'null'){
					arr.push(splitArr[0]);
				}
		}
	} else {
		msg = '선택한 담당자에게 미수행업무에 대한 SMS를 발송하시겠습니까?';
		arr.push(id);
	}
	if(confirm(msg)) {
		$.ajax({
			url		: "${pageContext.request.contextPath}/mwork/FM-NOWORK_SENDSMS.do",
			type		: "post",
			data		: {"userKey":arr+"","mode" : "mw"},
			dataType	: "json",
			success	: function(data){
				alert("알림메일이 발송되었습니다.");
			},
			error : function(){
				alert('error');
			}
		});
	}
}

function reloadNoWork(){
	getList();
}

// 2018-10-12 s,
function excelDown(){
	document.searchForm.action = "/excel/FM-MWORK014.do";
   	document.searchForm.submit();
}
</script>
</head>
<body>
<c:import url="/titlebar.do" />
<div class="search">
	<form id="searchForm" name="searchForm" method="post">
    <div class="defalt">
        <fieldset class="searchForm">
            <legend>기본검색</legend>
            <ul class="detail newField">
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
                <li class="btnArea">
                 	<button onclick="reloadNoWork(); return false;"  class="btnSearch">조건으로 검색</button>
				</li>
            </ul>
        </fieldset>
    </div>
    </form>
</div>
<div class="contents">
	<div class="topBtnArea">
		<ul class="btnList">
			<!--li><button onclick="sendSmsDoNotWork(); return false;"><span class="icoCal"></span>알림SMS 전체발송</button></li-->
			<!--li><button onclick="sendSmsDoNotWork('check'); return false;"><span class="icoCal"></span>알림SMS 선택발송</button></li-->
			<!-- <li><button onclick="mailSendAll(); return false;">알림메일 전체발송</button></li> -->
			<li><button type="button" onclick="checkMail()"><span class="icoMail"></span>알림메일 발송</button></li>
			<li><button type="button" onclick="excelDown()"><span class="icoExl"></span>엑셀다운로드</button></li>
		</ul>
	</div>
	<div class="talbeArea">
		<table summary="미완료 업무관리 (담당자별 업무현황)">
			<colgroup>
				<col />
				<col width="16%" />
				<col width="16%" />
				<col width="5%" />
				<col width="12%" />
				<col width="8%" />
				<col width="8%" />
				<col width="8%" />
				<col width="8%" />
			</colgroup>
			<caption>미완료 업무관리 (담당자별 업무현황)</caption>
			<thead>
			    <tr>
					<th scope="col">본부</th>
					<th scope="col">그룹,담당</th>
					<th scope="col">팀</th>
					<th scope="col"><input type="checkbox" id="btnCheckAll" /></th>
					<th scope="col">담당자</th>
					<th scope="col">총건수</th>
					<th scope="col">미완료</th>
					<th scope="col">완료</th>
					<th scope="col" class="last">메일발송</th>
				</tr>
			</thead>
			<tbody id="mailList">
			</tbody>
		</table>
    </div>
</div>
</body>
</html>