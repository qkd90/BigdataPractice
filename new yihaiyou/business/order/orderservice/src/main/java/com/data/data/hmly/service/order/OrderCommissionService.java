package com.data.data.hmly.service.order;

import com.data.data.hmly.service.entity.SysResourceMap;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.order.entity.Commission;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.enums.CommissionLevel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * Created by guoshijie on 2015/11/4.
 */
@Service
public class OrderCommissionService {

	@Resource
	private CommissionService      commissionService;
	@Resource
	private LineOrderService       lineOrderService;
	@Resource
	private TicketOrderService     ticketOrderService;
	@Resource
	private HotelOrderService      hotelOrderService;
	@Resource
	private RestaurantOrderService restaurantOrderService;

	public float createCommionssion(User user, OrderDetail orderDetail, Map<String, SysResourceMap> commissionMap, CommissionLevel level) {
		Commission commission = new Commission();
		commission.setUser(user);
		commission.setProduct(orderDetail.getProduct());
		commission.setTopProduct(orderDetail.getProduct().getTopProduct());
		commission.setLevel(level);
		SysResourceMap sysResourceMap = commissionMap.get(level.toString());
		float commissionRatio = Float.valueOf(sysResourceMap.getDescription());
		float rebate = getRebate(orderDetail);
		int count = orderDetail.getNum();
		commission.setMoney(rebate * commissionRatio * count);
		commission.setOrderDetail(orderDetail);
		commission.setOrderNo(orderDetail.getOrder().getOrderNo());
		commission.setCreatedTime(new Date());
		commissionService.save(commission);
		return commissionRatio;
	}

	private float getRebate(OrderDetail orderDetail) {

		switch (orderDetail.getProduct().getProType()) {
			case line:
				return lineOrderService.getRebate(orderDetail);
			case scenic:
				return ticketOrderService.getRebate(orderDetail);
			case restaurant:
				return restaurantOrderService.getRebate(orderDetail);
			case hotel:
				return hotelOrderService.getRebate(orderDetail);
			default:
				return 0;
		}
	}


}
