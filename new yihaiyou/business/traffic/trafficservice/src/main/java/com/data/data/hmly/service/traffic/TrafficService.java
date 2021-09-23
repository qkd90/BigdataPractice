package com.data.data.hmly.service.traffic;

import com.data.data.hmly.service.common.entity.enums.ProductSource;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.traffic.dao.TrafficDao;
import com.data.data.hmly.service.traffic.entity.Traffic;
import com.data.data.hmly.service.traffic.entity.TrafficPrice;
import com.data.data.hmly.service.traffic.entity.enums.TrafficType;
import com.data.data.hmly.service.transportation.TransportationService;
import com.data.data.hmly.service.transportation.entity.Transportation;
import com.data.hmly.service.translation.train.Qunar.PassBy;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.DateUtils;
import com.zuipin.util.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by guoshijie on 2015/12/11.
 */
@Service
public class TrafficService {

    @Resource
    private TrafficDao trafficDao;
    @Resource
    private TrafficPriceService trafficPriceService;



    @Resource
    private TrafficJuheFlightService trafficJuheFlightService;
    @Resource
    private TransportationService transportationService;
    @Resource
    private TrafficJuheTrainService trafficJuheTrainService;
//    private Traffic12306TrainService traffic12306TrainService;
    @Resource
    private TrafficQunarTrainService trafficQunarTrainService;

    private static final Log LOG = LogFactory.getLog(TrafficService.class);


    private static final Integer TIME_OUT_HOUR = 1;
    private static final String JOIN = "-";
    private static final String CHECK_INTER_KEY = "yyyyMMddHH";
    private final ScheduledExecutorService outTimeTrafficChecker = Executors.newSingleThreadScheduledExecutor();

    @PostConstruct
    public void startCheck() {
        outTimeTrafficChecker.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                removeTimeOutTraffic(PLAN_TRAFFIC, TrafficType.AIRPLANE);
                removeTimeOutTraffic(TRAIN_TRAFFIC, TrafficType.TRAIN);
            }
        }, TIME_OUT_HOUR, TIME_OUT_HOUR, TimeUnit.HOURS);
    }

    private void removeTimeOutTraffic(Map<String, List<Traffic>> planTraffic, TrafficType trafficType) {
        for (String key : planTraffic.keySet()) {
            String[] keys = key.split(JOIN);
            if (keys.length == 4) {
                String timeStr = keys[keys.length - 1];
                try {
                    Date time = DateUtils.parse(timeStr, CHECK_INTER_KEY);
                    Long diff = DateUtils.getDateDiffHour(new Date(), time);
                    //diff >= TIME_OUT_HOUR + 1 避免上一个小时获取，这一个小时  马上进行清处，web端又没动，记住了之前数据，导致异常，帮延迟一小时清除
                    if (diff >= TIME_OUT_HOUR + 3) {
                        LOG.info(String.format("%s time out", timeStr));
                        removeTrafficAndPrices(planTraffic, trafficType, key);
                    }
                } catch (ParseException e) {
                    LOG.error(e.getMessage(), e);
                }
            }
        }
    }

    private void removeTrafficAndPrices(Map<String, List<Traffic>> planTraffic, TrafficType trafficType, String key) {
        List<Traffic> removeTraffics = planTraffic.remove(key);
        ConcurrentHashMap<String, TrafficPrice> prices = null;
        if (trafficType == TrafficType.AIRPLANE) {
            prices = PLAN_TRAFFIC_PRICES;
        } else {
            prices = TRAIN_TRAFFIC_PRICES;
        }
        for (Traffic traffic : removeTraffics) {
            for (TrafficPrice trafficPrice : traffic.getPrices()) {
                prices.remove(trafficPrice);
            }
        }
    }

    public static final ConcurrentHashMap<String, List<Traffic>> PLAN_TRAFFIC = new ConcurrentHashMap<String, List<Traffic>>();
    public static final ConcurrentHashMap<String, List<Traffic>> TRAIN_TRAFFIC = new ConcurrentHashMap<String, List<Traffic>>();
    public static final ConcurrentHashMap<String, TrafficPrice> PLAN_TRAFFIC_PRICES = new ConcurrentHashMap<String, TrafficPrice>();
    public static final ConcurrentHashMap<String, TrafficPrice> TRAIN_TRAFFIC_PRICES = new ConcurrentHashMap<String, TrafficPrice>();
