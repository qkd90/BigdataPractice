package com.zuipin.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class MobileChargeConstants {
	
	// properties
	public static final PropertiesManager	PROPERTIES_MANAGER			= (PropertiesManager) SpringContextHolder.getBean("propertiesManager");
	
	// params key
	public static final String				MERID_KEY					= "MerID";
	public static final String				MERACCOUNT_KEY				= "MerAccount";
	public static final String				ORDERID_KEY					= "OrderID";
	public static final String				TRANSTAT_KEY				= "TranStat";
	public static final String				TRANINFO_KEY				= "TranInfo";
	public static final String				CARDTYPE_KEY				= "CardType";
	public static final String				VALUE_KEY					= "Value";
	public static final String				COMMAND_KEY					= "Command";
	public static final String				INTERFACENAME_KEY			= "InterfaceName";
	public static final String				INTERFACENUMBER_KEY			= "InterfaceNumber";
	public static final String				DATETIME_KEY				= "Datetime";
	public static final String				TRANORDER_KEY				= "TranOrder";
	public static final String				ATTACH_KEY					= "Attach";
	public static final String				KEY_KEY						= "Key";
	public static final String				SIGN_KEY					= "Sign";
	public static final String				ORDERINFO_KEY				= "Orderinfo";
	
	// username;password
	public static final String				MERID_VALUE					= PROPERTIES_MANAGER.getString("kc.merId");
	public static final String				MERACCOUNT_VALUE			= PROPERTIES_MANAGER.getString("kc.merAccount");
	public static final String				KEY_VALUE					= PROPERTIES_MANAGER.getString("kc.key");
	
	// min Balance
	public static final Double				MIN_BALANCE					= Double.valueOf(PROPERTIES_MANAGER.getString("kc.minBalance"));
	
	// datetime format
	public static final SimpleDateFormat	SDF							= new SimpleDateFormat("yyyyMMddHHmmss");
	// decimal format
	public static final DecimalFormat		DF							= new DecimalFormat("#.00");
	
	// url
	public static final String				URL							= "http://www.007ka.cn/recvcdkey.php";
	public static final String				QUERY_URL					= "http://www.007ka.cn/fast_query.php";
	public static final String				ACCOUNT_URL					= "http://www.kc0592.com/Interface/account_money.aspx";
	public static final String				NOTIFY_URL					= "http://www.jxmall.com/mobileChargeNotify.html";
	
	// encoding
	public static final String				ENCODING					= "GB2312";
	
	// 交易成功
	public static final String				TRAN_STAT_SUCCESS			= "1";
	// 查询充值状态，充值成功
	public static final String				TRAN_STAT_CHARGE_SUCCESS	= "1";
	// 查询充值状态，充值中
	public static final String				TRAN_STAT_CHARGE_ING		= "4";
	
	// 数据库手机号码类型（1移动 2电信 3联通）
	public static final String				CMCC_TYPE					= "1";
	public static final String				CT_TYPE						= "2";
	public static final String				CU_TYPE						= "3";
	
}
