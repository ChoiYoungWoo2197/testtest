/**
 ***********************************
 * @source ChartServiceImpl.java
 * @date 2015. 08. 24.
 * @project isms3
 * @description rMateChartH5 servicerImpl
 ***********************************
 */
package com.uwo.isms.service.impl;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONValue;
import org.springframework.stereotype.Service;

import com.uwo.isms.service.ChartService;
import com.uwo.isms.web.FMSetupController;

@Service("chartService")
public class ChartServiceImpl implements ChartService {
	
	Logger log = LogManager.getLogger(FMSetupController.class);

	@Override
	public String state002_chartJson(List<?> jsonList, String cateField, String sMode) {
		
		List<LinkedHashMap<String,Object>> listJson = new LinkedList<LinkedHashMap<String,Object>>();
		
        for(int i=0; i<jsonList.size(); i++){
        	Map<String, Object> dto = (Map<String, Object>)jsonList.get(i);
			LinkedHashMap<String,Object> jsonObj = new LinkedHashMap<String,Object>();
			if(!dto.get(cateField.toLowerCase()).toString().equals("ALL") && sMode.equals("") || !dto.get(cateField.toLowerCase()).toString().equals("전체") && !sMode.equals("")){
				jsonObj.put(cateField, dto.get(cateField.toLowerCase()));
				jsonObj.put("COMP", dto.get("comp"));
				jsonObj.put("DELAY", dto.get("delay"));
				jsonObj.put("NOWORK", dto.get("nowork"));
				//jsonObj.put("TODAY", dto.get("today"));
				
				listJson.add(jsonObj);
			}
		}
        
        return JSONValue.toJSONString(listJson).replaceAll(",\"", ", \"");
	}

    @Override
	public String state002_chart(String sTitle, String mTitle, String cateField) {
		
		String chartGrid = "<rMateChart backgroundColor=\"0xFFFFFF\" cornerRadius=\"12\" borderStyle=\"none\">"
				 + "	<Options>"
				 + "		<Caption text=\""+sTitle+" - "+mTitle+"\"/>"
				 + "		<SubCaption text=\"건수\" textAlign=\"right\" />"
				 + "		<Legend defaultMouseOverAction=\"false\"/>"
				 + "	</Options>"
				 + "	<NumberFormatter id=\"numfmt\" useThousandsSeparator=\"true\"/>"
				 + "	<Column2DChart showDataTips=\"true\" dataTipJsFunction=\"dataTipFunc002\">"
				 + "		<horizontalAxis>"
				 + "			<CategoryLinearAxis id=\"hAxis\" categoryField=\""+cateField+"\"/>"
				 + "		</horizontalAxis>"
				 + "		<verticalAxis>"
				 + "			<LinearAxis displayName=\"건\" formatter=\"{numfmt}\"/>"
				 + "		</verticalAxis>"
				 + "		<horizontalAxisRenderers>"
				 + "			<ScrollableAxisRenderer axis=\"{hAxis}\" visibleItemSize=\"10\"/>"
				 + "		</horizontalAxisRenderers>"	
				 + "		<series>"
				 + "			<Column2DSet type=\"stacked\" showTotalLabel=\"\" totalLabelJsFunction=\"totalFunc\">"
				 + "				<series>"
				 + "					<Column2DSeries labelPosition=\"inside\" yField=\"COMP\" displayName=\"완료\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<fill>"
				 + "							<SolidColor color=\"0xABF200\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "					</Column2DSeries>"
				 + "					<Column2DSeries labelPosition=\"inside\" yField=\"DELAY\" displayName=\"지연\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<fill>"
				 + "							<SolidColor color=\"0xFF9436\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "					</Column2DSeries>"
				 + "					<Column2DSeries labelPosition=\"inside\" yField=\"NOWORK\" displayName=\"미진행\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<fill>"
				 + "							<SolidColor color=\"0x5AAEFF\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "					</Column2DSeries>"
//				 + "					<Column2DSeries labelPosition=\"inside\" yField=\"TODAY\" displayName=\"금일완료\"  showValueLabels=\"[]\" color=\"#ffffff\">"
//				 + "						<fill>"
//				 + "							<SolidColor color=\"0xFFE400\" alpha=\"1\"/>"
//				 + "						</fill>"
//				 + "						<showDataEffect>"
//				 + "							<SeriesInterpolate/>"
//				 + "						</showDataEffect>"
//				 + "					</Column2DSeries>"
				 + "				</series>"
				 + "			</Column2DSet>"
				 + "		</series>"
				 + "	</Column2DChart>"
				 + "</rMateChart>";
		
		return chartGrid;
	}
	
