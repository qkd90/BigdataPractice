package com.data.data.hmly.action.mobile;

import com.data.data.hmly.action.mobile.request.OrderUpdateRequest;
import com.data.data.hmly.action.mobile.request.SimpleTourist;
import com.data.data.hmly.action.mobile.response.*;
import com.data.data.hmly.service.OrderMobileService;
import com.data.data.hmly.service.common.entity.enums.ProductType;
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
import com.data.data.hmly.service.order.OrderService;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.sales.InsuranceService;
import com.data.data.hmly.service.sales.entity.Insurance;
import com.data.data.hmly.service.ticket.TicketDatepriceService;
import com.data.data.hmly.service.ticket.TicketPriceService;
import com.data.data.hmly.service.ticket.entity.TicketDateprice;
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
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by huangpeijie on 2016-04-13,0013.
 */
public class OrderWebAction extends MobileBaseAction {
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

    private final ObjectMapper mapper = new ObjectMapper();

    public Member user;
    public Long orderId;
    public Integer status;
    public String cardNum;
    public Integer pageNo;
    public Integer pageSize;
    public String json;
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
        Order order = orderMobileService.saveOrder(orderUpdateRequest, user);
        result.put("success", true);
        JSONObject o = new JSONObject();
        o.put("id", order.getId());
        o.put("price", order.getPrice());
        o.put("createTime", order.getCreateTime());
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

    /**
     * 订单列表
     *
     * @return
     */
    @AjaxCheck
    @NeedLogin
    public Result list() throws LoginException {
        user = getLoginUser();
        if (status == null) {
            status = 1;
        }
        Page page = new Page(pageNo, pageSize);
        List<Order> orders = orderService.searchMyOrder(user.getId(), status, page);
        List<OrderSimpleResponse> responses = Lists.transform(orders, new Function<Order, OrderSimpleResponse>() {
            @Override
            public OrderSimpleResponse apply(Order input) {
                return orderMobileService.orderToSimpleResponse(input);
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
        Boolean success = orderMobileService.saveTourist(touristUpdateRequest, user);
        result.put("success", success);
        return json(JSONObject.fromObject(result));
    }

    /**
     * 常用旅客列表
     *
     * @return
     */
    @AjaxCheck
    @NeedLogin
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
            Float rebate = linetypepricedate.getRebate() == null ? 0f : linetypepricedate.getRebate();
            result.put("adultPrice", linetypepricedate.getDiscountPrice() + rebate);
            if (linetypepricedate.getChildPrice() == null) {
                result.put("childPrice", 0);
            } else {
                Float childRebate = linetypepricedate.getChildRebate() == null ? 0f : linetypepricedate.getChildRebate();
                result.put("childPrice", linetypepricedate.getChildPrice() + childRebate);
            }
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
    public Result getTicketDatePriceList() throws ParseException {
        String dateString = DateUtils.formatShortDate(new Date());
        Date date = DateUtils.parseShortTime(dateString);
        date = DateUtils.add(date, Calendar.DAY_OF_MONTH, 1);
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
        List<HotelPriceCalendar> calendarList = hotelPriceService.findTypePriceDate(hotelPriceId, start, DateUtils.add(end, Calendar.DAY_OF_MONTH, -1), 1);
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
        List<HotelPriceCalendar> calendarList = hotelPriceService.findTypePriceDate(hotelPriceId, start, DateUtils.add(end, Calendar.DAY_OF_MONTH, -1), num);
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
        Order order = orderMobileService.saveBalanceOrder(orderUpdateRequest, user);
        result.put("success", true);
        JSONObject o = new JSONObject();
        o.put("id", order.getId());
        o.put("price", order.getPrice());
        o.put("createTime", order.getCreateTime());
        result.put("order", o);
        return jsonResult(result);
    }
}
