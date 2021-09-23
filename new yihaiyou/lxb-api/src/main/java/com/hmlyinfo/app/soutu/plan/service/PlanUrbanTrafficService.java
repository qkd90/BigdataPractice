package com.hmlyinfo.app.soutu.plan.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.hmlyinfo.app.soutu.plan.domain.PlanUrbanTrafficRel;
import com.hmlyinfo.app.soutu.plan.domain.UrbanTraffic;
import com.hmlyinfo.base.util.ListUtil;

@Service
public class PlanUrbanTrafficService {

	@Autowired
	private UrbanTrafficService trafficService;
	@Autowired
	private PlanUrbanTrafficRelService relService;
	
	/**
	  * 根据行程ID查询行程的小交通列表
	  *
	  * @Title: getPlanUrbanTraffic
	  * @author: shiqingju
	  * @email shiqingju@hmlyinfo.com
	  * @Description: TODO
	  * @return List<UrbanTraffic>    返回类型
	  * @throws
	 */
	public List<UrbanTraffic> getPlanUrbanTraffic(Long planId)
	{
		Map<String, Object> paramMap = Maps.newHashMap();
		paramMap.put("planId", planId);
		List<PlanUrbanTrafficRel> relList = relService.list(paramMap);
		Set<Long> uids = ListUtil.getIdSet(relList, "urbanTrafficId");
		
		paramMap.clear();
		paramMap.put("ids", uids);
		return trafficService.list(paramMap);
	}
	
	/**
	  * 保存行程的景点交通
	  *
	  * @Title: saveScenicTraffic
	  * @author: shiqingju
	  * @email shiqingju@hmlyinfo.com
	  * @Description: TODO
	  * @return void    返回类型
	  * @throws
	 */
	@Transient
	public void saveScenicTraffic(List<UrbanTraffic> trafficList, Long planId)
	{
		// 先删除目前的行程交通关联数据
		relService.delScenicRel(planId);
		
		savePlanTraffic(trafficList, planId);
	}
	
	/**
	 * 保存行程酒店的交通
	  *
	  * @Title: saveHotelTraffic
	  * @author: shiqingju
	  * @email shiqingju@hmlyinfo.com
	  * @Description: TODO
	  * @return void    返回类型
	  * @throws
	 */
	@Transient
	public void saveHotelTraffic(List<UrbanTraffic> trafficList, Long planId)
	{
		relService.delHotelRel(planId);
		
		savePlanTraffic(trafficList, planId);
	}
	
	/**
	  * 保存行程的小交通
	  * 如果小交通库中已经有数据，则更新小交通，否则新增
	  * @Title: savePlanTraffic
	  * @author: shiqingju
	  * @email shiqingju@hmlyinfo.com
	  * @Description: TODO
	  * @return void    返回类型
	  * @throws
	 */
	private void savePlanTraffic(List<UrbanTraffic> trafficList, Long planId)
	{
		// 判断小交通库中是否已经有同样数据，有则更新，否则新增
		Map<String, Object> trafficParam = Maps.newHashMap();
		for (UrbanTraffic traffic : trafficList)
		{
			trafficParam.put("startTripType", traffic.getStartTripType());
			trafficParam.put("startTripId", traffic.getStartTripId());
			trafficParam.put("endTripType", traffic.getEndTripType());
			trafficParam.put("endTripId", traffic.getEndTripId());
			
			UrbanTraffic srcUrbanTraffic = (UrbanTraffic)trafficService.one(trafficParam);
			if (srcUrbanTraffic != null)
			{
				traffic.setId(srcUrbanTraffic.getId());
				trafficService.update(traffic);
			}
			else
			{
				trafficService.insert(traffic);
			}
			
			// 新增关联数据
			PlanUrbanTrafficRel rel = PlanUrbanTrafficRel.fromUrbanTraffic(traffic);
			rel.setPlanId(planId);
			
			relService.insert(rel);
		}
	}
}
