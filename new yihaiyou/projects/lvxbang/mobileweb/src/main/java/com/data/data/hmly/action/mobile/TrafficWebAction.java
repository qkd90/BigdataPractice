package com.data.data.hmly.action.mobile;

import com.data.data.hmly.action.mobile.request.PlanRecommendRequest;
import com.data.data.hmly.action.mobile.response.TrafficPriceResponse;
import com.data.data.hmly.action.mobile.response.TrafficRecommendResponse;
import com.data.data.hmly.action.mobile.response.TrafficResponse;
import com.data.data.hmly.service.TrafficMobileService;
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.traffic.TrafficService;
import com.data.data.hmly.service.traffic.entity.Traffic;
import com.data.data.hmly.service.traffic.entity.enums.TrafficType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.struts.AjaxCheck;
import com.google.common.collect.Lists;
import com.opensymphony.xwork2.Result;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by huangpeijie on 2016-04-13,0013.
 */
public class TrafficWebAction extends MobileBaseAction {
    @Resource
    private TrafficService trafficService;
    @Resource
    private TrafficMobileService trafficMobileService;

    private final ObjectMapper mapper = new ObjectMapper();

    public String json;
    public String startDate;
    public Long fromCityId;
    public Long toCityId;
    public TrafficType trafficType;
    public PlanRecommendRequest planRecommendRequest;

    /**
     * 推荐交通
     *
     * @return
     */
    @AjaxCheck
    public Result recommend() throws IOException, ParseException {
        planRecommendRequest = mapper.readValue(json, PlanRecommendRequest.class);
        List<TrafficRecommendResponse> responseList = trafficMobileService.recommendTraffics(planRecommendRequest);
        result.put("trafficList", responseList);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    /**
     * 交通列表
     *
     * @return
     */
    @AjaxCheck
    public Result list() throws ParseException {
        List<Traffic> traffics = trafficService.doQueryAndSaveByCityOrPort(fromCityId, "", toCityId, "", trafficType, DateUtils.parseShortTime(startDate));
        List<TrafficResponse> list = Lists.newArrayList();
        for (Traffic traffic : traffics) {
            TrafficResponse response = new TrafficResponse(traffic);
            Collections.sort(response.getPrices(), new Comparator<TrafficPriceResponse>() {
                @Override
                public int compare(TrafficPriceResponse o1, TrafficPriceResponse o2) {
                    return o1.getPrice().intValue() - o2.getPrice().intValue();
                }
            });
            list.add(response);
        }
        result.put("trafficList", list);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }
}
