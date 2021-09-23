package com.data.data.hmly.service.outer.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * Created by caiys on 2016/11/29.
 */
@Entity
@Table(name = "outer_question")
public class OuterQuestion extends com.framework.hibernate.util.Entity {
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name = "title")
    private String title;       // 标题
    @Column(name = "desc")
    private String desc;     // 问题描述
    @Column(name = "singleFlag")
    private Boolean singleFlag;       // 答案是否唯一

    @Transient
    private List<OuterQuestionCandidate> questionCandidates;

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Boolean getSingleFlag() {
        return singleFlag;
    }

    public void setSingleFlag(Boolean singleFlag) {
        this.singleFlag = singleFlag;
    }

    public List<OuterQuestionCandidate> getQuestionCandidates() {
        return questionCandidates;
    }

    public void setQuestionCandidates(List<OuterQuestionCandidate> questionCandidates) {
        this.questionCandidates = questionCandidates;
    }
}
