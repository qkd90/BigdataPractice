package com.hmlyinfo.app.soutu.order.controller;

import com.hmlyinfo.app.soutu.account.service.MemberService;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.order.domain.OrderPassenger;
import com.hmlyinfo.app.soutu.order.service.OrderPassengerService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.Validate;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/auth")
public class OrderPassengerApi
{
	@Autowired
	private OrderPassengerService service;

	/**
	 * 新增
	 * <ul>
	 * 	<li>url:/api/orderpassenger/add</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/orderpassenger/add")
	public @ResponseBody ActionResult add(final HttpServletRequest request, final OrderPassenger domain)
	{
        domain.setUserId(MemberService.getCurrentUserId());
		service.edit(domain);

		return ActionResult.createSuccess(domain);
	}

    @RequestMapping("/orderpassenger/addlist")
    @ResponseBody
    public ActionResult addList(final String passengers) {
        Long userId = MemberService.getCurrentUserId();
        try {
            List<Map<String, Object>> list = new ObjectMapper().readValue(passengers, List.class);
            service.addList(list, userId);
            return ActionResult.createSuccess();
        } catch (IOException e) {
            e.printStackTrace();
            return ActionResult.createFail(ErrorCode.ERROR_51001, "参数错误");
        }




    }

	/**
	 * 删除
	 * <ul>
	 * 	<li>必选:标识{id}<li>
	 *  <li>url:/api/orderpassenger/del</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/orderpassenger/del")
	public @ResponseBody ActionResult del(final HttpServletRequest request, final String id)
	{
		Validate.notNull(id, ErrorCode.ERROR_50001, "标识{id}不能为空");
		service.delete(id);
		
		return ActionResult.createSuccess();
	}
	
	
	/**
	 * 编辑
	 * <ul>
	 * 	<li>必选:标识{id}<li>
	 *  <li>url:/api/orderpassenger/edit</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/orderpassenger/edit")
	public @ResponseBody ActionResult edit(final HttpServletRequest request, final OrderPassenger domain)
	{
		Validate.notNull(request.getParameter("id"), ErrorCode.ERROR_50001, "标识{id}不能为空");
		service.edit(domain);
		
		return ActionResult.createSuccess(domain);
	}
	
	/**
	 * 
	 * 查询列表
	 * <ul>
	 * 	<li>可选：分页大小{pageSize=10}</li>
	 * 	<li>可选：请求页码{page=1}</li>
	 * 	<li>url:/api/orderpassenger/list</li>
	 * </ul>
	 * 
	 * @return 
	 */
	@RequestMapping("/orderpassenger/list")
	public @ResponseBody ActionResult list(final HttpServletRequest request)
	{
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		
		return ActionResult.createSuccess(service.list(paramMap));
	}
	
	/**
	 * 查询对象
	 * <ul>
	 * 	<li>必选:标识{id}</li>
	 *  <li>url:/api/orderpassenger/info</li>
	 * </ul>
	 * 
	 * @return
	 */
	@RequestMapping("/orderpassenger/info")
	public @ResponseBody ActionResult info(final String id) 
	{
		Validate.notNull(id, ErrorCode.ERROR_50001, "标识{id}不能为空");
		return ActionResult.createSuccess(service.info(Long.valueOf(id)));
	}
}
