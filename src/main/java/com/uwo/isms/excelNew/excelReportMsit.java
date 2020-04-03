package com.uwo.isms.excelNew;

import com.uwo.isms.service.ExcelNewService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
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

public class excelReportMsit extends AbstractView {
    private static final String CONTENT_TYPE = "application/vnd.ms-excel";

    @Autowired
    private ServletContext servletContext;

    @Resource(name = "excelNewService")
    ExcelNewService excelNewService;

    public excelReportMsit() {
        setContentType(CONTENT_TYPE);
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String formFile = servletContext.getRealPath("/")+"excelForm" + File.separator + "form_msit.xlsx";
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(formFile));

        List<?> listReport = (List<?>) model.get("listComps");

        XSSFWorkbook wb = new XSSFWorkbook(bis);

        Map<String, Object> mapCellStyleLv2 = new HashMap<String, Object>();
        mapCellStyleLv2.put("align", "center");
        mapCellStyleLv2.put("bold", true);
        mapCellStyleLv2.put("font_size", 11);
        mapCellStyleLv2.put("bg_color", "217,217,217");

        Map<String, Object> mapCellStyleLv3 = new HashMap<String, Object>();
        mapCellStyleLv3.put("align", "center");
        mapCellStyleLv3.put("bg_color", "216,228,188");

        Map<String, Object> mapCellStyleSub = new HashMap<String, Object>();
        mapCellStyleSub.put("align", "center");

        Map<String, Object> mapCellStyleSub2 = new HashMap<String, Object>();

        XSSFSheet sheet1 = wb.getSheetAt(0);
        XSSFSheet sheet2 = wb.getSheetAt(1);
        XSSFSheet sheet3 = wb.getSheetAt(2);

        XSSFRow row1=null, row2=null, row3=null;
        XSSFCell cell1=null, cell2=null, cell3=null;

        int cols2=7, cols3 = 11;
        String lv2Cod = null, lv3Cod = null;
        int intRow1 = 25;
        int intRow2 = 3, intCell2 = 0, intLv3Seq2=1;
        int intRow3 = 6, intCell3 = 0, intLv3Seq3=1;
        int intLv2Row2 = intRow2;
        int intLv2Row3 = intRow3;
        int intLv3Row3 = intLv2Row3;
        String strSeq = "";
        List<String> listCellInfo = new ArrayList<String>();

