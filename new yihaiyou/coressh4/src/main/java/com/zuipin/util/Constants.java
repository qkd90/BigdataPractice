package com.zuipin.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * @版权：象屿商城 版权所有 (c) 2012
 * @author:zhengry
 * @version Revision 2.0.0
 * @email:zryuan@xiangyu.cn
 * @see:
 * @创建日期：2013-9-23
 * @功能说明：
 */
public class Constants {
	public static String		UTF8							= "utf-8";
	public static String		PATH							= "path";
	public static String		RES_PATH						= "resPath";
	public static final String	TUAN_PATH						= "tuanPath";
	public static final Long	GENERAL_REGION					= 10000L;
	public static final Long	XIAMEN							= 10011l;
	public static final String	MEMBER							= "member";
	public static final String	MY_CART							= "my_jxmall_cart";
	public static final String	MY_CART_COUPON					= "my_jxmall_cart_coupon";
	public static final String	MY_CART_SPECIAL_DEDUCTIBLE		= "my_jxmall_cart_special_deductible";
	public static final String	MY_CART_DELIVERY				= "my_jxmall_cart_delivery_type";
	public static final String	MY_CART_UNIVERSAL_DEDUCTIBLE	= "my_jxmall_cart_universal_deductible";
	public static final Integer	BROWSE_EXPIRY					= 3600 * 24 * 30;
	
	/**
	 * 购物车来源
	 */
	public static final String	MY_CART_REFERE_URL				= "MY_CART_REFERE_URL";
	
	public static final Long	JXJS_STORE_ID					= 36l;
	
	public static final String	RESOURCE_VERSION				= "resource_version";
	public static final String	IS_JXCARDFLAG_DEFAULT			= "0";
	/**
	 * .商城右侧广告数量
	 */
	public static final Integer	INDEX_NOTICE_AMOUNT				= 6;
	
	/**
	 * .三级分类显示数量
	 */
	public static final Integer	INDEX_TERTIARY_CAT_AMOUNT		= 10;
	/**
	 * .首页右侧广告显示数量
	 */
	public static Integer		INDEX_RIGHT_ADVANCE_AMOUNT		= 4;
	
	public static final Integer	SHOP_RIGHT_ADS_AMOUNT			= 2;
	public static final Integer	SHOP_SEARCH_SHOW_NUM			= 15;
	public static final Integer	SHOP_DISCOUNT_SHOW_NUM			= 8;
	
	public static final String	SHOP_PRAISE_STR					= "shop_praise_str";
	public static final String	PUB_INFO_PRAISE_STR				= "pub_info_praise_str";
	
	public static final Integer	SHOP_PRODUCT_LIST_NUM			= 6;
	/**
	 * 商品详细页推荐商品显示数量
	 */
	public static final Integer	PRODUCT_DETAIL_RECOMMEND_AMOUNT	= 6;
	public static final Integer	HOT_MER_SHOW_NUM				= 6;
	public static final Integer	HOT_COM_SHOW_NUM				= 7;
	public static final Integer	DISCOUNT_SHOW_NUM				= 5;
	public static final Integer	CAT_PRODUCT_LIST_PAGE_SIZE		= 36;
	public static final Integer	SHOP_SHOW_NUM					= 6;
	
