<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<link type="text/css" rel="stylesheet" href="/common/css/dynatree/ui.dynatree.css" id="skinSheet"/>
<script type="text/javascript" src="/common/js/jquery.cookie.js"></script>
<script type="text/javascript" src="/common/js/jquery.dynatree.js"></script>

<script type="text/javascript">
	var firstTreeData = [];
	$(document).ready(function() {
		initDynaTree();
		$.ui.dynatree.getNode($('.dynatree-node')).toggleExpand();

		$("#btnCollapseAll").click(function(){
			$("#tree1").dynatree("getRoot").visit(function(node){
				node.expand(false);
			});
			return false;
		});
		$("#btnExpandAll").click(function(){
			$("#tree1").dynatree("getRoot").visit(function(node){
				node.expand(true);
			});
			return false;
		});


		// 2017-05-16, layer scroll 변경(낮은 해상도 호환)
		var offset = $("#treeContainer").offset();
		$(window).scroll(function() {
			if (offset.top < $(window).scrollTop()) {
				$("#treeContainer").stop().css("top", $(window).scrollTop() - offset.top + "px");
			}
			else {
				$("#treeContainer").stop().css("top", 0);
			}
		});
	});


	function fn_search() {
		$("#tree1").dynatree("destroy");
		$("#tree1").empty();
		initDynaTree();
		//document.treeForm.action = "${pageContext.request.contextPath}/comps/FM-COMPS005.do";
		//document.treeForm.submit();
	}

	function initDynaTree(){
		/* $.ui.dynatree.getNode($('.dynatree-node')).toggleExpand(); */
		$("#tree1").dynatree({
// 			checkbox: true,
			debugLevel: 0,
			selectMode: 3,
			activate:true,
			initAjax:{
				url		: "${pageContext.request.contextPath}/comps/FM-COMPS005_getStdList.do",
				data	: {service: $("#service").val(), docYn: $("#docYn").val(), searchCondition: $("#searchCondition").val()},
				type	: "post"
			},
			onLazyRead: function(node) {
				// 하위 node 불러오기
				onLazyRead(node);
			},
			onSelect: function(select, node) {
			},
			onActivate: function(node) {
				$("#btnMappingSampleDoc").prop("disabled", true);
				$("#fileCnt, #sampleDocCnt").html("");

				if(node.data.depth == "STND"){
					$("#stdName").text(node.data.title);		// 표준명
					$("#cntr1Name").text("");					// 통제목적
					$("#cntr2Name").text("");					// 통제항목
					$("#cntr2Dtl").text("");					// 상세내용
					$("#cntr3Key").text("");					// 통제점검
					$("#cntr3Name").text("");					// 점검항목
					$("#cntr3Dtl").text("");					// 설명
					$("#mappingBoard").text("");
					$("#mappingSampleDocList").hide();
				}
				else if(node.data.depth == "CNTR1"){
					$("#stdName").text(node.parent.data.title);	// 표준명
					$("#cntr1Name").text(node.data.title);		// 통제목적
					$("#cntr2Name").text("");					// 통제항목
					$("#cntr2Dtl").text("");					// 상세내용
					$("#cntr3Key").text("");					// 통제점검
					$("#cntr3Name").text("");					// 점검항목
					$("#cntr3Dtl").text("");					// 설명
					$("#mappingBoard").text("");
					$("#mappingSampleDocList").hide();
				}
				else if(node.data.depth == "CNTR2"){
					$("#stdName").text(node.parent.parent.data.title);	// 표준명
					$("#cntr1Name").text(node.parent.data.title);		// 통제목적
					$("#cntr2Name").text(node.data.title);				// 통제항목
					$("#cntr2Dtl").text("");							// 상세내용
					$("#cntr3Key").text("");							// 통제점검
					$("#cntr3Name").text("");							// 점검항목
					$("#cntr3Dtl").text("");							// 설명
					$("#mappingBoard").text("");
					$("#mappingSampleDocList").hide();
				}
				else if(node.data.depth == "CNTR3"){
					var replaceText1 = node.parent.data.lvDetail;
					var replaceText2 = node.data.lvDetail;
					if(replaceText1 != null){
						replaceText1 = replaceText1.replace(/\n/g, "<br />");
					}
					if(replaceText2 != null){
						replaceText2 = replaceText2.replace(/\n/g, "<br />");
					}

					$("#stdName").text(node.parent.parent.parent.data.title);	// 표준명
					$("#cntr1Name").text(node.parent.parent.data.title);		// 통제목적
					$("#cntr2Name").text(node.parent.data.title);				// 통제항목
					$("#cntr2Dtl").html(replaceText1);							// 상세내용
					$("#cntr3Key").text(node.data.title);						// 통제점검
					$("#cntr3Name").text(node.data.name);						// 점검항목
					$("#cntr3Dtl").html(replaceText2);							// 설명
					$("#mappingSampleDocList").show();

					var ucmCod = node.data.ucmCtrCod;
					var ucmGbn = node.data.ucmCtrGbn;
					var lvKey = node.data.key;

					$("#ucmCtrCod").val(ucmCod);							// 통제항목 테이블 통제항목 대표 코드
					$("#ucm3lvCod").val(lvKey);							// 통제항목 테이블 3lv_cod

					var delYn = node.data.select;

					// UWO_CTR_MTR의 ucm_ctr_key 갖고 오기
					getUcmCtrKey(ucmGbn, lvKey, delYn);
					$("#btnMappingSampleDoc").prop("disabled", false);
				}
			},
			onDblClick: function(node, event) {
				node.toggleSelect();
			},
			onKeydown: function(node, event) {
				if( event.which == 32 ) {
					node.toggleSelect();
					return false;
				}
			},
//				initId: "treeData",
			cookieId: "dynatree-Cb3",
			idPrefix: "dynatree-Cb3-"
		});
	}

	// dynatree node click할 때 일어나는 event
	function onLazyRead(node, arg0, arg1) {
		var key;
		var depth;
		var gbn;
		if(node) {
			gbn = node.data.ucmCtrGbn.toString();
			depth = node.data.depth.toString();
			key = node.data.key.toString();
		} else {
			depth = arg0;
			key = arg1;
		}
		node.appendAjax({
				url: "${pageContext.request.contextPath}/comps/FM-COMPS005_getCntrList.do",
				data: {"key": key, "depth":depth, "gbn":gbn, service: $("#service").val(), docYn: $("#docYn").val(), searchCondition: $("#searchCondition").val()},
				type: "post"
				,
				success: function(textStatus, data) {
					for(var i=0; i<data.length; i++){
						if(data[i].used_yn == "Y"){
							node.select = false;
						}
					}
				}
		});
		return node;
	}

	// UWO_CTR_MTR의 ucm_ctr_key 갖고 오기
	function getUcmCtrKey(ucmGbn, lvKey, delYn){
		var ucmCtrKey = "";

		$.ajax({
			url		: "${pageContext.request.contextPath}/comps/FM-COMPS005_getUcmCtrKey.do",
			type		: "post",
			data		: {"utcCtrGbn": ucmGbn, "utc3lvCod":lvKey},
			dataType	: "json",
			success	: function(data){
				ucmCtrKey = data.result;

				$("#ucmCtrKey").val(ucmCtrKey);
				// 관련지침서 get
				getGuideList(ucmCtrKey);

				// 매핑된 양식서 리스트 get
				getMappingSampleDocList(ucmCtrKey)
			},
			error : function(){
				alert("error :: getUcmCtrKey()");
			}
		});
	}

	// 관련문서 갖고오기
	function getGuideList(ucmCtrKey){
		$.ajax({
			url		: "${pageContext.request.contextPath}/comps/FM-COMPS005_getGuideList.do",
			type		: "post",
			data		: {"ctrKey": ucmCtrKey},
			dataType	: "json",
			success	: function(data){
				var list = "";
				for(var i=0; i<data.result.length; i++){
					if(i == data.result.length-1){
						list += '<a href="javascript:brdPopup('+data.result[i].ucbBrdKey+')"><span class="icoDown"></span>'+data.result[i].ubmBrdTle+'</a>';
					}else {
						list += '<a href="javascript:brdPopup('+data.result[i].ucbBrdKey+')"><span class="icoDown"></span>'+data.result[i].ubmBrdTle+'</a><br />';
					}
				}
				$("#fileCnt").html(" - 총 "+data.result.length+"건");
				$("#mappingBoard").html(list);

				if(data.result.length == 0){
					$("#mappingBoard").text("해당 관련문서가 없습니다.");
				}
			},
			error : function(){
				alert("error :: 관련문서를 갖고 오지 못했습니다.");
			}
		});
	}

	// 맵핑 증적양식 리스트 갖고오기
	function getMappingSampleDocList(ucmCtrKey){
		$.ajax({
			url		: "${pageContext.request.contextPath}/comps/FM-COMPS005_mappingSampleDocList.do",
			type		: "post",
			data		: {"mode": "get", "ctrKey": ucmCtrKey},
			dataType	: "json",
			success	: function(data){

				if(data.result.length == 0) {
					$("#mappingSampleDocList").hide();
				} else {
					$("#mappingSampleDocList").show();
				}

				var list = "";
				for(var i=0; i<data.result.length; i++){
					list += '<tr><td>'+data.result[i].service+'</td>'
						  + '<td>'+data.result[i].docGbnNm+'</td>'
						  + '<td class="fontLeft"><a href="javascript:docPopup('+data.result[i].utdTrcKey+')">'+data.result[i].utdDocNam+'</a></td>'
						  + '<td>'+data.result[i].workTerm+'</td>'
						  + '<td>'+data.result[i].utdAprYn+'</td>'
						  + '<td class="last"><a class="btnSmDelete" href="javascript:delMappingSampleDoc('+data.result[i].utcMapKey+');">설정해제</a></td></tr>';
				}
				$("#sampleDocCnt").html(" - 총 "+data.result.length+"건");
				$("#sampleDocTbody").empty().append(list);
			},
			error : function(){
				alert("error :: 맵핑된 증적양식 리스트를 갖고 오지 못했습니다.");
			}
		});
	}

	// 통제항목-증적양식 매핑 삭제
	function delMappingSampleDoc(mapKey){
		$.ajax({
			url			: "${pageContext.request.contextPath}/comps/FM-COMPS005_mappingSampleDocList.do",
			type		: "post",
			data		: {"mode": "del", "mapKey": mapKey},
			dataType	: "json",
			success	: function(data){
				// 통제항목키 갖고 오기
				var ctrKey = $("#ucmCtrKey").val();
				getMappingSampleDocList(ctrKey);
				alert("증적양식 연결을 삭제했습니다.");
			},
			error : function(){
				alert("error :: 맵핑 된 증적양식을 삭제하지 못했습니다.");
			}
		});
	}

	// 첨부파일 다운로드
	function getFile(key){
		$("#downKey").val(key);
		document.fileDown.action = "${pageContext.request.contextPath}/common/getFileDown.do";
	   	document.fileDown.submit();
	}

	// 통제항목-증적양식 매핑  popup
	function setMappingSampleDocf(){
		window.open("","setMappingSampleDocPopup","width=740, height=800, menubar=no, location=no, status=no, scrollbars=yes");
		document.mappingSampleDocForm.submit();
	}

	// 관련문서 게시판 팝업창
	function brdPopup(brdKey){
		window.open("","getbrdView","width=500, height=450, menubar=no, location=no, status=no, scrollbars=yes");
		$("#brdViewKey").val(brdKey);
		document.brdViewForm.submit();
	}

	function excelDown(){
		document.treeForm.action = "${pageContext.request.contextPath}/excel/FM-COMPS005.do";
	   	document.treeForm.submit();
	}
