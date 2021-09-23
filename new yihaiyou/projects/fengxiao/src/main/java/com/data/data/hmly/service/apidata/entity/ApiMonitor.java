package com.data.data.hmly.service.apidata.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by caiys on 2017/1/4.
 */
@Entity
@Table(name = "api_monitor")
public class ApiMonitor extends com.framework.hibernate.util.Entity {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private String id;      // 标识
    @Column(name = "name")
    private String name;    // 名称
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "testTime", length = 19)
    private Date testTime;  // 测试时间
    @Column(name = "status")
    private String status;  // 状态
    @Column(name = "remark")
    private String remark;  // 说明
    @Column(name = "orderBy")
    private String orderBy;  // 排序

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTestTime() {
        return testTime;
    }

    public void setTestTime(Date testTime) {
        this.testTime = testTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
