package com.hmlyinfo.app.soutu.hotel.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;
import java.util.Map;

public class CtripHotel extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private int cityCode;

	/**
	 *
	 */
	private String hotelName;

	/**
	 *
	 */
	private String addr;

	/**
	 *
	 */
	private String contact;

	/**
	 *
	 */
	private String imgUrl;

	private String coverSmall;

	/**
	 *
	 */
	private String service;

	/**
	 *
	 */
	private double latitude;

	/**
	 *
	 */
	private double longitude;

	/**
	 * 谷歌纬度
	 */
	private double googleLatitude;

	/**
	 * 谷歌经度
	 */
	private double googleLongitude;


	/**
	 *
	 */
	private double gcjLng;

	/**
	 *
	 */
	private double gcjLat;
	/**
	 *
	 */
	private long userId;

	/**
	 *
	 */
	private int score;

	/**
	 *
	 */
	private String hotelComment;

	/**
	 *
	 */
	private int commentNum;

	/**
	 *
	 */
	private double lowestHotelPrice;

	/**
	 *
	 */
	private String style;

	/**
	 *
	 */
	private int star;

	/**
	 *
	 */
	private String intro;

	/**
	 *
	 */
	private String openTime;

	/**
	 * 数据是否有效
	 */
	private String isValid;

	private transient String nickName;
	private transient String userface;
	private transient int distance;

	private int hotelRank;

	private transient List<Map<String, Object>> hotelRoomList;

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	@JsonProperty
	public String getHotelName() {
		return hotelName;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	@JsonProperty
	public String getAddr() {
		return addr;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@JsonProperty
	public String getContact() {
		return contact;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	@JsonProperty
	public String getImgUrl() {
		return imgUrl;
	}

	public void setService(String service) {
		this.service = service;
	}

	@JsonProperty
	public String getService() {
		return service;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	@JsonProperty
	public double getLatitude() {
		return latitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@JsonProperty
	public double getLongitude() {
		return longitude;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@JsonProperty
	public long getUserId() {
		return userId;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@JsonProperty
	public int getScore() {
		return score;
	}

	public void setHotelComment(String hotelComment) {
		this.hotelComment = hotelComment;
	}

	@JsonProperty
	public String getHotelComment() {
		return hotelComment;
	}

	@JsonProperty
	public int getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(int commentNum) {
		this.commentNum = commentNum;
	}

	public void setLowestHotelPrice(double lowestHotelPrice) {
		this.lowestHotelPrice = lowestHotelPrice;
	}

	@JsonProperty
	public double getLowestHotelPrice() {
		return lowestHotelPrice;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	@JsonProperty
	public String getStyle() {
		return style;
	}

	public void setStar(int star) {
		this.star = star;
	}

	@JsonProperty
	public int getStar() {
		return star;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	@JsonProperty
	public String getIntro() {
		return intro;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	@JsonProperty
	public String getOpenTime() {
		return openTime;
	}

	@JsonProperty
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@JsonProperty
	public String getUserface() {
		return userface;
	}

	public void setUserface(String userface) {
		this.userface = userface;
	}

	@JsonProperty
	public List<Map<String, Object>> getHotelRoomList() {
		return hotelRoomList;
	}

	public void setHotelRoomList(List<Map<String, Object>> hotelRoomList) {
		this.hotelRoomList = hotelRoomList;
	}

	@JsonProperty
	public int getCityCode() {
		return cityCode;
	}

	public void setCityCode(int cityCode) {
		this.cityCode = cityCode;
	}

	@JsonProperty
	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	@JsonProperty
	public int getHotelRank() {
		return hotelRank;
	}

	public void setHotelRank(int hotelRank) {
		this.hotelRank = hotelRank;
	}


	@JsonProperty
	public double getGoogleLatitude() {
		return googleLatitude;
	}

	public void setGoogleLatitude(double googleLatitude) {
		this.googleLatitude = googleLatitude;
	}

	@JsonProperty
	public double getGoogleLongitude() {
		return googleLongitude;
	}

	public void setGoogleLongitude(double googleLongitude) {
		this.googleLongitude = googleLongitude;
	}

	@JsonProperty
	public double getGcjLng() {
		return gcjLng;
	}

	public void setGcjLng(double gcjLng) {
		this.gcjLng = gcjLng;
	}

	@JsonProperty
	public double getGcjLat() {
		return gcjLat;
	}

	public void setGcjLat(double gcjLat) {
		this.gcjLat = gcjLat;
	}

	@JsonProperty
	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	@JsonProperty
	public String getCoverSmall() {
		return coverSmall;
	}

	public void setCoverSmall(String coverSmall) {
		this.coverSmall = coverSmall;
	}
}
