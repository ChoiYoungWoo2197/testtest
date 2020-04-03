package com.uwo.isms.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.uwo.isms.dao.FMCommonDAO;
import com.uwo.isms.service.FMCommonService;


@Service("fmCommonService")
public class FMCommonServiceImpl implements FMCommonService {
	
	Logger log = LogManager.getLogger(FMCommonServiceImpl.class);

	@Resource(name="fmCommonDAO")
	private FMCommonDAO fmCommonDAO;
	
	@Override
	public List<?> getUserList(Map<String, String> map) {
		// TODO Auto-generated method stub
		return fmCommonDAO.getUserList(map);
	}

	@Override
	public List<?> getWorkFiles(Map<String, String> map) {
		return fmCommonDAO.getWorkFIles(map);
	}
}
