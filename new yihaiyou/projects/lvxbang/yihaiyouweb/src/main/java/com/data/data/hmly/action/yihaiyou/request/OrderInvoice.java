package com.data.data.hmly.action.yihaiyou.request;

/**
 * Created by huangpeijie on 2016-04-25,0025.
 */
public class OrderInvoice {
    private String name;
    private String telephone;
    private String address;
    private String title;

    public OrderInvoice() {
    }

    public OrderInvoice(com.data.data.hmly.service.order.entity.OrderInvoice orderInvoice) {
        this.name = orderInvoice.getName();
        this.telephone = orderInvoice.getTelephone();
        this.address = orderInvoice.getAddress();
        this.title = orderInvoice.getTitle();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
