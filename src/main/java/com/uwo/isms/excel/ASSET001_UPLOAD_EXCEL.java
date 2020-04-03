package com.uwo.isms.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public class ASSET001_UPLOAD_EXCEL extends AbstractExcelView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook wb, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HSSFSheet sheet = wb.createSheet("서버");
		HSSFCellStyle cellStyle = wb.createCellStyle();
		
		setText(getCell(sheet, 0, 0), "자산업로드");
		setText(getStyleCell(sheet, cellStyle, 2, 0), "자산관리키");
		setText(getStyleCell(sheet, cellStyle, 2, 1), "그룹코드");
		setText(getStyleCell(sheet, cellStyle, 2, 2), "그룹명");
		setText(getStyleCell(sheet, cellStyle, 2, 3), "자산분류코드");
		setText(getStyleCell(sheet, cellStyle, 2, 4), "서비스코드");
		setText(getStyleCell(sheet, cellStyle, 2, 5), "서비스명");
		setText(getStyleCell(sheet, cellStyle, 2, 6), "서비스상세");
		setText(getStyleCell(sheet, cellStyle, 2, 7), "관리부서코드");
		setText(getStyleCell(sheet, cellStyle, 2, 8), "관리부서명");
		setText(getStyleCell(sheet, cellStyle, 2, 9), "관리담당자ID");
		setText(getStyleCell(sheet, cellStyle, 2, 10), "관리담당자명");
		setText(getStyleCell(sheet, cellStyle, 2, 11), "관리책임자ID");
		setText(getStyleCell(sheet, cellStyle, 2, 12), "관리책임자명");
		setText(getStyleCell(sheet, cellStyle, 2, 13), "운영부서코드");
		setText(getStyleCell(sheet, cellStyle, 2, 14), "운영부서명");
		setText(getStyleCell(sheet, cellStyle, 2, 15), "운영담당자ID");
		setText(getStyleCell(sheet, cellStyle, 2, 16), "운영담당자명");
		setText(getStyleCell(sheet, cellStyle, 2, 17), "운영책임자ID");
		setText(getStyleCell(sheet, cellStyle, 2, 18), "운영책임자명");
		setText(getStyleCell(sheet, cellStyle, 2, 19), "자산유형코드");
		setText(getStyleCell(sheet, cellStyle, 2, 20), "자산유형명");
		setText(getStyleCell(sheet, cellStyle, 2, 21), "자산명");
		setText(getStyleCell(sheet, cellStyle, 2, 22), "용도");
		setText(getStyleCell(sheet, cellStyle, 2, 23), "위치");
		setText(getStyleCell(sheet, cellStyle, 2, 24), "취약점그룹코드");
		setText(getStyleCell(sheet, cellStyle, 2, 25), "취약점그룹명");
		setText(getStyleCell(sheet, cellStyle, 2, 26), "기밀성(1/2/3)");
		setText(getStyleCell(sheet, cellStyle, 2, 27), "무결성(1/2/3)");
		setText(getStyleCell(sheet, cellStyle, 2, 28), "가용성(1/2/3)");
		setText(getStyleCell(sheet, cellStyle, 2, 29), "중요도점수");
		setText(getStyleCell(sheet, cellStyle, 2, 30), "중요도등급");
		setText(getStyleCell(sheet, cellStyle, 2, 31), "ISMS인증대상(Y/N)");
		setText(getStyleCell(sheet, cellStyle, 2, 32), "샘플링대상(Y/N)");
		setText(getStyleCell(sheet, cellStyle, 2, 33), "기반시설대상(Y/N)");
		setText(getStyleCell(sheet, cellStyle, 2, 34), "개인정보보유(Y/N)");
		setText(getStyleCell(sheet, cellStyle, 2, 35), "사용유무(Y/N)");
		setText(getStyleCell(sheet, cellStyle, 2, 36), "제조사");
		setText(getStyleCell(sheet, cellStyle, 2, 37), "모델명");
		setText(getStyleCell(sheet, cellStyle, 2, 38), "OS");
		setText(getStyleCell(sheet, cellStyle, 2, 39), "OS버전");
		setText(getStyleCell(sheet, cellStyle, 2, 40), "IP");
		setText(getStyleCell(sheet, cellStyle, 1000, 0), "");
		
		
		HSSFSheet sheet2 = wb.createSheet("네트워크");
		HSSFCellStyle cellStyle2 = wb.createCellStyle();
		
		setText(getCell(sheet2, 0, 0), "자산업로드");
		setText(getStyleCell(sheet2, cellStyle2, 2, 0), "자산관리키");
		setText(getStyleCell(sheet2, cellStyle2, 2, 1), "그룹코드");
		setText(getStyleCell(sheet2, cellStyle2, 2, 2), "그룹명");
		setText(getStyleCell(sheet2, cellStyle2, 2, 3), "자산분류코드");
		setText(getStyleCell(sheet2, cellStyle2, 2, 4), "서비스코드");
		setText(getStyleCell(sheet2, cellStyle2, 2, 5), "서비스명");
		setText(getStyleCell(sheet2, cellStyle2, 2, 6), "서비스상세");
		setText(getStyleCell(sheet2, cellStyle2, 2, 7), "관리부서코드");
		setText(getStyleCell(sheet2, cellStyle2, 2, 8), "관리부서명");
		setText(getStyleCell(sheet2, cellStyle2, 2, 9), "관리담당자ID");
		setText(getStyleCell(sheet2, cellStyle2, 2, 10), "관리담당자명");
		setText(getStyleCell(sheet2, cellStyle2, 2, 11), "관리책임자ID");
		setText(getStyleCell(sheet2, cellStyle2, 2, 12), "관리책임자명");
		setText(getStyleCell(sheet2, cellStyle2, 2, 13), "운영부서코드");
		setText(getStyleCell(sheet2, cellStyle2, 2, 14), "운영부서명");
		setText(getStyleCell(sheet2, cellStyle2, 2, 15), "운영담당자ID");
		setText(getStyleCell(sheet2, cellStyle2, 2, 16), "운영담당자명");
		setText(getStyleCell(sheet2, cellStyle2, 2, 17), "운영책임자ID");
		setText(getStyleCell(sheet2, cellStyle2, 2, 18), "운영책임자명");
		setText(getStyleCell(sheet2, cellStyle2, 2, 19), "자산유형코드");
		setText(getStyleCell(sheet2, cellStyle2, 2, 20), "자산유형명");
		setText(getStyleCell(sheet2, cellStyle2, 2, 21), "자산명");
		setText(getStyleCell(sheet2, cellStyle2, 2, 22), "용도");
		setText(getStyleCell(sheet2, cellStyle2, 2, 23), "위치");
		setText(getStyleCell(sheet2, cellStyle2, 2, 24), "취약점그룹코드");
		setText(getStyleCell(sheet2, cellStyle2, 2, 25), "취약점그룹명");
		setText(getStyleCell(sheet2, cellStyle2, 2, 26), "기밀성(1/2/3)");
		setText(getStyleCell(sheet2, cellStyle2, 2, 27), "무결성(1/2/3)");
		setText(getStyleCell(sheet2, cellStyle2, 2, 28), "가용성(1/2/3)");
		setText(getStyleCell(sheet2, cellStyle2, 2, 29), "중요도점수");
		setText(getStyleCell(sheet2, cellStyle2, 2, 30), "중요도등급");
		setText(getStyleCell(sheet2, cellStyle2, 2, 31), "ISMS인증대상(Y/N)");
		setText(getStyleCell(sheet2, cellStyle2, 2, 32), "샘플링대상(Y/N)");
		setText(getStyleCell(sheet2, cellStyle2, 2, 33), "기반시설대상(Y/N)");
		setText(getStyleCell(sheet2, cellStyle2, 2, 34), "개인정보보유(Y/N)");
		setText(getStyleCell(sheet2, cellStyle2, 2, 35), "사용유무(Y/N)");
		setText(getStyleCell(sheet2, cellStyle2, 2, 36), "제조사");
		setText(getStyleCell(sheet2, cellStyle2, 2, 37), "모델명");
		setText(getStyleCell(sheet2, cellStyle2, 2, 38), "OS버전");
		setText(getStyleCell(sheet2, cellStyle2, 2, 39), "IP");
		setText(getStyleCell(sheet2, cellStyle2, 1000, 0), "");

		
		HSSFSheet sheet3 = wb.createSheet("전자정보");
		HSSFCellStyle cellStyle3 = wb.createCellStyle();
		
		setText(getCell(sheet3, 0, 0), "자산업로드");
		setText(getStyleCell(sheet3, cellStyle3, 2, 0), "자산관리키");
		setText(getStyleCell(sheet3, cellStyle3, 2, 1), "그룹코드");
		setText(getStyleCell(sheet3, cellStyle3, 2, 2), "그룹명");
		setText(getStyleCell(sheet3, cellStyle3, 2, 3), "자산분류코드");
		setText(getStyleCell(sheet3, cellStyle3, 2, 4), "서비스코드");
		setText(getStyleCell(sheet3, cellStyle3, 2, 5), "서비스명");
		setText(getStyleCell(sheet3, cellStyle3, 2, 6), "서비스상세");
		setText(getStyleCell(sheet3, cellStyle3, 2, 7), "관리부서코드");
		setText(getStyleCell(sheet3, cellStyle3, 2, 8), "관리부서명");
		setText(getStyleCell(sheet3, cellStyle3, 2, 9), "관리담당자ID");
		setText(getStyleCell(sheet3, cellStyle3, 2, 10), "관리담당자명");
		setText(getStyleCell(sheet3, cellStyle3, 2, 11), "관리책임자ID");
		setText(getStyleCell(sheet3, cellStyle3, 2, 12), "관리책임자명");
		setText(getStyleCell(sheet3, cellStyle3, 2, 13), "운영부서코드");
		setText(getStyleCell(sheet3, cellStyle3, 2, 14), "운영부서명");
		setText(getStyleCell(sheet3, cellStyle3, 2, 15), "운영담당자ID");
		setText(getStyleCell(sheet3, cellStyle3, 2, 16), "운영담당자명");
		setText(getStyleCell(sheet3, cellStyle3, 2, 17), "운영책임자ID");
		setText(getStyleCell(sheet3, cellStyle3, 2, 18), "운영책임자명");
		setText(getStyleCell(sheet3, cellStyle3, 2, 19), "자산유형코드");
		setText(getStyleCell(sheet3, cellStyle3, 2, 20), "자산유형명");
		setText(getStyleCell(sheet3, cellStyle3, 2, 21), "자산명");
		setText(getStyleCell(sheet3, cellStyle3, 2, 22), "용도");
		setText(getStyleCell(sheet3, cellStyle3, 2, 23), "위치");
		setText(getStyleCell(sheet3, cellStyle3, 2, 24), "취약점그룹코드");
		setText(getStyleCell(sheet3, cellStyle3, 2, 25), "취약점그룹명");
		setText(getStyleCell(sheet3, cellStyle3, 2, 26), "기밀성(1/2/3)");
		setText(getStyleCell(sheet3, cellStyle3, 2, 27), "무결성(1/2/3)");
		setText(getStyleCell(sheet3, cellStyle3, 2, 28), "가용성(1/2/3)");
		setText(getStyleCell(sheet3, cellStyle3, 2, 29), "중요도점수");
		setText(getStyleCell(sheet3, cellStyle3, 2, 30), "중요도등급");
		setText(getStyleCell(sheet3, cellStyle3, 2, 31), "ISMS인증대상(Y/N)");
		setText(getStyleCell(sheet3, cellStyle3, 2, 32), "샘플링대상(Y/N)");
		setText(getStyleCell(sheet3, cellStyle3, 2, 33), "기반시설대상(Y/N)");
		setText(getStyleCell(sheet3, cellStyle3, 2, 34), "개인정보보유(Y/N)");
		setText(getStyleCell(sheet3, cellStyle3, 2, 35), "사용유무(Y/N)");
		setText(getStyleCell(sheet3, cellStyle3, 2, 36), "DBMS명");
		setText(getStyleCell(sheet3, cellStyle3, 2, 37), "DBMS버전");
		setText(getStyleCell(sheet3, cellStyle3, 2, 38), "S/W버전");
		setText(getStyleCell(sheet3, cellStyle3, 2, 39), "설치서버IP");
		setText(getStyleCell(sheet3, cellStyle3, 2, 40), "호스트명");
		setText(getStyleCell(sheet3, cellStyle3, 1000, 0), "");

		
		HSSFSheet sheet4 = wb.createSheet("보안장비");
		HSSFCellStyle cellStyle4 = wb.createCellStyle();
		
		setText(getCell(sheet4, 0, 0), "자산업로드");
		setText(getStyleCell(sheet4, cellStyle4, 2, 0), "자산관리키");
		setText(getStyleCell(sheet4, cellStyle4, 2, 1), "그룹코드");
		setText(getStyleCell(sheet4, cellStyle4, 2, 2), "그룹명");
		setText(getStyleCell(sheet4, cellStyle4, 2, 3), "자산분류코드");
		setText(getStyleCell(sheet4, cellStyle4, 2, 4), "서비스코드");
		setText(getStyleCell(sheet4, cellStyle4, 2, 5), "서비스명");
		setText(getStyleCell(sheet4, cellStyle4, 2, 6), "서비스상세");
		setText(getStyleCell(sheet4, cellStyle4, 2, 7), "관리부서코드");
		setText(getStyleCell(sheet4, cellStyle4, 2, 8), "관리부서명");
		setText(getStyleCell(sheet4, cellStyle4, 2, 9), "관리담당자ID");
		setText(getStyleCell(sheet4, cellStyle4, 2, 10), "관리담당자명");
		setText(getStyleCell(sheet4, cellStyle4, 2, 11), "관리책임자ID");
		setText(getStyleCell(sheet4, cellStyle4, 2, 12), "관리책임자명");
		setText(getStyleCell(sheet4, cellStyle4, 2, 13), "운영부서코드");
		setText(getStyleCell(sheet4, cellStyle4, 2, 14), "운영부서명");
		setText(getStyleCell(sheet4, cellStyle4, 2, 15), "운영담당자ID");
		setText(getStyleCell(sheet4, cellStyle4, 2, 16), "운영담당자명");
		setText(getStyleCell(sheet4, cellStyle4, 2, 17), "운영책임자ID");
		setText(getStyleCell(sheet4, cellStyle4, 2, 18), "운영책임자명");
		setText(getStyleCell(sheet4, cellStyle4, 2, 19), "자산유형코드");
		setText(getStyleCell(sheet4, cellStyle4, 2, 20), "자산유형명");
		setText(getStyleCell(sheet4, cellStyle4, 2, 21), "자산명");
		setText(getStyleCell(sheet4, cellStyle4, 2, 22), "용도");
		setText(getStyleCell(sheet4, cellStyle4, 2, 23), "위치");
		setText(getStyleCell(sheet4, cellStyle4, 2, 24), "취약점그룹코드");
		setText(getStyleCell(sheet4, cellStyle4, 2, 25), "취약점그룹명");
		setText(getStyleCell(sheet4, cellStyle4, 2, 26), "기밀성(1/2/3)");
		setText(getStyleCell(sheet4, cellStyle4, 2, 27), "무결성(1/2/3)");
		setText(getStyleCell(sheet4, cellStyle4, 2, 28), "가용성(1/2/3)");
		setText(getStyleCell(sheet4, cellStyle4, 2, 29), "중요도점수");
		setText(getStyleCell(sheet4, cellStyle4, 2, 30), "중요도등급");
		setText(getStyleCell(sheet4, cellStyle4, 2, 31), "ISMS인증대상(Y/N)");
		setText(getStyleCell(sheet4, cellStyle4, 2, 32), "샘플링대상(Y/N)");
		setText(getStyleCell(sheet4, cellStyle4, 2, 33), "기반시설대상(Y/N)");
		setText(getStyleCell(sheet4, cellStyle4, 2, 34), "개인정보보유(Y/N)");
		setText(getStyleCell(sheet4, cellStyle4, 2, 35), "사용유무(Y/N)");
		setText(getStyleCell(sheet4, cellStyle4, 2, 36), "제조사");
		setText(getStyleCell(sheet4, cellStyle4, 2, 37), "모델명");
		setText(getStyleCell(sheet4, cellStyle4, 2, 38), "IP");
		setText(getStyleCell(sheet4, cellStyle4, 1000, 0), "");

		
		HSSFSheet sheet5 = wb.createSheet("단말기");
		HSSFCellStyle cellStyle5 = wb.createCellStyle();
		
		setText(getCell(sheet5, 0, 0), "자산업로드");
		setText(getStyleCell(sheet5, cellStyle5, 2, 0), "자산관리키");
		setText(getStyleCell(sheet5, cellStyle5, 2, 1), "그룹코드");
		setText(getStyleCell(sheet5, cellStyle5, 2, 2), "그룹명");
		setText(getStyleCell(sheet5, cellStyle5, 2, 3), "자산분류코드");
		setText(getStyleCell(sheet5, cellStyle5, 2, 4), "서비스코드");
		setText(getStyleCell(sheet5, cellStyle5, 2, 5), "서비스명");
		setText(getStyleCell(sheet5, cellStyle5, 2, 6), "서비스상세");
		setText(getStyleCell(sheet5, cellStyle5, 2, 7), "관리부서코드");
		setText(getStyleCell(sheet5, cellStyle5, 2, 8), "관리부서명");
		setText(getStyleCell(sheet5, cellStyle5, 2, 9), "관리담당자ID");
		setText(getStyleCell(sheet5, cellStyle5, 2, 10), "관리담당자명");
		setText(getStyleCell(sheet5, cellStyle5, 2, 11), "관리책임자ID");
		setText(getStyleCell(sheet5, cellStyle5, 2, 12), "관리책임자명");
		setText(getStyleCell(sheet5, cellStyle5, 2, 13), "운영부서코드");
		setText(getStyleCell(sheet5, cellStyle5, 2, 14), "운영부서명");
		setText(getStyleCell(sheet5, cellStyle5, 2, 15), "운영담당자ID");
		setText(getStyleCell(sheet5, cellStyle5, 2, 16), "운영담당자명");
		setText(getStyleCell(sheet5, cellStyle5, 2, 17), "운영책임자ID");
		setText(getStyleCell(sheet5, cellStyle5, 2, 18), "운영책임자명");
		setText(getStyleCell(sheet5, cellStyle5, 2, 19), "자산유형코드");
		setText(getStyleCell(sheet5, cellStyle5, 2, 20), "자산유형명");
		setText(getStyleCell(sheet5, cellStyle5, 2, 21), "자산명");
		setText(getStyleCell(sheet5, cellStyle5, 2, 22), "용도");
		setText(getStyleCell(sheet5, cellStyle5, 2, 23), "위치");
		setText(getStyleCell(sheet5, cellStyle5, 2, 24), "취약점그룹코드");
		setText(getStyleCell(sheet5, cellStyle5, 2, 25), "취약점그룹명");
		setText(getStyleCell(sheet5, cellStyle5, 2, 26), "기밀성(1/2/3)");
		setText(getStyleCell(sheet5, cellStyle5, 2, 27), "무결성(1/2/3)");
		setText(getStyleCell(sheet5, cellStyle5, 2, 28), "가용성(1/2/3)");
		setText(getStyleCell(sheet5, cellStyle5, 2, 29), "중요도점수");
		setText(getStyleCell(sheet5, cellStyle5, 2, 30), "중요도등급");
		setText(getStyleCell(sheet5, cellStyle5, 2, 31), "ISMS인증대상(Y/N)");
		setText(getStyleCell(sheet5, cellStyle5, 2, 32), "샘플링대상(Y/N)");
		setText(getStyleCell(sheet5, cellStyle5, 2, 33), "기반시설대상(Y/N)");
		setText(getStyleCell(sheet5, cellStyle5, 2, 34), "개인정보보유(Y/N)");
		setText(getStyleCell(sheet5, cellStyle5, 2, 35), "사용유무(Y/N)");
		setText(getStyleCell(sheet5, cellStyle5, 2, 36), "제조사");
		setText(getStyleCell(sheet5, cellStyle5, 2, 37), "모델명");
		setText(getStyleCell(sheet5, cellStyle5, 1000, 0), "");

		
		HSSFSheet sheet6 = wb.createSheet("응용프로그램");
		HSSFCellStyle cellStyle6 = wb.createCellStyle();
		
		setText(getCell(sheet6, 0, 0), "자산업로드");
		setText(getStyleCell(sheet6, cellStyle6, 2, 0), "자산관리키");
		setText(getStyleCell(sheet6, cellStyle6, 2, 1), "그룹코드");
		setText(getStyleCell(sheet6, cellStyle6, 2, 2), "그룹명");
		setText(getStyleCell(sheet6, cellStyle6, 2, 3), "자산분류코드");
		setText(getStyleCell(sheet6, cellStyle6, 2, 4), "서비스코드");
		setText(getStyleCell(sheet6, cellStyle6, 2, 5), "서비스명");
		setText(getStyleCell(sheet6, cellStyle6, 2, 6), "서비스상세");
		setText(getStyleCell(sheet6, cellStyle6, 2, 7), "관리부서코드");
		setText(getStyleCell(sheet6, cellStyle6, 2, 8), "관리부서명");
		setText(getStyleCell(sheet6, cellStyle6, 2, 9), "관리담당자ID");
		setText(getStyleCell(sheet6, cellStyle6, 2, 10), "관리담당자명");
		setText(getStyleCell(sheet6, cellStyle6, 2, 11), "관리책임자ID");
		setText(getStyleCell(sheet6, cellStyle6, 2, 12), "관리책임자명");
		setText(getStyleCell(sheet6, cellStyle6, 2, 13), "운영부서코드");
		setText(getStyleCell(sheet6, cellStyle6, 2, 14), "운영부서명");
		setText(getStyleCell(sheet6, cellStyle6, 2, 15), "운영담당자ID");
		setText(getStyleCell(sheet6, cellStyle6, 2, 16), "운영담당자명");
		setText(getStyleCell(sheet6, cellStyle6, 2, 17), "운영책임자ID");
		setText(getStyleCell(sheet6, cellStyle6, 2, 18), "운영책임자명");
		setText(getStyleCell(sheet6, cellStyle6, 2, 19), "자산유형코드");
		setText(getStyleCell(sheet6, cellStyle6, 2, 20), "자산유형명");
		setText(getStyleCell(sheet6, cellStyle6, 2, 21), "자산명");
		setText(getStyleCell(sheet6, cellStyle6, 2, 22), "용도");
		setText(getStyleCell(sheet6, cellStyle6, 2, 23), "위치");
		setText(getStyleCell(sheet6, cellStyle6, 2, 24), "취약점그룹코드");
		setText(getStyleCell(sheet6, cellStyle6, 2, 25), "취약점그룹명");
		setText(getStyleCell(sheet6, cellStyle6, 2, 26), "기밀성(1/2/3)");
		setText(getStyleCell(sheet6, cellStyle6, 2, 27), "무결성(1/2/3)");
		setText(getStyleCell(sheet6, cellStyle6, 2, 28), "가용성(1/2/3)");
		setText(getStyleCell(sheet6, cellStyle6, 2, 29), "중요도점수");
		setText(getStyleCell(sheet6, cellStyle6, 2, 30), "중요도등급");
		setText(getStyleCell(sheet6, cellStyle6, 2, 31), "ISMS인증대상(Y/N)");
		setText(getStyleCell(sheet6, cellStyle6, 2, 32), "샘플링대상(Y/N)");
		setText(getStyleCell(sheet6, cellStyle6, 2, 33), "기반시설대상(Y/N)");
		setText(getStyleCell(sheet6, cellStyle6, 2, 34), "개인정보보유(Y/N)");
		setText(getStyleCell(sheet6, cellStyle6, 2, 35), "사용유무(Y/N)");
		setText(getStyleCell(sheet6, cellStyle6, 2, 36), "OS버전");
		setText(getStyleCell(sheet6, cellStyle6, 2, 37), "S/W버전");
		setText(getStyleCell(sheet6, cellStyle6, 2, 38), "URL");
		setText(getStyleCell(sheet6, cellStyle6, 2, 39), "IP");
		setText(getStyleCell(sheet6, cellStyle6, 2, 40), "설치서버IP");
		setText(getStyleCell(sheet6, cellStyle6, 2, 41), "호스트명");
		setText(getStyleCell(sheet6, cellStyle6, 1000, 0), "");

		
		HSSFSheet sheet7 = wb.createSheet("물리적자산");
		HSSFCellStyle cellStyle7 = wb.createCellStyle();
		
		setText(getStyleCell(sheet7, cellStyle7, 2, 0), "자산관리키");
		setText(getStyleCell(sheet7, cellStyle7, 2, 1), "그룹코드");
		setText(getStyleCell(sheet7, cellStyle7, 2, 2), "그룹명");
		setText(getStyleCell(sheet7, cellStyle7, 2, 3), "자산분류코드");
		setText(getStyleCell(sheet7, cellStyle7, 2, 4), "서비스코드");
		setText(getStyleCell(sheet7, cellStyle7, 2, 5), "서비스명");
		setText(getStyleCell(sheet7, cellStyle7, 2, 6), "서비스상세");
		setText(getStyleCell(sheet7, cellStyle7, 2, 7), "관리부서코드");
		setText(getStyleCell(sheet7, cellStyle7, 2, 8), "관리부서명");
		setText(getStyleCell(sheet7, cellStyle7, 2, 9), "관리담당자ID");
		setText(getStyleCell(sheet7, cellStyle7, 2, 10), "관리담당자명");
		setText(getStyleCell(sheet7, cellStyle7, 2, 11), "관리책임자ID");
		setText(getStyleCell(sheet7, cellStyle7, 2, 12), "관리책임자명");
		setText(getStyleCell(sheet7, cellStyle7, 2, 13), "운영부서코드");
		setText(getStyleCell(sheet7, cellStyle7, 2, 14), "운영부서명");
		setText(getStyleCell(sheet7, cellStyle7, 2, 15), "운영담당자ID");
		setText(getStyleCell(sheet7, cellStyle7, 2, 16), "운영담당자명");
		setText(getStyleCell(sheet7, cellStyle7, 2, 17), "운영책임자ID");
		setText(getStyleCell(sheet7, cellStyle7, 2, 18), "운영책임자명");
		setText(getStyleCell(sheet7, cellStyle7, 2, 19), "자산유형코드");
		setText(getStyleCell(sheet7, cellStyle7, 2, 20), "자산유형명");
		setText(getStyleCell(sheet7, cellStyle7, 2, 21), "자산명");
		setText(getStyleCell(sheet7, cellStyle7, 2, 22), "용도");
		setText(getStyleCell(sheet7, cellStyle7, 2, 23), "위치");
		setText(getStyleCell(sheet7, cellStyle7, 2, 24), "취약점그룹코드");
		setText(getStyleCell(sheet7, cellStyle7, 2, 25), "취약점그룹명");
		setText(getStyleCell(sheet7, cellStyle7, 2, 26), "기밀성(1/2/3)");
		setText(getStyleCell(sheet7, cellStyle7, 2, 27), "무결성(1/2/3)");
		setText(getStyleCell(sheet7, cellStyle7, 2, 28), "가용성(1/2/3)");
		setText(getStyleCell(sheet7, cellStyle7, 2, 29), "중요도점수");
		setText(getStyleCell(sheet7, cellStyle7, 2, 30), "중요도등급");
		setText(getStyleCell(sheet7, cellStyle7, 2, 31), "ISMS인증대상(Y/N)");
		setText(getStyleCell(sheet7, cellStyle7, 2, 32), "샘플링대상(Y/N)");
		setText(getStyleCell(sheet7, cellStyle7, 2, 33), "기반시설대상(Y/N)");
		setText(getStyleCell(sheet7, cellStyle7, 2, 34), "개인정보보유(Y/N)");
		setText(getStyleCell(sheet7, cellStyle7, 2, 35), "사용유무(Y/N)");
		setText(getStyleCell(sheet7, cellStyle7, 2, 36), "제조사");
		setText(getStyleCell(sheet7, cellStyle7, 1000, 0), "");

		
		HSSFSheet sheet8 = wb.createSheet("문서");
		HSSFCellStyle cellStyle8 = wb.createCellStyle();
		
		setText(getCell(sheet8, 0, 0), "자산업로드");
		setText(getStyleCell(sheet8, cellStyle8, 2, 0), "자산관리키");
		setText(getStyleCell(sheet8, cellStyle8, 2, 1), "그룹코드");
		setText(getStyleCell(sheet8, cellStyle8, 2, 2), "그룹명");
		setText(getStyleCell(sheet8, cellStyle8, 2, 3), "자산분류코드");
		setText(getStyleCell(sheet8, cellStyle8, 2, 4), "서비스코드");
		setText(getStyleCell(sheet8, cellStyle8, 2, 5), "서비스명");
		setText(getStyleCell(sheet8, cellStyle8, 2, 6), "서비스상세");
		setText(getStyleCell(sheet8, cellStyle8, 2, 7), "관리부서코드");
		setText(getStyleCell(sheet8, cellStyle8, 2, 8), "관리부서명");
		setText(getStyleCell(sheet8, cellStyle8, 2, 9), "관리담당자ID");
		setText(getStyleCell(sheet8, cellStyle8, 2, 10), "관리담당자명");
		setText(getStyleCell(sheet8, cellStyle8, 2, 11), "관리책임자ID");
		setText(getStyleCell(sheet8, cellStyle8, 2, 12), "관리책임자명");
		setText(getStyleCell(sheet8, cellStyle8, 2, 13), "운영부서코드");
		setText(getStyleCell(sheet8, cellStyle8, 2, 14), "운영부서명");
		setText(getStyleCell(sheet8, cellStyle8, 2, 15), "운영담당자ID");
		setText(getStyleCell(sheet8, cellStyle8, 2, 16), "운영담당자명");
		setText(getStyleCell(sheet8, cellStyle8, 2, 17), "운영책임자ID");
		setText(getStyleCell(sheet8, cellStyle8, 2, 18), "운영책임자명");
		setText(getStyleCell(sheet8, cellStyle8, 2, 19), "자산유형코드");
		setText(getStyleCell(sheet8, cellStyle8, 2, 20), "자산유형명");
		setText(getStyleCell(sheet8, cellStyle8, 2, 21), "자산명");
		setText(getStyleCell(sheet8, cellStyle8, 2, 22), "용도");
		setText(getStyleCell(sheet8, cellStyle8, 2, 23), "위치");
		setText(getStyleCell(sheet8, cellStyle8, 2, 24), "취약점그룹코드");
		setText(getStyleCell(sheet8, cellStyle8, 2, 25), "취약점그룹명");
		setText(getStyleCell(sheet8, cellStyle8, 2, 26), "기밀성(1/2/3)");
		setText(getStyleCell(sheet8, cellStyle8, 2, 27), "무결성(1/2/3)");
		setText(getStyleCell(sheet8, cellStyle8, 2, 28), "가용성(1/2/3)");
		setText(getStyleCell(sheet8, cellStyle8, 2, 29), "중요도점수");
		setText(getStyleCell(sheet8, cellStyle8, 2, 30), "중요도등급");
		setText(getStyleCell(sheet8, cellStyle8, 2, 31), "ISMS인증대상(Y/N)");
		setText(getStyleCell(sheet8, cellStyle8, 2, 32), "샘플링대상(Y/N)");
		setText(getStyleCell(sheet8, cellStyle8, 2, 33), "기반시설대상(Y/N)");
		setText(getStyleCell(sheet8, cellStyle8, 2, 34), "개인정보보유(Y/N)");
		setText(getStyleCell(sheet8, cellStyle8, 2, 35), "사용유무(Y/N)");
		setText(getStyleCell(sheet8, cellStyle8, 2, 36), "보존연한");
		setText(getStyleCell(sheet8, cellStyle8, 2, 37), "문서분류");
		setText(getStyleCell(sheet8, cellStyle8, 1000, 0), "");

		
		
		HSSFSheet sheet9 = wb.createSheet("자산유형");
		HSSFCellStyle cellStyle9 = wb.createCellStyle();
		
		
		HSSFSheet sheet10 = wb.createSheet("코드");
		HSSFCellStyle cellStyle10 = wb.createCellStyle();
		
		setText(getCell(sheet10, 0, 0), "자산분류");
		setText(getStyleCell(sheet10, cellStyle10, 2, 0), "자산분류명");
		setText(getStyleCell(sheet10, cellStyle10, 2, 1), "자산분류코드");
		
		List<?> list = (List<?>)model.get("list");
		for(int i = 0; i< list.size();i++){
			EgovMap map = (EgovMap)list.get(i);
			setText(getStyleCell(sheet10, cellStyle10, 3+i, 0),(String)map.get("uacCatNam"));
			setText(getStyleCell(sheet10, cellStyle10, 3+i, 1),(String)map.get("uacCatCod"));

		}
		
		setText(getCell(sheet10, 0, 3), "서비스코드");
		setText(getStyleCell(sheet10, cellStyle10, 2, 3), "서비스명");
		setText(getStyleCell(sheet10, cellStyle10, 2, 4), "서비스코드");
		
		List<?> svcList = (List<?>)model.get("svcList");
		for(int i = 0; i< svcList.size();i++){
			EgovMap map = (EgovMap)svcList.get(i);
			setText(getStyleCell(sheet10, cellStyle10, 3+i, 3),(String)map.get("name"));
			setText(getStyleCell(sheet10, cellStyle10, 3+i, 4),(String)map.get("code"));
		}
		
		setText(getCell(sheet10, 0, 6), "부서코드");
		setText(getStyleCell(sheet10, cellStyle10, 2, 6), "부서명");
		setText(getStyleCell(sheet10, cellStyle10, 2, 7), "부서코드");
		
		
		List<?> depList = (List<?>)model.get("depList");
		for(int i = 0; i< depList.size();i++){
			EgovMap map = (EgovMap)depList.get(i);
			setText(getStyleCell(sheet10, cellStyle10, 3+i, 6),(String)map.get("name"));
			setText(getStyleCell(sheet10, cellStyle10, 3+i, 7),(String)map.get("code"));
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
