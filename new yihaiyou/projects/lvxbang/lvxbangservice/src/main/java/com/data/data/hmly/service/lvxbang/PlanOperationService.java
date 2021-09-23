package com.data.data.hmly.service.lvxbang;

import com.data.data.hmly.service.area.AreaService;
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.hotel.HotelService;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.lvxbang.request.PlanDayUpdateRequest;
import com.data.data.hmly.service.lvxbang.request.PlanTripUpdateRequest;
import com.data.data.hmly.service.lvxbang.request.PlanUpdateRequest;
import com.data.data.hmly.service.lvxbang.request.TripNode;
import com.data.data.hmly.service.lvxbang.response.MiniCityResponse;
import com.data.data.hmly.service.lvxbang.response.PlanOptimizeDayResponse;
import com.data.data.hmly.service.lvxbang.response.PlanOptimizeResponse;
import com.data.data.hmly.service.lvxbang.util.PlanTransformer;
import com.data.data.hmly.service.plan.PlanDayService;
import com.data.data.hmly.service.plan.PlanService;
import com.data.data.hmly.service.plan.PlanTripService;
import com.data.data.hmly.service.plan.RecommendPlanService;
import com.data.data.hmly.service.plan.entity.Plan;
import com.data.data.hmly.service.plan.entity.PlanDay;
import com.data.data.hmly.service.plan.entity.PlanStatistic;
import com.data.data.hmly.service.plan.entity.PlanTrip;
import com.data.data.hmly.service.plan.entity.RecommendPlan;
import com.data.data.hmly.service.plan.entity.RecommendPlanDay;
import com.data.data.hmly.service.plan.entity.RecommendPlanTrip;
import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.data.hmly.service.traffic.TrafficService;
import com.data.data.hmly.service.traffic.entity.Traffic;
import com.data.data.hmly.util.Clock;
import com.data.hmly.service.translation.direction.amap.AmapDirectionService;
import com.data.hmly.service.translation.direction.amap.pojo.AmapTaxiDirectionResult;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.zuipin.util.GlobalTheadPool;
import com.zuipin.util.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

//import com.data.hmly.service.translation.direction.baidu.pojo.BaiduTaxiDirection;

/**
 * @author Jonathan.Guo
 */
@Service
public class PlanOperationService {

    private final Logger logger = Logger.getLogger(PlanOperationService.class);

    @Resource
    private AreaService areaService;
    @Resource
    private PlanService planService;
    @Resource
    private PlanDayService planDayService;
    @Resource
    private PlanTripService planTripService;
    @Resource
    private RecommendPlanService recommendPlanService;
    @Resource
    private ScenicInfoService scenicInfoService;
    @Resource
    private HotelService hotelService;
    @Resource
    private TrafficService trafficService;

    private Double slat;
    private Double slng;
//    int n = 1;

    public Plan createPlan(Member user) {
        Date date = DateUtils.truncateTime(DateUtils.getNextDay(new Date()));
        return createPlan(user, date);
    }

    public Plan createPlan(Member user, Date date) {
        Date now = new Date();
        Plan plan = new Plan();
        plan.setUser(user);
        plan.setPlanDays(0);
        plan.setStartTime(date);
        plan.setCreateTime(now);
        plan.setModifyTime(now);
        plan.setPlatform(1);
        plan.setPushFlag(0);
        plan.setSource(0);
        plan.setValid(true);
        plan.setStatus(Plan.STATUS_VALID);
        planService.save(plan);
        return plan;
    }

    public boolean addOneDay(Plan plan) {
        return addDays(plan, 1);
    }

    public boolean addDays(Plan plan, int days) {
        if (days < 1) {
            logger.error("错误的天数, " + days);
            return false;
        }
        Date now = new Date();
        int i = days;
        try {
            while (i > 0) {
                PlanDay planDay = new PlanDay();
                planDay.setPlan(plan);
                planDay.setDate(DateUtils.add(plan.getStartTime(), Calendar.DAY_OF_MONTH, plan.getPlanDays() + 1));
                planDay.setDays(plan.getPlanDays() + 1);
                planDay.setCreateTime(now);
                planDay.setModifyTime(now);
                planDayService.save(planDay);
                plan.setPlanDays(plan.getPlanDays() + 1);
                i -= 1;
            }
        } catch (Exception e) {
            logger.error("添加行程天失败", e);
            return false;
        }
        return true;
    }

