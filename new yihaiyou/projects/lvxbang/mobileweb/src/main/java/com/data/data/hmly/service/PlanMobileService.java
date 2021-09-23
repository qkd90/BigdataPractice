package com.data.data.hmly.service;

import com.data.data.hmly.action.mobile.response.*;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.hotel.HotelPriceService;
import com.data.data.hmly.service.hotel.entity.HotelPrice;
import com.data.data.hmly.service.hotel.entity.enums.PriceStatus;
import com.data.data.hmly.service.lvxbang.DiscountService;
import com.data.data.hmly.service.lvxbang.PlanBookingService;
import com.data.data.hmly.service.lvxbang.PlanOrderService;
import com.data.data.hmly.service.lvxbang.response.HotelResponse;
import com.data.data.hmly.service.lvxbang.response.TrafficResponse;
import com.data.data.hmly.service.lxbcommon.entity.UserCoupon;
import com.data.data.hmly.service.plan.entity.Plan;
import com.data.data.hmly.service.plan.entity.PlanDay;
import com.data.data.hmly.service.plan.entity.PlanTrip;
import com.data.data.hmly.service.traffic.entity.TrafficPrice;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by huangpeijie on 2016-04-26,0026.
 */
@Service
public class PlanMobileService {
    @Resource
    private PlanOrderService planOrderService;
    @Resource
    private DiscountService discountService;
    @Resource
    private HotelPriceService hotelPriceService;
    @Resource
    private PlanBookingService planBookingService;

    private final ObjectMapper mapper = new ObjectMapper();

    public PlanOrderResponse planToOrder(Plan plan) throws IOException, ParseException {
        Map<String, Object> dataMap = collectPlanBookingData(plan);
        List<TrafficResponse> planeList = Lists.newArrayList();
        List<TrafficResponse> trainList = Lists.newArrayList();
        List<TrafficResponse> shipList = Lists.newArrayList();
        List<HotelResponse> hotelList = Lists.newArrayList();
        List<Map<String, Object>> ticketList = Lists.newArrayList();
        planOrderService.prepareTraffic(dataMap, planeList, trainList, shipList);
        planOrderService.prepareHotel(dataMap, hotelList);
        planOrderService.prepareTicket(plan, ticketList);
        List<UserCoupon> userCouponsList = discountService.listUserCoupon(ProductType.plan, plan.getUser());

        PlanOrderResponse response = new PlanOrderResponse();
        response.setName(plan.getName());
        response.setDay(plan.getPlanDays());
        response.setPlayDate(DateUtils.formatShortDate(plan.getStartTime()));

        List<PlanOrderTrafficResponse> planes = Lists.transform(planeList, new Function<TrafficResponse, PlanOrderTrafficResponse>() {
            @Override
            public PlanOrderTrafficResponse apply(TrafficResponse input) {
                return new PlanOrderTrafficResponse(input);
            }
        });
        response.setPlanes(planes);

        List<PlanOrderTrafficResponse> trains = Lists.transform(trainList, new Function<TrafficResponse, PlanOrderTrafficResponse>() {
            @Override
            public PlanOrderTrafficResponse apply(TrafficResponse input) {
                return new PlanOrderTrafficResponse(input);
            }
        });
        response.setTrains(trains);

        List<HotelSimpleResponse> hotels = Lists.transform(hotelList, new Function<HotelResponse, HotelSimpleResponse>() {
            @Override
            public HotelSimpleResponse apply(HotelResponse input) {
                return new HotelSimpleResponse(input);
            }
        });
        response.setHotels(hotels);

        List<PlanOrderDayResponse> days = Lists.newArrayList();
        for (Map<String, Object> map : ticketList) {
            days.add(mapper.readValue(mapper.writeValueAsString(map), PlanOrderDayResponse.class));
        }
        response.setDays(days);

        List<CouponResponse> coupons = Lists.transform(userCouponsList, new Function<UserCoupon, CouponResponse>() {
            @Override
            public CouponResponse apply(UserCoupon input) {
                return new CouponResponse(input);
            }
        });
        response.setCoupons(coupons);
        return response;
    }

    public PlanOrderResponse planToOrder(Map<String, Object> data, Member user) throws IOException, ParseException {
        List<TrafficResponse> planeList = Lists.newArrayList();
        List<TrafficResponse> trainList = Lists.newArrayList();
        List<TrafficResponse> shipList = Lists.newArrayList();
        List<HotelResponse> hotelList = Lists.newArrayList();
        List<Map<String, Object>> ticketList = Lists.newArrayList();
        List<Map<String, Object>> traffics = (List<Map<String, Object>>) data.get("traffic");
        StringBuilder builder = new StringBuilder();
        for (Map<String, Object> map : traffics) {
            TrafficPrice trafficPrice = planBookingService.saveTraffic(map);
            if (builder.length() > 0) {
                builder.append(",");
            }
            builder.append(trafficPrice.getId());
        }
        Map<String, Object> dataMap = Maps.newHashMap();
        dataMap.put("traffic", builder.toString());
        dataMap.put("hotel", data.get("hotel"));
        List<Map<String, Object>> plan = (List<Map<String, Object>>) data.get("plan");
        planOrderService.prepareTraffic(dataMap, planeList, trainList, shipList);
        planOrderService.prepareHotel(dataMap, hotelList);
        planOrderService.prepareTicket(plan, ticketList, DateUtils.parseShortTime((String) data.get("playDate")));
        List<UserCoupon> userCouponsList = discountService.listUserCoupon(ProductType.plan, user);

        PlanOrderResponse response = new PlanOrderResponse();
        response.setName((String) data.get("name"));
        response.setDay(plan.size());
        response.setPlayDate((String) data.get("playDate"));

        List<PlanOrderTrafficResponse> planes = Lists.transform(planeList, new Function<TrafficResponse, PlanOrderTrafficResponse>() {
            @Override
            public PlanOrderTrafficResponse apply(TrafficResponse input) {
                return new PlanOrderTrafficResponse(input);
            }
        });
        response.setPlanes(planes);

        List<PlanOrderTrafficResponse> trains = Lists.transform(trainList, new Function<TrafficResponse, PlanOrderTrafficResponse>() {
            @Override
            public PlanOrderTrafficResponse apply(TrafficResponse input) {
                return new PlanOrderTrafficResponse(input);
            }
        });
        response.setTrains(trains);

        List<HotelSimpleResponse> hotels = Lists.transform(hotelList, new Function<HotelResponse, HotelSimpleResponse>() {
            @Override
            public HotelSimpleResponse apply(HotelResponse input) {
                return new HotelSimpleResponse(input);
            }
        });
        response.setHotels(hotels);

        List<PlanOrderDayResponse> days = Lists.newArrayList();
        for (Map<String, Object> map : ticketList) {
            days.add(mapper.readValue(mapper.writeValueAsString(map), PlanOrderDayResponse.class));
        }
        response.setDays(days);

        List<CouponResponse> coupons = Lists.transform(userCouponsList, new Function<UserCoupon, CouponResponse>() {
            @Override
            public CouponResponse apply(UserCoupon input) {
                return new CouponResponse(input);
            }
        });
        response.setCoupons(coupons);
        return response;
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
}
