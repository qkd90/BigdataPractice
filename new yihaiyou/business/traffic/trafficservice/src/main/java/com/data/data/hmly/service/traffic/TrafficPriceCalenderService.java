package com.data.data.hmly.service.traffic;

import com.data.data.hmly.service.traffic.dao.TrafficPriceCalenderDao;
import com.data.data.hmly.service.traffic.entity.Traffic;
import com.data.data.hmly.service.traffic.entity.TrafficPrice;
import com.data.data.hmly.service.traffic.entity.TrafficPriceCalender;
import com.framework.hibernate.util.Criteria;
import com.zuipin.util.DateUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by dy on 2016/8/24.
 */
@Service
public class TrafficPriceCalenderService {
    @Resource
    private TrafficPriceCalenderDao priceCalenderDao;
    @Resource
    private TrafficPriceService trafficPriceService;




    /**
     * 保存
     * @param trafficPrice
     * @param calenderData
     */
    public void saveCalender(TrafficPrice trafficPrice, String calenderData) {

        delByTrafficPrice(trafficPrice);

        JSONArray jsonArray = JSONArray.fromObject(calenderData);

        List<TrafficPriceCalender> calenderList = new ArrayList<TrafficPriceCalender>();
        List<Float> priceList = new ArrayList<Float>();
        List<Float> marketPriceList = new ArrayList<Float>();
        List<Float> cpriceList = new ArrayList<Float>();
        for (Object o : jsonArray) {
            JSONObject jsonObject = JSONObject.fromObject(o);
            TrafficPriceCalender calender = new TrafficPriceCalender();
            calender.setTraffic(trafficPrice.getTraffic());
            calender.setTrafficPrice(trafficPrice);
            Float price = 0f;
            Float marketPrice = 0f;
            Float cPrice = 0f;
            if (jsonObject.get("price") != null) {
                price = Float.parseFloat(jsonObject.get("price").toString());
            }
            if (jsonObject.get("marketPrice") != null) {
                marketPrice = Float.parseFloat(jsonObject.get("marketPrice").toString());
            }
            if (jsonObject.get("cPrice") != null) {
                cPrice = Float.parseFloat(jsonObject.get("cPrice").toString());
            }

            if (jsonObject.get("inventory") != null) {
                calender.setInventory(Integer.parseInt(jsonObject.get("inventory").toString()));
            }

            if (jsonObject.get("leaveTime") != null) {
                calender.setLeaveTime(DateUtils.getDate(jsonObject.get("leaveTime").toString(), "yyyy-MM-dd HH:mm:ss"));
            }

            if (jsonObject.get("arriveTime") != null) {
                calender.setArriveTime(DateUtils.getDate(jsonObject.get("arriveTime").toString(), "yyyy-MM-dd HH:mm:ss"));
            }

            calender.setPrice(price);
            priceList.add(price);
            calender.setMarketPrice(marketPrice);
            marketPriceList.add(marketPrice);
            calender.setCprice(cPrice);
            cpriceList.add(cPrice);

            calenderList.add(calender);
        }
        if (!calenderList.isEmpty()) {
            priceCalenderDao.save(calenderList);
            trafficPrice.setPrice(Collections.min(priceList));
            trafficPrice.setMarketPrice(Collections.min(marketPriceList));
            trafficPrice.setCprice(Collections.min(cpriceList));
            trafficPriceService.update(trafficPrice);
        }
    }

    /**
     * 通过TrafficPrice删除
     * @param trafficPrice
     */
    public void delByTrafficPrice(TrafficPrice trafficPrice) {
        String hql = "delete from TrafficPriceCalender trc where trc.trafficPrice.id=?";
        priceCalenderDao.updateByHQL(hql, trafficPrice.getId());
    }

    public List<Map<String, String>> findListByDateAndPriceId(Date date, long priceId) {

        List<Map<String, String>> maps = new ArrayList<Map<String, String>>();
        Criteria<TrafficPriceCalender> criteria = new Criteria<TrafficPriceCalender>(TrafficPriceCalender.class);

        criteria.eq("trafficPrice.id", priceId);

        if (date != null) {
            criteria.ge("leaveTime", date);
        }

        List<TrafficPriceCalender> calenders = new ArrayList<TrafficPriceCalender>();
        calenders = priceCalenderDao.findByCriteria(criteria);
        for (TrafficPriceCalender calender : calenders) {
            String dateStr = DateUtils.format(calender.getLeaveTime(), "yyyy-MM-dd");
            Date day = DateUtils.toDate(dateStr);
            if (calender.getPrice() != null) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id", "1" + day.getTime());
                map.put("price", String.valueOf(calender.getPrice()));
                map.put("title", "a.分销：" + calender.getPrice());
                map.put("start", DateUtils.format(day, "yyyy-MM-dd"));
                maps.add(map);
            }
            if (calender.getMarketPrice() != null) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id", "2" + day.getTime());
                map.put("marketPrice", String.valueOf(calender.getMarketPrice()));
                map.put("title", "b.市价：" + calender.getMarketPrice());
                map.put("start", DateUtils.format(day, "yyyy-MM-dd"));
                maps.add(map);
            }
            if (calender.getCprice() != null) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id", "3" + day.getTime());
                map.put("cPrice", String.valueOf(calender.getCprice()));
                map.put("title", "c.C端：" + calender.getCprice());
                map.put("start", DateUtils.format(day, "yyyy-MM-dd"));
                maps.add(map);
            }
            if (calender.getInventory() != null) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id", "4" + day.getTime());
                map.put("inventory", String.valueOf(calender.getInventory()));
                map.put("title", "d.库存：" + calender.getInventory());
                map.put("start", DateUtils.format(day, "yyyy-MM-dd"));
                maps.add(map);
            }
            if (calender.getLeaveTime() != null) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id", "5" + day.getTime());
                map.put("leaveTime", String.valueOf(calender.getLeaveTime()));
                map.put("title", "始" + DateUtils.format(calender.getLeaveTime(), "MM-dd HH:mm"));
                map.put("start", DateUtils.format(day, "yyyy-MM-dd"));
                maps.add(map);
            }
            if (calender.getArriveTime() != null) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id", "6" + day.getTime());
                map.put("arriveTime", String.valueOf(calender.getArriveTime()));
                map.put("title", "终" + DateUtils.format(calender.getArriveTime(), "MM-dd HH:mm"));
                map.put("start", DateUtils.format(day, "yyyy-MM-dd"));
                maps.add(map);
            }
        }
        return maps;
    }

    public void delBytraffic(Traffic traffic) {
        String hql = "delete from TrafficPriceCalender trc where trc.traffic.id=?";
        priceCalenderDao.updateByHQL(hql, traffic.getId());
    }

    public TrafficPriceCalender findMinPrice(Traffic traffic, TrafficPrice trafficPrice, Date startTime) {
        Criteria<TrafficPriceCalender> criteria = new Criteria<TrafficPriceCalender>(TrafficPriceCalender.class);
        criteria.eq("trafficPrice.id", trafficPrice.getId());
        criteria.eq("traffic.id", traffic.getId());
        if (startTime != null) {
            criteria.ge("leaveTime", startTime);
        }

        List<TrafficPriceCalender> trafficPriceCalenders = new ArrayList<TrafficPriceCalender>();
        trafficPriceCalenders = priceCalenderDao.findByCriteria(criteria);

        if (trafficPriceCalenders.isEmpty()) {
            return null;
        }
        return trafficPriceCalenders.get(0);
    }
}
