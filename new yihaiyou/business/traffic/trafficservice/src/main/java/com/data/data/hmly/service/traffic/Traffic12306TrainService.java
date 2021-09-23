package com.data.data.hmly.service.traffic;

import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.traffic.entity.Traffic;
import com.data.data.hmly.service.traffic.entity.TrafficPrice;
import com.data.data.hmly.service.traffic.entity.enums.TrafficType;
import com.data.data.hmly.service.transportation.TransportationService;
import com.data.data.hmly.service.transportation.entity.Transportation;
import com.data.hmly.service.translation.train.Kyfw12306.Kyfw12306TrainService;
import com.data.hmly.service.translation.train.Kyfw12306.entity.LeftTickets;
import com.google.gson.Gson;
import com.zuipin.util.GlobalTheadPool;
import com.zuipin.util.MD5;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

/**
 * Created by Sane on 15/12/25.
 */
@Service
public class Traffic12306TrainService {

    @Resource
    private TransportationService transportationService;

    private static Map<String, String> seatTypeMap = new HashMap<String, String>();

    static {
        seatTypeMap.put("0", "棚车");
        seatTypeMap.put("1", "硬座");
        seatTypeMap.put("2", "软座");
        seatTypeMap.put("3", "硬卧");
        seatTypeMap.put("4", "软卧");
        seatTypeMap.put("5", "包厢硬卧");
        seatTypeMap.put("6", "高级软卧");
        seatTypeMap.put("7", "一等软座");
        seatTypeMap.put("8", "二等软座");
        seatTypeMap.put("9", "商务座");
        seatTypeMap.put("A", "高级动卧");
        seatTypeMap.put("B", "混编硬座");
        seatTypeMap.put("C", "混编硬卧");
        seatTypeMap.put("D", "包厢软座");
        seatTypeMap.put("E", "特等软座");
        seatTypeMap.put("F", "动卧");
        seatTypeMap.put("G", "二人软包");
        seatTypeMap.put("H", "一人软包");
        seatTypeMap.put("I", "一等双软");
        seatTypeMap.put("J", "二等双软");
        seatTypeMap.put("K", "混编软座");
        seatTypeMap.put("L", "混编软卧");
        seatTypeMap.put("M", "一等座");
        seatTypeMap.put("O", "二等座");
        seatTypeMap.put("P", "特等座");
        seatTypeMap.put("Q", "观光座");
        seatTypeMap.put("S", "一等包座");
    }

    private static final Log LOG = LogFactory.getLog(Traffic12306TrainService.class);

