package com.data.data.hmly.service;

import com.data.data.hmly.action.yihaiyou.request.OrderContact;
import com.data.data.hmly.action.yihaiyou.request.OrderUpdateRequest;
import com.data.data.hmly.action.yihaiyou.request.SimpleTourist;
import com.data.data.hmly.action.yihaiyou.response.CruiseShipOrderResponse;
import com.data.data.hmly.action.yihaiyou.response.FerryOrderResponse;
import com.data.data.hmly.action.yihaiyou.response.HotelOrderResponse;
import com.data.data.hmly.action.yihaiyou.response.HotelPriceResponse;
import com.data.data.hmly.action.yihaiyou.response.OrderDetailSimpleResponse;
import com.data.data.hmly.action.yihaiyou.response.OrderResponse;
import com.data.data.hmly.action.yihaiyou.response.OrderSimpleResponse;
import com.data.data.hmly.action.yihaiyou.response.PlanOrderHotelResponse;
import com.data.data.hmly.action.yihaiyou.response.ProValidCodeResponse;
import com.data.data.hmly.action.yihaiyou.response.SailOrderResponse;
import com.data.data.hmly.action.yihaiyou.response.TicketOrderResponse;
import com.data.data.hmly.action.yihaiyou.response.TicketPriceResponse;
import com.data.data.hmly.action.yihaiyou.response.TicketResponse;
import com.data.data.hmly.action.yihaiyou.response.TicketScenicResponse;
import com.data.data.hmly.enums.Gender;
import com.data.data.hmly.service.balance.BalanceService;
import com.data.data.hmly.service.balance.entity.enums.AccountType;
import com.data.data.hmly.service.common.ProValidCodeService;
import com.data.data.hmly.service.common.ProductimageService;
import com.data.data.hmly.service.common.YhyMsgService;
import com.data.data.hmly.service.common.entity.ProValidCode;
import com.data.data.hmly.service.common.entity.Productimage;
import com.data.data.hmly.service.common.entity.enums.ProductSource;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.cruiseship.CruiseShipDateService;
import com.data.data.hmly.service.cruiseship.CruiseShipRoomService;
import com.data.data.hmly.service.cruiseship.entity.CruiseShip;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipDate;
import com.data.data.hmly.service.discount.UserCouponService;
import com.data.data.hmly.service.discount.entity.DiscountContext;
import com.data.data.hmly.service.discount.strategy.CouponStrategy;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.hotel.HotelPriceService;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.hotel.entity.HotelPrice;
import com.data.data.hmly.service.hotel.entity.HotelPriceCalendar;
import com.data.data.hmly.service.line.LineService;
import com.data.data.hmly.service.lxbcommon.entity.UserCoupon;
import com.data.data.hmly.service.lxbcommon.entity.enums.UserCouponStatus;
import com.data.data.hmly.service.order.FerryOrderService;
import com.data.data.hmly.service.order.OrderDetailService;
import com.data.data.hmly.service.order.OrderInvoiceService;
import com.data.data.hmly.service.order.OrderService;
import com.data.data.hmly.service.order.OrderValidateCodeService;
import com.data.data.hmly.service.order.YhyOrderService;
import com.data.data.hmly.service.order.dao.OrderDao;
import com.data.data.hmly.service.order.entity.FerryOrder;
import com.data.data.hmly.service.order.entity.FerryOrderItem;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.OrderInvoice;
import com.data.data.hmly.service.order.entity.OrderTourist;
import com.data.data.hmly.service.order.entity.OrderTouristService;
import com.data.data.hmly.service.order.entity.enums.OrderDetailStatus;
import com.data.data.hmly.service.order.entity.enums.OrderPayType;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.order.entity.enums.OrderTouristPeopleType;
import com.data.data.hmly.service.order.entity.enums.OrderType;
import com.data.data.hmly.service.order.entity.enums.OrderWay;
import com.data.data.hmly.service.order.util.FerryUtil;
import com.data.data.hmly.service.order.util.MsgTemplateKey;
import com.data.data.hmly.service.order.vo.OrderResult;
import com.data.data.hmly.service.sales.InsuranceService;
import com.data.data.hmly.service.sales.entity.Insurance;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.data.hmly.service.ticket.TicketDatepriceService;
import com.data.data.hmly.service.ticket.TicketPriceService;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.data.data.hmly.service.ticket.entity.TicketDateprice;
import com.data.data.hmly.service.ticket.entity.TicketPrice;
import com.data.data.hmly.service.user.TouristService;
import com.data.data.hmly.service.user.entity.Tourist;
import com.data.data.hmly.service.user.entity.enums.TouristPeopleType;
import com.data.data.hmly.service.user.entity.enums.TouristStatus;
import com.data.data.hmly.util.GenOrderNo;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.QiniuUtil;
import com.zuipin.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangpeijie on 2016-04-26,0026.
 */
