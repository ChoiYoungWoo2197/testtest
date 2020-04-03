/**
 ***********************************
 * @source CommonConfig.java
 * @date 2014. 10. 15.
 * @project isms3_godohwa
 * @description 
 ***********************************
 */
package com.uwo.isms.common;

public class CommonConfig {
	
	public final static String SES_USER_OBJECT_KEY = "SES_USER_OBJECT";
	
	/** login success **/
	public final static String SES_LOGIN_S = "1";
	
	/** login fail **/
	public final static String SES_LOGIN_F = "2";
	
	/** seq **/
	public final static String SES_USER_KEY = "SES_USER_KEY";
	
	/** ID **/
	public final static String SES_USER_ID_KEY = "SES_USER_ID";
	/** NAME **/
	public final static String SES_USER_NAME_KEY = "SES_USER_NAME";
	
	/** 사업부 CODE **/
	public final static String SES_DIVISION_KEY = "SES_DIVISION_CODE";
	/** 사업부 NAME **/
	public final static String SES_DIVISION_NAME_KEY = "SES_DIVISION_NAME";
	
	/** 서비스 CODE **/
	public final static String SES_SERVICE_KEY = "SES_SERVICE_CODE";
	/** 서비스 CODE **/
	public final static String SES_SERVICE_NAME_KEY = "SES_SERVICE_NAME";
	
	/** 부서 CODE **/
	public final static String SES_DEPT_KEY = "SES_DEPT_CODE";
	/** 부서 NAME **/
	public final static String SES_DEPT_NAME_KEY = "SES_DEPT_NAME";
	
	/** 사용자권한(담당) **/
	public final static String SES_USER_AUTH_KEY = "SES_USER_AUTH";
	
	/** 사용자권한(결재) **/
	public final static String SES_USER_APV_AUTH_KEY = "SES_USER_APV_AUTH";
	
	/** 회사정보 **/
	public final static String SES_USER_COMPANY_KEY = "SES_USER_COMPANY";
	
	/** 회사 사업부 여부 0:없음, 1:있음 **/
	public final static String SES_USER_COMPANY_DIV_KEY = "SES_USER_COMPANY_DIV";
	
	/** 회사 서비스 여부 0:없음, 1:있음 **/
	public final static String SES_USER_COMPANY_SVC_KEY = "SES_USER_COMPANY_SVC";
	
	/** 회사 부서 여부 0:없음, 1:있음 **/
	public final static String SES_USER_COMPANY_DEP_KEY = "SES_USER_COMPANY_DEP";
	
	/** 대주기 시퀀스 **/
	public final static String SES_MAN_CYL_KEY = "SES_MAN_CYL";
	
	/** 대주기 시작일 **/
	public final static String SES_MAN_STD_KEY = "SES_MAN_STD";
	
	/** 대주기 종료일 **/
	public final static String SES_MAN_END_KEY = "SES_MAN_END";
	
	public final static String SES_PWD_CHG_YN = "SES_PWD_CHG_YN";
	
	/** 본부, 그룹,담당, 팀 CODE **/
	public final static String SES_ST_ORG = "SES_ST_ORG";
	public final static String SES_HQ_ORG = "SES_HQ_ORG";
	public final static String SES_GP_ORG = "SES_GP_ORG";
}
