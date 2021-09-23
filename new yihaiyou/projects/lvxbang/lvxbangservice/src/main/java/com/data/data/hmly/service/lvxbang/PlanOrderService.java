package com.data.data.hmly.service.lvxbang;

import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.hotel.HotelPriceService;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.hotel.entity.HotelPrice;
import com.data.data.hmly.service.lvxbang.response.HotelResponse;
import com.data.data.hmly.service.lvxbang.response.TicketResponse;
import com.data.data.hmly.service.lvxbang.response.TrafficResponse;
import com.data.data.hmly.service.plan.entity.Plan;
import com.data.data.hmly.service.plan.entity.PlanDay;
import com.data.data.hmly.service.plan.entity.PlanTrip;
import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.data.hmly.service.ticket.TicketDatepriceService;
import com.data.data.hmly.service.ticket.TicketService;
import com.data.data.hmly.service.ticket.entity.TicketDateprice;
import com.data.data.hmly.service.traffic.TrafficPriceService;
import com.data.data.hmly.service.traffic.entity.Traffic;
import com.data.data.hmly.service.traffic.entity.TrafficPrice;
import com.data.data.hmly.service.traffic.entity.enums.TrafficType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zuipin.util.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by vacuity on 16/1/7.
 */

@Service
public class PlanOrderService {

    private final Logger logger = Logger.getLogger(PlanOrderService.class);

    @Resource
    private TrafficPriceService trafficPriceService;
    @Resource
    private HotelPriceService hotelPriceService;
    @Resource
    private TicketDatepriceService ticketDatepriceService;
    @Resource
    private TicketService ticketService;
    @Resource
    private ScenicInfoService scenicInfoService;

    public List<HotelResponse> getHotelList(Plan plan) {
        //
        List<PlanDay> planDayList = plan.getPlanDayList();
        //
        List<HotelResponse> hotelResponseList = new ArrayList<HotelResponse>();
        Long lastHotelId = -1L;
        int size = -1;
        for (PlanDay planDay : planDayList) {
            //
            Hotel hotel = planDay.getHotel();
            // 当前天没有酒店
            if (hotel == null) {
                lastHotelId = -1L;
            } else {
                // 今天和昨天同一家酒店
                if (hotel.getId() == lastHotelId) {
                    // 更新酒店天数
                    Integer nowDays = hotelResponseList.get(size).getDays();
                    hotelResponseList.get(size).setDays(nowDays + 1);
                } else {
                    // 新的酒店，设置天数为0，开始时间为当前天
                    HotelResponse hotelResponse = new HotelResponse();
                    hotelResponse.setId(hotel.getId());
                    hotelResponse.setDays(1);
                    hotelResponse.setStartDays(planDay.getDays());
                    hotelResponseList.add(hotelResponse);
                    size++;
                }

            }
        }
        return hotelResponseList;
    }

    // 处理行程中的交通订单
    public void getPlaneList(Plan plan, List<TrafficResponse> planeResponseList, List<TrafficResponse> trainResponseList, List<TrafficResponse> shipResponseList) {
        //
        Date startDate = plan.getStartTime();
        List<PlanDay> planDayList = plan.getPlanDayList();
        for (PlanDay planDay : planDayList) {
            //
            Traffic traffic = planDay.getTraffic();
            if (traffic != null) {
                //
                TrafficResponse trafficResponse = new TrafficResponse();
                trafficResponse.setId(traffic.getId());
                trafficResponse.setDate(afterDays(startDate, planDay.getDays() - 1));
                trafficResponse.setTrafficType(traffic.getTrafficType());
                trafficResponse.setCompany(traffic.getCompany());
                trafficResponse.setTrafficCode(traffic.getTrafficCode());
                trafficResponse.setLeaveTime(traffic.getLeaveTime());
                trafficResponse.setLeavePort(traffic.getLeaveTransportation().getName());
                trafficResponse.setTrafficTime(timeToString(traffic.getFlightTime()));
                trafficResponse.setArriveTime(traffic.getArriveTime());
                trafficResponse.setArrivePort(traffic.getArriveTransportation().getName());
                // priceId seatType price
                List<TrafficPrice> trafficPriceList = trafficPriceService.getForPlanOrder(trafficResponse.getDate(), traffic.getId());
                if (!trafficPriceList.isEmpty()) {
                    //
                    TrafficPrice trafficPrice = trafficPriceList.get(0);
                    trafficResponse.setPriceId(trafficPrice.getId());
                    trafficResponse.setSeatType(trafficPrice.getSeatName());
                    trafficResponse.setPrice(trafficPrice.getPrice());
                } else {
                    trafficResponse.setPriceId(-1L);
                    trafficResponse.setSeatType("");
                    trafficResponse.setPrice(0F);
                }

                switch (traffic.getTrafficType()) {
                    case AIRPLANE:
                        planeResponseList.add(trafficResponse);
                        break;
                    case TRAIN:
                        trainResponseList.add(trafficResponse);
                        break;
                    case SHIP:
                        shipResponseList.add(trafficResponse);
                        break;
                    default:
                        break;
                }
            }
        }
    }


