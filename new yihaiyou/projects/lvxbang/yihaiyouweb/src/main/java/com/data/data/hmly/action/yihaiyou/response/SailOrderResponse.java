package com.data.data.hmly.action.yihaiyou.response;

import com.data.data.hmly.action.yihaiyou.request.SimpleTourist;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.order.entity.Order;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by huangpeijie on 2016-10-25,0025.
 */
public class SailOrderResponse extends OrderSimpleResponse {
    private TicketPriceResponse ticketPrice;
    private SysUnit company;
    private List<ProValidCodeResponse> codes;
    private List<SimpleTourist> tourists = Lists.newArrayList();

    public SailOrderResponse() {
    }

    public SailOrderResponse(Order order) {
        super(order);
    }

    public TicketPriceResponse getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(TicketPriceResponse ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public SysUnit getCompany() {
        return company;
    }

    public void setCompany(SysUnit company) {
        this.company = company;
    }

    public List<ProValidCodeResponse> getCodes() {
        return codes;
    }

    public void setCodes(List<ProValidCodeResponse> codes) {
        this.codes = codes;
    }

    public List<SimpleTourist> getTourists() {
        return tourists;
    }

    public void setTourists(List<SimpleTourist> tourists) {
        this.tourists = tourists;
    }
}
