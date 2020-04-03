package com.uwo.isms.excelNew;

import com.uwo.isms.service.ExcelNewService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.AbstractView;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class excelReportInfraMP extends AbstractView {
    private static final String CONTENT_TYPE = "application/vnd.ms-excel";

    @Autowired
    private ServletContext servletContext;

    @Resource(name = "excelNewService")
    ExcelNewService excelNewService;

    public excelReportInfraMP() {
        setContentType(CONTENT_TYPE);
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String formFile = servletContext.getRealPath("/")+"excelForm" + File.separator + "form_infra_mp.xlsx";
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(formFile));

        List<?> listReport = (List<?>) model.get("listComps");
        XSSFWorkbook wb = new XSSFWorkbook(bis);

        Map<String, Object> mapCellStyleBold = new HashMap<String, Object>();
        mapCellStyleBold.put("bold", true);
        Map<String, Object> mapCellStyleLeft = new HashMap<String, Object>();
        mapCellStyleLeft.put("align", "left");
        Map<String, Object> mapCellStyleBG_g = new HashMap<String, Object>();
        mapCellStyleBG_g.put("bg_color", "217,217,217");
        Map<String, Object> mapCellStyleBG_b = new HashMap<String, Object>();
        mapCellStyleBG_b.put("bg_color", "220,230,241");
        Map<String, Object> mapCellStyleBG_r = new HashMap<String, Object>();
        mapCellStyleBG_r.put("bg_color", "242,220,219");

        XSSFSheet sheet1 = wb.getSheetAt(0), sheetX = null;
        XSSFRow row1 = null, rowX = null;
        XSSFCell cellX = null;

        int intRow1 = 20, intRowX = 3;
        int intLv2Row = intRowX;
        int cols = 16;
        int intPoint = 0, intPointT = 0, intPointT2 = 0;
        float fltPoint = 0, fltPointT = 0, fltPointT2 = 0;

        String lv1Cod = null, lv2Cod = null;
        String lv2Nam = null;
        String strLv2Cod=null;
        List<String> listCellInfo = new ArrayList<String>();

        for(int i = 0; i< listReport.size();i++) {
            EgovMap mapRows = (EgovMap) listReport.get(i);
            listCellInfo.clear();
            if(lv1Cod==null||!lv1Cod.equals(mapRows.get("ucm1lvCod").toString())){

                if(intLv2Row != intRowX){
                    sheetX.addMergedRegion(new CellRangeAddress(intLv2Row, intRowX-1, 0, 0));
                    sheetX.addMergedRegion(new CellRangeAddress(intLv2Row, intRowX-1, 1, 1));
                    row1 = sheet1.getRow(intRow1++);
                    row1.getCell(2).setCellValue(strLv2Cod+". "+lv2Nam);
                    row1.getCell(12).setCellValue(intPoint==0?0:(intPoint-fltPoint)/intPoint*100);
                    row1 = sheet1.getRow(intRow1++);
                    row1.getCell(12).setCellValue(intPointT==0?0:(intPointT-fltPointT)/intPointT*100);
                    intPoint = 0;
                    fltPoint = 0;
                    intPointT2 += intPointT;
                    fltPointT2 += fltPointT;
                    intPointT = 0;
                    fltPointT = 0;
                }
                lv1Cod = String.valueOf(mapRows.get("ucm1lvCod"));
                sheetX = wb.getSheetAt(Integer.valueOf(lv1Cod)+1);
                intLv2Row=intRowX = 3;
            }
            if(!String.valueOf(mapRows.get("ucm2lvCod")).trim().equals(lv2Cod)){
                if(intLv2Row != intRowX){
                    row1 = sheet1.getRow(intRow1++);
                    row1.getCell(2).setCellValue(strLv2Cod+". "+lv2Nam);
                    row1.getCell(12).setCellValue(intPoint==0?0:(intPoint-fltPoint)/intPoint*100);
                    intPoint = 0;
                    fltPoint = 0;
                    sheetX.addMergedRegion(new CellRangeAddress(intLv2Row, intRowX-1, 0, 0));
                    sheetX.addMergedRegion(new CellRangeAddress(intLv2Row, intRowX-1, 1, 1));
                }
                lv2Cod = String.valueOf(mapRows.get("ucm2lvCod")).trim();
                lv2Nam = String.valueOf(mapRows.get("ucm2lvNam")).trim();
                strLv2Cod = lv2Cod.replaceFirst("^"+lv1Cod+"\\.", "");
                listCellInfo.add(strLv2Cod);
                listCellInfo.add(String.valueOf(mapRows.get("ucm2lvNam")).trim());
                intLv2Row = intRowX;
            }else{
                listCellInfo.add("");
                listCellInfo.add("");
            }

            listCellInfo.add(mapRows.get("ucm3lvCod").toString().replaceFirst("^"+lv1Cod+"\\.", ""));
            listCellInfo.add(mapRows.get("refNo").toString());
            listCellInfo.add(mapRows.get("ucm3lvNam").toString());
            listCellInfo.add(mapRows.get("grade").toString());
            listCellInfo.add(mapRows.get("point").toString());

            if(mapRows.get("aswMark")!=null){
                String aswMark = mapRows.get("aswMark").toString();
                String aswVal = mapRows.get("aswVal").toString();
                listCellInfo.add(aswMark);
                if(!aswMark.equals("N/A")){
                    intPoint += Integer.parseInt(mapRows.get("point").toString());
                    intPointT += Integer.parseInt(mapRows.get("point").toString());

                    fltPoint += Float.valueOf(aswVal)*Integer.parseInt(mapRows.get("point").toString());
                    fltPointT += Float.valueOf(aswVal)*Integer.parseInt(mapRows.get("point").toString());
                }
            }else{
                listCellInfo.add("");
            }

            listCellInfo.add("IF(H"+(intRowX+1)+"=\"N/A\",\"N/A\",(IF(H"+(intRowX+1)+"=\"O\",1,IF(H"+(intRowX+1)+"=\"P\",0.5,0))*$G"+(intRowX+1)+"))");
            rowX = createRowCell(wb, sheetX, intRowX++, cols);
            for(int c=0; c<listCellInfo.size(); c++){
                cellX = rowX.getCell(c);
                cellX.setCellValue(listCellInfo.get(c));
                if(c>=0&&c<=1){
                    cellX.setCellStyle(cellStyle(wb, mapCellStyleBold));
                }else if(c==4){
                    cellX.setCellStyle(cellStyle(wb, mapCellStyleLeft));
                }else if(c==7){
                    switch (listCellInfo.get(c)){
                        case "" :
                            cellX.setCellStyle(cellStyle(wb, mapCellStyleBG_b));
                            break;
                        case "N/A" :
                            cellX.setCellStyle(cellStyle(wb, mapCellStyleBG_g));
                            break;
                        case "O" : case "P":
                            cellX.setCellStyle(cellStyle(wb, mapCellStyleBG_r));
                            break;
                    }
                }else if(c==8){
                    cellX.setCellFormula(listCellInfo.get(c));
                }
            }
        }
        if(intLv2Row != intRowX){
            sheetX.addMergedRegion(new CellRangeAddress(intLv2Row, intRowX-1, 0, 0));
            sheetX.addMergedRegion(new CellRangeAddress(intLv2Row, intRowX-1, 1, 1));
            row1 = sheet1.getRow(intRow1++);
            row1.getCell(2).setCellValue(strLv2Cod+". "+lv2Nam);
            row1.getCell(12).setCellValue(intPoint==0?0:(intPoint-fltPoint)/intPoint*100);
            row1 = sheet1.getRow(intRow1++);
            row1.getCell(12).setCellValue(intPointT==0?0:(intPointT-fltPointT)/intPointT*100);
            intPointT2 += intPointT;
            fltPointT2 += fltPointT;
            row1 = sheet1.getRow(intRow1++);
            row1.getCell(12).setCellValue(intPointT2==0?0:(intPointT2-fltPointT2)/intPointT2*100);
            intRow1=5;
            sheet1.getRow(intRow1++).getCell(2).setCellFormula("M33");
            sheet1.getRow(intRow1++).getCell(2).setCellFormula("M38");
            sheet1.getRow(intRow1++).getCell(2).setCellFormula("M39");
        }

        ServletOutputStream out = response.getOutputStream();
        wb.write(out);
        if (out != null) out.close();
    }

    private XSSFRow createRowCell(XSSFWorkbook wb, XSSFSheet sheet, int intRow, int intCols){
        XSSFRow row = sheet.createRow(intRow);
        XSSFCell cell = null;
        Map<String, Object> mapCellStyle = new HashMap<String, Object>();
        for(int i=0; i<intCols; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(cellStyle(wb, mapCellStyle));
        }
        return row;
    }

    private XSSFCellStyle cellStyle(XSSFWorkbook wb, Map<String, Object> mapCellStyle){
        XSSFCellStyle cs = wb.createCellStyle();
        cs.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cs.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cs.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cs.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cs.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cs.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cs.setWrapText(true);

        Font font = wb.createFont();
        font.setFontName("맑은 고딕");
        font.setFontHeight((short)(20*8));
        cs.setFont(font);

        if(mapCellStyle.size()>0) {
            Map<String, Object> mapCellStyles = new HashMap<String, Object>();
            mapCellStyles.put("_cs", cs);
            mapCellStyles.put("_font", font);
            mapCellStyles.put("_custom", mapCellStyle);
            cs = excelNewService.cellStyle(mapCellStyles);
        }
        return cs;
    }
}
