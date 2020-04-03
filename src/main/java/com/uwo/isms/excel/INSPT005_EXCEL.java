package com.uwo.isms.excel;

import com.uwo.isms.common.Node;
import com.uwo.isms.domain.ProtectionMeasureTaskVO;
import com.uwo.isms.domain.ProtectionMeasureVO;
import com.uwo.isms.util.EgovStringUtil;
import com.uwo.isms.util.ExcelUtil;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.WorkbookUtil;
import org.jsoup.Jsoup;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class INSPT005_EXCEL extends AbstractExcelView{

	private final static int TITLE_INDEX = 0;
	private final static int STATUS_INDEX = 1;
	private final static int STARTED_AT_INDEX = 2;
	private final static int ENDED_AT_INDEX = 3;
	private final static int BUDGET_INDEX = 4;
	private final static int TARGET_FACILITY_INDEX = 5;
	private final static int PURPOSE_INDEX = 6;
	private final static int EVALUATION_INDEX = 7;
	private final static int CONTENT_INDEX = 8;

	private final static int HEADER_ROW_INDEX_STARTED_AT = 0;
	private final static int BODY_ROW_INDEX_STARTED_AT = 1;

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ProtectionMeasureVO protectionMeasure = (ProtectionMeasureVO) model.get("protectionMeasure");
		List<ProtectionMeasureTaskVO> protectionMeasureTask = (List<ProtectionMeasureTaskVO>) model.get("protectionMeasureTask");
		Node<ProtectionMeasureTaskVO> rootNode = (Node<ProtectionMeasureTaskVO>) model.get("rootNode");

		for (Node<ProtectionMeasureTaskVO> categoryNode : rootNode.getChildren()) {
			
//			HSSFSheet sheet = workbook.createSheet(categoryNode.getData().getTitle());
			HSSFSheet sheet = workbook.createSheet(WorkbookUtil.createSafeSheetName(categoryNode.getData().getTitle()));
			
			HSSFRow header = sheet.createRow(HEADER_ROW_INDEX_STARTED_AT);
			header.createCell(TITLE_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue("과제 제목");
			header.createCell(STATUS_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue("상태");
			header.createCell(STARTED_AT_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue("시작일");
			header.createCell(ENDED_AT_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue("종료일");
			header.createCell(BUDGET_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue("예산");
			header.createCell(TARGET_FACILITY_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue("대상시설");
			header.createCell(PURPOSE_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue("목적");
			header.createCell(EVALUATION_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue("성과평가지표");
			header.createCell(CONTENT_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue("과제 내용");

			int rowNumber = BODY_ROW_INDEX_STARTED_AT;
			for (Node<ProtectionMeasureTaskVO> taskNode : categoryNode.getChildren()) {
				ProtectionMeasureTaskVO task = (ProtectionMeasureTaskVO) taskNode.getData();

				HSSFRow row = sheet.createRow(rowNumber);

				row.createCell(TITLE_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue(task.getTitle());
				row.createCell(STATUS_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue(task.getStatus().getTitle());
				row.createCell(STARTED_AT_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue(task.getStartedAt());
				row.createCell(ENDED_AT_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue(task.getEndedAt());
				row.createCell(BUDGET_INDEX, HSSFCell.CELL_TYPE_NUMERIC).setCellValue(task.getBudget());
				row.createCell(TARGET_FACILITY_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue(task.getTargetFacility());
				row.createCell(PURPOSE_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue(task.getPurpose());
				row.createCell(EVALUATION_INDEX, HSSFCell.CELL_TYPE_STRING).setCellValue(task.getEvaluationIndex());
				row.createCell(CONTENT_INDEX, HSSFCell.CELL_TYPE_STRING)
						.setCellValue(StringUtils.isBlank(task.getTaskContent()) ? "" : Jsoup.parse(task.getTaskContent()).text());
				
				rowNumber++;
			}

			HSSFCellStyle headerStyle = ExcelUtil.getBasicHeaderStyle(workbook);
			HSSFCellStyle bodyStyle = ExcelUtil.getBasicBodyStyle(workbook);

			for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
				HSSFRow loopRow = sheet.getRow(i);
				for (int k = 0; k < loopRow.getPhysicalNumberOfCells(); k++) {
					HSSFCell loopCell = loopRow.getCell(k);
					if (loopCell != null) {
						if (i == 0) {
							loopCell.setCellStyle(headerStyle);
						} else {
							loopCell.setCellStyle(bodyStyle);
						}
					}
				}
			}

			ExcelUtil.autoSizeColumn(sheet, TITLE_INDEX);
			ExcelUtil.autoSizeColumn(sheet, STATUS_INDEX);
			ExcelUtil.autoSizeColumn(sheet, STARTED_AT_INDEX);
			ExcelUtil.autoSizeColumn(sheet, ENDED_AT_INDEX);
			ExcelUtil.autoSizeColumn(sheet, BUDGET_INDEX);
			ExcelUtil.autoSizeColumn(sheet, TARGET_FACILITY_INDEX);
			ExcelUtil.autoSizeColumn(sheet, PURPOSE_INDEX);
			ExcelUtil.autoSizeColumn(sheet, EVALUATION_INDEX);
			ExcelUtil.autoSizeColumn(sheet, CONTENT_INDEX);
		}

		response.setHeader("Content-Disposition",
				"attachment;filename=" + new String((protectionMeasure.getSeason() + "년-추진과제.xls").getBytes("ksc5601"),
						"8859_1")  + ";");
		response.setContentType("application/vnd.ms-excel;charset=euc-kr");

	}

}
