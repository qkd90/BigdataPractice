package com.data.data.service.optimize.process;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.data.data.service.AreaService;
import com.data.data.service.optimize.PlanHourType;
import com.data.data.service.optimize.PlanOptimizeType;
import com.data.data.service.optimize.process.plan.PlanScenic;
import com.data.data.service.pojo.Area;
import com.data.data.service.pojo.Dis;
import com.hmlyinfo.app.soutu.plan.domain.PlanTrip;
import com.hmlyinfo.app.soutu.plan.domain.type.TripType;
import com.hmlyinfo.app.soutu.plan.newAlg.modal.Point;

public abstract class OptimizeProcess {
	protected final static Integer								maxDayScenic	= 4;
	/**
	 * 旧行程
	 */
	protected List<Map<String, Object>>							oldPlanTrips;
	/**
	 * 优化后行程
	 */
	protected Map<String, List<PlanTrip>>						optimzePlanTrips;

	/**
	 * 用户定义的时间类型
	 */
	protected PlanHourType										planHourType;
	/**
	 * 每个城市的时间花费
	 */
	protected Map<String, Integer>								cityDayCost		= new HashMap<String, Integer>();
	/**
	 * 用户选择的优化类型
	 */
	protected PlanOptimizeType									planOptimizeType;
	private final static String									scenicInfoKey	= "scenicInfo";
	private final static String									foodKey			= "food";
	private final static String									tripTypeKey		= "tripType";
	private final static String									hotelKey		= "hotel";
	protected int												days;
	/**
	 * 被移除的景点
	 */
	protected List<Point>										removedPoints;
	/**
	 * 添加的景点
	 */
	protected List<Point>										addedPoints;
	private final Map<String, Map<Integer, LinkedList<Point>>>	resultPlans		= new HashMap<String, Map<Integer, LinkedList<Point>>>();

	public OptimizeProcess(PlanHourType planHourType, PlanOptimizeType planOptimizeType, List<Map<String, Object>> oldPlanTrips, int day) {
		// TODO Auto-generated constructor stub
		this.planHourType = planHourType;
		this.planOptimizeType = planOptimizeType;
		this.oldPlanTrips = oldPlanTrips;
		days = day;
	}

	public static OptimizeProcess initOptimizeProcess(PlanHourType planHourType, PlanOptimizeType planOptimizeType,
			List<Map<String, Object>> oldPlanTrips, int days) {
		OptimizeProcess optimizeProcess = null;
		if (PlanOptimizeType.OPTIMIZE_DAY_CONFIRM == planOptimizeType) {
			optimizeProcess = new DayConfirmProcess(planHourType, planOptimizeType, oldPlanTrips, days);
		} else if (PlanOptimizeType.OPTIMIZE_POINT_DAY == planOptimizeType) {
			optimizeProcess = new PointDayProcess(planHourType, planOptimizeType, oldPlanTrips, days);
		} else if (PlanOptimizeType.OPTIMIZE_SCENIC_CONFIRM == planOptimizeType) {
			optimizeProcess = new ScenifConfirmProcess(planHourType, planOptimizeType, oldPlanTrips, days);
		}
		optimizeProcess.optimize();
		return optimizeProcess;
	}

	public void optimize() {
		Map<String, Map<Integer, List<Point>>> maps = spileScenicInfo();
		countCityDayDispatch(maps);
		for (Entry<String, Map<Integer, List<Point>>> cityPlanTrip : maps.entrySet()) {
			Map<Integer, LinkedList<Point>> resultPlan = optimizeCityPlans(cityPlanTrip);
			resultPlans.put(cityPlanTrip.getKey(), resultPlan);
		}
	}

	/**
	 * 计算每个城市的时间花费
	 * 
	 * @param maps
	 */
	private void countCityDayDispatch(Map<String, Map<Integer, List<Point>>> maps) {
		for (Entry<String, Map<Integer, List<Point>>> cityPlans : maps.entrySet()) {
			int day = countPlaysTimeCost(cityPlans);
			cityDayCost.put(cityPlans.getKey(), day);
		}
		reDispatchCityCost();
	}

