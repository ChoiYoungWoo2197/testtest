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

public class COMPS003_msit extends AbstractExcelView {
    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      HSSFWorkbook wb, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        List<?> listComps = (List<?>) model.get("listComps");
        HSSFSheet sheet = null;
        HSSFCell cell = null;
        String strCtrCod = null, lv2Nam = null, lv3Nam = null, lv3Dtl = null;
        int intRow = 4, intLv3Seq = 1;
        for(int i = 0; i< listComps.size();i++) {
            EgovMap mapRows = (EgovMap) listComps.get(i);
            if( !String.valueOf(mapRows.get("ucmCtrCod")).trim().equals(strCtrCod) ){
                strCtrCod = String.valueOf(mapRows.get("ucmCtrCod")).trim();
                sheet = wb.createSheet((i+1)+". "+mapRows.get("ucm1lvNam"));
                cell = getCell(sheet, 0, 0);
                cell.setCellValue(String.valueOf(mapRows.get("ucm1lvNam")));
                intRow = 4;
                String strTitles[] = {"점검분야", "점검항목", "점검항목", "선택(해당문항에 V표시)", "문항번호", "배점", "점수", "문항 및 배점", "점검기준", "code"};
                for(int c=0; c< strTitles.length; c++){
                    if(c==0 || (c>0 && strTitles[c]!=strTitles[c-1])){
                        cell = getCell(sheet, intRow, c);
                        cell.setCellValue(strTitles[c]);
                    }
                }
                intRow+=2;
            }

            String strLv2Nam = null, strLv3Dtl = null;

            if(!String.valueOf(mapRows.get("ucm2lvNam")).trim().equals(lv2Nam)) {
                lv2Nam = String.valueOf(mapRows.get("ucm2lvNam")).trim();
                strLv2Nam = lv2Nam;
            }

            if(!String.valueOf(mapRows.get("ucm3lvNam")).trim().equals(lv3Nam)) {
                lv3Nam = String.valueOf(mapRows.get("ucm3lvNam")).trim();
                List<String> listCellInfo = new ArrayList<String>();
                listCellInfo.add(strLv2Nam);
                listCellInfo.add(String.valueOf(intLv3Seq++));
                listCellInfo.add(lv3Nam);
                listCellInfo.add("");
                listCellInfo.add(String.valueOf(mapRows.get("qstCnt")));
                listCellInfo.add(String.valueOf(mapRows.get("maxPoint")));
                listCellInfo.add(String.valueOf(mapRows.get("maxPoint")));
                System.out.println(listCellInfo);
                for(int c=0; c<listCellInfo.size(); c++){
                    cell = getCell(sheet, intRow, c);
                    cell.setCellValue(listCellInfo.get(c));
                }
                cell = getCell(sheet, intRow, 9);
                cell.setCellValue(String.valueOf(mapRows.get("ucmGolNo")));
                intRow++;
            }

            if(!String.valueOf(mapRows.get("ucm3lvDtl")).trim().equals(lv3Dtl)) {
                lv3Dtl = String.valueOf(mapRows.get("ucm3lvDtl")).trim();
                strLv3Dtl = lv3Dtl;
            }

            List<String> listCellInfo = new ArrayList<String>();
            listCellInfo.add(String.valueOf(mapRows.get("seq")));
            listCellInfo.add(String.valueOf(mapRows.get("point")));
            listCellInfo.add(String.valueOf(mapRows.get("point")));
            listCellInfo.add(String.valueOf(mapRows.get("title")));
            listCellInfo.add(strLv3Dtl);

            for(int c=0; c<listCellInfo.size(); c++){
                cell = getCell(sheet, intRow, c+4);
                cell.setCellValue(listCellInfo.get(c));
            }
            intRow++;
        }
    }


}
