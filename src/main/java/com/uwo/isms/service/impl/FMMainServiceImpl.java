package com.uwo.isms.service.impl;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.uwo.isms.dao.FMMainDAO;
import com.uwo.isms.domain.BoardVO;
import com.uwo.isms.domain.FileVO;
import com.uwo.isms.service.FMMainService;
import com.uwo.isms.web.FMMainController;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service("fmMainService")
public class FMMainServiceImpl implements FMMainService {

	Logger log = LogManager.getLogger(FMMainController.class);

	@Resource(name="fmMainDAO")
	private FMMainDAO fmMainDAO;

	@Override
	public List<?> fmMain_DetailAlarmList(Map map) {
		return fmMainDAO.fmMain_DetailAlarmList(map);
	}

	@Override
	public BoardVO fm_board_read(BoardVO vo) {
		return fmMainDAO.fm_board_read(vo);
	}

	@Override
	public List<?> fm_file(FileVO fvo) {
		return fmMainDAO.fm_file(fvo);
	}

	@Override
	public List<?> MyWorkList(Map map) {
		return fmMainDAO.MyWorkList(map);
	}

	@Override
	public List<?> SubWorkList(Map map) {
		return fmMainDAO.SubWorkList(map);
	}

	@Override
	public List<?> ApprovalWorkList(Map map) {
		return fmMainDAO.ApprovalWorkList(map);
	}

	@Override
	public List<?> AllWorkList(Map map) {
		return fmMainDAO.AllWorkList(map);
	}

	@Override
	public List<?> DatePickList(Map map) {
		return fmMainDAO.DatePickList(map);
	}

	@Override
	public List<?> nPeriodWorkList(Map map) {
		return fmMainDAO.nPeriodWorkList(map);
	}

	@Override
	public List<?> np_popup(Map map) {
		return fmMainDAO.np_popup(map);
	}

	@Override
	public List<?> npDetail(Map map) {
		return fmMainDAO.npDetail(map);
	}

	@Override
	public void saveNpWork(EgovMap eMap) {
		fmMainDAO.saveNpWork(eMap);
	}

	@Override
	public List<?> PrdSearchList(Map map) {
		return fmMainDAO.PrdSearchList(map);
	}

	@Override
	public List<?> NprdSearchList(Map map) {
		return fmMainDAO.NprdSearchList(map);
	}

	@Override
	public List<?> workPercent(Map map) {
		return fmMainDAO.workPercent(map);
	}

	@Override
	public List<?> fmMain_DetailAlarmList(String key) {
		return fmMainDAO.fmMain_DetailAlarmList(key);
	}

	@Override
	public List<?> fmMain_NotiList(Map<String, String> map) {
		return fmMainDAO.fmMain_NotiList(map);
	}

	@Override
	public List<?> fmMain_AlarmList(String key) {
		return fmMainDAO.fmMain_AlarmList(key);
	}

	@Override
	public EgovMap selectComplianceAndDocCount(Map<String, String> map) {
		return fmMainDAO.selectComplianceAndDocCount(map);
	}

	@Override
	public EgovMap selectProgressWorkCount(Map<String, String> map) {
		return fmMainDAO.selectProgressWorkCount(map);
	}

	@Override
	public EgovMap selectPeriodWorkCount(Map<String, String> map) {
		return fmMainDAO.selectPeriodWorkCount(map);
	}

	@Override
	public List<?> selectComplianceTitle(Map<String, String> map) {
		return fmMainDAO.selectComplianceTitle(map);
	}

	@Override
	public EgovMap selectComplianceDetailCount(Map<String, String> map) {
		return fmMainDAO.selectComplianceDetailCount(map);
	}

	@Override
	public EgovMap selectApprovalCount(Map<String, String> map) {
		return fmMainDAO.selectApprovalCount(map);
	}
}
