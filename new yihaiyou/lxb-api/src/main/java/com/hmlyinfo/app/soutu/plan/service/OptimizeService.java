package com.hmlyinfo.app.soutu.plan.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Multimap;
import com.hmlyinfo.app.soutu.delicacy.service.RestaurantService;
import com.hmlyinfo.app.soutu.hotel.service.CtripHotelService;
import com.hmlyinfo.app.soutu.plan.domain.type.TripType;
import com.hmlyinfo.app.soutu.plan.newAlg.ToolService;
import com.hmlyinfo.app.soutu.plan.newAlg.modal.Point;
import com.hmlyinfo.app.soutu.scenic.service.CityService;
import com.hmlyinfo.app.soutu.scenic.service.ScenicInfoService;
import com.hmlyinfo.base.util.HttpUtil;

/**
 * Created by guoshijie on 2014/12/11.
 */
@SuppressWarnings({ "unchecked", "unchecked" })
@Service
public class OptimizeService {

	@Autowired
	PlanTripService						planTripService;
	@Autowired
	ScenicInfoService					scenicInfoService;
	@Autowired
	RestaurantService					restaurantService;
	@Autowired
	PlanDaysService						planDaysService;
	@Autowired
	TransTimeService					transTimeService;
	@Autowired
	CtripHotelService					hotelService;
	@Autowired
	private CityService					cityService;
	@Autowired
	private StaticDayOptimizeService	staticDayOptimizeService;
	@Autowired
	private StaticScenicOptimizeService	staticScenicOptimizeService;
	@Autowired
	private KmeansService				kmeansService;
	@Autowired
	private ToolService					toolService;
	@Autowired
	private TspService					tspService;

	// 三种类型（0_只排一天，1_景点个数不变改变天数，2_天数不变可以改变景点数量）
	public final static int				TYPE_STATIC_ALL		= 0;
	public final static int				TYPE_STATIC_SCENIC	= 1;
	public final static int				TYPE_STATIC_DAY		= 2;
	// 四种时间（0_只排一天，1_宽松，2_适中，3_紧凑）
	public final static int				HOUR_NONE			= 0;
	public final static int				HOUR_LOOSE			= 1;
	public final static int				HOUR_MEDIUM			= 2;
	public final static int				HOUR_COMPACT		= 3;
	// 四种时间上下限
	public final static int[][]			HOUR				= { { 420, 420 }, { 360, 480 }, { 480, 690 }, { 690, 960 } };
	// 速度
	private final static int			SPEED				= 5000;

