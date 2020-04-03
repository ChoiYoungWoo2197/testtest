/**
 ***********************************
 * @source CommonConfig.java
 * @date 2014. 10. 15.
 * @project isms3_godohwa
 * @description
 ***********************************
 */
package com.uwo.isms.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.hsqldb.lib.StringUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.uwo.isms.service.CommonCodeService;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public class SelectBoxTagHandler extends TagSupport {

	private static final long serialVersionUID = 1L;

	private CommonCodeService commonCodeService;

	private SessionHandler sessionHandler;

	private String name = "";
	private String code = "";
	private String value = "";
	private String type = "";
	private String event = "";
	private String allText = "";
	private String classes = "";
	private String forbidden = "false";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getAllText() {
		return allText;
	}

	public void setAllText(String allText) {
		this.allText = allText;
	}

	public String getClasses() {
		return classes;
	}

	public void setClasses(String classes) {
		this.classes = classes;
	}

	public String getForbidden() {
		return forbidden;
	}

	public void setForbidden(String forbidden) {
		this.forbidden = forbidden;
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			ApplicationContext ctx = WebApplicationContextUtils
					.getWebApplicationContext(pageContext.getServletContext());
			commonCodeService = ctx.getBean(CommonCodeService.class);
			sessionHandler = ctx.getBean(SessionHandler.class);

			String sesService = (String) sessionHandler.getCurrentSession().getAttribute(CommonConfig.SES_SERVICE_KEY);
			String sesDivision = (String) sessionHandler.getCurrentSession().getAttribute(CommonConfig.SES_DIVISION_KEY);
			String sesDept = (String) sessionHandler.getCurrentSession().getAttribute(CommonConfig.SES_DEPT_KEY);
			String sesAuth = (String) sessionHandler.getCurrentSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);

			String sesStOrg = (String) sessionHandler.getCurrentSession().getAttribute(CommonConfig.SES_ST_ORG);
			String sesHqOrg = (String) sessionHandler.getCurrentSession().getAttribute(CommonConfig.SES_HQ_ORG);
			String sesGpOrg = (String) sessionHandler.getCurrentSession().getAttribute(CommonConfig.SES_GP_ORG);

			JspWriter out = pageContext.getOut();
			StringBuffer strHtml = new StringBuffer();
			List<?> list = new ArrayList();

			boolean allFlg = false;
			String attr = "";

			if ("DIV".equals(type)) {
				Map<String, String> map = new HashMap<String, String>();
				list = commonCodeService.getServiceAuthList(map);
				allFlg = true;
				if (!"A".equals(sesAuth)) {
					value = sesDivision;
				} else {
					value = "";
				}
			// 서비스 1차 카테고리
			// 2016-10-24 2차에서 1차로 내용 변경
			} else if ("S".equals(type)) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("sesServiceKey", sesService);
				map.put("sesAuthKey", sesAuth);
				list = commonCodeService.getServiceAuthList_N(map);
				allFlg = true;
				if (value == null) {
					if (!"A".equals(sesAuth)) {
						value = sesService;
					} else {
						value = "";
					}
				}
			// 2016-10-24 서비스 2차 카테고리
			} else if ("S2".equals(type)) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("sesServiceKey", sesService);
				map.put("sesAuthKey", sesAuth);
				list = commonCodeService.getServiceAuthList(map);
				allFlg = true;
				if (value == null) {
					if (!"A".equals(sesAuth)) {
						value = sesService;
					} else {
						value = "";
					}
				}

			} else if ("CAT".equals(type)) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("sesServiceKey", sesService);
				map.put("sesAuthKey", sesAuth);
				list = commonCodeService.getServiceAuthList_CAT(map);
				allFlg = true;

			} else if ("SNA".equals(type)) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("sesServiceKey", sesService);
				map.put("sesAuthKey", sesAuth);
				list = commonCodeService.getServiceAuthList(map);
				// dept select box
			} else if ("D".equals(type)) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("sesDeptKey", sesDept);
				map.put("sesAuthKey", sesAuth);
				list = commonCodeService.getDeptAuthList(map);

				if (!"P".equals(map.get("sesAuthKey"))) {
					allFlg = true;
				}
			} else if ("stOrg".equals(type)) {
				list = commonCodeService.getDeptCodeList("1");
				allFlg = true;
				if (!"A".equals(sesAuth)) {
					value = sesStOrg;
				}
			} else if ("hqOrg".equals(type)) {
				list = commonCodeService.getDeptCodeList("2");
				allFlg = true;
				if (!"A".equals(sesAuth)) {
					value = sesHqOrg;
				}
			} else if ("gpOrg".equals(type)) {
				list = commonCodeService.getDeptCodeList("3");
				allFlg = true;
				if (!"A".equals(sesAuth)) {
					value = sesGpOrg;
				}
			} else if ("A".equals(type)) {
				list = commonCodeService.getCommonCodeList(code);
				allFlg = true;
			} else {
				list = commonCodeService.getCommonCodeList(code);
				allFlg = false;
			}

			if (!"A".equals(sesAuth) && forbidden.equals("true")) {
				attr = "disabled='disabled'";
			}

			// tag 생성
			if (StringUtil.isEmpty(event)) {
				strHtml.append("<select id='" + name + "' name='" + name + "' class='selectBox " + classes + "' " + attr +">");
			} else {
				strHtml.append(
						"<select id='" + name + "' name='" + name + "' class='selectBox " + classes + "' onchange='" + event + "' " + attr +">");
			}
			if (allFlg) {
				if (StringUtil.isEmpty(allText)) {
					strHtml.append("<option value=''>전체</option>");
				} else {
					strHtml.append("<option value=''>" + allText + "</option>");
				}
			}

			for (int i = 0; i < list.size(); i++) {
				EgovMap map = (EgovMap) list.get(i);
				String optionClass = "";
				if (map.get("className") != null) {
					optionClass = "class='" + map.get("className").toString() + "'";
				}
				if (map.get("code").equals(value)) {
					strHtml.append("<option value='" + map.get("code") + "' " + optionClass + " selected='selected'>" + map.get("name")
							+ "</option>");
				} else {
					strHtml.append("<option value='" + map.get("code") + "' " + optionClass + ">" + map.get("name") + "</option>");
				}
			}
			strHtml.append("</select>");
			out.print(strHtml.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}
}
