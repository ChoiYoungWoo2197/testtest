/**
 ***********************************
 * @source LoginController.java
 * @date 2014. 10. 13.
 * @project isms3
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.uwo.isms.domain.CycleVO;
import com.uwo.isms.domain.LoginVO;
import com.uwo.isms.domain.SearchVO;
import com.uwo.isms.service.LoginService;
import com.uwo.isms.util.EgovUserDetailsHelper;
import com.uwo.isms.common.CommonConfig;
import com.uwo.isms.common.Crypto;
import com.uwo.isms.common.IsmsMessageSource;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;


@Controller
@RequestMapping("/login")
public class LoginController {

	//private final String key = "abcdefghijklmnopqrstuvwxyz012345";
	private final String key = "01234567890123456789012345678901";

	Logger log = LogManager.getLogger(FMSetupController.class);

	/** LoginService */
	@Resource(name = "fmLoginService")
	LoginService loginService;

	/** IsmsMessageSource */
    IsmsMessageSource IsmsMessageSource;

    /** properties **/
    @Resource(name = "propertiesService")
    protected EgovPropertyService properties ;

    /**
	 * 일반(세션) 로그인을 처리한다
	 * @param vo - 아이디, 비밀번호가 담긴 LoginVO
	 * @param request - 세션처리를 위한 HttpServletRequest
	 * @return result - 로그인결과(세션정보)
	 * @exception Exception
	 */
    @RequestMapping(value="/actionLogin.do")
    public String actionLogin(@ModelAttribute("loginVO") LoginVO loginVO,
    		                   HttpServletRequest request,
    		                   ModelMap model)
            throws Exception {

    	// 1. 일반 로그인 처리
//    	properties.refreshPropertyFiles();
    	String company = properties.getString("company");
    	String company_div = properties.getString("company_div");
    	String company_svc = properties.getString("company_svc");
    	String company_dep = properties.getString("company_dep");

    	log.debug("############################### company :: "+company+", company_div :: "+company_div+", company_svc :: "+company_svc+", company_dep :: "+company_dep);

    	loginVO.setCompany(company);
    	// id, pw 확인
    	LoginVO resultVO = loginService.actionLogin(loginVO);

    	LoginVO manCylVO = loginService.getManCyl(loginVO);



        if (resultVO != null && resultVO.getId() != null && !resultVO.getId().equals("")) {

        	// 사용자 잠금(uum_use_yn이 'N')상태이면 잠금 상태라는 메세지 띄우기
        	if(resultVO.getUum_use_yn().equals("L")){
        		model.addAttribute("message", "Locked");
        		return "redirect:/index.jsp";
        	}

        	// 2017-06-13, Key, Id 정보를 세션에 저장. changePassword.jsp 위변조 방지목적

        	request.getSession().removeAttribute(CommonConfig.SES_USER_KEY);
        	request.getSession().removeAttribute(CommonConfig.SES_USER_ID_KEY);
        	request.getSession().setAttribute(CommonConfig.SES_USER_KEY, resultVO.getuum_usr_key());
        	request.getSession().setAttribute(CommonConfig.SES_USER_ID_KEY, resultVO.getId());

        	// 2017-06-13, 최초 비밀번호 이거나 90일이 경과했을 경우
        	if (resultVO.getUum_chg_mdh() == null) {
        		model.addAttribute("message", "P1");
        		return "redirect:/index.jsp";
        	}
        	else if (Integer.valueOf(resultVO.getUum_chg_mdh()) > 90) {
        		model.addAttribute("message", "P2");
        		return "redirect:/index.jsp";
        	}

        	// 2-1. 로그인 정보를 세션에 저장
        	request.getSession().setAttribute("loginVO", resultVO);
        	request.getSession().setAttribute("menuList", loginService.getMenuList(resultVO.getuum_usr_key()));
        	request.getSession().setAttribute("menuAuthList", loginService.getMenuAuthList(resultVO.getuum_usr_key()));
        	request.getSession().setAttribute("spdMenuList", loginService.getSpdMenuList(resultVO.getuum_usr_key()));
        	//request.getSession().setAttribute(CommonConfig.SES_USER_KEY, resultVO.getuum_usr_key());
        	//request.getSession().setAttribute(CommonConfig.SES_USER_ID_KEY, resultVO.getId());
        	request.getSession().setAttribute(CommonConfig.SES_USER_NAME_KEY, resultVO.getUum_usr_nam());
        	request.getSession().setAttribute(CommonConfig.SES_DIVISION_KEY, resultVO.getUum_div_cod());
        	request.getSession().setAttribute(CommonConfig.SES_DIVISION_NAME_KEY, resultVO.getDivision());
        	request.getSession().setAttribute(CommonConfig.SES_SERVICE_KEY, resultVO.getUum_svc_cod());
        	request.getSession().setAttribute(CommonConfig.SES_SERVICE_NAME_KEY, resultVO.getService());
        	request.getSession().setAttribute(CommonConfig.SES_DEPT_KEY, resultVO.getUum_dep_cod());
        	request.getSession().setAttribute(CommonConfig.SES_DEPT_NAME_KEY, resultVO.getDept());
        	request.getSession().setAttribute(CommonConfig.SES_USER_AUTH_KEY, resultVO.getUat_auh_gbn());
        	request.getSession().setAttribute(CommonConfig.SES_USER_APV_AUTH_KEY, resultVO.getUum_apv_gbn());
        	request.getSession().setAttribute(CommonConfig.SES_USER_COMPANY_KEY, company);
        	request.getSession().setAttribute(CommonConfig.SES_USER_COMPANY_DIV_KEY, company_div);
        	request.getSession().setAttribute(CommonConfig.SES_USER_COMPANY_SVC_KEY, company_svc);
        	request.getSession().setAttribute(CommonConfig.SES_USER_COMPANY_DEP_KEY, company_dep);
        	request.getSession().setAttribute(CommonConfig.SES_MAN_CYL_KEY, manCylVO.getUmm_man_cyl());
        	request.getSession().setAttribute(CommonConfig.SES_MAN_STD_KEY, manCylVO.getUmm_std_dat());
        	request.getSession().setAttribute(CommonConfig.SES_MAN_END_KEY, manCylVO.getUmm_end_dat());
        	request.getSession().setAttribute(CommonConfig.SES_PWD_CHG_YN, resultVO.getPwd_chg_yn());

        	request.getSession().setAttribute(CommonConfig.SES_ST_ORG, resultVO.getStOrg());
        	request.getSession().setAttribute(CommonConfig.SES_HQ_ORG, resultVO.getHqOrg());
        	request.getSession().setAttribute(CommonConfig.SES_GP_ORG, resultVO.getGpOrg());


        	log.info("###############################"+CommonConfig.SES_USER_NAME_KEY+" 접속");

        	// 접속로그 생성
        	LoginVO logVO = new LoginVO();
        	logVO.setUlm_log_gbn(CommonConfig.SES_LOGIN_S);		// login success
        	logVO.setId(resultVO.getId());
        	logVO.setPassword(loginVO.getPassword());
        	logVO.setUlm_lin_ip(request.getRemoteAddr());


        	loginService.insertLogInfo(logVO);

        	// 로그인 성공 시 실패횟수 초기화
        	EgovMap map = new EgovMap();
    		map.put("failCnt", "0");
    		map.put("useYn", "Y");
    		map.put("userKey", resultVO.getuum_usr_key());
    		loginService.setLoginFailCnt(map);

    		String mainUrl = "/FM-MAIN.do";
    		String prevUrl = request.getParameter("prevUrl");

    		if(prevUrl == null || prevUrl.equals("") || prevUrl.equals("null")){
    			// 2017-10-11, 로그인 후 첫 페이지
    			if (resultVO.getUat_def_mnu() != null) {
    				mainUrl = resultVO.getUat_def_mnu();
    			}
    		}
    		else {
    			mainUrl = prevUrl;
    		}
    		return "redirect:"+mainUrl;

        } else {
        	String userKey = loginService.isExistId(loginVO.getId());

        	if(userKey != null){
        		// 접속로그 생성
            	LoginVO logVO = new LoginVO();
            	logVO.setUlm_log_gbn(CommonConfig.SES_LOGIN_F);		// login fail
            	logVO.setId(loginVO.getId());
            	logVO.setPassword(loginVO.getPassword());
            	logVO.setUlm_lin_ip(request.getRemoteAddr());

            	loginService.insertLogInfo(logVO);

            	// 해당 userKey의 login 실패 횟수 갖고오기
            	String failCnt = loginService.getLoginFailCnt(userKey);

            	// login 실패횟수 더하기
            	int fail_cnt = (Integer.parseInt(failCnt) + 1);
            	log.info("########"+loginVO.getId()+" login 실패횟수 : "+fail_cnt);

            	EgovMap map = new EgovMap();
        		map.put("failCnt", fail_cnt);
        		map.put("useYn", "Y");
        		map.put("userKey", userKey);

            	// 실패횟수가 10이면 사용자 잠금(uum_use_yn을 'N'으로)
            	if(fail_cnt == 5){
            		map.put("useYn", "L");
            		loginService.setLoginFailCnt(map);

            		model.addAttribute("message", "SetLockId");
            	}else if(fail_cnt > 5){
            		map.put("useYn", "L");
            		loginService.setLoginFailCnt(map);

            		model.addAttribute("message", "Locked");
            	}else{
            		loginService.setLoginFailCnt(map);
            		model.addAttribute("message", "F");
            	}
        	}
        	else{
//        		model.addAttribute("message", "NotExistUser");
        		model.addAttribute("message", "F");
        	}
        	return "redirect:/index.jsp";
        }
    }

    @RequestMapping(value="/logout.do")
    public String logout(HttpServletRequest request) throws Exception {

    	request.getSession().removeAttribute("loginVO");
    	request.getSession().removeAttribute(CommonConfig.SES_USER_ID_KEY);
    	request.getSession().removeAttribute(CommonConfig.SES_USER_NAME_KEY);
    	request.getSession().removeAttribute(CommonConfig.SES_DIVISION_KEY);
    	request.getSession().removeAttribute(CommonConfig.SES_DIVISION_NAME_KEY);
    	request.getSession().removeAttribute(CommonConfig.SES_SERVICE_KEY);
    	request.getSession().removeAttribute(CommonConfig.SES_SERVICE_NAME_KEY);
//    	request.getSession().removeAttribute(CommonConfig.SES_USER_AUTH_KEY);

    	request.getSession().invalidate();

    	return "redirect:/index.jsp";
    }

    @RequestMapping(value="/GetManCyl.do")
	public ModelAndView getManCyl (@RequestParam("gubun") String gubun, @RequestParam("mancyl") String manCyl, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

        int manCylInt = Integer.parseInt(manCyl);
        if(gubun.equals("pre")){
        	manCylInt = manCylInt-1;
        }else if(gubun.equals("next")){
        	manCylInt = manCylInt+1;
        }
        manCyl = manCylInt+"";

        LoginVO loginVO = new LoginVO();
        loginVO.setUmm_man_cyl(manCyl);
        loginVO.setGubun(gubun);

        LoginVO manCylVO = loginService.getManCyl(loginVO);

        req.getSession().setAttribute(CommonConfig.SES_MAN_CYL_KEY, manCylVO.getUmm_man_cyl());
        req.getSession().setAttribute(CommonConfig.SES_MAN_STD_KEY, manCylVO.getUmm_std_dat());
        req.getSession().setAttribute(CommonConfig.SES_MAN_END_KEY, manCylVO.getUmm_end_dat());

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
        resultMap.put("result", manCylVO);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

    @RequestMapping(value="/SSO_LOGIN.do")
	public String FM_SSO_LOGIN(ModelMap model,
			final HttpServletRequest req, HttpServletResponse res) throws Exception {

		String userId = req.getParameter("userId");
		// 2017-09-25
		userId = userId.trim().replace(" ", "+");
		//userId = "SKB2300";
		//userId = "B3eekp8/WVte53a/a0gabw==";
		String decString = Crypto.decrypt(userId);
		log.debug("userId: {}", decString);
		EgovMap map = loginService.getUser(decString);

		if (map.size()>0) {
			LoginVO vo = new LoginVO();
			vo.setId((String)map.get("uumUsrId"));
			vo.setPassword((String)map.get("uumUsrPwd"));
			vo.setIsSso(true);

			return actionLogin(vo, req, model);
		}
		else {
			model.addAttribute("message", "F");
			return "redirect:/index.jsp";
		}
	}

    @RequestMapping(value="/changePassword.do")
	public String changePassword(ModelMap model,
			final HttpServletRequest req, HttpServletResponse res) throws Exception {

    	Map<String, String> map = new HashMap<String, String>();

		String usrKey = (String)req.getSession().getAttribute(CommonConfig.SES_USER_KEY);
    	String usrId = (String)req.getSession().getAttribute(CommonConfig.SES_USER_ID_KEY);
		String password = req.getParameter("password");

    	map.put("usrKey", usrKey);
    	map.put("password", password);
    	String result = loginService.changePassword(map);

    	// 기존과 동일한 비밀번호는 사용하실 수 없습니다.
    	if (result.equals("D")) {
    		model.addAttribute("message", "D");
    		return "redirect:/changePassword.jsp";
    	}
    	else {
    		LoginVO vo = new LoginVO();
			vo.setId(usrId);
			vo.setPassword(password);

    		return actionLogin(vo, req, model);
    	}
	}
}
