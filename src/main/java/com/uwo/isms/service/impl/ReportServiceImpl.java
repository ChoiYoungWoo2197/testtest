/**
 ***********************************
 * @source ReportServiceImpl.java
 * @date 2015. 08. 24.
 * @project isms3
 * @description JasperReport ServiceImpl
 ***********************************
 */
package com.uwo.isms.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.uwo.isms.common.CommonConfig;

//import sun.org.mozilla.javascript.internal.regexp.SubString;

import com.uwo.isms.common.Constants;
import com.uwo.isms.dao.FMAssetDAO;
import com.uwo.isms.dao.FMBoardDAO;
import com.uwo.isms.dao.FMInsptDAO;
import com.uwo.isms.domain.FileVO;
import com.uwo.isms.domain.SearchVO;
import com.uwo.isms.service.ReportService;
import com.uwo.isms.web.FMSetupController;

@Service("reportService")
public class ReportServiceImpl implements ReportService {

	Logger log = LogManager.getLogger(FMSetupController.class);

	@Resource(name="fmInsptDAO")
	private FMInsptDAO fmInsptDAO;

	@Resource(name="fmAssetDAO")
	private FMAssetDAO fmAssetDAO;

	@Resource(name = "fmBoardDAO")
	private FMBoardDAO fmBoardDAO;

	public String textCut(String text,int cutNumber){
		text = " * " + text;
		if(text.length() > cutNumber - 5){
			text = text.substring(0,cutNumber) + "...";
		}
		return text;
	}

