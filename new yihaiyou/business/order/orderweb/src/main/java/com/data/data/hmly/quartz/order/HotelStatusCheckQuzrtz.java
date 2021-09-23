package com.data.data.hmly.quartz.order;

import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.elong.ElongHotelService;
import com.data.data.hmly.service.elong.pojo.CreditCardWithStatus;
import com.data.data.hmly.service.elong.pojo.EnumCreditCardStatus;
import com.data.data.hmly.service.elong.pojo.OrderDetailResult;
import com.data.data.hmly.service.elong.service.result.HotelOrderDetailResult;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.hotel.HotelPriceService;
import com.data.data.hmly.service.hotel.entity.HotelPrice;
import com.data.data.hmly.service.hotel.entity.enums.PriceStatus;
import com.data.data.hmly.service.order.OrderDetailService;
import com.data.data.hmly.service.order.OrderDispatchService;
import com.data.data.hmly.service.order.OrderLogService;
import com.data.data.hmly.service.order.OrderMsgService;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.OrderLog;
import com.data.data.hmly.service.order.entity.enums.OrderDetailStatus;
import com.data.data.hmly.service.order.entity.enums.OrderLogLevel;
import com.framework.hibernate.util.Page;
import com.zuipin.util.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2016/5/3.
 */
@Component
public class HotelStatusCheckQuzrtz {

    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private OrderMsgService orderMsgService;
    @Resource
    private OrderDispatchService orderDispatchService;
    @Resource
    private ElongHotelService elongHotelService;
    @Resource
    private HotelPriceService hotelPriceService;

    @Resource
    private OrderLogService orderLogService;

    public void doCheckHotelOrderStatus() {
        User user = orderLogService.getSysOrderLogUser();
        List<OrderDetail> orderDetailList;
        Integer pageIndex = 1;
        Integer pageSize = 1000;
        Integer processed = 0;
        Integer total = 0;
        Page page;
        OrderDetail condition = new OrderDetail();
        List<OrderDetailStatus> neededStatus = new ArrayList<OrderDetailStatus>();
        // 取出订单提交成功(PAYED)或预订成功(SUCCESS)的酒店订单
        neededStatus.add(OrderDetailStatus.PAYED);
        neededStatus.add(OrderDetailStatus.SUCCESS);
        condition.setNeededStatuses(neededStatus);
        condition.setProductType(ProductType.hotel);
        while (true) {
            page = new Page(pageIndex, pageSize);
            orderDetailList = orderDetailService.list(condition);
            if (orderDetailList.isEmpty()) {
                break;
            }
            // 总待处理订单数目
            total = page.getTotalCount();
            Iterator<OrderDetail> iterator = orderDetailList.iterator();
            while (iterator.hasNext()) {
                OrderDetail orderDetail = iterator.next();
                Long costId = orderDetail.getCostId();
                if (costId == null || costId <= 0) {
                    OrderLog orderHotelLog = orderLogService.createOrderLog(user, "订单详情#"
                                    + orderDetail.getId() + "##没有消费ID(costId),本次不处理!",
                            orderDetail.getOrder().getId(), orderDetail.getId());
                    orderLogService.loggingOrderLog(orderHotelLog);
                    continue;
                }
                HotelPrice hotelPrice = hotelPriceService.findFullById(costId);
                if (hotelPrice == null) {
                    OrderLog orderHotelLog = orderLogService.createOrderLog(user, "订单详情#"
                                    + orderDetail.getId() + "##没有找酒店价格(hotelPrice),本次不处理",
                            orderDetail.getOrder().getId(), orderDetail.getId());
                    orderLogService.loggingOrderLog(orderHotelLog);
                    continue;
                }
                String realOrderId = orderDetail.getRealOrderId();
                if (StringUtils.hasText(realOrderId) && !"FAILED".equals(realOrderId)) {
                    // 获取艺龙酒店订单详情
                    Long elongOrderId = Long.parseLong(realOrderId);
                    HotelOrderDetailResult hotelOrderDetailResult = elongHotelService.getOrderDetail(elongOrderId);
                    if (hotelOrderDetailResult != null && "0".equals(hotelOrderDetailResult.getCode())) {
                        OrderDetailResult orderDetailResult = hotelOrderDetailResult.getResult();
                        if (orderDetailResult != null) {
                            // 处理艺龙酒店订单状态
                            Map<String, Object> result = doProcessHotelOrderStatus(user, orderDetail, hotelPrice, orderDetailResult);
                            // 更新订单详情状态等信息
                            orderDispatchService.updateOrderStatus(orderDetail, result);
                        } else {
                            OrderLog orderHotelLog = orderLogService.createOrderLog(user, "订单详情#"
                                            + orderDetail.getId() + "##艺龙酒店订单没有找到详情,本次不处理!",
                                    orderDetail.getOrder().getId(), orderDetail.getId());
                            orderLogService.loggingOrderLog(orderHotelLog);
                            continue;
                        }
                    } else {
                        if (hotelOrderDetailResult == null) {
                            OrderLog orderHotelLog = orderLogService.createOrderLog(user, "订单详情#" + orderDetail.getId()
                                            + "##没有艺龙酒店订单详情(hotelOrderDetailResult)本次不处理!",
                                    orderDetail.getOrder().getId(), orderDetail.getId());
                            orderLogService.loggingOrderLog(orderHotelLog);
                        } else {
                            OrderLog orderHotelLog = orderLogService.createOrderLog(user, "订单详情#"
                                            + orderDetail.getId() + "##" + hotelOrderDetailResult.getCode() + "##本次不处理!",
                                    orderDetail.getOrder().getId(), orderDetail.getId());
                            orderLogService.loggingOrderLog(orderHotelLog);
                        }
                        continue;
                    }
                } else {
                    OrderLog orderHotelLog = orderLogService.createOrderLog(user, "订单详情#"
                            + orderDetail.getId() + "##酒店订单没有接口订单ID!本次不处理",
                            orderDetail.getOrder().getId(), orderDetail.getId());
                    orderLogService.loggingOrderLog(orderHotelLog);
                    continue;
                }
            }
            // 本次已处理总订单详情数目
            processed += orderDetailList.size();
            if (processed >= total) {
                break;
            }
            pageIndex += 1;
            orderDetailList.clear();
        }
    }

