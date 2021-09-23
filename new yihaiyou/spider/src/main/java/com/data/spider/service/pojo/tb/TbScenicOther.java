package com.data.spider.service.pojo.tb;

import com.framework.hibernate.util.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.sql.Timestamp;

/**
 * Created by Sane on 15/9/21.
 */
@Entity
@Table(name = "tb_scenic_other")
public class TbScenicOther extends com.framework.hibernate.util.Entity {
    private long id;
    private long scenicInfoId;
    private String introduction;
    private String advice;
    private String address;
    private String telephone;
    private String website;
    private String warning;
    private String bestTime;
    private String hospital;
    private String custom;
    private String recommend;
    private Timestamp modifyTime;
    private Timestamp createTime;

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "scenic_info_id", nullable = false, insertable = true, updatable = true)
    public long getScenicInfoId() {
        return scenicInfoId;
    }

    public void setScenicInfoId(long scenicInfoId) {
        this.scenicInfoId = scenicInfoId;
    }

    @Basic
    @Column(name = "introduction", nullable = true, insertable = true, updatable = true, length = 65535)
    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    @Basic
    @Column(name = "advice", nullable = true, insertable = true, updatable = true, length = 500)
    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    @Basic
    @Column(name = "address", nullable = true, insertable = true, updatable = true, length = 500)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "telephone", nullable = true, insertable = true, updatable = true, length = 500)
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Basic
    @Column(name = "website", nullable = true, insertable = true, updatable = true, length = 500)
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Basic
    @Column(name = "warning", nullable = true, insertable = true, updatable = true, length = 500)
    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    @Basic
    @Column(name = "best_time", nullable = true, insertable = true, updatable = true, length = 500)
    public String getBestTime() {
        return bestTime;
    }

    public void setBestTime(String bestTime) {
        this.bestTime = bestTime;
    }

    @Basic
    @Column(name = "hospital", nullable = true, insertable = true, updatable = true, length = 500)
    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    @Basic
    @Column(name = "custom", nullable = true, insertable = true, updatable = true, length = 500)
    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    @Basic
    @Column(name = "recommend", nullable = true, insertable = true, updatable = true, length = 500)
    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    @Basic
    @Column(name = "modify_time", nullable = true, insertable = true, updatable = true)
    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Basic
    @Column(name = "create_time", nullable = true, insertable = true, updatable = true)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TbScenicOther that = (TbScenicOther) o;

        if (id != that.id) return false;
        if (scenicInfoId != that.scenicInfoId) return false;
        if (introduction != null ? !introduction.equals(that.introduction) : that.introduction != null) return false;
        if (advice != null ? !advice.equals(that.advice) : that.advice != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (telephone != null ? !telephone.equals(that.telephone) : that.telephone != null) return false;
        if (website != null ? !website.equals(that.website) : that.website != null) return false;
        if (warning != null ? !warning.equals(that.warning) : that.warning != null) return false;
        if (bestTime != null ? !bestTime.equals(that.bestTime) : that.bestTime != null) return false;
        if (hospital != null ? !hospital.equals(that.hospital) : that.hospital != null) return false;
        if (custom != null ? !custom.equals(that.custom) : that.custom != null) return false;
        if (recommend != null ? !recommend.equals(that.recommend) : that.recommend != null) return false;
        if (modifyTime != null ? !modifyTime.equals(that.modifyTime) : that.modifyTime != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (scenicInfoId ^ (scenicInfoId >>> 32));
        result = 31 * result + (introduction != null ? introduction.hashCode() : 0);
        result = 31 * result + (advice != null ? advice.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (telephone != null ? telephone.hashCode() : 0);
        result = 31 * result + (website != null ? website.hashCode() : 0);
        result = 31 * result + (warning != null ? warning.hashCode() : 0);
        result = 31 * result + (bestTime != null ? bestTime.hashCode() : 0);
        result = 31 * result + (hospital != null ? hospital.hashCode() : 0);
        result = 31 * result + (custom != null ? custom.hashCode() : 0);
        result = 31 * result + (recommend != null ? recommend.hashCode() : 0);
        result = 31 * result + (modifyTime != null ? modifyTime.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }
}