	@Override
	public List<Map<String,Object>> mwork001_report(List<?> calendarList, List<?> resultList, String sYear, String sMonth, int iYear, int iMonth) {

		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

		HashMap<String,Object> param = new HashMap<String,Object>();
		param.put("sYear", sYear);
		param.put("sMonth", sMonth);

		Map<String, Object> dataList = (Map<String, Object>) calendarList.get(0);
		Map<String, Object> workList = null;

		int fStart = Integer.parseInt(dataList.get("fstart").toString());
		int rowCnt = 0;
		int dayCnt = 0;
		String text1 = "";
		String text2 = "";

		if(fStart == 1 || fStart == 7){
			for(dayCnt = 1; dayCnt < 6; dayCnt++){
				param.put("day" + dayCnt, "");
				param.put("text" + dayCnt, "");
				param.put("text" + dayCnt+"_1", "");
				param.put("work" + dayCnt, "");
				param.put("work" + dayCnt+"_1", "");
			}
		}else{
			for(dayCnt = 1; dayCnt < fStart - 1; dayCnt++){
				param.put("day" + dayCnt, "");
				param.put("text" + dayCnt, "");
				param.put("text" + dayCnt+"_1", "");
				param.put("work" + dayCnt, "");
				param.put("work" + dayCnt+"_1", "");
			}
			rowCnt = dayCnt - 1;
		}

		HashMap<String,Object> param1 = new HashMap<String,Object>();
		HashMap<String,Object> param2 = new HashMap<String,Object>();
		HashMap<String,Object> param3 = new HashMap<String,Object>();
		HashMap<String,Object> param4 = new HashMap<String,Object>();
		HashMap<String,Object> param5 = new HashMap<String,Object>();

		for(int i = 0; i < calendarList.size(); i++){
			dataList = (Map<String, Object>) calendarList.get(i);
			if(Integer.parseInt(dataList.get("syear").toString()) == iYear && Integer.parseInt(dataList.get("smonth").toString()) == iMonth && Integer.parseInt(dataList.get("uwyDayWek").toString()) > 1 && Integer.parseInt(dataList.get("uwyDayWek").toString()) < 7){
				rowCnt++;
				int textCnt = 0;
				if(dayCnt < 6){
					param.put("day" + rowCnt, dataList.get("sday").toString());
					for(int k = 0; k < resultList.size(); k++){
						workList = (Map<String, Object>) resultList.get(k);
						if(dataList.get("uwyWrkYmd").toString().equals(workList.get("utwEndDat").toString())){
						//if(Integer.parseInt(dataList.get("uwyWrkYmd").toString()) >= Integer.parseInt(workList.get("utwStrDat").toString()) && Integer.parseInt(dataList.get("uwyWrkYmd").toString()) <= Integer.parseInt(workList.get("utwEndDat").toString())){
							if(textCnt == 0){
								text1 = textCut(workList.get("utdDocNam").toString(),30);
							}else if(textCnt == 1){
								text2 = textCut(workList.get("utdDocNam").toString(),30);
							}
							textCnt++;
						}
					}
					param.put("work" + rowCnt, textCnt);
					param.put("work" + rowCnt+"_1", "업무 : ");
					param.put("text" + rowCnt, text1);
					param.put("text" + rowCnt+"_1", text2);
				}else if(dayCnt < 11){
					param1.put("day" + rowCnt, dataList.get("sday").toString());
					for(int k = 0; k < resultList.size(); k++){
						workList = (Map<String, Object>) resultList.get(k);
						if(dataList.get("uwyWrkYmd").toString().equals(workList.get("utwEndDat").toString())){
						//if(Integer.parseInt(dataList.get("uwyWrkYmd").toString()) >= Integer.parseInt(workList.get("utwStrDat").toString()) && Integer.parseInt(dataList.get("uwyWrkYmd").toString()) <= Integer.parseInt(workList.get("utwEndDat").toString())){
							if(textCnt == 0){
								text1 = textCut(workList.get("utdDocNam").toString(),30);
							}else if(textCnt == 1){
								text2 = textCut(workList.get("utdDocNam").toString(),30);
							}
							textCnt++;
						}
					}
					param1.put("work" + rowCnt, textCnt);
					param1.put("work" + rowCnt+"_1", "업무 : ");
					param1.put("text" + rowCnt, text1);
					param1.put("text" + rowCnt+"_1", text2);
				}else if(dayCnt < 16){
					param2.put("day" + rowCnt, dataList.get("sday").toString());
					for(int k = 0; k < resultList.size(); k++){
						workList = (Map<String, Object>) resultList.get(k);
						if(dataList.get("uwyWrkYmd").toString().equals(workList.get("utwEndDat").toString())){
						//if(Integer.parseInt(dataList.get("uwyWrkYmd").toString()) >= Integer.parseInt(workList.get("utwStrDat").toString()) && Integer.parseInt(dataList.get("uwyWrkYmd").toString()) <= Integer.parseInt(workList.get("utwEndDat").toString())){
							if(textCnt == 0){
								text1 = textCut(workList.get("utdDocNam").toString(),30);
							}else if(textCnt == 1){
								text2 = textCut(workList.get("utdDocNam").toString(),30);
							}
							textCnt++;
						}
					}
					param2.put("work" + rowCnt, textCnt);
					param2.put("work" + rowCnt+"_1", "업무 : ");
					param2.put("text" + rowCnt, text1);
					param2.put("text" + rowCnt+"_1", text2);
				}else if(dayCnt < 21){
					param3.put("day" + rowCnt, dataList.get("sday").toString());
					for(int k = 0; k < resultList.size(); k++){
						workList = (Map<String, Object>) resultList.get(k);
						if(dataList.get("uwyWrkYmd").toString().equals(workList.get("utwEndDat").toString())){
						//if(Integer.parseInt(dataList.get("uwyWrkYmd").toString()) >= Integer.parseInt(workList.get("utwStrDat").toString()) && Integer.parseInt(dataList.get("uwyWrkYmd").toString()) <= Integer.parseInt(workList.get("utwEndDat").toString())){
							if(textCnt == 0){
								textCnt++;
								text1 = textCut(workList.get("utdDocNam").toString(),30);
							}else if(textCnt == 1){
								text2 = textCut(workList.get("utdDocNam").toString(),30);
							}
							textCnt++;
						}
					}
					param3.put("work" + rowCnt, textCnt);
					param3.put("work" + rowCnt+"_1", "업무 : ");
					param3.put("text" + rowCnt, text1);
					param3.put("text" + rowCnt+"_1", text2);
				}else if(dayCnt < 26){
					param4.put("day" + rowCnt, dataList.get("sday").toString());
					for(int k = 0; k < resultList.size(); k++){
						workList = (Map<String, Object>) resultList.get(k);
						if(dataList.get("uwyWrkYmd").toString().equals(workList.get("utwEndDat").toString())){
						//if(Integer.parseInt(dataList.get("uwyWrkYmd").toString()) >= Integer.parseInt(workList.get("utwStrDat").toString()) && Integer.parseInt(dataList.get("uwyWrkYmd").toString()) <= Integer.parseInt(workList.get("utwEndDat").toString())){
							if(textCnt == 0){
								textCnt++;
								text1 = textCut(workList.get("utdDocNam").toString(),30);
							}else if(textCnt == 1){
								text2 = textCut(workList.get("utdDocNam").toString(),30);
							}
							textCnt++;
						}
					}
					param4.put("work" + rowCnt, textCnt);
					param4.put("work" + rowCnt+"_1", "업무 : ");
					param4.put("text" + rowCnt, text1);
					param4.put("text" + rowCnt+"_1", text2);
				}else if(dayCnt < 31){
					param5.put("day" + rowCnt, dataList.get("sday").toString());
					for(int k = 0; k < resultList.size(); k++){
						workList = (Map<String, Object>) resultList.get(k);
						if(dataList.get("uwyWrkYmd").toString().equals(workList.get("utwEndDat").toString())){
						//if(Integer.parseInt(dataList.get("uwyWrkYmd").toString()) >= Integer.parseInt(workList.get("utwStrDat").toString()) && Integer.parseInt(dataList.get("uwyWrkYmd").toString()) <= Integer.parseInt(workList.get("utwEndDat").toString())){
							if(textCnt == 0){
								textCnt++;
								text1 = textCut(workList.get("utdDocNam").toString(),30);
							}else if(textCnt == 1){
								text2 = textCut(workList.get("utdDocNam").toString(),30);
							}
							textCnt++;
						}
					}
					param5.put("work" + rowCnt, textCnt);
					param5.put("work" + rowCnt+"_1", "업무 : ");
					param5.put("text" + rowCnt, text1);
					param5.put("text" + rowCnt+"_1", text2);
				}
				dayCnt++;
				text1 = "";
				text2 = "";
			}
			if(rowCnt == 5){
				rowCnt = 0;
			}
		}
		if(dayCnt < 31){
			for(int z = dayCnt; z < 31; z++){
				rowCnt++;
				param5.put("day" + rowCnt, "");
				param5.put("text" + rowCnt, "");
				param5.put("text" + rowCnt+"_1", "");
				param5.put("work" + rowCnt, "");
				param5.put("work" + rowCnt+"_1", "");
			}
		}
		list.add(param);
		list.add(param1);
		list.add(param2);
		list.add(param3);
		list.add(param4);
		list.add(param5);

		return list;
	}

