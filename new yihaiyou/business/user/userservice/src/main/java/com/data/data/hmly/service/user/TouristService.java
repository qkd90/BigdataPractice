package com.data.data.hmly.service.user;

import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.user.dao.TouristDao;
import com.data.data.hmly.service.user.entity.Tourist;
import com.data.data.hmly.service.user.entity.enums.TouristStatus;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.StringUtils;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guoshijie on 2015/10/30.
 */
@Service
public class TouristService {

    @Resource
    private TouristDao touristDao;

    public void save(Tourist tourist) {
        touristDao.save(tourist);
    }

    public void update(Tourist tourist) {
        touristDao.update(tourist);
    }
    public Tourist get(Long id) {
        Criteria<Tourist> criteria = new Criteria<Tourist>(Tourist.class);
        criteria.eq("id", id);
        return touristDao.findUniqueByCriteria(criteria);
    }

    public Long countMyTourist(Tourist tourist) {
        Criteria<Tourist> criteria = createCriteria(tourist);
        criteria.eq("status", TouristStatus.normal);
        criteria.setProjection(Projections.rowCount());
        return (Long) touristDao.findUniqueValue(criteria);
    }

    public List<Tourist> list(Tourist tourist, Page page, String... orderProperty) {
        Criteria<Tourist> criteria = createCriteria(tourist, orderProperty);
        criteria.eq("status", TouristStatus.normal);
        if (page == null) {
            return touristDao.findByCriteria(criteria);
        }
        return touristDao.findByCriteria(criteria, page);
    }

    public List<Tourist> listByIds(List<Long> ids) {
        Criteria<Tourist> criteria = new Criteria<Tourist>(Tourist.class);
        criteria.in("id", ids.toArray());
        return touristDao.findByCriteria(criteria);
    }

    public Criteria<Tourist> createCriteria(Tourist tourist, String... orderProperty) {
        Criteria<Tourist> criteria = new Criteria<Tourist>(Tourist.class);
        if (orderProperty.length == 2) {
            criteria.orderBy(orderProperty[0], orderProperty[1]);
        } else if (orderProperty.length == 1) {
            criteria.orderBy(orderProperty[0], "desc");
        }
        if (tourist == null) {
            return criteria;
        }
        if (tourist.getUser() != null) {
            criteria.eq("user.id", tourist.getUser().getId());
        }
        if (tourist.getName() != null) {
            criteria.like("name", tourist.getName());
        }
        return criteria;
    }

    public List<Tourist> getMyTourist(User user, String name, Page page) {
        Criteria<Tourist> criteria = new Criteria<Tourist>(Tourist.class);
        criteria.eq("user.id", user.getId());
        criteria.eq("status", TouristStatus.normal);
        if (StringUtils.isNotBlank(name)) {
            criteria.like("name", name);
        }
        if (page != null) {
            return touristDao.findByCriteria(criteria, page);
        }
        return touristDao.findByCriteria(criteria);

    }

    public Integer countMyTouristByidNumber(User user, String idNumber) {
        Criteria<Tourist> criteria = new Criteria<>(Tourist.class);
        criteria.eq("user.id", user.getId());
        criteria.eq("status", TouristStatus.normal);
        criteria.eq("idNumber", idNumber);
        criteria.setProjection(Projections.rowCount());
        Long count = touristDao.findLongCriteria(criteria);
        return count == null ? 0 : count.intValue();
    }

    public Long myTouristCount(User user, String name) {
        Criteria<Tourist> criteria = new Criteria<Tourist>(Tourist.class);
        criteria.eq("user.id", user.getId());
        criteria.eq("status", TouristStatus.normal);
        if (StringUtils.isNotBlank(name)) {
            criteria.like("name", name);
        }
        criteria.setProjection(Projections.rowCount());
        return touristDao.findLongCriteria(criteria);
    }

    // 批量删除
    public void delByIds(String touristIds, User user) {
        List<Long> idList = new ArrayList<Long>();
        for (String id : touristIds.split(",")) {
            if (id != null && !"".equals(id)) {
                idList.add(Long.parseLong(id));
            }
        }
        Criteria<Tourist> criteria = new Criteria<Tourist>(Tourist.class);
        criteria.in("id", idList);
        criteria.eq("user.id", user.getId());
        List<Tourist> touristList = touristDao.findByCriteria(criteria);
        if (!touristList.isEmpty()) {
            for (Tourist tourist : touristList) {
                tourist.setStatus(TouristStatus.deleted);
            }
            touristDao.save(touristList);
        }
    }

    public void delById(Long id, User user) {
        Criteria<Tourist> criteria = new Criteria<Tourist>(Tourist.class);
        criteria.eq("id", id);
        criteria.eq("user.id", user.getId());
        List<Tourist> touristList = touristDao.findByCriteria(criteria);
        if (!touristList.isEmpty()) {
            for (Tourist tourist : touristList) {
                tourist.setStatus(TouristStatus.deleted);
            }
            touristDao.save(touristList);
        }
    }

    // 新增/更新联系人
    public void saveTourist(Tourist tourist) {
        touristDao.saveOrUpdate(tourist, tourist.getId());
    }

    public Tourist getByUserAndIdNumber(Long userId, String idNumber) {
        Criteria<Tourist> criteria = new Criteria<Tourist>(Tourist.class);
        criteria.eq("user.id", userId);
        criteria.eq("idNumber", idNumber);
        criteria.eq("status", TouristStatus.normal);
        return touristDao.findUniqueByCriteria(criteria);
    }



}
