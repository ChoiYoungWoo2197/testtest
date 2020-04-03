package com.uwo.isms.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.uwo.isms.common.CommonConfig;
import com.uwo.isms.common.EventTitleMessage;
import com.uwo.isms.domain.ApprMapVO;
import com.uwo.isms.domain.BoardVO;
import com.uwo.isms.domain.FileVO;
import com.uwo.isms.domain.IFP_DTL_VO;
import com.uwo.isms.domain.IFP_MTR_VO;
import com.uwo.isms.domain.SampleDocVO;
import com.uwo.isms.domain.SearchVO;
import com.uwo.isms.domain.WorkVO;
import com.uwo.isms.service.CommonUtilService;
import com.uwo.isms.service.SendMailService;
import com.uwo.isms.util.EgovDateUtil;
import com.uwo.isms.web.FMCompsController;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Repository("fmMworkDAO")
public class FMMworkDAO extends EgovAbstractDAO  {

	@Resource(name = "sendMailService")
	SendMailService sendMailService;

	@Autowired
	private CommonUtilService commonUtilService;

	Logger log = LogManager.getLogger(FMCompsController.class);

	public List<?> fm_file(FileVO fvo) {
		return list("fileup.QR_FILE_LIST",fvo);
	}

	public List<?> fm_mwork001_list(int iMonth) {
		List<?> list = list("fmMwork.QR_MWORK001_A",iMonth);	//
		return list;
	}

	public int fm_mwork001_cnt(SearchVO searchVO) {
		return (Integer)select("fmMwork.QR_MWORK001_B", searchVO);
	}


	public List<?> fm_mwork009_list(SearchVO searchVO) {
		List<?> list = list("fmMwork.QR_MWORK009_A", searchVO); //
		return list;
	}

	public int fm_mwork009_cnt(SearchVO searchVO) {
		return (Integer) select("fmMwork.QR_MWORK009_B", searchVO);
	}

	public void fm_mwork009_write(BoardVO vo, List<FileVO> list) {
		int key = (Integer)select("fmMwork.QR_MWORK009_H");
		vo.setUbm_brd_key(key);
		insert("fmMwork.QR_MWORK009_C", vo);
		for (int i = 0; i < list.size(); i++) {
			FileVO fvo = list.get(i);
			fvo.setUmf_con_key(String.valueOf(key));
			insert("fileup.QR_FILEUP001_A", fvo);
		}
	}

	public BoardVO fm_mwork009_read(BoardVO vo) {
		//update("fmMwork.QR_MWORK009_G", vo);
		return (BoardVO) select("fmMwork.QR_MWORK009_D", vo);
	}

	public void fm_mwork009_update(BoardVO vo, List<FileVO> list) {

		update("fmMwork.QR_MWORK009_E", vo);
		for (int i = 0; i < list.size(); i++) {
			FileVO fvo = list.get(i);
			fvo.setUmf_con_key(String.valueOf(vo.getUbm_brd_key()));
			insert("fileup.QR_FILEUP001_A", fvo);
		}
	}

	public void fm_mwork009_delete(BoardVO vo) {
		update("fmMwork.QR_MWORK009_F", vo);

		FileVO fileVO = new FileVO();
		fileVO.setUmf_tbl_gbn("BRD");
		fileVO.setUmf_con_gbn("1");
		fileVO.setUmf_con_key(Integer.toString(vo.getUbm_brd_key()));
		update("fileup.QR_FILE_DELETE_BYCON", fileVO);
	}

	public List<?> fm_mwork010_list(SearchVO searchVO) {
		List<?> list = list("fmMwork.QR_MWORK010_A", searchVO); //
		return list;
	}

	public int fm_mwork010_cnt(SearchVO searchVO) {
		return (Integer) select("fmMwork.QR_MWORK010_B", searchVO);
	}

	public void fm_mwork010_write(BoardVO vo, List<FileVO> list) {
		int key = (Integer)select("fmMwork.QR_MWORK010_H");
		vo.setUbm_brd_key(key);
		insert("fmMwork.QR_MWORK010_C", vo);
		for (int i = 0; i < list.size(); i++) {
			FileVO fvo = list.get(i);
			fvo.setUmf_con_key(String.valueOf(key));
			insert("fileup.QR_FILEUP001_A", fvo);
		}
	}

	public BoardVO fm_mwork010_read(BoardVO vo) {
		//update("fmMwork.QR_MWORK010_G", vo);
		return (BoardVO) select("fmMwork.QR_MWORK010_D", vo);
	}

	public void fm_mwork010_update(BoardVO vo, List<FileVO> list) {

		update("fmMwork.QR_MWORK010_E", vo);
		for (int i = 0; i < list.size(); i++) {
			FileVO fvo = list.get(i);
			fvo.setUmf_con_key(String.valueOf(vo.getUbm_brd_key()));
			insert("fileup.QR_FILEUP001_A", fvo);
		}
	}

