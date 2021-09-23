package com.data.data.hmly.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

public class WfConstants {
	public static String	OPERATOR_VALID	= "1";
	public static int		intTimeOut		= 1000;
	public static String	internalCode	= "FA58E621A5F193AFFA58E621A5F193AF";	// 加密内码
	public static String	password		= "123456";							// 登录账号的默认密码
	public static int		pageIndex		= 1;									// 默认从第一页开始查询, 等于-1，表示查询所有
	public static int		pageSize		= 20;									// 每页显示的记录数
	public static int		logFlag			= 9;									// 等于1时不记录操作日志;等于2时系统只记录增加、删除、修改等关键操作日志；等于9时记录增加、删除、修改、和查询等操作的日志
	public static int		tpye			= 1;
	public static int		queryType		= 2;									// 等于1表示：精确查询;等于2表示：模糊查询；
	public static int		unitLike		= 1;									// 等于1表示：启动模糊查询下级用户（select * from wf_staff
	public static int		USBKEYVALIDATE	= 0;									// 等于0标识不使用USB验证 等于1表示开启
																					
	// where unit_id like
	// '900003%';）；等于2表示：不启用模糊查询；
	public static enum REASON {
		采购入库, 修改供应商数量, 创建采购任务, 驳回采购任务, 订单购买, 创建采购单, 配送
	};
	
	public static enum DUTYREASON {
		修改采购价, 修改商品预警, 订单负买, 创建采购单
	};
	
	public static String	ComName						= "";									// 单位名称
	public static String	userName					= "DEMO_";								// 学生默认的单位名称
	// private static WfActionlog wfActionlog = new WfActionlog();
	public static String	positionID					= "GB_1001";							// 系统给学生默认的职位编号
																								
	public static String	insertString				= "新增操作";
	public static String	deleteString				= "删除操作";
	public static String	updateString				= "更新操作";
	public static String	checkSum					= "激活操作";
	public static String	frozen						= "冻结操作";
	public static String	remove_Frozen				= "解冻操作";
	public static String	getString					= "查看操作";
	public static String	searchString				= "查询操作";
	
	public static int		reportFontLen				= 5;									// 报表中显示的每行字体长度
																								
	public static String	cron						= "0 0 6 * * ? *";
	
	public static String	FTPSAVEPATH					= "E:/ftp/";
	public static String	EXCELSAVEPATH				= "excelFile";
	public static String	CommodityFileSavePath		= "Commodity";
	// 设置权限管理方式 0=不控制提交功能 1=控制提交功能
	public static int		AUTHORIZATIONTYPE			= 0;
	
	/** 定时器中触发器的常量名 **/
	// 报表定时器触发器名称
	public static String	REPORTTRIGGER				= "POLICYREPORTTRIGGER";
	// 分仓商品预警触发器名称
	public static String	LOCALCOMMODITYWARNING		= "LOCALCOMMODITYWARNINGTRIGGER";
	// 供应商商品预警触发器名称
	public static String	SUPPLIERCOMMODITYWARNING	= "SUPPLIERCOMMODITYWARNINGTRIGGER";
	
	// 品牌
	public static String	BRAND						= "brand";
	
	// 积分制
	public static int		FIRSTPOINT					= 10;									// 第一天签到积分
	public static int		SECONDPOINT					= 10;									// 连续两天签到积分
																								
	// public static List getBeans(Class entityClass)
	// {
	// ApplicationContext context =
	// WebApplicationContextUtils.getWebApplicationContext(ServletActionContext.getServletContext());
	// BaseDAOImpl baseDAO = (BaseDAOImpl) context.getBean("baseDAO");
	// return baseDAO.queryHibernate(entityClass, true);
	// }
	