@Service
public class OrderMobileService {
    @Resource
    private TouristService touristService;
    //    @Resource
//    private MemberService memberService;
    @Resource
    private OrderService orderService;
    @Resource
    private OrderDao orderDao;
    @Resource
    private OrderDetailService orderDetailService;
    //    @Resource
//    private TrafficPriceService trafficPriceService;
    @Resource
    private HotelPriceService hotelPriceService;
    //    @Resource
//    private PlanOrderService planOrderService;
    @Resource
    private TicketPriceService ticketPriceService;
    @Resource
    private UserCouponService userCouponService;
    @Resource
    private OrderInvoiceService orderInvoiceService;
    //    @Resource
//    private OrderInsuranceService orderInsuranceService;
    @Resource
    private LineService lineService;
    @Resource
    private TbAreaService tbAreaService;
    @Resource
    private InsuranceService insuranceService;
    @Resource
    private BalanceService balanceService;
    @Resource
    private TicketDatepriceService ticketDatepriceService;
    @Resource
    private OrderTouristService orderTouristService;
    @Resource
    private CruiseShipRoomService cruiseShipRoomService;
    @Resource
    private CruiseShipDateService cruiseShipDateService;
    @Resource
    private PropertiesManager propertiesManager;
    @Resource
    private FerryOrderService ferryOrderService;
    @Resource
    private ProductimageService productimageService;
    @Resource
    private YhyOrderService yhyOrderService;
    @Resource
    private FerryMobileService ferryMobileService;
    @Resource
    private MemberService memberService;
    @Resource
    private OrderValidateCodeService orderValidateCodeService;
    @Resource
    private ProValidCodeService proValidCodeService;
    @Resource
    private YhyMsgService yhyMsgService;

    public Order saveOrder(OrderUpdateRequest orderUpdateRequest, Member user, FerryOrder ferryOrder) throws Exception {
        Order order = null;
        if (orderUpdateRequest.getId() != null) {
            order = orderService.get(orderUpdateRequest.getId());
        }
        if (order == null) {
            order = new Order();
            order.setOrderNo(GenOrderNo.generate(propertiesManager.getString("MACHINE_NO", "")));
            order.setCreateTime(new Date());
            order.setOrderWay(OrderWay.weixin);
        }
        order.setUser(user);
        order.setRecName(orderUpdateRequest.getContact().getName());
        order.setMobile(orderUpdateRequest.getContact().getTelephone());
        order.setModifyTime(new Date());
        order.setHasComment(false);
        order.setPlayDate(DateUtils.parseShortTime(orderUpdateRequest.getPlayDate()));
        order.setName(orderUpdateRequest.getName());
        order.setStatus(OrderStatus.PROCESSING);
        order.setOrderType(OrderType.valueOf(orderUpdateRequest.getOrderType()));
        if (order.getOrderType() == null) {
            order.setDeleteFlag(true);
            return order;
        }
        if (OrderType.plan.equals(order.getOrderType())) {
            order.setDay(orderUpdateRequest.getDay());
        }
        orderDao.save(order);
        if (orderUpdateRequest.getTourists() != null && !orderUpdateRequest.getTourists().isEmpty()) {
            List<Tourist> touristList = touristService.listByIds(orderUpdateRequest.getTourists());
            orderUpdateRequest.completeDetails(touristList);
            if (orderUpdateRequest.getChildTourists() != null && orderUpdateRequest.getChildTourists().size() > 0) {
                List<Tourist> childTouristList = touristService.listByIds(orderUpdateRequest.getChildTourists());
                orderUpdateRequest.completeChildDetail(childTouristList);
            }
        }
        List<OrderDetail> orderDetailList = orderDetailService.createLxbOrderDetails(orderUpdateRequest.getDetails(), order, true);
        order.setOrderDetails(orderDetailList);
        for (OrderDetail orderDetail : orderDetailList) {
            if (orderDetail.getStatus().equals(OrderDetailStatus.UNCONFIRMED) && order.getStatus().equals(OrderStatus.PROCESSING)) {
                order.setStatus(OrderStatus.UNCONFIRMED);
                break;
            }
        }
        if (OrderType.hotel.equals(order.getOrderType()) && ProductSource.ELONG.equals(orderDetailList.get(0).getProduct().getSource())) {
            order.setStatus(OrderStatus.PAYED);
        }
        if (order.getStatus().equals(OrderStatus.PROCESSING)) {
            orderService.orderWaitTime(order);
        }
        orderInsurance(order, orderUpdateRequest);
        orderDao.save(order);
        if (ferryOrder != null) {
            order.setPrice(order.getPrice() + ferryOrder.getAmount());
            orderService.update(order);
            ferryOrder.setOrderId(order.getId());
            ferryOrderService.updateOrder(ferryOrder);
        }
        if (OrderType.plan.equals(order.getOrderType()) && order.getPrice() == 0f) {
            order.setStatus(OrderStatus.INVALID);
            order.setDeleteFlag(true);
        }
        orderService.update(order);
//        user.setTelephone(order.getMobile());
//        user.setUserName(order.getRecName());
//        user.setMobile(order.getMobile());
//        memberService.update(user);

//        orderCoupon(order, orderUpdateRequest.getCouponId());
        if (!order.getDeleteFlag()) {
            orderService.updateOrderDetailInventory(order);
        }
        for (OrderDetail orderDetail : orderDetailList) {
            // 短信发送
            if (OrderType.yacht.equals(order.getOrderType()) || OrderType.sailboat.equals(order.getOrderType()) || OrderType.huanguyou.equals(order.getOrderType())) {
                Map<String, Object> msgData = new HashMap<String, Object>();
                // 待确认的
                if (OrderStatus.UNCONFIRMED.equals(order.getStatus())) {
                    // @WEB_SMS
                    // 用户提示待确认短信
                    yhyMsgService.doSendSMS(msgData, order.getMobile(), MsgTemplateKey.WEB_TICKET_WAIT_CFM_TLE);
                    // @WEB_SMS
                    // 商户提示待确认短信
                    yhyMsgService.doSendSMS(msgData, orderDetail.getProduct().getCompanyUnit().getSysUnitDetail().getMobile(), MsgTemplateKey.MERCHANT_TICKET_WAIT_CFM_TLE);
                } else if (OrderStatus.WAIT.equals(order.getStatus())) {
                    // 等待支付
                    Integer timeout = 0;
                    switch (order.getOrderType()) {
                        case yacht:
                            timeout = propertiesManager.getInteger("ORDER_TICKET_PAY_WAIT_TIME");
                            break;
                        case sailboat:
                            timeout = propertiesManager.getInteger("ORDER_SAILBOAT_PAY_WAIT_TIME");
                            break;
                        case huanguyou:
                            timeout = propertiesManager.getInteger("ORDER_TICKET_PAY_WAIT_TIME");
                            break;
                        default:
                            timeout = 30;
                            break;
                    }
                    // 游艇/帆船/鹭岛游 短信发送
                    msgData.put("timeout", timeout);
                    msgData.put("orderNo", order.getOrderNo());
                    // @WEB_SMS
                    yhyMsgService.doSendSMS(msgData, order.getMobile(), MsgTemplateKey.WEB_TICKET_WAIT_PAY_TLE);
                }
            }
        }
        return order;
    }

