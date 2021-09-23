package com.hmlyinfo.app.soutu.plan.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hmlyinfo.app.soutu.account.domain.User;
import com.hmlyinfo.app.soutu.account.service.UserService;
import com.hmlyinfo.app.soutu.activity.domain.JoinedPlan;
import com.hmlyinfo.app.soutu.activity.service.JoinedPlanService;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.delicacy.service.RestaurantService;
import com.hmlyinfo.app.soutu.hotel.service.CtripHotelService;
import com.hmlyinfo.app.soutu.plan.domain.Plan;
import com.hmlyinfo.app.soutu.plan.domain.PlanDay;
import com.hmlyinfo.app.soutu.plan.domain.PlanTrip;
import com.hmlyinfo.app.soutu.plan.domain.type.TravelType;
import com.hmlyinfo.app.soutu.plan.domain.type.TripType;
import com.hmlyinfo.app.soutu.recplan.domain.RecplanTrip;
import com.hmlyinfo.app.soutu.scenic.domain.ScenicInfo;
import com.hmlyinfo.app.soutu.scenic.service.CityService;
import com.hmlyinfo.app.soutu.scenic.service.DestinationService;
import com.hmlyinfo.app.soutu.scenic.service.ScenicInfoService;
import com.hmlyinfo.app.soutu.scenic.service.ScenicOtherService;
import com.hmlyinfo.base.util.StringUtil;
import com.hmlyinfo.base.util.Validate;

/**
 * 
 * @ClassName: PlanDetailService
 * @Description: TODO
 * @author shiqingju
 * @email shiqingju@hmlyinfo.com
 * @date 2015年11月9日 下午4:22:45
 *
 */
@Service
public class PlanDetailService {

	Logger					logger			= Logger.getLogger(PlanDetailService.class);

	@Autowired
	PlanService				planService;
	@Autowired
	PlanDaysService			planDaysService;
	@Autowired
	PlanTripService			planTripService;
	@Autowired
	ScenicInfoService		scenicInfoService;
	@Autowired
	CtripHotelService		ctripHotelService;
	@Autowired
	RestaurantService		restaurantService;
	@Autowired
	ScenicOtherService		scenicOtherService;
	@Autowired
	CityService				cityService;
	@Autowired
	JoinedPlanService		joinedPlanService;
	@Autowired
	DestinationService		destinationService;
	@Autowired
	private BaiduDisService	baiduDisService;
	@Autowired
	private PlanUrbanTrafficService planTrafficService;

	@Autowired
	UserService				userService;

	SimpleDateFormat		FORMAT_HH_MM	= new SimpleDateFormat("hh:mm");

	/**
	  * 查询行程详情
	  * @email shiqingju@hmlyinfo.com
	  *
	  * @param planId 行程ID
	  * @param flag 是否对酒店进行处理
	  * @param traffic 是否对交通进行处理
	  * @return
	  *
	  * @return Plan
	  * @throws
	 */
	public Plan getPlanDetail(Long planId, int flag, int traffic) {
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("id", planId);
//		List<String> planColumns = Lists.newArrayList("*");
//		paramMap.put("needColumns", planColumns);
//
//		List<Plan> planList = planService.listColumns(paramMap);
//		Validate.isTrue(planList.size() > 0, ErrorCode.ERROR_53001);
//
//		Plan plan = planList.get(0);
		Plan plan = planService.info(planId);
		Validate.isTrue(plan != null, ErrorCode.ERROR_53001);
		// 查询行程天列表
		List<PlanDay> planDayList = planDaysService.list(ImmutableMap.of("planId", (Object) planId));
		plan.setDayCount(planDayList.size());
		// 获取行程天酒店
		planDaysService.addDayHotels(planDayList);
		
		preparePlanDayDetail(plan, planDayList, flag, traffic);
		plan.setPlanDayList(planDayList);

		// 获取行程景点数量
		int totalScenicCount = 0;
		for (PlanDay planDay : planDayList) {
			totalScenicCount += planDay.getScenicCount();
		}
		plan.setScenicCount(totalScenicCount);

		return plan;
	}

