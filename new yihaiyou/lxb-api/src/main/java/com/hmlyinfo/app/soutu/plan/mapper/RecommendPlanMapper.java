package com.hmlyinfo.app.soutu.plan.mapper;

import java.util.List;
import java.util.Map;

import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.app.soutu.plan.domain.Plan;
import com.hmlyinfo.app.soutu.plan.domain.RecommendPlan;

public interface RecommendPlanMapper <T extends RecommendPlan> extends BaseMapper<T>{
	public List<RecommendPlan> listColumns(Map<String, Object> paramMap);
}
