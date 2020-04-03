package com.uwo.isms.service;

import java.util.List;
import java.util.Map;

import com.uwo.isms.domain.BoardVO;
import com.uwo.isms.domain.FileVO;
import com.uwo.isms.domain.IFP_DTL_VO;
import com.uwo.isms.domain.IFP_MTR_VO;
import com.uwo.isms.domain.SearchVO;
import com.uwo.isms.domain.WorkVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface FMMworkService {

	public List<?> fm_file(FileVO fvo);

	public List<?> fm_mwork001_list(int iMonth);

	public int fm_mwork001_cnt(SearchVO searchVO);

	public List<?> fm_mwork009_list(SearchVO searchVO);

	public int fm_mwork009_cnt(SearchVO searchVO);

	public void fm_mwork009_write(BoardVO vo, List<FileVO> list);

	public BoardVO fm_mwork009_read(BoardVO vo);

	public void fm_mwork009_update(BoardVO vo, List<FileVO> list);

	public void fm_mwork009_delete(BoardVO vo);

	public List<?> fm_mwork010_list(SearchVO searchVO);

	public int fm_mwork010_cnt(SearchVO searchVO);

	public void fm_mwork010_write(BoardVO vo, List<FileVO> list);

	public BoardVO fm_mwork010_read(BoardVO vo);

	public void fm_mwork010_update(BoardVO vo, List<FileVO> list);

	public void fm_mwork010_delete(BoardVO vo);

	public List<?> fm_mwork011_list(SearchVO searchVO);

	public int fm_mwork011_cnt(SearchVO searchVO);

	public void fm_mwork011_write(BoardVO vo, List<FileVO> list);

	public BoardVO fm_mwork011_read(BoardVO vo);

	public void fm_mwork011_update(BoardVO vo, List<FileVO> list);

	public void fm_mwork011_delete(BoardVO vo);

	public List<?> fm_mwork014_list(SearchVO searchVO);

	public List<?> fm_mwork008_list(SearchVO searchVO);

	public List<?> getWorkCount(SearchVO searchVO);

	public List<?> getWorkDCount(SearchVO searchVO);

	public List<?> getNoWorkList(SearchVO searchVO);

	public List<?> getComWorkList(SearchVO searchVO);

	public List<?> getNamWorkCount(SearchVO searchVO);

	public List<?> getCalendarYear();

	public List<?> getCalendarList(Map map);

	public List<?> getWorkList(SearchVO searchVO);

	public List<?> getWorkList(Map map);

	public WorkVO getWorkState(String utwWrkKey);

	public List<?> getCntrList(String utwTrcKey);

	public int fm_mwork_workSave(WorkVO workVO, List<FileVO> list);

	public List<?> fm_mwork013_getWorkAppList(SearchVO searchVO);

	public int fm_mwork013_getWorkAppCnt(SearchVO searchVO);

	public int fm_mwork_stateUpdate(WorkVO workVO);

	public int fm_mwork_del(String workKey);

	public List<?> getWorkerist(SearchVO searchVO);

	public List<?> fm_mwork002_agnIdList(SearchVO searchVO);

	public void fm_mwork002_setAgnId(SearchVO searchVO);

	public void fm_mwork006_setWrkId(SearchVO searchVO);

	public void fm_mwork013_appStateUpdate(WorkVO workVO);

	public List<?> ifpMtrList(Map map);

	public EgovMap ifp_Mtr_popup(String mtrKey);

	public void saveIfpMtr(IFP_MTR_VO ifpMtrVo);

	public void modifyIfpMtr(IFP_MTR_VO ifpMtrVo);

	public void ifpDel(Map map);

	public List<?> ifpDtlList(String mtrKey);

	public EgovMap ifp_Dtl_popup(String dtlKey);

	public void saveIfpDtl(IFP_DTL_VO ifpDtlVo);

	public void modifyIfpDtl(IFP_DTL_VO ifpDtlVo);

	public void eraseIfpDtl(IFP_DTL_VO ifpDtlVo);

	public void ifpDel2(Map map);

	public List<?> fm_mwork012_list(SearchVO searchVO);

	public int fm_mwork012_cnt(SearchVO searchVO);

	public List<?> fm_mwork014_list_nowork(Map map);

	public String fm_mwork014_get_celnum(String string);

	public void fm_mwork014_send_sms(Map map);

	public List<?> fm_mwork018_list(SearchVO searchVO);

	public List<?> fmMwork004_List(SearchVO searchVO);

	public List<?> get_stdlist();

	public List<?> fmMwork005_List(SearchVO searchVO);

	public List<?> fmMwork005_termList(SearchVO searchVO);

	public List<?> getDeptUserList(String usrKey);

	public List<?> getWorkMapKey(Map userMap);

	public void changeWork(Map userMap);

	public void changeWorkMap(Map userMap);

	public List<?> getDeptList(Map<String, String> map);

	public int fmMwork005_termCount(SearchVO searchVO);

	public List<?> getAllWorkList(SearchVO searchVO);

	public int getAllWorkListCnt(SearchVO searchVO);

	public int getDelayCnt(String usrKey);

	public int getCompCnt(String usrKey);

	public int getNoworkCnt(String usrKey);

	public String getUserName(String usrKey);

	public List<?> getworklist(SearchVO searchVO);

	public int getworklist_count(SearchVO searchVO);

	public List<?> getAllWorkList(Map map);

	public List<?> getSimilarWorkList(Map map);

	public List<?> getAllWorkList_file(Map map);

	public void batchUpload(Map map, List<FileVO> list);

	public List<?> fmMwork005_t(Map<String, String> map);

	public EgovMap fmMwork006_trs_info(String addList);

	public String fmMwork006_reqTransfer(Map<String, String> map);

	public List<?> getUsrList(Map<String, String> map);

	public String fmMwork005_app(Map<String, String> map);

	public String fmMwork005_rtn(Map<String, String> map);

	public List<?> getBcyList(String mngKey, String usrKey);

	public String getWorkKey(String utwTrcKey, String usrKey);

	public String getMngKey(Map<String, String> map);

	public String getWorkId(Map<String, String> map);

	public List<?> selectWorkListByTrcKey(String trcKey);

	public List<?> selectTransferListByAppKey(String appKey);

	/**
	 * 2018-05-11 s, 결재정보
	 * @param appKey
	 * @return
	 */
	WorkVO selectAppByAppKey(String appKey);

	public String fm_mwork006_createZip(List<EgovMap> list, SearchVO searchVO) throws Exception;
}