	/**
	 * 查询行程的详细信息，获取行程的中的节点详情
	 * 计算行程花费
	 * 
	 * @Title: preparePlanDayDetail
	 * @email shiqingju@hmlyinfo.com
	 * @date 2015年11月9日 下午3:33:07
	 *
	 * @param plan
	 * @param planDayList
	 * @param flag 是否处理酒店的办理入住等逻辑
	 * @param traffic
	 *
	 * @return void
	 * @throws
	 */
	private void preparePlanDayDetail(Plan plan, List<PlanDay> planDayList, int flag, int traffic) {
		List<String> scenicColumns = Lists.newArrayList("id", "city_code", "name", "ticket", "price", "address", "telephone",
				"advice_time", "advice_hours", "open_time", "cover_small", "cover_medium", "cover_large", "longitude", "latitude",
				"gcj_lng", "gcj_lat", "lowest_price", "market_price", "highest_price", "first_ticket_id", "first_ticket_source",
				"flag_three_level_region", "parents_three_level_region", "father_name", "father", "score");
		List<String> hotelColumns = Lists.newArrayList("id", "lowest_hotel_price", "img_url", "hotel_name", "longitude", "latitude",
				"addr", "contact", "city_code", "gcj_lng", "gcj_lat", "intro", "score");
		List<String> restaurantColumns = Lists.newArrayList("id", "res_phone", "city_code", "res_name", "res_address", "res_price",
				"res_picture", "res_latitude", "res_longitude", "res_comment", "gcj_lng", "gcj_lat", "score");
		Map<Integer, List<String>> colMap = Maps.newHashMap();
		colMap.put(TripType.SCENIC.value(), scenicColumns);
		colMap.put(TripType.HOTEL.value(), hotelColumns);
		colMap.put(TripType.RESTAURANT.value(), restaurantColumns);
		
		int planCost = 0;

		for (int i = 0; i < planDayList.size(); i++) {
			PlanDay planDay = planDayList.get(i);

			// 获取行程节点列表
			List<PlanTrip> planTripList = planDay.getPlanTripList();
			// 如果行程节点列表为空，则从数据库中查询
			if (planTripList == null)
			{
				// 获取行程天所有景点
				Map<String, Object> planTripMap = new HashMap<String, Object>();
				planTripMap.put("planDaysId", planDay.getId());
				planTripMap.put("planId", plan.getId());
				planTripList = planTripService.list(planTripMap);
			}
			
			
			// TODO 考虑这段是否有需要
			// 处理交通信息
			if (traffic == 1) {
				trafficForPdf(planTripList);
			}

			// 获取每天景点数量
			int scenicCount = 0;
			for (PlanTrip planTrip : planTripList) 
			{
				try {
					if (planTrip.getTripType() == TripType.SCENIC.value() || planTrip.getTripType() == TripType.RESTAURANT.value()) {
						scenicCount++;
					}
				} catch (Exception e) {
					logger.error("数据异常，该行程有不含有tripType的行程点");
				}
			}
			planDay.setScenicCount(scenicCount);

			List<Map<String, Object>> scenicList = planTripService.listDetail(planTripList, colMap);
			planCost += planDay.getTotalPrice();
			// 获取当前天以及前一天城市编号
			if (scenicList.size() > 0) {
				try {
					Map<String, Object> scenicMap = (Map<String, Object>) scenicList.get(0).get("detail");
					int cityCode = Integer.valueOf(scenicMap.get("cityCode").toString());
					planDay.setCityCode(cityCode);

					if (i > 0) {
						PlanDay yesterday = planDayList.get(i - 1);
						planDay.setEndCityCode(yesterday.getCityCode());
					}
				} catch (Exception e) {
					System.out.println();
				}

			}

			planDay.setScenicList(scenicList);
		}
		
		plan.setPlanCost(planCost);
	}
	
