package com.uwo.isms.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;

import com.uwo.isms.common.CommonConfig;
import com.uwo.isms.service.FMCommonService;
import com.uwo.isms.util.EgovStringUtil;
import egovframework.rte.fdl.property.EgovPropertyService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uwo.isms.dao.FMMworkDAO;
import com.uwo.isms.service.FMMworkService;
import com.uwo.isms.domain.ApprMapVO;
import com.uwo.isms.domain.BoardVO;
import com.uwo.isms.domain.FileVO;
import com.uwo.isms.domain.IFP_DTL_VO;
import com.uwo.isms.domain.IFP_MTR_VO;
import com.uwo.isms.domain.SearchVO;
import com.uwo.isms.domain.WorkVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;


@Service("fmMworkService")
public class FMMworkServiceImpl implements FMMworkService {

	Logger log = LogManager.getLogger(FMMworkServiceImpl.class);

	@Resource(name="fmMworkDAO")
	private FMMworkDAO fmMworkDAO;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;

	@Autowired
	protected FMCommonService commonService;

	@Override
	public List<?> fm_file(FileVO fvo) {
		return fmMworkDAO.fm_file(fvo);
	}


	@Override
	public List<?> fm_mwork001_list(int iMonth) {
		return fmMworkDAO.fm_mwork001_list(iMonth);
	}

	@Override
	public int fm_mwork001_cnt(SearchVO searchVO) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public List<?> fm_mwork009_list(SearchVO searchVO) {
		return fmMworkDAO.fm_mwork009_list(searchVO);
	}

	@Override
	public int fm_mwork009_cnt(SearchVO searchVO) {
		return fmMworkDAO.fm_mwork009_cnt(searchVO);
	}

	@Override
	public void fm_mwork009_write(BoardVO vo, List<FileVO> list) {
		fmMworkDAO.fm_mwork009_write(vo,list);
	}

	@Override
	public BoardVO fm_mwork009_read(BoardVO vo) {
		return fmMworkDAO.fm_mwork009_read(vo);
	}

	@Override
	public void fm_mwork009_update(BoardVO vo, List<FileVO> list) {
		fmMworkDAO.fm_mwork009_update(vo,list);
	}

	@Override
	public void fm_mwork009_delete(BoardVO vo) {
		fmMworkDAO.fm_mwork009_delete(vo);
	}

	@Override
	public List<?> fm_mwork010_list(SearchVO searchVO) {
		return fmMworkDAO.fm_mwork010_list(searchVO);
	}

	@Override
	public int fm_mwork010_cnt(SearchVO searchVO) {
		return fmMworkDAO.fm_mwork010_cnt(searchVO);
	}

	@Override
	public void fm_mwork010_write(BoardVO vo, List<FileVO> list) {
		fmMworkDAO.fm_mwork010_write(vo,list);
	}

	@Override
	public BoardVO fm_mwork010_read(BoardVO vo) {
		return fmMworkDAO.fm_mwork010_read(vo);
	}

	@Override
	public void fm_mwork010_update(BoardVO vo, List<FileVO> list) {
		fmMworkDAO.fm_mwork010_update(vo,list);
	}

	@Override
	public void fm_mwork010_delete(BoardVO vo) {
		fmMworkDAO.fm_mwork010_delete(vo);
	}

	@Override
	public List<?> fm_mwork011_list(SearchVO searchVO) {
		return fmMworkDAO.fm_mwork011_list(searchVO);
	}

	@Override
	public int fm_mwork011_cnt(SearchVO searchVO) {
		return fmMworkDAO.fm_mwork011_cnt(searchVO);
	}

	@Override
	public void fm_mwork011_write(BoardVO vo, List<FileVO> list) {
		fmMworkDAO.fm_mwork011_write(vo,list);
	}

	@Override
	public BoardVO fm_mwork011_read(BoardVO vo) {
		return fmMworkDAO.fm_mwork011_read(vo);
	}

	@Override
	public void fm_mwork011_update(BoardVO vo, List<FileVO> list) {
		fmMworkDAO.fm_mwork011_update(vo,list);
	}