	@Override
	public List<Map<String,Object>> comps003_report(List<?> fileList, List<?> resultList, List<?> hisList) {

		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

		Map<String, Object> dataList = null;

		String sTemp = "";
//		String mTemp = "";

		for(int i = 0; i < resultList.size(); i++){
			dataList = (Map<String, Object>) resultList.get(i);
			HashMap<String,Object> param = new HashMap<String,Object>();

			if(!sTemp.equals(dataList.get("ucm1lvNam").toString())){
				sTemp = dataList.get("ucm1lvNam").toString();
				param.put("sTitle", dataList.get("ucm1lvCod") + " " + dataList.get("ucm1lvNam"));
			}
//			if(!mTemp.equals(dataList.get("ucm2lvNam").toString())){
//				mTemp = dataList.get("ucm2lvNam").toString();
//				param.put("mTitle", dataList.get("ucm2lvCod") + " " + dataList.get("ucm2lvNam"));
//			}
			param.put("mTitle", dataList.get("ucm2lvCod") + " " + dataList.get("ucm2lvNam"));

			param.put("text1", dataList.get("ucm3lvCod"));
			param.put("text2", dataList.get("ucm3lvNam"));
			if(dataList.get("ucm3ldYn").toString().equals("N")){
				param.put("text3", "Y");
			}else{
				param.put("text3", "N");
			}
			param.put("text4", dataList.get("ucm3lvDtl"));

			String fText = "";
			for(int k = 0; k < fileList.size(); k++){
				Map<String, Object> fList = null;
				fList = (Map<String, Object>) fileList.get(k);
				if(fList.get("ucbCtrKey").equals(dataList.get("ucmCtrKey"))){
					if(k != 0){
						fText = fText + "\n";
					}
					fText = fText + "* " + fList.get("ubmBrdTle");
				}
			}
			param.put("text5", fText);

			String hText = "";
			for(int z = 0; z < hisList.size(); z++){
				Map<String, Object> hList = null;
				hList = (Map<String, Object>) hisList.get(z);
				if(hList.get("utcCtrKey").equals(dataList.get("ucmCtrKey"))){
					if(z != 0){
						hText = hText + "\n";
					}
					hText = hText + "* " + hList.get("utdDocNam");
				}
			}

			param.put("text6", hText);

			list.add(param);
		}

		return list;
	}

