package com.data.data.hmly.service.entity;

import com.data.data.hmly.enums.AdsOpenType;
import com.data.data.hmly.enums.AdsStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.util.Date;

/**
 * Created by vacuity on 15/10/26.
 */

@Entity
@Table(name = "ads")
public class Ads extends com.framework.hibernate.util.Entity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true, nullable = false)
    private long id;

    @ManyToOne
    @JoinColumn(name = "positionId", unique = true, nullable = false, updatable = false)
    private SysResourceMap sysResourceMap;

    @Column(name = "startTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Column(name = "endTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    @Column(name = "url")
    private String url;

    @Column(name = "adTitle")
    private  String adTitle;

    @Column(name = "imgPath")
    private String imgPath;

    @Column(name = "sort")
    private Integer sort;

    @Column(name = "openType")
    @Enumerated(EnumType.STRING)
    private AdsOpenType openType;

    @ManyToOne
    @JoinColumn(name = "userid", unique = true, nullable = false, updatable = false)
    private User user;

    @Column(name = "addTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date addTime;

    @Column(name = "adStatus")
    @Enumerated(EnumType.STRING)
    private AdsStatus adStatus;

    @ManyToOne
    @JoinColumn(name = "unitId")
    private SysUnit sysUnit;

    @Transient
    private Boolean forDisplay;

    @Transient
    private String adsStime;

    @Transient
    private String adsEtime;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SysResourceMap getSysResourceMap() {
        return sysResourceMap;
    }

    public void setSysResourceMap(SysResourceMap sysResourceMap) {
        this.sysResourceMap = sysResourceMap;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAdTitle() {
        return adTitle;
    }

    public void setAdTitle(String adTitle) {
        this.adTitle = adTitle;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public AdsOpenType getOpenType() {
        return openType;
    }

    public void setOpenType(AdsOpenType openType) {
        this.openType = openType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public AdsStatus getAdStatus() {
        return adStatus;
    }

    public void setAdStatus(AdsStatus adStatus) {
        this.adStatus = adStatus;
    }

    public SysUnit getSysUnit() {
        return sysUnit;
    }

    public void setSysUnit(SysUnit sysUnit) {
        this.sysUnit = sysUnit;
    }

    public Boolean getForDisplay() {
        return forDisplay;
    }

    public void setForDisplay(Boolean forDisplay) {
        this.forDisplay = forDisplay;
    }

    public String getAdsStime() {
        return adsStime;
    }

    public void setAdsStime(String adsStime) {
        this.adsStime = adsStime;
    }

    public String getAdsEtime() {
        return adsEtime;
    }

    public void setAdsEtime(String adsEtime) {
        this.adsEtime = adsEtime;
    }
}
