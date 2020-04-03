package com.uwo.isms.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.uwo.isms.common.CommonConfig;
import com.uwo.isms.common.Constants;
import com.uwo.isms.common.UwoStringUtils;
import com.uwo.isms.dao.CommonUtilDAO;
import com.uwo.isms.dao.FMAssetDAO;
import com.uwo.isms.domain.RiskVO;
import com.uwo.isms.domain.SearchVO;
import com.uwo.isms.service.CommonUtilService;
import com.uwo.isms.service.FMAssetService;

import egovframework.rte.psl.dataaccess.util.EgovMap;


@Service("fmAssetService")
public class FMAssetServiceImpl implements FMAssetService {

	Logger log = LogManager.getLogger(FMAssetServiceImpl.class);

	@Resource(name="fmAssetDAO")
	private FMAssetDAO fmAssetDAO;

	@Resource(name="commonUtilDAO")
	private CommonUtilDAO commonUtilDAO;

	@Autowired
	CommonUtilService commonUtilService;

	@Override
	public List<?> fm_asset001_list(SearchVO searchVO) {
		return fmAssetDAO.fm_asset001_list(searchVO);
	}

	@Override
	public int fm_asset001_cnt(SearchVO searchVO) {
		return fmAssetDAO.fm_asset001_cnt(searchVO);
	}

	@Override
	public EgovMap fm_asset001_assetCodeInfo(String code) {
		return fmAssetDAO.fm_asset001_assetCodeInfo(code);
	}

	@Override
	public void fm_asset001_assetCode_insert(Map<String, Object> map) {
		String assCodSeq = commonUtilDAO.getAssCodSeq();

		String depCod = (String) map.get("uarDepCod");
		String svrCod = (String) map.get("uarSvrCod");
		String rskGrp = (String) map.get("uarAssGbn");
		String rskNam = (String) map.get("uarAssGbn");
		String catCod = (String) map.get("assCatCod");
		String valCl2 = (String) map.get("uarValCl2");

		valCl2 = valCl2.toUpperCase();

		String uarDepNam = fmAssetDAO.fmasset001_asset_grpcode01(depCod);
		String uarSubNam = fmAssetDAO.fmasset001_asset_grpcode02(svrCod);

		String uarRskGrp = "";
		String uarRskNam = "";

		/*
		 * 2016-06-28, 예외처리 주석처리
		if(catCod.equals("SE")){

			uarRskGrp = fmAssetDAO.fmasset001_asset_grpcode05(valCl2);
			uarRskNam = fmAssetDAO.fmasset001_asset_grpcode06(valCl2);

		} else if (catCod.equals("TE")) {

			uarRskGrp = fmAssetDAO.fmasset001_asset_grpcode05(valCl2);
			uarRskNam = fmAssetDAO.fmasset001_asset_grpcode06(valCl2);

		} else {

			uarRskGrp = fmAssetDAO.fmasset001_asset_grpcode03(rskGrp);
			uarRskNam = fmAssetDAO.fmasset001_asset_grpcode04(rskNam);

		}
		*/
		uarRskGrp = fmAssetDAO.fmasset001_asset_grpcode03(rskGrp);
		uarRskNam = fmAssetDAO.fmasset001_asset_grpcode04(rskNam);

		System.out.println("aaaaaa = " + uarDepNam);
		System.out.println("bbbbbb = " + uarSubNam);
		System.out.println("cccccc = " + uarRskGrp);
		System.out.println("dddddd = " + uarRskNam);

		//String uarAssCod = Constants.ASS_COD_GBN + map.get("assCatCod") + "_" + map.get("uarSvrCod") + "_" + assCodSeq;
		String uarAssCod = new StringBuffer().append("I-").append(map.get("uarSvrCod")).append(map.get("uarAssGbn")).append(assCodSeq).toString();
		String uarGrpCod = map.get("uarDepCod") + "_" + map.get("uarSvrCod") + "_" + uarRskGrp + "_" + map.get("uarAssLvl");
		String uarGrpNam = uarDepNam + "_" + uarSubNam + "_" + uarRskNam + "_" + map.get("uarAssLvl");

		map.put("uarGrpCod", uarGrpCod);
		map.put("uarGrpNam", uarGrpNam);
		map.put("uarAssCod", uarAssCod);
		map.put("uarRskGrp", uarRskGrp);
		map.put("uarRskNam", uarRskNam);

		fmAssetDAO.fm_asset001_assetCode_insert(map);

		if(fmAssetDAO.fm_asset001_groupCdCount(uarGrpCod) == 0){

			String grpCodSeq = commonUtilDAO.getGrpCodSeq();

			map.put("uagGrpKey", grpCodSeq);

			fmAssetDAO.fm_asset001_group_insert(map);
		}


	}

