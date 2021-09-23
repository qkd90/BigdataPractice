package com.hmlyinfo.app.soutu.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.common.domain.Sequence;
import com.hmlyinfo.app.soutu.common.mapper.SequenceMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class SequenceService extends BaseService<Sequence, Long>{

	@Autowired
	private SequenceMapper<Sequence> mapper;
	
	public static final String ORDER_KEY = "orderseq";
	public static final String QUNAR_KEY = "order_qunar_seq";
	public static final String RENWOYOU_KEY = "order_renwoyou_seq";
	
	public static final String WX_KEY = "pay_weixin_seq";
	public static final String ALI_KEY = "pay_ali_seq";
	public static final String ALL_IN_KEY = "pay_all_in_seq";

	@Override
	public BaseMapper<Sequence> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

	/**
	 * 根据名称获取序列当前值
	 * @param name
	 * @return
	 */
	public int currentValue(String name)
	{
		return mapper.currentValue(name);
	}
	
	/**
	 * 根据名称获取序列下一个值
	 * @param name
	 * @return
	 */
	public int nextValue(String name)
	{
		return mapper.nextValue(name);
	}
	
	/**
	 * 获取订单序列号
	 * @return
	 */
	public int getOrderSeq()
	{
		return mapper.nextValue(ORDER_KEY);
	}
	
	/**
	 * 获取qunar订单序列号
	 * @return
	 */
	public int getOrderQunarSeq()
	{
		return mapper.nextValue(QUNAR_KEY);
	}
	
	/**
	 * 获取renwoyou订单序列号
	 * @return
	 */
	public int getOrderRenwoyouSeq()
	{
		return mapper.nextValue(RENWOYOU_KEY);
	}
	
	/**
	 * 获取微信支付单序列号
	 * @return
	 */
	public int getPayWxSeq()
	{
		return mapper.nextValue(WX_KEY);
	}
	
	/**
	 * 获取支付宝支付单序列号
	 * @return
	 */
	public int getPayAliSeq()
	{
		return mapper.nextValue(ALI_KEY);
	}

    /**
     * 获取通联支付单序列号
     */
    public int getPayAllInSeq() {
        return mapper.nextValue(ALL_IN_KEY);
    }

    /**
     * 获取支付单序列号
     */
    public int getOrderSeq(String payType) {
        return mapper.nextValue(payType);
    }
}
