package com.data.data.hmly.service.comment.vo;

/**
 * Created by dy on 2016/12/9.
 */
public class CommentSumarryVo extends com.framework.hibernate.util.Entity {

    private Long targetId;
    private Long priceId;
    private String targetName;
    private Integer score;
    private Long count;

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getPriceId() {
        return priceId;
    }

    public void setPriceId(Long priceId) {
        this.priceId = priceId;
    }
}
