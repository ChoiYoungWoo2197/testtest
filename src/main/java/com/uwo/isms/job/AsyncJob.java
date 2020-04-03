/**
 ***********************************
 * @source CommonCodeController.java
 * @date 2014. 10. 20.
 * @project isms3_godohwa
 * @description
 ***********************************
 */
package com.uwo.isms.job;

import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StopWatch;

import java.util.*;

import javax.annotation.Resource;

import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.stereotype.Component;

import com.uwo.isms.common.CommonConfig;
import com.uwo.isms.common.Crypto;
import com.uwo.isms.dao.CommonUtilDAO;
import com.uwo.isms.dao.FMAssetDAO;
import com.uwo.isms.dao.FMRiskmDAO;
import com.uwo.isms.dao.LegacyDAO;
import com.uwo.isms.dao.LocalDAO;
import com.uwo.isms.dao.SmartGuardDAO;
import com.uwo.isms.service.CommonUtilService;
import com.uwo.isms.service.IsmsBatchService;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@Component("asyncJob")
public class AsyncJob {

	@Resource(name="legacyDAO")
	private LegacyDAO legacyDAO;

	@Resource(name="localDAO")
	private LocalDAO localDAO;

	@Resource(name="fmAssetDAO")
	private FMAssetDAO fmAssetDAO;

	@Resource(name="commonUtilDAO")
	private CommonUtilDAO commonUtilDAO;

	@Resource(name = "commonUtilService")
	private CommonUtilService commonUtilService;

	@Resource
	FMRiskmDAO fmRiskmDAO;

	@Resource
	IsmsBatchService ismsBatchService;

	@Resource
	private SmartGuardDAO smartGuardDAO;

	private static final Logger log = LogManager.getLogger(AsyncJob.class);
	private static StopWatch stopWatch = new StopWatch("job");

	private static MessageDigestPasswordEncoder passwordEncoder = new MessageDigestPasswordEncoder("SHA-256");

	public void userJob() throws SQLException {

		try {
			stopWatch.start();
			List<?> legacyDept = legacyDAO.legacyDeptList();
			List<?> legacyPos = legacyDAO.legacyPosList();
			List<?> legacyUser = legacyDAO.legacyUserList();
			List<?> localUser = localDAO.localUserList();

			List<EgovMap> deptList = new ArrayList<EgovMap>();
			List<EgovMap> posList = new ArrayList<EgovMap>();
			List<EgovMap> userList = new ArrayList<EgovMap>();

			String apv1 = commonUtilService.getApproval("APV1");
			String apv2 = commonUtilService.getApproval("APV2");

			if (log.isDebugEnabled()) {
				log.debug("==========================================================================================");
				log.debug("=== " + "ISC User, Group, Duty DB sync START");
				log.debug("==========================================================================================");
			}

			// 구성원 존재유무 확인
	 	 	if (legacyUser.size() > 0) {
	 	 		for (int i = 0; i < legacyUser.size() ; i++) {
	 	 			EgovMap map = (EgovMap)legacyUser.get(i);

	 	 			for (int j = 0; j < localUser.size(); j++) {
	 	 				EgovMap map2 = (EgovMap)localUser.get(j);

	 	 				if (StringUtils.equalsIgnoreCase(map.get("userId").toString(), map2.get("uumUsrId").toString())) {
	 	 					map.put("isNew", 0);
	 	 					break;
	 	 				}
	 	 			}

	 	 		}

	 	 	}

		    // 부서 업데이트
		 	if (legacyDept.size() > 0) {
		 		for (int i = 0; i < legacyDept.size() ; i++) {
		 			EgovMap map = (EgovMap)legacyDept.get(i);
		 			// 외주업체 groupname null이 존재함
		 			if (StringUtils.isEmpty(map.get("groupName").toString())) {
		 				map.put("groupName", map.get("groupId").toString());
		 			}
		 			deptList.add(map);
		 		}
		 		localDAO.updateDept(deptList);
		 	}

		 	// 직책 업데이트
	 	 	if (legacyPos.size() > 0) {
	 	 		for (int i = 0; i < legacyPos.size() ; i++) {
	 	 			EgovMap map = (EgovMap)legacyPos.get(i);
	 	 			posList.add(map);
	 	 		}
	 	 		localDAO.updatePos(posList);
	 	 	}

		 	// 구성원 업데이트
	 	 	if (legacyUser.size() > 0) {
	 	 		for (int i = 0; i < legacyUser.size() ; i++) {
	 	 			EgovMap map = (EgovMap)legacyUser.get(i);

	 	 			if (map.get("isNew").equals(1)) {
 	 				//	log.debug("userId: {}", map.get("userId").toString());
	 	 	 			String userPwd = passwordEncoder.encodePassword(map.get("userId").toString(), null);
	 	 	 			map.put("userPwd", userPwd);
	 	 			}

	 	 			// 2016-07-20, email, mobile 암호화
	 	 			if (map.get("email") != null && StringUtils.isNotEmpty(map.get("email").toString())) {
		 	 			String email = Crypto.encAes256(map.get("email").toString());
		 	 			map.put("email", email);
	 	 			}
	 	 			if (map.get("usermobileNumber") != null && StringUtils.isNotEmpty(map.get("usermobileNumber").toString())) {
		 	 			String usermobileNumber = Crypto.encAes256(map.get("usermobileNumber").toString());
		 	 			map.put("usermobileNumber", usermobileNumber);
	 	 			}

	 	 			// 2018-05-11 s, 기본 결재자 지정
	 	 			map.put("apvOne", apv1);
	 	 			map.put("apvTwo", apv2);

	 	 			userList.add(map);
	 	 		}
	 	 		localDAO.updateUser(userList);
	 	 	}

	 	 	if (log.isDebugEnabled()) {
				tempDebugTool(deptList, "ISC Group List");
				tempDebugTool(posList, "ISC Duty List");
				tempDebugTool(userList, "ISC User List");
			}

	 	 	// 업데이트 되지 않은 사용자는 사용안함 처리
	 	 	localDAO.disableUser();

	 	 	// 1차 결재자, 서비스 업데이트
	 	 	// 2018-05-11 s, 주석처리
	 	 	localDAO.updateUserInfo();

		}
		catch (Exception exception) {
			ExceptionUtils.getStackTrace(exception);
		}
	 	finally {
		 	stopWatch.stop();
		 	log.info(stopWatch.prettyPrint());

			if (log.isDebugEnabled()) {
				log.debug("==========================================================================================");
				log.debug("=== " + "ISC User, Group, Duty DB sync END");
				log.debug("==========================================================================================");
			}
	 	}
	}

