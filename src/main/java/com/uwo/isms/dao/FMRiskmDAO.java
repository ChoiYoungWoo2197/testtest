package com.uwo.isms.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.uwo.isms.domain.SearchVO;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Repository("fmRiskmDAO")
public class FMRiskmDAO extends EgovAbstractDAO  {

	static final int batchSize = 50;

	public List<?> fm_riskm003_list(Map<String, String> map) {
		List<?> list = list("fmRiskm.QR_RISKM003_LIST", map);
		return list;
	}

	public EgovMap fm_riskm003_rskInfo(Map<String, String> map) {
		return (EgovMap) select("fmRiskm.QR_RISKM003_RSK_INFO", map);
	}

	public void fm_riskm003_rskm_insert(Map<String, String> map) {
		update("fmRiskm.QR_RISKM003_INSERT", map);
	}

	public void fm_riskm003_rskm_update(Map<String, String> map) {
		update("fmRiskm.QR_RISKM003_UPDATE", map);
	}

	public List<?> fm_riskm003_rskList(Map<String, String> map) {
		List<?> list = list("fmRiskm.QR_RISKM003_RSK_LIST", map);
		return list;
	}

	public List<?> fm_riskm003_svr_list() {
		List<?> list = list("fmRiskm.QR_RISKM003_SVR_LIST", null);
		return list;
	}

	public EgovMap fm_riskm003_pnt(String cocKey) {
		return (EgovMap) select("fmRiskm.QR_RISKM003_PNT", cocKey);
	}

	public EgovMap fm_riskm003_etc_info(Map<String, String> map) {
		return (EgovMap) select("fmRiskm.QR_RISKM003_ETC_INFO", map);
	}

	public void fmRiskm003_mngSave(Map<String, String> map) {
		update("fmRiskm.QR_RISKM003_MNG_SAVE", map);
	}

	public void fmRiskm003_rskReq(Map<String, String> map) {
		update("fmRiskm.QR_RISKM003_RSK_REQ", map);
	}

	public void fmRiskm003_rskAppr(Map<String, String> map) {
		update("fmRiskm.QR_RISKM003_RSK_APPR", map);
	}

	public void fmRiskm003_rskRej(Map<String, String> map) {
		update("fmRiskm.QR_RISKM003_RSK_REJ", map);
	}

	public List<?> fm_riskm004_list(Map<String, Object> map) {
		return list("fmRiskm.QR_RISKM004_LIST", map);
	}

	public EgovMap fm_riskm004_info(String usoCockey) {
		return (EgovMap) select("fmRiskm.QR_RISKM004_INFO", usoCockey);
	}

	public void fm_riskm004_insert(Map<String, Object> map) {
		insert("fmRiskm.QR_RISKM004_INSERT", map);
	}

	public void fm_riskm004_update(Map<String, Object> map) {
		update("fmRiskm.QR_RISKM004_UPDATE", map);
	}

	public List<?> fm_riskm005_list(Map<String, Object> map) {
		return list("fmRiskm.QR_RISKM005_LIST", map);
	}

	public EgovMap fm_riskm005_info(String usmSroKey) {
		return (EgovMap) select("fmRiskm.QR_RISKM005_INFO", usmSroKey);
	}

	public void fm_riskm005_insert(Map<String, Object> map) {
		insert("fmRiskm.QR_RISKM005_INSERT", map);
	}

	public void fm_riskm005_update(Map<String, Object> map) {
		update("fmRiskm.QR_RISKM005_UPDATE", map);
	}

	public EgovMap fm_riskm003_grpInfo(Map<String, String> map) {
		return (EgovMap) select("fmRiskm.QR_RISKM003_GRP_INFO", map);
	}

	public List<?> fm_riskm003_report(Map<String, String> map) {
		List<?> list = list("fmRiskm.QR_RISKM003_REPORT", map);
		return list;
	}

	public List<?> fm_riskm003_t_report(Map<String, String> map) {
		List<?> list = list("fmRiskm.QR_RISKM003_T_REPORT", map);
		return list;
	}

	public EgovMap fm_riskm003_info(Map<String, String> map) {
		return (EgovMap) select("fmRiskm.QR_RISKM003_INFO", map);
	}

	public List<?> fm_riskm003_bcyList(Map<String, String> map) {
		return list("fmRiskm.QR_RISKM003_BCY_LIST", map);
	}

	public int fm_riskm003_update_ursStaCodS(Map<String, String> map) {
		return update("fmRiskm.QR_RISKM003_UPDATE_URS_STA_COD_S", map);
	}


	public List<?> fm_riskm006_list(Map<String, String> map) {
		List<?> list = list("fmRiskm.QR_RISKM006_LIST",map);
		return list;
	}

	public int fm_riskm006_cnt(Map<String, String> map) {
		return (Integer) select("fmRiskm.QR_RISKM006_CNT",map);
	}

	public List<?> fm_riskm006_rst_list(Map<String, String> map) {
		return list("fmRiskm.QR_RISKM006_RST_LIST", map);
	}

