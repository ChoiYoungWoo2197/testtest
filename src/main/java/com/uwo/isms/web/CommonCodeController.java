/**
 ***********************************
 * @source CommonCodeController.java
 * @date 2014. 10. 20.
 * @project isms3_godohwa
 * @description 
 ***********************************
 */
package com.uwo.isms.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.uwo.isms.common.CommonConfig;
import com.uwo.isms.domain.SearchVO;
import com.uwo.isms.domain.UserVO;
import com.uwo.isms.service.CommonCodeService;

import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
@RequestMapping("/code")
public class CommonCodeController {
	
	@Resource(name = "commonCodeService")
	private CommonCodeService commonCodeService;
	
	/** properties **/
    @Resource(name = "propertiesService")
    protected EgovPropertyService properties ;
    
	@RequestMapping("/GETSampleCode.do")
	public ModelAndView fmSetup006 (ModelMap model,	HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		ModelAndView mav = new ModelAndView(); 
		
        
        List<?> list = commonCodeService.getSampleCode();

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");
		
		Map resultMap = new HashMap();
        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}
	
	@RequestMapping("/GETDivCode.do")
	public ModelAndView getDivCode (ModelMap model,	HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		ModelAndView mav = new ModelAndView(); 
		
        
        List<?> list = commonCodeService.getDivCode();

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");
		
		Map resultMap = new HashMap();
        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}
	
	@RequestMapping("/GETSvcCode.do" )
	public ModelAndView getSvcCode (ModelMap model,	HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		ModelAndView mav = new ModelAndView(); 
		
		String standard = req.getParameter("standard");
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");
		Map resultMap = new HashMap();
        resultMap.put("result", commonCodeService.getSvcCode(standard));
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}
	

	@RequestMapping("/GetSelectBox.do" )
	public ModelAndView getSelectBox (ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {
		ModelAndView mav = new ModelAndView(); 

		String code		= req.getParameter("code");
		String sqlUrl	= req.getParameter("sqlUrl");
		String mode		= req.getParameter("mode");
		String worker	= req.getParameter("worker");
		String auth		= (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);
		String manCyl	= (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY);
		String company	= properties.getString("company");
				
		SearchVO searchVO = new SearchVO();
		searchVO.setCode(code);
		searchVO.setSqlUrl(sqlUrl);
		searchVO.setSearchCondition(mode);
		searchVO.setAuth(auth);
		searchVO.setManCyl(manCyl);
		searchVO.setSearchKeyword(company);
		searchVO.setWorker(worker);
		
        List<?> list = commonCodeService.getSelectBoxCode(searchVO);
        
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");
		
		Map resultMap = new HashMap();
        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}
	
	@RequestMapping("/GetManCycleSelectBox.do" )
	public ModelAndView getManCycleSelectBox (ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		ModelAndView mav = new ModelAndView(); 

		String company	= properties.getString("company");
				
        List<?> list = commonCodeService.getManCycleSelectBoxCode(company);
        
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");
		
		Map resultMap = new HashMap();
        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}
	
	@RequestMapping("/getCommonCodeList.do" )
	public ModelAndView getCommonCodeList (ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {
		ModelAndView mav = new ModelAndView();
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", commonCodeService.getCommonCodeList(req.getParameter("code")));
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}
	
	@RequestMapping("/getNonCodeList.do" )
	public ModelAndView getNonCodeList (ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {
		ModelAndView mav = new ModelAndView();
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
        //resultMap.put("result", commonCodeService.getNonCodeList(req.getParameter("code")));
		resultMap.put("result", commonCodeService.getNonCodeList((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY)));
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}
	
	
	@RequestMapping("/getWorkerList.do" )
	public ModelAndView getWorkerList (@RequestParam Map<String, String> map, ModelMap model, 
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		ModelAndView mav = new ModelAndView();
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		map.put("auth", (String)req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));		
		map.put("userKey", (String)req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
        resultMap.put("result", commonCodeService.getWorkerList(map));
        resultMap.put("authKey", (String)req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}
	
	@RequestMapping("/getOprusrList.do" )
	public ModelAndView getOprusrList (@RequestParam Map<String, String> map, ModelMap model, 
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		ModelAndView mav = new ModelAndView();
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		map.put("auth", (String)req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));
		map.put("userKey", (String)req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
        resultMap.put("result", commonCodeService.getOprusrList(map));
        resultMap.put("authKey", (String)req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}
	
	@RequestMapping("/getWorkerAllList.do" )
	public ModelAndView getWorkerAllList (@RequestParam Map<String, String> map, ModelMap model, 
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		ModelAndView mav = new ModelAndView();
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", commonCodeService.getWorkerList(map));
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}
	
	@RequestMapping("/getAssCatList.do" )
	public ModelAndView getAssCatList (ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {
		ModelAndView mav = new ModelAndView();
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", commonCodeService.getAssCatList());
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping("/getAssGbnList.do" )
	public ModelAndView getAssGbnList (ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {
		ModelAndView mav = new ModelAndView();
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", commonCodeService.getAssGbnList());
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}
	
	@RequestMapping("/getSenarioList.do" )
	public ModelAndView getSenarioList (@RequestParam Map<String, String> map, ModelMap model, 
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		ModelAndView mav = new ModelAndView();
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", commonCodeService.getSenarioList(map));
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}
	
	@RequestMapping("/getDeptList.do" )
	public ModelAndView getDeptList (@RequestParam Map<String, String> map, ModelMap model, 
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		ModelAndView mav = new ModelAndView();
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", commonCodeService.getDeptList(map));
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}
	
	@RequestMapping("/getDepartList.do" )
	public ModelAndView getDepartList (@RequestParam Map<String, String> map, ModelMap model, 
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		ModelAndView mav = new ModelAndView();
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", commonCodeService.getDepartList(map));
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}
	
	@RequestMapping("/getOprtList.do" )
	public ModelAndView getOprtList (@RequestParam Map<String, String> map, ModelMap model, 
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		ModelAndView mav = new ModelAndView();
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", commonCodeService.getOprtList(map));
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}
	
	@RequestMapping("/getDoaList.do" )
	public ModelAndView getDoaList (@RequestParam Map<String, String> map, ModelMap model, 
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		ModelAndView mav = new ModelAndView();
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", commonCodeService.getDoaList());
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");
		
		return mav;
	}
}