    @Override
	public String state002_chartBar(String sTitle, String mTitle, String cateField) {
		
		String chartGrid = "<rMateChart backgroundColor=\"0xFFFFFF\" cornerRadius=\"12\" borderStyle=\"none\">"
				 + "	<Options>"
				 + "		<Caption text=\""+sTitle+" - "+mTitle+"\"/>"
				 + "		<SubCaption text=\"건수\" textAlign=\"right\" />"
				 + "		<Legend defaultMouseOverAction=\"false\"/>"
				 + "	</Options>"
				 + "	<NumberFormatter id=\"numfmt\" useThousandsSeparator=\"true\"/>"
				 + "	<Bar2DChart showDataTips=\"true\" dataTipJsFunction=\"dataTipFunc002_B\">"
				 + "		<horizontalAxis>"
				 + "			<LinearAxis displayName=\"건\" formatter=\"{numfmt}\"/>"
				 + "		</horizontalAxis>"
				 + "		<verticalAxis>"
				 + "			<CategoryLinearAxis id=\"hAxis\" categoryField=\""+cateField+"\" ticksBetweenLabels=\"true\" direction=\"inverted\"/>"
				 + "		</verticalAxis>"
				 + "		<verticalAxisRenderers>"
				 + "			<ScrollableAxisRenderer axis=\"{hAxis}\" visibleItemSize=\"10\"/>"
				 + "		</verticalAxisRenderers>"	
				 + "		<series>"
				 + "			<Bar2DSet type=\"stacked\" showTotalLabel=\"\" totalLabelJsFunction=\"totalFunc\">"
				 + "				<series>"
				 + "					<Bar2DSeries labelPosition=\"inside\" xField=\"COMP\" displayName=\"완료\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<fill>"
				 + "							<SolidColor color=\"0xABF200\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "					</Bar2DSeries>"
				 + "					<Bar2DSeries labelPosition=\"inside\" xField=\"DELAY\" displayName=\"지연\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<fill>"
				 + "							<SolidColor color=\"0xFF9436\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "					</Bar2DSeries>"
				 + "					<Bar2DSeries labelPosition=\"inside\" xField=\"NOWORK\" displayName=\"미진행\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<fill>"
				 + "							<SolidColor color=\"0x5AAEFF\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "					</Bar2DSeries>"
//				 + "					<Bar2DSeries labelPosition=\"inside\" xField=\"TODAY\" displayName=\"금일완료\"  showValueLabels=\"[]\" color=\"#ffffff\">"
//				 + "						<fill>"
//				 + "							<SolidColor color=\"0xFFE400\" alpha=\"1\"/>"
//				 + "						</fill>"
//				 + "						<showDataEffect>"
//				 + "							<SeriesInterpolate/>"
//				 + "						</showDataEffect>"
//				 + "					</Bar2DSeries>"
				 + "				</series>"
				 + "			</Bar2DSet>"
				 + "		</series>"
				 + "	</Bar2DChart>"
				 + "</rMateChart>";
		
		return chartGrid;
	}
	
	@Override
	public String state003_chartJson(List<?> jsonList, List<?> comList, List<?> serviceList) {
		
		List<LinkedHashMap<String,Object>> listJson = new LinkedList<LinkedHashMap<String,Object>>();
		
		for(int k=0; k<comList.size(); k++){
			Map<String, Object> dtoCom = (Map<String, Object>)comList.get(k);
			LinkedHashMap<String,Object> jsonObj = new LinkedHashMap<String,Object>();
			jsonObj.put("COMPLIANCE", dtoCom.get("ucmCtrCod"));
			for(int z=0; z<serviceList.size(); z++){
				Map<String, Object> dtoSev = (Map<String, Object>)serviceList.get(z);
				
				jsonObj.put(dtoSev.get("name").toString(), "0");
				for(int i=0; i<jsonList.size(); i++){
					Map<String, Object> dto = (Map<String, Object>)jsonList.get(i);
					if(dto.get("ucmCtrCod").toString().equals(dtoCom.get("ucmCtrCod").toString())){
						jsonObj.put(dto.get("svcNam").toString(), dto.get("totP"));
					}
				}
			}
			
			listJson.add(jsonObj);
		}
		
        return JSONValue.toJSONString(listJson).replaceAll(",\"", ", \"");
	}
	
