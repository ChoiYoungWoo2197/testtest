package com.uwo.isms.interceptor;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.uwo.isms.util.EgovUserDetailsHelper;

public class MaintenanceInterceptor extends HandlerInterceptorAdapter{

	private static final Logger log = LogManager.getLogger(MaintenanceInterceptor.class);
	private static final String indexPage = "/index.jsp?prevUrl=";
	private static final String errorPage = "/common/error403.jsp";

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler)
	    throws Exception {

		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			log.info("**************************** MaintenanceInterceptor isAuthenticated: {}", request.getRequestURL());
			String refer = URLEncoder.encode(request.getRequestURI(), "UTF-8");
			response.sendRedirect(indexPage + refer);
			return false;
		}

		Boolean getAuthoritiesChk = EgovUserDetailsHelper.getAuthoritiesChk(request);
		if(!getAuthoritiesChk) {
			log.info("**************************** MaintenanceInterceptor getAuthoritiesChk: {}", request.getRequestURL());
			response.sendRedirect(errorPage);
			return false;
		}

		return true;
	}

}