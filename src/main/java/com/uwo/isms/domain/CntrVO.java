package com.uwo.isms.domain;

import java.util.Date;

public class CntrVO {
	
	/** uwo_ctr_mtr 마스터키 **/
	private String ucm_ctr_key;
	/** 심사주기코드 **/
	private String ucm_bcy_cod;
	/** 표준구분 **/
	private String ucm_ctr_gbn;
	/** 통제항목코드 **/
	private String ucm_ctr_cod;
	/** 레벨구분 **/
	private String ucm_lvl_gbn;
	/** 레벨code **/
	private String ucm_lvl_cod;
	/** 1레벨 **/
	private String ucm_1lv_cod;
	/** 1레벨설명 **/
	private String ucm_1lv_nam;
	/** 2레벨 **/
	private String ucm_2lv_cod;
	/** 2레벨설명 **/
	private String ucm_2lv_nam;
	/** 2레벨 세부설명 **/
	private String ucm_2lv_dtl;
	/** 3레벨 **/
	private String ucm_3lv_cod;
	/** 3레벨설명 **/
	private String ucm_3lv_nam;
	/** 3레벨세부설명 **/
	private String ucm_3lv_dtl;
	/** 4레벨 **/
	private String  ucm_4lv_cod;
	/** 4레벨설명 **/
	private String ucm_4lv_nam;
	/** 5레벨 **/
	private String ucm_5lv_cod;
	/** 5레벨설명 **/
	private String ucm_5lv_nam;
	/** 관련지침 **/
	private String ucm_rlt_gid;
	/** 운영현황 **/ 
	private String ucm_ctr_opr;
	/** 사업부(통제항목) **/
	private String ucm_div_cod;
	/** 사용유무 **/
	private String ucm_lvu_yn;
	/** 1레벨 사용유무 **/
	private String ucm_1lu_yn;
	/** 2레벨 사용유무 **/
	private String ucm_2lu_yn;
	/** 2레벨 사용유무 **/
	private String ucm_3lu_yn;
	/** 4레벨 사용유무 **/
	private String ucm_4lu_yn;
	/** 5레벨 사용유무 **/
	private String ucm_5lu_yn;
	/** 비고 **/
	private String ucm_etc;
	/** GOAL NO **/
	private String ucm_gol_no;
	/** 합산점수 **/
	private String ucm_sum_pnt;
	/** 생성자ID **/
	private String ucm_rgt_id;
	/** 최종수정자 ID **/
	private String ucm_upd_id;
	/** 삭제여부 **/
	private String ucm_1ld_yn;
	
	private String ucm_2ld_yn;
	
	private String ucm_3ld_yn;
	
	private String ucm_4ld_yn;
	
	private String ucm_5ld_yn;
	
	private Date ucm_rgt_mdh;
	
	private Date ucm_upd_mdh;
	
	/** uwo_trc_ctr 통제항목양식 매핑키 **/
	private String utc_map_key;
	
	/** 심사주기 코드 **/
	private String utc_byc_cod;
	
	/** 통제항목코드 **/
	private String utc_ctr_key;
	
	/** 매핑양식코드 **/
	private String utc_trc_key;
	
	/** 비고사항 **/
	private String utc_ctr_cmt;
	
	/** 생성자ID **/
	private String utc_rgt_id;
	
	/** 최종수정자ID **/
	private String utc_upt_id;

	public String getUcm_ctr_key() {
		return ucm_ctr_key;
	}

	public void setUcm_ctr_key(String ucm_ctr_key) {
		this.ucm_ctr_key = ucm_ctr_key;
	}

	public String getUcm_bcy_cod() {
		return ucm_bcy_cod;
	}

	public void setUcm_bcy_cod(String ucm_bcy_cod) {
		this.ucm_bcy_cod = ucm_bcy_cod;
	}

	public String getUcm_ctr_gbn() {
		return ucm_ctr_gbn;
	}

	public void setUcm_ctr_gbn(String ucm_ctr_gbn) {
		this.ucm_ctr_gbn = ucm_ctr_gbn;
	}

	public String getUcm_ctr_cod() {
		return ucm_ctr_cod;
	}

	public void setUcm_ctr_cod(String ucm_ctr_cod) {
		this.ucm_ctr_cod = ucm_ctr_cod;
	}

	public String getUcm_lvl_gbn() {
		return ucm_lvl_gbn;
	}

	public void setUcm_lvl_gbn(String ucm_lvl_gbn) {
		this.ucm_lvl_gbn = ucm_lvl_gbn;
	}

	public String getUcm_lvl_cod() {
		return ucm_lvl_cod;
	}

