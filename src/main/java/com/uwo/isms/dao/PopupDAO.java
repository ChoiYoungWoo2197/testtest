/**
 ***********************************
 * @source PopupDAO.java
 * @date 2019. 06. 25.
 * @project isams skb
 * @description popup dao
 ***********************************
 */

package com.uwo.isms.dao;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import org.springframework.stereotype.Repository;
import com.uwo.isms.domain.PopupVO;
import java.util.List;
import java.util.Map;

@Repository("PopupDAO")
public class PopupDAO extends EgovAbstractDAO {

    public int popup_be_key(int brd_key) { return (int) select("popup.QR_BE_KEY", brd_key); }

    public PopupVO popup_read_key(int brd_key) { return (PopupVO) select("popup.QR_READ_KEY", brd_key); }

    public void popup_insert(PopupVO pvo) {
        insert("popup.QR_INSERT", pvo);
    }

    public void popup_update(PopupVO pvo) { update("popup.QR_UPDATE", pvo); }

    public void popup_update_insert(PopupVO pvo) { update("popup.QR_UPDATE_INSERT", pvo); }

    public List<?> popup(String now) {
        List<?> list = list("popup.QR_POPUP", now);
        return list;
    }

    public PopupVO popup_info(Map map) { return (PopupVO) select("popup.QR_POPUP_INFO", map); }

}
