/**
 ***********************************
 * @source FMRiskmController.java
 * @date 2014. 12. 11.
 * @project isms3
 * @description 위험관리 controller
 ***********************************
 */
package com.uwo.isms.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.uwo.isms.common.CommonConfig;
import com.uwo.isms.common.Constants;
import com.uwo.isms.domain.SearchVO;
import com.uwo.isms.service.CommonCodeService;
import com.uwo.isms.service.FMRiskmService;
import com.uwo.isms.util.PaginationUtil;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
@RequestMapping("/riskm")
public class FMRiskmController {

	Logger log = LogManager.getLogger(FMRiskmController.class);

	@Resource(name = "fmRiskmService")
	private FMRiskmService fmRiskmService;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;

	@Resource(name = "commonCodeService")
	private CommonCodeService commonCodeService;

	@RequestMapping(value="/FM-RISKM003.do")
	public String fmRiskm003 (@RequestParam Map<String, String> map,
			HttpServletRequest req, ModelMap model) throws Exception {

		// 2017-06-08
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("upType", "A");
        model.addAttribute("assCatList", commonCodeService.getAssCatList(paramMap));

        model.addAttribute("assGbnList", commonCodeService.getAssGbnList());
		model.addAttribute("authGbn", (String)req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));

		// 2017-07-03
		model.addAttribute("cat", (String) map.get("cat"));
		model.addAttribute("doaGrd", (String) map.get("doaGrd"));

