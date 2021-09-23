package com.data.data.hmly.service.order;

import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.OrderLog;
import com.data.data.hmly.service.order.entity.enums.OrderDetailStatus;
import com.data.data.hmly.service.order.entity.enums.OrderLogLevel;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.order.entity.enums.OrderType;
import com.data.data.hmly.service.order.vo.OrderResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzl on 2016/10/18.
 */
@Service
public class YhyOrderService {

    @Resource
    private OrderLogService orderLogService;
    @Resource
    private OrderService orderService;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private YhyTicketOrderService yhyTicketOrderService;
    @Resource
    private YhyCruiseShipOrderService yhyCruiseShipOrderService;
    @Resource
    private YhyHotelOrderService yhyHotelOrderService;
    @Resource
    private YhyPlanOrderService yhyPlanOrderService;


    /**
     * 前端订单下单流程
     * @param orderId
     * @return
     */
    public OrderResult doTakeOrder(Long orderId) {
        OrderResult result = new OrderResult();
        User orderLogUser = orderLogService.getSysOrderLogUser();
        List<OrderResult> resultList = new ArrayList<OrderResult>();
        Order order = orderService.get(orderId);
        OrderType orderType = order.getOrderType();
        if (orderType == null) {
            result.setSuccess(false);
            result.setMsg("订单类型错误(0)");
            result.setErrorCode("orderType_null");
            return result;
        }
        switch (orderType) {
            case ticket:
                resultList = yhyTicketOrderService.doTakeTicketOrder(orderId);
                result = this.doProcessResult(result, resultList, order);
                break;
            case sailboat:
                resultList = yhyTicketOrderService.doTakeTicketOrder(orderId);
                result = this.doProcessResult(result, resultList, order);
                break;
            case yacht:
                resultList = yhyTicketOrderService.doTakeTicketOrder(orderId);
                result = this.doProcessResult(result, resultList, order);
                break;
            case huanguyou:
                resultList = yhyTicketOrderService.doTakeTicketOrder(orderId);
                result = this.doProcessResult(result, resultList, order);
                break;
            case cruiseship:
                resultList = yhyCruiseShipOrderService.doTakeCruiseShipOrder(orderId);
                result = this.doProcessResult(result, resultList, order);
                break;
            case hotel:
                resultList = yhyHotelOrderService.doTakeHotelOrder(orderId);
                result = this.doProcessResult(result, resultList, order);
                break;
            case plan:
                resultList = yhyPlanOrderService.doTakePlanOrder(orderId);
                result = this.doProcessResult(result, resultList, order);
                break;
            default:
                result.setSuccess(false);
                result.setMsg("无效的订单类型!");
                result.setOrderStatus(OrderStatus.CLOSED);
                OrderLog log1 = orderLogService.createOrderLog(orderLogUser, "订单#" + order.getId()
                        + "下单失败! 无效的订单类型!!", orderId, null, OrderLogLevel.warn);
                orderLogService.loggingOrderLog(log1);
                // 更新订单信息
                orderService.updateByResult(result);
                break;
        }
        return result;
    }


    /**
     * 处理下单结果
     * @param result
     * @param resultList
     * @param order
     * @return
     */
    private OrderResult doProcessResult(OrderResult result, List<OrderResult> resultList, Order order) {
        Integer orderDetailNum = resultList.size();
        Integer successNum = 0;
        Integer payedNum = 0;
        result.setOrderId(order.getId());
        for (OrderResult detailResult : resultList) {
            boolean success = detailResult.getSuccess();
            if (success) {
                successNum++;
            }
            if (OrderDetailStatus.PAYED.equals(detailResult.getOrderDetailStatus())) {
                payedNum++;
            }
            OrderDetail orderDetail = orderDetailService.get(OrderDetail.class, detailResult.getOrderDetailId());
            // 更新每个orderDetail下单结果
            orderDetailService.updateByResult(orderDetail, detailResult);
        }
        if (successNum == orderDetailNum) {
            // 全部成功
            result.setSuccess(true);
            result.setMsg("下单成功!");
            result.setOrderStatus(OrderStatus.SUCCESS);
        } else if (successNum == 0) {
            // 全部失败
            result.setSuccess(false);
            result.setMsg("下单失败!");
            result.setOrderStatus(OrderStatus.FAILED);
        } else if (successNum > 0 && successNum < orderDetailNum) {
            // 部分失败
            result.setSuccess(false);
            result.setMsg("部分下单失败! 请至订单详情查看!!");
            result.setOrderStatus(OrderStatus.PARTIAL_FAILED);
        }
        if (payedNum > 0) {
            result.setOrderStatus(OrderStatus.PAYED);
        }
        // 更新订单信息
        orderService.updateByResult(result);
        return result;
    }

}
