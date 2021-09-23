package com.data.data.hmly.action.mobile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.opensymphony.xwork2.ActionProxy;
import org.junit.Ignore;

import java.util.List;
import java.util.Map;

/**
 * Created by huangpeijie on 2016-04-25,0025.
 */
@Ignore
public class TrafficTest extends BaseTest {
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * 5.1 推荐交通
     *
     * @throws Exception
     */
    public void testRecommend() throws Exception {
        Map<String, Object> map = Maps.newHashMap();
        map.put("startDate", "2016-05-28");
        map.put("startCityId", 350200);

        List<Map<String, Object>> plan = Lists.newArrayList();

        Map<String, Object> day1 = Maps.newHashMap();
        day1.put("cityId", 120100);
        day1.put("day", 1);
        plan.add(day1);

        Map<String, Object> day2 = Maps.newHashMap();
        day2.put("cityId", 310100);
        day2.put("day", 1);
        plan.add(day2);

        map.put("plan", plan);
        request.setParameter("json", mapper.writeValueAsString(map));
        ActionProxy proxy = getActionProxy("/traffic/recommend.jhtml");
        proxy.execute();
    }

    /**
     * 5.2 交通列表
     *
     * @throws Exception
     */
    public void testList() throws Exception {
        request.setParameter("startDate", "2016-05-02");
        request.setParameter("fromCityId", "310100");
        request.setParameter("toCityId", "350200");
        request.setParameter("trafficType", "AIRPLANE");
        ActionProxy proxy = getActionProxy("/traffic/list.jhtml");
        proxy.execute();
    }
}
