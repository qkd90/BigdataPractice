package com.data.data.hmly.service.order;

import com.data.data.hmly.service.common.YhyMsgService;
import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.common.entity.enums.ProductSource;
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
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.hotel.HotelPriceService;
import com.data.data.hmly.service.hotel.entity.HotelPrice;
import com.data.data.hmly.service.hotel.entity.enums.PriceStatus;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.OrderLog;
import com.data.data.hmly.service.order.entity.OrderTourist;
import com.data.data.hmly.service.order.entity.enums.OrderDetailStatus;
import com.data.data.hmly.service.order.entity.enums.OrderLogLevel;
import com.data.data.hmly.service.order.util.MsgTemplateKey;
import com.data.data.hmly.service.order.vo.OrderResult;
import com.zuipin.util.PropertiesManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zzl on 2016/10/21.
 */
@Service
public class YhyHotelOrderService {

    Logger logger = Logger.getLogger(YhyHotelOrderService.class);

    @Resource
    private PropertiesManager propertiesManager;
    @Resource
    private OrderLogService orderLogService;
    @Resource
    private OrderService orderService;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private YhyMsgService yhyMsgService;
    @Resource
    private HotelPriceService hotelPriceService;
    @Resource
    private ElongHotelService elongHotelService;


    public List<OrderResult> doTakeHotelOrder(Long orderId) {
        List<OrderResult> resultList = new ArrayList<OrderResult>();
        Order order = orderService.get(orderId);
        List<OrderDetail> orderDetailList = orderDetailService.getByOrderIdAndProductType(orderId, ProductType.hotel);
        for (OrderDetail orderDetail : orderDetailList) {
            OrderResult result = this.doTakeHotelOrderDetail(order, orderDetail);
            resultList.add(result);
        }
        return resultList;
    }

    public OrderResult doTakeHotelOrderDetail(Order order, OrderDetail orderDetail) {
        OrderResult result = new OrderResult();
        User orderLogUser = orderLogService.getSysOrderLogUser();
        Long orderId = order.getId();
        Product product = orderDetail.getProduct();
        if (product == null) {
            result.setSuccess(false);
            result.setMsg("下单失败!产品信息错误");
            result.setErrorCode("product_null");
            result.setRealOrderId("FAILED");
            result.setOrderDetailId(orderDetail.getId());
            result.setApiResult("酒店民宿下单失败! 对应的产品不存在!");
            result.setOrderDetailStatus(OrderDetailStatus.FAILED);
//            resultList.add(result);
            // 写入订单日志
            OrderLog log1 = orderLogService.createOrderLog(orderLogUser, "订单详情#" + orderDetail.getId()
                    + "下单失败! 对应的产品信息不存在!", orderId, orderDetail.getId(), OrderLogLevel.warn);
            orderLogService.loggingOrderLog(log1);
        }
        // 艺龙酒店
        if (ProductSource.ELONG.equals(product.getSource())) {
            // 艺龙酒店接口下单
            result = this.doTakeToElong(order, orderDetail, result);
//            resultList.add(result);
        } else if (product.getSource() == null || ProductSource.LXB.equals(product.getSource())) {
            // 供应商民宿
            result.setSuccess(true);
            result.setMsg("下单成功");
            result.setErrorCode("0");
            result.setRealOrderId(Long.toString(orderDetail.getId()));
            result.setOrderDetailId(orderDetail.getId());
            result.setApiResult("酒店民宿(供应商)下单成功!");
            result.setOrderDetailStatus(OrderDetailStatus.PAYED);
            Map<String, Object> msgData = new HashMap<String, Object>();
            msgData.put("proName", orderDetail.getProduct().getName());
            // @WEB_SMS 短信发送
            // 用户提示待确认短信
            yhyMsgService.doSendSMS(msgData, order.getMobile(), MsgTemplateKey.WEB_HOTEL_PAY_SUCCESS_TLE);
            // @WEB_SMS 短信发送
            // 商户提示待确认短信
            yhyMsgService.doSendSMS(msgData, product.getCompanyUnit().getSysUnitDetail().getMobile(), MsgTemplateKey.MERCHANT_HOTEL_WAIT_CFM_TLE);
//            resultList.add(result);
            // 写入订单日志
            OrderLog log1 = orderLogService.createOrderLog(orderLogUser, "订单详情(G)#"
                    + orderDetail.getId() + "下单成功!", orderId, orderDetail.getId(), OrderLogLevel.warn);
            orderLogService.loggingOrderLog(log1);
        }
        return result;
    }


