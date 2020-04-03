package com.uwo.isms.excel;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.uwo.isms.dao.ExcelDAO;

public class RISKM010_REPORT_EXCEL extends AbstractExcelView{

	@Resource(name="excelDAO")
    private ExcelDAO excelDAO;
	
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook wb, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, String> map = (Map<String, String>)model.get("paramMap");
		
		HSSFSheet sheet = wb.createSheet("정보보호 관리체계");
		HSSFCellStyle cellStyle = wb.createCellStyle(); 
		
		setText(getCell(sheet, 0, 0),"정보보호 관리체계 위험조치계획서");
		setText(getStyleCell(sheet, cellStyle, 2, 0), "NO");
		setText(getStyleCell(sheet, cellStyle, 2, 1), "Concern Code");
		setText(getStyleCell(sheet, cellStyle, 2, 2), "Concern");
		setText(getStyleCell(sheet, cellStyle, 2, 3), "취약점 내용");
		setText(getStyleCell(sheet, cellStyle, 2, 4), "개선방안 및 보호대책");
		setText(getStyleCell(sheet, cellStyle, 2, 5), "위험도");
		setText(getStyleCell(sheet, cellStyle, 2, 6), "위험처리");
		setText(getStyleCell(sheet, cellStyle, 2, 7), "조치예정일(시기)");
		setText(getStyleCell(sheet, cellStyle, 2, 8), "추진부서");
		setText(getStyleCell(sheet, cellStyle, 2, 9), "제약사항");
		
		List<Map<String, String>> reportList = excelDAO.riskm010_report_down(map);
		int rowCnt = 3;
		int cnt = 1;
		for(Map<String, String> reportMap : reportList){
			setText(getStyleCell(sheet, cellStyle, rowCnt, 0), String.valueOf(cnt++));
			setText(getStyleCell(sheet, cellStyle, rowCnt, 1), reportMap.get("usoCocCod"));
			setText(getStyleCell(sheet, cellStyle, rowCnt, 2), reportMap.get("usoCocDes"));
			setText(getStyleCell(sheet, cellStyle, rowCnt, 3), reportMap.get("urvVlbDes"));
			setText(getStyleCell(sheet, cellStyle, rowCnt, 4), reportMap.get("ursMesDes"));
			setText(getStyleCell(sheet, cellStyle, rowCnt, 5), reportMap.get("usoRskGrdChg"));
			setText(getStyleCell(sheet, cellStyle, rowCnt, 6), reportMap.get("rsk"));
			setText(getStyleCell(sheet, cellStyle, rowCnt, 7), reportMap.get("ursReqMdh"));
			setText(getStyleCell(sheet, cellStyle, rowCnt, 8), reportMap.get("mngDepNam"));
			setText(getStyleCell(sheet, cellStyle, rowCnt, 9), reportMap.get("ursLimDes"));
			rowCnt++;
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
