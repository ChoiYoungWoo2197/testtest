/**
 ***********************************
 * @source LiginService.java
 * @date 2014. 10. 13.
 * @project isms3
 * @description
 ***********************************
 */
package com.uwo.isms.service;

import java.util.List;
import java.util.Map;

import com.uwo.isms.domain.LoginVO;
import egovframework.rte.psl.dataaccess.util.EgovMap;


public interface LoginService {

	/**
     * 2011.08.26
	 * EsntlId를 이용한 로그인을 처리한다
	 * @param vo UserVO
	 * @return UserVO
	 * @exception Exception
	 */
    public LoginVO actionLoginByEsntlId(LoginVO vo) throws Exception;

	/**
	 * 일반 로그인을 처리한다
	 * @param vo UserVO
	 * @return UserVO
	 * @exception Exception
	 */
    LoginVO actionLogin(LoginVO vo) throws Exception;

    /**
	 * 인증서 로그인을 처리한다
	 * @param vo UserVO
	 * @return UserVO
	 * @exception Exception
	 */
    LoginVO actionCrtfctLogin(LoginVO vo) throws Exception;

    /**
	 * 아이디를 찾는다.
	 * @param vo UserVO
	 * @return UserVO
	 * @exception Exception
	 */
    LoginVO searchId(LoginVO vo) throws Exception;

    /**
	 * 비밀번호를 찾는다.
	 * @param vo UserVO
	 * @return boolean
	 * @exception Exception
	 */
    boolean searchPassword(LoginVO vo) throws Exception;

	/**
	 * @param loginVO
	 * @return
	 */
	public LoginVO getManCyl(LoginVO loginVO);

	public void insertLogInfo(LoginVO logVO);

	public String isExistId(String id);

	public String getLoginFailCnt(String userKey);

	public void setLoginFailCnt(EgovMap map);

	public List<?> getMenuList(String userkey);

	public List<?> getMenuAuthList(String userkey);

	public List<?> getSpdMenuList(String getuum_usr_key);

	public EgovMap getUser(String decString);

	/*
	 * 2017-06-14, 비밀번호 변경
	 */
	public String changePassword(Map<String, String> map);
}