    public void orderInsurance(Order order, OrderUpdateRequest orderUpdateRequest) {
        Float price = 0F;
        for (OrderDetail orderDetail : order.getOrderDetails()) {
            price += orderService.calPrice(orderDetail);
        }
        order.setPrice(price);
    }

    public void completeInsuranceDetail(Order order, List<Object> ids) {
        Integer num = 0;
        OrderDetail orderDetail = order.getOrderDetails().get(0);
        for (OrderDetail detail : order.getOrderDetails()) {
            num += detail.getNum();
        }
        List<Map<String, Object>> detailList = Lists.newArrayList();
        for (Object id : ids) {
            Insurance insurance = insuranceService.get(Long.valueOf(id.toString()));
            if (insurance != null) {
                Map<String, Object> detail = insuranceToMap(insurance);
                detail.put("startTime", com.zuipin.util.DateUtils.formatDate(orderDetail.getPlayDate()));
                detail.put("num", num);
                detailList.add(detail);
            }
        }
        order.getOrderDetails().addAll(orderDetailService.createLxbOrderDetails(detailList, order, false));
    }

    public Map<String, Object> insuranceToMap(Insurance insurance) {
        Map<String, Object> detail = Maps.newHashMap();
        detail.put("priceId", insurance.getId());
        detail.put("price", insurance.getPrice());
        detail.put("type", "insurance");
        detail.put("seatType", insurance.getName());
        return detail;
    }

    public OrderInvoice orderInvoice(com.data.data.hmly.action.yihaiyou.request.OrderInvoice orderInvoice, Order order) {
        if (orderInvoice != null) {
            OrderInvoice invoice = new OrderInvoice();
            invoice.setName(orderInvoice.getName());
            invoice.setAddress(orderInvoice.getAddress());
            invoice.setTelephone(orderInvoice.getTelephone());
            invoice.setTitle(orderInvoice.getTitle());
            orderInvoiceService.save(invoice);
            return invoice;
        } else {
            return null;
        }
    }

    public void orderCreditCard(Order order, List<OrderDetail> orderDetailList) {
        OrderDetail orderDetail = orderDetailList.get(0);
        if (orderDetail.getCreditCard() != null && orderDetail.getCreditCard().getId() != null) {
            order.setStatus(OrderStatus.PAYED);
        } else {
            orderDetail.setStatus(OrderDetailStatus.INVALID);
            orderDetailService.update(orderDetail);
            order.setStatus(OrderStatus.INVALID);
        }
    }

    private void orderCoupon(Order order, Long userCouponId) {
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
    }

