package com.uwo.isms.excel;

import com.uwo.isms.domain.UserVO;
import com.uwo.isms.util.EgovUserDetailsHelper;
import com.uwo.isms.util.ExcelUtil;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.vfs.util.UserAuthenticatorUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.hsqldb.rights.User;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class INSPT006_EXCEL extends AbstractExcelView{

	private final static int CONTROL_KEY_INDEX = 0;
	private final static int ANSWER_KEY_INDEX = 1;
	private final static int LV1_CELL_NUMBER_INDEX = 2;
	private final static int LV1_CELL_TITLE_INDEX = 3;
	private final static int LV2_CELL_NUMBER_INDEX = 4;
	private final static int LV2_CELL_TITLE_INDEX = 5;
	private final static int LV3_CELL_NUMBER_INDEX = 6;
	private final static int LV3_CELL_TITLE_INDEX = 7;
	private final static int ANSWER_SELECT_INDEX = 8;
	private final static int ANSWER_NUMBER_INDEX = 9;
	private final static int ANSWER_TITLE_INDEX = 10;
	private final static int RESULT_COMMENT_INDEX = 11;
	private final static int POLICY_COMMENT_INDEX = 12;

	private final static int HEADER_ROW_INDEX_STARTED_AT = 0;
	private final static int BODY_ROW_INDEX_STARTED_AT = 1;

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
									  HSSFWorkbook workbook, HttpServletRequest request,
									  HttpServletResponse response) throws Exception {

		String complianceKind = (String) model.get("complianceKind");
		Map<String, String> filter = (Map<String, String>) model.get("filter");
		List<EgovMap> controlItemList = (List<EgovMap>) model.get("controlItemList");
		List<EgovMap> answerList = (List<EgovMap>) model.get("answerList");
		List<EgovMap> additionalInfoList = (List<EgovMap>) model.get("additionalInfoList");

		HSSFCellStyle headerStyle = ExcelUtil.getBasicHeaderStyle(workbook);
		HSSFCellStyle bodyStyle = ExcelUtil.getBasicBodyStyle(workbook);
		HSSFCellStyle editableCellStyle = ExcelUtil.getEditableCellStyle(workbook);

		HSSFSheet resultSheet = workbook.createSheet("평가결과");
		HSSFSheet metaSheet = workbook.createSheet("METADATA");

		HSSFRow metaRow2 = metaSheet.createRow(0);
		metaRow2.createCell(1, HSSFCell.CELL_TYPE_STRING).setCellValue(3);
		metaRow2.createCell(2, HSSFCell.CELL_TYPE_STRING).setCellValue(filter.get("bcyCode"));
		metaRow2.createCell(3, HSSFCell.CELL_TYPE_STRING).setCellValue(filter.get("service"));
		metaRow2.createCell(4, HSSFCell.CELL_TYPE_STRING).setCellValue(filter.get("standard"));
		metaRow2.createCell(0, HSSFCell.CELL_TYPE_STRING)
				.setCellValue(DigestUtils.md5Hex(filter.get("bcyCode") + filter.get("service") + filter.get("standard")).toUpperCase());

		resultSheet.setDefaultColumnStyle(ANSWER_SELECT_INDEX, editableCellStyle);
		resultSheet.setDefaultColumnStyle(RESULT_COMMENT_INDEX, editableCellStyle);

		if (isInfraMpOrLa(complianceKind)) {
			resultSheet.setDefaultColumnStyle(POLICY_COMMENT_INDEX, editableCellStyle);
		}

		HSSFRow header = resultSheet.createRow(HEADER_ROW_INDEX_STARTED_AT);

		header.createCell(LV1_CELL_NUMBER_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue("영역 번호");
		header.createCell(LV1_CELL_TITLE_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue("영역");
		header.createCell(LV2_CELL_NUMBER_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue("분야 번호");
		header.createCell(LV2_CELL_TITLE_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue("분야");
		header.createCell(LV3_CELL_NUMBER_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue("항목 번호");
		header.createCell(LV3_CELL_TITLE_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue("항목");
		header.createCell(ANSWER_SELECT_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue("문항 선택");
		header.createCell(ANSWER_NUMBER_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue("문항 번호");
		header.createCell(ANSWER_TITLE_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue("문항");
		header.createCell(RESULT_COMMENT_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue("점검결과");
		header.createCell(CONTROL_KEY_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue("KEY1");
		header.createCell(ANSWER_KEY_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue("KEY2");

		if (isInfraMpOrLa(complianceKind)) {
			header.createCell(POLICY_COMMENT_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue("정책, 지침");
		}

		int rowNumber = BODY_ROW_INDEX_STARTED_AT;
		int rowNumberMerge = -1;
		for (EgovMap controlItem : controlItemList) {

			final int currentControlItemKey = ((BigDecimal) controlItem.get("ucmCtrKey")).intValue();
			List<EgovMap> currenAdditionalInfoList = new ArrayList<EgovMap>(additionalInfoList);

			CollectionUtils.filter(currenAdditionalInfoList, new Predicate() {
				@Override
				public boolean evaluate(Object object) {
					BigDecimal ctrKey = (BigDecimal) ((EgovMap) object).get("uirCtrKey");
					return currentControlItemKey == ctrKey.intValue();
				}
			});

			EgovMap currentAdditionalInfo = currenAdditionalInfoList.isEmpty() ? new EgovMap() : currenAdditionalInfoList.get(0);

			String lv1Cod = (String) controlItem.get("ucm1lvCod");
			String lv1Nam = (String) controlItem.get("ucm1lvNam");
			String lv2Cod = (String) controlItem.get("ucm2lvCod");
			String lv2Nam = (String) controlItem.get("ucm2lvNam");
			String lv3Cod = (String) controlItem.get("ucm3lvCod");
			String lv3Nam = (String) controlItem.get("ucm3lvNam");
			String rstDtl = (String) currentAdditionalInfo.get("uidRstDtl");
			String rltDoc = (String) currentAdditionalInfo.get("uidRltDoc");

			HSSFRow controlItemRow = resultSheet.createRow(rowNumber);

			controlItemRow.createCell(LV1_CELL_NUMBER_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue(lv1Cod);
			controlItemRow.createCell(LV1_CELL_TITLE_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue(lv1Nam);
			controlItemRow.createCell(LV2_CELL_NUMBER_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue(lv2Cod);
			controlItemRow.createCell(LV2_CELL_TITLE_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue(lv2Nam);
			controlItemRow.createCell(LV3_CELL_NUMBER_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue(lv3Cod);
			controlItemRow.createCell(LV3_CELL_TITLE_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue(lv3Nam);

			List<EgovMap> answerOfControlItem = new ArrayList<EgovMap>(answerList);
			CollectionUtils.filter(answerOfControlItem, new Predicate() {
				@Override
				public boolean evaluate(Object object) {
					int loopControlItemKey = Integer.parseInt ((String) ((EgovMap) object).get("ctrKey"));
					return loopControlItemKey == currentControlItemKey;
				}
			});

			for (int answerNumber = 0; answerNumber < answerOfControlItem.size(); answerNumber++) {
				EgovMap answer = answerOfControlItem.get(answerNumber);
				HSSFRow currentRow = null;

				boolean selected = Boolean.parseBoolean((String) answer.get("selected"));
				int id = ((BigDecimal) answer.get("id")).intValue();
				int number = ((BigDecimal) answer.get("seq")).intValue();
				String title = (String) answer.get("title");

				if(number==1){
					if (rowNumberMerge != -1) {
						resultSheet.addMergedRegion(new CellRangeAddress(rowNumberMerge, rowNumber-1, RESULT_COMMENT_INDEX, RESULT_COMMENT_INDEX));
						if (isInfraMpOrLa(complianceKind)) {
							resultSheet.addMergedRegion(new CellRangeAddress(rowNumberMerge, rowNumber-1, POLICY_COMMENT_INDEX, POLICY_COMMENT_INDEX));
						}
						rowNumberMerge = rowNumber;
					}else{
						rowNumberMerge = rowNumber;
					}
				}

				if (answerNumber == 0) {
					currentRow = controlItemRow;
					controlItemRow.createCell(ANSWER_NUMBER_INDEX, HSSFCell.CELL_TYPE_NUMERIC).setCellValue(number);
					controlItemRow.createCell(ANSWER_TITLE_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue(title);

					controlItemRow.createCell(CONTROL_KEY_INDEX, HSSFCell.CELL_TYPE_NUMERIC).setCellValue(currentControlItemKey);
					controlItemRow.createCell(ANSWER_KEY_INDEX, HSSFCell.CELL_TYPE_NUMERIC).setCellValue(id);
				} else {
					HSSFRow answerRow = resultSheet.createRow(++rowNumber);
					currentRow = answerRow;
					answerRow.createCell(ANSWER_NUMBER_INDEX, HSSFCell.CELL_TYPE_NUMERIC).setCellValue(number);
					answerRow.createCell(ANSWER_TITLE_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue(title);

					answerRow.createCell(LV1_CELL_NUMBER_INDEX, HSSFCell.CELL_TYPE_BLANK);
					answerRow.createCell(LV1_CELL_TITLE_INDEX, HSSFCell.CELL_TYPE_BLANK);
					answerRow.createCell(LV2_CELL_NUMBER_INDEX, HSSFCell.CELL_TYPE_BLANK);
					answerRow.createCell(LV2_CELL_TITLE_INDEX, HSSFCell.CELL_TYPE_BLANK);
					answerRow.createCell(LV3_CELL_NUMBER_INDEX, HSSFCell.CELL_TYPE_BLANK);
					answerRow.createCell(LV3_CELL_TITLE_INDEX, HSSFCell.CELL_TYPE_BLANK);

					answerRow.createCell(CONTROL_KEY_INDEX, HSSFCell.CELL_TYPE_NUMERIC).setCellValue(currentControlItemKey);
					answerRow.createCell(ANSWER_KEY_INDEX, HSSFCell.CELL_TYPE_NUMERIC).setCellValue(id);
				}

				if (selected) {
					currentRow.createCell(ANSWER_SELECT_INDEX, HSSFCell.CELL_TYPE_NUMERIC).setCellValue(1);
				} else {
					currentRow.createCell(ANSWER_SELECT_INDEX, HSSFCell.CELL_TYPE_BLANK);
				}

			}

			if (StringUtils.isBlank(rstDtl)) {
				controlItemRow.createCell(RESULT_COMMENT_INDEX, HSSFCell.CELL_TYPE_BLANK);
			} else {
				controlItemRow.createCell(RESULT_COMMENT_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue(rstDtl);
			}

			if (isInfraMpOrLa(complianceKind)) {
				if (StringUtils.isBlank(rltDoc)) {
					controlItemRow.createCell(POLICY_COMMENT_INDEX, HSSFCell.CELL_TYPE_BLANK);
				} else {
					controlItemRow.createCell(POLICY_COMMENT_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue(rltDoc);
				}
			}

			rowNumber++;
		}

		if (rowNumberMerge != rowNumber) {
			resultSheet.addMergedRegion(new CellRangeAddress(rowNumberMerge, rowNumber-1, RESULT_COMMENT_INDEX, RESULT_COMMENT_INDEX));
			if (isInfraMpOrLa(complianceKind)) {
				resultSheet.addMergedRegion(new CellRangeAddress(rowNumberMerge, rowNumber-1, POLICY_COMMENT_INDEX, POLICY_COMMENT_INDEX));
			}
		}

		for (int i = 0; i < resultSheet.getPhysicalNumberOfRows(); i++) {
			HSSFRow loopRow = resultSheet.getRow(i);
			for (int k = 0; k < loopRow.getPhysicalNumberOfCells(); k++) {
				HSSFCell loopCell = loopRow.getCell(k);
				if (loopCell != null) {
					if (i == 0) {
						loopCell.setCellStyle(headerStyle);
					} else {
						if (k == ANSWER_SELECT_INDEX || k == RESULT_COMMENT_INDEX || k == POLICY_COMMENT_INDEX) {
							loopCell.setCellStyle(editableCellStyle);
						} else {
							loopCell.setCellStyle(bodyStyle);
						}
					}
				}
			}
		}

		resultSheet.setColumnWidth(LV1_CELL_NUMBER_INDEX, ExcelUtil.MIN_COLUMN_WIDTH * ExcelUtil.COLUMN_WIDTH_UNIT);
		ExcelUtil.autoSizeColumn(resultSheet, LV1_CELL_TITLE_INDEX);
		resultSheet.setColumnWidth(LV2_CELL_NUMBER_INDEX, ExcelUtil.MIN_COLUMN_WIDTH * ExcelUtil.COLUMN_WIDTH_UNIT);
		ExcelUtil.autoSizeColumn(resultSheet, LV2_CELL_TITLE_INDEX);
		resultSheet.setColumnWidth(LV3_CELL_NUMBER_INDEX, ExcelUtil.MIN_COLUMN_WIDTH * ExcelUtil.COLUMN_WIDTH_UNIT);
		ExcelUtil.autoSizeColumn(resultSheet, LV3_CELL_TITLE_INDEX);
		resultSheet.setColumnWidth(ANSWER_SELECT_INDEX, ExcelUtil.MIN_COLUMN_WIDTH * ExcelUtil.COLUMN_WIDTH_UNIT);
		resultSheet.setColumnWidth(ANSWER_NUMBER_INDEX, ExcelUtil.MIN_COLUMN_WIDTH * ExcelUtil.COLUMN_WIDTH_UNIT);
		ExcelUtil.autoSizeColumn(resultSheet, ANSWER_TITLE_INDEX);
		ExcelUtil.autoSizeColumn(resultSheet, RESULT_COMMENT_INDEX);

		ExcelUtil.setHiddenColumn(resultSheet, CONTROL_KEY_INDEX);
		ExcelUtil.setHiddenColumn(resultSheet, ANSWER_KEY_INDEX);

		if (isInfraMpOrLa(complianceKind)) {
			resultSheet.autoSizeColumn(POLICY_COMMENT_INDEX);
		}

		response.setHeader("Content-Disposition", "attachment;filename=" + new String("평가결과.xls".getBytes("ksc5601"), "8859_1")  + ";");
		response.setContentType("application/vnd.ms-excel;charset=euc-kr");

	}

	private boolean isInfraMpOrLa(String complianceKind) {
		return (complianceKind.equals("infra_mp") || complianceKind.equals("infra_la"));
	}

}
