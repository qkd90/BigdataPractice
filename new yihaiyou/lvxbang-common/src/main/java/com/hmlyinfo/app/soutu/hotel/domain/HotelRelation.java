package com.hmlyinfo.app.soutu.hotel.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class HotelRelation extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 *
	 */
	private long indexId;

	/**
	 *
	 */
	private long hotelId;


	public void setIndexId(long indexId) {
		this.indexId = indexId;
	}

	@JsonProperty
	public long getIndexId() {
		return indexId;
	}

	public void setHotelId(long hotelId) {
		this.hotelId = hotelId;
	}

	@JsonProperty
	public long getHotelId() {
		return hotelId;
	}
}