    public OrderResponse orderToResponse(Order order) {
        Map<String, List<OrderDetail>> scenicMap = Maps.newLinkedHashMap();
        Map<Long, List<OrderDetail>> hotelMap = Maps.newLinkedHashMap();
        List<OrderDetail> orderDetailList = Lists.newArrayList();
        if (order.getOrderType().equals(OrderType.line)) {
            for (OrderDetail orderDetail : order.getOrderDetails()) {
                if (orderDetail.getProductType().equals(ProductType.line)) {
                    orderDetailList.add(orderDetail);
                }
            }
        } else {
            orderDetailList = order.getOrderDetails();
        }
        for (OrderDetail orderDetail : orderDetailList) {
            switch (orderDetail.getProductType()) {
                case hotel:
                    List<OrderDetail> hotelDetails = hotelMap.get(orderDetail.getProduct().getId());
                    if (hotelDetails == null) {
                        hotelDetails = Lists.newArrayList();
                    }
                    hotelDetails.add(orderDetail);
                    hotelMap.put(orderDetail.getProduct().getId(), hotelDetails);
//                    HotelPrice hotelPrice = hotelPriceService.get(orderDetail.getCostId());
//                    PlanOrderHotelResponse hotel = new PlanOrderHotelResponse(hotelPrice);
//                    hotel.setStartDate(DateUtils.formatShortDate(orderDetail.getPlayDate()));
//                    hotel.setEndDate(DateUtils.formatDate(orderDetail.getLeaveDate()));
//                    hotel.setTourists(Lists.transform(orderDetail.getOrderTouristList(), new Function<OrderTourist, SimpleTourist>() {
//                        @Override
//                        public SimpleTourist apply(OrderTourist input) {
//                            return new SimpleTourist(input);
//                        }
//                    }));
//                    hotels.add(hotel);
                    break;
                case scenic:
                    List<OrderDetail> details = scenicMap.get(DateUtils.formatShortDate(orderDetail.getPlayDate()));
                    if (details == null) {
                        details = Lists.newArrayList();
                    }
                    details.add(orderDetail);
                    scenicMap.put(DateUtils.formatShortDate(orderDetail.getPlayDate()), details);
                    break;
                default:
                    break;
            }
        }
        OrderResponse response = new OrderResponse(order);
        response.withHotels(completePlanHotels(hotelMap)).withScenics(completePlanScenics(scenicMap));

        List<FerryOrder> ferryOrderList = ferryOrderService.getByOrderId(order.getId());
        if (!ferryOrderList.isEmpty()) {
            FerryOrder ferryOrder = ferryOrderList.get(0);
            FerryOrderResponse ferryOrderResponse = new FerryOrderResponse(ferryOrder);
            ferryOrderResponse.setTouristList(Lists.transform(ferryOrder.getFerryOrderItemList(), new Function<FerryOrderItem, SimpleTourist>() {
                @Override
                public SimpleTourist apply(FerryOrderItem input) {
                    SimpleTourist tourist = new SimpleTourist();
                    tourist.setName(input.getName());
                    return tourist;
                }
            }));
            response.setFerry(ferryOrderResponse);
        }

        completeTouristNum(order, response);

        OrderContact contact = new OrderContact();
        contact.setName(order.getRecName());
        contact.setTelephone(order.getMobile());
        response.setContact(contact);
        if (order.getInvoice() != null) {
            response.setInvoice(new com.data.data.hmly.action.yihaiyou.request.OrderInvoice(order.getInvoice()));
        }
        if (order.getUserCoupon() != null) {
            response.setCouponValue(order.getUserCoupon().getCoupon().getFaceValue());
        }
        return response;
    }

    public OrderDetailSimpleResponse orderDetailToResponse(OrderDetail orderDetail) {
        OrderDetailSimpleResponse response = new OrderDetailSimpleResponse();
        response.setId(orderDetail.getId());
        switch (orderDetail.getProductType()) {
            case scenic:
                Ticket ticket = (Ticket) orderDetail.getProduct();
                ScenicInfo scenicInfo = ticket.getScenicInfo();
                response.setProId(scenicInfo.getId());
                response.setPriceId(orderDetail.getCostId());
                response.setCover(cover(scenicInfo.getCover()));
                response.setProType(ProductType.scenic.name());
                response.setName(scenicInfo.getName());
                break;
            case hotel:
                Hotel hotel = (Hotel) orderDetail.getProduct();
                response.setProId(hotel.getId());
                response.setPriceId(orderDetail.getCostId());
                response.setName(hotel.getName());
                response.setProType(ProductType.hotel.name());
                Productimage hotelImage = productimageService.findCover(hotel.getId(), null, ProductType.hotel);
                if (hotelImage != null) {
                    response.setCover(cover(hotelImage.getPath()));
                }
                break;
            case sailboat: case yacht: case huanguyou:
                Ticket sailboat = (Ticket) orderDetail.getProduct();
                response.setProId(sailboat.getId());
                response.setPriceId(orderDetail.getCostId());
                response.setProType(ProductType.sailboat.name());
                response.setName(sailboat.getName());
                Productimage sailImage = productimageService.findCover(sailboat.getId(), null, ProductType.scenic);
                if (sailImage != null) {
                    response.setCover(cover(sailImage.getPath()));
                }
                break;
            case cruiseship:
                CruiseShip cruiseShip = (CruiseShip) orderDetail.getProduct();
                List<CruiseShipDate> cruiseShipDates = cruiseShipDateService.listCruiseShipDates(cruiseShip.getId(), orderDetail.getPlayDate(), orderDetail.getPlayDate());
                if (cruiseShipDates.isEmpty()) {
                    return null;
                }
                CruiseShipDate cruiseShipDate = cruiseShipDates.get(0);
                response.setProId(cruiseShipDate.getId());
                response.setPriceId(orderDetail.getCostId());
                response.setName(cruiseShip.getName());
                response.setProType(ProductType.cruiseship.name());
                response.setCover(cover(cruiseShip.getCoverImage()));
                break;
        }
        return response;
    }

