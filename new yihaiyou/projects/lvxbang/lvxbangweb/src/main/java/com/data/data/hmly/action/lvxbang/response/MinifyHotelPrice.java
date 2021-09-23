package com.data.data.hmly.action.lvxbang.response;

import com.data.data.hmly.service.hotel.entity.enums.PriceStatus;

/**
 * @author Jonathan.Guo
 */
public class MinifyHotelPrice {
    public Long id;
    public String name;
    public boolean hasBreakfast;
    public boolean canCancel;
    public Float price;
    public String ratePlanCode;
    public String priceStartDate;
    public String priceEndDate;
    public String roomDescription;
    public PriceStatus status;
    public String changeRule;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHasBreakfast() {
        return hasBreakfast;
    }

    public void setHasBreakfast(boolean hasBreakfast) {
        this.hasBreakfast = hasBreakfast;
    }

    public boolean isCanCancel() {
        return canCancel;
    }

    public void setCanCancel(boolean canCancel) {
        this.canCancel = canCancel;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getRatePlanCode() {
        return ratePlanCode;
    }

    public void setRatePlanCode(String ratePlanCode) {
        this.ratePlanCode = ratePlanCode;
    }

    public String getPriceStartDate() {
        return priceStartDate;
    }

    public void setPriceStartDate(String priceStartDate) {
        this.priceStartDate = priceStartDate;
    }

    public String getPriceEndDate() {
        return priceEndDate;
    }

    public void setPriceEndDate(String priceEndDate) {
        this.priceEndDate = priceEndDate;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }

    public PriceStatus getStatus() {
        return status;
    }

    public void setStatus(PriceStatus status) {
        this.status = status;
    }

    public String getChangeRule() {
        return changeRule;
    }

    public void setChangeRule(String changeRule) {
        this.changeRule = changeRule;
    }
}
