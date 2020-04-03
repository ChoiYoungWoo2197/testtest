/**
 ***********************************
 * @source LoginController.java
 * @date 2014. 10. 13.
 * @project isms3
 * @description
 ***********************************
 */
package com.uwo.isms.web;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uwo.isms.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.uwo.isms.common.CommonConfig;
import com.uwo.isms.domain.SearchVO;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;


@Controller
@RequestMapping("/excel")
public class ExcelController {

	Logger log = LogManager.getLogger(FMSetupController.class);

	/** LoginService */
	@Resource(name = "excelService")
	ExcelService excelService;

	@Resource(name = "commonCodeService")
	CommonCodeService commonCodeService;

	@Autowired
	FMAssetService fmAssetService;

	@Autowired
	FMMworkService fmMworkService;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;

	@Resource(name = "fmInsptService")
	FMInsptService fmInsptService;

	@RequestMapping(value="/FM-MWORK001.do")
    public ModelAndView mwork001_excel(@ModelAttribute("searchVO") SearchVO searchVO
    		,final HttpServletRequest req, HttpServletResponse res) throws Exception {

    	new GregorianCalendar();
    	req.getSession().getAttribute(CommonConfig.SES_USER_ID_KEY);

    	String fileName = "정보보호일정표.xls";

		res.setHeader("Content-Transfer-Encoding:", "binary");
		res.setContentType("application/vnd.ms-excel;charset=euc-kr");
		res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("ksc5601"), "8859_1")  + ";");
		res.setHeader("Pragma", "no-cache;");
		res.setHeader("Expires", "-1;");



    	Map sMap = new HashMap();
    	String manCyl = (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY);
    	String key = (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY);
    	String auth = (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);
//    	String compareDate = req.getParameter("sYear")+"0101";

    	log.info(auth);

    	sMap.put("key", key);
    	sMap.put("manCyl", manCyl);
    	sMap.put("auth", auth);
