package com.data.data.hmly.service.order;

import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.common.entity.enums.ProductSource;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.OrderLog;
import com.data.data.hmly.service.order.entity.enums.OrderDetailStatus;
import com.data.data.hmly.service.order.entity.enums.OrderLogLevel;
import com.data.data.hmly.service.order.vo.OrderResult;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzl on 2016/10/19.
 */
@Service
public class YhyCruiseShipOrderService {

    @Resource
    private OrderLogService orderLogService;
    @Resource
    private OrderService orderService;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private OrderMsgService orderMsgService;

    Logger logger = Logger.getLogger(YhyCruiseShipOrderService.class);

    public List<OrderResult> doTakeCruiseShipOrder(Long orderId) {
        // 针对每个orderDetail的下单结果集合
        List<OrderResult> resultList = new ArrayList<OrderResult>();
        Order order = orderService.get(orderId);
        List<OrderDetail> orderDetailList = orderDetailService.getByOrderIdAndProductType(orderId, ProductType.cruiseship);
        for (OrderDetail orderDetail : orderDetailList) {
            OrderResult result = this.doTakeCruiseShipOrderDetail(order, orderDetail);
            resultList.add(result);
        }
        return resultList;
    }

    public OrderResult doTakeCruiseShipOrderDetail(Order order, OrderDetail orderDetail) {
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
            result.setApiResult("邮轮下单失败! 对应的产品不存在!");
            result.setOrderDetailStatus(OrderDetailStatus.FAILED);
//            resultList.add(result);
            // 写入订单日志
            OrderLog log1 = orderLogService.createOrderLog(orderLogUser, "订单详情#" + orderDetail.getId()
                    + "下单失败! 对应的产品信息不存在!", orderId, orderDetail.getId(), OrderLogLevel.warn);
            orderLogService.loggingOrderLog(log1);
//                return result;
        }
        if (product.getSource() != null) {
            // ...
        } else if (product.getSource() == null || ProductSource.LXB.equals(product.getSource())) {
            result.setSuccess(true);
            result.setMsg("下单成功");
            result.setErrorCode("0");
            result.setRealOrderId(Long.toString(orderDetail.getId()));
            result.setOrderDetailId(orderDetail.getId());
            result.setApiResult("邮轮(供应商)下单成功!");
            result.setOrderDetailStatus(OrderDetailStatus.SUCCESS);
            // 写入订单日志
            OrderLog log1 = orderLogService.createOrderLog(orderLogUser, "订单详情(G)#"
                    + orderDetail.getId() + "下单成功!", orderId, orderDetail.getId(), OrderLogLevel.warn);
            orderLogService.loggingOrderLog(log1);
        }
        return result;
    }
}
