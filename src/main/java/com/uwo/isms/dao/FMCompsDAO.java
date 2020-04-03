package com.uwo.isms.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.uwo.isms.domain.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.uwo.isms.service.SendMailService;
import com.uwo.isms.web.FMCompsController;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Repository("fmCompsDAO")
public class FMCompsDAO extends EgovAbstractDAO {

	Logger log = LogManager.getLogger(FMCompsController.class);
/*
	@Resource(name = "sendMailService")
	SendMailService sendMailService;*/

	@Resource(name="excelNewDAO")
	private ExcelNewDAO excelNewDAO;

	public List<?> fm_comps001_list(SearchVO searchVO) {
		List<?> list = list("fmComps.QR_COMPS001_A", searchVO); //
		return list;
	}

	public String fm_comps001_getManCylMax() {
		return (String) select("fmComps.QR_COMPS001_GET_MANCYLMAX");
	}

	public int fm_comps001_cnt(SearchVO searchVO) {
		return (Integer) select("fmComps.QR_COMPS001_B", searchVO);
	}

	public CycleVO fm_comps001_read(CycleVO vo) {
		return (CycleVO)select("fmComps.QR_COMPS001_C",vo);
	}

	public void fm_comps001_write(CycleVO vo) {
		//select("fmComps.QR_COMPS001_F");
		insert("fmComps.QR_COMPS001_D",vo);

	}


	public void fm_comps001_update(CycleVO vo) {
		update("fmComps.QR_COMPS001_E",vo);
	}

	public List<?> fm_comps001_list_new(SearchVO searchVO) {
		return (List<?>) list("fmComps.QR_COMPS001_LIST_NEW", searchVO);
	}

	public List<?> fm_comps002_list(SearchVO searchVO) {
		return list("fmComps.QR_COMPS002_LIST", searchVO);
	}

	public int fm_comps002_cnt(SearchVO searchVO) {
		return (Integer) select("fmComps.QR_COMPS002_CNT", searchVO);
	}

	public EgovMap fm_comps002_stnd_info(String strStndCod) {
		return (EgovMap)select("fmComps.QR_COMPS002_STND_INFO", strStndCod);
	}

	public List<?> fm_comps002_org_list(String strStndCod) {
		return list("fmComps.QR_COMPS002_ORG_LIST", strStndCod);
	}

	public void fm_comps002_stnd_insert(Map<String, Object> map) {
		insert("fmComps.QR_COMPS002_STND_INSERT", map);
	}

	public void fm_comps002_stnd_update(Map<String, Object> map) {
		update("fmComps.QR_COMPS002_STND_UPDATE", map);
	}

	public int fm_comps002_stnd_cod_cnt(Map<String, Object> map) {
		return (Integer)select("fmComps.QR_COMPS002_STND_COD_CNT", map);
	}

	public EgovMap fm_comps002_org_info(Map<String, String> map) {
		return (EgovMap)select("fmComps.QR_COMPS002_ORG_INFO", map);
	}

	public void fm_comps002_org_insert(Map<String, String> map) {
		insert("fmComps.QR_COMPS002_ORG_INSERT", map);
	}

	public void fm_comps002_org_update(Map<String, String> map) {
		update("fmComps.QR_COMPS002_ORG_UPDATE", map);
	}

	public int fm_comps002_org_cod_cnt(Map<String, String> map) {
		return (Integer)select("fmComps.QR_COMPS002_ORG_COD_CNT", map);
	}

	public List<?> fm_comps003_list(Map map) {
		return list("fmComps.QR_COMPS003_A",map);
	}

	public List<?> fm_comps003_2D_list(Map map) {
		return list("fmComps.QR_COMPS003_B",map);
	}

	public List<?> fm_comps003_3D_list(Map map) {
		return list("fmComps.QR_COMPS003_C",map);
	}

	public void fm_comps003_insert(Map map) {
		insert("fmComps.QR_COMPS003_D",map);
		//insert("fmLog.QR_LOG004",map);
	}

	public void fm_comps003_update(Map map) {
		update("fmComps.QR_COMPS003_E",map);
		/*
		List<CntrVO> list = (List)list("fmLog.QR_LOG001",map);
		for(int i=0;i<list.size();i++){
			CntrVO vo = list.get(i);
			insert("fmLog.QR_LOG005",vo);
		}
		*/
	}