	public final static String	QQ_MEMBER_DEFAULT_PWD			= "#QQ";
	public static final String	COOKIE_AD_SRC					= "ad_src";
	public static final Integer	EXPIRY_AD_SRC					= 3600 * 24 * 1;
	// 会员等级
	public static final Long	MEMBER_GRADE_VIP1				= 1L;																		// 贵宾卡
	public static final Long	MEMBER_GRADE_VIP2				= 2L;																		// 贵宾邀请卡
	public static final Long	MEMBER_GRADE_GOLD1				= 3L;																		// 金卡
	public static final Long	MEMBER_GRADE_GOLD2				= 4L;																		// 金卡邀请卡
	public static final Long	MEMBER_GRADE_DIA1				= 5L;																		// 钻石卡
	public static final Long	MEMBER_GRADE_DIA2				= 6L;																		// 钻石邀请卡
	public static final Long	MEMBER_GRADE_NOR				= 7L;																		// 普通用户
	public static final Long	MEMBER_GRADE_LC					= 8L;
	/**
	 * 传福优惠分类
	 */
	public static final Long	CHUAN_FU_SHUI_GUO_CAT			= 2150l;
	public static final Long	CHUAN_FU_SHU_CAI_CAT			= 2149l;
	public static final Double	CHUAN_FU_DISCOUNT				= 0.5;
	public static final Double	CHUAN_FU_MAX_FREE_MONEY			= 40.00;
	// 赠送账号类型
	public final static String	CENT_SAVE_10					= "10";																	// 返现账号
	public final static String	CENT_SAVE_11					= "11";
	public final static String	GIFE_TYPE_16					= "16";
	// 赠送类型
	public final static String	GIFE_TYPE_10					= "10";
	// t_member_cent_save状态
	public final static String	STATE_10						= "10";
	
	public static final double	FREE_CARRY_COST					= 69;
	
	public static final double	CARRY_COST						= 5;
	
	// 是否银行促销 10.无银行促销 11招行 12 建行 13中行 14支付宝 15交行 16银联
	public static final String	MEMBER_LOGIN_NAME_CARD			= "CARD";
	public static final String	PROMOTION_NO					= "0";
	public static final String	BANK_PROMOTION_DEFAULT			= "10";
	public static final String	BANK_CMB_PROMOTION				= "11";
	public static final String	BANK_CCB_PROMOTION				= "12";
	public static final String	BANK_BOC_PROMOTION				= "13";
	public static final String	BANK_ALOPAY_PROMOTION			= "14";
	public static final String	BANK_COMM_PROMOTION				= "15";
	public static final String	BANK_UNIONPAY_PROMOTION			= "16";
	public static final String	UNFARE							= "11";
	public static final String	SIGN_BUY						= "3";
	/**
	 * 登入会员
	 */
	public static final String	PROMOTION_GIFTS					= "promotionGifts";
	public static final String	NEWZONE_PROID					= "newzoneProId";
	public static final Double	JIXIANGKA_PAY_MONEY				= 0D;
	/**
	 * 订单详情类别
	 */
	public static final String	ORDER_DETAIL_COMM				= "0";
	public static final String	ORDER_DETAIL_SIGN				= "1";
	// 商城新闻列表大小
	public static final int		NEWS_PAGE_SIZE					= 10;
	public static final String	DEFULT_NUM						= "0";
	public static final String	LOGIN_NAME						= "login_name";
	// 会员打折授权
	public static final String	MEMBER_DISCOUNT_AUTH			= "11";
	
	public static final String	MEMBER_SHOP_CART_ADD			= "add";
	public static final String	MEMBER_SHOP_CART_SUBTRACT		= "subtract";
	public static final String	MEMBER_SHOP_CART_DEL			= "del";
	public static final String	MEMBER_SHOP_CART_UPDATE			= "update";
	public static final String	MEMBER_SHOP_CART_ALL			= "all";
	/**
	 * 商品详情页比价
	 */
	public static final String	SITE_PRICE_360BUY				= "京东商城";
	public static final String	SITE_PRICE_YIHAODIAN			= "1号店";
	public static final String	SITE_PRICE_AMAZON				= "亚马逊中国";
	public static final String	SITE_PRICE_SUNING				= "苏宁易购";
	public static final String	SITE_PRICE_IS_COMPARE_0			= "0";
	
	public final static String	GROSS_PROFIT					= "GROSS_PROFIT";
	public final static Double	GROSS_PROFIT_VALUE				= 0.05d;
	/**
	 * 会员卡默认密码
	 */
	public static final String	DEFAULT_PWD						= "#";
	/**
	 * 正常状态
	 */
	public static final String	MEMBER_STATE					= "10";
	/**
	 * 会员状态正常consultPage
	 */
	public static final String	MEMBER_NORMAL_STATE				= "10";
	
