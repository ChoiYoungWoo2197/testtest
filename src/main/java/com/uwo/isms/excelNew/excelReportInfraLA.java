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

public class excelReportInfraLA extends AbstractView {
    private static final String CONTENT_TYPE = "application/vnd.ms-excel";

    @Autowired
    private ServletContext servletContext;

    @Resource(name = "excelNewService")
    ExcelNewService excelNewService;

    private XSSFWorkbook wb = null;
    private XSSFSheet sheet1 = null, sheetX = null;

    private Map<String, Object> mapCellStyleLeft = new HashMap<String, Object>();
    private Map<String, Object> mapCellStyleBG = new HashMap<String, Object>();

    public excelReportInfraLA() {
        setContentType(CONTENT_TYPE);
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String formFile = servletContext.getRealPath("/") + "excelForm" + File.separator + "form_infra_la.xlsx";
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(formFile));

        List<?> listReport = (List<?>) model.get("listComps");
        wb = new XSSFWorkbook(bis);

        mapCellStyleLeft.put("align", "left");
        mapCellStyleBG.put("bg_color", "204,255,204");

        sheet1 = wb.getSheetAt(0);

        XSSFRow rowX = null;
        XSSFCell cellX = null;

        int intRowInit = 3, intColSize = 16;
        int intRow1 = 5, intRow1T = 5, intRow1M = 5, intRow1MC = 0;
        int intRowX = intRowInit;
        int intRowXLv1 = intRowX, intRowXLv2 = intRowX, intRowXLv3 = intRowX;
        int lv3Point = 0, lv3PointT = 0, lv3PointC = 0;
        String lv1Cod = null, lv2Cod = null, lv3Cod = null;

        List<String> listCellInfo = new ArrayList<String>();

