package com.data.data.hmly.service.vo;

import java.io.Serializable;
import java.util.List;

import com.data.data.hmly.service.entity.Label;

public class ProductLabelVo implements Serializable {
	
	private static final long		serialVersionUID	= 1L;
	
	/** VO字段,不存数据库 **/
	private Long					id;
	private String 					name;
	private String 					cityName;
	private Long 					cityId;
	private List<Label> 			labels;
	
	
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
	public List<Label> getLabels() {
		return labels;
	}
	public void setLabels(List<Label> labels) {
		this.labels = labels;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public Long getCityId() {
		return cityId;
	}
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	
}
