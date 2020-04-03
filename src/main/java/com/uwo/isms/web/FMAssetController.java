package com.uwo.isms.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.uwo.isms.service.CommonCodeService;
import com.uwo.isms.service.FMAssetService;
import com.uwo.isms.util.PaginationUtil;
import com.uwo.isms.common.CommonConfig;
import com.uwo.isms.common.Constants;
import com.uwo.isms.domain.RiskVO;
import com.uwo.isms.domain.SearchVO;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
@RequestMapping("/asset")
public class FMAssetController {

	Logger log = LogManager.getLogger(FMSetupController.class);

	@Resource(name = "fmAssetService")
	private FMAssetService fmAssetService;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;

	@Resource(name = "commonCodeService")
	private CommonCodeService commonCodeService;

	@RequestMapping("/FM-ASSET001.do")
	public String fmAsset001(ModelMap model, HttpServletRequest req) throws Exception {

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("sesAuthKey", (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));
		paramMap.put("sesServiceKey", (String) req.getSession().getAttribute(CommonConfig.SES_SERVICE_KEY));
		paramMap.put("sesDeptKey", (String) req.getSession().getAttribute(CommonConfig.SES_DEPT_KEY));
		// 2016-06-20
		paramMap.put("upType", "A");

		model.addAttribute("assCatList", commonCodeService.getAssCatList(paramMap));
		model.addAttribute("assGbnList", commonCodeService.getAssGbnList());

		// 2016-11-30, ISC 연동 자산은 manCyl = 0
		model.addAttribute("assetManCyl", "0");

		return "FM-ASSET001";
	}

