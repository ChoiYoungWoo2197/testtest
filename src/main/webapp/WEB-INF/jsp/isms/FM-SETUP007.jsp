<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
<script type="text/javascript">
	$(document).ready(function() {
		stOrgList();

		// 2018-04-19 s, 서비스 없음 검색조건 추가
		var service = "${searchVO.service}";
		$("#service").append('<option value="0000">서비스 미지정</option>');
		if (service == "0000") {
			$("#service").val(service);
		}
	});

	function fn_egov_search_Restde() {
		document.listForm.pageIndex.value = 1;
		document.listForm.submit();
	}

	function fn_egov_pageview(pageNo) {
		document.listForm.pageIndex.value = pageNo;
		document.listForm.action = "FM-SETUP007.do";
		document.listForm.submit();
	}

	function userPopup(key, mode) {
		window.open("", "userDetailPopup",	"width=460, height=700, menubar=no, location=no, status=no, scrollbars=yes");
		$("#usrKey").val(key);
		$("#mode").val(mode);
		document.userDetailForm.submit();
	}

	function addUser(mode) {
		window.open("", "userDetailPopup",	"width=460, height=700, menubar=no, location=no, status=no, scrollbars=yes");
		$("#mode").val("1");
		document.userDetailForm.submit();
	}

	function failClean(key) {
		if (confirm("사용자 잠금 해제 및 비밀번호를 초기화 하시겠습니까?")) {
			$.ajax({
					url : "${pageContext.request.contextPath}/setup/FM-SETUP007_FAIL_CLEAN.do",
					type : "post",
					data : {"key" : key	},
					dataType : "json",
					success : function(data) {
						alert("사용자 잠금 해제 및 비밀번호가 초기화 되었습니다.\n비밀번호는 아이디(사번)입니다.");
					},
					error : function() {
						alert('error');
					}
				});
		}
	}


	// 2018-05-10 s, 엑셀 다운로드/업로드
	function excelDown() {
		var params = formSerializeObject($("#searchForm"));
		openPostUrl("${pageContext.request.contextPath}/excel/FM-SETUP007.do", params);
	}

	function excelUpload() {
		openPostPopup("FM-SETUP007_excel_popup.do", 450, 360);
	}

