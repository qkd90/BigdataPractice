package com.data.data.hmly.service.region;

import com.data.data.hmly.service.base.service.BaseService;
import com.data.data.hmly.service.hotel.dao.HotelRegionDao;
import com.data.data.hmly.service.hotel.entity.HotelRegion;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by zzl on 2015/12/7.
 */
@Service
public class HotelRegionMgrService extends BaseService<HotelRegion> {

    @Resource
    private HotelRegionDao hotelRegionDao;

    public HotelRegion selById(Long id) {
        return hotelRegionDao.load(id);
    }

    public void del(Long id) {
        hotelRegionDao.delete(id, HotelRegion.class);
    }

    public void update(HotelRegion region) {
        hotelRegionDao.update(region);
    }

    public void insert(HotelRegion region) {
        hotelRegionDao.save(region);
    }

    @Override
    public DataAccess<HotelRegion> getDao() {
        return hotelRegionDao;
    }

    @Override
    public Criteria<HotelRegion> makeCriteria(Map<String, Object> paramMap, Criteria<HotelRegion> c) {
        DetachedCriteria dc_city = c.createCriteria("city", "city");
        if (paramMap.containsKey("id") && Long.parseLong(paramMap.get("id").toString()) > 0) {
            c.eq("id", Long.parseLong(paramMap.get("id").toString()));
        }
        if (paramMap.containsKey("name") && !"".equals(paramMap.get("name"))) {
            c.like("name", paramMap.get("name").toString());
        }
        if (paramMap.containsKey("points") && !"".equals(paramMap.get("points"))) {
            c.eq("points", paramMap.get("points").toString());
        }
        if (paramMap.containsKey("description") && !"".equals(paramMap.get("description"))) {
            c.eq("description", paramMap.get("description").toString());
        }
        if (paramMap.containsKey("priority") && !"".equals(paramMap.get("priority"))) {
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
        if (paramMap.containsKey("name") && !"".equals(paramMap.get("name"))) {
            c.like("name", paramMap.get("name").toString());
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
