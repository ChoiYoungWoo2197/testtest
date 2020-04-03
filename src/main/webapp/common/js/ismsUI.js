$(document).ready(function(){

    $( "#wrap.pop article button.popClose span.icoClose").html("<em>닫기</em>");
    $( "#wrap article button span.icoCal").html("<em>달력</em>");

    /*---------------------  lnb 제어  ---------------------*/
    $( ".lnb > li > .toggle" ).click(function() {
        var e=$(this);
        var eHasClass=e.parent().hasClass('active');

        $( ".lnb > li > .toggle").parent().removeClass('active');
        $( ".lnb > li > .toggle").text("하위메뉴 열기");
        if(eHasClass==true){
            e.parent().removeClass('active');
        }else{
            e.parent().addClass('active');
            e.text("하위메뉴 닫기");
            $( ".lnb > li.sel > .toggle").text("하위메뉴");
        }
    });

    /*---------------------  메인 탭 제어  ---------------------*/
    $( ".noticeUl dt > a" ).click(function() {
        var e=$(this).parent().parent().parent();
        e.parent().find("li").removeClass('On');
        e.addClass('On');
    });

    /*---------------------  검색영역 상세검색 제어  ---------------------*/
    $( ".search .btnOpen" ).click(function() {
        var e=$(this);
        var eHasClass=e.hasClass('open');
        if(eHasClass==true){
            e.removeClass('open');
            e.parent().parent().parent().find('div.detail').hide();
        }else{
            e.addClass('open');
            e.parent().parent().parent().find('div.detail').show();
        }

    });

    /*---------------------  팝업 상세보기 제어  ---------------------*/
    $( ".cont_container .contents .title .btnOpen" ).click(function() {
        var e=$(this);
        var eHasClass=e.hasClass('open');
        if(eHasClass==true){
            e.removeClass('open');
            e.html('상세 열기 <span></span>');
            e.parent().next().hide();
            e.parent().parent().find('.topBtnArea').hide();
            e.parent().parent().find('.bottomBtnArea').hide();
            e.parent().parent().find('.centerBtnArea').hide();
            e.parent().parent().find('.leftBtnArea').hide();
            e.parent().parent().find('.paging').hide();
        }else{
            e.addClass('open');
            e.html('상세 닫기 <span></span>');
            e.parent().next().show();
            e.parent().parent().find('.topBtnArea').show();
            e.parent().parent().find('.bottomBtnArea').show();
            e.parent().parent().find('.centerBtnArea').show();
            e.parent().parent().find('.leftBtnArea').show();
            e.parent().parent().find('.paging').show();
        }
    });

    /*---------------------  메인 탭 제어  ---------------------*/
    $( ".detailList  > dt > a" ).click(function() {
        var e=$(this).parent().parent();
        $( ".detailList").removeClass('sel');
        e.addClass('sel');
    });

    /*---------------------  트리구조 영역 따라다니기  ---------------------*/
    $(window).on("scroll",function(){
		if($(this).scrollTop() > 0){
			$(".treeMovingArea").addClass('movingTop');
            //$(".sideOne").addClass('movingTop');

		}
		else{
			$(".treeMovingArea").removeClass('movingTop');
            //$(".sideOne").removeClass('movingTop');
		}
	});

    /*---------------------  달력 제어  ---------------------*/
    var	workList = $(".workList"),
        btnList = $(".btnMoreArea");
    for(i =0; i<workList.length-1; i++){
        var e=workList[i];
        if(e.getElementsByTagName("li").length>3){
            $(e).parent().find(".btnMoreArea").addClass("view");
        }
    };
    $(".calendarUI .talbeArea table tbody tr:nth-child(6)").addClass("passRow");
    $(".calendarUI .talbeArea table tbody td:nth-child(n+6)").addClass("passCol");

    $( ".workArea .listPop .listContainer .btnMoreArea" ).click(function() {
        var e=$(this).parent().parent().parent();
        $( ".workArea").removeClass('view');
        e.addClass('view');
    });
    $( ".workArea .listPop .listContainer button.btnSmall.close" ).click(function() {
        var e=$(this).parent().parent().parent();
        $( ".workArea").removeClass('view');
    });

    $( ".calendarUI .btnOpen" ).click(function() {
        var e=$(this);
        var eHasClass=e.hasClass('open');
        if(eHasClass==true){
            e.removeClass('open');
            e.parent().parent().parent().find('div.select_layer').hide();
        }else{
            e.addClass('open');
            e.parent().parent().parent().find('div.select_layer').show();
        }
    });
    $( ".calendarUI .select_layer .layer_foot a" ).click(function() {
        var e=$(this);
        $( ".calendarUI .btnOpen" ).click();
    });
   /*---------------------  메뉴리스트 제어  ---------------------*/
   var  mnList = $(".menuSelect .mainMenu"),
        subList = $(".menuSelect .mainMenu .subMenu .menuList")
        mnListHeight=0,
        mnListTotlaHeight=0,
        mnListWidth =$(".menuSelect").width()/4,
        listNum=0;


    mnList.width(mnListWidth);
    for(i =0; i<mnList.length; i++){
        var e=$(mnList[i]),
            numE=Math.floor(i/4);
        if(mnListHeight < e.height()){
            mnListHeight=e.height();
        }
        if(i!=0 && i-(4*numE)==0){
            for(var j=(4*listNum); j < i;j++){
                if(listNum==0){
                    $(mnList[j]).addClass("top");
                    $(mnList[j]).height(mnListHeight+1);
                }else{
                    $(mnList[j]).height(mnListHeight+2);
                }
                $(subList[j]).height(mnListHeight-$(mnList[j]).find("dt.item").height());
            }
            mnListTotlaHeight= mnListTotlaHeight+mnListHeight;
            mnListHeight=0;
        }

        if(i==mnList.length-1){
            if(mnListHeight < e.height()){
                mnListHeight=e.height();
            }
            for(var j=4*(numE); j < i+1;j++){
                $(mnList[j]).height(mnListHeight+2);
                $(subList[j]).height(mnListHeight-$(mnList[j]).find("dt.item").height());
            }
            mnListTotlaHeight= mnListTotlaHeight + mnListHeight;
        }

        listNum=numE;
        $(".menuSelect").height(mnListTotlaHeight+(listNum*2)+1);
    };
    function setLayout() {

		var wheight = $( window ).height(),
            wWidth = $( window ).width(),
            hHeight = $( "header" ).height(),
            sortInP =$( "#wrap article .relatedWork dd .centets .sort > li .sortType01" ),
            sortWidthP =$( "#wrap article .relatedWork dd .centets .sort > li .sortType01" ).width(),
            fHeight =$( "#wrap.pop article > .centerBtnArea").height(),
            popList = $( "#wrap article .layerPop" ),
            checkLastSearchList = $("#wrap article .search .detail > li:last-child").find("input");
		$( ".pop #content-box" ).height(wheight-125);

        if(wWidth<1010){
            $(".sideOne").addClass('leftStop');
        }else{
            $(".sideOne").removeClass('leftStop');
        }

        $( sortInP ).find("p").width(sortWidthP-91);
        for(i =0; i<popList.length; i++){
            var e=$(popList[i]);
                LayerPopHeight = $( e ).height(),
                LayerPopTitHeight = $( e ).find( ".popTitle" ).height(),
                LayerPopSearchHeight = $( e ).find( ".search" ).height();
            $( e ).find(".layerContents").height(LayerPopHeight-LayerPopTitHeight-LayerPopSearchHeight-130);
        };
        if(checkLastSearchList.hasClass("inputText")){
            if(!checkLastSearchList.hasClass("cal")){
                $(checkLastSearchList).parent().width("100%");
            }
        }
        $("#wrap article .search .detail.newField > li:last-child").css("width","auto");

	}

	$( window ).resize(function() {
		setLayout();
	});

	setLayout();
    $( "button.openPop" ).click(function() {
        var e=$(this),
            sortInP =$( "#wrap article .relatedWork dd .centets .sort > li .sortType01" ),
            sortWidthP,
            LayerPopHeight,
            LayerPopWidth,
            LayerPopTitHeight,
            LayerPopSearchHeight;
        $(e).parent().find(".layerPop").show();
        $(e).parent().find(".layerPopType02").show();
        $(e).parent().find(".popBG").show();
        $(".talbeArea").css("z-index", "100");
        if($(e).parent().parent().parent().parent().hasClass("talbeArea")){
            $(e).parent().parent().parent().parent().css("z-index", "11000");
        }else if($(e).parent().parent().parent().parent().parent().hasClass("talbeArea")){
            $(e).parent().parent().parent().parent().parent().css("z-index", "11000");
        }else{
            $(e).parent().parent().parent().parent().parent().parent().parent().parent().css("z-index", "11000");
        }
        $("#wrap.pop .closeArea").hide();
        $("#wrap.pop header").hide();
        sortWidthP = $( "#wrap article .relatedWork dd .centets .sort > li .sortType01" ).width();
        LayerPopHeight = $(e).parent().find( ".layerPop" ).height();
        LayerPopTitHeight = $(e).parent().find( ".layerPop .popTitle" ).height();
        LayerPopSearchHeight = $(e).parent().find( ".layerPop .search" ).height();
        $( sortInP ).find("p").width(sortWidthP-91);
        $(e).parent().find(".layerPop .layerContents" ).height(LayerPopHeight-LayerPopTitHeight-LayerPopSearchHeight-130);
        sortWidthP = $( "#wrap article .relatedWork dd .centets .sort > li .sortType01" ).width();
        LayerPopHeight = -$(e).parent().find(".layerPopType02" ).height()/2;
        LayerPopWidth = -$(e).parent().find(".layerPopType02" ).width()/2;
        $(e).parent().find(".layerPopType02" ).css("margin-left",LayerPopWidth);
        $(e).parent().find(".layerPopType02" ).css("margin-top",LayerPopHeight);
    });
    $( ".layerPop .popClose,.layerPopType02 .popClose" ).click(function() {
        var e=$(this);
        $(e).parent().parent().find(".layerPop").hide();
        $(e).parent().parent().find(".layerPopType02").hide();
        $(e).parent().parent().find(".popBG").hide();
        $(".talbeArea").css("z-index", "100");
        $("#wrap.pop .closeArea").show();
        $("#wrap.pop header").show();
    });

    $("#wrap article .search .detail > li:last-child").addClass("last");
    $("#wrap article .search .detail.newField > li:nth-last-child(2)").addClass("last");
    $("#wrap article .search .detail.newField > li:last-child").removeClass("last");
    $("#wrap article .relatedWork dd > ul > li:last-child").addClass("last");
    //$(".listDetailArea").addClass("subLine");
    var	detailAreaList = $(".contents > .listDetailArea");
    for(i =0; i<detailAreaList.length-1; i++){
        var e=detailAreaList[i];
        $(e).addClass("subLine");
    };


    /*
     * init
     */
    if ($.jgrid) {
    	$.jgrid.defaults = $.extend($.jgrid.defaults, {
    		autoencode: true,
    		height : 400
    	});
    }

    getApproval();
});
function chkSelectName(id){
    var newE=$("#"+id);
    var newEText = $("#"+id + " >option:selected").html();
    $(newE).hide();
    $(newE).parent().find(".selectOptionName").html(newEText);
}


// to number format 000,000
function toNumberFormat(number) {
	return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

// 레이어 결재 수
function getApproval() {
	if ($("#approval").length > 0) {
		$.post("/getApprovalCount.do", function(json) {
			var data = json.result;
			$("#approval .step1").text(data.step1);
			$("#approval .step2").text(data.step2);
			$("#approval .step3").text(data.step3);
		}, "json");
	}
}