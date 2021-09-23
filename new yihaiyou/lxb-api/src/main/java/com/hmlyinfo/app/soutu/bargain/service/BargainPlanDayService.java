package com.hmlyinfo.app.soutu.bargain.service;

import com.hmlyinfo.app.soutu.bargain.domain.BargainPlanDay;
import com.hmlyinfo.app.soutu.bargain.mapper.BargainPlanDayMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BargainPlanDayService extends BaseService<BargainPlanDay, Long> {

    @Autowired
    private BargainPlanDayMapper<BargainPlanDay> mapper;

    @Override
    public BaseMapper<BargainPlanDay> getMapper() {
        return mapper;
    }

    @Override
    public String getKey() {
        return "id";
    }

}
