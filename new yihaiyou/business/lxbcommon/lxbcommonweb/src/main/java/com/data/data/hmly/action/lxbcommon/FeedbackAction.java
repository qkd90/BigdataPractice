package com.data.data.hmly.action.lxbcommon;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.lxbcommon.FeedbackService;
import com.data.data.hmly.service.lxbcommon.entity.Feedback;
import com.data.data.hmly.service.lxbcommon.entity.enums.FeedBackStatus;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.DateUtils;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2016/4/15.
 */
public class FeedbackAction extends FrameBaseAction {

    private int page;
    private int rows;
    private String orderProperty;
    private String orderType;

    private Long id;

    private Feedback feedback = new Feedback();



    @Resource
    private FeedbackService feedbackService;


    public Result list() {
        return dispatch();
    }

    public Result getFeedbackList() {
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        Page page = new Page(this.page, this.rows);
        List<Feedback> feedBackList = feedbackService.getFeedBackList(feedback, page, "createTime", "desc");
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig("creator", "replier");
        return datagrid(feedBackList, page.getTotalCount(), jsonConfig);
    }

    public Result getFeedbackDetail() {
        Map<String, Object> result = new HashMap<String, Object>();
        if (id != null && id > 0) {
            Feedback feedback = feedbackService.get(id);
            if (feedback != null) {
                result.put("id", feedback.getId());
                result.put("feedback.id", feedback.getId());
                Member creator = feedback.getCreator();
                if (StringUtils.hasText(creator.getNickName())) {
                    result.put("creatorName", creator.getNickName() + "(" + creator.getId() + ")");
                } else if (StringUtils.hasText(creator.getUserName())) {
                    result.put("creatorName", creator.getUserName() + "(" + creator.getId() + ")");
                } else {
                    result.put("creatorName", creator.getId());
                }
                result.put("contact", feedback.getContact());
                result.put("createTime", DateUtils.format(feedback.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
                result.put("content", feedback.getContent());
                result.put("feedback.replyContent", feedback.getReplyContent());
            } else {
                result.put("id", "ID不可为空或该反馈不存在!");
            }
        } else {
            result.put("id", "ID不可为空或该反馈不存在!");
        }
        return json(JSONObject.fromObject(result));
    }

    public Result replyFeedback() {
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        Map<String, Object> result = new HashMap<String, Object>();
        if (id != null && id > 0) {
            Feedback replyFeedback = feedbackService.get(id);
            if (replyFeedback != null) {
                replyFeedback.setReplier(loginUser);
                replyFeedback.setReplyContent(feedback.getReplyContent());
                replyFeedback.setReplyTime(new Date());
                replyFeedback.setStatus(FeedBackStatus.REPLYED);
                feedbackService.doReplyFeedback(replyFeedback);
                result.put("success", true);
                result.put("msg", "回复成功!");
            } else {
                result.put("success", false);
                result.put("msg", "该反馈不存在! 可能已经被删除!");
            }
        } else {
            result.put("success", false);
            result.put("msg", "反馈ID错误, ID不能为空!");
        }
        return json(JSONObject.fromObject(result));
    }

    public Result delFeedback() {
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        Map<String, Object> result = new HashMap<String, Object>();
        if (id != null && id > 0) {
            Feedback delFeedback = feedbackService.get(id);
            delFeedback.setDelFlag(1);
            feedbackService.update(delFeedback);
            result.put("success", true);
            result.put("msg", "删除成功!");
        } else {
            result.put("success", false);
            result.put("msg", "反馈ID错误, ID不能为空!");
        }
        return json(JSONObject.fromObject(result));
    }

    public Result closeFeedback() {
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        Map<String, Object> result = new HashMap<String, Object>();
        if (id != null && id > 0) {
            Feedback delFeedback = feedbackService.get(id);
            delFeedback.setStatus(FeedBackStatus.CLOSED);
            feedbackService.update(delFeedback);
            result.put("success", true);
            result.put("msg", "关闭成功!");
        } else {
            result.put("success", false);
            result.put("msg", "反馈ID错误, ID不能为空!");
        }
        return json(JSONObject.fromObject(result));
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getOrderProperty() {
        return orderProperty;
    }

    public void setOrderProperty(String orderProperty) {
        this.orderProperty = orderProperty;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }
}
