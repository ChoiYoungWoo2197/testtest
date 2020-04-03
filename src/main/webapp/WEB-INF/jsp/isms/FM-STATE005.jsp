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

function tReplace(text){
	text = text.replace(/@@/gi,'<br/>').replace(/:/gi,'&nbsp;:&nbsp;').replace(/\(/gi,'&nbsp;(');
	text = "<a style='color:#e0002a;'>" + text.substring(0, text.indexOf('부분미흡')) + "</a>" + "<a style='color:#ef8009;'>" + text.substring(text.indexOf('부분미흡'), text.indexOf('양호')) + "</a>" + text.substring(text.indexOf('양호'), text.length);
	return text;
}

function detailChart(){
	$.ajax({
		url		: "${pageContext.request.contextPath}/state/FM-STATE005_STD_LIST.do",
		type		: "post",
		data		: {},
		dataType	: "json",
		success	: function(data){
			var _div = $("#grid_w");			
			var list = "";			
        	for(var i = 0;i<data.result.length;i++){
        		
        		var item = data.result[i];
        		
        		var sevCod = data.result[i].uagSvcCod;
        		var service = data.result[i].service;
        		var c06 = tReplace(data.result[i].c06);
        		var c07 = tReplace(data.result[i].c07);
        		var c08 = tReplace(data.result[i].c08);
        		var c09 = tReplace(data.result[i].c09);
        		var c10 = tReplace(data.result[i].c10);
        		var c11 = tReplace(data.result[i].c11);
        		var c12 = tReplace(data.result[i].c12);
        		var c13 = tReplace(data.result[i].c13);
        		var tot = tReplace(data.result[i].tot);
        		
        		if(service == 'all'){        			
        			list = "<tr>" +
    			    "<td><a href='#' onclick='subDetailChart(\"\");'>전체</a></td>" +
    			    "<td>" + c06 + "</td>" +
    			    "<td>" + c07 + "</td>" +
    			    "<td>" + c08 + "</td>" +
    			    "<td>" + c09 + "</td>" +
    			    "<td>" + c10 + "</td>" +
    			    "<td>" + c11 + "</td>" +
    			    "<td>" + c12 + "</td>" +
    			    "<td>" + c13 + "</td>" +
    			    "<td class='last'>" + tot + "</td>" +
    				"</tr>" ;				
    				$("#grid_wf").append(list);
        		}else{
        			list = "<tr>" +
        			"<td><a href='#' onclick='subDetailChart(\""+ sevCod +"\");'>" + service + "</a></td>" +
        			"<td>" + c06 + "</td>" +
    			    "<td>" + c07 + "</td>" +
    			    "<td>" + c08 + "</td>" +
    			    "<td>" + c09 + "</td>" +
    			    "<td>" + c10 + "</td>" +
    			    "<td>" + c11 + "</td>" +
    			    "<td>" + c12 + "</td>" +
    			    "<td>" + c13 + "</td>" +
    			    "<td class='last'>" + tot + "</td>" +
    				"</tr>" ;					
    				_div.append(list);
        		}
        	}        	
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
		url		: "${pageContext.request.contextPath}/state/FM-STATE005_DEPT_LIST.do",
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
        		var c06 = tReplace(data.result[i].c06);
        		var c07 = tReplace(data.result[i].c07);
        		var c08 = tReplace(data.result[i].c08);
        		var c09 = tReplace(data.result[i].c09);
        		var c10 = tReplace(data.result[i].c10);
        		var c11 = tReplace(data.result[i].c11);
        		var c12 = tReplace(data.result[i].c12);
        		var c13 = tReplace(data.result[i].c13);
        		var tot = tReplace(data.result[i].tot);
        		
        		if(dept == 'all' && i == data.result.length-1){
    				tRow = "" +
    			     "<span class='wType00' style='width:95px;'>전체</span>"+
    			     "<span class='wType00' style='width:95px;margin-left:4px;'>" + c06 + "</span>" +
    			     "<span class='wType00' style='width:95px;margin-left:4px;'>" + c07 + "</span>" +
    			     "<span class='wType00' style='width:95px;margin-left:4px;'>" + c08 + "</span>" +
    			     "<span class='wType00' style='width:95px;margin-left:4px;'>" + c09 + "</span>" +
    			     "<span class='wType00' style='width:95px;margin-left:4px;'>" + c10 + "</span>" +
    			     "<span class='wType00' style='width:95px;margin-left:4px;'>" + c11 + "</span>" +
    			     "<span class='wType00' style='width:95px;margin-left:4px;'>" + c12 + "</span>" +
    			     "<span class='wType00' style='width:95px;margin-left:4px;'>" + c13 + "</span>" +
    			     "<span class='last wType00' style='width:100px;margin-left:4px;'>" + tot + "</span>";
    				_div_2.append(tRow);
    			}else{    				
    				dt = "<span class='wType00' style='width:95px;'>"+dept + "</span>";
    				tRow = "<li>" + 	
    			     dt + 
    			     "<span class='wType00' style='width:95px;margin-left:4px;'>" + c06 + "</span>" +
    			     "<span class='wType00' style='width:95px;margin-left:4px;'>" + c07 + "</span>" +
    			     "<span class='wType00' style='width:95px;margin-left:4px;'>" + c08 + "</span>" +
    			     "<span class='wType00' style='width:95px;margin-left:4px;'>" + c09 + "</span>" +
    			     "<span class='wType00' style='width:95px;margin-left:4px;'>" + c10 + "</span>" +
    			     "<span class='wType00' style='width:95px;margin-left:4px;'>" + c11 + "</span>" +
    			     "<span class='wType00' style='width:95px;margin-left:4px;'>" + c12 + "</span>" +
    			     "<span class='wType00' style='width:95px;margin-left:4px;'>" + c13 + "</span>" +
    			     "<span class='last wType00' style='width:100px;margin-left:4px;'>" + tot + "</span>";
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
	$('form[name=chartPopupForm]').attr('action','${pageContext.request.contextPath}/chart/FM-STATE005_popup.do').submit();
}
</script>
</head>
<body>
	<p class="history"><a href="">통계</a><span>&gt;</span>위험현황</p>
	<div class="conttitle">위험현황</div>
	<div class="pointTable">
		<div class="title">
			서비스별
			<div class="topBtnArea" style="top: -42px; margin-bottom: -42px; position: relative;">
                <ul class="btnList">
                	<li style="margin-top: 3px;"><span style="font-weight:normal;">완료(전체)</span></li>
                    <li>
						<button onclick="chartPopup('SERVICE');"><span class="icoList"></span>차트</button>
					</li>
                </ul>
            </div>
        </div>
		<div class="talbeArea" style="overflow-x: scroll;">
			<table summary="통제항목별 진행현황을 통제항목, 전체, 완료, 비연, 미진행, 금일완료 등으로 확인 할 수 있습니다." style="width: 1125px;">
				<colgroup>
					<col width="*">
					<col width="10%">
					<col width="10%">
					<col width="10%">
					<col width="10%">
					<col width="10%">
					<col width="10%">
					<col width="10%">
					<col width="10%">
					<col width="10%">
				</colgroup>
				<caption>서비스별 위험현황</caption> 
				<thead>
                    <tr>
                        <th>서비스</th>
                        <th>전자정보</th>
                        <th>서버</th>
                        <th>네트워크</th>
                        <th>정보보호시스템</th>
                        <th>문서</th>
                        <th>소프트웨어</th>
                        <th>단말기</th>
                        <th>물리적자산</th>
                        <th class="last">전체</th>
                    </tr>
				</thead>
				<tfoot id="grid_wf">                      
				</tfoot>
                <tbody id="grid_w">                      
                </tbody>
			</table>
		</div>
	</div>
	<div class="contents">
	    <div class="listMoveArea dualArea" style="margin: 0;min-height:500px;">
	        <div class="listArea" style="padding: 0px 0px 0px 1px; width: 100%;">
	            <div class="title">
	            	부서별
	            	<div class="topBtnArea" style="top: -42px; margin-bottom: -42px; position: relative;">
		                <ul class="btnList">
		                	<li style="margin-top: 3px;"><span style="font-weight:normal;">완료(전체)</span></li>
		                    <li>
								<button onclick="chartPopup('DEPT');"><span class="icoList"></span>차트</button>
							</li>
		                </ul>
		            </div>
	            </div>
	            <div class="talbeArea" style="overflow-x: scroll;">
	                <dl class="workingArea" style="width: 1125px;">
	                    <dt>
	                    	<span class="wType00" style="width:95px;">부서</span>
	                        <span class="wType00" style="width:95px;">전자정보</span>
	                        <span class="wType00" style="width:95px;">서버</span>
	                        <span class="wType00" style="width:95px;">네트워크</span>
	                        <span class="wType00" style="width:95px;">정보보호시스템</span>
	                        <span class="wType00" style="width:95px;">문서</span>
	                        <span class="wType00" style="width:95px;">소프트웨어</span>
	                        <span class="wType00" style="width:95px;">단말기</span>
	                        <span class="wType00" style="width:95px;">물리적자산</span>
	                        <span class="last wType00" style="width:100px;">전체</span>
	                    </dt>
	                    <dd class="listArea">
	                        <ul class="listUl" id="grid_w02">
	                        </ul>
	                    </dd>
	                    <dd class="resume" id="grid_w02_1">
	                    </dd>
	                </dl>
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