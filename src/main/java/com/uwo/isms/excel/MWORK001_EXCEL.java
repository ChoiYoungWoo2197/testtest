package com.uwo.isms.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.uwo.isms.common.CommonConfig;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public class MWORK001_EXCEL extends AbstractExcelView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook wb, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
	
		HSSFCell cell = null;
		HSSFSheet sheet = wb.createSheet("정보보호 일정");

		setText(getCell(sheet, 0, 0), "서비스");
		setText(getCell(sheet, 0, 1), request.getSession().getAttribute(CommonConfig.SES_SERVICE_NAME_KEY).toString());
		setText(getCell(sheet, 0, 2), "부서");
		setText(getCell(sheet, 0, 3), request.getSession().getAttribute(CommonConfig.SES_DEPT_NAME_KEY).toString());
		setText(getCell(sheet, 0, 4), "이름");
		setText(getCell(sheet, 0, 5), request.getSession().getAttribute(CommonConfig.SES_USER_NAME_KEY).toString());
		
		setText(getCell(sheet, 2, 0), "업무명");
		setText(getCell(sheet, 2, 1), "시작일");
		setText(getCell(sheet, 2, 2), "종료일");
		setText(getCell(sheet, 2, 3), "주기");
		setText(getCell(sheet, 2, 4), "처리상태");
		
		List<?> workList = (List<?>)model.get("workList");
		
		for(int i = 0; i< workList.size();i++){
			EgovMap map = (EgovMap)workList.get(i);
			
			cell = getCell(sheet, 3+i, 0);
			setText(cell,(String)map.get("utdDocNam"));
			
			cell = getCell(sheet, 3+i, 1);
			setText(cell,(String)map.get("utwStrDat"));
			
			cell = getCell(sheet, 3+i, 2);
			setText(cell,(String)map.get("utwEndDat"));
			
			cell = getCell(sheet, 3+i, 3);
			setText(cell,(String)map.get("utdTrmGbn"));
			
			cell = getCell(sheet, 3+i, 4);
			setText(cell,(String)map.get("sta"));	
		}
	}

}
