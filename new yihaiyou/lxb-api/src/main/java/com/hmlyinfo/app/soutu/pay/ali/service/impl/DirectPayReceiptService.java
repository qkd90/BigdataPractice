package com.hmlyinfo.app.soutu.pay.ali.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.pay.ali.domain.DirectPayReceiptDto;
import com.hmlyinfo.app.soutu.pay.ali.mapper.DirectPayReceiptMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

/**
 * 
 *
 * <p>Title: DirectPayReceiptService.java</p>
 *
 * <p>Description: 支付宝回执服务</p>
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
public class DirectPayReceiptService  extends BaseService<DirectPayReceiptDto, Long>{
	@Autowired
	private DirectPayReceiptMapper daoMapper;

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseMapper<DirectPayReceiptDto> getMapper() {
		// TODO Auto-generated method stub
		return daoMapper;
	}
}