    public void processHotelResponse(List<HotelResponse> hotelResponseList, Date startDate) {
        // 处理每一个酒店的开始日期和结束日期
        for (HotelResponse hotelResponse : hotelResponseList) {
            //
            hotelResponse.setStartDate(afterDays(startDate, hotelResponse.getStartDays() - 1));
            hotelResponse.setLeaveDate(afterDays(hotelResponse.getStartDate(), hotelResponse.getDays() - 1));
            List<HotelPrice> hotelPriceList = hotelPriceService.getForPlanOrder(hotelResponse.getStartDate(), hotelResponse.getLeaveDate(), hotelResponse.getId(), hotelResponse.getDays());
            if (!hotelResponseList.isEmpty()) {
                //
                HotelPrice hotelPrice = hotelPriceList.get(0);
                hotelResponse.setPrice(hotelPrice.getPrice());
                hotelResponse.setRoomDescription(hotelPrice.getRoomDescription());
                hotelResponse.setBreakfast(hotelPrice.getBreakfast());
            } else {
                hotelResponse.setPrice(0F);
                hotelResponse.setRoomDescription("暂无房型信息");
                hotelResponse.setBreakfast(false);
            }
        }
    }


    /**
     * 根据自由行页面填写的信息结合行程信息确定订单填写页面信息
     * @param map
     * @param planeResponseList
     * @param trainResponseList
     * @param shipResponseList
     */
    public void prepareTraffic(Map<String, Object> map, List<TrafficResponse> planeResponseList,
                            List<TrafficResponse> trainResponseList, List<TrafficResponse> shipResponseList) {
        if (map.get("traffic") != null && StringUtils.isNotBlank(map.get("traffic").toString())) {
            // 所有交通价格id
            String traffidPriceIdsString = map.get("traffic").toString();
            List<TrafficPrice> trafficPriceList = trafficPriceService.getByIds(traffidPriceIdsString);

            for (TrafficPrice trafficPrice : trafficPriceList) {

                TrafficResponse trafficResponse = trafficPriceToResponse(trafficPrice);
                // 根据不同交通类型加入不同列表
                switch (trafficPrice.getTraffic().getTrafficType()) {
                    case AIRPLANE:
                        planeResponseList.add(trafficResponse);
                        break;
                    case TRAIN:
                        trainResponseList.add(trafficResponse);
                        break;
                    case SHIP:
                        shipResponseList.add(trafficResponse);
                        break;
                    default:
                        break;
                }
            }
        }


    }

    //
    public void prepareHotel(Map<String, Object> map, List<HotelResponse> hotelResponseList) {
        if (map.get("hotel") != null) {
            // 酒店信息列表
            List<Map<String, Object>> hotelMapList = (List<Map<String, Object>>) map.get("hotel");
            for (Map<String, Object> hotelMap : hotelMapList) {
                // 对每一个酒店进行查询
                HotelResponse hotelResponse = mapToHotel(hotelMap);
                hotelResponseList.add(hotelResponse);
            }
        }
    }


    public TrafficResponse trafficPriceToResponse(TrafficPrice trafficPrice) {
        // 从交通类型数据转化为页面需要的数据
        Traffic traffic = trafficPrice.getTraffic();
        TrafficType trafficType = traffic.getTrafficType();
        TrafficResponse trafficResponse = new TrafficResponse();
        trafficResponse.setId(traffic.getId());
        trafficResponse.setDate(trafficPrice.getDate());
        trafficResponse.setTrafficType(trafficType);
        trafficResponse.setCompany(traffic.getCompany());
        trafficResponse.setTrafficCode(traffic.getTrafficCode());
        trafficResponse.setLeaveTime(traffic.getLeaveTime());
        trafficResponse.setLeavePort(traffic.getLeaveTransportation().getName());
        trafficResponse.setTrafficTime(timeToString(traffic.getFlightTime()));
        trafficResponse.setArriveTime(traffic.getArriveTime());
        trafficResponse.setArrivePort(traffic.getArriveTransportation().getName());

        trafficResponse.setLeaveCity(processCityName(traffic.getLeaveCity().getName()));
        trafficResponse.setArriveCity(processCityName(traffic.getArriveCity().getName()));
        trafficResponse.setAdditionalFuelTax(trafficPrice.getAdditionalFuelTax());
        trafficResponse.setAirportBuildFee(trafficPrice.getAirportBuildFee());
        // 与价格相关的数据
        trafficResponse.setPriceId(trafficPrice.getId());
        trafficResponse.setSeatType(trafficPrice.getSeatName());
        trafficResponse.setPrice(trafficPrice.getPrice());
        return trafficResponse;
    }

