package com.hmlyinfo.app.soutu.plan.service;

import com.google.common.base.Joiner;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.hmlyinfo.app.soutu.account.service.MemberService;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.plan.domain.Plan;
import com.hmlyinfo.app.soutu.plan.domain.PlanCity;
import com.hmlyinfo.app.soutu.plan.domain.PlanDay;
import com.hmlyinfo.app.soutu.plan.domain.PlanTrip;
import com.hmlyinfo.app.soutu.plan.domain.type.TripType;
import com.hmlyinfo.app.soutu.scenic.service.ScenicInfoService;
import com.hmlyinfo.base.util.Validate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 实现行程的复杂操作
 */
@Service
public class PlanOperationService {

	@Autowired
	private PlanService planService;
	@Autowired
	private PlanDaysService planDaysService;
	@Autowired
	private PlanTripService planTripService;
	@Autowired
	private PlanCityService planCityService;
	@Autowired
	private ScenicInfoService scenicInfoService;


	/**
	 * 更新当天所有行程站
	 * <ul>
	 * <li>必选:标识{planId}</li>
	 * </ul>
	 *
	 */
	public Plan updatePlanTrip(Map<String, Object> planMap) {

		Map<String, Object> resultMap = updatePlan(planMap);
		Plan oldplan = (Plan) resultMap.get("plan");
		long planDaysId = (Long) resultMap.get("planDaysId");

		Plan plan = planService.info(oldplan.getId());
		// 查询行程天列表
		PlanDay planDay = planDaysService.info(planDaysId);
		plan.setPlanDay(planDay);

		return plan;
	}

	@Transactional
	@SuppressWarnings("unchecked")
	private Map<String, Object> updatePlan(Map<String, Object> planMap) {
		long planId = Long.valueOf(planMap.get("planId").toString());
		int days;

		// 校验用户是否正确
		long userId = MemberService.getCurrentUserId();
		Validate.isTrue(userId == planService.info(planId).getUserId(), ErrorCode.ERROR_53003);

		days = Integer.valueOf(planMap.get("days").toString());
		List<Map<String, Object>> scenicDataList = (List) planMap.get("scenicData");

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("planId", planId);
		paramMap.put("days", days);
		List<PlanDay> planDaysList = planDaysService.list(paramMap);
		Plan plan = planService.info(planId);
		Calendar calendar = Calendar.getInstance();

		if (plan.getStartTime() != null) {
			calendar.setTime(plan.getStartTime());
		}

		calendar.add(Calendar.DAY_OF_MONTH, days - 1);

		// 这一天不存在，新建一天
		if (planDaysList.size() == 0) {
			PlanDay newPlanDays = new PlanDay();
			newPlanDays.setDays(days);
			newPlanDays.setPlanId(planId);
			newPlanDays.setDate(calendar.getTime());
			planDaysService.insert(newPlanDays);
			planDaysList.add(newPlanDays);
		} else {
			PlanDay planDay = planDaysList.get(0);
			planDay.setDate(calendar.getTime());
			planDaysService.update(planDay);
		}

		long planDaysId = planDaysList.get(0).getId();

		// 删除原有景点信息
		planTripService.deleteByDay(planDaysId);

		// 插入新景点信息
		for (int i = 0; i < scenicDataList.size(); ++i) {
			Map<String, Object> sMap = scenicDataList.get(i);

			// 对输入值进行校验
			Validate.notNull(sMap.get("scenicId"), ErrorCode.ERROR_50001);
			Validate.notNull(sMap.get("travelType"), ErrorCode.ERROR_50001);
			Validate.notNull(sMap.get("tripType"), ErrorCode.ERROR_50001);

			PlanTrip planTrip = new PlanTrip();

			planTrip.setPlanId(planId);
			planTrip.setUserId(userId);
			planTrip.setScenicId(Long.parseLong(sMap.get("scenicId").toString()));
			planTrip.setOrderNum(i + 1);
			planTrip.setPlanDaysId(planDaysId);
			planTrip.setTravelType(Integer.parseInt(sMap.get("travelType").toString()));
			planTrip.setTripType(Integer.parseInt(sMap.get("tripType").toString()));
			planTrip.setTripDesc(ObjectUtils.getDisplayString(sMap.get("remark")));
			planTrip.setTravelDetail(ObjectUtils.getDisplayString(sMap.get("travelDetail")));
			planTrip.setTravelTime(ObjectUtils.getDisplayString(sMap.get("travelTime")));
			if (sMap.get("travelHours") != null) {
				planTrip.setTravelHours(Integer.parseInt(sMap.get("travelHours").toString()));
			}
			planTrip.setTravelPrice(Integer.parseInt(sMap.get("travelPrice").toString()));
			planTrip.setTravelMileage(ObjectUtils.getDisplayString(sMap.get("travelMileage")));

			// 行程节点时间可能为空
			if (sMap.get("startTime") != null) {
				planTrip.setStartTime(Time.valueOf(ObjectUtils.getDisplayString(sMap.get("startTime"))));
			}

			planTripService.insert(planTrip);
		}

		// 更新行程的统计信息
		updatePlanSTATS(planId + "");

		Map<String, Object> resultMap = Maps.newHashMap();
		resultMap.put("plan", plan);
		resultMap.put("planDaysId", planDaysId);

		return resultMap;
	}

