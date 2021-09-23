package com.data.data.hmly.service.restaurant;

import com.data.data.hmly.service.base.service.BaseService;
import com.data.data.hmly.service.restaurant.dao.DelicacyRestaurantDao;
import com.data.data.hmly.service.restaurant.entity.Delicacy;
import com.data.data.hmly.service.restaurant.entity.DelicacyRestaurant;
import com.data.data.hmly.service.restaurant.entity.Restaurant;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2015/12/4.
 */
@Service
public class DelicacyResMgrService extends BaseService<DelicacyRestaurant> {

    @Resource
    private DelicacyMgrService delicacyMgrService;
    @Resource
    private DelicacyRestaurantDao delicacyRestaurantDao;

    /**
     * 更新餐厅美食关联状态
     * @param restaurant
     */
    public void updateDelRes(Restaurant restaurant) {
        Criteria<DelicacyRestaurant> c = new Criteria<DelicacyRestaurant>(DelicacyRestaurant.class);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("resId", restaurant.getId());
        c = makeCriteria(paramMap, c);
        List<DelicacyRestaurant> restaurants = delicacyRestaurantDao.findByCriteria(c);
        for (DelicacyRestaurant delicacyRestaurant : restaurants) {
            delicacyRestaurant.setStatus(restaurant.getStatus());
            delicacyRestaurantDao.update(delicacyRestaurant);
            doBuildAndIndex(delicacyRestaurant.getDelicacy().getId(), restaurant.getId());
        }
    }

    public void doBuildAndIndex(Long delicacyId, Long resId) {
        // 自动构建和索引相应的美食
        Criteria<DelicacyRestaurant> c = new Criteria<DelicacyRestaurant>(DelicacyRestaurant.class);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("delicacyId", delicacyId);
        paramMap.put("resId", resId);
        c = makeCriteria(paramMap, c);
        DelicacyRestaurant delicacyRestaurant = delicacyRestaurantDao.findUniqueByCriteria(c);
        Delicacy delicacy = delicacyRestaurant.getDelicacy();
        delicacyMgrService.buildAndIndex(delicacy);
    }

    public List<Long> listResIds(Map<String, Object> paramMap) {
        Criteria<DelicacyRestaurant> c = new Criteria<DelicacyRestaurant>(DelicacyRestaurant.class);
        c = makeCriteria(paramMap, c);
        List<Long> ids = new ArrayList<Long>();
        List<DelicacyRestaurant> delicacyRestaurantList = delicacyRestaurantDao.findByCriteria(c);
        if (delicacyRestaurantList != null && delicacyRestaurantList.size() > 0) {
            for (DelicacyRestaurant delicacyRestaurant : delicacyRestaurantList) {
                Long resId = delicacyRestaurant.getRestaurant().getId();
                ids.add(resId);
            }
        }
        return ids;
    }


    public void delByResId(Long resId, Long delicacyId) {
        Criteria<DelicacyRestaurant> c = new Criteria<DelicacyRestaurant>(DelicacyRestaurant.class);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("resId", resId);
        paramMap.put("delicacyId", delicacyId);
        c = makeCriteria(paramMap, c);
        delicacyRestaurantDao.delete(delicacyRestaurantDao.findUniqueByCriteria(c));
    }

    @Override
    public DataAccess<DelicacyRestaurant> getDao() {
        return delicacyRestaurantDao;
    }

    @Override
    public Criteria<DelicacyRestaurant> makeCriteria(Map<String, Object> paramMap, Criteria<DelicacyRestaurant> c) {
        DetachedCriteria dc_res = c.createCriteria("restaurant", "res");
        DetachedCriteria dc_deli = c.createCriteria("delicacy", "deli");
        if (paramMap.containsKey("delicacyId") && Long.parseLong(paramMap.get("delicacyId").toString()) > 0) {
            dc_deli.add(Restrictions.eq("id", Long.parseLong(paramMap.get("delicacyId").toString())));
        }
        if (paramMap.containsKey("resId") && Long.parseLong(paramMap.get("resId").toString()) > 0) {
            dc_res.add(Restrictions.eq("id", Long.parseLong(paramMap.get("resId").toString())));
        }
        return c;
    }
}

