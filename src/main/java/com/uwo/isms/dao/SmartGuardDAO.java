package com.uwo.isms.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;


@Repository("smartGuardDAO")
public class SmartGuardDAO extends EgovAbstractDAO {
	@Resource(name = "sqlMapClientSmartGuard")

	public void setSuperSqlMapClient(SqlMapClient sqlMapClient) {
		super.setSuperSqlMapClient(sqlMapClient);
	}
	// 자산
	public List<?> legacyAssetList() {
		return list("JOB.selectSmartGuardAssetList");
	}

	// SSR 진단코드
	public List<?> legacyDiagnosisItemInfoList() {
		return list("JOB.selectSmartGuardDiagnosisItemInfoList");
	}

	// SSR 진단결과
	public List<?> legacyDiagnosisResultList() {
		return list("JOB.selectSmartGuardDiagnosisResultList");
	}
}
