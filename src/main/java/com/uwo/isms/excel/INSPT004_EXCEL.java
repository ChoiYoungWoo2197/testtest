package com.uwo.isms.excel;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

public class INSPT004_EXCEL extends AbstractExcelView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook wb, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		//Style
		HSSFCellStyle styleTitle = setStyle(wb, "title");
		HSSFCellStyle styleHead = setStyle(wb, "head");
		HSSFCellStyle styleHead2 = setStyle(wb, "head2");
		HSSFCellStyle styleDefault = setStyle(wb, "default");
		HSSFCellStyle styleCenter = setStyle(wb, "center");

		//Data
		List<?> listResult = (List<?>) model.get("result");
		List<?> listFile = (List<?>) model.get("file");
		List<EgovMap> saFileList = (List<EgovMap>) model.get("saFile");
		Map<String, String> mapFileList = new HashMap<String, String>();
		for(int f=0; f<listFile.size();f++) {
			EgovMap mapFile = (EgovMap) listFile.get(f);
			if(mapFile.get("umfConKey")!=null) {
				mapFileList.put(mapFile.get("umfConKey").toString(), mapFile.get("umfConFnm").toString());
			}
		}

		//Sheet
		String strSheetTitle = "대책 명세서 관리";
		HSSFSheet sheet = wb.createSheet(strSheetTitle);
		HSSFCell cell = null;
		HSSFCell cellTemp = null;
		int intRow = 0;

		//Title
		cell = getCell(sheet, intRow, 0);
		cell.setCellStyle(styleTitle);
		cell.setCellValue(strSheetTitle);
		sheet.addMergedRegion(new CellRangeAddress(intRow,intRow,0,2));

		//Head
		intRow = 2;
		List<String> listTitles = new ArrayList<String>(Arrays.asList("통제분야", "통제항목", "통제점검\r\n번호", "상세내용", "수립여부", "운영현황", "관련문서\n(정책, 지침 등 세부조항번호까지)", "기록\n(증적자료)"));
		for(int c=0; c< listTitles.size(); c++){
			cell = getCell(sheet, intRow, c);
			cell.setCellStyle(styleHead);
			cell.setCellValue(listTitles.get(c));
			cellTemp = getCell(sheet, intRow+1, c);
			cellTemp.setCellStyle(styleHead2);
			sheet.addMergedRegion(new CellRangeAddress(intRow,intRow+1, c, c));
		}

		//Default
		intRow = 4;
		String strKeys;
		String str1Lv = "", strPre1Lv = "";
		String str2Lv = "", strPre2Lv = "";
		int intPre1LvRow = intRow;
		int intPre2LvRow = intRow;
		List<String> listCol = new ArrayList<String>();
		for(int r = 0; r< listResult.size();r++){
			EgovMap mapRow = (EgovMap)listResult.get(r);

			strKeys = "";//증적파일
//			if(mapRow.get("wrkKeys")!=null){
//				String keys[] = mapRow.get("wrkKeys").toString().split("\\,");
//				for(int k=0;k<keys.length; k++) {
//					if(keys[k]!="" && mapFileList.get(keys[k])!=null){
//						if(strKeys != "" ) strKeys += "\r\n";
//						strKeys += "- "+mapFileList.get(keys[k]);
//					}
//				}
//			}

			for (EgovMap saFileMap : saFileList) {
				if (saFileMap.get("ctrKey").equals(mapRow.get("ucmCtrKey"))) {
					if(strKeys != "" ) strKeys += "\r\n";
					strKeys += saFileMap.get("conFnm");
				}
			}

			str1Lv = mapRow.get("ucm1lvCod").toString();
			str2Lv = mapRow.get("ucm2lvCod").toString();
			listCol.clear();
			listCol.add(str1Lv+" "+mapRow.get("ucm1lvNam").toString().replaceAll("[\r\n]", ""));
			listCol.add(str2Lv+" "+mapRow.get("ucm2lvNam").toString().replaceAll("[\r\n]", ""));
			listCol.add((String) mapRow.get("ucm3lvCod"));
			listCol.add((String) mapRow.get("ucm3lvNam"));
			listCol.add(mapRow.get("ucm3ldYn").toString().equals("N")?"Y":"N");//수립여부
			listCol.add((String) mapRow.get("ucmMsrDtl"));
			listCol.add((String) mapRow.get("utdDocNam")); //수정 전 확인
			//listCol.add("");
			listCol.add(strKeys);

			for(int c=0; c<listCol.size(); c++) {
				cell = getCell(sheet, r+intRow, c);
				cell.setCellStyle(styleDefault);
				cell.setCellValue(listCol.get(c));
				if(c==0){
					if(strPre1Lv=="") strPre1Lv = str1Lv;
					if(!str1Lv.equals(strPre1Lv)){
						sheet.addMergedRegion(new CellRangeAddress(intPre1LvRow,r+intRow-1, c, c));
						strPre1Lv = str1Lv;
						intPre1LvRow = r+intRow;
					}
				}else if(c==1){
					if(strPre2Lv=="") strPre2Lv = str2Lv;
					if(!str2Lv.equals(strPre2Lv)){
						sheet.addMergedRegion(new CellRangeAddress(intPre2LvRow,r+intRow-1, c, c));
						strPre2Lv = str2Lv;
						intPre2LvRow = r+intRow;
					}
				}else if(c==2||c==4){
					cell.setCellStyle(styleCenter);
				}
			}
		}
		if(intPre1LvRow != listResult.size()+intRow-1){
			sheet.addMergedRegion(new CellRangeAddress(intPre1LvRow,listResult.size()+intRow-1, 0, 0));
		}
		if(intPre2LvRow != listResult.size()+intRow-1){
			sheet.addMergedRegion(new CellRangeAddress(intPre2LvRow,listResult.size()+intRow-1, 1, 1));
		}

		//Width Size
		int intColwidth[] = {100, 160, 80, 400, 80, 300, 300, 300};
		for(int k=0; k<intColwidth.length; k++){
			sheet.setColumnWidth(k, Math.round(intColwidth[k]*36.5f));
		}

	}

	private HSSFCellStyle setStyle(HSSFWorkbook wb, String type){
		HSSFCellStyle style = wb.createCellStyle();
		if(type=="title"){
			HSSFFont fontTitle = wb.createFont();
			fontTitle.setFontHeightInPoints((short)18);
			fontTitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			style.setFont(fontTitle);
		}else if(type=="head"){
			HSSFFont fontHead = wb.createFont();
			fontHead.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			style.setFont(fontHead);
			style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			style.setWrapText(true);
		}else if(type=="head2"){
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		}else if(type=="center"){
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		}else if(type=="default"){
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			style.setWrapText(true);
		}
		return style;
	}

}
