package com.data.data.hmly.service.traffic;

import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.traffic.dao.TrafficPriceDao;
import com.data.data.hmly.service.traffic.entity.Traffic;
import com.data.data.hmly.service.traffic.entity.TrafficPrice;
import com.data.data.hmly.service.traffic.entity.TrafficPriceCalender;
import com.data.data.hmly.service.traffic.entity.enums.TrafficType;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by guoshijie on 2015/12/11.
 */
@Service
public class TrafficPriceService {

    @Resource
    private TrafficPriceDao trafficPriceDao;
    @Resource
    private TrafficPriceCalenderService trafficPriceCalenderService;
//    @Resource
//    private TrafficDao trafficDao;
//    @Resource
//    private TrafficPriceService trafficPriceService;
    @Resource
    private TrafficJuheFlightService trafficJuheService;

//    /**
//     * 获取交通价格列表
//     *
//     * @param leaveCityId  出发城市
//     * @param arriveCityId 到达城市
//     * @param date         日期
//     * @param type         类型：飞机、火车等
//     * @return 对应条件的交通价格列表，包含座次、价格、始发到达时间等
//     */
//    public List<TrafficPrice> findByCityAndDate(Long leaveCityId, Long arriveCityId, Date date, TrafficType type) {
//        List<Traffic> traffics = trafficService.doQueryAndSaveByCity(leaveCityId, arriveCityId, type , date);
//        List<TrafficPrice> result = new ArrayList<TrafficPrice>();
//        for (Traffic traffic : traffics) {
//            List<TrafficPrice> prices = findByIdAndDate(traffic.getId(), date);
//            if (prices != null && !prices.isEmpty()) {
//                result.addAll(prices);
//            } else if (type == TrafficType.AIRPLANE) {
//                //如果符合条件的trafficPrices为null，去聚合接口查询一次
////                List<TrafficPrice> juhePrices = trafficJuheService.getTrafficPrices(traffic, date, "");
//                List<TrafficPrice> juhePrices = trafficJuheService.getTrafficPrices(traffic, date, "");
//                result.addAll(juhePrices);
//                trafficPriceDao.save(juhePrices);
//            }
//        }
//        return result;
//    }

    /**
     * 获取交通价格列表
     *
     * @param leaveCityId  出发城市
     * @param arriveCityId 到达城市
     * @param date         日期
     * @param type         类型：飞机、火车等
     * @return 对应条件的交通价格列表，包含座次、价格、始发到达时间等
     */
    public List<TrafficPrice> findByCityAndDate(Long leaveCityId, Long arriveCityId, Date date, TrafficType type) {
        List<TrafficPrice> prices = new ArrayList<TrafficPrice>();
        if (prices != null && !prices.isEmpty()) {
            prices.addAll(prices);
        } else if (type == TrafficType.AIRPLANE) {
            //如果符合条件的trafficPrices为null，去聚合接口查询一次
            List<TrafficPrice> juhePrices = trafficJuheService.getTrafficPrices(new TbArea(leaveCityId),
                    new TbArea(arriveCityId), date, "");
            if (juhePrices == null)
                return null;
            prices.addAll(juhePrices);
//            List<Traffic> traffics = new ArrayList<>();
            List<Long> trafficIds = new ArrayList<Long>();
            for (TrafficPrice price : prices) {
                Traffic traffic = price.getTraffic();
                if (trafficIds.contains(traffic.getId())) {
                    continue;
                }
//                trafficDao.save(traffic);
            }
//            trafficPriceDao.save(juhePrices);
        }
        return prices;
    }

    public List<TrafficPrice> findByIdAndDate(Long trafficId, Date date) {
        TrafficPrice trafficPrice = new TrafficPrice();
        Traffic traffic = new Traffic();
        traffic.setId(trafficId);
        trafficPrice.setTraffic(traffic);
        trafficPrice.setLeaveTime(date);
        trafficPrice.setCreateTime(new Date());
        return list(trafficPrice, new Page(0, 1));
    }

    public List<TrafficPrice> list(TrafficPrice trafficPrice, Page page, String... orderProperties) {
        Criteria<TrafficPrice> criteria = createCriteria(trafficPrice, orderProperties);
        if (page == null) {
            return trafficPriceDao.findByCriteria(criteria);
        }
        return trafficPriceDao.findByCriteria(criteria, page);
    }

