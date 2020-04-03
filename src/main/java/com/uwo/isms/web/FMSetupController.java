package com.uwo.isms.web;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uwo.isms.service.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import com.uwo.isms.domain.SearchVO;
import com.uwo.isms.domain.UserVO;
import com.uwo.isms.util.FileUpload;
import com.uwo.isms.util.PaginationUtil;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Controller
@RequestMapping("/setup")
public class FMSetupController {

	Logger log = LogManager.getLogger(FMSetupController.class);

	/** EgovSampleService */
	@Resource(name = "fmSetupService")
	private FMSetupService fmSetupService;

	@Resource(name = "fileUploadService")
	FileUploadService fileUploadService;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;

	@Resource(name = "commonCodeService")
	private CommonCodeService commonCodeService;

	@Resource(name = "sendMailService")
	SendMailService sendMailService;

	@Resource(name = "commonUtilService")
	private CommonUtilService commonUtilService;

	@Resource(name = "fmCompsService")
	private FMCompsService fmCompsService;

	@Autowired
	private DefaultBeanValidator beanValidator;

	@RequestMapping(value = "/FM-SETUP001.do")
	public String fmSetup001(@ModelAttribute("searchVO") SearchVO searchVO,
			ModelMap model,final HttpServletRequest req) throws Exception {

		searchVO.setSekAth((String)req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));

		int totCnt = fmSetupService.fm_setup001_cnt(searchVO);
        model.addAttribute("paginationInfo", new PaginationUtil(searchVO, totCnt).getPageData());
        model.addAttribute("resultList", fmSetupService.fm_setup001_list(searchVO));

