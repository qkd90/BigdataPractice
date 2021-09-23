package com.data.data.hmly.service.wechat.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.wechat.entity.WechatDataItem;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import com.gson.inf.MsgTypes;

/**
 * Created by vacuity on 15/11/20.
 */

@Repository
public class WechatDataItemDao extends DataAccess<WechatDataItem> {

	
	public List<WechatDataItem> findItemList(MsgTypes type, SysUnit companyUnit) {
		Criteria<WechatDataItem> criteria = new Criteria<WechatDataItem>(WechatDataItem.class);
		List<WechatDataItem> dataItems = new ArrayList<WechatDataItem>();
		if (type != null) {
			criteria.eq("type", type);
		}
		if (companyUnit != null) {
			criteria.eq("company", companyUnit);
		}
		
		dataItems = findByCriteria(criteria);
		
		return dataItems;
	}

	/**
	 * 站点管理员获取文本素材
	 * @param type
	 * @param sysUnList
	 * @return
	 */
	public List<WechatDataItem> findItemListByUlist(MsgTypes type,
			List<SysUnit> sysUnList) {
		Criteria<WechatDataItem> criteria = new Criteria<WechatDataItem>(WechatDataItem.class);
		List<WechatDataItem> dataItems = new ArrayList<WechatDataItem>();
		if (type != null) {
			criteria.eq("type", type);
		}
		if (sysUnList != null) {
			criteria.in("company", sysUnList);
		}
		
		dataItems = findByCriteria(criteria);
		
		return dataItems;
	}
	
	
	
	
}
