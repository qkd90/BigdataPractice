package com.data.data.hmly.service.cruiseship.entity;
// Generated 2016-9-13 14:06:21 by Hibernate Tools 3.2.0.CR1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "cruise_ship_extend")
public class CruiseShipExtend extends com.framework.hibernate.util.Entity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cruiseShipId", nullable = false)
    private CruiseShip cruiseShip;
    @Column(name = "visaInfo")
    private String visaInfo;
    @Column(name = "lightPoint")
    private String lightPoint;
    @Column(name = "quoteNoContainDesc")
    private String quoteNoContainDesc;
    @Column(name = "quoteContainDesc")
    private String quoteContainDesc;
    @Column(name = "orderKnow")
    private String orderKnow;
    @Column(name = "howToOrder")
    private String howToOrder;
    @Column(name = "signWay")
    private String signWay;
    @Column(name = "payWay")
    private String payWay;
    @Column(name = "introduction")
    private String introduction;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createTime", length = 19)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updateTime", length = 19)
    private Date updateTime;

    public CruiseShipExtend() {
    }


    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    public CruiseShip getCruiseShip() {
        return this.cruiseShip;
    }
    
    public void setCruiseShip(CruiseShip cruiseShip) {
        this.cruiseShip = cruiseShip;
    }

    public String getVisaInfo() {
        return visaInfo;
    }

    public void setVisaInfo(String visaInfo) {
        this.visaInfo = visaInfo;
    }

    public String getLightPoint() {
        return this.lightPoint;
    }
    
    public void setLightPoint(String lightPoint) {
        this.lightPoint = lightPoint;
    }
    public String getQuoteNoContainDesc() {
        return this.quoteNoContainDesc;
    }
    
    public void setQuoteNoContainDesc(String quoteNoContainDesc) {
        this.quoteNoContainDesc = quoteNoContainDesc;
    }
    public String getQuoteContainDesc() {
        return this.quoteContainDesc;
    }
    
    public void setQuoteContainDesc(String quoteContainDesc) {
        this.quoteContainDesc = quoteContainDesc;
    }
    public String getOrderKnow() {
        return this.orderKnow;
    }
    
    public void setOrderKnow(String orderKnow) {
        this.orderKnow = orderKnow;
    }
    public String getHowToOrder() {
        return this.howToOrder;
    }
    
    public void setHowToOrder(String howToOrder) {
        this.howToOrder = howToOrder;
    }
    public String getSignWay() {
        return this.signWay;
    }
    
    public void setSignWay(String signWay) {
        this.signWay = signWay;
    }
    public String getPayWay() {
        return this.payWay;
    }
    
    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }
    public String getIntroduction() {
        return this.introduction;
    }
    
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
    public Date getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getUpdateTime() {
        return this.updateTime;
    }
    
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }




}


