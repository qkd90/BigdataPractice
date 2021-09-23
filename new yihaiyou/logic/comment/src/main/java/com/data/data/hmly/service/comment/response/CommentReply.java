package com.data.data.hmly.service.comment.response;

import com.data.data.hmly.service.comment.entity.Comment;
import com.data.data.hmly.service.entity.Member;
import com.zuipin.util.StringUtils;

import java.util.Date;


/**
 * Created by HMLY on 2016/1/11.
 */
public class CommentReply {
    private Long id;
    private String userName;
    private Date createTime;
    private String content;
    private String head;

    public CommentReply(Comment comment) {
        this.id = comment.getId();
        Member user = (Member) comment.getUser();
        if (user != null) {
            if (StringUtils.hasText(user.getNickName())) {
                this.userName = user.getNickName();
            } else {
                this.userName = user.getAccount();
            }
            if (StringUtils.hasText(user.getHead())) {
                this.head = user.getHead();
            } else {
                this.head = "";
            }
//            if (comment.getUser().getUserExinfo() != null) {
//                this.userName = StringUtils.isBlank(comment.getUser().getUserExinfo().getNickName()) ? comment.getUser().getAccount() : comment.getUser().getUserExinfo().getNickName();
//                this.head = StringUtils.isBlank(comment.getUser().getUserExinfo().getHead()) ? "" : comment.getUser().getUserExinfo().getHead();
//            } else {
//                this.userName = comment.getUser().getAccount();
//                this.head = "";
//            }
        } else {
            this.userName = "匿名驴友";
            this.head = "";
        }
        this.createTime = comment.getCreateTime();
        this.content = comment.getContent();
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }
}


