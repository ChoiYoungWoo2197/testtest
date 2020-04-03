package com.uwo.isms.service.impl;

import java.util.Map;

import javax.annotation.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import com.uwo.isms.dao.IsmsBatchDAO;
import com.uwo.isms.service.IsmsBatchService;

import egovframework.rte.psl.dataaccess.util.EgovMap;


@Service("ismsBatchService")
public class IsmsBatchServiceImpl implements IsmsBatchService {

	Logger log = LogManager.getLogger(IsmsBatchServiceImpl.class);

	@Resource(name="ismsBatchDAO")
	private IsmsBatchDAO ismsBatchDAO;

	@Override
	public EgovMap selectLastManagementCycle() {
		return ismsBatchDAO.selectLastUwoMcyMtr();
	}

	@Override
	public void insertManagementCycle(Map<String, String> map) {
		ismsBatchDAO.insertUwoMcyMtr(map);
	}

	@Override
	public void insertManagementCycleData(Map<String, String> map) {

		// 1. insert uwo_svc_mng
		// ismsBatchDAO.insertUwoSvcMng(map);

		// 2. insert uwo_ctr_mtr: 통제항목
		ismsBatchDAO.insertUwoCtrMtr(map);

		// 3. insert uwo_ctr_map: 통제항목 - 서비스 매핑
		ismsBatchDAO.insertUwoCtrMap(map);

		// 4. insert uwo_trc_doc: 정보보호활동
		ismsBatchDAO.insertUwoTrcDoc(map);

		// 5. insert uwo_mng_fle: 정보보호활동의 첨부파일
		ismsBatchDAO.insertUwoMngFle(map);

		// 6. insert uwo_ctr_brd: 통제항목의 보안정책자료실
		ismsBatchDAO.insertUwoCtrBrd(map);

		// 7. insert uwo_trc_ctr: 정보보호활동의 통제항목
		ismsBatchDAO.insertUwoTrcCtr(map);

		// 8. insert uwo_trc_map: 정보보호활동의 담당자
		ismsBatchDAO.insertUwoTrcMap(map);

		// 2017-06-23
		// 9. insert uwo_ass_mtr: 자산정보를 심사자산정보로
		ismsBatchDAO.insertUwoAssMtr(map);
	}
}
