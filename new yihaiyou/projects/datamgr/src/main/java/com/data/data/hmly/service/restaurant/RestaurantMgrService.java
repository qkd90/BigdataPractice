package com.data.data.hmly.service.restaurant;

import com.data.data.hmly.service.base.service.BaseService;
import com.data.data.hmly.service.restaurant.dao.RestaurantDao;
import com.data.data.hmly.service.restaurant.entity.Restaurant;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import com.zuipin.util.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2015/11/30.
 */
@Service
public class RestaurantMgrService extends BaseService<Restaurant> {
    @Resource
    private RestaurantDao restaurantDao;


    public Restaurant selBySourceId(String sourceId) {
        Criteria<Restaurant> c = new Criteria<Restaurant>(Restaurant.class);
        c.eq("sourceId", sourceId);
        return restaurantDao.findUniqueByCriteria(c);
    }

    public Restaurant selById(Long id) {
        return restaurantDao.load(id);
    }

    @Override
    public DataAccess<Restaurant> getDao() {
        return restaurantDao;
    }

    @Override
    public Criteria<Restaurant> makeCriteria(Map<String, Object> paramMap, Criteria<Restaurant> c) {
        DetachedCriteria dc_area = c.createCriteria("city", "city");
        if (paramMap.containsKey("id") && Long.parseLong(paramMap.get("id").toString()) > 0) {
            c.eq("id", Long.parseLong(paramMap.get("id").toString()));
        }
        if (paramMap.containsKey("name") && !"".equals(paramMap.get("name"))) {
            c.like("name", "%" + paramMap.get("name") + "%");
        }
        if (paramMap.containsKey("score") && !"".equals(paramMap.get("score"))) {
            c.eq("score", paramMap.get("score"));
        }
        if (paramMap.containsKey("ranking") && !"".equals(paramMap.get("ranking"))) {
            c.eq("ranking", paramMap.get("ranking"));
        }
        if (paramMap.containsKey("price") && !"".equals(paramMap.get("price"))) {
            c.eq("price", paramMap.get("price"));
        }
        if (paramMap.containsKey("cover") && !"".equals(paramMap.get("cover"))) {
            c.eq("cover", paramMap.get("cover"));
        }
        if (paramMap.containsKey("cityCode") && !"".equals(paramMap.get("cityCode"))) {
            dc_area.add(Restrictions.like("cityCode", paramMap.get("cityCode") + "%"));
            if (paramMap.get("isChina") != null && Boolean.valueOf(paramMap.get("isChina").toString())) {
                dc_area.add(Restrictions.sqlRestriction("length(city_code) = 6"));
            } else if (paramMap.get("isChina") != null && !Boolean.valueOf(paramMap.get("isChina").toString())) {
                dc_area.add(Restrictions.sqlRestriction("length(city_code) = 7"));
            }
        }
        if (paramMap.containsKey("isShow") && !"".equals(paramMap.get("isShow"))) {
            c.eq("isShow", paramMap.get("isShow"));
        }
        if (paramMap.containsKey("status") && !"".equals(paramMap.get("status"))) {
            c.eq("status", paramMap.get("status"));
        }
        if (paramMap.containsKey("createTime") && !"".equals(paramMap.get("createTime"))) {
            c.eq("createTime", paramMap.get("createTime"));
        }
        if (paramMap.containsKey("modifyTime") && !"".equals(paramMap.get("modifyTime"))) {
            c.eq("modifyTime", paramMap.get("modifyTime"));
        }
        if (paramMap.containsKey("createdBy") && !"".equals(paramMap.get("createdBy"))) {
            c.eq("created_by", paramMap.get("createdBy"));
        }
        if (paramMap.containsKey("feature") && !"".equals(paramMap.get("feature"))) {
            c.eq("feature", paramMap.get("feature"));
        }
        if (paramMap.containsKey("hotNum") && !"".equals(paramMap.get("hotNum"))) {
            c.eq("hotNum", paramMap.get("hotNum"));
        }
        if (paramMap.containsKey("ids")) {
            ArrayList<?> ids = (ArrayList) paramMap.get("ids");
            if (ids.size() > 0) {
                c.in("id", ids);
            }
        }
        if (paramMap.containsKey("needCoverImage") && StringUtils.isNotBlank(paramMap.get("needCoverImage").toString())) {
            c.isNotNull("cover");
            c.ne("cover", "");
        }
        if (paramMap.containsKey("notNeedCoverImage") && StringUtils.isNotBlank(paramMap.get("notNeedCoverImage").toString())) {
            c.or(Restrictions.isNull("cover"), Restrictions.eq("cover", ""));
        }
        if (paramMap.containsKey("resName") && !"".equals(paramMap.get("resName"))) {
            c.like("name", "%" + paramMap.get("resName") + "%");
        }
        return c;
    }


}
