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

public class MWORK005_EXCEL extends AbstractExcelView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook wb, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
	
		HSSFCell cell = null;
		HSSFSheet sheet = wb.createSheet("담당자 현황");
				
		setText(getCell(sheet, 2, 0), "담당자");
		setText(getCell(sheet, 2, 1), "부서");
		setText(getCell(sheet, 2, 2), "일간");
		setText(getCell(sheet, 2, 3), "주간");
		setText(getCell(sheet, 2, 4), "월간");
		setText(getCell(sheet, 2, 5), "격월");
		setText(getCell(sheet, 2, 6), "분기");
		setText(getCell(sheet, 2, 7), "반기");
		setText(getCell(sheet, 2, 8), "연간");
		setText(getCell(sheet, 2, 9), "비주기");
		
		List<?> workList = (List<?>)model.get("workList");
		
		for(int i = 0; i< workList.size();i++){
			EgovMap map = (EgovMap)workList.get(i);
			
			cell = getCell(sheet, 3+i, 0);
			setText(cell,(String)map.get("utwWrkNm"));
			
			cell = getCell(sheet, 3+i, 1);
			setText(cell,(String)map.get("utwDepCod"));
			
			cell = getCell(sheet, 3+i, 2);
			setText(cell,String.valueOf(map.get("trmD")));
			
			cell = getCell(sheet, 3+i, 3);
			setText(cell,String.valueOf(map.get("trmW")));
			
			cell = getCell(sheet, 3+i, 4);
			setText(cell,String.valueOf(map.get("trmM")));
			
			cell = getCell(sheet, 3+i, 5);
			setText(cell,String.valueOf(map.get("trmT")));
			
			cell = getCell(sheet, 3+i, 6);
			setText(cell,String.valueOf(map.get("trmQ")));
			
			cell = getCell(sheet, 3+i, 7);
			setText(cell,String.valueOf(map.get("trmH")));
			
			cell = getCell(sheet, 3+i, 8);
			setText(cell,String.valueOf(map.get("trmY")));
			
			cell = getCell(sheet, 3+i, 9);
			setText(cell,String.valueOf(map.get("trmN")));
		}
		
		HSSFSheet sheet2 = wb.createSheet("일간업무");
		
		setText(getCell(sheet2, 2, 0), "업무명");
		setText(getCell(sheet2, 2, 1), "업무시작일");
		setText(getCell(sheet2, 2, 2), "업무종료일");
		setText(getCell(sheet2, 2, 3), "대무자");
		setText(getCell(sheet2, 2, 4), "처리상태");
		
		List<?> detailList = (List<?>)model.get("detailList");
		
		int cnt = 0;
		for(int i = 0; i< detailList.size();i++){
			EgovMap map = (EgovMap)detailList.get(i);
			
			String gbn = (String)map.get("utdTrmGbn"); 
			
			
			if(gbn.equals("D")){
			
			cell = getCell(sheet2, 3+cnt, 0);
			setText(cell,(String)map.get("utdDocNam"));
			
			cell = getCell(sheet2, 3+cnt, 1);
			setText(cell,(String)map.get("utwStrDat"));
			
			cell = getCell(sheet2, 3+cnt, 2);
			setText(cell,(String)map.get("utwEndDat"));
			
			cell = getCell(sheet2, 3+cnt, 3);
			setText(cell,(String)map.get("utwAgnId"));
			
			cell = getCell(sheet2, 3+cnt, 4);
			setText(cell,(String)map.get("sta"));
			
			cnt++;
			}
		}
		
		HSSFSheet sheet3 = wb.createSheet("주간업무");
		
		setText(getCell(sheet3, 2, 0), "업무명");
		setText(getCell(sheet3, 2, 1), "업무시작일");
		setText(getCell(sheet3, 2, 2), "업무종료일");
		setText(getCell(sheet3, 2, 3), "대무자");
		setText(getCell(sheet3, 2, 4), "처리상태");
		
		
		cnt = 0;
		for(int i = 0; i< detailList.size();i++){
			EgovMap map = (EgovMap)detailList.get(i);
			
			String gbn = (String)map.get("utdTrmGbn");			
			
			if(gbn.equals("W")){
			
			cell = getCell(sheet3, 3+cnt, 0);
			setText(cell,(String)map.get("utdDocNam"));
			
			cell = getCell(sheet3, 3+cnt, 1);
			setText(cell,(String)map.get("utwStrDat"));
			
			cell = getCell(sheet3, 3+cnt, 2);
			setText(cell,(String)map.get("utwEndDat"));
			
			cell = getCell(sheet3, 3+cnt, 3);
			setText(cell,(String)map.get("utwAgnId"));
			
			cell = getCell(sheet2, 3+cnt, 4);
			setText(cell,(String)map.get("sta"));
			
			cnt++;
			}
		}
		
		HSSFSheet sheet4 = wb.createSheet("월간업무");
		
		setText(getCell(sheet4, 2, 0), "업무명");
		setText(getCell(sheet4, 2, 1), "업무시작일");
		setText(getCell(sheet4, 2, 2), "업무종료일");
		setText(getCell(sheet4, 2, 3), "대무자");
		setText(getCell(sheet4, 2, 4), "처리상태");
		
		cnt = 0;
		for(int i = 0; i< detailList.size();i++){
			EgovMap map = (EgovMap)detailList.get(i);
			
			String gbn = (String)map.get("utdTrmGbn"); 
			
			if(gbn.equals("M")){
			
			cell = getCell(sheet4, 3+cnt, 0);
			setText(cell,(String)map.get("utdDocNam"));
			
			cell = getCell(sheet4, 3+cnt, 1);
			setText(cell,(String)map.get("utwStrDat"));
			
			cell = getCell(sheet4, 3+cnt, 2);
			setText(cell,(String)map.get("utwEndDat"));
			
			cell = getCell(sheet4, 3+cnt, 3);
			setText(cell,(String)map.get("utwAgnId"));
			
			cell = getCell(sheet4, 3+cnt, 4);
			setText(cell,(String)map.get("sta"));
			
			cnt++;
			}
		}
		
		HSSFSheet sheet5 = wb.createSheet("격월업무");
		
		setText(getCell(sheet5, 2, 0), "업무명");
		setText(getCell(sheet5, 2, 1), "업무시작일");
		setText(getCell(sheet5, 2, 2), "업무종료일");
		setText(getCell(sheet5, 2, 3), "대무자");
		setText(getCell(sheet5, 2, 4), "처리상태");
		
		cnt = 0;
		for(int i = 0; i< detailList.size();i++){
			EgovMap map = (EgovMap)detailList.get(i);
			
			String gbn = (String)map.get("utdTrmGbn"); 
			
			if(gbn.equals("T")){
			
			cell = getCell(sheet5, 3+cnt, 0);
			setText(cell,(String)map.get("utdDocNam"));
			
			cell = getCell(sheet5, 3+cnt, 1);
			setText(cell,(String)map.get("utwStrDat"));
			
			cell = getCell(sheet5, 3+cnt, 2);
			setText(cell,(String)map.get("utwEndDat"));
			
			cell = getCell(sheet5, 3+cnt, 3);
			setText(cell,(String)map.get("utwAgnId"));
			
			cell = getCell(sheet5, 3+cnt, 4);
			setText(cell,(String)map.get("sta"));
			
			cnt++;
			}
		}
		
		HSSFSheet sheet6 = wb.createSheet("분기업무");
		
		setText(getCell(sheet6, 2, 0), "업무명");
		setText(getCell(sheet6, 2, 1), "업무시작일");
		setText(getCell(sheet6, 2, 2), "업무종료일");
		setText(getCell(sheet6, 2, 3), "대무자");
		setText(getCell(sheet6, 2, 4), "처리상태");
		
		cnt = 0;
		for(int i = 0; i< detailList.size();i++){
			EgovMap map = (EgovMap)detailList.get(i);
			
			String gbn = (String)map.get("utdTrmGbn"); 
			
			if(gbn.equals("Q")){
			
			cell = getCell(sheet6, 3+cnt, 0);
			setText(cell,(String)map.get("utdDocNam"));
			
			cell = getCell(sheet6, 3+cnt, 1);
			setText(cell,(String)map.get("utwStrDat"));
			
			cell = getCell(sheet6, 3+cnt, 2);
			setText(cell,(String)map.get("utwEndDat"));
			
			cell = getCell(sheet6, 3+cnt, 3);
			setText(cell,(String)map.get("utwAgnId"));
			
			cell = getCell(sheet6, 3+cnt, 4);
			setText(cell,(String)map.get("sta"));
			
			cnt++;
			}
		}
		
		HSSFSheet sheet7 = wb.createSheet("반기업무");
		
		setText(getCell(sheet7, 2, 0), "업무명");
		setText(getCell(sheet7, 2, 1), "업무시작일");
		setText(getCell(sheet7, 2, 2), "업무종료일");
		setText(getCell(sheet7, 2, 3), "대무자");
		setText(getCell(sheet7, 2, 4), "처리상태");
		
		cnt = 0;
		for(int i = 0; i< detailList.size();i++){
			EgovMap map = (EgovMap)detailList.get(i);
			
			String gbn = (String)map.get("utdTrmGbn"); 
			
			if(gbn.equals("H")){
			
			cell = getCell(sheet7, 3+cnt, 0);
			setText(cell,(String)map.get("utdDocNam"));
			
			cell = getCell(sheet7, 3+cnt, 1);
			setText(cell,(String)map.get("utwStrDat"));
			
			cell = getCell(sheet7, 3+cnt, 2);
			setText(cell,(String)map.get("utwEndDat"));
			
			cell = getCell(sheet7, 3+cnt, 3);
			setText(cell,(String)map.get("utwAgnId"));
			
			cell = getCell(sheet7, 3+cnt, 4);
			setText(cell,(String)map.get("sta"));
			
			cnt++;
			}
		}
		
		HSSFSheet sheet8 = wb.createSheet("연간업무");
		
		setText(getCell(sheet8, 2, 0), "업무명");
		setText(getCell(sheet8, 2, 1), "업무시작일");
		setText(getCell(sheet8, 2, 2), "업무종료일");
		setText(getCell(sheet8, 2, 3), "대무자");
		setText(getCell(sheet8, 2, 4), "처리상태");
		
		cnt = 0;
		for(int i = 0; i< detailList.size();i++){
			EgovMap map = (EgovMap)detailList.get(i);
			
			String gbn = (String)map.get("utdTrmGbn"); 
			
			if(gbn.equals("Y")){
			
			cell = getCell(sheet8, 3+cnt, 0);
			setText(cell,(String)map.get("utdDocNam"));
			
			cell = getCell(sheet8, 3+cnt, 1);
			setText(cell,(String)map.get("utwStrDat"));
			
			cell = getCell(sheet8, 3+cnt, 2);
			setText(cell,(String)map.get("utwEndDat"));
			
			cell = getCell(sheet8, 3+cnt, 3);
			setText(cell,(String)map.get("utwAgnId"));
			
			cell = getCell(sheet8, 3+cnt, 4);
			setText(cell,(String)map.get("sta"));
			
			cnt++;
			}
		}
		
		HSSFSheet sheet9 = wb.createSheet("비주기업무");
		
		setText(getCell(sheet9, 2, 0), "업무명");
		setText(getCell(sheet9, 2, 1), "업무시작일");
		setText(getCell(sheet9, 2, 2), "업무종료일");
		setText(getCell(sheet9, 2, 3), "대무자");
		setText(getCell(sheet9, 2, 4), "처리상태");
		
		cnt = 0;
		for(int i = 0; i< detailList.size();i++){
			EgovMap map = (EgovMap)detailList.get(i);
			
			String gbn = (String)map.get("utdTrmGbn");
			
			if(gbn.substring(0, 1).equals("N")){
			
			cell = getCell(sheet9, 3+cnt, 0);
			setText(cell,(String)map.get("utdDocNam"));
			
			cell = getCell(sheet9, 3+cnt, 1);
			setText(cell,(String)map.get("utwStrDat"));
			
			cell = getCell(sheet9, 3+cnt, 2);
			setText(cell,(String)map.get("utwEndDat"));
			
			cell = getCell(sheet9, 3+cnt, 3);
			setText(cell,(String)map.get("utwAgnId"));
			
			cell = getCell(sheet9, 3+cnt, 4);
			setText(cell,(String)map.get("sta"));
			
			cnt++;
			}
		}
	}	

}
