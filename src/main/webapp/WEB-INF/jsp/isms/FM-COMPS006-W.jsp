<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"  %>
<!DOCTYPE html>
<html lang="ko">
<head>
<link rel="stylesheet" type="text/css" href="/common/css/tree/jquery.treeview.css" />
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
<script type="text/javaScript">

var savedDepartments = ${savedDepartmentListJson}
var savedDepartmentCodes = _.reduce(savedDepartments, function (ids, department) {
	ids.push(department.uomDepCod);
	return ids;
}, []);

$(document).ready(function() {
	deptAjax();
});

function fn_auh_save(gubun) {
	if($("#uccSndNam").val() == "") {
		alert("코드명을 입력하세요.");
		$("#uccSndNam").focus();
		return;
	}

	strUrl = "${pageContext.request.contextPath}/comps/FM-COMPS006_U.do";

	$.ajax({
		 url : strUrl,
		 type : "post",
		 data : $("#sForm").serialize(),
		 dataType : "json",
		 success : function(data){
				alert("저장 되었습니다.");
				document.sForm.action = "FM-COMPS006_RW.do";
			   	document.sForm.submit();
		 },
		 error : function(){
			 alert('error');
		 }
	 });
}


/* 글 목록 화면 function */
function fn_selectList() {
	document.sForm.action = "FM-COMPS006.do?year=" + "${paramMap.bcyCode}";
   	document.sForm.submit();
}

function fn_getDeptList() {
	$('#searchCondition').val('');
	deptAjax();
}


function fn_search() {
	//초기화
	$('#browser').find('span.file').find('a').css('backgroundColor','');

	//빈 입력값 처리
	if($('#searchCondition').val() == "") {
		alert('부서를 입력해주세요.');
		return false;
	}

	$('#browser').children('li').each(function(i, item1) {
		//span --> folder
		if($(item1).find('span').attr('class').indexOf('folder') != -1) {
			$(item1).children('ul').children('li').each(function (i, item2) {
				if($(item2).find('span').attr('class').indexOf('folder') != -1) {
					$(item2).children('ul').children('li').each(function (i, item3) {
						if( $(item3).find('span.file').find('a').text().indexOf($('#searchCondition').val()) != -1) {
							//메뉴트리 토글
							$(item1).attr('class').indexOf('expandable') != -1 ? $("#browser").data("toggler").apply($(item1).children('span.folder')) : false;
							$(item2).attr('class').indexOf('expandable') != -1 ? $("#browser").data("toggler").apply($(item2).children('span.folder')) : false;
							//하이라이트
							$(item3).find('span.file').find('a').css('backgroundColor','yellow').focus();
						}
					});

				}
				else {
					if( $(item2).find('span.file').find('a').text().indexOf($('#searchCondition').val()) != -1) {
						//메뉴트리 토글
						$(item1).attr('class').indexOf('expandable') != -1 ? $("#browser").data("toggler").apply($(item1).children('span.folder')) : false;
						//하이라이트
						$(item2).find('span.file').find('a').css('backgroundColor','yellow').focus();
					}
				}
			});
		}
		//span --> file
		else {
			if( $(item1).find('span.file').find('a').text().indexOf($('#searchCondition').val()) != -1) {
				//하이라이트
				$(item1).find('span.file').find('a').css('backgroundColor','yellow').focus();
			}

		}

	});
}