	/**
	 * 复制一份行程到编辑模块
	 * <ul>
	 * <li>必选:行程id{planId}</li>
	 * </ul>
	 *
	 * @return planId
	 * @throws java.text.ParseException
	 */
	@Transactional
	public long quotePlan(Map<String, Object> paramMap) throws ParseException {
		// 取得当前用户ID
		long userId = MemberService.getCurrentUserId();

		long newId = saveAsNewPlan(paramMap, userId);
		// 引用行程就代表已经保存，设置状态为1
		Plan plan = planService.info(newId);
		plan.setStatus(1);
		planService.update(plan);

		String oldId = paramMap.get("planId").toString();

		Map<String, Object> pMap = new HashMap<String, Object>();
		pMap.put("planId", oldId);
		// 取得需要的行程天，行程节点，行程城市
		List<PlanDay> planDayList = planDaysService.list(pMap);
		List<PlanTrip> planTripList = planTripService.list(pMap);
		List<PlanCity> planCityList = planCityService.list(pMap);

		// 声明一个map,key放days，List放行程节点和行程城市
		Map<Object, List<PlanTrip>> planTripMap = new HashMap<Object, List<PlanTrip>>();
		Map<Object, List<PlanCity>> planCityMap = new HashMap<Object, List<PlanCity>>();

		if (planDayList.isEmpty()) {
			return newId;
		}

		// 循环插入planDays到新的planDays
		for (PlanTrip planTrip : planTripList) {
			// 判断行程节点是第几天，并放入相应的天数
			List<PlanTrip> list = planTripMap.get(planTrip.getPlanDaysId());
			if (list == null) {
				list = new ArrayList<PlanTrip>();
			}
			list.add(planTrip);
			planTripMap.put(planTrip.getPlanDaysId(), list);
		}
		for (PlanCity planCity : planCityList) {
			List<PlanCity> list = planCityMap.get(planCity.getPlanDaysId());
			if (list == null) {
				list = new ArrayList<PlanCity>();
			}
			list.add(planCity);
			planCityMap.put(planCity.getPlanDaysId(), list);
		}

		for (PlanDay planDay : planDayList) {
			planTripMap.put(planDay.getDays(), planTripMap.get(planDay.getId()));
			planCityMap.put(planDay.getDays(), planCityMap.get(planDay.getId()));
			planDay.setId(null);
			planDay.setCreateTime(null);
			planDay.setPlanId(newId);
		}

		planDaysService.insertMore(planDayList);

		// 通过planId取得planDayId
		paramMap.put("planId", newId);
		planDayList = planDaysService.list(paramMap);

		// 将新的planId放入行程节点和行程城市中
		for (PlanDay planDay : planDayList) {
			// 循环将每个天数的新行程天编号放入行程节点中
			if (!planTripList.isEmpty() && planTripMap.get(planDay.getDays()) != null) {
				for (PlanTrip planTrip : planTripMap.get(planDay.getDays())) {
					planTrip.setPlanDaysId(planDay.getId());
				}
			}
			if (!planCityList.isEmpty() && planCityMap.get(planDay.getDays()) != null) {
				for (PlanCity planCity : planCityMap.get(planDay.getDays())) {
					planCity.setPlanDaysId(planDay.getId());
				}
			}
		}

		// 循环插入planTrip到新的planTrip
		for (PlanTrip planTrip : planTripList) {
			planTrip.setId(null);
			planTrip.setUserId(userId);
			planTrip.setCreateTime(null);
			planTrip.setPlanId(newId);
		}
		if (!planTripList.isEmpty()) {
			planTripService.insertMore(planTripList);
		}

		// 循环插入planCity到新的planCity
		for (PlanCity planCity : planCityList) {
			planCity.setId(null);
			planCity.setCreateTime(null);
			planCity.setPlanId(newId);
		}
		if (!planCityList.isEmpty()) {
			planCityService.insertList(planCityList);
		}
		// 增加行程引用数
		paramMap.put("quoteNum", true);
		paramMap.put("id", oldId);
		planService.addAllNum(paramMap);

		// 更新交通信息
		planTripService.insertTrafficCost(newId);

		return newId;
	}



	private long saveAsNewPlan(Map<String, Object> paramMap, long userId) throws ParseException {
		Plan plan = planService.info(Long.valueOf(paramMap.get("planId").toString()));
		// 新加ID，用户ID，删除收藏，引用，分享数
		plan.setId(null);
		plan.setUserId(userId);
		plan.setStartTime(new Date());
		plan.setRecommendFlag(false);
		plan.setCommentNum(0);
		plan.setViewNum(0);
		plan.setCollectNum(0);
		plan.setQuoteNum(0);
		plan.setShareNum(0);
		plan.setCreateTime(null);

		if (paramMap.get("planName") != null) {
			plan.setPlanName((String) paramMap.get("planName"));
		}
		if (paramMap.get("startTime") != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dstr = paramMap.get("startTime") + "";
			Date date = sdf.parse(dstr);
			plan.setStartTime(date);
		}
		// 插入数据库
		planService.insert(plan);

		// 获取插入新行程ID
		return plan.getId();
	}


