package com.uwo.isms.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

//import com.uwo.isms.common.BoardVO;



import com.uwo.isms.common.Constants;
import com.uwo.isms.domain.BoardVO;
import com.uwo.isms.domain.FileVO;
//import egovframework.com.sym.cal.service.RestdeVO;
import com.uwo.isms.domain.SearchVO;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Repository("fmBoardDAO")
public class FMBoardDAO extends EgovAbstractDAO {

	public List<?> fm_file(FileVO fvo) {
		return list("fileup.QR_FILE_LIST", fvo);
	}

	public List<?> fm_board001_list(SearchVO searchVO) {
		List<?> list = list("fmBoard.QR_BOARD001_A", searchVO); //
		return list;
	}

	public int fm_board001_cnt(SearchVO searchVO) {
		return (Integer) select("fmBoard.QR_BOARD001_B", searchVO);
	}

	public int fm_board001_write(BoardVO vo) {
		insert("fmBoard.QR_BOARD001_C", vo);
		int key = (Integer) select("fmBoard.QR_BOARD001_H");
		/*
		for (int i = 0; i < list.size(); i++) {
			FileVO fvo = list.get(i);
			fvo.setUmf_con_key(String.valueOf(key));
			insert("fileup.QR_FILEUP001_A", fvo);
		}
		*/
		return key;		
	}
/*	
	public void fm_board001_write(Map<String, Object> map) {
		insert("fmBoard.QR_BOARD001_C", map);
	}
*/
	public BoardVO fm_board001_read(BoardVO vo) {
		return (BoardVO) select("fmBoard.QR_BOARD001_D", vo);
	}

	public int fm_board001_update(BoardVO vo) {
		update("fmBoard.QR_BOARD001_E", vo);
		int key = vo.getUbm_brd_key();
		return key;
	}

	public void fm_board001_delete(BoardVO vo) {
		update("fmBoard.QR_BOARD001_F", vo);

		FileVO fileVO = new FileVO();
		fileVO.setUmf_tbl_gbn(Constants.FILE_GBN_BRD);
		fileVO.setUmf_con_gbn(Constants.FILE_CON_NTC);
		fileVO.setUmf_con_key(Integer.toString(vo.getUbm_brd_key()));
		update("fileup.QR_FILE_DELETE_BYCON", fileVO);
	}

	public List<?> fm_board002_list(SearchVO searchVO) {
		List<?> list = list("fmBoard.QR_BOARD002_A", searchVO); //
		return list;
	}

	public int fm_board002_cnt(SearchVO searchVO) {
		return (Integer) select("fmBoard.QR_BOARD002_B", searchVO);
	}

	public int fm_board002_write(BoardVO vo) {
		insert("fmBoard.QR_BOARD002_C", vo);
		int key = (Integer) select("fmBoard.QR_BOARD002_H");
		return key;
	}

	public BoardVO fm_board002_read(BoardVO vo) {
		return (BoardVO) select("fmBoard.QR_BOARD002_D", vo);
	}

	public int fm_board002_update(BoardVO vo) {
		update("fmBoard.QR_BOARD002_E", vo);
		int key = vo.getUbm_brd_key();
		return key;
	}

	public void fm_board002_delete(BoardVO vo) {
		update("fmBoard.QR_BOARD002_F", vo);

		FileVO fileVO = new FileVO();
		fileVO.setUmf_tbl_gbn(Constants.FILE_GBN_BRD);
		fileVO.setUmf_con_gbn(Constants.FILE_CON_SCR);
		fileVO.setUmf_con_key(Integer.toString(vo.getUbm_brd_key()));
		update("fileup.QR_FILE_DELETE_BYCON", fileVO);
	}

	public List<?> fm_board003_list(SearchVO searchVO) {
		List<?> list = list("fmBoard.QR_BOARD003_A", searchVO); //
		return list;
	}

	public int fm_board003_cnt(SearchVO searchVO) {
		return (Integer) select("fmBoard.QR_BOARD003_B", searchVO);
	}

	public int fm_board003_write(BoardVO vo) {		
		insert("fmBoard.QR_BOARD003_C", vo);
		int key = (Integer) select("fmBoard.QR_BOARD003_H");
		return key;
	}

	public BoardVO fm_board003_read(BoardVO vo) {
		return (BoardVO) select("fmBoard.QR_BOARD003_D", vo);
	}

