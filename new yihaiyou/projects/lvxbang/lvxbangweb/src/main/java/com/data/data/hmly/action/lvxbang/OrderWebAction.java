package com.data.data.hmly.action.lvxbang;

import com.data.data.hmly.action.user.UserConstans;
import com.data.data.hmly.service.MemberService;
import com.data.data.hmly.service.TbAreaService;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.discount.UserCouponService;
import com.data.data.hmly.service.discount.entity.DiscountContext;
import com.data.data.hmly.service.discount.strategy.CouponStrategy;
import com.data.data.hmly.service.elong.ElongHotelService;
import com.data.data.hmly.service.elong.pojo.CreditCardValidateResult;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.entity.UserStatus;
import com.data.data.hmly.service.hotel.HotelPriceService;
import com.data.data.hmly.service.hotel.HotelService;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.hotel.entity.HotelPrice;
import com.data.data.hmly.service.hotel.entity.HotelPriceCalendar;
import com.data.data.hmly.service.hotel.entity.enums.PriceStatus;
import com.data.data.hmly.service.line.*;
import com.data.data.hmly.service.line.entity.*;
import com.data.data.hmly.service.lvxbang.DiscountService;
import com.data.data.hmly.service.lvxbang.OrderDetailResponseService;
import com.data.data.hmly.service.lvxbang.PlanOrderService;
import com.data.data.hmly.service.lvxbang.response.HotelResponse;
import com.data.data.hmly.service.lvxbang.response.OrderDetailResponse;
import com.data.data.hmly.service.lvxbang.response.TicketResponse;
import com.data.data.hmly.service.lvxbang.response.TrafficResponse;
import com.data.data.hmly.service.lxbcommon.entity.UserCoupon;
import com.data.data.hmly.service.lxbcommon.entity.enums.UserCouponStatus;
import com.data.data.hmly.service.order.OrderDetailService;
import com.data.data.hmly.service.order.OrderService;
import com.data.data.hmly.service.order.entity.*;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.plan.PlanService;
import com.data.data.hmly.service.plan.entity.Plan;
import com.data.data.hmly.service.sales.InsuranceService;
import com.data.data.hmly.service.sales.entity.Insurance;
import com.data.data.hmly.service.ticket.TicketDatepriceService;
import com.data.data.hmly.service.ticket.TicketPriceService;
import com.data.data.hmly.service.ticket.TicketService;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.data.data.hmly.service.ticket.entity.TicketDateprice;
import com.data.data.hmly.service.ticket.entity.TicketPrice;
import com.data.data.hmly.service.ticket.entity.enmus.TicketType;
import com.data.data.hmly.service.ticket.response.TicketPriceAddInfo;
import com.data.data.hmly.service.traffic.TrafficPriceService;
import com.data.data.hmly.service.traffic.entity.Traffic;
import com.data.data.hmly.service.traffic.entity.TrafficPrice;
import com.data.data.hmly.service.traffic.entity.enums.TrafficType;
import com.data.data.hmly.service.user.TouristService;
import com.data.data.hmly.service.user.entity.Tourist;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by vacuity on 15/12/24.
 */
public class OrderWebAction extends LxbAction {

    @Resource
    private OrderService orderService;
    @Resource
    private TrafficPriceService trafficPriceService;
    @Resource
    private TouristService touristService;
    @Resource
    private HotelService hotelService;
    @Resource
    private HotelPriceService hotelPriceService;
    @Resource
    private TicketService ticketService;
    @Resource
    private TicketPriceService ticketPriceService;
    @Resource
    private TicketDatepriceService ticketDatepriceService;
    @Resource
    private PlanService planService;
    @Resource
    private PlanOrderService planOrderService;
    @Resource
    private OrderDetailResponseService orderDetailResponseService;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private MemberService memberService;
//    @Resource
//    private HotelElongService hotelElongService;
    @Resource
    private ElongHotelService elongHotelService;
    @Resource
    private DiscountService discountService;
    @Resource
    private UserCouponService userCouponService;
//    @Resource
//    private LineService lineService;
    @Resource
    private LinetypepricedateService linetypepricedateService;
    @Resource
    private LinetypepriceService linetypepriceService;
    @Resource
    private TbAreaService tbAreaService;
    @Resource
    private LineDepartureService lineDepartureService;
    @Resource
    private LineexplainService lineexplainService;
//    @Resource
//    private UserService userService;
    @Resource
    private InsuranceService insuranceService;
    @Resource
    private LinedaysProductPriceService linedaysProductPriceService;

    public Member user = new Member();

    // 创建订单需要的所有信息
    public String data;

    public Order order = new Order();

