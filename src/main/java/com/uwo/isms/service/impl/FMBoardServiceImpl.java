package com.uwo.isms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.uwo.isms.common.CommonConfig;
import com.uwo.isms.common.EventTitleMessage;
import com.uwo.isms.dao.CommonUtilDAO;
import com.uwo.isms.dao.FMBoardDAO;
import com.uwo.isms.service.CommonUtilService;
import com.uwo.isms.service.FMBoardService;
import com.uwo.isms.service.SendMailService;
import com.uwo.isms.web.FMSetupController;
import com.uwo.isms.domain.BoardVO;
import com.uwo.isms.domain.FileVO;
import com.uwo.isms.domain.SearchVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service("fmBoardService")
public class FMBoardServiceImpl implements FMBoardService {

	Logger log = LogManager.getLogger(FMSetupController.class);

	@Resource(name = "fmBoardDAO")
	private FMBoardDAO fmBoardDAO;

	@Resource(name = "commonUtilDAO")
	private CommonUtilDAO commonUtilDAO;

	@Resource(name = "sendMailService")
	SendMailService sendMailService;

	@Resource(name = "commonUtilService")
	CommonUtilService commonUtilService;

	@Override
	public List<?> fm_file(FileVO fvo) {
		return fmBoardDAO.fm_file(fvo);
	}

	@Override
	public List<?> fm_board001_list(SearchVO searchVO) {
		return fmBoardDAO.fm_board001_list(searchVO);
	}

	@Override
	public int fm_board001_cnt(SearchVO searchVO) {
		return fmBoardDAO.fm_board001_cnt(searchVO);
	}

