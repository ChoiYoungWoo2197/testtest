package com.uwo.isms.excel;

import java.util.*;

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

public class MWORK006_EXCEL extends AbstractExcelView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook wb, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HSSFSheet sheets = null;
		HSSFCell cell = null;
		String strSheetTitles[] = {"미진행업무", "지연업무", "완료업무"};
		int intColwidth[] = {80, 300, 80, 80, 100, 120, 100, 80, 120, 120};

		for(int s=0; s< strSheetTitles.length; s++) {
			sheets = wb.createSheet(strSheetTitles[s]);
			List<String> listTitles = new ArrayList<String>(Arrays.asList("업무코드", "업무명", "업무상태", "업무주기", "업무마감일", "담당자", "서비스"));
			List<String> listCellKey = new ArrayList<String>(Arrays.asList("utwWrkKey", "utdDocNam", "displayWrkSta", "utwTrmCod", "utwEndDat", "utwWrkId", "utwSvcCod"));
			List<?> listWork = null;
			if(s==0) {
				listWork = (List<?>) model.get("noWorkList");
			}else if(s==1) {
				listWork = (List<?>) model.get("delayWorkList");
			}else if(s==2){
				listTitles.add("처리상태");
//				listTitles.add("최초 완료일");
//				listTitles.add("최종 수정일");
				listCellKey.add("utwComNam");
//				listCellKey.add("utwWrkDat");
//				listCellKey.add("utwUptMdh");
				listWork = (List<?>)model.get("comWorkList");
			}

			Map<String, Object> mapCellInfo1 = new HashMap<String, Object>();
			for(int c=0; c< listTitles.size(); c++){
				mapCellInfo1.put("row", 2);
				mapCellInfo1.put("col", c);
				mapCellInfo1.put("style", "th");
				cell = funcSetCell(wb, sheets, mapCellInfo1);
				cell.setCellValue(listTitles.get(c));
			}

			Map<String, Object> mapCellInfo2 = new HashMap<String, Object>();
			for(int l = 0; l< listWork.size();l++){
				EgovMap map = (EgovMap)listWork.get(l);
				for(int k=0; k<listCellKey.size(); k++){
					mapCellInfo2.put("row", l+3);
					mapCellInfo2.put("col", k);
					cell = funcSetCell(wb, sheets, mapCellInfo2);
					if(listCellKey.get(k)=="utwWrkKey"){
						cell.setCellValue(map.get(listCellKey.get(k)).toString());
					}else{
						cell.setCellValue((String) map.get(listCellKey.get(k)));
					}
				}
			}
			for(int k=0; k<listCellKey.size(); k++){
				sheets.setColumnWidth(k, Math.round(intColwidth[k]*36.5f));
			}
		}
	}

	private HSSFCell funcSetCell(HSSFWorkbook wb, HSSFSheet sheets, Map<String, Object> mapCellInfo){
		HSSFCell cell = getCell(sheets, (Integer) mapCellInfo.get("row"), (Integer) mapCellInfo.get("col"));
//		HSSFCellStyle style = wb.createCellStyle();
//		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		cell.setCellType(Cell.CELL_TYPE_STRING);
//		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//		style.setWrapText(true);

//		if(mapCellInfo.get("style")!=null && mapCellInfo.get("style").equals("th")){
//			style.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
//			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//			HSSFFont font = wb.createFont();
//			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//			style.setFont(font);
//		}
//		cell.setCellStyle(style);
		return cell;
	}
}
