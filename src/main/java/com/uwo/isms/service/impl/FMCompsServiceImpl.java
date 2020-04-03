package com.uwo.isms.service.impl;


import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.uwo.isms.dao.CommonCodeDAO;
import com.uwo.isms.domain.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.hsqldb.lib.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.uwo.isms.common.CommonConfig;
import com.uwo.isms.common.Constants;
import com.uwo.isms.common.EventTitleMessage;
import com.uwo.isms.dao.FMCompsDAO;
import com.uwo.isms.service.CommonUtilService;
import com.uwo.isms.service.FMCompsService;
import com.uwo.isms.service.SendMailService;
import com.uwo.isms.util.EgovDateUtil;
import com.uwo.isms.web.FMCompsController;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service("fmCompsService")
public class FMCompsServiceImpl implements FMCompsService {

	Logger log = LogManager.getLogger(FMCompsController.class);

	@Resource(name = "fmCompsDAO")
	private FMCompsDAO fmCompsDAO;

	@Resource(name = "sendMailService")
	SendMailService sendMailService;

	@Autowired
	CommonUtilService commonUtilService;

	@Autowired
	CommonCodeDAO commonCodeDAO;

	@Override
	public List<?> fm_comps001_list(SearchVO searchVO) {
		return fmCompsDAO.fm_comps001_list(searchVO);
	}

	@Override
	public String fm_comps001_getManCylMax() {
		return fmCompsDAO.fm_comps001_getManCylMax();
	}

	@Override
	public int fm_comps001_cnt(SearchVO searchVO) {
		return fmCompsDAO.fm_comps001_cnt(searchVO);
	}

	@Override
	public CycleVO fm_comps001_read(CycleVO vo) {
		return fmCompsDAO.fm_comps001_read(vo);
	}


	@Override
	public void fm_comps001_write(CycleVO vo) {
		fmCompsDAO.fm_comps001_write(vo);
	}


	@Override
	public void fm_comps001_update(CycleVO vo) {
		fmCompsDAO.fm_comps001_update(vo);
	}

	@Override
	public List<?> fm_comps001_list_new(SearchVO searchVO) {
		return fmCompsDAO.fm_comps001_list_new(searchVO);
	}

	@Override
	public List<?> fm_comps002_list(SearchVO searchVO) {
		return fmCompsDAO.fm_comps002_list(searchVO);
	}

	@Override
	public int fm_comps002_cnt(SearchVO searchVO) {
		return fmCompsDAO.fm_comps002_cnt(searchVO);
	}

	@Override
	public EgovMap fm_comps002_stnd_info(String strStndCod) {
		return fmCompsDAO.fm_comps002_stnd_info(strStndCod);
	}

	@Override
	public List<?> fm_comps002_org_list(String strStndCod) {
		return fmCompsDAO.fm_comps002_org_list(strStndCod);
	}

	@Override
	public EgovMap fm_comps002_org_info(Map<String, String> map) {
		return fmCompsDAO.fm_comps002_org_info(map);
	}

	@Override
	public String fm_comps002_stnd_insert(Map<String, Object> map) {
		if(fmCompsDAO.fm_comps002_stnd_cod_cnt(map) > 0){
			return Constants.RETURN_DUP;
		} else {
			fmCompsDAO.fm_comps002_stnd_insert(map);
			return Constants.RETURN_SUCCESS;
		}
	}

	@Override
	public String fm_comps002_stnd_update(Map<String, Object> map) {
		if(fmCompsDAO.fm_comps002_stnd_cod_cnt(map) > 0){
			return Constants.RETURN_DUP;
		} else {
			fmCompsDAO.fm_comps002_stnd_update(map);
			return Constants.RETURN_SUCCESS;
		}
	}

	@Override
	public String fm_comps002_org_insert(Map<String, String> map) {
		if(fmCompsDAO.fm_comps002_org_cod_cnt(map) > 0){
			return Constants.RETURN_DUP;
		} else {
			fmCompsDAO.fm_comps002_org_insert(map);
			return Constants.RETURN_SUCCESS;
		}
	}

	@Override
	public String fm_comps002_org_update(Map<String, String> map) {
		if(fmCompsDAO.fm_comps002_org_cod_cnt(map) > 0){
			return Constants.RETURN_DUP;
		} else {
			fmCompsDAO.fm_comps002_org_update(map);
			return Constants.RETURN_SUCCESS;
		}
	}


	@Override
	public String fm_comps003_key(String key) {
		return fmCompsDAO.fm_comps003_key(key);
	}

	@Override
	public List<?> fm_comps003_list(Map map) {
		return fmCompsDAO.fm_comps003_list(map);
	}

	@Override
	public List<?> fm_comps003_2D_list(Map map) {
		return fmCompsDAO.fm_comps003_2D_list(map);
	}

	@Override
	public List<?> fm_comps003_3D_list(Map map) {
		return fmCompsDAO.fm_comps003_3D_list(map);
	}

	@Override
	public void fm_comps003_insert(Map map) {
		fmCompsDAO.fm_comps003_insert(map);
	}

	@Override
	public void fm_comps003_update(Map map) {
		fmCompsDAO.fm_comps003_update(map);
	}

	@Override
	public void fm_comps003_2D_insert(Map map) {
		fmCompsDAO.fm_comps003_2D_insert(map);
	}

	@Override
	public void fm_comps003_2D_update(Map map) {
		fmCompsDAO.fm_comps003_2D_update(map);
	}

	@Override
	public void fm_comps003_3D_insert(Map map) {
		fmCompsDAO.fm_comps003_3D_insert(map);
	}

	@Override
	public void fm_comps003_3D_update(Map map) {
		fmCompsDAO.fm_comps003_3D_update(map);
	}

	@Override
	public List<?> fm_comps003_popup(Map<String, String> map) {
		return fmCompsDAO.fm_comps003_popup(map);
	}

	@Override
	public List<?> fm_comps003_popup_div() {
		return fmCompsDAO.fm_comps003_popup_div();
	}


	@Override
	public List<?> fm_comps003_3D_mappinglist() {
		return fmCompsDAO.fm_comps003_3D_mappinglist();
	}

	@Override
	public void fm_comps003_popup_mapping(Map map) {
		fmCompsDAO.fm_comps003_popup_mapping(map);
	}

