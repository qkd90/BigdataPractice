package com.data.data.hmly.action.yhypc;

import com.data.data.hmly.action.yhypc.request.OrderUpdateRequest;
import com.data.data.hmly.action.yhypc.vo.CruiseshipOrderRoomRequest;
import com.data.data.hmly.service.FerryWebService;
import com.data.data.hmly.service.MemberService;
import com.data.data.hmly.service.OrderWebService;
import com.data.data.hmly.service.common.ProductimageService;
import com.data.data.hmly.service.common.entity.Productimage;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.cruiseship.CruiseShipDateService;
import com.data.data.hmly.service.cruiseship.CruiseShipRoomService;
import com.data.data.hmly.service.cruiseship.entity.CruiseShip;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipDate;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipRoom;
import com.data.data.hmly.service.cruiseship.entity.enums.CruiseShipRoomType;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.hotel.HotelPriceService;
import com.data.data.hmly.service.hotel.HotelService;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.hotel.entity.HotelPrice;
import com.data.data.hmly.service.hotel.entity.HotelPriceCalendar;
import com.data.data.hmly.service.order.OrderService;
import com.data.data.hmly.service.order.YhyOrderService;
import com.data.data.hmly.service.order.entity.FerryOrder;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.OrderTourist;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.order.entity.enums.OrderType;
import com.data.data.hmly.service.order.util.FerryUtil;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.data.hmly.service.ticket.TicketDatepriceService;
import com.data.data.hmly.service.ticket.TicketPriceService;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.data.data.hmly.service.ticket.entity.TicketDateprice;
import com.data.data.hmly.service.ticket.entity.TicketPrice;
import com.data.data.hmly.service.user.TouristService;
import com.data.data.hmly.service.user.entity.Tourist;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.struts.AjaxCheck;
import com.google.common.collect.Lists;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.DateUtils;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.MD5;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangpeijie on 2017-01-03,0003.
 */
public class OrderWebAction extends YhyAction {
    @Resource
    private HotelService hotelService;
    @Resource
    private HotelPriceService hotelPriceService;
    @Resource
    private TicketPriceService ticketPriceService;
    @Resource
    private ProductimageService productimageService;
    @Resource
    private TicketDatepriceService ticketDatepriceService;
    @Resource
    private OrderService orderService;
    @Resource
    private YhyOrderService yhyOrderService;
    @Resource
    private MemberService memberService;
    @Resource
    private TouristService touristService;
    @Resource
    private CruiseShipDateService cruiseShipDateService;
    @Resource
    private CruiseShipRoomService cruiseShipRoomService;
    @Resource
    private FerryWebService ferryWebService;
    @Resource
    private OrderWebService orderWebService;

    private final ObjectMapper mapper = new ObjectMapper();

    public Member user;

    //酒店
    public Long hotelPriceId;
    public Long hotelId;
    public String startDate;
    public String endDate;
    public Integer num;
    public String checkCode;
    public HotelPrice hotelPrice;
    public Hotel hotel;

    //海上休闲、景点
    private Long ticketPriceId;
    public Long ticketDatePriceId;
    private Long ticketId;
    private Ticket ticket;
    private TicketPrice ticketPrice;
    public TicketDateprice ticketDateprice;
    public ScenicInfo scenicInfo;

    //游轮旅游
    private Long dateId;
    private Long cruiseshiId;
    private CruiseShip cruiseShip = new CruiseShip();
    private CruiseShipRoomType roomType;
    private List<CruiseShipRoom> cruiseShipRoomList = Lists.newArrayList();
    private List<CruiseshipOrderRoomRequest> cruiseshipOrderRoomRequests = Lists.newArrayList();
    private Float totalPrice;

    public String json;
    public String ferryJson;
    public Long orderId;
    public Order order;
    public OrderDetail orderDetail;
    public OrderUpdateRequest orderUpdateRequest;

    public String payPassword;


    private Map<String, Object> map = new HashMap<String, Object>();

