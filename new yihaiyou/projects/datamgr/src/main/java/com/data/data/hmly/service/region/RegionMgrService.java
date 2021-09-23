package com.data.data.hmly.service.region;

import com.data.data.hmly.service.base.service.BaseService;
import com.data.data.hmly.service.scenic.dao.RegionDao;
import com.data.data.hmly.service.scenic.entity.Region;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by zzl on 2015/12/7.
 */
@Service
public class RegionMgrService extends BaseService<Region> {

    @Resource
    private RegionDao regionDao;

    public Region selById(Long id) {
        return regionDao.load(id);
    }

    public void del(Long id) {
        regionDao.delete(id, Region.class);
    }

    public void update(Region region) {
        regionDao.update(region);
    }

    public void insert(Region region) {
        regionDao.save(region);
    }

    @Override
    public DataAccess<Region> getDao() {
        return regionDao;
    }

    @Override
    public Criteria<Region> makeCriteria(Map<String, Object> paramMap, Criteria<Region> c) {
        DetachedCriteria dc_city = c.createCriteria("city", "city");
        if (paramMap.containsKey("id") && Long.parseLong(paramMap.get("id").toString()) > 0) {
            c.eq("id", Long.parseLong(paramMap.get("id").toString()));
        }
        if (paramMap.containsKey("name") && !"".equals(paramMap.get("name").toString())) {
            c.like("name", paramMap.get("name").toString(), MatchMode.ANYWHERE);
        }
        if (paramMap.containsKey("points") && !"".equals(paramMap.get("points").toString())) {
            c.eq("points", paramMap.get("points").toString());
        }
        if (paramMap.containsKey("description") && !"".equals(paramMap.get("description").toString())) {
            c.eq("description", paramMap.get("description").toString());
        }
        if (paramMap.containsKey("priority") && !"".equals(paramMap.get("priority").toString())) {
            c.eq("priority", paramMap.get("priority").toString());
        }
        if (paramMap.containsKey("cityCode") && !"".equals(paramMap.get("cityCode"))) {
            dc_city.add(Restrictions.like("cityCode", paramMap.get("cityCode") + "%"));
            if (paramMap.get("isChina") != null && Boolean.valueOf(paramMap.get("isChina").toString())) {
                dc_city.add(Restrictions.sqlRestriction("length(city_code) = 6"));
            } else if (paramMap.get("isChina") != null && !Boolean.valueOf(paramMap.get("isChina").toString())) {
                dc_city.add(Restrictions.sqlRestriction("length(city_code) = 7"));
            }
        }
        if (paramMap.containsKey("type") && !"".equals(paramMap.get("type").toString())) {
            c.eq("regionType", Integer.parseInt(paramMap.get("type").toString()));
        }
        if (paramMap.containsKey("ids")) {
            String[] ids = (String[]) paramMap.get("ids");
            if (ids.length > 0) {
                c.in("id", ids);
            }
        }
        return c;
    }
}