	@Override
	public List<Map<String,Object>> inspt002_report(List<?> resultList, Map<String, String> map) {

		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

		Map<String, Object> dataList = null;

		for(int i = 0; i < resultList.size(); i++){
			dataList = (Map<String, Object>) resultList.get(i);
			HashMap<String,Object> param = new HashMap<String,Object>();

			if(i == 0){
				param.put("text1", map.get("sTitle"));
				param.put("text2", map.get("mTitle"));
				param.put("text3", map.get("bcyText"));
				if(map.get("ufmRstSta").equals("N")){
					param.put("text4", "- 총 결함 "+ dataList.get("totcnt")+"건 중 "+ dataList.get("totcntC")+"건 완료");
					param.put("text5", "- 총 결함 "+ dataList.get("totcnt")+"건 중 "+ dataList.get("totcntI")+"건 미완료");
				}
			}
			if(map.get("ufmRstSta").equals("Y")){
				param.put("text4", "□ 결함 내역 : 총 "+ dataList.get("totcnt")+" 건");
				param.put("text5", "□ 보완조치 내역 : 총 "+ dataList.get("totcntC")+" 건");
				param.put("text6", map.get("sTitle") + "정보보호 최고책임자 :               (인)");
				list.add(param);
				break;
			}
			param.put("text6", dataList.get("ucmGolNo") + " " + dataList.get("ucm1lvNam"));
			param.put("text7", "○ "+dataList.get("ufmFltNam"));
			param.put("text8", dataList.get("ufmFltDes"));
			param.put("text9", map.get("comDat" + dataList.get("ufmFltKey")));

			list.add(param);
		}

		return list;
	}

	@Override
	public List<Map<String,Object>> inspt002_C_report(List<?> resultList, Map<String, String> map) {

		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

		Map<String, Object> detaiData = null;

		for(int i = 0; i < resultList.size(); i++){
			detaiData = (Map<String, Object>) resultList.get(i);

			Map<String, String> detailMap = new HashMap<String, String> ();

			detailMap.putAll(map);
			detailMap.put("ufmFltKey", detaiData.get("ufmFltKey").toString());

			List<?> detailList = fmInsptDAO.fm_inspt002_report_mng(detailMap);

			Map<String, Object> data = null;

			for(int k = 0; k < detailList.size(); k++){
				data = (Map<String, Object>) detailList.get(k);
				HashMap<String,Object> param = new HashMap<String,Object>();

				param.put("text1", data.get("ufmCtrDes") + " " + data.get("ucm3lvCod") + " " + data.get("ucm3lvNam"));
				if(data.get("ufmFltLvl").toString().equals("1")){
					param.put("text2", "□ 중결함			■ 결 함");
				}else{
					param.put("text2", "■ 중결함			□ 결 함");
				}

				String sText = "";

				if(data.get("ufmFltDes").toString().length() > 0){
					sText += "□ 결함내용<br/>" + data.get("ufmFltDes") + "<br/><br/>";
				}

				if(data.get("ufmFltDes").toString().length() > 0){
					sText += "□ 보완내역<br/>" + data.get("ufpImpDes") + "<br/><br/>";
				}

				FileVO fvo = new FileVO();
				fvo.setUmf_con_key(data.get("ufpMapKey").toString());
				fvo.setUmf_tbl_gbn(Constants.FILE_GBN_INS);
				fvo.setUmf_con_gbn(Constants.FILE_CON_INS);
				List<?> fileList = fmBoardDAO.fm_file(fvo);

				FileVO fileData = new FileVO();

				if(fileList.size() > 0){
					sText += "□ 관련근거<br/>";
				}

				for(int z = 0; z < fileList.size(); z++){
					fileData = (FileVO) fileList.get(z);
					sText = sText + (z+1) + ". " + fileData.getUmf_con_fnm();
					if(z != fileList.size() - 1){
						sText = sText + "<br/>";
					}
				}

				param.put("text3", sText);
				param.put("text4", data.get("ufpUptMdh"));

				list.add(param);
			}
		}

		return list;
	}

