package com.data.data.hmly.service.outOrder;

import com.data.data.hmly.service.SysUnitService;
import com.data.data.hmly.service.balance.BalanceService;
import com.data.data.hmly.service.balance.entity.enums.AccountType;
import com.data.data.hmly.service.common.entity.QuantitySales;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.cruiseship.CruiseShipRoomService;
import com.data.data.hmly.service.cruiseship.CruiseShipService;
import com.data.data.hmly.service.cruiseship.entity.CruiseShip;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipRoom;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.hotel.HotelPriceService;
import com.data.data.hmly.service.hotel.HotelService;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.hotel.entity.HotelPrice;
import com.data.data.hmly.service.line.LineService;
import com.data.data.hmly.service.line.LinetypepriceService;
import com.data.data.hmly.service.line.LinetypepricedateService;
import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.line.entity.Linetypeprice;
import com.data.data.hmly.service.line.entity.enums.LineConfirmAndPayType;
import com.data.data.hmly.service.order.OrderDetailService;
import com.data.data.hmly.service.order.OrderLogService;
import com.data.data.hmly.service.order.OrderMsgService;
import com.data.data.hmly.service.order.OrderService;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.OrderLog;
import com.data.data.hmly.service.order.entity.enums.OrderDetailStatus;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.order.entity.enums.OrderType;
import com.data.data.hmly.service.outOrder.entity.JszxOrder;
import com.data.data.hmly.service.outOrder.entity.JszxOrderDetail;
import com.data.data.hmly.service.outOrder.entity.enums.JszxOrderDetailPriceType;
import com.data.data.hmly.service.outOrder.entity.enums.JszxOrderStatus;
import com.data.data.hmly.service.outOrder.entity.enums.JszxOrderType;
import com.data.data.hmly.service.outOrder.entity.enums.SourceType;
import com.data.data.hmly.service.ticket.TicketDatepriceService;
import com.data.data.hmly.service.ticket.TicketPriceService;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.data.data.hmly.service.ticket.entity.TicketDateprice;
import com.data.data.hmly.service.ticket.entity.TicketPrice;
import com.data.data.hmly.service.ticket.entity.enmus.TicketType;
import com.data.data.hmly.service.traffic.TrafficPriceCalenderService;
import com.data.data.hmly.service.traffic.TrafficPriceService;
import com.data.data.hmly.service.traffic.TrafficService;
import com.data.data.hmly.service.traffic.entity.Traffic;
import com.data.data.hmly.service.traffic.entity.TrafficPrice;
import com.zuipin.util.DateUtils;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2016/6/1.
 */
@Service
public class OutOrderDispatchService {

    @Resource
    private PropertiesManager propertiesManager;
    @Resource
    private SysUnitService sysUnitService;

    @Resource
    private OrderService orderService;
    @Resource
    private OrderLogService orderLogService;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private OrderMsgService orderMsgService;
    @Resource
    private TicketPriceService ticketPriceService;
    @Resource
    private TicketDatepriceService ticketDatepriceService;
    @Resource
    private LineService lineService;
    @Resource
    private LinetypepriceService linetypepriceService;
    @Resource
    private LinetypepricedateService linetypepricedateService;
    @Resource
    private HotelService hotelService;
    @Resource
    private HotelPriceService hotelPriceService;
    @Resource
    private CruiseShipService cruiseShipService;
    @Resource
    private CruiseShipRoomService cruiseShipRoomService;


    @Resource
    private JszxOrderService jszxOrderService;
    @Resource
    private JszxOrderDetailService jszxOrderDetailService;

    @Resource
    private BalanceService balanceService;


