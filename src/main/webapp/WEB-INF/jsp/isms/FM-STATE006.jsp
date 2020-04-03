<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<script type="text/javascript">

$(document).ready(function() {
	detailChart()
});
function mainLoding(){
	var sevNam = '${searchVO.serviceName}';
	if(sevNam != ''){
		$('#grid_w').find('tr').each(function(){
			if($(this).find('td').find('a').text() == sevNam){
				$(this).find('td').find('a').click();
			}
		});
	}
}
function detailChart(){
	$.ajax({
		url		: "${pageContext.request.contextPath}/state/FM-STATE006_STD_LIST.do",
		type		: "post",
		data		: {},
		dataType	: "json",
		success	: function(data){
			var _div = $("#grid_w");
			var list = "";
        	for(var i = 0;i<data.result.length;i++){

        		var item = data.result[i];

        		var sevCod = data.result[i].uarSubCod;
        		var service = data.result[i].service;
        		var c06 = data.result[i].c06;
        		var c07 = data.result[i].c07;
        		var c08 = data.result[i].c08;
        		var c09 = data.result[i].c09;
        		var c10 = data.result[i].c10;
        		var c11 = data.result[i].c11;
        		var c12 = data.result[i].c12;
        		var c13 = data.result[i].c13;
        		var c17 = data.result[i].c17;

        		if(service == 'ALL'){
        			list = "<tr>" +
    			    "<td><a href='javascript:;' onclick='subDetailChart(\"\");'>전체</a></td>" +
    			    "<td>" + c06 + "</td>" +
    			    "<td>" + c07 + "</td>" +
    			    "<td>" + c08 + "</td>" +
    			    "<td>" + c09 + "</td>" +
    			    "<td>" + c10 + "</td>" +
    			    "<td>" + c11 + "</td>" +
    			    "<td>" + c12 + "</td>" +
    			    "<td>" + c13 + "</td>" +
    			    "<td class='last'>" + c17 + "</td>" +
    				"</tr>" ;
    				$("#grid_wf").append(list);
        		}else{
        			list = "<tr>" +
        			"<td><a href='javascript:;' onclick='subDetailChart(\""+ sevCod +"\");'>" + service + "</a></td>" +
        			"<td>" + c06 + "</td>" +
    			    "<td>" + c07 + "</td>" +
    			    "<td>" + c08 + "</td>" +
    			    "<td>" + c09 + "</td>" +
    			    "<td>" + c10 + "</td>" +
    			    "<td>" + c11 + "</td>" +
    			    "<td>" + c12 + "</td>" +
    			    "<td>" + c13 + "</td>" +
    			    "<td class='last'>" + c17 + "</td>" +
    				"</tr>" ;
    				_div.append(list);
        		}
        	}
        	mainLoding();
        	subDetailChart('');
		},
		error : function(){
			alert('error');
		}
	});
}

function subDetailChart(service){
	$('input[name=service]').val(service);
	$.ajax({
		url		: "${pageContext.request.contextPath}/state/FM-STATE006_SUB_LIST.do",
		type		: "post",
		data		: {"service":service},
		dataType	: "json",
		success	: function(data){
			var _div = $("#grid_w02");
			var _div_2 = $("#grid_w02_1");
			_div.empty();
			_div_2.empty();
			var list = "";
			var tRow = "";
        	for(var i = 0;i<data.result.length;i++){

        		var item = data.result[i];

        		var dept = data.result[i].dept;
        		var c06 = data.result[i].c06;
        		var c07 = data.result[i].c07;
        		var c08 = data.result[i].c08;
        		var c09 = data.result[i].c09;
        		var c10 = data.result[i].c10;
        		var c11 = data.result[i].c11;
        		var c12 = data.result[i].c12;
        		var c13 = data.result[i].c13;
        		var c17 = data.result[i].c17;

        		if(dept == 'all' && i == data.result.length-1){
    				tRow = "" +
    			     "<span class='wType00' style='width:110px;'>전체</span>"+
    			     "<span class='wType00' style='width:82px;margin-left:4px;'>" + c06 + "</span>" +
    			     "<span class='wType00' style='width:82px;margin-left:4px;'>" + c07 + "</span>" +
    			     "<span class='wType00' style='width:82px;margin-left:4px;'>" + c08 + "</span>" +
    			     "<span class='wType00' style='width:82px;margin-left:4px;'>" + c09 + "</span>" +
    			     "<span class='wType00' style='width:82px;margin-left:4px;'>" + c10 + "</span>" +
    			     "<span class='wType00' style='width:82px;margin-left:4px;'>" + c11 + "</span>" +
    			     "<span class='wType00' style='width:82px;margin-left:4px;'>" + c12 + "</span>" +
    			     "<span class='wType00' style='width:82px;margin-left:4px;'>" + c13 + "</span>" +
    			     "<span class='last wType00' style='width:82px;margin-left:4px;'>" + c17 + "</span>";
    				_div_2.append(tRow);
    			}else{
    				dt = "<span class='wType00' style='width:110px;'>"+dept + "</span>";
    				tRow = "<li>" +
    			     dt +
    			     "<span class='wType00' style='width:82px;margin-left:4px;'>" + c06 + "</span>" +
    			     "<span class='wType00' style='width:82px;margin-left:4px;'>" + c07 + "</span>" +
    			     "<span class='wType00' style='width:82px;margin-left:4px;'>" + c08 + "</span>" +
    			     "<span class='wType00' style='width:82px;margin-left:4px;'>" + c09 + "</span>" +
    			     "<span class='wType00' style='width:82px;margin-left:4px;'>" + c10 + "</span>" +
    			     "<span class='wType00' style='width:82px;margin-left:4px;'>" + c11 + "</span>" +
    			     "<span class='wType00' style='width:82px;margin-left:4px;'>" + c12 + "</span>" +
    			     "<span class='wType00' style='width:82px;margin-left:4px;'>" + c13 + "</span>" +
    			     "<span class='last wType00' style='width:82px;margin-left:4px;'>" + c17 + "</span>" +
    				"</li>" ;
    				_div.append(tRow);
    			}
        	}
		},
		error : function(){
			alert('error');
		}
	});
	return false;
}

