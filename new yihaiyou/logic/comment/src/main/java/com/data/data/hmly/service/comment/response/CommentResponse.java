package com.data.data.hmly.service.comment.response;

import com.data.data.hmly.service.comment.entity.Comment;
import com.data.data.hmly.service.comment.entity.CommentPhoto;
import com.data.data.hmly.service.entity.Member;
import com.google.common.collect.Lists;
import com.zuipin.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by HMLY on 2016/1/4.
 */
public class CommentResponse {
    private Long id;
    private String userName;
    private String head;
    private Date createTime;
    private String content;
    private Integer score;
    private List<CommentReply> replies = new ArrayList<CommentReply>();
    private List<String> imagePaths = Lists.newArrayList();

//    public class Test{
//        CommentResponse comment;
//        List<CommentResponse> replies;
//    }

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        Member user = (Member) comment.getUser();
        if (comment.getUser() != null) {
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
//                this.head = StringUtils.isBlank(comment.getUser().getUserExinfo().getHead()) ? "" : comment.getUser().getUserExinfo().getHead();
//                this.userName = StringUtils.isBlank(comment.getUser().getUserExinfo().getNickName()) ? comment.getUser().getAccount() : comment.getUser().getUserExinfo().getNickName();
//            } else {
//                this.userName = comment.getUser().getAccount();
//                this.head = "";
//            }

        } else {
            this.userName = "匿名驴友";
            this.head = "";
        }
        if (comment.getCommentScores().size() < 1) {
            this.score = 80;
        } else {
            this.score = comment.getCommentScores().get(0).getScore();
        }
        this.content = comment.getContent();
        this.createTime = comment.getCreateTime();
        if (!comment.getComments().isEmpty()) {
            for (Comment c : comment.getComments()) {
                replies.add(new CommentReply(c));
            }
        }
        if (!comment.getCommentPhotos().isEmpty()) {
            for (CommentPhoto commentPhoto : comment.getCommentPhotos()) {
                imagePaths.add(commentPhoto.getImagePath());
            }
        }
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

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public List<CommentReply> getReplies() {
        return replies;
    }

    public void setReplies(List<CommentReply> replies) {
        this.replies = replies;
    }

    public List<String> getImagePaths() {
        return imagePaths;
    }

    public void setImagePaths(List<String> imagePaths) {
        this.imagePaths = imagePaths;
    }
}
