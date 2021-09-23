package com.data.data.hmly.service.wechat;

import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.wechat.dao.WechatDataItemDao;
import com.data.data.hmly.service.wechat.entity.WechatDataItem;
import com.data.data.hmly.service.wechat.entity.WechatDataNews;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.gson.inf.MsgTypes;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class WechatDataItemService {
    @Resource
    private WechatDataItemDao wechatDataItemDao;
    @Resource
    private WechatDataImgTextService newsService;

    public WechatDataItem load(Long id) {
    	return wechatDataItemDao.load(id);
    }

	public void save(WechatDataItem wechatDataItem) {
		
		wechatDataItemDao.save(wechatDataItem);
		
	}

	public List<WechatDataItem> findList(SysUnit companyUnit, MsgTypes news, Page pageInfo) {
		
		Criteria<WechatDataItem> criteria = new Criteria<WechatDataItem>(WechatDataItem.class);
		
		criteria.eq("company", companyUnit);
		criteria.eq("type", news);
		
		return wechatDataItemDao.findByCriteria(criteria, pageInfo);
	}

	public void delItem(WechatDataItem dataItem) {
		wechatDataItemDao.delete(dataItem);
	}

	public WechatDataItem getItemById(long id) {
		return wechatDataItemDao.load(id);
	}

	public List<WechatDataItem> findListByKeyword(SysUnit companyUnit,
			MsgTypes news, String keyword, Page pageInfo, SysUser sysUser) {
		
		List<WechatDataNews> allDataNews = newsService.findListBykeyword(keyword, sysUser);
		 Set<Long> itemIds = new HashSet<Long>();  
		
		for (WechatDataNews dataNews : allDataNews) {
			itemIds.add(dataNews.getDataItem().getId());
		}
		
		Criteria<WechatDataItem> criteria = new Criteria<WechatDataItem>(WechatDataItem.class);
		criteria.eq("company", companyUnit);
		criteria.eq("type", news);
		criteria.in("id", itemIds);
		
		return wechatDataItemDao.findByCriteria(criteria, pageInfo);
	}

	/**
	 * 全局管理员过滤图文素材
	 * @param news
	 * @param keyword
	 * @param pageInfo
	 * @return
	 */
	public List<WechatDataItem> findListByKeyword(MsgTypes news,
			String keyword, Page pageInfo) {
		List<WechatDataNews> allDataNews = newsService.findListBykeyword(keyword, null);
		 Set<Long> itemIds = new HashSet<Long>();  
		
		for (WechatDataNews dataNews : allDataNews) {
			itemIds.add(dataNews.getDataItem().getId());
		}
		
		Criteria<WechatDataItem> criteria = new Criteria<WechatDataItem>(WechatDataItem.class);
//		criteria.eq("company", companyUnit);
		criteria.eq("type", news);
		if (itemIds.size() > 0) {
			criteria.in("id", itemIds);
		}
		criteria.orderBy("createTime", "ASC");
		
		return wechatDataItemDao.findByCriteria(criteria, pageInfo);
	}

	/**
	 * 站点管理员过滤图文素材
	 * @param news
	 * @param keyword
	 * @param sysUnList
	 * @param pageInfo
	 * @return
	 */
	public List<WechatDataItem> findListByKeywordByUlist(MsgTypes news,
			String keyword, List<SysUnit> sysUnList, Page pageInfo) {
		List<WechatDataNews> allDataNews = newsService.findListBykeyword(keyword, null);
		 Set<Long> itemIds = new HashSet<Long>();  
		
		for (WechatDataNews dataNews : allDataNews) {
			itemIds.add(dataNews.getDataItem().getId());
		}
		
		Criteria<WechatDataItem> criteria = new Criteria<WechatDataItem>(WechatDataItem.class);
		criteria.in("company", sysUnList);
		criteria.eq("type", news);
		if (itemIds != null && itemIds.size() > 0) {
			criteria.in("id", itemIds);
		}
		criteria.orderBy("createTime", "ASC");
		return wechatDataItemDao.findByCriteria(criteria, pageInfo);
	}

}
