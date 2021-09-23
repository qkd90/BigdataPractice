package com.data.data.hmly.service.ticket.entity.enmus;

/**
 * 
 * @author dy
 *
 */
public enum Status {
	show("显示"), hide("隐藏"), del("违规"), outday("过期");

	private String description;

	Status(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
