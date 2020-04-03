package com.uwo.isms.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.uwo.isms.domain.RiskVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public class ASSET001_EXCEL extends AbstractExcelView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook wb, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		List<?> infoList = (List<?>)model.get("infoList");
		List<?> assList = (List<?>)model.get("assList");

		for (int cnt = 0; cnt < infoList.size(); cnt++) {

			EgovMap info = (EgovMap)infoList.get(cnt);
			List<?> list = null;
			if (assList.size() > 0) {
				list = (List<?>)assList.get(cnt);
			}

			String sheetName = info.get("uacCatNam").toString();

			HSSFCell cell = null;
    		HSSFSheet sheet = wb.createSheet(sheetName);

    		int j = 0;

    		setText(getCell(sheet, 0, 0), sheetName);
    		setText(getCell(sheet, 2, j++), "자산관리키");
    		setText(getCell(sheet, 2, j++), "그룹코드");
    		setText(getCell(sheet, 2, j++), "그룹명");
    		setText(getCell(sheet, 2, j++), "분류코드");
    		setText(getCell(sheet, 2, j++), "서비스코드");
    		setText(getCell(sheet, 2, j++), "서비스명");
    		setText(getCell(sheet, 2, j++), "서비스상세");
    		setText(getCell(sheet, 2, j++), "관리부서코드");
    		setText(getCell(sheet, 2, j++), "관리부서명");
    		setText(getCell(sheet, 2, j++), "관리담당자ID");
    		setText(getCell(sheet, 2, j++), "관리담당자명");
    		setText(getCell(sheet, 2, j++), "관리책임자ID");
    		setText(getCell(sheet, 2, j++), "관리책임자명");
    		setText(getCell(sheet, 2, j++), "운영부서코드");
    		setText(getCell(sheet, 2, j++), "운영부서명");
    		setText(getCell(sheet, 2, j++), "운영담당자ID");
    		setText(getCell(sheet, 2, j++), "운영담당자명");
    		setText(getCell(sheet, 2, j++), "운영책임자ID");
    		setText(getCell(sheet, 2, j++), "운영책임자명");
    		setText(getCell(sheet, 2, j++), "자산유형코드");
    		setText(getCell(sheet, 2, j++), "자산유형명");
    		setText(getCell(sheet, 2, j++), "자산명");
    		setText(getCell(sheet, 2, j++), "용도");
    		setText(getCell(sheet, 2, j++), "위치");
    		setText(getCell(sheet, 2, j++), "취약점그룹코드");
    		setText(getCell(sheet, 2, j++), "취약점그룹명");
    		setText(getCell(sheet, 2, j++), "기밀성(1/2/3)");
    		setText(getCell(sheet, 2, j++), "무결성(1/2/3)");
    		setText(getCell(sheet, 2, j++), "가용성(1/2/3)");
    		setText(getCell(sheet, 2, j++), "점수");
    		setText(getCell(sheet, 2, j++), "등급");
    		setText(getCell(sheet, 2, j++), "인증대상(Y/N)");
    		setText(getCell(sheet, 2, j++), "샘플링대상(Y/N)");
    		setText(getCell(sheet, 2, j++), "기반시설대상(Y/N)");
    		setText(getCell(sheet, 2, j++), "개인정보보유(Y/N)");
    		setText(getCell(sheet, 2, j++), "사용유무(Y/N)");

    		setText(getCell(sheet, 2, j++), "IP");
    		setText(getCell(sheet, 2, j++), "호스트");
    		setText(getCell(sheet, 2, j++), "OS");

    		// 가변 필드
    		if (info.get("uacValCl0") != null) {
    			setText(getCell(sheet, 2, j++), info.get("uacValCl0").toString());
    		}
    		if (info.get("uacValCl1") != null) {
    			setText(getCell(sheet, 2, j++), info.get("uacValCl1").toString());
    		}
    		if (info.get("uacValCl2") != null) {
    			setText(getCell(sheet, 2, j++), info.get("uacValCl2").toString());
    		}
    		if (info.get("uacValCl3") != null) {
    			setText(getCell(sheet, 2, j++), info.get("uacValCl3").toString());
    		}
    		if (info.get("uacValCl4") != null) {
    			setText(getCell(sheet, 2, j++), info.get("uacValCl4").toString());
    		}
    		if (info.get("uacValCl5") != null) {
    			setText(getCell(sheet, 2, j++), info.get("uacValCl5").toString());
    		}
    		if (info.get("uacValCl6") != null) {
    			setText(getCell(sheet, 2, j++), info.get("uacValCl6").toString());
    		}
    		if (info.get("uacValCl7") != null) {
    			setText(getCell(sheet, 2, j++), info.get("uacValCl7").toString());
    		}
    		if (info.get("uacValCl8") != null) {
    			setText(getCell(sheet, 2, j++), info.get("uacValCl8").toString());
    		}
    		if (info.get("uacValCl9") != null) {
    			setText(getCell(sheet, 2, j++), info.get("uacValCl9").toString());
    		}

    		if (list != null) {
    			for(int i = 0; i< list.size();i++){
	    			RiskVO vo = (RiskVO)list.get(i);

	    			j = 0;
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_ass_key());
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_grp_cod());
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_grp_nam());
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_cat_cod());
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_sub_cod());
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_sub_nam());
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_svr_etc());
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_dep_cod());
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_dep_nam());
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_own_id());
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_own_nam());
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_adm_id());
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_adm_nam());
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_opr_cod());
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_opr_nam());
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_use_id());
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_use_nam());
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_pic_id());
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_pic_nam());
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_ass_gbn());
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_ass_nam());
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_eqp_nam());
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_dtl_exp());
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_adm_pos());
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_rsk_grp());
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_rsk_nam());
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_app_con());
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_app_itg());
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_app_avt());
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_app_tot());
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_ass_lvl());
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_aud_yn());
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_smp_yn());
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_inf_yn());
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_prv_yn());
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_use_yn());
	    			cell = getCell(sheet, 3+i, j++);

	    			setText(cell, vo.getUar_ip());
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_host());
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_os());
	    			cell = getCell(sheet, 3+i, j++);

	    			setText(cell, vo.getUar_val_cl0());
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_val_cl1());
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_val_cl2());
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_val_cl3());
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_val_cl4());
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_val_cl5());
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_val_cl6());
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_val_cl7());
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_val_cl8());
	    			cell = getCell(sheet, 3+i, j++);
	    			setText(cell, vo.getUar_val_cl9());
	    		}
			}
		}

		HSSFSheet sheet9 = wb.createSheet("참고하세요");

		HSSFCellStyle impactStyle = wb.createCellStyle();
		HSSFFont font = wb.createFont();
	    font.setColor(HSSFColor.RED.index);
	    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	    impactStyle.setFont(font);

		int i = 0;
		setText(getCell(sheet9, i++, 0), "엑셀 업로드시 참고하세요");
		i++;
		setText(getCell(sheet9, i++, 0), "아래 * 표시는 필수항목 입니다.");
		setText(getCell(sheet9, i++, 0), "관련 코드는 코드표 시트를 참조하여 주시기 바랍니다.");
		setText(getCell(sheet9, i++, 0), "아래 컬럼을 제외한 컬럼은 필수입력사항은 아니나 서버/단말기 자산은 필수로 OS를 입력하여 주시기 바랍니다. (코드표 참조)");
		i++;
		setText(getCell(sheet9, i, 0), "자산관리키");
		setText(getCell(sheet9, i++, 1), "기본적으로 시스템에서 부여하는 코드, 기입하거나 변경금지");
		setText(getCell(sheet9, i, 0), "그룹코드");
		setText(getCell(sheet9, i++, 1), "기본적으로 시스템에서 부여하는 코드, 기입하거나 변경금지");
		setText(getCell(sheet9, i, 0), "그룹명");
		setText(getCell(sheet9, i++, 1), "기본적으로 시스템에서 부여하는 코드, 기입하거나 변경금지");
		getCell(sheet9, i, 0).setCellStyle(impactStyle);
		setText(getCell(sheet9, i, 0), "* 분류코드");
		setText(getCell(sheet9, i++, 1), "해당 자산의 분류코드를 입력합니다. (코드표 참조)");
		setText(getCell(sheet9, i, 0), "* 서비스코드");
		setText(getCell(sheet9, i++, 1), "해당 자산의 서비스코드를 입력합니다. (코드표 참조)");
		setText(getCell(sheet9, i, 0), "서비스명");
		setText(getCell(sheet9, i++, 1), "해당 자산의 서비스명을 입력합니다.");
		setText(getCell(sheet9, i, 0), "서비스상세");
		setText(getCell(sheet9, i++, 1), "해당 자산의 서비스상세 내용을 기입합니다.");
		getCell(sheet9, i, 0).setCellStyle(impactStyle);
		setText(getCell(sheet9, i, 0), "*관리부서코드");
		setText(getCell(sheet9, i++, 1), "관리부서 코드를 기입합니다. (코드표 참조)");
		setText(getCell(sheet9, i, 0), "관리부서명");
		setText(getCell(sheet9, i++, 1), "관리부서명을 기입합니다.");
		getCell(sheet9, i, 0).setCellStyle(impactStyle);
		setText(getCell(sheet9, i, 0), "*관리담당자ID");
		setText(getCell(sheet9, i++, 1), "관리담당자 사번을 기입합니다.");
		setText(getCell(sheet9, i, 0), "관리담당자명");
		setText(getCell(sheet9, i++, 1), "관리담당자명을 기입합니다.");
		getCell(sheet9, i, 0).setCellStyle(impactStyle);
		setText(getCell(sheet9, i, 0), "*관리책임자ID");
		setText(getCell(sheet9, i++, 1), "관리책임자 사번을 기입합니다.");
		setText(getCell(sheet9, i, 0), "관리책임자명");
		setText(getCell(sheet9, i++, 1), "관리책임자명을 기입합니다.");
		getCell(sheet9, i, 0).setCellStyle(impactStyle);
		setText(getCell(sheet9, i, 0), "*운영부서코드");
		setText(getCell(sheet9, i++, 1), "운영부서 코드를 기입합니다. (코드표 참조)");
		setText(getCell(sheet9, i, 0), "운영부서명");
		setText(getCell(sheet9, i++, 1), "운영부서명을 기입합니다.");
		getCell(sheet9, i, 0).setCellStyle(impactStyle);
		setText(getCell(sheet9, i, 0), "*운영담당자ID");
		setText(getCell(sheet9, i++, 1), "운영담당자 사번을 기입합니다.");
		setText(getCell(sheet9, i, 0), "운영담당자명");
		setText(getCell(sheet9, i++, 1), "운영담당자명을 기입합니다.");
		getCell(sheet9, i, 0).setCellStyle(impactStyle);
		setText(getCell(sheet9, i, 0), "*운영책임자ID");
		setText(getCell(sheet9, i++, 1), "운영책임자 사번을 기입합니다.");
		setText(getCell(sheet9, i, 0), "운영책임자명");
		setText(getCell(sheet9, i++, 1), "운영책임자명을 기입합니다.");
		getCell(sheet9, i, 0).setCellStyle(impactStyle);
		setText(getCell(sheet9, i, 0), "*자산유형코드");
		setText(getCell(sheet9, i++, 1), "자산유형코드를 기입합니다. (코드표 참조)");
		setText(getCell(sheet9, i, 0), "자산유형명");
		setText(getCell(sheet9, i++, 1), "자산유형명을 기입합니다.");
		setText(getCell(sheet9, i, 0), "자산명");
		setText(getCell(sheet9, i++, 1), "자산명을 기입합니다.");
		setText(getCell(sheet9, i, 0), "용도");
		setText(getCell(sheet9, i++, 1), "자산의 용도를 기입합니다.");
		setText(getCell(sheet9, i, 0), "위치");
		setText(getCell(sheet9, i++, 1), "자산의 위치를 기입합니다.");
		setText(getCell(sheet9, i, 0), "취약점그룹코드");
		setText(getCell(sheet9, i++, 1), "시스템에서 부여하므로 기입하거나 변경금지");
		setText(getCell(sheet9, i, 0), "취약점그룹명");
		setText(getCell(sheet9, i++, 1), "시스템에서 부여하므로 기입하거나 변경금지");
		getCell(sheet9, i, 0).setCellStyle(impactStyle);
		setText(getCell(sheet9, i, 0), "*기밀성(1/2/3)");
		setText(getCell(sheet9, i++, 1), "기밀성 점수를 부여합니다. 1/2/3중 하나를 입력합니다.");
		getCell(sheet9, i, 0).setCellStyle(impactStyle);
		setText(getCell(sheet9, i, 0), "*무결성(1/2/3)");
		setText(getCell(sheet9, i++, 1), "무결성 점수를 부여합니다. 1/2/3중 하나를 입력합니다.");
		getCell(sheet9, i, 0).setCellStyle(impactStyle);
		setText(getCell(sheet9, i, 0), "*가용성(1/2/3)");
		setText(getCell(sheet9, i++, 1), "가용성 점수를 부여합니다. 1/2/3중 하나를 입력합니다.");
		setText(getCell(sheet9, i, 0), "점수");
		setText(getCell(sheet9, i++, 1), "시스템에서 부여하므로 기입하거나 변경금지");
		setText(getCell(sheet9, i, 0), "등급");
		setText(getCell(sheet9, i++, 1), "시스템에서 부여하므로 기입하거나 변경금지");
		getCell(sheet9, i, 0).setCellStyle(impactStyle);
		setText(getCell(sheet9, i, 0), "*인증대상(Y/N)");
		setText(getCell(sheet9, i++, 1), "인증대상여부 기입");
		getCell(sheet9, i, 0).setCellStyle(impactStyle);
		setText(getCell(sheet9, i, 0), "*샘플링대상(Y/N)");
		setText(getCell(sheet9, i++, 1), "샘플링대상여부 기입");
		getCell(sheet9, i, 0).setCellStyle(impactStyle);
		setText(getCell(sheet9, i, 0), "*기반시설대상(Y/N)");
		setText(getCell(sheet9, i++, 1), "기반시설대상여부 기입");
		getCell(sheet9, i, 0).setCellStyle(impactStyle);
		setText(getCell(sheet9, i, 0), "*개인정보보유(Y/N)");
		setText(getCell(sheet9, i++, 1), "개인정보보유여부 기입");
		getCell(sheet9, i, 0).setCellStyle(impactStyle);
		setText(getCell(sheet9, i, 0), "*사용유무(Y/N)");
		setText(getCell(sheet9, i++, 1), "사용유무 기입 ");


		HSSFSheet sheet10 = wb.createSheet("코드표");

		i = 0;
		setText(getCell(sheet10, i++, 0), "코드표");
		i++;
		setText(getStyleCell(sheet10, impactStyle, i++, 0), "분류코드");
		setText(getCell(sheet10, i, 0), "코드명");
		setText(getCell(sheet10, i++, 1), "코드");

		List<?> catList = (List<?>)model.get("catList");
		for (int j = 0; j < catList.size(); j++) {
			EgovMap map = (EgovMap)catList.get(j);
			setText(getCell(sheet10, i, 0),(String)map.get("name"));
			setText(getCell(sheet10, i++, 1),(String)map.get("code"));
		}

		i++;
		setText(getStyleCell(sheet10, impactStyle, i++, 0), "서비스코드");
		setText(getCell(sheet10, i, 0), "코드명");
		setText(getCell(sheet10, i++, 1), "코드");

		List<?> svcList = (List<?>)model.get("svcList");
		for (int j = 0; j < svcList.size(); j++) {
			EgovMap map = (EgovMap)svcList.get(j);
			setText(getCell(sheet10, i, 0),(String)map.get("name"));
			setText(getCell(sheet10, i++, 1),(String)map.get("code"));
		}

		i++;
		setText(getStyleCell(sheet10, impactStyle, i++, 0), "자산유형코드");
		setText(getCell(sheet10, i, 0), "코드명");
		setText(getCell(sheet10, i++, 1), "코드");

		List<?> dbList = (List<?>)model.get("dbList");
		for (int j = 0; j <dbList.size(); j++) {
			EgovMap map = (EgovMap)dbList.get(j);
			setText(getCell(sheet10, i, 0),(String)map.get("name"));
			setText(getCell(sheet10, i++, 1),(String)map.get("code"));
		}

		i++;
		setText(getStyleCell(sheet10, impactStyle, i++, 0), "OS코드");
		setText(getCell(sheet10, i, 0), "코드명");
		setText(getCell(sheet10, i++, 1), "코드");

		List<?> osList = (List<?>)model.get("osList");
		for (int j = 0; j < osList.size(); j++) {
			EgovMap map = (EgovMap)osList.get(j);
			setText(getCell(sheet10, i, 0),(String)map.get("name"));
			setText(getCell(sheet10, i++, 1),(String)map.get("code"));
		}

		i++;
		setText(getStyleCell(sheet10, impactStyle, i++, 0), "부서코드");
		setText(getCell(sheet10, i, 0), "코드명");
		setText(getCell(sheet10, i++, 1), "코드");

		List<?> depList = (List<?>)model.get("depList");
		for (int j = 0; j < depList.size(); j++) {
			EgovMap map = (EgovMap)depList.get(j);
			setText(getCell(sheet10, i, 0),(String)map.get("name"));
			setText(getCell(sheet10, i++, 1),(String)map.get("code"));
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
