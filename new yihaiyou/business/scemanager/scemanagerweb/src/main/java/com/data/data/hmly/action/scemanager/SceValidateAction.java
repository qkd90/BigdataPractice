package com.data.data.hmly.action.scemanager;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.common.entity.ProductValidateCode;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Result;

/**
 * 线路
 * @author caiys
 * @date 2015年10月13日 下午4:12:38
 */
public class SceValidateAction extends FrameBaseAction implements ModelDriven<ProductValidateCode> {

	private ProductValidateCode productValidateCode = new ProductValidateCode();
	
	
	
	public Result validateCode(){
		return dispatch();
	}
	
	
	
	@Override
	public ProductValidateCode getModel() {
		return productValidateCode;
	}

	

}
