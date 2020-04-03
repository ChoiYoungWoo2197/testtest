package com.uwo.isms.domain;

import java.util.Date;

public class CntrLogVO {
	private String ucl_log_key;
	
	private String ucl_log_gbn;	
	/** uwo_ctr_mtr 마스터키 **/
	private String ucl_ctr_key;
	/** 심사주기코드 **/
	private String ucl_bcy_cod;
	/** 표준구분 **/
	private String ucl_ctr_gbn;
	/** 통제항목코드 **/
	private String ucl_ctr_cod;
	/** 레벨구분 **/
	private String ucl_lvl_gbn;
	/** 레벨code **/
	private String ucl_lvl_cod;
	/** 1레벨 **/
	private String ucl_1lv_cod;
	/** 1레벨설명 **/
	private String ucl_1lv_nam;
	/** 2레벨 **/
	private String ucl_2lv_cod;
	/** 2레벨설명 **/
	private String ucl_2lv_nam;
	/** 2레벨 세부설명 **/
	private String ucl_2lv_dtl;
	/** 3레벨 **/
	private String ucl_3lv_cod;
	/** 3레벨설명 **/
	private String ucl_3lv_nam;
	/** 3레벨세부설명 **/
	private String ucl_3lv_dtl;
	/** 4레벨 **/
	private String  ucl_4lv_cod;
	/** 4레벨설명 **/
	private String ucl_4lv_nam;
	/** 5레벨 **/
	private String ucl_5lv_cod;
	/** 5레벨설명 **/
	private String ucl_5lv_nam;
	/** 관련지침 **/
	private String ucl_rlt_gid;
	/** 운영현황 **/ 
	private String ucl_ctr_opr;
	/** 사업부(통제항목) **/
	private String ucl_div_cod;
	/** 사용유무 **/
	private String ucl_lvu_yn;
	/** 1레벨 사용유무 **/
	private String ucl_1lu_yn;
	/** 2레벨 사용유무 **/
	private String ucl_2lu_yn;
	/** 2레벨 사용유무 **/
	private String ucl_3lu_yn;
	/** 4레벨 사용유무 **/
	private String ucl_4lu_yn;
	/** 5레벨 사용유무 **/
	private String ucl_5lu_yn;
	/** 비고 **/
	private String ucl_etc;
	/** GOAL NO **/
	private String ucl_gol_no;
	/** 합산점수 **/
	private String ucl_sum_pnt;
	/** 생성자ID **/
	private String ucl_rgt_id;
	/** 최종수정자 ID **/
	private String ucl_upd_id;
	/** 삭제여부 **/
	private String ucl_1ld_yn;
	
	private String ucl_2ld_yn;
	
	private String ucl_3ld_yn;
	
	private String ucl_4ld_yn;
	
	private String ucl_5ld_yn;
	
	private Date ucl_rgt_mdh;
	
	private Date ucl_upd_mdh;

	public String getUcl_log_key() {
		return ucl_log_key;
	}

	public void setUcl_log_key(String ucl_log_key) {
		this.ucl_log_key = ucl_log_key;
	}

	public String getUcl_log_gbn() {
		return ucl_log_gbn;
	}

	public void setUcl_log_gbn(String ucl_log_gbn) {
		this.ucl_log_gbn = ucl_log_gbn;
	}

	public String getUcl_ctr_key() {
		return ucl_ctr_key;
	}

	public void setUcl_ctr_key(String ucl_ctr_key) {
		this.ucl_ctr_key = ucl_ctr_key;
	}

	public String getUcl_bcy_cod() {
		return ucl_bcy_cod;
	}

	public void setUcl_bcy_cod(String ucl_bcy_cod) {
		this.ucl_bcy_cod = ucl_bcy_cod;
	}

	public String getUcl_ctr_gbn() {
		return ucl_ctr_gbn;
	}

	public void setUcl_ctr_gbn(String ucl_ctr_gbn) {
		this.ucl_ctr_gbn = ucl_ctr_gbn;
	}

	public String getUcl_ctr_cod() {
		return ucl_ctr_cod;
	}