	@Override
	public void fm_asset001_assetCode_update(Map<String, Object> map) {
		//fmAssetDAO.fm_asset001_assetCodeLog_insert(map);
		String catCod = (String) map.get("assCatCod");
		String depCod = (String) map.get("uarDepCod");
		String subCod = (String) map.get("uarSvrCod");
		String rskGrp = (String) map.get("uarAssGbn");
		String rskNam = (String) map.get("uarAssGbn");
		String valCl2 = (String) map.get("uarValCl2");
		String aaaaaa = (String) map.get("uarAssLvl");

		valCl2 = valCl2.toUpperCase();

		System.out.println("catCod = " + catCod);
		System.out.println("depCod = " + depCod);
		System.out.println("subCod = " + subCod);
		System.out.println("rskGrp = " + rskGrp);
		System.out.println("rskNam = " + rskNam);
		System.out.println("valCl2 = " + valCl2);
		System.out.println("aaaaaa = " + aaaaaa);

		String uarDepNam = fmAssetDAO.fmasset001_asset_grpcode01(depCod);
		String uarSubNam = fmAssetDAO.fmasset001_asset_grpcode02(subCod);

		String uarRskGrp = "";
		String uarRskNam = "";

		/*
		 * 2016-06-28, 예외처리 주석처리
		if(catCod.equals("SE")){

			uarRskGrp = fmAssetDAO.fmasset001_asset_grpcode05(valCl2);
			uarRskNam = fmAssetDAO.fmasset001_asset_grpcode06(valCl2);

		} else if (catCod.equals("TE")) {

			uarRskGrp = fmAssetDAO.fmasset001_asset_grpcode05(valCl2);
			uarRskNam = fmAssetDAO.fmasset001_asset_grpcode06(valCl2);

		} else {

			uarRskGrp = fmAssetDAO.fmasset001_asset_grpcode03(rskGrp);
			uarRskNam = fmAssetDAO.fmasset001_asset_grpcode04(rskNam);

		}
		*/
		uarRskGrp = fmAssetDAO.fmasset001_asset_grpcode03(rskGrp);
		uarRskNam = fmAssetDAO.fmasset001_asset_grpcode04(rskNam);

		System.out.println(uarDepNam);
		System.out.println(uarSubNam);
		System.out.println(uarRskGrp);
		System.out.println(uarRskNam);
		System.out.println(aaaaaa);

		/*int sum = uarAppCon +  uarAppItg +  uarAppAvt;
		String grade = "";
		if(sum >=3 && sum <=4 ){
			grade = "L(1)";
		} else if(sum >=5 && sum <=7 ){
			grade = "M(2)";
		} else if(sum >=8 && sum <=9 ){
			grade = "H(3)";
		} */

		/*if(UwoStringUtils.cellToStr(row.getCell(3)).equals("SE")){
			uarRskGrp = UwoStringUtils.cellToStr(row.getCell(38));
			uarRskNam = UwoStringUtils.cellToStr(row.getCell(38));

			map.put("uarRskGrp", fmAssetDAO.fmasset001_rsk_cod_ext(uarRskGrp));
			map.put("uarRskNam", fmAssetDAO.fmasset001_rsk_nam_ext(uarRskNam));

		}else if (UwoStringUtils.cellToStr(row.getCell(3)).equals("TE")){
			uarRskGrp = UwoStringUtils.cellToStr(row.getCell(38));
			uarRskNam = UwoStringUtils.cellToStr(row.getCell(38));

			map.put("uarRskGrp", fmAssetDAO.fmasset001_rsk_cod_ext(uarRskGrp));
			map.put("uarRskNam", fmAssetDAO.fmasset001_rsk_nam_ext(uarRskNam));

		}else {
			uarRskGrp = UwoStringUtils.cellToStr(row.getCell(19));
			uarRskNam = UwoStringUtils.cellToStr(row.getCell(19));

			map.put("uarRskGrp", fmAssetDAO.fmasset001_rsk_cod(uarRskGrp));
			map.put("uarRskNam", fmAssetDAO.fmasset001_rsk_nam(uarRskNam));
		}*/

		String uarGrpCod = map.get("uarDepCod") + "_" + map.get("uarSvrCod") + "_" + uarRskGrp + "_" + map.get("uarAssLvl");
		String uarGrpNam = uarDepNam + "_" + uarSubNam + "_" + uarRskNam + "_" + map.get("uarAssLvl");

		map.put("uarGrpCod", uarGrpCod);
		map.put("uarGrpNam", uarGrpNam);
		map.put("uarRskGrp", uarRskGrp);
		map.put("uarRskNam", uarRskNam);

		fmAssetDAO.fm_asset001_assetCode_update(map);

		if(fmAssetDAO.fm_asset001_groupCdCount(uarGrpCod) == 0){

			String grpCodSeq = commonUtilDAO.getGrpCodSeq();

			map.put("uagGrpKey", grpCodSeq);

			fmAssetDAO.fm_asset001_group_insert(map);
		}
	}

