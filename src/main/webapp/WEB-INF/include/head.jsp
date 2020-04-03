<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<%@ page import="com.uwo.isms.common.CommonConfig" %>
<%
	String SES_USER_KEY		= (String) request.getSession().getAttribute(CommonConfig.SES_USER_KEY);
	String SES_USER_ID		= (String) request.getSession().getAttribute(CommonConfig.SES_USER_ID_KEY);
	String SES_USER_NAME	= (String) request.getSession().getAttribute(CommonConfig.SES_USER_NAME_KEY);
	// 사업부 구분
	String SES_DIVISION_CD	= (String) request.getSession().getAttribute(CommonConfig.SES_DIVISION_KEY);
	String SES_DIVISION_NAME= (String) request.getSession().getAttribute(CommonConfig.SES_DIVISION_NAME_KEY);
	// 서비스 구분
	String SES_SERVICE_CD	= (String) request.getSession().getAttribute(CommonConfig.SES_SERVICE_KEY);
	String SES_SERVICE_NAME	= (String) request.getSession().getAttribute(CommonConfig.SES_SERVICE_NAME_KEY);
	// 부서 구분
	String SES_DEPT_CD		= (String) request.getSession().getAttribute(CommonConfig.SES_DEPT_KEY);
	String SES_DEPT_NAME	= (String) request.getSession().getAttribute(CommonConfig.SES_DEPT_NAME_KEY);
	// 사용자권한(담당)
	String SES_USER_AUTH	= (String) request.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);
	// 사용자권한(결재)
	String SES_USER_APV_AUTH= (String) request.getSession().getAttribute(CommonConfig.SES_USER_APV_AUTH_KEY);
	// 회사
	String SES_USER_COMPANY	= (String) request.getSession().getAttribute(CommonConfig.SES_USER_COMPANY_KEY);
	// 회사_사업부여부
	String SES_USER_COMPANY_DIV	= (String) request.getSession().getAttribute(CommonConfig.SES_USER_COMPANY_DIV_KEY);
	// 회사_서비스여부
	String SES_USER_COMPANY_SVC	= (String) request.getSession().getAttribute(CommonConfig.SES_USER_COMPANY_SVC_KEY);
	// 회사_부서여부
	String SES_USER_COMPANY_DEP	= (String) request.getSession().getAttribute(CommonConfig.SES_USER_COMPANY_DEP_KEY);
	
	// 대주기 시퀀스
	String SES_MAN_CYL	= (String) request.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY);
	// 대주기 시작일
	String SES_MAN_STD	= (String) request.getSession().getAttribute(CommonConfig.SES_MAN_STD_KEY);
	// 대주기 종료일
	String SES_MAN_END	= (String) request.getSession().getAttribute(CommonConfig.SES_MAN_END_KEY);
%>