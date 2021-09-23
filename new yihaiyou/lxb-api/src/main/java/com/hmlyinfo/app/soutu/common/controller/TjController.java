package com.hmlyinfo.app.soutu.common.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hmlyinfo.app.soutu.browse.domain.Browse;
import com.hmlyinfo.app.soutu.browse.service.BrowseService;

@Controller
@RequestMapping("/tj")
public class TjController 
{
	@Autowired
	private BrowseService bs;
	
	@RequestMapping("v1")
	public @ResponseBody void tjv1(HttpServletRequest request, String addr, String uid)
	{
		String ip = request.getHeader("x-real-ip");
		String browserType = request.getHeader("User-Agent");
		if (StringUtils.isNotBlank(browserType) && browserType.length() > 50)
		{
			browserType = browserType.substring(0, 50);
		}
		
		Browse b = new Browse();
		b.setIp(ip);
		b.setUserId(uid);
		b.setBrowserType(browserType);
		b.setUrl(addr);
		b.setCreateTime(new Date());
		
		bs.insert(b);
		
	}
}
