package com.uwo.isms.excel;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.uwo.isms.domain.RiskVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@SuppressWarnings("deprecation")
public class ASSET001_REPORT_EXCEL extends AbstractExcelView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook wb, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		List<?> infoList = (List<?>)model.get("infoList");
		List<?> assList = (List<?>)model.get("assList");

		String[] colNames = {"구분", "자산상세 내역", "관리형태", "보안요구사항", "보안\n영향도"};
		String[][] colModelLast = {
				{"기밀성", "무결성", "가용성", "등급"},
				{"getUar_app_con", "getUar_app_itg", "getUar_app_avt", "getSec_lvl_tot"}
			};
		String[][][] colModel = {
				{	// 1. 전자정보(DB)
					{"1", "6", "4", "3", "1"},
					{"자산코드", "종류", "DBMS명\n(인스턴스단위)", "설치 서버\n호스트명", "설치 서버\nIP주소", "서비스분류", "용도\n(목적 및 기능)",
					 "관리부서", "운용자", "책임자(팀장)", "위치\n(상세위치)"},
					{"getUar_ass_cod", "getUar_ass_nam", "getUar_eqp_nam", "getUar_host", "getUar_ip", "getUar_sub_nam", "getUar_dtl_exp",
					 "getUar_dep_nam", "getUar_own_nam", "getUar_adm_nam", "getUar_adm_pos"}
				},
				{	// 2. 서버
					{"1", "9", "4", "3", "1"},
					{"자산코드", "자산명\n(호스트명)", "제조사", "모델명", "OS", "OS버전", "IP", "서비스분류", "용도\n(목적 및 기능)", "웹사이트 운용\n(도메인)",
					 "관리부서", "운용자", "책임자(팀장)", "위치\n(상세위치)"},
					{"getUar_ass_cod", "getUar_eqp_nam", "getUar_val_cl0", "getUar_val_cl1", "getUar_os", "getUar_val_cl2", "getUar_ip", "getUar_sub_nam", "getUar_dtl_exp", "getUar_val_cl5",
					 "getUar_dep_nam", "getUar_own_nam", "getUar_adm_nam", "getUar_adm_pos"}
				},
				{	 // 3. 네트워크
					{"1", "7", "4", "3", "1"},
					{"자산코드", "자산명\n(호스트명)", "제조사", "모델명", "OS버전", "IP", "서비스분류", "용도\n(목적 및 기능)",
					 "관리부서", "운용자", "책임자(팀장)", "위치\n(상세위치)"},
					{"getUar_ass_cod", "getUar_eqp_nam", "getUar_val_cl0", "getUar_val_cl1", "getUar_val_cl2", "getUar_ip", "getUar_sub_nam", "getUar_dtl_exp",
					 "getUar_dep_nam", "getUar_own_nam", "getUar_adm_nam", "getUar_adm_pos"}
				},
				{	 // 4. 정보보호시스템
					{"1", "6", "4", "3", "1"},
					{"자산코드", "자산명\n(호스트명)", "제조사", "모델명", "IP", "서비스분류", "용도\n(목적 및 기능)",
					 "관리부서", "운용자", "책임자(팀장)", "위치\n(상세위치)"},
					{"getUar_ass_cod", "getUar_eqp_nam", "getUar_val_cl0", "getUar_val_cl1", "getUar_ip", "getUar_sub_nam", "getUar_dtl_exp",
					 "getUar_dep_nam", "getUar_own_nam", "getUar_adm_nam", "getUar_adm_pos"}
				},
				{	 // 5. 문서
					{"1", "4", "4", "3", "1"},
					{"자산코드", "문서번호", "문서명", "보존연한", "용도\n(목적 및 기능)",
					 "관리부서", "관리자", "책임자(팀장)", "위치\n(상세위치)"},
					{"getUar_ass_cod", "getUar_val_cl2", "getUar_eqp_nam", "getUar_val_cl1", "getUar_dtl_exp",
					 "getUar_dep_nam", "getUar_own_nam", "getUar_adm_nam", "getUar_adm_pos"}
				},
				{	// 6. 소프트웨어
					{"1", "3", "4", "3", "1"},
					{"자산코드", "자산명", "수량", "용도\n(목적 및 기능)",
					 "관리부서", "관리자", "책임자(팀장)", "위치\n(상세위치)"},
					{"getUar_ass_cod", "getUar_eqp_nam", "getUar_val_cl2", "getUar_dtl_exp",
					 "getUar_dep_nam", "getUar_own_nam", "getUar_adm_nam", "getUar_adm_pos"}
				},
				{	// 7. 단말기
					{"1", "5", "5", "3", "1"},
					{"자산코드", "자산명\n(호스트명)", "제조사", "모델명", "OS", "용도\n(목적 및 기능)",
					 "소속팀", "사용자\n(소속/명)", "관리자", "책임자(팀장)", "위치\n(상세위치)"},
					{"getUar_ass_cod", "getUar_eqp_nam", "getUar_val_cl0", "getUar_val_cl1", "getUar_os", "getUar_dtl_exp",
					 "getUar_dep_nam", "getUar_opr_etc", "getUar_own_nam", "getUar_adm_nam", "getUar_adm_pos"}
				},
				{	// 8. 물리적 자산
					{"1", "3", "4", "3", "1"},
					{"자산코드", "자산명", "수량", "용도\n(목적 및 기능)",
					 "관리부서", "관리자", "책임자(팀장)", "위치\n(상세위치)"},
					{"getUar_ass_cod", "getUar_eqp_nam", "getUar_val_cl1", "getUar_dtl_exp",
					 "getUar_dep_nam", "getUar_own_nam", "getUar_adm_nam", "getUar_adm_pos"}
				},
				{	// 9. 저장장치
					{"1", "7", "4", "3", "1"},
					{"자산코드", "자산명\n(호스트명)", "제조사", "모델명", "OS버전", "IP", "서비스분류", "용도\n(목적 및 기능)",
					 "관리부서", "운용자", "책임자(팀장)", "위치\n(상세위치)"},
					{"getUar_ass_cod", "getUar_eqp_nam", "getUar_val_cl0", "getUar_val_cl1", "getUar_val_cl2", "getUar_ip", "getUar_sub_nam", "getUar_dtl_exp",
					 "getUar_dep_nam", "getUar_own_nam", "getUar_adm_nam", "getUar_adm_pos"}
				}
			};

		HSSFSheet sheet = null;
		HSSFPrintSetup ps = null;
		HSSFFont font = null;
		int row = 0;
		int col = 0;

		// cover
		HSSFCellStyle styleCover = wb.createCellStyle();
		font = wb.createFont();
		font.setFontHeightInPoints((short)36);
	    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	    styleCover.setFont(font);
	    styleCover.setAlignment(HSSFCellStyle.ALIGN_CENTER);

	    // cover footer
	    HSSFCellStyle styleCoverFooter = wb.createCellStyle();
		font = wb.createFont();
		font.setFontHeightInPoints((short)20);
	    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	    styleCoverFooter.setFont(font);
	    styleCoverFooter.setAlignment(HSSFCellStyle.ALIGN_CENTER);


		// title
		font = wb.createFont();
		font.setFontHeightInPoints((short)16);
	    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	    HSSFCellStyle styleTitle = wb.createCellStyle();
	    styleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		styleTitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		styleTitle.setFont(font);

		// th
		font = wb.createFont();
		font.setFontHeightInPoints((short)10);
	    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	    HSSFCellStyle styleTh = wb.createCellStyle();
	    styleTh.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		styleTh.setFont(font);
		// td
		font = wb.createFont();
		font.setFontHeightInPoints((short)10);
	    HSSFCellStyle styleTd = wb.createCellStyle();
	    styleTd.setFont(font);

		// header
		font = wb.createFont();
		font.setFontHeightInPoints((short)9);
	    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		HSSFCellStyle styleH1 = wb.createCellStyle();
		styleH1.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		styleH1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		styleH1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    styleH1.setFont(font);
	    // header2
	    HSSFCellStyle styleH2 = wb.createCellStyle();
		styleH2.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
		styleH2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		styleH2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    styleH2.setFont(font);

	    // body
	    font = wb.createFont();
	    HSSFCellStyle styleBody = wb.createCellStyle();
	    font.setFontHeightInPoints((short)9);
	    styleBody.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    styleBody.setFont(font);


	    // Cover
		sheet = wb.createSheet("Cover");
		sheet.setDisplayGridlines(false);
		// 인쇄시 A4 Landscape, 한페이지에 모든열 맞추기
		ps = sheet.getPrintSetup();
		ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
		ps.setLandscape(true);
		ps.setFitHeight((short)0);
		ps.setFitWidth((short)1);
		// Cover title
		col = 13;
		row = 10;
		sheet.addMergedRegion(new Region(row, (short)0, row, (short)col));
		getCell(sheet, row, 0).setCellStyle(styleCover);
		setText(getCell(sheet, row, 0), "자산관리대장");
		row = 15;
		sheet.addMergedRegion(new Region(row, (short)0, row, (short)col));
		getCell(sheet, row, 0).setCellStyle(styleCoverFooter);
		setText(getCell(sheet, row, 0), "SK broadband");
		// Cover date
	    Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy. MM. dd");
		String currentDate = sdf.format(cal.getTime());
		row = 25;
		sheet.addMergedRegion(new Region(row, (short)0, row, (short)col));
		getCell(sheet, row, 0).setCellStyle(styleCoverFooter);
		setText(getCell(sheet, row, 0), currentDate);


		for (int cnt = 0; cnt < infoList.size(); cnt++) {

			if (cnt >= colModel.length) {
				break;
			}

			EgovMap info = (EgovMap)infoList.get(cnt);
			List<?> list = (List<?>)assList.get(cnt);

			row = 0;
			col = 0;
			String catName = info.get("uacCatNam").toString();
			String sheetName =  String.valueOf(cnt + 1) + ". " + catName;

    		sheet = wb.createSheet(sheetName);
    		sheet.setDisplayGridlines(false);
    		sheet.setAutobreaks(true);
    		sheet.setDefaultRowHeight((short)520);

    		// 인쇄시 A4 Landscape, 한페이지에 모든열 맞추기
    		ps = sheet.getPrintSetup();
    		ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
    		ps.setLandscape(true);
    		ps.setFitHeight((short)0);
    		ps.setFitWidth((short)1);

    		// column 합
    		int colLength = colModel[cnt][1].length + colModelLast[1].length;

    		// title
    		col = 0;
    		int colTitleSize = colLength - 1;
    		sheet.addMergedRegion(new Region(row, (short)col, row, (short)colTitleSize));
			getCell(sheet, row, col).setCellStyle(styleTitle);
			setText(getCell(sheet, row, col), catName + " 목록 및 중요도 평가");
    		row++;

    		// 자산 분류
    		col = 0;
    		int colSummarySize = 7;
    		setText(getStyleCell(sheet, styleTh, row, col), "자산분류");
    		col++;
    		sheet.addMergedRegion(new Region(row, (short)col, row, (short)colSummarySize));
    		for (int i = col; i < col + colSummarySize; i++) {
    			setText(getStyleCell(sheet, styleTh, row, i), null);
    		}
    		setText(getStyleCell(sheet, styleTd, row, col), catName);
    		row++;

    		// 자산 설명
    		col = 0;
    		setText(getStyleCell(sheet, styleTh, row, col), "Description");
    		col++;
    		sheet.addMergedRegion(new Region(row, (short)col, row, (short)colSummarySize));
    		for (int i = col; i < col + colSummarySize; i++) {
    			setText(getStyleCell(sheet, styleTh, row, i), null);
    		}
    		setText(getStyleCell(sheet, styleTd, row, col), (String)info.get("uacCatDes"));
    		row = row + 2;

    		// column header style 적용
    		for (int i = 0; i < colLength; i++) {
    			setText(getStyleCell(sheet, styleH1, row, i), null);
    		}

    		// column header merge & set value
    		col = 0;
    		int firstCol = 0;
    		int lastCol = 0;
    		for (int i = 0; i < colNames.length; i++) {
    			lastCol = firstCol + Integer.parseInt(colModel[cnt][0][i]) - 1;
    			sheet.addMergedRegion(new Region(row, (short)firstCol, row, (short)lastCol));
    			setText(getStyleCell(sheet, styleH1, row, firstCol), colNames[i]);
    			firstCol = lastCol + 1;
    		}
    		row++;

    		// column name
			col = 0;
    		for (int i = 0; i < colModel[cnt][1].length; i++) {
    			setText(getStyleCell(sheet, styleH2, row, col), colModel[cnt][1][i]);
    			col++;
    		}
    		// last column name
    		for (int i = 0; i < colModelLast[0].length; i++) {
    			setText(getStyleCell(sheet, styleH2, row, col), colModelLast[0][i]);
    			col++;
    		}
    		row++;

    		for (int i = 0; i < list.size(); i++) {
    			RiskVO vo = (RiskVO)list.get(i);
    			// column data
    			col = 0;
    			for (int j = 0; j < colModel[cnt][2].length; j++) {
    				Method method = vo.getClass().getDeclaredMethod(colModel[cnt][2][col]);
        			setText(getStyleCell(sheet, styleBody, row, col), (String)method.invoke(vo));
        			col++;
    			}
    			// last cloumn data
    			for (int j = 0; j < colModelLast[1].length; j++) {
    				Method method = vo.getClass().getDeclaredMethod(colModelLast[1][j]);
    				setText(getStyleCell(sheet, styleBody, row, col), (String)method.invoke(vo));
    				col++;
        		}
    			row++;
    		}

    		// set column width
    		for (int i = 0; i < colLength; i++) {
    			sheet.autoSizeColumn((short)i);
    			sheet.setColumnWidth(i, (sheet.getColumnWidth(i)) + 600);
			}
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