	public void fm_mwork010_delete(BoardVO vo) {
		update("fmMwork.QR_MWORK010_F", vo);

		FileVO fileVO = new FileVO();
		fileVO.setUmf_tbl_gbn("BRD");
		fileVO.setUmf_con_gbn("2");
		fileVO.setUmf_con_key(Integer.toString(vo.getUbm_brd_key()));
		update("fileup.QR_FILE_DELETE_BYCON", fileVO);
	}

	public List<?> fm_mwork011_list(SearchVO searchVO) {
		List<?> list = list("fmMwork.QR_MWORK011_A", searchVO); //
		return list;
	}

	public int fm_mwork011_cnt(SearchVO searchVO) {
		return (Integer) select("fmMwork.QR_MWORK011_B", searchVO);
	}

	public void fm_mwork011_write(BoardVO vo, List<FileVO> list) {
		int key = (Integer)select("fmMwork.QR_MWORK011_H");
		vo.setUbm_brd_key(key);
		insert("fmMwork.QR_MWORK011_C", vo);
		for (int i = 0; i < list.size(); i++) {
			FileVO fvo = list.get(i);
			fvo.setUmf_con_key(String.valueOf(key));
			insert("fileup.QR_FILEUP001_A", fvo);
		}
	}

	public BoardVO fm_mwork011_read(BoardVO vo) {
		//update("fmMwork.QR_MWORK011_G", vo);
		return (BoardVO) select("fmMwork.QR_MWORK011_D", vo);
	}

	public void fm_mwork011_update(BoardVO vo, List<FileVO> list) {

		update("fmMwork.QR_MWORK011_E", vo);
		for (int i = 0; i < list.size(); i++) {
			FileVO fvo = list.get(i);
			fvo.setUmf_con_key(String.valueOf(vo.getUbm_brd_key()));
			insert("fileup.QR_FILEUP001_A", fvo);
		}
	}

	public void fm_mwork011_delete(BoardVO vo) {
		update("fmMwork.QR_MWORK011_F", vo);

		FileVO fileVO = new FileVO();
		fileVO.setUmf_tbl_gbn("BRD");
		fileVO.setUmf_con_gbn("7");
		fileVO.setUmf_con_key(Integer.toString(vo.getUbm_brd_key()));
		update("fileup.QR_FILE_DELETE_BYCON", fileVO);
	}

	public List<?> fm_mwork014_list(SearchVO searchVO) {
		List<?> list = list("fmMwork.QR_MWORK014_A",searchVO); //
		return list;
	}


	public List<?> fm_mwork014_list_nowork(Map map) {
		List<?> list = list("fmMwork.QR_MWORK014_B",map); //
		return list;
	}

	public List<?> fm_mwork008_list(SearchVO searchVO) {
		List<?> list = list("fmMwork.QR_MWORK008_A", searchVO); //
		return list;
	}

	public List<?> fm_mwork002_006_getWorkCount(SearchVO searchVO) {
		List<?> list = list("fmMwork.QR_MWORK002_A", searchVO); //
		return list;
	}

	public List<?> fm_mwork002_006_getNoWorkList(SearchVO searchVO) {
		List<?> list = list("fmMwork.QR_MWORK002_B", searchVO); //
		return list;
	}

	public List<?> fm_mwork002_006_getComWorkList(SearchVO searchVO) {
		List<?> list = list("fmMwork.QR_MWORK002_C", searchVO); //
		return list;
	}

	public List<?> fm_mwork002_006_getNamWorkCount(SearchVO searchVO) {
		List<?> list = list("fmMwork.QR_MWORK002_D", searchVO); //
		return list;
	}

	public List<?> fm_mwork002_006_getWorkDCount(SearchVO searchVO) {
		List<?> list = list("fmMwork.QR_MWORK002_G", searchVO); //
		return list;
	}

	public List<?> getAllWorkerist(SearchVO searchVO) {
		List<?> list = list("fmMwork.QR_MWORK002_H", searchVO);
		return list;
	}

	public int getAllWorkeristCnt(SearchVO searchVO) {
		return (Integer) select("fmMwork.QR_MWORK002_I", searchVO);
	}

	public List<?> fm_mwork003_007_getWorkList(SearchVO searchVO) {
		List<?> list = list("fmMwork.QR_MWORK003_A", searchVO); //
		return list;
	}

	public List<?> getCalendarYear() {
		return list("fmMwork.QR_MWORK001_YEAR");
	}

	public List<?> getCalendarList(Map map) {
		List<?> list = list("fmMwork.QR_MWORK001_CAL", map); //
		return list;
	}

	public List<?> getWorkList(Map map) {
		List<?> list = list("fmMwork.QR_MWORK001_A", map); //
		return list;
	}

	public WorkVO getWorkState(String utwWrkKey) {
		WorkVO workState = (WorkVO) select("fmMwork.QR_MWORK_WORKSTATE", utwWrkKey);
		return workState;
	}