    // 处理酒店数据
    private HotelResponse mapToHotel(Map<String, Object> hotelMap) {
        //
//        private Long id;
//        private String name;
//        private String roomDescription;
//        private Boolean breakfast;
//        private Date startDate;
//        private Date leaveDate;
//        private Float price;
//        private Float totalPrice;
//
//        private Integer startDays;
//        private Integer days;
//
//        private Long ratePlanCode;

        HotelResponse hotelResponse = new HotelResponse();
        Long hotelId = Long.parseLong(hotelMap.get("hotelId").toString());
        String name = hotelMap.get("name").toString();
        Long priceId = Long.parseLong(hotelMap.get("priceId").toString());
        Date startDate = formatDate(hotelMap.get("startDate").toString());
        Date leaveDate = formatDate(hotelMap.get("leaveDate").toString());
        String payType = "";
        if (hotelMap.get("payType") != null) {
            payType = hotelMap.get("payType").toString();
        }
        Long days = (leaveDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);

        hotelResponse.setId(hotelId);
        hotelResponse.setPriceId(priceId);
        hotelResponse.setStartDate(startDate);
        hotelResponse.setLeaveDate(leaveDate);
        hotelResponse.setDays(days.intValue());
        hotelResponse.setName(name);
        hotelResponse.setPayType(payType);

        HotelPrice hotelPrice = hotelPriceService.get(priceId);
        if (hotelPrice == null) {
            hotelResponse.setRoomDescription("暂无信息");
            hotelResponse.setBreakfast(false);
            hotelResponse.setPrice(0F);
            hotelResponse.setTotalPrice(0F);
        } else {
            hotelResponse.setRoomDescription(hotelPrice.getRoomDescription());
            hotelResponse.setBreakfast(hotelPrice.getBreakfast());
            hotelResponse.setPrice(hotelPrice.getPrice());
            hotelResponse.setTotalPrice(hotelPrice.getPrice() * days);
            hotelResponse.setSource(String.valueOf(hotelPrice.getHotel().getSource()));
            hotelResponse.setRoomName(hotelPrice.getRoomName());
            hotelResponse.setStatus(hotelPrice.getStatus());
        }
//
//        List<HotelPrice> hotelPriceList = hotelPriceService.getListForOrder(startDate, leaveDate, hotelId, ratePlanCode);
//        if (hotelPriceList.size() != days.intValue()) {
//            // 并不是每天都可以预订, 不满足条件
//            // 对于这种情况使价格为0表示不可预定
//            hotelResponse.setRoomDescription("暂无信息");
//            hotelResponse.setBreakfast(false);
//            hotelResponse.setPrice(0F);
//            hotelResponse.setTotalPrice(0F);
//        } else {
//            // 查询所需日期范围内酒店的单价之和和最低价格
//            Float price = Float.MAX_VALUE;
//            Float totalPrice = 0F;
//            for (HotelPrice hotelPrice : hotelPriceList) {
//                if (price > hotelPrice.getPrice()) {
//                    price = hotelPrice.getPrice();
//                }
//                totalPrice += hotelPrice.getPrice();
//            }
//            HotelPrice hotelPrice = hotelPriceList.get(0);
//            hotelResponse.setName(hotelPrice.getHotel().getName());
//            hotelResponse.setRoomDescription(hotelPrice.getRoomDescription());
//            hotelResponse.setBreakfast(hotelPrice.getBreakfast());
//            hotelResponse.setPrice(price);
//            hotelResponse.setTotalPrice(totalPrice);
//        }

        return hotelResponse;
    }