	public static final short	MEMBER_SM_MSG_TYPE				= 20;
	public static final short	MEMBER_SM_MSG_TYPE_FIND_PWD		= 31;
	public static final short	MEMBER_SM_MSG_TYPE_REGISTER		= 30;
	/**
	 * 邮件发送方式类别， 用户注册
	 */
	public static final String	EMAIL_RAND_TYPE					= "1";
	/**
	 * 邮件发送方式类别， 找回密码
	 */
	public static final String	PWD_RAND_TYPE					= "2";
	/**
	 * 邮件发送方式类别，大客户登录
	 */
	public static final String	LOGIN_RAND_TYPE					= "3";
	/**
	 * 是否记录日志
	 */
	public static final boolean	ISLOG							= true;
	/**
	 * ACCOUNT_TYPE 账号类型 1.购物卡账号 2.返现账号3.邀请返现账号
	 */
	public static final String	ACCOUNT_TYPE_1					= "1";
	public static final String	ACCOUNT_TYPE_2					= "2";
	public static final String	ACCOUNT_TYPE_3					= "3";
	public static final String	ACCOUNT_TYPE_4					= "4";
	/**
	 * TRADE_MODE 交易模式 1.商城 2.店面
	 */
	public static final String	TRADE_MODE_1					= "1";
	public static final String	TRADE_MODE_2					= "2";
	/**
	 * TRADE_TYPE 交易类型 1.购物卡充值 2.购物卡消费 3.返现抵扣 4返现 5.购物卡（订单取消返现） 6.返现卡（订单取消返现）
	 */
	public static final String	TRADE_TYPE_1					= "1";
	public static final String	TRADE_TYPE_2					= "2";
	public static final String	TRADE_TYPE_3					= "3";
	public static final String	TRADE_TYPE_4					= "4";
	public static final String	TRADE_TYPE_5					= "5";
	public static final String	TRADE_TYPE_6					= "6";
	public static final String	TRADE_TYPE_7					= "7";
	public static final String	TRADE_TYPE_8					= "8";
	public static final String	TRADE_TYPE_9					= "9";
	/**
	 * 免单金额返还
	 */
	public static final String	GIFE_TYPE_24					= "24";
	/**
	 * 赠送类型 10 返现 11 储值 12 电子券
	 */
	public static final String	SAVE_HIS_10						= "10";
	public static final String	SAVE_HIS_11						= "11";
	public static final String	SAVE_HIS_12						= "12";
	
	public static final String	FREE_ORDER_MSG					= "感谢您对吉象吉送的支持，您已成功领取<money>元<type>，请查看账户余额";
	
	// 0激活 1查询
	public final static String	COUPON_FLAG						= "0";
	public final static String	PROMOTION_SIGN_TYPE_3			= "3";
	
	/**
	 * 大客户
	 */
	public static final String	LARGE_CUSTOMER_CARD_NO			= "8888888888";
	public static final String	ORDER_NO_PREFIX_LC				= "LC";
	
	public static List<String>	PROMOTION_CLASS					= new ArrayList<String>();
	
	/**
	 * "1" 为实物商品 "2"为虚拟商品
	 */
	public static final String	TUAN_PRO_TYPE					= "1";
	public static final String	TUAN_VIRTUAL_PRO				= "2";
	
	/**
	 * 支付完成
	 */
	public static final String	ORDER_PAY_STATE					= "1";
	
	public static final Short	TUAN_SM_MODEL_2025				= 2025;
	
	public static final String	TUAN_SM							= "您在“小象团”购买的--<TUANNAME>(团购券号:<ACCOUNT>, 密码:<PWD>) 有效期至:<DATE>。<CONTENT>";
	
	public static final Integer	TUAN_SEQUENCE_LENGTH			= 4;
	