	public List<?> getCntrList(String utwTrcKey) {
		List<?> cntrList = list("fmMwork.QR_MWORK_CNTRLIST", utwTrcKey);
		return cntrList;
	}

	public int fm_mworkSave(WorkVO workVO, List<FileVO> list) {

		String workKey = workVO.getUtw_wrk_key();
		String appGbn = workVO.getUam_app_gbn();
		String agnYn = workVO.getUtw_agn_yn();

		// 업무완료 세부코드
		if (workVO.getUtw_com_sta() == null) {
			workVO.setUtw_com_sta(workVO.getUtw_wrk_sta());
		}

		// work log
		WorkVO logData = (WorkVO) select("fmMwork.QR_MWORK_SELECT_LOG", workKey);
		if(logData != null){
			logData.setUtw_rgt_id(workVO.getUam_req_id());
			insert("fmMwork.QR_MWORK_INSERT_LOG", logData);
		}

		update("fmMwork.QR_MWORK_UPDATE", workVO);

		// 자가결재와 임시저장은 UWO_APP_MTR 생성x
		if(appGbn.equals("1")){
			ApprMapVO apprMapVO = new ApprMapVO();
			String appLev = workVO.getUam_app_lev();

			apprMapVO.setUam_wrk_key(workKey);
			apprMapVO.setUam_app_gbn(appGbn);		// 결재구분 -> 1:업무완료 승인결재, 2:대무자 승인결재
			apprMapVO.setUam_app_lev(appLev);
			apprMapVO.setUam_sta_cod(workVO.getUam_sta_cod());
			apprMapVO.setUam_req_id(workVO.getUam_req_id());
			apprMapVO.setUam_rgt_id(workVO.getUam_rgt_id());

			// 업무담당자의 결재자 정보 갖고오기
			Map<String, String> approver = (Map<String, String>) select("fmMwork.QR_SELECT_APPROVAL", workVO.getUtw_wrk_id());

			// 2018-03-21 s, 1차 결재자가 없을 경우 예외처리
			// 2018-05-11 s, SECURITY -> APV1
			String uumApvOne = approver.get("uumApvOne");
			String commonApprover = commonUtilService.getApproval("APV1");
			if (uumApvOne != null) {
				apprMapVO.setUam_cf1_id(uumApvOne);
			}
			else {
				apprMapVO.setUam_cf1_id(commonApprover);
			}

			if(appLev.equals("1")){
				apprMapVO.setUam_cf2_id("");
			}else if(appLev.equals("2")){
				apprMapVO.setUam_cf2_id(approver.get("uumApvTwo"));
			}

			// 2016-07-08 업무삭제요청 시
			// 2018-07-18 s, SECURITY2 추가 및 조건 변경
			if (workVO.getUtw_com_sta().equals("93")) {

				apprMapVO.setUam_app_lev("1");
				apprMapVO.setUam_cf1_id("");
				apprMapVO.setUam_cf2_id("");

				// 2018-07-02 s, APV1 -> SECURITY
				String security = commonUtilService.getApproval("SECURITY");

				String security2 = commonUtilService.getApproval("SECURITY2");

				if ( !StringUtils.isBlank(security) && !StringUtils.isBlank(security2)) {
					apprMapVO.setUam_app_lev("2");
					apprMapVO.setUam_cf1_id(security);
					apprMapVO.setUam_cf2_id(security2);
				}
				else if ( !StringUtils.isBlank(security) && StringUtils.isBlank(security2)) {
					apprMapVO.setUam_cf1_id(security);
				}
				else if (StringUtils.isBlank(security) && !StringUtils.isBlank(security2)) {
					apprMapVO.setUam_cf1_id(security2);
				}
			}


			insert("fmMwork.QR_INSERT_APPR_WORK", apprMapVO);	// INSERT UWO_APP_MTR

			// UWO_EVT_ARM INSERT
			// 결재상신(E02)
			String key = workVO.getUam_req_id();
			String eventCode = EventTitleMessage.E02_CODE;
			String title = EventTitleMessage.E02;
			String contents = "";
			String userKey = apprMapVO.getUam_cf1_id();
	        String keyName = (String) select("commonCode.QR_MAILNAME_SELECT", key);

	        contents = title + "<p />해당내용을 확인해주세요.<br /> ■ 상신일자 : " + EgovDateUtil.getToday()
	        		+ "<br /> ■ 상신자 : " + keyName + "<br /> ■ 업무명 : " + logData.getUtd_doc_nam();

	        // sendMail
	        sendMailService.sendMail(key, eventCode, title, contents, userKey);
		}

		/* 대무자 요청 중인 경우
		* 1. UWO_TRC_WRK의 UTW_AGN_ID = '', UTW_AGN_YN = 'N'으로 변경
		* 2. UWO_APP_MTR의 UAM_APP_GBN이 2인 UAM_STA_COD = 30으로 변경
		*/
		if(agnYn.equals("Y")){
			if(workVO.getAgn_sta_dtl().equals("21")){
				// 업무 완료/상신 하는 사람이 대무자가 아닐 경우만
				if(!workVO.getUtw_upt_id().equals(workVO.getUtw_agn_id())){
					update("fmMwork.QR_UPDATE_AGNCANCEL", workKey);
					update("fmMwork.QR_UPDATE_AGNAPPCANCEL", workKey);
				}
			}
		}

		// 첨부파일 수 만큼 insert
		for (int i = 0; i < list.size(); i++) {
			FileVO fvo = list.get(i);
			fvo.setUmf_con_key(workKey);
			insert("fileup.QR_FILEUP001_A", fvo);
		}

		// 2017-10-11
		// ext. Polaris Converter
		if (list.size() > 0) {
			commonUtilService.insertPolarisConverter(workKey);
		}

		return 0;
	}

