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
<script type="text/javascript" src="/common/js/jquery.js"></script>
<link rel="stylesheet" type="text/css" href="/common/css/reset.css"/>
<style>
html, body, .popup {height: 100%;}
.popup { overflow: hidden; min-width: 0; padding: 0 10px; border-top: none; }
.popup .wrapTop { border-bottom: 1px solid #ddd; background: #fff; text-align: center; position: relative; }
.popup .wrapTop .top { height: 5px; border-radius: 0 0 5px 5px; background: #ff5a20; }
.popup .wrapTop h1{ padding: 5px 50px; display: inline-block; font-size: 25px; font-family: 'NG'; font-weight: 700; color: #333; }
.popup .wrapTop .top_close { cursor:pointer; position: absolute; top: 15px; right: 10px; width: 25px; height: 25px; background: url(../common/images/common/blit/blit.png) no-repeat -47px -760px; }
.popup .wrapContent { overflow: hidden; padding-top: 10px; }
.popup .content { padding: 0 10px 20px 10px; overflow: auto; }
.popup .bottom { border-top: 1px solid #ddd; padding: 15px 0; text-align: center; }
.popup .bottom table td:first-child {text-align: left;}
.popup .bottom table td:last-child {text-align: right;}
label { cursor: pointer; padding: 10px; }
</style>
<script  type="text/javascript">
    $(window).load(function() {
        popup_resize();
        $(window).resize(function(){popup_resize()});
        $(".top_close").click(function(){self.close()});
        $(".moveArticle").click(function(){
            if(!opener.window.name.length) opener.window.name = "skb_parent";
            $("#formBoard input[name=selectedId]").val("${popupVo.brd_key}");
            $("#formBoard").attr("target", opener.window.name).submit();
            self.close();
        });
        $(".closeOneDay").click(function(){
            var dt = new Date();
            var days = 1;
            dt.setTime(dt.getTime() + (days * 24 * 60 * 60 * 1000));
            document.cookie = "popup_${popupVo.popup_id}_${popupVo.brd_key}=N; expires=" + dt.toUTCString() + "; Path=/;";
            self.close();
        });
        self.focus();
    });
    function popup_resize(){
        $(".content").height($("html").height()-($(".wrapTop").height()+$(".bottom").height()+62));
    }
</script>
</head>
<body>
<div class="pop popup">
    <div class="wrapTop">
        <div class="top"></div>
        <h1>${popupVo.title}</h1>
        <div class="top_close"></div>
    </div>
    <div class="wrapContent" id="content-box">
        <div class="content">
            ${popupVo.content}
        </div>
        <div class="bottom">
            <table>
                <tbody>
                    <tr>
                        <td><label class="moveArticle">게시물로 이동</label></td>
                        <td><label class="closeOneDay">하루동안 열지않기</label></td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
<form id="formBoard" action="/board/FM-BOARD001_V.do" method="post">
    <input type="hidden" name="selectedId" />
</form>
</body>
</html>