package com.data.data.hmly.service.ticket.entity;

import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.common.entity.interfaces.ProductPrice;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ticketdateprice")
public class TicketDateprice extends com.framework.hibernate.util.Entity implements java.io.Serializable, ProductPrice {

	private static final long serialVersionUID = 1L;

	private Long        id;                                            // 主键ID
	private Date        huiDate;                                    	// 日期
	private TicketPrice ticketPriceId;                                // 门票报价Id
	private Float       priPrice;                                    // 分销价
	private Float       rebate;                                    // 佣金
	private Float		maketPrice;								//市场价
	private Float		price;								//市场价
	private Integer inventory;

	public TicketDateprice() {

	}

	public TicketDateprice(Long id, Date huiDate, TicketPrice ticketPriceId,
	                       Float priPrice , Float rebate) {
		super();
		this.id = id;
		this.huiDate = huiDate;
		this.ticketPriceId = ticketPriceId;
		this.priPrice = priPrice;
		this.rebate = rebate;
	}

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "huiDate", length = 20)
	public Date getHuiDate() {
		return huiDate;
	}

	public void setHuiDate(Date huiDate) {
		this.huiDate = huiDate;
	}

	@Column(name = "priPrice", length = 20)
	public Float getPriPrice() {
		return priPrice;
	}

	public void setPriPrice(Float priPrice) {
		this.priPrice = priPrice;
	}

	@ManyToOne
	@JoinColumn(name = "ticketPriceId")
	public TicketPrice getTicketPriceId() {
		return ticketPriceId;
	}

	public void setTicketPriceId(TicketPrice ticketPriceId) {
		this.ticketPriceId = ticketPriceId;
	}
	@Column(name = "rebate", length = 20)
	public Float getRebate() {
		return rebate;
	}

	public void setRebate(Float rebate) {
		this.rebate = rebate;
	}

	@Column(name = "maketPrice", length = 20)
	public Float getMaketPrice() {
		return maketPrice;
	}

	public void setMaketPrice(Float maketPrice) {
		this.maketPrice = maketPrice;
	}

	@Column(name = "inventory", length = 20)
	public Integer getInventory() {
		return inventory;
	}

	public void setInventory(Integer inventory) {
		this.inventory = inventory;
	}

	@Column(name = "price", length = 20)
	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	@Override
	@Transient
	public Long getProductId() {
		return ticketPriceId.getTicket().getId();
	}

	@Override
	@Transient
	public Long getPriceId() {
		return id;
	}

	@Override
	@Transient
	public String getName() {
		return ticketPriceId.getTicket().getName();
	}

	@Override
	@Transient
	public Date getDate() {
		return huiDate;
	}

	@Override
	@Transient
	public ProductType getProductType() {
		return ticketPriceId.getTicket().getProType();
	}
}
