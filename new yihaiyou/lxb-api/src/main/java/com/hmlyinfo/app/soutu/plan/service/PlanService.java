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
import java.util.Set;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hmlyinfo.app.soutu.account.domain.ThridPartyUser;
import com.hmlyinfo.app.soutu.account.domain.User;
import com.hmlyinfo.app.soutu.account.mapper.ThridPartyUserMapper;
import com.hmlyinfo.app.soutu.account.service.MemberService;
import com.hmlyinfo.app.soutu.account.service.UserService;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.base.properties.Config;
import com.hmlyinfo.app.soutu.common.service.DictService;
import com.hmlyinfo.app.soutu.delicacy.domain.Restaurant;
import com.hmlyinfo.app.soutu.delicacy.service.RestaurantService;
import com.hmlyinfo.app.soutu.hotel.domain.CtripHotel;
import com.hmlyinfo.app.soutu.hotel.service.CtripHotelService;
import com.hmlyinfo.app.soutu.hotel.service.HotelService;
import com.hmlyinfo.app.soutu.plan.domain.Collection;
import com.hmlyinfo.app.soutu.plan.domain.Plan;
import com.hmlyinfo.app.soutu.plan.domain.PlanCity;
import com.hmlyinfo.app.soutu.plan.domain.PlanDay;
import com.hmlyinfo.app.soutu.plan.domain.PlanTrip;
import com.hmlyinfo.app.soutu.plan.domain.type.TripType;
import com.hmlyinfo.app.soutu.plan.mapper.CollectionMapper;
import com.hmlyinfo.app.soutu.plan.mapper.PlanMapper;
import com.hmlyinfo.app.soutu.scenic.domain.City;
import com.hmlyinfo.app.soutu.scenic.domain.ScenicInfo;
import com.hmlyinfo.app.soutu.scenic.service.CityService;
import com.hmlyinfo.app.soutu.scenic.service.ScenicInfoService;
import com.hmlyinfo.app.soutu.scenicTicket.service.WeiXinService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import com.hmlyinfo.base.util.ListUtil;
import com.hmlyinfo.base.util.StringUtil;
import com.hmlyinfo.base.util.Validate;

@Service
public class PlanService extends BaseService<Plan, Long> {

	Logger									logger	= Logger.getLogger(PlanService.class);

	@Autowired
	private PlanMapper<Plan>				mapper;
	@Autowired
	private CityService						cityService;
	@Autowired
	private PlanDaysService					planDaysService;
	@Autowired
	private PlanTripService					planTripService;
	@Autowired
	private ScenicInfoService				scenicInfoService;
	@Autowired
	private UserService						userService;
	@Autowired
	private CollectionMapper<Collection>	collectMapper;
	@Autowired
	private PlanCityService					planCityService;
	@Autowired
	CtripHotelService						ctripHotelService;
	@Autowired
	RestaurantService						restaurantService;
	@Autowired
	private DictService						dictService;
	@Autowired
	private HotelService					hotelService;
	@Autowired
	private WeiXinService					weiXinService;
	@Autowired
	private ThridPartyUserMapper<ThridPartyUser> thridPartyUserMapper;

	@Override
	public BaseMapper<Plan> getMapper() {
		return mapper;
	}

	@Override
	public String getKey() {
		return "id";
	}

	/**
	 * 获取用于编辑的行程信息
	 * <ul>
	 * <li>必选:行程id{planId}</li>
	 * </ul>
	 * 
	 * @return plan
	 */
	public Plan infoTrip(long planId) {
		// 校验用户是否正确
		Plan plan = info(planId);
		if (!plan.isPublicFlag()) {
			Validate.isTrue(plan.getUserId() == MemberService.getCurrentUserId(), ErrorCode.ERROR_53003);
		}

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("planId", planId);
		plan.setDayCount(planDaysService.count(paramMap));

		List<PlanTrip> trips = planTripService.list(paramMap);
		List<Long> ids = new ArrayList<Long>();
		for (PlanTrip trip : trips) {
			if (trip.getTripType() == TripType.SCENIC.value()) {
				ids.add(trip.getScenicId());
			}
		}
		List<ScenicInfo> scenicInfos = scenicInfoService.listBrief(Collections.<String, Object> singletonMap("ids", ids));
		int totalPrice = 0;
		for (ScenicInfo scenicInfo : scenicInfos) {
			totalPrice += scenicInfo.getPrice();
		}
		plan.setTotalPrice(totalPrice);

		User user = userService.info(plan.getUserId());
		plan.setUser(user);
		return plan;
	}

