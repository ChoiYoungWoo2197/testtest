package com.uwo.isms.common;

import org.apache.poi.ss.usermodel.Cell;

public class UwoStringUtils {

	public static String objToStr(Object obj) {
		return obj == null ? "" : obj.toString();
	}
	
	public static String arrToStr(String[] arrStr, int idx) {
		String str = "";
		if( arrStr.length >= idx) {
			str = arrStr[idx];
		}
		return str;
	}
	
	public static String cellToStr(Cell obj) {
		if(obj != null){
			switch(obj.getCellType()) {
			    case Cell.CELL_TYPE_STRING: 
			    return obj.getStringCellValue();
			    
			    case Cell.CELL_TYPE_NUMERIC:
			    obj.setCellType(Cell.CELL_TYPE_STRING);
			    //return String.valueOf((obj.getNumericCellValue()));
			    return obj.getStringCellValue();
			    
			    case Cell.CELL_TYPE_BLANK:
			    return "";
			    
			    case Cell.CELL_TYPE_BOOLEAN:
			    return String.valueOf((obj.getBooleanCellValue()));
			    default: 
			}
		}
		return "";
	}
	
	
	
	public static boolean isNotEmpty(Object obj) {
		if(obj == null) {
			return false;
		} 
		if(obj.toString() == "") {
			return false;
		}
		return true;
	}
}
