package com.uwo.isms.domain;

import java.util.HashSet;

public class SampleDocVO {

	/** 증적양식담당 키 **/
	private String utmMapKey;
	/** 증적양식코드 **/
	private String utmTrcKey;
	/** 대주기코드**/
	private String utmBcyCod;
	/** 담당자 시퀀스 **/
	private String utmWrkId;
	/** 담당자 사업부 **/
	private String utmDivCod;
	/** 담당자 서비스 **/
	private String utmSvcCod;
	/** 담당자 부서 **/
	private String utmDepCod;
	/** 이전 담당자 시퀀스 **/
	private String utmMngOld;
	/** 문서접근권한 **/
	private String utmDwnYn;
	/** 삭제여부 **/
	private String utmDelYn;
	/** 생성자 시퀀스 **/
	private String utmRgtId;
	/** 수정자 시퀀스 **/
	private String utmUptId;

	/** 문서 마스터 키 **/
	private String utdTrcKey;
	/** 대주기코드 **/
	private String utdBcyCod;
	/** 문서명 **/
	private String utdDocNam;
	/** 문서종류 **/
	private String utdDocGbn;
	/** 업무주기구분 **/
	private String workTerm;
	private String utdTrmGbn;
	private String utdTrmGbnTxt;

	/** 증적주기구분 **/
	private String utdDtrGbn;
	/** 사업부코드 **/
	private String utdDivCod;
	/** 서비스코드 **/
	private String utdSvcCod;
	/** 부서코드 **/
	private String utdDepCod;
	/** 삭제여부 **/
	private String utdDelYn;
	/** 결재여부 **/
	private String utdAprYn;
	/** 결재단계 **/
	private String utdAppLev;
	/** 결재단계(old) **/
	private String aprlevOld;
	/** 업무시작일 **/
	private String utdStrDat;
	/** 업무만료일 **/
	private String utdEndDat;
	/** 업무수행일 **/
	private String utdCmpDat;
	/** 업무기한 **/
	private String utdWrkEnd;
	/** 문서설명 **/
	private String utdDocEtc;
	/** 메일 발송여부 **/
	private String utdSdmYn;
	/** 생성자 시퀀스 **/
	private String utdRgtId;
	/** 수정자 시퀀스 **/
	private String utdUptId;

	/** 수정자 시퀀스 **/
	private String utdDocCnt;


	/** add or update **/
	private String mode;

	/** 심사주기 start date **/
	private String manCylStr;

	/** 심사주기 End date **/
	private String manCylEnd;

	private String code;

	private HashSet<String> utwWrkIds;