	private void reDispatchCityCost() {
		// TODO Auto-generated method stub
		int totalCost = 0;
		Map<String, Integer> cityCost = new HashMap<String, Integer>();
		int days = this.days;
		if (this.days < cityDayCost.size()) {// 天数小于城市数
			for (Entry<String, Integer> day : cityDayCost.entrySet()) {
				cityCost.put(day.getKey(), days-- <= 0 ? 1 : 0);
			}
		} else {// 天数大于城市数，按比例进行时间分配，不足的加1天，
			for (Entry<String, Integer> entry : cityDayCost.entrySet()) {
				totalCost += entry.getValue();
			}
			float avgCost = (float) totalCost / cityDayCost.size();
			Map<String, Integer> gtAvgCostRate = new HashMap<String, Integer>();
			int useDays = 0;
			for (Entry<String, Integer> entry : cityDayCost.entrySet()) {
				// float rate = (float) entry.getValue() / totalCost;
				if (entry.getValue() < avgCost) {
					useDays += 1;
					cityCost.put(entry.getKey(), 1);
				} else {
					gtAvgCostRate.put(entry.getKey(), entry.getValue());
				}
			}
			int left = this.days - useDays;
			if (left > 0) {
				do {
					for (Entry<String, Integer> entry : gtAvgCostRate.entrySet()) {
						int day = Math.round(entry.getValue() / avgCost);
						day = day == 0 ? 1 : day;
						int min = Math.min(left, day);
						String key = entry.getKey();
						if (cityCost.containsKey(key)) {
							cityCost.put(key, cityCost.get(key) + min);
						} else {
							cityCost.put(key, min);
						}
						left -= min;
						if (left <= 0) {
							break;
						}
					}
				} while (left > 0);
			}

		}
		cityDayCost = cityCost;
	}

	private int countPlaysTimeCost(Entry<String, Map<Integer, List<Point>>> cityPlans) {
		int total = 0;
		for (Entry<Integer, List<Point>> entry : cityPlans.getValue().entrySet()) {
			if (entry.getKey() == TripType.SCENIC.value() || entry.getKey() == TripType.RESTAURANT.value()) {
				total = countPlanCost(entry.getValue());
			}
		}
		return total;
	}

	private Map<Integer, LinkedList<Point>> optimizeCityPlans(Entry<String, Map<Integer, List<Point>>> cityPlanTrip) {
		String cityCode = cityPlanTrip.getKey();
		Map<Integer, List<Point>> plans = cityPlanTrip.getValue();
		List<Point> hotels = null;
		List<Point> scenicAndRes = new ArrayList<Point>();
		for (Entry<Integer, List<Point>> plan : plans.entrySet()) {
			if (plan.getKey() == TripType.HOTEL.value()) {
				hotels = plan.getValue();
			} else {
				scenicAndRes.addAll(plan.getValue());
			}
		}
		return optimizeCityPlans(cityCode, hotels, scenicAndRes);
	}

	private Map<Integer, LinkedList<Point>> optimizeCityPlans(String cityCode, List<Point> hotels, List<Point> scenicAndRes) {
		// TODO Auto-generated method stub
		Integer day = cityDayCost.get(cityCode);
		PlanScenic planScenic = PlanScenic.planScenic(scenicAndRes, day, planHourType, cityCode);
		return planScenic.planScenicPriority.getResultPlay();
		// if (day > 0) {
		// if (PlanOptimizeType.OPTIMIZE_DAY_CONFIRM == planOptimizeType) {
		// if (day == 1) {
		// int totalCost = countPlanCost(scenicAndRes);
		// if (!checkIfDayPlanIsReasonbleable(totalCost, scenicAndRes.size())) {
		// reasonableDayScenic(scenicAndRes, totalCost);
		// }
		// } else {
		//
		// }
		// } else {
		//
		// }
		// }
	}

