package com.data.data.hmly.action.other;

import com.data.data.hmly.action.other.util.VisitHistoryCookieUtils;
import com.data.data.hmly.action.user.UserConstans;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.hotel.HotelService;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.other.OtherVisitHistoryService;
import com.data.data.hmly.service.other.entity.OtherVisitHistory;
import com.framework.hibernate.util.Page;
import com.framework.struts.AjaxCheck;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.opensymphony.xwork2.Result;
import com.zuipin.action.JxmallAction;
import com.zuipin.util.CollectionUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VisitHistoryWebAction extends JxmallAction {
	private static final long serialVersionUID = 900341852078393539L;
	@Resource
	private OtherVisitHistoryService otherVisitHistoryService;
    @Resource
    private HotelService hotelService;

    private Integer page = 1;
	private Integer rows = 10;

	// 返回数据
	Map<String, Object> map = new HashMap<String, Object>();
	
	/**
	 * 分页显示历史记录
	 * @author caiys
	 * @date 2015年12月22日 下午5:34:10
	 * @return
	 */
	@AjaxCheck
	public Result page() {
		// 请求参数
		String resType = (String) getParameter("resType");
		String title = (String) getParameter("title");
		// 读取cookie，如果cookie失效需重写cookie
		String cookieId = VisitHistoryCookieUtils.getCookieId(getRequest(), getResponse());
		
		OtherVisitHistory ovh = new OtherVisitHistory();
		if (StringUtils.isNotBlank(resType)) {
			ovh.setResType(ProductType.valueOf(resType));
		}
		ovh.setTitle(title);
		ovh.setCookieId(cookieId);
		User user = (User) getSessionAttribute(UserConstans.CURRENT_LOGIN_USER);
		if (user != null) {
			ovh.setUserId(user.getId());
		}
		ovh.setDeleteFlag(false);
		Page pageInfo = new Page(page, rows);
		otherVisitHistoryService.findOtherVisitHistoryList(ovh, pageInfo);
		JSONObject json = JSONObject.fromObject(pageInfo);
		return json(json);
	}

	/**
	 * 查询前N条
	 * 测试链接：/other/visitHistory/listTop.jhtml?limit=10&resType=scenic
	 * @author caiys
	 * @date 2015年12月22日 下午5:34:32
	 * @return
	 */
	@AjaxCheck
	public Result listTop() {
		// 请求参数
		String limit = (String) getParameter("limit");
		if (StringUtils.isBlank(limit)) {	// 默认条数
			limit = "10";	
		}
		String resType = (String) getParameter("resType");
		// 读取cookie，如果cookie失效需重写cookie
		String cookieId = VisitHistoryCookieUtils.getCookieId(getRequest(), getResponse());
		
		OtherVisitHistory ovh = new OtherVisitHistory();
		if (StringUtils.isNotBlank(resType)) {
			ovh.setResType(ProductType.valueOf(resType));
		}
		ovh.setCookieId(cookieId);
		User user = (User) getSessionAttribute(UserConstans.CURRENT_LOGIN_USER);
		if (user != null) {
			ovh.setUserId(user.getId());
		}
		ovh.setDeleteFlag(false);
        List<OtherVisitHistory> otherVisitHistorys = otherVisitHistoryService.findOtherVisitHistoryTop(ovh, Integer.parseInt(limit));
        List<Long> hotelIds = Lists.newArrayList(Lists.transform(otherVisitHistorys, new Function<OtherVisitHistory, Long>() {
            @Override
            public Long apply(OtherVisitHistory otherVisitHistory) {
                return otherVisitHistory.getResObjectId();
            }
        }));
       List<Hotel> hotels = hotelService.listByIds(hotelIds);
        List<Map<String, Object>> results = Lists.newArrayList(Lists.transform(hotels, new Function<Hotel, Map<String, Object>>() {
            @Override
            public Map<String, Object> apply(Hotel hotel) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("imgPath", hotel.getCover());
                map.put("path", "/hotel_detail_" + hotel.getId() + ".html");
                map.put("title", hotel.getName());
                map.put("star", hotel.getStar());
                map.put("score", hotel.getScore());
				int count = CollectionUtils.isEmpty(hotel.getCommentList()) ? 0 : hotel.getCommentList().size();
				map.put("count", count);
                return map;
            }
        }));
        JSONArray json = JSONArray.fromObject(results);
		return json(json);
	}
	
	/**
	 * 批量清除浏览历史
	 * @author caiys
	 * @date 2015年12月22日 下午5:55:13
	 * @return
	 */
	@AjaxCheck
	public Result batchClearHistory() {
		// 请求参数
		String resTypeStr = (String) getParameter("resType");
		// 读取cookie，如果cookie失效需重写cookie
		String cookieId = VisitHistoryCookieUtils.getCookieId(getRequest(), getResponse());
		
		ProductType resType = null;
		if (StringUtils.isNotBlank(resTypeStr)) {
			resType = ProductType.valueOf(resTypeStr);
		}
		User user = (User) getSessionAttribute(UserConstans.CURRENT_LOGIN_USER);
		Long userId = null;
		if (user != null) {
			userId = user.getId();
		}
		otherVisitHistoryService.doClearHistoryBy(cookieId, userId, resType);
		map.put("success", true);
		map.put("errorMsg", "");
		JSONObject json = JSONObject.fromObject(map);
		return json(json);
	}
	
	/**
	 * 根据标识清除浏览历史
	 * @author caiys
	 * @date 2015年12月22日 下午5:55:13
	 * @return
	 */
	@AjaxCheck
	public Result clearHistory() {
		// 请求参数
		String ids = (String) getParameter("ids");
		// 读取cookie，如果cookie失效需重写cookie
		String cookieId = VisitHistoryCookieUtils.getCookieId(getRequest(), getResponse());
		User user = (User) getSessionAttribute(UserConstans.CURRENT_LOGIN_USER);
		Long userId = null;
		if (user != null) {
			userId = user.getId();
		}
		otherVisitHistoryService.doClearHistoryBy(ids, cookieId, userId);
		map.put("success", true);
		map.put("errorMsg", "");
		JSONObject json = JSONObject.fromObject(map);
		return json(json);
	}
	
	
}
