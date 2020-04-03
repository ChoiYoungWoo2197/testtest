package com.uwo.isms.service.impl;

import java.util.*;

import javax.annotation.Resource;

import com.uwo.isms.common.Constants;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.uwo.isms.dao.CommonCodeDAO;
import com.uwo.isms.dao.FMStateDAO;
import com.uwo.isms.service.FMStateService;
import com.uwo.isms.web.FMSetupController;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import com.uwo.isms.domain.SearchVO;

@Service("fmStateService")
public class FMStateServiceImpl implements FMStateService {

	Logger log = LogManager.getLogger(FMSetupController.class);

	@Resource(name="fmStateDAO")
	private FMStateDAO fmStateDAO;

	@Autowired
	private CommonCodeDAO commonCodeDAO;

	@Override
	public List<?> fmSTATE001_BOARD_LIST_1(SearchVO searchVO) {
		return fmStateDAO.fmSTATE001_BOARD_LIST_1(searchVO);
	}

	@Override
	public List<?> fmSTATE001_BOARD_LIST_2(SearchVO searchVO) {
		return fmStateDAO.fmSTATE001_BOARD_LIST_2(searchVO);
	}

	@Override
	public List<?> fmSTATE001_BOARD_LIST_3(SearchVO searchVO) {
		return fmStateDAO.fmSTATE001_BOARD_LIST_3(searchVO);
	}

	@Override
	public List<?> fmSTATE001_BOARD_LIST_4(SearchVO searchVO) {
		return fmStateDAO.fmSTATE001_BOARD_LIST_4(searchVO);
	}

	@Override
	public List<?> fmSTATE001_BOARD_LIST_1_2(SearchVO searchVO) {
		return fmStateDAO.fmSTATE001_BOARD_LIST_1_2(searchVO);
	}

	@Override
	public List<?> fmSTATE001_BOARD_LIST_2_2(SearchVO searchVO) {
		return fmStateDAO.fmSTATE001_BOARD_LIST_2_2(searchVO);
	}

	@Override
	public List<?> fmSTATE001_BOARD_LIST_3_2(SearchVO searchVO) {
		return fmStateDAO.fmSTATE001_BOARD_LIST_3_2(searchVO);
	}

	@Override
	public List<?> fmSTATE001_BOARD_LIST_4_2(SearchVO searchVO) {
		return fmStateDAO.fmSTATE001_BOARD_LIST_4_2(searchVO);
	}

	@Override
	public List<?> divlist() {
		return fmStateDAO.divlist();
	}

	@Override
	public List<?> fmSTATE002_STD_LIST(SearchVO searchVO) {
		return fmStateDAO.fmSTATE002_STD_LIST(searchVO);
	}

	@Override
	public List<?> fmSTATE002_DEPT_LIST(SearchVO searchVO) {
		return fmStateDAO.fmSTATE002_DEPT_LIST(searchVO);
	}

	@Override
	public List<?> fmSTATE002_USER_LIST(SearchVO searchVO) {
		return fmStateDAO.fmSTATE002_USER_LIST(searchVO);
	}

	@Override
	public List<?> fmSTATE002_TO_POP_LIST(SearchVO searchVO) {
		return fmStateDAO.fmSTATE002_TO_POP_LIST(searchVO);
	}

	@Override
	public List<?> fmSTATE002_TO_POP_STDLIST(SearchVO searchVO) {
		return fmStateDAO.fmSTATE002_TO_POP_STDLIST(searchVO);
	}

	@Override
	public List<?> fmSTATE002_TO_POP_DEPLIST(SearchVO searchVO) {
		return fmStateDAO.fmSTATE002_TO_POP_DEPLIST(searchVO);
	}

	@Override
	public List<?> fmSTATE002_TO_POP_WORKERLIST(SearchVO searchVO) {
		return fmStateDAO.fmSTATE002_TO_POP_WORKERLIST(searchVO);
	}

	@Override
	public List<?> fmSTATE003_LIST(SearchVO searchVO) {
		return fmStateDAO.fmSTATE003_LIST(searchVO);
	}

	@Override
	public List<?> fmSTATE003_DIV(SearchVO searchVO) {
		return fmStateDAO.fmSTATE003_DIV(searchVO);
	}

	@Override
	public List<?> fmSTATE003_COM(SearchVO searchVO) {
		return fmStateDAO.fmSTATE003_COM(searchVO);
	}

	@Override
	public List<?> fmSTATE000_MAIN(SearchVO searchVO) {
		return fmStateDAO.fmSTATE000_MAIN(searchVO);
	}

	@Override
	public List<?> fmSTATE004_STD_LIST(SearchVO searchVO) {
		return fmStateDAO.fmSTATE004_STD_LIST(searchVO);
	}

	@Override
	public List<?> fmSTATE004_DEPT_LIST(SearchVO searchVO) {
		return fmStateDAO.fmSTATE004_DEPT_LIST(searchVO);
	}

	@Override
	public List<?> fmSTATE004_USER_LIST(SearchVO searchVO) {
		return fmStateDAO.fmSTATE004_USER_LIST(searchVO);
	}

	@Override
	public List<?> fmSTATE005_STD_LIST(SearchVO searchVO) {
		return fmStateDAO.fmSTATE005_STD_LIST(searchVO);
	}

	@Override
	public List<?> fmSTATE005_DEPT_LIST(SearchVO searchVO) {
		return fmStateDAO.fmSTATE005_DEPT_LIST(searchVO);
	}

	@Override
	public List<?> fmSTATE006_STD_LIST(SearchVO searchVO) {
		return fmStateDAO.fmSTATE006_STD_LIST(searchVO);
	}

	@Override
	public List<?> fmSTATE006_SUB_LIST(SearchVO searchVO) {
		return fmStateDAO.fmSTATE006_SUB_LIST(searchVO);
	}


	/* 2016-10-19,  신규 통계 */
	@Override
	public List<?> selectWorkServiceStatistics(Map<String, String> map) {
		List<EgovMap> list = fmStateDAO.selectWorkServiceStatistics(map);
		list = getAppendTotal(list);
		return list;
	}

	@Override
	public List<?> selectWorkDepStatistics(Map<String, String> map) {
		List<EgovMap> list = fmStateDAO.selectWorkDepStatistics(map);
		list = getAppendTotal(list);
		return list;
	}

	@Override
	public List<?> selectWorkUserStatistics(Map<String, String> map) {
		List<EgovMap> list = fmStateDAO.selectWorkUserStatistics(map);
		list = getAppendTotal(list);
		return list;
	}

	@Override
	public List<?> selectWorkMonthStatistics(Map<String, String> map) {
		List<EgovMap> list = fmStateDAO.selectWorkMonthStatistics(map);
		list = getAppendTotal(list);
		return list;
	}

	@Override
	public List<?> selectWorkCtrStatistics(Map<String, String> map) {
		List<EgovMap> list = fmStateDAO.selectWorkCtrStatistics(map);
		list = getAppendTotal(list);
		return list;
	}

	@Override
	public List<?> selectAssetServiceStatistics(Map<String, Object> map) {
		// asset category
		List<?> codeList = selectAssetCodeList();
		map.put("list", codeList);

		List<EgovMap> list = fmStateDAO.selectAssetServiceStatistics(map);
		list = getAppendSumTotal(list);
		return list;
	}

	@Override
	public List<?> selectAssetServiceStatisticsOfAsset(Map<String, Object> map) {
		// service
		List<?> codeList = commonCodeDAO.getSvcCodeList("");
		map.put("list", codeList);

		List<EgovMap> list = fmStateDAO.selectAssetServiceStatisticsOfAsset(map);
		list = getAppendSumTotal(list);
		return list;
	}

	@Override
	public List<?> selectAssetDepStatistics(Map<String, Object> map) {
		// asset category
		List<?> codeList = fmStateDAO.selectAssetCodeList();
		map.put("list", codeList);

		List<EgovMap> list = fmStateDAO.selectAssetDepStatistics(map);
		list = getAppendSumTotal(list);
		return list;
	}

	@Override
	public List<?> selectAssetDepStatisticsOfAsset(Map<String, Object> map) {
		// service
		List<?> codeList = selectDepCodeListOfService(map);
		map.put("list", codeList);

		List<EgovMap> list = null;
		if (codeList.size() > 0) {
			list = fmStateDAO.selectAssetDepStatisticsOfAsset(map);
			list = getAppendSumTotal(list);
		}
		return list;
	}

	@Override
	public List<?> selectAssetCodeList() {
		// asset category
		return fmStateDAO.selectAssetCodeList();
	}

	@Override
	public List<?> selectDepCodeListOfService(Map<String, Object> map) {
		return fmStateDAO.selectDepCodeListOfService(map);
	};

	/*
	 * append Statistics total count
	 */
	protected List<EgovMap> getAppendTotal(List<EgovMap> list) {
		float total = 0;
		float comp = 0;
		float delay = 0;
		float ready = 0;

		EgovMap tmap = new EgovMap();
		// set default
		tmap.put("titleCode", "total");
		tmap.put("titleName", "Total");
		tmap.put("total", 0);
		tmap.put("comp", 0);
		tmap.put("delay", 0);
		tmap.put("ready", 0);
		tmap.put("perComp", 0);
		tmap.put("perDelay", 0);
		tmap.put("perReady", 0);

		if (list.size() > 0) {
			for (EgovMap m : list) {
				comp += Float.parseFloat(m.get("comp").toString());
				delay += Float.parseFloat(m.get("delay").toString());
				ready += Float.parseFloat(m.get("ready").toString());
			}
			total = comp + delay + ready;

			if (total > 0) {
				int perComp = 100 - Math.round((delay + ready) / total * 100);
				int perDelay = 100 - Math.round((comp + ready) / total * 100);
				int perReady = 100 - perComp - perDelay;
				tmap.put("total", total);
				tmap.put("comp", comp);
				tmap.put("delay", delay);
				tmap.put("ready", ready);
				tmap.put("perComp", perComp);
				tmap.put("perDelay", perDelay);
				tmap.put("perReady", perReady);
			}
		}
		list.add(tmap);
		return list;
	}

