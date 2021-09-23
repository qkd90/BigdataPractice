package com.hmlyinfo.app.soutu.weixin.service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.hmlyinfo.app.soutu.base.properties.Config;
import com.hmlyinfo.app.soutu.weixin.domain.Graphic;
import com.hmlyinfo.app.soutu.weixin.domain.Reply;
import com.hmlyinfo.app.soutu.weixin.domain.ReplyItem;
import com.hmlyinfo.app.soutu.weixin.domain.ReplyText;
import com.hmlyinfo.app.soutu.weixin.domain.WxNew;
import com.hmlyinfo.app.soutu.weixin.domain.WxReplyLog;
import com.hmlyinfo.app.soutu.weixin.domain.WxReplyVoice;
import com.hmlyinfo.app.soutu.weixin.mapper.ReplyMapper;
import com.hmlyinfo.app.soutu.wxuser.WxUserService;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import com.hmlyinfo.base.util.HttpClientUtils;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.StringUtil;

@Service
public class ReplyService extends BaseService<Reply, Long> {

	@Autowired
	private ReplyMapper<Reply> mapper;

	@Autowired
	private WxUserService userService;
	@Autowired
    private GPSService gpsService;
	@Autowired
	private WeiXinService weixinService;
	@Autowired
	private ReplyItemService wxReplyItemService;
	@Autowired
	private ReplyTextService wxReplyTextService;
	@Autowired
	private WxReplyVoiceService wxReplyVoiceService;
	@Autowired
	private ReplyGraphicService replyGraphicService;
	@Autowired
	private WxReplyLogService wxReplyLogService;
	
	// 公众号名称
	private static final String APP_UserName = Config.get("WX_UserName");
	
	private static ObjectMapper om = new ObjectMapper();
	private static final Log logs = LogFactory.getLog(ReplyService.class);
	
	/**
	 * 根据公众号中的用户操作返回相应回复，并记录下日志
	 * @param request
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException
	 */
	public void reply(HttpServletRequest request, HttpServletResponse response) throws KeyManagementException, NoSuchAlgorithmException, ClientProtocolException, IOException 
	{
        Map<String, String> map = HttpUtil.parseXml(request);
        String event = map.get("Event");
        String msgType = map.get("MsgType");
        String openId = map.get("FromUserName");

        // 记录日志
        WxReplyLog wxReplyLog = new WxReplyLog();
        wxReplyLog.setOpenId(openId);
        if (!StringUtil.isEmpty(event)){
        	wxReplyLog.setEvent(event);
        }
        else {
        	// 用户发送文本时
        	wxReplyLog.setEvent(msgType);
		}
        wxReplyLog.setStatus("T");
        
        // 更新用户地理位置
        if ("LOCATION".equals(event))
        {
        	Map<String, Object> loactionMap = Maps.newHashMap();
    		String rsu = gpsService.gps2Gcj(Double.parseDouble((String) map.get("Latitude")), Double.parseDouble((String) map.get("Longitude")));
    		String lat = rsu.split(",")[0];
    		String lng = rsu.split(",")[1];
    		loactionMap.put("lat", lat);
    		loactionMap.put("lng", lng);
            userService.updateUserLocation(openId, loactionMap);
        }
        
        // 更新用户信息
        updateWxUser(openId);
        
        Map<String, Object> replyMap = Maps.newHashMap();
        // 事件需与用户发送事件区分，1表示接收普通消息（例如文本、图片、语言等）；2表示接收事件消息（例如关注、取消关注等）
        int replyMsgType = 0;
        String xmlStr = "";
        
        // 菜单点击事件
        if ("CLICK".equals(event))
        {
        	// 菜单事件使用eventKey
        	replyMsgType = 2;
        	replyMap = replyXml((String) map.get("EventKey"), replyMsgType, map);
        	xmlStr = (String) replyMap.get("xmlStr");
            wxReplyLog.setType((Long) replyMap.get("msgId"));
        }
        
        // 关注
        if ("subscribe".equals(event))
        {
        	replyMsgType = 2;
        	replyMap = replyXml("subscribe", replyMsgType, map);
        	xmlStr = (String) replyMap.get("xmlStr");
        	wxReplyLog.setType((Long) replyMap.get("msgId"));
        	
        	// 判断是否属于带参数的二维码关注事件，并在日志中记录参数值
            String eventKey = (String) map.get("EventKey");
            if (!StringUtil.isEmpty(eventKey) && eventKey.startsWith("qrscene_")) {
                eventKey = eventKey.substring(8);
            }
            wxReplyLog.setEventKey(eventKey);
        }
        
        // 取消关注
        if ("unsubscribe".equals(event))
        {
        	replyMsgType = 2;
        	replyMap = replyXml("unsubscribe", replyMsgType, map);
        	xmlStr = (String) replyMap.get("xmlStr");
        	wxReplyLog.setType((Long) replyMap.get("msgId"));
        }
        
        // 用户扫描二维码，并在日志中记录参数值
        if ("SCAN".equals(event))
        {
            String eventKey = (String) map.get("EventKey");
            if (!StringUtil.isEmpty(eventKey) ) {
                wxReplyLog.setEventKey(eventKey);
            }
        }
        
        // 用户发送文字
        if ("text".equals(msgType)) 
        {
        	replyMsgType = 1;
        	String keyword = (String) map.get("Content");
        	wxReplyLog.setContent(keyword);
        	replyMap = replyXml(keyword, replyMsgType, map);
        	xmlStr = (String) replyMap.get("xmlStr");
        	wxReplyLog.setType((Long) replyMap.get("msgId"));
        }

        wxReplyLogService.insert(wxReplyLog);
        
        // TODO:测试时使用
        System.out.println(xmlStr);
	}
	