	@Override
	public String state003_chart(String sTitle, String mTitle, String cateField, List<?> serviceList, String service) {
		
		String[] sColor = {"0xABF200","0xFF9436","0xFFE400","0xCC3D3D","0x5AAEFF"};

		String chartGrid = "<rMateChart backgroundColor=\"0xFFFFFF\" cornerRadius=\"12\" borderStyle=\"none\">"
				 + "	<Options>"
				 + "		<Caption text=\""+sTitle+" - "+mTitle+"\"/>"
				 + "		<SubCaption text=\"달성률(%)\" textAlign=\"right\" />"				 
				 + "		<Legend defaultMouseOverAction=\"true\"/>"
				 + "	</Options>"
				 + "	<RadarChart id=\"chart1\" isSeriesOnAxis=\"true\" type=\"polygon\" paddingTop=\"20\" paddingBottom=\"20\" showDataTips=\"true\">"
				 + "		<radialAxis>"
				 + "			<LinearAxis id=\"rAxis\"/>"
				 + "		</radialAxis>"
				 + "		<angularAxis>"
				 + "			<CategoryAxis id=\"aAxis\" categoryField=\"" +cateField+ "\" displayName=\"달성률(%)\"/>"
				 + "		</angularAxis>"
				 + "		<radialAxisRenderers>"
				 + "			<Axis2DRenderer axis=\"{rAxis}\" horizontal=\"true\" visible=\"true\" tickPlacement=\"outside\" tickLength=\"4\">"
				 + "				<axisStroke>"
				 + "					<Stroke color=\"#555555\" weight=\"1\"/>"
				 + "				</axisStroke>"
				 + "			</Axis2DRenderer>"
				 + "		</radialAxisRenderers>"
				 + "		<angularAxisRenderers>"
				 + "			<AngularAxisRenderer axis=\"{aAxis}\"/>"
				 + "		</angularAxisRenderers>"
				 + "		<series>";
		for(int z=0; z<serviceList.size(); z++){
			Map<String, Object> dtoSev = (Map<String, Object>)serviceList.get(z);
			if(service.equals("") || (!service.equals("") && service.equals(dtoSev.get("code").toString())))
		chartGrid += "			<RadarSeries field=\"" +dtoSev.get("name").toString()+ "\" displayName=\"" +dtoSev.get("name").toString()+ "\">";
				if(z == 3){
		chartGrid += "				<lineStroke>"
				 + "					<Stroke color=\"" + sColor[z] + "\" weight=\"1\"/>"
				 + "				</lineStroke>"
				 + "				<fill>"
				 + "					<SolidColor color=\"" + sColor[z] + "\"/>"
				 + "				</fill>";
				}
		chartGrid += "				<showDataEffect>"
				 + "					<SeriesInterpolate/>"
				 + "				</showDataEffect>"
				 + "			</RadarSeries>";
		}
		chartGrid += "		</series>"
				 + "	</RadarChart>"
				 + "</rMateChart>";
		
		return chartGrid;
	}
	
	@Override
	public String state004_chartJson(List<?> jsonList, String cateField, String sMode) {
		
		List<LinkedHashMap<String,Object>> listJson = new LinkedList<LinkedHashMap<String,Object>>();
		
        for(int i=0; i<jsonList.size(); i++){
        	Map<String, Object> dto = (Map<String, Object>)jsonList.get(i);
			LinkedHashMap<String,Object> jsonObj = new LinkedHashMap<String,Object>();
			if(!dto.get(cateField.toLowerCase()).toString().equals("ALL") || (i != jsonList.size()-1)){
				jsonObj.put(cateField, dto.get(cateField.toLowerCase()));
				jsonObj.put("ISMS", dto.get("ismsP"));
				jsonObj.put("SKPNG", dto.get("skpngP"));
				jsonObj.put("PIMS", dto.get("pimsP"));
				
				listJson.add(jsonObj);
			}
		}

        return JSONValue.toJSONString(listJson).replaceAll(",\"", ", \"");
	}
	
	@Override
	public String state004_chart(String sTitle, String mTitle, String cateField) {
		
		String chartGrid = "<rMateChart backgroundColor=\"0xFFFFFF\" cornerRadius=\"12\" borderStyle=\"none\">"
				 + "	<Options>"
				 + "		<Caption text=\""+sTitle+" - "+mTitle+"\"/>"
				 + "		<SubCaption text=\"완료(%)\" textAlign=\"right\" />"
				 + "		<Legend defaultMouseOverAction=\"false\"/>"
				 + "	</Options>"
				 + "	<NumberFormatter id=\"numfmt\" useThousandsSeparator=\"true\"/>"
				 + "	<Column2DChart showDataTips=\"true\" dataTipJsFunction=\"dataTipFunc004\">"
				 + "		<horizontalAxis>"
				 + "			<CategoryLinearAxis id=\"hAxis\" categoryField=\""+cateField+"\"/>"
				 + "		</horizontalAxis>"
				 + "		<verticalAxis>"
				 + "			<LinearAxis displayName=\"%\" interval=\"100\" formatter=\"{numfmt}\"/>"
				 + "		</verticalAxis>"
				 + "		<horizontalAxisRenderers>"
				 + "			<ScrollableAxisRenderer axis=\"{hAxis}\" visibleItemSize=\"10\"/>"
				 + "		</horizontalAxisRenderers>"					 
				 + "				<series>"
				 + "					<Column2DSeries labelPosition=\"inside\" yField=\"ISMS\" displayName=\"ISMS\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<fill>"
				 + "							<SolidColor color=\"0xABF200\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "					</Column2DSeries>"
				 + "					<Column2DSeries labelPosition=\"inside\" yField=\"SKPNG\" displayName=\"SKPNG\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<fill>"
				 + "							<SolidColor color=\"0xFF9436\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "					</Column2DSeries>"
				 + "					<Column2DSeries labelPosition=\"inside\" yField=\"PIMS\" displayName=\"PIMS\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<fill>"
				 + "							<SolidColor color=\"0x5AAEFF\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "					</Column2DSeries>"
				 + "				</series>"
				 + "	</Column2DChart>"
				 + "</rMateChart>";
		
		return chartGrid;
	}
	
