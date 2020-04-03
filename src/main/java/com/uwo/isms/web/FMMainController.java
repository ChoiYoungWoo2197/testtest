/**
 ***********************************
 * @source FMBasicController.java
 * @date 2014. 10. 9.
 * @project isms3
 * @description 정보보호관리개요 controller
 ***********************************
 */
package com.uwo.isms.web;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uwo.isms.domain.PopupVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UrlPathHelper;

import com.uwo.isms.common.CommonConfig;
import com.uwo.isms.service.FMInsptService;
import com.uwo.isms.service.FMMainService;
import com.uwo.isms.service.FMRiskmService;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import com.uwo.isms.service.PopupService;

@Controller
@RequestMapping("/")
public class FMMainController {

	Logger log = LogManager.getLogger(FMMainController.class);

	@Resource(name = "fmMainService")
	private FMMainService fmMainService;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;

	@Resource(name = "popupService")
	private PopupService popupService;

	@Autowired
	private FMRiskmService fmRiskmService;

	@Autowired
	private FMInsptService fmInsptService;

	@RequestMapping(value = "FM-MAIN.do")
	public String fmMain(ModelMap model,final HttpServletRequest req) throws Exception {

		model.addAttribute("usrKey", (String)req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		model.addAttribute("authKey", (String)req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));

		long time = System.currentTimeMillis();
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd H"); // 0~23시로 표기하기 위해서 H로 수정
		String now = dayTime.format(new Date(time));

		List<?> listPopup = popupService.popup(now);
		model.addAttribute("listPopup", listPopup);

		return "main";
	}

	@RequestMapping(value = "FM-MAIN_getBottomList.do")
	public ModelAndView fmGetBottomList(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

        Map<String, String> map = new HashMap<String, String>();

        String key = (String)req.getSession().getAttribute(CommonConfig.SES_USER_KEY);
        String authKey = (String)req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);
        // 2018-04-18 s, authKey -> auth
        map.put("auth", authKey);

        List<?> notiList = fmMainService.fmMain_NotiList(map);
        List<?> alarmList = fmMainService.fmMain_AlarmList(key);

        res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("noti", notiList);
        resultMap.put("alarm", alarmList);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping(value = "FM-MAIN_DetailAlarmList.do")
	public ModelAndView fmDetailAlarmList(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

        Map map = new HashMap();

        String code = req.getParameter("alarmCode");
        String s_auth = (String)req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);
        String key = (String)req.getSession().getAttribute(CommonConfig.SES_USER_KEY);
        String auth="";

        if(s_auth.equals("A")){
        	auth = "0";
        }else if(s_auth.equals("V")){
        	auth="1";
        }else if(s_auth.equals("P")){
        	auth="2";
        }
        map.put("code",code);
        map.put("auth",auth);
        List<?> list = new ArrayList();
        if(code.equals("1") || code.equals("2")){
        	list = fmMainService.fmMain_DetailAlarmList(map);
        }else{
        	list = fmMainService.fmMain_DetailAlarmList(key);
        }




        res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

