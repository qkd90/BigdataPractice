package com.data.data.hmly.service.apidata;

import com.data.data.hmly.service.SysUserService;
import com.data.data.hmly.service.balance.BalanceService;
import com.data.data.hmly.service.balance.entity.enums.AccountType;
import com.data.data.hmly.service.common.YhyMsgService;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.elong.ElongHotelService;
import com.data.data.hmly.service.elong.pojo.CancelOrderResult;
import com.data.data.hmly.service.elong.service.result.HotelOrderCancelResult;
import com.data.data.hmly.service.entity.FerryMember;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.hotel.HotelPriceService;
import com.data.data.hmly.service.nctripticket.CtripTicketApiService;
import com.data.data.hmly.service.nctripticket.CtripTicketService;
import com.data.data.hmly.service.nctripticket.entity.CtripOrderFormInfo;
import com.data.data.hmly.service.nctripticket.pojo.CtripCancelOrderItemVO;
import com.data.data.hmly.service.nctripticket.pojo.CtripResultStatusVO;
import com.data.data.hmly.service.order.OrderDetailService;
import com.data.data.hmly.service.order.OrderLogService;
import com.data.data.hmly.service.order.OrderService;
import com.data.data.hmly.service.order.dao.FerryOrderDao;
import com.data.data.hmly.service.order.dao.OrderDetailDao;
import com.data.data.hmly.service.order.entity.FerryOrder;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.OrderLog;
import com.data.data.hmly.service.order.entity.enums.OrderDetailStatus;
import com.data.data.hmly.service.order.entity.enums.OrderLogLevel;
import com.data.data.hmly.service.order.entity.enums.OrderPayType;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.order.entity.enums.OrderType;
import com.data.data.hmly.service.order.util.FerryUtil;
import com.data.data.hmly.service.order.util.MsgTemplateKey;
import com.zuipin.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 */
@Service
public class ApiOrderCancelService {

    @Resource
    private OrderService orderService;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private CtripTicketApiService ctripTicketApiService;
    @Resource
    private CtripTicketService ctripTicketService;
    @Resource
    private OrderDetailDao orderDetailDao;
    @Resource
    private BalanceService balanceService;
    @Resource
    private HotelPriceService hotelPriceService;
    @Resource
    private ElongHotelService elongHotelService;
    @Resource
    private OrderLogService orderLogService;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private FerryOrderDao ferryOrderDao;
    @Resource
    private YhyMsgService yhyMsgService;

//    private Log log = LogFactory.getLog(this.getClass());


    /**
     * 携程门票退单（已付款），
     * 提交退单申请，状态先为退订中
     * 后续定时查询退单中订单状态，可退设为已退款，不可退设为已关闭
     * @return
     */
    public Map<String, Object> doCancelForCtrip(Long orderDetailId) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        // 检查订单状态是否为成功的，否则不可处理退单
        OrderDetail orderDetail = orderDetailService.get(orderDetailId);
        if (orderDetail.getStatus() != OrderDetailStatus.SUCCESS) {
            result.put("success", false);
            result.put("errorMsg", "该订单状态下不允许退单处理！");
            return result;
        }

