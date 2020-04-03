/**
 ***********************************
 * @source CommonCodeService.java
 * @date 2014. 10. 20.
 * @project isms3_godohwa
 * @description
 ***********************************
 */
package com.uwo.isms.service;

import java.util.List;
import java.util.Map;

import com.uwo.isms.domain.SearchVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface CommonCodeService {

	/**
	 * @return
	 */

	public List<?> getCommonCodeList(String code);

	public List<?> getSvcCodeList(String code);

	public List<?> getDepCodeList();

	public List<?> getNonCodeList(String code);

	public List<?> getSampleCode();

	public List<?> getDivCode();

	public List<?> getSvcCode(String stnd);

	public List<?> getAuhList();

	/**
	 * @param searchVO
	 * @return
	 */
	public List<?> getSelectBoxCode(SearchVO searchVO);

	public List<?> getManCycleSelectBoxCode(String company);

	public String getMailName(String key);

	public List<?> getServiceAuthList(Map<String, String> map);

	public List<?> getServiceAuthList_N(Map<String, String> map);

	public List<?> getServiceAuthList_CAT(Map<String, String> map);

	public List<?> getDeptAuthList(Map<String, String> map);

	/**
	 * 담당자
	 * @param deptCode
	 * @return
	 */
	public List<?> getWorkerList(Map<String, String> map);

	public List<?> getOprusrList(Map<String, String> map);

	/**
	 * 자산 유형 코드
	 * @return
	 */
	public List<?> getAssCatList();

	/**
	 * 자산 유형 코드
	 * @return
	 */
	public List<?> getAssCatList(Map<String, String> map);

	/**
	 * 취약점 유형 코드
	 * @return
	 */
	public List<?> getVlbList();

	/**
	 * 중요도 코드
	 * @return
	 */
	public List<?> getVlbImpList();

	/**
	 * 자산 분류 코드
	 * @return
	 */
	public List<?> getAssGbnList();

	// 위험관리항목 - 기술적
	public List<?> getAssCatListA();
	// 위험관리항목 - 관리적
	public List<?> getAssCatListD();

	/**
	 * 위험관리 시나리오 코드
	 * @return
	 */
	public List<?> getSenarioList(Map<String, String> map);

	/**
	 * 우려사항 코드
	 * @return
	 */
	public List<?> getRiskCocList(String assCat);

	public List<?> getDeptList(Map<String, String> map);

	public List<?> getDeptCodeList(String udmDepLvl);

	public List<?> getDepartList(Map<String, String> map);

	public List<?> getOprtList(Map<String, String> map);

	/**
	 * DOA 코드 조회
	 * @param map
	 * @return
	 */
	public List<?> getDoaList();
	/**
	 * DOA 초기값
	 * @return
	 */
	public EgovMap getDoa();
}
