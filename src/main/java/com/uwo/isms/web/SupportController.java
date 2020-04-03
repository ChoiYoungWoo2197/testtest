package com.uwo.isms.web;

import com.uwo.isms.domain.LoginVO;
import com.uwo.isms.util.EgovUserDetailsHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller 에서 반복적으로 사용되는 기능들을 상속형태로 지원하기위한 클래스
 *
 * @author ageofsys
 * @since 1.0
 */
public class SupportController {

    protected Logger log = LogManager.getLogger(this.getClass());

    /**
     * json 형태의 응답을 하기위한 ModelAndView 객체를 생성하고 반환한다.
     *
     * @return jsonView 를 사용하는 ModelAndView
     */
    protected ModelAndView makeJsonResponse() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");
        return modelAndView;
    }

    protected LoginVO currentLoggedInUser() {
        return (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
    }

}
