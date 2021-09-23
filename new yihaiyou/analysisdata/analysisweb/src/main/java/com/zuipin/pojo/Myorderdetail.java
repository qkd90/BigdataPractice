package com.zuipin.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Myorderdetail extends com.framework.hibernate.util.Entity {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 2853537876717205221L;
	
	@Id
	private Long				id;
	
	private Long				proid;
	private Float				num, price;
	private Long				orderid;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public Long getProid() {
		return proid;
	}
	
	public void setProid(Long proid) {
		this.proid = proid;
	}
	
	public Float getNum() {
		return num;
	}
	
	public void setNum(Float num) {
		this.num = num;
	}
	
	public Float getPrice() {
		return price;
	}
	
	public void setPrice(Float price) {
		this.price = price;
	}
	
	public Long getOrderid() {
		return orderid;
	}
	
	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}
	
}