    public List<Traffic> getTraffics(final TbArea leaveCity, final TbArea arrivalCity, Date date) {
        final List<Traffic> traffics = new ArrayList<Traffic>();
        final List<Transportation> leaveTransportations = transportationService.findTrainStationByCity(leaveCity.getId().intValue());
        final List<Transportation> arriveTransportations = transportationService.findTrainStationByCity(arrivalCity.getId().intValue());
        if (leaveTransportations == null || leaveTransportations.isEmpty() || arriveTransportations == null
                || arriveTransportations.isEmpty()) {
            return null;
        }
        final String fightDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        final CountDownLatch latch = new CountDownLatch(leaveTransportations.size() * arriveTransportations.size());
        final List<Future<List<Traffic>>> collectResult = new ArrayList<Future<List<Traffic>>>();
        final String cacheKey = TrafficService.makeTrafficKey(leaveCity.getId(), arrivalCity.getId(), date);
        for (final Transportation leaveTransportation : leaveTransportations) {
            for (final Transportation arriveTransportation : arriveTransportations) {
                collectResult.add(GlobalTheadPool.instance.submit(new Callable<List<Traffic>>() {
                    @Override
                    public List<Traffic> call() throws Exception {
                        return searchTickets(fightDate, leaveTransportation, arriveTransportation, leaveCity, arrivalCity, traffics, latch);
                    }
                }));
            }
        }
        GlobalTheadPool.instance.submit(new Callable<Object>() {

            @Override
            public Object call() throws Exception {
                return collectAllTraffic(latch, collectResult, cacheKey, traffics);
            }
        });
        try {
            int sleep = 0;
            do {
                Thread.sleep(1000);
                sleep++;
                LOG.info(String.format("latch Current Count is %d sleep %d", latch.getCount(), sleep));
            } while (latch.getCount() != 0 && sleep < 10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
        }
        LOG.info(String.format("End Fetch ------------------------"));
        if (traffics != null && !traffics.isEmpty()) {
            for (Traffic traffic : traffics) {
                if (traffic != null && traffic.getPrices() != null)
                    for (TrafficPrice price : traffic.getPrices()) {
                        TrafficService.TRAIN_TRAFFIC_PRICES.putIfAbsent(price.getHashCode(), price);
                    }
            }
            TrafficService.TRAIN_TRAFFIC.putIfAbsent(cacheKey, traffics);
        }
        return traffics;
    }

    /**
     * 所有线程执行完毕，收集所有请求的结果
     *
     * @param latch
     * @param collectResult
     * @param cacheKey
     * @param traffics
     * @return
     * @throws InterruptedException
     * @throws java.util.concurrent.ExecutionException
     */
    private Object collectAllTraffic(CountDownLatch latch, List<Future<List<Traffic>>> collectResult, String cacheKey, List<Traffic> traffics) throws InterruptedException, java.util.concurrent.ExecutionException {
        latch.await();
        LOG.info(String.format("++++++++++++++++++++Finish Collecting %s", cacheKey));
        List<Traffic> tickets = new ArrayList<Traffic>();
        for (Future<List<Traffic>> future : collectResult) {
            tickets.addAll(future.get());
        }
        if (tickets != null && !tickets.isEmpty()) {
            for (Traffic traffic : traffics) {
                if (traffic.getPrices() != null)
                    for (TrafficPrice price : traffic.getPrices()) {
                        TrafficService.TRAIN_TRAFFIC_PRICES.putIfAbsent(price.getHashCode(), price);
                    }
            }
            TrafficService.TRAIN_TRAFFIC.put(cacheKey, traffics);
        }
        return null;
    }

    private List<Traffic> searchTickets(String fightDate, Transportation leaveTransportation, Transportation arriveTransportation, TbArea leaveCity, TbArea arrivalCity, List<Traffic> traffics, CountDownLatch latch) {
        try {
            LOG.info("START -------------------------");
            final List<LeftTickets.DataEntity> tickets = Kyfw12306TrainService.searchTickets(fightDate, leaveTransportation.getCode(), arriveTransportation.getCode());
//            LOG.info(leaveTransportation.getName() + "-" + arriveTransportation.getName() + " tickets length = " + tickets.size() + " -------------------------");
            List<Traffic> localTraffic = new ArrayList<Traffic>();
            if (tickets != null) {
                localTraffic.addAll(getTransform(fightDate, leaveCity, arrivalCity, leaveTransportation, arriveTransportation, tickets));
                localTraffic.remove(null);
//                if(price.getArriveTime().getTime() > date.getTime()){
                traffics.addAll(localTraffic);
            }
            LOG.info("END traffics length = " + traffics.size() + "-------------------------");
            return localTraffic;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            latch.countDown();
        }
        return null;
    }

    private List<Traffic> getTransform(String fightDate, final TbArea leaveCity, final TbArea arrivalCity, final Transportation leaveTransportation, final Transportation arriveTransportation, List<LeftTickets.DataEntity> tickets) {
        List<Traffic> traffics = new ArrayList<Traffic>();
        for (LeftTickets.DataEntity result : tickets) {
            if (leaveTransportation.getCode().equals(result.getQueryLeftNewDTO().getFrom_station_telecode()) && arriveTransportation.getCode().equals(result.getQueryLeftNewDTO().getTo_station_telecode())) {
                Traffic traffic = getTraffic(fightDate, result, leaveCity, leaveTransportation, arrivalCity, arriveTransportation);
                if (!traffic.getPrices().isEmpty()) {
                    traffics.add(traffic);
                }
            }
        }
        return traffics;
    }

    private Traffic getTraffic(String fightDate, LeftTickets.DataEntity ticket, TbArea leaveCity, Transportation leaveTransportation, TbArea arrivalCity, Transportation arriveTransportation) {

        Traffic traffic = new Traffic();
        traffic.setTrafficType(TrafficType.TRAIN);
        traffic.setLeaveCity(leaveCity);
        traffic.setLeaveTransportation(leaveTransportation);
        traffic.setLeaveTime(ticket.getQueryLeftNewDTO().getStart_time());
        traffic.setArriveCity(arrivalCity);
        traffic.setArriveTransportation(arriveTransportation);
        traffic.setArriveTime(ticket.getQueryLeftNewDTO().getArrive_time());
        traffic.setCompany(null);
        traffic.setTrafficModel(ticket.getQueryLeftNewDTO().getStation_train_code());
        traffic.setTrafficCode(ticket.getQueryLeftNewDTO().getStation_train_code());
        traffic.setStartPort(ticket.getQueryLeftNewDTO().getStart_station_name());
        traffic.setEndPort(ticket.getQueryLeftNewDTO().getEnd_station_name());
        List<TrafficPrice> prices = getFromTicket(fightDate, ticket, traffic);
        traffic.setPrices(prices);
        traffic.setFlightTime(Long.parseLong(ticket.getQueryLeftNewDTO().getLishiValue()));
        traffic.setHashCode(MD5.MD5Encode(new Gson().toJson(ticket)));
        return traffic;
    }

    private List<TrafficPrice> getFromTicket(String fightDate, LeftTickets.DataEntity ticket, Traffic traffic) {
        List<TrafficPrice> prices = new ArrayList<TrafficPrice>();
        Date leaveTime = new Date();
        Date arriveTime = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm");
        try {
//            Calendar c = Calendar.getInstance();
//            c.setTime(DateUtils.parse(fightDate + " "+ ticket.getQueryLeftNewDTO().getStart_time(),"yyyy-MM-dd HH:mm"));
            leaveTime = DateUtils.parse(fightDate + " " + ticket.getQueryLeftNewDTO().getStart_time(), "yyyy-MM-dd HH:mm");
            arriveTime.setTime(leaveTime.getTime() + Long.parseLong(ticket.getQueryLeftNewDTO().getLishiValue()) * 60000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            if (DateUtils.parse(fightDate, "yyyy-MM-dd").getTime() > arriveTime.getTime()) {
//                System.out.print("Error Arriave Day fightDate" + fightDate + "  arriveTime " + DateUtils.formatDate(arriveTime));
                return Collections.emptyList();
            }
        } catch (ParseException e) {

        }

        String ypInfo = ticket.getQueryLeftNewDTO().getYp_info();
        if (ypInfo.length() > 0) {
            int arrayLength = ypInfo.length() / 10;
            for (int i = 0, m = 6, n = 10, x = 0, y = 1; i < arrayLength; i++, m = m + 10, n = n + 10, x = x + 10, y = y + 10) {
                String seatTypeId = ypInfo.substring(x, y);
                String seatType;
                int seatNum;
                if (Integer.parseInt(ypInfo.substring(m, m + 1), 10) >= 3) {
                    seatType = "无座";
                    seatNum = Integer.parseInt(ypInfo.substring(m, n), 10) - 3000;
                } else {
                    seatType = seatTypeMap.get(seatTypeId);
                    seatNum = Integer.parseInt(ypInfo.substring(m, n), 10);
                }
                Float p = Float.parseFloat(ypInfo.substring(y, m)) / 10;
                if (p == 0) {
                    continue;
                }
                TrafficPrice price = new TrafficPrice(seatTypeId, seatType, arriveTime, leaveTime, p, String.
                        valueOf(seatNum), seatType, traffic);
                price.setHashCode(String.valueOf(price.hashCode()));
                prices.add(price);
            }
        }

        return prices;
    }

}
