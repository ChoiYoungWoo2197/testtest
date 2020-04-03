/**
 ***********************************
 * @source FMMworkController.java
 * @date 2014. 10. 9.
 * @project isms3
 * @description 정보보호관리업무 controller
 ***********************************
 */
package com.uwo.isms.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.uwo.isms.common.CommonConfig;
import com.uwo.isms.common.Constants;
import com.uwo.isms.common.EventTitleMessage;
import com.uwo.isms.domain.BoardVO;
import com.uwo.isms.domain.FileVO;
import com.uwo.isms.domain.IFP_DTL_VO;
import com.uwo.isms.domain.IFP_MTR_VO;
import com.uwo.isms.domain.SearchVO;
import com.uwo.isms.domain.WorkVO;
import com.uwo.isms.service.CommonCodeService;
import com.uwo.isms.service.CommonUtilService;
import com.uwo.isms.service.FMCompsService;
import com.uwo.isms.service.FMMworkService;
import com.uwo.isms.service.FileUploadService;
import com.uwo.isms.service.SendMailService;
import com.uwo.isms.util.EgovUserDetailsHelper;
import com.uwo.isms.util.FileUpload;
import com.uwo.isms.util.PaginationUtil;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
@RequestMapping("/mwork")
public class FMMworkController {

	Logger log = LogManager.getLogger(FMMworkController.class);

	@Resource(name = "fmMworkService")
	private FMMworkService fmMworkService;

	@Resource(name = "fileUploadService")
	FileUploadService fileUploadService;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;

	@Resource(name = "sendMailService")
	SendMailService sendMailService;

	@Resource(name = "commonUtilService")
	private CommonUtilService commonUtilService;

	@Resource(name = "commonCodeService")
	private CommonCodeService commonCodeService;

	@Autowired
	private FMCompsService fmCompsService;

