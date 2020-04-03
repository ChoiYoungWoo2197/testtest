package com.uwo.isms.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.uwo.isms.domain.*;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface FMCompsService {
	public List<?> fm_comps001_list(SearchVO searchVO);

	public String fm_comps001_getManCylMax();

	public int fm_comps001_cnt(SearchVO searchVO);

	public void fm_comps001_update(CycleVO vo);

	public CycleVO fm_comps001_read(CycleVO vo);

	public void fm_comps001_write(CycleVO vo);

	public List<?> fm_comps001_list_new(SearchVO searchVO);

	public List<?> fm_comps002_list(SearchVO searchVO);

	public int fm_comps002_cnt(SearchVO searchVO);

	public EgovMap fm_comps002_stnd_info(String strStndCod);

	public String fm_comps002_stnd_insert(Map<String, Object> map);

	public String fm_comps002_stnd_update(Map<String, Object> map);

	public List<?> fm_comps002_org_list(String strStndCod);

	public EgovMap fm_comps002_org_info(Map<String, String> map);

	public String fm_comps002_org_insert(Map<String, String> map);

	public String fm_comps002_org_update(Map<String, String> map);

	List<?> fm_comps003_3D_list(Map map);

	List<?> fm_comps003_2D_list(Map map);

	List<?> fm_comps003_list(Map map);

	public void fm_comps003_insert(Map map);

	public void fm_comps003_update(Map map);

	public void fm_comps003_2D_insert(Map map);

	public void fm_comps003_2D_update(Map map);

	public void fm_comps003_3D_insert(Map map);

	public void fm_comps003_3D_update(Map map);

	public String fm_comps003_key(String key);

	public List<?> fm_comps003_popup(Map<String, String> map);

	public List<?> fm_comps003_popup_div();

	public void fm_comps003_popup_mapping(Map map);

	public List<?> fm_comps003_3D_mappinglist();

	public void fm_comps003_del_mapping(String brdKey);

	public BoardVO fm_comps003_popup_brdView(String key);

	public List<?> fm_comps003_popup_brdFile(String key);
/*
	public List<?> fm_comps003_stdlist();

	public List<?> fm_comps003_svclist();

	public String fm_comps003_excel_insert(HttpServletRequest req, List list) throws Exception;*/

	public List<?> fm_comps004(SearchVO searchVO);

	public int fm_comps004_cnt(SearchVO searchVO);

	public List<?> fm_comps004_workerList(String utdTrcKey);

	public int fm_comps004_sampleDoc_save(SampleDocVO sampleDocVO, List<FileVO> list);

	public void fm_comps004_standard_change(Map<String, Object> map);


	public void fm_comps004_worker_change(Map<String, Object> map);

	public void fm_comps004_work_delete(Map<String, Object> map);

	public SampleDocVO fm_comps004_work_insert(Map<String, Object> map) throws Exception;

	public SampleDocVO fm_comps004_sampleDocInfo(SearchVO searchVO);

	public List<?> fm_file(FileVO fvo);

	/*public void fm_comps004_worker_del(String[] id, String utmTrcKey);*/

	/*public void fm_comps004_work_del(String[] id, String utmTrcKey);*/

	public List<?> fm_comps004_getCtrMapList(SearchVO searchVO);

	public List<?> fm_comps004_GetDepth(SearchVO searchVO);

	public List<?> fm_comps005_getCntr1List(SearchVO searchVO);

	public List fm_comps005_getCntr2List(SearchVO searchVO);

	public List fm_comps005_getCntr3List(SearchVO searchVO);

	public List<?> fm_comps005_getStdList(SearchVO searchVO);

	public List fm_comps005_getCntr4List(SearchVO searchVO);

	public List fm_comps005_getCntr5List(SearchVO searchVO);

	public void fm_comps005_setMapping_save(Map<String, String> map);

	public List<?> fm_comps005_getMappingSampleDocList(SearchVO searchVO);

	public void fm_comps005_delMappingSampleDocList(SearchVO searchVO);

	public List<?> fm_comps005_getGuideList(String utcCtrKey);

	public String fm_comps005_getUcmCtrKey(SearchVO searchVO);

	public List<?> fm_comps005_popup(SearchVO searchVO);

	/*public void fm_comps004_mapping_del(Map<String, String> map);*/

	/*public void fmComps004_worker_save(Map<String, String> map);*/

	public List<?> fm_comps003_report();

	/*public void CreateWork(Map<String, String> map);*/

	public HashSet<String> CreateWork(SampleDocVO sampleDocVO, String[] userKey);

	public List<?> fm_comps003_grid(Map<String, String> map);

	public List<?> fm_comps004_member(SearchVO searchVO);

	public List<?> fm_comps004_dept(SearchVO searchVO);

	public EgovMap nonWorkYn(String completeDate);

	public List<?> fmComps003_service_M(Map<String, String> map);

	public void rm_comps003_service_update(Map<String, Object> map);

	public void rm_comps003_service_insert(Map<String, Object> map);

	/*
	 * 206-10-27
	 */
	public String fm_comps004_excel_insert(HttpServletRequest req) throws Exception;

	/*
	 * 2017-02-16
	 */
	public String fm_comps004_insert_work(HttpServletRequest req) throws Exception;

	/*
	 * 2017-05-19
	 */
	public void fm_comps004_updateWorkLastInsertDate(Map<String, Object> map);
	public void fm_comps004_change_work_delete(Map<String, Object> map);

	/*
	 * 2018-02-20, 업무 삭제
	 */
	public String fm_comps004_del_doc(HttpServletRequest req) throws Exception;

	/*
	 * 2018-03-21 s, 컴플라이언스 항목 삭제
	 */
	public String fm_comps003_del_comps(Map<String, String> map) throws Exception;

	/*
	 * 2018-07-17 s, 업무삭제
	 */
	public String fm_comps_del_work(HttpServletRequest req) throws Exception;

	/*
	 * 2020-04-02 서비스 동기화
	 */
	public void fm_comps004_updateSameService(Map<String, Object> map);

	public List<?> getComCodPrefix();

	public CntrVO getControlItem(int controlKey);

	public String getKindOfCompliance(String complianceCode);

	public List<EgovMap> getControlItemList(Map map);

	public List<EgovMap> getComplianceListPerServiceByCycleCode(String cycleCode);

	public String getSaServiceCode();

	public String getSaComplianceCode();


}
