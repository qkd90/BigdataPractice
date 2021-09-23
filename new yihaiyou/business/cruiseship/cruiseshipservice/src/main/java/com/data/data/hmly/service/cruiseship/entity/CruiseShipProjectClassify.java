package com.data.data.hmly.service.cruiseship.entity;
// Generated 2016-9-13 14:06:21 by Hibernate Tools 3.2.0.CR1


import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "cruise_ship_project_classify")
public class CruiseShipProjectClassify extends com.framework.hibernate.util.Entity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name = "classifyName")
    private String classifyName;
    @Column(name = "showStatus")
    private Boolean showStatus;
    @Column(name = "coverImage")
    private String coverImage;
    @Column(name = "createTime", length = 19)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updateTime", length = 19)
    private Date updateTime;
    @Column(name = "sort")
    private Integer sort;

    @ManyToOne
    @JoinColumn(name = "parentId")
    private CruiseShipProjectClassify cruiseShipProjectClassify;

    @OneToMany(mappedBy = "cruiseShipProjectClassify", fetch = FetchType.LAZY)
    private Set<CruiseShipProject> cruiseShipProject;

    @Transient
    private List<CruiseShipProject> listCruiseShipProject;

    /**
     * 页面字段
     */
    @Transient
    private Long parentId;
    @Transient
    private String keyword;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public Boolean getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(Boolean showStatus) {
        this.showStatus = showStatus;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public CruiseShipProjectClassify getCruiseShipProjectClassify() {
        return cruiseShipProjectClassify;
    }

    public void setCruiseShipProjectClassify(CruiseShipProjectClassify cruiseShipProjectClassify) {
        this.cruiseShipProjectClassify = cruiseShipProjectClassify;
    }

    public Set<CruiseShipProject> getCruiseShipProject() {
        return cruiseShipProject;
    }

    public void setCruiseShipProject(Set<CruiseShipProject> cruiseShipProject) {
        this.cruiseShipProject = cruiseShipProject;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }


    public List<CruiseShipProject> getListCruiseShipProject() {
        return listCruiseShipProject;
    }

    public void setListCruiseShipProject(List<CruiseShipProject> listCruiseShipProject) {
        this.listCruiseShipProject = listCruiseShipProject;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}


