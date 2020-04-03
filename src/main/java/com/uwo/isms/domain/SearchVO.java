package com.uwo.isms.domain;

/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/**
 * @Class Name : SampleDefaultVO.java
 * @Description : SampleDefaultVO Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2009.03.16           최초생성
 *
 * @author 개발프레임웍크 실행환경 개발팀
 * @since 2009. 03.16
 * @version 1.0
 * @see
 *
 *  Copyright (C) by MOPAS All right reserved.
 */
public class SearchVO implements Serializable {

	/**
	 *  serialVersion UID
	 */
	private static final long serialVersionUID = -858838578081269359L;

	/** 검색조건 한 개일 때  */
    private String searchCondition = "";

    /** 검색Keyword  */
    private String searchKeyword = "";

    /** 검색사용여부 */
    private String searchUseYn = "";

    /** 현재페이지 */
    private int pageIndex = 1;

    /** 페이지갯수 */
    private int pageUnit = 10;

    /** 페이지사이즈 */
    private int pageSize = 15;

    /** firstIndex */
    private int firstIndex = 1;

    /** lastIndex */
    private int lastIndex = 1;

    /** recordCountPerPage */
    private int recordCountPerPage = 10;

    /** 사업부 code **/
    private String division;

    /** 사업부 name **/
    private String divisionName;

    /** 서비스 code **/
    private String service;
    private String popService;

    /** 대주기 코드 */
    private String bcyCode;

    /** 서비스 name **/
    private String serviceName;

    /** 부서 code **/
    private String dept;

    /** 담당자 code **/
    private String worker;

    /** 담당자 name **/
    private String workerName;

    /** 업무주기 **/
    private String workTerm;

    /** 주기 code **/
    private String workCycleTerm;

    /** 주기 name **/
    private String workCycleTermName;

    /** 비주기 code **/
    private String workNonCycleTerm;

    /** 비주기 name **/
    private String workNonCycleTermName;

    /** 표준 code **/
    private String standard;

    /** 표준 name **/
    private String stndNam;

	/** 대분류 Code **/
    private String firCod;

    /** 대분류 name **/
    private String firNam;

    /** 업무양식 code **/
    private String sampleCode;

    /** 업무양식 name **/
    private String sampleCodeName;

    /** 승인 code **/
    private String approval;

    /** 승인 name **/
    private String approvalName;

    /** 업무권한 code **/
    private String auth;

    /** 업무권한  name **/
    private String authName;

    /** 항목번호 **/
    private String goalNum;

    /** 업무명 **/
    private String workName;

    /** 업무메모 **/
    private String workMemo;

    /** work code **/
    private String workCode;

    /** doc code **/
    private String docCode;

    /** code **/
    private String code;

    /** sqlUrl **/
    private String sqlUrl;

    /** 대주기 seq **/
    private String manCyl;

    /** 조회권한 **/
    private String sekAth;

    /** 다운권한 **/
    private String dwnAth;

    /** 업무시작일 **/
	private String strDat;


	/** 업무만료일 **/
	private String endDat;

    /****/
    private String company;

    private String isDelay;

    private String assGbn;

    private String groupGbn;

    private String groupCode;

    private String groupName;

    private String wrkFileName;

    private String workState;

    private String sidx;

    private String order;

    private String depth1;

    private String depth2;

    private String depth3;

    private String wrkUptMdh;

    private String utwComSta;

    /** 자산관련   **/
    private String assCod;

    private String assNam;

    private String ualEqpNam;

    private String ualOwnNam;

    private String ualUseNam;

    private String year;

    private String month;

    private String mngCode;

    private String mngName;

    private String assLvl;

    private String audYn;

    private String smpYn;

    private String rgtId;

    private String rgtNam;

    private String rgtMdh;

    private String aosCod;

    private String assCat;

    private String catKey;

    private PaginationInfo paginationInfo;

    private String stOrg;

    private String hqOrg;

    private String gpOrg;

    private String ustOrg;

    private String uhqOrg;

    private String ugpOrg;

    private String oprusr;

    private String picusr;

    private String admusr;

    private String uarDepLv1;

    private String uarDepLv2;

    private String uarDepCod;

    private String uarOprLv1;

    private String uarOprLv2;

    private String uarOprCod;

    private String uarOwnId;

    private String uarAdmId;

    private String uarUseId;

    private String uarPicId;

    private String uar_dep_lv1;

    private String usrkey;

    private String findusr;