	/**
	 * 调整某一天的景点顺序
	 * 
	 * @param scenicAndRes
	 * @param totalCost
	 */
	private List<Point> reasonableDayScenic(List<Point> scenicAndRes, int totalCost) {
		scenicAndRes = sortScenics(scenicAndRes);
		int leftMinutes = planHourType.getAvg();
		List<Point> sequenceScenics = new ArrayList<Point>();
		Point lastAddPoint = null;
		for (Point point : scenicAndRes) {
			if (sequenceScenics.size() > maxDayScenic) {
				break;
			}
			if (sequenceScenics.size() > 0) {
				Dis dis = getPointToPointDis(lastAddPoint, point);
				if (dis != null && dis.getTaxiTime() + point.playHours < leftMinutes) {
					sequenceScenics.add(point);
					leftMinutes -= dis.getTaxiTime() + point.playHours;
				} else {
					break;
				}
			} else {
				sequenceScenics.add(point);
				leftMinutes -= point.playHours;
			}
			lastAddPoint = point;
		}
		return sequenceScenics;
	}

	private Dis getPointToPointDis(Point lastAddPoint, Point point) {
		// TODO Auto-generated method stub
		return Dis.builder(lastAddPoint.scenicInfoId, point.scenicInfoId);
	}

	private List<Point> sortScenics(List<Point> scenicAndRes) {
		Collections.sort(scenicAndRes, new Comparator<Point>() {
			@Override
			public int compare(Point o1, Point o2) {
				// TODO Auto-generated method stub
				return o1.orderNum - o2.orderNum;
			}
		});
		return scenicAndRes;
	}

	/**
	 * 按城市区分餐厅，酒店，景点数据
	 */
	public Map<String, Map<Integer, List<Point>>> spileScenicInfo() {
		Map<String, Map<Integer, List<Point>>> map = new HashMap<String, Map<Integer, List<Point>>>();
		Map<Long, Point> parentPoints = new HashMap<Long, Point>();
		List<Point> points = new ArrayList<Point>();
		for (Map<String, Object> planTrip : oldPlanTrips) {
			Point point = changePlanTripToPoint(planTrip);
			points.add(point);
			if (point.father != 0) {
				Point father = AreaService.instance.getPoint(point.father);
				// father.setChildPoints(new ArrayList<Point>());
				// father.getChildPoints().add(point);
				if (!parentPoints.containsKey(father.scenicInfoId)) {
					if (!father.getIsCity()) {
						father.setArea(AreaService.instance.getArea(father.scenicInfoId));
						parentPoints.put(father.scenicInfoId, father);
					} else {
						parentPoints.put(point.scenicInfoId, point);
					}
				}
			} else {
				parentPoints.put(point.scenicInfoId, point);
			}
		}
		for (Point point : points) {
			Point father = parentPoints.get(point.father);
			if (father != null) {
				parentPoints.remove(point.scenicInfoId);
				if (father.getChildPoints() == null) {
					father.setChildPoints(new ArrayList<Point>());
				}
				father.getChildPoints().add(point);
			}
		}
		for (Entry<Long, Point> entry : parentPoints.entrySet()) {
			Point point = entry.getValue();
			if (point.getTripType() == TripType.HOTEL.value()) {
				putPlanTrip(map, point, hotelKey, TripType.HOTEL);
			} else if (point.getTripType() == TripType.RESTAURANT.value()) {
				putPlanTrip(map, point, foodKey, TripType.RESTAURANT);
			} else {
				putPlanTrip(map, point, scenicInfoKey, TripType.SCENIC);
			}

		}
		return map;
	}

	private void putPlanTrip(Map<String, Map<Integer, List<Point>>> map, Point planTrip, String key, TripType tripType) {
		String cityCode = String.valueOf(planTrip.cityCode).substring(0, 4);
		if (!map.containsKey(cityCode)) {
			map.put(cityCode, new HashMap<Integer, List<Point>>());
		}
		Map<Integer, List<Point>> hotels = map.get(cityCode);
		if (!hotels.containsKey(tripType.value())) {
			hotels.put(tripType.value(), new ArrayList<Point>());
		}
		hotels.get(tripType.value()).add(planTrip);
	}

