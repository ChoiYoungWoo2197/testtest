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

public class INSPT001_EXCEL extends AbstractExcelView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook wb, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
	
		HSSFCell cell = null;
		HSSFSheet sheet = wb.createSheet("List");
		
		cell = getCell(sheet, 0, 0);
		setText(cell, "심사문서관리");
				
		setText(getCell(sheet, 2, 0), "서비스");
		setText(getCell(sheet, 2, 1), "부서");
		setText(getCell(sheet, 2, 2), "담당자");
		setText(getCell(sheet, 2, 3), "컴플라이언스");
		setText(getCell(sheet, 2, 4), "항목번호");		
		setText(getCell(sheet, 2, 5), "업무명(양식서)");
		setText(getCell(sheet, 2, 6), "업무주기");
		setText(getCell(sheet, 2, 7), "증적파일명");
		
		List<?> list = (List<?>)model.get("list");
		
		for(int i = 0; i< list.size();i++){
			EgovMap map = (EgovMap)list.get(i);
			
			cell = getCell(sheet, 3+i, 0);
			setText(cell,(String)map.get("utwSvcCod"));
			
			cell = getCell(sheet, 3+i, 1);
			setText(cell,(String)map.get("utwDepCod"));
			
			cell = getCell(sheet, 3+i, 2);
			setText(cell,(String)map.get("worker"));
			
			cell = getCell(sheet, 3+i, 3);
			setText(cell,(String)map.get("ucmCtrGbn"));
			
			cell = getCell(sheet, 3+i, 4);
			setText(cell,(String)map.get("ucm3lvCod"));
			
			cell = getCell(sheet, 3+i, 5);
			setText(cell,(String)map.get("utdDocNam"));
			
			cell = getCell(sheet, 3+i, 6);
			setText(cell,(String)map.get("termGbn"));
			
			cell = getCell(sheet, 3+i, 7);
			setText(cell,(String)map.get("fileName"));
		}
	}

}
