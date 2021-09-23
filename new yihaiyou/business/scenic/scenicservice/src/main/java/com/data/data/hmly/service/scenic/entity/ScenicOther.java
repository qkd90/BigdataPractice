package com.data.data.hmly.service.scenic.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "scenic_extend")
public class ScenicOther extends com.framework.hibernate.util.Entity implements java.io.Serializable {

    @Id
    @Column(name = "id")
    private Long id;
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @PrimaryKeyJoinColumn
    private ScenicInfo scenicInfo;
    @Column(name = "address")
    private String address;
    @Column(name = "telephone")
    private String telephone;
    @Column(name = "how")
    private String how;
    @Column(name = "open_time")
    private String openTime;
    @Column(name = "advice_time_desc")
    private String adviceTimeDesc;
    @Column(name = "advice_time")
    private Integer adviceTime;
    @Column(name = "description")
    private String description;
    @Column(name = "ticket_desc")
    private String ticketDesc;
    @Column(name = "notice")
    private String notice;
    @Column(name = "traffic_guide")
    private String trafficGuide;
    @Column(name = "website")
    private String website;
    @Column(name = "custom")
    private String custom;
    @Column(name = "recommend_reason")
    private String recommendReason;
    @Column(name = "ctrip_id")
    private Integer ctripId;
    @Column(name = "collect_num")
    private Integer collectNum;
    @Column(name = "comment_num")
    private Integer commentNum;
    @Column(name = "source")
    private String source;
    @Column(name = "source_id")
    private String sourceId;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ScenicInfo getScenicInfo() {
        return scenicInfo;
    }

    public void setScenicInfo(ScenicInfo scenicInfo) {
        this.scenicInfo = scenicInfo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getHow() {
        return how;
    }

    public void setHow(String how) {
        this.how = how;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getAdviceTimeDesc() {
        return adviceTimeDesc;
    }

    public void setAdviceTimeDesc(String adviceTimeDesc) {
        this.adviceTimeDesc = adviceTimeDesc;
    }

    public Integer getAdviceTime() {
        return adviceTime;
    }

    public void setAdviceTime(Integer adviceTime) {
        this.adviceTime = adviceTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTicketDesc() {
        return ticketDesc;
    }

    public void setTicketDesc(String ticketDesc) {
        this.ticketDesc = ticketDesc;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getTrafficGuide() {
        return trafficGuide;
    }

    public void setTrafficGuide(String trafficGuide) {
        this.trafficGuide = trafficGuide;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    public String getRecommendReason() {
        return recommendReason;
    }

    public void setRecommendReason(String recommendReason) {
        this.recommendReason = recommendReason;
    }

    public Integer getCtripId() {
        return ctripId;
    }

    public void setCtripId(Integer ctripId) {
        this.ctripId = ctripId;
    }

    public Integer getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(Integer collectNum) {
        this.collectNum = collectNum;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }
}