	@RequestMapping(value = "/FM-MWORK001.do")
	public String fmMwork001(ModelMap model, HttpServletRequest req) throws Exception {

		java.util.Calendar cal = java.util.Calendar.getInstance();

		String sYear = req.getParameter("sYear");
		String sMonth = req.getParameter("sMonth");
		String sessionStd = req.getSession().getAttribute(CommonConfig.SES_MAN_STD_KEY).toString();

		int iYear = cal.get(java.util.Calendar.YEAR);
		int iMonth = cal.get(java.util.Calendar.MONTH) + 1;
		int iDate = cal.get(java.util.Calendar.DATE);

		if (sYear == null || sMonth == null || sYear == "" || sMonth == "") {
			sYear = Integer.toString(iYear);
			if (sessionStd != "") {
				if (!sessionStd.equals(req.getParameter("sessionStd"))
						&& iYear != Integer.parseInt(sessionStd.substring(0, 4))) {
					sYear = sessionStd.substring(0, 4);
					iYear = Integer.parseInt(sYear);
					iMonth = Integer.parseInt(sessionStd.substring(4).substring(0, 2));
				}
			}
		} else {
			iYear = Integer.parseInt(sYear);
			iMonth = Integer.parseInt(sMonth);
		}

		sMonth = Integer.toString(iMonth).length() == 1 ? "0" + Integer.toString(iMonth) : Integer.toString(iMonth);

		String dMonth = new DateFormatSymbols(Locale.ENGLISH).getMonths()[iMonth - 1];

		Map map = new HashMap();

		map.put("manCyl", (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		map.put("division", (String) req.getSession().getAttribute(CommonConfig.SES_DIVISION_KEY));
		map.put("worker", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		map.put("iMonth", iMonth);
		map.put("iYear", iYear);

		List<?> calendarYear = fmMworkService.getCalendarYear();
		List<?> calendarList = fmMworkService.getCalendarList(map);
		List<?> resultList = fmMworkService.getWorkList(map);

		model.addAttribute("sessionStd", sessionStd);
		model.addAttribute("dMonth", dMonth.toUpperCase());
		model.addAttribute("sMonth", sMonth);
		model.addAttribute("sYear", sYear);
		model.addAttribute("calendarYear", calendarYear);
		model.addAttribute("calendarList", calendarList);
		model.addAttribute("resultList", resultList);

		return "FM-MWORK001";
	}

	@RequestMapping(value = "/FM-MWORK002.do")
	public String fmMwork002(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model, HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		String mode = "P";
		String division = req.getParameter("division");

		searchVO.setManCyl((String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		if (mode.equals("P")) {
			searchVO.setWorker((String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		} else if (mode.equals("D")) {
			searchVO.setDivision((String) req.getSession().getAttribute(CommonConfig.SES_DIVISION_KEY));
			searchVO.setWorker((String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		}

		// 업무현황 카운트 내거(미완료)
		List<?> workCount = fmMworkService.getWorkCount(searchVO);

		// 업무현황 카운트 내거(미완료_지연)
		List<?> workDCount = fmMworkService.getWorkDCount(searchVO);

		// 업무현황 카운트 남의거
		List<?> namWorkCount = fmMworkService.getNamWorkCount(searchVO);

		// 지연업무 리스트
		searchVO.setIsDelay("Y");
		List<?> delayWorkList = fmMworkService.getNoWorkList(searchVO);

		// 미완료업무 리스트
		searchVO.setIsDelay("N");
		List<?> noWorkList = fmMworkService.getNoWorkList(searchVO);

		// 완료업무 리스트
		List<?> comWorkList = fmMworkService.getComWorkList(searchVO);

		if (workCount.size() > 0) {
			Map<String, String> map = (Map<String, String>) workCount.get(0);
			model.addAttribute("miCnt", map.get("miCnt"));
			model.addAttribute("wanCnt", map.get("wanCnt"));
		}

		if (workDCount.size() > 0) {
			Map<String, String> map = (Map<String, String>) workDCount.get(0);
			model.addAttribute("miDCnt", map.get("miDCnt"));
			model.addAttribute("wanDCnt", map.get("wanDCnt"));
		}

		if (namWorkCount.size() > 0) {
			Map<String, String> map = (Map<String, String>) namWorkCount.get(0);
			model.addAttribute("namCnt1", map.get("namCnt1"));
			model.addAttribute("namCnt2", map.get("namCnt2"));
		}
		if (delayWorkList.size() > 0) {
			model.addAttribute("delayWorkList", delayWorkList);
		}
		if (noWorkList.size() > 0) {
			model.addAttribute("noWorkList", noWorkList);
		}
		if (comWorkList.size() > 0) {
			model.addAttribute("comWorkList", comWorkList);
		}
		return "FM-MWORK002";
	}

	@RequestMapping(value = "/FM-MWORK002_P.do")
	public ModelAndView fmMwork002_P(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		int page = Integer.parseInt(req.getParameter("page"));
		double rows = Integer.parseInt(req.getParameter("rows"));
		int endPage = page * (int) rows;
		int startPage = endPage - (int) rows + 1;
		searchVO.setFirstIndex(startPage);
		searchVO.setLastIndex(endPage);

		searchVO.setManCyl((String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

		searchVO.setAuth((String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));
		String findusr = (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY);

		if (searchVO.getAuth().equals("A")) {
			searchVO.setFindusr("");
		} else {
			searchVO.setFindusr(findusr);
		}

		System.out.println(searchVO.getAuth());
		System.out.println(searchVO.getFindusr());

		// 업무현황 카운트 내거(미완료)
		List<?> workCount = fmMworkService.getWorkCount(searchVO);

		// 업무현황 카운트 내거(미완료_지연)
		List<?> workDCount = fmMworkService.getWorkDCount(searchVO);

		// 업무현황 카운트 남의거
		List<?> namWorkCount = fmMworkService.getNamWorkCount(searchVO);

		// 전체업무 리스트
		List<?> allWorkList = fmMworkService.getAllWorkList(searchVO);

		Map resultMap = new HashMap();

		if (workCount.size() > 0) {
			resultMap.put("workCount", workCount);
		}

		if (workDCount.size() > 0) {
			resultMap.put("workDCount", workDCount);
		}

		if (namWorkCount.size() > 0) {
			resultMap.put("namWorkCount", namWorkCount);
		}

		Map<String, String> noneDisp = new HashMap<String, String>();
		noneDisp.put("display", "none");

		Map<String, Object> mergedRow = new HashMap<String, Object>();
		mergedRow.put("checkedAuth", noneDisp);
		mergedRow.put("utwWrkKey", noneDisp);
		mergedRow.put("utwTrcKey", noneDisp);
		mergedRow.put("displayWrkSta", noneDisp);
		mergedRow.put("period", noneDisp);
		mergedRow.put("utdDocNam", noneDisp);
		mergedRow.put("utwTrmCod", noneDisp);
		mergedRow.put("utwWrkId", noneDisp);
		mergedRow.put("wrkStaDtl", noneDisp);
		mergedRow.put("utwAgnId", noneDisp);
		mergedRow.put("agnStaDtl", noneDisp);
		mergedRow.put("utdAppNam", noneDisp);

		// jqGrid 총 records(총 목록 수)
		int count = 0;
		count = fmMworkService.getAllWorkListCnt(searchVO);

		String wrkKey = "";
		String wrkKey_next = "";
		int cellMerge = 1;

		ArrayList<Object> allWorkList_copy = new ArrayList<Object>(count);

		if (count > 0) {

			for (int i = 0; i < allWorkList.size(); i++) {
				allWorkList_copy.add(i, "");

				Map<String, Object> map = (Map<String, Object>) allWorkList.get(i);
				wrkKey = String.valueOf(map.get("utwWrkKey"));

				for (int j = i + 1; j < allWorkList.size() + 1; j++) {

					if (j == allWorkList.size()) {
						Map<String, String> disp = new HashMap<String, String>();
						disp.put("rowspan", cellMerge + "");

						Map<String, Object> mergeRow = new HashMap<String, Object>();
						mergeRow.put("checkedAuth", disp);
						mergeRow.put("utwWrkKey", disp);
						mergeRow.put("utwTrcKey", disp);
						mergeRow.put("displayWrkSta", disp);
						mergeRow.put("period", disp);
						mergeRow.put("utdDocNam", disp);
						mergeRow.put("utwTrmCod", disp);
						mergeRow.put("utwWrkId", disp);
						mergeRow.put("wrkStaDtl", disp);
						mergeRow.put("utwAgnId", disp);
						mergeRow.put("agnStaDtl", disp);
						mergeRow.put("utdAppNam", disp);

						map.put("attr", mergeRow);
						map.put("checkedAuth", wrkKey);
						allWorkList_copy.set(i, map);

						if (cellMerge != 1) {
							i = i + cellMerge - 1;
						}
						cellMerge = 1;
						break;
					}

					Map<String, Object> map_next = (Map<String, Object>) allWorkList.get(j);
					wrkKey_next = String.valueOf(map_next.get("utwWrkKey"));

					if (wrkKey.equals(wrkKey_next)) {
						allWorkList_copy.add(j, "");
						cellMerge++;
						map_next.put("attr", mergedRow);
						map_next.put("checkedAuth", false);
						allWorkList_copy.set(j, map_next);
					} else {
						Map<String, String> disp = new HashMap<String, String>();
						disp.put("rowspan", cellMerge + "");

						Map<String, Object> mergeRow = new HashMap<String, Object>();
						mergeRow.put("checkedAuth", disp);
						mergeRow.put("utwWrkKey", disp);
						mergeRow.put("utwTrcKey", disp);
						mergeRow.put("displayWrkSta", disp);
						mergeRow.put("period", disp);
						mergeRow.put("utdDocNam", disp);
						mergeRow.put("utwTrmCod", disp);
						mergeRow.put("utwWrkId", disp);
						mergeRow.put("wrkStaDtl", disp);
						mergeRow.put("utwAgnId", disp);
						mergeRow.put("agnStaDtl", disp);
						mergeRow.put("utdAppNam", disp);

						map.put("attr", mergeRow);
						map.put("checkedAuth", wrkKey);

						allWorkList_copy.set(i, map);

						if (cellMerge != 1) {
							i = i + cellMerge - 1;
						}
						cellMerge = 1;
						break;
					}

					if (i >= allWorkList.size()) {
						break;
					}
				}
			}

			resultMap.put("allWorkList", allWorkList_copy);
		}

		// jqGrid 총 페이지
		int total = (int) Math.ceil(count / rows);

		resultMap.put("page", page);
		resultMap.put("total", total);
		resultMap.put("records", count);

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping(value = "/FM-MWORK005_AGN_popup.do")
	public String fmMwork002_agnIdList(ModelMap model, @RequestParam Map<String, String> paramMap,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		SearchVO searchVO = new SearchVO();
		searchVO.setService(req.getSession().getAttribute(CommonConfig.SES_SERVICE_KEY).toString());
		searchVO.setDept(req.getSession().getAttribute(CommonConfig.SES_DEPT_KEY).toString());
		searchVO.setWorker(req.getSession().getAttribute(CommonConfig.SES_USER_KEY).toString());
		model.addAttribute("paramMap", paramMap);
		model.addAttribute("resultList", fmMworkService.fm_mwork002_agnIdList(searchVO));
		return "FM-MWORK005_AGN_popup";
	}

	@RequestMapping(value = "/FM-MWORK002_setAgnId")
	public ModelAndView fmMwork002_setAgnId(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		searchVO.setSearchKeyword(req.getSession().getAttribute(CommonConfig.SES_USER_KEY).toString());
		searchVO.setCode(req.getParameter("gubun"));
		if (searchVO.getWorker() == null || searchVO.getWorker().equals("")) {
			searchVO.setWorker(req.getParameter("change"));
		}

		fmMworkService.fm_mwork002_setAgnId(searchVO);

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		// Map resultMap = new HashMap();
		// resultMap.put("result", list);
		// mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping(value = "/FM-MWORK_popup.do")
	public String fmMwork002_popup(@RequestParam Map<String, String> paramMap, ModelMap model, HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		String utwWrkKey = paramMap.get("utwWrkKey");
		String utwTrcKey = paramMap.get("utwTrcKey");
		String mngKey = paramMap.get("mngKey");
		String usrKey = paramMap.get("usrKey");

		if (mngKey == null || mngKey.length() == 0) {
			mngKey = fmMworkService.getMngKey(paramMap);
		}

		if (usrKey == null || usrKey.length() == 0) {
			usrKey = fmMworkService.getWorkId(paramMap);
		}

		String company = propertyService.getString("company");

		List<?> bcyList = fmMworkService.getBcyList(mngKey, usrKey);

		if (utwWrkKey.isEmpty()) {
			utwWrkKey = fmMworkService.getWorkKey(utwTrcKey, usrKey);
		}

		// 업무상태 및 담당자 and 업무수행내용
		WorkVO workVO = fmMworkService.getWorkState(utwWrkKey);

		// 표준항목 상세내용
		List<?> cntrList = fmMworkService.getCntrList(utwTrcKey);
		String ctrKeyList = "";

		for (int i = 0; i < cntrList.size(); i++) {
			if (((Map) (cntrList.get(i))).containsKey("ucmCtrKey")) {
				ctrKeyList += ((Map) (cntrList.get(i))).get("ucmCtrKey").toString() + ",";
			}
		}
		if (!ctrKeyList.equals("")) {
			ctrKeyList = ctrKeyList.substring(0, ctrKeyList.length() - 1);
		}

		String bcyCod = (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY);
		// 증적양식파일 리스트
		List<?> sampleDocFileList = fileUploadService.selectFileList(utwTrcKey, Constants.FILE_GBN_DOC,
				Constants.FILE_CON_DOC, bcyCod);

		// 증적파일 리스트
		// List<?> fileList = fileUploadService.selectFileList(utwWrkKey,
		// Constants.FILE_GBN_WRK, Constants.FILE_CON_WRK);

		// 증적완료 파일 리스트
		List<?> proofFileList = fileUploadService.selectProofFileList(utwWrkKey, Constants.FILE_GBN_WRK,
				Constants.FILE_CON_WRK, company);

		// Map sMap = new HashMap();
		// sMap.put("manCyl", bcyCod);
		// sMap.put("trcKey", utwTrcKey);
		// List<?> similarWorkList = fmMworkService.getSimilarWorkList(sMap);

		// model.addAttribute("similarWorkList", similarWorkList);
		model.addAttribute("workVO", workVO);
		model.addAttribute("cntrList", cntrList);
		model.addAttribute("bcyList", bcyList);
		model.addAttribute("sampleDocFileList", sampleDocFileList);
		// model.addAttribute("fileList", fileList);
		model.addAttribute("proofFileList", proofFileList);
		model.addAttribute("ctrKey", ctrKeyList);
		model.addAttribute("loginAuth", (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));
		model.addAttribute("userKey", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		model.addAttribute("mngKey", mngKey);
		model.addAttribute("usrKey", usrKey);

		return "FM-MWORK_popup";
	}

	@RequestMapping(value = "/FM-MWORK_transPopup.do")
	public String fmMwork_transPopup(@RequestParam Map<String, String> paramMap, ModelMap model, HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		String wrkKey = paramMap.get("utwWrkKey");
		String appKey = paramMap.get("appKey");

		// 업무상태 및 담당자 and 업무수행내용
		WorkVO workVO = fmMworkService.getWorkState(wrkKey);
		List<?> wrkList = fmMworkService.selectTransferListByAppKey(appKey);

		// 2018-05-11 s, 결재정보
		WorkVO wv = fmMworkService.selectAppByAppKey(appKey);
		workVO.setUam_sta_cod(wv.getUam_sta_cod());
		workVO.setUam_cf1_id(wv.getUam_cf1_id());
		workVO.setUam_cf1_nam(wv.getUam_cf1_nam());
		workVO.setUam_cf1_mdh(wv.getUam_cf1_mdh());
		workVO.setUam_cf1_etc(wv.getUam_cf1_etc());
		workVO.setUam_cf2_id(wv.getUam_cf2_id());
		workVO.setUam_cf2_nam(wv.getUam_cf2_nam());
		workVO.setUam_cf2_mdh(wv.getUam_cf2_mdh());
		workVO.setUam_cf2_etc(wv.getUam_cf2_etc());
		workVO.setAgn_cf1_id(wv.getUam_cf1_id());
		workVO.setAgn_cf2_id(wv.getUam_cf2_id());
		workVO.setUam_req_nam(wv.getUam_req_nam());


		List<String> wrkKeys = new ArrayList<String>();
		String wrkId = null;
		for (int i = 0; i < wrkList.size(); i++) {
			EgovMap map2 = (EgovMap) wrkList.get(i);
			wrkKeys.add(map2.get("utwWrkKey").toString());
			wrkId = map2.get("uumUsrKey").toString();
		}
		String addList = StringUtils.join(wrkKeys, ",");

		model.addAttribute("usrKey", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		model.addAttribute("authKey", (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));
		model.addAttribute("workVO", workVO);
		model.addAttribute("wrkList", wrkList);
		model.addAttribute("addList", addList);
		model.addAttribute("wrkId", wrkId);

		return "FM-MWORK_transPopup";
	}

	@RequestMapping(value = "/FM-MWORK_copyWorkpopup.do")
	public String fmMwork002_copyWorkpopup(@RequestParam Map<String, String> paramMap, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		System.out.println("**********");
		System.out.println("utwTrcKeyutwTrcKey ======" + req.getParameter("utwTrcKey"));
		System.out.println("**********");

		Map searchMap = new HashMap();
		searchMap.put("utwWrkKey", req.getParameter("utwWrkKey"));
		searchMap.put("utwTrcKey", req.getParameter("utwTrcKey"));

		String utwWrkKey = req.getParameter("utwWrkKey");
		String utwTrcKey = req.getParameter("utwTrcKey");
		String mngKey = "";// paramMap.get("mngKey");
		String usrKey = "";// paramMap.get("usrKey");

		if (mngKey == null || mngKey.length() == 0) {
			System.out.println("**********");
			mngKey = fmMworkService.getMngKey(searchMap);
			System.out.println("**********");
		}

		if (usrKey == null || usrKey.length() == 0) {
			System.out.println("**********");
			usrKey = fmMworkService.getWorkId(searchMap);
			System.out.println("**********");
		}

		String company = propertyService.getString("company");

		List<?> bcyList = fmMworkService.getBcyList(mngKey, usrKey);

		if (utwWrkKey.isEmpty()) {
			System.out.println("**********");
			utwWrkKey = fmMworkService.getWorkKey(utwTrcKey, usrKey);
			System.out.println("**********");
		}

		// 업무상태 및 담당자 and 업무수행내용
		WorkVO workVO = fmMworkService.getWorkState(utwWrkKey);

		// 표준항목 상세내용
		List<?> cntrList = fmMworkService.getCntrList(utwTrcKey);
		String ctrKeyList = "";

		for (int i = 0; i < cntrList.size(); i++) {
			if (((Map) (cntrList.get(i))).containsKey("ucmCtrKey")) {
				ctrKeyList += ((Map) (cntrList.get(i))).get("ucmCtrKey").toString() + ",";
			}
		}
		if (!ctrKeyList.equals("")) {
			ctrKeyList = ctrKeyList.substring(0, ctrKeyList.length() - 1);
		}

		String bcyCod = (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY);
		// 증적양식파일 리스트
		List<?> smapleDocFileList = fileUploadService.selectFileList(utwTrcKey, Constants.FILE_GBN_DOC,
				Constants.FILE_CON_DOC, bcyCod);

		// 증적파일 리스트
		// List<?> fileList = fileUploadService.selectFileList(utwWrkKey,
		// Constants.FILE_GBN_WRK, Constants.FILE_CON_WRK);

		// 증적완료 파일 리스트
		List<?> proofFileList = fileUploadService.selectProofFileList(utwWrkKey, Constants.FILE_GBN_WRK,
				Constants.FILE_CON_WRK, company);

		model.addAttribute("workVO", workVO);
		model.addAttribute("cntrList", cntrList);
		model.addAttribute("bcyList", bcyList);
		model.addAttribute("smapleDocFileList", smapleDocFileList);
		// model.addAttribute("fileList", fileList);
		model.addAttribute("proofFileList", proofFileList);
		model.addAttribute("ctrKey", ctrKeyList);
		model.addAttribute("loginAuth", (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));
		model.addAttribute("mngKey", mngKey);
		model.addAttribute("usrKey", usrKey);

		return "FM-MWORK_copyWorkpopup";
	}

	@RequestMapping("/FM-MWORK_work_save.do")
	public ModelAndView fmMwork_work_save(@ModelAttribute("workVO") WorkVO workVO, HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		// get from properties, file uploadpath
		String uploadPath = propertyService.getString("workUploadPath");
		req.setAttribute("uploadPath", uploadPath);

		FileUpload fu = new FileUpload();
		// parammeter : req, umf_tbl_gbn, umf_con_gbn
		List<FileVO> list = fu.fileuplaod_J(req, Constants.FILE_GBN_WRK, Constants.FILE_CON_WRK);

		fmMworkService.fm_mwork_workSave(workVO, list);

		String workKey = workVO.getUtw_wrk_key();
		String trcKey = workVO.getUtw_trc_key();

		mav.addObject("utwWrkKey", workKey);
		mav.addObject("utwTrcKey", trcKey);
		mav.setViewName("redirect:../mwork/FM-MWORK_popup.do");

		return mav;
	}

	@RequestMapping("/FM-MWORK_work_stateUpdate.do")
	public ModelAndView fmMwork_stateUpdate(@ModelAttribute("workVO") WorkVO workVO, HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		String addList = req.getParameter("addList");
		String wrkId = req.getParameter("wrkId");
		workVO.setUtw_upt_id((String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		fmMworkService.fm_mwork_stateUpdate(workVO);

		// 결재승인처리
		// 2018-05-11 s, 19 -> 29
		if (addList != null && !addList.equals("") && workVO.getUam_sta_cod().equals("29")) {
			SearchVO searchVO = new SearchVO();
			searchVO.setWorker(wrkId);
			searchVO.setAddList(addList);
			searchVO.setRgtId(workVO.getUtw_upt_id());
			fmMworkService.fm_mwork006_setWrkId(searchVO);
		}

		String workKey = workVO.getUtw_wrk_key();
		String trcKey = workVO.getUtw_trc_key();

		mav.addObject("utwWrkKey", workKey);
		mav.addObject("utwTrcKey", trcKey);

		if (workVO.getAgn_app_key() != null && !workVO.getAgn_app_key().equals("")) {
			mav.addObject("appKey", workVO.getAgn_app_key());
			mav.setViewName("redirect:../mwork/FM-MWORK_transPopup.do");
		} else {
			mav.setViewName("redirect:../mwork/FM-MWORK_popup.do");
		}

		return mav;
	}

	@RequestMapping("/FM-MWORK_work_del.do")
	public void fmMwork_work_del(@ModelAttribute("workVO") WorkVO workVO, HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();
		int result = 1;

		String workKey = workVO.getUtw_wrk_key();

		System.out.println(workKey);
		result = fmMworkService.fm_mwork_del(workKey);

	}

	/*
	 * FM-COMPS005_getGuideList.do 와 동일함
	 */
	@RequestMapping("/FM-MWORK_getGuideList.do")
	public ModelAndView fmComps005_getGuideList(ModelMap model, HttpServletRequest req, HttpServletResponse res)
			throws Exception {

		ModelAndView mav = new ModelAndView();

		String ctrKey = req.getParameter("ctrKey");

		List<?> list = fmCompsService.fm_comps005_getGuideList(ctrKey);

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", list);
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping(value = "/FM-MWORK003.do")
	public String fmMwork003(@ModelAttribute("searchVO") SearchVO searchVO, HttpServletRequest req, ModelMap model)
			throws Exception {

		return "FM-MWORK003";
	}

	@RequestMapping(value = "/FM-MWORK003_getWorkList.do")
	public ModelAndView fmMwork003_getWorkList(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		String searchCondition = searchVO.getSearchCondition();
		if (searchCondition.equals("workName")) {
			searchVO.setWorkName(searchVO.getSearchKeyword());
		} else if (searchCondition.equals("goalNum")) {
			searchVO.setGoalNum(searchVO.getSearchKeyword());
		}

		searchVO.setManCyl((String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		searchVO.setDivision((String) req.getSession().getAttribute(CommonConfig.SES_DIVISION_KEY));
		searchVO.setWorker((String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));

		// 업무리스트
		List<?> workList = fmMworkService.getWorkList(searchVO);

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
		resultMap.put("workList", workList);
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping(value = "/FM-MWORK_performWrokPopup.do")
	public String fmMwork_performWorkPopup(@RequestParam("utdTrcKey") String utdTrcKey,
			@RequestParam("utdDocNam") String utdDocNam, @RequestParam("pageNo") String pageNo, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		String company = propertyService.getString("company");

		// 표준항목 상세내용
		List<?> cntrList = fmMworkService.getCntrList(utdTrcKey);
		String ctrKeyList = "";

		for (int i = 0; i < cntrList.size(); i++) {
			if (((Map) (cntrList.get(i))).containsKey("ucmCtrKey")) {
				ctrKeyList += ((Map) (cntrList.get(i))).get("ucmCtrKey").toString() + ",";
			}
		}
		if (!ctrKeyList.equals("")) {
			ctrKeyList = ctrKeyList.substring(0, ctrKeyList.length() - 1);
		}

		SearchVO searchVO = new SearchVO();
		searchVO.setDocCode(utdTrcKey);
		searchVO.setCompany(company);
		searchVO.setManCyl(req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY).toString());
		searchVO.setDivision(req.getSession().getAttribute(CommonConfig.SES_DIVISION_KEY).toString());

		if (pageNo.equals("003")) {
			searchVO.setWorker(req.getSession().getAttribute(CommonConfig.SES_USER_KEY).toString());
		}

		// 업무담당자 및 진척도
		List<?> workerList = fmMworkService.getWorkerist(searchVO);

		String bcyCod = (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY);
		// 증적양식파일 리스트
		List<?> smapleDocFileList = fileUploadService.selectFileList(utdTrcKey, Constants.FILE_GBN_DOC,
				Constants.FILE_CON_DOC, bcyCod);

		// 증적완료 파일 리스트
		List<?> proofFileList = fileUploadService.selectProofFileListByTrcKey(searchVO);

		model.addAttribute("cntrList", cntrList);
		model.addAttribute("workerList", workerList);
		model.addAttribute("smapleDocFileList", smapleDocFileList);
		model.addAttribute("proofFileList", proofFileList);
		model.addAttribute("ctrKey", ctrKeyList);
		model.addAttribute("utdDocNam", utdDocNam);

		return "FM-MWORK_performWorkPopup";
	}

	@RequestMapping(value = "/FM-MWORK004.do")
	public String fmMwork004(@ModelAttribute("searchVO") SearchVO searchVO, HttpServletRequest req, ModelMap model)
			throws Exception {

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("sesAuthKey", (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));
		paramMap.put("sesServiceKey", (String) req.getSession().getAttribute(CommonConfig.SES_SERVICE_KEY));
		paramMap.put("sesDeptKey", (String) req.getSession().getAttribute(CommonConfig.SES_DEPT_KEY));

		return "FM-MWORK004";
	}

	@RequestMapping("/FM-MWORK004List.do")
	public ModelAndView fmMwork004_List(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		searchVO.setManCyl((String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

		List<?> list = new ArrayList();
		list = fmMworkService.fmMwork004_List(searchVO);

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
		resultMap.put("result", list);
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping(value = "/FM-MWORK005.do")
	public String fmMwork005(@ModelAttribute("searchVO") SearchVO searchVO, HttpServletRequest req, ModelMap model)
			throws Exception {

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("sesAuthKey", (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));
		paramMap.put("sesServiceKey", (String) req.getSession().getAttribute(CommonConfig.SES_SERVICE_KEY));
		paramMap.put("sesDeptKey", (String) req.getSession().getAttribute(CommonConfig.SES_DEPT_KEY));

		return "FM-MWORK005";
	}

	@RequestMapping("/FM-MWORK005List.do")
	public ModelAndView fmMwork005_List(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		searchVO.setManCyl((String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		searchVO.setDivision((String) req.getSession().getAttribute(CommonConfig.SES_DIVISION_KEY));
		searchVO.setAuth((String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));
		String usrKey = (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY);

		if (searchVO.getAuth().equals("A")) {
			searchVO.setUsrkey("");
		} else {
			searchVO.setUsrkey(usrKey);
		}

		List<?> list = new ArrayList();
		list = fmMworkService.fmMwork005_List(searchVO);

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
		resultMap.put("result", list);
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping("/FM-MWORK005termList.do")
	public ModelAndView fmMwork005_termList(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		searchVO.setManCyl((String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		searchVO.setWorker(req.getParameter("id"));
		searchVO.setWorkCycleTerm(req.getParameter("term"));
		searchVO.setOrder(req.getParameter("order"));
		searchVO.setWorkState(req.getParameter("state"));

		List<?> list = new ArrayList();
		int cnt = 0;
		list = fmMworkService.fmMwork005_termList(searchVO);
		cnt = fmMworkService.fmMwork005_termCount(searchVO);
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
		resultMap.put("result", list);
		resultMap.put("cnt", cnt);
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping("/FM-MWORK005_USER_popup.do")
	public String fmMwork005_User_popup(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		String usrKey = req.getParameter("userKey");
		List<?> userList = new ArrayList();
		int workDelayCnt = 0;
		int workCompCnt = 0;
		int workNoworkCnt = 0;

		userList = fmMworkService.getDeptUserList(usrKey);
		workDelayCnt = fmMworkService.getDelayCnt(usrKey);
		workCompCnt = fmMworkService.getCompCnt(usrKey);
		workNoworkCnt = fmMworkService.getNoworkCnt(usrKey);
		String usrName = fmMworkService.getUserName(usrKey);

		model.addAttribute("usrKey", usrKey);
		model.addAttribute("usrKey", usrKey);
		model.addAttribute("usrName", usrName);
		model.addAttribute("workCompCnt", workCompCnt);
		model.addAttribute("workNoworkCnt", workNoworkCnt);
		model.addAttribute("workDelayCnt", workDelayCnt);

		model.addAttribute("userList", userList);

		return "FM-MWORK005_USER_popup";
	}

	@RequestMapping("/FM-MWORK006_TRS_POPUP.do")
	public String fmMwork006_trs_popup(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		String addList = req.getParameter("addList");
		String[] al = addList.split(",");

		List<EgovMap> list = new ArrayList<EgovMap>();

		//for (int i = 0; i < al.length; i++) {
		//	System.out.println(al[i]);
		//}

		// Map resultMap = new HashMap();

		for (int i = 0; i < al.length; i++) {
			list.add(fmMworkService.fmMwork006_trs_info(al[i]));
		}

		model.addAttribute("list", list);
		model.addAttribute("addList", addList);
		model.addAttribute("usrKey", req.getParameter("usrKey"));

		return "FM-MWORK006_TRS_POPUP";

	}

	@RequestMapping(value = "/FM-MWORK006_reqTransfer")
	public ModelAndView fmMwork006_reqTransfer(@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		map.put("uptId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY).toString());
		// usrKey, wrkKey, addList
		String result = fmMworkService.fmMwork006_reqTransfer(map);

		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("result", result);
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	/*
	 * @RequestMapping("/FM-MWORK_USRLIST_POPUP.do") public String
	 * fmMwork_usrListPopup (ModelMap model, HttpServletRequest req,
	 * HttpServletResponse res) throws Exception { Map<String, String> param =
	 * new HashMap<String,String>();
	 *
	 * param.put("uumUsrId", req.getParameter("ursMesMng"));
	 * param.put("uumUsrNam", req.getParameter("usrMngNam"));
	 * model.addAttribute("usrList", fmMworkService.getUsrList(param)); return
	 * "FM-MWORK_USRLIST_POPUP"; }
	 *
	 * @RequestMapping("/FM-MWORK006_USRLIST.do") public ModelAndView
	 * fmMwork006_usrlist (@RequestParam Map<String, String> map, ModelMap
	 * model, HttpServletRequest req, HttpServletResponse res) throws Exception
	 * {
	 *
	 * ModelAndView mav = new ModelAndView();
	 *
	 * res.setCharacterEncoding(""); res.setContentType(
	 * "text/xml; charset=UTF-8");
	 *
	 * Map<String, Object> resultMap = new HashMap<String, Object>();
	 * resultMap.put("result", fmMworkService.getUsrList(map));
	 * mav.addAllObjects(resultMap); mav.setViewName("jsonView");
	 *
	 * return mav; }
	 */

	@RequestMapping("/FM-MWORK005_Change_Work.do")
	public ModelAndView Change_Work(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		String gubun = req.getParameter("gubun");
		String beforeUser = req.getParameter("before");
		String changeUser = req.getParameter("change");
		Map userMap = new HashMap();
		userMap.put("before", beforeUser);
		userMap.put("change", changeUser);

		if (gubun.indexOf("D") > -1) {
			userMap.put("gubun", "D");
			fmMworkService.changeWork(userMap);
		}
		if (gubun.indexOf("N") > -1) {
			userMap.put("gubun", "N");
			fmMworkService.changeWork(userMap);
		}
		if (gubun.indexOf("C") > -1) {
			userMap.put("gubun", "C");
			fmMworkService.changeWork(userMap);
		}

		List<?> workMapKeylist = new ArrayList();
		workMapKeylist = fmMworkService.getWorkMapKey(userMap);
		String workMapKey = "";

		for (int i = 0; i < workMapKeylist.size(); i++) {
			workMapKey = (String) workMapKeylist.get(i);
			userMap.put("workMapKey", workMapKey);
			fmMworkService.changeWorkMap(userMap);
		}

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
		resultMap.put("result", "suc");
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping(value = "/FM-MWORK005_T.do")
	public String fmMwork005_t(@RequestParam Map<String, String> map, HttpServletRequest req, ModelMap model)
			throws Exception {

		map.put("manCyl", (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		map.put("usrId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		model.addAttribute("resultList", fmMworkService.fmMwork005_t(map));
		return "FM-MWORK005_T";
	}

	@RequestMapping("/FM-MWORK005_APP.do")
	public ModelAndView fmMwork005_app(@RequestParam Map<String, String> map, ModelMap model, HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		map.put("usrId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("result", fmMworkService.fmMwork005_app(map));
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping("/FM-MWORK005_RTN.do")
	public ModelAndView fmMwork005_rtn(@RequestParam Map<String, String> map, ModelMap model, HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		map.put("usrId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("result", fmMworkService.fmMwork005_rtn(map));
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping(value = "/FM-MWORK006.do")
	public String fmMwork006(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model, HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		model.addAttribute("loginAuth", (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));
		return "FM-MWORK006";
	}

	@RequestMapping(value="/FM-MWORK006_excelNew_popup.do")
	public String fmMwork006_excelNew_Upload (@RequestParam Map<String, String> map, HttpServletRequest req, ModelMap model) throws Exception {
		return "FM-MWORK006_excelNew_popup";
	}

	@RequestMapping(value="/FM-MWORK006_download_files.do")
	public String fmMwork006_download_files (@ModelAttribute SearchVO searchVO,
											 HttpServletRequest httpServletRequest,
											 HttpServletResponse httpServletResponse) throws Exception {

		searchVO.setFindusr("Y");
		searchVO.setWorkState("A");
		searchVO.setCompany(propertyService.getString("company"));
		searchVO.setManCyl((String)httpServletRequest.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		searchVO.setAuth((String) httpServletRequest.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));
		String findusr = (String) httpServletRequest.getSession().getAttribute(CommonConfig.SES_USER_KEY);

		searchVO.setPageIndex(1);
		searchVO.setPageUnit(Integer.MAX_VALUE);

		if (searchVO.getFindusr() == null) {
			searchVO.setFindusr("");
		} else {
			searchVO.setFindusr(findusr);
		}

		int totCnt = fmMworkService.getAllWorkListCnt(searchVO);
		PaginationInfo paginationInfo = new PaginationUtil(searchVO, totCnt).getPageData();

		List<EgovMap> list = (List<EgovMap>) fmMworkService.getAllWorkList(searchVO);

		// 제일 위에서 여기까지는 mwork/FM-MWORK006.do (com.uwo.isms.web.FMMworkController.fmMwork006_list) 에서
		// 개인 업무 현황 목록 요청하는 로직을 그대로 사용했다.
		// 중복된 코드를 리팩토링하면 좋겠지만 작업 시간이 없다.

		// fm_mwork006_createZip 메소드 로직도 기존에 있던걸 바탕으로 수정한 코드다.
		// 매개변수로 넘어가는 값들이 좀 이상하지만 시간상 그대로 진행한다.
		String zipName = fmMworkService.fm_mwork006_createZip(list, searchVO);

		String fileName = "개인업무증적파일.zip";

		File file = new File(zipName);

		ServletOutputStream out = httpServletResponse.getOutputStream();

		try {
			httpServletResponse.setContentType("application/zip");
			httpServletResponse.setHeader("Content-Transfer-Encoding:", "binary");
			httpServletResponse.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("ksc5601"), "8859_1") + ";");
			httpServletResponse.setHeader("Pragma", "no-cache;");
			httpServletResponse.setHeader("Expires", "-1;");
			httpServletResponse.setHeader("Content-Length", "" + file.length());

			FileInputStream in = new FileInputStream(file);
			byte[] buffer = new byte[8 * 1024];
			int bytesread = 0, bytesBuffered = 0;

			while ((bytesread = in.read(buffer)) > -1) {
				out.write(buffer, 0, bytesread);
				bytesBuffered += bytesread;
				if (bytesBuffered > 1024 * 1024) { //flush after 1MB
					bytesBuffered = 0;
					out.flush();
				}
			}
			in.close();
		} finally {
			if (out != null) {
				out.flush();
			}
			file.delete();
		}



		return null;
	}

	@RequestMapping(value = "/FM-MWORK007.do")
	public String fmMwork007(@ModelAttribute("searchVO") SearchVO searchVO, HttpServletRequest req, ModelMap model)
			throws Exception {

		model.addAttribute("loginAuth", (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));
		return "FM-MWORK007";
	}

	@RequestMapping(value = "/FM-MWORK007_getWorkList.do")
	public ModelAndView fmMwork007_getWorkList(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		searchVO.setManCyl((String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

		// 업무리스트
		List<?> workList = fmMworkService.getWorkList(searchVO);

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
		resultMap.put("workList", workList);
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping(value = "/FM-MWORK008.do")
	public String fmMwork008(@ModelAttribute("searchVO") SearchVO searchVO, HttpServletRequest req, ModelMap model)
			throws Exception {

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("sesAuthKey", (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));
		paramMap.put("sesServiceKey", (String) req.getSession().getAttribute(CommonConfig.SES_SERVICE_KEY));
		paramMap.put("sesDeptKey", (String) req.getSession().getAttribute(CommonConfig.SES_DEPT_KEY));

		return "FM-MWORK008";
	}

	@RequestMapping(value = "/FM-MWORK008_getCntrWork.do")
	public ModelAndView fmMwork008_getCntrWork(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		searchVO.setManCyl((String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		String div = (String) req.getSession().getAttribute(CommonConfig.SES_DIVISION_KEY);
		String key = (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY);
		String auth = (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);

		if (auth.equals("P")) {
			searchVO.setDivision(div);
			searchVO.setWorker(key);
		}

		searchVO.setCompany(propertyService.getString("company"));
		List<?> resultList = fmMworkService.fm_mwork008_list(searchVO);
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
		resultMap.put("resultList", resultList);
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping(value = "/FM-MWORK009.do")
	public String fmMwork009(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model,
			final HttpServletRequest req) throws Exception {

		searchVO.setSekAth((String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));

		int totCnt = fmMworkService.fm_mwork009_cnt(searchVO);
		model.addAttribute("paginationInfo", new PaginationUtil(searchVO, totCnt).getPageData());
		model.addAttribute("resultList", fmMworkService.fm_mwork009_list(searchVO));

		return "FM-MWORK009";
	}

	@RequestMapping(value = "FM-MWORK009_RW.do")
	public String FM_MWORK009_W(@ModelAttribute("vo") BoardVO vo, Model model, HttpServletRequest req)
			throws Exception {
		model.addAttribute("vo", new BoardVO());

		return "/FM-MWORK009-W";
	}

	@RequestMapping(value = "FM-MWORK009_W.do", method = RequestMethod.POST)
	public String FM_MWORK009_W(@ModelAttribute("vo") BoardVO vo, BindingResult bindingResult, Model model,
			final HttpServletRequest req) throws Exception {

		String uploadPath = propertyService.getString("boardUploadPath");
		req.setAttribute("uploadPath", uploadPath);

		FileUpload fu = new FileUpload();
		// parammeter : req, umf_tbl_gbn, umf_con_gbn
		List<FileVO> list = fu.fileuplaod(req, "BRD", "1");
		String division = (String) req.getSession().getAttribute(CommonConfig.SES_DIVISION_KEY);
		String key = (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY);
		vo.setUbm_rgt_id(key);
		vo.setUbm_div_cod(division);
		vo.setBcyCode(CommonConfig.SES_MAN_CYL_KEY);

		fmMworkService.fm_mwork009_write(vo, list);

		if (vo.getUbm_ntc_yn() == null || vo.getUbm_ntc_yn().equals("")) {
			vo.setUbm_ntc_yn("N");
		}

		if (vo.getUbm_ntc_yn().equals("Y")) {
			String userKey = "";
			String eventCode = EventTitleMessage.E08_CODE;
			String title = EventTitleMessage.E08;
			String contents = "";
			Map map = new HashMap();
			map.put("division", division);
			map.put("sekAuth", vo.getUbm_sek_ath());

			List uList = commonUtilService.getDivisionUser(map);
			for (int j = 0; j < uList.size(); j++) {
				userKey = (String) uList.get(j);
				contents = title + "<p />" + vo.getUbm_brd_cts() + "<br />";
				sendMailService.sendMail(key, eventCode, title, contents, userKey);

			}
		}

		return "redirect:/mwork/FM-MWORK009.do";
	}

	@RequestMapping("FM-MWORK009_V.do")
	public String FM_MWORK009_V(@RequestParam("selectedId") String id, Model model, final HttpServletRequest req)
			throws Exception {

		BoardVO vo = new BoardVO();
		vo.setUbm_brd_key(Integer.parseInt(id));
		FileVO fvo = new FileVO();
		fvo.setUmf_con_key(id);
		fvo.setUmf_tbl_gbn("BRD");
		fvo.setUmf_con_gbn("1");
		BoardVO rVo = fmMworkService.fm_mwork009_read(vo);

		int b_auth = Integer.parseInt(rVo.getUbm_dwn_ath());
		String s_auth = (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);

		model.addAttribute("vo", rVo);
		// 보드권한에 따른 파일리스트 출력여부
		if ((s_auth.equals("A") || (s_auth.equals("P") && b_auth > 1)) || ((s_auth.equals("N") && b_auth > 2))) {
			model.addAttribute("file", fmMworkService.fm_file(fvo));
		}
		return "/FM-MWORK009-V";
	}

	@RequestMapping("FM-MWORK009_R.do")
	public String FM_MWORK009_R(@RequestParam("seq") String seq, Model model, final HttpServletRequest req)
			throws Exception {
		BoardVO vo = new BoardVO();
		vo.setUbm_brd_key(Integer.parseInt(seq));
		FileVO fvo = new FileVO();
		fvo.setUmf_con_key(seq);
		fvo.setUmf_tbl_gbn("BRD");
		fvo.setUmf_con_gbn("1");
		BoardVO rVo = fmMworkService.fm_mwork009_read(vo);

		int b_auth = Integer.parseInt(rVo.getUbm_dwn_ath());
		String s_auth = (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);

		model.addAttribute("vo", rVo);
		// 보드권한에 따른 파일리스트 출력여부
		if ((s_auth.equals("A") || (s_auth.equals("P") && b_auth > 1)) || ((s_auth.equals("N") && b_auth > 2))) {
			model.addAttribute("file", fmMworkService.fm_file(fvo));
		}
		return "/FM-MWORK009-W";
	}

	@RequestMapping("FM-MWORK009_U.do")
	public String FM_MWORK009_U(BoardVO vo, BindingResult bindingResult, Model model, final HttpServletRequest req)
			throws Exception {
		String uploadPath = propertyService.getString("boardUploadPath");
		req.setAttribute("uploadPath", uploadPath);
		FileUpload fu = new FileUpload();
		// parammeter : req, umf_tbl_gbn, umf_con_gbn
		List<FileVO> list = fu.fileuplaod(req, "BRD", "1");

		vo.setUbm_rgt_id((String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		vo.setUbm_div_cod((String) req.getSession().getAttribute(CommonConfig.SES_DIVISION_KEY));
		vo.setBcyCode(CommonConfig.SES_MAN_CYL_KEY);

		fmMworkService.fm_mwork009_update(vo, list);
		String page = "redirect:/mwork/FM-MWORK009_R.do?seq=" + vo.getUbm_brd_key();
		return page;
	}

	@RequestMapping("FM-MWORK009_D.do")
	public String FM_MWORK009_D(BoardVO vo, BindingResult bindingResult, Model model) throws Exception {
		fmMworkService.fm_mwork009_delete(vo);
		return "forward:/mwork/FM-MWORK009.do";
	}

	@RequestMapping(value = "/FM-MWORK010.do")
	public String fmMwork010(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model,
			final HttpServletRequest req) throws Exception {

		searchVO.setSekAth((String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));

		int totCnt = fmMworkService.fm_mwork010_cnt(searchVO);
		model.addAttribute("paginationInfo", new PaginationUtil(searchVO, totCnt).getPageData());
		model.addAttribute("resultList", fmMworkService.fm_mwork010_list(searchVO));

		return "FM-MWORK010";
	}

	@RequestMapping(value = "FM-MWORK010_RW.do")
	public String FM_MWORK010_W(@ModelAttribute("vo") BoardVO vo, Model model, HttpServletRequest req)
			throws Exception {
		model.addAttribute("vo", new BoardVO());

		return "/FM-MWORK010-W";
	}

	@RequestMapping(value = "FM-MWORK010_W.do", method = RequestMethod.POST)
	public String FM_MWORK010_W(@ModelAttribute("vo") BoardVO vo, BindingResult bindingResult, Model model,
			final HttpServletRequest req) throws Exception {

		String uploadPath = propertyService.getString("boardUploadPath");
		req.setAttribute("uploadPath", uploadPath);

		FileUpload fu = new FileUpload();
		// parammeter : req, umf_tbl_gbn, umf_con_gbn
		List<FileVO> list = fu.fileuplaod(req, "BRD", "2");

		String division = (String) req.getSession().getAttribute(CommonConfig.SES_DIVISION_KEY);
		String key = (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY);
		vo.setUbm_rgt_id(key);
		vo.setUbm_div_cod(division);
		vo.setBcyCode(CommonConfig.SES_MAN_CYL_KEY);

		fmMworkService.fm_mwork010_write(vo, list);

		if (vo.getUbm_ntc_yn() == null || vo.getUbm_ntc_yn().equals("")) {
			vo.setUbm_ntc_yn("N");
		}

		if (vo.getUbm_ntc_yn().equals("Y")) {
			String userKey = "";
			String eventCode = EventTitleMessage.E09_CODE;
			String title = EventTitleMessage.E09;
			String contents = "";
			Map map = new HashMap();
			map.put("division", division);
			map.put("sekAuth", vo.getUbm_sek_ath());

			List uList = commonUtilService.getDivisionUser(map);
			for (int j = 0; j < uList.size(); j++) {
				userKey = (String) uList.get(j);
				contents = title + "<p />" + vo.getUbm_brd_cts() + "<br />";
				sendMailService.sendMail(key, eventCode, title, contents, userKey);

			}
		}

		return "redirect:/mwork/FM-MWORK010.do";
	}

	@RequestMapping("FM-MWORK010_V.do")
	public String FM_MWORK010_V(@RequestParam("selectedId") String id, Model model, final HttpServletRequest req)
			throws Exception {

		BoardVO vo = new BoardVO();
		vo.setUbm_brd_key(Integer.parseInt(id));
		FileVO fvo = new FileVO();
		fvo.setUmf_con_key(id);
		fvo.setUmf_tbl_gbn("BRD");
		fvo.setUmf_con_gbn("2");
		BoardVO rVo = fmMworkService.fm_mwork010_read(vo);

		int b_auth = Integer.parseInt(rVo.getUbm_dwn_ath());
		String s_auth = (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);

		model.addAttribute("vo", rVo);
		// 보드권한에 따른 파일리스트 출력여부
		if ((s_auth.equals("A") || (s_auth.equals("P") && b_auth > 1)) || ((s_auth.equals("N") && b_auth > 2))) {
			model.addAttribute("file", fmMworkService.fm_file(fvo));
		}
		return "/FM-MWORK010-V";
	}

	@RequestMapping("FM-MWORK010_R.do")
	public String FM_MWORK010_R(@RequestParam("seq") String seq, Model model, final HttpServletRequest req)
			throws Exception {
		BoardVO vo = new BoardVO();
		vo.setUbm_brd_key(Integer.parseInt(seq));
		FileVO fvo = new FileVO();
		fvo.setUmf_con_key(seq);
		fvo.setUmf_tbl_gbn("BRD");
		fvo.setUmf_con_gbn("2");
		BoardVO rVo = fmMworkService.fm_mwork010_read(vo);

		int b_auth = Integer.parseInt(rVo.getUbm_dwn_ath());
		String s_auth = (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);

		model.addAttribute("vo", rVo);
		// 보드권한에 따른 파일리스트 출력여부
		if ((s_auth.equals("A") || (s_auth.equals("P") && b_auth > 1)) || ((s_auth.equals("N") && b_auth > 2))) {
			model.addAttribute("file", fmMworkService.fm_file(fvo));
		}
		return "/FM-MWORK010-W";
	}

	@RequestMapping("FM-MWORK010_U.do")
	public String FM_MWORK010_U(BoardVO vo, BindingResult bindingResult, Model model, final HttpServletRequest req)
			throws Exception {

		String uploadPath = propertyService.getString("boardUploadPath");
		req.setAttribute("uploadPath", uploadPath);
		FileUpload fu = new FileUpload();
		// parammeter : req, umf_tbl_gbn, umf_con_gbn
		List<FileVO> list = fu.fileuplaod(req, "BRD", "2");

		vo.setUbm_rgt_id((String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		vo.setUbm_div_cod((String) req.getSession().getAttribute(CommonConfig.SES_DIVISION_KEY));
		vo.setBcyCode(CommonConfig.SES_MAN_CYL_KEY);

		fmMworkService.fm_mwork010_update(vo, list);
		String page = "redirect:/mwork/FM-MWORK010_R.do?seq=" + vo.getUbm_brd_key();
		return page;
	}

	@RequestMapping("FM-MWORK010_D.do")
	public String FM_MWORK010_D(BoardVO vo, BindingResult bindingResult, Model model) throws Exception {

		fmMworkService.fm_mwork010_delete(vo);
		return "forward:/mwork/FM-MWORK010.do";
	}

	@RequestMapping(value = "/FM-MWORK011.do")
	public String fmMwork011(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model,
			final HttpServletRequest req) throws Exception {

		searchVO.setSekAth((String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));

		int totCnt = fmMworkService.fm_mwork011_cnt(searchVO);
		model.addAttribute("paginationInfo", new PaginationUtil(searchVO, totCnt).getPageData());
		model.addAttribute("resultList", fmMworkService.fm_mwork011_list(searchVO));

		return "FM-MWORK011";
	}

	@RequestMapping(value = "FM-MWORK011_RW.do")
	public String FM_MWORK011_W(@ModelAttribute("vo") BoardVO vo, Model model, HttpServletRequest req)
			throws Exception {
		model.addAttribute("vo", new BoardVO());

		return "/FM-MWORK011-W";
	}

	@RequestMapping(value = "FM-MWORK011_W.do", method = RequestMethod.POST)
	public String FM_MWORK011_W(@ModelAttribute("vo") BoardVO vo, BindingResult bindingResult, Model model,
			final HttpServletRequest req) throws Exception {

		String uploadPath = propertyService.getString("boardUploadPath");
		req.setAttribute("uploadPath", uploadPath);

		FileUpload fu = new FileUpload();
		// parammeter : req, umf_tbl_gbn, umf_con_gbn
		List<FileVO> list = fu.fileuplaod(req, "BRD", "7");

		String division = (String) req.getSession().getAttribute(CommonConfig.SES_DIVISION_KEY);
		String key = (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY);
		vo.setUbm_rgt_id(key);
		vo.setUbm_div_cod(division);
		vo.setBcyCode(CommonConfig.SES_MAN_CYL_KEY);

		fmMworkService.fm_mwork011_write(vo, list);

		if (vo.getUbm_ntc_yn() == null || vo.getUbm_ntc_yn().equals("")) {
			vo.setUbm_ntc_yn("N");
		}

		if (vo.getUbm_ntc_yn().equals("Y")) {
			String userKey = "";
			String eventCode = EventTitleMessage.E08_CODE;
			String title = EventTitleMessage.E08;
			String contents = "";
			Map map = new HashMap();
			map.put("division", division);
			map.put("sekAuth", vo.getUbm_sek_ath());

			List uList = commonUtilService.getDivisionUser(map);
			for (int j = 0; j < uList.size(); j++) {
				userKey = (String) uList.get(j);
				contents = title + "<p />" + vo.getUbm_brd_cts() + "<br /> ";
				sendMailService.sendMail(key, eventCode, title, contents, userKey);

			}
		}

		return "redirect:/mwork/FM-MWORK011.do";
	}

	@RequestMapping("FM-MWORK011_V.do")
	public String FM_MWORK011_V(@RequestParam("selectedId") String id, Model model, final HttpServletRequest req)
			throws Exception {

		BoardVO vo = new BoardVO();
		vo.setUbm_brd_key(Integer.parseInt(id));
		FileVO fvo = new FileVO();
		fvo.setUmf_con_key(id);
		fvo.setUmf_tbl_gbn("BRD");
		fvo.setUmf_con_gbn("7");
		BoardVO rVo = fmMworkService.fm_mwork011_read(vo);

		int b_auth = Integer.parseInt(rVo.getUbm_dwn_ath());
		String s_auth = (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);

		model.addAttribute("vo", rVo);
		// 보드권한에 따른 파일리스트 출력여부
		if ((s_auth.equals("A") || (s_auth.equals("P") && b_auth > 1)) || ((s_auth.equals("N") && b_auth > 2))) {
			model.addAttribute("file", fmMworkService.fm_file(fvo));
		}
		return "/FM-MWORK011-V";
	}

	@RequestMapping("FM-MWORK011_R.do")
	public String FM_MWORK011_R(@RequestParam("seq") String seq, Model model, final HttpServletRequest req)
			throws Exception {
		BoardVO vo = new BoardVO();
		vo.setUbm_brd_key(Integer.parseInt(seq));
		FileVO fvo = new FileVO();
		fvo.setUmf_con_key(seq);
		fvo.setUmf_tbl_gbn("BRD");
		fvo.setUmf_con_gbn("7");
		BoardVO rVo = fmMworkService.fm_mwork011_read(vo);

		int b_auth = Integer.parseInt(rVo.getUbm_dwn_ath());
		String s_auth = (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);

		model.addAttribute("vo", rVo);
		// 보드권한에 따른 파일리스트 출력여부
		if ((s_auth.equals("A") || (s_auth.equals("P") && b_auth > 1)) || ((s_auth.equals("N") && b_auth > 2))) {
			model.addAttribute("file", fmMworkService.fm_file(fvo));
		}
		return "/FM-MWORK011-W";
	}

	@RequestMapping("FM-MWORK011_U.do")
	public String FM_MWORK011_U(BoardVO vo, BindingResult bindingResult, Model model, final HttpServletRequest req)
			throws Exception {

		String uploadPath = propertyService.getString("uploadpath");
		req.setAttribute("uploadPath", uploadPath);
		FileUpload fu = new FileUpload();
		// parammeter : req, umf_tbl_gbn, umf_con_gbn
		List<FileVO> list = fu.fileuplaod(req, "BRD", "7");

		vo.setUbm_rgt_id((String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		vo.setUbm_div_cod((String) req.getSession().getAttribute(CommonConfig.SES_DIVISION_KEY));
		vo.setBcyCode(CommonConfig.SES_MAN_CYL_KEY);

		fmMworkService.fm_mwork011_update(vo, list);
		String page = "redirect:/mwork/FM-MWORK011_R.do?seq=" + vo.getUbm_brd_key();
		return page;
	}

	@RequestMapping("FM-MWORK011_D.do")
	public String FM_MWORK011_D(BoardVO vo, BindingResult bindingResult, Model model) throws Exception {

		fmMworkService.fm_mwork011_delete(vo);
		return "forward:/mwork/FM-MWORK011.do";
	}

	@RequestMapping(value = "/FM-MWORK012.do")
	public String fmMwork012(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model,
			final HttpServletRequest req) throws Exception {

		searchVO.setWorker((String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		searchVO.setAuth((String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));

		int totCnt = fmMworkService.fm_mwork012_cnt(searchVO);
		model.addAttribute("paginationInfo", new PaginationUtil(searchVO, totCnt).getPageData());
		model.addAttribute("resultList", fmMworkService.fm_mwork012_list(searchVO));

		return "FM-MWORK012";
	}

	@RequestMapping(value = "/FM-MWORK013.do")
	public String fmMwork013(@ModelAttribute("searchVO") SearchVO searchVO, HttpServletRequest req, ModelMap model)
			throws Exception {

		return "FM-MWORK013";
	}

	@RequestMapping(value = "/FM-MWORK013_getAppList.do")
	public ModelAndView fmMwork013_getAppList(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();
		/*
		 * String auth =
		 * (String)req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY)
		 * ; if (auth.equals("A")) { searchVO.setDept(null); }
		 */
		searchVO.setUsrkey((String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		searchVO.setManCyl(req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY).toString());

		// 2018-07-18 s,
		// paging
        searchVO.setPageIndex(Integer.parseInt(req.getParameter("page")));
        searchVO.setPageUnit(Integer.parseInt(req.getParameter("rows")));
        // jqgrid sorting
        searchVO.setSidx(req.getParameter("sidx"));
        searchVO.setOrder(req.getParameter("sord"));

        int totCnt = fmMworkService.fm_mwork013_getWorkAppCnt(searchVO);
        PaginationInfo paginationInfo = new PaginationUtil(searchVO, totCnt).getPageData();

		// 업무리스트
		List<?> workAppList = fmMworkService.fm_mwork013_getWorkAppList(searchVO);

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
		resultMap.put("result", workAppList);

		// paging
		resultMap.put("currentPageNo", paginationInfo.getCurrentPageNo());
        resultMap.put("totalPage", paginationInfo.getTotalPageCount());
        resultMap.put("totalRecord", paginationInfo.getTotalRecordCount());

		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping(value = "/FM-MWORK013_his_popup.do")
	public String fmMwork013_his_popup(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		searchVO.setManCyl(req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY).toString());
		searchVO.setDivision(req.getSession().getAttribute(CommonConfig.SES_DIVISION_KEY).toString());
		searchVO.setCode(Constants.HIS_CODE);

		model.addAttribute("resultList", fmMworkService.fm_mwork013_getWorkAppList(searchVO));
		return "FM-MWORK013_his_popup";
	}

	@RequestMapping("/FM-MWORK013_app_stateUpdate.do")
	public ModelAndView fmMwork013_appStateUpdate(@ModelAttribute("workVO") WorkVO workVO, HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();
		int result = 1;

		fmMworkService.fm_mwork013_appStateUpdate(workVO);

		mav.setViewName("redirect:../mwork/FM-MWORK013.do");

		return mav;
	}

	@RequestMapping(value = "/FM-MWORK014.do")
	public String fmMwork014(@ModelAttribute("searchVO") SearchVO searchVO, HttpServletRequest req, ModelMap model)
			throws Exception {

		String auth = (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);
		String dept = "";
		if (!auth.equals("A")) {
			dept = (String) req.getSession().getAttribute(CommonConfig.SES_DEPT_KEY);
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("dept", dept);
		// model.addAttribute("depList",
		// commonCodeService.getCommonCodeList(Constants.DEP_COD));
		model.addAttribute("auth", auth);

		return "FM-MWORK014";
	}

	@RequestMapping(value = "/FM-MWORK014_LIST.do")
	public ModelAndView fmMwork014_list(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		ModelAndView mav = new ModelAndView();

		String company = propertyService.getString("company");
		String manCyl = (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY);
		String auth = (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);

		String stOrg = (String) req.getParameter("stOrg");
		String hqOrg = (String) req.getParameter("hqOrg");
		String gpOrg = (String) req.getParameter("gpOrg");

		searchVO.setCompany(company);
		searchVO.setManCyl(manCyl);
		searchVO.setStOrg(stOrg);
		searchVO.setHqOrg(hqOrg);
		searchVO.setGpOrg(gpOrg);

		List<?> list = fmMworkService.fm_mwork014_list(searchVO);
		model.addAttribute("resultList", list);

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
		resultMap.put("resultList", list);
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping(value = "/FM-NOWORK_SENDMAIL.do")
	public ModelAndView fmNOWORK_SENDMAIL(ModelMap model, HttpServletRequest req, HttpServletResponse res)
			throws Exception {

		ModelAndView mav = new ModelAndView();

		String key = (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY);
		String userKey = req.getParameter("userKey");

		// 2017-10-11, email template
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("uccFirCod", "evtArm");
		map2.put("uccSndCod", EventTitleMessage.E10_CODE);
		EgovMap emap = commonUtilService.getUccComCodeInfo(map2);
		String mailTemplate = (String) emap.get("uccEtc");

		String id[] = userKey.split(",");

		for (int u = 0; u < id.length; u++) {

			String eventCode = EventTitleMessage.E10_CODE;
			String title = EventTitleMessage.E10;
			//String contents = title + "<p />해당내용을 확인해주세요.<br />";
			String contents = "";
			Map map = new HashMap();
			map.put("manCyl", (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
			map.put("userKey", String.valueOf(id[u]));
			List<?> list = fmMworkService.fm_mwork014_list_nowork(map);

			for (int i = 0; i < list.size(); i++) {
				EgovMap cMap = (EgovMap) list.get(i);
				//contents += "■ 업무명 : " + cMap.get("utdDocNam") + "   ■ 업무기한 : " + cMap.get("utwStrDat") + " ~ "
				//		+ cMap.get("utwEndDat") + "<br />";

				// 2017-10-11, text to html
				contents += new StringBuffer()
						.append("<tr>")
						.append("<td width=\"436\" style=\"border-width:1; border-color:gray; border-style:solid;\">")
						.append("<p align=\"center\" style=\"line-height:120%; margin-top:0; margin-bottom:0;\">")
						.append(cMap.get("utdDocNam"))
						.append("</p>")
						.append("</td>")
						.append("<td width=\"77\" style=\"border-width:1; border-color:gray; border-style:solid;\">")
						.append("<p align=\"center\" style=\"line-height:120%; margin-top:0; margin-bottom:0;\">")
						.append(cMap.get("utdTrmGbnTxt"))
						.append("</p>")
						.append("</td>")
						.append("<td width=\"111\" style=\"border-width:1; border-color:gray; border-style:solid;\">")
						.append("<p align=\"center\" style=\"line-height:120%; margin-top:0; margin-bottom:0;\">")
						.append(cMap.get("utwEndDat"))
						.append("</p>")
						.append("</td>")
						.append("</tr>").toString();
			}
			log.debug("sendMail: {}", contents);
			String mailBody = mailTemplate.replace("{__BODY__}", contents);

			sendMailService.sendMail(key, eventCode, title, mailBody, String.valueOf(id[u]));

		}
		//res.setCharacterEncoding("");
		//res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
		resultMap.put("SUC", "SUC");
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping(value = "/FM-NOWORK_SENDSMS.do")
	public ModelAndView fmNOWORK_SENDSMS(ModelMap model, HttpServletRequest req, HttpServletResponse res)
			throws Exception {

		ModelAndView mav = new ModelAndView();

		String key = (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY);
		String userKey = req.getParameter("userKey");
		String type = req.getParameter("mode");

		String id[] = userKey.split(",");
		String title = EventTitleMessage.E10;
		String cel_num = "";

		for (int u = 0; u < id.length; u++) {
			String ukey = id[u];
			if (type.equals("mw")) {
				cel_num = fmMworkService.fm_mwork014_get_celnum(ukey);
				Map map = new HashMap();
				map.put("cnum", cel_num);
				map.put("content", title);
				fmMworkService.fm_mwork014_send_sms(map);
			} else if (type.equals("st")) {
				String comment[] = ukey.split("&");
				String nowork[] = comment[1].split(" ");
				String delay[] = comment[2].split(" ");
				ukey = comment[0];
				cel_num = fmMworkService.fm_mwork014_get_celnum(ukey);
				Map map = new HashMap();
				map.put("cnum", cel_num);
				map.put("content", "[ITCS 업무알림] \n 미진행 : " + nowork[0] + " 건, 지연 : " + delay[0] + " 건, 금일완료 : "
						+ comment[3] + " 건의 업무가 존재합니다.");
				fmMworkService.fm_mwork014_send_sms(map);
			}
		}

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
		resultMap.put("SUC", "SUC");
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	/* 2017-10-11, 사용안함 확인됨
	@RequestMapping(value = "/FM-NOWORK_SENDMAIL_ALL.do")
	public ModelAndView fmNOWORK_SENDMAIL_ALL(ModelMap model, HttpServletRequest req, HttpServletResponse res)
			throws Exception {

		ModelAndView mav = new ModelAndView();


		 * 알람 테이블 데이터 입력 userKey => 받는 사람 key key => 보내는 사람 key evtCode => 이벤트
		 * 상황(UWO_EVT_ARM 참조) sysGbn => mail/sms 구분코드 dispatcher-servlet.xml에 설정
		 * tit => 이벤트 알람명 EventTitleMessage 클래스에 타이틀명 선언
		 * commonUtilService.insertAlarm(map) 메소드로 데이터 저장

		String manCyl = (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY);

		String key = (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY);
		String userKey = "";
		String eventCode = EventTitleMessage.E10_CODE;
		String title = EventTitleMessage.E10;
		String contents = "";
		Map map = new HashMap();
		map.put("manCyl", manCyl);

		List uList = commonUtilService.getAllNoworkUser(manCyl);
		for (int j = 0; j < uList.size(); j++) {
			userKey = (String) uList.get(j);
			map.put("userKey", uList.get(j));
			contents = title + "<p />해당내용을 확인해주세요.<br /> ";
			List<?> list = fmMworkService.fm_mwork014_list_nowork(map);
			for (int i = 0; i < list.size(); i++) {
				EgovMap cMap = (EgovMap) list.get(i);
				contents += "■ 업무명 : " + cMap.get("utdDocNam") + " ■ 업무기한 : " + cMap.get("utwStrDat") + " ~ "
						+ cMap.get("utwEndDat") + "<br />";
			}
			sendMailService.sendMail(key, eventCode, title, contents, userKey);

		}

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
		resultMap.put("SUC", "SUC");
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}*/

	@RequestMapping(value = "/FM-MWORK015.do")
	public String fmMwork015(@ModelAttribute("searchVO") SearchVO searchVO, HttpServletRequest req, ModelMap model)
			throws Exception {

		int totCnt = fmMworkService.fm_mwork001_cnt(searchVO);
		model.addAttribute("paginationInfo", new PaginationUtil(searchVO, totCnt).getPageData());
		model.addAttribute("resultList", fmMworkService.fm_mwork009_list(searchVO));

		return "FM-MWORK015";
	}

	@RequestMapping(value = "/FM-MWORK016.do")
	public String fmMwork016(@ModelAttribute("searchVO") SearchVO searchVO, HttpServletRequest req, ModelMap model)
			throws Exception {

		int totCnt = fmMworkService.fm_mwork001_cnt(searchVO);
		model.addAttribute("paginationInfo", new PaginationUtil(searchVO, totCnt).getPageData());
		model.addAttribute("resultList", fmMworkService.fm_mwork009_list(searchVO));

		return "FM-MWORK016";
	}

	@RequestMapping(value = "/FM-MWORK017.do")
	public String fmMwork017(ModelMap model, HttpServletRequest req) throws Exception {

		return "FM-MWORK017";
	}

	@RequestMapping(value = "/FM-IFP_MTR_LIST.do")
	public ModelAndView fmIFP_MTR_LIST(ModelMap model, HttpServletRequest req, HttpServletResponse res)
			throws Exception {

		ModelAndView mav = new ModelAndView();

		String key = (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY);
		String division = req.getParameter("sDivision");
		String service = req.getParameter("sService");
		String dept = req.getParameter("sDept");
		String keyword = req.getParameter("sKeyword");
		if (division.equals("") || division == null) {
			division = (String) req.getSession().getAttribute(CommonConfig.SES_DIVISION_KEY);
			service = (String) req.getSession().getAttribute(CommonConfig.SES_SERVICE_KEY);
			dept = (String) req.getSession().getAttribute(CommonConfig.SES_DEPT_KEY);
		}
		Map map = new HashMap();
		map.put("division", division);
		map.put("service", service);
		map.put("dept", dept);
		map.put("keyword", keyword);
		map.put("manCyl", (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		List<?> list = fmMworkService.ifpMtrList(map);

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
		resultMap.put("result", list);
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping("/FM-IFP_MTR_popup.do")
	public String fmIFP_MTR_popup(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		String mtrKey = req.getParameter("mtrKey");

		EgovMap mtrMap = new EgovMap();
		List<?> posList = new ArrayList();

		if (mtrKey.equals("0")) {
			model.addAttribute("mode", "1");
		} else {
			mtrMap = fmMworkService.ifp_Mtr_popup(mtrKey);
			model.addAttribute("mode", "0");
		}

		model.addAttribute("mtrMap", mtrMap);
		model.addAttribute("mtrKey", mtrKey);

		return "FM-MWORK_IFP_MTR_popup";
	}

	@RequestMapping("/FM-MWORK017_IFP_MTR_save.do")
	public ModelAndView MWORK017_IFP_MTR_save(@ModelAttribute("ifpMtrVo") IFP_MTR_VO ifpMtrVo, HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		String divCod = (String) req.getSession().getAttribute(CommonConfig.SES_DIVISION_KEY);
		String svcCod = (String) req.getSession().getAttribute(CommonConfig.SES_SERVICE_KEY);
		String depCod = (String) req.getSession().getAttribute(CommonConfig.SES_DEPT_KEY);
		String manCyl = (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY);
		String key = (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY);

		if (ifpMtrVo.getMode().equals("1")) {
			ifpMtrVo.setUimDivCod(divCod);
			ifpMtrVo.setUimSvcCod(svcCod);
			ifpMtrVo.setUimDepCod(depCod);
			ifpMtrVo.setUimPrdCd(manCyl);
			ifpMtrVo.setUimRegId(key);
			fmMworkService.saveIfpMtr(ifpMtrVo);
		} else if (ifpMtrVo.getMode().equals("0")) {
			ifpMtrVo.setUimUpdId(key);
			fmMworkService.modifyIfpMtr(ifpMtrVo);
		}

		Map resultMap = new HashMap();
		resultMap.put("result", "SUC");
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping(value = "/FM-IFP_MTR_DEL.do")
	public ModelAndView fmIFP_MTR_DEL(ModelMap model, HttpServletRequest req, HttpServletResponse res)
			throws Exception {

		ModelAndView mav = new ModelAndView();

		String del = req.getParameter("delList");
		String key = (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY);

		List delList = new ArrayList();
		String delKey[] = del.split(",");
		for (int i = 0; i < delKey.length; i++) {
			delList.add(delKey[i]);
		}

		Map map = new HashMap();
		map.put("delList", delList);
		map.put("key", key);

		fmMworkService.ifpDel(map);

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
		resultMap.put("SUC", "SUC");
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping(value = "/FM-IFP_DTL_LIST.do")
	public ModelAndView fmIFP_DTL_LIST(ModelMap model, HttpServletRequest req, HttpServletResponse res)
			throws Exception {

		ModelAndView mav = new ModelAndView();

		String mtrKey = req.getParameter("mtrKey");
		Map map = new HashMap();
		List<?> list = fmMworkService.ifpDtlList(mtrKey);

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
		resultMap.put("result", list);
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping("/FM-IFP_DTL_popup.do")
	public String fmIFP_DTL_popup(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		String dtlKey = req.getParameter("dtlKey");
		String dtlMKey = req.getParameter("dtlMKey");

		EgovMap dtlMap = new EgovMap();
		EgovMap mtrMap = new EgovMap();
		List<?> posList = new ArrayList();

		mtrMap = fmMworkService.ifp_Mtr_popup(dtlMKey);

		if (dtlKey.equals("0")) {
			model.addAttribute("mode", "1");
		} else {
			dtlMap = fmMworkService.ifp_Dtl_popup(dtlKey);
			model.addAttribute("mode", "0");
		}

		model.addAttribute("mtrMap", mtrMap);
		model.addAttribute("dtlMap", dtlMap);
		model.addAttribute("dtlMKey", dtlMKey);
		model.addAttribute("dtlKey", dtlKey);

		return "FM-MWORK_IFP_DTL_popup";
	}

	@RequestMapping("/FM-MWORK017_IFP_DTL_save.do")
	public ModelAndView MWORK017_IFP_DTL_save(@ModelAttribute("ifpDtlVo") IFP_DTL_VO ifpDtlVo, HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		String key = (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY);

		if (ifpDtlVo.getMode().equals("1")) {
			ifpDtlVo.setUidRegId(key);
			fmMworkService.saveIfpDtl(ifpDtlVo);
			if (ifpDtlVo.getUidUseGbn().equals(Constants.FILE_GBN_DOC)) {
				fmMworkService.eraseIfpDtl(ifpDtlVo);
			}
		} else if (ifpDtlVo.getMode().equals("0")) {
			ifpDtlVo.setUidUpdId(key);
			fmMworkService.modifyIfpDtl(ifpDtlVo);
		}

		Map resultMap = new HashMap();
		resultMap.put("result", "SUC");
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping(value = "/FM-IFP_DTL_DEL.do")
	public ModelAndView fmIFP_DTL_DEL(ModelMap model, HttpServletRequest req, HttpServletResponse res)
			throws Exception {

		ModelAndView mav = new ModelAndView();

		String del = req.getParameter("delList");
		String key = (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY);

		List delList = new ArrayList();
		String delKey[] = del.split(",");
		for (int i = 0; i < delKey.length; i++) {
			delList.add(delKey[i]);
		}

		Map map = new HashMap();
		map.put("delList", delList);
		map.put("key", key);

		fmMworkService.ifpDel2(map);

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
		resultMap.put("SUC", "SUC");
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping(value = "/FM-MWORK018.do")
	public String fmMwork018(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model, HttpServletRequest req)
			throws Exception {

		return "FM-MWORK018";
	}

	@RequestMapping(value = "/FM-MWORK018_getWorkList.do")
	public ModelAndView fmMwork018_getWorkList(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		searchVO.setManCyl((String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

		// 업무리스트
		List<?> workList = fmMworkService.fm_mwork018_list(searchVO);

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
		resultMap.put("workList", workList);
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping(value = "/FM-MWORK019.do")
	public String fmMwork019(@ModelAttribute("searchVO") SearchVO searchVO, HttpServletRequest req, ModelMap model)
			throws Exception {

		int totCnt = fmMworkService.getworklist_count(searchVO);
		model.addAttribute("paginationInfo", new PaginationUtil(searchVO, totCnt).getPageData());
		model.addAttribute("resultList", fmMworkService.getworklist(searchVO));

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("sesAuthKey", (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));
		paramMap.put("sesServiceKey", (String) req.getSession().getAttribute(CommonConfig.SES_SERVICE_KEY));

		return "FM-MWORK019";
	}

	@RequestMapping(value = "/FM-MWORK019_getWorkList.do")
	public ModelAndView fmMwork019_getWorkList(@ModelAttribute("SearchVO") SearchVO searchVO, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		searchVO.setManCyl((String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

		List<?> resultList = fmMworkService.getworklist(searchVO);
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
		resultMap.put("resultList", resultList);
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping(value = "/getAllWorkList.do")
	public ModelAndView getAllWorkList(@ModelAttribute("SearchVO") SearchVO searchVO, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		Map map = new HashMap();
		map.put("worker", req.getParameter("worker"));
		map.put("trcKey", req.getParameter("trcKey"));
		map.put("manCyl", (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

		List<?> resultList = fmMworkService.getAllWorkList(map);
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
		resultMap.put("resultList", resultList);
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping(value = "/getAllWorkList_file.do")
	public ModelAndView getAllWorkList_file(@ModelAttribute("SearchVO") SearchVO searchVO, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		// Map map = new HashMap();
		// map.put("wrk_key", req.getParameter("wrk_key"));
		// map.put("manCyl",
		// (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

		String bcyCod = (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY);
		List<?> resultList = fileUploadService.selectFileList(req.getParameter("wrk_key"), Constants.FILE_GBN_WRK,
				Constants.FILE_CON_WRK, bcyCod);

		// List<?> resultList = fmMworkService.getAllWorkList_file(map);
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
		resultMap.put("resultList", resultList);
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping(value = "/getAllDocList_file.do")
	public ModelAndView getAllDocList_file(@ModelAttribute("SearchVO") SearchVO searchVO, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		// Map map = new HashMap();
		// map.put("wrk_key", req.getParameter("wrk_key"));
		// map.put("manCyl",
		// (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

		String bcyCod = (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY);
		List<?> resultList = fileUploadService.selectFileList_B(req.getParameter("wrk_key"), Constants.FILE_GBN_DOC,
				Constants.FILE_CON_DOC, bcyCod);

		// List<?> resultList = fmMworkService.getAllWorkList_file(map);
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
		resultMap.put("result", resultList);
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping(value = "/getSimilarWorkList.do")
	public ModelAndView getSimilarWorkList(@ModelAttribute("SearchVO") SearchVO searchVO, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		Map map = new HashMap();
		// map.put("worker", req.getParameter("worker"));
		map.put("trcKey", req.getParameter("trcKey"));
		map.put("wrkKey", req.getParameter("wrkKey"));
		map.put("manCyl", (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

		List<?> similarWorkList = fmMworkService.getSimilarWorkList(map);
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
		resultMap.put("similarWorkList", similarWorkList);
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping(value = "/batchUpload.do")
	public ModelAndView batchUpload(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		String uploadPath = propertyService.getString("workUploadPath");
		req.setAttribute("uploadPath", uploadPath);

		FileUpload fu = new FileUpload();
		List<FileVO> list = fu.fileuplaod_J(req, Constants.FILE_GBN_WRK, Constants.FILE_CON_WRK);

		Map map = new HashMap();
		map.put("wKey", req.getParameter("utw_wrk_key"));
		map.put("manCyl", (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

		fmMworkService.batchUpload(map, list);

		// Polaris Converter
		commonUtilService.insertPolarisConverter(req.getParameter("wKey"));

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
		resultMap.put("resultList", "suc");
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	/*
	 * 2016-10-19
	 * state 에서 옮겨옴. STATE002
	 */
	@RequestMapping(value="/FM-MWORK020.do")
	public String fmState002 (@ModelAttribute("searchVO") SearchVO searchVO
			, ModelMap model, HttpServletRequest req) throws Exception {

		return "FM-MWORK020";
	}

	/*
	 * 2016-10-27
	 * FM-MWORK002_P 가 거지발싸개처럼 만들어 놔서 다시 만듬
	 */
	@RequestMapping(value = "/FM-MWORK006_list.do")
	public ModelAndView fmMwork006_list(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

        String company = propertyService.getString("company");
        searchVO.setCompany(company);
        searchVO.setManCyl((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
   		searchVO.setAuth((String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));
		String findusr = (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY);

        searchVO.setPageIndex(Integer.parseInt(req.getParameter("page")));
        searchVO.setPageUnit(Integer.parseInt(req.getParameter("rows")));

        // 2017-08-14, jqgrid sorting
        searchVO.setCode(req.getParameter("sidx"));
        searchVO.setOrder(req.getParameter("sord"));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		if (searchVO.getFindusr() == null) {
			searchVO.setFindusr("");
		} else {
			searchVO.setFindusr(findusr);
		}

        int totCnt = fmMworkService.getAllWorkListCnt(searchVO);
        PaginationInfo paginationInfo = new PaginationUtil(searchVO, totCnt).getPageData();

        List<?> list = fmMworkService.getAllWorkList(searchVO);

        // Count
        searchVO.setWorkState("C");
        int workCompCount = fmMworkService.getAllWorkListCnt(searchVO);
        searchVO.setWorkState("D");
        int workDelayCount = fmMworkService.getAllWorkListCnt(searchVO);
        searchVO.setWorkState("R");
        int workReadyCount = fmMworkService.getAllWorkListCnt(searchVO);
        int workAllCount = workCompCount + workDelayCount + workReadyCount;

		resultMap.put("workAllCount", workAllCount);
		resultMap.put("workCompCount", workCompCount);
		resultMap.put("workDelayCount", workDelayCount);
		resultMap.put("workReadyCount", workReadyCount);


		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

        resultMap.put("result", list);
        resultMap.put("currentPageNo", paginationInfo.getCurrentPageNo());
        resultMap.put("totalPage", paginationInfo.getTotalPageCount());
        resultMap.put("totalRecord", paginationInfo.getTotalRecordCount());

        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping("/FM-MWORK_brd_popup.do")
	public String fmMwork_brd_popup (ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

        String key = req.getParameter("brdViewKey");

        BoardVO vo = fmCompsService.fm_comps003_popup_brdView(key);
        List<?> list = fmCompsService.fm_comps003_popup_brdFile(key);


        model.addAttribute("vo",vo);
        model.addAttribute("list",list);

        return "FM-COMPS003_brd_popup";
	}
}