	public List<?> fm_riskm006_rst_ass_list(Map<String, String> map) {
		return list("fmRiskm.QR_RISKM006_RST_ASS_LIST", map);
	}

	public List<?> fm_riskm006_rst_ass_sum_list(Map<String, String> map) {
		return list("fmRiskm.QR_RISKM006_RST_ASS_SUM_LIST", map);
	}

	public List<?> fm_riskm006_rst_down_list(Map<String, String> map) {
		return list("fmRiskm.QR_RISKM006_RST_DOWN_LIST", map);
	}

	public List<?> fm_ristk006_vlb_list(Map<String, String> map) {
		return list("fmRiskm.QR_RISKM006_RISKVLB_LIST", map);
	}

	public void fm_riskm006_rskm_insert(Map<String, String> map) {
		update("fmRiskm.QR_RISKM006_INSERT", map);
	}

	public void fm_riskm006_rskm_insert(List<Map<String, String>> list) throws SQLException {
		@SuppressWarnings("deprecation")
		SqlMapClient client = getSqlMapClient();

		try {
			client.startTransaction();
			client.startBatch();

			int cnt = 1;
			for (Map<String, String> map : list) {
				client.insert("fmRiskm.QR_RISKM006_INSERT", map);
				if (cnt % batchSize == 0) {
					client.executeBatch();
	                client.startBatch();
	            }
	            cnt++;
			}

			client.executeBatch();
			client.commitTransaction();

		} catch (Exception e) {
			e.printStackTrace();
		}
	 	finally {
	 		client.endTransaction();
		}
	}

	public void fm_riskm006_rskm_asset_smp_update_y(List<Map<String, String>> list) throws SQLException {
		@SuppressWarnings("deprecation")
		SqlMapClient client = getSqlMapClient();

		try {
			client.startTransaction();
			client.startBatch();

			int cnt = 1;
			for (Map<String, String> map : list) {
				client.insert("fmRiskm.QR_RISKM006_ASSET_SMP_UPDATE_Y", map);
				if (cnt % batchSize == 0) {
					client.executeBatch();
	                client.startBatch();
	            }
	            cnt++;
			}

			client.executeBatch();
			client.commitTransaction();

		} catch (Exception e) {
			e.printStackTrace();
		}
	 	finally {
	 		client.endTransaction();
		}
	}


	public int fm_riskm006_asset_exists(Map<String, String> map) {
		return (Integer) select("fmRiskm.QR_RISKM006_ASSET_EXISTS", map);
	}

	public int fm_riskm006_rskm_exists(Map<String, String> map) {
		return (Integer) select("fmRiskm.QR_RISKM006_EXISTS", map);
	}

	public List<?>  fm_riskm006_asset_code_list(Map<String, String> map) {
		return list("fmRiskm.QR_RISKM006_ASSET_CODE_LIST", map);
	}


	public void fm_riskm006_rskm_finallyInsert() {
		// 자산 위험관리는 일괄적으로 부모키를 등록함
		update("fmRiskm.QR_RISKM006_AFTER_INSERT");
	}

	public void fm_riskm006_rskm_srl_insert(Map<String, String> map) {
		delete("fmRiskm.QR_RISKM006_SRL_DELETE", map);
		update("fmRiskm.QR_RISKM006_SRL_INSERT", map);
	}

	public void fm_riskm006_rskm_srl_insert(List<Map<String, String>> list) throws SQLException {
		@SuppressWarnings("deprecation")
		SqlMapClient client = getSqlMapClient();

		try {
			client.startTransaction();
			client.startBatch();

			int cnt = 1;
			for (Map<String, String> map : list) {
				// 2017-02-24, 추후 UPDATE 구문을 추가해야 함
				//if (map.get("doNotDelete") == null) {
				//	client.delete("fmRiskm.QR_RISKM006_SRL_DELETE", map);
				//}
				// 2017-03-21, MERGE INTO 로 변경
				client.insert("fmRiskm.QR_RISKM006_SRL_INSERT", map);
				if (cnt % batchSize == 0) {
					client.executeBatch();
	                client.startBatch();
	            }
	            cnt++;
			}

			client.executeBatch();
			client.commitTransaction();

		} catch (Exception e) {
			e.printStackTrace();
		}
	 	finally {
	 		client.endTransaction();
		}
	}

	public List<?> fm_riskm007_list(Map<String, String> map) {
		return list("fmRiskm.QR_RISKM007_LIST", map);
	}

	public EgovMap fm_riskm007_info(String urvRskKey) {
		return (EgovMap) select("fmRiskm.QR_RISKM007_INFO", urvRskKey);
	}

	public void fm_riskm007_insert(Map<String, Object> map) {
		insert("fmRiskm.QR_RISKM007_INSERT", map);
	}

	public void fm_riskm007_update(Map<String, Object> map) {
		update("fmRiskm.QR_RISKM007_UPDATE", map);
	}

	public List<?> getCocList(Map<String, String> map) {
		return list("fmRiskm.QR_GET_COC_LIST", map);
	}

