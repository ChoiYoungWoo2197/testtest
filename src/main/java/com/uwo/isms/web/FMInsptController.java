/**
 ***********************************
 * @source FMInsptController.java
 * @date 2014. 10. 9.
 * @project isms3
 * @description 정보보호관리업무 controller
 ***********************************
 */
package com.uwo.isms.web;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.sql.Clob;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;
import javax.mail.internet.ContentType;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import com.google.common.collect.*;
import com.google.common.net.MediaType;
import com.sun.corba.se.spi.orbutil.threadpool.Work;
import com.uwo.isms.dao.ExcelNewDAO;
import com.uwo.isms.domain.*;
import com.uwo.isms.service.*;
import com.uwo.isms.util.CommonUtil;
import com.uwo.isms.util.EgovUserDetailsHelper;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.UnzipParameters;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
//import org.springmodules.validation.commons.DefaultBeanValidator;

import com.uwo.isms.common.CommonConfig;
import com.uwo.isms.common.Constants;
//import com.uwo.isms.service.CommonCodeService;
//import com.uwo.isms.service.FileUploadService;
//import com.uwo.isms.service.SendMailService;
import com.uwo.isms.util.FileUpload;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;


@Controller
@RequestMapping("/inspt")
public class FMInsptController extends SupportController {

	Logger log = LogManager.getLogger(FMInsptController.class);

	@Resource(name = "fmInsptService")
	private FMInsptService fmInsptService;

	@Resource(name = "fileUploadService")
	FileUploadService fileUploadService;

	@Resource(name = "fmBoardService")
	private FMBoardService fmBoardService;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;

	/*@Resource(name = "sendMailService")
	SendMailService sendMailService;*/

	@Resource(name = "commonUtilService")
	private CommonUtilService commonUtilService;

	/*@Resource(name = "commonCodeService")
	private CommonCodeService commonCodeService;*/

	@Resource(name = "excelNewService")
	ExcelNewService excelNewService;

	@Resource(name = "excelNewDAO")
	private ExcelNewDAO excelNewDAO;

	@Autowired
	private CommonService commonService;

	@Autowired
	private InspectionManagerService inspectionManagerService;

	@Autowired
	private FMCompsService fmCompsService;

	@Autowired
	private CommonUtil commonUtil;

	/*@Autowired
	private DefaultBeanValidator beanValidator;*/