	public void fm_comps003_2D_insert(Map map) {
		int row = (Integer)select("fmComps.QR_COMPS003_F",map);
		if(row > 0){
			insert("fmComps.QR_COMPS003_G",map);
			//insert("fmLog.QR_LOG006",map);
		}else{
			update("fmComps.QR_COMPS003_H",map);
			/*
			List<CntrVO> list = (List)list("fmLog.QR_LOG002",map);
			for(int i=0;i<list.size();i++){
				CntrVO vo = list.get(i);
				insert("fmLog.QR_LOG007",vo);
			}
			*/
		}
	}

	public void fm_comps003_2D_update(Map map) {
		update("fmComps.QR_COMPS003_I",map);
		// 2017-11-22 s, 주석처리
		//update("fmComps.QR_COMPS003_P",map);
		/*
		List<CntrVO> list = (List)list("fmLog.QR_LOG002",map);
		for(int i=0;i<list.size();i++){
			CntrVO vo = list.get(i);
			insert("fmLog.QR_LOG008",vo);
		}
		*/
	}

	public void fm_comps003_3D_insert(Map map) {
		int row = (Integer)select("fmComps.QR_COMPS003_J",map);
		if(row > 0){
			insert("fmComps.QR_COMPS003_K",map);
			//insert("fmLog.QR_LOG009",map);
		}else{
			update("fmComps.QR_COMPS003_L",map);
			/*
			List<CntrVO> list = (List)list("fmLog.QR_LOG003",map);
			for(int i=0;i<list.size();i++){
				CntrVO vo = list.get(i);
				insert("fmLog.QR_LOG010",vo);
			}
			*/
		}
	}

	public void fm_comps003_3D_update(Map map) {
		update("fmComps.QR_COMPS003_M",map);
		// 2017-11-22 s, 주석처리
		//update("fmComps.QR_COMPS003_O",map);
		//update("fmComps.QR_COMPS003_Q",map);
		/*
		List<CntrVO> list = (List)list("fmLog.QR_LOG003",map);
		for(int i=0;i<list.size();i++){
			CntrVO vo = list.get(i);
			insert("fmLog.QR_LOG011",vo);
		}
		*/
	}

	public String fm_comps003_key(String key) {
		return (String)select("fmComps.QR_COMPS003_N",key);
	}

	public List<?> fm_comps003_popup(Map<String, String> map) {
		List<?> list = (List)list("fmComps.QR_COMPS003_R",map);
		return list;
	}

	public List<?> fm_comps003_popup_div() {
		List<?> list = (List)list("fmComps.QR_COMPS003_S");
		return list;
	}


	public void fm_comps003_popup_mapping(Map map) {
		insert("fmComps.QR_COMPS003_T",map);
	}


	public List<?> fm_comps003_3D_mappinglist() {
		return list("fmComps.QR_COMPS003_U");
	}

	public void fm_comps003_del_mapping(String brdKey) {
		update("fmComps.QR_COMPS003_V",brdKey);
	}


	public BoardVO fm_comps003_popup_brdView(String key) {
		return (BoardVO)select("fmComps.QR_COMPS003_W",key);
	}


	public List<?> fm_comps003_popup_brdFile(String key) {
		return list("fmComps.QR_COMPS003_X",key);
	}

	public List<?> fm_comps003_stdlist() {
		return list("fmComps.QR_COMPS003_Y");
	}

	public List<?> fm_comps003_svclist() {
		return list("fmComps.QR_COMPS003_Z");
	}

	public void fm_comps003_excelInsert(Map map, List list) {
		insert("fmComps.QR_COMPS003_INSERT", map);

		for(int i=0; i < list.size(); i++){
			map.put("service", list.get(i));
			this.rm_comps003_service_insert(map);
		}
	}

	public void fm_comps003_excelUpdate(Map map, List list) {
		update("fmComps.QR_COMPS003_UPDATE", map);
		// 기존 Map 데이터 삭제by ctrKey
		delete("fmComps.QR_COMPS003_DELETE_MAP", map.get("ctrKey"));

		for(int i=0; i < list.size(); i++){
			map.put("service", list.get(i));
			this.rm_comps003_service_insert(map);
		}
	}