	public List<?> fm_mwork013_getWorkAppList(SearchVO searchVO) {

		List<?> list = list("fmMwork.QR_MWORK013_A", searchVO);
		return list;
	}

	public int fm_mwork013_getWorkAppCnt(SearchVO searchVO) {
		return  (Integer) select("fmMwork.QR_MWORK013_B", searchVO);
	}


	public int fm_mwork_stateUpdate(WorkVO workVO) {
		String workKey = workVO.getUtw_wrk_key();

		// work log
		WorkVO logData = (WorkVO) select("fmMwork.QR_MWORK_SELECT_LOG", workKey);
		if(logData != null){
			insert("fmMwork.QR_MWORK_INSERT_LOG", logData);
		}

		// 대무자요청 취소가 아닌 경우
		if(workVO.getAgn_app_key().equals("")){
			update("fmMwork.QR_MWORK_WSTATEU", workVO);
//			workVO.setUam_wrk_key(workKey);
			update("fmMwork.QR_MWORK_ASTATEU", workVO);

		}
		// 대무자요청 취소인 경우
		else{
			// 업무결재 취소가 있는 경우 : 업무결재와 대무자요청 취소
			if(workVO.getUam_app_key() != null && !workVO.getUam_app_key().equals("")){
				update("fmMwork.QR_MWORK_WSTATEU", workVO);
				update("fmMwork.QR_MWORK_ASTATEU", workVO);

				workVO.setUam_app_key(workVO.getAgn_app_key());
				update("fmMwork.QR_MWORK_ASTATEU", workVO);
			}else{
				workVO.setUam_app_key(workVO.getAgn_app_key());
				update("fmMwork.QR_MWORK_ASTATEU", workVO);
			}
		}

		// UWO_EVT_ARM INSERT
		// 결재승인(E03), 결재반려(E04), 결재상신(E02)
		String key = workVO.getUtw_upt_id();
		String eventCode = EventTitleMessage.E03_CODE;
		String title = EventTitleMessage.E03;
		String contents = "";
		String userKey = logData.getUtw_wrk_id();

		String workState = workVO.getUtw_wrk_sta();
		String appState = workVO.getUam_sta_cod();
		String appEtc = "";
		String keyName = (String) select("commonCode.QR_MAILNAME_SELECT", key);

		if(workState.equals("90")){
			// 결재승인(결재레벨에 관계없이 최종승인만 메일보냄)
			if(appState.equals("19")){
				appEtc = workVO.getUam_cf1_etc();
			}else if(appState.equals("29")){
				appEtc = workVO.getUam_cf2_etc();
			}

			contents = title + "<p />해당내용을 확인해주세요.<br /> ■ 결재일자 : " + EgovDateUtil.getToday()
	        		+ "<br /> ■ 결재자 : " + keyName + "<br /> ■ 의견 : " + appEtc + "<br /> ■ 업무명 : " + logData.getUtd_doc_nam();

			// sendMail
			sendMailService.sendMail(key, eventCode, title, contents, userKey);

		}else {
			// 결재상신(1차결재자 -> 2차결재자)
			if(appState.equals("21")){
				eventCode = EventTitleMessage.E02_CODE;
				title = EventTitleMessage.E02;
				key = logData.getUtw_wrk_id();
				userKey = workVO.getUam_cf2_id();
				keyName = (String) select("commonCode.QR_MAILNAME_SELECT", key);

		        contents = title + "<p />해당내용을 확인해주세요.<br /> ■ 상신일자 : " + EgovDateUtil.getToday()
		        		+ "<br /> ■ 상신자 : " + keyName + "<br /> ■ 업무명 : " + logData.getUtd_doc_nam();
			}
			// 결재상신 취소
			else if(appState.equals("30")) {
				key = "";
			}else{
				// 결재반려
				// 2017-06-29, 승인이 반려통보가 발송되는 버그 수정
				if(appState.equals("12") || appState.equals("22")) {
					eventCode = EventTitleMessage.E04_CODE;
					title = EventTitleMessage.E04;
				}

				if(appState.equals("12")){
					appEtc = workVO.getUam_cf1_etc();
				}else if(appState.equals("22")){
					appEtc = workVO.getUam_cf2_etc();
				}

				contents = title + "<p />해당내용을 확인해주세요.<br /> ■ 결재일자 : " + EgovDateUtil.getToday()
		        		+ "<br /> ■ 결재자 : " + keyName + "<br /> ■ 의견 : " + appEtc + "<br /> ■ 업무명 : " + logData.getUtd_doc_nam();
			}
			// sendMail
			sendMailService.sendMail(key, eventCode, title, contents, userKey);
		}
		return 0;
	}