</script>
	<form id="userDetailForm" name="userDetailForm" onsubmit="return false;" action="FM-SETUP007_popup.do" method="post" target="userDetailPopup">
		<input type="hidden" id="usrKey" name="usrKey" value="">
		<input type="hidden" id="mode" name="mode" value="">
		<input type="hidden" id="page" name="page" value="setup">
		<input type="hidden" id="menu" name="menu" value="09">
	</form>
		<c:import url="/titlebar.do" />
		<form id="searchForm" name="listForm" action="FM-SETUP007.do" method="post">
		<div class="search">
			<div class="defalt">
		        <fieldset class="searchForm">
		            <legend>기본검색</legend>
		            <ul class="detail newField">
						<li>
							<span class="title"><label for="stOrg">본부</label></span>
							<sb:select name="stOrg" type="stOrg" value="${searchVO.stOrg}" forbidden="true" allText="본부전체" />
						</li>
						<li>
							<span class="title"><label for="hqOrg">그룹,담당</label></span>
							<sb:select name="hqOrg" type="hqOrg" value="${searchVO.hqOrg}" forbidden="true" allText="그룹,담당전체" />
						</li>
		                <li>
		                    <span class="title"><label for="gpOrg">팀</label></span>
		                    <sb:select name="gpOrg" type="gpOrg" value="${searchVO.gpOrg}" forbidden="true" allText="팀전체" />
		                </li>
		            	<li>
		            		<span class="title"><label for="service">서비스</label></span>
		                    <sb:select name="service" type="S" value="${searchVO.service}" forbidden="true"/>
		            	</li>
						<li>
							<span class="title"><label for="auth">권한</label></span>
							<select id="auth" name="auth" class="selectBox">
                				<option value="" >전체</option>
								<c:forEach var="auh" items="${auhList}" varStatus="status">
									<option value="${auh.uatAuhKey}" <c:if test="${auh.uatAuhKey == searchVO.auth}">selected="selected" </c:if> >${auh.uatAuhNam}</option>
								</c:forEach>
							</select>
						</li>
						<li>
		            		<span class="title"><label for="uumUseYn">계정상태</label></span>
			               	<sb:select name="useYn" type="A" code="USER_STAT" value="${searchVO.useYn}" forbidden="true" />
		            	</li>
		            	<li>
		            		<span class="title"><label for="service">결재권한</label></span>
		                    <sb:select name="searchExt1" type="A" code="POS_YN" value="${searchVO.searchExt1}" forbidden="true" />
		            	</li>
		            	<li>
		            		<span class="title"><label for="service">실무담당</label></span>
		                    <sb:select name="searchExt2" type="A" code="TRA_YN" value="${searchVO.searchExt2}" forbidden="true" />
		            	</li>
						<li>
							<select id="searchCondition" class="selectBox" title="검색항목 선택" name="searchCondition">
								<option value="1"<c:if test="${searchVO.searchCondition == '1'}">selected="selected"</c:if>>이름</option>
								<option value="0"<c:if test="${searchVO.searchCondition == '0'}">selected="selected"</c:if>>사번</option>
							</select>
							<input id="searchKeyword" name="searchKeyword" value="${searchVO.searchKeyword}" class="inputText" type="text"	title="검색어 입력" placeholder="검색어를 입력하세요." style="width: 200px;" />
						</li>
						<li class="btnArea">
							<button type="button" onclick="fn_egov_search_Restde();" class="btnSearch ">조건으로 검색</button>
						</li>
					</ul>

				</fieldset>
			</div>
		</div>
		<div class="contents">
			<div class="topBtnArea">
				<ul class="btnList">
					<li><button type="button" onclick="addUser()"><span class="icoAdd"></span>사용자 등록</button></li>
					<li><button type="button" onclick="excelUpload()"><span class="icoSave"></span>엑셀 업로드</button></li>
					<li><button type="button" onclick="excelDown()"><span class="icoExl"></span>엑셀다운로드</button></li>
				</ul>
			</div>
			<div class="talbeArea">
				<table summary="계정설정 현황을 사번, 서비스, 부서, 권한, 이름, 로그인 실패횟수, 상세, 사용자	잠금해제 등의 항목으로 설명하고있습니다.">
					<colgroup>
						<col width="13%">
						<col width="">
						<col width="17%">
						<col width="11%">
						<col width="10%">
						<col width="8%">
						<col width="8%">
						<col width="8%">
						<col width="11%">
					</colgroup>
					<thead>
						<tr>
							<th scope="col">사번</th>
							<th scope="col">서비스</th>
							<th scope="col">부서</th>
							<th scope="col">권한</th>
							<th scope="col">이름</th>
							<th scope="col">계정상태</th>
							<th scope="col">로그인<br/> 실패횟수</th>
							<th scope="col">상세</th>
							<th scope="col" class="last">잠금해제 /<br/>비밀번호 초기화</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="result" items="${resultList}" varStatus="status">
							<tr>
								<td><c:out value="${result.uumUsrId}" /></td>
								<td><c:out value="${result.uumSvcCod}" /></td>
								<td><c:out value="${result.uumDepNam}" /></td>
								<td><c:out value="${result.uatAuhNam}" /></td>
								<td><c:out value="${result.uumUsrNam}" /></td>
								<td><c:out value="${result.useYnNam}" /></td>
								<td><c:out value="${result.uumPwdFai}" /></td>
								<td><button onclick="userPopup('${result.uumUsrKey}',0); return false">상세</button></td>
								<td class="last"><button onclick="failClean('${result.uumUsrKey}',0); return false">잠금해제</button></td>
							</tr>
						</c:forEach>
						<c:if test="${fn:length(resultList) == 0}">
							<tr class="last">
								<td class="last noDataList"colspan="9">
									등록된 계정이 없습니다.
								</td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</div>
			<div class="paging">
				<ui:pagination paginationInfo = "${paginationInfo}"	type="image" jsFunction="fn_egov_pageview"/>
				<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>"/>
			</div>
			<!-- div class="bottomBtnArea">
				<ul class="btnList">
					<li>
						<button onclick="addUser(); return false;">
							<span class="icoAdd"></span>등록
						</button>
					</li>
				</ul>
			</div -->
		</div>
		</form>