	@SuppressWarnings("unchecked")
	protected List<EgovMap> getAppendSumTotal(List<EgovMap> list) {

		EgovMap tmap = new EgovMap();
		tmap.put("titleCode", "total");
		tmap.put("titleName", "Total");

		if (list.size() > 0) {
			for (EgovMap m : list) {
				Iterator<String> keys = m.keySet().iterator();
				while (keys.hasNext()) {
					String key = keys.next();
					if (key.equals("titleCode") || key.equals("titleName")) {
						continue;
					}

					int val = Integer.parseInt(m.get(key).toString());
					if (tmap.containsKey(key)) {
						val += Integer.parseInt(tmap.get(key).toString());
					}
					tmap.put(key, val);
				}
			}
		}
		list.add(tmap);
		return list;
	}

	/*
	 * 2017-06-30, 자산유형별 위험도 합계
	 */
	@Override
	public List<?> selectRiskCatStatistics(Map<String, String> map) {
		float total = 0;
		float scoreH = 0;
		float scoreM = 0;
		float scoreL = 0;

		List<EgovMap> list = fmStateDAO.selectRiskCatStatistics(map);

		EgovMap tmap = new EgovMap();
		// set default
		tmap.put("titleCode", "total");
		tmap.put("titleName", "Total");
		tmap.put("scoreH", 0);
		tmap.put("scoreM", 0);
		tmap.put("scoreL", 0);

		if (list.size() > 0) {
			for (EgovMap m : list) {
				scoreH += Float.parseFloat(m.get("scoreH").toString());
				scoreM += Float.parseFloat(m.get("scoreM").toString());
				scoreL += Float.parseFloat(m.get("scoreL").toString());
			}

			total = scoreH + scoreM + scoreL;
			tmap.put("total", total);
			tmap.put("scoreH", scoreH);
			tmap.put("scoreM", scoreM);
			tmap.put("scoreL", scoreL);
		}
		list.add(tmap);

		return list;
	}

	public List<EgovMap> getMCY(){
		List<EgovMap> list = fmStateDAO.getMCY();
		return list;
	}

	public String getQuarterYear(String bcy_cod){
		return fmStateDAO.getQuarterYear(bcy_cod);
	}

	public List<Object> getStatisticsPreYear(Map<String, Object> map){
		List<Object> listReturn = new ArrayList<Object>();
		List<String> listBcyCod = new ArrayList<String>();
		if(map.get("pre_year_count")==null) map.put("pre_year_count", 3); // 정의되지 않았으면, 3년

		List<EgovMap> listPreYear = fmStateDAO.getStatisticsPreYear(map);
		Map<String, Object> mapLevel = new HashMap<String, Object>();

		for (EgovMap mapRow : listPreYear) {
			listBcyCod.add(mapRow.get("ummManCyl").toString());
			mapLevel.put(mapRow.get("ummManTle").toString(), null);
		}

		listReturn.add(listBcyCod);
		listReturn.add(listPreYear);
		listReturn.add(mapLevel);
		//map.put("list_bcy", listBcyCod);
		return listReturn;
	}

