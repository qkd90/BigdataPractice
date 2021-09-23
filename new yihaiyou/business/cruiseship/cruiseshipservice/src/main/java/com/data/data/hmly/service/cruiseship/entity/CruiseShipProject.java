package com.data.data.hmly.service.cruiseship.entity;
// Generated 2016-9-13 14:06:21 by Hibernate Tools 3.2.0.CR1


import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.goods.entity.Category;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.Transient;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "cruise_ship_project")
public class CruiseShipProject extends com.framework.hibernate.util.Entity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "introduction")
    private String introduction;
    @Column(name = "level")
    private Integer level;
    @Column(name = "peopleNum")
    private Integer peopleNum;
    @Column(name = "suitType")
    private String suitType;
    @Column(name = "costStatus")
    private String costStatus;
    @Column(name = "openStatus")
    private String openStatus;
    @Column(name = "createTime", length = 19)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updateTime", length = 19)
    private Date updateTime;
    @Column(name = "coverImage")
    private String coverImage;
    @Column(name = "showStatus")
    private Boolean showStatus;

    @ManyToOne
    @JoinColumn(name = "classifyId")
    private CruiseShipProjectClassify cruiseShipProjectClassify;
    @ManyToOne
    @JoinColumn(name = "shipId")
    private CruiseShip cruiseShip;

    @OneToMany(mappedBy = "cruiseShipProject", fetch = FetchType.LAZY)
    private Set<CruiseShipProjectImage> cruiseShipProjectImage;

    /**
     * 页面字段
     */
    @Transient
    private Long classifyId;

    @Transient
    private Long shipId;
    @Transient
    private List<CruiseShipProjectImage> projectImages;
    @Transient
    private List<CruiseShipProjectClassify> classifyList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(Integer peopleNum) {
        this.peopleNum = peopleNum;
    }

    public String getSuitType() {
        return suitType;
    }

    public void setSuitType(String suitType) {
        this.suitType = suitType;
    }

    public String getCostStatus() {
        return costStatus;
    }

    public void setCostStatus(String costStatus) {
        this.costStatus = costStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public List<CruiseShipProjectClassify> getClassifyList() {
        return classifyList;
    }

    public void setClassifyList(List<CruiseShipProjectClassify> classifyList) {
        this.classifyList = classifyList;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Long getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Long classifyId) {
        this.classifyId = classifyId;
    }

    public CruiseShip getCruiseShip() {
        return cruiseShip;
    }

    public Long getShipId() {
        return shipId;
    }

    public void setShipId(Long shipId) {
        this.shipId = shipId;
    }

    public void setCruiseShip(CruiseShip cruiseShip) {
        this.cruiseShip = cruiseShip;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Set<CruiseShipProjectImage> getCruiseShipProjectImage() {
        return cruiseShipProjectImage;
    }

    public List<CruiseShipProjectImage> getProjectImages() {
        return projectImages;
    }

    public void setProjectImages(List<CruiseShipProjectImage> projectImages) {
        this.projectImages = projectImages;
    }

    public CruiseShipProjectClassify getCruiseShipProjectClassify() {
        return cruiseShipProjectClassify;
    }

    public void setCruiseShipProjectClassify(CruiseShipProjectClassify cruiseShipProjectClassify) {
        this.cruiseShipProjectClassify = cruiseShipProjectClassify;
    }

    public void setCruiseShipProjectImage(Set<CruiseShipProjectImage> cruiseShipProjectImage) {
        this.cruiseShipProjectImage = cruiseShipProjectImage;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getOpenStatus() {
        return openStatus;
    }

    public void setOpenStatus(String openStatus) {
        this.openStatus = openStatus;
    }

    public Boolean getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(Boolean showStatus) {
        this.showStatus = showStatus;
    }
}