	public int fm_mwork_del(String workKey) {
		update("fmMwork.QR_MWORK_DEL", workKey);
		return 0;
	}

	public List<?> getWorkerist(SearchVO searchVO) {
		List<?> list = list("fmMwork.QR_MWORK_WORKERLIST", searchVO);
		return list;
	}

	public List<?> fm_mwork002_agnIdList(SearchVO searchVO) {
		List<?> list = list("fmMwork.QR_MWORK002_E", searchVO);
		return list;
	}

	public void fm_mwork002_setAgnId(SearchVO searchVO) {

		if(StringUtils.isEmpty(searchVO.getWorkCode())){
			List list = list("fmMwork.QR_MWORK005_SELECT_WORK",searchVO);

			for(int i=0; i< list.size(); i++){
				WorkVO logData = (WorkVO) select("fmMwork.QR_MWORK_SELECT_LOG", list.get(i));
				if(logData != null){
					insert("fmMwork.QR_MWORK_INSERT_LOG", logData);
				}
				searchVO.setWorkCode((String)list.get(i));
				update("fmMwork.QR_MWORK002_F", searchVO);

				ApprMapVO apprMapVO = new ApprMapVO();

				apprMapVO.setUam_wrk_key((String)list.get(i));
				apprMapVO.setUam_app_gbn("2");		// 결재구분 -> 1:업무완료 승인결재, 2:대무자 승인결재
				apprMapVO.setUam_app_lev("1");		// 대무자 결재는 2차 결재로.
				apprMapVO.setUam_sta_cod("31");
				apprMapVO.setUam_req_id(searchVO.getSearchKeyword());
				apprMapVO.setUam_rgt_id(searchVO.getSearchKeyword());

				apprMapVO.setUam_cf1_id(searchVO.getWorker());
				insert("fmMwork.QR_INSERT_APPR_WORK", apprMapVO);
			}


		}else{
			String[] workKey = searchVO.getWorkCode().split("\\,");

			for(int i=0; i<workKey.length; i++){
				// work log
				WorkVO logData = (WorkVO) select("fmMwork.QR_MWORK_SELECT_LOG", workKey[i]);
				if(logData != null){
					insert("fmMwork.QR_MWORK_INSERT_LOG", logData);
				}

				searchVO.setWorkCode(workKey[i]);
				update("fmMwork.QR_MWORK002_F", searchVO);

				ApprMapVO apprMapVO = new ApprMapVO();

				apprMapVO.setUam_wrk_key(workKey[i]);
				apprMapVO.setUam_app_gbn("2");		// 결재구분 -> 1:업무완료 승인결재, 2:대무자 승인결재
				apprMapVO.setUam_app_lev("2");		// 대무자 결재는 2차 결재로.
				apprMapVO.setUam_sta_cod("11");
				apprMapVO.setUam_req_id(searchVO.getSearchKeyword());
				apprMapVO.setUam_rgt_id(searchVO.getSearchKeyword());

				// 업무담당자의 결재자 정보 갖고오기
				Map<String, String> approver = (Map<String, String>) select("fmMwork.QR_SELECT_APPROVAL", searchVO.getSearchKeyword());
				apprMapVO.setUam_cf1_id(approver.get("uumApvOne"));
				apprMapVO.setUam_cf2_id(approver.get("uumApvTwo"));

				insert("fmMwork.QR_INSERT_APPR_WORK", apprMapVO);	// INSERT UWO_APP_MTR

				// UWO_EVT_ARM INSERT
				// 대무자요청결재상신 시(E05)
				String key = searchVO.getSearchKeyword();
				String eventCode = EventTitleMessage.E05_CODE;
				String title = EventTitleMessage.E05;
				String worker = searchVO.getWorker();
				String userKey = apprMapVO.getUam_cf1_id();

				String keyName = (String) select("commonCode.QR_MAILNAME_SELECT", key);
				String workerName = (String) select("commonCode.QR_MAILNAME_SELECT", worker);
				String contents = title + "<p />해당내용을 확인해주세요.<br /> ■ 상신일자 : " + EgovDateUtil.getToday()
						   		+ "<br /> ■ 상신자 : " + keyName + "<br /> ■ 대무자 : " + workerName + "<br /> ■ 업무명 : " + logData.getUtd_doc_nam();

				// sendMail
				sendMailService.sendMail(key, eventCode, title, contents, userKey);
			}
		}
	}

//	@SuppressWarnings("unchecked")
	public void fm_mwork006_setWrkId(SearchVO searchVO) {

		String[] addList = searchVO.getAddList().split("\\,");
		ArrayList<String> al = new ArrayList<String>();

		String userKey = searchVO.getWorker();

		for(int i=0; i<addList.length; i++){
			searchVO.setAddList(addList[i]);
			update("fmMwork.QR_MWORK006_SETWRKMAP", searchVO);
			update("fmMwork.QR_MWORK006_SETWRKID", searchVO);

			String wrkKey = addList[i];

			// 2018-07-18 s, email 발송 추가
			EgovMap docMap = selectDocByWrkKey(wrkKey);

			// 3. 이메일 발송 목록
			if (docMap.get("utdSdmYn").toString().equals("Y")) {
				String contents = new StringBuffer()
						.append("<tr>")
						.append("<td width=\"436\" style=\"border-width:1; border-color:gray; border-style:solid;\">")
						.append("<p align=\"center\" style=\"line-height:120%; margin-top:0; margin-bottom:0;\">")
						.append(docMap.get("utdDocNam").toString())
						.append("</p>")
						.append("</td>")
						.append("<td width=\"77\" style=\"border-width:1; border-color:gray; border-style:solid;\">")
						.append("<p align=\"center\" style=\"line-height:120%; margin-top:0; margin-bottom:0;\">")
						.append(docMap.get("utdTrmGbnTxt").toString())
						.append("</p>")
						.append("</td>")
						.append("<td width=\"111\" style=\"border-width:1; border-color:gray; border-style:solid;\">")
						.append("<p align=\"center\" style=\"line-height:120%; margin-top:0; margin-bottom:0;\">")
						.append(docMap.get("utdCmpDat").toString())
						.append("</p>")
						.append("</td>")
						.append("</tr>").toString();
				al.add(contents);
			}
		}

		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("uccFirCod", "evtArm");
		map2.put("uccSndCod", EventTitleMessage.E01_CODE);
		EgovMap emap = commonUtilService.getUccComCodeInfo(map2);
		String mailTemplate = (String) emap.get("uccEtc");


		// 4. 이메일 발송
		// UWO_EVT_ARM INSERT
		// 업무추가(E01)
		String sender = searchVO.getRgtId();
		String eventCode = EventTitleMessage.E01_CODE;
		String title = EventTitleMessage.E01;
		StringBuffer contents = new StringBuffer();
		contents.append(al);

		String mailBody = mailTemplate.replace("{__BODY__}", contents.toString());

		log.info("userKey: {}, content: {}", userKey, mailBody);
   		sendMailService.sendMail(sender, eventCode, title, mailBody, userKey);
	}

