package com.data.data.hmly.service.order;

import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.OrderLog;
import com.data.data.hmly.service.order.entity.enums.OrderDetailStatus;
import com.data.data.hmly.service.order.entity.enums.OrderLogLevel;
import com.data.data.hmly.service.order.vo.OrderResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzl on 2016/10/21.
 */
@Service
public class YhyPlanOrderService {

    @Resource
    private OrderService orderService;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private OrderLogService orderLogService;
    @Resource
    private YhyTicketOrderService yhyTicketOrderService;
    @Resource
    private YhyHotelOrderService yhyHotelOrderService;


    public List<OrderResult> doTakePlanOrder(Long orderId) {
        // 针对每个orderDetail的下单结果集合
        List<OrderResult> resultList = new ArrayList<OrderResult>();
        Order order = orderService.get(orderId);
        List<OrderDetail> orderDetailList = orderDetailService.getByOrderIdAndProductType(orderId, null);
        for (OrderDetail orderDetail : orderDetailList) {
            OrderResult result = this.doTakeLineOrderDetail(order, orderDetail);
            resultList.add(result);
        }
        return resultList;
    }

    private OrderResult doTakeLineOrderDetail(Order order, OrderDetail orderDetail) {
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
            result.setApiResult("行程详情下单失败! #" + orderDetail.getId() + "对应的产品不存在!");
            result.setOrderDetailStatus(OrderDetailStatus.FAILED);
//            resultList.add(result);
            // 写入订单日志
            OrderLog log1 = orderLogService.createOrderLog(orderLogUser, "订单详情#" + orderDetail.getId()
                    + "下单失败! 对应的产品信息不存在!", orderId, orderDetail.getId(), OrderLogLevel.warn);
            orderLogService.loggingOrderLog(log1);
        }
        // 行程中各种类型订单详情
        ProductType productType = product.getProType();
        switch (productType) {
            case scenic:
                result = yhyTicketOrderService.doTakeTicketOrderDetail(order, orderDetail);
                break;
            case hotel:
                result = yhyHotelOrderService.doTakeHotelOrderDetail(order, orderDetail);
            default:
                result.setSuccess(false);
                result.setMsg("无效的产品类型!");
                result.setErrorCode("proType_invalid");
                result.setRealOrderId(Long.toString(orderDetail.getId()));
                result.setOrderDetailId(orderDetail.getId());
                result.setApiResult("行程(# " + order.getId() + ")订单详情#"
                        + orderDetail.getId() + "下单失败,无效产品类型");
                result.setOrderDetailStatus(OrderDetailStatus.FAILED);
                OrderLog log1 = orderLogService.createOrderLog(orderLogUser, "订单详情#" + orderDetail.getId()
                        + "下单失败! 无效产品类型!", orderId, orderDetail.getId(), OrderLogLevel.warn);
                orderLogService.loggingOrderLog(log1);
                break;
        }
        return result;
    }
}
