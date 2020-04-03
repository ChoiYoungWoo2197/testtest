<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
<script type="text/javascript" src="/common/js/jquery.form.js"></script>
<script type="text/javascript" src="/common/js/ismsUI.js"></script>
<script type="text/javascript" src="/common/js/common.js"></script>
<script  type="text/javascript">


function setWrkId(){

	var angKey = $("#uumAgnKey").val();
	var addList = $("#addList").val();
	var utwWrkSta = false;
	var uamStaCod = false;

	$(".utwWrkSta").each(function() {
		if ($(this).val() == "완료") {
			utwWrkSta = true;
			return false;
		}
	});

	$(".uamStaCod").each(function() {
		if ($(this).val() == "11" && $(this).val() != "") {
			uamStaCod = true;
			return false;
		}
	});

	if(utwWrkSta){
		alert("이관 될 업무중에 완료된 업무가 있습니다.");
		return;
	}

	if(uamStaCod){
		alert("이관 될 업무중에 결재 진행중인 업무가 있습니다.");
		return;
	}

	if( !$("#uumAgnKey").val()) {
		alert("이관 담당자를 선택해 주십시요.");
		$("#usrPopBtn").click();
		return;
	}

	if(confirm("이관 요청 하시겠습니까?")){
		$.ajax({
			url		: "${pageContext.request.contextPath}/mwork/FM-MWORK006_reqTransfer.do",
			type		: "post",
			data		: {usrKey: "${usrKey}", angKey: angKey, addList: addList},
			dataType	: "json",
			success	: function(data) {
				if (data.result == "0") {
					try {
						alert("업무이관 요청을 완료하였습니다.");
						window.opener.searchList();
					}
					catch (ex) {
						opener.location.reload(true);
					}
					finally {
						self.close();
					}
				}
				else if (data.result == "9") {
					alert("지정된 이관처리 결재자가 없습니다.\n담당자에게 문의해 주십시요.");
					//opener.mypage();
				}
				else {
					alert("업무이관 요청을 실패하였습니다.");
				}
			},
			error : function(){
				alert("업무이관 요청을 실패하였습니다.");
			}
		});
	}
}

</script>
</head>
<body>
	<form id="userForm" name="userForm" method="post">
	<input type="hidden" id="usrKey" name="usrKey" value="<c:out value='${usrKey}'/>" />
		<div id="skipnavigation">
			<ul>
				<li><a href="#content-box">본문 바로가기</a></li>
			</ul>
		</div>
		<div id="wrap" class="pop">
			<header>
				<div class="borderBox"></div>
				<h1>이관 요청업무</h1>
			</header>
			<article class="cont" id="content-box">
				<div class="cont_container">
					<div class="contents">
						<div class="talbeArea">
							<table summary="이관 요청업무">
								<colgroup>
									<col width="25%" />
									<col width="" />
									<col width="20%" />
									<col width="15%" />
								</colgroup>
								<caption>이관업무 정보</caption>
								<thead>
									<tr>
										<th scope="col">업무상태</th>
										<th scope="col">업무명</th>
										<th scope="col">결재상태</th>
										<th scope="col" class="last">업무주기</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="result" items="${list}" varStatus="status">
										<tr>
											<td><c:out value="${result.utwWrkSta}"/></td>
											<td><c:out value="${result.utdDocNam}"/></td>
											<td><c:out value="${result.uamStaNam}"/></td>
											<td class="last"><c:out value="${result.utdTrmGnm}"/>
												<input type="hidden" class="utwWrkSta" value="<c:out value='${result.utwWrkSta}'/>"/>
												<input type="hidden" class="uamStaCod" value="<c:out value='${result.uamStaCod}'/>"/>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="cont_container">
					<div class="contents">
						<div class="talbeArea">
							<table summary="이관 담당자 지정">
								<colgroup>
									<col width="20%" />
									<col width="" />
									<col width="23%" />
								</colgroup>
								<tr>
									<th scope="row" class="listTitle"><label for="uumApvTwo">이관 담당자 :</label></th>
						    		<td class="fontLeft last">
						    			<input type="hidden" id="uumAgnKey" name="uumAgnKey" value="<c:out value='${user.uumAgnKey}'/>"/>
						    			<input type="hidden" id="uumAgnId" name="uumAgnId" value="<c:out value='${user.uumAgnId}'/>" />
						    			<input type="text" id="uumAgnNam" name="uumAgnNam" style="width: 80px;" class="inputText readonly" value="<c:out value='${user.uumAgnNam}'/>" readonly="readonly"/>
						    			<button type="button" id="usrPopBtn" name="usrPopBtn" onclick="userListPopup('uumAgnKey','uumAgnId','uumAgnNam', 'TY');"><span class="icoPer"></span></button>
						    		</td>
						    		<td class="fontRight last">
						    			<input type="hidden" id="addList" name="addList" value="${addList}" />
						    			<button type="button" id="wrkKey" name="wrkKey" onclick="setWrkId();"><span class="icoSave"></span>이관 요청</button>
						    		</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
				<div class="cont_container">
					<div class="bottomBtnArea">
						<ul class="btnList">
							<li></li>
						</ul>
					</div>
					<div class="centerBtnArea closeArea">
               			<button class="btnStrong close">닫기</button>
          			</div>
				</div>
			</article>
		</div>
	</form>
	<form id="mappingForm" name="mappingForm" method="post"></form>
</body>
</html>
