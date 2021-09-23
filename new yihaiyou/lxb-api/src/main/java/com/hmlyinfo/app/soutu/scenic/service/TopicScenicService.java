package com.hmlyinfo.app.soutu.scenic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.scenic.domain.TopicScenic;
import com.hmlyinfo.app.soutu.scenic.mapper.TopicScenicMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class TopicScenicService extends BaseService<TopicScenic, Long> {

    @Autowired
    private TopicScenicMapper<TopicScenic> mapper;

    @Override
    public BaseMapper<TopicScenic> getMapper() {
        return mapper;
    }

    @Override
    public String getKey() {
        return "id";
    }

}