	/**
	 * userJob 을 디버깅하기위해 임시로 만들어놓은 메소드, 추후 리팩토링이 필요하다.
	 * @param list
	 * @param title
	 */
	private void tempDebugTool(List<EgovMap> list, String title) {

		log.info("=== List Debug : " + title);

		for (EgovMap egovMap : list) {
			Iterator<String> keys = egovMap.keySet().iterator();
			while (keys.hasNext()) {
				String key = keys.next();
				Object rawValue = egovMap.get(key);
				log.info("key is " + key);
				log.info(key + " : " + (rawValue == null ? "" : rawValue.toString()));
			}
			log.info("------------------------------------------");
		}

	}



	/*
	 * 2016-11-25
	 * ISC Asset
	 */
	public void assetJob() throws SQLException {

		// 2017-05-26, batch size
		int batchSize = 50;

		try {
			stopWatch.start();

			List<EgovMap> assetList = new ArrayList<EgovMap>();

			// 1. select ISC Asset
			List<?> legacyAsset = legacyDAO.legacyAssetList();

			if (legacyAsset.size() > 0) {
				loop:
	 	 		for (int i = 0; i < legacyAsset.size() ; i++) {
	 	 			EgovMap map = (EgovMap) legacyAsset.get(i);
	 	 			EgovMap infoMap = new EgovMap();

					String uarGrpCod = "";
					String uarGrpNam = "";
	 	 			String uarRskGrp = "";
	 	 			String uarRskNam = "";
	 	 			String uarAppTot = "";
	 	 			String uarAssLvl = "";

	 	 			String uarDepCod = (String) map.get("manageDeptCode");
	 	 			String uarSubCod = (String) map.get("serviceCode");
	 	 			String catCod = (String) map.get("assetClassficationCode");

	 	 			// 2. 데이터 존재 유무 확인
	 				infoMap.put("uarAssCod", (String) map.get("assetCode"));
	 				infoMap.put("uarUptMdh", (String) map.get("updateDate"));
	 				infoMap.put("uarBcyCod", "0");

	 	 			EgovMap reMap = localDAO.selectAssetInfo(infoMap);
	 	 			if (reMap != null) {
	 	 				// 2-1. upt_mdh 확인
	 	 				if (reMap.get("updated").equals("true")) {
	 		 				infoMap.put("uarAssKey", reMap.get("uarAssKey").toString());
	 	 				}
	 	 				// 2-2. 업데이트되지 않은 경우 continue
	 	 				else {
	 	 					continue loop;
	 	 				}
	 	 			}

	 	 			// 2017-05-26, ISC에서 DeptName 가져옴
	 	 			//String uarDepNam = fmAssetDAO.fmasset001_asset_grpcode01(uarDepCod);
	 	 			String uarDepNam =  (String) map.get("manageDeptName");
	 	 			String uarSubNam = fmAssetDAO.fmasset001_asset_grpcode02(uarSubCod);

	 	 			/*
							manufacturer	productlName	produVersion	osversion		appurl
					6 DB	uar_eqp_nam						uar_val_cl0
					7 SE	uar_val_cl0		uar_val_cl1										uar_val_cl4
					8 NE	uar_val_cl0		uar_val_cl1
					9 SS	uar_val_cl0		uar_val_cl1
					10 DC					uar_val_cl0		[보존년한, 문서번호, 문서명]
					11 SW									uar_val_cl3		uar_val_cl0		uar_val_cl1
					12 TE					uar_val_cl0		uar_val_cl1
					13 PH	uar_eqp_nam		uar_val_cl0		uar_val_cl1
					17 SD	uar_val_cl0		uar_val_cl1		uar_val_cl2
					*/

	 	 			infoMap.put("uarIp", (String) map.get("totalIP"));
					infoMap.put("uarHost", (String) map.get("hostname"));
					infoMap.put("uarOs", (String) map.get("osname"));

					switch (catCod) {
					case "DB" :
						infoMap.put("uarEqpNam", (String) map.get("manufacturer"));
						infoMap.put("uarValCl0", (String) map.get("producVersion"));
						break;
					case "SE" :

						infoMap.put("uarEqpNam", (String) map.get("hostname"));
						infoMap.put("uarValCl0", (String) map.get("manufacturer"));
						infoMap.put("uarValCl1", (String) map.get("productlName"));
						infoMap.put("uarValCl2", (String) map.get("appurl"));
						break;
					case "NE" :
						infoMap.put("uarEqpNam", (String) map.get("hostname"));
						infoMap.put("uarValCl0", (String) map.get("manufacturer"));
						infoMap.put("uarValCl1", (String) map.get("productlName"));
						break;
					case "SS" :
						infoMap.put("uarEqpNam", (String) map.get("hostname"));
						infoMap.put("uarValCl0", (String) map.get("manufacturer"));
						infoMap.put("uarValCl1", (String) map.get("productlName"));
						break;
					case "DC" :
						//infoMap.put("uarEqpNam", (String) map.get("문서명"));
						infoMap.put("uarValCl0", (String) map.get("productlName"));
						//infoMap.put("uarValCl1", (String) map.get("보존년한"));
						//infoMap.put("uarValCl2", (String) map.get("문서번호"));
						break;
					case "SW" :
						infoMap.put("uarEqpNam", (String) map.get("hostname"));
						infoMap.put("uarValCl0", (String) map.get("osversion"));
						infoMap.put("uarValCl1", (String) map.get("appurl"));
						infoMap.put("uarValCl2", (String) map.get("producVersion"));
						break;
					case "TE" :
						infoMap.put("uarEqpNam", (String) map.get("hostname"));
						infoMap.put("uarValCl0", (String) map.get("productlName"));
						infoMap.put("uarValCl1", (String) map.get("producVersion"));
						break;
					case "PH" :
						infoMap.put("uarEqpNam", (String) map.get("manufacturer"));
						infoMap.put("uarValCl0", (String) map.get("productlName"));
						infoMap.put("uarValCl1", (String) map.get("producVersion"));
						break;
					case "SD" :
						infoMap.put("uarEqpNam", (String) map.get("hostname"));
						infoMap.put("uarValCl0", (String) map.get("manufacturer"));
						infoMap.put("uarValCl1", (String) map.get("productlName"));
						infoMap.put("uarValCl2", (String) map.get("producVersion"));
						break;
					}

	 	 			// 3. 위험도, 그룹코드, 그룹명 생성
	 	 			// 기밀성(1/2/3) + 무결성(1/2/3) + 가용성(1/2/3)
					int sum = Integer.valueOf((String) map.get("confidentiality"))
							+ Integer.valueOf((String) map.get("integrity"))
							+ Integer.valueOf((String) map.get("fusibility"));

					uarAppTot = String.valueOf(sum);

					if (sum >= 8) {
						uarAssLvl = "H(3)";
					}
					else if (sum >= 5) {
						uarAssLvl = "M(2)";
					}
					else {
						uarAssLvl = "L(1)";
					}


					// 4.그룹코드, 그룹명 생성
					EgovMap emap = new EgovMap();
	 	 			if (catCod.equals("TE")) { // OS
	 	 				emap = fmAssetDAO.fmasset001_rsk_os_info((String) map.get("osname"));
	 	 			}
	 	 			else {
	 	 				emap = fmAssetDAO.fmasset001_rsk_code_info((String) map.get("assetTypeCode"));
	 	 			}

					if (emap != null) {
						uarRskGrp = (String) emap.get("uarRskGrp");
						uarRskNam = (String) emap.get("uarRskNam");

						// 그룹코드 = 부서코드 + 서비스코드 + 자산유형 + 보안영향도
						uarGrpCod = new StringBuffer()
							.append(uarDepCod)
							.append("_")
							.append(uarSubCod)
							.append("_")
							.append(uarRskGrp)
							.append("_")
							.append(uarAssLvl).toString();

						// 그룹명 = 부서명 + 서비스명 + 자산유형 + 보안영향도
						uarGrpNam = new StringBuffer()
							.append(uarDepNam)
							.append("_")
							.append(uarSubNam)
							.append("_")
							.append(uarRskNam)
							.append("_")
							.append(uarAssLvl).toString();
					}


	 	 			infoMap.put("uarRgtId", (String) map.get("createID"));
	 				infoMap.put("uarRgtMdh", (String) map.get("createDate"));
	 				infoMap.put("uarUptId", (String) map.get("updateID"));
	 				infoMap.put("uarUseYn", (String) map.get("useYN"));

	 				infoMap.put("uarSvrEtc", (String) map.get("assetSvcDetl"));
	 				infoMap.put("uarDepCod", (String) map.get("manageDeptCode"));
	 				infoMap.put("uarOwnId", (String) map.get("manageChargeUserID"));
	 				infoMap.put("uarAdmId", (String) map.get("manageOfficerUserID"));
	 				infoMap.put("uarOprCod", (String) map.get("operateDeptCode"));
	 				infoMap.put("uarUseId", (String) map.get("operateChargeUserID"));
	 				infoMap.put("uarPicId", (String) map.get("operateOfficerUserID"));

	 				infoMap.put("uarCatCod", (String) map.get("assetClassficationCode"));
	 				infoMap.put("uarAssGbn", (String) map.get("assetTypeCode"));
	 				infoMap.put("uarAssNam", (String) map.get("assetName"));
	 				infoMap.put("uarDtlExp", (String) map.get("useDetail"));
	 				infoMap.put("uarAdmPos", (String) map.get("equipSite"));
	 				infoMap.put("uarRskGrp", uarRskGrp);
	 				infoMap.put("uarRskNam", uarRskNam);

	 				infoMap.put("uarAppCon", (String) map.get("confidentiality"));
	 				infoMap.put("uarAppItg", (String) map.get("integrity"));
	 				infoMap.put("uarAppAvt", (String) map.get("fusibility"));
	 				infoMap.put("uarAudYn", (String) map.get("ISAMSYN"));
	 				infoMap.put("uarSmpYn", (String) map.get("samplingYN"));
	 				infoMap.put("uarInfYn", (String) map.get("infrastructureYN"));
	 				infoMap.put("uarPrvYn", (String) map.get("personalDataYN"));

	 				infoMap.put("uarSubCod", uarSubCod);
	 				infoMap.put("uarSubNam", uarSubNam);
	 				infoMap.put("uarGrpCod", uarGrpCod);
	 				infoMap.put("uarGrpNam", uarGrpNam);
	 				infoMap.put("uarAssLvl", uarAssLvl);
	 				infoMap.put("uarAppTot", uarAppTot);

	 	 			assetList.add(infoMap);

	 	 			// 5. 그룹 생성
	 	 			if (fmAssetDAO.fm_asset001_groupCdCount(uarGrpCod) == 0) {
	 	 				infoMap.put("uagGrpKey", commonUtilDAO.getGrpCodSeq());
	 	 				localDAO.insertAssetGroup(infoMap);
	 	 			}

	 	 			// 6-1. INSERT / UPDATE 자산
					if (i > 0 && i % batchSize == 0) {
						localDAO.updateAsset(assetList);
						assetList.clear();
					}
	 	 		}

				// 6-2. INSERT / UPDATE 자산
				if (assetList.size() > 0) {
					localDAO.updateAsset(assetList);
				}
	 	 	}

		}
	 	finally {
		 	stopWatch.stop();
		 	log.info(stopWatch.prettyPrint());
	 	}
	}


