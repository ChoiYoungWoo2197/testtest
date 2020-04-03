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
<script type="text/javascript">

	$(document).ready(function() {
		setDispRegRsk();
		getWorkerList();
		$(":input:radio[name=urm_del_yn]").change(setDispRegRsk);
	});
	
	function setDispRegRsk(){
		var regRskChecked = $(":input:radio[name=urm_del_yn]:checked").val();
		if(regRskChecked == "Y"){
			$("#urmGrpCon").hide();
			$("#urmGrpItg").hide();
			$("#urmGrpAvt").hide();
			$("#urmGrpTot").hide();
			$("#urmGrpLvl").hide();
		}else{
			$("#urmGrpCon").show();
			$("#urmGrpItg").show();
			$("#urmGrpAvt").show();
			$("#urmGrpTot").show();
			$("#urmGrpLvl").show();
		}
	}
	
	function saveAssetGroupInfo(){
		/* var regRsk = $(":input:radio[name=urm_del_yn]:checked").val(); */
		
		/* if(regRsk == 'N'){
			var cScore = $("#urm_grp_con").val();
			var iScore = $("#urm_grp_itg").val();
			var aScore = $("#urm_grp_avt").val();
			
			if(cScore == '0' || iScore == '0' || aScore == '0'){
				alert("그룹 기밀성, 가용성, 무결성 점수를 설정해 주십시오.");
				return false;
			}
		} */
		
		/* var strNamLen = getTextLength($("#uag_grp_nam").val());
		if(strNamLen == 0){
			alert("그룹이름을 입력하세요.");
			return;
		} */
		
		
		/* var arrCat = $("#arr_cat_key").val().split("|"); 
		$("#uag_cat_key").val(arrCat[0]);
		$("#uag_cat_cod").val(arrCat[1]);
		$("#uag_grp_des").val($("#uag_svc_cod > option:selected").text()); */
		
		$("#assetGroupInputForm").ajaxSubmit({
			url : "${pageContext.request.contextPath}/asset/FM-ASSET002_assetGroup_save.do",
			success : function(data){
				alert("저장 되었습니다.");
				opener.location.reload();
				window.close();
			},
			error : function(data){
				alert("error");
			}
		});
	}
	
	/* function getImportanceScore(){
		
		var cScore = $("#urm_grp_con option:selected").text();
		var iScore = $("#urm_grp_itg option:selected").text();
		var aScore = $("#urm_grp_avt option:selected").text();
		var sum = 0;
		var grade = "";
		
		if(cScore != "" && iScore != "" && aScore != ""){
			sum = Number(cScore) + Number(iScore) + Number(aScore);

			if(sum >=3 && sum <=4 ){
				grade = "L(1)";
			} else if(sum >=5 && sum <=7 ){
				grade = "M(2)";
			} else if(sum >=8 && sum <=9 ){
				grade = "H(3)";
			} else{
				grade="";
			}
			
			$("#urm_grp_tot").val(sum);
			$("#urm_grp_lvl").val(grade);
		}
	}
	 */
	// 문자열 길이
   /*  function getTextLength(str) {
        var len = 0;
        for (var i = 0; i < str.length; i++) {
            if (escape(str.charAt(i)).length == 6) {
                len++;
            }
            len++;
        }
        return len;
    } */
	
    function getWorkerList() {
    	//var dept = $("select[name=uag_dep_cod]").val();
    	var dept = ($("#uag_dep_cod").val());

    	if(dept != ""){
    		$.ajax({
    			url			: "${pageContext.request.contextPath}/code/getWorkerList.do",
    			type		: "post",
    			data		: {code:dept},
    			dataType	: "json",
    			success		: function(data){
    				var inData ="";
    				inData += '<option value="">선택</option>';
    				for(var i=0; i<data.result.length; i++){
    					if('${riskVO.uag_adm_id}' == data.result[i].code) {
    						inData += '<option value="'+data.result[i].code+'" selected="selected">'+data.result[i].name+'</option>';
    					} else {
    						inData += '<option value="'+data.result[i].code+'">'+data.result[i].name+'</option>';
    					}
    					
    				}
    				$("#uag_adm_id").html(inData);
    			 },
    			 error : function(){
    				 alert('error');
    			 }
    		 });
    	}else{
    		$("#uag_adm_id").html('<option value="${riskVO.uag_adm_id}">"${riskVO.uag_adm_nam}"</option>');
    	}
    }
