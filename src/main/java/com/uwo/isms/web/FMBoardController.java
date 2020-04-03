/**
 ***********************************
 * @source FMBoardController.java
 * @date 2015. 05. 06.
 * @project isms3
 * @description 게시판 controller
 ***********************************
 */
package com.uwo.isms.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.uwo.isms.common.CommonConfig;
import com.uwo.isms.common.Constants;
import com.uwo.isms.domain.BoardVO;
import com.uwo.isms.domain.PopupVO;
import com.uwo.isms.domain.FileVO;
import com.uwo.isms.service.CommonCodeService;
import com.uwo.isms.service.CommonUtilService;
import com.uwo.isms.service.FMBoardService;
import com.uwo.isms.service.PopupService;
import com.uwo.isms.service.FileUploadService;
import com.uwo.isms.service.SendMailService;
import com.uwo.isms.service.impl.EgovUserDetailsSessionServiceImpl;
import com.uwo.isms.util.FileUpload;
import com.uwo.isms.util.PaginationUtil;
import com.uwo.isms.domain.SearchVO;

import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
@RequestMapping("/board")
public class FMBoardController {

	Logger log = LogManager.getLogger(FMBoardController.class);

	@Resource(name = "fmBoardService")
	private FMBoardService fmBoardService;

	@Resource(name = "popupService")
	private PopupService popupService;

	@Resource(name = "fileUploadService")
	FileUploadService fileUploadService;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;

	@Resource(name = "commonCodeService")
	private CommonCodeService commonCodeService;

	@Resource(name = "sendMailService")
	SendMailService sendMailService;

	@Resource(name = "commonUtilService")
	private CommonUtilService commonUtilService;

	@Autowired
	private DefaultBeanValidator beanValidator;

	@RequestMapping(value = "/FM-BOARD001.do")
	public String fmBorad001(@ModelAttribute("searchVO") SearchVO searchVO,
			ModelMap model, final HttpServletRequest req) throws Exception {

		searchVO.setSekAth((String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));

		int totCnt = fmBoardService.fm_board001_cnt(searchVO);
		model.addAttribute("paginationInfo", new PaginationUtil(searchVO, totCnt).getPageData());
        model.addAttribute("resultList", fmBoardService.fm_board001_list(searchVO));
        model.addAttribute("rstCod", req.getParameter("rstCod"));
        model.addAttribute("totCnt", totCnt);

        // 2017-04-18 s, 글쓰기 권한
        model.addAttribute("writeAuth", fmBoardService.getAuthoritiesWriteChk(req));

        return "FM-BOARD001";
	}

	@RequestMapping(value = "FM-BOARD001_RW.do")
	public String FM_BOARD001_W(@ModelAttribute("vo") BoardVO vo, Model model,
			HttpServletRequest req) throws Exception {

		model.addAttribute("vo", new BoardVO());

		List list = new ArrayList();
		list = fmBoardService.getSvcList();
		model.addAttribute("divlist", list);
		return "/FM-BOARD001-W";
	}
	/*
	@RequestMapping(value = "/FM-BOARD001_RW.do")
	public String fmBoard001_rw(HttpServletRequest req, HttpServletResponse res,
			@RequestParam Map<String, String> paramMap, ModelMap model) throws Exception {

		if(StringUtils.isNotEmpty(paramMap.get("ubmBrdKey"))) {
			model.addAttribute("auhInfo", fmBoardService.fm_board001_auh_info(paramMap.get("ubmBrdKey")));
			/*model.addAttribute("mapList", fmSetupService.fm_setup017_map_list(paramMap.get("uatAuhKey")));
			model.addAttribute("mapNodeList", fmSetupService.fm_setup017_node_list(paramMap.get("uatAuhKey")));
		}
		model.addAttribute("paramMap", paramMap);
		return "FM-BOARD001-W";
	}
	*/