	/**
	 * 计算每天的游玩时间和花费
	 * @Title: processPlanDayStat
	 * @email shiqingju@hmlyinfo.com
	 * @date 2015年11月9日 下午3:46:09
	 * @version  
	 * @deprecated 从2.7.0开始，行程花费由编辑行程时进行计算，而不在查询时进行处理
	 *
	 * @return void
	 * @throws
	 */
	@Deprecated
	private void processPlanDayStat(PlanDay planDay, List<Map<String, Object>> scenicList)
	{
		int time = 0;
		int cost = 0;
		Map<String, Object> hotelMap = new HashMap<String, Object>();
		for (Map<String, Object> scenicMap : scenicList) {
			int tripType = Integer.parseInt(scenicMap.get("tripType").toString());
			if (tripType == TripType.HOTEL.value()) {
				hotelMap = scenicMap;
				continue;
			}
			if (tripType == TripType.SCENIC.value()) {
				Map<String, Object> detailMap = (Map<String, Object>) scenicMap.get("detail");
				int advHour = StringUtil.getIntFromObj(detailMap.get("adviceHours"));
				time += advHour;
				if (detailMap.get("lowestPrice") != null) {
					double lowestPrice = Double.parseDouble(detailMap.get("lowestPrice").toString());
					cost += lowestPrice;
				} else if (detailMap.get("price") != null) {
					int price = Integer.parseInt(detailMap.get("price").toString());
					cost += price;
				}

			} else if (tripType == TripType.RESTAURANT.value()) {
				if(scenicMap.get("foodDetail") == null){
					continue;
				}
				Map<String, Object> detailMap = (Map<String, Object>) scenicMap.get("foodDetail");
				time += 60;
				if (detailMap.get("price") != null) {
					double price = Double.parseDouble(detailMap.get("price").toString());
					cost += price;
				}
			}
		}
		cost += planDay.getHotelCost();
		
		planDay.setTotalPrice(cost);
		planDay.setTotalTime(time);
	}

	// 返回planTrips中的酒店
	public PlanTrip hotelInTrip(List<PlanTrip> planTrips) {
		PlanTrip res = null;
		for (PlanTrip planTrip : planTrips) {
			if (planTrip.getTripType() == TripType.HOTEL.value()) {
				res = planTrip;
				break;
			}
		}

		return res;
	}

	// 返回planTrips中的交通枢纽
	public PlanTrip trsInTrip(List<PlanTrip> planTrips) {
		PlanTrip res = null;
		for (PlanTrip planTrip : planTrips) {
			if (planTrip.getTripType() == TripType.STATION.value()) {
				res = planTrip;
				break;
			}
		}

		return res;
	}

