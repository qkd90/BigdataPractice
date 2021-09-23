package com.zuipin.util;

import org.apache.log4j.Logger;

/**
 * Created by zzl on 2017/3/8.
 */
public class PropertiesUtil {

    private static final Logger LOGGER = Logger.getLogger(PinyinUtil.class);

    private static PropertiesManager propertiesManager = SpringContextHolder.getBean("propertiesManager");


    public static String getString(String key) {
        if (!StringUtils.hasText(key)) {
            LOGGER.error("key is null");
        }
        return propertiesManager.getString(key);
    }
}