    //    @NeedLogin
    public Result save() throws Exception {
        orderUpdateRequest = mapper.readValue(json, OrderUpdateRequest.class);
        if (OrderType.hotel.name().equals(orderUpdateRequest.getOrderType())) {
            String checkNum = (String) getSession().getAttribute("orderCheckNum");
            if (!checkNum.equals(checkCode)) {
                result.put("success", false);
                result.put("errMsg", "验证码错误");
                return jsonResult(result);
            }
        }
        Member user = getLoginUser();
        if (user == null) {
            result.put("success", false);
            result.put("errMsg", "未登录");
            return jsonResult(result);
        }
        FerryOrder ferryOrder = null;
        if (StringUtils.isNotBlank(ferryJson)) {
            if (user.getFerryMember() == null) {
                result.put("success", false);
                result.put("noMember", true);
                result.put("errMsg", "未绑定轮渡账号");
                return jsonResult(result);
            }
            if (!user.getFerryMember().getIsReal()) {
                result.put("success", false);
                result.put("noReal", true);
                result.put("errMsg", "账号未实名");
                return jsonResult(result);
            }
            Map<String, Object> data = mapper.readValue(ferryJson, Map.class);
            Map<String, Object> map = ferryWebService.saveOrder(data, user, true);
            if (!(Boolean) map.get("success")) {
                return jsonResult(map);
            }
            ferryOrder = (FerryOrder) map.get("order");
        }
        Order order = orderWebService.saveOrder(orderUpdateRequest, user, ferryOrder);
        if (order.getDeleteFlag()) {
            result.put("success", false);
            if (ferryOrder != null) {
                FerryUtil.payNotify(ferryOrder.getFerryNumber(), false);
            }
            if (order.getPrice() == 0f) {
                result.put("errMsg", "订单价格为0（不包含到付、担保酒店）");
            } else {
                result.put("errMsg", "订单中商品库存不足");
            }
            return jsonResult(result);
        }
        if (order.getOrderType().equals(OrderType.hotel) && order.getStatus().equals(OrderStatus.PAYED)) {
            yhyOrderService.doTakeOrder(order.getId());
        }
        result.put("success", true);
        JSONObject o = new JSONObject();
        o.put("id", order.getId());
        o.put("orderNo", order.getOrderNo());
        o.put("price", order.getPrice());
        o.put("createTime", order.getCreateTime());
        o.put("status", order.getStatus().name());
        result.put("order", o);
        return jsonResult(result);
    }

    public Result saveBalanceOrder() throws Exception {
        orderUpdateRequest = mapper.readValue(json, OrderUpdateRequest.class);
        user = getLoginUser();
        Member member = memberService.get(user.getId());
        if (OrderType.withdraw.name().equals(orderUpdateRequest.getOrderType()) && (StringUtils.isBlank(user.getPayPassword()) || StringUtils.isBlank(payPassword) || !user.getPayPassword().equals(MD5.caiBeiMD5(payPassword)))) {
            result.put("success", false);
            result.put("errMsg", "支付密码错误");
            return jsonResult(result);
        }
        Float price = Float.valueOf(orderUpdateRequest.getDetails().get(0).get("price").toString());
        if (OrderType.withdraw.name().equals(orderUpdateRequest.getOrderType()) && (price < 1 || price > member.getBalance())) {
            result.put("success", false);
            result.put("errMsg", "提现金额错误");
            return jsonResult(result);
        }
        if (price < 0) {
            result.put("success", false);
            result.put("errMsg", "金额错误");
            return jsonResult(result);
        }
        Order order = orderWebService.saveBalanceOrder(orderUpdateRequest, user);
        result.put("success", true);
        JSONObject o = new JSONObject();
        o.put("id", order.getId());
        o.put("price", order.getPrice());
        o.put("createTime", order.getCreateTime());
        result.put("order", o);
        return jsonResult(result);
    }

    public Result orderStatus() {
        Order order = orderService.get(orderId);
        result.put("success", true);
        result.put("status", order.getStatus().name());
        return jsonResult(result);
    }


    public Result orderCruiseshipRoom() {

        if (dateId == null) {
            return dispatch404();
        }
        CruiseShipDate cruiseShipDate = cruiseShipDateService.findById(dateId);
        cruiseShip = cruiseShipDate.getCruiseShip();
        cruiseShip.setDateId(dateId);
        cruiseShip.setStartDate(cruiseShipDate.getDate());
        cruiseShip.setEndDate(DateUtils.getEndDay(cruiseShipDate.getDate(), cruiseShip.getPlayDay()));
        cruiseShipRoomList = cruiseShipRoomService.getRoomTypeList(dateId, new Date());
        return dispatch();
    }


    public Result orderCruiseshipWrite() {
        if (dateId == null) {
            return dispatch404();
        }
        CruiseShipDate cruiseShipDate = cruiseShipDateService.findById(dateId);
        cruiseShip = cruiseShipDate.getCruiseShip();
        cruiseShip.setDateId(dateId);
        cruiseShip.setStartDate(cruiseShipDate.getDate());
        if (cruiseshipOrderRoomRequests.isEmpty()) {
            return dispatch404();
        }

        if (totalPrice == 0 || totalPrice == null) {
            return dispatch404();
        }
        return dispatch();
    }


    public Result orderHotelWrite() {
        hotelPrice = hotelPriceService.get(hotelPriceId);
        hotel = hotelPrice.getHotel();
        Productimage productimage = productimageService.findCover(hotel.getId(), null, ProductType.hotel);
        if (productimage != null) {
            hotel.setCover(productimage.getCompletePath());
        }
        return dispatch();
    }

