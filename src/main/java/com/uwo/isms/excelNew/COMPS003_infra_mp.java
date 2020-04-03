package com.uwo.isms.excelNew;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class COMPS003_infra_mp extends AbstractExcelView {
    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      HSSFWorkbook wb, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        List<?> listComps = (List<?>) model.get("listComps");
        HSSFSheet sheet = null;
        HSSFCell cell = null;
        String strCtrCod = null, lv1Cod = null, lv2Cod = null, lv2Nam=null;

        for(int i = 0; i< 2;i++) {
            sheet = wb.createSheet(String.valueOf(i+1));
            cell = getCell(sheet, 0, 0);
            cell.setCellValue("공백시트 입니다.");
        }

        int intRow = 0;
        for(int i = 0; i< listComps.size();i++) {
            EgovMap mapRows = (EgovMap) listComps.get(i);
            if( !String.valueOf(mapRows.get("ucmCtrCod")).trim().equals(strCtrCod) ){
                strCtrCod = String.valueOf(mapRows.get("ucmCtrCod")).trim();
                lv1Cod = String.valueOf(mapRows.get("ucm1lvCod"));
                sheet = wb.createSheet(String.valueOf(mapRows.get("ucm1lvNam")));

                cell = getCell(sheet, 0, 0);
                cell.setCellValue(String.valueOf(mapRows.get("ucm1lvNam")));

                intRow = 2;
                String strTitles[] = {"No", "분류", "No", "Ref No.", "점검항목", "등급", "점수", "code"};
                for(int c=0; c< strTitles.length; c++){
                    cell = getCell(sheet, intRow, c);
                    cell.setCellValue(strTitles[c]);
                }
                intRow++;
            }

            String strLv2Cod=null, strLv2Nam=null;
            if(!String.valueOf(mapRows.get("ucm2lvCod")).trim().equals(lv2Cod)){
                lv2Cod = String.valueOf(mapRows.get("ucm2lvCod")).trim();
                strLv2Cod = lv2Cod.replaceFirst("^"+lv1Cod+"\\.", "");
            }
            if(!String.valueOf(mapRows.get("ucm2lvNam")).trim().equals(lv2Nam)) {
                lv2Nam = String.valueOf(mapRows.get("ucm2lvNam")).trim();
                strLv2Nam = lv2Nam;
            }

            List<String> listCellInfo = new ArrayList<String>();
            listCellInfo.add(strLv2Cod);
            listCellInfo.add(strLv2Nam);
            listCellInfo.add(mapRows.get("ucm3lvCod").toString().replaceFirst("^"+lv1Cod+"\\.", ""));
            listCellInfo.add(mapRows.get("refNo").toString());
            listCellInfo.add(mapRows.get("ucm3lvNam").toString());
            listCellInfo.add(mapRows.get("grade").toString());
            listCellInfo.add(mapRows.get("point").toString());
            listCellInfo.add(mapRows.get("ucmGolNo").toString());

            for(int c=0; c<listCellInfo.size(); c++){
                cell = getCell(sheet, intRow, c);
                cell.setCellValue(listCellInfo.get(c));
            }
            intRow++;
        }
    }

}
