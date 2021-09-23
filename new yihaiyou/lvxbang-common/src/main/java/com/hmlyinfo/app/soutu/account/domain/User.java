package com.hmlyinfo.app.soutu.account.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


public class User extends BaseEntity {

	public static final int STATUS_ACTIVE = 1;

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 昵称
	 */
	private String nickname;

	/**
	 * 性别
	 *
	 * @author annyColor
	 */
	public enum Sex {
		man("男", "man"), woman("女", "woman");
		private String name;
		private String value;

		private Sex(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public String toString() {
			return value;
		}

		public String getName() {
			return name;
		}
	}

	private String sex;

	/**
	 * 会员头像
	 */
	private String userface;


	/**
	 * 最后登录时间
	 */
	private Date loginTime;

	/**
	 * 最后登录IP
	 */
	private String loginIp;

	/**
	 * 审核状态
	 */
	private int status;

	/**
	 * 注册时间
	 */
	private Date applyTime;

	public enum Type {
		normal("正常", "1"), Thrid("第三方", "2");
		private String name;
		private String value;

		private Type(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public String toString() {
			return value;
		}

		public String getName() {
			return name;
		}
	}

	/**
	 * 用户注册类型
	 */
	private Type type;
	/**
	 * 生日
	 */
	private Date birthday;
	/**
	 * 家乡
	 */
	private String hometown;
	/**
	 * 所在地
	 */
	private String location;
	/**
	 * 职业
	 */
	private String profession;
	/**
	 * 爱好
	 */
	private String hobbies;

	/**
	 * 感情状况
	 * zero为占位符，保证有效枚举数据从1开始
	 */
	public int emotionStatus;
	/*
    public enum EmotionEnum {
        zere, single, married, divorced, inlove, secret;
    }
    private EmotionEnum emotionStatus;
    */
	/**
	 * 电话
	 */
	private String phone;
	/**
	 * 电话是否公开
	 */
	private String phonePublic;
	/**
	 * qq号码
	 */
	private String qq;
	/**
	 * 自我描述
	 */
	private String description;

	private int age;

	/**
	 * 用户最后访问系统的维度
	 */
	private double lat;

	/**
	 * 用户最后访问系统的经度
	 */
	private double lng;

	/**
	 * 地理位置修改时间
	 */
	private Date locModifyTime;

	/**
	 * 用户最后访问系统的所在地
	 */
	private String lastAddr;

	public void setUsername(String username) {
		this.username = username;
	}

	@JsonProperty
	public String getUsername() {
		return username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@JsonProperty
	public String getPassword() {
		return password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonProperty
	public String getEmail() {
		return email;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@JsonProperty
	public String getNickname() {
		return nickname;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	@JsonProperty
	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	@JsonProperty
	public String getLoginIp() {
		return loginIp;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@JsonProperty
	public int getStatus() {
		return status;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	@JsonProperty
	public Date getApplyTime() {
		return applyTime;
	}

	@JsonProperty
	public String getUserface() {
		return userface;
	}

	public void setUserface(String userface) {
		this.userface = userface;
	}

	@JsonProperty
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@JsonProperty
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	@JsonProperty
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@JsonProperty
	public String getHometown() {
		return hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	@JsonProperty
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@JsonProperty
	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	@JsonProperty
	public String getHobbies() {
		return hobbies;
	}

	public void setHobbies(String hobbies) {
		this.hobbies = hobbies;
	}

	@JsonProperty
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@JsonProperty
	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	@JsonProperty
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@JsonProperty
	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	@JsonProperty
	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	@JsonProperty
	public String getLastAddr() {
		return lastAddr;
	}

	public void setLastAddr(String lastAddr) {
		this.lastAddr = lastAddr;
	}

	@JsonProperty
	public Date getLocModifyTime() {
		return locModifyTime;
	}

	public void setLocModifyTime(Date locModifyTime) {
		this.locModifyTime = locModifyTime;
	}


	@JsonProperty
	public String getPhonePublic() {
		return phonePublic;
	}

	public void setPhonePublic(String phonePublic) {
		this.phonePublic = phonePublic;
	}

	@JsonProperty
	public int getEmotionStatus() {
		return emotionStatus;
	}

	public void setEmotionStatus(int emotionStatus) {
		this.emotionStatus = emotionStatus;
	}


}
