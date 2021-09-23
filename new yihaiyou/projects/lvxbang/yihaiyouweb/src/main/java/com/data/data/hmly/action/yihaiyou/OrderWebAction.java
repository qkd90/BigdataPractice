package com.data.data.hmly.action.yihaiyou;

import com.data.data.hmly.action.yihaiyou.request.OrderUpdateRequest;
import com.data.data.hmly.action.yihaiyou.request.SimpleTourist;
import com.data.data.hmly.action.yihaiyou.response.CouponResponse;
import com.data.data.hmly.action.yihaiyou.response.CouponRuleResponse;
import com.data.data.hmly.action.yihaiyou.response.CruiseShipOrderResponse;
import com.data.data.hmly.action.yihaiyou.response.HotelOrderResponse;
import com.data.data.hmly.action.yihaiyou.response.InsuranceResponse;
import com.data.data.hmly.action.yihaiyou.response.OrderDetailSimpleResponse;
import com.data.data.hmly.action.yihaiyou.response.OrderResponse;
import com.data.data.hmly.action.yihaiyou.response.OrderSimpleResponse;
import com.data.data.hmly.action.yihaiyou.response.OrderTicketResponse;
import com.data.data.hmly.action.yihaiyou.response.SailOrderResponse;
import com.data.data.hmly.action.yihaiyou.response.TicketOrderResponse;
import com.data.data.hmly.service.FerryMobileService;
import com.data.data.hmly.service.OrderMobileService;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.cruiseship.CruiseShipRoomDateService;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipRoomDate;
import com.data.data.hmly.service.discount.UserCouponService;
import com.data.data.hmly.service.elong.ElongHotelService;
import com.data.data.hmly.service.elong.pojo.CreditCardValidateResult;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.UserStatus;
import com.data.data.hmly.service.hotel.HotelPriceService;
import com.data.data.hmly.service.hotel.entity.HotelPrice;
import com.data.data.hmly.service.hotel.entity.HotelPriceCalendar;
import com.data.data.hmly.service.hotel.entity.enums.PriceStatus;
import com.data.data.hmly.service.line.LinedaysProductPriceService;
import com.data.data.hmly.service.line.LinetypepriceService;
import com.data.data.hmly.service.line.LinetypepricedateService;
import com.data.data.hmly.service.line.entity.LinedaysProductPrice;
import com.data.data.hmly.service.line.entity.Linetypeprice;
import com.data.data.hmly.service.line.entity.Linetypepricedate;
import com.data.data.hmly.service.lvxbang.DiscountService;
import com.data.data.hmly.service.lxbcommon.entity.UserCoupon;
import com.data.data.hmly.service.lxbcommon.entity.enums.UserCouponStatus;
import com.data.data.hmly.service.order.FerryOrderService;
import com.data.data.hmly.service.order.OrderDetailService;
import com.data.data.hmly.service.order.OrderService;
import com.data.data.hmly.service.order.YhyOrderService;
import com.data.data.hmly.service.order.entity.FerryOrder;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderAll;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.order.entity.enums.OrderType;
import com.data.data.hmly.service.order.util.FerryUtil;
import com.data.data.hmly.service.sales.InsuranceService;
import com.data.data.hmly.service.sales.entity.Insurance;
import com.data.data.hmly.service.ticket.TicketDatepriceService;
import com.data.data.hmly.service.ticket.TicketPriceService;
import com.data.data.hmly.service.ticket.entity.TicketDateprice;
import com.data.data.hmly.service.ticket.entity.TicketPrice;
import com.data.data.hmly.service.user.TouristService;
import com.data.data.hmly.service.user.entity.Tourist;
import com.data.data.hmly.service.wechat.WechatDataImgTextService;
import com.data.data.hmly.service.wechat.entity.WechatDataNews;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.hibernate.util.Page;
import com.framework.struts.AjaxCheck;
import com.framework.struts.NeedLogin;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.DateUtils;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.MD5;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by huangpeijie on 2016-04-13,0013.
 */
