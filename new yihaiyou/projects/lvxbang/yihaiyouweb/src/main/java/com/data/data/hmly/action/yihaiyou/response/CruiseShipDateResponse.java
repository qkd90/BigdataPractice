package com.data.data.hmly.action.yihaiyou.response;

import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipDate;

/**
 * Created by huangpeijie on 2016-09-19,0019.
 */
public class CruiseShipDateResponse {
    private Long id;
    private String date;
    private Float minSalePrice;

    public CruiseShipDateResponse() {
    }

    public CruiseShipDateResponse(CruiseShipDate cruiseShipDate) {
        this.id = cruiseShipDate.getId();
        this.date = DateUtils.formatShortDate(cruiseShipDate.getDate());
        this.minSalePrice = cruiseShipDate.getMinSalePrice();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Float getMinSalePrice() {
        return minSalePrice;
    }

    public void setMinSalePrice(Float minSalePrice) {
        this.minSalePrice = minSalePrice;
    }
}
