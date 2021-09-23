package com.data.data.hmly.action.mobile.response;

import com.data.data.hmly.service.line.entity.Linetypeprice;

/**
 * Created by huangpeijie on 2016-07-22,0022.
 */
public class LineTypePriceResponse {
    private Long id;
    private String name;

    public LineTypePriceResponse() {
    }

    public LineTypePriceResponse(Linetypeprice linetypeprice) {
        this.id = linetypeprice.getId();
        this.name = linetypeprice.getQuoteName();
    }


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
}
