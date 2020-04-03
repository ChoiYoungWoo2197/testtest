package com.uwo.isms.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;


@Repository("legacyDAO")
public class LegacyDAO extends EgovAbstractDAO {
	@Resource(name = "sqlMapClient_SqlServer")

	public void setSuperSqlMapClient(SqlMapClient sqlMapClient) {
		super.setSuperSqlMapClient(sqlMapClient);
	}

	// 인사
	public List<?> legacyUserList() {
        return list("JOB.LEGACY_USER_LIST");
    }

	public List<?> legacyDeptList() {
		return list("JOB.LEGACY_DEPT_LIST");
	}

	public List<?> legacyPosList() {
		return list("JOB.LEGACY_POS_LIST");
	}

	// 자산
	public List<?> legacyAssetList() {
		return list("JOB.selectLegacyAssetList");
	}

	// SSR 진단코드
	public List<?> legacyDiagnosisItemInfoList() {
		return list("JOB.selectLegacyDiagnosisItemInfoList");
	}

	// SSR 진단결과
	public List<?> legacyDiagnosisResultList() {
		return list("JOB.selectLegacyDiagnosisResultList");
	}
}