	@Override
	public void fm_comps003_del_mapping(String brdKey) {
		fmCompsDAO.fm_comps003_del_mapping(brdKey);
	}


	@Override
	public BoardVO fm_comps003_popup_brdView(String key) {
		return fmCompsDAO.fm_comps003_popup_brdView(key);
	}


	@Override
	public List<?> fm_comps003_popup_brdFile(String key) {
		return fmCompsDAO.fm_comps003_popup_brdFile(key);
	}
/*
	@Override
	public List<?> fm_comps003_stdlist() {
		return fmCompsDAO.fm_comps003_stdlist();
	}

	@Override
	public List<?> fm_comps003_svclist() {
		return fmCompsDAO.fm_comps003_svclist();
	}

	@Override
	public String fm_comps003_excel_insert(HttpServletRequest req, List list) throws Exception {
		MultipartHttpServletRequest mreq=(MultipartHttpServletRequest) req;

		InputStream file=commonUtilService.decryptFileDAC(mreq.getFile("excelFile"));
		Workbook wb=WorkbookFactory.create(file);
		if(wb.getNumberOfSheets() < 2){
			return Constants.RETURN_FAIL;
		}

		for(int i=0; i<wb.getNumberOfSheets(); i++){  //20151118 PJH 엑셀 업로드 1시트 insert 제외되는 문제 수정(1->0)
			Sheet sheet=wb.getSheetAt(i);
			Map<String, String> map=new HashMap<String, String>();
			int lvl3Ctn=1;

			for(int j=5; j<=sheet.getLastRowNum(); j++){
				Row row=sheet.getRow(j);
				if(row!=null &&
						row.getCell(1)!=null && row.getCell(2)!=null && row.getCell(3)!=null && row.getCell(4)!=null &&
						row.getCell(5)!=null && row.getCell(5)!=null && row.getCell(7)!=null && row.getCell(6)!=null){

					map.put("UCM_BCY_COD",(String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
					map.put("UCM_CTR_GBN", req.getParameter("standard"));
					map.put("UCM_RGT_ID",(String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
					map.put("UCM_LVL_GBN", Constants.UCM_LVL_GBN);

					if(StringUtils.isNotEmpty(row.getCell(1).toString())){
						map.put("UCM_CTR_COD", row.getCell(1).toString().split("\\.")[0]);
						map.put("UCM_1LV_COD", row.getCell(1).toString());
						map.put("UCM_1LV_NAM", row.getCell(2).toString());
						map.put("UCM_1LD_YN", Constants.USE_N);
					}

					if(StringUtils.isNotEmpty(row.getCell(3).toString())){
						map.put("UCM_2LV_COD", row.getCell(3).toString());
						map.put("UCM_2LV_NAM", row.getCell(4).toString());
						map.put("UCM_2LV_DTL", row.getCell(5).toString());
						map.put("UCM_2LD_YN", Constants.USE_N);
						lvl3Ctn=1;
					}

					map.put("UCM_3LV_COD", map.get("UCM_2LV_COD")+"."+lvl3Ctn);
					map.put("UCM_3LV_NAM", row.getCell(6).toString());
					map.put("UCM_3LV_DTL", row.getCell(7).toString());
					map.put("UCM_3LD_YN", Constants.USE_N);

					String ctrKey=fmCompsDAO.fm_comps003_getCtrKey(map);
					if(StringUtils.isNotEmpty(ctrKey)){
						map.put("ctrKey", ctrKey);
						fmCompsDAO.fm_comps003_excelUpdate(map, list);
					}else{
						fmCompsDAO.fm_comps003_excelInsert(map, list);
					}
					lvl3Ctn++;
				}
			}
		}
		return Constants.RETURN_SUCCESS;
	}*/

	@Override
	public List<?> fm_comps004_GetDepth(SearchVO searchVO) {
		return fmCompsDAO.fm_comps004_GetDepth(searchVO);
	}

	@Override
	public List<?> fm_comps004_getCtrMapList(SearchVO searchVO) {
		return fmCompsDAO.fm_comps004_getCtrMapList(searchVO);
	}

	@Override
	public List<?> fm_file(FileVO fvo) {
		return fmCompsDAO.fm_file(fvo);
	}

	/* 2016-07-14 add comment
	@Override
	public void fm_comps004_worker_del(String[] id, String utmTrcKey) {

		for(int i=0; i<id.length; i++){
			String del_id = id[i];
			log.debug("삭제 할 업무담당자 ID :: "+ del_id);

			SampleDocMapVO smVO = new SampleDocMapVO();
			smVO.setUtmTrcKey(utmTrcKey);
			smVO.setUtmWrkId(del_id);

			fmCompsDAO.fm_comps004_worker_del(smVO);
		}
	}*/

	/* 2016-07-14 add comment
	@Override
	public void fm_comps004_work_del(String[] id, String utmTrcKey) {

		Map<String, String> map = new HashMap<String, String>();
		map.put("utmTrcKey", utmTrcKey);

		for (int i = 0; i < id.length; i++) {
			String utwWrkKey = id[i];
			log.debug("삭제 할 업무 ID :: "+ utwWrkKey);
			map.put("utwWrkKey", utwWrkKey);
			fmCompsDAO.fm_comps004_work_del(map);
		}
	}*/

	@Override
	public SampleDocVO fm_comps004_sampleDocInfo(SearchVO searchVO) {

		SampleDocVO vo = fmCompsDAO.fm_comps004_sampleDocInfo(searchVO);
		return vo;
	}

	@Override
	public int fm_comps004_sampleDoc_save(SampleDocVO sampleDocVO, List<FileVO> list) {

		String mode = sampleDocVO.getMode();
		int result = 0;

		// 업무기간 = 업무종료일 - 업무시작일
		if (sampleDocVO.getUtdEndDat() == null) {
			sampleDocVO.setUtdEndDat(sampleDocVO.getUtdCmpDat());
		}
		int diff = EgovDateUtil.getDaysDiff(sampleDocVO.getUtdStrDat(), sampleDocVO.getUtdEndDat());
		sampleDocVO.setUtdWrkEnd(diff+"");

		// mode에 따라 분기처리
		if(mode.equals("add")){
			if(sampleDocVO.getUtdSvcCod().isEmpty()){
				List<?> svcList = fmCompsDAO.getSvcList();
				for(int i=0; i< svcList.size();i++){
					EgovMap map = (EgovMap)svcList.get(i);
					sampleDocVO.setUtdSvcCod((String)map.get("cod"));
					result = fmCompsDAO.fm_comps004_add(sampleDocVO, list);
				}
			}else{
				result = fmCompsDAO.fm_comps004_add(sampleDocVO, list);
			}
		}
		else if(mode.equals("update")){
	        result = fmCompsDAO.fm_comps004_update(sampleDocVO, list);
		}
		return result;
	}

