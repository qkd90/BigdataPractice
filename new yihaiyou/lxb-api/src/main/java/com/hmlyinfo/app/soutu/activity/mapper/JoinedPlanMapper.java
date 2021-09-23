package com.hmlyinfo.app.soutu.activity.mapper;

import java.util.List;
import java.util.Map;

import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.app.soutu.activity.domain.JoinedPlan;
import com.hmlyinfo.app.soutu.plan.domain.Plan;

public interface JoinedPlanMapper <T extends JoinedPlan> extends BaseMapper<T>{
	
	public List<Map<String,Object>> planRanking(Map<String, Object> paramMap);
}
