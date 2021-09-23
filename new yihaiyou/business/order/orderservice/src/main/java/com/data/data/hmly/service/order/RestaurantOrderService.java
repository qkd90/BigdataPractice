package com.data.data.hmly.service.order;

import com.data.data.hmly.service.order.entity.OrderDetail;
import org.springframework.stereotype.Service;

/**
 * Created by guoshijie on 2015/10/29.
 */
@Service
public class RestaurantOrderService extends ProductOrderService{
	@Override
	public boolean isNeedConfirm(OrderDetail orderDetail) {
		return false;
	}

	@Override
	public boolean saveOrderDetail(OrderDetail orderDetail) {
		return false;
	}

	@Override
	public void fixPrice(OrderDetail orderDetail) {

	}

	@Override
	public float getRebate(OrderDetail orderDetail) {
		return 0;
	}
}
