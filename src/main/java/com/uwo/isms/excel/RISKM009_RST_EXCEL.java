package com.uwo.isms.excel;

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

public class RISKM009_RST_EXCEL extends AbstractExcelView {
	
	Logger log = LogManager.getLogger(FMSetupController.class);
	
	@Resource(name="fmRiskmDAO")
	private FMRiskmDAO fmRiskmDAO;
	
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook wb, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		/* 부서평가 대상 */
		
		HSSFCell cell = null;
		HSSFSheet sheet = wb.createSheet("진단대상 부서목록");
		HSSFCellStyle cellStyle = wb.createCellStyle();
		
		CellRangeAddress cra = new CellRangeAddress(1, 1, 0, 1);
		sheet.addMergedRegion(cra);
		setText(getCell(sheet, 1,0), "진단대상 부서목록");
		HSSFCellStyle cs = wb.createCellStyle();
		cs.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cs.setFillForegroundColor(new HSSFColor.YELLOW().getIndex());
		
		cs.setBorderTop(HSSFCellStyle.BORDER_THIN);   
		cs.setBorderBottom(HSSFCellStyle.BORDER_THIN); 
		cs.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cs.setBorderRight(HSSFCellStyle.BORDER_THIN);   
		sheet.getRow(1).getCell(0).setCellStyle(cs);
		sheet.getRow(1).setHeight((short) (256*2.5));
		
		setText(getStyleCell(sheet, cs, 3, 0), "부서코드");
		sheet.setColumnWidth(0, 20*256);
		setText(getStyleCell(sheet, cs, 3, 1), "부서명");
		sheet.setColumnWidth(1, 30*256);
		setText(getStyleCell(sheet, cs, 3, 1), "서비스코드");
		sheet.setColumnWidth(1, 20*256);
		setText(getStyleCell(sheet, cs, 3, 1), "서비스명");
		sheet.setColumnWidth(1, 30*256);
		List<Map<String, String>> sheet1List = (List<Map<String, String>>) model.get("sheet1List");

		for(int i = 0; i< sheet1List.size();i++){
			EgovMap map = (EgovMap)sheet1List.get(i);
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
		List<Map<String, String>> groupList = (List<Map<String, String>>) model.get("groupList"); 
		for(Map<String, String> groupMap : groupList){
			List<Map<String, String>> vlbList = (List<Map<String, String>>) model.get("vlbList"); 
			HSSFSheet depSheet = wb.createSheet(groupMap.get("uagSvrCod"));
			setText(getCell(depSheet, 1,4), "부서명");
			
			setText(getCell(depSheet, 2,0), "코드");
			setText(getCell(depSheet, 2,1), "분류");
			setText(getCell(depSheet, 2,2), "통제목적");
			setText(getCell(depSheet, 2,3), "통제사항");
			setText(getCell(depSheet, 2,4), "내용");
			
			
			int vlbCnt = 3;
			for(Map<String, String> vlbMap : vlbList){
				setText(getCell(depSheet, vlbCnt,0), vlbMap.get("usmSroCod"));
				setText(getCell(depSheet, vlbCnt,1), vlbMap.get("usmSroLv0"));
				setText(getCell(depSheet, vlbCnt,2), vlbMap.get("usmSroLv1"));
				setText(getCell(depSheet, vlbCnt,3), vlbMap.get("usmSroLv2"));
				setText(getCell(depSheet, vlbCnt,4), vlbMap.get("usmSroDes"));
				vlbCnt++;
			}
			
			int assCnt = 5;
			for(Map<String, String> depMap : sheet1List){
				if(depMap.get("uagSvrCod").equals(groupMap.get("uagSvrCod"))){
					depMap.put("ummManCyl", (String)request.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
					List<Map<String, String>> rstList = (List<Map<String, String>>)fmRiskmDAO.fm_riskm009_rst_down_list(depMap);
					setText(getCell(depSheet, 1, assCnt), depMap.get("udmDepNam")); 
					setText(getCell(depSheet, 2, assCnt), depMap.get("udmDepCod")); 
					depSheet.setColumnWidth(assCnt, 20*256);
					int accRowCnt = 3;
					for(Map<String, String> rstMap : rstList){ 
						setText(getCell(depSheet, accRowCnt, assCnt), rstMap.get("urrRskRst"));
						accRowCnt++;
					}
					assCnt++;
				}
			}
			List<Map<String, String>> assSumList = (List<Map<String, String>>)fmRiskmDAO.fm_riskm009_rst_ass_sum_list(groupMap);
			
			int asRowCnt = 3;
			setText(getCell(depSheet, 2, assCnt), "결과"); 
			setText(getCell(depSheet, 2, assCnt+1), "위험도"); 
			setText(getCell(depSheet, 2, assCnt+2), "위험등급"); 
			for(Map<String, String> asMap : assSumList){
				setText(getCell(depSheet, asRowCnt, assCnt), asMap.get("rs"));
				setText(getCell(depSheet, asRowCnt, assCnt+1), asMap.get("rr"));
				setText(getCell(depSheet, asRowCnt, assCnt+2), asMap.get("rsk")); 
				asRowCnt++;
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
