package com.uwo.isms.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

//import com.uwo.isms.common.BoardVO;


//import egovframework.com.sym.cal.service.RestdeVO;
import com.uwo.isms.domain.SearchVO;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Repository("fmStateDAO")
public class FMStateDAO extends EgovAbstractDAO  {

	public List<?> fmSTATE001_BOARD_LIST_1(SearchVO searchVO) {
		return list("fmState.QR_STATE001_A",searchVO);
	}

	public List<?> fmSTATE001_BOARD_LIST_2(SearchVO searchVO) {
		return list("fmState.QR_STATE001_B",searchVO);
	}

	public List<?> fmSTATE001_BOARD_LIST_3(SearchVO searchVO) {
		return list("fmState.QR_STATE001_C",searchVO);
	}

	public List<?> fmSTATE001_BOARD_LIST_4(SearchVO searchVO) {
		return list("fmState.QR_STATE001_D",searchVO);
	}

	public List<?> fmSTATE001_BOARD_LIST_1_2(SearchVO searchVO) {
		return list("fmState.QR_STATE001_E",searchVO);
	}

	public List<?> fmSTATE001_BOARD_LIST_2_2(SearchVO searchVO) {
		return list("fmState.QR_STATE001_F",searchVO);
	}

	public List<?> fmSTATE001_BOARD_LIST_3_2(SearchVO searchVO) {
		return list("fmState.QR_STATE001_G",searchVO);
	}

	public List<?> fmSTATE001_BOARD_LIST_4_2(SearchVO searchVO) {
		return list("fmState.QR_STATE001_H",searchVO);
	}

	public List<?> divlist() {
		return list("fmState.QR_STATE001_I");
	}

	public List<?> fmSTATE002_STD_LIST(SearchVO searchVO) {
		return list("fmState.QR_STATE002_A",searchVO);
	}

	public List<?> fmSTATE002_DEPT_LIST(SearchVO searchVO) {
		return list("fmState.QR_STATE002_B",searchVO);
	}

	public List<?> fmSTATE002_USER_LIST(SearchVO searchVO) {
		return list("fmState.QR_STATE002_C",searchVO);
	}

	public List<?> fmSTATE002_TO_POP_LIST(SearchVO searchVO) {
		return list("fmState.QR_STATE002_D",searchVO);
	}

	public List<?> fmSTATE002_TO_POP_STDLIST(SearchVO searchVO) {
		return list("fmState.QR_STATE002_E",searchVO);
	}

	public List<?> fmSTATE002_TO_POP_DEPLIST(SearchVO searchVO) {
		return list("fmState.QR_STATE002_F",searchVO);
	}

	public List<?> fmSTATE002_TO_POP_WORKERLIST(SearchVO searchVO) {
		return list("fmState.QR_STATE002_G",searchVO);
	}

	public List<?> fmSTATE003_LIST(SearchVO searchVO) {
		return list("fmState.QR_STATE003_L",searchVO);
	}

	public List<?> fmSTATE003_DIV(SearchVO searchVO) {
		return list("fmState.QR_STATE003_D",searchVO);
	}

	public List<?> fmSTATE003_COM(SearchVO searchVO) {
		return list("fmState.QR_STATE003_C",searchVO);
	}

	public List<?> fmSTATE000_MAIN(SearchVO searchVO) {
		return list("fmState.QR_STATE000_M",searchVO);
	}

	public List<?> fmSTATE004_STD_LIST(SearchVO searchVO) {
		return list("fmState.QR_STATE004_L",searchVO);
	}

	public List<?> fmSTATE004_DEPT_LIST(SearchVO searchVO) {
		return list("fmState.QR_STATE004_D",searchVO);
	}

	public List<?> fmSTATE004_USER_LIST(SearchVO searchVO) {
		return list("fmState.QR_STATE004_U",searchVO);
	}

	public List<?> fmSTATE005_STD_LIST(SearchVO searchVO) {
		return list("fmState.QR_STATE005_L",searchVO);
	}

	public List<?> fmSTATE005_DEPT_LIST(SearchVO searchVO) {
		return list("fmState.QR_STATE005_D",searchVO);
	}

	public List<?> fmSTATE006_STD_LIST(SearchVO searchVO) {
		return list("fmState.QR_STATE006_L",searchVO);
	}

	public List<?> fmSTATE006_SUB_LIST(SearchVO searchVO) {
		return list("fmState.QR_STATE006_S",searchVO);
	}


	/* 2016-10-19,  신규 통계 */
	public List<EgovMap> selectWorkServiceStatistics(Map<String, String> map) {
		return (List<EgovMap>) list("fmState.selectWorkServiceStatistics", map);
	}

	public List<EgovMap> selectWorkDepStatistics(Map<String, String> map) {
		return (List<EgovMap>) list("fmState.selectWorkDepStatistics", map);
	}

	public List<EgovMap> selectWorkUserStatistics(Map<String, String> map) {
		return (List<EgovMap>) list("fmState.selectWorkUserStatistics", map);
	}

	public List<EgovMap> selectWorkMonthStatistics(Map<String, String> map) {
		return (List<EgovMap>) list("fmState.selectWorkMonthStatistics", map);
	}

	public List<EgovMap> selectWorkCtrStatistics(Map<String, String> map) {
		return (List<EgovMap>) list("fmState.selectWorkCtrStatistics", map);
	}

