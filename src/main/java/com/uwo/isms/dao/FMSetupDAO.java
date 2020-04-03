package com.uwo.isms.dao;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.uwo.isms.domain.ApprMapVO;
import com.uwo.isms.domain.BoardVO;
import com.uwo.isms.domain.CycleVO;
import com.uwo.isms.domain.FileVO;
import com.uwo.isms.domain.SampleDocMapVO;
import com.uwo.isms.domain.SampleDocVO;
import com.uwo.isms.domain.SearchVO;
import com.uwo.isms.domain.UserVO;
import com.uwo.isms.domain.WorkVO;
import com.uwo.isms.util.EgovDateUtil;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Repository("fmSetupDAO")
public class FMSetupDAO extends EgovAbstractDAO {

	Logger log = LogManager.getLogger(FMSetupDAO.class);


	public List<?> testAjax(String id) {
		// TODO Auto-generated method stub
		return list("fmSetup.TESTAJAX", id);
	}

	public List<?> testGrid() {
		// TODO Auto-generated method stub
		return list("fmSetup.TEST_GRID");
	}

	public List<?> fm_setup001_list(SearchVO searchVO) {
		List<?> list = list("fmSetup.QR_SETUP001_A", searchVO); //
		return list;
	}

	public int fm_setup001_cnt(SearchVO searchVO) {
		return (Integer) select("fmSetup.QR_SETUP001_B", searchVO);
	}

	public void fm_setup001_write(BoardVO vo, List<FileVO> list) {
		int key = (Integer)select("fmSetup.QR_SETUP001_H");
		vo.setUbm_brd_key(key);
		insert("fmSetup.QR_SETUP001_C", vo);
		for (int i = 0; i < list.size(); i++) {
			FileVO fvo = list.get(i);
			fvo.setUmf_con_key(String.valueOf(key));
			insert("fileup.QR_FILEUP001_A", fvo);
		}
	}

	public BoardVO fm_setup001_read(BoardVO vo) {
		//update("fmSetup.QR_SETUP001_G", vo);
		return (BoardVO) select("fmSetup.QR_SETUP001_D", vo);
	}

	public void fm_setup001_update(BoardVO vo, List<FileVO> list) {

		update("fmSetup.QR_SETUP001_E", vo);
		for (int i = 0; i < list.size(); i++) {
			FileVO fvo = list.get(i);
			fvo.setUmf_con_key(String.valueOf(vo.getUbm_brd_key()));
			insert("fileup.QR_FILEUP001_A", fvo);
		}
	}

	public void fm_setup001_delete(BoardVO vo) {
		update("fmSetup.QR_SETUP001_F", vo);

		FileVO fileVO = new FileVO();
		fileVO.setUmf_tbl_gbn("BRD");
		fileVO.setUmf_con_gbn("3");
		fileVO.setUmf_con_key(Integer.toString(vo.getUbm_brd_key()));
		update("fileup.QR_FILE_DELETE_BYCON", fileVO);
	}

	public List<?> fm_setup002_list(SearchVO searchVO) {
		List<?> list = list("fmSetup.QR_SETUP002_A", searchVO); //
		return list;
	}

	public int fm_setup002_cnt(SearchVO searchVO) {
		return (Integer) select("fmSetup.QR_SETUP002_B", searchVO);
	}

	public void fm_setup002_write(BoardVO vo, List<FileVO> list) {
		int key = (Integer)select("fmSetup.QR_SETUP002_H");
		vo.setUbm_brd_key(key);
		insert("fmSetup.QR_SETUP002_C", vo);
		for (int i = 0; i < list.size(); i++) {
			FileVO fvo = list.get(i);
			fvo.setUmf_con_key(String.valueOf(key));
			insert("fileup.QR_FILEUP001_A", fvo);
		}
	}

	public BoardVO fm_setup002_read(BoardVO vo) {
		return (BoardVO) select("fmSetup.QR_SETUP002_D", vo);
	}

	public void fm_setup002_update(BoardVO vo, List<FileVO> list) {

		update("fmSetup.QR_SETUP002_E", vo);
		for (int i = 0; i < list.size(); i++) {
			FileVO fvo = list.get(i);
			fvo.setUmf_con_key(String.valueOf(vo.getUbm_brd_key()));
			insert("fileup.QR_FILEUP001_A", fvo);
		}
	}

	public void fm_setup002_delete(BoardVO vo) {
		update("fmSetup.QR_SETUP002_F", vo);

		FileVO fileVO = new FileVO();
		fileVO.setUmf_tbl_gbn("BRD");
		fileVO.setUmf_con_gbn("4");
		fileVO.setUmf_con_key(Integer.toString(vo.getUbm_brd_key()));
		update("fileup.QR_FILE_DELETE_BYCON", fileVO);
	}