	public void setUcm_lvl_cod(String ucm_lvl_cod) {
		this.ucm_lvl_cod = ucm_lvl_cod;
	}

	public String getUcm_1lv_cod() {
		return ucm_1lv_cod;
	}

	public void setUcm_1lv_cod(String ucm_1lv_cod) {
		this.ucm_1lv_cod = ucm_1lv_cod;
	}

	public String getUcm_1lv_nam() {
		return ucm_1lv_nam;
	}

	public void setUcm_1lv_nam(String ucm_1lv_nam) {
		this.ucm_1lv_nam = ucm_1lv_nam;
	}

	public String getUcm_2lv_cod() {
		return ucm_2lv_cod;
	}

	public void setUcm_2lv_cod(String ucm_2lv_cod) {
		this.ucm_2lv_cod = ucm_2lv_cod;
	}

	public String getUcm_2lv_nam() {
		return ucm_2lv_nam;
	}

	public void setUcm_2lv_nam(String ucm_2lv_nam) {
		this.ucm_2lv_nam = ucm_2lv_nam;
	}

	public String getUcm_2lv_dtl() {
		return ucm_2lv_dtl;
	}

	public void setUcm_2lv_dtl(String ucm_2lv_dtl) {
		this.ucm_2lv_dtl = ucm_2lv_dtl;
	}

	public String getUcm_3lv_cod() {
		return ucm_3lv_cod;
	}

	public void setUcm_3lv_cod(String ucm_3lv_cod) {
		this.ucm_3lv_cod = ucm_3lv_cod;
	}

	public String getUcm_3lv_nam() {
		return ucm_3lv_nam;
	}

	public void setUcm_3lv_nam(String ucm_3lv_nam) {
		this.ucm_3lv_nam = ucm_3lv_nam;
	}

	public String getUcm_3lv_dtl() {
		return ucm_3lv_dtl;
	}

	public void setUcm_3lv_dtl(String ucm_3lv_dtl) {
		this.ucm_3lv_dtl = ucm_3lv_dtl;
	}

	public String getUcm_4lv_cod() {
		return ucm_4lv_cod;
	}

	public void setUcm_4lv_cod(String ucm_4lv_cod) {
		this.ucm_4lv_cod = ucm_4lv_cod;
	}

	public String getUcm_4lv_nam() {
		return ucm_4lv_nam;
	}

	public void setUcm_4lv_nam(String ucm_4lv_nam) {
		this.ucm_4lv_nam = ucm_4lv_nam;
	}

	public String getUcm_5lv_cod() {
		return ucm_5lv_cod;
	}

	public void setUcm_5lv_cod(String ucm_5lv_cod) {
		this.ucm_5lv_cod = ucm_5lv_cod;
	}

	public String getUcm_5lv_nam() {
		return ucm_5lv_nam;
	}

	public void setUcm_5lv_nam(String ucm_5lv_nam) {
		this.ucm_5lv_nam = ucm_5lv_nam;
	}

	public String getUcm_rlt_gid() {
		return ucm_rlt_gid;
	}

	public void setUcm_rlt_gid(String ucm_rlt_gid) {
		this.ucm_rlt_gid = ucm_rlt_gid;
	}

	public String getUcm_ctr_opr() {
		return ucm_ctr_opr;
	}

	public void setUcm_ctr_opr(String ucm_ctr_opr) {
		this.ucm_ctr_opr = ucm_ctr_opr;
	}

	public String getUcm_div_cod() {
		return ucm_div_cod;
	}

	public void setUcm_div_cod(String ucm_div_cod) {
		this.ucm_div_cod = ucm_div_cod;
	}

	public String getUcm_lvu_yn() {
		return ucm_lvu_yn;
	}

	public void setUcm_lvu_yn(String ucm_lvu_yn) {
		this.ucm_lvu_yn = ucm_lvu_yn;
	}

	public String getUcm_1lu_yn() {
		return ucm_1lu_yn;
	}

	public void setUcm_1lu_yn(String ucm_1lu_yn) {
		this.ucm_1lu_yn = ucm_1lu_yn;
	}

	public String getUcm_2lu_yn() {
		return ucm_2lu_yn;
	}

	public void setUcm_2lu_yn(String ucm_2lu_yn) {
		this.ucm_2lu_yn = ucm_2lu_yn;
	}

	public String getUcm_3lu_yn() {
		return ucm_3lu_yn;
	}

	public void setUcm_3lu_yn(String ucm_3lu_yn) {
		this.ucm_3lu_yn = ucm_3lu_yn;
	}