        for(int i = 0; i< listReport.size();i++) {
            EgovMap mapRows = (EgovMap) listReport.get(i);
            if(lv2Cod==null || !lv2Cod.equals(mapRows.get("ucm2lvCod").toString())) {
                if(lv2Cod!=null) {
                    sheet2.addMergedRegion(new CellRangeAddress(intLv2Row2, intRow2-1, 0, 0));
                    sheet3.addMergedRegion(new CellRangeAddress(intLv2Row3, intRow3-1, 0, 0));
                    intLv2Row2 = intRow2;
                    intLv2Row3 = intRow3;
                }
                lv2Cod = mapRows.get("ucm2lvCod").toString();

                row1 = sheet1.getRow(intRow1++);
                row1.getCell(0).setCellValue(mapRows.get("ucm2lvNam").toString());
                row1.getCell(2).setCellFormula("B"+intRow1+"-D"+intRow1);
                row1.getCell(6).setCellFormula("F"+intRow1+"/E"+intRow1);

                row2 = createRowCell(wb, sheet2, intRow2, cols2);
                cell2 = row2.getCell(0);
                cell2.setCellValue(String.valueOf(mapRows.get("ucm2lvNam")));
                cell2.setCellStyle(cellStyle(wb, mapCellStyleLv2));

                row3 = createRowCell(wb, sheet3, intRow3, cols3);
                cell3 = row3.getCell(0);
                cell3.setCellValue(String.valueOf(mapRows.get("ucm2lvNam")));
                cell3.setCellStyle(cellStyle(wb, mapCellStyleLv2));
            }
            if(lv3Cod==null || !lv3Cod.equals(mapRows.get("ucm3lvCod").toString())) {
                if(lv3Cod!=null){
                    sheet3.getRow(intRow3-Integer.valueOf(strSeq)-1).getCell(4).setCellValue(strSeq);
                    sheet3.addMergedRegion(new CellRangeAddress(intLv3Row3+1, intRow3-1, 8, 8));
                    intLv3Row3 = intRow3;
                }
                lv3Cod = mapRows.get("ucm3lvCod").toString();
                listCellInfo.clear();
                listCellInfo.add(String.valueOf(intLv3Seq2++));
                listCellInfo.add(mapRows.get("ucm3lvNam").toString());
                listCellInfo.add("VLOOKUP(C"+(intLv3Seq2+2)+",'1. 정보시스템'!$C$7:$E$222,3,FALSE)");
                listCellInfo.add("IF(G"+(intLv3Seq2+2)+"=\"N/A\",\"N/A\",VLOOKUP(C"+(intLv3Seq2+2)+",'1. 정보시스템'!$C$7:$G$222,4,FALSE))");
                listCellInfo.add("VLOOKUP(C"+(intLv3Seq2+2)+",'1. 정보시스템'!$C$7:$D$222,2,FALSE)");
                listCellInfo.add("VLOOKUP(C"+(intLv3Seq2+2)+",'1. 정보시스템'!$C$7:$G$222,5,FALSE)");

                if(intLv2Row2 != intRow2){
                    row2 = createRowCell(wb, sheet2, intRow2, cols2);
                    cell2 = row2.getCell(0);
                    cell2.setCellStyle(cellStyle(wb, mapCellStyleSub));
                }
                intCell2 = 1;
                for(int c=0; c<listCellInfo.size(); c++){
                    cell2 = row2.getCell(c+intCell2);
                    if(c>=2&&c<=5){
                        cell2.setCellFormula(listCellInfo.get(c));
                    }else{
                        cell2.setCellValue(listCellInfo.get(c));
                    }
                    if(c==1) {
                        cell2.setCellStyle(cellStyle(wb, mapCellStyleSub2));
                    }else{
                        cell2.setCellStyle(cellStyle(wb, mapCellStyleSub));
                    }
                }

                listCellInfo.clear();
                listCellInfo.add(String.valueOf(intLv3Seq3++));
                listCellInfo.add(mapRows.get("ucm3lvNam").toString());
                listCellInfo.add("VLOOKUP(\"V\",D"+(intRow3+2)+":INDIRECT(ADDRESS(ROW()+E"+(intRow3+1)+",5,1,1,)),2,FALSE)");
                listCellInfo.add("");
                listCellInfo.add("INDIRECT(ADDRESS((ROW()+1),COLUMN()))");
                listCellInfo.add("VLOOKUP(\"V\",D"+(intRow3+2)+":INDIRECT(ADDRESS(ROW()+E"+(intRow3+1)+",7,1,1,)),4,FALSE)");
                if(intLv2Row3 != intRow3){
                    row3 = createRowCell(wb, sheet3, intRow3, cols3);
                    cell3 = row3.getCell(0);
                    cell3.setCellStyle(cellStyle(wb, mapCellStyleLv2));
                }
                intCell3 = 1;
                for(int c=0; c<listCellInfo.size(); c++){
                    cell3 = row3.getCell(c+intCell3);
                    if(c==2||c==4||c==5){
                        cell3.setCellFormula(listCellInfo.get(c));
                    }else{
                        cell3.setCellValue(listCellInfo.get(c));
                    }
                    cell3.setCellStyle(cellStyle(wb, mapCellStyleLv3));
                }
                //row3.getCell(3);
                //cell3.setCellStyle(cellStyle(wb, mapCellStyleLv2));
                for(int c=4; c<=10; c++){
                    cell3 = row3.getCell(c);
                    cell3.setCellStyle(cellStyle(wb, mapCellStyleLv3));
                }
                intRow2++;
                intRow3++;
            }

            listCellInfo.clear();
            strSeq = mapRows.get("seq").toString();

            listCellInfo.add(strSeq.toString().equals(mapRows.get("num").toString())?"v":"");
            listCellInfo.add(strSeq);
            listCellInfo.add(mapRows.get("point").toString());
            listCellInfo.add(mapRows.get("point").toString());
            listCellInfo.add(mapRows.get("title").toString());
            if(strSeq.equals("1")){
                listCellInfo.add(mapRows.get("ucm3lvDtl")==null?"":mapRows.get("ucm3lvDtl").toString());
            }
            row3 = createRowCell(wb, sheet3, intRow3, cols3);
            intCell3 = 3;
            for(int c=0; c<listCellInfo.size(); c++){
                cell3 = row3.getCell(c+intCell3);
                cell3.setCellValue(listCellInfo.get(c));
                if(c==2||c==3){
                    if(!listCellInfo.get(c).equals("N/A")){
                        double d=Double.parseDouble(listCellInfo.get(c));
                        cell3.setCellType(Cell.CELL_TYPE_NUMERIC);
                        cell3.setCellValue(d);
                    }
                }
                cell3.setCellStyle(cellStyle(wb, mapCellStyleSub));
            }
            for(int c=7; c<=8; c++){
                cell3 = row3.getCell(c);
                cell3.setCellStyle(cellStyle(wb, mapCellStyleSub2));
            }

            intRow3++;
        }
        if(intLv2Row2 != intRow2) {
            sheet2.addMergedRegion(new CellRangeAddress(intLv2Row2, intRow2-1, 0, 0));
        }
        if(intLv2Row3 != intRow3) {
            sheet3.addMergedRegion(new CellRangeAddress(intLv2Row3, intRow3 - 1, 0, 0));
        }
        if(intLv3Row3 != intRow3) {
            sheet3.getRow(intRow3-Integer.valueOf(strSeq)-1).getCell(4).setCellValue(strSeq);
            sheet3.addMergedRegion(new CellRangeAddress(intLv3Row3 + 1, intRow3 - 1, 8, 8));
        }

        ServletOutputStream out = response.getOutputStream();
        wb.write(out);

        if (out != null) out.close();
        //if (wb != null) wb.close();
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
        cs.setWrapText(true);

        Font font = wb.createFont();
        font.setFontName("굴림");
        font.setFontHeight((short)(20*10));
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
