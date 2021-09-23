package com.data.data.hmly.action.user;

import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.order.OrderService;
import com.data.data.hmly.service.order.entity.Order;
import com.framework.hibernate.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by guoshijie on 2015/11/16.
 */
@Service
public class HomeOrderService {

	@Resource
	private OrderService orderService;
	
	public List<Order> listOrder(Order order, Page page, User user, String... orderProperties) {
		return orderService.listByCustomer(order, page, user, orderProperties);
	}

	public Order detail(Long id) {
		return orderService.get(id);
	}

}
