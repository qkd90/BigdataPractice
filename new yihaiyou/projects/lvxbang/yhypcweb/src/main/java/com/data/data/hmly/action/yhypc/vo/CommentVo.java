package com.data.data.hmly.action.yhypc.vo;

import java.util.List;

/**
 * Created by zzl on 2017/1/12.
 */
public class CommentVo {
    private Long id;
    private String content;
    private Integer score;
    private String nickName;
    private String avatar;
    private String createTime;
    private List<CommentVo> replyCommentVos;

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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<CommentVo> getReplyCommentVos() {
        return replyCommentVos;
    }

    public void setReplyCommentVos(List<CommentVo> replyCommentVos) {
        this.replyCommentVos = replyCommentVos;
    }
}
