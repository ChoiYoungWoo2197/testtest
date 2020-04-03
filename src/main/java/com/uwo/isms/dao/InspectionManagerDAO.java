package com.uwo.isms.dao;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InspectionManagerDAO extends EgovAbstractDAO {

    Logger log = LogManager.getLogger(InspectionManagerDAO.class);

    public List<EgovMap> getAvailableComplianceList() {
        return (List<EgovMap>) list("inspectionManager.getAvailableCompliance");
    }

    public List<EgovMap> getControlItemList(Map map) {
        return (List<EgovMap>) list("inspectionManager.getControlItem", map);
    }

    public List<EgovMap> getAnswerList(Map map, String type) {
        List<EgovMap> answerList = new ArrayList<>();
        if (type.equalsIgnoreCase("msit"))
            answerList = getMsitAnswerList(map);
        if (type.equalsIgnoreCase("infra_mp"))
            answerList = getInfraMpAnswerList(map);
        if (type.equalsIgnoreCase("infra_la"))
            answerList = getInfraLaAnswerList(map);
        return answerList;
    }

    public List<EgovMap> getMsitAnswerList(Map map) {
        return (List<EgovMap>) list("inspectionManager.getMsitAnswer", map);
    }

    public List<EgovMap> getInfraMpAnswerList(Map map) {
        return (List<EgovMap>) list("inspectionManager.getInfraMpAnswer", map);
    }

    public List<EgovMap> getInfraLaAnswerList(Map map) {
        return (List<EgovMap>) list("inspectionManager.getInfraLaAnswer", map);
    }

    public EgovMap getAnswerById(String complianceKind, int answerId) {
        Map<String, Object> map = new HashMap<>();
        map.put("complianceKind", complianceKind);
        map.put("id", answerId);
        return (EgovMap) select("inspectionManager.getAnswer", map);
    }

    public List<EgovMap> getInspectionResultAdditionalInfo(Map map) {
        return (List<EgovMap>) list("inspectionManager.getInspectionResultAdditionalInfo", map);
    }

    public void saveInspectionResultAdditionalInfo(Map map) {
        update("inspectionManager.saveInspectionResultAdditionalInfo", map);
    }

    public int deleteInspectionResult(Map map) {
        return delete("inspectionManager.deleteInspectionResult", map);
    }

    public Object insertInspectionResult(Map map) {
        return insert("inspectionManager.insertInspectionResult", map);
    }

    public List<EgovMap> getFileList(Map map) {
        return (List<EgovMap>) list("inspectionManager.getFileList", map);
    }

    public List<EgovMap> getActivityList(Map map) {
        return (List<EgovMap>) list("inspectionManager.getActivityList", map);
    }

}
