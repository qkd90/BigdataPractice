package com.data.data.hmly.service.restaurant.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "restaurant_geoinfo")
public class RestaurantGeoInfo extends com.framework.hibernate.util.Entity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id")
	private Restaurant restaurant;
	@Column(name = "baidu_lng")
	private Double baiduLng;
	@Column(name = "baidu_lat")
	private Double baiduLat;
	@Column(name = "google_lng")
	private Double googleLng;
	@Column(name = "google_lat")
	private Double googleLat;

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Double getBaiduLng() {
		return baiduLng;
	}

	public void setBaiduLng(Double baiduLng) {
		this.baiduLng = baiduLng;
	}

	public Double getBaiduLat() {
		return baiduLat;
	}

	public void setBaiduLat(Double baiduLat) {
		this.baiduLat = baiduLat;
	}

	public Double getGoogleLng() {
		return googleLng;
	}

	public void setGoogleLng(Double googleLng) {
		this.googleLng = googleLng;
	}

	public Double getGoogleLat() {
		return googleLat;
	}

	public void setGoogleLat(Double googleLat) {
		this.googleLat = googleLat;
	}
}
