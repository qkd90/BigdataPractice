package com.data.data.hmly.action.yihaiyou.response;


import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.data.data.hmly.service.ticket.entity.TicketDateprice;
import com.data.data.hmly.service.ticket.entity.TicketPrice;
import com.data.data.hmly.service.ticket.entity.TicketPriceTypeExtend;
import com.data.data.hmly.service.ticket.response.TicketPriceAddInfo;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by huangpeijie on 2016-08-02,0002.
 */
public class OrderTicketResponse {
    private Long ticketId;
    private Long priceId;
    private String name;
    private String priceName;
    private Float price;
    private String playDate;
    private String getTicket;
    private String ticketType;
    private List<TicketPriceAddInfo> addInfoList;
    private List<TicketPriceTypeExtendResponse> orderKnowList = Lists.newArrayList();
    private Boolean isTodayValid;
    private Boolean isConditionRefund;
    private String startDate;
    private String endDate;

    public OrderTicketResponse() {
    }

    public OrderTicketResponse(TicketDateprice ticketDateprice) {
        this.price = ticketDateprice.getPriPrice();
        this.playDate = DateUtils.formatShortDate(ticketDateprice.getDate());
        TicketPrice ticketPrice = ticketDateprice.getTicketPriceId();
        if (ticketPrice == null) {
            return;
        }
        this.priceId = ticketPrice.getId();
        this.priceName = ticketPrice.getName();
        Ticket ticket = ticketPrice.getTicket();
        if (ticket == null) {
            return;
        }
        this.ticketId = ticket.getId();
        this.name = ticket.getTicketName();
        this.ticketType = ticket.getTicketType().name();
        for (TicketPriceTypeExtend ticketPriceTypeExtend : ticketPrice.getTicketPriceTypeExtends()) {
            if (ticketPriceTypeExtend.getFirstTitle().equals("预订须知")) {
                this.orderKnowList.add(new TicketPriceTypeExtendResponse(ticketPriceTypeExtend));
            }
        }
        this.isTodayValid = ticketPrice.getIsTodayValid();
        this.isConditionRefund = ticketPrice.getIsConditionRefund();
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Long getPriceId() {
        return priceId;
    }

    public void setPriceId(Long priceId) {
        this.priceId = priceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getPlayDate() {
        return playDate;
    }

    public void setPlayDate(String playDate) {
        this.playDate = playDate;
    }

    public List<TicketPriceAddInfo> getAddInfoList() {
        return addInfoList;
    }

    public void setAddInfoList(List<TicketPriceAddInfo> addInfoList) {
        this.addInfoList = addInfoList;
    }

    public String getPriceName() {
        return priceName;
    }

    public void setPriceName(String priceName) {
        this.priceName = priceName;
    }

    public String getGetTicket() {
        return getTicket;
    }

    public void setGetTicket(String getTicket) {
        this.getTicket = getTicket;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public List<TicketPriceTypeExtendResponse> getOrderKnowList() {
        return orderKnowList;
    }

    public void setOrderKnowList(List<TicketPriceTypeExtendResponse> orderKnowList) {
        this.orderKnowList = orderKnowList;
    }

    public Boolean getIsTodayValid() {
        return isTodayValid;
    }

    public void setIsTodayValid(Boolean isTodayValid) {
        this.isTodayValid = isTodayValid;
    }

    public Boolean getIsConditionRefund() {
        return isConditionRefund;
    }

    public void setIsConditionRefund(Boolean isConditionRefund) {
        this.isConditionRefund = isConditionRefund;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