	@RequestMapping(value = "/FM-INSPT008.do")
	public String fmInspt001Test(@ModelAttribute("searchVO") SearchVO searchVO,
							 HttpServletRequest request, ModelMap model) throws Exception {

		if ( searchVO.getStandard() != null && searchVO.getService() != null ) {
			searchVO.setManCyl((String) request.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

			List<EgovMap> controlItemList = (List<EgovMap>) fmInsptService.fm_inspt008_compliance_list(searchVO);
			List<EgovMap> workItemList = (List<EgovMap>) fmInsptService.fm_inspt008_work_list(searchVO);
			List<EgovMap> fileList = (List<EgovMap>) fmInsptService.fm_inspt008_file_list(searchVO);

			// 통제항목
			Table<String, String, EgovMap> controlItems = HashBasedTable.create();
			for (EgovMap egovMap : controlItemList) {
				controlItems.put((String) egovMap.get("ucm2lvCod"), (String) egovMap.get("ucm3lvCod"), egovMap);
			}

			// 활동
			Map<String, Table> activities = new HashMap<String, Table>();
			for (EgovMap egovMap : workItemList) {
				String lv3Code = (String) egovMap.get("ucm3lvCod");
				String trcKey = ((BigDecimal) egovMap.get("utwTrcKey")).toString();
				String wrkKey = ((BigDecimal) egovMap.get("utwWrkKey")).toString();

				if ( ! activities.containsKey(lv3Code)) {
					Table<String, String, EgovMap> workItemTable = HashBasedTable.create();
					activities.put(lv3Code, workItemTable);
				}

				Table<String, String, EgovMap> workItemTable = activities.get(lv3Code);

				workItemTable.put(trcKey, wrkKey, egovMap);
			}

			// 증적파일
			ArrayListMultimap<BigDecimal, EgovMap> files = ArrayListMultimap.create();
			for (EgovMap egovMap : fileList) {
				files.put(new BigDecimal((String) egovMap.get("umfConKey")), egovMap);
			}

			model.addAttribute("saServiceCode", fmCompsService.getSaServiceCode());
			model.addAttribute("search", searchVO);
			model.addAttribute("controlItems", controlItems);
			model.addAttribute("activities", activities);
			model.addAttribute("files", files.asMap());

		}

		return "FM-INSPT008";
	}

	@RequestMapping(value = "/FM-INSPT001.do")
	public String fmInspt001(@ModelAttribute("searchVO") SearchVO searchVO,
							 HttpServletRequest req, ModelMap model) throws Exception {

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("sesAuthKey", (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));
		paramMap.put("sesServiceKey", (String) req.getSession().getAttribute(CommonConfig.SES_SERVICE_KEY));
		paramMap.put("sesDeptKey", (String) req.getSession().getAttribute(CommonConfig.SES_DEPT_KEY));

		model.addAttribute("searchVO", searchVO);

		return "FM-INSPT001";
	}

	@RequestMapping("/FM-INSPT001_popup.do")
	public String fmInspt001_popup(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model,
								   HttpServletRequest req, HttpServletResponse res) throws Exception {

		String wKey = req.getParameter("wKey");

		String viewType = fmInsptService.inspt001_view(wKey);

		model.addAttribute("viewType", viewType);
		model.addAttribute("wKey", wKey);

		return "FM-INSPT001_popup";
	}

	@RequestMapping(value = "/FM-INSPT001_VIEW_UPDATE.do")
	public ModelAndView fmInspt001_viewUpdate(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model,
											  HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		searchVO.setManCyl((String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		searchVO.setWorkCode(req.getParameter("wKey"));
		searchVO.setCode(req.getParameter("utwVewLvl"));

		fmInsptService.fmInspt001_viewUpdate(searchVO);
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping(value = "/FM-INSPT001_getCntrWork.do")
	public ModelAndView fmInspt001_getCntrWork(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model,
											   HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		Map<String, String> map = new HashMap<String, String>();
		map.put("manCyl", (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		map.put("standard", req.getParameter("standard"));
		map.put("service", req.getParameter("service"));
		map.put("ucm1lvCod", req.getParameter("ucm1lvCod"));

//		List<?> resultList = fmInsptService.fm_inspt001_list(map);
		List<?> resultList = fmInsptService.fm_inspt001_list3(map);

		searchVO.setManCyl(req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY).toString());

//        searchVO.setCompany(propertyService.getString("company"));
//		List<?> resultList = fmInsptService.fm_inspt001_list(searchVO);

		// manCyl(대주기) 넘겨서 해당 주기의 파일만 리스트에 받아옴. (파일이 많아질 경우 루프 경과 시간이 가중됨.)
//		List<?> fileList = fmInsptService.fm_inspt001_getFileList(map);

		List<?> fileList = fmInsptService.fm_inspt001_getFileList(map);
		List<EgovMap> saFileList = (List<EgovMap>) fmInsptService.fm_inspt001_getSaFileList(map);
//		List<?> fileList = fmInsptService.fm_inspt001_getFileList_by_department_of_service(map);

		/*SA 증적 병합*/
//		List<EgovMap> saFileList = fmInsptService.getSaWorkFileRelatedOtherService(map);

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		log.info("---------------------");
		log.info("" + fileList);
		log.info("---------------------");

		Map resultMap = new HashMap();
		resultMap.put("fileList", fileList);
		resultMap.put("saFileList",
				map.get("service").equalsIgnoreCase(fmCompsService.getSaServiceCode()) ? null : saFileList);
		resultMap.put("resultList", resultList);
		resultMap.put("search", map);
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping("/FM-INSPT001_SA_FILE_SAVE.do")
	public ModelAndView fmInspt001_saFileSave(@RequestParam String service, @RequestParam int ctrKey,
											  @RequestParam int fleKey, @RequestParam String checked) {
		ModelAndView modelAndView = makeJsonResponse();

		Map<String, Object> map = new HashMap<>();
		map.put("service", service);
		map.put("ctrKey", ctrKey);
		map.put("fleKey", fleKey);

		// 저장
		if (checked.equalsIgnoreCase("Y")) {
			fmInsptService.fm_inspt001_insertMsrSaFile(map);
		}
		// 삭제
		else if (checked.equalsIgnoreCase("N")) {
			fmInsptService.fm_inspt001_deleteMsrSaFile(map);
		}

		return modelAndView;
	}

	@RequestMapping("/FM-INSPT001_FILE_SAVE.do")
	public ModelAndView fmInspt001_fileSave(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		// 2016-11-01, 건별로 처리, msrYn: N 처리
		Map<String, String> map = new HashMap<String, String>();
		map.put("fleKey", (String) req.getParameter("fleKey"));
		map.put("msrYn", (String) req.getParameter("msrYn"));

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", fmInsptService.fm_inspt001_setMsrFile(map));

		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;

	}

	@RequestMapping("/FM-INSPT001_getStdList.do")
	public void fmComps005_getStdList(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		String division = req.getParameter("service");
		SearchVO searchVO = new SearchVO();
		searchVO.setDivision(division);
		searchVO.setManCyl(req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY).toString());
		searchVO.setService(req.getParameter("service"));

		List<?> list = fmInsptService.fm_inspt001_getStdList(searchVO);
		List dataList = new ArrayList();

		for (int i = 0; i < list.size(); i++) {
			Map dataMap = new HashMap();

			dataMap.put("ucmCtrGbn", ((Map) list.get(i)).get("ucmCtrGbn"));
			dataMap.put("key", ((Map) list.get(i)).get("ucmCtrGbn"));
			dataMap.put("title", ((Map) list.get(i)).get("ucmCtrNam"));
			dataMap.put("isLazy", new Boolean(true));
			dataMap.put("hideCheckbox", new Boolean(true));
			dataMap.put("isFolder", new Boolean(true));
			dataMap.put("depth", "STD");
			dataList.add(dataMap);
		}

		JSON json = JSONSerializer.toJSON(dataList, new JsonConfig());
		res.setContentType("application/x-json;charset=euc-kr");
		res.getWriter().print(json.toString());
	}

	@RequestMapping("/FM-INSPT001_getCntrList.do")
	public void fmInspt001_getCntrList(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		String depth = req.getParameter("depth");
		String nextDepth = "";

		SearchVO searchVO = new SearchVO();
		searchVO.setSearchKeyword(req.getParameter("key"));
		searchVO.setDivision(req.getParameter("service"));
		searchVO.setManCyl((String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		searchVO.setService(req.getParameter("service"));
		searchVO.setStandard(req.getParameter("gbn"));

		List list = new ArrayList();
		List dataList = new ArrayList();

		String lvCode = "";
		String lvName = "";
		String lvDetail = "";
		if (depth.equals("STD")) {
			list = fmInsptService.fm_inspt001_getCntr1List(searchVO);

			lvCode = "ucm1lvCod";
			lvName = "ucm1lvNam";
			nextDepth = "CNTR1";
		} else if (depth.equals("CNTR1")) {
			list = fmInsptService.fm_inspt001_getCntr2List(searchVO);

			lvCode = "ucm2lvCod";
			lvName = "ucm2lvNam";
			lvDetail = "ucm2lvDtl";
			nextDepth = "CNTR2";
		}
/*        else if(depth.equals("CNTR2")){
        	list = fmInsptService.fm_inspt001_getCntr3List(searchVO);

        	lvCode = "ucm3lvCod";
        	lvName = "ucm3lvNam";
        	lvDetail = "ucm3lvDtl";
        	nextDepth = "CNTR3";
        }
        else if(depth.equals("CNTR3")){
        	list = fmInsptService.fm_inspt001_getCntr4List(searchVO);

        	lvCode = "ucm4lvCod";
        	lvName = "ucm4lvNam";
        	lvDetail = "ucm4lvDtl";
        	nextDepth = "CNTR4";
        }
        else if(depth.equals("CNTR4")){
        	list = fmInsptService.fm_inspt001_getCntr5List(searchVO);

        	lvCode = "ucm5lvCod";
        	lvName = "ucm5lvNam";
        	lvDetail = "ucm5lvDtl";
        	nextDepth = "CNTR5";
        }*/

		for (int i = 0; i < list.size(); i++) {
			Map dataMap = new HashMap();
			String delYN = "Y";

			dataMap.put("ucmCtrGbn", ((Map) list.get(i)).get("ucmCtrGbn"));
			dataMap.put("ucmCtrCod", ((Map) list.get(i)).get("ucmCtrCod"));
			dataMap.put("key", ((Map) list.get(i)).get(lvCode));
			dataMap.put("name", ((Map) list.get(i)).get(lvName));

			if (nextDepth.equals("CNTR3")) {
				dataMap.put("isLazy", new Boolean(false));
				dataMap.put("title", ((Map) list.get(i)).get(lvCode));
			} else {
				dataMap.put("isFolder", new Boolean(true));
				dataMap.put("isLazy", new Boolean(true));
				dataMap.put("title", ((Map) list.get(i)).get(lvCode) + " " + ((Map) list.get(i)).get(lvName));
			}

			dataMap.put("del_yn", ((Map) list.get(i)).get("ucmDelYn"));
			delYN = ((Map) list.get(i)).get("ucmDelYn").toString();
			if (delYN.equals("Y")) {
				dataMap.put("select", new Boolean(true));
			}
			if (!lvDetail.equals("")) {
				dataMap.put("lvDetail", ((Map) list.get(i)).get(lvDetail));
			}
//        	dataMap.put("hideCheckbox", new Boolean(true));
			dataMap.put("depth", nextDepth);
			dataList.add(dataMap);
		}

		JSON json = JSONSerializer.toJSON(dataList, new JsonConfig());
		res.setContentType("application/x-json;charset=euc-kr");
		res.getWriter().print(json.toString());
	}

	@RequestMapping(value = "/FM-INSPT002.do")
	public String fmInspt002(@ModelAttribute("searchVO") SearchVO searchVO,
							 HttpServletRequest req, ModelMap model) throws Exception {

		model.addAttribute("authKey", (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));
		return "FM-INSPT002";
	}

	@RequestMapping(value = "/FM-INSPT002_list.do")
	public ModelAndView fmInspt002_list(@RequestParam Map<String, String> map, ModelMap model,
										HttpServletRequest req, HttpServletResponse res) throws Exception {
		ModelAndView mav = new ModelAndView();

		map.put("ufmBcyCod", (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		map.put("usrKey", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		map.put("authKey", (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", fmInsptService.fm_inspt002_list(map));
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping(value = "/FM-INSPT002_mngList.do")
	public ModelAndView fmInspt002_mng_list(@RequestParam Map<String, String> map, ModelMap model,
											HttpServletRequest req, HttpServletResponse res) throws Exception {
		ModelAndView mav = new ModelAndView();

		map.put("usrKey", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		map.put("authKey", (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", fmInsptService.fm_inspt002_mng_list(map));
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping(value = "/FM-INSPT002_popup.do")
	public String fmInspt002_popup(@RequestParam Map<String, String> map,
								   HttpServletRequest req, ModelMap model) throws Exception {

		map.put("authKey", (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));
		map.put("usrKey", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		model.addAttribute("info", fmInsptService.fm_inspt002_info(map));
		return "FM-INSPT002_popup";
	}

	@RequestMapping(value = "/FM-INSPT002_mng_popup.do")
	public String fmInspt002_mng_popup(@RequestParam Map<String, String> map,
									   HttpServletRequest req, ModelMap model) throws Exception {

		EgovMap info = fmInsptService.fm_inspt002_mng_info(map);

		FileVO fvo = new FileVO();
		fvo.setUmf_con_key(info.get("ufpMapKey").toString());
		fvo.setUmf_tbl_gbn(Constants.FILE_GBN_INS);
		fvo.setUmf_con_gbn(Constants.FILE_CON_INS);
		model.addAttribute("authKey", (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));
		model.addAttribute("fileList", fmBoardService.fm_file(fvo));
		model.addAttribute("info", info);


		return "FM-INSPT002_mng_popup";
	}

	@RequestMapping(value = "/FM-INSPT002_W.do")
	public ModelAndView fmInspt002_w(@RequestParam Map<String, Object> map, ModelMap model,
									 HttpServletRequest req, HttpServletResponse res) throws Exception {
		ModelAndView mav = new ModelAndView();

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		map.put("ufpWrkId", req.getParameterValues("ufpWrkId"));
		map.put("mngType", req.getParameterValues("mngType"));
		map.put("rgtId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		map.put("ufmBcyCod", (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		fmInsptService.fm_inspt002_insert(map);

		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping(value = "/FM-INSPT002_U.do")
	public ModelAndView fmInspt002_u(@RequestParam Map<String, Object> map, ModelMap model,
									 HttpServletRequest req, HttpServletResponse res) throws Exception {
		ModelAndView mav = new ModelAndView();

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");
		map.put("ufpWrkId", req.getParameterValues("ufpWrkId"));
		map.put("mngType", req.getParameterValues("mngType"));
		map.put("uptId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		fmInsptService.fm_inspt002_update(map);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping(value = "/FM-INSPT002_MNG_U.do")
	public ModelAndView fmInspt002_mng_u(@RequestParam Map<String, String> map, ModelMap model,
										 HttpServletRequest req, HttpServletResponse res) throws Exception {
		ModelAndView mav = new ModelAndView();

		req.setAttribute("uploadPath", propertyService.getString("insptUploadPath"));
		FileUpload fu = new FileUpload();
		List<FileVO> list = fu.fileuplaod(req, Constants.FILE_GBN_INS, Constants.FILE_CON_INS);
		map.put("uptId", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		map.put("authKey", (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));
		fmInsptService.fm_inspt002_mng_update(map, list);

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping(value = "/FM-INSPT002_I_popup.do")
	public String fmInspt002_i_popup(@RequestParam Map<String, String> map,
									 HttpServletRequest req, ModelMap model) throws Exception {

		map.put("ufmBcyCod", (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		map.put("usrKey", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		map.put("authKey", (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));

		model.addAttribute("paramMap", map);
		model.addAttribute("result", fmInsptService.fm_inspt002_list(map));

		return "FM-INSPT002_I_popup";
	}

	@RequestMapping(value = "/FM-INSPT002_C_popup.do")
	public String fmInspt002_c_popup(@RequestParam Map<String, String> map,
									 HttpServletRequest req, ModelMap model) throws Exception {
		map.put("ufmBcyCod", (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

		model.addAttribute("paramMap", map);
		model.addAttribute("result", fmInsptService.fm_inspt002_list(map));

		return "FM-INSPT002_C_popup";
	}

	@RequestMapping(value = "/FM-INSPT003.do")
	public String fmInspt003(@ModelAttribute("searchVO") SearchVO searchVO,
							 HttpServletRequest req, ModelMap model) throws Exception {

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("sesAuthKey", (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));
		paramMap.put("sesServiceKey", (String) req.getSession().getAttribute(CommonConfig.SES_SERVICE_KEY));
		paramMap.put("sesDeptKey", (String) req.getSession().getAttribute(CommonConfig.SES_DEPT_KEY));


		return "FM-INSPT003";
	}

	@RequestMapping(value = "/FM-INSPT003_getCntrWork.do")
	public ModelAndView fmInspt003_getCntrWork(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model,
											   HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

		searchVO.setManCyl((String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

		searchVO.setCompany(propertyService.getString("company"));
		List<?> resultList = fmInsptService.fm_inspt003_list(searchVO);
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();

		JSONObject jsonObj = new JSONObject();
		JSONSerializer jsn = new JSONSerializer();
		System.out.println(jsn.toJSON(resultList));

		resultMap.put("resultList", resultList);
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping(value = "/FM-INSPT004.do")
	public String fmInspt004(@ModelAttribute("searchVO") SearchVO searchVO,
							 HttpServletRequest req, ModelMap model) throws Exception {

		model.addAttribute("authKey", (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));
		return "FM-INSPT004";
	}


	@RequestMapping(value = "/FM-INSPT004_getCntrWork.do")
	public ModelAndView fmInspt004_getCntrWork(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {

		ModelAndView mav = new ModelAndView();

		Map<String, String> map = new HashMap<String, String>();
		map.put("standard", req.getParameter("standard"));
		map.put("service", req.getParameter("service"));
		map.put("ucm1lvCod", req.getParameter("ucm1lvCod"));
		map.put("ucmBcyCod", (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		map.put("utwWrkSta", "90");
		List<?> resultList = fmInsptService.fm_inspt004_list(map);    //QR_COMPS003_C


		// 2016-11-01, 별도로 분리(FM-INSPT001_getCntrWork 참고)
		map.put("manCyl", (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		map.put("msrYn", "Y");

		List<?> fileList = fmInsptService.fm_inspt001_getFileList(map);
//		List<?> fileList = fmInsptService.fm_inspt001_getFileList_by_department_of_service(map);

		List<EgovMap> saFile = fmInsptService.getMeasureSaFile(map);

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
//		resultMap.put("brdlist", brdlist);
		resultMap.put("resultList", resultList);
		resultMap.put("fileList", fileList);
		resultMap.put("saFile", saFile);
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");


		return mav;
	}

	@RequestMapping(value = "/FM-INSPT004_getCtrCode.do")
	public ModelAndView fmInspt004_getCtrCode(HttpServletRequest req, HttpServletResponse res) throws Exception {
		ModelAndView mav = new ModelAndView();

		Map<String, String> map = new HashMap<String, String>();
		map.put("standard", req.getParameter("standard"));
		map.put("service", req.getParameter("service"));
		//map.put("ucm1lvCod", req.getParameter("ucm1lvCod"));
		map.put("ucmBcyCod", (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

		List<?> list = fmInsptService.fm_inspt004_getCtrCode(map);

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
		resultMap.put("result", list);
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}


	@RequestMapping("/FM-INSPT004_popup.do")
	public String fmInspt004_popup(ModelMap model, @RequestParam Map<String, String> map,
								   HttpServletRequest req, HttpServletResponse res) throws Exception {

		map.put("auth", (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));
		List<?> formlist = fmInsptService.fm_inspt004_popup(map);

		model.addAttribute("formlist", formlist);
		model.addAttribute("mapKey", map.get("mapKey"));

		return "FM-INSPT004_popup";
	}

	@RequestMapping(value = "/FM-INSPT004_MAPPING.do")
	public ModelAndView fmInspt004_popup_mapping(ModelMap model,
												 HttpServletRequest req, HttpServletResponse res) throws Exception {
		String brdKey = req.getParameter("brdKey");
		String mapKey = req.getParameter("mapKey");

		String rgt_id = (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY);
		Map map = new HashMap();
		map.put("mapKey", mapKey);
		map.put("brdKey", brdKey);
		map.put("rgt_id", rgt_id);
		fmInsptService.fm_inspt004_popup_mapping(map);

		ModelAndView mav = new ModelAndView();
		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");
		mav.addObject("result", "S");
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping(value = "/FM-INSPT004_SAV.do")
	public ModelAndView fmInspt004_SAV(@RequestParam Map<String, String> map, final HttpServletRequest req, HttpServletResponse res) throws Exception {
		/** EgovPropertyService.sample */

		ModelAndView mav = new ModelAndView();

		// 2018-03-22 s, 건별로 처리하는것 으로 변경
		/*
		String ucmMsrDtl = req.getParameter("ucmMsrDtl");
		if (ucmMsrDtl != "") {
			String[] pairs = ucmMsrDtl.split("\\^\\*\\^");
			for (int i = 0; i < pairs.length - 1; i = i + 2) {
				Map<String, String> map = new HashMap<String, String>();
			    map.put("ctrKey", pairs[i]);
			    map.put("ucmMsrDtl", pairs[i+1]);
			    map.put("ucmRgtId", (String)req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
			    log.debug(map);
			    fmInsptService.fm_inspt004_update(map);
			}
		}
		*/
		fmInsptService.fm_inspt004_update(map);

		res.setContentType("text/xml; charset=UTF-8");
		mav.setViewName("jsonView");
		return mav;
	}

	@RequestMapping(value = "/FM-INSPT004_downloadZip.do")
	public void fmInspt004_downloadZip(ModelMap model,
									   HttpServletRequest req, HttpServletResponse res) throws Exception {

		Map<String, String> map = new HashMap<String, String>();
		map.put("standard", req.getParameter("standard"));
		map.put("service", req.getParameter("service"));
		map.put("manCyl", (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		map.put("ucm1lvCod", req.getParameter("ucm1lvCod"));
		map.put("ucmBcyCod", (String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		List<EgovMap> list = (List<EgovMap>) fmInsptService.fm_inspt004_downloadList(map);
//		List<EgovMap> saFileList = (List<EgovMap>) fmInsptService.getMeasureSaFile(map);
		String zipName = fmInsptService.fm_inspt004_createZip(list);

		String fileName = "대책명세서관리.zip";

		File file = new File(zipName);

		/*
		byte[] fileBytes = new byte[(int) file.length()];
        FileInputStream in = null;

        try {
            in = new FileInputStream(file);
            in.read(fileBytes);
        } finally {
            if (in != null)
                in.close();
        }

        OutputStream out = null;

        try {
    		res.setContentType("application/zip");
        	res.setHeader("Content-Transfer-Encoding:", "binary");
    		res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("ksc5601"), "8859_1")  + ";");
    		res.setHeader("Pragma", "no-cache;");
    		res.setHeader("Expires", "-1;");
    		res.setHeader("Content-Length", "" + file.length());

            out = res.getOutputStream();
            out.write(fileBytes);
            out.flush();
        } finally {
            if (out != null) {
                out.close();
            }
            file.delete();
        }*/

		// 2017-09-12, 대용량 파일의 out of memory 문제 해결
		ServletOutputStream out = res.getOutputStream();

		try {
			res.setContentType("application/zip");
			res.setHeader("Content-Transfer-Encoding:", "binary");
			res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("ksc5601"), "8859_1") + ";");
			res.setHeader("Pragma", "no-cache;");
			res.setHeader("Expires", "-1;");
			res.setHeader("Content-Length", "" + file.length());

			FileInputStream in = new FileInputStream(file);
			byte[] buffer = new byte[8 * 1024];
			int bytesread = 0, bytesBuffered = 0;

			while ((bytesread = in.read(buffer)) > -1) {
				out.write(buffer, 0, bytesread);
				bytesBuffered += bytesread;
				if (bytesBuffered > 1024 * 1024) { //flush after 1MB
					bytesBuffered = 0;
					out.flush();
				}
			}
			in.close();
		} finally {
			if (out != null) {
				out.flush();
			}
			file.delete();
		}
	}


	/*
	 * Polaris Converter View
	 *
	 */
	@RequestMapping(value = "/FM-INSPT004_viewDoc.do")
	public String fmInspt004_viewDoc(ModelMap model,
									 HttpServletRequest req, HttpServletResponse res) throws Exception {

		String key = req.getParameter("umfFleKey");
		String idx = commonUtilService.getPolarisConverterId(key);

		if (idx == null) {
			// 2016-11-03, error 페이지를 download 로 대체
			return "redirect:/common/getFileDown.do?downKey=" + key;
		}
		return "redirect:/converter/viewer/" + idx;
	}

	@RequestMapping(value = "/FM-INSPT004_excel_popup.do")
	public String fmInspt004_excel_popup(HttpServletRequest req, ModelMap model) {

		model.addAttribute("compList", excelNewService.getCompList());
		return "FM-INSPT004_excel_popup";
	}

	@RequestMapping(value = "/FM-INSPT004_excelNew_Upload.do")
	public ModelAndView fmInspt004_excel_excelNew_Upload(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {
		ModelAndView mav = new ModelAndView();
		Map<String, Object> resultMap = new HashMap<String, Object>();

		String standard = req.getParameter("standard");
		String service = req.getParameter("service");

		if (standard.equals(null) || standard.length() == 0) {
			resultMap.put("result", "컴플라이언스의 코드가 없습니다");
		} else {
			Map<String, Object> mapReqInfo = new HashMap<>();
			mapReqInfo.put("UCM_BCY_COD", req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
			mapReqInfo.put("UCM_CTR_GBN", standard);
			mapReqInfo.put("UCM_SVC_COD", service);

			String strResult = excelNewService.fm_inspt004_excelNew(mapReqInfo, req);
			resultMap.put("result", strResult);
/*

			String strResult = "";
			if(!stndKind.equals("default")&&stndKind!="null"){

				if(result.get("result") == "success"){
					Map<String, Object> mapExcel = (Map<String, Object>) result.get("excel");
					strResult = excelNewService.fm_comps003_excelNew(stndKind, mapReqInfo, mapExcel);
				}else{
					strResult = Constants.RETURN_FAIL;
					resultMap.put("message", result.get("message"));
				}
			}else{
				strResult = excelNewService.fm_comps003_excelNew_D3(mapReqInfo, req);
			}
			resultMap.put("result", strResult);*/
		}
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/xml; charset=UTF-8");
		mav.addAllObjects(resultMap);
		mav.setViewName("jsonView");

		return mav;
	}

	/**
	 * [검색에서 넘어 오는 정보들]
	 * bcyCode 대주기 코드,
	 * service 서비스 코드,
	 * standard 컴플라이언스 코드
	 * workState 작업 상태 (INITIATED 진행중, FINISHED 완료)
	 * <p>
	 * [통제항목 클릭통해 넘어 오는 정보들]
	 * controlItem CTR_MTR 식별번호
	 */
	@RequestMapping(value = "FM-INSPT006_MANAGER_POPUP.do", method = RequestMethod.GET)
	public String managerPage(@ModelAttribute SearchVO searchVO, ModelMap model, HttpServletRequest httpServletRequest) {

		List mainCycleList = commonService.getAllMainCycleList();
		List serviceList = commonService.getAllServiceList();
		List complianceList = inspectionManagerService.getAvailableComplianceList();
		String selectedComplianceKind = StringUtils.isEmpty(searchVO.getStandard()) ? "" : fmCompsService.getKindOfCompliance(searchVO.getStandard());

		List<EgovMap> controlItemList = new ArrayList<>();
		if (!(StringUtils.isEmpty(searchVO.getBcyCode()) || StringUtils.isEmpty(searchVO.getService())
				|| StringUtils.isEmpty(searchVO.getStandard()))) {
			controlItemList = inspectionManagerService.getControlItemList(searchVO);
		}

		for (EgovMap egovMap : controlItemList) {
			String currentQueryString = httpServletRequest.getQueryString();
			String addQueryString = "controlItem=" + egovMap.get("ucmCtrKey");
			String mergedQueryString = CommonUtil.mergeUrlQuery(currentQueryString, addQueryString);
			egovMap.put("urlQuery", mergedQueryString);
		}

		CntrVO controlItem = new CntrVO();
		List<EgovMap> answerList = new ArrayList<>();
		List<EgovMap> fileListGroupByActivity = new ArrayList<>();
		EgovMap additionalInfo = new EgovMap();
		if (!StringUtils.isEmpty(searchVO.getControlItem())) {
			// 항목 기본 정보
			controlItem = fmCompsService.getControlItem(Integer.parseInt(searchVO.getControlItem()));
			// 항목 결과 정보 : 과기정통부, 관리/물리 취약점, 정보보호 수준평가
			answerList = inspectionManagerService.getAnswerList(searchVO);
			// 관련 증적 정보
			fileListGroupByActivity = inspectionManagerService.getFileListGroupByActivity(searchVO);
			//
			additionalInfo = inspectionManagerService.getInspectionResultAdditionalInfoOfControlItem(searchVO);
		}

		model.addAttribute("currentUrl", httpServletRequest.getRequestURI());
		model.addAttribute("mainCycleList", mainCycleList);
		model.addAttribute("serviceList", serviceList);
		model.addAttribute("complianceList", complianceList);
		model.addAttribute("controlItemList", controlItemList);
		model.addAttribute("controlItem", controlItem);
		model.addAttribute("answerList", answerList);
		model.addAttribute("additionalInfo", additionalInfo);
		model.addAttribute("selectedComplianceKind", selectedComplianceKind);
		model.addAttribute("fileListGroupByActivity", fileListGroupByActivity);

		return "FM-INSPT006_MANAGER_POPUP";
	}

	/**
	 * [검색에서 넘어 오는 정보들]
	 * bcyCode 대주기 코드,
	 * service 서비스 코드,
	 * standard 컴플라이언스 코드
	 * workState 작업 상태 (INITIATED 진행중, FINISHED 완료)
	 * <p>
	 * [통제항목 클릭통해 넘어 오는 정보들]
	 * controlItem CTR_MTR 식별번호
	 * <p>
	 * [항목 결과에서 넘어오는 정보들]
	 * selectedAnswer[] 선택된 문항의 식별번호 값들
	 */
	@RequestMapping(value = "FM-INSPT006_ANSWER_RESULT_RW.do", method = RequestMethod.POST)
	public String saveInspectionResult(@ModelAttribute SearchVO searchVO,
									   @RequestParam(value = "selectedAnswer[]", required = false, defaultValue = "") String[] selectedAnswer,
									   HttpServletRequest httpServletRequest) {
		String referer = httpServletRequest.getHeader("Referer");

		ArrayList<String> selectedAnswerList = new ArrayList<>();

		// selectedAnswer 에 입력된 값 중 빈값들은 제거한다.
		for (String answer : selectedAnswer) {
			if (StringUtils.isNotBlank(answer)) {
				selectedAnswerList.add(answer);
			}
		}

		selectedAnswer = selectedAnswerList.toArray(new String[selectedAnswerList.size()]);

		String complianceKind = fmCompsService.getKindOfCompliance(searchVO.getStandard());

		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

		ObjectMapper objectMapper = new ObjectMapper();

		Map<String, Object> map = objectMapper.convertValue(searchVO, Map.class);
		map.put("selectedAnswer", selectedAnswer);
		map.put("workerKey", loginVO.getuum_usr_key());
		map.put("complianceKind", complianceKind);

		inspectionManagerService.saveInspectionResult(map);

		return "redirect:" + referer;
	}

	@RequestMapping(value = "FM-INSPT006_INFO_RESULT_RW.do", method = RequestMethod.POST)
	public String saveInspectionAdditionalInfo(HttpServletRequest httpServletRequest,
											   @ModelAttribute SearchVO searchVO,
											   @RequestParam(value = "result-detail") String resultDetail,
											   @RequestParam(value = "result-related-document", required = false, defaultValue = "") String resultRelatedDocument) {
		String referer = httpServletRequest.getHeader("Referer");

		searchVO.setSearchExt1(resultDetail);
		searchVO.setSearchExt2(resultRelatedDocument);
		inspectionManagerService.saveInspectionResultAdditionalInfo(searchVO);

		return "redirect:" + referer;
	}

	/**
	 * 통제항목 관련 자체 증적 파일 목록
	 */
	@RequestMapping(value = "FM-INSPT006_FILELIST_OF_CONTROL.do", method = RequestMethod.GET)
	public ModelAndView getFileListOfControlItem(@ModelAttribute FileVO fileVO,
												 HttpServletRequest httpServletRequest) throws Exception {
		return makeJsonResponse().addObject("files", fileUploadService.getFileListByCurrentCycle(fileVO));
	}

	/**
	 * 통제항목 자체 증적 파일 등록/삭제
	 */
	@RequestMapping(value = "FM-INSPT006_SAVE_FILE_OF_CONTROL.do", method = RequestMethod.POST)
	public ModelAndView saveFileOfControl(@ModelAttribute FileVO fileVO,
										  @RequestParam(value = "deletedFiles[]", required = false) String[] deletedFiles,
										  HttpServletRequest httpServletRequest) throws Exception {
		fileVO.setUmf_tbl_gbn("IST");

		if (deletedFiles != null && deletedFiles.length > 0)
			fileUploadService.delFile(deletedFiles);

//		if (commonUtil.hasFileOnRequest(httpServletRequest))
			fileUploadService.saveFile(fileVO, httpServletRequest);

		return makeJsonResponse().addObject("deletedFiles", deletedFiles);
	}

	/**
	 * 디렉토리 이름이 통제항목의 3Lv 코드인 구조체의 압축파일을 업로드하면
	 * 해당 조건의 컴플라이언스 통제항목 자체 증적으로 일괄 등록되게 하는 기능이다.
	 * <p>
	 * 기반시설 점검 관련 증적의 UWO_MNG_FLE 의 캄럼 구성은 아래와 같이 한다.
	 * UMF_TBL_GBN : IST (고정)
	 * UMF_CON_GBN : 컴플라이언스 GBN (UWO_CTR_MTR.UCM_CTR_GBN 값)
	 * UMF_CON_KEY : 통제항목 식별번호 (UWO_CTR_MTR.UCM_CTR_KEY 값)
	 * <p>
	 * TODO 공통기능, 전역 사용 변수 분리와 같은 리팩토링이 필요하다. 지금은 시간이 없다.
	 *
	 * @param file
	 * @param bcyCode
	 * @param standard
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "FM-INSPT006_SAVE_FILE_IN_ZIP.do", method = RequestMethod.POST)
	public String saveFileInZip(@RequestParam("zipFile") MultipartFile file,
								@RequestParam("zipBcyCode") String bcyCode,
								@RequestParam("zipStandard") String standard,
								HttpServletRequest httpServletRequest) throws Exception {

		SearchVO searchVO = new SearchVO();
		searchVO.setBcyCode(bcyCode);
		searchVO.setStandard(standard);

		String referer = httpServletRequest.getHeader("Referer");

		fmInsptService.fm_inspt006_zip_upload(file, searchVO);

		return "redirect:" + referer;
	}

	@RequestMapping(value = "FM-INSPT006_DOWNLOAD_EXCEL.do", method = RequestMethod.POST)
    public ModelAndView fmInspt006DownloadExcelGet(@RequestParam String bcyCode,
                                                   @RequestParam String service,
                                                   @RequestParam String standard) {

		ModelAndView modelAndView = new ModelAndView("inspt006ExcelView");

        Map<String, String> controlFilterMap = new HashMap<>();
        controlFilterMap.put("bcyCode", bcyCode);
        controlFilterMap.put("service", service);
        controlFilterMap.put("standard", standard);

        String complianceKind = fmCompsService.getKindOfCompliance(standard);
        List<EgovMap> controlItemList = inspectionManagerService.getControlItemList(controlFilterMap);
        List<EgovMap> answerList = inspectionManagerService.getAnswerList(controlFilterMap);
        List<EgovMap> additionalInfoList = inspectionManagerService.getInspectionResultAdditionalInfo(controlFilterMap);

		modelAndView.addObject("filter", controlFilterMap);
		modelAndView.addObject("complianceKind", complianceKind);
		modelAndView.addObject("controlItemList", controlItemList);
		modelAndView.addObject("answerList", answerList);
		modelAndView.addObject("additionalInfoList", additionalInfoList);

        return modelAndView;
    }

	@RequestMapping(value = "FM-INSPT006_UPLOAD_EXCEL_POPUP.do", method = RequestMethod.GET)
	public ModelAndView fmInspt006UploadExcelPopup(@RequestParam String bcyCode,
												   @RequestParam String service,
												   @RequestParam String standard) {

		ModelAndView modelAndView = new ModelAndView("FM-INSPT006_UPLOAD_EXCEL_POPUP");
		modelAndView.addObject("bcyCode", bcyCode);
		modelAndView.addObject("service", service);
		modelAndView.addObject("standard", standard);

		return modelAndView;
	}

	@RequestMapping(value = "FM-INSPT006_SMART_UPLOAD_POPUP.do", method = RequestMethod.GET)
	public ModelAndView fmInspt006SmartUploadPopup(@RequestParam String bcyCode,
												   @RequestParam String service,
												   @RequestParam String standard) {

		ModelAndView modelAndView = new ModelAndView("FM-INSPT006_SMART_UPLOAD_POPUP");
		modelAndView.addObject("bcyCode", bcyCode);
		modelAndView.addObject("service", service);
		modelAndView.addObject("standard", standard);

		return modelAndView;
	}

	@RequestMapping(value = "FM-INSPT006_SMART_UPLOAD_ASYNC.do", method = RequestMethod.POST)
	public ModelAndView fmInspt006SmartUpload(HttpServletRequest httpServletRequest,
												  HttpServletResponse httpServletResponse,
												  @RequestParam MultipartFile file,
												  @ModelAttribute SearchVO searchVO,
												  @RequestParam String bcyCode,
												  @RequestParam String service,
												  @RequestParam String standard) throws Exception {

		ModelAndView modelAndView = makeJsonResponse();
		modelAndView.addObject("status", 200);

		File uploadPath = new File(propertyService.getString("uploadpath"));

		if (file.isEmpty()) {
			httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
			return null;
		}

		String fileName = file.getOriginalFilename();
		MediaType mediaType = MediaType.parse(file.getContentType());
		String fileExtension = FilenameUtils.getExtension(fileName);

		if (mediaType.equals(MediaType.MICROSOFT_EXCEL) || mediaType.equals(MediaType.OOXML_SHEET)) {
			fmInsptService.fm_inspt006_excel_upload(file, searchVO);
		} else if (mediaType.equals(MediaType.ZIP) || fileExtension.equals("zip") ) {

			ZipArchiveInputStream zipArchiveInputStream = new ZipArchiveInputStream(file.getInputStream());
			ZipEntry zipEntry = zipArchiveInputStream.getNextZipEntry();

			while (zipEntry != null) {
				String zipEntryName = zipEntry.getName();
				String zipEntryExtension = FilenameUtils.getExtension(zipEntryName);

				if (zipEntryExtension.equals("xls") || zipEntryExtension.equals("xlsx")) {
					extractFile(zipArchiveInputStream, uploadPath, zipEntryName);
					File excelFile = new File(uploadPath, zipEntryName);
					MultipartFile excelMultipartFile = new MockMultipartFile("file", excelFile.getName(),
							MediaType.MICROSOFT_EXCEL.toString(), IOUtils.toByteArray(new FileInputStream(excelFile)));
					fmInsptService.fm_inspt006_excel_upload(excelMultipartFile, searchVO);
					break;
				}

				zipEntry = zipArchiveInputStream.getNextZipEntry();
			}

			fmInsptService.fm_inspt006_zip_upload(file, searchVO);


		} else {

		}

		// 처리 결과
		httpServletResponse.setStatus(HttpStatus.OK.value());

		modelAndView.addObject("test", new String[]{"a", "b", "c"});

		return modelAndView;
	}

	private static void extractFile(InputStream inputStream, File outDir,
									String name) throws IOException {
		int count = -1;
		byte buffer[] = new byte[4096];
		BufferedOutputStream out = new BufferedOutputStream(
				new FileOutputStream(new File(outDir, name)), 4096);
		while ((count = inputStream.read(buffer, 0, 4096)) != -1) {
			out.write(buffer, 0, count);
		}
		out.close();
	}

	@RequestMapping(value = "FM-INSPT006_UPLOAD_EXCEL.do", method = RequestMethod.POST)
	public ModelAndView fmInspt006UploadExcel(HttpServletRequest httpServletRequest,
											  	@ModelAttribute SearchVO searchVO,
											  	@RequestParam MultipartFile file,
												@RequestParam String bcyCode,
												@RequestParam String service,
												@RequestParam String standard) throws Exception {

		ModelAndView modelAndView = makeJsonResponse();

		fmInsptService.fm_inspt006_excel_upload(file, searchVO);

		modelAndView.addObject("resultCode", Constants.RETURN_SUCCESS);

		return modelAndView;
	}


	@RequestMapping(value = "FM-INSPT007_FILELIST_OF_CONTROL.do", method = RequestMethod.GET)
	public ModelAndView getFileListOfControlItem007(@ModelAttribute FileVO fileVO,
													HttpServletRequest httpServletRequest) throws Exception {
		return makeJsonResponse().addObject("files", fileUploadService.getFileListByCurrentCycle(fileVO));
	}

	@RequestMapping(value = "FM-INSPT007_SAVE_FILE_OF_CONTROL.do", method = RequestMethod.POST)
	public ModelAndView saveFileOfControl007(@ModelAttribute FileVO fileVO,
											 @RequestParam(value = "deletedFiles[]", required = false) String[] deletedFiles,
											 HttpServletRequest httpServletRequest) throws Exception {
		fileVO.setUmf_tbl_gbn("SA");

		if (deletedFiles != null && deletedFiles.length > 0)
			fileUploadService.delFile(deletedFiles);

//		if (commonUtil.hasFileOnRequest(httpServletRequest))
			fileUploadService.saveFile(fileVO, httpServletRequest);

		return makeJsonResponse().addObject("deletedFiles", deletedFiles);
	}


	@RequestMapping(value = "FM-INSPT007_MANAGER_POPUP.do", method = RequestMethod.GET)
	public String fmInspt007ManagerPopup(HttpServletRequest httpServletRequest, ModelMap map) {

	    LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
	    String authGbn = loginVO.getUat_auh_gbn();
        String stOrg = loginVO.getStOrg();
        String hqOrg = loginVO.getHqOrg();
        String gpOrg = loginVO.getGpOrg();
		List mainCycleList = commonService.getAllMainCycleList();

		map.put("mainCycleList", mainCycleList);
        map.put("authGbn", authGbn);
        map.put("stOrg", stOrg);
        map.put("hqOrg", hqOrg);
        map.put("gpOrg", gpOrg);
		map.put("userKey", loginVO.getuum_usr_key());

		return "FM-INSPT007_MANAGER_POPUP";
	}

	@RequestMapping(value = "FM-INSPT007_MANAGER_EXCEL_DOWNLOAD.do", method = RequestMethod.GET)
	public ModelAndView fmInspt007ManagerExcelDownload(@RequestParam String bcyCode, @RequestParam String depCode) {

		ModelAndView modelAndView = new ModelAndView("inspt007SaExcelView");

		List<EgovMap> saExcelData = fmInsptService.getSaExcelData(bcyCode, depCode);
		modelAndView.addObject("bcyCode", bcyCode);
		modelAndView.addObject("depCode", depCode);
		modelAndView.addObject("saExcelData", saExcelData);

		return modelAndView;
	}

	@RequestMapping(value = "FM-INSPT007_MANAGER_ZIP_DOWNLOAD.do", method = RequestMethod.GET)
	public void fmInspt007ManagerZipDownload(@RequestParam String bcyCode, @RequestParam String depCode,
									   HttpServletRequest req, HttpServletResponse res) throws Exception {

		Map<String, String> map = new HashMap<String, String>();
		map.put("bcyCode", bcyCode);
		map.put("depCode", depCode);
		List<EgovMap> list = fmInsptService.getSaZipList(map);
		String zipName = fmInsptService.fm_inspt004_createZip(list);

		String fileName = "SA증적자료.zip";

		File file = new File(zipName);

		// 2017-09-12, 대용량 파일의 out of memory 문제 해결
		ServletOutputStream out = res.getOutputStream();

		try {
			res.setContentType("application/zip");
			res.setHeader("Content-Transfer-Encoding:", "binary");
			res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("ksc5601"), "8859_1") + ";");
			res.setHeader("Pragma", "no-cache;");
			res.setHeader("Expires", "-1;");
			res.setHeader("Content-Length", "" + file.length());

			FileInputStream in = new FileInputStream(file);
			byte[] buffer = new byte[8 * 1024];
			int bytesread = 0, bytesBuffered = 0;

			while ((bytesread = in.read(buffer)) > -1) {
				out.write(buffer, 0, bytesread);
				bytesBuffered += bytesread;
				if (bytesBuffered > 1024 * 1024) { //flush after 1MB
					bytesBuffered = 0;
					out.flush();
				}
			}
			in.close();
		} finally {
			if (out != null) {
				out.flush();
			}
			file.delete();
		}
	}

	@RequestMapping(value = "FM-INSPT007_MANAGER_EXCEL_UPLOAD_POPUP.do", method = RequestMethod.GET)
	public ModelAndView fmInspt007UploadExcelPopup(@RequestParam String bcyCode) {

		ModelAndView modelAndView = new ModelAndView("FM-INSPT007_UPLOAD_EXCEL_POPUP");
		modelAndView.addObject("bcyCode", bcyCode);

		return modelAndView;
	}

	@RequestMapping(value = "FM-INSPT007_UPLOAD_EXCEL.do", method = RequestMethod.POST)
	public ModelAndView fmInspt007UploadExcel(HttpServletRequest httpServletRequest,
											  @RequestParam String bcyCode) throws Exception {

		ModelAndView modelAndView = makeJsonResponse();

		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

		MultipartHttpServletRequest mReq = (MultipartHttpServletRequest) httpServletRequest;
		InputStream file = commonUtilService.decryptFileDAC(mReq.getFile("excelFile"));
		Workbook wb = WorkbookFactory.create(file);

		Sheet resultSheet = wb.getSheetAt(0);
		Sheet metaSheet = wb.getSheetAt(1);

		// metaSheet 유효성 검사
		Row metaSheetRow = metaSheet.getRow(0);
		String inputHashValue = metaSheetRow.getCell(0).getStringCellValue();
		int inputKeyItemNumber = (int) metaSheetRow.getCell(1).getNumericCellValue();
		String inputBcyCode = metaSheetRow.getCell(2).getStringCellValue();
		String inputDepCode = metaSheetRow.getCell(3).getStringCellValue();

		String hashValue = DigestUtils.md5Hex(inputBcyCode + inputDepCode).toUpperCase();

		if ( ! inputHashValue.equals(hashValue)) {

		}

		// resultSheet 변경점 확인

		Map<Integer, Map<String, Object>> result = new HashMap<>();
		Map<String, Object> resultOfControl = null;

		for (int i = 1; i < resultSheet.getLastRowNum(); i++) {
			Row row = resultSheet.getRow(i);
			if (row == null) continue;

			int wrkKey = (int) row.getCell(2).getNumericCellValue();
			if (wrkKey < 1) continue;

			String wrkPrgText = (String) row.getCell(6).getStringCellValue();
			WorkVO.statusType wrkPrgEnum = WorkVO.statusType.fromTitle(wrkPrgText);

			if (wrkPrgEnum != null) {
				Map<String, Integer> map = new HashMap<>();
				map.put("wrkKey", wrkKey);
				map.put("wrkPrg", wrkPrgEnum.getValue());
				fmInsptService.updateWrkPrg(map);
			}
		}

		modelAndView.addObject("resultCode", Constants.RETURN_SUCCESS);

		return modelAndView;
	}

	@RequestMapping(value = "FM-INSPT007_GET_DATA.do", method = RequestMethod.POST)
	public ModelAndView fmInspt007GetData(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model,
										  HttpServletRequest req, HttpServletResponse res) throws Exception {
		ModelAndView mav = makeJsonResponse();

		String bcyCode = req.getParameter("bcyCode");
		String depLevel1 = req.getParameter("depLevel1");
		String depLevel2 = req.getParameter("depLevel2");
		String depLevel3 = req.getParameter("depLevel3");

		Map<String, String> map = new HashMap<String, String>();
		map.put("manCyl", bcyCode);
		map.put("standard", fmCompsService.getSaComplianceCode());
		map.put("service", fmCompsService.getSaServiceCode());
		map.put("ucm1lvCod", depLevel1);
		map.put("ucm2lvCod", depLevel2);
		map.put("ucm3lvCod", depLevel3);


		List<EgovMap> resultList = (List<EgovMap>) fmInsptService.fm_inspt001_list2(map);

		for (EgovMap egovMap : resultList) {
			Clob wrkDtlClob = (Clob) egovMap.get("wrkDtl");
			egovMap.put("wrkDtl", commonUtil.clobToString(wrkDtlClob));
		}

		if (req.getParameter("onlyAssignedWork") != null
				&& req.getParameter("onlyAssignedWork").equals("assigned")) {
			List<EgovMap> tempResultList = new ArrayList<>();
			for (EgovMap egovMap: resultList) {
				if (egovMap.get("utdTrcKey") != null) {
					tempResultList.add(egovMap);
				}
			}
			resultList = tempResultList;
		}

		List<?> fileList = fmInsptService.fm_inspt001_getFileList2(map);

		Map resultMap = new HashMap();
		resultMap.put("fileList", fileList);
		resultMap.put("resultList", resultList);
		resultMap.put("search", map);
		mav.addAllObjects(resultMap);

		return mav;
	}

	@RequestMapping(value = "FM-INSPT007_SAVE_FILE_IN_ZIP.do", method = RequestMethod.POST)
	public String saveFileInZip07(@RequestParam("zipFile") MultipartFile file,
								  @RequestParam("zipDepCode") String depCode,
								  @RequestParam("zipBcyCode") String bcyCode,
								  HttpServletRequest httpServletRequest) throws Exception {

		String standard = fmCompsService.getSaComplianceCode();

		String referer = httpServletRequest.getHeader("Referer");
		String uploadBasePath = propertyService.getString("uploadpath");
		String tblGbnOfInspectionFile = "SA";

		Map<String, String> controlItemListQueryConditionMap = new HashMap<>();
		controlItemListQueryConditionMap.put("bcyCod", bcyCode);
		controlItemListQueryConditionMap.put("ctrGbn", standard);

		List<EgovMap> controlItemList = fmCompsService.getControlItemList(controlItemListQueryConditionMap);

		if (controlItemList.isEmpty()) {
			// TODO 오류 처리 필요
			return "redirect:" + referer;
		}

		Map<String, EgovMap> controlItemMap = new HashMap<>();
		for (EgovMap egovMap : controlItemList) {
			controlItemMap.put((String) egovMap.get("ucm3lvCod"), egovMap);
		}

		// 압축 파일을 임시 저장한다.
		File zip = File.createTempFile(UUID.randomUUID().toString(), "temp");
		FileOutputStream o = new FileOutputStream(zip);
		IOUtils.copy(file.getInputStream(), o);
		o.close();

		// 임시 저장된 압축 파일을 zip4j 라이브러리를 이용해 압축 해제한다.
		String unzipDestinationPath = uploadBasePath + File.separator + "sa_temp_unzip" + File.separator + UUID.randomUUID().toString();
		File tempUnzipDirectory = new File(unzipDestinationPath);

		try {
			ZipFile zipFile = new ZipFile(zip);
			UnzipParameters param = new UnzipParameters();
			zipFile.setFileNameCharset("ISO8859-1");
			List list = zipFile.getFileHeaders();

			for (Iterator iterator = list.iterator(); iterator.hasNext(); ) {
				FileHeader fh = (FileHeader) iterator.next();
				byte[] b = fh.getFileName().getBytes("ISO8859-1");
				String fileName = null;
				try {
					fileName = new String(b, Charset.forName("EUC-KR"));
				} catch (Throwable e) {
					fileName = fh.getFileName();
				}
				zipFile.extractFile(fh, unzipDestinationPath, param, fileName);
			}

			File[] directoryList = tempUnzipDirectory.listFiles();

			if (directoryList == null) {
				// TODO 오류 처리 필요
				return "redirect:" + referer;
			}

			for (File controlItemDirectory : directoryList) {
				String controlItem3LvCode = controlItemDirectory.getName();

				// 디렉토리가 아니거나, 파일을 포함하고 있지 않거나,
				// 해당 디렉토리 이름이 통제항목 번호가 아닌 경우는 더 이상 진행하지 않는다.
				if (!controlItemDirectory.isDirectory()
						|| controlItemDirectory.listFiles() == null
						|| controlItemDirectory.listFiles().length < 1
						|| !controlItemMap.containsKey(controlItem3LvCode)) continue;

				for (File fileOfControlItem : controlItemDirectory.listFiles()) {

					// 알아야 하는 것들 umf_tbl_gbn, umf_con_gbn, umf_con_key, umf_bcy_cod
					// umf_tbl_gbn 은 IST 로 고정 값이다.
					// umf_con_gbn, umf_bcy_cod 는 요청할때 줘야하는 정보다.
					// umf_con_key 는 통제항목 번호 1.1.1 이 값을 이용해서 실제 식별번호를 알아내야 한다.

					EgovMap matchedControlItem = controlItemMap.get(controlItem3LvCode);
					int matchedControlItemKey = ((BigDecimal) matchedControlItem.get("ucmCtrKey")).intValueExact();

					String pathThatMoveTo = uploadBasePath + File.separator + tblGbnOfInspectionFile + File.separator + depCode;
					File directoryThatMoveTo = new File(pathThatMoveTo);

					String rawFileName = fileOfControlItem.getName();
					String fileName = rawFileName;
					String fileNameToBeMoved = UUID.randomUUID().toString() + "_" + fileName;

					log.debug("###[rawFileName]### : " + rawFileName);
					log.debug("###[fileName]### : " + fileName);
					log.debug("###[fileNameToBeMoved]### : " + fileNameToBeMoved);

					File destinationFilePath = new File(pathThatMoveTo + File.separator + fileNameToBeMoved);

					if (!directoryThatMoveTo.exists()) {
						directoryThatMoveTo.mkdirs();
					}

					fileOfControlItem.renameTo(destinationFilePath);

					// TODO FileUpload.java 내용과 중복된다. 리팩토링 필요하다.
					FileVO fileVO = new FileVO();

					fileVO.setUmf_tbl_gbn(tblGbnOfInspectionFile);
					fileVO.setUmf_con_gbn(depCode);
					fileVO.setUmf_con_key(String.valueOf(matchedControlItemKey));
					fileVO.setUmf_bcy_cod(bcyCode);
					fileVO.setUmf_rgt_id((String) httpServletRequest.getSession().getAttribute(CommonConfig.SES_USER_KEY));
					fileVO.setUmf_svr_pth(pathThatMoveTo);
					fileVO.setUmf_svr_fnm(fileNameToBeMoved);
					fileVO.setUmf_con_fnm(fileName);
					String[] ext = (fileName).split("\\.");
					fileVO.setUmf_fle_ext(ext[ext.length - 1]);
					fileVO.setUmf_fle_siz(Long.toString(fileOfControlItem.length()));

					fileUploadService.storeFile(fileVO);

				}
			}

		} catch (ZipException e) {
			e.printStackTrace();
		} finally {
			// 임시 압축파일을 삭제한다.
			zip.delete();
			// 임시 압축 디렉토리를 삭제한다.
			if (tempUnzipDirectory.isDirectory()) {
				FileUtils.deleteDirectory(tempUnzipDirectory);
			}

		}

		return "redirect:" + referer;
	}
}
