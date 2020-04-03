/**
 ***********************************
 * @source FMStateController.java
 * @date 2014. 10. 9.
 * @project isms3
 * @description 종합현황판 controller
 ***********************************
 */
package com.uwo.isms.web;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uwo.isms.service.ExcelNewService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.uwo.isms.service.CommonCodeService;
import com.uwo.isms.service.FMStateService;
import com.uwo.isms.common.CommonConfig;
import com.uwo.isms.domain.SearchVO;

import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
@RequestMapping("/state")
public class FMStateController {

	Logger log = LogManager.getLogger(FMSetupController.class);

	@Resource(name = "fmStateService")
	private FMStateService fmStateService;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;

    @Resource(name = "excelNewService")
    ExcelNewService excelNewService;

	@Autowired
	private CommonCodeService commonCodeService;

	@RequestMapping(value="/FM-STATE001.do")
	public String fmState001 (@ModelAttribute("searchVO") SearchVO searchVO
			, ModelMap model, HttpServletRequest req) throws Exception {

        String auth = (String)req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);

        List<?> divlist = fmStateService.divlist();
        model.addAttribute("divlist", divlist);
        model.addAttribute("auth", auth);

		return "FM-STATE001";
	}

	@RequestMapping("/FM-STATE001_BOARD_LIST_1.do")
	public ModelAndView fmSTATE001_BOARD_LIST_1(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

        SearchVO searchVO = new SearchVO();
        searchVO.setManCyl((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
        searchVO.setCompany(propertyService.getString("company"));
        String auth = (String)req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);
        if(auth.equals("A")){
        	searchVO.setDivision(req.getParameter("divCod"));
        }else{
        	searchVO.setDivision((String)req.getSession().getAttribute(CommonConfig.SES_DIVISION_KEY));
        	searchVO.setDept((String)req.getSession().getAttribute(CommonConfig.SES_DEPT_KEY));
        }
        searchVO.setSearchKeyword(req.getParameter("cycle"));

        List<?> list = new ArrayList();
        list = fmStateService.fmSTATE001_BOARD_LIST_1(searchVO);

        res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping("/FM-STATE001_BOARD_LIST_1_2.do")
	public ModelAndView fmSTATE001_BOARD_LIST_1_2(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

        SearchVO searchVO = new SearchVO();
        searchVO.setManCyl((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
        searchVO.setCompany(propertyService.getString("company"));
        searchVO.setDept(req.getParameter("depCod"));
        String auth = (String)req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);
        if(auth.equals("A")){
        	searchVO.setDivision(req.getParameter("divCod"));
        }else{
        	searchVO.setDivision((String)req.getSession().getAttribute(CommonConfig.SES_DIVISION_KEY));
        	searchVO.setDept((String)req.getSession().getAttribute(CommonConfig.SES_DEPT_KEY));
        }
        searchVO.setSearchKeyword(req.getParameter("cycle"));

        List<?> list = new ArrayList();
        list = fmStateService.fmSTATE001_BOARD_LIST_1_2(searchVO);

        res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping("/FM-STATE001_BOARD_LIST_2.do")
	public ModelAndView fmSTATE001_BOARD_LIST_2(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

        SearchVO searchVO = new SearchVO();
        searchVO.setManCyl((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
        searchVO.setCompany(propertyService.getString("company"));
        String auth = (String)req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);
        if(auth.equals("A")){
        	searchVO.setDivision(req.getParameter("divCod"));
        }else{
        	searchVO.setDivision((String)req.getSession().getAttribute(CommonConfig.SES_DIVISION_KEY));
        	searchVO.setDept((String)req.getSession().getAttribute(CommonConfig.SES_DEPT_KEY));
        }
        searchVO.setSearchKeyword(req.getParameter("cycle"));
        List<?> list = new ArrayList();
        list = fmStateService.fmSTATE001_BOARD_LIST_2(searchVO);

        res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping("/FM-STATE001_BOARD_LIST_2_2.do")
	public ModelAndView fmSTATE001_BOARD_LIST_2_2(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

        SearchVO searchVO = new SearchVO();
        searchVO.setManCyl((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
        searchVO.setCompany(propertyService.getString("company"));
        searchVO.setDept(req.getParameter("depCod"));
        String auth = (String)req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);
        if(auth.equals("A")){
        	searchVO.setDivision(req.getParameter("divCod"));
        }else{
        	searchVO.setDivision((String)req.getSession().getAttribute(CommonConfig.SES_DIVISION_KEY));
        	searchVO.setDept((String)req.getSession().getAttribute(CommonConfig.SES_DEPT_KEY));
        }
        searchVO.setSearchKeyword(req.getParameter("cycle"));

        List<?> list = new ArrayList();
        list = fmStateService.fmSTATE001_BOARD_LIST_2_2(searchVO);

        res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping("/FM-STATE001_BOARD_LIST_3.do")
	public ModelAndView fmSTATE001_BOARD_LIST_3(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

        SearchVO searchVO = new SearchVO();
        searchVO.setManCyl((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
        searchVO.setCompany(propertyService.getString("company"));
        String auth = (String)req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);
        if(auth.equals("A")){
        	searchVO.setDivision(req.getParameter("divCod"));
        }else{
        	searchVO.setDivision((String)req.getSession().getAttribute(CommonConfig.SES_DIVISION_KEY));
        	searchVO.setDept((String)req.getSession().getAttribute(CommonConfig.SES_DEPT_KEY));
        }
        searchVO.setSearchKeyword(req.getParameter("cycle"));
        List<?> list = new ArrayList();
        list = fmStateService.fmSTATE001_BOARD_LIST_3(searchVO);

        res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping("/FM-STATE001_BOARD_LIST_3_2.do")
	public ModelAndView fmSTATE001_BOARD_LIST_3_2(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

        SearchVO searchVO = new SearchVO();
        searchVO.setManCyl((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
        searchVO.setCompany(propertyService.getString("company"));
        searchVO.setDept(req.getParameter("depCod"));
        String auth = (String)req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);
        if(auth.equals("A")){
        	searchVO.setDivision(req.getParameter("divCod"));
        }else{
        	searchVO.setDivision((String)req.getSession().getAttribute(CommonConfig.SES_DIVISION_KEY));
        	searchVO.setDept((String)req.getSession().getAttribute(CommonConfig.SES_DEPT_KEY));
        }
        searchVO.setSearchKeyword(req.getParameter("cycle"));

        List<?> list = new ArrayList();
        list = fmStateService.fmSTATE001_BOARD_LIST_3_2(searchVO);

        res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping("/FM-STATE001_BOARD_LIST_4.do")
	public ModelAndView fmSTATE001_BOARD_LIST_4(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

        SearchVO searchVO = new SearchVO();
        searchVO.setManCyl((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
        searchVO.setCompany(propertyService.getString("company"));
        String auth = (String)req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);
        if(auth.equals("A")){
        	searchVO.setDivision(req.getParameter("divCod"));
        }else{
        	searchVO.setDivision((String)req.getSession().getAttribute(CommonConfig.SES_DIVISION_KEY));
        	searchVO.setDept((String)req.getSession().getAttribute(CommonConfig.SES_DEPT_KEY));
        }
        searchVO.setSearchKeyword(req.getParameter("cycle"));
        List<?> list = new ArrayList();
        list = fmStateService.fmSTATE001_BOARD_LIST_4(searchVO);

        res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping("/FM-STATE001_BOARD_LIST_4_2.do")
	public ModelAndView fmSTATE001_BOARD_LIST_4_2(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

        SearchVO searchVO = new SearchVO();
        searchVO.setManCyl((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
        searchVO.setCompany(propertyService.getString("company"));
        searchVO.setDept(req.getParameter("depCod"));
        String auth = (String)req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);
        if(auth.equals("A")){
        	searchVO.setDivision(req.getParameter("divCod"));
        }else{
        	searchVO.setDivision((String)req.getSession().getAttribute(CommonConfig.SES_DIVISION_KEY));
        	searchVO.setDept((String)req.getSession().getAttribute(CommonConfig.SES_DEPT_KEY));
        }
        searchVO.setSearchKeyword(req.getParameter("cycle"));

        List<?> list = new ArrayList();
        list = fmStateService.fmSTATE001_BOARD_LIST_4_2(searchVO);

        res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	/*
	 * 2016-10-19
	 * mwork 로 이동
	@RequestMapping(value="/FM-STATE002.do")
	public String fmState002 (@ModelAttribute("searchVO") SearchVO searchVO
			, ModelMap model, HttpServletRequest req) throws Exception {

		return "FM-STATE002";
	}
	*/

	@RequestMapping("/FM-STATE002_STD_LIST.do")
	public ModelAndView fmSTATE002_STD_LIST(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

        SearchVO searchVO = new SearchVO();
        searchVO.setService(req.getParameter("service"));
        searchVO.setAuth(req.getParameter("auth"));
        searchVO.setDept(req.getParameter("dept"));
        searchVO.setManCyl(req.getParameter("axis"));

        List<?> list = new ArrayList();
        list = fmStateService.fmSTATE002_STD_LIST(searchVO);

        res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping("/FM-STATE002_DEPT_LIST.do")
	public ModelAndView fmSTATE002_DEPT_LIST(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

        SearchVO searchVO = new SearchVO();
        searchVO.setService(req.getParameter("service"));
        searchVO.setAuth(req.getParameter("auth"));
        searchVO.setDept(req.getParameter("dept"));
        searchVO.setManCyl(req.getParameter("axis"));
        searchVO.setStandard(req.getParameter("scale"));

        List<?> list = new ArrayList();
        list = fmStateService.fmSTATE002_DEPT_LIST(searchVO);

        res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping("/FM-STATE002_USER_LIST.do")
	public ModelAndView fmSTATE002_USER_LIST(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

        SearchVO searchVO = new SearchVO();
        searchVO.setService(req.getParameter("service"));
        searchVO.setDept(req.getParameter("dep"));
        searchVO.setManCyl(req.getParameter("axis"));
        searchVO.setStandard(req.getParameter("scale"));

        List<?> list = new ArrayList();
        list = fmStateService.fmSTATE002_USER_LIST(searchVO);

        res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping("/FM-STATE002_popup.do")
	public String fmSTATE002_POP(@ModelAttribute("searchVO") SearchVO searchVO,ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {
		ModelAndView mav = new ModelAndView();

        //SearchVO searchVO = new SearchVO();
        searchVO.setService(req.getParameter("link_service"));
        searchVO.setWorker(req.getParameter("link_id"));
        searchVO.setStandard(req.getParameter("link_std"));
        searchVO.setDept(req.getParameter("link_dept"));
        searchVO.setManCyl((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
        searchVO.setAuth((String)req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));
        searchVO.setSearchCondition(req.getParameter("link_sta"));
        searchVO.setSearchKeyword((String)req.getSession().getAttribute(CommonConfig.SES_DEPT_KEY));
        searchVO.setServiceName((String)req.getSession().getAttribute(CommonConfig.SES_SERVICE_KEY));

        /* 2016-11-02, 사용안하는 리소스로 주석처리
        List<?> list = new ArrayList();

        list = fmStateService.fmSTATE002_TO_POP_LIST(searchVO);

        model.addAttribute("list",list); */
        model.addAttribute("searchVO",searchVO);
        return "FM-STATE002_popup";
	}

	@RequestMapping("/FM-STATE002_search.do")
	public ModelAndView fmSTATE002_POP_search(ModelMap model, final HttpServletRequest req, HttpServletResponse res) throws Exception {
		ModelAndView mav = new ModelAndView();

        SearchVO searchVO = new SearchVO();
        searchVO.setWorker(req.getParameter("worker"));
        searchVO.setWorkerName(req.getParameter("workerName"));
        searchVO.setDept(req.getParameter("dept"));
        searchVO.setStOrg(req.getParameter("stOrg"));
        searchVO.setGpOrg(req.getParameter("gpOrg"));
        searchVO.setHqOrg(req.getParameter("hqOrg"));
        searchVO.setService(req.getParameter("service"));
        searchVO.setStandard(req.getParameter("standard"));
        searchVO.setManCyl((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
        searchVO.setAuth((String)req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));
        searchVO.setSearchCondition(req.getParameter("sta"));
        searchVO.setSearchKeyword((String)req.getSession().getAttribute(CommonConfig.SES_DEPT_KEY));
        searchVO.setServiceName((String)req.getSession().getAttribute(CommonConfig.SES_SERVICE_KEY));

        List<?> list = new ArrayList();
        list = fmStateService.fmSTATE002_TO_POP_LIST(searchVO);

        res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", list);
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");
		return mav;

	}

	@RequestMapping(value="/FM-STATE003.do")
	public String fmState003 (@ModelAttribute("searchVO") SearchVO searchVO
			, ModelMap model, HttpServletRequest req) throws Exception {

        searchVO.setStandard("S03");
        model.addAttribute("searchVO", searchVO);

		return "FM-STATE003";
	}

	@RequestMapping("/FM-STATE003_LIST.do")
	public ModelAndView fmSTATE003_LIST(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

        searchVO.setManCyl((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

        List<?> list = fmStateService.fmSTATE003_LIST(searchVO);

        res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping("/FM-STATE003_DIV.do")
	public ModelAndView fmSTATE003_DIV(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

        searchVO.setManCyl((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

        List<?> list = fmStateService.fmSTATE003_DIV(searchVO);

        res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping(value="/FM-STATE004.do")
	public String fmState004 (@ModelAttribute("searchVO") SearchVO searchVO
			, ModelMap model, HttpServletRequest req) throws Exception {

        searchVO.setStrDat((String)req.getSession().getAttribute(CommonConfig.SES_MAN_STD_KEY));
        model.addAttribute("searchVO", searchVO);

		return "FM-STATE004";
	}

	@RequestMapping("/FM-STATE004_STD_LIST.do")
	public ModelAndView fmSTATE004_STD_LIST(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

        searchVO.setManCyl((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

        List<?> list = new ArrayList();
        list = fmStateService.fmSTATE004_STD_LIST(searchVO);

        res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping("/FM-STATE004_DEPT_LIST.do")
	public ModelAndView fmSTATE004_DEPT_LIST(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

        searchVO.setManCyl((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

        List<?> list = new ArrayList();
        list = fmStateService.fmSTATE004_DEPT_LIST(searchVO);

        res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping("/FM-STATE004_USER_LIST.do")
	public ModelAndView fmSTATE004_USER_LIST(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

        searchVO.setManCyl((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

        List<?> list = new ArrayList();
        list = fmStateService.fmSTATE004_USER_LIST(searchVO);

        res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping(value="/FM-STATE005.do")
	public String fmState005 (@ModelAttribute("searchVO") SearchVO searchVO
			, ModelMap model, HttpServletRequest req) throws Exception {

        searchVO.setStrDat((String)req.getSession().getAttribute(CommonConfig.SES_MAN_STD_KEY));
        model.addAttribute("searchVO", searchVO);

		return "FM-STATE005";
	}

	@RequestMapping("/FM-STATE005_STD_LIST.do")
	public ModelAndView fmSTATE005_STD_LIST(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

        searchVO.setManCyl((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

        List<?> list = new ArrayList();
        list = fmStateService.fmSTATE005_STD_LIST(searchVO);

        res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping("/FM-STATE005_DEPT_LIST.do")
	public ModelAndView fmSTATE005_DEPT_LIST(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

        searchVO.setManCyl((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

        List<?> list = new ArrayList();
        list = fmStateService.fmSTATE005_DEPT_LIST(searchVO);

        res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping(value="/FM-STATE006.do")
	public String fmState006 (@ModelAttribute("searchVO") SearchVO searchVO
			, ModelMap model, HttpServletRequest req) throws Exception {

        searchVO.setStrDat((String)req.getSession().getAttribute(CommonConfig.SES_MAN_STD_KEY));
        model.addAttribute("searchVO", searchVO);

		return "FM-STATE006";
	}

	@RequestMapping("/FM-STATE006_STD_LIST.do")
	public ModelAndView fmSTATE006_STD_LIST(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

        searchVO.setManCyl((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

        List<?> list = new ArrayList();
        list = fmStateService.fmSTATE006_STD_LIST(searchVO);

        res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping("/FM-STATE006_SUB_LIST.do")
	public ModelAndView fmSTATE006_SUB_LIST(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

        searchVO.setManCyl((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
        System.out.println("12312"+ searchVO.getService());

        List<?> list = new ArrayList();
        list = fmStateService.fmSTATE006_SUB_LIST(searchVO);

        res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	/* 2016-10-19, 신규 */
	@RequestMapping(value="/FM-STATE010.do")
	public String fmState010 (ModelMap model, HttpServletRequest req) throws Exception {
		return "FM-STATE010";
	}

	@RequestMapping(value="/FM-STATE011.do")
	public String fmState011 (ModelMap model, HttpServletRequest req) throws Exception {
		return "FM-STATE011";
	}

	@RequestMapping(value="/FM-STATE012.do")
	public String fmState012 (ModelMap model, HttpServletRequest req) throws Exception {
		return "FM-STATE012";
	}

	@RequestMapping(value="/FM-STATE014.do")
	public String fmState014 (ModelMap model, HttpServletRequest req) throws Exception {
		return "FM-STATE014";
	}

	@RequestMapping(value="/FM-STATE016.do")
	public String fmState016 (ModelMap model, HttpServletRequest req) throws Exception {
		return "FM-STATE016";
	}

	@RequestMapping(value="/FM-STATE017.do")
	public String fmState017 (ModelMap model, HttpServletRequest req) throws Exception {
		return "FM-STATE017";
	}

	@RequestMapping("/getWorkServiceStatistics.do")
	public ModelAndView getWorkServiceStatistics(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		Map map = new HashMap();
		map.put("manCyl", (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		map.put("month", req.getParameter("month"));

        List<?> list = new ArrayList();
        list = fmStateService.selectWorkServiceStatistics(map);

        res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping("/getWorkDepStatistics.do")
	public ModelAndView getWorkDepStatistics(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		String depLevel = req.getParameter("depLevel") != null ? req.getParameter("depLevel") : "3";

		Map map = new HashMap();
        map.put("manCyl", (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
        map.put("service", req.getParameter("service"));
        map.put("depLevel", depLevel);
        map.put("depCode", req.getParameter("depCode"));
        // 2017-02-10
        if (req.getParameter("hasWork") != null) {
        	map.put("depLevel", null);
        	map.put("hasWork", req.getParameter("hasWork"));
        }

        List<?> list = new ArrayList();
        list = fmStateService.selectWorkDepStatistics(map);

		Map resultMap = new HashMap();
        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	/*
	 * 2017-02-10
	 * 사용자 통계
	 */
	@RequestMapping("/getWorkUserStatistics.do")
	public ModelAndView getWorkuserStatistics(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		Map map = new HashMap();
        map.put("manCyl", (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
        map.put("service", req.getParameter("service"));
        map.put("depCode", req.getParameter("depCode"));

        List<?> list = new ArrayList();
        list = fmStateService.selectWorkUserStatistics(map);

		Map resultMap = new HashMap();
        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping("/getWorkMonthStatistics.do")
	public ModelAndView getWorkMonthStatistics(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		Map map = new HashMap();
        map.put("manCyl", (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

        List<?> list = new ArrayList();
        list = fmStateService.selectWorkMonthStatistics(map);

        res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping("/getWorkCtrStatistics.do")
	public ModelAndView getWorkCtrStatistics(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		Map map = new HashMap();
        map.put("manCyl", (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
        map.put("service", req.getParameter("service"));
        map.put("standard", req.getParameter("standard"));

        List<?> list = new ArrayList();
        list = fmStateService.selectWorkCtrStatistics(map);

        res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping("/getAssetServiceStatistics.do")
	public ModelAndView getAssetServiceStatistics(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		Map map = new HashMap();
		map.put("manCyl", (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

        List<?> list = new ArrayList();
        list = fmStateService.selectAssetServiceStatistics(map);
        // asset
     	List<?> codeList = fmStateService.selectAssetCodeList();

        res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
        resultMap.put("result", list);
        resultMap.put("category", codeList);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping("/getAssetServiceStatisticsOfAsset.do")
	public ModelAndView getAssetServiceStatisticsOfAsset(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		Map map = new HashMap();
		map.put("manCyl", (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

        List<?> list = fmStateService.selectAssetServiceStatisticsOfAsset(map);
		// service
		List<?> codeList = commonCodeService.getSvcCodeList("");

        res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
        resultMap.put("result", list);
        resultMap.put("category", codeList);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping("/getAssetDepStatistics.do")
	public ModelAndView getAssetDepStatistics(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		Map map = new HashMap();
		map.put("manCyl", (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
	    map.put("service", req.getParameter("service"));

        List<?> list = new ArrayList();
        list = fmStateService.selectAssetDepStatistics(map);
        // asset
     	List<?> codeList = fmStateService.selectAssetCodeList();

        res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
        resultMap.put("result", list);
        resultMap.put("category", codeList);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping("/getAssetDepStatisticsOfAsset.do")
	public ModelAndView getAssetDepStatisticsOfAsset(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		Map map = new HashMap();
		map.put("manCyl", (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
	    map.put("service", req.getParameter("service"));

        List<?> list = fmStateService.selectAssetDepStatisticsOfAsset(map);
		// service
		List<?> codeList = fmStateService.selectDepCodeListOfService(map);

        res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
        resultMap.put("result", list);
        resultMap.put("category", codeList);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	/*
	 * 2017-06-30, 자산유형별 위험도 합계
	 */
	@RequestMapping("/getRiskCatStatistics.do")
	public ModelAndView getRiskCatStatistics(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		Map map = new HashMap();
        map.put("manCyl", (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
        map.put("service", req.getParameter("service"));
        map.put("standard", req.getParameter("standard"));

        List<?> list = new ArrayList();
        list = fmStateService.selectRiskCatStatistics(map);

		Map resultMap = new HashMap();
        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

    @RequestMapping(value="/FM-STATE_PDF_popup.do", method = RequestMethod.POST)
    public String fmStatePDF_popup (ModelMap model, HttpServletRequest req
                                    , @RequestParam(value = "type") String type
                                    , @RequestParam(value = "bcy_cod") String bcy_cod
                                    , @RequestParam(value = "ucm_ctr_gbn") String ucm_ctr_gbn
                                    , @RequestParam(value = "service") String service
                                    , @RequestParam(value = "title", defaultValue="") String title
                                    , @RequestParam(value = "dep", defaultValue="") String dep
    ) {
        model.addAttribute("type", type);
        model.addAttribute("bcy_cod", bcy_cod);
        model.addAttribute("ucm_ctr_gbn", ucm_ctr_gbn);
        model.addAttribute("service", service);
        model.addAttribute("title", title);
        model.addAttribute("dep", dep);

        return "FM-STATE_PDF_popup";
    }
    //보안점검
    @RequestMapping(value="/FM-STATE018.do")
    public String fmState018 (ModelMap model, HttpServletRequest req) throws Exception {
        String sesAuth = (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);
        Map<String, String> map = new HashMap<String, String>();
        map.put("sesAuthKey", sesAuth);
        List<EgovMap> listBcy = fmStateService.getMCY();
        model.addAttribute("listBcyCod", listBcy);

        if(req.getParameter("mode")!=null){
            String mode = req.getParameter("mode");
            String bcy_cod = req.getParameter("bcy_cod");
            if(bcy_cod==null){
                bcy_cod = (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY);
            }
            if(mode.equals("new")||mode.equals("edit")){
                map.put("mode", mode);
                map.put("bcy_cod", req.getParameter("bcy_cod"));
                map.put("editKind", req.getParameter("editKind"));
                map.put("editUse", req.getParameter("editUse"));
                for(int i=1; i<=5; i++){
                    map.put("editPoint"+i+"Val1", req.getParameter("editPoint"+i+"Val1"));
                    map.put("editPoint"+i+"Op1", req.getParameter("editPoint"+i+"Op1"));
                    map.put("editPoint"+i+"Val2", req.getParameter("editPoint"+i+"Val2"));
                    map.put("editPoint"+i+"Op2", req.getParameter("editPoint"+i+"Op2"));
                }
                model.addAttribute("result", fmStateService.setCriterionPoint(map));
                mode="setting";
            }

            if(mode.equals("setting")){
                map.put("bcy_cod", bcy_cod);
                List<Map> listCriterion = fmStateService.getCriterion(map);
                if(listCriterion.size()==0){
                    listCriterion = fmStateService.initCriterion(bcy_cod);
                }
                List<Map> listCriterionPoint = fmStateService.getCriterionPoint(map);
                if(listCriterionPoint.size()==0){
                    fmStateService.initCriterionPoint(bcy_cod);
                    listCriterionPoint = fmStateService.getCriterionPoint(map);
                }
                for(Iterator<Map> it = listCriterion.iterator(); it.hasNext();){
                    Map<String, Object> mapTempCriterion = it.next();
                    for (Map<String, String> mapCriterionPoint : listCriterionPoint) {
                        if(mapCriterionPoint.get("uscKindCod").equals(mapTempCriterion.get("uscKindCod"))){
                            it.remove();
                        }
                    }
                }
                model.addAttribute("bcy_cod", bcy_cod);
                model.addAttribute("listCriterion", listCriterion);
                model.addAttribute("listCriterionPoint", listCriterionPoint);
                return "FM-STATE018_setting";
            }
        }
        return "FM-STATE018";
    }

    @RequestMapping("/getStatisticsSaPart1.do")
    public ModelAndView getStatisticsSaPart1(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {
        ModelAndView mav = new ModelAndView();
        Map map = new HashMap();
        map.put("bcy_cod", req.getParameter("bcy_cod"));

        if(req.getParameter("quarter")!=null&&req.getParameter("quarter")!=""){
            String strYear = fmStateService.getQuarterYear(req.getParameter("bcy_cod"));
            if(req.getParameter("quarter").equals("1")){
                map.put("quarter_s", strYear.substring(0,4)+"-01-01");
                map.put("quarter_f", strYear.substring(0,4)+"-03-31");
            }else if(req.getParameter("quarter").equals("2")){
                map.put("quarter_s", strYear.substring(0,4)+"-04-01");
                map.put("quarter_f", strYear.substring(0,4)+"-06-30");
            }else if(req.getParameter("quarter").equals("3")){
                map.put("quarter_s", strYear.substring(0,4)+"-07-01");
                map.put("quarter_f", strYear.substring(0,4)+"-09-30");
            }else if(req.getParameter("quarter").equals("4")){
                map.put("quarter_s", strYear.substring(0,4)+"-10-01");
                map.put("quarter_f", strYear.substring(0,4)+"-12-31");
            }
        }
        List<?> list = fmStateService.getStatisticsSaPart1(map);
        Map resultMap = new HashMap();
        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
    }

    @RequestMapping("/getStatisticsSaPart2.do")
    public ModelAndView getStatisticsSaPart2(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {
        ModelAndView mav = new ModelAndView();
        Map map = new HashMap();
        map.put("bcy_cod", req.getParameter("bcy_cod"));
        map.put("dep", req.getParameter("dep"));
        if(req.getParameter("quarter")!=null&&req.getParameter("quarter")!=""){
            String strYear = fmStateService.getQuarterYear(req.getParameter("bcy_cod"));
            if(req.getParameter("quarter").equals("1")){
                map.put("quarter_s", strYear.substring(0,4)+"-01-01");
                map.put("quarter_f", strYear.substring(0,4)+"-03-31");
            }else if(req.getParameter("quarter").equals("2")){
                map.put("quarter_s", strYear.substring(0,4)+"-04-01");
                map.put("quarter_f", strYear.substring(0,4)+"-06-30");
            }else if(req.getParameter("quarter").equals("3")){
                map.put("quarter_s", strYear.substring(0,4)+"-07-01");
                map.put("quarter_f", strYear.substring(0,4)+"-09-30");
            }else if(req.getParameter("quarter").equals("4")){
                map.put("quarter_s", strYear.substring(0,4)+"-10-01");
                map.put("quarter_f", strYear.substring(0,4)+"-12-31");
            }
        }
        List<?> list = fmStateService.getStatisticsSaPart2(map);

        Map resultMap = new HashMap();
        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
    }

    @RequestMapping("/getStatisticsSaPart2_2.do")
    public ModelAndView getStatisticsSaPart2_2(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {
        String sesDept = (String) req.getSession().getAttribute(CommonConfig.SES_DEPT_KEY);

        ModelAndView mav = new ModelAndView();
        Map map = new HashMap();
        map.put("bcy_cod", req.getParameter("bcy_cod"));
        map.put("dep", sesDept);
        if(req.getParameter("quarter")!=null&&req.getParameter("quarter")!=""){
            String strYear = fmStateService.getQuarterYear(req.getParameter("bcy_cod"));
            if(req.getParameter("quarter").equals("1")){
                map.put("quarter_s", strYear.substring(0,4)+"-01-01");
                map.put("quarter_f", strYear.substring(0,4)+"-03-31");
            }else if(req.getParameter("quarter").equals("2")){
                map.put("quarter_s", strYear.substring(0,4)+"-04-01");
                map.put("quarter_f", strYear.substring(0,4)+"-06-30");
            }else if(req.getParameter("quarter").equals("3")){
                map.put("quarter_s", strYear.substring(0,4)+"-07-01");
                map.put("quarter_f", strYear.substring(0,4)+"-09-30");
            }else if(req.getParameter("quarter").equals("4")){
                map.put("quarter_s", strYear.substring(0,4)+"-10-01");
                map.put("quarter_f", strYear.substring(0,4)+"-12-31");
            }
        }
        List<?> list = fmStateService.getStatisticsSaPart2(map);

        Map resultMap = new HashMap();
        resultMap.put("result", list);

        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
    }

    @RequestMapping(value = "/FM-STATE018_EDIT_SA.do", method= RequestMethod.POST)
    @ResponseBody
    public ModelAndView fmSTATE018_EDIT_SA (ModelMap model, HttpServletRequest req, @RequestParam String param) {
        ModelAndView mav = new ModelAndView();

        Map<String, String> map = new HashMap<String, String>();
        String wrk_key = (String)req.getSession().getAttribute(CommonConfig.SES_USER_KEY);

        Map<String, Object> resultMap = new HashMap<String, Object>();
        map.put("param", param);
        map.put("wrk_key", wrk_key);

        resultMap.put("result", fmStateService.fmSTATE018_EDIT_SA(map));
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
    }

    //기반시설 //기반시설 관리/물리 취약점 분석평가
    @RequestMapping(value="/FM-STATE019.do")
    public String fmState019 (ModelMap model, HttpServletRequest req) throws Exception {
        String sesAuth = (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);
        Map<String, String> map = new HashMap<String, String>();
        map.put("sesAuthKey", sesAuth);

        model.addAttribute("listBcyCod", fmStateService.getMCY());
        model.addAttribute("listService", commonCodeService.getServiceAuthList_N(map));
        model.addAttribute("compList", excelNewService.getCompliance("infra_mp"));

        return "FM-STATE019";
    }

    @RequestMapping("/getStatisticsInfraMpPart1.do")
    public ModelAndView getStatisticsInfraMpPart1(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {
        ModelAndView mav = new ModelAndView();
        Map map = new HashMap();
        map.put("bcy_cod", req.getParameter("bcy_cod"));
        map.put("ucm_ctr_gbn", req.getParameter("ucm_ctr_gbn"));
        map.put("service", req.getParameter("service"));
        map.put("pre_year_count", 5); //5년

        List<?> list = new ArrayList();
        list = fmStateService.getStatisticsInfraMpPart1(map);

        Map resultMap = new HashMap();
        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
    }

    @RequestMapping("/getStatisticsInfraMpPart2.do")
    public ModelAndView getStatisticsInfraMpPart2(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {
        ModelAndView mav = new ModelAndView();
        Map map = new HashMap();
        map.put("bcy_cod", req.getParameter("bcy_cod"));
        map.put("ucm_ctr_gbn", req.getParameter("ucm_ctr_gbn"));
        map.put("service", req.getParameter("service"));

        List<?> list = new ArrayList();
        list = fmStateService.getStatisticsInfraMpPart2(map);

        Map resultMap = new HashMap();
        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
    }

    //기반시설 정보보호 수준평가
    @RequestMapping(value="/FM-STATE020.do")
    public String fmState020 (ModelMap model, HttpServletRequest req) throws Exception {
        String sesAuth = (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);
        Map<String, String> map = new HashMap<String, String>();
        map.put("sesAuthKey", sesAuth);

        model.addAttribute("listBcyCod", fmStateService.getMCY());
        model.addAttribute("listService", commonCodeService.getServiceAuthList_N(map));
        model.addAttribute("compList", excelNewService.getCompliance("infra_la"));

        return "FM-STATE020";
    }

    @RequestMapping("/getStatisticsInfraLaPart1.do")
    public ModelAndView getStatisticsInfraLaPart1(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {
        ModelAndView mav = new ModelAndView();
        Map map = new HashMap();
        map.put("bcy_cod", req.getParameter("bcy_cod"));
        map.put("ucm_ctr_gbn", req.getParameter("ucm_ctr_gbn"));
        map.put("service", req.getParameter("service"));

        List<?> list = new ArrayList();
        list = fmStateService.getStatisticsInfraLaPart1(map);

        Map resultMap = new HashMap();
        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");
        return mav;
    }

    @RequestMapping("/getStatisticsInfraLaPart2.do")
    public ModelAndView getStatisticsInfraLaPart2(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {
        ModelAndView mav = new ModelAndView();
        Map map = new HashMap();
        map.put("bcy_cod", req.getParameter("bcy_cod"));
        map.put("ucm_ctr_gbn", req.getParameter("ucm_ctr_gbn"));
        map.put("service", req.getParameter("service"));
        map.put("pre_year_count", 5); //5년

        List<?> list = fmStateService.getStatisticsInfraLaPart2(map);

        Map resultMap = new HashMap();
        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");
        return mav;
    }

    //과기정통부 체크리스트
    @RequestMapping(value="/FM-STATE021.do")
    public String fmState021 (ModelMap model, HttpServletRequest req) throws Exception {
        String sesAuth = (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);
        Map<String, String> map = new HashMap<String, String>();
        map.put("sesAuthKey", sesAuth);

        model.addAttribute("listBcyCod", fmStateService.getMCY());
        model.addAttribute("listService", commonCodeService.getServiceAuthList_N(map));
        model.addAttribute("compList", excelNewService.getCompliance("msit"));

        return "FM-STATE021";
    }

    @RequestMapping("/getStatisticsMsitPart1.do")
    public ModelAndView getStatisticsMsitPart1(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {
        ModelAndView mav = new ModelAndView();
        Map map = new HashMap();
        map.put("bcy_cod", req.getParameter("bcy_cod"));
        map.put("ucm_ctr_gbn", req.getParameter("ucm_ctr_gbn"));
        map.put("service", req.getParameter("service"));
        map.put("pre_year_count", 5); //5년

        List<?> list = new ArrayList();
        list = fmStateService.getStatisticsMsitPart1(map);
        Map resultMap = new HashMap();
        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

         return mav;
    }

    @RequestMapping("/getStatisticsMsitPart2.do")
    public ModelAndView getStatisticsMsitPart2(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {
        ModelAndView mav = new ModelAndView();
        Map map = new HashMap();
        map.put("bcy_cod", req.getParameter("bcy_cod"));
        map.put("ucm_ctr_gbn", req.getParameter("ucm_ctr_gbn"));
        map.put("service", req.getParameter("service"));

        List<?> list = new ArrayList();
        list = fmStateService.getStatisticsMsitPart2(map);

        Map resultMap = new HashMap();
        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
    }

    //부서별 보안점검
    @RequestMapping(value="/FM-STATE022.do")
    public String fmState022 (ModelMap model, HttpServletRequest req) throws Exception {
        model.addAttribute("listBcyCod", fmStateService.getMCY());

        return "FM-STATE022";
    }
}
