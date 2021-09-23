package com.data.data.hmly.service.order;

import com.data.data.hmly.service.order.entity.OrderDetail;

/**
 * Created by guoshijie on 2015/10/29.
 */
public abstract class ProductOrderService {

	public abstract boolean isNeedConfirm(OrderDetail orderDetail);

	public abstract boolean saveOrderDetail(OrderDetail orderDetail);

	public abstract void fixPrice(OrderDetail orderDetail);

	public abstract float getRebate(OrderDetail orderDetail);
}
