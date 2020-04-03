package com.uwo.isms.service.impl;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.uwo.isms.common.Constants;
import com.uwo.isms.common.Crypto;
import com.uwo.isms.dao.CommonUtilDAO;
import com.uwo.isms.dao.FMSetupDAO;
import com.uwo.isms.domain.BoardVO;
import com.uwo.isms.domain.FileVO;
import com.uwo.isms.domain.SampleDocMapVO;
import com.uwo.isms.domain.SampleDocVO;
import com.uwo.isms.domain.SearchVO;
import com.uwo.isms.domain.UserVO;
import com.uwo.isms.service.CommonUtilService;
import com.uwo.isms.service.FMSetupService;
import com.uwo.isms.service.FileUploadService;
import com.uwo.isms.service.SendMailService;

import egovframework.rte.psl.dataaccess.util.EgovMap;


@Service("fmSetupService")
public class FMSetupServiceImpl implements FMSetupService {

	Logger log = LogManager.getLogger(FMSetupServiceImpl.class);

	@Resource(name="fmSetupDAO")
	private FMSetupDAO fmSetupDAO;

	@Resource(name = "commonUtilDAO")
	private CommonUtilDAO commonUtilDAO;

	@Resource(name = "fileUploadService")
	FileUploadService fileUploadServiceImpl;

	@Resource(name = "sendMailService")
	SendMailService sendMailService;

	@Autowired
	CommonUtilService commonUtilService;

	private static MessageDigestPasswordEncoder passwordEncoder = new MessageDigestPasswordEncoder("SHA-256");

	/*
	@Override
	public List<?> fm_setup001_list() {
		return systemDAO.fm_setup001_list();
	}
*/
	@Override
	public List<?> testAjax(String id) {

		return fmSetupDAO.testAjax(id);
	}

	@Override
	public List<?> testGrid() {

		return fmSetupDAO.testGrid();
	}

	@Override
	public List<?> fm_setup001_list(SearchVO searchVO) {
		return fmSetupDAO.fm_setup001_list(searchVO);
	}

	@Override
	public int fm_setup001_cnt(SearchVO searchVO) {
		return fmSetupDAO.fm_setup001_cnt(searchVO);
	}

	@Override
	public void fm_setup001_write(BoardVO vo, List<FileVO> list) {
		fmSetupDAO.fm_setup001_write(vo,list);
	}

	@Override
	public BoardVO fm_setup001_read(BoardVO vo) {
		return fmSetupDAO.fm_setup001_read(vo);
	}

	@Override
	public void fm_setup001_update(BoardVO vo, List<FileVO> list) {
		fmSetupDAO.fm_setup001_update(vo,list);
	}

	@Override
	public void fm_setup001_delete(BoardVO vo) {
		fmSetupDAO.fm_setup001_delete(vo);
	}

	@Override
	public List<?> fm_setup002_list(SearchVO searchVO) {
		return fmSetupDAO.fm_setup002_list(searchVO);
	}

	@Override
	public int fm_setup002_cnt(SearchVO searchVO) {
		return fmSetupDAO.fm_setup002_cnt(searchVO);
	}

	@Override
	public void fm_setup002_write(BoardVO vo, List<FileVO> list) {
		fmSetupDAO.fm_setup002_write(vo,list);
	}

	@Override
	public BoardVO fm_setup002_read(BoardVO vo) {
		return fmSetupDAO.fm_setup002_read(vo);
	}

	@Override
	public void fm_setup002_update(BoardVO vo, List<FileVO> list) {
		fmSetupDAO.fm_setup002_update(vo,list);
	}

	@Override
	public void fm_setup002_delete(BoardVO vo) {
		fmSetupDAO.fm_setup002_delete(vo);
	}

	@Override
	public List<?> fm_setup012_list(SearchVO searchVO) {
		return fmSetupDAO.fm_setup012_list(searchVO);
	}

	@Override
	public int fm_setup012_cnt(SearchVO searchVO) {
		return fmSetupDAO.fm_setup012_cnt(searchVO);
	}

	@Override
	public List<?> fm_setup007(SearchVO vo) {
		return fmSetupDAO.fm_setup007(vo);
	}