    // 单程与往返机票
    public Long singleTrafficId;
    public Long returnTrafficId;
    public Long singleTrafficPriceId;
    public Long returnTrafficPriceId;
    public Traffic singleTraffic;
    public Traffic returnTraffic;
    public TrafficPrice singleTrafficPrice;
    public TrafficPrice returnTrafficPrice;
    // 常用联系人
    public List<Tourist> touristList = new ArrayList<Tourist>();
    public List<OrderTourist> orderTouristList = new ArrayList<OrderTourist>();

    // 优惠券
    public List<UserCoupon> userCouponsList = Lists.newArrayList();

    public Long userCouponId;
    public String couponName;
    public Float couponValue;

    // 酒店
    public Long hotelId;
    public String ratePlanCode;
    public Hotel hotel;
    public HotelPrice hotelPrice;
    public String startDate;
    public String leaveDate;
    public Integer days;
    //从酒店详情页点击预订所传的时间
    public String priceStartDate;
    public String priceEndDate;
    public String cardNum;
    public Float price;
    // 门票
    public Integer num;
    public Long ticketId;
    public Long ticketPriceId;
    public Ticket ticket;
    public TicketPrice ticketPrice;
    public String ticketDate;
    public TicketDateprice ticketDateprice;
    public TicketType ticketType;
    public List<TicketPriceAddInfo> ticketPriceAddInfos = new ArrayList<>();
    public Long detailId;

    // 行程
    public Long planId;
    public String planName;
    public List<TrafficResponse> planeList = new ArrayList<TrafficResponse>();
    public List<TrafficResponse> trainList = new ArrayList<TrafficResponse>();
    public List<TrafficResponse> shipList = new ArrayList<TrafficResponse>();
    public List<HotelResponse> hotelList = new ArrayList<HotelResponse>();
    public List<TicketResponse> ticketList = new ArrayList<TicketResponse>();
    public List<String> noNeed = new ArrayList<String>();
    public List<String> noTicket = new ArrayList<String>();


    public Long orderId;
    public Long orderDetailId;
    public Long returnOrderDetailId;
    //行程中酒店是到付还是担保状态
    public PriceStatus status = PriceStatus.UP;

    //路线
    public Long lineId;
    public String type;
    public Line line;
    public TbArea startCity;
    public Long linetypepriceId;
    public String lineStartDate;
    public Integer adultNum;
    public Integer childNum;
    public Linetypeprice linetypeprice;
    public Linetypepricedate linetypepricedate;
    public LineDeparture lineDeparture;
    public Lineexplain lineexplain;
    public List<Insurance> insuranceList;
    public Integer totalNum;
    public OrderInvoice invoice = new OrderInvoice();
    public Boolean needGuarantee;

    //详情
    public OrderDetailResponse orderDetailResponse;
    public OrderDetailResponse returnOrderDetailResponse;
    public List<OrderDetailResponse> planeDetailList = new ArrayList<OrderDetailResponse>();
    public List<OrderDetailResponse> trainDetailList = new ArrayList<OrderDetailResponse>();
    public List<OrderDetailResponse> hotelDetailList = new ArrayList<OrderDetailResponse>();
    public List<OrderDetailResponse> ticketDetailList = new ArrayList<OrderDetailResponse>();
    public CreditCard creditCardResponse = new CreditCard();

    //    private UserExinfo userExinfo;
    public Long priceId;

    public Result order() {
        return dispatch();
    }


    public Result orderSingleFlight() {
//        singleTrafficPriceId = 1L;
        if (orderId != null && orderId != 0) {
            order = orderService.get(orderId);
            if (order.getUserCoupon() != null) {
                UserCoupon userCoupon = order.getUserCoupon();

                discountService.completeTypes(userCoupon);
                userCouponId = userCoupon.getId();
            }
            orderDetailId = order.getOrderDetails().get(0).getId();
            singleTrafficPriceId = order.getOrderDetails().get(0).getCostId();
        }
        singleTrafficPrice = trafficPriceService.get(singleTrafficPriceId);
        singleTraffic = singleTrafficPrice.getTraffic();
        // time
        Long flightTime = singleTraffic.getFlightTime();
        String flightTimeString = timeToString(flightTime);
        singleTraffic.setFlightTimeString(flightTimeString);
        // city
        String leaveName = singleTraffic.getLeaveCity().getName();
        String arriveName = singleTraffic.getArriveCity().getName();
        singleTraffic.getLeaveCity().setName(processCityName(leaveName));
        singleTraffic.getArriveCity().setName(processCityName(arriveName));
        //
        user = getLoginUser();
//        user = userService.get(11);
        Tourist tourist = new Tourist();
        tourist.setUser(user);
        touristList = touristService.list(tourist, null);
        if (singleTraffic.getTrafficType() == TrafficType.TRAIN) {
            userCouponsList = discountService.listUserCoupon(ProductType.train, user);
        } else {
            userCouponsList = discountService.listUserCoupon(ProductType.flight, user);
        }
        return dispatch();
    }

