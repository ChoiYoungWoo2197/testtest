/**
 ***********************************
 * @source PopupServiceImpl.java
 * @date 2019. 06. 25.
 * @project isams skb
 * @description popup service impl
 ***********************************
 */

package com.uwo.isms.service.impl;

import javax.annotation.Resource;
import com.uwo.isms.service.PopupService;
import org.springframework.stereotype.Service;
import com.uwo.isms.domain.PopupVO;
import com.uwo.isms.dao.PopupDAO;
import java.util.List;
import java.util.Map;

@Service("popupService")
public class PopupServiceImpl implements PopupService {

    @Resource(name = "PopupDAO")
    private PopupDAO PopupDAO;

    @Override
    public int popup_be_key(int brd_key) {
        return PopupDAO.popup_be_key(brd_key);
    }

    @Override
    public PopupVO popup_read_key(int brd_key) {
        return PopupDAO.popup_read_key(brd_key);
    }

    @Override
    public void popup_insert(PopupVO pvo) { PopupDAO.popup_insert(pvo); }

    @Override
    public void popup_update(PopupVO pvo) {
        PopupDAO.popup_update(pvo);
    }

    @Override
    public void popup_update_insert(PopupVO pvo) { PopupDAO.popup_update_insert(pvo); }

    @Override
    public List<?> popup(String now) { return PopupDAO.popup(now); }

    @Override
    public PopupVO popup_info(Map map) { return PopupDAO.popup_info(map); }

}