	/*
	 * 2016-11-30
	 * fmAsset001과 동일하며, manCyl 존재 유무로 구분
	 */
	@RequestMapping(value="/FM-ASSET003.do")
	public String fmAsset003 (ModelMap model, HttpServletRequest req) throws Exception {

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("sesAuthKey", (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));
		paramMap.put("sesServiceKey", (String) req.getSession().getAttribute(CommonConfig.SES_SERVICE_KEY));
		paramMap.put("sesDeptKey", (String) req.getSession().getAttribute(CommonConfig.SES_DEPT_KEY));
		// 2016-06-20
		paramMap.put("upType", "A");

		model.addAttribute("assCatList", commonCodeService.getAssCatList(paramMap));
		model.addAttribute("assGbnList", commonCodeService.getAssGbnList());

		// 2016-11-30, ISC 연동 자산은 manCyl = 0
		model.addAttribute("assetManCyl", (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

		return "FM-ASSET001";
	}

	/*
	 * 2016-11-30
	 * 심사 자산 확정
	 */
	@RequestMapping("/FM-ASSET003_asset_update.do")
	public ModelAndView fmAsset003_asset_update (@RequestParam Map<String, Object> map, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		String[] assKeys = req.getParameter("assKeys").split("\\,");

		// 기존 0: 반영: 현재 주기
		map.put("manCyl", (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		map.put("rgtId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		map.put("assKeys", assKeys);
		fmAssetService.fm_asset003_update(map);

		mav.setViewName("jsonView");

        return mav;
	}


	@RequestMapping("/FM-ASSET001_popup.do")
	public String fmAsset001_popup (@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		String assetKey = req.getParameter("uarAssKey");
        String mode = req.getParameter("mode");

        if(assetKey != ""){
        	model.addAttribute("info", fmAssetService.fm_asset001_assetCodeInfo(assetKey));
        }
        Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("sesAuthKey", (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));
		paramMap.put("sesServiceKey", (String) req.getSession().getAttribute(CommonConfig.SES_SERVICE_KEY));
		paramMap.put("sesDeptKey", (String) req.getSession().getAttribute(CommonConfig.SES_DEPT_KEY));
		paramMap.put("mode", mode);

		model.addAttribute("assCatList", commonCodeService.getAssCatListA());
		model.addAttribute("assGbnList", commonCodeService.getAssGbnList());
		model.addAttribute("mode", mode);

        return "FM-ASSET001_popup";
	}



	@RequestMapping("/FM-ASSET001_list.do")
	public ModelAndView fmAsset001_list (@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

        String company = propertyService.getString("company");
        searchVO.setCompany(company);

        if (searchVO.getManCyl() == null || searchVO.getManCyl() == "") {
        	searchVO.setManCyl((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
        }

        searchVO.setPageIndex(Integer.parseInt(req.getParameter("page")));
        searchVO.setPageUnit(Integer.parseInt(req.getParameter("rows")));

        int totCnt = fmAssetService.fm_asset001_cnt(searchVO);
        PaginationInfo paginationInfo = new PaginationUtil(searchVO, totCnt).getPageData();
        List<?> list = fmAssetService.fm_asset001_list(searchVO);

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

	@RequestMapping("/FM-ASSET001_CAT_LIST.do")
	public ModelAndView fmAsset001_cat_list (ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("uacCatKey", req.getParameter("uarAssCat").toString().split("\\|")[0]);
		resultMap.put("result", fmAssetService.fm_asset008_info(paramMap));
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping("/FM-ASSET001_assetCode_save.do")
	public ModelAndView fmAsset001_assetCode_save (@RequestParam Map<String, Object> map,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();
		int result = 1;

        map.put("uarBcyCod", (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

		if(map.get("uarAssKey").equals("")){
			map.put("uarRgtId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
			map.put("assCodGbn", Constants.ASS_COD_GBN);
			fmAssetService.fm_asset001_assetCode_insert(map);
		}else{
			map.put("uarUatId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
			fmAssetService.fm_asset001_assetCode_update(map);
		}

        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping("/FM-ASSET001_excel_popup.do")
	public String fmAsset001_excel_popup(ModelMap model, HttpServletRequest req) throws Exception {

		return "FM-ASSET001_excel_popup";
	}

	@RequestMapping(value="/FM-ASSET001_excelUpload.do")
	public ModelAndView fmAsset001_excelUpload(ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		ModelAndView mav = new ModelAndView();
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", fmAssetService.fmAsset001_excelUpload(req));
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;

	}

	@RequestMapping(value="/FM-ASSET001_excelSave.do")
	public String fmAsset001_excelSave(ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		model.addAttribute("result", fmAssetService.fmAsset001_excelSave(req));
		return "FM-ASSET001_excel_popup";

	}




	@RequestMapping(value="/FM-ASSET002.do")
	public String fmAsset002 (@ModelAttribute("searchVO") SearchVO searchVO,
			HttpServletRequest req, ModelMap model) throws Exception {

        model.addAttribute("assCatList", commonCodeService.getAssCatList());
        model.addAttribute("assGbnList", commonCodeService.getAssGbnList());

		return "FM-ASSET002";
	}

	@RequestMapping("/FM-ASSET002_list.do")
	public ModelAndView fmAsset002_list (@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

        searchVO.setManCyl((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

        List<?> list = fmAssetService.fm_asset002_list(searchVO);

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping("/FM-ASSET002_popup.do")
	public String fmAsset002_popup (@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		String groupKey = req.getParameter("uag_grp_key");
        String depCode = req.getParameter("uag_dep_cod");

        String mode = "update";
        if(groupKey == ""){
        	mode = "add";
        }
        else{

        	RiskVO vo = fmAssetService.fm_asset002_assetGroupInfo(groupKey);
        	model.addAttribute("riskVO", vo);
        }
        model.addAttribute("uag_grp_key", groupKey);
        model.addAttribute("uag_dep_cod", depCode);
        model.addAttribute("mode", mode);
        model.addAttribute("assCatList", commonCodeService.getAssCatList());

        return "FM-ASSET002_popup";
	}

	@RequestMapping("/FM-ASSET002_assetGroup_save.do")
	public ModelAndView fmAsset002_assetGroup_save (@ModelAttribute("riskVO") RiskVO riskVO,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		String mode = riskVO.getUag_grp_key();
		if(mode.equals("")){
			// insert
			riskVO.setUag_bcy_cod((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
			riskVO.setUag_rgt_id((String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
			fmAssetService.fm_asset002_assetGroup_insert(riskVO);
		}else{
			// update
			riskVO.setUag_upt_id((String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));

			log.info(riskVO.getUag_adm_id());
			log.info(riskVO.getUag_use_yn());
			log.info(riskVO.getUag_grp_des());
			fmAssetService.fm_asset002_assetGroup_update(riskVO);

			/*if(riskVO.getUrm_rsk_key().equals("")){
				if(riskVO.getUrm_del_yn().equals("N")){
					// 위험관리 insert
					riskVO.setUrm_rgt_id((String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
					fmAssetService.fm_asset002_riskGroup_insert(riskVO);
				}
			}else{
				// 위험관리 update
				riskVO.setUrm_upt_id((String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
				fmAssetService.fm_asset002_riskGroup_update(riskVO);
			}*/
		}

        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping("/FM-ASSET002_assetlistBelongG.do")
	public ModelAndView fmAsset002_assetlistBelongG (@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

        String company = propertyService.getString("company");
        searchVO.setCompany(company);

        searchVO.setManCyl((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

        List<?> list = fmAssetService.fm_asset002_assetlistBelongG(searchVO);

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping("/FM-ASSET002_assetlistNotBelongG.do")
	public ModelAndView fmAsset002_assetlistNotBelongG (@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

        String company = propertyService.getString("company");
        searchVO.setCompany(company);

        List<?> list = fmAssetService.fm_asset002_assetlistNotBelongG(searchVO);

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping("/FM-ASSET002_groupAsset_update.do")
	public ModelAndView fmAsset002_groupAsset_update (@ModelAttribute("searchVO") SearchVO searchVO,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		searchVO.setWorker((String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));

		fmAssetService.fm_asset002_groupAsset_update(searchVO);

        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping(value="/FM-ASSET006.do")
	public String fmAsset006 (@ModelAttribute("searchVO") SearchVO searchVO,
			HttpServletRequest req, ModelMap model) throws Exception {

        int totCnt = fmAssetService.getHistory_count(searchVO);
        model.addAttribute("paginationInfo", new PaginationUtil(searchVO, totCnt).getPageData());
		model.addAttribute("resultList", fmAssetService.getHistory(searchVO));


		return "FM-ASSET006";
	}

	@RequestMapping(value="/FM-ASSET006_getHistory.do")
	public ModelAndView fmSetup011_getAccountList (@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		searchVO.setManCyl((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

		List<?> resultList = fmAssetService.getHistory(searchVO);
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
		resultMap.put("resultList", resultList);
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}


	@RequestMapping(value="/FM-ASSET008.do")
	public String fmAsset008 (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		return "FM-ASSET008";
	}

	@RequestMapping("/FM-ASSET008_LIST.do")
	public ModelAndView fmAsset008_list (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();


        resultMap.put("result", fmAssetService.fm_asset008_list(map));
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping(value="/FM-ASSET008_popup.do")
	public String fmAsset008_popup (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		model.addAttribute("info", fmAssetService.fm_asset008_info(map));
		return "FM-ASSET008_popup";
	}

	@RequestMapping("/FM-ASSET008_W.do")
	public ModelAndView fmAsset008_add (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		map.put("uacRgtId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
        fmAssetService.fm_asset008_add(map);

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
        resultMap.put("result", "S");
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping("/FM-ASSET008_U.do")
	public ModelAndView fmAsset008_mod (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		map.put("uacUptId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
        fmAssetService.fm_asset008_mod(map);

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

        Map resultMap = new HashMap();
        resultMap.put("result", "S");
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}
}
