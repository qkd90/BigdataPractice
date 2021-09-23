package com.zuipin.pojo;

import javax.persistence.Id;
import javax.persistence.Table;

import com.framework.hibernate.util.Entity;

@javax.persistence.Entity
@Table(name = "month")
public class Month extends Entity {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -3822269707602695812L;
	@Id
	private Long				id;
	private Long				proid;
	private Float				num, price;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
