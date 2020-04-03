package com.uwo.isms.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public class MWORK019_EXCEL extends AbstractExcelView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook wb, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
	
		HSSFCell cell = null;
		HSSFSheet sheet = wb.createSheet("List");
		
		cell = getCell(sheet, 0, 0);
		setText(cell, "수행업무이력");
				
		setText(getCell(sheet, 2, 0), "문서명");
		setText(getCell(sheet, 2, 1), "문서종류");
		setText(getCell(sheet, 2, 2), "업무주기");
		setText(getCell(sheet, 2, 3), "서비스");
		setText(getCell(sheet, 2, 4), "삭제여부");
		setText(getCell(sheet, 2, 5), "결재여부");
		setText(getCell(sheet, 2, 6), "결재단계");
		setText(getCell(sheet, 2, 7), "업무기한");
		setText(getCell(sheet, 2, 8), "업무시작일");
		setText(getCell(sheet, 2, 9), "업무종료일");
		setText(getCell(sheet, 2, 10), "문서설명");
		setText(getCell(sheet, 2, 11), "메일발송여부");
		setText(getCell(sheet, 2, 12), "생성자");
		setText(getCell(sheet, 2, 13), "생성일시");
		setText(getCell(sheet, 2, 14), "최종수정자");
		setText(getCell(sheet, 2, 15), "최종수정일시");
		
		List<?> list = (List<?>)model.get("list");
		
		for(int i = 0; i< list.size();i++){
			EgovMap map = (EgovMap)list.get(i);
			
			cell = getCell(sheet, 3+i, 0);
			setText(cell,(String)map.get("utlDocNam"));
			
			cell = getCell(sheet, 3+i, 1);
			setText(cell,(String)map.get("utlDocGbn"));
			
			cell = getCell(sheet, 3+i, 2);
			setText(cell,(String)map.get("utlTrmGbn"));
			
			cell = getCell(sheet, 3+i, 3);
			setText(cell,(String)map.get("utlSvcCod"));
			
			cell = getCell(sheet, 3+i, 4);
			setText(cell,(String)map.get("utlDelYn"));
			
			cell = getCell(sheet, 3+i, 5);
			setText(cell,(String)map.get("utlAprYn"));
			
			cell = getCell(sheet, 3+i, 6);
			setText(cell,(String)map.get("utlAppLev"));
			
			cell = getCell(sheet, 3+i, 7);
			setText(cell,(String)map.get("utlWrkEnd"));
			
			cell = getCell(sheet, 3+i, 8);
			setText(cell,(String)map.get("utlStrDat"));
			
			cell = getCell(sheet, 3+i, 9);
			setText(cell,(String)map.get("utlEndDat"));
			
			cell = getCell(sheet, 3+i, 10);
			setText(cell,(String)map.get("utlDocEtc"));
			
			cell = getCell(sheet, 3+i, 11);
			setText(cell,(String)map.get("utlSdmYn"));
			
			cell = getCell(sheet, 3+i, 12);
			setText(cell,(String)map.get("utlRgtId"));
			
			cell = getCell(sheet, 3+i, 13);
			setText(cell,(String)map.get("utlRgtMdh"));
			
			cell = getCell(sheet, 3+i, 14);
			setText(cell,(String)map.get("utlUptId"));
			
			cell = getCell(sheet, 3+i, 15);
			setText(cell,(String)map.get("utlUptMdh"));
		}
	}

}
