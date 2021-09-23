package com.data.data.hmly.service.nctripticket.entity;


import com.data.data.hmly.service.ctripcommon.enums.RowStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * Created by caiys on 2016/1/25.
 */
@Entity
@Table(name = "nctrip_scenic_spot_product")
public class CtripScenicSpotProduct extends com.framework.hibernate.util.Entity {
    @Id
    @Column(name = "id", length = 20)
    private Long id;	            //  同景点ID
    @Column(name = "productDescription")
    private String productDescription;	   //  门票产品描述信息
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createTime", length = 19)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updateTime", length = 19)
    private Date updateTime;
    @Column(name = "rowStatus")
    @Enumerated(EnumType.STRING)
    private RowStatus rowStatus;

    @Transient
    private List<CtripProductAddInfo>       productAddInfoList;	   //  门票产品附加信息
    @Transient
    private List<CtripScenicSpotResource>   resourceList;           //  门票资源简单信息列表

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public List<CtripProductAddInfo> getProductAddInfoList() {
        return productAddInfoList;
    }

    public void setProductAddInfoList(List<CtripProductAddInfo> productAddInfoList) {
        this.productAddInfoList = productAddInfoList;
    }

    public List<CtripScenicSpotResource> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<CtripScenicSpotResource> resourceList) {
        this.resourceList = resourceList;
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

    public RowStatus getRowStatus() {
        return rowStatus;
    }

    public void setRowStatus(RowStatus rowStatus) {
        this.rowStatus = rowStatus;
    }
}