    public Result orderSailboatPay() {
        Member member = getLoginUser();
        user = memberService.get(member.getId());
        order = orderService.get(orderId);
        if (order.getWaitTime() != null) {
            order.setWaitSeconds(Long.valueOf(DateUtils.getDateDiffLong(order.getWaitTime(), new Date()) / 1000).intValue());
        }
        orderDetail = order.getOrderDetails().get(0);
        Productimage productimage = productimageService.findCover(orderDetail.getProduct().getId(), null, ProductType.scenic);
        if (productimage != null) {
            orderDetail.getProduct().setImgUrl(productimage.getCompletePath());
        }
        return dispatch();
    }

    public Result orderCruiseshipPay() {
        Member member = getLoginUser();
        user = memberService.get(member.getId());
        order = orderService.get(orderId);
        if (order.getWaitTime() != null) {
            order.setWaitSeconds(Long.valueOf(DateUtils.getDateDiffLong(order.getWaitTime(), new Date()) / 1000).intValue());
        }
        orderDetail = order.getOrderDetails().get(0);
        Productimage productimage = productimageService.findCover(orderDetail.getProduct().getId(), null, ProductType.cruiseship);
        if (productimage != null) {
            orderDetail.getProduct().setImgUrl(productimage.getCompletePath());
        }
        return dispatch();
    }

    public Result orderSailboatWrite() throws ParseException {
        ticketPrice = ticketPriceService.getPrice(ticketPriceId);
        String dateString = DateUtils.formatShortDate(new Date());
        Date date = DateUtils.parseShortTime(dateString);
        date = DateUtils.add(date, Calendar.DAY_OF_MONTH, 1);
        List<TicketDateprice> ticketDatepriceList = ticketDatepriceService.findTypePriceDate(ticketPriceId, date, null, 1);
        if (!ticketDatepriceList.isEmpty()) {
            ticketDateprice = ticketDatepriceList.get(0);
        }
        return dispatch();
    }

    public Result orderTicketWrite() throws ParseException {
        ticketPrice = ticketPriceService.getPrice(ticketPriceId);
        String dateString = DateUtils.formatShortDate(new Date());
        Date date = DateUtils.parseShortTime(dateString);
        date = DateUtils.add(date, Calendar.DAY_OF_MONTH, 1);
        List<TicketDateprice> ticketDatepriceList = ticketDatepriceService.findTypePriceDate(ticketPriceId, date, null, 1);
        if (!ticketDatepriceList.isEmpty()) {
            ticketDateprice = ticketDatepriceList.get(0);
        }
        return dispatch();
    }

    public Result orderTicketPay() {
        Member member = getLoginUser();
        user = memberService.get(member.getId());
        order = orderService.get(orderId);
        if (order.getWaitTime() != null) {
            order.setWaitSeconds(Long.valueOf(DateUtils.getDateDiffLong(order.getWaitTime(), new Date()) / 1000).intValue());
        }
        orderDetail = order.getOrderDetails().get(0);
        scenicInfo = ((Ticket) orderDetail.getProduct()).getScenicInfo();
        return dispatch();
    }


    public Result getCustomerList() {
        Member member = getLoginUser();
        if (member == null) {
            member = memberService.get(100802L);
        }
        List<Tourist> touristList = touristService.getMyTourist(member, "", null);
        map.put("touristList", touristList);
        simpleResult(map, true, "");
        return json(JSONObject.fromObject(map, JsonFilter.getIncludeConfig()));
    }

    public Result getCustomerInfo() {
        String touristId = (String) getParameter("touristId");
        if (StringUtils.isBlank(touristId)) {
            simpleResult(map, false, "");
            return json(JSONObject.fromObject(map, JsonFilter.getIncludeConfig()));
        }
        Tourist tourist = touristService.get(Long.parseLong(touristId));
        map.put("tourist", tourist);
        simpleResult(map, true, "");
        return json(JSONObject.fromObject(map, JsonFilter.getIncludeConfig()));
    }

    public Result orderSailboatSuccess() {
        /*hotelPrice = hotelPriceService.get(hotelPriceId);
        hotel = hotelPrice.getHotel();
        Productimage productimage = productimageService.findCover(hotel.getId(), null, ProductType.hotel);
        if (productimage != null) {
            hotel.setCover(productimage.getCompletePath());
        }*/
        return dispatch();
    }

    @AjaxCheck
    public Result getTicketDatePriceList() throws ParseException {
        String dateString = DateUtils.formatShortDate(new Date());
        Date date = DateUtils.parseShortTime(dateString);
        date = DateUtils.add(date, Calendar.DAY_OF_MONTH, 1);
        List<TicketDateprice> ticketDatepriceList = ticketDatepriceService.findTypePriceDate(ticketPriceId, date, null, 1);
        result.put("ticketDatepriceList", ticketDatepriceList);
        result.put("success", true);
        return json(JSONObject.fromObject(result, JsonFilter.getIncludeConfig()));
    }