    /**
     * 供应商门票下单
     * @param order
     * @param orderDetail
     * @return
     * productType: scenic
     */
    public Map<String, Object> doDispatchToTicket(Order order, OrderDetail orderDetail) {
        // 预定结果
        Map<String, Object> result = new HashMap<String, Object>();
        User user = orderLogService.getSysOrderLogUser();
        String logName = OrderType.scenic.equals(order) ? "门票" : "游艇帆船";
        try {
            order = orderService.get(order.getId());
            SysUnit companyUint = this.getCompanyUnit();
            orderDetail = orderDetailService.get(orderDetail.getId());
            Long costId = orderDetail.getCostId();
            // 获取门票的价格概要信息(门票价格类型)
            TicketPrice ticketPrice = ticketPriceService.findFullById(costId);
            Ticket ticket = ticketPrice.getTicket();
            TicketDateprice ticketDateprice = ticketDatepriceService.getTicketDatePrice(ticketPrice.getId(), orderDetail.getPlayDate());
            // 新建供应商系统订单
            JszxOrder jszxOrder = new JszxOrder();
            jszxOrder.setProName(ticket.getName());
            jszxOrder.setProduct(ticket);
            if (OrderType.sailboat.equals(order.getOrderType())) {
                jszxOrder.setProType(ProductType.sailboat);
                // 设置门票订单子类型
                if (TicketType.sailboat.equals(ticket.getTicketType())) {
                    jszxOrder.setJszxOrderType(JszxOrderType.sailboat);
                } else if (TicketType.yacht.equals(ticket.getTicketType())) {
                    jszxOrder.setJszxOrderType(JszxOrderType.yacht);
                }
            } else {
                jszxOrder.setProType(ProductType.scenic);
                // 设置门票订单子类型
                jszxOrder.setJszxOrderType(JszxOrderType.scenic);
            }
            if (ticket.getCompanyUnit() != null) {
                jszxOrder.setSupplierUnit(ticket.getCompanyUnit());
            }
            jszxOrder.setStatus(JszxOrderStatus.UNPAY);
            jszxOrder.setCompanyUnit(companyUint);
            jszxOrder.setOrderSourceId(order.getId());
            jszxOrder.setSourceType(SourceType.LVXBANG);
            jszxOrder.setOrderNo(order.getOrderNo());
            jszxOrder.setValidDay(ticket.getValidOrderDay());
            // 订单实际金额, 总价
            jszxOrder.setActualPayPrice(order.getPrice());
            jszxOrder.setTotalPrice(order.getPrice());
            // 联系人, 联系电话
            jszxOrder.setContact(order.getRecName());
            jszxOrder.setPhone(order.getMobile());
            jszxOrderService.save(jszxOrder, this.getAccountUser(), companyUint);
            OrderLog orderLog1 = orderLogService.createOrderLog(user, "订单详情(G)#" + orderDetail.getId()
                    + "保存供应商" + logName + "订单信息成功(#" + jszxOrder.getId() + "),现在状态:"
                    + orderDetail.getStatus().getDescription(), order.getId(), orderDetail.getId());
            orderLogService.loggingOrderLog(orderLog1);
            // 保存供应商订单id到旅行帮订单信息中
            order.setJszxOrderId(jszxOrder.getId());
            orderService.update(order);
            // 供应商系统订单详情
            List<JszxOrderDetail> jszxOrderDetailList = new ArrayList<JszxOrderDetail>();
            Integer countInt = orderDetail.getNum();
            Float orderTotalPrice = 0F;
            Float orderQuantityTotalPrice = 0F;
            JszxOrderDetail jszxOrderDetail = new JszxOrderDetail();
            jszxOrderDetail.setJszxOrder(jszxOrder);
            // 门票价格类型id
            jszxOrderDetail.setTypePriceId(ticketPrice.getId());
            // 门票价格类型名称
            jszxOrderDetail.setTicketName(ticketPrice.getName());
            // 门票价格类型
            jszxOrderDetail.setType(formatPriceType(ticketPrice.getType()));
            // 门票类型数量
            jszxOrderDetail.setCount(countInt);
            // 门票类型剩余数量(未验票数量)
            jszxOrderDetail.setRestCount(countInt);
            // 门票销售价格
            jszxOrderDetail.setSalesPrice(ticketDateprice.getPriPrice() * countInt);
            // 门票单价
            Float singlePrice = ticketDateprice.getPriPrice();
            // 门票总价
            Float totalPrice = ticketDateprice.getPriPrice() * countInt;
            // 门票单价
            jszxOrderDetail.setPrice(singlePrice);
            // 实际支付金额
            jszxOrderDetail.setActualPay(totalPrice);
            // 供应商订单详情总价(不含佣金)
            jszxOrderDetail.setTotalPrice(totalPrice);
            // 销售总价 (含佣金)
            jszxOrderDetail.setSalesPrice(totalPrice);
            //拱量
            QuantitySales quantitySales = jszxOrderService.getQuantitySales(jszxOrder.getProduct(), ticketPrice.getId(), jszxOrder.getCompanyUnit(), jszxOrder);
            if (quantitySales != null) {
                Float quantityTotalPrice = jszxOrderService.getQuantityTotalPrice(quantitySales, countInt, ticketDateprice.getPriPrice(), "scenic");
                jszxOrderDetail.setQuantityPrice(quantityTotalPrice);
                jszxOrderDetail.setActualPay(quantityTotalPrice);
                orderTotalPrice += quantityTotalPrice;
                orderQuantityTotalPrice += quantityTotalPrice;
            } else {
                jszxOrderDetail.setActualPay(totalPrice);
                orderQuantityTotalPrice += totalPrice;
                orderTotalPrice += totalPrice;
            }
            Integer validDay = ticket.getValidOrderDay() == null ? 1 : ticket.getValidOrderDay();
            //使用时间
            Date startTime = DateUtils.parse(orderDetail.getPlayDate() + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
            jszxOrderDetail.setStartTime(startTime);
            //门票使用截至时间
            Date endTime = DateUtils.getEndDay(startTime, validDay - 1);
            jszxOrderDetail.setEndTime(endTime);
            jszxOrderDetail.setRefundCount(0);
            jszxOrderDetailList.add(jszxOrderDetail);
            jszxOrder.setActualPayPrice(orderTotalPrice); //更新拱量后的总价
            jszxOrder.setQuantityTotalPrice(orderQuantityTotalPrice); //更新拱量价
            jszxOrderDetailService.saveAll(jszxOrderDetailList);
            jszxOrderService.update(jszxOrder, jszxOrder.getUser(), jszxOrder.getCompanyUnit());
            OrderLog orderLog2 = orderLogService.createOrderLog(user, "订单详情#" + orderDetail.getId()
                    + "保存供应商" + logName + "订单详情信息成功(#" + jszxOrderDetail.getId()
                    + "),现在状态:" + orderDetail.getStatus().getDescription(), order.getId(), orderDetail.getId());
            orderLogService.loggingOrderLog(orderLog2);
            // 检查余额并支付
            SysUser accountUser = this.getAccountUser();
            Double rechargePrice = jszxOrder.getActualPayPrice() - accountUser.getBalance();
            if (rechargePrice <= 0) {
                // 余额充足
                Double payPrice = jszxOrder.getActualPayPrice().doubleValue();
                SysUser jszxOrderUser = jszxOrder.getUser();
                balanceService.updateBalance(payPrice, AccountType.consume, jszxOrderUser.getId(), jszxOrder.getSupplierId(), user.getId(), jszxOrder.getOrderNo(), jszxOrder.getId());
                jszxOrder.setStatus(JszxOrderStatus.PAYED);
                jszxOrder.setActualPayPrice(jszxOrder.getActualPayPrice());
                int msgCount = 0;
                if (jszxOrder.getMsgCount() != null) {
                    msgCount =  jszxOrder.getMsgCount() + 1;
                } else {
                    msgCount++;
                }
                jszxOrder.setMsgCount(msgCount);
                jszxOrderService.update(jszxOrder, jszxOrderUser, jszxOrder.getCompanyUnit());
                OrderLog orderLog3 = orderLogService.createOrderLog(user, "订单详情#" + orderDetail.getId()
                        + "供应商" + logName + "订单支付成功!(#" + jszxOrder.getId() + "##" + jszxOrderDetail.getId()
                        + "),现在状态:" + orderDetail.getStatus().getDescription(), order.getId(), orderDetail.getId());
                orderLogService.loggingOrderLog(orderLog3);
                result.put("orderId", jszxOrderDetail.getId());
                result.put("status", OrderDetailStatus.SUCCESS);
                result.put("apiResult", "接口(供应商)门票下单成功");
                OrderLog orderLog4 = orderLogService.createOrderLog(user, "订单详情#" + orderDetail.getId()
                        + "供应商" + logName + "下单成功!(#" + jszxOrder.getId() + "##" + jszxOrderDetail.getId()
                        + "),现在状态:" + orderDetail.getStatus().getDescription(), order.getId(), orderDetail.getId());
                orderLogService.loggingOrderLog(orderLog4);
                // @SMS 发送门票预订成功短信
                orderMsgService.doSendTicketBookingSuccessMsg(orderDetail, ticketPrice);
                // @SMS 发送验证码短信
                jszxOrderService.doSendMsg(jszxOrder, null);
            } else {
                // 余额不足
                OrderLog orderLog3 = orderLogService.createOrderLog(user, "订单详情#" + orderDetail.getId()
                        + "供应商" + logName + "订单支付失败(余额不足)!(#" + jszxOrder.getId() + "##" + jszxOrderDetail.getId()
                        + "),现在状态:" + orderDetail.getStatus().getDescription(), order.getId(), orderDetail.getId());
                orderLogService.loggingOrderLog(orderLog3);
                result.put("orderId", jszxOrderDetail.getId());
                result.put("status", OrderDetailStatus.FAILED);
                result.put("apiResult", "接口(供应商)" + logName + "下单失败!供应商余额不足");
            }
        } catch (Exception e) {
            // 下单异常
            e.printStackTrace();
            OrderLog orderLog3 = orderLogService.createOrderLog(user, "订单详情#" + orderDetail.getId()
                    + "供应商" + logName + "订单下单异常,现在状态:"
                    + orderDetail.getStatus().getDescription(), order.getId(), orderDetail.getId());
            orderLogService.loggingOrderLog(orderLog3);
            result.put("orderId", "0");
            result.put("status", OrderDetailStatus.FAILED);
            result.put("apiResult", "接口(供应商)" + logName + "下单失败!下单异常");
        }
        return result;
    }

    /**
     * 供应商线路下单
     * @param order
     * @param orderDetails
     * @return
     * productType: line
     */
    public Map<String, Object> doDispatchToLine(Order order, List<OrderDetail> orderDetails) {
        // 预定结果
        Map<String, Object> result = new HashMap<String, Object>();
        User user = orderLogService.getSysOrderLogUser();
        SysUnit companyUint = this.getCompanyUnit();
        try {
            Long lineId = orderDetails.get(0).getProduct().getId();
            Line line = lineService.loadLine(lineId);
            // 新建供应商系统订单
            JszxOrder jszxOrder = new JszxOrder();
            jszxOrder.setProName(line.getName());
            jszxOrder.setProduct(line);
            jszxOrder.setProType(ProductType.line);
            jszxOrder.setOrderSourceId(order.getId());
            jszxOrder.setSourceType(SourceType.LVXBANG);
            jszxOrder.setOrderNo(order.getOrderNo());
            if (LineConfirmAndPayType.confirm.equals(line.getConfirmAndPay())) {
                jszxOrder.setIsConfirm(0);
            } else {
                jszxOrder.setIsConfirm(-2);
            }
            jszxOrder.setStatus(JszxOrderStatus.WAITING);
            // 订单实际金额, 总价
            jszxOrder.setActualPayPrice(order.getPrice());
            jszxOrder.setTotalPrice(order.getPrice());
            // 联系人, 联系电话
            jszxOrder.setContact(order.getRecName());
            jszxOrder.setPhone(order.getMobile());
            if (line.getCompanyUnit() != null) {
                jszxOrder.setSupplierUnit(line.getCompanyUnit());
            }
            jszxOrderService.save(jszxOrder, this.getAccountUser(), this.getCompanyUnit());
            OrderLog orderLog1 = orderLogService.createOrderLog(user, "订单(G)#" + order.getId()
                    + "保存供应商线路订单信息成功(#" + jszxOrder.getId()
                    + "),订单(Order)现在状态:" + order.getStatus().getDescription(), order.getId(), null);
            orderLogService.loggingOrderLog(orderLog1);
            // 保存供应商订单id到旅行帮订单信息中
            order.setJszxOrderId(jszxOrder.getId());
            orderService.update(order);
            // 供应商系统订单详情
            List<JszxOrderDetail> jszxOrderDetailList = new ArrayList<JszxOrderDetail>();
            Float orderTotalPrice = 0f;
            Float orderQuantityTotalPrice = 0f;
            for (OrderDetail orderDetail : orderDetails) {
                Long costId = orderDetail.getCostId();
                Linetypeprice linetypeprice = linetypepriceService.findFullById(costId);
                Integer countInt = orderDetail.getNum();
                JszxOrderDetail jszxOrderDetail = new JszxOrderDetail();
                jszxOrderDetail.setJszxOrder(jszxOrder);
                jszxOrderDetail.setTypePriceId(costId);
                jszxOrderDetail.setTicketName(line.getName());
                jszxOrderDetail.setType(formatPriceType(orderDetail.getSeatType()));
                jszxOrderDetail.setCount(countInt);
                jszxOrderDetail.setRestCount(countInt);
                if ("成人".equals(orderDetail.getSeatType())) {
                    jszxOrderDetail.setType(JszxOrderDetailPriceType.adult);
                } else if ("儿童".equals(orderDetail.getSeatType())) {
                    jszxOrderDetail.setType(JszxOrderDetailPriceType.child);
                }
                Float totalPrice = orderDetail.getFinalPrice();
                Float singlePrice = orderDetail.getUnitPrice();
                // 单价
                jszxOrderDetail.setPrice(singlePrice);
                // 实际支付金额
                jszxOrderDetail.setActualPay(totalPrice);
                // 销售总价 (含佣金)
                jszxOrderDetail.setSalesPrice(totalPrice);
                jszxOrderDetail.setRefundCount(0);
                // 供应商订单详情总价(不含佣金)
                jszxOrderDetail.setTotalPrice(totalPrice);
                //拱量
                QuantitySales quantitySales = jszxOrderService.getQuantitySales(jszxOrder.getProduct(), costId, jszxOrder.getCompanyUnit(), jszxOrder);
                if (quantitySales != null) {
                    Float quantityTotalPrice = jszxOrderService.getQuantityTotalPrice(quantitySales, 1, totalPrice, formatPriceTypeToString(orderDetail.getSeatType()));
                    //设置供应商订单详情拱量金额
                    jszxOrderDetail.setQuantityPrice(quantityTotalPrice);
                    //订单票种实际支付总额, 即拱量后供应商订单详情总价
                    jszxOrderDetail.setActualPay(quantityTotalPrice);
                    // 累加供应商订单拱量后总价, 即供应商订单实际总支付金额
                    // 如果没有手动更改实际支付金额, 那么实际支付总价与拱量总价相等
                    orderTotalPrice += quantityTotalPrice;
                    orderQuantityTotalPrice += quantityTotalPrice;
                } else {
                    // 没有发生拱量, 实际支付总价与旅行帮订单详情总价相当, 即价格没有变化
                    jszxOrderDetail.setActualPay(totalPrice);
                    // 累加拱量总价
                    orderQuantityTotalPrice += totalPrice;
                    orderTotalPrice += totalPrice;
                }
                Integer validDay = line.getValidOrderDay();
                try {
                    //使用时间
                    Date startTime = com.data.data.hmly.service.common.util.DateUtils.parse(orderDetail.getPlayDate() + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
                    jszxOrderDetail.setStartTime(startTime);
                    //门票使用截至时间
                    Date endTime = com.data.data.hmly.service.common.util.DateUtils.getEndDay(startTime, validDay - 1);
                    jszxOrderDetail.setEndTime(endTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                jszxOrderDetailList.add(jszxOrderDetail);
            }
            // 更新拱量后的总价
            jszxOrder.setActualPayPrice(orderTotalPrice);
            // 更新拱量价, 如果没有手动更改实际支付总价, 那么拱量总价与实际支付总价相等
            jszxOrder.setQuantityTotalPrice(orderQuantityTotalPrice);
            // 保存所有供应商系统订单详情
            jszxOrderDetailService.saveAll(jszxOrderDetailList);
            jszxOrderService.update(jszxOrder, jszxOrder.getUser(), jszxOrder.getCompanyUnit());
            OrderLog orderLog2 = orderLogService.createOrderLog(user, "订单(G)#" + order.getId()
                    + "保存供应商线路订单详情信息成功(#" + jszxOrder.getId()
                    + "),订单(Order)现在状态:" + order.getStatus().getDescription(), order.getId(), null);
            orderLogService.loggingOrderLog(orderLog2);
            //...//...
            // 无需确认!!的线路, 检查余额并支付
            if (LineConfirmAndPayType.noconfirm.equals(line.getConfirmAndPay())) {
                SysUser accountUser = this.getAccountUser();
                SysUser jszxOrderUser = jszxOrder.getUser();
                Double rechargePrice = jszxOrder.getActualPayPrice() - accountUser.getBalance();
                if (rechargePrice <= 0) {
                    // 余额充足
                    Double payPrice = jszxOrder.getActualPayPrice().doubleValue();
                    balanceService.updateBalance(payPrice, AccountType.consume, jszxOrderUser.getId(), jszxOrder.getSupplierId(), user.getId(), jszxOrder.getOrderNo(), jszxOrder.getId());
                    jszxOrder.setStatus(JszxOrderStatus.PAYED);
                    jszxOrder.setActualPayPrice(jszxOrder.getActualPayPrice());
                    int msgCount = 0;
                    if (jszxOrder.getMsgCount() != null) {
                        msgCount =  jszxOrder.getMsgCount() + 1;
                    } else {
                        msgCount++;
                    }
                    jszxOrder.setMsgCount(msgCount);
                    jszxOrderService.update(jszxOrder, jszxOrderUser, jszxOrder.getCompanyUnit());
                    OrderLog orderLog3 = orderLogService.createOrderLog(user, "订单(G)#" + order.getId()
                            + "供应商线路订单支付成功!(#" + jszxOrder.getId()
                            + "),订单(Order)现在状态:" + order.getStatus().getDescription(), order.getId(), null);
                    orderLogService.loggingOrderLog(orderLog3);
//                    result.put("orderId", jszxOrderDetail.getId());
//                    result.put("status", OrderDetailStatus.SUCCESS);
//                    result.put("apiResult", "接口(供应商)线路下单成功");
                    // 更新旅行帮订单状态(供应商下单成功, 状态变更为 "成功")
                    order.setStatus(OrderStatus.SUCCESS);
                    orderService.update(order);
                    //
//                    result.put("orderId", jszxOrder.getId());
//                    result.put("status", OrderDetailStatus.SUCCESS);
//                    result.put("apiResult", "接口(供应商)线路下单成功!");
                    //
                    OrderLog orderLog4 = orderLogService.createOrderLog(user, "订单(G)#" + order.getId()
                            + "供应商线路下单成功!(#" + jszxOrder.getId()
                            + "),订单(Order)现在状态:" + order.getStatus().getDescription(), order.getId(), null);
                    orderLogService.loggingOrderLog(orderLog4);
                    // @SMS 发送线路预订成功短信
                    orderMsgService.doSendLineBookingSuccessMsg(order, line);
                } else {
                    // 余额不足
                    OrderLog orderLog3 = orderLogService.createOrderLog(user, "订单(G)#" + order.getId()
                            + "供应商线路订单支付失败(余额不足)!等待供应商支付!(#" + jszxOrder.getId()
                            + "),订单(Order)现在状态:"
                            + order.getStatus().getDescription(), order.getId(), null);
                    orderLogService.loggingOrderLog(orderLog3);
                    // 更新供应商订单状态 (余额不足, 状态变更为待支付)
                    jszxOrder.setStatus(JszxOrderStatus.UNPAY);
                    jszxOrderService.update(jszxOrder, jszxOrderUser, jszxOrder.getCompanyUnit());
                    result.put("orderId", jszxOrder.getId());
                    result.put("status", OrderDetailStatus.FAILED);
                    result.put("apiResult", "接口(供应商)线路下单失败!供应商余额不足");
                    // 更新旅行帮订单状态(供应商账户余额不足, 状态变更为 "处理中", 继续等待供应商处理)
                    order.setStatus(OrderStatus.PROCESSING);
                    orderService.update(order);
                }
            } else if (LineConfirmAndPayType.confirm.equals(line.getConfirmAndPay())) {
                // 需要确认的线路订单
//                result.put("orderId", jszxOrder.getId());
//                result.put("status", OrderDetailStatus.BOOKING);
//                result.put("apiResult", "接口(供应商)线路下单成功!等待供应商确认线路订单");
                // 立即更新供应订单id, 以便后续状态检查
//                orderDetail.setRealOrderId(Long.toString(jszxOrder.getId()));
//                orderDetailService.update(orderDetail);
                // 更新旅行帮订单状态 (需要确认的线路, 状态变更为 "处理中...")
                order.setStatus(OrderStatus.PROCESSING);
                orderService.update(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
            OrderLog orderLog3 = orderLogService.createOrderLog(user, "订单(G)#" + order.getId()
                    + "供应商线路订单下单异常,订单(Order)现在状态:"
                    + order.getStatus().getDescription(), order.getId(), null);
            orderLogService.loggingOrderLog(orderLog3);
            // 更新订单状态
            order.setStatus(OrderStatus.FAILED);
            orderService.update(order);
//            result.put("realOrderId", "0");
//            result.put("status", OrderDetailStatus.FAILED);
//            result.put("apiResult", "接口(供应商)线路下单失败!下单异常");
        }
        return result;
    }

    /**
     * 供应商酒店下单
     * @param order
     * @param orderDetails
     * @return
     */
    public Map<String, Object> doDispatchToHotel(Order order, List<OrderDetail> orderDetails) {
        // 预订结果
        Map<String, Object> result = new HashMap<String, Object>();
        User user = orderLogService.getSysOrderLogUser();
        SysUnit companyUnit = this.getCompanyUnit();
        try {
            Long hotelId = orderDetails.get(0).getProduct().getId();
            Hotel hotel = hotelService.get(hotelId);
            // 新建供应商系统订单
            JszxOrder jszxOrder = new JszxOrder();
            jszxOrder.setProName(hotel.getName());
            jszxOrder.setProduct(hotel);
            jszxOrder.setProType(ProductType.hotel);
            jszxOrder.setOrderSourceId(order.getId());
            jszxOrder.setSourceType(SourceType.LVXBANG);
            jszxOrder.setOrderNo(order.getOrderNo());
            // 供应商酒店订单无确认过程
            jszxOrder.setIsConfirm(-2);
            // 供应商订单状态
            jszxOrder.setStatus(JszxOrderStatus.WAITING);
            // 订单实际金额,总价
            // 酒店总价在 OrderDetail 中
            jszxOrder.setActualPayPrice(orderDetails.get(0).getFinalPrice());
            jszxOrder.setTotalPrice(orderDetails.get(0).getFinalPrice());
            // 联系人, 联系电话
            jszxOrder.setContact(order.getRecName());
            jszxOrder.setPhone(order.getMobile());
            if (hotel.getCompanyUnit() != null) {
                jszxOrder.setSupplierUnit(hotel.getCompanyUnit());
            }
            jszxOrderService.save(jszxOrder, this.getAccountUser(), this.getCompanyUnit());
            OrderLog orderLog1 = orderLogService.createOrderLog(user, "订单(G)#" + order.getId()
                    + "保存供应商酒店订单信息成功(#" + jszxOrder.getId()
                    + "),订单(Order)现在状态:" + order.getStatus().getDescription(), order.getId(), null);
            orderLogService.loggingOrderLog(orderLog1);
            // 保存供应商订单id到旅行帮订单信息中
            order.setJszxOrderId(jszxOrder.getId());
            orderService.update(order);
            // 供应商系统订单详情
            List<JszxOrderDetail> jszxOrderDetailList = new ArrayList<JszxOrderDetail>();
            Float orderTotalPrice = 0f;
            Float orderQuantityTotalPrice = 0f;
            for (OrderDetail orderDetail : orderDetails) {
                Long costId = orderDetail.getCostId();
                HotelPrice hotelPrice = hotelPriceService.findFullById(costId);
                JszxOrderDetail jszxOrderDetail = new JszxOrderDetail();
                jszxOrderDetail.setJszxOrder(jszxOrder);
                jszxOrderDetail.setTypePriceId(costId);
                // 酒店名称
                jszxOrderDetail.setTicketName(hotel.getName());
                // 床型描述
                jszxOrderDetail.setDesc(orderDetail.getSeatType());
                // 房间数量
                jszxOrder.setCount(orderDetail.getNum());
                Float totalPrice = orderDetail.getFinalPrice();
                Float singlePrice = orderDetail.getUnitPrice();
                // 单价
                jszxOrderDetail.setPrice(singlePrice);
                // 实际支付金额
                jszxOrderDetail.setActualPay(totalPrice);
                // 销售总价 (含佣金)
                jszxOrderDetail.setTotalPrice(totalPrice);
                // 拱量
                QuantitySales quantitySales = jszxOrderService.getQuantitySales(jszxOrder.getProduct(), costId, jszxOrder.getCompanyUnit(), jszxOrder);
                if (quantitySales != null) {
                    Float quantityTotalPrice = jszxOrderService.getQuantityTotalPrice(quantitySales, 1, totalPrice, "");
                    // 设置供应商订单详情拱量金额
                    jszxOrderDetail.setQuantityPrice(quantityTotalPrice);
                    // 订单票种实际支付总额, 即拱量后供应商订单详情总价
                    jszxOrderDetail.setActualPay(quantityTotalPrice);
                    // 累加供应商订单拱量后总价, 即供应商订单实际总支付金额
                    // 如果没有手动更改实际支付金额, 那么实际支付总价与拱量总价相等
                    orderTotalPrice += quantityTotalPrice;
                    orderQuantityTotalPrice += quantityTotalPrice;
                } else {
                    // 没有发生拱量, 实际支付总价与旅行帮订单详情总价相当, 即价格没有变化
                    jszxOrderDetail.setActualPay(totalPrice);
                    // 累加 拱量总价
                    orderQuantityTotalPrice += totalPrice;
                    orderTotalPrice += totalPrice;
                }
                // 入住, 退房时间
                jszxOrderDetail.setStartTime(orderDetail.getPlayDate());
                jszxOrderDetail.setEndTime(orderDetail.getLeaveDate());
                // ......
                jszxOrderDetailList.add(jszxOrderDetail);
            }
            // 更新拱量后的总价
            jszxOrder.setActualPayPrice(orderTotalPrice);
            // 更新拱量价, 如果没有手动更改实际支付总价, 那么拱量总价与实际支付总价相等
            jszxOrder.setQuantityTotalPrice(orderQuantityTotalPrice);
            // 保存所有供应商系统订单详情
            jszxOrderDetailService.saveAll(jszxOrderDetailList);
            jszxOrderService.update(jszxOrder, jszxOrder.getUser(), jszxOrder.getCompanyUnit());
            OrderLog orderLog2 = orderLogService.createOrderLog(user, "订单(G)#" + order.getId()
                    + "保存供应商酒店订单详情信息成功(#" + jszxOrder.getId()
                    + "),订单(Order)现在状态:" + order.getStatus().getDescription(), order.getId(), null);
            orderLogService.loggingOrderLog(orderLog2);
            // 供应商支付
            SysUser accountUser = this.getAccountUser();
            SysUser jszxOrderUser = jszxOrder.getUser();
            Double rechargePrice = jszxOrder.getActualPayPrice() - accountUser.getBalance();
            if (rechargePrice <= 0) {
                // 余额充足
                Double payPrice = jszxOrder.getActualPayPrice().doubleValue();
                balanceService.updateBalance(payPrice, AccountType.consume, jszxOrderUser.getId(), jszxOrder.getSupplierId(), user.getId(), jszxOrder.getOrderNo(), jszxOrder.getId());
                jszxOrder.setStatus(JszxOrderStatus.PAYED);
                jszxOrder.setActualPayPrice(jszxOrder.getActualPayPrice());
                int msgCount = 0;
                if (jszxOrder.getMsgCount() != null) {
                    msgCount = jszxOrder.getMsgCount() + 1;
                } else {
                    msgCount++;
                }
                jszxOrder.setMsgCount(msgCount);
                jszxOrderService.update(jszxOrder, jszxOrderUser, jszxOrder.getCompanyUnit());
                OrderLog orderLog3 = orderLogService.createOrderLog(user, "订单(G)#" + order.getId()
                        + "供应商酒店订单支付成功!(#" + jszxOrder.getId()
                        + "),订单(Order)现在状态:" + order.getStatus().getDescription(), order.getId(), null);
                orderLogService.loggingOrderLog(orderLog3);
                // 非组合线路订单, 才更新整个订单状态
                if (order.getIsCombineLine() == null || !order.getIsCombineLine()) {
                    order.setStatus(OrderStatus.SUCCESS);
                    orderService.update(order);
                }
                // ......
                OrderLog orderLog4 = orderLogService.createOrderLog(user, "订单(G)#" + order.getId()
                        + "供应商酒店下单成功!(#" + jszxOrder.getId()
                        + "),订单(Order)现在状态:" + order.getStatus().getDescription(), order.getId(), null);
                orderLogService.loggingOrderLog(orderLog4);
                // 更新所有订单详情状态和结果
                for (int i = 0; i < orderDetails.size(); i++) {
                    OrderDetail orderDetail = orderDetails.get(i);
                    orderDetail.setStatus(OrderDetailStatus.SUCCESS);
                    orderDetail.setApiResult("供应商酒店下单成功");
                    orderDetail.setRealOrderId(Long.toString(jszxOrderDetailList.get(i).getId()));
                    orderDetailService.update(orderDetail);
                }
                // @SMS 发送酒店预订成功短信
                orderMsgService.doSendHotelBookingSuccessMsg(orderDetails.get(0), hotelPriceService.findFullById(orderDetails.get(0).getCostId()));
            } else {
                // 余额不足
                OrderLog orderLog3 = orderLogService.createOrderLog(user, "订单(G)#" + order.getId()
                        + "供应商酒店订单支付失败(余额不足)!等待供应商支付!(#" + jszxOrder.getId()
                        + "),订单(Order)现在状态:"
                        + order.getStatus().getDescription(), order.getId(), null);
                orderLogService.loggingOrderLog(orderLog3);
                // 更新供应商订单状态 (余额不足, 状态变更为待支付)
                jszxOrder.setStatus(JszxOrderStatus.UNPAY);
                jszxOrderService.update(jszxOrder, jszxOrderUser, jszxOrder.getCompanyUnit());
                // 更新旅行帮订单状态(供应商账户余额不足, 状态变更为 "处理中", 继续等待供应商处理)
                // 非组合线路订单, 才更新整个订单状态
                if (order.getIsCombineLine() == null || !order.getIsCombineLine()) {
                    order.setStatus(OrderStatus.PROCESSING);
                    orderService.update(order);
                }
                // 更新所有订单详情状态和结果
                for (int i = 0; i < orderDetails.size(); i++) {
                    OrderDetail orderDetail = orderDetails.get(i);
                    orderDetail.setStatus(OrderDetailStatus.BOOKING);
                    orderDetail.setApiResult("供应商酒店订单支付失败(余额不足)!等待供应商支付!");
                    orderDetail.setRealOrderId(Long.toString(jszxOrderDetailList.get(i).getId()));
                    orderDetailService.update(orderDetail);
                }
            }
        } catch (Exception e) {
            StringWriter errorWritter = new StringWriter();
            e.printStackTrace(new PrintWriter(errorWritter));
            OrderLog orderLog1 = orderLogService.createOrderLog(user, "订单(G)#" + order.getId()
                    + "堆栈信息开始####: " + errorWritter.toString() + "#####堆栈信息结束"
                    + "订单(Order)现在状态:" + order.getStatus().getDescription(), order.getId(), null);
            orderLogService.loggingOrderLog(orderLog1);
            // ......
            OrderLog orderLog3 = orderLogService.createOrderLog(user, "订单(G)#" + order.getId()
                    + "供应商酒店订单下单异常,订单(Order)现在状态:"
                    + order.getStatus().getDescription(), order.getId(), null);
            orderLogService.loggingOrderLog(orderLog3);
            // 非组合线路订单, 才更新整个订单状态
            if (order.getIsCombineLine() == null || !order.getIsCombineLine()) {
                order.setStatus(OrderStatus.FAILED);
                orderService.update(order);
            }
            // 更新所有订单详情状态和结果
            for (int i = 0; i < orderDetails.size(); i++) {
                OrderDetail orderDetail = orderDetails.get(i);
                orderDetail.setStatus(OrderDetailStatus.BOOKING);
                orderDetail.setApiResult("供应商酒店订单下单异常!");
                orderDetail.setRealOrderId("FAILED");
                orderDetailService.update(orderDetail);
            }
        }
        return result;
    }

    @Resource
    private TrafficService trafficService;
    @Resource
    private TrafficPriceService trafficPriceService;
    @Resource
    private TrafficPriceCalenderService trafficPriceCalenderService;


    /**
     * 供应商船票下单
     * @param order
     * @param orderDetails
     * @return
     */
    public Map<String, Object> doDispatchToShip(Order order, List<OrderDetail> orderDetails) {
        // 预订结果
        Map<String, Object> result = new HashMap<String, Object>();
        User user = orderLogService.getSysOrderLogUser();
        SysUnit companyUnit = this.getCompanyUnit();
        try {
            Long trafficId = orderDetails.get(0).getProduct().getId();
            Traffic traffic = trafficService.get(trafficId);
            // 新建供应商系统订单
            JszxOrder jszxOrder = new JszxOrder();
            jszxOrder.setProName(traffic.getName());
            jszxOrder.setProduct(traffic);
            jszxOrder.setProType(ProductType.ship);
            jszxOrder.setOrderSourceId(order.getId());
            jszxOrder.setOrderNo(order.getOrderNo());
            // 供应商交通-船票订单无确认过程
            // 供应商订单状态
            jszxOrder.setStatus(JszxOrderStatus.WAITING);
            // 订单实际金额, 总价
            jszxOrder.setActualPayPrice(order.getPrice());
            jszxOrder.setTotalPrice(order.getPrice());
            // 联系人, 联系电话
            jszxOrder.setContact(order.getRecName());
            jszxOrder.setPhone(order.getMobile());
            if (traffic.getCompanyUnit() != null) {
                jszxOrder.setSupplierUnit(traffic.getCompanyUnit());
            }
            // 保存供应商系统订单信息
            jszxOrderService.save(jszxOrder, this.getAccountUser(), this.getCompanyUnit());
            OrderLog orderLog1 = orderLogService.createOrderLog(user, "订单(G)#" + order.getId()
                    + "保存供应商交通(船票)订单信息成功(#" + jszxOrder.getId()
                    + "),订单(Order)现在状态:" + order.getStatus().getDescription(), order.getId(), null);
            orderLogService.loggingOrderLog(orderLog1);
            // 保存供应商订单id到旅行帮订单信息中
            order.setJszxOrderId(jszxOrder.getId());
            orderService.update(order);
            // 供应商系统订单详情
            List<JszxOrderDetail> jszxOrderDetailList = new ArrayList<JszxOrderDetail>();
            Float orderTotalPrice = 0f;
            Float orderQuantityTotalPrice = 0f;
            for (OrderDetail orderDetail : orderDetails) {
                Long costId = orderDetail.getCostId();
                TrafficPrice trafficPrice = trafficPriceService.findFullById(costId);
                JszxOrderDetail jszxOrderDetail = new JszxOrderDetail();
                jszxOrderDetail.setJszxOrder(jszxOrder);
                jszxOrderDetail.setTypePriceId(costId);
                // 交通-船票名称
                jszxOrderDetail.setTicketName(traffic.getName());
                // 交通-船票-票种数量
                jszxOrderDetail.setCount(orderDetail.getNum());
                // 交通-船票-票种描述
                jszxOrderDetail.setDesc(orderDetail.getSeatType());
                // 船票数量
//                jszxOrder.setCount(orderDetail.getNum());
                Float totalPrice = orderDetail.getFinalPrice();
                Float singlePrice = orderDetail.getUnitPrice();
                // 单价
                jszxOrderDetail.setPrice(singlePrice);
                // 实际支付金额
                jszxOrderDetail.setActualPay(totalPrice);
                // 销售总价(含佣金)
                jszxOrderDetail.setTotalPrice(totalPrice);
                // 拱量
                QuantitySales quantitySales = jszxOrderService.getQuantitySales(jszxOrder.getProduct(), costId, jszxOrder.getCompanyUnit(), jszxOrder);
                if (quantitySales != null) {
                    Float quantityTotalPrice = jszxOrderService.getQuantityTotalPrice(quantitySales, 1, totalPrice, "");
                    // 设置供应商订单详情拱量金额
                    jszxOrderDetail.setQuantityPrice(quantityTotalPrice);
                    // 订单票种实际支付总额, 即拱量后供应商订单详情总价
                    jszxOrderDetail.setActualPay(quantityTotalPrice);
                    // 累加供应商订单拱量后总价, 即供应商订单实际总支付金额
                    // 如果没有手动更改实际支付金额, 那么实际支付总价与拱量总价相等
                    orderTotalPrice += quantityTotalPrice;
                    orderQuantityTotalPrice += quantityTotalPrice;
                } else {
                    // 没有发生拱量, 实际支付总价与旅行帮订单详情总价相当, 即价格没有变化
                    jszxOrderDetail.setActualPay(totalPrice);
                    // 累加 拱量总价
                    orderQuantityTotalPrice += totalPrice;
                    orderTotalPrice += totalPrice;
                }
                // 出发, 到达时间
                jszxOrderDetail.setStartTime(orderDetail.getPlayDate());
                jszxOrderDetail.setEndTime(orderDetail.getLeaveDate());
                // ......
                jszxOrderDetailList.add(jszxOrderDetail);
            }
            // 更新拱量后的总价
            jszxOrder.setActualPayPrice(orderTotalPrice);
            // 更新拱量价, 如果没有手动更改实际支付总价, 那么拱量总价与实际支付总价相等
            jszxOrder.setQuantityTotalPrice(orderQuantityTotalPrice);
            // 保存所有供应商系统订单详情
            jszxOrderDetailService.saveAll(jszxOrderDetailList);
            jszxOrderService.update(jszxOrder, jszxOrder.getUser(), jszxOrder.getCompanyUnit());
            OrderLog orderLog2 = orderLogService.createOrderLog(user, "订单(G)#" + order.getId()
                    + "保存供应商船票订单详情信息成功(#" + jszxOrder.getId()
                    + "),订单(Order)现在状态:" + order.getStatus().getDescription(), order.getId(), null);
            orderLogService.loggingOrderLog(orderLog2);
            // 供应商支付
            SysUser accountUser = this.getAccountUser();
            SysUser jszxOrderUser = jszxOrder.getUser();
            Double rechargePrice = jszxOrder.getActualPayPrice() - accountUser.getBalance();
            if (rechargePrice <= 0) {
                // 余额充足
                Double payPrice = jszxOrder.getActualPayPrice().doubleValue();
                balanceService.updateBalance(payPrice, AccountType.consume, jszxOrderUser.getId(), jszxOrder.getSupplierId(), user.getId(), jszxOrder.getOrderNo(), jszxOrder.getId());
                jszxOrder.setStatus(JszxOrderStatus.PAYED);
                jszxOrder.setActualPayPrice(jszxOrder.getActualPayPrice());
                int msgCount = 0;
                if (jszxOrder.getMsgCount() != null) {
                    msgCount = jszxOrder.getMsgCount() + 1;
                } else {
                    msgCount++;
                }
                jszxOrder.setMsgCount(msgCount);
                jszxOrderService.update(jszxOrder, jszxOrderUser, jszxOrder.getCompanyUnit());
                OrderLog orderLog3 = orderLogService.createOrderLog(user, "订单(G)#" + order.getId()
                        + "供应商交通-船票订单支付成功!(#" + jszxOrder.getId()
                        + "),订单(Order)现在状态:" + order.getStatus().getDescription(), order.getId(), null);
                orderLogService.loggingOrderLog(orderLog3);
                // 非组合线路订单, 才更新整个订单状态
                if (order.getIsCombineLine() == null || !order.getIsCombineLine()) {
                    order.setStatus(OrderStatus.SUCCESS);
                    orderService.update(order);
                }
                // ......
                OrderLog orderLog4 = orderLogService.createOrderLog(user, "订单(G)#" + order.getId()
                        + "供应商交通-船票下单成功!(#" + jszxOrder.getId()
                        + "),订单(Order)现在状态:" + order.getStatus().getDescription(), order.getId(), null);
                orderLogService.loggingOrderLog(orderLog4);
                // 更新所有订单详情状态和结果
                for (int i = 0; i < orderDetails.size(); i++) {
                    OrderDetail orderDetail = orderDetails.get(i);
                    orderDetail.setStatus(OrderDetailStatus.SUCCESS);
                    orderDetail.setApiResult("供应商船票下单成功");
                    orderDetail.setRealOrderId(Long.toString(jszxOrderDetailList.get(i).getId()));
                    orderDetailService.update(orderDetail);
                }
                // @SMS 发送交通-船票预订成功短信
                orderMsgService.doSendShipBookingSuccessMsg(order, traffic);
            } else {
                // 余额不足
                OrderLog orderLog3 = orderLogService.createOrderLog(user, "订单(G)#" + order.getId()
                        + "供应商交通-船票订单支付失败(余额不足)!等待供应商支付!(#" + jszxOrder.getId()
                        + "),订单(Order)现在状态:"
                        + order.getStatus().getDescription(), order.getId(), null);
                orderLogService.loggingOrderLog(orderLog3);
                // 更新供应商订单状态 (余额不足, 状态变更为待支付)
                jszxOrder.setStatus(JszxOrderStatus.UNPAY);
                jszxOrderService.update(jszxOrder, jszxOrderUser, jszxOrder.getCompanyUnit());
                // 更新旅行帮订单状态(供应商账户余额不足, 状态变更为 "处理中", 继续等待供应商处理)
                // 非组合线路订单, 才更新整个订单状态
                if (order.getIsCombineLine() == null || !order.getIsCombineLine()) {
                    order.setStatus(OrderStatus.PROCESSING);
                    orderService.update(order);
                }
                // 更新所有订单详情状态和结果
                for (int i = 0; i < orderDetails.size(); i++) {
                    OrderDetail orderDetail = orderDetails.get(i);
                    orderDetail.setStatus(OrderDetailStatus.BOOKING);
                    orderDetail.setApiResult("供应商船票订单支付失败(余额不足)!等待供应商支付!");
                    orderDetail.setRealOrderId(Long.toString(jszxOrderDetailList.get(i).getId()));
                    orderDetailService.update(orderDetail);
                }
            }
            //...
        } catch (Exception e) {
            StringWriter errorWritter = new StringWriter();
            e.printStackTrace(new PrintWriter(errorWritter));
            OrderLog orderLog1 = orderLogService.createOrderLog(user, "订单(G)#" + order.getId()
                    + "堆栈信息开始####: " + errorWritter.toString() + "#####堆栈信息结束"
                    + "订单(Order)现在状态:" + order.getStatus().getDescription(), order.getId(), null);
            orderLogService.loggingOrderLog(orderLog1);
            // ......
            OrderLog orderLog3 = orderLogService.createOrderLog(user, "订单(G)#" + order.getId()
                    + "供应商船票订单下单异常,订单(Order)现在状态:"
                    + order.getStatus().getDescription(), order.getId(), null);
            orderLogService.loggingOrderLog(orderLog3);
            // 非组合线路订单, 才更新整个订单状态
            if (order.getIsCombineLine() == null || !order.getIsCombineLine()) {
                order.setStatus(OrderStatus.FAILED);
                orderService.update(order);
            }
            // 更新所有订单详情状态和结果
            for (int i = 0; i < orderDetails.size(); i++) {
                OrderDetail orderDetail = orderDetails.get(i);
                orderDetail.setStatus(OrderDetailStatus.BOOKING);
                orderDetail.setApiResult("供应商船票订单下单异常!");
                orderDetail.setRealOrderId("FAILED");
                orderDetailService.update(orderDetail);
            }
        }
        return result;
    }


    public Map<String, Object> doDispatchToCruiseShip(Order order, List<OrderDetail> orderDetails) {
        // 预订结果
        Map<String, Object> result = new HashMap<String, Object>();
        User user = orderLogService.getSysOrderLogUser();
        SysUnit companyUint = this.getCompanyUnit();
        try {
            Long cruiseShipId = orderDetails.get(0).getProduct().getId();
            CruiseShip cruiseShip = cruiseShipService.findById(cruiseShipId);
            // 新建供应商系统订单
            JszxOrder jszxOrder = new JszxOrder();
            jszxOrder.setProName(cruiseShip.getName());
            jszxOrder.setProduct(cruiseShip);
            jszxOrder.setProType(ProductType.cruiseship);
            jszxOrder.setOrderSourceId(order.getId());
            jszxOrder.setSourceType(SourceType.LVXBANG);
            jszxOrder.setOrderNo(order.getOrderNo());
            // 邮轮订单需要确认
            jszxOrder.setIsConfirm(0);
            // 订单状态: 等待
            jszxOrder.setStatus(JszxOrderStatus.WAITING);
            // 订单实际金额, 总价
            jszxOrder.setActualPayPrice(order.getPrice());
            jszxOrder.setTotalPrice(order.getPrice());
            // 联系人, 联系电话
            jszxOrder.setContact(order.getRecName());
            jszxOrder.setPhone(order.getMobile());
            //
            if (cruiseShip.getCompanyUnit() != null) {
                jszxOrder.setSupplierUnit(cruiseShip.getCompanyUnit());
            }
            // 保存供应商系统订单信息
            jszxOrderService.save(jszxOrder, this.getAccountUser(), this.getCompanyUnit());
            OrderLog orderLog1 = orderLogService.createOrderLog(user, "订单(G)#" + order.getId()
                    + "保存供应商邮轮订单信息成功(#" + jszxOrder.getId()
                    + "),订单(Order)现在状态:" + order.getStatus().getDescription(), order.getId(), null);
            orderLogService.loggingOrderLog(orderLog1);
            // 保存供应商订单id到旅行帮订单信息中
            order.setJszxOrderId(jszxOrder.getId());
            orderService.update(order);
            // 供应商系统订单详情
            List<JszxOrderDetail> jszxOrderDetailList = new ArrayList<JszxOrderDetail>();
            Float orderTotalPrice = 0f;
            Float orderQuantityTotalPrice = 0f;
            for (OrderDetail orderDetail : orderDetails) {
                Long costId = orderDetail.getCostId();
                CruiseShipRoom cruiseShipRoom = cruiseShipRoomService.findById(costId);
                Integer countInt = orderDetail.getNum();
                JszxOrderDetail jszxOrderDetail = new JszxOrderDetail();
                jszxOrderDetail.setJszxOrder(jszxOrder);
                jszxOrderDetail.setTypePriceId(costId);
                jszxOrderDetail.setTicketName(cruiseShip.getName());
//                jszxOrderDetail.setType();
                jszxOrderDetail.setCount(countInt);
                jszxOrderDetail.setRestCount(countInt);
                Float totalPrice = orderDetail.getFinalPrice();
                Float singlePrice = orderDetail.getUnitPrice();
                // 单价
                jszxOrderDetail.setPrice(singlePrice);
                // 实际支付金额
                jszxOrderDetail.setActualPay(totalPrice);
                // 销售总价 (含佣金)
                jszxOrderDetail.setSalesPrice(totalPrice);
                jszxOrderDetail.setRefundCount(0);
                // 供应商订单详情总价(不含佣金)
                jszxOrderDetail.setTotalPrice(totalPrice);
                // 拱量
                QuantitySales quantitySales = jszxOrderService.getQuantitySales(jszxOrder.getProduct(), costId, jszxOrder.getCompanyUnit(), jszxOrder);
                if (quantitySales != null) {
                    Float quantityTotalPrice = jszxOrderService.getQuantityTotalPrice(quantitySales, 1, totalPrice, formatPriceTypeToString(orderDetail.getSeatType()));
                    //设置供应商订单详情拱量金额
                    jszxOrderDetail.setQuantityPrice(quantityTotalPrice);
                    //订单票种实际支付总额, 即拱量后供应商订单详情总价
                    jszxOrderDetail.setActualPay(quantityTotalPrice);
                    // 累加供应商订单拱量后总价, 即供应商订单实际总支付金额
                    // 如果没有手动更改实际支付金额, 那么实际支付总价与拱量总价相等
                    orderTotalPrice += quantityTotalPrice;
                    orderQuantityTotalPrice += quantityTotalPrice;
                } else {
                    // 没有发生拱量, 实际支付总价与旅行帮订单详情总价相当, 即价格没有变化
                    jszxOrderDetail.setActualPay(totalPrice);
                    // 累加拱量总价
                    orderQuantityTotalPrice += totalPrice;
                    orderTotalPrice += totalPrice;
                }
                // 使用日期范围
                jszxOrderDetail.setStartTime(orderDetail.getPlayDate());
                jszxOrderDetail.setEndTime(orderDetail.getPlayDate());
                jszxOrderDetailList.add(jszxOrderDetail);
            }
            // 更新拱量后的总价
            jszxOrder.setActualPayPrice(orderTotalPrice);
            // 更新拱量价, 如果没有手动更改实际支付总价, 那么拱量总价与实际支付总额相等
            jszxOrder.setQuantityTotalPrice(orderQuantityTotalPrice);
            // 保存所有供应商系统订单详情
            jszxOrderDetailService.saveAll(jszxOrderDetailList);
            jszxOrderService.update(jszxOrder, jszxOrder.getUser(), jszxOrder.getCompanyUnit());
            OrderLog orderLog2 = orderLogService.createOrderLog(user, "订单(G)#" + order.getId()
                    + "保存供应商邮轮订单详情信息成功(#" + jszxOrder.getId()
                    + "),订单(Order)现在状态:" + order.getStatus().getDescription(), order.getId(), null);
            orderLogService.loggingOrderLog(orderLog2);
            // ... // ...
            // 邮轮订单均需要确认
            order.setStatus(OrderStatus.PROCESSING);
            orderService.update(order);
        } catch (Exception e) {
            e.printStackTrace();
            OrderLog orderLog3 = orderLogService.createOrderLog(user, "订单(G)#" + order.getId()
                    + "供应商邮轮订单下单异常,订单(Order)现在状态:"
                    + order.getStatus().getDescription(), order.getId(), null);
            orderLogService.loggingOrderLog(orderLog3);
            // 更新订单状态
            order.setStatus(OrderStatus.FAILED);
            orderService.update(order);
        }
        return result;
    }

    public JszxOrderDetailPriceType formatPriceType(String type) {

        if ("adult".equals(type) || "成人票".equals(type)) {
            return JszxOrderDetailPriceType.adult;
        } else if ("student".equals(type) || "学生票".equals(type)) {
            return JszxOrderDetailPriceType.student;
        } else if ("child".equals(type) || "儿童票".equals(type)) {
            return JszxOrderDetailPriceType.child;
        } else if ("oldman".equals(type) || "老人票".equals(type)) {
            return JszxOrderDetailPriceType.oldman;
        } else if ("taopiao".equals(type) || "套票".equals(type)) {
            return JszxOrderDetailPriceType.taopiao;
        } else if ("team".equals(type) || "团体票".equals(type)) {
            return JszxOrderDetailPriceType.team;
        } else {
            return JszxOrderDetailPriceType.other;
        }
    }
    public String formatPriceTypeToString(String type) {

        if ("成人票".equals(type)) {
            return "adult";
        } else if ("学生票".equals(type)) {
            return "student";
        } else if ("儿童票".equals(type)) {
            return "child";
        } else if ("老人票".equals(type)) {
            return "oldman";
        } else if ("套票".equals(type)) {
            return "taopiao";
        } else if ("团体票".equals(type)) {
            return "team";
        } else {
            return "adult";
        }
    }

    /**
     * 线路订单支付超时检查
     * @param waitTime
     * @param nowTime
     * @return
     */
    public boolean payCheckByTime(Date waitTime, Date nowTime) {
        if (waitTime.getTime() > nowTime.getTime()) {
            return true;
        }
        return false;
    }

    public SysUnit getCompanyUnit() {
        String companyIdStr = propertiesManager.getString("OUT_ORDER_COMPANY_ID");
        if (StringUtils.hasText(companyIdStr)) {
            Long companyId = Long.parseLong(companyIdStr);
            SysUnit sysUnit =  sysUnitService.findUnitById(companyId);
            return sysUnit;
        }
        return null;
    }

    public SysUser getAccountUser() {
        String accountUserIdStr = propertiesManager.getString("OUT_ORDER_ACCOUNT_ID");
        if (StringUtils.hasText("accountUserIdStr")) {
            Long accountUserId = Long.parseLong(accountUserIdStr);
            SysUser sysUser = balanceService.findBalanceAccountBy(accountUserId);
            return sysUser;
        }
        return null;
    }
}
