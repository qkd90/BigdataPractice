package com.data.data.hmly.action.yhy;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.comment.CommentService;
import com.data.data.hmly.service.comment.entity.Comment;
import com.data.data.hmly.service.comment.entity.enums.CommentStatus;
import com.data.data.hmly.service.comment.vo.CommentSumarryVo;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.ticket.TicketPriceService;
import com.data.data.hmly.service.ticket.entity.TicketPrice;
import com.data.data.hmly.util.PageData;
import com.framework.hibernate.util.Page;
import com.google.common.collect.Lists;
import com.opensymphony.xwork2.Result;
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
 * Created by dy on 2016/12/9.
 */
public class YhyCommentAction extends FrameBaseAction {

    @Resource
    private CommentService commentService;
    @Resource
    private TicketPriceService ticketPriceService;

    private Long productId;
    private PageData<Comment> commentPageData = new PageData<Comment>();
    private PageData<CommentSumarryVo> commentSumarryVoPageData = new PageData<CommentSumarryVo>();
    private Comment comment = new Comment();
    private TicketPrice ticketPrice = new TicketPrice();
    private Map<String, Object> map = new HashMap<String, Object>();
    private Integer draw;
    private Integer start = 1;
    private Integer length = 10;

    public Result doReplyComment() {
        if (StringUtils.isNotBlank(comment.getContent())) {
            Comment oldComment = commentService.getComment(comment.getRepliedId());
            comment.setContent(StringUtils.htmlEncode(comment.getContent()));
            comment.setType(oldComment.getType());
            comment.setStatus(CommentStatus.NORMAL);
            comment.setCreateTime(new Date());
            comment.setTargetId(oldComment.getTargetId());
            comment.setUser(getLoginUser());
            comment.setOrderNo(oldComment.getOrderNo());
            comment.setPriceId(oldComment.getPriceId());
            commentService.save(comment);
            oldComment.setReplyStatus(1);
            commentService.update(oldComment);
            simpleResult(map, true, "");
        } else {
            simpleResult(map, false, "");
        }

        return jsonResult(map);
    }

    public Result commentSummaryList() {

        Integer pageIndex = start / length + 1;
        Page page = new Page(pageIndex, length);
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        List<CommentSumarryVo> commentSumarryVos = Lists.newArrayList();
        comment.setCompanyUnitId(getCompanyUnit().getId());
        List<CommentSumarryVo> sumarryVos = Lists.newArrayList();
        switch (comment.getType()) {
            case hotel:
                sumarryVos = commentService.getHotelCommentSumarryList(comment, page);
                break;
            case sailboat:
                sumarryVos = commentService.getSailboatCommentSumarryList(comment, page);
                break;
            default:
                break;
        }
        result.put("data", sumarryVos);
        result.put("recordsTotal", page.getTotalCount());
        result.put("recordsFiltered", page.getTotalCount());
        result.put("draw", draw);
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        return json(JSONObject.fromObject(result, jsonConfig));
    }


    public Result commentList() {
        Integer pageIndex = start / length + 1;
        Page page = new Page(pageIndex, length);
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        comment.setCompanyUnitId(loginUser.getSysUnit().getCompanyUnit().getId());
        List<Comment>  commentList = commentService.getCommentList(comment, page);
        result.put("data", commentList);
        result.put("recordsTotal", page.getTotalCount());
        result.put("recordsFiltered", page.getTotalCount());
        result.put("draw", draw);
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig("commentScores", "comments", "user");
        return json(JSONObject.fromObject(result, jsonConfig));
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }


    public PageData<CommentSumarryVo> getCommentSumarryVoPageData() {
        return commentSumarryVoPageData;
    }

    public void setCommentSumarryVoPageData(PageData<CommentSumarryVo> commentSumarryVoPageData) {
        this.commentSumarryVoPageData = commentSumarryVoPageData;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public TicketPrice getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(TicketPrice ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public PageData<Comment> getCommentPageData() {
        return commentPageData;
    }

    public void setCommentPageData(PageData<Comment> commentPageData) {
        this.commentPageData = commentPageData;
    }

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }
}