    private String utdDocNam;

    private String wrkKey;

    private String addList;

    private String utwWrkId;

    private String wrkOldId;

    private String useYn;

    private String controlItem;

    // 2018-05-11 s, 예비용
    private String searchExt1;
    private String searchExt2;
    private String searchExt3;
    private String searchExt4;
    private String searchExt5;

	public String getSearchExt1() {
		return searchExt1;
	}

	public void setSearchExt1(String searchExt1) {
		this.searchExt1 = searchExt1;
	}

	public String getSearchExt2() {
		return searchExt2;
	}

	public void setSearchExt2(String searchExt2) {
		this.searchExt2 = searchExt2;
	}

	public String getSearchExt3() {
		return searchExt3;
	}

	public void setSearchExt3(String searchExt3) {
		this.searchExt3 = searchExt3;
	}

	public String getSearchExt4() {
		return searchExt4;
	}

	public void setSearchExt4(String searchExt4) {
		this.searchExt4 = searchExt4;
	}

	public String getSearchExt5() {
		return searchExt5;
	}

	public void setSearchExt5(String searchExt5) {
		this.searchExt5 = searchExt5;
	}

	public void setWrkOldId(String wrkOldId) {
		this.wrkOldId = wrkOldId;
	}

	public String getPopService() {
		return popService;
	}

	public void setPopService(String popService) {
		this.popService = popService;
	}

	public String getUtwWrkId() {
		return utwWrkId;
	}

	public void setUtwWrkId(String utwWrkId) {
		this.utwWrkId = utwWrkId;
	}

	public String getWrkKey() {
		return wrkKey;
	}

	public void setWrkKey(String wrkKey) {
		this.wrkKey = wrkKey;
	}

	public String getAddList() {
		return addList;
	}

	public void setAddList(String addList) {
		this.addList = addList;
	}

	public String getUtdDocNam() {
		return utdDocNam;
	}

	public void setUtdDocNam(String utdDocNam) {
		this.utdDocNam = utdDocNam;
	}

	public String getFindusr() {
		return findusr;
	}

	public void setFindusr(String findusr) {
		this.findusr = findusr;
	}

	public String getUsrkey() {
		return usrkey;
	}

	public void setUsrkey(String usrkey) {
		this.usrkey = usrkey;
	}

	public String getUar_dep_lv1() {
		return uar_dep_lv1;
	}

	public void setUar_dep_lv1(String uar_dep_lv1) {
		this.uar_dep_lv1 = uar_dep_lv1;
	}

	public String getUarDepLv1() {
		return uarDepLv1;
	}

	public void setUarDepLv1(String uarDepLv1) {
		this.uarDepLv1 = uarDepLv1;
	}

	public String getUarDepLv2() {
		return uarDepLv2;
	}

	public void setUarDepLv2(String uarDepLv2) {
		this.uarDepLv2 = uarDepLv2;
	}

	public String getUarOprLv1() {
		return uarOprLv1;
	}

	public void setUarOprLv1(String uarOprLv1) {
		this.uarOprLv1 = uarOprLv1;
	}

	public String getUarOprLv2() {
		return uarOprLv2;
	}

	public void setUarOprLv2(String uarOprLv2) {
		this.uarOprLv2 = uarOprLv2;
	}

	public String getUarAdmId() {
		return uarAdmId;
	}

	public void setUarAdmId(String uarAdmId) {
		this.uarAdmId = uarAdmId;
	}

	public String getUarUseId() {
		return uarUseId;
	}

	public void setUarUseId(String uarUseId) {
		this.uarUseId = uarUseId;
	}

	public String getUarPicId() {
		return uarPicId;
	}

	public void setUarPicId(String uarPicId) {
		this.uarPicId = uarPicId;
	}

	public String getUarOwnId() {
		return uarOwnId;
	}

	public void setUarOwnId(String uarOwnId) {
		this.uarOwnId = uarOwnId;
	}

	public String getUarOprCod() {
		return uarOprCod;
	}

	public void setUarOprCod(String uarOprCod) {
		this.uarOprCod = uarOprCod;
	}

	public String getUarDepCod() {
		return uarDepCod;
	}

	public void setUarDepCod(String uarDepCod) {
		this.uarDepCod = uarDepCod;
	}

	public String getAdmusr() {
		return admusr;
	}

	public void setAdmusr(String admusr) {
		this.admusr = admusr;
	}

	public String getPicusr() {
		return picusr;
	}

