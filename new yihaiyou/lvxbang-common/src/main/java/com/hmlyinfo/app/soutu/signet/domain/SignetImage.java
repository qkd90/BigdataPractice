package com.hmlyinfo.app.soutu.signet.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class SignetImage extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private long bookId;

	private String weixinUrl;
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
	private String weather;

	/**
	 *
	 */
	private String memo;


	/**
	 *
	 */
	private long signetRecordId;


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

	public void setWeather(String weather) {
		this.weather = weather;
	}

	@JsonProperty
	public String getWeather() {
		return weather;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@JsonProperty
	public String getMemo() {
		return memo;
	}

	public void setSignetRecordId(long signetRecordId) {
		this.signetRecordId = signetRecordId;
	}

	@JsonProperty
	public long getSignetRecordId() {
		return signetRecordId;
	}

	@JsonProperty
	public long getBookId() {
		return bookId;
	}

	public void setBookId(long bookId) {
		this.bookId = bookId;
	}

	@JsonProperty
	public String getWeixinUrl() {
		return weixinUrl;
	}

	public void setWeixinUrl(String weixinUrl) {
		this.weixinUrl = weixinUrl;
	}
}