public class OrderWebAction extends BaseAction {
    @Resource
    private TouristService touristService;
    @Resource
    private OrderService orderService;
    @Resource
    private OrderMobileService orderMobileService;
    @Resource
    private UserCouponService userCouponService;
    @Resource
    private DiscountService discountService;
    @Resource
    private WechatDataImgTextService wechatDataImgTextService;
    @Resource
    private ElongHotelService elongHotelService;
    @Resource
    private InsuranceService insuranceService;
    @Resource
    private LinetypepriceService linetypepriceService;
    @Resource
    private LinetypepricedateService linetypepricedateService;
    @Resource
    private TicketDatepriceService ticketDatepriceService;
    @Resource
    private TicketPriceService ticketPriceService;
    @Resource
    private HotelPriceService hotelPriceService;
    @Resource
    private LinedaysProductPriceService linedaysProductPriceService;
    @Resource
    private CruiseShipRoomDateService cruiseShipRoomDateService;
    @Resource
    private YhyOrderService yhyOrderService;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private FerryMobileService ferryMobileService;
    @Resource
    private FerryOrderService ferryOrderService;

    private final ObjectMapper mapper = new ObjectMapper();

    public Member user;
    public Long orderId;
    public String cardNum;
    public Integer pageNo;
    public Integer pageSize;
    public String json;
    public String ferryJson;
    public OrderUpdateRequest orderUpdateRequest;
    public SimpleTourist touristUpdateRequest;
    public Long linetypepriceId;
    public String lineStartDate;

    public Long ticketDatePriceId;
    public Long ticketPriceId;
    public Integer num;

    public Long hotelPriceId;
    public String startDate;
    public String endDate;

    public String type;
    public Boolean hasComment;
    public String orderColumn;
    public String orderBy;
    public String status;

    public String payPassword;

    public Long touristId;
    public List<ProductType> types = Lists.newArrayList(ProductType.scenic, ProductType.hotel, ProductType.sailboat, ProductType.yacht, ProductType.huanguyou, ProductType.cruiseship);

    /**
     * 保存订单
     *
     * @return
     */
    @AjaxCheck
    @NeedLogin
    public Result save() throws Exception {
//        objectMapper.enableDefaultTyping();
        orderUpdateRequest = mapper.readValue(json, OrderUpdateRequest.class);

        user = getLoginUser();
        if (UserStatus.blacklist.equals(user.getStatus())) {
            result.put("success", false);
            result.put("errMsg", "该用户在黑名单中");
            return jsonResult(result);
        }
        FerryOrder ferryOrder = null;
        if (StringUtils.isNotBlank(ferryJson)) {
            if (user.getFerryMember() == null || !user.getFerryMember().getIsReal()) {
                result.put("success", false);
                result.put("noReal", true);
                result.put("errMsg", "用户未实名");
                return jsonResult(result);
            }
            Map<String, Object> data = mapper.readValue(ferryJson, Map.class);
            Map<String, Object> map = ferryMobileService.saveOrder(data, user, true);
            if (!(Boolean) map.get("success")) {
                return jsonResult(map);
            }
            ferryOrder = (FerryOrder) map.get("order");
        }
        Order order = orderMobileService.saveOrder(orderUpdateRequest, user, ferryOrder);
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
        o.put("price", order.getPrice());
        o.put("createTime", order.getCreateTime());
        o.put("status", order.getStatus().name());
        result.put("order", o);
        return jsonResult(result);
    }

    /**
     * 验证信用卡号
     *
     * @return
     */
    @AjaxCheck
    public Result validateCreditCart() {
        CreditCardValidateResult credit = elongHotelService.validateCreditCart(cardNum);
        result.put("success", credit.getResult().isIsValid());
        return json(JSONObject.fromObject(result));
    }

