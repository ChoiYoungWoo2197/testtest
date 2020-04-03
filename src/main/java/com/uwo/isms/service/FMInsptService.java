package com.uwo.isms.service;

import java.util.List;
import java.util.Map;

import com.uwo.isms.domain.BoardVO;
import com.uwo.isms.domain.FileVO;
import com.uwo.isms.domain.SearchVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.springframework.web.multipart.MultipartFile;

public interface FMInsptService {

	public List<?> fm_inspt001_list(Map<String, String> map);

	public List<?> fm_inspt001_list2(Map<String, String> map);

	public List<?> fm_inspt001_list3(Map<String, String> map);

	public List<?> fm_inspt001_getFileList(Map<String, String> map);

	public List<?> fm_inspt001_getSaFileList(Map<String, String> map);

	public List<?> fm_inspt001_getFileList_by_department_of_service(Map<String, String> map);

	public int fm_inspt001_setMsrFile(Map<String, String> map);

	public List<?> fm_inspt001_getStdList(SearchVO searchVO);

	public List<?> fm_inspt001_getCntr1List(SearchVO searchVO);

	public List fm_inspt001_getCntr2List(SearchVO searchVO);

	public List fm_inspt001_getCntr3List(SearchVO searchVO);

	public List fm_inspt001_getCntr4List(SearchVO searchVO);

	public List fm_inspt001_getCntr5List(SearchVO searchVO);

	public List<?> fm_inspt002_list(Map<String, String> map);

	public EgovMap fm_inspt002_info(Map<String, String> map);

	public void fm_inspt002_insert(Map<String, Object> map);

	public void fm_inspt002_update(Map<String, Object> map);

	void fm_inspt002_mng_update(Map<String, String> map, List<FileVO> list);

	List<?> fm_inspt002_mng_list(Map<String, String> map);

	EgovMap fm_inspt002_mng_info(Map<String, String> map);

	void fm_inspt002_apv_update(Map<String, Object> map);

	public String inspt001_view(String wKey);

	public void fmInspt001_viewUpdate(SearchVO searchVO);

	public List<?> fm_inspt002_report(Map<String, String> map);

	public List<?> fm_inspt002_report_mng(Map<String, String> map);

	public List<?> fm_inspt003_list(SearchVO searchVO);

	public List<?> fm_inspt004_list(Map<String, String> map);

	public List<?> fm_inspt004_report(Map<String, String> map);

	public List<?> fm_inspt004_getCtrCode(Map<String, String> map);

	public List<?> fm_inspt004_mappinglist();

	public List<?> fmInspt004_service_M(Map<String, String> map);

	public List<?> fm_inspt004_popup(Map<String, String> map);

	public void fm_inspt004_popup_mapping(Map map);

	public void fm_inspt004_update(Map map);

	public EgovMap fm_inspt004_download(Map map);

	public List<?> fm_inspt004_downloadList(Map map);

	public String fm_inspt004_createZip(List<EgovMap> list) throws Exception;

	public String fm_inspt004_createZip_with_sa(List<EgovMap> list, List<EgovMap> saFileList) throws Exception;

	public List<?> getInspt002MainList(Map map);

	public List<EgovMap> getSaWorkFileRelatedOtherService(Map map);

	public void fm_inspt001_insertMsrSaFile(Map map);

	public void fm_inspt001_deleteMsrSaFile(Map map);

	public List<EgovMap> getMeasureSaFile(Map map);

    List<EgovMap> getSaExcelData(String bcyCode, String depCode);

    void updateWrkPrg(Map map);

	List<EgovMap> getSaZipList(Map<String, String> map);

	public List<?> fm_inspt001_getFileList2(Map<String, String> map);

	public boolean isFileOfCompletedWork(int fileKey);

	public List<EgovMap> fm_inspt008_compliance_list(SearchVO searchVO);

	public List<EgovMap> fm_inspt008_work_list(SearchVO searchVO);

	List<EgovMap> fm_inspt008_file_list(SearchVO searchVO);

	public void fm_inspt006_excel_upload(MultipartFile file, SearchVO searchVO) throws Exception;

	public void fm_inspt006_zip_upload(MultipartFile file, SearchVO searchVO) throws Exception;
}