	@Override
	public List<Map<String,Object>> riskm003_report(List<?> resultList, Map<String, String> map) {

		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

		Map<String, Object> dataList = null;

		String tempTitle = "";

		for(int i = 0; i < resultList.size(); i++){
			dataList = (Map<String, Object>) resultList.get(i);
			HashMap<String,Object> param = new HashMap<String,Object>();

			if(i == 0 && map.get("grpKey").equals("0")){
				param.put("sTitle", "정보보호관리체계 위험조치계획서");
				param.put("sColumn", "위험시나리오<br/>코드");
				param.put("mColumn", "위험시나리오");
			}else{
				param.put("sTitle", dataList.get("uagGrpNam") + " 위험조치계획서");
				param.put("sColumn", "우려사항<br/>코드");
				param.put("mColumn", "우려사항");
			}

			param.put("text0", i+1);

			param.put("text4", dataList.get("urgMesDes"));
			param.put("text5", dataList.get("usoCocGrd"));
			param.put("text6", dataList.get("urgRskPrc"));
			param.put("text7", dataList.get("urgRgtMdh"));
			if(map.get("grpKey").equals("0")){
				param.put("text1", "정보보호 " + dataList.get("usoAssCat"));
				param.put("text2", dataList.get("usmSroCod"));
				param.put("text3", dataList.get("usmSroDes"));
				param.put("text8", "시스템관리자팀");
			}else{
				if(!dataList.get("usoFirCat").toString().equals(tempTitle)){
					tempTitle = dataList.get("usoFirCat").toString();
					param.put("mTitle", tempTitle);
				}
				param.put("text1", dataList.get("uagGrpDes"));
				param.put("text2", dataList.get("usoCocCod"));
				param.put("text3", dataList.get("usoCocNam"));
				param.put("text8", dataList.get("uagDepNam"));
				param.put("text10", dataList.get("urgRstDes"));
			}
			param.put("text9", dataList.get("urgLimDes"));

			list.add(param);
		}

		return list;
	}

	@Override
	public List<Map<String,Object>> asset002_report(List<?> resultList, Map<String, String> map, SearchVO searchVO) {

		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

		Map<String, Object> dataList = null;

		for(int i = 0; i < resultList.size(); i++){
			dataList = (Map<String, Object>) resultList.get(i);

			searchVO.setCode(dataList.get("uagGrpKey").toString());

			List<?> detailList = fmAssetDAO.fm_asset002_report(searchVO);

			Map<String, Object> data = null;

			for(int k = 0; k < detailList.size(); k++){
				data = (Map<String, Object>) detailList.get(k);
				HashMap<String,Object> param = new HashMap<String,Object>();

				if(k == 0){
					param.put("mTitle", dataList.get("uagCatNam") + " 그룹핑 결과");
					param.put("text1", "그룹명");
					param.put("text2", "팀명");
					param.put("text3", "서비스");
					if(dataList.get("uagAssCat").toString().equals("9")){
						param.put("text4", "종류");
					}else if(dataList.get("uagAssCat").toString().equals("6") || dataList.get("uagAssCat").toString().equals("7")){
						param.put("text4", "OS");
					}else{
						param.put("text4", "제조사");
					}
					param.put("text5", "등급");
				}
				param.put("text6", data.get("uagGrpNam"));
				param.put("text7", data.get("depNam"));
				param.put("text8", data.get("serviceNam"));
				if(dataList.get("uagAssCat").toString().equals("9")){
					param.put("text9", data.get("uarValCl2"));
				}else if(dataList.get("uagAssCat").toString().equals("6") || dataList.get("uagAssCat").toString().equals("7")){
					param.put("text9", data.get("uarAosNam"));
				}else{
					param.put("text9", data.get("uarMakNam"));
				}
				param.put("text10", data.get("uarAssLvl"));
				list.add(param);
			}
		}

		return list;
	}