	public void fm_mwork013_appStateUpdate(WorkVO workVO) {
		update("fmMwork.QR_MWORK_ASTATEU", workVO);

		// UWO_EVT_ARM INSERT
		// 대무자요청 결재승인(E06), 대무자요청 결재반려(E07)
		String key = workVO.getUam_upt_id();
		String eventCode = EventTitleMessage.E06_CODE;
		String title = EventTitleMessage.E06;

		String appState = workVO.getUam_sta_cod();
		String appEtc = "";
		String contents = "";
		String userKey = workVO.getUtw_wrk_id();
		String keyName = (String) select("commonCode.QR_MAILNAME_SELECT", key);

		// 대무자요청 결재승인(대무자는 무조건 2차결재)
		if(appState.equals("29")){
			appEtc = workVO.getUam_cf2_etc();
			contents = title + "<p />해당내용을 확인해주세요.<br /> ■ 결재일자 : " + EgovDateUtil.getToday()
	        		+ "<br /> ■ 결재자 : " + keyName + "<br /> ■ 의견 : " + appEtc + "<br /> ■ 업무명 : " + workVO.getUtd_doc_nam();

			// sendMail
			sendMailService.sendMail(key, eventCode, title, contents, userKey);
		}
		else if(appState.equals("21")){
			// 대무자요청 결재 승인(1차->2차)
			eventCode = EventTitleMessage.E05_CODE;
			title = EventTitleMessage.E05;
			key = workVO.getUtw_wrk_id();
			keyName = (String) select("commonCode.QR_MAILNAME_SELECT", key);
			String workerName = workVO.getUtw_agn_id();
			userKey = workVO.getUam_cf2_id();

			contents = title + "<p />해당내용을 확인해주세요.<br /> ■ 상신일자 : " + EgovDateUtil.getToday()
			   		+ "<br /> ■ 상신자 : " + keyName + "<br /> ■ 대무자 : " + workerName + "<br /> ■ 업무명 : " + workVO.getUtd_doc_nam();

			// sendMail
			sendMailService.sendMail(key, eventCode, title, contents, userKey);
		}
		else if(appState.equals("12") || appState.equals("22")) {
			// 대무자요청 결재반려
			eventCode = EventTitleMessage.E07_CODE;
			title = EventTitleMessage.E07;

			if(appState.equals("12")){
				appEtc = workVO.getUam_cf1_etc();
			}else if(appState.equals("22")){
				appEtc = workVO.getUam_cf2_etc();
			}
			contents = title + "<p />해당내용을 확인해주세요.<br /> ■ 결재일자 : " + EgovDateUtil.getToday()
	        		+ "<br /> ■ 결재자 : " + keyName + "<br /> ■ 의견 : " + appEtc + "<br /> ■ 업무명 : " + workVO.getUtd_doc_nam();

			// sendMail
			sendMailService.sendMail(key, eventCode, title, contents, userKey);
		}
	}

