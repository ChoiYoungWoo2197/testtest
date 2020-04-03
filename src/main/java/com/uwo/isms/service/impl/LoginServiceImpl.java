/**
 ***********************************
 * @source LoginServiceImpl.java
 * @date 2014. 10. 14.
 * @project isms3
 * @description
 ***********************************
 */
package com.uwo.isms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.stereotype.Service;

import com.uwo.isms.dao.LoginDAO;
import com.uwo.isms.domain.LoginVO;
import com.uwo.isms.service.FMSetupService;
import com.uwo.isms.service.LoginService;
import com.uwo.isms.web.FMSetupController;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service("fmLoginService")
public class LoginServiceImpl extends EgovAbstractServiceImpl implements LoginService {

	Logger log = LogManager.getLogger(FMSetupController.class);

	@Resource(name="fmLoginDAO")
    private LoginDAO loginDAO;

	@Resource(name = "fmSetupService")
	private FMSetupService fmSetupService;

	private static MessageDigestPasswordEncoder passwordEncoder = new MessageDigestPasswordEncoder("SHA-256");

    /**
	 * 일반 로그인을 처리한다
	 * @param vo LoginVO
	 * @return LoginVO
	 * @exception Exception
	 */
    public LoginVO actionLogin(LoginVO vo) throws Exception {

    	// 1. 입력한 비밀번호를 암호화한다.
//    	String enpassword = EgovFileScrty.encryptPassword(vo.getPassword());
//    	vo.setPassword(enpassword);
    	if (vo.getIsSso() == false) {
        	String encodedPassword = passwordEncoder.encodePassword(vo.getPassword(), null);
        	vo.setPassword(encodedPassword);
    	}

    	// 2. 아이디와 암호화된 비밀번호가 DB와 일치하는지 확인한다.
    	LoginVO loginVO = loginDAO.actionLogin(vo);

    	// 3. 결과를 리턴한다.
    	if (loginVO != null && !loginVO.getId().equals("") && !loginVO.getPassword().equals("")) {
    		return loginVO;
    	} else {
    		loginVO = new LoginVO();
    	}

    	return loginVO;
    }

	/* (non-Javadoc)
	 * @see com.uwo.isms.service.LoginService#actionLoginByEsntlId(egovframework.com.cmm.LoginVO)
	 */
	@Override
	public LoginVO actionLoginByEsntlId(LoginVO vo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uwo.isms.service.LoginService#actionCrtfctLogin(egovframework.com.cmm.LoginVO)
	 */
	@Override
	public LoginVO actionCrtfctLogin(LoginVO vo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uwo.isms.service.LoginService#searchId(egovframework.com.cmm.LoginVO)
	 */
	@Override
	public LoginVO searchId(LoginVO vo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uwo.isms.service.LoginService#searchPassword(egovframework.com.cmm.LoginVO)
	 */
	@Override
	public boolean searchPassword(LoginVO vo) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.uwo.isms.service.LoginService#getManCyl()
	 */
	@Override
	public LoginVO getManCyl(LoginVO loginVO) {

		LoginVO manCyl = loginDAO.getManCyl(loginVO);

    	// 3. 결과를 리턴한다.
    	if (manCyl != null && !manCyl.getUmm_man_cyl().equals("")) {
    		return manCyl;
    	} else {
    		manCyl = loginDAO.getManCylRetry(loginVO);
    	}

    	return manCyl;
	}

	@Override
	public void insertLogInfo(LoginVO logVO) {
		loginDAO.insertLogInfo(logVO);
	}

	@Override
	public String isExistId(String id) {
		return loginDAO.isExistId(id);
	}

	@Override
	public String getLoginFailCnt(String userKey) {
		return loginDAO.getLoginFailCnt(userKey);
	}

	@Override
	public void setLoginFailCnt(EgovMap map) {
		loginDAO.setLoginFailCnt(map);
	}

	@Override
	public List<?> getMenuList(String userkey) {
		List<?> listMenu = loginDAO.getMenuList(userkey);
		Map<String, String> mapMenuLv1 = new HashMap<>();
		Map<String, Object> mapTempMenu;
		String strPrt = "";
		String strUrl = "";
		for(int i = 0; i< listMenu.size();i++) {
			mapTempMenu = (Map<String, Object>) listMenu.get(i);
			//System.out.println(mapTempMenu);
			if(mapTempMenu.get("ummMnuPrt")==null&&mapTempMenu.get("ummMnuLvl").toString().equals("1")){
				mapMenuLv1.put(mapTempMenu.get("ummMnuKey").toString(), mapTempMenu.get("ummMnuUrl").toString());
			}else{
				//System.out.println(strPrt);
				if(mapTempMenu.get("ummMnuPrt")!=null){
					if(!mapTempMenu.get("ummMnuPrt").toString().equals(strPrt)){
						strPrt=mapTempMenu.get("ummMnuPrt").toString();
						strUrl=mapMenuLv1.get(strPrt);
						mapMenuLv1.put(strPrt, mapTempMenu.get("ummMnuUrl").toString());
					}else{
						if(mapTempMenu.get("ummMnuUrl").toString().equals(strUrl)){
							mapMenuLv1.put(strPrt, strUrl);
						}
					}
				}
			}
		}

		for(int i = 0; i< listMenu.size();i++) {
			mapTempMenu = (Map<String, Object>) listMenu.get(i);
			if(mapTempMenu.get("ummMnuPrt")==null&&mapTempMenu.get("ummMnuLvl").toString().equals("1")){
				mapTempMenu.put("ummMnuUrl", mapMenuLv1.get(mapTempMenu.get("ummMnuKey").toString()));

			}

		}
		return listMenu;
	}

	@Override
	public List<?> getMenuAuthList(String userkey) {
		return loginDAO.getMenuAuthList(userkey);
	}

	@Override
	public List<?> getSpdMenuList(String userkey) {
		return loginDAO.getSpdMenuList(userkey);
	}

	@Override
	public EgovMap getUser(String decString) {
		return loginDAO.getUser(decString);
	}

	/*
	 * 2017-06-14, 비밀번호 변경
	 */
	@Override
	public String changePassword(Map<String, String> map) {
    	return fmSetupService.fmSetup007_pwd_update(map);
	}
}
