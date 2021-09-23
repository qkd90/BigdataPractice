package com.hmlyinfo.app.soutu.scenic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.scenic.domain.Topic;
import com.hmlyinfo.app.soutu.scenic.mapper.TopicMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class TopicService extends BaseService<Topic, Long> {

    @Autowired
    private TopicMapper<Topic> mapper;

    @Override
    public BaseMapper<Topic> getMapper() {
        return mapper;
    }

    @Override
    public String getKey() {
        return "id";
    }

}
