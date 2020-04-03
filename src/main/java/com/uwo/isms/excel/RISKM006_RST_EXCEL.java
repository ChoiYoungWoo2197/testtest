package com.uwo.isms.excel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.uwo.isms.common.CommonConfig;
import com.uwo.isms.dao.FMRiskmDAO;
import com.uwo.isms.web.FMSetupController;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public class RISKM006_RST_EXCEL extends AbstractExcelView {

	Logger log = LogManager.getLogger(FMSetupController.class);

	@Resource(name="fmRiskmDAO")
	private FMRiskmDAO fmRiskmDAO;

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook wb, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		/* 자산 평가 */

		//HSSFCell cell = null;
		HSSFSheet sheet = wb.createSheet("대상 및 자산평가");
		HSSFCellStyle cellStyle = wb.createCellStyle();

		CellRangeAddress cra = new CellRangeAddress(1, 1, 0, 11);
		sheet.addMergedRegion(cra);
		setText(getCell(sheet, 1,0), "진단대상 및 자산목록");
		HSSFCellStyle cs = wb.createCellStyle();
		cs.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cs.setFillForegroundColor(new HSSFColor.YELLOW().getIndex());

		cs.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cs.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cs.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cs.setBorderRight(HSSFCellStyle.BORDER_THIN);
		sheet.getRow(1).getCell(0).setCellStyle(cs);
		sheet.getRow(1).setHeight((short) (256*2.5));

		setText(getStyleCell(sheet, cellStyle, 3, 0), "그룹코드");
		sheet.setColumnWidth(0, 30*256);
		setText(getStyleCell(sheet, cellStyle, 3, 1), "조직(팀)");
		sheet.setColumnWidth(1, 17*256);
		setText(getStyleCell(sheet, cellStyle, 3, 2), "제조사");
		sheet.setColumnWidth(2, 15*256);
		setText(getStyleCell(sheet, cellStyle, 3, 3), "모델명");
		sheet.setColumnWidth(3, 15*256);
		setText(getStyleCell(sheet, cellStyle, 3, 4), "호스트명");
		sheet.setColumnWidth(4, 15*256);
		setText(getStyleCell(sheet, cellStyle, 3, 5), "IP");
		sheet.setColumnWidth(5, 16*256);
		setText(getStyleCell(sheet, cellStyle, 3, 6), "위치");
		sheet.setColumnWidth(6, 35*256);
		setText(getStyleCell(sheet, cellStyle, 3, 7), "서비스");
		sheet.setColumnWidth(7, 13*256);
		setText(getStyleCell(sheet, cellStyle, 3, 8), "용도");
		sheet.setColumnWidth(8, 40*256);
		setText(getStyleCell(sheet, cellStyle, 3, 9), "담당자");
		sheet.setColumnWidth(9, 10*256);
		setText(getStyleCell(sheet, cellStyle, 3, 10), "자산등급");
		sheet.setColumnWidth(10, 10*256);
		setText(getStyleCell(sheet, cellStyle, 3, 11), "비고");
		sheet.setColumnWidth(11, 10*256);

		List<?> sheet1List = (List<?>) model.get("sheet1List");

		for(int i = 0; i< sheet1List.size();i++){
			EgovMap map = (EgovMap)sheet1List.get(i);
			//log.info("Excel : " + map);
			setText(getStyleCell(sheet, cellStyle, 4+i, 0),(String)map.get("uagGrpCod"));
			setText(getStyleCell(sheet, cellStyle, 4+i, 1),(String)map.get("uagDepNam"));
			setText(getStyleCell(sheet, cellStyle, 4+i, 2),(String)map.get("maker"));
			setText(getStyleCell(sheet, cellStyle, 4+i, 3),(String)map.get("modelnm"));
			setText(getStyleCell(sheet, cellStyle, 4+i, 4),(String)map.get("uarHost"));
			setText(getStyleCell(sheet, cellStyle, 4+i, 5),(String)map.get("uarIp"));
			setText(getStyleCell(sheet, cellStyle, 4+i, 6),(String)map.get("uarAdmPos"));
			setText(getStyleCell(sheet, cellStyle, 4+i, 7),(String)map.get("uarSvrNam"));
			setText(getStyleCell(sheet, cellStyle, 4+i, 8),(String)map.get("uarDtlExp"));
			setText(getStyleCell(sheet, cellStyle, 4+i, 9),(String)map.get("uarUseNam"));
			setText(getStyleCell(sheet, cellStyle, 4+i, 10),(String)map.get("uarAssLvl"));
			setText(getStyleCell(sheet, cellStyle, 4+i, 11),(String)map.get("rem"));
		}


		/*
		 * 평가 리스트 다운로드
		 */
		List<Map<String, String>> groupList = (List<Map<String, String>>) model.get("groupList");
		HSSFSheet assSheet = null;
		String assCat = (String)model.get("assCat");
		String assGbn = (String)model.get("assGbn");
		String service = (String)model.get("service");
		String downType = (String)model.get("downType");

		for(Map<String, String> grpMap : groupList){
			if(!"".equals(assCat) && assCat != null){
				grpMap.put("assCat", assCat);
			}
			if(!"".equals(assGbn) && assGbn != null){
				grpMap.put("assGbn", assGbn);
			}
			if(!"".equals(downType) && downType != null){
				grpMap.put("downType", downType);
			}
			if(!"".equals(service) && service != null){
				grpMap.put("service", service);
			}

			//log.info("grpMap : " + grpMap);
			if(wb.getSheetIndex(grpMap.get("uagGrpCod")) == -1){
				assSheet = wb.createSheet(grpMap.get("uagGrpCod"));
			}
			CellRangeAddress cra1 = null;

			HSSFCellStyle cs1 = wb.createCellStyle();
			cs1.setAlignment(HSSFCellStyle.ALIGN_CENTER);

			cra1 = new CellRangeAddress(0,0,0,3);
			assSheet.addMergedRegion(cra1);
			setText(getCell(assSheet, 0,0), "운영부서");
			assSheet.getRow(0).getCell(0).setCellStyle(cs1);

			cra1 = new CellRangeAddress(1,1,0,3);
			assSheet.addMergedRegion(cra1);
			setText(getCell(assSheet, 1,0), "괸리부서");
			assSheet.getRow(1).getCell(0).setCellStyle(cs1);

			cra1 = new CellRangeAddress(2,2,0,3);
			assSheet.addMergedRegion(cra1);
			setText(getCell(assSheet, 2,0), "관리자");
			assSheet.getRow(2).getCell(0).setCellStyle(cs1);

			cra1 = new CellRangeAddress(3,3,0,3);
			assSheet.addMergedRegion(cra1);
			setText(getCell(assSheet, 3,0), "IP");
			assSheet.getRow(3).getCell(0).setCellStyle(cs1);

			cra1 = new CellRangeAddress(4,4,0,3);
			assSheet.addMergedRegion(cra1);
			setText(getCell(assSheet, 4,0), "HOST명");
			assSheet.getRow(4).getCell(0).setCellStyle(cs1);

			cra1 = new CellRangeAddress(5,5,0,3);
			assSheet.addMergedRegion(cra1);
			setText(getCell(assSheet, 5,0), "URL");
			assSheet.getRow(5).getCell(0).setCellStyle(cs1);


			setText(getCell(assSheet, 6,0), "취약점코드");
			setText(getCell(assSheet, 6,1), "구분");
			assSheet.setColumnWidth(1, 18*256);
			setText(getCell(assSheet, 6,2), "항목");
			assSheet.setColumnWidth(2, 45*256);
			setText(getCell(assSheet, 6,3), "위험도");
			assSheet.setColumnWidth(3, 7*256);
			setText(getCell(assSheet, 0,4), (String)request.getSession().getAttribute(CommonConfig.SES_DEPT_NAME_KEY));

			//int cellCnt = 4;
			String vlbType = grpMap.get("uarRskGrp");
			grpMap.put("ummManCyl", (String)request.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));

			List<Map<String, String>> vlbList = (List<Map<String, String>>)fmRiskmDAO.fm_ristk006_vlb_list(grpMap);

			// 1. 진단항목 출력
			int vlbCnt = 7;

			// 1-1. 진단항목을 HashMap 에 저장함
			Map<String, Integer> codeMap = new HashMap<String, Integer>();

			for(Map<String, String> vlbMap : vlbList){
				if(vlbType.equals(vlbMap.get("urvRskTyp"))){
					codeMap.put(vlbMap.get("urvVlbCod"), vlbCnt);
					setText(getCell(assSheet, vlbCnt,0), vlbMap.get("urvVlbCod"));
					setText(getCell(assSheet, vlbCnt,1), vlbMap.get("urvRskDiv"));
					setText(getCell(assSheet, vlbCnt,2), vlbMap.get("urvRskItm"));
					setText(getCell(assSheet, vlbCnt,3), vlbMap.get("urvRskImp"));
					vlbCnt++;
				}
			}
			List<Map<String, String>> assList = (List<Map<String, String>>)fmRiskmDAO.fm_riskm006_rst_ass_list(grpMap);

			// 2. 자산 정보 출력
			int assCnt = 4;
			for(Map<String, String> assMap : assList){
				assMap.put("ummManCyl", (String)request.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
				List<Map<String, String>> rstList = (List<Map<String, String>>)fmRiskmDAO.fm_riskm006_rst_down_list(assMap);
				setText(getCell(assSheet, 1, assCnt), assMap.get("uarDepNam"));
				setText(getCell(assSheet, 2, assCnt), assMap.get("uarOwnNam"));
				setText(getCell(assSheet, 3, assCnt), assMap.get("uarIp"));
				setText(getCell(assSheet, 4, assCnt), assMap.get("uarHost"));
				setText(getCell(assSheet, 5, assCnt), assMap.get("cat3"));
				setText(getCell(assSheet, 6, assCnt), assMap.get("uarAssCod"));
				assSheet.setColumnWidth(assCnt, 20*256);

				// 3. 자산 진단 결과 출력
				//    진단항목과 자산별 진단항목의 항목 불일치가 발생하여 진단항목 Map과 비교 출력
				//int accRowCnt = 7;
				for(Map<String, String> rstMap : rstList){
					String vlbCod = rstMap.get("urvVlbCod");
					if (codeMap.get(vlbCod) != null) {
						int row = codeMap.get(vlbCod);
						setText(getCell(assSheet, row, assCnt), rstMap.get("urrRskRst"));
					}
					//accRowCnt++;
				}
				assCnt++;
			}
			List<Map<String, String>> assSumList = (List<Map<String, String>>)fmRiskmDAO.fm_riskm006_rst_ass_sum_list(grpMap);

			// 4. 자산 그룹 결과 출력
			//    진단항목과 자산별 진단항목의 항목 불일치(&순서)가 발생하여 진단항목 Map과 비교 출력
			//int asRowCnt = 7;
			setText(getCell(assSheet, 6, assCnt), "결과");
			setText(getCell(assSheet, 6, assCnt+1), "위험도");
			setText(getCell(assSheet, 6, assCnt+2), "위험등급");
			for(Map<String, String> asMap : assSumList){
				String vlbCod = asMap.get("urvVlbCod");
				if (codeMap.get(vlbCod) != null) {
					int row = codeMap.get(vlbCod);
					setText(getCell(assSheet, row, assCnt), asMap.get("rs"));
					setText(getCell(assSheet, row, assCnt+1), asMap.get("rr"));
					setText(getCell(assSheet, row, assCnt+2), asMap.get("rsk"));
				}
				//asRowCnt++;
			}
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
