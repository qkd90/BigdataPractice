package com.hmlyinfo.app.soutu.hotel.domain;

import com.hmlyinfo.base.persistent.PageDto;

import java.util.Map;

public class HotelSearchParam {

	/**
	 * 按名称模糊查询
	 */
	private String hotelName;

	/**
	 * 城市编号
	 */
	private int cityCode;

	/**
	 * 距离
	 */
	private int distance;

	/**
	 * 开始记录数
	 */
	private int startIndex;

	/**
	 * 查询的记录条数
	 */
	private int length;

	/**
	 * 百度维度
	 */
	private double latitude;

	/**
	 * 百度经度
	 */
	private double longitude;

	/**
	 * 谷歌维度
	 */
	private double google_latitude;

	/**
	 * 谷歌经度
	 */
	private double google_longitude;

	/**
	 * 最低价格
	 */
	private double lowestPrice;

	/**
	 * 最高价格
	 */
	private double highestPrice;

	/**
	 * 排序字段
	 */
	private String orderColumn;

	/**
	 * 排序类型
	 */
	private String orderType;


	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public int getCityCode() {
		return cityCode;
	}

	public void setCityCode(int cityCode) {
		this.cityCode = cityCode;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getGoogle_latitude() {
		return google_latitude;
	}

	public void setGoogle_latitude(double google_latitude) {
		this.google_latitude = google_latitude;
	}

	public double getGoogle_longitude() {
		return google_longitude;
	}

	public void setGoogle_longitude(double google_longitude) {
		this.google_longitude = google_longitude;
	}

	public double getLowestPrice() {
		return lowestPrice;
	}

	public void setLowestPrice(double lowestPrice) {
		this.lowestPrice = lowestPrice;
	}

	public double getHighestPrice() {
		return highestPrice;
	}

	public void setHighestPrice(double highestPrice) {
		this.highestPrice = highestPrice;
	}

	public String getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	//从map转化为类
	public static HotelSearchParam fromMap(Map<String, Object> paramMap) {

		HotelSearchParam hotelSearchParam = new HotelSearchParam();
		if (paramMap.get("name") != null) {
			hotelSearchParam.setHotelName((String) paramMap.get("name"));
		}
		if (paramMap.get("hotelName") != null) {
			hotelSearchParam.setHotelName((String) paramMap.get("hotelName"));
		}
		if (paramMap.get("cityCode") != null) {
			hotelSearchParam.setCityCode(Integer.valueOf((String) paramMap.get("cityCode")));
		}
		if (paramMap.get("distance") != null) {
			hotelSearchParam.setDistance(Integer.parseInt((String) paramMap.get("distance")) / 1000);
		}
		if (paramMap.get("latitude") != null) {
			hotelSearchParam.setLatitude(Double.parseDouble((String) paramMap.get("latitude")));
		}
		if (paramMap.get("longitude") != null) {
			hotelSearchParam.setLongitude(Double.parseDouble((String) paramMap.get("longitude")));
		}
		if (paramMap.get("gcjLat") != null) {
			hotelSearchParam.setGoogle_latitude(Double.parseDouble((String) paramMap.get("gcjLat")));
		}
		if (paramMap.get("gcjLng") != null) {
			hotelSearchParam.setGoogle_longitude(Double.parseDouble((String) paramMap.get("gcjLng")));
		}
		if (paramMap.get("price") != null) {
			String priceString = (String) paramMap.get("price");
			String[] price = priceString.split(",");
			if (price.length < 2) {
				hotelSearchParam.setLowestPrice(Double.parseDouble(price[0]));
			} else {
				hotelSearchParam.setLowestPrice(Double.parseDouble(price[0]));
				hotelSearchParam.setHighestPrice(Double.parseDouble(price[1]));
			}
		}
		if (paramMap.get("orderColumn") != null) {
			hotelSearchParam.setOrderColumn((String) paramMap.get("orderColumn"));
		} else if (paramMap.get("latitude") != null || paramMap.get("gcjLat") != null) {
			hotelSearchParam.setOrderColumn("distance");
			hotelSearchParam.setOrderType("asc");
		}
		if (paramMap.get("orderType") != null) {
			hotelSearchParam.setOrderType((String) paramMap.get("orderType"));
		}

		PageDto pt = (PageDto) paramMap.get("pageDto");
		// 处理分页
		if (pt != null) {
			hotelSearchParam.setStartIndex(pt.getRowOffset());
			hotelSearchParam.setLength(pt.getRowLimit());
		}
		return hotelSearchParam;
	}
}
