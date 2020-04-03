<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
<%@ include file="/WEB-INF/include/contents.jsp"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<META http-equiv="X-UA-Compatible" content="IE=edge">
<title>SK broadband ISAMS</title>
<link rel="stylesheet" type="text/css" href="/common/css/reset.css"/>
<link rel="stylesheet" type="text/css" href="/common/css/common.css"/>
<link rel="stylesheet" type="text/css" href="/common/css/pop.css"/>
<!--[if IE]> <script src="/common/js/html5.js"></script> <![endif]-->
<script type="text/javascript" src="/common/js/jquery.js"></script>
<script type="text/javascript" src="/common/js/ismsUI.js"></script>
<script type="text/javascript" src="/common/js/jquery.form.js"></script>
<script type="text/javascript" src="/common/js/common.js"></script>
<script  type="text/javascript">

$(document).ready(function() {
	changeCocKey();
});

function fn_save(){	
	var usoAssCat = $("#usoAssCat").val();
	if($("#cocKey").val() == ''){
		alert("우려사항을 선택하여 주세요.");
		$("#cocKey").focus();
		return;
	}
	
	if($("#urgRskChk").val() == ''){
		alert("자산가치 선택하여 주세요.");
		$("#urgRskChk").focus();
		return;
	}
	if($("#urgRskChk").val() == ''){
		alert("위험처리를 선택하여 주세요.");
		$("#urgRskChk").focus();
		return;
	}
	
	if($("#urgSroKey").val() == "") {
		alert("위험 시나리오를 선택하여 주세요.");
		return;
	}
	
	var urgRskKey = $("#urgRskKey").val();
	var cocKey = $("#cocKey").val().split( '|' );;
	$("#urgCocKey").val(cocKey[0]);
	
	if(urgRskKey == ''){
		url = "${pageContext.request.contextPath}/riskm/FM-RISKM003_W.do";
	} else {
		url = "${pageContext.request.contextPath}/riskm/FM-RISKM003_U.do";
	}
	$.ajax({
		url			: url,
		type		: "post",
		data		: $("#popForm").serialize(),
		dataType	: "json",
		success	: function(data){
			alert("저장되었습니다.");
			opener.location.reload();
			window.close();
		},
		error : function(){
			alert('error');
		}
	});
}

function searchSenarioList() {
	$.ajax({
		url			: "${pageContext.request.contextPath}/code/getSenarioList.do",
		type		: "post",
		data		: {"code": $("#popCode").val(), "name": $("#popName").val()},
		dataType	: "json",
		success	: function(data){		
			var sroList = "";
			for(var i=0; i<data.result.length; i++) {
				sroList += 
					'<tr>'+
					'<td><a href="javascript:selSenario(\''+data.result[i].usmSroKey+'\',\''+data.result[i].usmSroCod+'\');">'+data.result[i].usmSroCod+'</a></td>'+					
					'<td class="last">'+data.result[i].usmSroDes+'</td>'+
					'</tr>';
			}
			if(data.result.length == 0){
				userList += '<tr class="last"><td class="last noDataList" colspan="2">시나리오 정보가 없습니다.</td></tr>';
			}	
			$("#popSroList").html(sroList);		
		}, 
		error : function(data){
			alert("error");
		}
	});
}


function selSenario(sroKey, sroCod) {
	$("#sroPopBG").hide();
	$("#sroLayerPop").hide();
	$("#urgSroKey").val(sroKey);
	$("#sroCod").val(sroCod);
		
	$(".talbeArea").css("z-index", "100");
	$("#wrap.pop .closeArea").show();
	$("#wrap.pop header").show();
}