function deptAjax(){
	$.ajax({
		type : "POST"
		, async : true
		, url : "${pageContext.request.contextPath}/comps/FM-COMPS004_dept.do" //Request URL
		, timeout : 30000
		, cache : false
		, data : $('#treeForm').serialize()
		, dataType: "json"
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
				var service = response.result[i].udmSvcCod;
				var lv1 = response.result[i].udmDep1lv;
				var lv2 = response.result[i].udmDep2lv;
				var lv3 = response.result[i].udmDep3lv;
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

				jsTreeStr += '<span class="'+classStr+'" id="'+lvl+'"><input type="checkbox" data-department-code="' + orgId + '" name="uomDepCod" class="'+lv1+'@@'+lv2+'@@'+lv3+'" value="'+orgId+"@@"+orgNM+'"'

				// 해당 코드를 주석 처리하고 저장된 부서 정보는 제일 마지막 라인에서 동적으로 처리한다.
				// if(service == $('#uomSvcCod').val()){
				// 	jsTreeStr += ' checked="checked"';
				// }else if(service != null){
				// 	jsTreeStr += ' disabled="disabled"';
				// }

				jsTreeStr += '/> <a href="'+orgId+'" class="jsTreeData">'+orgNM+'</a></span>';

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

			$('input[name=uomDepCod]').bind('click', function(){
				var _data = $(this).val().split('@@');
				var chk = $(this).is(":checked");
				$("input[name=uomDepCod]").each(function(i) {
					var _class = $(this).attr('class');
					if(_class.indexOf(_data[0]) > -1 && $(this).attr('disabled') == null){
						if(chk == true ) {
							$(this).prop("checked", true);
						} else {
							$(this).prop("checked", false);
						}
					}
				});
			});

			$('.jsTreeData').bind('click', function(){
				return false;
			});

			$('.jsTreeNoData').bind('click', function(){
				return false;
			});

			// 저장된 부서 정보(savedDepartments, savedDepartmentCodes)를 이용해 해당 부서 체크 체크여부를 적용한다.
			for (var i = 0; i < savedDepartmentCodes.length; i++) {
				$("[data-department-code='" + savedDepartmentCodes[i] +"']").prop("checked", "checked");

				var type = $("[data-department-code='" + savedDepartmentCodes[i] +"']").attr('class').split("@@");
				//d(1~2 depth)
				//d구성 >> 부모부서@@부모부서@@부서(체크된곳)  >> SKBD0000@@SKBDUU00@@SKBDUUX0
				for (var d=0 ; d<=1; d++) {
					var id = '#'+(Number(d) + 1 );
					//체크되어 있던곳의 자신의 부모(1~2 depth)를 찾아서 색깔을 바꿔준다.
					$("[data-department-code='" + savedDepartmentCodes[i] +"']").parents('li.expandable').find("span" + id).each(function(i) {
						var data = $(this).find('input').attr('class');
						var depCode = data.split("@@");
						if(depCode[d].indexOf(type[d]) != -1) {
							//자신의 부모를 찾았으면
							//$("#browser").data("toggler").apply($("[data-department-code='" + savedDepartmentCodes[i] +"']").parents('li.expandable').find('span.folder'));
							$("#browser").data("toggler").apply(this); //메뉴를 펼쳐준다.
							//$(this).find('a').css('color','red');	   //색깔을 바꾼다.
						}
					});
				}
			}

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

</script>
</head>
<body>

<form id="sForm" name="sForm" method="post">
<input type="hidden" id="uccFirCod" name="uccFirCod" value="SERVICE" />
<input type="hidden" id="uomSvcCod" name="uomSvcCod" value="${info.uccSndCod }" />
<input type="hidden" id="uccSndCod" name="uccSndCod" value="${info.uccSndCod }" />
<input type="hidden" id="hUccSndCod" name="hUccSndCod" value="${info.uccSndCod }" />
<input type="hidden" id="uomBcyCod" name="uomBcyCod" value="${paramMap.uomBcyCod }" />
<input type="hidden" name="service" value="${paramMap.service }" />
<c:import url="/titlebar.do" />
<div class="contents">
    <div class="talbeArea">
        <table summary="서비스부서설정">
            <colgroup>
                <col width="20%" />
				<col width="" />
            </colgroup>
            <caption>서비스부서설정 정보</caption>
            <tbody>
                <tr>
                    <th scope="row"><label for="year">연도</label></th>
                    <td class="fontLeft last">
                    	<input type="text" id="year" class="inputText" style="width:150px" name="bcyCode" value="${paramMap.bcyCode}" title="코드 선택" readonly="readonly"/>
                    </td>
              	</tr>
				<tr>
                    <th scope="row"><label for="uccSndCod">서비스 코드</label></th>
                    <td class="fontLeft last">
                    	<input type="hidden" name="uccUseYn" value="${info.uccUseYn }"/>
                    	<input type="text" id="uccSndCod" class="inputText" style="width:150px" value="${info.uccSndCod}" title="코드 선택" readonly="readonly"/>
                    </td>
              	</tr>
              	<tr>
                    <th scope="row"><label for="uccSndNam">서비스명</label></th>
                    <td colspan="3" class="fontLeft last">
                    	<input type="text" id="uccSndNam" name="uccSndNam" class="inputText" style="width:150px" value="${info.uccSndNam}" title="코드명 입력" readonly="readonly"/>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
    <div>
    	<div style="float: left; width: 50%;">
			<div class="title">부서 선택</div>
			<div class="search" style="width: 96.5%;">
				<div class="defalt">
					<fieldset class="searchForm">
						<legend>상세 검색</legend>
						<ul class="detail">
							<li class="last"><span class="title"><label for="searchCondition"> 부서명 검색</label></span>
								<input id="searchCondition" class="inputText" type="text"	title="부서명 입력" placeholder="부서명 입력" name="searchCondition" style="width:270px;"/>
							</li>
							<li></li>
						</ul>
						<button type="button" class="btnSearch" onclick="fn_search();">검색</button>
						<button type="button" class="btnSearch" onclick="fn_getDeptList();">초기화</button>
					</fieldset>
				</div>
			</div><br>
			<div class="foldList" id="target">
				<div class="treeBox" style="height: 430px; padding: 10px;">
					<ul id="browser" class="filetree">
					</ul>
				</div>
			</div>
		</div>
		<div style="float: right; width: 45%;">
			<div class="title">부서 현황</div>
			<div style="height: 535px; border: 1px solid #ddd;">
				<ul id="deptList" style="height: 515px; overflow: auto; padding: 10px;">
					<c:forEach var="result" items="${deptList}" varStatus="status">
						<li title="<c:out value="${result.uomDepCod}" />"><c:out value="${result.uomDepNam}" /></li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="bottomBtnArea">
	    <ul class="btnList">
	        <li>
				<button class="btnStrong" onclick="fn_auh_save('U'); return false;"><span class="icoRepair"></span>수정</button>
				<button class="btnStrong" onclick="fn_selectList(); return false;"><span class="icoList"></span>목록</button>
	        </li>
	    </ul>
	</div>
	<div id="loading">
		<img src="/common/images/common/tree/ajax-loader.gif" width="24" height="24" />
	</div>
</div>
</form>
</body>
</html>