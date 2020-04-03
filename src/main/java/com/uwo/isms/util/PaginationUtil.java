package com.uwo.isms.util;

import com.uwo.isms.domain.SearchVO;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

public class PaginationUtil {
	private PaginationInfo paginationInfo;
	
	public PaginationUtil(SearchVO searchVO, int totCnt){
		paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());
		paginationInfo.setTotalRecordCount(totCnt);

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		searchVO.setPaginationInfo(paginationInfo);
	}
	
	public PaginationInfo getPageData() {
		return paginationInfo;
	}
}