	public List<EgovMap> selectAssetServiceStatistics(Map<String, Object> map) {
		return (List<EgovMap>) list("fmState.selectAssetServiceStatistics", map);
	}

	public List<EgovMap> selectAssetServiceStatisticsOfAsset(Map<String, Object> map) {
		return (List<EgovMap>) list("fmState.selectAssetServiceStatisticsOfAsset", map);
	}

	public List<EgovMap> selectAssetDepStatistics(Map<String, Object> map) {
		return (List<EgovMap>) list("fmState.selectAssetDepStatistics", map);
	}

	public List<EgovMap> selectAssetDepStatisticsOfAsset(Map<String, Object> map) {
		return (List<EgovMap>) list("fmState.selectAssetDepStatisticsOfAsset", map);
	}

	public List<EgovMap> selectAssetCodeList() {
		return (List<EgovMap>) list("fmState.selectAssetCodeList", null);
	}

	public List<EgovMap> selectDepCodeListOfService(Map<String, Object> map) {
		return (List<EgovMap>) list("fmState.selectDepCodeListOfService", map);
	}

	/*
	 * 2017-06-30, 자산유형별 위험도 합계
	 */
	public List<EgovMap> selectRiskCatStatistics(Map<String, String> map) {
		return (List<EgovMap>) list("fmState.selectRiskCatStatistics", map);
	}



	/*
	통계 추가
	 */
	public List<EgovMap> getMCY(){
		return (List<EgovMap>) list("fmState.getMCY");
	}

	public String getQuarterYear(String bcy_cod){
		return(String) select("fmState.getQuarterYear", bcy_cod);
	}

	public List<EgovMap> getStatisticsPreYear(Map<String, Object> mapPreYear) {
		return (List<EgovMap>) list("fmState.getStatisticsPreYear", mapPreYear);
	}

	public List<EgovMap> getStatisticsSaLv1(Map<String, String> map) {
		return (List<EgovMap>) list("fmState.getStatisticsSaLv1", map);
	}
	public List<EgovMap> getStatisticsSaPart1(Map<String, String> map) {
		return (List<EgovMap>) list("fmState.getStatisticsSaPart1", map);
	}
	public List<EgovMap> getStatisticsSaPart2(Map<String, String> map) {
		return (List<EgovMap>) list("fmState.getStatisticsSaPart2", map);
	}

	public Integer getDepSaLevel(Map<String, String> map) {
		return (Integer) select("fmState.getDepSaLevel", map);
	}
	public void insertDepSaLevel(Map<String, String> map) {
		insert("fmState.insertDepSaLevel", map);
	}
	public void updateDepSaLevel(Map<String, String> map) {
		update("fmState.updateDepSaLevel", map);
	}
	public void deleteDepSaLevel(Map<String, String> map) {
		delete("fmState.deleteDepSaLevel", map);
	}

	public void initCriterion(Map<String, String> map){
		insert("fmState.initCriterion", map);
	}
	public List<Map> getCriterion(Map<String, String> map){
		return(List<Map>) list("fmState.getCriterion", map);
	}
	public List<Map> getCriterionPoint(Map<String, String> map){
		return(List<Map>) list("fmState.getCriterionPoint", map);
	}

	public void updateCriterion(Map<String, String> map){
		update("fmState.updateCriterion", map);
	}
	public void insertCriterionPoint(Map<String, String> map){
		insert("fmState.insertCriterionPoint", map);
	}

	public void updateCriterionPoint(Map<String, String> map){
		update("fmState.updateCriterionPoint", map);
	}

	public List<EgovMap> getStatisticsSaYears(Map<String, Object> map) {
		return (List<EgovMap>) list("fmState.getStatisticsSaYears", map);
	}

	public List<EgovMap> getStatisticsInfraMpPart1_1(Map<String, Object> map) {
		return (List<EgovMap>) list("fmState.getStatisticsInfraMpPart1_1", map);
	}
	public List<EgovMap> getStatisticsInfraMpPart1_2(Map<String, Object> map) {
		return (List<EgovMap>) list("fmState.getStatisticsInfraMpPart1_2", map);
	}
	public List<EgovMap> getStatisticsInfraMpPart2(Map<String, String> map) {
		return (List<EgovMap>) list("fmState.getStatisticsInfraMpPart2", map);
	}

	public List<EgovMap> getStatisticsInfraLaPart1(Map<String, String> map) {
		return (List<EgovMap>) list("fmState.getStatisticsInfraLaPart1", map);
	}
	public List<EgovMap> getStatisticsInfraLaPart2(Map<String, Object> map) {
		return (List<EgovMap>) list("fmState.getStatisticsInfraLaPart2", map);
	}

	public List<EgovMap> getStatisticsMsitPart1_1(Map<String, Object> map) {
		return (List<EgovMap>) list("fmState.getStatisticsMsitPart1_1", map);
	}
	public List<EgovMap> getStatisticsMsitPart1_2(Map<String, Object> map) {
		return (List<EgovMap>) list("fmState.getStatisticsMsitPart1_2", map);
	}
	public List<EgovMap> getStatisticsMsitPart2(Map<String, String> map) {
		return (List<EgovMap>) list("fmState.getStatisticsMsitPart2", map);
	}
}
