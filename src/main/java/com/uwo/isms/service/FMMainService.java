package com.uwo.isms.service;

import java.util.List;
import java.util.Map;

import com.uwo.isms.domain.BoardVO;
import com.uwo.isms.domain.FileVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface FMMainService {

	List<?> fmMain_DetailAlarmList(Map map);

	BoardVO fm_board_read(BoardVO vo);

	List<?> fm_file(FileVO fvo);

	List<?> MyWorkList(Map map);

	List<?> SubWorkList(Map map);

	List<?> ApprovalWorkList(Map map);

	List<?> AllWorkList(Map map);

	List<?> DatePickList(Map map);

	List<?> nPeriodWorkList(Map map);

	List<?> np_popup(Map map);

	List<?> npDetail(Map map);

	void saveNpWork(EgovMap eMap);

	List<?> PrdSearchList(Map map);

	List<?> NprdSearchList(Map map);

	List<?> workPercent(Map map);

	List<?> fmMain_DetailAlarmList(String key);

	List<?> fmMain_NotiList(Map<String, String> map);

	List<?> fmMain_AlarmList(String key);

	EgovMap selectComplianceAndDocCount(Map<String, String> map);

	EgovMap selectProgressWorkCount(Map<String, String> map);

	EgovMap selectPeriodWorkCount(Map<String, String> map);

	List<?> selectComplianceTitle(Map<String, String> map);

	EgovMap selectComplianceDetailCount(Map<String, String> map);

	EgovMap selectApprovalCount(Map<String, String> map);
}