	@Override
	public void fm_comps004_standard_change(Map<String, Object> map) {
		String[] ids = (String[]) map.get("ids");
		String hasIds = ids.length > 0 && !ids[0].equals("") ? "1" : null;
		map.put("hasIds", hasIds);
		fmCompsDAO.QR_COMPS004_standard_delete(map);
		if (hasIds != null) {
			fmCompsDAO.QR_COMPS004_standard_insert(map);
		}
	}

	@Override
	public void fm_comps004_worker_change(Map<String, Object> map) {
		String[] ids = (String[]) map.get("ids");
		String hasIds = ids.length > 0 && !ids[0].equals("") ? "1" : null;
		map.put("hasIds", hasIds);
		fmCompsDAO.QR_COMPS004_worker_delete(map);
		if (hasIds != null) {
			fmCompsDAO.QR_COMPS004_worker_insert(map);
		}
	}

	@Override
	public void fm_comps004_work_delete(Map<String, Object> map) {
		String[] ids = (String[]) map.get("ids");
		String hasIds = ids.length > 0 && !ids[0].equals("") ? "1" : null;
		map.put("hasIds", hasIds);
		fmCompsDAO.QR_COMPS004_work_delete(map);
	}

	/*
	 * 2017-02-17
	 * 		add doNotSendMail
	 * 		mod return resultIds
	 */
	@Override
	public SampleDocVO fm_comps004_work_insert(Map<String, Object> map) throws Exception {

		HashSet<String> resultIds = new HashSet<String>();

		SearchVO searchVO = new SearchVO();
		searchVO.setDocCode(map.get("trcKey").toString());
		SampleDocVO sampleDocVO = fmCompsDAO.fm_comps004_sampleDocInfo(searchVO);
		sampleDocVO.setUtdRgtId(map.get("rgtId").toString());
		// worker
		String[] ids = (String[]) map.get("ids");
		String hasIds = ids.length > 0 && !ids[0].equals("") ? "1" : null;

		if (sampleDocVO.getUtdDelYn().equals("N") && hasIds != null) {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date docStartDate = sdf.parse(sampleDocVO.getUtdStrDat());
			Date docEndDate = sdf.parse(sampleDocVO.getUtdCmpDat());	// 2016-10-28, endDat -> cmpDat
			Date now = new Date();

			Calendar startDate = Calendar.getInstance();
			Calendar endDate = Calendar.getInstance();

			startDate.setTime(docStartDate);

			// 업무마감일이 오늘 이전일 경우 문서종료일까지 업무할당. 아닐 경우 오늘까지
			if (now.compareTo(docEndDate) > 0) {
				endDate.setTime(docEndDate);
			}
			else {
				endDate.setTime(now);
			}

			// 비주기 업무생성
			// 2016-10-28, getUtdDtrGbn 추가
			if (sampleDocVO.getUtdTrmGbn().equals("Y") || sampleDocVO.getUtdTrmGbn().equals("N") || sampleDocVO.getUtdDtrGbn().equals("Y")) {
				resultIds = CreateWork(sampleDocVO, ids);
			}
			// 주기 업무생성
			else {
				// trmGbn으로 업무 시작/종료일 가져옴
				Map<String, String> map2 = new HashMap<String, String>();
				map2.put("startDate", sdf.format(startDate.getTime()));
				map2.put("endDate", sdf.format(endDate.getTime()));

				// 증적등록주기가 별도로 설정되어 있을 경우 업무주기를 대체함
				if (sampleDocVO.getUtdDtrGbn().equals("E")) {
					map2.put("trmGbn", sampleDocVO.getUtdTrmGbn());
				}
				else {
					map2.put("trmGbn", sampleDocVO.getUtdDtrGbn());
				}
				List<?> workingList = fmCompsDAO.QR_COMPS004_get_workingday(map2);

				if (workingList != null) {
					for (int i = 0; i < workingList.size(); i++) {
						EgovMap item = (EgovMap)workingList.get(i);

						// 업무 시작일이 시작기준일 이전일 경우 continue
						Date tmpDate = sdf.parse(item.get("startDate").toString());
						if (docStartDate.compareTo(tmpDate) > 0) {
							continue;
						}

						sampleDocVO.setUtdStrDat(item.get("startDate").toString());
						sampleDocVO.setUtdCmpDat(item.get("endDate").toString());
						resultIds = CreateWork(sampleDocVO, ids);
					}
				}	// //if (workingList != null) {
			}

			// sendmail
			// 2017-02-17, add doNotSendMail
			if (sampleDocVO.getUtdSdmYn().equals("Y") && map.get("doNotSendMail") == null) {
				// UWO_EVT_ARM INSERT
				// 업무추가(E01)
				String key = sampleDocVO.getUtdRgtId();
				String eventCode = EventTitleMessage.E01_CODE;
				String title = EventTitleMessage.E01;
				/*String contents = title + "<p />해당내용을 확인해주세요.<br /> ■ 업무명 : " + sampleDocVO.getUtdDocNam()
		        		+ "<br /> ■ 업무주기 : " + sampleDocVO.getUtdTrmGbnTxt() + "<br /> ■ 업무기한 : " + sampleDocVO.getUtdStrDat() + " ~ " + sampleDocVO.getUtdCmpDat(); */
				// 2017-09-11, text to html
				String contents = new StringBuffer()
						.append("<tr>")
						.append("<td width=\"436\" style=\"border-width:1; border-color:gray; border-style:solid;\">")
						.append("<p align=\"center\" style=\"line-height:120%; margin-top:0; margin-bottom:0;\">")
						.append(sampleDocVO.getUtdDocNam())
						.append("</p>")
						.append("</td>")
						.append("<td width=\"77\" style=\"border-width:1; border-color:gray; border-style:solid;\">")
						.append("<p align=\"center\" style=\"line-height:120%; margin-top:0; margin-bottom:0;\">")
						.append(sampleDocVO.getUtdTrmGbnTxt())
						.append("</p>")
						.append("</td>")
						.append("<td width=\"111\" style=\"border-width:1; border-color:gray; border-style:solid;\">")
						.append("<p align=\"center\" style=\"line-height:120%; margin-top:0; margin-bottom:0;\">")
						.append(sampleDocVO.getUtdCmpDat())
						.append("</p>")
						.append("</td>")
						.append("</tr>").toString();

				// 2017-09-11, email template
				Map<String, String> map2 = new HashMap<String, String>();
				map2.put("uccFirCod", "evtArm");
				map2.put("uccSndCod", EventTitleMessage.E01_CODE);
				EgovMap emap = commonUtilService.getUccComCodeInfo(map2);
				String mailTemplate = (String) emap.get("uccEtc");

				String mailBody = mailTemplate.replace("{__BODY__}", contents.toString());

				if (resultIds != null) {
					for (String userKey : resultIds) {
						//log.info("id: {}, content: {}", ids, mailBody);
						sendMailService.sendMail(key, eventCode, title, mailBody, userKey);
					}
				}
			}

		}	// //if (sampleDocVO.getUtdDelYn().equals("N")) {

		sampleDocVO.setUtwWrkIds(resultIds);
		return sampleDocVO;
	}

