package com.data.data.service.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.framework.hibernate.util.Entity;

@javax.persistence.Entity
@Table(name = "tb_scenic_info")
public class ScenicInfo extends Entity {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	@Id
	@GeneratedValue
	private Long				id;
	private String				name;
	private String				city_code;
	private Double				longitude, latitude;
	private Integer				ranking;
	private Integer				advice_hours;
	private Long				father;
	private Integer				is_city;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCity_code() {
		return city_code;
	}

	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}

	public Integer getAdvice_hours() {
		return advice_hours;
	}

	public void setAdvice_hours(Integer advice_hours) {
		this.advice_hours = advice_hours;
	}

	public Long getFather() {
		return father;
	}

	public void setFather(Long father) {
		this.father = father;
	}

	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}

	public Integer getIs_city() {
		return is_city;
	}

	public void setIs_city(Integer is_city) {
		this.is_city = is_city;
	}

}
