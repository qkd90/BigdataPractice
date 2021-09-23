package com.data.data.hmly.service.order;

import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.elong.ElongHotelService;
import com.data.data.hmly.service.elong.pojo.Contact;
import com.data.data.hmly.service.elong.pojo.CreateOrderCondition;
import com.data.data.hmly.service.elong.pojo.CreateOrderRoom;
import com.data.data.hmly.service.elong.pojo.CreditCard;
import com.data.data.hmly.service.elong.pojo.CreditCardValidateResult;
import com.data.data.hmly.service.elong.pojo.Customer;
import com.data.data.hmly.service.elong.pojo.EnumConfirmationType;
import com.data.data.hmly.service.elong.pojo.EnumCurrencyCode;
import com.data.data.hmly.service.elong.pojo.EnumGuestTypeCode;
import com.data.data.hmly.service.elong.pojo.EnumIdType;
import com.data.data.hmly.service.elong.pojo.EnumPaymentType;
import com.data.data.hmly.service.elong.util.Tool;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.hotel.HotelPriceService;
import com.data.data.hmly.service.hotel.entity.HotelPrice;
import com.data.data.hmly.service.hotel.entity.enums.PriceStatus;
import com.data.data.hmly.service.nctripticket.CtripTicketApiService;
import com.data.data.hmly.service.nctripticket.CtripTicketService;
import com.data.data.hmly.service.nctripticket.entity.CtripOrderContactInfo;
import com.data.data.hmly.service.nctripticket.entity.CtripOrderFormInfo;
import com.data.data.hmly.service.nctripticket.entity.CtripOrderFormResourceInfo;
import com.data.data.hmly.service.nctripticket.entity.CtripOrderPassengerInfo;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.OrderLog;
import com.data.data.hmly.service.order.entity.OrderTourist;
import com.data.data.hmly.service.order.entity.enums.OrderDetailStatus;
import com.data.data.hmly.service.order.entity.enums.OrderTouristIdType;
import com.data.data.hmly.service.order.entity.enums.OrderTouristPeopleType;
import com.data.data.hmly.service.ticket.TicketDatepriceService;
import com.data.data.hmly.service.ticket.TicketPriceService;
import com.data.data.hmly.service.ticket.entity.TicketDateprice;
import com.data.data.hmly.service.ticket.entity.TicketPrice;
import com.data.data.hmly.service.traffic.TrafficPriceService;
import com.data.data.hmly.service.traffic.entity.Traffic;
import com.data.data.hmly.service.traffic.entity.TrafficPrice;
import com.data.data.hmly.service.transportation.entity.Transportation;
import com.data.hmly.service.translation.flight.juhe.JuheFlightService;
import com.data.hmly.service.translation.flight.juhe.entity.CreateOrderResult;
import com.data.hmly.service.translation.flight.juhe.entity.OrderFlights;
import com.data.hmly.service.translation.flight.juhe.entity.OrderPassenger;
import com.data.hmly.service.translation.train.juhe.JuheTrainService;
import com.data.hmly.service.translation.train.juhe.entity.Passenger;
import com.data.hmly.service.translation.train.juhe.entity.SubmitOrderRequest;
import com.data.hmly.service.translation.train.juhe.entity.SubmitOrderResult;
import com.zuipin.util.PropertiesManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zzl on 2016/2/16.
 */
@Service
public class OrderDispatchService {

    Logger logger = Logger.getLogger(OrderDispatchService.class);

    @Resource
    private PropertiesManager propertiesManager;

    @Resource
    private OrderCancelService orderCancelService;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private TicketPriceService ticketPriceService;
    @Resource
    private TicketDatepriceService ticketDatepriceService;
    @Resource
    private CtripTicketService ctripTicketService;
    @Resource
    private CtripTicketApiService ctripTicketApiService;
    @Resource
    private TrafficPriceService trafficPriceService;
    @Resource
    private HotelPriceService hotelPriceService;
    @Resource
    private ElongHotelService elongHotelService;
    @Resource
    private OrderLogService orderLogService;
    @Resource
    private OrderMsgService orderMsgService;

