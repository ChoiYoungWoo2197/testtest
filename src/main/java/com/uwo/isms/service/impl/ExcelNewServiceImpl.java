/**
 ***********************************
 * @source ExcelNewServiceImpl.java
 * @date 2019. 06. 25.
 * @project isams skb
 * @description excel new service impl
 ***********************************
 */
package com.uwo.isms.service.impl;

import java.io.File;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.uwo.isms.common.Constants;
import com.uwo.isms.dao.ExcelNewDAO;
import com.uwo.isms.dao.FMCompsDAO;
import com.uwo.isms.dao.FMMworkDAO;
import com.uwo.isms.service.CommonUtilService;
import com.uwo.isms.service.ExcelNewService;
import com.uwo.isms.web.FMSetupController;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service("excelNewService")
public class ExcelNewServiceImpl implements ExcelNewService {
    Logger log = LogManager.getLogger(FMSetupController.class);

    @Autowired
    CommonUtilService commonUtilService;

    @Resource(name="excelNewDAO")
    private ExcelNewDAO excelNewDAO;

    @Resource(name = "fmCompsDAO")
    private FMCompsDAO fmCompsDAO;

    @Resource(name="fmMworkDAO")
    private FMMworkDAO fmMworkDAO;

    @Autowired
    private ServletContext servletContext;

    @Override
    public Map<String, Object> uploadExcel(String stndKind, HttpServletRequest req) throws Exception {
        //Excel To Object

        if(stndKind==null || stndKind.equals(null)) return failureMessage("룰타입이 설정되지 않았습니다.");

        Map<String, Object> mapRule = loadRule(stndKind); //룰 로딩

        if(mapRule.get("result")!="success") return mapRule;

        Map<String, Object> mapExcel = new HashMap<String, Object>();
        List listExcelSheetName = new ArrayList();
        List listExcelSheet = new ArrayList();

        MultipartHttpServletRequest mReq = (MultipartHttpServletRequest) req;
        InputStream file = commonUtilService.decryptFileDAC(mReq.getFile("excelFile"));
        Workbook wb = WorkbookFactory.create(file);

        FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();

        Map<String, String> mapRuleSheets = (Map<String, String>) mapRule.get("sheets");
        DataFormatter formatter = new DataFormatter();
        DecimalFormat df = new DecimalFormat();

        if(wb.getNumberOfSheets()<mapRuleSheets.size()){
            return failureMessage("시트수가 룰의 시트수보다 적습니다.");
        }

        for(int i=0; i<mapRuleSheets.size(); i++) {
            String strRuleSheet = mapRuleSheets.get("sheet_"+(i+1));
            if(!strRuleSheet.equals(null)&&strRuleSheet!="") {
                Map<String, Object> mapRuleSheet = (Map<String, Object>) mapRule.get(strRuleSheet);
                Sheet sheet = wb.getSheetAt(i);

                listExcelSheetName.add(sheet.getSheetName());

                int intStartRow = Integer.parseInt(mapRuleSheet.get("startRow").toString());
                int intStartCell = Integer.parseInt(mapRuleSheet.get("startCell").toString());
                int intFinishCell = Integer.parseInt(mapRuleSheet.get("finishCell").toString());

                Map<String, Object> mapRuleCells = (Map<String, Object>) mapRuleSheet.get("cells");
                List listExcelRow = new ArrayList();
                int intLastRow = sheet.getLastRowNum();

                for(int r=intStartRow-1; r<=intLastRow; r++) {
                    Row row = sheet.getRow(r);
                    List listExcelCell = new ArrayList();

                    if(row!=null){
                        for(int c=intStartCell-1; c<intFinishCell; c++){
                            Map<String, String> mapRuleCell = (Map<String, String>) mapRuleCells.get(String.valueOf(c+1));
                            String strCellType = "String";
                            String strCellValue = null;
                            Boolean nullCheck = false;

                            if(mapRuleCell!=null){
                                if(mapRuleCell.get("type")!=null) strCellType = String.valueOf(mapRuleCell.get("type"));
                                if(mapRuleCell.get("nullCheck")!=null){
                                    nullCheck = Boolean.parseBoolean(mapRuleCell.get("nullCheck"));
                                }
                                try{
                                    Cell cell = row.getCell(c);

                                    if(cell!=null) {
                                        //System.out.println(cell.getCellType() + " / " + evaluator.evaluateFormulaCell(cell));
                                        if( cell.getCellType() == Cell.CELL_TYPE_FORMULA ){
                                            //System.out.println(evaluator.evaluateFormulaCell(cell));
                                            if(evaluator.evaluateFormulaCell(cell)==Cell.CELL_TYPE_NUMERIC) {
                                                strCellValue = df.format(cell.getNumericCellValue());
                                            }else{
                                                strCellValue = cell.getStringCellValue();
                                            }
                                        }else{
                                            strCellValue = formatter.formatCellValue(cell);
                                            //strCellValue = strCellValue.replace("\n", " ");
                                        }
                                    }
                                    if(strCellValue!=null) strCellValue = strCellValue.trim();

                                    if(!strCellType.equals("String")&&strCellValue!=null){
                                        try {
                                            //현재 데이터타입 검사는 Integer와 Float만 지원함
                                            //String은 검사하지 않음
                                            if(strCellType.equals("Integer")){
                                                int cnvCellValue = Integer.parseInt(strCellValue);
                                                strCellValue = String.valueOf(cnvCellValue);
                                                //System.out.print(cnvCellValue);
                                            }else if(strCellType.equals("Float")){
                                                Float cnvCellValue = Float.parseFloat(strCellValue);
                                                strCellValue = String.valueOf(cnvCellValue);
                                                //System.out.print(cnvCellValue);
                                            }
                                        } catch(Exception e){
                                            return failureMessage("데이터 타입("+strCellType+") 오류\nError [Sheet:"+(i+1)+", Row:"+(r+1)+", Cell:"+(c+1)+"]");
                                        }
                                    }
                                    if(nullCheck&&(strCellValue==null||strCellValue=="")){
                                        String strFailure = "널 값이 허용되지 않는 셀입니다.\n";
                                        strFailure += "Position [Sheet:"+(i+1)+", Row:"+(r+1)+", Cell:"+(c+1)+"]\n";
                                        strFailure += "Last Row [" + (intLastRow+1) + "]";
                                        return failureMessage(strFailure);
                                    }
                                    if(mapRuleCell!=null&&mapRuleCell.get("regex")!=null&&strCellValue!=null&&!StringUtils.isEmpty(strCellValue)) {
                                        if(!Pattern.matches(mapRuleCell.get("regex"), strCellValue) ){
                                            String strFailure = "정규표현식에 맞지 않습니다.\n";
                                            strFailure += "Regex [" + mapRuleCell.get("regex") + "]\n";
                                            strFailure += "Value [" + strCellValue + "]\n";
                                            strFailure += "Position [Sheet:"+(i+1)+", Row:"+(r+1)+", Cell:"+(c+1)+"]";
                                            return failureMessage(strFailure);
                                        }
                                    }
                                } catch(Exception e) {
                                    return failureMessage("Error [Sheet:"+(i+1)+", Row:"+(r+1)+", Cell:"+(c+1)+"]\n" + e);
                                }
                                listExcelCell.add(strCellValue);
                            }
                        }
                    }else{
                        for(int c=intStartCell-1; c<intFinishCell; c++) listExcelCell.add(null);
                    }

                    boolean blAllCellEmpty = true;
                    for(int c=0; c<listExcelCell.size(); c++){
                        if (String.valueOf(listExcelCell.get(c)) != "null" && String.valueOf(listExcelCell.get(c)) != "") {
                            blAllCellEmpty = false;
                            break;
                        }
                    }
                    if(!blAllCellEmpty) {
                        listExcelRow.add(listExcelCell);
                    }
                }
                listExcelSheet.add(listExcelRow);
            }else{
                //룰 검사를 했기때문에 일어날수 없는 에러메세지이지만..
                //return failureMessage("시트에 대한 룰이 없습니다.");
            }
        }

        Map<String, Object> mapSheet = new HashMap<String, Object>();
        mapSheet.put("sheetName", listExcelSheetName);
        mapSheet.put("sheetData", listExcelSheet);

        mapExcel.put("result", "success");
        mapExcel.put("excel", mapSheet);
        //return null;
        return mapExcel;
    }

