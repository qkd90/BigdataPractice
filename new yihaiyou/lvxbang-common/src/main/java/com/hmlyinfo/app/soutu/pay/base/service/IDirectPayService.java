package com.hmlyinfo.app.soutu.pay.base.service;

import java.util.Map;

/**
 * <p>Title: IDirectPayService.java</p>
 * <p/>
 * <p>Description: 支付宝支付接口</p>
 * <p/>
 * <p>Date:2013-10-8</p>
 * <p/>
 * <p>Copyright: Copyright (c) 2013</p>
 * <p/>
 * <p>Company: www.myleyi.com</p>
 *
 * @author zheng.yongfeng
 */
public interface IDirectPayService {
	/**
	 * 处理支付成功后的相关业务逻辑
	 *
	 * @param paramMap
	 */
	boolean doBusiness(Map paramMap);

	/**
	 * 判断该笔订单是否在商户网站中已经做过处理
	 * 如果有做过处理，不执行商户的业务程序
	 *
	 * @return
	 */
	boolean hasBusinessDone(Map paramMap);

	/**
	 * 处理订单前先锁住订单记录
	 *
	 * @param paramMap
	 */
	void lockTrade(Map paramMap);
}
