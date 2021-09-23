package com.hmlyinfo.app.soutu.plan.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.plan.domain.UrbanTraffic;
import com.hmlyinfo.app.soutu.plan.service.PlanUrbanTrafficService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.Validate;

@Controller
@RequestMapping("/api/pub/planUrbanTraffic")
public class PlanUrbanTrafficApi 
{
	@Autowired
	private PlanUrbanTrafficService service;
	private ObjectMapper om = new ObjectMapper();
	
	/**
	 * 查询当天所有行程节点信息
	 * <ul>
	 * 	<li>必选:行程id{planId}</li>
	 * 	<li>url:/api/plan/pub/planUrbanTraffic/getPlanUrbanTraffic</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/getPlanUrbanTraffic")
	public @ResponseBody ActionResult getPlanUrbanTraffic(String planId)
	{
		Validate.notNull(planId, ErrorCode.ERROR_51001);
		
		return ActionResult.createSuccess(service.getPlanUrbanTraffic(Long.valueOf(planId)));
	}
	
	/**
	 * 保存行程景点交通
	 * <ul>
	 * 	<li>必选:行程id{planId}</li>
	 *  <li>必选:jsonData{jsonData}</li>
	 *  <li>必选:第若干天{days}</li>
	 * 	<li>url:/api/plan/pub/planUrbanTraffic/saveScenicTraffic</li>
	 * </ul>
	 *
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@RequestMapping("/saveScenicTraffic")
	public @ResponseBody ActionResult saveScenicTraffic(String jsonData, String planId) throws JsonParseException, JsonMappingException, IOException
	{
		List<UrbanTraffic> trafficList = jsonToUrbanTraffic(jsonData);
		service.saveScenicTraffic(trafficList, Long.valueOf(planId));
		
		return ActionResult.createSuccess();
	}
	
	
	/**
	 * 保存行程酒店交通
	 * <ul>
	 * 	<li>必选:行程id{planId}</li>
	 *  <li>必选:jsonData{jsonData}</li>
	 * 	<li>url:/api/plan/pub/planUrbanTraffic/saveHotelTraffic</li>
	 * </ul>
	 *
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@RequestMapping("/saveHotelTraffic")
	public @ResponseBody ActionResult saveHotelTraffic(String jsonData, String planId) throws JsonParseException, JsonMappingException, IOException
	{
		List<UrbanTraffic> trafficList = jsonToUrbanTraffic(jsonData);
		service.saveHotelTraffic(trafficList, Long.valueOf(planId));
		
		return ActionResult.createSuccess();
	}
	
	private List<UrbanTraffic> jsonToUrbanTraffic(String jsonData) throws JsonParseException, JsonMappingException, IOException
	{
		Validate.notNull(jsonData, ErrorCode.ERROR_51001);
		
		JavaType type =  om.getTypeFactory().constructParametricType(List.class, UrbanTraffic.class);
		
		List<UrbanTraffic> trafficList = om.readValue(jsonData, type);
		
		return trafficList;
	}

}
