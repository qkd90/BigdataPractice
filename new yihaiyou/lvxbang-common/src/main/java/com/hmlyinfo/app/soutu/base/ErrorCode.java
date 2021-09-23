package com.hmlyinfo.app.soutu.base;

public interface ErrorCode {

	/**
	 * 验证异常
	 */
	public final static int ERROR_50001 = 50001;

	/**
	 * 没有数据的操作权限
	 */
	public final static int ERROR_50002 = 50002;

	/**
	 * 搜索服务不可用
	 */
	public final static int ERROR_50003 = 50003;

	/**
	 * 传入参数有误
	 */
	public final static int ERROR_51001 = 51001;

	/**
	 * token不存在，登录异常
	 */
	public final static int ERROR_51002 = 51002;

	/**
	 * 用户未登录
	 */
	public final static int ERROR_51003 = 51003;

	/**
	 * 用户不存在
	 */
	public final static int ERROR_51004 = 51004;

	/**
	 * 用户名密码不匹配
	 */
	public final static int ERROR_51005 = 51005;

	/**
	 * 客户端id不存在
	 */
	public final static int ERROR_51006 = 51006;

	/**
	 * 客户端Secret或url不匹配
	 */
	public final static int ERROR_51007 = 51007;

	/**
	 * 无效的临时令牌
	 */
	public final static int ERROR_51008 = 51008;

	/**
	 * 用户已存在
	 */
	public final static int ERROR_51009 = 51009;

	/**
	 * 邮箱已存在
	 */
	public final static int ERROR_51010 = 51010;

	/**
	 * 电话号码异常
	 */
	public static final int ERROR_51011 = 51011;

	/**
	 * 景点id不存在
	 */
	public final static int ERROR_52002 = 52002;

	/**
	 * 该用户已经对这条评论点过有用
	 */
	public final static int ERROR_52003 = 52003;

	/**
	 * 所要求的相册不存在
	 */
	public final static int ERROR_52004 = 52004;

	/**
	 * commentId不存在
	 */
	public final static int ERROR_52005 = 52005;

	/**
	 * 对应景点的目的地已存在
	 */
	public final static int ERROR_52006 = 52006;

	/**
	 * 目的地信息不存在
	 */
	public final static int ERROR_52007 = 52007;

	/**
	 * 行程信息不存在
	 */
	public final static int ERROR_53001 = 53001;

	/**
	 * 所删除天不存在
	 */
	public final static int ERROR_53002 = 53002;

	/**
	 * 当前用户无权操作此行程
	 */
	public final static int ERROR_53003 = 53003;

	/**
	 * 行程为空
	 */
	public final static int ERROR_53004 = 53004;

	/**
	 * 所选景点数目为0
	 */
	public final static int ERROR_53005 = 53005;

	/**
	 * 所选积分类型当天已经达到上限
	 */
	public final static int ERROR_54001 = 54001;

	/**
	 * 无效的积分类型
	 */
	public final static int ERROR_54002 = 54002;

	/**
	 * 游记信息不存在
	 */
	public final static int ERROR_55001 = 55001;

	/**
	 * 已经点过赞了
	 */
	public final static int ERROR_55002 = 55002;

	/**
	 * 已经投过票
	 */
	public final static int ERROR_59001 = 59001;

	/**
	 * 行程参加的活动和当期活动不匹配
	 */
	public final static int ERROR_59002 = 59002;

	/**
	 * 账号未激活
	 */
	public final static int ERROR_58001 = 58001;

	/**
	 * 景点门票预定失败
	 */
	public final static int ERROR_56001 = 56001;

	/**
	 * 景点门票支付失败
	 */
	public final static int ERROR_56002 = 56002;

	/**
	 * 退票失败
	 */
	public final static int ERROR_56003 = 56003;

	/**
	 * 退款失败
	 */
	public final static int ERROR_56004 = 56004;

	/**
	 * 门票价格信息不存在
	 */
	public final static int ERROR_56006 = 56006;

	/**
	 * 行程预定失败
	 */
	public final static int ERROR_56005 = 56005;

	/**
	 * 前端传入数据格式有误
	 */
	public final static int ERROR_56007 = 56007;

	/**
	 * 订单未处于待支付状态，不可支付
	 */
	public final static int ERROR_56008 = 56008;

}
