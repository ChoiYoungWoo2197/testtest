
$(document).ready(function() {

	$("#year-select-redirect").on("change", function (event) {
		location.href = "?year=" + $(this).val();
	});

	//document.onkeydown = doNotEnter;
	$(window).keypress(function(e) {
		if(e.target.nodeName == "TEXTAREA") {
			return true;
		}
	    if (e.keyCode == 13) {
	    	$(".btnSearch:visible").first().click();
	    	return false;
	    }
	});

	$(".close").click(function(){
		window.close();
	});


	$(".chkAll01").click(function(){
		if($(".chkAll01").is(":checked") == true ) {
			$(".chkNode01").prop("checked", true);
		} else {
			$(".chkNode01").prop("checked", false);
		}
	});
	$(".chkAll02").click(function(){
		if($(".chkAll02").is(":checked") == true ) {
			$(".chkNode02").prop("checked", true);
		} else {
			$(".chkNode02").prop("checked", false);
		}
	});
	$(".chkAll03").click(function(){
		if($(".chkAll03").is(":checked") == true ) {
			$(".chkNode03").prop("checked", true);
		} else {
			$(".chkNode03").prop("checked", false);
		}
	});

	if ($.jgrid) {
		$.jgrid.defaults = $.extend($.jgrid.defaults, {
			autoencode: true
		});
	}

	// scroll up
	if ($.scrollUp) {
		$.scrollUp();
	}
});

function doNotEnter(){

	if(event.target.nodeName == "TEXTAREA") {
		return true;
	}

    if(event.keyCode == 13){
		return false;
	}
}

/** input 배열 유효성 검사**/
function arrValidation(arrName) {
	var flg = true;
	arrName.each( function() {
		if($(this).val() == "") {
			$(this).focus();
			flg = false;
		}
	});
	return flg;
}

/** 영문, 숫자만 **/
function engNumValidation(str) {
	var chk=/^[a-zA-Z0-9]*$/;
	if(!chk.test(str)) {
		return false;
	}
	return true;
}

/** 영문, 숫자, 특수기호만**/
function nonKoValidation(str) {
	var chk = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{6,16}$/;
	if(!chk.test(str)) {
		return false;
	}
	return true;
}

