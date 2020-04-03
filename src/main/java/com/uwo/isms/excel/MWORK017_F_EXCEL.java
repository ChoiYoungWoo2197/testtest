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

public class MWORK017_F_EXCEL extends AbstractExcelView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook wb, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
	
		HSSFCell cell = null;
		HSSFSheet sheet = wb.createSheet("개인정보흐름");
		
		setText(getCell(sheet, 1, 0), "개인정보내역");
				
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
		
		
		List<?> flowMList = (List<?>)model.get("flowMList");
		
		for(int i = 0; i< flowMList.size();i++){
			EgovMap map = (EgovMap)flowMList.get(i);
			
			cell = getCell(sheet, 3+i, 0);			
			setText(cell,(String)map.get("uimAppNam"));
			
			cell = getCell(sheet, 3+i, 1);
			setText(cell,(String)map.get("uimUseSys"));
			
			cell = getCell(sheet, 3+i, 2);
			setText(cell,(String)map.get("uimSavSys"));
			
			cell = getCell(sheet, 3+i, 3);
			setText(cell,(String)map.get("uimSavCnt"));
			
			cell = getCell(sheet, 3+i, 4);
			setText(cell,(String)map.get("uimSavDur"));
			
			cell = getCell(sheet, 3+i, 5);
			setText(cell,(String)map.get("uimMngDep"));
			
			cell = getCell(sheet, 3+i, 6);
			setText(cell,(String)map.get("uimGetOwn"));
			
			cell = getCell(sheet, 3+i, 7);
			setText(cell,(String)map.get("uimGetWay"));
			
			cell = getCell(sheet, 3+i, 8);
			setText(cell,(String)map.get("uimGetAim"));
			
			cell = getCell(sheet, 3+i, 9);
			setText(cell,(String)map.get("uimGetWrk"));
			
			cell = getCell(sheet, 3+i, 10);
			setText(cell,(String)map.get("uimGetDat"));
			
			cell = getCell(sheet, 3+i, 11);
			setText(cell,(String)map.get("uimGivOwn"));
			
			cell = getCell(sheet, 3+i, 12);
			setText(cell,(String)map.get("uimDatJno"));

			cell = getCell(sheet, 3+i, 13);
			setText(cell,(String)map.get("uimDatCan"));
			
			cell = getCell(sheet, 3+i, 14);
			setText(cell,(String)map.get("uimDatCdn"));
			
			cell = getCell(sheet, 3+i, 15);
			setText(cell,(String)map.get("uimDatUid"));
			
			cell = getCell(sheet, 3+i, 16);
			setText(cell,(String)map.get("uimDatPwd"));
			
			cell = getCell(sheet, 3+i, 17);
			setText(cell,(String)map.get("uimDatNam"));
			
			cell = getCell(sheet, 3+i, 18);
			setText(cell,(String)map.get("uimDatAdr"));
			
			cell = getCell(sheet, 3+i, 19);
			setText(cell,(String)map.get("uimDatTno"));
			
			cell = getCell(sheet, 3+i, 20);
			setText(cell,(String)map.get("uimDatEml"));
			
			cell = getCell(sheet, 3+i, 21);
			setText(cell,(String)map.get("uimDatEtc1"));
			
			cell = getCell(sheet, 3+i, 22);
			setText(cell,(String)map.get("uimDatEtc2"));
			
			cell = getCell(sheet, 3+i, 23);
			setText(cell,(String)map.get("uimDatEtc3"));
		}
		
		setText(getCell(sheet, 5, 0), "정보명(활동내역)");
		
		setText(getCell(sheet, 6, 0), "정보사용구분");
		setText(getCell(sheet, 6, 1), "사용내역");
		setText(getCell(sheet, 6, 2), "정보사용일시");
		setText(getCell(sheet, 6, 3), "사용자");
		setText(getCell(sheet, 6, 4), "사용건수");
		setText(getCell(sheet, 6, 5), "사용경로");
		setText(getCell(sheet, 6, 6), "정보요청일시");
		setText(getCell(sheet, 6, 7), "요청자");
		setText(getCell(sheet, 6, 8), "요청경로");
		setText(getCell(sheet, 6, 9), "주민등록번호");
		setText(getCell(sheet, 6, 10), "계좌번호");
		setText(getCell(sheet, 6, 11), "카드번호");
		setText(getCell(sheet, 6, 12), "ID");
		setText(getCell(sheet, 6, 13), "비밀번호");
		setText(getCell(sheet, 6, 14), "성명");
		setText(getCell(sheet, 6, 15), "주소");
		setText(getCell(sheet, 6, 16), "전화번호");
		setText(getCell(sheet, 6, 17), "이메일");
		setText(getCell(sheet, 6, 18), "기타1");
		setText(getCell(sheet, 6, 19), "기타2");
		setText(getCell(sheet, 6, 20), "기타3");
		
		List<?> flowDList = (List<?>)model.get("flowDList");
		
		for(int i = 0; i< flowDList.size();i++){
			EgovMap map = (EgovMap)flowDList.get(i);
			
			cell = getCell(sheet, 7+i, 0);			
			setText(cell,(String)map.get("uidUseGbn"));
			
			cell = getCell(sheet, 7+i, 1);
			setText(cell,(String)map.get("uidUseCmt"));
			
			cell = getCell(sheet, 7+i, 2);
			setText(cell,(String)map.get("uidUseDat"));
			
			cell = getCell(sheet, 7+i, 3);
			setText(cell,(String)map.get("uidUseOwn"));
			
			cell = getCell(sheet, 7+i, 4);
			setText(cell,(String)map.get("uidUseCnt"));
			
			cell = getCell(sheet, 7+i, 5);
			setText(cell,(String)map.get("uidUseWay"));
			
			cell = getCell(sheet, 7+i, 6);
			setText(cell,(String)map.get("uidReqDat"));
			
			cell = getCell(sheet, 7+i, 7);
			setText(cell,(String)map.get("uidReqOwn"));
			
			cell = getCell(sheet, 7+i, 8);
			setText(cell,(String)map.get("uidReqWay"));
			
			cell = getCell(sheet, 7+i, 9);
			setText(cell,(String)map.get("uidDatJno"));
			
			cell = getCell(sheet, 7+i, 10);
			setText(cell,(String)map.get("uidDatCan"));
			
			cell = getCell(sheet, 7+i, 11);
			setText(cell,(String)map.get("uidDatCdn"));
			
			cell = getCell(sheet, 7+i, 12);
			setText(cell,(String)map.get("uidDatUid"));

			cell = getCell(sheet, 7+i, 13);
			setText(cell,(String)map.get("uidDatPwd"));
			
			cell = getCell(sheet, 7+i, 14);
			setText(cell,(String)map.get("uidDatNam"));
			
			cell = getCell(sheet, 7+i, 15);
			setText(cell,(String)map.get("uidDatAdr"));
			
			cell = getCell(sheet, 7+i, 16);
			setText(cell,(String)map.get("uidDatTno"));
			
			cell = getCell(sheet, 7+i, 17);
			setText(cell,(String)map.get("uidDatEml"));
			
			cell = getCell(sheet, 7+i, 18);
			setText(cell,(String)map.get("uidDatEtc1"));
			
			cell = getCell(sheet, 7+i, 19);
			setText(cell,(String)map.get("uidDatEtc2"));
			
			cell = getCell(sheet, 7+i, 20);
			setText(cell,(String)map.get("uidDatEtc3"));
		}
	}

}
