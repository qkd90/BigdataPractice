package com.data.data.hmly.service;

import com.data.data.hmly.action.mobile.request.OrderContact;
import com.data.data.hmly.action.mobile.request.OrderUpdateRequest;
import com.data.data.hmly.action.mobile.request.SimpleTourist;
import com.data.data.hmly.action.mobile.response.*;
import com.data.data.hmly.enums.Gender;
import com.data.data.hmly.service.balance.BalanceService;
import com.data.data.hmly.service.balance.entity.enums.AccountType;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.discount.UserCouponService;
import com.data.data.hmly.service.discount.entity.DiscountContext;
import com.data.data.hmly.service.discount.strategy.CouponStrategy;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.hotel.HotelPriceService;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.hotel.entity.HotelPrice;
import com.data.data.hmly.service.hotel.entity.HotelPriceCalendar;
import com.data.data.hmly.service.line.LineService;
import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.line.entity.enums.CombineType;
import com.data.data.hmly.service.line.entity.enums.LineConfirmAndPayType;
import com.data.data.hmly.service.lvxbang.PlanOrderService;
import com.data.data.hmly.service.lxbcommon.entity.UserCoupon;
import com.data.data.hmly.service.lxbcommon.entity.enums.UserCouponStatus;
import com.data.data.hmly.service.order.OrderDetailService;
import com.data.data.hmly.service.order.OrderInvoiceService;
import com.data.data.hmly.service.order.OrderService;
import com.data.data.hmly.service.order.dao.OrderDao;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.OrderInvoice;
import com.data.data.hmly.service.order.entity.OrderTourist;
import com.data.data.hmly.service.order.entity.enums.OrderCostType;
import com.data.data.hmly.service.order.entity.enums.OrderDetailStatus;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.order.entity.enums.OrderType;
import com.data.data.hmly.service.sales.InsuranceService;
import com.data.data.hmly.service.sales.entity.Insurance;
import com.data.data.hmly.service.ticket.TicketPriceService;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.data.data.hmly.service.ticket.entity.TicketPrice;
import com.data.data.hmly.service.traffic.TrafficPriceService;
import com.data.data.hmly.service.traffic.entity.TrafficPrice;
import com.data.data.hmly.service.user.TouristService;
import com.data.data.hmly.service.user.entity.Tourist;
import com.data.data.hmly.service.user.entity.enums.TouristPeopleType;
import com.data.data.hmly.service.user.entity.enums.TouristStatus;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zuipin.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by huangpeijie on 2016-04-26,0026.
 */
@Service
public class OrderMobileService {
    @Resource
    private TouristService touristService;
    @Resource
    private MemberService memberService;
    @Resource
    private OrderService orderService;
    @Resource
    private OrderDao orderDao;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private TrafficPriceService trafficPriceService;
    @Resource
    private HotelPriceService hotelPriceService;
    @Resource
    private PlanOrderService planOrderService;
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