    /**
     * 门票下单
     * proType: scenic
     *
     * @param orderDetail
     */
    public Map<String, Object> doDispatchToTicket(Order order, OrderDetail orderDetail) {
        User user = orderLogService.getSysOrderLogUser();
        orderDetail = orderDetailService.get(orderDetail.getId());
        // 预定结果
        Map<String, Object> result = new HashMap<String, Object>();
        Long costId = orderDetail.getCostId();
        // 获取门票的价格概要信息
        TicketPrice ticketPrice = ticketPriceService.findFullById(costId);
        Long ctripTicketId = ticketPrice.getCtripTicketId();
        Long ctripResourceId = ticketPrice.getCtripResourceId();
        // 出发日期
        Date playDate = orderDetail.getPlayDate();
        //
        CtripOrderFormResourceInfo ctripOrderFormResourceInfo = new CtripOrderFormResourceInfo();
        ctripOrderFormResourceInfo.setResourceId(ctripResourceId);
        ctripOrderFormResourceInfo.setUseDate(playDate);
        // 门票数量
        ctripOrderFormResourceInfo.setQuantity(orderDetail.getNum());
        // 获取预定日期的的实际价格信息
        Long ticketPriceId = ticketPrice.getId();
        TicketDateprice ticketDateprice = ticketDatepriceService.getTicketDatePrice(ticketPriceId, playDate);
        // 获得具体日期的价格
        ctripOrderFormResourceInfo.setPrice(ticketDateprice.getPrice());
        // 批量下单列表
        List<CtripOrderFormResourceInfo> resourceInfoList = new ArrayList<CtripOrderFormResourceInfo>();
        resourceInfoList.add(ctripOrderFormResourceInfo);
        // 订单联系人信息
        CtripOrderContactInfo contactInfo = new CtripOrderContactInfo();
        // 订单联系人名称
        String recName = order.getRecName();
        // 订单联系人手机
        String mobilePhone = order.getMobile();
        contactInfo.setName(recName);
        contactInfo.setMobile(mobilePhone);
        // 获取旅客列表
        List<OrderTourist> orderTouristList = orderDetail.getOrderTouristList();
        // 定义携程旅客信息列表
        List<CtripOrderPassengerInfo> passengerInfoList = new ArrayList<CtripOrderPassengerInfo>();
        for (OrderTourist orderTourist : orderTouristList) {
            CtripOrderPassengerInfo passengerInfo = new CtripOrderPassengerInfo();
            // 旅客信息id (旅客信息表的id, 非!用户表id)
            passengerInfo.setCustomerInfoId(orderTourist.getId());
            // 旅客名称
            passengerInfo.setcName(orderTourist.getName());
            // 旅客身份类型
            passengerInfo.setIdCardType(1);
            // 旅客身份证号码
            passengerInfo.setIdCardNo(orderTourist.getIdNumber());
            // 旅客联系信息
            passengerInfo.setContactInfo(orderTourist.getTel());
            // 保存一个旅客信息
            passengerInfoList.add(passengerInfo);
        }
        //
        CtripOrderFormInfo ctripOrderFormInfo = ctripTicketService.saveOrderInfo(ctripTicketId, resourceInfoList, contactInfo, passengerInfoList);
        // 写入订单 日志
        OrderLog orderLog1 = orderLogService.createOrderLog(user, "订单详情#" + orderDetail.getId() + "保存接口订单信息成功(#" + ctripOrderFormInfo.getId() + "),现在状态:" + orderDetail.getStatus().getDescription(), order.getId(), orderDetail.getId());
        orderLogService.loggingOrderLog(orderLog1);
        String orderId = "FAILED" + "-" + ctripOrderFormInfo.getId();
        try {
            String uuid = UUID.randomUUID().toString();
            ctripTicketApiService.doCreateOrder(ctripOrderFormInfo, contactInfo, passengerInfoList, uuid);
            OrderLog orderLog2 = orderLogService.createOrderLog(user, "订单详情#" + orderDetail.getId() + "已经向接口下单(#" + ctripOrderFormInfo.getCtripOrderId() + "),现在状态:" + orderDetail.getStatus().getDescription(), order.getId(), orderDetail.getId());
            orderLogService.loggingOrderLog(orderLog2);
            orderId = Long.toString(ctripOrderFormInfo.getCtripOrderId());
            // 再次检查订单状态
            if (com.data.data.hmly.service.ctripcommon.enums.OrderStatus.SUCCESS.equals(ctripOrderFormInfo.getOrderStatus())) {
                // 更改初始订单状态, 更新实际订单id
                result.put("orderId", orderId);
                result.put("status", OrderDetailStatus.SUCCESS);
                result.put("apiResult", "接口(门票)下单成功");
                OrderLog orderLog3 = orderLogService.createOrderLog(user, "订单详情#" + orderDetail.getId() + "接口下单成功(#" + ctripOrderFormInfo.getCtripOrderId() + ")", order.getId(), orderDetail.getId());
                orderLogService.loggingOrderLog(orderLog3);
                // @SMS 发送门票预订成功短信
                orderMsgService.doSendTicketBookingSuccessMsg(orderDetail, ticketPrice);
                return result;
//                updateOrderStatus(orderDetail, orderId, OrderDetailStatus.SUCCESS, ProductType.scenic);
            }
            // 更新初始订单状态
            result.put("orderId", orderId);
            result.put("status", OrderDetailStatus.FAILED);
            result.put("apiResult", "下单异常, 检查接口(门票)");
            OrderLog orderLog3 = orderLogService.createOrderLog(user, "订单详情#" + orderDetail.getId() + "接口下单异常(#" + ctripOrderFormInfo.getCtripOrderId() + ")检查接口", order.getId(), orderDetail.getId());
            orderLogService.loggingOrderLog(orderLog3);
            // @SMS 发送门票预订失败短信
            orderMsgService.doSendTicketBookingFailMsg(orderDetail, ticketPrice);
            return result;
//            updateOrderStatus(orderDetail, orderId, OrderDetailStatus.FAILED, ProductType.scenic);
        } catch (Exception e) {
            // 下单失败
            logger.error("#订单详情OrderDetail#" + orderDetail.getId() + "@" + "门票#" + orderId + "下单异常", e);
            // 回写失败状态
            ctripTicketApiService.updateOrderFail(ctripOrderFormInfo);
            result.put("apiResult", "下单异常, 检查接口(门票)");
            result.put("orderId", Long.toString(ctripOrderFormInfo.getId()));
            result.put("status", OrderDetailStatus.FAILED);
            OrderLog orderLog4 = orderLogService.createOrderLog(user, "订单详情#" + orderDetail.getId() + "接口下单异常(#" + ctripOrderFormInfo.getCtripOrderId() + ")检查接口", order.getId(), orderDetail.getId());
            orderLogService.loggingOrderLog(orderLog4);
            return result;
        }
    }