    public List<?> getStatisticsSaPart1(Map<String, String> map) {
        List<EgovMap> list = fmStateDAO.getStatisticsSaPart1(map);
        List<EgovMap> listMapCompLv1 = fmStateDAO.getStatisticsSaLv1(map);
        List<Object> listStatistics = new ArrayList<Object>();

        List<Map> listDep2 = new ArrayList<Map>();
        List<Map> listDep3 = new ArrayList<Map>();
        Map<String, Object> mapDep2 = new HashMap<String, Object>();
        Map<String, Object> mapDep3 = new HashMap<String, Object>();
        String strDep1, strDep2, strDep3;
        String strDep1Nam, strDep2Nam, strDep3Nam;
        String strPreDep2=null, strPreDep3=null;

        int intWrkPrg;
        int intDepCnt = 0;

		Map<String, Object> mapCompLv1 = new HashMap<String, Object>();
		List<Map> listComp = new ArrayList<Map>();
		String strLv1, strLv2;
		String strPreLv2=null;

        if (list.size() > 0) {
			map.put("use", "y");
			Map<String, Map> mapCPS = this.loadCriterionPoint(map);

			for (EgovMap mapRow : list) {
				strDep1=mapRow.get("udmDep1lv")==null?"NULL":String.valueOf(mapRow.get("udmDep1lv"));
				strDep2=mapRow.get("udmDep2lv")!=null?mapRow.get("udmDep2lv").toString():strDep1;
				strDep3=mapRow.get("udmDep3lv")!=null?mapRow.get("udmDep3lv").toString():strDep2;
				strDep1Nam=mapRow.get("dep1Nam")==null?"NULL":String.valueOf(mapRow.get("dep1Nam"));
				strDep2Nam=mapRow.get("dep2Nam")!=null?mapRow.get("dep2Nam").toString():strDep1Nam;
				strDep3Nam=mapRow.get("dep3Nam")!=null?mapRow.get("dep3Nam").toString():strDep2Nam;
                intWrkPrg = Integer.valueOf(mapRow.get("utwWrkPrg").toString());

                if(!strDep3.equals(strPreDep3)){
                    if(strPreDep3!=null){
						mapDep3 = calSaMap(mapDep3, mapCPS);
                        Map<String, Object> mapTempDep3 = new HashMap<String, Object>();
                        mapTempDep3.putAll(mapDep3);
                        listDep3.add(mapTempDep3);
                    }
                    mapDep3.clear();
                    mapDep3.put("code", strDep3);
                    mapDep3.put("title", strDep3Nam);
                    mapDep3.put("cnt_na", 0);
                    mapDep3.put("cnt_y", 0);
                    mapDep3.put("cnt_up", 0);
                    mapDep3.put("cnt_lp", 0);
                    mapDep3.put("cnt_n", 0);
                    mapDep3.put("prg_sum", 0);
                    mapDep3.put("prg_cnt", 0);
                    mapDep3.put("file_count", 0);
					mapDep3.put("file_limit", 0);
                    mapDep3.put("sa_edit", mapRow.get("udsSaLevel")==null?"":mapRow.get("udsSaLevel").toString());
					intDepCnt++;
                }
                if(intWrkPrg==-1) {
                    mapDep3.put("cnt_na", Integer.valueOf(mapDep3.get("cnt_na").toString())+1);
                }else{
                    if(intWrkPrg==100) {
                        mapDep3.put("cnt_y", Integer.valueOf(mapDep3.get("cnt_y").toString())+1);
                    }else if(intWrkPrg==70) {
                        mapDep3.put("cnt_up", Integer.valueOf(mapDep3.get("cnt_up").toString())+1);
                    }else if(intWrkPrg==30) {
                        mapDep3.put("cnt_lp", Integer.valueOf(mapDep3.get("cnt_lp").toString())+1);
                    }else if(intWrkPrg==0) {
                        mapDep3.put("cnt_n", Integer.valueOf(mapDep3.get("cnt_n").toString())+1);
                    }
					mapDep3.put("prg_sum", Integer.valueOf(mapDep3.get("prg_sum").toString())+intWrkPrg);
                    mapDep3.put("prg_cnt", Integer.valueOf(mapDep3.get("prg_cnt").toString())+1);
                }
                if(mapRow.get("fleCnt")!=null) {
					mapDep3.put("file_count", Integer.valueOf(mapDep3.get("file_count").toString())+Integer.valueOf(mapRow.get("fleCnt").toString()));
				}
                if(mapRow.get("utdDocCnt")!=null) {
					mapDep3.put("file_limit", Integer.valueOf(mapDep3.get("file_limit").toString()) + Integer.valueOf(mapRow.get("utdDocCnt").toString()));
				}
                strPreDep3 = strDep3;

                if(!strDep2.equals(strPreDep2)){
                    if(strPreDep2!=null){
                        List<Object> listTempDep3 = new ArrayList<Object>();
                        listTempDep3.addAll(listDep3);
                        mapDep2.put("dep3", listTempDep3);

                        Map<String, Object> mapTempDep2 = new HashMap<String, Object>();
                        mapTempDep2.putAll(mapDep2);
                        listDep2.add(mapTempDep2);
                        listDep3.clear();
                    }
                    mapDep2.clear();
                    mapDep2.put("code", strDep2);
                    mapDep2.put("title", strDep2Nam);
                }
                strPreDep2 = strDep2;

                strLv1=mapRow.get("ucm1lvCod").toString();
                strLv2=mapRow.get("ucm2lvCod").toString();

				if(mapCompLv1.get(strLv1)==null){
					Map<String, Object> mapCompTempLv1 = new HashMap<String, Object>();
					mapCompTempLv1.put("code", strLv1);
					mapCompTempLv1.put("prg_sum", 0);
					mapCompTempLv1.put("prg_cnt", 0);
					mapCompTempLv1.put("cnt_y", 0);
					mapCompTempLv1.put("cnt_up", 0);
					mapCompTempLv1.put("cnt_lp", 0);
					mapCompTempLv1.put("cnt_n", 0);
					mapCompTempLv1.put("cnt_na", 0);
					mapCompTempLv1.put("count", 0);
					mapCompTempLv1.put("prg", 0);
					mapCompTempLv1.put("dep", new HashMap<String, Object>());
					mapCompLv1.put(strLv1, mapCompTempLv1);
				}

				Map<String, Object> mapCompInfoLv1 = (Map<String, Object>) mapCompLv1.get(strLv1);
				if(intWrkPrg==-1) {
					mapCompInfoLv1.put("cnt_na", Integer.valueOf(mapCompInfoLv1.get("cnt_na").toString())+1);
				}else{
					if(intWrkPrg==100) {
						mapCompInfoLv1.put("cnt_y", Integer.valueOf(mapCompInfoLv1.get("cnt_y").toString())+1);
					}else if(intWrkPrg==70) {
						mapCompInfoLv1.put("cnt_up", Integer.valueOf(mapCompInfoLv1.get("cnt_up").toString())+1);
					}else if(intWrkPrg==30) {
						mapCompInfoLv1.put("cnt_lp", Integer.valueOf(mapCompInfoLv1.get("cnt_lp").toString())+1);
					}else if(intWrkPrg==0) {
						mapCompInfoLv1.put("cnt_n", Integer.valueOf(mapCompInfoLv1.get("cnt_n").toString())+1);
					}
					mapCompInfoLv1.put("prg_sum", Integer.valueOf(mapCompInfoLv1.get("prg_sum").toString())+intWrkPrg);
					mapCompInfoLv1.put("prg_cnt", Integer.valueOf(mapCompInfoLv1.get("prg_cnt").toString())+1);
				}
				if(strPreLv2!=strLv2){
					mapCompInfoLv1.put("count", Integer.valueOf(mapCompInfoLv1.get("count").toString())+1);
				}
				strPreLv2 = strLv2;

				Map<String, Object> mapCompDepInfo = (Map<String, Object>) mapCompInfoLv1.get("dep");
				if(mapCompDepInfo.get(strDep3)==null){
					Map<String, Object> mapCompTempDep = new HashMap<String, Object>();
					mapCompTempDep.put("prg", 0);
					mapCompTempDep.put("count", 0);
					mapCompTempDep.put("na", 0);
					mapCompDepInfo.put(strDep3, mapCompTempDep);
				}
				Map<String, Object> mapCompDepInfo2 = (Map<String, Object>) mapCompDepInfo.get(strDep3);

				if(intWrkPrg>=0) {
					mapCompDepInfo2.put("prg", Integer.valueOf(mapCompDepInfo2.get("prg").toString()) + intWrkPrg);
					mapCompDepInfo2.put("count", Integer.valueOf(mapCompDepInfo2.get("count").toString()) + 1);
				}else{
					mapCompDepInfo2.put("na", Integer.valueOf(mapCompDepInfo2.get("na").toString()) + 1);
				}
				mapCompDepInfo.put(strDep3, mapCompDepInfo2);
				mapCompInfoLv1.put("dep", mapCompDepInfo);
				mapCompLv1.put(strLv1, mapCompInfoLv1);
            }

            mapDep3 = calSaMap(mapDep3, mapCPS);
            listDep3.add(mapDep3);
            mapDep2.put("dep3", listDep3);
            listDep2.add(mapDep2);

			for (EgovMap mapRow : listMapCompLv1) {
				Map<String, Object> mapTempComp = (Map<String, Object>) mapCompLv1.get(mapRow.get("ucm1lvCod").toString());
				if(mapTempComp!=null){
					mapTempComp.put("title", mapRow.get("ucm1lvNam").toString());
					mapTempComp.put("dep_cnt", intDepCnt);
					mapTempComp.put("prg", Math.round(Float.valueOf(Float.valueOf(mapTempComp.get("prg_sum").toString())/(intDepCnt*Integer.valueOf(mapTempComp.get("count").toString())-Integer.valueOf(mapTempComp.get("cnt_na").toString())))*100)/100.0);
					listComp.add(mapTempComp);
				}
			}
			listStatistics.add(listDep2);
			listStatistics.add(listComp);
			listStatistics.add(this.getCriterion(map));

			Map<String, Object> mapSaYears = new HashMap<String, Object>();
			Map<String, Map> mapSa = new HashMap<String, Map>();
			mapSaYears.put("bcy_cod", map.get("bcy_cod"));
			mapSaYears.put("pre_year_count", 5);
			List<Object> listPreInfo = getStatisticsPreYear(mapSaYears);

			mapSaYears.put("list_bcy", listPreInfo.get(0));
			List<EgovMap> listSaYears = fmStateDAO.getStatisticsSaYears(mapSaYears);

			List<Object> listSa = new ArrayList<Object>();
			if(listSaYears.size()>0) {
				List<String> listTempDep = new ArrayList<String>();
				List<String[]> listDep = new ArrayList<String[]>();

				for (EgovMap mapSaInfos : listSaYears) {
					Map<String, Object> mapTempSaInfo = new HashMap<String, Object>();
					Integer intSum = 0, intCnt = 0;
					intSum = Integer.valueOf(mapSaInfos.get("pY").toString())*100;
					intSum += Integer.valueOf(mapSaInfos.get("pUp").toString())*70;
					intSum += Integer.valueOf(mapSaInfos.get("pLp").toString())*30;
					intCnt = Integer.valueOf(mapSaInfos.get("pY").toString());
					intCnt += Integer.valueOf(mapSaInfos.get("pUp").toString());
					intCnt += Integer.valueOf(mapSaInfos.get("pLp").toString());
					intCnt += Integer.valueOf(mapSaInfos.get("pN").toString());

					mapTempSaInfo.put("cnt_na", mapSaInfos.get("pNa"));
					mapTempSaInfo.put("file_limit", mapSaInfos.get("utdDocCnt")!=null?mapSaInfos.get("utdDocCnt"):0);
					mapTempSaInfo.put("file_count", mapSaInfos.get("fleCnt")!=null?mapSaInfos.get("fleCnt"):0);
					mapTempSaInfo.put("sa_edit", mapSaInfos.get("saLevel")!=null?mapSaInfos.get("saLevel"):"");
					mapTempSaInfo.put("prg_sum", intSum);
					mapTempSaInfo.put("prg_cnt", intCnt);
					mapTempSaInfo = calSaMap(mapTempSaInfo, mapCPS);

					if(mapSa.get(mapSaInfos.get("prdCod"))==null){
						mapSa.put(mapSaInfos.get("prdCod").toString(), new HashMap<String, String>());
					}
					Map<String, String> mapTempSaYearCod = mapSa.get(mapSaInfos.get("prdCod").toString());
					mapTempSaYearCod.put(mapSaInfos.get("depCod").toString(), mapTempSaInfo.get("sa").toString());

					if(!listTempDep.contains(mapSaInfos.get("depCod").toString())) {
						listTempDep.add(mapSaInfos.get("depCod").toString());
						String[] strTempDep = {String.valueOf(mapSaInfos.get("depCod")), String.valueOf(mapSaInfos.get("depNam"))};
						listDep.add(strTempDep);
					}
				}
				listSa.add(mapSa);
				listSa.add(listDep);
				listPreInfo.remove(2);
				listSa.add(listPreInfo);
				listStatistics.add(listSa);
			}
        }

        return listStatistics;
    }

	private Map loadCriterionPoint(Map<String, String> map){
		Map<String, List> mapCPS = new HashMap<String, List>();


		List<Map> listCPS = this.getCriterionPoint(map);
		if (listCPS.size() > 0) {
			for (Map<String, Object> mapTempCPS : listCPS) {
				mapCPS.put(mapTempCPS.get("uscKindCod").toString(), (List<Map>) mapTempCPS.get("uscPoint"));
			}
		}
		//System.out.println(mapCPS);
    	return mapCPS;
	}

