package com.data.data.hmly.action.yihaiyou;

import com.data.data.hmly.service.lxbcommon.FeedbackService;
import com.data.data.hmly.service.lxbcommon.entity.Feedback;
import com.data.data.hmly.service.lxbcommon.entity.enums.FeedBackStatus;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dy on 2017/1/6.
 */
public class FeedBackWebAction extends BaseAction {

    @Resource
    private FeedbackService feedbackService;

    private Map<String, Object> map = new HashMap<String, Object>();

    public Result submitFeedBack() {
        String content = (String) getParameter("content");
        String contact = (String) getParameter("contact");

        Feedback feedback = new Feedback();
        feedback.setContact(StringUtils.htmlEncode(contact));
        feedback.setContent(StringUtils.htmlEncode(content));
        feedback.setCreateTime(new Date());
        feedback.setCreator(getLoginUser());
        feedback.setStatus(FeedBackStatus.OPEN);
        feedback.setDelFlag(0);
        feedbackService.save(feedback);

        simpleResult(map, true, "");
        return jsonResult(map);
    }
}
