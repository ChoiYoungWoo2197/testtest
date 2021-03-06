package com.uwo.isms.excel;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.uwo.isms.common.CommonConfig;
import com.uwo.isms.dao.ExcelDAO;
import com.uwo.isms.dao.FMRiskmDAO;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public class RISKM003_REPORT_EXCEL extends AbstractExcelView{

	@Resource(name="excelDAO")
    private ExcelDAO excelDAO;
	
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook wb, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, String> map = (Map<String, String>)model.get("paramMap");
		
		List<Map<String, String>> typeList = excelDAO.riskm003_report_type(map);
		for(Map<String, String> typeMap : typeList){
			HSSFSheet sheet = wb.createSheet(typeMap.get("uacCatNam"));
			HSSFCellStyle cellStyle = wb.createCellStyle(); 
			
			setText(getCell(sheet, 0, 0), typeMap.get("uacCatNam") + " 위험조치계획서");
			setText(getStyleCell(sheet, cellStyle, 2, 0), "NO");
			setText(getStyleCell(sheet, cellStyle, 2, 1), "해당자산(그룹)");
			setText(getStyleCell(sheet, cellStyle, 2, 2), "Concern Code");
			setText(getStyleCell(sheet, cellStyle, 2, 3), "Concern");
			setText(getStyleCell(sheet, cellStyle, 2, 4), "개선방안 및 보호대책");
			setText(getStyleCell(sheet, cellStyle, 2, 5), "위험도");
			setText(getStyleCell(sheet, cellStyle, 2, 6), "위험처리");
			setText(getStyleCell(sheet, cellStyle, 2, 7), "조치예정일(시기)");
			setText(getStyleCell(sheet, cellStyle, 2, 8), "추진부서");
			setText(getStyleCell(sheet, cellStyle, 2, 9), "제약사항");
			
			typeMap.put("svr", map.get("svr"));
			typeMap.put("manCyl", map.get("manCyl"));
			List<Map<String, String>> reportList = excelDAO.riskm003_report_down(typeMap);
			int rowCnt = 3;
			int cnt = 1;
			for(Map<String, String> reportMap : reportList){
				setText(getStyleCell(sheet, cellStyle, rowCnt, 0), String.valueOf(cnt++));
				setText(getStyleCell(sheet, cellStyle, rowCnt, 1), reportMap.get("uagGrpNam"));
				setText(getStyleCell(sheet, cellStyle, rowCnt, 2), reportMap.get("usoCocCod"));
				setText(getStyleCell(sheet, cellStyle, rowCnt, 3), reportMap.get("usoCocDes"));
				setText(getStyleCell(sheet, cellStyle, rowCnt, 4), reportMap.get("ursMesDes"));
				setText(getStyleCell(sheet, cellStyle, rowCnt, 5), reportMap.get("usoRskGrdChg"));
				setText(getStyleCell(sheet, cellStyle, rowCnt, 6), reportMap.get("rsk"));
				setText(getStyleCell(sheet, cellStyle, rowCnt, 7), reportMap.get("ursReqMdh"));
				setText(getStyleCell(sheet, cellStyle, rowCnt, 8), reportMap.get("mngDepNam"));
				setText(getStyleCell(sheet, cellStyle, rowCnt, 9), reportMap.get("ursLimDes"));
				rowCnt++;
			}
		}
	
		/*
		List<?> list = (List<?>)model.get("sheet1List");
		
		for(int i = 0; i< list.size();i++){
			EgovMap map1 = (EgovMap)list.get(i);
			setText(getStyleCell(sheet, cellStyle, 3+i, 0),(String)map.get("uagSvcNam"));
			setText(getStyleCell(sheet, cellStyle, 3+i, 1),(String)map.get("uagDepNam"));
			setText(getStyleCell(sheet, cellStyle, 3+i, 2),(String)map.get("uagMngNam"));
			setText(getStyleCell(sheet, cellStyle, 3+i, 3),(String)map.get("uagGrpNam"));
			setText(getStyleCell(sheet, cellStyle, 3+i, 4),(String)map.get("uagGrpCod"));
			setText(getStyleCell(sheet, cellStyle, 3+i, 5),"");
			setText(getStyleCell(sheet, cellStyle, 3+i, 6),"");
			setText(getStyleCell(sheet, cellStyle, 3+i, 7),"");
			setText(getStyleCell(sheet, cellStyle, 3+i, 8),"");
			setText(getStyleCell(sheet, cellStyle, 3+i, 9),"");
			setText(getStyleCell(sheet, cellStyle, 3+i, 10),"");
		}
		
		HSSFSheet sheet2 = wb.createSheet("자산그룹 및 자산현황");
		HSSFCellStyle cellStyle2 = wb.createCellStyle();
		setText(getCell(sheet2, 0, 0), "자산그룹 및 자산현황");
		setText(getStyleCell(sheet2, cellStyle2, 2, 0), "자산유형");
		setText(getStyleCell(sheet2, cellStyle2, 2, 1), "그룹코드");
		setText(getStyleCell(sheet2, cellStyle2, 2, 2), "그룹명");
		setText(getStyleCell(sheet2, cellStyle2, 2, 3), "자산일련번호");
		setText(getStyleCell(sheet2, cellStyle2, 2, 4), "자산부서명");
		setText(getStyleCell(sheet2, cellStyle2, 2, 5), "DB/Version");
		setText(getStyleCell(sheet2, cellStyle2, 2, 6), "호스트명");
		setText(getStyleCell(sheet2, cellStyle2, 2, 7), "위치");
		setText(getStyleCell(sheet2, cellStyle2, 2, 8), "서비스");
		setText(getStyleCell(sheet2, cellStyle2, 2, 9), "서비스(용도)");
		setText(getStyleCell(sheet2, cellStyle2, 2, 10), "자산등급");
		
		List<?> list2 = (List<?>)model.get("sheet2List");
		for(int i = 0; i< list2.size();i++){
			EgovMap map2 = (EgovMap)list2.get(i);
			setText(getStyleCell(sheet2, cellStyle2, 3+i, 0),(String)map.get("uacCatNam"));
			setText(getStyleCell(sheet2, cellStyle2, 3+i, 1),(String)map.get("uagGrpCod"));
			setText(getStyleCell(sheet2, cellStyle2, 3+i, 2),(String)map.get("uagGrpNam"));
			setText(getStyleCell(sheet2, cellStyle2, 3+i, 3),(String)map.get("uarAssSeq"));
			setText(getStyleCell(sheet2, cellStyle2, 3+i, 4),(String)map.get("uarDepNam"));
			setText(getStyleCell(sheet2, cellStyle2, 3+i, 5),(String)map.get("uarEqpNam"));
			setText(getStyleCell(sheet2, cellStyle2, 3+i, 6),(String)map.get("uarValCl0"));
			setText(getStyleCell(sheet2, cellStyle2, 3+i, 7),(String)map.get("uarAdmPos"));
			setText(getStyleCell(sheet2, cellStyle2, 3+i, 8),(String)map.get("uarSvrNam"));
			setText(getStyleCell(sheet2, cellStyle2, 3+i, 9),(String)map.get("uarDtlExp"));
			setText(getStyleCell(sheet2, cellStyle2, 3+i, 10),(String)map.get("uarAssLvl"));
		}
		
	
		
		HSSFSheet sheet3 = wb.createSheet("우려사항 및 위험시나리오");
		HSSFCellStyle cellStyle3 = wb.createCellStyle();
		setText(getCell(sheet3, 0, 0), "우려사항 및 위험시나리오");
		setText(getStyleCell(sheet3, cellStyle3, 2, 0), "자산유형");
		setText(getStyleCell(sheet3, cellStyle3, 2, 1), "대분류");
		setText(getStyleCell(sheet3, cellStyle3, 2, 2), "중분류");
		setText(getStyleCell(sheet3, cellStyle3, 2, 3), "Concern Code");
		setText(getStyleCell(sheet3, cellStyle3, 2, 4), "Concern List");
		setText(getStyleCell(sheet3, cellStyle3, 2, 5), "중요도");
		setText(getStyleCell(sheet3, cellStyle3, 2, 6), "발생가능성");
		setText(getStyleCell(sheet3, cellStyle3, 2, 7), "취약점 Code");
		
		List<?> list3 = (List<?>)model.get("sheet3List");
		for(int i = 0; i< list3.size();i++){
			EgovMap map3 = (EgovMap)list3.get(i);
			setText(getStyleCell(sheet3, cellStyle3, 3+i, 0),(String)map.get("usoAssCat"));
			setText(getStyleCell(sheet3, cellStyle3, 3+i, 1),"");
			setText(getStyleCell(sheet3, cellStyle3, 3+i, 2),(String)map.get("usoCocCod"));
			setText(getStyleCell(sheet3, cellStyle3, 3+i, 3),(String)map.get("usoCocNam"));
			setText(getStyleCell(sheet3, cellStyle3, 3+i, 4),(String)map.get("uacCatNam"));
			setText(getStyleCell(sheet3, cellStyle3, 3+i, 5),(String)map.get("usoCocGrd"));
			setText(getStyleCell(sheet3, cellStyle3, 3+i, 6),(String)map.get("usoFrqGrd"));
			setText(getStyleCell(sheet3, cellStyle3, 3+i, 7),(String)map.get("usoScrCod"));
		}
		*/
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
