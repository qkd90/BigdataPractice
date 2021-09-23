package com.data.data.hmly.action.mobile.response;

import com.data.data.hmly.service.hotel.entity.Hotel;

/**
 * Created by huangpeijie on 2016-04-21,0021.
 */
public class PlanHotelResponse extends BaseHotelResponse {
    private String priceId;
    private String priceName;
    private Float price;

    public PlanHotelResponse() {
    }

    public PlanHotelResponse(Hotel hotel) {
        super(hotel);
    }

    public String getPriceId() {
        return priceId;
    }

    public void setPriceId(String priceId) {
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
}
