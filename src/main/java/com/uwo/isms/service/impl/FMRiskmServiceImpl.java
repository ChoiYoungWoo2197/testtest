package com.uwo.isms.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hsqldb.lib.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.uwo.isms.common.CommonConfig;
import com.uwo.isms.common.Constants;
import com.uwo.isms.common.UwoStringUtils;
import com.uwo.isms.dao.CommonUtilDAO;
import com.uwo.isms.dao.FMRiskmDAO;
import com.uwo.isms.domain.SearchVO;
import com.uwo.isms.service.CommonUtilService;
import com.uwo.isms.service.FMRiskmService;

import egovframework.rte.psl.dataaccess.util.EgovMap;


@Service("fmRiskmService")
public class FMRiskmServiceImpl implements FMRiskmService {

	Logger log = LogManager.getLogger(FMRiskmServiceImpl.class);

	@Resource(name="fmRiskmDAO")
	private FMRiskmDAO fmRiskmDAO;

	@Resource(name="commonUtilDAO")
	private CommonUtilDAO commonUtilDAO;

	@Autowired
	CommonUtilService commonUtilService;

	@Override
	public List<?> fm_riskm003_list(Map<String, String> map) {
		return fmRiskmDAO.fm_riskm003_list(map);
	}

	@Override
	public EgovMap fm_riskm003_info(Map<String, String> map) {
		return fmRiskmDAO.fm_riskm003_info(map);
	}

	@Override
	public EgovMap fm_riskm003_rskInfo(Map<String, String> map) {
		return fmRiskmDAO.fm_riskm003_rskInfo(map);
	}

	@Override
	public void fm_riskm003_rskm_insert(Map<String, String> map) {
		getRskPntLvl(map);
		fmRiskmDAO.fm_riskm003_rskm_insert(map);
	}

	@Override
	public void fm_riskm003_rskm_update(Map<String, String> map) {
		getRskPntLvl(map);
		fmRiskmDAO.fm_riskm003_rskm_update(map);
	}

	@Override
	public List<?> fm_riskm003_rskList(Map<String, String> map) {
		return fmRiskmDAO.fm_riskm003_rskList(map);
	}

	@Override
	public List<?> fm_riskm003_svr_list() {
		return fmRiskmDAO.fm_riskm003_svr_list();
	}

	@Override
	public List<?> fm_riskm003_bcyList(Map<String, String> map) {
		return fmRiskmDAO.fm_riskm003_bcyList(map);
	}

    /*
	@Override
	public String fm_riskm003_excel_insert(HttpServletRequest req) {
		try {
			MultipartHttpServletRequest mreq = (MultipartHttpServletRequest) req;
			//HSSFWorkbook wb = new HSSFWorkbook(mreq.getFile("excelFile").getInputStream());
			InputStream file = commonUtilService.decryptFileDAC(mreq.getFile("excelFile"));
			Workbook wb = WorkbookFactory.create(file);

			Sheet sheet = wb.getSheetAt(0);

			for(int i=3; i<=sheet.getLastRowNum(); i++){
				Map<String, String> map = new HashMap<String, String>();
				Row row = sheet.getRow(i);
				if( row != null &&
					UwoStringUtils.isNotEmpty(row.getCell(4)) && UwoStringUtils.isNotEmpty(row.getCell(5)) &&
					UwoStringUtils.isNotEmpty(row.getCell(6)) && UwoStringUtils.isNotEmpty(row.getCell(7))) {

					Map<String, String> paramMap = new HashMap<String, String>();
					paramMap.put("grpCod", String.valueOf(row.getCell(4)));
					paramMap.put("cocCod", String.valueOf(row.getCell(5)));
					paramMap.put("rskPrcNam", String.valueOf(row.getCell(6)));
					EgovMap grpMap = fmRiskmDAO.fm_riskm003_grpInfo(paramMap);

					map.put("urgBcyCod" ,(String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
					map.put("urgRgtId"	,(String)req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
					map.put("urgDivCod"	, Constants.DIV_COD);
					map.put("urgSvcCod"	, grpMap.get("uagSvcCod").toString());
					map.put("urgDepCod"	, grpMap.get("uagDepCod").toString());
					map.put("urgGrpKey"	, grpMap.get("uagGrpKey").toString());
					map.put("urgMngId"	, grpMap.get("uagMngId").toString());
					map.put("urgAssVal"	, grpMap.get("uagGrpLvl").toString());
					map.put("urgCocKey"	, grpMap.get("uagCocKey").toString());
					map.put("urgRskPrc"	, grpMap.get("uagRskPrc").toString());
					map.put("urgRskChk"	, String.valueOf(row.getCell(7)));
					map.put("urgMesDes"	, String.valueOf(row.getCell(8)));
					map.put("urgMesYn"	, Constants.USE_N);
					map.put("urgLimDes"	, String.valueOf(row.getCell(9)));
					map.put("urgRstDes"	, String.valueOf(row.getCell(10)));
					getRskPntLvl(map);
					fmRiskmDAO.fm_riskm003_rskm_insert(map);
				}
			}
		} catch (Exception e){
			e.printStackTrace();
			return Constants.RETURN_FAIL;
		}
		return Constants.RETURN_SUCCESS;

	}
	*/

