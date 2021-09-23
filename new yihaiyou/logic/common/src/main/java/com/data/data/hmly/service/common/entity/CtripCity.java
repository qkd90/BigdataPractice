package com.data.data.hmly.service.common.entity;


import com.data.data.hmly.service.entity.TbArea;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ctrip_city")
public class CtripCity extends com.framework.hibernate.util.Entity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
	@OneToOne
    @JoinColumn(name = "city_id")
    private TbArea city;
    @Column(name = "name")
    private String name;
    @Column(name = "code_name")
    private String codeName;
    @Column(name = "province")
    private Long province;
    @Column(name = "hotel_count")
    private Long hotelCount;
    @Column(name = "price_count")
    private Long priceCount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TbArea getCity() {
		return city;
	}

	public void setCity(TbArea city) {
		this.city = city;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public Long getProvince() {
		return province;
	}

	public void setProvince(Long province) {
		this.province = province;
	}

	public Long getHotelCount() {
		return hotelCount;
	}

	public void setHotelCount(Long hotelCount) {
		this.hotelCount = hotelCount;
	}

	public Long getPriceCount() {
		return priceCount;
	}

	public void setPriceCount(Long priceCount) {
		this.priceCount = priceCount;
	}
}
