package com.data.data.hmly.service.outer.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by caiys on 2016/11/29.
 */
@Entity
@Table(name = "outer_mascot_bename")
public class OuterMascotBename extends com.framework.hibernate.util.Entity {
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name = "mascotName")
    private String mascotName;       // 吉祥物名称
    @Column(name = "participator")
    private String participator;     // 参与者姓名
    @Column(name = "phone")
    private String phone;             // 电话
    @Column(name = "candidateFlag")
    private Boolean candidateFlag;          // 是否入围标志
    @Column(name = "ranking")
    private Integer ranking;             // 排名
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createTime", length = 19)
    private Date createTime;            // 创建时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMascotName() {
        return mascotName;
    }

    public void setMascotName(String mascotName) {
        this.mascotName = mascotName;
    }

    public String getParticipator() {
        return participator;
    }

    public void setParticipator(String participator) {
        this.participator = participator;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getCandidateFlag() {
        return candidateFlag;
    }

    public void setCandidateFlag(Boolean candidateFlag) {
        this.candidateFlag = candidateFlag;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