	/*
	 * 2017-02-17, mod 업무가 신규 등록된 담당자 id를 hashSet으로 return
	 */
	@Override
	public HashSet<String> CreateWork(SampleDocVO vo, String userKey[]) {

		HashSet<String> resultSet = new HashSet<String>();

		for (String key : userKey) {

			WorkVO workVO = new WorkVO();
			UserVO uservo = new UserVO();
			uservo = fmCompsDAO.getUserAgnCheck(key);

			workVO.setUtw_trc_key(vo.getUtdTrcKey());
			workVO.setUtw_prd_cod(vo.getUtdBcyCod());
			workVO.setUtw_trm_cod(vo.getUtdTrmGbn());
			// 2017-07-25
			workVO.setUtd_dtr_gbn(vo.getUtdDtrGbn());

			// 업무 생성시 상태는 10 : 미완료
			workVO.setUtw_wrk_sta("10");
			workVO.setUtw_str_dat(vo.getUtdStrDat());
			workVO.setUtw_end_dat(vo.getUtdCmpDat());	// 2016-10-28, endDat -> cmpDat
			workVO.setUtw_div_cod(vo.getUtdDivCod());
			workVO.setUtw_svc_cod(vo.getUtdSvcCod());
			workVO.setUtw_rgt_id(vo.getUtdRgtId());
			workVO.setUtw_wrk_id(key);

			// 대무자가 존재하고 Sysdate가 대무일 사이에 유효하다면
			// 업무 생성시 대무자여부 : 'N'으로 셋팅
			if ("Y".equals(uservo.getAgnDateYn())) {
				workVO.setUtw_agn_id(uservo.getUumAgnId());
				workVO.setUtw_agn_yn("Y");
			} else {
				workVO.setUtw_agn_yn("N");
			}

			int result = fmCompsDAO.fm_comps004_insertWork(workVO);
			if (result > 0) {
				resultSet.add(key);
			}
		}

		return resultSet;
	}

	/**
	 *  insert 될 업무담당자 list
	 * @param //SampleDocVO vo
	 * @return SampleDocMapVO mapVO
	 */
	public List<SampleDocMapVO> getWorkerList(SampleDocVO vo){
		log.debug("########## getWorkerList start ###########");
		String[] worker = vo.getUtmWrkId().split("\\,");
		String[] div = vo.getUtmDivCod().split("\\,");
		String[] svc = vo.getUtmSvcCod().split("\\,");
		String[] dep = vo.getUtmDepCod().split("\\,");
		int cnt = worker.length;

		List<SampleDocMapVO> workerList = new ArrayList<SampleDocMapVO>();
		for(int i=0; i<cnt; i++){
			if(!worker[i].equals("")){
				SampleDocMapVO mapVO = new SampleDocMapVO();

				mapVO.setUtmBcyCod(vo.getUtdBcyCod());
				mapVO.setUtmDelYn("N");
				mapVO.setUtmTrcKey(vo.getUtdTrcKey());
				mapVO.setUtmDwnYn("Y");
				mapVO.setUtmWrkId(worker[i]);
				mapVO.setUtmDivCod(div[i]);
				mapVO.setUtmSvcCod(svc[i]);
				mapVO.setUtmDepCod(dep[i]);
				mapVO.setUtmRgtId(vo.getUtdRgtId());
				mapVO.setUtmUptId(vo.getUtdUptId());

				workerList.add(mapVO);
			}
		}
		return workerList;
	}

	@Override
	public List<?> fm_comps004(SearchVO searchVO) {
		return fmCompsDAO.fm_comps004(searchVO);
	}

	@Override
	public int fm_comps004_cnt(SearchVO searchVO) {
		return fmCompsDAO.fm_comps004_cnt(searchVO);
	}

	@Override
	public List<?> fm_comps004_workerList(String utdTrcKey) {
		return fmCompsDAO.fm_comps004_workerList(utdTrcKey);
	}


	@Override
	public List<?> fm_comps005_getStdList(SearchVO searchVO) {

		return fmCompsDAO.fm_comps005_getStdList(searchVO);
	}

	@Override
	public List<?> fm_comps005_getCntr1List(SearchVO searchVO) {

		return fmCompsDAO.fm_comps005_getCntr1List(searchVO);
	}

	@Override
	public List<?> fm_comps005_getCntr2List(SearchVO searchVO) {
		return fmCompsDAO.fm_comps005_getCntr2List(searchVO);
	}

	@Override
	public List<?> fm_comps005_getCntr3List(SearchVO searchVO) {
		return fmCompsDAO.fm_comps005_getCntr3List(searchVO);
	}

	@Override
	public List<?> fm_comps005_getCntr4List(SearchVO searchVO) {
		return fmCompsDAO.fm_comps005_getCntr4List(searchVO);
	}

