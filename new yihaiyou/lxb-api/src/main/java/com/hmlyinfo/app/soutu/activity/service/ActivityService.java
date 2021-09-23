package com.hmlyinfo.app.soutu.activity.service;

import com.hmlyinfo.app.soutu.activity.domain.Activity;
import com.hmlyinfo.app.soutu.activity.mapper.ActivityMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ActivityService extends BaseService<Activity, Long>{

	@Autowired
	private ActivityMapper<Activity> mapper;

	@Override
	public BaseMapper<Activity> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

    public Activity getCurrentActivity() {
        Map<String, Object> activeMap = new HashMap<String, Object>();
        activeMap.put("status", Activity.STATUS_RUNNING);
        List<Activity> list = list(activeMap);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

}
