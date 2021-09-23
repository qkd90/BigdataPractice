package com.data.data.hmly.quartz.outOrder;

import com.data.data.hmly.service.balance.BalanceService;
import com.data.data.hmly.service.balance.entity.enums.AccountType;
import com.data.data.hmly.service.common.ProductValidateCodeService;
import com.data.data.hmly.service.common.entity.ProductValidateCode;
import com.data.data.hmly.service.common.entity.enums.ProductSource;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.order.OrderDetailService;
import com.data.data.hmly.service.order.OrderLogService;
import com.data.data.hmly.service.order.OrderRefundService;
import com.data.data.hmly.service.order.OrderService;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.OrderLog;
import com.data.data.hmly.service.order.entity.enums.OrderDetailStatus;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.outOrder.JszxOrderDetailService;
import com.data.data.hmly.service.outOrder.JszxOrderService;
import com.data.data.hmly.service.outOrder.OutOrderCancelService;
import com.data.data.hmly.service.outOrder.entity.JszxOrder;
import com.data.data.hmly.service.outOrder.entity.JszxOrderDetail;
import com.data.data.hmly.service.outOrder.entity.enums.JszxOrderDetailStatus;
import com.data.data.hmly.service.outOrder.entity.enums.JszxOrderStatus;
import com.framework.hibernate.util.Page;
import com.zuipin.util.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by zzl on 2016/8/24.
 */
@Component
public class TicketAutoRefundQuartz {

    @Resource
    private OrderLogService orderLogService;
    @Resource
    private OrderService orderService;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private JszxOrderService jszxOrderService;
    @Resource
    private JszxOrderDetailService jszxOrderDetailService;
    @Resource
    private OutOrderCancelService outOrderCancelService;
    @Resource
    private BalanceService balanceService;
    @Resource
    private ProductValidateCodeService productValidateCodeService;
    @Resource
    private OrderRefundService orderRefundService;




