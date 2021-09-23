package com.hmlyinfo.app.soutu.activity.service;

import com.hmlyinfo.app.soutu.activity.domain.Activity;
import com.hmlyinfo.app.soutu.activity.domain.ActivityUser;
import com.hmlyinfo.app.soutu.activity.mapper.ActivityUserMapper;
import com.hmlyinfo.app.soutu.base.properties.Config;
import com.hmlyinfo.base.exception.BizValidateException;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import com.hmlyinfo.base.util.SMSUtil;
import com.hmlyinfo.base.util.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ActivityUserService extends BaseService<ActivityUser, Long> {

    @Autowired
    private ActivityUserMapper<ActivityUser> mapper;
    @Autowired
    private ActivityService activityService;

    private static boolean sendSMS = true;
    private static String content = Config.get("sms.activity.content");

    @Override
    public BaseMapper<ActivityUser> getMapper() {
        return mapper;
    }

    @Override
    public String getKey() {
        return "id";
    }

    public void save(ActivityUser activityUser) {
        Validate.notNull(activityUser.getName());
        Validate.notNull(activityUser.getPhone());
        Validate.notNull(activityUser.getSex());
        Activity activity = activityService.getCurrentActivity();
        Validate.notNull(activity, -1, "活动已结束");
        activityUser.setActivityId(activity.getId());
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (activityUser.getId() != null) {
            update(activityUser);
            return;
        }
        paramMap.put("phone", activityUser.getPhone());
        List<ActivityUser> list = list(paramMap);
        if (!list.isEmpty()) {
            throw new BizValidateException(-1, "号码已存在");
        }
        insert(activityUser);
        if (sendSMS) {
            Map<String, Object> countMap = new HashMap<String, Object>();
            countMap.put("activityId", activity.getId());
            int count = count(countMap);
            if (count > 500) {
                sendSMS = false;
            } else {
                SMSUtil.send(activityUser.getPhone(), content);
            }
        }
    }

    public List<ActivityUser> getByOpenId(String openId) {
        Activity activity = activityService.getCurrentActivity();
        if (activity == null) {
            return null;
        }
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("openId", openId);
        paramMap.put("activityId", activity.getId());
        List<ActivityUser> list = list(paramMap);
        if (list.isEmpty()) {
            return null;
        }
        return list;
    }
}
