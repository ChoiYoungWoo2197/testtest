/**
 ***********************************
 * @source ExcelNewController.java
 * @date 2019. 06. 25.
 * @project isams skb
 * @description excel new type controller
 ***********************************
 */

package com.uwo.isms.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uwo.isms.common.CommonConfig;
import com.uwo.isms.dao.ExcelNewDAO;
import com.uwo.isms.domain.SearchVO;
import com.uwo.isms.service.FMMworkService;
import egovframework.rte.fdl.property.EgovPropertyService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.uwo.isms.service.ExcelNewService;
import org.springframework.web.servlet.ModelAndView;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/excelNew")
public class ExcelNewController {

    Logger log = LogManager.getLogger(FMSetupController.class);

    @Resource(name = "excelNewService")
    ExcelNewService excelNewService;

    @Resource(name="excelNewDAO")
    private ExcelNewDAO excelNewDAO;

    @Autowired
    FMMworkService fmMworkService;

    @Resource(name = "propertiesService")
    protected EgovPropertyService propertyService;

    @RequestMapping(value="/FM-COMPS003.do")
    public ModelAndView downComps003(@ModelAttribute("searchVO") SearchVO searchVO,
                                 ModelMap model, final HttpServletRequest req, HttpServletResponse res) throws Exception {

        new GregorianCalendar();
        req.getSession().getAttribute(CommonConfig.SES_USER_ID_KEY);

        String stndName = String.valueOf(excelNewDAO.getStndName(req.getParameter("standard")));
        String fileName = stndName +" 설정.xls";

        res.setHeader("Content-Transfer-Encoding:", "binary");
        res.setContentType("application/vnd.ms-excel;charset=utf-8");
        res.setHeader("Content-Disposition", "attachment;filename=\"" + new String(fileName.getBytes("ksc5601"), "8859_1") + "\";");
        res.setHeader("Pragma", "no-cache;");
        res.setHeader("Expires", "-1;");

        Map<String, String> mapComps = new HashMap<String, String>();
        mapComps.put("ucmBcyCod", (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
        mapComps.put("ucmCtrGbn", req.getParameter("standard"));
        mapComps.put("service", req.getParameter("service"));

        List<?> listComps = excelNewService.downComps003(mapComps);
        Map<String, Object> mapResult = new HashMap<String, Object>();
        mapResult.put("listComps", listComps);
        mapResult.put("standard", req.getParameter("standard"));
        String stndKind = String.valueOf(excelNewService.getStndKind(req.getParameter("standard")));
        switch (stndKind){
            case "isms" :
                return new ModelAndView("comps003ExcelNewView_ISMS", mapResult);
            case "isms_p" :
                return new ModelAndView("comps003ExcelNewView_ISMS_P", mapResult);
            case "infra_mp" :
                return new ModelAndView("comps003ExcelNewView_INFRA_MP", mapResult);
            case "msit" :
                return new ModelAndView("comps003ExcelNewView_MSIT", mapResult);
            case "infra_la" :
                return new ModelAndView("comps003ExcelNewView_INFRA_LA", mapResult);
            default :
                return new ModelAndView("comps003ExcelNewView_DEFAULT", mapResult);
        }
    }

    @RequestMapping(value="excelReportMsit.do")
    public ModelAndView excelReportMsit(final HttpServletRequest req, HttpServletResponse res) throws Exception {
        String fileName = "테스트 과기정통부.xlsx";

        res.setHeader("Content-Transfer-Encoding:", "binary");
        res.setContentType("application/vnd.ms-excel;charset=utf-8");
        res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("ksc5601"), "8859_1") + ";");
        res.setHeader("Pragma", "no-cache;");
        res.setHeader("Expires", "-1;");
        res.setHeader("Set-Cookie", "excelDown=true; path=/");

        Map<String, String> mapComps = new HashMap<String, String>();
        mapComps.put("ucmBcyCod", req.getParameter("bcy_cod"));
        mapComps.put("ucmCtrGbn", req.getParameter("ctr_gbn"));
        mapComps.put("service", req.getParameter("service"));

        List<Object> listComps = (List<Object>) excelNewService.downComps003(mapComps);//보고서용 쿼리를 따로 만들어야 한다.
        Map<String, Object> mapResult = new HashMap<String, Object>();
        mapResult.put("listComps", listComps);

        return new ModelAndView("excelReportMsit", mapResult);
    }

