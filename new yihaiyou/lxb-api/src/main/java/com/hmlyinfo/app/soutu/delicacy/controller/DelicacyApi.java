package com.hmlyinfo.app.soutu.delicacy.controller;

import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.delicacy.service.DelicacyService;
import com.hmlyinfo.app.soutu.delicacy.service.DianPingService;
import com.hmlyinfo.app.soutu.delicacy.service.RestaurantService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.Validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@Controller
@RequestMapping("/api/pub/delicacy")
public class DelicacyApi
{
	@Autowired
	private DelicacyService service;
	@Autowired
	private RestaurantService restaurantService;
	@Autowired
	private DianPingService dianService;
	
	@RequestMapping("/info")
	public @ResponseBody ActionResult info(long id)
	{
		return ActionResult.createSuccess(service.info(id));
	}
	
	/**
	 * 系统按条件列出美食列表
	 * <ul>
	 * 	<li>必选:城市id{cityId}</li>
	 *  <li>可选:当地人好评数{localNum}</li>
	 * 	<li>可选:游客好评数{touristNum}</li>
	 * 	<li>可选:美食名{foodName}</li>
	 * 	<li>可选:菜系{cuision}</li>
	 * 	<li>可选:口味{taste}</li>
	 * 	<li>可选:价格{price}</li>
	 *  <li>url:/api/pub/delicacy/list</li>
	 * </ul>
	 *  
	 * @return 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/list")
	public @ResponseBody ActionResult getDelicacy(final HttpServletRequest request) throws UnsupportedEncodingException {
		Validate.notNull(request.getParameter("cityId"), ErrorCode.ERROR_51001);
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		return ActionResult.createSuccess(service.getDelicacy(paramMap));
	}
	
	/**
	 * 系统按条件列出美食数量
	 * <ul>
	 * 	<li>必选:城市id{cityId}</li>
	 *  <li>可选:当地人好评数{localNum}</li>
	 * 	<li>可选:游客好评数{touristNum}</li>
	 * 	<li>可选:美食名{foodName}</li>
	 * 	<li>可选:菜系{create_time}</li>
	 * 	<li>可选:口味{create_time}</li>
	 * 	<li>可选:价格{create_time}</li>
	 *  <li>url:/api/pub/delicacy/count</li>
	 * </ul>
	 *  
	 * @return 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/count")
	public @ResponseBody ActionResult count(final HttpServletRequest request) throws UnsupportedEncodingException {
		Validate.notNull(request.getParameter("cityId"), ErrorCode.ERROR_51001);
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		return service.countAsResult(paramMap);
	}
	
	/**
	 * 系统通过美食id返回更多美食图片
	 * <ul>
	 * 	<li>必选:美食id{delicacyId}</li>
	 * </ul>
	 *  <li>url:/api/pub/delicacy/picturelist</li>
	 * @return 
	 */
	@RequestMapping("/picturelist")
	public @ResponseBody ActionResult getPicture(final HttpServletRequest request) {
		Validate.notNull(request.getParameter("delicacyId"), ErrorCode.ERROR_51001);
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		return ActionResult.createSuccess(service.getPicture(paramMap));
	}
	
	/**
	 * 系统通过美食查询餐厅
	 * <ul>
	 * 	<li>必选:美食id{delicacyId}</li>
	 * </ul>
	 *  <li>url:/api/pub/delicacy/restaurant/listbydelicacy</li>
	 * @return 
	 */
	@RequestMapping("/restaurant/listbydelicacy")
	public @ResponseBody ActionResult getRest(final HttpServletRequest request) {
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		return ActionResult.createSuccess(service.getRest(paramMap));
	}
	
	/**
	 * 美食分享数
	 * <ul>
	 * 	<li>必选:美食id{delicacyId}</li>
	 * </ul>
	 *  <li>url:/api/pub/delicacy/addshare</li>
	 * @return 
	 */
	@RequestMapping("/addshare")
	public @ResponseBody ActionResult addShareNum(final HttpServletRequest request) {
		Validate.notNull(request.getParameter("delicacyId"), ErrorCode.ERROR_51001);
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		service.addShareNum(paramMap);
		return ActionResult.createSuccess();
	}
	
	/**
	 * 通过餐厅ID查询餐厅信息
	 * <ul>
	 * 	<li>必选:餐厅id{id}</li>
	 * </ul>
	 * 	<li>url:/api/pub/delicacy/resinfo</li>
	 * @return 
	 */
	@RequestMapping("/resinfo")
	public @ResponseBody ActionResult resInfo(Long id) {
		return ActionResult.createSuccess(restaurantService.info(id));
	}

    @RequestMapping("/restaurant/list")
    @ResponseBody
    public ActionResult listByPosition(HttpServletRequest request) {
        Map<String, Object> params = HttpUtil.parsePageMap(request);
        return ActionResult.createSuccess(restaurantService.list(params));
    }

    @RequestMapping("/restaurant/count")
    @ResponseBody
    public ActionResult countByPosion(HttpServletRequest request) {
        Map<String, Object> params = HttpUtil.parsePageMap(request);
        return restaurantService.countAsResult(params);
    }
    
    
    @RequestMapping("/restaurant/test")
    @ResponseBody
    public ActionResult testComment(HttpServletRequest request) {
        Map<String, Object> params = HttpUtil.parsePageMap(request);
        return ActionResult.createSuccess(dianService.test());
    }
    
    @RequestMapping("/restaurant/insertshopid")
    @ResponseBody
    public void insertDianpingId() throws IOException {
    	dianService.updateShopId();
    }
}
