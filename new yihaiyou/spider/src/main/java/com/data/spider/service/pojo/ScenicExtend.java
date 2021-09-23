package com.data.spider.service.pojo;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Sane on 16/2/1.
 */
@Entity
@Table(name = "scenic_extend", catalog = "")
public class ScenicExtend extends com.framework.hibernate.util.Entity {
    private int id;
    private String address;
    private String telephone;
    private String how;
    private String openTime;
    private String adviceTimeDesc;
    private Integer adviceTime;
    private String description;
    private String ticketDesc;
    private String notice;
    private String trafficGuide;
    private String website;
    private String custom;
    private String recommendReason;
    private Integer ctripId;
    private Integer collectNum;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "address", nullable = true, length = 200)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "telephone", nullable = true, length = 100)
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Basic
    @Column(name = "how", nullable = true, length = 500)
    public String getHow() {
        return how;
    }

    public void setHow(String how) {
        this.how = how;
    }

    @Basic
    @Column(name = "open_time", nullable = true, length = 500)
    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    @Basic
    @Column(name = "advice_time_desc", nullable = true, length = 500)
    public String getAdviceTimeDesc() {
        return adviceTimeDesc;
    }

    public void setAdviceTimeDesc(String adviceTimeDesc) {
        this.adviceTimeDesc = adviceTimeDesc;
    }

    @Basic
    @Column(name = "advice_time", nullable = true)
    public Integer getAdviceTime() {
        return adviceTime;
    }

    public void setAdviceTime(Integer adviceTime) {
        this.adviceTime = adviceTime;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 8192)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "ticket_desc", nullable = true, length = 500)
    public String getTicketDesc() {
        return ticketDesc;
    }

    public void setTicketDesc(String ticketDesc) {
        this.ticketDesc = ticketDesc;
    }

    @Basic
    @Column(name = "notice", nullable = true, length = 3072)
    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    @Basic
    @Column(name = "traffic_guide", nullable = true, length = 3072)
    public String getTrafficGuide() {
        return trafficGuide;
    }

    public void setTrafficGuide(String trafficGuide) {
        this.trafficGuide = trafficGuide;
    }

    @Basic
    @Column(name = "website", nullable = true, length = 500)
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Basic
    @Column(name = "custom", nullable = true, length = 300)
    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    @Basic
    @Column(name = "recommend_reason", nullable = true, length = 500)
    public String getRecommendReason() {
        return recommendReason;
    }

    public void setRecommendReason(String recommendReason) {
        this.recommendReason = recommendReason;
    }

    @Basic
    @Column(name = "ctrip_id", nullable = true)
    public Integer getCtripId() {
        return ctripId;
    }

    public void setCtripId(Integer ctripId) {
        this.ctripId = ctripId;
    }

    @Basic
    @Column(name = "collect_num", nullable = true)
    public Integer getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(Integer collectNum) {
        this.collectNum = collectNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScenicExtend that = (ScenicExtend) o;

        if (id != that.id) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (telephone != null ? !telephone.equals(that.telephone) : that.telephone != null) return false;
        if (how != null ? !how.equals(that.how) : that.how != null) return false;
        if (openTime != null ? !openTime.equals(that.openTime) : that.openTime != null) return false;
        if (adviceTimeDesc != null ? !adviceTimeDesc.equals(that.adviceTimeDesc) : that.adviceTimeDesc != null)
            return false;
        if (adviceTime != null ? !adviceTime.equals(that.adviceTime) : that.adviceTime != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (ticketDesc != null ? !ticketDesc.equals(that.ticketDesc) : that.ticketDesc != null) return false;
        if (notice != null ? !notice.equals(that.notice) : that.notice != null) return false;
        if (trafficGuide != null ? !trafficGuide.equals(that.trafficGuide) : that.trafficGuide != null) return false;
        if (website != null ? !website.equals(that.website) : that.website != null) return false;
        if (custom != null ? !custom.equals(that.custom) : that.custom != null) return false;
        if (recommendReason != null ? !recommendReason.equals(that.recommendReason) : that.recommendReason != null)
            return false;
        if (ctripId != null ? !ctripId.equals(that.ctripId) : that.ctripId != null) return false;
        if (collectNum != null ? !collectNum.equals(that.collectNum) : that.collectNum != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (telephone != null ? telephone.hashCode() : 0);
        result = 31 * result + (how != null ? how.hashCode() : 0);
        result = 31 * result + (openTime != null ? openTime.hashCode() : 0);
        result = 31 * result + (adviceTimeDesc != null ? adviceTimeDesc.hashCode() : 0);
        result = 31 * result + (adviceTime != null ? adviceTime.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (ticketDesc != null ? ticketDesc.hashCode() : 0);
        result = 31 * result + (notice != null ? notice.hashCode() : 0);
        result = 31 * result + (trafficGuide != null ? trafficGuide.hashCode() : 0);
        result = 31 * result + (website != null ? website.hashCode() : 0);
        result = 31 * result + (custom != null ? custom.hashCode() : 0);
        result = 31 * result + (recommendReason != null ? recommendReason.hashCode() : 0);
        result = 31 * result + (ctripId != null ? ctripId.hashCode() : 0);
        result = 31 * result + (collectNum != null ? collectNum.hashCode() : 0);
        return result;
    }
}
