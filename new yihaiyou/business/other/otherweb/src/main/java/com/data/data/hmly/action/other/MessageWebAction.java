package com.data.data.hmly.action.other;

import com.data.data.hmly.action.user.UserConstans;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.other.OtherMessageService;
import com.data.data.hmly.service.other.entity.OtherMessage;
import com.data.data.hmly.service.other.enums.MsgType;
import com.framework.hibernate.util.Page;
import com.framework.struts.AjaxCheck;
import com.opensymphony.xwork2.Result;
import com.zuipin.action.JxmallAction;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 由于页面路径在lvxbang目录下此类移至lvxbang包下
 * @author caiys
 * @date 2016年1月3日 下午4:28:44
 * 参考com.data.data.hmly.action.lvxbang.MessageWebAction
 */
@Deprecated
public class MessageWebAction extends JxmallAction {
	private static final long serialVersionUID = 900341852078393539L;
	@Resource
	private OtherMessageService otherMessageService;
	
	private Integer page = 1;
	private Integer rows = 10;

	// 返回数据
	Map<String, Object> map = new HashMap<String, Object>();

	/**
	 * 分页显示消息
	 * @author caiys
	 * @date 2015年12月22日 下午5:34:10
	 * @return
	 */
	@AjaxCheck
	public Result page() {
		// 请求参数
		String msgType = (String) getParameter("msgType");
		String title = (String) getParameter("title");
		
		OtherMessage om = new OtherMessage();
		if (StringUtils.isNotBlank(msgType)) {
			om.setMsgType(MsgType.valueOf(msgType));
		}
		om.setTitle(title);
		om.setDeleteFlag(false);
		Member user = (Member) getSessionAttribute(UserConstans.CURRENT_LOGIN_USER);
		if (user != null) {
			om.setToUser(user);
			Page pageInfo = new Page(page, rows);
			otherMessageService.findOtherMessageList(om, pageInfo);
			JSONObject json = JSONObject.fromObject(pageInfo);
			return json(json);
		}
		// 用户未登录
		return null;
	}

	/**
	 * 批量清除消息
	 * @author caiys
	 * @date 2015年12月22日 下午5:55:13
	 * @return
	 */
	@AjaxCheck
	public Result batchClearMessage() {
		// 请求参数
		String msgTypeStr = (String) getParameter("msgType");
		
		MsgType msgType = null;
		if (StringUtils.isNotBlank(msgTypeStr)) {
			msgType = MsgType.valueOf(msgTypeStr);
		}
		User user = (User) getSessionAttribute(UserConstans.CURRENT_LOGIN_USER);
		Long userId = null;
		if (user != null) {
			userId = user.getId();
			otherMessageService.doClearMessageBy(msgType, userId);
			map.put("success", true);
			map.put("errorMsg", "");
			JSONObject json = JSONObject.fromObject(map);
			return json(json);
		} 
		map.put("success", false);
		map.put("errorMsg", "用户未登录");
		JSONObject json = JSONObject.fromObject(map);
		return json(json);
	}
	
	/**
	 * 根据标识清除消息
	 * @author caiys
	 * @date 2015年12月22日 下午5:55:13
	 * @return
	 */
	@AjaxCheck
	public Result clearMessage() {
		// 请求参数
		String ids = (String) getParameter("ids");
		
		User user = (User) getSessionAttribute(UserConstans.CURRENT_LOGIN_USER);
		if (user != null) {
			otherMessageService.doClearMessageBy(ids, user.getId());
			map.put("success", true);
			map.put("errorMsg", "");
			JSONObject json = JSONObject.fromObject(map);
			return json(json);
		} 
		map.put("success", false);
		map.put("errorMsg", "用户未登录");
		JSONObject json = JSONObject.fromObject(map);
		return json(json);
	}
	
	/**
	 * 根据标识批量设置消息已读
	 * @author caiys
	 * @date 2015年12月22日 下午5:55:13
	 * @return
	 */
	@AjaxCheck
	public Result batchReadMessage() {
		// 请求参数
		String ids = (String) getParameter("ids");
		
		User user = (User) getSessionAttribute(UserConstans.CURRENT_LOGIN_USER);
		if (user != null) {
			otherMessageService.doBatchSetMessage(ids, true, user.getId());
			map.put("success", true);
			map.put("errorMsg", "");
			JSONObject json = JSONObject.fromObject(map);
			return json(json);
		} 
		map.put("success", false);
		map.put("errorMsg", "用户未登录");
		JSONObject json = JSONObject.fromObject(map);
		return json(json);
	}
	
	/**
	 * 根据标识批量设置消息未读
	 * @author caiys
	 * @date 2015年12月22日 下午5:55:13
	 * @return
	 */
	@AjaxCheck
	public Result batchUnReadMessage() {
		// 请求参数
		String ids = (String) getParameter("ids");
		
		User user = (User) getSessionAttribute(UserConstans.CURRENT_LOGIN_USER);
		if (user != null) {
			otherMessageService.doBatchSetMessage(ids, false, user.getId());
			map.put("success", true);
			map.put("errorMsg", "");
			JSONObject json = JSONObject.fromObject(map);
			return json(json);
		} 
		map.put("success", false);
		map.put("errorMsg", "用户未登录");
		JSONObject json = JSONObject.fromObject(map);
		return json(json);
	}

	/**
	 * 读取单条消息
	 * @author caiys
	 * @date 2015年12月24日 下午6:28:53
	 * @return
	 */
	@AjaxCheck
	public Result readMessage() {
		// 请求参数
		String id = (String) getParameter("id");
		
		User user = (User) getSessionAttribute(UserConstans.CURRENT_LOGIN_USER);
		if (user != null) {
			otherMessageService.doReadMessage(Long.valueOf(id), user.getId());
			map.put("success", true);
			map.put("errorMsg", "");
			JSONObject json = JSONObject.fromObject(map);
			return json(json);
		} 
		map.put("success", false);
		map.put("errorMsg", "用户未登录");
		JSONObject json = JSONObject.fromObject(map);
		return json(json);
	}
	
}
