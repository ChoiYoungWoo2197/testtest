package com.uwo.isms.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.uwo.isms.domain.BoardVO;
import com.uwo.isms.domain.FileVO;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Repository("fmMainDAO")
public class FMMainDAO extends EgovAbstractDAO  {

	public List<?> fmMain_DetailAlarmList(Map map) {
		return list("fmMain.QR_MAIN_A",map);
	}

	public BoardVO fm_board_read(BoardVO vo) {
		return (BoardVO) select("fmMain.QR_MAIN_B", vo);
	}

	public List<?> fm_file(FileVO fvo) {
		return list("fileup.QR_FILE_LIST",fvo);
	}

	public List<?> MyWorkList(Map map) {
		return list("fmMain.QR_MAIN_C",map);
	}

	public List<?> SubWorkList(Map map) {
		return list("fmMain.QR_MAIN_D",map);
	}

	public List<?> ApprovalWorkList(Map map) {
		return list("fmMain.QR_MAIN_E",map);
	}

	public List<?> AllWorkList(Map map) {
		return list("fmMain.QR_MAIN_F",map);
	}

	public List<?> DatePickList(Map map) {
		return list("fmMain.QR_MAIN_G",map);
	}

	public List<?> nPeriodWorkList(Map map) {
		return list("fmMain.QR_MAIN_H",map);
	}

	public List<?> np_popup(Map map) {
		return list("fmMain.QR_MAIN_I",map);
	}

	public List<?> npDetail(Map map) {
		return list("fmMain.QR_MAIN_J",map);
	}

	public void saveNpWork(EgovMap eMap) {
		insert("fmMain.QR_MAIN_K",eMap);
	}

	public List<?> PrdSearchList(Map map) {
		return list("fmMain.QR_MAIN_L",map);
	}

	public List<?> NprdSearchList(Map map) {
		return list("fmMain.QR_MAIN_M",map);
	}

	public List<?> workPercent(Map map) {
		return list("fmMain.QR_MAIN_N",map);
	}

	public List<?> fmMain_DetailAlarmList(String key) {
		return list("fmMain.QR_MAIN_O",key);
	}

	public List<?> fmMainLeftMenu(Map<String, String> map) {
		return list("fmMain.QR_MAIN_LEFT_MENU_LIST",map);
	}

	public List<?> fmMain_NotiList(Map<String, String> map) {
		return list("fmMain.QR_MAIN_NOTI_LIST",map);
	}

	public List<?> fmMain_AlarmList(String key) {
		return list("fmMain.QR_MAIN_ALARM_LIST",key);
	}

	public EgovMap selectComplianceAndDocCount(Map<String, String> map) {
		return (EgovMap) select("fmMain.selectComplianceAndDocCount", map);
	}

	public EgovMap selectProgressWorkCount(Map<String, String> map) {
		return (EgovMap) select("fmMain.selectProgressWorkCount", map);
	}

	public EgovMap selectPeriodWorkCount(Map<String, String> map) {
		return (EgovMap) select("fmMain.selectPeriodWorkCount", map);
	}

	public List<?> selectComplianceTitle(Map<String, String> map) {
		return list("fmMain.selectComplianceTitle", map);
	}

	public EgovMap selectComplianceDetailCount(Map<String, String> map) {
		return (EgovMap) select("fmMain.selectComplianceDetailCount", map);
	}

	public EgovMap selectApprovalCount(Map<String, String> map) {
		return (EgovMap) select("fmMain.selectApprovalCount", map);
	}

}
