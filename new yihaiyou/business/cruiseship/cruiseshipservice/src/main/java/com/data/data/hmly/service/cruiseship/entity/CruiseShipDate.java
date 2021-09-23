package com.data.data.hmly.service.cruiseship.entity;
// Generated 2016-9-13 14:06:21 by Hibernate Tools 3.2.0.CR1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "cruise_ship_date")
public class CruiseShipDate extends com.framework.hibernate.util.Entity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "cruiseShipId")
    private CruiseShip cruiseShip;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date", length = 19)
    private Date date;
    @Column(name = "minDiscountPrice")
    private Float minDiscountPrice;
    @Column(name = "minSalePrice")
    private Float minSalePrice;
    @Column(name = "minMarketPrice")
    private Float minMarketPrice;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createTime", length = 19)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updateTime", length = 19)
    private Date updateTime;

    @OneToMany(mappedBy = "cruiseShipDate", fetch = FetchType.LAZY)
    private Set<CruiseShipRoomDate> cruiseShipRoomDates;

    public CruiseShipDate() {
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
    public Date getDate() {
        return this.date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    public Float getMinDiscountPrice() {
        return this.minDiscountPrice;
    }
    
    public void setMinDiscountPrice(Float minDiscountPrice) {
        this.minDiscountPrice = minDiscountPrice;
    }
    public Float getMinSalePrice() {
        return this.minSalePrice;
    }
    
    public void setMinSalePrice(Float minSalePrice) {
        this.minSalePrice = minSalePrice;
    }
    public Float getMinMarketPrice() {
        return this.minMarketPrice;
    }
    
    public void setMinMarketPrice(Float minMarketPrice) {
        this.minMarketPrice = minMarketPrice;
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