	@Override
	public List<?> fm_comps005_getCntr5List(SearchVO searchVO) {
		return fmCompsDAO.fm_comps005_getCntr5List(searchVO);
	}

	@Override
	public void fm_comps005_setMapping_save(Map<String, String> map) {
		fmCompsDAO.fm_comps005_setMapping_save(map);
	}

	@Override
	public List<?> fm_comps005_getMappingSampleDocList(SearchVO searchVO) {
		return fmCompsDAO.fm_comps005_getMappingSampleDocList(searchVO);
	}

	@Override
	public void fm_comps005_delMappingSampleDocList(SearchVO searchVO) {
		String[] mapKeyList = searchVO.getCode().split("\\,");

		for(int i=0; i< mapKeyList.length; i++){
			searchVO.setCode(mapKeyList[i]);
			if(!mapKeyList[i].equals("")){
				fmCompsDAO.fm_comps005_delMappingSampleDocList(searchVO);
			}
		}
	}

	@Override
	public List<?> fm_comps005_getGuideList(String ctrKey) {
		return fmCompsDAO.fm_comps005_getGuideList(ctrKey);
	}

	@Override
	public String fm_comps005_getUcmCtrKey(SearchVO searchVO) {
		return fmCompsDAO.fm_comps005_getUcmCtrKey(searchVO);
	}

	@Override
	public List<?> fm_comps005_popup(SearchVO searchVO) {
		List<?> list = fmCompsDAO.fm_comps005_popup(searchVO);
		return list;
	}

	/* 2016-07-14 add comment
	@Override
	public void fm_comps004_mapping_del(Map<String, String> map) {
		String[] arrCode = map.get("code").split("\\,");
		for(String code : arrCode) {
			SearchVO searchVO = new SearchVO();
			searchVO.setCode(code);
			searchVO.setWorker(map.get("worker"));
			fmCompsDAO.fm_comps005_delMappingSampleDocList(searchVO);
		}

	}*/

	/*
	@Override
	public void fmComps004_worker_save(Map<String, String> map) {
		String[] arrUsrKey = map.get("userKey").split("\\,");
		for(String userKey : arrUsrKey) {
			SampleDocMapVO sampleDocMapVO = new SampleDocMapVO();
			sampleDocMapVO.setUtmTrcKey(map.get("utmTrcKey"));
			sampleDocMapVO.setUtmBcyCod(map.get("utmBcyCod"));
			sampleDocMapVO.setUtmRgtId(map.get("utmRgtId"));
			sampleDocMapVO.setUtmWrkId(userKey);
			sampleDocMapVO.setUtmDelYn("N");
			sampleDocMapVO.setUtmDwnYn("Y");

			if (fmCompsDAO.fm_comps004_workerCheck(sampleDocMapVO) == 0) {
				fmCompsDAO.fm_comps004_insertWorker(sampleDocMapVO);
			}
		}
	}*/

	@Override
	public List<?> fm_comps003_report() {
		return fmCompsDAO.fm_comps003_report();
	}


	@Override
	public List<?> fm_comps003_grid(Map<String, String> map) {
		return fmCompsDAO.fm_comps003_grid(map);
	}

	/* 2016-07-14 add comment
	@Override
	public void CreateWork(Map<String, String> map) {
		String[] arrUsrKey = map.get("userKey").split("\\,");

		SearchVO searchVO = new SearchVO();
		searchVO.setDocCode(map.get("utmTrcKey"));
		SampleDocVO vo = fmCompsDAO.fm_comps004_sampleDocInfo(searchVO);

		// 비주기만 처리함
		if (vo.getUtdTrmGbn().equals("Y") || vo.getUtdTrmGbn().equals("N")) {

			for(String userKey : arrUsrKey) {
				//String workKey = fmCompsDAO.getWorkKey();	// seq
				String dept = fmCompsDAO.getDeptByUser(userKey);

				WorkVO workVO = new WorkVO();
				UserVO uservo = new UserVO();
				uservo = fmCompsDAO.getUserAgnCheck(userKey);

				//workVO.setUtw_wrk_key(workKey);
				workVO.setUtw_trc_key(vo.getUtdTrcKey());
				workVO.setUtw_prd_cod(vo.getUtdBcyCod());
				workVO.setUtw_trm_cod(vo.getUtdTrmGbn());
				// 업무 생성시 상태는 10 : 미완료
				workVO.setUtw_wrk_sta("10");
				workVO.setUtw_str_dat(vo.getUtdStrDat());
				workVO.setUtw_end_dat(vo.getUtdEndDat());
				workVO.setUtw_cmp_dat(vo.getUtdCmpDat());
				workVO.setUtw_div_cod(vo.getUtdDivCod());
				workVO.setUtw_svc_cod(vo.getUtdSvcCod());
				workVO.setUtw_dep_cod(dept);
				workVO.setUtw_wrk_id(userKey);

				if ("Y".equals(uservo.getAgnDateYn())) {		// 대무자가 존재하고 Sysdate가 대무일 사이에 유효하다면
					workVO.setUtw_agn_id(uservo.getUumAgnId());
					workVO.setUtw_agn_yn("Y");
				} else {
					workVO.setUtw_agn_yn("N");
				}

				// 업무 생성시 대무자여부 : 'N'으로 셋팅
				workVO.setUtw_rgt_id(map.get("utmRgtId"));

				fmCompsDAO.fm_comps004_insertWork(workVO);
			}
		}
	}*/

	@Override
	public List<?> fm_comps004_member(SearchVO searchVO) {
		List<?> list = fmCompsDAO.fm_comps004_member(searchVO);
		return list;
	}

	@Override
	public List<?> fm_comps004_dept(SearchVO searchVO) {
		List<?> list = fmCompsDAO.fm_comps004_dept(searchVO);
		return list;
	}

	@Override
	public EgovMap nonWorkYn(String completeDate) {
		return (EgovMap)fmCompsDAO.nonWorkYn(completeDate);
	}

	@Override
	public List<?> fmComps003_service_M(Map<String, String> map) {
		return fmCompsDAO.fmComps003_service_M(map);
	}

