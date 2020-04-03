/**
 ***********************************
 * @source CommonCodeService.java
 * @date 2014. 10. 20.
 * @project isms3_godohwa
 * @description
 ***********************************
 */
package com.uwo.isms.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.uwo.isms.domain.FileVO;
import com.uwo.isms.domain.SearchVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;


public interface CommonUtilService {

	FileVO getFileName(String key);

	void insertAlarm(Map map);

	EgovMap getSendUserInfo(String key);

	EgovMap getRecvUserInfo(String userKey);

	List getAllNoworkUser(String manCyl);

	List getDivisionUser(Map map);

	List<?> userList(SearchVO searchVO);

	List searchUserByKeyword(String keyword);

	List searchUserByInUserKey(List<String> userKeys);

	public InputStream decryptFileDAC(MultipartFile file) throws Exception;

	public void insertPolarisConverter(String key);

	public String getPolarisConverterId(String key);

	public EgovMap getUccComCodeInfo(Map<String, String> map);

	String getExcelCellValue(Cell cell);

	String getApproval(String code);
}
