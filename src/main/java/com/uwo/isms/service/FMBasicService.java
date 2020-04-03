package com.uwo.isms.service;

import java.util.List;
import java.util.Map;

import com.uwo.isms.domain.BoardVO;
import com.uwo.isms.domain.FileVO;
import com.uwo.isms.domain.SearchVO;

public interface FMBasicService {
	
	public List<?> fm_file(FileVO fvo);

	public List<?> fm_basic001_list(SearchVO searchVO);

	public int fm_basic001_cnt(SearchVO searchVO);
	
	public BoardVO fm_basic001_read(BoardVO vo);
	
	public List<?> fm_basic002_list(SearchVO searchVO);

	public int fm_basic002_cnt(SearchVO searchVO);
	
	public BoardVO fm_basic002_read(BoardVO vo);

	public List<?> fm_basic003_list(SearchVO searchVO);

	public List<?> fmBasic003_DetailList(SearchVO searchVO);

	public List<?> fmBasic003_MergeList(SearchVO searchVO);

	public List<?> getCntrList(Map map);

	public List<?> fm_basic003_stdlist();
}
