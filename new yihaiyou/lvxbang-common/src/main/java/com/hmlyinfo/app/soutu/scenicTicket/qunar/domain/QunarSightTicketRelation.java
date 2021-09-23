package com.hmlyinfo.app.soutu.scenicTicket.qunar.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class QunarSightTicketRelation extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * Qunar景点id
	 */
	private long sightId;

	/**
	 * Qunar门票id
	 */
	private long productId;


	public void setSightId(long sightId) {
		this.sightId = sightId;
	}

	@JsonProperty
	public long getSightId() {
		return sightId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	@JsonProperty
	public long getProductId() {
		return productId;
	}
}
