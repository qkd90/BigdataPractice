package com.data.data.hmly.action.mobile.response;

import com.data.data.hmly.action.mobile.request.OrderContact;
import com.data.data.hmly.action.mobile.request.SimpleTourist;
import com.data.data.hmly.service.order.entity.Order;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by huangpeijie on 2016-08-03,0003.
 */
public class TicketOrderResponse extends OrderSimpleResponse {
    private OrderContact contact;
    private Long scenicId;
    private Integer num;
    private String getTicket;
    private List<SimpleTourist> tourists = Lists.newArrayList();

    public TicketOrderResponse() {
    }

    public TicketOrderResponse(Order order) {
        super(order);
    }

    public OrderContact getContact() {
        return contact;
    }

    public void setContact(OrderContact contact) {
        this.contact = contact;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getGetTicket() {
        return getTicket;
    }

    public void setGetTicket(String getTicket) {
        this.getTicket = getTicket;
    }

    public Long getScenicId() {
        return scenicId;
    }

    public void setScenicId(Long scenicId) {
        this.scenicId = scenicId;
    }

    public List<SimpleTourist> getTourists() {
        return tourists;
    }

    public void setTourists(List<SimpleTourist> tourists) {
        this.tourists = tourists;
    }

    public void completeGetTicket(String getTicket) {
        switch (getTicket) {
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
}