function chartPopup(sMode){
	window.open("","chartPopup","width=650, height=650, menubar=no, location=no, status=no,scrollbars=yes");
	$('input[name=sMode]').val(sMode);
	$('form[name=chartPopupForm]').attr('action','${pageContext.request.contextPath}/chart/FM-STATE006_popup.do').submit();
}
</script>
</head>
<body>
	<p class="history"><a href="">통계</a><span>&gt;</span>자산내역</p>
	<div class="conttitle">자산내역</div>
	<div class="pointTable">
		<div class="title">서비스별</div>
		<div class="talbeArea">
			<table summary="통제항목별 진행현황을 통제항목, 전체, 완료, 비연, 미진행, 금일완료 등으로 확인 할 수 있습니다.">
				<colgroup>
					<col width="">
					<col width="10%">
					<col width="10%">
					<col width="10%">
					<col width="10%">
					<col width="10%">
					<col width="10%">
					<col width="10%">
					<col width="10%">
				</colgroup>
				<caption>서비스별 자산내역</caption>
				<thead>
                    <tr>
                        <th>서비스</th>
                        <th>전자정보</th>
                        <th>서버</th>
                        <th>네트워크</th>
                        <th>정보보호<br/>시스템</th>
                        <th>문서</th>
                        <th>소프트웨어</th>
                        <th>단말기</th>
                        <th>물리적자산</th>
                        <th class="last">저장장치</th>
                    </tr>
				</thead>
				<tfoot id="grid_wf">
				</tfoot>
                <tbody id="grid_w">
                </tbody>
			</table>
			<div class="topBtnArea">
                <ul class="btnList">
                    <li>
						<button onclick="chartPopup('SERVICE');"><span class="icoList"></span>차트</button>
					</li>
                </ul>
            </div>
		</div>
	</div>
	<div class="contents">
	    <div class="listMoveArea dualArea" style="margin: 0;">
	        <div class="listArea" style="padding: 0px 0px 0px 1px; width: 100%;">
	            <div class="title">부서별</div>
	            <div class="talbeArea">
	                <dl class="workingArea">
	                    <dt>
	                    	<span class="wType00" style="width:110px;">부서</span>
	                        <span class="wType00" style="width:82px;">전자정보</span>
	                        <span class="wType00" style="width:82px;">서버</span>
	                        <span class="wType00" style="width:82px;">네트워크</span>
	                        <span class="wType00" style="width:82px;">정보보호<br/>시스템</span>
	                        <span class="wType00" style="width:82px;">문서</span>
	                        <span class="wType00" style="width:82px;">소프트웨어</span>
	                        <span class="wType00" style="width:82px;">단말기</span>
	                        <span class="wType00" style="width:82px;">물리적자산</span>
	                        <span class="last wType00" style="width:82px;">저장장치</span>
	                    </dt>
	                    <dd class="listArea">
	                        <ul class="listUl" id="grid_w02">
	                        </ul>
	                    </dd>
	                    <dd class="resume" id="grid_w02_1">
	                    </dd>
	                </dl>
					<div class="topBtnArea">
						<ul class="btnList">
							<li>
	                        	<button onclick="chartPopup('DEPT');"><span class="icoList"></span>차트</button>
                        	</li>
						</ul>
					</div>
	            </div>
	        </div>
		</div>
	</div>
	<form id="chartPopupForm" name="chartPopupForm" method="post" target="chartPopup">
		<input type="hidden" name="service" value=""/>
		<input type="hidden" name="sMode" value=""/>
	</form>
</body>
</html>