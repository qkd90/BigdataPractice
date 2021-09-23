package com.data.data.hmly.action.wechat;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.wechat.WechatService;
import com.data.data.hmly.service.wechat.entity.enums.NoticeType;
import com.framework.struts.AjaxCheck;
import com.opensymphony.xwork2.Result;

/**
 * Created by vacuity on 15/11/20.
 */
public class WechatAction extends FrameBaseAction {
	private static final long serialVersionUID = -617072372295001263L;
	@Resource
    private WechatService wechatService;
    private Map<String, Object> map = new HashMap<String, Object>();


	/**
	 * 微信菜单同步：/wechat/wechat/syncWechatMenu.jhtml?accountId=1
	 * 异步同步参见WechatService.doSyncWechatMenuThread
	 * @author caiys
	 * @date 2015年11月25日 上午11:09:53
	 * @return
	 */
	@AjaxCheck
    public Result syncWechatMenu() {
		String accountId = (String) getParameter("accountId");
		boolean success = false;
		try {
			success = wechatService.doSyncWechatMenu(Long.valueOf(accountId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		simpleResult(map, success, "");
		return jsonResult(map);
    }

	/**
	 * 创建带参数永久二维码：/wechat/wechat/createLimitQrcodeUrl.jhtml?accountId=1&sceneStr=cys
	 * @author caiys
	 * @date 2015年11月25日 上午11:09:53
	 * @return
	 */
	@AjaxCheck
    public Result createLimitQrcodeUrl() {
		String accountId = (String) getParameter("accountId");
		String sceneStr = (String) getParameter("sceneStr");
		boolean success = false;
		try {
			String ticketUrl = wechatService.doCreateLimitQrcodeUrl(Long.valueOf(accountId), sceneStr);
			success = true;
			map.put("ticketUrl", ticketUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		simpleResult(map, success, "");
		return jsonResult(map);
    }

	/**
	 * 发送模板消息，touser为openId：/wechat/wechat/sendTplMessage.jhtml?accountId=1&touser=ohzHvsq4HEfLEHVo9axpaxm_j2XU
	 * @author caiys
	 * @date 2015年12月7日 下午8:51:35
	 * @return
	 */
	@AjaxCheck
    public Result sendTplMessage() {
		String accountId = (String) getParameter("accountId");
		String touser = (String) getParameter("touser");	// 支持多个，","逗号分隔
		boolean success = false;
		try {
			success = wechatService.doSendTplMessage(Long.valueOf(accountId), touser, NoticeType.deliver, "你预订的商品已经发出，请留意查收！", "http://www.baidu.com");
		} catch (Exception e) {
			e.printStackTrace();
		}
		simpleResult(map, success, "");
		return jsonResult(map);
    }

	/**
	 * 群发文本消息：/wechat/wechat/massSendallText.jhtml?accountId=1
	 * @author caiys
	 * @date 2015年12月7日 下午8:51:35
	 * @return
	 */
	@AjaxCheck
    public Result massSendallText() {
		String accountId = (String) getParameter("accountId");
		boolean success = false;
		try {
			success = wechatService.doMassSendallText(Long.valueOf(accountId), "天气变冷，谨防感冒！");
		} catch (Exception e) {
			e.printStackTrace();
		}
		simpleResult(map, success, "");
		return jsonResult(map);
    }
	
	/**
	 * 从服务器获取用户列表：/wechat/wechat/getFollowerList.jhtml?accountId=1
	 * @author huangpeijie
	 * @date 2015年12月10日17:32:51
	 * @return
	 */
	@AjaxCheck
	public Result getFollowerList() {
		String accountId = (String) getParameter("accountId");
		boolean success = false;
		try {
			wechatService.doGetFollower(Long.valueOf(accountId));
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		simpleResult(map, success, "");
		return jsonResult(map);
	}

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}