	/*
	 * 2017-01-25
	 * ISC SSR 진단관리
	 */
	public void diagnosisJob() throws SQLException {

		try {
			stopWatch.start();

			List<Map<String, String>> itemInfoList = new ArrayList<Map<String, String>>();
			List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
			List<Map<String, String>> srlList = new ArrayList<Map<String, String>>();
			//List<Map<String, String>> assetCodeList = new ArrayList<Map<String, String>>();

			HashSet<String> grpSet = new HashSet<String>();
			HashSet<String> assetCodeSet = new HashSet<String>();

			// 현재 주기
			EgovMap mcyMap = ismsBatchService.selectLastManagementCycle();
			String manCyl = (String) mcyMap.get("ummManCyl");
			String rgtId = "1";

			// 1. 진단 코드
			List<?> legacyItemInfoList = legacyDAO.legacyDiagnosisItemInfoList();
	 	 	if (legacyItemInfoList.size() > 0) {
	 	 		for (int i = 0; i < legacyItemInfoList.size() ; i++) {
	 	 			EgovMap map = (EgovMap)legacyItemInfoList.get(i);
	 	 			Map<String, String> emap = new HashMap<String, String>();

	 	 			String urvRskItm = new StringBuffer()
	 	 					.append((String) map.get("userCode"))
	 	 					.append(" ")
	 	 					.append((String) map.get("diagnosisItemName"))
	 	 					.toString();

	 	 			// U, M, D
	 	 			String urvRskImp = "";
	 	 			switch (map.get("diagnosisItemRiskGrade").toString()) {
	 	 			case "1" : urvRskImp = "D"; break;
	 	 			case "2" : urvRskImp = "M"; break;
	 	 			case "3" : urvRskImp = "U"; break;
	 	 			}

	 	 			emap.put("urvVlbCod", (String) map.get("diagnosisItemCode"));
	 	 			emap.put("urvRskItm", urvRskItm);
	 	 			emap.put("urvRskDiv", (String) map.get("userClass"));
	 	 			emap.put("urvRskImp", urvRskImp);
	 	 			emap.put("urvRskOrd", String.valueOf(i));
	 	 			emap.put("urvRskSrc", "SSR");
	 	 			emap.put("urvRgtId", rgtId);

	 	 			itemInfoList.add(emap);
	 	 		}
	 	 		localDAO.updateUwoRskVlb(itemInfoList);

	 	 	}

	 	 	// 2. 진단 상세 결과
			List<?> legacyResultList = legacyDAO.legacyDiagnosisResultList();
			if (legacyResultList.size() > 0) {
	 	 		for (int i = 0; i < legacyResultList.size() ; i++) {
	 	 			EgovMap map = (EgovMap)legacyResultList.get(i);
	 	 			Map<String, String> emap = new HashMap<String, String>();

	 	 			// assetcode
	 	 			assetCodeSet.add(map.get("assetCode").toString());

	 	 			String rst = "";
	 	 			switch (map.get("diagnosisResult").toString()) {
	 	 			case "PASS" : rst = "Y"; break;
	 	 			case "FAIL" : rst = "N"; break;
	 	 			default : rst = "NA"; break;
	 	 			}

	 	 			emap.put("manCylKey", manCyl);
	 	 			emap.put("assCod", (String) map.get("assetCode"));
	 	 			emap.put("vlbCod", (String) map.get("diagnosisItemCode"));
	 	 			emap.put("rst", rst);
	 	 			emap.put("urrRgtId", rgtId);
	 	 			emap.put("urrRskTyp", "A");

	 	 			resultList.add(emap);
	 	 		}

				fmRiskmDAO.fm_riskm006_rskm_insert(resultList);

				for (String item: assetCodeSet) {
					Map<String, String> assetMap = new HashMap<String, String>();
					assetMap.put("uarAssCod", item);
					assetMap.put("uarUptId", rgtId);
					//assetCodeList.add(assetMap);

	 	 			grpSet.add(localDAO.getAssetGroupCode(assetMap));
				}

				/*
				 * 2017-04-07, 샘플링 대상 Y 제외
	 	 		// update uwo_ass_mtr smp_yn = Y
				// 취약점 진단 자산의 경우 샘플링 대상을 Y로 변경
				fmRiskmDAO.fm_riskm006_rskm_asset_smp_update_y(assetCodeList);
				*/


				// update uwo_rsk_srl
				for (String item: grpSet) {
					Map<String, String> srlMap = new HashMap<String, String>();
					srlMap.put("manCylKey", manCyl);
					srlMap.put("urrRskTyp", "A");
					srlMap.put("uagGrpCod", item);
					srlMap.put("doNotDelete", "1");
					srlList.add(srlMap);
				}
				fmRiskmDAO.fm_riskm006_rskm_srl_insert(srlList);
	 	 	}
		}
	 	finally {
		 	stopWatch.stop();
		 	log.info(stopWatch.prettyPrint());
	 	}
	}