	@Override
	public void fm_mwork011_delete(BoardVO vo) {
		fmMworkDAO.fm_mwork011_delete(vo);
	}

	@Override
	public List<?> fm_mwork014_list(SearchVO searchVO) {
		return fmMworkDAO.fm_mwork014_list(searchVO);
	}


	@Override
	public List<?> fm_mwork014_list_nowork(Map map) {
		return fmMworkDAO.fm_mwork014_list_nowork(map);
	}

	@Override
	public List<?> fm_mwork008_list(SearchVO searchVO) {
		return fmMworkDAO.fm_mwork008_list(searchVO);
	}

	@Override
	public List<?> getWorkCount(SearchVO searchVO) {
		return fmMworkDAO.fm_mwork002_006_getWorkCount(searchVO);
	}

	@Override
	public List<?> getWorkDCount(SearchVO searchVO) {
		return fmMworkDAO.fm_mwork002_006_getWorkDCount(searchVO);
	}

	@Override
	public List<?> getNoWorkList(SearchVO searchVO) {
		return fmMworkDAO.fm_mwork002_006_getNoWorkList(searchVO);
	}

	@Override
	public List<?> getComWorkList(SearchVO searchVO) {
		return fmMworkDAO.fm_mwork002_006_getComWorkList(searchVO);
	}

	@Override
	public List<?> getNamWorkCount(SearchVO searchVO) {
		return fmMworkDAO.fm_mwork002_006_getNamWorkCount(searchVO);
	}

	@Override
	public List<?> getCalendarYear() {
		return fmMworkDAO.getCalendarYear();
	}

	@Override
	public List<?> getCalendarList(Map map) {
		return fmMworkDAO.getCalendarList(map);
	}

	@Override
	public List<?> getWorkList(SearchVO searchVO) {
		return fmMworkDAO.fm_mwork003_007_getWorkList(searchVO);
	}

	@Override
	public List<?> getWorkList(Map map) {
		return fmMworkDAO.getWorkList(map);
	}

	@Override
	public WorkVO getWorkState(String utwWrkKey) {
		return fmMworkDAO.getWorkState(utwWrkKey);
	}

	@Override
	public List<?> getCntrList(String utwTrcKey) {
		return fmMworkDAO.getCntrList(utwTrcKey);
	}

	@Override
	public int fm_mwork_workSave(WorkVO workVO, List<FileVO> list) {
		return fmMworkDAO.fm_mworkSave(workVO, list);
	}

	@Override
	public List<?> fm_mwork013_getWorkAppList(SearchVO searchVO) {
		return fmMworkDAO.fm_mwork013_getWorkAppList(searchVO);
	}

	@Override
	public int fm_mwork013_getWorkAppCnt(SearchVO searchVO) {
		return fmMworkDAO.fm_mwork013_getWorkAppCnt(searchVO);
	}

	@Override
	public int fm_mwork_stateUpdate(WorkVO workVO) {
		return fmMworkDAO.fm_mwork_stateUpdate(workVO);
	}

	@Override
	public int fm_mwork_del(String workKey) {
		return fmMworkDAO.fm_mwork_del(workKey);
	}

	@Override
	public List<?> getWorkerist(SearchVO searchVO) {
		return fmMworkDAO.getWorkerist(searchVO);
	}

	@Override
	public List<?> fm_mwork002_agnIdList(SearchVO searchVO) {
		return fmMworkDAO.fm_mwork002_agnIdList(searchVO);
	}

	@Override
	public List<?> getAllWorkList(SearchVO searchVO) {
		return fmMworkDAO.getAllWorkerist(searchVO);
	}

	@Override
	public int getAllWorkListCnt(SearchVO searchVO) {
		return fmMworkDAO.getAllWorkeristCnt(searchVO);
	}

	@Override
	public void fm_mwork002_setAgnId(SearchVO searchVO) {
		fmMworkDAO.fm_mwork002_setAgnId(searchVO);
	}

	@Override
	public void fm_mwork006_setWrkId(SearchVO searchVO) {
		fmMworkDAO.fm_mwork006_setWrkId(searchVO);
	}

	@Override
	public void fm_mwork013_appStateUpdate(WorkVO workVO) {
		fmMworkDAO.fm_mwork013_appStateUpdate(workVO);
	}

