package com.data.data.hmly.service.traffic;

import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.traffic.entity.Traffic;
import com.data.data.hmly.service.traffic.entity.TrafficPrice;
import com.data.data.hmly.service.traffic.entity.enums.TrafficType;
import com.data.data.hmly.service.transportation.TransportationService;
import com.data.data.hmly.service.transportation.entity.Transportation;
import com.data.hmly.service.translation.train.juhe.JuheTrainService;
import com.data.hmly.service.translation.train.juhe.entity.TicketsAvailable.ResultEntity.Ticket;
import com.google.gson.Gson;
import com.zuipin.util.GlobalTheadPool;
import com.zuipin.util.MD5;
import com.zuipin.util.PropertiesManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

/**
 * Created by Sane on 15/12/25.
 */
@Service
public class TrafficJuheTrainService {

    @Resource
    private TransportationService transportationService;
    @Resource
    private PropertiesManager propertiesManager;
    private static final Log LOG = LogFactory.getLog(TrafficJuheTrainService.class);

    public List<Traffic> getTraffics(final TbArea leaveCity, final TbArea arrivalCity, Date date) {
        final List<Traffic> traffics = new ArrayList<Traffic>();
        final List<Transportation> leaveTransportations = transportationService.findTrainStationByCity(leaveCity.getId().intValue());
        final List<Transportation> arriveTransportations = transportationService.findTrainStationByCity(arrivalCity.getId().intValue());
        if (leaveTransportations == null || leaveTransportations.isEmpty() || arriveTransportations == null
                || arriveTransportations.isEmpty()) {
            return null;
        }
        final String fightDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        final String key = propertiesManager.getString("JUHE_TRAIN_KEY");
        final CountDownLatch latch = new CountDownLatch(leaveTransportations.size() * arriveTransportations.size());
        final List<Future<List<Traffic>>> collectResult = new ArrayList<Future<List<Traffic>>>();
        final String cacheKey = TrafficService.makeTrafficKey(leaveCity.getId(), arrivalCity.getId(), date);
        for (final Transportation leaveTransportation : leaveTransportations) {
            for (final Transportation arriveTransportation : arriveTransportations) {
//                final String fromCity = leaveTransportation.getCode();
//                final String toCity = arriveTransportation.getCode();
//                List<Ticket> tickets = JuheTrainService.searchTickets(fightDate, fromCity, toCity, key);
//                if (tickets == null)
//                    continue;
//                traffics.addAll(getTransform(leaveCity, arrivalCity, leaveTransportation, arriveTransportation, tickets));
                collectResult.add(GlobalTheadPool.instance.submit(new Callable<List<Traffic>>() {
                    @Override
                    public List<Traffic> call() throws Exception {
                        return searchTickets(fightDate, leaveTransportation, arriveTransportation, key, leaveCity, arrivalCity, traffics, latch);
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
                LOG.info(String.format("Plan Current Count is %d sleep %d", latch.getCount(), sleep));
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

    private List<Traffic> searchTickets(String fightDate, Transportation leaveTransportation, Transportation arriveTransportation, String key, TbArea leaveCity, TbArea arrivalCity, List<Traffic> traffics, CountDownLatch latch) {
        try {
            LOG.info("START -------------------------");
            final List<Ticket> tickets = JuheTrainService.searchTickets(fightDate, leaveTransportation.getCode(), arriveTransportation.getCode(), key);
            List<Traffic> localTraffic = new ArrayList<Traffic>();
            if (tickets != null) {
                localTraffic.addAll(getTransform(leaveCity, arrivalCity, leaveTransportation, arriveTransportation, tickets));
                localTraffic.remove(null);
                traffics.addAll(localTraffic);
            }
//            int i = 0;
//            do {
//                LOG.info(String.format("DOING MESSAGE %d -------------------------", i));
//                Thread.sleep(1000);
//            } while (i++ < 20);
            LOG.info("END -------------------------");
            return localTraffic;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            latch.countDown();
        }
        return null;
    }

    private List<Traffic> getTransform(final TbArea leaveCity, final TbArea arrivalCity, final Transportation leaveTransportation, final Transportation arriveTransportation, List<Ticket> tickets) {
//        return Lists.transform(tickets, new Function<Ticket, Traffic>() {
//            public Traffic apply(Ticket result) {
//                if (!leaveTransportation.getCode().endsWith(result.getFrom_station_code()) || !arriveTransportation.getCode().endsWith(result.getTo_station_code())) {
//                    return getTraffic(result, leaveCity, leaveTransportation, arrivalCity, arriveTransportation);
//                }
//                return null;
//            }
//        });
        List<Traffic> traffics = new ArrayList<Traffic>();
        for (Ticket result : tickets) {
            if (leaveTransportation.getCode().equals(result.getFrom_station_code()) && arriveTransportation.getCode().equals(result.getTo_station_code())) {
                traffics.add(getTraffic(result, leaveCity, leaveTransportation, arrivalCity, arriveTransportation));
            }
        }
        return traffics;
    }

    private Traffic getTraffic(Ticket ticket, TbArea leaveCity, Transportation leaveTransportation, TbArea arrivalCity, Transportation arriveTransportation) {

        Traffic traffic = new Traffic();
        traffic.setTrafficType(TrafficType.TRAIN);
        traffic.setLeaveCity(leaveCity);
        traffic.setLeaveTransportation(leaveTransportation);
        traffic.setLeaveTime(ticket.getStart_time());
        traffic.setArriveCity(arrivalCity);
        traffic.setArriveTransportation(arriveTransportation);
        traffic.setArriveTime(ticket.getArrive_time());
        traffic.setCompany(null);
        traffic.setTrafficModel(ticket.getTrain_no());
        traffic.setTrafficCode(ticket.getTrain_code());
        traffic.setStartPort(ticket.getStart_station_name());
        traffic.setEndPort(ticket.getEnd_station_name());
        List<TrafficPrice> prices = getFromTicket(ticket, traffic);
        traffic.setPrices(prices);
        traffic.setFlightTime(Long.parseLong(ticket.getRun_time_minute()));
        traffic.setHashCode(MD5.MD5Encode(new Gson().toJson(ticket)));
        return traffic;
    }

    private List<TrafficPrice> getFromTicket(Ticket ticket, Traffic traffic) {
        List<TrafficPrice> prices = new ArrayList<TrafficPrice>();
        Date leaveTime = new Date();
        Date arriveTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm");
        try {
            leaveTime = sdf.parse(ticket.getTrain_start_date() + " " + ticket.getStart_time());
            arriveTime.setTime(leaveTime.getTime() + Long.parseLong(ticket.getRun_time_minute()) * 60000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (ticket.getRw_price() != 0 && !"--".equals(ticket.getRw_num()))
            prices.add(getRw(ticket, traffic, leaveTime, arriveTime));
        if (ticket.getYw_price() != 0 && !"--".equals(ticket.getYw_num()))
            prices.add(getYw(ticket, traffic, leaveTime, arriveTime));
        if (ticket.getTdz_price() != 0 && !"--".equals(ticket.getTdz_num()))
            prices.add(getTdz(ticket, traffic, leaveTime, arriveTime));
        if (ticket.getSwz_price() != 0 && !"--".equals(ticket.getSwz_num()))
            prices.add(getSwz(ticket, traffic, leaveTime, arriveTime));
        if (ticket.getRz_price() != 0 && !"--".equals(ticket.getRz_num()))
            prices.add(getRz(ticket, traffic, leaveTime, arriveTime));
        if (ticket.getYdz_price() != 0 && !"--".equals(ticket.getYdz_num()))
            prices.add(getYdz(ticket, traffic, leaveTime, arriveTime));
        if (ticket.getEdz_price() != 0 && !"--".equals(ticket.getEdz_num()))
            prices.add(getEdz(ticket, traffic, leaveTime, arriveTime));
        if (ticket.getYz_price() != 0 && !"--".equals(ticket.getYz_num()))
            prices.add(getYz(ticket, traffic, leaveTime, arriveTime));
        if (ticket.getWz_price() != 0 && !"--".equals(ticket.getWz_num()))
            prices.add(getWz(ticket, traffic, leaveTime, arriveTime));
        return prices;
    }

    private TrafficPrice setPrice(TrafficPrice price, double p, String num, String name) {
        price.setPrice((float) p);
        price.setSeatNum(num);
        price.setSeatName(name);
        price.setSeatCode(getZwCode(name));
        price.setHashCode(String.valueOf(price.hashCode()));
        return price;
    }

    private String getZwCode(String name) {
        switch (name) {
            case "商务座":
                return "9";
            case "特等座":
                return "P";
            case "一等座":
                return "M";
            case "二等座":
                return "O";
            case "高级软卧":
                return "6";
            case "软卧":
                return "4";
            case "硬卧":
                return "3";
            case "软座":
                return "2";
            case "硬座":
                return "1";
            default:
                return null;
        }
    }

//    //高级软卧
//    private TrafficPrice getGjrw(Ticket ticket, Traffic traffic, Date leaveTime, Date arriveTime) {
//        if (ticket.getRz_price() == 0 || "--".equals(ticket.getRz_num()) || "0".equals(ticket.getRz_num()))
//            return null;
//        TrafficPrice price = new TrafficPrice(traffic, leaveTime, arriveTime);
//        price = setPrice(price, ticket.getGjrw_price(), ticket.getGjrw_num(), "高级软卧");
//        return price;
//    }

    //软卧
    private TrafficPrice getRw(Ticket ticket, Traffic traffic, Date leaveTime, Date arriveTime) {
        TrafficPrice price = new TrafficPrice(traffic, leaveTime, arriveTime);
        price = setPrice(price, ticket.getRw_price(), ticket.getRw_num(), "软卧");
        return price;
    }

    //硬卧
    private TrafficPrice getYw(Ticket ticket, Traffic traffic, Date leaveTime, Date arriveTime) {
        TrafficPrice price = new TrafficPrice(traffic, leaveTime, arriveTime);
        price = setPrice(price, ticket.getYw_price(), ticket.getYw_num(), "硬卧");
        return price;
    }

    //特等座
    private TrafficPrice getTdz(Ticket ticket, Traffic traffic, Date leaveTime, Date arriveTime) {
        TrafficPrice price = new TrafficPrice(traffic, leaveTime, arriveTime);
        price = setPrice(price, ticket.getTdz_price(), ticket.getTdz_num(), "特等座");
        return price;
    }


    //商务座
    private TrafficPrice getSwz(Ticket ticket, Traffic traffic, Date leaveTime, Date arriveTime) {
        TrafficPrice price = new TrafficPrice(traffic, leaveTime, arriveTime);
        price = setPrice(price, ticket.getSwz_price(), ticket.getSwz_num(), "商务座");
        return price;
    }


    //软座
    private TrafficPrice getRz(Ticket ticket, Traffic traffic, Date leaveTime, Date arriveTime) {
        TrafficPrice price = new TrafficPrice(traffic, leaveTime, arriveTime);
        price = setPrice(price, ticket.getRz_price(), ticket.getRz_num(), "软座");
        return price;
    }

    //一等座
    private TrafficPrice getYdz(Ticket ticket, Traffic traffic, Date leaveTime, Date arriveTime) {
        TrafficPrice price = new TrafficPrice(traffic, leaveTime, arriveTime);
        price = setPrice(price, ticket.getYdz_price(), ticket.getYdz_num(), "一等座");
        return price;
    }

    //二等座
    private TrafficPrice getEdz(Ticket ticket, Traffic traffic, Date leaveTime, Date arriveTime) {
        TrafficPrice price = new TrafficPrice(traffic, leaveTime, arriveTime);
        price = setPrice(price, ticket.getEdz_price(), ticket.getEdz_num(), "二等座");
        return price;
    }

    //硬座
    private TrafficPrice getYz(Ticket ticket, Traffic traffic, Date leaveTime, Date arriveTime) {
        TrafficPrice price = new TrafficPrice(traffic, leaveTime, arriveTime);
        price = setPrice(price, ticket.getYz_price(), ticket.getYz_num(), "硬座");
        return price;
    }

    //无座
    private TrafficPrice getWz(Ticket ticket, Traffic traffic, Date leaveTime, Date arriveTime) {
        TrafficPrice price = new TrafficPrice(traffic, leaveTime, arriveTime);
        if (traffic.getTrafficCode().startsWith("K")) {
            price = setPrice(price, ticket.getWz_price(), ticket.getWz_num(), "硬座");
        } else if (traffic.getTrafficCode().startsWith("G") || traffic.getTrafficCode().startsWith("D")) {
            price = setPrice(price, ticket.getWz_price(), ticket.getWz_num(), "二等座");
        }
        return price;
    }
}
