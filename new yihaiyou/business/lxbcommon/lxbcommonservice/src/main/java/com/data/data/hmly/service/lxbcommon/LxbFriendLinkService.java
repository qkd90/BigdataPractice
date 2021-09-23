package com.data.data.hmly.service.lxbcommon;


import com.data.data.hmly.service.lxbcommon.dao.LxbFriendLinkDao;
import com.data.data.hmly.service.lxbcommon.entity.LxbFriendLink;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zzl on 2016/3/25.
 */
@Service
public class LxbFriendLinkService {

    @Resource
    private LxbFriendLinkDao lxbFriendLinkDao;

    public LxbFriendLink get(Long id) {
        return lxbFriendLinkDao.load(id);
    }

    public void save(LxbFriendLink lxbFriendLink) {
        lxbFriendLinkDao.save(lxbFriendLink);
    }

    public void update(LxbFriendLink lxbFriendLink) {
        lxbFriendLinkDao.update(lxbFriendLink);
    }

    public List<LxbFriendLink> list(LxbFriendLink lxbFriendLink, Page page, String...orderProperties) {
        Criteria<LxbFriendLink> criteria = createCriteria(lxbFriendLink, orderProperties);
        if (page != null) {
            return lxbFriendLinkDao.findByCriteria(criteria, page);
        }
        return lxbFriendLinkDao.findByCriteria(criteria);
    }


    public Criteria<LxbFriendLink> createCriteria(LxbFriendLink condition, String... orderProperties) {

        Criteria<LxbFriendLink> criteria = new Criteria<LxbFriendLink>(LxbFriendLink.class);
        if (orderProperties != null) {
            if (orderProperties.length > 1 && orderProperties[0] != null && orderProperties[1] != null) {
                criteria.orderBy(orderProperties[0], orderProperties[1]);
            } else if (orderProperties.length == 1 && orderProperties[0] != null) {
                criteria.orderBy(orderProperties[0], "desc");
            }
        }
        if (StringUtils.hasText(condition.getLinkName())) {
            criteria.like("linkName", condition.getLinkName());
        }
        if (condition.getStatus() != null) {
            criteria.eq("status", condition.getStatus());
        }
        return criteria;
    }

}
