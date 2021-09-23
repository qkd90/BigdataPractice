package com.hmlyinfo.app.soutu.plan.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hmlyinfo.app.soutu.account.service.MemberService;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.delicacy.domain.Delicacy;
import com.hmlyinfo.app.soutu.delicacy.domain.DelicacyRes;
import com.hmlyinfo.app.soutu.delicacy.domain.Restaurant;
import com.hmlyinfo.app.soutu.delicacy.service.DelicacyResService;
import com.hmlyinfo.app.soutu.delicacy.service.DelicacyService;
import com.hmlyinfo.app.soutu.delicacy.service.RestaurantService;
import com.hmlyinfo.app.soutu.hotel.domain.CtripHotel;
import com.hmlyinfo.app.soutu.hotel.service.CtripHotelService;
import com.hmlyinfo.app.soutu.plan.domain.OptimizeOption;
import com.hmlyinfo.app.soutu.plan.domain.PlanDay;
import com.hmlyinfo.app.soutu.plan.domain.PlanTrip;
import com.hmlyinfo.app.soutu.plan.domain.type.TravelType;
import com.hmlyinfo.app.soutu.plan.domain.type.TripType;
import com.hmlyinfo.app.soutu.plan.mapper.PlanDaysMapper;
import com.hmlyinfo.app.soutu.scenic.domain.City;
import com.hmlyinfo.app.soutu.scenic.domain.ScenicInfo;
import com.hmlyinfo.app.soutu.scenic.service.CityService;
import com.hmlyinfo.app.soutu.scenic.service.ScenicInfoService;
import com.hmlyinfo.base.exception.BizValidateException;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.persistent.PageDto;
import com.hmlyinfo.base.service.BaseService;
import com.hmlyinfo.base.util.ListUtil;
import com.hmlyinfo.base.util.ParamUtil;
import com.hmlyinfo.base.util.Validate;

@Service
public class PlanDaysService extends BaseService<PlanDay, Long> {

	private static final Logger		logger					= Logger.getLogger(PlanDaysService.class);

	private static final int		GULANGYU_SCENIC_ID		= 13165;

	private static final long		ZHONGSHAN_RUJIA_HOTEL	= 744314;

	private static final int		TYPE_TRANSIT			= 1;
	private static final int		TYPE_DRIVING			= 2;
	private static final int		TYPE_WALKING			= 3;

	private static final String		HOTEL_PREFIX			= "H";
	private static final String		SCENIC_PREFIX			= "S";
	private static final String		RESTAURANT_PREFIX		= "R";

	// 原来景点
	private static final int		SCENIC_TYPE_OLD			= 1;
	// 原来景点但是改变了所在天数
	private static final int		SCENIC_TYPE_CHANGE		= 2;
	// 新加景点
	private static final int		SCENIC_TYPE_NEW			= 3;

	// 每天游玩时间，超过则为紧凑
	// 游玩时间少于下限，为宽松
	private static final int		DAY_LOOSE				= 1;
	// 游玩时间多于上限，为紧凑
	private static final int		DAY_COMPACT				= 2;
	// 游玩时间满足要求
	private static final int		DAY_NORMAL				= 3;
	// 四种时间上下限
	public final static int[][]		HOUR					= { { 420, 420 }, { 360, 480 }, { 480, 690 }, { 690, 960 } };

	private static final String		BAIDU_STATIC_MAP_URL	= "http://api.map.baidu.com/staticimage?width=280&height=280";

	private final int				MIN_PRICE				= 200;
	private final int				MAX_PRICE				= 400;
	private final int				MAX_DISTANCE			= 2000;

	@Autowired
	private PlanDaysMapper<PlanDay>	mapper;
	@Autowired
	private PlanTripService			planTripService;
	@Autowired
	private PlanService				planService;
	@Autowired
	private ScenicInfoService		scenicInfoService;
	@Autowired
	private CtripHotelService		hotelService;
	@Autowired
	private RestaurantService		restaurantService;
	@Autowired
	private OptimizeService			optimizeService;
	@Autowired
	private CityService				cityService;
	@Autowired
	private DelicacyResService		delicacyResService;
	@Autowired
	private DelicacyService			delicacyService;

	@Override
	public BaseMapper<PlanDay> getMapper() {
		// TODO Auto-generated method stub
		return mapper;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return "id";
	}

