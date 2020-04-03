/**
 ***********************************
 * @source CommonCodeServiceImpl.java
 * @date 2014. 10. 20.
 * @project isms3_godohwa
 * @description
 ***********************************
 */
package com.uwo.isms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.uwo.isms.common.UwoStringUtils;
import com.uwo.isms.dao.CommonCodeDAO;
import com.uwo.isms.domain.SearchVO;
import com.uwo.isms.service.CommonCodeService;
import com.uwo.isms.web.FMSetupController;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service("commonCodeService")
public class CommonCodeServiceImpl implements CommonCodeService {

	Logger log = LogManager.getLogger(FMSetupController.class);

	@Resource(name="commonCodeDAO")
	private CommonCodeDAO commonCodeDAO;

	/* 업무양식 코드 조회
	 * @see com.uwo.isms.service.CommonCodeService#getSampleCode()
	 */

	@Override
	public List<?> getCommonCodeList(String code) {
		return commonCodeDAO.getCommonCodeList(code);
	}

	@Override
	public List<?> getSvcCodeList(String code) {
		return commonCodeDAO.getSvcCodeList(code);
	}

	@Override
	public List<?> getDepCodeList() {
		return commonCodeDAO.getDepCodeList();
	}

	@Override
	public List<?> getNonCodeList(String code) {
		return commonCodeDAO.getNonCodeList(code);
	}

	@Override
	public List<?> getSampleCode() {
		return commonCodeDAO.getSampleCode();
	}

	@Override
	public List<?> getDivCode() {
		return commonCodeDAO.getDivCode();
	}

	@Override
	public List<?> getSvcCode(String stnd) {
		return commonCodeDAO.getSvcCode(stnd);
	}

	@Override
	public List<?> getAuhList() {
		return commonCodeDAO.getAuhList();
	}

	@Override
	public List<?> getSelectBoxCode(SearchVO searchVO) {
		return commonCodeDAO.getSelectBox(searchVO);
	}

	@Override
	public List<?> getManCycleSelectBoxCode(String company) {
		return commonCodeDAO.getManCycleSelectBoxCode(company);
	}

	@Override
	public String getMailName(String key) {
		return commonCodeDAO.getMailName(key);
	}


	@Override
	public List<?> getServiceAuthList(Map<String, String> map) {
		return commonCodeDAO.getServiceAuthList(map);
	}

	@Override
	public List<?> getServiceAuthList_N(Map<String, String> map) {
		return commonCodeDAO.getServiceAuthList_N(map);
	}

	@Override
	public List<?> getServiceAuthList_CAT(Map<String, String> map) {
		return commonCodeDAO.getServiceAuthList_CAT(map);
	}

	@Override
	public List<?> getDeptAuthList(Map<String, String> map) {
		return commonCodeDAO.getDeptAuthList(map);
	}

	@Override
	public List<?> getWorkerList(Map<String, String> map) {
		List<?> list = null;
		if(UwoStringUtils.objToStr(map.get("type")).equals("A")) {
			list = commonCodeDAO.getWorkerAllList(map);
		} else {
			list = commonCodeDAO.getWorkerList(map);
		}
		return list;
	}

	@Override
	public List<?> getOprusrList(Map<String, String> map) {
		List<?> list = null;
			list = commonCodeDAO.getOprusrList(map);
		return list;
	}

	@Override
	public List<?> getAssCatList() {
		return commonCodeDAO.getAssCatList();
	}

	@Override
	public List<?> getAssCatList(Map<String, String> map) {
		return commonCodeDAO.getAssCatList(map);
	}

	/*
	 * 2017-01-18
	 * (non-Javadoc)
	 * @see com.uwo.isms.service.CommonCodeService#getAssCatListA()
	 */
	@Override
	public List<?> getAssCatListA() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("upType", "A");
		return getAssCatList(map);
	}
	@Override
	public List<?> getAssCatListD() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("upType", "D");
		return getAssCatList(map);
	}

	@Override
	public List<?> getVlbList() {
		return commonCodeDAO.getVlbList();
	}

	@Override
	public List<?> getVlbImpList() {
		return commonCodeDAO.getVlbImpList();
	}

	@Override
	public List<?> getAssGbnList() {
		return commonCodeDAO.getAssGbnList();
	}

	@Override
	public List<?> getSenarioList(Map<String, String> map) {
		return commonCodeDAO.getSenarioList(map);
	}

	@Override
	public List<?> getRiskCocList(String assCat) {
		return commonCodeDAO.getRiskCocList(assCat);
	}

	@Override
	public List<?> getDeptList(Map<String, String> map) {
		return commonCodeDAO.getDeptList(map);
	}

	@Override
	public List<?> getDeptCodeList(String udmDepLvl) {
		return commonCodeDAO.getDeptCodeList(udmDepLvl);
	}

	@Override
	public List<?> getDepartList(Map<String, String> map) {
		return commonCodeDAO.getDepartList(map);
	}

	@Override
	public List<?> getOprtList(Map<String, String> map) {
		return commonCodeDAO.getOprtList(map);
	}

	@Override
	public List<?> getDoaList() {
		return commonCodeDAO.getDoaList();
	}

	@Override
	public EgovMap getDoa() {
		return commonCodeDAO.getDoa();
	}
}