	@Override
	public void fm_asset001_assetCodeLog_insert(Map<String, Object> map) {
		fmAssetDAO.fm_asset001_assetCodeLog_insert(map);
	}

	@Override
	public List<?> fm_asset002_list(SearchVO searchVO) {
		return fmAssetDAO.fm_asset002_list(searchVO);
	}

	@Override
	public RiskVO fm_asset002_assetGroupInfo(String groupKey) {
		return fmAssetDAO.fm_asset002_assetGroupInfo(groupKey);
	}

	@Override
	public void fm_asset002_assetGroup_insert(RiskVO riskVO) {
		String grpCodSeq = commonUtilDAO.getGrpCodSeq();
		String uagGrpCod = Constants.GRP_COD_GBN + riskVO.getUag_cat_cod() + "_" +
				riskVO.getUag_svc_cod() + "_" + riskVO.getUag_dep_cod() + "_" + grpCodSeq;
		riskVO.setUag_grp_cod(uagGrpCod);
		fmAssetDAO.fm_asset002_assetGroup_insert(riskVO);
	}

	@Override
	public void fm_asset002_assetGroup_update(RiskVO riskVO) {
		fmAssetDAO.fm_asset002_assetGroup_update(riskVO);
	}

	@Override
	public List<?> fm_asset002_assetlistBelongG(SearchVO searchVO) {
		return fmAssetDAO.fm_asset002_assetlistBelongG(searchVO);
	}

	@Override
	public List<?> fm_asset002_assetlistNotBelongG(SearchVO searchVO) {
		return fmAssetDAO.fm_asset002_assetlistNotBelongG(searchVO);
	}

	@Override
	public void fm_asset002_groupAsset_update(SearchVO searchVO) {
		fmAssetDAO.fm_asset002_groupAsset_update(searchVO);
	}

	@Override
	public void fm_asset002_riskGroup_insert(RiskVO riskVO) {
		fmAssetDAO.fm_asset002_riskGroup_insert(riskVO);
	}

	@Override
	public void fm_asset002_riskGroup_update(RiskVO riskVO) {
		fmAssetDAO.fm_asset002_riskGroup_update(riskVO);
	}

	@Override
	public List<?> fm_asset008_list(Map<String, String> map) {
		return fmAssetDAO.fm_asset008_list(map);
	}

	@Override
	public void fm_asset008_add(Map<String, String> map) {
		fmAssetDAO.fm_asset008_insert(map);
	}

	@Override
	public void fm_asset008_mod(Map<String, String> map) {
		fmAssetDAO.fm_asset008_update(map);
	}