    /**
     * 处理艺龙酒店订单状态
     * @param user
     * @param orderDetail
     * @param hotelPrice
     * @param orderDetailResult
     * @return
     */
    private Map<String, Object> doProcessHotelOrderStatus(User user, OrderDetail orderDetail, HotelPrice hotelPrice, OrderDetailResult orderDetailResult) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("type", ProductType.hotel);
        result.put("orderId", orderDetail.getRealOrderId());
        String hotelOrderStatus = orderDetailResult.getStatus();
        OrderLog orderHotelLog = orderLogService.createOrderLog(user, "订单详情#"
                        + orderDetail.getId() + "##获取艺龙酒店状态成功!"
                        + "#艺龙酒店订单状态: " + hotelOrderStatus,
                orderDetail.getOrder().getId(), orderDetail.getId(), OrderLogLevel.debug);
        orderLogService.loggingOrderLog(orderHotelLog);
        if (PriceStatus.GUARANTEE.equals(hotelPrice.getStatus())) {
            CreditCardWithStatus creditCard = orderDetailResult.getCreditCard();
            // 担保酒店处理信用卡担保状态
            if (creditCard != null) {
                result = doProcessCreditCardStatus(user, creditCard, orderDetail, hotelPrice);
                // 信用卡担保失败时候, 立即返回失败订单!
                if (OrderDetailStatus.FAILED.equals(OrderDetailStatus.valueOf(result.get("status").toString()))) {
                    return result;
                }
            } else {
                OrderLog orderHotelLog1 = orderLogService.createOrderLog(user, "订单详情#"
                                + orderDetail.getId() + "##担保酒店没有找到信用卡信息",
                        orderDetail.getOrder().getId(), orderDetail.getId());
                orderLogService.loggingOrderLog(orderHotelLog1);
                result.put("apiResult", "担保酒店没有找到信用卡信息##已获取艺龙酒店订单状态:" + hotelOrderStatus);
                result.put("status", OrderDetailStatus.CANCELED);
            }
        }
        if ("A".equals(hotelOrderStatus)) {
            OrderLog orderHotelLog2 = orderLogService.createOrderLog(user, "订单详情#"
                            + orderDetail.getId() + "##酒店预订成功"
                            + "#艺龙酒店订单状态: " + hotelOrderStatus + "(已确认)",
                    orderDetail.getOrder().getId(), orderDetail.getId(), OrderLogLevel.debug);
            orderLogService.loggingOrderLog(orderHotelLog2);
            result.put("apiResult", "酒店预订成功!艺龙酒店订单状态:" + hotelOrderStatus );
            result.put("status", OrderDetailStatus.SUCCESS);
            // 检查订单详情状态, 避免多次发送短信
            if (!OrderDetailStatus.SUCCESS.equals(orderDetail.getStatus())) {
                // @SMS 发送酒店预订成功短信
                orderMsgService.doSendHotelBookingSuccessMsg(orderDetail, hotelPrice);
            }
        } else if ("D".equals(hotelOrderStatus)) {
            // 订单被删除时候, 检查信用卡担保状态,  确认是否因为信用卡担保问题
            CreditCardWithStatus creditCard = orderDetailResult.getCreditCard();
            // 额外处理信用卡担保结果, 再处理艺龙酒店订单状态
            if (creditCard != null) {
                // 担保酒店处理信用卡担保结果(方法中处理艺龙酒店订单状态)
                result = doProcessCreditCardStatus(user, creditCard, orderDetail, hotelPrice);
                if (OrderDetailStatus.FAILED.equals(OrderDetailStatus.valueOf(result.get("status").toString()))) {
                    return result;
                }
            } else {
                result.put("apiResult", "已获取艺龙酒店订单状态:" + hotelOrderStatus + "(已删除, 艺龙删除订单!)");
                result.put("status", OrderDetailStatus.CANCELED);
            }
        } else if ("B".equals(hotelOrderStatus)) {
            result.put("apiResult", "已获取艺龙酒店订单状态:" + hotelOrderStatus + "(NO SHOW)");
            result.put("status", orderDetail.getStatus());
        } else if ("B1".equals(hotelOrderStatus)) {
            result.put("apiResult", "已获取艺龙酒店订单状态:" + hotelOrderStatus + "(有预订未查到)");
            result.put("status", orderDetail.getStatus());
        } else if ("B2".equals(hotelOrderStatus)) {
            result.put("apiResult", "已获取艺龙酒店订单状态:" + hotelOrderStatus + "(待查)");
            result.put("status", orderDetail.getStatus());
        } else if ("B3".equals(hotelOrderStatus)) {
            result.put("apiResult", "已获取艺龙酒店订单状态:" + hotelOrderStatus + "(暂不确定)");
            result.put("status", orderDetail.getStatus());
        } else if ("C".equals(hotelOrderStatus)) {
            result.put("apiResult", "已获取艺龙酒店订单状态:" + hotelOrderStatus + "(已结账)");
            result.put("status", orderDetail.getStatus());
        } else if ("E".equals(hotelOrderStatus)) {
            result.put("apiResult", "已获取艺龙酒店订单状态:" + hotelOrderStatus + "(取消)");
            result.put("status", OrderDetailStatus.CANCELED);
            // 检查订单详情状态, 避免多次发送短信
            if (!OrderDetailStatus.CANCELED.equals(orderDetail.getStatus())) {
                // @SMS 发送酒店预订失败短信
                orderMsgService.doSendHotelBookingFailMsg(orderDetail, hotelPrice);
            }
        } else if ("F".equals(hotelOrderStatus)) {
            result.put("apiResult", "已获取艺龙酒店订单状态:" + hotelOrderStatus + "(已入住)");
            result.put("status", orderDetail.getStatus());
        } else if ("G".equals(hotelOrderStatus)) {
            result.put("apiResult", "已获取艺龙酒店订单状态:" + hotelOrderStatus + "(变价)");
            result.put("status", orderDetail.getStatus());
        } else if ("H".equals(hotelOrderStatus)) {
            result.put("apiResult", "已获取艺龙酒店订单状态:" + hotelOrderStatus + "(变价)");
            result.put("status", orderDetail.getStatus());
        } else if ("N".equals(hotelOrderStatus)) {
            result.put("apiResult", "已获取艺龙酒店订单状态:" + hotelOrderStatus + "(新单)");
            result.put("status", orderDetail.getStatus());
        } else if ("O".equals(hotelOrderStatus)) {
            result.put("apiResult", "已获取艺龙酒店订单状态:" + hotelOrderStatus + "(满房)");
            result.put("status", OrderDetailStatus.CANCELED);
            // 检查订单详情状态, 避免多次发送短信
            if (!OrderDetailStatus.CANCELED.equals(orderDetail.getStatus())) {
                // @SMS 发送酒店预订失败短信
                orderMsgService.doSendHotelBookingFailMsg(orderDetail, hotelPrice);
            }
        } else if ("S".equals(hotelOrderStatus)) {
            result.put("apiResult", "已获取艺龙酒店订单状态:" + hotelOrderStatus + "(特殊)");
            result.put("status", orderDetail.getStatus());
        } else if ("U".equals(hotelOrderStatus)) {
            result.put("apiResult", "已获取艺龙酒店订单状态:" + hotelOrderStatus + "(特殊满房)");
            result.put("status", OrderDetailStatus.CANCELED);
            // 检查订单详情状态, 避免多次发送短信
            if (!OrderDetailStatus.CANCELED.equals(orderDetail.getStatus())) {
                // @SMS 发送酒店预订失败短信
                orderMsgService.doSendHotelBookingFailMsg(orderDetail, hotelPrice);
            }
        } else if ("V".equals(hotelOrderStatus)) {
            result.put("apiResult", "已获取艺龙酒店订单状态:" + hotelOrderStatus + "(已审)");
            result.put("status", orderDetail.getStatus());
        } else if ("Z".equals(hotelOrderStatus)) {
            result.put("apiResult", "已获取艺龙酒店订单状态:" + hotelOrderStatus + "(删除)");
            result.put("status", OrderDetailStatus.FAILED);
            // 检查订单详情状态, 避免多次发送短信
            if (!OrderDetailStatus.FAILED.equals(orderDetail.getStatus())) {
                // @SMS 发送酒店预订失败短信
                orderMsgService.doSendHotelBookingFailMsg(orderDetail, hotelPrice);
            }
        }
        return result;
    }

    /**
     * 处理艺龙酒店订单担保状态
     * @param user
     * @param creditCard
     * @param orderDetail
     * @param hotelPrice
     * @return
     */
    private Map<String, Object> doProcessCreditCardStatus(User user, CreditCardWithStatus creditCard, OrderDetail orderDetail, HotelPrice hotelPrice) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("type", ProductType.hotel);
        result.put("orderId", orderDetail.getRealOrderId());
        if (EnumCreditCardStatus.Fail.equals(creditCard.getStatus())) {
            // 检查订单详情状态, 避免多次发送短信
            if (!OrderDetailStatus.FAILED.equals(orderDetail.getStatus())) {
                // @SMS 发送酒店预订失败短信
                orderMsgService.doSendHotelBookingFailMsg(orderDetail, hotelPrice);
            }
            OrderLog orderHotelLog = orderLogService.createOrderLog(user, "订单详情#"
                            + orderDetail.getId() + "##信用卡担保失败,取消授权"
                            + "#现在交易类型: " + creditCard.getProcessType().value()
                            + "#现在交易状态: " + creditCard.getStatus().value(),
                    orderDetail.getOrder().getId(), orderDetail.getId(), OrderLogLevel.error);
            orderLogService.loggingOrderLog(orderHotelLog);
            result.put("apiResult", "信用卡担保失败,取消授权");
            result.put("status", OrderDetailStatus.FAILED);
        } else if (EnumCreditCardStatus.Succeed.equals(creditCard.getStatus())) {
            OrderLog orderHotelLog = orderLogService.createOrderLog(user, "订单详情#"
                            + orderDetail.getId() + "##信用卡担保成功"
                            + "#现在交易类型: " + creditCard.getProcessType().value()
                            + "#现在交易状态: " + creditCard.getStatus().value(),
                    orderDetail.getOrder().getId(), orderDetail.getId(), OrderLogLevel.debug);
            orderLogService.loggingOrderLog(orderHotelLog);
            result.put("apiResult", "信用卡担保已授权");
            result.put("status", orderDetail.getStatus());
        } else if (EnumCreditCardStatus.Processing.equals(creditCard.getStatus())) {
            OrderLog orderHotelLog = orderLogService.createOrderLog(user, "订单详情#"
                            + orderDetail.getId() + "##信用卡担保处理中"
                            + "#现在交易类型: " + creditCard.getProcessType().value()
                            + "#现在交易状态: " + creditCard.getStatus().value(),
                    orderDetail.getOrder().getId(), orderDetail.getId());
            orderLogService.loggingOrderLog(orderHotelLog);
            result.put("apiResult", "信用卡担保处理中");
            result.put("status", orderDetail.getStatus());
        } else if (EnumCreditCardStatus.UnProcess.equals(creditCard.getStatus())) {
            OrderLog orderHotelLog = orderLogService.createOrderLog(user, "订单详情#"
                            + orderDetail.getId() + "##信用卡担保未处理"
                            + "#现在交易类型: " + creditCard.getProcessType().value()
                            + "#现在交易状态: " + creditCard.getStatus().value(),
                    orderDetail.getOrder().getId(), orderDetail.getId());
            orderLogService.loggingOrderLog(orderHotelLog);
            result.put("apiResult", "信用卡担保未处理");
            result.put("status", orderDetail.getStatus());
        }
        return result;
    }
}
