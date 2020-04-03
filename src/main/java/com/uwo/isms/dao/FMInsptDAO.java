package com.uwo.isms.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.uwo.isms.common.EventTitleMessage;
import com.uwo.isms.domain.ApprMapVO;
import com.uwo.isms.domain.BoardVO;
import com.uwo.isms.domain.FileVO;
import com.uwo.isms.domain.IFP_DTL_VO;
import com.uwo.isms.domain.IFP_MTR_VO;
import com.uwo.isms.domain.SearchVO;
import com.uwo.isms.domain.WorkVO;
import com.uwo.isms.service.SendMailService;
import com.uwo.isms.util.EgovDateUtil;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Repository("fmInsptDAO")
public class FMInsptDAO extends EgovAbstractDAO  {

	@Resource(name = "sendMailService")
	SendMailService sendMailService;

	public List<?> fm_inspt001_list(Map<String, String> map) {
		List<?> list = list("fmInspt.QR_INSPT001_A", map);
		return list;
	}

	public List<?> fm_inspt001_list2(Map<String, String> map) {
		List<?> list = list("fmInspt.QR_INSPT001_A2", map);
		return list;
	}

	public List<?> fm_inspt001_list3(Map<String, String> map) {
		List<?> list = list("fmInspt.QR_INSPT001_A3", map);
		return list;
	}

	public List<?> fm_inspt001_getFileList(Map<String, String> map) {
		List<?> list = list("fmInspt.QR_INSPT001_GETFILELIST", map);
		return list;
	}

	public List<?> fm_inspt001_getFileList2(Map<String, String> map) {
		List<?> list = list("fmInspt.QR_INSPT001_GETFILELIST_2", map);
		return list;
	}

	public List<EgovMap> fm_inspt001_getSaFileList(Map<String, String> map) {
		List<EgovMap> list = (List<EgovMap>) list("fmInspt.QR_INSPT001_GETSAFILELIST", map);
		return list;
	}

	public List<?> fm_inspt001_getFileList_by_department_of_service(Map<String, String> map) {
		List<?> list = list("fmInspt.QR_INSPT001_GETFILELIST_BY_DEPARTMENT_OF_SERVICE", map);
		return list;
	}

	public int fm_inspt001_setMsrFile(Map<String, String> map) {
		return update("fmInspt.QR_INSPT001_SETMSRFILE", map);
	}

	public List<?> fm_inspt001_getStdList(SearchVO searchVO) {
		List<?> list = list("fmInspt.QR_INSPT001_TREE", searchVO); //
		return list;
	}

	/**
	 * 시스템설정 - 통제항목 설정 - tree, cntr1 select
	 * @param searchVO
	 * @return
	 */
	public List<?> fm_inspt001_getCntr1List(SearchVO searchVO) {

		List<?> list = list("fmInspt.QR_INSPT001_B", searchVO); //
		return list;
	}

	/**
	 * 시스템설정 - 통제항목 설정 - tree, cntr2 select
	 * @param searchVO
	 * @return
	 */
	public List<?> fm_inspt001_getCntr2List(SearchVO searchVO) {
		List<?> list = list("fmInspt.QR_INSPT001_C", searchVO); //
		return list;
	}

	/**
	 * 시스템설정 - 통제항목 설정 - tree, cntr3 select
	 * @param searchVO
	 * @return
	 */
	public List<?> fm_inspt001_getCntr3List(SearchVO searchVO) {
		List<?> list = list("fmInspt.QR_INSPT001_D", searchVO); //
		return list;
	}

	/**
	 * 시스템설정 - 통제항목 설정 - tree, cntr4 select
	 * @param searchVO
	 * @return
	 */
	public List fm_inspt001_getCntr4List(SearchVO searchVO) {
		List<?> list = list("fmInspt.QR_INSPT001_E", searchVO); //
		return list;
	}

	/**
	 * 시스템설정 - 통제항목 설정 - tree, cntr5 select
	 * @param searchVO
	 * @return
	 */
	public List fm_inspt001_getCntr5List(SearchVO searchVO) {
		List<?> list = list("fmInspt.QR_INSPT001_F", searchVO); //
		return list;
	}


	public List<?> fm_inspt002_list(Map<String, String> map) {
		return list("fmInspt.QR_INSPT002_LIST", map);
	}

	public EgovMap fm_inspt002_info(Map<String, String> map) {
		return (EgovMap)select("fmInspt.QR_INSPT002_INFO", map);
	}

	public void fm_inspt002_insert(Map<String, Object> map) {
		insert("fmInspt.QR_INSPT002_INSERT", map);
	}

	public void fm_inspt002_update(Map<String, Object> map) {
		update("fmInspt.QR_INSPT002_UPDATE", map);
	}

	public void fm_inspt002_updateRst(Map<String, String> map) {
		update("fmInspt.QR_INSPT002_UPDATE_RST", map);
	}

	public void fm_inspt002_mng_insert(Map<String, String> map) {
		insert("fmInspt.QR_INSPT002_MNG_INSERT", map);
	}

	public void fm_inspt002_mng_update(Map<String, String> map) {
		update("fmInspt.QR_INSPT002_MNG_UPDATE", map);
	}

