/**
 ***********************************
 * @source ReportController.java
 * @date 2015. 08. 24.
 * @project isms3
 * @description JasperReport controller
 ***********************************
 */
package com.uwo.isms.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.uwo.isms.common.CommonConfig;
import com.uwo.isms.domain.SearchVO;
import com.uwo.isms.service.FMAssetService;
import com.uwo.isms.service.FMCompsService;
import com.uwo.isms.service.FMInsptService;
import com.uwo.isms.service.FMMworkService;
import com.uwo.isms.service.FMRiskmService;
import com.uwo.isms.service.FMSetupService;
import com.uwo.isms.service.ReportService;
import com.uwo.isms.util.EgovUserDetailsHelper;

@Controller
@RequestMapping("/report")
public class ReportController {

	Logger log = LogManager.getLogger(FMSetupController.class);

	@Resource(name = "fmCompsService")
	private FMCompsService fmCompsService;

	@Resource(name = "fmMworkService")
	private FMMworkService fmMworkService;

	@Resource(name = "fmSetupService")
	private FMSetupService fmSetupService;

	@Resource(name = "fmInsptService")
	private FMInsptService fmInsptService;

	@Resource(name = "fmRiskmService")
	private FMRiskmService fmRiskmService;

	@Resource(name = "fmAssetService")
	private FMAssetService fmAssetService;

	@Resource(name = "reportService")
	private ReportService reportService;