function changeCocKey() {
	var cocKey = $("#cocKey").val().split( '|' );;
	$("#urgCocNam").html(cocKey[1]);
}
</script>
</head>
<body>
<form id="popForm" name="popForm" method="post">
	<input type="hidden" id="urgRskKey" name="urgRskKey" value="${info.urgRskKey}">
	<input type="hidden" id="urgSvcCod" name="urgSvcCod" >
	<input type="hidden" id="urgDepCod" name="urgDepCod" >
	<input type="hidden" id="urgMngId" name="urgMngId">
	<input type="hidden" id="urgGrpKey" name="urgGrpKey" value="0">
	<input type="hidden" id="urgAssVal" name="urgAssVal" value="3">
	<input type="hidden" id="urgCocKey" name="urgCocKey" value="${paramMap.urgCocKey}">
	
	<div id="skipnavigation">
	    <ul>
	        <li><a href="#content-box">본문 바로가기</a></li>
	    </ul>
	</div>
	<div id="wrap" class="pop">
		<header>
			<div class="borderBox"></div>
			<h1>위험관리 세부내용</h1>
		</header>
		<article class="cont" id="content-box">
			<div class="cont_container">
				<div class="contents">
					<div class="talbeArea">
						<table summary="위험관리 세부내용"> 
							<colgroup>
								<col width="20%">
								<col width="30%">
								<col width="20%">
								<col width="30%">
							</colgroup>
							<caption>자산코드세부내용</caption> 
							<tbody>
								<tr>
									<th scope="row"><label for="salAssVal">자산가치</label></th>
									<td class="fontLeft last" colspan="3">
										<select id="urgAssVal" name="salAssVal" disabled="true" class="selectBox"  title="항목 선택">
											<option value="3">1등급</option>
			                            </select> 
									</td>
								</tr>
								<tr>
									<th scope="row"><label for="urgRskChk">위험진단결과</label></th>
									<td class="fontLeft">
										<select id="urgRskChk" name="urgRskChk" class="selectBox"  title="항목 선택">
											<option value="">선택</option>
											<option value="N" <c:if test="${info.urgRskChk eq 'N'}">selected="selected"</c:if>>미흡</option>
											<option value="P" <c:if test="${info.urgRskChk eq 'P'}">selected="selected"</c:if>>부분미흡</option>
											<option value="Y" <c:if test="${info.urgRskChk eq 'Y'}">selected="selected"</c:if>>양호</option>
											<option value="A" <c:if test="${info.urgRskChk eq 'A'}">selected="selected"</c:if>>N/A</option>
										</select>
									</td>
									<th scope="row"><label for="urgRskPrc">위험처리</label></th>
									<td class="fontLeft last" colspan="3">
										<sb:select name="urgRskPrc"  type="A" allText="선택" code="RSKPRC" value="${info.urgRskPrc}"/>
									</td>
								</tr>
								<tr>
									<th scope="row"><label for="cocKey">우려사항</label></th>
									<td colspan="3" class="fontLeft last">
										<select id="cocKey" name="cocKey" class="selectBox" style="width:392px;" onchange="changeCocKey();">
											<option value="">선택</option>
											<c:forEach var="rskCoc" items="${rskCocList}" varStatus="status">
											  <c:choose>
											  	<c:when test="${fn:length(rskCoc.name) > 50}">
													<option value="${rskCoc.key}|${rskCoc.name}" <c:if test="${rskCoc.key eq info.urgCocKey}">selected="selected"</c:if>><c:out value="${rskCoc.code} | ${fn:substring(rskCoc.name, 0,50)}..."/></option>
												</c:when>
												<c:otherwise>
													<option value="${rskCoc.key}|${rskCoc.name}" <c:if test="${rskCoc.key eq info.urgCocKey}">selected="selected"</c:if>><c:out value="${rskCoc.code} | ${rskCoc.name}"/></option>												</c:otherwise>
											  </c:choose>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<th scope="row"><label for="urgCocNam">우려사항명</label></th>
									<td colspan="3" class="fontLeft last">
										<textarea id="urgCocNam" name="urgCocNam" disabled="true"  class="txtAreaType04"></textarea>
									</td>
								</tr>
								<tr>
									<th scope="row"><label for="urgMesDes">보호대책</label></th>
									<td class="fontLeft last" colspan="3">
										<textarea id="urgMesDes" name="urgMesDes" maxlength="250" class="txtAreaType04">${info.urgMesDes}</textarea>
									</td>
								</tr>
								<tr>
									<th scope="row"><label for="urgLimDes">대책제약사항</label></th>
									<td class="fontLeft last" colspan="3">
										<textarea id="urgLimDes" name="urgLimDes" maxlength="250" class="txtAreaType04">${info.urgLimDes}</textarea>
									</td>
								</tr>
								<tr>
									<th scope="row"><label for="urgMesYn">대책승인</label></th>
									<td colspan="3" class="fontLeft last">
										
										<select id="urgMesYn" name="urgMesYn" <c:if test="${paramMap.authKey ne 'P'}">disabled="true"</c:if> class="selectBox">
											<option value="Y" <c:if test="${info.urgMesYn eq 'Y'}">selected="selected"</c:if>>승인</option>
											<option value="N" <c:if test="${info.urgMesYn eq 'N' or empty info.urgMesYn}">selected="selected"</c:if>>미승인</option>
										</select>
									</td>
								</tr>
								<tr>
									<th scope="row"><label for="urgRstDes">조치결과</label></th>
									<td class="fontLeft last" colspan="3">
										<textarea id="urgRstDes" name="urgRstDes" maxlength="250" class="txtAreaType04">${info.urgRstDes}</textarea>
									</td>
								</tr>
								<tr>
									<th scope="row"><label for="urgRstYn">결과승인</label></th>
									<td colspan="3" class="fontLeft last">
										<select id="urgRstYn" name="urgRstYn"  <c:if test="${paramMap.authKey ne 'P'}">disabled="true"</c:if> class="selectBox">
											<option value="Y" <c:if test="${info.urgRstYn eq 'Y'}">selected="selected"</c:if>>승인</option>
											<option value="N" <c:if test="${info.urgRstYn eq 'N' or empty info.urgRstYn}">selected="selected"</c:if>>미승인</option>
										</select>
									</td>
								</tr>
								<tr>
									<th scope="row"><label for="sroCod">위험시나리오</label></th>
									<td class="fontLeft last" colspan="3">
										<input type="text" id="sroCod" name="sroCod" class="inputText" value="<c:out value='${info.usmSroCod}'/>" style="width:150px" readonly="readonly">
										<input type="hidden" id="urgSroKey" name="urgSroKey" value="<c:out value='${info.urgSroKey}'/>">
										<button type="button" class="openPop">검색</button>
										<div id="sroLayerPop" class="layerPop" style="display: none;">
									       <div class="popTitle">
									           <p>위험 시나리오 지정</p>
									       </div>
									       <div class="search">
									           <div class="defalt">
									               <fieldset class="searchForm">
									                    <legend>기본검색</legend>
														<ul class="detail">
									                        <li class="last">
																<input id="usmSroCod" name="usmSroCod" class="inputText wdShort" type="text" title="코드 입력" placeholder="코드 입력">
																<input id="usmSroDes" name="usmSroDes" class="inputText" style="width:250px" type="text" title="내용 입력" placeholder="내용을 입력하세요.">
															</li>
									                    </ul>
									                    <button type="button" onclick="searchSenarioList();" class="btnSearch defaltBtn">검색</button>
									                </fieldset>
									            </div>
											</div>
											<div class="layerContents" style="height:400px;">
												<div class="contents">
													<div class="talbeArea" style="z-index: 100;">
														<table>  
															<colgroup>
																<col width="20%">
																<col width="*">
															</colgroup>
									                        <caption>업무담당자</caption> 
															<thead>
																<tr>
																	<th scope="row">시나리오 코드</th>
																	<th scope="row" class="last">시나리오 내용</th>
																</tr>
															</thead>
															<tbody id="popSroList">
															</tbody>
														</table>
						                             </div>
						                         </div>
						                     </div>
						                     <button type="button" class="popClose"><span class="icoClose"><em>닫기</em></span></button>
						                 </div>
						                 <div id="sroPopBG" class="popBG" style="display: none;"></div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="bottomBtnArea">
					<ul class="btnList">
						<li><button type="button"  onclick="fn_save();"><span class="icoSave"></span>저장</button></li>
					</ul>
				</div>
			</div>
	       <div class="centerBtnArea closeArea">
               <button class="btnStrong close">닫기</button>
           </div>
	    </article>
	</div>
</form>
</body>
</html>