    public Plan optimize(Plan plan) {

        //todo: use real plan optimize and get the result plan to return
        //plan = OptimizeUtil.optimize(plan)
        return plan;
    }

    public RecommendPlan exportRecommend(Long planId) {
        Plan plan = planService.get(planId);
        return exportRecommend(plan);
    }

    public RecommendPlan exportRecommend(Plan plan) {
        RecommendPlan recommendPlan = PlanTransformer.fromPlan(plan);

        recommendPlan.setRecommendPlanDays(Lists.transform(plan.getPlanDayList(), new Function<PlanDay, RecommendPlanDay>() {
            @Override
            public RecommendPlanDay apply(PlanDay planDay) {
                RecommendPlanDay recommendPlanDay = PlanTransformer.fromPlanDay(planDay);
                recommendPlanDay.setRecommendPlanTrips(Lists.transform(planDay.getPlanTripList(), new Function<PlanTrip, RecommendPlanTrip>() {
                    @Override
                    public RecommendPlanTrip apply(PlanTrip planTrip) {
                        return PlanTransformer.fromPlanTrip(planTrip);
                    }
                }));
                return recommendPlanDay;
            }
        }));

        recommendPlan.setLineId(plan.getId());
        recommendPlanService.save(recommendPlan);
        return recommendPlan;
    }

    public Plan doQuoteFromPlan(Long planId, Member user, String ip) {
        Plan plan = planService.get(planId);
        return doQuote(plan, user, ip);
    }

    public Plan doQuote(Plan plan, Member user, String ip) {
        Plan target = PlanTransformer.copyPlan(plan);
        target.setUser(user);
        TbArea startCity = areaService.getLocation(ip);
        if (startCity == null) {
            startCity = plan.getStartCity();
        }
        target.setStartCity(startCity);
        planService.save(target);
        planService.saveStatistic(target.getPlanStatistic());
        for (PlanDay planDay : plan.getPlanDayList()) {
            PlanDay dayTarget = PlanTransformer.copyPlanDay(planDay);
            dayTarget.setPlan(target);
            planDayService.save(dayTarget);
            for (PlanTrip planTrip : planDay.getPlanTripList()) {
                PlanTrip tripTarget = PlanTransformer.copyPlanTrip(planTrip);
                tripTarget.setPlan(target);
                tripTarget.setPlanDay(dayTarget);
                planTripService.save(tripTarget);
            }
        }
        return target;
    }

    public Plan createPlan(PlanUpdateRequest request, Member user, String ip) {
        Plan plan = new Plan();
        plan.setUser(user);
        plan.setName(request.name);
        if (StringUtils.isBlank(plan.getName())) {
            fillPlanName(request, plan, user);
        }
        plan.setTips(request.tips);
        if (request.startTime == null) {
            plan.setStartTime(DateUtils.add(new Date(), Calendar.DAY_OF_MONTH, 1));
        } else {
            plan.setStartTime(request.startTime);
        }
        plan.setPlatform(1);
        plan.setPushFlag(0);
        plan.setSource(0);
        plan.setValid(true);
        if (request.days != null) {
            plan.setPlanDays(request.days.size());
        } else {
            plan.setPlanDays(0);
        }
        TbArea startCity = getStartCity(request, ip);
        plan.setStartCity(startCity);
        plan.setStatus(Plan.STATUS_VALID);
        plan.setCompleteFlag(false);
        planService.save(plan);
        PlanStatistic planStatistic = new PlanStatistic();
        planStatistic.setPlan(plan);
        plan.setPlanStatistic(planStatistic);
        if (request.days == null) {
            planService.saveStatistic(planStatistic);
            return plan;
        }
        int m = 1;
        StringBuilder passScenics = new StringBuilder();
        int size = 1;
        for (int i = 0; i < request.days.size(); i++) {
            PlanDayUpdateRequest day = request.days.get(i);
            if (i > 0 && !day.cityId.equals(request.days.get(i - 1).cityId)) {
                m++;
            }
            PlanDay planDay = fillPlanDay(plan, i, m, day);
            planDayService.save(planDay);
            if (day.trips == null) {
                continue;
            }
//            n = 1;
            for (PlanTripUpdateRequest trip : request.days.get(i).trips) {
                PlanTrip planTrip = fillPlanTrip(plan, planDay, trip);
                if (size < 4) {
                    passScenics.append(planTrip.getScenicInfo().getName()).append("-");
                    size++;
                }
                planTripService.save(planTrip);
//                n++;
            }

            planDayService.update(planDay);
            fillDayPrice(plan, planDay);
        }
        plan.setPassScenics(passScenics.deleteCharAt(passScenics.length() - 1).toString());

        planStatistic.setPlanCost(planStatistic.getPlanTicketCost() + planStatistic.getPlanHotelCost() + planStatistic.getPlanTravelCost());
        planStatistic.setPlanSeasonticketCost(planStatistic.getPlanCost());
        planStatistic.setIncludeSeasonticketCost(planStatistic.getPlanCost());
        planService.saveStatistic(planStatistic);
        return plan;
    }