	@Override
	public void fm_setup007_edit(UserVO vo) {
		if (StringUtils.isNotEmpty(vo.getUumUsrPwd())) {
			String userPwd = passwordEncoder.encodePassword(vo.getUumUsrPwd(), null);
			vo.setUumUsrPwd(userPwd);
		}

		// 2016-07-26, 암호화를 제외한 로그 생성
		fmSetupDAO.fm_usr_log_insert(vo);

		// 2016-07-20, email, mobile, phone 암호화
 		if (StringUtils.isNotEmpty(vo.getUumMalAds())) {
 			String uumMalAds = Crypto.encAes256(vo.getUumMalAds());
 			vo.setUumMalAds(uumMalAds);
 		}
 		if (StringUtils.isNotEmpty(vo.getUumCelNum())) {
 			String uumCelNum = Crypto.encAes256(vo.getUumCelNum());
 			vo.setUumCelNum(uumCelNum);
 		}
 		if (StringUtils.isNotEmpty(vo.getUumTelNum())) {
			String uumTelNum = Crypto.encAes256(vo.getUumTelNum());
			vo.setUumTelNum(uumTelNum);
 		}

		fmSetupDAO.fm_setup007_edit(vo);
	}

	@Override
	public void fm_setup007_insert(UserVO vo) {
		if (StringUtils.isNotEmpty(vo.getUumUsrPwd())) {
			String userPwd = passwordEncoder.encodePassword(vo.getUumUsrPwd(), null);
			vo.setUumUsrPwd(userPwd);
		}

		// 2016-07-20, email, mobile, phone 암호화
 		if (StringUtils.isNotEmpty(vo.getUumMalAds())) {
 			String uumMalAds = Crypto.encAes256(vo.getUumMalAds());
 			vo.setUumMalAds(uumMalAds);
 		}
 		if (StringUtils.isNotEmpty(vo.getUumCelNum())) {
 			String uumCelNum = Crypto.encAes256(vo.getUumCelNum());
 			vo.setUumCelNum(uumCelNum);
 		}
 		if (StringUtils.isNotEmpty(vo.getUumTelNum())) {
			String uumTelNum = Crypto.encAes256(vo.getUumTelNum());
			vo.setUumTelNum(uumTelNum);
 		}

 		fmSetupDAO.fm_setup007_insert(vo);
	}

	@Override
	public void fm_setup007_del(String id) {
		fmSetupDAO.fm_setup007_del(id);
	}

	@Override
	public List<?> fm_setup007_member(SearchVO searchVO) {
		List<?> list = fmSetupDAO.fm_setup007_member(searchVO);
		return list;
	}

	@Override
	public List<?> getUsrList(Map<String, String> map) {
		return fmSetupDAO.getUsrList(map);
	}



	/**
	 *  insert 될 업무담당자 list
	 * @param SampleDocVO vo
	 * @return SampleDocMapVO mapVO
	 */
	private List<SampleDocMapVO> getWorkerList(SampleDocVO vo){

		String[] worker = vo.getUtmWrkId().split("\\,");
		String[] div = vo.getUtmDivCod().split("\\,");
		String[] svc = vo.getUtmSvcCod().split("\\,");
		String[] dep = vo.getUtmDepCod().split("\\,");
		int cnt = worker.length;

		List<SampleDocMapVO> workerList = new ArrayList<SampleDocMapVO>();

		for(int i=0; i<cnt; i++){
			if(!worker[i].equals("")){
				SampleDocMapVO mapVO = new SampleDocMapVO();

				mapVO.setUtmBcyCod(vo.getUtdBcyCod());
				mapVO.setUtmDelYn("N");
				mapVO.setUtmTrcKey(vo.getUtdTrcKey());
				mapVO.setUtmDwnYn("Y");
				mapVO.setUtmWrkId(worker[i]);
				mapVO.setUtmDivCod(div[i]);
				mapVO.setUtmSvcCod(svc[i]);
				mapVO.setUtmDepCod(dep[i]);
				mapVO.setUtmRgtId(vo.getUtdRgtId());
				mapVO.setUtmUptId(vo.getUtdUptId());

				workerList.add(mapVO);
			}
		}
		return workerList;
	}






	@Override
	public EgovMap fm_setup013_read(String usr_key) {
		return fmSetupDAO.fm_setup013_read(usr_key);
	}

	@Override
	public List<?> fm_setup013_dept(Map param) {
		return fmSetupDAO.fm_setup013_dept(param);
	}

	@Override
	public void fm_setup013_update(UserVO vo) {
		fmSetupDAO.fm_setup013_update(vo);
	}