        // 开始接口退单申请
        String realOrderId = orderDetail.getRealOrderId();
        if (StringUtils.isBlank(realOrderId)) {
            result.put("success", false);
            result.put("errorMsg", "接口订单编号为空！");
            return result;
        }
        String reason = "其他";
        String uuid = UUID.randomUUID().toString();
        // 查询订单详情（获取订单状态和订单项信息）
        List<CtripCancelOrderItemVO> cancelItems = new ArrayList<CtripCancelOrderItemVO>(); // 资源退款信息集合
        com.data.data.hmly.service.ctripcommon.enums.OrderStatus ctripOrderStatus = ctripTicketApiService.doGetOrderStatus(Long.valueOf(realOrderId), uuid, cancelItems);
        if (ctripOrderStatus != com.data.data.hmly.service.ctripcommon.enums.OrderStatus.SUCCESS) {
            result.put("success", false);
            result.put("errorMsg", "接口返回订单状态不一致，不能退单！");
            return result;
        }
        CtripResultStatusVO ctripResultStatusVO = ctripTicketApiService.doOrderCancelWithMsg(Long.valueOf(realOrderId), reason, cancelItems, uuid);
        if (ctripResultStatusVO != null && ctripResultStatusVO.getIsSuccess()) {
            // 记录订单日志
            doRecordLog("接口退单申请已提交", orderDetail.getOrder().getId(), orderDetail.getId(), OrderLogLevel.info);
            // 更新接口订单状态为退订中
            ctripTicketService.updateOrderStatus(Long.valueOf(realOrderId), com.data.data.hmly.service.ctripcommon.enums.OrderStatus.CANCELING);
            // 更新本地订单状态
            orderDetail.setStatus(OrderDetailStatus.CANCELING);
            orderDetail.setRefund(orderDetail.getTotalPrice());
            orderDetail.setApiResult("接口退单申请已提交");
            orderDetailService.update(orderDetail);
            // 更新订单状态
            Order order = orderDetail.getOrder();
            orderService.updateOrderStatus(order, OrderStatus.CANCELING, OrderDetailStatus.CANCELING);
        } else {
            // 记录订单日志
            doRecordLog("接口返回不允许退单，接口错误消息：" + ctripResultStatusVO.getCustomerErrorMessage() + "#realOrderId=" + realOrderId,
                    orderDetail.getOrder().getId(), orderDetail.getId(), OrderLogLevel.error);

            result.put("success", false);
            result.put("errorMsg", "接口返回不允许退单，接口错误消息：" + ctripResultStatusVO.getCustomerErrorMessage());
            return result;
        }
        result.put("success", true);
        return result;
    }

    /**
     * 艺龙酒店退单（已付款）
     * @return
     */
    public Map<String, Object> doCancelForElong(Long orderDetailId) {
        Map<String, Object> result = new HashMap<String, Object>();
        // 检查订单状态是否为成功的，否则不可处理退单
        OrderDetail orderDetail = orderDetailService.get(orderDetailId);
        if (orderDetail.getStatus() != OrderDetailStatus.SUCCESS) {
            result.put("success", false);
            result.put("errorMsg", "该订单状态下不允许退单处理！");
            return result;
        }

        // 开始接口退单
        String realOrderId = orderDetail.getRealOrderId();
        if (StringUtils.isBlank(realOrderId)) {
            result.put("success", false);
            result.put("errorMsg", "接口订单编号为空！");
            return result;
        }

        Order order = orderDetail.getOrder();
//        HotelPrice hotelPrice = hotelPriceService.findFullById(orderDetail.getCostId());
        Long elongOrderId = Long.parseLong(realOrderId);
        HotelOrderCancelResult hotelCancelOrderResult = elongHotelService.cancelOrder(elongOrderId, "其他", "其他");
        CancelOrderResult cancelOrderResult = hotelCancelOrderResult.getResult();
        if ("0".equals(hotelCancelOrderResult.getCode()) && cancelOrderResult != null) {
            if (cancelOrderResult.isSuccesss()) {
                // 记录订单日志
                doRecordLog("接口退单成功", orderDetail.getOrder().getId(), orderDetail.getId(), OrderLogLevel.info);

                // 状态为已取消，更新状态
                orderDetail.setStatus(OrderDetailStatus.CLOSED);
                orderDetail.setApiResult("接口退单成功");
                orderDetailService.update(orderDetail);
                // 更新订单状态
                orderService.updateOrderStatus(order, OrderStatus.CLOSED, OrderDetailStatus.CLOSED);
            } else {
                // 记录订单日志
                doRecordLog("接口返回退订失败", orderDetail.getOrder().getId(), orderDetail.getId(), OrderLogLevel.error);

                result.put("success", false);
                result.put("errorMsg", "接口返回退订失败！");
                return result;
            }
        } else if (hotelCancelOrderResult.getCode().contains("H001056")) {
            // H001056 : 订单已经处于取消状态
            // 记录订单日志
            doRecordLog("接口返回订单已退订，已同步更新本地订单状态", orderDetail.getOrder().getId(), orderDetail.getId(), OrderLogLevel.warn);
            // 状态为已取消，更新状态
            orderDetail.setStatus(OrderDetailStatus.CLOSED);
            orderDetail.setApiResult("更新订单状态成功");
            orderDetailService.update(orderDetail);
            // 更新订单状态
            orderService.updateOrderStatus(order, OrderStatus.CLOSED, OrderDetailStatus.CLOSED);
            result.put("success", false);
            result.put("errorMsg", "接口返回订单已退订，已同步更新本地订单状态！");
            return result;
        } else {    // 其他退订失败情况
            // 记录订单日志
            doRecordLog("接口返回结果信息不正确", orderDetail.getOrder().getId(), orderDetail.getId(), OrderLogLevel.error);

            result.put("success", false);
            result.put("errorMsg", "接口返回结果信息不正确！");
            return result;
        }

        result.put("success", true);
        return result;
    }

    /**
     * 携程门票退单状态同步，
     * 申请“门票退单”定时检查携程退单状态，成功则更新本地订单状态
     */
    public void doSyncCtripOrderCancel(CtripOrderFormInfo ctripOrderFormInfo, com.data.data.hmly.service.ctripcommon.enums.OrderStatus orderStatus) throws Exception {
        // 如果是取消中，设置处理时间，防止本次定时再次扫描
        if (orderStatus == com.data.data.hmly.service.ctripcommon.enums.OrderStatus.CANCELING) {
            ctripTicketService.updateOrderStatus(ctripOrderFormInfo.getCtripOrderId(), orderStatus, new Date());
            return;
        }
        // 如果不为已取消，回退订单状态（暂时没有退单失败状态）
        OrderDetail orderDetail = orderDetailDao.findUniqueBy(String.valueOf(ctripOrderFormInfo.getCtripOrderId()));
        Order order = orderDetail.getOrder();
        if (orderStatus != com.data.data.hmly.service.ctripcommon.enums.OrderStatus.CANCELED) {
            // 记录订单日志
            doRecordLog("接口退单失败，接口状态为：" + orderStatus.getDescription(), orderDetail.getOrder().getId(), orderDetail.getId(), OrderLogLevel.error);

            ctripTicketService.updateOrderStatus(ctripOrderFormInfo.getCtripOrderId(), com.data.data.hmly.service.ctripcommon.enums.OrderStatus.SUCCESS, new Date());
            orderDetail.setStatus(OrderDetailStatus.SUCCESS);
            orderDetail.setRefund(null);
            orderDetail.setApiResult("接口退单失败，接口状态为：" + orderStatus.getDescription());
            orderDetailService.update(orderDetail);
            // 更新订单状态
            orderService.updateOrderStatus(order, OrderStatus.SUCCESS, OrderDetailStatus.SUCCESS);
            return;
        }
        // 记录订单日志
        doRecordLog("接口退单成功", orderDetail.getOrder().getId(), orderDetail.getId(), OrderLogLevel.info);
        // 状态为已退款，更新状态，并退款至用户账户
        ctripTicketService.updateOrderStatus(ctripOrderFormInfo.getCtripOrderId(), com.data.data.hmly.service.ctripcommon.enums.OrderStatus.CANCELED, new Date());
        orderDetail.setStatus(OrderDetailStatus.REFUNDED);
        orderDetail.setApiResult("接口退单成功");
        orderDetail.setRefundDate(new Date());
        orderDetailService.update(orderDetail);
        // 更新订单状态
        orderService.updateOrderStatus(order, OrderStatus.REFUND, OrderDetailStatus.REFUNDED);

        // 判断支付方式
        String runningNo = order.getOrderNo() + "-" + orderDetail.getId();
        Float amount = orderDetail.getTotalPrice();
        if (order.getPayType() == OrderPayType.ONLINE) {    // 余额支付
            balanceService.updateBalanceMember(amount.doubleValue(), AccountType.refund, order.getUser().getId(), null, runningNo, orderDetail.getId(), order.getOrderType(), orderDetail.getProductType());
        } else { // 非余额支付，原来退回
            balanceService.doThirdPayRefund(order.getPayType(), order.getOrderNo(), runningNo, order.getPrice(), amount);
        }
    }

    /**
     * 记录订单日志
     * @param content
     * @param orderId
     * @param orderDetailId
     */
    public void doRecordLog(String content, Long orderId, Long orderDetailId, OrderLogLevel orderLogLevel) {
        SysUser admin = sysUserService.findUserByAccount("admin");  // 默认为admin操作
        OrderLog orderLog = orderLogService.createOrderLog(admin, content, orderId, orderDetailId, orderLogLevel);
        orderLogService.loggingOrderLog(orderLog);
    }

    public void doSyncFerryOrderCancel(FerryOrder ferryOrder) throws Exception {
        Map<String, Object> result = FerryUtil.getSaleOrder(null, null, ferryOrder.getFerryNumber());
        ferryOrder.setCancelHandleTime(new Date());
        ferryOrderDao.update(ferryOrder);
        if (!(Boolean) result.get("success")) {
            doRecordLog("船票接口订单查询失败", ferryOrder.getId(), null, OrderLogLevel.error);
            return;
        }
        List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("list");
        if (list.isEmpty()) {
            doRecordLog("船票接口订单查询失败", ferryOrder.getId(), null, OrderLogLevel.error);
            return;
        }
        Map<String, Object> ferry = list.get(0);
        if ("已退票".equals(ferry.get("stateInfo"))) {
            ferryOrder.setRefundDate(new Date());
            ferryOrder.setStatus(OrderStatus.REFUND);
            ferryOrder.setModifyTime(new Date());
            ferryOrderDao.update(ferryOrder);
            doRecordLog("船票已退票", ferryOrder.getId(), null, OrderLogLevel.info);


            // 如果是行程规划，查询是否有船票订单
            com.data.data.hmly.service.order.entity.Order order = null;
            if (ferryOrder.getOrderId() != null) {
                order = orderService.get(ferryOrder.getOrderId());
                orderService.updateOrderStatus(order, OrderStatus.REFUND, OrderDetailStatus.REFUNDED);
            }

            String runningNo = ferryOrder.getOrderNumber() + "-" + ferryOrder.getId();
            OrderType orderType = OrderType.ferry;
            Float totalPrice = ferryOrder.getAmount();
            String orderNo = ferryOrder.getOrderNumber();
            OrderPayType orderPayType = ferryOrder.getPayType();
            if (ferryOrder.getOrderId() != null) {  // 行程订单，取总订单信息
                orderType = OrderType.plan;
                totalPrice = order.getPrice();
                orderPayType = order.getPayType();
            }
            // 判断支付方式
            Float amount = ferryOrder.getReturnAmount();
            if (orderPayType == OrderPayType.ONLINE) {    // 余额支付
                balanceService.updateBalanceMember(amount.doubleValue(), AccountType.refund, ferryOrder.getUser().getId(), null, runningNo, ferryOrder.getId(), orderType, ProductType.ferry);
            } else { // 非余额支付，原来退回
                balanceService.doThirdPayRefund(orderPayType, orderNo, runningNo, totalPrice, amount);
            }
            // 发送短信
            Map<String, Object> msgData = new HashMap<String, Object>();
            msgData.put("orderNo", ferryOrder.getOrderNumber());
            msgData.put("amount", amount);
            FerryMember ferryMember = ferryOrder.getUser().getFerryMember();
            // @WEB_SMS
            yhyMsgService.doSendSMS(msgData, ferryMember.getMobile(), MsgTemplateKey.USER_REFUND_SUCCESS_TLE);
        }
    }

}