    public Plan createPlanByMobile(final PlanUpdateRequest request, Member user, String ip) {
        final Plan plan = new Plan();
        plan.setUser(user);
        plan.setName(request.name);
        if (StringUtils.isBlank(plan.getName())) {
            fillPlanName(request, plan, user);
        }
        plan.setTips(request.tips);
        if (request.startTime == null) {
            plan.setStartTime(DateUtils.add(new Date(), Calendar.DAY_OF_MONTH, 1));
        } else {
            plan.setStartTime(request.startTime);
        }
        plan.setPlatform(1);
        plan.setPushFlag(0);
        plan.setSource(0);
        plan.setValid(true);
        if (request.days != null) {
            plan.setPlanDays(request.days.size());
        } else {
            plan.setPlanDays(0);
        }
        TbArea startCity = getStartCity(request, ip);
        plan.setStartCity(startCity);
        plan.setStatus(Plan.STATUS_VALID);
        plan.setCompleteFlag(false);
        planService.save(plan);
        GlobalTheadPool.instance.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                Plan taskPlan = planService.get(plan.getId());
                PlanStatistic planStatistic = new PlanStatistic();
                planStatistic.setPlan(taskPlan);
                taskPlan.setPlanStatistic(planStatistic);
                if (request.days == null) {
                    planService.saveStatistic(planStatistic);
                    return taskPlan;
                }
                int m = 1;
                StringBuilder passScenics = new StringBuilder();
                int size = 1;
                for (int i = 0; i < request.days.size(); i++) {
                    PlanDayUpdateRequest day = request.days.get(i);
                    if (i > 0 && !day.cityId.equals(request.days.get(i - 1).cityId)) {
                        m++;
                    }
                    PlanDay planDay = fillPlanDay(taskPlan, i, m, day);
                    planDayService.save(planDay);
                    if (day.trips == null) {
                        continue;
                    }
//            n = 1;
                    for (PlanTripUpdateRequest trip : request.days.get(i).trips) {
                        PlanTrip planTrip = fillPlanTrip(taskPlan, planDay, trip);
                        if (size < 4) {
                            passScenics.append(planTrip.getScenicInfo().getName()).append("-");
                            size++;
                        }
                        planTripService.save(planTrip);
//                n++;
                    }

                    planDayService.update(planDay);
                    fillDayPrice(taskPlan, planDay);
                }
                taskPlan.setPassScenics(passScenics.deleteCharAt(passScenics.length() - 1).toString());

                planStatistic.setPlanCost(planStatistic.getPlanTicketCost() + planStatistic.getPlanHotelCost() + planStatistic.getPlanTravelCost());
                planStatistic.setPlanSeasonticketCost(planStatistic.getPlanCost());
                planStatistic.setViewNum(1);
                planStatistic.setQuoteNum(0);
                planStatistic.setIncludeSeasonticketCost(planStatistic.getPlanCost());
                planService.saveStatistic(planStatistic);
                planService.update(taskPlan);
                return null;
            }
        });
        return plan;
    }

    private PlanDay fillPlanDay(Plan plan, int i, int m, PlanDayUpdateRequest day) {
        PlanDay planDay = new PlanDay();
        planDay.setPlan(plan);
        planDay.setDays(i + 1);
        planDay.setDate(DateUtils.add(plan.getStartTime(), Calendar.DAY_OF_MONTH, i + m));
        planDay.setTicketCost(0f);
        planDay.setFoodCost(0f);
        planDay.setIncludeSeasonticketCost(0f);
        if (day.hotelId != null) {
            Hotel hotel = hotelService.get(day.hotelId);
            planDay.setHotel(hotel);
            planDay.setHotelCost(hotel.getPrice());
        } else {
            planDay.setHotelCost(0f);
        }
        if (day.trafficId != null) {
            Traffic traffic = trafficService.get(day.trafficId);
            planDay.setTraffic(traffic);
            planDay.setTrafficCost(traffic.getPrice());
        } else {
            planDay.setTrafficCost(0f);
        }
        if (day.cityId != null) {
            TbArea city = new TbArea();
            city.setId(day.cityId);
            planDay.setCity(city);
            if (plan.getCity() == null) {
                plan.setCity(city);
                if (plan.getId() != null) {
                    planService.update(plan);
                } else {
                    planService.save(plan);
                }
            }
        }
        return planDay;
    }

    private void fillDayPrice(Plan plan, PlanDay planDay) {
        PlanStatistic planStatistic = plan.getPlanStatistic();
        planStatistic.setPlanTicketCost(planStatistic.getPlanTicketCost() + planDay.getTicketCost());
        planStatistic.setPlanTravelCost(planStatistic.getPlanTravelCost() + planDay.getTrafficCost());
        planStatistic.setPlanHotelCost(planStatistic.getPlanHotelCost() + planDay.getHotelCost());
    }

    private PlanTrip fillPlanTrip(Plan plan, PlanDay planDay, PlanTripUpdateRequest trip) {
        PlanTrip planTrip = new PlanTrip();
        planTrip.setPlan(plan);
        planTrip.setPlanDay(planDay);
        ScenicInfo scenicInfo = scenicInfoService.get(trip.scenicId);

        if (scenicInfo != null) {
            if (slng != null && slat != null && slng > 0d && slat > 0d) {
                String result = getDisByAmap(scenicInfo);
                planTrip.setTravelMileage(result);
            }
            slat = scenicInfo.getScenicGeoinfo().getBaiduLat();
            slng = scenicInfo.getScenicGeoinfo().getBaiduLng();
            planTrip.setScenicInfo(scenicInfo);
            planDay.setTicketCost(planDay.getTicketCost() + scenicInfo.getPrice());
            if (plan.getCoverPath() == null) {
                plan.setCoverPath(scenicInfo.getCover());
            }
        }
        planTrip.setTripType(trip.type);
        planTrip.setTravelType(trip.traffic);
        if (trip.trafficCost != null) {
            planTrip.setTravelPrice(trip.trafficCost);
            planDay.setTrafficCost(planDay.getTrafficCost() + trip.trafficCost);
        }
        return planTrip;
    }

    public String getDisByAmap(ScenicInfo scenicInfo) {
        AmapTaxiDirectionResult result;
        String dis = null;
        if (scenicInfo.getScenicGeoinfo() != null) {
            result = AmapDirectionService.getAmapTaxiDirectionWithBaiduCoordinate(slat, slng,
                    scenicInfo.getScenicGeoinfo().getBaiduLat(),
                    scenicInfo.getScenicGeoinfo().getBaiduLng(),
                    "1c061beb50cf259298c21e0d8a34df9f");
//                      result = BaiduTaxiDirectionService.getBaiduTaxiMap(null, null, slat, slng, scenicInfo.getScenicGeoinfo().getBaiduLat(), scenicInfo.getScenicGeoinfo().getBaiduLng(), "driving", "p2FOUcg91ZU7pLNssqP4yKWL");
        } else {
            result = null;
        }
        if (result != null && result.getRoute().getPaths() != null) {
            dis = result.getRoute().getPaths().get(0).getDistance();
        }
        return dis;
    }

    public Plan updatePlan(PlanUpdateRequest request, Member user, String ip) {
        Plan plan = planService.get(request.id);
        if (plan == null || !plan.getUser().getId().equals(user.getId())) {
            logger.error("plan#" + request.id + " not exists or not editable by user#" + user.getId());
            return createPlan(request, user, ip);
        }
        Date now = new Date();
        plan.setName(request.name);
        if (StringUtils.isBlank(plan.getName())) {
            fillPlanName(request, plan, user);
        }
        plan.setTips(request.tips);
        if (request.startTime != null) {
            plan.setStartTime(request.startTime);
        }
        TbArea startCity = new TbArea();
        if (request.getCityId() == null) {
            startCity = areaService.getLocation(ip);
            if (startCity == null) {
                startCity = new TbArea();
                startCity.setId(350200l);
            }
        } else {
            startCity.setId(request.getCityId());
        }
        plan.setStartCity(startCity);
        planService.update(plan);

        if (request.days == null) {
            return plan;
        }

        Map<Long, PlanDay> dayMap = Maps.uniqueIndex(plan.getPlanDayList(), new Function<PlanDay, Long>() {
            @Override
            public Long apply(PlanDay planDay) {
                return planDay.getId();
            }
        });
        List<PlanTrip> tripList = Lists.newArrayList();
        for (PlanDay planDay : plan.getPlanDayList()) {
            tripList.addAll(planDay.getPlanTripList());
        }
        Map<Long, PlanTrip> tripMap = Maps.uniqueIndex(tripList, new Function<PlanTrip, Long>() {
            @Override
            public Long apply(PlanTrip planTrip) {
                return planTrip.getId();
            }
        });
        plan.setPlanDays(0);
        int m = 1;
        for (int i = 0; i < request.days.size(); i++) {
            PlanDayUpdateRequest day = request.days.get(i);
            if (i > 0 && !day.cityId.equals(request.days.get(i - 1).cityId)) {
                m++;
            }
            PlanDay planDay;
            if (day.id == null) {
                planDay = fillPlanDay(plan, plan.getPlanDays(), m, day);
                planDay.setPlan(plan);
                planDay.setCreateTime(now);
                planDay.setModifyTime(now);
                planDayService.save(planDay);
                fillDayPrice(plan, planDay);
                plan.setPlanDays(plan.getPlanDays() + 1);
            } else {
                planDay = dayMap.remove(day.id);
            }
            populateTrip(plan, tripMap, planDay, day.trips);
        }
        for (PlanDay planDay : dayMap.values()) {
            planDayService.delete(planDay);
        }
        for (PlanTrip planTrip : tripMap.values()) {
            planTripService.delete(planTrip);
        }
        return plan;
    }

    private void populateTrip(Plan plan, Map<Long, PlanTrip> tripMap, PlanDay planDay, List<PlanTripUpdateRequest> trips) {
        Date now = new Date();
        for (int i = 0; i < trips.size(); i++) {
            PlanTripUpdateRequest trip = trips.get(i);
            ScenicInfo scenicInfo = scenicInfoService.get(trip.scenicId);
            if (trip.id != null) {
                PlanTrip planTrip = tripMap.remove(trip.id);
                if (planTrip != null && planTrip.getScenicInfo().getId().equals(trip.scenicId)) {
                    planTrip.setPlanDay(planDay);
                    planTrip.setOrderNum(i);
                    planTrip.setModifyTime(now);
                    chuliTrip(planTrip, scenicInfo);
                    slat = scenicInfo.getScenicGeoinfo().getBaiduLat();
                    slng = scenicInfo.getScenicGeoinfo().getBaiduLng();
                    planTripService.save(planTrip);
                    continue;
                }
            }
            PlanTrip planTrip = new PlanTrip();
            planTrip.setPlan(plan);
            planTrip.setPlanDay(planDay);
//            ScenicInfo scenicInfo = new ScenicInfo();
            scenicInfo.setId(trip.scenicId);
            planTrip.setScenicInfo(scenicInfo);
            planTrip.setTripType(trip.type);
            planTrip.setTravelType(trip.traffic);
            if (slng != null && slat != null && slng > 0d && slat > 0d) {
                planTrip.setTravelMileage(getDisByAmap(scenicInfo));
            }
            slat = scenicInfo.getScenicGeoinfo().getBaiduLat();
            slng = scenicInfo.getScenicGeoinfo().getBaiduLng();
            planTrip.setOrderNum(i);
            planTrip.setCreateTime(now);
            planTrip.setModifyTime(now);
            planTripService.save(planTrip);
        }
    }

    public void chuliTrip(PlanTrip planTrip, ScenicInfo scenicInfo) {
        if (slng != null && slat != null && slng > 0d && slat > 0d) {
            planTrip.setTravelMileage(getDisByAmap(scenicInfo));
        }
    }

    public PlanOptimizeResponse getRecommendPlanResponse(Long recommendPlanId, Set<String> failedScenic) {
        PlanOptimizeResponse response = new PlanOptimizeResponse();
        Clock clock = new Clock();
        RecommendPlan recommendPlan = recommendPlanService.get(recommendPlanId);
        logger.info("read recommend plan cost: " + clock.elapseTime());
        Boolean flag = false;
        Set<Long> scenics = Sets.newHashSet();
        for (RecommendPlanDay recommendPlanDay : recommendPlan.getRecommendPlanDays()) {
            flag = recommendPlanDayToDayResponse(response, recommendPlanDay, failedScenic, scenics) || flag;
        }
        if (flag) {
            Map<Long, List<PlanOptimizeDayResponse>> map = new LinkedHashMap<Long, List<PlanOptimizeDayResponse>>();
            Collections.sort(response.data, new Comparator<PlanOptimizeDayResponse>() {
                @Override
                public int compare(PlanOptimizeDayResponse o1, PlanOptimizeDayResponse o2) {
                    return Integer.valueOf(o1.day).compareTo(Integer.valueOf(o2.day));
                }
            });
            for (PlanOptimizeDayResponse planOptimizeDayResponse : response.data) {
                if (planOptimizeDayResponse.city.id == null || planOptimizeDayResponse.city.id < 0) {
                    continue;
                }
                Long cityId = planOptimizeDayResponse.city.id / 100 * 100;
                if (map.containsKey(cityId)) {
                    planOptimizeDayResponse.day = map.get(cityId).size() + 1;
                    map.get(cityId).add(planOptimizeDayResponse);
                } else {
                    planOptimizeDayResponse.day = 1;
                    List<PlanOptimizeDayResponse> list = Lists.newArrayList(planOptimizeDayResponse);
                    map.put(cityId, list);
                }
            }
            response.data.clear();
            for (List<PlanOptimizeDayResponse> planOptimizeDayResponses : map.values()) {
                response.data.addAll(planOptimizeDayResponses);
            }
            return response;
        }
        return null;
    }

    public Boolean recommendPlanDayToDayResponse(PlanOptimizeResponse response, RecommendPlanDay recommendPlanDay, Set<String> failedScenic, Set<Long> scenics) {
        Boolean flag = false;
        if (StringUtils.isBlank(recommendPlanDay.getCitys())) {
            return false;
        }
        String[] cityIds = recommendPlanDay.getCitys().split(",");
        List<Long> citys = Lists.newArrayList();
        for (String cityIdStr : cityIds) {
            if (StringUtils.isBlank(cityIdStr)) {
                continue;
            }
            Long cityId = Long.valueOf(cityIdStr) / 100 * 100;
            if (citys.indexOf(cityId) < 0) {
                citys.add(cityId);
            }
        }
        for (Long cityId : citys) {
            PlanOptimizeDayResponse dayResponse = new PlanOptimizeDayResponse();
            dayResponse.day = recommendPlanDay.getDay();
            dayResponse.city = new MiniCityResponse();
            dayResponse.city.id = cityId;
            dayResponse.tripList = Lists.newArrayList();
            for (RecommendPlanTrip recommendPlanTrip : recommendPlanDay.getRecommendPlanTrips()) {
                if (recommendPlanTrip.getTripType() != 1) {
                    if (StringUtils.isNotBlank(recommendPlanTrip.getScenicName())) {
                        failedScenic.add(recommendPlanTrip.getScenicName());
                    }
                    continue;
                }
                if (recommendPlanTrip.getScenicId() == null || recommendPlanTrip.getScenicId() <= 0) {
                    if (StringUtils.isNotBlank(recommendPlanTrip.getScenicName())) {
                        failedScenic.add(recommendPlanTrip.getScenicName());
                    }
                    continue;
                }
                if (recommendPlanTrip.getCityCode() != null && Long.valueOf(recommendPlanTrip.getCityCode()) / 100 == dayResponse.city.id / 100 && !scenics.contains(recommendPlanTrip.getScenicId())) {
                    TripNode tripNode = new TripNode();
                    tripNode.id = recommendPlanTrip.getScenicId();
                    tripNode.type = recommendPlanTrip.getTripType();
                    dayResponse.tripList.add(tripNode);
                    scenics.add(recommendPlanTrip.getScenicId());
                    flag = true;
                }
            }
            if (!dayResponse.tripList.isEmpty()) {
                response.data.add(dayResponse);
            }
        }
        return flag;
    }

    public PlanOptimizeResponse getPlanResponse(Long planId) {
        PlanOptimizeResponse response = new PlanOptimizeResponse();
        Plan plan = planService.get(planId);
        response.id = plan.getId();
        Map<Long, Integer> map = new HashMap<Long, Integer>();
        for (PlanDay planDay : plan.getPlanDayList()) {
            PlanOptimizeDayResponse dayResponse = new PlanOptimizeDayResponse();
            dayResponse.day = planDay.getDays();
            dayResponse.city = new MiniCityResponse();
            dayResponse.city.id = planDay.getCity().getId();
            dayResponse.tripList = Lists.newArrayList();
            for (PlanTrip planTrip : planDay.getPlanTripList()) {
                try {
                    TripNode tripNode = new TripNode();
                    tripNode.id = planTrip.getScenicInfo().getId();
                    tripNode.type = planTrip.getTripType();
                    dayResponse.tripList.add(tripNode);
                } catch (ObjectNotFoundException e) {
                    logger.error("no such scenic trip id#" + planTrip.getId());
                }
            }
            response.data.add(dayResponse);
        }
        Collections.sort(response.data, new Comparator<PlanOptimizeDayResponse>() {
            @Override
            public int compare(PlanOptimizeDayResponse o1, PlanOptimizeDayResponse o2) {
                return Integer.valueOf(o1.day).compareTo(Integer.valueOf(o2.day));
            }
        });
        for (PlanOptimizeDayResponse planOptimizeDayResponse : response.data) {
            if (map.containsKey(planOptimizeDayResponse.city.id)) {
                Integer day = map.get(planOptimizeDayResponse.city.id) + 1;
                planOptimizeDayResponse.day = day;
                map.put(planOptimizeDayResponse.city.id, day);
            } else {
                planOptimizeDayResponse.day = 1;
                map.put(planOptimizeDayResponse.city.id, 1);
            }
        }
        return response;
    }

    private void fillPlanName(PlanUpdateRequest request, Plan plan, Member user) {
        StringBuilder builder = new StringBuilder();
        if (StringUtils.isNotBlank(user.getNickName())) {
            builder.append(user.getNickName()).append("的");
        }
        List<Long> cityIds = Lists.newArrayList(Lists.transform(request.days, new Function<PlanDayUpdateRequest, Long>() {
            @Override
            public Long apply(PlanDayUpdateRequest dayUpdateRequest) {
                return dayUpdateRequest.cityId;
            }
        }));
        List<TbArea> cityList = areaService.getByIds(cityIds);
        for (TbArea city : cityList) {
            builder.append(city.getName()).append("、");
        }
        builder.setLength(builder.length() - 1);
        builder.append(request.days.size()).append("日游");
        plan.setName(builder.toString());
    }

    private TbArea getStartCity(PlanUpdateRequest request, String ip) {
        TbArea startCity = new TbArea();
        if (request.getCityId() == null) {
            startCity = areaService.getLocation(ip);
            if (startCity == null) {
                startCity = new TbArea();
                startCity.setId(350200l);
            }
        } else {
            startCity.setId(request.getCityId());
        }
        return startCity;
    }

    public Double getSlng() {
        return slng;
    }

    public void setSlng(Double slng) {
        this.slng = slng;
    }

    public Double getSlat() {
        return slat;
    }

    public void setSlat(Double slat) {
        this.slat = slat;
    }
}