	/*
	 * 2017-04-20
	 * Smart Guard Asset
	 */
	public void assetJob2() throws SQLException {

		try {
			stopWatch.start();

			List<EgovMap> assetList = new ArrayList<EgovMap>();

			// 1. select ISC Asset
			List<?> legacyAsset = smartGuardDAO.legacyAssetList();

			if (legacyAsset.size() > 0) {
				loop:
	 	 		for (int i = 0; i < legacyAsset.size() ; i++) {
	 	 			EgovMap map = (EgovMap) legacyAsset.get(i);
	 	 			EgovMap infoMap = new EgovMap();

					String uarGrpCod = "";
					String uarGrpNam = "";
	 	 			String uarRskGrp = "";
	 	 			String uarRskNam = "";
	 	 			String uarAppTot = "";
	 	 			String uarAssLvl = "";

	 	 			String uarDepCod = (String) map.get("manageDeptCode");
	 	 			String uarSubCod = (String) map.get("serviceCode");
	 	 			String catCod = (String) map.get("assetClassficationCode");

	 	 			// 2. 데이터 존재 유무 확인
	 	 			//    assetCode와 catCode로 확인
	 				infoMap.put("uarExtCod", (String) map.get("assetCode"));
	 				infoMap.put("uarUptMdh", (String) map.get("updateDate"));
	 				infoMap.put("uarBcyCod", "0");

	 				// 2-1. 자산키+카테고리로 자산 존재 유무 확인
	 				// SELECT FROM uar_ass_mtr WHERE uar_ext_cod = AND uar_cat_cod = (SELECT cat_key FROM uwo_ass_cat WHERE cat_cod = #catCod#)
	 				// 2-2. 있을 경우 업데이트 일 확인
	 				// 2-3. 없을 경우 자산코드 생성 및 그룹 생성
	 				// ISAMS-서비스코드+자산유형(세부)+10000001

	 	 			EgovMap reMap = localDAO.selectAssetInfo(infoMap);
	 	 			if (reMap != null) {
	 	 				// 2-1. upt_mdh 확인
	 	 				if (reMap.get("updated").equals("true")) {
	 		 				infoMap.put("uarAssKey", reMap.get("uarAssKey").toString());
	 	 				}
	 	 				// 2-2. 업데이트되지 않은 경우 continue
	 	 				else {
	 	 					continue loop;
	 	 				}
	 	 			}

	 	 			String uarDepNam = fmAssetDAO.fmasset001_asset_grpcode01(uarDepCod);
	 	 			String uarSubNam = fmAssetDAO.fmasset001_asset_grpcode02(uarSubCod);

	 	 			/*
							manufacturer	productlName	produVersion	osversion		appurl
					6 DB	uar_eqp_nam						uar_val_cl0
					7 SE	uar_val_cl0		uar_val_cl1										uar_val_cl4
					8 NE	uar_val_cl0		uar_val_cl1
					9 SS	uar_val_cl0		uar_val_cl1
					10 DC					uar_val_cl0		[보존년한, 문서번호, 문서명]
					11 SW									uar_val_cl3		uar_val_cl0		uar_val_cl1
					12 TE					uar_val_cl0		uar_val_cl1
					13 PH	uar_eqp_nam		uar_val_cl0		uar_val_cl1
					17 SD	uar_val_cl0		uar_val_cl1		uar_val_cl2
					*/

	 	 			infoMap.put("uarIp", (String) map.get("totalIP"));
					infoMap.put("uarHost", (String) map.get("hostname"));
					infoMap.put("uarOs", (String) map.get("osname"));

					switch (catCod) {
					case "DB" :
						infoMap.put("uarEqpNam", (String) map.get("manufacturer"));
						infoMap.put("uarValCl0", (String) map.get("producVersion"));
						break;
					case "SE" :

						infoMap.put("uarEqpNam", (String) map.get("hostname"));
						infoMap.put("uarValCl0", (String) map.get("manufacturer"));
						infoMap.put("uarValCl1", (String) map.get("productlName"));
						infoMap.put("uarValCl2", (String) map.get("appurl"));
						break;
					case "NE" :
						infoMap.put("uarEqpNam", (String) map.get("hostname"));
						infoMap.put("uarValCl0", (String) map.get("manufacturer"));
						infoMap.put("uarValCl1", (String) map.get("productlName"));
						break;
					case "SS" :
						infoMap.put("uarEqpNam", (String) map.get("hostname"));
						infoMap.put("uarValCl0", (String) map.get("manufacturer"));
						infoMap.put("uarValCl1", (String) map.get("productlName"));
						break;
					case "DC" :
						//infoMap.put("uarEqpNam", (String) map.get("문서명"));
						infoMap.put("uarValCl0", (String) map.get("productlName"));
						//infoMap.put("uarValCl1", (String) map.get("보존년한"));
						//infoMap.put("uarValCl2", (String) map.get("문서번호"));
						break;
					case "SW" :
						infoMap.put("uarEqpNam", (String) map.get("hostname"));
						infoMap.put("uarValCl0", (String) map.get("osversion"));
						infoMap.put("uarValCl1", (String) map.get("appurl"));
						infoMap.put("uarValCl2", (String) map.get("producVersion"));
						break;
					case "TE" :
						infoMap.put("uarEqpNam", (String) map.get("hostname"));
						infoMap.put("uarValCl0", (String) map.get("productlName"));
						infoMap.put("uarValCl1", (String) map.get("producVersion"));
						break;
					case "PH" :
						infoMap.put("uarEqpNam", (String) map.get("manufacturer"));
						infoMap.put("uarValCl0", (String) map.get("productlName"));
						infoMap.put("uarValCl1", (String) map.get("producVersion"));
						break;
					case "SD" :
						infoMap.put("uarEqpNam", (String) map.get("hostname"));
						infoMap.put("uarValCl0", (String) map.get("manufacturer"));
						infoMap.put("uarValCl1", (String) map.get("productlName"));
						infoMap.put("uarValCl2", (String) map.get("producVersion"));
						break;
					}

	 	 			// 3. 위험도, 그룹코드, 그룹명 생성
	 	 			// 기밀성(1/2/3) + 무결성(1/2/3) + 가용성(1/2/3)
					int sum = Integer.valueOf((String) map.get("confidentiality"))
							+ Integer.valueOf((String) map.get("integrity"))
							+ Integer.valueOf((String) map.get("fusibility"));

					uarAppTot = String.valueOf(sum);

					if (sum >= 8) {
						uarAssLvl = "H(3)";
					}
					else if (sum >= 5) {
						uarAssLvl = "M(2)";
					}
					else {
						uarAssLvl = "L(1)";
					}


					// 4.그룹코드, 그룹명 생성
					EgovMap emap = new EgovMap();
	 	 			if (catCod.equals("TE")) { // OS
	 	 				emap = fmAssetDAO.fmasset001_rsk_os_info((String) map.get("osname"));
	 	 			}
	 	 			else {
	 	 				emap = fmAssetDAO.fmasset001_rsk_code_info((String) map.get("assetTypeCode"));
	 	 			}

					if (emap != null) {
						uarRskGrp = (String) emap.get("uarRskGrp");
						uarRskNam = (String) emap.get("uarRskNam");

						// 그룹코드 = 부서코드 + 서비스코드 + 자산유형 + 보안영향도
						uarGrpCod = new StringBuffer()
							.append(uarDepCod)
							.append("_")
							.append(uarSubCod)
							.append("_")
							.append(uarRskGrp)
							.append("_")
							.append(uarAssLvl).toString();

						// 그룹명 = 부서명 + 서비스명 + 자산유형 + 보안영향도
						uarGrpNam = new StringBuffer()
							.append(uarDepNam)
							.append("_")
							.append(uarSubNam)
							.append("_")
							.append(uarRskNam)
							.append("_")
							.append(uarAssLvl).toString();
					}


	 	 			infoMap.put("uarRgtId", (String) map.get("createID"));
	 				infoMap.put("uarRgtMdh", (String) map.get("createDate"));
	 				infoMap.put("uarUptId", (String) map.get("updateID"));
	 				infoMap.put("uarUseYn", (String) map.get("useYN"));

	 				infoMap.put("uarSvrEtc", (String) map.get("assetSvcDetl"));
	 				infoMap.put("uarDepCod", (String) map.get("manageDeptCode"));
	 				infoMap.put("uarOwnId", (String) map.get("manageChargeUserID"));
	 				infoMap.put("uarAdmId", (String) map.get("manageOfficerUserID"));
	 				infoMap.put("uarOprCod", (String) map.get("operateDeptCode"));
	 				infoMap.put("uarUseId", (String) map.get("operateChargeUserID"));
	 				infoMap.put("uarPicId", (String) map.get("operateOfficerUserID"));

	 				infoMap.put("uarCatCod", (String) map.get("assetClassficationCode"));
	 				infoMap.put("uarAssGbn", (String) map.get("assetTypeCode"));
	 				infoMap.put("uarAssNam", (String) map.get("assetName"));
	 				infoMap.put("uarDtlExp", (String) map.get("useDetail"));
	 				infoMap.put("uarAdmPos", (String) map.get("equipSite"));
	 				infoMap.put("uarRskGrp", uarRskGrp);
	 				infoMap.put("uarRskNam", uarRskNam);

	 				infoMap.put("uarAppCon", (String) map.get("confidentiality"));
	 				infoMap.put("uarAppItg", (String) map.get("integrity"));
	 				infoMap.put("uarAppAvt", (String) map.get("fusibility"));
	 				infoMap.put("uarAudYn", (String) map.get("ISAMSYN"));
	 				infoMap.put("uarSmpYn", (String) map.get("samplingYN"));
	 				infoMap.put("uarInfYn", (String) map.get("infrastructureYN"));
	 				infoMap.put("uarPrvYn", (String) map.get("personalDataYN"));

	 				infoMap.put("uarSubCod", uarSubCod);
	 				infoMap.put("uarSubNam", uarSubNam);
	 				infoMap.put("uarGrpCod", uarGrpCod);
	 				infoMap.put("uarGrpNam", uarGrpNam);
	 				infoMap.put("uarAssLvl", uarAssLvl);
	 				infoMap.put("uarAppTot", uarAppTot);

	 	 			assetList.add(infoMap);

	 	 			// 5. 그룹 생성
	 	 			if (fmAssetDAO.fm_asset001_groupCdCount(uarGrpCod) == 0) {
	 	 				infoMap.put("uagGrpKey", commonUtilDAO.getGrpCodSeq());
	 	 				localDAO.insertAssetGroup(infoMap);
	 	 			}
	 	 		}

				// 6. INSERT / UPDATE 자산
				localDAO.updateAsset(assetList);
	 	 	}

		}
	 	finally {
		 	stopWatch.stop();
		 	log.info(stopWatch.prettyPrint());
	 	}
	}


