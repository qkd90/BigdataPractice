package com.data.data.hmly.action.lxbcommon;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.action.lxbcommon.vo.CommentScoreTypeVo;
import com.data.data.hmly.action.lxbcommon.vo.CommentTreeVo;
import com.data.data.hmly.service.MemberService;
import com.data.data.hmly.service.comment.entity.Comment;
import com.data.data.hmly.service.comment.entity.CommentScoreType;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.lxbcommon.CommentMgrService;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.DateUtils;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2016/4/25.
 */
public class CommentAction extends FrameBaseAction {

    @Resource
    private CommentMgrService commentMgrService;

    @Resource
    private MemberService memberService;
    private int page;
    private int rows;
    private String orderProperty;
    private String orderType;

    private Long id;
    private String replyContent;
    private String content;


    private Comment comment = new Comment();


    public Result list() {
        return dispatch();
    }

    public Result getCommentData() {
        Page page = new Page(this.page, this.rows);
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig("commentScores", "commentScoreType");
        List<Comment> commentList = commentMgrService.listTreeData(comment, page, orderProperty, orderType);
        List<CommentTreeVo> commentTreeVos = new ArrayList<CommentTreeVo>();
        if (!commentList.isEmpty()) {
            for (Comment comment : commentList) {
                List<Comment> childCommentList = comment.getComments();
                CommentTreeVo rootCommentTreeVo = new CommentTreeVo();
                rootCommentTreeVo.setId(comment.getId());
                if (comment.getUser() != null) {
                    rootCommentTreeVo.setUserName(comment.getUser().getUserName());
                } else {
                    rootCommentTreeVo.setUserName("匿名用户");
                }
                rootCommentTreeVo.setTargetId(comment.getTargetId());
                rootCommentTreeVo.setType(comment.getType());
                rootCommentTreeVo.setContent(StringUtils.htmlEncode(comment.getContent()));
                rootCommentTreeVo.setCreateTime(DateUtils.formatDate(comment.getCreateTime()));
                rootCommentTreeVo.setRepliedId(comment.getRepliedId());
                rootCommentTreeVo.setCommentScores(comment.getCommentScores());
                rootCommentTreeVo.setCommentPhotos(comment.getCommentPhotos());
                rootCommentTreeVo.setIconCls("icon-comment");
                if (childCommentList != null) {
                    List<CommentTreeVo> childCommentTreeVoList = new ArrayList<CommentTreeVo>();
                    for (Comment childComment : childCommentList) {
                        CommentTreeVo childCommentTreeVo = new CommentTreeVo();
                        childCommentTreeVo.setId(childComment.getId());
                        if (childComment.getUser() != null) {
                            childCommentTreeVo.setUserName(childComment.getUser().getUserName());
                        } else {
                            childCommentTreeVo.setUserName("匿名用户");
                        }
                        childCommentTreeVo.setTargetId(childComment.getTargetId());
                        childCommentTreeVo.setType(childComment.getType());
                        childCommentTreeVo.setContent(childComment.getContent());
                        childCommentTreeVo.setCreateTime(DateUtils.formatDate(childComment.getCreateTime()));
                        childCommentTreeVo.setRepliedId(childComment.getRepliedId());
                        childCommentTreeVo.setCommentScores(childComment.getCommentScores());
                        childCommentTreeVo.setCommentPhotos(childComment.getCommentPhotos());
                        childCommentTreeVo.setIconCls("icon-comment-reply");
                        childCommentTreeVoList.add(childCommentTreeVo);
                    }
                    rootCommentTreeVo.setChildren(childCommentTreeVoList);
                }
                commentTreeVos.add(rootCommentTreeVo);
            }
        }
        Result r = datagrid(commentTreeVos, page.getTotalCount(), jsonConfig);
        return r;
    }


    public Result comboScoreTypeList() {
        String targetType = getRequest().getParameter("targetType");
        if (StringUtils.hasText(targetType)) {
            List<CommentScoreTypeVo> typeVoList = new ArrayList<CommentScoreTypeVo>();
            List<CommentScoreType> scoreTypeList = commentMgrService.getScoreTypeList(ProductType.valueOf(targetType));
            for (CommentScoreType commentScoreType : scoreTypeList) {
                CommentScoreTypeVo scoreTypeVo = new CommentScoreTypeVo();
                scoreTypeVo.setId(commentScoreType.getId());
                scoreTypeVo.setName(commentScoreType.getName().replace("&nbsp;", ""));
                typeVoList.add(scoreTypeVo);
            }
            return json(JSONArray.fromObject(typeVoList));
        }
        return null;
    }

    public Result getCommentDetail() {
        Map<String, Object> result = new HashMap<String, Object>();
        if (id != null && id > 0) {
            Comment comment = commentMgrService.get(id);
            if (comment != null) {
                result.put("id", comment.getId());
                result.put("comment.id", comment.getId());
                if (comment.getUser() != null) {
                    result.put("userName", comment.getUser().getUserName());
                } else {
                    result.put("userName", "匿名用户");
                }
                result.put("content", comment.getContent());
            } else {
                result.put("id", "ID不可为空或该评论不存在!");
            }
        } else {
            result.put("id", "ID不可为空或该评论不存在!");
        }
        return json(JSONObject.fromObject(result));
    }

    public Result replyComment() {
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        Map<String, Object> result = new HashMap<String, Object>();
        if (id != null && id > 0) {
            Comment comment = commentMgrService.get(id);
            if (comment != null) {
                Comment replyComment = new Comment();
                if (StringUtils.hasText(replyContent)) {
                    replyComment.setCreateTime(new Date());
                    replyComment.setRepliedId(comment.getId());
                    replyComment.setTargetId(comment.getTargetId());
                    replyComment.setContent(replyContent);
                    replyComment.setType(comment.getType());
                    replyComment.setStatus(comment.getStatus());
                    replyComment.setUser(loginUser);
                    commentMgrService.save(replyComment);
                    result.put("success", true);
                    result.put("msg", "回复成功!");
                } else {
                    result.put("success", false);
                    result.put("msg", "回复内容不可为空!");
                }
            } else {
                result.put("success", false);
                result.put("msg", "该评论不存在! 可能已经被删除!");
            }
        } else {
            result.put("success", false);
            result.put("msg", "评论ID错误, ID不能为空!");
        }
        return json(JSONObject.fromObject(result));
    }

    public Result editComment() {
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        Map<String, Object> result = new HashMap<String, Object>();
        if (id != null && id > 0) {
            Comment comment = commentMgrService.get(id);
            if (comment != null) {
                if (StringUtils.hasText(content)) {
                    comment.setContent(content);
                    commentMgrService.update(comment);
                    result.put("success", true);
                    result.put("msg", "修改成功!");
                } else {
                    result.put("success", false);
                    result.put("msg", "评论内容不可为空!");
                }
            } else {
                result.put("success", false);
                result.put("msg", "该评论不存在! 可能已经被删除!");
            }
        } else {
            result.put("success", false);
            result.put("msg", "该评论不存在! 可能已经被删除!");
        }
        return json(JSONObject.fromObject(result));
    }

    public Result delComment() {
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        Map<String, Object> result = new HashMap<String, Object>();
        if (id != null && id > 0) {
            Comment comment = commentMgrService.get(id);
            if (comment != null) {
                commentMgrService.delete(comment);
                result.put("success", true);
                result.put("msg", "评论删除成功!");
            } else {
                result.put("success", false);
                result.put("msg", "该评论不存在! 可能已经被删除!");
            }
        } else {
            result.put("success", false);
            result.put("msg", "评论ID错误, ID不能为空!");
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

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}