    private Map calSaMap(Map<String, Object> mapValue, Map<String, Map> mapCPS){
		int intCntNa = Integer.valueOf(mapValue.get("cnt_na").toString());
        int intPrgSum = Integer.valueOf(mapValue.get("prg_sum").toString());
        int intPrgCnt = Integer.valueOf(mapValue.get("prg_cnt").toString());
        int intFileCnt = Integer.valueOf(mapValue.get("file_count").toString());
        int intFileLimitCnt = Integer.valueOf(mapValue.get("file_limit").toString());

        int intNa = 0;
        int intProg = 0;
        int intInc = 0;
        int sumSa = 0;

        List<Integer> listSa = new ArrayList<>();

        Boolean blPoint1, blPoint2;
        String strSa = mapValue.get("sa_edit").toString();

        Float fltPrgPer=0f, fltFilePer=0f;
        Double dblSa;
		mapValue.put("cnt", 0);

		if(mapCPS.get("na")!=null) {
			List<Map> listNaCP = (List<Map>) mapCPS.get("na");
			for (Map<String, String> mapCP : listNaCP) {
				blPoint1 = false;
				blPoint2 = false;
				if (mapCP.get("uscpP1Val") != null && mapCP.get("uscpP1Op") != null) {
					if (mapCP.get("uscpP1Op").equals("lt") && Integer.valueOf(mapCP.get("uscpP1Val")) < intCntNa) {
						blPoint1 = true;
					} else if (mapCP.get("uscpP1Op").equals("lte") && Integer.valueOf(mapCP.get("uscpP1Val")) <= intCntNa) {
						blPoint1 = true;
					} else if (mapCP.get("uscpP1Op").equals("eq") && Integer.valueOf(mapCP.get("uscpP1Val")) == intCntNa) {
						blPoint1 = true;
					} else if (mapCP.get("uscpP1Op").equals("gt") && Integer.valueOf(mapCP.get("uscpP1Val")) > intCntNa) {
						blPoint1 = true;
					} else if (mapCP.get("uscpP1Op").equals("gte") && Integer.valueOf(mapCP.get("uscpP1Val")) >= intCntNa) {
						blPoint1 = true;
					}
				} else {
					blPoint1 = true;
				}
				if (mapCP.get("uscpP2Val") != null && mapCP.get("uscpP2Op") != null) {
					if (mapCP.get("uscpP2Op").equals("lt") && Integer.valueOf(mapCP.get("uscpP2Val")) > intCntNa) {
						blPoint2 = true;
					} else if (mapCP.get("uscpP2Op").equals("lte") && Integer.valueOf(mapCP.get("uscpP2Val")) >= intCntNa) {
						blPoint2 = true;
					} else if (mapCP.get("uscpP2Op").equals("eq") && Integer.valueOf(mapCP.get("uscpP2Val")) == intCntNa) {
						blPoint2 = true;
					} else if (mapCP.get("uscpP2Op").equals("gt") && Integer.valueOf(mapCP.get("uscpP2Val")) < intCntNa) {
						blPoint2 = true;
					} else if (mapCP.get("uscpP2Op").equals("gte") && Integer.valueOf(mapCP.get("uscpP2Val")) <= intCntNa) {
						blPoint2 = true;
					}
				} else {
					blPoint2 = true;
				}
				if (blPoint1 && blPoint2) {
					intNa = Integer.valueOf(String.valueOf(mapCP.get("uscpKindPoint")));
					break;
				}
			}
//			System.out.println("na : "+intNa);
			mapValue.put("na", intNa);
			listSa.add(intNa);
		}

		if(mapCPS.get("prog")!=null) {
			List<Map> listProgCP = (List<Map>) mapCPS.get("prog");
			if(intPrgCnt==0) {
				intProg = 1;
			}else {
				fltPrgPer = Float.valueOf(intPrgSum) / Float.valueOf(intPrgCnt);
			}
			for (Map<String, String> mapCP : listProgCP) {
				blPoint1 = false;
				blPoint2 = false;
				if (mapCP.get("uscpP1Val") != null && mapCP.get("uscpP1Op") != null) {
					if (mapCP.get("uscpP1Op").equals("lt") && Float.valueOf(mapCP.get("uscpP1Val")) < fltPrgPer) {
						blPoint1 = true;
					} else if (mapCP.get("uscpP1Op").equals("lte") && Float.valueOf(mapCP.get("uscpP1Val")) <= fltPrgPer) {
						blPoint1 = true;
					} else if (mapCP.get("uscpP1Op").equals("eq") && Float.valueOf(mapCP.get("uscpP1Val")) == fltPrgPer) {
						blPoint1 = true;
					} else if (mapCP.get("uscpP1Op").equals("gt") && Float.valueOf(mapCP.get("uscpP1Val")) > fltPrgPer) {
						blPoint1 = true;
					} else if (mapCP.get("uscpP1Op").equals("gte") && Float.valueOf(mapCP.get("uscpP1Val")) >= fltPrgPer) {
						blPoint1 = true;
					}
				} else {
					blPoint1 = true;
				}
				if (mapCP.get("uscpP2Val") != null && mapCP.get("uscpP2Op") != null) {
					if (mapCP.get("uscpP2Op").equals("lt") && Float.valueOf(mapCP.get("uscpP2Val")) > fltPrgPer) {
						blPoint2 = true;
					} else if (mapCP.get("uscpP2Op").equals("lte") && Float.valueOf(mapCP.get("uscpP2Val")) >= fltPrgPer) {
						blPoint2 = true;
					} else if (mapCP.get("uscpP2Op").equals("eq") && Float.valueOf(mapCP.get("uscpP2Val")) == fltPrgPer) {
						blPoint2 = true;
					} else if (mapCP.get("uscpP2Op").equals("gt") && Float.valueOf(mapCP.get("uscpP2Val")) < fltPrgPer) {
						blPoint2 = true;
					} else if (mapCP.get("uscpP2Op").equals("gte") && Float.valueOf(mapCP.get("uscpP2Val")) <= fltPrgPer) {
						blPoint2 = true;
					}
				} else {
					blPoint2 = true;
				}
				if (blPoint1 && blPoint2) {
					intProg = Integer.valueOf(String.valueOf(mapCP.get("uscpKindPoint")));
					break;
				}
			}
//			System.out.println("prg : "+intProg);
			mapValue.put("prg", intProg);
			mapValue.put("prg_per", fltPrgPer);
			listSa.add(intProg);
		}

		if(mapCPS.get("inc")!=null) {
			List<Map> listIncCP = (List<Map>) mapCPS.get("inc");
			if(intFileCnt<=0) {
				fltFilePer = 0f;
			}else if(intFileLimitCnt<=0) {
				fltFilePer = 100f;
			}else {
				fltFilePer = (Float.valueOf(intFileCnt) *100) / Float.valueOf(intFileLimitCnt);
				if(fltFilePer>100f) fltFilePer=100f;
			}
			for (Map<String, String> mapCP : listIncCP) {
				blPoint1 = false;
				blPoint2 = false;
				if (mapCP.get("uscpP1Val") != null && mapCP.get("uscpP1Op") != null) {
					if (mapCP.get("uscpP1Op").equals("lt") && Float.valueOf(mapCP.get("uscpP1Val")) < fltFilePer) {
						blPoint1 = true;
					} else if (mapCP.get("uscpP1Op").equals("lte") && Float.valueOf(mapCP.get("uscpP1Val")) <= fltFilePer) {
						blPoint1 = true;
					} else if (mapCP.get("uscpP1Op").equals("eq") && Float.valueOf(mapCP.get("uscpP1Val")) == fltFilePer) {
						blPoint1 = true;
					} else if (mapCP.get("uscpP1Op").equals("gt") && Float.valueOf(mapCP.get("uscpP1Val")) > fltFilePer) {
						blPoint1 = true;
					} else if (mapCP.get("uscpP1Op").equals("gte") && Float.valueOf(mapCP.get("uscpP1Val")) >= fltFilePer) {
						blPoint1 = true;
					}
				} else {
					blPoint1 = true;
				}
				if (mapCP.get("uscpP2Val") != null && mapCP.get("uscpP2Op") != null) {
					if (mapCP.get("uscpP2Op").equals("lt") && Float.valueOf(mapCP.get("uscpP2Val")) > fltFilePer) {
						blPoint2 = true;
					} else if (mapCP.get("uscpP2Op").equals("lte") && Float.valueOf(mapCP.get("uscpP2Val")) >= fltFilePer) {
						blPoint2 = true;
					} else if (mapCP.get("uscpP2Op").equals("eq") && Float.valueOf(mapCP.get("uscpP2Val")) == fltFilePer) {
						blPoint2 = true;
					} else if (mapCP.get("uscpP2Op").equals("gt") && Float.valueOf(mapCP.get("uscpP2Val")) < fltFilePer) {
						blPoint2 = true;
					} else if (mapCP.get("uscpP2Op").equals("gte") && Float.valueOf(mapCP.get("uscpP2Val")) <= fltFilePer) {
						blPoint2 = true;
					}
				} else {
					blPoint2 = true;
				}
				if (blPoint1 && blPoint2) {
					intInc = Integer.valueOf(String.valueOf(mapCP.get("uscpKindPoint")));
					break;
				}
			}
			//System.out.println("### ["+intInc+"] "+fltFilePer+" = " +Float.valueOf(intFileCnt)+" / "+Float.valueOf(intFileLimitCnt));
			mapValue.put("file", intInc);
			listSa.add(intInc);
		}

		for(int i : listSa) sumSa += i;
		dblSa = Double.valueOf(Math.round((sumSa/Double.valueOf(listSa.size()))*100)/100.0);
//        System.out.println(listSa);
//        System.out.println(sumSa+" / "+dblSa);

		if(strSa!="") {
			mapValue.put("sa", strSa);
		}else{
			mapValue.put("sa", dblSa);
		}
		mapValue.put("sa_edit", strSa);
		mapValue.put("sa_cal", dblSa);
		mapValue.put("cnt", intPrgCnt+intCntNa);

        return mapValue;
    }

