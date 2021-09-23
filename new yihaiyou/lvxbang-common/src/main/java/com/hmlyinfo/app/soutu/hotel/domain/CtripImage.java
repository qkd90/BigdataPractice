package com.hmlyinfo.app.soutu.hotel.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;

public class CtripImage extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 *
	 */
	private long hotelId;

	/**
	 *
	 */
	private long indexId;


	/**
	 *
	 */
	private String imgBig;

	/**
	 *
	 */
	private String imgSmall;

	/**
	 *
	 */
	private int type;

	/**
	 *
	 */
	private String description;

	/**
	 *
	 */
	private int width;

	/**
	 *
	 */
	private int height;

	private transient Date tempCreateTime;

	public void setHotelId(long hotelId) {
		this.hotelId = hotelId;
	}

	@JsonProperty
	public long getHotelId() {
		return hotelId;
	}

	@JsonProperty
	public long getIndexId() {
		return indexId;
	}

	public void setIndexId(long indexId) {
		this.indexId = indexId;
	}

	public void setImgBig(String imgBig) {
		this.imgBig = imgBig;
	}

	@JsonProperty
	public String getImgBig() {
		return imgBig;
	}

	public void setImgSmall(String imgSmall) {
		this.imgSmall = imgSmall;
	}

	@JsonProperty
	public String getImgSmall() {
		return imgSmall;
	}

	public void setType(int type) {
		this.type = type;
	}

	@JsonProperty
	public int getType() {
		return type;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty
	public String getDescription() {
		return description;
	}

	@JsonProperty
	public Date getTempCreateTime() {
		return this.getCreateTime();
	}

	public void setTempCreateTime(Date tempCreateTime) {
		this.tempCreateTime = tempCreateTime;
	}

	@JsonProperty
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	@JsonProperty
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}


}
