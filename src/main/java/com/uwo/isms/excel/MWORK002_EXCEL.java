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

public class MWORK002_EXCEL extends AbstractExcelView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook wb, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
	
		HSSFCell cell = null;
		HSSFSheet sheet = wb.createSheet("미완료업무");
				
		setText(getCell(sheet, 2, 0), "업무기간");
		setText(getCell(sheet, 2, 1), "업무명");
		setText(getCell(sheet, 2, 2), "표준");
		setText(getCell(sheet, 2, 3), "항목번호");
		setText(getCell(sheet, 2, 4), "업무주기");
		setText(getCell(sheet, 2, 5), "대무자");
		
		List<?> noWorkList = (List<?>)model.get("noWorkList");
		
		for(int i = 0; i< noWorkList.size();i++){
			EgovMap map = (EgovMap)noWorkList.get(i);
			
			cell = getCell(sheet, 3+i, 0);
			String period = (String)map.get("utwStrDat")+"~"+(String)map.get("utwEndDat");
			setText(cell,period);
			
			cell = getCell(sheet, 3+i, 1);
			setText(cell,(String)map.get("utdDocNam"));
			
			cell = getCell(sheet, 3+i, 2);
			setText(cell,(String)map.get("ucmCtrGbn"));
			
			cell = getCell(sheet, 3+i, 3);
			setText(cell,(String)map.get("ucmGolNo"));
			
			cell = getCell(sheet, 3+i, 4);
			setText(cell,(String)map.get("utwTrmCod"));
			
			cell = getCell(sheet, 3+i, 5);
			setText(cell,(String)map.get("utwAgnId"));
		}
		
		HSSFSheet sheet2 = wb.createSheet("완료업무");
		
		setText(getCell(sheet2, 2, 0), "업무기간");
		setText(getCell(sheet2, 2, 1), "업무명");
		setText(getCell(sheet2, 2, 2), "표준");
		setText(getCell(sheet2, 2, 3), "항목번호");
		setText(getCell(sheet2, 2, 4), "업무주기");
		setText(getCell(sheet2, 2, 5), "완료자");
		
		List<?> comWorkList = (List<?>)model.get("comWorkList");
		
		for(int i = 0; i< comWorkList.size();i++){
			EgovMap map = (EgovMap)comWorkList.get(i);
			
			cell = getCell(sheet2, 3+i, 0);
			String period = (String)map.get("utwStrDat")+"~"+(String)map.get("utwEndDat");
			setText(cell,period);
			
			cell = getCell(sheet2, 3+i, 1);
			setText(cell,(String)map.get("utdDocNam"));
			
			cell = getCell(sheet2, 3+i, 2);
			setText(cell,(String)map.get("ucmCtrGbn"));
			
			cell = getCell(sheet2, 3+i, 3);
			setText(cell,(String)map.get("ucmGolNo"));
			
			cell = getCell(sheet2, 3+i, 4);
			setText(cell,(String)map.get("utwTrmCod"));
			
			cell = getCell(sheet2, 3+i, 5);
			setText(cell,(String)map.get("utwWrkId"));
		}
		
		HSSFSheet sheet3 = wb.createSheet("지연업무");
		
		setText(getCell(sheet3, 2, 0), "업무기간");
		setText(getCell(sheet3, 2, 1), "업무명");
		setText(getCell(sheet3, 2, 2), "표준");
		setText(getCell(sheet3, 2, 3), "항목번호");
		setText(getCell(sheet3, 2, 4), "업무주기");
		setText(getCell(sheet3, 2, 5), "완료자");
		
		List<?> delayWorkList = (List<?>)model.get("delayWorkList");
		
		for(int i = 0; i< delayWorkList.size();i++){
			EgovMap map = (EgovMap)delayWorkList.get(i);
			
			cell = getCell(sheet3, 3+i, 0);
			String period = (String)map.get("utwStrDat")+"~"+(String)map.get("utwEndDat");
			setText(cell,period);
			
			cell = getCell(sheet3, 3+i, 1);
			setText(cell,(String)map.get("utdDocNam"));
			
			cell = getCell(sheet3, 3+i, 2);
			setText(cell,(String)map.get("ucmCtrGbn"));
			
			cell = getCell(sheet3, 3+i, 3);
			setText(cell,(String)map.get("ucmGolNo"));
			
			cell = getCell(sheet3, 3+i, 4);
			setText(cell,(String)map.get("utwTrmCod"));
			
			cell = getCell(sheet3, 3+i, 5);
			setText(cell,(String)map.get("utwWrkId"));
		}
	}

}
