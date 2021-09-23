package com.hmlyinfo.app.soutu.scenic.controller;

import com.hmlyinfo.app.soutu.scenic.service.CityService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by guoshijie on 2014/7/23.
 */
@Controller
@RequestMapping("/api/pub/city")
public class CityApi {

    @Autowired
    CityService cityService;

    @RequestMapping("list")
    @ResponseBody
    public ActionResult list(HttpServletRequest request) {
        Map<String, Object> params = HttpUtil.parsePageMap(request);
        return ActionResult.createSuccess(cityService.list(params));
    }

    @RequestMapping("info")
    @ResponseBody
    public ActionResult info(HttpServletRequest request) {
        return ActionResult.createSuccess(cityService.info(Long.valueOf(request.getParameter("id"))));
    }
    
    /**
     * 城市级别是1的城市
     * @param request
     * @return
     */
    @RequestMapping("listcity")
    @ResponseBody
    public ActionResult listLevel(HttpServletRequest request) {
    	Map<String, Object> params = HttpUtil.parsePageMap(request);
    	params.put("level", 1);
    	return ActionResult.createSuccess(cityService.list(params));
    }
    
}
