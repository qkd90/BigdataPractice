package com.data.data.hmly.service.hotel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by caiys on 2016/5/26.
 */
@Entity
@Table(name = "hotel_price_calendar")
public class HotelPriceCalendar extends com.framework.hibernate.util.Entity {
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "hotelPriceId")
    private HotelPrice hotelPrice;
    @Column(name = "hotelId")
    private Long hotelId;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date", length = 19)
    private Date date;
    @Column(name = "member")
    private Float member;
    @Column(name = "marketPrice")
    private Float marketPrice;
    @Column(name = "cPrice")
    private Float cprice;
    @Column(name = "cost")
    private Float cost;
    @Column(name = "addBed")
    private Float addBed;
    @Column(name = "status")
    private Boolean status;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createTime", length = 19)
    private Date createTime;

    @Column(name = "inventory", length = 20)
    private Integer inventory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HotelPrice getHotelPrice() {
        return hotelPrice;
    }

    public void setHotelPrice(HotelPrice hotelPrice) {
        this.hotelPrice = hotelPrice;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Float getMember() {
        return member;
    }

    public void setMember(Float member) {
        this.member = member;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public Float getAddBed() {
        return addBed;
    }

    public void setAddBed(Float addBed) {
        this.addBed = addBed;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public Float getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Float marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Float getCprice() {
        return cprice;
    }

    public void setCprice(Float cprice) {
        this.cprice = cprice;
    }
}
