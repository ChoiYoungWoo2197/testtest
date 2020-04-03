package com.uwo.isms.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.uwo.isms.domain.FileVO;
import com.uwo.isms.domain.SearchVO;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Repository("fileUpDAO")
public class FileUploadDAO  extends EgovAbstractDAO{
	
	public void file_upload(FileVO vo) {		
		insert("fileup.QR_FILEUP001_A",vo);
	}

	/**
	 * file list select
	 * @param vo
	 * @return
	 */
	public List<FileVO> selectFileList(FileVO vo) {
		List<FileVO> fileVOList = (List<FileVO>) list("fileup.QR_FILE_LIST", vo);
		return fileVOList;
	}

	public List<FileVO> selectFileList2(FileVO vo) {
		List<FileVO> fileVOList = (List<FileVO>) list("fileup.QR_FILE_LIST2", vo);
		return fileVOList;
	}
	
	public List<FileVO> selectFileList_B(FileVO vo) {
		List<FileVO> fileVOList = (List<FileVO>) list("fileup.QR_FILE_LIST_B", vo);
		return fileVOList;
	}

	public void delFile(String key) {
		update("fileup.QR_FILE_DELETE",key);
	}

	/**
	 * UMF_TBL_GBN, UMF_CON_GBN, UMF_CON_KEY 조건으로 파일을 삭제한다.
	 */
	public void delFile(FileVO fileVO) {
		update("fileup.QR_FILE_DELETE_BYCON", fileVO);
	}

	public String boardKey(String key) {		
		return (String)select("fileup.QR_FILE_BOARDKEY",key);
	}

	public List<FileVO> selectProofFileList(FileVO vo) {
		List<FileVO> fileVOList = (List<FileVO>) list("fileup.QR_PROOFFILE_LIST", vo);
		return fileVOList;
	}

	public List<FileVO> selectProofFileListByTrcKey(SearchVO searchVO) {
		List<FileVO> fileVOList = (List<FileVO>) list("fileup.QR_PROOFFILE_BY_TRCKEY_LIST", searchVO);
		return fileVOList;
	}

	public String getFileSvrName(String fKey) {
		return (String)select("fileup.QR_GET_SVR_FILE_NAME",fKey);
	}
	
	public String getFileConName(String fKey) {
		return (String)select("fileup.QR_GET_CON_FILE_NAME",fKey);
	}

	public List getFileName(String fKey) {
		return list("fileup.QR_GET_FILE_NAME",fKey);
	}
}