	public List<?> fm_setup012_list(SearchVO searchVO) {
		List<?> list = list("fmSetup.QR_SETUP012_A", searchVO); //
		return list;
	}

	public int fm_setup012_cnt(SearchVO searchVO) {
		return (Integer) select("fmSetup.QR_SETUP012_B", searchVO);
	}



	public List<?> fm_setup007(SearchVO vo) {
		List<?> list = list("fmSetup.QR_SETUP007_A", vo);
		return list;
	}

	public void fm_setup007_edit(UserVO vo) {
		update("fmSetup.QR_SETUP007_D", vo);
		//insert("fmLog.QR_LOG012",vo);
	}

	public void fm_setup007_insert(UserVO vo) {
		insert("fmSetup.QR_SETUP007_E", vo);
	}

	public void fm_setup007_del(String id) {
		update("fmSetup.QR_SETUP007_F", id);
		insert("fmLog.QR_LOG013",id);
	}

	public List<?> getUsrList(Map<String, String> map) {
		return list("fmSetup.QR_GET_USR_LIST", map);
	}

	public List<?> fm_setup007_member(SearchVO searchVO) {
		List<?> list = list("fmSetup.QR_SETUP007_AGN",searchVO);
		return list;
	}

	public int fmSetup007_pwd_chk(Map<String, String> map) {
		return (Integer)select("fmSetup.QR_SETUP007_H", map);
	}

	public void fmSetup007_pwd_update(Map<String, String> map) {
		update("fmSetup.QR_SETUP007_I", map);
	}

	public List<?> fm_file(FileVO fvo) {
		return list("fileup.QR_FILE_LIST",fvo);
	}

	public EgovMap fm_setup013_read(String usr_key) {
		return (EgovMap)select("fmSetup.QR_SETUP013_A",usr_key);
	}

	public List<?> fm_setup013_dept(Map param) {
		return (List<?>)list("fmSetup.QR_SETUP013_B",param);
	}

	public void fm_setup013_update(UserVO vo) {
		update("fmSetup.QR_SETUP013_C",vo);
		//insert("fmLog.QR_LOG012",vo);
	}



	public int fm_setup007_cnt(SearchVO searchVO) {
		return (Integer)select("fmSetup.QR_SETUP007_B",searchVO);
	}

	public EgovMap fm_setup007_popup(String usrKey) {
		return (EgovMap)select("fmSetup.QR_SETUP007_C",usrKey);
	}


	public void fmSetup007_fail_clean(Map<String, String> map) {
		update("fmSetup.QR_SETUP007_F", map);
	}

	public List<?> getYmdList(Map map) {
		List<?> list = list("fmSetup.QR_SETUP012_A", map);
		return list;
	}

	public void updateYmd(Map map) {
		update("fmSetup.QR_SETUP012_B",map);
	}

	public int fm_setup008_cnt(SearchVO searchVO) {
		int result = (Integer) select("fmSetup.QR_SETUP008_A", searchVO);
		return result;
	}

	public List<?> fm_setup008_list(SearchVO searchVO) {
		List<?> list = list("fmSetup.QR_SETUP008_B", searchVO);
		return list;
	}

	public EgovMap fm_setup008_codeInfo(SearchVO searchVO) {
		return (EgovMap) select("fmSetup.QR_SETUP008_C", searchVO);
	}

	public void fm_setup008_codeInfo_insert(EgovMap map) {
		insert("fmSetup.QR_SETUP008_D", map);
	}

	public void fm_setup008_codeInfo_update(EgovMap map) {
		update("fmSetup.QR_SETUP008_E", map);
	}

	public int fmSetup007_duplicate_test(String uId) {
		int result = (Integer) select("fmSetup.QR_SETUP007_G", uId);
		return result;
	}



	public int fm_setup011_cnt(SearchVO searchVO) {
		return (Integer) select("fmSetup.QR_SETUP011_B", searchVO);
	}

	public List<?> fm_setup011_getaccountlist(SearchVO searchVO) {
		List<?> list = list("fmSetup.QR_SETUP011_A", searchVO);
		return list;
	}

	public List<?> fm_setup014_getcontrollist(SearchVO searchVO) {
		List<?> list = list("fmSetup.QR_SETUP014_A", searchVO);
		return list;
	}

	public List<?> fm_setup010_getloginlist(SearchVO searchVO) {
		List<?> list = list("fmSetup.QR_SETUP010_A", searchVO);
		return list;
	}

