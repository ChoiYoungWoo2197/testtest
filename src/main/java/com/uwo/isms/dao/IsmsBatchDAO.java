package com.uwo.isms.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Repository("ismsBatchDAO")
public class IsmsBatchDAO extends EgovAbstractDAO {

	public EgovMap getWorkDaysOfMonth(String currentDate) {
		return (EgovMap) select("IsmsBatch.GET_WORK_DAYS_OF_MONTH", currentDate);
	}

	public EgovMap getWorkDaysOfWeek(String currentDate) {
		return (EgovMap) select("IsmsBatch.GET_WORK_DAYS_OF_WEEK", currentDate);
	}

	@SuppressWarnings("unchecked")
	public List<EgovMap> selectWork(Map<String, String> map) {
		return (List<EgovMap>) list("IsmsBatch.SELECT_UWO_TRC_WORK", map);
	}

	public void insertWork(Map<String, String> map) {
		insert("IsmsBatch.INSERT_UWO_TRC_WORK", map);
	}

	/*
	 * 2017-02-01
	 * 운영주기 복제
	 */
	public EgovMap selectLastUwoMcyMtr() {
		return (EgovMap) select("IsmsBatch.SELECT_LAST_UWO_MCY_MTR", null);
	}

	public void insertUwoMcyMtr(Map<String, String> map) {
		insert("IsmsBatch.INSERT_UWO_MCY_MTR", map);
	}

	public void insertUwoSvcMng(Map<String, String> map) {
		insert("IsmsBatch.INSERT_UWO_SVC_MNG", map);
	}

	public void insertUwoCtrMtr(Map<String, String> map) {
		insert("IsmsBatch.INSERT_UWO_CTR_MTR", map);
	}

	public void insertUwoCtrMap(Map<String, String> map) {
		insert("IsmsBatch.INSERT_UWO_CTR_MAP", map);
	}

	public void insertUwoTrcDoc(Map<String, String> map) {
		insert("IsmsBatch.INSERT_UWO_TRC_DOC", map);
	}

	public void insertUwoMngFle(Map<String, String> map) {
		insert("IsmsBatch.INSERT_UWO_MNG_FLE", map);
	}

	public void insertUwoCtrBrd(Map<String, String> map) {
		insert("IsmsBatch.INSERT_UWO_CTR_BRD", map);
	}

	public void insertUwoTrcCtr(Map<String, String> map) {
		insert("IsmsBatch.INSERT_UWO_TRC_CTR", map);
	}

	public void insertUwoTrcMap(Map<String, String> map) {
		insert("IsmsBatch.INSERT_UWO_TRC_MAP", map);
	}

	public void insertUwoAssMtr(Map<String, String> map) {
		insert("IsmsBatch.INSERT_UWO_ASS_MTR", map);
	}
}

