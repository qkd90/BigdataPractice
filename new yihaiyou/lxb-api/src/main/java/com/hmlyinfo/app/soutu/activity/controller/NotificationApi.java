package com.hmlyinfo.app.soutu.activity.controller;

import com.hmlyinfo.app.soutu.activity.domain.Notification;
import com.hmlyinfo.app.soutu.activity.service.NotificationService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by guoshijie on 2015/1/6.
 */
@Controller
@RequestMapping("/api/pub/notification")
public class NotificationApi {

    @Autowired
    NotificationService notificationService;

    @RequestMapping("list")
    @ResponseBody
    public ActionResult list(HttpServletRequest request) {
        Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
        paramMap.put("status", Notification.STATUS_IN_USE);
        return ActionResult.createSuccess(notificationService.list(paramMap));
    }
}
