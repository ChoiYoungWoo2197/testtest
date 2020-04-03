package com.uwo.isms.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uwo.isms.dao.ExcelNewDAO;
import com.uwo.isms.service.*;
import com.uwo.isms.util.*;
import net.sf.json.JSON;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.uwo.isms.common.CommonConfig;
import com.uwo.isms.common.Constants;
import com.uwo.isms.domain.BoardVO;
import com.uwo.isms.domain.CycleVO;
import com.uwo.isms.domain.FileVO;
import com.uwo.isms.domain.SampleDocVO;
import com.uwo.isms.domain.SearchVO;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
@RequestMapping("/comps")
public class FMCompsController {

	Logger log = LogManager.getLogger(FMCompsController.class);

	@Resource(name = "fmCompsService")
	private FMCompsService fmCompsService;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;

	@Resource(name = "commonCodeService")
	private CommonCodeService commonCodeService;

	@Resource(name = "fileUploadService")
	FileUploadService fileUploadService;

	@Resource(name = "excelNewService")
	ExcelNewService excelNewService;

	@Autowired
	private FMMworkService fmMworkService;

	@Autowired
	private CommonUtilService commonUtilService;

	@Autowired
	private FMSetupService fmSetupService;

	@Autowired
	private IsmsBatchService ismsBatchService;

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	private CommonService commonService;

	@RequestMapping(value = "/FM-COMPS001.do")
	public String fmComps001(@ModelAttribute("searchVO") SearchVO searchVO, HttpServletRequest req,
			ModelMap model) throws Exception {

		int totCnt = fmCompsService.fm_comps001_cnt(searchVO);
		model.addAttribute("paginationInfo", new PaginationUtil(searchVO, totCnt).getPageData());
		model.addAttribute("resultList", fmCompsService.fm_comps001_list(searchVO));
		model.addAttribute("totCnt", totCnt);

		return "FM-COMPS001";
	}

	@RequestMapping("/FM-COMPS001_getManCylMax.do")
	public ModelAndView FM_COMPS001_getManCylMax(HttpServletRequest req, ModelMap model,
			HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		mav.addObject("result", fmCompsService.fm_comps001_getManCylMax());
		mav.setViewName("jsonView");
		return mav;
	}

	@RequestMapping("FM-COMPS001_V.do")
	public String FM_COMPS001_V(@RequestParam("selectedId") String id, Model model,
			final HttpServletRequest req) throws Exception {

		CycleVO vo = new CycleVO();
		vo.setUmm_man_cyl(id);
		CycleVO rvo = fmCompsService.fm_comps001_read(vo);

		model.addAttribute("vo", rvo);

		return "/FM-COMPS001-V";
	}

	@RequestMapping("FM-COMPS001_R.do")
	public String FM_COMPS001_R(@RequestParam("seq") String seq, Model model,
			final HttpServletRequest req) throws Exception {

		CycleVO vo = new CycleVO();
		vo.setUmm_man_cyl(seq);
		CycleVO rVo = fmCompsService.fm_comps001_read(vo);

		model.addAttribute("vo", rVo);

		return "/FM-COMPS001-W";
	}

	@RequestMapping(value = "FM-COMPS001_RW.do")
	public String FM_COMPS001_W(@ModelAttribute("vo") CycleVO vo, Model model,
			HttpServletRequest req) throws Exception {

		model.addAttribute("vo", new CycleVO());
		return "/FM-COMPS001-W";
	}

	@RequestMapping(value = "FM-COMPS001_W.do", method = RequestMethod.POST)
	public ModelAndView FM_COMPS001_W(@ModelAttribute("vo") CycleVO vo, BindingResult bindingResult,
			Model model, final HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		vo.setUmm_cre_id((String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));

		String msg = "";
		// int bfCnt =
		// fmCompsService.fm_comps001_bfMcy(vo,(String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

		String manCylEnd = (String) req.getSession().getAttribute(CommonConfig.SES_MAN_END_KEY);
		int bf_date = Integer.parseInt(manCylEnd);
		String in_date_ = vo.getUmm_std_dat().substring(0, 4) + vo.getUmm_std_dat().substring(5, 7)
				+ vo.getUmm_std_dat().substring(8, 10);
		int in_date = Integer.parseInt(in_date_);

		if (bf_date < in_date) {

			// 2017-02-01, 기존 마지막 운영 주기를 가져옴
			Map<String, String> map = new HashMap<String, String>();
			EgovMap mcyMap = null;
			mcyMap = ismsBatchService.selectLastManagementCycle();
			map.put("rgtId", vo.getUmm_cre_id());
			map.put("manCyl", (String) mcyMap.get("ummManCyl"));

			String manCon = vo.getUmm_man_con();

			manCon = EgovStringUtil.getHtmlStrCnvr(manCon);

			vo.setUmm_man_con(manCon);

			fmCompsService.fm_comps001_write(vo);

			msg = "0";

			// 2016-09-01, 운영주기 생성시 기존 운영주기 데이터 복제
			if (mcyMap.get("ummManCyl") != null) {
				mcyMap = ismsBatchService.selectLastManagementCycle();

				map.put("newManCyl", (String) mcyMap.get("ummManCyl"));
				map.put("startDate", (String) mcyMap.get("ummStdDat"));
				map.put("endDate", (String) mcyMap.get("ummEndDat"));
				ismsBatchService.insertManagementCycleData(map);
			}

		} else {
			msg = "1";
		}

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		mav.addObject("result", msg);
		mav.setViewName("jsonView");
		return mav;
	}

	@RequestMapping("FM-COMPS001_U.do")
	public String FM_COMPS001_U(CycleVO vo, BindingResult bindingResult, Model model,
			final HttpServletRequest req) throws Exception {
		fmCompsService.fm_comps001_update(vo);

		// String page = "redirect:/comps/FM-COMPS001_R.do?seq="+ vo.getUmm_man_cyl();
		// return page;
		return "redirect:/comps/FM-COMPS001.do";
	}

	@RequestMapping(value = "/FM-COMPS002.do")
	public String fmComps015(@ModelAttribute("searchVO") SearchVO searchVO, HttpServletRequest req,
			ModelMap model) throws Exception {

		searchVO.setStndNam(req.getParameter("stndNam"));

		int totCnt = fmCompsService.fm_comps002_cnt(searchVO);
		model.addAttribute("paginationInfo", new PaginationUtil(searchVO, totCnt).getPageData());
		model.addAttribute("list", fmCompsService.fm_comps002_list(searchVO));

		return "FM-COMPS002";
	}

	@RequestMapping(value = "/FM-COMPS002_RW.do")
	public String fmComps015_rw(HttpServletRequest req, ModelMap model) throws Exception {

		String strStndCod = req.getParameter("stndCod");
		if (StringUtils.isNotEmpty(strStndCod)) {
			EgovMap stndInfo = fmCompsService.fm_comps002_stnd_info(strStndCod);
			List<?> deptList = fmCompsService.fm_comps002_org_list(strStndCod);

			model.addAttribute("stndInfo", stndInfo);
			model.addAttribute("deptList", deptList);
		}
		return "FM-COMPS002-W";
	}

