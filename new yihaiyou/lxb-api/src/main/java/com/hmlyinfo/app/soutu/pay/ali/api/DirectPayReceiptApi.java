package com.hmlyinfo.app.soutu.pay.ali.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.pay.ali.domain.DirectPayReceiptDto;
import com.hmlyinfo.app.soutu.pay.ali.service.impl.DirectPayReceiptService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.Validate;

/**
 * 
 *
 * <p>Title: DirectPayReceiptApi.java</p>
 *
 * <p>Description:支付宝回执相关Api </p>
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
@Controller
@RequestMapping("/api/alipay/directPayReceipt/")
@SuppressWarnings("unchecked")
public class DirectPayReceiptApi{
	@Autowired
	private DirectPayReceiptService directPayReceiptService;

	/**
	 * 新增支付宝回执信息
	 * @param request
	 * <ul>
	 * 	<li>url:/api/alipay/directPayReceipt/add</li>
	 * </ul>
	 * @return
	 */
	@RequestMapping("add")
	public @ResponseBody ActionResult add(final HttpServletRequest request, final DirectPayReceiptDto dto)
	{
		return ActionResult.createSuccess(directPayReceiptService.insert(dto));
	}
	
	
	/**
	 * 根据支付宝回执id删除支付宝回执
	 * 信息
	 * @param request
	 * <ul>
	 * 	<li>必选:支付宝回执标识{receiptId}<li>
	 *  <li>url:/api/alipay/directPayReceipt/del</li>
	 * </ul>
	 * @return
	 */
	@RequestMapping("del")
	public @ResponseBody ActionResult del(final HttpServletRequest request, final DirectPayReceiptDto dto){
		
		Validate.notNull(dto.getReceiptId(), ErrorCode.ERROR_50001, "支付宝回执标识{receiptId}不能为空");
		directPayReceiptService.del(dto.getReceiptId() + "");
		return ActionResult.createSuccess();
	}
	
	
	/**
	 * 编辑支付宝回执信息
	 * @param request
	 * <ul>
	 * 	<li>必选:支付宝回执标识{receiptId}<li>
	 *  <li>url:/api/alipay/directPayReceipt/edit</li>
	 * </ul>
	 * @return
	 */
	@RequestMapping("edit")
	public @ResponseBody ActionResult edit(final HttpServletRequest request, final DirectPayReceiptDto dto){
		Validate.notNull(dto.getReceiptId(), ErrorCode.ERROR_50001, "支付宝回执标识{receiptId}不能为空");
		return ActionResult.createSuccess(directPayReceiptService.edit(dto));
	}
	
}