	public void setPicusr(String picusr) {
		this.picusr = picusr;
	}

	public String getOprusr() {
		return oprusr;
	}

	public void setOprusr(String oprusr) {
		this.oprusr = oprusr;
	}

	public String getUstOrg() {
		return ustOrg;
	}

	public void setUstOrg(String ustOrg) {
		this.ustOrg = ustOrg;
	}

	public String getUhqOrg() {
		return uhqOrg;
	}

	public void setUhqOrg(String uhqOrg) {
		this.uhqOrg = uhqOrg;
	}

	public String getUgpOrg() {
		return ugpOrg;
	}

	public void setUgpOrg(String ugpOrg) {
		this.ugpOrg = ugpOrg;
	}

	public String getStrDat() {
		return strDat;
	}

	public void setStrDat(String strDat) {
		this.strDat = strDat;
	}

	public String getEndDat() {
		return endDat;
	}

	public void setEndDat(String endDat) {
		this.endDat = endDat;
	}

    public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getAssCod() {
		return assCod;
	}

	public void setAssCod(String assCod) {
		this.assCod = assCod;
	}

	public String getAssNam() {
		return assNam;
	}

	public void setAssNam(String assNam) {
		this.assNam = assNam;
	}

	public String getUalEqpNam() {
		return ualEqpNam;
	}

	public void setUalEqpNam(String ualEqpNam) {
		this.ualEqpNam = ualEqpNam;
	}

	public String getUalOwnNam() {
		return ualOwnNam;
	}

	public void setUalOwnNam(String ualOwnNam) {
		this.ualOwnNam = ualOwnNam;
	}

    public String getDepth1() {
		return depth1;
	}

	public void setDepth1(String depth1) {
		this.depth1 = depth1;
	}

	public String getDepth2() {
		return depth2;
	}

	public void setDepth2(String depth2) {
		this.depth2 = depth2;
	}

	public String getDepth3() {
		return depth3;
	}

	public void setDepth3(String depth3) {
		this.depth3 = depth3;
	}

	public String getWrkFileName() {
		return wrkFileName;
	}

	public void setWrkFileName(String wrkFileName) {
		this.wrkFileName = wrkFileName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSekAth() {
		return sekAth;
	}

	public void setSekAth(String sekAth) {
		this.sekAth = sekAth;
	}

	public String getDwnAth() {
		return dwnAth;
	}

	public void setDwnAth(String dwnAth) {
		this.dwnAth = dwnAth;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public int getFirstIndex() {
		return firstIndex;
	}

	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}

	public int getLastIndex() {
		return lastIndex;
	}

	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}

	public int getRecordCountPerPage() {
		return recordCountPerPage;
	}

	public void setRecordCountPerPage(int recordCountPerPage) {
		this.recordCountPerPage = recordCountPerPage;
	}

	public String getSearchCondition() {
        return searchCondition;
    }

