package com.hmlyinfo.app.soutu.weixin.service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.hmlyinfo.app.soutu.account.domain.User;
import com.hmlyinfo.app.soutu.weixin.domain.WxReplyLog;
import com.hmlyinfo.app.soutu.weixin.mapper.WxReplyLogMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import com.hmlyinfo.base.util.StringUtil;

@Service
public class WxReplyLogService extends BaseService<WxReplyLog, Long>{

	@Autowired
	private WxReplyLogMapper<WxReplyLog> mapper;

	@Override
	public BaseMapper<WxReplyLog> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

	/**
	 * 记录微信用户操作日志
	 * @param map，输入项为用户操作的xml
	 */
	public void addLog(Map<String, Object> map) 
	{
        String event = (String) map.get("Event");
        String msgType = (String) map.get("MsgType");
        String openId = (String) map.get("FromUserName");

        WxReplyLog wxReplyLog = new WxReplyLog();
        wxReplyLog.setOpenId(openId);
        if (!StringUtil.isEmpty(event))
        {
        	wxReplyLog.setEvent(event);
        }
        else 
        {
        	// 用户发送文本时，记录event为text
        	wxReplyLog.setEvent(msgType);
		}
        
        if ("subscribe".equals(event))
        {
        	// 如果是属于二维码扫描关注，则记录二维码后面的参数
            String eventKey = (String) map.get("EventKey");
            if (!StringUtil.isEmpty(eventKey) && eventKey.startsWith("qrscene_")) 
            {
                eventKey = eventKey.substring(8);
            }
            wxReplyLog.setEventKey(eventKey);
        }
       
        // 如果是用户发送文本事件，则记录文本内容
        String content = (String) map.get("Content");
        if (!StringUtil.isEmpty(content)) 
        {
            wxReplyLog.setContent(content);
        }

        insert(wxReplyLog);
	}
}
