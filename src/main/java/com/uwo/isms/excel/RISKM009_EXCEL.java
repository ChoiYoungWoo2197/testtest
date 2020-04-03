package com.uwo.isms.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.uwo.isms.common.CommonConfig;
import com.uwo.isms.web.FMSetupController;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public class RISKM009_EXCEL extends AbstractExcelView {

	Logger log = LogManager.getLogger(FMSetupController.class);

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook wb, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		/* 자산 평가 */
		HSSFCell cell = null;
		HSSFSheet sheet = wb.createSheet("대상 및 자산평가");

		CellRangeAddress cra = new CellRangeAddress(1, 1, 0, 1);
		sheet.addMergedRegion(cra);
		setText(getCell(sheet, 1,0), "진단대상 부서목록");
		HSSFCellStyle cs = wb.createCellStyle();
		cs.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cs.setFillForegroundColor(new HSSFColor.YELLOW().getIndex());
		sheet.getRow(1).getCell(0).setCellStyle(cs);

		sheet.getRow(1).setHeight((short) (256*2.5));

		setText(getCell(sheet, 3, 0), "부서코드");
		sheet.setColumnWidth(0, 20*256);
		setText(getCell(sheet, 3, 1), "부서명");
		sheet.setColumnWidth(1, 30*256);
		setText(getCell(sheet, 3, 2), "서비스코드");
		sheet.setColumnWidth(1, 30*256);
		setText(getCell(sheet, 3, 3), "서비스명");
		sheet.setColumnWidth(1, 30*256);
		List<?> sheet1List = (List<?>) model.get("sheet1List");

		for(int i = 0; i< sheet1List.size();i++){
			EgovMap map = (EgovMap)sheet1List.get(i);
			log.info("Excel : " + map);
			cell = getCell(sheet, 4+i, 0);
			setText(cell,(String)map.get("udmDepCod"));
			cell = getCell(sheet, 4+i, 1);
			setText(cell,(String)map.get("udmDepNam"));
			cell = getCell(sheet, 4+i, 2);
			setText(cell,(String)map.get("uagSvrCod"));
			cell = getCell(sheet, 4+i, 3);
			setText(cell,(String)map.get("uagSvrNam"));
		}

		/* 평가 양식 시트 */
		List<Map<String, String>> depList = (List<Map<String, String>>) model.get("sheet1List");
		List<Map<String, String>> svrList = (List<Map<String, String>>) model.get("svrList");
		List<Map<String, String>> vlbList = (List<Map<String, String>>) model.get("vlbList");
		for(Map<String, String> svrMap : svrList){
			String svrCode = svrMap.get("uagSvrCod");
			HSSFSheet depSheet = wb.createSheet(svrMap.get("uagSvrNam"));
			//setText(getCell(depSheet, 0,0), "부서코드");
			setText(getCell(depSheet, 1,4), "부서명");

			setText(getCell(depSheet, 2,0), "코드");
			setText(getCell(depSheet, 2,1), "분류");
			setText(getCell(depSheet, 2,2), "통제목적");
			setText(getCell(depSheet, 2,3), "통제사항");
			setText(getCell(depSheet, 2,4), "내용");

			int cellCnt = 5;
			for(Map<String, String> assMap : depList){
				if(svrCode.equals(assMap.get("uagSvrCod"))){
					log.info("assMap : " + assMap);
					setText(getCell(depSheet, 1,cellCnt), assMap.get("udmDepNam"));
					setText(getCell(depSheet, 2,cellCnt), assMap.get("udmDepCod"));
					cellCnt++;
				}
			}
			int vlbCnt = 3;
			for(Map<String, String> vlbMap : vlbList){
				setText(getCell(depSheet, vlbCnt,0), vlbMap.get("usmSroCod"));
				setText(getCell(depSheet, vlbCnt,1), vlbMap.get("usmSroLv0"));
				setText(getCell(depSheet, vlbCnt,2), vlbMap.get("usmSroLv1"));
				setText(getCell(depSheet, vlbCnt,3), vlbMap.get("usmSroLv2"));
				setText(getCell(depSheet, vlbCnt,4), vlbMap.get("usmSroDes"));
				vlbCnt++;
			}
		}
	}

}