	public List<?> ifpMtrList(Map map) {
		List<?> list = list("fmMwork.QR_MWORK017_A", map);
		return list;
	}

	public EgovMap ifp_Mtr_popup(String mtrKey) {
		EgovMap map = (EgovMap)select("fmMwork.QR_MWORK017_B",mtrKey);
		return map;
	}

	public void saveIfpMtr(IFP_MTR_VO ifpMtrVo) {

		int mKey = (Integer)select("fmMwork.QR_MWORK017_P");

		ifpMtrVo.setUimMtrKey(mKey);

		insert("fmMwork.QR_MWORK017_C",ifpMtrVo);
		insert("fmMwork.QR_MWORK017_D",ifpMtrVo);
	}

	public void modifyIfpMtr(IFP_MTR_VO ifpMtrVo) {
		update("fmMwork.QR_MWORK017_E",ifpMtrVo);
	}

	public void ifpDel(Map map) {
		update("fmMwork.QR_MWORK017_F",map);
		update("fmMwork.QR_MWORK017_G",map);
	}

	public List<?> ifpDtlList(String mtrKey) {
		List<?> list = list("fmMwork.QR_MWORK017_H", mtrKey);
		return list;
	}

	public EgovMap ifp_Dtl_popup(String dtlKey) {
		EgovMap map = (EgovMap)select("fmMwork.QR_MWORK017_I",dtlKey);
		return map;
	}

	public void saveIfpDtl(IFP_DTL_VO ifpDtlVo) {
		insert("fmMwork.QR_MWORK017_J",ifpDtlVo);
	}

	public void modifyIfpDtl(IFP_DTL_VO ifpDtlVo) {
		update("fmMwork.QR_MWORK017_K",ifpDtlVo);
	}

	public void eraseIfpDtl(IFP_DTL_VO ifpDtlVo) {
		update("fmMwork.QR_MWORK017_L",ifpDtlVo);
	}

	public void ifpDel2(Map map) {
		update("fmMwork.QR_MWORK017_M",map);
	}

	public List<?> fm_mwork012_list(SearchVO searchVO) {
		List<?> list = list("fmMwork.QR_MWORK012_A", searchVO);
		return list;
	}

	public int fm_mwork012_cnt(SearchVO searchVO) {
		int cnt = (Integer) select("fmMwork.QR_MWORK012_B", searchVO);
		return cnt;
	}

	public String fm_mwork014_get_celnum(String string) {
		String cel_num = (String) select("fmMwork.QR_MWORK014_C", string);
		return cel_num;
	}

	public void fm_mwork014_send_sms(Map map) {
		insert("fmMwork.QR_MWORK014_D",map);
	}


	public List<?> fm_mwork018_list(SearchVO searchVO) {
		List<?> list = list("fmMwork.QR_MWORK018_A", searchVO);
		return list;
	}

	public List<?> fmMwork004_List(SearchVO searchVO) {
		List<?> list = list("fmMwork.QR_MWORK004_A", searchVO);
		return list;
	}

	public List<?> get_stdlist() {
		List<?> list = list("fmMwork.QR_MWORK004_B");
		return list;
	}

	public List<?> fmMwork005_List(SearchVO searchVO) {
		List<?> list = list("fmMwork.QR_MWORK005_A", searchVO);
		return list;
	}

	public List<?> fmMwork005_termList(SearchVO searchVO) {
		List<?> list = list("fmMwork.QR_MWORK005_B", searchVO);
		return list;
	}

	public List<?> getDeptUserList(String usrKey) {
		List<?> list = list("fmMwork.QR_MWORK005_C", usrKey);
		return list;
	}

	public List<?> getWorkMapKey(Map userMap) {
		List<?> list = list("fmMwork.QR_MWORK005_D", userMap);
		return list;
	}

	public void changeWork(Map userMap) {
		update("fmMwork.QR_MWORK005_E",userMap);
	}

	public void changeWorkMap(Map userMap) {
		update("fmMwork.QR_MWORK005_F",userMap);
	}

	public List<?> getDeptList(Map<String, String> map) {
		List<?> list = list("fmMwork.QR_MWORK014_E", map);
		return list;
	}

	public int fmMwork005_termCount(SearchVO searchVO) {
		int cnt = (Integer)select("fmMwork.QR_MWORK005_G",searchVO);
		return cnt;
	}