	/**
	 * 更新行程统计信息：天数、花费
	 */
	public void updatePlanSTATS(String planId) {
		Map<String, Object> paramMap = Collections.<String, Object> singletonMap("planId", planId);
		List<PlanTrip> tripList = planTripService.list(paramMap);
		Multimap<Long, PlanTrip> planDayMap = ArrayListMultimap.create();

		/**
		 * 更新当前行程中的所有城市列表
		 */
		Map<Integer, List<String>> colMap = Maps.newHashMap();
		colMap.put(TripType.SCENIC.value(), Lists.newArrayList("id", "city_code"));
		colMap.put(TripType.HOTEL.value(), Lists.newArrayList("id", "city_code"));
		colMap.put(TripType.RESTAURANT.value(), Lists.newArrayList("id", "city_code"));

		List<Map<String, Object>> tripCityList = planTripService.listDetail(tripList, colMap);
		// 拼接城市编号
		Set<String> cityIdSet = Sets.newHashSet();
		for (Map<String, Object> tc : tripCityList) {
			Map<String, Object> codeMap = (Map<String, Object>) tc.get("detail");
			if (codeMap == null)
				continue;
			String cityCode = ObjectUtils.getDisplayString(codeMap.get("cityCode"));
			if (StringUtils.isNotBlank(cityCode)) {
				cityIdSet.add(cityCode);
			}
		}
		String cityIds = Joiner.on(",").join(cityIdSet);

		/**
		 * 计算每天的 酒店花费、门票花费、交通花费； 游玩时间、交通时间
		 */
		// 门票花费，不包含套票花费
		int ticketCost = 0;
		// 门票花费，包含套票花费
		int ticketSeasonCost = 0;
		// 行程酒店花费
		int planHotelCost = 0;
		// 行程交通费用
		int planTravelCost = 0;
		// 餐厅花费
		int resCost = 0;

		// 将行程按照行程天分组
		for (PlanTrip trip : tripList) {
			planDayMap.put(trip.getPlanDaysId(), trip);
		}

		// 计算景点和酒店，景点的酒店需要每个行程天分别计算
		for (Long key : planDayMap.keySet()) {
			List<PlanTrip> dayTrips = (List<PlanTrip>) planDayMap.get(key);

			// 每天门票花费和游玩时间
			Map<String, Object> resMap = scenicInfoService.countAmountAndPlayTimeV2(dayTrips);
			// 门票花费，不包含套票花费
			int dayTicketCost = (Integer) resMap.get("amount");
			int dayPlayTime = (Integer) resMap.get("playTime");
			int hotelPrice = (Integer) resMap.get("hotelPrice");
			// 门票花费，包含套票花费
			int totalSeasonTicketPrice = (Integer) resMap.get("totalSeasonTicketPrice");
			// 每天交通花费，交通时间
			int dayTrafficCost = (Integer) resMap.get("travelCost");
			int dayTrafficHours = (Integer) resMap.get("travelMin");
			//
			resCost += (Integer) resMap.get("resCost");

			// 行程花费统计
			ticketCost += dayTicketCost;
			ticketSeasonCost += totalSeasonTicketPrice;
			planHotelCost += hotelPrice;
			planTravelCost += dayTrafficCost;

			// 更新每天的统计数据
			PlanDay pd = new PlanDay();
			pd.setId(key);
			pd.setTicketCost(dayTicketCost);
			pd.setIncludeSeasonticketCost(totalSeasonTicketPrice);
			pd.setTrafficCost(dayTrafficCost);
			pd.setTrafficTime(dayTrafficHours);
			pd.setPlayTime(dayPlayTime);
			pd.setHotelCost(hotelPrice);
			pd.setTotalPrice(dayTicketCost + dayTrafficCost + hotelPrice);
			pd.setTotalSeasonTicketPrice(totalSeasonTicketPrice + dayTrafficCost + hotelPrice);

			planDaysService.update(pd);
		}

		Map<String, Object> dayMap = new HashMap<String, Object>();
		dayMap.put("planId", planId);
		int daycount = planDaysService.count(dayMap);

		Plan plan = new Plan();
		plan.setId(Long.valueOf(planId));
		plan.setPlanCost(ticketCost + planTravelCost + planHotelCost + resCost);
		plan.setPlanSeasonticketCost(ticketSeasonCost + planTravelCost + planHotelCost + resCost);
		plan.setPlanTicketCost(ticketCost);
		plan.setIncludeSeasonticketCost(ticketSeasonCost);
		plan.setPlanHotelCost(planHotelCost);
		plan.setPlanTravelCost(planTravelCost);
		plan.setPlanDays(daycount);
		plan.setCityIds(cityIds);

		planService.update(plan);
	}

}