	public void setUcl_ctr_cod(String ucl_ctr_cod) {
		this.ucl_ctr_cod = ucl_ctr_cod;
	}

	public String getUcl_lvl_gbn() {
		return ucl_lvl_gbn;
	}

	public void setUcl_lvl_gbn(String ucl_lvl_gbn) {
		this.ucl_lvl_gbn = ucl_lvl_gbn;
	}

	public String getUcl_lvl_cod() {
		return ucl_lvl_cod;
	}

	public void setUcl_lvl_cod(String ucl_lvl_cod) {
		this.ucl_lvl_cod = ucl_lvl_cod;
	}

	public String getUcl_1lv_cod() {
		return ucl_1lv_cod;
	}

	public void setUcl_1lv_cod(String ucl_1lv_cod) {
		this.ucl_1lv_cod = ucl_1lv_cod;
	}

	public String getUcl_1lv_nam() {
		return ucl_1lv_nam;
	}

	public void setUcl_1lv_nam(String ucl_1lv_nam) {
		this.ucl_1lv_nam = ucl_1lv_nam;
	}

	public String getUcl_2lv_cod() {
		return ucl_2lv_cod;
	}

	public void setUcl_2lv_cod(String ucl_2lv_cod) {
		this.ucl_2lv_cod = ucl_2lv_cod;
	}

	public String getUcl_2lv_nam() {
		return ucl_2lv_nam;
	}

	public void setUcl_2lv_nam(String ucl_2lv_nam) {
		this.ucl_2lv_nam = ucl_2lv_nam;
	}

	public String getUcl_2lv_dtl() {
		return ucl_2lv_dtl;
	}

	public void setUcl_2lv_dtl(String ucl_2lv_dtl) {
		this.ucl_2lv_dtl = ucl_2lv_dtl;
	}

	public String getUcl_3lv_cod() {
		return ucl_3lv_cod;
	}

	public void setUcl_3lv_cod(String ucl_3lv_cod) {
		this.ucl_3lv_cod = ucl_3lv_cod;
	}

	public String getUcl_3lv_nam() {
		return ucl_3lv_nam;
	}

	public void setUcl_3lv_nam(String ucl_3lv_nam) {
		this.ucl_3lv_nam = ucl_3lv_nam;
	}

	public String getUcl_3lv_dtl() {
		return ucl_3lv_dtl;
	}

	public void setUcl_3lv_dtl(String ucl_3lv_dtl) {
		this.ucl_3lv_dtl = ucl_3lv_dtl;
	}

	public String getUcl_4lv_cod() {
		return ucl_4lv_cod;
	}

	public void setUcl_4lv_cod(String ucl_4lv_cod) {
		this.ucl_4lv_cod = ucl_4lv_cod;
	}

	public String getUcl_4lv_nam() {
		return ucl_4lv_nam;
	}

	public void setUcl_4lv_nam(String ucl_4lv_nam) {
		this.ucl_4lv_nam = ucl_4lv_nam;
	}

	public String getUcl_5lv_cod() {
		return ucl_5lv_cod;
	}

	public void setUcl_5lv_cod(String ucl_5lv_cod) {
		this.ucl_5lv_cod = ucl_5lv_cod;
	}

	public String getUcl_5lv_nam() {
		return ucl_5lv_nam;
	}

	public void setUcl_5lv_nam(String ucl_5lv_nam) {
		this.ucl_5lv_nam = ucl_5lv_nam;
	}

	public String getUcl_rlt_gid() {
		return ucl_rlt_gid;
	}

	public void setUcl_rlt_gid(String ucl_rlt_gid) {
		this.ucl_rlt_gid = ucl_rlt_gid;
	}

	public String getUcl_ctr_opr() {
		return ucl_ctr_opr;
	}

	public void setUcl_ctr_opr(String ucl_ctr_opr) {
		this.ucl_ctr_opr = ucl_ctr_opr;
	}

	public String getUcl_div_cod() {
		return ucl_div_cod;
	}

	public void setUcl_div_cod(String ucl_div_cod) {
		this.ucl_div_cod = ucl_div_cod;
	}

