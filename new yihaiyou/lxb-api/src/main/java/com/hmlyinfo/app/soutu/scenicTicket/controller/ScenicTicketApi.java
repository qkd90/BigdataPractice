package com.hmlyinfo.app.soutu.scenicTicket.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.scenicTicket.domain.ScenicRecommendTicket;
import com.hmlyinfo.app.soutu.scenicTicket.service.RenwoyouService;
import com.hmlyinfo.app.soutu.scenicTicket.service.ScenicTicketService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.StringUtil;
import com.hmlyinfo.base.util.Validate;

@Controller
@RequestMapping("/api/public/scenicTicket")
public class ScenicTicketApi {
	@Autowired
	private ScenicTicketService	service;
	@Autowired
	private RenwoyouService		renwoyouService;

	/**
	 * 获取门票详细信息
	 * <ul>
	 * <li>必选：id 门票信息</li>
	 * <li>必选：type 类型（1：任我游；...）</li>
	 * <li>url:/api/ScenicTicket/list</li>
	 * </ul>
	 */
	@RequestMapping("/info")
	@ResponseBody
	public ActionResult info(Long id, int type) {
		return ActionResult.createSuccess(service.info(id, type));
	}

	/**
	 *
	 * 查询门票的数量
	 * <ul>
	 * <li>必选：scenicId 景点编号</li>
	 * <li>url:/api/ScenicTicket/count</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("count")
	public @ResponseBody ActionResult count(final String scenicId) {
		Validate.notNull(scenicId, ErrorCode.ERROR_51001);
		Map<String, Object> paramMap = Maps.newHashMap();
		return ActionResult.createCount(service.count(Long.valueOf(scenicId)));
	}

	/**
	 *
	 * 查询列表
	 * <ul>
	 * <li>可选：分页大小{pageSize=10}</li>
	 * <li>可选：请求页码{page=1}</li>
	 * <li>url:/api/ScenicTicket/list</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/list")
	public @ResponseBody ActionResult list(final HttpServletRequest request) {
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		return ActionResult.createSuccess(service.list(paramMap));
	}

	/**
	 * 多个id查询
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/listColumns")
	@ResponseBody
	public ActionResult listColumns(final HttpServletRequest request) {
		Map<String, Object> params = HttpUtil.parsePageMap(request);
		String scenicIds = request.getParameter("scenicIds");
		if (StringUtil.isNotBlank(scenicIds)) {
			List<String> scenicList = Lists.newArrayList(scenicIds.split(","));
			params.put("scenicIds", scenicList);
		}
		return ActionResult.createSuccess(service.list(params));
	}

	/**
	 * 所有类型的门票列表
	 * 
	 * @return
	 */
	@RequestMapping("/listAll")
	@ResponseBody
	public ActionResult listAll(final HttpServletRequest request) {
		Map<String, Object> params = HttpUtil.parsePageMap(request);
		return ActionResult.createSuccess(service.listAll(params));
	}

	/**
	 * 按照景点id查询所有门票数据
	 * <url> <li>planId: 行程编号，必选</li> </url>
	 */
	@RequestMapping("listByPlan")
	@ResponseBody
	public ActionResult listByPlan(Long planId) {
		Validate.notNull(planId, ErrorCode.ERROR_51001);
		return ActionResult.createSuccess(service.listByPlan(planId));
	}

	/**
	 * 查询行程的景点是否购买过门票
	 * <url> <li>planId: 行程编号，必选</li> </url>
	 */
	@RequestMapping("listOrdered")
	@ResponseBody
	public ActionResult listOrdered(Long planId) {
		Validate.notNull(planId, ErrorCode.ERROR_51001);
		return ActionResult.createSuccess(service.listOrdered(planId));
	}

	@RequestMapping("listScenicId")
	@ResponseBody
	public ActionResult listScenicList(Long planId) {
		Validate.notNull(planId, ErrorCode.ERROR_51001);
		return ActionResult.createSuccess(service.listScenicIdList(planId));
	}

	@RequestMapping("isTicketExist")
	@ResponseBody
	public ActionResult isTicketExist(Long scenicId) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", service.isScenicHasTicket(scenicId));
		return ActionResult.createSuccess(result);
	}

	/**
	 * 通过行程id查询推荐套票列表
	 * 
	 * @author liujing
	 * @param planId
	 * @return
	 */
	@RequestMapping("/listTicketByPlan")
	@ResponseBody
	public ActionResult listTicketByPlan(Long planId) {
		Validate.notNull(planId, ErrorCode.ERROR_50001);
		Map<Integer, List<List<ScenicRecommendTicket>>> result = service.listSeasonTicketByPlan(planId);
		if (result.size() > 0) {
			return ActionResult.createSuccess(result);
		} else {
			return ActionResult.createSuccess();
		}
	}

	/**
	 * 根据门票id和类型查询是否有价格数据
	 * 
	 * 必选标识：{ticketId}
	 * 必选标识：{type}
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/pricenum")
	@ResponseBody
	public ActionResult priceNum(final HttpServletRequest request) {
		Validate.notNull(request.getParameter("ticketId"), ErrorCode.ERROR_50001);
		Validate.notNull(request.getParameter("type"), ErrorCode.ERROR_50001);
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		return service.priceNum(paramMap);
	}

	/**
	 * 查询门票可用库存
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/availableCount")
	public @ResponseBody ActionResult availableCount(final HttpServletRequest request) {
		Validate.notNull(request.getParameter("ticketId"), ErrorCode.ERROR_51001, "门票id不可为空");
		Validate.notNull(request.getParameter("type"), ErrorCode.ERROR_51001, "门票类型不可为空");
		Validate.notNull(request.getParameter("useDate"), ErrorCode.ERROR_51001, "门票使用日期不可为空");
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		return service.availableCount(paramMap);
	}

}
