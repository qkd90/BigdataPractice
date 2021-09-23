package com.data.data.hmly.service.ticket.response;

import java.util.List;

/**
 * Created by huangpeijie on 2016-03-02,0002.
 */
public class TicketPriceAddInfo extends com.framework.hibernate.util.Entity {
    private String subTitle;
    private List<String> descDetails;

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public List<String> getDescDetails() {
        return descDetails;
    }

    public void setDescDetails(List<String> descDetails) {
        this.descDetails = descDetails;
    }
}
