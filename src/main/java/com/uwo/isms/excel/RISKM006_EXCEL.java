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

public class RISKM006_EXCEL extends AbstractExcelView {

	Logger log = LogManager.getLogger(FMSetupController.class);

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook wb, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		/* 자산 평가 */
		HSSFCell cell = null;
		HSSFSheet sheet = wb.createSheet("대상 및 자산평가");

		CellRangeAddress cra = new CellRangeAddress(1, 1, 0, 11);
		sheet.addMergedRegion(cra);
		setText(getCell(sheet, 1,0), "진단대상 및 자산목록");
		HSSFCellStyle cs = wb.createCellStyle();
		cs.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cs.setFillForegroundColor(new HSSFColor.YELLOW().getIndex());
		sheet.getRow(1).getCell(0).setCellStyle(cs);

		sheet.getRow(1).setHeight((short) (256*2.5));

		setText(getCell(sheet, 3, 0), "그룹코드");
		sheet.setColumnWidth(0, 30*256);
		setText(getCell(sheet, 3, 1), "조직(팀)");
		sheet.setColumnWidth(1, 17*256);
		setText(getCell(sheet, 3, 2), "제조사");
		sheet.setColumnWidth(2, 15*256);
		setText(getCell(sheet, 3, 3), "모델명");
		sheet.setColumnWidth(3, 15*256);
		setText(getCell(sheet, 3, 4), "호스트명");
		sheet.setColumnWidth(4, 15*256);
		setText(getCell(sheet, 3, 5), "IP");
		sheet.setColumnWidth(5, 16*256);
		setText(getCell(sheet, 3, 6), "위치");
		sheet.setColumnWidth(6, 35*256);
		setText(getCell(sheet, 3, 7), "서비스");
		sheet.setColumnWidth(7, 13*256);
		setText(getCell(sheet, 3, 8), "용도");
		sheet.setColumnWidth(8, 40*256);
		setText(getCell(sheet, 3, 9), "담당자");
		sheet.setColumnWidth(9, 10*256);
		setText(getCell(sheet, 3, 10), "자산등급");
		sheet.setColumnWidth(10, 10*256);
		setText(getCell(sheet, 3, 11), "비고");
		sheet.setColumnWidth(11, 10*256);

		List<?> sheet1List = (List<?>) model.get("sheet1List");

		for(int i = 0; i< sheet1List.size();i++){
			EgovMap map = (EgovMap)sheet1List.get(i);
			log.info("Excel : " + map);
			cell = getCell(sheet, 4+i, 0);
			setText(cell,(String)map.get("uagGrpCod"));

			cell = getCell(sheet, 4+i, 1);
			setText(cell,(String)map.get("uagDepNam"));

			cell = getCell(sheet, 4+i, 2);
			setText(cell,(String)map.get("maker"));

			cell = getCell(sheet, 4+i, 3);
			setText(cell,(String)map.get("modelnm"));

			cell = getCell(sheet, 4+i, 4);
			setText(cell,(String)map.get("hostnm"));

			cell = getCell(sheet, 4+i, 5);
			setText(cell,(String)map.get("ip"));

			cell = getCell(sheet, 4+i, 6);
			setText(cell,(String)map.get("uarAdmPos"));

			cell = getCell(sheet, 4+i, 7);
			setText(cell,(String)map.get("uarSvrNam"));

			cell = getCell(sheet, 4+i, 8);
			setText(cell,(String)map.get("uarDtlExp"));

			cell = getCell(sheet, 4+i, 9);
			setText(cell,(String)map.get("uarUseNam"));

			cell = getCell(sheet, 4+i, 10);
			setText(cell,(String)map.get("uarAssLvl"));

			cell = getCell(sheet, 4+i, 11);
			setText(cell,(String)map.get("rem"));
		}

		/* 평가 양식 시트 */
		List<Map<String, String>> groupList = (List<Map<String, String>>) model.get("groupList");
		List<Map<String, String>> assList = (List<Map<String, String>>) model.get("assList");
		List<Map<String, String>> vlbList = (List<Map<String, String>>) model.get("vlbList");
		for(Map<String, String> grpMap : groupList){
			//log.debug("grpMap : " + grpMap);
			HSSFSheet assSheet = wb.createSheet(grpMap.get("uagGrpNam"));
			setText(getCell(assSheet, 0,0), "운영부서");
			setText(getCell(assSheet, 1,0), "괸리부서");
			setText(getCell(assSheet, 2,0), "관리자");
			setText(getCell(assSheet, 3,0), "IP");
			setText(getCell(assSheet, 4,0), "HOST명");
			setText(getCell(assSheet, 5,0), "URL");

			setText(getCell(assSheet, 6,0), "취약점코드");
			setText(getCell(assSheet, 6,1), "구분");
			setText(getCell(assSheet, 6,2), "항목");
			setText(getCell(assSheet, 6,3), "위험도");
			//setText(getCell(assSheet, 0,4), (String)request.getSession().getAttribute(CommonConfig.SES_DEPT_NAME_KEY));
			int cellCnt = 4;
			String vlbType = "";
			for(Map<String, String> assMap : assList){
				if(grpMap.get("uagGrpCod").equals(assMap.get("uarGrpCod"))){
					//log.debug("assMap : " + assMap);
					// 2017-05-19 추가
					setText(getCell(assSheet, 0,cellCnt), assMap.get("uarOprNam"));

					setText(getCell(assSheet, 1,cellCnt), assMap.get("uarDepNam"));
					setText(getCell(assSheet, 2,cellCnt), assMap.get("uarOwnNam"));
					setText(getCell(assSheet, 3,cellCnt), assMap.get("uarIp"));
					setText(getCell(assSheet, 4,cellCnt), assMap.get("uarHost"));
					setText(getCell(assSheet, 5,cellCnt), assMap.get("uarOs"));
					setText(getCell(assSheet, 6,cellCnt), assMap.get("uarAssCod"));
					vlbType = assMap.get("uarRskGrp");
					cellCnt++;
				}
			}
			int vlbCnt = 7;
			for(Map<String, String> vlbMap : vlbList){
				if(vlbType != null && vlbType.equals(vlbMap.get("urvRskTyp"))){
					setText(getCell(assSheet, vlbCnt,0), vlbMap.get("urvVlbCod"));
					setText(getCell(assSheet, vlbCnt,1), vlbMap.get("urvRskDiv"));
					setText(getCell(assSheet, vlbCnt,2), vlbMap.get("urvRskItm"));
					setText(getCell(assSheet, vlbCnt,3), vlbMap.get("urvRskImp"));
					vlbCnt++;
				}
			}

			/*
			 * 1. 부서, 2. 운영담당자, 3. IP, 4. 호스트명, 5. URL, 6. 자산키
			 */
		}

	}

}
