package com.zuipin.util;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

/**
 * @版权：象屿商城 版权所有 (c) 2012
 * @author Kingsley
 * @version Revision 2.0.0
 * @email:wxlong@xiangyu.cn
 * @see:
 * @创建日期：2012-9-21
 * @功能说明：
 */
public class PropertiesManager extends PropertyPlaceholderConfigurer implements Serializable {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 3279899240894830753L;
	
	public String getLogpath() {
		return getString("log.path");
	}
	
	public String getLogtoHivepath() {
		return getString("log.to.hive.path");
	}
	
	public String getZkHostIp() {
		return getString("zkHostIp");
	}
	
	public String getCatchPriceUrl() {
		return getString("catchpriceurl");
	}
	
	public String getCouponTemplate() {
		return getString("coupon.template");
	}
	
	public String getCouponDateNum() {
		return getString("coupon.dateNum");
	}
	
	public int getMaxZkNum() {
		return getInteger("maxZkNum");
	}
	
	public String getPromotionPackage() {
		return getString("promotion.package");
	}
	
	public String getCookieDomain() {
		return getString("cookie.domain");
	}
	
	public String getShopCartCookieDomain() {
		return getString("shopCart.domain");
	}
	
	@Override
	protected Properties mergeProperties() throws IOException {
		properties = super.mergeProperties();
		Constants.PATH = (String) properties.get("path");
		Constants.RES_PATH = (String) properties.get("respath");
		return properties;
	}
	
	public String getString(String key) {
		String value = properties.getProperty(key);
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		return value;
	}
	public String getString(String key, String defaultString) {
		return properties.getProperty(key, defaultString).trim();
	}
	
	public Long getLong(String key) {
		return Long.valueOf(getString(key));
	}
	
	public Integer getInteger(String key) {
		return Integer.valueOf(getString(key));
	}

	public Float getFloat(String key) {
		return Float.valueOf(getString(key));
	}

	public Boolean getBoolean(String key) {
		return Boolean.valueOf(getString(key));
	}

	public Boolean getBoolean(String key, Boolean defaultBoolean) {
        String value = properties.getProperty(key);
        if (StringUtils.isNotBlank(value)) {
            return Boolean.valueOf(value);
        }
        return defaultBoolean;
	}
	
	private Properties	properties;
	
}
