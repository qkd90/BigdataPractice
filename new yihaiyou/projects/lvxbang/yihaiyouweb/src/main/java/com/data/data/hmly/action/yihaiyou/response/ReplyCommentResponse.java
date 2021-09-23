package com.data.data.hmly.action.yihaiyou.response;

import com.data.data.hmly.service.comment.entity.Comment;

/**
 * Created by huangpeijie on 2016-12-19,0019.
 */
public class ReplyCommentResponse {
    private Long id;
    private String content;
    private String userName;
    private String commentDate;

    public ReplyCommentResponse() {
    }

    public ReplyCommentResponse(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        if (comment.getUser() != null) {
            this.userName = comment.getUser().getNickName();
        }
        this.commentDate = comment.getCreateTimeStr();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }
}
