package com.data.data.hmly.service;

import com.data.data.hmly.action.yihaiyou.response.HotelPriceResponse;
import com.data.data.hmly.action.yihaiyou.response.OptimizeDetailDayResponse;
import com.data.data.hmly.action.yihaiyou.response.OptimizeDetailHotelResponse;
import com.data.data.hmly.action.yihaiyou.response.OptimizeDetailResponse;
import com.data.data.hmly.action.yihaiyou.response.PlanDayResponse;
import com.data.data.hmly.action.yihaiyou.response.PlanHotelResponse;
import com.data.data.hmly.action.yihaiyou.response.PlanOrderHotelResponse;
import com.data.data.hmly.action.yihaiyou.response.PlanOrderResponse;
import com.data.data.hmly.action.yihaiyou.response.PlanResponse;
import com.data.data.hmly.action.yihaiyou.response.PlanTrafficResponse;
import com.data.data.hmly.action.yihaiyou.response.PlanTripResponse;
import com.data.data.hmly.action.yihaiyou.response.TicketResponse;
import com.data.data.hmly.action.yihaiyou.response.TicketScenicResponse;
import com.data.data.hmly.action.yihaiyou.vo.PlanDayVo;
import com.data.data.hmly.action.yihaiyou.vo.PlanVo;
import com.data.data.hmly.action.yihaiyou.vo.ScenicInfoVo;
import com.data.data.hmly.service.common.ProductimageService;
import com.data.data.hmly.service.common.entity.Productimage;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.hotel.HotelPriceService;
import com.data.data.hmly.service.hotel.HotelService;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.hotel.entity.HotelPrice;
import com.data.data.hmly.service.hotel.entity.HotelPriceCalendar;
import com.data.data.hmly.service.hotel.entity.enums.PriceStatus;
import com.data.data.hmly.service.hotel.request.HotelSearchRequest;
import com.data.data.hmly.service.hotel.vo.HotelSolrEntity;
import com.data.data.hmly.service.lvxbang.DiscountService;
import com.data.data.hmly.service.lvxbang.PlanBookingService;
import com.data.data.hmly.service.lvxbang.PlanOptimizeService;
import com.data.data.hmly.service.lvxbang.PlanOrderService;
import com.data.data.hmly.service.lvxbang.request.PlanOptimizeRequest;
import com.data.data.hmly.service.lvxbang.request.TripNode;
import com.data.data.hmly.service.lvxbang.response.PlanOptimizeDayResponse;
import com.data.data.hmly.service.lvxbang.response.PlanOptimizeResponse;
import com.data.data.hmly.service.plan.PlanDayService;
import com.data.data.hmly.service.plan.PlanService;
import com.data.data.hmly.service.plan.PlanTripService;
import com.data.data.hmly.service.plan.entity.Plan;
import com.data.data.hmly.service.plan.entity.PlanDay;
import com.data.data.hmly.service.plan.entity.PlanTrip;
import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.data.hmly.service.scenic.vo.ScenicSolrEntity;
import com.data.data.hmly.service.ticket.TicketDatepriceService;
import com.data.data.hmly.service.ticket.entity.TicketDateprice;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.hibernate.util.Page;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zuipin.util.ConcurrentUtil;
import com.zuipin.util.GlobalTheadPool;
import com.zuipin.util.QiniuUtil;
import com.zuipin.util.SpringContextHolder;
import com.zuipin.util.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * Created by huangpeijie on 2016-04-26,0026.
 */
@Service
public class PlanMobileService {
    @Resource
    private PlanService planService;
    @Resource
    private PlanDayService planDayService;
    @Resource
    private PlanTripService planTripService;
    @Resource
    private PlanOrderService planOrderService;
    @Resource
    private DiscountService discountService;
    @Resource
    private HotelPriceService hotelPriceService;
    @Resource
    private PlanBookingService planBookingService;
    @Resource
    private PlanOptimizeService planOptimizeService;
    @Resource
    private ScenicInfoService scenicInfoService;
    @Resource
    private HotelService hotelService;
    @Resource
    private TicketDatepriceService ticketDatepriceService;
    @Resource
    private FerryMobileService ferryMobileService;
    @Resource
    private ProductimageService productimageService;

    private static final String cityId = "350200";