	@Override
	public List<Map<String,Object>> asset002_I_report(List<?> resultList, Map<String, String> map) {

		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

		Map<String, Object> dataList = null;

		for(int i = 0; i < resultList.size(); i++){
			dataList = (Map<String, Object>) resultList.get(i);
			HashMap<String,Object> param = new HashMap<String,Object>();

			param.put("text1", i+1);
			param.put("text2", dataList.get("usmSroDes"));
			param.put("text3", dataList.get("usoCocGrd"));
			param.put("text4", dataList.get("urgMesDes"));

			list.add(param);
		}

		return list;
	}

	@Override
	public List<Map<String,Object>> asset002_T_report(List<?> resultList, Map<String, String> map) {

		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

		Map<String, Object> dataList = null;

		String tempTitle = "";
		String tempColumn = "";


		for(int i = 0; i < resultList.size(); i++){
			dataList = (Map<String, Object>) resultList.get(i);
			HashMap<String,Object> param = new HashMap<String,Object>();

			if(!dataList.get("usoAssCat").toString().equals(tempTitle)){
				tempTitle = dataList.get("usoAssCat").toString();
				param.put("mTitle", tempTitle + " 위험평가 결과");

				param.put("text1", "No");
				param.put("text2", "해당 그룹명");
				param.put("text3", "취약성 내용");
				param.put("text4", "위험도");
				param.put("text5", "개선방안 및 보호대첵");
			}

			if(!dataList.get("usoFirCat").toString().equals(tempColumn)){
				tempColumn = dataList.get("usoFirCat").toString();
				param.put("mColumn", tempColumn);
			}

			param.put("text6", i+1);
			param.put("text7", dataList.get("uagGrpNam"));
			param.put("text8", dataList.get("usoCocNam"));
			param.put("text9", dataList.get("usoCocGrd"));
			param.put("text10", dataList.get("urgMesDes"));

			list.add(param);
		}

		return list;
	}


	@Override
	public List<Map<String,Object>> inspt004_report(List<?> resultList, List<?> fileList) throws Exception {

		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

		Map<String, Object> dataList = null;
//		List<EgovMap> saFileList = fmInsptDAO.getMeasureSaFile();

		String sTemp = "";

		for(int i = 0; i < resultList.size(); i++){
			dataList = (Map<String, Object>) resultList.get(i);
			HashMap<String,Object> param = new HashMap<String,Object>();

			String currentCtrKey = dataList.get("ucmCtrKey").toString();

			if(!sTemp.equals(dataList.get("ucm1lvNam").toString())){
				sTemp = dataList.get("ucm1lvNam").toString();
				param.put("sTitle", dataList.get("ucm1lvCod") + " " + dataList.get("ucm1lvNam"));
			}

			param.put("mTitle", dataList.get("ucm2lvCod") + " " + dataList.get("ucm2lvNam"));

			param.put("text1", dataList.get("ucm3lvCod"));
			param.put("text2", dataList.get("ucm3lvNam"));
			if(dataList.get("ucm3ldYn").toString().equals("N")){
				param.put("text3", "Y");
			}else{
				param.put("text3", "N");
			}
			//param.put("text4", dataList.get("ucm3lvDtl"));
			param.put("text4", dataList.get("ucmMsrDtl"));

			String hText = "";
			if (dataList.get("utdDocNam") != null) {
				String[] pairs = dataList.get("utdDocNam").toString().split("\\^\\*\\^");
				for (int j = 0; j < pairs.length; j++) {
					if(j != 0) {
						hText = hText + "\n";
					}
					hText += "* " + pairs[j];
				}
			}
			param.put("text5", hText);

			String fText = "";
			/* 2017-07-26, 별도 List로 처리
			if (dataList.get("fileName") != null) {
				String[] pairs = dataList.get("fileName").toString().split("\\^\\*\\^");
				for (int j = 0; j < pairs.length - 1; j = j + 2) {
					if(j != 0) {
						fText = fText + "\n";
					}
					fText += "* " + pairs[j+1];
				}
			} */
			if (dataList.get("wrkKeys") != null && fileList.size() > 0) {
				fText = "";
				String[] wrkKeys = dataList.get("wrkKeys").toString().split(",");

				for (int j = 0; j < wrkKeys.length; j++) {
					for(int k = 0; k < fileList.size(); k++){
						Map<String, String> m = (Map<String, String>) fileList.get(k);
						if (wrkKeys[j].equals(m.get("umfConKey"))) {
							if (fText != "") {
								fText = fText + "\n";
							}
							fText += "* " + m.get("umfConFnm");
						}
					}
				}
			}

			param.put("text6", fText);

			list.add(param);
		}

		return list;
	}