	@Override
	public String state004_chartBar(String sTitle, String mTitle, String cateField) {
		
		String chartGrid = "<rMateChart backgroundColor=\"0xFFFFFF\" cornerRadius=\"12\" borderStyle=\"none\">"
				 + "	<Options>"
				 + "		<Caption text=\""+sTitle+" - "+mTitle+"\"/>"
				 + "		<SubCaption text=\"완료(%)\" textAlign=\"right\" />"
				 + "		<Legend defaultMouseOverAction=\"false\"/>"
				 + "	</Options>"
				 + "	<NumberFormatter id=\"numfmt\" useThousandsSeparator=\"true\"/>"
				 + "	<Bar2DChart showDataTips=\"true\" dataTipJsFunction=\"dataTipFunc004_B\">"
				 + "		<horizontalAxis>"
				 + "			<LinearAxis displayName=\"%\" interval=\"100\" formatter=\"{numfmt}\"/>"
				 + "		</horizontalAxis>"
				 + "		<verticalAxis>"
				 + "			<CategoryLinearAxis id=\"hAxis\" categoryField=\""+cateField+"\" ticksBetweenLabels=\"true\" direction=\"inverted\"/>"
				 + "		</verticalAxis>"
				 + "		<verticalAxisRenderers>"
				 + "			<ScrollableAxisRenderer axis=\"{hAxis}\" visibleItemSize=\"5\"/>"
				 + "		</verticalAxisRenderers>"				 
				 + "				<series>"
				 + "					<Bar2DSeries labelPosition=\"inside\" xField=\"ISMS\" displayName=\"ISMS\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<fill>"
				 + "							<SolidColor color=\"0xABF200\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "					</Bar2DSeries>"
				 + "					<Bar2DSeries labelPosition=\"inside\" xField=\"SKPNG\" displayName=\"SKPNG\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<fill>"
				 + "							<SolidColor color=\"0xFF9436\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "					</Bar2DSeries>"
				 + "					<Bar2DSeries labelPosition=\"inside\" xField=\"PIMS\" displayName=\"PIMS\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<fill>"
				 + "							<SolidColor color=\"0x5AAEFF\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "					</Bar2DSeries>"
				 + "				</series>"
				 + "	</Bar2DChart>"
				 + "</rMateChart>";
		
		return chartGrid;
	}
	
	@Override
	public String state005_chartJson(List<?> jsonList, String cateField, String sMode) {
		
		List<LinkedHashMap<String,String>> listJson = new LinkedList<LinkedHashMap<String,String>>();
		
        for(int i=0; i<jsonList.size(); i++){
        	Map<String, String> dto = (Map<String, String>)jsonList.get(i);
			LinkedHashMap<String,String> jsonObj = new LinkedHashMap<String,String>();
			String sTemp = "";
			if(!dto.get(cateField.toLowerCase()).toString().equals("all") || (i != jsonList.size()-1)){
				jsonObj.put(cateField, dto.get(cateField.toLowerCase()));
				
				sTemp = dto.get("c06");
				jsonObj.put("c06_i", sTemp.substring(sTemp.indexOf(":")+1, sTemp.indexOf("(")));
				sTemp = sTemp.substring(sTemp.indexOf(")"), sTemp.length());
				jsonObj.put("c06_s", sTemp.substring(sTemp.indexOf(":")+1, sTemp.indexOf("(")));
				sTemp = sTemp.substring(sTemp.indexOf(")"), sTemp.length());
				jsonObj.put("c06_c", sTemp.substring(sTemp.indexOf(":")+1, sTemp.indexOf("(")));
				
				sTemp = dto.get("c07");
				jsonObj.put("c07_i", sTemp.substring(sTemp.indexOf(":")+1, sTemp.indexOf("(")));
				sTemp = sTemp.substring(sTemp.indexOf(")"), sTemp.length());
				jsonObj.put("c07_s", sTemp.substring(sTemp.indexOf(":")+1, sTemp.indexOf("(")));
				sTemp = sTemp.substring(sTemp.indexOf(")"), sTemp.length());
				jsonObj.put("c07_c", sTemp.substring(sTemp.indexOf(":")+1, sTemp.indexOf("(")));
				
				sTemp = dto.get("c08");
				jsonObj.put("c08_i", sTemp.substring(sTemp.indexOf(":")+1, sTemp.indexOf("(")));
				sTemp = sTemp.substring(sTemp.indexOf(")"), sTemp.length());
				jsonObj.put("c08_s", sTemp.substring(sTemp.indexOf(":")+1, sTemp.indexOf("(")));
				sTemp = sTemp.substring(sTemp.indexOf(")"), sTemp.length());
				jsonObj.put("c08_c", sTemp.substring(sTemp.indexOf(":")+1, sTemp.indexOf("(")));
				
				sTemp = dto.get("c09");
				jsonObj.put("c09_i", sTemp.substring(sTemp.indexOf(":")+1, sTemp.indexOf("(")));
				sTemp = sTemp.substring(sTemp.indexOf(")"), sTemp.length());
				jsonObj.put("c09_s", sTemp.substring(sTemp.indexOf(":")+1, sTemp.indexOf("(")));
				sTemp = sTemp.substring(sTemp.indexOf(")"), sTemp.length());
				jsonObj.put("c09_c", sTemp.substring(sTemp.indexOf(":")+1, sTemp.indexOf("(")));
				
				sTemp = dto.get("c10");
				jsonObj.put("c10_i", sTemp.substring(sTemp.indexOf(":")+1, sTemp.indexOf("(")));
				sTemp = sTemp.substring(sTemp.indexOf(")"), sTemp.length());
				jsonObj.put("c10_s", sTemp.substring(sTemp.indexOf(":")+1, sTemp.indexOf("(")));
				sTemp = sTemp.substring(sTemp.indexOf(")"), sTemp.length());
				jsonObj.put("c10_c", sTemp.substring(sTemp.indexOf(":")+1, sTemp.indexOf("(")));
				
				sTemp = dto.get("c11");
				jsonObj.put("c11_i", sTemp.substring(sTemp.indexOf(":")+1, sTemp.indexOf("(")));
				sTemp = sTemp.substring(sTemp.indexOf(")"), sTemp.length());
				jsonObj.put("c11_s", sTemp.substring(sTemp.indexOf(":")+1, sTemp.indexOf("(")));
				sTemp = sTemp.substring(sTemp.indexOf(")"), sTemp.length());
				jsonObj.put("c11_c", sTemp.substring(sTemp.indexOf(":")+1, sTemp.indexOf("(")));
				
				sTemp = dto.get("c12");
				jsonObj.put("c12_i", sTemp.substring(sTemp.indexOf(":")+1, sTemp.indexOf("(")));
				sTemp = sTemp.substring(sTemp.indexOf(")"), sTemp.length());
				jsonObj.put("c12_s", sTemp.substring(sTemp.indexOf(":")+1, sTemp.indexOf("(")));
				sTemp = sTemp.substring(sTemp.indexOf(")"), sTemp.length());
				jsonObj.put("c12_c", sTemp.substring(sTemp.indexOf(":")+1, sTemp.indexOf("(")));
				
				sTemp = dto.get("c13");
				jsonObj.put("c13_i", sTemp.substring(sTemp.indexOf(":")+1, sTemp.indexOf("(")));
				sTemp = sTemp.substring(sTemp.indexOf(")"), sTemp.length());
				jsonObj.put("c13_s", sTemp.substring(sTemp.indexOf(":")+1, sTemp.indexOf("(")));
				sTemp = sTemp.substring(sTemp.indexOf(")"), sTemp.length());
				jsonObj.put("c13_c", sTemp.substring(sTemp.indexOf(":")+1, sTemp.indexOf("(")));
				
				listJson.add(jsonObj);
			}
		}

        return JSONValue.toJSONString(listJson).replaceAll(",\"", ", \"");
	}
	
