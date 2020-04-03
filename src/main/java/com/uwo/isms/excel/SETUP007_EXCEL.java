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

public class SETUP007_EXCEL extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook wb, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HSSFFont font = null;

		// th(필수)
		font = wb.createFont();
		font.setFontHeightInPoints((short)10);
	    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	    HSSFCellStyle styleTh = wb.createCellStyle();
	    styleTh.setFillForegroundColor(HSSFColor.ORANGE.index);
	    styleTh.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    styleTh.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		styleTh.setFont(font);

		// th(선택)
		font = wb.createFont();
		font.setFontHeightInPoints((short)10);
	    font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
	    HSSFCellStyle styleTd = wb.createCellStyle();
	    styleTd.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
	    styleTd.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    styleTd.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		styleTd.setFont(font);

		HSSFCell cell = null;
		HSSFSheet sheet = wb.createSheet("사용자계정 설정");

		int j = 0;
		setText(getStyleCell(sheet, styleTh, 2, j++), "아이디");
		setText(getStyleCell(sheet, styleTh, 2, j++), "이름");
		setText(getStyleCell(sheet, styleTd, 2, j++), "비밀번호");
		setText(getStyleCell(sheet, styleTh, 2, j++), "이메일");
		setText(getStyleCell(sheet, styleTd, 2, j++), "서비스");

		setText(getStyleCell(sheet, styleTd, 2, j++), "팀");
		setText(getStyleCell(sheet, styleTh, 2, j++), "팀코드");
		setText(getStyleCell(sheet, styleTd, 2, j++), "직급");
		setText(getStyleCell(sheet, styleTh, 2, j++), "직급코드");
		setText(getStyleCell(sheet, styleTd, 2, j++), "휴대전화");

		setText(getStyleCell(sheet, styleTd, 2, j++), "일반전화");
		setText(getStyleCell(sheet, styleTh, 2, j++), "담당권한");
		setText(getStyleCell(sheet, styleTd, 2, j++), "결재권한");
		setText(getStyleCell(sheet, styleTd, 2, j++), "실무담당자");
		setText(getStyleCell(sheet, styleTh, 2, j++), "사용여부");

		setText(getStyleCell(sheet, styleTd, 2, j++), "1차결재자");
		setText(getStyleCell(sheet, styleTd, 2, j++), "1차결재자 아이디");
		setText(getStyleCell(sheet, styleTd, 2, j++), "2차결재자");
		setText(getStyleCell(sheet, styleTd, 2, j++), "2차결재자 아이디");
		setText(getStyleCell(sheet, styleTd, 2, j++), "대무자");

		setText(getStyleCell(sheet, styleTd, 2, j++), "대무자 아이디");
		setText(getStyleCell(sheet, styleTd, 2, j++), "대무기간 시작일");
		setText(getStyleCell(sheet, styleTd, 2, j++), "대무기간 종료일");

		// 2018-11-07 s,
		setText(getStyleCell(sheet, styleTd, 2, j++), "등록일");
		setText(getStyleCell(sheet, styleTd, 2, j++), "수정일");

		List<?> list = (List<?>) model.get("list");

		for(int i = 0; i< list.size();i++){
			j = 0;
			EgovMap map = (EgovMap)list.get(i);

			cell = getCell(sheet, 3+i, j++);
			setText(cell,(String)map.get("uumUsrId"));			// 아이디
			cell = getCell(sheet, 3+i, j++);
			setText(cell,(String)map.get("uumUsrNam"));			// 이름
			cell = getCell(sheet, 3+i, j++);
			setText(cell,"");									// 비밀번호
			cell = getCell(sheet, 3+i, j++);
			setText(cell,(String)map.get("uumMalAds"));			// 이메일
			cell = getCell(sheet, 3+i, j++);
			setText(cell,(String)map.get("uumDivCod"));			// 서비스

			cell = getCell(sheet, 3+i, j++);
			setText(cell,(String)map.get("uumDepNam"));			// 팀
			cell = getCell(sheet, 3+i, j++);
			setText(cell,(String)map.get("uumDepCod"));			// 팀코드
			cell = getCell(sheet, 3+i, j++);
			setText(cell,(String)map.get("uumPosCodNam"));		// 직급
			cell = getCell(sheet, 3+i, j++);
			setText(cell,(String)map.get("uumPosCod"));			// 직급코드
			cell = getCell(sheet, 3+i, j++);
			setText(cell,(String)map.get("uumCelNum"));			// 휴대전화

			cell = getCell(sheet, 3+i, j++);
			setText(cell,(String)map.get("uumTelNum"));			// 일반전화
			cell = getCell(sheet, 3+i, j++);
			setText(cell,(String)map.get("uatAuhNam"));			// 담당권한
			cell = getCell(sheet, 3+i, j++);
			setText(cell,(String)map.get("uumApvGbn"));			// 결재권한
			cell = getCell(sheet, 3+i, j++);
			setText(cell,(String)map.get("uumTraGbn"));			// 이관담당권한
			cell = getCell(sheet, 3+i, j++);
			setText(cell,(String)map.get("useYnNam"));			// 사용여부

			cell = getCell(sheet, 3+i, j++);
			setText(cell,(String)map.get("uumApvOneNam"));		// 1차결재자
			cell = getCell(sheet, 3+i, j++);
			setText(cell,(String)map.get("uumApvOne"));			// 1차결재자 아이디
			cell = getCell(sheet, 3+i, j++);
			setText(cell,(String)map.get("uumApvTwoNam"));		// 2차결재자
			cell = getCell(sheet, 3+i, j++);
			setText(cell,(String)map.get("uumApvTwo"));			// 2차결재자 아이디
			cell = getCell(sheet, 3+i, j++);
			setText(cell,(String)map.get("uumAgnNam"));			// 대무자

			cell = getCell(sheet, 3+i, j++);
			setText(cell,(String)map.get("uumAgnId"));			// 대무자 아이디
			cell = getCell(sheet, 3+i, j++);
			setText(cell,(String)map.get("uumAgnStr"));			// 대무기간 시작일
			cell = getCell(sheet, 3+i, j++);
			setText(cell,(String)map.get("uumAgnEnd"));			// 대무기간 종료일

			cell = getCell(sheet, 3+i, j++);
			setText(cell,(String)map.get("uumRgtMdh"));
			cell = getCell(sheet, 3+i, j++);
			setText(cell,(String)map.get("uumUptMdh"));
		}

		// set column width
		EgovMap map = (EgovMap)list.get(0);
		for (int i = 0; i < map.size(); i++) {
			sheet.autoSizeColumn((short)i);
			sheet.setColumnWidth(i, (sheet.getColumnWidth(i)) + 800);
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
