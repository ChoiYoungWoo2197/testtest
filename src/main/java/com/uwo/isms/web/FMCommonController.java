package com.uwo.isms.web;

import java.util.HashMap;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.uwo.isms.common.CommonConfig;
import com.uwo.isms.domain.SearchVO;
import com.uwo.isms.domain.UserVO;
import com.uwo.isms.service.FMCommonService;
import com.uwo.isms.util.EgovUserDetailsHelper;

@Controller
@RequestMapping("/common")
public class FMCommonController {

	Logger log = LogManager.getLogger(FMCommonController.class);

	@Autowired
	private FMCommonService fmCommonService;

	@Autowired
	private FMSetupController fmSetupController;

	@RequestMapping("/FM-COMMON_users_popup.do")
	public String fmCommon_usrListPopup (ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, String> param = new HashMap<String,String>();

        param.put("service", req.getParameter("service"));
        param.put("uumUsrId", req.getParameter("ursMesMng"));
	    param.put("uumUsrNam", req.getParameter("usrMngNam"));
	    param.put("stOrg", req.getParameter("stOrg"));
	    param.put("hqOrg", req.getParameter("hqOrg"));
	    param.put("gpOrg", req.getParameter("gpOrg"));

	    model.addAttribute("setUsrKey", req.getParameter("setUsrKey"));
	    model.addAttribute("setUsrId", req.getParameter("setUsrId"));
	    model.addAttribute("setUsrName", req.getParameter("setUsrName"));

	    // 2017-01-20
	    model.addAttribute("apvGbn", req.getParameter("apvGbn"));
	    model.addAttribute("setDepCode", req.getParameter("setDepCode"));
	    model.addAttribute("setDepName", req.getParameter("setDepName"));

        return "FM-COMMON_users_popup";
	}

	@RequestMapping("/FM-COMMON_userlist.do")
	public ModelAndView fmCommon_userlist (@RequestParam Map<String, String> map, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", fmCommonService.getUserList(map));
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping("/FM-COMMON_myinfo_popup.do")
	public String fmCommon_info (@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		String usrKey = (String)req.getSession().getAttribute(CommonConfig.SES_USER_KEY);
		searchVO.setUsrkey(usrKey);
		model.addAttribute("updatePage", "/common/FM-COMMON_myinfo_U.do");

		return fmSetupController.fmSetup007_popup(searchVO, model, req,  res);
	}

	@RequestMapping("FM-COMMON_myinfo_U.do")
	public ModelAndView FM_SETUP007_U(@ModelAttribute("vo") UserVO vo, BindingResult bindingResult,
			Model model,final HttpServletRequest req, HttpServletResponse res) throws Exception {

		return fmSetupController.FM_SETUP007_U(vo, bindingResult, model,req, res);

	}
}
