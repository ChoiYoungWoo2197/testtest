package com.uwo.isms.excelNew;

import com.uwo.isms.service.ExcelNewService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.AbstractView;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

public class excelWorkState extends AbstractView {
    private static final String CONTENT_TYPE = "application/vnd.ms-excel";

    @Autowired
    private ServletContext servletContext;

    @Resource(name = "excelNewService")
    ExcelNewService excelNewService;

    public excelWorkState() {
        setContentType(CONTENT_TYPE);
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List listReports = new ArrayList();
        listReports.add(model.get("noWorkList"));
        listReports.add(model.get("delayWorkList"));
        listReports.add(model.get("comWorkList"));
        List<String> listSheetTitles = Arrays.asList("미진행업무", "지연업무", "완료업무");
        List<String> listCellTitles = Arrays.asList("업무코드", "업무명", "업무설명", "업무상태", "업무결과", "업무수행내용", "업무주기", "업무마감일", "담당자", "서비스", "처리상태","최초 완료일","최종 수정일");
        List<Integer> listCellWidths = Arrays.asList(60, 150, 200, 80, 60, 280, 80, 120, 100, 100, 100, 100, 100);

        XSSFWorkbook wb = new XSSFWorkbook();

        Map<String, Object> mapCellStyleTitleEdit = new HashMap<String, Object>();
        mapCellStyleTitleEdit.put("align", "center");
        mapCellStyleTitleEdit.put("bg_color", "155,194,230");

        XSSFSheet sheetX;
        XSSFRow rowX;
        XSSFCell cellX;

        int intRowX;
        String strPrg="";

        List<String> listCellInfo = new ArrayList<String>();

        for(int s=0; s<listReports.size(); s++) {
            List<EgovMap> listReport = (List<EgovMap>) listReports.get(s);
            sheetX = wb.createSheet(listSheetTitles.get(s));
            listCellInfo.clear();
            listCellInfo.add("utwWrkKey");
            listCellInfo.add("utdDocNam");
            listCellInfo.add("utdDocEtc");
            listCellInfo.add("displayWrkSta");
            listCellInfo.add("utwWrkPrg");
            listCellInfo.add("wrkDtl");
            listCellInfo.add("utwTrmCod");
            listCellInfo.add("utwEndDat");
            listCellInfo.add("utwWrkId");
            listCellInfo.add("utwSvcCod");
            if(s==2){
                listCellInfo.add("utwComNam");
            }
            rowX = sheetX.createRow(2);
            for(int c=0; c<listCellInfo.size(); c++){
                cellX = rowX.createCell(c);
                cellX.setCellValue(listCellTitles.get(c));
                if(s!=2&&(c==4||c==5)){
                    cellX.setCellStyle(cellStyle(wb, mapCellStyleTitleEdit));
                }
                sheetX.setColumnWidth(c, Math.round(listCellWidths.get(c)*36.5f));
            }

            intRowX=3;
            for(int r=0; r<listReport.size(); r++) {
                EgovMap mapRows = (EgovMap) listReport.get(r);
                rowX = sheetX.createRow(intRowX+r);
                for(int c=0; c<listCellInfo.size(); c++){
                    cellX = rowX.createCell(c);
                    if(c== 4){
                        switch(mapRows.get(listCellInfo.get(c)).toString()){
                            case "100":
                                strPrg = "Y"; break;
                            case "70":
                                strPrg = "UP"; break;
                            case "30":
                                strPrg = "LP"; break;
                            case "0":
                            	if("10".equals(mapRows.get("utwComSta"))) {
                            		strPrg = ""; break;
                            	} else {
                            		strPrg = "N"; break;
                            	}
                            case "-1":
                                strPrg = "N/A"; break;
                        }
                        cellX.setCellValue(strPrg);
                    }else if(c==5||c==11||c==12){
                        cellX.setCellValue((String) mapRows.get(listCellInfo.get(c)));
                    }else{
                        cellX.setCellValue(mapRows.get(listCellInfo.get(c))!=null?String.valueOf(mapRows.get(listCellInfo.get(c))):"");
                    }
                }
            }
        }
        ServletOutputStream out = response.getOutputStream();
        wb.write(out);
        if (out != null) out.close();
    }


    private XSSFCellStyle cellStyle(XSSFWorkbook wb, Map<String, Object> mapCellStyle){
        XSSFCellStyle cs = wb.createCellStyle();
        if(mapCellStyle.size()>0) {
            Map<String, Object> mapCellStyles = new HashMap<String, Object>();
            mapCellStyles.put("_cs", cs);
            mapCellStyles.put("_custom", mapCellStyle);
            cs = excelNewService.cellStyle(mapCellStyles);
        }
        return cs;
    }

}