	@Override
	public String state005_chart(String sTitle, String mTitle, String cateField) {
		
		String chartGrid = "<rMateChart backgroundColor=\"0xFFFFFF\" cornerRadius=\"12\" borderStyle=\"none\">"
				 + "	<Options>"
				 + "		<Caption text=\""+sTitle+" - "+mTitle+"\"/>"
				 + "		<SubCaption text=\"건수\" textAlign=\"right\" />"
				 + "		<Legend position=\"right\" defaultMouseOverAction=\"false\" width=\"200\" height=\"300\" verticalScrollPolicy=\"on\" labelPlacement=\"right\" direction=\"vertical\" />"
				 + "	</Options>"
				 + "	<NumberFormatter id=\"numfmt\" useThousandsSeparator=\"true\"/>"
				 + "	<Column2DChart showDataTips=\"true\" dataTipJsFunction=\"dataTipFunc005\">"
				 + "		<horizontalAxis>"
				 + "			<CategoryLinearAxis id=\"hAxis\" categoryField=\""+cateField+"\"/>"
				 + "		</horizontalAxis>"
				 + "		<verticalAxis>"
				 + "			<LinearAxis displayName=\"건\" interval=\"\" formatter=\"{numfmt}\"/>"
				 + "		</verticalAxis>"
				 + "		<horizontalAxisRenderers>"
				 + "			<ScrollableAxisRenderer axis=\"{hAxis}\" visibleItemSize=\"2\"/>"
				 + "		</horizontalAxisRenderers>"					 
				 + "		<series>"
				 + "			<Column2DSet type=\"stacked\" showTotalLabel=\"\" totalLabelJsFunction=\"totalFunc\" labelYOffset=\"0\">"
				 + "				<series>"
				 + "					<Column2DSeries labelPosition=\"inside\" yField=\"c06_i\" displayName=\"전자정보-미흡\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<fill>"
				 + "							<SolidColor color=\"0xFF9436\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "					</Column2DSeries>"
				 + "					<Column2DSeries labelPosition=\"inside\" yField=\"c06_s\" displayName=\"전자정보-부분미흡\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<fill>"
				 + "							<SolidColor color=\"0xFFE400\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "					</Column2DSeries>"
				 + "					<Column2DSeries labelPosition=\"inside\" yField=\"c06_c\" displayName=\"전자정보-양호\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<fill>"
				 + "							<SolidColor color=\"0xABF200\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "					</Column2DSeries>"
				 + "				</series>"
				 + "			</Column2DSet>"
				 + "			<Column2DSet type=\"stacked\" showTotalLabel=\"\" totalLabelJsFunction=\"totalFunc\" labelYOffset=\"0\">"
				 + "				<series>"
				 + "					<Column2DSeries labelPosition=\"inside\" yField=\"c07_i\" displayName=\"서버-미흡\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<fill>"
				 + "							<SolidColor color=\"0xFF9436\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "					</Column2DSeries>"
				 + "					<Column2DSeries labelPosition=\"inside\" yField=\"c07_s\" displayName=\"서버-부분미흡\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<fill>"
				 + "							<SolidColor color=\"0xFFE400\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "					</Column2DSeries>"
				 + "					<Column2DSeries labelPosition=\"inside\" yField=\"c07_c\" displayName=\"서버-양호\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<fill>"
				 + "							<SolidColor color=\"0xABF200\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "					</Column2DSeries>"
				 + "				</series>"
				 + "			</Column2DSet>"
				 + "			<Column2DSet type=\"stacked\" showTotalLabel=\"\" totalLabelJsFunction=\"totalFunc\" labelYOffset=\"0\">"
				 + "				<series>"
				 + "					<Column2DSeries labelPosition=\"inside\" yField=\"c08_i\" displayName=\"네트워크-미흡\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<fill>"
				 + "							<SolidColor color=\"0xFF9436\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "					</Column2DSeries>"
				 + "					<Column2DSeries labelPosition=\"inside\" yField=\"c08_s\" displayName=\"네트워크-부분미흡\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<fill>"
				 + "							<SolidColor color=\"0xFFE400\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "					</Column2DSeries>"
				 + "					<Column2DSeries labelPosition=\"inside\" yField=\"c08_c\" displayName=\"네트워크-양호\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<fill>"
				 + "							<SolidColor color=\"0xABF200\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "					</Column2DSeries>"
				 + "				</series>"
				 + "			</Column2DSet>"
				 + "			<Column2DSet type=\"stacked\" showTotalLabel=\"\" totalLabelJsFunction=\"totalFunc\" labelYOffset=\"0\">"
				 + "				<series>"
				 + "					<Column2DSeries labelPosition=\"inside\" yField=\"c09_i\" displayName=\"정보보호시스템-미흡\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<fill>"
				 + "							<SolidColor color=\"0xFF9436\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "					</Column2DSeries>"
				 + "					<Column2DSeries labelPosition=\"inside\" yField=\"c09_s\" displayName=\"정보보호시스템-부분미흡\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<fill>"
				 + "							<SolidColor color=\"0xFFE400\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "					</Column2DSeries>"
				 + "					<Column2DSeries labelPosition=\"inside\" yField=\"c09_c\" displayName=\"정보보호시스템-양호\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<fill>"
				 + "							<SolidColor color=\"0xABF200\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "					</Column2DSeries>"
				 + "				</series>"
				 + "			</Column2DSet>"
				 + "			<Column2DSet type=\"stacked\" showTotalLabel=\"\" totalLabelJsFunction=\"totalFunc\" labelYOffset=\"0\">"
				 + "				<series>"
				 + "					<Column2DSeries labelPosition=\"inside\" yField=\"c10_i\" displayName=\"문서-미흡\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<fill>"
				 + "							<SolidColor color=\"0xFF9436\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "					</Column2DSeries>"
				 + "					<Column2DSeries labelPosition=\"inside\" yField=\"c10_s\" displayName=\"문서-부분미흡\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<fill>"
				 + "							<SolidColor color=\"0xFFE400\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "					</Column2DSeries>"
				 + "					<Column2DSeries labelPosition=\"inside\" yField=\"c10_c\" displayName=\"문서-양호\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<fill>"
				 + "							<SolidColor color=\"0xABF200\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "					</Column2DSeries>"
				 + "				</series>"
				 + "			</Column2DSet>"
				 + "			<Column2DSet type=\"stacked\" showTotalLabel=\"\" totalLabelJsFunction=\"totalFunc\" labelYOffset=\"0\">"
				 + "				<series>"
				 + "					<Column2DSeries labelPosition=\"inside\" yField=\"c11_i\" displayName=\"소프트웨어-미흡\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<fill>"
				 + "							<SolidColor color=\"0xFF9436\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "					</Column2DSeries>"
				 + "					<Column2DSeries labelPosition=\"inside\" yField=\"c11_s\" displayName=\"소프트웨어-부분미흡\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<fill>"
				 + "							<SolidColor color=\"0xFFE400\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "					</Column2DSeries>"
				 + "					<Column2DSeries labelPosition=\"inside\" yField=\"c11_c\" displayName=\"소프트웨어-양호\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<fill>"
				 + "							<SolidColor color=\"0xABF200\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "					</Column2DSeries>"
				 + "				</series>"
				 + "			</Column2DSet>"
				 + "			<Column2DSet type=\"stacked\" showTotalLabel=\"\" totalLabelJsFunction=\"totalFunc\" labelYOffset=\"0\">"
				 + "				<series>"
				 + "					<Column2DSeries labelPosition=\"inside\" yField=\"c12_i\" displayName=\"단말기-미흡\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<fill>"
				 + "							<SolidColor color=\"0xFF9436\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "					</Column2DSeries>"
				 + "					<Column2DSeries labelPosition=\"inside\" yField=\"c12_s\" displayName=\"단말기-부분미흡\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<fill>"
				 + "							<SolidColor color=\"0xFFE400\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "					</Column2DSeries>"
				 + "					<Column2DSeries labelPosition=\"inside\" yField=\"c12_c\" displayName=\"단말기-양호\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<fill>"
				 + "							<SolidColor color=\"0xABF200\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "					</Column2DSeries>"
				 + "				</series>"
				 + "			</Column2DSet>"
				 + "			<Column2DSet type=\"stacked\" showTotalLabel=\"\" totalLabelJsFunction=\"totalFunc\" labelYOffset=\"0\">"
				 + "				<series>"
				 + "					<Column2DSeries labelPosition=\"inside\" yField=\"c13_i\" displayName=\"물리적자산-미흡\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<fill>"
				 + "							<SolidColor color=\"0xFF9436\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "					</Column2DSeries>"
				 + "					<Column2DSeries labelPosition=\"inside\" yField=\"c13_s\" displayName=\"물리적자산-부분미흡\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<fill>"
				 + "							<SolidColor color=\"0xFFE400\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "					</Column2DSeries>"
				 + "					<Column2DSeries labelPosition=\"inside\" yField=\"c13_c\" displayName=\"물리적자산-양호\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<fill>"
				 + "							<SolidColor color=\"0xABF200\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "					</Column2DSeries>"
				 + "				</series>"
				 + "			</Column2DSet>"
				 + "		</series>"
				 + "	</Column2DChart>"
				 + "</rMateChart>";
		
		return chartGrid;
	}
	
