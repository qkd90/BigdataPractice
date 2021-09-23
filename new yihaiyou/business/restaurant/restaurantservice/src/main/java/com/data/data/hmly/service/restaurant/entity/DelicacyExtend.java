package com.data.data.hmly.service.restaurant.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "delicacy_extend")
public class DelicacyExtend extends com.framework.hibernate.util.Entity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Delicacy delicacy;
    @Column(name = "introduction")
    private String introduction;
    @Column(name = "collect_num")
    private Integer collectNum;
    @Column(name = "recommend_num")
    private Integer recommendNum;
    @Column(name = "share_num")
    private Integer shareNum;
    @Column(name = "agree_num")
    private Integer agreeNum;
    @Column(name = "view_num")
    private Integer viewNum;
    @Column(name = "recommend_reason")
    private String recommendReason;

    public Delicacy getDelicacy() {
        return delicacy;
    }

    public void setDelicacy(Delicacy delicacy) {
        this.delicacy = delicacy;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Integer getRecommendNum() {
        return recommendNum;
    }

    public void setRecommendNum(Integer recommendNum) {
        this.recommendNum = recommendNum;
    }

    public Integer getShareNum() {
        return shareNum;
    }

    public void setShareNum(Integer shareNum) {
        this.shareNum = shareNum;
    }

    public Integer getAgreeNum() {
        return agreeNum;
    }

    public void setAgreeNum(Integer agreeNum) {
        this.agreeNum = agreeNum;
    }

    public String getRecommendReason() {
        return recommendReason;
    }

    public void setRecommendReason(String recommendReason) {
        this.recommendReason = recommendReason;
    }

    public Integer getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(Integer collectNum) {
        this.collectNum = collectNum;
    }

    public Integer getViewNum() {
        return viewNum;
    }

    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }
}
