package com.data.data.hmly.service.lvxbang.response;

/**
 * Created by vacuity on 16/1/18.
 */
public class PersonalRecplanResponse extends PersonalPlanResponse {

    private Integer colloctNum;
    private Integer quoteNum;
    private Integer browsingNum;

    public Integer getColloctNum() {
        return colloctNum;
    }

    public void setColloctNum(Integer colloctNum) {
        this.colloctNum = colloctNum;
    }

    public Integer getQuoteNum() {
        return quoteNum;
    }

    public void setQuoteNum(Integer quoteNum) {
        this.quoteNum = quoteNum;
    }

    public Integer getBrowsingNum() {
        return browsingNum;
    }

    public void setBrowsingNum(Integer browsingNum) {
        this.browsingNum = browsingNum;
    }
}
