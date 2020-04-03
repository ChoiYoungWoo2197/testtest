package com.uwo.isms.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * UWO_TRC_WRK VO
 * @author User
 *
 */
public class WorkVO {

	/** 마스터키 **/
	private String utw_wrk_key;

	/** 심사주기 **/
	private String utw_prd_cod;

	/** 주기코드 **/
	private String utw_trm_cod;
	private String trm_cod_nam;

	/** 매핑양식코드 **/
	private String utw_trc_key;
	private String utd_doc_nam;
	private String utd_doc_etc;
	private String utd_app_lev;
	private String utd_doc_cnt;
	private String utd_dtr_gbn;

	/** 수행내용 **/
	private String utw_wrk_dtl;

	/** 업무상태코드 **/
	private String utw_wrk_sta;
	private String utw_com_sta;
	private int utw_wrk_prg;
	private String utw_wrk_sta_origin;

	/** 업무상태 detail code **/
	private String wrk_sta_dtl;

	/** 업무수행일 **/
	private String utw_wrk_dat;

	/** 업무시작일 **/
	private String utw_str_dat;

	/** 업무종료일 **/
	private String utw_end_dat;

	/** 업무지정종료일 **/
	private String utw_cmp_dat;

	/** 사업부코드 **/
	private String utw_div_cod;

	/** 서비스코드 **/
	private String utw_svc_cod;
	private String utw_svc_nam;

	/** 부서코드 **/
	private String utw_dep_cod;

	/** 삭제유무 **/
	private String utw_del_yn;

	/** 담당자key **/
	private String utw_wrk_id;

	/** 담당자 이름 **/
	private String utw_wrk_nam;

	/** 담당자 부서명 **/
	private String utw_dep_nam;

	/** 대무자key **/
	private String utw_agn_id;
	private String agn_app_key;

	/** 대무자 이름 **/
	private String utw_agn_nam;

	/** 대무자 승인여부 **/
	private String utw_agn_yn;

	/** 대무자 승인 상태 detail **/
	private String agn_sta_dtl;

	/** 업무 결재자key **/
	private String utw_apr_id;

	/** 생성자key **/
	private String utw_rgt_id;

	/** 수정자key **/
	private String utw_upt_id;

	/** 결재테이블 key **/
	private String uam_app_key;
	/** 업무코드 **/
	private String uam_wrk_key;
	/** 결재구분 **/
	private String uam_app_gbn;
	/** 결재단계 **/
	private String uam_app_lev;
	/** 결재상태 **/
	private String uam_sta_cod;
	private String uam_trm_cod;
	/** 결재요청자 **/
	private String uam_req_id;

	// 2018-09-17 s,
	private String uam_req_nam;

	private String uam_req_mdh;
	/** 1차 결재 처리자 **/
	private String uam_cf1_id;
	private String uam_cf1_nam;
	private String uam_cf1_mdh;
	private String uam_cf1_etc;
	/** 2차 결재 처리자 **/
	private String uam_cf2_id;
	private String uam_cf2_nam;
	private String uam_cf2_mdh;
	private String uam_cf2_etc;
	/** 생성자 ID **/
	private String uam_rgt_id;
	/** 수정자 ID **/
	private String uam_upt_id;
	/** 삭제여부 **/
	private String uam_del_yn;

	/** 결재방법 **/
	private String utd_apr_yn;
	private String apr_yn_nam;

	// 이관 결재자
	private String agn_cf1_id;
	private String agn_cf1_nam;
	private String agn_cf1_mdh;
	private String agn_cf1_etc;

	// 2017-05-19
	private String agn_cf2_id;
	private String agn_cf2_nam;
	private String agn_cf2_mdh;
	private String agn_cf2_etc;

	// 2018-10-12 s,
	private String utw_rgt_mdh;
	private String utw_upt_mdh;

	public String getAgn_cf1_id() {
		return agn_cf1_id;
	}

	public void setAgn_cf1_id(String agn_cf1_id) {
		this.agn_cf1_id = agn_cf1_id;
	}

	public String getAgn_cf1_nam() {
		return agn_cf1_nam;
	}

	public void setAgn_cf1_nam(String agn_cf1_nam) {
		this.agn_cf1_nam = agn_cf1_nam;
	}

	public String getAgn_cf1_mdh() {
		return agn_cf1_mdh;
	}

	public void setAgn_cf1_mdh(String agn_cf1_mdh) {
		this.agn_cf1_mdh = agn_cf1_mdh;
	}

	public String getAgn_cf1_etc() {
		return agn_cf1_etc;
	}

	public void setAgn_cf1_etc(String agn_cf1_etc) {
		this.agn_cf1_etc = agn_cf1_etc;
	}