	@RequestMapping(value = "/FM-COMPS002_W.do")
	public String fmComps015_w(HttpServletRequest req, HttpServletResponse res,
			@RequestParam Map<String, Object> map, ModelMap model) throws Exception {
		map.put("uccFirCod", Constants.STND_COD);
		map.put("uccFirNam", Constants.STND_NAM);
		map.put("uccRgtId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		String strResult = fmCompsService.fm_comps002_stnd_insert(map);

		EgovMap stndInfo = fmCompsService.fm_comps002_stnd_info(map.get("uccSndCod").toString());
		List<?> deptList = fmCompsService.fm_comps002_org_list(map.get("uccSndCod").toString());

		model.addAttribute("stndInfo", stndInfo);
		model.addAttribute("deptList", deptList);
		model.addAttribute("result", strResult);
		return "FM-COMPS002-W";
	}

	@RequestMapping(value = "/FM-COMPS002_U.do")
	public String fmComps015_u(HttpServletRequest req, HttpServletResponse res,
			@RequestParam Map<String, Object> map, ModelMap model) throws Exception {
		map.put("uccRgtId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		String strResult = fmCompsService.fm_comps002_stnd_update(map);

		EgovMap stndInfo = fmCompsService.fm_comps002_stnd_info(map.get("stndCod").toString());
		List<?> deptList = fmCompsService.fm_comps002_org_list(map.get("stndCod").toString());

		model.addAttribute("stndInfo", stndInfo);
		model.addAttribute("deptList", deptList);
		model.addAttribute("result", strResult);
		return "FM-COMPS002-W";
	}

	@RequestMapping(value = "/FM-COMPS002_popup.do")
	public String fmComps015_popup(HttpServletRequest req, HttpServletResponse res,
			@RequestParam Map<String, String> map, ModelMap model) throws Exception {
		model.addAttribute("stndCod", req.getParameter("stndCod").toString());
		model.addAttribute("info", fmCompsService.fm_comps002_org_info(map));
		return "FM-COMPS002_popup";
	}

	@RequestMapping(value = "/FM-COMPS002_W_popup.do")
	public ModelAndView fmComps015_w_popup(HttpServletRequest req, HttpServletResponse res,
			@RequestParam Map<String, String> map, ModelMap model) throws Exception {
		map.put("uomCsrCod", Constants.DIV_COD);
		map.put("uomDivCod", Constants.DIV_NAM);
		map.put("uomCtrCod", req.getParameter("stndCod").toString());
		map.put("uomRgtId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));

		ModelAndView mav = new ModelAndView();
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		mav.addObject("result", fmCompsService.fm_comps002_org_insert(map));
		mav.setViewName("jsonView");
		return mav;
	}

	@RequestMapping(value = "/FM-COMPS002_U_popup.do")
	public ModelAndView fmComps015_u_popup(HttpServletRequest req, HttpServletResponse res,
			@RequestParam Map<String, String> map, ModelMap model) throws Exception {
		map.put("uomRgtId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		map.put("stndCod", req.getParameter("stndCod").toString());
		map.put("oldDivCod", Constants.DIV_COD);

		ModelAndView mav = new ModelAndView();
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		mav.addObject("result", fmCompsService.fm_comps002_org_update(map));
		mav.setViewName("jsonView");
		return mav;
	}

	@RequestMapping(value = "/FM-COMPS003.do")
	public String fmComps003(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model,
			final HttpServletRequest req) throws Exception {

		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			model.addAttribute("message", "faile");
			return "redirect:/index.jsp";
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("ucmBcyCod", (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		map.put("ucmCtrGbn", searchVO.getStandard());
		map.put("service", searchVO.getService());
		List<?> list = fmCompsService.fm_comps003_list(map);

		model.addAttribute("resultList", list);
		model.addAttribute("searchVO", searchVO);

		String stndKind = String.valueOf(excelNewService.getStndKind(req.getParameter("standard")));
		switch (stndKind) {
		case "infra_mp":
		case "msit":
		case "infra_la":
			return "FM-COMPS003_Other";
		default:
			return "FM-COMPS003";
		}
	}

	@RequestMapping(value = "/FM-COMPS003_2D.do")
	public String fmComps003_2D(ModelMap model, final HttpServletRequest req) throws Exception {

		Map<String, String> map = new HashMap<String, String>();
		map.put("standard", req.getParameter("standard"));
		map.put("service", req.getParameter("service"));
		map.put("ucm1lvCod", req.getParameter("hUcm1lvCod"));
		map.put("ucmBcyCod", (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

		List<?> list = fmCompsService.fm_comps003_2D_list(map);
		model.addAttribute("resultList", list);
		model.addAttribute("ucm1lvCod", req.getParameter("hUcm1lvCod"));
		model.addAttribute("standard", req.getParameter("standard"));
		model.addAttribute("service", req.getParameter("service"));

		String stndKind = String.valueOf(excelNewService.getStndKind(req.getParameter("standard")));
		switch (stndKind) {
		case "infra_mp":
		case "msit":
		case "infra_la":
			return "FM-COMPS003-2D_Other";
		default:
			return "FM-COMPS003-2D";
		}
	}

	@RequestMapping(value = "/FM-COMPS003_3D.do")
	public String fmComps003_3D(ModelMap model, final HttpServletRequest req) throws Exception {

		Map<String, String> map = new HashMap<String, String>();
		map.put("standard", req.getParameter("standard"));
		map.put("service", req.getParameter("service"));
		map.put("ucm2lvCod", req.getParameter("hUcm2lvCod"));
		map.put("ucmBcyCod", (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		List<?> list = fmCompsService.fm_comps003_3D_list(map);

		List listKeyCode = new ArrayList();

		for (int i = 0; i < list.size(); i++) {
			EgovMap mapCtrKey = (EgovMap) list.get(i);

			Map<String, String> mapKeyCode = new HashMap<String, String>();
			mapKeyCode.put("key", mapCtrKey.get("ucmCtrKey").toString());
			mapKeyCode.put("code", mapCtrKey.get("ucm3lvCod").toString());
			listKeyCode.add(mapKeyCode);
		}

		Map<String, Object> mapKeyCodes = new HashMap<String, Object>();
		mapKeyCodes.put("listKeyCode", listKeyCode);

		String stndKind = String.valueOf(excelNewService.getStndKind(req.getParameter("standard")));
		List<?> listExpData = excelNewService.fm_comps003_3D_ExpData(stndKind, mapKeyCodes);
		List<?> brdlist = excelNewService.fm_comps003_3D_mappinglistInCtrKey(mapKeyCodes);

		model.addAttribute("resultList", list);
		model.addAttribute("stndKind", stndKind);
		model.addAttribute("listExpData", listExpData);
		model.addAttribute("brdlist", brdlist);

		model.addAttribute("ucm1lvCod", req.getParameter("hUcm1lvCod"));
		model.addAttribute("ucm2lvCod", req.getParameter("hUcm2lvCod"));
		model.addAttribute("standard", req.getParameter("standard"));
		model.addAttribute("service", req.getParameter("service"));

		model.addAttribute("serviceList", commonCodeService.getServiceAuthList_N(null));
		model.addAttribute("serviceNode", fmCompsService.fmComps003_service_M(map));

		if (stndKind.equals("infra_mp")) {
			return "FM-COMPS003-3D_infra_mp";
		} else if (stndKind.equals("msit")) {
			return "FM-COMPS003-3D_msit";
		} else if (stndKind.equals("infra_la")) {
			return "FM-COMPS003-3D_infra_la";
		}
		return "FM-COMPS003-3D";
	}

	@RequestMapping(value = "/FM-COMPS003_SAV.do")
	public String fmComps003_SAV(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model,
			final HttpServletRequest req) throws Exception {

		String sts[] = req.getParameterValues("sts");
		String ucm1lvNam[] = req.getParameterValues("ucm1lvNam");
		String ucm1lvCod[] = req.getParameterValues("ucm1lvCod");
		String rad = "rad_";
		String[] ucmCtrKey = req.getParameterValues("ucmCtrKey");

		Map<String, String> map = new HashMap<String, String>();
		map.put("UCM_BCY_COD",
				(String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		map.put("UCM_RGT_ID", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		map.put("UCM_CTR_GBN", req.getParameter("standard"));
		// map.put("UCM_DIV_COD", req.getParameter("service"));

		for (int i = 0; i < ucm1lvNam.length; i++) {
			map.put("UCM_1LV_COD", ucm1lvCod[i]);
			map.put("UCM_1LV_NAM", ucm1lvNam[i]);
			map.put("UCM_1LD_YN", (String) req.getParameter(rad + i));

			if (sts[i].equals("C")) {
				fmCompsService.fm_comps003_insert(map);
			} else if (sts[i].equals("U")) {
				fmCompsService.fm_comps003_update(map);
			}
		}
		model.addAttribute("searchVO", searchVO);
		return "forward:/comps/FM-COMPS003.do";
	}

	@RequestMapping(value = "/FM-COMPS003_2D_SAV.do")
	public String fmComps003_2D_SAV(ModelMap model, final HttpServletRequest req) throws Exception {

		String sts[] = req.getParameterValues("sts");
		String ucm2lvCod[] = req.getParameterValues("ucm2lvCod");
		String ucm2lvNam[] = req.getParameterValues("ucm2lvNam");
		String ucm2lvDtl[] = req.getParameterValues("ucm2lvDtl");
		String rad = "rad_";
		Map<String, String> map = new HashMap<String, String>();
		map.put("UCM_BCY_COD",
				(String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		// map.put("UCM_DIV_COD", (String)req.getParameter("service"));
		map.put("UCM_1LV_COD", req.getParameter("hUcm1lvCod"));
		map.put("UCM_CTR_GBN", req.getParameter("standard"));
		map.put("UCM_RGT_ID", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		map.put("KEY", req.getParameter("hUcm1lvCod"));

		for (int i = 0; i < ucm2lvCod.length; i++) {
			map.put("UCM_2LV_COD", ucm2lvCod[i]);
			map.put("UCM_2LV_NAM", ucm2lvNam[i]);
			map.put("UCM_2LV_DTL", ucm2lvDtl[i]);
			map.put("UCM_2LD_YN", (String) req.getParameter("rad_" + i));

			if (sts[i].equals("C")) {
				fmCompsService.fm_comps003_2D_insert(map);
			} else if (sts[i].equals("U")) {
				fmCompsService.fm_comps003_2D_update(map);
			}
		}

		return "forward:/comps/FM-COMPS003_2D.do";
	}

	@RequestMapping(value = "/FM-COMPS003_3D_SAV.do")
	public String fmComps003_3D_SAV(ModelMap model, final HttpServletRequest req) throws Exception {

		String[] sts = req.getParameterValues("sts");
		String[] ucm3lvCod = req.getParameterValues("ucm3lvCod");
		String[] ucm3lvNam = req.getParameterValues("ucm3lvNam");
		String[] ucm3lvDtl = req.getParameterValues("ucm3lvDtl");
		/* String[] ucmRltGid = req.getParameterValues("ucmRltGid"); */
		String rad = "rad_";
		String[] ucmCtrKey = req.getParameterValues("ucmCtrKey");

		Map<String, String> map = new HashMap<String, String>();
		map.put("UCM_BCY_COD",
				(String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		map.put("UCM_CTR_GBN", req.getParameter("standard"));
		// map.put("UCM_DIV_COD", (String)req.getParameter("service"));
		map.put("UCM_RGT_ID", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		if (ucm3lvCod != null) {
			for (int i = 0; i < ucm3lvCod.length; i++) {
				map.put("UCM_3LV_COD", ucm3lvCod[i]);
				map.put("UCM_3LV_NAM", ucm3lvNam[i]);
				map.put("UCM_3LV_DTL", ucm3lvDtl[i]);
				/* map.put("UCM_RLT_GID", String.valueOf(ucmRltGid[i])); */
				map.put("UCM_2LV_COD", req.getParameter("hUcm2lvCod"));
				map.put("KEY", req.getParameter("hUcm2lvCod"));
				map.put("UCM_3LD_YN", (String) req.getParameter("rad_" + i));

				if (sts[i].equals("C")) {
					fmCompsService.fm_comps003_3D_insert(map);

					/*
					 * 2017-05-04, 주석처리 Map<String, Object> serviceList = new HashMap<String,
					 * Object>(); serviceList.put("UCM_BCY_COD",
					 * (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
					 * serviceList.put("UCM_CTR_GBN", req.getParameter("standard"));
					 * serviceList.put("UCM_2LV_COD", req.getParameter("hUcm2lvCod"));
					 * serviceList.put("UCM_3LV_COD", ucm3lvCod[i]); serviceList.put("UCM_RGT_GID",
					 * (String)req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
					 * fmCompsService.rm_comps003_service_insert(serviceList);
					 */
				} else if (sts[i].equals("U")) {
					fmCompsService.fm_comps003_3D_update(map);

					Map<String, Object> serviceList = new HashMap<String, Object>();
					serviceList.put("ucmCtrKey", ucmCtrKey[i]);
					serviceList.put("serviceList",
							req.getParameterValues("uwoCtrMap_" + ucmCtrKey[i]));
					serviceList.put("ucmRgtId",
							(String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
					fmCompsService.rm_comps003_service_update(serviceList);
				}
			}
		}
		return "forward:/comps/FM-COMPS003_3D.do";
	}

	@RequestMapping("/FM-COMPS003_popup.do")
	public String fmComps003_popup(ModelMap model, @RequestParam Map<String, String> map,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		map.put("auth", (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));
		List<?> formlist = fmCompsService.fm_comps003_popup(map);

		model.addAttribute("formlist", formlist);
		model.addAttribute("mapKey", map.get("mapKey"));

		return "FM-COMPS003_popup";
	}

	// excelUpload popup
	/*
	 * @RequestMapping(value="/FM-COMPS003_excel_popup.do") public String
	 * fmComps003_excel_popup(ModelMap model, HttpServletRequest req,
	 * HttpServletResponse res) throws Exception {
	 * 
	 * SearchVO searchVO = new SearchVO();
	 * 
	 * model.addAttribute("resultList",
	 * commonCodeService.getServiceAuthList_N(null));
	 * 
	 * // return : jsp page name return "FM-COMPS003_excel_popup"; }
	 */

	/*
	 * @RequestMapping(value="/FM-COMPS003_excelUpload.do") public ModelAndView
	 * fmComps003_excelUpload(ModelMap model, HttpServletRequest req,
	 * HttpServletResponse res) throws Exception { ModelAndView mav = new
	 * ModelAndView();
	 * 
	 * MultipartHttpServletRequest mreq = (MultipartHttpServletRequest) req; String
	 * excelName = mreq.getFile("excelFile").getOriginalFilename(); String strResult
	 * = "";
	 * 
	 * String[] service = mreq.getParameterValues("service"); List dataList = new
	 * ArrayList(); for (int i=0; i < service.length-1; i++) {
	 * dataList.add(service[i]); }
	 * 
	 * String ext = FilenameUtils.getExtension(excelName);
	 * if(StringUtils.equalsIgnoreCase(ext, "xlsx") ||
	 * StringUtils.equalsIgnoreCase(ext, "xls")) { strResult =
	 * fmCompsService.fm_comps003_excel_insert(req, dataList); }else{ strResult =
	 * Constants.RETURN_FAIL; }
	 * 
	 * res.setCharacterEncoding(""); res.setContentType("text/xml; charset=UTF-8");
	 * 
	 * Map<String, Object> resultMap = new HashMap<String, Object>();
	 * resultMap.put("result", strResult); mav.addAllObjects(resultMap);
	 * mav.setViewName("jsonView");
	 * 
	 * return mav;
	 * 
	 * }
	 */

	@RequestMapping(value = "/FM-COMPS003_MAPPING.do")
	public ModelAndView fmComps003_popup_mapping(ModelMap model, HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		String brdKey = req.getParameter("brdKey");
		String mapKey = req.getParameter("mapKey");
		String ucbGolNo = req.getParameter("ucbGolNo");

		String rgt_id = (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY);
		Map map = new HashMap();
		map.put("mapKey", mapKey);
		map.put("brdKey", brdKey);
		map.put("ucbGolNo", ucbGolNo);
		map.put("rgt_id", rgt_id);
		fmCompsService.fm_comps003_popup_mapping(map);

		ModelAndView mav = new ModelAndView();
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");
		mav.addObject("result", "S");
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping(value = "/FM-COMPS003_DEL_MAPPING.do")
	public String fmComps003_del_mapping(ModelMap model, HttpServletRequest req) throws Exception {

		String brdKey = req.getParameter("brdKey");
		fmCompsService.fm_comps003_del_mapping(brdKey);

		return "forward:/comps/FM-COMPS003_3D.do";
	}

	@RequestMapping("/FM-COMPS003_brd_popup.do")
	public String fmComps003_brd_view(ModelMap model, HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		String key = req.getParameter("brdViewKey");

		BoardVO vo = fmCompsService.fm_comps003_popup_brdView(key);
		List<?> list = fmCompsService.fm_comps003_popup_brdFile(key);

		model.addAttribute("vo", vo);
		model.addAttribute("list", list);

		return "FM-COMPS003_brd_popup";
	}

	@RequestMapping("/FM-COMPS004.do")
	public String fmComps004(@ModelAttribute("searchVO") SearchVO searchVO, HttpServletRequest req,
			ModelMap model) throws Exception {

		return "FM-COMPS004";
	}

	@RequestMapping("/FM-COMPS004_list.do")
	public ModelAndView fmComps004_list(@ModelAttribute("searchVO") SearchVO searchVO,
			ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		String company = propertyService.getString("company");
		searchVO.setManCyl((String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		searchVO.setDivision((String) req.getSession().getAttribute(CommonConfig.SES_DIVISION_KEY));
		searchVO.setCompany(company);

		searchVO.setPageIndex(Integer.parseInt(req.getParameter("page")));
		searchVO.setPageUnit(Integer.parseInt(req.getParameter("rows")));

		// 2017-08-14, jqgrid sorting
		searchVO.setCode(req.getParameter("sidx"));
		searchVO.setOrder(req.getParameter("sord"));

		int totCnt = fmCompsService.fm_comps004_cnt(searchVO);
		PaginationInfo paginationInfo = new PaginationUtil(searchVO, totCnt).getPageData();
		List<?> list = fmCompsService.fm_comps004(searchVO);

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", list);
		resultMap.put("currentPageNo", paginationInfo.getCurrentPageNo());
		resultMap.put("totalPage", paginationInfo.getTotalPageCount());
		resultMap.put("totalRecord", paginationInfo.getTotalRecordCount());

		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping("/FM-COMPS004_popup.do")
	public String fmComps004_popup(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		String trcKey = req.getParameter("utdTrcKey");
		String company = propertyService.getString("company");

		searchVO.setDocCode(trcKey);
		searchVO.setCompany(company);

		String mode = "update";
		if (trcKey == "") {
			mode = "add";
		} else {

			SampleDocVO vo = fmCompsService.fm_comps004_sampleDocInfo(searchVO);
			String bcyCod = (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY);
			List<FileVO> fileVOList = fileUploadService.selectFileList(trcKey, "DOC", "5", bcyCod);
			model.addAttribute("sampleDocVO", vo);
			if (fileVOList.size() > 0) {
				model.addAttribute("fileVOList", fileVOList);
			}
		}
		model.addAttribute("utdTrcKey", trcKey);
		model.addAttribute("mode", mode);
		return "FM-COMPS004_popup";
	}

	/*
	 * 2016-07-14 add comment
	 * 
	 * @RequestMapping("/FM-COMPS004_member_popup.do") public String
	 * fmComps004_member_popup (ModelMap model, HttpServletRequest req,
	 * HttpServletResponse res, @RequestParam Map<String, String> paramMap) throws
	 * Exception { System.out.println("123" + req.getParameter("utdTrcKey"));
	 * 
	 * model.addAttribute("paramMap", paramMap);
	 * 
	 * return "FM-COMPS004_member_popup"; }
	 */

	/*
	 * @RequestMapping("/FM-COMPS004_cntr_popup.do") public String
	 * fmComps004_cntr_popup (@ModelAttribute("searchVO") SearchVO searchVO,
	 * ModelMap model, HttpServletRequest req, HttpServletResponse res) throws
	 * Exception {
	 * 
	 * String trcKey = req.getParameter("trcKey"); String company =
	 * propertyService.getString("company");
	 * 
	 * model.addAttribute("utdTrcKey", trcKey);
	 * 
	 * return "FM-COMPS004_cntr_popup"; }
	 */

	@RequestMapping("/GetDepth.do")
	public ModelAndView fmComps004_GetDepth(ModelMap model, HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		SearchVO searchVO = new SearchVO();
		searchVO.setCode(req.getParameter("code"));
		// searchVO.setDivision((String)req.getSession().getAttribute(CommonConfig.SES_SERVICE_KEY));
		searchVO.setManCyl((String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		searchVO.setService(req.getParameter("svc"));
		if (req.getParameter("svc") == null || req.getParameter("svc").equals("")) {
			searchVO.setService(
					(String) req.getSession().getAttribute(CommonConfig.SES_SERVICE_KEY));
		}
		searchVO.setStandard(req.getParameter("standard"));
		searchVO.setDepth1(req.getParameter("depth1"));
		searchVO.setDepth2(req.getParameter("depth2"));
		searchVO.setDepth3(req.getParameter("depth3"));

		List<?> list = fmCompsService.fm_comps004_GetDepth(searchVO);

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", list);
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping("/FM-COMPS004_workerList.do")
	public ModelAndView fmComps004_workerList(ModelMap model, HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();
		List<?> list = fmCompsService.fm_comps004_workerList(req.getParameter("utdTrcKey"));

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", list);
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping("/FM-COMPS004_getCtrMapList.do")
	public ModelAndView fmComps004_getCtrMapList(ModelMap model, HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();
		String utdTrcKey = req.getParameter("trcKey");
		String company = propertyService.getString("company");

		SearchVO searchVO = new SearchVO();
		searchVO.setDocCode(utdTrcKey);

		List<?> list = fmCompsService.fm_comps004_getCtrMapList(searchVO);

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", list);
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	/*
	 * 2016-07-14 add comment
	 * 
	 * @RequestMapping("/FM-COMPS004_worker_del.do") public ModelAndView
	 * fmComps004_workerDel(HttpServletRequest req, HttpServletResponse res) throws
	 * Exception {
	 * 
	 * ModelAndView mav = new ModelAndView();
	 * 
	 * // 업무담당자 삭제 시 해당 업무도 삭제. String[] id = req.getParameter("id").split("\\,");
	 * String utmTrcKey = req.getParameter("utmTrcKey");
	 * fmCompsService.fm_comps004_worker_del(id, utmTrcKey);
	 * 
	 * res.setCharacterEncoding(""); res.setContentType("text/xml; charset=UTF-8");
	 * 
	 * Map<String, Object> resultMap = new HashMap<String, Object>();
	 * //resultMap.put("result", list); mav.addAllObjects(resultMap);
	 * mav.setViewName("jsonView");
	 * 
	 * return mav; }
	 */

	@RequestMapping("/FM-COMPS004_sampleDoc_save.do")
	public ModelAndView fmComps004_sampleDoc_save(
			@ModelAttribute("sampleDocVO") SampleDocVO sampleDocVO, HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		ModelAndView mav = new ModelAndView();
		int result = 1;
		// get from properties, file uploadpath
		String uploadPath = propertyService.getString("docSampleUploadPath");
		req.setAttribute("uploadPath", uploadPath);
		FileUpload fu = new FileUpload();
		// parammeter : req, umf_tbl_gbn, umf_con_gbn

		List<FileVO> list = new ArrayList<FileVO>();

		String mode = sampleDocVO.getMode();
		if (mode.equals("update")) {
			sampleDocVO
					.setUtdUptId((String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		}

		sampleDocVO
				.setManCylStr((String) req.getSession().getAttribute(CommonConfig.SES_MAN_STD_KEY));
		sampleDocVO
				.setManCylEnd((String) req.getSession().getAttribute(CommonConfig.SES_MAN_END_KEY));
		sampleDocVO.setUtdDivCod(Constants.DIV_COD);

		String appYn = sampleDocVO.getUtdAprYn();
		if (appYn.equals("N")) {
			sampleDocVO.setUtdAppLev("0");
		}

		String term = sampleDocVO.getUtdTrmGbn();
		if (term.length() > 1) {
			sampleDocVO.setUtdTrmGbn("N" + term);
		}

		String completeDate = sampleDocVO.getUtdCmpDat();
		EgovMap eMap = fmCompsService.nonWorkYn(completeDate);

		String msg = "";
		// 비주기일 경우에만 체크함
		if (eMap.get("uwyWrkYn").equals("Y")) {
			if (term.equals("Y") || term.equals("N")) {
				msg = (String) eMap.get("uwyEtc");
				if (StringUtils.isEmpty(msg)) {
					msg = "주말";
				}
			} else {
				eMap.put("uwyWrkYn", "N");
			}
		}

		if (eMap.get("uwyWrkYn").equals("N")) {
			// 2016-07-06 add comment
			// if(!sampleDocVO.getUtdDocGbn().equals("50")){
			list = fu.fileuplaod_J(req, "DOC", "5");
			// }

			// 1. insert/update doc
			result = fmCompsService.fm_comps004_sampleDoc_save(sampleDocVO, list);

			String trcKey = String.valueOf(result);
			String[] standardData = req.getParameter("standardData").split("\\,");
			String[] workerData = req.getParameter("workerData").split("\\,");
			String[] workData = req.getParameter("workData").split("\\,");

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("uptId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
			map.put("rgtId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
			map.put("bcyCod",
					req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY).toString());
			map.put("trcKey", trcKey);

			if (sampleDocVO.getUtdDelYn().equals("N")) {
				// 2. insert/update standard
				map.put("ids", standardData);
				fmCompsService.fm_comps004_standard_change(map);

				// 3. insert/update worker
				map.put("ids", workerData);
				fmCompsService.fm_comps004_worker_change(map);

				// 4. insert/update doc
				map.put("ids", workData);
				fmCompsService.fm_comps004_work_delete(map);

				// 5. insert work
				if (req.getParameter("addWork").equals("true")) {
					map.put("ids", workerData);
					fmCompsService.fm_comps004_work_insert(map);
				}

				// 2017-05-19
				// 6. 증적주기, 담당자 변경시 기존 미완료 데이터 삭제처리
				fmCompsService.fm_comps004_change_work_delete(map);

				// 2017-02-19
				// 7. update 최종할당일(wrk_upt_mdh)
				fmCompsService.fm_comps004_updateWorkLastInsertDate(map);

				//2020-04-03
				// 8. 보호활동의 서비스 변경시 업무의 서비스도 업데이트
				if (mode.equals("update") && (!workData[0].equals(""))) {
					map.put("ids", workData);
					map.put("svcCod",sampleDocVO.getUtdSvcCod());
					fmCompsService.fm_comps004_updateSameService(map);
				}

				// ext. Polaris Converter
				commonUtilService.insertPolarisConverter(Integer.toString(result));
			}
		}

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", result);
		resultMap.put("nonWorkYn", eMap.get("uwyWrkYn"));
		resultMap.put("msg", msg);
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	/*
	 * 2016-07-14 add comment
	 * 
	 * @RequestMapping("/FM-COMPS004_worker_save.do") public ModelAndView
	 * fmComps004_worker_save (@RequestParam Map<String, String> map,
	 * HttpServletRequest req, HttpServletResponse res) throws Exception {
	 * 
	 * ModelAndView mav = new ModelAndView();
	 * fmCompsService.fmComps004_worker_save(map);
	 * 
	 * fmCompsService.CreateWork(map);
	 * 
	 * Map<String, Object> resultMap = new HashMap<String, Object>();
	 * resultMap.put("result", "S"); mav.addAllObjects(resultMap);
	 * mav.setViewName("jsonView");
	 * 
	 * return mav; }
	 */

	@RequestMapping(value = "/FM-COMPS005.do")
	public String fmComps005(@ModelAttribute("searchVO") SearchVO searchVO, HttpServletRequest req,
			ModelMap model) throws Exception {

		model.addAttribute("searchVO", searchVO);
		return "FM-COMPS005";
	}

	@RequestMapping("/FM-COMPS004_getWorkList.do")
	public ModelAndView fmComps004_getWorkList(ModelMap model, HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		String trcKey = req.getParameter("trcKey");

		List<?> list = fmMworkService.selectWorkListByTrcKey(trcKey);

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", list);
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	/*
	 * FM-COMPS004_work_del.do
	 * 
	 * @RequestMapping("/FM-COMPS004_work_del.do") public ModelAndView
	 * fmComps004_workDel(HttpServletRequest req, HttpServletResponse res) throws
	 * Exception {
	 * 
	 * ModelAndView mav = new ModelAndView();
	 * 
	 * String[] id = req.getParameter("id").split("\\,"); String utmTrcKey =
	 * req.getParameter("utmTrcKey"); fmCompsService.fm_comps004_work_del(id,
	 * utmTrcKey);
	 * 
	 * res.setCharacterEncoding(""); res.setContentType("text/xml; charset=UTF-8");
	 * 
	 * Map<String, Object> resultMap = new HashMap<String, Object>();
	 * mav.addAllObjects(resultMap); mav.setViewName("jsonView");
	 * 
	 * return mav; }
	 */

	@RequestMapping("/FM-COMPS005_getStdList.do")
	public void fmComps005_getStdList(ModelMap model, HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		String division = req.getParameter("service");
		SearchVO searchVO = new SearchVO();
		searchVO.setDivision(division);
		searchVO.setManCyl(req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY).toString());
		searchVO.setService(req.getParameter("service"));

		// 2016-11-02
		searchVO.setUseYn(req.getParameter("docYn"));
		searchVO.setSearchCondition(req.getParameter("searchCondition"));

		List<?> list = fmCompsService.fm_comps005_getStdList(searchVO);
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> dataMap = new HashMap<String, Object>();

			dataMap.put("ucmCtrGbn", ((Map) list.get(i)).get("ucmCtrGbn"));
			dataMap.put("key", ((Map) list.get(i)).get("ucmCtrGbn"));
			dataMap.put("title", ((Map) list.get(i)).get("ucmCtrNam"));
			dataMap.put("isLazy", new Boolean(true));
			dataMap.put("hideCheckbox", new Boolean(true));
			dataMap.put("isFolder", new Boolean(true));
			dataMap.put("depth", "STD");
			dataList.add(dataMap);
		}

		JSON json = JSONSerializer.toJSON(dataList, new JsonConfig());
		res.setContentType("application/x-json;charset=euc-kr");
		res.getWriter().print(json.toString());
	}

	@RequestMapping("/FM-COMPS005_getCntrList.do")
	public void fmComps005_getCntrList(ModelMap model, HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		// ModelAndView mav = new ModelAndView();

		String depth = req.getParameter("depth");
		String nextDepth = "";

		SearchVO searchVO = new SearchVO();
		searchVO.setSearchKeyword(req.getParameter("key"));
		searchVO.setDivision(req.getParameter("service"));
		searchVO.setManCyl((String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		searchVO.setService(req.getParameter("service"));
		searchVO.setStandard(req.getParameter("gbn"));

		// 2016-11-02
		searchVO.setUseYn(req.getParameter("docYn"));
		searchVO.setSearchCondition(req.getParameter("searchCondition"));

		List<?> list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

		String lvCode = "";
		String lvName = "";
		String lvDetail = "";
		if (depth.equals("STD")) {
			list = fmCompsService.fm_comps005_getCntr1List(searchVO);

			lvCode = "ucm1lvCod";
			lvName = "ucm1lvNam";
			nextDepth = "CNTR1";
		} else if (depth.equals("CNTR1")) {
			list = fmCompsService.fm_comps005_getCntr2List(searchVO);

			lvCode = "ucm2lvCod";
			lvName = "ucm2lvNam";
			lvDetail = "ucm2lvDtl";
			nextDepth = "CNTR2";
		} else if (depth.equals("CNTR2")) {
			list = fmCompsService.fm_comps005_getCntr3List(searchVO);

			lvCode = "ucm3lvCod";
			lvName = "ucm3lvNam";
			lvDetail = "ucm3lvDtl";
			nextDepth = "CNTR3";
		} else if (depth.equals("CNTR3")) {
			list = fmCompsService.fm_comps005_getCntr4List(searchVO);

			lvCode = "ucm4lvCod";
			lvName = "ucm4lvNam";
			lvDetail = "ucm4lvDtl";
			nextDepth = "CNTR4";
		} else if (depth.equals("CNTR4")) {
			list = fmCompsService.fm_comps005_getCntr5List(searchVO);

			lvCode = "ucm5lvCod";
			lvName = "ucm5lvNam";
			lvDetail = "ucm5lvDtl";
			nextDepth = "CNTR5";
		}

		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			String delYN = "Y";

			dataMap.put("ucmCtrGbn", ((Map) list.get(i)).get("ucmCtrGbn"));
			dataMap.put("ucmCtrCod", ((Map) list.get(i)).get("ucmCtrCod"));
			dataMap.put("key", ((Map) list.get(i)).get(lvCode));
			dataMap.put("name", ((Map) list.get(i)).get(lvName));

			if (nextDepth.equals("CNTR3")) {
				dataMap.put("isLazy", new Boolean(false));
				dataMap.put("title", ((Map) list.get(i)).get(lvCode));
			} else {
				dataMap.put("isFolder", new Boolean(true));
				dataMap.put("isLazy", new Boolean(true));
				dataMap.put("title",
						((Map) list.get(i)).get(lvCode) + " " + ((Map) list.get(i)).get(lvName));
			}

			dataMap.put("del_yn", ((Map) list.get(i)).get("ucmDelYn"));
			delYN = ((Map) list.get(i)).get("ucmDelYn").toString();
			if (delYN.equals("Y")) {
				dataMap.put("select", new Boolean(true));
			}
			if (!lvDetail.equals("")) {
				dataMap.put("lvDetail", ((Map) list.get(i)).get(lvDetail));
			}
//        	dataMap.put("hideCheckbox", new Boolean(true));
			dataMap.put("depth", nextDepth);
			dataList.add(dataMap);
		}

		JSON json = JSONSerializer.toJSON(dataList, new JsonConfig());
		res.setContentType("application/x-json;charset=euc-kr");
		res.getWriter().print(json.toString());
	}

	@RequestMapping("/FM-COMPS005_mappingSampleDocList.do")
	public ModelAndView fmComps005_mappingSampleDocList(ModelMap model, HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		String mode = req.getParameter("mode");
		String searchKeyword = req.getParameter("ctrKey");
		String company = propertyService.getString("company");
		String utcMapKey = "";

		SearchVO searchVO = new SearchVO();
		searchVO.setCompany(company);
		searchVO.setSearchKeyword(searchKeyword);
		searchVO.setManCyl((String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		searchVO.setWorker((String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));

		List<?> list = new ArrayList();

		if (mode.equals("get")) {
			list = fmCompsService.fm_comps005_getMappingSampleDocList(searchVO);
		} else if (mode.equals("del")) {
			utcMapKey = req.getParameter("mapKey");
			searchVO.setCode(utcMapKey);

			fmCompsService.fm_comps005_delMappingSampleDocList(searchVO);
		}

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", list);
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping("/FM-COMPS005_popup.do")
	public String fmComps005_popup(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		String ucmCtrKey = req.getParameter("ucmCtrKey");

		model.addAttribute("ucmCtrKey", ucmCtrKey);
		return "FM-COMPS005_popup";
	}

	@RequestMapping("/FM-COMPS005_list.do")
	public ModelAndView fmComps005_list(@ModelAttribute("searchVO") SearchVO searchVO,
			ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		String company = propertyService.getString("company");

		searchVO.setCompany(company);
		searchVO.setManCyl(req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY).toString());

		List<?> list = fmCompsService.fm_comps005_popup(searchVO);

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", list);
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping("/FM-COMPS005_getGuideList.do")
	public ModelAndView fmComps005_getGuideList(ModelMap model, HttpServletRequest req,
			HttpServletResponse res) throws Exception {

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

	@RequestMapping("/FM-COMPS005_getUcmCtrKey.do")
	public ModelAndView fmComps005_getUcmCtrKey(ModelMap model, HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		String utcCtrGbn = req.getParameter("utcCtrGbn");
		String utc3lvCod = req.getParameter("utc3lvCod");
		// String division = req.getParameter("division");
		String bcyCod = req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY).toString();

		SearchVO searchVO = new SearchVO();
		searchVO.setCode(utcCtrGbn);
		searchVO.setSearchKeyword(utc3lvCod);
		// searchVO.setDivision(division);
		searchVO.setManCyl(bcyCod);

		String ucmCtrKey = fmCompsService.fm_comps005_getUcmCtrKey(searchVO);

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", ucmCtrKey);
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping("/FM-COMPS005_setMapping_save.do")
	public ModelAndView fmComps005_setMapping_save(HttpServletRequest req, HttpServletResponse res)
			throws Exception {

		ModelAndView mav = new ModelAndView();
		int result = 1;

		SearchVO searchVO = new SearchVO();

		String utcCtrKey = req.getParameter("code");
		String utcTrcKey = req.getParameter("utcTrcKey");
		String utcBcyCod = req.getParameter("utcBcyCod");
		String utcRgtId = req.getParameter("utcRgtId");

		Map<String, String> map = new HashMap<String, String>();
		map.put("utcCtrKey", utcCtrKey);
		map.put("utcTrcKey", utcTrcKey);
		map.put("utcBcyCod", utcBcyCod);
		map.put("utcRgtId", utcRgtId);

		fmCompsService.fm_comps005_setMapping_save(map);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", result);
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	/*
	 * 2016-07-14 add comment
	 * 
	 * @RequestMapping("/FM-COMPS004_mapping_del.do") public ModelAndView
	 * fmComps004_mapping_del (HttpServletRequest req, HttpServletResponse res,
	 * 
	 * @RequestParam Map<String, String> map ) throws Exception { ModelAndView mav =
	 * new ModelAndView(); fmCompsService.fm_comps004_mapping_del(map);
	 * 
	 * Map<String, Object> resultMap = new HashMap<String, Object>();
	 * resultMap.put("result", "S"); mav.addAllObjects(resultMap);
	 * mav.setViewName("jsonView");
	 * 
	 * return mav; }
	 */

	@RequestMapping("/FM-COMPS004_member.do")
	public ModelAndView fmComps004_member(@ModelAttribute("searchVO") SearchVO searchVO,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		ModelAndView mav = new ModelAndView();
		List<?> treeStr = fmCompsService.fm_comps004_member(searchVO);

		Map resultMap = new HashMap();

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");
		resultMap.put("result", treeStr);
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");
		return mav;
	}

	@RequestMapping("/FM-COMPS004_dept.do")
	public ModelAndView fmComps004_dept(@ModelAttribute("searchVO") SearchVO searchVO,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		ModelAndView mav = new ModelAndView();
		List<?> treeStr = fmCompsService.fm_comps004_dept(searchVO);

		Map resultMap = new HashMap();

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");
		resultMap.put("result", treeStr);
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");
		return mav;
	}

	@RequestMapping("/FM-COMPS003_comps_popup.do")
	public String fmComps003_comps_popup(@ModelAttribute("searchVO") SearchVO searchVO,
			ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {
		String uccSndCod = req.getParameter("uccSndCod");

		model.addAttribute("listComCodPrefix", fmCompsService.getComCodPrefix());
		model.addAttribute("uccSndCod", uccSndCod);
		return "FM-COMPS003_comps_popup";
	}

	/*
	 * 2016-10-15 시스템관리에서 이동 FM-SETUP021.do
	 */
	@RequestMapping(value = "/FM-COMPS006_RW.do")
	public String fmSetup021_rw(HttpServletRequest req, HttpServletResponse res,
			@RequestParam Map<String, String> paramMap, ModelMap model) throws Exception {

		EgovMap info = fmSetupService.fm_setup021_info(paramMap);
		model.addAttribute("info", info);

		paramMap.put("code", "DEPT");

		// model.addAttribute("mapList",
		// fmSetupService.fm_setup021_dept_list(paramMap));

		List savedDepartmentList = fmSetupService.fm_setup021_node_list(paramMap);

		model.addAttribute("deptList", savedDepartmentList);
		model.addAttribute("savedDepartmentListJson",
				commonUtil.getJsonFromObject(savedDepartmentList));
		model.addAttribute("paramMap", paramMap);

		return "FM-COMPS006-W";
	}

	@RequestMapping(value = "/FM-COMPS006.do")
	public String fmSetup021(@ModelAttribute("searchVO") SearchVO searchVO, HttpServletRequest req,
			ModelMap model) throws Exception {

		int currentYear = commonUtil.getCurrentYear();
		int selectedYear = searchVO.getYear() == null ? currentYear
				: Integer.parseInt(searchVO.getYear());
		searchVO.setBcyCode(commonUtil.yearToBcyCode(selectedYear));
		List<EgovMap> mainCycleList = commonService.getAllMainCycleList();

		// model.addAttribute("serviceList",
		// fmSetupService.fm_setup018_node_list("SERVICE"));

		model.addAttribute("resultList", fmSetupService.fm_setup021_list(searchVO));
		model.addAttribute("searchVO", searchVO);
		model.addAttribute("currentYear", currentYear);
		model.addAttribute("selectedYear", selectedYear);
		model.addAttribute("mainCycleList", mainCycleList);

		return "FM-COMPS006";
	}

	@RequestMapping(value = "/FM-COMPS006_U.do")
	public ModelAndView fmSetup021_u(HttpServletRequest req, HttpServletResponse res,
			@RequestParam Map<String, Object> map, ModelMap model) throws Exception {
		map.put("uccRgtId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		map.put("uomDepCod", req.getParameterValues("uomDepCod"));

		ModelAndView mav = new ModelAndView();
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		String strResult = fmSetupService.fm_setup018_update(map);

		if (strResult.equals("S")) {
			fmSetupService.fm_setup021_map_update(map);
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", strResult);
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");
		return mav;
	}

	/*
	 * 2016-10-27, 보호활동 엑셀 업로드
	 */
	@RequestMapping(value = "/FM-COMPS004_excel_popup.do")
	public String fmComps004_excel_popup(@RequestParam Map<String, String> map,
			HttpServletRequest req, ModelMap model) throws Exception {

		return "FM-COMPS004_excel_popup";
	}

	@RequestMapping("/FM-COMPS004_excelSave.do")
	public ModelAndView fmComps004_excelSave(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		ModelAndView mav = new ModelAndView();
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", fmCompsService.fm_comps004_excel_insert(req));
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");
		return mav;
	}

	/*
	 * 2017-02-16, 업무 일괄할당
	 */
	@RequestMapping("/FM-COMPS004_insert_work.do")
	public ModelAndView fmComps004_insertWork(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		ModelAndView mav = new ModelAndView();

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", fmCompsService.fm_comps004_insert_work(req));
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");
		return mav;
	}

	/*
	 * 2018-02-20, 업무 삭제
	 */
	@RequestMapping("/FM-COMPS004_del_doc.do")
	public ModelAndView fmComps004_del_doc(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		ModelAndView mav = new ModelAndView();

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", fmCompsService.fm_comps004_del_doc(req));
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");
		return mav;
	}

	/*
	 * 2018-03-21 s, 컴플라이인스 항목 삭제
	 */
	@RequestMapping("/FM-COMPS003_del_comps.do")
	public ModelAndView fmComps003_comps_delete(@RequestParam Map<String, String> map,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		ModelAndView mav = new ModelAndView();

		map.put("bcyCod", (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String stndKind = excelNewService.getStndKind(map.get("standard"));
		map.put("stndKind", stndKind);
		resultMap.put("result", fmCompsService.fm_comps003_del_comps(map));
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");
		return mav;
	}

	/*
	 * 2018-07-17 s, 업무 일괄 삭제
	 */
	@RequestMapping("/FM-COMPS_del_work.do")
	public ModelAndView fmComps_del_work(@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", fmCompsService.fm_comps_del_work(req));

		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping(value = "/FM-COMPS003_excelNew_popup.do")
	public String fmComps003_excelNew_popup(ModelMap model, HttpServletRequest req,
			HttpServletResponse res) {

		String currentCycleCode = (String) req.getSession()
				.getAttribute(CommonConfig.SES_MAN_CYL_KEY);
		List<EgovMap> complianceListPerService = fmCompsService
				.getComplianceListPerServiceByCycleCode(currentCycleCode);
		Map<String, List<EgovMap>> complianceMapPerService = new HashMap<>();

		for (EgovMap egovMap : complianceListPerService) {
			String serviceCode = (String) egovMap.get("serviceCode");
			String complianceName = (String) egovMap.get("complianceName");

			if (!complianceMapPerService.containsKey(serviceCode)) {
				List<EgovMap> complianceList = new ArrayList<>();
				complianceList.add(egovMap);
				complianceMapPerService.put(serviceCode, complianceList);
			} else {
				complianceMapPerService.get(serviceCode).add(egovMap);
			}
		}

		String sesAuth = (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);
		Map<String, String> map = new HashMap<String, String>();
		map.put("sesAuthKey", sesAuth);
		List<EgovMap> serviceList = (List<EgovMap>) commonCodeService.getServiceAuthList_N(map);

		for (EgovMap egovMap : serviceList) {
			if (complianceMapPerService.containsKey(egovMap.get("code"))) {
				egovMap.put("compliance", complianceMapPerService.get(egovMap.get("code")));
			}
		}

		model.addAttribute("compList", excelNewService.getCompList());
		model.addAttribute("resultList", serviceList);
		model.addAttribute("compliancePerService", complianceMapPerService);

		return "FM-COMPS003_excelNew_popup";
	}

	@RequestMapping(value = "/FM-COMPS003_excelNew_Upload.do")
	public ModelAndView fmComps003_excelNew_Upload(ModelMap model, HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		ModelAndView mav = new ModelAndView();
		Map<String, Object> resultMap = new HashMap<String, Object>();

		String standard = req.getParameter("standard");

		if (standard.equals(null) || standard.length() == 0) {
			resultMap.put("result", "컴플라이언스의 코드가 없습니다");
		} else {

			Map<String, Object> mapReqInfo = new HashMap<>();
			mapReqInfo.put("UCM_BCY_COD",
					req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
			mapReqInfo.put("UCM_RGT_ID", req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
			mapReqInfo.put("UCM_CTR_GBN", standard);
			mapReqInfo.put("service", req.getParameterValues("service"));

			String stndKind = String.valueOf(excelNewService.getStndKind(standard));
			// System.out.println(stndKind);

			String strResult = "";
			if (!stndKind.equals("default") && stndKind != "null") {
				Map<String, Object> result = excelNewService.uploadExcel(stndKind, req);
				if (result.get("result") == "success") {
					Map<String, Object> mapExcel = (Map<String, Object>) result.get("excel");
					strResult = excelNewService.fm_comps003_excelNew(stndKind, mapReqInfo,
							mapExcel);
				} else {
					strResult = Constants.RETURN_FAIL;
					resultMap.put("message", result.get("message"));
				}
			} else {
				strResult = excelNewService.fm_comps003_excelNew_D3(mapReqInfo, req);
			}
			resultMap.put("result", strResult);
		}
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/xml; charset=UTF-8");
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}
}