	@Override
	public String state006_chartJson(List<?> jsonList, String cateField, String sMode) {
		
		List<LinkedHashMap<String,Object>> listJson = new LinkedList<LinkedHashMap<String,Object>>();
		
        for(int i=0; i<jsonList.size(); i++){
        	Map<String, Object> dto = (Map<String, Object>)jsonList.get(i);
			LinkedHashMap<String,Object> jsonObj = new LinkedHashMap<String,Object>();
			if(!dto.get(cateField.toLowerCase()).toString().equals("all") || (i != jsonList.size()-1)){
				jsonObj.put(cateField, dto.get(cateField.toLowerCase()));
				jsonObj.put("C06", dto.get("c06"));
				jsonObj.put("C07", dto.get("c07"));
				jsonObj.put("C08", dto.get("c08"));
				jsonObj.put("C09", dto.get("c09"));
				jsonObj.put("C10", dto.get("c10"));
				jsonObj.put("C11", dto.get("c11"));
				jsonObj.put("C12", dto.get("c12"));
				jsonObj.put("C13", dto.get("c13"));
				
				listJson.add(jsonObj);
			}
		}

        return JSONValue.toJSONString(listJson).replaceAll(",\"", ", \"");
	}
	
	@Override
	public String state006_chart(String sTitle, String mTitle, String cateField) {
		
		String chartGrid = "<rMateChart backgroundColor=\"0xFFFFFF\" cornerRadius=\"12\" borderStyle=\"none\">"
				 + "	<Options>"
				 + "		<Caption text=\""+sTitle+" - "+mTitle+"\"/>"
				 + "		<SubCaption text=\"건수\" textAlign=\"right\" />"
				 + "		<Legend defaultMouseOverAction=\"false\"/>"
				 + "	</Options>"
				 + "	<NumberFormatter id=\"numfmt\" useThousandsSeparator=\"true\"/>"
				 + "	<Column2DChart showDataTips=\"true\" dataTipJsFunction=\"dataTipFunc006\">"
				 + "		<horizontalAxis>"
				 + "			<CategoryLinearAxis id=\"hAxis\" categoryField=\""+cateField+"\"/>"
				 + "		</horizontalAxis>"
				 + "		<verticalAxis>"
				 + "			<LinearAxis displayName=\"건\" interval=\"\" formatter=\"{numfmt}\"/>"
				 + "		</verticalAxis>"
				 + "		<horizontalAxisRenderers>"
				 + "			<ScrollableAxisRenderer axis=\"{hAxis}\" visibleItemSize=\"10\"/>"
				 + "		</horizontalAxisRenderers>"					 
				 + "		<series>"
				 + "			<Column2DSet type=\"stacked\" showTotalLabel=\"\" totalLabelJsFunction=\"totalFunc\" labelYOffset=\"0\">"
				 + "				<series>"
				 + "					<Column2DSeries labelPosition=\"inside\" yField=\"C06\" displayName=\"전자정보\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "						<fill>"
				 + "							<SolidColor color=\"0xFFEE90\" alpha=\"1\"/>"
				 + "						</fill>"				 
				 + "					</Column2DSeries>"
				 + "					<Column2DSeries labelPosition=\"inside\" yField=\"C07\" displayName=\"서버\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "						<fill>"
				 + "							<SolidColor color=\"0xFFCA6C\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "					</Column2DSeries>"
				 + "					<Column2DSeries labelPosition=\"inside\" yField=\"C08\" displayName=\"네트워크\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "						<fill>"
				 + "							<SolidColor color=\"0xFFA648\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "					</Column2DSeries>"
				 + "					<Column2DSeries labelPosition=\"inside\" yField=\"C09\" displayName=\"정보보호시스템\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "						<fill>"
				 + "							<SolidColor color=\"0xFF8224\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "					</Column2DSeries>"
				 + "					<Column2DSeries labelPosition=\"inside\" yField=\"C10\" displayName=\"문서\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "						<fill>"
				 + "							<SolidColor color=\"0xFF5E00\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "					</Column2DSeries>"
				 + "					<Column2DSeries labelPosition=\"inside\" yField=\"C11\" displayName=\"소프트웨어\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "						<fill>"
				 + "							<SolidColor color=\"0xDB3A00\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "					</Column2DSeries>"
				 + "					<Column2DSeries labelPosition=\"inside\" yField=\"C12\" displayName=\"단말기\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "						<fill>"
				 + "							<SolidColor color=\"0xB71600\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "					</Column2DSeries>"
				 + "					<Column2DSeries labelPosition=\"inside\" yField=\"C13\" displayName=\"물리적자산\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "						<fill>"
				 + "							<SolidColor color=\"0x930000\" alpha=\"1\"/>"
				 + "						</fill>"				 
				 + "					</Column2DSeries>"
				 + "				</series>"
				 + "			</Column2DSet>"
				 + "		</series>"
				 + "	</Column2DChart>"
				 + "</rMateChart>";
		
		return chartGrid;
	}
	