	@Override
	public List<?> ifpMtrList(Map map) {
		return fmMworkDAO.ifpMtrList(map);
	}

	@Override
	public EgovMap ifp_Mtr_popup(String mtrKey) {
		return fmMworkDAO.ifp_Mtr_popup(mtrKey);
	}

	@Override
	public void saveIfpMtr(IFP_MTR_VO ifpMtrVo) {
		fmMworkDAO.saveIfpMtr(ifpMtrVo);
	}

	@Override
	public void modifyIfpMtr(IFP_MTR_VO ifpMtrVo) {
		fmMworkDAO.modifyIfpMtr(ifpMtrVo);
	}

	@Override
	public void ifpDel(Map map) {
		fmMworkDAO.ifpDel(map);
	}

	@Override
	public List<?> ifpDtlList(String mtrKey) {
		return fmMworkDAO.ifpDtlList(mtrKey);
	}

	@Override
	public EgovMap ifp_Dtl_popup(String dtlKey) {
		return fmMworkDAO.ifp_Dtl_popup(dtlKey);
	}

	@Override
	public void saveIfpDtl(IFP_DTL_VO ifpDtlVo) {
		fmMworkDAO.saveIfpDtl(ifpDtlVo);
	}

	@Override
	public void modifyIfpDtl(IFP_DTL_VO ifpDtlVo) {
		fmMworkDAO.modifyIfpDtl(ifpDtlVo);
	}

	@Override
	public void eraseIfpDtl(IFP_DTL_VO ifpDtlVo) {
		fmMworkDAO.eraseIfpDtl(ifpDtlVo);
	}

	@Override
	public void ifpDel2(Map map) {
		fmMworkDAO.ifpDel2(map);
	}

	@Override
	public List<?> fm_mwork012_list(SearchVO searchVO) {
		return fmMworkDAO.fm_mwork012_list(searchVO);
	}

	@Override
	public int fm_mwork012_cnt(SearchVO searchVO) {
		return fmMworkDAO.fm_mwork012_cnt(searchVO);
	}

	@Override
	public String fm_mwork014_get_celnum(String string) {
		return fmMworkDAO.fm_mwork014_get_celnum(string);
	}

	@Override
	public void fm_mwork014_send_sms(Map map) {
		fmMworkDAO.fm_mwork014_send_sms(map);
	}

	@Override
	public List<?> fm_mwork018_list(SearchVO searchVO) {
		return fmMworkDAO.fm_mwork018_list(searchVO);
	}

	@Override
	public List<?> fmMwork004_List(SearchVO searchVO) {
		return fmMworkDAO.fmMwork004_List(searchVO);
	}

	@Override
	public List<?> get_stdlist() {
		return fmMworkDAO.get_stdlist();
	}

	@Override
	public List<?> fmMwork005_List(SearchVO searchVO) {
		return fmMworkDAO.fmMwork005_List(searchVO);
	}

	@Override
	public List<?> fmMwork005_termList(SearchVO searchVO) {
		return fmMworkDAO.fmMwork005_termList(searchVO);
	}

	@Override
	public List<?> getDeptUserList(String usrKey) {
		return fmMworkDAO.getDeptUserList(usrKey);
	}

	@Override
	public List<?> getWorkMapKey(Map userMap) {
		return fmMworkDAO.getWorkMapKey(userMap);
	}

	@Override
	public void changeWork(Map userMap) {
		fmMworkDAO.changeWork(userMap);
	}

	@Override
	public void changeWorkMap(Map userMap) {
		fmMworkDAO.changeWorkMap(userMap);
	}

	@Override
	public List<?> getDeptList(Map<String, String> map) {
		return fmMworkDAO.getDeptList(map);
	}

	@Override
	public int fmMwork005_termCount(SearchVO searchVO) {
		int cnt = fmMworkDAO.fmMwork005_termCount(searchVO);
		return cnt;
	}


	@Override
	public int getDelayCnt(String usrKey) {
		int cnt = fmMworkDAO.getDelayCnt(usrKey);
		return cnt;
	}