	// 获得当前年份
	public static int getNowDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		return Integer.valueOf(format.format(new Date()));
	}
	
	/*
	 * public static List getBeans(String hql) { ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(ServletActionContext.getServletContext());
	 * BaseDAOImpl baseDAO = (BaseDAOImpl) context.getBean("baseDAO"); return baseDAO.queryHibernate(hql); }
	 */
	
	/**
	 * 通过JVM的UUID产生，并取出-
	 * 
	 * @return 长度为32位的字符串
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		StringBuilder sb = new StringBuilder(32);
		sb.append(uuid.substring(0, 8));
		sb.append(uuid.substring(14, 18));
		sb.append(uuid.substring(19, 23));
		sb.append(uuid.substring(24));
		return sb.toString();
	}
	
	/**
	 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
	 * 
	 * @return SimpleDateFormat("yyyy-MM-dd HH:mm:ss") Timestamp类型
	 */
	public static Timestamp getSimpleDateFormat1() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp nowSystemTime = Timestamp.valueOf(sdf.format(cal.getTime()));
		return nowSystemTime;
	}
	
	/**
	 * SimpleDateFormat("yyyyMMddHHmmss")
	 * 
	 * @return SimpleDateFormat("yyyyMMddHHmmss") 字符串类型
	 */
	public static String getSimpleDateFormat2() {
		return new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(java.util.Calendar.getInstance().getTime());
	}
	
	/**
	 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
	 * 
	 * @return SimpleDateFormat("yyyy-MM-dd HH:mm:ss") 字符串类型
	 */
	public static String getSimpleDateFormat3() {
		return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(java.util.Calendar.getInstance().getTime());
	}
	
	/**
	 * Java获取客户端真实IP地址的方法 通常通过request.getRemoteAddr()取得客户端的IP地址，做鉴权和校验，逻辑没问题，那么肯定request .getRemoteAddr()出了问题，google下，发现有人遇到类似的问题。 在JSP里，获取客户端的IP地址的方法是：request.getRemoteAddr
	 * （），这种方法在大部分情况下都是有效的。但是在通过了Apache，Squid等反向代理软件就不能获取到客户端的真实IP地址了。 如果使用了反向代理软件，将http://192.168.1.110：2046/ 的URL反向代理为 http://www.javapeixun.com.cn /
	 * 的URL时，用request.getRemoteAddr（）方法获取的IP地址是：127.0 .0.1　或　192.168.1.110，而并不是客户端的真实IP。 经过代理以后，由于在客户端和服务之间增加了中间层，因此服务器无法直接拿到客户端的IP，服务器端应用也无法直接通过转发请求的地址返回给客户端。 但是在转发请求的HTTP头信息中
	 * ，增加了X－FORWARDED－FOR信息。用以跟踪原有的客户端IP地址和原来客户端请求的服务器地址。当我们访问http ://www.javapeixun.com.cn /index.jsp/ 时， 其实并不是我们浏览器真正访问到了服务器上的index.jsp文件，而是先由代理服务器去访问http
	 * ://192.168.1.110：2046/index.jsp ，代理服务器再将访问到的结果返回给我们的浏览器， 因为是代理服务器去访问index. jsp的，所以index.jsp中通过request.getRemoteAddr（）的方法获取的IP实际上是代理服务器的地址 ，并不是客户端的IP地址。 于是可得出获得客户端真实IP地址的方法一：
	 */
	public String getRemortIP(HttpServletRequest request) {
		if (request.getHeader("x-forwarded-for") == null) {
			return request.getRemoteAddr();
		}
		return request.getHeader("x-forwarded-for");
	}
	
	/**
	 * 可是当我访问http://www.5a520.cn /index.jsp/ 时，返回的IP地址始终是unknown，也并不是如上所示的127.0.0.1　或　192.168.1.110了， 而我访问http://192.168.1.110：2046/index.jsp
	 * 时，则能返回客户端的真实IP地址，写了个方法去验证。原因出在了Squid上。squid.conf 的 配制文件　forwarded_for 项默认是为on，如果 forwarded_for 设成了 off 　则：X-Forwarded-For： unknown 于是可得出获得客户端真实IP地址的方法二：
	 */
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * 是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串Ｉｐ值，究竟哪个才是真正的用户端的真实IP呢？ 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。 如：X-Forwarded-For：192.168.1.110， 192.168.1.120， 192.168.1.130，
	 * 192.168.1.100用户真实IP为： 192.168.1.110
	 * 
	 * @参照 http://suoyihen.iteye.com/blog/1355934
	 * @return 获得客户端的真实IP地址
	 */
	public static String getIp() {
		if (ServletActionContext.getRequest() == null)
			return "";
		String ip = ServletActionContext.getRequest().getHeader("X-Requested-For");
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = ServletActionContext.getRequest().getHeader("X-Forwarded-For");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = ServletActionContext.getRequest().getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = ServletActionContext.getRequest().getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = ServletActionContext.getRequest().getHeader("HTTP_CLIENT_IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = ServletActionContext.getRequest().getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = ServletActionContext.getRequest().getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * 根据IP获得MAC地址
	 * 
	 * @param ip
	 * @return 根据IP获得MAC地址
	 */
	public static String getMac(String ip) {
		String str = "";
		String macAddress = "";
		try {
			Process p = Runtime.getRuntime().exec("nbtstat -A " + ip);
			InputStreamReader ir = new InputStreamReader(p.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			for (int i = 1; i < 100; i++) {
				str = input.readLine();
				if (str != null) {
					if (str.indexOf("MAC Address") > 1) {
						macAddress = str.substring(str.indexOf("MAC Address") + 14, str.length());
						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace(System.out);
		}
		return macAddress;
		
	}
	
	/**
	 * 记录操作日志，参数是动作的内容如：做了账号解除冻结操作!
	 * 
	 * @param target
	 *            目标或主体，如对demo账号，对人员信息、员工实体进行操作等
	 * @param actionContent
	 *            参数是动作的内容如：做了账号解除冻结操作!,查看了某个人员的信息等z
	 */
	/*
	 * public static void recordingLog(String target, String actionName, String actionBefore, String actionAfter) { wfActionlog.setLogId(WfConstants.getUUID());
	 * wfActionlog.setInitiatorAccount(ServletActionContext.getRequest().getSession().getAttribute("account").toString());
	 * wfActionlog.setStaffName(ServletActionContext.getRequest().getSession().getAttribute("staffName").toString());
	 * wfActionlog.setIpAddress(ServletActionContext.getRequest().getSession().getAttribute("ip").toString());
	 * wfActionlog.setClientMac(ServletActionContext.getRequest().getSession().getAttribute("mac").toString());
	 * wfActionlog.setActionTime(WfConstants.getSimpleDateFormat3().toString());
	 * wfActionlog.setPositionId(ServletActionContext.getRequest().getSession().getAttribute("usePositionId").toString());
	 * wfActionlog.setPositionName(ServletActionContext.getRequest().getSession().getAttribute("positionName").toString()); wfActionlog.setTarget(target);
	 * wfActionlog.setActionName(actionName); wfActionlog.setActionBefore(actionBefore); wfActionlog.setActionAfter(actionAfter); } public static WfActionlog getWfActionlog() {
	 * return wfActionlog; } public static void setWfActionlog(WfActionlog wfActionlog) { WfConstants.wfActionlog = wfActionlog; }
	 */
	
	public static void main(String[] args) {
		// System.out.println(new Constants().getSimpleDateFormat1());
		// System.out.println(new Constants().getSimpleDateFormat2());
		// System.out.println(new Constants().getSimpleDateFormat3());
		Short s = 1;
		System.out.println(Short.valueOf("1") == s);
	}
	
	/**
	 * 获取资源文件里面的键值
	 * 
	 * @param propName
	 * @return
	 */
	/*
	 * public static final String getPolicyRes(String propName) { return PropConfig.getPropValue(propName); }
	 */
}