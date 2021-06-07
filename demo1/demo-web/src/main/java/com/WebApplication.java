/*
 * frxs Inc.  湖南兴盛优选电子商务有限公司.
 * Copyright (c) 2017-2019. All Rights Reserved.
 */
package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lst
 * @version : Application.java,v 0.1 2019年08月14日 11:40
 */

@SpringBootApplication
@MapperScan("com.xsyx.mapper")
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
//
//        try (ConfigurableApplicationContext applicationContext = SpringApplication.run(WebApplication.class, args)) {
////            ITTrackingWatcherService commonService = applicationContext.getBean(ITTrackingWatcherService.class);
////            commonService.list();
//            ITOrderService commonService = applicationContext.getBean(ITOrderService.class);
//            commonService.list();
//        }

    }
}