	public String getUtw_dep_nam() {
		return utw_dep_nam;
	}

	public void setUtw_dep_nam(String utw_dep_nam) {
		this.utw_dep_nam = utw_dep_nam;
	}

	public String getUtw_svc_nam() {
		return utw_svc_nam;
	}

	public void setUtw_svc_nam(String utw_svc_nam) {
		this.utw_svc_nam = utw_svc_nam;
	}

	public String getUtd_doc_etc() {
		return utd_doc_etc;
	}

	public void setUtd_doc_etc(String utd_doc_etc) {
		this.utd_doc_etc = utd_doc_etc;
	}

	public String getUtw_wrk_key() {
		return utw_wrk_key;
	}

	public void setUtw_wrk_key(String utw_wrk_key) {
		this.utw_wrk_key = utw_wrk_key;
	}

	public String getUtw_prd_cod() {
		return utw_prd_cod;
	}

	public void setUtw_prd_cod(String utw_prd_cod) {
		this.utw_prd_cod = utw_prd_cod;
	}

	public String getUtw_trm_cod() {
		return utw_trm_cod;
	}

	public void setUtw_trm_cod(String utw_trm_cod) {
		this.utw_trm_cod = utw_trm_cod;
	}

	public String getUtw_wrk_dtl() {
		return utw_wrk_dtl;
	}

	public void setUtw_wrk_dtl(String utw_wrk_dtl) {
		this.utw_wrk_dtl = utw_wrk_dtl;
	}

	public String getUtw_wrk_sta() {
		return utw_wrk_sta;
	}

	public void setUtw_wrk_sta(String utw_wrk_sta) {
		this.utw_wrk_sta = utw_wrk_sta;
	}

	public String getUtw_com_sta() {
		return utw_com_sta;
	}

	public void setUtw_com_sta(String utw_com_sta) {
		this.utw_com_sta = utw_com_sta;
	}

	public String getUtw_wrk_dat() {
		return utw_wrk_dat;
	}

	public void setUtw_wrk_dat(String utw_wrk_dat) {
		this.utw_wrk_dat = utw_wrk_dat;
	}

	public String getUtw_str_dat() {
		return utw_str_dat;
	}

	public void setUtw_str_dat(String utw_str_dat) {
		this.utw_str_dat = utw_str_dat;
	}

	public String getUtw_end_dat() {
		return utw_end_dat;
	}

	public void setUtw_end_dat(String utw_end_dat) {
		this.utw_end_dat = utw_end_dat;
	}

	public String getUtw_cmp_dat() {
		return utw_cmp_dat;
	}

	public void setUtw_cmp_dat(String utw_cmp_dat) {
		this.utw_cmp_dat = utw_cmp_dat;
	}

	public String getUtw_svc_cod() {
		return utw_svc_cod;
	}

	public void setUtw_svc_cod(String utw_svc_cod) {
		this.utw_svc_cod = utw_svc_cod;
	}

	public String getUtw_del_yn() {
		return utw_del_yn;
	}

	public void setUtw_del_yn(String utw_del_yn) {
		this.utw_del_yn = utw_del_yn;
	}

	public String getUtw_wrk_id() {
		return utw_wrk_id;
	}

	public void setUtw_wrk_id(String utw_wrk_id) {
		this.utw_wrk_id = utw_wrk_id;
	}

	public String getUtw_agn_id() {
		return utw_agn_id;
	}

	public void setUtw_agn_id(String utw_agn_id) {
		this.utw_agn_id = utw_agn_id;
	}

	public String getUtw_agn_yn() {
		return utw_agn_yn;
	}

	public void setUtw_agn_yn(String utw_agn_yn) {
		this.utw_agn_yn = utw_agn_yn;
	}

	public String getUtw_apr_id() {
		return utw_apr_id;
	}

	public void setUtw_apr_id(String utw_apr_id) {
		this.utw_apr_id = utw_apr_id;
	}

	public String getUtw_rgt_id() {
		return utw_rgt_id;
	}

	public void setUtw_rgt_id(String utw_rgt_id) {
		this.utw_rgt_id = utw_rgt_id;
	}

	public String getUtw_upt_id() {
		return utw_upt_id;
	}

	public void setUtw_upt_id(String utw_upt_id) {
		this.utw_upt_id = utw_upt_id;
	}

	public String getUtw_div_cod() {
		return utw_div_cod;
	}

	public void setUtw_div_cod(String utw_div_cod) {
		this.utw_div_cod = utw_div_cod;
	}

	public String getUtw_dep_cod() {
		return utw_dep_cod;
	}