    public Map<String, Object> doDispatchToHotel(Order order, OrderDetail orderDetail) {
        User user = orderLogService.getSysOrderLogUser();
        orderDetail = orderDetailService.get(orderDetail.getId());
        // 预定结果
        Map<String, Object> result = new HashMap<String, Object>();
        Long costId = orderDetail.getCostId();
        HotelPrice hotelPrice = hotelPriceService.findFullById(costId);
        // 创建酒店订单详情
        CreateOrderCondition condition = getHotelOderCondition(orderDetail, hotelPrice);
        if (PriceStatus.GUARANTEE.equals(hotelPrice.getStatus())) {
            if (condition.getCreditCard() == null) {
                result.put("apiResult", "接口(酒店)下单失败(信用卡信息错误或不可用!)");
                result.put("orderId", "FAILED");
                result.put("status", OrderDetailStatus.FAILED);
                // @SMS 发送酒店预订失败短信
                orderMsgService.doSendHotelBookingFailMsg(orderDetail, hotelPrice);
                return result;
            }
        }
        //酒店产品(Product)
        Product product = orderDetail.getProduct();
        // 酒店的id, 艺龙的, 也就是product里面的sourceid
        condition.setHotelId(Long.toString(product.getSourceId()));
        // 酒店房间的id, 艺龙的, 也就是roomId
        condition.setRoomTypeId(String.format("%04d", Integer.parseInt(hotelPrice.getRoomId())));
        // 酒店房间的价格计划id,艺龙的, 也就是reteplanCode
        String ratePlanCodeStr = hotelPrice.getRatePlanCode();
        String ratePlanCode = "";
        if (ratePlanCodeStr.contains("-")) {
            ratePlanCode = ratePlanCodeStr.split("-")[0];
        } else {
            ratePlanCode = ratePlanCodeStr;
        }
        condition.setRatePlanId(Integer.parseInt(ratePlanCode));
        // 计算总价格
//        HotelDetail hotelDetail = elongHotelService.getHotelDetail(orderDetail.getPlayDate(), orderDetail.getLeaveDate(), hotelPrice.getHotel().getSourceId(), 1, "123");
//        Float nowPrice = getPriceByHotelDetail(hotelDetail, hotelPrice.getRoomId());
        BigDecimal totalFee = BigDecimal.valueOf(hotelPrice.getPrice() * orderDetail.getNum() * orderDetail.getDays());
        condition.setTotalPrice(totalFee);
        // 付款类型
        condition.setPaymentType(EnumPaymentType.SelfPay);
        condition.setSupplierCardNo(null);
        //旅行帮订单id
        condition.setAffiliateConfirmationId(order.getOrderNo());
        //短信提醒
        condition.setConfirmationType(EnumConfirmationType.SMS_cn);
        // 联系人
        Contact contact = new Contact();
        contact.setName(order.getRecName());
        contact.setMobile(order.getMobile());
        //联系人
        condition.setContact(contact);
        // 信用卡(暂时没用)
//        condition.setCreditCard(null);
        // 钱币
        condition.setCurrencyCode(EnumCurrencyCode.RMB);
        // ip地址
        condition.setCustomerIPAddress(order.getIpAddr());
        //用户类型
        condition.setCustomerType(EnumGuestTypeCode.Chinese);
        //其他一些附加信息
        condition.setExtendInfo(null);
        condition.setInvoice(null);
        condition.setIsForceGuarantee(false);
        condition.setIsGuaranteeOrCharged(false);
        condition.setIsNeedInvoice(false);
        condition.setNightlyRates(null);
        //给艺龙的备注
        condition.setNoteToElong("");
        condition.setNoteToHotel(null);
        condition.setCustomerIPAddress("27.154.225.170");
        String orderId = "FAILED";
        try {
            // 下单操作
            com.data.data.hmly.service.elong.pojo.CreateOrderResult createOrderResult = elongHotelService.createOrder(condition);
//            orderId = Long.toString(createOrderResult.getOrderId());
            String resultCode = createOrderResult.getCode();
            if ("0".equals(resultCode) && createOrderResult.getCreateOrderResultDetail().getOrderId() > 0) {
                orderId = Long.toString(createOrderResult.getCreateOrderResultDetail().getOrderId());
                OrderLog orderHotelLog1 = orderLogService.createOrderLog(user, "订单详情#"
                        + orderDetail.getId() + "订单提交成功!", order.getId(), orderDetail.getId());
                orderLogService.loggingOrderLog(orderHotelLog1);
//                // *** 发送酒店预订结果短信 ***
//                doSendHotelMsg(orderDetail, createOrderResult);
                // 更改初始订单状态, 更新实际订单id
                result.put("apiResult", "接口(酒店)订单提交成功(" + createOrderResult.getCreateOrderResultDetail().getOrderId() + ")");
                result.put("orderId", orderId);
                result.put("status", OrderDetailStatus.PAYED);
                // @SMS 发送酒店预订成功短信 (将在酒店状态检查定时器中发送成短信)
//                orderMsgService.doSendHotelBookingSuccessMsg(orderDetail, hotelPrice);
                return result;
//            updateOrderStatus(orderDetail, orderId, OrderDetailStatus.SUCCESS, ProductType.hotel);
            }
            if (resultCode.contains("H001044-5")) {
                //H001044-5|下单失败,<Error><Message>订单总价不相等,订单总价应为</Message><SumPrice>228.00000</SumPrice></Error>
                Pattern p = Pattern.compile("<SumPrice>(\\d+\\.?\\d+)");
                Matcher m = p.matcher(resultCode);
                BigDecimal realFee = new BigDecimal(0);
                while (m.find()) {
                    realFee = new BigDecimal(m.group(1));
                }
                condition.setTotalPrice(realFee);
                // 校正价格后重新下单
                createOrderResult = elongHotelService.createOrder(condition);
                if ("0".equals(createOrderResult.getCode()) && createOrderResult.getCreateOrderResultDetail().getOrderId() > 0) {
                    orderId = Long.toString(createOrderResult.getCreateOrderResultDetail().getOrderId());
                    OrderLog orderHotelLog1 = orderLogService.createOrderLog(user, "订单详情#"
                            + orderDetail.getId() + "预定成功!", order.getId(), orderDetail.getId());
                    orderLogService.loggingOrderLog(orderHotelLog1);
                    // *** 发送酒店预订结果短信 ***
//                    doSendHotelMsg(orderDetail, createOrderResult);
                    // 更改初始订单状态, 更新实际订单id
                    result.put("apiResult", "接口(酒店)下单成功!");
                    result.put("orderId", orderId);
                    result.put("status", OrderDetailStatus.PAYED);
                    // @SMS 发送酒店预订成功短信 (将在酒店状态检查定时器中发送成短信)
//                    orderMsgService.doSendHotelBookingSuccessMsg(orderDetail, hotelPrice);
                    return result;
//            updateOrderStatus(orderDetail, orderId, OrderDetailStatus.SUCCESS, ProductType.hotel);
                }
            }
            // 写入订单 日志
            OrderLog orderHotelLog1 = orderLogService.createOrderLog(user, "订单详情#"
                    + orderDetail.getId() + "预定失败! 接口返回原因: "
                    + createOrderResult.getCode(), order.getId(), orderDetail.getId());
            orderLogService.loggingOrderLog(orderHotelLog1);
            // 更新初始订单状态
            result.put("apiResult", createOrderResult.getCode());
            result.put("orderId", orderId);
            result.put("status", OrderDetailStatus.FAILED);
            // *** 发送酒店预订结果短信 ***
//            doSendHotelMsg(orderDetail, createOrderResult);
            // @SMS 发送酒店预订失败短信
            orderMsgService.doSendHotelBookingFailMsg(orderDetail, hotelPrice);
            return result;
//            updateOrderStatus(orderDetail, "FAILED", OrderDetailStatus.FAILED, ProductType.hotel);
        } catch (Exception e) {
            // 写入订单 日志
            OrderLog orderHotelLog1 = orderLogService.createOrderLog(user, "订单详情#"
                    + orderDetail.getId() + "预定失败! 下单出现异常! 检查网络或艺龙接口", order.getId(), orderDetail.getId());
            orderLogService.loggingOrderLog(orderHotelLog1);
            logger.error("#订单详情OrderDetail#" + orderDetail.getId() + "@" + "酒店#" + orderId + "下单异常", e);
            result.put("apiResult", "下单异常, 检查接口(艺龙酒店)");
            result.put("status", OrderDetailStatus.FAILED);
            return result;
        }
    }