	@Override
	public int fm_setup007_cnt(SearchVO searchVO) {
		return fmSetupDAO.fm_setup007_cnt(searchVO);
	}

	@Override
	public EgovMap fm_setup007_popup(String usrKey) {
		 EgovMap map = fmSetupDAO.fm_setup007_popup(usrKey);

    	// 2016-07-20, email, mobile, phone 복호화
 		if (map.get("uumMalAds") != null && map.get("uumMalAds") != "") {
 			String uumMalAds = Crypto.decAes256(map.get("uumMalAds").toString());
 			map.put("uumMalAds", uumMalAds);
 		}
 		if (map.get("uumCelNum") != null && map.get("uumCelNum") != "") {
 			String uumCelNum = Crypto.decAes256(map.get("uumCelNum").toString());
 			map.put("uumCelNum", uumCelNum);
 		}
 		if (map.get("uumTelNum") != null && map.get("uumTelNum") != "") {
			String uumTelNum = Crypto.decAes256(map.get("uumTelNum").toString());
			map.put("uumTelNum", uumTelNum);
 		}
 		return map;
	}


	@Override
	public void fmSetup007_fail_clean(String key) {
		// 2017-06-14, 사용자 아이디로 비밀번호를 초기화 함
		EgovMap userMap = this.fm_setup007_popup(key);
		String usrId = (String) userMap.get("uumUsrId");

		Map<String, String> map = new HashMap<String, String>();
    	String encodedPassword = passwordEncoder.encodePassword(usrId, null);
    	map.put("usrKey", key);
    	map.put("newPwd", encodedPassword);

		fmSetupDAO.fmSetup007_fail_clean(map);
	}

	@Override
	public List<?> getYmdList(Map map) {
		List<?> list = fmSetupDAO.getYmdList(map);
		return list;
	}

	@Override
	public void updateYmd(Map map) {
		fmSetupDAO.updateYmd(map);
	}

	@Override
	public List<?> fm_setup008_list(SearchVO searchVO) {
		return fmSetupDAO.fm_setup008_list(searchVO);
	}

	@Override
	public int fm_setup008_cnt(SearchVO searchVO) {
		return fmSetupDAO.fm_setup008_cnt(searchVO);
	}

	@Override
	public EgovMap fm_setup008_codeInfo(SearchVO searchVO) {
		return fmSetupDAO.fm_setup008_codeInfo(searchVO);
	}

	@Override
	public void fm_setup008_codeInfo_insert(EgovMap map) {
		fmSetupDAO.fm_setup008_codeInfo_insert(map);
	}

	@Override
	public void fm_setup008_codeInfo_update(EgovMap map) {
		fmSetupDAO.fm_setup008_codeInfo_update(map);
	}

	@Override
	public int fmSetup007_duplicate_test(String uId) {
		return fmSetupDAO.fmSetup007_duplicate_test(uId);
	}

	@Override
	public int fm_setup011_cnt(SearchVO searchVO) {
		return fmSetupDAO.fm_setup011_cnt(searchVO);
	}

	@Override
	public List<?> getcontrollist(SearchVO searchVO) {
		return fmSetupDAO.fm_setup014_getcontrollist(searchVO);
	}

	@Override
	public List<?> getloginlist(SearchVO searchVO) {
		return fmSetupDAO.fm_setup010_getloginlist(searchVO);
	}

	@Override
	public List<?> getaccountlist(SearchVO searchVO) {
		return fmSetupDAO.fm_setup011_getaccountlist(searchVO);
	}

	@Override
	public List<?> fm_setup016_mnu_list(SearchVO searchVO) {
		return fmSetupDAO.fm_setup016_mnu_list(searchVO);
	}

	@Override
	public List<?> fm_setup016_node_list(String ummMnuKey) {
		return fmSetupDAO.fm_setup016_node_list(ummMnuKey);
	}

	@Override
	public int fm_setup016_mnn_cnt(SearchVO searchVO) {
		return fmSetupDAO.fm_setup016_mnn_cnt(searchVO);
	}

	@Override
	public EgovMap fm_setup016_mnu_info(String ummMnuKey) {
		return fmSetupDAO.fm_setup016_mnu_info(ummMnuKey);
	}

	@Override
	public void fm_setup016_mnu_insert(Map<String, Object> map) {
		fmSetupDAO.fm_setup016_mnu_insert(map);
	}

