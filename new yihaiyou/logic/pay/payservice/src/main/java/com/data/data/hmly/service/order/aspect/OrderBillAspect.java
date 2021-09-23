package com.data.data.hmly.service.order.aspect;

import com.data.data.hmly.service.balance.BalanceService;
import com.data.data.hmly.service.order.OrderDetailService;
import com.data.data.hmly.service.order.entity.OrderDetail;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zzl on 2016/10/25.
 */
@Aspect
@Service
public class OrderBillAspect {

    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private BalanceService balanceService;


    @After("(execution(* com.data.data.hmly.service.order.OrderDetailService.updateByResult(..)))")
    public void doBill(JoinPoint joinPoint) {
        try {
            Object[] objs = joinPoint.getArgs();
            // 判断是单条还是多条
            if (objs[0] instanceof List) {
                // 不可多条订单记录结算
                // ......
            } else {
                OrderDetail orderDetail = (OrderDetail) objs[0];
                orderDetail = orderDetailService.findFullById(orderDetail.getId());
                balanceService.doOrderDetailBill(orderDetail);
                // 处理对账单汇总
//                orderBillSummaryService.doCreateBillSummary();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