    private Map<String, Object> loadRule(String stndKind){
        Map<String, Object> mapRule = new HashMap<String, Object>();
        try {
            String path = servletContext.getRealPath("/")+"excelRule" + File.separator + "rule_"+stndKind+".xml";
            Document doc = null;
            try {
                DocumentBuilderFactory docBldFac = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBld = docBldFac.newDocumentBuilder();
                doc = docBld.parse(path);
            } catch (Exception e){
                return failureMessage("룰 파일을 찾을 수 없거나, 사용할 수 없습니다.");
            }

            doc.getDocumentElement().normalize();
            Element elemDoc = doc.getDocumentElement();
            NodeList nodeListRuleStyle = elemDoc.getElementsByTagName("ruleStyle");

            List listRuleStyle = new ArrayList();

            for(int i = 0; i < nodeListRuleStyle.getLength(); i++){
                Node nodeRuleStyle = nodeListRuleStyle.item(i);
                if(nodeRuleStyle.getNodeType() == Node.ELEMENT_NODE){
                    Element elemRuleStyle = (Element) nodeRuleStyle;

                    String strRuleStyleID = elemRuleStyle.getAttribute("id");
                    if( strRuleStyleID.length() == 0 ){
                        return failureMessage("일치하는 룰스타일 ID가 없습니다.");
                    }

                    if(!listRuleStyle.contains("ruleStyle_"+strRuleStyleID)) listRuleStyle.add("ruleStyle_"+strRuleStyleID);

                    String strStartRow = elemRuleStyle.getAttribute("startRow");
                    if( strStartRow.length() == 0 ){
                        return failureMessage("룰에 startRow가 정의되지 않았습니다.");
                    }

                    Map<String, Object> mapRuleStyle = new HashMap<String, Object>();
                    mapRuleStyle.put("startRow", strStartRow);

                    String strStartCell = elemRuleStyle.getAttribute("startCell");
                    if( strStartCell.length() > 0 ){
                        mapRuleStyle.put("startCell", strStartCell);
                    }else{
                        mapRuleStyle.put("startCell", "1");
                    }
                    String strFinishCell = elemRuleStyle.getAttribute("finishCell");
                    if( strFinishCell.length() > 0 ) mapRuleStyle.put("finishCell", strFinishCell);

                    NodeList nodeListCells = elemRuleStyle.getElementsByTagName("cell");
                    if(nodeListCells.getLength() > 0 ){
                        Map<String, Object> mapCells = new HashMap<String, Object>();
                        for(int j = 0; j < nodeListCells.getLength(); j++) {
                            Node nodeCells = nodeListCells.item(j);
                            if (nodeCells.getNodeType() == Node.ELEMENT_NODE) {
                                Element elemCells = (Element) nodeCells;
                                Map<String, String> mapCell = new HashMap<String, String>();
                                String strOrder = elemCells.getAttribute("order");
                                String strType = elemCells.getAttribute("type");
                                if( strType.length() > 0 ) mapCell.put("type", strType);
                                String strNullCheck = elemCells.getAttribute("nullCheck");
                                if( strNullCheck.length() > 0 ) mapCell.put("nullCheck", strNullCheck);

                                if(elemCells.getChildNodes().getLength() > 0){
                                    NodeList nodeListElemCells = elemCells.getChildNodes();
                                    for(int k = 0; k < nodeListElemCells.getLength(); k++) {
                                        Node nodeElemCell = nodeListElemCells.item(k);
                                        if (nodeElemCell.getNodeType() == Node.ELEMENT_NODE) {
                                            mapCell.put(nodeElemCell.getNodeName(), nodeElemCell.getTextContent());
                                        }
                                    }
                                }
                                mapCells.put(strOrder, mapCell);
                            }
                        }
                        mapRuleStyle.put("cells", mapCells);
                    }
                    mapRule.put("ruleStyle_"+strRuleStyleID, mapRuleStyle);
                }
            }
            NodeList nodeListSheets = elemDoc.getElementsByTagName("sheets");

            for(int i = 0; i < nodeListSheets.getLength(); i++) {
                Node nodeSheets = nodeListSheets.item(i);
                Map<String, String> mapSheets = new HashMap<String, String>();
                if (nodeSheets.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemSheets = (Element) nodeSheets;
                    NodeList nodeListSheet = elemSheets.getElementsByTagName("sheet");
                    for(int j = 0; j < nodeListSheet.getLength(); j++) {
                        Node nodeSheet = nodeListSheet.item(j);
                        if (nodeSheet.getNodeType() == Node.ELEMENT_NODE) {
                            Element elemSheet = (Element) nodeSheet;
                            if(elemSheet.getAttribute("ruleStyle").length() > 0){
                                if(listRuleStyle.contains("ruleStyle_"+elemSheet.getAttribute("ruleStyle"))) {
                                    mapSheets.put("sheet_"+(j+1), "ruleStyle_"+ elemSheet.getAttribute("ruleStyle"));
                                }else{
                                    return failureMessage("룰스타일 ID(\"+elemSheet.getAttribute(\"ruleStyle\")+\")가 존재하지 않습니다.");
                                }
                            }else{
                                //return failureMessage("룰스타일 ID를 찾을 수 업습니다.");
                                mapSheets.put("sheet_"+(j+1), "");
                            }
                        }
                    }
                }
                mapRule.put("sheets", mapSheets);
            }
            mapRule.put("result", "success");
        } catch (Exception e){
            return failureMessage(e.getMessage());
        }
        return mapRule;
    }

