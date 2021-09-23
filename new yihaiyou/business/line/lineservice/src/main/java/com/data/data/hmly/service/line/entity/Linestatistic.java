package com.data.data.hmly.service.line.entity;

// Generated 2015-10-15 11:48:07 by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Linestatistic generated by hbm2java
 */
@Entity
@Table(name = "linestatistic")
public class Linestatistic extends com.framework.hibernate.util.Entity implements java.io.Serializable {
	private static final long serialVersionUID = -4926881024503077637L;
	private Long	id;
	private Integer	shareNum;
	private Integer	pinglunNum;
	private Integer	clickNum;
	private Integer	collectionNum;
	private Integer	visitNum;
	private Integer	orderNum;
	private Line	line;

	public Linestatistic() {
	}

	public Linestatistic(Integer shareNum, Integer pinglunNum, Integer clickNum, Integer collectionNum, Integer visitNum) {
		this.shareNum = shareNum;
		this.pinglunNum = pinglunNum;
		this.clickNum = clickNum;
		this.collectionNum = collectionNum;
		this.visitNum = visitNum;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "shareNum")
	public Integer getShareNum() {
		return shareNum;
	}

	public void setShareNum(Integer shareNum) {
		this.shareNum = shareNum;
	}

	@Column(name = "pinglunNum")
	public Integer getPinglunNum() {
		return pinglunNum;
	}

	public void setPinglunNum(Integer pinglunNum) {
		this.pinglunNum = pinglunNum;
	}

	@Column(name = "clickNum")
	public Integer getClickNum() {
		return clickNum;
	}

	public void setClickNum(Integer clickNum) {
		this.clickNum = clickNum;
	}

	@Column(name = "collectionNum")
	public Integer getCollectionNum() {
		return collectionNum;
	}

	public void setCollectionNum(Integer collectionNum) {
		this.collectionNum = collectionNum;
	}

	@Column(name = "visitNum")
	public Integer getVisitNum() {
		return visitNum;
	}

	public void setVisitNum(Integer visitNum) {
		this.visitNum = visitNum;
	}

	@Column(name = "orderNum")
	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="lineId",nullable=false)
	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
	}

}
