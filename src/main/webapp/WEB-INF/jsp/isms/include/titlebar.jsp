<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="sb" uri="/config/tlds/custom-taglib" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<p class="history"><a href="<c:out value="${pagePrtUrl}"/>"><c:out value="${pagePrtTitle}"/></a><span>&gt;</span><c:out value="${pageTitle}"/></p>
	<div class="conttitle"><c:out value="${pageTitle}"/></div>