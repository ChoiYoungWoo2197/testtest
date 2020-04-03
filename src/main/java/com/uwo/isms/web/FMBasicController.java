/**
 ***********************************
 * @source FMBasicController.java
 * @date 2014. 10. 9.
 * @project isms3
 * @description 정보보호관리개요 controller
 ***********************************
 */
package com.uwo.isms.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.uwo.isms.service.FMBasicService;
import com.uwo.isms.util.EgovUserDetailsHelper;
import com.uwo.isms.util.FileUpload;
import com.uwo.isms.common.CommonConfig;
import com.uwo.isms.domain.BoardVO;
import com.uwo.isms.domain.FileVO;
import com.uwo.isms.domain.SearchVO;
import com.uwo.isms.domain.WorkVO;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
@RequestMapping("/basic")
public class FMBasicController {
	
	Logger log = LogManager.getLogger(FMSetupController.class);
	
	@Resource(name = "fmBasicService")
	private FMBasicService fmBasicService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;

	@Autowired
	private DefaultBeanValidator beanValidator;
	
	@RequestMapping(value = "/FM-BASIC001.do")
	public String fmBasic001(@ModelAttribute("searchVO") SearchVO searchVO,
			ModelMap model,final HttpServletRequest req) throws Exception {
		
		String menuAuth = "";
		/*
		 * 로그인 여부 체크(모든 액션에 처음 체크하기)
		 */
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			model.addAttribute("message", "faile");
			return "redirect:/index.jsp";
		}

		
		/** EgovPropertyService.sample */

		searchVO.setPageUnit(10);
		searchVO.setPageSize(100);

