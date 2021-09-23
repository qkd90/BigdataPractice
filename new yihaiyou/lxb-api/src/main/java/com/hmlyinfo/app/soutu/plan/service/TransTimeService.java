package com.hmlyinfo.app.soutu.plan.service;

import com.hmlyinfo.app.soutu.plan.domain.TransTime;
import com.hmlyinfo.app.soutu.plan.mapper.TransTimeMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by guoshijie on 2014/12/11.
 */
@Service
public class TransTimeService extends BaseService<TransTime, Long>{

    @Autowired
    TransTimeMapper<TransTime> mapper;

    @Override
    public BaseMapper getMapper() {
        return mapper;
    }

    @Override
    public String getKey() {
        return null;
    }

}