	// 在planTrips中插入交通信息，但是并不更新到数据库
	public void trafficForPdf(List<PlanTrip> planTrips) {
		baiduDisService.setTripBaiduMap(planTrips);

		for (int i = 0; i < planTrips.size() - 1; i++) {

			PlanTrip planTrip = planTrips.get(i);

			if (planTrip.getBaiduMap() == null) {
				continue;
			}
			Map<String, Object> baiduMap = planTrip.getBaiduMap();
			int walkDis = (Integer) baiduMap.get("walkDis");
			if (walkDis == 0) {
				continue;
			}
			if (walkDis < 1000) {
				planTrip.setTravelType(TravelType.WALKING.value());
				int time = (Integer) baiduMap.get("walkTime");
				String travelTime = "";

				if (time > 60) {
					travelTime = travelTime + (time / 60) + "小时";
				}
				travelTime = travelTime + (time % 60) + "分钟";
				planTrip.setTravelTime(travelTime);
				planTrip.setTravelHours((Integer) baiduMap.get("walkTime"));
				planTrip.setTravelPrice(0);
				planTrip.setTravelMileage(baiduMap.get("walkDis") + "米");

			} else {
				double cost = (Double) baiduMap.get("cost");
				int time = (Integer) baiduMap.get("taxiTime");
				String travelTime = "";

				if (time > 60) {
					travelTime = travelTime + (time / 60) + "小时";
				}
				travelTime = travelTime + (time % 60) + "分钟";
				planTrip.setTravelTime(travelTime);
				planTrip.setTravelType(TravelType.DRIVING.value());
				planTrip.setTravelHours(time);
				planTrip.setTravelPrice(new Double(cost).intValue());
				int taxiDis = (Integer) baiduMap.get("taxiDis");
				if (taxiDis < 1000) {
					planTrip.setTravelMileage(taxiDis + "米");
				} else {
					// 1024米显示1公里
					if (taxiDis % 1000 < 100) {
						planTrip.setTravelMileage((taxiDis / 1000) + "公里");
					} else {
						double dis = Double.parseDouble(taxiDis + "") / 1000.0;
						DecimalFormat df = new DecimalFormat("0.0");
						planTrip.setTravelMileage(df.format(dis) + "公里");
					}
				}
			}
		}
	}


	public Plan childDetail(Long planId, int flag, int traffic) {
		Plan plan = getPlanDetail(planId, flag, traffic);

		return processPlanLevel(plan);
		
	}
	
	/**
	 * 处理行程中景点的层级结构
	 * 从2.7.0开始，前端需要使用层级结构来展示行程，考虑到多个平台之间的通用性，将这个方法放在API中
	 * 此方法的注释清查看RecplanDetailService
	 * @Title: processPlanLevel
	 * @email shiqingju@hmlyinfo.com
	 * @date 2015年11月9日 下午4:19:41
	 * @version 2.7.0
	 *
	 * @param plan
	 *
	 * @return void
	 * @throws
	 */
	private Plan processPlanLevel(Plan plan)
	{
		HashSet<Long> parentSet = new HashSet<Long>();

		for (PlanDay planDay : plan.getPlanDayList()){
			
			// 旧的行程点列表
			List<Map<String, Object>> planTripList = planDay.getScenicList();

			List<Map<String, Object>> newPlanTripList = new ArrayList<Map<String, Object>>();
			// 上一个父景点
			long lastScenic = -1;
			for (Map<String, Object> planTrip : planTripList) {
				int tripType = Integer.parseInt(planTrip.get("tripType").toString());
				if (tripType == TripType.HOTEL.value() || tripType == TripType.STATION.value()){
					newPlanTripList.add(planTrip);
					lastScenic = -1;
					continue;
				}
				if (tripType == TripType.RESTAURANT.value()) {
					if (lastScenic == -1) {
						newPlanTripList.add(planTrip);
						continue;
					} else {
						if (newPlanTripList.get(newPlanTripList.size() - 1).get("childrenList") == null){
							List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();
							childList.add(planTrip);
							newPlanTripList.get(newPlanTripList.size() - 1).put("childrenList", childList);
						} else {
							List<Map<String, Object>> childList = (List<Map<String, Object>>) newPlanTripList.get(newPlanTripList.size() - 1).get("childrenList");
							childList.add(planTrip);
						}

						continue;
					}
				}

				// 当前景点
				long scenicId = Long.parseLong(planTrip.get("scenicId").toString());
				Map<String, Object> scenicInfo = (Map<String, Object>) planTrip.get("detail");
				Object parentsThreeLevelRegion = scenicInfo.get("parentsThreeLevelRegion");
				// 当前景点是父景点
				if (parentsThreeLevelRegion == null || "F".equals(parentsThreeLevelRegion.toString())){
					lastScenic = scenicId;
					newPlanTripList.add(planTrip);
					continue;
				} else {
					long parentId = Long.parseLong(scenicInfo.get("father").toString());
					if (parentId == lastScenic) {
						if (newPlanTripList.get(newPlanTripList.size() - 1).get("childrenList") == null){
							List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();
							childList.add(planTrip);
							newPlanTripList.get(newPlanTripList.size() - 1).put("childrenList", childList);
						} else {
							List<Map<String, Object>> childList = (List<Map<String, Object>>) newPlanTripList.get(newPlanTripList.size() - 1).get("childrenList");
							childList.add(planTrip);
						}
					} else {
						lastScenic = parentId;
						// 添加到父景点Set中
						parentSet.add(parentId);
						// New parent-trip
						Map<String, Object> parentTrip = new HashMap<String, Object>();
						parentTrip.put("scenicId", parentId);
						parentTrip.put("tripType", TripType.SCENIC.value());
						parentTrip.put("planId", planTrip.get("planId"));
						parentTrip.put("planDaysId", planTrip.get("planDaysId"));

						List<Map<String, Object>> children = new ArrayList<Map<String, Object>>();
						children.add(planTrip);
						parentTrip.put("childrenList", children);

						newPlanTripList.add(parentTrip);
					}
				}



			}

			// 把子景点中的最后一个餐厅移出
			for (int i = 0; i < newPlanTripList.size(); i++){
				Map<String, Object> planTrip = newPlanTripList.get(i);
				List<Map<String, Object>> children = (List<Map<String, Object>>) planTrip.get("childrenList");
				if (children == null){
					continue;
				}
				int loc = i + 1;
				int childrenSize = children.size();
				if (childrenSize > 0){
					for (int j = childrenSize - 1; j >= 0; j--){
						Map<String, Object> lastTrip = children.get(j);
						int tripType = Integer.parseInt(lastTrip.get("tripType").toString());
						if (tripType == TripType.RESTAURANT.value()){
							newPlanTripList.add(loc++, lastTrip);
							children.remove(j);
						}else {
							break;
						}
					}

				}
			}
			// Day
			planDay.setScenicList(newPlanTripList);
		}

		// 暂时存储查询到的景点信息
		Map<Long, Object> infoMap = new HashMap<Long, Object>();

		if (parentSet.size() > 0) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("ids", parentSet);
			List<ScenicInfo> scenicList = scenicInfoService.listColumns(paramMap, Lists.newArrayList("*"));
			for (ScenicInfo scenicInfo : scenicList) {
				infoMap.put(scenicInfo.getId(), scenicInfo);
			}
		}