	/**
	 * 系统列出热门行程
	 * <ul>
	 * <li>可选:条数{pageSize}</li>
	 * </ul>
	 * 
	 * @return planList
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getHotPlan(Map<String, Object> paramMap) {

		paramMap.put("homepageFlag", true);
		List<Map<String, Object>> planList = (List) mapper.list(paramMap);
		List<String> userList = ListUtil.getIdList(planList, "userId");

		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("ids", userList);

		List<User> userNameList = userService.list(userMap);

		Map<String, String> joinMap = new HashMap<String, String>();
		joinMap.put("userId", "id");

		return ListUtil.listJoin(planList, userNameList, joinMap, "user", null);
	}

	/**
	 * 创建行程计划
	 * <ul>
	 * <li>必选:景点id{scenicId}</li>
	 * </ul>
	 * 
	 * @return plan
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public Plan creatPlan(Plan plan) {
		// 取得当前登陆用户
		User user = userService.info(MemberService.getCurrentUserId());
		// user = userService.info(1L);
		// 取得昵称
		String nickName = user.getNickname();
		// 获取用户ID和景点ID,城市ID
		long userId = user.getId();
		long scenicId = plan.getScenicId();
		// 通过景点ID获取景点信息
		Map<String, Object> scenicMap = (Map<String, Object>) scenicInfoService.info(scenicId);

		// 取得景点名称和景点封面大图，设置“XX用户的行程计划”
		String scenicName = scenicMap.get("name").toString();
		String coverPath = scenicMap.get("coverLarge").toString();
		String coverSmall = scenicMap.get("coverSmall").toString();

		if (plan.getPlanName() == null) {
			String planName = nickName + "的" + scenicName + "行程";
			plan.setPlanName(planName);
		}

		// 将所有数据放入plan中
		plan.setUserId(userId);
		plan.setScenicId(scenicId);
		plan.setCoverPath(coverPath);
		plan.setCoverSmall(coverSmall);
		plan.setPublicFlag(true);
		plan.setRecommendFlag(false);
		/*
		 * if (plan.getStartTime() == null) { plan.setStartTime(new Date()); }
		 */
		plan.setCityId(Long.parseLong(scenicMap.get("cityCode").toString()));
		// 服务器端插入数据
		insert(plan);

		// 为这个行程新建第一天
		PlanDay newPlanDays = new PlanDay();
		newPlanDays.setPlanId(plan.getId());
		newPlanDays.setDays(1);
		planDaysService.insert(newPlanDays);

		// 新增行程城市
		PlanCity city = new PlanCity();
		city.setPlanId(plan.getId());
		city.setPlanDaysId(newPlanDays.getId());
		city.setCityId(Long.parseLong(scenicMap.get("cityCode").toString()));
		planCityService.insert(city);

