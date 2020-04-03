/**
 ***********************************
 * @source LiginService.java
 * @date 2014. 10. 13.
 * @project isms3
 * @description
 ***********************************
 */
package com.uwo.isms.service;

import java.util.List;
import java.util.Map;

import com.uwo.isms.domain.SearchVO;

public interface ExcelService {

	List<?> mwork008_excel(SearchVO searchVO);

	List<?> getNoWorkList(SearchVO searchVO);

	List<?> getComWorkList(SearchVO searchVO);

	List<?> getWorkList(SearchVO searchVO);

	List<?> getFlowMList(String mtrKey);

	List<?> getFlowDList(String mtrKey);

	List<?> getAllList();

	List<?> getCycleWorkList(Map sMap);

	List<?> getYmdList(String compareDate);

	List<?> comps005_excel(SearchVO searchVO);

	List<?> riskm005_excel(SearchVO searchVO);

	List<?> asset001_excel(SearchVO searchVO);

	List<?> asset001_sample_excel(Map<String, String> map);

	List<?> getStdWorkList(SearchVO searchVO);

	List<?> getUserWorkList(SearchVO searchVO);

	List<?> getUserDetailWorkList(SearchVO searchVO);

	List<?> setup010_excel(SearchVO searchVO);

	List<?> setup011_excel(SearchVO searchVO);

	List<?> mwork019_excel(SearchVO searchVO);

	List<?> comps004_excel(SearchVO searchVO);

	List<?> asset006_excel(SearchVO searchVO);

	List<?> inspt001_excel(SearchVO searchVO);

	Map<String, Object> riskm003_excel(SearchVO searchVO);

	Map<String, Object> riskm003_etc_excel(Map<String, String> map);

	Map<String, Object> riskm006_excel(Map<String, String> map);

	Map<String, Object> riskm006_etc_excel(Map<String, String> map);

	Map<String, Object> riskm006_excel_list_down(Map<String, String> map);

	Map<String, Object> riskm003_report_down(Map<String, String> map);

	String riskm003_svr_name(Map<String, String> map);

	Map<String, Object> riskm009_excel(Map<String, String> map);

	Map<String, Object> riskm009_excel_list_down(Map<String, String> map);

	/**
	 * 2018-05-10 s, 계정 정보
	 * @param searchVO
	 * @return
	 */
	List<?> setup007_excel(SearchVO searchVO);
}
