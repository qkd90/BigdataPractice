package com.hmlyinfo.app.soutu.scenicTicket.controller;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.hmlyinfo.app.soutu.scenicTicket.service.WeiXinService;

@Controller
@RequestMapping("/api/pub/weixin")
public class WeiXinApi {

    Logger logger = Logger.getLogger(WeiXinApi.class);

    @Autowired
    private WeiXinService service;
    
    // 微信推送
 	@RequestMapping("/pushtem")
    @ResponseBody
 	public String push() throws KeyManagementException, NoSuchAlgorithmException, ClientProtocolException, IOException
 	{
 		String temId = "URbKCD64Nr3Ger3ER_Gvv8OTHbZBb3lA-vWlFwKH6Ao";
 		String url = "http://m.lvxbang.com";
		String[][] infoStrings = {{"first", "恭喜你购买成功！","#173177"}, {"type", "旅行社", "#173177"}, {"name", "门票购买", "#173177"}, {"productType", "门票购买", "#173177"},
						{"serviceName", "马尔代夫7天双人游", "#173177"}, {"time", "2013年9月30日 15:39", "#173177"}, {"remark", "如有疑问，请资讯13745456787。", "#173177"}};
 		
		Map<String, Map<String, Object>> paramMap = Maps.newHashMap();
		for(String[] info : infoStrings)
		{
			Map<String, Object> infoMap = Maps.newHashMap();
			infoMap.put("value", info[1]);
			paramMap.put(info[0], infoMap);
		}
 		
        return service.wxPushTemplate(temId, "test", url, paramMap);
 	}
 	
}