	/*
	@Override
	public String fm_riskm003_etcExcel_insert(HttpServletRequest req) {
		try {
			MultipartHttpServletRequest mreq = (MultipartHttpServletRequest) req;
			//HSSFWorkbook wb = new HSSFWorkbook(mreq.getFile("excelFile").getInputStream());
			InputStream file = commonUtilService.decryptFileDAC(mreq.getFile("excelFile"));
			Workbook wb = WorkbookFactory.create(file);

			Sheet sheet = wb.getSheetAt(0);

			for(int i=3; i<=sheet.getLastRowNum(); i++){
				Map<String, String> map = new HashMap<String, String>();
				Row row = sheet.getRow(i);
				if( row != null &&
					UwoStringUtils.isNotEmpty(row.getCell(0)) && UwoStringUtils.isNotEmpty(row.getCell(2)) ||
					UwoStringUtils.isNotEmpty(row.getCell(3)) && UwoStringUtils.isNotEmpty(row.getCell(4)) ) {

					Map<String, String> paramMap = new HashMap<String, String>();
					paramMap.put("sroCod", String.valueOf(row.getCell(0)));
					paramMap.put("cocCod", String.valueOf(row.getCell(2)));
					paramMap.put("rskPrcNam", String.valueOf(row.getCell(4)));
					EgovMap etcInfo = fmRiskmDAO.fm_riskm003_etc_info(paramMap);

					map.put("urgBcyCod" ,(String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
					map.put("urgRgtId"	,(String)req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
					map.put("urgSroKey"	, etcInfo.get("usmSroKey").toString());
					map.put("urgCocKey"	, etcInfo.get("usmCocKey").toString());
					map.put("urgRskPrc"	, etcInfo.get("usmRskPrc").toString());

					map.put("urgGrpKey"	, "0");
					map.put("urgRskChk"	, String.valueOf(row.getCell(3)));
					map.put("urgMesDes"	, String.valueOf(row.getCell(5)));
					map.put("urgMesYn"	, Constants.USE_N);
					map.put("urgLimDes"	, String.valueOf(row.getCell(6)));
					map.put("urgRstDes"	, String.valueOf(row.getCell(7)));
					map.put("urgAssVal"	, Constants.MNG_ASS_VAL);

					getRskPntLvl(map);
					fmRiskmDAO.fm_riskm003_rskm_insert(map);
				}
			}
		} catch (Exception e){
			return Constants.RETURN_FAIL;
		}
		return Constants.RETURN_SUCCESS;
	}
	*/

	@Override
	public void fmRiskm003_mngSave(Map<String, String> map) {
		fmRiskmDAO.fmRiskm003_mngSave(map);
	}

	@Override
	public void fmRiskm003_rskReq(Map<String, String> map) {
		if("S".equals(map.get("ursStaCod")) || "X".equals(map.get("ursStaCod"))){
			map.put("newUrsStaCod", "P");
		}else if("C".equals(map.get("ursStaCod")) || "R".equals(map.get("ursStaCod"))){
			map.put("newUrsStaCod", "V");
		}

		fmRiskmDAO.fmRiskm003_rskReq(map);
	}

	/*
	 * 2017-04-05, 위험관리 임시저장
	 */
	@Override
	public void fmRiskm003_rskSave(Map<String, String> map) {
		fmRiskmDAO.fmRiskm003_rskReq(map);
	}

	@Override
	public void fmRiskm003_rskAppr(Map<String, String> map) {
		if("P".equals(map.get("ursStaCod"))){
			map.put("newUrsStaCod", "C");
		}else if("V".equals(map.get("ursStaCod"))){
			map.put("newUrsStaCod", "A");
		}

		// 2017-05-31, 수정사항이 있을경우 저장됨
		fmRiskmDAO.fmRiskm003_rskReq(map);

		fmRiskmDAO.fmRiskm003_rskAppr(map);
	}

	@Override
	public void fmRiskm003_rskRej(Map<String, String> map) {
		if("P".equals(map.get("ursStaCod"))){
			map.put("newUrsStaCod", "X");
		}else if("V".equals(map.get("ursStaCod"))){
			map.put("newUrsStaCod", "R");
		}


		// 2017-05-31, 수정사항이 있을경우 저장됨
		fmRiskmDAO.fmRiskm003_rskReq(map);

		fmRiskmDAO.fmRiskm003_rskRej(map);
	}

	@Override
	public int fm_riskm003_update_ursStaCodS(Map<String, String> map) {
		// 2. 마스터(UWO_RSK_SRL) 일괄적으로 Q -> S
		return fmRiskmDAO.fm_riskm003_update_ursStaCodS(map);
	}