    public Map<String, Object> doDispatchToTrain(Order order, OrderDetail orderDetail) {
        User user = orderLogService.getSysOrderLogUser();
        orderDetail = orderDetailService.get(orderDetail.getId());
        Map<String, Object> result = new HashMap<String, Object>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Long costId = orderDetail.getCostId();
        TrafficPrice trafficPrice = trafficPriceService.findFullById(costId);
        //
        Traffic traffic = trafficPrice.getTraffic();
        Transportation leaveTrasTransportation = traffic.getLeaveTransportation();
        Transportation arriveTransportation = traffic.getArriveTransportation();
        // 车次
        String trainCode = traffic.getTrafficCode();
        // 出发火车站代码和名称
        String fromCode = leaveTrasTransportation.getCode();
        String fromName = leaveTrasTransportation.getName();
        // 到达火车站代码和名称
        String toCode = arriveTransportation.getCode();
        String toName = arriveTransportation.getName();
        // 出发日期
        String leaveDate = sdf.format(orderDetail.getPlayDate());
        // 生成订单id
        String orderId = orderDetail.getRealOrderId();
        //
        SubmitOrderRequest submitOrderRequest = new SubmitOrderRequest(leaveDate, fromCode, fromName, toCode, toName, trainCode);
        // 旅客信息列表
        List<OrderTourist> orderTouristList = orderDetail.getOrderTouristList();
        List<Passenger> passengerList = getTrainPassengers(orderTouristList, trafficPrice);
        String trainKey = propertiesManager.getString("JUHE_TRAIN_KEY");
        try {
            //更新实际订单id(可能非真实订单号,注意下方重新获取)
            result.put("orderId", orderId);
            SubmitOrderResult submitOrderResult = JuheTrainService.submitOrder(submitOrderRequest, trainKey, orderId, passengerList);
            if (submitOrderResult.getError_code() == 0) {
                // 不要立即pay!!!
//                com.data.hmly.service.translation.train.juhe.entity.PaymentResult payResult = JuheTrainService.pay(trainKey, submitOrderResult.getResult().getOrderid());
                // 更新真实订单ID
                orderDetail.setRealOrderId(submitOrderResult.getResult().getOrderid());
                orderDetailService.update(orderDetail); //立即更新接口订单id
                // 更改初始订单状态, 更新实际订单id
//                if ("0".equals(payResult.getError_code())) {
//                    result.put("status", OrderDetailStatus.SUCCESS);
//                    return result;
//                } else {
//                    result.put("status", OrderDetailStatus.FAILED);
//                }
                result.put("status", OrderDetailStatus.BOOKING);
                result.put("orderId", orderDetail.getRealOrderId());
                result.put("apiResult", "接口预定中,请稍候查看状态");
                OrderLog orderTrainLog1 = orderLogService.createOrderLog(user, "订单详情#"
                        + orderDetail.getId() + "向接口提交订单成功,现在状态:"
                        + OrderDetailStatus.BOOKING.getDescription()
                        + "(#" + orderDetail.getRealOrderId() + ")", order.getId(), orderDetail.getId());
                orderLogService.loggingOrderLog(orderTrainLog1);
                return result;
//            updateOrderStatus(orderDetail, orderId, OrderDetailStatus.SUCCESS, ProductType.train);
            } else {
                // 更新初始订单状态
                result.put("apiResult", submitOrderResult.getReason());
                result.put("status", OrderDetailStatus.FAILED);
                OrderLog orderTrainLog2 = orderLogService.createOrderLog(user, "订单详情#"
                        + orderDetail.getId() + "向接口提交订单失败,现在状态:"
                        + OrderDetailStatus.FAILED.getDescription()
                        + "(#" + orderDetail.getRealOrderId() + ")", order.getId(), orderDetail.getId());
                orderLogService.loggingOrderLog(orderTrainLog2);
                return result;
//            updateOrderStatus(orderDetail, orderId, OrderDetailStatus.FAILED, ProductType.train);
            }
        } catch (Exception e) {
            logger.error("#订单详情OrderDetail#" + orderDetail.getId() + "@" + "火车票#" + orderId + "下单异常", e);
            result.put("apiResult", "下单异常, 检查接口(火车票)");
            result.put("status", OrderDetailStatus.FAILED);
            OrderLog orderTrainLog2 = orderLogService.createOrderLog(user, "订单详情#"
                    + orderDetail.getId() + "接口下单失败,现在状态:"
                    + OrderDetailStatus.FAILED.getDescription()
                    + "(#" + orderDetail.getRealOrderId() + ")", order.getId(), orderDetail.getId());
            orderLogService.loggingOrderLog(orderTrainLog2);
            return result;
        }
    }