    private final ObjectMapper mapper = new ObjectMapper();

//    public PlanOrderResponse planToOrder(Plan plan) throws IOException, ParseException {
//        Map<String, Object> dataMap = collectPlanBookingData(plan);
//        List<TrafficResponse> planeList = Lists.newArrayList();
//        List<TrafficResponse> trainList = Lists.newArrayList();
//        List<TrafficResponse> shipList = Lists.newArrayList();
//        List<HotelResponse> hotelList = Lists.newArrayList();
//        List<Map<String, Object>> ticketList = Lists.newArrayList();
//        planOrderService.prepareTraffic(dataMap, planeList, trainList, shipList);
//        planOrderService.prepareHotel(dataMap, hotelList);
//        planOrderService.prepareTicket(plan, ticketList);
//        List<UserCoupon> userCouponsList = discountService.listUserCoupon(ProductType.plan, plan.getUser());
//
//        PlanOrderResponse response = new PlanOrderResponse();
//        response.setName(plan.getName());
//        response.setDay(plan.getPlanDays());
//        response.setPlayDate(DateUtils.formatShortDate(plan.getStartTime()));
//
//        List<PlanOrderTrafficResponse> planes = Lists.transform(planeList, new Function<TrafficResponse, PlanOrderTrafficResponse>() {
//            @Override
//            public PlanOrderTrafficResponse apply(TrafficResponse input) {
//                return new PlanOrderTrafficResponse(input);
//            }
//        });
//        response.setPlanes(planes);
//
//        List<PlanOrderTrafficResponse> trains = Lists.transform(trainList, new Function<TrafficResponse, PlanOrderTrafficResponse>() {
//            @Override
//            public PlanOrderTrafficResponse apply(TrafficResponse input) {
//                return new PlanOrderTrafficResponse(input);
//            }
//        });
//        response.setTrains(trains);
//
//        List<HotelSimpleResponse> hotels = Lists.transform(hotelList, new Function<HotelResponse, HotelSimpleResponse>() {
//            @Override
//            public HotelSimpleResponse apply(HotelResponse input) {
//                return new HotelSimpleResponse(input);
//            }
//        });
//        response.setHotels(hotels);
//
//        List<PlanOrderDayResponse> days = Lists.newArrayList();
//        for (Map<String, Object> map : ticketList) {
//            days.add(mapper.readValue(mapper.writeValueAsString(map), PlanOrderDayResponse.class));
//        }
//        response.setDays(days);
//
//        List<CouponResponse> coupons = Lists.transform(userCouponsList, new Function<UserCoupon, CouponResponse>() {
//            @Override
//            public CouponResponse apply(UserCoupon input) {
//                return new CouponResponse(input);
//            }
//        });
//        response.setCoupons(coupons);
//        return response;
//    }

    public void doSaveWebPlan(PlanVo webPlan, Plan plan) {
        plan.setName(webPlan.getName());
        TbArea tbArea = new TbArea();
        tbArea.setId(350200L);
        plan.setCity(tbArea);
        plan.setCityIds("350200");
        plan.setStartTime(DateUtils.getDate(webPlan.getStartDate(), "yyyy-MM-dd"));
        plan.setCreateTime(new Date());
        plan.setPlanDays(webPlan.getDays().size());
        plan.setStatus(1);
        plan.setValid(true);
        plan.setSource(0);
        plan.setPlatform(1);
        plan.setStartCity(tbArea);
        plan.setCompleteFlag(true);
        plan.setPushFlag(0);
        planService.save(plan);
        for (PlanDayVo dayVo : webPlan.getDays()) {
            PlanDay day = new PlanDay();
            day.setPlan(plan);
            day.setCity(tbArea);
            day.setDate(DateUtils.getDate(dayVo.getStartDate(), "yyyy-MM-dd"));
            day.setDays(dayVo.getDay());
            day.setPlayTime(dayVo.getPlayTime());
            day.setCreateTime(new Date());
            day.setModifyTime(new Date());
            // day hotel
            if (dayVo.getHotel() != null) {
                Hotel hotel = new Hotel();
                hotel.setId(dayVo.getHotel().getId());
                day.setHotel(hotel);
                day.setHotelCost(dayVo.getHotel().getPrice());
            }
            // day ferry
            if (dayVo.getFerry() != null) {
                day.setTrafficCost(dayVo.getFerry().getPrice());
            }
            // save plan day
            planDayService.save(day);
            List<ScenicInfoVo> scenicInfoVos = dayVo.getScenics();
            Float scenicCost = 0F;
            if (scenicInfoVos != null) {
                // update plan cover
                plan.setCoverPath(scenicInfoVos.get(0).getCover());
                planService.update(plan);
                for (ScenicInfoVo scenicInfoVo : scenicInfoVos) {
                    PlanTrip planTrip = new PlanTrip();
                    planTrip.setPlan(plan);
                    planTrip.setPlanDay(day);
                    ScenicInfo scenicInfo = new ScenicInfo();
                    scenicInfo.setId(scenicInfoVo.getId());
                    planTrip.setScenicInfo(scenicInfo);
                    planTrip.setTripType(1);
                    planTrip.setCreateTime(new Date());
                    scenicCost += scenicInfoVo.getPrice();
                    // save plan trip
                    planTripService.save(planTrip);
                }
            }
            // update plan day scenic ticket cost
            day.setTicketCost(scenicCost);
            planDayService.update(day);
        }
    }