	public int fm_comps003_lvlCodCount(Map<String, String> map) {
		return (Integer) select("fmComps.QR_COMPS003_LVL_COD_COUNT", map);
	}


	public String fm_comps003_getCtrKey(Map<String, String> map) {
		return (String) select("fmComps.QR_COMPS003_GET_CTR_KEY", map);
	}

	public List<?> fm_comps004(SearchVO searchVO) {
		List<?> list = list("fmComps.QR_COMPS004_A", searchVO);
		return list;
	}

	public int fm_comps004_cnt(SearchVO searchVO) {
		return (Integer) select("fmComps.QR_COMPS004_CNT",searchVO);
	}

	/**
	 * 시스템설정 - 증적양식 설정 - 팝업 - trcKey에 해당하는 증적양식 정보 갖고오기
	 * @param trcKey
	 * @return
	 */
	public SampleDocVO fm_comps004_sampleDocInfo(SearchVO searchVO) {
		SampleDocVO vo = (SampleDocVO) select("fmComps.QR_COMPS004_F", searchVO);
		return vo;
	}

	/**
	 * 시스템설정 - 증적양식 설정 - 팝업 - 업무담당자 그리드 테이블 리스트 쿼리
	 * @param uumUsrKey
	 * @return
	 */
	public List<?> fm_comps004_workerList(String utdTrcKey) {
		List<?> list = list("fmComps.QR_COMPS004_B", utdTrcKey); //
		return list;
	}


