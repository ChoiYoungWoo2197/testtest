<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
<%@ include file="/WEB-INF/include/contents.jsp"%>
<%@ include file="/WEB-INF/include/head.jsp"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<META http-equiv="X-UA-Compatible" content="IE=edge">
<title>SK broadband ISAMS</title>
<link rel="stylesheet" type="text/css" href="/common/css/reset.css"/>
<link rel="stylesheet" type="text/css" href="/common/css/common.css"/>
<link rel="stylesheet" type="text/css" href="/common/css/pop.css"/>
<link rel="stylesheet" type="text/css" href="/common/css/sub.css"/>
<link rel="stylesheet" type="text/css" href="/common/css/jquery-ui.css"/>
<!--[if IE]> <script src="/common/js/html5.js"></script> <![endif]-->
<script type="text/javascript" src="/common/js/jquery.js"></script>
<script type="text/javascript" src="/common/js/jquery.form.js"></script>
<script type="text/javascript" src="/common/js/jquery.metadata.js"></script>
<script type="text/javascript" src="/common/js/jquery.MultiFile.js"></script>
<script type="text/javascript" src="/common/js/jquery.blockUI.js"></script>
<script type="text/javascript" src="/common/js/jquery-ui.js"></script>
<script type="text/javascript" src="/common/js/ismsUI.js"></script>
<script type="text/javascript" src="/common/js/common.js"></script>

<link rel="stylesheet" type="text/css" href="/common/css/tree/jquery.treeview.css" />
<!-- css block -->
<style type="text/css">
	#loading{
		border:0;
		display:none;
		text-align: center;
		background: #ffffff;
		filter: alpha(opacity=60);
		opacity: alpha*0.6;
	}
	.memLast{
		padding:65px 0 !important;
	}
