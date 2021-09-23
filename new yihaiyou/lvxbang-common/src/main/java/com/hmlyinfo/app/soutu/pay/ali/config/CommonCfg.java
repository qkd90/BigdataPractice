package com.hmlyinfo.app.soutu.pay.ali.config;

/**
 * <p>Title: CommonCfg.java</p>
 * <p/>
 * <p>Description: 支付宝基础信息</p>
 * <p/>
 * <p>Date:2013-12-26</p>
 * <p/>
 * <p>Copyright: Copyright (c) 2013</p>
 * <p/>
 * <p>Company: www.myleyi.com</p>
 *
 * @author zheng.yongfeng
 */
public class CommonCfg {
	//商户支付宝账号
//	public static final String LOGIN_NAME = PropertiesUtils.getCfgInfo(CfgConstants.ALIPAY_LOGIN_NAME);
	public static final String LOGIN_NAME = "service@hmlyinfo.com";

	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
//	public static final String PARTNER = PropertiesUtils.getCfgInfo(CfgConstants.ALIPAY_PARTNER);
	public static final String PARTNER = "2088912063394461";

	// 商户的私钥
//	public static final String KEY = PropertiesUtils.getCfgInfo(CfgConstants.ALIPAY_KEY);
	public static final String KEY = "hafzjeb6kmdutto2scdi6f1n9txnbpgr";

	// 字符编码格式 utf-8
	public static final String INPUT_CHARSET = "utf-8";

}