    public PlanOrderResponse planToOrder(Map<String, Object> data) throws IOException, ParseException {
        PlanOrderResponse response = new PlanOrderResponse();
        response.setPlayDate(data.get("startDate").toString());
        List<Map<String, Object>> requestDays = (List<Map<String, Object>>) data.get("days");
        Date date = DateUtils.parseShortTime(response.getPlayDate());
        Map<Long, List<Map<String, Date>>> hotelMap = Maps.newLinkedHashMap();
        List<TicketScenicResponse> scenics = Lists.newArrayList();
        for (Map<String, Object> requestDay : requestDays) {
            List<Object> priceIds = (List<Object>) requestDay.get("hotels");
            if (priceIds != null) {
                for (Object object : priceIds) {
                    Long priceId = Long.valueOf(object.toString());
                    List<Map<String, Date>> dateMapList = hotelMap.get(priceId);
                    Map<String, Date> dateMap = null;
                    if (dateMapList == null) {
                        dateMapList = Lists.newArrayList();
                    }
                    for (Map<String, Date> map : dateMapList) {
                        if (map.get("endDate").equals(date)) {
                            dateMap = map;
                            break;
                        }
                    }
                    if (dateMap == null) {
                        dateMap = Maps.newHashMap();
                        dateMap.put("startDate", date);
                        dateMapList.add(dateMap);
                    }
                    dateMap.put("endDate", DateUtils.add(date, Calendar.DAY_OF_MONTH, 1));
                    hotelMap.put(priceId, dateMapList);
                }
            }
            List<TicketScenicResponse> scenicResponses = getDayResponse(requestDay, date);
            scenics.addAll(scenicResponses);
            date = DateUtils.add(date, Calendar.DAY_OF_MONTH, 1);
        }
        response.setHotels(getHotels(hotelMap));
        response.setDay(requestDays.size());
        response.setScenics(scenics);
        return response;
    }

    private List<TicketScenicResponse> getDayResponse(Map<String, Object> requestDay, Date date) {
        List<TicketScenicResponse> scenics = Lists.newArrayList();
        Map<String, List<Object>> map = (Map<String, List<Object>>) requestDay.get("scenics");
        List<Long> priceIds = Lists.newArrayList();
        for (List<Object> objects : map.values()) {
            for (Object object : objects) {
                priceIds.add(Long.valueOf(object.toString()));
            }
        }
        if (priceIds.isEmpty()) {
            return scenics;
        }
        List<TicketDateprice> ticketDatepriceList = ticketDatepriceService.getByPriceIdsAndDate(priceIds, date);
        Map<Long, List<TicketDateprice>> ticketMap = Maps.newLinkedHashMap();
        for (TicketDateprice ticketDateprice : ticketDatepriceList) {
            Long scenicId = ticketDateprice.getTicketPriceId().getTicket().getScenicInfo().getId();
            List<TicketDateprice> ticketDateprices = ticketMap.get(scenicId);
            if (ticketDateprices == null) {
                ticketDateprices = Lists.newArrayList();
            }
            ticketDateprices.add(ticketDateprice);
            ticketMap.put(scenicId, ticketDateprices);
        }
        for (List<TicketDateprice> datepriceList : ticketMap.values()) {
            if (datepriceList.isEmpty()) {
                continue;
            }
            TicketScenicResponse scenic = new TicketScenicResponse();
            scenic.setPlayDate(DateUtils.formatShortDate(date));
            scenic.setScenicName(datepriceList.get(0).getTicketPriceId().getTicket().getScenicInfo().getName());
            List<TicketResponse> tickets = Lists.transform(datepriceList, new Function<TicketDateprice, TicketResponse>() {
                @Override
                public TicketResponse apply(TicketDateprice input) {
                    return ticketDatePriceToResponse(input);
                }
            });
            scenic.setTickets(tickets);
            scenics.add(scenic);
        }
        return scenics;
    }

