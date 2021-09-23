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
public class PlanTest extends BaseTest {
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * 7.1 保存行程
     *
     * @throws Exception
     */
    public void testSave() throws Exception {
        Map<String, Object> map = Maps.newHashMap();
        map.put("id", 0);
        map.put("name", "接口测试保存行程");
        map.put("startCityId", 350200);
        map.put("startDate", "2016-05-17");
        map.put("userId", 151);

        List<Map<String, Object>> days = Lists.newArrayList();
        Map<String, Object> day1 = Maps.newHashMap();
        day1.put("cityId", 310100);

        day1.put("trips", trips());
        days.add(day1);

        Map<String, Object> day2 = Maps.newHashMap();
        day2.put("cityId", 310100);
        List<Map<String, Object>> trips2 = Lists.newArrayList();
        Map<String, Object> trip21 = Maps.newHashMap();
        trip21.put("id", 0);
        trip21.put("scenicId", 2055);
        trip21.put("type", 1);
        trips2.add(trip21);
        day2.put("trips", trips2);
        days.add(day2);

        Map<String, Object> day3 = Maps.newHashMap();
        day3.put("cityId", 310100);
        List<Map<String, Object>> trips3 = Lists.newArrayList();
        Map<String, Object> trip31 = Maps.newHashMap();
        trip31.put("id", 0);
        trip31.put("scenicId", 2067);
        trip31.put("type", 1);
        trips3.add(trip31);

        Map<String, Object> trip32 = Maps.newHashMap();
        trip32.put("id", 0);
        trip32.put("scenicId", 2678);
        trip32.put("type", 1);
        trips3.add(trip32);
        day3.put("trips", trips3);
        days.add(day3);
        map.put("days", days);

        map.put("trafficAndHotel", trafficAndHotel());

        request.setParameter("json", mapper.writeValueAsString(map));
        ActionProxy proxy = getActionProxy("/plan/save.jhtml");
        proxy.execute();
    }

    public List<Map<String, Object>> trips() {
        List<Map<String, Object>> trips1 = Lists.newArrayList();
        Map<String, Object> trip11 = Maps.newHashMap();
        trip11.put("id", 0);
        trip11.put("scenicId", 2034);
        trip11.put("type", 1);
        trips1.add(trip11);

        Map<String, Object> trip12 = Maps.newHashMap();
        trip12.put("id", 0);
        trip12.put("scenicId", 2035);
        trip12.put("type", 1);
        trips1.add(trip12);

        Map<String, Object> trip13 = Maps.newHashMap();
        trip13.put("id", 0);
        trip13.put("scenicId", 2052);
        trip13.put("type", 1);
        trips1.add(trip13);

        Map<String, Object> trip14 = Maps.newHashMap();
        trip14.put("id", 0);
        trip14.put("scenicId", 2116);
        trip14.put("type", 1);
        trips1.add(trip14);

        Map<String, Object> trip15 = Maps.newHashMap();
        trip15.put("id", 0);
        trip15.put("scenicId", 2178);
        trip15.put("type", 1);
        trips1.add(trip15);
        return trips1;
    }

    public List<Map<String, Object>> trafficAndHotel() {
        List<Map<String, Object>> trafficAndHotel = Lists.newArrayList();
        Map<String, Object> t1 = Maps.newHashMap();
        t1.put("cityId", 310100);

//        Map<String, Object> traffic1 = Maps.newHashMap();
//        traffic1.put("priceHash", "1268197400");
//        traffic1.put("trafficHash", "edfc24ef2cea25679e87134ce8ce9745");
//        traffic1.put("key", "350200##310100##TRAIN##20160517");
//        t1.put("traffic", traffic1);

//        Map<String, Object> retraffic1 = Maps.newHashMap();
//        retraffic1.put("priceHash", "625552825");
//        retraffic1.put("trafficHash", "b609df796369482baa743c4cce131953");
//        retraffic1.put("key", "310100##350200##TRAIN##20160521");
//        t1.put("returnTraffic", retraffic1);

        Map<String, Object> hotel1 = Maps.newHashMap();
        hotel1.put("hotelId", 1523200);
        hotel1.put("priceId", 4034642);
        hotel1.put("startDate", "2016-05-17");
        hotel1.put("endDate", "2016-05-21");
        t1.put("hotel", hotel1);
        trafficAndHotel.add(t1);
        return trafficAndHotel;
    }

    /**
     * 7.2 行程列表
     *
     * @throws Exception
     */
    public void testList() throws Exception {
        request.setParameter("pageNo", "1");
        request.setParameter("pageSize", "10");
        ActionProxy proxy = getActionProxy("/plan/list.jhtml");
        proxy.execute();
    }

    /**
     * 搜索行程列表
     *
     * @throws Exception
     */
    public void testSearchList() throws Exception {
        Map<String, Object> map = Maps.newHashMap();
        map.put("name", "厦门");
        request.setParameter("json", mapper.writeValueAsString(map));
        request.setParameter("pageNo", "1");
        request.setParameter("pageSize", "10");
        ActionProxy proxy = getActionProxy("/plan/searchList.jhtml");
        proxy.execute();
    }

    /**
     * 7.3 行程详情
     *
     * @throws Exception
     */
    public void testDetail() throws Exception {
        request.setParameter("planId", "107587");
        ActionProxy proxy = getActionProxy("/plan/detail.jhtml");
        proxy.execute();
    }

    /**
     * 7.4 删除行程
     *
     * @throws Exception
     */
    public void testDelete() throws Exception {
        request.setParameter("userId", "151");
        request.setParameter("planId", "107587");
        ActionProxy proxy = getActionProxy("/plan/delete.jhtml");
        proxy.execute();
    }

    /**
     * 7.5 智能排序
     *
     * @throws Exception
     */
    public void testOptimize() throws Exception {
        Map<String, Object> map = Maps.newHashMap();
        map.put("day", 3);
        map.put("hour", 2);
        map.put("type", 2);

        Map<String, Integer> cityDays = Maps.newHashMap();
        cityDays.put("1201", 1);
        cityDays.put("3101", 2);
        map.put("cityDays", cityDays);

        List<Map<String, Object>> scenicList = Lists.newArrayList();

        Map<String, Object> scenic1 = Maps.newHashMap();
        scenic1.put("type", 1);
        scenic1.put("id", 2067);
        scenicList.add(scenic1);

        Map<String, Object> scenic2 = Maps.newHashMap();
        scenic2.put("type", 1);
        scenic2.put("id", 2678);
        scenicList.add(scenic2);

        Map<String, Object> scenic3 = Maps.newHashMap();
        scenic3.put("type", 1);
        scenic3.put("id", 1664);
        scenicList.add(scenic3);

        map.put("scenicList", scenicList);

        request.setParameter("json", mapper.writeValueAsString(map));
        ActionProxy proxy = getActionProxy("/plan/optimize.jhtml");
        proxy.execute();
    }

    /**
     * 7.6 行程下单
     *
     * @throws Exception
     */
    public void testOrder() throws Exception {
        request.setParameter("planId", "152581");
        request.setParameter("userId", "151");
        ActionProxy proxy = getActionProxy("/plan/order.jhtml");
        proxy.execute();
    }
}