    public void prepareTicket(Plan plan, List<TicketResponse> ticketResponseList, List<String> noTicket) {
        // 处理行程中的所有门票信息
        List<Long> scenicIdList = new ArrayList<Long>();
        List<Date> dateList = new ArrayList<>();
        Map<Date, List<Long>> map = new LinkedHashMap<>();
        Map<String, Object> dateTicket = new HashMap<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for (PlanDay planDay : plan.getPlanDayList()) {
            List<Long> scenicIds = new ArrayList<>();
            String cityIdAndDay = planDay.getCity().getId().toString() + ":" + planDay.getDays().toString();
            dateTicket.put(DateUtils.formatShortDate(planDay.getDate()), cityIdAndDay);
            for (PlanTrip planTrip : planDay.getPlanTripList()) {
                if (planTrip.getTripType().intValue() == 1) {
                    // 把所有景点id加入一个list中方便统一查询
                    scenicIdList.add(planTrip.getScenicInfo().getId());
                    scenicIds.add(planTrip.getScenicInfo().getId());
                }
            }
            try {
                map.put(format.parse(format.format(planDay.getDate())), scenicIds);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        // 没有查询到景点
        if (scenicIdList.isEmpty()) {
            return;
        }
        // 对每一个景点查询第二天的具有最低价格的门票的价格
        //应该根据具体游玩日期来选择门票，有就查没有就不显示
//        Date date = afterDays(new Date(), 1);
//        String tomorrowDateString = getTomorrowDate();
//        Date date = formatDate(tomorrowDateString);
        List<TicketDateprice> ticketDatepriceList = new ArrayList<>();
        for (Date d : map.keySet()) {
            for (TicketDateprice t : ticketDatepriceService.getForPlanOrder(d, map.get(d))) {
                ticketDatepriceList.add(t);
            }
        }
//        List<TicketDateprice> ticketDatepriceList = ticketDatepriceService.getForPlanOrder(date, scenicIdList);
        for (TicketDateprice ticketDateprice : ticketDatepriceList) {
            //
//            private Long id;
//            private Long priceId;
//            private String name;
//            private Date date;
//            private Float price;
            String[] cd = dateTicket.get(DateUtils.formatShortDate(ticketDateprice.getHuiDate())).toString().split(":");
            TicketResponse ticketResponse = new TicketResponse();
            ticketResponse.setId(ticketDateprice.getProductId());
            ticketResponse.setPriceId(ticketDateprice.getTicketPriceId().getId());
            ticketResponse.setName(ticketDateprice.getTicketPriceId().getName());
            ticketResponse.setPrice(ticketDateprice.getPriPrice());
            ticketResponse.setDate(format.format(ticketDateprice.getHuiDate()));
            ticketResponse.setCityId(Long.parseLong(cd[0]));
            ticketResponse.setDay(Integer.parseInt(cd[1]));
            ticketResponseList.add(ticketResponse);
            scenicIdList.remove(ticketDateprice.getTicketPriceId().getTicket().getScenicInfo().getId());
        }
        for (Long id : scenicIdList) {
            ScenicInfo scenicInfo = scenicInfoService.get(id);
            noTicket.add(scenicInfo.getName());
//            if (scenicInfo.getPrice() == null || scenicInfo.getPrice() == 0) {
//                noNeed.add(scenicInfo.getName());
//            } else {
//                noTicket.add(scenicInfo.getName());
//            }
        }
    }

    public void prepareTicket(Plan plan, List<Map<String, Object>> maps) throws ParseException {
        // 处理行程中的所有门票信息
        for (PlanDay planDay : plan.getPlanDayList()) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("cityId", planDay.getCity().getId());
            map.put("cityName", planDay.getCity().getName());
            map.put("day", planDay.getDays());
            List<Long> scenicIds = Lists.transform(planDay.getPlanTripList(), new Function<PlanTrip, Long>() {
                @Override
                public Long apply(PlanTrip input) {
                    return input.getScenicInfo().getId();
                }
            });
            List<Map<String, Object>> tickets = Lists.newArrayList();
            for (TicketDateprice ticketDateprice : ticketDatepriceService.getForPlanOrder(DateUtils.parseShortTime(DateUtils.formatShortDate(planDay.getDate())), scenicIds)) {
                Map<String, Object> ticket = Maps.newHashMap();
                ticketDateprice.getTicketPriceId().formatType();
                ticket.put("scenicName", ticketDateprice.getTicketPriceId().getTicket().getScenicInfo().getName());
                ticket.put("ticketId", ticketDateprice.getProductId());
                ticket.put("ticketName", ticketDateprice.getTicketPriceId().getName());
                ticket.put("priceId", ticketDateprice.getTicketPriceId().getId());
                ticket.put("price", ticketDateprice.getPriPrice());
                ticket.put("playDate", DateUtils.formatShortDate(planDay.getDate()));
                ticket.put("seatType", ticketDateprice.getTicketPriceId().getFormatType());
                ticket.put("status", "normal");
                tickets.add(ticket);
                scenicIds.remove(ticketDateprice.getTicketPriceId().getTicket().getScenicInfo().getId());
            }
            for (Long id : scenicIds) {
                ScenicInfo scenicInfo = scenicInfoService.get(id);
                Map<String, Object> ticket = Maps.newHashMap();
                ticket.put("scenicName", scenicInfo.getName());
                ticket.put("playDate", DateUtils.formatShortDate(planDay.getDate()));
                ticket.put("status", "noTicket");
                tickets.add(ticket);
            }
            map.put("tickets", tickets);
            maps.add(map);
        }
    }

    public void prepareTicket(List<Map<String, Object>> planDays, List<Map<String, Object>> maps, Date date) throws ParseException {
        // 处理行程中的所有门票信息
        for (Map<String, Object> planDay : planDays) {
            if (Integer.valueOf(planDay.get("day").toString()) == 1) {
                date = DateUtils.add(date, Calendar.DAY_OF_MONTH, 1);
            }
            Map<String, Object> map = Maps.newHashMap();
            map.put("cityId", planDay.get("cityId"));
            map.put("cityName", planDay.get("cityName"));
            map.put("day", planDay.get("day"));
            List<Long> scenicIds = Lists.transform((List<Object>) planDay.get("scenicIds"), new Function<Object, Long>() {
                @Override
                public Long apply(Object input) {
                    return Long.valueOf(input.toString());
                }
            });
            List<Map<String, Object>> tickets = Lists.newArrayList();
            for (TicketDateprice ticketDateprice : ticketDatepriceService.getForPlanOrder(DateUtils.parseShortTime(DateUtils.formatShortDate(date)), scenicIds)) {
                Map<String, Object> ticket = Maps.newHashMap();
                ticketDateprice.getTicketPriceId().formatType();
                ticket.put("scenicName", ticketDateprice.getTicketPriceId().getTicket().getScenicInfo().getName());
                ticket.put("ticketId", ticketDateprice.getProductId());
                ticket.put("ticketName", ticketDateprice.getTicketPriceId().getName());
                ticket.put("priceId", ticketDateprice.getTicketPriceId().getId());
                ticket.put("price", ticketDateprice.getPriPrice());
                ticket.put("playDate", DateUtils.formatShortDate(date));
                ticket.put("seatType", ticketDateprice.getTicketPriceId().getFormatType());
                ticket.put("status", "normal");
                tickets.add(ticket);
                scenicIds.remove(ticketDateprice.getTicketPriceId().getTicket().getScenicInfo().getId());
            }
            for (Long id : scenicIds) {
                ScenicInfo scenicInfo = scenicInfoService.get(id);
                Map<String, Object> ticket = Maps.newHashMap();
                ticket.put("scenicName", scenicInfo.getName());
                ticket.put("playDate", DateUtils.formatShortDate(date));
                ticket.put("status", "noTicket");
                tickets.add(ticket);
            }
            map.put("tickets", tickets);
            maps.add(map);
            date = DateUtils.add(date, Calendar.DAY_OF_MONTH, 1);
        }
    }

    // 一个日期经过days天之后的日期
    private Date afterDays(Date startDate, Integer days) {
        //
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);
        calendar.add(calendar.DATE, days); //把日期往后增加一天.整数往后推,负数往前移动
        return calendar.getTime();
    }

    /**
     * 把136分钟转化为2h16m
     *
     * @param trafficTime
     * @return
     */
    private String timeToString(Long trafficTime) {
        //
        Long time = trafficTime;
        Long day = time / (60 * 24);
        time -= day * 24 * 60;
        Long hour = time / 60;
        time -= hour * 60;
        Long minute = time;
        String flightTimeString = "";
        if (day.intValue() != 0) {
            flightTimeString += day + "天";
        }
        if (hour.intValue() != 0) {
            flightTimeString += hour + "小时";
        }
        if (minute.intValue() != 0) {
            flightTimeString += minute + "分钟";
        }
        return flightTimeString;
    }

    // 格式化数据
    public Map<String, Object> formatMap(String data) {
        try {
            return new ObjectMapper().readValue(data, Map.class);
        } catch (IOException e) {
            logger.error("数据格式化异常:" + e.getLocalizedMessage());
        }
        return null;
    }

    // 字符串格式化为时间
    private Date formatDate(String dateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            logger.error("格式化时间失败");
            return null;
        }
    }

    // 获取第二天的日期并格式化为字符串
    private String getTomorrowDate() {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, 1);
        date = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    private String processCityName(String name) {
        //
        String[] names = name.split("市");
        return names[0];
    }
}
