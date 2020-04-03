package com.uwo.isms.excel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public class COMPS004_EXCEL extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook wb, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HSSFSheet sheet = wb.createSheet("정보보호활동 설정");
		HSSFCell cell = null;

		String strTitles[] = {"업무코드", "서비스", "업무명", "알림메일", "결재방법", "업무주기", "마감일자", "증적등록주기", "필요증적갯수", "사용여부", "업무시작일", "업무종료일", "문서유형", "업무설명", "통제항목", "업무담당자", "업무담당자(사번)"};
		for(int c=0; c< strTitles.length; c++){
			cell = getCell(sheet, 2, c);
			cell.setCellValue(strTitles[c]);
		}

		List<?> list = (List<?>) model.get("list");
		for(int i = 0; i< list.size();i++){
			EgovMap map = (EgovMap)list.get(i);
			List<String> listCellInfo = new ArrayList<String>();
			listCellInfo.add(String.valueOf(map.get("utdTrcKey")).trim());
			listCellInfo.add((String) map.get("service"));
			listCellInfo.add((String) map.get("utdDocNam"));
			listCellInfo.add((String) map.get("sdmYn"));
			listCellInfo.add((String) map.get("utdAprYn"));
			listCellInfo.add((String) map.get("workTerm"));
			listCellInfo.add((String) map.get("cmpDat"));
			listCellInfo.add((String) map.get("dtrGbn"));
			listCellInfo.add(String.valueOf(map.get("utdDocCnt")).trim());
			listCellInfo.add((String) map.get("delYn"));
			listCellInfo.add((String) map.get("utdStrDat"));
			listCellInfo.add((String) map.get("utdEndDat"));
			listCellInfo.add((String) map.get("docGbnNm"));
			listCellInfo.add((String) map.get("utdDocEtc"));
			listCellInfo.add((String) map.get("ctrList"));
			listCellInfo.add((String) map.get("workerList"));
			listCellInfo.add((String) map.get("workerIdList"));

			for(int c=0; c<listCellInfo.size(); c++){
				cell = getCell(sheet, i+3, c);
				cell.setCellValue(listCellInfo.get(c));
			}
		}
	}

}