	public List<?> fm_setup016_mnu_list(SearchVO searchVO) {
		return list("fmSetup.QR_SETUP016_MNU_LIST", searchVO);
	}

	public List<?> fm_setup016_node_list(String ummMnuKey) {
		return list("fmSetup.QR_SETUP016_NODE_LIST", ummMnuKey);
	}

	public int fm_setup016_mnn_cnt(SearchVO searchVO) {
		return (Integer)select("fmSetup.QR_SETUP016_MNU_CNT", searchVO);
	}

	public EgovMap fm_setup016_mnu_info(String ummMnuKey) {
		return (EgovMap)select("fmSetup.QR_SETUP016_MNU_INFO", ummMnuKey);
	}

	public void fm_setup016_mnu_insert(Map<String, Object> map) {
		insert("fmSetup.QR_SETUP016_MNU_INSERT", map);
	}

	public void fm_setup016_mnu_update(Map<String, Object> map) {
		update("fmSetup.QR_SETUP016_MNU_UPDATE", map);
	}

	public List<?> fm_setup017_auh_list(SearchVO searchVO) {
		return list("fmSetup.QR_SETUP017_AUH_LIST", searchVO);
	}

	public EgovMap fm_setup017_auh_info(String uamAuhKey) {
		return (EgovMap)select("fmSetup.QR_SETUP017_AUH_INFO", uamAuhKey);
	}

	public List<?> fm_setup017_map_list(String uamAuhKey) {
		return list("fmSetup.QR_SETUP017_MAP_LIST", uamAuhKey);
	}

	public List<?> fm_setup017_node_list(String uamAuhKey) {
		return list("fmSetup.QR_SETUP017_NODE_LIST", uamAuhKey);
	}

	public String fm_setup017_auh_insert(Map<String, Object> map) {
		return (String) insert("fmSetup.QR_SETUP017_AUH_INSERT", map);
	}

	public void fm_setup017_auh_update(Map<String, Object> map) {
		update("fmSetup.QR_SETUP017_AUH_UPDATE", map);
	}

	public void fm_setup017_map_insert(Map<String, String> map) {
		insert("fmSetup.QR_SETUP017_MAP_INSERT", map);
	}

	public void fm_setup017_map_delete(String uamAuhKey) {
		delete("fmSetup.QR_SETUP017_MAP_DELETE", uamAuhKey);
	}

	public int getloginlist_count(SearchVO searchVO) {
		int cnt = (Integer)select("fmSetup.QR_SETUP010_B", searchVO);
		return cnt;
	}

	public int getaccountlist_count(SearchVO searchVO) {
		int cnt = (Integer)select("fmSetup.QR_SETUP011_B", searchVO);
		return cnt;
	}

	public List<?> fm_setup018_list(SearchVO searchVO) {
		return list("fmSetup.QR_SETUP018_LIST", searchVO);
	}

	public int fm_setup018_cnt(SearchVO searchVO) {
		return (Integer) select("fmSetup.QR_SETUP018_CNT", searchVO);
	}

	public EgovMap fm_setup018_info(Map<String, String> map) {
		return (EgovMap)select("fmSetup.QR_SETUP018_INFO", map);
	}

	public void fm_setup018_insert(Map<String, Object> map) {
		insert("fmSetup.QR_SETUP018_INSERT", map);
	}

	public void fm_setup018_update(Map<String, Object> map) {
		update("fmSetup.QR_SETUP018_UPDATE", map);
	}

	public int fm_setup018_cod_cnt(Map<String, Object> map) {
		return (Integer)select("fmSetup.QR_SETUP018_COD_CNT", map);
	}

	public List<?> fm_setup018_node_list(String code) {
		return list("fmSetup.QR_SETUP018_NODE_LIST", code);
	}
	public List<?> fm_setup018_node_list_sort(Map<String, String> map) {
		return list("fmSetup.QR_SETUP018_NODE_LIST_SORT", map);
	}

	public List<?> fm_setup019_cal(Map<String, Object> map) {
		return list("fmSetup.QR_SETUP019_CAL", map);
	}

	public List<?> fm_setup019(SearchVO searchVO) {
		return list("fmSetup.QR_SETUP019_LIST", searchVO);
	}

	public int fm_setup019_cnt(SearchVO searchVO) {
		return (Integer)select("fmSetup.QR_SETUP019_CNT", searchVO);
	}

	public void fm_setup019_sav_ymd(Map map) {
		update("fmSetup.QR_SETUP019_SAV_YMD",map);
	}

