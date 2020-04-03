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

public class excelReportSAC extends AbstractView {
    private static final String CONTENT_TYPE = "application/vnd.ms-excel";

    @Autowired
    private ServletContext servletContext;

    @Resource(name = "excelNewService")
    ExcelNewService excelNewService;

    private XSSFWorkbook wb = null;
    private XSSFSheet sheet1 = null;
    private XSSFSheet sheet2 = null;

    private Map<String, Object> mapCellStyleLeft = new HashMap<String, Object>();
    private Map<String, Object> mapCellStyle8 = new HashMap<String, Object>();
    private Map<String, Object> mapCellStyle11 = new HashMap<String, Object>();
    private Map<String, Object> mapCellStyle11R = new HashMap<String, Object>();
    private Map<String, Object> mapCellStyle12 = new HashMap<String, Object>();
    private Map<String, Object> mapCellStyle11C = new HashMap<String, Object>();
    private Map<String, Object> mapCellStyle11L = new HashMap<String, Object>();
    private Map<String, Object> mapCellStyle11Per = new HashMap<String, Object>();

    public excelReportSAC() {
        setContentType(CONTENT_TYPE);
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String formFile = servletContext.getRealPath("/") + "excelForm" + File.separator + "form_sac.xlsx";
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(formFile));

        List<?> listReport = (List<?>) model.get("listComps");

        wb = new XSSFWorkbook(bis);

        initCellStyle();

        sheet1 = wb.getSheetAt(0);
        sheet2 = wb.getSheetAt(1);

        XSSFRow row2 = null;
        XSSFCell cell2 = null;

        int intRow1Init = 6, intRow2Init = 3, intCol2Size = 22;
        int intRow2 = intRow2Init;
        int intRow1Lv0 = intRow1Init, intRow1Lv1 = intRow1Init, intRow1Lv2 = intRow1Init, intRow1P2 = intRow1Init;
        int intRow2Lv0 = intRow2, intRow2Lv1 = intRow2, intRow2Lv2 = intRow2;

        int intNewLv0 = 1, intNewLv1 = 1, intNewLv2 = 1;

        String strLv0 = null, strLv1 = null, strLv2 = null;
        String lv1Cod =null, strLv1Nam = null, strLv2Nam = null, strPrvLv1Nam = null, strPrvLv2Nam = null;
        String strCnvLv1 = null, strCnvLv2 = null;

        String strLvTitle[] = {"관리체계 수립 및 운영", "보호대책 요구사항"};

        //test
        String[] strRef = {"1.2.1","2.1.3","2.3.3","2.3.4","2.4.1","2.4.2","2.4.3","2.4.4","2.4.5","2.4.6","2.4.7","2.5.1","2.5.2","2.5.3","2.5.4","2.5.5","2.5.6","2.6.1","2.6.2","2.6.3","2.6.4","2.6.5","2.6.6","2.6.7","2.7.1","2.7.2","2.8.1","2.8.2","2.8.3","2.8.4","2.8.5","2.8.6","2.9.1","2.9.2","2.9.3","2.9.4","2.9.5","2.9.6","2.9.7","2.10.1","2.10.2","2.10.3","2.10.7","2.10.8","2.10.9"};

        List<String> listCellInfo = new ArrayList<String>();