    public void setSearchCondition(String searchCondition) {
        this.searchCondition = searchCondition;
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public String getSearchUseYn() {
        return searchUseYn;
    }

    public void setSearchUseYn(String searchUseYn) {
        this.searchUseYn = searchUseYn;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageUnit() {
        return pageUnit;
    }

    public void setPageUnit(int pageUnit) {
        this.pageUnit = pageUnit;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

	/**
	 * @return the divisionName
	 */
	public String getDivisionName() {
		return divisionName;
	}

	/**
	 * @param divisionName the divisionName to set
	 */
	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	/**
	 * @return the service
	 */
	public String getService() {
		return service;
	}

	/**
	 * @param service the service to set
	 */
	public void setService(String service) {
		this.service = service;
	}

	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * @param serviceName the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * @return the worker
	 */
	public String getWorker() {
		return worker;
	}

	/**
	 * @param worker the worker to set
	 */
	public void setWorker(String worker) {
		this.worker = worker;
	}

	/**
	 * @return the workerName
	 */
	public String getWorkerName() {
		return workerName;
	}

	/**
	 * @param workerName the workerName to set
	 */
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}

	/**
	 * @return the workTerm
	 */
	public String getWorkTerm() {
		return workTerm;
	}

	/**
	 * @param workTerm the workTerm to set
	 */
	public void setWorkTerm(String workTerm) {
		this.workTerm = workTerm;
	}

	/**
	 * @return the workCycleTerm
	 */
	public String getWorkCycleTerm() {
		return workCycleTerm;
	}

	/**
	 * @param workCycleTerm the workCycleTerm to set
	 */
	public void setWorkCycleTerm(String workCycleTerm) {
		this.workCycleTerm = workCycleTerm;
	}

	/**
	 * @return the workCycleTermName
	 */
	public String getWorkCycleTermName() {
		return workCycleTermName;
	}

	/**
	 * @param workCycleTermName the workCycleTermName to set
	 */
	public void setWorkCycleTermName(String workCycleTermName) {
		this.workCycleTermName = workCycleTermName;
	}

	/**
	 * @return the workNonCycleTerm
	 */
	public String getWorkNonCycleTerm() {
		return workNonCycleTerm;
	}

	/**
	 * @param workNonCycleTerm the workNonCycleTerm to set
	 */
	public void setWorkNonCycleTerm(String workNonCycleTerm) {
		this.workNonCycleTerm = workNonCycleTerm;
	}

	/**
	 * @return the workNonCycleTermName
	 */
	public String getWorkNonCycleTermName() {
		return workNonCycleTermName;
	}

	/**
	 * @param workNonCycleTermName the workNonCycleTermName to set
	 */
	public void setWorkNonCycleTermName(String workNonCycleTermName) {
		this.workNonCycleTermName = workNonCycleTermName;
	}

	/**
	 * @return the standard
	 */
	public String getStandard() {
		return standard;
	}

	/**
	 * @param standard the standard to set
	 */
	public void setStandard(String standard) {
		this.standard = standard;
	}

	/**
	 * @return the stndNam
	 */
	public String getStndNam() {
		return stndNam;
	}

	/**
	 * @param stndNam the stndNam to set
	 */
	public void setStndNam(String stndNam) {
		this.stndNam = stndNam;
	}

	/**
	 * @return the sampleCode
	 */
	public String getSampleCode() {
		return sampleCode;
	}

	/**
	 * @param sampleCode the sampleCode to set
	 */
	public void setSampleCode(String sampleCode) {
		this.sampleCode = sampleCode;
	}

	/**
	 * @return the sampleCodeName
	 */
	public String getSampleCodeName() {
		return sampleCodeName;
	}

	/**
	 * @param sampleCodeName the sampleCodeName to set
	 */
	public void setSampleCodeName(String sampleCodeName) {
		this.sampleCodeName = sampleCodeName;
	}

	/**
	 * @return the approval
	 */
	public String getApproval() {
		return approval;
	}

	/**
	 * @param approval the approval to set
	 */
	public void setApproval(String approval) {
		this.approval = approval;
	}

	/**
	 * @return the approvalName
	 */
	public String getApprovalName() {
		return approvalName;
	}

	/**
	 * @param approvalName the approvalName to set
	 */
	public void setApprovalName(String approvalName) {
		this.approvalName = approvalName;
	}

	/**
	 * @return the auth
	 */
	public String getAuth() {
		return auth;
	}

	/**
	 * @param auth the auth to set
	 */
	public void setAuth(String auth) {
		this.auth = auth;
	}

	/**
	 * @return the authName
	 */
	public String getAuthName() {
		return authName;
	}

	/**
	 * @param authName the authName to set
	 */
	public void setAuthName(String authName) {
		this.authName = authName;
	}

	/**
	 * @return the goalNum
	 */
	public String getGoalNum() {
		return goalNum;
	}

	/**
	 * @param goalNum the goalNum to set
	 */
	public void setGoalNum(String goalNum) {
		this.goalNum = goalNum;
	}

	/**
	 * @return the workName
	 */
	public String getWorkName() {
		return workName;
	}

	/**
	 * @param workName the workName to set
	 */
	public void setWorkName(String workName) {
		this.workName = workName;
	}

	/**
	 * @return the workMemo
	 */
	public String getWorkMemo() {
		return workMemo;
	}

	/**
	 * @param workMemo the workMemo to set
	 */
	public void setWorkMemo(String workMemo) {
		this.workMemo = workMemo;
	}

	/**
	 * @return the workCode
	 */
	public String getWorkCode() {
		return workCode;
	}

	/**
	 * @param workCode the workCode to set
	 */
	public void setWorkCode(String workCode) {
		this.workCode = workCode;
	}

	/**
	 * @return the docCode
	 */
	public String getDocCode() {
		return docCode;
	}

	/**
	 * @param docCode the docCode to set
	 */
	public void setDocCode(String docCode) {
		this.docCode = docCode;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the sqlUrl
	 */
	public String getSqlUrl() {
		return sqlUrl;
	}

	/**
	 * @param sqlUrl the sqlUrl to set
	 */
	public void setSqlUrl(String sqlUrl) {
		this.sqlUrl = sqlUrl;
	}

	/**
	 * @return the manCyl
	 */
	public String getManCyl() {
		return manCyl;
	}

	/**
	 * @param manCyl the manCyl to set
	 */
	public void setManCyl(String manCyl) {
		this.manCyl = manCyl;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getIsDelay() {
		return isDelay;
	}

	public void setIsDelay(String isDelay) {
		this.isDelay = isDelay;
	}

	public String getAssGbn() {
		return assGbn;
	}

	public void setAssGbn(String assGbn) {
		this.assGbn = assGbn;
	}

	public String getGroupGbn() {
		return groupGbn;
	}

	public void setGroupGbn(String groupGbn) {
		this.groupGbn = groupGbn;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getWorkState() {
		return workState;
	}

	public void setWorkState(String workState) {
		this.workState = workState;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getFirCod() {
		return firCod;
	}

	public void setFirCod(String firCod) {
		this.firCod = firCod;
	}

	public String getFirNam() {
		return firNam;
	}

	public void setFirNam(String firNam) {
		this.firNam = firNam;
	}

	public String getMngCode() {
		return mngCode;
	}

	public void setMngCode(String mngCode) {
		this.mngCode = mngCode;
	}

	public String getMngName() {
		return mngName;
	}

	public void setMngName(String mngName) {
		this.mngName = mngName;
	}

	public String getAssLvl() {
		return assLvl;
	}

	public void setAssLvl(String assLvl) {
		this.assLvl = assLvl;
	}

	public String getAudYn() {
		return audYn;
	}

	public void setAudYn(String audYn) {
		this.audYn = audYn;
	}

	public String getSmpYn() {
		return smpYn;
	}

	public void setSmpYn(String smpYn) {
		this.smpYn = smpYn;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getUalUseNam() {
		return ualUseNam;
	}

	public void setUalUseNam(String ualUseNam) {
		this.ualUseNam = ualUseNam;
	}

	public String getRgtId() {
		return rgtId;
	}

	public void setRgtId(String rgtId) {
		this.rgtId = rgtId;
	}

	public String getRgtNam() {
		return rgtNam;
	}

	public void setRgtNam(String rgtNam) {
		this.rgtNam = rgtNam;
	}

	public String getRgtMdh() {
		return rgtMdh;
	}

	public String getAssCat() {
		return assCat;
	}

	public void setAssCat(String assCat) {
		this.assCat = assCat;
	}

	public String getAosCod() {
		return aosCod;
	}

	public void setAosCod(String aosCod) {
		this.aosCod = aosCod;
	}

	public void setRgtMdh(String rgtMdh) {
		this.rgtMdh = rgtMdh;
	}

	public String getCatKey() {
		return catKey;
	}

	public void setCatKey(String catKey) {
		this.catKey = catKey;
	}

	public PaginationInfo getPaginationInfo() {
		return paginationInfo;
	}

	public void setPaginationInfo(PaginationInfo paginationInfo) {
		this.paginationInfo = paginationInfo;
	}

	public String getStOrg() {
		return stOrg;
	}

	public void setStOrg(String stOrg) {
		this.stOrg = stOrg;
	}

	public String getHqOrg() {
		return hqOrg;
	}

	public void setHqOrg(String hqOrg) {
		this.hqOrg = hqOrg;
	}

	public String getGpOrg() {
		return gpOrg;
	}

	public void setGpOrg(String gpOrg) {
		this.gpOrg = gpOrg;
	}


	public String getWrkOldId() {
		return wrkOldId;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getUseYn() {
		return useYn;
	}

	public String getWrkUptMdh() {
		return wrkUptMdh;
	}

	public void setWrkUptMdh(String wrkUptMdh) {
		this.wrkUptMdh = wrkUptMdh;
	}

	public String getUtwComSta() {
		return utwComSta;
	}

	public void setUtwComSta(String utwComSta) {
		this.utwComSta = utwComSta;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getBcyCode() {
		return bcyCode;
	}

	public void setBcyCode(String bcyCode) {
		this.bcyCode = bcyCode;
	}

	public String getControlItem() {
		return controlItem;
	}

	public void setControlItem(String controlItem) {
		this.controlItem = controlItem;
	}
}
