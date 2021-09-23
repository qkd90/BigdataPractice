package com.data.data.hmly.service.wechat.dao;

import com.data.data.hmly.service.wechat.entity.WechatResource;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Repository
public class WechatResourceDao extends DataAccess<WechatResource> {
	
	/**
	 * 查询资源
	 * @author caiys
	 * @date 2015年11月24日 上午10:33:12
	 * @param wechatResource
	 * @return
	 */
	public List<WechatResource> findWechatResource(WechatResource wechatResource) {
		Criteria<WechatResource> criteria = new Criteria<WechatResource>(WechatResource.class);
		if (StringUtils.isNotBlank(wechatResource.getContent())) {
			criteria.eq("content", wechatResource.getContent());
		}
		if (wechatResource.getResType() != null) {
			criteria.eq("resType", wechatResource.getResType());
			
		}
		if (wechatResource.getValidFlag() != null) {
			criteria.eq("validFlag", wechatResource.getValidFlag());
		}
		
		criteria.orderBy("createTime", "asc");
		return findByCriteria(criteria);
	}


	public List<WechatResource> getList(WechatResource wechatResource, Page page) {
		Criteria<WechatResource> criteria = new Criteria<WechatResource>(WechatResource.class);
		if (StringUtils.isNotBlank(wechatResource.getResName())) {
			criteria.like("resName", wechatResource.getResName());
		}
		return findByCriteria(criteria, page);
	}

	public WechatResource get(Long id) {
		if (id == null || id == 0) {
			return null;
		}
		Criteria<WechatResource> criteria = new Criteria<WechatResource>(WechatResource.class);
		criteria.eq("id", id);
		return findUniqueByCriteria(criteria);
	}

    public List<WechatResource> getByIds(String ids) {
        List<Long> idList = new ArrayList<Long>();
        for (String id : ids.split(",")) {
            if (id != null && !"".equals(id)) {
                idList.add(Long.parseLong(id));
            }
        }
        Criteria<WechatResource> criteria = new Criteria<WechatResource>(WechatResource.class);
        criteria.in("id", idList);
        return findByCriteria(criteria);
    }


	public List<WechatResource> getResourceList() {
		Criteria<WechatResource> criteria = new Criteria<WechatResource>(WechatResource.class);
		return findAll();
	}

	public List<WechatResource> getByIdSet(HashSet<Long> idSet) {
		Criteria<WechatResource> criteria = new Criteria<WechatResource>(WechatResource.class);
		criteria.in("id", idSet);
		
		return findByCriteria(criteria);
	}

}