	@RequestMapping(value = "FM-BOARD001_W.do", method = RequestMethod.POST)
	public String FM_BOARD001_W(@ModelAttribute("vo") BoardVO vo,
			BindingResult bindingResult, Model model, @RequestParam Map<String, Object> maps,
			final HttpServletRequest req) throws Exception {

		String uploadPath = propertyService.getString("boardUploadPath");
		req.setAttribute("uploadPath", uploadPath);

		FileUpload fu = new FileUpload();
		// parammeter : req, umf_tbl_gbn, umf_con_gbn
		List<FileVO> list = fu.fileuplaod(req, Constants.FILE_GBN_BRD, Constants.FILE_CON_NTC);

		fmBoardService.fm_board001_write(vo, list, req);

		if(!req.getParameter("popupStatus").equals(null) && req.getParameter("popupStatus").equals("Y")){
			PopupVO pvo = new PopupVO();
			pvo.setStatus(req.getParameter("popupStatus"));
			pvo.setStart_date(req.getParameter("popupStartDate")+" "+req.getParameter("popupStartDateHour"));
			pvo.setFinish_date(req.getParameter("popupFinishDate")+" "+req.getParameter("popupFinishDateHour"));
			pvo.setWidth(Integer.parseInt(req.getParameter("popupWidth")));
			pvo.setHeight(Integer.parseInt(req.getParameter("popupHeight")));
			pvo.setTop(Integer.parseInt(req.getParameter("popupTop")));
			pvo.setLeft(Integer.parseInt(req.getParameter("popupLeft")));
			popupService.popup_insert(pvo);
		}

		fmBoardService.sendMail(vo, list, req);

		return "redirect:/board/FM-BOARD001.do";
	}