    private Map<String, Object> failureMessage(String strMessage){
        Map<String, Object> failureRule = new HashMap<String, Object>();
        failureRule.put("result", "failure");
        failureRule.put("message", strMessage);
        log.info("failureMessage :" +strMessage);
        return failureRule;
    }

    @Override
    public List<?> downComps003(Map<String, String> mapComps) {
        List<?> listComps = new ArrayList<>();
        switch (String.valueOf(excelNewDAO.getStndKind(mapComps.get("ucmCtrGbn")))){
            case "isms" : case "isms_p" :
                listComps = excelNewDAO.downComps003_isms(mapComps);
                break;
            case "infra_mp" :
                listComps = excelNewDAO.downComps003_infra_mp(mapComps);
                break;
            case "msit" :
                listComps = excelNewDAO.downComps003_msit(mapComps);
                break;
            case "infra_la" :
                listComps = excelNewDAO.downComps003_infra_la(mapComps);
                break;
            default :
                listComps = excelNewDAO.downComps003_default(mapComps);
                break;
        }
        return listComps;
    }

    @Override
    public List<?> fm_comps003_3D_mappinglistInCtrKey(Map mapCtrKeys) {
        return excelNewDAO.fm_comps003_3D_mappinglistInCtrKey(mapCtrKeys);
    }

    @Override
    public String fm_comps003_excelNew(String stndKind, Map<String, Object> mapReqInfo, Map<String, Object> mapExcel){
        String[] service = (String[]) mapReqInfo.get("service");
        List listService = new ArrayList();
        for (int i=0; i < service.length-1; i++) {
            listService.add(service[i]);
        }

        mapExcel.put("UCM_BCY_COD", mapReqInfo.get("UCM_BCY_COD"));
        mapExcel.put("UCM_CTR_GBN", mapReqInfo.get("UCM_CTR_GBN"));
        mapExcel.put("UCM_RGT_ID", mapReqInfo.get("UCM_RGT_ID"));
        mapExcel.put("UCM_LVL_GBN", Constants.UCM_LVL_GBN);
        mapExcel.put("listService", listService);

        String strResult = "";
        switch(stndKind){
            case "isms" : case "isms_p" :
                strResult = this.excelNew_isms(mapExcel);
                break;
            case "infra_mp" :
                strResult = this.excelNew_infra_mp(mapExcel);
                break;
            case "msit" :
                strResult = this.excelNew_msit(mapExcel);
                break;
            case "infra_la" :
                strResult = this.excelNew_infra_la(mapExcel);
                break;
            default :
                break;
        }
        return strResult;
    }

    public void excelProcessing(Map<String, String> mapExcelRow, List listService){
        String strCtrKey = fmCompsDAO.fm_comps003_getCtrKey(mapExcelRow);
        if(StringUtils.isNotEmpty(strCtrKey)) {
            mapExcelRow.put("ctrKey", strCtrKey);
            fmCompsDAO.fm_comps003_excelUpdate(mapExcelRow, listService);
        }else{
            mapExcelRow.remove("ctrKey");
            fmCompsDAO.fm_comps003_excelInsert(mapExcelRow, listService);
        }
    }

