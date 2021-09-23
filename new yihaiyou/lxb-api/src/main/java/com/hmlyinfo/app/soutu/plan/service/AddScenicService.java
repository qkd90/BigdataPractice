package com.hmlyinfo.app.soutu.plan.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.plan.domain.PlanTrip;
import com.hmlyinfo.app.soutu.plan.domain.type.TravelType;
import com.hmlyinfo.app.soutu.plan.domain.type.TripType;
import com.hmlyinfo.app.soutu.plan.newAlg.modal.Point;
import com.hmlyinfo.app.soutu.scenic.service.ScenicInfoService;

@Service
public class AddScenicService {

	Logger						logger	= Logger.getLogger(AddScenicService.class);

	@Autowired
	private ScenicInfoService	scenicInfoService;
	@Autowired
	private PlanDaysService		planDaysService;
	@Autowired
	private OptimizeService		optimizeService;
	@Autowired
	private TspService			tspService;
	@Autowired
	private DisService			disService;

	/**
	 * 根据现有行程数据判断新加景点位置
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> addScenic(HttpServletRequest request) {

		List<Map<String, Object>> planDays = new ArrayList<Map<String, Object>>();
		long scenicId = 0;
		int tripType = 0;
		int dayIndex = -1;
		try {

			// 取出现有行程数据
			planDays = new ObjectMapper().readValue(request.getParameter("dayList"), ArrayList.class);
			scenicId = Long.parseLong(request.getParameter("scenicId"));
			tripType = Integer.parseInt(request.getParameter("tripType"));
			if (request.getParameter("dayIndex") != null) {
				dayIndex = Integer.parseInt(request.getParameter("dayIndex"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("数据格式化错误: " + e.getCause());
			e.printStackTrace();
		}
		long tempId = 0;
		Map<Integer, List<PlanTrip>> scenicDayMap = new HashMap<Integer, List<PlanTrip>>();

		// 把现有行程数据按天分组
		for (int i = 0; i < planDays.size(); i++) {
			// 天
			Map<String, Object> dayMap = planDays.get(i);
			// 行程
			List<Map<String, Object>> tripMaps = (List<Map<String, Object>>) dayMap.get("tripList");

			List<PlanTrip> planTrips = new ArrayList<PlanTrip>();
			for (Map<String, Object> map : tripMaps) {
				if (map == null || map.get("scenicId") == null) {
					continue;
				}
				PlanTrip planTrip = new PlanTrip();
				planTrip.setId(tempId++);
				planTrip.setScenicId(Long.parseLong(map.get("scenicId").toString()));
				planTrip.setTripType(Integer.parseInt(map.get("tripType").toString()));
				planTrip.setDay(i + 1);
				planTrip.setTravelType(TravelType.DRIVING.value());

				if (planTrip.getTripType() == TripType.SCENIC.value() || planTrip.getTripType() == TripType.HOTEL.value()
						|| planTrip.getTripType() == TripType.RESTAURANT.value()) {
					planTrips.add(planTrip);
				}

			}
			//
			scenicDayMap.put(i + 1, planTrips);

		}

		// 新加景点或者餐厅转化为一个行程点
		PlanTrip newPlanTrip = new PlanTrip();
		newPlanTrip.setId(tempId);
		newPlanTrip.setScenicId(scenicId);
		newPlanTrip.setTripType(tripType);
		newPlanTrip.setTravelType(TravelType.DRIVING.value());

		// 记录最小总时间
		int time = Integer.MAX_VALUE;
		// 记录最小总时间对应的天
		int day = 1;
		List<Long> bestList = new ArrayList<Long>();

		// 遍历每天的行程
		for (Entry<Integer, List<PlanTrip>> entry : scenicDayMap.entrySet()) {
			//
			int today = entry.getKey();
			List<PlanTrip> dayList = entry.getValue();
			List<PlanTrip> tripList = new ArrayList<PlanTrip>();
			tripList.addAll(dayList);
			tripList.add(newPlanTrip);
			// 加载节点对应的景点、酒店、餐厅的信息
			List<Map<String, Object>> tripDetailList = planDaysService.prepareScenicData(tripList);
			// 将行程节点转成对应的point用于计算
			List<Point> pointList = optimizeService.preparePoint(tripDetailList);
			Point hotelPoint = null;

			// 获取酒店信息
			// 酒店点的经纬度转换乘以11111
			// （11111是从坐标系传唤为实际距离（单位：米）的比例）
			for (Map<String, Object> planTrip : tripDetailList) { // 获取酒店信息
				if (Integer.valueOf(planTrip.get("tripType").toString()) == TripType.HOTEL.value()) {
					hotelPoint = new Point();
					hotelPoint.id = Long.parseLong(planTrip.get("id").toString());
					Map<String, Object> hotel = (Map<String, Object>) planTrip.get("hotel");
					hotelPoint.x = 11111 * (Double) hotel.get("longitude");
					hotelPoint.y = 11111 * (Double) hotel.get("latitude");
					break;
				}
			}
			List<Point> list = tspService.tsp(pointList, hotelPoint);
			int listTime = disService.listTime(list, DisService.MODE_TAXI);

			// 不返回酒店信息，因此如果含有酒店就从1开始获取
			int sIndex = 0;
			if (hotelPoint != null) {
				sIndex = 1;
			}
			if (listTime < time) {
				time = listTime;
				day = today;
				bestList.clear();
				for (int i = sIndex; i < list.size(); i++) {
					bestList.add(list.get(i).scenicInfoId);
				}
			}
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		//
		if (dayIndex == -1) {
			resultMap.put("day", day);
		} else {
			resultMap.put("day", dayIndex);
		}
		resultMap.put("planTrips", bestList);
		return resultMap;

	}

}
