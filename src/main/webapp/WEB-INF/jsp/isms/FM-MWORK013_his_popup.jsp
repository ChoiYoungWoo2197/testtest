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
<link rel="stylesheet" type="text/css" href="/common/css/reset.css" />
<link rel="stylesheet" type="text/css" href="/common/css/common.css" />
<link rel="stylesheet" type="text/css" href="/common/css/pop.css" />
<link rel="stylesheet" type="text/css" href="/common/css/sub.css" />
<!--[if IE]> <script src="/common/js/html5.js"></script> <![endif]-->
<script type="text/javascript" src="/common/js/jquery.js"></script>
<script type="text/javascript" src="/common/js/jquery.form.js"></script>
<script type="text/javascript" src="/common/js/ismsUI.js"></script>
<script type="text/javascript" src="/common/js/common.js"></script>
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
				<h1>결재리스트 이력</h1>
			</header>
            <article class="cont" id="content-box">
                <div class="cont_container">
					<div class="talbeArea">
						<table summary="결재리스트 이력"> 
							<colgroup>
								<col width="6%"/>
								<col width="8%"/>
								<col width="6%"/>
								<col/>
								<col width="8%"/>
								<col width="6%"/>
								<col width="12%"/>
								<col width="8%"/>
								<col width="6%"/>
								<col width="12%"/>
							</colgroup>
							<caption>결재리스트 이력</caption> 
							<thead>
							   <tr>
									<th rowspan="2">결재유형</th>
									<th rowspan="2">상신일</th>
									<th rowspan="2">상신자</th>
									<th rowspan="2">결재내용</th>
									<th colspan="3">1차 결재</th>
									<th colspan="3">2차 결재</th>
								</tr>
								<tr>
									<th>승인일</th>
									<th>결재자</th>
									<th>의견</th>
									<th>승인일</th>
									<th>결재자</th>
									<th>의견</th>
								</tr>
							</thead>
							<tbody id="tbody">
							<c:forEach var="result" items="${resultList}" varStatus="status">
			                	<tr>		                		
			                		<td><c:out value='${result.appGbnNam}'/></td>
			                		<td><c:out value='${result.uamReqMdh}'/></td>
			                		<td><c:out value='${result.uamReqNam}'/></td>
			                		<td><c:out value='${result.utdDocNam}'/></td>
			                		<td><c:out value='${result.uamCf1Mdh}'/></td>
			                		<td><c:out value='${result.uamCf1Nam}'/></td>
			                		<td><c:out value='${result.uamCf1Etc}'/></td>
			                		<td><c:out value='${result.uamCf2Mdh}'/></td>
			                		<td><c:out value='${result.uamCf2Nam}'/></td>
			                		<td class="last"><c:out value='${result.uamCf2Etc}'/></td>
			                	</tr>
							</c:forEach>
							</tbody>
						</table>
				    </div>
                </div>
                <div class="centerBtnArea closeArea">
				<button class="btnStrong close" type="button">닫기</button>
			</div>
            </article>
        </div>
    </body>
</html>