    public String excelNew_isms(Map<String, Object> mapExcel){
        //ISMS, ISMS-P
        List listSheetData = (List) mapExcel.get("sheetData");
        int lvl3Ctn=1;

        for(int s=0; s<listSheetData.size(); s++){
            List listSheetRows = (List) listSheetData.get(s);
            Map<String, String> mapExcelRow = new HashMap<String, String>();

            mapExcelRow.put("UCM_BCY_COD", mapExcel.get("UCM_BCY_COD").toString());
            mapExcelRow.put("UCM_CTR_GBN", mapExcel.get("UCM_CTR_GBN").toString());
            mapExcelRow.put("UCM_RGT_ID", mapExcel.get("UCM_RGT_ID").toString());
            mapExcelRow.put("UCM_LVL_GBN", Constants.UCM_LVL_GBN);

            for(int r=0; r<listSheetRows.size(); r++){
                List listCells = (List) listSheetRows.get(r);
                if(listCells.get(0)!=null&&StringUtils.isNotEmpty(listCells.get(0).toString())) {
                    mapExcelRow.put("UCM_CTR_COD", String.format("%05d", s+1));
                    mapExcelRow.put("UCM_1LV_COD", listCells.get(0).toString());
                    mapExcelRow.put("UCM_1LV_NAM", listCells.get(1).toString());
                    mapExcelRow.put("UCM_1LD_YN", Constants.USE_N);
                }
                if(listCells.get(2)!=null&&StringUtils.isNotEmpty(listCells.get(2).toString())) {
                    mapExcelRow.put("UCM_2LV_COD", listCells.get(2).toString());
                    mapExcelRow.put("UCM_2LV_NAM", listCells.get(3).toString());
                    mapExcelRow.put("UCM_2LV_DTL", listCells.get(4).toString());
                    mapExcelRow.put("UCM_2LD_YN", Constants.USE_N);
                    lvl3Ctn = 1;
                }

                mapExcelRow.put("UCM_3LV_COD", mapExcelRow.get("UCM_2LV_COD") +"."+ lvl3Ctn);
                mapExcelRow.put("UCM_3LV_NAM", listCells.get(5).toString());
                mapExcelRow.put("UCM_3LV_DTL", listCells.get(6).toString());
                mapExcelRow.put("UCM_3LD_YN", Constants.USE_N);

                this.excelProcessing(mapExcelRow, (List) mapExcel.get("listService"));
                lvl3Ctn++;
            }
        }
        return Constants.RETURN_SUCCESS;
    }

    public String excelNew_infra_mp(Map<String, Object> mapExcel){
        //기반시설 관리적 물리적
        List listSheetName = (List) mapExcel.get("sheetName");
        List listSheetData = (List) mapExcel.get("sheetData");

        List listSheetRows3 = (List) listSheetData.get(0);
        List listCells3 = (List) listSheetRows3.get(0);

        int lvl2Ctn=1, lvl3Ctn = 1;
        for(int s=0; s<listSheetData.size(); s++){
            String strSheetName = (String) listSheetName.get(s);
            List listSheetRows = (List) listSheetData.get(s);
            Map<String, String> mapExcelRow = new HashMap<String, String>();

            mapExcelRow.put("UCM_BCY_COD", mapExcel.get("UCM_BCY_COD").toString());
            mapExcelRow.put("UCM_CTR_GBN", mapExcel.get("UCM_CTR_GBN").toString());
            mapExcelRow.put("UCM_RGT_ID", mapExcel.get("UCM_RGT_ID").toString());
            mapExcelRow.put("UCM_LVL_GBN", Constants.UCM_LVL_GBN);

            mapExcelRow.put("UCM_CTR_COD", String.format("%05d", s+1));
            mapExcelRow.put("UCM_1LV_COD", String.valueOf(s+1));
            mapExcelRow.put("UCM_1LV_NAM", strSheetName.trim());
            mapExcelRow.put("UCM_1LD_YN", Constants.USE_N);

            String strCtrKey_K = "";
            for (int r = 0; r < listSheetRows.size(); r++) {
                List listCells = (List) listSheetRows.get(r);
                if(listCells.get(3) != null && StringUtils.isNotEmpty(listCells.get(3).toString())
                        && listCells.get(4) != null && StringUtils.isNotEmpty(listCells.get(4).toString())) {
                    if (listCells.get(1) != null && StringUtils.isNotEmpty(listCells.get(1).toString())) {
                        mapExcelRow.put("UCM_2LV_COD", mapExcelRow.get("UCM_1LV_COD") + "." + lvl2Ctn);
                        mapExcelRow.put("UCM_2LV_NAM", listCells.get(1).toString());
                        mapExcelRow.put("UCM_2LV_DTL", "");
                        mapExcelRow.put("UCM_2LD_YN", Constants.USE_N);
                        lvl2Ctn++;
                        lvl3Ctn = 1;
                    }
                    if (StringUtils.isNotEmpty(listCells.get(3).toString())) {
                        mapExcelRow.put("UCM_3LV_COD", mapExcelRow.get("UCM_2LV_COD") + "." + lvl3Ctn);
                        mapExcelRow.put("UCM_3LV_NAM", listCells.get(3).toString());
                        mapExcelRow.put("UCM_3LV_DTL", "");
                        mapExcelRow.put("UCM_3LD_YN", Constants.USE_N);
                        //this.excelProcessing(mapExcelRow, (List) mapExcel.get("listService"));
                        String strCtrKey = fmCompsDAO.fm_comps003_getCtrKey(mapExcelRow);

                        if (StringUtils.isNotEmpty(strCtrKey)) {
                            mapExcelRow.put("ctrKey", strCtrKey);
                            strCtrKey_K = strCtrKey;
                            fmCompsDAO.fm_comps003_excelUpdate(mapExcelRow, (List) mapExcel.get("listService"));
                            excelNewDAO.fm_comps003_del_exp_data_infra_mp(strCtrKey_K);
                        } else {
                            mapExcelRow.remove("ctrKey");
                            fmCompsDAO.fm_comps003_excelInsert(mapExcelRow, (List) mapExcel.get("listService"));
                            strCtrKey_K = fmCompsDAO.fm_comps003_getCtrKey(mapExcelRow);
                        }

                        Map<String, String> mapExcel_K = new HashMap<String, String>();
                        mapExcel_K.put("CTR_KEY", strCtrKey_K);
                        mapExcel_K.put("LVL_COD", mapExcelRow.get("UCM_3LV_COD"));
                        mapExcel_K.put("REF_NO", listCells.get(2).toString());
                        mapExcel_K.put("GRADE", listCells.get(4).toString());
                        mapExcel_K.put("POINT", listCells.get(5).toString());
                        excelNewDAO.fm_comps003_ins_exp_data_infra_mp(mapExcel_K);
                        lvl3Ctn++;
                    }
                }
            }
        }
        return Constants.RETURN_SUCCESS;
    }

