package com.data.data.hmly.service.base.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 *
 * <p>Title: BizConstants.java</p>
 *
 * <p>Description: 业务逻辑相关公用静态常量</p>
 * 
 * <p>Date:2013-7-31</p>
 *
 * <p>Copyright: Copyright (c) 2013</p>
 *
 * <p>Company: www.myleyi.com</p>
 *
 * @author zheng.yongfeng
 * @version
 */
public class BizConstants {
	/**
	 * 定义默认的请求响应流内容
	 */
	public final static String CONTENT_TYPE_HTML = "text/html; charset=UTF-8";
	
	/**
	 * 记住用户登陆名
	 */
	public final static String REMEMBER_FLAG_YES = "1";
	
	/**
	 * 不用记住用户登陆名
	 */
	public final static String REMEBER_FLAG_NO = "0";
	
	/**
	 * 常用标识，有效/是
	 */
	public final static String COMMON_FLAG_TRUE = "T";
	
	/**
	 * 常用标识，无效/否
	 */
	public final static String COMMON_FLAG_FALSE = "F";
	
	/**
	 * 常用标识，启用
	 */
	public final static String COMMON_STATUS_OPEN = "1";
	
	/**
	 * 常用标识，关闭
	 */
	public final static String COMMON_STATUS_CLOSE = "0";
	
	
	/**
	 * cookie默认有效期
	 */
	public final static int COOKIE_MAX_AGE = 3600*24*31;
	
	/**
	 * 系统当前时间
	 */
	public final static String CURRENT_SYS_TIME = "currentSysTime";
	
	/**
	 * 默认主键值
	 */
	public final static int DEFAULT_KEY_INT = -1;
	
	/**
	 * 保存用户信息的session key
	 */
	public final static String SESSION_KEY_USER = "loginName";

	/**
	 * 保存用户信息的session key
	 */
	public final static String SESSION_KEY_MANAGER = "loginManager";	
	
	/**
	 * 门户类型：1-管理员门户；2-商家门户
	 */
	public final static String PORTAL_TYPE_MGRER = "1";
	public final static String PORTAL_TYPE_PARTNER = "2";
	
	/**
	 * 类型：1-门户；2-菜单；3-按钮；4-链接
	 */
	public final static String MENU_TYPE_PORTAL = "1";
	public final static String MENU_TYPE_MENU = "2";
	public final static String MENU_TYPE_BUTTON = "3";
	public final static String MENU_TYPE_LINK = "4";
	
	/**
	 * 注册类型：1-手机；2-邮箱
	 */
	public final static String REGISTER_TYPE_PHONE = "1";
	public final static String REGISTER_TYPE_EMAIL = "2";
	
	/**
	 * 菜单顶层节点标识
	 */
	public final static String PARENT_MENU_ID = "0";
	
	/**
	 * 区域顶层节点标识
	 */
	public final static String PARENT_AREA_ID = "0";
	
	/**
	 * 保存用户信息的session key
	 */
	public final static String SESSION_KEY_USERINFO = "userInfo";

	/**
	 * 请求参数服务标识service_id
	 */
	public final static String REQUEST_SERVICE_ID = "SERVICE_ID";
	
	/**
	 * 页面验证码
	 */
	public final static String PAGE_VALIDATE_CODE = "validCodeImage";
	
	/**
	 * 短信验证码
	 */
	public final static String SMS_VALIDATE_CODE = "smsCodeImage";
	
	/**
	 * 默认密码
	 */
	public final static String DEFAULT_PASSWORD = "123456";
	
	/**
	 * 存储账户相关操作记录，比如：修改找回，激活，修改邮箱手机等；只限单次修改。如果集群需考虑其他实现方式
	 * key=用户ID；value=操作方式（SMS：短信；EMAIL：邮箱）;客户端IP;有效时长
	 */
	public static Map<String, String> ACCOUNTOPT = new HashMap<String, String>();
	public final static long ACCOUNTOPT_TIME = 1800;  // 有效时长半小时：0.5*60*60
	public final static String ACCOUNTOPT_SMS = "SMS";
	public final static String ACCOUNTOPT_EMAIL = "EMAIL";

	/**
	 * 操作结果代码:UNKNOW；SUCCESS；FAILURE；UNAUTH
	 */
	public final static String OPT_RESULT_UNKNOW = "UNKNOW";
	public final static String OPT_RESULT_SUCCESS = "SUCCESS";
	public final static String OPT_RESULT_FAILURE = "FAILURE";
	public final static String OPT_RESULT_UNAUTH = "UNAUTH";
	
	/**
	 * 常用标识：1-是；2-否；
	 */
	public final static int COMMON_TRUE = 1;
	public final static int COMMON_FALSE = 2;

	/**
	 * 商户状态：1-待审核；2-经营中；3-下架；4-拒绝
	 */
	public final static String COMPANY_STATUS_UNAUDIT = "1";
	public final static String COMPANY_STATUS_THROUGH = "2";
	public final static String COMPANY_STATUS_DOWN = "3";
	public final static String COMPANY_STATUS_REJECT = "4";

	/**
	 * 审核状态日志类型：1-商户；2-景点；3-酒店；4-门票；5-房型；6-特价线路
	 */
	public final static long CHECKSTATUSLOG_TYPE_COMPANY = 1;
	public final static long CHECKSTATUSLOG_TYPE_SCENIC = 2;
	public final static long CHECKSTATUSLOG_TYPE_HOTEL = 3;
	public final static long CHECKSTATUSLOG_TYPE_TICKET = 4;
	public final static long CHECKSTATUSLOG_ROOMTYPE = 5;
	public final static long CHECKSTATUSLOG_TYPE_SPECIALLINE = 6;
	
	
	/**
	 * 商户审核类型：1-申请商户；2-修改资料；3-重新上架
	 */
	
	public final static String COMPANY_APPLY = "1";
	public final static String COMPANY_UPDATEJUR = "2";
	public final static String COMPANY_RESHELF = "3";

	/**
	 *  酒店索引资源
	 */
	public final static int HOTEL_INDEX_RESOURCE_OWN = 2;//来自自己上传
	
	/**
	 * 推荐行程状态：1-草稿；2-上架；3-下架
	 */
	public final static int RECPLAN_STATUS_DRAFT = 1;
	public final static int RECPLAN_STATUS_UP = 2;
	public final static int RECPLAN_STATUS_DOWN = 3;

	/**
	 * 推荐位置：1-TOP1；2-TOP2；3-TOP3；99-无
	 */
	public final static int RECLOC_TOP1 = 1;
	public final static int RECLOC_TOP2 = 2;
	public final static int RECLOC_TOP3 = 3;
	public final static int RECLOC_NONE = 99;

	/**
	 * trip类型：1-景点；3-酒店；2-美食；4-交通
	 */
	public final static int TRIP_TYPE_SCENIC = 1;
	public final static int TRIP_TYPE_HOTEL = 3;
	public final static int TRIP_TYPE_DELICACY = 2;
	public final static int TRIP_TYPE_TRAFFIC = 4;

	/**
	 * 交通类型：3-步行；1-公交；2-打的
	 */
	public final static int TRAFFIC_TYPE_WALK = 3;
	public final static int TRAFFIC_TYPE_BUS = 1;
	public final static int TRAFFIC_TYPE_TAXI = 2;

	/**
	 * 审核的类型
	 */
	public final static String CHECK_TYPE_STATUS_MODIFY = "2";//资料修改
	public final static String CHECK_TYPE_STATUS_APPLY = "1";//申请用户

	public static final String API_KEY = "R9424rkP6oyCzex5FuLa7XIw";
}
