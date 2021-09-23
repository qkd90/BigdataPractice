package com.data.data.hmly.service.order.aspect;

import com.data.data.hmly.service.common.ProductService;
import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.order.OrderAliasService;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderAlias;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guoshijie on 2015/11/11.
 */
//@Aspect
//@Service
public class OrderAspect {

	private Logger logger = Logger.getLogger(OrderAspect.class);

	@Resource
	private OrderAliasService orderAliasService;
	@Resource
	private ProductService productService;

//	@After("execution (* com.data.data.hmly.service.order.OrderService.save(..))")
	public void doAfter(JoinPoint pjp) {

		logger.info("创建订单关联关系");
		if (pjp.getArgs().length == 0) {
			logger.error("没有订单数据，处理失败");
		}
		Order order = null;
		for (Object o : pjp.getArgs()) {
			if (o instanceof Order) {
				order = (Order) o;
				break;
			}
		}
		Map<Long, OrderAlias> aliasMap = new HashMap<Long, OrderAlias>();
		for (OrderDetail orderDetail : order.getOrderDetails()) {
			Product product = productService.get(orderDetail.getProduct().getId());
			//1. create alias with product owner
			createOwnerAlias(order, aliasMap, product);

			//2. create alias with product owner's each superior
			createDistributorAlias(order, aliasMap, product);

			//3. create alias with product's top product owner
			createTopProductAlias(order, aliasMap, product);
		}

		List<OrderAlias> orderAliases = Lists.newArrayList(aliasMap.values());

		orderAliasService.saveAll(orderAliases);
		logger.info("创建订单关联关系成功");
	}

	private void createOwnerAlias(Order order, Map<Long, OrderAlias> aliasMap, Product product) {
		OrderAlias orderAlias = createAlias(product.getUser(), order);
		aliasMap.put(product.getUser().getId(), orderAlias);
	}

	private void createDistributorAlias(Order order, Map<Long, OrderAlias> aliasMap, Product product) {

		User user = product.getUser();
		if (user.getParent() != null && !user.getParent().getId().equals(user.getId())) {
			createDistributorAlias(order, aliasMap, user.getParent());
		}
	}

	private void createDistributorAlias(Order order, Map<Long, OrderAlias> aliasMap, User user) {
		OrderAlias orderAlias = createAlias(user, order);
		aliasMap.put(user.getId(), orderAlias);
		if (user.getParent() != null && !user.getParent().getId().equals(user.getId())) {
			createDistributorAlias(order, aliasMap, user);
		}
	}

	private void createTopProductAlias(Order order, Map<Long, OrderAlias> aliasMap, Product product) {
		User user = product.getTopProduct().getUser();
		OrderAlias orderAlias = createAlias(user, order);
		aliasMap.put(user.getId(), orderAlias);
	}

	private OrderAlias createAlias(User user, Order order) {
		OrderAlias orderAlias = new OrderAlias();
		orderAlias.setUser(user);
		orderAlias.setOrder(order);
		return orderAlias;
	}
}
