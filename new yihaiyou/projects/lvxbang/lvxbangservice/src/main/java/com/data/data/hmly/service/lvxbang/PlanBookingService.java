package com.data.data.hmly.service.lvxbang;

import com.data.data.hmly.service.area.AreaService;
import com.data.data.hmly.service.common.entity.enums.ProductSource;
import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.hotel.HotelPriceService;
import com.data.data.hmly.service.hotel.HotelService;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.hotel.entity.HotelPrice;
import com.data.data.hmly.service.hotel.vo.HotelRoomSolrEntity;
import com.data.data.hmly.service.hotel.vo.HotelSolrEntity;
import com.data.data.hmly.service.lvxbang.request.PlanBookingRequest;
import com.data.data.hmly.service.lvxbang.response.BookingResponse;
import com.data.data.hmly.service.lvxbang.response.HotelBookingResponse;
import com.data.data.hmly.service.lvxbang.response.PlanBookingResponse;
import com.data.data.hmly.service.lvxbang.response.TrafficBookingResponse;
import com.data.data.hmly.service.plan.PlanService;
import com.data.data.hmly.service.plan.entity.Plan;
import com.data.data.hmly.service.plan.entity.PlanDay;
import com.data.data.hmly.service.plan.entity.PlanTrip;
import com.data.data.hmly.service.scenic.ScenicGeoInfoService;
import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.entity.ScenicGeoinfo;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.data.hmly.service.traffic.TrafficPriceService;
import com.data.data.hmly.service.traffic.TrafficService;
import com.data.data.hmly.service.traffic.entity.Traffic;
import com.data.data.hmly.service.traffic.entity.TrafficPrice;
import com.data.data.hmly.service.traffic.entity.enums.TrafficType;
import com.data.data.hmly.service.transportation.entity.Transportation;
import com.data.data.hmly.util.Clock;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zuipin.util.ConcurrentUtil;
import com.zuipin.util.GlobalTheadPool;
import com.zuipin.util.SpringContextHolder;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

//import com.data.data.hmly.service.outOrder.OutOrderDispatchService;

/**
 * @author Jonathan.Guo
 */
@Service
public class PlanBookingService {

    @Resource
    private AreaService areaService;
    @Resource
    private HotelPriceService hotelPriceService;
    //    @Resource
//    private HotelRoomIndexService hotelRoomIndexService;
    @Resource
    private PlanService planService;
    @Resource
    private ScenicInfoService scenicInfoService;
    @Resource
    private TrafficService trafficService;
    @Resource
    private TrafficPriceService trafficPriceService;
    @Resource
    private ScenicGeoInfoService scenicGeoInfoService;
    @Resource
    private HotelService hotelService;
//    @Resource
//    private OutOrderDispatchService outOrderDispatchService;
//    @Resource
//    private HotelElongService hotelElongService;
//    @Resource
//    private HotelRoomIndexService hotelRoomIndexService;
    private static final Log LOG = LogFactory.getLog(PlanBookingService.class);

//    public List<PlanBookingResponse> doBook(Long planId, String ip, Map<Long, Map<String, String>> changedTraffic, Long changedCity, Map<Long, TrafficType> changedTrafficType, Date time) {
//        Plan plan = planService.get(planId);
//        return doBook(plan, ip, changedTraffic, changedCity, changedTrafficType, time);
//    }

