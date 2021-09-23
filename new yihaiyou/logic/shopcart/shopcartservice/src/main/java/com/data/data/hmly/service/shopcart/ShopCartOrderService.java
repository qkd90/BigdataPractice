package com.data.data.hmly.service.shopcart;

import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.order.HotelOrderService;
import com.data.data.hmly.service.order.LineOrderService;
import com.data.data.hmly.service.order.OrderDetailService;
import com.data.data.hmly.service.order.RestaurantOrderService;
import com.data.data.hmly.service.order.TicketOrderService;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.OrderTourist;
import com.data.data.hmly.service.order.entity.OrderTouristService;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.zuipin.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by guoshijie on 2015/10/29.
 */
@Service
public class ShopCartOrderService {

	@Resource
	private com.data.data.hmly.service.order.OrderService orderService;
	@Resource
	private OrderDetailService orderDetailService;
	@Resource
	private OrderTouristService orderTouristService;
	@Resource
	private LineOrderService lineOrderService;
	@Resource
	private TicketOrderService ticketOrderService;
	@Resource
	private HotelOrderService hotelOrderService;
	@Resource
	private RestaurantOrderService restaurantOrderService;


	public Order doCreateOrder(Member user, Order order, List<OrderDetail> orderDetailList, List<OrderTourist> orderTouristList, Date playDate) {

		if (order == null) {
			order = new Order();
			//todo: should not use tourist information, we should find out a way to fill WeChat user's userName
			if (user == null || StringUtils.isBlank(user.getUserName()) || StringUtils.isBlank(user.getMobile())) {
				order.setRecName(orderTouristList.get(0).getName());
				order.setMobile(orderTouristList.get(0).getTel());
			} else {
				order.setRecName(user.getUserName());
				order.setMobile(user.getMobile());
			}
		}
		checkNeedConfirm(order, orderDetailList);
		order.setModifyTime(new Date());
		order.setCreateTime(new Date());
		order.setUser(user);
		order.setOrderDetails(orderDetailList);
		orderService.save(order);
		doCreateOrderDetail(order, orderDetailList, playDate);
		doCreateOrderTourist(order, orderTouristList);
		return order;
	}

	public void checkNeedConfirm(Order order, List<OrderDetail> orderDetailList) {
		for (OrderDetail orderDetail : orderDetailList) {
			if (isNeedConfirm(orderDetail)) {
				order.setStatus(OrderStatus.UNCONFIRMED);
				return;
			}
		}
		order.setStatus(OrderStatus.WAIT);
	}

	public boolean isNeedConfirm(OrderDetail orderDetail) {
		switch (orderDetail.getProduct().getProType()) {
			case line:
				return lineOrderService.isNeedConfirm(orderDetail);
			case scenic:
				return ticketOrderService.isNeedConfirm(orderDetail);
			case hotel:
				return hotelOrderService.isNeedConfirm(orderDetail);
			case restaurant:
				return restaurantOrderService.isNeedConfirm(orderDetail);
			default:
				return false;
		}
	}

	public void doCreateOrderDetail(Order order, List<OrderDetail> orderDetailList, Date playDate) {
		for (OrderDetail orderDetail : orderDetailList) {
			if (orderDetail.getNum() == 0) {
				continue;
			}
			orderDetail.setOrder(order);
			orderDetail.setPlayDate(playDate);
			fixOrderDetailPrice(orderDetail);
			doPromotion(orderDetail);
			orderDetailService.save(orderDetail);
		}
	}

	public void fixOrderDetailPrice(OrderDetail orderDetail) {
		switch (orderDetail.getProduct().getProType()) {
			case line:
				lineOrderService.fixPrice(orderDetail);
				return;
			case scenic:
				ticketOrderService.fixPrice(orderDetail);
				return;
			case restaurant:
				restaurantOrderService.fixPrice(orderDetail);
				return;
			case hotel:
				hotelOrderService.fixPrice(orderDetail);
				return;
		}
	}

	public void doPromotion(OrderDetail orderDetail) {
		float finalPrice = orderDetail.getTotalPrice();
		//处理促销流程

		orderDetail.setFinalPrice(finalPrice);
	}

	public void doCreateOrderTourist(Order order, List<OrderTourist> orderTouristList) {
		for (OrderTourist orderTourist : orderTouristList) {
			orderTourist.setOrder(order);
			orderTouristService.save(orderTourist);
		}
	}
}
