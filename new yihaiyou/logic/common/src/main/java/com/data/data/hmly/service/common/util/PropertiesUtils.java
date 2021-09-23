package com.data.data.hmly.service.common.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * 
 *
 * <p>Title: PropertiesUtils.java</p>
 *
 * <p>Description: 加载classpath下的资源配置文件工具类</p>
 * 
 * <p>Date:2013-7-3</p>
 *
 * <p>Copyright: Copyright (c) 2013</p>
 *
 * <p>Company: www.myleyi.com</p>
 *
 * @author zheng.yongfeng
 * @version
 */
public class PropertiesUtils {
	private static Logger logger = LogManager.getLogger(PropertiesUtils.class);
	
	private static String CFG_FILE_PATH = "errorcode.properties";
	
	private static Properties cfgProperties = getProperties(CFG_FILE_PATH);
	
	/**
	 * 获取配置文件"cfg.properties"中的配置信息
	 * @param key
	 * @return
	 */
	public static String getCfgInfo(int key){
		return getCfgInfo(String.valueOf(key));
	}
	
	/**
	 * 获取配置文件"cfg.properties"中的配置信息
	 * @param key
	 * @return
	 */
	public static String getCfgInfo(String key){
		return cfgProperties == null ? null : cfgProperties.getProperty(key);
	}
	
	/**
	 * 获取指定资源文件指定信息
	 * @param propertiesFilePath
	 * @param key
	 * @return
	 */
	public static String getValue(String propertiesFilePath,String key){
		return getProperties(propertiesFilePath).getProperty(key);
	}
	

	
	/**
	 * 获取指定的资源对象
	 * @param propertiesFilePath
	 * @return
	 */
	public static Properties getProperties(String propertiesFilePath){
		Properties properties = null;
		try {
			logger.info("加载资源[" + propertiesFilePath + "] ...");
			properties = PropertiesLoaderUtils.loadAllProperties(propertiesFilePath);
		} 
		catch (IOException e) {
			logger.error("加载资源[" + propertiesFilePath + "]失败");
			logger.error(e.getMessage());

			e.printStackTrace();
		}
		
		return properties;
	}
}

