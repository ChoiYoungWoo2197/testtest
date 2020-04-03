package com.uwo.isms.web;

import com.uwo.isms.job.AsyncJob;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

import java.sql.*;
import java.util.Map;

@Controller
@RequestMapping("/debug")
public class DebugController {

    Logger log = LogManager.getLogger(FMSetupController.class);

    @Autowired
    XmlWebApplicationContext webApplicationContext;

    @Autowired
    AsyncJob asyncJob;

//    @RequestMapping(value = "/query.do")
    public String queryToDatabase(@RequestParam Map<String, String> params, ModelMap model) {

        // 사용자 입력값 변수에 저장
        String datasourceBeanId = params.get("datasource_bean_id");
        String query = params.get("query");

        // view 입력필드에 사용자 입력값 다시 출력하기위해 정보 저장
        model.addAllAttributes(params);

        // 사용자 입력값이 설정되어 있는 경우만 질의문 실행
        if (StringUtils.isNotBlank(datasourceBeanId) && StringUtils.isNotBlank(query)) {

            Connection connection = null;
            PreparedStatement pstmt = null;
            ResultSet resultSet = null;

            // 예외 처리 목록
            // 1. 데이터소스 빈 가지고 올 시 발생할 수 있는 BeansException
            // 2. 데이터베이스 작업 시 발생할 수 있는 SQLException
            try {

                // 해당하는 빈을 스프링 컨텍스트에서 가지고 온다.
                BasicDataSource dataSource = webApplicationContext.getBean(datasourceBeanId, BasicDataSource.class);

                // 데이터베이스 연결을 생성한다.
                connection = dataSource.getConnection();

                // 질의를 실행하고 결과를 생성한다.
                pstmt = connection.prepareStatement(query);
                resultSet = pstmt.executeQuery();

                // 질의 결과에 대한 메타데이터 정보를 가지고 온다. (row에 대한 column 갯수를 확인하기 위해)
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                int columnCount = resultSetMetaData.getColumnCount();

                // 정상적인 질의 결과를 HTML TABLE 형태로 표현하기위해 문자열을 가공을 시작한다.
                StringBuilder stringBuilder = new StringBuilder("<table>");

                // 칼럼 이름을 추가한다.
                stringBuilder.append("<tr>");
                for (int i=1; i<columnCount+1; i++) {
                    stringBuilder.append("<th>" + resultSetMetaData.getColumnName(i) + "</th>");
                }
                stringBuilder.append("</tr>");

                // 각 row에 대한 칼럼 값들을 추가한다.
                while (resultSet.next())
                {
                    stringBuilder.append("<tr>");
                    for (int i=1; i<columnCount+1; i++) {
                        stringBuilder.append("<td>" + resultSet.getString(i) + "</td>");
                    }
                    stringBuilder.append("</tr>");
                }
                stringBuilder.append("</table>");

                model.addAttribute("result", stringBuilder.toString());

            } catch (BeansException exception) {
                model.addAttribute("result", datasourceBeanId + " 아이디로 생성된 빈 정보가 없습니다.");
            } catch (SQLException exception) {
                model.addAttribute("result", ExceptionUtils.getStackTrace(exception));
            } finally {
                try { resultSet.close(); } catch (Exception e) {  }
                try { pstmt.close(); } catch (Exception e) {  }
                try { connection.close(); } catch (Exception e) {  }
            }

        }

        // 4. View 처리
        return "debug/query-to-database";
    }

//    @RequestMapping(value = "/refresh.do")
    public void refreshContext(@RequestParam Map<String, String> params, ModelMap model) {

        ApplicationContext parentApplicationContext = webApplicationContext.getParent();

        if (parentApplicationContext != null) {
            ((ConfigurableApplicationContext) parentApplicationContext).refresh();
        }

        ((ConfigurableApplicationContext) webApplicationContext).refresh();

    }

//    @RequestMapping(value = "/sync.do")
    public void asyncDb() {

        try {
            asyncJob.userJob();
        } catch (SQLException exception) {}

    }

}