	@Override
	public List<?> fm_riskm004_list(Map<String, Object> map) {
		return fmRiskmDAO.fm_riskm004_list(map);
	}

	@Override
	public EgovMap fm_riskm004_info(String usoCockey) {
		return fmRiskmDAO.fm_riskm004_info(usoCockey);
	}

	@Override
	public void fm_riskm004_insert(Map<String, Object> map) {
//		String cocCodSeq = commonUtilDAO.getCocCodSeq();
//		String usoCocCod = Constants.COC_COD_GBN + map.get("assCatCod") + "_" + cocCodSeq ;
//		map.put("usoCocCod", usoCocCod);
//		map.put("usoCocGbn", Constants.COC_GBN);
		fmRiskmDAO.fm_riskm004_insert(map);
	}

	@Override
	public void fm_riskm004_update(Map<String, Object> map) {
		fmRiskmDAO.fm_riskm004_update(map);
	}

	@Override
	public List<?> fm_riskm005_list(Map<String, Object> map) {
		return fmRiskmDAO.fm_riskm005_list(map);
	}

	@Override
	public EgovMap fm_riskm005_info(String usmSroKey) {
		return fmRiskmDAO.fm_riskm005_info(usmSroKey);
	}

	@Override
	public void fm_riskm005_insert(Map<String, Object> map) {

		String sroCodSeq = commonUtilDAO.getSroCodSeq();
		map.put("usmSroCod", Constants.SRO_COD_GBN  + sroCodSeq);
		fmRiskmDAO.fm_riskm005_insert(map);
	}

	@Override
	public void fm_riskm005_update(Map<String, Object> map) {
		fmRiskmDAO.fm_riskm005_update(map);
	}
	/**
	 * 위험값 위험도 계산
	 * @param map
	 */
	public void getRskPntLvl(Map<String, String> map) {
		log.debug("#################### getRskPntLvl Start");
		log.debug("#################### urgGrpKey [{}]", map.get("urgGrpKey"));
		log.debug("#################### urgCocKey [{}]", map.get("urgCocKey"));
		log.debug("#################### urgAssVal [{}]", map.get("urgAssVal"));

		String urgGrpKey = map.get("urgGrpKey");
		EgovMap pntMap = fmRiskmDAO.fm_riskm003_pnt(map.get("urgCocKey"));
		int urgAssVal = Integer.valueOf(map.get("urgAssVal"));

		Integer urgPnt = null;
		if("N".equals(map.get("urgRskChk"))) {
			urgPnt = 3;
		} else if("P".equals(map.get("urgRskChk"))) {
			urgPnt = 2;
		}
		// 관리체계
		int urgRskPnt = 0;
		String urgRskLvl = "";
		if(urgPnt != null) {
			if("0".equals(urgGrpKey)) {
				String usoCocPnt = pntMap.get("usoCocPnt").toString();
				urgRskPnt = urgAssVal + Integer.valueOf(usoCocPnt) + urgPnt;
			} else {
				String usoRskPnt = pntMap.get("usoRskPnt").toString();
				urgRskPnt = urgAssVal + (Integer.valueOf(usoRskPnt) * 2);
			}

			if(urgRskPnt >= 7) {
				urgRskLvl = Constants.LVL_H;
			} else if(urgRskPnt >= 5) {
				urgRskLvl = Constants.LVL_M;
			} else if(urgRskPnt >= 3) {
				urgRskLvl = Constants.LVL_L;
			}
		}

		// 결과승인
		String urgStaCod = "";
		if("Y".equals(map.get("urgRstYn"))) {
			urgStaCod = Constants.RSK_STA_R29;
		} else if("Y".equals(map.get("urgMesYn"))) {
			urgStaCod = Constants.RSK_STA_R19;
		} else if(StringUtils.isNotEmpty(map.get("urgRstDes"))) {
			urgStaCod = Constants.RSK_STA_R20;
		} else if(StringUtils.isNotEmpty(map.get("urgLimDes")) ||
				StringUtils.isNotEmpty(map.get("urgMesDes"))) {
			urgStaCod = Constants.RSK_STA_R10;
		} else {
			urgStaCod = Constants.RSK_STA_R01;
		}

		map.put("urgStaCod", urgStaCod);
		map.put("urgRskPnt", String.valueOf(urgRskPnt));
		map.put("urgRskLvl", urgRskLvl);

		log.debug("getRskPntLvl - urgStaCod [{}]", urgStaCod);
		log.debug("getRskPntLvl - urgRskPnt [{}]", urgRskPnt);
		log.debug("getRskPntLvl - urgRskLvl [{}]", urgRskLvl);
		log.debug("#################### getRskPntLvl End");
	}

	@Override
	public List<?> fm_riskm003_report(Map<String, String> map) {
		return fmRiskmDAO.fm_riskm003_report(map);
	}

