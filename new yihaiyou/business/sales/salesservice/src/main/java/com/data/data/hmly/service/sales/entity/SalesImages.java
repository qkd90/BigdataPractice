package com.data.data.hmly.service.sales.entity;

import com.data.data.hmly.service.sales.entity.enums.SalesType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by zzl on 2016/7/6.
 */
@Entity
@Table(name = "lxb_sales_images")
public class SalesImages extends com.framework.hibernate.util.Entity implements java.io.Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "target_type")
    @Enumerated(EnumType.STRING)
    private SalesType targetType;

    @Column(name = "target_id")
    private Long targetId;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "img_desc")
    private String imgDesc;

    @Column(name = "sort")
    private Long sort;

    @Column(name = "create_time")
    private Date createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SalesType getTargetType() {
        return targetType;
    }

    public void setTargetType(SalesType targetType) {
        this.targetType = targetType;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgDesc() {
        return imgDesc;
    }

    public void setImgDesc(String imgDesc) {
        this.imgDesc = imgDesc;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