    public Order saveOrder(OrderUpdateRequest orderUpdateRequest, Member user) throws Exception {
        Order order = null;
        if (orderUpdateRequest.getId() != null) {
            order = orderService.get(orderUpdateRequest.getId());
        }
        if (order == null) {
            order = new Order();
            order.setOrderNo(orderService.makeOrderNo());
            order.setCreateTime(new Date());
        }
        order.setUser(user);
        order.setRecName(orderUpdateRequest.getContact().getName());
        order.setMobile(orderUpdateRequest.getContact().getTelephone());
        order.setModifyTime(new Date());
        order.setHasComment(false);
        order.setPlayDate(DateUtils.parseShortTime(orderUpdateRequest.getPlayDate()));
        Boolean status = false;
        if ("line".equals(orderUpdateRequest.getOrderType())) {
            order.setOrderType(OrderType.line);
        } else if ("plan".equals(orderUpdateRequest.getOrderType())) {
            order.setName(orderUpdateRequest.getName());
            order.setOrderType(OrderType.plan);
            order.setDay(orderUpdateRequest.getDay());
            order.setStatus(OrderStatus.WAIT);
        } else if ("ticket".equals(orderUpdateRequest.getOrderType())) {
            order.setName(orderUpdateRequest.getName());
            order.setOrderType(OrderType.ticket);
            order.setStatus(OrderStatus.WAIT);
        } else if ("hotel".equals(orderUpdateRequest.getOrderType())) {
            order.setName(orderUpdateRequest.getName());
            order.setOrderType(OrderType.hotel);
            Map<String, Object> creditCardDetail = (Map<String, Object>) orderUpdateRequest.getDetails().get(0).get("creditCard");
            status = Boolean.valueOf(creditCardDetail.get("status").toString());
            if (!status) {
                order.setStatus(OrderStatus.PAYED);
            }
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
        orderInsurance(order, orderUpdateRequest);
        order.setInvoice(orderInvoice(orderUpdateRequest.getInvoice(), order));
        if (status) {
            orderCreditCard(order, orderDetailList);
        }
        if (order.getOrderType().equals(OrderType.line)) {
            orderService.completeCombineLineDetail(order, (List<Map<String, Object>>) orderUpdateRequest.getDetails().get(0).get("tourist"), orderUpdateRequest.getCreditCard());
        }
        orderDao.save(order);

        user.setTelephone(order.getMobile());
        user.setUserName(order.getRecName());
        user.setMobile(order.getMobile());
        memberService.update(user);

        orderCoupon(order, orderUpdateRequest.getCouponId());
        orderService.updateOrderDetailInventory(order);
        return order;
    }

    public void orderInsurance(Order order, OrderUpdateRequest orderUpdateRequest) {
        Float price = 0F;
//        Integer num = 0;
        if (order.getOrderType().equals(OrderType.line)) {
            completeInsuranceDetail(order, orderUpdateRequest.getInsurances());
        }
        for (OrderDetail orderDetail : order.getOrderDetails()) {
            if (orderDetail.getProductType() != ProductType.hotel) {
                price += orderService.calPrice(orderDetail);
            }
//            num += orderDetail.getNum();
        }
        Float insurancePrice = 0f;
        if (order.getOrderType().equals(OrderType.line)) {
//            List<OrderInsurance> orderInsurances = orderInsuranceService.createLxbOrderInsurances(orderUpdateRequest.getInsurances(), order);
//            order.setOrderInsurances(orderInsurances);
//            for (OrderInsurance orderInsurance : orderInsurances) {
//                insurancePrice += orderInsurance.getInsurance().getPrice() * num;
//            }
            Line line = lineService.loadLine(order.getOrderDetails().get(0).getProduct().getId());
            if (LineConfirmAndPayType.confirm.equals(line.getConfirmAndPay()) && !CombineType.combine.equals(line.getCombineType())) {
                order.setStatus(OrderStatus.UNCONFIRMED);
            } else {
                order.setStatus(OrderStatus.WAIT);
            }
            order.setName("<" + line.getName() + ">" + line.getAppendTitle());
        }
        order.setInsurancePrice(insurancePrice);
        order.setPrice(price + insurancePrice);
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

    public OrderInvoice orderInvoice(com.data.data.hmly.action.mobile.request.OrderInvoice orderInvoice, Order order) {
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
            orderDetail.setStatus(OrderDetailStatus.FAILED);
            orderDetailService.update(orderDetail);
            order.setStatus(OrderStatus.FAILED);
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
        List<PlanOrderTrafficResponse> planes = Lists.newArrayList();
        List<PlanOrderTrafficResponse> trains = Lists.newArrayList();
        List<HotelSimpleResponse> hotels = Lists.newArrayList();
        List<PlanOrderDayResponse> days = Lists.newArrayList();
        Map<String, List<OrderDetail>> map = Maps.newLinkedHashMap();
        LineOrderDetailResponse lineOrderDetail = new LineOrderDetailResponse();
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
                case flight:
                    TrafficPrice plane = trafficPriceService.get(orderDetail.getCostId());
                    planes.add(new PlanOrderTrafficResponse(planOrderService.trafficPriceToResponse(plane)));
                    break;
                case train:
                    TrafficPrice train = trafficPriceService.get(orderDetail.getCostId());
                    trains.add(new PlanOrderTrafficResponse(planOrderService.trafficPriceToResponse(train)));
                    break;
                case hotel:
                    HotelPrice hotelPrice = hotelPriceService.get(orderDetail.getCostId());
                    HotelSimpleResponse hotel = new HotelSimpleResponse(hotelPrice);
                    hotel.setStartDate(DateUtils.formatShortDate(orderDetail.getPlayDate()));
                    hotel.setEndDate(DateUtils.formatDate(orderDetail.getLeaveDate()));
                    hotels.add(hotel);
                    break;
                case scenic:
                    List<OrderDetail> details = map.get(DateUtils.formatShortDate(orderDetail.getPlayDate()));
                    if (details == null) {
                        details = Lists.newArrayList();
                    }
                    details.add(orderDetail);
                    map.put(DateUtils.formatShortDate(orderDetail.getPlayDate()), details);
                    break;
                case line:
                    if ("成人".equals(orderDetail.getSeatType())) {
                        Line line = lineService.loadLine(orderDetail.getProduct().getId());
                        lineOrderDetail.setLineId(line.getId());
                        lineOrderDetail.setDay(line.getPlayDay());
                        TbArea startCity = tbAreaService.getById(line.getStartCityId());
                        lineOrderDetail.setStartCity(startCity.getName());
                        lineOrderDetail.setAdultNum(orderDetail.getNum());
                        lineOrderDetail.setAdultPrice(orderDetail.getUnitPrice());
                        lineOrderDetail.setStartDate(DateUtils.formatShortDate(orderDetail.getPlayDate()));
                    } else if ("儿童".equals(orderDetail.getSeatType())) {
                        lineOrderDetail.setChildNum(orderDetail.getNum());
                        lineOrderDetail.setChildPrice(orderDetail.getUnitPrice());
                    }
                default:
                    break;
            }
        }
        OrderResponse response = new OrderResponse(order);
        response.withPlanes(planes).withTrains(trains).withHotels(hotels).withDays(completePlanDays(days, map));
        response.setLineOrderDetail(lineOrderDetail);

        completeTourist(order, response);

        OrderContact contact = new OrderContact();
        contact.setName(order.getRecName());
        contact.setTelephone(order.getMobile());
        response.setContact(contact);
        if (order.getInvoice() != null) {
            response.setInvoice(new com.data.data.hmly.action.mobile.request.OrderInvoice(order.getInvoice()));
        }
        if (order.getUserCoupon() != null) {
            response.setCouponValue(order.getUserCoupon().getCoupon().getFaceValue());
        }
        return response;
    }

