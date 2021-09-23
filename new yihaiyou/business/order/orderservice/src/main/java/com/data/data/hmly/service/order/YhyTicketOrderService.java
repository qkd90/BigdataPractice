package com.data.data.hmly.service.order;

import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.common.entity.enums.ProductSource;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.entity.User;
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
import com.data.data.hmly.service.order.entity.enums.OrderLogLevel;
import com.data.data.hmly.service.order.vo.OrderResult;
import com.data.data.hmly.service.ticket.TicketDatepriceService;
import com.data.data.hmly.service.ticket.TicketPriceService;
import com.data.data.hmly.service.ticket.entity.TicketDateprice;
import com.data.data.hmly.service.ticket.entity.TicketPrice;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by zzl on 2016/10/18.
 */
@Service
public class YhyTicketOrderService {

    Logger logger = Logger.getLogger(YhyTicketOrderService.class);

    @Resource
    private OrderLogService orderLogService;
    @Resource
    private OrderService orderService;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private OrderMsgService orderMsgService;

    @Resource
    private TicketPriceService ticketPriceService;
    @Resource
    private TicketDatepriceService ticketDatepriceService;
    @Resource
    private CtripTicketService ctripTicketService;
    @Resource
    private CtripTicketApiService ctripTicketApiService;

    public List<OrderResult> doTakeTicketOrder(Long orderId) {
        // 针对每个orderDetail的下单结果集合
        List<OrderResult> resultList = new ArrayList<OrderResult>();
        Order order = orderService.get(orderId);
        List<OrderDetail> orderDetailList = orderDetailService.getByOrderIdAndProductType(orderId, ProductType.scenic);
        for (OrderDetail orderDetail : orderDetailList) {
            OrderResult result = this.doTakeTicketOrderDetail(order, orderDetail);
            resultList.add(result);
        }
        return resultList;
    }

    public OrderResult doTakeTicketOrderDetail(Order order, OrderDetail orderDetail) {
        User orderLogUser = orderLogService.getSysOrderLogUser();
        OrderResult result = new OrderResult();
        Product product = orderDetail.getProduct();
        Long orderId = order.getId();
        if (product == null) {
            result.setSuccess(false);
            result.setMsg("下单失败!产品信息错误");
            result.setErrorCode("product_null");
            result.setRealOrderId("FAILED");
            result.setOrderDetailId(orderDetail.getId());
            result.setApiResult("门票下单失败! 对应的产品不存在!");
            result.setOrderDetailStatus(OrderDetailStatus.FAILED);
//            resultList.add(result);
            // 写入订单日志
            OrderLog log1 = orderLogService.createOrderLog(orderLogUser, "订单详情#" + orderDetail.getId()
                    + "下单失败! 对应的产品信息不存在!", orderId, orderDetail.getId(), OrderLogLevel.warn);
            orderLogService.loggingOrderLog(log1);
//                return result;
        }
        // 携程门票
        if (ProductSource.CTRIP.equals(product.getSource())) {
            // 向携程接口下单
            result = this.doTakeToCtrip(order, orderDetail, result);
//            resultList.add(result);
//                return result;
        } else if (product.getSource() == null || ProductSource.LXB.equals(product.getSource())) {
            // 供应商门票
            result.setSuccess(true);
            result.setMsg("下单成功");
            result.setErrorCode("0");
            result.setRealOrderId(Long.toString(orderDetail.getId()));
            result.setOrderDetailId(orderDetail.getId());
            result.setApiResult("门票(供应商)下单成功!");
            result.setOrderDetailStatus(OrderDetailStatus.SUCCESS);
//            resultList.add(result);
            // 写入订单日志
            OrderLog log1 = orderLogService.createOrderLog(orderLogUser, "订单详情(G)#"
                    + orderDetail.getId() + "下单成功!", orderId, orderDetail.getId(), OrderLogLevel.warn);
            orderLogService.loggingOrderLog(log1);
//                return result;
        }
        return result;
    }