    private OrderResult doTakeToElong(Order order, OrderDetail orderDetail, OrderResult orderResult) {
        User user = orderLogService.getSysOrderLogUser();
        orderDetail = orderDetailService.get(orderDetail.getId());
        // 预定结果
        OrderResult result = new OrderResult();
        Long costId = orderDetail.getCostId();
        HotelPrice hotelPrice = hotelPriceService.findFullById(costId);
        // 创建酒店订单详情
        CreateOrderCondition condition = getHotelOderCondition(orderDetail, hotelPrice);
        if (PriceStatus.GUARANTEE.equals(hotelPrice.getStatus())) {
            if (condition.getCreditCard() == null) {
                result.setSuccess(false);
                result.setErrorCode("credit_card_error");
                result.setMsg("下单失败,信用卡无效!");
                result.setApiResult("接口(酒店)下单失败(信用卡信息错误或不可用!)");
                result.setRealOrderId("FAILED");
                result.setOrderDetailId(orderDetail.getId());
                result.setOrderDetailStatus(OrderDetailStatus.FAILED);
                // @SMS 发送酒店预订失败短信
//                orderMsgService.doSendHotelBookingFailMsg(orderDetail, hotelPrice);
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
                result.setSuccess(true);
                result.setErrorCode("0");
                result.setMsg("下单成功!");
                result.setApiResult("接口(酒店)订单提交成功(" + createOrderResult.getCreateOrderResultDetail().getOrderId() + ")");
                result.setRealOrderId(orderId);
                result.setOrderDetailId(orderDetail.getId());
                result.setOrderDetailStatus(OrderDetailStatus.SUCCESS);
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
                    result.setSuccess(true);
                    result.setErrorCode("0");
                    result.setMsg("下单成功!");
                    result.setApiResult("接口(酒店)订单提交成功(" + createOrderResult.getCreateOrderResultDetail().getOrderId() + ")");
                    result.setRealOrderId(orderId);
                    result.setOrderDetailId(orderDetail.getId());
                    result.setOrderDetailStatus(OrderDetailStatus.SUCCESS);
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
            result.setSuccess(false);
            result.setErrorCode("hotel_order_failed");
            result.setMsg("下单失败!");
            result.setApiResult(createOrderResult.getCode());
            result.setRealOrderId(orderId);
            result.setOrderDetailId(orderDetail.getId());
            result.setOrderDetailStatus(OrderDetailStatus.FAILED);
            // *** 发送酒店预订结果短信 ***
//            doSendHotelMsg(orderDetail, createOrderResult);
            // @SMS 发送酒店预订失败短信
//            orderMsgService.doSendHotelBookingFailMsg(orderDetail, hotelPrice);
            return result;
//            updateOrderStatus(orderDetail, "FAILED", OrderDetailStatus.FAILED, ProductType.hotel);
        } catch (Exception e) {
            // 写入订单 日志
            OrderLog orderHotelLog1 = orderLogService.createOrderLog(user, "订单详情#"
                    + orderDetail.getId() + "预定失败! 下单出现异常! 检查网络或艺龙接口", order.getId(), orderDetail.getId());
            orderLogService.loggingOrderLog(orderHotelLog1);
            logger.error("#订单详情OrderDetail#" + orderDetail.getId() + "@" + "酒店#" + orderId + "下单异常", e);
            result.setSuccess(false);
            result.setErrorCode("hotel_order_error");
            result.setMsg("下单失败!");
            result.setApiResult("下单异常, 检查接口(艺龙酒店)");
            result.setRealOrderId(orderId);
            result.setOrderDetailId(orderDetail.getId());
            result.setOrderDetailStatus(OrderDetailStatus.FAILED);
            return result;
        }
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
}
