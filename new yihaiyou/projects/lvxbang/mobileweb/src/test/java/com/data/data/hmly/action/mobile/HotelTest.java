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
public class HotelTest extends BaseTest {
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * 6.1 推荐酒店
     *
     * @throws Exception
     */
    public void testRecommend() throws Exception {
        Map<String, Object> map = Maps.newHashMap();
        map.put("startDate", "2016-05-10");

        List<Map<String, Object>> plan = Lists.newArrayList();

        Map<String, Object> day1 = Maps.newHashMap();
        day1.put("cityId", 310100);
        day1.put("day", 2);

        List<Map<String, Object>> tripList1 = Lists.newArrayList();
        Map<String, Object> trip1 = Maps.newHashMap();
        trip1.put("id", 1563);
        trip1.put("ranking", 123);
        tripList1.add(trip1);

        Map<String, Object> trip2 = Maps.newHashMap();
        trip2.put("id", 2067);
        trip2.put("ranking", 120);
        tripList1.add(trip2);

        Map<String, Object> trip3 = Maps.newHashMap();
        trip3.put("id", 2034);
        trip3.put("ranking", 12);
        tripList1.add(trip3);
        day1.put("tripList", tripList1);
        plan.add(day1);

        Map<String, Object> day2 = Maps.newHashMap();
        day2.put("cityId", 350200);
        day2.put("day", 3);

        List<Map<String, Object>> tripList2 = Lists.newArrayList();
        Map<String, Object> trip4 = Maps.newHashMap();
        trip4.put("id", 1059640);
        trip4.put("ranking", 12);
        tripList2.add(trip4);

        Map<String, Object> trip5 = Maps.newHashMap();
        trip5.put("id", 1059641);
        trip5.put("ranking", 120);
        tripList2.add(trip5);

        Map<String, Object> trip6 = Maps.newHashMap();
        trip6.put("id", 1059642);
        trip6.put("ranking", 110);
        tripList2.add(trip6);
        day2.put("tripList", tripList1);
        plan.add(day2);

        map.put("plan", plan);
        request.setParameter("json", mapper.writeValueAsString(map));
        ActionProxy proxy = getActionProxy("/hotel/recommend.jhtml");
        proxy.execute();
    }

    /**
     * 6.2 酒店列表
     *
     * @throws Exception
     */
    public void testList() throws Exception {
        Map<String, Object> map = Maps.newHashMap();
        map.put("name", "");
        List<Long> cityIds = Lists.newArrayList(350200l);
        map.put("cityIds", cityIds);
        map.put("star", "");
        List<Float> priceRange = Lists.newArrayList(0f, 100000f);
        map.put("priceRange", priceRange);
        map.put("orderColumn", "productScore");
        map.put("orderType", "desc");
        map.put("startDate", "2016-04-28");
        map.put("endDate", "2016-05-02");

        request.setParameter("json", mapper.writeValueAsString(map));
        request.setParameter("pageNo", "1");
        request.setParameter("pageSize", "10");
        ActionProxy proxy = getActionProxy("/hotel/list.jhtml");
        proxy.execute();
    }

    /**
     * 6.3 酒店详情
     *
     * @throws Exception
     */
    public void testDetail() throws Exception {
        request.setParameter("id", "1688306");
        request.setParameter("startDate", "2016-04-28");
        request.setParameter("endDate", "2016-05-02");
        ActionProxy proxy = getActionProxy("/hotel/detail.jhtml");
        proxy.execute();
    }
}
