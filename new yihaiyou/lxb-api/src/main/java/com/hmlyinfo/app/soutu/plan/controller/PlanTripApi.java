package com.hmlyinfo.app.soutu.plan.controller;

import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.plan.domain.Plan;
import com.hmlyinfo.app.soutu.plan.domain.PlanTrip;
import com.hmlyinfo.app.soutu.plan.service.PlanOperationService;
import com.hmlyinfo.app.soutu.plan.service.PlanTripService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.Validate;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/auth/plan/trip")
public class PlanTripApi
{
	@Autowired
	private PlanTripService service;
	@Autowired
	private PlanOperationService planOperationService;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	
	/**
	 * 修改行程站（增、删、改）
	 * <ul>
	 * 	<li>必选:当天行程景点信息{planData}<li>
	 *  <li>url:/api/plan/trip/updateTrip</li>
	 * </ul>
	 *
	 * @return
	 */
	
	@RequestMapping("/updateTrip")
	public @ResponseBody ActionResult update(final HttpServletRequest request)
	{
		Validate.notNull(request.getParameter("planData"), ErrorCode.ERROR_51001);
		String jsonStr = request.getParameter("planData");
		
		Map<String, Object> paramMap = null;
		try {
			paramMap = objectMapper.readValue(jsonStr, Map.class);
		} catch (JsonParseException e) {
			Validate.notNull(null, ErrorCode.ERROR_51001);
		} catch (JsonMappingException e) {
			Validate.notNull(null, ErrorCode.ERROR_51001);
		} catch (IOException e) {
			Validate.notNull(null, ErrorCode.ERROR_51001);
		}
		
		Plan plan = planOperationService.updatePlanTrip(paramMap);
		return ActionResult.createSuccess(plan);
	}
	
	
	/**
	 * 删除某一天行程站(暂时不用)
	 * <ul>
	 * 	<li>必选:标识{id}<li>
	 *  <li>url:/api/plan/trip/del</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/del")
	public @ResponseBody ActionResult delDayTrips(final HttpServletRequest request, PlanTrip plantrip)
	{
		Validate.notNull(plantrip.getId(), ErrorCode.ERROR_51001); //非空验证
		plantrip = service.info(plantrip.getId()); //根据ID取出所有数据
		Map<String, Object> resultMap = new HashMap<String, Object>(); //定义MAP
		resultMap.put("planDaysId", plantrip.getPlanDaysId());  //把取到的数据放进MAP里
		List<PlanTrip> planList = service.list(resultMap);  //服务端取出的书库放到LIST里
		for(int i =0;i<planList.size();i++){  //循环遍历
			if(planList.get(i).getOrderNum()>plantrip.getOrderNum()){
				planList.get(i).setOrderNum(planList.get(i).getOrderNum() - 1);  //删除后所有后面的顺序号都要减1
				service.update(planList.get(i));  //更新到数据库
				}
		}
		service.del(plantrip.getId().toString());  // 删除这条数据
		
		return ActionResult.createSuccess(plantrip);
	}

    /**
     * 获取单个行程站的信息
     * <ul>
     *     <li>必选：id 行程站id</li>
     * </ul>
     * return ActionResult
     */
    @RequestMapping("/info")
    @ResponseBody
    public ActionResult getTripRemark(final HttpServletRequest request)
    {
        Validate.notNull(request.getParameter("id"), ErrorCode.ERROR_51001);
        return ActionResult.createSuccess(service.info(Long.valueOf(request.getParameter("id"))));
    }
	/**
	 * 修改某一天行程站备注
	 * <ul>
	 * 	<li>必选:标识{id}<li>
	 *  <li>url:/api/auth/plan/trip/updateremark</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/updateremark")
	public @ResponseBody ActionResult updateTripRemark(final HttpServletRequest request, PlanTrip plantrip)
	{
		Validate.notNull(plantrip.getTripDesc(), ErrorCode.ERROR_51001);  
		Validate.notNull(plantrip.getId(), ErrorCode.ERROR_51001);  
		
		return ActionResult.createSuccess(service.updateRemark(plantrip));
	}
	/**
	 * 修改某一天行程内行程站顺序(暂时不用)
	 * <ul>
	 * 	<li>必选:标识{id}<li>
	 *  <li>url:/api/plan/trip/update</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/update")
	public @ResponseBody ActionResult updateTripStation(final HttpServletRequest request)
	{
		Validate.notNull(request.getParameter("orderNum"), ErrorCode.ERROR_51001);  //非空验证
		String[] orderNum = request.getParameter("orderNum").toString().split(",");  //定义数组
		for(int i =0;i<orderNum.length;i++){  //遍历数组
			PlanTrip plantrip = service.info(Long.valueOf(orderNum[i]));  //根据顺序号取到的值放到Plantrip里
			plantrip.setOrderNum(i+1);  //数组开始为0所以要加1
			service.update(plantrip);  //更新到数据库
		}
		
		return ActionResult.createSuccess(true);
	}
	/**
	 * 修改某一天行程到另一天行程站顺序(暂时不用)
	 * <ul>
	 * 	<li>必选:标识{id}<li>
	 *  <li>url:/api/plan/trip/batchupdate</li>
	 * </ul>
	 *
	 * @return
	 */
	
	@RequestMapping("/batchupdate")
	public @ResponseBody ActionResult updateTripOrder(final HttpServletRequest request)
	{
		Validate.notNull(request.getParameter("planTripId"), ErrorCode.ERROR_50001);  //非空验证
		Validate.notNull(request.getParameter("planDaysId"), ErrorCode.ERROR_50001);  //非空验证
		//从服务端取出数据并且类型转换
		PlanTrip plantrip  = service.info(Long.valueOf(request.getParameter("planTripId").toString()));  
		//定义MAP
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//把数据放到MAP里
		resultMap.put("planDaysId", plantrip.getPlanDaysId());
		//定义LIST并且把数据放到LIST里
		List<PlanTrip> planList = service.list(resultMap);
		for(int i =0;i<planList.size();i++){  //遍历循环
			if(planList.get(i).getOrderNum()>plantrip.getOrderNum()){
				planList.get(i).setOrderNum(planList.get(i).getOrderNum() - 1);
				service.update(planList.get(i));  //更新数据
			}
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("PlanDaysId", request.getParameter("planDaysId"));
		int  order = service.count(paramMap)+1;
		plantrip.setOrderNum(order);
		plantrip.setPlanDaysId(Long.valueOf(request.getParameter("planDaysId").toString()));
		service.update(plantrip); //更新数据
		
		return ActionResult.createSuccess(plantrip);
	}
	
	
	
}
