package com.uwo.isms.domain;


import java.io.Serializable;

public class LoginVO implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -8274004534207618049L;

	/** seq **/
	private String uum_usr_key;

	/** 아이디 */
	private String id;

	/** 이름 */
	private String uum_usr_nam;

	/** 이메일주소 */
	private String uum_mal_ads;

	/** 비밀번호 */
	private String password;

	/** 사용자 권한(담당) */
	private String uum_ath_gbn;
	/** 사용자 권한(결재) */
	private String uum_apv_gbn;

	/** 조직(사업부)CODE */
	private String uum_div_cod;
	/** 조직(사업부)명 */
	private String division;

	/** 조직(서비스)CODE */
	private String uum_svc_cod;
	/** 조직(서비스)명 */
	private String service;

	/** 조직(부서)ID */
	private String uum_dep_cod;
	/** 조직(부서)명 */
	private String dept;

	/** 사용여부 **/
	private String uum_use_yn;

	/** 회사명 **/
	private String company;

	/** 로그인 구분(1:성공/2:실패) **/
	private String ulm_log_gbn;

	/** 로그인 ip **/
	private String ulm_lin_ip;

	/** 대주기 시퀀스 **/
	private String umm_man_cyl;
	/** 대주기 시작일 **/
	private String umm_std_dat;
	/** 대주기 종료일 **/
	private String umm_end_dat;
	/** 대주기(이전/다음) 구분 **/
	private String gubun;

	/** 권한키 **/
	private String uum_auh_key;


	/** 권한등급 구분 **/
	private String uat_auh_gbn;

	/** 비밀번호 변경 여부 **/
	private String pwd_chg_yn;
	private String uum_chg_mdh;

	/** 본부, 그룹,담당, 팀 CODE **/
	private String stOrg;
	private String hqOrg;
	private String gpOrg;

	// SSO 접근유무
	private Boolean isSso = false;

	// 2017-10-11
	private String uat_def_mnu;

	/**
	 * id attribute 를 리턴한다.
	 * @return String
	 */
	public String getId() {
		return id;
	}
	/**
	 * id attribute 값을 설정한다.
	 * @param id String
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * uum_usr_nam attribute 를 리턴한다.
	 * @return String
	 */
	public String getUum_usr_nam() {
		return uum_usr_nam;
	}
	/**
	 * uum_usr_nam attribute 값을 설정한다.
	 * @param uum_usr_nam String
	 */
	public void setUum_usr_nam(String uum_usr_nam) {
		this.uum_usr_nam = uum_usr_nam;
	}
	/**
	 * uum_div_cod attribute 를 리턴한다.
	 * @return String
	 */
	public String getUum_div_cod() {
		return uum_div_cod;
	}
	/**
	 * uum_div_cod attribute 값을 설정한다.
	 * @param uum_div_cod String
	 */
	public void setUum_div_cod(String uum_div_cod) {
		this.uum_div_cod = uum_div_cod;
	}
	/**
	 * uum_mal_ads attribute 를 리턴한다.
	 * @return String
	 */
	public String getUum_mal_ads() {
		return uum_mal_ads;
	}
	/**
	 * uum_mal_ads attribute 값을 설정한다.
	 * @param uum_mal_ads String
	 */
	public void setUum_mal_ads(String uum_mal_ads) {
		this.uum_mal_ads = uum_mal_ads;
	}
	/**
	 * password attribute 를 리턴한다.
	 * @return String
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * password attribute 값을 설정한다.
	 * @param password String
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * uum_svc_cod attribute 를 리턴한다.
	 * @return String
	 */
	public String getUum_svc_cod() {
		return uum_svc_cod;
	}
	/**
	 * uum_svc_cod attribute 값을 설정한다.
	 * @param uum_svc_cod String
	 */
	public void setUum_svc_cod(String uum_svc_cod) {
		this.uum_svc_cod = uum_svc_cod;
	}
	/**
	 * service attribute 를 리턴한다.
	 * @return String
	 */
	public String getService() {
		return service;
	}
	/**
	 * service attribute 값을 설정한다.
	 * @param service String
	 */
	public void setService(String service) {
		this.service = service;
	}
	/**
	 * uum_dep_cod attribute 를 리턴한다.
	 * @return String
	 */
	public String getUum_dep_cod() {
		return uum_dep_cod;
	}
	/**
	 * uum_dep_cod attribute 값을 설정한다.
	 * @param uum_dep_cod String
	 */
	public void setUum_dep_cod(String uum_dep_cod) {
		this.uum_dep_cod = uum_dep_cod;
	}
	/**
	 * division attribute 를 리턴한다.
	 * @return String
	 */
	public String getDivision() {
		return division;
	}
	/**
	 * division attribute 값을 설정한다.
	 * @param division String
	 */
	public void setDivision(String division) {
		this.division = division;
	}
	/**
	 * @return the dept
	 */
	public String getDept() {
		return dept;
	}
	/**
	 * @param dept the dept to set
	 */
	public void setDept(String dept) {
		this.dept = dept;
	}
	/**
	 * @return the uum_usr_key
	 */
	public String getuum_usr_key() {
		return uum_usr_key;
	}
	/**
	 * @param uum_usr_key the uum_usr_key to set
	 */
	public void setuum_usr_key(String uum_usr_key) {
		this.uum_usr_key = uum_usr_key;
	}
	/**
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}
	/**
	 * @param company the company to set
	 */
	public void setCompany(String company) {
		this.company = company;
	}
	/**
	 * @return the uum_ath_gbn
	 */
	public String getUum_ath_gbn() {
		return uum_ath_gbn;
	}
	/**
	 * @param uum_ath_gbn the uum_ath_gbn to set
	 */
	public void setUum_ath_gbn(String uum_ath_gbn) {
		this.uum_ath_gbn = uum_ath_gbn;
	}
	/**
	 * @return the uum_apv_gbn
	 */
	public String getUum_apv_gbn() {
		return uum_apv_gbn;
	}
	/**
	 * @param uum_apv_gbn the uum_apv_gbn to set
	 */
	public void setUum_apv_gbn(String uum_apv_gbn) {
		this.uum_apv_gbn = uum_apv_gbn;
	}
	/**
	 * @return the umm_man_cyl
	 */
	public String getUmm_man_cyl() {
		return umm_man_cyl;
	}
	/**
	 * @param umm_man_cyl the umm_man_cyl to set
	 */
	public void setUmm_man_cyl(String umm_man_cyl) {
		this.umm_man_cyl = umm_man_cyl;
	}
	/**
	 * @return the umm_std_dat
	 */
	public String getUmm_std_dat() {
		return umm_std_dat;
	}
	/**
	 * @param umm_std_dat the umm_std_dat to set
	 */
	public void setUmm_std_dat(String umm_std_dat) {
		this.umm_std_dat = umm_std_dat;
	}
	/**
	 * @return the umm_end_dat
	 */
	public String getUmm_end_dat() {
		return umm_end_dat;
	}
	/**
	 * @param umm_end_dat the umm_end_dat to set
	 */
	public void setUmm_end_dat(String umm_end_dat) {
		this.umm_end_dat = umm_end_dat;
	}
	public String getGubun() {
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	public String getUlm_log_gbn() {
		return ulm_log_gbn;
	}
	public void setUlm_log_gbn(String ulm_log_gbn) {
		this.ulm_log_gbn = ulm_log_gbn;
	}
	public String getUlm_lin_ip() {
		return ulm_lin_ip;
	}
	public void setUlm_lin_ip(String ulm_lin_ip) {
		this.ulm_lin_ip = ulm_lin_ip;
	}
	public String getUum_use_yn() {
		return uum_use_yn;
	}
	public void setUum_use_yn(String uum_use_yn) {
		this.uum_use_yn = uum_use_yn;
	}
	public String getUum_auh_key() {
		return uum_auh_key;
	}
	public void setUum_auh_key(String uum_auh_key) {
		this.uum_auh_key = uum_auh_key;
	}
	public String getUat_auh_gbn() {
		return uat_auh_gbn;
	}
	public void setUat_auh_gbn(String uat_auh_gbn) {
		this.uat_auh_gbn = uat_auh_gbn;
	}
	public String getPwd_chg_yn() {
		return pwd_chg_yn;
	}
	public void setPwd_chg_yn(String pwd_chg_yn) {
		this.pwd_chg_yn = pwd_chg_yn;
	}
	public String getStOrg() {
		return stOrg;
	}
	public void setStOrg(String stOrg) {
		this.stOrg = stOrg;
	}
	public String getHqOrg() {
		return hqOrg;
	}
	public void setHqOrg(String hqOrg) {
		this.hqOrg = hqOrg;
	}
	public String getGpOrg() {
		return gpOrg;
	}
	public void setGpOrg(String gpOrg) {
		this.gpOrg = gpOrg;
	}

	public Boolean getIsSso() {
		return isSso;
	}
	public void setIsSso(Boolean isSso) {
		this.isSso = isSso;
	}

	public String getUum_chg_mdh() {
		return uum_chg_mdh;
	}
	public void setUum_chg_mdh(String uum_chg_mdh) {
		this.uum_chg_mdh = uum_chg_mdh;
	}
	public String getUat_def_mnu() {
		return uat_def_mnu;
	}
	public void setUat_def_mnu(String uat_def_mnu) {
		this.uat_def_mnu = uat_def_mnu;
	}
}