	@Override
	public List<?> fm_riskm003_t_report(Map<String, String> map) {
		return fmRiskmDAO.fm_riskm003_t_report(map);
	}

	@Override
	public List<?> fm_riskm006_list(Map<String, String> map) {
		return fmRiskmDAO.fm_riskm006_list(map);
	}

	@Override
	public int fm_riskm006_cnt(Map<String, String> map) {
		return fmRiskmDAO.fm_riskm006_cnt(map);
	}

	@Override
	public List<?> fm_riskm006_rst_list(Map<String, String> map) {
		return fmRiskmDAO.fm_riskm006_rst_list(map);
	}


	/*
	 * 2016-09-28
	 * Host, IP 등으로 자산 확인
	 * 자산 존재유무, 업로드 등의 공통 엑셀 Index
	 */
	@Override
	public EgovMap fm_riskm006_excel_common(String assCat) {

		EgovMap map = new EgovMap();
		int xKey = 1;	// 항목코드 column index
		int xVal = 5;	// 점검항목 first column index
		int yFirst; 	// data first index
		int yDep, yCmp1, yCmp2, yCat;

		// 		6.전자정보			7/8.서버/네트워크		9.정보보호시스템		11. 소프트웨어		SW001.모바일 		SW012.홈페이지		12.단말기
		// 1	uar_dep_nam			uar_dep_nam			uar_dep_nam			uar_dep_nam													uar_dep_nam
		// 2	uar_val_cl1			uar_val_cl3			uar_val_cl2			uar_val_cl2			uar_dep_nam			uar_dep_nam
		// 3	uar_val_cl2			uar_eqp_nam			uar_eqp_nam			uar_eqp_nam			uar_dtl_exp			uar_val_cl1			uar_eqp_nam
		// 4	uar_ass_nam			uar_ass_nam								uar_ass_nam			uar_ass_nam			uar_ass_nam			uar_ass_nam
		// 5											uar_ass_nam

		// default
		yDep = 1;
		yCmp1 = 2;
		yCmp2 = 3;
		yCat = 4;
		yFirst = 6;

		switch (assCat) {
		case "6" :
			yCmp1 = 2;
			yCmp2 = 3;
			yFirst = 7;
			break;
		case "7" :
			yCmp1 = 2;
			yCmp2 = 3;
			yFirst = 7;
			break;
		case "8" :
			yCmp1 = 2;
			yCmp2 = 3;
			yFirst = 6;
			break;
		case "9" :
			yCmp1 = 2;
			yCmp2 = 3;
			yCat = 5;
			yFirst = 7;
			break;
		case "11" :
			yCmp1 = 2;
			yCmp2 = 3;
			yFirst = 7;
			break;
		case "SW001" :
		case "SW012" :
			yDep = 2;
			yCmp1 = 3;
			yCmp2 = 3;
			yFirst = 6;
			break;
		case "12" :
			yDep = 1;
			yCmp1 = 3;
			yCmp2 = 3;
			yFirst = 7;
			break;
		}

		map.put("yDep", yDep);
		map.put("yCmp1", yCmp1);
		map.put("yCmp2", yCmp2);
		map.put("yCat", yCat);
		map.put("yFirst", yFirst);
		map.put("xKey", xKey);
		map.put("xVal", xVal);

		return map;
	}

	/*
	 * 2016-09-22
	 * Host, IP 등으로 자산 존재 유무 확인
	 */
	/*
	@Override
	public List<?> fm_riskm006_excel_xlsx_asset_exists(HttpServletRequest req)	 {

		int xVal, yDep, yCmp1, yCmp2, yCat;
		String assCat;
		EgovMap codeMap = null;
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();

		try {
			assCat = req.getParameter("uarAssCat").toString();
			codeMap = fm_riskm006_excel_common(assCat);
			xVal = (int) codeMap.get("xVal");
			yDep = (int) codeMap.get("yDep");
			yCmp1 = (int) codeMap.get("yCmp1");
			yCmp2 = (int) codeMap.get("yCmp2");
			yCat = (int) codeMap.get("yCat");

			MultipartHttpServletRequest mreq = (MultipartHttpServletRequest) req;
			//HSSFWorkbook wb = new HSSFWorkbook(mreq.getFile("excelFile").getInputStream());
			InputStream file = commonUtilService.decryptFileDAC(mreq.getFile("excelFile"));
			XSSFWorkbook wb = new XSSFWorkbook(file);

			outerloop:
			for (int i = 0; i < wb.getNumberOfSheets() + 1; i++) {
				XSSFSheet sheet = wb.getSheetAt(i);

				// sheet name: 진단결과
				if (!wb.getSheetName(i).equals("진단결과")) {
					continue;
				}

				for (int j = xVal; j < sheet.getRow(0).getLastCellNum(); j++) {

					// 첫번째 행의 데이터가 존재하지 않을 경우
					if (sheet.getRow(0).getCell(j).toString().equals("")) {
						break outerloop;
					}

					Map<String, String> map = new HashMap<String, String>();
					map.put("depNam", sheet.getRow(yDep).getCell(j).getStringCellValue());
					map.put("cmpVal1", sheet.getRow(yCmp1).getCell(j).getStringCellValue().replaceAll("[\r\n]", ""));
					map.put("cmpVal2", sheet.getRow(yCmp2).getCell(j).getStringCellValue().replaceAll("[\r\n]", ""));
					map.put("assNam", sheet.getRow(yCat).getCell(j).getStringCellValue());
					map.put("assCat", assCat);


					// 자산 존재유무 확인
					int result = fmRiskmDAO.fm_riskm006_asset_exists(map);
					if (result == 0) {
						resultList.add(map);
					}
				}
			}

		} catch (Exception e) {
  			throw new RuntimeException(e);
		}

		return resultList;
	}
    */