    public Result orderReturnFlight() {
//        singleTrafficPriceId = 1L;
//        returnTrafficPriceId = 3L;
        if (orderId != null && orderId != 0) {
            //
            order = orderService.get(orderId);
            if (order.getUserCoupon() != null) {
                UserCoupon userCoupon = order.getUserCoupon();
                discountService.completeTypes(userCoupon);
                userCouponId = userCoupon.getId();
            }
            orderDetailId = order.getOrderDetails().get(0).getId();
            returnOrderDetailId = order.getOrderDetails().get(1).getId();
            singleTrafficPriceId = orderService.get(orderId).getOrderDetails().get(0).getCostId();
            returnTrafficPriceId = orderService.get(orderId).getOrderDetails().get(1).getCostId();
        }
        singleTrafficPrice = trafficPriceService.get(singleTrafficPriceId);
        singleTraffic = singleTrafficPrice.getTraffic();
        returnTrafficPrice = trafficPriceService.get(returnTrafficPriceId);
        returnTraffic = returnTrafficPrice.getTraffic();
        // time
        Long flightTime = singleTraffic.getFlightTime();
        String flightTimeString = timeToString(flightTime);
        singleTraffic.setFlightTimeString(flightTimeString);

        Long returnTime = returnTraffic.getFlightTime();
        String returnTimeString = timeToString(returnTime);
        returnTraffic.setFlightTimeString(returnTimeString);
        // city
        String leaveName = singleTraffic.getLeaveCity().getName();
        String arriveName = singleTraffic.getArriveCity().getName();
        singleTraffic.getLeaveCity().setName(processCityName(leaveName));
        singleTraffic.getArriveCity().setName(processCityName(arriveName));

        String returnLeaveName = returnTraffic.getLeaveCity().getName();
        String returnArriveName = returnTraffic.getArriveCity().getName();
        returnTraffic.getLeaveCity().setName(processCityName(returnLeaveName));
        returnTraffic.getArriveCity().setName(processCityName(returnArriveName));
        //
        user = getLoginUser();
//        user = userService.get(11);
        Tourist tourist = new Tourist();
        tourist.setUser(user);
        touristList = touristService.list(tourist, null);
        if (singleTraffic.getTrafficType() == TrafficType.TRAIN) {
            userCouponsList = discountService.listUserCoupon(ProductType.train, user);
        } else {
            userCouponsList = discountService.listUserCoupon(ProductType.flight, user);
        }
        return dispatch();
    }

    public Result checkHotelOrder() throws ParseException {
        Date start = com.zuipin.util.DateUtils.parseShortTime(priceStartDate);
        Date end = com.zuipin.util.DateUtils.parseShortTime(priceEndDate);
        List<HotelPriceCalendar> calendarList = hotelPriceService.findTypePriceDate(priceId, start, end, num);
        result.put("success", calendarList.size() == com.zuipin.util.DateUtils.getDateDiff(start, end));
        return json(JSONObject.fromObject(result));
    }

    public Result orderHotel() throws ParseException {
//        hotelId = 3048L;
//        ratePlanCode = 11721709L;
        if (orderId != null && orderId != 0) {
            order = orderService.get(orderId);
            orderDetailId = order.getOrderDetails().get(0).getId();
            hotelId = orderService.get(orderId).getOrderDetails().get(0).getProduct().getId();
            ratePlanCode = orderService.get(orderId).getOrderDetails().get(0).getRatePlanCode();
        }
        Date start = com.zuipin.util.DateUtils.parseShortTime(priceStartDate);
        Date end = com.zuipin.util.DateUtils.parseShortTime(priceEndDate);
        List<HotelPriceCalendar> calendarList = hotelPriceService.findTypePriceDate(priceId, start, end, 1);
        if (calendarList.size() != com.zuipin.util.DateUtils.getDateDiff(start, end)) {
            return redirect("/hotel_detail_" + hotelId + ".html");
        }
        Float total = 0f;
        for (HotelPriceCalendar calendar : calendarList) {
            Float cPrice = calendar.getCprice() == null ? 0f : calendar.getCprice();
            total += calendar.getMember() + cPrice;
        }
        price = total / calendarList.size();
        hotel = hotelService.get(hotelId);

        //todo: 注意，这里现在使用了无日期的搜索方式，仅用于适配当前的数据，一旦酒店房型的日期数据有了以后，必须第一时间切换到带日期的搜索模式，具体可以参照这次修改之前的代码
//        String dateString = formatDate(new Date());
//        HotelPrice searchCondition = new HotelPrice();
        hotelPrice = hotelPriceService.get(priceId);
//        searchCondition.setHotel(hotel);
//        List<HotelPrice> hotelPriceList = hotelPriceService.list(searchCondition, new Page(0, 1), "price", "asc");
//        hotelPrice = hotelPriceList.get(0);
        user = getLoginUser();
//        user = userService.get(11);
        Tourist tourist = new Tourist();
        tourist.setUser(user);
        touristList = touristService.list(tourist, null);
        userCouponsList = discountService.listUserCoupon(ProductType.hotel, user);
        return dispatch();
    }

