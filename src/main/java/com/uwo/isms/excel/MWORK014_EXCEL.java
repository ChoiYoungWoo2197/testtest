package com.uwo.isms.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public class MWORK014_EXCEL extends AbstractExcelView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook wb, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		int row = 0;
		int col = 0;

		// header
		HSSFFont font = null;
		font = wb.createFont();
	    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		HSSFCellStyle styleTh = wb.createCellStyle();
		styleTh.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		styleTh.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		styleTh.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		styleTh.setFont(font);
		// td
		font = wb.createFont();
	    HSSFCellStyle styleTd = wb.createCellStyle();
	    styleTd.setFont(font);

		HSSFCell cell = null;
		HSSFSheet sheet = wb.createSheet("미완료 업무");

		row = 2;
		col = 0;

		setText(getStyleCell(sheet, styleTh, row, col++), "본부");
		setText(getStyleCell(sheet, styleTh, row, col++), "그룹,담당");
		setText(getStyleCell(sheet, styleTh, row, col++), "팀");
		setText(getStyleCell(sheet, styleTh, row, col++), "담당자");
		setText(getStyleCell(sheet, styleTh, row, col++), "총건수");
		setText(getStyleCell(sheet, styleTh, row, col++), "미완료");
		setText(getStyleCell(sheet, styleTh, row, col++), "완료");

		List<?> list = (List<?>)model.get("list");

		row = 3;
		for(int i = 0; i< list.size();i++){
			EgovMap map = (EgovMap)list.get(i);
			col = 0;

			String depFull = (String)map.get("udmDepFul");
			String[] depName = depFull.split("\\^");

			int k = 0;
			for (; k < 3 && k < depName.length; k++) {
				setText(getStyleCell(sheet, styleTd, row, col++), depName[k]);
			}

			for (; k < 3; k++) {
				setText(getStyleCell(sheet, styleTd, row, col++), "");
			}

			setText(getStyleCell(sheet, styleTd, row, col++), (String) map.get("wrkNm"));
			setText(getStyleCell(sheet, styleTd, row, col++), String.valueOf(map.get("allworkcnt")));
			setText(getStyleCell(sheet, styleTd, row, col++), String.valueOf(map.get("noworkcnt")));
			setText(getStyleCell(sheet, styleTd, row, col++), String.valueOf(map.get("compcnt")));
			row++;
		}

		// set width
		for (int i = 0; i < list.size(); i++) {
			sheet.autoSizeColumn((short)i);
			sheet.setColumnWidth(i, (sheet.getColumnWidth(i)) + 1500);
		}
	}

	public HSSFCell getStyleCell(HSSFSheet sheet, HSSFCellStyle cellStyle, int row, int col) {
		HSSFCell cell = getCell(sheet, row, col);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cell.setCellType(Cell.CELL_TYPE_STRING);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyle.setWrapText(true);
		cell.setCellStyle(cellStyle);
		return cell;
	}
}
