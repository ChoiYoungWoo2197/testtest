/**
 ***********************************
 * @source ExcelNewDAO.java
 * @date 2019. 07. 04.
 * @project isams skb
 * @description excel new type DAO
 ***********************************
 */
package com.uwo.isms.dao;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("excelNewDAO")
public class ExcelNewDAO extends EgovAbstractDAO {

    public String getStndKind(String code) {
        return (String) select("excelNew.QR_COMP_STND_KIND", code);
    }
    public String getStndName(String code) {
        return (String) select("excelNew.QR_COMP_STND_NAME", code);
    }
    public List<?> getCompList(){
        return list("excelNew.QR_COMP_LIST");
    }
    public List<?> getCompliance(String comp){
        return list("excelNew.QR_COMPLIANCE", comp);
    }

    public List<?> fm_comps003_3D_mappinglistInCtrKey(Map mapCtrKeys) {
        return list("fmComps.QR_COMPS003_U_InCtrKey", mapCtrKeys);
    }

    public List<?> downComps003_isms(Map<String, String> map) {
        return list("excelNew.QR_COMPS003_ISMS", map);
    }
    public List<?> downComps003_infra_mp(Map<String, String> map) {
        return list("excelNew.QR_COMPS003_INFRA_MP", map);
    }
    public List<?> downComps003_msit(Map<String, String> map) {
        return list("excelNew.QR_COMPS003_MSIT", map);
    }
    public List<?> downComps003_infra_la(Map<String, String> map) {
        return list("excelNew.QR_COMPS003_INFRA_LA", map);
    }

    public List<?> downComps003_default(Map<String, String> map) {
        return list("excelNew.QR_COMPS003_ISMS", map);
    }

    //exp_data_infra_mp - 기반시설 관리적/물리적
    public void fm_comps003_ins_exp_data_infra_mp(Map<String, String> map){
        insert("fmComps.QR_COMPS003_ins_exp_data_infra_mp", map);
    }

    public void fm_comps003_del_exp_data_infra_mp(String strCtrKey){
        delete("fmComps.QR_COMPS003_del_exp_data_infra_mp", strCtrKey);
    }

    public void fm_comps003_del_all_exp_data_infra_mp(Map<String, String> map) {
        delete("fmComps.QR_COMPS003_del_all_exp_data_infra_mp", map);
    }

    public List<?> fm_comps003_exp_data_infra_mp(Map mapKeyCodes) {
        return list("fmComps.QR_COMPS003_exp_data_infra_mp", mapKeyCodes);
    }


    //exp_data_msit - 과기정통부 확장
    public void fm_comps003_ins_exp_data_msit(Map<String, String> map){
        insert("fmComps.QR_COMPS003_ins_exp_data_msit", map);
    }

    public void fm_comps003_del_exp_data_msit(String strCtrKey) {
        delete("fmComps.QR_COMPS003_del_exp_data_msit", strCtrKey);
    }

    public void fm_comps003_del_all_exp_data_msit(Map<String, String> map) {
        delete("fmComps.QR_COMPS003_del_all_exp_data_msit", map);
    }

    public List<?> fm_comps003_exp_data_msit(Map mapKeyCodes) {
        return list("fmComps.QR_COMPS003_exp_data_msit", mapKeyCodes);
    }


    //exp_data_infra_la - 기반시설 수준평가 확장
    public void fm_comps003_ins_exp_data_infra_la(Map<String, String> map) {
        insert("fmComps.QR_COMPS003_ins_exp_data_infra_la", map);
    }

    public void fm_comps003_del_exp_data_infra_la(String strCtrKey){
        delete("fmComps.QR_COMPS003_del_exp_data_infra_la", strCtrKey);
    }

    public void fm_comps003_del_all_exp_data_infra_la(Map<String, String> map) {
        delete("fmComps.QR_COMPS003_del_all_exp_data_infra_la", map);
    }

    public List<?> fm_comps003_exp_data_infra_la(Map mapKeyCodes) {
        return list("fmComps.QR_COMPS003_exp_data_infra_la", mapKeyCodes);
    }

    public int fm_inspt004_count(Map<String, String> map) {
        return (Integer) select("excelNew.QR_INSPT004_COUNT", map);
    }

    public void fm_inspt004_update(Map<String, String> map) {
        update("excelNew.QR_INSPT004_UPDATE", map);
    }
}
