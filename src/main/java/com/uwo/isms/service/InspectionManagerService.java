package com.uwo.isms.service;

import com.uwo.isms.dao.InspectionManagerDAO;
import com.uwo.isms.domain.SearchVO;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

@Service
public class InspectionManagerService {

    @Autowired
    private InspectionManagerDAO inspectionManagerDAO;

    @Autowired
    private FMCompsService fmCompsService;

    public List<EgovMap> getAvailableComplianceList() {
        return inspectionManagerDAO.getAvailableComplianceList();
    }

    public List<EgovMap> getControlItemList(Map map) {
        return inspectionManagerDAO.getControlItemList(map);
    }

    public List<EgovMap> getControlItemList(SearchVO searchVO) {
        Map<String, String> map = new HashMap<>();
        map.put("bcyCode", searchVO.getBcyCode());
        map.put("service", searchVO.getService());
        map.put("standard", searchVO.getStandard());
        map.put("status", searchVO.getWorkState());

        return getControlItemList(map);
    }

    public List<EgovMap> getAnswerList(Map map) {
        String kindOfCompliance = fmCompsService.getKindOfCompliance((String) map.get("standard"));
        return inspectionManagerDAO.getAnswerList(map, kindOfCompliance);
    }

    public List<EgovMap> getAnswerList(SearchVO searchVO) {
        Map<String, Object> map = new HashMap<>();
        map.put("bcyCode", Integer.parseInt(searchVO.getBcyCode()));
        map.put("service", searchVO.getService());
        map.put("controlItem", Integer.parseInt(searchVO.getControlItem()));
        map.put("standard", searchVO.getStandard());
        return getAnswerList(map);
    }

    public EgovMap getInspectionResultAdditionalInfoOfControlItem(SearchVO searchVO) {
        List<EgovMap> list = getInspectionResultAdditionalInfo(searchVO);
        return list.size() > 0 ? list.get(0) : null;
    }

    public List<EgovMap> getInspectionResultAdditionalInfo(SearchVO searchVO) {
        Map<String, Object> map = new HashMap<>();
        map.put("service", searchVO.getService());
        map.put("standard", searchVO.getStandard());
        map.put("bcyCode", searchVO.getBcyCode());
        map.put("controlItem", searchVO.getControlItem());
        return inspectionManagerDAO.getInspectionResultAdditionalInfo(map);
    }

    public List<EgovMap> getInspectionResultAdditionalInfo(Map map) {
        return inspectionManagerDAO.getInspectionResultAdditionalInfo(map);
    }

    public void saveInspectionResultAdditionalInfo(SearchVO searchVO) {
        Map<String, Object> map = new HashMap<>();
        map.put("service", searchVO.getService());
        map.put("standard", searchVO.getStandard());
        map.put("bcyCode", searchVO.getBcyCode());
        map.put("controlItem", searchVO.getControlItem());
        map.put("rstDtl", searchVO.getSearchExt1());
        map.put("rltDoc", searchVO.getSearchExt2());
        inspectionManagerDAO.saveInspectionResultAdditionalInfo(map);
    }

    public void saveInspectionResultAdditionalInfo(Map map) {
        inspectionManagerDAO.saveInspectionResultAdditionalInfo(map);
    }

    public void saveInspectionResult(Map map) {
        inspectionManagerDAO.deleteInspectionResult(map);
        String complianceKind = (String) map.get("complianceKind");

        for ( String selectedAnswerId : (String[]) map.get("selectedAnswer")) {
            map.put("answerId", selectedAnswerId);
            if (complianceKind.equalsIgnoreCase("infra_mp")) {

                String point = getInfraMpPoint(selectedAnswerId);

                map.put("answerValue", point);
            } else {
                EgovMap answer = inspectionManagerDAO.getAnswerById(complianceKind, Integer.parseInt(selectedAnswerId));
                map.put("answerValue", answer.get("point"));
            }
            inspectionManagerDAO.insertInspectionResult(map);
        }

    }

    public String getInfraMpPoint(String answerId) {
        String point = "";
        if (answerId.equalsIgnoreCase("O")) {
            point = "1";
        } else if (answerId.equalsIgnoreCase("P")) {
            point = "0.5";
        } else if (answerId.equalsIgnoreCase("X")) {
            point = "0";
        }
        return point;
    }

    /**
     *
     * @param map bcyCode, service, controlItem
     */
    public List<EgovMap> getFileListGroupByActivity(Map map) {

        List<EgovMap> activityList = inspectionManagerDAO.getActivityList(map);
        List<EgovMap> fileList = inspectionManagerDAO.getFileList(map);

        for (EgovMap activityMap : activityList) {
            ArrayList<EgovMap> matchedFileList = new ArrayList<>();
            int trcKeyOfActivity = ((BigDecimal) activityMap.get("utdTrcKey")).intValue();

            for (EgovMap fileMap : fileList) {
                int trcKeyOfFile = ((BigDecimal) fileMap.get("utdTrcKey")).intValue();
                if (trcKeyOfActivity == trcKeyOfFile)
                    matchedFileList.add(fileMap);

            }
            activityMap.put("files", matchedFileList);
        }

        return activityList;
    }

    public List<EgovMap> getFileListGroupByActivity(SearchVO searchVO) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("bcyCode", Integer.parseInt(searchVO.getBcyCode()));
        map.put("service", searchVO.getService());
        map.put("controlItem", Integer.parseInt(searchVO.getControlItem()));
        return getFileListGroupByActivity(map);
    }

}