	/**
	 * 시스템설정 - 증적양식 설정 - 팝업 - 증적양식 추가
	 * @param sampleDocVO
	 * @param workerList
	 * @param list
	 * @return
	 */
	public int fm_comps004_add(SampleDocVO sampleDocVO, List<FileVO> list) {
		String docKey = "";
		// seq_doc_num 구하기
		docKey = (String) select("fmComps.QR_COMPS004_SEQ");
		int result = Integer.valueOf(docKey);

		// 증적양식 insert
		sampleDocVO.setUtdTrcKey(docKey);
		insert("fmComps.QR_COMPS004_D", sampleDocVO);				// INSERT UWO_TRC_DOC

		// 업무담당자 수 만큼 insert, 업무생성
		/*if(workerList != null){

			for(int i=0; i< workerList.size(); i++){

				String termGbn = sampleDocVO.getUtdTrmGbn();
				String startDate = sampleDocVO.getUtdStrDat();
				String endDate = sampleDocVO.getUtdEndDat();

				String manCylEnd = sampleDocVO.getManCylEnd();
				int manCylEndInt = Integer.parseInt(EgovDateUtil.validChkDate(manCylEnd));
				int startDateInt = Integer.parseInt(EgovDateUtil.validChkDate(startDate));
				SampleDocMapVO smVO = workerList.get(i);


				 * 업무생성 - 심사주기 내에서 업무 한번에 생성
				 *
				 * 첫번째 UTW_STR_DAT에 INSERT : UTD_STR_DAT
				 * 두번째부터 : UTD_STR_DAT + 주기에 해당하는 날짜 더하기.(일간 : 1, 주간 : 7, 월간 : 1개월...)

				if(!(termGbn.substring(0, 1).equals("N"))){
					while(startDateInt < manCylEndInt){
						// work 생성시 주기는 D1112 형식으로. 배치 실행 될때 중복 생성 방지
						String changeTermCode = EgovDateUtil.getTermCode(termGbn, startDate);

						// UWO_TRC_WRK.UTW_WRK_KEY 생성
						String workKey = (String) select("fmMwork.QR_SELECT_SEQ");

						// 주간 마지막 주는 업무 생성 안함.(53주는 없는걸로..)
						if(!changeTermCode.equals("53")){

							WorkVO workVO = new WorkVO();
							workVO.setUtw_wrk_key(workKey);
							workVO.setUtw_trc_key(docKey);
							workVO.setUtw_prd_cod(sampleDocVO.getUtdBcyCod());
							workVO.setUtw_trm_cod(changeTermCode);
							// 업무 생성시 상태는 10 : 미완료
							workVO.setUtw_wrk_sta("10");
							workVO.setUtw_str_dat(startDate);
							workVO.setUtw_end_dat(endDate);
							workVO.setUtw_div_cod(smVO.getUtmDivCod());
							workVO.setUtw_svc_cod(smVO.getUtmSvcCod());
							workVO.setUtw_wrk_id(smVO.getUtmWrkId());
							// 업무 생성시 대무자여부 : 'N'으로 셋팅
							workVO.setUtw_agn_yn("N");
							workVO.setUtw_dep_cod(smVO.getUtmDepCod());
							workVO.setUtw_rgt_id(sampleDocVO.getUtdRgtId());

							insert("fmMwork.QR_INSERT_WORK", workVO);			// INSERT UWO_TRC_WRK
						}

						// utw_str_dat, utw_end_dat(업무시작일, 업무종료일) 구하기
						if(termGbn.equals("D")){
							startDate = EgovDateUtil.addYearMonthDay(startDate, 0, 0, 1);
							endDate = EgovDateUtil.addYearMonthDay(endDate, 0, 0, 1);
						}
						else if(termGbn.equals("W")){
							startDate = EgovDateUtil.addYearMonthDay(startDate, 0, 0, 7);
							endDate = EgovDateUtil.addYearMonthDay(endDate, 0, 0, 7);
						}
						else if(termGbn.equals("M")){
							startDate = EgovDateUtil.addYearMonthDay(startDate, 0, 1, 0);
							endDate = EgovDateUtil.addYearMonthDay(endDate, 0, 1, 0);
						}else if(termGbn.equals("T")){
							startDate = EgovDateUtil.addYearMonthDay(startDate, 0, 2, 0);
							endDate = EgovDateUtil.addYearMonthDay(endDate, 0, 2, 0);
						}else if(termGbn.equals("Q")){
							startDate = EgovDateUtil.addYearMonthDay(startDate, 0, 4, 0);
							endDate = EgovDateUtil.addYearMonthDay(endDate, 0, 4, 0);
						}else if(termGbn.equals("H")){
							startDate = EgovDateUtil.addYearMonthDay(startDate, 0, 6, 0);
							endDate = EgovDateUtil.addYearMonthDay(endDate, 0, 6, 0);
						}else if(termGbn.equals("Y")){
							startDate = EgovDateUtil.addYearMonthDay(startDate, 1, 0, 0);
							endDate = EgovDateUtil.addYearMonthDay(endDate, 1, 0, 0);
						}

						startDateInt = Integer.parseInt(EgovDateUtil.validChkDate(startDate));
					}
				}
			}
		}*/

		// 첨부파일 수 만큼 insert
		if (list != null) {		// 2016-10-27 null 조건 추가
			for (int i = 0; i < list.size(); i++) {
				FileVO fvo = list.get(i);
				fvo.setUmf_con_key(docKey);
				insert("fileup.QR_FILEUP001_A", fvo);
			}
		}

		// 양식없음일 경우 첨부파일명을 0으로
		if(sampleDocVO.getUtdDocGbn().equals("50")){
			FileVO fileVO = new FileVO();
			fileVO.setUmf_tbl_gbn("DOC");
			fileVO.setUmf_con_gbn("5");
			fileVO.setUmf_con_key(docKey);
			fileVO.setUmf_con_fnm("0");
			fileVO.setUmf_bcy_cod(sampleDocVO.getUtdBcyCod());

			insert("fileup.QR_FILEUP001_A", fileVO);
		}

		return result;
	}

