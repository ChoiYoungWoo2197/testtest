package com.uwo.isms.service.impl;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.uwo.isms.service.EgovUserDetailsService;
import com.uwo.isms.web.FMSetupController;

/**
 *
 * @author 공통서비스 개발팀 서준식
 * @since 2011. 6. 25.
 * @version 1.0
 * @see
 *
 * <pre>
 * 개정이력(Modification Information)
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2011. 8. 12.    서준식        최초생성
 *
 *  </pre>
 */

@Service
public class EgovUserDetailsSessionServiceImpl extends EgovAbstractServiceImpl implements EgovUserDetailsService {

	Logger log = LogManager.getLogger(FMSetupController.class);

	public Object getAuthenticatedUser() {
		if (RequestContextHolder.getRequestAttributes() == null) {
			return null;
		}

		return RequestContextHolder.getRequestAttributes().getAttribute("loginVO", RequestAttributes.SCOPE_SESSION);

	}

	/*
	 * 메뉴 접근 권한 확인
	 * 1. menuAuthList 에 등록이 안된 URL은 무조건 true
	 * 2. url에 _RW 가 포함되어 있을 경우 mnuRgt = Y' 는 true
	 * 3. 아닐 경우 mnuKey = 'Y' 는 true
	 */
	public Boolean getAuthoritiesChk(HttpServletRequest req) throws Exception {

		boolean chk = true;

		String urlMappign = req.getRequestURI();

		/*
		* 20190812 ageofsys
		*
		* 메뉴 접근 권한을 URI 기준으로 판단하고 있다.
		* 문제는 /login/\*, index.jsp 를 제외한 모든 요청을 메뉴 접근 권한 체크에 포함시키고 있다는 것이다.
		* 위 이슈로 인해 발생하는 문제들을 URI에 RW를 붙이거나, URI 문자열과 jsp 문자열을 일치시키는 방법으로 해결하고 있다.
		* 그 중 URI 문자열과 jsp 문자열을 일치시키는 방법은 여러가지 문제점들이 있다.
		* jsp 파일 요청은 메뉴 권한 체크를 진행하지 않게 수정한다.
		 * */
		if (urlMappign.endsWith(".jsp"))
			return chk;

		// 권한 설정을 리턴한다.
		List<?> listAuth = (List<?>) RequestContextHolder.getRequestAttributes().getAttribute("menuAuthList", RequestAttributes.SCOPE_SESSION);

		/*
		String urlMappign = req.getRequestURI();
		String valueMappign = urlMappign.substring(urlMappign.lastIndexOf("/"), urlMappign.lastIndexOf(".")-1);
		if(urlMappign.indexOf("_") > 0){
			valueMappign = urlMappign.substring(urlMappign.lastIndexOf("/"), urlMappign.indexOf("_")-1);
		}
		Map<String, Object> authList = null;
		for(int i=0; i<listAuth.size();i++){
			authList = (Map<String, Object>)listAuth.get(i);
			String authUrl = authList.get("ummMnuUrl").toString();
			String ummMnuUrl = "";
			if(authUrl.lastIndexOf("/") > 0){
				ummMnuUrl = authUrl.substring(authUrl.lastIndexOf("/"), authUrl.lastIndexOf(".")-1);;
			}

			if(valueMappign.equals(ummMnuUrl) && authList.get("mnuKey").equals("Y")){
				chk = true;
			}
		}*/


		String valueMappign = urlMappign.substring(urlMappign.lastIndexOf("/") + 1, urlMappign.lastIndexOf("."));
		boolean chkRW = false;

		if (urlMappign.indexOf("_RW") > 0) {
			chkRW = true;
		}
		if (urlMappign.indexOf("_") > 0) {
			valueMappign = urlMappign.substring(urlMappign.lastIndexOf("/") + 1, urlMappign.indexOf("_"));
		}

		Map<String, Object> authList = null;
		for(int i = 0; i < listAuth.size(); i++) {
			authList = (Map<String, Object>)listAuth.get(i);
			String authUrl = authList.get("ummMnuUrl").toString();
			String ummMnuUrl = "";

			if(authUrl.lastIndexOf("/") > 0) {
				ummMnuUrl = authUrl.substring(authUrl.lastIndexOf("/") + 1, authUrl.lastIndexOf("."));
			} else {
				ummMnuUrl = authUrl.substring(0, authUrl.lastIndexOf("."));
			}

			if (valueMappign.equals(ummMnuUrl)) {
				chk = false;
				if (chkRW) {
					if (authList.get("mnuRgt").equals("Y")) {
						chk = true;
						break;
					}
				} else if (authList.get("mnuKey").equals("Y")) {
					chk = true;
					break;
				}
			}
		}
		//log.info("urlMappign: {}, valueMappign: {}", urlMappign, valueMappign);
		return chk;
	}

	public List<String> getAuthorities() {

		// 권한 설정을 리턴한다.
		List<String> listAuth = new ArrayList<String>();

		return listAuth;
	}

	public Boolean isAuthenticated() {
		// 인증된 유저인지 확인한다.

		if (RequestContextHolder.getRequestAttributes() == null) {
			return false;
		} else {

			if (RequestContextHolder.getRequestAttributes().getAttribute("loginVO", RequestAttributes.SCOPE_SESSION) == null) {
				return false;
			} else {
				return true;
			}
		}

	}

}
