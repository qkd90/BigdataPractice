package com.hmlyinfo.app.soutu.activity.service;

import com.hmlyinfo.app.soutu.activity.domain.Activity;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.base.util.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.activity.domain.PlanPoll;
import com.hmlyinfo.app.soutu.activity.mapper.PlanPollMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PlanPollService extends BaseService<PlanPoll, Long>{

	@Autowired
	private PlanPollMapper<PlanPoll> mapper;
	@Autowired
	private  ActivityService activityService;
	@Override
	public BaseMapper<PlanPoll> getMapper()
	{
		return mapper;
	}

	@Override
	public String getKey()
	{
		return "id";
	}

	/**
	 * 判断行程是否被当前用户投过票
	 */
	public boolean isUserPoll(long userId) {
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
		List<PlanPoll> list = list(paramMap);
		return !list.isEmpty();
	}

}
