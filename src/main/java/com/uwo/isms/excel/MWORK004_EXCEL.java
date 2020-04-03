package com.uwo.isms.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public class MWORK004_EXCEL extends AbstractExcelView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook wb, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
	
		HSSFCell cell = null;
		HSSFSheet sheet = wb.createSheet("통제항목별업무현황");
				
		setText(getCell(sheet, 2, 0), "통제목적");
		setText(getCell(sheet, 2, 1), "통제항목");
		setText(getCell(sheet, 2, 2), "통제점검");
		setText(getCell(sheet, 2, 3), "점검항목");
		setText(getCell(sheet, 2, 4), "업무명(양식서)");
		setText(getCell(sheet, 2, 5), "업무주기");
		setText(getCell(sheet, 2, 6), "담당자");
		
		List<?> workList = (List<?>)model.get("workList");
		
		for(int i = 0; i< workList.size();i++){
			EgovMap map = (EgovMap)workList.get(i);
			
			cell = getCell(sheet, 3+i, 0);
			setText(cell,(String)map.get("ucm1lvCod")+(String)map.get("ucm1lvNam"));
			
			cell = getCell(sheet, 3+i, 1);
			setText(cell,(String)map.get("ucm2lvCod")+(String)map.get("ucm2lvNam"));
			
			cell = getCell(sheet, 3+i, 2);
			setText(cell,(String)map.get("ucm3lvCod"));
			
			cell = getCell(sheet, 3+i, 3);
			setText(cell,(String)map.get("ucm3lvNam"));
			
			cell = getCell(sheet, 3+i, 4);
			setText(cell,(String)map.get("utdDocNam"));
			
			cell = getCell(sheet, 3+i, 5);
			setText(cell,(String)map.get("utdTrmGbn"));
			
			cell = getCell(sheet, 3+i, 6);
			setText(cell,(String)map.get("usrNm"));
		}
	}

}
