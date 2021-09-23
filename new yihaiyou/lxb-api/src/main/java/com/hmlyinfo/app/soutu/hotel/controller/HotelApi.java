package com.hmlyinfo.app.soutu.hotel.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.hotel.service.CTripService;
import com.hmlyinfo.app.soutu.hotel.service.CtripHotelService;
import com.hmlyinfo.app.soutu.hotel.service.HotelCommentService;
import com.hmlyinfo.app.soutu.hotel.service.HotelService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.Validate;

@Controller
@RequestMapping("/api/pub/hotel")
public class HotelApi {
	@Autowired
	HotelService		hotelService;
	@Autowired
	HotelCommentService	hotelCommentService;
	@Autowired
	CTripService		ctService;
	@Autowired
	CtripHotelService	chService;

	/**
	 * 查询酒店评论
	 * <ul>
	 * <li>必填：酒店Id</li>
	 * <li>url:/api/pub/hotel/searchcomment</li>
	 * </ul>
	 */
	@RequestMapping("/searchcomment")
	public @ResponseBody ActionResult searchComment(final HttpServletRequest request) {
		Validate.notNull(request.getParameter("hotelId"), ErrorCode.ERROR_51001);
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		return ActionResult.createSuccess(hotelCommentService.listComment(paramMap));
	}

	@RequestMapping("/countcomment")
	@ResponseBody
	public ActionResult countComment(final HttpServletRequest request) {
		Validate.notNull(request.getParameter("hotelId"), ErrorCode.ERROR_51001);
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		long indexId = hotelCommentService.getIndexId(paramMap);
		Map<String, Object> indexMap = new HashMap<String, Object>();
		indexMap.put("indexId", indexId);
		return hotelCommentService.countAsResult(indexMap);
	}

	/**
	 * <ul>
	 * <li>必填:cityCode 城市id</li>
	 * <li>可选：scenicId 景点id，用于查询景点周边酒店</li>
	 * <li>可选：longitude 经度，用于查询周边酒店</li>
	 * <li>可选：latitude 纬度，用于查询周边酒店</li>
	 * <li>可选:orderColumn 排序字段（默认为score）</li>
	 * <li>可选:orderType 升序asc，降序desc</li>
	 * <li>可选:startDate 开始时间（默认当天）</li>
	 * <li>可选:endDate 结束时间（默认第二天）</li>
	 * </ul>
	 */
	@RequestMapping("/list")
	@ResponseBody
	public ActionResult list(final HttpServletRequest request) {
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);

		return ActionResult.createSuccess(hotelService.listCtrip(paramMap));
	}

	@RequestMapping("/counts")
	@ResponseBody
	public ActionResult count(final HttpServletRequest request) {
		Map<String, Object> params = HttpUtil.parsePageMap(request);

		return hotelService.countCtrip(params);
	}

	@RequestMapping("/info")
	@ResponseBody
	public ActionResult infoCtrip(final HttpServletRequest request) {
		Map<String, Object> params = HttpUtil.parsePageMap(request);
		return ActionResult.createSuccess(hotelService.infoCtrip(params));
	}

	@RequestMapping("/listname")
	@ResponseBody
	public ActionResult listName(final HttpServletRequest request) {
		Validate.isTrue(request.getParameter("name") != null, ErrorCode.ERROR_51001);
		Validate.isTrue(request.getParameter("cityCode") != null, ErrorCode.ERROR_51001);
		Map<String, Object> params = HttpUtil.parsePageMap(request);

		return ActionResult.createSuccess(hotelService.listName(params));
	}

	@RequestMapping("/test")
	@ResponseBody
	public ActionResult test(final HttpServletRequest request) {
		Validate.isTrue(request.getParameter("name") != null, ErrorCode.ERROR_51001);
		Validate.isTrue(request.getParameter("cityCode") != null, ErrorCode.ERROR_51001);
		Map<String, Object> params = HttpUtil.parsePageMap(request);

		return ActionResult.createSuccess(hotelService.listName(params));
	}

}