    public Map<String, Object> doDispatchToFlight(Order order, OrderDetail orderDetail) {
        User user = orderLogService.getSysOrderLogUser();
        orderDetail = orderDetailService.get(orderDetail.getId());
        Map<String, Object> result = new HashMap<String, Object>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Long costId = orderDetail.getCostId();
        TrafficPrice trafficPrice = trafficPriceService.get(costId);
        // 机票价格
        Float price = trafficPrice.getPrice();
        //机场建设费
        Float airportBuildFee = trafficPrice.getAirportBuildFee() == null ? 50 : trafficPrice.getAirportBuildFee();
        // 燃油附加费
        Float additionalFuelTax = trafficPrice.getAdditionalFuelTax();
        // 订单总费用
//        Float totalFee = price + airportBuildFee + additionalFuelTax;
        //
        Traffic traffic = trafficPrice.getTraffic();
        Transportation leaveTransportation = traffic.getLeaveTransportation();
        Transportation arriveTransportation = traffic.getArriveTransportation();
        // 起飞机场代码
        String leaveCode = leaveTransportation.getCode();
        // 落地机场代码
        String arriveCode = arriveTransportation.getCode();
        // 出发日期
        String takeoffDate = sdf.format(orderDetail.getPlayDate());
        // 航班代码
        String trafficCode = traffic.getTrafficCode();
        // 舱位代码
        String carbin = trafficPrice.getSeatCode();
        // 航班信息
        OrderFlights flight = new OrderFlights(leaveCode, arriveCode, takeoffDate, trafficCode, carbin);
        //
        List<OrderTourist> orderTouristList = orderDetail.getOrderTouristList();
        // 获取旅客信息列表
        List<OrderPassenger> orderPassengerList = getFilghtPassengers(orderTouristList, trafficPrice);
        String orderId = orderDetail.getRealOrderId();
        String flightKey = propertiesManager.getString("JUHE_FLIGHT_KEY");
        Integer totalAmount = price.intValue() + airportBuildFee.intValue();
        try {
            CreateOrderResult createOrderResult = JuheFlightService.createOrder(flightKey, orderId, totalAmount, flight, orderPassengerList);
            String resuleCode = createOrderResult.getError_code();
            if ("200".equals(resuleCode)) {
//                // 接口支付
//                PaymentResult payResult = JuheFlightService.pay(flightKey, orderId);
//                // 更改初始订单状态, 更新实际订单id
//                if ("200".equals(payResult.getError_code())) {
//                    result.put("status", OrderDetailStatus.SUCCESS);
//                } else {
//                    result.put("status", OrderDetailStatus.FAILED);
//                }
                result.put("status", OrderDetailStatus.BOOKING);
                result.put("orderId", orderDetail.getRealOrderId());
                result.put("apiResult", "接口预定中,请稍候查看状态");
                OrderLog orderFlightLog1 = orderLogService.createOrderLog(user, "订单详情#"
                        + orderDetail.getId() + "向接口提交订单成功,现在状态:"
                        + OrderDetailStatus.BOOKING.getDescription()
                        + "(#" + orderDetail.getRealOrderId() + ")", order.getId(), orderDetail.getId());
                orderLogService.loggingOrderLog(orderFlightLog1);
                return result;
//            updateOrderStatus(orderDetail, orderId, OrderDetailStatus.SUCCESS, ProductType.flight);
            } else {
                // 更新初始订单状态
                result.put("apiResult", createOrderResult.getError_code() + "(" + createOrderResult.getReason() + ")");
                result.put("orderId", orderId);
                result.put("status", OrderDetailStatus.FAILED);
                OrderLog orderTrainLog2 = orderLogService.createOrderLog(user, "订单详情#"
                        + orderDetail.getId() + "向接口提交订单失败,现在状态:"
                        + OrderDetailStatus.FAILED.getDescription()
                        + "(#" + orderDetail.getRealOrderId() + ")", order.getId(), orderDetail.getId());
                orderLogService.loggingOrderLog(orderTrainLog2);
                // @auto_cancel (聚合接口下单失败, 自动取消该机票订单)
                orderCancelService.doStartCancel(order, orderDetail, (SysUser) user);
                OrderLog orderTrainLog3 = orderLogService.createOrderLog(user, "订单详情#"
                        + orderDetail.getId() + "向聚合接口提交订单失败,发起订单取消操作, 现在状态:"
                        + OrderDetailStatus.FAILED.getDescription()
                        + "(#" + orderDetail.getRealOrderId() + ")", order.getId(), orderDetail.getId());
                orderLogService.loggingOrderLog(orderTrainLog3);
                return result;
//            updateOrderStatus(orderDetail, orderId, OrderDetailStatus.FAILED, ProductType.flight);
            }
        } catch (Exception e) {
            logger.error("#订单详情OrderDetail#" + orderDetail.getId() + "@" + "机票#" + orderId + "下单异常", e);
            result.put("apiResult", "旅行帮下单异常, 检查接口(机票)");
            result.put("status", OrderDetailStatus.FAILED);
            orderDetail.setStatus(OrderDetailStatus.FAILED);
            orderDetailService.update(orderDetail);
            OrderLog orderTrainLog2 = orderLogService.createOrderLog(user, "订单详情#"
                    + orderDetail.getId() + "旅行帮下单异常, 现在状态:"
                    + OrderDetailStatus.FAILED.getDescription()
                    + "(#" + orderDetail.getRealOrderId() + ")", order.getId(), orderDetail.getId());
            orderLogService.loggingOrderLog(orderTrainLog2);
            // @auto_cancel (旅行帮下单失败, 自动取消该机票订单)
            orderCancelService.doStartCancel(order, orderDetail, (SysUser) user);
            OrderLog orderTrainLog3 = orderLogService.createOrderLog(user, "订单详情#"
                    + orderDetail.getId() + "旅行帮下单异常,发起订单取消操作, 现在状态:"
                    + OrderDetailStatus.FAILED.getDescription()
                    + "(#" + orderDetail.getRealOrderId() + ")", order.getId(), orderDetail.getId());
            orderLogService.loggingOrderLog(orderTrainLog3);
            return result;
        }
    }

