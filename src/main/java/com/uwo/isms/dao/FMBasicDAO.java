package com.uwo.isms.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

//import com.uwo.isms.common.BoardVO;




import com.uwo.isms.domain.BoardVO;
import com.uwo.isms.domain.FileVO;
//import egovframework.com.sym.cal.service.RestdeVO;
import com.uwo.isms.domain.SearchVO;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;

@Repository("fmBasicDAO")
public class FMBasicDAO extends EgovAbstractDAO  {
	
	public List<?> fm_file(FileVO fvo) {
		return list("fileup.QR_FILE_LIST",fvo);
	}

	public List<?> fm_basic001_list(SearchVO searchVO) {
		List<?> list = list("fmBasic.QR_BASIC001_A", searchVO); //
		return list;
	}

	public int fm_basic001_cnt(SearchVO searchVO) {
		return (Integer) select("fmBasic.QR_BASIC001_B", searchVO);
	}

	public BoardVO fm_basic001_read(BoardVO vo) {
		//update("fmBasic.QR_BASIC001_D", vo);
		return (BoardVO) select("fmBasic.QR_BASIC001_C", vo);
	}
	
	public List<?> fm_basic002_list(SearchVO searchVO) {
		List<?> list = list("fmBasic.QR_BASIC002_A", searchVO); //
		return list;
	}

	public int fm_basic002_cnt(SearchVO searchVO) {
		return (Integer) select("fmBasic.QR_BASIC002_B", searchVO);
	}

	public BoardVO fm_basic002_read(BoardVO vo) {
		//update("fmBasic.QR_BASIC002_D", vo);
		return (BoardVO) select("fmBasic.QR_BASIC002_C", vo);
	}

	public List<?> fm_basic003_list(SearchVO searchVO) {
		List<?> list = list("fmBasic.QR_BASIC003_A", searchVO); //
		return list;
	}

	public List<?> fmBasic003_DetailList(SearchVO searchVO) {
		List<?> list = list("fmBasic.QR_BASIC003_B", searchVO); //
		return list;
	}

	public List<?> fmBasic003_MergeList(SearchVO searchVO) {
		List<?> list = list("fmBasic.QR_BASIC003_C", searchVO); //
		return list;
	}

	public List<?> getCntrList(Map map) {
		List<?> list = list("fmBasic.QR_BASIC003_D", map); //
		return list;
	}

	public List<?> fm_basic003_stdlist() {
		List<?> list = list("fmBasic.QR_BASIC003_E"); //
		return list;
	}
}