    private Criteria<TrafficPrice> createCriteria(TrafficPrice trafficPrice, String... orderProperties) {
        Criteria<TrafficPrice> criteria = new Criteria<TrafficPrice>(TrafficPrice.class);
        if (orderProperties.length == 2) {
            criteria.orderBy(orderProperties[0], orderProperties[1]);
        } else if (orderProperties.length == 1) {
            criteria.orderBy(Order.desc(orderProperties[0]));
        }
        if (trafficPrice.getTraffic() != null) {
            criteria.eq("traffic.id", trafficPrice.getTraffic().getId());
        }
        if (StringUtils.isNotBlank(trafficPrice.getName())) {
            criteria.like("name", trafficPrice.getName());
        }
        if (trafficPrice.getDate() != null) {
            criteria.eq("leaveTime", trafficPrice.getLeaveTime());
        }
        if (trafficPrice.getCreateTime() != null) {
            criteria.gt("createTime", trafficPrice.getCreateTime());
        }
        return criteria;
    }

    public TrafficPrice forOrder(Long trafficId, Date date) {
        TrafficPrice trafficPrice = new TrafficPrice();
        Traffic traffic = new Traffic();
        traffic.setId(trafficId);
        trafficPrice.setTraffic(traffic);
        trafficPrice.setLeaveTime(date);
        Criteria<TrafficPrice> criteria = createCriteria(trafficPrice);
        return trafficPriceDao.findUniqueByCriteria(criteria);
    }

    public TrafficPrice get(Long id) {
        return trafficPriceDao.load(id);
    }

    public TrafficPrice findFullById(Long id) {
        Criteria<TrafficPrice> criteria = new Criteria<TrafficPrice>(TrafficPrice.class);
        DetachedCriteria trafficCriteria = criteria.createCriteria("traffic");
        trafficCriteria.createCriteria("leaveCity");
        trafficCriteria.createCriteria("arriveCity");
        trafficCriteria.createCriteria("leaveTransportation");
        trafficCriteria.createCriteria("arriveTransportation");
        criteria.eq("id", id);
        return trafficPriceDao.findUniqueByCriteria(criteria);
     }

    public List<TrafficPrice> getForPlanOrder(Date date, Long trafficId) {
        //
        Criteria<TrafficPrice> criteria = new Criteria<TrafficPrice>(TrafficPrice.class);
        if (date != null) {
            criteria.eq("leaveTime", date);
        }
        if (trafficId != null) {
            criteria.eq("traffic.id", trafficId);
        }
        criteria.orderBy("price", "asc");
        return trafficPriceDao.findByCriteria(criteria);
    }

    public List<TrafficPrice> getByIds(String idsString) {
        if (!StringUtils.isNotBlank(idsString)) {
            //
            return null;
        }
        Criteria<TrafficPrice> criteria = new Criteria<TrafficPrice>(TrafficPrice.class);
        String[] idStringList = idsString.split(",");
        Long[] ids = new Long[idStringList.length];
        for (int i = 0; i < idStringList.length; i++) {
            ids[i] = Long.parseLong(idStringList[i]);
        }
        criteria.in("id", ids);
        //
        return trafficPriceDao.findByCriteria(criteria);
    }

    public void save(TrafficPrice trafficPrice) {
        trafficPriceDao.save(trafficPrice);
    }

    public void update(TrafficPrice tempTrafficPrice) {
        trafficPriceDao.update(tempTrafficPrice);
    }

    public List<TrafficPrice> getTrafficPriceListByTraffic(Long trafficId) {
        Criteria<TrafficPrice> criteria = new Criteria<TrafficPrice>(TrafficPrice.class);
        criteria.eq("traffic.id", trafficId);
        return trafficPriceDao.findByCriteria(criteria);
    }

    public void delTrafficPrice(TrafficPrice trafficPrice) {
        String hql = "delete from TrafficPrice tp where tp.id=?";
        trafficPriceDao.updateByHQL(hql, trafficPrice.getId());
    }

    public void delByTraffic(Traffic traffic) {
        String hql = "delete from TrafficPrice tp where tp.traffic.id=?";
        trafficPriceDao.updateByHQL(hql, traffic.getId());
        trafficPriceCalenderService.delBytraffic(traffic);
    }

    public void findMinPriceAndInventory(Traffic trc, Date leaveDate) {
        List<Float> minMarketPrice = new ArrayList<Float>();
        List<Integer> minInventory = new ArrayList<Integer>();
        List<TrafficPrice> trafficPrices = new ArrayList<TrafficPrice>();
        trafficPrices = getTrafficPriceListByTraffic(trc.getId());
        for (TrafficPrice price : trafficPrices) {
            TrafficPriceCalender minPriceCalender = trafficPriceCalenderService.findMinPrice(trc, price, leaveDate);
            if (minPriceCalender != null) {
                minMarketPrice.add(minPriceCalender.getMarketPrice());
                minInventory.add(minPriceCalender.getInventory());
            }
        }

        if (!minMarketPrice.isEmpty()) {
            trc.setPrice(Collections.min(minMarketPrice));
        }
        if (!minInventory.isEmpty()) {
            trc.setFerryInventory(Collections.min(minInventory));
        }
    }


    }


