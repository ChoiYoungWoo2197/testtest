/**
 ***********************************
 * @source PopupService.java
 * @date 2019. 06. 25.
 * @project isams skb
 * @description popup service
 ***********************************
 */

package com.uwo.isms.service;

import com.uwo.isms.domain.PopupVO;
import java.util.List;
import java.util.Map;

public interface PopupService {
    int popup_be_key(int brd_key);
    PopupVO popup_read_key(int brd_key);
    void popup_insert(PopupVO pvo);
    void popup_update(PopupVO pvo);
    void popup_update_insert(PopupVO pvo);
    List<?> popup(String now);
    PopupVO popup_info(Map map);
}
