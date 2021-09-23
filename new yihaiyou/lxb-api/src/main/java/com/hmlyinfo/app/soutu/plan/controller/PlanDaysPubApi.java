package com.hmlyinfo.app.soutu.plan.controller;

import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.plan.service.OptimizeService;
import com.hmlyinfo.app.soutu.plan.service.PlanDaysService;
import com.hmlyinfo.app.soutu.plan.service.PlanTripService;
import com.hmlyinfo.app.soutu.scenic.service.CityService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.Validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by guoshijie on 2014/9/18.
 */
@Controller
@RequestMapping("/api/pub/plan/days")
public class PlanDaysPubApi {

    @Autowired
    PlanDaysService planDaysService;
    @Autowired
    PlanTripService tripService;
    @Autowired
    OptimizeService optimizeService;
    @Autowired
    private CityService cityService;

    
    /**
	 * 查询行程天
	 * <ul>
	 * 	<li>必选:{planId}</li>
	 * 	<li>url:/api/pub/plan/days/list</li>
	 * </ul>
	 *
	 * @return
	 */
    @RequestMapping("/list")
    public @ResponseBody ActionResult list(String planId)
	{
		Validate.notNull(planId, ErrorCode.ERROR_51001);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("planId", planId);
		
		return ActionResult.createSuccess(planDaysService.list(paramMap));
	}

    @SuppressWarnings("unchecked")
	@RequestMapping("optimize")
    @ResponseBody
    public ActionResult optimizeAll(HttpServletRequest request) throws Exception {
    	
        return ActionResult.createSuccess(planDaysService.newOptimize(request));
    }
}
