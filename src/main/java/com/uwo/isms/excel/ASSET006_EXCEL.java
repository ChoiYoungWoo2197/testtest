package com.uwo.isms.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.uwo.isms.common.UwoStringUtils;
import com.uwo.isms.domain.RiskVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public class ASSET006_EXCEL extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook wb, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HSSFCell cell = null;
		HSSFSheet sheet = wb.createSheet("자산이력");

		sheet.addMergedRegion(new CellRangeAddress(2, 3, 0, 0));
		setText(getCell(sheet, 2, 0), "자산유형");
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 1, 1));
		setText(getCell(sheet, 2, 1), "서비스명");
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 2, 2));
		setText(getCell(sheet, 2, 2), "부서명");
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 3, 3));
		setText(getCell(sheet, 2, 3), "부서명");
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 4, 4));
		setText(getCell(sheet, 2, 4), "자산(장비)명");
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 5, 5));
		setText(getCell(sheet, 2, 5), "제조사");
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 6, 6));
		setText(getCell(sheet, 2, 6), "모델명");
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 7, 7));
		setText(getCell(sheet, 2, 7), "용도");
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 8, 8));
		setText(getCell(sheet, 2, 8), "관리자");
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 9, 9));
		setText(getCell(sheet, 2, 9), "운용자");
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 10, 10));
		setText(getCell(sheet, 2, 10), "책임자");
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 11, 13));
		setText(getCell(sheet, 2, 11), "평가항목");
		setText(getCell(sheet, 3, 11), "기밀성(C)");
		setText(getCell(sheet, 3, 12), "무결성(I)");
		setText(getCell(sheet, 3, 13), "가용성(A)");
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 14, 15));
		setText(getCell(sheet, 2, 14), "중요도");		
		setText(getCell(sheet, 3, 14), "점수");
		setText(getCell(sheet, 3, 15), "등급");
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 16, 16));
		setText(getCell(sheet, 2, 16), "인증대상");
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 17, 17));
		setText(getCell(sheet, 2, 17), "샘플링대상");
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 18, 18));
		setText(getCell(sheet, 2, 18), "가변필드1");
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 19, 19));
		setText(getCell(sheet, 2, 19), "가변필드2");
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 20, 20));
		setText(getCell(sheet, 2, 20), "가변필드3");
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 21, 21));
		setText(getCell(sheet, 2, 21), "가변필드4");
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 22, 22));
		setText(getCell(sheet, 2, 22), "가변필드5");
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 23, 23));
		setText(getCell(sheet, 2, 23), "가변필드6");
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 24, 24));
		setText(getCell(sheet, 2, 24), "가변필드7");
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 25, 25));
		setText(getCell(sheet, 2, 25), "가변필드8");
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 26, 26));
		setText(getCell(sheet, 2, 26), "가변필드9");
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 27, 27));
		setText(getCell(sheet, 2, 27), "가변필드10");
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 28, 28));
		setText(getCell(sheet, 2, 28), "수정자");
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 29, 29));
		setText(getCell(sheet, 2, 29), "수정일");
		
		
		List<?> list = (List<?>)model.get("list");
		
		for(int i = 0; i< list.size();i++){
			EgovMap map = (EgovMap)list.get(i);
			
			cell = getCell(sheet, 4+i, 0);
			setText(cell, UwoStringUtils.objToStr(map.get("uacCatNam")));
			
			cell = getCell(sheet, 4+i, 1);
			setText(cell,  UwoStringUtils.objToStr(map.get("uaoSvrNam")));
			
			cell = getCell(sheet, 4+i, 2);
			setText(cell, UwoStringUtils.objToStr(map.get("uaoDepNam")));
			
			cell = getCell(sheet, 4+i, 3);
			setText(cell, UwoStringUtils.objToStr(map.get("uaoEqpNam")));
			
			cell = getCell(sheet, 4+i, 4);
			setText(cell, UwoStringUtils.objToStr(map.get("uaoAosNam")));
			
			cell = getCell(sheet, 4+i, 5);
			setText(cell, UwoStringUtils.objToStr(map.get("uaoMakNam")));
			
			cell = getCell(sheet, 4+i, 6);
			setText(cell, UwoStringUtils.objToStr(map.get("uaoDtlNam")));
			
			cell = getCell(sheet, 4+i, 7);
			setText(cell, UwoStringUtils.objToStr(UwoStringUtils.objToStr(map.get("uaoDtlExp"))));
			
			cell = getCell(sheet, 4+i, 8);
			setText(cell, UwoStringUtils.objToStr(map.get("uaoUseNam")));
			
			cell = getCell(sheet, 4+i, 9);
			setText(cell, UwoStringUtils.objToStr(map.get("uaoAdmNam")));
			
			cell = getCell(sheet, 4+i, 10);
			setText(cell, UwoStringUtils.objToStr(map.get("uaoOwnNam")));
			
			cell = getCell(sheet, 4+i, 11);
			setText(cell, UwoStringUtils.objToStr(map.get("uaoAdmPos")));
			
			cell = getCell(sheet, 4+i, 12);
			setText(cell, UwoStringUtils.objToStr(map.get("uaoAppCon")));
			
			cell = getCell(sheet, 4+i, 13);
			setText(cell, UwoStringUtils.objToStr(map.get("uaoAppItg")));
			
			cell = getCell(sheet, 4+i, 14);
			setText(cell, UwoStringUtils.objToStr(map.get("uaoAppAvt")));
			
			cell = getCell(sheet, 4+i, 15);
			setText(cell, UwoStringUtils.objToStr(map.get("uaoAssLvl")));
			
			cell = getCell(sheet, 4+i, 16);
			setText(cell, UwoStringUtils.objToStr(map.get("uaoAudYn")));
			
			cell = getCell(sheet, 4+i, 17);
			setText(cell, UwoStringUtils.objToStr(map.get("uaoSmpYn")));
			
			cell = getCell(sheet, 4+i, 18);
			setText(cell, UwoStringUtils.objToStr(map.get("uaoValCl0")));
			
			cell = getCell(sheet, 4+i, 19);
			setText(cell, UwoStringUtils.objToStr(map.get("uaoValCl1")));
			
			cell = getCell(sheet, 4+i, 20);
			setText(cell, UwoStringUtils.objToStr(map.get("uaoValCl2")));
			
			cell = getCell(sheet, 4+i, 21);
			setText(cell, UwoStringUtils.objToStr(map.get("uaoValCl3")));
			
			cell = getCell(sheet, 4+i, 22);
			setText(cell, UwoStringUtils.objToStr(map.get("uaoValCl4")));
			
			cell = getCell(sheet, 4+i, 23);
			setText(cell, UwoStringUtils.objToStr(map.get("uaoValCl5")));
			
			cell = getCell(sheet, 4+i, 24);
			setText(cell, UwoStringUtils.objToStr(map.get("uaoValCl6")));
			
			cell = getCell(sheet, 4+i, 25);
			setText(cell, UwoStringUtils.objToStr(map.get("uaoValCl7")));
			
			cell = getCell(sheet, 4+i, 26);
			setText(cell, UwoStringUtils.objToStr(map.get("uaoValCl8")));
			
			cell = getCell(sheet, 4+i, 27);
			setText(cell, UwoStringUtils.objToStr(map.get("uaoValCl9")));
			
			cell = getCell(sheet, 4+i, 28);
			setText(cell, UwoStringUtils.objToStr(map.get("uaoRgtId")));
			
			cell = getCell(sheet, 4+i, 29);
			setText(cell, UwoStringUtils.objToStr(map.get("uaoRgtMdh")));
		}
	}

}