//    	sMap.put("compareDate", compareDate);
    	sMap.put("sYear", req.getParameter("sYear"));

    	System.out.println(sMap);

        List<?> workList = excelService.getCycleWorkList(sMap);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("workList", workList);

    	return new ModelAndView("mwork001ExcelView", map);
    }

    @RequestMapping(value="/FM-MWORK002.do")
    public ModelAndView mwork002_excel(@ModelAttribute("searchVO") SearchVO searchVO
    		,final HttpServletRequest req, HttpServletResponse res) throws Exception {

    	new GregorianCalendar();
    	req.getSession().getAttribute(CommonConfig.SES_USER_ID_KEY);

    	String fileName = "정보보호업무현황(개인).xls";

		res.setHeader("Content-Transfer-Encoding:", "binary");
		res.setContentType("application/vnd.ms-excel;charset=euc-kr");
		res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("ksc5601"), "8859_1")  + ";");
		res.setHeader("Pragma", "no-cache;");
		res.setHeader("Expires", "-1;");

		String mode = "P";
        req.getParameter("division");

        searchVO.setManCyl((String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
        if(mode.equals("P")){
        	searchVO.setWorker((String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
        }else if(mode.equals("D")){
        	searchVO.setDivision((String) req.getSession().getAttribute(CommonConfig.SES_DIVISION_KEY));
        	searchVO.setWorker((String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
        }


    	searchVO.setManCyl((String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

        // 미완료업무 리스트
    	searchVO.setIsDelay("N");
        List<?> noWorkList = excelService.getNoWorkList(searchVO);
        // 완료업무 리스트
        List<?> comWorkList = excelService.getComWorkList(searchVO);

        //지연업무 리스트
        searchVO.setIsDelay("Y");
        List<?> delayWorkList = excelService.getNoWorkList(searchVO);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("noWorkList", noWorkList);
		map.put("comWorkList", comWorkList);
		map.put("delayWorkList", delayWorkList);

    	return new ModelAndView("mwork002ExcelView", map);
    }

    @RequestMapping(value="/FM-MWORK003.do")
    public ModelAndView mwork003_excel(@ModelAttribute("searchVO") SearchVO searchVO
    		,final HttpServletRequest req, HttpServletResponse res) throws Exception {

    	new GregorianCalendar();
    	req.getSession().getAttribute(CommonConfig.SES_USER_ID_KEY);

    	String fileName = "나의수행업무.xls";

		res.setHeader("Content-Transfer-Encoding:", "binary");
		res.setContentType("application/vnd.ms-excel;charset=euc-kr");
		res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("ksc5601"), "8859_1")  + ";");
		res.setHeader("Pragma", "no-cache;");
		res.setHeader("Expires", "-1;");

    	String searchCondition = searchVO.getSearchCondition();
        if(searchCondition.equals("workName")){
        	searchVO.setWorkName(searchVO.getSearchKeyword());
        }else if(searchCondition.equals("goalNum")){
        	searchVO.setGoalNum(searchVO.getSearchKeyword());
        }

        searchVO.setManCyl((String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
        searchVO.setDivision((String) req.getSession().getAttribute(CommonConfig.SES_DIVISION_KEY));
        searchVO.setWorker((String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));


        // 업무리스트
        List<?> workList = excelService.getWorkList(searchVO);

        Map<String, Object> map = new HashMap<String, Object>();
		map.put("workList", workList);

		return new ModelAndView("mwork003ExcelView", map);
    }

    @RequestMapping(value="/FM-MWORK004.do")
    public ModelAndView mwork004_excel(@ModelAttribute("searchVO") SearchVO searchVO
    		,final HttpServletRequest req, HttpServletResponse res) throws Exception {

    	new GregorianCalendar();
    	req.getSession().getAttribute(CommonConfig.SES_USER_ID_KEY);

    	String fileName = "통제항목별업무현황.xls";

		res.setHeader("Content-Transfer-Encoding:", "binary");
		res.setContentType("application/vnd.ms-excel;charset=euc-kr");
		res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("ksc5601"), "8859_1")  + ";");
		res.setHeader("Pragma", "no-cache;");
		res.setHeader("Expires", "-1;");

        searchVO.setManCyl((String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

        // 업무리스트
        List<?> workList = excelService.getStdWorkList(searchVO);

        Map<String, Object> map = new HashMap<String, Object>();
		map.put("workList", workList);

		return new ModelAndView("mwork004ExcelView", map);
    }

    @RequestMapping(value="/FM-MWORK005.do")
    public ModelAndView mwork005_excel(@ModelAttribute("searchVO") SearchVO searchVO
    		,final HttpServletRequest req, HttpServletResponse res) throws Exception {

    	new GregorianCalendar();
    	req.getSession().getAttribute(CommonConfig.SES_USER_ID_KEY);

    	String fileName = "담당자별업무현황_"+searchVO.getWorker()+".xls";

		res.setHeader("Content-Transfer-Encoding:", "binary");
		res.setContentType("application/vnd.ms-excel;charset=euc-kr");
		res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("ksc5601"), "8859_1")  + ";");
		res.setHeader("Pragma", "no-cache;");
		res.setHeader("Expires", "-1;");

        searchVO.setManCyl((String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

        //String[] cycleArr = {"Y","H","Q","T","M","W","D","N"};


        List<?> workList = excelService.getUserWorkList(searchVO);
        List<?> detailList = excelService.getUserDetailWorkList(searchVO);

        Map<String, Object> map = new HashMap<String, Object>();
		map.put("workList", workList);
		map.put("detailList", detailList);

		return new ModelAndView("mwork005ExcelView", map);
    }

    @RequestMapping(value="/FM-MWORK006.do")
    public ModelAndView mwork006_excel(@ModelAttribute("searchVO") SearchVO searchVO
    		,final HttpServletRequest req, HttpServletResponse res) throws Exception {

    	String fileName = "정보보호업무현황";
    	if(searchVO.getFindusr()!=null&&searchVO.getFindusr().equals("Y")){
			fileName += "(개인)";
		}else{
			fileName += "(부서)";
		}
    	fileName += ".xls";

		res.setHeader("Content-Transfer-Encoding:", "binary");
		res.setContentType("application/vnd.ms-excel;charset=euc-kr");
		res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("ksc5601"), "8859_1")  + ";");
		res.setHeader("Pragma", "no-cache;");
		res.setHeader("Expires", "-1;");


        String company = propertyService.getString("company");
        searchVO.setCompany(company);
        searchVO.setManCyl((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
   		searchVO.setAuth((String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));
		String findusr = (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY);

		searchVO.setFirstIndex(0);
		searchVO.setRecordCountPerPage(10000);

		if (searchVO.getFindusr() == null) {
			searchVO.setFindusr("");
		} else {
			searchVO.setFindusr(findusr);
		}

        // 완료업무 리스트
        searchVO.setWorkState("C");
        List<?> comWorkList = fmMworkService.getAllWorkList(searchVO);
        // 지연업무 리스트
        searchVO.setWorkState("D");
        List<?> delayWorkList = fmMworkService.getAllWorkList(searchVO);
        // 미완료업무 리스트
        searchVO.setWorkState("R");
        List<?> noWorkList = fmMworkService.getAllWorkList(searchVO);


		Map<String, Object> map = new HashMap<String, Object>();
		map.put("comWorkList", comWorkList);
		map.put("delayWorkList", delayWorkList);
		map.put("noWorkList", noWorkList);

    	return new ModelAndView("mwork006ExcelView", map);
    }

    @RequestMapping(value="/FM-MWORK007.do")
    public ModelAndView mwork007_excel(@ModelAttribute("searchVO") SearchVO searchVO
    		,final HttpServletRequest req, HttpServletResponse res) throws Exception {

    	new GregorianCalendar();
    	req.getSession().getAttribute(CommonConfig.SES_USER_ID_KEY);

    	String fileName = "사업부수행업무.xls";

		res.setHeader("Content-Transfer-Encoding:", "binary");
		res.setContentType("application/vnd.ms-excel;charset=euc-kr");
		res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("ksc5601"), "8859_1")  + ";");
		res.setHeader("Pragma", "no-cache;");
		res.setHeader("Expires", "-1;");

    	searchVO.setManCyl((String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

        // 업무리스트
        List<?> workList = excelService.getWorkList(searchVO);
        Map<String, Object> map = new HashMap<String, Object>();
		map.put("workList", workList);

		return new ModelAndView("mwork007ExcelView", map);
    }

    @RequestMapping(value="/FM-MWORK008.do")
    public ModelAndView mwork008_excel(@ModelAttribute("searchVO") SearchVO searchVO
    		,final HttpServletRequest req, HttpServletResponse res) throws Exception {

    	new GregorianCalendar();
    	req.getSession().getAttribute(CommonConfig.SES_USER_ID_KEY);

    	String fileName = "통제항목업무현황.xls";

		res.setHeader("Content-Transfer-Encoding:", "binary");
		res.setContentType("application/vnd.ms-excel;charset=euc-kr");
		res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("ksc5601"), "8859_1")  + ";");
		res.setHeader("Pragma", "no-cache;");
		res.setHeader("Expires", "-1;");

    	searchVO.setManCyl((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
        searchVO.setDivision((String)req.getSession().getAttribute(CommonConfig.SES_DIVISION_KEY));
        searchVO.setCompany(propertyService.getString("company"));
    	List<?> list = excelService.mwork008_excel(searchVO);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);

    	return new ModelAndView("mwork008ExcelView", map);
    }

    @RequestMapping(value="/FM-MWORK019.do")
    public ModelAndView mwork019_excel(@ModelAttribute("searchVO") SearchVO searchVO
    		,final HttpServletRequest req, HttpServletResponse res) throws Exception {

    	new GregorianCalendar();
    	req.getSession().getAttribute(CommonConfig.SES_USER_ID_KEY);

    	String fileName = "수행업무이력.xls";

		res.setHeader("Content-Transfer-Encoding:", "binary");
		res.setContentType("application/vnd.ms-excel;charset=euc-kr");
		res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("ksc5601"), "8859_1")  + ";");
		res.setHeader("Pragma", "no-cache;");
		res.setHeader("Expires", "-1;");

    	searchVO.setManCyl((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
    	searchVO.setCompany(propertyService.getString("company"));
    	List<?> list = excelService.mwork019_excel(searchVO);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);

    	return new ModelAndView("mwork019ExcelView", map);
    }

    /*
     * 2018-10-12 s, 미완료 업무
     */
    @RequestMapping(value="/FM-MWORK014.do")
    public ModelAndView mwork014_excel(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

    	new GregorianCalendar();
    	req.getSession().getAttribute(CommonConfig.SES_USER_ID_KEY);

    	String fileName = "미완료업무현황.xls";

		res.setHeader("Content-Transfer-Encoding:", "binary");
		res.setContentType("application/vnd.ms-excel;charset=euc-kr");
		res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("ksc5601"), "8859_1")  + ";");
		res.setHeader("Pragma", "no-cache;");
		res.setHeader("Expires", "-1;");

		String company = propertyService.getString("company");
		String manCyl = (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY);

		String stOrg = (String) req.getParameter("stOrg");
		String hqOrg = (String) req.getParameter("hqOrg");
		String gpOrg = (String) req.getParameter("gpOrg");

		searchVO.setCompany(company);
		searchVO.setManCyl(manCyl);
		searchVO.setStOrg(stOrg);
		searchVO.setHqOrg(hqOrg);
		searchVO.setGpOrg(gpOrg);

		List<?> list = fmMworkService.fm_mwork014_list(searchVO);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);

    	return new ModelAndView("mwork014ExcelView", map);
    }

	@RequestMapping(value="/FM-INSPT004.do")
	public ModelAndView inspt004_excel(@ModelAttribute("searchVO") SearchVO searchVO
			,final HttpServletRequest req, HttpServletResponse res) throws Exception {

		new GregorianCalendar();
		req.getSession().getAttribute(CommonConfig.SES_USER_ID_KEY);

		String fileName = "대책명세서";
		if(req.getParameter("ucm1lvCod").length()>0){
			fileName += "-통제분야_"+req.getParameter("ucm1lvCod");
		}
		fileName += ".xls";

		res.setHeader("Content-Transfer-Encoding:", "binary");
		res.setContentType("application/vnd.ms-excel;charset=euc-kr");
		res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("ksc5601"), "8859_1")  + ";");
		res.setHeader("Pragma", "no-cache;");
		res.setHeader("Expires", "-1;");

		Map<String, Object> mapInspt = new HashMap<String, Object>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("standard", req.getParameter("standard"));
		map.put("service", req.getParameter("service"));
		map.put("ucm1lvCod", req.getParameter("ucm1lvCod"));
		map.put("ucmBcyCod", (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		map.put("utwWrkSta", "90");
		mapInspt.put("result", fmInsptService.fm_inspt004_list(map));

		map.put("manCyl", (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		map.put("msrYn", "Y");

		mapInspt.put("file",fmInsptService.fm_inspt001_getFileList(map));
		mapInspt.put("saFile",fmInsptService.getMeasureSaFile(map));
//		mapInspt.put("file",fmInsptService.fm_inspt001_getFileList_by_department_of_service(map));

		return new ModelAndView("inspt004ExcelView", mapInspt);
	}

    @RequestMapping(value="/FM-COMPS004.do")
    public ModelAndView comps004_excel(@ModelAttribute("searchVO") SearchVO searchVO
    		,final HttpServletRequest req, HttpServletResponse res) throws Exception {

    	new GregorianCalendar();
    	req.getSession().getAttribute(CommonConfig.SES_USER_ID_KEY);

    	String fileName = "정보보호활동 설정.xls";

		res.setHeader("Content-Transfer-Encoding:", "binary");
		res.setContentType("application/vnd.ms-excel;charset=euc-kr");
		res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("ksc5601"), "8859_1")  + ";");
		res.setHeader("Pragma", "no-cache;");
		res.setHeader("Expires", "-1;");

		searchVO.setManCyl((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		searchVO.setCompany(propertyService.getString("company"));

        List<?> list = excelService.comps004_excel(searchVO);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);

    	return new ModelAndView("comps004ExcelView", map);
    }
    @RequestMapping(value="/FM-COMPS005.do")
    public ModelAndView comps005_excel(@ModelAttribute("searchVO") SearchVO searchVO
    		,final HttpServletRequest req, HttpServletResponse res) throws Exception {

    	new GregorianCalendar();
    	req.getSession().getAttribute(CommonConfig.SES_USER_ID_KEY);

    	String fileName = "통제항목설정.xls";

		res.setHeader("Content-Transfer-Encoding:", "binary");
		res.setContentType("application/vnd.ms-excel;charset=euc-kr");
		res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("ksc5601"), "8859_1")  + ";");
		res.setHeader("Pragma", "no-cache;");
		res.setHeader("Expires", "-1;");

		searchVO.setManCyl((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
        searchVO.setDivision(req.getParameter("service"));
        searchVO.setService(req.getParameter("service"));

        List<?> list = excelService.comps005_excel(searchVO);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);

    	return new ModelAndView("comps005ExcelView", map);
    }

    @RequestMapping(value="/FM-SETUP010.do")
    public ModelAndView setup010_excel(@ModelAttribute("searchVO") SearchVO searchVO
    		,final HttpServletRequest req, HttpServletResponse res) throws Exception {

    	new GregorianCalendar();
    	req.getSession().getAttribute(CommonConfig.SES_USER_ID_KEY);

    	String fileName = "로그인 이력.xls";

		res.setHeader("Content-Transfer-Encoding:", "binary");
		res.setContentType("application/vnd.ms-excel;charset=euc-kr");
		res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("ksc5601"), "8859_1")  + ";");
		res.setHeader("Pragma", "no-cache;");
		res.setHeader("Expires", "-1;");

		searchVO.setCompany(propertyService.getString("company"));
		searchVO.setCode("All_List");
    	List<?> list = excelService.setup010_excel(searchVO);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);

    	return new ModelAndView("setup010ExcelView", map);
    }
    @RequestMapping(value="/FM-SETUP011.do")
    public ModelAndView setup011_excel(@ModelAttribute("searchVO") SearchVO searchVO
    		,final HttpServletRequest req, HttpServletResponse res) throws Exception {

    	new GregorianCalendar();
    	req.getSession().getAttribute(CommonConfig.SES_USER_ID_KEY);

    	String fileName = "계정 변경 이력.xls";

		res.setHeader("Content-Transfer-Encoding:", "binary");
		res.setContentType("application/vnd.ms-excel;charset=euc-kr");
		res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("ksc5601"), "8859_1")  + ";");
		res.setHeader("Pragma", "no-cache;");
		res.setHeader("Expires", "-1;");

		searchVO.setCompany(propertyService.getString("company"));
		searchVO.setCode("All_List");
    	List<?> list = excelService.setup011_excel(searchVO);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);

    	return new ModelAndView("setup011ExcelView", map);
    }

    @RequestMapping(value="/FM-MWORK017_F.do")
    public ModelAndView mwork007_F_excel(final HttpServletRequest req, HttpServletResponse res) throws Exception {

    	new GregorianCalendar();
    	req.getSession().getAttribute(CommonConfig.SES_USER_ID_KEY);

    	String fileName = "개인정보 흐름도.xls";

		res.setHeader("Content-Transfer-Encoding:", "binary");
		res.setContentType("application/vnd.ms-excel;charset=euc-kr");
		res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("ksc5601"), "8859_1")  + ";");
		res.setHeader("Pragma", "no-cache;");
		res.setHeader("Expires", "-1;");

    	//searchVO.setManCyl((String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		String mtrKey = req.getParameter("mtrKey");
        // 흐름도
        List<?> flowMList = excelService.getFlowMList(mtrKey);
        List<?> flowDList = excelService.getFlowDList(mtrKey);
        Map<String, Object> map = new HashMap<String, Object>();
		map.put("flowMList", flowMList);
		map.put("flowDList", flowDList);

		return new ModelAndView("mwork017_F_ExcelView", map);
    }

    @RequestMapping(value="/FM-MWORK017_E.do")
    public ModelAndView mwork007_E_excel(final HttpServletRequest req, HttpServletResponse res) throws Exception {

    	new GregorianCalendar();
    	req.getSession().getAttribute(CommonConfig.SES_USER_ID_KEY);

    	String fileName = "개인정보 흐름.xls";

		res.setHeader("Content-Transfer-Encoding:", "binary");
		res.setContentType("application/vnd.ms-excel;charset=euc-kr");
		res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("ksc5601"), "8859_1")  + ";");
		res.setHeader("Pragma", "no-cache;");
		res.setHeader("Expires", "-1;");

    	//searchVO.setManCyl((String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		//String mtrKey = req.getParameter("mtrKey");
        // 흐름도
        List<?> excelList = excelService.getAllList();
        //List<?> flowDList = excelService.getFlowDList(mtrKey);
        Map<String, Object> map = new HashMap<String, Object>();
		map.put("excelList", excelList);

		return new ModelAndView("mwork017_E_ExcelView", map);
    }


    @RequestMapping(value="/FM-SETUP012.do")
    public ModelAndView setup012_excel(@ModelAttribute("searchVO") SearchVO searchVO
    		,final HttpServletRequest req, HttpServletResponse res) throws Exception {

    	String fileName = "공휴일 관리.xls";

		res.setHeader("Content-Transfer-Encoding:", "binary");
		res.setContentType("application/vnd.ms-excel;charset=euc-kr");
		res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("ksc5601"), "8859_1")  + ";");
		res.setHeader("Pragma", "no-cache;");
		res.setHeader("Expires", "-1;");



    	Map sMap = new HashMap();
    	String compareDate = req.getParameter("sYear");
    	sMap.put("compareDate", compareDate);

        List<?> ymdList = excelService.getYmdList(compareDate);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ymdList", ymdList);

    	return new ModelAndView("setup012ExcelView", map);
    }

    @RequestMapping(value="/FM-ASSET001.do")
    public ModelAndView asset001_excel(@ModelAttribute("searchVO") SearchVO searchVO
    		,final HttpServletRequest req, HttpServletResponse res) throws Exception {
    	String fileName = "자산관리.xls";

		res.setHeader("Content-Transfer-Encoding:", "binary");
		res.setContentType("application/vnd.ms-excel;charset=euc-kr");
		res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("ksc5601"), "8859_1")  + ";");
		res.setHeader("Pragma", "no-cache;");
		res.setHeader("Expires", "-1;");

		String company = propertyService.getString("company");
        searchVO.setCompany(company);

        // 2016-11-30, if() 추가
        if (searchVO.getManCyl() == null || searchVO.getManCyl() == "") {
	        searchVO.setManCyl((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
        }

        Map<String, Object> map = new HashMap<String, Object>();

        // 2016-06-28, 하드코딩 들어냄
        Map<String, String> paramMap = new HashMap<String, String>();

        paramMap.put("uacUseYn", "Y");
        paramMap.put("uacCatTyp", "A");

        // 2017-05-24, 자산분류 선택시 해당 자산분류만 다운로드 됨
        paramMap.put("uacCatKey", searchVO.getAssCat());

        List<?> infoList = fmAssetService.fm_asset008_list(paramMap);

        List<List<?>> assList = new ArrayList<List<?>>();

        for (int i = 0; i < infoList.size(); i++) {
        	EgovMap item = (EgovMap)infoList.get(i);
        	String num = (String)item.get("uacCatKey").toString();
        	searchVO.setAssCat(num);
        	assList.add(excelService.asset001_excel(searchVO));
        }

        map.put("infoList", infoList);
        map.put("assList", assList);

        // code
        // 분류코드 / 서비스코드 / 부서코드 / 자산유형코드 / OS코드
        //map.put("catList", commonCodeService.getCommonCodeList("CAT"));
        paramMap.put("upType", "A");
        map.put("catList", commonCodeService.getAssCatList(paramMap));
        map.put("svcList", commonCodeService.getSvcCodeList("SVCOD"));
        map.put("depList", commonCodeService.getDepCodeList());
        map.put("dbList", commonCodeService.getCommonCodeList("CAT_DB"));
        map.put("osList", commonCodeService.getCommonCodeList("CAT_SE"));

    	return new ModelAndView("asset001ExcelView", map);
    }

    /*
     * 2016-07-25
     * 자산관리대장 REPORT
     */
    @RequestMapping(value="/FM-ASSET001_REPORT.do")
    public ModelAndView asset001_report_excel(@ModelAttribute("searchVO") SearchVO searchVO
    		,final HttpServletRequest req, HttpServletResponse res) throws Exception {
    	String fileName = "자산관리대장.xls";

		res.setHeader("Content-Transfer-Encoding:", "binary");
		res.setContentType("application/vnd.ms-excel;charset=euc-kr");
		res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("ksc5601"), "8859_1")  + ";");
		res.setHeader("Pragma", "no-cache;");
		res.setHeader("Expires", "-1;");

		String company = propertyService.getString("company");
        searchVO.setCompany(company);

        // 2016-11-30, if() 추가
        if (searchVO.getManCyl() == null || searchVO.getManCyl() == "") {
        	searchVO.setManCyl((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
        }

        Map<String, Object> map = new HashMap<String, Object>();

        // 2016-06-28, 하드코딩 들어냄
        Map<String, String> paramMap = new HashMap<String, String>();

        paramMap.put("uacUseYn", "Y");
        paramMap.put("uacCatTyp", "A");

        // 2017-05-24, 자산분류 선택시 해당 자산분류만 다운로드 됨
        paramMap.put("uacCatKey", searchVO.getAssCat());

        List<?> infoList = fmAssetService.fm_asset008_list(paramMap);

        List<List<?>> assList = new ArrayList<List<?>>();

        for (int i = 0; i < infoList.size(); i++) {
        	EgovMap item = (EgovMap)infoList.get(i);
        	String num = (String)item.get("uacCatKey").toString();
        	searchVO.setAssCat(num);
        	assList.add(excelService.asset001_excel(searchVO));
        }

        map.put("infoList", infoList);
        map.put("assList", assList);

        // code
        // 분류코드 / 서비스코드 / 부서코드 / 자산유형코드 / OS코드
        //map.put("catList", commonCodeService.getCommonCodeList("CAT"));
        paramMap.put("upType", "A");
        map.put("catList", commonCodeService.getAssCatList(paramMap));
        map.put("svcList", commonCodeService.getSvcCodeList("SVCOD"));
        map.put("depList", commonCodeService.getDepCodeList());
        map.put("dbList", commonCodeService.getCommonCodeList("CAT_DB"));
        map.put("osList", commonCodeService.getCommonCodeList("CAT_SE"));

    	return new ModelAndView("asset001ReportExcelView", map);
    }


    @RequestMapping(value="/FM-ASSET001_sample.do")
    public ModelAndView asset001_sample_excel(final HttpServletRequest req, HttpServletResponse res) throws Exception {

    	String fileName = "자산업로드_양식.xls";

		res.setHeader("Content-Transfer-Encoding:", "binary");
		res.setContentType("application/vnd.ms-excel;charset=euc-kr");
		res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("ksc5601"), "8859_1")  + ";");
		res.setHeader("Pragma", "no-cache;");
		res.setHeader("Expires", "-1;");


    	Map<String, Object> map = new HashMap<String, Object>();
        Map<String, String> paramMap = new HashMap<String, String>();

        paramMap.put("uacUseYn", "Y");
        paramMap.put("uacCatTyp", "A");
        List<?> infoList = fmAssetService.fm_asset008_list(paramMap);

        List<List<?>> assList = new ArrayList<List<?>>();

        map.put("infoList", infoList);
        map.put("assList", assList);

        // code
        // 분류코드 / 서비스코드 / 부서코드 / 자산유형코드 / OS코드
        paramMap.put("upType", "A");
        map.put("catList", commonCodeService.getAssCatList(paramMap));
        map.put("svcList", commonCodeService.getSvcCodeList("SVCOD"));
        map.put("depList", commonCodeService.getDepCodeList());
        map.put("dbList", commonCodeService.getCommonCodeList("CAT_DB"));
        map.put("osList", commonCodeService.getCommonCodeList("CAT_SE"));

        // 2016-08-17, 자산 다운로드와 동일하게 변경
        return new ModelAndView("asset001ExcelView", map);

    }

    @RequestMapping(value="/FM-RISKM005.do")
    public ModelAndView riskm005_excel(@ModelAttribute("searchVO") SearchVO searchVO
    		,final HttpServletRequest req, HttpServletResponse res) throws Exception {

    	String fileName = "통제항목설정.xls";

		res.setHeader("Content-Transfer-Encoding:", "binary");
		res.setContentType("application/vnd.ms-excel;charset=euc-kr");
		res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("ksc5601"), "8859_1")  + ";");
		res.setHeader("Pragma", "no-cache;");
		res.setHeader("Expires", "-1;");

		//searchVO.setManCyl((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
        searchVO.setDivision(req.getParameter("hDiv"));

        List<?> list = excelService.riskm005_excel(searchVO);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);

    	return new ModelAndView("riskm005ExcelView", map);
    }

    @RequestMapping(value="/FM-ASSET006.do")
    public ModelAndView asset006_excel(@ModelAttribute("searchVO") SearchVO searchVO
    		,final HttpServletRequest req, HttpServletResponse res) throws Exception {

    	String fileName = "자산이력.xls";

		res.setHeader("Content-Transfer-Encoding:", "binary");
		res.setContentType("application/vnd.ms-excel;charset=euc-kr");
		res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("ksc5601"), "8859_1")  + ";");
		res.setHeader("Pragma", "no-cache;");
		res.setHeader("Expires", "-1;");

		searchVO.setManCyl((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

        List<?> list = excelService.asset006_excel(searchVO);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);

    	return new ModelAndView("asset006ExcelView", map);
    }
    @RequestMapping(value="/FM-INSPT001.do")
    public ModelAndView inspt001_excel(@ModelAttribute("searchVO") SearchVO searchVO
    		,final HttpServletRequest req, HttpServletResponse res) throws Exception {

    	String fileName = "심사문서관리.xls";

		res.setHeader("Content-Transfer-Encoding:", "binary");
		res.setContentType("application/vnd.ms-excel;charset=euc-kr");
		res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("ksc5601"), "8859_1")  + ";");
		res.setHeader("Pragma", "no-cache;");
		res.setHeader("Expires", "-1;");

    	searchVO.setManCyl((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
        searchVO.setDivision((String)req.getSession().getAttribute(CommonConfig.SES_DIVISION_KEY));
        searchVO.setCompany(propertyService.getString("company"));
    	List<?> list = excelService.inspt001_excel(searchVO);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);

    	return new ModelAndView("inspt001ExcelView", map);
    }

    @RequestMapping(value="/FM-RISKM003.do")
    public ModelAndView riskm003_excel(@ModelAttribute("searchVO") SearchVO searchVO
    		,final HttpServletRequest req, HttpServletResponse res) throws Exception {

    	String fileName = "위험조치관리_자산일반.xls";

		res.setHeader("Content-Transfer-Encoding:", "binary");
		res.setContentType("application/vnd.ms-excel;charset=euc-kr");
		res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("ksc5601"), "8859_1")  + ";");
		res.setHeader("Pragma", "no-cache;");
		res.setHeader("Expires", "-1;");


		searchVO.setManCyl((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
    	return new ModelAndView("riskm003ExcelView",  excelService.riskm003_excel(searchVO));
    }

    @RequestMapping(value="/FM-RISKM003_ETC.do")
    public ModelAndView riskm003_etc_excel(@RequestParam Map<String, String> map,
    		final HttpServletRequest req, HttpServletResponse res) throws Exception {

    	String fileName = "위험조치관리_관리체제.xls";

		res.setHeader("Content-Transfer-Encoding:", "binary");
		res.setContentType("application/vnd.ms-excel;charset=euc-kr");
		res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("ksc5601"), "8859_1")  + ";");
		res.setHeader("Pragma", "no-cache;");
		res.setHeader("Expires", "-1;");


		map.put("bcyCod",(String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		map.put("grpKey","0");
    	return new ModelAndView("riskm003EtcExcelView",  excelService.riskm003_etc_excel(map));
    }

    @RequestMapping(value="/FM-RISKM006.do")
    public ModelAndView riskm006_excel(@ModelAttribute("searchVO") SearchVO searchVO
    		,final HttpServletRequest req, HttpServletResponse res) throws Exception {

    	Map<String, String> map = new HashMap<String,String>();

    	String fileName = "기술적_취약점결과_양식.xls";

		res.setHeader("Content-Transfer-Encoding:", "binary");
		res.setContentType("application/vnd.ms-excel;charset=euc-kr");
		res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("ksc5601"), "8859_1")  + ";");
		res.setHeader("Pragma", "no-cache;");
		res.setHeader("Expires", "-1;");

		map.put("assCat", req.getParameter("assCatS"));
		map.put("assGbn", req.getParameter("assGbnS"));
		map.put("upType", req.getParameter("upType"));
		map.put("manCyl", (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
    	return new ModelAndView("riskm006ExcelView",  excelService.riskm006_excel(map));
    }

    @RequestMapping(value="/FM-RISKM006_ETC.do")
    public ModelAndView riskm006_etc_excel(@RequestParam Map<String, String> map,
    		final HttpServletRequest req, HttpServletResponse res) throws Exception {

    	String fileName = "위험조치관리_관리체제.xls";

		res.setHeader("Content-Transfer-Encoding:", "binary");
		res.setContentType("application/vnd.ms-excel;charset=euc-kr");
		res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("ksc5601"), "8859_1")  + ";");
		res.setHeader("Pragma", "no-cache;");
		res.setHeader("Expires", "-1;");


		map.put("bcyCod",(String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		map.put("grpKey","0");
    	return new ModelAndView("riskm003EtcExcelView",  excelService.riskm003_etc_excel(map));
    }

    @RequestMapping(value="/FM-RISKM006_LIST_DOWN.do")
    public ModelAndView riskm006_excel_list_down(@RequestParam Map<String, String> map
    		,final HttpServletRequest req, HttpServletResponse res) throws Exception {

    	String fileName = new StringBuffer().append("취약점결과_").append(map.get("assCatNam")).append(".xls").toString();

		res.setHeader("Content-Transfer-Encoding:", "binary");
		res.setContentType("application/vnd.ms-excel;charset=euc-kr");
		res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("ksc5601"), "8859_1")  + ";");
		res.setHeader("Pragma", "no-cache;");
		res.setHeader("Expires", "-1;");
		map.put("manCyl", (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
    	return new ModelAndView("riskm006RstExcelView",  excelService.riskm006_excel_list_down(map));
    }

    @RequestMapping(value="/FM-RISKM003_REPORT_DOWN.do")
    public ModelAndView riskm003_report_down(@ModelAttribute("searchVO") SearchVO searchVO
    		,final HttpServletRequest req, HttpServletResponse res) throws Exception {

    	Map<String, String> map = new HashMap<String,String>();
    	Map<String, Object> resultMap = new HashMap<String,Object>();

    	map.put("svr", req.getParameter("svrS"));
    	String svcNam = excelService.riskm003_svr_name(map);
    	String fileName = "SKB_" + svcNam + "_위험조치계획서.xls";

		res.setHeader("Content-Transfer-Encoding:", "binary");
		res.setContentType("application/vnd.ms-excel;charset=euc-kr");
		res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("ksc5601"), "8859_1")  + ";");
		res.setHeader("Pragma", "no-cache;");
		res.setHeader("Expires", "-1;");

		map.put("manCyl", (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

		resultMap.put("paramMap",  map);
    	return new ModelAndView("riskm003ReportView",  resultMap);
//    	return new ModelAndView("riskm003ReportView",  excelService.riskm003_report_down(map));
    }

    @RequestMapping(value="/FM-RISKM009.do")
    public ModelAndView riskm009_excel(@ModelAttribute("searchVO") SearchVO searchVO
    		,final HttpServletRequest req, HttpServletResponse res) throws Exception {

    	Map<String, String> map = new HashMap<String,String>();

    	String fileName = "부서별 취약점결과 양식.xls";

		res.setHeader("Content-Transfer-Encoding:", "binary");
		res.setContentType("application/vnd.ms-excel;charset=euc-kr");
		res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("ksc5601"), "8859_1")  + ";");
		res.setHeader("Pragma", "no-cache;");
		res.setHeader("Expires", "-1;");

		map.put("assCat", req.getParameter("assCatS"));
		map.put("assGbn", req.getParameter("assGbnS"));
		map.put("upType", req.getParameter("upType"));
		map.put("manCyl", (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
    	return new ModelAndView("riskm009ExcelView",  excelService.riskm009_excel(map));
    }

    @RequestMapping(value="/FM-RISKM009_LIST_DOWN.do")
    public ModelAndView riskm009_excel_list_down(@ModelAttribute("searchVO") SearchVO searchVO
    		,final HttpServletRequest req, HttpServletResponse res) throws Exception {

    	Map<String, String> map = new HashMap<String,String>();

    	String fileName = "취약점결과(전체).xls";

		res.setHeader("Content-Transfer-Encoding:", "binary");
		res.setContentType("application/vnd.ms-excel;charset=euc-kr");
		res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("ksc5601"), "8859_1")  + ";");
		res.setHeader("Pragma", "no-cache;");
		res.setHeader("Expires", "-1;");
		map.put("downType", req.getParameter("downType"));
		map.put("manCyl", (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		map.put("service", req.getParameter("service"));
		log.info("map : "  + map);
    	return new ModelAndView("riskm009RstExcelView",  excelService.riskm009_excel_list_down(map));
    }

    @RequestMapping(value="/FM-RISKM010_REPORT_DOWN.do")
    public ModelAndView riskm010_report_down(@ModelAttribute("searchVO") SearchVO searchVO
    		,final HttpServletRequest req, HttpServletResponse res) throws Exception {

    	Map<String, String> map = new HashMap<String,String>();
    	Map<String, Object> resultMap = new HashMap<String,Object>();

    	map.put("svr", req.getParameter("svrS"));
    	String svcNam = excelService.riskm003_svr_name(map);
    	String fileName = "SKB_" + svcNam + "_부서별_위험조치계획서.xls";

		res.setHeader("Content-Transfer-Encoding:", "binary");
		res.setContentType("application/vnd.ms-excel;charset=euc-kr");
		res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("ksc5601"), "8859_1")  + ";");
		res.setHeader("Pragma", "no-cache;");
		res.setHeader("Expires", "-1;");

		map.put("manCyl", (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

		resultMap.put("paramMap",  map);
    	return new ModelAndView("riskm010ReportView",  resultMap);
//    	return new ModelAndView("riskm003ReportView",  excelService.riskm003_report_down(map));
    }


    /**
	 * 2018-05-10 s, 계정 정보
	 * @param searchVO
	 * @return
	 */
    @RequestMapping(value="/FM-SETUP007.do")
    public ModelAndView setup007_excel(@ModelAttribute("searchVO") SearchVO searchVO
    		,final HttpServletRequest req, HttpServletResponse res) throws Exception {

    	new GregorianCalendar();
    	req.getSession().getAttribute(CommonConfig.SES_USER_ID_KEY);

    	String fileName = "사용자계정.xls";

		res.setHeader("Content-Transfer-Encoding:", "binary");
		res.setContentType("application/vnd.ms-excel;charset=euc-kr");
		res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("ksc5601"), "8859_1")  + ";");
		res.setHeader("Pragma", "no-cache;");
		res.setHeader("Expires", "-1;");

		searchVO.setManCyl((String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

        List<?> list = excelService.setup007_excel(searchVO);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);

    	return new ModelAndView("setup007ExcelView", map);
    }

}