	public void fm_setup019_re_ymd(Map map) {
		update("fmSetup.QR_SETUP019_RE_YMD",map);
	}

	public List<?> fm_setup019_Year() {
		return list("fmSetup.QR_SETUP019_LIST_YEAR");
	}

	public List<?> fm_setup019_Month() {
		return list("fmSetup.QR_SETUP019_LIST_MONTH");
	}

	public int fm_setup017_auh_cnt(SearchVO searchVO) {
		return (Integer)select("fmSetup.QR_SETUP017_AUH_CNT", searchVO);
	}

	public void fm_setup018_insertMap(Map<String, Object> map) {
		insert("fmSetup.QR_SETUP018_INSERT_MAP", map);
	}

	public void fm_setup018_updateMap(Map<String, Object> map) {
		update("fmSetup.QR_SETUP018_UPDATE_MAP", map);
	}

	public EgovMap fm_setup018_info_Dept(Map<String, String> map) {
		return (EgovMap)select("fmSetup.QR_SETUP018_INFO_DEPT", map);
	}

	public EgovMap fm_setup018_info_stnd(Map<String, String> map) {
		return (EgovMap)select("fmSetup.QR_SETUP018_INFO_STND", map);
	}

	public int fm_setup018_stnd_count(Map<String, Object> map) {
		return (Integer) select("fmSetup.QR_SETUP018_STND_COUNT", map);
	}

	public void fm_setup018_insert_stnd(Map<String, Object> map) {
		insert("fmSetup.QR_SETUP018_INSERT_STND", map);
	}

	public void fm_setup018_update_stnd(Map<String, Object> map) {
		update("fmSetup.QR_SETUP018_UPDATE_STND", map);
	}

	public List<?> fm_setup021_list(SearchVO searchVO) {
		return list("fmSetup.QR_SETUP021_LIST", searchVO);
	}

	public List<?> fm_setup021_dept_list(Map<String, String> map) {
		return list("fmSetup.QR_SETUP021_DEPT_LIST", map);
	}

	public List<?> fm_setup021_node_list(Map<String, String> map) {
		return list("fmSetup.QR_SETUP021_NODE_LIST", map);
	}

	public EgovMap fm_setup021_info(Map<String, String> map) {
		return (EgovMap)select("fmSetup.QR_SETUP021_INFO", map);
	}

	public void fm_setup021_map_delete(String uomSvcCod) {
		delete("fmSetup.QR_SETUP021_MAP_DELETE", uomSvcCod);
	}

	public void fm_setup021_map_delete_with_svc_bcy(Map map) {
		delete("fmSetup.QR_SETUP021_MAP_DELETE_WITH_SVC_BCY", map);
	}

	public void fm_setup021_dep_delete(String uomDepCod) {
		delete("fmSetup.QR_SETUP021_DEP_DELETE", uomDepCod);
	}

	public int fm_setup020_cnt(SearchVO searchVO) {
		return (Integer) select("fmSetup.QR_SETUP020_CNT", searchVO);
	}

	public List<?> fm_setup020_list(SearchVO searchVO) {
		return list("fmSetup.QR_SETUP020_LIST", searchVO);
	}

	public Map<String, String> fm_setup020_view(Map<String, Object> map) {
		return (Map<String, String>) select("fmSetup.QR_SETUP020_VIEW", map);
	}

	public String fm_setup020_insert(Map<String, Object> map) {
		return (String) insert("fmSetup.QR_SETUP020_INSERT", map);
	}

	public void fm_setup020_update(Map<String, Object> map) {
		update("fmSetup.QR_SETUP020_UPDATE", map);
	}

	public void fm_setup021_member_update(Map<String, Object> map) {
		update("fmSetup.QR_SETUP021_M_UPDATE", map);
	}

	public void fm_setup021_member_re(Map<String, Object> map) {
		update("fmSetup.QR_SETUP021_M_RE_UPDATE", map);
	}

	public void fm_usr_log_insert(UserVO vo) {
		insert("fmLog.QR_LOG012",vo);
	}

	/**
	 * 2018-05-10 s, 사용자 엑셀 업로드
	 */
	public void batchUpdateUser(List<UserVO> list) {
		insert("fmSetup.batchUpdateUser", list);
	}


	public int fm_cnt_com_cod_prefix(Map<String, Object> map) {
		return (Integer)select("fmSetup.QR_CNT_COM_COD_PREFIX", map);
	}
	public int fm_cnt_com_cod_comp_name(Map<String, Object> map) {
		return (Integer)select("fmSetup.QR_CNT_COM_COD_COMP_NAME", map);
	}
}