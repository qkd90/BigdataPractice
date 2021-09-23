package com.data.data.hmly.service.traffic;

import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.traffic.entity.Traffic;
import com.data.data.hmly.service.traffic.entity.TrafficPrice;
import com.data.data.hmly.service.traffic.entity.enums.TrafficType;
import com.data.data.hmly.service.transportation.TransportationService;
import com.data.data.hmly.service.transportation.entity.Transportation;
import com.data.hmly.service.translation.flight.juhe.JuheFlightService;
import com.data.hmly.service.translation.flight.juhe.entity.FlightPolicy;
import com.data.hmly.service.translation.flight.juhe.entity.FlightPolicy.ResultEntity;
import com.data.hmly.service.translation.flight.juhe.entity.FlightPolicy.ResultEntity.CabinInfosEntity;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.zuipin.util.GlobalTheadPool;
import com.zuipin.util.MD5;
import com.zuipin.util.PropertiesManager;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

/**
 * Created by Sane on 15/12/25.
 */
@Service
public class TrafficJuheFlightService {

    @Resource
    private TransportationService transportationService;
    @Resource
    private PropertiesManager propertiesManager;
    private static final Log LOG = LogFactory.getLog(TrafficJuheFlightService.class);


    public List<TrafficPrice> getTrafficPrices(Traffic traffic, Date date, String airline) {
        List<TrafficPrice> prices = new ArrayList<TrafficPrice>();
        String fromCity = traffic.getLeaveTransportation().getCode();
        String toCity = traffic.getArriveTransportation().getCode();
        String fightDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        String key = propertiesManager.getString("JUHE_FLIGHT_KEY");
        FlightPolicy policy = JuheFlightService.search(fromCity, toCity, fightDate, airline, key);
        if (policy == null)
            return null;
        for (FlightPolicy.ResultEntity result : policy.getResult()) {

            //需判断出发、到达的机场是否符合要求，有的城市如上海，查询的SHA的时候，会把PVG浦东一起返回
            if (result.getTakeoffPort().equals(fromCity) && result.getLandingPort().equals(toCity)) {
                Date leaveTime;
                Date arriveTime;
                try {
                    leaveTime = new SimpleDateFormat("yyyyMMddHHmmss").parse(result.getTakeoffTime());
                    arriveTime = new SimpleDateFormat("yyyyMMddHHmmss").parse(result.getLandingTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                    return null;
                }
                for (FlightPolicy.ResultEntity.CabinInfosEntity cabinInfo : result.getCabinInfos()) {
                    prices.add(getTrafficPrice(traffic, leaveTime, arriveTime, cabinInfo, result.getAdultAirportPrice(), result.getAdultFuelPrice()));
                }
            }

        }
        return prices;
    }

    private TrafficPrice getTrafficPrice(Traffic traffic, Date leaveTime, Date arriveTime, CabinInfosEntity cabinInfo, String adultAirportPrice, String fuelPrice) {
        TrafficPrice price = new TrafficPrice();
        price.setTraffic(traffic);
        price.setPrice(Float.parseFloat(cabinInfo.getFinallyPrice()));
        price.setSeatNum(cabinInfo.getSeating());
        price.setSeatCode(cabinInfo.getCabin());
        price.setSeatType(cabinInfo.getCabinGrade());
        price.setSeatName(cabinInfo.getCabinGrade());
        price.setChangePolicy(cabinInfo.getChangePolicy());
        price.setBackPolicy(cabinInfo.getBackPolicy());
        price.setLeaveTime(leaveTime);
        price.setArriveTime(arriveTime);
        price.setAirportBuildFee(Float.valueOf(adultAirportPrice));
        if (price.getAirportBuildFee() == null || price.getAirportBuildFee() == 0) {
            price.setAirportBuildFee(50f);
        }
        price.setAdditionalFuelTax(Float.valueOf(fuelPrice));
        price.setHashCode(String.valueOf(cabinInfo.hashCode()));
        return price;
    }

    public List<TrafficPrice> getTrafficPrices(final TbArea leaveCity, final TbArea arrivalCity, Date date, String airline) {
        List<TrafficPrice> prices = new ArrayList<TrafficPrice>();
        String fightDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        List<Transportation> leavePorts = transportationService.findAirportsByCity(leaveCity.getId().intValue());
        List<Transportation> arrivePorts = transportationService.findAirportsByCity(arrivalCity.getId().intValue());
        if (leavePorts == null || leavePorts.isEmpty() || arrivePorts == null
                || arrivePorts.isEmpty()) {
            return null;
        }
        String key = propertiesManager.getString("JUHE_FLIGHT_KEY");
        for (final Transportation leavePort : leavePorts) {
            for (final Transportation arrivePort : arrivePorts) {
                FlightPolicy policy = JuheFlightService.search(leavePort.getCode(), arrivePort.getCode(), fightDate, airline, key);
                if (policy == null)
                    return null;
                prices.addAll(getResults(leaveCity, arrivalCity, leavePort, arrivePort, policy));
            }
        }
        return prices;
    }

    private List<TrafficPrice> getResults(TbArea leaveCity, TbArea arrivalCity, Transportation leavePort, Transportation arrivePort, FlightPolicy policy) {
        List<ResultEntity> results = policy.getResult();
        List<TrafficPrice> prices = new ArrayList<TrafficPrice>();
        for (ResultEntity result : results) {
            if (result.getTakeoffPort().equals(leavePort) && result.getLandingPort().equals(arrivePort)) {
                Traffic traffic = getTraffic(result, leaveCity, leavePort, arrivalCity, arrivePort);
                prices.addAll(getFromCabins(result, traffic));
            }
        }
        return prices;
    }

    private List<TrafficPrice> getFromCabins(ResultEntity result, Traffic traffic) {
        List<TrafficPrice> prices = new ArrayList<TrafficPrice>();
        Date leaveTime;
        Date arriveTime;
        try {
            leaveTime = new SimpleDateFormat("yyyyMMddHHmmss").parse(result.getTakeoffTime());
            arriveTime = new SimpleDateFormat("yyyyMMddHHmmss").parse(result.getLandingTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return prices;
        }
        List<CabinInfosEntity> cabins = result.getCabinInfos();
        for (CabinInfosEntity cabinInfo : cabins) {
            prices.add(getTrafficPrice(traffic, leaveTime, arriveTime, cabinInfo, result.getAdultAirportPrice(), result.getAdultFuelPrice()));
        }
        return prices;
    }

    public List<Traffic> getTraffics(final TbArea leaveCity, final TbArea arrivalCity, Date date) {
        final List<Traffic> traffics = new ArrayList<Traffic>();
        List<Transportation> leaveTransportations = transportationService.findAirportsByCity(leaveCity.getId().intValue());
        List<Transportation> arriveTransportations = transportationService.findAirportsByCity(arrivalCity.getId().intValue());
        if (leaveTransportations == null || leaveTransportations.isEmpty() || arriveTransportations == null
                || arriveTransportations.isEmpty()) {
            return null;
        }
        final String fightDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        final String key = propertiesManager.getString("JUHE_FLIGHT_KEY");
        final CountDownLatch latch = new CountDownLatch(leaveTransportations.size() * arriveTransportations.size());
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
//        log.info(String.format("Start to Fetch Juhe %s", stopWatch));
        final List<Future<List<Traffic>>> collectResult = new ArrayList<Future<List<Traffic>>>();
        final String cacheKey = TrafficService.makeTrafficKey(leaveCity.getId(), arrivalCity.getId(), date);
        for (final Transportation leaveTransportation : leaveTransportations) {
            for (final Transportation arriveTransportation : arriveTransportations) {
                // 并发请求聚合
                collectResult.add(GlobalTheadPool.instance.submit(new Callable<List<Traffic>>() {
                    @Override
                    public List<Traffic> call() throws Exception {
                        return searchTraffic(leaveTransportation, arriveTransportation, fightDate, key, leaveCity, arrivalCity, traffics, latch);
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
            //超过10秒即返回数据，剩余数据  由另外一个线程去收集
            int sleep = 0;
            do {
                Thread.sleep(1000);
                LOG.info(String.format("Plan Current Count is %d sleep %d", latch.getCount(), sleep));
                sleep++;
            } while (latch.getCount() != 0 && sleep < 10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
        }
        stopWatch.stop();
        LOG.info(String.format("End Fetch ------------------------"));
        if (traffics != null && !traffics.isEmpty()) {
            traffics.removeAll(Collections.singleton(null));
            for (Traffic traffic : traffics) {
                if (traffic.getPrices() != null)
                    for (TrafficPrice price : traffic.getPrices()) {
                        TrafficService.PLAN_TRAFFIC_PRICES.putIfAbsent(price.getHashCode(), price);
                    }
            }
            TrafficService.PLAN_TRAFFIC.putIfAbsent(cacheKey, traffics);
        }
        return traffics;
    }

    private Object collectAllTraffic(CountDownLatch latch, List<Future<List<Traffic>>> collectResult, String cacheKey, List<Traffic> traffics) throws InterruptedException, java.util.concurrent.ExecutionException {
        latch.await();
        LOG.info(String.format("++++++++++++++++++++Finish Collecting %s", cacheKey));
        List<Traffic> trafficList = new ArrayList<Traffic>();
        for (Future<List<Traffic>> future : collectResult) {
            trafficList.addAll(future.get());
        }
        if (trafficList != null && !trafficList.isEmpty()) {
            for (Traffic traffic : traffics) {
                if (traffic.getPrices() != null)
                    for (TrafficPrice price : traffic.getPrices()) {
                        TrafficService.PLAN_TRAFFIC_PRICES.putIfAbsent(price.getHashCode(), price);
                    }
            }
            TrafficService.PLAN_TRAFFIC.put(cacheKey, traffics);
        }
        return null;
    }

    private List<Traffic> searchTraffic(Transportation leaveTransportation, Transportation arriveTransportation, String fightDate, String key, TbArea leaveCity, TbArea arrivalCity, List<Traffic> traffics, CountDownLatch latch) {
        try {
            LOG.info("START -------------------------");
            List<Traffic> localTraffic = new ArrayList<Traffic>();
            FlightPolicy policy = JuheFlightService.search(leaveTransportation.getCode(), arriveTransportation.getCode(), fightDate, "", key);
            if (policy != null && policy.getResult() != null) {
                localTraffic.addAll(getTransform(policy, leaveCity, leaveTransportation, arrivalCity, arriveTransportation));
                for (Traffic traffic : localTraffic) {
                    if (traffic != null) {
                        traffics.add(traffic);
                    }
                }
//                traffics.addAll(localTraffic);
//                traffics.remove(null);
            }
            LOG.info("END -------------------------");
            return localTraffic;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            latch.countDown();
        }
        return null;
    }

    private List<Traffic> getTransform(FlightPolicy policy, final TbArea leaveCity, final Transportation leaveTransportation, final TbArea arrivalCity, final Transportation arriveTransportation) {
        return Lists.transform(policy.getResult(), new Function<ResultEntity, Traffic>() {
            public Traffic apply(ResultEntity result) {
                if (result.getTakeoffPort().equals(leaveTransportation.getCode()) && result.getLandingPort().equals(arriveTransportation.getCode())) {
                    return getTraffic(result, leaveCity, leaveTransportation, arrivalCity, arriveTransportation);
                }
                return null;
            }
        });
    }

    private Traffic getTraffic(FlightPolicy.ResultEntity result, TbArea leaveCity, Transportation leaveTransportation, TbArea arrivalCity, Transportation arriveTransportation) {
        Traffic traffic = new Traffic();
        traffic.setTrafficType(TrafficType.AIRPLANE);
        traffic.setLeaveCity(leaveCity);
        traffic.setLeaveTransportation(leaveTransportation);
        traffic.setLeaveTime(result.getTakeoffTime().substring(8, 10) + ":" + result.getTakeoffTime().substring(10, 12));
        traffic.setArriveCity(arrivalCity);
        traffic.setArriveTransportation(arriveTransportation);
        traffic.setArriveTime(result.getLandingTime().substring(8, 10) + ":" + result.getLandingTime().substring(10, 12));
        traffic.setCompany(result.getAirlineName());
        traffic.setTrafficModel(result.getPlaneTypeDesc());
        traffic.setTrafficCode(result.getFlightNum());
        traffic.setCompanyCode(result.getAirline());
        List<TrafficPrice> prices = getFromCabins(result, traffic);
        traffic.setPrices(prices);
        traffic.setStatus(ProductStatus.UP);
        traffic.setHashCode(MD5.MD5Encode(new Gson().toJson(result)));
        try {
            Date leaveTime = new SimpleDateFormat("yyyyMMddHHmmss").parse(result.getTakeoffTime());
            Date arriveTime = new SimpleDateFormat("yyyyMMddHHmmss").parse(result.getLandingTime());
            traffic.setFlightTime((arriveTime.getTime() - leaveTime.getTime()) / 60000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return traffic;
    }
}
