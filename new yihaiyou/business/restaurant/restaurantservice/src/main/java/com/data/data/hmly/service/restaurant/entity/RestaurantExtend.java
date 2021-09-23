package com.data.data.hmly.service.restaurant.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "restaurant_extend")
public class RestaurantExtend extends com.framework.hibernate.util.Entity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id")
	private Restaurant restaurant;
	@Column(name = "address")
	private String address;
	@Column(name = "telephone")
	private String telephone;
	@Column(name = "shop_hours")
	private String shopHours;
	@Column(name = "short_comment")
	private String shortComment;

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getShopHours() {
		return shopHours;
	}

	public void setShopHours(String shopHours) {
		this.shopHours = shopHours;
	}

	public String getShortComment() {
		return shortComment;
	}

	public void setShortComment(String shortComment) {
		this.shortComment = shortComment;
	}
}