    private List<Passenger> getTrainPassengers(List<OrderTourist> orderTouristList, TrafficPrice trafficPrice) {
        List<Passenger> passengerList = new ArrayList<Passenger>();
        // 火车票价格
        Float price = trafficPrice.getPrice();
        for (OrderTourist orderTourist : orderTouristList) {
            String name = orderTourist.getName();
            String idNumber = orderTourist.getIdNumber();
            String seatName = trafficPrice.getSeatName();
            if ("无座".equals(seatName)) {
                seatName = "二等座";
            }
            String seatCode = trafficPrice.getSeatCode();
            Passenger passenger = new Passenger(name, idNumber, Float.toString(price), seatCode, seatName);
            passenger.setPassengerid(orderTourist.getId().intValue());
            passengerList.add(passenger);
        }
        return passengerList;
    }

    /**
     * 获得乘客列表(机票)
     *
     * @param orderTouristList
     * @return
     */
    private List<OrderPassenger> getFilghtPassengers(List<OrderTourist> orderTouristList, TrafficPrice trafficPrice) {
        List<OrderPassenger> orderPassengerList = new ArrayList<OrderPassenger>();
        // 机票价格
        Float price = trafficPrice.getPrice();
        //机场建设费
        Float airportBuildFee = trafficPrice.getAirportBuildFee() == null ? 50 : trafficPrice.getAirportBuildFee();
        // 燃油附加费
        Float additionalFuelTax = trafficPrice.getAdditionalFuelTax();
        for (OrderTourist orderTourist : orderTouristList) {
            String passengerType = orderTourist.getPeopleType() == null ? OrderTouristPeopleType.ADULT.toString() : orderTourist.getPeopleType().toString();
            String passengerName = orderTourist.getName();
            String passengerTel = orderTourist.getTel();
            String idType;
            if (OrderTouristIdType.IDCARD.equals(orderTourist.getIdType())) {
                idType = "0";
            } else if (OrderTouristIdType.PASSPORT.equals(orderTourist.getIdType())) {
                idType = "1";
            } else {
                idType = "2";
            }
            String idNumber = orderTourist.getIdNumber();
            OrderPassenger orderPassenger = new OrderPassenger(passengerType, passengerName, passengerTel, idType, idNumber, Float.toString(price), Float.toString(airportBuildFee == null ? 0 : airportBuildFee), Float.toString(additionalFuelTax == null ? 0 : additionalFuelTax));
            orderPassengerList.add(orderPassenger);
        }
        return orderPassengerList;
    }

