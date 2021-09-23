package com.data.data.hmly.service;

import com.data.data.hmly.enums.ShenzhouAccessTokenStatus;
import com.data.data.hmly.service.dao.ShenzhouAccessTokenDao;
import com.data.data.hmly.service.entity.ShenzhouAccessToken;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by huangpeijie on 2016-10-07,0007.
 */
@Service
public class ShenzhouAccessTokenService {
    @Resource
    private ShenzhouAccessTokenDao shenzhouAccessTokenDao;

    public ShenzhouAccessToken get(Long id) {
        return shenzhouAccessTokenDao.load(id);
    }

    public List<ShenzhouAccessToken> getUsedToken() {
        Criteria<ShenzhouAccessToken> criteria = new Criteria<ShenzhouAccessToken>(ShenzhouAccessToken.class);
        criteria.eq("status", ShenzhouAccessTokenStatus.used);
        criteria.gt("accessTokenDate", new Date());
        return shenzhouAccessTokenDao.findByCriteria(criteria);
    }
}