	public String getUcl_lvu_yn() {
		return ucl_lvu_yn;
	}

	public void setUcl_lvu_yn(String ucl_lvu_yn) {
		this.ucl_lvu_yn = ucl_lvu_yn;
	}

	public String getUcl_1lu_yn() {
		return ucl_1lu_yn;
	}

	public void setUcl_1lu_yn(String ucl_1lu_yn) {
		this.ucl_1lu_yn = ucl_1lu_yn;
	}

	public String getUcl_2lu_yn() {
		return ucl_2lu_yn;
	}

	public void setUcl_2lu_yn(String ucl_2lu_yn) {
		this.ucl_2lu_yn = ucl_2lu_yn;
	}

	public String getUcl_3lu_yn() {
		return ucl_3lu_yn;
	}

	public void setUcl_3lu_yn(String ucl_3lu_yn) {
		this.ucl_3lu_yn = ucl_3lu_yn;
	}

	public String getUcl_4lu_yn() {
		return ucl_4lu_yn;
	}

	public void setUcl_4lu_yn(String ucl_4lu_yn) {
		this.ucl_4lu_yn = ucl_4lu_yn;
	}

	public String getUcl_5lu_yn() {
		return ucl_5lu_yn;
	}

	public void setUcl_5lu_yn(String ucl_5lu_yn) {
		this.ucl_5lu_yn = ucl_5lu_yn;
	}

	public String getUcl_etc() {
		return ucl_etc;
	}

	public void setUcl_etc(String ucl_etc) {
		this.ucl_etc = ucl_etc;
	}

	public String getUcl_gol_no() {
		return ucl_gol_no;
	}

	public void setUcl_gol_no(String ucl_gol_no) {
		this.ucl_gol_no = ucl_gol_no;
	}

	public String getUcl_sum_pnt() {
		return ucl_sum_pnt;
	}

	public void setUcl_sum_pnt(String ucl_sum_pnt) {
		this.ucl_sum_pnt = ucl_sum_pnt;
	}

	public String getUcl_rgt_id() {
		return ucl_rgt_id;
	}

	public void setUcl_rgt_id(String ucl_rgt_id) {
		this.ucl_rgt_id = ucl_rgt_id;
	}

	public String getUcl_upd_id() {
		return ucl_upd_id;
	}

	public void setUcl_upd_id(String ucl_upd_id) {
		this.ucl_upd_id = ucl_upd_id;
	}

	public String getUcl_1ld_yn() {
		return ucl_1ld_yn;
	}

	public void setUcl_1ld_yn(String ucl_1ld_yn) {
		this.ucl_1ld_yn = ucl_1ld_yn;
	}

	public String getUcl_2ld_yn() {
		return ucl_2ld_yn;
	}

	public void setUcl_2ld_yn(String ucl_2ld_yn) {
		this.ucl_2ld_yn = ucl_2ld_yn;
	}

	public String getUcl_3ld_yn() {
		return ucl_3ld_yn;
	}

	public void setUcl_3ld_yn(String ucl_3ld_yn) {
		this.ucl_3ld_yn = ucl_3ld_yn;
	}

	public String getUcl_4ld_yn() {
		return ucl_4ld_yn;
	}

	public void setUcl_4ld_yn(String ucl_4ld_yn) {
		this.ucl_4ld_yn = ucl_4ld_yn;
	}

	public String getUcl_5ld_yn() {
		return ucl_5ld_yn;
	}

	public void setUcl_5ld_yn(String ucl_5ld_yn) {
		this.ucl_5ld_yn = ucl_5ld_yn;
	}

	public Date getUcl_rgt_mdh() {
		return ucl_rgt_mdh;
	}

	public void setUcl_rgt_mdh(Date ucl_rgt_mdh) {
		this.ucl_rgt_mdh = ucl_rgt_mdh;
	}

	public Date getUcl_upd_mdh() {
		return ucl_upd_mdh;
	}

	public void setUcl_upd_mdh(Date ucl_upd_mdh) {
		this.ucl_upd_mdh = ucl_upd_mdh;
	}
	
	
}