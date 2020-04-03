package com.uwo.isms.dao;

import com.uwo.isms.domain.ProtectionMeasureTaskVO;
import com.uwo.isms.domain.ProtectionMeasureTaskWorkerVO;
import com.uwo.isms.domain.ProtectionMeasureVO;
import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ProtectionMeasureDAO extends EgovAbstractDAO {

    Logger log = LogManager.getLogger(ProtectionMeasureDAO.class);

    /*===[ 보호대책 ]=================================================================================================*/
    /*================================================================================================================*/

    public ProtectionMeasureVO getProtectionMeasure(int id) {
        ProtectionMeasureVO protectionMeasureVO = new ProtectionMeasureVO();
        protectionMeasureVO.setId(id);
        return (ProtectionMeasureVO) select("protectionMeasure.getProtectionMeasureList", protectionMeasureVO);
    }

    public ProtectionMeasureVO getProtectionMeasureBySeason(int season) {
        ProtectionMeasureVO protectionMeasureVO = new ProtectionMeasureVO();
        protectionMeasureVO.setSeason(season);
        return (ProtectionMeasureVO) select("protectionMeasure.getProtectionMeasureList", protectionMeasureVO);
    }

    public int storeProtectionMeasure(ProtectionMeasureVO protectionMeasureVO) {
        return (int) insert("protectionMeasure.storeProtectionMeasure", protectionMeasureVO);
    }

    public void updateProtectionMeasure(ProtectionMeasureVO protectionMeasureVO) {
        update("protectionMeasure.updateProtectionMeasureById", protectionMeasureVO);
    }

    public void deleteProtectionMeasure(int id) {
        delete("protectionMeasure.deleteProtectionMeasureById", id);
    }

    public List getSeasonListOfWorker(int userKey) {
        return list("protectionMeasure.getSeasonListOfWorker", userKey);
    }

    /*===[ 보호대책 중점과제 ]========================================================================================*/
    /*================================================================================================================*/

    public ProtectionMeasureTaskVO getProtectionMeasureTask(int id) {
        ProtectionMeasureTaskVO protectionMeasureTaskVO = new ProtectionMeasureTaskVO();
        protectionMeasureTaskVO.setId(id);
        return (ProtectionMeasureTaskVO) select("protectionMeasure.getProtectionMeasureTaskList", protectionMeasureTaskVO);
    }

    public List getProtectionMeasureTaskList(ProtectionMeasureTaskVO protectionMeasureTaskVO) {
        return list("protectionMeasure.getProtectionMeasureTaskList", protectionMeasureTaskVO);
    }

    public List getProtectionMeasureTaskListByProtectionMeasureId(int protectionMeasureId) {
        return list("protectionMeasure.getSimpleProtectionMeasureTaskList", protectionMeasureId);
    }

    public List getProtectionMeasureTaskListBySeason(int season) {
        ProtectionMeasureVO protectionMeasureVO = getProtectionMeasureBySeason(season);
        ProtectionMeasureTaskVO protectionMeasureTaskVO = new ProtectionMeasureTaskVO();
        protectionMeasureTaskVO.setProtectionMeasureId(protectionMeasureVO.getId());
        return list("protectionMeasure.getProtectionMeasureTaskList", protectionMeasureTaskVO);
    }

    public List getProtectionMeasureTaskFileListByProtectionMeasureId(int protectionMeasureId) {
        return list("protectionMeasure.getProtectionMeasureTaskFileList", protectionMeasureId);
    }

    public List getProtectionMeasureTaskOfWorker(Map map) {
        return list("protectionMeasure.getTaskListOfWorkerBySeason", map);
    }

    public int storeProtectionMeasureTask(ProtectionMeasureTaskVO protectionMeasureTaskVO) {
        return (int) insert("protectionMeasure.storeProtectionMeasureTask", protectionMeasureTaskVO);
    }

    public void updateProtectionMeasureTask(ProtectionMeasureTaskVO protectionMeasureTaskVO) {
        update("protectionMeasure.updateProtectionMeasureTaskById", protectionMeasureTaskVO);
    }

    public int deleteProtectionMeasureTask(int id) {
        return delete("protectionMeasure.deleteProtectionMeasureTaskById", id);
    }

    public List<EgovMap> getCategoryListOfUser(Map map) {
        return (List<EgovMap>) list("protectionMeasure.getCategoryListOfUser", map);
    }

    public List<EgovMap> getTaskListOfUser(Map map) {
        return (List<EgovMap>) list("protectionMeasure.getTaskListOfUser", map);
    }


    /*===[ 보호대책 중점과제 담당자 ]=================================================================================*/
    /*================================================================================================================*/

    public ProtectionMeasureTaskWorkerVO getProtectionMeasureTaskWorker(int id) {
        return (ProtectionMeasureTaskWorkerVO) select("protectionMeasure.getProtectionMeasureTaskWorkerById", id);
    }

    public ProtectionMeasureTaskWorkerVO getProtectionMeasureTaskWorker(ProtectionMeasureTaskWorkerVO protectionMeasureTaskWorkerVO) {
        return (ProtectionMeasureTaskWorkerVO) getProtectionMeasureTaskWorker(protectionMeasureTaskWorkerVO.getId());
    }

    public List getProtectionMeasureTaskWorkerList(ProtectionMeasureTaskWorkerVO protectionMeasureTaskWorkerVO) {
        return list("protectionMeasure.getProtectionMeasureTaskWorkerList", protectionMeasureTaskWorkerVO);
    }

    public int storeProtectionMeasureTaskWorker(ProtectionMeasureTaskWorkerVO protectionMeasureTaskWorkerVO) {
        return (int) insert("protectionMeasure.storeProtectionMeasureTaskWorker", protectionMeasureTaskWorkerVO);
    }

    public void updateProtectionMeasureTaskWorker(ProtectionMeasureTaskWorkerVO protectionMeasureTaskWorkerVO) {
        update("protectionMeasure.updateProtectionMeasureTaskWorkerById", protectionMeasureTaskWorkerVO);
    }

    public void deleteProtectionMeasureTaskWorker(int id) {
        ProtectionMeasureTaskWorkerVO protectionMeasureTaskWorkerVO = new ProtectionMeasureTaskWorkerVO();
        protectionMeasureTaskWorkerVO.setId(id);
        delete("protectionMeasure.deleteProtectionMeasureTaskWorker", protectionMeasureTaskWorkerVO);
    }

    public void deleteProtectionMeasureTaskWorker(ProtectionMeasureTaskWorkerVO protectionMeasureTaskWorkerVO) {
        delete("protectionMeasure.deleteProtectionMeasureTaskWorker", protectionMeasureTaskWorkerVO);
    }

    public ProtectionMeasureVO getLastProtectionMeasure() {
        return (ProtectionMeasureVO) select("protectionMeasure.getLastProtectionMeasure");
    }
}
