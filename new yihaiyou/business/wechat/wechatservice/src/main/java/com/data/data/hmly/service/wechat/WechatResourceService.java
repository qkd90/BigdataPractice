package com.data.data.hmly.service.wechat;

import com.data.data.hmly.service.wechat.dao.WechatResourceDao;
import com.data.data.hmly.service.wechat.entity.WechatResource;
import com.framework.hibernate.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;

@Service
public class WechatResourceService {
    @Resource
    private WechatResourceDao wechatResourceDao;
    
	/**
	 * 查询资源
	 * @author caiys
	 * @date 2015年11月24日 上午10:33:12
	 * @param wechatResource
	 * @return
	 */
	public List<WechatResource> findWechatResource(WechatResource wechatResource) {
		return wechatResourceDao.findWechatResource(wechatResource);
	}


	public List<WechatResource> getList(WechatResource wechatResource, Page page) {
		return wechatResourceDao.getList(wechatResource, page);
	}

	public WechatResource get(Long id) {
        return wechatResourceDao.get(id);
	}

    public void saveOrUpdate(WechatResource wechatResource) {
        wechatResourceDao.saveOrUpdate(wechatResource, wechatResource.getId());
    }


    public void doValidByIds(String ids, Boolean validFlag) {
        List<WechatResource> wechatResourceList = wechatResourceDao.getByIds(ids);
        for (WechatResource wechatResource : wechatResourceList) {
            wechatResource.setValidFlag(validFlag);
        }
        wechatResourceDao.updateAll(wechatResourceList);
    }

    public List<WechatResource> getResourceList() {
        return wechatResourceDao.getResourceList();
    }

    public List<WechatResource> getByIdSet(HashSet<Long> idSet) {
        return wechatResourceDao.getByIdSet(idSet);
    }
}
