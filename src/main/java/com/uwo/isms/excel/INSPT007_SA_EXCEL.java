package com.uwo.isms.excel;

import com.uwo.isms.domain.WorkVO;
import com.uwo.isms.util.ExcelUtil;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.math.BigDecimal;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class INSPT007_SA_EXCEL extends AbstractExcelView{

	private final static int CTR_KEY_INDEX = 0;
	private final static int TRC_KEY_INDEX = 1;
	private final static int WRK_KEY_INDEX = 2;

	private final static int LV2_COD_INDEX = 3;
	private final static int LV2_NAM_INDEX = 4;
	private final static int LV3_COD_INDEX = 5;
	private final static int WRK_PRG_INDEX = 6;
	private final static int LV3_NAM_INDEX = 7;
	private final static int DOC_NAM_INDEX = 8;
	private final static int DEP_NAM_INDEX = 9;
	private final static int USR_NAM_INDEX = 10;
	private final static int WRK_DTL_INDEX = 11;


	private final static int HEADER_ROW_INDEX_STARTED_AT = 0;
	private final static int BODY_ROW_INDEX_STARTED_AT = 1;

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
									  HSSFWorkbook workbook, HttpServletRequest request,
									  HttpServletResponse response) throws Exception {

		String bcyCode = (String) model.get("bcyCode");
		String depCode = (String) model.get("depCode");
		List<EgovMap> saExcelData = (List<EgovMap>) model.get("saExcelData");

		HSSFCellStyle headerStyle = ExcelUtil.getBasicHeaderStyle(workbook);
		HSSFCellStyle bodyStyle = ExcelUtil.getBasicBodyStyle(workbook);
		HSSFCellStyle editableCellStyle = ExcelUtil.getEditableCellStyle(workbook);

		HSSFSheet saSheet = workbook.createSheet("self-audit 작업현황");
		HSSFSheet metaSheet = workbook.createSheet("METADATA");

		HSSFRow metaRow1 = metaSheet.createRow(0);
		metaRow1.createCell(1, HSSFCell.CELL_TYPE_STRING).setCellValue(2);
		metaRow1.createCell(2, HSSFCell.CELL_TYPE_STRING).setCellValue(bcyCode);
		metaRow1.createCell(3, HSSFCell.CELL_TYPE_STRING).setCellValue(depCode);
		metaRow1.createCell(0, HSSFCell.CELL_TYPE_STRING)
				.setCellValue(DigestUtils.md5Hex(bcyCode + depCode).toUpperCase());

		saSheet.setDefaultColumnStyle(WRK_PRG_INDEX, editableCellStyle);

		HSSFRow header = saSheet.createRow(HEADER_ROW_INDEX_STARTED_AT);

		header.createCell(CTR_KEY_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue("KEY1");
		header.createCell(TRC_KEY_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue("KEY2");
		header.createCell(WRK_KEY_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue("KEY3");
		header.createCell(LV2_COD_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue("번호");
		header.createCell(LV2_NAM_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue("통제항목");
		header.createCell(LV3_COD_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue("점검번호");
		header.createCell(LV3_NAM_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue("통제점검");
		header.createCell(DOC_NAM_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue("업무명");
		header.createCell(DEP_NAM_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue("부서");
		header.createCell(USR_NAM_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue("담당자");
		header.createCell(WRK_PRG_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue("결과");
		header.createCell(WRK_DTL_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue("수행내역");

		int rowNumber = BODY_ROW_INDEX_STARTED_AT;
		for (EgovMap controlItem : saExcelData) {

			boolean hasWrk = false;

			int ctrKey = ((BigDecimal) controlItem.get("ctrKey")).intValue();
			int trcKey = controlItem.get("trcKey") == null ? 0 : ((BigDecimal) controlItem.get("trcKey")).intValue();
			int wrkKey = controlItem.get("wrkKey") == null ? 0 : ((BigDecimal) controlItem.get("wrkKey")).intValue();

			if (trcKey != 0 && wrkKey != 0) {
				hasWrk = true;
			}

			String lv2Cod = (String) controlItem.get("lv2Cod");
			String lv2Nam = (String) controlItem.get("lv2Nam");
			String lv3Cod = (String) controlItem.get("lv3Cod");
			String lv3Nam = (String) controlItem.get("lv3Nam");
			String docNam = controlItem.get("docNam") == null ? "" : (String) controlItem.get("docNam");
			String depNam = controlItem.get("depNam") == null ? "" : (String) controlItem.get("depNam");
			String usrNam = controlItem.get("usrNam") == null ? "" : (String) controlItem.get("usrNam");
			int wrkPrg = controlItem.get("wrkPrg") == null ? 0 : ((BigDecimal) controlItem.get("wrkPrg")).intValue();
			WorkVO.statusType wrkPrgEnum = WorkVO.statusType.fromValue(wrkPrg);
			String wrkPrgText = wrkPrgEnum.getTitle();

			String wrkDtl = "";

			if (controlItem.get("wrkDtl") instanceof Clob) {
				Clob clob = (Clob) controlItem.get("wrkDtl");
				StringBuilder strOut = new StringBuilder();
				BufferedReader br = new BufferedReader(clob.getCharacterStream());
				String str = "";
				while ((str = br.readLine()) != null) {
					strOut.append(str);
				}
				wrkDtl = strOut.toString();
			}

			wrkDtl = StringUtils.isBlank(wrkDtl) ? "" : Jsoup.parse(wrkDtl).text();

			HSSFRow controlItemRow = saSheet.createRow(rowNumber);

			controlItemRow.createCell(CTR_KEY_INDEX, HSSFCell.CELL_TYPE_NUMERIC).setCellValue(ctrKey);
			controlItemRow.createCell(TRC_KEY_INDEX, HSSFCell.CELL_TYPE_NUMERIC).setCellValue(trcKey);
			controlItemRow.createCell(WRK_KEY_INDEX, HSSFCell.CELL_TYPE_NUMERIC).setCellValue(wrkKey);
			controlItemRow.createCell(LV2_COD_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue(lv2Cod);
			controlItemRow.createCell(LV2_NAM_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue(lv2Nam);
			controlItemRow.createCell(LV3_COD_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue(lv3Cod);
			controlItemRow.createCell(LV3_NAM_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue(lv3Nam);
			controlItemRow.createCell(DOC_NAM_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue(docNam);
			controlItemRow.createCell(DEP_NAM_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue(depNam);
			controlItemRow.createCell(USR_NAM_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue(usrNam);

			if (hasWrk) {
				controlItemRow.createCell(WRK_PRG_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue(wrkPrgText);
			} else {
				controlItemRow.createCell(WRK_PRG_INDEX, HSSFCell.CELL_TYPE_BLANK);
			}

			controlItemRow.createCell(WRK_DTL_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue(wrkDtl);

			rowNumber++;
		}


		for (int i = 0; i < saSheet.getPhysicalNumberOfRows(); i++) {
			HSSFRow loopRow = saSheet.getRow(i);
			for (int k = 0; k < loopRow.getPhysicalNumberOfCells(); k++) {
				HSSFCell loopCell = loopRow.getCell(k);
				if (loopCell != null) {
					if (i == 0) {
						loopCell.setCellStyle(headerStyle);
					} else {
						if (k != WRK_PRG_INDEX) {
							loopCell.setCellStyle(bodyStyle);
						}
					}
				}
			}
		}

		saSheet.setColumnWidth(LV2_COD_INDEX, ExcelUtil.MIN_COLUMN_WIDTH * ExcelUtil.COLUMN_WIDTH_UNIT);
		ExcelUtil.autoSizeColumn(saSheet, LV2_NAM_INDEX);
		saSheet.setColumnWidth(LV3_COD_INDEX, ExcelUtil.MIN_COLUMN_WIDTH * ExcelUtil.COLUMN_WIDTH_UNIT);
		ExcelUtil.autoSizeColumn(saSheet, LV3_NAM_INDEX);
		ExcelUtil.autoSizeColumn(saSheet, DOC_NAM_INDEX);
		saSheet.setColumnWidth(DEP_NAM_INDEX, ExcelUtil.MIN_COLUMN_WIDTH * ExcelUtil.COLUMN_WIDTH_UNIT);
		saSheet.setColumnWidth(USR_NAM_INDEX, ExcelUtil.MIN_COLUMN_WIDTH * ExcelUtil.COLUMN_WIDTH_UNIT);
		saSheet.setColumnWidth(WRK_PRG_INDEX, ExcelUtil.MIN_COLUMN_WIDTH * ExcelUtil.COLUMN_WIDTH_UNIT);
		ExcelUtil.autoSizeColumn(saSheet, WRK_DTL_INDEX);

		ExcelUtil.setHiddenColumn(saSheet, CTR_KEY_INDEX);
		ExcelUtil.setHiddenColumn(saSheet, TRC_KEY_INDEX);
		ExcelUtil.setHiddenColumn(saSheet, WRK_KEY_INDEX);

		response.setHeader("Content-Disposition", "attachment;filename=" + new String("Self-Audit작업현황.xls".getBytes("ksc5601"), "8859_1")  + ";");
		response.setContentType("application/vnd.ms-excel;charset=euc-kr");

	}

}
