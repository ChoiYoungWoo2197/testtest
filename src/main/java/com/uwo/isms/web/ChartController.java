/**
 ***********************************
 * @source ChartController.java
 * @date 2015. 08. 24.
 * @project isms3
 * @description rMateChartH5 controller
 ***********************************
 */
package com.uwo.isms.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.uwo.isms.common.CommonConfig;
import com.uwo.isms.domain.SearchVO;
import com.uwo.isms.service.ChartService;
import com.uwo.isms.service.CommonCodeService;
import com.uwo.isms.service.FMStateService;
import com.uwo.isms.util.EgovUserDetailsHelper;

@Controller
@RequestMapping("/chart")
public class ChartController {

	Logger log = LogManager.getLogger(FMSetupController.class);

	@Resource(name = "fmStateService")
	private FMStateService fmStateService;

	@Resource(name = "commonCodeService")
	private CommonCodeService commonCodeService;

	@Resource(name = "chartService")
	private ChartService chartService;

	@RequestMapping(value="/FM-STATE002_popup.do")
	public String state002_chart ( ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

        String sTitle = "진행현황";
        String mTitle = "";
        String cateField = "SCALE";
        String sMode = req.getParameter("sMode");

        SearchVO searchVO = new SearchVO();
        searchVO.setService(req.getParameter("service"));
        searchVO.setAuth(req.getParameter("auth"));
        searchVO.setDept(req.getParameter("dept"));
        searchVO.setManCyl(req.getParameter("axis"));
        searchVO.setStandard(req.getParameter("scale"));
        List<?> jsonList = new ArrayList();

        if(sMode.equals("USER")){
        	jsonList = fmStateService.fmSTATE002_USER_LIST(searchVO);
        	mTitle = "담당자별";
        	cateField = "USRNAM";
        }else if(sMode.equals("DEPT")){
        	jsonList = fmStateService.fmSTATE002_DEPT_LIST(searchVO);
        	mTitle = "부서별";
        }else{
        	jsonList = fmStateService.fmSTATE002_STD_LIST(searchVO);
        	mTitle = "서비스별";
        }

        String listJson = chartService.state002_chartJson(jsonList, cateField, sMode);

        String chartGrid = "";

        if(sMode.equals("USER")){
        	chartGrid = chartService.state002_chartBar(sTitle, mTitle, cateField);
        }else{
        	chartGrid = chartService.state002_chart(sTitle, mTitle, cateField);
        }

        model.addAttribute("chartName", "rMateScrollChartH5");
        model.addAttribute("sTitle", sTitle);
        model.addAttribute("mTitle", mTitle);
        model.addAttribute("listJson", listJson);
        model.addAttribute("chartGrid", chartGrid);

        return "FM-CHART";
	}