    public Result getHotelPriceList() throws ParseException {

        Map<String, Object> map = Maps.newHashMap();
//        Hotel h = hotelService.get(hotelId);
//        List<HotelPrice> list = hotelPriceService.getListForOrder(hotelId, startDate, leaveDate, priceId);
        Date start = DateUtils.parseShortTime(startDate);
        Date end = DateUtils.parseShortTime(leaveDate);
        List<HotelPriceCalendar> calendarList = hotelPriceService.findTypePriceDate(priceId, start, end, 1);
        if (calendarList.size() == DateUtils.getDateDiff(start, end)) {
            Float sum = 0F;
//            Float min = Float.MAX_VALUE;
            for (HotelPriceCalendar dayPrice : calendarList) {
                Float cPrice = dayPrice.getCprice() == null ? 0f : dayPrice.getCprice();
                sum += dayPrice.getMember() + cPrice;
//                if (min > dayPrice.getMember()) {
//                    min = dayPrice.getMember();
//                }
            }
            map.put("success", true);
            map.put("sum", sum);
            map.put("min", sum / calendarList.size());
        } else {
            map.put("success", false);
            map.put("sum", 0);
            map.put("min", 0);
        }
//        艺龙实时获取酒店数据
//        HotelPriceRequest hotelPriceRequest = new HotelPriceRequest();
//        hotelPriceRequest.setElongId(String.valueOf(h.getSourceId()));
//        hotelPriceRequest.setHotelId(hotelId);
//        hotelPriceRequest.setArrive(DateUtils.parseShortTime(startDate));
//        hotelPriceRequest.setDeparture(DateUtils.parseShortTime(leaveDate));
//        hotelPriceRequest.setRoomId(hotelPriceList.get(0).getRoomId());
//        hotelPriceRequest.setRoomTypeId(hotelPriceList.get(0).getRoomTypeId());
//        int days = DateUtils.DayDiff( DateUtils.parseShortTime(leaveDate), DateUtils.parseShortTime(startDate));
//        hotelPriceRequest.setRatePla(Integer.valueOf(hotelPriceList.get(0).getRatePlanCode()));
//        List<HotelPrice> list =
//                hotelElongService.doHotelPrices(hotelPriceRequest);


//        HotelDetail hotelDetail = elongHotelService.getHotelDetail( DateUtils.parseShortTime(startDate), DateUtils.parseShortTime(leaveDate),
//                hotelId, 1, "2" , Integer.valueOf(hotelPriceList.get(0).getRatePlanCode()), hotelPriceList.get(0).getRoomId());
//        Date arriveDate, Date departureDate, long hotelId, int ratePlanId, String roomId, Integer checkInPersonNum, String detailOptions
        //原先用days == hotelPriceList.size()，酒店没有单独每天数据,所以会一直报查不到
        JSONObject jsonObject = JSONObject.fromObject(map);
        return json(jsonObject);
    }

    public Result checkTicketOrder() {
        List<TicketDateprice> ticketDatepriceList = ticketDatepriceService.findTypePriceDate(ticketPriceId, getDate(ticketDate), getDate(ticketDate), num);
        result.put("success", !ticketDatepriceList.isEmpty());
        return json(JSONObject.fromObject(result));
    }

    public Result orderTicket() {
        if (orderId != null && orderId != 0) {
            order = orderService.get(orderId);
            orderDetailId = order.getOrderDetails().get(0).getId();
            if (order.getUserCoupon() != null) {
                UserCoupon userCoupon = order.getUserCoupon();

                discountService.completeTypes(userCoupon);
                userCouponId = userCoupon.getId();
            }
            ticketId = orderService.get(orderId).getOrderDetails().get(0).getProduct().getId();
            ticketPriceId = orderService.get(orderId).getOrderDetails().get(0).getCostId();
        }
        ticket = ticketService.loadTicket(ticketId);
        ticketPrice = ticketPriceService.findById(ticketPriceId);
        ticketPriceAddInfos = ticketPriceService.findTicketPriceAddInfoList(ticketPrice);
        List<TicketDateprice> ticketDatepriceList = ticketDatepriceService.findTypePriceDate(ticketPriceId, new Date(), null, 1);
        if (!ticketDatepriceList.isEmpty()) {
            ticketDateprice = ticketDatepriceList.get(0);
        } else {
            ticketDateprice = new TicketDateprice();
            ticketDateprice.setHuiDate(new Date());
            ticketDateprice.setPriPrice(0F);
        }
        user = getLoginUser();
        Tourist tourist = new Tourist();
        tourist.setUser(user);
        touristList = touristService.list(tourist, null);
        userCouponsList = discountService.listUserCoupon(ProductType.scenic, user);
        return dispatch();
    }