    public List<?> getStatisticsSaPart2(Map<String, String> map) {
        List<EgovMap> list = fmStateDAO.getStatisticsSaPart2(map);
        List<Object> listStatistics = new ArrayList<>();

		List<String> listLv0Title = Arrays.asList(new String[]{"관리체계 수립 및 운영", "보호대책 요구사항",""});

		List<Map> listLv0 = new ArrayList<>();
		List<Map> listLv1 = new ArrayList<>();
		List<Map> listLv2 = new ArrayList<>();
		//List<Map> listLv3 = new ArrayList<>();
		Map<String, Object> mapLv0 = new HashMap<>();
		Map<String, Object> mapLv1 = new HashMap<>();
		Map<String, Object> mapLv2 = new HashMap<>();
		//Map<String, Object> mapLv3 = new HashMap<>();

        String strLv0, strLv1, strLv2;//, strLv3;
        String strPreLv0=null, strPreLv1=null, strPreLv2=null;//, strPreLv3=null;
		int intWrkPrg;
		int intCntLv1=0, intCntLv2=0;//, intCntLv3=0;

        if (list.size() > 0) {
            for (EgovMap mapRow : list) {
				strLv1=mapRow.get("ucm1lvCod").toString();
				strLv2=mapRow.get("ucm2lvCod").toString();
				strLv0=String.valueOf(strLv1.split("\\.")[0]);
				intWrkPrg = Integer.valueOf(mapRow.get("utwWrkPrg").toString());

				if(!strLv2.equals(strPreLv2)){
					if(strPreLv2!=null){
						Map<String, Object> mapTempLv2 = new HashMap<String, Object>();
						mapTempLv2.putAll(mapLv2);
						listLv2.add(mapTempLv2);
						mapLv1.put("count", intCntLv2);
					}
					mapLv2.clear();
					mapLv2.put("code", strLv2);
					mapLv2.put("title", mapRow.get("ucm2lvNam").toString());
					mapLv2.put("cnt_y", 0);
					mapLv2.put("cnt_up", 0);
					mapLv2.put("cnt_lp", 0);
					mapLv2.put("cnt_n", 0);
					mapLv2.put("cnt_na", 0);
					mapLv2.put("prg_sum", 0);
					mapLv2.put("prg_cnt", 0);
					intCntLv2++;
					intCntLv1++;
				}
				if(intWrkPrg==-1){
					mapLv2.put("cnt_na", Integer.valueOf(mapLv2.get("cnt_na").toString())+1);
				}else{
					if(intWrkPrg==100) {
						mapLv2.put("cnt_y", Integer.valueOf(mapLv2.get("cnt_y").toString())+1);
					}else if(intWrkPrg==70) {
						mapLv2.put("cnt_up", Integer.valueOf(mapLv2.get("cnt_up").toString())+1);
					}else if(intWrkPrg==30) {
						mapLv2.put("cnt_lp", Integer.valueOf(mapLv2.get("cnt_lp").toString())+1);
					}else if(intWrkPrg==0) {
						mapLv2.put("cnt_n", Integer.valueOf(mapLv2.get("cnt_n").toString())+1);
					}
					mapLv2.put("prg_sum", Integer.valueOf(mapLv2.get("prg_sum").toString())+intWrkPrg);
					mapLv2.put("prg_cnt", Integer.valueOf(mapLv2.get("prg_cnt").toString())+1);
				}
				strPreLv2 = strLv2;

				if(!strLv1.equals(strPreLv1)){
					if(strPreLv1!=null){
						List<Object> listTempLv2 = new ArrayList<Object>();
						listTempLv2.addAll(listLv2);
						listLv2.clear();
						mapLv1.put("lv2", listTempLv2);
						mapLv0.put("count", intCntLv1-1);

						Map<String, Object> mapTempLv1 = new HashMap<String, Object>();
						mapTempLv1.putAll(mapLv1);
						listLv1.add(mapTempLv1);
						intCntLv2=1;
					}
					mapLv1.clear();
					mapLv1.put("code", strLv1);
					mapLv1.put("title", mapRow.get("ucm1lvNam").toString());
					mapLv1.put("cnt_y", 0);
					mapLv1.put("cnt_up", 0);
					mapLv1.put("cnt_lp", 0);
					mapLv1.put("cnt_n", 0);
					mapLv1.put("cnt_na", 0);
				}

				if(intWrkPrg==100) {
					mapLv1.put("cnt_y", Integer.valueOf(mapLv1.get("cnt_y").toString())+1);
				}else if(intWrkPrg==70) {
					mapLv1.put("cnt_up", Integer.valueOf(mapLv1.get("cnt_up").toString())+1);
				}else if(intWrkPrg==30) {
					mapLv1.put("cnt_lp", Integer.valueOf(mapLv1.get("cnt_lp").toString())+1);
				}else if(intWrkPrg==0) {
					mapLv1.put("cnt_n", Integer.valueOf(mapLv1.get("cnt_n").toString())+1);
				}else if(intWrkPrg==-1){
					mapLv1.put("cnt_na", Integer.valueOf(mapLv1.get("cnt_na").toString())+1);
				}
				strPreLv1 = strLv1;

				if(!strLv0.equals(strPreLv0)) {
					if(strPreLv0!=null){
						List<Object> listTempLv1 = new ArrayList<Object>();
						listTempLv1.addAll(listLv1);
						listLv1.clear();
						mapLv0.put("lv1", listTempLv1);

						Map<String, Object> mapTempLv0 = new HashMap<String, Object>();
						mapTempLv0.putAll(mapLv0);
						listLv0.add(mapTempLv0);
						intCntLv1=1;
					}
					mapLv0.clear();
					mapLv0.put("code", strLv0);
					mapLv0.put("title", listLv0Title.get(Integer.valueOf(strLv0)-1));
				}
				strPreLv0 = strLv0;
            }

			listLv2.add(mapLv2);
			mapLv1.put("lv2", listLv2);
			mapLv1.put("count", intCntLv2);

			listLv1.add(mapLv1);
			mapLv0.put("lv1", listLv1);
			mapLv0.put("count", intCntLv1);

			listLv0.add(mapLv0);

			listStatistics.add(listLv0Title);
			listStatistics.add(listLv0);
        }
        return listStatistics;
    }

	public String fmSTATE018_EDIT_SA(Map<String, String> map){
		Map<String, String> mapSa = new HashMap<String, String>();
		JSONObject joParam = JSONObject.fromObject(map.get("param"));
		String bcy_cod = joParam.get("bcy_cod").toString();
		JSONArray jaListSa = JSONArray.fromObject(joParam.get("list_sa"));
		for (Object joObject : jaListSa) {
			JSONObject joObj = JSONObject.fromObject(joObject);
			mapSa.clear();
			mapSa.put("wrk_key", map.get("wrk_key"));
			mapSa.put("bcy_cod", bcy_cod);
			mapSa.put("dept", joObj.get("dept").toString());
			mapSa.put("sa", joObj.get("sa").toString());
			if(mapSa.get("sa").equals("-")){
				fmStateDAO.deleteDepSaLevel(mapSa);
			}else{
				if(fmStateDAO.getDepSaLevel(mapSa)>0){
					fmStateDAO.updateDepSaLevel(mapSa);
				}else{
					fmStateDAO.insertDepSaLevel(mapSa);
				}
			}
		}
		return Constants.RETURN_SUCCESS;
	}

	public List<Map> initCriterion(String bcy_cod){
		Map<String, String> map = new HashMap<String, String>();
		map.put("bcy_cod", bcy_cod);
		String[][] strCrt = {
			{"inc", "증적자료", "퍼센트"}
			, {"na", "N/A 과다", "개수"}
			, {"prog", "진척도", "퍼센트"}
		};
		for (int i=0; i<strCrt.length; i++) {
			map.put("kind_cod", strCrt[i][0]);
			map.put("kind_title", strCrt[i][1]);
			map.put("kind_type", strCrt[i][2]);
			fmStateDAO.initCriterion(map);
		}
		return fmStateDAO.getCriterion(map);
	}

	public void initCriterionPoint(String bcy_cod){
		Map<String, String> map = new HashMap<String, String>();
		map.put("bcy_cod", bcy_cod);
		String[][] strCrtPnt = {
			{"inc", "1", null, null, "20", "lte"}
			, {"inc", "2", "20", "lt", "40", "lte"}
			, {"inc", "3", "40", "lt", "60", "lte"}
			, {"inc", "4", "60", "lt", "80", "lte"}
			, {"inc", "5", "80", "lt", null, null}
			, {"na", "1", "50", "lte", null, null}
			, {"na", "2", "40", "lte", "50", "lt"}
			, {"na", "3", "30", "lte", "40", "lt"}
			, {"na", "4", "20", "lt", "30", "lt"}
			, {"na", "5", null, null, "20", "lte"}
			, {"prog", "1", null, null, "60", "lte"}
			, {"prog", "2", "60", "lt", "80", "lte"}
			, {"prog", "3", "80", "lt", "90", "lte"}
			, {"prog", "4", "90", "lt", "95", "lte"}
			, {"prog", "5", "95", "lt", "100", "lte"}
		};

		for (int i=0; i<strCrtPnt.length; i++) {
			map.put("editKind", strCrtPnt[i][0]);
			map.put("point", strCrtPnt[i][1]);
			map.put("point1Val", strCrtPnt[i][2]);
			map.put("point1Op", strCrtPnt[i][3]);
			map.put("point2Val", strCrtPnt[i][4]);
			map.put("point2Op", strCrtPnt[i][5]);
			fmStateDAO.insertCriterionPoint(map);
		}
	}

	public List<Map> getCriterion(Map<String, String> map){
		return fmStateDAO.getCriterion(map);
	}

