package com.data.data.hmly.action.lvxbang;

import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.lxbcommon.FeedbackService;
import com.data.data.hmly.service.lxbcommon.entity.Feedback;
import com.data.data.hmly.service.lxbcommon.entity.enums.FeedBackStatus;
import com.opensymphony.xwork2.Result;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zzl on 2016/4/18.
 */
public class FeedbackWebAction extends LxbAction {

    public Feedback feedback = new Feedback();

    @Resource
    private FeedbackService feedbackService;

    public Result saveMyFeedbak() {
        Member member = getLoginUser();
        Map<String, Object> result = new HashMap<String, Object>();
        if (member != null) {
            Feedback newFeedback = new Feedback();
            newFeedback.setContact(feedback.getContact());
            newFeedback.setContent(feedback.getContent());
            newFeedback.setCreator(member);
            newFeedback.setCreateTime(new Date());
            newFeedback.setStatus(FeedBackStatus.OPEN);
            newFeedback.setDelFlag(0);
            feedbackService.save(newFeedback);
            result.put("success", true);
            return json(JSONObject.fromObject(result));
        }
        return null;
    }
}
