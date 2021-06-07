/*
 * frxs Inc.  湖南兴盛优选电子商务有限公司.
 * Copyright (c) 2017-2019. All Rights Reserved.
 */
package com.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.service.ProviderService;

/**
 * @author lst
 * @version : ProviderService.java,v 0.1 2019年08月14日 11:13
 */
@Service(version = "1.0.0")
public class ProviderServiceImpl implements ProviderService {

    /**
     * 测试方法查询用户信息
     *
     * @return
     */
    @Override
    public String queryUserInfo() {
        return "hello I  am  harry";
    }
}