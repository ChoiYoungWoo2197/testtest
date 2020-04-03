package com.uwo.isms.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.uwo.isms.domain.RiskVO;
import com.uwo.isms.domain.SearchVO;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Repository("fmAssetDAO")
public class FMAssetDAO extends EgovAbstractDAO  {

	public List<?> fm_asset001_list(SearchVO searchVO) {
		List<?> list = list("fmAsset.QR_ASSET001_A",searchVO);	//
		return list;
	}

	public int fm_asset001_cnt(SearchVO searchVO) {
		return (Integer) select("fmAsset.QR_ASSET001_CNT",searchVO);
	}

	public EgovMap fm_asset001_assetCodeInfo(String code) {
		System.out.println("code = " + code);
		return (EgovMap) select("fmAsset.QR_ASSET001_B", code);
	}

	public String fmasset001_asset_grpcode01(String depCod) {
		return (String) select("fmAsset.QR_ASSET001_GRPCODE01", depCod);
	}

	public String fmasset001_asset_grpcode02(String subCod) {
		return (String) select("fmAsset.QR_ASSET001_GRPCODE02", subCod);
	}

	public String fmasset001_asset_grpcode03(String rskGrp) {
		return (String) select("fmAsset.QR_ASSET001_GRPCODE03", rskGrp);
	}

	public String fmasset001_asset_grpcode04(String rskNam) {
		return (String) select("fmAsset.QR_ASSET001_GRPCODE04", rskNam);
	}

	public String fmasset001_asset_grpcode05(String valCl2) {
		return (String) select("fmAsset.QR_ASSET001_GRPCODE05", valCl2);
	}

	public String fmasset001_asset_grpcode06(String valCl2) {
		return (String) select("fmAsset.QR_ASSET001_GRPCODE06", valCl2);
	}

	public void fm_asset001_assetCode_insert(Map<String, Object> map) {
		insert("fmAsset.QR_ASSET001_C", map);
	}

	public void fm_asset001_group_insert(Map<String, Object> map) {
		insert("fmAsset.QR_ASSET001_GROUP_INSERT", map);
	}

	public void fm_asset001_assetCodeLog_insert(Map<String, Object> map) {
		insert("fmAsset.QR_ASSET001_D", map);
	}

	public void fm_asset001_assetCode_update(Map<String, Object> map) {
		update("fmAsset.QR_ASSET001_E", map);
	}

	public int fm_asset001_excel_insert(Map<String, String> map) {
		return update("fmAsset.QR_ASSET001_G", map);
	}

	//엑셀 업로드시 자산업데이트 (12.22)
	public int fm_asset001_excel_update(Map<String, String> map) {
		return update("fmAsset.QR_ASSET001_G_UPDATE", map);
	}

	//자산그룹 체크 (11.11)
	public int fm_asset001_groupCdCount(String groupCd) {
		return (Integer) select("fmAsset.QR_ASSET001_GROUP_COUNT", groupCd);
	}

	public List<?> fm_asset002_list(SearchVO searchVO) {
		List<?> list = list("fmAsset.QR_ASSET002_A",searchVO);	//
		return list;
	}

	public RiskVO fm_asset002_assetGroupInfo(String groupKey) {
		return (RiskVO) select("fmAsset.QR_ASSET002_D", groupKey);
	}

	//그룹코드 insert
	public void fm_asset002_assetGroup_insert(RiskVO riskVO) {
		insert("fmAsset.QR_ASSET002_E", riskVO);
	}

	public void fm_asset001_assetGroup_insert(Map<String, String> map) {
		insert("fmAsset.QR_ASSET001_H", map);
	}

	public void fm_asset002_assetGroup_update(RiskVO riskVO) {
		update("fmAsset.QR_ASSET002_F", riskVO);
	}

	public List<?> fm_asset002_assetlistBelongG(SearchVO searchVO) {
		List<?> list = list("fmAsset.QR_ASSET002_B",searchVO);	//
		return list;
	}

	public List<?> fm_asset002_assetlistNotBelongG(SearchVO searchVO) {
		List<?> list = list("fmAsset.QR_ASSET002_C",searchVO);	//
		return list;
	}

	public void fm_asset002_groupAsset_update(SearchVO searchVO) {
		update("fmAsset.QR_ASSET002_G", searchVO);
	}

