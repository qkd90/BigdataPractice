package com.data.data.hmly.action.wechat;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.data.data.hmly.service.wechat.WechatSupportAccountService;
import com.zuipin.util.JsonFilter;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang3.StringUtils;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.SysUnitService;
import com.data.data.hmly.service.wechat.WechatAccountService;
import com.data.data.hmly.service.wechat.WechatFollowerService;
import com.data.data.hmly.service.wechat.WechatService;
import com.data.data.hmly.service.wechat.entity.WechatAccount;
import com.data.data.hmly.service.wechat.entity.WechatFollower;
import com.data.data.hmly.service.wechat.entity.enums.NoticeType;
import com.framework.hibernate.util.Page;
import com.framework.struts.AjaxCheck;
import com.opensymphony.xwork2.Result;

/**
 * Created by vacuity on 15/11/20.
 */
public class WechatFollowerAction extends FrameBaseAction {
	private static final long serialVersionUID = -617072372295001263L;
	@Resource
    private WechatFollowerService followerService;
    @Resource
    private WechatSupportAccountService wechatSupportAccountService;
	@Resource
    private WechatAccountService accountService;
	@Resource
    private SysUnitService unitService;
	@Resource
    private WechatService wechatService;
	private Long accountId;
    private Map<String, Object> map = new HashMap<String, Object>();
    private Integer			page				= 1;
   	private Integer			rows				= 10;
    
	@AjaxCheck
    public Result followerList() {
		Boolean  validFlag = true;
		List<WechatAccount>  accountList = accountService.getAccountListAll(getCompanyUnit(), validFlag);
		if (accountList.size() > 0) {
			accountId = accountList.get(0).getId();
		}
		return dispatch();
	}
	
	@AjaxCheck
    public Result sysFollowerList() throws Exception {
		
		String accountid = (String) getParameter("accountid");
		List<WechatFollower> followers = null;
		if (!StringUtils.isEmpty(accountid)) {
			followers = wechatService.doGetFollower(Long.parseLong(accountid));
			simpleResult(map, true, "");
		} else {
			simpleResult(map, false, "");
		}
		
		return jsonResult(map);
	}
	
	
	/**
	 * 获取用户列表
	 * @return
	 */
	@AjaxCheck
    public Result followerDatagrid() {
		
		Page pageInfo = new Page(page, rows);
		
		String accountid = (String) getParameter("accountid");
		String nickname = (String) getParameter("nickname");
		String isSupportStr = (String) getParameter("isSupport");

		WechatFollower follower = new WechatFollower();
		List<WechatFollower> followers = new ArrayList<WechatFollower>();
		if (!StringUtils.isEmpty(accountid)) {
			WechatAccount account = accountService.load(Long.parseLong(accountid));
			follower.setFollowAccount(account);
			if (StringUtils.isNotBlank(nickname)) {
				follower.setNickName(nickname);
			}
			followers = followerService.findFollowersList(follower, isSupportStr, pageInfo);
		} else {
			followers = followerService.findFollowersList(follower, isSupportStr, pageInfo);
		}
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig("followAccount");
		return datagrid(followers, pageInfo.getTotalCount(), jsonConfig);
	}

    public Result setSupporter() {
        Map<String, Object> result;
        String openId = getRequest().getParameter("openId");
        String accountIdStr = getRequest().getParameter("accountId");
        result = wechatSupportAccountService.doSetSupporter(accountIdStr, openId);
        return json(JSONObject.fromObject(result));
    }

    public Result delSupporter() {
        Map<String, Object> result;
        String openId = getRequest().getParameter("openId");
        String accountIdStr = getRequest().getParameter("accountId");
        result = wechatSupportAccountService.doDelSupporter(accountIdStr, openId);
        return json(JSONObject.fromObject(result));
    }

	/**
	 * 发送模板消息，touser为openId：/wechat/wechat/sendTplMessage.jhtml?accountId=1&touser=ohzHvsq4HEfLEHVo9axpaxm_j2XU
	 * @author caiys
	 * @date 2015年12月7日 下午8:51:35
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@AjaxCheck
    public Result sendTplMessage() throws UnsupportedEncodingException {
		String accountId = (String) getParameter("accountId");
		String fansIds = (String) getParameter("fansIds");	// 支持多个，","逗号分隔
		String noticeType = (String) getParameter("informType");
		String content = (String) getParameter("infromContent");
		content = new String (content.getBytes("iso8859-1"), "UTF-8");
		String url = (String) getParameter("url");
		NoticeType type = null;
		if ("pay".equals(noticeType)) {
			type = NoticeType.pay;
		} else if ("deliver".equals(noticeType)) {
			type = NoticeType.deliver;
		} else if ("order".equals(noticeType)) {
			type = NoticeType.order;
		} else if ("notice".equals(noticeType)) {
			type = NoticeType.notice;
		}
		
		boolean success = false;
		try {
			if (!StringUtils.isEmpty(content)) {
				success = wechatService.doSendTplMessage(Long.valueOf(accountId), fansIds, type, content, url);
			}
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
	 * @throws UnsupportedEncodingException 
	 */
	@AjaxCheck
    public Result massSendallText() throws UnsupportedEncodingException {
		String accountId = (String) getParameter("accountId");
		String content = (String) getParameter("content");
		content = new String (content.getBytes("iso8859-1"), "UTF-8");
		
		content = content.replaceAll("<br />", "\r\n");
		content = content.replaceAll("\r\n\r\n", "\n");
		
		
		boolean success = false;
		try {
			success = wechatService.doMassSendallText(Long.valueOf(accountId), content);
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

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
}
