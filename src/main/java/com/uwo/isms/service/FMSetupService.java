package com.uwo.isms.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.uwo.isms.domain.BoardVO;
import com.uwo.isms.domain.CycleVO;
import com.uwo.isms.domain.FileVO;
import com.uwo.isms.domain.SampleDocVO;
import com.uwo.isms.domain.SearchVO;
import com.uwo.isms.domain.UserVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface FMSetupService {

	public List<?> testAjax(String id);

	public List<?> testGrid();

	public List<?> fm_setup001_list(SearchVO searchVO);

	public int fm_setup001_cnt(SearchVO searchVO);

	public void fm_setup001_write(BoardVO vo, List<FileVO> list);

	public BoardVO fm_setup001_read(BoardVO vo);

	public void fm_setup001_update(BoardVO vo, List<FileVO> list);

	public void fm_setup001_delete(BoardVO vo);

	public List<?> fm_setup002_list(SearchVO searchVO);

	public int fm_setup002_cnt(SearchVO searchVO);

	public void fm_setup002_write(BoardVO vo, List<FileVO> list);

	public BoardVO fm_setup002_read(BoardVO vo);

	public void fm_setup002_update(BoardVO vo, List<FileVO> list);

	public void fm_setup002_delete(BoardVO vo);

	public List<?> fm_setup012_list(SearchVO searchVO);

	public int fm_setup012_cnt(SearchVO searchVO);

	public List<?> fm_setup007(SearchVO vo);

	public void fm_setup007_edit(UserVO vo);

	public void fm_setup007_insert(UserVO vo);

	public void fm_setup007_del(String id);

	public EgovMap fm_setup013_read(String usr_key);

	public List<?> fm_setup013_dept(Map param);

	public void fm_setup013_update(UserVO vo);

	public int fm_setup007_cnt(SearchVO searchVO);

	public EgovMap fm_setup007_popup(String usrKey);

	public List<?> fm_setup007_member(SearchVO searchVO);

	public List<?> getUsrList(Map<String, String> map);

	public List<?> getYmdList(Map map);

	public void updateYmd(Map map);

	public List<?> fm_file(FileVO fvo);

	public List<?> fm_setup008_list(SearchVO searchVO);

	public int fm_setup008_cnt(SearchVO searchVO);

	public EgovMap fm_setup008_codeInfo(SearchVO searchVO);

	public void fm_setup008_codeInfo_insert(EgovMap map);

	public void fm_setup008_codeInfo_update(EgovMap map);

	public void fmSetup007_fail_clean(String key);

	public int fmSetup007_duplicate_test(String uId);

	public int fm_setup011_cnt(SearchVO searchVO);

	public List<?> getcontrollist(SearchVO searchVO);

	public List<?> getloginlist(SearchVO searchVO);

	public List<?> getaccountlist(SearchVO searchVO);

	public List<?> fm_setup016_mnu_list(SearchVO searchVO);

	public Object fm_setup016_node_list(String ummMnuKey);

	public int fm_setup016_mnn_cnt(SearchVO searchVO);

	public EgovMap fm_setup016_mnu_info(String ummMnuKey);

	public void fm_setup016_mnu_insert(Map<String, Object> map);

	public void fm_setup016_mnu_update(Map<String, Object> map);

	public List<?> fm_setup017_auh_list(SearchVO searchVO);

	public EgovMap fm_setup017_auh_info(String uatAuhKey);

	public List<?> fm_setup017_map_list(String uamAuhKey);

	public List<?> fm_setup017_node_list(String uamAuhKey);

	public String fm_setup017_auh_insert(Map<String, Object> map);

	public void fm_setup017_auh_update(Map<String, Object> map);

	public int getloginlist_count(SearchVO searchVO);

	public int getaccountlist_count(SearchVO searchVO);

	public int fm_setup018_cnt(SearchVO searchVO);

	public List<?> fm_setup018_list(SearchVO searchVO);

	public String fm_setup018_insert(Map<String, Object> map);

	public EgovMap fm_setup018_info(Map<String, String> map);

	public String fm_setup018_update(Map<String, Object> map);

	public List<?> fm_setup018_node_list(String code);

	public List<?> fm_setup018_node_list_sort(Map<String, String> map);

	public List<?> fm_setup019_cal(Map<String, Object> map);

	public List<?> fm_setup019(SearchVO searchVO);

	public int fm_setup019_cnt(SearchVO searchVO);

	public void fm_setup019_sav_ymd(Map map);

	public void fm_setup019_re_ymd(Map map);

	public List<?> fm_setup019_Year();

	public List<?> fm_setup019_Month();

	public int fm_setup017_auh_cnt(SearchVO searchVO);

	public String fmSetup007_pwd_update(Map<String, String> map);

	public List<?> fm_setup021_list(SearchVO searchVO);

	public List<?> fm_setup021_dept_list(Map<String, String> map);

	public List<?> fm_setup021_node_list(Map<String, String> map);

	public EgovMap fm_setup021_info(Map<String, String> map);

	public void fm_setup021_map_update(Map<String, Object> map);

	public int fm_setup020_cnt(SearchVO searchVO);

	public List<?> fm_setup020_list(SearchVO searchVO);

	public Map<String, String> fm_setup020_view(Map<String, Object> map);

	public String fm_setup020_insert(Map<String, Object> map, List<FileVO> list);

	public String fm_setup020_update(Map<String, Object> map, List<FileVO> list);

	/**
	 * 2018-05-10 s, 사용자 업로드
	 */
	public String fm_setup007_excel_insert(HttpServletRequest req) throws Exception;
}