	@Override
	public void fm_setup016_mnu_update(Map<String, Object> map) {
		fmSetupDAO.fm_setup016_mnu_update(map);
	}

	@Override
	public List<?> fm_setup017_auh_list(SearchVO searchVO) {
		return fmSetupDAO.fm_setup017_auh_list(searchVO);
	}

	@Override
	public EgovMap fm_setup017_auh_info(String uamAuhKey) {
		return fmSetupDAO.fm_setup017_auh_info(uamAuhKey);
	}

	@Override
	public List<?> fm_setup017_map_list(String uamAuhKey) {
		return fmSetupDAO.fm_setup017_map_list(uamAuhKey);
	}

	@Override
	public List<?> fm_setup017_node_list(String uamAuhKey) {
		return fmSetupDAO.fm_setup017_node_list(uamAuhKey);
	}

	@Override
	public String fm_setup017_auh_insert(Map<String, Object> map) {
		return fmSetupDAO.fm_setup017_auh_insert(map);
	}

	@Override
	public void fm_setup017_auh_update(Map<String, Object> map) {
		fmSetupDAO.fm_setup017_auh_update(map);
		fmSetupDAO.fm_setup017_map_delete(map.get("uatAuhKey").toString());
		if( map.get("uamMnuKey") != null) {
			String[] arrMnuKey = (String[]) map.get("uamMnuKey");
			String[] arrMnuRgt = (String[]) map.get("uamMnuRgt");
			for(String uamMnuKey : arrMnuKey) {
				Map<String, String> mnuMap = new HashMap<String, String>();
				mnuMap.put("uamAuhKey", map.get("uatAuhKey").toString());
				mnuMap.put("uamMnuKey", uamMnuKey);
				mnuMap.put("uamRgtId", map.get("uatRgtId").toString());

				// 2017-10-10 if null 추가
				if (arrMnuRgt != null) {
					// MENU RW 권한
					for (String uamMnuRgt : arrMnuRgt) {
						if (uamMnuKey.equals(uamMnuRgt)) {
							mnuMap.put("uamMnuRgt", "Y");
							break;
						}
					}
				}
				fmSetupDAO.fm_setup017_map_insert(mnuMap);
			}
		}
	}

	@Override
	public List<?> fm_file(FileVO fvo) {
		return fmSetupDAO.fm_file(fvo);
	}

	@Override
	public int getloginlist_count(SearchVO searchVO) {
		return fmSetupDAO.getloginlist_count(searchVO);
	}

	@Override
	public int getaccountlist_count(SearchVO searchVO) {
		return fmSetupDAO.getaccountlist_count(searchVO);
	}

	@Override
	public int fm_setup018_cnt(SearchVO searchVO) {
		return fmSetupDAO.fm_setup018_cnt(searchVO);
	}

	@Override
	public List<?> fm_setup018_list(SearchVO searchVO) {
		return fmSetupDAO.fm_setup018_list(searchVO);
	}

	@Override
	public List<?> fm_setup018_node_list(String code) {
		return fmSetupDAO.fm_setup018_node_list(code);
	}

	@Override
	public List<?> fm_setup018_node_list_sort(Map<String, String> map) {
		return fmSetupDAO.fm_setup018_node_list_sort(map);
	}

	@Override
	public EgovMap fm_setup018_info(Map<String, String> map) {
		if(map.get("uccFirCod").equals("DEPT")){
			return fmSetupDAO.fm_setup018_info_Dept(map);
		}else if(map.get("uccFirCod").equals("STND")){
			return fmSetupDAO.fm_setup018_info_stnd(map);
		}else{
			return fmSetupDAO.fm_setup018_info(map);
		}
	}

	@Override
	public String fm_setup018_insert(Map<String, Object> map) {
		if(fmSetupDAO.fm_setup018_cod_cnt(map) > 0) {
			//중복검사 - 코드
			return Constants.RETURN_DUP;
		}

		if(map.get("uccFirCod").equals("STND")){
			Map<String, Object> map_temp = new HashMap<String, Object>();
			map_temp.put("uccSndNam", map.get("uccSndNam"));
			map_temp.put("uccEtc", map.get("uccEtc"));

			if(fmSetupDAO.fm_cnt_com_cod_comp_name(map_temp) > 0) {
				//중복검사 - 컴플라이언스명
				return "DN";
			}else if(fmSetupDAO.fm_cnt_com_cod_prefix(map_temp) > 0) {
				//중복검사 - 접두어
				return "DP";
			}
		}
		fmSetupDAO.fm_setup018_insert(map);

		if(map.get("uccFirCod").equals("DEPT")){
			fmSetupDAO.fm_setup018_insertMap(map);
		}else if(map.get("uccFirCod").equals("STND")){
			map.put("hUccSndCod", map.get("uccSndCod"));
			fmSetupDAO.fm_setup018_insert_stnd(map);
		}
		return Constants.RETURN_SUCCESS;
	}

