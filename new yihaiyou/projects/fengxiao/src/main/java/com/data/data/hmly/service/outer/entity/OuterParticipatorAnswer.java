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
@Table(name = "outer_participator_answer")
public class OuterParticipatorAnswer extends com.framework.hibernate.util.Entity {
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name = "collectInfoId")
    private Long collectInfoId;     // 信息收集标识
    @Column(name = "questionId")
    private Long questionId;        // 问题标识
    @Column(name = "candidateId")
    private Long candidateId;       // 问题候选值标识
    @Column(name = "answerFlag")
    private Boolean answerFlag;       // 答案标志(冗余候选值答案列)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCollectInfoId() {
        return collectInfoId;
    }

    public void setCollectInfoId(Long collectInfoId) {
        this.collectInfoId = collectInfoId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    public Boolean getAnswerFlag() {
        return answerFlag;
    }

    public void setAnswerFlag(Boolean answerFlag) {
        this.answerFlag = answerFlag;
    }
}
