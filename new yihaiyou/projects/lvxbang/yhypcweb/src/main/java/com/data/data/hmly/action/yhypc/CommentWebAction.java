package com.data.data.hmly.action.yhypc;

import com.data.data.hmly.action.yhypc.response.CommentResponse;
import com.data.data.hmly.action.yhypc.vo.CommentVo;
import com.data.data.hmly.service.CommentWebService;
import com.data.data.hmly.service.comment.CommentService;
import com.data.data.hmly.service.comment.entity.Comment;
import com.data.data.hmly.service.comment.entity.enums.CommentStatus;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.order.OrderDetailService;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.framework.hibernate.util.Page;
import com.framework.struts.AjaxCheck;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2017/1/18.
 */
public class CommentWebAction extends YhyAction {
    @Resource
    private CommentService commentService;
    @Resource
    private CommentWebService commentWebService;
    @Resource
    private OrderDetailService orderDetailService;

    public Map<String, Object> result = new HashMap<String, Object>();
    public Member user;
    public Comment comment = new Comment();
    public Integer score;
    public Long orderDetailId;
    public Integer pageIndex;
    public Integer pageSize;

    public Result saveComment() {
        user = getLoginUser();
        if (orderDetailId == null || orderDetailId < 1) {
            result.put("success", false);
            return jsonResult(result);
        }
        OrderDetail orderDetail = orderDetailService.get(orderDetailId);
        if (orderDetail == null) {
            result.put("success", false);
            return jsonResult(result);
        }
        if (orderDetail.getHasComment()) {
            result.put("success", false);
            result.put("errMsg", "订单已评价");
            return jsonResult(result);
        }
        comment.setContent(StringUtils.htmlEncode(comment.getContent()));
        commentWebService.save(score, orderDetail, user, comment);
        result.put("success", true);
        return jsonResult(result);
    }

    public Result findComment() {
        Comment search = new Comment();
        search.setOrderDetailId(orderDetailId);
        List<Comment> commentList = commentService.list(search, null);
        if (commentList.isEmpty()) {
            result.put("success", false);
            result.put("errMsg", "订单未评价");
            return jsonResult(result);
        }
        CommentResponse response = commentWebService.commentToResponse(commentList.get(0));
        result.put("success", true);
        result.put("comment", response);
        return jsonResult(result);
    }

    public Result countDetailComment() {
        final HttpServletRequest request = getRequest();
        String productIdStr = request.getParameter("productId");
        String typeStr = request.getParameter("proType");
        if (!StringUtils.hasText(productIdStr) || !StringUtils.hasText(typeStr)) {
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        Long productId = Long.parseLong(productIdStr);
        ProductType type = ProductType.valueOf(typeStr);
        Comment condition = new Comment();
        condition.setTargetId(productId);
        condition.setType(type);
        Long count = commentService.count(condition);
        return json(JSONArray.fromObject(count));
    }

    public Result getDetailComment() {
        final HttpServletRequest request = getRequest();
        String productIdStr = request.getParameter("productId");
        String typeStr = request.getParameter("proType");
        if (!StringUtils.hasText(productIdStr) || !StringUtils.hasText(typeStr)) {
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        Long productId = Long.parseLong(productIdStr);
        ProductType type = ProductType.valueOf(typeStr);
        Comment condition = new Comment();
        condition.setTargetId(productId);
        condition.setType(type);
        Page page = new Page(pageIndex, pageSize);
        List<Comment> comments = commentService.list(condition, page);
        List<CommentVo> commentVos = commentWebService.buildCommentData(comments);
        result.put("commentVos", commentVos);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    @AjaxCheck
    public Result saveRePlanComment() {
        user = getLoginUser();
        comment.setUser(user);
        comment.setCreateTime(new Date());
        comment.setStatus(CommentStatus.NORMAL);
        comment.setContent(StringUtils.htmlEncode(comment.getContent()));
        commentService.save(comment);
        result.put("success", true);
        result.put("msg", "评论成功");
        return json(JSONObject.fromObject(result));
    }


    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}