	@RequestMapping("FM-BOARD001_V.do")
	public String FM_BOARD001_V(@RequestParam("selectedId") String id,
			Model model, final HttpServletRequest req) throws Exception {

		String s_auth = (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);

		BoardVO vo = new BoardVO();
		vo.setUbm_brd_key(Integer.parseInt(id));
		vo.setAuthKey(s_auth);

		FileVO fvo = new FileVO();
		fvo.setUmf_con_key(id);
		fvo.setUmf_tbl_gbn(Constants.FILE_GBN_BRD);
		fvo.setUmf_con_gbn(Constants.FILE_CON_NTC);
		BoardVO rVo = fmBoardService.fm_board001_read(vo);

		int b_auth = Integer.parseInt(rVo.getUbm_dwn_ath());

		model.addAttribute("vo", rVo);
		// 보드권한에 따른 파일리스트 출력여부
		if ((s_auth.equals("A") || (s_auth.equals("V") && b_auth > 1)) || ((s_auth.equals("P") && b_auth > 2))) {
			model.addAttribute("file", fmBoardService.fm_file(fvo));
		}

		model.addAttribute("usrKey", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		model.addAttribute("authKey", (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));

		return "/FM-BOARD001-V";
	}

	@RequestMapping("FM-BOARD001_R.do")
	public String FM_BOARD001_R(@RequestParam("seq") String seq, Model model,
			final HttpServletRequest req) throws Exception {
		BoardVO vo = new BoardVO();
		vo.setUbm_brd_key(Integer.parseInt(seq));
		FileVO fvo = new FileVO();
		fvo.setUmf_con_key(seq);
		fvo.setUmf_tbl_gbn(Constants.FILE_GBN_BRD);
		fvo.setUmf_con_gbn(Constants.FILE_CON_NTC);
		BoardVO rVo = fmBoardService.fm_board001_read(vo);

		int b_auth = Integer.parseInt(rVo.getUbm_dwn_ath());
		String s_auth = (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);

		model.addAttribute("vo", rVo);
		if(rVo.getUbm_brd_gbn().equals("1")){
			PopupVO popupVo = popupService.popup_read_key(rVo.getUbm_brd_key());
			model.addAttribute("pvo", popupVo);
		}

		 //보드권한에 따른 파일리스트 출력여부
		if ((s_auth.equals("A") || (s_auth.equals("V") && b_auth > 1)) || ((s_auth.equals("P") && b_auth > 2))) {
			model.addAttribute("file", fmBoardService.fm_file(fvo));
		}

		List list = new ArrayList();
		list = fmBoardService.getSvcList();
		model.addAttribute("divlist", list);
		return "/FM-BOARD001-W";
	}

	@RequestMapping("FM-BOARD001_U.do")
	public String FM_BOARD001_U(BoardVO vo, BindingResult bindingResult,
			Model model, final HttpServletRequest req) throws Exception {

		String uploadPath = propertyService.getString("boardUploadPath");
		req.setAttribute("uploadPath", uploadPath);
		FileUpload fu = new FileUpload();
		// parammeter : req, umf_tbl_gbn, umf_con_gbn
		List<FileVO> list = fu.fileuplaod(req, Constants.FILE_GBN_BRD, Constants.FILE_CON_NTC);

		vo.setUbm_rgt_id((String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		vo.setUbm_div_cod((String) req.getSession().getAttribute(CommonConfig.SES_SERVICE_KEY));
		vo.setBcyCode(CommonConfig.SES_MAN_CYL_KEY);
		vo.setAuthKey((String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));

		fmBoardService.fm_board001_update(vo, list);

		if(!req.getParameter("popupStatus").equals(null) && !req.getParameter("popupStatus").equals("N")){
			int popupCount = popupService.popup_be_key(vo.getUbm_brd_key());
			PopupVO pvo = new PopupVO();
			pvo.setBrd_key(vo.getUbm_brd_key());
			pvo.setStatus(req.getParameter("popupStatus"));
			pvo.setStart_date(req.getParameter("popupStartDate")+" "+req.getParameter("popupStartDateHour"));
			pvo.setFinish_date(req.getParameter("popupFinishDate")+" "+req.getParameter("popupFinishDateHour"));

			pvo.setWidth(Integer.parseInt(req.getParameter("popupWidth")));
			pvo.setHeight(Integer.parseInt(req.getParameter("popupHeight")));
			pvo.setTop(Integer.parseInt(req.getParameter("popupTop")));
			pvo.setLeft(Integer.parseInt(req.getParameter("popupLeft")));
			if(popupCount>0){
				pvo.setPopup_id(Integer.parseInt(req.getParameter("popupID")));
				popupService.popup_update(pvo);
			}else{
				popupService.popup_update_insert(pvo);
			}

		}else if(req.getParameter("popupStatus").equals("N") && !req.getParameter("popupID").equals(null)){
			int popupCount = popupService.popup_be_key(vo.getUbm_brd_key());
			if(popupCount>0) {
				PopupVO pvo = new PopupVO();
				pvo.setBrd_key(vo.getUbm_brd_key());
				pvo.setStatus(req.getParameter("popupStatus"));
				pvo.setPopup_id(Integer.parseInt(req.getParameter("popupID")));
				popupService.popup_update(pvo);
			}
		}

		String page = "redirect:/board/FM-BOARD001.do";
		return page;
	}

	@RequestMapping("FM-BOARD001_D.do")
	public String FM_BOARD001_D(BoardVO vo, BindingResult bindingResult,
			Model model, final HttpServletRequest req) throws Exception {

		vo.setUbm_rgt_id((String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		vo.setAuthKey((String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));

		fmBoardService.fm_board001_delete(vo);
		//model.addAttribute("rstCod", Constants.RST_DEL);
		return "redirect:/board/FM-BOARD001.do";
	}

	@RequestMapping(value = "/FM-BOARD002.do")
	public String fmBoard002(@ModelAttribute("searchVO") SearchVO searchVO,
			ModelMap model, final HttpServletRequest req) throws Exception {

		searchVO.setSekAth((String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));

		int totCnt = fmBoardService.fm_board002_cnt(searchVO);
        model.addAttribute("paginationInfo", new PaginationUtil(searchVO, totCnt).getPageData());
        model.addAttribute("resultList", fmBoardService.fm_board002_list(searchVO));
        model.addAttribute("totCnt", totCnt);

        // 2017-04-18 s, 글쓰기 권한
        model.addAttribute("writeAuth", fmBoardService.getAuthoritiesWriteChk(req));

		return "FM-BOARD002";
	}

	@RequestMapping(value = "FM-BOARD002_RW.do")
	public String FM_BOARD002_W(@ModelAttribute("vo") BoardVO vo, Model model,
			HttpServletRequest req) throws Exception {
		model.addAttribute("vo", new BoardVO());

		List list = new ArrayList();
		list = fmBoardService.getSvcList();
		model.addAttribute("divlist", list);

		return "/FM-BOARD002-W";
	}

	@RequestMapping(value = "FM-BOARD002_W.do", method = RequestMethod.POST)
	public String FM_BOARD002_W(@ModelAttribute("vo") BoardVO vo,
			BindingResult bindingResult, Model model,
			final HttpServletRequest req) throws Exception {

		String uploadPath = propertyService.getString("boardUploadPath");

		if (bindingResult.hasErrors()) {
			model.addAttribute("vo", vo);
			return "/FM-BOARD002-W";
		}
		req.setAttribute("uploadPath", uploadPath);

		FileUpload fu = new FileUpload();
		// parammeter : req, umf_tbl_gbn, umf_con_gbn
		List<FileVO> list = fu.fileuplaod(req, Constants.FILE_GBN_BRD, Constants.FILE_CON_SCR);

		fmBoardService.fm_board002_write(vo, list,req);

		fmBoardService.sendMail(vo, list, req);

		return "redirect:/board/FM-BOARD002.do";
	}

	@RequestMapping("FM-BOARD002_V.do")
	public String FM_BOARD002_V(@RequestParam("selectedId") String id,
			Model model, final HttpServletRequest req) throws Exception {

		String s_auth = (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);

		BoardVO vo = new BoardVO();
		vo.setUbm_brd_key(Integer.parseInt(id));
		vo.setAuthKey(s_auth);

		FileVO fvo = new FileVO();
		fvo.setUmf_con_key(id);
		fvo.setUmf_tbl_gbn(Constants.FILE_GBN_BRD);
		fvo.setUmf_con_gbn(Constants.FILE_CON_SCR);
		BoardVO rVo = fmBoardService.fm_board002_read(vo);

		int b_auth = Integer.parseInt(rVo.getUbm_dwn_ath());

		model.addAttribute("vo", rVo);
		// 보드권한에 따른 파일리스트 출력여부
		if ((s_auth.equals("A") || (s_auth.equals("V") && b_auth > 1)) || ((s_auth.equals("P") && b_auth > 2))) {
			model.addAttribute("file", fmBoardService.fm_file(fvo));
		}

		model.addAttribute("usrKey", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		model.addAttribute("authKey", (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));

		return "/FM-BOARD002-V";
	}

	@RequestMapping("FM-BOARD002_R.do")
	public String FM_BOARD002_R(@RequestParam("seq") String seq, Model model,
			final HttpServletRequest req) throws Exception {
		BoardVO vo = new BoardVO();
		vo.setUbm_brd_key(Integer.parseInt(seq));
		FileVO fvo = new FileVO();
		fvo.setUmf_con_key(seq);
		fvo.setUmf_tbl_gbn(Constants.FILE_GBN_BRD);
		fvo.setUmf_con_gbn(Constants.FILE_CON_SCR);
		BoardVO rVo = fmBoardService.fm_board002_read(vo);

		int b_auth = Integer.parseInt(rVo.getUbm_dwn_ath());
		String s_auth = (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);

		model.addAttribute("vo", rVo);
		// 보드권한에 따른 파일리스트 출력여부
		if ((s_auth.equals("A") || (s_auth.equals("V") && b_auth > 1)) || ((s_auth.equals("P") && b_auth > 2))) {
			model.addAttribute("file", fmBoardService.fm_file(fvo));
		}

		List list = new ArrayList();
		list = fmBoardService.getSvcList();
		model.addAttribute("divlist", list);
		return "/FM-BOARD002-W";
	}

	@RequestMapping("FM-BOARD002_U.do")
	public String FM_BOARD002_U(BoardVO vo, BindingResult bindingResult,
			Model model, final HttpServletRequest req) throws Exception {

		String uploadPath = propertyService.getString("boardUploadPath");
		req.setAttribute("uploadPath", uploadPath);
		FileUpload fu = new FileUpload();
		// parammeter : req, umf_tbl_gbn, umf_con_gbn
		List<FileVO> list = fu.fileuplaod(req, Constants.FILE_GBN_BRD, Constants.FILE_CON_SCR);

		vo.setUbm_rgt_id((String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		vo.setUbm_div_cod((String) req.getSession().getAttribute(CommonConfig.SES_SERVICE_KEY));
		vo.setBcyCode(CommonConfig.SES_MAN_CYL_KEY);
		vo.setAuthKey((String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));

		fmBoardService.fm_board002_update(vo, list);

		//String page = "redirect:/board/FM-BOARD002_R.do?seq="+ vo.getUbm_brd_key();
		//return page;
		return "redirect:/board/FM-BOARD002.do";
	}

	@RequestMapping("FM-BOARD002_D.do")
	public String FM_BOARD002_D(BoardVO vo, BindingResult bindingResult,
			Model model, final HttpServletRequest req) throws Exception {

		vo.setUbm_rgt_id((String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		vo.setAuthKey((String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));

		fmBoardService.fm_board002_delete(vo);
		return "redirect:/board/FM-BOARD002.do";
	}

	@RequestMapping(value = "/FM-BOARD003.do")
	public String fmBorad003(@ModelAttribute("searchVO") SearchVO searchVO,
			ModelMap model, final HttpServletRequest req) throws Exception {

		searchVO.setSekAth((String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));

		int totCnt = fmBoardService.fm_board003_cnt(searchVO);
        model.addAttribute("paginationInfo", new PaginationUtil(searchVO, totCnt).getPageData());
        model.addAttribute("resultList", fmBoardService.fm_board003_list(searchVO));
        model.addAttribute("totCnt", totCnt);

        // 2018-07-02 s, 글쓰기 권한
        model.addAttribute("writeAuth", fmBoardService.getAuthoritiesWriteChk(req));

		return "FM-BOARD003";
	}

	@RequestMapping(value = "FM-BOARD003_RW.do")
	public String FM_BOARD003_W(@ModelAttribute("vo") BoardVO vo, Model model,
			HttpServletRequest req) throws Exception {
		model.addAttribute("vo", new BoardVO());

		List list = new ArrayList();
		list = fmBoardService.getSvcList();
		model.addAttribute("divlist", list);
		return "/FM-BOARD003-W";
	}

	@RequestMapping(value = "FM-BOARD003_W.do", method = RequestMethod.POST)
	public String FM_BOARD003_W(@ModelAttribute("vo") BoardVO vo,
			BindingResult bindingResult, Model model,
			final HttpServletRequest req) throws Exception {

		String uploadPath = propertyService.getString("boardUploadPath");
		req.setAttribute("uploadPath", uploadPath);

		FileUpload fu = new FileUpload();
		// parammeter : req, umf_tbl_gbn, umf_con_gbn
		List<FileVO> list = fu.fileuplaod(req, Constants.FILE_GBN_BRD, Constants.FILE_CON_DAT);

		fmBoardService.fm_board003_write(vo, list, req);

		if (vo.getUbm_ntc_yn() == null || vo.getUbm_ntc_yn().equals("")) {
			vo.setUbm_ntc_yn("N");
		}

		fmBoardService.sendMail(vo, list, req);

		return "redirect:/board/FM-BOARD003.do";
	}

	@RequestMapping("FM-BOARD003_V.do")
	public String FM_BOARD003_V(@RequestParam("selectedId") String id,
			Model model, final HttpServletRequest req) throws Exception {

		String s_auth = (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);

		BoardVO vo = new BoardVO();
		vo.setUbm_brd_key(Integer.parseInt(id));
		vo.setAuthKey(s_auth);

		FileVO fvo = new FileVO();
		fvo.setUmf_con_key(id);
		fvo.setUmf_tbl_gbn(Constants.FILE_GBN_BRD);
		fvo.setUmf_con_gbn(Constants.FILE_CON_DAT);
		BoardVO rVo = fmBoardService.fm_board003_read(vo);

		int b_auth = Integer.parseInt(rVo.getUbm_dwn_ath());

		model.addAttribute("vo", rVo);
		// 보드권한에 따른 파일리스트 출력여부
		if ((s_auth.equals("A") || (s_auth.equals("V") && b_auth > 1))|| ((s_auth.equals("P") && b_auth > 2))) {
			model.addAttribute("file", fmBoardService.fm_file(fvo));
		}

		model.addAttribute("usrKey", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		model.addAttribute("authKey", (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));

		return "/FM-BOARD003-V";
	}

	@RequestMapping("FM-BOARD003_R.do")
	public String FM_BOARD003_R(@RequestParam("seq") String seq, Model model,
			final HttpServletRequest req) throws Exception {
		BoardVO vo = new BoardVO();
		vo.setUbm_brd_key(Integer.parseInt(seq));
		FileVO fvo = new FileVO();
		fvo.setUmf_con_key(seq);
		fvo.setUmf_tbl_gbn(Constants.FILE_GBN_BRD);
		fvo.setUmf_con_gbn(Constants.FILE_CON_DAT);
		BoardVO rVo = fmBoardService.fm_board003_read(vo);

		int b_auth = Integer.parseInt(rVo.getUbm_dwn_ath());
		String s_auth = (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);

		model.addAttribute("vo", rVo);
		// 보드권한에 따른 파일리스트 출력여부
		if ((s_auth.equals("A") || (s_auth.equals("V") && b_auth > 1)) || ((s_auth.equals("P") && b_auth > 2))) {
			model.addAttribute("file", fmBoardService.fm_file(fvo));
		}

		List list = new ArrayList();
		list = fmBoardService.getSvcList();
		model.addAttribute("divlist", list);
		return "/FM-BOARD003-W";
	}

	@RequestMapping("FM-BOARD003_U.do")
	public String FM_BOARD003_U(BoardVO vo, BindingResult bindingResult,
			Model model, final HttpServletRequest req) throws Exception {
		String uploadPath = propertyService.getString("boardUploadPath");
		req.setAttribute("uploadPath", uploadPath);
		FileUpload fu = new FileUpload();
		// parammeter : req, umf_tbl_gbn, umf_con_gbn
		List<FileVO> list = fu.fileuplaod(req, Constants.FILE_GBN_BRD, Constants.FILE_CON_DAT);

		vo.setUbm_rgt_id((String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		vo.setUbm_div_cod((String) req.getSession().getAttribute(CommonConfig.SES_SERVICE_KEY));
		vo.setBcyCode(CommonConfig.SES_MAN_CYL_KEY);
		vo.setAuthKey((String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));

		fmBoardService.fm_board003_update(vo, list);
		String page = "redirect:/board/FM-BOARD003.do";
		return page;
	}

	@RequestMapping("FM-BOARD003_D.do")
	public String FM_BOARD003_D(BoardVO vo, BindingResult bindingResult,
			Model model, final HttpServletRequest req) throws Exception {

		vo.setUbm_rgt_id((String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		vo.setAuthKey((String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));

		fmBoardService.fm_board003_delete(vo);
		return "redirect:/board/FM-BOARD003.do";
	}

	@RequestMapping(value = "/FM-BOARD004.do")
	public String fmBoard004(@ModelAttribute("searchVO") SearchVO searchVO,
			ModelMap model, final HttpServletRequest req) throws Exception {

		searchVO.setSekAth((String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));

		int totCnt = fmBoardService.fm_board004_cnt(searchVO);
        model.addAttribute("paginationInfo", new PaginationUtil(searchVO, totCnt).getPageData());
        model.addAttribute("resultList", fmBoardService.fm_board004_list(searchVO));
        model.addAttribute("totCnt", totCnt);

        // 2017-04-18 s, 글쓰기 권한
        model.addAttribute("writeAuth", fmBoardService.getAuthoritiesWriteChk(req));

		return "FM-BOARD004";
	}

	@RequestMapping(value = "FM-BOARD004_RW.do")
	public String FM_BOARD004_W(@ModelAttribute("vo") BoardVO vo, Model model,
			HttpServletRequest req) throws Exception {
		model.addAttribute("vo", new BoardVO());

		List list = new ArrayList();
		list = fmBoardService.getSvcList();
		model.addAttribute("divlist", list);
		return "/FM-BOARD004-W";
	}

	@RequestMapping(value = "FM-BOARD004_W.do", method = RequestMethod.POST)
	public String FM_BOARD004_W(@ModelAttribute("vo") BoardVO vo,
			BindingResult bindingResult, Model model,
			final HttpServletRequest req) throws Exception {

		String uploadPath = propertyService.getString("boardUploadPath");
		req.setAttribute("uploadPath", uploadPath);

		FileUpload fu = new FileUpload();
		// parammeter : req, umf_tbl_gbn, umf_con_gbn
		List<FileVO> list = fu.fileuplaod(req, Constants.FILE_GBN_BRD, Constants.FILE_CON_QNA);

		fmBoardService.fm_board004_write(vo, list, req);

		// 2017-09-25, service 로 이동
		//fmBoardService.sendMail(vo, list, req);

		return "redirect:/board/FM-BOARD004.do";
	}

	@RequestMapping("FM-BOARD004_V.do")
	public String FM_BOARD004_V(@RequestParam("selectedId") String id,
			Model model, final HttpServletRequest req) throws Exception {

		String s_auth = (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);

		BoardVO vo = new BoardVO();
		vo.setUbm_brd_key(Integer.parseInt(id));
		vo.setAuthKey(s_auth);

		FileVO fvo = new FileVO();
		fvo.setUmf_con_key(id);
		fvo.setUmf_tbl_gbn(Constants.FILE_GBN_BRD);
		fvo.setUmf_con_gbn(Constants.FILE_CON_QNA);
		BoardVO rVo = fmBoardService.fm_board004_read(vo);

		int b_auth = Integer.parseInt(rVo.getUbm_dwn_ath());

		Map map = new HashMap();
		map.put("brdKey", Integer.parseInt(id));
		map.put("manCyl", req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		map.put("service", req.getSession().getAttribute(CommonConfig.SES_SERVICE_KEY));

		model.addAttribute("std",fmBoardService.getCntrData(map));
		model.addAttribute("vo", rVo);
		model.addAttribute("file", fmBoardService.fm_file(fvo));

		model.addAttribute("usrKey", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		model.addAttribute("authKey", (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));

		return "/FM-BOARD004-V";
	}

	@RequestMapping("FM-BOARD004_R.do")
	public String FM_BOARD004_R(@RequestParam("seq") String seq, Model model,
			final HttpServletRequest req) throws Exception {
		BoardVO vo = new BoardVO();
		vo.setUbm_brd_key(Integer.parseInt(seq));
		FileVO fvo = new FileVO();
		fvo.setUmf_con_key(seq);
		fvo.setUmf_tbl_gbn(Constants.FILE_GBN_BRD);
		fvo.setUmf_con_gbn(Constants.FILE_CON_QNA);
		BoardVO rVo = fmBoardService.fm_board004_read(vo);

		int b_auth = Integer.parseInt(rVo.getUbm_dwn_ath());
		String s_auth = (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);

		model.addAttribute("vo", rVo);
		// 보드권한에 따른 파일리스트 출력여부
		if ((s_auth.equals("A") || (s_auth.equals("V") && b_auth > 1)) || ((s_auth.equals("P") && b_auth > 2))) {
			model.addAttribute("file", fmBoardService.fm_file(fvo));
		}

		Map map = new HashMap();
		map.put("brdKey", Integer.parseInt(seq));
		map.put("manCyl", req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		map.put("service", req.getSession().getAttribute(CommonConfig.SES_SERVICE_KEY));

		List list = new ArrayList();
		list = fmBoardService.getSvcList();
		model.addAttribute("divlist", list);
		model.addAttribute("std",fmBoardService.getCntrData(map));
		return "/FM-BOARD004-W";
	}

	@RequestMapping("FM-BOARD004_U.do")
	public String FM_BOARD004_U(BoardVO vo, BindingResult bindingResult,
			Model model, final HttpServletRequest req) throws Exception {
/*
		String uploadPath = propertyService.getString("boardUploadPath");
		req.setAttribute("uploadPath", uploadPath);
		FileUpload fu = new FileUpload();
		// parammeter : req, umf_tbl_gbn, umf_con_gbn
		List<FileVO> list = fu.fileuplaod(req, Constants.FILE_GBN_BRD, Constants.FILE_CON_QNA);

		vo.setUbm_rgt_id((String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		vo.setUbm_div_cod((String) req.getSession().getAttribute(CommonConfig.SES_SERVICE_KEY));
		vo.setBcyCode(CommonConfig.SES_MAN_CYL_KEY);
*/
		vo.setUbm_rgt_id((String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		vo.setAuthKey((String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));

		fmBoardService.fm_board004_update(vo);
		String page = "redirect:/board/FM-BOARD004.do";
		return page;
	}

	@RequestMapping("FM-BOARD004_copyToFAQ.do")
	public String FM_BOARD004_copyToFAQ(BoardVO vo, BindingResult bindingResult,
			Model model, final HttpServletRequest req) throws Exception {

		BoardVO qVo = fmBoardService.fm_board004_read(vo);

		vo.setStandard(qVo.getStandard());
		vo.setDepth3(qVo.getDepth3());
		vo.setUbm_brd_tle("[Q&A이관] "+vo.getUbm_brd_tle());

		fmBoardService.fm_copyToFAQ(vo);
		String page = "redirect:/board/FM-BOARD004.do";
		return page;
	}

	@RequestMapping("FM-BOARD004_D.do")
	public String FM_BOARD004_D(BoardVO vo, BindingResult bindingResult,
			Model model, final HttpServletRequest req) throws Exception {

		vo.setUbm_rgt_id((String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		vo.setAuthKey((String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));

		beanValidator.validate(vo, bindingResult);

		if (bindingResult.hasErrors()) {
			model.addAttribute("vo", vo);
			return "/FM-BOARD004-W";
		}

		fmBoardService.fm_board004_delete(vo);
		return "redirect:/board/FM-BOARD004.do";
	}

	@RequestMapping(value = "SaveAnswer.do")
	public ModelAndView SaveAnswer(ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

        Map map = new HashMap();

        String brdKey = req.getParameter("brdKey");
        String ans = req.getParameter("ans");

        map.put("brdKey",brdKey);
        map.put("ans",ans);

        fmBoardService.saveAnswer(map);

        res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
       // resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping(value = "/FM-BOARD005.do")
	public String fmBoard005(@ModelAttribute("searchVO") SearchVO searchVO,
			ModelMap model, final HttpServletRequest req) throws Exception {

		searchVO.setSekAth((String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));

		int totCnt = fmBoardService.fm_board005_cnt(searchVO);
        model.addAttribute("paginationInfo", new PaginationUtil(searchVO, totCnt).getPageData());
        model.addAttribute("resultList", fmBoardService.fm_board005_list(searchVO));
        model.addAttribute("totCnt", totCnt);

        // 2017-04-18 s, 글쓰기 권한
        model.addAttribute("writeAuth", fmBoardService.getAuthoritiesWriteChk(req));

        return "FM-BOARD005";
	}

	@RequestMapping(value = "FM-BOARD005_RW.do")
	public String FM_BOARD005_W(@ModelAttribute("vo") BoardVO vo, Model model,
			HttpServletRequest req) throws Exception {
		model.addAttribute("vo", new BoardVO());

		List list = new ArrayList();
		list = fmBoardService.getSvcList();
		model.addAttribute("divlist", list);
		return "/FM-BOARD005-W";
	}

	@RequestMapping(value = "FM-BOARD005_W.do", method = RequestMethod.POST)
	public String FM_BOARD005_W(@ModelAttribute("vo") BoardVO vo,
			BindingResult bindingResult, Model model,
			final HttpServletRequest req) throws Exception {

		String uploadPath = propertyService.getString("boardUploadPath");
		req.setAttribute("uploadPath", uploadPath);

		FileUpload fu = new FileUpload();
		// parammeter : req, umf_tbl_gbn, umf_con_gbn
		List<FileVO> list = fu.fileuplaod(req, Constants.FILE_GBN_BRD, Constants.FILE_CON_FAQ);

		String division = (String) req.getSession().getAttribute(CommonConfig.SES_SERVICE_KEY);
		String key = (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY);
		vo.setUbm_rgt_id(key);
		vo.setUbm_div_cod(division);
		vo.setBcyCode(CommonConfig.SES_MAN_CYL_KEY);

		fmBoardService.fm_board005_write(vo, list, req);

		fmBoardService.sendMail(vo, list, req);

		return "redirect:/board/FM-BOARD005.do";
	}

	@RequestMapping("FM-BOARD005_V.do")
	public String FM_BOARD005_V(@RequestParam("selectedId") String id,
			Model model, final HttpServletRequest req) throws Exception {

		String s_auth = (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);

		BoardVO vo = new BoardVO();
		vo.setUbm_brd_key(Integer.parseInt(id));
		vo.setAuthKey(s_auth);

		FileVO fvo = new FileVO();
		fvo.setUmf_con_key(id);
		fvo.setUmf_tbl_gbn(Constants.FILE_GBN_BRD);
		fvo.setUmf_con_gbn(Constants.FILE_CON_FAQ);
		BoardVO rVo = fmBoardService.fm_board005_read(vo);

		int b_auth = Integer.parseInt(rVo.getUbm_dwn_ath());

		Map map = new HashMap();
		map.put("brdKey", Integer.parseInt(id));
		map.put("manCyl", req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
		map.put("service", req.getSession().getAttribute(CommonConfig.SES_SERVICE_KEY));

		model.addAttribute("std",fmBoardService.getCntrData(map));
		model.addAttribute("vo", rVo);
		model.addAttribute("file", fmBoardService.fm_file(fvo));

		model.addAttribute("usrKey", (String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		model.addAttribute("authKey", (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));

		return "/FM-BOARD005-V";
	}

	@RequestMapping("FM-BOARD005_R.do")
	public String FM_BOARD005_R(@RequestParam("seq") String seq, Model model,
			final HttpServletRequest req) throws Exception {
		BoardVO vo = new BoardVO();
		vo.setUbm_brd_key(Integer.parseInt(seq));
		FileVO fvo = new FileVO();
		fvo.setUmf_con_key(seq);
		fvo.setUmf_tbl_gbn(Constants.FILE_GBN_BRD);
		fvo.setUmf_con_gbn(Constants.FILE_CON_FAQ);
		BoardVO rVo = fmBoardService.fm_board005_read(vo);

		int b_auth = Integer.parseInt(rVo.getUbm_dwn_ath());
		String s_auth = (String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY);

		model.addAttribute("vo", rVo);
		// 보드권한에 따른 파일리스트 출력여부
		if ((s_auth.equals("A") || (s_auth.equals("V") && b_auth > 1)) || ((s_auth.equals("P") && b_auth > 2))) {
			model.addAttribute("file", fmBoardService.fm_file(fvo));
		}

		List list = new ArrayList();
		list = fmBoardService.getSvcList();
		model.addAttribute("divlist", list);
		return "/FM-BOARD005-W";
	}

	@RequestMapping("FM-BOARD005_U.do")
	public String FM_BOARD005_U(BoardVO vo, BindingResult bindingResult,
			Model model, final HttpServletRequest req) throws Exception {
		String uploadPath = propertyService.getString("boardUploadPath");
		req.setAttribute("uploadPath", uploadPath);
		FileUpload fu = new FileUpload();
		// parammeter : req, umf_tbl_gbn, umf_con_gbn
		List<FileVO> list = fu.fileuplaod(req, Constants.FILE_GBN_BRD, Constants.FILE_CON_FAQ);

		vo.setUbm_rgt_id((String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		vo.setUbm_div_cod((String) req.getSession().getAttribute(CommonConfig.SES_SERVICE_KEY));
		vo.setBcyCode(CommonConfig.SES_MAN_CYL_KEY);
		vo.setAuthKey((String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));

		fmBoardService.fm_board005_update(vo, list);
		String page = "redirect:/board/FM-BOARD005.do";
		return page;
	}

	@RequestMapping("FM-BOARD005_D.do")
	public String FM_BOARD005_D(BoardVO vo, BindingResult bindingResult,
			Model model, final HttpServletRequest req) throws Exception {

		vo.setUbm_rgt_id((String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
		vo.setAuthKey((String) req.getSession().getAttribute(CommonConfig.SES_USER_AUTH_KEY));

		fmBoardService.fm_board005_delete(vo);
		return "redirect:/board/FM-BOARD005.do";
	}
}
