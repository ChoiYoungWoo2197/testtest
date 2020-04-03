package com.uwo.isms.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.uwo.isms.domain.BoardVO;
import com.uwo.isms.domain.FileVO;
import com.uwo.isms.domain.SearchVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface FMBoardService {

	List<?> fm_file(FileVO fvo);

	List<?> fm_board001_list(SearchVO searchVO);

	int fm_board001_cnt(SearchVO searchVO);

	public EgovMap fm_board001_auh_info(String ubmBrdKey);

	void fm_board001_write(BoardVO vo, List<FileVO> list, HttpServletRequest req);

	BoardVO fm_board001_read(BoardVO vo);

	void fm_board001_update(BoardVO vo, List<FileVO> list);

	void fm_board001_delete(BoardVO vo);

	List<?> fm_board002_list(SearchVO searchVO);

	int fm_board002_cnt(SearchVO searchVO);

	void fm_board002_write(BoardVO vo, List<FileVO> list, HttpServletRequest req);

	BoardVO fm_board002_read(BoardVO vo);

	void fm_board002_update(BoardVO vo, List<FileVO> list);

	void fm_board002_delete(BoardVO vo);

	List<?> fm_board003_list(SearchVO searchVO);

	int fm_board003_cnt(SearchVO searchVO);

	void fm_board003_write(BoardVO vo, List<FileVO> list, HttpServletRequest req);

	BoardVO fm_board003_read(BoardVO vo);

	void fm_board003_update(BoardVO vo, List<FileVO> list);

	void fm_board003_delete(BoardVO vo);

	List<?> fm_board004_list(SearchVO searchVO);

	int fm_board004_cnt(SearchVO searchVO);

	void fm_board004_write(BoardVO vo, List<FileVO> list, HttpServletRequest req);

	BoardVO fm_board004_read(BoardVO vo);

	void fm_board004_update(BoardVO vo);

	void fm_board004_delete(BoardVO vo);

	List<?> fm_board005_list(SearchVO searchVO);

	int fm_board005_cnt(SearchVO searchVO);

	void fm_board005_write(BoardVO vo, List<FileVO> list, HttpServletRequest req);

	BoardVO fm_board005_read(BoardVO vo);

	void fm_board005_update(BoardVO vo, List<FileVO> list);

	void fm_board005_delete(BoardVO vo);

	List getSvcList();

	void fm_copyToFAQ(BoardVO vo);

	void saveAnswer(Map map);

	public EgovMap getCntrData(Map map);

	void sendMail(BoardVO vo, List<FileVO> list, HttpServletRequest req);

	/**
	 * 2018-04-18 s, 글쓰기 페이지 접근권한 확인
	 * @param HttpServletRequest
	 * @return Boolean
	 * @throws Exception
	 */
	public Boolean getAuthoritiesWriteChk(HttpServletRequest req) throws Exception;
}