	@Override
	public void rm_comps003_service_update(Map<String, Object> map) {
		Map<String, String> hMap = new HashMap<String, String>();
		hMap.put("ucmCtrKey", (String) map.get("ucmCtrKey"));
		fmCompsDAO.rm_comps003_service_delete(hMap);
		if( map.get("serviceList") != null) {
			String[] serviceStr = (String[]) map.get("serviceList");
			for(String service : serviceStr) {
				hMap.put("service", service);
				hMap.put("ucmRgtId", (String) map.get("ucmRgtId"));

				fmCompsDAO.rm_comps003_service_update(hMap);
			}
		}
	}

	@Override
	public void rm_comps003_service_insert(Map<String, Object> map) {
		fmCompsDAO.rm_comps003_service_insert(map);
	}

	/*
	 * (non-Javadoc)
	 * @see com.uwo.isms.service.FMCompsService#fm_comps004_excel_insert(javax.servlet.http.HttpServletRequest)
	 * 2016-10-27, 보호활동 엑셀 업로드
	 */
	@Override
	public String fm_comps004_excel_insert(HttpServletRequest req) throws Exception {
		int firstY = 0;
		int firstX = 0;
		Cell cell = null;
		Date date = null;

		DataFormatter formatter = new DataFormatter(); 						// creating formatter using the default locale
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");			// excel date format
		SimpleDateFormat sdfLocale = new SimpleDateFormat("yyyy-MM-dd");	// locale date format

		Map<String, String> map = null;
		Map<String, Object> objMap = null;

		try {
			MultipartHttpServletRequest mreq = (MultipartHttpServletRequest) req;
			InputStream file = commonUtilService.decryptFileDAC(mreq.getFile("excelFile"));
			Workbook wb = WorkbookFactory.create(file);

			// 첫번째 sheet 만 처리함
			Sheet sheet = wb.getSheetAt(0);

			// 데이터 기준점 [업무코드] 검색
			outerloop:
			for (int y = 0; y <= sheet.getLastRowNum(); y++) {
				Row row = sheet.getRow(y);
		        if (row != null) {
					for (int x = 0; x < row.getLastCellNum(); x++) {
						cell = row.getCell(x);
						if (cell != null && cell.toString().equals("업무코드")) {
							firstY = y + 1;
							firstX = x;
							break outerloop;
						}
					}
		        }
			}

			// 데이터 검색
			for (int y = firstY; y <= sheet.getLastRowNum(); y++) {
				int x = firstX;

				cell = sheet.getRow(y).getCell(x);
				String utdTrcKey = formatter.formatCellValue(cell); 			// 업무코드
				String service = sheet.getRow(y).getCell(++x).toString();		// 서비스
				String utdDocNam = sheet.getRow(y).getCell(++x).toString();		// 업무명
				String sdmYn = sheet.getRow(y).getCell(++x).toString();			// 알림메일
				String utdAppLev = sheet.getRow(y).getCell(++x).toString();		// 결제방법
				String workTerm = sheet.getRow(y).getCell(++x).toString();		// 업무주기
				cell = sheet.getRow(y).getCell(++x);
				String cmpDat = formatter.formatCellValue(cell);				// 마감일자
				if (cell != null && cell.getCellType() == Cell.CELL_TYPE_NUMERIC && DateUtil.isCellDateFormatted(cell)) {
					date = sdf.parse(cmpDat);
					cmpDat = sdfLocale.format(date);
				}
				String dtrGbn = String.valueOf(sheet.getRow(y).getCell(++x));	// 증적등록주기
				String utdDocCnt = sheet.getRow(y).getCell(++x).toString();		// 필요증적갯수
				String delYn = sheet.getRow(y).getCell(++x).toString();			// 사용여부
				cell = sheet.getRow(y).getCell(++x);
				String utdStrDat = formatter.formatCellValue(cell);				// 업무시작일
				if (cell != null && cell.getCellType() == Cell.CELL_TYPE_NUMERIC && DateUtil.isCellDateFormatted(cell)) {
					date = sdf.parse(utdStrDat);
					utdStrDat = sdfLocale.format(date);
				}
				cell = sheet.getRow(y).getCell(++x);
				String utdEndDat = formatter.formatCellValue(cell);				// 업무종료일
				if (cell != null && cell.getCellType() == Cell.CELL_TYPE_NUMERIC && DateUtil.isCellDateFormatted(cell)) {
					date = sdf.parse(utdEndDat);
					utdEndDat = sdfLocale.format(date);
				}

				// 2017-09-12, 마감일이 없는 경우 업무 종료일로 대체
				if (StringUtil.isEmpty(cmpDat)) {
					cmpDat = utdEndDat;
				}

				String docGbnNm = sheet.getRow(y).getCell(++x) == null ? "" : sheet.getRow(y).getCell(x).toString();		// 문서유형
				String utdDocEtc = sheet.getRow(y).getCell(++x) == null ? "" : sheet.getRow(y).getCell(x).toString();		// 업무설명
				String ctrs = sheet.getRow(y).getCell(++x) == null ? "" : sheet.getRow(y).getCell(x).toString();			// 통제항목
				x++; // 업무담당자 Skip
				cell = sheet.getRow(y).getCell(++x);
				String workers = formatter.formatCellValue(cell); 				// 업무담당자(사번)

				sdmYn = sdmYn != null && (StringUtils.equalsIgnoreCase(sdmYn, "Y") || sdmYn.equals("사용")) ? "Y" : "N";
				delYn = delYn != null && (StringUtils.equalsIgnoreCase(delYn, "N") || delYn.equals("사용")) ? "N" : "Y";

				map = new HashMap<String, String>();
				map.put("service", service);
				map.put("utdAppLev", utdAppLev);
				map.put("workTerm", workTerm);
				map.put("dtrGbn", dtrGbn);
				map.put("docGbnNm", docGbnNm);

				// 코드명을 코드로 변환
				EgovMap resultCode = fmCompsDAO.fm_comps004_selectCode(map);
				service = resultCode.get("service").toString();
				utdAppLev = resultCode.get("utdapplev").toString();
				workTerm = resultCode.get("workterm").toString();
				dtrGbn = resultCode.get("dtrgbn").toString();
				docGbnNm = resultCode.get("docgbnnm").toString();

				String utdAprYn = utdAppLev.equals("0") ? "N" : "Y";	// 결재방법(자가,승인)
				utdDocEtc = utdDocEtc.replaceAll("\\r?\\n", "<br />");	// 설명 줄바꿈

				if (workTerm.equals(dtrGbn)) {	// 업무주기와 증적등록 주기가 같을 경우
					dtrGbn = "E";
				}

				String ctrCodeData[] = ctrs.split("\\r?\\n|,");
				String workerNoData[] = workers.split("\\r?\\n|,");


				SampleDocVO sampleDocVO = new SampleDocVO();
				if (utdTrcKey != null && utdTrcKey != "") {
					sampleDocVO.setMode("update");
					sampleDocVO.setUtdTrcKey(utdTrcKey);
				}
				else {
					sampleDocVO.setMode("add");
				}

				sampleDocVO.setUtdBcyCod(req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY).toString());
				sampleDocVO.setUtdDocNam(utdDocNam);
				sampleDocVO.setUtdDocCnt(utdDocCnt);
				sampleDocVO.setUtdDocGbn(docGbnNm);
				sampleDocVO.setUtdTrmGbn(workTerm);
				sampleDocVO.setUtdDtrGbn(dtrGbn);
				sampleDocVO.setUtdDivCod(Constants.DIV_COD);
				sampleDocVO.setUtdSvcCod(service);
				sampleDocVO.setUtdDelYn(delYn);
				sampleDocVO.setUtdAprYn(utdAprYn);
				sampleDocVO.setUtdAppLev(utdAppLev);
				sampleDocVO.setUtdStrDat(utdStrDat);
				sampleDocVO.setUtdEndDat(utdEndDat);
				sampleDocVO.setUtdCmpDat(cmpDat);
				sampleDocVO.setUtdDocEtc(utdDocEtc);
				sampleDocVO.setUtdSdmYn(sdmYn);

				sampleDocVO.setUtdRgtId((String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
				sampleDocVO.setUtdUptId((String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));

				//1. insert/update doc
				int result = fm_comps004_sampleDoc_save(sampleDocVO, null);

				String trcKey = String.valueOf(result);

				objMap = new HashMap<String, Object>();
				objMap.put("uptId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
				objMap.put("rgtId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
				objMap.put("bcyCod", req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY).toString());
				objMap.put("trcKey", trcKey);

				if (sampleDocVO.getUtdDelYn().equals("N")) {

					if (ctrCodeData.length > 0 && !ctrCodeData[0].equals("")) {

						// 통제항목 번호로 ctrKey 를 가져옴
						HashSet<String> standardSet = new HashSet<String>();
						String standardData[] = null;

						map = new HashMap<String, String>();
						map.put("bcyCod", req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY).toString());
						for (int i = 0; i < ctrCodeData.length; i++) {
							ctrCodeData[i] = ctrCodeData[i].trim();
							map.put("preFix", ctrCodeData[i].substring(0, 1));
							map.put("ctrCode", ctrCodeData[i].substring(1, ctrCodeData[i].length()));
							String ctrKey = fmCompsDAO.QR_COMPS004_getCtrKeyByCode(map);
							if (ctrKey != null && ctrKey != "") {
								standardSet.add(ctrKey);
							}
							standardData = standardSet.toArray(new String[standardSet.size()]);
						}

						// 2. insert/update standard
						objMap.put("ids", standardData);
						fm_comps004_standard_change(objMap);
					}

					if (workerNoData.length > 0 && !workerNoData[0].equals("")) {

						// 사번으로 usrKey 를 가져옴
						HashSet<String> workerSet = new HashSet<String>();
						String workerData[] = null;

						map = new HashMap<String, String>();
						for (int i = 0; i < workerNoData.length; i++) {
							map.put("usrId", workerNoData[i].trim());
							String usrKey = fmCompsDAO.QR_COMPS004_getUsrKeyById(map);
							if (usrKey != null && usrKey != "") {
								workerSet.add(usrKey);
							}
							workerData = workerSet.toArray(new String[workerSet.size()]);
						}

						// 3. insert/update worker
						objMap.put("ids", workerData);
						fm_comps004_worker_change(objMap);

						// 2017-02-16, 업무 일괄할당 버튼으로 대체
						// 5. insert work
						//objMap.put("ids", workerData);
						//fm_comps004_work_insert(objMap);
					}
				}
			}

		} catch (Exception e){
			//return Constants.RETURN_FAIL;
  			throw new RuntimeException(e);
		}
		return Constants.RETURN_SUCCESS;
	}

	/*
	 * 2017-02-16, 업무 일괄할당
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String fm_comps004_insert_work(HttpServletRequest req) throws Exception {

		String[] trcKeys = req.getParameter("trcKeys").split(",");
		Map<String, Object> emailList = new HashMap<String, Object>();

		try {
			for (int i = 0; i < trcKeys.length; i++) {

				String trcKey = trcKeys[i];

				// 1. 담당자 정보를 가져옴
				ArrayList<String> workerData = new ArrayList<String>();
				List<EgovMap> workerList = (List<EgovMap>) this.fm_comps004_workerList(trcKey);
		        for (EgovMap m : workerList) {
		        	workerData.add(m.get("uumUsrKey").toString());
		        }

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("rgtId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
				map.put("uptId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
				map.put("trcKey", trcKey);
				map.put("ids", workerData.toArray(new String[0]));
				map.put("doNotSendMail", "Y");

				// 2. 업무 할당
				SampleDocVO sampleDocVO = fm_comps004_work_insert(map);

				// 3. 이메일 발송 목록
				if (sampleDocVO.getUtdSdmYn().equals("Y")) {
					/*String contents = new StringBuffer("<br />")
								.append("■ 업무명 : ")
								.append(sampleDocVO.getUtdDocNam())
								.append("&nbsp;&nbsp;■ 업무주기 : ")
								.append(sampleDocVO.getUtdTrmGbnTxt())
								.append("&nbsp;&nbsp;■ 업무기한 : ")
								.append(sampleDocVO.getUtdStrDat())
								.append(" ~ ")
								.append(sampleDocVO.getUtdCmpDat())
								.toString(); */
					// 2017-09-11, text to html
					String contents = new StringBuffer()
							.append("<tr>")
							.append("<td width=\"436\" style=\"border-width:1; border-color:gray; border-style:solid;\">")
							.append("<p align=\"center\" style=\"line-height:120%; margin-top:0; margin-bottom:0;\">")
							.append(sampleDocVO.getUtdDocNam())
							.append("</p>")
							.append("</td>")
							.append("<td width=\"77\" style=\"border-width:1; border-color:gray; border-style:solid;\">")
							.append("<p align=\"center\" style=\"line-height:120%; margin-top:0; margin-bottom:0;\">")
							.append(sampleDocVO.getUtdTrmGbnTxt())
							.append("</p>")
							.append("</td>")
							.append("<td width=\"111\" style=\"border-width:1; border-color:gray; border-style:solid;\">")
							.append("<p align=\"center\" style=\"line-height:120%; margin-top:0; margin-bottom:0;\">")
							.append(sampleDocVO.getUtdCmpDat())
							.append("</p>")
							.append("</td>")
							.append("</tr>").toString();

					for (String item : sampleDocVO.getUtwWrkIds()) {
						ArrayList<String> al = new ArrayList<String>();
						if (emailList.get(item) != null) {
							al = (ArrayList<String>) emailList.get(item);
						}
						al.add(contents);
						emailList.put(item, al);
					}
				}

