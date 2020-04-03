/**
 ***********************************
 * @source LoginDAO.java
 * @date 2014. 10. 14.
 * @project isms3
 * @description 
 ***********************************
 */
package com.uwo.isms.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.uwo.isms.domain.LoginVO;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Repository("fmLoginDAO")
public class LoginDAO extends EgovAbstractDAO {

	/**
	 * 일반 로그인을 처리한다
	 * @param vo LoginVO
	 * @return LoginVO
	 * @exception Exception
	 */
    public LoginVO actionLogin(LoginVO vo) throws Exception {
    	return (LoginVO)select("fmLogin.actionLogin", vo);
    }

	/**
	 * @return
	 */
	public LoginVO getManCyl(LoginVO loginVO) {
		return (LoginVO)select("fmLogin.getManCyl", loginVO);
	}

	/**
	 * @return
	 */
	public LoginVO getManCylRetry(LoginVO loginVO) {
		return (LoginVO)select("fmLogin.getManCylRetry", loginVO);
	}

	public void insertLogInfo(LoginVO logVO) {
		insert("fmLogin.INSERT_lOG_INFO", logVO);
	}

	public String isExistId(String id) {
		return (String) select("fmLogin.isExistId", id);
	}

	public String getLoginFailCnt(String userKey) {
		return (String) select("fmLogin.getFailCnt", userKey);
	}

	public void setLoginFailCnt(EgovMap map) {
		update("fmLogin.setLoginFailCnt", map);
	}
	
	public List<?> getMenuList(String userkey) {
		return list("fmLogin.getMenuList", userkey);
	}
	
	public List<?> getMenuAuthList(String userkey) {
		return list("fmLogin.getMenuAuthList", userkey);
	}

	public List<?> getSpdMenuList(String userkey) {
		return list("fmLogin.getSpdMenuList", userkey);
	}

	public EgovMap getUser(String decString) {
		return (EgovMap) select("fmLogin.getUser", decString);
	}
}