    public Result checkLineOrder() {
        Date date = getDate(lineStartDate);
        linetypeprice = linetypepriceService.getLinetypeprice(linetypepriceId);
        List<Linetypepricedate> linetypepricedates = linetypepricedateService.findTypePriceDate(linetypepriceId, date, date, 1);
        result.put("success", !orderService.lineInventoryCheck(linetypepricedates, adultNum).isEmpty());
        return json(JSONObject.fromObject(result));
    }

    public Result orderLine() {
        Date date;
        if (orderId != null && orderId != 0) {
            order = orderService.get(orderId);
            if (!order.getStatus().equals(OrderStatus.UNCONFIRMED)) {
                return redirect("/lvxbang/order/lineOrderDetail.jhtml?orderId=" + orderId);
            }
            orderDetailId = order.getOrderDetails().get(0).getId();
            if (order.getOrderDetails().size() > 1) {
                returnOrderDetailId = order.getOrderDetails().get(1).getId();
            }
            if (order.getUserCoupon() != null) {
                UserCoupon userCoupon = order.getUserCoupon();

                discountService.completeTypes(userCoupon);
                userCouponId = userCoupon.getId();
            }
            linetypepriceId = orderService.get(orderId).getOrderDetails().get(0).getCostId();
            invoice = order.getInvoice();
            date = order.getPlayDate();
        } else {
            date = getDate(lineStartDate);
        }

        linetypeprice = linetypepriceService.getLinetypeprice(linetypepriceId);
        startCity = tbAreaService.getById(linetypeprice.getLine().getStartCityId());
//        String dateString = formatDay(new Date());
        List<Linetypepricedate> linetypepricedates = linetypepricedateService.findTypePriceDate(linetypepriceId, date, null, 1);
        if (!linetypepricedates.isEmpty()) {
            linetypepricedate = linetypepricedates.get(0);
        } else {
            linetypepricedate = new Linetypepricedate();
            linetypepricedate.setDay(date);
            linetypepricedate.setDiscountPrice(0f);
        }
        lineDeparture = lineDepartureService.getDepartureWithInfoByLine(linetypeprice.getLine());
        lineexplain = lineexplainService.getByLine(linetypeprice.getLine().getId());
        user = getLoginUser();
        Tourist tourist = new Tourist();
        tourist.setUser(user);
        touristList = touristService.list(tourist, null);
        userCouponsList = discountService.listUserCoupon(ProductType.line, user);
        insuranceList = insuranceService.listLineInsurance();

        LinedaysProductPrice linedaysProductPrice = new LinedaysProductPrice();
        linedaysProductPrice.setLineId(linetypeprice.getLine().getId());
        linedaysProductPrice.setProductType(ProductType.hotel);
        List<LinedaysProductPrice> hotelProductPrices = linedaysProductPriceService.list(linedaysProductPrice, null);
        needGuarantee = false;
        for (LinedaysProductPrice hotelProductPrice : hotelProductPrices) {
            HotelPrice hotelPrice = hotelPriceService.get(hotelProductPrice.getPriceId());
            if (hotelPrice.getStatus().equals(PriceStatus.GUARANTEE)) {
                needGuarantee = true;
                break;
            }
        }
        return dispatch();
    }

    public Result getTicketDatePrice() {
        //
        Map<String, Object> map = Maps.newHashMap();
        Date date = getDate(ticketDate);
        if (date == null) {
            // 格式化时间失败
            map.put("success", false);
            map.put("price", 0);
            JSONObject jsonObject = JSONObject.fromObject(map);
            return json(jsonObject);
        }

        List<TicketDateprice> ticketDatepriceList = ticketDatepriceService.findTypePriceDate(ticketPriceId, date, date);
        if (ticketDatepriceList.isEmpty()) {
            //
            map.put("success", false);
            map.put("price", 0);
            JSONObject jsonObject = JSONObject.fromObject(map);
            return json(jsonObject);
        } else {
            map.put("success", true);
            map.put("price", ticketDatepriceList.get(0).getPriPrice());
            JSONObject jsonObject = JSONObject.fromObject(map);
            return json(jsonObject);
        }
    }

    public Result getTicketPriceList() {
        //
        String dateString = formatDay(new Date());
        Date date = getDate(dateString);
        List<TicketDateprice> ticketDatepriceList = ticketDatepriceService.findTypePriceDate(ticketPriceId, date, null, 1);

        String[] includeConfig = new String[]{};
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig(null, includeConfig);
        JSONArray jsonArray = JSONArray.fromObject(ticketDatepriceList, jsonConfig);
        return json(jsonArray);
    }

