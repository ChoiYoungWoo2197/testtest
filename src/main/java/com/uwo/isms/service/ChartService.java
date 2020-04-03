/**
 ***********************************
 * @source ChartService.java
 * @date 2015. 08. 24.
 * @project isms3
 * @description rMateChartH5 servicer
 ***********************************
 */
package com.uwo.isms.service;

import java.util.List;

public interface ChartService {

	String state002_chartJson(List<?> jsonList, String cateField, String sMode);
	
	String state002_chart(String sTitle, String mTitle, String cateField);
	
	String state002_chartBar(String sTitle, String mTitle, String cateField);
	
	String state003_chartJson(List<?> jsonList, List<?> comList, List<?> serviceList);
	
	String state003_chart(String sTitle, String mTitle, String cateField, List<?> serviceList, String service);

	String state004_chartJson(List<?> jsonList, String cateField, String sMode);
	
	String state004_chart(String sTitle, String mTitle, String cateField);
	
	String state004_chartBar(String sTitle, String mTitle, String cateField);

	String state005_chartJson(List<?> jsonList, String cateField, String sMode);
	
	String state005_chart(String sTitle, String mTitle, String cateField);
	
	String state006_chartJson(List<?> jsonList, String cateField, String sMode);
	
	String state006_chart(String sTitle, String mTitle, String cateField);
	
	String state000_chart(String sTitle, String mTitle, String cateField);
}
