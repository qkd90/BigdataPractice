package com.hmlyinfo.app.soutu.plan.mapper;

import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.app.soutu.plan.domain.PlanUrbanTrafficRel;

public interface PlanUrbanTrafficRelMapper <T extends PlanUrbanTrafficRel> extends BaseMapper<T>{
	
	void delScenicRel(Long planId);
	
	void delHotelRel(Long planId);
	
}