    public void doAutoRefundTicket() {
        User user = orderLogService.getSysOrderLogUser();
        List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
        Integer pageIndex = 1;
        Integer pageSize = 1000;
        Integer processed = 0;
        Integer total = 0;
        Page page;
        OrderDetail condition = new OrderDetail();
        Map<String, Object> result = new HashMap<String, Object>();
        // 获取供应商, 门票类型订单
        condition.setProductType(ProductType.scenic);
        // 供应商(旅行帮)订单, source为空即可
        List<ProductSource> thirdOrderSources = new ArrayList<ProductSource>();
        condition.setThirdOrderSources(thirdOrderSources);
        // 过期门票限制
        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.set(Calendar.HOUR_OF_DAY, 0);
        nowCalendar.set(Calendar.MINUTE, 0);
        nowCalendar.set(Calendar.SECOND, 0);
        condition.setMaxPlayDate(nowCalendar.getTime());
        condition.setStatus(OrderDetailStatus.SUCCESS);
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
                result.clear();
                OrderDetail orderDetail = iterator.next();
                Order order = orderDetail.getOrder();
                OrderLog log1 = orderLogService.createOrderLog(user, "订单详情(G)#" + orderDetail.getId()
                        + "供应商门票过期, 检查使用状态!", order.getId(), orderDetail.getId());
                orderLogService.loggingOrderLog(log1);
                String jszxOrderDetailIdStr = orderDetail.getRealOrderId();
                if (!StringUtils.isNotBlank() || !StringUtils.isNumber(jszxOrderDetailIdStr)) {
                    OrderLog log2 = orderLogService.createOrderLog(user, "订单详情(G)#" + orderDetail.getId()
                            + "没有找到供应商订单详情ID! 无法处理!", order.getId(), orderDetail.getId());
                    orderLogService.loggingOrderLog(log2);
                    continue;
                }
                Long jszxOrderDetailId = Long.parseLong(jszxOrderDetailIdStr);
                JszxOrderDetail jszxOrderDetail = jszxOrderDetailService.get(jszxOrderDetailId);
                JszxOrder jszxOrder = jszxOrderService.load(jszxOrderDetail.getJszxOrder().getId());
                // 判断过期门票的验票情况
                // 剩余验证次数
                Integer restCount = jszxOrderDetail.getRestCount();
                // 总可验证次数
                Integer count = jszxOrderDetail.getCount();
                if (restCount == 0) {
                    OrderLog log2 = orderLogService.createOrderLog(user, "订单详情(G)#" + orderDetail.getId()
                            + "已使用所有门票, 无剩余可验次数! 无需退款!", order.getId(), orderDetail.getId());
                    orderLogService.loggingOrderLog(log2);
                    continue;
                } else if (restCount > 0) {
                    // 全部未验票, 订单全退
                    if (count.equals(restCount)) {
                        // 旅行帮全额退款流程
                        Map<String, Object> cancelResult = outOrderCancelService.doStartCancel(order, orderDetail, (SysUser) user);
                        // 旅行帮订单详情状态更新
                        // 退款失败
                        if (!(Boolean) cancelResult.get("isAbleToCancel")) {
                            orderDetail.setApiResult("门票过期, 全部未使用, 但是退款失败! 等待重新尝试!");
                            OrderLog log2 = orderLogService.createOrderLog(user, "订单详情(G)#" + orderDetail.getId()
                                            + "门票过期, 全部未使用, 但是退款失败! 等待重新尝试!",
                                    order.getId(), orderDetail.getId());
                            orderLogService.loggingOrderLog(log2);
                        } else {
                            // 非组合线路订单, 增加更新订单状态
                            if (!order.getIsCombineLine()) {
                                order.setStatus(OrderStatus.CANCELED);
                                orderService.update(order);
                            }
                            // 更新旅行帮订单详情信息
                            orderDetail.setStatus(OrderDetailStatus.CANCELED);
                            orderDetail.setApiResult("门票过期, 全部未使用, 自动退款");
                            OrderLog log2 = orderLogService.createOrderLog(user, "订单详情(G)#" + orderDetail.getId()
                                            + "门票过期, 全部未使用, 退款金额: " + orderDetail.getFinalPrice(),
                                    order.getId(), orderDetail.getId());
                            orderLogService.loggingOrderLog(log2);
                            orderDetailService.update(orderDetail);
                            // 供应商方面退款
                            balanceService.updateBalance(orderDetail.getFinalPrice().doubleValue(), AccountType.refund, jszxOrder.getUser().getId(), jszxOrder.getSupplierId(), user.getId(), jszxOrder.getOrderNo(), jszxOrder.getId());
                            // 更新供应商订单信息
                            jszxOrderDetail.setUseStatus(JszxOrderDetailStatus.CANCEL);
                            jszxOrder.setStatus(JszxOrderStatus.CANCELED);
                            jszxOrderService.update(jszxOrder, jszxOrder.getUser(), jszxOrder.getCompanyUnit());
                            jszxOrderDetailService.update(jszxOrderDetail);
//                            // 门票验证码表更新
//                            // 验证码更新
//                            ProductValidateCode productValidateCode = productValidateCodeService.findValidateCodeByOrderNo(jszxOrderDetail.getTicketNo());
//                            productValidateCode.setOrderCount(0);     // 可验票数变更为0
//                            productValidateCode.setRefundCount(count);   // 退款数目变更为总数量
//                            productValidateCode.syncUsed(jszxOrderDetail.getEndTime());
//                            productValidateCode.setUpdateTime(new Date());
//                            productValidateCodeService.update(productValidateCode);
                        }
                    } else if (restCount > 0 && !restCount.equals(count)) {
                        // 部分未验票, 部分退款
                        // 旅行帮退款流程
                        // 单价
                        Float singlePrice = orderDetail.getFinalPrice() / orderDetail.getNum();
                        // 未验票部分退款
                        Map<String, Object> cancelResult = orderRefundService.doStartRefund(order, orderDetail, singlePrice * restCount);
                       // 退款失败
                        if (!(Boolean) cancelResult.get("isAbleToCancel")) {
                            orderDetail.setApiResult("门票过期, 未使用(" + restCount + "张), 但是退款失败! 等待重新尝试!");
                            OrderLog log2 = orderLogService.createOrderLog(user, "订单详情(G)#" + orderDetail.getId()
                                            + "门票过期, 未使用(" + restCount + "张), 但是退款失败! 等待重新尝试!",
                                    order.getId(), orderDetail.getId());
                            orderLogService.loggingOrderLog(log2);
                        } else {
                            // 非组合线路订单, 增加更新订单状态
                            if (!order.getIsCombineLine()) {
                                order.setStatus(OrderStatus.CANCELED);
                                orderService.update(order);
                            }
                            // 更新旅行帮订单详情信息
                            orderDetail.setStatus(OrderDetailStatus.CANCELED);
                            orderDetail.setApiResult("门票过期, 未使用(" + restCount + "张), 已自动退款: " + singlePrice * restCount);
                            OrderLog log2 = orderLogService.createOrderLog(user, "订单详情(G)#" + orderDetail.getId()
                                            + "门票过期, 未使用(" + restCount + "张), 退款金额: " + orderDetail.getFinalPrice(),
                                    order.getId(), orderDetail.getId());
                            orderLogService.loggingOrderLog(log2);
                            orderDetailService.update(orderDetail);
                            // 供应商方面退款
                            balanceService.updateBalance(singlePrice.doubleValue() * restCount, AccountType.refund, jszxOrder.getUser().getId(), jszxOrder.getSupplierId(), user.getId(), jszxOrder.getOrderNo(), jszxOrder.getId());
                            // 更新供应商订单信息
                            jszxOrderDetail.setUseStatus(JszxOrderDetailStatus.CANCEL);
                            jszxOrder.setStatus(JszxOrderStatus.CANCELED);
                            jszxOrderService.update(jszxOrder, jszxOrder.getUser(), jszxOrder.getCompanyUnit());
                            jszxOrderDetailService.update(jszxOrderDetail);
//                            // 门票验证码表更新
//                            // 验证码更新
//                            ProductValidateCode productValidateCode = productValidateCodeService.findValidateCodeByOrderNo(jszxOrderDetail.getTicketNo());
//                            productValidateCode.setOrderCount(0);     // 可验票数变更为0
//                            productValidateCode.setRefundCount(count);   // 退款数目变更为总数量
//                            productValidateCode.syncUsed(jszxOrderDetail.getEndTime());
//                            productValidateCode.setUpdateTime(new Date());
//                            productValidateCodeService.update(productValidateCode);
                        }
                    }
                    // 门票验证码表更新
                    // 验证码更新
                    ProductValidateCode productValidateCode = productValidateCodeService.findValidateCodeByOrderNo(jszxOrderDetail.getTicketNo());
                    productValidateCode.setOrderCount(0);     // 可验票数变更为0
                    productValidateCode.setRefundCount(restCount);   // 退款数目变更为剩余验证次数
                    productValidateCode.syncUsed(jszxOrderDetail.getEndTime());
                    productValidateCode.setUpdateTime(new Date());
                    productValidateCodeService.update(productValidateCode);
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
}
