package com.hmlyinfo.app.soutu.plan.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.hmlyinfo.app.soutu.account.domain.User;
import com.hmlyinfo.app.soutu.account.service.MemberService;
import com.hmlyinfo.app.soutu.account.service.UserService;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.delicacy.service.RestaurantService;
import com.hmlyinfo.app.soutu.hotel.service.CtripHotelService;
import com.hmlyinfo.app.soutu.plan.domain.Plan;
import com.hmlyinfo.app.soutu.plan.domain.PlanCity;
import com.hmlyinfo.app.soutu.plan.domain.PlanDay;
import com.hmlyinfo.app.soutu.plan.domain.PlanTrip;
import com.hmlyinfo.app.soutu.plan.domain.type.TravelType;
import com.hmlyinfo.app.soutu.plan.domain.type.TripType;
import com.hmlyinfo.app.soutu.recplan.domain.Recplan;
import com.hmlyinfo.app.soutu.recplan.service.RecplanService;
import com.hmlyinfo.app.soutu.scenic.domain.City;
import com.hmlyinfo.app.soutu.scenic.domain.Destination;
import com.hmlyinfo.app.soutu.scenic.service.CityService;
import com.hmlyinfo.app.soutu.scenic.service.DestinationService;
import com.hmlyinfo.app.soutu.scenic.service.ScenicInfoService;
import com.hmlyinfo.base.exception.BizValidateException;
import com.hmlyinfo.base.util.ListUtil;
import com.hmlyinfo.base.util.Validate;

/**
 * 实现行程的编辑、修改功能
 */

@Service
public class PlanEditService {

	private static final Logger	logger	= Logger.getLogger(PlanEditService.class);

	@Autowired
	private PlanService			planService;
	@Autowired
	private PlanDaysService		planDaysService;
	@Autowired
	private PlanCityService 	planCityService;
	@Autowired
	private PlanTripService		planTripService;
	@Autowired
	private OptimizeService		optimizeService;
	@Autowired
	private RecplanService		recplanService;
	@Autowired
	private CityService			cityService;
	@Autowired
	private UserService			userService;
	@Autowired
	private ScenicInfoService	scenicInfoService;
	@Autowired
	private CtripHotelService	ctripHotelService;
	@Autowired
	private RestaurantService	restaurantService;
	@Autowired
	private DestinationService	destinationService;

