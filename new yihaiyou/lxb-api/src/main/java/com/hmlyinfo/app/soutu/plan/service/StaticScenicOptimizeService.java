package com.hmlyinfo.app.soutu.plan.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Multimap;
import com.hmlyinfo.app.soutu.plan.domain.type.TripType;
import com.hmlyinfo.app.soutu.plan.newAlg.ToolService;
import com.hmlyinfo.app.soutu.plan.newAlg.modal.Point;
import com.hmlyinfo.app.soutu.scenic.domain.City;
import com.hmlyinfo.app.soutu.scenic.service.CityService;

@Service
public class StaticScenicOptimizeService {

	@Autowired
	private OptimizeService		optimizeService;
	@Autowired
	private CityService			cityService;
	@Autowired
	private ToolService			toolService;
	@Autowired
	private DisService			disService;
	@Autowired
	private TspService			tspService;

	// 四种时间（0_只排一天，1_宽松，2_适中，3_紧凑）
	public final static int		HOUR_NONE		= 0;
	public final static int		HOUR_LOOSE		= 1;
	public final static int		HOUR_MEDIUM		= 2;
	public final static int		HOUR_COMPACT	= 3;
	// 四种时间上下限
	public final static int[][]	HOUR			= { { 420, 420 }, { 360, 480 }, { 480, 690 }, { 690, 960 } };

	@SuppressWarnings("unchecked")
	public List<List<Map<String, Object>>> scenicOptimize(List<Map<String, Object>> tripDetailList, List<Point> pointList, int hour) {

		for (int i = 0; i < pointList.size(); i++) {
			Point point = pointList.get(i);
			point.id = i;
		}

		Map<Integer, List<Point>> dayPointMap = staticScenicOptimize(tripDetailList, hour);

		Point hotelPoint = null;

		// TODO：酒店点的经纬度转换为什么是乘以11111？？？
		// 11111是从坐标系传唤为实际距离（单位：米）的比例
		for (Map<String, Object> planTrip : tripDetailList) { // 获取酒店信息
			if (Integer.valueOf(planTrip.get("tripType").toString()) == TripType.HOTEL.value()) {
				hotelPoint = new Point();
				hotelPoint.id = pointList.size();
				Map<String, Object> hotel = (Map<String, Object>) planTrip.get("hotel");
				hotelPoint.x = 11111 * (Double) hotel.get("longitude");
				hotelPoint.y = 11111 * (Double) hotel.get("latitude");
				break;
			}
		}
		// todo: 暂时不处理行程节省时间

		long hotelSym = -1;
		Map<Long, Map<String, Object>> tripMap = new HashMap<Long, Map<String, Object>>();
		for (Map<String, Object> tripDetail : tripDetailList) {
			if (Integer.valueOf(tripDetail.get("tripType").toString()) == TripType.HOTEL.value()) {
				tripMap.put(hotelSym, tripDetail);
			} else {
				tripMap.put(Long.valueOf(tripDetail.get("scenicId").toString()), tripDetail);
			}
		}

		// 处理每天数据
		List<List<Map<String, Object>>> optimizedTripList = new ArrayList<List<Map<String, Object>>>();
		for (Map.Entry<Integer, List<Point>> entry : dayPointMap.entrySet()) {
			//
			List<Point> dayPointList = entry.getValue();
			// List<Point> list = new ArrayList<Point>();
			// 景点个数不变
			// OptimizePlanService optimizePlanService = new
			// OptimizePlanService(dayPointList, hotelPoint);
			// optimizePlanService.init(dayPointList, hotelPoint);
			// list = optimizePlanService.rePlan();
			List<Point> list = tspService.tsp(dayPointList, hotelPoint);

			List<Map<String, Object>> tripList = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < list.size(); i++) {
				Point point = list.get(i);
				if (tripMap.get(point.scenicInfoId) != null) {
					tripList.add(tripMap.get(point.scenicInfoId));
				} else {
					tripList.add(tripMap.get(hotelSym));

				}

			}
			// optimizedTotalTime += getTotalTime(tripList);
			optimizedTripList.add(tripList);
		}

		return optimizedTripList;
	}

	// 不变化景点，增加或减少天数,在分区阶段处理
	@SuppressWarnings("unchecked")
	public Map<Integer, List<Point>> staticScenicOptimize(List<Map<String, Object>> tripDetailList, int hour) {
		int leftHour = HOUR[hour][0];
		int rightHour = HOUR[hour][1];

		// 获取城市信息
		int cityCode = 0;
		for (Map<String, Object> tripMap : tripDetailList) {
			if (Integer.valueOf(tripMap.get("tripType").toString()) == TripType.SCENIC.value()) {
				Map<String, Object> scenicInfo = (Map<String, Object>) tripMap.get("scenicInfo");
				cityCode = Integer.parseInt(scenicInfo.get("cityCode").toString());
				break;
			}
		}
		// 默认每两个景点之间需要30分钟
		int perTime = 30;
		List<City> cityList = cityService.list(Collections.<String, Object> singletonMap("cityCode", cityCode));
		if (cityList.isEmpty()) {
			perTime = cityList.get(0).getHour();
		}
		int totalTime = 0;
		for (Map<String, Object> tripMap : tripDetailList) {
			if (Integer.valueOf(tripMap.get("tripType").toString()) == TripType.SCENIC.value()) {
				Map<String, Object> scenicInfo = (Map<String, Object>) tripMap.get("scenicInfo");
				totalTime += Integer.parseInt(scenicInfo.get("adviceHours").toString());
			}
		}
		totalTime = totalTime + perTime * (tripDetailList.size() - 1);
		int days = totalTime / ((leftHour + rightHour) / 2);
		if (totalTime % ((leftHour + rightHour) / 2) != 0) {
			days += 1;
		}
		int avTime = HOUR[hour][1] + 1;
		days -= 1;
		while (avTime > HOUR[hour][1]) {
			days += 1;
			// 将行程节点转成对应的point用于计算
			List<Point> pointList = optimizeService.preparePoint(tripDetailList);

			Multimap<Long, Point> restorePoint = toolService.preDoPoint(pointList);
			Map<Integer, List<Point>> dayPointMap = new HashMap<Integer, List<Point>>();

			dayPointMap = optimizeService.parseDays(pointList, days); // 使用Kmeans对point进行拆分

			toolService.afterDoPoint(dayPointMap, restorePoint);

			// 对天数少于预定天数且景点数多于预定天数的重新规划
			dayPointMap = optimizeService.reOptimize(dayPointMap, restorePoint, days, tripDetailList.size());

			// 计算平均每天的游玩时间
			int totalPlayTime = 0;
			for (Integer key : dayPointMap.keySet()) {
				List<Point> points = dayPointMap.get(key);
				// 一天的总时间
				totalPlayTime += disService.listTime(points, DisService.MODE_TAXI);
			}
			avTime = totalPlayTime / days;
			// 满足条件
			if (avTime <= HOUR[hour][1]) {
				return dayPointMap;
			}
		}
		return null;

	}

}
