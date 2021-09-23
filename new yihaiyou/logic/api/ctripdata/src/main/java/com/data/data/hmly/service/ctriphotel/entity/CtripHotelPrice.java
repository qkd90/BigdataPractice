package com.data.data.hmly.service.ctriphotel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by vacuity on 15/12/4.
 */
@Entity
@Table(name = "ctrip_hotel_price")
public class CtripHotelPrice extends com.framework.hibernate.util.Entity {
    private Long id;
    private Long ratePlanCode;
    private Long hotelId;
    private Integer ratePlanCategory;
    private Boolean isCommissionabl;
    private Boolean rateReturn;
    private Long marketCode;
    private Integer numberOfUnits;
    private Date start;
    private Date end;
    private Boolean isInstantConfirm;
    private String status;
    private Float amountBeforeTax;
    private Integer numberOfGuests;
    private Float listPrice;
    private Integer guaranteeCode;
    private Date createdTime;
    private Date updateTime;

    private Boolean breakfast;
    private Date cancelStart;
    private Date cancelEnd;
    private String roomDescription;

    @Id
    @GeneratedValue
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    @Column(name = "ratePlanCode")
    public Long getRatePlanCode() {
        return ratePlanCode;
    }

    public void setRatePlanCode(Long ratePlanCode) {
        this.ratePlanCode = ratePlanCode;
    }

    
    @Column(name = "hotelId")
    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    
    @Column(name = "ratePlanCategory")
    public Integer getRatePlanCategory() {
        return ratePlanCategory;
    }

    public void setRatePlanCategory(Integer ratePlanCategory) {
        this.ratePlanCategory = ratePlanCategory;
    }

    
    @Column(name = "isCommissionabl")
    public Boolean getIsCommissionabl() {
        return isCommissionabl;
    }

    public void setIsCommissionabl(Boolean isCommissionabl) {
        this.isCommissionabl = isCommissionabl;
    }

    
    @Column(name = "rateReturn")
    public Boolean getRateReturn() {
        return rateReturn;
    }

    public void setRateReturn(Boolean rateReturn) {
        this.rateReturn = rateReturn;
    }

    
    @Column(name = "marketCode")
    public Long getMarketCode() {
        return marketCode;
    }

    public void setMarketCode(Long marketCode) {
        this.marketCode = marketCode;
    }

    
    @Column(name = "numberOfUnits")
    public Integer getNumberOfUnits() {
        return numberOfUnits;
    }

    public void setNumberOfUnits(Integer numberOfUnits) {
        this.numberOfUnits = numberOfUnits;
    }

    
    @Column(name = "start")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    
    @Column(name = "end")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    
    @Column(name = "isInstantConfirm")
    public Boolean getIsInstantConfirm() {
        return isInstantConfirm;
    }

    public void setIsInstantConfirm(Boolean isInstantConfirm) {
        this.isInstantConfirm = isInstantConfirm;
    }

    
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
    @Column(name = "amountBeforeTax")
    public Float getAmountBeforeTax() {
        return amountBeforeTax;
    }

    public void setAmountBeforeTax(Float amountBeforeTax) {
        this.amountBeforeTax = amountBeforeTax;
    }

    
    @Column(name = "numberOfGuests")
    public Integer getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(Integer numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    
    @Column(name = "listPrice")
    public Float getListPrice() {
        return listPrice;
    }

    public void setListPrice(Float listPrice) {
        this.listPrice = listPrice;
    }

    
    @Column(name = "guaranteeCode")
    public Integer getGuaranteeCode() {
        return guaranteeCode;
    }

    public void setGuaranteeCode(Integer guaranteeCode) {
        this.guaranteeCode = guaranteeCode;
    }
    
    @Column(name = "createdTime")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    
    @Column(name = "updateTime")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


    @Column(name = "breakfast")
    public Boolean getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(Boolean breakfast) {
        this.breakfast = breakfast;
    }

    @Column(name = "cancelStart")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCancelStart() {
        return cancelStart;
    }

    public void setCancelStart(Date cancelStart) {
        this.cancelStart = cancelStart;
    }

    @Column(name = "cancelEnd")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCancelEnd() {
        return cancelEnd;
    }

    public void setCancelEnd(Date cancelEnd) {
        this.cancelEnd = cancelEnd;
    }

    @Column(name = "roomDescription")
    public String getRoomDescription() {
        return roomDescription;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }
}
