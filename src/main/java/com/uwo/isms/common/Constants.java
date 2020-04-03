/**
 ***********************************
 * @source Constants.java
 * @date 2015. 7. 01.
 * @project isms3
 * @description 
 ***********************************
 */
package com.uwo.isms.common;

public class Constants {

	public final static String RETURN_TYPE = "json";
	
	/** 레벨구분 **/
	public final static String UCM_LVL_GBN = "3";
	
	/** 사용 유무 Y **/
	public final static String USE_Y = "Y";
	
	/** 사용 유무 N **/
	public final static String USE_N = "N";

	/** 성공 **/
	public static final String RETURN_SUCCESS = "S";
	
	/** 중복 **/
	public static final String RETURN_DUP = "D";
	
	/** 실패 **/
	public static final String RETURN_FAIL = "F";

	/** 통제항목표준코드 **/
	public static final String STND_COD = "STND";
	
	/** 통제항목표준명 **/
	public static final String STND_NAM = "통제항목표준명";
	
	public static final String CSR_COD = "SFC";
	
	/** 사업부코드 **/
	public static final String DIV_COD = "D001";
	
	/** 사업부코드 **/
	public static final String DIV_NAM = "사업부";
	
	/** 서비스 코드 **/
	public static final String SVC_COD = "SERVICE";
	
	/** 부서 코드 **/
	public static final String DEP_COD = "DEPT";

	/** 이력 코드 **/
	public static final String HIS_CODE = "H";
	
	/** 권한등급 코드 **/
	public static final String AUH_COD = "AUTH";
	
	/** 문서 양식 **/
	public static final String DGBN_COD = "DGBN";
	
	/** 권한등급 코드 통합관리자 **/
	public static final String AUH_A = "A";
	
	/** 권한등급 코드 부서관리자 **/
	public static final String AUH_V = "V";
	
	/** 권한등급 코드 담당자 **/
	public static final String AUH_P = "P";
	
	/** 레벨 상 **/
	public static final String LVL_H  = "H";
	
	/** 레벨 중 **/
	public static final String LVL_M  = "M";
	
	/** 레벨 하 **/
	public static final String LVL_L  = "L";
	
	
	/** 위험처리 상태 자료등록 **/
	public static final String RSK_STA_R01  = "R01";
	
	/** 위험처리 상태 조치계획 등록 **/
	public static final String RSK_STA_R10  = "R10";
	
	/** 위험처리 상태 조치계획 승인 **/
	public static final String RSK_STA_R19  = "R19";
	
	/** 위험처리 상태 처리결과 등록 **/
	public static final String RSK_STA_R20  = "R20";
	
	/** 위험처리 상태 처리결과 승인 **/
	public static final String RSK_STA_R29  = "R29";

	/** 파일 구분 보안  **/
	public static final String FILE_GBN_INS = "INS";
	
	/** 파일 구분 담당 업무  **/
	public static final String FILE_GBN_WRK = "WRK";
	
	/** 파일 구분 수행업무  **/
	public static final String FILE_GBN_DOC = "DOC";
	
	/** 파일 구분 게시판  **/
	public static final String FILE_GBN_BRD = "BRD";
	
	/** 파일 구분 서비스관리  **/
	public static final String FILE_GBN_SVC = "SVC";

	/** 파일 구분 기반시설 보호대책 지침 파일  **/
	public static final String FILE_GBN_PTM = "PTM";
	
	/** 파일 구분 보안 코드  **/
	public static final String FILE_CON_INS = "8";	
	
	/** 파일 구분 공지 코드  **/
	public static final String FILE_CON_NTC = "1";
	
	/** 파일 구분 보안정책 코드  **/
	public static final String FILE_CON_SCR = "2";

	/** 파일 구분 자료실 코드  **/
	public static final String FILE_CON_DAT = "3";
	
	/** 파일 구분 Q&A 코드  **/
	public static final String FILE_CON_QNA = "4";
	
	/** 파일 구분 수행업무 코드  **/
	public static final String FILE_CON_DOC = "5";
	
	/** 파일 구분 업무 코드  **/
	public static final String FILE_CON_WRK = "6";
	
	/** 파일 구분 FAQ 코드  **/
	public static final String FILE_CON_FAQ = "7";
	
	/** 파일 구분 서비스관리 - 전체조직도 코드  **/
	public static final String FILE_CON_ALL = "9";	

	/** 파일 구분 서비스관리 - 정보보호조직도 코드  **/
	public static final String FILE_CON_INF = "10";

	/** 파일 구분 기반시설 보호대책 지침 파일  **/
	public static final String FILE_CON_PTM_GUIDE = "11";

	/** 파일 구분 기반시설 보호대책 중점과제 파일  **/
	public static final String FILE_CON_PTM_TASK = "12";
	
	/** 자산 코드 구분  **/
	public static final String ASS_COD_GBN = "ASS_";
	
	/** 우려사항 코드 구분  **/
	public static final String COC_COD_GBN = "CV_";

	/** 자산 그룹 코드 구분  **/
	public static final String GRP_COD_GBN = "GR_";

	/** 위험시나리오 코드 구분  **/
	public static final String SRO_COD_GBN = "RS_ISMS_";

	/** 결함 코드 구분  **/
	public static final String FLT_COD_GBN = "FLT_";

	/** 관리체제 자산가치  **/
	public static final String MNG_ASS_VAL = "3";

	/** 관리체제 그룹키  **/
	public static final String MNG_CAT_KEY = "99";

	/** 결함 조치결과 미완료  **/
	public static final Object RST_STA_N = "N";

	/** 우려사항 구분 코드 **/
	public static final Object COC_GBN = "0";

	/** 삭제 코드 **/
	public static final Object RST_DEL = "D";

	/** 결함 처리 **/
	public static final String FLT_STA_SUC = "9"; 
	
}
