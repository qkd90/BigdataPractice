package com.data.data.hmly.service.ticket.entity;

public class TicketMinData {
	private Long id;
	private Float minRebate;
	private Float minPriPrice;
	private Float minMarketPrice;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Float getMinRebate() {
		return minRebate;
	}
	public void setMinRebate(Float minRebate) {
		this.minRebate = minRebate;
	}
	public Float getMinPriPrice() {
		return minPriPrice;
	}
	public void setMinPriPrice(Float minPriPrice) {
		this.minPriPrice = minPriPrice;
	}

	public Float getMinMarketPrice() {
		return minMarketPrice;
	}

	public void setMinMarketPrice(Float minMarketPrice) {
		this.minMarketPrice = minMarketPrice;
	}
}
