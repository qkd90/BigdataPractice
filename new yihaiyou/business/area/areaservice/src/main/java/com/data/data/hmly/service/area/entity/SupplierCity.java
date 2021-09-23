package com.data.data.hmly.service.area.entity;

import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.TbArea;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by guoshijie on 2015/11/27.
 */
@Entity
@Table(name = "suppliercity")
public class SupplierCity extends com.framework.hibernate.util.Entity implements java.io.Serializable {

	private static final long serialVersionUID = -4875190604609069378L;
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "supplierId", nullable = false, updatable = false)
	private SysUnit supplier;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cityId", nullable = false, updatable = false)
	private TbArea city;
	@Column(name = "recommended")
	private boolean recommended;
	@Column(name = "selected")
	private boolean selected;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SysUnit getSupplier() {
		return supplier;
	}

	public void setSupplier(SysUnit supplier) {
		this.supplier = supplier;
	}

	public TbArea getCity() {
		return city;
	}

	public void setCity(TbArea city) {
		this.city = city;
	}

	public boolean isRecommended() {
		return recommended;
	}

	public void setRecommended(boolean recommended) {
		this.recommended = recommended;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
