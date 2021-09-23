package com.hmlyinfo.app.soutu.activity.controller;

import com.hmlyinfo.app.soutu.activity.domain.ActivityUser;
import com.hmlyinfo.app.soutu.activity.service.ActivityUserService;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/api/pub/activity/user")
public class ActivityUserApi {

    @Autowired
    private ActivityUserService service;

    @RequestMapping("/save")
    @ResponseBody
    public ActionResult add(ActivityUser activityUser) {
        service.save(activityUser);
        return ActionResult.createSuccess(activityUser);
    }

    /**
     * @param openId
     * @return ActivityUser
     */
    @RequestMapping("/getbyopenid")
    @ResponseBody
    public ActionResult info(String openId) {
        if (StringUtil.isEmpty(openId)) {
            return ActionResult.createFail(ErrorCode.ERROR_51001, "没有openId");
        }
        List<ActivityUser> activityUser = service.getByOpenId(openId);
        if (activityUser == null) {
            return ActionResult.createFail(ErrorCode.ERROR_51001, "没有对应的用户");
        }
        return ActionResult.createSuccess(activityUser);
    }


}