    public String excelNew_msit(Map<String, Object> mapExcel){
        //과기정통부 체크리스트
        List listSheetName = (List) mapExcel.get("sheetName");
        List listSheetData = (List) mapExcel.get("sheetData");

        int lvl2Ctn=1, lvl3Ctn = 1;
        for(int s=0; s<listSheetData.size(); s++){
            String strSheetName = (String) listSheetName.get(s);
            List listSheetRows = (List) listSheetData.get(s);
            Map<String, String> mapExcelRow = new HashMap<String, String>();

            mapExcelRow.put("UCM_BCY_COD", mapExcel.get("UCM_BCY_COD").toString());
            mapExcelRow.put("UCM_CTR_GBN", mapExcel.get("UCM_CTR_GBN").toString());
            mapExcelRow.put("UCM_RGT_ID", mapExcel.get("UCM_RGT_ID").toString());
            mapExcelRow.put("UCM_LVL_GBN", Constants.UCM_LVL_GBN);

            mapExcelRow.put("UCM_CTR_COD", String.format("%05d", s+1));
            mapExcelRow.put("UCM_1LV_COD", strSheetName.split("\\.")[0]);
            mapExcelRow.put("UCM_1LV_NAM", strSheetName.split("\\.")[1].trim());
            mapExcelRow.put("UCM_1LD_YN", Constants.USE_N);

            String strCtrKey_M = "";
            for (int r = 0; r < listSheetRows.size(); r++) {
                List listCells = (List) listSheetRows.get(r);
                if(listCells.get(4)!=null&&StringUtils.isNotEmpty(listCells.get(4).toString())) {
                    if(listCells.get(0)!=null&&StringUtils.isNotEmpty(listCells.get(0).toString())) {
                        mapExcelRow.put("UCM_2LV_COD", mapExcelRow.get("UCM_1LV_COD") +"."+ lvl2Ctn);
                        mapExcelRow.put("UCM_2LV_NAM", listCells.get(0).toString());
                        mapExcelRow.put("UCM_2LV_DTL", "");
                        mapExcelRow.put("UCM_2LD_YN", Constants.USE_N);
                        lvl2Ctn++;
                        lvl3Ctn = 1;
                    }

                    if(listCells.get(1)!=null&&StringUtils.isNotEmpty(listCells.get(1).toString())) {
                        mapExcelRow.put("UCM_3LV_COD", mapExcelRow.get("UCM_2LV_COD") +"."+ lvl3Ctn);
                        mapExcelRow.put("UCM_3LV_NAM", listCells.get(2).toString());
                        mapExcelRow.put("UCM_3LD_YN", Constants.USE_N);

                        List listTempCells = (List) listSheetRows.get(r+1);
                        mapExcelRow.put("UCM_3LV_DTL", listTempCells.get(8)==null||listTempCells.get(8).equals("null")?"":listTempCells.get(8).toString());

                        String strCtrKey = fmCompsDAO.fm_comps003_getCtrKey(mapExcelRow);
                        if(StringUtils.isNotEmpty(strCtrKey)) {
                            mapExcelRow.put("ctrKey", strCtrKey);
                            fmCompsDAO.fm_comps003_excelUpdate(mapExcelRow, (List) mapExcel.get("listService"));
                            strCtrKey_M = strCtrKey;
                        }else{
                            mapExcelRow.remove("ctrKey");
                            fmCompsDAO.fm_comps003_excelInsert(mapExcelRow, (List) mapExcel.get("listService"));
                            strCtrKey_M = fmCompsDAO.fm_comps003_getCtrKey(mapExcelRow);
                        }
                        excelNewDAO.fm_comps003_del_exp_data_msit(strCtrKey_M);
                        lvl3Ctn++;
                    }else{
                        //세부 문항
                        String strPoint = listCells.get(5).toString();
//System.out.println(listCells);
                        Map<String, String> mapExcel_M = new HashMap<String, String>();
                        mapExcel_M.put("CTR_KEY", strCtrKey_M);
                        mapExcel_M.put("LVL_COD", mapExcelRow.get("UCM_3LV_COD"));
                        mapExcel_M.put("SEQ", listCells.get(4).toString());
                        mapExcel_M.put("TITLE", listCells.get(7)==null?"":listCells.get(7).toString());
                        if( strPoint.contains("N/A") ){
                            mapExcel_M.put("POINT", "N/A");
                        }else{
                            mapExcel_M.put("POINT", strPoint);
                        }
                        excelNewDAO.fm_comps003_ins_exp_data_msit(mapExcel_M);
                    }
                }
            }
        }
        return Constants.RETURN_SUCCESS;
    }