    public List<PlanBookingResponse> doBook(Plan plan, Map<Long, Map<String, String>> changedTraffic, Long changedCity, Map<Long, TrafficType> changedTrafficType, Date time) {
        Map<Long, PlanBookingResponse> responseMap = Maps.newHashMap();
        Map<Long, Integer> coreScenicRank = Maps.newHashMap();
        Calendar calendar = Calendar.getInstance();
        if (time != null) {
            calendar.setTime(time);
        } else {
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        if (changedCity != null) {
            TbArea tbArea = areaService.get(changedCity);
            plan.setStartCity(tbArea);
        }
        for (PlanDay planDay : plan.getPlanDayList()) {
            createResponseByCity(responseMap, coreScenicRank, calendar, planDay);
        }
        TbArea startCity = plan.getStartCity();
//        if (startCity == null) {
//            startCity = getLocalCity(plan, ip);
//        }
        List<PlanBookingResponse> responseList = Lists.newArrayList(responseMap.values());
        resortCity(responseList);
        int num = 0;
        for (PlanBookingResponse response : responseList) {
            response.setTrafficType(changedTrafficType.get(response.getCityId()));
            num += response.getTrafficType() == TrafficType.ALL ? 3 : 2;
        }
        //每到一个城市，需要查询交通和酒店， 最后一次需要回来，只需要交通
        CountDownLatch down = new CountDownLatch(num + (changedTrafficType.get(responseList.get(responseList.size() - 1).getCityId()) == TrafficType.ALL ? 2 : 1));
        for (int i = 0; i < responseList.size(); i++) {
            PlanBookingResponse response = responseList.get(i);
            if (i == 0) {
                response.withFromCityId(startCity.getId()).withFromCityName(startCity.getName());
            } else {
                PlanBookingResponse preCity = responseList.get(i - 1);
                response.withFromCityId(preCity.getCityId()).withFromCityName(preCity.getCityName());
            }
//            changeCity(response, changedCity);
            Map<String, Boolean> fillResult = fillChangedTraffic(response, changedTraffic, response.getFromCityId(), down, false);
            findTrafficAndHotel(response, down, fillResult);
        }
        PlanBookingResponse lastDay = responseList.get(responseList.size() - 1);
        Map<String, Boolean> fillResult = fillChangedTraffic(lastDay, changedTraffic, lastDay.getCityId(), down, true);
        if (!fillResult.get(TrafficType.AIRPLANE.toString()) && lastDay.getTrafficType() != TrafficType.TRAIN) {
            doFillPlane(lastDay, lastDay.getCityId(), startCity.getId(), lastDay.getToDate(), down);
        }
        if (!fillResult.get(TrafficType.TRAIN.toString()) && lastDay.getTrafficType() != TrafficType.AIRPLANE) {
            doFillTrain(lastDay, lastDay.getCityId(), startCity.getId(), lastDay.getToDate(), down);
        }

        try {
            down.await();
        } catch (InterruptedException e) {
            LOG.error(e.getMessage(), e);
        }
        resortTraffic(responseList);
        return responseList;
    }

    private void createResponseByCity(Map<Long, PlanBookingResponse> responseMap, Map<Long, Integer> coreScenicRank, Calendar calendar, PlanDay planDay) {
        PlanBookingResponse response = responseMap.get(planDay.getCity().getId());
        if (response == null) {
            response = new PlanBookingResponse();
            response.withCityId(planDay.getCity().getId())
                    .withCityName(planDay.getCity().getName())
                    .withFromDate(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, planDay.getCity().getCount() + 1);
            response.withToDate(calendar.getTime()).addDays(planDay.getCity().getCount());
        }
        List<String> scenicList = Lists.newArrayList();
        for (PlanTrip planTrip : planDay.getPlanTripList()) {
            if (planTrip.getTripType() != 1) {
                continue;
            }
            scenicList.add(planTrip.getScenicInfo().getName());
            if (response.getCoreScenic() == null) {
                response.setCoreScenic(planTrip.getScenicInfo().getId());
                coreScenicRank.put(planTrip.getScenicInfo().getId(), planTrip.getScenicInfo().getRanking());
                continue;
            }
            int coreRanking = coreScenicRank.get(response.getCoreScenic());
            if (coreRanking > planTrip.getScenicInfo().getRanking()) {
                response.setCoreScenic(planTrip.getScenicInfo().getId());
                coreScenicRank.put(planTrip.getScenicInfo().getId(), planTrip.getScenicInfo().getRanking());
            }
        }
        response.addTripLine(scenicList);
        responseMap.put(planDay.getCity().getId(), response);
    }

//    private void changeCity(PlanBookingResponse response, Map<Long, Long> changedCity) {
//        Long changedCityId = changedCity.get(response.getFromCityId());
//        if (changedCityId != null) {
//            TbArea city = areaService.get(changedCityId);
//            response.withFromCityId(changedCityId).withFromCityName(city.getName());
//        }
//    }

    private Map<String, Boolean> fillChangedTraffic(PlanBookingResponse response, Map<Long, Map<String, String>> changedTraffic, Long cityId, CountDownLatch down, Boolean isReturn) {
        Map<String, Boolean> result = Maps.newHashMap();
        result.put(TrafficType.AIRPLANE.toString(), false);
        result.put(TrafficType.TRAIN.toString(), false);
        result.put("HOTEL", false);
        if (changedTraffic == null) {
            return result;
        }
        Map<String, String> changedCityTraffic = changedTraffic.get(cityId);
        Map<String, String> changedCityHotel = changedTraffic.get(response.getCityId());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Long responseFromCity = response.getFromCityId();
        Long responseToCity = response.getCityId();
        String responseFromDate = format.format(response.getFromDate());
        String responseToDate = format.format(response.getToDate());
        if (changedCityHotel != null && changedCityHotel.get("HOTEL") != null && response.getHotels().isEmpty()) {
            String[] code = changedCityHotel.get("HOTEL").split(",");
            try {
                fillHotel(Long.valueOf(code[0]), format.parse(code[1]), format.parse(code[2]), response);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            down.countDown();
            result.put("HOTEL", true);
        }
        if (changedCityTraffic == null) {
            return result;
        }

        if (changedCityTraffic.get(TrafficType.AIRPLANE.toString()) != null) {
            TrafficPrice planePrice = trafficService.getPlanPriceByHashCode(changedCityTraffic.get(TrafficType.AIRPLANE.toString()));
            if (planePrice.getTraffic() == null || planePrice.getTraffic().getLeaveCity() == null) {
                return result;
            }
            Long planeFromCity = planePrice.getTraffic().getLeaveCity().getId();
            String planeFromDate = format.format(planePrice.getLeaveTime());
            if ((responseFromCity.equals(planeFromCity) && responseFromDate.equals(planeFromDate) && !isReturn) || (responseToCity.equals(planeFromCity) && responseToDate.equals(planeFromDate) && isReturn)) {
                response.addPlane(new TrafficBookingResponse(planePrice));
                down.countDown();
                result.put(TrafficType.AIRPLANE.toString(), true);
            }
        }
        if (changedCityTraffic.get(TrafficType.TRAIN.toString()) != null) {
            TrafficPrice trainPrice = trafficService.getTrainPriceByHashCode(changedCityTraffic.get(TrafficType.TRAIN.toString()));
            if (trainPrice.getTraffic() == null || trainPrice.getTraffic().getLeaveCity() == null) {
                return result;
            }
            Long trainFromCity = trainPrice.getTraffic().getLeaveCity().getId();
            String trainFromDate = format.format(trainPrice.getLeaveTime());
            if ((responseFromCity.equals(trainFromCity) && responseFromDate.equals(trainFromDate) && !isReturn) || (responseToCity.equals(trainFromCity) && responseToDate.equals(trainFromDate) && isReturn)) {
                response.addTrain(new TrafficBookingResponse(trainPrice));
                down.countDown();
                result.put(TrafficType.TRAIN.toString(), true);
            }
        }
        return result;
    }

    private void resortCity(List<PlanBookingResponse> responseList) {
        Collections.sort(responseList, new Comparator<PlanBookingResponse>() {
            @Override
            public int compare(PlanBookingResponse o1, PlanBookingResponse o2) {
                if (o1.getFromDate().before(o2.getFromDate())) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
    }

    private void resortTraffic(List<PlanBookingResponse> responseList) {
        for (PlanBookingResponse response : responseList) {
            if (response.getTrains().size() > 1) {
                List<BookingResponse> temp = Lists.newArrayList(response.getTrains());
                Collections.sort(temp, new Comparator<BookingResponse>() {
                    @Override
                    public int compare(BookingResponse o1, BookingResponse o2) {
                        return o1.getDate().before(o2.getDate()) ? -1 : 1;
                    }
                });
                response.getTrains().clear();
                response.getTrains().addAll(temp);
            }
            if (response.getPlanes().size() > 1) {
                List<BookingResponse> temp = Lists.newArrayList(response.getPlanes());
                Collections.sort(temp, new Comparator<BookingResponse>() {
                    @Override
                    public int compare(BookingResponse o1, BookingResponse o2) {
                        return o1.getDate().before(o2.getDate()) ? -1 : 1;
                    }
                });
                response.getPlanes().clear();
                response.getPlanes().addAll(temp);
            }
        }
    }

    private void findTrafficAndHotel(final PlanBookingResponse response, final CountDownLatch down, Map<String, Boolean> fillResult) {
        if (response.getTrafficType() == null) {
            response.setTrafficType(TrafficType.TRAIN);
        }
        if (!fillResult.get(TrafficType.AIRPLANE.toString()) && response.getTrafficType() != TrafficType.TRAIN) {
            doFillPlane(response, response.getFromCityId(), response.getCityId(), response.getFromDate(), down);
        }
        if (!fillResult.get(TrafficType.TRAIN.toString()) && response.getTrafficType() != TrafficType.AIRPLANE) {
            doFillTrain(response, response.getFromCityId(), response.getCityId(), response.getFromDate(), down);
        }
        if (!fillResult.get("HOTEL")) {
            ScenicGeoinfo scenicGeoinfo = scenicGeoInfoService.get(response.getCoreScenic());
            findHotel(response, scenicGeoinfo.getBaiduLng(), scenicGeoinfo.getBaiduLat(), null, down);
        }
    }

    public TbArea getLocalCity(Plan plan, String ip) {
        TbArea startCity = areaService.getLocation(ip);
        if (startCity != null) {
            return startCity;
        } else {
            return plan.getPlanDayList().get(0).getCity();
        }
    }

    public PlanBookingResponse doRecommendBooking(PlanBookingRequest request, Boolean isReturn) {
        PlanBookingResponse response = new PlanBookingResponse();
        response.setCoreScenic(request.getCoreScenic());
        TbArea toCity = areaService.get(request.getToCityId());
        TbArea fromCity = areaService.get(request.getFromCityId());
        response.withCityId(toCity.getId()).withCityName(toCity.getName())
                .withFromCityId(fromCity.getId()).withFromCityName(fromCity.getName());

        Calendar calendar = Calendar.getInstance();
        Date fromDate = request.getFromDate();
        if (fromDate != null) {
            calendar.setTime(fromDate);
        } else {
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        response.withFromDate(calendar.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, request.getDays() + 1);
        response.withToDate(calendar.getTime()).addDays(request.getDays());
        CountDownLatch down;
        int num = 1;

        if (request.getTrafficType() == null) {
            request.setTrafficType(TrafficType.AIRPLANE);
        }

        if (request.getTrafficType() == TrafficType.ALL) {
            num += 2;
            if (isReturn) {
                num += 2;
            }
        } else if (isReturn) {
            num += 2;
        } else {
            num += 1;
        }
        down = new CountDownLatch(num);
        if (request.getTrafficType() == TrafficType.AIRPLANE) {
            doFillPlane(response, response.getFromCityId(), response.getCityId(), response.getFromDate(), down);
            if (isReturn) {
                doFillPlane(response, response.getCityId(), request.getPrevFromCityId(), response.getToDate(), down);
            }
        } else if (request.getTrafficType() == TrafficType.TRAIN) {
            doFillTrain(response, response.getFromCityId(), response.getCityId(), response.getFromDate(), down);
            if (isReturn) {
                doFillTrain(response, response.getCityId(), request.getPrevFromCityId(), response.getToDate(), down);
            }
        } else {
            doFillPlane(response, response.getFromCityId(), response.getCityId(), response.getFromDate(), down);
            doFillTrain(response, response.getFromCityId(), response.getCityId(), response.getFromDate(), down);
            if (isReturn) {
                doFillPlane(response, response.getCityId(), request.getPrevFromCityId(), response.getToDate(), down);
                doFillTrain(response, response.getCityId(), request.getPrevFromCityId(), response.getToDate(), down);
            }
        }
        ScenicInfo scenicInfo = scenicInfoService.get(request.getCoreScenic());
        findHotel(response, scenicInfo.getScenicGeoinfo().getBaiduLng(), scenicInfo.getScenicGeoinfo().getBaiduLat(), request.getHotelStar(), down);
        try {
            down.await();
        } catch (InterruptedException e) {
            LOG.error(e.getMessage(), e);
        }
        resortTraffic(Lists.newArrayList(response));
        return response;
    }

    private void doFillPlane(final PlanBookingResponse response, final Long fromCityId, final Long toCityId, final Date date, final CountDownLatch down) {

        GlobalTheadPool.instance.submit(new Callable<Object>() {

            @Override
            public Object call() throws Exception {
                return fillPlaneTask(fromCityId, toCityId, date, response, down);
            }
        });
    }

    private Object fillPlaneTask(Long fromCityId, Long toCityId, Date date, PlanBookingResponse response, CountDownLatch down) {
        try {
            StopWatch watch = new StopWatch();
            watch.start();
            LOG.info(String.format("doFillPlane %s", watch));
            Clock clock = new Clock();
            System.out.println("1花费" + clock.elapseTime() + "ms");

            List<Traffic> planeList = trafficService.doQueryAndSaveByCity(fromCityId, toCityId, TrafficType.AIRPLANE, date);
            System.out.println("2花费" + clock.elapseTime() + "ms");
            if (planeList == null || planeList.isEmpty()) {
                TrafficBookingResponse plane = new TrafficBookingResponse();
                plane.setDate(date);
                response.addPlane(plane);
                watch.stop();
                LOG.info(String.format("Finish doFillPlane %s", watch));
                return null;
            }
            TrafficBookingResponse cheapestPrice = getCheapestPrice(planeList, response);


            if (cheapestPrice == null) {
                cheapestPrice = new TrafficBookingResponse();
                cheapestPrice.setDate(date);
            }
            response.addPlane(cheapestPrice);
            watch.stop();
            LOG.info(String.format("Finish doFillPlane %s", watch));
            return null;
        } catch (Exception e) {
            LOG.error("fail", e);
        } finally {
            down.countDown();
        }
        return null;
    }

    private void doFillTrain(final PlanBookingResponse response, final Long fromCityId, final Long toCityId, final Date date, final CountDownLatch down) {
        GlobalTheadPool.instance.submit(new Callable<Object>() {

            @Override
            public Object call() throws Exception {
                return fillTrainTask(fromCityId, toCityId, date, response, down);
            }
        });
    }

    private Object fillTrainTask(Long fromCityId, Long toCityId, Date date, PlanBookingResponse response, CountDownLatch down) {
        try {
            StopWatch watch = new StopWatch();
            watch.start();
            LOG.info(String.format("doFillTrain %s", watch));
            List<Traffic> trainList = trafficService.doQueryAndSaveByCity(fromCityId, toCityId, TrafficType.TRAIN, date);
            if (trainList == null || trainList.isEmpty()) {
                TrafficBookingResponse train = new TrafficBookingResponse();
                train.setDate(date);
                response.addTrain(train);
                watch.stop();
                LOG.info(String.format("Finish doFillTrain %s", watch));
                return null;
            }
            TrafficBookingResponse cheapestPrice = getCheapestPrice(trainList, response);
            if (cheapestPrice == null) {
                cheapestPrice = new TrafficBookingResponse();
                cheapestPrice.setDate(date);
            }
            response.addTrain(cheapestPrice);
            watch.stop();
            LOG.info(String.format("Finish doFillTrain %s", watch));
            return null;
        } finally {
            down.countDown();
        }
    }

    private TrafficBookingResponse getCheapestPrice(List<Traffic> trafficList, PlanBookingResponse response) {

        List<TrafficPrice> list = Lists.newArrayList();
        ScenicInfo scenicInfo = scenicInfoService.get(response.getCoreScenic());
        Double minDis = Double.MAX_VALUE;
        List<TrafficPrice> priceList = Lists.newArrayList();
        for (Traffic traffic : trafficList) {
            if (traffic.getTrafficType() == TrafficType.AIRPLANE || !traffic.getTrafficCode().startsWith("K")) {
                list.addAll(traffic.getPrices());
            }
        }
        Transportation transportation;
        if (response.getFromCityId().equals(trafficList.get(0).getLeaveTransportation().getCityId().longValue())) {
            transportation = trafficList.get(0).getArriveTransportation();
        } else {
            transportation = trafficList.get(0).getLeaveTransportation();
        }
        for (TrafficPrice tp : list) {
            Double dis = getDistance(scenicInfo.getScenicGeoinfo().getBaiduLng(), scenicInfo.getScenicGeoinfo().getBaiduLat(),
                    transportation.getLng(), transportation.getLat());
            if (dis < minDis) {
                priceList.clear();
                minDis = dis;
                priceList.add(tp);
            } else if (dis.equals(minDis)) {
                priceList.add(tp);
            }
        }

        Collections.sort(priceList, new Comparator<TrafficPrice>() {
            @Override
            public int compare(TrafficPrice o1, TrafficPrice o2) {
                return (int) (o1.getPrice() - o2.getPrice());
            }
        });
        for (TrafficPrice trafficPrice : priceList) {
            if (!"0".equals(trafficPrice.getSeatNum().trim())) {
                return new TrafficBookingResponse(trafficPrice);
            }
        }
        return null;
    }

//    private final  double PI = 3.14159265358979323; // 圆周率
//    private final  double R = 6371229; // 地球的半径

    public static double getDistance(double longt1, double lat1, double longt2, double lat2) {
        double pi = 3.14159265358979323; // 圆周率
        double r = 6371229; // 地球的半径
        double x;
        double y;
        double distance;
        x = (longt2 - longt1) * pi * r
                * Math.cos(((lat1 + lat2) / 2) * pi / 180) / 180;
        y = (lat2 - lat1) * pi * r / 180;
        distance = Math.hypot(x, y);
        return distance;
    }



    public void findHotel(final PlanBookingResponse response, final Double longitude, final Double latitude, final Integer hotelStar, final CountDownLatch down) {

        GlobalTheadPool.instance.submit(new Callable<Object>() {

            @Override
            public Object call() throws Exception {
                return fillHotelTask(response, hotelStar, longitude, latitude, down);
            }
        });
    }

    private Object fillHotelTask(PlanBookingResponse response, Integer hotelStar, Double longitude, Double latitude, CountDownLatch down) {
        SessionFactory sessionFactory = (SessionFactory) SpringContextHolder.getBean("sessionFactory");
        boolean participate = ConcurrentUtil.bindHibernateSessionToThread(sessionFactory);
        try {
            StopWatch watch = new StopWatch();
            watch.start();
            LOG.info(String.format("findHotel %s", watch));
            StringBuilder builder = new StringBuilder();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(response.getFromDate());
//            builder.append("(");
//            for (int i = 0; i < response.getDays(); i++) {
//                if (builder.length() != 1) {
//                    builder.append(" AND ");
//                }
//                builder.append("date:").append(DateUtils.getDate(calendar.getTime()));
//                calendar.add(Calendar.DAY_OF_MONTH, 1);
//            }
//            builder.append(")");
            builder.append("cityId:[" + response.getCityId() + " TO " + (response.getCityId() + 99) + "]");
            if (hotelStar != null) {
                builder.append(" AND star:").append(hotelStar);
            }
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            builder.append(" AND start:[* TO ").append(format.format(response.getFromDate())).append("] AND end:[").append(format.format(response.getToDate())).append(" TO *]");
            List<HotelSolrEntity> hotelSolrEntities = hotelService.findNearByHotel(builder.toString(), longitude, latitude, 50f, null);
            if (hotelSolrEntities.isEmpty()) {
                return null;
            }
            for (int i = 0; i < hotelSolrEntities.size(); i++) {
                fillHotel(hotelSolrEntities.get(i).getId(), response.getFromDate(), response.getToDate(), response);
                if (!response.getHotels().isEmpty()) {
                    HotelBookingResponse hotelBookingResponse = (HotelBookingResponse) response.getHotels().get(0);
                    if (!hotelBookingResponse.getAvailableRooms().isEmpty()) {
                        break;
                    }
                    response.getHotels().clear();
                }
            }
            watch.stop();
            LOG.info(String.format("Finish findHotel %s", watch));
            return null;
        } catch (Exception e) {
            LOG.error("failed", e);
        } finally {
            down.countDown();
            ConcurrentUtil.closeHibernateSessionFromThread(participate, sessionFactory);
        }
        return null;
    }

    private void fillHotel(Long hotelId, Date enterDate, Date leaveDate, PlanBookingResponse response) {
        HotelPrice priceFilter = new HotelPrice();
        Hotel hotelFilter = new Hotel();
        hotelFilter.setId(hotelId);
        priceFilter.setHotel(hotelFilter);
        priceFilter.setStart(enterDate);
        priceFilter.setEnd(leaveDate);
        List<HotelPrice> hotelPrice = hotelPriceService.list(priceFilter, null, null, "price", "asc");
        if (hotelPrice.isEmpty()) {
            return;
        }
        HotelBookingResponse hotelBookingResponse = new HotelBookingResponse(hotelPrice.get(0)).checkIn(enterDate).checkOut(leaveDate);
        List<HotelRoomSolrEntity> roomSolrEntityList = Lists.transform(hotelPrice, new Function<HotelPrice, HotelRoomSolrEntity>() {
            @Override
            public HotelRoomSolrEntity apply(HotelPrice input) {
                return new HotelRoomSolrEntity(input);
            }
        });
        hotelBookingResponse.addAvailableRooms(roomSolrEntityList);
        response.addHotel(hotelBookingResponse);
    }

    public List<Long> savePlanTransAndHotel(Map<String, Object> map, Long planId) {
        final Plan plan = planService.getNoCache(planId);
        List<Long> trafficIdList = Lists.newArrayList();
        List<PlanDay> planDayList = plan.getPlanDayList();
        Integer totalDays = planDayList.size();
        Collections.sort(planDayList, new Comparator<PlanDay>() {
            @Override
            public int compare(PlanDay o1, PlanDay o2) {
                return o1.getDays() - o2.getDays();
            }
        });
        for (final PlanDay planDay : plan.getPlanDayList()) {
            long start = System.currentTimeMillis();
            if (planDay.getCity() == null) {
                continue;
            }
            Map<String, Object> transAndHotel = (Map<String, Object>) map.get(planDay.getCity().getId().toString());
            if (transAndHotel == null) {
                continue;
            }
            Object trafficPriceIdStr = transAndHotel.remove("traffic");
            List<Map<String, Object>> trafficList = (List<Map<String, Object>>) trafficPriceIdStr;
            if (trafficPriceIdStr != null) {
                if (!trafficList.isEmpty()) {
                    Map<String, Object> trafficMap = trafficList.get(0);
                    TrafficPrice trafficPrice = saveTraffic(trafficMap);
                    Long priceId = trafficPrice.getPriceId();
//                    if (priceId == null) {
//                        LOG.error("priceId ...........");
//                    }
                    fillTraffic(planDay, priceId);
                    trafficIdList.add(priceId);
                }
                if (trafficList.size() > 1) {
                    Map<String, Object> trafficMap = trafficList.get(1);
                    TrafficPrice trafficPrice = saveTraffic(trafficMap);
                    fillReturnTraffic(planDayList.get(totalDays - 1), trafficPrice.getPriceId());
                    trafficIdList.add(trafficPrice.getPriceId());
                }
            }
            Object hotelPriceIdStr = transAndHotel.get("hotel");
            if (hotelPriceIdStr != null) {
                Long hotelPriceId = Long.valueOf(hotelPriceIdStr.toString());
                fillHotel(planDay, hotelPriceId);
            }
            System.out.println(System.currentTimeMillis() - start);
        }
        plan.setCompleteFlag(true);
        planService.save(plan);
        GlobalTheadPool.instance.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                SessionFactory sessionFactory = (SessionFactory) SpringContextHolder.getBean("sessionFactory");
                boolean participate = ConcurrentUtil.bindHibernateSessionToThread(sessionFactory);
                Plan pland = planService.get(plan.getId());
                planService.indexPlan(pland);
                ConcurrentUtil.closeHibernateSessionFromThread(participate, sessionFactory);
                return null;
            }
        });
        return trafficIdList;
    }


    public TrafficPrice saveTraffic(Map<String, Object> trafficMap) {
        String[] keys = trafficMap.get("key").toString().split("##");
        List<Traffic> traffics = TrafficService.findTraffic(keys[0], keys[1], "TRAIN".equals(keys[2]) ? TrafficType.TRAIN : TrafficType.AIRPLANE, keys[3]);
        Traffic traffic = getTrafficByHashCode(traffics, trafficMap.get("trafficHash").toString());
//        traffic.setUser(outOrderDispatchService.getAccountUser());
//        traffic.setCompanyUnit(outOrderDispatchService.getCompanyUnit());
        traffic.setStatus(ProductStatus.UP);
        traffic.setSource(ProductSource.JUHE);
        if (TrafficType.AIRPLANE.equals(traffic.getTrafficType())) {
            traffic.setProType(ProductType.flight);
        } else if (TrafficType.TRAIN.equals(traffic.getTrafficType())) {
            traffic.setProType(ProductType.train);
        }
        traffic.setName(traffic.getLeaveTransportation().getName() + "-" + traffic.getArriveTransportation().getName() + "(" + traffic.getTrafficCode() + ")");
        traffic.setCreateTime(new Date());
//        if (traffic != null && traffic.getUser() == null) {
//            SysUser user = new SysUser();
//            user.setId(1L);
//            traffic.setUser(user);
//        }
        if (traffic == null) {
            throw new RuntimeException("包含超时交通数据");
        }
        trafficService.save(traffic);
        TrafficPrice trafficPrice = getTrafficPriceByHashCode(traffic.getPrices(), trafficMap.get("priceHash").toString());
        trafficPriceService.save(trafficPrice);
        return trafficPrice;
    }

    private void fillTraffic(PlanDay planDay, Long trafficPriceId) {
        TrafficPrice trafficPrice = trafficPriceService.get(trafficPriceId);
        if (trafficPrice == null) {
            return;
        }
        planDay.setTraffic(trafficPrice.getTraffic());
        planDay.setTrafficPriceId(trafficPriceId);
        planDay.setTrafficCost(trafficPrice.getPrice());
        planDay.setTrafficTime(trafficPrice.getTraffic().getFlightTime().intValue() / 60000);
    }

    private void fillReturnTraffic(PlanDay planDay, Long trafficPriceId) {
        TrafficPrice trafficPrice = trafficPriceService.get(trafficPriceId);
        if (trafficPrice == null) {
            return;
        }
        planDay.setReturnTraffic(trafficPrice.getTraffic());
        planDay.setReturnTrafficPriceId(trafficPriceId);
        planDay.setReturnTrafficCost(trafficPrice.getPrice());
        if (planDay.getTrafficTime() != null) {
            planDay.setTrafficTime(planDay.getTrafficTime() + trafficPrice.getTraffic().getFlightTime().intValue() / 60000);
        } else {
            planDay.setTrafficTime(trafficPrice.getTraffic().getFlightTime().intValue() / 60000);
        }
    }

    private void fillHotel(PlanDay planDay, Long hotelPriceId) {
        HotelPrice hotelPrice = hotelPriceService.get(hotelPriceId);
        if (hotelPrice == null) {
            return;
        }
        planDay.setHotel(hotelPrice.getHotel());
        planDay.setHotelCode(hotelPrice.getId().toString());
        planDay.setHotelCost(hotelPrice.getPrice());
    }

    private Traffic getTrafficByHashCode(List<Traffic> traffics, String singleTrafficId) {
        if (traffics == null) {
            return null;
        }
        for (Traffic traffic : traffics) {
            if (traffic.getHashCode().equals(singleTrafficId)) {
                return traffic;
            }
        }
        return null;
    }

    private TrafficPrice getTrafficPriceByHashCode(List<TrafficPrice> prices, String singleTrafficPriceId) {
        for (TrafficPrice price : prices) {
            if (price.getHashCode().equals(singleTrafficPriceId)) {
                return price;
            }
        }
        return null;
    }

}