		for (PlanDay planDay : plan.getPlanDayList()) {
			
			for (Map<String, Object> planTrip : planDay.getScenicList()) {
				int tripType = Integer.parseInt(planTrip.get("tripType").toString());
				if (tripType == TripType.SCENIC.value() && planTrip.get("detail") == null) {
					long scenicId = Long.parseLong(planTrip.get("scenicId").toString());
					if (infoMap.get(scenicId) != null){
						planTrip.put("detail", infoMap.get(scenicId));
					}
				}
			}
		}

		return plan;
	}
	
	/**
	 * 将只有框架数据的行程转换成结构丰富并包含层级的行程的行程
	 * @Title: processPlanWithDetailAndLevel
	 * @email shiqingju@hmlyinfo.com
	 * @date 2015年11月9日 下午4:03:27
	 * @version 2.7.0
	 *
	 * @param plan 框架行程，只包含 {planDayList: [ { planTripList : [ { scenicId : scenicId, tripType : tripType }, { ... } ] }, { ... } ]}
	 * @return
	 *
	 * @return Plan 结构丰富的行程，包含planTrip的detail和层级结构
	 * @throws
	 */
	public Plan processPlanWithDetailAndLevel(Plan plan)
	{
		// 查询行程结构丰富的数据
		preparePlanDayDetail(plan, plan.getPlanDayList(), 0, 0);
		return processPlanLevel(plan);
	}
	
}
