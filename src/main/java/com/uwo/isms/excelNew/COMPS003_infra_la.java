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

public class COMPS003_infra_la extends AbstractExcelView {
    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      HSSFWorkbook wb, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        List<?> listComps = (List<?>) model.get("listComps");
        HSSFSheet sheet = null;
        HSSFCell cell = null;
        String strCtrCod = null, lv1Cod = null, lv1Nam=null, lv2Cod = null, lv2Nam=null, lv2Dtl=null, lv3Cod=null, lv3Nam=null;

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
                sheet = wb.createSheet(lv1Cod+". "+mapRows.get("ucm1lvNam").toString());

                cell = getCell(sheet, 0, 0);
                cell.setCellValue(lv1Cod+". "+mapRows.get("ucm1lvNam").toString());

                intRow = 2;
                String strTitles[] = {"통제분야", "통제항목", "평가내용", "적용여부", "세부평가항목", "적용여부", "착안사항 설명", "", "증빙자료예시", "code"};
                for(int c=0; c< strTitles.length; c++){
                    cell = getCell(sheet, intRow, c);
                    cell.setCellValue(strTitles[c]);
                }
                intRow++;
            }

            String strLv1Nam=null, strLv2Nam=null, strLv2Dtl=null, strLv3Nam=null;
            if(!String.valueOf(mapRows.get("ucm1lvCod")).trim().equals(lv1Cod) || !String.valueOf(mapRows.get("ucm1lvNam")).trim().equals(lv1Nam)) {
                lv1Cod = String.valueOf(mapRows.get("ucm1lvCod")).trim();
                lv1Nam = String.valueOf(mapRows.get("ucm1lvNam")).trim();
                strLv1Nam = lv1Cod+". "+lv1Nam;
            }
            if(!String.valueOf(mapRows.get("ucm2lvCod")).trim().equals(lv2Cod) || !String.valueOf(mapRows.get("ucm2lvNam")).trim().equals(lv2Nam)) {
                lv2Cod = String.valueOf(mapRows.get("ucm2lvCod")).trim();
                lv2Nam = String.valueOf(mapRows.get("ucm2lvNam")).trim();
                strLv2Nam = lv2Cod+" "+lv2Nam;
            }
            if(!String.valueOf(mapRows.get("ucm2lvDtl")).trim().equals(lv2Dtl)) {
                lv2Dtl = String.valueOf(mapRows.get("ucm2lvDtl")).trim();
                strLv2Dtl = lv2Dtl;
            }
            if(!String.valueOf(mapRows.get("ucm3lvCod")).trim().equals(lv3Cod) || !String.valueOf(mapRows.get("ucm3lvNam")).trim().equals(lv3Nam)) {
                lv3Cod = String.valueOf(mapRows.get("ucm3lvCod")).trim();
                lv3Nam = String.valueOf(mapRows.get("ucm3lvNam")).trim();
                strLv3Nam = lv3Cod.replaceFirst("^"+lv2Cod+"\\.", "")+". "+lv3Nam;
            }

            List<String> listCellInfo = new ArrayList<String>();
            listCellInfo.add(strLv1Nam);
            listCellInfo.add(strLv2Nam);
            listCellInfo.add(strLv2Dtl);
            if(strLv2Dtl==null){
                listCellInfo.add(null);
            }else{
                listCellInfo.add("Y");
            }
            listCellInfo.add(strLv3Nam);
            if(strLv3Nam==null){
                listCellInfo.add(null);
            }else{
                listCellInfo.add("Y");
            }

            listCellInfo.add(mapRows.get("seq").toString()+". "+mapRows.get("title").toString());
            listCellInfo.add(null);
            listCellInfo.add(mapRows.get("note").toString());

            if(strLv3Nam==null){
                listCellInfo.add(null);
            }else{
                listCellInfo.add(String.valueOf(mapRows.get("ucmGolNo")));
            }

            for(int c=0; c<listCellInfo.size(); c++){
                cell = getCell(sheet, intRow, c);
                cell.setCellValue(listCellInfo.get(c));
            }
            intRow++;
        }
    }


}