	/**
	 * 添加行程计划天
	 * <ul>
	 * <li>必选:行程id{planId}</li>
	 * <li>必选:第若干天{days}</li>
	 * </ul>
	 * 
	 * @return
	 */
	@Transactional
	public PlanDay addDays(PlanDay planDays) {
		// 校验用户是否正确
		long userId = MemberService.getCurrentUserId();
		Validate.isTrue(userId == planService.info(planDays.getPlanId()).getUserId(), ErrorCode.ERROR_53003);

		// 取得该plan的天集合
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("planId", planDays.getPlanId());
		List<PlanDay> dayList = list(paramMap);
		// 更新同一个行程下其他天的天数
		for (int i = 0; i < dayList.size(); ++i) {
			if (dayList.get(i).getDays() >= planDays.getDays()) {
				dayList.get(i).setDays(dayList.get(i).getDays() + 1);
				update(dayList.get(i));
			}
		}
		insert(planDays);
		return planDays;
	}



	/**
	 * 修改行程计划天
	 * <ul>
	 * <li>必选:行程天id数组{days}</li>
	 * </ul>
	 * 
	 * @return
	 */
	@Transactional
	public List<PlanDay> updateDays(List<Long> daysIdList) {
		long planId = info(daysIdList.get(0)).getPlanId();
		// 校验用户是否正确
		long userId = MemberService.getCurrentUserId();
		Validate.isTrue(userId == planService.info(planId).getUserId(), ErrorCode.ERROR_53003);

		List<PlanDay> planDaysList = new ArrayList<PlanDay>();
		for (int i = 0; i < daysIdList.size(); ++i) {
			PlanDay planDays = info(daysIdList.get(i));
			planDays.setDays(i + 1); // 数组是0开始的所以加1
			update(planDays);
			planDaysList.add(planDays);
		}
		return planDaysList;
	}

	/**
	 * 查询当天所有景点信息
	 * <ul>
	 * <li>必选:行程id{planId}</li>
	 * <li>必选:第若干天{days}</li>
	 * <li>url:/api/plan/days/list</li>
	 * </ul>
	 * 
	 * @return
	 */
	public List<PlanTrip> listTrip(Map<String, Object> paramMap) {
		// 校验用户是否正确
		// long userId = MemberService.getCurrentUserId();
		// Plan plan =
		// planService.info(Long.valueOf(paramMap.get("planId").toString()));
		// if (userId != plan.getUserId() && plan.isPublicFlag() == false){
		// Validate.notNull(null, ErrorCode.ERROR_53003);
		// }

		// 获取行程天信息，SQL中已经做了表连接
		List<PlanTrip> planTripList = planTripService.list(paramMap);

		return planTripList;
	}

	/**
	 * 更新planDay耗时和公里数
	 * <ul>
	 * <li>必选:行程天id{id}</li>
	 * <li>必选:耗时{spendTime}</li>
	 * <li>必选:公里{kilometre}</li>
	 * </ul>
	 * 
	 * @return
	 */
	public void addSpendTime(Map<String, Object> paramMap) {
		PlanDay planDay = mapper.selById(paramMap.get("id").toString());
		planDay.setSpendTime(paramMap.get("spendTime").toString());
		planDay.setKilometre(paramMap.get("kilometre").toString());
		mapper.update(planDay);
	}

	/**
	 * 智能排序
	 * @Title: optimize
	 * @email shiqingju@hmlyinfo.com
	 *
	 * @param planTripList
	 * @param days
	 * @param hour
	 * @param type
	 * @return
	 *
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	public List<Map<String, Object>> optimize(List<PlanTrip> planTripList, OptimizeOption options) {
		List<Map<String, Object>> tripDetailList = prepareScenicData(planTripList); // 加载节点对应的景点、酒店、餐厅的信息
		// OptimizeProcess process =
		// OptimizeProcess.initOptimizeProcess(PlanHourType.findHour(hour),
		// PlanOptimizeType.find(type),
		// tripDetailList, days);
		// Map<String, Map<Integer, LinkedList<Point>>> resultPlans =
		// process.getResultPlans();
		List<List<Map<String, Object>>> optimizeResult = optimizeService.optimize(tripDetailList, options.getDays(), options.getHour(), options.getType());
		Map<String, List<Map<String, Object>>> dayTripMap = new HashMap<String, List<Map<String, Object>>>(); // 将原来的行程按天拆分
		String key = null;
		for (Map<String, Object> tripDetail : tripDetailList) {
			key = tripDetail.get("day").toString();
			List<Map<String, Object>> tripList = dayTripMap.get(key);
			if (tripList == null) {
				tripList = new ArrayList<Map<String, Object>>();
			}
			tripList.add(tripDetail);
			dayTripMap.put(key, tripList);
		}
		if (dayTripMap.size() == 1) {
			dayTripMap.put("1", dayTripMap.remove(key));
		}
		int size = dayTripMap.size() > optimizeResult.size() ? dayTripMap.size() : optimizeResult.size();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> hotelTrip = null;
		for (int i = 1; i <= size; i++) {
			Map<String, Object> compareResult = new HashMap<String, Object>();
			List<Map<String, Object>> originList = dayTripMap.get(i + "");
			
			/**
			 *  组装智能优化前的结果
			 */
			if (originList != null) {
				Map<String, Object> before = new HashMap<String, Object>();
				before.put("beforeData", originList);
				before.put("befimg", getStaticMapUrl(originList));
				compareResult.put("before", before);
			} else {
				compareResult.put("before", null);
			}
			
