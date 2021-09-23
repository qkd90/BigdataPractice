package com.data.data.hmly.action.mobile.response;

import com.data.data.hmly.service.hotel.entity.HotelPrice;
import com.data.data.hmly.service.hotel.entity.enums.PriceStatus;

/**
 * Created by huangpeijie on 2016-04-18,0018.
 */
public class HotelPriceResponse {
    private Long priceId;
    private String priceName;
    private Float price;
    private String payType;

    public HotelPriceResponse() {
    }

    public HotelPriceResponse(HotelPrice hotelPrice) {
        this.priceId = hotelPrice.getId();
        this.priceName = hotelPrice.getRoomName();
        this.price = hotelPrice.getPrice();
        if (PriceStatus.UP.equals(hotelPrice.getStatus())) {
            this.payType = "到付";
        } else {
            this.payType = "担保";
        }
    }

    public Long getPriceId() {
        return priceId;
    }

    public void setPriceId(Long priceId) {
        this.priceId = priceId;
    }

    public String getPriceName() {
        return priceName;
    }

    public void setPriceName(String priceName) {
        this.priceName = priceName;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }
}