	@Override
	public EgovMap fm_asset008_info(Map<String, String> map) {
		return fmAssetDAO.fm_asset008_info(map);
	}

	@Override
	public List<?> getHistory(SearchVO searchVO) {
		return fmAssetDAO.getHistory(searchVO);
	}

	@Override
	public int getHistory_count(SearchVO searchVO) {
		return fmAssetDAO.getHistory_count(searchVO);
	}

	@Override
	public List<?> fmAsset001_excelUpload(HttpServletRequest req) throws Exception {
		MultipartHttpServletRequest mreq = (MultipartHttpServletRequest) req;

		InputStream file = commonUtilService.decryptFileDAC(mreq.getFile("excelFile"));
		Workbook wb = WorkbookFactory.create(file);

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for(int i=0; i<wb.getNumberOfSheets()-2; i++) { //-2하는 이유 : 현재 업로드 양식에서 끝의 2개 시트는 코드정보가 담긴 시트이기 때문에
			Sheet sheet = wb.getSheetAt(i);
			log.info("sheet : " + sheet.getSheetName());
			for(int j=3; j<=sheet.getLastRowNum(); j++){ //현재 양식 기준 3번째 줄 부터 데이터가 시작됨

				Row row = sheet.getRow(j);
				if(row != null) {

					if (UwoStringUtils.cellToStr(row.getCell(3)) == "") {
						continue;
					}

					Map<String, Object> map = new HashMap<String, Object>();
					EgovMap emap = new EgovMap();

					if (UwoStringUtils.cellToStr(row.getCell(3)).equals("TE")){
						String code = UwoStringUtils.cellToStr(row.getCell(38)); // OS
						emap = fmAssetDAO.fmasset001_rsk_os_info(code);
					}else {
						String code = UwoStringUtils.cellToStr(row.getCell(19)); // uarAssGbn
						emap = fmAssetDAO.fmasset001_rsk_code_info(code);
					}

					String uarAppTot = "";
					String uarAssLvl = "";
					String uarGrpCod = "";
					String uarGrpNam = "";
					String uarRskGrp = "";
					String uarRskNam = "";

					if (row.getCell(26) != null && row.getCell(27) != null && row.getCell(28) != null) {
						// 기밀성(1/2/3) + 무결성(1/2/3) + 가용성(1/2/3)
						int sum = Integer.valueOf(UwoStringUtils.cellToStr(row.getCell(26)))
								+ Integer.valueOf(UwoStringUtils.cellToStr(row.getCell(27)))
								+ Integer.valueOf(UwoStringUtils.cellToStr(row.getCell(28)));

						uarAppTot = String.valueOf(sum);

						if (sum >= 8) {
							uarAssLvl = "H(3)";
						}
						else if(sum >= 5) {
							uarAssLvl = "M(2)";
						}
						else {
							uarAssLvl = "L(1)";
						}
					}

					if (emap != null) {
						uarRskGrp = (String) emap.get("uarRskGrp");
						uarRskNam = (String) emap.get("uarRskNam");

						// 그룹코드 = 부서코드 + 서비스코드 + 자산유형 + 보안영향도
						uarGrpCod = new StringBuffer()
							.append(UwoStringUtils.cellToStr(row.getCell(7)))
							.append("_")
							.append(UwoStringUtils.cellToStr(row.getCell(4)))
							.append("_")
							.append(uarRskGrp)
							.append("_")
							.append(uarAssLvl).toString();

						// 그룹명 = 부서명 + 서비스명 + 자산유형 + 보안영향도
						uarGrpNam = new StringBuffer()
							.append(UwoStringUtils.cellToStr(row.getCell(8)))
							.append("_")
							.append(UwoStringUtils.cellToStr(row.getCell(5)))
							.append("_")
							.append(uarRskNam)
							.append("_")
							.append(uarAssLvl).toString();
					}

					map.put("uarAppTot" , uarAppTot);
					map.put("uarAssLvl"	, uarAssLvl);

					map.put("uarGrpCod" , uarGrpCod);
					map.put("uarGrpNam" , uarGrpNam);
					map.put("uarRskGrp" , uarRskGrp);
					map.put("uarRskNam" , uarRskNam);

					map.put("uarAssKey" , UwoStringUtils.cellToStr(row.getCell(0)));
					//map.put("uarGrpCod" , UwoStringUtils.cellToStr(row.getCell(1)));
					//map.put("uarGrpNam" , UwoStringUtils.cellToStr(row.getCell(2)));
					map.put("uarCatCod" , UwoStringUtils.cellToStr(row.getCell(3)));
					map.put("uarSubCod"	, UwoStringUtils.cellToStr(row.getCell(4)));
					map.put("uarSubNam"	, UwoStringUtils.cellToStr(row.getCell(5)));
					map.put("uarSvrEtc"	, UwoStringUtils.cellToStr(row.getCell(6)));
					map.put("uarDepCod"	, UwoStringUtils.cellToStr(row.getCell(7)));
					map.put("uarDepNam"	, UwoStringUtils.cellToStr(row.getCell(8)));
					map.put("uarOwnId"	, UwoStringUtils.cellToStr(row.getCell(9)));
					map.put("uarOwnNam"	, UwoStringUtils.cellToStr(row.getCell(10)));
					map.put("uarAdmId"	, UwoStringUtils.cellToStr(row.getCell(11)));
					map.put("uarAdmNam"	, UwoStringUtils.cellToStr(row.getCell(12)));
					map.put("uarOprCod"	, UwoStringUtils.cellToStr(row.getCell(13)));
					map.put("uarOprNam"	, UwoStringUtils.cellToStr(row.getCell(14)));
					map.put("uarUseId"	, UwoStringUtils.cellToStr(row.getCell(15)));
					map.put("uarUseNam"	, UwoStringUtils.cellToStr(row.getCell(16)));
					map.put("uarPicId"	, UwoStringUtils.cellToStr(row.getCell(17)));
					map.put("uarPicNam"	, UwoStringUtils.cellToStr(row.getCell(18)));
					map.put("uarAssGbn"	, UwoStringUtils.cellToStr(row.getCell(19)));
					map.put("uarAssNam"	, UwoStringUtils.cellToStr(row.getCell(20)));
					map.put("uarEqpNam"	, UwoStringUtils.cellToStr(row.getCell(21)));
					map.put("uarDtlExp"	, UwoStringUtils.cellToStr(row.getCell(22)));
					map.put("uarAdmPos"	, UwoStringUtils.cellToStr(row.getCell(23)));
					map.put("uarAppCon"	, UwoStringUtils.cellToStr(row.getCell(26)));
					map.put("uarAppItg"	, UwoStringUtils.cellToStr(row.getCell(27)));
					map.put("uarAppAvt"	, UwoStringUtils.cellToStr(row.getCell(28)));
					//map.put("uarAppTot"	, UwoStringUtils.cellToStr(row.getCell(29)));
					//map.put("uarAssLvl"	, UwoStringUtils.cellToStr(row.getCell(30)));
					map.put("uarAudYn"	, UwoStringUtils.cellToStr(row.getCell(31)));
					map.put("uarSmpYn"	, UwoStringUtils.cellToStr(row.getCell(32)));
					map.put("uarInfYn"	, UwoStringUtils.cellToStr(row.getCell(33)));
					map.put("uarPrvYn"	, UwoStringUtils.cellToStr(row.getCell(34)));
					map.put("uarUseYn"	, UwoStringUtils.cellToStr(row.getCell(35)));
					map.put("uarIp"		, UwoStringUtils.cellToStr(row.getCell(36)));
					map.put("uarHost"	, UwoStringUtils.cellToStr(row.getCell(37)));
					map.put("uarOs"		, UwoStringUtils.cellToStr(row.getCell(38)));
					map.put("uarValCl0"	, UwoStringUtils.cellToStr(row.getCell(39)));
					map.put("uarValCl1"	, UwoStringUtils.cellToStr(row.getCell(40)));
					map.put("uarValCl2"	, UwoStringUtils.cellToStr(row.getCell(41)));
					map.put("uarValCl3"	, UwoStringUtils.cellToStr(row.getCell(42)));
					map.put("uarValCl4"	, UwoStringUtils.cellToStr(row.getCell(43)));
					map.put("uarValCl5"	, UwoStringUtils.cellToStr(row.getCell(44)));
					map.put("uarValCl6"	, UwoStringUtils.cellToStr(row.getCell(45)));
					map.put("uarValCl7"	, UwoStringUtils.cellToStr(row.getCell(46)));
					map.put("uarValCl8"	, UwoStringUtils.cellToStr(row.getCell(47)));
					map.put("uarValCl9"	, UwoStringUtils.cellToStr(row.getCell(48)));
					list.add(map);
				}
			}
		}
		return list;
	}

