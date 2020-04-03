package com.uwo.isms.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component("sessionHandler")
public class SessionHandler {    
    /**
     * 현재 Request 리턴
     * @return
     */
    public HttpServletRequest getCurrentRequest(){
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    }
    
    /**
     * 현재 Session 리턴
     * @return
     */
    public HttpSession getCurrentSession(){
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();
    }
}
