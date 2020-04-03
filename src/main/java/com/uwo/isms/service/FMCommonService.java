package com.uwo.isms.service;

import java.util.List;
import java.util.Map;

public interface FMCommonService {
	
	public List<?> getUserList(Map<String, String> map);
	public List<?> getWorkFiles(Map<String, String> map);
}