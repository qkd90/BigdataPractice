package com.data.data.hmly.service.cruiseship.entity;
// Generated 2016-9-13 14:06:21 by Hibernate Tools 3.2.0.CR1


import javax.persistence.*;
import java.util.Date;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "cruise_ship_project_image")
public class CruiseShipProjectImage extends com.framework.hibernate.util.Entity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name = "path")
    private String path;
    @Column(name = "createTime", length = 19)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updateTime", length = 19)
    private Date updateTime;
    @Column(name = "coverFlag")
    private Boolean coverFlag;
    @Column(name = "showOrder")
    private Integer showOrder;

    @ManyToOne
    @JoinColumn(name = "projectId")
    private CruiseShipProject cruiseShipProject;

    /**
     * 页面字段
     */
    @Transient
    private Long projectId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public CruiseShipProject getCruiseShipProject() {
        return cruiseShipProject;
    }

    public void setCruiseShipProject(CruiseShipProject cruiseShipProject) {
        this.cruiseShipProject = cruiseShipProject;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Boolean getCoverFlag() {
        return coverFlag;
    }

    public void setCoverFlag(Boolean coverFlag) {
        this.coverFlag = coverFlag;
    }

    public Integer getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(Integer showOrder) {
        this.showOrder = showOrder;
    }


}


