package com.uwo.isms.domain;

/**
 * UWO_APP_MTR VO
 * @author User
 *
 */
public class ApprMapVO {

	/** 마스터키 **/
	private String uam_app_key;
	/** 업무코드 **/
	private String uam_wrk_key;
	/** 결재구분 **/
	private String uam_app_gbn;
	/** 결재단계 **/
	private String uam_app_lev;
	/** 결재상태 **/
	private String uam_sta_cod;
	/** 결재내용 **/
	private String uam_trm_cod;
	/** 결재요청자 **/
	private String uam_req_id;
	/** 1차 결재 처리자 **/
	private String uam_cf1_id;
	/** 1차 결재 의견 **/
	private String uam_cf1_etc;
	/** 2차 결재 처리자 **/
	private String uam_cf2_id;
	/** 2차 결재 의견 **/
	private String uam_cf2_etc;
	/** 생성자 ID **/
	private String uam_rgt_id;
	/** 수정자 ID **/
	private String uam_upt_id;
	/** 삭제여부 **/
	private String uam_del_yn;
	public String getUam_app_key() {
		return uam_app_key;
	}
	public void setUam_app_key(String uam_app_key) {
		this.uam_app_key = uam_app_key;
	}
	public String getUam_wrk_key() {
		return uam_wrk_key;
	}
	public void setUam_wrk_key(String uam_wrk_key) {
		this.uam_wrk_key = uam_wrk_key;
	}
	public String getUam_app_gbn() {
		return uam_app_gbn;
	}
	public void setUam_app_gbn(String uam_app_gbn) {
		this.uam_app_gbn = uam_app_gbn;
	}
	public String getUam_app_lev() {
		return uam_app_lev;
	}
	public void setUam_app_lev(String uam_app_lev) {
		this.uam_app_lev = uam_app_lev;
	}
	public String getUam_sta_cod() {
		return uam_sta_cod;
	}
	public void setUam_sta_cod(String uam_sta_cod) {
		this.uam_sta_cod = uam_sta_cod;
	}
	public String getUam_trm_cod() {
		return uam_trm_cod;
	}
	public void setUam_trm_cod(String uam_trm_cod) {
		this.uam_trm_cod = uam_trm_cod;
	}
	public String getUam_req_id() {
		return uam_req_id;
	}
	public void setUam_req_id(String uam_req_id) {
		this.uam_req_id = uam_req_id;
	}
	public String getUam_cf1_id() {
		return uam_cf1_id;
	}
	public void setUam_cf1_id(String uam_cf1_id) {
		this.uam_cf1_id = uam_cf1_id;
	}
	public String getUam_cf1_etc() {
		return uam_cf1_etc;
	}
	public void setUam_cf1_etc(String uam_cf1_etc) {
		this.uam_cf1_etc = uam_cf1_etc;
	}
	public String getUam_cf2_id() {
		return uam_cf2_id;
	}
	public void setUam_cf2_id(String uam_cf2_id) {
		this.uam_cf2_id = uam_cf2_id;
	}
	public String getUam_cf2_etc() {
		return uam_cf2_etc;
	}
	public void setUam_cf2_etc(String uam_cf2_etc) {
		this.uam_cf2_etc = uam_cf2_etc;
	}
	public String getUam_rgt_id() {
		return uam_rgt_id;
	}
	public void setUam_rgt_id(String uam_rgt_id) {
		this.uam_rgt_id = uam_rgt_id;
	}
	public String getUam_upt_id() {
		return uam_upt_id;
	}
	public void setUam_upt_id(String uam_upt_id) {
		this.uam_upt_id = uam_upt_id;
	}
	public String getUam_del_yn() {
		return uam_del_yn;
	}
	public void setUam_del_yn(String uam_del_yn) {
		this.uam_del_yn = uam_del_yn;
	}
}
