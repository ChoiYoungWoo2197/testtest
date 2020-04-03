package com.uwo.isms.service;

import java.util.Map;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface IsmsBatchService {

	public EgovMap selectLastManagementCycle();

	public void insertManagementCycle(Map<String, String> map);

	public void insertManagementCycleData(Map<String, String> map);
}