/** 메일 **/
function mailValidation(str) {
	var chk = /^([0-9a-zA-Z_\.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$/;
	if(!chk.test(str)) {
		return false;
	}
	return true;
}

/** 담당자 정보를 가져온다 **/
function getWorkerList(dept, title) {
	if(dept != ""){
		$.ajax({
			url			: "${pageContext.request.contextPath}/code/getWorkerList.do",
			type		: "post",
			data		: {code:dept},
			dataType	: "json",
			success		: function(data){
				var inData ="";

				if(title == "") {
					title = "전체";
				}

				if(data.authKey != "P") {
					inData += '<option value="">'+title+'</option>';
				}
				for(var i=0; i<data.result.length; i++){
					inData += '<option value="'+data.result[i].code+'">'+data.result[i].name+'</option>';
				}
				$("#worker").html(inData);
			 },
			 error : function(){
				 alert('error');
			 }
		 });
	}else{
		$("#worker").html('<option value="">'+title+'</option>');
	}
}

function getWorkerInTag(data, title, val) {
	var inData = "";
	if(title == "" || title == undefined) {
		title = "전체";
	}
	if(data.authKey != "P") {
		inData += '<option value="">'+title+'</option>';
	}
	for(var i=0; i<data.result.length; i++){
		if(val != "" && val == data.result[i].code) {
			inData += '<option value="'+data.result[i].code+'" selected="selected">'+data.result[i].name+'</option>';
		} else {
			inData += '<option value="'+data.result[i].code+'">'+data.result[i].name+'</option>';
		}
	}
	return inData;

}

function isNull(obj){
	if(obj == "" || obj == null || obj == "undefined") {
		return true;
	} else {
		return false;
	}
}

function loadingBarSet(message) {
	if (!message) {
		message = "로딩중...";
	}
	$(document).ajaxStart(function() {
		$.blockUI({
			message : '<h1><img id="loadingBar" alt="loading" src="/common/images/common/loading.gif" />' + message + '</h1>',
			css : {
				border: 'none',
			    padding: '15px',
			    backgroundColor: '#000',
			    '-webkit-border-radius': '10px',
			    '-moz-border-radius': '10px',
			    opacity: .5,
			    color: '#fff'
			}
		});
	});

	$(document).ajaxStop(function() {
		$.unblockUI();
	});
}

/* 본부, 그룹,담당, 팀 조회*/
function stOrgList() {
	if ($("#stOrg").attr("disabled") != "disabled") {
		$("#hqOrg").chained("#stOrg");
		$("#gpOrg").chained("#hqOrg");
	}
}

/* 담당자 선택 팝업 */
function userListPopup(setUsrKey, setUsrId, setUsrName, apvGbn, setDepCode, setDepName) {
	var html = '<form id="usrPopForm" action="../common/FM-COMMON_users_popup.do" method="post" target="usrPopup">'
			 + '<input type="hidden" name="setUsrKey" value="' + setUsrKey + '"/>'
			 + '<input type="hidden" name="setUsrId" value="' + setUsrId + '"/>'
			 + '<input type="hidden" name="setUsrName" value="' + setUsrName + '"/>'
			 + '<input type="hidden" name="apvGbn" value="' + apvGbn + '"/>'
			 + '<input type="hidden" name="setDepCode" value="' + setDepCode + '"/>'
			 + '<input type="hidden" name="setDepName" value="' + setDepName + '"/>'
			 + '</form>';
	$("body").append(html);
	var win = window.open("","usrPopup","width=600, height=650, menubar=no, location=no, status=no,scrollbars=yes");
	$("#usrPopForm").submit().remove();
	win.focus();
}

/* set input value="" */
function inputClear(str) {
	var arr = str.split(",");
	$.each(arr, function(key, val) {
		$("#" + val).val("");
	});
}

/* 문자열 자르기 */
function cutString(str, len, dot) {
    var j = 0;
    if (typeof dot == "undefined") {
    	dot = "...";
    }
    for (var i = 0; i < str.length; i++) {
            j += (str.charCodeAt(i) > 128) ? 2 : 1;
            if (j > len) return str.substring(0,i) + dot;
    }
    return str;
}

function formSerialize(form) {
	var disabled = form.find(':input:disabled').removeAttr('disabled');
	var formData = form.serialize();
	disabled.prop("disabled", true);
	return formData;
}

function formSerializeObject(form) {
	var disabled = form.find(':input:disabled').removeAttr('disabled');
	var arr = form.serializeArray();
	var formData = {};
	$.each(arr, function() {
		formData[this.name] = this.value;
	});
	disabled.prop("disabled", true);
	return formData;
}

function validatePassword(str) {

	if (!str) {
		return "비밀번호를 입력해주세요.";
	}

	var arrKind = [ /(\d)/g, /([~`!@#$%\^&*()\-\=\_\+\[\]\{}\\\|\;\'\:\"\,\.\/\<\>\?])/g, /([a-zA-Z])/g ];
	var cnt = 0;
	for (var i = 0; i < arrKind.length; i++) {
		if (arrKind[i].test(str)) {
			cnt++;
		}
	}
	if (cnt < 2 || (cnt == 2 && str.length < 10) || (cnt == 3 && str.length < 8)) {
		return "비밀번호는 영문, 숫자, 특수문자 중\n2종류 조합 10자리 이상으로 또는\n3종류 조합 8자리 이상으로 입력해주세요.";
	} else {
		return;
	}
}


//window.open using POST
function openPostPopup(url, width, height, params, blank) {
	var input = "";
	var name = url.substring(url.indexOf("."), url.indexOf(".") - 10);
	if (blank == 1) {
		name += Math.floor(Math.random() * 1000) + 1;
	}
	if (params) {
		$.each(params, function(key, val) {
			input += '<input type="hidden" name="'+key+'" value="'+val+'"/>';
		});
	}
	var win = window.open("", name, "width="+width+", height="+height+", menubar=no, location=no, status=no, scrollbars=yes");
	$("#postPopup").empty()
		.append(input)
		.attr("action", url)
		.attr("target", name)
		.submit();
	win.focus();
}


//POST
function openPostUrl(url, params) {
	var input = "";
	var name = url.substring(url.indexOf("."), url.indexOf(".") - 10);
	if (params) {
		$.each(params, function(key, val) {
			input += '<input type="hidden" name="'+key+'" value="'+val+'"/>';
		});
	}
	$("#postPopup").empty()
		.append(input)
		.attr("action", url)
		.attr("target", "")
		.submit();
}

function workPopup(wrkKey, docKey) {
	openPostPopup("/mwork/FM-MWORK_popup.do", 730, 800, { utwWrkKey: wrkKey, utwTrcKey: docKey }, 1);
}

// 2016-10-31
function docPopup(docKey) {
	openPostPopup("/comps/FM-COMPS004_popup.do", 730, 800, { utdTrcKey: docKey }, 1);
}

// 2018-03-21 s, get grid text
function getGridTextIcon(cellvalue, css) {
   	var val = '';
	if ( !cellvalue) {
		return val;
	}
	$.each(cellvalue.split(","), function(k, v) {
		var arr = v.split(";");
		var color, text;
		if (arr.length == 2) {
			color = arr[0];
			text = arr[1];
		}
		else {
			color = '';
			text = arr[0];
		}
		val += '<span class="ui-corner-all cmpIcon'+ color;
		if (css) {
			val += ' ' + css;
		}
		val += '">'+ text +'</span>';
	});

	return val;
}

/**
 * 전역 범위로 사용하는 파일 다운로드 요청 함수
 * @param fileKey
 * @returns {boolean}
 */
function sendFileRequest(fileKey) {
	var $tempFileDownloadForm
		= $("<form id='tempFileDownloadForm' action='/common/getFileDown.do' method='post'></form>");

	var $downKeyInput = $("<input type='hidden' name='downKey'>");
	$downKeyInput.val(fileKey);

	$tempFileDownloadForm.append($downKeyInput);
	$("body").append($tempFileDownloadForm);

	$tempFileDownloadForm.submit();

	$tempFileDownloadForm.remove();

	return false;
}

