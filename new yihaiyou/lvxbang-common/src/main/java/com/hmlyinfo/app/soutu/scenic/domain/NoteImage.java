package com.hmlyinfo.app.soutu.scenic.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class NoteImage extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 游记id
	 */
	private long noteId;

	/**
	 * 游记天id
	 */
	private long notedayId;

	/**
	 * 游记景点编号
	 */
	private long noteScenicId;

	/**
	 * 景点编号
	 */
	private long scenicId;

	/**
	 * 对应的行程节点编号
	 */
	private long planTripId;

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
	 * 图片描述
	 */
	private String imageDesc;

	/**
	 * 评论数量
	 */
	private int commentCount;

	/**
	 * 点赞数量
	 */
	private int goodCount;

	/**
	 * 图片宽度
	 */
	private int imageWidth;

	/**
	 * 图片高度
	 */
	private int imageHeight;

	private transient boolean praised;

	public void setNoteId(long noteId) {
		this.noteId = noteId;
	}

	@JsonProperty
	public long getNoteId() {
		return noteId;
	}

	public void setNotedayId(long notedayId) {
		this.notedayId = notedayId;
	}

	@JsonProperty
	public long getNotedayId() {
		return notedayId;
	}

	public void setNoteScenicId(long noteScenicId) {
		this.noteScenicId = noteScenicId;
	}

	@JsonProperty
	public long getNoteScenicId() {
		return noteScenicId;
	}

	public void setScenicId(long scenicId) {
		this.scenicId = scenicId;
	}

	@JsonProperty
	public long getScenicId() {
		return scenicId;
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

	public void setImageDesc(String imageDesc) {
		this.imageDesc = imageDesc;
	}

	@JsonProperty
	public String getImageDesc() {
		return imageDesc;
	}

	@JsonProperty
	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	@JsonProperty
	public int getGoodCount() {
		return goodCount;
	}

	public void setGoodCount(int goodCount) {
		this.goodCount = goodCount;
	}

	@JsonProperty
	public int getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}

	@JsonProperty
	public int getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}

	@JsonProperty
	public boolean isPraised() {
		return praised;
	}

	public void setPraised(boolean praised) {
		this.praised = praised;
	}

	@JsonProperty
	public long getPlanTripId() {
		return planTripId;
	}

	public void setPlanTripId(long planTripId) {
		this.planTripId = planTripId;
	}
}