	@Override
	public String fm_setup018_update(Map<String, Object> map) {
		if(map.get("uccFirCod").equals("STND")){
			Map<String, Object> map_temp = new HashMap<String, Object>();
			map_temp.put("uccSndCod", map.get("hUccSndCod"));
			map_temp.put("uccEtc", map.get("uccEtc"));

			if(map.get("uccEtc").toString().length() <= 0) return "BP";
			if(fmSetupDAO.fm_cnt_com_cod_prefix(map_temp) > 0) {
				return "DP";
			}
		}
		fmSetupDAO.fm_setup018_update(map);

		if(map.get("uccFirCod").equals("DEPT")){
			if(map.get("uomDepYn").equals("Y")) {
				fmSetupDAO.fm_setup018_updateMap(map);
			} else {
				map.put("uccSndCod", map.get("hUccSndCod"));
				fmSetupDAO.fm_setup018_insertMap(map);
			}
		}else if(map.get("uccFirCod").equals("STND")){
			int intExpCount = fmSetupDAO.fm_setup018_stnd_count(map);
			if(intExpCount>0){
				fmSetupDAO.fm_setup018_update_stnd(map);
			}else{
				fmSetupDAO.fm_setup018_insert_stnd(map);
			}
		}
		return Constants.RETURN_SUCCESS;
	}

	@Override
	public List<?> fm_setup019_cal(Map<String, Object> map) {
		return fmSetupDAO.fm_setup019_cal(map);
	}

	@Override
	public List<?> fm_setup019(SearchVO searchVO) {
		return fmSetupDAO.fm_setup019(searchVO);
	}

	@Override
	public int fm_setup019_cnt(SearchVO searchVO) {
		return fmSetupDAO.fm_setup019_cnt(searchVO);
	}

	@Override
	public void fm_setup019_sav_ymd(Map map) {
		fmSetupDAO.fm_setup019_sav_ymd(map);
	}

	@Override
	public void fm_setup019_re_ymd(Map map) {
		fmSetupDAO.fm_setup019_re_ymd(map);
	}

	@Override
	public List<?> fm_setup019_Year() {
		return fmSetupDAO.fm_setup019_Year();
	}

	@Override
	public List<?> fm_setup019_Month() {
		return fmSetupDAO.fm_setup019_Month();
	}

	@Override
	public int fm_setup017_auh_cnt(SearchVO searchVO) {
		return fmSetupDAO.fm_setup017_auh_cnt(searchVO);
	}

	@Override
	public String fmSetup007_pwd_update(Map<String, String> map) {
		String result = "";

		String password = (String) map.get("password");
    	String encodedPassword = passwordEncoder.encodePassword(password, null);
    	map.put("newPwd", encodedPassword);

		if(fmSetupDAO.fmSetup007_pwd_chk(map) > 0) {
			result = Constants.RETURN_DUP;
		} else {
			fmSetupDAO.fmSetup007_pwd_update(map);
			result = Constants.RETURN_SUCCESS;
		}
		return result;
	}

	@Override
	public List<?> fm_setup021_list(SearchVO searchVO) {
		return fmSetupDAO.fm_setup021_list(searchVO);
	}

	@Override
	public List<?> fm_setup021_dept_list(Map<String, String> map) {
		return fmSetupDAO.fm_setup021_dept_list(map);
	}

	@Override
	public List<?> fm_setup021_node_list(Map<String, String> map) {
		return fmSetupDAO.fm_setup021_node_list(map);
	}
	@Override
	public EgovMap fm_setup021_info(Map<String, String> map) {
		return fmSetupDAO.fm_setup021_info(map);
	}

