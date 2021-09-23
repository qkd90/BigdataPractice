package com.data.data.hmly.service.base;

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
     * 未登录或者登录超时
     */
    public final static int ERROR_61001 = 61001;
	
	/**
	 * 没有访问权限
	 */
	public final static int ERROR_61002 = 61002;
	
	/**
	 * 找回密码操作已失效
	 */
	public final static int ERROR_61003 = 61003;
	
	/**
	 * 邮箱/手机格式不正确
	 */
	public final static int ERROR_61004 = 61004;
	
	/**
	 * 注册操作已失效
	 */
	public final static int ERROR_61005 = 61005;
	
	/**
	 * 商户名称已存在
	 */
	public final static int ERROR_62001 = 62001;

	/**
	 * 酒店opensearch，验证错误
	 */
	public static final int ERROR_50003 = 50003;

}