				// 2017-05-19, 증적주기, 담당자 변경시 기존 미완료 데이터 삭제처리
				this.fm_comps004_change_work_delete(map);
				// 2017-07-18, 마지막배치일시 저장
				this.fm_comps004_updateWorkLastInsertDate(map);
			}

			// 2017-09-11, email template
			Map<String, String> map2 = new HashMap<String, String>();
			map2.put("uccFirCod", "evtArm");
			map2.put("uccSndCod", EventTitleMessage.E01_CODE);
			EgovMap emap = commonUtilService.getUccComCodeInfo(map2);
			String mailTemplate = (String) emap.get("uccEtc");


			// 4. 이메일 발송
			for (String userKey : emailList.keySet()) {
				// UWO_EVT_ARM INSERT
				// 업무추가(E01)
				String sender = (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY);
				String eventCode = EventTitleMessage.E01_CODE;
				String title = EventTitleMessage.E01;
				//StringBuffer mailBody = new StringBuffer(title).append("<p>해당내용을 확인해주세요.</p>");
				// 2017-09-11, text to html
				//StringBuffer mailBody = new StringBuffer();
				StringBuffer contents = new StringBuffer();

				for (String item : (ArrayList<String>) emailList.get(userKey)) {
					contents.append(item);
				}

				String mailBody = mailTemplate.replace("{__BODY__}", contents.toString());

				//log.info("userKey: {}, content: {}", userKey, mailBody);
	       		sendMailService.sendMail(sender, eventCode, title, mailBody, userKey);
	        }

		} catch (Exception e) {
  			throw new RuntimeException(e);
		}

		return Constants.RETURN_SUCCESS;
	}

	@Override
	public void fm_comps004_updateWorkLastInsertDate(Map<String, Object> map) {
		fmCompsDAO.QR_COMPS004_updateWorkLastInsertDate(map);
	}


	@Override
	public void fm_comps004_change_work_delete(Map<String, Object> map) {
		fmCompsDAO.QR_COMPS004_change_work_delete(map);
	}


	/*
	 * 2018-02-20, 업무 삭제
	 */
	@Override
	public String fm_comps004_del_doc(HttpServletRequest req) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		String[] trcKeys = req.getParameter("trcKeys").split("\\,");
		map.put("trcKeys", trcKeys);

		try {
			fmCompsDAO.QR_COMPS004_del_doc(map);
		} catch (Exception e) {
  			throw new RuntimeException(e);
		}

		return Constants.RETURN_SUCCESS;
	}

	/*
	 * 2018-03-21 s, 컴플라이언스 항목 삭제
	 */
	@Override
	public String fm_comps003_del_comps(Map<String, String> map) throws Exception {
		try {
			fmCompsDAO.QR_COMPS003_del_comps(map);
		} catch (Exception e) {
  			throw new RuntimeException(e);
		}

		return Constants.RETURN_SUCCESS;
	}


	/*
	 * 2018-07-17 s, 업무삭제
	 */
	@Override
	public String fm_comps_del_work(HttpServletRequest req) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		String[] wrkKeys = req.getParameter("wrkKeys").split("\\,");
		map.put("wrkKeys", wrkKeys);

		try {
			fmCompsDAO.QR_COMPS_del_work(map);
		} catch (Exception e) {
  			throw new RuntimeException(e);
		}

		return Constants.RETURN_SUCCESS;
	}

	/*
	 * 2020-04-02 서비스 동기화
	 */
	@Override
	public void fm_comps004_updateSameService(Map<String, Object> map) {
		fmCompsDAO.QR_COMPS004_updateSameService(map);
	}

	@Override
	public List<?> getComCodPrefix() {
		List<?> list = fmCompsDAO.getComCodPrefix();
		return list;
	}

	public CntrVO getControlItem(int controlKey) {
		return fmCompsDAO.getControlItemByKey(controlKey);
	}

	@Override
	public String getKindOfCompliance(String complianceCode) {
		return fmCompsDAO.getKindOfCompliance(complianceCode);
	}

	public List<EgovMap> getControlItemList(Map map) {
		return fmCompsDAO.getControlItemList(map);
	}

	@Override
	public List<EgovMap> getComplianceListPerServiceByCycleCode(String cycleCode) {
		return fmCompsDAO.getComplianceListPerServiceByCycleCode(cycleCode);
	}


	/**
     * Self-Audit 서비스 코드를 가지고 온다.
     * 두개 이상일 경우 첫번째 값을 가지고 온다.
     */
    @Override
    public String getSaServiceCode() {
        List<EgovMap> saServiceList = (List<EgovMap>) commonCodeDAO.getCommonCodeList("SVC_SA");
        return saServiceList == null ? null : (String) saServiceList.get(0).get("code");
    }

    /**
     * Self-Audit 서비스에서 사용하는 컴플라이언스 코드를 가지고 온다.
     * 두개 이상일 경우 첫번째 값을 가지고 온다.
     */
    @Override
    public String getSaComplianceCode() {
        List<EgovMap> saComplianceList = (List<EgovMap>) commonCodeDAO.getCommonCodeList("COMP_SA");
        return saComplianceList == null ? null : (String) saComplianceList.get(0).get("code");
    }


}