	public List<?> fm_inspt002_mng_list(Map<String, String> map) {
		return list("fmInspt.QR_INSPT002_MNG_LIST", map);
	}

	public EgovMap fm_inspt002_mng_info(Map<String, String> map) {
		return (EgovMap)select("fmInspt.QR_INSPT002_MNG_INFO", map);
	}

	public void fm_inspt002_apv_update(Map<String, Object> map) {
		update("fmInspt.QR_INSPT002_APV_UPDATE", map);
	}

	public void fm_inspt002_mng_delete(Map<String, String> map) {
		delete("fmInspt.QR_INSPT002_MNG_DELETE", map);
	}

	public String inspt001_view(String wKey) {
		return (String)select("fmInspt.QR_INSPT001_VIEW", wKey);
	}

	public void fmInspt001_viewUpdate(SearchVO searchVO) {
		update("fmInspt.QR_INSPT001_VIEW_UPDATE", searchVO);
	}

	public List<?> fm_inspt002_report(Map<String, String> map) {
		return list("fmInspt.QR_INSPT002_REPORT", map);
	}

	public List<?> fm_inspt002_report_mng(Map<String, String> map) {
		return list("fmInspt.QR_INSPT002_REPORT_MNG", map);
	}

	public List<?> fm_inspt003_list(SearchVO searchVO) {
		List<?> list = list("fmInspt.QR_INSPT003_A", searchVO); //
		return list;
	}

	public List<?> fm_inspt004_list(Map<String, String> map) {
		List<?> list = list("fmInspt.QR_INSPT004_A", map); //
		return list;
	}

	public List<?> fm_inspt004_report(Map<String, String>  map) {
		List<?> list = list("fmInspt.QR_INSPT004_REPORT", map); //
		return list;
	}

	public List<?> fm_inspt004_getCtrCode(Map<String, String> map) {
		List<?> list = list("fmInspt.QR_INSPT004_CTRCODE", map); //
		return list;
	}

	public List<?> fm_inspt004_mappinglist() {
		return list("fmInspt.QR_INSPT004_U");
	}

	public List<?> fmInspt004_service_M(Map map) {
		List<?> list = list("fmInspt.QR_INSPT004_SERVICE_M",map); //
		return list;
	}

	public List<?> fm_inspt004_popup(Map<String, String> map) {
		List<?> list = (List)list("fmInspt.QR_INSPT004_R",map);
		return list;
	}

	public void fm_inspt004_popup_mapping(Map map) {
		insert("fmInspt.QR_INSPT004_T",map);
	}

	public void fm_inspt004_update(Map map) {
		update("fmInspt.QR_INSPT004_M",map);
	}

	public EgovMap fm_inspt004_download(Map map) {
		return (EgovMap)select("fmInspt.QR_INSPT004_FILE", map);
	}

	public List<?> fm_inspt004_downloadList(Map map) {
		return list("fmInspt.QR_INSPT004_01_FILES", map);
	}

	public List<?> getInspt002MainList(Map map) {
		return list("fmInspt.QR_INSPT002_MAIN_LIST", map);
	}

	/* bcyCod, service, standard */
	public List<EgovMap> getSaWorkFileRelatedOtherService(Map map) {
		return (List<EgovMap>) list("fmInspt.getSaWorkFileRelatedOtherService", map);
	}

	public void fm_inspt001_insertMsrSaFile(Map map) {
		insert("fmInspt.QR_INSPT001_INSERTMSRSAFILE", map);
	}

	public void fm_inspt001_deleteMsrSaFile(Map map) {
		insert("fmInspt.QR_INSPT001_DELETEMSRSAFILE", map);
	}

	public List<EgovMap> getMeasureSaFile(Map map) {
		return (List<EgovMap>) list("fmInspt.getMeasureSaFile", map);
	}

	public List<EgovMap> getSaExcelData(String bcyCode, String depCode) {
		Map<String, String> map = new HashMap<>();
		map.put("bcyCode", bcyCode);
		map.put("depCode", depCode);
		return (List<EgovMap>) list("fmInspt.getSaExcelData", map);
	}

	public void updateWrkPrg(Map map) {
		update("fmInspt.updateWrkPrg", map);
	}

	public List<EgovMap> getSaZipList(Map<String, String> map) {
		return (List<EgovMap>) list("fmInspt.getSaZipList", map);
}

	public EgovMap getWorkByFile(int fileKey) {
		return (EgovMap) select("fmInspt.getWorkByFile", fileKey);
	}

    public List<EgovMap> fm_inspt008_compliance_list(SearchVO searchVO) {
		return (List<EgovMap>) list("fmInspt.QR_INSPT008_COMPLIANCE_LIST", searchVO);
    }

	public List<EgovMap> fm_inspt008_work_list(SearchVO searchVO) {
		return (List<EgovMap>) list("fmInspt.QR_INSPT008_WORK_LIST", searchVO);
	}

	public List<EgovMap> fm_inspt008_file_list(SearchVO searchVO) {
		return (List<EgovMap>) list("fmInspt.QR_INSPT008_FILE_LIST", searchVO);
	}
}
