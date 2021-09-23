package com.hmlyinfo.app.soutu.activity.service;

import com.hmlyinfo.app.soutu.account.domain.User;
import com.hmlyinfo.app.soutu.account.service.MemberService;
import com.hmlyinfo.app.soutu.account.service.UserService;
import com.hmlyinfo.app.soutu.activity.domain.Activity;
import com.hmlyinfo.app.soutu.activity.domain.JoinedPlan;
import com.hmlyinfo.app.soutu.activity.domain.PlanPoll;
import com.hmlyinfo.app.soutu.activity.mapper.JoinedPlanMapper;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.delicacy.domain.Restaurant;
import com.hmlyinfo.app.soutu.delicacy.service.RestaurantService;
import com.hmlyinfo.app.soutu.hotel.domain.CtripHotel;
import com.hmlyinfo.app.soutu.hotel.service.CtripHotelService;
import com.hmlyinfo.app.soutu.plan.domain.Plan;
import com.hmlyinfo.app.soutu.plan.domain.PlanTrip;
import com.hmlyinfo.app.soutu.plan.domain.type.TripType;
import com.hmlyinfo.app.soutu.plan.service.PlanService;
import com.hmlyinfo.app.soutu.plan.service.PlanTripService;
import com.hmlyinfo.app.soutu.scenic.service.ScenicInfoService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import com.hmlyinfo.base.util.ListUtil;
import com.hmlyinfo.base.util.Validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JoinedPlanService extends BaseService<JoinedPlan, Long>{

	@Autowired
	JoinedPlanMapper<JoinedPlan> mapper;
	@Autowired
	ActivityService activityService;
	@Autowired
	PlanService planService;
	@Autowired
	PlanTripService planTripService;
	@Autowired
	ScenicInfoService scenicInfoService;
	@Autowired
	RestaurantService restaurantService;
	@Autowired
	CtripHotelService ctripHotelService;
	@Autowired
	UserService userService;
	@Autowired
	PlanPollService planPollService;

	@Override
	public BaseMapper<JoinedPlan> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

	public JoinedPlan joinActivity(Long planId) {
		//判断plan是否为空
		List<PlanTrip> planTripList = planTripService.list(Collections.<String, Object>singletonMap("planId", planId));
		Validate.isTrue(!planTripList.isEmpty(),ErrorCode.ERROR_53004, "行程为空");
		/*根据状态是1(进行中)的查询activity表对应的活动集合*/
		Map<String, Object> activeMap = new HashMap<String, Object>();
		activeMap.put("status", Activity.STATUS_RUNNING);
		List<Activity> activityList = activityService.list(activeMap);
		Validate.isTrue(!activityList.isEmpty(), ErrorCode.ERROR_53001, "当前没有活动");
		long activityId = activityList.get(0).getId();
		Plan plan = planService.info(planId);
		/*根据planId和activityId查询满足的集合，存在就抛出异常*/
		Validate.dataAuthorityCheck(plan);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		long userId = MemberService.getCurrentUserId();
		paramMap.put("userId", userId);
		paramMap.put("activityId", activityId);
		List<JoinedPlan> list = list(paramMap);
		Validate.isTrue(list.isEmpty(), ErrorCode.ERROR_51009, "planId和activityId已经存在!");
		JoinedPlan joinedPlan = new JoinedPlan();
		joinedPlan.setPlanId(Long.valueOf(planId));
		joinedPlan.setActivityId(activityId);
		joinedPlan.setUserId(userId);
		joinedPlan.setPollCount(0);
		insert(joinedPlan);
		return joinedPlan;
	}

	public ActionResult countCurrent(Map<String, Object> paramMap) {
		List<Activity> activityList = activityService.list(Collections.<String, Object>singletonMap("status", Activity.STATUS_RUNNING));
		Validate.isTrue(!activityList.isEmpty(), ErrorCode.ERROR_51001, "没有活动进行中");
		Activity activity = activityList.get(0);
		paramMap.put("activityId", activity.getId());
		return countAsResult(paramMap);
	}

	public List<Map<String, Object>> listWithDetail(Map<String, Object> paramMap) {
		prepareSortInformation(paramMap);
		List<Activity> activityList = activityService.list(Collections.<String, Object>singletonMap("status", Activity.STATUS_RUNNING));
		Validate.isTrue(!activityList.isEmpty(), ErrorCode.ERROR_51001, "没有活动进行中");
		Activity activity = activityList.get(0);
		paramMap.put("activityId", activity.getId());
		List<JoinedPlan> joinedPlanList = list(paramMap);
		//获取对应的plan信息
		List<Long> planIdList = ListUtil.getIdList(joinedPlanList, "planId");
		Map<String, Object> planParams = new HashMap<String, Object>();
		planParams.put("ids", planIdList);
		List<String> planColumns = new ArrayList<String>();
		planColumns.add("id");
		planColumns.add("plan_name");
		planParams.put("needColumns", planColumns);
		List<Plan> planList = planService.listColumns(planParams);
		for (Plan plan : planList) {
			List<PlanTrip> planTripList = planTripService.list(Collections.<String, Object>singletonMap("planId", plan.getId()));
			if (planTripList.isEmpty()) {
				continue;
			}
			Long hotelId = null;
			Long restaurantId = null;
			Long scenicId = null;
			for (PlanTrip planTrip : planTripList) {
				if (planTrip.getTripType() == TripType.SCENIC.value()) {
					scenicId = planTrip.getScenicId();
					break;
				} else if (planTrip.getTripType() == TripType.HOTEL.value()) {
					if (hotelId == null) {
						hotelId = planTrip.getScenicId();
					}
				} else {
					if (restaurantId == null) {
						restaurantId = planTrip.getScenicId();
					}
				}
			}
			if (scenicId != null) {
				Map<String, Object> scenicInfo = (Map<String, Object>) scenicInfoService
						.info(scenicId);
				if (scenicInfo != null) {
					plan.setCoverPath((String) scenicInfo.get("coverSmall"));
				}
			} else if (hotelId != null) {
				CtripHotel ctripHotel = ctripHotelService.info(hotelId);
				if (ctripHotel != null) {
					plan.setCoverPath(ctripHotel.getImgUrl());
				}
			} else {
				Restaurant restaurant = restaurantService.info(restaurantId);
				if (restaurant != null) {
					plan.setCoverPath(restaurant.getResPicture());
				}
			}
		}
		List<Map<String, Object>> resultList = ListUtil.listJoin(joinedPlanList, planList, "planId=id", "plan", null);
		//获取对应的user信息
		List<Long> userIdList = ListUtil.getIdList(joinedPlanList, "userId");
		Map<String, Object> userParams = new HashMap<String, Object>();
		userParams.put("ids", userIdList);
		List<String> userColumns = new ArrayList<String>();
		userColumns.add("id");
		userColumns.add("nickname");
		userColumns.add("userface");
		List<User> userList = userService.listColumns(userParams, userColumns);
		resultList = ListUtil.listJoin(resultList, userList, "userId=id", "user", null);
		Map<String, Object> planPollParams = new HashMap<String, Object>();
		planPollParams.put("userId", paramMap.get("currentUserId"));
		planPollParams.put("activityId", activity.getId());
		List<PlanPoll> planPollList = planPollService.list(planPollParams);
		long votedId = -1;
		if (!planPollList.isEmpty()) {
			votedId = planPollList.get(0).getJoinedPlanId();
		}
		for (Map<String, Object> joinedPlan : resultList) {
			if (votedId == Long.valueOf(joinedPlan.get("id").toString())) {
				joinedPlan.put("voted", true);
			} else {
				joinedPlan.put("voted", false);
			}
		}
		return resultList;
	}

	public void prepareSortInformation(Map<String,Object> paramMap) {
		String sortFlag = (String) paramMap.get("sortFlag");
		String sortType = (String) paramMap.get("sortType");
		if ("createTime".equals(sortFlag)) {
			paramMap.put("orderColumn", "create_time");
		} else if ("modifyTime".equals(sortFlag)) {
			paramMap.put("orderColumn", "modify_time");
		} else if ("pollCount".equals(sortFlag)) {
			paramMap.put("orderColumn", "poll_count");
		}
		if ("asc".equals(sortType)) {
			paramMap.put("orderType", "asc");
		} else if ("desc".equals(sortType)) {
			paramMap.put("orderType", "desc");
		}
	}

	/**
	 * 用户是否有行程参赛
	 *
	 * @return
	 */
	public boolean isUserJoinedPlan(long userId) {
		/* 根据状态是1(进行中)的查询activity表对应的活动 */
		Map<String, Object> activeMap = new HashMap<String, Object>();
		activeMap.put("status", Activity.STATUS_RUNNING);
		List<Activity> activityList = activityService.list(activeMap);
		Validate.isTrue(!activityList.isEmpty(), ErrorCode.ERROR_53001, "当前没有活动！");
		long activityId = activityList.get(0).getId();
		/* 根据userid和activityId查询对应的集合数据，如果集合不为空，说明已经投票，就抛出异常 */
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		paramMap.put("activityId", activityId);
		List<JoinedPlan> joinedPlanList = list(paramMap);
		return !joinedPlanList.isEmpty();
	}

	/**
	 * @return Map key=planId,value=JoinedPlan
	 */
	public Map<Long, JoinedPlan> getJoinedPlanMapByPlan(List<Long> planIdList) {
		Map<String, Object> activeMap = new HashMap<String, Object>();
		activeMap.put("status", Activity.STATUS_RUNNING);
		List<Activity> activityList = activityService.list(activeMap);
		if (activityList.isEmpty()) {
			return new HashMap<Long, JoinedPlan>();
		}
		long activityId = activityList.get(0).getId();
		Map<String, Object> joinedPlanParams = new HashMap<String, Object>();
		joinedPlanParams.put("planIds", planIdList);
		joinedPlanParams.put("activityId", activityId);
		List<JoinedPlan> joinedPlanList = list(joinedPlanParams);
		Map<Long, JoinedPlan> joinedPlanMap = new HashMap<Long, JoinedPlan>();
		for (JoinedPlan joinedPlan : joinedPlanList) {
			joinedPlanMap.put(joinedPlan.getPlanId(), joinedPlan);
		}
		return joinedPlanMap;
	}
	
	public  List<Map<String, Object>> getPlanRanking(Map<String,Object> paramsMap)
	{
		return mapper.planRanking(paramsMap);
	}
}
