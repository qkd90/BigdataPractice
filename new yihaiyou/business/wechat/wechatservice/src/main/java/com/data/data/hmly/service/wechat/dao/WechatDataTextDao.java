package com.data.data.hmly.service.wechat.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.data.data.hmly.service.wechat.entity.WechatDataItem;
import com.data.data.hmly.service.wechat.entity.WechatDataText;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;

/**
 * Created by vacuity on 15/11/20.
 */

@Repository
public class WechatDataTextDao extends DataAccess<WechatDataText> {

	/**
	 * 查询列表
	 * @author caiys
	 * @date 2015年11月25日 下午4:56:00
	 * @param wechatDataText
	 * @return
	 */
	public List<WechatDataText> findBy(WechatDataText wechatDataText) {
		Criteria<WechatDataText> criteria = new Criteria<WechatDataText>(WechatDataText.class);
		if (wechatDataText.getDataItem() != null) {
			criteria.eq("dataItem.id", wechatDataText.getDataItem().getId());
		}
		List<WechatDataText> wechatDataTexts = findByCriteria(criteria);
		return wechatDataTexts;
	}

	public List<WechatDataText> findByItemId(WechatDataItem item) {
		Criteria<WechatDataText> criteria = new Criteria<WechatDataText>(WechatDataText.class);
		if (item != null) {
			criteria.eq("dataItem", item);
		}
		List<WechatDataText> wechatDataTexts = findByCriteria(criteria);
		return wechatDataTexts;
	}
}
