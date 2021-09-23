package com.data.data.hmly.service.nctripticket.entity;

import com.data.data.hmly.service.ctripcommon.enums.AddInfoDetailType;
import com.data.data.hmly.service.ctripcommon.enums.RowStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;


@Entity
@Table(name = "nctrip_addinfo_detail")
public class CtripAddInfoDetail extends com.framework.hibernate.util.Entity {
    @Id
    @GeneratedValue
    @Column(name = "id", length = 20, unique = true, nullable = false)
    private Long    id;	              // 描述信息ID
    @Column(name = "targetId", length = 20)
    private Long    targetId;	           // 目标ID
    @Column(name = "targetType")
    @Enumerated(EnumType.STRING)
    private AddInfoDetailType targetType;     // 目标类型
    @Column(name = "descDetail")
    private String descDetail;		//	资源描述信息
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createTime", length = 19)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updateTime", length = 19)
    private Date updateTime;
    @Column(name = "rowStatus")
    @Enumerated(EnumType.STRING)
    private RowStatus rowStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public AddInfoDetailType getTargetType() {
        return targetType;
    }

    public void setTargetType(AddInfoDetailType targetType) {
        this.targetType = targetType;
    }

    public String getDescDetail() {
        return descDetail;
    }

    public void setDescDetail(String descDetail) {
        this.descDetail = descDetail;
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