		return mav;
	}

	/* 2018-04-18 s, 정상적으로 작동하지 않아서 FM-BOARD001_V.do 로 변경
	@RequestMapping(value = "FM-MAIN_TO_BOARD.do")
	public String FM_MAIN_TO_BOARD(Model model,final HttpServletRequest req) throws Exception {

		String brdGbn = req.getParameter("brdGbn");
		String brdKey = req.getParameter("brdKey");

		BoardVO vo = new BoardVO();
		vo.setUbm_brd_key(Integer.parseInt(brdKey));
		vo.setUbm_brd_gbn(brdGbn);
		FileVO fvo = new FileVO();
		fvo.setUmf_con_key(brdKey);
		fvo.setUmf_tbl_gbn(Constants.FILE_GBN_BRD);
		fvo.setUmf_con_gbn(brdGbn);
		BoardVO rVo = fmMainService.fm_board_read(vo);
		model.addAttribute("vo", rVo);

		model.addAttribute("vo", rVo);
		//보드권한에 따른 파일리스트 출력여부
		model.addAttribute("file", fmMainService.fm_file(fvo));

		return "FM-BOARD001-V";
	}*/

	@RequestMapping(value = "FM-MAIN_DetailWorkList.do")
	public ModelAndView fmDetailWorkList(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

       // String code = req.getParameter("code");
		String key = (String)req.getSession().getAttribute(CommonConfig.SES_USER_KEY);
        //String dateText = req.getParameter("dateText");
        //String sel = req.getParameter("sel");
        //String prd = req.getParameter("prd");
        //String keyword = req.getParameter("keyword");
        String auth = (String)req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);
        String dept = (String)req.getSession().getAttribute(CommonConfig.SES_DEPT_KEY);
		Map map = new HashMap();
		map.put("key", key);
		//map.put("dept", dept);
		//map.put("dateText", dateText);
		//map.put("keyword", keyword);
		//map.put("prd", prd);
		map.put("manCyl", (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		//map.put("auth", auth);

		List<?> delaylist = new ArrayList();
		List<?> noworklist = new ArrayList();
		List<?> compworklist = new ArrayList();

       	delaylist = fmMainService.SubWorkList(map);
       	noworklist = fmMainService.ApprovalWorkList(map);
       	compworklist = fmMainService.AllWorkList(map);

        res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
        resultMap.put("delay", delaylist);
        resultMap.put("nowork", noworklist);
        resultMap.put("compwork", compworklist);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping(value = "FM-MAIN_nPeriodWorkList.do")
	public ModelAndView fmnPeriodWorkList(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

        String key = (String)req.getSession().getAttribute(CommonConfig.SES_USER_KEY);
        String manCyl = (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY);

        Map map = new HashMap();
        map.put("key", key);
        map.put("manCyl",manCyl);

        List<?> list = new ArrayList();
        list = fmMainService.nPeriodWorkList(map);

        res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping(value="/FM-NP_popup.do")
	public String np_popup (@RequestParam("npKey") String npKey, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

        /*String key = (String)req.getSession().getAttribute(CommonConfig.SES_USER_KEY);
        List npList = new ArrayList();

        String npKey_[] = npKey.split(",");
        for(int i=0;i<npKey_.length;i++){
        	npList.add(npKey_[i]);
        }

        Map map = new HashMap();
        map.put("npList", npList);
        map.put("key", key);

        List<?> list = fmMainService.np_popup(map);*/

        model.addAttribute("npKey", npKey);

		return "FM-NP_popup";
	}

	@RequestMapping(value = "FM-MAIN_CreateNPList.do")
	public ModelAndView fmCreateNPList(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

        String npKey = req.getParameter("np");
        String key = (String)req.getSession().getAttribute(CommonConfig.SES_USER_KEY);
        List npList = new ArrayList();
        String npKey_[] = npKey.split(",");
        for(int i=0;i<npKey_.length;i++){
        	npList.add(npKey_[i]);
        }

        Map map = new HashMap();
        map.put("npList", npList);
        map.put("key", key);

        List<?> list = fmMainService.np_popup(map);

        res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping(value = "FM-MAIN_SAVE_NP.do")
	public ModelAndView fmMAIN_SAVE_NP(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

        String npKeyList = req.getParameter("npKeyList");
        String stDateList = req.getParameter("stDateList");
        String wrkEndList = req.getParameter("wrkEndList");
        String gbnList = req.getParameter("gbnList");

        String key = (String)req.getSession().getAttribute(CommonConfig.SES_USER_KEY);
        String div = (String)req.getSession().getAttribute(CommonConfig.SES_DIVISION_KEY);
        String svc = (String)req.getSession().getAttribute(CommonConfig.SES_SERVICE_KEY);
        String dep = (String)req.getSession().getAttribute(CommonConfig.SES_DEPT_KEY);
        String cyl = (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY);
        //List npList = new ArrayList();
        String npKey[] = npKeyList.split(",");
        String stDate[] = stDateList.split(",");
        String endDate[] = wrkEndList.split(",");
        String gbn[]= gbnList.split(",");

        //for(int i=0;i<npKey.length;i++){
        //	npList.add(npKey[i]);
        //}

        //Map map = new HashMap();
        //map.put("npList", npList);
        //map.put("key", key);

       // List<?> list = fmMainService.npDetail(map);

        for(int i=0;i<npKey.length;i++){
        	EgovMap eMap = new EgovMap();
        	eMap.put("utdTrcKey", npKey[i]);
        	eMap.put("key", key);
        	eMap.put("div", div);
        	eMap.put("svc", svc);
        	eMap.put("dep", dep);
        	eMap.put("cyl", cyl);
        	eMap.put("stdDate", stDate[i]);
        	eMap.put("utdWrkEnd", endDate[i]);
        	eMap.put("utdTrmGbn", gbn[i]);
        	fmMainService.saveNpWork(eMap);
        }

        res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
        resultMap.put("SUC", "SUC");
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping(value = "FM-MAIN_WORK_PERCENT.do")
	public ModelAndView fmWORK_PERCENT(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

        Map map = new HashMap();
        String key = (String)req.getSession().getAttribute(CommonConfig.SES_USER_KEY);
        String manCyl = (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY);
        String auth = (String)req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);
        String dept = (String)req.getSession().getAttribute(CommonConfig.SES_DEPT_KEY);
        map.put("key", key);
        map.put("manCyl", manCyl);
        map.put("auth", auth);
        map.put("dept", dept);
        List<?> list = fmMainService.workPercent(map);

        res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

		return mav;
	}


	@RequestMapping("/FM-RSIKM003_MAIN.do")
	public ModelAndView fmRiskm003_main (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();
		Map<String, Object> resultMap = new HashMap<String, Object>();

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		map.put("manCyl", (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		map.put("usrKey", (String)req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		map.put("usrId", (String)req.getSession().getAttribute(CommonConfig.SES_USER_ID_KEY));
		map.put("authKey", (String)req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));

		resultMap.put("rsklist", fmRiskmService.getRst003MainList(map));
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping("/FM-RSIKM010_MAIN.do")
	public ModelAndView fmRiskm_main010 (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();
		Map<String, Object> resultMap = new HashMap<String, Object>();

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		map.put("manCyl", (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		map.put("usrKey", (String)req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		map.put("authKey", (String)req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));

		resultMap.put("rsklist", fmRiskmService.getRst010MainList(map));
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping("/FM-INSPT002_MAIN.do")
	public ModelAndView fmInspt002_main (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();
		Map<String, Object> resultMap = new HashMap<String, Object>();

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		map.put("manCyl", (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		map.put("usrKey", (String)req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		map.put("authKey", (String)req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));

		resultMap.put("rsklist", fmInsptService.getInspt002MainList(map));
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	/*
	 *  전체 점검 항목 수
	 *  전체 보안 업무 수
	 */
	@RequestMapping("/getComplianceAndDocCount.do")
	public ModelAndView getComplianceAndDocCount (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();
		Map<String, Object> resultMap = new HashMap<String, Object>();

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		map.put("manCyl", (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

		resultMap.put("result", fmMainService.selectComplianceAndDocCount(map));
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	/*
	 *  전체 정보보호 업무 진행현황
	 *  완료, 지연, 미진행
	 *  비주기, 주기
	 */
	@RequestMapping("/getTotalProgressWorkCount.do")
	public ModelAndView getTotalProgressWorkCount (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();
		Map<String, Object> resultMap = new HashMap<String, Object>();

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		map.put("manCyl", (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		map.put("standard", req.getParameter("standard"));

		resultMap.put("result", fmMainService.selectProgressWorkCount(map));
		resultMap.put("periodResult", fmMainService.selectPeriodWorkCount(map));
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	/*
	 *  사용자 정보보호 업무 진행현황
	 *  완료, 지연, 미진행
	 *  비주기, 주기
	 */
	@RequestMapping("/getProgressWorkCount.do")
	public ModelAndView getProgressWorkCount (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();
		Map<String, Object> resultMap = new HashMap<String, Object>();

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		map.put("manCyl", (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		map.put("usrKey", (String)req.getSession().getAttribute(CommonConfig.SES_USER_KEY));

		resultMap.put("result", fmMainService.selectProgressWorkCount(map));
		resultMap.put("periodResult", fmMainService.selectPeriodWorkCount(map));
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	/*
	 *  컴플라이언스 현황
	 *  통제 분야/항목 수, 점검 항목 수
	 *  통제 항목 타이틀
	 */
	@RequestMapping("/getComplianceSummary.do")
	public ModelAndView getComplianceSummary (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();
		Map<String, Object> resultMap = new HashMap<String, Object>();

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		map.put("manCyl", (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		map.put("standard", req.getParameter("standard"));

		resultMap.put("result", fmMainService.selectComplianceDetailCount(map));
		//resultMap.put("list", fmMainService.selectComplianceTitle(map));
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	/*
	 *  좌측 메뉴 결재 수
	 */
	@RequestMapping("/getApprovalCount.do")
	public ModelAndView getApprovalCount (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();
		Map<String, Object> resultMap = new HashMap<String, Object>();

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		map.put("manCyl", (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		map.put("usrKey", (String)req.getSession().getAttribute(CommonConfig.SES_USER_KEY));

		resultMap.put("result", fmMainService.selectApprovalCount(map));
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	/*
	 * 2016-10-20
	 * 상단 네비게이션
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/titlebar.do")
	public String titlebar(ModelMap model,final HttpServletRequest req) throws Exception {

		List<?> menuList = (List<?>)req.getSession().getAttribute("menuList");
		UrlPathHelper urlPathHelper = new UrlPathHelper();
		String url = urlPathHelper.getOriginatingRequestUri(req);
		String urlPath = req.getContextPath();
		String uri = url.substring(urlPath.length());

		String pageTitle = null;
		String pageMnuPrt = null;
		String pagePrtTitle = null;
		String pagePrtUrl = null;

		// 현재 페이지 정보
		for (EgovMap map : (List<EgovMap>) menuList) {
			if (map.get("ummMnuPrt") != null && uri.equals(map.get("ummMnuUrl").toString())) {
				pageTitle = map.get("ummMnuNam").toString();
				pageMnuPrt = map.get("ummMnuPrt").toString();
				break;
			}
		}

		// _V, _RW, _R 등 처리
		if (pageTitle == null) {
			for (EgovMap map : (List<EgovMap>) menuList) {
				if (map.get("ummMnuPrt") != null && uri.substring(0, 18).equals(map.get("ummMnuUrl").toString().substring(0, 18))) {
					pageTitle = map.get("ummMnuNam").toString();
					pageMnuPrt = map.get("ummMnuPrt").toString();
					break;
				}
			}
		}

		// 부모 정보
		if (pageMnuPrt != null) {
			for (EgovMap map : (List<EgovMap>) menuList) {
				if (pageMnuPrt.equals(map.get("ummMnuKey").toString())) {
					pagePrtTitle = map.get("ummMnuNam").toString();
					pagePrtUrl = map.get("ummMnuUrl").toString();
					break;
				}
			}
		}

		//model.addAttribute("menuList", menuList);
		//model.addAttribute("requestUri", uri);
		model.addAttribute("pageTitle", pageTitle);
		model.addAttribute("pageMnuPrt", pageMnuPrt);
		model.addAttribute("pagePrtTitle", pagePrtTitle);
		model.addAttribute("pagePrtUrl", pagePrtUrl);

		return "/include/titlebar";
	}
}