	/*
	 * 2016-09-28
	 * 등록된 취약점 결과 존재 유무 확인
	 */
	@Override
	public String fm_riskm006_excel_exists(HttpServletRequest req)	 {

		int xVal = 4;	// 점검항목 first column index
		int yKey = 6;	// asset code
		int rskExists = 0;

		try {
			MultipartHttpServletRequest mreq = (MultipartHttpServletRequest) req;
			InputStream file = commonUtilService.decryptFileDAC(mreq.getFile("excelFile"));
			Workbook wb = WorkbookFactory.create(file);

			for (int i = 0; i < wb.getNumberOfSheets(); i++) {
				Sheet sheet = wb.getSheetAt(i);

				if (sheet.getRow(0) == null) {
					continue;
				}

				outerloop:
				for (int j = xVal; j <= sheet.getRow(0).getLastCellNum(); j++) {

					if (sheet.getRow(0).getCell(j) == null) {
						break outerloop;
					}

					Map<String, String> map = new HashMap<String, String>();
					map.put("manCyl", (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
					map.put("assCod", sheet.getRow(yKey).getCell(j).getStringCellValue());

					rskExists = fmRiskmDAO.fm_riskm006_rskm_exists(map);
					if (rskExists > 0) {
						break outerloop;
					}
				}
			}

		} catch (Exception e) {
  			throw new RuntimeException(e);
		}

		if (rskExists > 0) {
			return Constants.RETURN_FAIL;
		} else {
			return Constants.RETURN_SUCCESS;
		}
	}

	/*
	 * 2016-09-28
	 * Host, IP 등으로 자산 확인
	 * INSERT 취약점 결과
	 * 2017-03-21, asset code 로 자산확인으로 원복
	 */
	@Override
	public String fm_riskm006_excel_insert(HttpServletRequest req) {

		int xKey = 0;	// 항목코드 column index
		int xVal = 4;	// 점검항목 first column index
		int yKey = 6;	// asset code
		int yFirst = 7;	// data first index
		String assCod;

		try {
			List<Map<String, String>> rstList = new ArrayList<Map<String, String>>();
			List<Map<String, String>> srlList = new ArrayList<Map<String, String>>();
			List<Map<String, String>> assetCodeList = new ArrayList<Map<String, String>>();

			HashSet<String> grpSet = new HashSet<String>();
			HashSet<String> assetCodeSet = new HashSet<String>();


			MultipartHttpServletRequest mreq = (MultipartHttpServletRequest) req;
			InputStream file = commonUtilService.decryptFileDAC(mreq.getFile("excelFile"));
			Workbook wb = WorkbookFactory.create(file);


			for (int i = 0; i < wb.getNumberOfSheets(); i++) {
				Sheet sheet = wb.getSheetAt(i);

				if (sheet.getRow(0) == null) {
					continue;
				}

				outerloop:
				for (int j = xVal; j <= sheet.getRow(0).getLastCellNum(); j++) {

					// 첫번째 행의 데이터가 존재하지 않을 경우
					if (sheet.getRow(0).getCell(j) == null) {
						break outerloop;
					}

					assCod = sheet.getRow(yKey).getCell(j).getStringCellValue();

					// aseet code
					assetCodeSet.add(assCod);

					// 취약점 그룹
					Map<String, String> gMap = new HashMap<String, String>();
					gMap.put("uarBcyCod", (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
					gMap.put("uarAssCod", assCod);
					gMap.put("uarSmpYn", "Y");
					String grpCod = fmRiskmDAO.selectGrpCodOfAssetCode(gMap);
					grpSet.add(grpCod);

					// 취약점 결과
					innerloop:
					for (int k = yFirst; k < sheet.getLastRowNum(); k++) {

						// 항목코드가 존재하지 않을 경우
						if (sheet.getRow(k).getCell(xKey).toString().equals("") || StringUtil.isEmpty(assCod)) {
							break innerloop;
						}

						Map<String, String> mapRst = new HashMap<String, String>();
						mapRst.put("manCylKey", (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
						mapRst.put("urrRgtId", (String)req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
						mapRst.put("urrRskTyp", "A");
						mapRst.put("vlbCod", sheet.getRow(k).getCell(xKey).getStringCellValue());
						mapRst.put("assCod", assCod);

						String rst;
						String rstData = sheet.getRow(k).getCell(j).getStringCellValue().trim();
						rstData = rstData.toUpperCase();
						switch (rstData) {
							case "양호":
							case "Y":
								rst = "Y";
								break;
							case "취약":
							case "N":
								rst = "N";
								break;
							default:
								rst = "NA";
								break;
						}
						mapRst.put("rst", rst);
						rstList.add(mapRst);

					}
				}
			}

			if (rstList.size() > 0) {
				fmRiskmDAO.fm_riskm006_rskm_insert(rstList);

				// update uwo_ass_mtr smp_yn = Y
				// 취약점 진단 자산의 경우 샘플링 대상을 Y로 변경
				for (String item: assetCodeSet) {
					Map<String, String> assetMap = new HashMap<String, String>();
					assetMap.put("uarAssCod", item);
					assetMap.put("uarUptId", (String)req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
					assetCodeList.add(assetMap);
				}
				fmRiskmDAO.fm_riskm006_rskm_asset_smp_update_y(assetCodeList);

				// update uwo_rsk_srl
				for (String item: grpSet) {
					Map<String, String> srlMap = new HashMap<String, String>();
					srlMap.put("manCylKey", (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
					srlMap.put("uarUptId", (String)req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
					srlMap.put("urrRskTyp", "A");
					srlMap.put("uagGrpCod", item);
					srlList.add(srlMap);
				}
				fmRiskmDAO.fm_riskm006_rskm_srl_insert(srlList);
			}

		} catch (Exception e){
			//return Constants.RETURN_FAIL;
  			throw new RuntimeException(e);
		}
		return Constants.RETURN_SUCCESS;

	}



	/*
	 * 2016-09-22
	 * ass_key 로 체크하는 기존 로직
	 * 사용하지 않음
	@Override
	public String fm_riskm006_excel_insert(HttpServletRequest req) {

		try {
			MultipartHttpServletRequest mreq = (MultipartHttpServletRequest) req;
			//HSSFWorkbook wb = new HSSFWorkbook(mreq.getFile("excelFile").getInputStream());
			InputStream file = commonUtilService.decryptFileDAC(mreq.getFile("excelFile"));
			HSSFWorkbook wb = new HSSFWorkbook(file);

			int sheetCnt = wb.getNumberOfSheets();

			List<Map<String, String>> rstList = new ArrayList<Map<String, String>>();
			List<Map<String, String>> srlList = new ArrayList<Map<String, String>>();

			for(int i = 1; i < sheetCnt; i++){
				HSSFSheet sheet = wb.getSheetAt(i);
				if(sheet.getRow(i) != null){
					for(int cc=4; cc < sheet.getRow(i).getLastCellNum(); cc++){
						String rowAssCode = sheet.getRow(6).getCell(cc).getStringCellValue();
						for(int sc=7; sc < sheet.getLastRowNum(); sc++){
							Map<String, String> map = new HashMap<String, String>();
							HSSFRow row = sheet.getRow(sc);

							map.put("manCylKey", (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
							map.put("assCod", (String)rowAssCode);
							map.put("vlbCod", row.getCell(0).toString());
							if(row.getCell(cc) != null){
								if("".equals(row.getCell(cc).toString().trim())){
									map.put("rst", "NA");
								}else{
									map.put("rst", row.getCell(cc).toString());
								}
							}else{
								map.put("rst", "NA");
							}
							map.put("urrRgtId"	,(String)req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
							map.put("urrRskTyp", "A");
							rstList.add(map);
						}
						fmRiskmDAO.fm_riskm006_rskm_insert(rstList);
					}
					Map<String, String> map2 = new HashMap<String, String>();
					map2.put("uagGrpCod", sheet.getSheetName());
					map2.put("urrRskTyp", "A");
					map2.put("manCylKey", (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
					srlList.add(map2);
				}
				fmRiskmDAO.fm_riskm006_rskm_srl_insert(srlList);
			}

		} catch (Exception e){
			e.printStackTrace();
			return Constants.RETURN_FAIL;
		}
		return Constants.RETURN_SUCCESS;

	}
	*/

	/*
	 * 2016-09-22
	 * ass_key 로 체크하는 기존 로직
	 * 사용하지 않음
	@Override
	public String fm_riskm006_excel_exists(HttpServletRequest req)	 {

		int resultCnt = 0;

		try {
			MultipartHttpServletRequest mreq = (MultipartHttpServletRequest) req;
			//HSSFWorkbook wb = new HSSFWorkbook(mreq.getFile("excelFile").getInputStream());
			InputStream file = commonUtilService.decryptFileDAC(mreq.getFile("excelFile"));
			HSSFWorkbook wb = new HSSFWorkbook(file);

			int sheetCnt = wb.getNumberOfSheets();

			outerloop:
			for(int i = 1; i < sheetCnt; i++){
				HSSFSheet sheet = wb.getSheetAt(i);
				if(sheet.getRow(i) != null){
					for(int cc=4; cc < sheet.getRow(i).getLastCellNum(); cc++){
						String rowAssCode = sheet.getRow(6).getCell(cc).getStringCellValue();
						for(int sc=7; sc < sheet.getLastRowNum(); sc++){
							Map<String, String> map = new HashMap<String, String>();
							HSSFRow row = sheet.getRow(sc);

							map.put("manCylKey", (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
							map.put("assCod", (String)rowAssCode);
							map.put("vlbCod", row.getCell(0).toString());

							resultCnt = fmRiskmDAO.fm_riskm006_rskm_exists(map);
							if (resultCnt > 0) {
								break outerloop;
							}
						}
					}
				}

			}

		} catch (Exception e){
			e.printStackTrace();
			return Constants.RETURN_FAIL;
		}

		if (resultCnt > 0) {
			return Constants.RETURN_FAIL;
		} else {
			return Constants.RETURN_SUCCESS;
		}
	}
	*/

	@Override
	public String fm_riskm006_etcExcel_insert(HttpServletRequest req) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> fm_riskm007_list(Map<String, String> map) {
		return fmRiskmDAO.fm_riskm007_list(map);
	}

	@Override
	public EgovMap fm_riskm007_info(String urvRskKey) {
		return fmRiskmDAO.fm_riskm007_info(urvRskKey);
	}

	@Override
	public void fm_riskm007_insert(Map<String, Object> map) {
		fmRiskmDAO.fm_riskm007_insert(map);
	}

	@Override
	public void fm_riskm007_update(Map<String, Object> map) {
		fmRiskmDAO.fm_riskm007_update(map);
	}

	@Override
	public List<?> getCocList(Map<String, String> map) {
		return fmRiskmDAO.getCocList(map);
	}

	@Override
	public List<?> getUsrList(Map<String, String> map) {
		return fmRiskmDAO.getUsrList(map);
	}

	@Override
	public List<?> fm_riskm008_list(Map<String, String> map) {
		return fmRiskmDAO.fm_riskm008_list(map);
	}

	@Override
	public List<?> fm_riskm008_dep_list(Map<String, String> map) {
		return fmRiskmDAO.fm_riskm008_dep_list(map);
	}

	@Override
	public void fmRiskm008_addRskDepList(List<Map<String, String>> list) {
		for(Map<String, String> map : list){
			fmRiskmDAO.fmRiskm008_addRskDepList(map);
		}
	}

	@Override
	public void fmRiskm008_delRskDepList(List<Map<String, String>> list) {
		for(Map<String, String> map : list){
			fmRiskmDAO.fmRiskm008_delRskDepList(map);
		}
	}

	@Override
	public void fm_riskm008_update_rskDepMng(Map<String, String> map) {
		fmRiskmDAO.fm_riskm008_update_rskDepMng(map);
	}

	@Override
	public List<?> fm_riskm009_list(Map<String, String> map) {
		return fmRiskmDAO.fm_riskm009_list(map);
	}

	@Override
	public List<?> fm_riskm009_rst_list(Map<String, String> map) {
		return fmRiskmDAO.fm_riskm009_rst_list(map);
	}

	@Override
	public String fm_riskm009_excel_insert(HttpServletRequest req) {
		try {
			MultipartHttpServletRequest mreq = (MultipartHttpServletRequest) req;
			InputStream file = commonUtilService.decryptFileDAC(mreq.getFile("excelFile"));
			Workbook wb = WorkbookFactory.create(file);

			int sheetCnt = wb.getNumberOfSheets();

			for(int i = 1; i < sheetCnt; i++){
				Sheet sheet = wb.getSheetAt(i);
				if(sheet.getRow(2) != null){
					for(int cc=5; cc < sheet.getRow(2).getLastCellNum(); cc++){
						String rowAssCode = sheet.getRow(2).getCell(cc).getStringCellValue();
						for(int sc=3; sc <= sheet.getLastRowNum(); sc++){
							Map<String, String> map = new HashMap<String, String>();
							Row row = sheet.getRow(sc);

							map.put("manCylKey", (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
							map.put("assCod", (String)rowAssCode);
							map.put("vlbCod", row.getCell(0).toString());
							if(row.getCell(cc) != null){
								if("".equals(row.getCell(cc).toString().trim())){
									map.put("rst", "NA");
								}else{
									map.put("rst", row.getCell(cc).toString());
								}
							}else{
								map.put("rst", "NA");
							}
							map.put("urrRgtId"	,(String)req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
							map.put("urrRskTyp", "D");
							fmRiskmDAO.fm_riskm006_rskm_insert(map);
						}
					}
					Map<String, String> srlMap = new HashMap<String, String>();
					srlMap.put("uagSvrCod", sheet.getSheetName());
					srlMap.put("urrRskTyp", "D");
					srlMap.put("manCylKey", (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
					fmRiskmDAO.fm_riskm009_rskm_srl_insert(srlMap);
				}
			}

			fmRiskmDAO.fm_riskm006_rskm_finallyInsert();

		} catch (Exception e){
			e.printStackTrace();
			return Constants.RETURN_FAIL;
		}
		return Constants.RETURN_SUCCESS;

	}

	@Override
	public List<?> fmRiskm010_list(Map<String, String> map) {
		return fmRiskmDAO.fmRiskm010_list(map);
	}

	@Override
	public List<?> fm_riskm010_rskList(Map<String, String> map) {
		return fmRiskmDAO.fm_riskm010_rskList(map);
	}

	@Override
	public EgovMap fm_riskm010_rskInfo(Map<String, String> map) {
		return fmRiskmDAO.fm_riskm010_rskInfo(map);
	}

	@Override
	public int fm_riskm010_update_urrStaCodS(Map<String, String> map) {
		// 1. 상세(UWO_RSK_RST) 일괄적으로 Q -> S
		fmRiskmDAO.fm_riskm010_update_urrStaCodS(map);
		// 2. 마스터(UWO_RSK_SRL) 일괄적으로 Q -> S
		fmRiskmDAO.fm_riskm010_update_ursStaCodS(map);

		return 1;
	}

	@Override
	public List<?> fm_riskm010_urrDepList(Map<String, String> map) {
		return fmRiskmDAO.fm_riskm010_urrDepList(map);
	}

	@Override
	public String fm_riskm010_urrRskKey(Map<String, String> map) {
		return fmRiskmDAO.fm_riskm010_urrRskKey(map);
	}

	@Override
	public EgovMap fm_riskm010_urrDetail(Map<String, String> map) {
		return fmRiskmDAO.fm_riskm010_urrDetail(map);
	}

	@Override
	public void fmRiskm010_mngSave(Map<String, String> map) {
		fmRiskmDAO.fmRiskm010_mngSave(map);

		// 2016-03-16, 담당자 지정 추가
		String[] arrRskKey = map.get("urrRskKey").split(",");
		String[] arrRstUsr = map.get("urrRstUsr").split(",");
		for (int i = 0; i < arrRskKey.length; i++) {
			Map<String, String> map2 = new HashMap<String, String>();
			map2.put("urrRskKey", arrRskKey[i]);
			map2.put("urrRstUsr", arrRstUsr[i]);
			map2.put("uptId", map.get("uptId"));
			fmRiskmDAO.fmRiskm010_mngSaveUrr(map2);

		}
	}

	@Override
	public void fmRiskm010_rskReq(Map<String, String> map) {
		if("S".equals(map.get("urrStaCod")) || "X".equals(map.get("urrStaCod"))){
			map.put("newUrrStaCod", "P");
		}else if("C".equals(map.get("urrStaCod")) || "R".equals(map.get("urrStaCod"))){
			map.put("newUrrStaCod", "V");
		}

		fmRiskmDAO.fmRiskm010_rskReq(map);
		fmRiskmDAO.fmRiskm010_updateUrsSta(map);
	}

	/*
	 * 2017-03-16, 위험관리 임시저장
	 */
	@Override
	public void fmRiskm010_rskSave(Map<String, String> map) {
		fmRiskmDAO.fmRiskm010_rskReq(map);
	}


	@Override
	public void fmRiskm010_rskAppr(Map<String, String> map) {
		if("P".equals(map.get("urrStaCod"))){
			map.put("newUrrStaCod", "C");
		}else if("V".equals(map.get("urrStaCod"))){
			map.put("newUrrStaCod", "A");
		}

		fmRiskmDAO.fmRiskm010_rskAppr(map);

		// 2017-05-31, 수정사항이 있을경우 저장됨
		fmRiskmDAO.fmRiskm010_rskReq(map);

		fmRiskmDAO.fmRiskm010_updateUrsSta(map);
	}

	@Override
	public void fmRiskm010_rskRej(Map<String, String> map) {
		if("P".equals(map.get("urrStaCod"))){
			map.put("newUrrStaCod", "X");
		}else if("V".equals(map.get("urrStaCod"))){
			map.put("newUrrStaCod", "R");
		}

		// 2017-05-31, 수정사항이 있을경우 저장됨
		fmRiskmDAO.fmRiskm010_rskReq(map);

		fmRiskmDAO.fmRiskm010_rskRej(map);
	}

	@Override
	public List<?> getRst003MainList(Map<String, String> map) {
		return fmRiskmDAO.getRst003MainList(map);
	}

	@Override
	public List<?> getRst010MainList(Map<String, String> map) {
		return fmRiskmDAO.getRst010MainList(map);
	}
}
