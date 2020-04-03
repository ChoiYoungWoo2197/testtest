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

public class COMPS003_isms_p extends AbstractExcelView {
    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      HSSFWorkbook wb, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        List<?> listComps = (List<?>) model.get("listComps");
        HSSFSheet sheet = null;
        HSSFCell cell = null;
        String strCtrCod = null, lv1Cod = null, lv1Nam = null, lv2Cod = null, lv2Nam = null, lv2Dtl = null, strCtrCod2;
        int intRow = 4;

        for(int i = 0; i< listComps.size();i++) {
            EgovMap mapRows = (EgovMap) listComps.get(i);
            if( !String.valueOf(mapRows.get("ucmCtrCod")).trim().equals(strCtrCod) ){
                strCtrCod = String.valueOf(mapRows.get("ucmCtrCod")).trim();
                strCtrCod2 = strCtrCod;
                if (checkInteger(strCtrCod)) strCtrCod2 = String.valueOf(Integer.parseInt(strCtrCod));

                sheet = wb.createSheet(strCtrCod2);
                cell = getCell(sheet, 1, 1);
                cell.setCellValue(strCtrCod2);

                intRow = 4;
                String strTitles[] = {"분야", "분야", "항목", "항목", "상세내용", "주요 확인사항", "설명"};
                for(int c=0; c< strTitles.length; c++){
                    if(c==0 || (c>0 && strTitles[c]!=strTitles[c-1])){
                        cell = getCell(sheet, intRow, c + 1);
                        cell.setCellValue(strTitles[c]);
                    }
                }
                intRow++;
            }

            String strLv1Cod = null, strLv1Nam = null, strLv2Cod = null, strLv2Nam = null, strLv2Dtl = null;
            if(!String.valueOf(mapRows.get("ucm1lvCod")).trim().equals(lv1Cod)) {
                lv1Cod = String.valueOf(mapRows.get("ucm1lvCod")).trim();
                strLv1Cod = lv1Cod;
            }
            if(!String.valueOf(mapRows.get("ucm1lvNam")).trim().equals(lv1Nam)) {
                lv1Nam = String.valueOf(mapRows.get("ucm1lvNam")).trim();
                strLv1Nam = lv1Nam;
            }
            if(!String.valueOf(mapRows.get("ucm2lvCod")).trim().equals(lv2Cod)) {
                lv2Cod = String.valueOf(mapRows.get("ucm2lvCod")).trim();
                strLv2Cod = lv2Cod;
            }
            if(!String.valueOf(mapRows.get("ucm2lvNam")).trim().equals(lv2Nam)) {
                lv2Nam = String.valueOf(mapRows.get("ucm2lvNam")).trim();
                strLv2Nam = lv2Nam;
            }
            if(!String.valueOf(mapRows.get("ucm2lvDtl")).trim().equals(lv2Dtl)) {
                lv2Dtl = String.valueOf(mapRows.get("ucm2lvDtl")).trim();
                strLv2Dtl = lv2Dtl;
            }

            List<String> listCellInfo = new ArrayList<String>();
            listCellInfo.add(strLv1Cod);
            listCellInfo.add(strLv1Nam);
            listCellInfo.add(strLv2Cod);
            listCellInfo.add(strLv2Nam);
            listCellInfo.add(strLv2Dtl);
            listCellInfo.add(String.valueOf(mapRows.get("ucm3lvNam")).trim());
            listCellInfo.add(String.valueOf(mapRows.get("ucm3lvDtl")).trim());
            for(int c=0; c<listCellInfo.size(); c++){
                cell = getCell(sheet, intRow, c+1);
                cell.setCellValue(listCellInfo.get(c));
            }
            intRow++;
        }

    }
    private static boolean checkInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
