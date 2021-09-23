package com.data.data.hmly.service.wechat.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.data.data.hmly.service.wechat.entity.WechatDataNews;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;

/**
 * Created by vacuity on 15/11/20.
 */

@Repository
public class WechatDataImgTextDao extends DataAccess<WechatDataNews> {

	/**
	 * 查询列表
	 * @author caiys
	 * @date 2015年11月25日 下午5:24:45
	 * @param wechatDataImgText
	 * @param limit
	 * @return
	 */
	public List<WechatDataNews> findBy(WechatDataNews wechatDataImgText, Integer limit) {
		Criteria<WechatDataNews> criteria = new Criteria<WechatDataNews>(WechatDataNews.class);
		if (wechatDataImgText.getDataItem() != null) {
			criteria.eq("dataItem.id", wechatDataImgText.getDataItem().getId());
		}
		criteria.orderBy("id", "asc");
		List<WechatDataNews> wechatDataImgTexts = null;
		if (limit != null) {
			Page page = new Page(1, 10);
			wechatDataImgTexts = findByCriteria(criteria, page);
		} else {
			wechatDataImgTexts = findByCriteria(criteria);
		}
		return wechatDataImgTexts;
	}

	public List<WechatDataNews> findByItemId(long itemId) {
		Criteria<WechatDataNews> criteria = new Criteria<WechatDataNews>(WechatDataNews.class);
		criteria.eq("dataItem.id", itemId);
		return findByCriteria(criteria);
	}
}
