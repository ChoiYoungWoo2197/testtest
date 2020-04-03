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
function changeMappingUser(userKey){
	$("#change").val(userKey);	
	
	var obj = document.getElementsByName("checkT");
	var checkCnt = 0;
	var arr = new Array;											
		for(var i=0; i<obj.length; i++){
			if(obj[i].checked == true){
				checkCnt++;
			}
    	}
		if(checkCnt ==0){
			alert("이관조건을 선택하세요");
			return;
		}		
		for(var i=0; i<obj.length; i++){
			if(obj[i].checked){
				arr.push(obj[i].value);
			}
		}	
		$("#gubun").val(arr);		
	
	$("#mappingForm").ajaxSubmit({
		//url : "${pageContext.request.contextPath}/mwork/FM-MWORK005_Change_Work.do",		
		url : "${pageContext.request.contextPath}/mwork/FM-MWORK002_setAgnId.do",
		success : function(data){
			alert("업무담당자 이관요청되었습니다.");	
			parent.opener.search();
			window.close();
		},
		error : function(e){
			alert("error");	
		}
	});
}
</script>
</head>
<body>
<body>
	<div id="skipnavigation">
	    <ul>
	        <li><a href="#content-box">본문 바로가기</a></li>
	    </ul>
	</div>
	<div id="wrap" class="pop">
		<header>
			<div class="borderBox"></div>
			<h1>담당자 정보</h1>
		</header>
		<article class="cont" id="content-box">
			<div class="cont_container">
				<div class="contents">
					<div class="title">담당자 정보</div>
					<div class="talbeArea">
		        		<table summary="담당자 정보"> 
		            		<colgroup>
								<col width="25%" />
								<col width="25%" />
								<col width="25%" />
								<col width="25" />
							</colgroup>
							<caption>담당자 정보</caption> 
							<thead>
								<tr>
									 <th scope="col">담당자</th>
	                                 <th scope="col">미진행</th>
	                                 <th scope="col">지연</th>
	                                 <th scope="col">완료</th>
								</tr>
							</thead>
							<tbody>
		    					<tr>
		    						<td><c:out value="${usrName}"/></td>
		    						<td><c:out value="${workNoworkCnt}"/></td>
		    						<td><c:out value="${workDelayCnt}"/></td>
		    						<td class="last"><c:out value="${workCompCnt}"/></td>
								</tr>						
		        			</tbody>
		    			</table>
					</div>
				</div>
				<div class="contents">
					<div class="title">이관내용 선택</div>
					<div class="talbeArea">
		        		<table summary="이관내용 선택"> 
		            		<colgroup>
								<col width="15%" />
								<col width="*" />
							</colgroup>
							<caption>이관내용 선택</caption>
							<tbody>
								<tr>
									<th scope="row" class="listTitle">선택</td>
									<td class="fontLeft last">
										<input type="checkbox" id="checkD" name="checkT" value="D"> <label for="checkD">지연</label>
										<input type="checkbox" id="checkN" name="checkT" value="N"> <label for="checkN">미진행</label>
										<input type="checkbox" id="checkC" name="checkT" value="C"> <label for="checkC">완료</label>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="contents">
					<div class="title">업무변경 가능자</div>
					<div class="talbeArea">
		        		<table summary="업무변경 가능자"> 
		            		<colgroup>
								<col width="50%" />
								<col width="*%" />
							</colgroup>
							<caption>업무변경 가능자</caption> 
							<thead>
								<tr>
									 <th scope="col">담당자</th>
	                                 <th scope="col">직급</th>
								</tr>
							</thead>
							<tbody> 
		                    <c:forEach var="result" items="${userList}" varStatus="status">                  	
								<tr style="cursor: pointer;" onclick="changeMappingUser('${result.uumUsrKey}')">
		                        	<td><c:out value="${result.uumUsrNam}"/></td>
		                            <td class="last"><c:out value="${result.uumPosCod}"/></td>                            
		                        </tr>   
							</c:forEach>
							<c:if test="${fn:length(userList) == 0}">
								<tr class="last">
									<td class="last noDataList" colspan="2">
										변경 가능한 사용자가 없습니다.
									</td>
								</tr>
							</c:if>					
		        			</tbody>
		    			</table>
					</div>
				</div> 

           </div>
           <div class="centerBtnArea">
				<button class="btnStrong close" onclick="return false;" >닫기</button>
			</div>
	</article>
</div>
<form id="mappingForm" name="mappingForm" method="post">
	<input type="hidden" id="before" name="before" value="${usrKey }">
	<input type="hidden" id="change" name="change">
	<input type="hidden" id="gubun" name="gubun">
</form>
</form>
</body>
</html>
