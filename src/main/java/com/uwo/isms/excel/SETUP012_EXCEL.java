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

public class SETUP012_EXCEL extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook wb, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HSSFCell cell = null;
		HSSFSheet sheet = wb.createSheet("공휴일관리");

		setText(getCell(sheet, 2, 0), "일자");
		setText(getCell(sheet, 2, 1), "휴일구분");
		setText(getCell(sheet, 2, 2), "사용자 지정 휴일 내역");

		List<?> ymdList = (List<?>) model.get("ymdList");

		for (int i = 0; i < ymdList.size(); i++) {
			EgovMap map = (EgovMap) ymdList.get(i);

			String uwyWrkYmd = (String) map.get("uwyWrkYmd");
			String uwyWrkYn = (String) map.get("uwyWrkYn");
			String uwyEtc = (String) map.get("uwyEtc");
			String uwyDayWek = (String) map.get("uwyDayWek");
			
			
			cell = getCell(sheet, 3 + i, 0);
			setText(cell, uwyWrkYmd);
			
			cell = getCell(sheet, 3 + i, 1);
			if(uwyWrkYn.equals("N")){
				setText(cell, "업무일");
			}else if(uwyWrkYn.equals("Y")){
				setText(cell, "휴일");
				if(uwyDayWek.equals("1") || uwyDayWek.equals("7")){
					//setText(cell, "지정공휴일");
				}else{
					cell = getCell(sheet, 3+i, 2);
					setText(cell, uwyEtc);
				}
			}
		}
	}

}