	public void fm_asset002_riskGroup_insert(RiskVO riskVO) {
		insert("fmAsset.QR_ASSET002_H", riskVO);
	}

	public void fm_asset002_riskGroup_update(RiskVO riskVO) {
		update("fmAsset.QR_ASSET002_I", riskVO);
	}

	public List<?> getHistory(SearchVO searchVO) {
		List<?> list = list("fmAsset.QR_ASSET006_A", searchVO);
		return list;
	}

	public int getHistory_count(SearchVO searchVO) {
		int cnt = (Integer)select("fmAsset.QR_ASSET006_B", searchVO);
		return cnt;
	}

	public List<?> fm_asset008_list(Map<String, String> map) {
		return list("fmAsset.QR_ASSET008_LIST", map);
	}

	public void fm_asset008_insert(Map<String, String> map) {
		insert("fmAsset.QR_ASSET008_INSERT", map);
	}

	public void fm_asset008_update(Map<String, String> map) {
		update("fmAsset.QR_ASSET008_UPDATE", map);
	}

	public EgovMap fm_asset008_info(Map<String, String> map) {
		return (EgovMap) select("fmAsset.QR_ASSET008_INFO", map);
	}

	public List<?> fm_asset002_report(SearchVO searchVO) {
		List<?> list = list("fmAsset.QR_ASSET002_REPORT",searchVO);	//
		return list;
	}

	public String fmasset001_rsk_cod(String uarRskGrp) {
		return (String) select("fmAsset.QR_ASSET001_RSK_COD", uarRskGrp) ;
	}

	public String fmasset001_rsk_cod_ext(String uarRskGrp) {
		return (String) select("fmAsset.QR_ASSET001_RSK_COD_EXT", uarRskGrp) ;
	}

	public String fmasset001_rsk_nam(String uarRskNam) {
		return (String) select("fmAsset.QR_ASSET001_RSK_NAM", uarRskNam) ;
	}

	public String fmasset001_rsk_nam_ext(String uarRskNam) {
		return (String) select("fmAsset.QR_ASSET001_RSK_NAM_EXT", uarRskNam) ;
	}

	public EgovMap fmasset001_rsk_code_info(String uarRskGrp) {
		return (EgovMap) select("fmAsset.QR_ASSET001_RSK_CODE_INFO", uarRskGrp) ;
	}

	public EgovMap fmasset001_rsk_os_info(String uarRskGrp) {
		return (EgovMap) select("fmAsset.QR_ASSET001_RSK_OS_INFO", uarRskGrp) ;
	}

	/*
	 * 2017-06-28, 심사자산 확정/제외
	 */
	public void fm_asset003_addFinal(Map<String, Object> map) {
		insert("fmAsset.assetAddFinal", map);
		fm_asset003_updateAud(map);
	}
	public void fm_asset003_removeFinal(Map<String, Object> map) {
		insert("fmAsset.assetRemoveFinal", map);
		fm_asset003_updateAud(map);
	}

	/*
	 * 2017-06-30
	 * ISC 자산의 심사여부를 모두 N 처리 후 현재 주기의 심사자산에 있는 자산만 Y로 변경
	 * 실서버의 Table Lock으로 인해 EXISTS(self) 를 사용하지 못하고
	 * Oralce 의 최대 파라미터가 1,000개 이기 때문에 500개씩 나눠서 처리함
	 */
	public void fm_asset003_updateAud(Map<String, Object> map) {
		update("fmAsset.assetUpdateAudN", map);

		List<?> list = list("fmAsset.assetSelectAud", map);
		List<String> assKeys = new ArrayList<String>();

		int cnt = 1;
		for (Object item : list) {
			assKeys.add((String) item);
			if (cnt % 500 == 0) {
				map.put("assKeys", assKeys);
				update("fmAsset.assetUpdateAudY", map);
				assKeys.clear();
			}
			cnt++;
		}
		if (assKeys.size() > 0) {
			map.put("assKeys", assKeys);
			update("fmAsset.assetUpdateAudY", map);
		}
	}

	/*
	 * 2017-06-28, 샘플링대상 추가/제외
	 */
	public void fm_asset003_addSample(Map<String, Object> map) {
		map.put("smpYn", "Y");
		insert("fmAsset.assetUpdateSample", map);
	}
	public void fm_asset003_removeSample(Map<String, Object> map) {
		map.put("smpYn", "N");
		insert("fmAsset.assetUpdateSample", map);
	}
}
