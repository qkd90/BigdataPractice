/*
 * frxs Inc.  湖南兴盛优选电子商务有限公司.
 * Copyright (c) 2017-2019. All Rights Reserved.
 */
package com;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lst
 * @version : Application.java,v 0.1 2019年08月14日 11:00
 */

@SpringBootApplication
@EnableDubbo
public class Application {
    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);


    }

}