		/** pageing */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		searchVO.setSekAth((String)req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));
		
		List<?> list = fmBasicService.fm_basic001_list(searchVO);
		model.addAttribute("resultList", list);

		int totCnt = fmBasicService.fm_basic001_cnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);

		return "FM-BASIC001";
	}

	@RequestMapping("FM-BASIC001_V.do")
	public String FM_BASIC001_V(@RequestParam("selectedId") String id,
			Model model,final HttpServletRequest req) throws Exception {

		BoardVO vo = new BoardVO();
		vo.setUbm_brd_key(Integer.parseInt(id));
		FileVO fvo = new FileVO();
		fvo.setUmf_con_key(id);
		fvo.setUmf_tbl_gbn("BRD");
		fvo.setUmf_con_gbn("3");
		BoardVO rVo = fmBasicService.fm_basic001_read(vo);
		model.addAttribute("vo", rVo);
		
		int b_auth = Integer.parseInt(rVo.getUbm_dwn_ath());
		String s_auth = (String)req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);
		
		model.addAttribute("vo", rVo);
		//보드권한에 따른 파일리스트 출력여부
		if((s_auth.equals("A") || (s_auth.equals("P")&& b_auth > 1)) || ((s_auth.equals("N")&&b_auth > 2))){
			model.addAttribute("file", fmBasicService.fm_file(fvo));
		}
		
		return "/FM-BASIC001-V";
	}

	
	@RequestMapping(value = "/FM-BASIC002.do")
	public String fmBasic002(@ModelAttribute("searchVO") SearchVO searchVO,
			ModelMap model,final HttpServletRequest req) throws Exception {

		/*
		 * 로그인 여부 체크(모든 액션에 처음 체크하기)
		 */
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			model.addAttribute("message", "faile");
			return "redirect:/index.jsp";
		}

		/** EgovPropertyService.sample */

		searchVO.setPageUnit(10);
		searchVO.setPageSize(100);

		/** pageing */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		searchVO.setSekAth((String)req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));
		
		List<?> list = fmBasicService.fm_basic002_list(searchVO);
		model.addAttribute("resultList", list);

		int totCnt = fmBasicService.fm_basic002_cnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);

		return "FM-BASIC002";
	}

	@RequestMapping("FM-BASIC002_V.do")
	public String FM_BASIC002_V(@RequestParam("selectedId") String id,
			Model model,final HttpServletRequest req) throws Exception {

		BoardVO vo = new BoardVO();
		vo.setUbm_brd_key(Integer.parseInt(id));
		FileVO fvo = new FileVO();
		fvo.setUmf_con_key(id);
		fvo.setUmf_tbl_gbn("BRD");
		fvo.setUmf_con_gbn("4");
		BoardVO rVo = fmBasicService.fm_basic002_read(vo);
		model.addAttribute("vo", rVo);
		
		int b_auth = Integer.parseInt(rVo.getUbm_dwn_ath());
		String s_auth = (String)req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);
		
		model.addAttribute("vo", rVo);
		//보드권한에 따른 파일리스트 출력여부
		if((s_auth.equals("A") || (s_auth.equals("P")&& b_auth > 1)) || ((s_auth.equals("N")&&b_auth > 2))){
			model.addAttribute("file", fmBasicService.fm_file(fvo));
		}
		
		return "/FM-BASIC002-V";
	}
	
	@RequestMapping(value="/FM-BASIC003.do")
	public String fmBasic003 (@ModelAttribute("searchVO") SearchVO searchVO
			, ModelMap model,final HttpServletRequest req) throws Exception {		
		/*
		 * 로그인 여부 체크(모든 액션에 처음 체크하기)
		 */
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
        if(!isAuthenticated) {
	        model.addAttribute("message", "faile");
	        return "redirect:/index.jsp";
	    }
        
        searchVO.setManCyl((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
        if(searchVO.getStandard()==null || searchVO.getStandard().equals("")){
        	searchVO.setStandard("S03");
        }
        searchVO.setService((String)req.getSession().getAttribute(CommonConfig.SES_SERVICE_KEY));
        
		List<?> list = fmBasicService.fm_basic003_list(searchVO);
		List<?> stdlist = fmBasicService.fm_basic003_stdlist();	
        model.addAttribute("resultList", list);
        model.addAttribute("stdlist", stdlist);
        
		return "FM-BASIC003";
	}
	
	@RequestMapping("/FM-BASIC003_DetailList.do")
	public ModelAndView fmBasic003_DetailList(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		ModelAndView mav = new ModelAndView(); 
		
		/*
		 * 로그인 여부 체크(모든 액션에 처음 체크하기)
		 */
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
        if(!isAuthenticated) {
	        model.addAttribute("message", "faile");
	        
	        mav.setViewName(req.getContextPath()+"/index.jsp");
	    }
        String searchKeyword = req.getParameter("ctrKey");
        String standard = req.getParameter("standard");
        
        SearchVO searchVO = new SearchVO();
        searchVO.setSearchKeyword(searchKeyword);
        searchVO.setManCyl((String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
        searchVO.setService((String)req.getSession().getAttribute(CommonConfig.SES_SERVICE_KEY));
        searchVO.setStandard(standard);

        List<?> list = new ArrayList();
        List<?> merge = new ArrayList();
        list = fmBasicService.fmBasic003_DetailList(searchVO);
        merge = fmBasicService.fmBasic003_MergeList(searchVO);
                
        res.setCharacterEncoding("");
		res.setContentType("text/xml; charset="+"UTF-8");
		
		Map resultMap = new HashMap();
        resultMap.put("result", list);
        resultMap.put("merge", merge);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}
	
	@RequestMapping(value="/FM-BASIC_popup.do")
	public String fmMwork002_popup (ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		/*
		 * 로그인 여부 체크(모든 액션에 처음 체크하기)
		 */
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
        if(!isAuthenticated) {
	        model.addAttribute("message", "faile");
	        return "redirect:/index.jsp";
	    }
        
        String ucmCtrKey = req.getParameter("ucmCtrKey");        
        
        Map map = new HashMap();
        map.put("ucmCtrKey", ucmCtrKey);
        map.put("division", req.getSession().getAttribute(CommonConfig.SES_DIVISION_KEY));
        
        // 표준항목 상세내용
        List<?> cntrList = fmBasicService.getCntrList(map);
        
        model.addAttribute("cntrList", cntrList);
        model.addAttribute("ctrKey", ucmCtrKey);
        
		return "FM-BASIC003_popup";
	}

}