	@RequestMapping(value="/FM-STATE003_popup.do")
	public String state003_chart (@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

        searchVO.setManCyl((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

        String sTitle = "달성률";
        String mTitle = "도메인별";
        String cateField = "COMPLIANCE";

        List<?> serviceList = commonCodeService.getCommonCodeList("SERVICE");

        List<?> comList = fmStateService.fmSTATE003_COM(searchVO);

        List<?> jsonList = fmStateService.fmSTATE003_LIST(searchVO);

        String listJson = chartService.state003_chartJson(jsonList, comList, serviceList);

        String chartGrid = chartService.state003_chart(mTitle, searchVO.getStndNam(), cateField, serviceList, searchVO.getService());

        model.addAttribute("chartName", "rMateRadarChartH5");
        model.addAttribute("sTitle", sTitle);
        model.addAttribute("mTitle", mTitle);
        model.addAttribute("listJson", listJson);
        model.addAttribute("chartGrid", chartGrid);

        return "FM-CHART";
	}

	@RequestMapping(value="/FM-STATE004_popup.do")
	public String state004_chart (@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		String sTitle = "업무현황";
        String mTitle = "";
        String cateField = "MONTH";
        String sMode = req.getParameter("sMode");

        searchVO.setManCyl((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
        List<?> jsonList = new ArrayList();

        if(sMode.equals("USER")){
        	jsonList = fmStateService.fmSTATE004_USER_LIST(searchVO);
        	mTitle = "담당자별";
        	cateField = "USERNAM";
        }else if(sMode.equals("DEPT")){
        	jsonList = fmStateService.fmSTATE004_DEPT_LIST(searchVO);
        	mTitle = "부서별";
        	cateField = "DEPT";
        }else{
        	jsonList = fmStateService.fmSTATE004_STD_LIST(searchVO);
        	mTitle = "기간별";
        }

        String listJson = chartService.state004_chartJson(jsonList, cateField, sMode);

        String chartGrid = "";

        if(sMode.equals("USER")){
        	chartGrid = chartService.state004_chartBar(sTitle, mTitle, cateField);
        }else{
        	chartGrid = chartService.state004_chart(sTitle, mTitle, cateField);
        }

        model.addAttribute("chartName", "rMateScrollChartH5");
        model.addAttribute("sTitle", sTitle);
        model.addAttribute("mTitle", mTitle);
        model.addAttribute("listJson", listJson);
        model.addAttribute("chartGrid", chartGrid);

        return "FM-CHART";
	}

	@RequestMapping(value="/FM-STATE005_popup.do")
	public String state005_chart (@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

        String sTitle = "위험현황";
        String mTitle = "";
        String cateField = "SERVICE";
        String sMode = req.getParameter("sMode");

        searchVO.setManCyl((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
        List<?> jsonList = new ArrayList();

        if(sMode.equals("DEPT")){
        	jsonList = fmStateService.fmSTATE005_DEPT_LIST(searchVO);
        	mTitle = "부서별";
        	cateField = "DEPT";
        }else{
        	jsonList = fmStateService.fmSTATE005_STD_LIST(searchVO);
        	mTitle = "서비스별";
        }

        String listJson = chartService.state005_chartJson(jsonList, cateField, sMode);

        String chartGrid = "";

        chartGrid = chartService.state005_chart(sTitle, mTitle, cateField);

        model.addAttribute("chartName", "rMateScrollChartH5");
        model.addAttribute("sTitle", sTitle);
        model.addAttribute("mTitle", mTitle);
        model.addAttribute("listJson", listJson);
        model.addAttribute("chartGrid", chartGrid);

        return "FM-CHART";
	}

	@RequestMapping(value="/FM-STATE006_popup.do")
	public String state006_chart (@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

        String sTitle = "자산내역";
        String mTitle = "";
        String cateField = "SERVICE";
        String sMode = req.getParameter("sMode");

        searchVO.setManCyl((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
        List<?> jsonList = new ArrayList();

        if(sMode.equals("DEPT")){
        	jsonList = fmStateService.fmSTATE006_SUB_LIST(searchVO);
        	mTitle = "부서별";
        	cateField = "DEPT";
        }else{
        	jsonList = fmStateService.fmSTATE006_STD_LIST(searchVO);
        	mTitle = "서비스별";
        }

        String listJson = chartService.state006_chartJson(jsonList, cateField, sMode);

        String chartGrid = "";

        chartGrid = chartService.state006_chart(sTitle, mTitle, cateField);

        model.addAttribute("chartName", "rMateScrollChartH5");
        model.addAttribute("sTitle", sTitle);
        model.addAttribute("mTitle", mTitle);
        model.addAttribute("listJson", listJson);
        model.addAttribute("chartGrid", chartGrid);

        return "FM-CHART";
	}

	@RequestMapping(value="/FM-STATE000_popup.do")
	public String state000 (@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		String sTitle = "";
        String mTitle = "";
        String cateField = "SERVICE";

        searchVO.setManCyl((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
        List<?> jsonList = new ArrayList();

    	jsonList = fmStateService.fmSTATE006_STD_LIST(searchVO);

        String listJson = chartService.state006_chartJson(jsonList, cateField, "SERVICE");

        String chartGrid = "";

        chartGrid = chartService.state000_chart(sTitle, mTitle, cateField);

        model.addAttribute("chartName", "rMateChartH5");
        model.addAttribute("listJson", listJson);
        model.addAttribute("chartGrid", chartGrid);

        return "FM-CHART_M";
	}

}