    /**
     * 创建艺龙酒店订单部分详情
     *
     * @param orderDetail
     * @return
     */
    private CreateOrderCondition getHotelOderCondition(OrderDetail orderDetail, HotelPrice hotelPrice) {
        User user = orderLogService.getSysOrderLogUser();
        CreateOrderCondition condition = new CreateOrderCondition();
        // 入店时间
        condition.setArrivalDate(orderDetail.getPlayDate());
        // 最早入店时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR, calendar.get(Calendar.HOUR) + 2);
        calendar.set(Calendar.MINUTE, 0);
        Long nowTime = calendar.getTime().getTime();
        Long arriveTime = orderDetail.getPlayDate().getTime() + 36000000l;
        // 处理当天入住的情况
        condition.setEarliestArrivalTime(new Date(arriveTime > nowTime ? arriveTime : nowTime));
        // 最迟入店时间
        condition.setLatestArrivalTime(new Date(orderDetail.getPlayDate().getTime() + 43200000l));
        // 离店时间
        condition.setDepartureDate(orderDetail.getLeaveDate());
        // 入住客户数
        condition.setNumberOfCustomers(orderDetail.getOrderTouristList().size());
        // 入住房间间数目
        condition.setNumberOfRooms(orderDetail.getNum());
        // 房间旅客详情
        condition.setOrderRooms(getRooms(orderDetail));
        // 担保酒店设置信用卡信息
        if (PriceStatus.GUARANTEE.equals(hotelPrice.getStatus())) {
            com.data.data.hmly.service.order.entity.CreditCard creditCard = orderDetail.getCreditCard();
            CreditCard elongCreditCard = toElongCreditCard(creditCard);
            if (null != elongCreditCard) {
                condition.setCreditCard(elongCreditCard);
            } else {
                OrderLog orderTrainLog1 = orderLogService.createOrderLog(user, "订单详情#"
                        + orderDetail.getId() + "接口下单失败(信用卡信息不可用或信息有误!),现在状态:"
                        + OrderDetailStatus.FAILED.getDescription()
                        + "(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId());
                orderLogService.loggingOrderLog(orderTrainLog1);
            }
        }
        return condition;
    }

    /**
     * 担保酒店设置信用卡信息
     *
     * @param creditCard
     * @return
     */
    private CreditCard toElongCreditCard(com.data.data.hmly.service.order.entity.CreditCard creditCard) {
        try {
            CreditCard elongCreditCard = new CreditCard();
            String hotelKey = propertiesManager.getString("ELONG_APP_KEY");
            Long stampCode = System.currentTimeMillis() / 1000;
            String key = hotelKey.substring(hotelKey.length() - 8, hotelKey.length());
            String cardNo = creditCard.getCardNum();
            CreditCardValidateResult cardValidateResult = elongHotelService.validateCreditCart(cardNo);
            cardNo = stampCode + "#" + cardNo;
            cardNo = Tool.encryptDES(cardNo, key);
            boolean isNeedVerifyCode = cardValidateResult.getResult().isIsNeedVerifyCode();
            boolean isValid = cardValidateResult.getResult().isIsValid();
            if (!isValid) {
                return null;
            }
            elongCreditCard.setNumber(cardNo);
            if (isNeedVerifyCode) {
                elongCreditCard.setCVV(creditCard.getCvv());
            }
            elongCreditCard.setExpirationYear(creditCard.getExpirationYear());
            elongCreditCard.setExpirationMonth(creditCard.getExpirationMonth());
            elongCreditCard.setHolderName(creditCard.getHolderName());
            elongCreditCard.setIdType(EnumIdType.valueOf(creditCard.getCreditCardIdType().toString()));
            elongCreditCard.setIdNo(creditCard.getIdNo());
            return elongCreditCard;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//    /**
//     * 根据hotelDetail和对应的roomId获取实时的酒店价格
//     * @param hotelDetail
//     * @param roomId
//     * @return
//     */
//    private Float getPriceByHotelDetail(HotelDetail hotelDetail, String roomId) {
//        List<HotelDetail.ResultEntity.HotelsEntity.RoomsEntity> roomList = hotelDetail.getResult().getHotels().get(0).getRooms();
//
//    }

    /**
     * 酒店每个房间旅客详情信息构建
     *
     * @param orderDetail
     * @return
     */
    private List<CreateOrderRoom> getRooms(OrderDetail orderDetail) {
        List<CreateOrderRoom> rooms = new ArrayList<CreateOrderRoom>();
        for (int i = 0; i < orderDetail.getNum(); i++) {
            // 房间旅客详情
            List<Customer> customers = new ArrayList<Customer>();
            for (OrderTourist orderTourist : orderDetail.getOrderTouristList()) {
                Customer customer = new Customer();
                customer.setName(orderTourist.getName());
                customer.setMobile(orderTourist.getTel());
                customers.add(customer);
            }
            CreateOrderRoom room = new CreateOrderRoom();
            room.setCustomers(customers);
            rooms.add(room);
        }
        return rooms;
    }

//    /**
//     * 酒店短信发送
//     * 旧的发送方法, 已废弃, 无用, 勿用
//     * @param orderDetail
//     * @param createOrderResult
//     */
//    private void doSendHotelMsg(OrderDetail orderDetail,
//                                com.data.data.hmly.service.elong.pojo.CreateOrderResult createOrderResult) {
//        User user = orderLogService.getSysOrderLogUser();
//        Order order = orderDetail.getOrder();
//        HotelPrice hotelPrice = hotelPriceService.findFullById(orderDetail.getCostId());
//        Hotel hotel = hotelPrice.getHotel();
//        StringBuilder msg = new StringBuilder();
//        SendingMsg sendingMsg = new SendingMsg();
//        sendingMsg.setSendtime(new Date());
//        sendingMsg.setStatus(SendStatus.newed);
//        String resultCode = createOrderResult.getCode();
//        if ("0".equals(resultCode)) {
//            Long apiOrderId = createOrderResult.getCreateOrderResultDetail().getOrderId();
//            msg.append("您已成功预订");
//            msg.append(hotel.getName() + "(" + hotelPrice.getRoomName() + "),");
//            msg.append("订单号: " + apiOrderId + ".");
//            msg.append("入住时间" + DateUtils.format(orderDetail.getPlayDate(), "yyyy-MM-dd") + ".");
//            msg.append("稍后将有工作人员与您联系, 请保持通讯畅通.感谢您选择旅行帮出行!");
//            sendingMsg.setContext(msg.toString());
//            sendingMsg.setReceivernum(order.getMobile());
//            sendingMsgService.save(sendingMsg);
//        } else {
//            msg.append("酒店预订失败!如需帮助, 请联系旅行帮客服!感谢您选择旅行帮出行!");
//            sendingMsg.setContext(msg.toString());
//            sendingMsg.setReceivernum(order.getMobile());
//            sendingMsgService.save(sendingMsg);
//        }
//        OrderLog orderFlightLog3 = orderLogService.createOrderLog(user, "订单详情#"
//                + orderDetail.getId() + "," + order.getRecName() + "短信发送成功!"
//                + ",订单详情状态: " + orderDetail.getStatus().getDescription()
//                + ",短信内容: " + msg.toString()
//                + "(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId());
//        orderLogService.loggingOrderLog(orderFlightLog3);
//    }

    /**
     * 更新订单(OrderDetail)状态
     *
     * @param orderDetail
     * @param result
     */
    public void updateOrderStatus(OrderDetail orderDetail, Map<String, Object> result) {
        // 更改初始订单状态, 更新实际订单id
        orderDetail.setRealOrderId(result.get("orderId").toString());
        orderDetail.setApiResult(result.get("apiResult") == null ? "" : result.get("apiResult").toString());
        orderDetail.setStatus(OrderDetailStatus.valueOf(result.get("status").toString()));
        orderDetailService.update(orderDetail);
        OrderDetailStatus status = orderDetail.getStatus();
        ProductType type = ProductType.valueOf(result.get("type").toString());
        if (status.equals(OrderDetailStatus.SUCCESS)) {
            logger.info("订单详情OrderDetail#" + orderDetail.getId() + "@" + type.toString() + "下单完成->" + status.toString());
        } else if (status.equals(OrderDetailStatus.FAILED)) {
            logger.error("订单详情OrderDetail#" + orderDetail.getId() + "@" + type.toString() + "下单完成->" + status.toString());
        } else {
            logger.info("订单详情OrderDetail#" + orderDetail.getId() + "@" + type.toString() + "下单完成->" + status.toString());
        }
    }
}
