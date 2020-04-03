package com.uwo.isms.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uwo.isms.web.ProtectionMeasureController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.multi.MultiInternalFrameUI;
import java.io.BufferedReader;
import java.sql.Clob;
import java.util.*;

/**
 * 전역 범위에서 사용되는 일반적인 코드들을 재사용 가능한 형태로 만들어 놓은 클래스
 *
 * @author ageofsys
 * @since 1.0
 */
@Component
public class CommonUtil {

    private Logger log = LogManager.getLogger(ProtectionMeasureController.class);

    /**
     * 현재 연도 정보를 반환한다.
     */
    public int getCurrentYear() {
        Calendar calendar = new GregorianCalendar(Locale.KOREA);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 객체를 JSON 포맷으로 변환 후 반환한다.
     * @param object JSON으로 변환할 객체
     * @return JSON 문자열
     */
    public String getJsonFromObject(Object object) {
        String json = null;

        try {
            json = (new ObjectMapper()).writeValueAsString(object);
        } catch (JsonProcessingException exception) {
            log.error(exception);
        }

        return json;
    }

    /**
     * Http 요청에 Multipart 파일 정보가 있는지를 확인한다.
     * @param httpServletRequest HttpServletRequest
     * @return boolean
     */
    public boolean hasFileOnRequest(HttpServletRequest httpServletRequest) {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) httpServletRequest;
        Map<String, MultipartFile> map = multipartHttpServletRequest.getFileMap();
        boolean hasFile = false;
        for (Map.Entry<String, MultipartFile> entry : map.entrySet()) {
            MultipartFile multipartFile = entry.getValue();
            if ( ! multipartFile.isEmpty()) {
                hasFile = true;
                break;
            }
        }
        return hasFile;
    }

    /**
     * 403 페이지로 이동시킨다.
     */
    public void redirectTo403Page(HttpServletResponse httpServletResponse) throws Exception {
        httpServletResponse.sendRedirect("/common/error403.jsp");
    }

    /**
     * 시작, 종료 값 사이 연속된 정수 리스트를 반환한다.
     * @param start 시작 값
     * @param end 종료 값
     */
    public List<Integer> getNumberBetween(int start, int end) {
        List<Integer> numbers = new ArrayList<Integer>();

        for (int i = start; i < end + 1; i++) {
            numbers.add(i);
        }
        return numbers;
    }

    /**
     * 연도 정보를 BCY_CODE 정보로 변경한다.
     * 2019 -> 19
     * 연도 정보에서 뒷 2자리가 BCY_CODE 가 된다.
     */
    public String yearToBcyCode(int year) {
        return String.valueOf(year).substring(2);
    }

    public static String mergeUrlQuery(String baseQuery, String addQuery) {

        String mergedUrlQueryString = "";

        Map<String, String> baseQueryMap = CommonUtil.urlQueryStringToMap(baseQuery);
        Map<String, String> addQueryMap = CommonUtil.urlQueryStringToMap(addQuery);

        baseQueryMap.putAll(addQueryMap);

        for (Map.Entry<String, String> entry : baseQueryMap.entrySet()) {
            mergedUrlQueryString += entry.getKey() + "=" + entry.getValue() + "&";
        }

        return mergedUrlQueryString;
    }

    public static Map<String, String> urlQueryStringToMap(String UrlQueryString) {
        HashMap<String, String> data = new HashMap<String, String>();

        final String[] arrParameters = UrlQueryString.split("&");
        for (final String tempParameterString : arrParameters) {

            final String[] arrTempParameter = tempParameterString
                    .split("=");

            if (arrTempParameter.length >= 2) {
                final String parameterKey = arrTempParameter[0];
                final String parameterValue = arrTempParameter[1];
                data.put(parameterKey, parameterValue);
            } else {
                final String parameterKey = arrTempParameter[0];
                data.put(parameterKey, "");
            }
        }

        return data;
    }

    public String clobToString(Clob clob) throws Exception {
        String result = "";
        if (clob != null) {
            StringBuffer strBuffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(clob.getCharacterStream());
            while ((result = reader.readLine()) != null) {
                strBuffer.append(result);
            }
            result = strBuffer.toString();
        }
        return result;
    }


}
