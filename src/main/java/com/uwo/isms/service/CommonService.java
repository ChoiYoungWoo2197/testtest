package com.uwo.isms.service;

import com.uwo.isms.dao.CommonCodeDAO;
import com.uwo.isms.dao.FMCompsDAO;
import com.uwo.isms.dao.LoginDAO;
import com.uwo.isms.domain.LoginVO;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommonService {

    @Autowired
    private FMCompsDAO fmCompsDAO;

    @Autowired
    private LoginDAO loginDAO;

    @Autowired
    private CommonCodeDAO commonCodeDAO;

    /**
     * 모든 서비스 목록을 가지고 온다.
     */
    public List getAllServiceList() {
        return fmCompsDAO.fm_comps003_svclist();
    }

    /**
     * 모든 대운영주기 목록을 가지고 온다.
     */
    public List getAllMainCycleList() {
        List<EgovMap> cycleList = (List<EgovMap>) commonCodeDAO.getManCycleSelectBoxCode("");
        for (EgovMap cycle : cycleList) {
            cycle.put("year", 2000 + Integer.valueOf((String) cycle.get("code")));
        }
        return cycleList;
    }

}
