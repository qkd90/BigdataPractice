package com.hmlyinfo.app.soutu.scenic.domain;

import com.hmlyinfo.base.persistent.BaseEntity;

public class CommentGood extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 对应comment的userId
	 */
	private long commentUserId;
	/**
	 * 评论id
	 */
	private long commentId;

	/**
	 * 用户id
	 */
	private long userId;


	public void setCommentId(long commentId) {
		this.commentId = commentId;
	}

	public long getCommentId() {
		return commentId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getUserId() {
		return userId;
	}

	public long getCommentUserId() {
		return commentUserId;
	}

	public void setCommentUserId(long commentUserId) {
		this.commentUserId = commentUserId;
	}

}