    public String excelNew_infra_la(Map<String, Object> mapExcel){
        //기반시설 수준평가
        List listSheetName = (List) mapExcel.get("sheetName");
        List listSheetData = (List) mapExcel.get("sheetData");

        int lvl2Ctn=1, lvl3Ctn=1, expSeq=1;
        for(int s=0; s<listSheetData.size(); s++){
            String strSheetName = (String) listSheetName.get(s);
            List listSheetRows = (List) listSheetData.get(s);

            Map<String, String> mapExcelRow = new HashMap<String, String>();

            mapExcelRow.put("UCM_BCY_COD", mapExcel.get("UCM_BCY_COD").toString());
            mapExcelRow.put("UCM_CTR_GBN", mapExcel.get("UCM_CTR_GBN").toString());
            mapExcelRow.put("UCM_RGT_ID", mapExcel.get("UCM_RGT_ID").toString());
            mapExcelRow.put("UCM_LVL_GBN", Constants.UCM_LVL_GBN);

            String strCtrKey_G = "";
            for (int r = 0; r < listSheetRows.size(); r++) {
                List listCells = (List) listSheetRows.get(r);
                //System.out.println(listCells);
                if(listCells.get(0)!=null&&StringUtils.isNotEmpty(listCells.get(0).toString())) {
                    mapExcelRow.put("UCM_CTR_COD", String.format("%05d", s+1));
                    mapExcelRow.put("UCM_1LV_COD", listCells.get(0).toString().split("\\.")[0]);
                    mapExcelRow.put("UCM_1LV_NAM", listCells.get(0).toString().split("\\.")[1].trim());
                    mapExcelRow.put("UCM_1LD_YN", Constants.USE_N);
                    lvl2Ctn = 1;
                }

                if(listCells.get(1)!=null&&StringUtils.isNotEmpty(listCells.get(1).toString())) {
                    mapExcelRow.put("UCM_2LV_COD", mapExcelRow.get("UCM_1LV_COD") + "." + lvl2Ctn);
                    mapExcelRow.put("UCM_2LV_NAM", listCells.get(1).toString().replaceFirst("[\\d]+\\.[\\d]+","").trim());
                    mapExcelRow.put("UCM_2LV_DTL", listCells.get(2).toString());
                    mapExcelRow.put("UCM_2LD_YN", Constants.USE_N);
                    //System.out.println( mapExcelRow.get("UCM_2LV_NAM") );
                    lvl2Ctn++;
                    lvl3Ctn = 1;
                }

                if(listCells.get(3)!=null&&StringUtils.isNotEmpty(listCells.get(3).toString())) {
                    mapExcelRow.put("UCM_3LV_COD", mapExcelRow.get("UCM_2LV_COD") + "." + lvl3Ctn);
                    mapExcelRow.put("UCM_3LV_NAM", listCells.get(3).toString().replaceFirst("\\d+\\.\\s",""));
                    mapExcelRow.put("UCM_3LD_YN", Constants.USE_N);
                    mapExcelRow.put("UCM_3LV_DTL", "");
                    lvl3Ctn++;
                    expSeq=1;
                    String strCtrKey = fmCompsDAO.fm_comps003_getCtrKey(mapExcelRow);
                    //System.out.println("strCtrKey : "+strCtrKey);
                    if(StringUtils.isNotEmpty(strCtrKey)){
                        mapExcelRow.put("ctrKey", strCtrKey);
                        strCtrKey_G = strCtrKey;
                        fmCompsDAO.fm_comps003_excelUpdate(mapExcelRow, (List) mapExcel.get("listService"));
                        excelNewDAO.fm_comps003_del_exp_data_infra_la(strCtrKey_G);
                    }else{
                        //System.out.println(mapExcelRow);
                        mapExcelRow.remove("ctrKey");
                        fmCompsDAO.fm_comps003_excelInsert(mapExcelRow, (List) mapExcel.get("listService"));
                        strCtrKey_G = fmCompsDAO.fm_comps003_getCtrKey(mapExcelRow);
                    }
                }

                if(listCells.get(4)!=null&&StringUtils.isNotEmpty(listCells.get(4).toString())){
                    Map<String, String> mapExcel_G = new HashMap<String, String>();
                    mapExcel_G.put("CTR_KEY", strCtrKey_G);
                    mapExcel_G.put("LVL_COD", mapExcelRow.get("UCM_3LV_COD"));
                    mapExcel_G.put("SEQ", String.valueOf(expSeq));
                    mapExcel_G.put("TITLE", listCells.get(4).toString().replaceFirst("\\d+\\.\\s","").trim());
                    mapExcel_G.put("NOTE", listCells.get(5).toString());

                    excelNewDAO.fm_comps003_ins_exp_data_infra_la(mapExcel_G);
                    expSeq++;
                }
            }
        }
        return Constants.RETURN_SUCCESS;
    }

