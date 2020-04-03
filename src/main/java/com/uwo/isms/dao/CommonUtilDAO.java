/**
 ***********************************
 * @source CommonCodeDAO.java
 * @date 2014. 10. 20.
 * @project isms3_godohwa
 * @description
 ***********************************
 */
package com.uwo.isms.dao;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;









import com.uwo.isms.domain.FileVO;
import com.uwo.isms.domain.SearchVO;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Repository("commonUtilDAO")
public class CommonUtilDAO extends EgovAbstractDAO {

	public FileVO getFileName(String key) {
		return (FileVO)select("commonUtil.QR_GET_FILENAME",key);
	}

	public void insertAlarm(Map map) {
		insert("commonUtil.QR_INSERT_ALARM",map);
	}

	public EgovMap getSendUserInfo(String key) {
		return (EgovMap)select("commonUtil.QR_GET_SENDUSER",key);
	}

	public EgovMap getRecvUserInfo(String userKey) {
		return (EgovMap)select("commonUtil.QR_GET_RECVUSER",userKey);
	}

	public List getAllNoworkUser(String manCyl) {
		return list("commonUtil.QR_GET_RECVUSER_LIST",manCyl);
	}

	public List getDivisionUser(Map map) {
		return list("commonUtil.QR_GET_DIVUSER_LIST",map);
	}

	public List<?> userList(SearchVO searchVO) {
		return list("commonUtil.QR_GET_USER_LIST",searchVO);
	}

	public List searchUserByKeyword(String keyword) {
		return list("commonUtil.QR_GET_USER_LIST_BY_KEYWORD", keyword);
	}

	public List searchUserByInUserKey(List<String> userKeys) {
		return list("commonUtil.QR_GET_USER_LIST_BY_IN_USERKEYS", userKeys);
	}

	public void writeFileTable(FileVO vo){
		insert("fileup.QR_FILEUP001_A", vo);
	}

	public String getCocCodSeq() {
		return (String)select("commonUtil.QR_GET_COC_COD_SEQ");
	}

	public String getAssCodSeq() {
		return (String)select("commonUtil.QR_GET_ASS_COD_SEQ");
	}

	public String getGrpCodSeq() {
		return (String)select("commonUtil.QR_GET_GRP_COD_SEQ");
	}

	public String getSroCodSeq() {
		return (String)select("commonUtil.QR_GET_SRO_COD_SEQ");
	}

	public String getFltCodSeq() {
		return (String)select("commonUtil.QR_GET_FLT_COD_SEQ");
	}

	public void insertPolarisConverter(String key) {
		insert("commonUtil.QR_INSERT_POLARIS_CONVERT", key);
	}

	public String getPolarisConverterId(String key) {
		return (String)select("commonUtil.QR_GET_POLARIS_CONVERT_ID", key);
	}

	/*
	 * 2016-11-01
	 * 통제항목과 부서명으로 wrkKey 가져
	 */
	public String getWrkKeyByCtrAndDep(Map<String, String> map) {
		return (String) select("commonUtil.getWrkKeyByCtrAndDep", map);
	}

	public EgovMap getUccComCodeInfo(Map<String, String> map) {
		return (EgovMap) select("commonUtil.getUccComCodeInfo", map);
	}

	/*
	 * 2017-09-25, get 관리자 key
	 */
	public List<?> getAdminUserKeys() {
		return list("commonUtil.QR_GET_ADMIN_LIST", null);
	}

	/*
	 * 2018-05-11 s, 기본 결재담당자
	 */
	public String getApproval(String code) {
		return (String) select("fmMwork.QR_SELECT_COMMON_APPROVAL", code);
	}
}