			/**
			 * 组装智能优化后的结果
			 */
			// 如果智能优化后有这一天
			if (i - 1 < optimizeResult.size()) {
				List<Map<String, Object>> optimizedList = optimizeResult.get(i - 1);
				// 如果当天没有选择酒店并且前端需要推荐酒店，则推荐一个酒店
				if (hotelTrip == null && options.isAddHotel()) {
					hotelTrip = checkHotel(optimizedList, tripDetailList);
				}
				// 如果当天有酒店，则将酒店信息增加到天中
				if (hotelTrip != null)
				{
					addHotel(hotelTrip, optimizedList);
				}
				
				Map<String, Object> after = new HashMap<String, Object>();
				after.put("afterData", optimizedList);
				after.put("aftimg", getStaticMapUrl(optimizedList));
				compareResult.put("after", after);
			} 
			// 没有这一天
			else {
				compareResult.put("after", null);
			}
			compareResult.put("day", i);
			compareResult.put("saveTime", -1);
			result.add(compareResult);
		}
		return result;
	}


	/**
	 * 为行程推荐酒店
	 * @Title: checkHotel
	 * @email pengwei@hmlyinfo.com
	 * @date 2015年11月8日 下午1:18:17
	 *
	 * @param tripList
	 * @param tripDetailList
	 * @return
	 *
	 * @return Map<String,Object>
	 * @throws
	 */
	public Map<String, Object> checkHotel(List<Map<String, Object>> tripList, List<Map<String, Object>> tripDetailList) {
		Map<String, Object> firstTrip = tripList.get(0);
		if ((Integer) firstTrip.get("tripType") == TripType.HOTEL.value()) {
			return firstTrip;
		}

		for (Map<String, Object> map : tripList) {
			map.put("orderNum", Integer.parseInt(map.get("orderNum").toString()) + 1);
		}

		int cityCode = 0;
		double lng = 0;
		double lat = 0;

		// 查找所在城市
		for (Map<String, Object> tripMap : tripList) {
			if (cityCode != 0) {
				break;
			}
			if ((Integer) firstTrip.get("tripType") == TripType.SCENIC.value()) {
				Map<String, Object> scenicMap = (Map<String, Object>) tripMap.get("scenicInfo");
				if (scenicMap != null && scenicMap.get("cityCode") != null) {
					cityCode = Integer.valueOf(scenicMap.get("cityCode").toString());
					lng = Double.valueOf(scenicMap.get("longitude").toString());
					lat = Double.valueOf(scenicMap.get("latitude").toString());
				}
			} else {
				Map<String, Object> foodMap = (Map<String, Object>) tripMap.get("food");
				if (foodMap != null && foodMap.get("cityCode") != null) {
					cityCode = Integer.valueOf(foodMap.get("cityCode").toString());
					lng = Double.valueOf(foodMap.get("resLongitude").toString());
					lat = Double.valueOf(foodMap.get("resLatitude").toString());
				}
			}
		}

		// 对于厦门的进行特殊处理，如果所有景点都位于鼓浪屿上就不处理，否则就选择不在鼓浪屿上的酒店
		Map<String, Object> outGulangyu = null;
		if (cityCode == 350200) {
			List<Long> gulangyuScenic = new ArrayList<Long>();
			gulangyuScenic.add((long) GULANGYU_SCENIC_ID);
			Map<String, Object> gulangyuScenicMap = new HashMap<String, Object>();
			gulangyuScenicMap.put("father", GULANGYU_SCENIC_ID);
			List<ScenicInfo> scenicInfos = scenicInfoService.listColumns(gulangyuScenicMap, Lists.newArrayList("id"));
			for (int i = 0; i < scenicInfos.size(); i++) {
				ScenicInfo guScenicInfo = scenicInfos.get(i);
				gulangyuScenic.add(guScenicInfo.getId());
			}

			for (Map<String, Object> tripMap : tripDetailList) {
				long tripScenicId = (Long) tripMap.get("scenicId");
				if (!gulangyuScenic.contains(tripScenicId)) {
					outGulangyu = tripMap;
					break;
				}
			}
		}

		// 一次性查询出所有酒店数据然后做判断处理速度太慢了，因为酒店数据内容太过庞大，花在传输上的时间太过漫长了，所以只能每批条件查询一次
		// 谁如果有更好的办法解决这个问题，就果断改掉
		// find all in distance
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (outGulangyu == null) {
			// 非厦门或者全部是鼓浪屿上的景点，处理方式不变
			paramMap.put("cityCode", cityCode);
			paramMap.put("latitude", lat);
			paramMap.put("longitude", lng);
			paramMap.put("isValid", "T");
			paramMap.put("orderColumn", "hotel_rank");
			paramMap.put("orderType", "desc");
			paramMap.put("distance", MAX_DISTANCE);
		} else {
			// 厦门景点，且不全是鼓浪屿上的，按距离排序
			// Long xmScenicId = (Long) outGulangyu.get("scenicId");
			// Map<String, Object> xmScenicInfo = (Map<String, Object>)
			// scenicInfoService.info(xmScenicId);
			// int xmCityCode =
			// Integer.parseInt(xmScenicInfo.get("cityCode").toString());
			// paramMap.put("cityCode", xmCityCode);
			// paramMap.put("latitude", xmScenicInfo.get("latitude"));
			// paramMap.put("longitude", xmScenicInfo.get("longitude"));
			// paramMap.put("isValid", "T");
			// paramMap.put("distance", MAX_DISTANCE);
			// 暂时先选中山路酒店（如家）
			paramMap.put("id", ZHONGSHAN_RUJIA_HOTEL);
		}

		List<CtripHotel> hotelList = hotelService.list(paramMap);
		if (!hotelList.isEmpty()) {
			for (CtripHotel hotel : hotelList) {
				if (hotel.getLowestHotelPrice() < MAX_PRICE && hotel.getLowestHotelPrice() > MIN_PRICE) {
					return hotelTrip(hotel, firstTrip);
				}
			}
			return hotelTrip(hotelList.get(0), firstTrip);
		}
		// find first ranked and price fitted in city
		paramMap.clear();
		paramMap.put("cityCode", cityCode);
		paramMap.put("isValid", "T");
		paramMap.put("lowestPrice", MIN_PRICE);
		paramMap.put("highestPrice", MAX_PRICE);
		PageDto pageDto = new PageDto(1, 1, 0);
		paramMap.put("pageDto", pageDto);
		hotelList = hotelService.list(paramMap);
		if (!hotelList.isEmpty()) {
			return hotelTrip(hotelList.get(0), firstTrip);
		}
		// find first ranked in city
		paramMap.clear();
		paramMap.put("cityCode", cityCode);
		paramMap.put("isValid", "T");
		paramMap.put("pageDto", pageDto);
		hotelList = hotelService.list(paramMap);
		if (!hotelList.isEmpty()) {
			return hotelTrip(hotelList.get(0), firstTrip);
		}
		return null;
	}

	public void addHotel(Map<String, Object> hotel, List<Map<String, Object>> tripList) {
		Map<String, Object> firstTrip = tripList.get(0);
		if ((Integer) firstTrip.get("tripType") == TripType.HOTEL.value()) {
			return;
		}
		if (hotel != null) {
			tripList.add(0, hotel);
		}

	}

	public Map<String, Object> hotelTrip(CtripHotel hotel, Map<String, Object> trip) {
		try {
			Map<String, Object> hotelTrip = new HashMap<String, Object>();
			hotelTrip.put("planId", trip.get("planId"));
			hotelTrip.put("planDaysId", trip.get("planDaysId"));
			hotelTrip.put("userId", trip.get("userId"));
			hotelTrip.put("tripType", TripType.HOTEL.value());
			hotelTrip.put("orderNum", 1);
			hotelTrip.put("scenicId", hotel.getId());

			Map map = PropertyUtils.describe(hotel);
			map.remove("class");
			hotelTrip.put("hotel", map);
			return hotelTrip;
		} catch (Exception e) {
			logger.error("酒店数据转换失败", e);
		}
		return null;
	}

	private String getStaticMapUrl(List<Map<String, Object>> originList) {
		StringBuilder url = new StringBuilder();
		StringBuilder pathBuilder = new StringBuilder();
		StringBuilder scenicBuilder = new StringBuilder();
		StringBuilder labelBuilder = new StringBuilder();
		url.setLength(0);
		// url.append(BAIDU_STATIC_MAP_URL).append("&center=").append(getCityCode(originList));
		url.append(BAIDU_STATIC_MAP_URL);
		pathBuilder.setLength(0);
		scenicBuilder.setLength(0);
		int index = 1;
		for (Map<String, Object> origin : originList) {
			pathBuilder.append(getPositionStr(origin)).append(";");
			scenicBuilder.append(getPositionStr(origin)).append("|");
			labelBuilder.append(index++).append(getName(origin)).append(",0,12,0x000000,0xffffff,1|");
		}
		pathBuilder.setLength(pathBuilder.length() - 1);
		scenicBuilder.setLength(scenicBuilder.length() - 1);
		labelBuilder.setLength(labelBuilder.length() - 1);
		if (originList.size() > 1) {
			url.append("&paths=").append(pathBuilder.toString());
			url.append("&pathStyles=0xff0906,3,0.7");
		}
		url.append("&labels=").append(scenicBuilder.toString());
		url.append("&labelStyles=").append(labelBuilder.toString());
		return url.toString();
	}

	List<Map<String, Object>> prepareScenicData(List<PlanTrip> planTrips) {
		List<Long> scenicIdList = new ArrayList<Long>();
		List<Long> restaurantIdList = new ArrayList<Long>();
		List<Long> hotelIdList = new ArrayList<Long>();

		for (PlanTrip planTrip : planTrips) {
			if (planTrip.getTripType() == TripType.HOTEL.value()) {
				hotelIdList.add(planTrip.getScenicId());
			} else if (planTrip.getTripType() == TripType.RESTAURANT.value()) {
				restaurantIdList.add(planTrip.getScenicId());
			} else {
				scenicIdList.add(planTrip.getScenicId());
			}
		}
		List<Map<String, Object>> resultlist = new ArrayList<Map<String, Object>>();

		if (!scenicIdList.isEmpty()) {
			List<ScenicInfo> scenicInfoList = scenicInfoService.listBrief(Collections.<String, Object> singletonMap("ids", scenicIdList));
			resultlist = ListUtil.listJoin(planTrips, scenicInfoList, "scenicId=id", "scenicInfo", null);
		}
		if (!restaurantIdList.isEmpty()) {
			List<Restaurant> restaurantList = restaurantService.list(Collections.<String, Object> singletonMap("ids", restaurantIdList));
			if (!resultlist.isEmpty()) {
				resultlist = ListUtil.listJoin(resultlist, restaurantList, "scenicId=id", "food", null);
			} else {
				resultlist = ListUtil.listJoin(planTrips, restaurantList, "scenicId=id", "food", null);
			}
		}
		if (!hotelIdList.isEmpty()) {
			List<CtripHotel> hotelList = hotelService.list(Collections.<String, Object> singletonMap("ids", hotelIdList));
			if (!resultlist.isEmpty()) {
				resultlist = ListUtil.listJoin(resultlist, hotelList, "scenicId=id", "hotel", null);
			} else {
				resultlist = ListUtil.listJoin(planTrips, hotelList, "scenicId=id", "hotel", null);
			}
		}

		return resultlist;
	}

	String getPositionStr(Map<String, Object> planTrip) {
		if ((Integer) planTrip.get("tripType") == TripType.RESTAURANT.value()) {
			Map<String, Object> food = (Map<String, Object>) planTrip.get("food");
			return food.get("resLongitude") + "," + food.get("resLatitude");
		} else if ((Integer) planTrip.get("tripType") == TripType.HOTEL.value()) {
			Map<String, Object> hotel = (Map<String, Object>) planTrip.get("hotel");
			return hotel.get("longitude") + "," + hotel.get("latitude");
		} else {
			Map<String, Object> scenicInfo = (Map<String, Object>) planTrip.get("scenicInfo");
			return scenicInfo.get("longitude") + "," + scenicInfo.get("latitude");
		}
	}

	String getName(Map<String, Object> planTrip) {
		if ((Integer) planTrip.get("tripType") == TripType.RESTAURANT.value()) {
			Map<String, Object> food = (Map<String, Object>) planTrip.get("food");
			return food.get("resName").toString();
		} else if ((Integer) planTrip.get("tripType") == TripType.HOTEL.value()) {
			Map<String, Object> hotel = (Map<String, Object>) planTrip.get("hotel");
			return hotel.get("hotelName").toString();
		} else {
			Map<String, Object> scenicInfo = (Map<String, Object>) planTrip.get("scenicInfo");
			return scenicInfo.get("name").toString();
		}

	}

	String getCityCode(List<Map<String, Object>> planTripList) {
		String cityCode = null;
		for (Map<String, Object> planTrip : planTripList) {
			if ((Integer) planTrip.get("tripType") == TripType.HOTEL.value()) {
				Map<String, Object> hotel = (Map<String, Object>) planTrip.get("hotel");
				cityCode = hotel.get("cityCode").toString();
			} else if ((Integer) planTrip.get("tripType") == TripType.SCENIC.value()) {
				Map<String, Object> scenicInfo = (Map<String, Object>) planTrip.get("scenicInfo");
				cityCode = scenicInfo.get("cityCode").toString();
			}
		}
		if (cityCode == null) {
			return "";
		}
		List<City> cityList = cityService.list(Collections.<String, Object> singletonMap("cityCode", cityCode));
		if (cityList == null || cityList.isEmpty()) {
			return "";
		}
		return cityList.get(0).getName();
	}

	public Map<String, Object> prepareResult(List<Map<String, Object>> list) {
		return null;
	}

	public void deleteByPlan(Long planId) {
		mapper.delByPlan(planId);
	}

	@Transactional
	public void insertMore(List<PlanDay> list) {
		// 设置主键
		long maxId = mapper.getMaxId();
		for (PlanDay day : list)
		{
			day.setId(++maxId);
		}
		mapper.insertmore(list);
	}

	// 新的智能优化返回结构
	public Map<String, Object> newOptimize(HttpServletRequest request) {

		try {
			List<Map<String, Object>> planTrips = new ObjectMapper().readValue(request.getParameter("planTrip"), ArrayList.class);
			int days = Integer.parseInt(request.getParameter("days"));
			int hour = 0;
			int type = 0;
			if (request.getParameter("hour") != null) {
				hour = Integer.parseInt(request.getParameter("hour"));
			}
			if (request.getParameter("type") != null) {
				type = Integer.parseInt(request.getParameter("type"));
			}
			return beforeOptimize(planTrips, days, hour, type);
		} catch (IOException e) {
			logger.error("传入数据解析失败", e);
			throw new BizValidateException(ErrorCode.ERROR_51001, "传入数据解析失败");
		}
	}

	// 智能优化前数据处理
	public Map<String, Object> beforeOptimize(List<Map<String, Object>> planTrips, int days, int hour, int type) {
		List<PlanTrip> planTripList = new ArrayList<PlanTrip>();
		long tempId = 0;
		//
		Map<Long, Integer> scenicDayMap = new HashMap<Long, Integer>();
		// 旧的行程中所有景点
		Set<Long> allOldScenicIds = new HashSet<Long>();

		for (Map<String, Object> map : planTrips) {
			PlanTrip planTrip = new PlanTrip();
			planTrip.setId(tempId++);
			planTrip.setScenicId(Long.parseLong(map.get("id").toString()));
			planTrip.setTripType(Integer.parseInt(map.get("tripType").toString()));
			planTrip.setDay(Integer.parseInt(map.get("day").toString()));
			planTrip.setTravelType(TravelType.DRIVING.value());
			planTripList.add(planTrip);
			//
			allOldScenicIds.add(planTrip.getScenicId());
			// 如果行程天大于当前天，则先将之前的数据插入列表，然后构建新的一天的数据

			scenicDayMap.put(planTrip.getScenicId(), planTrip.getDay());
		}
		return afterOptimize(days, hour, type, planTripList, scenicDayMap, allOldScenicIds);
	}

	// 智能优化及后续数据处理
	public Map<String, Object> afterOptimize(int days, int hour, int type, List<PlanTrip> planTripList, Map<Long, Integer> scenicDayMap,
			Set<Long> allOldScenicIds) {

		OptimizeOption options = new OptimizeOption();
		options.setDays(days);
		options.setHour(hour);
		options.setType(type);
		
		List<Map<String, Object>> result = optimize(planTripList, options);
		Set<Long> allNewScenicIds = new HashSet<Long>();
		int newScenicCount = 0;
		int newHotelCount = 0;
		int changeCount = 0;
		int dayIndex = 1;
		double cost = 0;
		int scenicNum = 0;
		int hotelNum = 0;
		// 统计所有的酒店id用来获取美食信息
		List<String> restaurantIds = new ArrayList<String>();

		// 旧的trip列表
		Map<Long, Map<String, Object>> oldScenicMaps = new HashMap<Long, Map<String, Object>>();

		for (Map<String, Object> dayMap : result) {
			// 每天游玩总时间
			int dayPlayTime = 0;
			// 每天的城市列表
			int oldCityCode = 0;
			List<String> cityNameList = new ArrayList<String>();

			// 旧的行程
			if (dayMap.get("before") != null) {
				Map<String, Object> beforeMap = (Map<String, Object>) dayMap.get("before");
				List<Map<String, Object>> oldScenics = (List<Map<String, Object>>) beforeMap.get("beforeData");

				for (Map<String, Object> oldScenicMap : oldScenics) {
					Long oldScenicId = (Long) oldScenicMap.get("scenicId");
					oldScenicMaps.put(oldScenicId, oldScenicMap);

					// 判断行程是否宽松
					if (Integer.parseInt(oldScenicMap.get("tripType").toString()) == TripType.SCENIC.value()) {
						Map<String, Object> sMap = (Map<String, Object>) oldScenicMap.get("scenicInfo");
						dayPlayTime += (Integer) sMap.get("adviceHours");
					} else if (Integer.parseInt(oldScenicMap.get("tripType").toString()) == TripType.RESTAURANT.value()) {
						//
						dayPlayTime += 60;
					}
				}
			}

			// 如果减少了天数，新的行程可能不存在这一天
			if (dayMap.get("after") == null) {
				if (dayPlayTime > HOUR[hour][1]) {
					dayMap.put("dayType", DAY_COMPACT);
				} else if (dayPlayTime < HOUR[hour][0]) {
					dayMap.put("dayType", DAY_LOOSE);
				} else {
					dayMap.put("dayType", DAY_NORMAL);
				}
				dayIndex++;
				continue;
			}
			// 新的行程
			Map<String, Object> afterMap = (Map<String, Object>) dayMap.get("after");
			List<Map<String, Object>> scenicList = (List<Map<String, Object>>) afterMap.get("afterData");
			List<Map<String, Object>> independList = new ArrayList<Map<String, Object>>();

			for (Map<String, Object> scenicMap : scenicList) {

				// Map<String, Object> independMap =
				// Collections.unmodifiableMap(scenicMap);
				Map<String, Object> independMap = Maps.newHashMap(scenicMap);

				// 获取城市信息
				int newCityCode = 0;
				if (Integer.parseInt(independMap.get("tripType").toString()) == TripType.SCENIC.value()) {
					Map<String, Object> sMap = (Map<String, Object>) independMap.get("scenicInfo");
					newCityCode = Integer.parseInt((String) sMap.get("cityCode"));
					double sprice = 0;
					if (sMap.get("lowestPrice") != null) {
						sprice = Double.parseDouble(sMap.get("lowestPrice").toString());
					} else if (sMap.get("price") != null) {
						sprice = Double.parseDouble(sMap.get("price").toString());
					}
					cost += sprice;
					scenicNum++;
				} else if (Integer.parseInt(independMap.get("tripType").toString()) == TripType.RESTAURANT.value()) {
					// 餐厅，可添加新的处理
					Map<String, Object> rMap = (Map<String, Object>) independMap.get("food");
					String resId = rMap.get("id").toString();
					restaurantIds.add(resId);
					scenicNum++;
				} else {
					Map<String, Object> hMap = (Map<String, Object>) independMap.get("hotel");
					newCityCode = (Integer) hMap.get("cityCode");
					double hprice = Double.parseDouble(hMap.get("lowestHotelPrice").toString());
					cost += hprice;
					hotelNum++;
				}
				if (newCityCode != 0 && newCityCode != oldCityCode) {
					oldCityCode = newCityCode;
					City newCity = cityService.list(Collections.<String, Object> singletonMap("cityCode", newCityCode)).get(0);
					cityNameList.add(newCity.getName());
				}

				Long newScenicId = (Long) independMap.get("scenicId");
				allNewScenicIds.add(newScenicId);

				// 判断是否是新增行程点
				Integer oldDay = scenicDayMap.get(newScenicId);
				if (oldDay == null) {
					independMap.put("scenicType", SCENIC_TYPE_NEW);
					if ((Integer) independMap.get("tripType") == TripType.HOTEL.value()) {
						// 新增酒店数量
						newHotelCount++;
					} else if ((Integer) independMap.get("tripType") == TripType.SCENIC.value()) {
						newScenicCount++;
					}
				} else if (oldDay == dayIndex) {
					independMap.put("scenicType", SCENIC_TYPE_OLD);
				} else if ((Integer) independMap.get("tripType") == TripType.SCENIC.value()) {
					independMap.put("scenicFrom", oldDay);
					independMap.put("scenicTo", dayIndex);
					independMap.put("scenicType", SCENIC_TYPE_CHANGE);
					changeCount++;
				}

				independList.add(independMap);
			}
			//
			afterMap.put("afterData", independList);

			dayMap.put("cityList", cityNameList);
			//
			if (dayPlayTime > HOUR[hour][1]) {
				dayMap.put("dayType", DAY_COMPACT);
			} else if (dayPlayTime < HOUR[hour][0]) {
				dayMap.put("dayType", DAY_LOOSE);
			} else {
				dayMap.put("dayType", DAY_NORMAL);
			}
			dayIndex++;
		}
		List<Map<String, Object>> delList = new ArrayList<Map<String, Object>>();
		for (Long oldScenicId : allOldScenicIds) {
			if (!allNewScenicIds.contains(oldScenicId)) {
				delList.add(oldScenicMaps.get(oldScenicId));
			}
		}

		//
		if (restaurantIds.size() > 0) {
			Map<String, Object> resMap = new HashMap<String, Object>();
			resMap.put("resIds", restaurantIds);
			List<DelicacyRes> delicacyRes = delicacyResService.list(resMap);
			List<String> delicacyIds = ListUtil.getIdList(delicacyRes, "delicacyId");
			if (delicacyIds.size() > 0) {
				resMap.remove("resIds");
				resMap.put("ids", delicacyIds);
				List<Delicacy> delicacies = delicacyService.listPrice(resMap);
				for (Delicacy delicacy : delicacies) {
					cost += delicacy.getPrice();
				}
			}
		}

		Map<String, Object> res = new HashMap<String, Object>();
		res.put("res", result);
		res.put("newScenicCount", newScenicCount);
		res.put("newHotelCount", newHotelCount);
		res.put("changeCount", changeCount);
		res.put("delList", delList);
		res.put("cost", cost);
		res.put("scenicNum", scenicNum);
		res.put("hotelNum", hotelNum);
		return res;
	}
	
	public void updateHotel(PlanDay planday)
	{
		mapper.updateHotel(planday);
	}
	
	/**
	 * 为planDays中查询hotel信息
	 * @Title: addDayHotels
	 * @email shiqingju@hmlyinfo.com
	 * @date 2015年11月10日 下午7:57:38
	 * @version 2.7.0
	 *
	 * @param dayList
	 *
	 * @return void
	 * @throws
	 */
	public List<PlanDay> addDayHotels(List<PlanDay> dayList)
	{
		List<CtripHotel> hotelList = hotelService.list(ParamUtil.createIdsMap(dayList, "hotelId"));
		
		Map<Long, CtripHotel> hotelMap = ListUtil.toIdMap("id", hotelList);
		
		for (PlanDay day : dayList)
		{
			day.setHotel(hotelMap.get(day.getHotelId()));
		}
		
		return dayList;
	}
	
	/**
	 * 查询酒店的价格花费
	 * @Title: setHotelCost
	 * @email shiqingju@hmlyinfo.com
	 * @date 2015年11月12日 下午3:59:19
	 * @version 
	 *
	 * @param dayList
	 * @return
	 *
	 * @return List<PlanDay>
	 * @throws
	 */
	public List<PlanDay> setHotelCost(List<PlanDay> dayList)
	{
		dayList = addDayHotels(dayList);
		for (PlanDay day : dayList)
		{
			if (day.getHotel() != null)
			{
				day.setHotelCost((int)day.getHotel().getLowestHotelPrice());
			}
		}
		
		return dayList;
	}
}