	@RequestMapping(value="/FM-MWORK001.do")
	public ModelAndView mwork001_report(HttpServletRequest req, ModelAndView modelAndView) throws Exception {

		java.util.Calendar cal = java.util.Calendar.getInstance();

		String sYear = req.getParameter("sYear");
		String sMonth = req.getParameter("sMonth");

		int iYear = cal.get(java.util.Calendar.YEAR);
		int iMonth = cal.get(java.util.Calendar.MONTH)+1;
		int iDate = cal.get(java.util.Calendar.DATE);

        if(sYear == null || sMonth == null || sYear == "" || sMonth == ""){
        		sYear = Integer.toString(iYear);
        }else{
                iYear = Integer.parseInt(sYear);
                iMonth = Integer.parseInt(sMonth);
        }

        sMonth = Integer.toString(iMonth).length() == 1 ? "0" + Integer.toString(iMonth) : Integer.toString(iMonth);

        Map map = new HashMap();

        map.put("manCyl",(String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
        map.put("division", (String) req.getSession().getAttribute(CommonConfig.SES_DIVISION_KEY));
        map.put("worker", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
        map.put("iMonth", iMonth);
        map.put("iYear", iYear);

        List<?> calendarList = fmMworkService.getCalendarList(map);
        List<?> resultList = fmMworkService.getWorkList(map);

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        list = reportService.mwork001_report(calendarList, resultList, sYear, sMonth, iYear, iMonth);

		JRDataSource JRdataSource = new JRBeanCollectionDataSource(list);

        Map<String,Object> parameterMap = new HashMap<String,Object>();

        parameterMap.put("datasource", JRdataSource);

        modelAndView = new ModelAndView("mwork001Report", parameterMap);

        return modelAndView;
	}

	@RequestMapping(value="/FM-COMPS003.do")
	public ModelAndView comps003_report(HttpServletRequest req, ModelAndView modelAndView) throws Exception {

		Map<String, String> map = new HashMap<String, String>();
		map.put("standard", req.getParameter("standard"));
		map.put("service", req.getParameter("service"));
		map.put("ucmBcyCod", (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		List<?> fileList = fmCompsService.fm_comps003_3D_mappinglist();

		List<?> resultList = fmCompsService.fm_comps003_grid(map);

		List<?> hisList = fmCompsService.fm_comps003_report();

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        list = reportService.comps003_report(fileList, resultList, hisList);

		JRDataSource JRdataSource = new JRBeanCollectionDataSource(list);

        Map<String,Object> parameterMap = new HashMap<String,Object>();

        parameterMap.put("datasource", JRdataSource);

        modelAndView = new ModelAndView("comps003Report", parameterMap);

        return modelAndView;
	}

	@RequestMapping(value="/FM-SETUP020.do")
	public ModelAndView setup020_report(HttpServletRequest req, ModelAndView modelAndView) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uvmSvcKey", req.getParameter("uvmSvcKey"));

		Map<String, String> resultList = fmSetupService.fm_setup020_view(map);

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        list.add(resultList);

		JRDataSource JRdataSource = new JRBeanCollectionDataSource(list);

        Map<String,Object> parameterMap = new HashMap<String,Object>();

        parameterMap.put("datasource", JRdataSource);

        modelAndView = new ModelAndView("setup020Report", parameterMap);

        return modelAndView;
	}

	@RequestMapping(value="/FM-INSPT002.do")
	public ModelAndView inspt002_report(HttpServletRequest req, ModelAndView modelAndView, @RequestParam Map<String, String> paramMap) throws Exception {
		paramMap.put("ufmBcyCod", (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

		List<?> resultList = fmInsptService.fm_inspt002_report(paramMap);

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		list = reportService.inspt002_report(resultList, paramMap);

		JRDataSource JRdataSource = new JRBeanCollectionDataSource(list);

        Map<String,Object> parameterMap = new HashMap<String,Object>();

        parameterMap.put("datasource", JRdataSource);

        if(paramMap.get("ufmRstSta").equals("Y")){
        	modelAndView = new ModelAndView("inspt002Report_C", parameterMap);
        }else{
        	modelAndView = new ModelAndView("inspt002Report_I", parameterMap);
        }

        return modelAndView;
	}

	@RequestMapping(value="/FM-INSPT002_D.do")
	public ModelAndView inspt002_D_report(HttpServletRequest req, ModelAndView modelAndView, @RequestParam Map<String, String> paramMap) throws Exception {
		paramMap.put("ufmBcyCod", (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

		List<?> resultList = fmInsptService.fm_inspt002_report(paramMap);

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		list = reportService.inspt002_C_report(resultList, paramMap);

		JRDataSource JRdataSource = new JRBeanCollectionDataSource(list);

        Map<String,Object> parameterMap = new HashMap<String,Object>();

        parameterMap.put("datasource", JRdataSource);

        modelAndView = new ModelAndView("inspt002Report_D", parameterMap);

        return modelAndView;
	}

	@RequestMapping(value="/FM-RISKM003.do")
	public ModelAndView riskm003_report (HttpServletRequest req, ModelAndView modelAndView, @RequestParam Map<String, String> paramMap) throws Exception {

		paramMap.put("bcyCod", (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		List<?> resultList = null;
		if(!paramMap.get("grpKey").equals("0")){
			paramMap.put("grpKey", "= " + paramMap.get("grpKey"));
			resultList = fmRiskmService.fm_riskm003_report(paramMap);
		}else{
			resultList = fmRiskmService.fm_riskm003_rskList(paramMap);
		}
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		list = reportService.riskm003_report(resultList, paramMap);

		JRDataSource JRdataSource = new JRBeanCollectionDataSource(list);

        Map<String,Object> parameterMap = new HashMap<String,Object>();

        parameterMap.put("datasource", JRdataSource);

//        modelAndView = new ModelAndView("riskm003Report", parameterMap);
        modelAndView = new ModelAndView("riskm003Report_KTL", parameterMap);

		return modelAndView;
	}

	@RequestMapping(value="/FM-ASSET002.do")
	public ModelAndView asset002_report(HttpServletRequest req, ModelAndView modelAndView, @RequestParam Map<String, String> paramMap, @ModelAttribute("searchVO") SearchVO searchVO) throws Exception {
		searchVO.setManCyl((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

		// 제목설정[S]
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

		HashMap<String,Object> param = new HashMap<String,Object>();

		param.put("sTitle", "3.2.4 자산 그룹핑 결과 (자세한 결과는 위험분석시트 참고)");

		list.add(param);

		JRDataSource JRdataSource = new JRBeanCollectionDataSource(list);

        Map<String,Object> parameterMap = new HashMap<String,Object>();

        parameterMap.put("datasource", JRdataSource);
        // 제목설정[E]

        List<?> resultList = fmAssetService.fm_asset002_list(searchVO);

    	List<Map<String, Object>> listDetail = new ArrayList<Map<String, Object>>();

    	listDetail = reportService.asset002_report(resultList, paramMap, searchVO);

    	JRDataSource JRdataSourceDetail = new JRBeanCollectionDataSource(listDetail);

    	parameterMap.put("subDetail", JRdataSourceDetail);

        String path = getClass().getResource("/").getPath();

    	JasperCompileManager.compileReportToFile(path + "report/asset002_Detail.jrxml");

    	modelAndView = new ModelAndView("asset002Report", parameterMap);

        return modelAndView;
	}

	@RequestMapping(value="/FM-ASSET002_I.do")
	public ModelAndView asset002_I_report (HttpServletRequest req, ModelAndView modelAndView, @RequestParam Map<String, String> paramMap) throws Exception {

		paramMap.put("bcyCod", (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		paramMap.put("grpKey", "0");

        List<?> resultList = fmRiskmService.fm_riskm003_rskList(paramMap);

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		list = reportService.asset002_I_report(resultList, paramMap);

		JRDataSource JRdataSource = new JRBeanCollectionDataSource(list);

        Map<String,Object> parameterMap = new HashMap<String,Object>();

        parameterMap.put("datasource", JRdataSource);

        modelAndView = new ModelAndView("asset002Report_I", parameterMap);

		return modelAndView;
	}

	@RequestMapping(value="/FM-ASSET002_T.do")
	public ModelAndView asset002_T_report (HttpServletRequest req, ModelAndView modelAndView, @RequestParam Map<String, String> paramMap) throws Exception {

		paramMap.put("bcyCod", (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		paramMap.put("grpKey", "NOT IN(0,10)");

		// 제목설정[S]
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

		HashMap<String,Object> param = new HashMap<String,Object>();

		param.put("sTitle", "7.1.2 기술적 보안 영역(Server, Network, 전자정보, 정보보호시스템 등)");

		list.add(param);

		JRDataSource JRdataSource = new JRBeanCollectionDataSource(list);

        Map<String,Object> parameterMap = new HashMap<String,Object>();

        parameterMap.put("datasource", JRdataSource);
        // 제목설정[E]

        List<?> resultList = fmRiskmService.fm_riskm003_t_report(paramMap);

    	List<Map<String, Object>> listDetail = new ArrayList<Map<String, Object>>();

    	listDetail = reportService.asset002_T_report(resultList, paramMap);

    	JRDataSource JRdataSourceDetail = new JRBeanCollectionDataSource(listDetail);

    	parameterMap.put("subDetail", JRdataSourceDetail);

        String path = getClass().getResource("/").getPath();

    	JasperCompileManager.compileReportToFile(path + "report/asset002_T_Detail.jrxml");

        modelAndView = new ModelAndView("asset002Report_T", parameterMap);

		return modelAndView;
	}

	@RequestMapping(value="/FM-INSPT004.do")
	public ModelAndView inspt004_report(HttpServletRequest req, ModelAndView modelAndView) throws Exception {

		Map<String, String> map = new HashMap<String, String>();
		map.put("standard", req.getParameter("standard"));
		map.put("service", req.getParameter("service"));
		map.put("ucm1lvCod", req.getParameter("ucm1lvCod"));
		map.put("ucmBcyCod", (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		// 2017-07-26, 업무완료
		map.put("utwWrkSta", "90");
		List<?> resultList = fmInsptService.fm_inspt004_list(map);

		// 2017-07-26, 증적파일이 누락되어 있었음
		map.put("manCyl", (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
	    map.put("msrYn", "Y");

		List<?> fileList = fmInsptService.fm_inspt001_getFileList(map);
		List<EgovMap> saFileList = fmInsptService.getMeasureSaFile(map);
//		List<?> fileList = fmInsptService.fm_inspt001_getFileList_by_department_of_service(map);

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

//        list = reportService.inspt004_report(resultList, fileList, saFileList);
        list = reportService.inspt004_report(resultList, new ArrayList(), saFileList);

		JRDataSource JRdataSource = new JRBeanCollectionDataSource(list);

		Map<String,Object> parameterMap = new HashMap<String,Object>();

		parameterMap.put("datasource", JRdataSource);

        modelAndView = new ModelAndView("inspt004Report", parameterMap);

        return modelAndView;
	}


}
