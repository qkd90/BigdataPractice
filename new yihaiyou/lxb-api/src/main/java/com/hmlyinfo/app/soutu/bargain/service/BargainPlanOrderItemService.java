package com.hmlyinfo.app.soutu.bargain.service;

import com.hmlyinfo.app.soutu.bargain.domain.BargainPlanOrderItem;
import com.hmlyinfo.app.soutu.bargain.mapper.BargainPlanOrderItemMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BargainPlanOrderItemService extends BaseService<BargainPlanOrderItem, Long> {

    @Autowired
    private BargainPlanOrderItemMapper<BargainPlanOrderItem> mapper;

    @Override
    public BaseMapper<BargainPlanOrderItem> getMapper() {
        return mapper;
    }

    @Override
    public String getKey() {
        return "id";
    }

}
