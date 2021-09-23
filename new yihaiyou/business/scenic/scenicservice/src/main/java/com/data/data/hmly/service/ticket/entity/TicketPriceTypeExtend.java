package com.data.data.hmly.service.ticket.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tciket_price_type_extend")
public class TicketPriceTypeExtend extends com.framework.hibernate.util.Entity implements Serializable {

	private static final long serialVersionUID = 1L;
/*
	`id` bigint(20) NOT NULL,
	`first_title` varchar(255) DEFAULT NULL COMMENT '一级标题',
			`second_title` varchar(255) DEFAULT NULL COMMENT '二级小标题',
			`content` varchar(500) DEFAULT NULL,
	`type_price_id` bigint(20) DEFAULT NULL COMMENT '票型ID',
			`create_time` datetime DEFAULT NULL,
	*/

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "first_title")
	private String firstTitle;

	@Column(name = "second_title")
	private String secondTitle;

	@Column(name = "content")
	private String content;

	@ManyToOne
	@JoinColumn(name = "type_price_id", nullable = false)
	private TicketPrice ticketPrice;

	@Column(name = "create_time")
	private Date createTime;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstTitle() {
		return firstTitle;
	}

	public void setFirstTitle(String firstTitle) {
		this.firstTitle = firstTitle;
	}

	public String getSecondTitle() {
		return secondTitle;
	}

	public void setSecondTitle(String secondTitle) {
		this.secondTitle = secondTitle;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public TicketPrice getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(TicketPrice ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


}
