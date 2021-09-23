package com.hmlyinfo.app.soutu.activity.service;

import com.hmlyinfo.app.soutu.activity.domain.Notification;
import com.hmlyinfo.app.soutu.activity.mapper.NotificationMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService extends BaseService<Notification, Long> {

    @Autowired
    private NotificationMapper<Notification> mapper;

    @Override
    public BaseMapper<Notification> getMapper()
    {
        return mapper;
    }

    @Override
    public String getKey()
    {
        return "id";
    }

}
