package com.data.data.hmly.service.order.entity;

import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.order.entity.enums.CommissionLevel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by vacuity on 15/11/4.
 */

@Entity
@Table(name = "commission")
public class Commission extends com.framework.hibernate.util.Entity {

	/**
	 *
	 */
	private static final long serialVersionUID = 3453312089240024934L;

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	private long id;

	@ManyToOne
	@JoinColumn(name = "userId", unique = true, nullable = false, updatable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productId", unique = true, nullable = false, updatable = false)
	private Product product;

	@ManyToOne
	@JoinColumn(name = "topProductId", unique = true, nullable = true, updatable = false)
	private Product topProduct;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orderDetailId", unique = true, nullable = false, updatable = false)
	private OrderDetail orderDetail;

	@Column(name = "orderNo")
	private String orderNo;

	@Column(name = "money")
	private float money;

	@Column(name = "level")
	@Enumerated(EnumType.STRING)
	private CommissionLevel level;

	@Column(name = "createdTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdTime;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Product getTopProduct() {
		return topProduct;
	}

	public void setTopProduct(Product topProduct) {
        this.topProduct = topProduct;
    }

	public OrderDetail getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(OrderDetail orderDetail) {
		this.orderDetail = orderDetail;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

	public CommissionLevel getLevel() {
		return level;
	}

	public void setLevel(CommissionLevel level) {
		this.level = level;
	}

	public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
