package com.uwo.isms.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public class RISKM003_ETC_EXCEL extends AbstractExcelView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook wb, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
	
		HSSFSheet sheet = wb.createSheet("관리체계");
		HSSFCellStyle cellStyle = wb.createCellStyle();
	
		setText(getCell(sheet, 0, 0), "관리체계");
		setText(getStyleCell(sheet, cellStyle, 2, 0), "위험시나리오코드");
		setText(getStyleCell(sheet, cellStyle, 2, 1), "위험시나리오");
		setText(getStyleCell(sheet, cellStyle, 2, 2), "우려사항 코드");
		setText(getStyleCell(sheet, cellStyle, 2, 3), "위험진단결과");
		setText(getStyleCell(sheet, cellStyle, 2, 4), "위험처리");
		setText(getStyleCell(sheet, cellStyle, 2, 5), "보호대책");
		setText(getStyleCell(sheet, cellStyle, 2, 6), "대책제약사항");
		setText(getStyleCell(sheet, cellStyle, 2, 7), "조치결과");
		
		HSSFSheet sheet2 = wb.createSheet("관리체계 위협목록");
		HSSFCellStyle cellStyle2 = wb.createCellStyle();
		setText(getCell(sheet2, 0, 0), "관리체계 위협목록");
		setText(getStyleCell(sheet2, cellStyle2, 2, 0), "분류");
		setText(getStyleCell(sheet2, cellStyle2, 2, 1), "T_code");
		setText(getStyleCell(sheet2, cellStyle2, 2, 2), "위협항목");
		setText(getStyleCell(sheet2, cellStyle2, 2, 3), "내용");
		setText(getStyleCell(sheet2, cellStyle2, 2, 4), "위협강도");
		setText(getStyleCell(sheet2, cellStyle2, 2, 5), "발생빈도");
		setText(getStyleCell(sheet2, cellStyle2, 2, 6), "위협강도평가");
		setText(getStyleCell(sheet2, cellStyle2, 2, 7), "발생빈도평가");
		setText(getStyleCell(sheet2, cellStyle2, 2, 8), "위협평가점수표");
		setText(getStyleCell(sheet2, cellStyle2, 2, 9), "위협등급");
		setText(getStyleCell(sheet2, cellStyle2, 2, 10), "위협등급점수");
		
		List<?> list2 = (List<?>)model.get("sheet2List");
		for(int i = 0; i< list2.size();i++){
			EgovMap map = (EgovMap)list2.get(i);
			setText(getStyleCell(sheet2, cellStyle2, 3+i, 0),(String)map.get("usoAssCat"));
			setText(getStyleCell(sheet2, cellStyle2, 3+i, 1),(String)map.get("usoCocCod"));
			setText(getStyleCell(sheet2, cellStyle2, 3+i, 2),(String)map.get("usoCocNam"));
			setText(getStyleCell(sheet2, cellStyle2, 3+i, 3),(String)map.get("usoCocDes"));
			setText(getStyleCell(sheet2, cellStyle2, 3+i, 4),(String)map.get("usoCocGrd"));
			setText(getStyleCell(sheet2, cellStyle2, 3+i, 5),(String)map.get("usoFrqGrd"));
			setText(getStyleCell(sheet2, cellStyle2, 3+i, 6),(String)map.get("usoCocPnt"));
			setText(getStyleCell(sheet2, cellStyle2, 3+i, 7),(String)map.get("usoFrqPnt"));
			setText(getStyleCell(sheet2, cellStyle2, 3+i, 8),(String)map.get("usoSumPnt"));
			setText(getStyleCell(sheet2, cellStyle2, 3+i, 9),(String)map.get("usoRskGrd"));
			setText(getStyleCell(sheet2, cellStyle2, 3+i, 10),(String)map.get("usoRskPnt"));
		}
		
		
		HSSFSheet sheet3 = wb.createSheet("위험시나리오");
		HSSFCellStyle cellStyle3 = wb.createCellStyle();
		setText(getCell(sheet3, 0, 0), "위험시나리오");
		setText(getStyleCell(sheet3, cellStyle3, 2, 0), "위험시나리오");
		setText(getStyleCell(sheet3, cellStyle3, 2, 1), "분류");
		setText(getStyleCell(sheet3, cellStyle3, 2, 2), "통제항목");
		setText(getStyleCell(sheet3, cellStyle3, 2, 3), "통제사항");
		setText(getStyleCell(sheet3, cellStyle3, 2, 4), "위험시나리오");
		setText(getStyleCell(sheet3, cellStyle3, 2, 5), "위협코드");
		setText(getStyleCell(sheet3, cellStyle3, 2, 6), "참조코드");
		
		List<?> list3 = (List<?>)model.get("sheet3List");
		for(int i = 0; i< list3.size();i++){
			EgovMap map = (EgovMap)list3.get(i);
			setText(getStyleCell(sheet3, cellStyle3, 3+i, 0),(String)map.get("usmSroCod"));
			setText(getStyleCell(sheet3, cellStyle3, 3+i, 1),(String)map.get("usmSroLv0"));
			setText(getStyleCell(sheet3, cellStyle3, 3+i, 2),(String)map.get("usmSroLv1"));
			setText(getStyleCell(sheet3, cellStyle3, 3+i, 3),(String)map.get("usmSroLv2"));
			setText(getStyleCell(sheet3, cellStyle3, 3+i, 4),(String)map.get("usmSroDes"));
			setText(getStyleCell(sheet3, cellStyle3, 3+i, 5),(String)map.get("usmCocCod"));
			setText(getStyleCell(sheet3, cellStyle3, 3+i, 6),(String)map.get("usmRefCod"));
			
		}
		
	}
	
	
	public HSSFCell getStyleCell(HSSFSheet sheet, HSSFCellStyle cellStyle, int row, int col){
		HSSFCell cell = getCell(sheet, row, col);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);   
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);   
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);   
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); 
		cell.setCellType(Cell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		return cell;
	}

}
