package com.data.data.hmly.action.yihaiyou.response;

import com.data.data.hmly.action.yihaiyou.request.OrderContact;
import com.data.data.hmly.service.order.entity.Order;

/**
 * Created by huangpeijie on 2016-09-28,0028.
 */
public class CruiseShipOrderResponse extends OrderSimpleResponse {
    private String startCity;
    private String endDate;
    private OrderContact contact;

    public CruiseShipOrderResponse() {
    }

    public CruiseShipOrderResponse(Order order) {
        super(order);
    }

    public String getStartCity() {
        return startCity;
    }

    public void setStartCity(String startCity) {
        this.startCity = startCity;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public OrderContact getContact() {
        return contact;
    }

    public void setContact(OrderContact contact) {
        this.contact = contact;
    }
}
