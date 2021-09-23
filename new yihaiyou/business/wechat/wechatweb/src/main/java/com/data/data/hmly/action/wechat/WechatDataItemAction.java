package com.data.data.hmly.action.wechat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.wechat.WechatDataItemService;
import com.data.data.hmly.service.wechat.entity.WechatDataItem;
import com.framework.struts.AjaxCheck;
import com.gson.inf.MsgTypes;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Result;

/**
 * Created by vacuity on 15/11/20.
 */
public class WechatDataItemAction extends FrameBaseAction implements ModelDriven<WechatDataItem> {
	private static final long serialVersionUID = -617072372295001263L;
	@Resource
    private WechatDataItemService itemService;
    private Map<String, Object> map = new HashMap<String, Object>();
    private WechatDataItem wechatDataItem = new WechatDataItem();
    
    
   
    
	@AjaxCheck
    public Result saveItem() {
		
		String type = (String) getParameter("type");
		String itemId = (String) getParameter("itemId");
		
		if (StringUtils.isEmpty(itemId)) {
			wechatDataItem.setCompany(getCompanyUnit());
			wechatDataItem.setCreateTime(new Date());
			wechatDataItem.setUpdateTime(new Date());
			wechatDataItem.setType(MsgTypes.news);
			itemService.save(wechatDataItem);
		} else {
			wechatDataItem = itemService.getItemById(Long.parseLong(itemId));
		}
		
		
		
		map.put("id", wechatDataItem.getId());
		simpleResult(map, true, "");
		return jsonResult(map);
    }
	
	
	

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

	@Override
	public WechatDataItem getModel() {
		return wechatDataItem;
	}
}