		return plan;
	}

	/**
	 * 
	 * 通过景点ID返回公开的行程计划
	 * <ul>
	 * <li>必选：景点id{scenicId}</li>
	 * </ul>
	 * 
	 * @return planList
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getRelatePlan(Map<String, Object> paramMap) {
		List<PlanTrip> planTripList = planTripService.list(paramMap);
		List<Long> ids = new ArrayList<Long>();
		for (PlanTrip planTrip : planTripList) {
			ids.add(planTrip.getPlanId());
		}
		paramMap.clear();
		paramMap.put("ids", ids);
		paramMap.put("publicFlag", 1);
		List<Map<String, Object>> planList = (List) mapper.list(paramMap);
		List<String> userList = ListUtil.getIdList(planList, "userId");

		List<User> userNameList = userService.list(Collections.<String, Object> singletonMap("ids", userList));

		Map<String, String> joinMap = new HashMap<String, String>();
		joinMap.put("userId", "id");

		return ListUtil.listJoin(planList, userNameList, joinMap, "user", null);
	}

	public ActionResult countRelatePlan(Map<String, Object> paramMap) {
		List<PlanTrip> planTripList = planTripService.list(paramMap);
		List<Long> ids = new ArrayList<Long>();
		for (PlanTrip planTrip : planTripList) {
			ids.add(planTrip.getPlanId());
		}
		paramMap.clear();
		paramMap.put("ids", ids);
		paramMap.put("publicFlag", 1);
		return countAsResult(paramMap);
	}

	/**
	 * 
	 * 通过planId修改小贴士内容
	 * <ul>
	 * <li>必选：行程id{planId}</li>
	 * <li>必选:内容{tipsContent}</li>
	 * </ul>
	 * 
	 * @return map
	 */
	public Map<String, Object> updateTips(Map<String, Object> paramMap) {
		String planId = paramMap.get("planId").toString();

		Object content = paramMap.get("tipsContent");
		Plan plan = mapper.selById(planId);
		Validate.dataAuthorityCheck(plan);
		if (content != null) {
			plan.setTipsContent(content.toString());
		} else {
			plan.setTipsContent("");
		}
		mapper.update(plan);

		return paramMap;
	}

	/**
	 * 
	 * 通过planId修改出发时间
	 * <ul>
	 * <li>必选：行程id{planId}</li>
	 * </ul>
	 * 
	 * @return map
	 * @throws ParseException
	 */
	public Plan updateTime(Map<String, Object> paramMap) throws ParseException {
		String planId = paramMap.get("planId").toString();
		Object starttime = paramMap.get("startTime");
		Plan plan = mapper.selById(planId);
		Validate.dataAuthorityCheck(plan);
		if (starttime != null) {
			String timeString = starttime.toString();
			if (!StringUtil.isEmpty(timeString)) {
				Date timeDate = new SimpleDateFormat("yyyy-MM-dd").parse(timeString);
				plan.setStartTime(timeDate);
			} else {
				plan.setStartTime(null);
			}
		} else {
			plan.setStartTime(null);
		}
		mapper.updateStartTime(plan);

		return plan;
	}

	/**
	 * 
	 * 通过planId修改行程名
	 * <ul>
	 * <li>必选：行程id{planId}</li>
	 * <li>必选:内容{tipsContent}</li>
	 * </ul>
	 * 
	 * @return map
	 */
	public Map<String, Object> updatePlanName(Map<String, Object> paramMap) {
		String planId = paramMap.get("planId").toString();
		String planName = paramMap.get("planName").toString();
		Plan plan = mapper.selById(planId);
		Validate.dataAuthorityCheck(plan);
		plan.setPlanName(planName);
		mapper.update(plan);

		return paramMap;
	}

	public boolean addPlanView(String planId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", planId);
		int result = mapper.addView(params);
		return result > 0;
	}

	/**
	 * 
	 * 通过userId返回收藏行程
	 * <ul>
	 * <li>必选：城市id{cityId}</li>
	 * </ul>
	 * 
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List<Plan> getCollection(Map<String, Object> paramMap) {
		paramMap.put("collectUserId", paramMap.get("userId"));
		paramMap.remove("userId");
		List<Plan> planList = mapper.list(paramMap);
		List<String> userList = ListUtil.getIdList(planList, "userId");

		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("ids", userList);

		List<User> userNameList = userService.list(userMap);

		Map<String, String> joinMap = new HashMap<String, String>();
		joinMap.put("userId", "id");

		return (List) ListUtil.listJoin(planList, userNameList, joinMap, "user", null);
	}

	public ActionResult countCollection(Map<String, Object> paramMap) {
		paramMap.put("collectUserId", paramMap.get("userId"));
		paramMap.remove("userId");
		return countAsResult(paramMap);
	}

	/**
	 * 
	 * 删除收藏行程
	 * <ul>
	 * <li>必选：我的收藏id{id}</li>
	 * </ul>
	 * 
	 */
	public void delCollection(Map<String, Object> paramMap) {
		String id = paramMap.get("id").toString();
		Collection collect = collectMapper.selById(id);
		Validate.dataAuthorityCheck(collect);
		paramMap.put("planId", collect.getPlanId());
		paramMap.put("collectNum", true);
		delAllNum(paramMap);
		collectMapper.del(id);
	}

	/**
	 * 系统查询计划的行程信息
	 * <ul>
	 * <li>必选:行程id{planId}</li>
	 * </ul>
	 * 
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List listPlanTrips(long planId) {
		Plan plan = info(planId);
		// 行程必须存在
		Validate.notNull(plan, ErrorCode.ERROR_53001);
		// 行程必须公开，或者用户查看自己的行程
		Validate.isTrue(plan.isPublicFlag() || plan.getUserId() == MemberService.getCurrentUserId(), ErrorCode.ERROR_53003);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("planId", planId);

		List<PlanTrip> tripList = planTripService.list(paramMap);
		List<PlanDay> days = planDaysService.list(Collections.<String, Object> singletonMap("planId", planId));
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

		Map<String, Map<String, Object>> dayMap = new HashMap<String, Map<String, Object>>();
		for (PlanDay day : days) {

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("dayInfo", day);
			map.put("tripList", new ArrayList<Map<String, Object>>());
			resultList.add(map);
			dayMap.put(day.getId().toString(), map);
		}
		List<Long> scenicIdList = ListUtil.getIdList(tripList, "scenicId");
		if (!scenicIdList.isEmpty()) {
			Map<String, Object> scenicMap = new HashMap<String, Object>();
			scenicMap.put("ids", scenicIdList);

			List<ScenicInfo> scenicList = scenicInfoService.list(scenicMap);
			List<CtripHotel> hotelList = ctripHotelService.list(scenicMap);
			List<Restaurant> restaurantList = restaurantService.list(scenicMap);

			Map<String, CtripHotel> hotelMap = new HashMap<String, CtripHotel>();
			Map<String, Restaurant> restaurantMap = new HashMap<String, Restaurant>();
			for (CtripHotel ctripHotel : hotelList) {
				hotelMap.put(ctripHotel.getId().toString(), ctripHotel);
			}
			for (Restaurant restaurant : restaurantList) {
				restaurantMap.put(restaurant.getId().toString(), restaurant);
			}
			List<Map<String, Object>> trips = ListUtil.listJoin(tripList, scenicList, "scenicId=id", "scenic", null);
			for (Map<String, Object> trip : trips) {
				if (Integer.valueOf(trip.get("tripType").toString()) == TripType.HOTEL.value()) {
					trip.put("scenic", hotelMap.get(trip.get("scenicId").toString()));
				} else if (Integer.valueOf(trip.get("tripType").toString()) == TripType.RESTAURANT.value()) {
					trip.put("scenic", restaurantMap.get(trip.get("scenicId").toString()));
				}
				((ArrayList) dayMap.get(trip.get("planDaysId").toString()).get("tripList")).add(trip);
			}
		}
		return resultList;
	}

	/**
	 * 收藏，引用，评论，分享，浏览数加一
	 * <ul>
	 * <li>必选:{paramMap}</li>
	 * </ul>
	 */
	public void addAllNum(Map<String, Object> paramMap) {
		mapper.updateAllNum(paramMap);
	}

	/**
	 * 收藏，引用，评论，分享，浏览数减一
	 * <ul>
	 * <li>必选:{paramMap}</li>
	 * </ul>
	 */
	public void delAllNum(Map<String, Object> paramMap) {
		mapper.delAllNum(paramMap);
	}

	/**
	 * 增加收藏
	 * <ul>
	 * <li>必选:行程ID{id}</li>
	 * </ul>
	 * 
	 */
	public void addCollect(Map<String, Object> paramMap) {
		Collection collect = new Collection();
		collect.setUserId(MemberService.getCurrentUserId());
		collect.setPlanId(Long.valueOf(paramMap.get("id").toString()));
		paramMap.put("collectNum", true);
		addAllNum(paramMap);
		collectMapper.insert(collect);
	}

	/**
	 * 添加所有景点的camcount数
	 * <ul>
	 * <li>必选:行程id{planId}</li>
	 * </ul>
	 * 
	 */
	public void addCamCount(Map<String, Object> paramMap) {
		List<PlanTrip> planTripList = planTripService.list(paramMap);
		List<Long> ids = new ArrayList<Long>();
		for (PlanTrip planTrip : planTripList) {
			ids.add(planTrip.getScenicId());
		}
		scenicInfoService.updateShareNum(ids);
	}

	@Transactional
	public void deletePlan(Long id) {
		Plan plan = info(id);
		Validate.dataAuthorityCheck(plan);
		planTripService.deleteByPlan(id);
		planDaysService.deleteByPlan(id);
		del(id.toString());
	}

	public List<Plan> listMyPlan(Map<String, Object> params) {
		Long userId = MemberService.getCurrentUserId();
		params.put("userId", userId);

		params.put("status", Plan.PLAN_STATUS_TRUE);// 获取有点击保存的数据

		List<String> columns = new ArrayList<String>();
		columns.add("id");
		columns.add("plan_name");
		columns.add("city_id");
		columns.add("plan_days");
		columns.add("plan_cost");
		columns.add("start_time");
		columns.add("cover_small");
		params.put("needColumns", columns);

		List<Plan> planList = mapper.list(params);

		// 每个行程所经过的所有城市的名称列表
		for (Plan plan : planList) {
			List<String> cityNames = new ArrayList<String>();
			String cityIds = plan.getCityIds();
			if (cityIds == null || "".equals(cityIds)) {
				continue;
			}
			String[] cityCodes = cityIds.split(",");
			for (String cityCode : cityCodes) {
				if (!"0".equals(cityCode)) {
					String cityName = dictService.getCityById(cityCode);
					cityNames.add(cityName);
				}
			}
			plan.setCityNames(cityNames);
		}

		// toupia
		/*
		 * for (Plan plan : planList) { Map<String, Object> result = new
		 * HashMap<String, Object>(); result.put("id", plan.getId());
		 * result.put("planName", plan.getPlanName()); result.put("cityId",
		 * plan.getCityId()); result.put("startTime", plan.getStartTime());
		 * result.put("coverSmall", plan.getCoverSmall());
		 * result.put("planDays", plan.getPlanDays()); result.put("totalPrice",
		 * plan.getTotalPrice()); JoinedPlan joinedPlan =
		 * joinedPlanMap.get(plan.getId()); if (joinedPlan != null) {
		 * result.put("votes", joinedPlan.getPollCount()); } else {
		 * result.put("votes", -1); } resultList.add(result); }
		 */
		return planList;
	}

	/*
	 * public List<Plan> listWithDaysAndCost(Map<String, Object> params) {
	 * List<Plan> list = listColumns(params); for (Plan plan : list) {
	 * Map<String, Object> paramMap = new HashMap<String, Object>();
	 * paramMap.put("planId", plan.getId()); int count =
	 * planDaysService.count(paramMap); List<PlanTrip> tripList =
	 * planTripService.list(paramMap); int totalPrice = 0; for (PlanTrip
	 * planTrip : tripList) { if (planTrip.getTripType() ==
	 * TripType.SCENIC.value()) { Map<String, Object> scenicInfo = (Map<String,
	 * Object>) scenicInfoService.info(planTrip.getScenicId()); if (scenicInfo
	 * != null) { totalPrice +=
	 * Integer.parseInt(scenicInfo.get("price").toString()); } } else if
	 * (planTrip.getTripType() == TripType.RESTAURANT.value()) { Restaurant
	 * restaurant = restaurantService.info(planTrip.getScenicId()); if
	 * (restaurant != null) { totalPrice += restaurant.getResPrice(); } } }
	 * plan.setDayCount(count); plan.setTotalPrice(totalPrice); } return list; }
	 */
	public List<Plan> listColumns(Map<String, Object> params) {
		return mapper.listColumns(params);
	}

	/**
	 * 
	 * 通过planId修改行程状态
	 * <ul>
	 * <li>必选：行程id{planId}</li>
	 * <li>必选:内容{status}</li>
	 * </ul>
	 * 
	 * @return map
	 */
	public Map<String, Object> updatePlanStatus(Map<String, Object> paramMap) {
		String planId = paramMap.get("planId").toString();
		Object statusObj = paramMap.get("status");
		int status = 0;
		if (null != statusObj) {
			try {
				status = Integer.parseInt(statusObj.toString());
			} catch (Exception e) {
				status = 0;
			}
		}
		Plan plan = mapper.selById(planId);
		Validate.dataAuthorityCheck(plan);
		plan.setStatus(status);
		mapper.update(plan);
		return paramMap;
	}

	// 查询当前用户所有行程中包含的城市
	public List<City> arrivedCityList() {
		long uid = MemberService.getCurrentUserId();
		Map<String, Object> planMap = new HashMap<String, Object>();
		planMap.put("userId", uid);
		planMap.put("ststus", 1);
		planMap.put("platform", Plan.PLATFORM_PC);
		// 查询该用户的所有行程
		List<Plan> planList = listColumns(planMap, Lists.newArrayList("city_ids"));
		Set<String> citySet = new HashSet<String>();
		String cityIdsString = "";
		// 遍历行程取出所有城市id
		for (int i = 0; i < planList.size(); i++) {
			Plan plan = planList.get(i);
			String cityIds = plan.getCityIds();

			cityIdsString = cityIdsString + cityIds + ",";
		}
		String[] cityList = cityIdsString.split(",");
		for (String cityCode : cityList) {
			if (!cityCode.equals("") && cityCode != null && !cityCode.equals("0")) {
				cityCode = cityCode.substring(0, 4) + "00";
				citySet.add(cityCode);
			}
		}
		Map<String, Object> cityParams = Maps.newHashMap();
		cityParams.put("cityCodes", citySet);
		List<City> citys = cityService.list(cityParams);
		return citys;
	}

	// 为行程插入用户
	public void insertUser(Map<String, Object> paramMap) {
		long planId = Long.parseLong((String) paramMap.get("planId"));
		long uid = MemberService.getCurrentUserId();
		Plan plan = info(planId);
		Validate.isTrue(plan.getUserId() == 0, ErrorCode.ERROR_51001, "行程中已经存在用户不能插入");
		plan.setUserId(uid);
		plan.setStatus(Plan.PLAN_STATUS_TRUE);
		try {
			update(plan);
		} catch (Exception e) {
			Validate.isTrue(false, ErrorCode.ERROR_50001, "数据插入失败：" + e.getMessage());
		}
	}

	// 为每一天推荐一个酒店
	public List<CtripHotel> reCtripHotels(Map<String, Object> paramMap) throws Exception {
		List<CtripHotel> ctripHotels = new ArrayList<CtripHotel>();
		//
		List<Map<String, Object>> locations = new ArrayList<Map<String, Object>>();
		String price = "";
		try {
			locations = new ObjectMapper().readValue(paramMap.get("locations").toString(), ArrayList.class);
			price = paramMap.get("price").toString();

		} catch (Exception e) {
			logger.error("数据格式化出现异常" + e.getMessage());
			throw e;
		}
		Map<String, Object> searchMap = new HashMap<String, Object>();
		if (!"".equals(price)) {
			searchMap.put("price", price);
		}
		// 经纬度数据
		for (int i = 0; i < locations.size(); i++) {
			Map<String, Object> loc = locations.get(i);

			if (loc.get("sLng") == null || loc.get("sLat") == null || loc.get("eLng") == null || loc.get("eLat") == null) {
				logger.error("前端传输数据有误");
				ctripHotels.add(null);
				continue;
			}
			searchMap.put("sLng", loc.get("sLng"));
			searchMap.put("sLat", loc.get("sLat"));
			searchMap.put("eLng", loc.get("eLng"));
			searchMap.put("eLat", loc.get("eLat"));
			CtripHotel hotel = recHotel(searchMap);
			ctripHotels.add(hotel);
		}

		return ctripHotels;

	}

	// 推荐酒店
	public CtripHotel recHotel(Map<String, Object> tripMap) {

		// 酒店距离第一个景点的距离
		int sdis = 2000;
		// 酒店距离最后一个景点的距离
		int edis = 4000;
		// 删选酒店条件
		Map<String, Object> hotelMap = new HashMap<String, Object>();
		hotelMap.put("lowestPrice", tripMap.get("costL"));
		hotelMap.put("highestPrice", tripMap.get("costU"));
		hotelMap.put("orderColumn", "score");
		hotelMap.put("orderType", "desc");
		if (tripMap.get("price") != null) {
			hotelMap.put("price", tripMap.get("price"));
		}

		hotelMap.put("longitude", tripMap.get("sLng"));
		hotelMap.put("latitude", tripMap.get("sLat"));
		hotelMap.put("distance", sdis + "");

		List<String> hotelIdList = hotelService.getHotelIdByOpenQuery(hotelMap);

		hotelMap.put("longitude", tripMap.get("eLng"));
		hotelMap.put("latitude", tripMap.get("eLat"));
		hotelMap.put("distance", edis + "");
		hotelMap.put("ids", hotelIdList);
		List<String> ehotels = hotelService.getHotelIdByOpenQuery(hotelMap);

		if (ehotels.isEmpty()) {
			return null;
		} else {
			return ctripHotelService.info(Long.parseLong(ehotels.get(0)));
		}

	}

	public List<Map<String, Object>> listIds(Map<String, Object> paramMap) {
		return mapper.listids(paramMap);
	}
	
	// 创建行程后消息推送
    public void pushCreatePlanMsg(Long planId)
    {
    	Plan plan = info(planId);
    	
    	// 判断行程是否已经推送过，或者行程不可用
    	if (plan.getPushFlag() == plan.PUSH_YES || plan.getStatus() != 1){
    		return;
    	}
    	
    	String url = Config.get("H5_SRV_ADDR_WWW") + "/plan/detail/" + planId;
    	String content = "您的专属“私人定制”已成功保存，请到自定义菜单--【行程】--【我的行程】查看相关结果。或者点击<a href='" + url + "'>" + "详情</a>查看";
		
		Long userId = plan.getUserId();
 		Map<String, Object> userMap = Maps.newHashMap();
 		userMap.put("userId", userId);
 		userMap.put("type", "3");
 		List<ThridPartyUser> userList = thridPartyUserMapper.list(userMap);
 		String openId = userList.get(0).getOpenId();
		
		try {
			weiXinService.wxPushKfMsg(openId, content);
		} catch (Exception e) {
			e.printStackTrace();
			Validate.isTrue(false, ErrorCode.ERROR_50001, "推送信息发送失败");
		} 
		
		plan.setPushFlag(plan.PUSH_YES);
		update(plan);
	}
    
    /**
     * 更新行程酒店，同时更新行程的酒店总花费
     * V2.7版本 PC端酒店编辑页面只保存酒店
     * @Title: updatePlanhotel
     * @email shiqingju@hmlyinfo.com
     * @date 2015年11月9日 下午2:30:57
     *
     * @param plan
     *
     * @return void
     * @throws
     */
    public void updatePlanhotel(Plan plan)
    {
    	Validate.notNull(plan.getId());
    	Plan srcPlan = info(plan.getId());
    	
    	// 自己的行程才可以编辑
    	Validate.isTrue(srcPlan.getUserId() == MemberService.getCurrentUserId(), ErrorCode.ERROR_50002);
    	
    	List<PlanDay> planDayList = plan.getPlanDayList();
    	planDaysService.setHotelCost(planDayList);
    	
    	int planHotelCost = 0;
    	for (PlanDay planDay : planDayList)
    	{
    		Validate.notNull(planDay.getDays());
    		
    		planHotelCost += planDay.getHotelCost();
    		planDay.setPlanId(plan.getId());
    		
    		planDaysService.updateHotel(planDay);
    	}
    	
    	srcPlan.setPlanHotelCost(planHotelCost);
    	
    	this.update(srcPlan);
    }
    
    /**
	 * 根据传入的类型获取行程的第一个节点
	 * @Title: getFirstTripByType
	 * @email shiqingju@hmlyinfo.com
	 * @date 2015年11月16日 下午3:30:46
	 * @version 2.7.0 行程封面现在使用的是行程中第一个景点的封面图片
	 *
	 * @param tripType
	 * @return
	 *
	 * @return PlanTrip
	 * @throws
	 */
	public PlanTrip getFirstTripByType(Plan plan, TripType tripType)
	{
		List<PlanTrip> tripList = Lists.newArrayList();
		
		for (PlanDay day : plan.getPlanDayList())
		{
			for (PlanCity city : day.getCityList())
			{
				for (PlanTrip trip : city.getPlanTripList())
				{
					if (trip.getTripType() == tripType.value())
					{
						tripList.add(trip);
						break;
					}
				}
			}
		}
		
		return ListUtil.getSingle(tripList);
	}
    
}