	@Override
	public int getCompCnt(String usrKey) {
		int cnt = fmMworkDAO.getCompCnt(usrKey);
		return cnt;
	}


	@Override
	public int getNoworkCnt(String usrKey) {
		int cnt = fmMworkDAO.getNoworkCnt(usrKey);
		return cnt;
	}


	@Override
	public String getUserName(String usrKey) {
		return fmMworkDAO.getUserName(usrKey);
	}

	@Override
	public List<?> getworklist(SearchVO searchVO) {
		return fmMworkDAO.fm_mwork019_getworklist(searchVO);
	}


	@Override
	public int getworklist_count(SearchVO searchVO) {
		return fmMworkDAO.getworklist_count(searchVO);
	}


	@Override
	public List<?> getAllWorkList(Map map) {
		return fmMworkDAO.getAllWorkList(map);
	}


	@Override
	public List<?> getAllWorkList_file(Map map) {
		return fmMworkDAO.getAllWorkList_file(map);
	}

	@Override
	public List<?> getSimilarWorkList(Map map) {
		return fmMworkDAO.getSimilarWorkList(map);
	}

	@Override
	public void batchUpload(Map map, List<FileVO> list) {
		fmMworkDAO.batchUpload(map,list);
	}


	@Override
	public List<?> fmMwork005_t(Map<String, String> map) {
		return fmMworkDAO.fmMwork005_t(map);
	}


	@Override
	public String fmMwork005_app(Map<String, String> map) {
		String[] arrWrkKey = map.get("wrkKey").split("\\,");
		for(String wrkKey : arrWrkKey) {
			fmMworkDAO.fmMwork005_appWrk(wrkKey);
			fmMworkDAO.fmMwork005_appApp(wrkKey);
		}
		return null;
	}


	@Override
	public String fmMwork005_rtn(Map<String, String> map) {
		String[] arrWrkKey = map.get("wrkKey").split("\\,");
		for(String wrkKey : arrWrkKey) {
			fmMworkDAO.fmMwork005_rtnWrk(wrkKey);
			fmMworkDAO.fmMwork005_rtnApp(wrkKey);
		}
		return null;
	}

	@Override
	public EgovMap fmMwork006_trs_info(String addList) {
		return fmMworkDAO.fmMwork006_trs_info(addList);
	}

	@Override
	public String fmMwork006_reqTransfer(Map<String, String> map) {

		try {
			// wrkKeys
			String[] addList = map.get("addList").split("\\,");

			// 1. set ApprMapVO
			ApprMapVO apprMapVO = new ApprMapVO();
			// 업무담당자의 결재자 정보 갖고오기
			EgovMap approver = fmMworkDAO.selectApprovalByWrkId(map.get("usrKey").toString());

			// 2017-03-09, 결재자가 없으면 에러처리
			if (approver == null || approver.get("uumApvOne") == null) {
				return "9";
			}

			apprMapVO.setUam_wrk_key(addList[0]);
			apprMapVO.setUam_cf1_id(approver.get("uumApvOne").toString());

			// 2018-05-11 s, 2차
			apprMapVO.setUam_cf2_id(approver.get("uumApvTwo").toString());

			apprMapVO.setUam_app_gbn("2");	// 2:대무자 승인결재
			// 2018-05-11 s, 2차 결재로 변경
			apprMapVO.setUam_app_lev("2");	// 1:1차결재
			apprMapVO.setUam_sta_cod("11");	// 11:업무이관요청
			apprMapVO.setUam_req_id(map.get("usrKey"));
			apprMapVO.setUam_rgt_id(map.get("uptId"));

			// 2. 1차 결재자에게 상신
			//String appKey = fmMworkDAO.selectSeqAppMtr();
			//apprMapVO.setUam_app_key(appKey);
			String appKey = fmMworkDAO.insertApprWork(apprMapVO);

			// 3. INSERT trs_map
			// app_key, wrk_key, wrk_id
			Map<String, String> map2 = new HashMap<String, String>();
			map2.put("appKey", appKey);
			map2.put("wrkId", map.get("angKey"));
			for (int i = 0; i < addList.length; i++) {
				map2.put("wrkKey", addList[i]);
				fmMworkDAO.insertTransferMap(map2);
			}
		}
		catch (Exception e) {
			return "1";
		}

		return "0";
	}


