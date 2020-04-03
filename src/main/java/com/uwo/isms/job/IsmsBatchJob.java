/**
 ***********************************
 * @source IsmsBatchJob.java
 * @date 2016. 01. 11.
 * @project isms3
 * @description
 ***********************************
 */
package com.uwo.isms.job;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.uwo.isms.common.EventTitleMessage;
import com.uwo.isms.dao.IsmsBatchDAO;
import com.uwo.isms.service.CommonCodeService;
import com.uwo.isms.service.IsmsBatchService;
import com.uwo.isms.service.SendMailService;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@Component("ismsBatchJob")
public class IsmsBatchJob {

	@Resource(name="ismsBatchDAO")
	private IsmsBatchDAO ismsBatchDao;

	@Resource(name = "sendMailService")
	SendMailService sendMailService;

	@Resource
	IsmsBatchService ismsBatchService;

	@Resource
	CommonCodeService commonCodeService;

	Logger log = LogManager.getLogger(IsmsBatchJob.class);

	public void job() {

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = null;

		// test
		//cal.set(2018, 9, 1);

		sdf = new SimpleDateFormat("yyyyMMdd");
		String currentDate = sdf.format(cal.getTime());

		//sdf = new SimpleDateFormat("yy");
		//String currentYear = sdf.format(cal.getTime());

		sdf = new SimpleDateFormat("M");
        int currentMonth = Integer.parseInt(sdf.format(cal.getTime()));

        // Email
        List<EgovMap> emailListFinal = new ArrayList<EgovMap>();
        List<EgovMap> mailList = null;

        EgovMap result = null;

        // 이번 달의 시작일, 종료일 (근무일 기준)
        result = ismsBatchDao.getWorkDaysOfMonth(currentDate);
		String firstDateOfMonth = result.get("firstDate").toString();
		String lastDateOfMonth = result.get("lastDate").toString();

        // 이번 주의 시작일, 종료일 (근무일 기준)
		result = ismsBatchDao.getWorkDaysOfWeek(currentDate);
		String firstDateOfWeek = result.get("firstDate").toString();
		String lastDateOfWeek = result.get("lastDate").toString();

		log.info("currentDate: {}, currentMonth: {}", currentDate, currentMonth);
		log.info("firstDateOfMonth: {}, lastDateOfMonth: {}, firstDateOfWeek: {}, lastDateOfWeek: {}", firstDateOfMonth, lastDateOfMonth, firstDateOfWeek, lastDateOfWeek);

		Map<String, String> map = new HashMap<String, String>();
		map.put("adminId", "43");
		//map.put("bcyCode", currentYear);
		map.put("currentDate", currentDate);
		map.put("utdSdmYn", "Y");	// send mail

		// D 일간
		log.debug("IsmsBatchJob: 일간");
		map.put("TrmGbn", "D");
		map.put("lastDate", currentDate);
		mailList = ismsBatchDao.selectWork(map);
		emailListFinal.addAll(mailList);
		ismsBatchDao.insertWork(map);

		// W 주간 (매주 월요일)
		if (currentDate.equals(firstDateOfWeek)) {
			log.debug("IsmsBatchJob: 주간");
			map.put("TrmGbn", "W");
			map.put("lastDate", lastDateOfWeek);

			mailList = ismsBatchDao.selectWork(map);
			emailListFinal.addAll(mailList);
			ismsBatchDao.insertWork(map);
		}

		// M 월간 (매월 1일)
		if (currentDate.equals(firstDateOfMonth)) {
			log.debug("IsmsBatchJob: 월간");
			map.put("TrmGbn", "M");
			map.put("lastDate", lastDateOfMonth);

			mailList = ismsBatchDao.selectWork(map);
			emailListFinal.addAll(mailList);
			ismsBatchDao.insertWork(map);
		}

		// T 격월 (짝수달)
		if (currentDate.equals(firstDateOfMonth) && currentMonth % 2 == 1) {
			log.debug("IsmsBatchJob: 격월");
			map.put("TrmGbn", "T");
			map.put("lastDate", lastDateOfMonth);

			mailList = ismsBatchDao.selectWork(map);
			emailListFinal.addAll(mailList);
			ismsBatchDao.insertWork(map);
		}

		// Q 분기 (3,6,9,12)
		if (currentDate.equals(firstDateOfMonth) && currentMonth % 3 == 1) {
			log.debug("IsmsBatchJob: 분기");
			map.put("TrmGbn", "Q");
			map.put("lastDate", lastDateOfMonth);

			mailList = ismsBatchDao.selectWork(map);
			emailListFinal.addAll(mailList);
			ismsBatchDao.insertWork(map);
		}

		// H 반기 (6,12)
		if (currentDate.equals(firstDateOfMonth) && currentMonth % 6 == 1) {
			log.debug("IsmsBatchJob: 반기");
			map.put("TrmGbn", "H");
			map.put("lastDate", lastDateOfMonth);

			mailList = ismsBatchDao.selectWork(map);
			emailListFinal.addAll(mailList);
			ismsBatchDao.insertWork(map);
		}

		// send mail
		String eventCode = EventTitleMessage.E01_CODE;
		String title = EventTitleMessage.E01;

		log.debug(emailListFinal);
		for (int i = 0; i < emailListFinal.size(); i++) {
			EgovMap cMap = (EgovMap)emailListFinal.get(i);
			String contents = title
					 		+ "<br />해당내용을 확인해주세요."
							+ "<br />■ 업무명 : " + cMap.get("utdDocNam")
							+ "<br />■ 업무기한 : " + cMap.get("utwStrDat") + " ~ " + cMap.get("utwEndDat")+"<br />";
			//log.debug(contents);
			String senderId = map.get("adminId").toString();
			String receiverId = cMap.get("utmWrkId").toString();
			sendMailService.sendMail(senderId, eventCode, title, contents, receiverId);
		}
	}

	/* 2017-02-01
	 * 운영주기 자동생성 스케쥴러
	 * 스케쥴러는 매일 실행되며 기존 운영주기 종료일 다음날 생성됨
	 */
	public void jobManagementCycle() {

		Map<String, String> map = new HashMap<String, String>();
		EgovMap mcyMap = null;

		mcyMap = ismsBatchService.selectLastManagementCycle();

		map.put("rgtId", "1");
		map.put("manCyl", (String) mcyMap.get("ummManCyl"));

		// 오늘 이후의 종료일이 존재하면 처리하지 않음
		if (mcyMap.get("existsDate").equals("false")) {
			ismsBatchService.insertManagementCycle(map);

			mcyMap = ismsBatchService.selectLastManagementCycle();

			map.put("newManCyl", (String) mcyMap.get("ummManCyl"));
			map.put("startDate", (String) mcyMap.get("ummStdDat"));
			map.put("endDate", (String) mcyMap.get("ummEndDat"));

			ismsBatchService.insertManagementCycleData(map);
		}
	}

}
