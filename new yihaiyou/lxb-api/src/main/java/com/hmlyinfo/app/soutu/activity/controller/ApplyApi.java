package com.hmlyinfo.app.soutu.activity.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hmlyinfo.app.soutu.account.service.MemberService;
import com.hmlyinfo.app.soutu.activity.domain.Apply;
import com.hmlyinfo.app.soutu.activity.service.ApplyService;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.Validate;

@Controller
@RequestMapping("/api/auth/apply")
public class ApplyApi
{
	@Autowired
	private ApplyService applyService;
	/**
	 * 
	 * 查询列表
	 *
	 * @return 
	 */
	@RequestMapping("/list")
	public @ResponseBody ActionResult list(final HttpServletRequest request)
	{
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		
		return ActionResult.createSuccess(applyService.list(paramMap));
	}
	
	/**
	 * 根据id查询对象
	 * @param id
	 * @return
	 */
	@RequestMapping("/info")
	public @ResponseBody ActionResult info(HttpServletRequest request){
		long id=Long.valueOf(request.getParameter("id"));
		return ActionResult.createSuccess(applyService.info(id));
	}
	
	/**
	 * 添加
	 * @return
	 */
	@RequestMapping("/add")
	public @ResponseBody ActionResult apply(HttpServletRequest request){
		/*获取参数*/
		Long userId=MemberService.getCurrentUserId();
		/*String email=request.getParameter("email");*/
		String phone=request.getParameter("phone");
		String address=request.getParameter("address");
		/*验证数据*/
		/*Validate.notNull(email, ErrorCode.ERROR_50001);*/
		Validate.notNull(phone, ErrorCode.ERROR_50001);
		Validate.notNull(address, ErrorCode.ERROR_50001);
		/*根据用户id查询是否已经申请，已经申请过就抛出异常*/
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		List<Apply> list=applyService.list(paramMap);
		Validate.isTrue(list.isEmpty(), ErrorCode.ERROR_51009, "用户id已经存在！");
		Apply apply = new Apply();
		apply.setUserId(userId);
		/*apply.setEmail(email);*/
		apply.setPhone(phone);
		apply.setAddress(address);
		applyService.insert(apply);
		return ActionResult.createSuccess(apply);
	}

	/**
	 * 判断用户是否申请
	 * @param request
	 * @return
	 */
	@RequestMapping("/isUserApply")
	public @ResponseBody ActionResult isUserApply(HttpServletRequest request){
		long userId = MemberService.getCurrentUserId();
		boolean flag = applyService.isUserApply(userId);
		Map<String, Boolean> result = new HashMap<String, Boolean>();
		result.put("isUserApply",flag);
		return ActionResult.createSuccess(result);
	}
}