	/**
	 * 路径分隔符
	 */
	public static final String	SEPARATOR						= File.separator;
	
	public static final Integer	REGISTER_SEND_SM_NUM			= 3;
	public static final Integer	FIND_PWD_SEND_SM_NUM			= 3;
	public static final Integer	SEND_SM_IP_MAX_NUM				= 5;
	
	public static final String	SHARE							= "share";
	public static final String	IS_SHARE						= "is_share";
	
	/**
	 * emd促销
	 */
	public final static String	EMD_PROMOTION_FLAG				= "emd_promotion_flag";
	public final static String	EMD_PROMOTION_FLAG_USE			= "emd_promotion_flag_use";
	public final static String	EMD_NOTICE						= "emd_notice";
	public final static String	EMD_NOTICE_USE					= "emd_notice_use";
	
	/**
	 * seesion valueName
	 */
	public static final String	URL								= "url";
	/**
	 * 是否自动登陆 0为自动登陆
	 */
	public static final String	IS_AUTOMATIC_LOGIN				= "1";
	public static final String	NO_AUTOMATIC_LOGIN				= "0";
	public static final String	REMEMBER_TYPE					= "1";
	public static final String	NO_REMEMBER_TYPE				= "0";
	public static final String	MEMBER_INFO						= "member_info";
	public static final String	MEMBER_FLAG						= "member_flag";
	
	/**
	 * 购物车类别 0 普通购物车 1促销商品购物车 2团购商品
	 */
	public static final String	MEMBER_SHOP_CART_0				= "0";
	public static final String	MEMBER_SHOP_CART_1				= "1";
	public static final String	MEMBER_SHOP_CART_2				= "2";
	
	public static final String	COOKIE_SHOP_CART				= "shop_cart";
	public static final String	COOKIE_PROMOTION_SHOP_CART		= "promotion_shop_cart";
	public static final String	DYNAMIC_ERR_DEFAULT				= "96";
	
	public final static String	VIEWINFO_MAP					= "viewinfoMap";
	
	public final static String	QQ_CAI_BEI_KEY1					= "qq_caibei_key1";
	public final static String	QQ_CAI_BEI_KEY2					= "qq_caibei_key2";
	
	/**
	 * 文件上传
	 */
	public static final int		BUFFER_SIZE						= 1024 * 1024 * 2;
	
	public static final String	MEMBER_MASTER					= "member.master";
	public static final String	MEMBER_SHAIDAN					= "member.shaidan";
	public static final String	MEMBER_HEAD						= "member.head";
	
	/**
	 * 我的订单列表大小
	 */
	public static final int		DEFAULT_PAGE_SIZE				= 15;
	// 我的关注列表大小
	public static final int		MY_ATTEN_PAGE_SIZE				= 4;
	// 已购商品列表大小
	public static final int		BOUGHT_PAGE_SIZE				= 10;
	// 我的免单中记录列表大小
	public static final int		FREE_ORDER_PAGE_SIZE			= 5;
	// 我的储值消费明细记录列表大小
	public static final int		SVC_CARD_PAGE_SIZE				= 10;
	public static final String	MALL_KEY_NO						= "1002";
	/**
	 * 商品下架
	 */
	public static final String	PRODUCT_THE_SHELF				= "12";
	public static final String	COMMENT_FLAG					= "1";
	/**
	 * 讨论区商品分页
	 */
	public static final int		PRO_NEW_NUM						= 20;
	/**
	 * 默认浏览人数
	 */
	public static final Long	BROWSE_NUM						= 0l;
	public static final String	COMMENT_SVC_MONEY				= "comment_money";
	public static final String	COMMENT_PEOPLE_NUM				= "comment_people_num";
	
