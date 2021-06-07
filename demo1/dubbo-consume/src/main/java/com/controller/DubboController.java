/*
 * frxs Inc.  湖南兴盛优选电子商务有限公司.
 * Copyright (c) 2017-2019. All Rights Reserved.
 */
package com.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.service.ProviderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lst
 * @version : DubboController.java,v 0.1 2019年08月14日 15:29
 */

@RestController
@RequestMapping("/index")
public class DubboController {

    @Reference(version = "1.0.0")
    ProviderService providerService;

    @GetMapping("/hello")
    public String testDubbo() {
        return providerService.queryUserInfo();
    }

}