    public List<?> fm_comps003_3D_ExpData(String stndKind, Map mapKeyCodes) {
        List<?> listExpData = new ArrayList();
        switch(stndKind){
            case "infra_mp" :
                listExpData = excelNewDAO.fm_comps003_exp_data_infra_mp(mapKeyCodes);
                break;
            case "msit" :
                listExpData = excelNewDAO.fm_comps003_exp_data_msit(mapKeyCodes);
                break;
            case "infra_la" :
                listExpData = excelNewDAO.fm_comps003_exp_data_infra_la(mapKeyCodes);
                break;
            default :
                listExpData = null;
        }
        return listExpData;
    }

    public String fm_comps003_excelNew_D3(Map<String, Object> mapReqInfo, HttpServletRequest req) throws Exception {
        MultipartHttpServletRequest mReq = (MultipartHttpServletRequest) req;
        InputStream file = commonUtilService.decryptFileDAC(mReq.getFile("excelFile"));
        Workbook wb = WorkbookFactory.create(file);
        DataFormatter formatter = new DataFormatter();

        if(wb.getNumberOfSheets() < 1) return Constants.RETURN_FAIL;

        String[] service = (String[]) mapReqInfo.get("service");
        List listService = new ArrayList();
        for (int i=0; i < service.length-1; i++){
            listService.add(service[i]);
        }

        Map<String, String> mapExcelRow=new HashMap<String, String>();
        mapExcelRow.put("UCM_BCY_COD", String.valueOf(mapReqInfo.get("UCM_BCY_COD")));
        mapExcelRow.put("UCM_RGT_ID", String.valueOf(mapReqInfo.get("UCM_RGT_ID")));
        mapExcelRow.put("UCM_CTR_GBN", String.valueOf(mapReqInfo.get("UCM_CTR_GBN")));

        for(int s=0; s<wb.getNumberOfSheets(); s++){
            Sheet sheet=wb.getSheetAt(s);
            int lvl3Ctn=1;
            for(int r=5; r<=sheet.getLastRowNum(); r++){
                Row row=sheet.getRow(r);
                if(row!=null) {
                    String strCell1 = row.getCell(1) == null ? "" : formatter.formatCellValue(row.getCell(1));
                    String strCell2 = row.getCell(2) == null ? "" : formatter.formatCellValue(row.getCell(2));
                    String strCell3 = row.getCell(3) == null ? "" : formatter.formatCellValue(row.getCell(3));
                    String strCell4 = row.getCell(4) == null ? "" : formatter.formatCellValue(row.getCell(4));
                    String strCell5 = row.getCell(5) == null ? "" : formatter.formatCellValue(row.getCell(5));
                    String strCell6 = row.getCell(6) == null ? "" : formatter.formatCellValue(row.getCell(6));
                    String strCell7 = row.getCell(7) == null ? "" : formatter.formatCellValue(row.getCell(7));

                    if (strCell1 != "" || strCell2 != "" || strCell3 != "" || strCell4 != "" || strCell5 != "" || strCell6 != "" || strCell7 != "") {
                        if (StringUtils.isNotEmpty(strCell1) && strCell1 != "null") {
                            mapExcelRow.put("UCM_CTR_COD", String.format("%05d", s + 1));
                            mapExcelRow.put("UCM_1LV_COD", strCell1);
                            mapExcelRow.put("UCM_1LV_NAM", strCell2);
                            mapExcelRow.put("UCM_1LD_YN", Constants.USE_N);
                        }
                        if (StringUtils.isNotEmpty(strCell3) && strCell3 != "null") {
                            mapExcelRow.put("UCM_2LV_COD", strCell3);
                            mapExcelRow.put("UCM_2LV_NAM", strCell4);
                            mapExcelRow.put("UCM_2LV_DTL", strCell5);
                            mapExcelRow.put("UCM_2LD_YN", Constants.USE_N);
                            lvl3Ctn = 1;
                        }
                        mapExcelRow.put("UCM_3LV_COD", mapExcelRow.get("UCM_2LV_COD") + "." + lvl3Ctn);
                        mapExcelRow.put("UCM_3LV_NAM", strCell6);
                        mapExcelRow.put("UCM_3LV_DTL", strCell7);
                        mapExcelRow.put("UCM_3LD_YN", Constants.USE_N);
                        this.excelProcessing(mapExcelRow, listService);
                        lvl3Ctn++;
                    }
                }
            }
        }
        return Constants.RETURN_SUCCESS;
    }

    public String fm_inspt004_excelNew(Map<String, Object> mapReqInfo, HttpServletRequest req) throws Exception {
        MultipartHttpServletRequest mReq = (MultipartHttpServletRequest) req;
        InputStream file = commonUtilService.decryptFileDAC(mReq.getFile("excelFile"));
        Workbook wb = WorkbookFactory.create(file);
        DataFormatter formatter = new DataFormatter();

        if(wb.getNumberOfSheets() < 1) return Constants.RETURN_FAIL;
        Map<String, String> mapExcelRow=new HashMap<String, String>();
        mapExcelRow.put("UCM_BCY_COD", String.valueOf(mapReqInfo.get("UCM_BCY_COD")));
        mapExcelRow.put("UCM_CTR_GBN", String.valueOf(mapReqInfo.get("UCM_CTR_GBN")));
        mapExcelRow.put("UCM_SVC_COD", String.valueOf(mapReqInfo.get("UCM_SVC_COD")));

        Sheet sheet=wb.getSheetAt(0);
        for(int r=4; r<=sheet.getLastRowNum(); r++){
            Row row=sheet.getRow(r);
            if(row!=null) {
                String strLv3Cod = row.getCell(2) == null ? "" : formatter.formatCellValue(row.getCell(2));
                String strMsrDtl = row.getCell(5) == null ? "" : formatter.formatCellValue(row.getCell(5));
                if (strLv3Cod != "") {
                    mapExcelRow.put("UCM_3LV_COD", strLv3Cod);
                    mapExcelRow.put("UCM_MSR_DTL", strMsrDtl);
                    if(excelNewDAO.fm_inspt004_count(mapExcelRow)>0){
                        excelNewDAO.fm_inspt004_update(mapExcelRow);
                    }
                }
            }
        }
        return Constants.RETURN_SUCCESS;
    }