</script>
</head>
<body>
<c:import url="/titlebar.do" />
<div class="search">
	<div class="defalt">
		<fieldset class="searchForm">
			<legend>상세 검색</legend>
			<ul class="detail">
				<li>
                    <span class="title"><label for="service">서비스</label></span>
         			<sb:select name="service" type="S"/>
                </li>
                <li>
					<span class="title"><label for="docYn">관련업무</label></span>
                    <select name="docYn" class="selectBox" id="docYn">
						<option value="">전체</option>
						<option value="N">없음</option>
						<option value="Y">있음</option>
					</select>
				</li>
				<li class="last"><span class="title"><label for="searchCondition"> 항목명</label></span>
               		<input id="searchCondition" class="inputText" type="text"	title="점항목명 입력" placeholder="항목명 입력하세요." name="searchCondition" style="width:200px;"/>
				</li>
				<li></li>
			</ul>
			<button type="button" class="btnSearch" onclick="fn_search();">조건으로 검색</button>
		</fieldset>
	</div>
</div>
<div class="contents listForDivide forBtnDel">
	<form id="treeForm" name ="treeForm" method="post" >
    <div id="treeContainer" class="sideOne">
        <!-- <div>
			<a href="#" id="btnExpandAll">Expand all</a>
			<a href="#" id="btnCollapseAll">Collapse all</a>
		</div> -->
        <div id="tree1" class="treeBox">
        </div>
    </div>
    </form>
	<div class="sideTwo">
		<div class="selectDetail">
			<ul class="detailArea">
				<li class="floatList">
					<dl>
						<dt>표준명</dt>
						<dd id="stdName"></dd>
					</dl>
				</li>
				<li class="floatList">
					<dl>
					    <dt>통제점검</dt>
					    <dd id="cntr3Key"></dd>
					</dl>
				</li>
				<li>
					<dl>
					    <dt>통제목적</dt>
					    <dd id="cntr1Name"></dd>
					</dl>
				</li>
				<li>
					<dl>
						<dt>통제항목</dt>
						<dd id="cntr2Name"></dd>
					</dl>
				</li>
				<li>
					<dl>
					    <dt>상세내용</dt>
					    <dd id="cntr2Dtl"></dd>
					</dl>
				</li>
				<li>
				    <dl>
				        <dt>점검항목</dt>
				        <dd id="cntr3Name"></dd>
				    </dl>
				</li>
				<li>
					<dl>
					    <dt>관련 업무명 <strong id="sampleDocCnt"></strong>
					    <button id="btnMappingSampleDoc" onclick="setMappingSampleDocf();" disabled="disabled" style="float: right; margin-top: -3px;"><span class="icoSynch"></span>업무명(양식서)연결 설정</button>
					    </dt>
					    <dd>
						    <div id="mappingSampleDocList" class="talbeArea" style="display: none;">
					    	<table>
					    		<colgroup>
					    			<col width="10%"/>
					    			<col width="10%"/>
					    			<col width=""/>
					    			<col width="10%"/>
					    			<col width="10%"/>
					    			<col width="10%"/>
					    		</colgroup>
					    		<thead>
					    		<tr>
					    			<th>서비스</th>
					    			<th>문서양식</th>
					    			<th>업무명</th>
					    			<th>업무주기</th>
					    			<th>승인</th>
					    			<th class="last">설정</th>
					    		</tr>
					    		</thead>
					    		<tbody id="sampleDocTbody">
					    		</tbody>
					    	</table>
					    	</div>
						</dd>
					</dl>
				</li>
				<li>
				    <dl>
				        <dt>설명</dt>
				        <dd id="cntr3Dtl"></dd>
				    </dl>
				</li>
				<li>
				    <dl>
				        <dt>관련문서(정책, 지침 등) <strong id="fileCnt"></strong></dt>
				        <dd><ul id="mappingBoard" class="listArea"></ul></dd>
					</dl>
				</li>
			</ul>
		</div>
	</div>
</div>
<form id="form">
	<input type="hidden" id="ucmCtrCod" name="ucmCtrCod">
	<input type="hidden" id="ucm3lvCod" name="ucm3lvCod">
</form>
<form id="mappingSampleDocForm" name="mappingSampleDocForm" action="/comps/FM-COMPS005_popup.do" method="post" target="setMappingSampleDocPopup">
	<input type="hidden" id="ucmCtrKey" name="ucmCtrKey" value="">
</form>
<form id="brdViewForm" name="brdViewForm" action="/comps/FM-COMPS003_brd_popup.do" method="post" target="getbrdView">
	<input type="hidden" name="brdViewKey" id="brdViewKey" value="">
</form>
</body>
</html>

