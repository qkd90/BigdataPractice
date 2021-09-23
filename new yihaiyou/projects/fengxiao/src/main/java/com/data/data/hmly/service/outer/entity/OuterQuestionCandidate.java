package com.data.data.hmly.service.outer.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by caiys on 2016/11/29.
 */
@Entity
@Table(name = "outer_question_candidate")
public class OuterQuestionCandidate extends com.framework.hibernate.util.Entity {
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name = "questionId")
    private Long questionId;       // 问题标识
    @Column(name = "desc")
    private String desc;       // 候选值描述
    @Column(name = "answerFlag")
    private Boolean answerFlag;       // 答案标志

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Boolean getAnswerFlag() {
        return answerFlag;
    }

    public void setAnswerFlag(Boolean answerFlag) {
        this.answerFlag = answerFlag;
    }
}
