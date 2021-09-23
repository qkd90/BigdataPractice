package com.data.data.hmly.service.lxbcommon;

import com.data.data.hmly.service.lxbcommon.dao.CustomRequireDestinationDao;
import com.data.data.hmly.service.lxbcommon.entity.CustomRequireDestination;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by caiys on 2016/6/15.
 */
@Service
public class CustomRequireDestinationService {
    @Resource
    private CustomRequireDestinationDao customRequireDestinationDao;

    public CustomRequireDestination get(Long id) {
        return customRequireDestinationDao.load(id);
    }
}
