package com.hmlyinfo.app.soutu.scenic.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class CommentImage extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 评论id
	 */

	private long commentId;

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
	 * 对应comment的userId
	 */
	private long commentUserId;


	@JsonProperty
	public long getCommentId() {
		return commentId;
	}

	public void setCommentId(long commentId) {
		this.commentId = commentId;
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

	@JsonProperty
	public long getCommentUserId() {
		return commentUserId;
	}

	public void setCommentUserId(long commentUserId) {
		this.commentUserId = commentUserId;
	}

}
