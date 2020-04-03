package com.uwo.isms.dao;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Repository("fmCommonDAO")
public class FMCommonDAO extends EgovAbstractDAO {
	
	Logger log = LogManager.getLogger(FMCommonDAO.class);
	
	public List<?> getUserList(Map<String, String> map) {
		return list("fmCommon.QR_GET_USER_LIST", map);
	}

	public List<?> getWorkFIles(Map<String, String> map) {
		return list("fmCommon.GET_WORK_FILES", map);
	}
}