	public List<Map> getCriterionPoint(Map<String, String> map){
		List<Map> listCriterionPoints = fmStateDAO.getCriterionPoint(map);
		List<Map> listCP = new ArrayList<Map>();
		Map<String, Object> mapCriterion = new HashMap<String, Object>();
		String uscKindCod = null;
		if (listCriterionPoints.size() > 0) {
			for (Map<String, String> mapRowCriterion : listCriterionPoints) {
				if(uscKindCod==null||!uscKindCod.equals(mapRowCriterion.get("uscKindCod"))){
					if(uscKindCod!=null) {
						if(((List<Map>) mapCriterion.get("uscPoint")).size()>0) {
							Map<String, Object> mapTempCriterion = new HashMap<String, Object>();
							mapTempCriterion.putAll(mapCriterion);
							listCP.add(mapTempCriterion);
						}
					}
					mapCriterion.clear();
					mapCriterion.put("uscBcyCod", mapRowCriterion.get("uscBcyCod"));
					mapCriterion.put("uscKindCod", mapRowCriterion.get("uscKindCod"));
					mapCriterion.put("uscKindTitle", mapRowCriterion.get("uscKindTitle"));
					mapCriterion.put("uscKindType", mapRowCriterion.get("uscKindType"));
					mapCriterion.put("uscUse", mapRowCriterion.get("uscUse"));
					mapCriterion.put("uscPoint", new ArrayList<Map>());
					uscKindCod=mapRowCriterion.get("uscKindCod");
				}
				if(mapRowCriterion.get("uscpKindPoint")!=null) {
					Map<String, String> mapCriterionPoint = new HashMap<String, String>();
					mapCriterionPoint.put("uscpKindPoint", mapRowCriterion.get("uscpKindPoint"));
					mapCriterionPoint.put("uscpP1Val", mapRowCriterion.get("uscpP1Val"));
					mapCriterionPoint.put("uscpP1Op", mapRowCriterion.get("uscpP1Op"));
					mapCriterionPoint.put("uscpP2Val", mapRowCriterion.get("uscpP2Val"));
					mapCriterionPoint.put("uscpP2Op", mapRowCriterion.get("uscpP2Op"));

					List<Map> listCriterionPoint = (List<Map>) mapCriterion.get("uscPoint");
					listCriterionPoint.add(mapCriterionPoint);
					mapCriterion.put("uscPoint", listCriterionPoint);
				}
			}

			if(((List<Map>) mapCriterion.get("uscPoint")).size()>0) {
				listCP.add(mapCriterion);
			}
		}
		return listCP;
	}

	public String setCriterionPoint(Map<String, String> map) {
		Map<String, String> mapCriterion = new HashMap<String, String>();
		mapCriterion.put("bcy_cod", map.get("bcy_cod"));
		mapCriterion.put("editKind", map.get("editKind"));
		mapCriterion.put("editUse", map.get("editUse"));

		for(int i=1; i<=5; i++){
			mapCriterion.put("point", String.valueOf(i));
			mapCriterion.put("point1Val", map.get("editPoint"+i+"Val1"));
			mapCriterion.put("point1Op", map.get("editPoint"+i+"Op1"));
			mapCriterion.put("point2Val", map.get("editPoint"+i+"Val2"));
			mapCriterion.put("point2Op", map.get("editPoint"+i+"Op2"));
			if(map.get("mode").equals("new")){
				try {
					fmStateDAO.insertCriterionPoint(mapCriterion);
				} catch (Exception e) {
					return "insert";
				}
			}else{
				try {
					fmStateDAO.updateCriterion(mapCriterion);
					fmStateDAO.updateCriterionPoint(mapCriterion);
				} catch (Exception e) {
					return "update";
				}
			}
		}
		return "success";
	}

	public List<?> getStatisticsInfraMpPart1(Map<String, Object> map) {
		List<EgovMap> list = fmStateDAO.getStatisticsInfraMpPart1_1(map);
		List<EgovMap> listStatistic = new ArrayList<EgovMap>();
		List<EgovMap> listStatistics = new ArrayList<EgovMap>();
		List<EgovMap> listStatisticsY = new ArrayList<EgovMap>();

		String strLv1 = null, strLv1Nam = null;
		int points=0, totalPoints=0;
		float aswPoints=0, totalAswPoints=0;

		if (list.size() > 0) {
			for (EgovMap mapRow : list) {
				if(strLv1==null||!strLv1.equals(mapRow.get("ucm1lvCod").toString())){
					if(strLv1!=null){
						EgovMap mapStatistic = new EgovMap();
						mapStatistic.put("code", strLv1);
						mapStatistic.put("title", strLv1Nam);
						mapStatistic.put("value", Math.round(Float.valueOf(aswPoints/points*100)*100)/100.0);
						listStatistic.add(mapStatistic);
					}
					strLv1 = mapRow.get("ucm1lvCod").toString();
					strLv1Nam = mapRow.get("ucm1lvNam").toString();
					points = 0;
					aswPoints = 0;
				}
				if(!mapRow.get("uirAswVal").toString().equals("N/A")){
					points += Integer.valueOf(mapRow.get("point").toString());
					aswPoints += Integer.valueOf(mapRow.get("point").toString())*Float.valueOf(mapRow.get("uirAswVal").toString());
					totalPoints += Integer.valueOf(mapRow.get("point").toString());
					totalAswPoints += Integer.valueOf(mapRow.get("point").toString())*Float.valueOf(mapRow.get("uirAswVal").toString());
				}
			}
			EgovMap mapStatistics = new EgovMap();
			EgovMap mapStatistic = new EgovMap();
			mapStatistic.put("code", strLv1);
			mapStatistic.put("title", strLv1Nam);
			mapStatistic.put("value", Math.round(Float.valueOf(aswPoints/points*100)*100)/100.0);
			listStatistic.add(mapStatistic);
			mapStatistics.put("data", listStatistic);

			EgovMap mapAvgStatistic = new EgovMap();
			mapAvgStatistic.put("code", "avg");
			mapAvgStatistic.put("title", "평균");
			mapAvgStatistic.put("value", Math.round(Float.valueOf(totalAswPoints/totalPoints*100)*100)/100.0);
			mapStatistics.put("avg", mapAvgStatistic);

			listStatistics.add(mapStatistics);

			List<Object> listPreInfo = getStatisticsPreYear(map);
			map.put("list_bcy", listPreInfo.get(0));
			List<EgovMap> listPreYear = (List<EgovMap>) listPreInfo.get(1);
			EgovMap mapBcyValue = new EgovMap();

			List<EgovMap> listY = fmStateDAO.getStatisticsInfraMpPart1_2(map);

			String strLv1Y = null, strLv1YNam = null, strBcyCodY=null;


			if(listY.size() > 0){
				for (EgovMap mapRow : listY) {
					//System.out.println(mapRow);
					if(strLv1Y==null||!strLv1Y.equals(mapRow.get("ucm1lvCod").toString())) {
						if (strLv1Y != null) {
							EgovMap mapTempBcyValue = new EgovMap();
							mapTempBcyValue.putAll(mapBcyValue);
							EgovMap mapStatisticY = new EgovMap();
							mapStatisticY.put("code", strLv1Y);
							mapStatisticY.put("title", strLv1YNam);
							mapStatisticY.put("value", mapTempBcyValue);
							listStatisticsY.add(mapStatisticY);
							mapBcyValue.clear();
						}
						strLv1Y = mapRow.get("ucm1lvCod").toString();
						strLv1YNam = mapRow.get("ucm1lvNam").toString();
						//strBcyCodY = mapRow.get("ucmBcyCod").toString();

					}
					strBcyCodY = mapRow.get("ucmBcyCod").toString();
					mapBcyValue.put(strBcyCodY, Math.round((Float.valueOf(mapRow.get("answer").toString())/Float.valueOf(mapRow.get("point").toString())*100)*100)/100.0);
				}
				EgovMap mapTempBcyValue = new EgovMap();
				mapTempBcyValue.putAll(mapBcyValue);
				EgovMap mapStatisticY = new EgovMap();
				mapStatisticY.put("code", strLv1Y);
				mapStatisticY.put("title", strLv1YNam);
				mapStatisticY.put("value", mapTempBcyValue);
				listStatisticsY.add(mapStatisticY);

				EgovMap mapYearValue = new EgovMap();
				mapYearValue.put("data", listStatisticsY);
				mapYearValue.put("bcy", listPreYear);
				listStatistics.add(mapYearValue);
			}

		}
		return listStatistics;
	}

