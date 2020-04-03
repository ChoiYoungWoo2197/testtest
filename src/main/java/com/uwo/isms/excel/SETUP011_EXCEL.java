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

public class SETUP011_EXCEL extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook wb, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HSSFCell cell = null;
		HSSFSheet sheet = wb.createSheet("계정 이력");

		setText(getCell(sheet, 2, 0), "ID");
		setText(getCell(sheet, 2, 1), "이름");
		setText(getCell(sheet, 2, 2), "부서");
		setText(getCell(sheet, 2, 3), "직급");
		setText(getCell(sheet, 2, 4), "사번");
		setText(getCell(sheet, 2, 5), "이메일");
		setText(getCell(sheet, 2, 6), "자리번호");
		setText(getCell(sheet, 2, 7), "휴대폰번호");
		List<?> list = (List<?>) model.get("list");

		for(int i = 0; i< list.size();i++){
			EgovMap map = (EgovMap)list.get(i);
			
			cell = getCell(sheet, 3+i, 0);
			setText(cell,(String)map.get("uulUsrId"));
			
			cell = getCell(sheet, 3+i, 1);
			setText(cell,(String)map.get("uulUsrNam"));
			
			cell = getCell(sheet, 3+i, 2);
			setText(cell,(String)map.get("uulDepCod"));
			
			cell = getCell(sheet, 3+i, 3);
			setText(cell,(String)map.get("uulMalAds"));
			
			cell = getCell(sheet, 3+i, 4);
			setText(cell,(String)map.get("uulPosCod"));
			
			cell = getCell(sheet, 3+i, 5);
			setText(cell,(String)map.get("uulUsrNum"));
			
			cell = getCell(sheet, 3+i, 6);
			setText(cell,(String)map.get("uulTelNum"));
			
			cell = getCell(sheet, 3+i, 7);
			setText(cell,(String)map.get("uulCelNum"));
		}
	}

}