    @RequestMapping(value="excelReportInfraMP.do")
    public ModelAndView excelReportInfraMP(final HttpServletRequest req, HttpServletResponse res) throws Exception {
        String fileName = "테스트 기반시설 관리물리.xlsx";
        res.setHeader("Content-Transfer-Encoding:", "binary");
        res.setContentType("application/vnd.ms-excel;charset=utf-8");
        res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("ksc5601"), "8859_1") + ";");
        res.setHeader("Pragma", "no-cache;");
        res.setHeader("Expires", "-1;");
        res.setHeader("Set-Cookie", "excelDown=true; path=/");

        Map<String, String> mapComps = new HashMap<String, String>();
        mapComps.put("ucmBcyCod", req.getParameter("bcy_cod"));
        mapComps.put("ucmCtrGbn", req.getParameter("ctr_gbn"));
        mapComps.put("service", req.getParameter("service"));

        List<Object> listComps = (List<Object>) excelNewService.downComps003(mapComps);//보고서용 쿼리를 따로 만들어야 한다.
        Map<String, Object> mapResult = new HashMap<String, Object>();
        mapResult.put("listComps", listComps);

        return new ModelAndView("excelReportInfraMP", mapResult);
    }

    @RequestMapping(value="excelReportInfraLA.do")
    public ModelAndView excelReportInfraLA(final HttpServletRequest req, HttpServletResponse res) throws Exception {
        String fileName = "테스트 기반시설 수준평가.xlsx";
        res.setHeader("Content-Transfer-Encoding:", "binary");
        res.setContentType("application/vnd.ms-excel;charset=utf-8");
        res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("ksc5601"), "8859_1") + ";");
        res.setHeader("Pragma", "no-cache;");
        res.setHeader("Expires", "-1;");
        res.setHeader("Set-Cookie", "excelDown=true; path=/");


        Map<String, String> mapComps = new HashMap<String, String>();
        mapComps.put("ucmBcyCod", req.getParameter("bcy_cod"));
        mapComps.put("ucmCtrGbn", req.getParameter("ctr_gbn"));
        mapComps.put("service", req.getParameter("service"));

        List<Object> listComps = (List<Object>) excelNewService.downComps003(mapComps);//보고서용 쿼리를 따로 만들어야 한다.
        Map<String, Object> mapResult = new HashMap<String, Object>();
        mapResult.put("listComps", listComps);


        return new ModelAndView("excelReportInfraLA", mapResult);
    }

    @RequestMapping(value="excelReportSAC.do")
    public ModelAndView excelReportSAC(final HttpServletRequest req, HttpServletResponse res) throws Exception {
        String fileName = "테스트 SA 체크리스트.xlsx";
        res.setHeader("Content-Transfer-Encoding:", "binary");
        res.setContentType("application/vnd.ms-excel;charset=utf-8");
        res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("ksc5601"), "8859_1") + ";");
        res.setHeader("Pragma", "no-cache;");
        res.setHeader("Expires", "-1;");
        res.setHeader("Set-Cookie", "excelDown=true; path=/");

        Map<String, String> mapComps = new HashMap<String, String>();
        mapComps.put("ucmBcyCod", (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
        mapComps.put("ucmCtrGbn", "ABP");//강제로 설정
        mapComps.put("service", "");

        List<Object> listComps = (List<Object>) excelNewService.downComps003(mapComps);//보고서용 쿼리를 따로 만들어야 한다.
        Map<String, Object> mapResult = new HashMap<String, Object>();
        mapResult.put("listComps", listComps);

        return new ModelAndView("excelReportSAC", mapResult);
    }

    @RequestMapping(value="excelReportSAS.do")
    public ModelAndView excelReportSAS(final HttpServletRequest req, HttpServletResponse res) throws Exception {
        String fileName = "테스트 SA 통계용.xlsx";
        res.setHeader("Content-Transfer-Encoding:", "binary");
        res.setContentType("application/vnd.ms-excel;charset=utf-8");
        res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("ksc5601"), "8859_1") + ";");
        res.setHeader("Pragma", "no-cache;");
        res.setHeader("Expires", "-1;");
        res.setHeader("Set-Cookie", "excelDown=true; path=/");

        Map<String, String> mapComps = new HashMap<String, String>();
        mapComps.put("ucmBcyCod", (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
        mapComps.put("ucmCtrGbn", "ABP");//강제로 설정
        mapComps.put("service", "");

        List<Object> listComps = (List<Object>) excelNewService.downComps003(mapComps);//보고서용 쿼리를 따로 만들어야 한다.
        Map<String, Object> mapResult = new HashMap<String, Object>();
        mapResult.put("listComps", listComps);

        return new ModelAndView("excelReportSAS", mapResult);
    }

    @RequestMapping(value="/FM-MWORK006.do")
    public ModelAndView excelWorkState(@ModelAttribute("searchVO") SearchVO searchVO
            ,final HttpServletRequest req, HttpServletResponse res) throws Exception {
        String fileName = "정보보호업무현황";
        if(searchVO.getFindusr()!=null&&searchVO.getFindusr().equals("Y")){
            fileName += "(개인)";
        }else{
            fileName += "(부서)";
        }
        fileName += ".xlsx";

        res.setHeader("Content-Transfer-Encoding:", "binary");
        res.setContentType("application/vnd.ms-excel;charset=utf-8");
        res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("ksc5601"), "8859_1") + ";");
        res.setHeader("Pragma", "no-cache;");
        res.setHeader("Expires", "-1;");
        //res.setHeader("Set-Cookie", "excelDown=true; path=/");

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

        return new ModelAndView("excelWorkState", map);
    }

    @RequestMapping(value="/FM-MWORK006_excelSave.do")
    public ModelAndView fmMwork006_excelNew_Save(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {
        ModelAndView mav = new ModelAndView();
        Map<String, Object> resultMap = new HashMap<String, Object>();

        Map<String, Object> mapReqInfo = new HashMap<>();
        mapReqInfo.put("UTW_PRD_COD", req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
        mapReqInfo.put("UTW_WRK_ID", req.getSession().getAttribute(CommonConfig.SES_USER_KEY));

        String strResult = excelNewService.fmMwork006_excelNew_Save(mapReqInfo, req);
        resultMap.put("result", strResult);

        res.setCharacterEncoding("UTF-8");
        res.setContentType("text/xml; charset=UTF-8");
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
    }
}