	/**
	 * 시스템설정 - 증적양식 설정 - 팝업 - 증적양식 수정
	 * @param sampleDocVO
	 * @param workerList
	 * @param list
	 * @return
	 */
	public int fm_comps004_update(SampleDocVO sampleDocVO, List<FileVO> list) {
		String docKey = sampleDocVO.getUtdTrcKey();
		int result = Integer.valueOf(docKey);
		// 증적양식 log
		SampleDocVO logDocVO = (SampleDocVO) select("fmComps.QR_COMPS004_I", docKey);
		if(logDocVO != null){
			logDocVO.setUtdRgtId(sampleDocVO.getUtdRgtId());
			insert("fmComps.QR_COMPS004_J", logDocVO);
		}

		// 증적양식 update
		update("fmComps.QR_COMPS004_H", sampleDocVO);	// UPDATE UWO_TRC_DOC

		// 증적양식의 사용여부 판단
        String delYn = sampleDocVO.getUtdDelYn();

        if(delYn.equals("Y")){
        	// 증적양식의 사용여부가 미사용일 경우 해당 증적양식의 업무, 문서, 첨부파일 DEL_YN을 'Y'로 변경
        	// 담당자는 화면에서 바로 DEL_YN을 'Y' 로 변경하므로 여기서는 안함.
			fm_comps004_worker_all_del(docKey);
        }
        // 증적양식의 사용여부가 사용인 경우
        else{

        	String aprYn = sampleDocVO.getUtdAprYn();
        	String aprlev = sampleDocVO.getUtdAppLev();
        	String aprlevOld = sampleDocVO.getAprlevOld();

			/*
			 * 업무 담당자 없이 문서 내용만 수정일 때 결재방법에 따른 업무처리
			 *
			 * 1. 결재방법이 변경 되었을 경우
			 * 1.1 UWO_TRC_WRK에서 docKey에 해당하는  UTW_WRK_KEY갖고오기
			 * 1.2 UWO_APP_MTR에서 UTW_WRK_KEY에 해당하는 결재정보 update
			 * 1.2.1 결재방법이 결재(1차,2차) -> 자가로 변경 시 delete
			 * 1.2.2 결재방법이 자가 -> 결재(1차, 2차)로 변경 시 insert
			 * 1.2.3 결재방법이 결재(1차) -> 결재(2차) 혹은 결재(2차) -> 결재(1차)로 변경 시 update
			 *
			 * 2. 결재방법이 변경되지 않았을 경우 특별한 처리 없음.
			 * */

			// 결재방법이 변경 되었을 경우(0:자가, 1:1차, 2:2차)
			if(!aprlev.equals(aprlevOld)){
				// doc_key에 해당하는 work_key select
				List workKeyList = (List) list("fmMwork.QR_SELECT_WORK_KEY", docKey);

				if(workKeyList != null){
					// 해당하는 work row 수 만큼 쿼리 실행
					for(int i=0; i<workKeyList.size(); i++){
						Map<String, String> workKeyMap = (Map<String, String>) workKeyList.get(i);

						ApprMapVO apprMapVO = new ApprMapVO();
						String appLev = sampleDocVO.getUtdAppLev();

						apprMapVO.setUam_wrk_key(workKeyMap.get("utwWrkKey"));
						apprMapVO.setUam_app_lev(appLev);
						apprMapVO.setUam_upt_id(sampleDocVO.getUtdUptId());

						if(!aprlev.equals("0")){
							// UWO_USR_MTR에서 업무담당자의 결재자 정보 갖고오기
							Map<String, String> approver = (Map<String, String>) select("fmMwork.QR_SELECT_APPROVAL", workKeyMap.get("utwWrkId"));
							apprMapVO.setUam_cf1_id(approver.get("uumApvOne"));

							if(appLev.equals("1")){
								apprMapVO.setUam_cf2_id("");
							}else if(appLev.equals("2")){
								apprMapVO.setUam_cf2_id(approver.get("uumApvTwo"));
							}
						}

						if (aprlevOld != null) {	// 2016-10-27 null 조건 추가
							// 결재방법이 결재(1차,2차) -> 자가로 변경 시 delete(update)
							if(!aprlevOld.equals("0") && aprlev.equals("0")){
								update("fmMwork.QR_DELETE_APPR_WORK", apprMapVO);
							}
							// 결재방법이 결재(1차) -> 결재(2차) 혹은 결재(2차) -> 결재(1차)로 변경 시 update
							else if(!aprlevOld.equals("0")){
								update("fmMwork.QR_UPDATE_APPR_WORK", apprMapVO);
							}
							// 결재방법이 자가 -> 결재(1차, 2차)로 변경 시 insert
							else if(aprlevOld.equals("0") && !aprlev.equals("0")){
								insert("fmMwork.QR_INSERT_APPR_WORK", apprMapVO);
							}
						}
					}
				}
			}

			// 첨부파일 수 만큼 insert
			if (list != null) {		// 2016-10-27 null 조건 추가
				for (int i = 0; i < list.size(); i++) {
					FileVO fvo = list.get(i);
					fvo.setUmf_con_key(docKey);
					insert("fileup.QR_FILEUP001_A", fvo);
				}
			}

			/* 2016-07-06 add comment
			// 양식없음일 경우 첨부파일명을 0으로
			if(sampleDocVO.getUtdDocGbn().equals("50")){

				FileVO fileVO = new FileVO();
				fileVO.setUmf_tbl_gbn("DOC");
				fileVO.setUmf_con_gbn("5");
				fileVO.setUmf_con_key(docKey);
				fileVO.setUmf_con_fnm("0");
				fileVO.setUmf_bcy_cod(sampleDocVO.getUtdBcyCod());

				List<?> fileList = list("fileup.QR_FILE_LIST", fileVO);

				if(fileList.size() == 0){
					insert("fileup.QR_FILEUP001_A", fileVO);
				}
			}
			*/
        }
		return result;
	}

