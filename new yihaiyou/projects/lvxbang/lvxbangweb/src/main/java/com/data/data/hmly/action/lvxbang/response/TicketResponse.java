package com.data.data.hmly.action.lvxbang.response;

import java.util.List;

/**
 * Created by huangpeijie on 2016-04-12,0012.
 */
public class TicketResponse {
    private String type;
    private List<TicketPriceResponse> priceList;

    public List<TicketPriceResponse> getPriceList() {
        return priceList;
    }

    public void setPriceList(List<TicketPriceResponse> priceList) {
        this.priceList = priceList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