	public List<?> getUsrList(Map<String, String> map) {
		return list("fmRiskm.QR_GET_USR_LIST", map);
	}

	public List<?> fm_riskm008_list(Map<String, String> map) {
		return list("fmRiskm.QR_RISKM008_LIST", map);
	}

	public List<?> fm_riskm008_dep_list(Map<String, String> map) {
		return list("fmRiskm.QR_RISKM008_DEP_LIST", map);
	}

	public void fmRiskm008_addRskDepList(Map<String, String> map){
		update("fmRiskm.QR_RISKM008_ADD_RISK_DEPLIST", map);
	}

	public void fmRiskm008_delRskDepList(Map<String, String> map){
		update("fmRiskm.QR_RISKM008_DEL_RISK_DEPLIST", map);
	}

	public void fm_riskm008_update_rskDepMng(Map<String, String> map){
		update("fmRiskm.QR_RISKM008_UPDATE_RISK_DEP_MNG", map);
	}

	public List<?> fm_riskm009_list(Map<String, String> map) {
		return list("fmRiskm.QR_RISKM009_LIST", map);
	}

	public List<?> fm_riskm009_rst_list(Map<String, String> map) {
		return list("fmRiskm.QR_RISKM009_RST_LIST", map);
	}

	public void fm_riskm009_rskm_srl_insert(Map<String, String> map) {
		delete("fmRiskm.QR_RISKM009_SRL_DELETE", map);
		update("fmRiskm.QR_RISKM009_SRL_INSERT", map);
	}

	public List<?> fm_riskm009_rst_down_list(Map<String, String> map) {
		return list("fmRiskm.QR_RISKM009_RST_DOWN_LIST", map);
	}

	public List<?> fm_riskm009_rst_ass_sum_list(Map<String, String> map) {
		return list("fmRiskm.QR_RISKM009_RST_ASS_SUM_LIST", map);
	}

	public List<?> fmRiskm010_list(Map<String, String> map) {
		return list("fmRiskm.QR_RISKM010_LIST", map);
	}

	public List<?> fm_riskm010_rskList(Map<String, String> map) {
		return list("fmRiskm.QR_RISKM010_RSK_LIST", map);
	}

	public EgovMap fm_riskm010_rskInfo(Map<String, String> map) {
		return (EgovMap) select("fmRiskm.QR_RISKM010_RSK_INFO", map);
	}

	public int fm_riskm010_update_urrStaCodQ(Map<String, String> map) {
		return update("fmRiskm.QR_RISKM010_UPDATE_URR_STA_COD_Q", map);
	}

	public int fm_riskm010_update_urrStaCodS(Map<String, String> map) {
		return update("fmRiskm.QR_RISKM010_UPDATE_URR_STA_COD_S", map);
	}

	public int fm_riskm010_update_ursStaCodQ(Map<String, String> map) {
		return update("fmRiskm.QR_RISKM010_UPDATE_URS_STA_COD_Q", map);
	}

	public int fm_riskm010_update_ursStaCodS(Map<String, String> map) {
		return update("fmRiskm.QR_RISKM010_UPDATE_URS_STA_COD_S", map);
	}

	public List<?> fm_riskm010_urrDepList(Map<String, String> map) {
		return list("fmRiskm.QR_RISKM010_URR_DEP_LIST", map);
	}

	public String fm_riskm010_urrRskKey(Map<String, String> map) {
		return (String) select("fmRiskm.QR_RISKM010_URR_RSK_KEY", map);
	}

	public EgovMap fm_riskm010_urrDetail(Map<String, String> map) {
		return (EgovMap) select("fmRiskm.QR_RISKM010_URR_DETAIL", map);
	}

	public void fmRiskm010_mngSave(Map<String, String> map) {
		update("fmRiskm.QR_RISKM010_MNG_SAVE", map);
	}

	public void fmRiskm010_mngSaveUrr(Map<String, String> map) {
		update("fmRiskm.QR_RISKM010_MNG_SAVE_URR", map);
	}

	public void fmRiskm010_rskReq(Map<String, String> map) {
		update("fmRiskm.QR_RISKM010_RSK_REQ", map);
	}

	public void fmRiskm010_rskAppr(Map<String, String> map) {
		update("fmRiskm.QR_RISKM010_RSK_APPR", map);
	}

	public void fmRiskm010_rskRej(Map<String, String> map) {
		update("fmRiskm.QR_RISKM010_RSK_REJ", map);
	}

	public void fmRiskm010_updateUrsSta(Map<String, String> map) {
		update("fmRiskm.QR_RISKM010_UPDATE_URS_STA_COD_PA", map);
	}

	public List<?> getRst003MainList(Map<String, String> map) {
		return list("fmRiskm.QR_RISKM003_MAIN_LIST", map);
	}

	public List<?> getRst010MainList(Map<String, String> map) {
		return list("fmRiskm.QR_RISKM010_MAIN_LIST", map);
	}

	// 2017-03-21
	public String selectGrpCodOfAssetCode(Map<String, String> map) {
		return (String) select("fmRiskm.selectGrpCodOfAssetCode", map);
	}
}