    public List<PlanOrderDayResponse> completePlanDays(List<PlanOrderDayResponse> days, Map<String, List<OrderDetail>> map) {
        for (String s : map.keySet()) {
            List<OrderDetail> details = map.get(s);
            PlanOrderDayResponse day = new PlanOrderDayResponse();
            if (details.get(0).getCity() != null) {
                day.setCityId(details.get(0).getCity().getId());
                day.setCityName(details.get(0).getCity().getName());
            }
            day.setDay(details.get(0).getDay());
            List<TicketResponse> tickets = Lists.newArrayList();
            for (OrderDetail detail : details) {
                TicketPrice ticketPrice = ticketPriceService.getPrice(detail.getCostId());
                TicketResponse ticket = new TicketResponse(ticketPrice);
                ticket.setPlayDate(DateUtils.formatShortDate(detail.getPlayDate()));
                tickets.add(ticket);
            }
            day.setTickets(tickets);
            days.add(day);
        }
        return days;
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
        if (OrderType.ticket.equals(order.getOrderType())) {
            Ticket ticket = (Ticket) order.getOrderDetails().get(0).getProduct();
            response.setCover(cover(ticket.getTicketImgUrl()));
        } else if (OrderType.hotel.equals(order.getOrderType())) {
            Hotel hotel = (Hotel) order.getOrderDetails().get(0).getProduct();
            response.setCover(cover(hotel.getCover()));
        } else if (OrderType.plan.equals(order.getOrderType())) {
            for (OrderDetail detail : order.getOrderDetails()) {
                if (ProductType.scenic.equals(detail.getProductType())) {
                    Ticket ticket = (Ticket) detail.getProduct();
                    response.setCover(cover(ticket.getTicketImgUrl()));
                    break;
                }
            }
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
            return "http://7u2inn.com2.z0.glb.qiniucdn.com/" + cover;
        }
    }