	/**
	 * @return the utmDocSeq
	 */
	public String getUtmMapKey() {
		return utmMapKey;
	}
	/**
	 * @param utmDocSeq the utmDocSeq to set
	 */
	public void setUtmMapKey(String utmMapKey) {
		this.utmMapKey = utmMapKey;
	}
	/**
	 * @return the utmBcyCod
	 */
	public String getUtmBcyCod() {
		return utmBcyCod;
	}
	/**
	 * @param utmBcyCod the utmBcyCod to set
	 */
	public void setUtmBcyCod(String utmBcyCod) {
		this.utmBcyCod = utmBcyCod;
	}
	/**
	 * @return the utmMngOld
	 */
	public String getUtmMngOld() {
		return utmMngOld;
	}
	/**
	 * @param utmMngOld the utmMngOld to set
	 */
	public void setUtmMngOld(String utmMngOld) {
		this.utmMngOld = utmMngOld;
	}
	/**
	 * @return the utmDwnYn
	 */
	public String getUtmDwnYn() {
		return utmDwnYn;
	}
	/**
	 * @param utmDwnYn the utmDwnYn to set
	 */
	public void setUtmDwnYn(String utmDwnYn) {
		this.utmDwnYn = utmDwnYn;
	}
	/**
	 * @return the utmDelYn
	 */
	public String getUtmDelYn() {
		return utmDelYn;
	}
	/**
	 * @param utmDelYn the utmDelYn to set
	 */
	public void setUtmDelYn(String utmDelYn) {
		this.utmDelYn = utmDelYn;
	}
	/**
	 * @return the utmRgtId
	 */
	public String getUtmRgtId() {
		return utmRgtId;
	}
	/**
	 * @param utmRgtId the utmRgtId to set
	 */
	public void setUtmRgtId(String utmRgtId) {
		this.utmRgtId = utmRgtId;
	}
	/**
	 * @return the utmUptId
	 */
	public String getUtmUptId() {
		return utmUptId;
	}
	/**
	 * @param utmUptId the utmUptId to set
	 */
	public void setUtmUptId(String utmUptId) {
		this.utmUptId = utmUptId;
	}
	/**
	 * @return the utdBcyCod
	 */
	public String getUtdBcyCod() {
		return utdBcyCod;
	}
	/**
	 * @param utdBcyCod the utdBcyCod to set
	 */
	public void setUtdBcyCod(String utdBcyCod) {
		this.utdBcyCod = utdBcyCod;
	}
	/**
	 * @return the utdDocNam
	 */
	public String getUtdDocNam() {
		return utdDocNam;
	}
	/**
	 * @param utdDocNam the utdDocNam to set
	 */
	public void setUtdDocNam(String utdDocNam) {
		this.utdDocNam = utdDocNam;
	}
	/**
	 * @return the utdDocGbn
	 */
	public String getUtdDocGbn() {
		return utdDocGbn;
	}
	/**
	 * @param utdDocGbn the utdDocGbn to set
	 */
	public void setUtdDocGbn(String utdDocGbn) {
		this.utdDocGbn = utdDocGbn;
	}
	/**
	 * @return the utdTrmGbn
	 */
	public String getUtdTrmGbn() {
		return utdTrmGbn;
	}
	/**
	 * @param utdTrmGbn the utdTrmGbn to set
	 */
	public void setUtdTrmGbn(String utdTrmGbn) {
		this.utdTrmGbn = utdTrmGbn;
	}
	/**
	 * @return the utdDtrGbn
	 */
	public String getUtdDtrGbn() {
		return utdDtrGbn;
	}
	/**
	 * @param utdDtrGbn the utdDtrGbn to set
	 */
	public void setUtdDtrGbn(String utdDtrGbn) {
		this.utdDtrGbn = utdDtrGbn;
	}
	/**
	 * @return the utdDivCod
	 */
	public String getUtdDivCod() {
		return utdDivCod;
	}
	/**
	 * @param utdDivCod the utdDivCod to set
	 */
	public void setUtdDivCod(String utdDivCod) {
		this.utdDivCod = utdDivCod;
	}
	/**
	 * @return the utdSvcCod
	 */
	public String getUtdSvcCod() {
		return utdSvcCod;
	}
	/**
	 * @param utdSvcCod the utdSvcCod to set
	 */
	public void setUtdSvcCod(String utdSvcCod) {
		this.utdSvcCod = utdSvcCod;
	}
	/**
	 * @return the utdDelYn
	 */
	public String getUtdDelYn() {
		return utdDelYn;
	}
	/**
	 * @param utdDelYn the utdDelYn to set
	 */
	public void setUtdDelYn(String utdDelYn) {
		this.utdDelYn = utdDelYn;
	}
	/**
	 * @return the utdAprYn
	 */
	public String getUtdAprYn() {
		return utdAprYn;
	}
	/**
	 * @param utdAprYn the utdAprYn to set
	 */
	public void setUtdAprYn(String utdAprYn) {
		this.utdAprYn = utdAprYn;
	}
	/**
	 * @return the utdStrDat
	 */
	public String getUtdStrDat() {
		return utdStrDat;
	}
	/**
	 * @param utdStrDat the utdStrDat to set
	 */
	public void setUtdStrDat(String utdStrDat) {
		this.utdStrDat = utdStrDat;
	}
	/**
	 * @return the utdEndDat
	 */
	public String getUtdEndDat() {
		return utdEndDat;
	}
	/**
	 * @param utdEndDat the utdEndDat to set
	 */
	public void setUtdEndDat(String utdEndDat) {
		this.utdEndDat = utdEndDat;
	}
	/**
	 * @return the utdWrkEnd
	 */
	public String getUtdWrkEnd() {
		return utdWrkEnd;
	}
	/**
	 * @param utdWrkDat the utdWrkEnd to set
	 */
	public void setUtdWrkEnd(String utdWrkEnd) {
		this.utdWrkEnd = utdWrkEnd;
	}
	/**
	 * @return the utdDocEtc
	 */
	public String getUtdDocEtc() {
		return utdDocEtc;
	}
	/**
	 * @param utdDocEtc the utdDocEtc to set
	 */
	public void setUtdDocEtc(String utdDocEtc) {
		this.utdDocEtc = utdDocEtc;
	}
	/**
	 * @return the utdSdmYn
	 */
	public String getUtdSdmYn() {
		return utdSdmYn;
	}
	/**
	 * @param utdSdmYn the utdSdmYn to set
	 */
	public void setUtdSdmYn(String utdSdmYn) {
		this.utdSdmYn = utdSdmYn;
	}
	/**
	 * @return the utdRgtId
	 */
	public String getUtdRgtId() {
		return utdRgtId;
	}
	/**
	 * @param utdRgtId the utdRgtId to set
	 */
	public void setUtdRgtId(String utdRgtId) {
		this.utdRgtId = utdRgtId;
	}
	/**
	 * @return the utdUptId
	 */
	public String getUtdUptId() {
		return utdUptId;
	}
	/**
	 * @param utdUptId the utdUptId to set
	 */
	public void setUtdUptId(String utdUptId) {
		this.utdUptId = utdUptId;
	}
	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}
	/**
	 * @param mode the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}
	/**
	 * @return the utdTrcKey
	 */
	public String getUtdTrcKey() {
		return utdTrcKey;
	}
	/**
	 * @param utdTrcKey the utdTrcKey to set
	 */
	public void setUtdTrcKey(String utdTrcKey) {
		this.utdTrcKey = utdTrcKey;
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
	public String getUtmWrkId() {
		return utmWrkId;
	}
	public void setUtmWrkId(String utmWrkId) {
		this.utmWrkId = utmWrkId;
	}
	public String getUtdAppLev() {
		return utdAppLev;
	}
	public void setUtdAppLev(String utdAppLev) {
		this.utdAppLev = utdAppLev;
	}
	public String getUtdDepCod() {
		return utdDepCod;
	}
	public void setUtdDepCod(String utdDepCod) {
		this.utdDepCod = utdDepCod;
	}
	public String getUtmDepCod() {
		return utmDepCod;
	}
	public void setUtmDepCod(String utmDepCod) {
		this.utmDepCod = utmDepCod;
	}
	public String getAprlevOld() {
		return aprlevOld;
	}
	public void setAprlevOld(String aprlevOld) {
		this.aprlevOld = aprlevOld;
	}
	public String getManCylStr() {
		return manCylStr;
	}
	public void setManCylStr(String manCylStr) {
		this.manCylStr = manCylStr;
	}
	public String getManCylEnd() {
		return manCylEnd;
	}
	public void setManCylEnd(String manCylEnd) {
		this.manCylEnd = manCylEnd;
	}
	public String getUtmTrcKey() {
		return utmTrcKey;
	}
	public void setUtmTrcKey(String utmTrcKey) {
		this.utmTrcKey = utmTrcKey;
	}
	public String getUtmDivCod() {
		return utmDivCod;
	}
	public void setUtmDivCod(String utmDivCod) {
		this.utmDivCod = utmDivCod;
	}
	public String getUtmSvcCod() {
		return utmSvcCod;
	}
	public void setUtmSvcCod(String utmSvcCod) {
		this.utmSvcCod = utmSvcCod;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getUtdDocCnt() {
		return utdDocCnt;
	}
	public void setUtdDocCnt(String utdDocCnt) {
		this.utdDocCnt = utdDocCnt;
	}
	public String getUtdCmpDat() {
		return utdCmpDat;
	}
	public void setUtdCmpDat(String utdCmpDat) {
		this.utdCmpDat = utdCmpDat;
	}

	public String getUtdTrmGbnTxt() {
		return utdTrmGbnTxt;
	}
	public void setUtdTrmGbnTxt(String utdTrmGbnTxt) {
		this.utdTrmGbnTxt = utdTrmGbnTxt;
	}
	public HashSet<String> getUtwWrkIds() {
		return utwWrkIds;
	}
	public void setUtwWrkIds(HashSet<String> utwWrkIds) {
		this.utwWrkIds = utwWrkIds;
	}
}