	public static final Long	COMMENT_DISTANCE_DAYS			= 1000 * 24 * 60 * 60 * 15l;
	public static final String	MEMBER_GRADE_NAME				= "贵宾卡";
	public static final String	ORDER_BBS_PEOPLE_NUM			= "order_bbs_people_num";
	public static final String	ORDER_BBS_MONEY					= "order_bbs_money";
	/**
	 * 评价晒单列表大小
	 */
	public static final int		MY_COMMENT_PAGE_SIZE			= 10;
	
	// 退款记录列表大小
	public static final int		RETURN_FORM_PAGE_SIZE			= 5;
	
	// 退换货申请中订单列表大小
	public static final int		ORDER_RETURN_LIST_PAGE_SIZE		= 5;
	// 退换货申请中记录列表大小
	public static final int		RECORD_RETURN_LIST_PAGE_SIZE	= 10;
	
	// 我的优惠券中记录列表大小
	public static final int		USER_COUPON_PAGE_SIZE			= 10;
	public static final String	SEARCH_DEFAULT_VALUE			= "请输入您要找的商品";
	
	/**
	 * 夺宝
	 */
	public final static int		WEEK_OF_FRI						= 6;
	public final static int		DUOBAO_PAGE_SIZE				= 9;
	public final static int		CRAZED_FRIDAY_BEGIN_HOUR		= 10;
	public final static int		CRAZED_FRIDAY_OVER_HOUR			= 23;
	public final static String	DUOBAO_DEFAULT_LIST				= "duobao_default_list";
	
	/**
	 * 分享默认保留天数
	 */
	public static final Integer	SHARE_DEFAULT_DAY				= 3600 * 24 * 7;
	public static final int		PROMOTION_ADVANCE_NUM			= 1;
	/**
	 * 国庆节开始以及结束时间
	 */
	public static String		NATIONAL_DAY_START_TIME			= "2013-10-01";
	public static String		NATIONAL_DAY_OVER_TIME			= "2013-10-08";
	
	public static final String	TUANGOU_KEY_NO					= "1005";
	
	public static final String	MEMBER_INFO_NO					= "member_info_no";
	/**
	 * 边逛边聊每日最大评价数
	 */
	public static final int		SHOPPING_AND_CHAT_MAX_FOR_DAY	= 3;
	
	/**
	 * 功能描述：生成商品入库单据编号
	 * 
	 * @author : Teny_lu 刘鹰
	 * @E_Mail : liuying5590@163.com
	 * @CreatedTime : 2014年12月8日-下午4:49:17
	 * @return
	 */
	public static String getInputNo() {
		return "PI" + DateFormatUtils.format(new Date(), "yyyyMMdd");
	}
	
	/**
	 * 功能描述：生成商品出库单据编号
	 * 
	 * @author : Teny_lu 刘鹰
	 * @E_Mail : liuying5590@163.com
	 * @CreatedTime : 2014年12月8日-下午4:49:17
	 * @return
	 */
	public static String getOutputNo() {
		return "PO" + DateFormatUtils.format(new Date(), "yyyyMMdd");
	}
	
	/**
	 * 功能描述：自动获取挂单号
	 * 
	 * @author : Teny_lu 刘鹰
	 * @E_Mail : liuying5590@163.com
	 * @CreatedTime : 2014年12月15日-上午10:19:41
	 * @return
	 */
	public static String getGuadanNo() {
		return "GD" + DateFormatUtils.format(new Date(), "yyyyMMdd");
	}
	
	/**
	 * 功能描述： 获取当前时间
	 * 
	 * @author : Teny_lu 刘鹰
	 * @E_Mail : liuying5590@163.com
	 * @CreatedTime : 2014年10月23日-下午7:05:15
	 * @return
	 */
	public static String getCurrTime() {
		return DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + RandomStringUtils.randomNumeric(6);
	}
	
	public static String	internalCode	= "FA58E621A5F193AFFA58E621A5F193AF";	// 加密内码
	public static Integer	log_info		= 1;
	public static Integer	log_debug		= 2;
	public static Integer	log_error		= 3;
	public static Integer	log_warn		= 4;
	public static String	password		= "123456";								// 默认密码
																					
}
