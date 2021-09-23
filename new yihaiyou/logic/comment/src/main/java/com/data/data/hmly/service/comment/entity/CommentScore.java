package com.data.data.hmly.service.comment.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by HMLY on 2015/12/28.
 */
@Entity(name = "comment_score")
public class CommentScore extends com.framework.hibernate.util.Entity implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "commentId")
    private Long commentId;
//    @Column(name = "scoreItemId")
//    private Long scoreItemId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commentScoreTypeId")
    private CommentScoreType commentScoreType;
    @Column(name = "score")
    private Integer score;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public CommentScoreType getCommentScoreType() {
        return commentScoreType;
    }

    public void setCommentScoreType(CommentScoreType commentScoreType) {
        this.commentScoreType = commentScoreType;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
