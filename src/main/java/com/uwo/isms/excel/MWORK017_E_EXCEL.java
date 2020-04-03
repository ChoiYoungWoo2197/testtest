package com.uwo.isms.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public class MWORK017_E_EXCEL extends AbstractExcelView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook wb, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
	
		HSSFCell cell = null;
		HSSFSheet sheet = wb.createSheet("개인정보흐름");
		
		
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 23));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 24, 44));
		setText(getCell(sheet, 1, 0), "개인정보내역");
		setText(getCell(sheet, 1, 24), "정보명(활동내역)");
				
		setText(getCell(sheet, 2, 0), "개인정보명");
		setText(getCell(sheet, 2, 1), "처리시스템");
		setText(getCell(sheet, 2, 2), "보유시스템");
		setText(getCell(sheet, 2, 3), "보유건수");
		setText(getCell(sheet, 2, 4), "보유기한");
		setText(getCell(sheet, 2, 5), "관리부서");
		setText(getCell(sheet, 2, 6), "수집대상");
		setText(getCell(sheet, 2, 7), "수집방법");
		setText(getCell(sheet, 2, 8), "수집목적");
		setText(getCell(sheet, 2, 9), "최초수집자");
		setText(getCell(sheet, 2, 10), "최초수집일시");
		setText(getCell(sheet, 2, 11), "제공자");
		setText(getCell(sheet, 2, 12), "주민등록번호");
		setText(getCell(sheet, 2, 13), "계좌번호");
		setText(getCell(sheet, 2, 14), "카드번호");
		setText(getCell(sheet, 2, 15), "ID");
		setText(getCell(sheet, 2, 16), "비밀번호");
		setText(getCell(sheet, 2, 17), "성명");
		setText(getCell(sheet, 2, 18), "주소");
		setText(getCell(sheet, 2, 19), "전화번호");
		setText(getCell(sheet, 2, 20), "이메일");
		setText(getCell(sheet, 2, 21), "기타1");
		setText(getCell(sheet, 2, 22), "기타2");
		setText(getCell(sheet, 2, 23), "기타3");
		setText(getCell(sheet, 2, 24), "정보사용구분");
		setText(getCell(sheet, 2, 25), "사용내역");
		setText(getCell(sheet, 2, 26), "정보사용일시");
		setText(getCell(sheet, 2, 27), "사용자");
		setText(getCell(sheet, 2, 28), "사용건수");
		setText(getCell(sheet, 2, 29), "사용경로");
		setText(getCell(sheet, 2, 30), "정보요청일시");
		setText(getCell(sheet, 2, 31), "요청자");
		setText(getCell(sheet, 2, 32), "요청경로");
		setText(getCell(sheet, 2, 33), "주민등록번호");
		setText(getCell(sheet, 2, 34), "계좌번호");
		setText(getCell(sheet, 2, 35), "카드번호");
		setText(getCell(sheet, 2, 36), "ID");
		setText(getCell(sheet, 2, 37), "비밀번호");
		setText(getCell(sheet, 2, 38), "성명");
		setText(getCell(sheet, 2, 39), "주소");
		setText(getCell(sheet, 2, 40), "전화번호");
		setText(getCell(sheet, 2, 41), "이메일");
		setText(getCell(sheet, 2, 42), "기타1");
		setText(getCell(sheet, 2, 43), "기타2");
		setText(getCell(sheet, 2, 44), "기타3");
		
		List<?> excelList = (List<?>)model.get("excelList");
		
		int eqCnt=0;
		int eqRow=0;
		int row=0;
		for(int i = 0; i< excelList.size();i++){
			EgovMap map = (EgovMap)excelList.get(i);
			EgovMap nextMap = new EgovMap();			
			
			if(i < excelList.size()-1){
				nextMap = (EgovMap)excelList.get(i+1);				
			}
			
			
			
			if(map.get("uimMtrKey").equals(nextMap.get("uimMtrKey"))){
				if(eqCnt<1){
					eqRow = 3+i;
				}
				eqCnt++;				
			}else{		
				row=3+i;
				if(eqCnt>0){
					row = eqRow;						
				}
				
				for(int j=0;j<24;j++){
					sheet.addMergedRegion(new CellRangeAddress(row, row+eqCnt, j, j));
				}
				
				eqCnt=0;
				
				
				cell = getCell(sheet, row, 0);			
				setText(cell,(String)map.get("uimAppNam"));
								
				cell = getCell(sheet, row, 1);
				setText(cell,(String)map.get("uimUseSys"));
				
				cell = getCell(sheet, row, 2);
				setText(cell,(String)map.get("uimSavSys"));
				
				cell = getCell(sheet, row, 3);
				setText(cell,(String)map.get("uimSavCnt"));
				
				String savDur= String.valueOf(map.get("uimSavDur"));
				if(savDur.equals("null")){
					savDur="";
				}
				cell = getCell(sheet, row, 4);
				setText(cell,savDur);
				
				cell = getCell(sheet, row, 5);
				setText(cell,(String)map.get("uimMngDep"));
				
				cell = getCell(sheet, row, 6);
				setText(cell,(String)map.get("uimGetOwn"));
				
				cell = getCell(sheet, row, 7);
				setText(cell,(String)map.get("uimGetWay"));
				
				cell = getCell(sheet, row, 8);
				setText(cell,(String)map.get("uimGetAim"));
				
				cell = getCell(sheet, row, 9);
				setText(cell,(String)map.get("uimGetWrk"));
				
				String getDat = String.valueOf(map.get("uimGetDat"));
				if(getDat.equals("null")){
					getDat = "";
				}
				cell = getCell(sheet, row, 10);
				setText(cell,getDat);
				
				cell = getCell(sheet, row, 11);
				setText(cell,(String)map.get("uimGivOwn"));
				
				cell = getCell(sheet, row, 12);
				setText(cell,(String)map.get("uimDatJno"));
	
				cell = getCell(sheet, row, 13);
				setText(cell,(String)map.get("uimDatCan"));
				
				cell = getCell(sheet, row, 14);
				setText(cell,(String)map.get("uimDatCdn"));
				
				cell = getCell(sheet, row, 15);
				setText(cell,(String)map.get("uimDatUid"));
				
				cell = getCell(sheet, row, 16);
				setText(cell,(String)map.get("uimDatPwd"));
				
				cell = getCell(sheet, row, 17);
				setText(cell,(String)map.get("uimDatNam"));
				
				cell = getCell(sheet, row, 18);
				setText(cell,(String)map.get("uimDatAdr"));
				
				cell = getCell(sheet, row, 19);
				setText(cell,(String)map.get("uimDatTno"));
				
				cell = getCell(sheet, row, 20);
				setText(cell,(String)map.get("uimDatEml"));
				
				cell = getCell(sheet, row, 21);
				setText(cell,(String)map.get("uimDatEtc1"));
				
				cell = getCell(sheet, row, 22);
				setText(cell,(String)map.get("uimDatEtc2"));
				
				cell = getCell(sheet, row, 23);
				setText(cell,(String)map.get("uimDatEtc3"));
				
								
			}
			
			String useGbn="";
			cell = getCell(sheet, 3+i, 24);
			if(map.get("uidUseGbn").equals("1")){
				useGbn = "수집";
			}else if(map.get("uidUseGbn").equals("2")){
				useGbn = "보유";
			}else if(map.get("uidUseGbn").equals("3")){
				useGbn = "이용";
			}else if(map.get("uidUseGbn").equals("4")){
				useGbn = "제공";
			}else{
				useGbn = "파기";
			}
			setText(cell,useGbn);
			
			cell = getCell(sheet, 3+i, 25);
			setText(cell,(String)map.get("uidUseCmt"));
			
			String useDat = String.valueOf(map.get("uidUseDat"));
			if(useDat.equals("null")){
				useDat = "";
			}
			cell = getCell(sheet, 3+i, 26);
			setText(cell,useDat);
			
			cell = getCell(sheet, 3+i, 27);
			setText(cell,(String)map.get("uidUseOwn"));
			
			cell = getCell(sheet, 3+i, 28);
			setText(cell,(String)map.get("uidUseCnt"));
			
			cell = getCell(sheet, 3+i, 29);
			setText(cell,(String)map.get("uidUseWay"));
			
			String reqDat = String.valueOf(map.get("uidReqDat"));
			if(reqDat.equals("null")){
				reqDat = "";
			}
			cell = getCell(sheet, 3+i, 30);
			setText(cell,reqDat);
			
			cell = getCell(sheet, 3+i, 31);
			setText(cell,(String)map.get("uidReqOwn"));
			
			cell = getCell(sheet, 3+i, 32);
			setText(cell,(String)map.get("uidReqWay"));
			
			cell = getCell(sheet, 3+i, 33);
			setText(cell,(String)map.get("uidDatJno"));
			
			cell = getCell(sheet, 3+i, 34);
			setText(cell,(String)map.get("uidDatCan"));
			
			cell = getCell(sheet, 3+i, 35);
			setText(cell,(String)map.get("uidDatCdn"));
			
			cell = getCell(sheet, 3+i, 36);
			setText(cell,(String)map.get("uidDatUid"));

			cell = getCell(sheet, 3+i, 37);
			setText(cell,(String)map.get("uidDatPwd"));
			
			cell = getCell(sheet, 3+i, 38);
			setText(cell,(String)map.get("uidDatNam"));
			
			cell = getCell(sheet, 3+i, 39);
			setText(cell,(String)map.get("uidDatAdr"));
			
			cell = getCell(sheet, 3+i, 40);
			setText(cell,(String)map.get("uidDatTno"));
			
			cell = getCell(sheet, 3+i, 41);
			setText(cell,(String)map.get("uidDatEml"));
			
			cell = getCell(sheet, 3+i, 42);
			setText(cell,(String)map.get("uidDatEtc1"));
			
			cell = getCell(sheet, 3+i, 43);
			setText(cell,(String)map.get("uidDatEtc2"));
			
			cell = getCell(sheet, 3+i, 44);
			setText(cell,(String)map.get("uidDatEtc3"));
		}		
	}
}