	@Override
	public List<?> getUsrList(Map<String, String> map) {
		// TODO Auto-generated method stub
		return fmMworkDAO.getUsrList(map);
	}


	@Override
	public List<?> getBcyList(String mngKey, String usrKey) {
		Map map = new HashMap();
		map.put("mngKey", mngKey);
		map.put("usrKey", usrKey);
		return fmMworkDAO.getBcyList(map);
	}


	@Override
	public String getWorkKey(String utwTrcKey, String usrKey) {
		Map map = new HashMap();
		map.put("trcKey", utwTrcKey);
		map.put("usrKey", usrKey);
		return fmMworkDAO.getWorkKey(map);
	}

	@Override
	public String getMngKey(Map<String, String> map) {
		return fmMworkDAO.getMngKey(map);
	}

	@Override
	public String getWorkId(Map<String, String> map) {
		return fmMworkDAO.getWorkId(map);
	}

	@Override
	public List<?> selectWorkListByTrcKey(String trcKey) {
		return fmMworkDAO.selectWorkListByTrcKey(trcKey);
	}

	@Override
	public List<?> selectTransferListByAppKey(String appKey) {
		return fmMworkDAO.selectTransferListByAppKey(appKey);
	}


	/**
	 * 2018-05-11 s, 결재정보
	 * @param appKey
	 * @return
	 */
	@Override
	public WorkVO selectAppByAppKey(String appKey) {
		return fmMworkDAO.selectAppByAppKey(appKey);
	}

	public String fm_mwork006_createZip(List<EgovMap> list, SearchVO searchVO) throws Exception {

		Map<String, String> map = new HashMap<>();
		map.put("bcyCod", searchVO.getManCyl());
		List<EgovMap> originWorkFiles = (List<EgovMap>) commonService.getWorkFiles(map);

		Map<Integer, ArrayList<EgovMap>> workFiles = new HashMap<>();
		for (EgovMap egovMap : originWorkFiles) {
			int workCode = Integer.parseInt((String) egovMap.get("umfConKey"));
			if ( ! workFiles.containsKey(workCode)) {
				workFiles.put(workCode, new ArrayList<EgovMap>());
			}
			List<EgovMap> fileList = workFiles.get(workCode);
			fileList.add(egovMap);
		}

		byte[] buffer = new byte[1024];
		ArrayList<String> nameList = new ArrayList<String>();

		String output = propertyService.getString("uploadpath")
				+ File.separator
				+ RandomStringUtils.randomAlphanumeric(10)
				+ ".zip";

		FileOutputStream fos = new FileOutputStream(output);
		ZipOutputStream zos = new ZipOutputStream(fos);

		for (int i = 0; i < list.size(); i++) {

			EgovMap entry = (EgovMap) list.get(i);

			String serviceName = (String) entry.get("utwSvcCod");
			int workCode = ((BigDecimal) entry.get("utwWrkKey")).intValue();
			String workName = (String) entry.get("utdDocNam");
			String work = workCode + "_" + workName;
			List<EgovMap> fileList = workFiles.get(workCode);

			String zipPath = serviceName + File.separator + work + File.separator;

			String zipName = zipPath;

			if (fileList == null) {
				continue;
			}

			for (EgovMap file: fileList) {
				String fileName = (String) file.get("umfConFnm");
				zipName = zipPath;
				zipName += fileName;
				String filePath = file.get("umfSvrPth").toString() + File.separator + file.get("umfSvrFnm").toString();

				ZipEntry ze = new ZipEntry(zipName);

				try {
					zos.putNextEntry(ze);
				} catch (ZipException exception) {
					log.warn("duplicate zip entry : " + zipName);
				}

				if (filePath != null) {
					File f = new File(filePath);
					if (f.exists()) {
						FileInputStream in = new FileInputStream(filePath);

						int len;
						while ((len = in.read(buffer)) > 0) {
							zos.write(buffer, 0, len);
						}
						in.close();
					}
				}
			}

		}
		zos.closeEntry();
		//remember close it
		zos.close();
		return output;
	}
}
