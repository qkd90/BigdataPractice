package com.data.data.hmly.service.order.entity;

import com.data.data.hmly.enums.Gender;
import com.data.data.hmly.service.order.entity.enums.OrderTouristIdType;
import com.data.data.hmly.service.order.entity.enums.OrderTouristPeopleType;

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
import javax.persistence.Transient;
import java.util.Date;

/**
 * Created by guoshijie on 2015/10/29.
 */
@Entity
@Table(name = "tordertourist")
public class OrderTourist extends com.framework.hibernate.util.Entity implements java.io.Serializable {

	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false, insertable = true, updatable = true)
	private Long               id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orderId", unique = true, nullable = false, updatable = false)
	private Order              order;
	@Column(name = "name")
	private String             name;
    @Column(name = "gender")
    private Gender gender;
	@Column(name = "tel")
	private String             tel;
	@Column(name = "idType")
	@Enumerated(EnumType.STRING)
	private OrderTouristIdType idType;
	@Column(name = "idNumber")
	private String             idNumber;
	@Column(name = "remark")
	private String             remark;
	@Column(name = "modifyTime")
	private Date               modifyTime;
	@Column(name = "createTime")
	private Date               createTime;

	@Column(name = "peopleType")
	@Enumerated(EnumType.STRING)
	private OrderTouristPeopleType peopleType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderDetailId")
    private OrderDetail orderDetail;

	@Transient
	private String genderStr;
	@Transient
	private String birthday;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public OrderTouristIdType getIdType() {
		return idType;
	}

	public void setIdType(OrderTouristIdType idType) {
		this.idType = idType;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public OrderTouristPeopleType getPeopleType() {
		return peopleType;
	}

	public void setPeopleType(OrderTouristPeopleType peopleType) {
		this.peopleType = peopleType;
	}

    public OrderDetail getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }

	public String getGenderStr() {
		return genderStr;
	}

	public void setGenderStr(String genderStr) {
		this.genderStr = genderStr;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
}
