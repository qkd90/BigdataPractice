package com.data.data.service.pojo;

import com.framework.hibernate.util.Entity;
import com.hmlyinfo.app.soutu.plan.newAlg.modal.Point;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Table(name = "data_map_region")
@javax.persistence.Entity
public class Area extends Entity {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -1959134189454463485L;
	@Id
	@GeneratedValue
	private Long				id;
	private String				name;
	@Column(name = "points")
	private String				pointStr;
	private Integer				priority;
	private String				description;
	private String				cityCode;
	@Transient
	private List<Point>			points;

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

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPointStr() {
		return pointStr;
	}

	public void setPointStr(String pointStr) {
		this.pointStr = pointStr;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

}