    /**
     * 携程门票下单方法
     * @param order
     * @param orderDetail
     * @param result
     * @return
     */
    private OrderResult doTakeToCtrip(Order order, OrderDetail orderDetail, OrderResult result) {
        User user = orderLogService.getSysOrderLogUser();
        orderDetail = orderDetailService.get(orderDetail.getId());
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
        OrderLog orderLog1 = orderLogService.createOrderLog(user, "订单详情(T)#"
                + orderDetail.getId() + "保存接口订单信息成功(#"
                + ctripOrderFormInfo.getId() + "),现在状态:"
                + orderDetail.getStatus().getDescription(), order.getId(), orderDetail.getId());
        orderLogService.loggingOrderLog(orderLog1);
        String orderId = "FAILED" + "-" + ctripOrderFormInfo.getId();
        try {
            String uuid = UUID.randomUUID().toString();
            ctripTicketApiService.doCreateOrder(ctripOrderFormInfo, contactInfo, passengerInfoList, uuid);
            OrderLog orderLog2 = orderLogService.createOrderLog(user, "订单详情(T)#" + orderDetail.getId()
                    + "已经向接口下单(#" + ctripOrderFormInfo.getCtripOrderId() + "),现在状态:"
                    + orderDetail.getStatus().getDescription(), order.getId(), orderDetail.getId());
            orderLogService.loggingOrderLog(orderLog2);
            orderId = Long.toString(ctripOrderFormInfo.getCtripOrderId());
            // 再次检查订单状态
            if (com.data.data.hmly.service.ctripcommon.enums.OrderStatus.SUCCESS.equals(ctripOrderFormInfo.getOrderStatus())) {
                // 更改初始订单状态, 更新实际订单id
                result.setSuccess(true);
                result.setMsg("下单成功");
                result.setErrorCode("0");
                result.setRealOrderId(orderId);
                result.setOrderDetailId(orderDetail.getId());
                result.setApiResult("门票(携程接口)下单成功!");
                result.setOrderDetailStatus(OrderDetailStatus.SUCCESS);
                OrderLog orderLog3 = orderLogService.createOrderLog(user, "订单详情(T)#"
                        + orderDetail.getId() + "接口下单成功(#" + ctripOrderFormInfo.getCtripOrderId()
                        + ")", order.getId(), orderDetail.getId());
                orderLogService.loggingOrderLog(orderLog3);
                // @SMS 发送门票预订成功短信
//                orderMsgService.doSendTicketBookingSuccessMsg(orderDetail, ticketPrice);
                return result;
//                updateOrderStatus(orderDetail, orderId, OrderDetailStatus.SUCCESS, ProductType.scenic);
            }
            // 更新初始订单状态
            result.setSuccess(false);
            result.setMsg("下单失败");
            result.setErrorCode("orderDetail_failed");
            result.setRealOrderId(orderId);
            result.setOrderDetailId(orderDetail.getId());
            result.setApiResult("下单失败, 检查携程接口(门票)");
            result.setOrderDetailStatus(OrderDetailStatus.FAILED);
            OrderLog orderLog3 = orderLogService.createOrderLog(user, "订单详情(T)#"
                    + orderDetail.getId() + "接口下单异常(#" + ctripOrderFormInfo.getCtripOrderId()
                    + ")检查接口", order.getId(), orderDetail.getId());
            orderLogService.loggingOrderLog(orderLog3);
            // @SMS 发送门票预订失败短信
//            orderMsgService.doSendTicketBookingFailMsg(orderDetail, ticketPrice);
            return result;
//            updateOrderStatus(orderDetail, orderId, OrderDetailStatus.FAILED, ProductType.scenic);
        } catch (Exception e) {
            // 下单失败
            logger.error("#订单详情OrderDetail#" + orderDetail.getId() + "@" + "门票#" + orderId + "下单异常", e);
            // 回写失败状态
            ctripTicketApiService.updateOrderFail(ctripOrderFormInfo);
            result.setSuccess(false);
            result.setMsg("下单失败");
            result.setErrorCode("orderDetail_failed");
            result.setRealOrderId(Long.toString(ctripOrderFormInfo.getId()));
            result.setOrderDetailId(orderDetail.getId());
            result.setApiResult("下单异常, 检查携程接口(门票)");
            result.setOrderDetailStatus(OrderDetailStatus.FAILED);
            OrderLog orderLog4 = orderLogService.createOrderLog(user, "订单详情(T)#" + orderDetail.getId() + "接口下单异常(#" + ctripOrderFormInfo.getCtripOrderId() + ")检查接口", order.getId(), orderDetail.getId());
            orderLogService.loggingOrderLog(orderLog4);
            return result;
        }
    }

}
