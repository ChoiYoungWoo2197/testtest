/**
 ***********************************
 * @source LoginServiceImpl.java
 * @date 2014. 10. 14.
 * @project isms3
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

import com.uwo.isms.common.Constants;
import com.uwo.isms.common.Crypto;
import com.uwo.isms.dao.ExcelDAO;
import com.uwo.isms.dao.LoginDAO;
import com.uwo.isms.domain.LoginVO;
import com.uwo.isms.domain.SearchVO;
import com.uwo.isms.domain.UserVO;
import com.uwo.isms.service.ExcelService;
import com.uwo.isms.service.LoginService;
import com.uwo.isms.web.FMSetupController;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service("excelService")
public class ExcelServiceImpl implements ExcelService {

	Logger log = LogManager.getLogger(FMSetupController.class);

	@Resource(name="excelDAO")
    private ExcelDAO excelDAO;

	@Override
	public List<?> mwork008_excel(SearchVO searchVO) {
		return excelDAO.mwork008_excel(searchVO);
	}

	@Override
	public List<?> getNoWorkList(SearchVO searchVO) {
		return excelDAO.getNoWorkList(searchVO);
	}

	@Override
	public List<?> getComWorkList(SearchVO searchVO) {
		return excelDAO.getComWorkList(searchVO);
	}

	@Override
	public List<?> getWorkList(SearchVO searchVO) {
		return excelDAO.getWorkList(searchVO);
	}

	@Override
	public List<?> getFlowMList(String mtrKey) {
		return excelDAO.getFlowMList(mtrKey);
	}

	@Override
	public List<?> getFlowDList(String mtrKey) {
		return excelDAO.getFlowDList(mtrKey);
	}

	@Override
	public List<?> getAllList() {
		return excelDAO.getAllList();
	}

	@Override
	public List<?> getCycleWorkList(Map sMap) {
		return excelDAO.getCycleWorkList(sMap);
	}

	@Override
	public List<?> getYmdList(String compareDate) {
		return excelDAO.getYmdList(compareDate);
	}

	@Override
	public List<?> comps005_excel(SearchVO searchVO) {
		return excelDAO.comps005_excel(searchVO);
	}

	@Override
	public List<?> riskm005_excel(SearchVO searchVO) {
		return excelDAO.riskm005_excel(searchVO);
	}

	@Override
	public List<?> asset001_excel(SearchVO searchVO) {
		return excelDAO.asset001_excel(searchVO);
	}

	@Override
	public List<?> asset001_sample_excel(Map<String, String> map) {
		map.put("uacUseYn", Constants.USE_Y);
		return excelDAO.asset001_sample_excel(map);
	}

	@Override
	public List<?> getStdWorkList(SearchVO searchVO) {
		return excelDAO.getStdWorkList(searchVO);
	}

	@Override
	public List<?> getUserWorkList(SearchVO searchVO) {
		return excelDAO.getUserWorkList(searchVO);
	}

	@Override
	public List<?> getUserDetailWorkList(SearchVO searchVO) {
		return excelDAO.getUserDetailWorkList(searchVO);
	}

	@Override
	public List<?> setup010_excel(SearchVO searchVO) {
		return excelDAO.setup010_excel(searchVO);
	}

	@Override
	public List<?> setup011_excel(SearchVO searchVO) {
		return excelDAO.setup011_excel(searchVO);
	}

	@Override
	public List<?> mwork019_excel(SearchVO searchVO) {
		return excelDAO.mwork019_excel(searchVO);
	}

	@Override
	public List<?> comps004_excel(SearchVO searchVO) {
		return excelDAO.comps004_excel(searchVO);
	}

	@Override
	public List<?> asset006_excel(SearchVO searchVO) {
		return excelDAO.asset006_excel(searchVO);
	}

	@Override
	public List<?> inspt001_excel(SearchVO searchVO) {
		return excelDAO.inspt001_excel(searchVO);
	}

	@Override
	public Map<String, Object> riskm003_excel(SearchVO searchVO) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("sheet1List", excelDAO.riskm003_upload_excel(searchVO));
		resultMap.put("sheet2List", excelDAO.riskm003_asset_excel(searchVO));


		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("bcyCod", searchVO.getManCyl());
		resultMap.put("sheet3List", excelDAO.riskm003_excel(paramMap));

		return resultMap;
	}

	@Override
	public Map<String, Object> riskm003_etc_excel(Map<String, String> map) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("sheet2List", excelDAO.riskm003_etc_excel(map));
		resultMap.put("sheet3List", excelDAO.riskm003_sro_excel(map));
		return resultMap;
	}

	@Override
	public Map<String, Object> riskm006_excel(Map<String, String> map) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("sheet1List", excelDAO.riskm006_checkTarget_excel(map));
		resultMap.put("vlbList", excelDAO.riskm006_riskvlb_list(map));
		resultMap.put("groupList", excelDAO.riskm006_riskgroup_excel(map));
		resultMap.put("assList", excelDAO.riskm006_ass_list(map));
		//resultMap.put("sheet2List", excelDAO.riskm006_asset_excel(searchVO));
		return resultMap;
	}

	@Override
	public Map<String, Object> riskm006_etc_excel(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> riskm006_excel_list_down(Map<String, String> map) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("sheet1List", excelDAO.riskm006_checkTarget_excel(map));
//		resultMap.put("vlbList", excelDAO.riskm006_riskvlb_list(map));
		resultMap.put("groupList", excelDAO.riskm006_riskgroup_excel(map));
//		resultMap.put("assList", excelDAO.riskm006_ass_list(map));
		resultMap.put("assCat", map.get("assCat"));
		resultMap.put("assGbn", map.get("assGbn"));
		resultMap.put("manCyl", map.get("manCyl"));
		resultMap.put("downType", map.get("downType"));

		Map<String, String> paramMap = new HashMap<String, String>();
		return resultMap;
	}

	@Override
	public Map<String, Object> riskm003_report_down(Map<String, String> map) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("rskTypeList", excelDAO.riskm003_report_type(map));
		resultMap.put("rskList", excelDAO.riskm003_report_down(map));
		Map<String, String> paramMap = new HashMap<String, String>();
		return resultMap;
	}

	@Override
	public String riskm003_svr_name(Map<String, String> map) {
		// TODO Auto-generated method stub
		return excelDAO.riskm003_svr_name(map);
	}


	@Override
	public Map<String, Object> riskm009_excel(Map<String, String> map) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("sheet1List", excelDAO.riskm009_checkTarget_excel(map));
		resultMap.put("svrList", excelDAO.riskm009_svr_list(map));
		resultMap.put("vlbList", excelDAO.riskm009_riskvlb_list(map));
		return resultMap;
	}

	@Override
	public Map<String, Object> riskm009_excel_list_down(Map<String, String> map) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("sheet1List", excelDAO.riskm009_checkTarget_excel(map));
		resultMap.put("vlbList", excelDAO.riskm009_riskvlb_list(map));
		resultMap.put("groupList", excelDAO.riskm009_riskgroup_excel(map));
//		resultMap.put("assList", excelDAO.riskm006_ass_list(map));
		resultMap.put("manCyl", map.get("manCyl"));
		resultMap.put("downType", map.get("downType"));

		Map<String, String> paramMap = new HashMap<String, String>();
		return resultMap;
	}

	/**
	 * 2018-05-10 s, 계정 정보
	 * @param searchVO
	 * @return
	 */
	@Override
	public List<?> setup007_excel(SearchVO searchVO) {
		@SuppressWarnings("unchecked")
		List<EgovMap> list = (List<EgovMap>) excelDAO.setup007_excel(searchVO);
		for (EgovMap map: list) {

	 		if (map.get("uumMalAds") != null && map.get("uumMalAds") != "") {
	 			String uumMalAds = Crypto.decAes256(map.get("uumMalAds").toString());
	 			map.put("uumMalAds", uumMalAds);
	 		}
	 		if (map.get("uumCelNum") != null && map.get("uumCelNum") != "") {
	 			String uumCelNum = Crypto.decAes256(map.get("uumCelNum").toString());
	 			map.put("uumCelNum", uumCelNum);
	 		}
	 		if (map.get("uumTelNum") != null && map.get("uumTelNum") != "") {
				String uumTelNum = Crypto.decAes256(map.get("uumTelNum").toString());
				map.put("uumTelNum", uumTelNum);
	 		}
		}
		return list;
	}

}
