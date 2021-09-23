package com.hmlyinfo.app.soutu.scenicTicket.qunar.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.scenicTicket.qunar.domain.QunarPrice;
import com.hmlyinfo.app.soutu.scenicTicket.qunar.domain.QunarTicket;
import com.hmlyinfo.app.soutu.scenicTicket.qunar.mapper.QunarPriceMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import com.hmlyinfo.base.util.ListUtil;
import com.hmlyinfo.base.util.Validate;

@Service
public class QunarPriceService extends BaseService<QunarPrice, Long>{

	private static final String PARTNER_CODE = "3999347859";
	private static final String	PARTNER_KEY = "a61ac440fe54d5e99025d5541aa12038";
	
	@Autowired
	private QunarPriceMapper<QunarPrice> mapper;
	@Autowired
	private QunarTicketService ticketService;
	@Autowired
	private QunarService qunarService;
	

	@Override
	public BaseMapper<QunarPrice> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}
	
	/**
	 * 根据门票的本地id查询门票价格数据
	 * @param paramMap
	 * @return
	 */
	public List<QunarPrice> listByTicketId(Map<String, Object> paramMap){
		String ticketId = (String) paramMap.get("ticketId");
		QunarTicket ticket = ticketService.info(Long.parseLong(ticketId));
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productId", ticket.getProductId());
		return mapper.list(params);
	}
	
	/**
	 * 查询门票的价格列表
	 * 根据门票的价格模式查询返回适当的数据
	 * @return
	 */
	public QunarPrice bizinfo(String productId, String date)
	{
		Map<String, Object> paramMap = Maps.newHashMap();
		paramMap.put("productId", productId);
		QunarTicket t = (QunarTicket)ticketService.one(paramMap);
		// 门票信息不存在
		Validate.notNull(t);
		// 日历模式查询某一天的门票
		if (t.getTeamType() == 0)
		{
			paramMap.put("useDate", date);
		}
		// 分类模式查询匹配区间段的门票
		else 
		{
			paramMap.put("displayDate", date);
		}
		
		List<QunarPrice> priceList = mapper.list(paramMap);
		Validate.notNull(ListUtil.getSingle(priceList), ErrorCode.ERROR_56006, "门票价格不存在");
		return ListUtil.getSingle(priceList);
		
	}
	
	public void getPriceInfo()
	{
		boolean flag = true;
		int pageNo = 1;
		while(flag)
		{
			Map<String, Object> priceMap = qunarService.priceInfoList(pageNo + "");
			List<Map<String, Object>> priceList = (List<Map<String, Object>>) priceMap.get("productPriceInfos");
			if(priceList.isEmpty()){
				flag = false;
				continue;
			}
			
			for(Map<String, Object> price : priceList){
				insertOrUpdate(price);
			}
			pageNo++;
		}
	}
	
	public Map<String, String> updatePrices(Map<String, Object> paramMap)
	{
		Map<String, String> params = new HashMap<String, String>();
		for (String keyName : paramMap.keySet()) {
			params.put(keyName, (String) paramMap.get(keyName));
		}
			
		String sign = params.get("sign");
		//checkResult为true，则验签通过
		//SignUtil.checkSign详见附录5.1.1
		boolean checkResult = SignUtil.checkSign(params, "partnerKey", sign);
		
		Map<String, String> responseMap = new HashMap<String, String>();
		if(checkResult){
			responseMap.put("status", "true");
			String resSign = SignUtil.sign(params, PARTNER_KEY);
			responseMap.put("sign", resSign);
			
			//根据id列表更新票状态
			final String ids = params.get("productIds");
			final String status = params.get("status");
			new Thread(){
	    		@Override
	    		public void run(){
	    			updatePriceStatus(ids, status);
	    		}
	    	}.start();
			
			
		}else{
			responseMap.put("status", "false");
			String resSign = SignUtil.sign(params, PARTNER_KEY);
			responseMap.put("sign", resSign);
		}
		
		return responseMap;
	}
	
	public void updatePriceStatus(String ids, String status)
	{
		if(status.equals("OFF_LINE")){
			priceOffLine(ids);
		}else{
			String[] productIds = ids.split(",");
			for(String productId : productIds){
				Map<String, Object> priceMap = qunarService.singlePrice(productId);
				insertOrUpdate(priceMap);
			}
		}
	}
	
	public void priceOffLine(String ids)
	{
		String[] productIds = ids.split(",");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("productIds", productIds);
		List<QunarPrice> oldPriceList = mapper.list(paramMap);
		for(QunarPrice oldPrice : oldPriceList){
			oldPrice.setStatus(QunarPrice.PRICE_STATUS_MISS);
		}
	}
	
	public void insertOrUpdate(Map<String, Object> priceMap)
	{
		String productId = (String) priceMap.get("productId");
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("productId", productId);
		List<QunarPrice> priceList =  mapper.list(paraMap);
		for(QunarPrice oldPrice : priceList){
			oldPrice.setStatus(QunarPrice.PRICE_STATUS_MISS);
			mapper.update(oldPrice);
		}
		
		List<Map<String, Object>> newPriceList = (List<Map<String, Object>>) priceMap.get("priceInfos");
		for(Map<String, Object> newPriceMap : newPriceList){
			QunarPrice newPrice = new QunarPrice();
			newPrice.setProductId(productId);
			newPrice.setPriceId((String) newPriceMap.get("priceId"));
			newPrice.setQunarPrice((Integer) newPriceMap.get("qunarPrice"));
			newPrice.setMarketPrice((Integer) newPriceMap.get("marketPrice"));
			newPrice.setAvailableCount((Integer) newPriceMap.get("availableCount"));
			newPrice.setUseDate((String) newPriceMap.get("useDate"));
			newPrice.setDisplayBeginDate((String) newPriceMap.get("displayBeginDate"));
			newPrice.setDisplayEndDate((String) newPriceMap.get("displayEndDate"));
			newPrice.setMinBuyCount((Integer) newPriceMap.get("minBuyCount"));
			newPrice.setMaxBuyCount((Integer) newPriceMap.get("maxBuyCount"));
			newPrice.setStatus(QunarPrice.PRICE_STATUS_VALID);
			mapper.insert(newPrice);
		}
	}

}