	@Override
	public void fm_setup021_map_update(Map<String, Object> map) {
		fmSetupDAO.fm_setup021_map_delete_with_svc_bcy(map);
		if( map.get("uomDepCod") != null) {
			String[] arrDepCod = (String[]) map.get("uomDepCod");
			fmSetupDAO.fm_setup021_member_re(map);
			for(String uomDepCod : arrDepCod) {
				Map<String, Object> mnuMap = new HashMap<String, Object>();
				mnuMap.put("service", map.get("uomSvcCod").toString());
				mnuMap.put("uccSndCod", uomDepCod.substring(0, uomDepCod.indexOf("@@")));
				mnuMap.put("uccSndNam", uomDepCod.substring(uomDepCod.indexOf("@@") + 2));
				mnuMap.put("uccUseYn", "Y");
				mnuMap.put("uccRgtId", map.get("uccRgtId").toString());
				mnuMap.put("uomBcyCod", map.get("uomBcyCod"));
				fmSetupDAO.fm_setup018_insertMap(mnuMap);
				fmSetupDAO.fm_setup021_member_update(mnuMap);
			}
		}
	}

	@Override
	public int fm_setup020_cnt(SearchVO searchVO) {
		return fmSetupDAO.fm_setup020_cnt(searchVO);
	}

	@Override
	public List<?> fm_setup020_list(SearchVO searchVO) {
		return fmSetupDAO.fm_setup020_list(searchVO);
	}

	@Override
	public Map<String, String> fm_setup020_view(Map<String, Object> map) {
		return fmSetupDAO.fm_setup020_view(map);
	}

	@Override
	public String fm_setup020_insert(Map<String, Object> map, List<FileVO> list) {
		String result = fmSetupDAO.fm_setup020_insert(map);
		for (int i = 0; i < list.size(); i++) {
			FileVO fvo = list.get(i);
			fvo.setUmf_con_key(result);
			commonUtilDAO.writeFileTable(fvo);
		}
		return result;
	}

	@Override
	public String fm_setup020_update(Map<String, Object> map, List<FileVO> list) {
		String result = map.get("uvmSvcKey").toString();
		fmSetupDAO.fm_setup020_update(map);
		for (int i = 0; i < list.size(); i++) {
			FileVO fvo = list.get(i);
			fvo.setUmf_con_key(result);
			commonUtilDAO.writeFileTable(fvo);
		}
		return result;
	}