	public int fm_board003_update(BoardVO vo) {
		update("fmBoard.QR_BOARD003_E", vo);
		int key = vo.getUbm_brd_key();
		return key;
	}

	public void fm_board003_delete(BoardVO vo) {
		update("fmBoard.QR_BOARD003_F", vo);

		FileVO fileVO = new FileVO();
		fileVO.setUmf_tbl_gbn(Constants.FILE_GBN_BRD);
		fileVO.setUmf_con_gbn(Constants.FILE_CON_DAT);
		fileVO.setUmf_con_key(Integer.toString(vo.getUbm_brd_key()));
		update("fileup.QR_FILE_DELETE_BYCON", fileVO);
	}

	public List<?> fm_board004_list(SearchVO searchVO) {
		List<?> list = list("fmBoard.QR_BOARD004_A", searchVO); //
		return list;
	}

	public int fm_board004_cnt(SearchVO searchVO) {
		return (Integer) select("fmBoard.QR_BOARD004_B", searchVO);
	}

	public int fm_board004_write(BoardVO vo) {
		
		insert("fmBoard.QR_BOARD004_C", vo);		
		int key = (Integer) select("fmBoard.QR_BOARD004_H");
		return key;
	}

	public BoardVO fm_board004_read(BoardVO vo) {
		return (BoardVO) select("fmBoard.QR_BOARD004_D", vo);
	}

	public void fm_board004_update(BoardVO vo) {
		update("fmBoard.QR_BOARD004_E", vo);
		/*
		for (int i = 0; i < list.size(); i++) {
			FileVO fvo = list.get(i);
			fvo.setUmf_con_key(String.valueOf(vo.getUbm_brd_key()));
			insert("fileup.QR_FILEUP001_A", fvo);
		}
		*/
	}

	public void fm_board004_delete(BoardVO vo) {
		update("fmBoard.QR_BOARD004_F", vo);

		FileVO fileVO = new FileVO();
		fileVO.setUmf_tbl_gbn(Constants.FILE_GBN_BRD);
		fileVO.setUmf_con_gbn(Constants.FILE_CON_QNA);
		fileVO.setUmf_con_key(Integer.toString(vo.getUbm_brd_key()));
		update("fileup.QR_FILE_DELETE_BYCON", fileVO);
	}

	public List<?> fm_board005_list(SearchVO searchVO) {
		List<?> list = list("fmBoard.QR_BOARD005_A", searchVO); //
		return list;
	}

	public int fm_board005_cnt(SearchVO searchVO) {
		return (Integer) select("fmBoard.QR_BOARD005_B", searchVO);
	}

	public int fm_board005_write(BoardVO vo) {
		
		insert("fmBoard.QR_BOARD005_C", vo);		
		int key = (Integer) select("fmBoard.QR_BOARD005_H");
		return key;
	}

	public BoardVO fm_board005_read(BoardVO vo) {
		return (BoardVO) select("fmBoard.QR_BOARD005_D", vo);
	}

	public int fm_board005_update(BoardVO vo) {
		update("fmBoard.QR_BOARD005_E", vo);
		int key = vo.getUbm_brd_key();
		return key;
	}

	public void fm_board005_delete(BoardVO vo) {
		update("fmBoard.QR_BOARD005_F", vo);

		FileVO fileVO = new FileVO();
		fileVO.setUmf_tbl_gbn(Constants.FILE_GBN_BRD);
		fileVO.setUmf_con_gbn(Constants.FILE_CON_FAQ);
		fileVO.setUmf_con_key(Integer.toString(vo.getUbm_brd_key()));
		update("fileup.QR_FILE_DELETE_BYCON", fileVO);
	}

	public List getSvcList() {
		List<?> list = list("fmBoard.QR_GET_SVC_LIST");
		return list;
	}
	
	public EgovMap fm_board001_auh_info(String ubmBrdKey) {
		return (EgovMap)select("fmBoard.QR_BOARD001_AUH_INFO", ubmBrdKey);
	}

	public void fm_copyToFAQ(BoardVO vo) {
		insert("fmBoard.QR_BOARD004_I", vo);
	}

	public void saveAnswer(Map map) {
		update("fmBoard.QR_BOARD004_J", map);
	}

	public EgovMap getCntrData(Map map) {
		EgovMap eMap = (EgovMap)select("fmBoard.QR_CNTR_DATA", map);	//
		return eMap;
	}
}