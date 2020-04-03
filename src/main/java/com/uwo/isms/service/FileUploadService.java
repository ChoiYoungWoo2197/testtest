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
package com.uwo.isms.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.uwo.isms.domain.FileVO;
import com.uwo.isms.domain.SearchVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * 파일업로드에 관한 인터페이스 클래스를 정의한다.
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
public interface FileUploadService {
	
	/**
	 * 파일을 업로드한다.
	 * @param request
	 * @return List<String>
	 * @throws Exception
	 */
	public List<FileVO> fileuplaod(HttpServletRequest request) throws Exception;

	/**
	 * @param trcKey
	 * @return
	 */
	public List<FileVO> selectFileList(String code, String tblGbn, String conGbn, String bcyCod) throws Exception;

	public List<FileVO> getFileList(FileVO fileVO) throws Exception;

	public List<FileVO> getFileList2(FileVO fileVO) throws Exception;

	public List<FileVO> getFileListByCurrentCycle(FileVO fileVO) throws Exception;

	public List<FileVO> getFileListByCurrentCycle2(FileVO fileVO) throws Exception;

	public List<FileVO> selectFileList_B(String code, String tblGbn, String conGbn, String bcyCod) throws Exception;

	public List<FileVO> saveFile(FileVO fileVO, HttpServletRequest httpServletRequest) throws Exception;

	public void storeFile(FileVO fileVO) throws Exception;

	public List<FileVO> uploadFile(FileVO fileVO, HttpServletRequest httpServletRequest) throws Exception;
	
	public void delFile(String key) throws Exception;

	public void delFile(String[] keys) throws Exception;

	public String boardKey(String key);

	public List<?> selectProofFileList(String utwWrkKey, String tblGbn,	String conGbn, String company);

	public List<?> selectProofFileListByTrcKey(SearchVO searchVO);

	public List getFileName(String fKey);
	
}