	/**
	 * 判断一个分区是否合理
	 */
	public Boolean checkIfDayPlanIsReasonbleable(int totalCost, int size) {
		if (totalCost <= planHourType.getAvg() && size < maxDayScenic) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 优化某个分区/天
	 */
	public abstract void optimizeDayPlan();

	/**
	 * 旅行商算法优化指定路线
	 */
	public void tipsPlan() {

	}

	/**
	 * 增加景点
	 */
	public abstract void addPlan();

	/**
	 * 减少景点
	 */
	public abstract void reducePlan();

	/**
	 * 计算景点和行程花费
	 */
	public int countPlanCost(List<Point> points) {
		int total = 0;
		for (Point point : points) {
			total += point.playHours;
		}
		return total;
	}

	protected Point changePlanTripToPoint(Map<String, Object> planTrip) {
		int distance = 11111;
		List<Point> list = new ArrayList<Point>();
		long i = 0;
		DecimalFormat fnum = new DecimalFormat("#.##"); // 格式化取坐标百分位
		Point point = new Point();
		if ((Integer) planTrip.get("tripType") == TripType.SCENIC.value()) {
			point.id = i;
			i++;
			Map<String, Object> scenicInfo = (Map<String, Object>) planTrip.get("scenicInfo");
			point.setTripType(TripType.SCENIC.value());
			point.x = distance * Double.parseDouble(scenicInfo.get("longitude").toString());
			point.x = Double.parseDouble(fnum.format(point.x));
			point.y = distance * Double.parseDouble(scenicInfo.get("latitude").toString());
			point.y = Double.parseDouble(fnum.format(point.y));
			point.playHours = Integer.parseInt(scenicInfo.get("adviceHours").toString());
			point.scenicInfoId = Long.parseLong(scenicInfo.get("id").toString());
			point.father = Long.parseLong(scenicInfo.get("father").toString());
			point.orderNum = Integer.parseInt(scenicInfo.get("orderNum").toString());
			point.cityCode = Integer.parseInt(scenicInfo.get("cityCode").toString());
			point.setName(scenicInfo.get("name").toString());
			list.add(point);
		} else if ((Integer) planTrip.get("tripType") == TripType.RESTAURANT.value()) {
			point.id = i;
			i++;
			Map<String, Object> food = (Map<String, Object>) planTrip.get("food");
			point.setTripType(TripType.RESTAURANT.value());
			point.x = distance * Double.parseDouble(food.get("resLongitude").toString());
			point.x = Double.parseDouble(fnum.format(point.x));
			point.y = distance * Double.parseDouble(food.get("resLatitude").toString());
			point.y = Double.parseDouble(fnum.format(point.y));
			point.playHours = 60;
			point.scenicInfoId = Long.parseLong(food.get("id").toString());
			point.father = 0;
			point.orderNum = 1;
			point.cityCode = Integer.parseInt(food.get("cityCode").toString());
			point.setName(food.get("resName").toString());
			list.add(point);
		} else if ((Integer) planTrip.get("tripType") == TripType.HOTEL.value()) {
			point.id = i;
			i++;
			Map<String, Object> hotel = (Map<String, Object>) planTrip.get("hotel");
			point.setTripType(TripType.HOTEL.value());
			point.x = distance * Double.parseDouble(hotel.get("longitude").toString());
			point.x = Double.parseDouble(fnum.format(point.x));
			point.y = distance * Double.parseDouble(hotel.get("latitude").toString());
			point.y = Double.parseDouble(fnum.format(point.y));
			point.playHours = 60;
			point.scenicInfoId = Long.parseLong(hotel.get("id").toString());
			point.father = 0;
			point.orderNum = 1;
			point.cityCode = Integer.parseInt(hotel.get("city_code").toString());
			point.setName(hotel.get("hotelName").toString());
			list.add(point);
		}
		Area area = AreaService.instance.getArea(point.scenicInfoId);
		point.setArea(area);
		return point;
	}

	public Map<String, Map<Integer, LinkedList<Point>>> getResultPlans() {
		return resultPlans;
	}
}