    public Result hotelPriceCalendar() throws ParseException {
        Date start = DateUtils.parseShortTime(startDate);
        Date end = DateUtils.parseShortTime(endDate);
        List<HotelPriceCalendar> hotelPriceCalendarList = hotelPriceService.findTypePriceDate(hotelPriceId, start, end, 1);
        if (hotelPriceCalendarList.size() == DateUtils.getDateDiff(start, end)) {
            result.put("success", true);
            result.put("calendarList", hotelPriceCalendarList);
        } else {
            result.put("success", false);
        }
        return json(JSONObject.fromObject(result, JsonFilter.getIncludeConfig()));
    }

    //    @AjaxCheck
//    @NeedLogin
    public Result checkHotelOrder() throws ParseException {
        Date start = DateUtils.parseShortTime(startDate);
        Date end = DateUtils.parseShortTime(endDate);
        List<HotelPriceCalendar> calendarList = hotelPriceService.findTypePriceDate(hotelPriceId, start, end, num);
        result.put("success", calendarList.size() == DateUtils.getDateDiff(start, end));
        return json(JSONObject.fromObject(result));
    }

    public Result checkTicketOrder() throws ParseException {
        TicketDateprice ticketDateprice = ticketDatepriceService.load(ticketDatePriceId);
        if (ticketDateprice.getInventory() != null && ticketDateprice.getInventory() > -1 && ticketDateprice.getInventory() < num) {
            result.put("success", false);
        } else {
            result.put("success", true);
        }
        return json(JSONObject.fromObject(result));
    }

    public Result orderHotelGuarantee() {
        return dispatch();
    }

    public Result orderHotelPay() {
        Member member = getLoginUser();
        user = memberService.get(member.getId());
        order = orderService.get(orderId);
        if (order.getWaitTime() != null) {
            order.setWaitSeconds(Long.valueOf(DateUtils.getDateDiffLong(order.getWaitTime(), new Date()) / 1000).intValue());
        }
        orderDetail = order.getOrderDetails().get(0);
        Productimage productimage = productimageService.findCover(orderDetail.getProduct().getId(), null, ProductType.hotel);
        if (productimage != null) {
            orderDetail.getProduct().setImgUrl(productimage.getCompletePath());
        }
        return dispatch();
    }

    public Result orderPlan() {
        return dispatch();
    }

    public Result orderPlanPay() {
        Member member = getLoginUser();
        user = memberService.get(member.getId());
        order = orderService.get(orderId);
        if (order.getWaitTime() != null) {
            order.setWaitSeconds(Long.valueOf(DateUtils.getDateDiffLong(order.getWaitTime(), new Date()) / 1000).intValue());
        }
        List<OrderTourist> touristList = Lists.newArrayList();
        for (OrderTourist orderTourist : order.getOrderTourists()) {
            Boolean inList = false;
            for (OrderTourist tourist : touristList) {
                if (orderTourist.getName().equals(tourist.getName())) {
                    inList = true;
                    break;
                }
            }
            if (!inList) {
                touristList.add(orderTourist);
            }
        }
        order.setTouristList(touristList);
        return dispatch();
    }

    public Long getTicketPriceId() {
        return ticketPriceId;
    }

    public void setTicketPriceId(Long ticketPriceId) {
        this.ticketPriceId = ticketPriceId;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public TicketPrice getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(TicketPrice ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Long getDateId() {
        return dateId;
    }

    public void setDateId(Long dateId) {
        this.dateId = dateId;
    }

    public CruiseShip getCruiseShip() {
        return cruiseShip;
    }

    public void setCruiseShip(CruiseShip cruiseShip) {
        this.cruiseShip = cruiseShip;
    }

    public List<CruiseShipRoom> getCruiseShipRoomList() {
        return cruiseShipRoomList;
    }

    public void setCruiseShipRoomList(List<CruiseShipRoom> cruiseShipRoomList) {
        this.cruiseShipRoomList = cruiseShipRoomList;
    }

    public Long getCruiseshiId() {
        return cruiseshiId;
    }

    public void setCruiseshiId(Long cruiseshiId) {
        this.cruiseshiId = cruiseshiId;
    }

    public List<CruiseshipOrderRoomRequest> getCruiseshipOrderRoomRequests() {
        return cruiseshipOrderRoomRequests;
    }

    public void setCruiseshipOrderRoomRequests(List<CruiseshipOrderRoomRequest> cruiseshipOrderRoomRequests) {
        this.cruiseshipOrderRoomRequests = cruiseshipOrderRoomRequests;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }
}
