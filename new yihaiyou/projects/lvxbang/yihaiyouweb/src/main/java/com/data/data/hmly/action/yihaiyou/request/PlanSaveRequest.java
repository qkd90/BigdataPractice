package com.data.data.hmly.action.yihaiyou.request;

import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.lvxbang.request.PlanDayUpdateRequest;
import com.data.data.hmly.service.lvxbang.request.PlanUpdateRequest;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zuipin.util.StringUtils;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Created by huangpeijie on 2016-04-19,0019.
 */
public class PlanSaveRequest {
    private Long id;
    private String name;
    private Long startCityId;
    private String startDate;
    private List<PlanDayUpdateRequest> days;
    private List<PlanTrafficAndHotelRequest> trafficAndHotel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStartCityId() {
        return startCityId;
    }

    public void setStartCityId(Long startCityId) {
        this.startCityId = startCityId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public List<PlanDayUpdateRequest> getDays() {
        return days;
    }

    public void setDays(List<PlanDayUpdateRequest> days) {
        this.days = days;
    }

    public List<PlanTrafficAndHotelRequest> getTrafficAndHotel() {
        return trafficAndHotel;
    }

    public void setTrafficAndHotel(List<PlanTrafficAndHotelRequest> trafficAndHotel) {
        this.trafficAndHotel = trafficAndHotel;
    }

    public PlanUpdateRequest toPlanUpdateRequest() throws ParseException {
        PlanUpdateRequest planUpdateRequest = new PlanUpdateRequest();
        planUpdateRequest.setId(this.id);
        planUpdateRequest.setName(this.name);
        planUpdateRequest.setCityId(this.startCityId);
        planUpdateRequest.setStartTime(DateUtils.parseShortTime(this.getStartDate()));
        planUpdateRequest.setDays(this.days);
        return planUpdateRequest;
    }

    public Map<String, Object> toTrafficAndHotelMap() throws IOException {
        Map<String, Object> map = Maps.newHashMap();
        for (PlanTrafficAndHotelRequest trafficAndHotelRequest : trafficAndHotel) {
            Map<String, Object> trafficAndHotel = Maps.newHashMap();
            List<Map<String, Object>> traffics = Lists.newArrayList();
            if (trafficAndHotelRequest.getTraffic() != null && StringUtils.isNotBlank(trafficAndHotelRequest.getTraffic().getKey())) {
                Map<String, Object> traffic = trafficAndHotelRequest.getTraffic().toTrafficMap();
                traffics.add(traffic);
            }
            if (trafficAndHotelRequest.getReturnTraffic() != null && StringUtils.isNotBlank(trafficAndHotelRequest.getReturnTraffic().getKey())) {
                Map<String, Object> returnTraffic = trafficAndHotelRequest.getReturnTraffic().toTrafficMap();
                traffics.add(returnTraffic);
            }
            trafficAndHotel.put("traffic", traffics);
            if (trafficAndHotelRequest.getHotel() != null) {
                trafficAndHotel.put("hotel", trafficAndHotelRequest.getHotel().getPriceId());
            }
            map.put(trafficAndHotelRequest.getCityId().toString(), trafficAndHotel);
        }
        return map;
    }
}
