package com.data.data.hmly.action.scevalidate;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.common.ProductValidateCodeService;
import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.common.entity.ProductValidateCode;
import com.data.data.hmly.service.ticket.TicketService;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.StringUtils;

import net.sf.json.JSONObject;

import javax.annotation.Resource;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 线路
 * @author caiys
 * @date 2015年10月13日 下午4:12:38
 */
public class SceValidateAction extends FrameBaseAction implements ModelDriven<ProductValidateCode> {

	private ProductValidateCode productValidateCode = new ProductValidateCode();
	
	@Resource
	private ProductValidateCodeService validateCodeService;
	@Resource
	private TicketService ticketService;
	
	
	Map<String, Object>	map					= new HashMap<String, Object>();
	/*
	 * 验证门票
	 * 
	 */
	public Result validateCode(){
		return dispatch();
	}
	
	
	@Override
	public ProductValidateCode getModel() {
		return productValidateCode;
	}

	public Result valCode(){
		
		String validateCode = (String) getParameter("validateCode");
		
		String content = "";
		
		if(!StringUtils.isEmpty(validateCode)){
			Long scenicId = null;
			if (getCompanyUnit().getSysUnitDetail() != null) {
				scenicId = getCompanyUnit().getSysUnitDetail().getScenicid();
			}
			ProductValidateCode productValidateCode = validateCodeService.validateByCode(validateCode, scenicId);
			
			if(productValidateCode != null){
				
//				Product product = 
				productValidateCode.setUsed(1);
				productValidateCode.setUpdateTime(new Date());
				
				validateCodeService.update(productValidateCode);
				
//				ticket ticket = ticketService.findTicketById(productValidateCode.getProduct().getId());
				
				Product product = productValidateCode.getProduct();
				
				map.put("product", product);
				
				content = "验证成功！";
				simpleResult(map, true, content);
			}else{
				content="验证码无效！";
				simpleResult(map, false, content);
			}
		}else{
			content = "请输入验证码!";
			simpleResult(map, false, content);
		}
		
		
//		JsonConfig jsonConfig = JsonFilter.getFilterConfig("user","supplier","topProduct","parent","productimage");
//		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		JSONObject json = JSONObject.fromObject(map);
//				JSONArray.fromObject(list,jsonConfig);
		return json(json);
		
		
//		return jsonResult(map);
	}

}
