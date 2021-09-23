package com.hmlyinfo.app.soutu.scenic.domain;

import com.hmlyinfo.base.persistent.PageDto;

import java.util.List;
import java.util.Map;

public class ScenicSearchParam {

	/**
	 * 按名称模糊查询
	 */
	private String name;

	/**
	 * 城市编号
	 */
	private int cityCode;

	/**
	 * 城市编号列表
	 */
	private List<Object> cityCodes;

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
	 * 百度纬度
	 */
	private double latitude;

	/**
	 * 百度经度
	 */
	private double longitude;

	/**
	 * 谷歌纬度
	 */
	private double gcjLat;

	/**
	 * 谷歌经度
	 */
	private double gcjLng;

	/**
	 * 是否是车站
	 */
	private int isStation;

	/**
	 * 是否是城市
	 */
	private int isCity;


	/**
	 * 排序字段
	 */
	private String orderColumn;

	/**
	 * 排序类型
	 */
	private String orderType;

	/**
	 * 父景点是否为三级景区
	 */
	private String parentsThreeLevelRegion;

	/**
	 * id列表
	 */
	private List<Long> ids;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public double getGcjLat() {
		return gcjLat;
	}

	public void setGcjLat(double gcjLat) {
		this.gcjLat = gcjLat;
	}

	public double getGcjLng() {
		return gcjLng;
	}

	public void setGcjLng(double gcjLng) {
		this.gcjLng = gcjLng;
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

	public List<Object> getCityCodes() {
		return cityCodes;
	}

	public void setCityCodes(List<Object> cityCodes) {
		this.cityCodes = cityCodes;
	}

	public int getIsStation() {
		return isStation;
	}

	public void setIsStation(int isStation) {
		this.isStation = isStation;
	}

	public int getIsCity() {
		return isCity;
	}

	public void setIsCity(int isCity) {
		this.isCity = isCity;
	}

	public String getParentsThreeLevelRegion() {
		return parentsThreeLevelRegion;
	}

	public void setParentsThreeLevelRegion(String parentsThreeLevelRegion) {
		this.parentsThreeLevelRegion = parentsThreeLevelRegion;
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	//从map转化为类
	public static ScenicSearchParam fromMap(Map<String, Object> paramMap) {

		ScenicSearchParam scenicSearchParam = new ScenicSearchParam();
		if (paramMap.get("name") != null) {
			scenicSearchParam.setName((String) paramMap.get("name"));
		}
		if (paramMap.get("cityCode") != null) {
			scenicSearchParam.setCityCode(Integer.parseInt(paramMap.get("cityCode").toString()));
		}
		if (paramMap.get("cityCodes") != null) {
			scenicSearchParam.setCityCodes((List<Object>) paramMap.get("cityCodes"));
		}
		if (paramMap.get("distance") != null) {
			scenicSearchParam.setDistance(Integer.parseInt((String) paramMap.get("distance")) / 1000);
		}
		if (paramMap.get("latitude") != null) {
			scenicSearchParam.setLatitude(Double.parseDouble((String) paramMap.get("latitude")));
		}
		if (paramMap.get("longitude") != null) {
			scenicSearchParam.setLongitude(Double.parseDouble((String) paramMap.get("longitude")));
		}
		if (paramMap.get("gcjLat") != null) {
			scenicSearchParam.setGcjLat(Double.parseDouble((String) paramMap.get("gcjLat")));
		}
		if (paramMap.get("gcjLng") != null) {
			scenicSearchParam.setGcjLng(Double.parseDouble((String) paramMap.get("gcjLng")));
		}
		if (paramMap.get("isCity") != null) {
			scenicSearchParam.setIsCity(Integer.parseInt((String) paramMap.get("isCity")));
		}
		if (paramMap.get("isStation") != null) {
			scenicSearchParam.setIsStation(Integer.parseInt((String) paramMap.get("isStation")));
		}
		if (paramMap.get("parentsThreeLevelRegion") != null) {
			scenicSearchParam.setParentsThreeLevelRegion(paramMap.get("parentsThreeLevelRegion").toString());
		}
		if (paramMap.get("ids") != null) {
			scenicSearchParam.setIds((List<Long>) paramMap.get("ids"));
		}

		PageDto pt = (PageDto) paramMap.get("pageDto");
		// 处理分页
		if (pt != null) {
			scenicSearchParam.setStartIndex(pt.getRowOffset());
			scenicSearchParam.setLength(pt.getRowLimit());
		}

		// 以下用来处理排序
		String orderColumn = "";
		String orderType = "";
		// 按距离排序
		if (paramMap.get("distanceFlag") != null) {
			orderColumn = "distance";
			orderType = "asc";
		}

		if (paramMap.get("orderColumn") != null) {
			String orderString = paramMap.get("orderColumn").toString();
			if (orderString.equals("ticket")) {
				orderColumn = "RANK";
				orderType = paramMap.get("orderType").toString();
			} else {
				orderColumn = orderString;
				orderType = paramMap.get("orderType").toString();
			}
		}

		if (orderColumn.equals("")) {
			orderColumn = "order_num";
			orderType = "asc";
		}
		scenicSearchParam.setOrderColumn(orderColumn);
		scenicSearchParam.setOrderType(orderType);

		return scenicSearchParam;
	}


}
