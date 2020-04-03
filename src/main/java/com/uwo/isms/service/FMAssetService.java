package com.uwo.isms.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.uwo.isms.domain.SearchVO;
import com.uwo.isms.domain.RiskVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface FMAssetService {

	public List<?> fm_asset001_list(SearchVO searchVO);

	public int fm_asset001_cnt(SearchVO searchVO);

	public EgovMap fm_asset001_assetCodeInfo(String code);

	//public void fm_asset001_assetCode_insert(Map<String, Object> map);

	public void fm_asset001_assetCode_insert(Map<String, Object> map);

	public void fm_asset001_assetCode_update(Map<String, Object> map);

	public void fm_asset001_assetCodeLog_insert(Map<String, Object> map);

	public List<?> fm_asset002_list(SearchVO searchVO);

	public List<?> fm_asset002_assetlistBelongG(SearchVO searchVO);

	public List<?> fm_asset002_assetlistNotBelongG(SearchVO searchVO);

	public RiskVO fm_asset002_assetGroupInfo(String groupKey);

	public void fm_asset002_assetGroup_insert(RiskVO riskVO);

	public void fm_asset002_assetGroup_update(RiskVO riskVO);

	public void fm_asset002_groupAsset_update(SearchVO searchVO);

	public void fm_asset002_riskGroup_insert(RiskVO riskVO);

	public void fm_asset002_riskGroup_update(RiskVO riskVO);

	public List<?> fm_asset008_list(Map<String, String> map);

	public void fm_asset008_add(Map<String, String> map);

	public void fm_asset008_mod(Map<String, String> map);

	public EgovMap fm_asset008_info(Map<String, String> map);

	public List<?> getHistory(SearchVO searchVO);

	public int getHistory_count(SearchVO searchVO);

	public List<?> fmAsset001_excelUpload(HttpServletRequest req) throws Exception;

	public EgovMap fmAsset001_excelSave(HttpServletRequest req);

	public void fm_asset003_update(Map<String, Object> map);

}

