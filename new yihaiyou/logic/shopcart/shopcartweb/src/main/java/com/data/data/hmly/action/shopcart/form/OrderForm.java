package com.data.data.hmly.action.shopcart.form;

import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.OrderTourist;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guoshijie on 2015/10/29.
 */
public class OrderForm {

	private String              playDate;
	private Order               order;
	private List<OrderDetail>   orderDetails = new ArrayList<OrderDetail>();
	private List<OrderTourist>  tourists     = new ArrayList<OrderTourist>();

	public String getPlayDate() {
		return playDate;
	}

	public void setPlayDate(String playDate) {
		this.playDate = playDate;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public List<OrderTourist> getTourists() {
		return tourists;
	}

	public void setTourists(List<OrderTourist> tourists) {
		this.tourists = tourists;
	}

}