    private TicketResponse ticketDatePriceToResponse(TicketDateprice ticketDateprice) {
        TicketResponse response = new TicketResponse();
        response.setTicketId(ticketDateprice.getTicketPriceId().getTicket().getId());
        response.setTicketName(ticketDateprice.getTicketPriceId().getName());
        response.setPriceId(ticketDateprice.getTicketPriceId().getId());
        response.setPrice(ticketDateprice.getPriPrice());
        return response;
    }

    private List<PlanOrderHotelResponse> getHotels(Map<Long, List<Map<String, Date>>> hotelMap) {
        List<PlanOrderHotelResponse> responses = Lists.newArrayList();
        Map<String, PlanOrderHotelResponse> hotelResponseMap = Maps.newHashMap();
        for (Long priceId : hotelMap.keySet()) {
            List<Map<String, Date>> dateMapList = hotelMap.get(priceId);
            for (Map<String, Date> dateMap : dateMapList) {
                Date startDate = dateMap.get("startDate");
                Date endDate = dateMap.get("endDate");
                Integer day = DateUtils.getDateDiff(startDate, endDate);
                List<HotelPriceCalendar> hotelPriceCalendars = hotelPriceService.findTypePriceDate(priceId, startDate, endDate);
                if (hotelPriceCalendars.size() < day) {
                    continue;
                }
                HotelPriceCalendar calendar = hotelPriceCalendars.get(0);

                String key = calendar.getHotelId() + "_" + DateUtils.formatShortDate(startDate) + "_" + DateUtils.formatShortDate(endDate);
                PlanOrderHotelResponse planOrderHotelResponse = hotelResponseMap.get(key);
                if (planOrderHotelResponse == null) {
                    planOrderHotelResponse = new PlanOrderHotelResponse(calendar.getHotelPrice());
                }

                HotelPriceResponse hotelPriceResponse = new HotelPriceResponse(calendar.getHotelPrice());
                Float price = 0f;
                for (HotelPriceCalendar hotelPriceCalendar : hotelPriceCalendars) {
                    price += (hotelPriceCalendar.getMember());
                }
                hotelPriceResponse.setPrice(price);
                hotelPriceResponse.setStartDate(DateUtils.formatShortDate(startDate));
                hotelPriceResponse.setEndDate(DateUtils.formatShortDate(endDate));
                hotelPriceResponse.setDay(day);

                List<HotelPriceResponse> hotelPriceResponseList = planOrderHotelResponse.getHotelPrices();
                hotelPriceResponseList.add(hotelPriceResponse);
                planOrderHotelResponse.setHotelPrices(hotelPriceResponseList);
                try {
                    if (StringUtils.isBlank(planOrderHotelResponse.getStartDate()) || startDate.before(DateUtils.parseShortTime(planOrderHotelResponse.getStartDate()))) {
                        planOrderHotelResponse.setStartDate(hotelPriceResponse.getStartDate());
                    }
                    if (StringUtils.isBlank(planOrderHotelResponse.getEndDate()) || endDate.after(DateUtils.parseShortTime(planOrderHotelResponse.getEndDate()))) {
                        planOrderHotelResponse.setEndDate(hotelPriceResponse.getEndDate());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                hotelResponseMap.put(key, planOrderHotelResponse);
            }
        }
        for (PlanOrderHotelResponse planOrderHotelResponse : hotelResponseMap.values()) {
            responses.add(planOrderHotelResponse);
        }
        Collections.sort(responses, new Comparator<PlanOrderHotelResponse>() {
            @Override
            public int compare(PlanOrderHotelResponse o1, PlanOrderHotelResponse o2) {
                return DateUtils.getDateDiff(o2.getStartDate(), o1.getStartDate(), "yyyy-MM-dd");
            }
        });
        return responses;
    }

    public Map<String, Object> collectPlanBookingData(Plan plan) {
        Map<String, Object> map = Maps.newHashMap();
        StringBuilder builder = new StringBuilder();
        Map<Long, Map<String, Object>> hotelMap = Maps.newLinkedHashMap();
        for (PlanDay planDay : plan.getPlanDayList()) {
            if (planDay.getTrafficPriceId() != null) {
                if (builder.length() > 0) {
                    builder.append(",");
                }
                builder.append(planDay.getTrafficPriceId());
            }
            if (planDay.getReturnTrafficPriceId() != null) {
                if (builder.length() > 0) {
                    builder.append(",");
                }
                builder.append(planDay.getReturnTrafficPriceId());
            }
            if (planDay.getHotel() == null) {
                continue;
            }
            Map<String, Object> hotel = hotelMap.get(planDay.getHotel().getId());
            if (hotel == null) {
                hotel = Maps.newHashMap();
                hotel.put("name", planDay.getHotel().getName());
                hotel.put("hotelId", planDay.getHotel().getId());
                hotel.put("priceId", planDay.getHotelCode());
                HotelPrice hotelPrice = hotelPriceService.get(Long.valueOf(planDay.getHotelCode()));
                hotel.put("payType", PriceStatus.UP.equals(hotelPrice.getStatus()) ? "到付" : "担保");
                hotel.put("startDate", DateUtils.getDate(DateUtils.add(planDay.getDate(), Calendar.DAY_OF_MONTH, -1)));
            }

            hotel.put("leaveDate", DateUtils.getDate(DateUtils.add(planDay.getDate(), Calendar.DAY_OF_MONTH, 1)));
            hotelMap.put(planDay.getHotel().getId(), hotel);
        }
        List<Map<String, Object>> hotelList = Lists.newArrayList(hotelMap.values());
        map.put("traffic", builder.toString());
        map.put("hotel", hotelList);
        return map;
    }

    public PlanResponse planToResponse(Plan plan) {
        PlanResponse response = new PlanResponse(plan);
        List<PlanDayResponse> days = Lists.transform(plan.getPlanDayList(), new Function<PlanDay, PlanDayResponse>() {
            @Override
            public PlanDayResponse apply(PlanDay input) {
                return dayToResponse(input);
            }
        });
        response.setDays(days);
        return response;
    }

    public PlanDayResponse dayToResponse(PlanDay planDay) {
        PlanDayResponse dayResponse = new PlanDayResponse(planDay);
        if (planDay.getTraffic() != null) {
            PlanTrafficResponse traffic = new PlanTrafficResponse(planDay.getTraffic());
            traffic.setPriceId(planDay.getTrafficPriceId());
            traffic.setPrice(planDay.getTrafficCost());
            dayResponse.setTraffic(traffic);
        }
        if (planDay.getReturnTraffic() != null) {
            PlanTrafficResponse returnTraffic = new PlanTrafficResponse(planDay.getReturnTraffic());
            returnTraffic.setPriceId(planDay.getReturnTrafficPriceId());
            returnTraffic.setPrice(planDay.getReturnTrafficCost());
            dayResponse.setReturnTraffic(returnTraffic);
        }
        if (planDay.getHotel() != null) {
            PlanHotelResponse hotel = new PlanHotelResponse(planDay.getHotel());
            hotel.setPriceId(planDay.getHotelCode());
            hotel.setPrice(planDay.getHotelCost());
            HotelPrice hotelPrice = hotelPriceService.get(Long.valueOf(hotel.getPriceId()));
            hotel.setPriceName(hotelPrice.getRoomName());
            dayResponse.setHotel(hotel);
        }
        List<PlanTripResponse> trips = Lists.transform(planDay.getPlanTripList(), new Function<PlanTrip, PlanTripResponse>() {
            @Override
            public PlanTripResponse apply(PlanTrip input) {
                return new PlanTripResponse(input);
            }
        });
        dayResponse.setTrips(trips);
        return dayResponse;
    }

    public OptimizeDetailResponse optimize(PlanOptimizeRequest planOptimizeRequest, String startDate, Boolean needHotel, HotelSearchRequest hotelSearchRequest) {
        PlanOptimizeResponse planOptimizeResponse = planOptimizeService.optimize(planOptimizeRequest);
        OptimizeDetailResponse response = new OptimizeDetailResponse();
        Map<Integer, List<Long>> map = Maps.newHashMap();
        List<Long> allNodeIds = Lists.newArrayList();
        for (PlanOptimizeDayResponse planOptimizeDayResponse : planOptimizeResponse.data) {
            List<Long> ids = Lists.transform(planOptimizeDayResponse.tripList, new Function<TripNode, Long>() {
                @Override
                public Long apply(TripNode input) {
                    return input.id;
                }
            });
            map.put(planOptimizeDayResponse.day, ids);
            allNodeIds.addAll(ids);
        }
        List<ScenicSolrEntity> scenicSolrEntities = scenicInfoService.listScenicFromSolr(allNodeIds);
        Map<Long, ScenicSolrEntity> scenicSolrEntityMap = Maps.uniqueIndex(scenicSolrEntities, new Function<ScenicSolrEntity, Long>() {
            @Override
            public Long apply(ScenicSolrEntity input) {
                return input.getId();
            }
        });
        try {
            Date date = DateUtils.parseShortTime(startDate);
            Float totalPrice = 0f;
            Integer scenicNum = 0;
            Boolean hasShip = false;
            List<OptimizeDetailDayResponse> days = Lists.newArrayList();
            for (Integer day : map.keySet()) {
                OptimizeDetailDayResponse dayResponse = new OptimizeDetailDayResponse();
                dayResponse.setDay(day);
                Date start = DateUtils.add(date, Calendar.DAY_OF_MONTH, day - 1);
                dayResponse.setDate(start);
                dayResponse.setStartDate(DateUtils.formatShortDate(start));
                dayResponse.setEndDate(DateUtils.formatShortDate(DateUtils.add(start, Calendar.DAY_OF_MONTH, 1)));
                dayResponse.setInIsland(true);
                dayResponse.setNeedShip(false);
                List<ScenicSolrEntity> scenics = Lists.newArrayList();
                Float price = 0f;
                Float playTime = 0f;
                for (Long id : map.get(day)) {
                    ScenicSolrEntity entity = scenicSolrEntityMap.get(id);
                    entity.setCover(cover(entity.getCover()));
                    price += entity.getPrice();
                    playTime += entity.getAdviceMinute() / 60f;
                    scenics.add(entity);
                    if (entity.getRegion() != null && entity.getRegion().contains("鼓浪屿")) {
                        dayResponse.setInIsland(false);
                    }
                    if (!hasShip && !dayResponse.getInIsland()) {
                        dayResponse.setNeedShip(true);
                        dayResponse.setFerry(ferryMobileService.getOptimizeDailyFlight(dayResponse.getStartDate()));
                        hasShip = true;
                    }
                }
                dayResponse.setScenics(scenics);
                dayResponse.setPrice(price);
                dayResponse.setPlayTime(Math.round(playTime * 100) / 100f);
                dayResponse.setCoreScenic(getCoreScenicId(scenics));
                days.add(dayResponse);
                totalPrice += price;
                scenicNum += scenics.size();
            }
            response.setPrice(totalPrice);
            response.setScenicNum(scenicNum);
            response.setDays(days);
            response.setStartDate(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (needHotel) {
            optimizeHotel(response, hotelSearchRequest);
        }
        return response;
    }

    private Long getCoreScenicId(List<ScenicSolrEntity> scenics) {
        if (scenics.isEmpty()) {
            return 0l;
        }
        Collections.sort(scenics, new Comparator<ScenicSolrEntity>() {
            @Override
            public int compare(ScenicSolrEntity o1, ScenicSolrEntity o2) {
                return o1.getRanking() - o2.getRanking();
            }
        });
        return scenics.get(0).getId();
    }

    public void optimizeHotel(OptimizeDetailResponse response, HotelSearchRequest hotelSearchRequest) {
        Collections.sort(response.getDays(), new Comparator<OptimizeDetailDayResponse>() {
            @Override
            public int compare(OptimizeDetailDayResponse o1, OptimizeDetailDayResponse o2) {
                return o1.getDay() - o2.getDay();
            }
        });
        Map<String, Map<String, Object>> startDateMap = Maps.newHashMap();
        List<ScenicSolrEntity> inIslandScenic = Lists.newArrayList();
        List<ScenicSolrEntity> outIslandScenic = Lists.newArrayList();
        for (OptimizeDetailDayResponse dayResponse : response.getDays()) {
            String key;
            if (dayResponse.getInIsland()) {
                key = "inIsland";
                inIslandScenic.addAll(dayResponse.getScenics());
            } else {
                key = "outIsland";
                outIslandScenic.addAll(dayResponse.getScenics());
            }
            Map<String, Object> dateMap = startDateMap.get(key);
            if (dateMap == null) {
                dateMap = Maps.newHashMap();
            }
            if (dateMap.get("startDate") == null) {
                dateMap.put("startDate", dayResponse.getDate());
                dateMap.put("endDate", DateUtils.add(dayResponse.getDate(), Calendar.DAY_OF_MONTH, 1));
            } else {
                dateMap.put("endDate", DateUtils.add(dayResponse.getDate(), Calendar.DAY_OF_MONTH, 1));
            }
            List<OptimizeDetailDayResponse> dayList = (List<OptimizeDetailDayResponse>) dateMap.get("dayList");
            if (dayList == null) {
                dayList = Lists.newArrayList();
            }
            dayList.add(dayResponse);
            dateMap.put("dayList", dayList);
            startDateMap.put(key, dateMap);
        }
        CountDownLatch down = new CountDownLatch(startDateMap.size());
        if (!inIslandScenic.isEmpty()) {
            Map<String, Object> inIsland = startDateMap.get("inIsland");
            inIsland.put("coreScenic", getCoreScenic(inIslandScenic));
            findHotel(inIsland, down, hotelSearchRequest);
        }
        if (!outIslandScenic.isEmpty()) {
            Map<String, Object> outIsland = startDateMap.get("outIsland");
            outIsland.put("coreScenic", getCoreScenic(outIslandScenic));
            findHotel(outIsland, down, hotelSearchRequest);
        }
        try {
            down.await();
        } catch (InterruptedException e) {

        }
    }

    public ScenicInfo getCoreScenic(List<ScenicSolrEntity> scenicSolrEntities) {
        Collections.sort(scenicSolrEntities, new Comparator<ScenicSolrEntity>() {
            @Override
            public int compare(ScenicSolrEntity o1, ScenicSolrEntity o2) {
                return o1.getRanking() - o2.getRanking();
            }
        });
        return scenicInfoService.get(scenicSolrEntities.get(0).getId());
    }

    public void findHotel(final Map<String, Object> map, final CountDownLatch down, final HotelSearchRequest hotelSearchRequest) {

        GlobalTheadPool.instance.submit(new Callable<Object>() {

            @Override
            public Object call() throws Exception {
                return fillHotelTask(map, down, hotelSearchRequest);
            }
        });
    }

    private Object fillHotelTask(Map<String, Object> map, CountDownLatch down, HotelSearchRequest hotelSearchRequest) {
        SessionFactory sessionFactory = SpringContextHolder.getBean("sessionFactory");
        boolean participate = ConcurrentUtil.bindHibernateSessionToThread(sessionFactory);
        try {
            StopWatch watch = new StopWatch();
            watch.start();
//            StringBuilder builder = new StringBuilder();
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(DateUtils.parseShortTime(response.getStartDate()));
            hotelSearchRequest.setCityIds(Lists.newArrayList(Long.valueOf(cityId)));
            hotelSearchRequest.setStartDate(DateUtils.formatShortDate((Date) map.get("startDate")));
            hotelSearchRequest.setEndDate(DateUtils.formatShortDate((Date) map.get("endDate")));
//            builder.append("cityId:").append(cityId).append(" AND start:[* TO ").append(DateUtils.formatShortDate((Date) map.get("startDate"))).append("T00:00:00Z] AND end:[").append(DateUtils.formatShortDate((Date) map.get("endDate"))).append("T23:59:59Z TO *]");
            ScenicInfo coreScenic = (ScenicInfo) map.get("coreScenic");
            List<HotelSolrEntity> hotelSolrEntities = hotelService.findNearByHotel(hotelSearchRequest.getSolrQueryStr(), coreScenic.getScenicGeoinfo().getBaiduLng(), coreScenic.getScenicGeoinfo().getBaiduLat(), 50f, null);
            if (hotelSolrEntities.isEmpty()) {
                return null;
            }
            OptimizeDetailHotelResponse hotel = new OptimizeDetailHotelResponse();
            for (HotelSolrEntity hotelSolrEntity : hotelSolrEntities) {
                hotel = fillHotel(hotelSolrEntity.getId(), (Date) map.get("startDate"), (Date) map.get("endDate"));
                if (hotel.getId() != null && hotel.getId() > 0) {
                    break;
                }
            }
            List<OptimizeDetailDayResponse> dayList = (List<OptimizeDetailDayResponse>) map.get("dayList");
            for (OptimizeDetailDayResponse dayResponse : dayList) {
                dayResponse.setHotel(hotel);
            }
            watch.stop();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            down.countDown();
            ConcurrentUtil.closeHibernateSessionFromThread(participate, sessionFactory);
        }
        return null;
    }

    private OptimizeDetailHotelResponse fillHotel(Long hotelId, Date enterDate, Date leaveDate) {
        HotelPrice priceFilter = new HotelPrice();
        Hotel hotelFilter = new Hotel();
        hotelFilter.setId(hotelId);
        priceFilter.setHotel(hotelFilter);
        priceFilter.setStart(enterDate);
        priceFilter.setEnd(leaveDate);
        priceFilter.setStatus(PriceStatus.UP);
        List<HotelPrice> hotelPrice = hotelPriceService.list(priceFilter, null, new Page(1, 1), "price", "asc");
        OptimizeDetailHotelResponse response = new OptimizeDetailHotelResponse();
        if (!hotelPrice.isEmpty()) {
            response = new OptimizeDetailHotelResponse(hotelPrice.get(0));
            Productimage productimage = productimageService.findCover(response.getId(), null, ProductType.hotel);
            if (productimage != null) {
                response.setCover(productimage.getPath());
            }
            response.setPrice(hotelPrice.get(0).getPrice());
        }
        response.setStartDate(DateUtils.formatShortDate(enterDate));
        response.setEndDate(DateUtils.formatShortDate(leaveDate));
        return response;
    }

    public TripNode scenicEntityToNode(ScenicSolrEntity scenicSolrEntity) {
        TripNode tripNode = new TripNode();
        tripNode.name = scenicSolrEntity.getName();
        tripNode.cover = tripNode.cover(scenicSolrEntity.getCover());
        tripNode.score = scenicSolrEntity.getScore();
        tripNode.price = String.valueOf(scenicSolrEntity.getPrice());
        tripNode.ranking = scenicSolrEntity.getRanking();
        tripNode.advice = scenicSolrEntity.getAdviceTime();
        tripNode.lng = scenicSolrEntity.getLongitude();
        tripNode.lat = scenicSolrEntity.getLatitude();
        tripNode.address = scenicSolrEntity.getAddress();
        tripNode.shortIntro = scenicSolrEntity.getShortComment();
        tripNode.cityId = scenicSolrEntity.getCityId().intValue();
        return tripNode;
    }

    private String cover(String cover) {
        if (StringUtils.isBlank(cover)) {
            return QiniuUtil.URL + "jingdian.png";
        } else {
            if (cover.startsWith("http")) {
                return cover;
            } else {
                return QiniuUtil.URL + cover;
            }
        }
    }

    public OptimizeDetailResponse planDetail(Plan plan) {
        OptimizeDetailResponse response = new OptimizeDetailResponse();
        response.setName(plan.getName());
        List<OptimizeDetailDayResponse> days = Lists.newArrayList();
        Float price = 0f;
        Integer scneicNum = 0;
        for (PlanDay planDay : plan.getPlanDayList()) {
            OptimizeDetailDayResponse dayResponse = new OptimizeDetailDayResponse();
            dayResponse.setDay(planDay.getDays());
            dayResponse.setPlayTime(planDay.getPlayTime().floatValue());
            dayResponse.setStartDate(DateUtils.formatShortDate(planDay.getDate()));
            List<ScenicSolrEntity> entities = Lists.newArrayList();
            Float dayPrice = 0f;
            for (PlanTrip planTrip : planDay.getPlanTripList()) {
                ScenicInfo scenicInfo = planTrip.getScenicInfo();
                if (scenicInfo != null) {
                    ScenicSolrEntity entity = new ScenicSolrEntity(scenicInfo);
                    entity.setPrice(scenicInfo.getPrice());
                    entities.add(entity);
                    dayPrice += scenicInfo.getPrice();
                }
            }
            price += dayPrice;
            scneicNum += entities.size();
            dayResponse.setPrice(dayPrice);
            dayResponse.setScenics(entities);
            days.add(dayResponse);
        }
        response.setPrice(price);
        response.setScenicNum(scneicNum);
        response.setDays(days);
        return response;
    }
}