	@Override
	public void fm_board001_write(BoardVO vo, List<FileVO> list, HttpServletRequest req) {

		vo.setUbm_rgt_id((String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		//vo.setUbm_div_cod((String) req.getSession().getAttribute(CommonConfig.SES_SERVICE_KEY));
		vo.setBcyCode(CommonConfig.SES_MAN_CYL_KEY);

		int key = fmBoardDAO.fm_board001_write(vo);

		for (int i = 0; i < list.size(); i++) {
			FileVO fvo = list.get(i);
			fvo.setUmf_con_key(String.valueOf(key));
			commonUtilDAO.writeFileTable(fvo);
		}
	}

	@Override
	public BoardVO fm_board001_read(BoardVO vo) {
		return fmBoardDAO.fm_board001_read(vo);
	}

	@Override
	public void fm_board001_update(BoardVO vo, List<FileVO> list) {
		int key = fmBoardDAO.fm_board001_update(vo);
		for (int i = 0; i < list.size(); i++) {
			FileVO fvo = list.get(i);
			fvo.setUmf_con_key(String.valueOf(key));
			commonUtilDAO.writeFileTable(fvo);
		}
	}

	@Override
	public void fm_board001_delete(BoardVO vo) {
		fmBoardDAO.fm_board001_delete(vo);
	}

	@Override
	public List<?> fm_board002_list(SearchVO searchVO) {
		return fmBoardDAO.fm_board002_list(searchVO);
	}

	@Override
	public int fm_board002_cnt(SearchVO searchVO) {
		return fmBoardDAO.fm_board002_cnt(searchVO);
	}

	@Override
	public void fm_board002_write(BoardVO vo, List<FileVO> list, HttpServletRequest req) {

		vo.setUbm_rgt_id((String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		//vo.setUbm_div_cod((String) req.getSession().getAttribute(CommonConfig.SES_SERVICE_KEY));
		vo.setBcyCode(CommonConfig.SES_MAN_CYL_KEY);

		int key = fmBoardDAO.fm_board002_write(vo);

		for (int i = 0; i < list.size(); i++) {
			FileVO fvo = list.get(i);
			fvo.setUmf_con_key(String.valueOf(key));
			commonUtilDAO.writeFileTable(fvo);
		}
	}

	@Override
	public BoardVO fm_board002_read(BoardVO vo) {
		return fmBoardDAO.fm_board002_read(vo);
	}

	@Override
	public void fm_board002_update(BoardVO vo, List<FileVO> list) {
		int key = fmBoardDAO.fm_board002_update(vo);
		for (int i = 0; i < list.size(); i++) {
			FileVO fvo = list.get(i);
			fvo.setUmf_con_key(String.valueOf(key));
			commonUtilDAO.writeFileTable(fvo);
		}
	}

	@Override
	public void fm_board002_delete(BoardVO vo) {
		fmBoardDAO.fm_board002_delete(vo);
	}

	@Override
	public List<?> fm_board003_list(SearchVO searchVO) {
		return fmBoardDAO.fm_board003_list(searchVO);
	}

	@Override
	public int fm_board003_cnt(SearchVO searchVO) {
		return fmBoardDAO.fm_board003_cnt(searchVO);
	}

	@Override
	public void fm_board003_write(BoardVO vo, List<FileVO> list, HttpServletRequest req) {

		vo.setUbm_rgt_id((String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		//vo.setUbm_div_cod((String) req.getSession().getAttribute(CommonConfig.SES_SERVICE_KEY));
		vo.setBcyCode(CommonConfig.SES_MAN_CYL_KEY);

		int key = fmBoardDAO.fm_board003_write(vo);

		for (int i = 0; i < list.size(); i++) {
			FileVO fvo = list.get(i);
			fvo.setUmf_con_key(String.valueOf(key));
			commonUtilDAO.writeFileTable(fvo);
		}
	}

	@Override
	public BoardVO fm_board003_read(BoardVO vo) {
		return fmBoardDAO.fm_board003_read(vo);
	}

	@Override
	public void fm_board003_update(BoardVO vo, List<FileVO> list) {
		int key = fmBoardDAO.fm_board003_update(vo);
		for (int i = 0; i < list.size(); i++) {
			FileVO fvo = list.get(i);
			fvo.setUmf_con_key(String.valueOf(key));
			commonUtilDAO.writeFileTable(fvo);
		}
	}

	@Override
	public void fm_board003_delete(BoardVO vo) {
		fmBoardDAO.fm_board003_delete(vo);
	}

	@Override
	public List<?> fm_board004_list(SearchVO searchVO) {
		return fmBoardDAO.fm_board004_list(searchVO);
	}

	@Override
	public int fm_board004_cnt(SearchVO searchVO) {
		return fmBoardDAO.fm_board004_cnt(searchVO);
	}

	@Override
	public void fm_board004_write(BoardVO vo, List<FileVO> list, HttpServletRequest req) {

		vo.setUbm_rgt_id((String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		vo.setUbm_div_cod((String) req.getSession().getAttribute(CommonConfig.SES_SERVICE_KEY));
		vo.setBcyCode(CommonConfig.SES_MAN_CYL_KEY);

		int key = fmBoardDAO.fm_board004_write(vo);

		for (int i = 0; i < list.size(); i++) {
			FileVO fvo = list.get(i);
			fvo.setUmf_con_key(String.valueOf(key));
			commonUtilDAO.writeFileTable(fvo);
		}


		/*
		 * 2017-09-25, 글 등록시 관리자에게 이메일 발송
		 */
		String sendUserName = (String) req.getSession().getAttribute(CommonConfig.SES_USER_NAME_KEY);
		vo.getUbm_brd_tle();
		vo.getUbm_brd_cts();

		// UWO_EVT_ARM INSERT
		String eventCode = EventTitleMessage.E09_CODE;
		String title = "ISAMS(정보보호활동관리시스템)에 QNA 문의글이 등록되었습니다.";

		// email template
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("uccFirCod", "evtArm");
		map2.put("uccSndCod", EventTitleMessage.E09_CODE);
		EgovMap emap = commonUtilService.getUccComCodeInfo(map2);
		String mailBody = (String) emap.get("uccEtc");

		mailBody = mailBody.replace("{__WRITER__}", sendUserName);
		mailBody = mailBody.replace("{__TITLE__}", vo.getUbm_brd_tle());
		mailBody = mailBody.replace("{__BODY__}", vo.getUbm_brd_cts());

		List<?> uList = commonUtilDAO.getAdminUserKeys();
		for (int j = 0; j < uList.size(); j++) {
			String userKey = (String) uList.get(j);
			sendMailService.sendMail((String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY), eventCode, title, mailBody, userKey);
		}
	}

	@Override
	public BoardVO fm_board004_read(BoardVO vo) {
		return fmBoardDAO.fm_board004_read(vo);
	}

	@Override
	public void fm_board004_update(BoardVO vo) {
		fmBoardDAO.fm_board004_update(vo);
	}

	@Override
	public void fm_board004_delete(BoardVO vo) {
		fmBoardDAO.fm_board004_delete(vo);
	}

	@Override
	public List<?> fm_board005_list(SearchVO searchVO) {
		return fmBoardDAO.fm_board005_list(searchVO);
	}

	@Override
	public int fm_board005_cnt(SearchVO searchVO) {
		return fmBoardDAO.fm_board005_cnt(searchVO);
	}

	@Override
	public void fm_board005_write(BoardVO vo, List<FileVO> list, HttpServletRequest req) {

		vo.setUbm_rgt_id((String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		//vo.setUbm_div_cod((String) req.getSession().getAttribute(CommonConfig.SES_SERVICE_KEY));
		vo.setBcyCode(CommonConfig.SES_MAN_CYL_KEY);

		int key = fmBoardDAO.fm_board005_write(vo);

		for (int i = 0; i < list.size(); i++) {
			FileVO fvo = list.get(i);
			fvo.setUmf_con_key(String.valueOf(key));
			commonUtilDAO.writeFileTable(fvo);
		}
	}

	@Override
	public BoardVO fm_board005_read(BoardVO vo) {
		return fmBoardDAO.fm_board005_read(vo);
	}

	@Override
	public void fm_board005_update(BoardVO vo, List<FileVO> list) {
		int key = fmBoardDAO.fm_board005_update(vo);
		for (int i = 0; i < list.size(); i++) {
			FileVO fvo = list.get(i);
			fvo.setUmf_con_key(String.valueOf(key));
			commonUtilDAO.writeFileTable(fvo);
		}
	}

	@Override
	public void fm_board005_delete(BoardVO vo) {
		fmBoardDAO.fm_board005_delete(vo);
	}

	@Override
	public List getSvcList() {
		return fmBoardDAO.getSvcList();
	}

	@Override
	public EgovMap fm_board001_auh_info(String ubmBrdKey) {
		return fmBoardDAO.fm_board001_auh_info(ubmBrdKey);
	}

	@Override
	public void fm_copyToFAQ(BoardVO vo) {
		fmBoardDAO.fm_copyToFAQ(vo);
	}

	@Override
	public void saveAnswer(Map map) {
		fmBoardDAO.saveAnswer(map);
	}

	@Override
	public EgovMap getCntrData(Map map) {
		return fmBoardDAO.getCntrData(map);
	}

	@Override
	public void sendMail(BoardVO vo, List<FileVO> list, HttpServletRequest req) {

		if (vo.getUbm_ntc_yn() == null || vo.getUbm_ntc_yn().equals("")) {
			vo.setUbm_ntc_yn("N");
		}

		if (vo.getUbm_ntc_yn().equals("Y")) {
			String userKey = "";
			String eventCode = EventTitleMessage.E08_CODE;
			String title = EventTitleMessage.E08;
			String contents = "";
			Map map = new HashMap();
			map.put("division", (String) req.getSession().getAttribute(CommonConfig.SES_SERVICE_KEY));
			map.put("sekAuth", vo.getUbm_sek_ath());

			List uList = commonUtilDAO.getDivisionUser(map);
			for (int j = 0; j < uList.size(); j++) {
				userKey = (String) uList.get(j);
				contents = title + "<p />" + vo.getUbm_brd_cts() + "<br /> ";
				sendMailService.sendMail((String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY), eventCode, title, contents, userKey);

			}
		}
	}



	/**
	 * 2018-04-18 s, 글쓰기 권한 확인
	 * EgovUserDetailsSessionServiceImp.getAuthoritiesChk() 복제 후 수정
	 */
	@Override
	public Boolean getAuthoritiesWriteChk(HttpServletRequest req) throws Exception {
		boolean chk = true;

		List<?> listAuth = (List<?>) RequestContextHolder.getRequestAttributes().getAttribute("menuAuthList", RequestAttributes.SCOPE_SESSION);

		String urlMappign = req.getRequestURI();
		String valueMappign = urlMappign.substring(urlMappign.lastIndexOf("/") + 1, urlMappign.lastIndexOf("."));

		if (urlMappign.indexOf("_") > 0) {
			valueMappign = urlMappign.substring(urlMappign.lastIndexOf("/") + 1, urlMappign.indexOf("_"));
		}

		Map<String, Object> authList = null;
		for(int i = 0; i < listAuth.size(); i++) {
			authList = (Map<String, Object>)listAuth.get(i);
			String authUrl = authList.get("ummMnuUrl").toString();
			String ummMnuUrl = "";

			if(authUrl.lastIndexOf("/") > 0) {
				ummMnuUrl = authUrl.substring(authUrl.lastIndexOf("/") + 1, authUrl.lastIndexOf("."));
			} else {
				ummMnuUrl = authUrl.substring(0, authUrl.lastIndexOf("."));
			}

			if (valueMappign.equals(ummMnuUrl)) {
				chk = false;
				if (authList.get("mnuRgt").equals("Y")) {
					chk = true;
					break;
				}
			}
		}
		return chk;
	}

}