    @AjaxCheck
    @NeedLogin
    public Result personalList() {
        user = getLoginUser();
        Page page = new Page(pageNo, pageSize);
        Order order = new Order();
        order.setUser(user);
        order.setHasComment(hasComment);
        order.setDeleteFlag(false);
        List<OrderType> orderTypeList = Lists.newArrayList();
        if (StringUtils.isNotBlank(type)) {
            String[] types = type.split(",");
            for (String s : types) {
                orderTypeList.add(OrderType.valueOf(s));
            }
        }
        if (StringUtils.isNotBlank(status)) {
            order.setStatus(OrderStatus.valueOf(status));
        }
        List<OrderAll> orders;
        if (orderColumn == null) {
            orders = orderService.searchMyOrderAll(order, orderTypeList, page);
        } else {
            orders = orderService.searchMyOrderAll(order, orderTypeList, page, orderColumn, orderBy);
        }
        List<OrderSimpleResponse> responses = Lists.transform(orders, new Function<OrderAll, OrderSimpleResponse>() {
            @Override
            public OrderSimpleResponse apply(OrderAll input) {
                return new OrderSimpleResponse(input);
            }
        });
        if (page.getPageIndex() >= page.getPageCount()) {
            result.put("nomore", true);
        } else {
            result.put("nomore", false);
        }
        result.put("orderList", responses);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    @AjaxCheck
    @NeedLogin
    public Result personalNoCommentList() {
        user = getLoginUser();
        Page page = new Page(pageNo, pageSize);
        List<OrderDetail> orderDetails = orderDetailService.getNoCommentList(user.getId(), types, page);
        List<OrderDetailSimpleResponse> responses = Lists.transform(orderDetails, new Function<OrderDetail, OrderDetailSimpleResponse>() {
            @Override
            public OrderDetailSimpleResponse apply(OrderDetail input) {
                return orderMobileService.orderDetailToResponse(input);
            }
        });
        responses.removeAll(Collections.singleton(null));
        if (page.getPageIndex() >= page.getPageCount()) {
            result.put("nomore", true);
        } else {
            result.put("nomore", false);
        }
        result.put("orderList", responses);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    /**
     * 订单详情
     *
     * @return
     */
    @AjaxCheck
    @NeedLogin
    public Result detail() throws LoginException {
        Order order = orderService.get(orderId);
        user = getLoginUser();
        if (order == null) {
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        if (!order.getUser().getId().equals(user.getId())) {
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        OrderResponse response = orderMobileService.orderToResponse(order);
        result.put("order", response);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    @AjaxCheck
    @NeedLogin
    public Result simpleDetail() throws LoginException {
        Order order = orderService.get(orderId);
        user = getLoginUser();
        if (order == null) {
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        if (!order.getUser().getId().equals(user.getId())) {
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        OrderSimpleResponse response = new OrderSimpleResponse(order);
        result.put("order", response);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    /**
     * 删除订单
     *
     * @return
     */
    @AjaxCheck
    @NeedLogin
    public Result delete() throws LoginException {
        user = getLoginUser();
        Order order = orderService.get(orderId);
        if (order == null) {
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        if (!order.getUser().getId().equals(user.getId())) {
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        orderService.delByIds(orderId.toString(), user);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    /**
     * 退款
     *
     * @return
     */
    @AjaxCheck
    @NeedLogin
    public Result refund() {
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    /**
     * 确认退款
     *
     * @return
     */
    @AjaxCheck
    @NeedLogin
    public Result refundConfirm() {
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    /**
     * 保存常用旅客
     *
     * @return
     */
    @AjaxCheck
    @NeedLogin
    public Result saveTourist() throws IOException, LoginException {
        touristUpdateRequest = mapper.readValue(json, SimpleTourist.class);
        user = getLoginUser();
        Integer resultNum = orderMobileService.saveTourist(touristUpdateRequest, user);
        if (resultNum == 0) {
            result.put("success", true);
        } else {
            result.put("success", false);
            if (resultNum == -1) {
                result.put("errMsg", "该出行人已存在");
            }
        }
        return json(JSONObject.fromObject(result));
    }

    @AjaxCheck
    @NeedLogin
    public Result deleteTourist() throws IOException, LoginException {
        user = getLoginUser();
        touristService.delById(touristId, user);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }


    /**
     * 常用旅客列表
     *
     * @return
     */
    @AjaxCheck
//    @NeedLogin
    public Result touristList() throws LoginException {
        user = getLoginUser();
        List<Tourist> tourists = touristService.getMyTourist(user, null, null);
        List<SimpleTourist> touristList = Lists.transform(tourists, new Function<Tourist, SimpleTourist>() {
            @Override
            public SimpleTourist apply(Tourist input) {
                return new SimpleTourist(input);
            }
        });
        result.put("touristList", touristList);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    @AjaxCheck
    @NeedLogin
    public Result touristDetail() {
        user = getLoginUser();
        Tourist tourist = touristService.get(touristId);
        SimpleTourist simpleTourist = new SimpleTourist(tourist);
        result.put("tourist", simpleTourist);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    /**
     * 红包列表
     *
     * @return
     */
    @AjaxCheck
    @NeedLogin
    public Result couponList() throws LoginException {
        user = getLoginUser();
        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setMember(user);
        userCoupon.setStatus(UserCouponStatus.unused);
        List<UserCoupon> unusedList = userCouponService.list(userCoupon, null);
        for (UserCoupon uc : unusedList) {
            discountService.completeTypes(uc);
        }
        List<CouponResponse> unusedCouponList = Lists.transform(unusedList, new Function<UserCoupon, CouponResponse>() {
            @Override
            public CouponResponse apply(UserCoupon input) {
                return new CouponResponse(input);
            }
        });

        userCoupon.setStatus(UserCouponStatus.used);
        List<UserCoupon> usedList = userCouponService.list(userCoupon, null);
        for (UserCoupon uc : usedList) {
            discountService.completeTypes(uc);
        }
        List<CouponResponse> usedCouponlist = Lists.transform(usedList, new Function<UserCoupon, CouponResponse>() {
            @Override
            public CouponResponse apply(UserCoupon input) {
                return new CouponResponse(input);
            }
        });

        List<WechatDataNews> list = wechatDataImgTextService.findListBykeyword("红包使用规则", null);
        if (list.isEmpty()) {
//            result.put("success", false);
            result.put("errorMsg", "没有红包使用规则");
//            return json(JSONObject.fromObject(result));
        } else {
            result.put("rule", new CouponRuleResponse(list.get(0)));
        }
        result.put("unusedCouponList", unusedCouponList);
        result.put("usedCouponlist", usedCouponlist);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    @AjaxCheck
    @NeedLogin
    public Result orderLine() throws ParseException {
        Linetypeprice linetypeprice = linetypepriceService.getLinetypeprice(linetypepriceId);
        Date date = DateUtils.parseShortTime(lineStartDate);
        List<Linetypepricedate> linetypepricedates = linetypepricedateService.findTypePriceDate(linetypepriceId, date, date, 1);
        if (!linetypepricedates.isEmpty()) {
            Linetypepricedate linetypepricedate = linetypepricedates.get(0);
            result.put("adultPrice", linetypepricedate.getDiscountPrice());
            result.put("childPrice", linetypepricedate.getChildPrice());
            result.put("oasiaHotel", linetypepricedate.getOasiaHotel());
        } else {
            result.put("adultPrice", 0);
            result.put("childPrice", 0);
        }
        result.put("line", linetypeprice.getLine());

        List<Insurance> insuranceList = insuranceService.listLineInsurance();
        List<InsuranceResponse> responses = Lists.transform(insuranceList, new Function<Insurance, InsuranceResponse>() {
            @Override
            public InsuranceResponse apply(Insurance input) {
                return new InsuranceResponse(input);
            }
        });

        LinedaysProductPrice linedaysProductPrice = new LinedaysProductPrice();
        linedaysProductPrice.setLineId(linetypeprice.getLine().getId());
        linedaysProductPrice.setProductType(ProductType.hotel);
        List<LinedaysProductPrice> hotelProductPrices = linedaysProductPriceService.list(linedaysProductPrice, null);
        Boolean needGuarantee = false;
        for (LinedaysProductPrice hotelProductPrice : hotelProductPrices) {
            HotelPrice hotelPrice = hotelPriceService.get(hotelProductPrice.getPriceId());
            if (hotelPrice.getStatus().equals(PriceStatus.GUARANTEE)) {
                needGuarantee = true;
                break;
            }
        }
        result.put("insuranceList", responses);
        result.put("needGuarantee", needGuarantee);
        result.put("success", true);
        return json(JSONObject.fromObject(result, JsonFilter.getIncludeConfig()));
    }

    @AjaxCheck
    @NeedLogin
    public Result checkLineOrder() throws ParseException {
        Date date = DateUtils.parseShortTime(lineStartDate);
        List<Linetypepricedate> linetypepricedates = linetypepricedateService.findTypePriceDate(linetypepriceId, date, date, 1);
        result.put("success", !orderService.lineInventoryCheck(linetypepricedates, num).isEmpty());
        return json(JSONObject.fromObject(result));
    }

    @AjaxCheck
    public Result getLineDatePriceList() throws ParseException {
        String dateString = DateUtils.formatShortDate(new Date());
        Date date = DateUtils.parseShortTime(dateString);
        date = DateUtils.add(date, Calendar.DAY_OF_MONTH, 1);
        List<Linetypepricedate> linetypepricedates = linetypepricedateService.findTypePriceDate(linetypepriceId, date, null, 1);
        result.put("linetypepricedates", orderService.lineInventoryCheck(linetypepricedates, 1));
        result.put("success", true);
        return json(JSONObject.fromObject(result, JsonFilter.getIncludeConfig()));
    }

    @AjaxCheck
    @NeedLogin
    public Result orderTicket() {
        TicketDateprice ticketDateprice = ticketDatepriceService.load(ticketDatePriceId);
        OrderTicketResponse response = new OrderTicketResponse(ticketDateprice);
        Map<String, Date> dateMap = ticketDatepriceService.getFirstAndEndDate(ticketDateprice.getTicketPriceId());
        Date start = dateMap.get("start");
        Date end = dateMap.get("end");
        if (start != null && end != null) {
            response.setStartDate(DateUtils.formatShortDate(start));
            response.setEndDate(DateUtils.formatShortDate(end));
        }
        response.setAddInfoList(ticketPriceService.findTicketPriceAddInfoList(ticketDateprice.getTicketPriceId()));
        result.put("ticket", response);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    @AjaxCheck
    @NeedLogin
    public Result checkTicketOrder() throws ParseException {
        TicketDateprice ticketDateprice = ticketDatepriceService.load(ticketDatePriceId);
        if (ticketDateprice.getInventory() != null && ticketDateprice.getInventory() > -1 && ticketDateprice.getInventory() < num) {
            result.put("success", false);
        } else {
            result.put("success", true);
        }
        return json(JSONObject.fromObject(result));
    }

    @AjaxCheck
    @NeedLogin
    public Result ticketOrderDetail() throws LoginException {
        Order order = orderService.get(orderId);
        user = getLoginUser();
        if (order == null) {
            result.put("errMsg", "订单不存在");
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        if (!order.getUser().getId().equals(user.getId())) {
            result.put("errMsg", "该订单不属于当前用户");
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        TicketOrderResponse response = orderMobileService.ticketOrderDetail(order);
        result.put("order", response);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    @AjaxCheck
    @NeedLogin
    public Result sailOrderDetail() {
        Order order = orderService.get(orderId);
        user = getLoginUser();
        if (order == null) {
            result.put("errMsg", "订单不存在");
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        if (!order.getUser().getId().equals(user.getId())) {
            result.put("errMsg", "该订单不属于当前用户");
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        SailOrderResponse response = orderMobileService.sailOrderDetail(order);
        result.put("order", response);
        result.put("success", true);
        return json(JSONObject.fromObject(result, JsonFilter.getIncludeConfig()));
    }

    @AjaxCheck
    public Result getTicketDatePriceList() throws ParseException {
        String dateString = DateUtils.formatShortDate(new Date());
        Date date = DateUtils.parseShortTime(dateString);
        TicketPrice ticketPrice = ticketPriceService.getPrice(ticketPriceId);
        if (!Boolean.TRUE.equals(ticketPrice.getIsTodayValid())) {
            date = DateUtils.add(date, Calendar.DAY_OF_MONTH, 1);
        }
        List<TicketDateprice> ticketDatepriceList = ticketDatepriceService.findTypePriceDate(ticketPriceId, date, null, 1);
        result.put("ticketDatepriceList", ticketDatepriceList);
        result.put("success", true);
        return json(JSONObject.fromObject(result, JsonFilter.getIncludeConfig()));
    }

    @AjaxCheck
    @NeedLogin
    public Result orderHotel() throws ParseException {
        HotelPrice hotelPrice = hotelPriceService.get(hotelPriceId);
        if (!hotelPrice.getStatus().equals(PriceStatus.UP) && !hotelPrice.getStatus().equals(PriceStatus.GUARANTEE)) {
            result.put("errMsg", "该房型不能预订");
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        Date start = DateUtils.parseShortTime(startDate);
        Date end = DateUtils.parseShortTime(endDate);
        List<HotelPriceCalendar> calendarList = hotelPriceService.findTypePriceDate(hotelPriceId, start, end, 1);
        if (calendarList.size() != DateUtils.getDateDiff(start, end)) {
            result.put("errMsg", "酒店库存不足");
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        result.put("hotel", hotelPrice.getHotel());
        result.put("hotelPrice", hotelPrice);
        result.put("priceCalendar", calendarList);
        result.put("success", true);
        return json(JSONObject.fromObject(result, JsonFilter.getIncludeConfig()));
    }

    @AjaxCheck
    @NeedLogin
    public Result checkHotelOrder() throws ParseException {
        Date start = DateUtils.parseShortTime(startDate);
        Date end = DateUtils.parseShortTime(endDate);
        List<HotelPriceCalendar> calendarList = hotelPriceService.findTypePriceDate(hotelPriceId, start, end, num);
        result.put("success", calendarList.size() == DateUtils.getDateDiff(start, end));
        return json(JSONObject.fromObject(result));
    }

    @AjaxCheck
    @NeedLogin
    public Result hotelOrderDetail() throws LoginException {
        Order order = orderService.get(orderId);
        user = getLoginUser();
        if (order == null) {
            result.put("errMsg", "订单不存在");
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        if (!order.getUser().getId().equals(user.getId())) {
            result.put("errMsg", "该订单不属于当前用户");
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        HotelOrderResponse response = orderMobileService.hotelOrderDetail(order);
        result.put("order", response);
        result.put("success", true);
        return json(JSONObject.fromObject(result, JsonFilter.getIncludeConfig()));
    }

    @AjaxCheck
    @NeedLogin
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
        Order order = orderMobileService.saveBalanceOrder(orderUpdateRequest, user);
        result.put("success", true);
        JSONObject o = new JSONObject();
        o.put("id", order.getId());
        o.put("price", order.getPrice());
        o.put("createTime", order.getCreateTime());
        result.put("order", o);
        return jsonResult(result);
    }

    @AjaxCheck
    @NeedLogin
    public Result checkCruiseShipOrder() throws IOException {
        Map<String, Integer> map = mapper.readValue(json, Map.class);
        for (String id : map.keySet()) {
            CruiseShipRoomDate date = cruiseShipRoomDateService.findById(Long.valueOf(id));
            if (date != null && date.getInventory() >= 0 && date.getInventory() < map.get(id)) {
                result.put("errMsg", "房间库存不足");
                result.put("success", false);
                return json(JSONObject.fromObject(result));
            }
        }
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    @AjaxCheck
    @NeedLogin
    public Result cruiseShipOrderDetail() {
        Order order = orderService.get(orderId);
        user = getLoginUser();
        if (order == null) {
            result.put("errMsg", "订单不存在");
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        if (!order.getUser().getId().equals(user.getId())) {
            result.put("errMsg", "该订单不属于当前用户");
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        CruiseShipOrderResponse response = orderMobileService.cruiseShipOrderDetail(order);
        result.put("order", response);
        result.put("success", true);
        return json(JSONObject.fromObject(result, JsonFilter.getIncludeConfig()));
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
