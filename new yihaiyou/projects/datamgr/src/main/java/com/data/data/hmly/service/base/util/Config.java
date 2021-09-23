package com.data.data.hmly.service.base.util;

import com.data.data.hmly.service.common.util.PropertiesUtils;

import java.util.Properties;

public class Config {
	
	private static final Properties cfgProperties = PropertiesUtils.getProperties("config.properties");
	
	public static String get(String key)
	{
		return cfgProperties.getProperty(key);
	}

}