    public Result getLineDatePriceList() {
        String dateString = formatDay(new Date());
        Date date = getDate(dateString);
        date = DateUtils.add(date, Calendar.DAY_OF_MONTH, 1);
        List<Linetypepricedate> linetypepricedates = linetypepricedateService.findTypePriceDate(linetypepriceId, date, null, 1);
        JSONArray jsonArray = JSONArray.fromObject(orderService.lineInventoryCheck(linetypepricedates, 1), JsonFilter.getIncludeConfig());
        return json(jsonArray);
    }

    public Result orderPlan() {
        //
//        data = "{\"traffic\":\"1,2,3\",\"hotel\":[{\"name\":\"厦门凤凰花青年旅社\",\"hotelId\":\"3048\",\"ratePlanCode\":\"11721709\",\"startDate\":\"2016-01-10\",\"leaveDate\":\"2016-01-12\"},{\"name\":\"厦门新阳光酒店公寓\",\"hotelId\":\"3049\",\"ratePlanCode\":\"11721847\",\"startDate\":\"2016-01-10\",\"leaveDate\":\"2016-01-12\"}]}";
//        planId = 106775L;
        Plan plan = planService.get(planId);
        planName = plan.getName();

        Map<String, Object> dataMap = planOrderService.formatMap(data);

        planOrderService.prepareTraffic(dataMap, planeList, trainList, shipList);
        planOrderService.prepareHotel(dataMap, hotelList);
        planOrderService.prepareTicket(plan, ticketList, noTicket);

        user = getLoginUser();
//        user = userService.get(11);
        Tourist tourist = new Tourist();
        tourist.setUser(user);
        touristList = touristService.list(tourist, null);
        for (HotelResponse hr : hotelList) {
            if (hr.getStatus() == PriceStatus.GUARANTEE) {
                status = PriceStatus.GUARANTEE;
            }
        }
        userCouponsList = discountService.listUserCoupon(ProductType.plan, user);
        return dispatch();
    }

    public Result getOrderInfo() {
        Map<String, Object> map = Maps.newHashMap();
        try {

            OrderDetail orderDetail = orderDetailService.get(orderDetailId);
            List<OrderTourist> orderTouristList = orderDetail.getOrderTouristList();
            JSONArray tourist = JSONArray.fromObject(orderTouristList, JsonFilter.getIncludeConfig());
            map.put("tourist", tourist);
            Integer num = orderDetail.getNum();
            map.put("num", num);
            Date date = orderDetail.getPlayDate();
            map.put("date", formatDay(date));
            if (orderDetail.getLeaveDate() != null) {
                Date leaveDate = orderDetail.getLeaveDate();
                map.put("leaveDate", formatDay(leaveDate));
            }

            if (returnOrderDetailId != null && returnOrderDetailId != 0) {

                OrderDetail returnDetail = orderDetailService.get(returnOrderDetailId);
                List<OrderTourist> returnTouristList = returnDetail.getOrderTouristList();
                JSONArray returnTourist = JSONArray.fromObject(returnTouristList, JsonFilter.getIncludeConfig());
                map.put("returnTourist", returnTourist);
                Integer returnNum = returnDetail.getNum();
                map.put("returnNum", returnNum);
            }
            map.put("success", true);
            JSONObject jsonObject = JSONObject.fromObject(map);
            return json(jsonObject);
        } catch (Exception e) {
            //
            map.put("success", false);
            map.put("price", 0);
            JSONObject jsonObject = JSONObject.fromObject(map);
            return json(jsonObject);
        }


    }

    public Result editPlanOrder() {
        //
        user = getLoginUser();
//        user = userService.get(11);
        Tourist tourist = new Tourist();
        tourist.setUser(user);
        touristList = touristService.list(tourist, null);
        order = orderService.get(orderId);
        if (order.getOrderDetails().isEmpty()) {
            return dispatch();
        }
        orderDetailResponse = orderDetailResponseService.orderToResponse(order.getOrderDetails().get(0));
        for (OrderDetail orderDetail : order.getOrderDetails()) {
            //
            switch (orderDetail.getProductType()) {
                //
                case flight:
                    planeDetailList.add(orderDetailResponseService.orderToTrafficDetail(orderDetail));
                    break;
                case train:
                    trainDetailList.add(orderDetailResponseService.orderToTrafficDetail(orderDetail));
                    break;
                case hotel:
                    hotelDetailList.add(orderDetailResponseService.orderToHotelDetail(orderDetail));
                    break;
                case scenic:
                    ticketDetailList.add(orderDetailResponseService.orderToTicketDetail(orderDetail));
                    break;
                default:
                    break;
            }
        }
        for (OrderDetailResponse odr : hotelDetailList) {
            if (odr.getPriceStatus() == PriceStatus.GUARANTEE) {
                status = PriceStatus.GUARANTEE;
                creditCardResponse = odr.getCreditCard();
            }
        }
        if (order.getUserCoupon() != null) {
            discountService.completeTypes(order.getUserCoupon());
        }
        userCouponsList = discountService.listUserCoupon(ProductType.plan, user);
        return dispatch();
    }