	public List<?> getStatisticsInfraMpPart2(Map<String, String> map) {
		List<EgovMap> list = fmStateDAO.getStatisticsInfraMpPart2(map);

		List<List> listStatistic = new ArrayList<List>();
		List<EgovMap> listStatisticLv1 = new ArrayList<EgovMap>();
		List<Map> listStatisticLv2 = new ArrayList<Map>();

		String strLv1 = null, strLv1Nam = null, strLv2 = null, strLv2Nam = null;
		Map<String, Object> mapStatisticLv2 = initInfraMpStatisticLv2();
		int lv2Cnt = 0, totalPoints=0;
		float totalAswPoints = 0;
		int item=0, na=0, points=0;
		float aswPoints=0;
		if (list.size() > 0) {
			for (EgovMap mapRow : list) {
				if(strLv2==null||!strLv2.equals(mapRow.get("ucm2lvCod").toString())){
					if(strLv2!=null) {
                        String[] Lv2 = strLv2.split("\\.");
						mapStatisticLv2.put("code", strLv2);
						mapStatisticLv2.put("title", Lv2[1]+". "+strLv2Nam);
						mapStatisticLv2.put("total", item);
						mapStatisticLv2.put("not_na", item-na);
						mapStatisticLv2.put("level", Math.round(Float.valueOf((points-aswPoints)/points*100)*100)/100.0);
						mapStatisticLv2.put("result_na", na);
						listStatisticLv2.add(mapStatisticLv2);
						totalPoints += points;
						totalAswPoints += aswPoints;
					}
					mapStatisticLv2 = initInfraMpStatisticLv2();
					strLv2 = mapRow.get("ucm2lvCod").toString();
					strLv2Nam = mapRow.get("ucm2lvNam").toString();
					item=na=0;
					points=0;
					aswPoints=0;
					lv2Cnt++;
				}
				if(strLv1==null||!strLv1.equals(mapRow.get("ucm1lvCod").toString())) {
					if (strLv1 != null) {
						EgovMap mapStatistic = new EgovMap();
						mapStatistic.put("code", strLv1);
						mapStatistic.put("title", strLv1Nam);
						mapStatistic.put("count", lv2Cnt);
						mapStatistic.put("points", totalPoints);
						mapStatistic.put("asw_points", totalAswPoints);
						mapStatistic.put("level", Math.round(Float.valueOf((totalPoints-totalAswPoints)/totalPoints*100)*100)/100.0);
						listStatisticLv1.add(mapStatistic);
					}
					strLv1 = mapRow.get("ucm1lvCod").toString();
					strLv1Nam = mapRow.get("ucm1lvNam").toString();
					lv2Cnt = 0;
					totalPoints =0;
					totalAswPoints =0;
				}

				switch(mapRow.get("grade").toString()){
					case "상":
						mapStatisticLv2.put("grade_1", Integer.valueOf(mapStatisticLv2.get("grade_1").toString())+1);
						break;
					case "중":
						mapStatisticLv2.put("grade_2", Integer.valueOf(mapStatisticLv2.get("grade_2").toString())+1);
						break;
					case "하":
						mapStatisticLv2.put("grade_3", Integer.valueOf(mapStatisticLv2.get("grade_3").toString())+1);
						break;
				}
				switch(mapRow.get("uirAswId").toString()){
					case "O":
						mapStatisticLv2.put("result_o", Integer.valueOf(mapStatisticLv2.get("result_o").toString())+1);
						break;
					case "X":
						mapStatisticLv2.put("result_x", Integer.valueOf(mapStatisticLv2.get("result_x").toString())+1);
						break;
					case "P":
						mapStatisticLv2.put("result_p", Integer.valueOf(mapStatisticLv2.get("result_p").toString())+1);
						break;
					case "N/A":
						na++;
						break;
				}
				mapStatisticLv2.put("total", Integer.valueOf(mapStatisticLv2.get("total").toString())+1);

				if(!mapRow.get("uirAswVal").toString().equals("N/A")){
					points += Integer.valueOf(mapRow.get("point").toString());
					aswPoints += Integer.valueOf(mapRow.get("point").toString())*Float.valueOf(mapRow.get("uirAswVal").toString());
				}
				item++;
			}
            String[] Lv2 = strLv2.split("\\.");
			mapStatisticLv2.put("code", strLv2);
			mapStatisticLv2.put("title", Lv2[1]+". "+strLv2Nam);
			mapStatisticLv2.put("total", item);
			mapStatisticLv2.put("not_na", item-na);
			mapStatisticLv2.put("level", Math.round(Float.valueOf((points-aswPoints)/points*100)*100)/100.0);
			mapStatisticLv2.put("result_na", na);
			listStatisticLv2.add(mapStatisticLv2);
			totalPoints += points;
			totalAswPoints += aswPoints;

			EgovMap mapStatistic = new EgovMap();
			mapStatistic.put("code", strLv1);
			mapStatistic.put("title", strLv1Nam);
			mapStatistic.put("count", ++lv2Cnt);
			mapStatistic.put("points", totalPoints);
			mapStatistic.put("asw_points", totalAswPoints);
			mapStatistic.put("level", Math.round(Float.valueOf((totalPoints-totalAswPoints)/totalPoints*100)*100)/100.0);
			listStatisticLv1.add(mapStatistic);
		}
		listStatistic.add(listStatisticLv1);
		listStatistic.add(listStatisticLv2);
		return listStatistic;
	}

	private Map<String, Object> initInfraMpStatisticLv2(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", "");
		map.put("title", "");
		map.put("total", 0);
		map.put("not_na", 0);
		map.put("grade_1", 0);
		map.put("grade_2", 0);
		map.put("grade_3", 0);
		map.put("result_o", 0);
		map.put("result_x", 0);
		map.put("result_p", 0);
		map.put("result_na", 0);
		map.put("level", 0);
		return map;
	}

	public List<?> getStatisticsInfraLaPart1(Map<String, String> map) {
		List<EgovMap> list = fmStateDAO.getStatisticsInfraLaPart1(map);
		List<Object> listStatistics = new ArrayList<Object>();
		Map<String, Object> mapLv1 = new HashMap<String, Object>();
		List<Object> listLv2 = new ArrayList<Object>();
		String strLv1 = null, aswVal=null;
		if (list.size() > 0) {
			for (EgovMap mapRow : list) {
				strLv1 = mapRow.get("ucm1lvCod").toString();
				if(mapLv1.get(strLv1)==null){
					Map<String, Object> mapLv1Info = initInfraLaStatisticPart1Lv1();
					mapLv1Info.put("title", strLv1+". "+mapRow.get("ucm1lvNam").toString().replaceAll("\n"," "));
					mapLv1.put(strLv1, mapLv1Info);
				}
				Map<String, Object> mapInfo1 = (Map<String, Object>) mapLv1.get(strLv1);
				mapInfo1.put("items", Integer.valueOf(mapInfo1.get("items").toString())+1 );

				if(mapRow.get("uirAswVal")!=null) {
					aswVal = mapRow.get("uirAswVal").toString();
					mapInfo1.put("answer", Integer.valueOf(mapInfo1.get("answer").toString()) + Integer.valueOf(aswVal));
				}else{
					aswVal = "0";
				}
				mapInfo1.put("points", Integer.valueOf(mapInfo1.get("points").toString())+5);
				mapInfo1.put("per", Math.round(Float.valueOf(Float.valueOf(mapInfo1.get("answer").toString())/Float.valueOf(mapInfo1.get("points").toString())*100)*100)/100.0);

				if(mapInfo1.get("level")==null||Integer.valueOf(mapInfo1.get("level").toString()) > Integer.valueOf(aswVal)){
					mapInfo1.put("level", aswVal);
				}
				mapLv1.put(strLv1, mapInfo1);

				Map<String, Object> mapInfo2 = new HashMap<String, Object>();
				mapInfo2.put("codeLv1", mapRow.get("ucm1lvCod").toString());
				mapInfo2.put("codeLv2", mapRow.get("ucm2lvCod").toString());
				mapInfo2.put("codeLv3", mapRow.get("ucm3lvCod").toString());
				mapInfo2.put("title", mapRow.get("ucm2lvNam").toString().replaceAll("\n"," "));
				mapInfo2.put("answer", aswVal);
				mapInfo2.put("codeLv3", mapRow.get("ucm3lvCod").toString());
				listLv2.add(mapInfo2);
			}
		}
		listStatistics.add(mapLv1);
		listStatistics.add(listLv2);
		return listStatistics;
	}

	private Map<String, Object> initInfraLaStatisticPart1Lv1(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", "");
		map.put("title", "");
		map.put("items", 0);
		map.put("answer", 0);
		map.put("points", 0);
		map.put("per", 0);
		map.put("level", null);
		return map;
	}

	public List<?> getStatisticsInfraLaPart2(Map<String, Object> map) {

		List<Object> listPreInfo = getStatisticsPreYear(map);
		map.put("list_bcy", listPreInfo.get(0));
		List<EgovMap> listPreYear = (List<EgovMap>) listPreInfo.get(1);
		Map<String, Object> mapLevel = (Map<String, Object>) listPreInfo.get(2);

		List<EgovMap> list = fmStateDAO.getStatisticsInfraLaPart2(map);
		List<Object> listStatistics = new ArrayList<Object>();
		String strLv1 = null, strLv2 = null, strLv3 = null;
		String preStrLv1 = null, preStrLv2 = null, preStrLv3=null;
		int intLv3Step=0, intAsw=0;

		List<Object> listLv1 = new ArrayList<Object>();
		List<Object> listLv2 = new ArrayList<Object>();
		List<Object> listLv3 = new ArrayList<Object>();
		Map<String, Object> mapLv1Info = new HashMap<String, Object>();
		Map<String, Object> mapLv2Info = new HashMap<String, Object>();
		Map<String, Object> mapLv3Info = new HashMap<String, Object>();

		String level = null;

		if (list.size() > 0) {
			for (EgovMap mapRow : list) {
				strLv1 = mapRow.get("ucm1lvCod").toString();
				strLv2 = mapRow.get("ucm2lvCod").toString();
				strLv3 = mapRow.get("ucm3lvCod").toString();

				if(!strLv3.equals(preStrLv3)){
					if(preStrLv3!=null){
						Map<String, Object> mapTempLv3Info = new HashMap<String, Object>();
						mapTempLv3Info.putAll(mapLv3Info);
						listLv3.add(mapTempLv3Info);
						intLv3Step++;
					}
					mapLv3Info.clear();
					mapLv3Info.put("code", strLv3);
					preStrLv3 = strLv3;
				}
				intAsw = Integer.valueOf(mapRow.get("uirAswVal").toString());
				mapLv3Info.put(mapRow.get("ummManTle").toString(), intAsw);
				//if(strLevel==null||Integer.valueOf(strLevel)>intAsw) strLevel = String.valueOf(intAsw);

				if(!strLv2.equals(preStrLv2)){
					if(preStrLv2!=null){
						List<Object> listTempLv3 = new ArrayList<Object>();
						listTempLv3.addAll(listLv3);
						mapLv2Info.put("lv3", listTempLv3);
						listLv3.clear();

						Map<String, Object> mapTempLv2Info = new HashMap<String, Object>();
						mapTempLv2Info.putAll(mapLv2Info);
						listLv2.add(mapTempLv2Info);
					}
					mapLv2Info.clear();
					mapLv2Info.put("code", strLv2);
					mapLv2Info.put("title", mapRow.get("ucm2lvNam").toString().replaceAll("\n"," "));
					preStrLv2 = strLv2;
				}

				if(!strLv1.equals(preStrLv1)){
					if(preStrLv1!=null){
						List<Object> listTempLv2 = new ArrayList<Object>();
						listTempLv2.addAll(listLv2);
						Map<String, Object> mapTempLevel = new HashMap<String, Object>();
						mapTempLevel.putAll(mapLevel);

						mapLv1Info.put("lv2", listTempLv2);
						mapLv1Info.put("count", intLv3Step);
						mapLv1Info.put("level", mapTempLevel);
						listLv2.clear();
						intLv3Step=0;

						mapLevel.clear();
						for (EgovMap mapRowYear : listPreYear) {
							mapLevel.put(mapRowYear.get("ummManTle").toString(), null);
						}
						Map<String, Object> mapTempLv1Info = new HashMap<String, Object>();
						mapTempLv1Info.putAll(mapLv1Info);
						listLv1.add(mapTempLv1Info);
					}
					mapLv1Info.clear();
					mapLv1Info.put("code", strLv1);
					mapLv1Info.put("title", mapRow.get("ucm1lvNam").toString().replaceAll("\n"," "));
					preStrLv1 = strLv1;
				}
				//mapLevel
				level = mapLevel.get(mapRow.get("ummManTle").toString())!=null?mapLevel.get(mapRow.get("ummManTle").toString()).toString():null;
				if(level==null||Integer.valueOf(level)>intAsw){
					mapLevel.put(mapRow.get("ummManTle").toString(),intAsw);
				}
			}

			listLv3.add(mapLv3Info);
			mapLv2Info.put("lv3", listLv3);
			listLv2.add(mapLv2Info);
			mapLv1Info.put("lv2", listLv2);
			mapLv1Info.put("count", ++intLv3Step);
			mapLv1Info.put("level", mapLevel);
			listLv1.add(mapLv1Info);
		}

		listStatistics.add(listPreYear);
		listStatistics.add(listLv1);
		return listStatistics;
	}

