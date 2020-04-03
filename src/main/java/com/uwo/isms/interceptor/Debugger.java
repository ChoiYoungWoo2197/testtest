package com.uwo.isms.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Debugger extends HandlerInterceptorAdapter {

    private org.apache.logging.log4j.Logger log = LogManager.getLogger(Debugger.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (log.isDebugEnabled()) {

            log.debug("==========================================================================================");
            log.debug("===================================== REQUEST START ======================================");
            log.debug("==========================================================================================");
            log.debug("=== Protocol : " + request.getProtocol());
            log.debug("=== URI : " + request.getRequestURI());
            log.debug("=== Method : " + request.getMethod());

            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                String headerValue = request.getHeader(headerName);
                log.debug("=== Header : " + headerName + " = " + headerValue);
            }

            Map parameters = request.getParameterMap();
            Set parameterEntrySet = parameters.entrySet();
            Iterator parameterIterator = parameterEntrySet.iterator();

            while (parameterIterator.hasNext()) {
                Map.Entry<String, String[]> entry = (Map.Entry<String, String[]>) parameterIterator.next();
                String key = entry.getKey();
                String[] value = entry.getValue();

                log.debug("=== Parameter : " + key + " = " + StringUtils.join(value, ","));
            }

            log.debug("==========================================================================================");

        }

        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        super.postHandle(request, response, handler, modelAndView);

        if (log.isDebugEnabled()) {
            log.debug("====================================== REQUEST END =======================================");
        }

    }
}