        for(int i = 0; i< listReport.size();i++) {
            EgovMap mapRows = (EgovMap) listReport.get(i);
            String asw = mapRows.get("uirAswVal")!=null?mapRows.get("uirAswVal").toString():"";

            listCellInfo.clear();
            if(lv1Cod==null||!lv1Cod.equals(mapRows.get("ucm1lvCod").toString())) {
                if(intRowXLv1 != intRowX) mergedCell(sheetX, intRowXLv1, intRowX-1, 0, 0);
                if(intRowXLv2 != intRowX) mergedCell(sheetX, intRowXLv2, intRowX-1, 1, 3);
                if(intRowXLv3 != intRowX){
                    mergedCell(sheetX, intRowXLv3, intRowX-1, 4, 5);
                    mergedCell(sheetX, intRowXLv3, intRowX-1, 9, 9);
                    sheet1.getRow(intRow1).getCell(6).setCellValue(lv3Point);
                    setSheet1P(intRow1, intRow1M, intRow1MC, intRow1T, lv3PointC, lv3PointT);
                    intRow1++;
                    intRow1T++;
                    intRow1M += ++intRow1MC;
                    lv3PointT = lv3PointC = intRow1MC = 0;
                }
                lv1Cod = String.valueOf(mapRows.get("ucm1lvCod"));
                sheetX = wb.getSheetAt(Integer.valueOf(lv1Cod)+1);
                listCellInfo.add(lv1Cod+". "+mapRows.get("ucm1lvNam"));
                intRowXLv1=intRowXLv2=intRowXLv3=intRowX=intRowInit;
            }else{
                listCellInfo.add("");
            }

            if(!String.valueOf(mapRows.get("ucm2lvCod")).trim().equals(lv2Cod)){
                if(intRowXLv2 != intRowX) mergedCell(sheetX, intRowXLv2, intRowX-1, 1, 3);
                if(intRowXLv3 != intRowX){
                    mergedCell(sheetX, intRowXLv3, intRowX-1, 4, 5);
                    mergedCell(sheetX, intRowXLv3, intRowX-1, 9, 9);
                    sheet1.getRow(intRow1).getCell(6).setCellValue(lv3Point);
                    intRow1++;
                    lv3PointC++;
                    intRow1MC++;
                }
                lv2Cod = String.valueOf(mapRows.get("ucm2lvCod")).trim();
                listCellInfo.add(lv2Cod+". "+String.valueOf(mapRows.get("ucm2lvNam")).trim());
                listCellInfo.add(String.valueOf(mapRows.get("ucm2lvDtl")).trim());
                listCellInfo.add("Y");//적용여부
                intRowXLv2=intRowXLv3=intRowX;
            }else{
                listCellInfo.add("");
                listCellInfo.add("");
                listCellInfo.add("");//적용여부
            }

            if(!String.valueOf(mapRows.get("ucm3lvCod")).trim().equals(lv3Cod)){
                if(intRowXLv3 != intRowX){
                    mergedCell(sheetX, intRowXLv3, intRowX-1, 4, 5);
                    mergedCell(sheetX, intRowXLv3, intRowX-1, 9, 9);
                    sheet1.getRow(intRow1).getCell(6).setCellValue(lv3Point);
                    intRow1++;
                    lv3PointC++;
                    intRow1MC++;
                    lv3Point=0; //선택을 안한경우...
                }
                lv3Cod = String.valueOf(mapRows.get("ucm3lvCod")).trim();
                listCellInfo.add(lv3Cod.replaceFirst("^"+lv2Cod+"\\.", "")+". "+String.valueOf(mapRows.get("ucm3lvNam")).trim());
                listCellInfo.add("Y");//적용여부
                listCellInfo.add(mapRows.get("seq")+". "+mapRows.get("title"));

                if(mapRows.get("seq").toString().equals(asw)){
                    listCellInfo.add("O");//평가결과
                    lv3Point = Integer.valueOf(mapRows.get("seq").toString());
                    lv3PointT += lv3Point;
                }else{
                    listCellInfo.add("");//평가결과
                }

                listCellInfo.add(String.valueOf(mapRows.get("note")));
                listCellInfo.add("인터뷰\n/\n문서검토");
                
                intRowXLv3=intRowX;
            }else{
                listCellInfo.add("");
                listCellInfo.add("Y");//적용여부
                listCellInfo.add(mapRows.get("seq")+". "+mapRows.get("title"));

                if(mapRows.get("seq").toString().equals(asw)){
                    listCellInfo.add("O");//평가결과
                    lv3Point = Integer.valueOf(mapRows.get("seq").toString());
                    lv3PointT += lv3Point;
                }else{
                    listCellInfo.add("");//평가결과
                }

                listCellInfo.add(String.valueOf(mapRows.get("note")));
            }

            rowX = createRowCell(wb, sheetX, intRowX++, intColSize);
            for(int c=0; c<listCellInfo.size(); c++) {
                cellX = rowX.getCell(c);
                cellX.setCellValue(listCellInfo.get(c));

                if(c==2||c==4||c==6||c==8){
                    cellX.setCellStyle(cellStyle(wb, mapCellStyleLeft));
                }else if(c==7){
                    cellX.setCellStyle(cellStyle(wb, mapCellStyleBG));
                }

            }
        }
        if(intRowXLv1 != intRowX) mergedCell(sheetX, intRowXLv1, intRowX-1, 0, 0);
        if(intRowXLv2 != intRowX) mergedCell(sheetX, intRowXLv2, intRowX-1, 1, 3);
        if(intRowXLv3 != intRowX){
            mergedCell(sheetX, intRowXLv3, intRowX-1, 4, 5);
            mergedCell(sheetX, intRowXLv3, intRowX-1, 9, 9);
            sheet1.getRow(intRow1).getCell(6).setCellValue(lv3Point);
            setSheet1P(intRow1, intRow1M, intRow1MC, intRow1T, lv3PointC, lv3PointT);
            intRow1T++;
        }
        sheet1.getRow(1).getCell(4).setCellFormula("R18");
        sheet1.getRow(intRow1T).getCell(13).setCellFormula("SUM(N6:N17)");
        sheet1.getRow(intRow1T).getCell(14).setCellFormula("SUM(O6:O17)");
        sheet1.getRow(intRow1T).getCell(15).setCellFormula("N18*5");
        sheet1.getRow(intRow1T).getCell(16).setCellFormula("O18/P18");
        sheet1.getRow(intRow1T).getCell(17).setCellFormula("SUM(R6:R17)/COUNT(R6:R17)");

        ServletOutputStream out = response.getOutputStream();
        wb.write(out);
        if (out != null) out.close();
    }

    private void setSheet1P(int intRow, int intRowM, int intRowMC, int intRowT, int lv3PointC, int lv3PointT){
        sheet1.getRow(intRow).getCell(7).setCellValue(lv3PointT);
        sheet1.getRow(intRow).getCell(8).setCellValue(lv3PointC+1);
        sheet1.getRow(intRowM).getCell(9).setCellFormula("MIN(G"+(intRowM+1)+":G"+(intRowM+intRowMC+1)+")");
        sheet1.getRow(intRowT).getCell(13).setCellValue(lv3PointC+1);
        sheet1.getRow(intRowT).getCell(14).setCellValue(lv3PointT);
        sheet1.getRow(intRowT).getCell(15).setCellFormula("N"+(intRowT+1)+"*5");
        sheet1.getRow(intRowT).getCell(16).setCellFormula("O"+(intRowT+1)+"/P"+(intRowT+1));
        sheet1.getRow(intRowT).getCell(17).setCellFormula("MIN($G$"+(intRowM+1)+":$G$"+(intRowM+intRowMC+1)+")");
    }

    private void mergedCell(XSSFSheet sheet, int rowS, int rowF, int cellS, int cellF){
        for(int i=cellS; i<=cellF; i++) {
            sheet.addMergedRegion(new CellRangeAddress(rowS, rowF, i, i));
        }
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