	public void QR_COMPS004_standard_delete(Map<String, Object> map) {
		update("fmComps.QR_COMPS004_standard_delete", map);
	}
	public void QR_COMPS004_standard_insert(Map<String, Object> map) {
		update("fmComps.QR_COMPS004_standard_insert", map);
	}
	public void QR_COMPS004_worker_delete(Map<String, Object> map) {
		update("fmComps.QR_COMPS004_worker_delete", map);
	}
	public void QR_COMPS004_worker_insert(Map<String, Object> map) {
		update("fmComps.QR_COMPS004_worker_insert", map);
	}
	public void QR_COMPS004_work_delete(Map<String, Object> map) {
		update("fmComps.QR_COMPS004_work_delete", map);
	}
	public List<?> QR_COMPS004_get_workingday(Map<String, String> map) {
		List<?> list = null;
		switch (map.get("trmGbn")) {
			case "D" :
				list = list("fmComps.QR_COMPS004_get_workingday_day", map);
			case "W" :
				list = list("fmComps.QR_COMPS004_get_workingday_week", map);
				break;
			case "M" :
			case "T" :
			case "Q" :
			case "H" :
				list = list("fmComps.QR_COMPS004_get_workingday", map);
				break;
		}
		return list;
	}



	public void fm_comps004_worker_del(SampleDocMapVO smVO) {
		update("fmComps.QR_COMPS004_G", smVO);
		update("fmMwork.QR_DOCDEL_WORK", smVO);
	}

	public void fm_comps004_work_del(Map<String, String> map) {
		update("fmMwork.deleteWorkByWrkKey", map);
	}

	private void fm_comps004_worker_all_del(String docKey) {
		SampleDocMapVO smVO = new SampleDocMapVO();
		smVO.setUtmTrcKey(docKey);

		update("fmComps.QR_COMPS004_G", smVO);
		update("fmMwork.QR_DOCDEL_WORK", smVO);

		FileVO fileVO = new FileVO();
		fileVO.setUmf_tbl_gbn("DOC");
		fileVO.setUmf_con_gbn("5");
		fileVO.setUmf_con_key(docKey);
		update("fileup.QR_FILE_DELETE_BYCON", fileVO);
	}

	public List<?> fm_comps004_getCtrMapList(SearchVO searchVO) {
		List<?> list = list("fmComps.QR_COMPS004_K", searchVO);
		return list;
	}

	public List<?> fm_comps004_GetDepth(SearchVO searchVO) {
		List<?> list = list("fmComps.QR_COMPS004_L", searchVO);
		return list;
	}


	public List<?> fm_file(FileVO fvo) {
		return list("fileup.QR_FILE_LIST",fvo);
	}

	/**
	 * 시스템설정 - 통제항목 설정 - 서비스 리스트
	 * @return
	 */
	public List<?> fm_comps005_getStdList(SearchVO searchVO) {
		List<?> list = list("fmComps.QR_COMPS005_A", searchVO); //
		return list;
	}

	/**
	 * 시스템설정 - 통제항목 설정 - tree, cntr1 select
	 * @param searchVO
	 * @return
	 */
	public List<?> fm_comps005_getCntr1List(SearchVO searchVO) {

		List<?> list = list("fmComps.QR_COMPS005_B", searchVO); //
		return list;
	}

	/**
	 * 시스템설정 - 통제항목 설정 - tree, cntr2 select
	 * @param searchVO
	 * @return
	 */
	public List<?> fm_comps005_getCntr2List(SearchVO searchVO) {
		List<?> list = list("fmComps.QR_COMPS005_C", searchVO); //
		return list;
	}

