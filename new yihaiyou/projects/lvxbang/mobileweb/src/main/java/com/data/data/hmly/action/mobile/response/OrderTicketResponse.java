package com.data.data.hmly.action.mobile.response;


import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.ticket.entity.TicketDateprice;
import com.data.data.hmly.service.ticket.response.TicketPriceAddInfo;

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
    private List<TicketPriceAddInfo> addInfoList;

    public OrderTicketResponse() {
    }

    public OrderTicketResponse(TicketDateprice ticketDateprice) {
        this.ticketId = ticketDateprice.getTicketPriceId().getTicket().getId();
        this.priceId = ticketDateprice.getTicketPriceId().getId();
        this.name = ticketDateprice.getTicketPriceId().getTicket().getTicketName();
        this.priceName = ticketDateprice.getTicketPriceId().getName();
        this.price = ticketDateprice.getPriPrice() + ticketDateprice.getRebate();
        this.playDate = DateUtils.formatShortDate(ticketDateprice.getDate());
        switch (ticketDateprice.getTicketPriceId().getGetTicket()) {
            case "messageget":
                this.getTicket = "短信";
                break;
            case "scenicget":
                this.getTicket = "景区取票";
                break;
            case "teamget":
                this.getTicket = "团队签单";
                break;
            case "sendget":
                this.getTicket = "送票上门";
                break;
            case "selfget":
                this.getTicket = "门市自取";
                break;
            case "otherget":
                this.getTicket = "其他";
                break;
            default:
                break;
        }
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
}