	/*
	 * 2017-04-28
	 * Smart Guard 진단관리
	 */
	public void diagnosisJob2() throws SQLException {

		try {
			stopWatch.start();

			List<Map<String, String>> itemInfoList = new ArrayList<Map<String, String>>();
			List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
			List<Map<String, String>> srlList = new ArrayList<Map<String, String>>();
			//List<Map<String, String>> assetCodeList = new ArrayList<Map<String, String>>();

			HashSet<String> grpSet = new HashSet<String>();
			HashSet<String> assetCodeSet = new HashSet<String>();

			// 현재 주기
			EgovMap mcyMap = ismsBatchService.selectLastManagementCycle();
			String manCyl = (String) mcyMap.get("ummManCyl");
			String rgtId = "1";

			// 1. 진단 코드
			List<?> legacyItemInfoList = legacyDAO.legacyDiagnosisItemInfoList();
	 	 	if (legacyItemInfoList.size() > 0) {
	 	 		for (int i = 0; i < legacyItemInfoList.size() ; i++) {
	 	 			EgovMap map = (EgovMap)legacyItemInfoList.get(i);
	 	 			Map<String, String> emap = new HashMap<String, String>();

	 	 			String urvRskItm = new StringBuffer()
	 	 					.append((String) map.get("userCode"))
	 	 					.append(" ")
	 	 					.append((String) map.get("diagnosisItemName"))
	 	 					.toString();

	 	 			// U, M, D
	 	 			String urvRskImp = "";
	 	 			switch (map.get("diagnosisItemRiskGrade").toString()) {
	 	 			case "1" : urvRskImp = "D"; break;
	 	 			case "2" : urvRskImp = "M"; break;
	 	 			case "3" : urvRskImp = "U"; break;
	 	 			}

	 	 			emap.put("urvVlbCod", (String) map.get("diagnosisItemCode"));
	 	 			emap.put("urvRskItm", urvRskItm);
	 	 			emap.put("urvRskDiv", (String) map.get("userClass"));
	 	 			emap.put("urvRskImp", urvRskImp);
	 	 			emap.put("urvRskOrd", String.valueOf(i));
	 	 			emap.put("urvRskSrc", "SSR");
	 	 			emap.put("urvRgtId", rgtId);

	 	 			itemInfoList.add(emap);
	 	 		}
	 	 		localDAO.updateUwoRskVlb(itemInfoList);

	 	 	}

	 	 	// 2. 진단 상세 결과
			List<?> legacyResultList = legacyDAO.legacyDiagnosisResultList();
			if (legacyResultList.size() > 0) {
	 	 		for (int i = 0; i < legacyResultList.size() ; i++) {
	 	 			EgovMap map = (EgovMap)legacyResultList.get(i);
	 	 			Map<String, String> emap = new HashMap<String, String>();

	 	 			// assetcode
	 	 			assetCodeSet.add(map.get("assetCode").toString());

	 	 			String rst = "";
	 	 			switch (map.get("diagnosisResult").toString()) {
	 	 			case "PASS" : rst = "Y"; break;
	 	 			case "FAIL" : rst = "N"; break;
	 	 			default : rst = "NA"; break;
	 	 			}

	 	 			emap.put("manCylKey", manCyl);
	 	 			emap.put("assCod", (String) map.get("assetCode"));
	 	 			emap.put("vlbCod", (String) map.get("diagnosisItemCode"));
	 	 			emap.put("rst", rst);
	 	 			emap.put("urrRgtId", rgtId);
	 	 			emap.put("urrRskTyp", "A");

	 	 			resultList.add(emap);
	 	 		}

				fmRiskmDAO.fm_riskm006_rskm_insert(resultList);

				/*
				 * 2017-04-07, 샘플링 대상 Y 제외
	 	 		// update uwo_ass_mtr smp_yn = Y
				// 취약점 진단 자산의 경우 샘플링 대상을 Y로 변경
				for (String item: assetCodeSet) {
					Map<String, String> assetMap = new HashMap<String, String>();
					assetMap.put("uarAssCod", item);
					assetMap.put("uarUptId", rgtId);
					assetCodeList.add(assetMap);

	 	 			grpSet.add(localDAO.getAssetGroupCode(assetMap));
				}
				fmRiskmDAO.fm_riskm006_rskm_asset_smp_update_y(assetCodeList);
				*/


				// update uwo_rsk_srl
				for (String item: grpSet) {
					Map<String, String> srlMap = new HashMap<String, String>();
					srlMap.put("manCylKey", manCyl);
					srlMap.put("urrRskTyp", "A");
					srlMap.put("uagGrpCod", item);
					srlMap.put("doNotDelete", "1");
					srlList.add(srlMap);
				}
				fmRiskmDAO.fm_riskm006_rskm_srl_insert(srlList);
	 	 	}
		}
	 	finally {
		 	stopWatch.stop();
		 	log.info(stopWatch.prettyPrint());
	 	}
	}
}