	public String getUcm_4lu_yn() {
		return ucm_4lu_yn;
	}

	public void setUcm_4lu_yn(String ucm_4lu_yn) {
		this.ucm_4lu_yn = ucm_4lu_yn;
	}

	public String getUcm_5lu_yn() {
		return ucm_5lu_yn;
	}

	public void setUcm_5lu_yn(String ucm_5lu_yn) {
		this.ucm_5lu_yn = ucm_5lu_yn;
	}

	public String getUcm_etc() {
		return ucm_etc;
	}

	public void setUcm_etc(String ucm_etc) {
		this.ucm_etc = ucm_etc;
	}

	public String getUcm_gol_no() {
		return ucm_gol_no;
	}

	public void setUcm_gol_no(String ucm_gol_no) {
		this.ucm_gol_no = ucm_gol_no;
	}

	public String getUcm_sum_pnt() {
		return ucm_sum_pnt;
	}

	public void setUcm_sum_pnt(String ucm_sum_pnt) {
		this.ucm_sum_pnt = ucm_sum_pnt;
	}

	public String getUcm_rgt_id() {
		return ucm_rgt_id;
	}

	public void setUcm_rgt_id(String ucm_rgt_id) {
		this.ucm_rgt_id = ucm_rgt_id;
	}

	public String getUcm_upd_id() {
		return ucm_upd_id;
	}

	public void setUcm_upd_id(String ucm_upd_id) {
		this.ucm_upd_id = ucm_upd_id;
	}

	public String getUcm_1ld_yn() {
		return ucm_1ld_yn;
	}

	public void setUcm_1ld_yn(String ucm_1ld_yn) {
		this.ucm_1ld_yn = ucm_1ld_yn;
	}

	public String getUcm_2ld_yn() {
		return ucm_2ld_yn;
	}

	public void setUcm_2ld_yn(String ucm_2ld_yn) {
		this.ucm_2ld_yn = ucm_2ld_yn;
	}

	public String getUcm_3ld_yn() {
		return ucm_3ld_yn;
	}

	public void setUcm_3ld_yn(String ucm_3ld_yn) {
		this.ucm_3ld_yn = ucm_3ld_yn;
	}

	public String getUcm_4ld_yn() {
		return ucm_4ld_yn;
	}

	public void setUcm_4ld_yn(String ucm_4ld_yn) {
		this.ucm_4ld_yn = ucm_4ld_yn;
	}

	public String getUcm_5ld_yn() {
		return ucm_5ld_yn;
	}

	public void setUcm_5ld_yn(String ucm_5ld_yn) {
		this.ucm_5ld_yn = ucm_5ld_yn;
	}

	public Date getUcm_rgt_mdh() {
		return ucm_rgt_mdh;
	}

	public void setUcm_rgt_mdh(Date ucm_rgt_mdh) {
		this.ucm_rgt_mdh = ucm_rgt_mdh;
	}

	public Date getUcm_upd_mdh() {
		return ucm_upd_mdh;
	}

	public void setUcm_upd_mdh(Date ucm_upd_mdh) {
		this.ucm_upd_mdh = ucm_upd_mdh;
	}

	public String getUtc_ctr_key() {
		return utc_ctr_key;
	}

	public void setUtc_ctr_key(String utc_ctr_key) {
		this.utc_ctr_key = utc_ctr_key;
	}

	public String getUtc_byc_cod() {
		return utc_byc_cod;
	}

	public void setUtc_byc_cod(String utc_byc_cod) {
		this.utc_byc_cod = utc_byc_cod;
	}

	public String getUtc_map_key() {
		return utc_map_key;
	}

	public void setUtc_map_key(String utc_map_key) {
		this.utc_map_key = utc_map_key;
	}

	public String getUtc_ctr_cmt() {
		return utc_ctr_cmt;
	}

	public void setUtc_ctr_cmt(String utc_ctr_cmt) {
		this.utc_ctr_cmt = utc_ctr_cmt;
	}

	public String getUtc_rgt_id() {
		return utc_rgt_id;
	}

	public void setUtc_rgt_id(String utc_rgt_id) {
		this.utc_rgt_id = utc_rgt_id;
	}

	public String getUtc_upt_id() {
		return utc_upt_id;
	}

	public void setUtc_upt_id(String utc_upt_id) {
		this.utc_upt_id = utc_upt_id;
	}

	public String getUtc_trc_key() {
		return utc_trc_key;
	}

	public void setUtc_trc_key(String utc_trc_key) {
		this.utc_trc_key = utc_trc_key;
	}

	
}