        for(int i = 0; i< listReport.size();i++) {
            EgovMap mapRows = (EgovMap) listReport.get(i);
            //test
            if(!Arrays.asList(strRef).contains(mapRows.get("ucm2lvCod").toString())) continue;

            listCellInfo.clear();
            lv1Cod = String.valueOf(mapRows.get("ucm1lvCod"));
            strLv1Nam = mapRows.get("ucm1lvNam").toString();
            strLv2Nam = mapRows.get("ucm2lvNam").toString();
            if(strPrvLv1Nam==null) strPrvLv1Nam=strLv1Nam;
            if(strPrvLv2Nam==null) strPrvLv2Nam=strLv2Nam;

            if(strLv0==null||!strLv0.equals(lv1Cod.substring(0, lv1Cod.indexOf(".")))){
                if(intRow2Lv0 != intRow2){
                    mergedCell(sheet2, intRow2Lv0, intRow2-1, 0, 1);
                    setSheet1P1(0, intRow1Lv0, Arrays.asList(String.valueOf(intNewLv0), strLvTitle[intNewLv0-1]));
                    mergedCell(sheet1, intRow1Lv0, intRow1Lv2, 1, 2);
                }
                if(intRow2Lv1 != intRow2){
                    newRowLv1(intRow1Lv1, intRow1Lv2, intRow1P2, intRow2, intRow2Lv1, Arrays.asList(strCnvLv1, strPrvLv1Nam));
                    strPrvLv1Nam = strLv1Nam;
                    intRow1P2++;
                }
                if(intRow2Lv2 != intRow2){
                    newRowLv2(intRow2, intRow1Lv2, intRow2Lv2, Arrays.asList(strCnvLv2, strPrvLv2Nam, "0"));
                    strPrvLv2Nam = strLv2Nam;
                    intRow1Lv2++;
                }
                strLv0 = lv1Cod.substring(0, lv1Cod.indexOf("."));
                listCellInfo.add(String.valueOf(intNewLv0));
                listCellInfo.add(strLvTitle[intNewLv0-1]);
                intRow2Lv0=intRow2Lv1=intRow2Lv2=intRow2;
                intRow1Lv0=intRow1Lv1=intRow1Lv2;
                intNewLv0++;
                intNewLv1 = 1;
                intNewLv2 = 1;
            }else{
                listCellInfo.add("-");
                listCellInfo.add("-");
            }

            if(strLv1==null||!strLv1.equals(lv1Cod)){
                if(intRow2Lv1 != intRow2){
                    newRowLv1(intRow1Lv1, intRow1Lv2, intRow1P2, intRow2, intRow2Lv1, Arrays.asList(strCnvLv1, strPrvLv1Nam));
                    strPrvLv1Nam = strLv1Nam;
                    intRow1P2++;
                }
                if(intRow2Lv2 != intRow2){
                    newRowLv2(intRow2, intRow1Lv2, intRow2Lv2, Arrays.asList(strCnvLv2, strPrvLv2Nam, "0"));
                    strPrvLv2Nam = strLv2Nam;
                    intRow1Lv2++;
                }
                strLv1 = lv1Cod;
                strCnvLv1 = intNewLv0+"."+intNewLv1;
                listCellInfo.add(strCnvLv1);
                listCellInfo.add(strLv1Nam);
                intRow2Lv1=intRow2Lv2=intRow2;
                intRow1Lv1=intRow1Lv2;
                intNewLv1++;
                intNewLv2 = 1;
            }else{
                listCellInfo.add("-");
                listCellInfo.add("-");
            }

            if(strLv2==null||!strLv2.equals(mapRows.get("ucm2lvCod").toString())){
                if(intRow2Lv2 != intRow2){
                    newRowLv2(intRow2, intRow1Lv2, intRow2Lv2, Arrays.asList(strCnvLv2, strPrvLv2Nam, "0"));
                    strPrvLv2Nam = strLv2Nam;
                    intRow1Lv2++;
                }
                strLv2 = String.valueOf(mapRows.get("ucm2lvCod"));
                strCnvLv2 = intNewLv0+"."+intNewLv1+"."+intNewLv2;

                listCellInfo.add(strCnvLv2);
                listCellInfo.add(strLv2Nam);
                listCellInfo.add(mapRows.get("ucm2lvDtl").toString());
                listCellInfo.add(mapRows.get("ucm2lvCod").toString());

                intRow2Lv2=intRow2;
                intNewLv2++;
            }else{
                listCellInfo.add("-");
                listCellInfo.add("-");
                listCellInfo.add("-");
                listCellInfo.add("-");
            }

            listCellInfo.add("");
            listCellInfo.add(mapRows.get("ucm3lvNam").toString());

            //listCellInfo.add(mapRows.get("ucm3lvDtl").toString());
            listCellInfo.add("-");

            listCellInfo.add("");
            listCellInfo.add("IF(L"+(intRow2+1)+"=\"N/A\",\"-\",IF(L"+(intRow2+1)+"=\"Y\",100%,IF(L"+(intRow2+1)+"=\"N\",0%,IF(L"+(intRow2+1)+"=\"P\",\"기입요망\",IF(L"+(intRow2+1)+"=\"\",\"\")))))");
            /*listCellInfo.add("");
            listCellInfo.add("");
            listCellInfo.add("");
            listCellInfo.add("");
            listCellInfo.add("");
            listCellInfo.add("");
            listCellInfo.add("");
            listCellInfo.add("");
            listCellInfo.add("");*/

            row2 = createRowCell(sheet2, intRow2++, intCol2Size);
            for(int c=0; c<listCellInfo.size(); c++) {
                cell2 = row2.getCell(c);
                cell2.setCellValue(listCellInfo.get(c));

                if(c==12){
                    cell2.setCellFormula(listCellInfo.get(c));
                }

                if(c==8){
                    cell2.setCellStyle(cellStyle(mapCellStyle8));
                }else if(c==9||c==10){
                    cell2.setCellStyle(cellStyle(mapCellStyleLeft));
                }else if(c==11){
                    if(listCellInfo.get(c)==""){
                        cell2.setCellStyle(cellStyle(mapCellStyle11R));
                    }else {
                        cell2.setCellStyle(cellStyle(mapCellStyle11));
                    }
                }else if(c==12){
                    cell2.setCellStyle(cellStyle(mapCellStyle12));
                }
            }
            row2.createCell(23).setCellFormula("IF(L"+(intRow2+1)+"=\"N/A\",COUNTBLANK(N"+(intRow2+1)+"),COUNTBLANK((O"+(intRow2+1)+":T"+(intRow2+1)+")))");
        }
        if(intRow2Lv0 != intRow2){
            mergedCell(sheet2, intRow2Lv0, intRow2-1, 0, 1);
            setSheet1P1(0, intRow1Lv0, Arrays.asList(String.valueOf(intNewLv0), strLvTitle[intNewLv0-1]));
            mergedCell(sheet1, intRow1Lv0, intRow1Lv2, 1, 2);
        }
        if(intRow2Lv1 != intRow2){
            newRowLv1(intRow1Lv1, intRow1Lv2, intRow1P2, intRow2, intRow2Lv1, Arrays.asList(strCnvLv1, strPrvLv1Nam));
        }
        if(intRow2Lv2 != intRow2){
            newRowLv2(intRow2, intRow1Lv2, intRow2Lv2, Arrays.asList(strCnvLv2, strPrvLv2Nam, "0"));
        }

