package com.hmlyinfo.app.soutu.plan.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Multimap;
import com.hmlyinfo.app.soutu.plan.domain.type.TripType;
import com.hmlyinfo.app.soutu.plan.newAlg.ToolService;
import com.hmlyinfo.app.soutu.plan.newAlg.modal.Point;
import com.hmlyinfo.app.soutu.scenic.domain.ScenicInfo;
import com.hmlyinfo.app.soutu.scenic.service.ScenicInfoService;

@Service
public class StaticDayOptimizeService {

	@Autowired
	private OptimizeService		optimizeService;
	@Autowired
	private ScenicInfoService	scenicInfoService;
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
	public List<List<Map<String, Object>>> dayOptimize(List<Map<String, Object>> tripDetailList, List<Point> pointList, int days, int hour) {

		// 统计当前所有景点的id
		Set<Long> scenicIdList = new HashSet<Long>();
		for (Point point : pointList) {
			scenicIdList.add(point.scenicInfoId);
		}

		// 天数不变，景点个数小于天数时，需要新增景点保证至少景点数和天数相等
		addPointBefore(pointList, scenicIdList, days, tripDetailList);
		// 实在没有景点可以添加的时候，只能改变天数
		if (pointList.size() < days) {
			days = pointList.size();
		}

		Multimap<Long, Point> restorePoint = toolService.preDoPoint(pointList);
		for (int i = 0; i < pointList.size(); i++) {
			Point point = pointList.get(i);
			point.id = i;
		}

		Map<Integer, List<Point>> dayPointMap = new HashMap<Integer, List<Point>>();

		if (days > 1) {
			dayPointMap = optimizeService.parseDays(pointList, days); // 使用Kmeans对point进行拆分
		} else {
			dayPointMap.put(1, pointList);
		}
		toolService.afterDoPoint(dayPointMap, restorePoint);

		// 对天数少于预定天数且景点数多于预定天数的重新规划
		dayPointMap = optimizeService.reOptimize(dayPointMap, restorePoint, days, tripDetailList.size());

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
			List<Point> list = new ArrayList<Point>();

			// 景点个数可以改变
			list = staticDaysOptimize(dayPointList, hotelPoint, hour, scenicIdList);

			List<Map<String, Object>> tripList = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < list.size(); i++) {
				Point point = list.get(i);
				if (tripMap.get(point.scenicInfoId) != null) {
					tripList.add(tripMap.get(point.scenicInfoId));
				} else {
					// 从景点表中取景点组成map然后插入（新增景点）
					Map<String, Object> newMap = scenicToMap(point, i);
					if (newMap == null) {
						tripList.add(tripMap.get(hotelSym));
					} else {
						tripList.add(newMap);
					}

				}

			}
			// optimizedTotalTime += getTotalTime(tripList);
			optimizedTripList.add(tripList);
		}

		return optimizedTripList;
	}

	// 当天数不变，且景点个数小于天数时，需要新增景点保证至少景点数和天数相等
	public void addPointBefore(List<Point> list, Set<Long> scenicIdList, int days, List<Map<String, Object>> tripDetailList) {
		if (list.size() >= days) {
			return;
		}
		int cityCode = 0;
		for (Point point : list) {
			cityCode = point.cityCode;
			if (cityCode != 0) {
				break;
			}
		}
		double avLng = 0;
		double avLat = 0;
		double maxDis = 20000;
		int leastTime = 6000;
		for (Point point : list) {
			avLng = avLng + point.x / 11111;
			avLat = avLat + point.y / 11111;
		}
		avLng = avLng / list.size();
		avLat = avLat / list.size();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("longitude", avLng);
		paramMap.put("latitude", avLat);
		paramMap.put("distance", maxDis);
		paramMap.put("adviceHours", leastTime);
		paramMap.put("cityCode", cityCode);
		// 获取满足条件的景点列表
		List<ScenicInfo> scenicInfos = scenicInfoService.addScennic(paramMap);
		// 需要添加的点数
		int addNum = days - list.size();
		int addedNum = 0;
		for (int i = 0; i < scenicInfos.size(); i++) {
			if (addedNum >= addNum) {
				break;
			}
			ScenicInfo scenicInfo = scenicInfos.get(i);

			long scenicId = scenicInfo.getId();
			if (scenicIdList.contains(scenicId)) {
				continue;
			}

			long scenicInfoId = scenicId;
			scenicIdList.add(scenicInfoId);
			long father = scenicInfo.getFather();
			int id = list.size();
			double x = scenicInfo.getLongitude() * 11111;
			double y = scenicInfo.getLatitude() * 11111;
			// 景点的游玩的时间
			int playHours = scenicInfo.getAdviceHours();
			Point point = new Point(id, x, y, playHours);
			point.scenicInfoId = scenicInfoId;
			point.father = father;
			point.cityCode = cityCode;
			list.add(point);
			// 对应的tripDetailList新建景点
			Map<String, Object> scenicMap = scenicToMap(point, tripDetailList.size());
			scenicMap.put("id", tripDetailList.size());
			tripDetailList.add(scenicMap);
			addedNum++;
		}
	}

	// 天数不变，景点个数可以改变
	public List<Point> staticDaysOptimize(List<Point> dayPointList, Point hotelPoint, int hour, Set<Long> scenicIdList) {
		int leftHour = HOUR[hour][0];
		int rightHour = HOUR[hour][1];

		// 前一次排序的结果，1代表增加了景点，-1代表减少了景点
		int beforeRes = 0;
		// 保存前一次排序的结果
		List<Point> lastPointList = new ArrayList<Point>();
		lastPointList.addAll(dayPointList);
		//
		while (true) {
			// OptimizePlanService optimizePlanService = new
			// OptimizePlanService(dayPointList, hotelPoint);
			// optimizePlanService.init(dayPointList, hotelPoint);
			// List<Point> list = optimizePlanService.rePlan();
			List<Point> list = tspService.tsp(dayPointList, hotelPoint);
			int listTime = disService.listTime(list, DisService.MODE_TAXI);
			if ((listTime >= leftHour && listTime <= rightHour)) {
				return list;
			} else {
				if (listTime < leftHour) {
					// 上次新增景点，这次还需要新增
					if (beforeRes >= 0) {
						// 记录本次结果
						lastPointList.clear();
						lastPointList.addAll(list);

						beforeRes = 1;
						Point newPoint = addPoint(list, scenicIdList, rightHour - listTime, hotelPoint);
						if (newPoint == null) {
							return list;
						}
						dayPointList.add(newPoint);
					} else {
						return lastPointList;
					}
				} else {
					// 游玩时间大于当前要求时间，但是只剩下一个景点所以不能删除
					if ((list.size() == 1) || (list.size() == 2 && hotelPoint != null)) {
						return list;
					}
					// 上次减少了景点，这次还需要减少
					if (beforeRes <= 0) {
						// 记录本次结果
						lastPointList.clear();
						lastPointList.addAll(list);

						List<Point> interimList = new ArrayList<Point>();
						interimList.addAll(list);
						if (hotelPoint != null) {
							interimList.remove(0);
						}

						beforeRes = -1;
						// 继续减少景点
						dayPointList.clear();
						dayPointList = delPoint(interimList);
					} else {
						return list;
					}
				}

			}
		}
	}

	// 把新加景点包装成和结果列表中相同结构
	@SuppressWarnings("unchecked")
	public Map<String, Object> scenicToMap(Point point, int i) {
		long scenicId = point.scenicInfoId;
		Map<String, Object> scenicInfo = (Map<String, Object>) scenicInfoService.info(scenicId);
		if (scenicInfo == null) {
			return null;
		}
		Map<String, Object> planTripMap = new HashMap<String, Object>();
		planTripMap.put("scenicId", scenicId);
		planTripMap.put("tripType", TripType.SCENIC.value());
		planTripMap.put("day", point.zoneId);
		planTripMap.put("orderNum", i);
		planTripMap.put("scenicInfo", scenicInfo);
		return planTripMap;
	}

	// 增加景点
	public Point addPoint(List<Point> pointList, Set<Long> scenicIdList, int leastTime, Point hotelPoint) {
		int cityCode = 0;
		for (Point point : pointList) {
			cityCode = point.cityCode;
			if (cityCode != 0) {
				break;
			}
		}
		double avLng = 0;
		double avLat = 0;
		double maxDis = 0;
		int startIndex = 0;
		if (hotelPoint != null) {
			startIndex = 1;
		}
		for (int i = startIndex; i < pointList.size(); i++) {
			Point point = pointList.get(i);
			if (point.distance > maxDis) {
				maxDis = point.distance;
			}
			avLng = avLng + point.x / 11111;
			avLat = avLat + point.y / 11111;
		}
		if (hotelPoint != null) {
			avLng = avLng / (pointList.size() - 1);
			avLat = avLat / (pointList.size() - 1);
		} else {
			avLng = avLng / pointList.size();
			avLat = avLat / pointList.size();
		}

		maxDis *= 1.2;
		if ((pointList.size() == 1) || (hotelPoint != null && pointList.size() == 2)) {
			maxDis = 20000;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("longitude", avLng);
		paramMap.put("latitude", avLat);
		paramMap.put("distance", maxDis);
		paramMap.put("adviceHours", leastTime);
		paramMap.put("cityCode", cityCode);
		// 插入城市条件
		// 获取满足条件的景点列表
		List<ScenicInfo> scenicInfos = scenicInfoService.addScennic(paramMap);
		if (scenicInfos.size() == 0) {
			return null;
		} else {
			// 排除行程中已经存在的景点
			for (int i = 0; i < scenicInfos.size(); i++) {
				ScenicInfo scenicInfo = scenicInfos.get(i);
				long scenicId = scenicInfo.getId();
				if (scenicIdList.contains(scenicId)) {
					continue;
				}

				long scenicInfoId = scenicId;
				scenicIdList.add(scenicInfoId);
				long father = scenicInfo.getFather();
				// 用-1标记新增景点
				long id = -1;
				double x = scenicInfo.getLongitude() * 11111;
				double y = scenicInfo.getLatitude() * 11111;
				// 该景点所归属的旅游天数
				int zoneId = pointList.get(0).zoneId;
				// 该点与最近的片区的几何中心的距离
				double distance = scenicInfo.getDistance();
				// 景点的游玩的时间
				int playHours = scenicInfo.getAdviceHours();

				Point point = new Point(id, x, y, zoneId, distance);
				point.scenicInfoId = scenicInfoId;
				point.father = father;
				point.playHours = playHours;
				return point;
			}
		}
		return null;
	}

	// 减少景点
	public List<Point> delPoint(List<Point> pointList) {
		int index = 0;
		int orderNum = pointList.get(0).orderNum;
		for (int i = 1; i < pointList.size(); i++) {
			Point point = pointList.get(i);
			if (point.orderNum > orderNum) {
				orderNum = point.orderNum;
				index = i;
			}
		}
		pointList.remove(index);
		return pointList;
	}

}