		return "FM-RISKM003";
	}

	/*@RequestMapping(value="/FM-RISKM003_M.do")
	public String fmRiskm003_m (@ModelAttribute("searchVO") SearchVO searchVO,
			HttpServletRequest req, ModelMap model) throws Exception {

        model.addAttribute("assCatList", commonCodeService.getAssCatList());
		return "FM-RISKM003_M";
	}*/

	@RequestMapping("/FM-RISKM003_list.do")
	public ModelAndView fmRiskm003_list (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		map.put("bcyCod", (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		if(!"A".equals((String)req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY))){
			//map.put("ursMesMng", (String)req.getSession().getAttribute(CommonConfig.SES_USER_ID_KEY));
			map.put("ursMngKey", (String)req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		}
        resultMap.put("result", fmRiskmService.fm_riskm003_list(map));
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping("/FM-RISKM003_popup.do")
	public String fmRiskm003_popup (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

       	model.addAttribute("paramMap", fmRiskmService.fm_riskm003_rskInfo(map));
       	model.addAttribute("authGbn", req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));
        return "FM-RISKM003_popup";
	}

	@RequestMapping("/FM-RISKM003_M_popup.do")
	public String fmRiskm003_m_popup (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

        model.addAttribute("rskCocList", commonCodeService.getRiskCocList(Constants.MNG_CAT_KEY));

        map.put("authKey", (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));
       	model.addAttribute("info", fmRiskmService.fm_riskm003_rskInfo(map));
       	model.addAttribute("paramMap", map);
        return "FM-RISKM003_M_popup";
	}

	@RequestMapping("/FM-RISKM003_rskList.do")
	public ModelAndView fmRiskm003_detailList (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		map.put("bcyCod", (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		if(!"A".equals((String)req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY))){
			//map.put("ursMesMng", (String)req.getSession().getAttribute(CommonConfig.SES_USER_ID_KEY));
			map.put("ursMngKey", (String)req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		}
        resultMap.put("result", fmRiskmService.fm_riskm003_rskList(map));
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping("/FM-RISKM003_W.do")
	public ModelAndView fmRiskm003_w (@RequestParam Map<String, String> map,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();
		map.put("urgRgtId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		map.put("urgBcyCod", (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		map.put("urgDivCod", Constants.DIV_COD);
		fmRiskmService.fm_riskm003_rskm_insert(map);
        mav.setViewName("jsonView");
        return mav;
	}

	@RequestMapping("/FM-RISKM003_U.do")
	public ModelAndView fmRiskm003_u (@RequestParam Map<String, String> map,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();
		map.put("urgUptId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		map.put("authKey", (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));
		fmRiskmService.fm_riskm003_rskm_update(map);
        mav.setViewName("jsonView");
        return mav;
	}

	/*
	 * 2016-02-23 사용안함 확인
	 */
	/*@RequestMapping("/FM-RISKM003_excelSave.do")
	public ModelAndView fmRiskm003_excelSave(HttpServletRequest req, HttpServletResponse res) throws Exception {
		ModelAndView mav = new ModelAndView();
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", fmRiskmService.fm_riskm003_excel_insert(req));
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");
        return mav;
	}*/

	/*
	 * 2016-02-23 사용안함 확인
	 */
	/*@RequestMapping("/FM-RISKM003_etcExcelSave.do")
	public ModelAndView fmRiskm003_etcExcelSave (@RequestParam Map<String, String> map,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", fmRiskmService.fm_riskm003_etcExcel_insert(req));
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");
        return mav;
	}*/

	/*@RequestMapping(value="/FM-RISKM003_excel_popup.do")
	public String fmRiskm003_excel_popup (@RequestParam Map<String, String> map,
			HttpServletRequest req, ModelMap model) throws Exception {

        model.addAttribute("paramMap", map);
		return "FM-RISKM003_excel_popup";
	}*/

	@RequestMapping("/FM-RISKM003_grp_popup.do")
	public String fmRiskm003_grp_popup (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

        if(StringUtils.isEmpty(map.get("bcyCod"))) {
        	map.put("bcyCod", (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
        }

        map.put("grpKey", map.get("grpPopKey"));
        model.addAttribute("info", fmRiskmService.fm_riskm003_info(map));
        model.addAttribute("rskList", fmRiskmService.fm_riskm003_rskList(map));
        model.addAttribute("bcyList", fmRiskmService.fm_riskm003_bcyList(map));
        return "FM-RISKM003_grp_popup";
	}

	@RequestMapping("/FM-RISKM003_report_popup.do")
	public String fmRiskm003_report_popup (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		if(StringUtils.isEmpty(map.get("bcyCod"))) {
			map.put("bcyCod", (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		}

		model.addAttribute("svrList", fmRiskmService.fm_riskm003_svr_list());
		return "FM-RISKM003_report_popup";
	}

	@RequestMapping("/FM-RISKM003_batch_popup.do")
	public String fmRiskm003_batch_popup (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		model.addAttribute("uagGrpCod", req.getParameter("uagGrpCod"));
		model.addAttribute("uagGrpNam", req.getParameter("uagGrpNam"));
		model.addAttribute("doa", req.getParameter("doa"));

		return "FM-RISKM003_batch_popup";
	}

	@RequestMapping("/FM-RISKM003_batchJob.do")
	public ModelAndView fmRiskm003_batchJob (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		map.put("manCyl", (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		map.put("uptId", (String)req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		map.put("ursReqMdh", req.getParameter("ursReqMdh"));
		map.put("uagGrpCod", req.getParameter("uagGrpCod"));
		map.put("doa", req.getParameter("doa"));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		if (StringUtil.isEmpty(req.getParameter("ursReqMdh")) || StringUtil.isEmpty(req.getParameter("doa")) || StringUtil.isEmpty(req.getParameter("uagGrpCod"))) {
			resultMap.put("result", "fail");
		} else {
			fmRiskmService.fm_riskm003_update_ursStaCodS(map);
			resultMap.put("result", "true");
		}

		mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping("/FM-RISKM003_MNGSAVE.do")
	public ModelAndView fmRiskm003_mngSave (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");
		map.put("ursUptId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));

		map.put("ursMesMng", (String) map.get("urrId"));
		fmRiskmService.fmRiskm003_mngSave(map);
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		resultMap.put("paramMap", fmRiskmService.fm_riskm003_rskInfo(map));
//		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping("/FM-RISKM003_RSKREQ.do")
	public ModelAndView fmRiskm003_rskReq (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");
		map.put("ursUptId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));

		fmRiskmService.fmRiskm003_rskReq(map);
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		resultMap.put("paramMap", fmRiskmService.fm_riskm003_rskInfo(map));
//		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	/*
	 * 2017-04-05, 위험관리 임시저장
	 */
	@RequestMapping("/FM-RISKM003_RSKSAVE.do")
	public ModelAndView fmRiskm003_rskSave (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();
		map.put("ursUptId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		fmRiskmService.fmRiskm003_rskSave(map);

		mav.setViewName("jsonView");
		return mav;
	}

	@RequestMapping("/FM-RISKM003_RSKAPPR.do")
	public ModelAndView fmRiskm003_rskAppr (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");
		map.put("ursUptId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));

		fmRiskmService.fmRiskm003_rskAppr(map);
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		resultMap.put("paramMap", fmRiskmService.fm_riskm003_rskInfo(map));
//		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping("/FM-RISKM003_RSKREJ.do")
	public ModelAndView fmRiskm003_rskRej (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");
		map.put("ursUptId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		log.info(" map : " + map);

		fmRiskmService.fmRiskm003_rskRej(map);
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		resultMap.put("paramMap", fmRiskmService.fm_riskm003_rskInfo(map));
//		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping("/FM-RISKM004.do")
	public String fmRiskm004 (@RequestParam Map<String, Object> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

        model.addAttribute("assCatList", commonCodeService.getAssCatList());
        return "FM-RISKM004";
	}

	@RequestMapping("/FM-RISKM004_LIST.do")
	public ModelAndView fmRiskm004_list (@RequestParam Map<String, Object> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
        ModelAndView mav = new ModelAndView();

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		map.put("bcyCod", (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
        resultMap.put("result", fmRiskmService.fm_riskm004_list(map));
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");
        return mav;
	}

	@RequestMapping("/FM-RISKM004_popup.do")
	public String fmRiskm004_popup (ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

        String usoCocKey = req.getParameter("usoCocKey");
        if(!StringUtil.isEmpty(usoCocKey)) {
        	model.addAttribute("info", fmRiskmService.fm_riskm004_info(usoCocKey));
        }
        model.addAttribute("assCatList", commonCodeService.getAssCatList());
        return "FM-RISKM004_popup";
	}

	@RequestMapping("/FM-RISKM004_W.do")
	public ModelAndView fmRiskm004_add (@RequestParam Map<String, Object> map,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();
		map.put("usoRgtId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		map.put("usoBcyCod", (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
    	fmRiskmService.fm_riskm004_insert(map);
    	mav.setViewName("jsonView");
        return mav;
	}

	@RequestMapping("/FM-RISKM004_U.do")
	public ModelAndView fmRiskm004_mod (@RequestParam Map<String, Object> map,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		map.put("usoUptId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		fmRiskmService.fm_riskm004_update(map);
        mav.setViewName("jsonView");
        return mav;
	}

	@RequestMapping("/FM-RISKM005.do")
	public String fmRiskm005 (@RequestParam Map<String, Object> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

        return "FM-RISKM005";
	}

	@RequestMapping("/FM-RISKM005_LIST.do")
	public ModelAndView fmRiskm005_list (@RequestParam Map<String, Object> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
        ModelAndView mav = new ModelAndView();

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		map.put("bcyCod", (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
        resultMap.put("result", fmRiskmService.fm_riskm005_list(map));
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");
        return mav;
	}

	@RequestMapping("/FM-RISKM005_popup.do")
	public String fmRiskm005_popup (ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

        String usmSroKey = req.getParameter("usmSroKey");
        if(!StringUtil.isEmpty(usmSroKey)) {
        	model.addAttribute("info", fmRiskmService.fm_riskm005_info(usmSroKey));
        }
        return "FM-RISKM005_popup";
	}

	@RequestMapping("/FM-RISKM005_W.do")
	public ModelAndView fmRiskm005_add (@RequestParam Map<String, Object> map,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		map.put("usmRgtId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		map.put("usmBcyCod", (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
    	fmRiskmService.fm_riskm005_insert(map);
    	mav.setViewName("jsonView");
        return mav;
	}

	@RequestMapping("/FM-RISKM005_U.do")
	public ModelAndView fmRiskm005_mod (@RequestParam Map<String, Object> map,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();
		map.put("usmUptId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		fmRiskmService.fm_riskm005_update(map);
        mav.setViewName("jsonView");
        return mav;
	}

	@RequestMapping(value="/FM-RISKM006.do")
	public String fmRiskm006 (@ModelAttribute("searchVO") SearchVO searchVO,
			HttpServletRequest req, ModelMap model) throws Exception {

		Map<String, String> paramMap = new HashMap<String, String>();
		// 2016-10-05
		paramMap.put("upType", "A");

        model.addAttribute("assCatList", commonCodeService.getAssCatList(paramMap));
        model.addAttribute("assGbnList", commonCodeService.getAssGbnList());
		return "FM-RISKM006";
	}

	@RequestMapping("/FM-RISKM006_list.do")
	public ModelAndView fmRiskm006_list (@RequestParam Map<String, String> map,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		SearchVO searchVO = new SearchVO();
        searchVO.setPageIndex(Integer.parseInt(req.getParameter("page")));
        searchVO.setPageUnit(Integer.parseInt(req.getParameter("rows")));

        map.put("ummManCyl", (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

        int totCnt = fmRiskmService.fm_riskm006_cnt(map);
        PaginationInfo paginationInfo = new PaginationUtil(searchVO, totCnt).getPageData();

        map.put("firstIndex", String.valueOf(searchVO.getFirstIndex()));
        map.put("recordCountPerPage", String.valueOf(searchVO.getRecordCountPerPage()));
        List<?> list = fmRiskmService.fm_riskm006_list(map);

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

	@RequestMapping(value="/FM-RISKM006_excel_popup.do")
	public String fmRiskm006_excel_popup (@RequestParam Map<String, String> map,
			HttpServletRequest req, ModelMap model) throws Exception {

        model.addAttribute("assCatList", commonCodeService.getAssCatList(map));
        model.addAttribute("assGbnList", commonCodeService.getAssGbnList());
        //model.addAttribute("vlbList", commonCodeService.getVlbList());
        model.addAttribute("paramMap", map);
		return "FM-RISKM006_excel_popup";
	}

	@RequestMapping(value="/FM-RISKM006_excel_down_popup.do")
	public String fmRiskm006_excel_down_popup (@RequestParam Map<String, String> map,
			HttpServletRequest req, ModelMap model) throws Exception {

		model.addAttribute("assCatList", commonCodeService.getAssCatList());
		model.addAttribute("assGbnList", commonCodeService.getAssGbnList());
		model.addAttribute("paramMap", map);
		return "FM-RISKM006_excel_down_popup";
	}

	@RequestMapping("/FM-RISKM006_excelSave.do")
	public ModelAndView fmRiskm006_excelSave(HttpServletRequest req, HttpServletResponse res) throws Exception {
		ModelAndView mav = new ModelAndView();
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", fmRiskmService.fm_riskm006_excel_insert(req));
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");
        return mav;
	}


	@RequestMapping("/FM-RISKM006_checkExcelSave.do")
	public ModelAndView fmRiskm006_checkExcelSave(HttpServletRequest req, HttpServletResponse res) throws Exception {
		ModelAndView mav = new ModelAndView();
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		//resultMap.put("assetNotFound", fmRiskmService.fm_riskm006_excel_xlsx_asset_exists(req));
		resultMap.put("riskExists", fmRiskmService.fm_riskm006_excel_exists(req));
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");
        return mav;
	}

	@RequestMapping("/FM-RISKM006_etcExcelSave.do")
	public ModelAndView fmRiskm006_etcExcelSave (@RequestParam Map<String, String> map,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", fmRiskmService.fm_riskm006_etcExcel_insert(req));
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");
        return mav;
	}

	@RequestMapping(value="/FM-RISKM006_detail_popup.do")
	public String fmRiskm006_detail_popup (@RequestParam Map<String, String> map,
			HttpServletRequest req, ModelMap model) throws Exception {

		model.addAttribute("paramMap", map);
		model.addAttribute("uarAssCod", req.getParameter("uarAssCod"));
//		model.addAttribute("result", fmRiskmService.fm_riskm006_rst_list(map));
		return "FM-RISKM006_detail_popup";
	}

	@RequestMapping("/FM-RISKM006_rst_list")
	public ModelAndView fmRiskm006_rst_list (@RequestParam Map<String, String> map,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		ModelAndView mav = new ModelAndView();
		//map.put("usmUptId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		map.put("ummManCyl", (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		resultMap.put("result", fmRiskmService.fm_riskm006_rst_list(map));
		mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");
        return mav;
	}

	@RequestMapping("/FM-RISKM007.do")
	public String fmRiskm007 (@RequestParam Map<String, Object> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

        model.addAttribute("vlbList", commonCodeService.getVlbList());
        return "FM-RISKM007";
	}

	@RequestMapping("/FM-RISKM007_LIST.do")
	public ModelAndView fmRiskm007_list (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
        ModelAndView mav = new ModelAndView();

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		map.put("bcyCod", (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
        resultMap.put("result", fmRiskmService.fm_riskm007_list(map));
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");
        return mav;
	}

	@RequestMapping("/FM-RISKM007_popup.do")
	public String fmRiskm007_popup (ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

        String urvRskKey = req.getParameter("urvRskKey");
        if(!StringUtil.isEmpty(urvRskKey)) {
        	model.addAttribute("info", fmRiskmService.fm_riskm007_info(urvRskKey));
        }
        model.addAttribute("vlbList", commonCodeService.getVlbList());
        model.addAttribute("vlbImpList", commonCodeService.getVlbImpList());
        return "FM-RISKM007_popup";
	}

	@RequestMapping("/FM-RISKM007_W.do")
	public ModelAndView fmRiskm007_add (@RequestParam Map<String, Object> map,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		map.put("rgtId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
    	fmRiskmService.fm_riskm007_insert(map);
    	mav.setViewName("jsonView");
        return mav;
	}

	@RequestMapping("/FM-RISKM007_U.do")
	public ModelAndView fmRiskm007_mod (@RequestParam Map<String, Object> map,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();
		map.put("uptId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		fmRiskmService.fm_riskm007_update(map);
        mav.setViewName("jsonView");
        return mav;
	}

	@RequestMapping("/FM_RISKM007_cocListPopup.do")
	public String fmRiskm007_cocListPopup (ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, String> param = new HashMap<String,String>();

        param.put("usoCocCod", req.getParameter("usoCocCod"));
	    param.put("usoCocNam", req.getParameter("usoCocNam"));
        model.addAttribute("cocList", fmRiskmService.getCocList(param));
        return "FM-RISKM007_cocListPopup";
	}

	@RequestMapping("/FM-RISKM007_COCLIST.do")
	public ModelAndView fmRiskm007_coclist (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
        ModelAndView mav = new ModelAndView();

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", fmRiskmService.getCocList(map));
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");
        return mav;
	}

	@RequestMapping(value="/FM-RISKM008.do")
	public String fmRiskm008 (@ModelAttribute("searchVO") SearchVO searchVO,
			HttpServletRequest req, ModelMap model) throws Exception {

		return "FM-RISKM008";
	}

	@RequestMapping("/FM-RISKM008_LIST.do")
	public ModelAndView fmRiskm008_list (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		map.put("ummManCyl", (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
        resultMap.put("result", fmRiskmService.fm_riskm008_list(map));
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping("/FM-RISKM008_Dep_Popup.do")
	public String fmRiskm008_dep_popup (ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, String> param = new HashMap<String,String>();

//		param.put("udmDepCod", req.getParameter("udmDepCod"));
//		param.put("udmDepNam", req.getParameter("udmDepNam"));
//		model.addAttribute("cocList", fmRiskmService.fm_riskm008_dep_list(param));
        return "FM-RISKM008_Dep_Popup";
	}

	@RequestMapping("/FM-RISKM008_dep_list.do")
	public ModelAndView fmRiskm008_dep_list (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		ModelAndView mav = new ModelAndView();

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

        resultMap.put("result", fmRiskmService.fm_riskm008_dep_list(map));
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping("/FM-RISKM008_addRskDepList.do")
	public ModelAndView fmRiskm008_addRskDepList (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		ModelAndView mav = new ModelAndView();

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");
		String depListStr = map.get("addList");
		StringTokenizer depSt = new StringTokenizer(depListStr, ",");
		List<Map<String,String>> depList = new ArrayList<Map<String,String>>();
		String manCylKey = (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY);
		String userKey = (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY);
		while(depSt.hasMoreTokens()){
			Map<String,String> depMap = new HashMap<String, String>();
			depMap.put("depCod", depSt.nextToken());
			depMap.put("ummManCyl", manCylKey);
			depMap.put("userKey", userKey);
			depList.add(depMap);
		}

		fmRiskmService.fmRiskm008_addRskDepList(depList);
        mav.setViewName("jsonView");
        return mav;
	}

	@RequestMapping("/FM-RISKM008_delRskDepList.do")
	public ModelAndView fmRiskm008_delRskDepList (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");
		String depListStr = map.get("delList");
		StringTokenizer depSt = new StringTokenizer(depListStr, ",");
		List<Map<String,String>> depList = new ArrayList<Map<String,String>>();
		while(depSt.hasMoreTokens()){
			Map<String,String> depMap = new HashMap<String, String>();
			depMap.put("urdDepKey", depSt.nextToken());
			depList.add(depMap);
		}
		fmRiskmService.fmRiskm008_delRskDepList(depList);
		mav.setViewName("jsonView");
		return mav;
	}

	@RequestMapping("/FM-RISKM008_mng_popup.do")
	public String fmRiskm008_mng_popup (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		model.addAttribute("depKey", req.getParameter("depKey"));
		model.addAttribute("depCod", req.getParameter("depCod"));

		return "FM-RISKM008_mng_popup";
	}


	@RequestMapping("/FM-RISKM010_updateRskDepMng.do")
	public ModelAndView fmRiskm010_updateRskDepMng (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		map.put("uptId", (String)req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		map.put("usrKey", req.getParameter("usrKey"));
		map.put("depKey", req.getParameter("depKey"));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		if (StringUtil.isEmpty(req.getParameter("usrKey")) || StringUtil.isEmpty(req.getParameter("depKey"))) {
			resultMap.put("result", "fail");
		} else {
			fmRiskmService.fm_riskm008_update_rskDepMng(map);
			resultMap.put("result", "true");
		}

		mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}


	@RequestMapping(value="/FM-RISKM009.do")
	public String fmRiskm009 (@ModelAttribute("searchVO") SearchVO searchVO,
			HttpServletRequest req, ModelMap model) throws Exception {

		return "FM-RISKM009";
	}

	@RequestMapping("/FM-RISKM009_list.do")
	public ModelAndView fmRiskm009_list (@RequestParam Map<String, String> map,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		ModelAndView mav = new ModelAndView();
		//map.put("usmUptId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		map.put("ummManCyl", (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		resultMap.put("result", fmRiskmService.fm_riskm009_list(map));
		mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");
        return mav;
	}

	@RequestMapping(value="/FM-RISKM009_detail_popup.do")
	public String fmRiskm009_detail_popup (@RequestParam Map<String, String> map,
			HttpServletRequest req, ModelMap model) throws Exception {

		model.addAttribute("paramMap", map);
		model.addAttribute("udmDepCod", req.getParameter("udmDepCod"));
		log.info("udmDepCod " + req.getParameter("udmDepCod"));
		return "FM-RISKM009_detail_popup";
	}

	@RequestMapping("/FM-RISKM009_rst_list")
	public ModelAndView fmRiskm009_rst_list (@RequestParam Map<String, String> map,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		ModelAndView mav = new ModelAndView();
		log.info("map :  " + map.get("udmDepCod"));
		//map.put("usmUptId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		map.put("ummManCyl", (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		resultMap.put("result", fmRiskmService.fm_riskm009_rst_list(map));
		mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");
        return mav;
	}


	@RequestMapping("/FM-RISKM009_excel_popup.do")
	public String fmRiskm009_excel_popup (@RequestParam Map<String, String> map,
			HttpServletRequest req, ModelMap model) throws Exception {

        model.addAttribute("assCatList", commonCodeService.getAssCatList(map));
        model.addAttribute("paramMap", map);
		return "FM-RISKM009_excel_popup";
	}

	@RequestMapping("/FM-RISKM009_excelSave.do")
	public ModelAndView fmRiskm009_excelSave(HttpServletRequest req, HttpServletResponse res) throws Exception {
		ModelAndView mav = new ModelAndView();
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", fmRiskmService.fm_riskm009_excel_insert(req));
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");
        return mav;
	}

	@RequestMapping(value="/FM-RISKM009_excel_down_popup.do")
	public String fmRiskm009_excel_down_popup (@RequestParam Map<String, String> map,
			HttpServletRequest req, ModelMap model) throws Exception {

		model.addAttribute("paramMap", map);
		return "FM-RISKM009_excel_down_popup";
	}

	@RequestMapping(value="/FM-RISKM010.do")
	public String fmRiskm010 (@RequestParam Map<String, String> map,
			HttpServletRequest req, ModelMap model) throws Exception {

		model.addAttribute("authGbn", (String)req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));
        model.addAttribute("doa", commonCodeService.getDoa());
        model.addAttribute("assCatList", commonCodeService.getAssCatList());

        // 2017-07-03
     	model.addAttribute("doaGrd", (String) map.get("doaGrd"));

        return "FM-RISKM010";
	}

	@RequestMapping("/FM-RISKM010_list.do")
	public ModelAndView fmRiskm010_list (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		map.put("cylKey", (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if("P".equals((String)req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY))){
			map.put("ursMngKey", (String)req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		}

		resultMap.put("result", fmRiskmService.fmRiskm010_list(map));
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping("/FM-RISKM010_rskList.do")
	public ModelAndView fmRiskm010_detailList (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		map.put("cylKey", (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if("P".equals((String)req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY))){
			map.put("ursMngKey", (String)req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		}

        resultMap.put("result", fmRiskmService.fm_riskm010_rskList(map));
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}


	@RequestMapping("/FM-RISKM010_popup.do")
	public String fmRiskm010_popup (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		if ("P".equals((String)req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY))) {
			model.addAttribute("urrRskKey", map.get("urrRskKey"));
		}

		EgovMap result = fmRiskmService.fm_riskm010_rskInfo(map);

		model.addAttribute("paramMap", result);
		model.addAttribute("authGbn", req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));
		model.addAttribute("grpKey", map.get("grpKey"));

		map.put("ursRskKey", result.get("ursRskKey").toString());
		model.addAttribute("depList", fmRiskmService.fm_riskm010_urrDepList(map));

		if ("P".equals((String)req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY)) ){
			//model.addAttribute("urrRskKey", fmRiskmService.fm_riskm010_urrRskKey(map));
			model.addAttribute("urrRskKey", map.get("urrRskKey"));
		}

		return "FM-RISKM010_popup";
	}

	@RequestMapping("/FM-RISKM010_urrDetail.do")
	public ModelAndView fmRiskm010_urrDetail (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();

		map.put("urrRskKey", req.getParameter("urrRskKey"));
		resultMap.put("result", fmRiskmService.fm_riskm010_urrDetail(map));

		mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}


	@RequestMapping("/FM-RISKM010_report_popup.do")
	public String fmRiskm010_report_popup (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		if(StringUtils.isEmpty(map.get("bcyCod"))) {
			map.put("bcyCod", (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		}

		model.addAttribute("svrList", fmRiskmService.fm_riskm003_svr_list());
		return "FM-RISKM010_report_popup";
	}

	@RequestMapping("/FM-RISKM010_batch_popup.do")
	public String fmRiskm010_batch_popup (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		model.addAttribute("uagSvrCod", req.getParameter("uagSvrCod"));
		model.addAttribute("uagSvrNam", req.getParameter("uagSvrNam"));
		model.addAttribute("doa", req.getParameter("doa"));

		return "FM-RISKM010_batch_popup";
	}

	@RequestMapping("/FM-RISKM010_batchJob.do")
	public ModelAndView fmRiskm010_batchJob (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		map.put("manCyl", (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		map.put("uptId", (String)req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		map.put("ursReqMdh", req.getParameter("ursReqMdh"));
		map.put("uagSvrCod", req.getParameter("uagSvrCod"));
		map.put("doa", req.getParameter("doa"));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		if (StringUtil.isEmpty(req.getParameter("ursReqMdh")) || StringUtil.isEmpty(req.getParameter("doa")) || StringUtil.isEmpty(req.getParameter("uagSvrCod"))) {
			resultMap.put("result", "fail");
		} else {
			fmRiskmService.fm_riskm010_update_urrStaCodS(map);
			resultMap.put("result", "true");
		}

		mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping("/FM-RISKM010_MNGSAVE.do")
	public ModelAndView fmRiskm010_mngSave (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");
		map.put("uptId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		fmRiskmService.fmRiskm010_mngSave(map);

		mav.setViewName("jsonView");
		return mav;
	}

	@RequestMapping("/FM-RISKM010_RSKREQ.do")
	public ModelAndView fmRiskm010_rskReq (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");
		map.put("uptId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));

		fmRiskmService.fmRiskm010_rskReq(map);

		mav.setViewName("jsonView");
		return mav;
	}

	/*
	 * 2017-03-16, 위험관리 임시저장
	 */
	@RequestMapping("/FM-RISKM010_RSKSAVE.do")
	public ModelAndView fmRiskm010_rskSave (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();
		map.put("uptId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		fmRiskmService.fmRiskm010_rskSave(map);

		mav.setViewName("jsonView");
		return mav;
	}

	@RequestMapping("/FM-RISKM010_RSKAPPR.do")
	public ModelAndView fmRiskm010_rskAppr (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");
		map.put("uptId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));

		fmRiskmService.fmRiskm010_rskAppr(map);

		mav.setViewName("jsonView");
		return mav;
	}

	@RequestMapping("/FM-RISKM010_RSKREJ.do")
	public ModelAndView fmRiskm010_rskRej (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");
		map.put("uptId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		log.info(" map : " + map);

		fmRiskmService.fmRiskm010_rskRej(map);

		mav.setViewName("jsonView");
		return mav;
	}
}
