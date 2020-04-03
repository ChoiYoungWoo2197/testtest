package com.uwo.isms.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;







//import com.uwo.isms.common.BoardVO;
import com.uwo.isms.dao.FMBasicDAO;
import com.uwo.isms.service.FMBasicService;
import com.uwo.isms.web.FMSetupController;
import com.uwo.isms.domain.BoardVO;
import com.uwo.isms.domain.FileVO;
//import egovframework.com.sym.cal.service.RestdeVO;
import com.uwo.isms.domain.SearchVO;


@Service("fmBasicService")
public class FMBasicServiceImpl implements FMBasicService {
	
	Logger log = LogManager.getLogger(FMSetupController.class);

	@Resource(name="fmBasicDAO")
	private FMBasicDAO fmBasicDAO;
	
	@Override
	public List<?> fm_file(FileVO fvo) {
		return fmBasicDAO.fm_file(fvo);
	}

	@Override
	public List<?> fm_basic001_list(SearchVO searchVO) {
		return fmBasicDAO.fm_basic001_list(searchVO);
	}

	@Override
	public int fm_basic001_cnt(SearchVO searchVO) {
		return fmBasicDAO.fm_basic001_cnt(searchVO);
	}

	@Override
	public BoardVO fm_basic001_read(BoardVO vo) {
		return fmBasicDAO.fm_basic001_read(vo);
	}
	
	@Override
	public List<?> fm_basic002_list(SearchVO searchVO) {
		return fmBasicDAO.fm_basic002_list(searchVO);
	}

	@Override
	public int fm_basic002_cnt(SearchVO searchVO) {
		return fmBasicDAO.fm_basic002_cnt(searchVO);
	}

	@Override
	public BoardVO fm_basic002_read(BoardVO vo) {
		return fmBasicDAO.fm_basic002_read(vo);
	}

	@Override
	public List<?> fm_basic003_list(SearchVO searchVO) {
		return fmBasicDAO.fm_basic003_list(searchVO);
	}

	@Override
	public List<?> fmBasic003_DetailList(SearchVO searchVO) {
		return fmBasicDAO.fmBasic003_DetailList(searchVO);
	}

	@Override
	public List<?> fmBasic003_MergeList(SearchVO searchVO) {
		return fmBasicDAO.fmBasic003_MergeList(searchVO);
	}

	@Override
	public List<?> getCntrList(Map map) {
		return fmBasicDAO.getCntrList(map);
	}

	@Override
	public List<?> fm_basic003_stdlist() {
		return fmBasicDAO.fm_basic003_stdlist();
	}

}
