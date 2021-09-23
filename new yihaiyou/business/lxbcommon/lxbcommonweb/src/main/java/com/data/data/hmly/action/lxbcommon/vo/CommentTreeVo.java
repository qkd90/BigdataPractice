package com.data.data.hmly.action.lxbcommon.vo;

import com.data.data.hmly.service.comment.entity.CommentPhoto;
import com.data.data.hmly.service.comment.entity.CommentScore;
import com.data.data.hmly.service.common.entity.enums.ProductType;

import java.util.List;

/**
 * Created by zzl on 2016/4/25.
 */
public class CommentTreeVo {

    private Long id;
    private String userName;
    private Long targetId;
    private ProductType type;
    private String content;
    private String createTime;
    private Long repliedId;
    private List<CommentScore> commentScores;
    private List<CommentPhoto> commentPhotos;
    private String iconCls;
    private List<CommentTreeVo> children;


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

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Long getRepliedId() {
        return repliedId;
    }

    public void setRepliedId(Long repliedId) {
        this.repliedId = repliedId;
    }

    public List<CommentScore> getCommentScores() {
        return commentScores;
    }

    public void setCommentScores(List<CommentScore> commentScores) {
        this.commentScores = commentScores;
    }

    public List<CommentPhoto> getCommentPhotos() {
        return commentPhotos;
    }

    public void setCommentPhotos(List<CommentPhoto> commentPhotos) {
        this.commentPhotos = commentPhotos;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public List<CommentTreeVo> getChildren() {
        return children;
    }

    public void setChildren(List<CommentTreeVo> children) {
        this.children = children;
    }
}