	/**
	 * 시스템설정 - 통제항목 설정 - tree, cntr3 select
	 * @param searchVO
	 * @return
	 */
	public List<?> fm_comps005_getCntr3List(SearchVO searchVO) {
		List<?> list = list("fmComps.QR_COMPS005_D", searchVO); //
		return list;
	}

	/**
	 * 시스템설정 - 통제항목 설정 - tree, cntr4 select
	 * @param searchVO
	 * @return
	 */
	public List fm_comps005_getCntr4List(SearchVO searchVO) {
		List<?> list = list("fmComps.QR_COMPS005_E", searchVO); //
		return list;
	}

	/**
	 * 시스템설정 - 통제항목 설정 - tree, cntr5 select
	 * @param searchVO
	 * @return
	 */
	public List fm_comps005_getCntr5List(SearchVO searchVO) {
		List<?> list = list("fmComps.QR_COMPS005_F", searchVO); //
		return list;
	}

	/**
	 * 시스템설정 - 통제항목 설정 - 증적양식, 통제항목 매핑
	 * @param searchVO
	 * @return
	 */
	public void fm_comps005_setMapping_save(Map<String, String> map) {
		insert("fmComps.QR_COMPS005_G", map);
	}

	public void fm_comps005_delMappingSampleDocList(SearchVO searchVO) {
		update("fmComps.QR_COMPS005_I", searchVO);
	}

	/**
	 * 시스템설정 - 통제항목 설정 - 매핑 된 증적양식 리스트 select
	 * @param searchVO
	 * @return
	 */
	public List<?> fm_comps005_getMappingSampleDocList(SearchVO searchVO) {
		List<?> list = list("fmComps.QR_COMPS005_H", searchVO); //
		return list;
	}

	/**
	 * 시스템설정 - 통제항목 설정 - 관련문서 리스트 select
	 * @param utcMapKey
	 * @return
	 */
	public List<?> fm_comps005_getGuideList(String ctrKey) {
		List<?> list = list("fmComps.QR_COMPS005_J", ctrKey);
		return list;
	}

	public String fm_comps005_getUcmCtrKey(SearchVO searchVO) {
		String ucmCtrKey = (String) select("fmComps.QR_COMPS005_K", searchVO);
		return ucmCtrKey;
	}

	public List<?> fm_comps005_popup(SearchVO searchVO) {
		List<?> list = list("fmComps.QR_COMPS005_L", searchVO);
		return list;
	}

	public void fm_comps004_insertWorker(SampleDocMapVO sampleDocMapVO) {
		insert("fmComps.QR_COMPS004_E", sampleDocMapVO);
	}

	public int fm_comps004_workerCheck(SampleDocMapVO sampleDocMapVO) {
		return (Integer) select("fmComps.QR_COMPS004_WORKER_CHECK", sampleDocMapVO);
	}

	public List<?> fm_comps003_report() {
		List<?> list = list("fmComps.QR_COMPS003_REPORT"); //
		return list;
	}

	public List<?> fm_comps003_grid(Map map) {
		List<?> list = list("fmComps.QR_COMPS003_GRID",map); //
		return list;
	}

	public String getWorkKey() {
		return (String) select("fmMwork.QR_SELECT_SEQ");
	}

	public int fm_comps004_insertWork(WorkVO workVO) {
		return update("fmMwork.QR_INSERT_WORK", workVO);			// INSERT UWO_TRC_WRK
	}

	public String getDeptByUser(String userKey) {
		return (String) select("fmMwork.QR_USER_DEPT", userKey);
	}

	public UserVO getUserAgnCheck(String userKey) {
		return (UserVO) select("fmMwork.QR_SELECT_AGN_CHECK", userKey);
	}

	public List getSvcList() {
		List<?> list = list("fmComps.QR_GET_SERVICE_LIST"); //
		return list;
	}

	public List<?> fm_comps004_member(SearchVO searchVO) {
		List<?> list = list("fmComps.QR_COMPS004_MEM",searchVO);
		return list;
	}

	public List<?> fm_comps004_dept(SearchVO searchVO) {
		List<?> list = list("fmComps.QR_COMPS004_DEP",searchVO);
		return list;
	}

	public EgovMap nonWorkYn(String completeDate) {
		return (EgovMap) select("fmComps.NON_WORK_YN", completeDate);
	}