    public String getStndKind(String code){
        return excelNewDAO.getStndKind(code);
    }

    public List<?> getCompList(){
        return excelNewDAO.getCompList();
    }

    public List<?> getCompliance(String comp){
        return excelNewDAO.getCompliance(comp);
    }


    public XSSFCellStyle cellStyle(Map<String, Object> mapCellStyles){
        XSSFCellStyle cs = (XSSFCellStyle) mapCellStyles.get("_cs");
        Font font = (Font) mapCellStyles.get("_font");
        Map<String, Object> mapCellStyle = (Map<String, Object>) mapCellStyles.get("_custom");

        if(mapCellStyle.size()>0){
            Set set = mapCellStyle.keySet();
            Iterator iterator = set.iterator();
            while(iterator.hasNext()){
                String key = (String)iterator.next();
                switch(key){
                    case "font_size" :
                        font.setFontHeight((short)(20*(int) mapCellStyle.get(key)));
                        break;
                    case "bold" :
                        if((boolean) mapCellStyle.get(key)) font.setBoldweight(Font.BOLDWEIGHT_BOLD);
                        break;
                    case "align" :
                        if(mapCellStyle.get(key)=="center"){
                            cs.setAlignment(XSSFCellStyle.ALIGN_CENTER);
                        }else if(mapCellStyle.get(key)=="left"){
                            cs.setAlignment(XSSFCellStyle.ALIGN_LEFT);
                        }else if(mapCellStyle.get(key)=="right"){
                            cs.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
                        }
                        break;
                    case "bg_color" :
                        String Rgb[] = mapCellStyle.get(key).toString().split(",");
                        cs.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
                        cs.setFillForegroundColor(new XSSFColor(new java.awt.Color(Integer.valueOf(Rgb[0]),Integer.valueOf(Rgb[1]),Integer.valueOf(Rgb[2]))));
                        break;
                }
            }
        }

        return cs;
    }

    public String fmMwork006_excelNew_Save(Map<String, Object> mapReqInfo, HttpServletRequest req) throws Exception {
        MultipartHttpServletRequest mReq = (MultipartHttpServletRequest) req;
        InputStream file = commonUtilService.decryptFileDAC(mReq.getFile("excelFile"));
        Workbook wb = WorkbookFactory.create(file);

        Sheet sheetX;
        Row rowX;
        String strWrkKey, strWrkSta, strComSta, strWrkPrg, strWrkDtl;
        String strWtkID = mapReqInfo.get("UTW_WRK_ID").toString();
        String strPrdCod = mapReqInfo.get("UTW_PRD_COD").toString();

        Map<String, String> mapInfo = new HashMap<String, String>();

        for(int s=0; s<wb.getNumberOfSheets(); s++) {
            sheetX = wb.getSheetAt(s);
            for(int r=3; r<=sheetX.getLastRowNum(); r++) {
                rowX = sheetX.getRow(r);
                if (rowX != null) {
                    strWrkKey = rowX.getCell(0).getStringCellValue();
                    try {
                    	strWrkDtl = rowX.getCell(5).getStringCellValue();
					} catch (NullPointerException e) {
						strWrkDtl = "";
					}
                    
                    List<?> workList = fmMworkDAO.selectfmMwork006(strWrkKey);
                    EgovMap workInfo = (EgovMap) workList.get(0);
                    
                    switch(rowX.getCell(4).getStringCellValue()){
                        case "Y" :
                            strWrkPrg = "100";
                            strWrkSta = "90";
                            strComSta = "90";
                            break;
                        case "UP" :
                            strWrkPrg = "70";
                            strWrkSta = "10";
                            strComSta = "10";
                            break;
                        case "LP" :
                            strWrkPrg = "30";
                            strWrkSta = "10";
                            strComSta = "10";
                            break;
                        case "N/A" :
                            strWrkPrg = "-1";
                            strWrkSta = "92";
                            strComSta = "92";
                            break;
                        case "N" :
                            strWrkPrg = "0";
                            strWrkSta = "10";
                            strComSta = "0";
                            break;
                        default :
                            strWrkPrg = String.valueOf(workInfo.get("utwWrkPrg"));
                            strWrkSta = String.valueOf(workInfo.get("utwWrkSta"));
                            strComSta = String.valueOf(workInfo.get("utwComSta"));
                            break;
                    }
                    mapInfo.clear();
                    mapInfo.put("UTW_WRK_ID", strWtkID);
                    mapInfo.put("UTW_PRD_COD", strPrdCod);
                    mapInfo.put("UTW_WRK_KEY", strWrkKey);
                    mapInfo.put("UTW_WRK_STA", strWrkSta);
                    mapInfo.put("UTW_WRK_PRG", strWrkPrg);
                    mapInfo.put("UTW_COM_STA", strComSta);
                    mapInfo.put("UTW_WRK_DTL", strWrkDtl);
                    fmMworkDAO.fmMwork006_excel_Update(mapInfo);
                }
            }
        }
        return Constants.RETURN_SUCCESS;
    }
}
