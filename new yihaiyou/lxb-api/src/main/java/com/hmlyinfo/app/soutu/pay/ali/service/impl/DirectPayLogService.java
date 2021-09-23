package com.hmlyinfo.app.soutu.pay.ali.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.hmlyinfo.app.soutu.pay.ali.domain.DirectPayLogDto;
import com.hmlyinfo.app.soutu.pay.ali.mapper.DirectPayLogMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

/**
 * 
 *
 * <p>Title: DirectPayLogService.java</p>
 *
 * <p>Description:支付宝交易日志 </p>
 * 
 * <p>Date:2013-7-30</p>
 *
 * <p>Copyright: Copyright (c) 2013</p>
 *
 * <p>Company: www.myleyi.com</p>
 *
 * @author zheng.yongfeng
 * @version
 */
@Service
public class DirectPayLogService extends BaseService<DirectPayLogDto, Long>{

	@Autowired
	private DirectPayLogMapper daoMapper;
	
	/**
	 * 根据订单号获取最新的支付请求日志记录
	 * 当支付失败或取消时同一订单可能发起多次支付请求
	 * @param orderId
	 * @return
	 */
	public DirectPayLogDto getLatestByOrderId(long orderId){
		Map<String, Object> paramMap = Maps.newHashMap();
		paramMap.put("orderId", orderId);
		List<DirectPayLogDto> list = daoMapper.list(paramMap);
		
		//默认获取第一个
		if (!list.isEmpty()) {
			return list.get(0);
		}
		
		return null;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseMapper<DirectPayLogDto> getMapper() {
		// TODO Auto-generated method stub
		return daoMapper;
	}
}
