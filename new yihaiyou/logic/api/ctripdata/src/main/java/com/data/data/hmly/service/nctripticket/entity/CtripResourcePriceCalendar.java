package com.data.data.hmly.service.nctripticket.entity;


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
import javax.persistence.Transient;
import java.util.Date;

/**
 * Created by caiys on 2016/1/26.
 */
@Entity
@Table(name = "nctrip_resource_price_calendar")
public class CtripResourcePriceCalendar extends com.framework.hibernate.util.Entity {
    @Id
    @GeneratedValue
    @Column(name = "id", length = 20, unique = true, nullable = false)
    private Long    id;	         //  景点ID
    @Column(name = "resourceId", length = 20)
    private Long    resourceId;	     //	资源ID
    @Column(name = "ctripResourceId", length = 20)
    private Long    ctripResourceId;	     //	携程资源ID
    @Column(name = "ctripProductId", length = 20)
    private Long    ctripProductId;	     //	门票产品ID
    @Column(name = "priceDate", length = 19)
    private Date    priceDate;		      //	日期
    @Column(name = "price", length = 20, precision = 2)
    private Double  price;		     //	价格
    @Column(name = "marketPrice", length = 20, precision = 2)
    private Double  marketPrice;		//	市场价
    @Column(name = "ctripPrice", length = 20, precision = 2)
    private Double  ctripPrice;		//	携程卖价
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
    private String    date;		      //	日期

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Date getPriceDate() {
        return priceDate;
    }

    public void setPriceDate(Date priceDate) {
        this.priceDate = priceDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Double marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Double getCtripPrice() {
        return ctripPrice;
    }

    public void setCtripPrice(Double ctripPrice) {
        this.ctripPrice = ctripPrice;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getCtripResourceId() {
        return ctripResourceId;
    }

    public void setCtripResourceId(Long ctripResourceId) {
        this.ctripResourceId = ctripResourceId;
    }

    public Long getCtripProductId() {
        return ctripProductId;
    }

    public void setCtripProductId(Long ctripProductId) {
        this.ctripProductId = ctripProductId;
    }
}
