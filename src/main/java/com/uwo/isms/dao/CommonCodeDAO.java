/**
 ***********************************
 * @source CommonCodeDAO.java
 * @date 2014. 10. 20.
 * @project isms3_godohwa
 * @description 
 ***********************************
 */
package com.uwo.isms.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.uwo.isms.domain.SearchVO;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Repository("commonCodeDAO")
public class CommonCodeDAO extends EgovAbstractDAO {

	/**
	 * @return
	 */
	public List<?>  getCommonCodeList(String code) {
		return list("commonCode.QR_COMMONCODE_LIST", code);
		//return list("commonCode.QR_SVCCODE_LIST", code);
	}
	
	public List<?>  getSvcCodeList(String code) {
		return list("commonCode.QR_SVCCODE_LIST", code);
		//return list("commonCode.QR_SVCCODE_LIST", code);
	}
	
	public List<?>  getDepCodeList() {
		return list("commonCode.QR_DEPCODE_LIST_B");
		//return list("commonCode.QR_SVCCODE_LIST", code);
	}
	
	/**
	 * @return
	 */
	public List<?>  getNonCodeList(String manCyl) {
		return list("commonCode.QR_TERMNonCYCLE_LIST", manCyl);
	}
	
	
	public List<?> getSampleCode() {
		List<?> list = list("commonCode.QR_SAMPLECODE_LIST");	//
		return list;
	}

	public List<?> getDivCode() {
		List<?> list = list("commonCode.QR_DIVCODE_LIST");	//
		return list;
	}

	public List<?> getSvcCode(String stnd) {
		List<?> list = list("commonCode.QR_SVCCODE_LIST", stnd);	//
		return list;
	}
	
	public List<?> getServiceAuthList(Map<String, String> map) {
		List<?> list = list("commonCode.QR_SVCCODE_AUTH_LIST", map);	//
		return list;
	}
	
	public List<?> getServiceAuthList_N(Map<String, String> map) {
		List<?> list = list("commonCode.QR_SVCCODE_AUTH_LIST_N", map);	//
		return list;
	}
	
	public List<?> getServiceAuthList_CAT(Map<String, String> map) {
		List<?> list = list("commonCode.QR_SVCCODE_AUTH_LIST_CAT", map);	//
		return list;
	}
	
	public List<?> getDeptAuthList(Map<String, String> map) {
		List<?> list = list("commonCode.QR_DEPCODE_AUTH_LIST", map);	//
		return list;
	}
	
	public List<?> getAuhList() {
		return list("commonCode.QR_AUTH_LIST");	//
	}

	/**
	 * @param searchVO
	 * @return
	 */
	public List<?> getSelectBox(SearchVO searchVO) {
		String sqlUrl = searchVO.getSqlUrl();
		
		List<?> list = list(sqlUrl, searchVO.getCode());	//
		return list;
	}

	public List<?> getManCycleSelectBoxCode(String company) {
		List<?> list = list("commonCode.QR_MANCYCLE_LIST", company);
		return list;
	}

	public String getMailName(String key) {
		return (String) select("commonCode.QR_MAILNAME_SELECT", key);
	}

	public List<?> getWorkerList(Map<String, String> map) {
		return list("commonCode.QR_WORKER_LIST", map);
	}
	
	public List<?> getOprusrList(Map<String, String> map) {
		return list("commonCode.QR_OPRUSR_LIST", map);
	}
	
	public List<?> getWorkerAllList(Map<String, String> map) {
		return list("commonCode.QR_WORKER_ALL_LIST", map);
	}

	public List<?> getAssCatList() {
		return list("commonCode.QR_ASSCAT_LIST");
	}
	
	public List<?> getAssCatList(Map<String, String> map) {
		return list("commonCode.QR_ASSCAT_TYPE_LIST", map);
	}
	
	public List<?> getVlbList() {
		return list("commonCode.QR_VLB_LIST");
	}
	
	public List<?> getVlbImpList() { 
		return list("commonCode.QR_VLB_IMP_LIST");
	}
	
	public List<?> getAssGbnList() {
		return list("commonCode.QR_ASSGBN_LIST");
	}

	public List<?> getSenarioList(Map<String, String> map) {
		return list("commonCode.QR_SENARIO_LIST");
	}
	
	public List<?> getRiskCocList(String assCat) {
		return list("commonCode.QR_RISKCOC_LIST", assCat);
	}
	
	public List<?> getDeptList(Map<String, String> map) {
		return list("commonCode.QR_DEPT_LIST", map);
	}
	
	public List<?> getDeptCodeList(String udmDepLvl) {
		return list("commonCode.QR_DEPT_CODE_LIST", udmDepLvl);
	}
	
	public List<?> getDepartList(Map<String, String> map) {
		return list("commonCode.QR_DEPART_LIST", map);
	}
	
	public List<?> getOprtList(Map<String, String> map) {
		return list("commonCode.QR_OPRT_LIST", map);
	}
	
	public List<?> getDoaList() {
		return list("commonCode.QR_DOA_LIST", null);
	}
	
	public EgovMap getDoa() {
		return (EgovMap)select("commonCode.QR_DOA", null ); 
	}
}