</style>
<script src="/common/js/tree/jquery.cookie.js" type="text/javascript"></script>
<script src="/common/js/tree/jquery.treeview.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		deptAjax();
		getWorkerList();
	});

	function deptAjax(){
		$.ajax({
			type : "POST"
			, async : true
			, url : "${pageContext.request.contextPath}/comps/FM-COMPS004_dept.do" //Request URL
			, timeout : 30000
			, cache : false
			, data : $('#treeForm').serialize()
			, dataType : "json"
			, contentType: "application/x-www-form-urlencoded; charset=UTF-8"
			, error : function(request, status, error) {
				alert("code : " + request.status + "\r\nmessage : " + request.reponseText);
			}
			, success : function(response, status, error) {
				var jsTreeStr = '';
				for(var i = 0; i < response.result.length; i++) {
					var orgNM = response.result[i].udmDepNam;
					var orgId = response.result[i].udmDepCod;
					var lvl = response.result[i].udmDepLvl;
					var nLvl = 0;
					if(i < response.result.length - 1) {
						nLvl = response.result[i+1].udmDepLvl;
					} else {
						nLvl = 0;
					}

					if(lvl < nLvl) {
						jsTreeStr += '<li class="expandable"><div class="hitarea expandable-hitarea"></div>';
					} else {
						jsTreeStr += '<li>';
					}

					var classStr = '';
					if(lvl < nLvl) {
						classStr = 'folder';
					} else {
						classStr = 'file';
					}

					jsTreeStr += '<span class="'+classStr+'" id="'+lvl+'"><a href="'+orgId+'" class="jsTreeData">'+orgNM+'</a></span>';

					if(lvl == 1 && nLvl == 1) {
						jsTreeStr += '</li>';
					} else if(lvl == 1 && nLvl == 2) {
						jsTreeStr += '<ul style="display: none;">';
					} else if(lvl == 2 && nLvl == 1) {
						jsTreeStr += '</li></ul></li>';
					} else if(lvl == 2 && nLvl == 2) {
						jsTreeStr += '</li>';
					} else if(lvl == 2 && nLvl == 3) {
						jsTreeStr += '<ul style="display: none;">';
					} else if(lvl == 3 && nLvl == 1) {
						jsTreeStr += '</li></ul></li></ul></li>';
					} else if(lvl == 3 && nLvl == 2) {
						jsTreeStr += '</li></ul></li>';
					} else if(lvl == 3 && nLvl == 3) {
						jsTreeStr += '</li>';
					} else if(lvl == 3 && nLvl == 4) {
						jsTreeStr += '<ul style="display: none;">';
					} else if(lvl == 4 && nLvl == 1) {
						jsTreeStr += '</li></ul></li></ul></li></ul></li>';
					} else if(lvl == 4 && nLvl == 2) {
						jsTreeStr += '</li></ul></li></ul></li>';
					} else if(lvl == 4 && nLvl == 3) {
						jsTreeStr += '</li></ul></li>';
					} else if(lvl == 4 && nLvl == 4) {
						jsTreeStr += '</li>';
					}

				}

				$('#browser').html(jsTreeStr);

				$("#browser").treeview({
					collapsed: true,
					animated: "fast",
					prerendered: true,
					persist: "location"
				});

				$('ul li:last-child').each(function() {
					if($(this).attr('class') == 'expandable') {
						$(this).attr('class', 'closed expandable lastExpandable').find('div:first').attr('class', 'hitarea lastExpandable-hitarea');
					}
				});

				$('.jsTreeData').bind('click', function(){
					var _data = $(this).attr('href');
					$.ajax({
						type : "POST"
						, async : true
						, url : "${pageContext.request.contextPath}/comps/FM-COMPS004_member.do" //Request URL
						, timeout : 30000
						, cache : false
						/* , data : {'dept':_data,'service':$('input[name=service]').val()} */
						, data : {'dept':_data}
						, contentType: "application/x-www-form-urlencoded; charset=UTF-8"
						, error : function(request, status, error) {
							alert("code : " + request.status + "\r\nmessage : " + request.reponseText);
						}
						, success : function(response, status, error) {
							$('#sehGridTb').empty();
							if(response.result.length > 0){
							for(var i = 0; i < response.result.length; i++) {
								var code = response.result[i].code;
								var uumUsrId = response.result[i].uumUsrId;
								var name = response.result[i].name;
								var uumSvcCod = response.result[i].uumSvcCod;
								var service = response.result[i].service;
								var uumDepCod = response.result[i].uumDepCod;
								var dept = response.result[i].dept;
								var pos = response.result[i].pos;
								var _row = "";

								_row += "<tr>";
								_row += "<td>";
								_row += "<input name=\"sehWorker\" class=\"chkNode02\" type=\"checkbox\" value=\"" + code + "\">";
								_row += "<input name=\"utmWrkId\" type=\"hidden\" value=\"" + code + "\">";
								_row += "<input name=\"utmSvcCod\" type=\"hidden\" value=\"" + uumSvcCod + "\">";
								_row += "<input name=\"utmDepCod\" type=\"hidden\" value=\"" + uumDepCod + "\">";
								_row += "</td>";
								_row += "<td>" + service + "</td>";
								_row += "<td>" + dept + "</td>";
								_row += "<td class=\"sehId\">" + uumUsrId + "</td>";
								_row += "<td>" + name + "</td>";
								_row += "<td class=\"last\">" + pos + "</td>";
								_row += "</tr>";

								$('#sehGridTb').append(_row);
							}
							}else{
								$('#sehGridTb').append('<tr class="last"><td class="last noDataList memLast" colspan="7">검색된 담당자가 없습니다.</td></tr>');
							}
						}
					});
					return false;
				});
				/*
				$('.jsTreeData').bind('click', function(){
					var _data = $(this).attr('href').split(',');
					var idTemp = true;
					if($('#sehGridTb').html() == '<tr class="last"><td class="last noDataList memLast" colspan="7">지정된 담당자가 없습니다.</td></tr>'){
						$('#sehGridTb').empty();
					}

					$('.memId').each(function (){
						if($(this).text() == _data[1]){
							idTemp = false;
						}
					});
					if(idTemp){
						var _row = "";

						_row += "<tr>";
						_row += "<td>";
						_row += "<input name=\"chkWorker\" class=\"chkNode01\" type=\"checkbox\" value=\"" + _data[0] + "\">";
						_row += "<input name=\"utmWrkId\" type=\"hidden\" value=\"" + _data[0] + "\">";
						_row += "<input name=\"utmDivCod\" type=\"hidden\" value=\"" + $(this).parent().parent().parent().parent().parent().parent().parent().find('.jsTreeNoData').attr('href') + "\">";
						_row += "<input name=\"utmSvcCod\" type=\"hidden\" value=\"" + $(this).parent().parent().parent().parent().find('.jsTreeNoData').attr('href') + "\">";
						_row += "<input name=\"utmDepCod\" type=\"hidden\" value=\"" + _data[2] + "\">";
						_row += "</td>";
						_row += "<td>" + $(this).parent().parent().parent().parent().parent().parent().parent().find('.jsTreeNoData').html() + "</td>";
						_row += "<td>" + $(this).parent().parent().parent().parent().find('.jsTreeNoData').text() + "</td>";
						_row += "<td class=\"memId\">" + _data[1] + "</td>";
						_row += "<td>" + $(this).text(); + "</td>";
						_row += "<td class=\"last\">" + _data[3] + "</td>";
						_row += "</tr>";

						$('#setGridTb').append(_row);
					}else{
						alert('선택된 담당자입니다.');
					}
					return false;
				});
				*/
				$('.jsTreeNoData').bind('click', function(){
					return false;
				});

			}
			, beforeSend: function() {
				var padingTop = (Number(($('#target').css('height')).replace("px","")) / 2) - 12;
				$('#loading').css('position', 'absolute');
				$('#loading').css('left', $('#target').offset().left);
				$('#loading').css('top', $('#target').offset().top);
				$('#loading').css('width', $('#target').css('width'));
				$('#loading').css('height', $('#target').css('height'));
				$('#loading').css('padding-top', padingTop);
				$('#loading').show().fadeIn('fast');
			}
			, complete: function() {
				$('#loading').fadeOut();
				$('#loading').css('border', 0).css('display', 'none').css('text-align', 'center').css('background', '#ffffff').css('filter', 'alpha(opacity=60)').css('opacity', 'alpha*0.6');
			}
		});
	}

	function getWorkerList(){
		$.ajax({
			url		: "${pageContext.request.contextPath}/comps/FM-COMPS004_workerList.do",
			type		: "post",
			data		: {"utdTrcKey":$("#utdTrcKey").val()},
			dataType	: "json",
			success	: function(data){
				var workerList = "";
				for(var i=0; i<data.result.length; i++) {
					workerList += '<tr>';
					workerList += '<td>';
					//workerList += "<input name=\"chkWorker\" class=\"chkNode01\" type=\"checkbox\" value=\"" + code + "\">";
					workerList += '<input type="checkbox" name="chkWorker" class="chkNode01" value="'+data.result[i].uumUsrKey+'">';
					//workerList += '<input type="hidden" name="utmWrkId" value="'+data.result[i].uumUsrKey+'">';
					//workerList += '<input type="hidden" name="utmDivCod" value="'+data.result[i].uumDivCod+'">';
					//workerList += '<input type="hidden" name="utmSvcCod" value="'+data.result[i].uumSvcCod+'">';
					//workerList += '<input type="hidden" name="utmDepCod" value="'+data.result[i].uumDepCod+'">';
					workerList += '</td>';
					workerList += '<td>'+data.result[i].service+'</td>';
					workerList += '<td>'+data.result[i].dept+'</td>';
					workerList += '<td class=\"memId\">'+data.result[i].uumUsrId+'</td>';
					workerList += '<td>'+data.result[i].uumUsrNam+'</td>';
					workerList += '<td class="last">'+data.result[i].pos+'</td>';
					workerList += '</tr>';
				}
				if(data.result.length == 0){
					workerList += '<tr class="last"><td class="last noDataList memLast" colspan="7">지정된 담당자가 없습니다.</td></tr>';
				}
				$("#setGridTb").html(workerList);
			 },
			 error : function(){
				 alert('error');
			 }
		 });
	}

	function delWorker(){
		var cnt = 0;
		$("input[name=chkWorker]:checked").each(function(i) {
			$(this).parent().parent().empty();
			cnt++;
		});
		if(cnt == 0) {
			alert("삭제 할 담당자를 선택하세요.");
			return;
		}
	}

	function saveWorkerList() {
		var cnt = 0;
		$("input[name=chkWorker]").each(function(i) {
			cnt++;
		});
		if(cnt == 0 || $('#setGridTb').html() == '<tr class="last"><td class="last noDataList memLast" colspan="7">지정된 담당자가 없습니다.</td></tr>'){
			alert('담당자를 선택하세요.');
			return false;
		}else{
			if($("#utdTrcKey").val() == "") {
				opener.saveSD(3);
			}else{
				saveWorkerListAjax('');
			}
		}
	}

	function saveWorkerListAjax(tKey) {
		var utmTrcKey = "";
		if(tKey != ''){
			$("#utdTrcKey").val(tKey);
		}
		var utmTrcKey = utmTrcKey = $("#utdTrcKey").val();
		var utmBcyCod = $("#utdBcyCod").val();
		var utmRgtId = $("#utdRgtId").val();

		var usrData = "";
		var cnt = 0;
		$("input[name=chkWorker]").each(function(i) {
			if(cnt != 0) {
				usrData += "," + $(this).val();
			} else {
				usrData += $(this).val();
			}
			cnt++;
		});

		$.ajax({
			url			: "${pageContext.request.contextPath}/comps/FM-COMPS004_worker_save.do",
			type		: "post",
			data		: {"userKey":usrData, "utmTrcKey":utmTrcKey, "utmBcyCod":utmBcyCod, "utmRgtId":utmRgtId},
			dataType	: "json",
			success	: function(data){
				alert("저장 되었습니다.");
				opener.location.href = "FM-COMPS004_popup.do?utdTrcKey="+ $("#utdTrcKey").val();
				self.close();
			},
			error : function(data){
				alert("error");
			}
		});
	}

	function memberAjax(){
		$.ajax({
			type : "POST"
			, async : true
			, url : "${pageContext.request.contextPath}/comps/FM-COMPS004_member.do" //Request URL
			, timeout : 30000
			, cache : false
			, data : {'searchKeyword':$('input[name=searchKeyword]').val(),'service':$('input[name=service]').val()}
			, contentType: "application/x-www-form-urlencoded; charset=UTF-8"
			, error : function(request, status, error) {
				alert("code : " + request.status + "\r\nmessage : " + request.reponseText);
			}
			, success : function(response, status, error) {
				$('#sehGridTb').empty();
				if(response.result.length > 0){
				for(var i = 0; i < response.result.length; i++) {
					var code = response.result[i].code;
					var uumUsrId = response.result[i].uumUsrId;
					var name = response.result[i].name;
					var uumSvcCod = response.result[i].uumSvcCod;
					var service = response.result[i].service;
					var uumDepCod = response.result[i].uumDepCod;
					var dept = response.result[i].dept;
					var pos = response.result[i].pos;
					var _row = "";

					_row += "<tr>";
					_row += "<td>";
					_row += "<input name=\"sehWorker\" class=\"chkNode02\" type=\"checkbox\" value=\"" + code + "\">";
					_row += "<input name=\"utmWrkId\" type=\"hidden\" value=\"" + code + "\">";
					_row += "<input name=\"utmSvcCod\" type=\"hidden\" value=\"" + uumSvcCod + "\">";
					_row += "<input name=\"utmDepCod\" type=\"hidden\" value=\"" + uumDepCod + "\">";
					_row += "</td>";
					_row += "<td>" + service + "</td>";
					_row += "<td>" + dept + "</td>";
					_row += "<td class=\"memId\">" + uumUsrId + "</td>";
					_row += "<td>" + name + "</td>";
					_row += "<td class=\"last\">" + pos + "</td>";
					_row += "</tr>";

					$('#sehGridTb').append(_row);
				}
				}else{
					$('#sehGridTb').append('<tr class="last"><td class="last noDataList memLast" colspan="7">검색된 담당자가 없습니다.</td></tr>');
				}
			}
		});
		return false;
	}

	function memberInput(){
		var inputData = "";
		var cnt = 0;
		var idTemp = true;
		$("input[name=sehWorker]:checked").each(function(i) {
			var _data = $(this).parent().parent().html();
			$('.memId').each(function (){
				if(_data.indexOf($(this).text()) > 0){
					idTemp = false;
				}
			});
			inputData += "<tr>";
			inputData += _data.replace('sehWorker','chkWorker').replace('chkNode02','chkNode01').replace('sehId','memId');
			inputData += "</tr>";
			cnt++;
		});
		if(cnt == 0) {
			alert("추가 할 담당자를 선택하세요.");
			return;
//		}else if (!idTemp){
//			alert('선택된 담당자입니다.');
//			return;
		}else{
			if($('#setGridTb').html() == '<tr class="last"><td class="last noDataList memLast" colspan="7">지정된 담당자가 없습니다.</td></tr>'){
				$('#setGridTb').empty();
			}
			$('#setGridTb').append(inputData);
		};
	}
