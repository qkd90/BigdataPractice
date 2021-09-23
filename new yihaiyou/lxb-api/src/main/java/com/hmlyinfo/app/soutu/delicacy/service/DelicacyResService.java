package com.hmlyinfo.app.soutu.delicacy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.delicacy.domain.DelicacyRes;
import com.hmlyinfo.app.soutu.delicacy.mapper.DelicacyResMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;


@Service
public class DelicacyResService extends BaseService<DelicacyRes, Long>{
    
    @Autowired
    private DelicacyResMapper<DelicacyRes> mapper;

    @Override
    public BaseMapper<DelicacyRes> getMapper() 
    {
        return mapper;
    }
    
    @Override
    public String getKey() 
    {
        return "id";
    }

}
