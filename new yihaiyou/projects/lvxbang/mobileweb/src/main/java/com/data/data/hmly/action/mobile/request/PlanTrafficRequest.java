package com.data.data.hmly.action.mobile.request;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

/**
 * Created by huangpeijie on 2016-04-18,0018.
 */
public class PlanTrafficRequest {
    private String key;
    private String trafficHash;
    private String priceHash;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTrafficHash() {
        return trafficHash;
    }

    public void setTrafficHash(String trafficHash) {
        this.trafficHash = trafficHash;
    }

    public String getPriceHash() {
        return priceHash;
    }

    public void setPriceHash(String priceHash) {
        this.priceHash = priceHash;
    }

    public Map<String, Object> toTrafficMap() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(mapper.writeValueAsString(this), Map.class);
    }
}