	/**
	 * 处理回复内容
	 * @param keyword
	 * @param replyMsgType
	 * @param map
	 * @return
	 * @throws IOException
	 */
	private Map<String, Object> replyXml(String keyword, int replyMsgType, Map<String, String> map) throws IOException 
    {
    	Map<String, Object> resultMap = Maps.newHashMap();
    	String xmlStr = "";
    	long replyid = -1;
    	
    	String openId = (String) map.get("FromUserName");
    	// 关键字匹配查询
    	Map<String, Object> paramMap = Maps.newHashMap();
    	paramMap.put("keyword", keyword);
    	paramMap.put("msgType", replyMsgType);
    	
    	List<Reply> replyList = mapper.listByKeyword(paramMap);
    	
    	if (replyList.size() > 0)
    	{
    		if (replyList.size() > 1)
        	{
        		logs.error("微信匹配关键字-->'" + keyword + "'匹配到多条记录");
        	}
    		
    		Reply wxReply = replyList.get(0);
    		// 返回replyid用于存放日志使用
    		replyid = wxReply.getId();
    		
    		Map<String, Object> itemMap = Maps.newHashMap();
    		itemMap.put("wxReplyItemGroupId", wxReply.getWxReplyItemGroupId());
    		itemMap.put("orderColumn", "order_num");
    		itemMap.put("orderType", "desc");
    		List<ReplyItem> itemList = wxReplyItemService.list(itemMap);
    		
    		// 若有多条回复消息，需循环判断是否类型都属于图文类型
    		if (itemList.size() > 1)
    		{
    			List<Long> ids = new ArrayList<Long>();
    			for (ReplyItem wxReplyItem : itemList)
    			{
    				// 如果不属于图文消息，剔除并打印日志
    				if (!"2".equals(wxReplyItem.getMsgType()))
    				{
    					logs.error("微信匹配关键字-->'" + keyword + "'时出错，itemId为" + wxReplyItem.getId() + "的数据不属于图文消息");
    					continue;
    				}
    				ids.add(wxReplyItem.getMsgId());
    			}
    			
    			Map<String, Object> graphicMap = Maps.newHashMap();
    			graphicMap.put("ids", ids);
    			List<Graphic> graphicList = replyGraphicService.list(graphicMap);
    			
    			List<WxNew> wxList = new ArrayList<WxNew>();
    			for (Graphic wxReplyGraphic : graphicList)
    			{
    				WxNew news = new WxNew();
    				news.setTitle(wxReplyGraphic.getTitle());
    				news.setDescription(wxReplyGraphic.getDescription());
    				news.setUrl(wxReplyGraphic.getUrl());
    				news.setPicUrl(wxReplyGraphic.getImagePath());
    				wxList.add(news);
    			}
    			
    			xmlStr = encodeWxXml(wxList, openId);
    		}
    		else if (itemList.size() == 1)
    		{
    			ReplyItem wxReplyItem = itemList.get(0);
    			String msgtype = wxReplyItem.getMsgType();
    			long msgid = Long.valueOf(wxReplyItem.getMsgId());
    			
    			// 纯文本
    			if ("1".equals(msgtype))
    			{
    				ReplyText wxReplyText = wxReplyTextService.info(msgid);
    				xmlStr = encodeWxTextXml(wxReplyText.getContent(), openId);
    			}
    			// 单图文
    			else if ("2".equals(msgtype))
    			{
        			Graphic graphic = replyGraphicService.info(msgid);
    				WxNew wxnew = new WxNew();
    				wxnew.setTitle(graphic.getTitle());
    				wxnew.setDescription(graphic.getDescription());
    				wxnew.setUrl(graphic.getUrl());
    				wxnew.setPicUrl(graphic.getImagePath());
    				List<WxNew> news = new ArrayList<WxNew>();
    				news.add(wxnew);
    				xmlStr = encodeWxXml(news, openId);
    			}
    			// 语音
    			else if ("3".equals(msgtype))
    			{
    				WxReplyVoice wxReplyVoice = wxReplyVoiceService.info(msgid);
    				xmlStr = encodeWxVoiceXml(wxReplyVoice.getMediaId(), openId);
    			}
    			// TODO:地理位置
    			// TODO:微信卡劵
    		}
    	}
    	// 没有匹配的回复时，转发到多客服
    	else
    	{
    		// TODO:多客服有多种处理方式
    		String serviceTpl = StringUtil.getStrFromFile(getClass().getResourceAsStream("wxNewsService.tpl"));
    		xmlStr = serviceTpl.replaceAll("#toUser", (String) map.get("FromUserName")).
        			replaceAll("#WxName", APP_UserName).
                    replaceAll("#createTime", new Date().getTime() + "");
    	}
    	
    	resultMap.put("msgId", replyid);
    	resultMap.put("xmlStr", xmlStr);
    	return resultMap;
	}
    
