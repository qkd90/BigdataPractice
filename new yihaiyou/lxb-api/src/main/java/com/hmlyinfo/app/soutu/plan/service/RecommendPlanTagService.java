package com.hmlyinfo.app.soutu.plan.service;

import com.hmlyinfo.app.soutu.plan.domain.RecommendPlan;
import com.hmlyinfo.app.soutu.plan.domain.RecommendPlanTag;
import com.hmlyinfo.app.soutu.plan.mapper.RecommendPlanTagMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import com.hmlyinfo.base.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class RecommendPlanTagService extends BaseService<RecommendPlanTag, Long>{

	@Autowired
	private RecommendPlanTagMapper<RecommendPlanTag> mapper;
	@Autowired
	private PlanCacheService planCacheService;
	@Autowired
	private RecommendPlanService recommendPlanService;

	@Override
	public BaseMapper<RecommendPlanTag> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

	public List<RecommendPlanTag> list(int cityCode, int day) {
//		List<RecommendPlanTag> list = planCacheService.getRecommendTags(cityCode, day);
//		if (list != null && !list.isEmpty()) {
//			return list;
//		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cityId", cityCode);
		params.put("planDays", day);
		List<RecommendPlan> planList = recommendPlanService.list(params);
		Set<String> tagSet = new HashSet<String>();
		for (RecommendPlan recommendPlan : planList) {
			String tagStr = recommendPlan.getTag();
			if (StringUtil.isEmpty(tagStr)) {
				continue;
			}
			String[] tags = tagStr.split(",");
			for (String tag : tags) {
				addMostQuotedRecommendPlan(cityCode, day, tag, recommendPlan);
				if (!tagSet.contains(tag)) {
					tagSet.add(tag);
				}
			}
		}
		if (tagSet.isEmpty()) {
			return new ArrayList<RecommendPlanTag>();
		}
		return  list(Collections.<String, Object>singletonMap("ids", tagSet));
//		planCacheService.addRecommendTags(cityCode, day, list);
//		return list;
	}

	public void addMostQuotedRecommendPlan(int cityCode, int day, String tag, RecommendPlan recommendPlan) {
		RecommendPlan origin = planCacheService.getRecommendPlan(cityCode, day, tag);
		if (origin == null) {
			planCacheService.addRecommendPlan(cityCode, day, tag, recommendPlan);
		} else if (recommendPlan.getQuoteNum() > origin.getQuoteNum()) {
			planCacheService.addRecommendPlan(cityCode, day, tag, recommendPlan);
		}
	}

}
