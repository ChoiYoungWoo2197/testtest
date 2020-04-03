/**
 ***********************************
 * @source ExcelNewService.java
 * @date 2019. 06. 25.
 * @project isams skb
 * @description excel new service
 ***********************************
 */
package com.uwo.isms.service;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface ExcelNewService {

    Map<String, Object> uploadExcel(String stndKind, HttpServletRequest req) throws Exception;

    List<?> downComps003(Map<String, String> mapComps);

    public List<?> fm_comps003_3D_mappinglistInCtrKey(Map mapCtrKeys);
    public String fm_comps003_excelNew(String stndKind, Map<String, Object> mapReqInfo, Map<String, Object> mapExcel);
    public List<?> fm_comps003_3D_ExpData(String stndKind, Map mapKeyCodes);
    public String fm_comps003_excelNew_D3(Map<String, Object> mapReqInfo, HttpServletRequest req) throws Exception;
    public String fm_inspt004_excelNew(Map<String, Object> mapReqInfo, HttpServletRequest req) throws Exception;


    public String getStndKind(String code);
    public List<?> getCompList();
    public List<?> getCompliance(String comp);

    public XSSFCellStyle cellStyle(Map<String, Object> mapCellStyle);

    public String fmMwork006_excelNew_Save(Map<String, Object> mapReqInfo, HttpServletRequest req) throws Exception;
}