	// 根据推荐行程数据进行不改变天数和景点数的智能优化并插入数据库，返回行程id
	public Map<String, Object> editFromRecplan(HttpServletRequest request) {

		// 目的地城市
		int cityCode = Integer.parseInt(request.getParameter("cityCode").toString());
		// 出发日期
		String startDate = "";
		if (request.getParameter("startDate") != null) {
			startDate = request.getParameter("startDate").toString();
		}

		List<Map<String, Object>> planTrips = new ArrayList<Map<String, Object>>();
		try {
			planTrips = new ObjectMapper().readValue(request.getParameter("tripList"), ArrayList.class);
		} catch (Exception e) {
			logger.error("传入数据解析失败", e);
			throw new BizValidateException(ErrorCode.ERROR_51001, "传入数据解析失败");
		}
		// 推荐行程的id信息
		Multimap<String, Map<String, Object>> recMap = ArrayListMultimap.create();

		int days = Integer.parseInt(request.getParameter("days"));
		int hour = 0;
		int type = 0;

		// 交通枢纽信息
		List<PlanTrip> transTrip = new ArrayList<PlanTrip>();
		// 行程点信息
		List<PlanTrip> planTripList = new ArrayList<PlanTrip>();

		int scenicSize = 0;
		// 存储推荐行程信息
		Map<Long, Long> sourceMap = new HashMap<Long, Long>();
		Map<Long, Long> delicacyMap = new HashMap<Long, Long>();
		for (Map<String, Object> map : planTrips) {
			recMap.put(map.get("sourceId").toString(), map);

			PlanTrip planTrip = new PlanTrip();
			planTrip.setScenicId(Long.parseLong(map.get("scenicId").toString()));
			planTrip.setTripType(Integer.parseInt(map.get("tripType").toString()));
			if (planTrip.getTripType() == TripType.SCENIC.value() || planTrip.getTripType() == TripType.RESTAURANT.value()) {
				scenicSize++;
			}
			if (!"-1".equals(map.get("delicacyId"))) {
				planTrip.setDelicacyId(Long.parseLong(map.get("delicacyId").toString()));
				delicacyMap.put(planTrip.getScenicId(), planTrip.getDelicacyId());
			}
			if (!"-1".equals(map.get("sourceTripId"))) {
				planTrip.setSource(1);
				planTrip.setSourceId(Long.parseLong(map.get("sourceTripId").toString()));
				sourceMap.put(planTrip.getScenicId(), planTrip.getSourceId());
			}
			planTrip.setTravelType(TravelType.DRIVING.value());

			if (planTrip.getTripType() == TripType.STATION.value()) {
				transTrip.add(planTrip);
			} else {
				planTripList.add(planTrip);
			}

		}

		Validate.isTrue(scenicSize > 0, ErrorCode.ERROR_53005, "所选景点数目为0");

		if (days > scenicSize) {
			days = scenicSize;
		}

		// 计算推荐行程累加数
		for (Map.Entry<String, Map<String, Object>> entry : recMap.entries()) {
			System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
			String keyString = entry.getKey();
			if (!keyString.equals("-1")) {
				Recplan recplan = recplanService.info(Long.parseLong(keyString));
				if (recplan != null) {
					int selected = recplan.getSelected();
					selected += entry.getValue().size();
					recplan.setSelected(selected);
					recplanService.update(recplan);
				}
			}
		}

		// 加载节点对应的景点、酒店、餐厅的信息
		List<Map<String, Object>> tripDetailList = planDaysService.prepareScenicData(planTripList);
		// 智能优化
		List<List<Map<String, Object>>> optimizeResult = optimizeService.optimize(tripDetailList, days, hour, type);

		// 所有行程天id
		List<Long> planDayId = new ArrayList<Long>();

		// 查询目的地城市详细信息
		Map<String, Object> cityMap = new HashMap<String, Object>();
		cityMap.put("cityCode", cityCode);
		List<City> cities = cityService.list(cityMap);
		String cityName = "";
		if (!cities.isEmpty()) {
			City city = cities.get(0);
			cityName = city.getName();
		}
		// 新的行程
		Plan plan = new Plan();
		long userId = MemberService.getCurrentUserId();
		plan.setUserId(userId);
		plan.setStatus(Plan.PLAN_STATUS_TRUE);
		plan.setPlatform(Plan.PLATFORM_UNCERTAIN);
		plan.setCityId(cityCode);
		// 处理行程名称
		String planName = "";
		if (userId == 0) {
			planName = "您的" + cityName + "行程计划";
		} else {
			User user = userService.info(userId);
			planName = user.getNickname() + cityName + "行程计划";
		}
		plan.setPlanName(planName);
		// 处理行程封面和描述
		List<Recplan> recplans = recplanService.list(cityMap);
		if (!recplans.isEmpty()) {
			int max = recplans.size();
			Random random = new Random();
			int recplanIndex = random.nextInt(max);
			Recplan recplan = recplans.get(recplanIndex);
			if (recplan.getCoverPath() != null && !"".equals(recplan.getCoverPath())) {
				plan.setCoverPath(recplan.getCoverPath());
			}
			if (recplan.getCoverSmall() != null && !"".equals(recplan.getCoverSmall())) {
				plan.setCoverSmall(recplan.getCoverSmall());
			}
			if (recplan.getDescription() != null && !"".equals(recplan.getDescription())) {
				plan.setDescription(recplan.getDescription());
			}
		}

		// 处理出发时间
		if (!"".equals(startDate)) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date date = formatter.parse(startDate);
				plan.setStartTime(date);
			} catch (ParseException e) {
				logger.error("格式化出发时间出现错误");
			}
		}

		planService.insert(plan);

		Map<String, Object> hotelTrip = null;
		int dayOrder = 0;
		for (int i = 0; i < optimizeResult.size(); i++) {

			// 新的行程天
			PlanDay planDay = new PlanDay();
			planDay.setPlanId(plan.getId());
			planDay.setDays(i);
			planDaysService.insert(planDay);
			planDayId.add(planDay.getId());

			List<Map<String, Object>> dayList = optimizeResult.get(i);
			// 添加酒店信息
			if (hotelTrip == null) {
				hotelTrip = planDaysService.checkHotel(dayList, tripDetailList);
			}
			planDaysService.addHotel(hotelTrip, dayList);

			int orderNum = 1;
			dayOrder++;
			for (int j = 0; j < dayList.size(); j++) {
				Map<String, Object> tripMap = dayList.get(j);

				PlanTrip planTrip = new PlanTrip();
				planTrip.setDay(dayOrder);
				planTrip.setPlanId(plan.getId());
				planTrip.setPlanDaysId(planDay.getId());
				planTrip.setSource(1);
				planTrip.setScenicId(Long.parseLong(tripMap.get("scenicId").toString()));
				planTrip.setTripType(Integer.parseInt(tripMap.get("tripType").toString()));
				if (sourceMap.get(planTrip.getScenicId()) != null) {
					planTrip.setSourceId(sourceMap.get(planTrip.getScenicId()));
				}
				if (delicacyMap.get(planTrip.getScenicId()) != null) {
					planTrip.setDelicacyId(delicacyMap.get(planTrip.getScenicId()));
				}
				planTrip.setOrderNum(orderNum);
				planTrip.setTravelType(TravelType.DRIVING.value());
				orderNum++;
				planTripService.insert(planTrip);
			}
		}

		// 处理交通枢纽数据
		if (transTrip.size() > 0) {
			// 初始交通
			PlanTrip sTransPlanTrip = transTrip.get(0);
			sTransPlanTrip.setPlanId(plan.getId());
			sTransPlanTrip.setPlanDaysId(planDayId.get(0));
			sTransPlanTrip.setDay(1);
			planTripService.insert(sTransPlanTrip);
			if (transTrip.size() > 1) {
				// 返回交通
				PlanTrip eTransPlanTrip = transTrip.get(1);
				eTransPlanTrip.setPlanId(plan.getId());
				eTransPlanTrip.setPlanDaysId(planDayId.get(planDayId.size() - 1));
				eTransPlanTrip.setDay(planDayId.size());
				planTripService.insert(eTransPlanTrip);
			}
		}

		Map<String, Object> restMap = new HashMap<String, Object>();
		restMap.put("planId", plan.getId());
		return restMap;
	}

	// 插入整个行程（或者编辑行程）
	@Transactional
	public Map<String, Object> editPlan(Map<String, Object> paramMap) {
		// 获取行程数据并格式化成类
		String planData = (String) paramMap.get("planData");
		Plan plan = new Plan();
		try {
			plan = new ObjectMapper().readValue(planData, Plan.class);
		} catch (Exception e) {
			Validate.isTrue(false, ErrorCode.ERROR_51001, "格式化错误");
		}
		// platform
		if (paramMap.get("platform") != null) {
			int platform = Integer.parseInt(paramMap.get("platform").toString());
			plan.setPlatform(platform);
		}
		// 根据有没有行程id判断是插入还是编辑行程
		long userId = MemberService.getCurrentUserId();
		if (plan.getId() == null || plan.getId() == 0) {
			if (userId != 0) {
				plan.setUserId(userId);
			}
			planService.insert(plan);
		}
		Plan sourcePlan = planService.info(plan.getId());
		if (sourcePlan.getUserId() != 0) {
			Validate.dataAuthorityCheck(sourcePlan);
		}
		planService.update(plan);
		planDaysService.deleteByPlan(plan.getId());
		planCityService.deleteByPlan(plan.getId());
		planTripService.deleteByPlan(plan.getId());

		String planCityCode = "";

		long planId = plan.getId();

		// 所有城市列表
		savePlanWithStatistic(plan, userId, planId);

		plan.setValid(true);
		plan.setPublicFlag(true);
		plan.setRecommendFlag(false);
		
		planCityCode = plan.getCityId() + "";
		// 处理行程名
		fillPlanName(plan, userId, planCityCode);
		
		// 处理行程封面
		PlanTrip scenicTrip = planService.getFirstTripByType(plan, TripType.SCENIC);
		if (scenicTrip != null)
		{
			Map<String, Object> scenicInfo = (Map<String, Object>) scenicInfoService.info(scenicTrip.getScenicId());
			if (scenicInfo != null && scenicInfo.get("coverLarge") != null) {
				plan.setCoverPath(scenicInfo.get("coverLarge").toString());
			}
			if (scenicInfo != null && scenicInfo.get("coverSmall") != null) {
				plan.setCoverSmall(scenicInfo.get("coverSmall").toString());
			}
		}
		

		planService.update(plan);

		Map<String, Object> res = new HashMap<String, Object>();
		res.put("planId", plan.getId());
		res.put("planName", plan.getPlanName());
		return res;
	}

	/**
	 * 保存行程的天、城市以及节点
	 * @Title: savePlanWithStatistic
	 * @email shiqingju@hmlyinfo.com
	 * @date 2015年11月16日 下午4:27:31
	 * @version 
	 *
	 * @param plan
	 * @param userId
	 * @param planId
	 *
	 * @return void
	 * @throws
	 */
	private void savePlanWithStatistic(Plan plan, long userId, long planId) {
		// 行程花费
		int planCost = 0;
		// 包含套票的行程花费
		int includeSeasonticketCost = 0;
		// 行程天数，自动统计程序生成
		int planDays = 0;
		// 行程酒店花费
		int planHotelCost = 0;
		// 行程交通花费
		int planTravelCost = 0;
		// 行程门票花费
		int planTicketCost = 0;
		// 行程的餐厅花费
		int planFoodCost = 0;
		// 行程门票花费（包含套票）
		int planSeasonticketCost = 0;

		// 获取行程天列表
		List<PlanDay> planDayList = plan.getPlanDayList();
		if (planDayList == null) {
			Validate.isTrue(false, ErrorCode.ERROR_51001);
		}
		planDays = planDayList.size();
		planDaysService.setHotelCost(planDayList);
		
		Set<Long> scenicSet = Sets.newHashSet();
		for (int i = 0; i < planDayList.size(); i++) {
			
			PlanDay planDay = planDayList.get(i);
			// 统计行程中各项花费
			planCost += planDay.getTotalPrice();
			includeSeasonticketCost += planDay.getIncludeSeasonticketCost();
			planTravelCost += planDay.getTrafficCost();
			planSeasonticketCost += planDay.getTotalSeasonTicketPrice();
			
			planTicketCost += planDay.getTicketCost();
			planHotelCost += planDay.getHotelCost();
			planFoodCost += planDay.getFoodCost();
			
			planDay.setPlanId(planId);
			
			// 处理行程所经过的城市
			for (PlanCity plancity : planDay.getCityList())
			{
				scenicSet.add(plancity.getCityId());
				// 第一个城市设置为行程城市
				if (plan.getCityId() == 0)
				{
					plan.setCityId(plancity.getCityId());
				}
			}
			
		}
		// 插入行程天
		planDaysService.insertMore(planDayList);
		savePlanCityAndTrip(userId, planId, planDayList);
		
		if (userId == 0) {
			plan.setStatus(Plan.PLAN_STATUS_FALSE);
		} else {
			plan.setStatus(Plan.PLAN_STATUS_TRUE);
		}
		plan.setPlanCost(planCost);
		plan.setIncludeSeasonticketCost(includeSeasonticketCost);
		plan.setPlanDays(planDays);
		plan.setPlanSeasonticketCost(planSeasonticketCost);
		plan.setPlanTravelCost(planTravelCost);
		plan.setPlanTicketCost(planTicketCost);
		plan.setPlanHotelCost(planHotelCost);
		plan.setPlanFoodCost(planFoodCost);
		plan.setCityIds(ListUtil.join(scenicSet, ","));
		
	}
	
	/**
	 * 批量保存行程天和行程节点
	 * @Title: savePlanCityAndTrip
	 * @email shiqingju@hmlyinfo.com
	 * @date 2015年11月16日 下午6:23:29
	 * @version 
	 *
	 * @param userId
	 * @param planId
	 * @param planDayList
	 *
	 * @return void
	 * @throws
	 */
	private void savePlanCityAndTrip(long userId, long planId, List<PlanDay> planDayList)
	{
		List<PlanCity> cityList = Lists.newArrayList();
		List<PlanTrip> tripList = Lists.newArrayList();
		
		for (PlanDay planday : planDayList)
		{
			for (PlanCity planCity : planday.getCityList())
			{
				planCity.setPlanId(planId);
				planCity.setPlanDaysId(planday.getId());
				
				cityList.add(planCity);
			}
		}
		
		planCityService.insertList(cityList);
		
		for (PlanCity city : cityList)
		{
			for (int i = 0; i < city.getPlanTripList().size(); i++)
			{
				PlanTrip trip = city.getPlanTripList().get(i);
				trip.setOrderNum(i);
				trip.setPlanId(planId);
				trip.setPlanDaysId(city.getPlanDaysId());
				trip.setUserId(userId);
				
				tripList.add(trip);
			}
		}
		
		planTripService.insertMore(tripList);
	}
	
	/**
	  * setPlanCity(设置行程城市的字段)
	  *
	  * @Title: savePlanCity
	  * @author: shiqingju
	  * @email shiqingju@hmlyinfo.com
	  * @return void    返回类型
	  * @throws
	 */
	private void setPlanCity(Long userId, Long planId, Long planDayId, List<PlanCity> planCityList)
	{
		for (PlanCity planCity : planCityList)
		{
			planCity.setPlanId(planId);
			planCity.setPlanDaysId(planDayId);
			
			if (planCity.getPlanTripList() != null)
			{
				setPlanTrip(userId, planId, planDayId, planCity.getId(), planCity.getPlanTripList());
			}
		}
	}
	
	/**
	  * setPlanTrip(设置行程节点的字段)
	  *
	  * @author shiqingju
	  * @param @param userId
	  * @param @param planId
	  * @param @param planDayId
	  * @param @param planCityId
	  * @param @param planTripList    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	private void setPlanTrip(Long userId, Long planId, Long planDayId, Long planCityId, List<PlanTrip> planTripList)
	{
		int orderNum = 0;
		for (PlanTrip planTrip : planTripList) {
			orderNum++;
			planTrip.setId(null);
			planTrip.setOrderNum(orderNum);
			planTrip.setPlanId(planId);
			planTrip.setPlanDaysId(planDayId);
			planTrip.setUserId(userId);

		}
	}
	

	private long getPlanScenicId(String planCityCode, String scenicCityCode) {
		List<Destination> destinations = destinationService.list(Collections.<String, Object> singletonMap("cityCode", scenicCityCode));
		if (!destinations.isEmpty()) {
			return destinations.get(0).getScenicId();
		}
		scenicCityCode = scenicCityCode.substring(0, 4) + "00";
		destinations = destinationService.list(Collections.<String, Object> singletonMap("cityCode", scenicCityCode));
		if (!destinations.isEmpty()) {
			return destinations.get(0).getScenicId();
		}
		List<City> cities = cityService.list(Collections.<String, Object> singletonMap("cityCode", planCityCode));
		if (cities.isEmpty()) {
			// 厦门
			logger.info("cityCode=" + planCityCode + "对应的目的地未找到");
			return 13444;
		}
		long fatherCityCode = cities.get(0).getFather();
		List<Destination> fatherDestinations = destinationService.list(Collections
				.<String, Object> singletonMap("cityCode", fatherCityCode));
		if (!fatherDestinations.isEmpty()) {
			return fatherDestinations.get(0).getScenicId();
		}
		return 0;
	}

	private void fillPlanName(Plan plan, long userId, String planCityCode) {
		if (plan.getPlanName() == null || "".equals(plan.getPlanName())) {
			String planName = "";
			Map<String, Object> cityMap = new HashMap<String, Object>();
			cityMap.put("cityCode", planCityCode);
			List<City> cityList = cityService.list(cityMap);
			if (cityList.size() > 0) {
				planName = planName + cityList.get(0).getName() + "的";
			}
			Map<String, Object> planMap = new HashMap<String, Object>();
			planMap.put("cityId", plan.getCityId());
			planMap.put("userId", userId);
			if (userId != 0) {
				planMap.put("status", 1);
			}
			List<Plan> planList = planService.list(planMap);
			int num = planList.size() + 1;
			planName = planName + "行程规划";
			if (num > 1) {
				planName += num;
			}
			plan.setPlanName(planName);
		}
	}

	private int getCityCode(PlanTrip planTrip) {
		if (planTrip.getTripType() == TripType.SCENIC.value()) {
			Map<String, Object> scenicMap = (Map<String, Object>) scenicInfoService.info(planTrip.getScenicId());
			return Integer.parseInt(scenicMap.get("cityCode").toString());
		} else if (planTrip.getTripType() == TripType.HOTEL.value()) {
			return ctripHotelService.info(planTrip.getScenicId()).getCityCode();
		} else if (planTrip.getTripType() == TripType.RESTAURANT.value()){
            return restaurantService.info(planTrip.getScenicId()).getCityCode();
        } else {
            // 不计算大交通所在城市
            return 0;
        }
	}
}
