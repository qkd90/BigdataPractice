package com.data.data.hmly.service.order.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.data.data.hmly.service.entity.User;

/**
 * Created by guoshijie on 2015/10/20.
 */
@Entity
@Table(name = "torderdetailflattened")
public class OrderDetailFlattened implements Serializable {

	@Id
	@GeneratedValue
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orderdetailId")
	private OrderDetail orderDetail;
	@ManyToOne
	@JoinColumn(name = "higherId")
	private User higher;
	@ManyToOne
	@JoinColumn(name = "superiorId")
	private User superior;

	public OrderDetail getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(OrderDetail orderDetail) {
		this.orderDetail = orderDetail;
	}

	public User getHigher() {
		return higher;
	}

	public void setHigher(User higher) {
		this.higher = higher;
	}

	public User getSuperior() {
		return superior;
	}

	public void setSuperior(User superior) {
		this.superior = superior;
	}
}
