/**
 ***********************************
 * @source PopupController.java
 * @date 2019. 06. 25.
 * @project isams skb
 * @description popup controller
 ***********************************
 */

package com.uwo.isms.web;

import com.uwo.isms.domain.PopupVO;
import com.uwo.isms.service.PopupService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/popup")
public class PopupController {
    Logger log = LogManager.getLogger(PopupController.class);

    @Resource(name = "popupService")
    private PopupService popupService;

    @RequestMapping(value = "POPUP.do")
    public String POPUP(ModelMap model, final HttpServletRequest req) throws Exception {

        Map map = new HashMap();
        map.put("pid", req.getParameter("pid"));
        map.put("bk", req.getParameter("bk"));

        PopupVO popupVo = popupService.popup_info(map);
        //log.info("################# popup data ");
        //log.info(popupVo);
        model.addAttribute("popupVo", popupVo);

        return "/POPUP";
    }

}