	public List<List<Map<String, Object>>> optimize(List<Map<String, Object>> tripDetailList, int days, int hour, int type) {

		List<Point> pointList = preparePoint(tripDetailList); // 将行程节点转成对应的point用于计算

		if (type == TYPE_STATIC_DAY) {
			// 天数不变
			return staticDayOptimizeService.dayOptimize(tripDetailList, pointList, days, hour);
		} else if (type == TYPE_STATIC_SCENIC) {
			// 景点个数不变
			return staticScenicOptimizeService.scenicOptimize(tripDetailList, pointList, hour);
		}

		Multimap<Long, Point> restorePoint = toolService.preDoPoint(pointList);
		for (int i = 0; i < pointList.size(); i++) {
			Point point = pointList.get(i);
			point.id = i;
		}

		Map<Integer, List<Point>> dayPointMap = new HashMap<Integer, List<Point>>();

		if (days > 1) {
			// 使用Kmeans对point进行拆分
			dayPointMap = parseDays(pointList, days);
		} else {
			dayPointMap.put(1, pointList);
		}
		toolService.afterDoPoint(dayPointMap, restorePoint);

		// 对天数少于预定天数且景点数多于预定天数的重新规划
		dayPointMap = reOptimize(dayPointMap, restorePoint, days, tripDetailList.size());

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
			// OptimizePlanService optimizePlanService = new
			// OptimizePlanService(entry.getValue(), hotelPoint);
			// optimizePlanService.init(entry.getValue(), hotelPoint);
			// List<Point> list = optimizePlanService.rePlan();

			List<Point> list = tspService.tsp(entry.getValue(), hotelPoint);

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

	public long getTotalTime(List<Map<String, Object>> planTripList) {
		int totalTime = 0;
		for (int i = 0; i < planTripList.size() - 1; i++) {
			String startPosition = getPositionStr(planTripList.get(i));
			String endPosition = getPositionStr(planTripList.get(i + 1));
			totalTime += getTransTime(startPosition, endPosition);
		}
		return totalTime;
	}

	String getPositionStr(Map<String, Object> planTrip) {
		String positionStr;

		if ((Integer) planTrip.get("tripType") == TripType.RESTAURANT.value()) {
			Map<String, Object> food = (Map<String, Object>) planTrip.get("food");
			positionStr = food.get("resLatitude") + "," + food.get("resLongitude");
		} else if ((Integer) planTrip.get("tripType") == TripType.HOTEL.value()) {
			Map<String, Object> hotel = (Map<String, Object>) planTrip.get("hotel");
			positionStr = hotel.get("latitude") + "," + hotel.get("longitude");
		} else {
			Map<String, Object> scenicInfo = (Map<String, Object>) planTrip.get("scenicInfo");
			positionStr = scenicInfo.get("latitude") + "," + scenicInfo.get("longitude");
		}
		return positionStr;
	}

	@SuppressWarnings("unchecked")
	int getTransTime(String startPosition, String endPosition) {
		int seconds = 0;
		try {
			String url = "http://api.map.baidu.com/direction/v1?origin_region=&destination_region=&" + "origin=" + startPosition
					+ "&destination=" + endPosition + "&output=json&ak=R9424rkP6oyCzex5FuLa7XIw&mode=driving";
			String result = HttpUtil.getWithTimeout(url, 1000);
			Map<String, Object> resultMap = new ObjectMapper().readValue(result, Map.class);
			seconds = Integer.valueOf(((List<Map>) ((Map) resultMap.get("result")).get("routes")).get(0).get("duration").toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return seconds;
	}

	public Map<Integer, List<Point>> parseDays(List<Point> pointList, int days) {

		int newDay = days;
		if (pointList.size() < days) {
			newDay = pointList.size();
		}
		// 生成聚类中心
		List<Point> center = kmeansService.generateCenter(pointList, newDay);

		// 迭代处理
		kmeansService.iteration(pointList, center, newDay);

		kmeansService.getZoneCase(pointList, center, newDay);

		// System .out.println("分区之后");
		for (int i = 0; i < pointList.size(); i++) {
			Point point = pointList.get(i);
			// System
			// .out.println("景点的id="+point.id+"\tx="+point.x+"\ty="+point.y+"\tzoneId="+point.zoneId+"\tdistance="+point.distance+"\tplayTime="+point.playHours);
		}
		Map<Integer, List<Point>> dayTripMap = new HashMap<Integer, List<Point>>();
		for (Point point : pointList) {
			List<Point> tripList = dayTripMap.get(point.zoneId);
			if (tripList == null) {
				tripList = new ArrayList<Point>();
			}
			tripList.add(point);
			dayTripMap.put(point.zoneId, tripList);
		}
		return dayTripMap;
	}

	List<Point> preparePoint(List<Map<String, Object>> planTripList) {
		int distance = 11111;
		List<Point> list = new ArrayList<Point>();

		long i = 0;

		DecimalFormat fnum = new DecimalFormat("#.##"); // 格式化取坐标百分位

		for (Map<String, Object> result : planTripList) {
			Point point = new Point();

			if ((Integer) result.get("tripType") == TripType.SCENIC.value()) {
				point.id = i;
				i++;
				Map<String, Object> scenicInfo = (Map<String, Object>) result.get("scenicInfo");
				// 拿出经纬度 //System
				// .out.println("第"+point.id+"个经纬度\t经度是"+Double.parseDouble(scenicInfo.get("longitude").toString())+""
				// + "\t纬度是"+
				// Double.parseDouble(scenicInfo.get("latitude").toString()));

				point.x = distance * Double.parseDouble(scenicInfo.get("longitude").toString());
				point.x = Double.parseDouble(fnum.format(point.x));
				// point.x = distance *
				// Double.parseDouble(scenicInfo.get("longitude").toString()) *
				// Math.cos(Double.parseDouble(scenicInfo.get("latitude").toString()));
				point.y = distance * Double.parseDouble(scenicInfo.get("latitude").toString());
				point.y = Double.parseDouble(fnum.format(point.y));
				point.playHours = Integer.parseInt(scenicInfo.get("adviceHours").toString());

				point.scenicInfoId = Long.parseLong(scenicInfo.get("id").toString());
				point.father = Long.parseLong(scenicInfo.get("father").toString());
				point.orderNum = Integer.parseInt(scenicInfo.get("orderNum").toString());
				point.cityCode = Integer.parseInt(scenicInfo.get("cityCode").toString());
				list.add(point);
			} else if ((Integer) result.get("tripType") == TripType.RESTAURANT.value()) {
				point.id = i;
				i++;
				Map<String, Object> food = (Map<String, Object>) result.get("food");
				point.x = distance * Double.parseDouble(food.get("resLongitude").toString());
				point.x = Double.parseDouble(fnum.format(point.x));
				// point.x = distance *
				// Double.parseDouble(food.get("resLongitude").toString()) *
				// Math.cos(Double.parseDouble(food.get("resLatitude").toString()));
				point.y = distance * Double.parseDouble(food.get("resLatitude").toString());
				point.y = Double.parseDouble(fnum.format(point.y));

				point.playHours = 60;
				point.scenicInfoId = Long.parseLong(food.get("id").toString());
				point.father = 0;
				point.orderNum = 1;
				point.cityCode = Integer.parseInt(food.get("cityCode").toString());

				list.add(point);
			}

		}
		return list;
	}

	public void optimize() {

	}

	// 对天数少于预定天数且景点数多于预定天数的重新规划
	public Map<Integer, List<Point>> reOptimize(Map<Integer, List<Point>> dayPointMap, Multimap<Long, Point> restorePoint, int days,
			int scenicSize) {
		// 判断是否满足规划条件
		while (dayPointMap.size() < days && scenicSize >= days) {
			int maxSixe = 0;
			int key = 0;
			int remainDays = days - dayPointMap.size() + 1;
			// 遍历旧的分组找出景点数最多的一组
			for (Map.Entry<Integer, List<Point>> kentity : dayPointMap.entrySet()) {
				List<Point> dpList = kentity.getValue();
				if (dpList.size() > maxSixe) {
					maxSixe = dpList.size();
					key = kentity.getKey();
				}
			}
			List<Point> largestList = dayPointMap.get(key);
			for (int i = 0; i < largestList.size(); i++) {
				Point point = largestList.get(i);
				point.id = i;
				point.distance = 0;
				point.zoneId = 0;
			}
			// 景点数最多的一组的新规划
			Map<Integer, List<Point>> remainDayPointMap = parseDays(largestList, remainDays);
			Map<Integer, List<Point>> newDayPointMap = new HashMap<Integer, List<Point>>();
			int index = 0;
			// 把新的规划与旧的整合
			for (Map.Entry<Integer, List<Point>> kentity : dayPointMap.entrySet()) {
				List<Point> dpList = kentity.getValue();
				if (kentity.getKey() == key) {
					for (Map.Entry<Integer, List<Point>> remainKentity : remainDayPointMap.entrySet()) {
						newDayPointMap.put(index, remainKentity.getValue());
						index++;
					}
					continue;
				}
				newDayPointMap.put(index, kentity.getValue());
				index++;
			}
			dayPointMap.clear();
			dayPointMap = newDayPointMap;
		}
		return dayPointMap;
	}

	// // 添加景点
	// private ScenicInfo addScecnic(List<Point> pointList)
	// {
	// Map<String, Object>
	// }

}