	/**
	 * 2018-05-10 s, 사용자 엑셀 업로드
	 */
	@Override
	public String fm_setup007_excel_insert(HttpServletRequest req) throws Exception {
		int firstY = 0;
		int firstX = 0;
		Cell cell = null;
		Date date = null;

		DataFormatter formatter = new DataFormatter(); 						// creating formatter using the default locale
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");			// excel date format
		SimpleDateFormat sdfLocale = new SimpleDateFormat("yyyy-MM-dd");	// locale date format

		List<UserVO> userList = new ArrayList<UserVO>();

		try {
			MultipartHttpServletRequest mreq = (MultipartHttpServletRequest) req;
			InputStream file = commonUtilService.decryptFileDAC(mreq.getFile("excelFile"));
			Workbook wb = WorkbookFactory.create(file);

			// 첫번째 sheet 만 처리함
			Sheet sheet = wb.getSheetAt(0);

			// 데이터 기준점 [계정코드] 검색
			outerloop:
			for (int y = 0; y <= sheet.getLastRowNum(); y++) {
				Row row = sheet.getRow(y);
		        if (row != null) {
					for (int x = 0; x < row.getLastCellNum(); x++) {
						cell = row.getCell(x);
						if (cell != null && cell.toString().equals("아이디")) {
							firstY = y + 1;
							firstX = x;
							break outerloop;
						}
					}
		        }
			}

			// 데이터 검색
			for (int y = firstY; y <= sheet.getLastRowNum(); y++) {
				int x = firstX;

				UserVO vo = new UserVO();

				vo.setUumUsrId(commonUtilService.getExcelCellValue(sheet.getRow(y).getCell(x)));		// 아이디
				vo.setUumUsrNam(commonUtilService.getExcelCellValue(sheet.getRow(y).getCell(++x)));		// 이름
				vo.setUumUsrPwd(commonUtilService.getExcelCellValue(sheet.getRow(y).getCell(++x)));		// 비밀번호
				vo.setUumMalAds(commonUtilService.getExcelCellValue(sheet.getRow(y).getCell(++x)));		// 이메일
				vo.setUumSvcCod(commonUtilService.getExcelCellValue(sheet.getRow(y).getCell(++x)));		// 서비스
				++x; // 팀 Skip
				vo.setUumDepCod(commonUtilService.getExcelCellValue(sheet.getRow(y).getCell(++x)));		// 팀코드
				++x; // 직급 Skip
				vo.setUumPosCod(commonUtilService.getExcelCellValue(sheet.getRow(y).getCell(++x)));		// 직급코드

				vo.setUumCelNum(commonUtilService.getExcelCellValue(sheet.getRow(y).getCell(++x)));		// 휴대전화
				vo.setUumTelNum(commonUtilService.getExcelCellValue(sheet.getRow(y).getCell(++x)));		// 일반전화
				vo.setUumAuhKey(commonUtilService.getExcelCellValue(sheet.getRow(y).getCell(++x)));		// 담당권한
				vo.setUumApvGbn(commonUtilService.getExcelCellValue(sheet.getRow(y).getCell(++x)));		// 결재권한
				vo.setUumTraGbn(commonUtilService.getExcelCellValue(sheet.getRow(y).getCell(++x)));		// 이관담당권한
				vo.setUumUseYn(commonUtilService.getExcelCellValue(sheet.getRow(y).getCell(++x)));		// 사용여부

				++x; // 1차결재자 Skip
				vo.setUumApvOne(commonUtilService.getExcelCellValue(sheet.getRow(y).getCell(++x)));		// 1차결재자 아이디
				++x; // 2차결재자 Skip
				vo.setUumApvTwo(commonUtilService.getExcelCellValue(sheet.getRow(y).getCell(++x)));		// 2차결재자 아이디
				++x; // 대무자 Skip
				vo.setUumAgnId(commonUtilService.getExcelCellValue(sheet.getRow(y).getCell(++x)));		// 대무자 아이디

				cell = sheet.getRow(y).getCell(++x);
				String uumAgnStr = formatter.formatCellValue(cell);				// 대무기간 시작일
				if (cell != null && cell.getCellType() == Cell.CELL_TYPE_NUMERIC && DateUtil.isCellDateFormatted(cell)) {
					date = sdf.parse(uumAgnStr);
					vo.setUumAgnStr(sdfLocale.format(date));
				}

				cell = sheet.getRow(y).getCell(++x);
				String uumAgnEnd = formatter.formatCellValue(cell);				// 대무기간 종료일
				if (cell != null && cell.getCellType() == Cell.CELL_TYPE_NUMERIC && DateUtil.isCellDateFormatted(cell)) {
					date = sdf.parse(uumAgnEnd);
					vo.setUumAgnEnd(sdfLocale.format(date));
				}

				// 비밀번호 암호화
				if (StringUtils.isNotBlank(vo.getUumUsrPwd())) {
					String userPwd = passwordEncoder.encodePassword(vo.getUumUsrPwd(), null);
					vo.setUumUsrPwd(userPwd);
				}

				// email, mobile, phone 암호화
		 		if (StringUtils.isNotBlank(vo.getUumMalAds())) {
		 			String uumMalAds = Crypto.encAes256(vo.getUumMalAds());
		 			vo.setUumMalAds(uumMalAds);
		 		}
		 		if (StringUtils.isNotBlank(vo.getUumCelNum())) {
		 			String uumCelNum = Crypto.encAes256(vo.getUumCelNum());
		 			vo.setUumCelNum(uumCelNum);
		 		}
		 		if (StringUtils.isNotBlank(vo.getUumTelNum())) {
					String uumTelNum = Crypto.encAes256(vo.getUumTelNum());
					vo.setUumTelNum(uumTelNum);
		 		}

		 		userList.add(vo);
			}
			this.batchUpdateUser(userList);

		} catch (Exception e){
			//return Constants.RETURN_FAIL;
  			throw new RuntimeException(e);
		}
		return Constants.RETURN_SUCCESS;
	}


	public void batchUpdateUser(List<UserVO> list) {
		int batchSize = 50;
		int cnt = 1;
		List<UserVO> userList = new ArrayList<UserVO>();

		for (UserVO el : list) {
			userList.add(el);
			if (cnt % batchSize == 0) {
				fmSetupDAO.batchUpdateUser(userList);
				userList.clear();
			}
            cnt++;
		}
		// Finally
		if (!userList.isEmpty()) {
			fmSetupDAO.batchUpdateUser(userList);
		}
	}
}
