package com.data.data.hmly.action.mobile.response;

import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.traffic.entity.Traffic;
import com.data.data.hmly.service.traffic.entity.TrafficPrice;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by huangpeijie on 2016-04-18,0018.
 */
public class TrafficResponse extends BaseTrafficResponse {
    private String trafficHash;
    private String leaveDate;
    private List<TrafficPriceResponse> prices = Lists.newArrayList();


    public TrafficResponse() {
    }

    public TrafficResponse(Traffic traffic) {
        super(traffic);
        this.trafficHash = traffic.getHashCode();
        if (traffic.getPrices() != null && !traffic.getPrices().isEmpty()) {
            this.leaveDate = DateUtils.formatShortDate(traffic.getPrices().get(0).getLeaveTime());
        }
        for (TrafficPrice trafficPrice : traffic.getPrices()) {
            this.prices.add(new TrafficPriceResponse(trafficPrice));
        }
    }

    public String getTrafficHash() {
        return trafficHash;
    }

    public void setTrafficHash(String trafficHash) {
        this.trafficHash = trafficHash;
    }

    public List<TrafficPriceResponse> getPrices() {
        return prices;
    }

    public void setPrices(List<TrafficPriceResponse> prices) {
        this.prices = prices;
    }

    public String getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(String leaveDate) {
        this.leaveDate = leaveDate;
    }
}