    public Boolean saveTourist(SimpleTourist touristUpdateRequest, Member user) {
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
                return false;
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
        touristService.saveTourist(tourist);
        return true;
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
        if (orderDetail.getStatus() == null) {
            orderDetail.setStatus(OrderDetailStatus.BOOKING);
        }
        response.setStatus(orderDetail.getStatus().getDescription());
        response.setOrderDetailStatus(orderDetail.getStatus());
        List<SimpleTourist> tourists = Lists.transform(order.getOrderDetails().get(0).getOrderTouristList(), new Function<OrderTourist, SimpleTourist>() {
            @Override
            public SimpleTourist apply(OrderTourist input) {
                return new SimpleTourist(input);
            }
        });
        response.setTourists(tourists);

        List<HotelPriceCalendar> calendarList = hotelPriceService.findTypePriceDate(orderDetail.getCostId(), orderDetail.getPlayDate(), DateUtils.add(orderDetail.getLeaveDate(), Calendar.DAY_OF_MONTH, -1));
        response.setCalendarList(calendarList);

        HotelPrice hotelPrice = hotelPriceService.get(orderDetail.getCostId());
        response.setAddress(hotelPrice.getHotel().getExtend().getAddress());
        response.setRoomName(hotelPrice.getRoomName());
        response.setPriceStatus(hotelPrice.getStatus());

        return response;
    }

    public Order saveBalanceOrder(OrderUpdateRequest orderUpdateRequest, Member user) {
        Order order = null;
        if (orderUpdateRequest.getId() != null) {
            order = orderService.get(orderUpdateRequest.getId());
        }
        if (order == null) {
            order = new Order();
            order.setOrderNo(orderService.makeOrderNo());
            order.setCreateTime(new Date());
        }
        order.setUser(user);
        AccountType accountType = null;
        if ("recharge".equals(orderUpdateRequest.getOrderType())) {
            order.setOrderType(OrderType.recharge);
            order.setName("充值");
            accountType = AccountType.recharge;
        } else if ("withdraw".equals(orderUpdateRequest.getOrderType())) {
            order.setOrderType(OrderType.withdraw);
            order.setName("提现");
            accountType = AccountType.withdraw;
        }
        order.setStatus(OrderStatus.WAIT);
        orderDao.save(order);

        Map<String, Object> detail = orderUpdateRequest.getDetails().get(0);
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrder(order);
        orderDetail.setTotalPrice(Float.valueOf(detail.get("price").toString()));
        orderDetail.setFinalPrice(Float.valueOf(detail.get("price").toString()));
        if (OrderType.recharge.equals(order.getOrderType())) {
            orderDetail.setProductType(ProductType.recharge);
            orderDetail.setCostType(OrderCostType.recharge);
        } else if (OrderType.withdraw.equals(order.getOrderType())) {
            orderDetail.setProductType(ProductType.withdraw);
            orderDetail.setCostType(OrderCostType.withdraw);
        }
        orderDetailService.save(orderDetail);

        List<OrderDetail> orderDetailList = Lists.newArrayList(orderDetail);
        order.setOrderDetails(orderDetailList);
        order.setPrice(orderDetail.getFinalPrice());
        orderDao.save(order);
        balanceService.savePayResult(order, accountType);
        return order;
    }
}