	public List<?> getStatisticsMsitPart1(Map<String, Object> map) {
		List<EgovMap> list = fmStateDAO.getStatisticsMsitPart1_1(map);
		List<EgovMap> listStatistic = new ArrayList<EgovMap>();
		List<EgovMap> listStatisticY = new ArrayList<EgovMap>();
		List<List> listStatistics = new ArrayList<List>();

		String strLv2 = null, strLv2Nam = null;
		int maxPoint=0, aswPoint=0;
		int totalMaxPoint=0, totalAswPoint=0;

		if(list.size() > 0){
			for (EgovMap mapRow : list) {
				if(strLv2==null||!strLv2.equals(mapRow.get("ucm2lvCod").toString())){
					if(strLv2!=null){
						EgovMap mapStatistic = new EgovMap();
						mapStatistic.put("code", strLv2);
						mapStatistic.put("title", strLv2Nam);
						mapStatistic.put("value", Math.round(Float.valueOf(aswPoint*100/Float.valueOf(maxPoint))));
						listStatistic.add(mapStatistic);
					}
					strLv2 = mapRow.get("ucm2lvCod").toString();
					strLv2Nam = mapRow.get("ucm2lvNam").toString();
					maxPoint = aswPoint = 0;
				}
				if(!mapRow.get("uirAswVal").toString().equals("N/A")){
					aswPoint += Integer.valueOf(mapRow.get("uirAswVal").toString());
					maxPoint += Integer.valueOf(mapRow.get("maxPoint").toString());
					totalAswPoint += Integer.valueOf(mapRow.get("uirAswVal").toString());
					totalMaxPoint += Integer.valueOf(mapRow.get("maxPoint").toString());
				}
			}
			EgovMap mapStatistic = new EgovMap();
			mapStatistic.put("code", strLv2);
			mapStatistic.put("title", strLv2Nam);
			mapStatistic.put("value", Math.round(Float.valueOf(aswPoint*100/Float.valueOf(maxPoint))));
			listStatistic.add(mapStatistic);

			EgovMap mapAvgStatistic = new EgovMap();
			mapAvgStatistic.put("code", "avg");
			mapAvgStatistic.put("title", "평균");
			mapAvgStatistic.put("value", Math.round(Float.valueOf(totalAswPoint*100/Float.valueOf(totalMaxPoint))));
			listStatistic.add(mapAvgStatistic);
		}
		listStatistics.add(listStatistic);

		List<Object> listPreInfo = getStatisticsPreYear(map);
		map.put("list_bcy", listPreInfo.get(0));
		List<EgovMap> listPreYear = (List<EgovMap>) listPreInfo.get(1);
		EgovMap mapBcyValue = new EgovMap();

		List<EgovMap> listY = fmStateDAO.getStatisticsMsitPart1_2(map);

		String strLv2Y = null, strLv2NamY = null, strBcyCodY=null;
		int maxPointY=0, aswPointY=0, totalMaxPointY=0, totalAswPointY=0;

		if(listY.size() > 0){
			for (EgovMap mapRow : listY) {
				if(strBcyCodY==null||!strBcyCodY.equals(mapRow.get("ucmBcyCod").toString())||!strLv2Y.equals(mapRow.get("ucm2lvCod").toString())){
					if(strBcyCodY!=null){
						mapBcyValue.put(strBcyCodY, Math.round(Float.valueOf(aswPointY*100/Float.valueOf(maxPointY))));
					}
					strBcyCodY = mapRow.get("ucmBcyCod").toString();
					maxPointY = aswPointY = 0;
				}

				if(strLv2Y==null||!strLv2Y.equals(mapRow.get("ucm2lvCod").toString())||!strBcyCodY.equals(mapRow.get("ucmBcyCod").toString())){
					if(strLv2Y!=null){
						EgovMap mapStatistic = new EgovMap();
						EgovMap mapTempBcyValue = new EgovMap();
						mapTempBcyValue .putAll(mapBcyValue);
						mapStatistic.put("code", strLv2Y);
						mapStatistic.put("title", strLv2NamY);
						mapStatistic.put("value", mapTempBcyValue);
						listStatisticY.add(mapStatistic);
						mapBcyValue.clear();
					}
					strLv2Y = mapRow.get("ucm2lvCod").toString();
					strLv2NamY = mapRow.get("ucm2lvNam").toString();
				}

				if(!mapRow.get("uirAswVal").toString().equals("N/A")){
					aswPointY += Integer.valueOf(mapRow.get("uirAswVal").toString());
					maxPointY += Integer.valueOf(mapRow.get("maxPoint").toString());
					totalAswPointY += Integer.valueOf(mapRow.get("uirAswVal").toString());
					totalMaxPointY += Integer.valueOf(mapRow.get("maxPoint").toString());
				}
			}
			mapBcyValue.put(strBcyCodY, Math.round(Float.valueOf(aswPointY*100/Float.valueOf(maxPointY))));
			EgovMap mapStatistic = new EgovMap();
			mapStatistic.put("code", strLv2Y);
			mapStatistic.put("title", strLv2NamY);
			mapStatistic.put("value", mapBcyValue);
			listStatisticY.add(mapStatistic);
		}
		listStatistics.add(listStatisticY);
		listStatistics.add(listPreYear);

		return listStatistics;
	}

	public List<?> getStatisticsMsitPart2(Map<String, String> map) {
		List<EgovMap> list = fmStateDAO.getStatisticsMsitPart2(map);

		List<List> listStatistic = new ArrayList<List>();
		List<EgovMap> listStatisticLv2 = new ArrayList<EgovMap>();
		List<EgovMap> listStatisticLv3 = new ArrayList<EgovMap>();

		String strLv2 = null, strLv2Nam = null;
		int maxPoint=0, aswPoint=0, lv3Cnt=0;

		if (list.size() > 0) {
			for (EgovMap mapRow : list) {

				if(strLv2==null||!strLv2.equals(mapRow.get("ucm2lvCod").toString())){
					if(strLv2!=null){
						EgovMap mapStatisticLv2 = new EgovMap();
						mapStatisticLv2.put("code", strLv2);
						mapStatisticLv2.put("title", strLv2Nam);
						mapStatisticLv2.put("avg", Math.round(Float.valueOf(aswPoint*100/Float.valueOf(maxPoint))));
						mapStatisticLv2.put("count", lv3Cnt);
						listStatisticLv2.add(mapStatisticLv2);
					}
					strLv2 = mapRow.get("ucm2lvCod").toString();
					strLv2Nam = mapRow.get("ucm2lvNam").toString();
					maxPoint = aswPoint = lv3Cnt = 0;
				}
				if(!mapRow.get("uirAswVal").toString().equals("N/A")){
					aswPoint += Integer.valueOf(mapRow.get("uirAswVal").toString());
					maxPoint += Integer.valueOf(mapRow.get("point").toString());
				}
				EgovMap mapStatisticLv3 = new EgovMap();
				mapStatisticLv3.put("lv2_code", strLv2);
				mapStatisticLv3.put("lv3_code", mapRow.get("ucm3lvCod"));
				mapStatisticLv3.put("lv3_title", mapRow.get("ucm3lvNam"));
				mapStatisticLv3.put("question", mapRow.get("qstCnt"));
				mapStatisticLv3.put("point", mapRow.get("point"));
				mapStatisticLv3.put("choice", mapRow.get("num"));
				mapStatisticLv3.put("answer", mapRow.get("uirAswVal"));
				listStatisticLv3.add(mapStatisticLv3);
				lv3Cnt++;
			}
			EgovMap mapStatisticLv2 = new EgovMap();
			mapStatisticLv2.put("code", strLv2);
			mapStatisticLv2.put("title", strLv2Nam);
			mapStatisticLv2.put("avg", Math.round(Float.valueOf(aswPoint*100/Float.valueOf(maxPoint))));
			mapStatisticLv2.put("count", lv3Cnt);
			listStatisticLv2.add(mapStatisticLv2);
			listStatistic.add(listStatisticLv2);
			listStatistic.add(listStatisticLv3);
		}
		return listStatistic;
	}
}
