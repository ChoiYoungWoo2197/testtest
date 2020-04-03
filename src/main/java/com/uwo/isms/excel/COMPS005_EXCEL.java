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

public class COMPS005_EXCEL extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook wb, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HSSFCell cell = null;
		HSSFSheet sheet = wb.createSheet("통제항목설정");

		setText(getCell(sheet, 2, 0), "표준구분");
		setText(getCell(sheet, 2, 1), "1레벨");
		setText(getCell(sheet, 2, 2), "1레벨설명");
		setText(getCell(sheet, 2, 3), "2레벨");
		setText(getCell(sheet, 2, 4), "2레벨설명");
		setText(getCell(sheet, 2, 5), "2레벨세부설명");
		setText(getCell(sheet, 2, 6), "3레벨");
		setText(getCell(sheet, 2, 7), "3레벨설명");
		setText(getCell(sheet, 2, 8), "3레벨세부설명");		
		setText(getCell(sheet, 2, 9), "1레벨미사용여부");
		setText(getCell(sheet, 2, 10), "2레벨미사용여부");
		setText(getCell(sheet, 2, 11), "3레벨미사용여부");

		List<?> list = (List<?>) model.get("list");

		for(int i = 0; i< list.size();i++){
			EgovMap map = (EgovMap)list.get(i);
			
			cell = getCell(sheet, 3+i, 0);
			setText(cell,(String)map.get("ucmCtrGbn"));
			
			cell = getCell(sheet, 3+i, 1);
			setText(cell,(String)map.get("ucm1lvCod"));
			
			cell = getCell(sheet, 3+i, 2);
			setText(cell,(String)map.get("ucm1lvNam"));
			
			cell = getCell(sheet, 3+i, 3);
			setText(cell,(String)map.get("ucm2lvCod"));
			
			cell = getCell(sheet, 3+i, 4);
			setText(cell,(String)map.get("ucm2lvNam"));
			
			cell = getCell(sheet, 3+i, 5);
			setText(cell,(String)map.get("ucm2lvDtl"));
			
			cell = getCell(sheet, 3+i, 6);
			setText(cell,(String)map.get("ucm3lvCod"));
			
			cell = getCell(sheet, 3+i, 7);
			setText(cell,(String)map.get("ucm3lvNam"));
			
			cell = getCell(sheet, 3+i, 8);
			setText(cell,(String)map.get("ucm3lvDtl"));
			
			cell = getCell(sheet, 3+i, 9);
			setText(cell,(String)map.get("ucm1ldYn"));
			
			cell = getCell(sheet, 3+i, 10);
			setText(cell,(String)map.get("ucm2ldYn"));
			
			cell = getCell(sheet, 3+i, 11);
			setText(cell,(String)map.get("ucm3ldYn"));
		}
	}

}
