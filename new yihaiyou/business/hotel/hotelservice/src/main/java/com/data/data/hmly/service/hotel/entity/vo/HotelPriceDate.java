package com.data.data.hmly.service.hotel.entity.vo;

import java.util.Date;

/**
 * Created by dy on 2016/6/5.
 */
public class HotelPriceDate {

    private Float discountPrice;
    private Date start;

    public Float getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Float discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }
}