	public void setUtw_dep_cod(String utw_dep_cod) {
		this.utw_dep_cod = utw_dep_cod;
	}

	public String getUtw_trc_key() {
		return utw_trc_key;
	}

	public void setUtw_trc_key(String utw_trc_key) {
		this.utw_trc_key = utw_trc_key;
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

	public String getUam_cf2_id() {
		return uam_cf2_id;
	}

	public void setUam_cf2_id(String uam_cf2_id) {
		this.uam_cf2_id = uam_cf2_id;
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

	public String getUtw_agn_nam() {
		return utw_agn_nam;
	}

	public void setUtw_agn_nam(String utw_agn_nam) {
		this.utw_agn_nam = utw_agn_nam;
	}

	public String getAgn_sta_dtl() {
		return agn_sta_dtl;
	}

	public void setAgn_sta_dtl(String agn_sta_dtl) {
		this.agn_sta_dtl = agn_sta_dtl;
	}

	public String getUam_trm_cod() {
		return uam_trm_cod;
	}

	public void setUam_trm_cod(String uam_trm_cod) {
		this.uam_trm_cod = uam_trm_cod;
	}

	public String getUam_req_mdh() {
		return uam_req_mdh;
	}

	public void setUam_req_mdh(String uam_req_mdh) {
		this.uam_req_mdh = uam_req_mdh;
	}

	public String getUam_cf1_nam() {
		return uam_cf1_nam;
	}

	public void setUam_cf1_nam(String uam_cf1_nam) {
		this.uam_cf1_nam = uam_cf1_nam;
	}

	public String getUam_cf1_mdh() {
		return uam_cf1_mdh;
	}

	public void setUam_cf1_mdh(String uam_cf1_mdh) {
		this.uam_cf1_mdh = uam_cf1_mdh;
	}

	public String getUam_cf1_etc() {
		return uam_cf1_etc;
	}

	public void setUam_cf1_etc(String uam_cf1_etc) {
		this.uam_cf1_etc = uam_cf1_etc;
	}

	public String getUam_cf2_nam() {
		return uam_cf2_nam;
	}

	public void setUam_cf2_nam(String uam_cf2_nam) {
		this.uam_cf2_nam = uam_cf2_nam;
	}

	public String getUam_cf2_mdh() {
		return uam_cf2_mdh;
	}

	public void setUam_cf2_mdh(String uam_cf2_mdh) {
		this.uam_cf2_mdh = uam_cf2_mdh;
	}

	public String getUam_cf2_etc() {
		return uam_cf2_etc;
	}

	public void setUam_cf2_etc(String uam_cf2_etc) {
		this.uam_cf2_etc = uam_cf2_etc;
	}

	public String getUtd_apr_yn() {
		return utd_apr_yn;
	}

	public void setUtd_apr_yn(String utd_apr_yn) {
		this.utd_apr_yn = utd_apr_yn;
	}

	public String getApr_yn_nam() {
		return apr_yn_nam;
	}

	public void setApr_yn_nam(String apr_yn_nam) {
		this.apr_yn_nam = apr_yn_nam;
	}

	public String getTrm_cod_nam() {
		return trm_cod_nam;
	}

	public void setTrm_cod_nam(String trm_cod_nam) {
		this.trm_cod_nam = trm_cod_nam;
	}

	public String getWrk_sta_dtl() {
		return wrk_sta_dtl;
	}

	public void setWrk_sta_dtl(String wrk_sta_dtl) {
		this.wrk_sta_dtl = wrk_sta_dtl;
	}

	public String getUtw_wrk_nam() {
		return utw_wrk_nam;
	}

	public void setUtw_wrk_nam(String utw_wrk_nam) {
		this.utw_wrk_nam = utw_wrk_nam;
	}

	public String getUtd_doc_nam() {
		return utd_doc_nam;
	}

	public void setUtd_doc_nam(String utd_doc_nam) {
		this.utd_doc_nam = utd_doc_nam;
	}

	public String getUtd_app_lev() {
		return utd_app_lev;
	}

	public void setUtd_app_lev(String utd_app_lev) {
		this.utd_app_lev = utd_app_lev;
	}

	public String getUam_app_key() {
		return uam_app_key;
	}

	public void setUam_app_key(String uam_app_key) {
		this.uam_app_key = uam_app_key;
	}

	public String getAgn_app_key() {
		return agn_app_key;
	}

	public void setAgn_app_key(String agn_app_key) {
		this.agn_app_key = agn_app_key;
	}

	public String getUtd_doc_cnt() {
		return utd_doc_cnt;
	}

	public void setUtd_doc_cnt(String utd_doc_cnt) {
		this.utd_doc_cnt = utd_doc_cnt;
	}

	public String getUtd_dtr_gbn() {
		return utd_dtr_gbn;
	}

	public void setUtd_dtr_gbn(String utd_dtr_gbn) {
		this.utd_dtr_gbn = utd_dtr_gbn;
	}

	public String getAgn_cf2_id() {
		return agn_cf2_id;
	}

	public void setAgn_cf2_id(String agn_cf2_id) {
		this.agn_cf2_id = agn_cf2_id;
	}

	public String getAgn_cf2_nam() {
		return agn_cf2_nam;
	}

	public void setAgn_cf2_nam(String agn_cf2_nam) {
		this.agn_cf2_nam = agn_cf2_nam;
	}

	public String getAgn_cf2_mdh() {
		return agn_cf2_mdh;
	}

	public void setAgn_cf2_mdh(String agn_cf2_mdh) {
		this.agn_cf2_mdh = agn_cf2_mdh;
	}

	public String getAgn_cf2_etc() {
		return agn_cf2_etc;
	}

	public void setAgn_cf2_etc(String agn_cf2_etc) {
		this.agn_cf2_etc = agn_cf2_etc;
	}

	public String getUam_req_nam() {
		return uam_req_nam;
	}

	public void setUam_req_nam(String uam_req_nam) {
		this.uam_req_nam = uam_req_nam;
	}

	public String getUtw_rgt_mdh() {
		return utw_rgt_mdh;
	}

	public void setUtw_rgt_mdh(String utw_rgt_mdh) {
		this.utw_rgt_mdh = utw_rgt_mdh;
	}

	public String getUtw_upt_mdh() {
		return utw_upt_mdh;
	}

	public void setUtw_upt_mdh(String utw_upt_mdh) {
		this.utw_upt_mdh = utw_upt_mdh;
	}

	public int getUtw_wrk_prg() {
		return utw_wrk_prg;
	}

	public void setUtw_wrk_prg(int utw_wrk_prg) {
		this.utw_wrk_prg = utw_wrk_prg;
	}

	public String getUtw_wrk_sta_origin() {
		return utw_wrk_sta_origin;
	}

	public void setUtw_wrk_sta_origin(String utw_wrk_sta_origin) {
		this.utw_wrk_sta_origin = utw_wrk_sta_origin;
	}

	/**
	 * 활동에 대한 업무결과 타입 enum
	 *
	 * value 값은 UWO_TRC_WRK.UTW_WRK_PRG 칼럼 값으로 사용된다.
	 * title 값은 화면에 표시되는 텍스트값으로 사용된다.
	 *
	 * 기존 업무 상태 또는 결과 정보를 담고 있던 UTW_WRK_STA, UTW_COM_STA 칼럼과는 독립적으로 사용되는 값이다.
	 * UTW_WRK_STA 와 UTW_COM_STA 칼럼을 이용해보려 하였지만 구조적으로 값 사용에 대한 논리가 없어 포기하였다.
	 *
	 * utw_wrk_prg_column_value == WorkVO.statusType.Y.getValue())
	 * WorkVO.statusType.fromValue(utw_wrk_prg_column_value) == WorkVO.statusType.Y
	 * 와 같은 방식으로 사용한다.
	 */
	public enum statusType {

		Y(100, "Y"),
		UP(70, "UP"),
		LP(30, "LP"),
		N(0, "N"),
		NA(-1 , "N/A");

		private static final Map<Integer, WorkVO.statusType> valueToTypeMap = new HashMap<Integer, WorkVO.statusType>();
		private static final Map<String, WorkVO.statusType> titleToTypeMap = new HashMap<String, WorkVO.statusType>();

		static {
			for (WorkVO.statusType workStatus : WorkVO.statusType.values()) {
				valueToTypeMap.put(workStatus.getValue(), workStatus);
				titleToTypeMap.put(workStatus.getTitle(), workStatus);
			}
		}

		private int value;
		private String title;

		statusType(int value, String title) {
			this.value = value;
			this.title = title;
		}

		public int getValue() {
			return value;
		}

		public String getTitle() {
			return title;
		}

		/**
		 * 정수 값으로 업무결과 타입 Enum 을 받는다.
		 *
		 * @param value 타입의 정수 값
		 * @return WorkVO.statusType Enum
		 */
		public static WorkVO.statusType fromValue(int value) {
			return valueToTypeMap.get(value);
		}

		public static WorkVO.statusType fromTitle(String title) { return titleToTypeMap.get(title); }

		/**
		 * 정수 값이 100 인 경우만 완료
		 * @return boolean
		 */
		public boolean isCompleted() {
			return value == 100;
		}
	}

}
