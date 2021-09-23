package com.data.data.hmly.service.lxbcommon;

import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.lxbcommon.dao.CustomRequireDao;
import com.data.data.hmly.service.lxbcommon.dao.CustomRequireDestinationDao;
import com.data.data.hmly.service.lxbcommon.entity.CustomRequire;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by caiys on 2016/6/15.
 */
@Service
public class CustomRequireService {
    @Resource
    private CustomRequireDao customRequireDao;
    @Resource
    private CustomRequireDestinationDao customRequireDestinationDao;

    public CustomRequire get(Long id) {
        return customRequireDao.load(id);
    }

    public void update(CustomRequire customRequire) {
        customRequireDao.update(customRequire);
    }

    public void save(CustomRequire customRequire) {
        customRequireDao.save(customRequire);
    }

    public void saveCustomRequire(CustomRequire customRequire) {
        customRequireDao.save(customRequire);
        customRequireDestinationDao.save(customRequire.getDestinations());
    }

    public List<CustomRequire> getCustomReqList(CustomRequire condition, Page page, String...orderProperties) {
        Criteria<CustomRequire> criteria = createCriteria(condition, orderProperties);
        if (page != null) {
            return customRequireDao.findByCriteria(criteria, page);
        }
        return customRequireDao.findByCriteria(criteria);
    }

    private Criteria<CustomRequire> createCriteria(CustomRequire condition, String... orderProperties) {
        Criteria<CustomRequire> criteria = new Criteria<CustomRequire>(CustomRequire.class);
        if (orderProperties != null) {
            if (orderProperties.length > 1 && orderProperties[0] != null && orderProperties[1] != null) {
                criteria.orderBy(orderProperties[0], orderProperties[1]);
            } else if (orderProperties.length == 1 && orderProperties[0] != null) {
                criteria.orderBy(orderProperties[0], "desc");
            }
        }
        if (condition == null) {
            return criteria;
        }
        if (condition.getCustomType() != null) {
            criteria.eq("customType", condition.getCustomType());
        }
        if (condition.getStartCity() != null) {
            DetachedCriteria dcArea = criteria.createCriteria("startCity", "startCity");
            dcArea.add(Restrictions.like("cityCode", condition.getStartCity().getCityCode() + "%"));
            if (condition.getIsChina() != null && condition.getIsChina()) {
                dcArea.add(Restrictions.sqlRestriction("length(city_code) = 6"));
            } else if (condition.getIsChina() != null && !condition.getIsChina()) {
                dcArea.add(Restrictions.sqlRestriction("length(city_code) = 7"));
            }
        }
        if (condition.getStartDate() != null) {
            criteria.eq("startDate", condition.getStartDate());
        }
        if (condition.getDay() != null && condition.getDay() > 0) {
            criteria.eq("day", condition.getDay());
        }
        if (condition.getAdjustFlag() != null) {
            criteria.eq("adjustFlag", condition.getAdjustFlag());
        }
        if (condition.getArrange() != null) {
            criteria.eq("arrange", condition.getArrange());
        }
        if (condition.getAdult() != null && condition.getAdult() > 0) {
            criteria.eq("adult", condition.getAdult());
        }
        if (condition.getChild() != null && condition.getChild() > 0) {
            criteria.eq("child", condition.getChild());
        }
        if (condition.getMinPrice() != null && condition.getMinPrice() > 0F) {
            criteria.eq("minPrice", condition.getMinPrice());
        }
        if (condition.getMaxPrice() != null && condition.getMaxPrice() > 0F) {
            criteria.eq("maxPrice", condition.getMaxPrice());
        }
        if (StringUtils.hasText(condition.getContactor())) {
            criteria.like("contactor", condition.getContactor());
        }
        if (StringUtils.hasText(condition.getContactPhone())) {
            criteria.like("contactPhone", condition.getContactPhone());
        }
        if (StringUtils.hasText(condition.getContactEmail())) {
            criteria.like("contactEmail", condition.getContactEmail());
        }
        if (condition.getMember() != null && condition.getMember().getId() != null) {
            criteria.eq("member.id", condition.getMember().getId());
        } else if (condition.getMember() != null) {
            DetachedCriteria creatorCriteria = criteria.createCriteria("member");
            Member member = condition.getMember();
            if (StringUtils.hasText(member.getUserName())) {
                creatorCriteria.add(Restrictions.like("userName", member.getUserName(), MatchMode.ANYWHERE));
            }
            if (StringUtils.hasText(member.getNickName())) {
                creatorCriteria.add(Restrictions.like("nickName", member.getNickName(), MatchMode.ANYWHERE));
            }
            // ...
        }
        if (condition.getCreateTime() != null) {
            criteria.eq("createTime", condition.getCreateTime());
        }
        if (condition.getStatus() != null) {
            criteria.eq("status", condition.getStatus());
        }
        if (StringUtils.hasText(condition.getRemark())) {
            criteria.like("remark", condition.getRemark());
        }
        if (condition.getHandler() != null) {
            DetachedCriteria handlerCriteria = criteria.createCriteria("handler");
            SysUser sysUser = condition.getHandler();
            if (StringUtils.hasText(sysUser.getUserName())) {
                handlerCriteria.add(Restrictions.like("userName", sysUser.getUserName(), MatchMode.ANYWHERE));
            }
        }
        if (condition.getHandleTime() != null) {
            criteria.eq("handleTime", condition.getHandleTime());
        }
        return criteria;
    }
}
