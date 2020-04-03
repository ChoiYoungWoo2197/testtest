/*
 * Copyright 2011 MOPAS(Ministry of Public Administration and Security).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.uwo.isms.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.uwo.isms.common.CommonConfig;
import com.uwo.isms.common.Constants;
import com.uwo.isms.dao.CommonUtilDAO;
import com.uwo.isms.domain.LoginVO;
import com.uwo.isms.util.EgovUserDetailsHelper;
import com.uwo.isms.util.FileUpload;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.uwo.isms.dao.FMSetupDAO;
import com.uwo.isms.dao.FileUploadDAO;
import com.uwo.isms.domain.FileVO;
import com.uwo.isms.domain.SearchVO;
import com.uwo.isms.service.FileUploadService;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;


/**
 * 파일업로드에 관한 비지니스 클래스를 정의한다.
 * @author 실행환경 개발팀 이영진
 * @since 2011.07.11
 * @version 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2011.07.11  이영진          최초 생성
 * 
 * </pre>
 */
@Service("fileUploadService")
public class FileUploadServiceImpl implements FileUploadService{

	//@Resource(name = "fileUploadProperties")
	//Properties fileuploadProperties;
	
	@Resource(name = "propertiesService")
    protected EgovPropertyService propertyService;
	
	@Resource(name="fileUpDAO")
	private FileUploadDAO fileUpDAO;

	@Autowired
	private CommonUtilDAO commonUtilDAO;

	@Autowired
	private HttpServletRequest httpServletRequest;

	/**
	 * @deprecated
	 */
	public List<FileVO> fileuplaod(HttpServletRequest request) throws Exception{
		final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
		// extract files
		final Map<String, MultipartFile> files = multiRequest.getFileMap();

		String uploadPath = propertyService.getString("uploadpath");
		File saveFolder = new File(uploadPath);
		String fileName = null;
		List<FileVO> result = new ArrayList<FileVO>();
		// 디렉토리 생성
		boolean isDir = false;

		if (!saveFolder.exists() || saveFolder.isFile()) {
			saveFolder.mkdirs();
		}

		if (!isDir) {

			Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
			MultipartFile file;
			String filePath;

			while (itr.hasNext()) {
				
				Entry<String, MultipartFile> entry = itr.next();
				file = entry.getValue();
				fileName = file.getOriginalFilename();
				if (!"".equals(fileName)) {
					FileVO vo = new FileVO();
					vo.setUmf_svr_pth(uploadPath);
					vo.setUmf_svr_fnm("Board");
					vo.setUmf_con_fnm(file.getOriginalFilename());
					vo.setUmf_fle_ext(file.getContentType());
					vo.setUmf_fle_siz(Long.toString(file.getSize()));
					// 파일 전송
					filePath = uploadPath + "\\" + fileName;
					file.transferTo(new File(filePath));
					result.add(vo);
				}
			}
		}
		return result;
	}

	public List<FileVO> getFileList(FileVO fileVO) {
		if (StringUtils.isBlank(fileVO.getUmf_tbl_gbn()) || StringUtils.isBlank(fileVO.getUmf_con_gbn())
				|| StringUtils.isBlank(fileVO.getUmf_con_key())) {
			return new ArrayList<FileVO>();
		}
		return fileUpDAO.selectFileList(fileVO);
	}

	public List<FileVO> getFileList2(FileVO fileVO) {
		if (StringUtils.isBlank(fileVO.getUmf_tbl_gbn()) || StringUtils.isBlank(fileVO.getUmf_con_gbn())
				|| StringUtils.isBlank(fileVO.getUmf_con_key())) {
			return new ArrayList<FileVO>();
		}
		return fileUpDAO.selectFileList2(fileVO);
	}

	public List<FileVO> getFileListByCurrentCycle(FileVO fileVO) {
		fileVO.setUmf_bcy_cod((String) httpServletRequest.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		return getFileList(fileVO);
	}

	public List<FileVO> getFileListByCurrentCycle2(FileVO fileVO) {
		fileVO.setUmf_bcy_cod((String) httpServletRequest.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		return getFileList2(fileVO);
	}

	/**
	 * @deprecated
	 */
	public List<FileVO> selectFileList(String code, String tblGbn, String conGbn, String bcyCod) throws Exception {
		FileVO vo = new FileVO();
		vo.setUmf_con_key(code);
		vo.setUmf_tbl_gbn(tblGbn);
		vo.setUmf_con_gbn(conGbn);
		vo.setUmf_bcy_cod(bcyCod);

		List<FileVO> fileVOList = getFileList(vo);
		return fileVOList;
	}

	public List<FileVO> saveFile(FileVO fileVO, HttpServletRequest httpServletRequest) throws Exception {
		// 파일 업로드
		List<FileVO> uploadedFileVOList = uploadFile(fileVO, httpServletRequest);
		// DB에 저장
		for (FileVO uploadedFileVO : uploadedFileVOList) {
			uploadedFileVO.setUmf_con_key(fileVO.getUmf_con_key());
			storeFile(uploadedFileVO);
		}
		return uploadedFileVOList;
	}

	public void storeFile(FileVO fileVO) throws Exception {
		commonUtilDAO.writeFileTable(fileVO);
	}

	public List<FileVO> uploadFile(FileVO fileVO, HttpServletRequest httpServletRequest) throws Exception {

		FileUpload fileUpload = new FileUpload();
		String uploadPath = propertyService.getString("uploadpath") + File.separator
				+ fileVO.getUmf_tbl_gbn() + File.separator + fileVO.getUmf_con_gbn();

		httpServletRequest.setAttribute("uploadPath", uploadPath);

		return fileUpload.fileuplaod(httpServletRequest, fileVO.getUmf_tbl_gbn(), fileVO.getUmf_con_gbn());
	}
	
	@Override
	public List<FileVO> selectFileList_B(String code, String tblGbn, String conGbn, String bcyCod) throws Exception {
		FileVO vo = new FileVO();
		vo.setUmf_con_key(code);
		vo.setUmf_tbl_gbn(tblGbn);
		vo.setUmf_con_gbn(conGbn);
		vo.setUmf_bcy_cod(bcyCod);
		
		List<FileVO> fileVOList = fileUpDAO.selectFileList_B(vo);
		return fileVOList;
	}

	@Override
	public void delFile(String key) throws Exception {
		fileUpDAO.delFile(key);
	}

	public void delFile(String[] keys) throws Exception {
		for (String key : keys) {
			delFile(key);
		}
	}

	@Override
	public String boardKey(String key) {
		return fileUpDAO.boardKey(key);
	}

	@Override
	public List<?> selectProofFileList(String utwWrkKey, String tblGbn,	String conGbn, String company) {
		
		FileVO vo = new FileVO();
		vo.setUmf_con_key(utwWrkKey);
		vo.setUmf_tbl_gbn(tblGbn);
		vo.setUmf_con_gbn(conGbn);
		vo.setCompany(company);
		
		List<FileVO> fileVOList = fileUpDAO.selectProofFileList(vo);
		return fileVOList;
	}

	@Override
	public List<?> selectProofFileListByTrcKey(SearchVO searchVO) {
		
		List<FileVO> fileVOList = fileUpDAO.selectProofFileListByTrcKey(searchVO);
		return fileVOList;
	}

	@Override
	public List getFileName(String fKey) {
		return fileUpDAO.getFileName(fKey);
	}
}