</script>
</head>    
<body>
	<div id="wrap" class="pop">
		<header>
			<div class="borderBox"></div>
			<h1>자산 그룹 설정</h1>
		</header>
		<article class="cont" id="content-box">
			<div class="cont_container">
				<div class="contents">
					<div class="talbeArea">
						<form id="assetGroupInputForm" name="assetGroupInputForm" method="post">
							<input type="hidden" id="uag_grp_key" name="uag_grp_key" value="${riskVO.uag_grp_key}">
							<input type="hidden" id="urm_grp_key" name="urm_grp_key" value="${riskVO.uag_grp_key}">
							<input type="hidden" id="urm_rsk_key" name="urm_rsk_key" value="${riskVO.urm_rsk_key}">
							<input type="hidden" id="uag_dep_cod" name="uag_dep_cod" value="${riskVO.uag_dep_cod}">
							<input type="hidden" id="uag_cat_key" name="uag_cat_key">
							<input type="hidden" id="uag_cat_cod" name="uag_cat_cod">
							<table>
								<colgroup>
									<col width="35%"/>
									<col width="*" />
								</colgroup>
								<tbody>
									<tr>
										<th scope="row" class="listTitle"><label for="uag_sub_nam">서비스</label></th>
										<td class="fontLeft last">
											<c:out value="${riskVO.uag_svr_nam}"/>
										</td>
									</tr>
									<tr>
										<th scope="row" class="listTitle"><label for="uag_dep_cod">부서</label></th>
										<td class="fontLeft last">
											<c:out value="${riskVO.uag_dep_nam}"/>
										</td>
									</tr>
									<tr>
										<th scope="row" class="listTitle"><label for="uag_adm_id">그룹관리자</label></th>
										<td class="fontLeft last">
											<select name="uag_adm_id" class="selectBox" id="uag_adm_id"  onselect="getWorkerList();">
												<option value="${riskVO.uag_adm_id}">${riskVO.uag_adm_nam}</option>
											</select>
										</td>
									</tr>
									<tr>
										<th scope="row" class="listTitle"><label for="uag_cat_nam">그룹유형</label></th>
										<td class="fontLeft last">
											<c:out value="${riskVO.uag_cat_nam}"/>
										</td>
									</tr>
									<tr>
										<th scope="row" class="listTitle"><label for="uag_grp_nam">그룹이름</label></th>
										<td class="fontLeft last">
											<c:out value="${riskVO.uag_grp_nam}"/>
										</td>
									</tr>
									<tr>
										<th scope="row" class="listTitle"><label for="uag_grp_des">그룹설명</label></th>
										<td class="fontLeft last">
											<input type="text" id="uag_grp_des" name="uag_grp_des" class="inputText" size="" value='<c:out value="${riskVO.uag_grp_des}"/>' maxlength="25" required="required">
										</td>
									</tr>
									<tr>
										<th scope="row" class="listTitle"><label for="uag_grp_lvl">그룹 등급</label></th>
										<td class="fontLeft last">
											<c:out value="${riskVO.uag_grp_lvl}"/>
										</td>
									</tr>
									<tr>
										<th scope="row" class="listTitle"><label for="uag_use_yn">사용여부</label></th>
										<td class="fontLeft last">
											<input type="radio" name="uag_use_yn" value="Y" <c:if test="${mode == 'add' || riskVO.uag_use_yn == 'Y'}"> checked="checked"</c:if> /><label for="uag_use_yn">사용</label>
											<input type="radio" name="uag_use_yn" value="N" <c:if test="${mode == 'update' && riskVO.uag_use_yn == 'N'}"> checked="checked"</c:if> /><label for="uag_use_yn">미사용</label>
										</td>
									</tr>
								</tbody>
							</table>
						</form>
					</div>
				</div>
				<div class="bottomBtnArea">
					<button type="button" onclick="saveAssetGroupInfo();"><span class="icoAdd"></span>저장</button>
				</div>
			</div>
			<div class="centerBtnArea">
				<button class="btnStrong close">닫기</button>
			</div>
		</article>
	</div> 
</body>
</html>