    // 创建订单
    public Result createOrder() {
        Map<String, Object> map = Maps.newHashMap();
        try {
//            User user = userService.get(getLoginUser().getId());
            Member user = memberService.get(getLoginUser().getId());
            if (UserStatus.blacklist.equals(user.getStatus())) {
                map.put("success", false);
                map.put("errMsg", "该用户在黑名单中");
                JSONObject jsonObject = JSONObject.fromObject(map);
                return json(jsonObject);
            }
//            User user = userService.get(11);
            order = orderService.createLxbOrder(data, user);
            // 保存订单中的联系人为常用联系人
            orderService.doOrderToTourist(order);
            if (order.getUserCoupon() != null && !order.getUserCoupon().getId().equals(userCouponId)) {
                UserCoupon userCoupon = order.getUserCoupon();
                if (new Date().before(userCoupon.getValidStart()) || new Date().after(userCoupon.getValidEnd())) {
                    userCoupon.setStatus(UserCouponStatus.expired);
                } else {
                    userCoupon.setStatus(UserCouponStatus.unused);
                }
                userCoupon.setUseTime(null);
                userCouponService.update(userCoupon);
                order.setUserCoupon(null);
                orderService.update(order);
            }

            if (order.getUserCoupon() != null && order.getUserCoupon().getId().equals(userCouponId)) {
                order.setPrice(order.getPrice() - order.getUserCoupon().getCoupon().getFaceValue());
                orderService.update(order);
            } else if (userCouponId != null && userCouponId > 0) {
                UserCoupon userCoupon = userCouponService.get(userCouponId);
                DiscountContext context = new DiscountContext(new CouponStrategy(order, userCoupon));
                if (context.checkUse()) {
                    order.setPrice(context.discount());
                    order.setUserCoupon(userCoupon);
                    orderService.update(order);
                    userCoupon.setStatus(UserCouponStatus.used);
                    userCoupon.setUseTime(new Date());
                    userCouponService.update(userCoupon);
                }
            }

            if (order == null) {
                map.put("success", false);
                JSONObject jsonObject = JSONObject.fromObject(map);
                return json(jsonObject);
            }


//            userExinfo = userExinfoService.getByUserId(user.getId());
//            if (userExinfo == null) {
//                userExinfo = new UserExinfo();
//                userExinfo.setUser(user);
//            }
//            userExinfo.setTelephone(order.getMobile());
            user.setTelephone(order.getMobile());
            user.setUserName(order.getRecName());
            user.setMobile(order.getMobile());
            memberService.update(user);
//            user.setUserExinfo(userExinfo);
//            userService.updateUserInfo(user);
//            userExinfoService.save(userExinfo);
            getSession().setAttribute(UserConstans.CURRENT_LOGIN_USER, user);
            getSession().setAttribute("account", user.getAccount());
            getSession().setAttribute("staffName", user.getUserName());
            getSession().setAttribute("userName", StringUtils.isBlank(user.getNickName()) ? user.getAccount() : user.getNickName());
            map.put("success", true);
            map.put("orderId", order.getId());
            JSONObject jsonObject = JSONObject.fromObject(map);
            return json(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            JSONObject jsonObject = JSONObject.fromObject(map);
            return json(jsonObject);
        }
    }

    public Result detail() {
        order = orderService.get(orderId);
        return dispatch();
    }

    /**
     * 门票订单详情
     *
     * @return
     */
    public Result ticketOrderDetail() {
        //
        order = orderService.get(orderId);
        OrderDetail detail = orderDetailService.get(detailId);
        if (detail != null) {
            orderDetailResponse = orderDetailResponseService.orderToTicketDetail(detail);
        }
        if (order.getUserCoupon() != null) {
            couponName = order.getUserCoupon().getCoupon().getName();
            couponValue = order.getUserCoupon().getCoupon().getFaceValue();
        }
        return dispatch();
    }

    public Result lineOrderDetail() {
        order = orderService.get(orderId);
        List<OrderDetail> orderDetails = Lists.newArrayList();
        for (OrderDetail orderDetail : order.getOrderDetails()) {
            if (orderDetail.getProductType().equals(ProductType.line)) {
                orderDetails.add(orderDetail);
            }
        }
        if (!orderDetails.isEmpty()) {
            orderDetailResponse = orderDetailResponseService.orderToTicketDetail(orderDetails.get(0));
            totalNum = orderDetailResponse.getNum();
            if (orderDetails.size() > 1 && ProductType.line.equals(orderDetails.get(1).getProductType())) {
                returnOrderDetailResponse = orderDetailResponseService.orderToResponse(orderDetails.get(1));
                totalNum += returnOrderDetailResponse.getNum();
            }
        }
        if (order.getUserCoupon() != null) {
            couponName = order.getUserCoupon().getCoupon().getName();
            couponValue = order.getUserCoupon().getCoupon().getFaceValue();
        }
        return dispatch();
    }

    /**
     * 酒店订单详情
     *
     * @return
     */
    public Result hotelOrderDetail() {
        //
        order = orderService.get(orderId);
        if (!order.getOrderDetails().isEmpty()) {
            orderDetailResponse = orderDetailResponseService.orderToHotelDetail(order.getOrderDetails().get(0));
        }
        return dispatch();
    }

    /**
     * 单程订单详情
     *
     * @return
     */
    public Result singleOrderDetail() {
        //
        order = orderService.get(orderId);
        if (!order.getOrderDetails().isEmpty()) {
            orderDetailResponse = orderDetailResponseService.orderToTrafficDetail(order.getOrderDetails().get(0));
        }
        if (order.getUserCoupon() != null) {
            couponName = order.getUserCoupon().getCoupon().getName();
            couponValue = order.getUserCoupon().getCoupon().getFaceValue();
        }
        return dispatch();
    }

    /**
     * 返程订单详情
     *
     * @return
     */
    public Result returnOrderDetail() {
        //
        order = orderService.get(orderId);
        if (order.getOrderDetails().size() > 1) {
            orderDetailResponse = orderDetailResponseService.orderToTrafficDetail(order.getOrderDetails().get(0));
            returnOrderDetailResponse = orderDetailResponseService.orderToTrafficDetail(order.getOrderDetails().get(1));
        }
        if (order.getUserCoupon() != null) {
            couponName = order.getUserCoupon().getCoupon().getName();
            couponValue = order.getUserCoupon().getCoupon().getFaceValue();
        }
        return dispatch();
    }


    /**
     * 行程订单详情
     *
     * @return
     */
    public Result planOrderDetail() {
        //
        order = orderService.get(orderId);
        if (order.getOrderDetails().isEmpty()) {
            return dispatch();
        }
        orderDetailResponse = orderDetailResponseService.orderToResponse(order.getOrderDetails().get(0));
        for (OrderDetail orderDetail : order.getOrderDetails()) {
            //
            switch (orderDetail.getProductType()) {
                //
                case flight:
                    planeDetailList.add(orderDetailResponseService.orderToTrafficDetail(orderDetail));
                    break;
                case train:
                    trainDetailList.add(orderDetailResponseService.orderToTrafficDetail(orderDetail));
                    break;
                case hotel:
                    hotelDetailList.add(orderDetailResponseService.orderToHotelDetail(orderDetail));
                    break;
                case scenic:
                    ticketDetailList.add(orderDetailResponseService.orderToTicketDetail(orderDetail));
                    break;
                default:
                    break;
            }
        }
        if (order.getUserCoupon() != null) {
            couponName = order.getUserCoupon().getCoupon().getName();
            couponValue = order.getUserCoupon().getCoupon().getFaceValue();
        }
        return dispatch();
    }

    //监听订单状态接口
    public Result orderStatus() {
        order = orderService.get(orderId);
        String orderStatus = order.getStatus().toString();
        String type = "";
        if ("train".equals(order.getOrderType().toString())
                || "flight".equals(order.getOrderType().toString())) {
            type = order.getOrderType().toString() + order.getOrderDetails().size();
        } else {
            type = order.getOrderType().toString();
        }
        List<String> list = new ArrayList<>();
        list.add(orderStatus);
        list.add(type);
        return json(JSONArray.fromObject(list));
    }

    public Result validateCreditCart() {
        CreditCardValidateResult credit = elongHotelService.validateCreditCart(cardNum);
        Map<String, Object> m = new HashMap<>();
        m.put("success", credit.getResult().isIsValid());
        return json(JSONObject.fromObject(m));
    }

    /**
     * 把136分钟转化为2h16m
     *
     * @param flightTime
     * @return
     */
    private String timeToString(Long flightTime) {
        //
        Long time = flightTime;
        Long day = time / (60 * 24);
        time -= day * 24 * 60;
        Long hour = time / 60;
        time -= hour * 60;
        Long minute = time;
        String flightTimeString = "";
        if (day.intValue() != 0) {
            flightTimeString += day + "d";
        }
        if (hour.intValue() != 0) {
            flightTimeString += hour + "h";
        }
        if (minute.intValue() != 0) {
            flightTimeString += minute + "m";
        }
        return flightTimeString;
    }

    private String processCityName(String name) {
        //
        String[] names = name.split("市");
        return names[0];
    }

//    private String formatDate(Date date) {
//        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
//        return dateFormater.format(date);
//    }

    private String formatDay(Date date) {
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormater.format(date);
    }

    private Date getDate(String dateString) {
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormater.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

}
