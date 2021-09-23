package com.hmlyinfo.app.soutu.common.service;

import com.hmlyinfo.app.soutu.hotel.domain.HotelSearchParam;
import com.hmlyinfo.app.soutu.scenic.domain.ScenicSearchParam;

import java.util.List;
import java.util.Map;

public interface ISearchService {
	/**
	 * 搜索景点列表
	 *
	 * @param scenicName 景点名称
	 * @param cityCode   城市编号
	 * @return
	 */
	List<Map<String, Object>> searchScenicByName(ScenicSearchParam scenicSearchParam);

	/**
	 * 搜索景点数量
	 *
	 * @param scenicName 景点名称
	 * @param cityCode   城市编号
	 * @return
	 */
	int countScenicByName(ScenicSearchParam scenicSearchParam);

	/**
	 * 根据酒店名称搜索酒店列表
	 *
	 * @param hotelName 酒店名称
	 * @param cityCode  城市编号
	 * @param start     开始位置
	 * @param end       结束位置
	 * @return
	 */
	List<Map<String, Object>> searchHotelByName(HotelSearchParam searchParam);

	/**
	 * 根据酒店名称统计酒店数量
	 *
	 * @param hotelName 酒店名称
	 * @param cityCode  城市编号
	 * @param start     开始位置
	 * @param end       结束位置
	 * @return
	 */
	int countHotelByName(HotelSearchParam searchParam);


}
