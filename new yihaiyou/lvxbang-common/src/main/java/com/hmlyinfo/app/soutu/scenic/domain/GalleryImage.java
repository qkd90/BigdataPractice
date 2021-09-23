package com.hmlyinfo.app.soutu.scenic.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;

public class GalleryImage extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 相册id
	 */
	private long galleryId;

	/**
	 * 图片地址大
	 */
	private String addressLarge;

	/**
	 * 图片地址中
	 */
	private String addressMedium;

	/**
	 * 图片地址小
	 */
	private String addressSmall;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 描述
	 */
	private String content;
	/**
	 * 排序
	 */
	private String imageOrder;

	private transient Date tempCreateTime;

	private int width;

	private int height;

	@JsonProperty
	public String getImageOrder() {
		return imageOrder;
	}

	public void setImageOrder(String imageOrder) {
		this.imageOrder = imageOrder;
	}

	public void setGalleryId(long galleryId) {
		this.galleryId = galleryId;
	}

	@JsonProperty
	public long getGalleryId() {
		return galleryId;
	}

	public void setAddressLarge(String addressLarge) {
		this.addressLarge = addressLarge;
	}

	@JsonProperty
	public String getAddressLarge() {
		return addressLarge;
	}

	public void setAddressMedium(String addressMedium) {
		this.addressMedium = addressMedium;
	}

	@JsonProperty
	public String getAddressMedium() {
		return addressMedium;
	}

	public void setAddressSmall(String addressSmall) {
		this.addressSmall = addressSmall;
	}

	@JsonProperty
	public String getAddressSmall() {
		return addressSmall;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@JsonProperty
	public String getTitle() {
		return title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@JsonProperty
	public String getContent() {
		return content;
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