	@Override
	public EgovMap fmAsset001_excelSave(HttpServletRequest req) { //실제 자산이 INSERT되는 메소드. 여기서 자산그룹 체크가 이루어줘야 함

		String[] uarSubCod = req.getParameterValues("uarSubCod");

		//System.out.println(uarSubCod);

		String[] uarDepCod = req.getParameterValues("uarDepCod");
		String[] uarOprCod = req.getParameterValues("uarOprCod");
		String[] uarCatCod = req.getParameterValues("uarCatCod");
		String[] uarAppCon = req.getParameterValues("uarAppCon");
		String[] uarAppItg = req.getParameterValues("uarAppItg");
		String[] uarAppAvt = req.getParameterValues("uarAppAvt");

		int sucCnt = 0;
		for(int i=0; i<uarSubCod.length; i++) {
			Map<String, String> infoMap = new HashMap<String, String>();

			String assCodSeq = commonUtilDAO.getAssCodSeq();
			//String uarAssCod = Constants.ASS_COD_GBN + uarCatCod[i] + "_" + uarSubCod[i] + "_" + assCodSeq;
			String uarAssCod = new StringBuffer().append("I-").append(uarSubCod[i]).append(uarCatCod[i]).append(assCodSeq).toString();

			infoMap.put("uarBcyCod"	, (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
			infoMap.put("uarRgtId"	, (String)req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
			infoMap.put("uarUseYn"	, Constants.USE_Y);

			infoMap.put("uarAssKey" , UwoStringUtils.arrToStr(req.getParameterValues("uarAssKey"), i));
			infoMap.put("uarAssCod"	, uarAssCod);
			infoMap.put("uarSubCod"	, uarSubCod[i]);
			infoMap.put("uarSubNam"	, UwoStringUtils.arrToStr(req.getParameterValues("uarSubNam"), i));
			infoMap.put("uarSvrEtc"	, UwoStringUtils.arrToStr(req.getParameterValues("uarSvrEtc"), i));
			infoMap.put("uarDepCod"	, uarDepCod[i]);
			infoMap.put("uarDepNam"	, UwoStringUtils.arrToStr(req.getParameterValues("uarDepNam"), i));
			infoMap.put("uarOwnId"	, UwoStringUtils.arrToStr(req.getParameterValues("uarOwnId"), i));
			infoMap.put("uarAdmId"	, UwoStringUtils.arrToStr(req.getParameterValues("uarAdmId"), i));
			infoMap.put("uarOprCod"	, uarOprCod[i]);
			infoMap.put("uarOprNam"	, UwoStringUtils.arrToStr(req.getParameterValues("uarOprNam"), i));
			infoMap.put("uarUseId"	, UwoStringUtils.arrToStr(req.getParameterValues("uarUseId"), i));
			infoMap.put("uarPicId"	, UwoStringUtils.arrToStr(req.getParameterValues("uarPicId"), i));
			infoMap.put("uarCatCod"	, UwoStringUtils.arrToStr(req.getParameterValues("uarCatCod"), i));
			infoMap.put("uarAssGbn"	, UwoStringUtils.arrToStr(req.getParameterValues("uarAssGbn"), i));
			infoMap.put("uarAssNam"	, UwoStringUtils.arrToStr(req.getParameterValues("uarAssNam"), i));
			infoMap.put("uarEqpNam"	, UwoStringUtils.arrToStr(req.getParameterValues("uarEqpNam"), i));
			infoMap.put("uarDtlExp"	, UwoStringUtils.arrToStr(req.getParameterValues("uarDtlExp"), i));
			infoMap.put("uarAdmPos"	, UwoStringUtils.arrToStr(req.getParameterValues("uarAdmPos"), i));
			infoMap.put("uarRskGrp"	, UwoStringUtils.arrToStr(req.getParameterValues("uarRskGrp"), i));
			infoMap.put("uarRskNam"	, UwoStringUtils.arrToStr(req.getParameterValues("uarRskNam"), i));
			infoMap.put("uarAppCon"	, uarAppCon[i]);
			infoMap.put("uarAppItg"	, uarAppItg[i]);
			infoMap.put("uarAppAvt"	, uarAppAvt[i]);
			infoMap.put("uarAudYn"	, UwoStringUtils.arrToStr(req.getParameterValues("uarAudYn"), i));
			infoMap.put("uarSmpYn"	, UwoStringUtils.arrToStr(req.getParameterValues("uarSmpYn"), i));
			infoMap.put("uarInfYn"	, UwoStringUtils.arrToStr(req.getParameterValues("uarInfYn"), i));
			infoMap.put("uarPrvYn"	, UwoStringUtils.arrToStr(req.getParameterValues("uarPrvYn"), i));

			infoMap.put("uarIp"		, UwoStringUtils.arrToStr(req.getParameterValues("uarIp"), i));
			infoMap.put("uarHost"	, UwoStringUtils.arrToStr(req.getParameterValues("uarHost"), i));
			infoMap.put("uarOs"		, UwoStringUtils.arrToStr(req.getParameterValues("uarOs"), i));

			infoMap.put("uarValCl0"	, UwoStringUtils.arrToStr(req.getParameterValues("uarValCl0"), i));
			infoMap.put("uarValCl1"	, UwoStringUtils.arrToStr(req.getParameterValues("uarValCl1"), i));
			infoMap.put("uarValCl2"	, UwoStringUtils.arrToStr(req.getParameterValues("uarValCl2"), i));
			infoMap.put("uarValCl3"	, UwoStringUtils.arrToStr(req.getParameterValues("uarValCl3"), i));
			infoMap.put("uarValCl4"	, UwoStringUtils.arrToStr(req.getParameterValues("uarValCl4"), i));
			infoMap.put("uarValCl5"	, UwoStringUtils.arrToStr(req.getParameterValues("uarValCl5"), i));
			infoMap.put("uarValCl6"	, UwoStringUtils.arrToStr(req.getParameterValues("uarValCl6"), i));
			infoMap.put("uarValCl7"	, UwoStringUtils.arrToStr(req.getParameterValues("uarValCl7"), i));
			infoMap.put("uarValCl8"	, UwoStringUtils.arrToStr(req.getParameterValues("uarValCl8"), i));
			infoMap.put("uarValCl9"	, UwoStringUtils.arrToStr(req.getParameterValues("uarValCl9"), i));

			/*
			 * 2016-08-10, fmAsset001_excelUpload() 로 옮김
			 *
			int sum = Integer.valueOf(uarAppCon[i]) +  Integer.valueOf(uarAppItg[i]) +  Integer.valueOf(uarAppAvt[i]);
			String grade = "";
			if(sum >=3 && sum <=4 ){
				grade = "L(1)";
			} else if(sum >=5 && sum <=7 ){
				grade = "M(2)";
			} else if(sum >=8 && sum <=9 ){
				grade = "H(3)";
			}

			String uarGrpCod;			//그룹코드 셋팅(코드)
			String uarGrpNam;			//그룹코드명 셋팅

			uarGrpCod = uarDepCod[i] + "_" + uarSubCod[i] + "_" + UwoStringUtils.arrToStr(req.getParameterValues("uarRskGrp"), i) + "_" + grade;
			uarGrpNam = UwoStringUtils.arrToStr(req.getParameterValues("uarDepNam"), i) + "_" + UwoStringUtils.arrToStr(req.getParameterValues("uarSubNam"), i) + "_" + UwoStringUtils.arrToStr(req.getParameterValues("uarRskNam"), i) + "_" + grade;

			infoMap.put("uarGrpCod", uarGrpCod);
			infoMap.put("uarGrpNam", uarGrpNam);
			infoMap.put("uarAppTot"	, String.valueOf(sum));
			infoMap.put("uarAssLvl"	, grade);
			*/
			String uarGrpCod = UwoStringUtils.arrToStr(req.getParameterValues("uarGrpCod"), i);
			String uarGrpNam = UwoStringUtils.arrToStr(req.getParameterValues("uarGrpNam"), i);
			String uarAppTot = UwoStringUtils.arrToStr(req.getParameterValues("uarAppTot"), i);
			String uarAssLvl = UwoStringUtils.arrToStr(req.getParameterValues("uarAssLvl"), i);
			infoMap.put("uarGrpCod" , uarGrpCod);
			infoMap.put("uarGrpNam" , uarGrpNam);
			infoMap.put("uarAppTot"	, uarAppTot);
			infoMap.put("uarAssLvl"	, uarAssLvl);


			if(infoMap.get("uarAssKey")==""){
				sucCnt += fmAssetDAO.fm_asset001_excel_insert(infoMap);
			} else {
				sucCnt += fmAssetDAO.fm_asset001_excel_update(infoMap);
			}


			//자산그룹 체크 & INSERT ----------------------------------------------------------------------
			if(fmAssetDAO.fm_asset001_groupCdCount(uarGrpCod) == 0) { //해당 그룹코드가 없으면(개수가 0이면)
				/*//fm_asset001_groupCdCount 쿼리 : result가 Integer로 나오는 쿼리
				SELECT COUNT(*)
				FROM 테이블명
				WHERE 자산그룹코드 = #groupCd#*/

				String grpCodSeq = commonUtilDAO.getGrpCodSeq();
				infoMap.put("uag_grp_key", grpCodSeq);
				infoMap.put("uag_grp_cod", uarGrpCod);
				infoMap.put("uag_grp_nam", uarGrpNam);
				infoMap.put("uag_sub_cod", uarSubCod[i]);
				infoMap.put("uag_sub_nam", UwoStringUtils.arrToStr(req.getParameterValues("uarSubNam"), i));
				infoMap.put("uag_dep_cod", uarDepCod[i]);
				//riskVO.setUag_dep_nam(UwoStringUtils.arrToStr(req.getParameterValues("uarDepNam"), i));
				infoMap.put("uag_adm_id", UwoStringUtils.arrToStr(req.getParameterValues("uarAdmId"), i));
				infoMap.put("uag_ass_cat", UwoStringUtils.arrToStr(req.getParameterValues("uarCatCod"), i));
				infoMap.put("uag_grp_lvl", uarAssLvl);

				infoMap.put("uag_rgt_id", (String)req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
				infoMap.put("uag_bcy_cod", (String)req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
				infoMap.put("uag_dep_cod", uarDepCod[i]);

				System.out.println();

				fmAssetDAO.fm_asset001_assetGroup_insert(infoMap); //그룹코드 INSERT

			}
		}

		EgovMap resultMap = new EgovMap();
		resultMap.put("totCnt", uarSubCod.length);
		resultMap.put("sucCnt", sucCnt);
		resultMap.put("errCnt", uarSubCod.length - sucCnt);
		return resultMap;
	}

	/*
	 * 2016-11-30
	 * 심사 자산 확정
	 * (non-Javadoc)
	 * @see com.uwo.isms.service.FMAssetService#fm_asset003_update(java.util.Map)
	 */
	@Override
	public void fm_asset003_update(Map<String, Object> map) {
		String command = (String) map.get("command");
		if (command.equals("addFinal")) {
			fmAssetDAO.fm_asset003_addFinal(map);
		}
		else if (command.equals("removeFinal")) {
			fmAssetDAO.fm_asset003_removeFinal(map);
		}
		else if (command.equals("addSample")) {
			fmAssetDAO.fm_asset003_addSample(map);
		}
		else if (command.equals("removeSample")) {
			fmAssetDAO.fm_asset003_removeSample(map);
		}
	}
}