        sheet2.getRow(0).getCell(18).setCellFormula("SUM(X"+intRow2Init+":X"+(intRow2-1)+")");

        sheet1.getRow(51).getCell(7).setCellFormula("'정보보호 체크리스트_운영, 개발'!S1");
        sheet1.getRow(52).getCell(7).setCellFormula("IF(I53=\"-\",\"N/A\",IF(I53=100%,\"Y\",IF(I53=0%,\"N\",\"P\")))");
        sheet1.getRow(52).getCell(8).setCellFormula("AVERAGE(I"+(intRow1Init+1)+":I"+(intRow1Lv2+1)+")");

        sheet1.getRow(2).getCell(12).setCellFormula("I53");
        sheet1.getRow(16).getCell(12).setCellFormula("SUM(M7:M16)");
        sheet1.getRow(16).getCell(13).setCellFormula("SUM(N7:N16)");
        sheet1.getRow(16).getCell(14).setCellFormula("SUM(O7:O16)");
        sheet1.getRow(16).getCell(15).setCellFormula("SUM(P7:P16)");
        sheet1.getRow(16).getCell(16).setCellFormula("SUM(Q7:Q16)");

        ServletOutputStream out = response.getOutputStream();
        wb.write(out);
        if (out != null) out.close();
    }

    private void initCellStyle(){

        mapCellStyleLeft.put("align", "left");

        mapCellStyle8.put("bg_color", "253,233,217");
        mapCellStyle8.put("per", true);

        mapCellStyle11.put("bg_color", "218,238,243");

        mapCellStyle11R.put("bg_color", "192,80,77");

        mapCellStyle12.put("bg_color", "218,238,243");
        mapCellStyle12.put("per", true);

        mapCellStyle11C.put("font_size", 11);

        mapCellStyle11L.put("font_size", 11);
        mapCellStyle11L.put("align", "left");

        mapCellStyle11Per.put("font_size", 11);
        mapCellStyle11Per.put("per", true);
    }

    private void setFml8(XSSFCell cell, int intS, int intF){
        String strFml = "IF(AND(";
        for(int c=intS; c<intF; c++) {
            if(c!=intS) strFml += ",";
            strFml += "M"+c+"=\"-\"";
        }
        strFml += "),\"-\",IF(SUM(M"+intS+":M"+(intF-1)+")>0";
        strFml += ",AVERAGE(M"+intS+":M"+(intF-1)+"),IF(SUM(M"+intS+":M"+(intF-1)+")=0,0)))";
        cell.setCellFormula(strFml);
    }

    private void newRowLv1(int intRow1Lv1, int intRow1Lv2, int intRow1P2, int intRow2, int intRow2Lv1, List<String> strVals){
        mergedCell(sheet2, intRow2Lv1, intRow2-1, 2, 3);
        setSheet1P1(1, intRow1Lv1, strVals);
        mergedCell(sheet1, intRow1Lv1, intRow1Lv2, 3, 4);
        setSheet1P2(intRow1P2, intRow1Lv1, intRow1Lv2);
    }

    private void newRowLv2(int intRow2, int intRow1Lv2, int intRow2Lv2, List<String> strVals){
        mergedCell(sheet2, intRow2Lv2, intRow2-1, 4, 7);
        setFml8(sheet2.getRow(intRow2Lv2).getCell(8), intRow2Lv2, intRow2);
        setSheet1P1(2, intRow1Lv2, strVals);
    }

    private void setSheet1P1(int strLv, int intRow, List<String> strVals){
        if(strLv==0){
            sheet1.getRow(intRow).getCell(1).setCellValue(strVals.get(0));
            sheet1.getRow(intRow).getCell(1).setCellStyle(cellStyle(mapCellStyle11C));
            sheet1.getRow(intRow).getCell(2).setCellValue(strVals.get(1));
            sheet1.getRow(intRow).getCell(2).setCellStyle(cellStyle(mapCellStyle11C));
        }else if(strLv==1){
            sheet1.getRow(intRow).getCell(3).setCellValue(strVals.get(0));
            sheet1.getRow(intRow).getCell(3).setCellStyle(cellStyle(mapCellStyle11C));
            sheet1.getRow(intRow).getCell(4).setCellValue(strVals.get(1));
            sheet1.getRow(intRow).getCell(4).setCellStyle(cellStyle(mapCellStyle11C));
        }else if(strLv==2){
            sheet1.getRow(intRow).getCell(5).setCellValue(strVals.get(0));
            sheet1.getRow(intRow).getCell(5).setCellStyle(cellStyle(mapCellStyle11C));
            sheet1.getRow(intRow).getCell(6).setCellValue(strVals.get(1));
            sheet1.getRow(intRow).getCell(6).setCellStyle(cellStyle(mapCellStyle11L));
            sheet1.getRow(intRow).getCell(7).setCellFormula("IF(I"+(intRow+1)+"=\"-\",\"N/A\",IF(I"+(intRow+1)+"=100%,\"Y\",IF(I"+(intRow+1)+"=0%,\"N\",\"P\")))");
            sheet1.getRow(intRow).getCell(8).setCellValue(Double.valueOf(strVals.get(2))/100);
            sheet1.getRow(intRow).getCell(8).setCellStyle(cellStyle(mapCellStyle11Per));
        }
    }

    private void setSheet1P2(int intRow, int intLv1, int intLv2){
        sheet1.getRow(intRow).getCell(11).setCellFormula("E"+(intLv1+1));
        sheet1.getRow(intRow).getCell(12).setCellFormula("COUNTIF($H$"+(intLv1+1)+":$H$"+(intLv2+1)+",\"Y\")");
        sheet1.getRow(intRow).getCell(13).setCellFormula("COUNTIF($H$"+(intLv1+1)+":$H$"+(intLv2+1)+",\"P\")");
        sheet1.getRow(intRow).getCell(14).setCellFormula("COUNTIF($H$"+(intLv1+1)+":$H$"+(intLv2+1)+",\"N\")");
        sheet1.getRow(intRow).getCell(15).setCellFormula("COUNTIF($H$"+(intLv1+1)+":$H$"+(intLv2+1)+",\"N/A\")");
        sheet1.getRow(intRow).getCell(16).setCellFormula("SUM(M"+(intRow+1)+":P"+(intRow+1)+")");
    }

    private void mergedCell(XSSFSheet sheet, int rowS, int rowF, int cellS, int cellF){
        for(int i=cellS; i<=cellF; i++) {
            sheet.addMergedRegion(new CellRangeAddress(rowS, rowF, i, i));
        }
    }

    private XSSFRow createRowCell(XSSFSheet sheet, int intRow, int intCols){
        XSSFRow row = sheet.createRow(intRow);
        XSSFCell cell = null;
        Map<String, Object> mapCellStyle = new HashMap<String, Object>();
        for(int i=0; i<intCols; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(cellStyle(mapCellStyle));
        }
        return row;
    }

    private XSSFCellStyle cellStyle(Map<String, Object> mapCellStyle){
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
        if(mapCellStyle.get("per")!=null&&mapCellStyle.get("per").equals(true)){
            XSSFDataFormat format = wb.createDataFormat();
            cs.setDataFormat(format.getFormat("0%"));
        }
        return cs;
    }

}
