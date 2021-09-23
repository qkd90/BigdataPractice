package com.data.data.hmly.service.pay.aop;

import com.data.data.hmly.service.order.OrderService;
import com.data.data.hmly.service.order.entity.Order;
import com.zuipin.util.SpringContextHolder;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by guoshijie on 2015/11/11.
 */
@Aspect
public class ProductValidateSMSAspect {
	private Logger logger = Logger.getLogger(ProductValidateSMSAspect.class);

	@Pointcut("execution(* com.data.data.hmly.service.pay.*.doBusiness(..))")
	private void anyMethod() {
	}//定义一个切入点

	@AfterReturning("anyMethod()")
	public void doAfter(JoinPoint pjp) {
		logger.info("开始准备发送验证码短信");
		OrderService orderService = SpringContextHolder.getBean("orderService");
		Order order = (Order) pjp.getArgs()[0];
		if (order == null) {
			logger.error("处理异常");
			return;
		}
		logger.info("开始发送#" + order.getId() + "的短信");
		orderService.doCalculateCommission(order);

		logger.info("发送#" + order.getId() + "的短信成功");
	}

	@AfterThrowing("anyMethod()")
	public void doAfterThrow(){
		logger.info("支付回调业务异常");
	}


}