	public List<Map<String,Object>> inspt004_report(List<?> resultList, List<?> fileList, List<EgovMap> saFileList) throws Exception {

		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

		Map<String, Object> dataList = null;
//		List<EgovMap> saFileList = fmInsptDAO.getMeasureSaFile();

		String sTemp = "";

		for(int i = 0; i < resultList.size(); i++){
			dataList = (Map<String, Object>) resultList.get(i);
			HashMap<String,Object> param = new HashMap<String,Object>();

			BigDecimal currentCtrKey = (BigDecimal) dataList.get("ucmCtrKey");

			if(!sTemp.equals(dataList.get("ucm1lvNam").toString())){
				sTemp = dataList.get("ucm1lvNam").toString();
				param.put("sTitle", dataList.get("ucm1lvCod") + " " + dataList.get("ucm1lvNam"));
			}

			param.put("mTitle", dataList.get("ucm2lvCod") + " " + dataList.get("ucm2lvNam"));

			param.put("text1", dataList.get("ucm3lvCod"));
			param.put("text2", dataList.get("ucm3lvNam"));
			if(dataList.get("ucm3ldYn").toString().equals("N")){
				param.put("text3", "Y");
			}else{
				param.put("text3", "N");
			}
			//param.put("text4", dataList.get("ucm3lvDtl"));
			param.put("text4", dataList.get("ucmMsrDtl"));

			String hText = "";
			if (dataList.get("utdDocNam") != null) {
				String[] pairs = dataList.get("utdDocNam").toString().split("\\^\\*\\^");
				for (int j = 0; j < pairs.length; j++) {
					if(j != 0) {
						hText = hText + "\n";
					}
					hText += "* " + pairs[j];
				}
			}
			param.put("text5", hText);

			String fText = "";
			/* 2017-07-26, 별도 List로 처리
			if (dataList.get("fileName") != null) {
				String[] pairs = dataList.get("fileName").toString().split("\\^\\*\\^");
				for (int j = 0; j < pairs.length - 1; j = j + 2) {
					if(j != 0) {
						fText = fText + "\n";
					}
					fText += "* " + pairs[j+1];
				}
			} */
			if (dataList.get("wrkKeys") != null && fileList.size() > 0) {
				fText = "";
				String[] wrkKeys = dataList.get("wrkKeys").toString().split(",");

				for (int j = 0; j < wrkKeys.length; j++) {
					for(int k = 0; k < fileList.size(); k++){
						Map<String, String> m = (Map<String, String>) fileList.get(k);
						if (wrkKeys[j].equals(m.get("umfConKey"))) {
							if (fText != "") {
								fText = fText + "\n";
							}
							fText += "* " + m.get("umfConFnm");
						}
					}
				}
			}

			for (EgovMap saFileMap : saFileList) {
				if (currentCtrKey.equals(saFileMap.get("ctrKey"))) {
					if (fText != "") {
						fText = fText + "\n";
					}
					fText += saFileMap.get("conFnm");
				}
			}

			param.put("text6", fText);

			list.add(param);
		}

		return list;
	}

}
