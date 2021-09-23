package com.hmlyinfo.app.soutu.base.properties;

import com.hmlyinfo.app.soutu.base.PropertiesUtils;

import java.util.Properties;

public class Config {

	private static final Properties cfgProperties = PropertiesUtils.getProperties("config.properties");

	public static String get(String key) {
		return cfgProperties.getProperty(key);
	}

}
