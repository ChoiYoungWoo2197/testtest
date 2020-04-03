/**
 ***********************************
 * @source CommonCodeServiceImpl.java
 * @date 2014. 10. 20.
 * @project isms3_godohwa
 * @description
 ***********************************
 */
package com.uwo.isms.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import SCSL.*;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.uwo.isms.dao.CommonUtilDAO;
import com.uwo.isms.domain.FileVO;
import com.uwo.isms.domain.SearchVO;
import com.uwo.isms.service.CommonUtilService;
import com.uwo.isms.web.FMSetupController;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;


@Service("commonUtilService")
public class CommonUtilServiceImpl implements CommonUtilService {

	Logger log = LogManager.getLogger(FMSetupController.class);

	@Resource(name="commonUtilDAO")
	private CommonUtilDAO commonUtilDAO;

	@Autowired
	protected EgovPropertyService propertyService;

	@Override
	public FileVO getFileName(String key) {
		return commonUtilDAO.getFileName(key);
	}

	@Override
	public void insertAlarm(Map map) {
		commonUtilDAO.insertAlarm(map);
	}

	@Override
	public EgovMap getSendUserInfo(String key) {
		return commonUtilDAO.getSendUserInfo(key);
	}

	@Override
	public EgovMap getRecvUserInfo(String userKey) {
		return commonUtilDAO.getRecvUserInfo(userKey);
	}

	@Override
	public List getAllNoworkUser(String manCyl) {
		return commonUtilDAO.getAllNoworkUser(manCyl);
	}

	@Override
	public List getDivisionUser(Map map) {
		return commonUtilDAO.getDivisionUser(map);
	}

	@Override
	public List<?> userList(SearchVO searchVO) {
		return commonUtilDAO.userList(searchVO);
	}

	public List searchUserByKeyword(String keyword) {
		return commonUtilDAO.searchUserByKeyword(keyword);
	}

	public List searchUserByInUserKey(List<String> userKeys) {
		return commonUtilDAO.searchUserByInUserKey(userKeys);
	}

	/*
	 * SKB : softcamp 문서 암호화 관련
	 */
	@Override
	public InputStream decryptFileDAC(MultipartFile file) throws Exception {
		String uploadPath = propertyService.getString("uploadpath");
		String srcFilePath = uploadPath + File.separator + RandomStringUtils.randomAlphanumeric(10);
		String dstFilePath = uploadPath + File.separator + RandomStringUtils.randomAlphanumeric(10);
		InputStream inputStream = null;

		if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            File srcFile = new File(srcFilePath);
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(srcFile));
            stream.write(bytes);
            stream.close();

    		SLDsFile sFile = new SLDsFile();
     		sFile.SettingPathForProperty("/home/tomadm/softcamp/02_Module/02_ServiceLinker/softcamp.properties");
    		int retVal = sFile.CreateDecryptFileDAC(
    				"/home/tomadm/softcamp/04_KeyFile/keyDAC_SVR0.sc",
    				"SECURITYDOMAIN",
    				srcFilePath,
    				dstFilePath);
    		File dstFile = new File(dstFilePath);
    		if (retVal == 0) {
    			inputStream = FileUtils.openInputStream(dstFile);
    		}
    		else {
    			inputStream = FileUtils.openInputStream(srcFile);
    		}

    		if (srcFile.exists()) {
    			srcFile.delete();
    		}
    		if (dstFile.exists()) {
    			dstFile.delete();
    		}
		}

		return inputStream;
	}

	/*
	 * SKB : Polaris Converter
	 */
	@Override
	public void insertPolarisConverter(String key) {
		commonUtilDAO.insertPolarisConverter(key);
	}

	@Override
	public String getPolarisConverterId(String key) {
		return commonUtilDAO.getPolarisConverterId(key);
	}

	@Override
	public EgovMap getUccComCodeInfo(Map<String, String> map) {
		return commonUtilDAO.getUccComCodeInfo(map);
	}

	/*
	 * 2018-05-11 s, 엑셀의 셀 값을 가져옴. default: ""
	 */
	@Override
	public String getExcelCellValue(Cell cell) {
		String result = "";
		try {
			if (cell != null) {
				if (cell.getCellType() != Cell.CELL_TYPE_STRING) {
					cell.setCellType(Cell.CELL_TYPE_STRING);
				}
				result = cell.getStringCellValue();
			}
			return result;
		} catch (IllegalArgumentException ex) {
			return result;
		}
	}

	/*
	 * 2018-05-11 s, 기본 결재담당자
	 */
	@Override
	public String getApproval(String code) {
		return commonUtilDAO.getApproval(code);
	}
}
