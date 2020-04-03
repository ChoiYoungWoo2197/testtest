/**
 ***********************************
 * @source LoginDAO.java
 * @date 2014. 10. 14.
 * @project isms3
 * @description
 ***********************************
 */
package com.uwo.isms.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.uwo.isms.domain.LoginVO;
import com.uwo.isms.domain.SearchVO;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;

@Repository("excelDAO")
public class ExcelDAO extends EgovAbstractDAO {

	public List<?> mwork008_excel(SearchVO searchVO) {
		return list("fmMwork.QR_MWORK008_A", searchVO);
	}

	public List<?> getNoWorkList(SearchVO searchVO) {
		return list("fmMwork.QR_MWORK002_B", searchVO);
	}

	public List<?> getComWorkList(SearchVO searchVO) {
		return list("fmMwork.QR_MWORK002_C", searchVO);
	}

	public List<?> getWorkList(SearchVO searchVO) {
		return list("fmMwork.QR_MWORK003_A", searchVO);
	}

	public List<?> getFlowMList(String mtrKey) {
		return list("fmMwork.QR_MWORK017_B",mtrKey);
	}

	public List<?> getFlowDList(String mtrKey) {
		return list("fmMwork.QR_MWORK017_N",mtrKey);
	}

	public List<?> getAllList() {
		return list("fmMwork.QR_MWORK017_O");
	}

	public List<?> getCycleWorkList(Map sMap) {
		return list("fmMwork.QR_MWORK001_C",sMap);
	}

	public List<?> getYmdList(String compareDate) {
		return list("fmSetup.QR_SETUP012_C",compareDate);
	}

	public List<?> comps005_excel(SearchVO searchVO) {
		return list("fmComps.QR_COMPS005_M",searchVO);
	}

	public List<?> riskm005_excel(SearchVO searchVO) {
		return list("fmRiskm.QR_RISKM005_P",searchVO);
	}

	public List<?> asset001_excel(SearchVO searchVO) {
		return list("fmAsset.QR_ASSET001_EXCEL_D",searchVO);
	}

	public List<?> getStdWorkList(SearchVO searchVO) {
		return list("fmMwork.QR_MWORK004_A",searchVO);
	}

	public List<?> getUserWorkList(SearchVO searchVO) {
		return list("fmMwork.QR_MWORK005_A",searchVO);
	}

	public List<?> getUserDetailWorkList(SearchVO searchVO) {
		return list("fmMwork.QR_MWORK005_B",searchVO);
	}

	public List<?> setup010_excel(SearchVO searchVO) {
		return list("fmSetup.QR_SETUP010_A",searchVO);
	}

	public List<?> setup011_excel(SearchVO searchVO) {
		return list("fmSetup.QR_SETUP011_A",searchVO);
	}

	public List<?> mwork019_excel(SearchVO searchVO) {
		return list("fmMwork.QR_MWORK019_A",searchVO);
	}

	public List<?> comps004_excel(SearchVO searchVO) {
		return list("fmComps.QR_COMPS004_A_EXCEL",searchVO);
	}

	public List<?> asset001_sample_excel(Map<String, String> map) {
		return list("fmAsset.QR_ASSET008_LIST", map);
	}

	public List<?> asset006_excel(SearchVO searchVO) {
		return list("fmAsset.QR_ASSET006_A",searchVO);
	}

	public List<?> inspt001_excel(SearchVO searchVO) {
		return list("fmInspt.QR_INSPT001_A",searchVO);
	}

	public List<?> riskm003_upload_excel(SearchVO searchVO) {
		return list("fmAsset.QR_ASSET002_A",searchVO);
	}

	public List<?> riskm003_asset_excel(SearchVO searchVO) {
		return list("fmAsset.QR_ASSET001_F",searchVO);
	}

	public List<?> riskm003_excel(Map<String, String> paramMap) {
		return list("fmRiskm.QR_RISKM004_LIST",paramMap);
	}

	public List<?> riskm003_etc_excel(Map<String, String> map) {
		return list("fmRiskm.QR_RISKM003_RSK_LIST",map);
	}

	public List<Map<String,String>> riskm003_report_down(Map<String, String> map) {
		return (List<Map<String, String>>)list("fmRiskm.QR_RISKM003_REPORT_LIST",map);
	}

	public List<Map<String, String>> riskm003_report_type(Map<String, String> map) {
		return (List<Map<String, String>>)list("fmRiskm.QR_RISKM003_REPORT_TYPE",map);
	}

	public List<?> riskm003_sro_excel(Map<String, String> map) {
		return list("fmRiskm.QR_RISKM005_LIST",map);
	}

	public String riskm003_svr_name(Map<String, String> map) {
		return (String) select("fmRiskm.QR_RISKM003_SVR_NAME", map);
	}

	public List<?> riskm006_checkTarget_excel(Map<String, String> map) {
		return list("fmRiskm.QR_RISKM006_EXCEL_CHECK_TARGET",map);
	}

	public List<?> riskm006_riskgroup_excel(Map<String, String> map) {
		return list("fmRiskm.QR_RISKM006_EXCEL_TARGET_GROUP", map);
	}

	public List<?> riskm006_ass_list(Map<String, String> map) {
		return list("fmRiskm.QR_RISKM006_EXCEL_ASS_LIST", map);
	}

	public List<?> riskm006_riskvlb_list(Map<String, String> map) {
		return list("fmRiskm.QR_RISKM006_RISKVLB_LIST", map);
	}

	public List<?> riskm006_asset_excel(Map<String, String> map) {
		return list("fmRiskm.QR_RISKM006_EXCEL_TARGET_GROUP", map);
	}

	public List<?> riskm006_excel(Map<String, String> paramMap) {
		return list("fmRiskm.QR_RISKM004_LIST",paramMap);
	}

	public List<?> riskm006_etc_excel(Map<String, String> map) {
		return list("fmRiskm.QR_RISKM003_RSK_LIST",map);
	}
	public List<?> riskm006_sro_excel(Map<String, String> map) {
		return list("fmRiskm.QR_RISKM005_LIST",map);
	}

	public List<?> riskm009_checkTarget_excel(Map<String, String> map) {
		return list("fmRiskm.QR_RISKM009_EXCEL_CHECK_TARGET",map);
	}

	public List<?> riskm009_svr_list(Map<String, String> map) {
		return list("fmRiskm.QR_RISKM009_SVR_LIST", map);
	}

	public List<?> riskm009_riskvlb_list(Map<String, String> map) {
		return list("fmRiskm.QR_RISKM009_RISKVLB_LIST", map);
	}

	public List<?> riskm009_riskgroup_excel(Map<String, String> map) {
		return list("fmRiskm.QR_RISKM009_EXCEL_TARGET_GROUP", map);
	}

	public List<Map<String,String>> riskm010_report_down(Map<String, String> map) {
		return (List<Map<String, String>>)list("fmRiskm.QR_RISKM010_REPORT_LIST",map);
	}

	public List<?> setup007_excel(SearchVO searchVO) {
		searchVO.setFirstIndex(0);
		searchVO.setRecordCountPerPage(100000);
		return list("fmSetup.QR_SETUP007_A",searchVO);
	}

}