	/**
	 * 回复图文消息
	 * @param list
	 * @param openId
	 * @return
	 */
    private String encodeWxXml(List<WxNew> list, String openId)
    {
        String bodyTpl = StringUtil.getStrFromFile(getClass().getResourceAsStream("wxNewsBody.tpl"));
        String itemTpl = StringUtil.getStrFromFile(getClass().getResourceAsStream("wxNewsItem.tpl"));

        StringBuffer bodySb = new StringBuffer();
        for (WxNew news : list)
        {
            bodySb.append(itemTpl.replaceAll("#title", news.getTitle())
                    .replaceAll("#description", news.getDescription())
                    .replaceAll("#picUrl", news.getPicUrl())
                    .replaceAll("#url", news.getUrl()));
        }
        String resXml = bodyTpl.replaceAll("#toUser", openId).
        		replaceAll("#WxName", APP_UserName).
                replaceAll("#createTime", new Date().getTime() + "").
                replaceAll("#count", list.size() + "").
                replaceAll("#items", bodySb.toString());

        return resXml;
    }

    /**
     * 回复文本消息
     * @param content
     * @param openId
     * @return
     */
    private String encodeWxTextXml(String content, String openId)
    {
        String textTpl = StringUtil.getStrFromFile(getClass().getResourceAsStream("wxNewsText.tpl"));
        String resXml = textTpl.replaceAll("#toUser", openId).
        		replaceAll("#WxName", APP_UserName).
                replaceAll("#createTime", new Date().getTime() + "").
                replaceAll("#content", content);

        return resXml;
    }
    
    /**
     * 回复语音消息
     * @param mediaId
     * @param openId
     * @return
     */
    private String encodeWxVoiceXml(String mediaId, String openId)
    {
        String textTpl = StringUtil.getStrFromFile(getClass().getResourceAsStream("wxNewsVoice.tpl"));
        String resXml = textTpl.replaceAll("#toUser", openId).
        		replaceAll("#WxName", APP_UserName).
                replaceAll("#createTime", new Date().getTime() + "").
                replaceAll("#mediaId", mediaId);

        return resXml;
    }
    
	/**
	 * 更新用户信息，返回用户信息给调用者，调用者判断是否为新增用户或者更新用户信息
	 * @param openId
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
    public void updateWxUser(String openId) throws KeyManagementException, NoSuchAlgorithmException, ClientProtocolException, IOException
    {
        String accessToken = weixinService.getToken().getAccessToken();
        Map<String, Object> bindInfoMap = Maps.newHashMap();

        // 使用access_token和openid获取用户信息
        String userUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + accessToken + "&openid=" + openId + "&lang=zh_CN";
        String userStr = "";
        userStr = userStr + HttpClientUtils.getHttps(userUrl);
        logs.info("微信返回的用户信息是:" + userStr);
        
        Map<String, Object> userMap = Maps.newHashMap();
        userMap = om.readValue(userStr, Map.class);

        bindInfoMap.put("openId", userMap.get("openid"));
        bindInfoMap.put("bindType", "3");
        bindInfoMap.put("userNickName", userMap.get("nickname"));
        bindInfoMap.put("userFacePath", userMap.get("headimgurl"));
        bindInfoMap.put("subscribe", userMap.get("subscribe"));
        String sexstr = "";
        try
        {
            int sex = (Integer)userMap.get("sex");
            if (sex == 1)
            {
                sexstr = "man";
            }
            else if (sex == 2)
            {
                sexstr = "woman";
            }
        }
        catch (Exception e)
        {
            logs.warn("获取用户性别失败，原因是:" + e.getLocalizedMessage());
        }
        bindInfoMap.put("sexstr", sexstr);

        userService.updateFollower(bindInfoMap);
    }
	
	@Override
	public BaseMapper<Reply> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}
	
}
