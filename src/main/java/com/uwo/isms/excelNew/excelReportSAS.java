package com.uwo.isms.excelNew;


import com.uwo.isms.service.ExcelNewService;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.AbstractView;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

public class excelReportSAS  extends AbstractView {
    private static final String CONTENT_TYPE = "application/vnd.ms-excel";

    @Autowired
    private ServletContext servletContext;

    @Resource(name = "excelNewService")
    ExcelNewService excelNewService;


    private XSSFWorkbook wb = null;
    private XSSFSheet sheet1 = null;

    public excelReportSAS() {
        setContentType(CONTENT_TYPE);
    }



    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String formFile = servletContext.getRealPath("/") + "excelForm" + File.separator + "form_sac.xlsx";
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(formFile));

        List<?> listReport = (List<?>) model.get("listComps");

        wb = new XSSFWorkbook(bis);



    }



}
