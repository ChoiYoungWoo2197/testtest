package com.uwo.isms.service;

import java.util.List;
import java.util.Map;

import com.uwo.isms.domain.SearchVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface FMStateService {

	public List<?> fmSTATE001_BOARD_LIST_1(SearchVO searchVO);

	public List<?> fmSTATE001_BOARD_LIST_2(SearchVO searchVO);

	public List<?> fmSTATE001_BOARD_LIST_3(SearchVO searchVO);

	public List<?> fmSTATE001_BOARD_LIST_4(SearchVO searchVO);

	public List<?> fmSTATE001_BOARD_LIST_1_2(SearchVO searchVO);

	public List<?> fmSTATE001_BOARD_LIST_2_2(SearchVO searchVO);

	public List<?> fmSTATE001_BOARD_LIST_3_2(SearchVO searchVO);

	public List<?> fmSTATE001_BOARD_LIST_4_2(SearchVO searchVO);

	public List<?> divlist();

	public List<?> fmSTATE002_STD_LIST(SearchVO searchVO);

	public List<?> fmSTATE002_DEPT_LIST(SearchVO searchVO);

	public List<?> fmSTATE002_USER_LIST(SearchVO searchVO);

	public List<?> fmSTATE002_TO_POP_LIST(SearchVO searchVO);

	public List<?> fmSTATE002_TO_POP_STDLIST(SearchVO searchVO);

	public List<?> fmSTATE002_TO_POP_DEPLIST(SearchVO searchVO);

	public List<?> fmSTATE002_TO_POP_WORKERLIST(SearchVO searchVO);

	public List<?> fmSTATE003_LIST(SearchVO searchVO);

	public List<?> fmSTATE003_DIV(SearchVO searchVO);

	public List<?> fmSTATE003_COM(SearchVO searchVO);

	public List<?> fmSTATE000_MAIN(SearchVO searchVO);

	public List<?> fmSTATE004_STD_LIST(SearchVO searchVO);

	public List<?> fmSTATE004_DEPT_LIST(SearchVO searchVO);

	public List<?> fmSTATE004_USER_LIST(SearchVO searchVO);

	public List<?> fmSTATE005_STD_LIST(SearchVO searchVO);

	public List<?> fmSTATE005_DEPT_LIST(SearchVO searchVO);

	public List<?> fmSTATE006_STD_LIST(SearchVO searchVO);

	public List<?> fmSTATE006_SUB_LIST(SearchVO searchVO);


	/* 2016-10-19,  신규 통계 */
	public List<?> selectWorkServiceStatistics(Map<String, String> map);

	public List<?> selectWorkDepStatistics(Map<String, String> map);

	public List<?> selectWorkUserStatistics(Map<String, String> map);

	public List<?> selectWorkMonthStatistics(Map<String, String> map);

	public List<?> selectWorkCtrStatistics(Map<String, String> map);

	public List<?> selectAssetServiceStatistics(Map<String, Object> map);

	public List<?> selectAssetServiceStatisticsOfAsset(Map<String, Object> map);

	public List<?> selectAssetDepStatistics(Map<String, Object> map);

	public List<?> selectAssetDepStatisticsOfAsset(Map<String, Object> map);

	public List<?> selectAssetCodeList();

	public List<?> selectDepCodeListOfService(Map<String, Object> map);

	/*
	 * 2017-06-30, 자산유형별 위험도 합계
	 */
	public List<?> selectRiskCatStatistics(Map<String, String> map);


	/* 통계 추가 작업 */
	public List<EgovMap> getMCY();
	public String getQuarterYear(String bcy_cod);

	public List<?> getStatisticsSaPart1(Map<String, String> map);
	public List<?> getStatisticsSaPart2(Map<String, String> map);
	public String fmSTATE018_EDIT_SA(Map<String, String> map);
	public List<Map> initCriterion(String bcy_cod);
	public void initCriterionPoint(String bcy_cod);
	public List<Map> getCriterion(Map<String, String> map);
	public List<Map> getCriterionPoint(Map<String, String> map);

	public String setCriterionPoint(Map<String, String> map);

	public List<?> getStatisticsInfraMpPart1(Map<String, Object> map);
	public List<?> getStatisticsInfraMpPart2(Map<String, String> map);

	public List<?> getStatisticsInfraLaPart1(Map<String, String> map);
	public List<?> getStatisticsInfraLaPart2(Map<String, Object> map);

	public List<?> getStatisticsMsitPart1(Map<String, Object> map);
	public List<?> getStatisticsMsitPart2(Map<String, String> map);

}
