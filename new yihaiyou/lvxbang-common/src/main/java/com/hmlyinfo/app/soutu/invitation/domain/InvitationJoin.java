package com.hmlyinfo.app.soutu.invitation.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class InvitationJoin extends BaseEntity {

	public static int NUMBER_TYPE_WEECHAT = 1;
	public static int NUMBER_TYPE_PHONE = 2;
	public static int NUMBER_TYPE_QQ = 3;
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 *
	 */
	private long invitationId;

	/**
	 *
	 */
	private long userId;

	/**
	 * 报名人姓名
	 */
	private String userName;

	/**
	 * 报名人性别
	 */
	private String userSex;

	/**
	 * 电话
	 */
	private String phone;

	/**
	 * 报名人数
	 */
	private int joinCounts;

	/**
	 * 备注信息
	 */
	private String remark;

	/**
	 * 出发日期
	 */
	private String addr;

	/**
	 * 号码类型
	 */
	private int numberType;

	/**
	 * 号码
	 */
	private String number;


	public void setInvitationId(long invitationId) {
		this.invitationId = invitationId;
	}

	@JsonProperty
	public long getInvitationId() {
		return invitationId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@JsonProperty
	public long getUserId() {
		return userId;
	}

	@JsonProperty
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@JsonProperty
	public String getUserSex() {
		return userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	@JsonProperty
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@JsonProperty
	public int getJoinCounts() {
		return joinCounts;
	}

	public void setJoinCounts(int joinCounts) {
		this.joinCounts = joinCounts;
	}

	@JsonProperty
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@JsonProperty
	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	@JsonProperty
	public int getNumberType() {
		return numberType;
	}

	public void setNumberType(int numberType) {
		this.numberType = numberType;
	}

	@JsonProperty
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
}
