package com.data.data.hmly.action.other;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.data.data.hmly.service.common.entity.enums.ProductType;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;

import com.data.data.hmly.action.user.UserConstans;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.other.OtherFavoriteService;
import com.data.data.hmly.service.other.entity.OtherFavorite;
import com.framework.hibernate.util.Page;
import com.framework.struts.AjaxCheck;
import com.opensymphony.xwork2.Result;
import com.zuipin.action.JxmallAction;

/**
 * 由于页面路径在lvxbang目录下此类移至lvxbang包下
 * @author caiys
 * @date 2016年1月3日 下午4:28:44
 * 参考com.data.data.hmly.action.lvxbang.FavoriteWebAction
 */
@Deprecated
public class FavoriteWebAction extends JxmallAction {
	private static final long serialVersionUID = 900341852078393539L;
	@Resource
	private OtherFavoriteService otherFavoriteService;
	private OtherFavorite otherFavorite;
	
	private Integer page = 1;
	private Integer rows = 10;

	// 返回数据
	Map<String, Object> map = new HashMap<String, Object>();

	
	/**
	 * 添加收藏
	 * @author caiys
	 * @date 2015年12月24日 下午5:49:10
	 * @return
	 */
	@AjaxCheck
	public Result addFavorite() {
		User user = (User) getSessionAttribute(UserConstans.CURRENT_LOGIN_USER);
		if (user != null) {
			// 检查是否已经收藏
			boolean exists = otherFavoriteService.checkExists(otherFavorite.getFavoriteType(), otherFavorite.getFavoriteId(), user.getId());
			if (exists) {
				map.put("success", false);
				map.put("errorMsg", "你已经收藏过该商品");
				JSONObject json = JSONObject.fromObject(map);
				return json(json);
			}
			otherFavorite.setUserId(user.getId());
			otherFavorite.setCreateTime(new Date());
			otherFavorite.setDeleteFlag(false);
			otherFavoriteService.doAddOtherFavorite(otherFavorite);
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
	 * 分页显示收藏夹
	 * @author caiys
	 * @date 2015年12月22日 下午5:34:10
	 * @return
	 */
	@AjaxCheck
	public Result page() {
		// 请求参数
		String favoriteType = (String) getParameter("favoriteType");
		String title = (String) getParameter("title");
		
		OtherFavorite of = new OtherFavorite();
		if (StringUtils.isNotBlank(favoriteType)) {
			of.setFavoriteType(ProductType.valueOf(favoriteType));
		}
		of.setTitle(title);
		of.setDeleteFlag(false);
		User user = (User) getSessionAttribute(UserConstans.CURRENT_LOGIN_USER);
		if (user != null) {
			of.setUserId(user.getId());
			Page pageInfo = new Page(page, rows);
			otherFavoriteService.findOtherFavoriteList(of, pageInfo);
			JSONObject json = JSONObject.fromObject(pageInfo);
			return json(json);
		}
		// 用户未登录
		return null;
	}

	/**
	 * 批量清除收藏夹
	 * @author caiys
	 * @date 2015年12月22日 下午5:55:13
	 * @return
	 */
	@AjaxCheck
	public Result batchClearFavorite() {
		// 请求参数
		String favoriteTypeStr = (String) getParameter("favoriteType");
		
		ProductType favoriteType = null;
		if (StringUtils.isNotBlank(favoriteTypeStr)) {
			favoriteType = ProductType.valueOf(favoriteTypeStr);
		}
		User user = (User) getSessionAttribute(UserConstans.CURRENT_LOGIN_USER);
		Long userId = null;
		if (user != null) {
			userId = user.getId();
			otherFavoriteService.doClearFavoriteBy(favoriteType, userId, null);
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
	 * 根据标识清除收藏夹
	 * @author caiys
	 * @date 2015年12月22日 下午5:55:13
	 * @return
	 */
	@AjaxCheck
	public Result clearFavorite() {
		// 请求参数
		String ids = (String) getParameter("ids");
		
		User user = (User) getSessionAttribute(UserConstans.CURRENT_LOGIN_USER);
		if (user != null) {
			otherFavoriteService.doClearFavoriteBy(ids, user.getId());
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
	 * 检查是否已经收藏过
	 * @author caiys
	 * @date 2015年12月22日 下午5:55:13
	 * @return
	 */
	@AjaxCheck
	public Result checkExists() {
		// 请求参数
		String favoriteTypeStr = (String) getParameter("favoriteType");
		String favoriteId = (String) getParameter("favoriteId");
		
		ProductType favoriteType = null;
		if (StringUtils.isNotBlank(favoriteTypeStr)) {
			favoriteType = ProductType.valueOf(favoriteTypeStr);
		}
		User user = (User) getSessionAttribute(UserConstans.CURRENT_LOGIN_USER);
		Long userId = null;
		if (user != null) {
			userId = user.getId();
			boolean exists = otherFavoriteService.checkExists(favoriteType, Long.valueOf(favoriteId), userId);
			map.put("success", true);
			map.put("errorMsg", "");
			map.put("exists", exists);
			JSONObject json = JSONObject.fromObject(map);
			return json(json);
		} 
		map.put("success", false);
		map.put("errorMsg", "用户未登录");
		JSONObject json = JSONObject.fromObject(map);
		return json(json);
	}

	public OtherFavorite getOtherFavorite() {
		return otherFavorite;
	}

	public void setOtherFavorite(OtherFavorite otherFavorite) {
		this.otherFavorite = otherFavorite;
	}

}
