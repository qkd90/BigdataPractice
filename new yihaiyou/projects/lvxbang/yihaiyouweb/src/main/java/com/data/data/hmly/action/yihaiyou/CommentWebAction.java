package com.data.data.hmly.action.yihaiyou;

import com.data.data.hmly.action.yihaiyou.response.CommentResponse;
import com.data.data.hmly.service.CommentMobileService;
import com.data.data.hmly.service.comment.CommentService;
import com.data.data.hmly.service.comment.entity.Comment;
import com.data.data.hmly.service.comment.entity.enums.CommentStatus;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.entity.Member;
import com.framework.hibernate.util.Page;
import com.framework.struts.AjaxCheck;
import com.framework.struts.NeedLogin;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by dy on 2016/10/10.
 */
public class CommentWebAction extends BaseAction {

    @Resource
    private CommentService commentService;
    @Resource
    private CommentMobileService commentMobileService;

    public Member user;
    public Comment comment = new Comment();
    public Long orderDetailId;
    public Integer score;
    public Integer pageNo;
    public Integer pageSize;
    public ProductType productType;
    public Long targetId;
    public Long commentId;


    @AjaxCheck
    @NeedLogin
    public Result saveComment() {
        user = getLoginUser();
        comment.setContent(StringUtils.htmlEncode(comment.getContent()));
        commentMobileService.save(score, orderDetailId, user, comment);
        result.put("success", true);
        result.put("msg", "评论成功");
        return json(JSONObject.fromObject(result));
    }

    @AjaxCheck
    @NeedLogin
    public Result saveRePlanComment() {
        user = getLoginUser();
        commentMobileService.save(comment, getLoginUser());
        result.put("success", true);
        result.put("msg", "评论成功");
        return json(JSONObject.fromObject(result));
    }

    @AjaxCheck
    @NeedLogin
    public Result deleteComment() {
        user = getLoginUser();
        commentService.delByIds(commentId.toString(), user);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    @AjaxCheck
    @NeedLogin
    public Result replyComment() {
        Member user = getLoginUser();
        comment.setUser(user);
        comment.setCreateTime(new Date());
        comment.setStatus(CommentStatus.NORMAL);
        commentService.saveComment(comment);
        result.put("msg", "回复成功");
        result.put("success", true);
        return json(JSONObject.fromObject(result));

    }

    @AjaxCheck
    @NeedLogin
    public Result personalList() {
        user = getLoginUser();
        Page page = new Page(pageNo, pageSize);
        List<CommentResponse> responses = commentMobileService.personalList(user, page);
        if (page.getPageIndex() >= page.getPageCount()) {
            result.put("nomore", true);
        } else {
            result.put("nomore", false);
        }
        result.put("success", true);
        result.put("commentList", responses);
        return json(JSONObject.fromObject(result));
    }

    @AjaxCheck
    public Result productComment() {
        Page page = new Page(pageNo, pageSize);
        List<CommentResponse> responses = commentMobileService.productComment(productType, targetId, page);
        if (page.getPageIndex() >= page.getPageCount()) {
            result.put("nomore", true);
        } else {
            result.put("nomore", false);
        }
        result.put("success", true);
        result.put("commentList", responses);
        return json(JSONObject.fromObject(result));
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}