//    private static final List<TrafficPrice> TRAFFIC_PRICE = new CopyOnWriteArrayList<TrafficPrice>();

    public Traffic get(Long id) {
        return trafficDao.load(id);
    }

    public TrafficPrice getPlanPriceByHashCode(String hashCode) {
        return PLAN_TRAFFIC_PRICES.get(hashCode);
    }

    public TrafficPrice getTrainPriceByHashCode(String hashCode) {
        return TRAIN_TRAFFIC_PRICES.get(hashCode);
    }


    public List<Traffic> doQueryAndSaveByCityOrPort(Long leaveCityId, String leavePort, Long arriveCityId, String arrivePort, TrafficType type, Date date) {
        Long leaveCity = leaveCityId;
        Long arriveCity = arriveCityId;
        if (leavePort != null && !"".equals(leavePort)) {
            leaveCity = (long) transportationService.findByCode(leavePort, type.equals(TrafficType.TRAIN) ? 1 : 2).getCityId();
        }
        if (arrivePort != null && !"".equals(arrivePort)) {
            arriveCity = (long) transportationService.findByCode(arrivePort, type.equals(TrafficType.TRAIN) ? 1 : 2).getCityId();
        }
        List<Traffic> trafficsAll = doQueryAndSaveByCity(leaveCity, arriveCity, type, date);
        if (trafficsAll == null) {
            return null;
        }
        LOG.info(String.format("==================== %s ", DateUtils.formatDate(date)));
        for (Traffic traffic : trafficsAll) {
            try {
                for (TrafficPrice price : traffic.getPrices()) {
                    LOG.info(String.format("leave %s arriave %s ", DateUtils.formatDate(price.getLeaveTime()), DateUtils.formatDate(price.getLeaveTime())));
                }
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }

        }
        List<Traffic> traffics = new ArrayList<Traffic>();
        //如果有指定leavePort或者arrivePort时,将不符合站点的过滤掉
        for (Traffic traffic : trafficsAll) {
            if (leavePort != null && !traffic.getLeaveTransportation().getCode().contains(leavePort))
                continue;
            if (arrivePort != null && !traffic.getArriveTransportation().getCode().contains(arrivePort))
                continue;
            traffics.add(traffic);
        }
        return traffics;
    }


    public List<Traffic> doQueryAndSaveByCityOrPort(Long leaveCityId, Transportation leavePort, Long arriveCityId, Transportation arrivePort, TrafficType type, Date date) {
        long leaveCity = leaveCityId;
        long arriveCity = arriveCityId;
        if (leavePort != null) {
            leaveCity = (long) leavePort.getCityId();
        }
        if (arrivePort != null) {
            arriveCity = (long) arrivePort.getCityId();
        }
        List<Traffic> trafficsAll = doQueryAndSaveByCity(leaveCity, arriveCity, type, date);
        List<Traffic> traffics = new ArrayList<Traffic>();
        //如果有指定leavePort或者arrivePort时,将不符合站点的过滤掉
        for (Traffic traffic : trafficsAll) {

            if (leavePort != null && !traffic.getLeaveTransportation().getCode().contains(leavePort.getCode()))
                continue;
            if (arrivePort != null && !traffic.getArriveTransportation().getCode().contains(arrivePort.getCode()))
                continue;
            traffics.add(traffic);
        }
        return traffics;
    }


    //public static Map<String, List<Traffic>> trafficsStatic;

    /**
     * 获取交通列表,先从数据库取,若取不到则调用相应接口(*如机票接口为聚合数据),获取相应数据.
     *
     * @param leaveCityId  出发城市
     * @param arriveCityId 到达城市
     * @param type         类型：飞机、火车等
     * @return 对应类型的交通列表，包含始发城市、班次、始发到达时等
     */
    public List<Traffic> doQueryAndSaveByCity(Long leaveCityId, Long arriveCityId, TrafficType type, Date date) {
        TbArea arrivalCity = new TbArea();
        arrivalCity.setId(arriveCityId);
        TbArea leaveCity = new TbArea();
        leaveCity.setId(leaveCityId);
        List<Traffic> traffics = null;
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        LOG.info(String.format("start fetching juhe %s %s", type, stopWatch));
        if (!checkIfHasToFech(leaveCityId, arriveCityId, type, date)) {
            //如果符合条件的traffic为null，去聚合接口查询一次
            if (type == TrafficType.AIRPLANE) {
                traffics = trafficJuheFlightService.getTraffics(leaveCity, arrivalCity, date);
//                LOG.info(String.format("_______AIRPLANE %d _______", traffics.size()));
            } else if (type == TrafficType.TRAIN) {
                traffics = trafficJuheTrainService.getTraffics(leaveCity, arrivalCity, date);
//                LOG.info(String.format("_______TRAIN %d _______", traffics.size()));
            }
        } else {
            traffics = findTraffic(leaveCity, arrivalCity, type, date);
        }
        stopWatch.stop();
        if (traffics == null || traffics.isEmpty()) {
            LOG.info(String.format("end fetching juhe %s %s %d", type, stopWatch, traffics != null ? traffics.size() : 0));
        }
        LOG.info(String.format("end fetching juhe %s %s %d", type, stopWatch, traffics != null ? traffics.size() : 0));
        return traffics;
    }

    private List<Traffic> findTraffic(TbArea leaveCity, TbArea arrivalCity, TrafficType type, Date date) {
        return findTraffic(leaveCity.getId(), arrivalCity.getId(), type, DateUtils.format(date, "yyyyMMdd"));
    }

    public static List<Traffic> findTraffic(Object leaveCityId, Object arrivalCityId, TrafficType type, String date) {
        String key = makeTrafficKey(leaveCityId, arrivalCityId, date);
        String passHourKey = makeTrafficPassHourKey(leaveCityId, arrivalCityId, date);
        LOG.info("findTraffic  " + key);
        if (type == TrafficType.AIRPLANE) {
            List<Traffic> traffics = PLAN_TRAFFIC.get(key);
            return traffics == null ? PLAN_TRAFFIC.get(passHourKey) : traffics;
        } else if (type == TrafficType.TRAIN) {
//            return TRAIN_TRAFFIC.get(key);
            List<Traffic> traffics = TRAIN_TRAFFIC.get(key);
            return traffics == null ? TRAIN_TRAFFIC.get(passHourKey) : traffics;
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    private boolean checkIfHasToFech(Long leaveCityId, Long arriveCityId, TrafficType type, Date date) {
        String key = makeTrafficKey(leaveCityId, arriveCityId, date);
        String tips = String.format("exist key : %s", key);
        if (type == TrafficType.AIRPLANE) {
            List<Traffic> traffices = PLAN_TRAFFIC.get(key);
            if (traffices == null) {
                return false;
            } else {
                LOG.info(tips);
                return true;
            }
        } else if (type == TrafficType.TRAIN) {
            List<Traffic> traffices = TRAIN_TRAFFIC.get(key);
            if (traffices == null) {
                return false;
            } else {
                LOG.info(tips);
                return true;
            }
        } else {
            return false;
        }
    }

    public static String makeTrafficKey(Object leaveCityId, Object arriveCityId, Date date) {
        return makeTrafficKey(leaveCityId, arriveCityId, DateUtils.format(date, "yyyyMMdd"));
    }

    public static String makeTrafficKey(Object leaveCityId, Object arriveCityId, String date) {
        return leaveCityId + JOIN + arriveCityId + JOIN + date + JOIN + DateUtils.format(new Date(), CHECK_INTER_KEY);
    }

    public static String makeTrafficPassHourKey(Object leaveCityId, Object arriveCityId, String date) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR_OF_DAY, -1);
        return leaveCityId + JOIN + arriveCityId + JOIN + date + JOIN + DateUtils.format(c.getTime(), CHECK_INTER_KEY);
    }

    public List<Traffic> list(Traffic traffic, Page page, String... orderProperties) {
        Criteria<Traffic> criteria = createCriteria(traffic, orderProperties);
        if (page == null) {
            return trafficDao.findByCriteria(criteria);
        }
        return trafficDao.findByCriteria(criteria, page);
    }
    public List<Traffic> getValidlist(Traffic traffic, Page page, String... orderProperties) {

//        String hql = "select * from traffic t where t.trafficType = 'SHIP' and exists " +
//                "(select 1 from traffic_price_calendar tpc where tpc.trafficId = t.productId and tpc.leaveTime > '2016-08-28 00:00:00');";

        List paramList = new ArrayList();

        StringBuffer sb = new StringBuffer();

        sb.append("from Traffic t where");

        formatterHql(sb, paramList, traffic);

        if (traffic.getLeaveDateStart() != null) {
            if (paramList.isEmpty()) {
                sb.append(" exists (select 1 from TrafficPriceCalender tpc where");
                sb.append(" tpc.traffic.id = t.id");
                sb.append(" and tpc.leaveTime >=?");
                paramList.add(traffic.getLeaveDateStart());
                sb.append(" and tpc.leaveTime <=?)");
                paramList.add(traffic.getLeaveDateEnd());
            } else {
                sb.append(" and exists (select 1 from TrafficPriceCalender tpc where");
                sb.append(" tpc.traffic.id = t.id");
                sb.append(" and tpc.leaveTime >=?");
                paramList.add(traffic.getLeaveDateStart());
                sb.append(" and tpc.leaveTime <=?)");
                paramList.add(traffic.getLeaveDateEnd());
            }
        }

        if (orderProperties.length == 2) {
            sb.append(" order by t.");
            sb.append(orderProperties[0]);
            sb.append(" " + orderProperties[1]);

        } else if (orderProperties.length == 1) {
            sb.append(" order by t.");
            sb.append(orderProperties[0]);
            sb.append(" asc");
        }

        return trafficDao.findByHQL(sb.toString(), page, paramList.toArray());
    }

    public void formatterHql(StringBuffer sb, List paramList, Traffic traffic) {
        if (StringUtils.isNotBlank(traffic.getName())) {
            if (paramList.isEmpty()) {
                sb.append(" t.name=?");
            } else {
                sb.append(" and t.name=");
            }
            paramList.add(traffic.getName());
        }
        if (traffic.getStatus() != null) {
            if (paramList.isEmpty()) {
                sb.append(" t.status=?");
            } else {
                sb.append(" and t.status=?");
            }
            paramList.add(traffic.getStatus());
        }
        if (StringUtils.isNotBlank(traffic.getTrafficCode())) {
            if (paramList.isEmpty()) {
                sb.append(" t.trafficCode=?");
            } else {
                sb.append(" and t.trafficCode=?");
            }
            paramList.add(traffic.getTrafficCode());
        }
        if (StringUtils.isNotBlank(traffic.getTrafficModel())) {
            if (paramList.isEmpty()) {
                sb.append(" t.trafficModel=?");
            } else {
                sb.append(" and t.trafficModel=?");
            }
            paramList.add(traffic.getTrafficModel());
        }
        if (traffic.getLeaveTransportation() != null
                && traffic.getLeaveTransportation().getId() != null
                && traffic.getLeaveTransportation().getId() != 0) {

            if (paramList.isEmpty()) {
                sb.append(" t.leaveTransportation.id=?");
            } else {
                sb.append(" and t.leaveTransportation.id=?");
            }
            paramList.add(traffic.getLeaveTransportation().getId());

        }
        if (traffic.getArriveTransportation() != null
                && traffic.getArriveTransportation().getId() != null
                && traffic.getArriveTransportation().getId() != 0) {
            if (paramList.isEmpty()) {
                sb.append(" t.arriveTransportation.id=?");
            } else {
                sb.append(" and t.arriveTransportation.id=?");
            }
            paramList.add(traffic.getArriveTransportation().getId());
        }
        if (traffic.getTrafficType() != null) {
            if (paramList.isEmpty()) {
                sb.append(" t.trafficType=?");
            } else {
                sb.append(" and t.trafficType=?");
            }
            paramList.add(traffic.getTrafficType());
        }
        if (traffic.getUser() != null) {
            if (paramList.isEmpty()) {
                sb.append(" t.user.id=?");
            } else {
                sb.append(" and t.user.id=?");
            }
            paramList.add(traffic.getUser().getId());
        }
        if (traffic.getLeaveCity() != null) {
            if (paramList.isEmpty()) {
                sb.append(" t.leaveCity.id=?");
            } else {
                sb.append(" and t.leaveCity.id=?");
            }
            paramList.add(traffic.getLeaveCity().getId());
        }
        if (traffic.getArriveCity() != null) {
            if (paramList.isEmpty()) {
                sb.append(" t.arriveCity.id=?");
            } else {
                sb.append(" and t.arriveCity.id=?");
            }
            paramList.add(traffic.getArriveCity().getId());
        }

        if (traffic.getSource() != null) {
            if (traffic.getSource() == ProductSource.LXB) {
                if (paramList.isEmpty()) {
                    sb.append(" t.source is null");
                } else {
                    sb.append(" and t.source is null");
                }

            } else {
                if (paramList.isEmpty()) {
                    sb.append(" t.source=?");
                } else {
                    sb.append(" and t.source=?");
                }
                paramList.add(traffic.getSource());
            }
        }

    }

    private Criteria<Traffic> createCriteria(Traffic traffic, String... orderProperties) {
        Criteria<Traffic> criteria = new Criteria<Traffic>(Traffic.class);
        if (orderProperties.length == 2) {
            criteria.orderBy(orderProperties[0], orderProperties[1]);
        } else if (orderProperties.length == 1) {
            criteria.orderBy(Order.desc(orderProperties[0]));
        }
        if (StringUtils.isNotBlank(traffic.getName())) {
            criteria.like("name", traffic.getName());
        }
        if (traffic.getStatus() != null) {
            criteria.eq("status", traffic.getStatus());
        }
        if (StringUtils.isNotBlank(traffic.getTrafficCode())) {
            criteria.eq("trafficCode", traffic.getTrafficCode());
        }
        if (StringUtils.isNotBlank(traffic.getTrafficModel())) {
            criteria.eq("trafficModel", traffic.getTrafficModel());
        }
        if (traffic.getLeaveTransportation() != null
                && traffic.getLeaveTransportation().getId() != null
                && traffic.getLeaveTransportation().getId() != 0) {
            criteria.eq("leaveTransportation.id", traffic.getLeaveTransportation().getId());
        }
        if (traffic.getArriveTransportation() != null
                && traffic.getArriveTransportation().getId() != null
                && traffic.getArriveTransportation().getId() != 0) {
            criteria.eq("arriveTransportation.id", traffic.getArriveTransportation().getId());
        }
        if (traffic.getTrafficType() != null) {
            criteria.eq("trafficType", traffic.getTrafficType());
        }
        if (traffic.getUser() != null) {
            criteria.eq("user.id", traffic.getUser().getId());
        }
        if (traffic.getLeaveCity() != null) {
            criteria.eq("leaveCity.id", traffic.getLeaveCity().getId());
        }
        if (traffic.getArriveCity() != null) {
            criteria.eq("arriveCity.id", traffic.getArriveCity().getId());
        }
        if (traffic.getSource() != null) {
            if (traffic.getSource() == ProductSource.LXB) {
                criteria.isNull("source");
            } else {
                criteria.eq("source", traffic.getSource());
            }
        }
        return criteria;
    }

    public List<PassBy> getPassBies(String leavePortName, String arrivePortName, String trafficCode, String date) {
        return trafficQunarTrainService.getPassBy(leavePortName, arrivePortName, trafficCode, date);
    }


    public void save(Traffic traffic) {
        trafficDao.save(traffic);
    }

    public void update(Traffic tempTraffic) {
        trafficDao.update(tempTraffic);
    }

    public void delTraffic(Traffic traffic) {
        trafficDao.delete(traffic);
        trafficPriceService.delByTraffic(traffic);
    }
}
