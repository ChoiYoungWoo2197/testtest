package com.uwo.isms.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.uwo.isms.domain.SearchVO;
import com.uwo.isms.domain.RiskVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface FMRiskmService {

	public List<?> fm_riskm003_list(Map<String, String> map);

	public EgovMap fm_riskm003_info(Map<String, String> map);

	public EgovMap fm_riskm003_rskInfo(Map<String, String> map);

	public List<?> fm_riskm003_rskList(Map<String, String> map);

	public List<?> fm_riskm003_svr_list();

	public void fm_riskm003_rskm_insert(Map<String, String> map);

	public void fm_riskm003_rskm_update(Map<String, String> map);

	public int fm_riskm003_update_ursStaCodS(Map<String, String> map);

	public List<?> fm_riskm004_list(Map<String, Object> map);

	public EgovMap fm_riskm004_info(String usoCockey);

	public void fm_riskm004_insert(Map<String, Object> map);

	public void fm_riskm004_update(Map<String, Object> map);

	public List<?> fm_riskm005_list(Map<String, Object> map);

	public EgovMap fm_riskm005_info(String usmSroKey);

	public void fm_riskm005_insert(Map<String, Object> map);

	public void fm_riskm005_update(Map<String, Object> map);

	//public String fm_riskm003_excel_insert(HttpServletRequest req);

	//public String fm_riskm003_etcExcel_insert(HttpServletRequest req);

	public List<?> fm_riskm003_report(Map<String, String> map);

	public List<?> fm_riskm003_t_report(Map<String, String> map);

	public List<?> fm_riskm003_bcyList(Map<String, String> map);

	public List<?> fm_riskm006_list(Map<String, String> map);

	public int fm_riskm006_cnt(Map<String, String> map);

	public List<?> fm_riskm006_rst_list(Map<String, String> map);

	public EgovMap fm_riskm006_excel_common(String assCod);

	//public List<?> fm_riskm006_excel_xlsx_asset_exists(HttpServletRequest req);

	public String fm_riskm006_excel_exists(HttpServletRequest req);

	public String fm_riskm006_excel_insert(HttpServletRequest req) throws SQLException;

	/*
	 * 2016-09-22
	 * ass_key 로 체크하는 기존 로직
	 * 사용하지 않음
	public String fm_riskm006_excel_insert(HttpServletRequest req) throws SQLException;

	public String fm_riskm006_excel_exists(HttpServletRequest req);
	*/

	public String fm_riskm006_etcExcel_insert(HttpServletRequest req);

	public List<?> fm_riskm007_list(Map<String, String> map);

	public EgovMap fm_riskm007_info(String urvRskKey);

	public void fm_riskm007_insert(Map<String, Object> map);

	public void fm_riskm007_update(Map<String, Object> map);

	public List<?> getCocList(Map<String, String> map);

	public List<?> getUsrList(Map<String, String> map);

	public void fmRiskm003_mngSave(Map<String, String> map);

	public void fmRiskm003_rskReq(Map<String, String> map);

	public void fmRiskm003_rskSave(Map<String, String> map);

	public void fmRiskm003_rskAppr(Map<String, String> map);

	public void fmRiskm003_rskRej(Map<String, String> map);

	public List<?> fm_riskm008_list(Map<String, String> map);

	public List<?> fm_riskm008_dep_list(Map<String, String> map);

	public void fmRiskm008_addRskDepList(List<Map<String, String>> list);

	public void fmRiskm008_delRskDepList(List<Map<String, String>> list);

	public void fm_riskm008_update_rskDepMng(Map<String, String> map);

	public List<?> fm_riskm009_list(Map<String, String> map);

	public List<?> fm_riskm009_rst_list(Map<String, String> map);

	public String fm_riskm009_excel_insert(HttpServletRequest req);

	public List<?> fmRiskm010_list(Map<String, String> map);

	public List<?> fm_riskm010_rskList(Map<String, String> map);

	public EgovMap fm_riskm010_rskInfo(Map<String, String> map);

	public int fm_riskm010_update_urrStaCodS(Map<String, String> map);

	public List<?> fm_riskm010_urrDepList(Map<String, String> map);

	public String fm_riskm010_urrRskKey(Map<String, String> map);

	public EgovMap fm_riskm010_urrDetail(Map<String, String> map);

	public void fmRiskm010_mngSave(Map<String, String> map);

	public void fmRiskm010_rskReq(Map<String, String> map);

	public void fmRiskm010_rskSave(Map<String, String> map);

	public void fmRiskm010_rskAppr(Map<String, String> map);

	public void fmRiskm010_rskRej(Map<String, String> map);

	public List<?> getRst003MainList(Map<String, String> map);

	public List<?> getRst010MainList(Map<String, String> map);
}

