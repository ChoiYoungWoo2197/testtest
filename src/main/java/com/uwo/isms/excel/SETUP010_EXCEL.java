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

public class SETUP010_EXCEL extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook wb, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HSSFCell cell = null;
		HSSFSheet sheet = wb.createSheet("로그인 이력");

		setText(getCell(sheet, 2, 0), "이름");
		setText(getCell(sheet, 2, 1), "로그인IP");
		setText(getCell(sheet, 2, 2), "로그인 일시");
		List<?> list = (List<?>) model.get("list");

		for(int i = 0; i< list.size();i++){
			EgovMap map = (EgovMap)list.get(i);
			
			cell = getCell(sheet, 3+i, 0);
			setText(cell,(String)map.get("ulmLinId"));
			
			cell = getCell(sheet, 3+i, 1);
			setText(cell,(String)map.get("ulmLinIp"));
			
			cell = getCell(sheet, 3+i, 2);
			setText(cell,(String)map.get("ulmRgtMdh"));			
		}
	}

}
