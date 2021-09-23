package com.hmlyinfo.app.soutu.recplan.controller;

import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.recplan.domain.Recplan;
import com.hmlyinfo.app.soutu.recplan.service.RecplanDetailService;
import com.hmlyinfo.app.soutu.recplan.service.RecplanService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/api/pub/recplan")
public class RecplanPubApi {
	@Autowired
	private RecplanService	service;
	@Autowired
	private RecplanDetailService recplanDetailService;

	/**
	 * 新增
	 * <ul>
	 * <li>url:/api/recplan/add</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/add")
	public @ResponseBody ActionResult add(final HttpServletRequest request, final Recplan domain) {
		service.edit(domain);

		return ActionResult.createSuccess(domain);
	}

	/**
	 * 删除
	 * <ul>
	 * <li>必选:标识{id}
	 * <li>
	 * <li>url:/api/recplan/del</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/del")
	public @ResponseBody ActionResult del(final HttpServletRequest request, final String id) {
		Validate.notNull(id, ErrorCode.ERROR_50001, "标识{id}不能为空");
		service.del(id);

		return ActionResult.createSuccess();
	}

	/**
	 * 编辑
	 * <ul>
	 * <li>必选:标识{id}
	 * <li>
	 * <li>url:/api/recplan/edit</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/edit")
	public @ResponseBody ActionResult edit(final HttpServletRequest request, final Recplan domain) {
		Validate.notNull(request.getParameter("id"), ErrorCode.ERROR_50001, "标识{id}不能为空");
		service.edit(domain);

		return ActionResult.createSuccess(domain);
	}

	/**
	 * 
	 * 查询列表
	 * <ul>
	 * <li>可选：分页大小{pageSize=10}</li>
	 * <li>可选：请求页码{page=1}</li>
	 * <li>url:/api/recplan/list</li>
	 * </ul>
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public @ResponseBody ActionResult list(final HttpServletRequest request) throws Exception {
		Validate.notNull(request.getParameter("cityCode"), ErrorCode.ERROR_51001, "cityCode不能为空");
		Validate.notNull(request.getParameter("days"), ErrorCode.ERROR_51001, "days不能为空");
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);

		return ActionResult.createSuccess(recplanDetailService.listDetails(paramMap));
	}

	/**
	 * 查询对象
	 * <ul>
	 * <li>必选:标识{id}</li>
	 * <li>url:/api/recplan/info</li>
	 * </ul>
	 * 
	 * @return
	 */
	@RequestMapping("/info")
	public @ResponseBody ActionResult info(final String id) {
		Validate.notNull(id, ErrorCode.ERROR_50001, "标识{id}不能为空");
		return ActionResult.createSuccess(service.info(Long.valueOf(id)));
	}

	/**
	 * 根据条件计算行程数量
	 * <ul>
	 * <li>url:/api/pub/recplan/count</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/count")
	@ResponseBody
	public ActionResult count(final HttpServletRequest request) {
		Map<String, Object> params = HttpUtil.parsePageMap(request);
		return service.countAsResult(params);
	}

	/**
	 * 查询详情
	 * <ul>
	 * <li>必选:标识{id}</li>
	 * <li>url:/api/pub/recplan/detail</li>
	 * </ul>
	 * 
	 * @return
	 * @throws Exception
	 * @throws
	 */
	@RequestMapping("/detail")
	public @ResponseBody ActionResult detail(final String id) throws Exception {
		Validate.notNull(id, ErrorCode.ERROR_50001, "标识{id}不能为空");
		Recplan recplan = recplanDetailService.detail(Long.parseLong(id));
		return ActionResult.createSuccess(recplan);
	}

	@RequestMapping("/test")
	public @ResponseBody ActionResult test(final String recommendPlanId) throws Exception {
		Validate.notNull(recommendPlanId, ErrorCode.ERROR_50001, "标识{id}不能为空");
		long id = service.insert(Long.parseLong(recommendPlanId));
		return ActionResult.createSuccess(recplanDetailService.detail(id));
	}

	@RequestMapping("/childdetail")
	public @ResponseBody ActionResult childDetail(final String id) throws Exception {
		Validate.notNull(id, ErrorCode.ERROR_50001, "标识{id}不能为空");
		Recplan recplan = recplanDetailService.childListDetail(Long.parseLong(id));
		return ActionResult.createSuccess(recplan);
	}

}
