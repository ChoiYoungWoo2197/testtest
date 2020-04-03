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

function detailChart(){
	$.ajax({
		url		: "${pageContext.request.contextPath}/state/FM-STATE004_STD_LIST.do",
		type		: "post",
		data		: {"service":$('select[name=service]').val()},
		dataType	: "json",
		success	: function(data){
			var _div = $("#grid_w");
			var _div_2 = $("#grid_wf");
			_div.empty();
			_div_2.empty();
			var list = "";
        	for(var i = 0;i<data.result.length;i++){

        		var item = data.result[i];

        		var month = data.result[i].month;
        		var isms = data.result[i].isms;
        		var skpng = data.result[i].skpng;
        		var pims = data.result[i].pims;
        		var totW = data.result[i].totW;
        		var totA = data.result[i].totA;
        		var totP = data.result[i].totP;

        		if(month == 'ALL'){
        			list = "<tr>" +
    			    "<td><a href='#' onclick='subDetailChart(\"\";')>전체</a></td>" +
    			    "<td>" + totA + "</td>" +
    			    "<td>" + isms + "</td>" +
    			    "<td>" + skpng + "</td>" +
    			    "<td>" + pims + "</td>" +
    			    "<td class='last'>" + totP + "%</td>" +
    				"</tr>" ;
    				_div_2.append(list);
        		}else{
        			list = "<tr>" +
        			"<td><a href='#' onclick='subDetailChart(\""+ month +"\");'>" + month + "</a></td>" +
    			    "<td>" + totA + "</td>" +
    			    "<td>" + isms + "</td>" +
    			    "<td>" + skpng + "</td>" +
    			    "<td>" + pims + "</td>" +
    			    "<td class='last'>" + totP + "%</td>" +
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

function subDetailChart(strDat){
	$('input[name=strDat]').val(strDat);
	$.ajax({
		url		: "${pageContext.request.contextPath}/state/FM-STATE004_DEPT_LIST.do",
		type		: "post",
		data		: {"service":$('select[name=service]').val(),"strDat":strDat},
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

        		var deptCod = data.result[i].utwDepCod;
        		var dept = data.result[i].dept;
        		var isms = data.result[i].isms;
        		var skpng = data.result[i].skpng;
        		var pims = data.result[i].pims;
        		var totW = data.result[i].totW;
        		var totA = data.result[i].totA;
        		var totP = data.result[i].totP;

        		if(dept == 'ALL' && i == data.result.length-1){
    				tRow = "" +
    			     "<span class='wType00' style='width:210px;'><a href='#' onclick='userDetailChart(\""+ strDat +"\",\"\");'>전체</a></span>"+
    			     "<span class='wType00' style='width:130px;margin-left:4px;'>" + totA + "</span>" +
    			     "<span class='wType00' style='width:130px;margin-left:4px;'>" + isms + "</span>" +
    			     "<span class='wType00' style='width:130px;margin-left:4px;'>" + skpng + "</span>" +
    			     "<span class='wType00' style='width:130px;margin-left:4px;'>" + pims + "</span>" +
    			     "<span class='last wType00' style='width:130px;margin-left:4px;'>" + totP + "%</span>";
    				_div_2.append(tRow);
    			}else{
    				dt = "<span class='wType00' style='width:210px;'><a href='#' onclick='userDetailChart(\""+ strDat +"\",\""+ deptCod +"\");'>"+dept + "</a></span>";
    				tRow = "<li>" +
    			     dt +
    			     "<span class='wType00' style='width:130px;margin-left:4px;'>" + totA + "</span>" +
    			     "<span class='wType00' style='width:130px;margin-left:4px;'>" + isms + "</span>" +
    			     "<span class='wType00' style='width:130px;margin-left:4px;'>" + skpng + "</span>" +
    			     "<span class='wType00' style='width:130px;margin-left:4px;'>" + pims + "</span>" +
    			     "<span class='last wType00' style='width:130px;margin-left:4px;'>" + totP + "%</span>";
    				"</li>" ;
    				_div.append(tRow);
    			}
        	}
        	userDetailChart(strDat,'');
		},
		error : function(){
			alert('error');
		}
	});
	return false;
}

function userDetailChart(strDat,dept){
	$('input[name=dept]').val(dept);
	$.ajax({
		url		: "${pageContext.request.contextPath}/state/FM-STATE004_USER_LIST.do",
		type		: "post",
		data		: {"service":$('select[name=service]').val(),"dept":dept,"strDat":strDat},
		dataType	: "json",
		success	: function(data){
			var _div = $("#grid_w03");
			var _div_2 = $("#grid_w03_1");
			_div.empty();
			_div_2.empty();
			var list = "";
			var tRow = "";
        	for(var i = 0;i<data.result.length;i++){

        		var item = data.result[i];

        		var userNm = data.result[i].usernam;
        		var isms = data.result[i].isms;
        		var skpng = data.result[i].skpng;
        		var pims = data.result[i].pims;
        		var totW = data.result[i].totW;
        		var totA = data.result[i].totA;
        		var totP = data.result[i].totP;

        		if(userNm == 'ALL' && i == data.result.length-1){
    				tRow = "" +
    			     "<span class='wType00' style='width:210px;'>전체</span>"+
    			     "<span class='wType00' style='width:130px;margin-left:4px;'>" + totA + "</span>" +
    			     "<span class='wType00' style='width:130px;margin-left:4px;'>" + isms + "</span>" +
    			     "<span class='wType00' style='width:130px;margin-left:4px;'>" + skpng + "</span>" +
    			     "<span class='wType00' style='width:130px;margin-left:4px;'>" + pims + "</span>" +
    			     "<span class='last wType00' style='width:130px;margin-left:4px;'>" + totP + "%</span>";
    				_div_2.append(tRow);
    			}else{
    				dt = "<span class='wType00' style='width:210px;'>"+ userNm + "</span>";
    				tRow = "<li>" +
    			     dt +
    			     "<span class='wType00' style='width:130px;margin-left:4px;'>" + totA + "</span>" +
    			     "<span class='wType00' style='width:130px;margin-left:4px;'>" + isms + "</span>" +
    			     "<span class='wType00' style='width:130px;margin-left:4px;'>" + skpng + "</span>" +
    			     "<span class='wType00' style='width:130px;margin-left:4px;'>" + pims + "</span>" +
    			     "<span class='last wType00' style='width:130px;margin-left:4px;'>" + totP + "%</span>";
    				"</li>" ;
    				_div.append(tRow);
    			}
        	}

		},
		error : function(){
			alert('error');
		}
	});
}

function chartPopup(sMode){
	$('input[name=service]').val($('select[name=service]').val());
	window.open("","chartPopup","width=650, height=650, menubar=no, location=no, status=no,scrollbars=yes");
	$('input[name=sMode]').val(sMode);
	$('form[name=chartPopupForm]').attr('action','${pageContext.request.contextPath}/chart/FM-STATE004_popup.do').submit();
}
</script>
</head>
<body>
	<p class="history"><a href="">통계</a><span>&gt;</span>업무현황</p>
	<div class="conttitle">업무현황</div>
	<div class="pointTable">
		<div class="title">기간별</div>
		<div class="talbeArea">
			<table summary="통제항목별 진행현황을 통제항목, 전체, 완료, 비연, 미진행, 금일완료 등으로 확인 할 수 있습니다.">
				<colgroup>
					<col width="">
					<col width="150px">
					<col width="150px">
					<col width="150px">
					<col width="150px">
					<col width="150px">
				</colgroup>
				<caption>기간별 업무현황</caption>
				<thead>
                    <tr>
                        <th>기간</th>
                        <th>전체</th>
                        <th>ISMS</th>
                        <th>SKPNG</th>
                        <th>PIMS</th>
                        <th class="last">완료</th>
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
						<sb:select name="service" type="S" allText="서비스 전체" value="${searchVO.service }"/>
	                </li>
                    <li><button class="btnOk" onclick="detailChart();">확인</button></li>
                    <li>
						<button onclick="chartPopup('MONTH');"><span class="icoList"></span>차트</button>
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
	                    	<span class="wType00" style="width:210px;">부서</span>
	                        <span class="wType00" style="width:130px;">전체</span>
	                        <span class="wType00" style="width:130px;">ISMS</span>
	                        <span class="wType00" style="width:130px;">SKPNG</span>
	                        <span class="wType00" style="width:130px;">PIMS</span>
	                        <span class="last wType00" style="width:130px;">완료</span>
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
	<div class="contents">
	    <div class="listMoveArea dualArea" style="margin: 0;">
	        <div class="listArea" style="padding: 0px 0px 0px 1px; width: 100%;">
	            <div class="title">담당자별</div>
	            <div class="talbeArea">
	                <dl class="workingArea">
	                    <dt>
	                    	<span class="wType01" style="width:210px;">담당자</span>
	                        <span class="wType02" style="width:130px;">전체</span>
	                        <span class="wType00" style="width:130px;">ISMS</span>
	                        <span class="wType00" style="width:130px;">SKPNG</span>
	                        <span class="wType00" style="width:130px;">PIMS</span>
	                        <span class="last wType00" style="width:130px;">완료</span>
	                    </dt>
	                    <dd class="listArea">
	                        <ul class="listUl" id="grid_w03">
	                        </ul>
	                    </dd>
	                    <dd class="resume" id="grid_w03_1">
	                    </dd>
	                </dl>
					<div class="topBtnArea">
						<ul class="btnList">
							<li>
	                        	<button onclick="chartPopup('USER');"><span class="icoList"></span>차트</button>
                        	</li>
						</ul>
					</div>
	            </div>
	        </div>
		</div>
	</div>
	<form id="chartPopupForm" name="chartPopupForm" method="post" target="chartPopup">
		<input type="hidden" name="service" value="${searchVO.service }"/>
		<input type="hidden" name="dept" value=""/>
		<input type="hidden" name="strDat" value=""/>
		<input type="hidden" name="sMode" value=""/>
	</form>
</body>
</html>