    public List<PlanOrderHotelResponse> completePlanHotels(Map<Long, List<OrderDetail>> map) {
        List<PlanOrderHotelResponse> hotels = Lists.newArrayList();
        for (Long id : map.keySet()) {
            List<OrderDetail> details = map.get(id);
            PlanOrderHotelResponse hotel = null;
            for (OrderDetail detail : details) {
                List<HotelPriceCalendar> hotelPriceCalendars = hotelPriceService.findTypePriceDate(detail.getCostId(), detail.getPlayDate(), detail.getLeaveDate());
                HotelPriceCalendar calendar = hotelPriceCalendars.get(0);
                if (hotel == null) {
                    hotel = new PlanOrderHotelResponse(calendar.getHotelPrice());
                    hotel.setTourists(Lists.transform(detail.getOrderTouristList(), new Function<OrderTourist, SimpleTourist>() {
                        @Override
                        public SimpleTourist apply(OrderTourist input) {
                            return new SimpleTourist(input);
                        }
                    }));
                }
                HotelPriceResponse hotelPriceResponse = new HotelPriceResponse(calendar.getHotelPrice());
                hotelPriceResponse.setPrice(detail.getFinalPrice());
                hotelPriceResponse.setStartDate(DateUtils.formatShortDate(detail.getPlayDate()));
                hotelPriceResponse.setEndDate(DateUtils.formatShortDate(detail.getLeaveDate()));
                hotelPriceResponse.setDay(detail.getDay());
                hotelPriceResponse.setNum(detail.getNum());

                List<HotelPriceResponse> hotelPriceResponseList = hotel.getHotelPrices();
                hotelPriceResponseList.add(hotelPriceResponse);
                hotel.setHotelPrices(hotelPriceResponseList);
                hotel.setPrice(hotel.getPrice() + hotelPriceResponse.getPrice());

                ProValidCode code = new ProValidCode();
                code.setOrderDetailId(detail.getId());
                List<ProValidCode> codes = proValidCodeService.getValidateInfoList(code, null);
                List<ProValidCodeResponse> codeResponses = Lists.transform(codes, new Function<ProValidCode, ProValidCodeResponse>() {
                    @Override
                    public ProValidCodeResponse apply(ProValidCode input) {
                        return new ProValidCodeResponse(input);
                    }
                });
                if (!codeResponses.isEmpty()) {
                    List<ProValidCodeResponse> hotelCodeResponses = hotel.getCodes();
                    hotelCodeResponses.addAll(codeResponses);
                    hotel.setCodes(codeResponses);
                }

                try {
                    if (StringUtils.isBlank(hotel.getStartDate()) || detail.getPlayDate().before(DateUtils.parseShortTime(hotel.getStartDate()))) {
                        hotel.setStartDate(hotelPriceResponse.getStartDate());
                    }
                    if (StringUtils.isBlank(hotel.getEndDate()) || detail.getLeaveDate().after(DateUtils.parseShortTime(hotel.getEndDate()))) {
                        hotel.setEndDate(hotelPriceResponse.getEndDate());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            hotels.add(hotel);
        }
        return hotels;
    }

    public List<TicketScenicResponse> completePlanScenics(Map<String, List<OrderDetail>> map) {
        Map<Long, TicketScenicResponse> scenicMap = Maps.newHashMap();
        for (String s : map.keySet()) {
            List<OrderDetail> details = map.get(s);
            for (OrderDetail detail : details) {
                TicketDateprice ticketDateprice = ticketDatepriceService.getTicketDatePrice(detail.getCostId(), detail.getPlayDate());
                TicketResponse ticket = new TicketResponse(ticketDateprice);
                ticket.setNum(detail.getNum());
                ticket.setPrice(detail.getFinalPrice());
                ScenicInfo scenicInfo = ticketDateprice.getTicketPriceId().getTicket().getScenicInfo();
                TicketScenicResponse scenic = scenicMap.get(scenicInfo.getId());
                if (scenic == null) {
                    scenic = new TicketScenicResponse();
                    scenic.setScenicName(scenicInfo.getName());
                    scenic.setPlayDate(DateUtils.formatShortDate(detail.getPlayDate()));
                    scenic.setTourists(Lists.transform(detail.getOrderTouristList(), new Function<OrderTourist, SimpleTourist>() {
                        @Override
                        public SimpleTourist apply(OrderTourist input) {
                            return new SimpleTourist(input);
                        }
                    }));
                }
                List<TicketResponse> tickets = scenic.getTickets();
                tickets.add(ticket);
                scenic.setTickets(tickets);
                scenic.setPrice(scenic.getPrice() + ticket.getPrice());

                ProValidCode code = new ProValidCode();
                code.setOrderDetailId(detail.getId());
                List<ProValidCode> codes = proValidCodeService.getValidateInfoList(code, null);
                List<ProValidCodeResponse> codeResponses = Lists.transform(codes, new Function<ProValidCode, ProValidCodeResponse>() {
                    @Override
                    public ProValidCodeResponse apply(ProValidCode input) {
                        return new ProValidCodeResponse(input);
                    }
                });
                if (!codeResponses.isEmpty()) {
                    List<ProValidCodeResponse> scenicCodeResponses = scenic.getCodes();
                    scenicCodeResponses.addAll(codeResponses);
                    scenic.setCodes(codeResponses);
                }

                scenicMap.put(scenicInfo.getId(), scenic);
            }
        }
        return Lists.newArrayList(scenicMap.values());
    }

    public void completeTouristNum(Order order, OrderResponse response) {
        List<OrderTourist> orderTourists = orderTouristService.getByOrderId(order.getId());
        Map<String, OrderTourist> adultTourists = Maps.newHashMap();
        Map<String, OrderTourist> childTourists = Maps.newHashMap();
        for (OrderTourist orderTourist : orderTourists) {
            if (OrderTouristPeopleType.ADULT.equals(orderTourist.getPeopleType()) && adultTourists.get(orderTourist.getIdNumber()) == null) {
                adultTourists.put(orderTourist.getIdNumber(), orderTourist);
            }
            if (OrderTouristPeopleType.CHILD.equals(orderTourist.getPeopleType()) && childTourists.get(orderTourist.getIdNumber()) == null) {
                childTourists.put(orderTourist.getIdNumber(), orderTourist);
            }
        }
        response.setAdultNum(adultTourists.size());
        response.setChildNum(childTourists.size());
    }

    public void completeTourist(Order order, OrderResponse response) {
        List<SimpleTourist> tourists = Lists.transform(order.getOrderDetails().get(0).getOrderTouristList(), new Function<OrderTourist, SimpleTourist>() {
            @Override
            public SimpleTourist apply(OrderTourist input) {
                return new SimpleTourist(input);
            }
        });
        response.setTourists(tourists);
        if (OrderType.line.equals(order.getOrderType()) && order.getOrderDetails().size() > 1) {
            List<SimpleTourist> childTourists = Lists.transform(order.getOrderDetails().get(1).getOrderTouristList(), new Function<OrderTourist, SimpleTourist>() {
                @Override
                public SimpleTourist apply(OrderTourist input) {
                    return new SimpleTourist(input);
                }
            });
            response.setChildTourists(childTourists);
        }
    }

    public OrderSimpleResponse orderToSimpleResponse(Order order) {
        OrderSimpleResponse response = new OrderSimpleResponse(order);
        OrderDetail orderDetail = order.getOrderDetails().get(0);
        if (OrderType.ticket.equals(order.getOrderType()) || OrderType.sailboat.equals(order.getOrderType()) || OrderType.yacht.equals(order.getOrderType()) || OrderType.huanguyou.equals(order.getOrderType())) {
            Ticket ticket = (Ticket) orderDetail.getProduct();
            response.setCover(cover(ticket.getTicketImgUrl()));
            response.setNum(orderDetail.getNum());
            response.setProId(ticket.getScenicInfo().getId());
            response.setProType(ProductType.scenic);
        } else if (OrderType.hotel.equals(order.getOrderType())) {
            response = new HotelOrderResponse(response);
            Hotel hotel = (Hotel) orderDetail.getProduct();
            response.setCover(cover(hotel.getCover()));
            response.setNum(orderDetail.getNum());
            ((HotelOrderResponse) response).setRoomName(orderDetail.getSeatType());
            ((HotelOrderResponse) response).setEndDate(DateUtils.formatShortDate(orderDetail.getLeaveDate()));
            response.setDay(orderDetail.getDays());
            response.setProId(hotel.getId());
            response.setProType(ProductType.hotel);
        } else if (OrderType.plan.equals(order.getOrderType())) {
            Integer num = 0;
            for (OrderDetail detail : order.getOrderDetails()) {
                if (detail.getOrderTouristList().size() > num) {
                    num = detail.getOrderTouristList().size();
                }
                if (ProductType.scenic.equals(detail.getProductType()) && StringUtils.isBlank(response.getCover())) {
                    Ticket ticket = (Ticket) detail.getProduct();
                    response.setCover(cover(ticket.getTicketImgUrl()));
                }
            }
            response.setNum(num);
        } else if (OrderType.cruiseship.equals(order.getOrderType())) {
            Integer num = 0;
            for (OrderDetail detail : order.getOrderDetails()) {
                num += detail.getNum();
            }
            response.setNum(num);
            CruiseShip cruiseShip = (CruiseShip) orderDetail.getProduct();
            response.setCover(cover(cruiseShip.getCoverImage()));
            response.setProId(cruiseShip.getId());
            response.setProType(ProductType.cruiseship);
        } else {
            response.setCover("");
        }
        return response;
    }

    private String cover(String cover) {
        if (StringUtils.isBlank(cover)) {
            return "";
        }
        if (cover.startsWith("http")) {
            return cover;
        } else {
            return QiniuUtil.URL + cover;
        }
    }

    public Integer saveTourist(SimpleTourist touristUpdateRequest, Member user) {
        Tourist tourist = null;
        if (touristUpdateRequest.getTouristId() != null) {
            tourist = touristService.get(touristUpdateRequest.getTouristId());
        }
        if (tourist == null) {
            tourist = new Tourist();
            tourist.setCreateTime(new Date());
            tourist.setStatus(TouristStatus.normal);
            tourist.setUser(user);
        }
        if (StringUtils.isNotBlank(touristUpdateRequest.getName())) {
            tourist.setName(touristUpdateRequest.getName());
        }
        if (touristUpdateRequest.getIdType() != null && StringUtils.isNotBlank(touristUpdateRequest.getIdNumber()) && !touristUpdateRequest.getIdNumber().equals(tourist.getIdNumber())) {
            Integer count = touristService.countMyTouristByidNumber(user, touristUpdateRequest.getIdNumber());
            if (count > 0) {
                return -1;
            }
            tourist.setIdType(touristUpdateRequest.getIdType());
            tourist.setIdNumber(touristUpdateRequest.getIdNumber());
        }
        if (StringUtils.isNotBlank(touristUpdateRequest.getTelephone())) {
            tourist.setTel(touristUpdateRequest.getTelephone());
        }
        if (touristUpdateRequest.getGender() != null) {
            tourist.setGender(touristUpdateRequest.getGender());
        } else {
            tourist.setGender(Gender.male);
        }
        if (touristUpdateRequest.getPeopleType() != null) {
            tourist.setPeopleType(touristUpdateRequest.getPeopleType());
        } else {
            tourist.setPeopleType(TouristPeopleType.ADULT);
        }
        if (StringUtils.isNotBlank(touristUpdateRequest.getEmail())) {
            tourist.setEmail(touristUpdateRequest.getEmail());
        }
        try {
            if (StringUtils.isNotBlank(touristUpdateRequest.getBirthday())) {
                tourist.setBirthday(DateUtils.parseShortTime(touristUpdateRequest.getBirthday()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (StringUtils.isNotBlank(touristUpdateRequest.getAddress())) {
            tourist.setAddress(touristUpdateRequest.getAddress());
        }
        touristService.saveTourist(tourist);
        return 0;
    }

    public TicketOrderResponse ticketOrderDetail(Order order) {
        TicketOrderResponse response = new TicketOrderResponse(order);
        OrderContact contact = new OrderContact();
        contact.setName(order.getRecName());
        contact.setTelephone(order.getMobile());
        response.setContact(contact);
        OrderDetail orderDetail = order.getOrderDetails().get(0);
        response.setNum(orderDetail.getNum());
        List<SimpleTourist> tourists = Lists.transform(orderDetail.getOrderTouristList(), new Function<OrderTourist, SimpleTourist>() {
            @Override
            public SimpleTourist apply(OrderTourist input) {
                return new SimpleTourist(input);
            }
        });
        response.setTourists(tourists);

        TicketPrice ticketPrice = ticketPriceService.getPrice(orderDetail.getCostId());
        response.setScenicId(ticketPrice.getTicket().getScenicInfo().getId());
        response.completeGetTicket(ticketPrice.getGetTicket());

        ProValidCode code = new ProValidCode();
        code.setOrderId(order.getId());
        List<ProValidCode> codes = proValidCodeService.getValidateInfoList(code, null);
        List<ProValidCodeResponse> codeResponses = Lists.transform(codes, new Function<ProValidCode, ProValidCodeResponse>() {
            @Override
            public ProValidCodeResponse apply(ProValidCode input) {
                return new ProValidCodeResponse(input);
            }
        });
        response.setCodes(codeResponses);
        return response;
    }

    public SailOrderResponse sailOrderDetail(Order order) {
        SailOrderResponse response = new SailOrderResponse(order);
        OrderDetail orderDetail = order.getOrderDetails().get(0);
        response.setNum(orderDetail.getNum());

        TicketPrice ticketPrice = ticketPriceService.getPrice(orderDetail.getCostId());
        Map<String, Date> dateMap = ticketDatepriceService.getFirstAndEndDate(ticketPrice);
        TicketPriceResponse priceResponse = new TicketPriceResponse(ticketPrice);
        Date start = dateMap.get("start");
        Date end = dateMap.get("end");
        if (start != null && end != null) {
            priceResponse.setStartDate(DateUtils.formatShortDate(start));
            priceResponse.setEndDate(DateUtils.formatShortDate(end));
        }
        response.setTicketPrice(priceResponse);
        response.setCompany(ticketPrice.getTicket().getCompanyUnit());

        ProValidCode code = new ProValidCode();
        code.setOrderId(order.getId());
        List<ProValidCode> codes = proValidCodeService.getValidateInfoList(code, null);
        List<ProValidCodeResponse> codeResponses = Lists.transform(codes, new Function<ProValidCode, ProValidCodeResponse>() {
            @Override
            public ProValidCodeResponse apply(ProValidCode input) {
                return new ProValidCodeResponse(input);
            }
        });
        response.setCodes(codeResponses);
        List<SimpleTourist> tourists = Lists.transform(orderDetail.getOrderTouristList(), new Function<OrderTourist, SimpleTourist>() {
            @Override
            public SimpleTourist apply(OrderTourist input) {
                return new SimpleTourist(input);
            }
        });
        response.setTourists(tourists);
        return response;
    }

    public HotelOrderResponse hotelOrderDetail(Order order) {
        HotelOrderResponse response = new HotelOrderResponse(order);
        OrderContact contact = new OrderContact();
        contact.setName(order.getRecName());
        contact.setTelephone(order.getMobile());
        response.setContact(contact);

        OrderDetail orderDetail = order.getOrderDetails().get(0);
        response.setNum(orderDetail.getNum());
        response.setEndDate(DateUtils.formatShortDate(orderDetail.getLeaveDate()));
        response.setHotelId(orderDetail.getProduct().getId());
//        if (orderDetail.getStatus() == null) {
//            orderDetail.setStatus(OrderDetailStatus.BOOKING);
//        }
        response.setHotelTelephone(orderDetail.getProduct().getCompanyUnit().getSysUnitDetail().getTelphone());
        response.setStatus(order.getStatus().getDescription());
        response.setOrderDetailStatus(orderDetail.getStatus());
        List<SimpleTourist> tourists = Lists.transform(orderDetail.getOrderTouristList(), new Function<OrderTourist, SimpleTourist>() {
            @Override
            public SimpleTourist apply(OrderTourist input) {
                return new SimpleTourist(input);
            }
        });
        response.setTourists(tourists);

//        List<HotelPriceCalendar> calendarList = hotelPriceService.findTypePriceDate(orderDetail.getCostId(), orderDetail.getPlayDate(), DateUtils.add(orderDetail.getLeaveDate(), Calendar.DAY_OF_MONTH, -1));
//        response.setCalendarList(calendarList);

        HotelPrice hotelPrice = hotelPriceService.get(orderDetail.getCostId());
        response.setAddress(hotelPrice.getHotel().getExtend().getAddress());
        response.setRoomName(hotelPrice.getRoomName());
        response.setPriceStatus(hotelPrice.getStatus());
        response.setHasBreakfast(hotelPrice.getBreakfast());
        response.setDay(orderDetail.getDays());
        response.setMsg(orderDetail.getMsg());

        ProValidCode code = new ProValidCode();
        code.setOrderId(order.getId());
        List<ProValidCode> codes = proValidCodeService.getValidateInfoList(code, null);
        List<ProValidCodeResponse> codeResponses = Lists.transform(codes, new Function<ProValidCode, ProValidCodeResponse>() {
            @Override
            public ProValidCodeResponse apply(ProValidCode input) {
                return new ProValidCodeResponse(input);
            }
        });
        response.setCodes(codeResponses);

        return response;
    }

    public Order saveBalanceOrder(OrderUpdateRequest orderUpdateRequest, Member user) {
        Order order = null;
        if (orderUpdateRequest.getId() != null) {
            order = orderService.get(orderUpdateRequest.getId());
        }
        if (order == null) {
            order = new Order();
            order.setOrderNo(GenOrderNo.generate(propertiesManager.getString("MACHINE_NO", "")));
            order.setCreateTime(new Date());
        }
        order.setUser(user);
        AccountType accountType = null;
        if (OrderType.recharge.name().equals(orderUpdateRequest.getOrderType())) {
            order.setOrderType(OrderType.recharge);
            order.setName("充值");
            accountType = AccountType.recharge;
        } else if (OrderType.withdraw.name().equals(orderUpdateRequest.getOrderType())) {
            order.setOrderType(OrderType.withdraw);
            order.setName("提现");
            accountType = AccountType.withdraw;
        }
        order.setStatus(OrderStatus.WAIT);
        Map<String, Object> detail = orderUpdateRequest.getDetails().get(0);
        order.setPrice(Float.valueOf(detail.get("price").toString()));
        Integer minute = Integer.valueOf(propertiesManager.getString("ORDER_BALANCE_PAY_WAIT_TIME"));
        order.setWaitTime(DateUtils.add(new Date(), Calendar.MINUTE, minute));
        order.setOrderWay(OrderWay.weixin);
        orderDao.save(order);
        balanceService.savePayResult(order, accountType);
        return order;
    }

    public CruiseShipOrderResponse cruiseShipOrderDetail(Order order) {
        CruiseShipOrderResponse response = new CruiseShipOrderResponse(order);
        OrderContact contact = new OrderContact();
        contact.setName(order.getRecName());
        contact.setTelephone(order.getMobile());
        response.setContact(contact);

        OrderDetail orderDetail = order.getOrderDetails().get(0);
        CruiseShip cruiseShip = (CruiseShip) orderDetail.getProduct();
        response.setStartCity(cruiseShip.getStartCity());
        Date date = DateUtils.add(orderDetail.getPlayDate(), Calendar.DAY_OF_MONTH, cruiseShip.getPlayDay());
        response.setEndDate(DateUtils.formatShortDate(date));
        Integer num = 0;
        for (OrderDetail detail : order.getOrderDetails()) {
            num += detail.getNum();
        }
        response.setNum(num);
        return response;
    }

    public void saveBalancePay(Order order, Member user) {
        payedOrderWaitTime(order);
        orderService.update(order);
        orderService.updateOrderBill(order);
        user.setBalance(user.getBalance() - order.getPrice());
        memberService.update(user);
        balanceService.savePayResult(order, AccountType.consume);
        OrderResult result = yhyOrderService.doTakeOrder(order.getId());
        for (OrderDetail orderDetail : order.getOrderDetails()) {
            OrderDetail detail = orderDetailService.get(orderDetail.getId());
            if (detail.getStatus().equals(OrderDetailStatus.SUCCESS) && (detail.getProduct().getSource() == null || ProductSource.LXB.equals(detail.getProduct().getSource()))) {
                orderValidateCodeService.doCreateValidateCode(order, detail);
            }
        }
        List<FerryOrder> ferryOrders = ferryOrderService.getByOrderId(order.getId());
        if (!ferryOrders.isEmpty()) {
            FerryOrder ferryOrder = ferryOrders.get(0);
            Map<String, Object> payResult = FerryUtil.payNotify(ferryOrder.getFerryNumber(), true);
            if (!(Boolean) payResult.get("success")) {
                if (result.getSuccess()) {
                    order.setStatus(OrderStatus.PARTIAL_FAILED);
                }
                ferryOrder.setStatus(OrderStatus.FAILED);
            } else {
                ferryOrder.setStatus(OrderStatus.SUCCESS);
            }
            ferryOrderService.updateOrder(ferryOrder);
            Map<String, Object> data = Maps.newHashMap();
            data.put("lineName", ferryOrder.getFlightLineName());
            // @WEB_MSG
            yhyMsgService.doSendSMS(data, ferryOrder.getUser().getFerryMember().getMobile(), MsgTemplateKey.WEB_FERRY_BOOKING_SUCCESS_TLE);
        }
        orderService.update(order);
    }

    public void payedOrderWaitTime(Order order) {
        order.setStatus(OrderStatus.PAYED);
        order.setPayType(OrderPayType.ONLINE);
        Integer minute = null;
        switch (order.getOrderType()) {
            case hotel:
                minute = Integer.valueOf(propertiesManager.getString("ORDER_PAYED_HOTEL_PAY_WAIT_TIME"));
                break;
            case plan:
                minute = Integer.valueOf(propertiesManager.getString("ORDER_PAYED_PLAN_PAY_WAIT_TIME"));
                break;
        }
        if (minute != null) {
            order.setWaitTime(DateUtils.add(new Date(), Calendar.MINUTE, minute));
        }
    }
}
