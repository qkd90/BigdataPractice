package com.hmlyinfo.app.soutu.scenic.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.google.common.collect.Maps;
import com.hmlyinfo.app.soutu.scenic.domain.RouteParam;
import com.hmlyinfo.app.soutu.scenic.domain.RouteResultBaidu;
import com.hmlyinfo.app.soutu.scenic.mapper.RouteResultBaiduMapper;
import com.hmlyinfo.base.util.HttpClientUtils;

public class RouteService {
	
	private static final String DRIVING_URL = "http://api.map.baidu.com/direction/v1?mode=driving&origin=#startLat,#startLng&destination=#endLat,#endLng&origin_region=#scity&destination_region=#ecity&output=json&ak=R9424rkP6oyCzex5FuLa7XIw";
	private static final String WALKING_URL = "http://api.map.baidu.com/direction/v1?mode=walking&origin=#startLat,#startLng&destination=#endLat,#endLng&origin_region=#scity&destination_region=#ecity&output=json&ak=R9424rkP6oyCzex5FuLa7XIw";
	private static final String BUS_URL = "http://api.map.baidu.com/direction/v1?mode=bus&origin=#startLat,#startLng&destination=#endLat,#endLng&origin_region=#scity&destination_region=#ecity&output=json&ak=R9424rkP6oyCzex5FuLa7XIw";
	
	
	private RouteResultBaiduMapper<RouteResultBaidu> brMapper;
	
	public RouteResultBaidu query(RouteParam rp)
	{
		Map<String, Object> paramMap = Maps.newHashMap();
		paramMap.put("startLat", rp.getStartLat());
		paramMap.put("startLng", rp.getStartLng());
		paramMap.put("startCitycode", rp.getStartCity().getId());
		paramMap.put("endLat", rp.getEndLat());
		paramMap.put("endLng", rp.getEndLng());
		paramMap.put("endCitycode", rp.getEndCity().getId());
		paramMap.put("travelType", rp.getTravelType());
		
		List<RouteResultBaidu> rList = brMapper.list(paramMap);
		// 如果没有交通线路信息，则通过百度API查询
		if (rList.size() == 0)
		{
			
		}
		
		return null;
	}
	
	private RouteResultBaidu queryWalk(RouteParam rp) throws ClientProtocolException, IOException
	{
		String url = WALKING_URL.replaceAll("#startLat", rp.getStartLat() + "")
								.replaceAll("#startLng", rp.getStartLng() + "")
								.replaceAll("#scity", rp.getStartCity().getName())
								.replaceAll("#endLat", rp.getEndLat() + "")
								.replaceAll("#endLng", rp.getEndLng() + "")
								.replaceAll("#ecity", rp.getEndCity().getName());
		
		String resultStr = HttpClientUtils.getHttp(url);
		return null;
	}
	
	private RouteResultBaidu queryDriving(double lat, double lng)
	{
		return null;
	}
	
	private RouteResultBaidu queryBus(double lat, double lng)
	{
		return null;
	}
}