	@Override
	public String state000_chart(String sTitle, String mTitle, String cateField) {
		
		String chartGrid = "<rMateChart backgroundColor=\"0xFFFFFF\" borderStyle=\"none\">"
				 + "	<Options>"
				 + "	</Options>"
				 + "	<NumberFormatter id=\"numfmt\" useThousandsSeparator=\"true\"/>"
				 + "	<Bar2DChart showDataTips=\"true\" dataTipJsFunction=\"dataTipFunc000\" itemClickJsFunction=\"chartItemClickHandler\">"
				 + "		<horizontalAxis>"
				 + "			<LinearAxis displayName=\"건\" formatter=\"{numfmt}\"/>"
				 + "		</horizontalAxis>"
				 + "		<verticalAxis>"
				 + "			<CategoryAxis id=\"hAxis\" categoryField=\""+cateField+"\" ticksBetweenLabels=\"true\" direction=\"inverted\"/>"
				 + "		</verticalAxis>"
				 + "		<series>"
				 + "			<Bar2DSet type=\"stacked\" showTotalLabel=\"\" totalLabelJsFunction=\"totalFunc\">"
				 + "				<series>"
				 + "					<Bar2DSeries labelPosition=\"inside\" xField=\"C06\" displayName=\"전자정보\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "						<fill>"
				 + "							<SolidColor color=\"0xFFEE90\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "					</Bar2DSeries>"
				 + "					<Bar2DSeries labelPosition=\"inside\" xField=\"C07\" displayName=\"서버\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "						<fill>"
				 + "							<SolidColor color=\"0xFFCA6C\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "					</Bar2DSeries>"
				 + "					<Bar2DSeries labelPosition=\"inside\" xField=\"C08\" displayName=\"네트워크\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "						<fill>"
				 + "							<SolidColor color=\"0xFFA648\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "					</Bar2DSeries>"
				 + "					<Bar2DSeries labelPosition=\"inside\" xField=\"C09\" displayName=\"정보보호시스템\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "						<fill>"
				 + "							<SolidColor color=\"0xFF8224\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "					</Bar2DSeries>"
				 + "					<Bar2DSeries labelPosition=\"inside\" xField=\"C10\" displayName=\"문서\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "						<fill>"
				 + "							<SolidColor color=\"0xFF5E00\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "					</Bar2DSeries>"
				 + "					<Bar2DSeries labelPosition=\"inside\" xField=\"C11\" displayName=\"소프트웨어\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "						<fill>"
				 + "							<SolidColor color=\"0xDB3A00\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "					</Bar2DSeries>"
				 + "					<Bar2DSeries labelPosition=\"inside\" xField=\"C12\" displayName=\"단말기\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "						<fill>"
				 + "							<SolidColor color=\"0xB71600\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "					</Bar2DSeries>"
				 + "					<Bar2DSeries labelPosition=\"inside\" xField=\"C13\" displayName=\"물리적자산\"  showValueLabels=\"[]\" color=\"#ffffff\">"
				 + "						<showDataEffect>"
				 + "							<SeriesInterpolate/>"
				 + "						</showDataEffect>"
				 + "						<fill>"
				 + "							<SolidColor color=\"0x930000\" alpha=\"1\"/>"
				 + "						</fill>"
				 + "					</Bar2DSeries>"
				 + "				</series>"
				 + "			</Bar2DSet>"
				 + "		</series>"
				 + "	</Bar2DChart>"
				 + "</rMateChart>";
		
		return chartGrid;
	}
}
