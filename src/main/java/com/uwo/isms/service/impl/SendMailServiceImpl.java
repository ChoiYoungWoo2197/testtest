package com.uwo.isms.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.uwo.isms.common.Crypto;
import com.uwo.isms.common.PlainMail;
import com.uwo.isms.service.CommonUtilService;
import com.uwo.isms.service.SendMailService;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service("sendMailService")
public class SendMailServiceImpl implements SendMailService {

	@Resource(name = "propertiesService")
	private EgovPropertyService propertySvc;

	@Resource(name = "commonUtilService")
	protected CommonUtilService commonUtilSvc;

	Logger log = LogManager.getLogger(SendMailServiceImpl.class);

	public void sendMail(String key, String eventCode, String title, String contents, String userKey) {

		if (!key.equals("")) {
			String sysGbn = propertySvc.getString("sendType");


			Map<String, Object> map = new HashMap<String, Object>();
	        map.put("key", key);
	        map.put("sysGbn", sysGbn);
	        map.put("evtCode", eventCode);
	        map.put("tit", title);
	        //map.put("contents", contents);
	        map.put("userKey", userKey);
	        commonUtilSvc.insertAlarm(map);

	        /*
	         * 메일전송
	         * emap => 보내는 사람정보 추출(이름,이메일)
	         * receiver => 받는 사람정보 추출(이메일)
	         * subject => 알람테이블의 제목과 같은 내용으로 메일 제목 전송
	         * contents => 메일 내용 작성 후 전송(html 사용 가능)
	         * PlainMail.SendMail(mailMap) 메소드로 메일 전송
	         */

	        EgovMap emap = commonUtilSvc.getSendUserInfo(key);
	     // 2017-09-11, email String -> EgovMap
	        EgovMap emap2 = commonUtilSvc.getRecvUserInfo(userKey);
	        String receiver = null;

	        // 2016-07-20, email 복호화
	        /*String uumMalAds = emap.get("uumMalAds").toString();
	 		if (uumMalAds != null && uumMalAds != "") {
	 			uumMalAds = Crypto.decAes256(uumMalAds);
	 		}*/
	        // 2017-09-08, sendUser
	        String uumMalAds = propertySvc.getString("sendUser");

	 		if (emap2 != null && emap2.get("uumMalAds") != null && emap2.get("uumMalAds") != "") {
	 			receiver = Crypto.decAes256(emap2.get("uumMalAds").toString());
	 		}

	 		// 2018-04-23 s, if (receiver != null) { 조건추가
	 		if (receiver != null) {
		 		// 2017-09-11, template USER_NAME 변환
		 		contents = contents.replace("{__USER_NAME__}", emap2.get("uumUsrNam").toString());

		        Map<String, Object> mailMap = new HashMap<String, Object>();
		        mailMap.put("subject", title);
		        mailMap.put("senderEmail", uumMalAds);
		        mailMap.put("senderName", emap.get("uumUsrNam"));
		        mailMap.put("receiver", receiver);
		        mailMap.put("contents", contents);

		        /*
		         * PlainMail.java 안에서 프로퍼티 호출이 되지 않아
		         * 컨트롤러 단에서 맵을 채워서 전달하는 방식으로 진행
		         * 맵에 입력되는 순서대로 인증유저, 인증유저비밀번호, SMTP 접속정보, SMTP 포트
		         */

		        Map<String, String> smtpMap = new HashMap<String, String>();
		        smtpMap.put("authUser",propertySvc.getString("authUser"));
		        smtpMap.put("authPassword",propertySvc.getString("authPassword"));
		        smtpMap.put("smtpInfo",propertySvc.getString("smtpInfo"));
		        smtpMap.put("smtpPort",propertySvc.getString("smtpPort"));

				//log.info("receiver: {}, subject: {}, content: {}", receiver, title, contents);

		        // 2017-06-14, if (MAL) 일 경우에만 메일 발송
		        if (sysGbn.equals("MAL")) {
		        	PlainMail.SendMail(mailMap,smtpMap);
		        }
	 		}
		}
	}
}
