package com.data.data.hmly.action.lvxbang;

import com.data.data.hmly.service.comment.CommentScoreTypeService;
import com.data.data.hmly.service.comment.CommentService;
import com.data.data.hmly.service.comment.entity.Comment;
import com.data.data.hmly.service.comment.entity.CommentScore;
import com.data.data.hmly.service.comment.entity.CommentScoreType;
import com.data.data.hmly.service.comment.entity.enums.CommentStatus;
import com.data.data.hmly.service.comment.response.CommentResponse;
import com.data.data.hmly.service.entity.Member;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import net.sf.json.JSONArray;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
 * Created by HMLY on 2015/12/31.
 */
public class CommentWebAction extends LxbAction {

    @Resource
    private CommentService commentService;
    @Resource
    private CommentScoreTypeService commentScoreTypeService;

    public Comment comment = new Comment();
    public CommentScoreType commentScoreType = new CommentScoreType();
    public Integer pageNo = 0;
    public Integer pageSize = 10;

    public Result count() {

        comment.setStatus(CommentStatus.NORMAL);
        List<Comment> list = commentService.list(comment, null);
        int count = list.size();
        return json(JSONArray.fromObject(count));
    }

    public Result list() {
        Page page = new Page(pageNo, pageSize);
        comment.setStatus(CommentStatus.NORMAL);
        List<Comment> list = commentService.list(comment, page);
        List<Comment> newList = new ArrayList<Comment>();
        for (Comment c : list) {
            List<CommentScore> commentScores = c.getCommentScores();
            List<CommentScore> newCommentScores = new ArrayList<CommentScore>();
            for (CommentScore cs : commentScores) {
                if (Objects.equals(cs.getCommentScoreType().getName(), "总体评价")) {
                    newCommentScores.add(cs);
                }
            }
            c.setCommentScores(newCommentScores);
            newList.add(c);
        }
        List<CommentResponse> commentResponses = new ArrayList<CommentResponse>();
        for (Comment c : newList) {
            CommentResponse cr = new CommentResponse(c);
            commentResponses.add(cr);
        }
        return json(JSONArray.fromObject(commentResponses, JsonFilter.getIncludeConfig("replies", "imagePaths")));
    }

    public Result commentScoreTypeList() {
        List<CommentScoreType> list = commentScoreTypeService.list(commentScoreType, null);
        return json(JSONArray.fromObject(list));
    }

    public Result saveComment() {
        Member user = getLoginUser(false);
        comment.setUser(user);
        comment.setCreateTime(new Date());
        comment.setStatus(CommentStatus.NORMAL);
        commentService.saveComment(comment);
        simpleResult(result, true, "");
        result.put("msg", "评论成功");
        return jsonResult(result);
    }

    public Result replyComment() {
        Member user = getLoginUser(false);
        comment.setUser(user);
        comment.setCreateTime(new Date());
        comment.setStatus(CommentStatus.NORMAL);
        commentService.saveComment(comment);
        simpleResult(result, true, "");
        result.put("msg", "回复成功");
        return jsonResult(result);

    }

}