	public int getDelayCnt(String usrKey) {
		int cnt = (Integer)select("fmMwork.QR_MWORK005_H",usrKey);
		return cnt;
	}

	public int getCompCnt(String usrKey) {
		int cnt = (Integer)select("fmMwork.QR_MWORK005_I",usrKey);
		return cnt;
	}

	public int getNoworkCnt(String usrKey) {
		int cnt = (Integer)select("fmMwork.QR_MWORK005_J",usrKey);
		return cnt;
	}

	public String getUserName(String usrKey) {
		String usr_name = (String) select("fmMwork.QR_MWORK005_K", usrKey);
		return usr_name;
	}

	public List<?> fm_mwork019_getworklist(SearchVO searchVO) {
		List<?> list = list("fmMwork.QR_MWORK019_A", searchVO);
		return list;
	}

	public int getworklist_count(SearchVO searchVO) {
		int cnt = (Integer)select("fmMwork.QR_MWORK019_B", searchVO);
		return cnt;
	}

	public List<?> getAllWorkList(Map map) {
		List<?> list = list("fmMwork.QR_ALL_WORK_LIST", map);
		return list;
	}

	public List<?> getSimilarWorkList(Map map) {
		List<?> list = list("fmMwork.QR_SIMILAR_WORK_LIST", map);
		return list;
	}

	public List<?> getAllWorkList_file(Map map) {
		List<?> list = list("fmMwork.QR_ALL_WORK_LIST_FILE", map);
		return list;
	}

	public void batchUpload(Map map, List<FileVO> list) {
		String workKey = (String)map.get("wKey");
		for (int i = 0; i < list.size(); i++) {
			FileVO fvo = list.get(i);
			fvo.setUmf_con_key(workKey);
			insert("fileup.QR_FILEUP001_A", fvo);
		}
	}

	public List<?> fmMwork005_t(Map<String, String> map) {
		return list("fmMwork.QR_MWORK005_TRA", map);
	}

	public void fmMwork005_appWrk(String wrkKey) {
		update("fmMwork.QR_MWORK005_APP_WRK",wrkKey);
	}

	public void fmMwork005_appApp(String wrkKey) {
		update("fmMwork.QR_MWORK005_APP_APP",wrkKey);
	}

	public void fmMwork005_rtnWrk(String wrkKey) {
		update("fmMwork.QR_MWORK005_RTN_WRK",wrkKey);
	}

	public void fmMwork005_rtnApp(String wrkKey) {
		update("fmMwork.QR_MWORK005_RTN_APP",wrkKey);
	}

	public EgovMap fmMwork006_trs_info(String addList) {
		return (EgovMap)select("fmMwork.QR_MWORK006_TRS_LIST", addList);
	}

	public List<?> getUsrList(Map<String, String> map) {
		return list("fmMwork.QR_GET_USR_LIST", map);
	}

	public List<?> getBcyList(Map map) {
		return list("fmMwork.QR_MWORK_BCY_LIST", map);
	}

	public String getWorkKey(Map map) {
		return (String)select("fmMwork.QR_MWORK_GET_WORK_KEY", map);
	}

	public String getMngKey(Map map) {
		return (String)select("fmMwork.QR_MWORK_MNG_KEY", map);
	}

	public String getWorkId(Map map) {
		return (String)select("fmMwork.QR_MWORK_GET_WORK_ID", map);
	}

	public List<?> selectWorkListByTrcKey(String trcKey) {
		return list("fmMwork.selectWorkListByTrcKey", trcKey);
	}

	public EgovMap selectApprovalByWrkId(String wrkId) {
		return (EgovMap) select("fmMwork.QR_SELECT_APPROVAL", wrkId);
	}

	public String selectSeqAppMtr() {
		return (String) select("fmMwork.selectSeqAppMtr");
	}

	public String insertApprWork(ApprMapVO apprMapVO) {
		return (String) insert("fmMwork.QR_INSERT_APPR_WORK", apprMapVO);
	}

	public void insertTransferMap(Map<String, String> map) {
		insert("fmMwork.insertTransferMap", map);
	}

	public List<?> selectTransferListByAppKey(String appKey) {
		return list("fmMwork.selectTransferListByAppKey", appKey);
	}

	/**
	 * 2018-05-11 s, 결재정보
	 * @param appKey
	 * @return
	 */
	public WorkVO selectAppByAppKey(String appKey) {
		return (WorkVO) select("fmMwork.selectAppByAppKey", appKey);
	}

	/**
	 * 2018-07-18 s, trc_doc Info by wrk_key
	 * @param appKey
	 * @return
	 */
	EgovMap selectDocByWrkKey(String wrkKey) {
		return (EgovMap) select("fmMwork.selectDocByWrkKey", wrkKey);
	}

	public void fmMwork006_excel_Update(Map<String, String> map) {
		update("fmMwork.updatefmMwork006", map);
	}
	
	public List<?> selectfmMwork006(String wrkKey) {
		return list("fmMwork.selectfmMwork006", wrkKey);
	}
}
