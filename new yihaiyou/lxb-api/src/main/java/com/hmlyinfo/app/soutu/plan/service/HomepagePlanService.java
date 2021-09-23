package com.hmlyinfo.app.soutu.plan.service;

import com.hmlyinfo.app.soutu.plan.domain.HomepagePlan;
import com.hmlyinfo.app.soutu.plan.mapper.HomepagePlanMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomepagePlanService extends BaseService<HomepagePlan, Long> {

    @Autowired
    private HomepagePlanMapper<HomepagePlan> mapper;

    @Override
    public BaseMapper<HomepagePlan> getMapper() {
        return mapper;
    }

    @Override
    public String getKey() {
        return "id";
    }

}