</script>
</head>
<body>
	<div id="skipnavigation">
	    <ul>
	        <li><a href="#content-box">본문 바로가기</a></li>
	    </ul>
	</div>
	<div id="wrap" class="pop">
	    <header>
	        <div class="borderBox"></div>
	        <h1>담당자 지정</h1>
	    </header>
   		<form id="treeForm" name="treeForm" method="post" >
   			<input type="hidden" id="utdTrcKey"	name="utdTrcKey" value="${paramMap.utdTrcKey}"/>
			<input type="hidden" id="utdBcyCod"	name="utdBcyCod" value="${paramMap.utdBcyCod}"/>
			<input type="hidden" id="utdRgtId" name="utdRgtId" value="${paramMap.utdRgtId}"/>
			<input type="hidden" id="service" name="service" value="${paramMap.utdSvcCod}"/>
			<article class="cont" id="content-box">
				<div class="cont_container">
					<div class="contents" style="width: 200px; padding-right: 20px; float: left;">
						<div class="foldList" id="target">
							<div class="treeBox" style="height:500px;">
								<ul id="browser" class="filetree">
								</ul>
							</div>
						</div>
					</div>
					<div class="contents" style="float: left;width: 390px;margin-right: -20px;">
						<div class="title">
							담당자 지정
							<div class="topBtnArea" style="top: -40px; margin-bottom: -42px; position: relative;">
								<ul class="btnList">
	                                <li>
										<input id="searchKeyword" name="searchKeyword" class="inputText" type="text" title="이름 입력" placeholder="담당자 이름을" onkeypress="doNotEnter();" style="width:80px;">
										<button type="button" onclick="memberAjax();" class="btnSearch defaltBtn">검색</button>
									</li>
									<li>
										<button type="button" onclick="memberInput();"><span class="icoPer"></span>담당자 추가</button>
									</li>
								</ul>
							</div>
						</div>
						<div class="talbeArea" style="height: 190px; overflow-y: auto;">
							<table>
								<colgroup>
									<col width="5%">
									<col width="15%">
									<col width="*">
									<col width="20%">
									<col width="20%">
									<col width="20%">
								</colgroup>
                                <caption>업무담당자</caption>
								<thead>
									<tr>
										<th scope="row"><input type="checkbox" class="chkAll02" title="리스트 전체 선택"></th>
										<th scope="row">서비스</th>
										<th scope="row">부서</th>
										<th scope="row">사번</th>
										<th scope="row">이름</th>
										<th scope="row" class="last">직급</th>
									</tr>
								</thead>
								<tbody id="sehGridTb">
									<tr class="last"><td class="last noDataList memLast" colspan="7">검색된 담당자가 없습니다.</td></tr>
								</tbody>
							</table>

						</div>
					</div>
					<div class="contents" style="float: left;width: 390px;margin-right: -20px;">
						<div class="title">
							선택된 담당자
							<div class="topBtnArea" style="top: -42px; margin-bottom: -42px; position: relative;">
								<ul class="btnList">
									<li>
										<button type="button" onclick="saveWorkerList();"><span class="icoPer"></span>담당자 저장</button>
									</li>
									<li>
										<button type="button" onclick="delWorker();"><span class="icoDel"></span>삭제</button>
									</li>
								</ul>
							</div>
						</div>
						<div class="talbeArea" style="height: 190px; overflow-y: auto;">
							<table>
								<colgroup>
									<col width="5%">
									<col width="15%">
									<col width="*">
									<col width="20%">
									<col width="20%">
									<col width="20%">
								</colgroup>
                                <caption>업무담당자</caption>
								<thead>
									<tr>
										<th scope="row"><input type="checkbox" class="chkAll01" title="리스트 전체 선택"></th>
										<th scope="row">서비스</th>
										<th scope="row">부서</th>
										<th scope="row">사번</th>
										<th scope="row">이름</th>
										<th scope="row" class="last">직급</th>
									</tr>
								</thead>
								<tbody id="setGridTb">
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="centerBtnArea closeArea">
					<button type="button" class="btnStrong close">닫기</button>
				</div>
			</article>
		</form>
	</div>
	<div id="loading">
		<img src="/common/images/common/tree/ajax-loader.gif" width="24" height="24" />
	</div>
</body>
</html>