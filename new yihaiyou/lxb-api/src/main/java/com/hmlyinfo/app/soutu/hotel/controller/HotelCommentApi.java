package com.hmlyinfo.app.soutu.hotel.controller;

import java.util.Map;

import com.hmlyinfo.app.soutu.account.service.UserService;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.hotel.service.HotelCommentService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.Validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/api/auth/hotel")
public class HotelCommentApi {
    @Autowired
    HotelCommentService service;

    @Autowired
    UserService userService;

    /**
     * 插入酒店评论
     * <ul>
     * <li>必填：酒店Id</li>
     * <li>url:/api/auth/hotel/insert</li>
     * </ul>
     */
    @RequestMapping("/insert")
    public @ResponseBody ActionResult insertComment(final HttpServletRequest request)
    {
    	//验证传入参数是否为空
		String hotelId = request.getParameter("hotelId");
		String content = request.getParameter("content");
		Validate.notNull(hotelId, ErrorCode.ERROR_51001);
		Validate.notNull(content, ErrorCode.ERROR_51001);
		
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		
		return ActionResult.createSuccess(service.addNewComment(paramMap));
    }
    
}

