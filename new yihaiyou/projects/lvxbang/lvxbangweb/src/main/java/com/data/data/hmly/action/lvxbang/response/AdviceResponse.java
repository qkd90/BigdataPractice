package com.data.data.hmly.action.lvxbang.response;

import com.data.data.hmly.service.comment.entity.Advice;

import java.util.Date;

/**
 * @author Jonathan.Guo
 */
public class AdviceResponse {
    public Long id;
    public String title;
    public String content;
    public String userName;
    public String userHead;
    public String reply;
    public Date createTime;

    public AdviceResponse(Advice advice) {
        this.id = advice.getId();
        this.title = advice.getTitle();
        this.content = advice.getContent();
        if (advice.getUser() != null) {
//            this.userName = advice.getUser().getUserExinfo().getNickName();
//            this.userHead = advice.getUser().getUserExinfo().getHead();
            this.userName = advice.getUser().getNickName();
            this.userHead = advice.getUser().getHead();
        }
        if (advice.getReplyList() != null && !advice.getReplyList().isEmpty()) {
            this.reply = advice.getReplyList().get(0).getContent();
        }
        this.createTime = advice.getCreateTime();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