	public List<?> fmComps003_service_M(Map map) {
		List<?> list = list("fmComps.QR_COMPS003_SERVICE_M",map); //
		return list;
	}

	public void rm_comps003_service_delete(Map map) {
		delete("fmComps.QR_COMPS003_SERVICE_DELETE", map);
	}

	public void rm_comps003_service_update(Map map) {
		insert("fmComps.QR_COMPS003_SERVICE_UPDATE", map);
	}

	public void rm_comps003_service_insert(Map map) {
		insert("fmComps.QR_COMPS003_SERVICE_INSERT", map);
	}

	public String fm_selectWrkIdsByTrcKey(String trcKey) {
		return (String) select("fmMwork.selectWrkIdsByTrcKey", trcKey);
	}



	/*
	 * 2016-10-27, 정보보호활동 업로드. 코드명을 코드로 변환
	 */
	public EgovMap fm_comps004_selectCode(Map<String, String> map) {
		return (EgovMap) select("fmComps.QR_COMP004_selectCode", map);
	}


	/*
	 * 2016-10-27, 정보보호활동 통제항목번호를 ctrKey로 변환
	 */
	public String QR_COMPS004_getCtrKeyByCode(Map<String, String> map) {
		return (String) select("fmComps.QR_COMPS004_getCtrKeyByCode", map);
	}

	/*
	 * 2016-10-27, 사번을 usrKey로 변환
	 */
	public String QR_COMPS004_getUsrKeyById(Map<String, String> map) {
		return (String) select("fmComps.QR_COMPS004_getUsrKeyById", map);
	}

	public int QR_COMPS004_updateWorkLastInsertDate(Map<String, Object> map) {
		return (int) update("fmComps.QR_COMPS004_updateWorkLastInsertDate", map);
	}

	/*
	 * 2017-05-19
	 */
	public void QR_COMPS004_change_work_delete(Map<String, Object> map) {
		update("fmComps.QR_COMPS004_change_work_delete", map);
	}

	public void QR_COMPS004_del_doc(Map<String, Object> map) {
		delete("fmComps.QR_COMPS004_del_doc", map);
	}

	/*
	 * 2018-03-21 s, 컴플라이언스 항목 삭제
	 */
	public void QR_COMPS003_del_comps(Map<String, String> map) {
		delete("fmComps.QR_COMPS003_del_ctr_map", map);
		delete("fmComps.QR_COMPS003_del_trc_ctr", map);
		String stndKind = String.valueOf(map.get("stndKind"));
		if(stndKind.equals("infra_mp")){
			excelNewDAO.fm_comps003_del_all_exp_data_infra_mp(map);
		}else if(stndKind.equals("msit")){
			excelNewDAO.fm_comps003_del_all_exp_data_msit(map);
		}else if(stndKind.equals("infra_la")){
			excelNewDAO.fm_comps003_del_all_exp_data_infra_la(map);
		}
		delete("fmComps.QR_COMPS003_del_ctr_mtr", map);
	}


	/*
	 * 2018-07-17 s, 업무삭제
	 */
	public void QR_COMPS_del_work(Map<String, Object> map) {
		delete("fmComps.QR_COMPS_del_work", map);
	}

	/*
	 * 2020-04-02 서비스 동기화
	 */
	public void QR_COMPS004_updateSameService(Map<String, Object> map) {
		update("fmComps.QR_COMPS004_updateSameService", map);
	}


	public List<?> getComCodPrefix() {
		List<?> list = list("fmComps.QR_COM_COD_PREFIX");
		return list;
	}

	public CntrVO getControlItemByKey(int controlKey) {
		Map<String, Object> map = new HashMap<>();
		map.put("ucm_ctr_key", controlKey);
		return (CntrVO) select("fmComps.getBaseControlItem", map);
	}

	public String getKindOfCompliance(String complianceCode) {
		return (String) select("fmComps.getKindOfCompliance", complianceCode);
	}

	public List<EgovMap> getControlItemList(Map map) {
		return (List<EgovMap>) list("fmComps.getControlItemList", map);
	}

	public List<EgovMap> getComplianceListPerServiceByCycleCode(String cycleCode) {
		return (List<EgovMap>) list("fmComps.getComplianceListPerService", cycleCode);
	}


}
