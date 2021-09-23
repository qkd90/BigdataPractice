package com.hmlyinfo.app.soutu.invitation.domain;

import com.hmlyinfo.app.soutu.account.domain.User;
import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;
import java.util.List;

public class Invitation extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 *
	 */
	private long userId;

	/**
	 *
	 */
	private long planId;

	/**
	 *
	 */
	private int planDays;

	/**
	 *
	 */
	private String intro;

	/**
	 *
	 */
	private String tel;

	/**
	 *
	 */
	private boolean telFlag;

	/**
	 *
	 */
	private Date startDate;

	/**
	 *
	 */
	private String addr;

	private double lat;

	private double lng;

	/**
	 * 发帖城市
	 */
	private String pubCitycode;

	/**
	 *
	 */
	private String authorSex;

	/**
	 *
	 */
	private String citys;

	/**
	 *
	 */
	private int likeCounts;

	/**
	 *
	 */
	private int commentCounts;

	/**
	 *
	 */
	private int joinCounts;

	/**
	 * 类型
	 */
	private int type;

	/**
	 * 结伴贴作者
	 */
	private transient User author;

	private transient List<InvitationImg> imgList;

	private transient Date endTime;

	/**
	 * 城市代码
	 */
	private int cityCode;


	public void setUserId(long userId) {
		this.userId = userId;
	}

	@JsonProperty
	public long getUserId() {
		return userId;
	}

	public void setPlanId(long planId) {
		this.planId = planId;
	}

	@JsonProperty
	public long getPlanId() {
		return planId;
	}

	public void setPlanDays(int planDays) {
		this.planDays = planDays;
	}

	@JsonProperty
	public int getPlanDays() {
		return planDays;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	@JsonProperty
	public String getIntro() {
		return intro;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@JsonProperty
	public String getTel() {
		return tel;
	}

	public void setTelFlag(boolean telFlag) {
		this.telFlag = telFlag;
	}

	@JsonProperty
	public boolean getTelFlag() {
		return telFlag;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@JsonProperty
	public Date getStartDate() {
		return startDate;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	@JsonProperty
	public String getAddr() {
		return addr;
	}

	public void setAuthorSex(String authorSex) {
		this.authorSex = authorSex;
	}

	@JsonProperty
	public String getAuthorSex() {
		return authorSex;
	}

	public void setCitys(String citys) {
		this.citys = citys;
	}

	@JsonProperty
	public String getCitys() {
		return citys;
	}

	public void setLikeCounts(int likeCounts) {
		this.likeCounts = likeCounts;
	}

	@JsonProperty
	public int getLikeCounts() {
		return likeCounts;
	}

	public void setCommentCounts(int commentCounts) {
		this.commentCounts = commentCounts;
	}

	@JsonProperty
	public int getCommentCounts() {
		return commentCounts;
	}

	public void setJoinCounts(int joinCounts) {
		this.joinCounts = joinCounts;
	}

	@JsonProperty
	public int getJoinCounts() {
		return joinCounts;
	}

	@JsonProperty
	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	@JsonProperty
	public List<InvitationImg> getImgList() {
		return imgList;
	}

	public void setImgList(List<InvitationImg> imgList) {
		this.imgList = imgList;
	}

	@JsonProperty
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@JsonProperty
	public int getCityCode() {
		return cityCode;
	}

	public void setCityCode(int cityCode) {
		this.cityCode = cityCode;
	}

	@JsonProperty
	public String getPubCitycode() {
		return pubCitycode;
	}

	public void setPubCitycode(String pubCitycode) {
		this.pubCitycode = pubCitycode;
	}


}