		return "FM-SETUP001";
	}

	@RequestMapping(value = "FM-SETUP001_RW.do")
	public String FM_SETUP001_W(@ModelAttribute("vo") BoardVO vo, Model model, HttpServletRequest req)
			throws Exception {
		model.addAttribute("vo", new BoardVO());

		return "/FM-SETUP001-W";
	}

	@RequestMapping(value = "FM-SETUP001_W.do", method = RequestMethod.POST)
	public String FM_SETUP001_W(@ModelAttribute("vo") BoardVO vo,
			BindingResult bindingResult, Model model,
			final HttpServletRequest req) throws Exception {

		String uploadPath = propertyService.getString("boardUploadPath");
		req.setAttribute("uploadPath", uploadPath);

		FileUpload fu = new FileUpload();
		// parammeter : req, umf_tbl_gbn, umf_con_gbn
		List<FileVO> list = fu.fileuplaod(req, "BRD", "3");

		String division = (String) req.getSession().getAttribute(CommonConfig.SES_DIVISION_KEY);
		String key = (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY);
		vo.setUbm_rgt_id(key);
		vo.setUbm_div_cod(division);
		vo.setBcyCode(CommonConfig.SES_MAN_CYL_KEY);

		fmSetupService.fm_setup001_write(vo, list);

		if(vo.getUbm_ntc_yn() == null || vo.getUbm_ntc_yn().equals("")){
			vo.setUbm_ntc_yn("N");
		}

		if(vo.getUbm_ntc_yn().equals("Y")){
	        String userKey = "";
			String eventCode = EventTitleMessage.E08_CODE;
			String title = EventTitleMessage.E08;
			String contents = "";
			Map map = new HashMap();
			map.put("division", division);
			map.put("sekAuth", vo.getUbm_sek_ath());

			List uList = commonUtilService.getDivisionUser(map);
			for(int j=0; j<uList.size();j++){
				userKey = (String)uList.get(j);
				contents = title + "<p />"+vo.getUbm_brd_cts()+"<br /> ";
				sendMailService.sendMail(key, eventCode, title, contents, userKey);

			}
		}


		return "redirect:/setup/FM-SETUP001.do";
	}

	@RequestMapping("FM-SETUP001_V.do")
	public String FM_SETUP001_V(@RequestParam("selectedId") String id,
			Model model,final HttpServletRequest req) throws Exception {

		BoardVO vo = new BoardVO();
		vo.setUbm_brd_key(Integer.parseInt(id));
		FileVO fvo = new FileVO();
		fvo.setUmf_con_key(id);
		fvo.setUmf_tbl_gbn("BRD");
		fvo.setUmf_con_gbn("3");
		BoardVO rVo = fmSetupService.fm_setup001_read(vo);

		int b_auth = Integer.parseInt(rVo.getUbm_dwn_ath());
		String s_auth = (String)req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);

		model.addAttribute("vo", rVo);
		//보드권한에 따른 파일리스트 출력여부
		if((s_auth.equals("A") || (s_auth.equals("P")&& b_auth > 1)) || ((s_auth.equals("N")&&b_auth > 2))){
			model.addAttribute("file", fmSetupService.fm_file(fvo));
		}
		return "/FM-SETUP001-V";
	}

	@RequestMapping("/FM-SETUP007_member_popup.do")
	public String fmSetup007_member_popup (ModelMap model, HttpServletRequest req, HttpServletResponse res, @RequestParam Map<String, String> paramMap) throws Exception {
		System.out.println("123" + req.getParameter("utdTrcKey"));

        model.addAttribute("paramMap", paramMap);

        return "FM-SETUP007_member_popup";
	}

	//개인정보 대무자 검색
	@RequestMapping("/FM-SETUP007_member.do")
	public ModelAndView fmSetup007_member (@ModelAttribute("searchVO") SearchVO searchVO, HttpServletRequest req, HttpServletResponse res) throws Exception {
		ModelAndView mav = new ModelAndView();
		List <?> treeStr = fmSetupService.fm_setup007_member(searchVO);

        Map resultMap = new HashMap();

        res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");
        resultMap.put("result", treeStr);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");
        return mav;
	}

	@RequestMapping("FM-SETUP001_R.do")
	public String FM_SETUP001_R(@RequestParam("seq") String seq, Model model,final HttpServletRequest req)
			throws Exception {
		BoardVO vo = new BoardVO();
		vo.setUbm_brd_key(Integer.parseInt(seq));
		FileVO fvo = new FileVO();
		fvo.setUmf_con_key(seq);
		fvo.setUmf_tbl_gbn("BRD");
		fvo.setUmf_con_gbn("3");
		BoardVO rVo = fmSetupService.fm_setup001_read(vo);

		int b_auth = Integer.parseInt(rVo.getUbm_dwn_ath());
		String s_auth = (String)req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);

		model.addAttribute("vo", rVo);
		//보드권한에 따른 파일리스트 출력여부
		if((s_auth.equals("A") || (s_auth.equals("P")&& b_auth > 1)) || ((s_auth.equals("N")&&b_auth > 2))){
			model.addAttribute("file", fmSetupService.fm_file(fvo));
		}
		return "/FM-SETUP001-W";
	}

	@RequestMapping("FM-SETUP001_U.do")
	public String FM_SETUP001_U(BoardVO vo, BindingResult bindingResult,
			Model model,final HttpServletRequest req) throws Exception {

		String uploadPath = propertyService.getString("boardUploadPath");
		req.setAttribute("uploadPath", uploadPath);
		FileUpload fu = new FileUpload();
		// parammeter : req, umf_tbl_gbn, umf_con_gbn
		List<FileVO> list = fu.fileuplaod(req, "BRD", "3");

		vo.setUbm_rgt_id((String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		vo.setUbm_div_cod((String) req.getSession().getAttribute(CommonConfig.SES_DIVISION_KEY));
		vo.setBcyCode(CommonConfig.SES_MAN_CYL_KEY);

		fmSetupService.fm_setup001_update(vo, list);
		String page = "redirect:/setup/FM-SETUP001_R.do?seq="+vo.getUbm_brd_key();
		return page;
	}

	@RequestMapping("FM-SETUP001_D.do")
	public String FM_SETUP001_D(BoardVO vo, BindingResult bindingResult,
			Model model) throws Exception {
		fmSetupService.fm_setup001_delete(vo);
		return "redirect:/setup/FM-SETUP001.do";
	}

	@RequestMapping(value = "/FM-SETUP002.do")
	public String fmSetup002(@ModelAttribute("searchVO") SearchVO searchVO,
			ModelMap model,final HttpServletRequest req) throws Exception {

		searchVO.setSekAth((String)req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));

		int totCnt = fmSetupService.fm_setup002_cnt(searchVO);
        model.addAttribute("paginationInfo", new PaginationUtil(searchVO, totCnt).getPageData());
        model.addAttribute("resultList", fmSetupService.fm_setup002_list(searchVO));

		return "FM-SETUP002";
	}

	@RequestMapping(value = "FM-SETUP002_RW.do")
	public String FM_SETUP002_W(@ModelAttribute("vo") BoardVO vo, Model model, HttpServletRequest req)
			throws Exception {
		model.addAttribute("vo", new BoardVO());

		return "/FM-SETUP002-W";
	}

	@RequestMapping(value = "FM-SETUP002_W.do", method = RequestMethod.POST)
	public String FM_SETUP002_W(@ModelAttribute("vo") BoardVO vo,
			BindingResult bindingResult, Model model,
			final HttpServletRequest req) throws Exception {

		String uploadPath = propertyService.getString("boardUploadPath");
		req.setAttribute("uploadPath", uploadPath);

		FileUpload fu = new FileUpload();
		// parammeter : req, umf_tbl_gbn, umf_con_gbn
		List<FileVO> list = fu.fileuplaod(req, "BRD", "4");

		String division = (String) req.getSession().getAttribute(CommonConfig.SES_DIVISION_KEY);
		String key = (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY);
		vo.setUbm_rgt_id(key);
		vo.setUbm_div_cod(division);
		vo.setBcyCode(CommonConfig.SES_MAN_CYL_KEY);

		fmSetupService.fm_setup002_write(vo, list);

		if(vo.getUbm_ntc_yn() == null || vo.getUbm_ntc_yn().equals("")){
			vo.setUbm_ntc_yn("N");
		}

		if(vo.getUbm_ntc_yn().equals("Y")){
	        String userKey = "";
			String eventCode = EventTitleMessage.E08_CODE;
			String title = EventTitleMessage.E08;
			String contents = "";
			Map map = new HashMap();
			map.put("division", division);
			map.put("sekAuth", vo.getUbm_sek_ath());

			List uList = commonUtilService.getDivisionUser(map);
			for(int j=0; j<uList.size();j++){
				userKey = (String)uList.get(j);
				contents = title + "<p />"+vo.getUbm_brd_cts()+"<br /> ";
				sendMailService.sendMail(key, eventCode, title, contents, userKey);

			}
		}

		return "redirect:/setup/FM-SETUP002.do";
	}

	@RequestMapping("FM-SETUP002_V.do")
	public String FM_SETUP002_V(@RequestParam("selectedId") String id,
			Model model,final HttpServletRequest req) throws Exception {

		BoardVO vo = new BoardVO();
		vo.setUbm_brd_key(Integer.parseInt(id));
		FileVO fvo = new FileVO();
		fvo.setUmf_con_key(id);
		fvo.setUmf_tbl_gbn("BRD");
		fvo.setUmf_con_gbn("4");
		BoardVO rVo = fmSetupService.fm_setup002_read(vo);

		int b_auth = Integer.parseInt(rVo.getUbm_dwn_ath());
		String s_auth = (String)req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);

		model.addAttribute("vo", rVo);
		//보드권한에 따른 파일리스트 출력여부
		if((s_auth.equals("A") || (s_auth.equals("P")&& b_auth > 1)) || ((s_auth.equals("N")&&b_auth > 2))){
			model.addAttribute("file", fmSetupService.fm_file(fvo));
		}
		return "/FM-SETUP002-V";
	}

	@RequestMapping("FM-SETUP002_R.do")
	public String FM_SETUP002_R(@RequestParam("seq") String seq, Model model,final HttpServletRequest req)
			throws Exception {
		BoardVO vo = new BoardVO();
		vo.setUbm_brd_key(Integer.parseInt(seq));
		FileVO fvo = new FileVO();
		fvo.setUmf_con_key(seq);
		fvo.setUmf_tbl_gbn("BRD");
		fvo.setUmf_con_gbn("4");
		BoardVO rVo = fmSetupService.fm_setup002_read(vo);

		int b_auth = Integer.parseInt(rVo.getUbm_dwn_ath());
		String s_auth = (String)req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);

		model.addAttribute("vo", rVo);
		//보드권한에 따른 파일리스트 출력여부
		if((s_auth.equals("A") || (s_auth.equals("P")&& b_auth > 1)) || ((s_auth.equals("N")&&b_auth > 2))){
			model.addAttribute("file", fmSetupService.fm_file(fvo));
		}
		return "/FM-SETUP002-W";
	}

	@RequestMapping("FM-SETUP002_U.do")
	public String FM_SETUP002_U(BoardVO vo, BindingResult bindingResult,
			Model model,final HttpServletRequest req) throws Exception {

		String uploadPath = propertyService.getString("boardUploadPath");
		req.setAttribute("uploadPath", uploadPath);
		FileUpload fu = new FileUpload();
		// parammeter : req, umf_tbl_gbn, umf_con_gbn
		List<FileVO> list = fu.fileuplaod(req, "BRD", "4");

		vo.setUbm_rgt_id((String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		vo.setUbm_div_cod((String) req.getSession().getAttribute(CommonConfig.SES_DIVISION_KEY));
		vo.setBcyCode(CommonConfig.SES_MAN_CYL_KEY);

		fmSetupService.fm_setup002_update(vo, list);
		String page = "redirect:/setup/FM-SETUP002_R.do?seq="+vo.getUbm_brd_key();
		return page;
	}

	@RequestMapping("FM-SETUP002_D.do")
	public String FM_SETUP002_D(BoardVO vo, BindingResult bindingResult,
			Model model) throws Exception {
		fmSetupService.fm_setup002_delete(vo);
		return "redirect:/setup/FM-SETUP002.do";
	}

	@RequestMapping(value = "/FM-SETUP007.do")
	public String fmSetup007(@ModelAttribute("searchVO") SearchVO searchVO,
			HttpServletRequest req, ModelMap model) throws Exception {

		int totCnt = fmSetupService.fm_setup007_cnt(searchVO);
        model.addAttribute("paginationInfo", new PaginationUtil(searchVO, totCnt).getPageData());
        model.addAttribute("resultList", fmSetupService.fm_setup007(searchVO));
        model.addAttribute("auhList",commonCodeService.getAuhList());
        model.addAttribute("searchVO",searchVO);

		return "FM-SETUP007";
	}

	@RequestMapping("/FM-SETUP007_popup.do")
	public String fmSetup007_popup (@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

        String usrKey = "";
        String menu = req.getParameter("menu");

        if(menu == null || menu.equals("") || menu.equals("null")){
        	usrKey = (String)req.getSession().getAttribute(CommonConfig.SES_USER_KEY);
        }else{
        	usrKey = req.getParameter("usrKey");
        }
        String mode = req.getParameter("mode");
        String page = req.getParameter("page");
        if(mode == null || mode.equals("") || mode.equals("null")){
        	mode = "0";
        }
        EgovMap userMap = new EgovMap();
        List<?> posList = new ArrayList();
        List<?> auhList = new ArrayList();
        if(mode.equals("0")){
        	userMap = fmSetupService.fm_setup007_popup(usrKey);
        }
        searchVO.setAuth("A");
    	searchVO.setSqlUrl("commonCode.QR_POSITION_LIST");
    	posList = commonCodeService.getSelectBoxCode(searchVO);
    	auhList = commonCodeService.getAuhList();

    	Map param = new HashMap();
		param.put("usr_dept", (String)req.getSession().getAttribute(CommonConfig.SES_DEPT_KEY));
		param.put("myId", (String)req.getSession().getAttribute(CommonConfig.SES_USER_KEY));

		//List apvList = fmSetupService.fm_setup013_dept(param);

		//model.addAttribute("apvList",apvList);
        model.addAttribute("mode", mode);
        model.addAttribute("page", page);
        model.addAttribute("user", userMap);
        model.addAttribute("posList",posList);
        model.addAttribute("auhList",auhList);
        //model.addAttribute("svcList", commonCodeService.getCommonCodeList(Constants.SVC_COD));
        model.addAttribute("svcList", commonCodeService.getSvcCode(Constants.SVC_COD));
        model.addAttribute("depList", commonCodeService.getCommonCodeList(Constants.DEP_COD));

        if (model.get("updatePage") == null) {
        	model.addAttribute("updatePage", "/setup/FM-SETUP007_U.do");
        }

        return "FM-SETUP007_popup";
	}

	@RequestMapping(value = "FM-SETUP007_W.do")
	public ModelAndView FM_SETUP007_W(@ModelAttribute("vo") UserVO vo,
			BindingResult bindingResult, Model model,
			final HttpServletRequest req, HttpServletResponse res) throws Exception {

		vo.setUumDivCod(Constants.DIV_COD);
		fmSetupService.fm_setup007_insert(vo);

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		ModelAndView mav = new ModelAndView();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", "S");
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;

	}


	@RequestMapping("FM-SETUP007_U.do")
	public ModelAndView FM_SETUP007_U(@ModelAttribute("vo") UserVO vo, BindingResult bindingResult,
			Model model,final HttpServletRequest req, HttpServletResponse res) throws Exception {

		vo.setUumDivCod(Constants.DIV_COD);
		vo.setUumRgtId((String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		fmSetupService.fm_setup007_edit(vo);

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		ModelAndView mav = new ModelAndView();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", "S");
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping("/FM-SETUP007_FAIL_CLEAN.do")
	public ModelAndView fmSetup007_fail_clean (HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

        String key = req.getParameter("key");
        fmSetupService.fmSetup007_fail_clean(key);

        res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("SUC", "SUC");
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping("/FM-SETUP007_DUPLICATE_TEST.do")
	public ModelAndView fmSetup007_duplicate_test (HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

        String uId = req.getParameter("uId");
        int cnt = fmSetupService.fmSetup007_duplicate_test(uId);

        res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("SUC", "SUC");
        resultMap.put("cnt", cnt);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping(value = "/FM-SETUP007_pwd_popup.do")
	public String fmSetup007_pwd_popup(HttpServletRequest req, ModelMap model) throws Exception {

		return "FM-SETUP007_pwd_popup";
	}

	@RequestMapping("/FM-SETUP007_PW.do")
	public ModelAndView fmSetup007_PW (@RequestParam Map<String, String> map,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();


        res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

        Map<String, Object> resultMap = new HashMap<String, Object>();

        map.put("usrKey", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
        resultMap.put("result", fmSetupService.fmSetup007_pwd_update(map));
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping(value = "/FM-SETUP008.do")
	public String fmSetup008(@ModelAttribute("searchVO") SearchVO searchVO,
			HttpServletRequest req, ModelMap model) throws Exception {

		searchVO.setManCyl(req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY).toString());

		int totCnt = fmSetupService.fm_setup008_cnt(searchVO);
        model.addAttribute("paginationInfo", new PaginationUtil(searchVO, totCnt).getPageData());
        model.addAttribute("codeList", fmSetupService.fm_setup008_list(searchVO));

		return "FM-SETUP008";
	}

	@RequestMapping("/FM-SETUP008_popup.do")
	public String fmSetup008_popup (@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

        String codeKey = req.getParameter("codeKey");
        String company = propertyService.getString("company");

        searchVO.setCode(codeKey);
        searchVO.setCompany(company);

        String mode = "update";
        if(codeKey == ""){
        	mode = "add";
        }
        else{

        	EgovMap codeInfo = fmSetupService.fm_setup008_codeInfo(searchVO);
        	model.addAttribute("codeInfo", codeInfo);
        }

        model.addAttribute("codeKey", codeKey);
        model.addAttribute("mode", mode);

        return "FM-SETUP008_popup";
	}

	@RequestMapping("/FM-SETUP008_codeInfo_save.do")
	public ModelAndView fmSetup006_codeInfo_save (HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();
		int result = 1;

        String bcyCod = req.getParameter("bcyCod").toString();
	    String code = req.getParameter("code").toString();
	    String codeNam = req.getParameter("codeNam").toString();
	    String useYn = req.getParameter("useYn").toString();
	    String codeEtc = req.getParameter("codeEtc").toString();
	    String mode = req.getParameter("mode").toString();
	    String rgtId = req.getSession().getAttribute(CommonConfig.SES_USER_KEY).toString();

	    EgovMap map = new EgovMap();
	    map.put("codeNam", codeNam);
	    map.put("useYn", useYn);
	    map.put("codeEtc", codeEtc);
	    map.put("rgtId", rgtId);

	    if(mode.equals("add")){
	    	map.put("bcyCod", bcyCod);
	    	fmSetupService.fm_setup008_codeInfo_insert(map);
	    }else{
	    	map.put("bcyCod", bcyCod);
	    	map.put("code", code);
	    	fmSetupService.fm_setup008_codeInfo_update(map);
	    }
	    res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

	    Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", result);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}


	@RequestMapping(value = "/FM-SETUP010.do")
	public String fmSetup010(@ModelAttribute("searchVO") SearchVO searchVO,
			HttpServletRequest req, ModelMap model) throws Exception {

		int totCnt = fmSetupService.getloginlist_count(searchVO);
        model.addAttribute("paginationInfo", new PaginationUtil(searchVO, totCnt).getPageData());
        model.addAttribute("resultList", fmSetupService.getloginlist(searchVO));

		return "FM-SETUP010";
	}

	@RequestMapping(value="/FM-SETUP010_getLoginList.do")
	public ModelAndView fmSetup010_getLoginList (@ModelAttribute("SearchVO") SearchVO searchVO, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		searchVO.setManCyl((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		searchVO.setWorker(req.getParameter("worker"));

		List<?> resultList = fmSetupService.getloginlist(searchVO);
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
		resultMap.put("resultList", resultList);
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping(value = "/FM-SETUP011.do")
	public String fmSetup011(@ModelAttribute("searchVO") SearchVO searchVO,
			HttpServletRequest req, ModelMap model) throws Exception {

        int totCnt = fmSetupService.getaccountlist_count(searchVO);
        model.addAttribute("paginationInfo", new PaginationUtil(searchVO, totCnt).getPageData());
        model.addAttribute("resultList", fmSetupService.getaccountlist(searchVO));

		return "FM-SETUP011";
	}


	@RequestMapping(value="/FM-SETUP011_getAccountList.do")
	public ModelAndView fmSetup011_getAccountList (@ModelAttribute("SearchVO") SearchVO searchVO, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		searchVO.setManCyl((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

		List<?> resultList = fmSetupService.getaccountlist(searchVO);
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
		resultMap.put("resultList", resultList);
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping(value = "/FM-SETUP012.do")
	public String BoardList12(ModelMap model,HttpServletRequest req) throws Exception {

		 java.util.Calendar cal = java.util.Calendar.getInstance();

			String sYear = req.getParameter("year");
			String sMonth = req.getParameter("month");

			int iYear = cal.get(java.util.Calendar.YEAR);
			int iMonth = cal.get(java.util.Calendar.MONTH);
			int iDate = cal.get(java.util.Calendar.DATE);

			String sSearchDate = "";
	        if(sYear == null || sMonth == null){
	                sSearchDate += Integer.toString(iYear);
	                sSearchDate += Integer.toString(iMonth+1).length() == 1 ? "0" + Integer.toString(iMonth+1) : Integer.toString(iMonth+1);
	        }else{
	                iYear = Integer.parseInt(sYear);
	                iMonth = Integer.parseInt(sMonth);
	                sSearchDate += sYear;
	                sSearchDate += Integer.toString(iMonth+1).length() == 1 ? "0" + Integer.toString(iMonth+1) :Integer.toString(iMonth+1);
	        }

	        Map map = new HashMap();

	        String param1 = "";
	        String param2 = "";

	        param1 += Integer.toString(iYear)+"-";
	        param1 += Integer.toString(iMonth+1).length() == 1 ? "0" + Integer.toString(iMonth+1) :Integer.toString(iMonth+1);
	        param1 += "-01";

	        param2 += Integer.toString(iYear);
	        param2 += Integer.toString(iMonth+1).length() == 1 ? "0" + Integer.toString(iMonth+1) :Integer.toString(iMonth+1);
	        param2 += "01";


	        map.put("param2", param1);
	        map.put("param1", param2);

	        List<?> resultList = fmSetupService.getYmdList(map);
	    	model.addAttribute("resultList", resultList);

			return "FM-SETUP012";
	}

	@RequestMapping(value = "/FM-SETUP012_CONstndM.do")
	public String FM_SETUP012_CONstndM(ModelMap model,HttpServletRequest req) throws Exception{

		String year = req.getParameter("strYear");
		String month = req.getParameter("strMonth");
		String day = req.getParameter("day");
		String yn = req.getParameter("yn");
		String etc = req.getParameter("etc");
		if(month.length() == 1){
			month = "0"+month;
		}


		String uwyWrkYmd = year+month+day;

		Map map = new HashMap();
		map.put("uwyWrkYmd", uwyWrkYmd);
		map.put("yn", yn);
		map.put("etc", etc);

		fmSetupService.updateYmd(map);

		return "redirect:/setup/FM-SETUP012.do";
	}

	@RequestMapping(value = "/FM-SETUP013.do")
	public String FM_SETUP013(Model model,final HttpServletRequest req) throws Exception {

		//UserVO vo = new UserVO();
		EgovMap map = new EgovMap();
		String usr_key = (String)req.getSession().getAttribute(CommonConfig.SES_USER_KEY);
		String usr_dept = (String)req.getSession().getAttribute(CommonConfig.SES_DEPT_KEY);
		String myId = (String)req.getSession().getAttribute(CommonConfig.SES_USER_KEY);
		Map param = new HashMap();
		param.put("usr_dept", usr_dept);
		param.put("myId", myId);
		map = fmSetupService.fm_setup013_read(usr_key);
		List apvList = fmSetupService.fm_setup013_dept(param);
		model.addAttribute("vo", map);
		model.addAttribute("apvList",apvList);

		return "/FM-SETUP013";
	}

	@RequestMapping("FM-SETUP013_U.do")
	public String FM_SETUP013_U(UserVO vo, BindingResult bindingResult,
			Model model,final HttpServletRequest req) throws Exception {

		fmSetupService.fm_setup013_update(vo);
		return "redirect:/FM-MAIN.do";
	}

	@RequestMapping(value = "/FM-SETUP014.do")
	public String fmSetup014(@ModelAttribute("searchVO") SearchVO searchVO,
			HttpServletRequest req, ModelMap model) throws Exception {

		return "FM-SETUP014";
	}



	@RequestMapping(value = "/FM-SETUP016.do")
	public String fmSetup016(@ModelAttribute("searchVO") SearchVO searchVO,
			HttpServletRequest req, ModelMap model) throws Exception {

		model.addAttribute("searchVO", searchVO);

		int totCnt = fmSetupService.fm_setup016_mnn_cnt(searchVO);
        model.addAttribute("paginationInfo", new PaginationUtil(searchVO, totCnt).getPageData());
        model.addAttribute("list", fmSetupService.fm_setup016_mnu_list(searchVO));
        model.addAttribute("totCnt", totCnt);

		return "FM-SETUP016";
	}

	@RequestMapping(value = "/FM-SETUP016_N.do")
	public String fmSetup016_n(HttpServletRequest req, HttpServletResponse res,
			@RequestParam Map<String, String> paramMap, ModelMap model) throws Exception {

		model.addAttribute("list", fmSetupService.fm_setup016_node_list(paramMap.get("ummMnuPrt")));
		model.addAttribute("paramMap", paramMap);
		return "FM-SETUP016-N";
	}

	@RequestMapping(value = "/FM-SETUP016_RW.do")
	public String fmSetup016_rw(HttpServletRequest req, HttpServletResponse res,
			@RequestParam Map<String, String> paramMap, ModelMap model) throws Exception {

		if(StringUtils.isNotEmpty(paramMap.get("ummMnuKey"))) {
			model.addAttribute("mnuInfo", fmSetupService.fm_setup016_mnu_info(paramMap.get("ummMnuKey")));
		}
		model.addAttribute("paramMap", paramMap);
		return "FM-SETUP016-W";
	}

	@RequestMapping(value = "/FM-SETUP016_W.do")
	public ModelAndView fmSetup016_w(HttpServletRequest req, HttpServletResponse res,
			@RequestParam Map<String, Object> map, ModelMap model) throws Exception {
		map.put("ummRgtId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		ModelAndView mav = new ModelAndView();
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		fmSetupService.fm_setup016_mnu_insert(map);
		mav.setViewName("jsonView");
		return mav;
	}

	@RequestMapping(value = "/FM-SETUP016_U.do")
	public ModelAndView fmSetup016_u(HttpServletRequest req, HttpServletResponse res,
			@RequestParam Map<String, Object> map, ModelMap model) throws Exception {
		map.put("ummRgtId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		ModelAndView mav = new ModelAndView();
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		fmSetupService.fm_setup016_mnu_update(map);
		mav.setViewName("jsonView");
		return mav;
	}

	@RequestMapping(value = "/FM-SETUP017.do")
	public String fmSetup017(@ModelAttribute("searchVO") SearchVO searchVO,
			HttpServletRequest req, ModelMap model) throws Exception {

		model.addAttribute("searchVO", searchVO);

		//int totCnt = fmSetupService.fm_setup016_mnn_cnt(searchVO);
		int totCnt = fmSetupService.fm_setup017_auh_cnt(searchVO);
        model.addAttribute("paginationInfo", new PaginationUtil(searchVO, totCnt).getPageData());
        model.addAttribute("list", fmSetupService.fm_setup017_auh_list(searchVO));
        model.addAttribute("totCnt", totCnt);

		return "FM-SETUP017";
	}

	@RequestMapping(value = "/FM-SETUP017_RW.do")
	public String fmSetup017_rw(HttpServletRequest req, HttpServletResponse res,
			@RequestParam Map<String, String> paramMap, ModelMap model) throws Exception {

		if(StringUtils.isNotEmpty(paramMap.get("uatAuhKey"))) {
			model.addAttribute("auhInfo", fmSetupService.fm_setup017_auh_info(paramMap.get("uatAuhKey")));
			model.addAttribute("mapList", fmSetupService.fm_setup017_map_list(paramMap.get("uatAuhKey")));
			model.addAttribute("mapNodeList", fmSetupService.fm_setup017_node_list(paramMap.get("uatAuhKey")));
		}

		model.addAttribute("auhGbnList", commonCodeService.getCommonCodeList(Constants.AUH_COD));
		model.addAttribute("paramMap", paramMap);
		return "FM-SETUP017-W";
	}

	@RequestMapping(value = "/FM-SETUP017_W.do")
	public ModelAndView fmSetup017_w(HttpServletRequest req, HttpServletResponse res,
			@RequestParam Map<String, Object> map, ModelMap model) throws Exception {
		map.put("uatRgtId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		ModelAndView mav = new ModelAndView();
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		mav.addObject("uatAuhKey", fmSetupService.fm_setup017_auh_insert(map));
		mav.setViewName("jsonView");
		return mav;
	}

	@RequestMapping(value = "/FM-SETUP017_U.do")
	public ModelAndView fmSetup017_u(HttpServletRequest req, HttpServletResponse res,
			@RequestParam Map<String, Object> map, ModelMap model) throws Exception {
		map.put("uatRgtId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		map.put("uamMnuKey", req.getParameterValues("uamMnuKey"));
		map.put("uamMnuRgt", req.getParameterValues("uamMnuRgt"));

		ModelAndView mav = new ModelAndView();
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");
		fmSetupService.fm_setup017_auh_update(map);
		mav.setViewName("jsonView");
		return mav;
	}

	@RequestMapping(value="/FM-SETUP014_getControlList.do")
	public ModelAndView fmSetup014_getControlList (@ModelAttribute("SearchVO") SearchVO searchVO, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		searchVO.setManCyl((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

		List<?> resultList = fmSetupService.getcontrollist(searchVO);
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
		resultMap.put("resultList", resultList);
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping("/testAjax.do")
	public ModelAndView testAjax(@RequestParam String id) throws Exception {
		// String id = "jangseok.kim";
		List<?> list = fmSetupService.testAjax(id);

		for (int i = 0; i < list.size(); i++) {

			System.out.println(list.get(i));
		}
		ModelAndView mav = new ModelAndView();

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("list", list);
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;

	}

	@RequestMapping("/policyList.do")
	public ModelAndView policyList() {

		ModelAndView mav = new ModelAndView();

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("list", "user1");
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");
		return mav;
	}

	@RequestMapping("/testGrid.do")
	public ModelAndView testGrid(ModelMap model, HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		List<?> list = fmSetupService.testGrid();
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");
		ModelAndView mav = new ModelAndView();

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", list);
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}


	@RequestMapping("/FM-SETUP007_list.do")
	public ModelAndView fmSetup007_list(@ModelAttribute("searchVO") SearchVO vo,
			ModelMap model, HttpServletRequest req, HttpServletResponse res)
			throws Exception {

		ModelAndView mav = new ModelAndView();

		List<?> list = fmSetupService.fm_setup007(vo);
		for (int i = 0; i < list.size(); i++) {
		}
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", list);
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping("/FM-SETUP007_edit.do")
	public ModelAndView fmSetup007_edit(@ModelAttribute("UserVo") UserVO vo,@ModelAttribute("searchVO") SearchVO svo,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		String oper = req.getParameter("oper");

		vo.setUumRgtId((String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		if (oper.equals("edit")) {
			fmSetupService.fm_setup007_edit(vo);
		} else if (oper.equals("add")) {
			fmSetupService.fm_setup007_insert(vo);
		} else if (oper.equals("del")) {
			String id = req.getParameter("id");
			fmSetupService.fm_setup007_del(id);
		}
		List<?> list = fmSetupService.fm_setup007(svo);
		for (int i = 0; i < list.size(); i++) {
			// System.out.println(list.get(i));
		}
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", list);
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping(value = "/FM-SETUP018.do")
	public String fmSetup018(@ModelAttribute("searchVO") SearchVO searchVO,
			HttpServletRequest req, ModelMap model) throws Exception {

		searchVO.setStndNam(req.getParameter("stndNam"));

		int totCnt = fmSetupService.fm_setup018_cnt(searchVO);
        model.addAttribute("paginationInfo", new PaginationUtil(searchVO, totCnt).getPageData());
        model.addAttribute("list", fmSetupService.fm_setup018_list(searchVO));
        model.addAttribute("totCnt", totCnt);

		return "FM-SETUP018";
	}


	@RequestMapping(value = "/FM-SETUP018_N.do")
	public String fmSetup018_n(HttpServletRequest req, HttpServletResponse res,
			@RequestParam Map<String, String> paramMap, ModelMap model) throws Exception {

		model.addAttribute("paramMap", paramMap);
		if(paramMap.get("uccFirCod").equals("STND")){
			model.addAttribute("list", fmSetupService.fm_setup018_node_list_sort(paramMap));
			return "FM-SETUP018-N_STND";
		}else{
			model.addAttribute("list", fmSetupService.fm_setup018_node_list(paramMap.get("uccFirCod")));
			return "FM-SETUP018-N";
		}
	}


	@RequestMapping(value = "/FM-SETUP018_RW.do")
	public String fmSetup018_rw(HttpServletRequest req, HttpServletResponse res,
			ModelMap model, @RequestParam Map<String, String> paramMap) throws Exception {

		String uccSndCod =  paramMap.get("uccSndCod");
		if(StringUtils.isNotEmpty(uccSndCod)) {
			EgovMap info = fmSetupService.fm_setup018_info(paramMap);
			model.addAttribute("info", info);
		}
		model.addAttribute("paramMap", paramMap);
		model.addAttribute("listComCodPrefix", fmCompsService.getComCodPrefix());
		if(paramMap.get("uccFirCod").equals("STND")){
			return "FM-SETUP018-W_STND";
		}
		return "FM-SETUP018-W";
	}

	@RequestMapping(value = "/FM-SETUP018_W.do")
	public ModelAndView fmSetup018_w(HttpServletRequest req, HttpServletResponse res,
			@RequestParam Map<String, Object> map, ModelMap model) throws Exception {
		map.put("uccRgtId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		String strResult = fmSetupService.fm_setup018_insert(map);

		ModelAndView mav = new ModelAndView();
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", strResult);
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");
		return mav;
	}

	@RequestMapping(value = "/FM-SETUP018_U.do")
	public ModelAndView fmSetup018_u(HttpServletRequest req, HttpServletResponse res,
			@RequestParam Map<String, Object> map, ModelMap model) throws Exception {
		map.put("uccRgtId",(String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		String strResult = fmSetupService.fm_setup018_update(map);

		ModelAndView mav = new ModelAndView();
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", strResult);
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");
		return mav;
	}

	@RequestMapping(value = "/FM-SETUP019.do")
	public String fmSetup019(@ModelAttribute("searchVO") SearchVO searchVO,
			HttpServletRequest req, ModelMap model) throws Exception {

		java.util.Calendar cal = java.util.Calendar.getInstance();

		String sYear = req.getParameter("sYear");
		String sMonth = req.getParameter("sMonth");

		int iYear = cal.get(java.util.Calendar.YEAR);
		int iMonth = cal.get(java.util.Calendar.MONTH)+1;
		int iDate = cal.get(java.util.Calendar.DATE);

		if(sYear == null || sMonth == null || sYear == "" || sMonth == ""){
    		sYear = Integer.toString(iYear);
        }else{
            iYear = Integer.parseInt(sYear);
            iMonth = Integer.parseInt(sMonth);
        }

        sMonth = Integer.toString(iMonth).length() == 1 ? "0" + Integer.toString(iMonth) : Integer.toString(iMonth);

        String dMonth = new DateFormatSymbols(Locale.ENGLISH).getMonths()[iMonth-1];

        Map map = new HashMap();

        map.put("iMonth", iMonth);
        map.put("iYear", iYear);

		List<?> list = fmSetupService.fm_setup019_cal(map);
		model.addAttribute("resultList", list);

		List<?> list_Y = fmSetupService.fm_setup019_Year();
		model.addAttribute("yearList", list_Y);

        model.addAttribute("dMonth", dMonth.toUpperCase());
        model.addAttribute("sMonth", sMonth);
        model.addAttribute("sYear", sYear);

		return "FM-SETUP019";
	}

	@RequestMapping(value = "/FM-SETUP019_SAVE_YMD.do")
	public ModelAndView fmSetup019_sav_ymd(HttpServletRequest req, HttpServletResponse res,
			ModelMap model) throws Exception {

		Map map = new HashMap();

		String key = req.getParameter("key");
		String etc = req.getParameter("etc");

		map.put("key", key);
		map.put("etc", etc);

		fmSetupService.fm_setup019_sav_ymd(map);

		ModelAndView mav = new ModelAndView();
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", "suc");
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");
		return mav;
	}

	@RequestMapping(value = "/FM-SETUP019_RE_YMD.do")
	public ModelAndView fmSetup019_re_ymd(HttpServletRequest req, HttpServletResponse res,
			ModelMap model) throws Exception {

		Map map = new HashMap();

		String key = req.getParameter("key");

		map.put("key", key);

		fmSetupService.fm_setup019_re_ymd(map);

		ModelAndView mav = new ModelAndView();
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", "suc");
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");
		return mav;
	}

	@RequestMapping(value = "/FM-SETUP020.do")
	public String fmSetup020(@ModelAttribute("searchVO") SearchVO searchVO,
			HttpServletRequest req, ModelMap model) throws Exception {

		model.addAttribute("list", fmSetupService.fm_setup020_list(searchVO));
		model.addAttribute("searchVO",searchVO);

		return "FM-SETUP020";
	}

	@RequestMapping(value = "/FM-SETUP020_RW.do")
	public String fmSetup020_rw(HttpServletRequest req, HttpServletResponse res,
			@RequestParam Map<String, Object> paramMap, ModelMap model) throws Exception {

		model.addAttribute("nonCycle", commonCodeService.getManCycleSelectBoxCode((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY)));
		model.addAttribute("serviceList", fmSetupService.fm_setup018_node_list("SERVICE"));

		if(paramMap.get("uvmSvcKey").toString() != ""){

			FileVO fvo = new FileVO();
			fvo.setUmf_con_key(paramMap.get("uvmSvcKey").toString());
			fvo.setUmf_tbl_gbn("SVC");
			fvo.setUmf_con_gbn("9");

			model.addAttribute("fileAll", fmSetupService.fm_file(fvo));

			fvo.setUmf_con_key(paramMap.get("uvmSvcKey").toString());
			fvo.setUmf_tbl_gbn("SVC");
			fvo.setUmf_con_gbn("10");

			model.addAttribute("fileInf", fmSetupService.fm_file(fvo));

			model.addAttribute("list", fmSetupService.fm_setup020_view(paramMap));
		}
		model.addAttribute("paramMap", paramMap);

		return "FM-SETUP020-W";
	}

	@RequestMapping(value = "/FM-SETUP020_N.do")
	public String fmSetup020_n(HttpServletRequest req, HttpServletResponse res,
			@RequestParam Map<String, Object> paramMap, ModelMap model) throws Exception {

		FileVO fvo = new FileVO();
		fvo.setUmf_con_key(paramMap.get("uvmSvcKey").toString());
		fvo.setUmf_tbl_gbn("SVC");
		fvo.setUmf_con_gbn("9");

		model.addAttribute("fileAll", fmSetupService.fm_file(fvo));

		fvo.setUmf_con_key(paramMap.get("uvmSvcKey").toString());
		fvo.setUmf_tbl_gbn("SVC");
		fvo.setUmf_con_gbn("10");

		model.addAttribute("fileInf", fmSetupService.fm_file(fvo));

		model.addAttribute("list", fmSetupService.fm_setup020_view(paramMap));
		model.addAttribute("paramMap", paramMap);

		return "FM-SETUP020-V";
	}

	@RequestMapping(value = "/FM-SETUP020_W.do")
	public String fmSetup020_w(HttpServletRequest req, HttpServletResponse res,
			@RequestParam Map<String, Object> map, ModelMap model) throws Exception {

		String uploadPath = propertyService.getString("serviceUploadPath");
		req.setAttribute("uploadPath", uploadPath);
		req.setAttribute("uvmAllOrg", map.get("uvmAllOrg"));
		req.setAttribute("uvmInfOrg", map.get("uvmInfOrg"));

		FileUpload fu = new FileUpload();

		List<FileVO> list = fu.fileuplaod_svc(req, Constants.FILE_GBN_SVC, Constants.FILE_CON_ALL, Constants.FILE_CON_INF);

		map.put("uvmRgtId",(String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));

		fmSetupService.fm_setup020_insert(map, list);

		return "redirect:/setup/FM-SETUP020.do";
	}

	@RequestMapping(value = "/FM-SETUP020_U.do")
	public String fmSetup020_u(HttpServletRequest req, HttpServletResponse res,
			@RequestParam Map<String, Object> map, ModelMap model) throws Exception {

		String uploadPath = propertyService.getString("serviceUploadPath");
		req.setAttribute("uploadPath", uploadPath);
		req.setAttribute("uvmAllOrg", map.get("uvmAllOrg"));
		req.setAttribute("uvmInfOrg", map.get("uvmInfOrg"));

		FileUpload fu = new FileUpload();
		List<FileVO> list = fu.fileuplaod_svc(req, Constants.FILE_GBN_SVC, Constants.FILE_CON_ALL, Constants.FILE_CON_INF);

		map.put("uvmRgtId",(String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));

		fmSetupService.fm_setup020_update(map, list);

		return "redirect:/setup/FM-SETUP020_N.do?uvmSvcKey=" + map.get("uvmSvcKey");
	}

	/*
	 * 2016-10-15 보호활동 설정으로 이동
	 * FM-COMP006.do
	 *
	@RequestMapping(value = "/FM-SETUP021.do")
	public String fmSetup021(@ModelAttribute("searchVO") SearchVO searchVO,
			HttpServletRequest req, ModelMap model) throws Exception {

		//model.addAttribute("serviceList", fmSetupService.fm_setup018_node_list("SERVICE"));
		model.addAttribute("resultList", fmSetupService.fm_setup021_list(searchVO));
		model.addAttribute("searchVO", searchVO);

		return "FM-SETUP021";
	}

	@RequestMapping(value = "/FM-SETUP021_RW.do")
	public String fmSetup021_rw(HttpServletRequest req, HttpServletResponse res,
			@RequestParam Map<String, String> paramMap, ModelMap model) throws Exception {

		EgovMap info = fmSetupService.fm_setup021_info(paramMap);
		model.addAttribute("info", info);

		paramMap.put("code", "DEPT");
		//model.addAttribute("mapList", fmSetupService.fm_setup021_dept_list(paramMap));
		model.addAttribute("mapNodeList", fmSetupService.fm_setup021_node_list());
		model.addAttribute("paramMap", paramMap);

		return "FM-SETUP021-W";
	}

	@RequestMapping(value = "/FM-SETUP021_U.do")
	public ModelAndView fmSetup021_u(HttpServletRequest req, HttpServletResponse res,
		@RequestParam Map<String, Object> map, ModelMap model) throws Exception {
		map.put("uccRgtId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		map.put("uomDepCod", req.getParameterValues("uomDepCod"));

		ModelAndView mav = new ModelAndView();
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		String strResult = fmSetupService.fm_setup018_update(map);

		if(strResult.equals("S")){
			fmSetupService.fm_setup021_map_update(map);
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", strResult);
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");
		return mav;
	} */

	// cego
	@RequestMapping(value = "/FM-SETUP099.do")
	public String fmSetup099(@ModelAttribute("searchVO") SearchVO searchVO,
			HttpServletRequest req, ModelMap model) throws Exception {

		return "FM-SETUP099";
	}

	/**
	 * 2018-05-10, 사용자 계정 엑셀 업로드
	 */
	@RequestMapping(value="/FM-SETUP007_excel_popup.do")
	public String fmSetup007_excel_popup (@RequestParam Map<String, String> map,
			HttpServletRequest req, ModelMap model) throws Exception {

		return "FM-SETUP007_excel_popup";
	}

	@RequestMapping("/FM-SETUP007_excelSave.do")
	public ModelAndView fmSetup007_excelSave(HttpServletRequest req, HttpServletResponse res) throws Exception {
		ModelAndView mav = new ModelAndView();
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", fmSetupService.fm_setup007_excel_insert(req));
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");
        return mav;
	}


}
