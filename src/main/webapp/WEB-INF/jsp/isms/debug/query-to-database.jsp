<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <META http-equiv="X-UA-Compatible" content="IE=edge">
    <title>SK broadband ISAMS Debug Tool</title>
    <style>
        * {
            box-sizing: border-box
        }

        body {
            padding: 2rem
        }

        .box {
            width: 100%;
            border: 1px solid #ccc;
            margin-bottom: 2rem;
        }

        .box-header {
            border-bottom: 1px solid #ccc;
            background-color: #eeeeee;
            padding: 0.5rem 1rem;
        }

        .box-body {
            padding: 0.5rem 1rem;
        }

        .input-field {
            margin-bottom: 1rem;
        }

        .input-field > label {
            display: block;
            margin-bottom: 0.5rem;
        }

        input, textarea {
            width: 100%;
            border: 1px solid #ccc;
            padding: 0.5rem;
        }

        table {
            border: 1px solid #ccc;
            border-collapse: collapse;
        }

        th {
            background-color: #eee;
        }

        th, td {
            border: 1px solid #ccc;
            padding: 0.5rem;
        }
    </style>
</head>
<body>

    <div class="box">

        <div class="box-header">설정</div>
        <div class="box-body">
            <form method="post" action="/debug/query.do">
                <div class="input-field">
                    <label for="datasource_bean_id">데이터소스 빈 아이디</label>
                    <input type="text" id="datasource_bean_id" name="datasource_bean_id" value="${datasource_bean_id}">
                </div>
                <div class="input-field">
                    <label for="query">질의문</label>
                    <textarea id="query" name="query" rows="10">${query}</textarea>
                </div>
                <input type="submit" value="질의문 실행">
            </form>
        </div>

    </div>

    <div class="box">
        <div class="box-header">결과</div>
        <div class="box-body">${result}</div>
    </div>

</body>
</html>