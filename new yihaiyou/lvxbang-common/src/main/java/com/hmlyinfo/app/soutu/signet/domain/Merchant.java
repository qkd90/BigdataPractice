package com.hmlyinfo.app.soutu.signet.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class Merchant extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 商家自己定义的备注信息
	 */
	private String memo;

	/**
	 *
	 */
	private String name;

	/**
	 *
	 */
	private String bookType;

	/**
	 *
	 */
	private String sname;

	/**
	 *
	 */
	private String signetImage;

	/**
	 *
	 */
	private String addr;

	/**
	 *
	 */
	private String lat;

	/**
	 *
	 */
	private String lng;

	/**
	 *
	 */
	private String bgImg;

	/**
	 *
	 */
	private String bgColor;

	/**
	 *
	 */
	private int pageno;

	/**
	 *
	 */
	private long roadId;

	/**
	 * 被扫次数
	 */
	private long visitCount;

	/**
	 * 商家简介
	 */
	private String intro;


	public void setMemo(String memo) {
		this.memo = memo;
	}

	@JsonProperty
	public String getMemo() {
		return memo;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty
	public String getName() {
		return name;
	}

	public void setBookType(String bookType) {
		this.bookType = bookType;
	}

	@JsonProperty
	public String getBookType() {
		return bookType;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	@JsonProperty
	public String getSname() {
		return sname;
	}

	public void setSignetImage(String signetImage) {
		this.signetImage = signetImage;
	}

	@JsonProperty
	public String getSignetImage() {
		return signetImage;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	@JsonProperty
	public String getAddr() {
		return addr;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	@JsonProperty
	public String getLat() {
		return lat;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	@JsonProperty
	public String getLng() {
		return lng;
	}

	public void setBgImg(String bgImg) {
		this.bgImg = bgImg;
	}

	@JsonProperty
	public String getBgImg() {
		return bgImg;
	}

	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

	@JsonProperty
	public String getBgColor() {
		return bgColor;
	}

	public void setPageno(int pageno) {
		this.pageno = pageno;
	}

	@JsonProperty
	public int getPageno() {
		return pageno;
	}

	public void setRoadId(long roadId) {
		this.roadId = roadId;
	}

	@JsonProperty
	public long getRoadId() {
		return roadId;
	}

	public void setVisitCount(long visitCount) {
		this.visitCount = visitCount;
	}

	@JsonProperty
	public long getVisitCount() {
		return visitCount;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	@JsonProperty
	public String getIntro() {
		return intro;
	}
}
