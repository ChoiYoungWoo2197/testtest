/**
 ***********************************
 * @source ReportService.java
 * @date 2015. 08. 24.
 * @project isms3
 * @description JasperReport Service
 ***********************************
 */
package com.uwo.isms.service;

import java.util.List;
import java.util.Map;

import com.uwo.isms.domain.SearchVO;
import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface ReportService {

	List<Map<String,Object>> mwork001_report(List<?> calendarList, List<?> resultList, String sYear, String sMonth, int iYear, int iMonth);

	List<Map<String,Object>> comps003_report(List<?> fileList, List<?> resultList, List<?> hisList);

	List<Map<String,Object>> inspt002_report(List<?> resultList, Map<String, String> map);

	List<Map<String,Object>> inspt002_C_report(List<?> resultList, Map<String, String> map);

	List<Map<String,Object>> riskm003_report(List<?> resultList, Map<String, String> map);

	List<Map<String,Object>> asset002_report(List<?> resultList, Map<String, String> map, SearchVO searchVO);

	List<Map<String,Object>> asset002_I_report(List<?> resultList, Map<String, String> map);

	List<Map<String,Object>> asset002_T_report(List<?> resultList, Map<String, String> map);

	List<Map<String,Object>> inspt004_report(List<?> resultList, List<?> fileList) throws Exception;

	public List<Map<String,Object>> inspt004_report(List<?> resultList, List<?> fileList, List<EgovMap> saFileList) throws Exception;

}
