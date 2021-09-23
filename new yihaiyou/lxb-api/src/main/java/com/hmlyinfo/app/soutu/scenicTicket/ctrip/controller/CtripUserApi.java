package com.hmlyinfo.app.soutu.scenicTicket.ctrip.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hmlyinfo.app.soutu.scenicTicket.ctrip.domain.CtripUser;
import com.hmlyinfo.app.soutu.scenicTicket.ctrip.mapper.CtripUserMapper;
import com.hmlyinfo.app.soutu.scenicTicket.ctrip.service.CtripUserService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.HttpUtil;


@Controller
@RequestMapping("/api/pub/ctripuser")
public class CtripUserApi {
	
	@Autowired
	private CtripUserService service;
	@Autowired
	private CtripUserMapper<CtripUser> mapper;
	
	@RequestMapping("/downloaduid")
	public @ResponseBody ActionResult downloadUid()
	{
		service.getCtripUniqueUid();
		return ActionResult.createSuccess();
	}

}
