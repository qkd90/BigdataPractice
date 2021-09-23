package com.data.data.hmly.service.pay.aop;

import com.data.data.hmly.service.order.OrderService;
import com.data.data.hmly.service.order.entity.Order;
import com.zuipin.util.SpringContextHolder;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Service;

/**
 * Created by guoshijie on 2015/11/6.
 */
@Aspect
@Service
public class CommissionAspect {
	private Logger logger = Logger.getLogger(CommissionAspect.class);

	@Before("execution(* com.data.data.hmly.service.pay.*.doBusiness(..))")
	public void doAccessCheck() {
		logger.info("开始处理支付回调业务");
	}

	@AfterReturning("execution(* com.data.data.hmly.service.pay.*.doBusiness(..))")
	public void doAfter(JoinPoint pjp) {
		logger.info("开始处理佣金计算工作");
		OrderService orderService = SpringContextHolder.getBean("orderService");
		Order order = (Order) pjp.getArgs()[0];
		if (order == null) {
			logger.error("佣金处理异常");
			return;
		}
		logger.info("开始处理#"+order.getId()+"的佣金");
		orderService.doCalculateCommission(order);

		logger.info("处理#" + order.getId() + "的佣金结束");
	}

	@AfterThrowing("execution(* com.data.data.hmly.service.pay.*.doBusiness(..))")
	public void doAfterThrow(){
		logger.info("支付回调业务异常");
	}


}
